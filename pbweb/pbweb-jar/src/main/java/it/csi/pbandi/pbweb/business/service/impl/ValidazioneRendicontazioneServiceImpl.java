/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.business.service.impl;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.dto.EsitoOperazioni;
import it.csi.pbandi.pbservizit.integration.dao.impl.IterAutorizzativiDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionebackoffice.GestioneBackofficeBusinessImpl;
import it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweb.business.service.ValidazioneRendicontazioneService;
import it.csi.pbandi.pbweb.dto.EsitoDTO;
import it.csi.pbandi.pbweb.dto.archivioFile.EsitoLeggiFile;
import it.csi.pbandi.pbweb.dto.custom.EsitoValidazioneRendicontazionePiuGreen;
import it.csi.pbandi.pbweb.integration.dao.CheckListDAO;
import it.csi.pbandi.pbweb.integration.dao.ValidazioneRendicontazioneDAO;
import it.csi.pbandi.pbweb.integration.dao.request.CercaDocumentiDiSpesaValidazioneRequest;
import it.csi.pbandi.pbweb.integration.dao.request.OperazioneMassivaRequest;
import it.csi.pbandi.pbweb.integration.dao.request.ProseguiChiudiValidazioneRequest;
import it.csi.pbandi.pbweb.integration.dao.request.RichiediIntegrazioneRequest;
import it.csi.pbandi.pbweb.integration.dao.request.SalvaCheckListValidazioneDocumentaleHtmlRequest;
import it.csi.pbandi.pbweb.integration.dao.request.ValidaMensilitaRequest;
import it.csi.pbandi.pbweb.integration.dao.request.ValidaParzialmenteDocumentoRequest;
import it.csi.pbandi.pbweb.integration.dao.request.VerificaOperazioneMassivaRequest;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.DocumentoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.profilazione.ProfilazioneBusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklist.EsitoOperazioneDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.ChecklistHtmlDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.EsitoSalvaModuloCheckListHtmlDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.FileDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.IstanzaAttivitaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.ValidazioneRendicontazioneDTO;
import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbweb.pbandiutil.common.util.json.JSONArray;
import it.csi.pbandi.pbweb.util.Constants;
import it.csi.pbandi.pbweb.util.ErrorMessages;

@Service
public class ValidazioneRendicontazioneServiceImpl implements ValidazioneRendicontazioneService {

	private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	ValidazioneRendicontazioneDAO validazioneRendicontazioneDAO;

	@Autowired
	CheckListServiceImpl checkListService;

	@Autowired
	private CheckListDAO checkListDAO;

	@Autowired
	protected BeanUtil beanUtil;

	@Autowired
	private DocumentoManager documentoManager;

	@Autowired
	private GestioneBackofficeBusinessImpl gestioneBackofficeBusinessImpl;

	@Autowired
	private IterAutorizzativiDAOImpl iterAutorizzativiDAOImpl;

	@Autowired
	private ProfilazioneBusinessImpl profilazioneBusinessImpl;

	/*
	 * @Override public Response getSvilConstant (HttpServletRequest req) throws
	 * InvalidParameterException, Exception { UserInfoSec userInfo = (UserInfoSec)
	 * req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR); return
	 * Response.ok().entity(validazioneRendicontazioneDAO.getSvilConstant().build())
	 * ; }
	 */

	@Override
	public Response inizializzaValidazione(Long idDichiarazioneDiSpesa, Long idProgetto, Long idBandoLinea,
			String codiceRuolo, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(validazioneRendicontazioneDAO.inizializzaValidazione(idDichiarazioneDiSpesa, idProgetto,
						idBandoLinea, userInfo.getIdSoggetto(), codiceRuolo, userInfo.getIdUtente(),
						userInfo.getIdIride()))
				.build();
	}

	@Override
	public Response initIntegrazione(Long idDichiarazioneSpesa, HttpServletRequest req) throws Exception {
		return Response.ok().entity(validazioneRendicontazioneDAO.initIntegrazione(idDichiarazioneSpesa)).build();
	}

	@Override
	public Response bandoIsEnteCompetenzaFinpiemonte(Long progBandoLineaIntervento, HttpServletRequest req)
			throws Exception {

		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");

		Boolean response = null;

		try {
			response = gestioneBackofficeBusinessImpl.bandoIsEnteCompetenzaFinpiemonte(progBandoLineaIntervento);

		} catch (Exception e) {
			LOG.error(prf + ": " + e);
			e.getStackTrace();
			throw e;

		}

		LOG.info(prf + " END");

		return Response.ok().entity(response).build();

	}

	@Override
	public Response inizializzaPopupChiudiValidazione(Long idDichiarazioneDiSpesa, Long idProgetto, Long idBandoLinea,
			HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(validazioneRendicontazioneDAO.inizializzaPopupChiudiValidazione(idDichiarazioneDiSpesa,
						idProgetto, idBandoLinea, userInfo.getIdUtente(), userInfo.getIdIride()))
				.build();
	}

	@Override
	public Response cercaDocumentiDiSpesaValidazione(
			CercaDocumentiDiSpesaValidazioneRequest cercaDocumentiDiSpesaValidazioneRequest, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(validazioneRendicontazioneDAO.cercaDocumentiDiSpesaValidazione(
						cercaDocumentiDiSpesaValidazioneRequest, userInfo.getIdUtente(), userInfo.getIdIride()))
				.build();
	}

	@Override
	public Response recuperaMensilitaDichiarazioneSpesa(Long idDichiarazioneSpesa, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(validazioneRendicontazioneDAO.recuperaMensilitaDichiarazioneSpesa(idDichiarazioneSpesa,
						userInfo.getIdUtente(), userInfo.getIdIride()))
				.build();
	}

