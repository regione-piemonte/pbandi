/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.business.CustomSecurityHelper;
import it.csi.pbandi.pbservizit.business.api.CronoProgrammaApi;
import it.csi.pbandi.pbservizit.business.intf.MessageConstants;
import it.csi.pbandi.pbservizit.dto.ExecResults;
import it.csi.pbandi.pbservizit.dto.cronoprogramma.CronoprogrammaItem;
import it.csi.pbandi.pbservizit.dto.cronoprogramma.DatiCronoprogramma;
import it.csi.pbandi.pbservizit.dto.cronoprogramma.EsitoOperazioni;
import it.csi.pbandi.pbservizit.dto.cronoprogramma.Iter;
import it.csi.pbandi.pbservizit.dto.cronoprogramma.MotivoScostamento;
import it.csi.pbandi.pbservizit.dto.cronoprogramma.ResponseGetFasiMonit;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.integration.dao.CronoProgrammaDAO;
import it.csi.pbandi.pbservizit.integration.request.SalvaFasiMonitoraggioGestioneRequest;
import it.csi.pbandi.pbservizit.integration.request.ValidazioneDatiCronoProgrammaRequest;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.profilazione.ProfilazioneBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma.EsitoFindFasiMonitoraggio;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma.EsitoSaveFasiMonitoraggio;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma.FaseMonitoraggioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma.IterDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma.MotivoScostamentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma.TipoOperazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DatiGeneraliDTO;
import it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbservizit.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbservizit.util.Constants;
import it.csi.pbandi.pbservizit.util.ErrorMessages;
import it.csi.pbandi.pbservizit.util.UseCaseConstants;

@Service
public class CronoProgrammaApiServiceImpl implements CronoProgrammaApi {

//	@Autowired
//	protected BeanUtil beanUtil;
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
//	@Autowired
//	InizializzazioneDAO inizializzazioneDAO;
	
	@Autowired
	CronoProgrammaDAO cronoProgrammaDAO;
	
//	@Autowired
//	private ProfilazioneSrv profilazioneSrv;
	
	@Autowired
	private CustomSecurityHelper springSecurityHelper;

	@Autowired
	GestioneDatiGeneraliBusinessImpl gestioneDatiGeneraliBusinessImpl;
	
	@Autowired
	private ProfilazioneBusinessImpl profilazioneSrv;
	
