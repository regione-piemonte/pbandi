/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.business.service.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbworkspace.business.service.AttivitaService;
import it.csi.pbandi.pbworkspace.dto.RicercaAttivitaPrecedenteDTO;
import it.csi.pbandi.pbworkspace.dto.profilazione.ConsensoInformatoDTO;
import it.csi.pbandi.pbworkspace.exception.ErroreGestitoException;
import it.csi.pbandi.pbworkspace.exception.RecordNotFoundException;
import it.csi.pbandi.pbworkspace.exception.UtenteException;
import it.csi.pbandi.pbworkspace.integration.dao.AttivitaDAO;
import it.csi.pbandi.pbworkspace.integration.request.CambiaStatoNotificaRequest;
import it.csi.pbandi.pbworkspace.integration.request.NotificaRequest;
import it.csi.pbandi.pbworkspace.integration.vo.AttivitaVO;
import it.csi.pbandi.pbworkspace.integration.vo.BandoVO;
import it.csi.pbandi.pbworkspace.integration.vo.PbandiTNotificaProcessoVO;
import it.csi.pbandi.pbworkspace.integration.vo.ProgettoVO;
import it.csi.pbandi.pbworkspace.util.Constants;
import it.csi.pbandi.pbworkspace.util.StringUtil;

@Service
public class AttivitaServiceImpl implements AttivitaService {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	AttivitaDAO attivitaDao;