	@Override
	public Response validaMensilita(List<ValidaMensilitaRequest> validaMensilitaRequest, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(validazioneRendicontazioneDAO.validaMensilita(validaMensilitaRequest,
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response dettaglioDocumentoDiSpesaPerValidazione(Long idDichiarazioneDiSpesa, Long idDocumentoDiSpesa,
			Long idProgetto, Long idBandoLinea, HttpServletRequest req) throws Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(validazioneRendicontazioneDAO.dettaglioDocumentoDiSpesaPerValidazione(idDichiarazioneDiSpesa,
						idDocumentoDiSpesa, idProgetto, idBandoLinea, userInfo.getIdUtente(), userInfo.getIdIride()))
				.build();
	}

	@Override
	public Response dettaglioDocumentoDiSpesaPerValidazioneOld(Long idDichiarazioneDiSpesa, Long idDocumentoDiSpesa,
			Long idProgetto, Long idBandoLinea, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(validazioneRendicontazioneDAO.dettaglioDocumentoDiSpesaPerValidazioneOld(idDichiarazioneDiSpesa,
						idDocumentoDiSpesa, idProgetto, idBandoLinea, userInfo.getIdUtente(), userInfo.getIdIride()))
				.build();
	}

	@Override
	public Response setDataNotifica(Long idIntegrazioneSpesa, Date dataNotifica, HttpServletRequest req)
			throws Exception {
		EsitoDTO esito = new EsitoDTO();
		try {
			validazioneRendicontazioneDAO.setDataNotifica(idIntegrazioneSpesa, dataNotifica);
			esito.setEsito(true);
			esito.setMessaggio("Data aggiornata correttamente.");
		} catch (Exception e) {
			esito.setEsito(false);
			esito.setMessaggio("Data NON aggiornata.");
		}
		return Response.ok().entity(esito).build();
	}

	@Override
	public Response sospendiDocumento(Long idDichiarazioneDiSpesa, Long idDocumentoDiSpesa, Long idProgetto,
			String noteValidazione, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		EsitoDTO esito = new EsitoDTO();
		try {
			validazioneRendicontazioneDAO.sospendiDocumento(idDichiarazioneDiSpesa, idDocumentoDiSpesa, idProgetto,
					noteValidazione, userInfo.getIdUtente(), userInfo.getIdIride(), Boolean.TRUE);
			esito.setEsito(true);
			esito.setMessaggio("Documento sospeso correttamente.");
		} catch (Exception e) {
			esito.setEsito(false);
			esito.setMessaggio("Documento NON sospeso.");
		}
		return Response.ok().entity(esito).build();
	}

	@Override
	public Response respingiDocumento(Long idDichiarazioneDiSpesa, Long idDocumentoDiSpesa, Long idProgetto,
			String noteValidazione, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		EsitoDTO esito = new EsitoDTO();
		try {
			validazioneRendicontazioneDAO.respingiDocumento(idDichiarazioneDiSpesa, idDocumentoDiSpesa, idProgetto,
					noteValidazione, userInfo.getIdUtente(), userInfo.getIdIride());
			esito.setEsito(true);
			esito.setMessaggio("Documento respinto correttamente.");
		} catch (Exception e) {
			esito.setEsito(false);
			esito.setMessaggio("Documento NON respinto.");
		}
		return Response.ok().entity(esito).build();
	}

	@Override
	public Response invalidaDocumento(Long idDichiarazioneDiSpesa, Long idDocumentoDiSpesa, Long idProgetto,
			String noteValidazione, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		EsitoDTO esito = new EsitoDTO();
		try {
			validazioneRendicontazioneDAO.invalidaDocumento(idDichiarazioneDiSpesa, idDocumentoDiSpesa, idProgetto,
					noteValidazione, userInfo.getIdUtente(), userInfo.getIdIride());
			esito.setEsito(true);
			esito.setMessaggio("Documento invalidato correttamente.");
		} catch (Exception e) {
			esito.setEsito(false);
			esito.setMessaggio("Documento NON invalidato.");
		}
		return Response.ok().entity(esito).build();
	}

	@Override
	public Response validaDocumento(Long idDichiarazioneDiSpesa, Long idDocumentoDiSpesa, Long idProgetto,
			String noteValidazione, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		EsitoDTO esito = new EsitoDTO();
		try {
			validazioneRendicontazioneDAO.validaDocumento(idDichiarazioneDiSpesa, idDocumentoDiSpesa, idProgetto,
					noteValidazione, userInfo.getIdUtente(), userInfo.getIdIride());
			esito.setEsito(true);
			esito.setMessaggio("Documento validato correttamente.");
		} catch (Exception e) {
			esito.setEsito(false);
			if ("Errore nella chiamata al servizio".equals(e.getMessage())) {
				esito.setMessaggio(e.getMessage());
			} else {
				esito.setMessaggio("Documento NON validato.");
			}
		}
		return Response.ok().entity(esito).build();
	}

	@Override
	public Response validaParzialmenteDocumento(ValidaParzialmenteDocumentoRequest validaParzialmenteDocumentoRequest,
			HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		EsitoDTO esito = new EsitoDTO();
		try {
			esito = validazioneRendicontazioneDAO.validaParzialmenteDocumento(validaParzialmenteDocumentoRequest,
					userInfo.getIdUtente(), userInfo.getIdIride());
		} catch (Exception e) {
			esito.setEsito(false);
			esito.setMessaggio("Documento NON salvato.");
		}
		return Response.ok().entity(esito).build();
	}

	@Override
	public Response reportDettaglioDocumentoDiSpesa(Long idDichiarazioneDiSpesa, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		EsitoLeggiFile esito = validazioneRendicontazioneDAO.reportDettaglioDocumentoDiSpesa(idDichiarazioneDiSpesa,
				userInfo.getIdUtente(), userInfo.getIdIride());
		return Response.ok().header("header-nome-file", esito.getNomeFile()).entity(esito.getBytes()).build();
	}

	@Override
	public Response verificaOperazioneMassiva(VerificaOperazioneMassivaRequest verificaOperazioneMassivaRequest,
			HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(validazioneRendicontazioneDAO.verificaOperazioneMassiva(verificaOperazioneMassivaRequest,
						userInfo.getIdUtente(), userInfo.getIdIride()))
				.build();
	}

	@Override
	public Response operazioneMassiva(OperazioneMassivaRequest operazioneMassivaRequest, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		EsitoDTO esito = new EsitoDTO();
		try {
			esito = validazioneRendicontazioneDAO.operazioneMassiva(operazioneMassivaRequest, userInfo.getIdUtente(),
					userInfo.getIdIride());
		} catch (Exception e) {
			esito.setEsito(false);
			esito.setMessaggio(ErrorMessages.ERRORE_SERVER);
		}
		return Response.ok().entity(esito).build();
	}

	@Override
	public Response richiediIntegrazione(RichiediIntegrazioneRequest richiediIntegrazioneRequest,
			HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		// return
		// Response.ok().entity(validazioneRendicontazioneDAO.richiediIntegrazione(richiediIntegrazioneRequest,
		// userInfo.getIdUtente(), userInfo.getIdIride())).build();
		EsitoDTO esito = new EsitoDTO();
		try {
			esito = validazioneRendicontazioneDAO.richiediIntegrazione(richiediIntegrazioneRequest,
					userInfo.getIdUtente(), userInfo.getIdIride());
		} catch (Exception e) {
			esito.setEsito(false);
			if ("Errore nella chiamata al servizio.".equals(e.getMessage())) {
				esito.setMessaggio(e.getMessage());
			} else {
				esito.setMessaggio(ErrorMessages.ERRORE_SERVER);
			}
		}
		return Response.ok().entity(esito).build();
	}

	@Transactional
	@Override
	public Response newRichiediIntegrazione(MultipartFormDataInput form, HttpServletRequest req) throws Exception {

		it.csi.pbandi.pbservizit.dto.EsitoOperazioni esitoPlus = new EsitoOperazioni();

		Integer avviaIter;

		try {
			// Parametri
			Long idProgetto = form.getFormDataPart("idProgetto", Long.class, null);
			Long idUtente = form.getFormDataPart("idUtente", Long.class, null);
			avviaIter = form.getFormDataPart("inviaIter", Integer.class, null);

			String nomeCheckListInterna = null;
			File checkListInterna = null;
			Boolean visibilitaChecklist = null;
			try {
				nomeCheckListInterna = form.getFormDataPart("nomeCheckListInterna", String.class, null);
				checkListInterna = form.getFormDataPart("checkListInterna", File.class, null);
				visibilitaChecklist = form.getFormDataPart("visibilitaChecklist", String.class, null).equals("1");
			} catch (Exception ignored) {
				//il file puo non esserci
			}
			String nomeReportValidazione = null;
			File reportValidazione = null;
			Boolean visibilitaReport = null;
			try {
				nomeReportValidazione = form.getFormDataPart("nomeReportValidazione", String.class, null);
				reportValidazione = form.getFormDataPart("reportValidazione", File.class, null);
				visibilitaReport = form.getFormDataPart("visibilitaReport", String.class, null).equals("1");
			} catch (Exception ignored) {
				//il file puo non esserci
			}

			Long idIntegrazioneSpesa;

			// 1 - Crea integrazione
			EsitoDTO esitoInt = validazioneRendicontazioneDAO.newRichiediIntegrazione(form);
			if (!esitoInt.getEsito()) {
				esitoPlus.setEsito(false);
				LOG.error("Errore in newRichiediIntegrazione: " + esitoInt.getMessaggio());
				esitoPlus.setMsg("Errore durante il salvataggio della richiesta integrazione.");
				return Response.ok(esitoPlus).build();
			} else {
				idIntegrazioneSpesa = esitoInt.getId();
			}

			// salvaChecklistInterna
			String res1 = validazioneRendicontazioneDAO.salvaChecklistInterna(checkListInterna, nomeCheckListInterna,
					visibilitaChecklist, BigDecimal.valueOf(idProgetto), idUtente,
					BigDecimal.valueOf(idIntegrazioneSpesa), true);
			if (!Objects.equals(res1, "OK")) {
				esitoPlus.setEsito(false);
				LOG.error("Errore in salvaChecklistInterna: " + res1);
				esitoPlus.setMsg("Errore durante il salvataggio della checklist interna.");
				return Response.ok(esitoPlus).build();
			}
			// salvaReportValidazione
			res1 = validazioneRendicontazioneDAO.salvaReportValidazione(reportValidazione, nomeReportValidazione,
					visibilitaReport, BigDecimal.valueOf(idProgetto), idUtente, BigDecimal.valueOf(idIntegrazioneSpesa),
					true);
			if (!Objects.equals(res1, "OK")) {
				esitoPlus.setEsito(false);
				LOG.error("Errore in salvaReportValidazione: " + res1);
				esitoPlus.setMsg("Errore durante il salvataggio del report di validazione.");
				return Response.ok(esitoPlus).build();
			}

			// 2 - Salva lettera accompagnatoria
			res1 = validazioneRendicontazioneDAO.salvaAllegatoIntegrazione(form, idIntegrazioneSpesa);
			if (!Objects.equals(res1, "OK")) {
				esitoPlus.setEsito(false);
				LOG.error("Errore in salvaAllegatoIntegrazione: " + res1);
				esitoPlus.setMsg("Errore durante il salvataggio della lettera accompagnatoria.");
				return Response.ok(esitoPlus).build();
			}

			// 3 - SOLO FINPIEMONTE - Avvia iter
			if (avviaIter == 1) { // 1 = true
				String res2 = iterAutorizzativiDAOImpl.avviaIterAutorizzativo(1L, 453L, idIntegrazioneSpesa, idProgetto,
						idUtente);
				if (!Objects.equals(res2, "")) {
					esitoPlus.setEsito(false);
					LOG.error("Errore in avviaIterAutorizzativo: " + res2);
					esitoPlus.setMsg("Errore durante l'avvio dell'iter autorizzativo.");
					return Response.ok(esitoPlus).build();
				}
			}

			// return
			// Response.ok().entity(validazioneRendicontazioneDAO.chiudiValidazione(idDichiarazioneDiSpesa,
			// idDocumentoIndex, idBandoLinea, invioExtraProcedura, userInfo.getIdUtente(),
			// userInfo.getIdIride())).build();
		} catch (Exception e) {
			LOG.error("Errore generale: " + e.toString());
			throw e;
		}

		esitoPlus.setEsito(true);
		if (avviaIter == 1) {
			esitoPlus.setMsg("Iter avviato con successo.");
		} else {
			esitoPlus.setMsg("Richiesta di integrazione creata con successo.");
		}

		return Response.ok(esitoPlus).build();

	}

	@Override
	public Response chiudiRichiestaIntegrazione(Long idIntegrazione, HttpServletRequest req) {
		return Response.ok().entity(validazioneRendicontazioneDAO.chiudiRichiestaIntegrazione(idIntegrazione, req))
				.build();
	}

	@Override
	public Response chiudiValidazione(Long idDichiarazioneDiSpesa, Long idDocumentoIndex, Long idBandoLinea,
			Boolean invioExtraProcedura, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(validazioneRendicontazioneDAO.chiudiValidazione(idDichiarazioneDiSpesa,
				idDocumentoIndex, idBandoLinea, invioExtraProcedura, userInfo.getIdUtente(), userInfo.getIdIride()))
				.build();
	}

	@Override
	public Response proseguiChiudiValidazione(ProseguiChiudiValidazioneRequest proseguiChiudiValidazioneRequest,
			HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(validazioneRendicontazioneDAO.proseguiChiudiValidazione(proseguiChiudiValidazioneRequest,
						userInfo.getIdUtente(), userInfo.getIdIride()))
				.build();
	}

	@Override
	public Response salvaProtocollo(Long idDocumentoIndex, String protocollo, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		Boolean esito;
		try {
			esito = validazioneRendicontazioneDAO.salvaProtocollo(idDocumentoIndex, protocollo, userInfo.getIdUtente(),
					userInfo.getIdIride());
		} catch (Exception e) {
			esito = false;
		}
		return Response.ok().entity(esito).build();
	}

	@Override
	public Response getModuloCheckListValidazioneHtml(String idProgetto, String idDichiarazioneDiSpesa,
			HttpServletRequest req) throws InvalidParameterException, Exception {
		String prf = "[CheckListServiceImpl::getModuloCheckListValidazioneHtml] ";

		// CPBEGestioneCheckListHtml > inizializzaCP "checklist IN INSERIMENTO"

		LOG.debug(prf + "BEGIN");
		LOG.debug(prf + "idProgetto=" + idProgetto);
		LOG.debug(prf + "idDichiarazioneDiSpesa=" + idDichiarazioneDiSpesa);

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		String soggettoControllore = "";
		if (StringUtils.isNotBlank(userInfo.getCognome()) && StringUtils.isNotBlank(userInfo.getNome())) {
			soggettoControllore = userInfo.getCognome() + " " + userInfo.getNome();
		} else if (StringUtils.isNotBlank(userInfo.getCodFisc())) {
			soggettoControllore = userInfo.getCodFisc();
		}
		LOG.debug(prf + "soggettoControllore=" + soggettoControllore);

		return Response.ok()
				.entity(validazioneRendicontazioneDAO.getModuloCheckListValidazioneHtml(userInfo.getIdUtente(),
						userInfo.getIdIride(), Long.valueOf(idProgetto), Long.valueOf(idDichiarazioneDiSpesa),
						soggettoControllore))
				.build();

	}

	@Override
	public Response initDropDownCL(Long idProgetto, HttpServletRequest req) throws Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(validazioneRendicontazioneDAO.initDropDownCL(idProgetto)).build();
	}

	@Transactional
	public Response chiudiValidazioneEsito(MultipartFormDataInput multipartFormData, HttpServletRequest req)
			throws Exception {
		EsitoOperazioni esito = new EsitoOperazioni();
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		// Parametri comuni
		Long idProgetto = multipartFormData.getFormDataPart("idProgetto", Long.class, null);
		Long idUtente = multipartFormData.getFormDataPart("idUtente", Long.class, null);
		int entita = multipartFormData.getFormDataPart("entita", int.class, null);

		if (entita != 1 && entita != 2 && entita != 3 && entita != 4) {
			esito.setEsito(false);
			esito.setMsg("entità non valorizzata o fuori range.");
			return Response.ok().entity(esito).build();
		}

		BigDecimal idDichiarazioneSpesa = multipartFormData.getFormDataPart("idDichiarazioneSpesa", BigDecimal.class,
				null);
		BigDecimal idAttribValidSpesa = multipartFormData.getFormDataPart("idAttribValidSpesa", BigDecimal.class, null);
		BigDecimal idEsitoValidSpesa = multipartFormData.getFormDataPart("idEsitoValidSpesa", BigDecimal.class, null);

		String listIdModalitaAgevolazioneString = multipartFormData.getFormDataPart("listIdModalitaAgevolazione",
				String.class, null);
		List<Long> listIdModalitaAgevolazione = new ArrayList<Long>();
		if (listIdModalitaAgevolazioneString != null) {
			JSONArray jsonArray = new JSONArray(listIdModalitaAgevolazioneString);
			for (int i = 0; i < jsonArray.length(); i++) {
				listIdModalitaAgevolazione.add(jsonArray.getLong(i));
			}
		}

		Double premialita = null;
		;
		try {
			premialita = multipartFormData.getFormDataPart("premialita", Double.class, null);
		} catch (Exception e) {
			premialita = null;
		}
		BigDecimal idCausaleErogContributo = null;
		;
		try {
			idCausaleErogContributo = multipartFormData.getFormDataPart("idCausaleErogContributo", BigDecimal.class,
					null);
		} catch (Exception e) {
			idCausaleErogContributo = null;
		}
		BigDecimal idCausaleErogFinanz = null;
		;
		try {
			idCausaleErogFinanz = multipartFormData.getFormDataPart("idCausaleErogFinanz", BigDecimal.class, null);
		} catch (Exception e) {
			idCausaleErogFinanz = null;
		}
		String nomeFile = null;
		File filePart = null;
		Boolean visibilitaFile = null;
		try {
			nomeFile = multipartFormData.getFormDataPart("nomeLetteraAccompagnatoria", String.class, null);
			filePart = multipartFormData.getFormDataPart("letteraAccompagnatoria", File.class, null);
			visibilitaFile = multipartFormData.getFormDataPart("visibilitaLettera", String.class, null).equals("1");
		}catch (Exception e){
			if(entita == 1) {
				esito.setEsito(false);
				esito.setMsg("Lettera accompagnatoria mancante.");
				return Response.ok().entity(esito).build();
			}
		}

		String nomeCheckListInterna = null;
		File checkListInterna = null;
		Boolean visibilitaChecklist = null;
		try {
			nomeCheckListInterna = multipartFormData.getFormDataPart("nomeCheckListInterna", String.class, null);
			checkListInterna = multipartFormData.getFormDataPart("checkListInterna", File.class, null);
			visibilitaChecklist = multipartFormData.getFormDataPart("visibilitaChecklist", String.class, null)
					.equals("1");
		} catch (Exception ignored) {
		}

		String nomeReportValidazione = null;
		File reportValidazione = null;
		Boolean visibilitaReport = null;
		try {
			nomeReportValidazione = multipartFormData.getFormDataPart("nomeReportValidazione", String.class, null);
			reportValidazione = multipartFormData.getFormDataPart("reportValidazione", File.class, null);
			visibilitaReport = multipartFormData.getFormDataPart("visibilitaReport", String.class, null).equals("1");
		} catch (Exception ignored) {
		}

		BigDecimal importoDaErogare = null;
		BigDecimal importoIres = null;
		switch (entita) {
			case 2:
				String res27 = validazioneRendicontazioneDAO.verificaPropostaErogazione(BigDecimal.valueOf(idProgetto));
				if (!Objects.equals(res27, "OK")) {
					esito.setEsito(false);
					esito.setMsg("Errore in fase di inserimento proposta di erogazione: " + res27);
					return Response.ok().entity(esito).build();
				}

				try {
					importoDaErogare = multipartFormData.getFormDataPart("importoDaErogare", BigDecimal.class, null);
				} catch (Exception e) {
					importoIres = null;
				}
				try {
					importoIres = multipartFormData.getFormDataPart("importoIres", BigDecimal.class, null);
				} catch (Exception e) {
					importoIres = BigDecimal.ZERO;
				}
				break;
			case 3:
				// PBAN-OPE-STE-V01-REV024-Verifica inserimento proposta di revoca
				String res4 = validazioneRendicontazioneDAO.verificaInserimentoPropostaRevoca(new Date(), idProgetto);
				if (!Objects.equals(res4, "OK")) {
					esito.setEsito(false);
					esito.setMsg("Errore in fase di inserimento proposta di revoca: " + res4);
					return Response.ok().entity(esito).build();
				}
				break;
			case 4:
				// PBAN-OPE-STE-V01-REV024-Verifica inserimento proposta di revoca
				String res5 = validazioneRendicontazioneDAO.verificaInserimentoPropostaRevoca(new Date(), idProgetto);
				if (!Objects.equals(res5, "OK")) {
					esito.setEsito(false);
					esito.setMsg("Errore in fase di inserimento proposta di revoca: " + res5);
					return Response.ok().entity(esito).build();
				}
				String res29 = validazioneRendicontazioneDAO.verificaPropostaErogazione(BigDecimal.valueOf(idProgetto));
				if (!Objects.equals(res29, "OK")) {
					esito.setEsito(false);
					esito.setMsg("Errore in fase di inserimento proposta di erogazione: " + res29);
					return Response.ok().entity(esito).build();
				}
				try {
					importoDaErogare = multipartFormData.getFormDataPart("importoDaErogare", BigDecimal.class, null);
				} catch (Exception e) {
					importoIres = null;
				}
				try {
					importoIres = multipartFormData.getFormDataPart("importoIres", BigDecimal.class, null);
				} catch (Exception e) {
					importoIres = BigDecimal.ZERO;
				}
				break;
		}

		Long idDocumentoIndexCL;
		String isChiudiValidazione = multipartFormData.getFormDataPart("isChiudiValidazione", String.class, null);
		if ("1".equals(isChiudiValidazione)) {
			Response chiudiValidazioneChecklist = chiudiValidazioneChecklistHtml(req, multipartFormData);
			EsitoOperazioni esitoOperazioni = (EsitoOperazioni) chiudiValidazioneChecklist.getEntity();
			esito.setParams(esitoOperazioni.getParams());
			idDocumentoIndexCL = Long.parseLong(esitoOperazioni.getParams()[0]);
		} else {
			Response saveChecklistDocumentale = checkListService.saveCheckListDocumentaleHtml(multipartFormData);
			EsitoOperazioneDTO esitoOperazioneDTO = (EsitoOperazioneDTO) saveChecklistDocumentale.getEntity();
			idDocumentoIndexCL = esitoOperazioneDTO.getIdDocumentoIndexCL();
		}
		LOG.info("idDichiarazioneSpesa: " + idDichiarazioneSpesa + "\nidDocumentoIndexCL: " + idDocumentoIndexCL);
		Long idCheckList = validazioneRendicontazioneDAO.getIdChecklist(idDichiarazioneSpesa.longValue(),
				idDocumentoIndexCL);
		LOG.info("idCheckList: " + idCheckList);
		if (idCheckList == null) {
			esito.setEsito(false);
			esito.setMsg("Checklist non trovata");
			return Response.ok().entity(esito).build();
		}

		// Gestisco i 3 casi di combinazioni attributo - esito
		switch (entita) {
			case 1: // lettera accompagnatoria + avvio iter
				int res1 = validazioneRendicontazioneDAO.updateCriteri(idAttribValidSpesa, idEsitoValidSpesa,
						idDichiarazioneSpesa);
				if (res1 == 0) { // Controllo se l'update è andato a buon fine
					esito.setEsito(false);
					esito.setMsg("Errore durante l'aggiornamento della dichiarazione di spesa");
					return Response.ok().entity(esito).build();
				}

				String res2 = iterAutorizzativiDAOImpl.avviaIterAutorizzativo(2L, 242L, idCheckList, idProgetto,
						idUtente);
				if (!Objects.equals(res2, "")) { // Controllo se l'avvia iter è andato a buon fine
					esito.setEsito(false);
					esito.setMsg("Errore durante l'avvio dell'iter: " + res2);
					return Response.ok().entity(esito).build();
				}

				String res3 = validazioneRendicontazioneDAO.salvaLetteraAccompagnatoria(filePart, nomeFile,
						visibilitaFile, BigDecimal.valueOf(idProgetto), entita, idUtente,
						BigDecimal.valueOf(idCheckList));
				if (!Objects.equals(res3, "OK")) { // Controllo se il salvataggio della lettera è andato a buon fine
					esito.setEsito(false);
					esito.setMsg("Errore durante il salvataggio della lettera accompagnatoria: " + res3);
					return Response.ok().entity(esito).build();
				}

				String res20 = validazioneRendicontazioneDAO.salvaChecklistInterna(checkListInterna,
						nomeCheckListInterna, visibilitaChecklist, BigDecimal.valueOf(idProgetto), idUtente,
						BigDecimal.valueOf(idCheckList), false);
				if (!Objects.equals(res20, "OK")) {
					esito.setEsito(false);
					esito.setMsg("Errore durante il salvataggio della checklist interna: " + res20);
					return Response.ok().entity(esito).build();
				}
				String res24 = validazioneRendicontazioneDAO.salvaReportValidazione(reportValidazione,
						nomeReportValidazione, visibilitaReport, BigDecimal.valueOf(idProgetto), idUtente,
						BigDecimal.valueOf(idCheckList), false);
				if (!Objects.equals(res24, "OK")) {
					esito.setEsito(false);
					esito.setMsg("Errore durante il salvataggio del report validazione: " + res24);
					return Response.ok().entity(esito).build();
				}

				esito.setEsito(true);
				esito.setMsg("Caso 1 terminato con successo.");
				break;
			case 2: // proposta di erogazione
				int res9 = validazioneRendicontazioneDAO.updateCriteri(idAttribValidSpesa, idEsitoValidSpesa,
						idDichiarazioneSpesa);
				if (res9 == 0) {
					esito.setEsito(false);
					esito.setMsg("Errore durante l'aggiornamento della dichiarazione di spesa");
					return Response.ok().entity(esito).build();
				}

				String res8 = validazioneRendicontazioneDAO.creaPropostaErogazione(listIdModalitaAgevolazione,
						importoDaErogare, importoIres, BigDecimal.valueOf(idProgetto), idUtente,
						idDichiarazioneSpesa.longValue(), premialita, idCausaleErogContributo, idCausaleErogFinanz,
						req);
				if (!Objects.equals(res8, "OK")) {
					esito.setEsito(false);
					esito.setMsg("Errore durante la creazione della proposta di erogazione: " + res8);
					return Response.ok().entity(esito).build();
				}

				String res21 = validazioneRendicontazioneDAO.salvaChecklistInterna(checkListInterna,
						nomeCheckListInterna, visibilitaChecklist, BigDecimal.valueOf(idProgetto), idUtente,
						BigDecimal.valueOf(idCheckList), false);
				if (!Objects.equals(res21, "OK")) {
					esito.setEsito(false);
					esito.setMsg("Errore durante il salvataggio della checklist interna: " + res21);
					return Response.ok().entity(esito).build();
				}
				String res25 = validazioneRendicontazioneDAO.salvaReportValidazione(reportValidazione,
						nomeReportValidazione, visibilitaReport, BigDecimal.valueOf(idProgetto), idUtente,
						BigDecimal.valueOf(idCheckList), false);
				if (!Objects.equals(res25, "OK")) {
					esito.setEsito(false);
					esito.setMsg("Errore durante il salvataggio del report validazione: " + res25);
					return Response.ok().entity(esito).build();
				}

				esito.setEsito(true);
				esito.setMsg("Caso 2 terminato con successo.");
				break;
			case 3: // lettera accompagnatoria + proposta di revoca

				int res6 = validazioneRendicontazioneDAO.updateCriteri(idAttribValidSpesa, idEsitoValidSpesa,
						idDichiarazioneSpesa);
				if (res6 == 0) {
					esito.setEsito(false);
					esito.setMsg("Errore durante l'aggiornamento della dichiarazione di spesa");
					return Response.ok().entity(esito).build();
				}

				//CHIAMATA A DAO (Algoritmo 7.1)--->>
				String res5 = validazioneRendicontazioneDAO.creaPropostaRevoca(BigDecimal.valueOf(idProgetto), idDichiarazioneSpesa, idUtente, filePart, nomeFile, visibilitaFile, entita);
				// Restituisce idGestioneRevoca attraverso res5
				if (!Objects.equals(res5, "OK")) {
					esito.setEsito(false);
					esito.setMsg("Errore durante il salvataggio della proposta di revoca: " + res5);
					return Response.ok().entity(esito).build();
				}

				String res22 = validazioneRendicontazioneDAO.salvaChecklistInterna(checkListInterna,
						nomeCheckListInterna, visibilitaChecklist, BigDecimal.valueOf(idProgetto), idUtente,
						BigDecimal.valueOf(idCheckList), false);
				if (!Objects.equals(res22, "OK")) {
					esito.setEsito(false);
					esito.setMsg("Errore durante il salvataggio della checklist interna: " + res22);
					return Response.ok().entity(esito).build();
				}
				String res26 = validazioneRendicontazioneDAO.salvaReportValidazione(reportValidazione,
						nomeReportValidazione, visibilitaReport, BigDecimal.valueOf(idProgetto), idUtente,
						BigDecimal.valueOf(idCheckList), false);
				if (!Objects.equals(res26, "OK")) {
					esito.setEsito(false);
					esito.setMsg("Errore durante il salvataggio del report validazione: " + res26);
					return Response.ok().entity(esito).build();
				}

				esito.setEsito(true);
				esito.setMsg("Caso 3 terminato con successo.");
				break;
			case 4: // proposta di revoca + proposta di erogazione e revoca
				int res11 = validazioneRendicontazioneDAO.updateCriteri(idAttribValidSpesa, idEsitoValidSpesa,
						idDichiarazioneSpesa);
				if (res11 == 0) {
					esito.setEsito(false);
					esito.setMsg("Errore durante l'aggiornamento della dichiarazione di spesa");
					return Response.ok().entity(esito).build();
				}

				String res13 = validazioneRendicontazioneDAO.creaPropostaRevocaEdErogazione(importoDaErogare,
						importoIres, BigDecimal.valueOf(idProgetto), idDichiarazioneSpesa.longValue(), idUtente,
						premialita, idCausaleErogContributo, idCausaleErogFinanz);
				if (res13 != "OK") {
					esito.setEsito(false);
					esito.setMsg("Errore in creaPropostaRevocaEdErogazione: " + res13);
					return Response.ok().entity(esito).build();
				}

				String res23 = validazioneRendicontazioneDAO.salvaChecklistInterna(checkListInterna,
						nomeCheckListInterna, visibilitaChecklist, BigDecimal.valueOf(idProgetto), idUtente,
						BigDecimal.valueOf(idCheckList), false);
				if (res23 != "OK") {
					esito.setEsito(false);
					esito.setMsg("Errore durante il salvataggio della checklist interna: " + res23);
					return Response.ok().entity(esito).build();
				}
				String res27 = validazioneRendicontazioneDAO.salvaReportValidazione(reportValidazione,
						nomeReportValidazione, visibilitaReport, BigDecimal.valueOf(idProgetto), idUtente,
						BigDecimal.valueOf(idCheckList), false);
				if (res27 != "OK") {
					esito.setEsito(false);
					esito.setMsg("Errore durante il salvataggio del report validazione: " + res27);
					return Response.ok().entity(esito).build();
				}

				esito.setEsito(true);
				esito.setMsg("Caso 4 terminato con successo.");
				break;
		}
		
		try {
			Boolean isBR63 = profilazioneBusinessImpl.isRegolaApplicabileForProgetto(idUtente, userInfo.getIdIride(), idProgetto,
					RegoleConstants.BR63_RAGIONERIA_DELEGATA);
	
			if (isBR63 != null && isBR63.equals(Boolean.TRUE)) {
				validazioneRendicontazioneDAO.inviaNotificaChiudiValidRagioneriaDelegata(new Long(idDichiarazioneSpesa.longValue()), idProgetto, idUtente, userInfo.getIdIride());
			}
		} catch(Exception e) {
			throw e;
		}
		
		return Response.ok().entity(esito).build();
	}

	@Override
	public Response chiudiValidazioneUfficio(Long idDichiarazioneSpesa, HttpServletRequest req) {
		EsitoOperazioni esito;
		try {
			esito = validazioneRendicontazioneDAO.chiudiValidazioneUfficio(idDichiarazioneSpesa, req);
		} catch (Exception e) {
			esito = new EsitoOperazioni();
			esito.setEsito(false);
			esito.setMsg(e.getMessage());
		}
		esito.setParams(new String[0]);
		return Response.ok().entity(esito).build();
	}

	@Override
	public Response saveCheckListDocumentaleHtml(HttpServletRequest req,
			SalvaCheckListValidazioneDocumentaleHtmlRequest salvachechlistaffReq)
			throws InvalidParameterException, Exception {
		String prf = "[ValidazioneRendicontazioneServiceImpl::saveCheckListDocumentaleHtml] ";
		LOG.debug(prf + "BEGIN");
		LOG.debug(prf + "idProgetto=" + salvachechlistaffReq.getIdProgetto());
		LOG.debug(prf + "idDichiarazione=" + salvachechlistaffReq.getIdDichiarazioneDiSpesa());
		LOG.debug(prf + "idDocumentoIndex=" + salvachechlistaffReq.getIdDocumentoIndex());
		LOG.debug(prf + "statoChecklist=" + salvachechlistaffReq.getStato());
		LOG.debug(prf + "checklistHtml=" + salvachechlistaffReq.getChecklistHtml());

		// PK pbandiWeb : ChecklistHtmlAction.java > saveCheckListDocumentaleHtml

		boolean success = false;
		EsitoSalvaModuloCheckListHtmlDTO esitoSalvataggio = null;

		UserInfoSec user = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

		ChecklistHtmlDTO checkListHtmlDTO = new ChecklistHtmlDTO();
		checkListHtmlDTO.setContentHtml(salvachechlistaffReq.getChecklistHtml());
		checkListHtmlDTO.setCodStatoTipoDocIndex(salvachechlistaffReq.getStato());
		checkListHtmlDTO.setIdDocumentoIndex(Long.valueOf(salvachechlistaffReq.getIdDocumentoIndex()));

		esitoSalvataggio = validazioneRendicontazioneDAO.saveCheckListDocumentaleHtml(user.getIdUtente(),
				user.getIdIride(), salvachechlistaffReq.getIdProgetto(), salvachechlistaffReq.getStato(),
				checkListHtmlDTO, salvachechlistaffReq.getIdDichiarazioneDiSpesa());
		esitoSalvataggio.setIdDocumentoIndex(salvachechlistaffReq.getIdDocumentoIndex());

		LOG.debug(prf + " after checklistHtmlBusinessImpl.saveCheckListDocumentaleHtml:\n [Esito] : "
				+ esitoSalvataggio.getEsito() + "\n [idDocumentoIndex] : " + esitoSalvataggio.getIdDocumentoIndex());
		success = esitoSalvataggio.getEsito();

		LOG.debug(prf + "END");
		return Response.ok().entity(success).build();
	}

	@Override
	public Response chiudiValidazioneChecklistHtmlOld(HttpServletRequest req,
			SalvaCheckListValidazioneDocumentaleHtmlRequest salvachechlistaffReq)
			throws InvalidParameterException, Exception {

		String prf = "[ValidazioneRendicontazioneServiceImpl::chiudiValidazioneChecklistHtml] ";

		LOG.debug(prf + "BEGIN");
		LOG.debug(prf + "salvachechlistaffReq=" + salvachechlistaffReq);

		UserInfoSec user = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = user.getIdUtente();
		String idIride = user.getIdIride();

		// PK pbandiWeb : CPBEConfermaSalvaCheckListValidazione::chiudiValidazione

		final ValidazioneRendicontazioneDTO validazioneDTO = new ValidazioneRendicontazioneDTO();

		validazioneDTO.setIdBandoLinea(salvachechlistaffReq.getIdBandoLinea());

		validazioneDTO.setIdProgetto(salvachechlistaffReq.getIdProgetto());
		validazioneDTO.setIdDichiarazioneDiSpesa(salvachechlistaffReq.getIdDichiarazioneDiSpesa());

		if (salvachechlistaffReq.isDsIntegrativaConsentita())
			validazioneDTO.setFlagRichiestaIntegrativa("S");

		validazioneDTO.setCodiceProgetto(salvachechlistaffReq.getCodiceProgetto());

		Long idSogg = user.getIdSoggetto();
		LOG.debug(prf + "user.getIdSoggetto=" + idSogg);

		Long idSoggetto = user.getBeneficiarioSelezionato().getIdBeneficiario();
		LOG.debug(prf + "user.getBeneficiarioSelezionato().getIdBeneficiario=" + idSoggetto);

		if (idSoggetto != null)
			validazioneDTO.setIdSoggettoBen(idSoggetto);
//		else {
//			validazioneDTO.setIdSoggettoBen(theModel.getAppDatadatiGenerali().getBeneficiario().getIdBeneficiario());
//		}

		String CfBeneficiario = user.getBeneficiarioSelezionato().getCodiceFiscale();
		LOG.debug(prf + "CfBeneficiario=" + CfBeneficiario);
		validazioneDTO.setCfBeneficiario(CfBeneficiario);

		final IstanzaAttivitaDTO istanza = new IstanzaAttivitaDTO();
		// istanza.setId(sessionContext.getIstanzaAttivitaCorrente().getTaskIdentity());
		// //VALID-DICH-SPESA , id=47 ???
		istanza.setId(it.csi.pbandi.pbweb.util.Constants.TASK_IDENTITY_VALIDAZIONE);

		String checkListHtml = salvachechlistaffReq.getChecklistHtml();
		validazioneDTO.setCheckListHtml(checkListHtml);

		EsitoValidazioneRendicontazionePiuGreen esito = validazioneRendicontazioneDAO
				.chiudiValidazioneChecklistHtml(idUtente, idIride, istanza, validazioneDTO);

		LOG.debug(prf + "esito=" + esito);

		/*
		 * impostaMessaggiDaEsito serve per generare delle stringhe/url e metterle in
		 * sessione
		 * 
		 * impostaMessaggiDaEsito(theModel, result,
		 * SALVAMODULOCHECKLISTVALIDAZIONE_OUTCOME_CODE__KO, new ImpostatoreDiMessaggi()
		 * { public Object caricaEsito() throws Exception { return
		 * getValidazioneDichiarazioneDiSpesaBusiness() .chiudiValidazioneChecklistHtml(
		 * sessionContext.getUserInfo(), sessionContext.getCodUtenteFlux(), istanza,
		 * validazioneDTO); } });
		 * 
		 */

		it.csi.pbandi.pbservizit.dto.EsitoOperazioni es = new EsitoOperazioni();
		es.setEsito(esito.getEsito());
		es.setMsg(esito.getMessage());

		String nomeFileCK = validazioneRendicontazioneDAO.getNomeFile(esito.getIdDocIndexDichiarazione());
		LOG.debug(prf + "nomeFileCK=" + nomeFileCK);

		String nomeFileRD = validazioneRendicontazioneDAO.getNomeFile(esito.getIdReportDettaglioDocSpesa());
		LOG.debug(prf + "nomeFileRD=" + nomeFileRD);

		String[] parametri = { esito.getIdDocIndexDichiarazione() + "", nomeFileCK,
				esito.getIdReportDettaglioDocSpesa() + "", nomeFileRD };
		es.setParams(parametri); // idDOcumentoIndex Checklist, idDocumentoIndexExcell, nomi dei files

		LOG.debug(prf + "END");
		return Response.ok().entity(es).build();
	}

	@Override
	@Transactional
	public Response chiudiValidazioneChecklistHtml(HttpServletRequest req, MultipartFormDataInput multipartFormData)
			throws InvalidParameterException, Exception {

		String prf = "[ValidazioneRendicontazioneServiceImpl::chiudiValidazioneChecklistHtml] ";

		LOG.debug(prf + "BEGIN");
		LOG.info(prf + "BEGIN");
		// LOG.debug(prf+"salvachechlistaffReq="+salvachechlistaffReq);

		// Parametri

		UserInfoSec user = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

		Long idUtente = user.getIdUtente();
		String idIride = user.getIdIride();

		Long idProgetto = multipartFormData.getFormDataPart("idProgetto", Long.class, null);
		Long idDichiarazioneDiSpesa = multipartFormData.getFormDataPart("idDichiarazioneDiSpesa", Long.class, null);
		Long idBandoLinea = multipartFormData.getFormDataPart("idBandoLinea", Long.class, null);
		String codiceProgetto = multipartFormData.getFormDataPart("codiceProgetto", String.class, null);
		Boolean isDsIntegrativaConsentita = multipartFormData.getFormDataPart("dsIntegrativaConsentita", Boolean.class,
				null);
		String checkListHtml = multipartFormData.getFormDataPart("checklistHtml", String.class, null);
		Long idChecklist = multipartFormData.getFormDataPart("idChecklist", Long.class, null);
		if (idChecklist != null && idChecklist.equals(-1L)) {
			idChecklist = null;
		}

		LOG.info(prf + "idChecklist: " + idChecklist);

		// File
		Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
		List<InputPart> files = map.get("file");

		FileDTO[] verbali = leggiFilesDaMultipart(files);
		if (verbali == null || verbali.length == 0) {

			// List <PbandiCCostantiVO> costntiList = genericDAO.findListWhere(vo)
			// String estensioniAllegatiStr =
			// validazioneRendicontazioneDAO.getCostantiAllegati(idUtente,
			// idIride).getAttributo();
			// List <String> estensioniAllegati = Arrays.asList(
			// estensioniAllegatiStr.split(",") );

			// LOG.info(prf + " " + estensioniAllegati);

			// throw new Exception("Nessun file allegato.");
			// TODO verificare se è obbligatorio l'allegato
			verbali = null;

		} else {
			LOG.debug(prf + " verbali.length=" + verbali.length);
			LOG.info(prf + " verbali.length=" + verbali.length);
		}

		//////////////////// Logica metodo Codice del vecchio metodo
		//////////////////// //////////////////////////////

		it.csi.pbandi.pbservizit.dto.EsitoOperazioni es = new EsitoOperazioni();

		try {
			// PK pbandiWeb : CPBEConfermaSalvaCheckListValidazione::chiudiValidazione

			final ValidazioneRendicontazioneDTO validazioneDTO = new ValidazioneRendicontazioneDTO();

			validazioneDTO.setIdBandoLinea(idBandoLinea);

			validazioneDTO.setIdProgetto(idProgetto);
			validazioneDTO.setIdDichiarazioneDiSpesa(idDichiarazioneDiSpesa);

			if (isDsIntegrativaConsentita)
				validazioneDTO.setFlagRichiestaIntegrativa("S");

			validazioneDTO.setCodiceProgetto(codiceProgetto);

			Long idSogg = user.getIdSoggetto();
			LOG.debug(prf + "user.getIdSoggetto=" + idSogg);

			Long idSoggetto = user.getBeneficiarioSelezionato().getIdBeneficiario();
			LOG.debug(prf + "user.getBeneficiarioSelezionato().getIdBeneficiario=" + idSoggetto);

			if (idSoggetto != null)
				validazioneDTO.setIdSoggettoBen(idSoggetto);
			// else {
			// validazioneDTO.setIdSoggettoBen(theModel.getAppDatadatiGenerali().getBeneficiario().getIdBeneficiario());
			// }

			String CfBeneficiario = user.getBeneficiarioSelezionato().getCodiceFiscale();
			LOG.debug(prf + "CfBeneficiario=" + CfBeneficiario);
			validazioneDTO.setCfBeneficiario(CfBeneficiario);

			final IstanzaAttivitaDTO istanza = new IstanzaAttivitaDTO();
			// istanza.setId(sessionContext.getIstanzaAttivitaCorrente().getTaskIdentity());
			// //VALID-DICH-SPESA , id=47 ???
			istanza.setId(it.csi.pbandi.pbweb.util.Constants.TASK_IDENTITY_VALIDAZIONE);

			validazioneDTO.setCheckListHtml(checkListHtml);

			// #####->
			EsitoValidazioneRendicontazionePiuGreen esito = validazioneRendicontazioneDAO
					.chiudiValidazioneChecklistHtml(idUtente, idIride, istanza, validazioneDTO, idChecklist, verbali);

			try {
				Boolean isBR63 = profilazioneBusinessImpl.isRegolaApplicabileForProgetto(idUtente, idIride, idProgetto,
						RegoleConstants.BR63_RAGIONERIA_DELEGATA);
	
				if (isBR63 != null && isBR63.equals(Boolean.TRUE)) {
					validazioneRendicontazioneDAO.inviaNotificaChiudiValidRagioneriaDelegata(idDichiarazioneDiSpesa, idProgetto, idUtente, idIride);
				}
			} catch (Exception e) {
				throw e;
			}
			LOG.debug(prf + "esito=" + esito);

			/*
			 * impostaMessaggiDaEsito serve per generare delle stringhe/url e metterle in
			 * sessione
			 * 
			 * impostaMessaggiDaEsito(theModel, result,
			 * SALVAMODULOCHECKLISTVALIDAZIONE_OUTCOME_CODE__KO, new ImpostatoreDiMessaggi()
			 * { public Object caricaEsito() throws Exception { return
			 * getValidazioneDichiarazioneDiSpesaBusiness() .chiudiValidazioneChecklistHtml(
			 * sessionContext.getUserInfo(), sessionContext.getCodUtenteFlux(), istanza,
			 * validazioneDTO); } });
			 * 
			 */

			es.setEsito(esito.getEsito());
			es.setMsg(esito.getMessage());

			String nomeFileCK = validazioneRendicontazioneDAO.getNomeFile(esito.getIdDocIndexDichiarazione());
			LOG.debug(prf + "nomeFileCK=" + nomeFileCK);

			String nomeFileRD = validazioneRendicontazioneDAO.getNomeFile(esito.getIdReportDettaglioDocSpesa());
			LOG.debug(prf + "nomeFileRD=" + nomeFileRD);

			String[] parametri = { esito.getIdDocIndexDichiarazione() + "", nomeFileCK,
					esito.getIdReportDettaglioDocSpesa() + "", nomeFileRD };
			es.setParams(parametri); // idDOcumentoIndex Checklist, idDocumentoIndexExcell, nomi dei files

			//////////////////// Logica metodo Codice del vecchio metodo
			//////////////////// //////////////////////////////

			/*
			 * non so se serve il return lascio qui nel caso servisse return
			 * Response.ok().entity(checkListDAO.saveCheckListInLocoHtmlDef(idUtente,
			 * idIride, idChecklist, idProgetto, statoChecklist, checkListHtml,
			 * verbali)).build();
			 */

			// checkListDAO.saveCheckListInLocoHtmlDef(idUtente, idIride,
			// idChecklist,idProgetto, statoChecklist, checkListHtml, verbali);

			/*
			 * // Multi-upload: ora ci possono essere piu allegati. Long idChecklist = null;
			 * FileDTO[] allegati = verbali; if(allegati != null) {
			 * LOG.info("checkListHtmlDTO.getAllegati() != null");
			 * 
			 * for (FileDTO fileDTO : allegati) {
			 * 
			 * //AllegatoCheckListClDTO allegatoCheckListClDTO =
			 * beanUtil.transform(checkListHtmlDTO, // AllegatoCheckListClDTO.class,
			 * MAP_CHECKLIST_IN_LOCO_DTO_TO_ALLEGATO_VERBALE_DTO);
			 * //allegatoCheckListInLocoDTO.setIdChecklist(idChecklist); commentato poiche
			 * sovrascritto sotto. EsitoSalvaModuloCheckListDTO DTO;
			 * 
			 * 
			 * AllegatoCheckListClDTO allegatoCheckListClDTO = new AllegatoCheckListClDTO();
			 * //allegatoCheckListClDTO.setIdChecklist( BigDecimal.valueOf(idChecklist) );
			 * allegatoCheckListClDTO.setIdProgetto( BigDecimal.valueOf(idProgetto) );
			 * allegatoCheckListClDTO.setCodiceProgetto(codiceProgetto);
			 * allegatoCheckListClDTO.setBytesDocumento(fileDTO.getBytes());
			 * allegatoCheckListClDTO.setNomeFile(fileDTO.getNome());
			 * 
			 * 
			 * LOG.
			 * info("before documentoManager.creaDocumento for allegatoCheckList con idChecklist "
			 * +idChecklist+" e nomeFile "+allegatoCheckListClDTO.getNomeFile());
			 * documentoManager.creaDocumentoCL(idUtente,
			 * allegatoCheckListClDTO,null,null,null,null); }
			 * 
			 * 
			 * 
			 * 
			 * esito.setEsito(true); } else { LOG.warn("bytesVerbale null"); }
			 */

		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}

		LOG.debug(prf + "END");
		LOG.info(prf + "END");

		return Response.ok().entity(es).build();
	}

	private FileDTO[] leggiFilesDaMultipart(List<InputPart> files) throws Exception {
		String prf = "[ChecklistServiceImpl::leggiFilesDaMultipart]";
		LOG.debug(prf + " BEGIN");

		FileDTO[] res = null;

		if (files != null) {

			res = new FileDTO[files.size()];
			try {

				String nomeFile = null;
				byte[] content;
				MultivaluedMap<String, String> header;
				int i = 0;

				for (InputPart inputPart : files) {

					header = inputPart.getHeaders();
					nomeFile = getFileName(header);
					content = IOUtils.toByteArray(inputPart.getBody(InputStream.class, null));

					LOG.debug(prf + " nomeFile orig =" + nomeFile);
					if (content != null) {
						LOG.debug(prf + " content length=" + content.length);
					} else {
						LOG.debug(prf + " content NULL");
					}

					String time = DateUtil.getTime(new java.util.Date(), "ddMMyyyyHHmmss");
					String newNome = "VCL_" + (i + 1) + "_" + time + "_" + nomeFile;
					LOG.debug(prf + " newNome =" + newNome);

					FileDTO f = new FileDTO();
					f.setNome(newNome);
					f.setBytes(content);

					res[i] = f;
					i++;
				}

			} catch (Exception e) {
				LOG.error(prf + " ERRORE: ", e);
				throw new Exception("Errore durante la lettura dei file da MultipartFormDataInput.", e);
			} finally {
				LOG.info(prf + " END");
			}
		}

		return res;
	}

	public static String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
		for (String value : contentDisposition) {
			if (value.trim().startsWith("filename")) {
				String[] name = value.split("=");
				if (name.length > 1) {
					return name[1].trim().replaceAll("\"", "");
				}
			}
		}
		return null;
	}

	
	@Override
	public Response getSumImportoErogProgettiPercettori(Long progrBandoLineaIntervento, Long idDichiarazioneDiSpesa,
			HttpServletRequest req) throws InvalidParameterException, Exception {
		String prf = "[ValidazioneRendicontazioneServiceImpl::getSumImportoErogProgettiPercettori] ";

		LOG.info(prf + "BEGIN");
		LOG.info(prf + "progrBandoLineaIntervento=" + progrBandoLineaIntervento);
		LOG.info(prf + "idDichiarazioneDiSpesa=" + idDichiarazioneDiSpesa);

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

		return Response.ok()
				.entity(validazioneRendicontazioneDAO.getSumImportoErogProgettiPercettori(userInfo.getIdUtente(),
						userInfo.getIdIride(), progrBandoLineaIntervento, idDichiarazioneDiSpesa))
				.build();
	}

}
