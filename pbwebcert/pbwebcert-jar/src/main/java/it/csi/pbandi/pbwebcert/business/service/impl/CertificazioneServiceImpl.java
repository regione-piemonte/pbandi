/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.business.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.filestorage.exceptions.IncorrectUploadPathException;
import it.csi.pbandi.pbservizit.business.intf.ErrorConstants;
import it.csi.pbandi.pbservizit.dto.profilazione.Beneficiario;
import it.csi.pbandi.pbservizit.dto.profilazione.Ruolo;
import it.csi.pbandi.pbservizit.dto.profilazione.UserInfoDTO;
import it.csi.pbandi.pbservizit.exception.DaoException;
import it.csi.pbandi.pbservizit.exception.ErroreGestitoException;
import it.csi.pbandi.pbservizit.exception.RecordNotFoundException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.integration.dao.ProfilazioneDAO;
import it.csi.pbandi.pbservizit.integration.vo.TipoAnagraficaVO;
import it.csi.pbandi.pbservizit.security.BeneficiarioSec;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbservizit.util.UseCaseConstants;
import it.csi.pbandi.pbwebcert.business.service.CertificazioneService;
import it.csi.pbandi.pbwebcert.dto.ProgettoNuovaCertificazioneDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.BandoLineaDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.BeneficiarioDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.CodiceDescrizione;
import it.csi.pbandi.pbwebcert.dto.certificazione.DocumentoCertificazioneDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.DocumentoDescrizione;
import it.csi.pbandi.pbwebcert.dto.certificazione.EsitoOperazioni;
import it.csi.pbandi.pbwebcert.dto.certificazione.FiltroRicercaDocumentoDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.LineaDiInterventoDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.ProgettoCertificazioneIntermediaFinaleDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.ProgettoDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.PropostaCertificazioneDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.PropostaProgettoDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.StatoPropostaDTO;
import it.csi.pbandi.pbwebcert.dto.rilevazionicontrolli.EsitoGenerazioneReportDTO;
import it.csi.pbandi.pbwebcert.exception.CertificazioneException;
import it.csi.pbandi.pbwebcert.integration.dao.CertificazioneDao;
import it.csi.pbandi.pbwebcert.integration.request.AccodaPropostaRequest;
import it.csi.pbandi.pbwebcert.integration.request.AggiornaDatiIntermediaFinaleRequest;
import it.csi.pbandi.pbwebcert.integration.request.AggiornaImportoNettoRequest;
import it.csi.pbandi.pbwebcert.integration.request.AggiornaStatoRequest;
import it.csi.pbandi.pbwebcert.integration.request.AmmettiESospendiProgettiRequest;
import it.csi.pbandi.pbwebcert.integration.request.CancellaAllegatiRequest;
import it.csi.pbandi.pbwebcert.integration.request.CreaIntermediaFinaleRequest;
import it.csi.pbandi.pbwebcert.integration.request.CreaPropostaRequest;
import it.csi.pbandi.pbwebcert.integration.request.GestisciPropostaIntermediaFinaleRequest;
import it.csi.pbandi.pbwebcert.integration.request.GestisciPropostaRequest;
import it.csi.pbandi.pbwebcert.integration.request.InviaReportRequest;
import it.csi.pbandi.pbwebcert.integration.request.LanciaPropostaRequest;
import it.csi.pbandi.pbwebcert.integration.request.ModificaAllegatiRequest;
import it.csi.pbandi.pbwebcert.integration.request.PropostaCertifLineaRequest;
import it.csi.pbandi.pbwebcert.integration.request.PropostaCertificazioneAllegatiRequest;
import it.csi.pbandi.pbwebcert.integration.request.ProposteCertificazioneRequest;
import it.csi.pbandi.pbwebcert.integration.vo.ProgettoNuovaCertificazione;
import it.csi.pbandi.pbwebcert.util.BeanUtil;
import it.csi.pbandi.pbwebcert.util.Constants;
import it.csi.pbandi.pbwebcert.util.DateUtil;
import it.csi.pbandi.pbwebcert.util.StringUtil;

@Service
public class CertificazioneServiceImpl implements CertificazioneService {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	ProfilazioneDAO profilazioneDao;

	@Autowired
	CertificazioneDao certificazioneDao;

	@Autowired
	protected BeanUtil beanUtil;

	@Override
	public Response getCertificazioneHome(Long idSg, Long idSgBen, Long idU, String role, HttpServletRequest req)
			throws UtenteException, ErroreGestitoException {

		String prf = "[CertificazioneServiceImpl::getCertificazioneHome]";
		LOG.info(prf + " BEGIN");

		HttpSession session = req.getSession();
		UserInfoSec userInfo2 = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + "userInfo=" + userInfo2);

		if (userInfo2 == null) {
			LOG.warn(prf + "userInfo in sessione null");
			throw new UtenteException("Utente in sessione non valido");
		}

		// TODO : verifico la valorizzazione dei parametri idSg, idSgBen, idU, role,

		LOG.info(prf + " idSg=" + idSg + ", idSgBen=" + idSgBen + ", idU=" + idU + " role=" + role);

		try {
			UserInfoDTO user = inizializza(idSg, idSgBen, idU, role, userInfo2);
			userInfo2 = aggiornaConUserInfoDTO(user, userInfo2);
			userInfo2.setCodiceRuolo(role);
			List<TipoAnagraficaVO> listaTA = profilazioneDao.findTipiAnagrafica(BigDecimal.valueOf(idSg));
			for (TipoAnagraficaVO tipoAnagraficaVO : listaTA) {
				if (StringUtils.equals(role, tipoAnagraficaVO.getDescBreveTipoAnagrafica())) {
					userInfo2.setRuolo(tipoAnagraficaVO.getDescTipoAnagrafica());
					break;
				}
			}

			if (idSgBen.compareTo(0L) == 0) {
				// beneficiario non selezionato
				LOG.debug("Beneficiario non selezionato");
			} else {
				Beneficiario[] y = user.getBeneficiari();
				for (Beneficiario beneficiario : user.getBeneficiari()) {
					if (Long.compare(idSgBen, beneficiario.getId()) == 0) {
						BeneficiarioSec beneficiarioSelezionato = new BeneficiarioSec();
						beneficiarioSelezionato.setCodiceFiscale(beneficiario.getCodiceFiscale());
						beneficiarioSelezionato.setDenominazione(beneficiario.getDescrizione());
						beneficiarioSelezionato.setIdBeneficiario(idSgBen);
						userInfo2.setBeneficiarioSelezionato(beneficiarioSelezionato);
						break;
					}
				}
			}

			LOG.info(prf + " userInfo2=" + userInfo2);

		} catch (Exception e) {
			LOG.error(prf + " Exception " + e.getMessage());
			e.printStackTrace();
			throw new ErroreGestitoException("Errore gestito");
		}