	@Override
	public Response getBandi(Long idBeneficiario, HttpServletRequest req)
			throws ErroreGestitoException, UtenteException, RecordNotFoundException {
		String prf = "[AttivitaServiceImpl::getBandi]";
		LOG.info(prf + " BEGIN");
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		if (userInfo.getIdSoggetto() == null || userInfo.getCodiceRuolo() == null) {
			throw new UtenteException("utente non valorizzato correttamente");
		}
		if (idBeneficiario == null) {
			throw new InvalidParameterException("idBeneficiario non valorizzato");
		}
		LOG.info(prf + " getBandi with idSoggetto: " + userInfo.getIdSoggetto() + " , ruoloUtente: "
				+ userInfo.getCodiceRuolo() + " , idSoggettoBen: " + idBeneficiario);

		List<BandoVO> bandiOrdinati = new ArrayList<BandoVO>();
		try {

			List<BandoVO> bandi = attivitaDao.getBandi(userInfo.getCodiceRuolo(), userInfo.getIdSoggetto(),
					idBeneficiario);
			if (bandi != null) {
				LOG.info(prf + "sono stati trovati " + bandi.size() + " bandi");

				// E' stato richiesto di elencare prima i bandolinea nuova programmazione
				// e poi quelli vecchia programmazione (6/4/2017).
				// (nota: BandoVO.nomeBandoLinea è il nome del bandolinea, non del bando)
				bandiOrdinati = attivitaDao.ordinaBandi(bandi);

			} else {
				LOG.info(prf + "non sono stati trovati bandi");
			}

			return Response.ok(bandiOrdinati).build();

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read BandoVO", e);
			throw new ErroreGestitoException("DaoException while trying to read BandoVO", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public Response getBandoByProgr(@Context HttpServletRequest req, Long progrBandoLineaIntervento)
			throws ErroreGestitoException, UtenteException, RecordNotFoundException {
		String prf = "[AttivitaServiceImpl::getBandoByProgr]";
		LOG.info(prf + " BEGIN");
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		if (userInfo.getIdSoggetto() == null || userInfo.getCodiceRuolo() == null
				|| userInfo.getBeneficiarioSelezionato().getIdBeneficiario() == null) {
			throw new UtenteException("utente non valorizzato correttamente");
		}

		try {

			List<BandoVO> bandi = attivitaDao.getBandoByProgr(userInfo.getCodiceRuolo(), userInfo.getIdSoggetto(),
					userInfo.getBeneficiarioSelezionato().getIdBeneficiario(), progrBandoLineaIntervento);
			if (bandi != null && bandi.size() == 1) {
				return Response.ok().entity(bandi.get(0)).build();
			}
			return Response.ok(null).build();

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read BandoVO", e);
			throw new ErroreGestitoException("DaoException while trying to read BandoVO", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public Response getProgetti(HttpServletRequest req, Long idBeneficiario, Long progrBandoLineaIntervento)
			throws ErroreGestitoException, UtenteException {
		String prf = "[AttivitaServiceImpl::getProgetti]";
		LOG.info(prf + " BEGIN");

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		if (userInfo.getIdUtente() == null || userInfo.getIdIride() == null) {
			throw new UtenteException("utente non valorizzato correttamente");
		}
		if (progrBandoLineaIntervento == null) {
			throw new InvalidParameterException("idBando non valorizzato");
		}
		if (idBeneficiario == null) {
			throw new InvalidParameterException("idBeneficiario non valorizzato");
		}

		List<ProgettoVO> comboProgetti = null;
		try {
			comboProgetti = attivitaDao.getProgettiWithProcedures(progrBandoLineaIntervento, idBeneficiario,
					userInfo.getIdUtente());
		} catch (Exception e) {
			LOG.error("Exception " + e.getMessage());
			throw new ErroreGestitoException("");
		}

		LOG.info(prf + "progetti found: " + (comboProgetti != null ? comboProgetti.size() : 0));
		LOG.info(prf + " END");
		return Response.ok(comboProgetti).build();
	}

	@Override
	public Response getAttivita(HttpServletRequest req, Long idBeneficiario, Long progrBandoLineaIntervento,
			String descrAttivita, Long idProgetto, Long start) throws InvalidParameterException, Exception {
		String prf = "[AttivitaServiceImpl::getAttivita]";
		LOG.info(prf + " BEGIN");

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + " userInfo=" + userInfo);

		Long idUtente = userInfo.getIdUtente();
		String descBreveTipoAnag = userInfo.getCodiceRuolo();
		List<AttivitaVO> attivita = null;
		try {
			attivita = getAttivitaBEN(idUtente, userInfo.getIdIride(), descBreveTipoAnag, idBeneficiario,
					progrBandoLineaIntervento, start, idProgetto, descrAttivita);
		} catch (InvalidParameterException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}

		/*
		 * ArrayList<AttivitaProcesso> elencoAttivitaProcesso = new
		 * ArrayList<AttivitaProcesso>();
		 * 
		 * if (attivita != null && attivita.size() > 0) { Map<String, String> map = new
		 * HashMap<String, String>(); map.put("descrTask", "nome");
		 * map.put("codiceVisualizzato", "progetto"); map.put("flagLock", "stato");
		 * 
		 * for (AttivitaVO att : attivita) { AttivitaProcesso attivitaProcesso =
		 * beanUtil.transform(att, AttivitaProcesso.class, map); //
		 * logger.shallowDump(task, "info");
		 * attivitaProcesso.setId(att.getDescrBreveTask() + (att.getIdProgetto() != null
		 * ? "&" + (att.getIdProgetto().toString()) : "")); //
		 * sb.append("\ntask.getDescrTask(): " + att.getDescrTask() + " //
		 * ,task.getAcronimoProgetto(): " // + att.getAcronimoProgetto() +
		 * " , task.getIdBusiness(): " + // att.getIdBusiness() // +
		 * " , task.getIdNotifica(): " + att.getIdNotifica()); BandoProcesso
		 * bandoProcesso = new BandoProcesso();
		 * bandoProcesso.setNome(att.getNomeBandoLinea());
		 * attivitaProcesso.setBando(bandoProcesso); if (att.getAcronimoProgetto() !=
		 * null) { attivitaProcesso.setProgetto(att.getAcronimoProgetto() + "-" +
		 * attivitaProcesso.getProgetto()); } if (att.getIdBusiness() != null) { if
		 * (att.getDescrTask().toUpperCase().startsWith("VALID")) { //
		 * logger.info("concat the id business for validazione !!! //
		 * "+task.getIdBusiness()); attivitaProcesso.setNome(att.getDescrTask() + " " +
		 * att.getIdBusiness().toString());
		 * attivitaProcesso.setId(att.getDescrBreveTask() + "&" +
		 * (att.getIdProgetto().toString()) + "&" + (att.getIdBusiness().toString()) +
		 * "&" + att.getDescrTask()); } else if
		 * (att.getDescrBreveTask().toUpperCase().equalsIgnoreCase("RICH-EROG-CALC-CAU")
		 * ) { // logger.info("concat the id business for RICH-EROG-CALC-CAU !!! //
		 * "+task.getIdBusiness()); // attivitaProcesso.setNome(task.getDescrTask()+" //
		 * "+task.getIdBusiness().toString()) ;
		 * attivitaProcesso.setId(att.getDescrBreveTask() + "&" +
		 * (att.getIdProgetto().toString()) + "&" + (att.getIdBusiness().toString())); }
		 * }
		 * 
		 * if (attivitaProcesso.getStato() != null &&
		 * attivitaProcesso.getStato().equalsIgnoreCase("S")) {
		 * attivitaProcesso.setStato(Constants.ATTIVITA_IN_USO);
		 * attivitaProcesso.setDenominazioneLock(att.getDenominazioneLock());
		 * LOG.info(prf + "task.getDenominazioneLock(): " + att.getDenominazioneLock() +
		 * " +++++++++++++"); } else {
		 * attivitaProcesso.setStato(Constants.ATTIVITA_DISPONIBILE); }
		 * 
		 * // setto id che verrà usato per la ricerca della notifica e successiva //
		 * cancellazione if (att.getIdNotifica() != null) {
		 * attivitaProcesso.setId(att.getDescrBreveTask() + (att.getIdProgetto() != null
		 * ? "&" + (att.getIdProgetto().toString()) : "") + (att.getIdNotifica() != null
		 * ? "&" + (att.getIdNotifica().toString()) : "")); }
		 * 
		 * } }theModel.setAppDatarisRicercaAttivita(elencoAttivitaProcesso);
		 */

		List<AttivitaVO> attivitaFiltered = new ArrayList<>();
		for (AttivitaVO att : attivita) {
			// se è una notifica, finisce nella tabella apposita
			if (att.getIdNotifica() == null) { // Perchè se rimuovo questo IF vedo notifiche con attivita
				attivitaFiltered.add(att);
			}
		}
		LOG.info(prf + " END");
		return Response.ok(attivitaFiltered).build();
	}

	private List<AttivitaVO> getAttivitaBEN(Long idUtente, String idIride, String descBreveTipoAnag,
			Long idBeneficiario, Long progrBandoLineaIntervento, Long start, Long idProgetto, String descrAttivita)
			throws InvalidParameterException, Exception {
		String prf = "[AttivitaServiceImpl::getAttivitaBEN]";
		LOG.info(prf + " BEGIN");
		if (idUtente == null || idIride == null || descBreveTipoAnag == null || idBeneficiario == null
				|| progrBandoLineaIntervento == null) {
			throw new InvalidParameterException(
					" dati utente o progrBandoLineaIntervento non correttamente valorizzati");
		}
		try {
			List<AttivitaVO> attivita = attivitaDao.getAttivitaBENWithProcedures(progrBandoLineaIntervento,
					idBeneficiario, idUtente, descBreveTipoAnag, start, idProgetto, descrAttivita);
			return attivita;
		} catch (Exception e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
	}

	private List<PbandiTNotificaProcessoVO> findElencoNotifiche(HttpServletRequest req, Long idBandoLineaIntervento,
			Long idProgetto, List<ProgettoVO> elencPrj) throws InvalidParameterException, Exception {
		String prf = "[AttivitaServiceImpl::findElencoNotifiche]";
		LOG.info(prf + " BEGIN");

		if (idBandoLineaIntervento == null) {
			throw new InvalidParameterException("idBandoLinea Intervento non correttamente valorizzati");
		}
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

		long[] idProgetti = null;
		Map<Long, String> mappaPrj = new HashMap<>();

		// se viene postato un progetto, uso quello
		if (idProgetto != null) {
			idProgetti = new long[1];
			idProgetti[0] = idProgetto;

			// devo recuperare la descrizione del progetto
			for (ProgettoVO p : elencPrj) {
				LOG.info("progetto codice==" + p.getId() + ", descr=" + p.getCodiceVisualizzato());

				if (idProgetto.equals(p.getId())) {
					mappaPrj.put(p.getId(), p.getCodiceVisualizzato());
					break;
				}
			}

		} else {
			if (elencPrj != null) {
				idProgetti = new long[elencPrj.size()];
				int i = 0;
				for (ProgettoVO p : elencPrj) {
					LOG.info("progetto codice==" + p.getId() + ", descr=" + p.getCodiceVisualizzato());
					if (!mappaPrj.containsKey(p.getId())) {
						mappaPrj.put(p.getId(), p.getCodiceVisualizzato());
						idProgetti[i] = p.getId();
						i++;
					}
				}
			}
		}

		LOG.info(" idBandoLineaIntervento = " + idBandoLineaIntervento);
		LOG.info(" idSoggettoBeneficiario = " + userInfo.getBeneficiarioSelezionato().getIdBeneficiario());
		LOG.info(" idProgetti = " + idProgetti);
		LOG.info(" codiceRuolo = " + userInfo.getCodiceRuolo());
		List<PbandiTNotificaProcessoVO> listaNotifiche = null;
		try {
			listaNotifiche = attivitaDao.caricaListaNotifiche(idBandoLineaIntervento,
					userInfo.getBeneficiarioSelezionato().getIdBeneficiario(), idProgetti, userInfo.getCodiceRuolo());
		} catch (Exception e) {
			throw e;
		}
		if (listaNotifiche != null) {
			LOG.info(" trovata listaNotifiche di dimensione = " + listaNotifiche.size());
		} else {
			LOG.info(" listaNotifiche NULL");
		}

		LOG.info(prf + " END");
		return listaNotifiche;
	}

	@Override
	public Response getNotifiche(HttpServletRequest req, NotificaRequest notificaRequest)
			throws InvalidParameterException, Exception {
		return Response.ok().entity(findElencoNotifiche(req, notificaRequest.getProgrBandoLineaIntervento(),
				notificaRequest.getIdProgetto(), notificaRequest.getElencoPrj())).build();
	}

	@Override
	public Response countNotifiche(HttpServletRequest req, NotificaRequest notificaRequest)
			throws InvalidParameterException, Exception {
		List<PbandiTNotificaProcessoVO> notifiche = findElencoNotifiche(req,
				notificaRequest.getProgrBandoLineaIntervento(), notificaRequest.getIdProgetto(),
				notificaRequest.getElencoPrj());
		Integer countDaLeggere = 0, countLetteOArchiviate = 0;
		if (notifiche != null) {
			for (PbandiTNotificaProcessoVO n : notifiche) {
				if (n.getStatoNotifica().equals("I")) {// da leggere
					countDaLeggere++;
				}
			}
			countLetteOArchiviate = notifiche.size() - countDaLeggere;
		}
		List<Integer> counts = new ArrayList<>();
		counts.add(countDaLeggere);
		counts.add(countLetteOArchiviate);
		return Response.ok(counts).build();
	}

	private void aggiornaStatoNotifiche(Long idUtente, String identitaIride, long[] elencoIdNotifiche, String stato)
			throws Exception {

		String prf = "[AttivitaServiceImpl::aggiornaStatoNotifiche]";
		LOG.info(prf + " BEGIN");
		Integer ret = 0;
		if (idUtente == null || identitaIride == null || elencoIdNotifiche == null || stato == null) {
			throw new InvalidParameterException("utente, elenco notiifche o stato non correttamente valorizzati");
		}
		// Stati delle notifiche:
		// I : Inviata
		// C : chiusa/archiviata
		// R : read/letta
		try {
			if (StringUtil.contieneSolo(stato.toUpperCase(), "ICR")) {
				// devo aggiornare piu' notifiche
				ret = attivitaDao.aggiornaStatoNotifiche(elencoIdNotifiche, stato.toUpperCase());

			} else {
				LOG.warn(prf + " parametro stato non consentito");
			}

			if (ret == 0)
				LOG.info(prf + " Nessuna notifica aggiornata");
			else
				LOG.info(prf + " Aggiornate " + ret + " notifiche.");
		} catch (Exception e) {
			throw e;
		}

		LOG.info(prf + " END");
	}

	@Override
	public Response cambiaStatoNotifica(HttpServletRequest req, CambiaStatoNotificaRequest cambiaStatoNotificaRequest)
			throws InvalidParameterException, Exception {
		String prf = "[AttivitaServiceImpl::cambiaStatoNotifica]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " idNotifica: " + cambiaStatoNotificaRequest.getIdNotifica() + ", statoNotifica: "
				+ cambiaStatoNotificaRequest.getStatoNotifica());
		boolean success = false;
		try {
			UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

			if (cambiaStatoNotificaRequest.getIdNotifica() != null) {

				// leggo i dati della notifica
				Long idUtente = userInfo.getIdUtente();
				String identitaDigitale = userInfo.getIdIride();

				long[] arrayNotifica = new long[1];
				arrayNotifica[0] = cambiaStatoNotificaRequest.getIdNotifica();

				aggiornaStatoNotifiche(idUtente, identitaDigitale, arrayNotifica,
						cambiaStatoNotificaRequest.getStatoNotifica());

				success = true;
			}

		} catch (InvalidParameterException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}

		LOG.info(prf + " END");
		return Response.ok().entity(success).build();
	}

	@Override
	public Response cambiaStatoNotificheSelezionate(HttpServletRequest req,
			CambiaStatoNotificaRequest cambiaStatoNotificaRequest) throws InvalidParameterException, Exception {
		String prf = "[AttivitaServiceImpl::cambiaStatoNotificheSelezionate]";
		LOG.info(prf + " BEGIN");

		boolean success = false;
		Integer esito = null;
		// Se modalitaArchivio ON cambio stato da C a "statoNotifica"
		// Se modalitaArchivio OFF cambio stato da I o R a "statoNotifica"

		try {

			if (cambiaStatoNotificaRequest.getIdNotificheArray() != null
					&& cambiaStatoNotificaRequest.getIdNotificheArray().size() > 0) {

				UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

				Long idUtente = userInfo.getIdUtente();
				String identitaDigitale = userInfo.getIdIride();

				aggiornaStatoNotifiche(idUtente, identitaDigitale,
						ArrayUtils.toPrimitive(cambiaStatoNotificaRequest.getIdNotificheArray().toArray(new Long[0])),
						cambiaStatoNotificaRequest.getStatoNotifica());
				success = true;
			}

		} catch (InvalidParameterException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}

		LOG.info(prf + " END");
		return Response.ok().entity(success).build();
	}

	@Override
	public Response startAttivita(HttpServletRequest req, String descBreveTask, Long idProgetto)
			throws InvalidParameterException, Exception {

		String prf = "[AttivitaServiceImpl::startAttivita]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " descBreveTask=" + descBreveTask + ", idProgetto=" + idProgetto);

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + " userInfo=" + userInfo);

		if (userInfo == null) {
			throw new InvalidParameterException("utente in sessione non correttamente valorizzato");
		}

		if (userInfo.getIdUtente() == null || userInfo.getIdIride() == null || descBreveTask == null
				|| idProgetto == null) {
			throw new InvalidParameterException(" parametri in input non correttamente valorizzati");
		}

		String ret = null;
		try {

			ret = attivitaDao.startAttivita(userInfo.getIdUtente(), userInfo.getIdIride(), descBreveTask, idProgetto);

		} catch (InvalidParameterException e) {
			LOG.error(prf + "InvalidParameterException :" + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception :" + e.getMessage());
			throw e;
		}

		LOG.info(prf + " END");
		return Response.ok().entity(ret).build();
	}

	@Override
	public Response trovaConsensoInvioComunicazione(HttpServletRequest req)
			throws InvalidParameterException, Exception {

		String prf = "[AttivitaServiceImpl::trovaConsensoInvioComunicazione] ";
		LOG.info(prf + " BEGIN");

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

		if (userInfo == null) {
			throw new InvalidParameterException("utente in sessione non correttamente valorizzato");
		}
		LOG.info(prf + "IdUtente = " + userInfo.getIdUtente() + "; IdIride = " + userInfo.getIdIride());

		if (userInfo.getIdUtente() == null || userInfo.getIdIride() == null) {
			throw new InvalidParameterException(" parametri in input non correttamente valorizzati");
		}

		ConsensoInformatoDTO ret = null;
		try {

			ret = attivitaDao.trovaConsensoInvioComunicazione(userInfo.getIdUtente());

		} catch (InvalidParameterException e) {
			LOG.error(prf + "InvalidParameterException :" + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception :" + e.getMessage());
			throw e;
		}

		LOG.info(prf + " END");
		return Response.ok().entity(ret).build();
	}

	@Override
	public Response salvaConsensoInvioComunicazione(HttpServletRequest req, String emailConsenso,
			String flagConsensoMail) throws InvalidParameterException, Exception {

		String prf = "[AttivitaServiceImpl::salvaConsensoInvioComunicazione]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " emailConsenso=" + emailConsenso + ", flagConsensoMail=" + flagConsensoMail);

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + " userInfo=" + userInfo);

		if (userInfo == null) {
			throw new InvalidParameterException("utente in sessione non correttamente valorizzato");
		}

		if (userInfo.getIdUtente() == null || userInfo.getIdIride() == null) {
			throw new InvalidParameterException(" parametri in input non correttamente valorizzati");
		}

		Integer ret = null;
		try {

			ret = attivitaDao.salvaConsensoInvioComunicazione(userInfo.getIdUtente(), emailConsenso, flagConsensoMail);

		} catch (InvalidParameterException e) {
			LOG.error(prf + "InvalidParameterException :" + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception :" + e.getMessage());
			throw e;
		}

		LOG.info(prf + " END");
		return Response.ok().entity(ret).build();
	}

	public Response ricercaAttivitaPrecedente(Long idBeneficiario, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		String prf = "[AttivitaServiceImpl::ricercaAttivitaPrecedente]";
		LOG.info(prf + " BEGIN");

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + " userInfo=" + userInfo);

		Long idUtente = userInfo.getIdUtente();
		String descBreveTipoAnag = userInfo.getCodiceRuolo();

		LOG.info(prf + " idUtente = " + idUtente + "; descBreveTipoAnag = " + descBreveTipoAnag + "; idBeneficiario = "
				+ idBeneficiario);

		if (idUtente == null || StringUtil.isEmpty(descBreveTipoAnag)) {
			throw new UtenteException("utente non valorizzato correttamente");
		}
		if (idBeneficiario == null) {
			throw new InvalidParameterException("idBeneficiario non valorizzato");
		}

		RicercaAttivitaPrecedenteDTO ricercaAttivitaPrecedenteDTO = null;
		try {

			ricercaAttivitaPrecedenteDTO = attivitaDao.ricercaAttivitaPrecedente(idUtente, descBreveTipoAnag,
					idBeneficiario);

		} catch (InvalidParameterException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}

		LOG.info(prf + " END");
		return Response.ok(ricercaAttivitaPrecedenteDTO).build();
	}

}