	@Override
	public Response getCodiceProgetto(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[CronoProgrammaServiceImpl::getCodiceProgetto]";
		LOG.debug(prf + " BEGIN");
		try {
			if(idProgetto == null) { return inviaErroreBadRequest("Parametro mancato: ?[idProgetto]"); }
			String codiceProgetto = cronoProgrammaDAO.findCodiceProgetto(idProgetto);

			LOG.debug(prf + " END");
			return Response.ok().entity(codiceProgetto).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	@Override
	public Response getProgrammazioneByIdProgetto(HttpServletRequest req, Long idProgetto) throws Exception {
		String prf = "[CronoProgrammaServiceImpl::getProgrammazioneByIdProgetto]";
		LOG.debug(prf + " BEGIN");
		if(idProgetto == null) return inviaErroreBadRequest("Parametero mancato ?[idProgetto]");
		
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
				
			String programmazione = cronoProgrammaDAO.findProgrammazioneByIdProgetto(idUtente, idIride, idProgetto);
			
			LOG.debug(prf + " END");
			return Response.ok().entity(programmazione).build();
		} catch (Exception e) {
			LOG.error(prf+"Exception e "+e.getMessage());
			//e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Response getTipoOperazione(HttpServletRequest req, Long idProgetto, String programmazione) throws Exception {
		String prf = "[CronoProgrammaServiceImpl::getTipoOperazione]";
		LOG.debug(prf + " BEGIN");
		if(idProgetto == null) return inviaErroreBadRequest("Parametero mancato ?[idProgetto]");
		if(programmazione == null) return inviaErroreBadRequest("Parametero mancato ?[programmazione]");
		
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
				
			TipoOperazioneDTO[] tipiOperazione = cronoProgrammaDAO.findTipoOperazione(idUtente, idIride, idProgetto, programmazione);
			
			LOG.debug(prf + " END");
			return Response.ok().entity(tipiOperazione[0]).build();
		} catch (Exception e) {
			LOG.error(prf+"Exception e "+e.getMessage());
			//e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Response getFasiMonitoraggioGestione(HttpServletRequest req, Long idProgetto, String programmazione)
			throws Exception {
		String prf = "[CronoProgrammaServiceImpl::getFasiMonitoraggioGestione]";
		LOG.debug(prf + " BEGIN");
		if(idProgetto == null) return inviaErroreBadRequest("Parametero mancato ?[idProgetto]");
		if(programmazione == null) return inviaErroreBadRequest("Parametero mancato ?[programmazione]");
		
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			DatiCronoprogramma datiCrono = new DatiCronoprogramma();
			boolean setAllEditable = false; 
			EsitoFindFasiMonitoraggio esito = cronoProgrammaDAO.findFasiMonitoraggioGestione(idUtente, idIride, idProgetto, programmazione);
			LOG.info(prf + " esito :"+ esito.getDescIter() + " successo :"+ esito.getSuccesso());
			ArrayList<CronoprogrammaItem> res = new ArrayList<CronoprogrammaItem>();
			if (esito.getSuccesso()) {
				if (esito.getFasiMonitoraggio() != null) {
					for (FaseMonitoraggioDTO dto : esito.getFasiMonitoraggio()) {
						CronoprogrammaItem item = new CronoprogrammaItem();

						item.setIdFaseMonit(dto.getIdFaseMonit());
						item.setDescFaseMonit(dto.getDescFaseMonit());
						item.setFlagObbligatorio(dto.getObbligatorio());
						item.setCodIgrue(dto.getCodIgrueT35());
						item.setIdIter(dto.getIdIter());

						//LOG.info(prf + " dtInizio : " + dto.getDtInizioEffettiva() + " :::: " + dto.getDtInizioEffettiva().toString());
						item.setDtInizioPrevista(dto.getDtInizioPrevista() != null ? dto.getDtInizioPrevista().toString() : null);
						item.setDtInizioEffettiva(dto.getDtInizioEffettiva() != null ? dto.getDtInizioEffettiva().toString() : null);
						item.setDtFinePrevista(dto.getDtFinePrevista() != null ? dto.getDtFinePrevista().toString() : null);
						item.setDtFineEffettiva(dto.getDtFineEffettiva() != null ? dto.getDtFineEffettiva().toString() : null);
						item.setIdMotivoScostamento(dto.getIdMotivoScostamento());

						
						// setto le editabilità dei campi
						item.setMotivoScostamentoEditable(true);
						
						/*
						 * FIX PBandi-2101.
						 * L'editabilita' delle date dipende se l' utente e'
						 * abilitato al caso d'uso OPECRN001 ed al caso d'uso OPECRN001-A
						 */

 						if (springSecurityHelper.verifyCurrentUserForUC(userInfo, UseCaseConstants.UC_OPECRN001)) {
							/*
							 * Come era prima della fix PBandi-2101
							 */
 							LOG.info(prf + " OPECRN001");
							item.setDtInizioPrevistaEditable(false || setAllEditable);
							item.setDtInizioEffettivaEditable(true);
							item.setDtFinePrevistaEditable(false || setAllEditable);
							item.setDtFineEffettivaEditable(true);
		
//							if (dto.getDtInizioPrevista() == null  || setAllEditable) {
//								item.setDtInizioPrevistaEditable(true);
//							}
//							if (dto.getDtFinePrevista() == null  || setAllEditable) {
//								item.setDtFinePrevistaEditable(true);
//							}
						}
						
 						if (springSecurityHelper.verifyCurrentUserForUC(userInfo, UseCaseConstants.UC_OPECRN001_A)) {
							LOG.info(prf + " OPECRN001-A");
							item.setDtInizioPrevistaEditable(true);
							item.setDtInizioEffettivaEditable(true);
							item.setDtFinePrevistaEditable(true);
							item.setDtFineEffettivaEditable(true);
						}
						

						// alla fine aggiungo l'elemento alla lista
						res.add(item);
					}

				}
				
				// aggiungo i dati cronoprogramma
				datiCrono.setDescIter(esito.getDescIter());
				datiCrono.setDtConcessioneComitato(esito.getDtConcessione() != null ? DateUtil.getData(esito.getDtConcessione().getTime()) : null);			
				datiCrono.setCodiceFaseFinaleObbligatoria(esito.getCodFaseObbligatoriaFinale());
				
				
			}

			ResponseGetFasiMonit responseGetFasiMonit = new ResponseGetFasiMonit();
			responseGetFasiMonit.setItem(res);
			responseGetFasiMonit.setDatiCrono(datiCrono);
			LOG.debug(prf + " END");
			return Response.ok().entity(responseGetFasiMonit).build();
		} catch (Exception e) {
			LOG.error(prf+"Exception e "+e.getMessage());
			//e.printStackTrace();
			throw e;
		}
	}
	
	
	@Override
	public Response getMotivoScostamento(HttpServletRequest req) throws Exception {
		String prf = "[CronoProgrammaServiceImpl::getMotivoScostamento]";
		LOG.debug(prf + " BEGIN");
		
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
				
			MotivoScostamentoDTO[]  lista = cronoProgrammaDAO.findMotivoScostamento(idUtente, idIride);
			ArrayList<MotivoScostamento> res = new ArrayList<MotivoScostamento>();
			if (lista != null) {
				for (MotivoScostamentoDTO dto : lista) {
					MotivoScostamento m = new MotivoScostamento();
					m.setIdMotivoScostamento(dto.getIdMotivoScostamento());
					m.setDescMotivoScostamento(dto.getDescMotivoScostamento());
					m.setCodIgrue(dto.getCodIgrueT37T49T53());
					res.add(m);
				}
			}
			LOG.debug(prf + " END");
			return Response.ok().entity(res).build();
		} catch (Exception e) {
			LOG.error(prf+"Exception e "+e.getMessage());
			//e.printStackTrace();
			throw e;
		}
				
	}

	

	@Override
	public Response getIter(HttpServletRequest req, Long  idTipoOperazione, String programazzione) throws Exception {
		String prf = "[CronoProgrammaServiceImpl::getIter]";
		LOG.debug(prf + " BEGIN");
		
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			
			IterDTO[] listaIter = cronoProgrammaDAO.findIter(idUtente, idIride , idTipoOperazione, programazzione);
			ArrayList<Iter> res =  new ArrayList<Iter>();
			if (listaIter != null) {
				for (IterDTO dto : listaIter) {
					Iter it = new Iter();
					it.setIdIter(dto.getIdIter());
					it.setDescIter(dto.getDescIter());
					it.setCodIgrue(dto.getCodIgrueT35());
					res.add(it);
				}
			}
			
			LOG.debug(prf + " END");
			return Response.ok().entity(res).build();
		} catch (Exception e) {
			LOG.error(prf+"Exception e "+e.getMessage());
			//e.printStackTrace();
			throw e;
		}
	}