		LOG.info(prf + " END");
		return Response.ok().entity(userInfo2).build();
	}

	private UserInfoDTO inizializza(Long idSg, Long idSgBen, Long idU, String role, UserInfoSec userInfo2)
			throws UtenteException, DaoException {
		String prf = "[CertificazioneServiceImpl::inizializza]";
		LOG.info(prf + " BEGIN");

		UserInfoDTO userInfoDTO = profilazioneDao.getInfoUtente(userInfo2.getCodFisc(), userInfo2.getNome(),
				userInfo2.getCognome());
		LOG.info(prf + " userInfoDTO=" + userInfoDTO);

		// verifico che utente sia presente sul DB
		if (userInfoDTO == null) {
			LOG.warn(prf + "Utente[" + idU + " - " + userInfo2.getCodFisc() + "] non censito in PBANDI");
			UtenteException ue = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
			throw ue;
		}

		// verifico che parametro idU corrisponda all'idUtente in sessione
		if (Long.compare(idU, userInfoDTO.getIdUtente().longValue()) == 0) {
			LOG.debug(prf + " idUtente in sessione (" + userInfoDTO.getIdUtente() + ") corretto");
		} else {
			LOG.warn(prf + " idUtente in sessione (" + userInfoDTO.getIdUtente() + ") diverso da quello in request ("
					+ idU + ")");
			UtenteException ue = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
			throw ue;
		}
		// verifico che parametro idS corrisponda all'idSoggetto dell'utente in sessione
		if (Long.compare(idSg, userInfoDTO.getIdSoggetto().longValue()) == 0) {
			LOG.debug(prf + " idSoggetto in sessione (" + userInfoDTO.getIdSoggetto() + ") corretto");
		} else {
			LOG.warn(prf + " idSoggetto in sessione (" + userInfoDTO.getIdSoggetto()
					+ ") diverso da quello in request (" + idSg + ")");
			UtenteException ue = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
			throw ue;
		}

		// verifico che parametro role corrisponda ad almeno un ruolo dell'utente in
		// sessione
		Ruolo[] ruoli = userInfoDTO.getRuoli();
		boolean checkRole = false;
		for (Ruolo ruolo : ruoli) {
			if (StringUtils.equals(ruolo.getDescrizioneBreve(), role)) {
				checkRole = true;
			}
		}
		if (!checkRole) {
			// parametro "role" passato in request non corrisponde a nessun ruolo utente
			LOG.warn(prf + "parametro \"role\" passato in request non corrisponde a nessun ruolo utente");
			UtenteException ue = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
			throw ue;
		}

		// verifico l'utente in sessione possa accedere a questa funzionalita
		if (!profilazioneDao.hasPermesso(idSg, idU, role, UseCaseConstants.UC_MENUCERT)) { // TODO ----> verificare cosa
																							// passare, da dove arriva
																							// MENUCERT ??

			LOG.warn(prf + " idSoggetto in sessione (" + idSg + ") non ha i permessi per accedere alla certificazione");
			UtenteException ue = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
			throw ue;
		}

		// verifico i beneficiari
		if (idSgBen.compareTo(0L) == 0) {
			// beneficiario non selezionato
			LOG.debug("Beneficiario non selezionato");
		} else {
			BeneficiarioSec beneficiarioSec = profilazioneDao.findBeneficiarioByIdSoggettoBen(idU,
					userInfo2.getCodFisc(), role, idSgBen);

			if (beneficiarioSec != null && Long.compare(idSgBen, beneficiarioSec.getIdBeneficiario()) == 0) {
				// beneficiario valido
				LOG.debug("Beneficiario idSgBen=" + idSgBen + " trovato");

				Beneficiario b = new Beneficiario();
				b.setId(beneficiarioSec.getIdBeneficiario());
				b.setCodiceFiscale(beneficiarioSec.getCodiceFiscale());
				b.setDescrizione(beneficiarioSec.getDenominazione());

				Beneficiario[] arrBenef = new Beneficiario[1];
				arrBenef[0] = b;

				userInfoDTO.setBeneficiari(arrBenef);
			} else {
				LOG.warn(prf + " id soggetto beneficiario (" + idSgBen + ") non e' associato all'utente " + idU);
				UtenteException ue = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
				throw ue;
			}
		}

		LOG.info(prf + " END");
		return userInfoDTO;
	}

	private UserInfoSec aggiornaConUserInfoDTO(UserInfoDTO user, UserInfoSec userInfo) {
		String prf = "[CertificazioneServiceImpl::aggiornaConUserInfoDTO]";
		LOG.info(prf + " BEGIN");
		UserInfoSec u = userInfo;

		if (user.getBeneficiari() != null && user.getBeneficiari().length > 0) {
			Beneficiario ben = user.getBeneficiari()[0];
			BeneficiarioSec b = new BeneficiarioSec();
			b.setCodiceFiscale(ben.getCodiceFiscale());
			b.setDenominazione(ben.getCodiceFiscale());
			b.setIdBeneficiario(new Long(ben.getId()));
			userInfo.setBeneficiarioSelezionato(b);
		}

		u.setIdSoggetto(user.getIdSoggetto());
		u.setIdUtente(user.getIdUtente());
		u.setIsIncaricato(user.getIsIncaricato());

		if (user.getRuoli() != null && user.getRuoli().length > 0) {
			List<Ruolo> y = Arrays.asList(user.getRuoli());
			u.setRuoli(new ArrayList<Ruolo>(y));
		}
		LOG.info(prf + " END");
		return u;
	}

	/****************************
	 * SERVIZI DI CHECKLIST E DICHIARAZIONE FINALE
	 ******************************/
	/******************************************************************************************************/
	public Response getProposteCertificazione(HttpServletRequest req,
			ProposteCertificazioneRequest proposteCertificazioneRequest) throws Exception {
		String prf = "[CertificazioneServiceImpl::getProposteCertificazione]";
		LOG.info(prf + " BEGIN");
		PropostaCertificazioneDTO[] proposte = null;
		try {
			UserInfoSec userInfo = proposteCertificazioneRequest.getUserInfo();
			List<String> statiProposte = proposteCertificazioneRequest.getStati();
			if (userInfo == null) {
				throw new UtenteException("utente non valorizzato");
			}

			proposte = certificazioneDao.findProposteCertificazione(null, null, statiProposte);
		} catch (RecordNotFoundException e) {
			LOG.error(prf + e.getMessage());
			throw new RecordNotFoundException("Nessun proposte trovate");
		}
		LOG.info(prf + " END");
		return Response.ok(proposte).build();

	}

	@Override
	public Response getProposteAggiornamentoStatoCertificazione(HttpServletRequest req,
			ProposteCertificazioneRequest proposteCertificazioneRequest) throws Exception {
		String prf = "[CertificazioneServiceImpl::getProposteAggiornamentoStatoCertificazione]";
		LOG.debug(prf + " BEGIN");
		PropostaCertificazioneDTO[] proposte = null;
		UserInfoSec userInfo = proposteCertificazioneRequest.getUserInfo();
		List<String> statiProposte = proposteCertificazioneRequest.getStati();
		if (userInfo == null) {
			throw new UtenteException("utente non valorizzato");
		}
		try {
			proposte = certificazioneDao.findProposteCertificazione(null, null, statiProposte);
//			LOG.debug(prf + "++++++++++++++++Proposte trovate++++++++++++++++");
//			for (PropostaCertificazioneDTO p : proposte) {
//				LOG.debug("idProposta: " + p.getIdPropostaCertificaz() + " dtOraCreazione: " + p.getDtOraCreazione() + "idLinea: " + p.getIdLineaDiIntervento());
//			}

			ArrayList<PropostaCertificazioneDTO> proposteTrovate = new ArrayList<PropostaCertificazioneDTO>();
			// Ottengo l'ultima proposta approvata per la nuova programmazione
			PropostaCertificazioneDTO lastPropostaNuovaProgrammazioneApprovata = ottieniUltimaPropostaApprovataPorFesr1420(proposte);
			LOG.debug(prf + " lastPropostaNuovaProgrammazioneApprovata: " + lastPropostaNuovaProgrammazioneApprovata.getDescBreveStatoPropostaCert());
			/*
			 * NUOVO VINCOLO: inibire la modifica di una approvata se per quella stessa
			 * approvata esiste una proposta aperta successiva.
			 */
			boolean flagPorFesr1420ApertaTrovata = false;
			LOG.debug(prf + "BEFORE: flagPorFesr1420ApertaTrovata: " + flagPorFesr1420ApertaTrovata);
			for (PropostaCertificazioneDTO proposta : proposte) {
				Long idLineaInterventoProposta = proposta.getIdLineaDiIntervento();
//
//				LOG.debug(prf + " PROPOSTA ID = " + proposta.getIdPropostaCertificaz() + 
//						", STATO PROPOSTA = " + proposta.getDescBreveStatoPropostaCert() + 
//						" ID LINEA INTERVENTO: " + proposta.getIdLineaDiIntervento() +
//						"IS AFTER : " +isAfter(proposta, lastPropostaNuovaProgrammazioneApprovata));

				if (proposta.getDescBreveStatoPropostaCert().equals(Constants.DESC_BREVE_STATO_PROPOSTA_INTERMEDIA_FINALE)
						|| proposta.getDescBreveStatoPropostaCert().equals(Constants.DESC_BREVE_STATO_PROPOSTA_CHIUSURA_CONTI)) {
					proposteTrovate.add(proposta);
					proposta.setIsApprovataeNuovaProgrammazione(false);

				} else if (proposta.getDescBreveStatoPropostaCert()
						.equals(Constants.DESC_BREVE_STATO_PROPOSTA_APERTA)) {
//					LOG.debug("PROPOSTA APERTA : " + proposta.getIdPropostaCertificaz());
					proposteTrovate.add(proposta);
					proposta.setIsApprovataeNuovaProgrammazione(false);
//					LOG.debug(prf + "BEFORE: flagPorFesr1420ApertaTrovata: " + flagPorFesr1420ApertaTrovata+ 
//							"proposta.dtOraCreazione :" + proposta.getDtOraCreazione()+
//							"lastPropostaNuovaProgrammazioneApprovata.dtOraCreazione " +
//							lastPropostaNuovaProgrammazioneApprovata.getDtOraCreazione() + 
//							" isAfter: " + isAfter(proposta, lastPropostaNuovaProgrammazioneApprovata));
					
					if (idLineaInterventoProposta == Constants.ID_LINEA_INTERVENTO_POR_FESR_2014_2020) {
						if (lastPropostaNuovaProgrammazioneApprovata != null
								&& isAfter(proposta, lastPropostaNuovaProgrammazioneApprovata)) {
							flagPorFesr1420ApertaTrovata = true;
						}
					}
				} 
			}
			//JIRA 2985
			if (lastPropostaNuovaProgrammazioneApprovata != null) {
//				LOG.debug(prf + "DENTRO IF: AGGIUNGO L'ULTIMA PROPOSTA APPROVATA");
				lastPropostaNuovaProgrammazioneApprovata.setEsistePropostaApertaSuccessiva(flagPorFesr1420ApertaTrovata);
				lastPropostaNuovaProgrammazioneApprovata.setIsApprovataeNuovaProgrammazione(Boolean.TRUE);
				proposteTrovate.add(lastPropostaNuovaProgrammazioneApprovata);
			}
			
			LOG.debug(prf + " END");
			return Response.ok(proposteTrovate).build();

		} catch (Exception e) {
			throw e;
		}
	}

	private PropostaCertificazioneDTO ottieniUltimaPropostaApprovataPorFesr1420(PropostaCertificazioneDTO[] proposte)
			throws Exception {
		String prf = "[CertificazioneServiceImpl::ottieniUltimaPropostaApprovataPorFesr1420]";
		LOG.info(prf + " BEGIN");

		PropostaCertificazioneDTO lastPropostaPorFesr1420Approvata = null;
		// Trovare l'ultima proposta approvata ed POR FESR 14 20
		for (PropostaCertificazioneDTO proposta : proposte) {
			Long idLineaIntervento = proposta.getIdLineaDiIntervento();
//			LOG.info(prf + " idLineaIntervento: " + idLineaIntervento);
			if (idLineaIntervento != null && idLineaIntervento == Constants.ID_LINEA_INTERVENTO_POR_FESR_2014_2020
					&& proposta.getDescBreveStatoPropostaCert().equals(Constants.DESC_BREVE_STATO_PROPOSTA_APPROVATA)) {
				LOG.debug(prf + "idLineaIntervento != null " + proposta.getIdPropostaCertificaz());
				if (lastPropostaPorFesr1420Approvata == null) {
					lastPropostaPorFesr1420Approvata = proposta;
				} else {
					if (isAfter(proposta, lastPropostaPorFesr1420Approvata)) {
						lastPropostaPorFesr1420Approvata = proposta;
						LOG.debug(prf + "isAfter() - lastPropostaApprovataTrovata=" + proposta.getIdPropostaCertificaz());
					}
				}
			}
		}
		LOG.info(prf + " lastPropostaPorFesr1420Approvata: " + lastPropostaPorFesr1420Approvata);
		LOG.info(prf + " END");
		return lastPropostaPorFesr1420Approvata;
	}

	@Override
	public Response getLineeDiInterventoFromIdLinee(HttpServletRequest req,
			PropostaCertifLineaRequest propostaCertifLineaRequest) throws UtenteException, Exception {
		String prf = "[CertificazioneServiceImpl::getLineeDiInterventoFromIdLinee]";
		LOG.info(prf + " BEGIN");
		LineaDiInterventoDTO[] lineeDiIntervento = null;
		UserInfoSec userInfo = propostaCertifLineaRequest.getUserInfo();
		if (userInfo == null) {
			throw new UtenteException("utente non valorizzato");
		}

		try {
			lineeDiIntervento = certificazioneDao
					.getLineeDiInterventoFromIdLinee(propostaCertifLineaRequest.getIdLineeIntervento());

		} catch (RecordNotFoundException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}

		LOG.info(prf + " END");
		return Response.ok(lineeDiIntervento).build();
	}

	@Override
	public Response getPropostaCertificazione(HttpServletRequest req, BigDecimal idProposta)
			throws UtenteException, Exception {
		String prf = "[CertificazioneServiceImpl::getPropostaCertificazione]";
		LOG.info(prf + " BEGIN");
		PropostaCertificazioneDTO proposta = null;
		try {
			if (idProposta != null) {
				proposta = certificazioneDao.findPropostaCertificazione(idProposta);
			}

		} catch (RecordNotFoundException e) {
			LOG.error(prf + e.getMessage());
		}
		LOG.info(prf + " END");
		return Response.ok(proposta).build();

	}

	@Override
	public Response getAllegatiPropostaCertificazione(HttpServletRequest req,
			PropostaCertificazioneAllegatiRequest allegatiPropostaRequest) throws UtenteException, Exception {
		String prf = "[CertificazioneServiceImpl::getAllegatiPropostaCertificazione]";
		LOG.info(prf + " BEGIN");
		BigDecimal idProposta = allegatiPropostaRequest.getIdPropostaCertificazione();
		List<String> codiciTipoDocumento = allegatiPropostaRequest.getCodiciTipoDocumento();
		DocumentoCertificazioneDTO[] documenti = null;
		List<DocumentoCertificazioneDTO> documentiAllegati = new ArrayList<>();
		try {
			documenti = certificazioneDao.findAllegatiPropostaCertificazione(idProposta, codiciTipoDocumento);
			if (documenti.length > 0) {
				for (DocumentoCertificazioneDTO documento : documenti) {
					documentiAllegati.add(documento);
				}
			}
		} catch (RecordNotFoundException e) {
			LOG.error(prf + e.getMessage());
		}

		LOG.info(prf + " END");
		return Response.ok(documentiAllegati).build();
	}

	@Override
	public Response cancellaAllegati(HttpServletRequest req, CancellaAllegatiRequest cancellaAllegatiRequest)
			throws Exception {
		String prf = "[CertificazioneServiceImpl::cancellaAllegati]";
		LOG.info(prf + " BEGIN");
		// Validazione input
		String msgErrore = "";
		List<String> idDocumentiSelezionati = cancellaAllegatiRequest.getIdDocumentiSelezionati();
		if (idDocumentiSelezionati.size() == 0 || idDocumentiSelezionati == null) {
			msgErrore = "Http body mancato : json {idDocumentiSelezionati}";
			return inviaErroreBadRequest(msgErrore);
		}
		try {
			idDocumentiSelezionati = cancellaAllegatiRequest.getIdDocumentiSelezionati();
			EsitoOperazioni esito = new EsitoOperazioni();
			esito = certificazioneDao.cancellaAllegati(idDocumentiSelezionati);
			LOG.info(prf + " END");
			return Response.ok(esito).build();
		} catch (RecordNotFoundException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}

	}

	@Override
	public Response allegaFileProposta(HttpServletRequest req, Long idProposta, MultipartFormDataInput input)
			throws Exception {
		String prf = "[CertificazioneServiceImpl::allegaFileProposta]";
		LOG.info(prf + " BEGIN");
		String msgErrore = "";
		try {
			// Validazione input
			if (idProposta == null) {
				msgErrore = "Parametro mancato : ? [idProposta, idStatoNuovo, idUtente]";
				return inviaErroreBadRequest(msgErrore);
			}
			if (input.getFormDataMap().isEmpty()) {
				msgErrore = "Http body mancato : formData {allegato}";
				return inviaErroreBadRequest(msgErrore);
			}

			// controlla se l'idUtente non è null
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			if (idUtente == null) {
				return Response.serverError().entity("idUtente non valido").build();
			}

			// leggi il form-data nel body
			Map<String, List<InputPart>> map = input.getFormDataMap();
			List<InputPart> file = map.get("file");
			List<InputPart> inputTipDocumento = map.get("tipoDocumento");
			List<InputPart> inputIdProgetti = map.get("idProgetto");

			List<String> tipiDocumenti = new ArrayList<>();
			List<Long> idProgetti = new ArrayList<>();

			// controlla input - file e tipoDocumento
			if (file.size() == 0) {
				msgErrore = "Http body mancato : formData: {allegato : [file] }";
				return inviaErroreBadRequest(msgErrore);
			}
			if (inputTipDocumento.size() == 0) {
				msgErrore = "Http body mancato : formData: {allegato : [tipoDocumento] }";
				return inviaErroreBadRequest(msgErrore);
			}

			// controlla l'idProgetto non è null - idProgetto - non obligattorio
			for (InputPart inputPart : inputTipDocumento) {
				LOG.info(prf + " tipoDocumento: " + inputPart.getBodyAsString());
				tipiDocumenti.add(inputPart.getBodyAsString());
			}
			Long idProgetto = null;
			if (inputIdProgetti != null) {
				for (InputPart inputPart : inputIdProgetti) {
					LOG.info(prf + " idProgetto: " + inputPart.getBodyAsString());
					idProgetti.add(Long.valueOf(inputPart.getBodyAsString()));
				}
				idProgetto = idProgetti.get(0);
			}

			String tipoDocumento = tipiDocumenti.get(0);
			EsitoOperazioni esito = null;
			esito = certificazioneDao.allegaFileProposta(idUtente, idProposta, idProgetto, file, tipoDocumento);
			LOG.info(prf + " END");
			return Response.ok(esito).build();
		} catch (IncorrectUploadPathException ex) {
			LOG.error(prf + " " + ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			LOG.error(prf + " " + ex.getMessage());
			throw ex;
		}

	}

	@Override
	public Response modificaAllegati(HttpServletRequest req, Long idProposta, ModificaAllegatiRequest modficaRequest) {
		String prf = "[CertificazioneServiceImpl::modificaAllegati]";
		LOG.info(prf + " BEGIN");
		EsitoOperazioni esito = new EsitoOperazioni();
		try {
			List<DocumentoDescrizione> documentiDescrizioni = modficaRequest.getDocumentiDescrizioni();
			esito = certificazioneDao.modificaAllegati(documentiDescrizioni);
			LOG.info(prf + " END");
			return Response.ok(esito).build();
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}

	}

	private boolean isAfter(PropostaCertificazioneDTO first, PropostaCertificazioneDTO second) throws Exception {
		return DateUtil.after(first.getDtOraCreazione(), second.getDtOraCreazione());
	}

	/******************************************************************************************************/

	@Override
	public Response getAttivitaLineaIntervento(HttpServletRequest req, Long idLineaIntervento)
			throws UtenteException, Exception {
		String prf = "[CertificazioneServiceImpl::getAttivitaLineaIntervento]";
		LOG.info(prf + " BEGIN");
		if (idLineaIntervento == null) {
			EsitoOperazioni esito = new EsitoOperazioni();
			esito.setEsito(false);
			esito.setMsg("Parametro : ?[idLineaIntervento] mancato");
			return Response.status(Response.Status.BAD_REQUEST).entity(esito).type(MediaType.APPLICATION_JSON).build();
		}
		List<LineaDiInterventoDTO> attivitaLineaInterventoDTOs = new ArrayList<>();
		try {
			attivitaLineaInterventoDTOs = certificazioneDao.findAttivitaLineaIntervento(idLineaIntervento);
			LOG.info(prf + " END");
			return Response.ok(attivitaLineaInterventoDTOs).build();
		} catch (RecordNotFoundException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
	}

	@Override
	public Response getAllProgetti(HttpServletRequest req, Long idProposta, Long idLineaIntervento, Long idBeneficiario,
			String nomeBandoLinea, String denominazioneBeneficiario) throws UtenteException, Exception {
		String prf = "[CertificazioneServiceImpl::getAllProgetti]";
		LOG.info(prf + " BEGIN");
		List<ProgettoDTO> progetti = new ArrayList<ProgettoDTO>();
		try {
			String msgErrore = "Selezionare una delle due coppie di parametri [idLineaIntervento, idBeneficiario] o [nomeBandoLinea, denominazioneBeneficiario]";
			if (idLineaIntervento != null && idBeneficiario != null && nomeBandoLinea != null
					&& denominazioneBeneficiario != null) {
				return inviaErroreBadRequest(msgErrore);
			}
			if (idLineaIntervento != null && idBeneficiario != null && nomeBandoLinea != null
					&& denominazioneBeneficiario == null) {
				return inviaErroreBadRequest(msgErrore);
			}
			if (idLineaIntervento != null && idBeneficiario != null && nomeBandoLinea == null
					&& denominazioneBeneficiario != null) {
				return inviaErroreBadRequest(msgErrore);
			}
			//if (idLineaIntervento == null && idBeneficiario != null && nomeBandoLinea != null
			//		&& denominazioneBeneficiario != null) {
			//	return inviaErroreBadRequest(msgErrore);
			//}
			if (idLineaIntervento != null && idBeneficiario == null && nomeBandoLinea != null
					&& denominazioneBeneficiario != null) {
				return inviaErroreBadRequest(msgErrore);
			}

			if (idLineaIntervento == null && idBeneficiario == null && nomeBandoLinea == null
					&& denominazioneBeneficiario == null) {
				LOG.info(prf + " CASO 1");
				progetti = certificazioneDao.findAllProgetti(null, null, null);
			} else if (idLineaIntervento == null && idBeneficiario == null && nomeBandoLinea != null
					&& denominazioneBeneficiario == null) {
				progetti = certificazioneDao.findAllProgetti(null, null, null);
			} else if (idLineaIntervento != null && idBeneficiario == null && nomeBandoLinea == null
					&& denominazioneBeneficiario == null) {
				LOG.info(prf + " CASO 2");
				progetti = certificazioneDao.findAllProgetti(idLineaIntervento, idProposta, null);
			} else if (idLineaIntervento != null && idBeneficiario != null && nomeBandoLinea == null
					&& denominazioneBeneficiario == null) {
				LOG.info(prf + " CASO 3");
				progetti = certificazioneDao.findAllProgetti(idLineaIntervento, idProposta, idBeneficiario);
			} else {
				LOG.info(prf + " CASO 4");
				progetti = certificazioneDao.findAllProgettiDaProposta(idProposta, nomeBandoLinea,
						denominazioneBeneficiario);
			}

		} catch (RecordNotFoundException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
		LOG.info(prf + " END");
		return Response.ok(progetti).build();
	}

	@Override
	public Response getAllBeneficiari(HttpServletRequest req, Long idProposta, Long idLineaIntervento,
			String nomeBandoLinea) throws UtenteException, Exception {
		String prf = "[CertificazioneServiceImpl::getProgettiLineaIntervento]";
		LOG.info(prf + " BEGIN");
		List<BeneficiarioDTO> beneficiari = new ArrayList<>();
		try {
			if (idLineaIntervento != null && nomeBandoLinea != null) {
				String msgErrore = "Selezionare solo uno dei due parametri [idLineaIntervento, nomeBandoLinea]";
				return inviaErroreBadRequest(msgErrore);
			} else if (idLineaIntervento != null && nomeBandoLinea == null) {
				beneficiari = certificazioneDao.findAllBeneficiari(idProposta, idLineaIntervento);
			} else {
				beneficiari = certificazioneDao.findAllBeneficiariDaProposta(idProposta, nomeBandoLinea);
			}

		} catch (RecordNotFoundException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
		LOG.info(prf + " END");
		return Response.ok(beneficiari).build();
	}

	@Override
	public Response getProgettiProposta(HttpServletRequest req, Long idProposta, Long idProgetto,
			Long idLineaIntervento, Long idBeneficiario) throws UtenteException, Exception {
		String prf = "[CertificazioneServiceImpl::getProgettiProposta]";
		LOG.info(prf + " BEGIN");
		List<PropostaCertificazioneDTO> progetti = new ArrayList<>();
		try {
			progetti = certificazioneDao.findProgettiProposta(idProgetto, idProposta, idLineaIntervento,
					idBeneficiario);
			LOG.info(prf + " END");
			return Response.ok(progetti).build();
		} catch (RecordNotFoundException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
	}

	@Override
	public Response getDettaglioProposta(HttpServletRequest req, Long idProposta) throws UtenteException, Exception {
		String prf = "[CertificazioneServiceImpl::getDettaglioProposta]";
		LOG.info(prf + " BEGIN");
		PropostaCertificazioneDTO progetti = null;
		try {
			progetti = certificazioneDao.findDettaglioProposta(idProposta);
			LOG.info(prf + " END");
			return Response.ok(progetti).build();
		} catch (RecordNotFoundException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
	}

	/******************************************
	 * SERVIZI DI GESTIONE PROPOSTE
	 *******************************/
	/******************************************************************************************************/

	@Override
	public Response creaIntermediaFinale(HttpServletRequest req,
			CreaIntermediaFinaleRequest creaIntermediaFinaleRequest) throws Exception {
		String prf = "[CertificazioneServiceImpl::creaIntermediaFinale]";
		LOG.info(prf + " BEGIN");
		String msgErrore = "";
		Long idUtente = creaIntermediaFinaleRequest.getIdUtente();
		if (idUtente == null) {
			msgErrore = "Http body mancato : json {idUtente}";
			return inviaErroreBadRequest(msgErrore);
		}
		EsitoOperazioni esito = new EsitoOperazioni();
		try {
			esito = certificazioneDao.creaIntermediaFinale(idUtente);
			LOG.info(prf + " END");
			return Response.ok(esito).build();
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public Response getAllBandoLineaIntermediaFinale(HttpServletRequest req, Long idProposta)
			throws CertificazioneException, Exception {
		String prf = "[CertificazioneServiceImpl::getAllBandoLineaIntermediaFinale]";
		LOG.info(prf + " BEGIN");
		List<BandoLineaDTO> bandiLinea = new ArrayList<>();
		try {
			String msgErrore = "Parametro mancato : ?[idProposta]";
			if (idProposta == null) {
				return inviaErroreBadRequest(msgErrore);
			}
			bandiLinea = certificazioneDao.findBandoLineaIntermediaFinale(idProposta);
		} catch (RecordNotFoundException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
		LOG.info(prf + " END");
		return Response.ok(bandiLinea).build();
	}

	@Override
	public Response getAllBeneficiariIntermediaFinale(HttpServletRequest req, Long idProposta, String nomeBandoLinea)
			throws CertificazioneException, Exception {
		String prf = "[CertificazioneServiceImpl::getAllBeneficiariIntermediaFinale]";
		LOG.info(prf + " BEGIN");
		List<BeneficiarioDTO> beneficiari = new ArrayList<>();
		try {
			String msgErrore = "Parametro mancato : ?[idProposta]";
			if (idProposta == null) {
				return inviaErroreBadRequest(msgErrore);
			}

			LOG.info(prf + " CASO 1");
			beneficiari = certificazioneDao.findBeneficiariIntermediaFinale(idProposta, nomeBandoLinea);

		} catch (RecordNotFoundException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
		LOG.info(prf + " END");
		return Response.ok(beneficiari).build();
	}

	@Override
	public Response getAllProgettiIntermediaFinale(HttpServletRequest req, Long idProposta, String nomeBandoLinea,
			String denominazioneBeneficiario) throws CertificazioneException, Exception {
		String prf = "[CertificazioneServiceImpl::getAllProgettiIntermediaFinale]";
		LOG.info(prf + " BEGIN");
		List<ProgettoDTO> progetti = new ArrayList<>();
		try {
			String msgErrore = "Parametro mancato : ?[idProposta]";
			if (idProposta == null) {
				return inviaErroreBadRequest(msgErrore);
			}

			LOG.info(prf + " CASO 1");
			progetti = certificazioneDao.findProgettiIntermediaFinale(idProposta, nomeBandoLinea,
					denominazioneBeneficiario);

		} catch (RecordNotFoundException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
		LOG.info(prf + " END");
		return Response.ok(progetti).build();
	}

	@Override
	public Response getGestisciPropostaIntermediaFinale(HttpServletRequest req, Long idProposta,
			GestisciPropostaIntermediaFinaleRequest gestisciPropostaRequest) throws CertificazioneException, Exception {
		String prf = "[CertificazioneServiceImpl::getGestisciPropostaIntermediaFinale]";
		LOG.info(prf + " BEGIN");
		try {
			List<ProgettoCertificazioneIntermediaFinaleDTO> progettiIF = new ArrayList<>();
			if (!StringUtil.isEmpty(beanUtil.transform(idProposta, String.class))) {
				if (gestisciPropostaRequest != null) {
					String nomeBandoLinea = gestisciPropostaRequest.getNomeBandoLinea();
					String codiceProgetto = gestisciPropostaRequest.getCodiceProgetto();
					String denominazioneBeneficiario = gestisciPropostaRequest.getDenominazioneBeneficiario();
					Long idLineaIntervento = gestisciPropostaRequest.getIdLineaDiIntervento();

					nomeBandoLinea = StringUtils.isEmpty(nomeBandoLinea) ? null : nomeBandoLinea;
					denominazioneBeneficiario = StringUtils.isEmpty(denominazioneBeneficiario) ? null
							: denominazioneBeneficiario;
					codiceProgetto = StringUtils.isEmpty(codiceProgetto) ? null : codiceProgetto;

					progettiIF = certificazioneDao.getGestisciPropostaIntermediaFinale(idProposta, nomeBandoLinea,
							codiceProgetto, denominazioneBeneficiario, idLineaIntervento);

				}
			}
			LOG.info(prf + " END");
			return Response.ok(progettiIF).build();
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public Response modificaProgettiIntermediaFinale(HttpServletRequest req,
			AggiornaDatiIntermediaFinaleRequest aggiornaRequest) throws UtenteException, Exception {
		String prf = "[CertificazioneServiceImpl::modificaProgettiIntermediaFinale]";
		LOG.info(prf + " BEGIN");
		EsitoOperazioni esito = new EsitoOperazioni();
		try {
			for (ProgettoCertificazioneIntermediaFinaleDTO p : aggiornaRequest.getElencoProgettiIntermediaFinale()) {
				String msgErrore = "Progetto: " + p.getCodiceProgetto()
						+ ", Parametro mancato : ?[idDettPropCertAnnual]";
				if (p.getIdDettPropCertAnnual() == null) {
					return inviaErroreBadRequest(msgErrore);
				}
			}
			esito = certificazioneDao
					.modificaProgettiIntermediaFinale(aggiornaRequest.getElencoProgettiIntermediaFinale());
			LOG.info(prf + " END");
			return Response.ok(esito).build();
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public Response getStatiSelezionabili(HttpServletRequest req) throws CertificazioneException, Exception {
		String prf = "[CertificazioneServiceImpl::findStatiSelezionabili]";
		LOG.info(prf + " BEGIN");
		try {
			List<StatoPropostaDTO> stati = new ArrayList<>();
			stati = certificazioneDao.getStatiSelezionabili();
			LOG.info(prf + " END");
			return Response.ok(stati).build();
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public Response aggiornaStatoProposta(HttpServletRequest req, AggiornaStatoRequest aggiornaStatoRequest)
			throws UtenteException, Exception {
		String prf = "[CertificazioneServiceImpl::aggiornaStatoProposta]";
		LOG.info(prf + " BEGIN");
		// Validazione input
		if (aggiornaStatoRequest.getIdProposta() == null
				|| aggiornaStatoRequest.getIdStatoNuovo() == null && aggiornaStatoRequest.getIdUtente() == null) {
			String msgErrore = "Http body mancato o non valido: {idProposta, idStatoNuovo, idUtente}";
			return inviaErroreBadRequest(msgErrore);
		}
		EsitoOperazioni esito = new EsitoOperazioni();
		try {
			Integer esitoAggiornaStato = certificazioneDao.aggiornaStatoProposta(aggiornaStatoRequest.getIdUtente(),
					aggiornaStatoRequest.getIdProposta(), aggiornaStatoRequest.getIdStatoNuovo());
			if (esitoAggiornaStato == null) {
				esito.setEsito(true);
				esito.setMsg("Salvataggio avvenuto con successo.");
			} else if (esitoAggiornaStato.intValue() == 0) {
				esito.setEsito(true);
				esito.setMsg(
						"L'elaborazione dei progetti da sottoporre ad alert (anticipi non coperti da spesa validata) e' stata completata ed e' stata inviata la mail di notifica.");
			} else if (esitoAggiornaStato.intValue() == -1) {
				esito.setEsito(false);
				esito.setMsg(
						"ATTENZIONE! Non e' stato possibile portare a termine l'elaborazione dei progetti da sottoporre ad alert (anticipi non coperti da spesa validata). Contattare l'Assistenza.");
			} else if (esitoAggiornaStato.intValue() == -2) {
				esito.setEsito(false);
				esito.setMsg(
						"L'elaborazione dei progetti da sottoporre ad alert (anticipi non coperti da spesa validata) e' stata completata. Non sono stati rilevati progetti da segnalare.");
			} else if (esitoAggiornaStato.intValue() == -3) {
				esito.setEsito(false);
				esito.setMsg(
						"ATTENZIONE! Non e' stato possibile portare a termine l'invio della mail di notifica dei progetti da sottoporre ad alert (anticipi non coperti da spesa validata). Contattare l'Assistenza.");
			}
			LOG.info(prf + " END");
			return Response.ok(esito).build();
		} catch (CertificazioneException e) {
			LOG.error(prf + e.getMessage());
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public Response getGestisciProposta(HttpServletRequest req, Long idProposta,
			GestisciPropostaRequest gestisciPropostaRequest) throws CertificazioneException, Exception {
		String prf = "[CertificazioneServiceImpl::getGestisciProposta]";
		LOG.info(prf + " BEGIN");
		try {
			List<ProgettoNuovaCertificazioneDTO> progettiNCDTO = new ArrayList<>();
			if (!StringUtil.isEmpty(beanUtil.transform(idProposta, String.class))) {
				if (gestisciPropostaRequest.getFiltro() != null) {
					String nomeBandoLinea = null;
					String codiceProgetto = null;
					String denominazioneBeneficiario = null;

					nomeBandoLinea = gestisciPropostaRequest.getFiltro().getNomeBandoLinea();
					codiceProgetto = gestisciPropostaRequest.getFiltro().getCodiceProgetto();
					denominazioneBeneficiario = gestisciPropostaRequest.getFiltro().getDenominazioneBeneficiario();
					boolean progettiModificati = gestisciPropostaRequest.getFiltro().getProgettiModificati();
					progettiNCDTO = certificazioneDao.getGestisciProposta(idProposta, nomeBandoLinea, codiceProgetto,
							denominazioneBeneficiario, progettiModificati);

				}
			}
			LOG.info(prf + " END");
			return Response.ok(progettiNCDTO).build();
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw new Exception(e.getMessage());
		}

	}

	@Override
	public Response getBandoLineaDaProposta(HttpServletRequest req, Long idProposta)
			throws CertificazioneException, Exception {
		String prf = "[CertificazioneServiceImpl::findGestisciProposta]";
		LOG.info(prf + " BEGIN");
		try {
			List<BandoLineaDTO> bandiLinea = certificazioneDao.getBandoLinea(idProposta);
			LOG.info(prf + " END");
			return Response.ok(bandiLinea).build();
		} catch (RecordNotFoundException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
	}

	@Override
	public Response aggiornaImportoNetto(HttpServletRequest req,
			AggiornaImportoNettoRequest aggiornaImportoNettoRequest) throws UtenteException, Exception {
		String prf = "[CertificazioneServiceImpl::aggiornaImportoNetto]";
		LOG.info(prf + " BEGIN");
		EsitoOperazioni esito = new EsitoOperazioni();

		if (aggiornaImportoNettoRequest.getProgetti().size() == 0
				|| aggiornaImportoNettoRequest.getProgetti() == null) {
			String msgErrore = "Http body mancato o non valido: {progetti}";
			return inviaErroreBadRequest(msgErrore);
		}
		try {

			// controlla se l'idUtente non è null
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			if (idUtente == null) {
				return Response.serverError().entity("idUtente non valido").build();
			}
			int error = 0;
			for (ProgettoNuovaCertificazione progetto : aggiornaImportoNettoRequest.getProgetti()) {
				Long idProposta = progetto.getIdPropostaCertificazione();
				Long idDettProposta = progetto.getIdDettPropostaCertif();
				Double nuovoImportoNetto = progetto.getNuovoImportoCertificazioneNetto();
				String nota = progetto.getNota();
				Long idProgetto = progetto.getIdProgetto();
				if (idProposta == null || idDettProposta == null || idProgetto == null) {
					return inviaErroreBadRequest("Bad request: uno o più parametri mancanti!");
				}
				if (nota == null)
					nota = "";
				esito = certificazioneDao.aggiornaImportoNetto(idUtente, idProposta, idDettProposta, nuovoImportoNetto,
						nota, idProgetto);
				if (!esito.getEsito()) {
					error++;
				}
				LOG.info(prf + " END");
			}
			if (error > 0) {
				esito.setEsito(false);
				esito.setMsg("Errore durante l'aggiornamento Certificazione Netto!!");
			}
			return Response.ok(esito).build();
		} catch (RecordNotFoundException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
	}

	@Override
	public Response checkProceduraAggiornamentoTerminata(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[CertificazioneServiceImpl::checkProceduraAggiornamentoTerminata]";
		LOG.info(prf + " BEGIN");
		try {
			Boolean esito = certificazioneDao.checkProceduraAggiornamentoTerminata();
			LOG.info(prf + " END");
			return Response.ok(esito).build();
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}

	}

	@Override
	public Response chiusuraContiPropostaIntermediaFinale(HttpServletRequest req, Long idProposta)
			throws UtenteException, Exception {
		String prf = "[CertificazioneServiceImpl::chiusuraContiPropostaIntermediaFinale]";
		LOG.info(prf + " BEGIN");
		EsitoOperazioni esito = new EsitoOperazioni();
		try {
			// controlla se l'idUtente non è null
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			if (idUtente == null) {
				return inviaErroreUnauthorized("idUtente non valido");
			}
			if (idProposta == null) {
				return inviaErroreNotFound("Parametro mancato : ? [idProposta]");
			}
			esito = certificazioneDao.chiusuraContiPropostaIntermediaFinale(idUtente, idProposta);
			LOG.info(prf + " END");
			return Response.ok(esito).build();
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
	}

	@Override
	public Response inviaReportPostGestione(HttpServletRequest req, InviaReportRequest inviaReportRequest)
			throws UtenteException, Exception {
		String prf = "[CertificazioneServiceImpl::inviaReportPostGestione]";
		LOG.info(prf + " BEGIN");
		EsitoOperazioni esito = new EsitoOperazioni();

		try {
			// controlla se l'idUtente non è null
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			if (idUtente == null) {
				inviaErroreUnauthorized("idUtente non valido");
			}
			PropostaCertificazioneDTO dto = inviaReportRequest.getPropostaCertificazione();
			if (dto == null) {
				return inviaErroreNotFound("Http body mancato : ? {propostaCertificazioneDTO}");
			}
			// Chiamata del metodo asincrono per l'invio del report
			Long idLineaDiIntervento = getIdLineaDiInterverntoDaProposta(idUtente, dto.getIdPropostaCertificaz());
			LOG.info(prf + " idLineaDiIntervento = "+idLineaDiIntervento);

			esito = certificazioneDao.inviaReportPostGestione(idUtente, dto, new Long[] { idLineaDiIntervento });
			LOG.info(prf + " END");
			return Response.ok(esito).build();
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
	}

	private Long getIdLineaDiInterverntoDaProposta(Long idUtente, Long idProposta) {
		String prf = "[CertificazioneServiceImpl::getIdLineaDiInterverntoDaProposta]";
		LOG.info(prf + " BEGIN");
		if (idProposta == null) {
			return null;
		}
		try {
			CodiceDescrizione idLineaDiInterventoCD = findLineaInterventoFromProposta(idUtente, idProposta);
			if (idLineaDiInterventoCD != null) {
				Long idLineaDiIntervento = new Long(idLineaDiInterventoCD.getCodice());
				LOG.info(prf + " END");
				return idLineaDiIntervento;
			}
		} catch (Exception e) {
			LOG.error("Errore : " + e.getMessage());
			throw e;
		}
		LOG.info(prf + " END");
		return null;
	}

	private CodiceDescrizione findLineaInterventoFromProposta(Long idUtente, Long idProposta) {
		String prf = "[CertificazioneServiceImpl::findLineaInterventoFromProposta]";
		LOG.info(prf + " BEGIN");
		LineaDiInterventoDTO lineaIntervento = certificazioneDao.findLineaDiInterventoFromProposta(idUtente,
				idProposta);
		HashMap<String, String> trsMap = new HashMap<String, String>();
		trsMap.put("idLineaDiIntervento", "codice");
		trsMap.put("descLinea", "descrizione");
		LOG.info(prf + " END");
		return beanUtil.transform(lineaIntervento, CodiceDescrizione.class, trsMap);

	}

	@Override
	public Response aggiornaDatiIntermediaFinale(HttpServletRequest req, Long idProposta)
			throws UtenteException, Exception {
		String prf = "[CertificazioneServiceImpl::aggiornaPropostaIntermediaFinale]";
		LOG.info(prf + " BEGIN");
		try {
			// controlla se l'idUtente non è null
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			if (idUtente == null) {
				return inviaErroreUnauthorized("idUtente non valido");
			}

			// List<ProgettoCertificazioneIntermediaFinaleDTO> progetti =
			// aggiornaDatiRequest.getProgetti();

			certificazioneDao.aggiornaDatiIntermediaFinale(idUtente, idProposta);
			LOG.info(prf + " END");
			return Response.ok().build();
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}

	}

	/******************************************
	 * SERVIZI DI CAMPIONAMENTO
	 *********************************/
	/******************************************************************************************************/
	@Override
	public Response getAnnoContabile(HttpServletRequest req) throws Exception {
		String prf = "[CertificazioneServiceImpl::getComboAnnoContabile]";
		LOG.info(prf + " BEGIN");
		List<CodiceDescrizione> elencoAnnoContabile = new ArrayList<>();
		try {
			elencoAnnoContabile = certificazioneDao.getAnnoContabile();
			LOG.info(prf + " END");
			return Response.ok(elencoAnnoContabile).build();
		} catch (RecordNotFoundException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
	}

	@Override
	public Response getLineeInterventoNormative(HttpServletRequest req, Boolean isConsultazione) {
		String prf = "[CertificazioneServiceImpl::getLineeInterventoNormative]";
		LOG.info(prf + " BEGIN");

		List<CodiceDescrizione> elencoNormative = new ArrayList<>();
		try {
			elencoNormative = certificazioneDao.getLineeInterventoNormative(isConsultazione,
					Constants.FLAG_CAMPIONIONAMENTO_CERTIFICAZIONE);
			LOG.info(prf + " END");
			return Response.ok(elencoNormative).build();
		} catch (RecordNotFoundException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
	}

	@Override
	public Response eseguiEstrazioneCampionamento(HttpServletRequest req, Long idNormativa) throws Exception {
		String prf = "[CertificazioneServiceImpl::eseguiEstrazioneCampionamento]";
		LOG.info(prf + " BEGIN");
		String msgErrore = "";
		if (idNormativa == null) {
			msgErrore = "Parametro mancato :  [{idNormativa}]";
			inviaErroreBadRequest(msgErrore);
		}
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			if (idUtente == null) {
				return inviaErroreUnauthorized("id Utente non valido");
			}
			EsitoGenerazioneReportDTO esito = certificazioneDao.eseguiEstrazioneCampionamento(idNormativa, idUtente);
			LOG.info(prf + " END");
			return Response.ok(esito).build();
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
	}

	@Override
	public Response getElencoReportCampionamento(HttpServletRequest req, Long idNormativa, Long idAnnoContabile)
			throws UtenteException, Exception {
		String prf = "[CertificazioneServiceImpl::getElencoReportCampionamento]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			if (idUtente == null) {
				return Response.serverError().entity("idUtente non valido").build();
			}
			List<it.csi.pbandi.pbwebcert.dto.rilevazionicontrolli.ReportCampionamentoDTO> elencoReport = certificazioneDao
					.getElencoReportCampionamento(idNormativa, idAnnoContabile);
			LOG.info(prf + " END");
			return Response.ok(elencoReport).build();
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
	}

	@Override
	public Response getContenutoDocumentoById(HttpServletRequest req, Long idDocumentoIndex) throws Exception {
		String prf = "[CertificazioneServiceImpl::getContenutoDocumentoById]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			if (idUtente == null) {
				return Response.serverError().entity("idUtente non valido").build();
			}
			EsitoGenerazioneReportDTO esito = certificazioneDao.getContenutoDocumentoById(idDocumentoIndex);
			LOG.info(prf + " END");
			return Response.ok(esito.getReport()).build();
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
	}

	/******************************************
	 * SERVIZI DI RICERCA DOCUMENTI
	 *******************************/
	/******************************************************************************************************/
	@Override
	public Response ricercaDocumenti(HttpServletRequest req, FiltroRicercaDocumentoDTO filtroRicerca) throws Exception {
		String prf = "[CertificazioneServiceImpl::ricercaDocumenti]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			if (idUtente == null) {
				return Response.serverError().entity("idUtente non valido").build();
			}
			List<DocumentoCertificazioneDTO> documenti = certificazioneDao.ricercaDocumenti(filtroRicerca);
			LOG.info(prf + " END");
			return Response.ok(documenti).build();
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
	}

	/*********************************
	 * SERVIZI DI CREA PROPOSTA CERTIFICAZIONE
	 *****************************/
	/******************************************************************************************************/

	@Override
	public Response creaAnteprimaPropostaCertificazione(HttpServletRequest req, CreaPropostaRequest creaPropostaRequest)
			throws Exception {
		String prf = "[CertificazioneServiceImpl::creaAnteprimaPropostaCertificazione]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			if (idUtente == null) {
				return Response.serverError().entity("idUtente non valido").build();
			}
			PropostaCertificazioneDTO dto = creaPropostaRequest.getPropostaCertificazione();
			String idLinea = beanUtil
					.transform(creaPropostaRequest.getPropostaCertificazione().getIdLineaDiIntervento(), String.class);
			Long[] idLineeDiIntervento = ottieniIdLineeDiIntervento(idLinea,
					creaPropostaRequest.getLineeDiInterventoDisponibili());

			EsitoOperazioni esito = certificazioneDao.creaAnteprimaPropostaCertificazione(idUtente, dto,
					idLineeDiIntervento);
			LOG.info(prf + " END");
			return Response.ok(esito).build();
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
	}

	private Long[] ottieniIdLineeDiIntervento(String idLineaSelezionata,
			List<CodiceDescrizione> lineeDiInterventoDisponibili) {

		LOG.debug("BEGIN, idLineaSelezionata=" + idLineaSelezionata);

		Long[] idLineeDiIntervento;
		Long idLineaRealmenteSelezionata = ottieniIdLineaDiIntervento(idLineaSelezionata);
		LOG.debug("idLineaRealmenteSelezionata=" + idLineaRealmenteSelezionata);

		if (idLineaRealmenteSelezionata == null) {
			List<String> linee = new ArrayList<String>();
			for (CodiceDescrizione linea : lineeDiInterventoDisponibili) {
				if (!Constants.PROPOSTA_CERTIFICAZIONE_TUTTE_LE_LINEE_DI_INTERVENTO.equals(linea.getCodice())) {
					linee.add(linea.getCodice());
				}
			}
			idLineeDiIntervento = beanUtil.transform(linee, Long.class);
		} else {
			idLineeDiIntervento = new Long[] { idLineaRealmenteSelezionata };
		}
		LOG.debug("END");
		return idLineeDiIntervento;
	}

	private Long ottieniIdLineaDiIntervento(String idLineaSelezionata) {
		return Constants.PROPOSTA_CERTIFICAZIONE_TUTTE_LE_LINEE_DI_INTERVENTO.equals(idLineaSelezionata) ? null
				: convertStringToLongIfNotNull(idLineaSelezionata);
	}

	private Long convertStringToLongIfNotNull(String idLineaSelezionata) {
		return StringUtil.isEmpty(idLineaSelezionata) ? null : beanUtil.transform(idLineaSelezionata, Long.class);
	}

	@Override
	public Response getAllBandoLineaPerAnteprima(HttpServletRequest req, Long idProposta) throws Exception {
		String prf = "[CertificazioneServiceImpl::getAllBandoLineaPerAnteprima]";
		LOG.info(prf + " BEGIN");
		List<BandoLineaDTO> bandiLinea = new ArrayList<>();
		try {
			String msgErrore = "Parametro mancato : ?[idProposta]";
			if (idProposta == null) {
				return inviaErroreBadRequest(msgErrore);
			}
			bandiLinea = certificazioneDao.findBandoLineaPerAnteprima(idProposta);
		} catch (RecordNotFoundException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
		LOG.info(prf + " END");
		return Response.ok(bandiLinea).build();
	}

	@Override
	public Response getAllBeneficiariPerAnteprima(HttpServletRequest req, Long idProposta,
			Long progrBandoLineaIntervento) throws Exception {
		String prf = "[CertificazioneServiceImpl::getAllBeneficiariPerAnteprima]";
		LOG.info(prf + " BEGIN");
		List<BeneficiarioDTO> beneficiari = new ArrayList<>();
		try {
			String msgErrore = "Parametro mancato : ?[idProposta]";
			if (idProposta == null) {
				return inviaErroreBadRequest(msgErrore);
			}

			LOG.info(prf + " CASO 1");
			beneficiari = certificazioneDao.findBeneficiariPerAnteprima(idProposta, progrBandoLineaIntervento);

		} catch (RecordNotFoundException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
		LOG.info(prf + " END");
		return Response.ok(beneficiari).build();
	}

	@Override
	public Response getAntePrimaProposta(HttpServletRequest req, Long idProposta, Long progrBandoLineaIntervento,
			Long idBeneficiario, Long idProgetto, Long idLineaDiIntervento) throws Exception {
		String prf = "[CertificazioneServiceImpl::getAntePrimaProposta]";
		LOG.info(prf + " BEGIN");
		List<PropostaProgettoDTO> proposteProgetto = new ArrayList<>();
		try {
			String msgErrore = "Parametro mancato : ?[idProposta]";
			if (idProposta == null) {
				return inviaErroreBadRequest(msgErrore);
			}

			LOG.info(prf + " CASO 1");
			proposteProgetto = certificazioneDao.findAntePrimaProposta(idProposta, progrBandoLineaIntervento,
					idBeneficiario, idProgetto, idLineaDiIntervento);

		} catch (RecordNotFoundException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
		LOG.info(prf + " END");
		return Response.ok(proposteProgetto).build();
	}

	@Override
	public Response getAllProgettiPerAnteprima(HttpServletRequest req, Long idProposta, Long progrBandoLineaIntervento,
			Long idBeneficiario) throws Exception {
		String prf = "[CertificazioneServiceImpl::getAllProgettiPerAnteprima]";
		LOG.info(prf + " BEGIN");
		List<ProgettoDTO> progetti = new ArrayList<>();
		try {
			String msgErrore = "Parametro mancato : ?[idProposta]";
			if (idProposta == null) {
				return inviaErroreBadRequest(msgErrore);
			}

			progetti = certificazioneDao.findProgettiPerAnteprima(idProposta, progrBandoLineaIntervento,
					idBeneficiario);

		} catch (RecordNotFoundException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
		LOG.info(prf + " END");
		return Response.ok(progetti).build();
	}

	@Override
	public Response getLineeDiInterventoDisponibili(HttpServletRequest req) throws Exception {
		String prf = "[CertificazioneServiceImpl::getLineeDiInterventoDisponibili]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idSoggetto = userInfo.getIdSoggetto();
			String codiceRuolo = userInfo.getCodiceRuolo();
			if (idSoggetto == null || codiceRuolo == null) {
				return inviaErroreUnauthorized("Utente non autorizzato");
			}
			List<LineaDiInterventoDTO> lineeDiIntervento = certificazioneDao
					.findLineeDiInterventoDisponibili(idSoggetto, codiceRuolo);
			return Response.ok(lineeDiIntervento).build();
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public Response sospendiProgettiProposta(HttpServletRequest req, AmmettiESospendiProgettiRequest sospendiRequest)
			throws Exception {
		String prf = "[CertificazioneServiceImpl::sospendiProgettiProposta]";
		LOG.info(prf + " BEGIN");
		try {
			Long[] idsPreviewDettPropCer = sospendiRequest.getIdsPreviewDettPropCer();
			if (idsPreviewDettPropCer.length == 0 || idsPreviewDettPropCer == null) {
				inviaRispostaVuota("Nessuna risorsa è stata modificata!");
			}
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			if (idUtente == null)
				return inviaErroreUnauthorized("Utente non autorizzato!");
			EsitoOperazioni esito;
			esito = certificazioneDao.sospendiProgettiProposta(idUtente, idsPreviewDettPropCer);
			
			if(!esito.getEsito())
				return Response.ok(esito).build();
			
			esito = certificazioneDao.sospendiProgettiPropostaRev(idUtente, idsPreviewDettPropCer);
			
			return Response.ok(esito).build();
			
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response ammettiProgettiProposta(HttpServletRequest req, AmmettiESospendiProgettiRequest ammettiRequest)
			throws Exception {
		String prf = "[CertificazioneServiceImpl::ammettiProgettiProposta]";
		LOG.info(prf + " BEGIN");
		try {
			Long[] idsPreviewDettPropCer = ammettiRequest.getIdsPreviewDettPropCer();
			if (idsPreviewDettPropCer.length == 0 || idsPreviewDettPropCer == null) {
				inviaRispostaVuota("Nessuna risorsa è stata modificata!");
			}
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			if (idUtente == null)
				inviaErroreUnauthorized("Utente non autorizzato!");
			EsitoOperazioni esito;
			esito = certificazioneDao.ammettiProgettiProposta(idUtente, idsPreviewDettPropCer);

			if(!esito.getEsito())
				return Response.ok(esito).build();
			
			esito = certificazioneDao.ammettiProgettiPropostaRev(idUtente, idsPreviewDettPropCer);
			
			return Response.ok(esito).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response accodaPropostaCertificazione(HttpServletRequest req, AccodaPropostaRequest accodaRequest)
			throws Exception {
		String prf = "[CertificazioneServiceImpl::accodaPropostaCertificazione]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			if (idUtente == null)
				return inviaErroreUnauthorized("Utente non autorizzato!");
			PropostaCertificazioneDTO proposta = accodaRequest.getProposta();
			if (proposta == null) {
				return inviaErroreBadRequest("Http body mancato : {proposta}");
			}
			EsitoOperazioni esito = certificazioneDao.accodaPropostaCertificazione(idUtente, proposta);
			return Response.ok(esito).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response lanciaCreazioneProposta(HttpServletRequest req, LanciaPropostaRequest lanciaRequest)
			throws Exception {
		String prf = "[CertificazioneServiceImpl::lanciaCreazioneProposta]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			if (idUtente == null)
				return inviaErroreUnauthorized("Utente non autorizzato!");
			PropostaCertificazioneDTO proposta = lanciaRequest.getProposta();
			List<CodiceDescrizione> lineeDiInterventoDisponibili = lanciaRequest.getLineeDiInterventoDisponibili();
			if (proposta == null) {
				return inviaErroreBadRequest("Http body mancato : {proposta}");
			}

			final Long[] idLineeDiIntervento = ottieniIdLineeDiIntervento(
					beanUtil.transform(proposta.getIdLineaDiIntervento(), String.class), lineeDiInterventoDisponibili);
			
			EsitoOperazioni esito = certificazioneDao.lanciaCreazioneProposta(idUtente, proposta, idLineeDiIntervento);
			return Response.ok(esito).build();
		} catch (Exception e) {
			throw e;
		}
	}

	private Response inviaErroreBadRequest(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.BAD_REQUEST).entity(esito).type(MediaType.APPLICATION_JSON).build();
	}

	private Response inviaErroreNotFound(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.NOT_FOUND).entity(esito).type(MediaType.APPLICATION_JSON).build();
	}

	private Response inviaErroreUnauthorized(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.UNAUTHORIZED).entity(esito).type(MediaType.APPLICATION_JSON).build();
	}

	private Response inviaRispostaVuota(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.OK).entity(esito).type(MediaType.APPLICATION_JSON).build();
	}
	
	@Override
	public Response propostePerRicercaDocumenti(HttpServletRequest req, ProposteCertificazioneRequest proposteCertificazioneRequest)
			throws Exception {
		String prf = "[CertificazioneServiceImpl::propostePerRicercaDocumenti]";
		LOG.info(prf + " BEGIN");
		try {
			
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			if (idUtente == null)
				return inviaErroreUnauthorized("Utente non autorizzato!");
			
			List<String> statiProposte = proposteCertificazioneRequest.getStati();
			
			PropostaCertificazioneDTO[] proposte = certificazioneDao.propostePerRicercaDocumenti(userInfo.getIdUtente(), userInfo.getIdIride(), statiProposte);
			
			return Response.ok(proposte).build();
			
		} catch (Exception e) {
			throw e;
		}
	}

}
