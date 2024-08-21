/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservizit.business.api.CronoProgrammaFasiApi;
import it.csi.pbandi.pbservizit.dto.ResponseCodeMessageAllegati;
import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbservizit.dto.cronoprogrammaFasi.AllegatiCronoprogrammaFasi;
import it.csi.pbandi.pbservizit.dto.cronoprogrammaFasi.CronoprogrammaFasiItem;
import it.csi.pbandi.pbservizit.dto.cronoprogrammaFasi.CronoprogrammaListFasiItemAllegati;
import it.csi.pbandi.pbservizit.exception.ErroreGestitoException;
import it.csi.pbandi.pbservizit.integration.dao.CronoProgrammaFasiDAO;
import it.csi.pbandi.pbservizit.util.BeanUtil;
import it.csi.pbandi.pbservizit.util.Constants;

@Service
public class CronoProgrammaFasiApiImpl implements CronoProgrammaFasiApi {

	@Autowired
	protected BeanUtil beanUtil;
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	CronoProgrammaFasiDAO cronoProgrammaDAO;

	////////////////////////// getDataCronoprogramma //////////////////////////////

	public Response getDataCronoprogramma(Long idProgetto, HttpServletRequest req) throws Exception {

		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");

		try {

			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			if (idProgetto == null)
				throw new Exception("Id progetto non valorizzato");

			// Recupero delle informazioni principali
			List<CronoprogrammaFasiItem> cronoprogrammaFasiItemList = cronoProgrammaDAO
					.getDataCronoprogramma(idProgetto);
			List<CronoprogrammaListFasiItemAllegati> response = splitByIdIter(cronoprogrammaFasiItemList);
			setFasiAttive(response, idProgetto);

			// Recupero degli allegati
			getAllegati(idProgetto, response);

			LOG.debug(prf + " END");

			return Response.ok().entity(response).build();
			// return Response.status(Response.Status.OK).entity(response).build();
			// return ResponseEntity.ok(response);

		} catch (Exception e) {
			LOG.error(prf + "Exception e " + e.getMessage());

			return Response.status(500).entity(e.getMessage()).build();

		}

	}

	private List<CronoprogrammaListFasiItemAllegati> splitByIdIter(
			List<CronoprogrammaFasiItem> cronoprogrammaFasiItemList) {

		LinkedList<CronoprogrammaListFasiItemAllegati> result = new LinkedList<CronoprogrammaListFasiItemAllegati>();

		int indexList = 0;
		CronoprogrammaFasiItem precedente = null;
		CronoprogrammaListFasiItemAllegati tempList = new CronoprogrammaListFasiItemAllegati();

		while (indexList < cronoprogrammaFasiItemList.size()) {
			CronoprogrammaFasiItem attuale = cronoprogrammaFasiItemList.get(indexList);

			if (precedente == null || !precedente.getIdIter().equals(attuale.getIdIter())) {

				tempList = new CronoprogrammaListFasiItemAllegati();
				result.add(tempList);

			}

			tempList.addItem(attuale);
			precedente = attuale;

			indexList++;
		}

		return result;
	}

	private void setFasiAttive(List<CronoprogrammaListFasiItemAllegati> data, Long idProgetto) throws Exception {

		// questo serve per abilitare/disabilitare le fasi dell'iter, solo una fase iter
		// alla volta puo' essere attiva
		// la fase attiva è la prima null

		int i = 0;
		Long idDichiarazioneSpesaPrecedente = null;
		Long idTipoDichiarazSpesaPrecedente = null;
		Boolean flagFaseChiusaPrecedente = null;
		Boolean flagFaseChiusa = null;
		Boolean trovata = Boolean.FALSE;
		try {
			while (i < data.size()) {
				List<CronoprogrammaFasiItem> list = data.get(i).getFasiItemList();
				flagFaseChiusaPrecedente = flagFaseChiusa;
				flagFaseChiusa = cronoProgrammaDAO.getFlagFaseChiusaProgettoIter(idProgetto, list.get(0).getIdIter());

				if (list.get(0).getIdDichiarazioneSpesa() == null && !trovata) {
					for (CronoprogrammaFasiItem temp : list) {
						temp.setFaseIterAttiva(true);
					}
					trovata = Boolean.TRUE;
				}
				if (i > 0) {
					if (flagFaseChiusaPrecedente != null && flagFaseChiusaPrecedente.equals(Boolean.TRUE)
							&& idDichiarazioneSpesaPrecedente == null && idTipoDichiarazSpesaPrecedente == null) {
//						Abilitare la fase con id_iter N e se la fase N non ha il tipo di dichiarazione di spesa
//						allora abilitare la fase successiva N+1
						for (CronoprogrammaFasiItem temp : list) {
							temp.setFaseIterAttiva(true);
						}
						trovata = Boolean.TRUE;
					}
				}

				if (list.size() > 0) {
					idDichiarazioneSpesaPrecedente = list.get(0).getIdDichiarazioneSpesa();
					idTipoDichiarazSpesaPrecedente = list.get(0).getIdTipoDichiarazSpesa();
				}
				i++;
			}
		} catch (Exception e) {
			throw e;
		}

	}