	/*
	@Override
	public Response controllaDatiPerSalvataggioGestione(HttpServletRequest req, ValidazioneDatiCronoProgrammaRequest validazioneDatiRequest)
			throws Exception {
		String prf = "[CronoProgrammaServiceImpl::controllaDatiPerSalvataggioGestione]";
		LOG.info(prf + " START");
		try {
			ExecResults result = new ExecResults();
			boolean isError = false;
			Set<String> globalErrors = new HashSet<String>();
			Set<String> globalMessages = new HashSet<String>();
			Map<String, String> fieldErrors = new HashMap<String, String>();
			
			// controllo prima fase: data inizio prevista deve essere posteriore
			// alla data presentazione domanda (se esiste)
			//  NON VALE PIU : [DM] 15-11-2010: vale per tutte le fasi
			// 3/3/2011 TNT vale solo per tipo operazione ==3

			// CR 20 maggio
			boolean isBR30 = false;
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Long idBeneficiario = userInfo.getBeneficiarioSelezionato().getIdBeneficiario();
			
			LOG.info(prf + " CARICA DATI GENERALI");
			DatiGeneraliDTO datiGenerali = gestioneDatiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride, validazioneDatiRequest.getIdProgetto(), idBeneficiario);
			Long idBando = datiGenerali.getIdBandoLinea();
			LOG.info(prf + " idBando = "+ idBando);
			
			if (profilazioneSrv.isRegolaApplicabileForBandoLinea(idUtente, idIride, idBando, RegoleConstants.BR30_DATA_AMMISSIBILITA_PER_PROGETTTI)) {
				isBR30 = true;
			}
			

			int rowNum = 0;

			boolean faseFinaleOk = false;
			boolean altreFasiOk = false;

			for (CronoprogrammaItem item : validazioneDatiRequest.getFasi()) {
				boolean isRowError = eseguiControlliFormaliSuRiga(item, rowNum,
						fieldErrors, globalErrors, validazioneDatiRequest.getDatiCrono(), isBR30);
				if (isRowError) {
					isError = isRowError;
				}
				rowNum++;
			}
			
			if (!isError) {
				faseFinaleOk = eseguiControlliFaseFinale(validazioneDatiRequest.getFasi(), validazioneDatiRequest.getDatiCrono());
				if (faseFinaleOk) {
					altreFasiOk = eseguiControlliAltreFasi(validazioneDatiRequest.getFasi(), fieldErrors,
							globalErrors, validazioneDatiRequest.getDatiCrono());
					// Attenzione! Si sono valorizzate le date della fase conclusiva: è necessario valorizzare le date delle altre fasi presenti
					if (!altreFasiOk)
						isError = true;
				}
			}
			if (isError) {
				//MessageManager.setGlobalErrors(result, globalErrors.toArray(new String[]{}));
				result.setGlobalErrors(globalErrors);
				result.setFldErrors(fieldErrors);
				//result.setResultCode(codeError);
			} else {
				globalMessages.add(MessageConstants.CONFERMA_SALVATAGGIO);
				result.setGlobalMessages(globalMessages);
				
			}
	
			LOG.info(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			LOG.error(prf+"Exception e "+e.getMessage());
			//e.printStackTrace();
			throw e;
		}
			
	}
	
	private boolean eseguiControlliAltreFasi(ArrayList<CronoprogrammaItem> fasi, Map<String, String> fieldErrors,
			Set<String> globalErrors, DatiCronoprogramma datiCrono) {
		String prf = "[CronoProgrammaServiceImpl::eseguiControlliAltreFasi]";
		LOG.info(prf + " START");
		boolean altreFasiOk = true;
		int rowNum = 0;
		for (CronoprogrammaItem item : fasi) {
			String rowName = "appDataelencoFasiCronoprogramma[" + rowNum + "]";
			String dtInizioEffettivaField = rowName + ".dtInizioEffettiva";
			String dtFineEffettivaField = rowName + ".dtFineEffettiva";
			String dtInizioPrevistaField = rowName + ".dtInizioPrevista";
			String dtFinePrevistaField = rowName + ".dtFinePrevista";

			if (!datiCrono.getCodiceFaseFinaleObbligatoria().equalsIgnoreCase(
					item.getCodIgrue())) {

				if (!StringUtil.isEmpty(item.getDtInizioPrevista())) {
					altreFasiOk = true;

					if (StringUtil.isEmpty(item.getDtInizioEffettiva())) {
						altreFasiOk = false;
						fieldErrors.put(dtInizioEffettivaField, ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO);
					}

					if (StringUtil.isEmpty(item.getDtFineEffettiva())) {
						altreFasiOk = false;
						fieldErrors.put(dtFineEffettivaField, ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO);
					}
					if (StringUtil.isEmpty(item.getDtFinePrevista())) {
						altreFasiOk = false;
						fieldErrors.put(dtFinePrevistaField, ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO);
					}
				}

			}

			rowNum++;
		}
		LOG.info(prf + " END");
		return altreFasiOk;
	}
	

	private boolean eseguiControlliFaseFinale(ArrayList<CronoprogrammaItem> fasi, DatiCronoprogramma datiCrono) {
		String prf = "[CronoProgrammaServiceImpl::eseguiControlliFaseFinale]";
		LOG.info(prf + " START");
		boolean okFaseFinale = false;

		for (CronoprogrammaItem item : fasi) {
			if (datiCrono.getCodiceFaseFinaleObbligatoria().equalsIgnoreCase(
					item.getCodIgrue())) {
				if (!StringUtil.isEmpty(item.getDtInizioEffettiva())
						&& !StringUtil.isEmpty(item.getDtFineEffettiva())) {
					if (DateUtil.isValidDate(item.getDtInizioEffettiva())
							&& DateUtil.isValidDate(item.getDtFineEffettiva())) {
						okFaseFinale = true;
						break;
					}
				}
			}
		}
		LOG.info(prf + " END");
		return okFaseFinale;
	}

	private boolean eseguiControlliFormaliSuRiga(CronoprogrammaItem item, int rowNum, Map<String, String> fieldErrors,
			Set<String> globalErrors, DatiCronoprogramma datiCrono, boolean isBR30) throws Exception {
		String prf = "[CronoProgrammaServiceImpl::eseguiControlliFormaliSuRiga]";
		LOG.debug(prf + " START");
		String rowName = "appDataelencoFasiCronoprogramma[" + rowNum + "]"; // speriamo


		String dtInizioPrevistaField = rowName + ".dtInizioPrevista";
		String dtInizioEffettivaField = rowName + ".dtInizioEffettiva";
		String dtFinePrevistaField = rowName + ".dtFinePrevista";
		String dtFineEffettivaField = rowName + ".dtFineEffettiva";
		String idMotivoScostamentoField = rowName + ".idMotivoScostamento";

		boolean isError = false;
		boolean isDtInizioPrevistaValid = true;
		boolean isDtInizioEffettivaValid = true;
		boolean isDtFinePrevistaValid = true;
		boolean isDtFineEffettivaValid = true;

		// le date inserite sono valide?
		if (!StringUtil.isEmpty(item.getDtInizioPrevista())) {
			if (!DateUtil.isValidDate(item.getDtInizioPrevista())) {
				fieldErrors.put(dtInizioPrevistaField, " Data Inizio Prevista["+rowNum+"] - " +ErrorMessages.ERRORE_FORMATO_DATA);
				isDtInizioPrevistaValid = false;
				isError = true;
			}
		}

		if (datiCrono.getCodiceFaseFinaleObbligatoria().equalsIgnoreCase(
				item.getCodIgrue())
				&& StringUtil.isEmpty(item.getDtInizioEffettiva())
				&& !StringUtil.isEmpty(item.getDtFineEffettiva())) {
			fieldErrors.put(dtInizioEffettivaField,  " ["+rowNum+"] - " + ErrorMessages.KEY_MSG_DATE_EFFETTIVE_OBBLIGATORIE_CRONO);
			isDtInizioEffettivaValid = false;
			isError = true;
		}

		if (datiCrono.getCodiceFaseFinaleObbligatoria().equalsIgnoreCase(
				item.getCodIgrue())
				&& StringUtil.isEmpty(item.getDtFineEffettiva())
				&& !StringUtil.isEmpty(item.getDtInizioEffettiva())) {
			fieldErrors.put(dtFineEffettivaField, " ["+rowNum+"] - " + ErrorMessages.KEY_MSG_DATE_EFFETTIVE_OBBLIGATORIE_CRONO);
			isDtFineEffettivaValid = false;
			isError = true;
		}

		if (!StringUtil.isEmpty(item.getDtInizioEffettiva())) {
			if (!DateUtil.isValidDate(item.getDtInizioEffettiva())) {
				fieldErrors.put(dtInizioEffettivaField, " Data Inizio Effettiva["+rowNum+"] - " + ErrorMessages.FORMATO_DATA);
				isDtInizioEffettivaValid = false;
				isError = true;
			}
		}

		if (!StringUtil.isEmpty(item.getDtFinePrevista())) {
			if (!DateUtil.isValidDate(item.getDtFinePrevista())) {
				fieldErrors.put(dtFinePrevistaField, " Data Fine Prevista["+rowNum+"] - "+ ErrorMessages.FORMATO_DATA);
				isDtFinePrevistaValid = false;
				isError = true;
			}
		}

		if (!StringUtil.isEmpty(item.getDtFineEffettiva())) {
			if (!DateUtil.isValidDate(item.getDtFineEffettiva())) {
				fieldErrors.put(dtFineEffettivaField, " Data Fine Effettiva["+rowNum+"] - " + ErrorMessages.FORMATO_DATA);
				isDtFineEffettivaValid = false;
				isError = true;
			}
		}
		
		// 3b. Fase con "data inizio effettiva" valorizzata e
		// "data inizio prevista" non valorizzata
		if ((!StringUtil.isEmpty(item.getDtInizioEffettiva()) && isDtInizioEffettivaValid)
				&& StringUtil.isEmpty(item.getDtInizioPrevista())) {
			fieldErrors.put(dtInizioPrevistaField, " ["+rowNum+"] - " + ErrorMessages.KEY_MSG_MANCA_DATA_INIZIO_PREVISTA_CRONO);
			isError = true;
		}

		// 3c. Fase con "data fine effettiva" valorizzata e "data fine prevista"
		// non valorizzata
		if ((!StringUtil.isEmpty(item.getDtFineEffettiva()) && isDtFineEffettivaValid)
				&& StringUtil.isEmpty(item.getDtFinePrevista())) {
			fieldErrors.put(dtFinePrevistaField," ["+rowNum+"] - " + ErrorMessages.KEY_MSG_MANCA_DATA_FINE_PREVISTA_CRONO);
			isError = true;
		}
		
		// 3e. Fase con "data inizio/fine prevista" parzialmente valorizzate
		if (StringUtil.isEmpty(item.getDtInizioPrevista())
				&& (!StringUtil.isEmpty(item.getDtFinePrevista()) && isDtFinePrevistaValid)) {
			fieldErrors.put(dtInizioPrevistaField, ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO + "Data Inizio Prevista ["+rowNum+"]");
			globalErrors.add("["+rowNum+"] - " + ErrorMessages.KEY_MSG_DATE_NON_OBBLIGATORIE_CRONO);
			isError = true;
		}
		if ((!StringUtil.isEmpty(item.getDtInizioPrevista()) && isDtInizioPrevistaValid)
				&& StringUtil.isEmpty(item.getDtFinePrevista())) {
			fieldErrors.put(dtFinePrevistaField,  ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO + "Data Fine Prevista ["+rowNum+"]");
			globalErrors.add("["+rowNum+"] - " + ErrorMessages.KEY_MSG_DATE_NON_OBBLIGATORIE_CRONO);
			isError = true;
		}
		
		// 3f. L'attore inserisce Data fine prevista minore di data inizio
		// prevista
		if ((!StringUtil.isEmpty(item.getDtInizioPrevista()) && isDtInizioPrevistaValid)
				&& (!StringUtil.isEmpty(item.getDtFinePrevista()) && isDtFinePrevistaValid)) {
			if (DateUtil.before(DateUtil.getDate(item.getDtFinePrevista()),
					DateUtil.getDate(item.getDtInizioPrevista()))) {
				fieldErrors.put(dtInizioPrevistaField,"["+rowNum+"] - " + ErrorMessages.KEY_ERR_DATE_INCOERENTI_CRONO);
				fieldErrors.put(dtFinePrevistaField, "["+rowNum+"] - " +ErrorMessages.KEY_ERR_DATE_INCOERENTI_CRONO);
				globalErrors.add("["+rowNum+"] - " +ErrorMessages.KEY_MSG_DATA_PREVISTA_FINE_MINORE_INIZIO_CRONO);
				isError = true;
			}
		}
		
		// 3g. L'attore inserisce Data fine effettiva minore di data inizio
		// effettiva
		if ((!StringUtil.isEmpty(item.getDtInizioEffettiva()) && isDtInizioEffettivaValid)
				&& (!StringUtil.isEmpty(item.getDtFineEffettiva()) && isDtFineEffettivaValid)) {
			if (DateUtil.before(DateUtil.getDate(item.getDtFineEffettiva()),
					DateUtil.getDate(item.getDtInizioEffettiva()))) {
				fieldErrors.put(dtInizioEffettivaField, "["+rowNum+"] - " + ErrorMessages.KEY_ERR_DATE_INCOERENTI_CRONO);
				fieldErrors.put(dtFineEffettivaField, "["+rowNum+"] - " + ErrorMessages.KEY_ERR_DATE_INCOERENTI_CRONO);
				globalErrors.add("["+rowNum+"] - " + ErrorMessages.KEY_MSG_DATA_EFFETTIVA_FINE_MINORE_INIZIO_CRONO);
				isError = true;
			}
		}
		
		// 3h. L'attore inserisce Data fine effettiva e/o data inizio effettiva
		// future
		Date oggi = DateUtil.getDataOdiernaSenzaOra();
		if (!StringUtil.isEmpty(item.getDtInizioEffettiva())
				&& isDtInizioEffettivaValid) {
			if (DateUtil.after(DateUtil.getDate(item.getDtInizioEffettiva()), oggi)) {
				fieldErrors.put(dtInizioEffettivaField,  "["+rowNum+"] - " +ErrorMessages.KEY_ERR_DATA_FUTURA_CRONO);
				globalErrors.add("["+rowNum+"] - " +ErrorMessages.KEY_MSG_DATE_EFFETIVE_FUTURE_CRONO);
				isError = true;
			}
		}
		if (!StringUtil.isEmpty(item.getDtFineEffettiva())
				&& isDtFineEffettivaValid) {
			if (DateUtil.after(DateUtil.getDate(item.getDtFineEffettiva()), oggi)) {
				fieldErrors.put(dtFineEffettivaField, "["+rowNum+"] - " +ErrorMessages.KEY_ERR_DATA_FUTURA_CRONO);
				globalErrors.add("["+rowNum+"] - " +ErrorMessages.KEY_MSG_DATE_EFFETIVE_FUTURE_CRONO);
				isError = true;
			}
		}
			
		// 3i. L'attore inserisce Data inizio previsa e/o data inizio effettiva
		// per la prima fase di un iter precedente alla data di presentazione
		// domanda
		// [DM] 15-11-2010: vale per tutte le fasi

		if (!StringUtil.isEmpty(datiCrono.getDtPresentazioneDomanda())) {

			Long idTipoOperazione = datiCrono.getIdTipoOperazione();
			if (idTipoOperazione == 3 && !isBR30) {
				if (!StringUtil.isEmpty(item.getDtInizioPrevista()) && isDtInizioPrevistaValid) {
					if (DateUtil.before(DateUtil.getDate(item.getDtInizioPrevista()), DateUtil.getDate(datiCrono.getDtPresentazioneDomanda()))) {
						fieldErrors.put(dtInizioPrevistaField, "["+rowNum+"] - " +ErrorMessages.KEY_ERR_DATA_PRECEDENTE_PRESENTAZIONE_CRONO);
						globalErrors.add("["+rowNum+"] - " +ErrorMessages.KEY_MSG_DATE_PREVISTA_ED_EFFETTIVE_PRIMA_FASE_CRONO);
						isError = true;
					}
				}

				if (!StringUtil.isEmpty(item.getDtInizioEffettiva())
						&& isDtInizioEffettivaValid) {
					if (DateUtil.before(DateUtil.getDate(item.getDtInizioEffettiva()), DateUtil.getDate(datiCrono.getDtPresentazioneDomanda()))) {
						fieldErrors.put(dtInizioEffettivaField, "["+rowNum+"] - " +ErrorMessages.KEY_ERR_DATA_PRECEDENTE_PRESENTAZIONE_CRONO);
						globalErrors.add("["+rowNum+"] - " +ErrorMessages.KEY_MSG_DATE_PREVISTA_ED_EFFETTIVE_PRIMA_FASE_CRONO);
						isError = true;
					}
				}
			}
		}
		
		// spostato qua da controlli formali in modo che sia sempre eseguito
		if (!StringUtil.isEmpty(item.getDtInizioPrevista())
				&& !StringUtil.isEmpty(item.getDtInizioEffettiva())) {
			if (!DateUtil.equals(DateUtil.getDate(item.getDtInizioPrevista()),
					DateUtil.getDate(item.getDtInizioEffettiva()))) {
				if (item.getIdMotivoScostamento() == null) {
					fieldErrors.put(idMotivoScostamentoField, "["+rowNum+"] - " +ErrorMessages.KEY_MSG_MANCA_MOTIVO_SCOSTAMENTO_CRONO);
					globalErrors.add(ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO + ": Motivo Scostamento ["+rowNum+"]");
					isError = true;
				}
			}
		}
		if (!StringUtil.isEmpty(item.getDtFinePrevista())
				&& !StringUtil.isEmpty(item.getDtFineEffettiva())) {
			if (!DateUtil.equals(DateUtil.getDate(item.getDtFinePrevista()),
					DateUtil.getDate(item.getDtFineEffettiva()))) {
				if (item.getIdMotivoScostamento() == null) {
					fieldErrors.put(idMotivoScostamentoField, "["+rowNum+"] - " +ErrorMessages.KEY_MSG_MANCA_MOTIVO_SCOSTAMENTO_CRONO);
					globalErrors.add(ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO+ ": Motivo Scostamento ["+rowNum+"]");
					isError = true;
				}
			}
		}
		LOG.debug(prf + " END");
		return isError;
	}
	*/