	private void getAllegati(Long idProgetto, List<CronoprogrammaListFasiItemAllegati> response) throws Exception {

		for (CronoprogrammaListFasiItemAllegati temp : response) {
			if (temp.getFasiItemList().size() > 0) {
				Long idIter = temp.getFasiItemList().get(0).getIdIter();
				if (idIter != null) {
					temp.setAllegatiList(cronoProgrammaDAO.getAllegatiByIter(idProgetto, idIter));
					if (temp.getAllegatiList() != null && temp.getAllegatiList().size() > 0) {
						for (AllegatiCronoprogrammaFasi all : temp.getAllegatiList()) {
							all.setAssociato(Boolean.TRUE);
						}
					}
				}
			}

		}

	}

	////////////////////////// saveDataCronoprogramma //////////////////////////////

	@Override
	@Transactional
	public Response saveDataCronoprogramma(Long idProgetto,
			List<CronoprogrammaListFasiItemAllegati> dataCronoprogrammaItemeAllegati, HttpServletRequest req)
			throws Exception {

		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");

		ResponseCodeMessageAllegati response = null;
		EsitoAssociaFilesDTO esito = null;
		try {

			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			if (idUtente == null)
				throw new Exception("variabili id utente non valorizzata");

			if (idProgetto == null)
				throw new Exception("variabili id progetto non valorizzata");

			if (dataCronoprogrammaItemeAllegati == null)
				throw new Exception("dati  non valorizzati");

			// per ogni elemento nella lista devo capire se esiste nel DB, se
			// se non eiste inserisco il record nel DB
			// se esiste vado in update
			for (CronoprogrammaListFasiItemAllegati tmpList : dataCronoprogrammaItemeAllegati) {

				if (tmpList == null)
					throw new Exception("dati  non valorizzati");
				boolean dateCompletate = true;
				for (CronoprogrammaFasiItem tmp : tmpList.getFasiItemList()) {

					if (tmp == null)
						throw new Exception("uno o piu elementi nei dati sono null");

					boolean exist = cronoProgrammaDAO.existRowInProgettoFaseMonit(idProgetto, tmp.getIdFaseMonit());
					if (!exist) {
						// inserimento
						cronoProgrammaDAO.insertDateInProgettoFaseMonit(idProgetto, tmp, idUtente);
					} else {
						// update
						if (tmp.getEdit()) {
							cronoProgrammaDAO.updateDateInProgettoFaseMonit(idProgetto, tmp, idUtente);
						}
					}
					if (tmp.getDataPrevista() == null || tmp.getDataEffettiva() == null) {
						dateCompletate = false;
					}
				}

				// cerco nella PBANDI_R_PROGETTO_ITER, se non c'e' inserisco un nuovo record
				// default flag_fase_chiusa a 0, metterlo a 1 quando anche l’ultima data
				// effettiva dell'ultima attivita' della fase abilitata e' compilata
				if (tmpList != null && tmpList.getFasiItemList()!=null && tmpList.getFasiItemList().size()>0 && tmpList.getFasiItemList().get(0)!=null) {
					cronoProgrammaDAO.insertOrUpdateProgettoIter(idProgetto,
							tmpList.getFasiItemList().get(0).getIdIter());
				}
				
				if (tmpList.getAllegatiList() != null && tmpList.getAllegatiList().size() > 0) {
					AssociaFilesRequest request = new AssociaFilesRequest();
					List<Long> ids = new ArrayList<Long>();
					for (AllegatiCronoprogrammaFasi all : tmpList.getAllegatiList()) {
						if (all.getAssociato().equals(Boolean.FALSE)) {
							ids.add(all.getIdDocumentoIndex());
						}
					}
					request.setElencoIdDocumentoIndex((ArrayList<Long>) ids);
					request.setIdProgetto(tmpList.getAllegatiList().get(0).getIdProgetto());
					esito = cronoProgrammaDAO.associaAllegati(request, tmpList.getAllegatiList().get(0).getIdIter(),
							idUtente);
				}
			}
		} catch (Exception e) {
			LOG.error(prf + "Exception e " + e.getMessage());

			return Response.status(500).entity(e.getMessage()).build();
		} finally {
			LOG.info(prf + " END");
		}

		response = new ResponseCodeMessageAllegati("OK", "Salvataggio avvenuto con successo", esito);
		return Response.ok().entity(response).build();
	}

	@Override
	public Response disassociaAllegato(Long idDocumentoIndex, Long idProgettoIter, Long idProgetto,
			HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(cronoProgrammaDAO.disassociaAllegato(idDocumentoIndex, idProgettoIter, idProgetto,
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response getAllegatiIterProgetto(Long idProgetto, Long idIter, HttpServletRequest req) throws Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idIter == 0) {
			throw new InvalidParameterException("idIter non valorizzato");
		}
		if (userInfo.getIdUtente() == 0) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		if (userInfo.getIdIride() == null) {
			throw new InvalidParameterException("idIride non valorizzato");
		}

		List<AllegatiCronoprogrammaFasi> allegati = null;
		try {
			allegati = cronoProgrammaDAO.getAllegatiByIter(idProgetto, idIter);
			if (allegati != null && allegati.size() > 0) {
				for (AllegatiCronoprogrammaFasi all : allegati) {
					all.setAssociato(Boolean.TRUE);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
		return Response.ok().entity(allegati).build();
	}

}