	@Override
	public Response salvaFasiMonitoraggioGestione(HttpServletRequest req,
			SalvaFasiMonitoraggioGestioneRequest salvaRequest) throws Exception {
		String prf = "[CronoProgrammaServiceImpl::salvaFasiMonitoraggioGestione]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			FaseMonitoraggioDTO[] fasiDto = trasformaFasiPerSalvataggio(salvaRequest.getFasi(), false);
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			
			Long idProgetto = salvaRequest.getIdProgetto();
			EsitoSaveFasiMonitoraggio esito  = cronoProgrammaDAO.salvaFasiMonitoraggioGestione(idUtente, idIride, idProgetto, fasiDto);
				
			
			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch (Exception e) {
			LOG.error(prf+"Exception e "+e.getMessage());
			//e.printStackTrace();
			throw e;
		}
	}


	private FaseMonitoraggioDTO[] trasformaFasiPerSalvataggio(ArrayList<CronoprogrammaItem> fasi, boolean isAvvio) {
		String prf = "[CronoProgrammaServiceImpl::trasformaFasiPerSalvataggio]";
		LOG.debug(prf + " START");
		ArrayList<FaseMonitoraggioDTO> lista = new ArrayList<FaseMonitoraggioDTO>();
		for (CronoprogrammaItem item : fasi) {
			if(item.getDtInizioPrevista()!=null || item.getDtFinePrevista()!=null || item.getDtInizioEffettiva()!=null
					|| item.getDtFineEffettiva()!=null ){
				FaseMonitoraggioDTO dto = new FaseMonitoraggioDTO();
				dto.setIdFaseMonit(item.getIdFaseMonit());
				dto.setIdIter(item.getIdIter());
				dto.setDtInizioPrevista(DateUtil.getDate(item.getDtInizioPrevista()));
				dto.setDtFinePrevista(DateUtil.getDate(item.getDtFinePrevista()));
				if (!isAvvio) {
					dto.setDtInizioEffettiva(DateUtil.getDate(item.getDtInizioEffettiva()));
					dto.setDtFineEffettiva(DateUtil.getDate(item.getDtFineEffettiva()));
					dto.setIdMotivoScostamento(item.getIdMotivoScostamento());
				}
				lista.add(dto);
			}
		}
		LOG.debug(prf + " END");
		return lista.toArray(new FaseMonitoraggioDTO[]{});
	}

	
	@Override
	public Response getDataConcessione(HttpServletRequest req, Long idProgetto) throws Exception {
		String prf = "[CronoProgrammaServiceImpl::getDataConcessione]";
		LOG.debug(prf + " START");
		
		try {
			Date dtConcessione = cronoProgrammaDAO.findDataConcessione(idProgetto);
			LOG.debug(prf + " END");
			return  Response.ok().entity(dtConcessione).build();
		}  catch (Exception e) {
			LOG.error(prf+"Exception e "+e.getMessage());
			throw e;
		}
		
		
	}
	
	@Override
	public Response getDataPresentazioneDomanda(HttpServletRequest req, Long idProgetto) throws Exception {
		String prf = "[CronoProgrammaServiceImpl::getDataPresentazioneDomanda]";
		LOG.debug(prf + " START");
		
		try {
			Date dtPresentazioneDomanda = cronoProgrammaDAO.findDataPresentazioneDomanda(idProgetto);
			LOG.debug(prf + " END");
			return  Response.ok().entity(dtPresentazioneDomanda).build();
		}  catch (Exception e) {
			LOG.error(prf+"Exception e "+e.getMessage());
			throw e;
		}
	}


	
	
	
	/****************************************************************** GESTIONE CRONOPROGRAMMA - AVVIO  ******************************************************************/

	@Override
	public Response getFasiMonitoraggioAvvio(HttpServletRequest req, Long idProgetto, String programmazione, Long idIter)
			throws Exception {
		String prf = "[CronoProgrammaServiceImpl::getFasiMonitoraggioAvvio]";
		LOG.debug(prf + " BEGIN");
		if(idProgetto == null) return inviaErroreBadRequest("Parametero mancato ?[idProgetto]");
		if(programmazione == null) return inviaErroreBadRequest("Parametero mancato ?[programmazione]");
		
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			DatiCronoprogramma datiCrono = new DatiCronoprogramma();
			boolean setAllEditable = false; 
			EsitoFindFasiMonitoraggio esito = cronoProgrammaDAO.findFasiMonitoraggioAvvio(idUtente, idIride, idProgetto, programmazione, idIter);
			LOG.info(prf + " esito :"+ esito.getDescIter() + " successo :"+ esito.getSuccesso());
			ArrayList<CronoprogrammaItem> res = new ArrayList<CronoprogrammaItem>();
			if (esito.getSuccesso()) {
				if (esito.getFasiMonitoraggio() != null) {
					for (FaseMonitoraggioDTO dto : esito.getFasiMonitoraggio()) {
						CronoprogrammaItem item = new CronoprogrammaItem();

						item.setIdFaseMonit(dto.getIdFaseMonit());
						item.setDescFaseMonit(dto.getDescFaseMonit());
						item.setFlagObbligatorio(dto.getObbligatorio());
						item.setCodIgrue(dto.getCodIgrueT35());
						item.setIdIter(dto.getIdIter());
						
						item.setDtInizioPrevistaEditable(true);
						item.setDtFinePrevistaEditable(true);
						// alla fine aggiungo l'elemento alla lista
						res.add(item);
					}

				}
				datiCrono.setDtConcessioneComitato(esito.getDtConcessione() != null ? DateUtil.getData(esito.getDtConcessione().getTime()) : null);				
			}
			
			LOG.debug(prf + " END");
			return Response.ok().entity(res).build();
		} catch (Exception e) {
			LOG.error(prf+"Exception e "+e.getMessage());
			//e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public Response controllaDatiPerSalvataggioAvvio(HttpServletRequest req,
			ValidazioneDatiCronoProgrammaRequest validazioneDatiRequest) throws Exception {
		String prf = "[CronoProgrammaServiceImpl::controllaDatiPerSalvataggioAvvio]";
		LOG.debug(prf + " START");
		ExecResults result = new ExecResults();
		
		boolean isError = false;
		Set<String> globalErrors = new HashSet<String>();
		Set<String> globalMessages = new HashSet<String>();
		Map<String, String> fieldErrors = new HashMap<String, String>();

		try {
			// controllo prima fase: data inizio prevista deve essere posteriore
			// alla data presentazione domanda (se esiste)
			//  NON VALE PIU : [DM] 15-11-2010: vale per tutte le fasi
			// 3/3/2011 TNT vale solo per tipo operazione ==3
	
			// CR 20 maggio
			boolean isBR30 = false;
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Long idBeneficiario = userInfo.getBeneficiarioSelezionato().getIdBeneficiario();
			DatiGeneraliDTO datiGenerali = gestioneDatiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride, validazioneDatiRequest.getIdProgetto(), idBeneficiario);
			Long idBando = datiGenerali.getIdBandoLinea();
			LOG.debug(prf + " idBando = "+ idBando);
			
			if (profilazioneSrv.isRegolaApplicabileForBandoLinea(idUtente, idIride, idBando, RegoleConstants.BR30_DATA_AMMISSIBILITA_PER_PROGETTTI)) {
				isBR30 = true;
			}
						
			ArrayList<CronoprogrammaItem> fasi = validazioneDatiRequest.getFasi();
			DatiCronoprogramma datiCrono = validazioneDatiRequest.getDatiCrono();
	
			int i = 0;
			for (CronoprogrammaItem item : fasi) {
				LOG.debug("[CPBEGestioneCronoprogrammaAvvio :: controllaDatiPerSalvataggio] itemFaseMonit = "+ item.getIdFaseMonit());
				String rowName = "appDataelencoFasiCronoprogramma[" + i + "]"; 
			
				String dtInizioPrevistaField = rowName + ".dtInizioPrevista";
				String dtFinePrevistaField = rowName + ".dtFinePrevista";
	
				boolean isRowError = false;
				boolean isDtInizioPrevistaValid = true;
				boolean isDtFinePrevistaValid = true;
	
				// le date inserite sono valide?
				if (!StringUtil.isEmpty(item.getDtInizioPrevista())) {
					if (!DateUtil.isValidDate(item.getDtInizioPrevista())) {
						fieldErrors.put(dtInizioPrevistaField, "Data Inizio Prevista[" + i + "]" + ErrorMessages.ERRORE_FORMATO_DATA );
						isDtInizioPrevistaValid = false;
						isRowError = true;
					}
				}
	
				if (!StringUtil.isEmpty(item.getDtFinePrevista())) {
					if (!DateUtil.isValidDate(item.getDtFinePrevista())) {
						fieldErrors.put(dtFinePrevistaField, "Data Fine Prevista[" + i + "]" + ErrorMessages.ERRORE_FORMATO_DATA );
						isDtFinePrevistaValid = false;
						isRowError = true;
					}
				}
	
				// ci sono tutte le date richieste?
				if (item.getFlagObbligatorio()) {
					// fase obbligatoria
					if (StringUtil.isEmpty(item.getDtInizioPrevista())) {
						fieldErrors.put(dtInizioPrevistaField, "Data Inizio Prevista[" + i + "]" + ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO);
						isRowError = true;
					}
					if (StringUtil.isEmpty(item.getDtFinePrevista())) {
						fieldErrors.put(dtFinePrevistaField, "Data Fine Prevista[" + i + "]" + ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO );
						isRowError = true;
					}
				} else {
					// fase non obbligatoria
					if (StringUtil.isEmpty(item.getDtInizioPrevista()) && (!StringUtil.isEmpty(item.getDtFinePrevista()) && isDtFinePrevistaValid)) {
						fieldErrors.put(dtInizioPrevistaField, "[" + i + "]" + ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO  );
						globalErrors.add("[" + i + "]" +ErrorMessages.KEY_MSG_DATE_NON_OBBLIGATORIE_CRONO);
						isRowError = true;
					}
					if ((!StringUtil.isEmpty(item.getDtInizioPrevista()) && isDtInizioPrevistaValid) && StringUtil.isEmpty(item.getDtFinePrevista())) {
						fieldErrors.put(dtFinePrevistaField,  "[" + i + "]" + ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO );
						globalErrors.add("[" + i + "]" + ErrorMessages.KEY_MSG_DATE_NON_OBBLIGATORIE_CRONO);
						isRowError = true;
					}
				}
	
				boolean errDataInizio = false;
				// controllo coerenza date
				if ((!StringUtil.isEmpty(item.getDtInizioPrevista()) && isDtInizioPrevistaValid) && (!StringUtil.isEmpty(item.getDtFinePrevista()) && isDtFinePrevistaValid)) {
					if (
					// TNT jira 1662
					DateUtil.before(DateUtil.getDate(item.getDtFinePrevista()), DateUtil.getDate(item.getDtInizioPrevista()))) {
						fieldErrors.put(dtInizioPrevistaField, "[" + i + "]" + ErrorMessages.KEY_ERR_DATE_INCOERENTI_CRONO);
						fieldErrors.put(dtFinePrevistaField, "[" + i + "]" + ErrorMessages.KEY_ERR_DATE_INCOERENTI_CRONO );
						globalErrors.add("[" + i + "]" +ErrorMessages.KEY_MSG_DATA_PREVISTA_FINE_MINORE_INIZIO_CRONO);
						isRowError = true;
					}
	
				}
				Long idTipoOperazione = datiCrono.getIdTipoOperazione();
	
				// CR 20 maggio
				LOG.debug("[CPBEGestioneCronoprogrammaAvvio :: controllaDatiPerSalvataggio] idTipoOperazione = "+ idTipoOperazione);
				if (idTipoOperazione == 3 && !isBR30 && !errDataInizio && !StringUtil.isEmpty(datiCrono.getDtPresentazioneDomanda())
						&& (!StringUtil.isEmpty(item.getDtInizioPrevista()) && isDtInizioPrevistaValid)) {
					LOG.debug("[CPBEGestioneCronoprogrammaAvvio :: controllaDatiPerSalvataggio] dataInizioPrevista ="+ item.getDtInizioPrevista());
					LOG.debug("[CPBEGestioneCronoprogrammaAvvio :: controllaDatiPerSalvataggio] dataPresentaioneDomanda ="+ datiCrono.getDtPresentazioneDomanda());
					if ( DateUtil.before( DateUtil.getDate(item.getDtInizioPrevista()), DateUtil.getDate(datiCrono.getDtPresentazioneDomanda()) )
							&& item.getIdFaseMonit() != 30 && item.getIdFaseMonit() != 31 && item.getIdFaseMonit() != 32 ) {
						fieldErrors.put(dtInizioPrevistaField, "[" + i + "]" + ErrorMessages.KEY_ERR_DATA_PRECEDENTE_PRESENTAZIONE_CRONO);
						globalErrors.add("[" + i + "]" + ErrorMessages.KEY_MSG_DATE_PREVISTA_ED_EFFETTIVE_PRIMA_FASE_CRONO);
						isRowError = true;
					}
				}
	
				// se c'è stato un errore sulla riga, allora segnalo globalmente
				// l'errore
				if (isRowError) {
					isError = isRowError;
				}
				i++;
			}
	
			if (isError) {
				//MessageManager.setGlobalErrors(result, globalErrors.toArray(new String[]{}));
				result.setGlobalErrors(globalErrors);
				result.setFldErrors(fieldErrors);
				//result.setResultCode(codeError);
			} else {
				globalMessages.add(MessageConstants.CONFERMA_SALVATAGGIO);
				result.setGlobalMessages(globalMessages);
			}

			return Response.ok().entity(result).build();
		} catch (Exception e) {
			LOG.error(prf+"Exception e "+e.getMessage());
			//e.printStackTrace();
			throw e;
		}
	}


	@Override
	public Response salvaFasiMonitoraggioAvvio(HttpServletRequest req,
			SalvaFasiMonitoraggioGestioneRequest salvaRequest) throws Exception {
		String prf = "[CronoProgrammaServiceImpl::salvaFasiMonitoraggioAvvio]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			FaseMonitoraggioDTO[] fasiDto = trasformaFasiPerSalvataggio(salvaRequest.getFasi(), false);
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			
			Long idProgetto = salvaRequest.getIdProgetto();
			Long idTipoOperazione = salvaRequest.getIdTipoOperazione();
			EsitoSaveFasiMonitoraggio esito  = cronoProgrammaDAO.salvaFasiMonitoraggioAvvio(idUtente, idIride, idProgetto, idTipoOperazione, fasiDto);
				
			
			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch (Exception e) {
			LOG.error(prf+"Exception e "+e.getMessage());
			//e.printStackTrace();
			throw e;
		}
	}


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	///////////////////////////////////////////// METODI DI RESPONSE HTTP /////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private Response inviaErroreBadRequest(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.BAD_REQUEST).entity(esito).type( MediaType.APPLICATION_JSON).build();
	}

}
