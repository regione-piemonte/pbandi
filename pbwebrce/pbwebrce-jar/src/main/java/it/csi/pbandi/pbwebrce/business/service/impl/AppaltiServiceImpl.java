/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.business.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.TreeBidiMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatididominio.GestioneDatiDiDominioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneappalti.AppaltoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneappalti.EsitoGestioneAppalti;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DatiGeneraliDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedocumentidispesa.MessaggioDTO;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebrce.business.service.AppaltiService;
import it.csi.pbandi.pbwebrce.dto.CodiceDescrizione;
import it.csi.pbandi.pbwebrce.dto.EsitoOperazioni;
import it.csi.pbandi.pbwebrce.dto.EsitoProceduraAggiudicazioneDTO;
import it.csi.pbandi.pbwebrce.dto.ExecResults;
import it.csi.pbandi.pbwebrce.dto.FiltroProcedureAggiudicazione;
import it.csi.pbandi.pbwebrce.dto.ModificaProcedureAggiudicazioneRequest;
import it.csi.pbandi.pbwebrce.dto.ProceduraAggiudicazione;
import it.csi.pbandi.pbwebrce.dto.ProceduraAggiudicazioneDTO;
import it.csi.pbandi.pbwebrce.dto.StepAggiudicazione;
import it.csi.pbandi.pbwebrce.dto.affidamenti.Appalto;
import it.csi.pbandi.pbwebrce.integration.dao.AppaltiDAO;
import it.csi.pbandi.pbwebrce.integration.dao.InizializzazioneDAO;
import it.csi.pbandi.pbwebrce.integration.request.CreaAppaltoRequest;
import it.csi.pbandi.pbwebrce.integration.request.CreaProcAggRequest;
import it.csi.pbandi.pbwebrce.integration.request.GetProcedureAggiudicazioneRequest;
import it.csi.pbandi.pbwebrce.util.BeanUtil;
import it.csi.pbandi.pbwebrce.util.Constants;
import it.csi.pbandi.pbwebrce.util.ErrorMessages;
import it.csi.pbandi.pbwebrce.util.StringUtil;

@Service
public class AppaltiServiceImpl implements AppaltiService {
	@Autowired
	protected BeanUtil beanUtil;
		
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	InizializzazioneDAO inizializzazioneDAO;
	
	@Autowired
	AppaltiDAO appaltiDAO;
	
	
	@Autowired
	GestioneDatiGeneraliBusinessImpl gestioneDatiGeneraliBusinessImpl;
	
	@Autowired
	GestioneDatiDiDominioBusinessImpl gestioneDatiDiDominioBusinessImpl;
	
	/// definizione costanti di outcome
	final String INSERISCIPROCEDURAAGGIUDICAZIONE_OUTCOME_CODE__OK_BEN = "OK_BEN";
	final String INSERISCIPROCEDURAAGGIUDICAZIONE_OUTCOME_CODE__OK_ISTR = "OK_ISTR";
	final String INSERISCIPROCEDURAAGGIUDICAZIONE_OUTCOME_CODE__OK_APP = "OK_APP";
	final String INSERISCIPROCEDURAAGGIUDICAZIONE_OUTCOME_CODE__ERROR = "ERROR";
	
	private static final BidiMap MAP_FROM_APPALTO_TO_APPALTODTO = new TreeBidiMap();
	private static final BidiMap MAP_FROM_APPALTODTO_TO_APPALTO;
	static {
		MAP_FROM_APPALTO_TO_APPALTODTO.put("idAppalto", "idAppalto");
		MAP_FROM_APPALTO_TO_APPALTODTO.put("dtPrevistaInizioLavori",
		"dtInizioPrevista");
		MAP_FROM_APPALTO_TO_APPALTODTO.put("dtConsegnaLavori",
		"dtConsegnaLavori");
		MAP_FROM_APPALTO_TO_APPALTODTO.put("dtFirmaContratto",
		"dtFirmaContratto");
		MAP_FROM_APPALTO_TO_APPALTODTO.put("importoContratto",
		"importoContratto");
		MAP_FROM_APPALTO_TO_APPALTODTO.put("bilancioPreventivo",
		"bilancioPreventivo");
		MAP_FROM_APPALTO_TO_APPALTODTO.put("oggettoAppalto", "oggettoAppalto");
		MAP_FROM_APPALTO_TO_APPALTODTO.put("idProceduraAggiudicazione",
		"idProceduraAggiudicaz");
		//FIXME saarebbe meglio che la definizione di descProcAgg fosse codProcAgg se questo corrisponde a tale valore sul db
		MAP_FROM_APPALTO_TO_APPALTODTO.put("proceduraAggiudicazione",
		"descProcAgg");
		MAP_FROM_APPALTO_TO_APPALTODTO.put("descrizioneProceduraAggiudicazione",
		"descrizioneProcAgg");
		MAP_FROM_APPALTO_TO_APPALTODTO.put("dtPubGUUE", "dtGuue");
		MAP_FROM_APPALTO_TO_APPALTODTO.put("dtPubGURI", "dtGuri");
		MAP_FROM_APPALTO_TO_APPALTODTO.put("dtPubQuotidianiNazionali",
		"dtQuotNazionali");
		MAP_FROM_APPALTO_TO_APPALTODTO.put("dtPubStazioneAppaltante",
		"dtWebStazAppaltante");
		MAP_FROM_APPALTO_TO_APPALTODTO.put("dtPubLLPP", "dtWebOsservatorio");
		// }L{ PBANDI-1884
		MAP_FROM_APPALTO_TO_APPALTODTO.put("idTipologiaAppalto", "idTipologiaAppalto");
		MAP_FROM_APPALTO_TO_APPALTODTO.put("impresaAppaltatrice", "impresaAppaltatrice");
		MAP_FROM_APPALTO_TO_APPALTODTO.put("identificativoIntervento", "interventoPisu");

		MAP_FROM_APPALTO_TO_APPALTODTO.put("importoRibassoAsta", "importoRibassoAsta");
		MAP_FROM_APPALTO_TO_APPALTODTO.put("percentualeRibassoAsta", "percentualeRibassoAsta");
		MAP_FROM_APPALTO_TO_APPALTODTO.put("sopraSoglia", "sopraSoglia");
		MAP_FROM_APPALTO_TO_APPALTODTO.put("idTipoAffidamento", "idTipoAffidamento");
		MAP_FROM_APPALTO_TO_APPALTODTO.put("idTipoPercettore", "idTipoPercettore");

		MAP_FROM_APPALTODTO_TO_APPALTO = MAP_FROM_APPALTO_TO_APPALTODTO
		.inverseBidiMap();
	}
	
	private static final BidiMap MAP_FROM_STEPAGGIUDICAZIONE_TO_STEPAGGIUDICAZIONEDTO = new TreeBidiMap();
	private static final BidiMap MAP_FROM_STEPAGGIUDICAZIONEDTO_TO_STEPAGGIUDICAZIONE;
	static {
		MAP_FROM_STEPAGGIUDICAZIONE_TO_STEPAGGIUDICAZIONEDTO.put("dtEffettiva", "dtEffettiva");
		MAP_FROM_STEPAGGIUDICAZIONE_TO_STEPAGGIUDICAZIONEDTO.put("dtPrevista", "dtPrevista");
		MAP_FROM_STEPAGGIUDICAZIONE_TO_STEPAGGIUDICAZIONEDTO.put("id", "idStepAggiudicazione");
		MAP_FROM_STEPAGGIUDICAZIONE_TO_STEPAGGIUDICAZIONEDTO.put("idMotivoScostamento", "idMotivoScostamento");
		MAP_FROM_STEPAGGIUDICAZIONE_TO_STEPAGGIUDICAZIONEDTO.put("importoStep", "importo");
		MAP_FROM_STEPAGGIUDICAZIONE_TO_STEPAGGIUDICAZIONEDTO.put("label", "descStepAggiudicazione");
		MAP_FROM_STEPAGGIUDICAZIONEDTO_TO_STEPAGGIUDICAZIONE = MAP_FROM_STEPAGGIUDICAZIONE_TO_STEPAGGIUDICAZIONEDTO
				.inverseBidiMap();
	}
	
	@Override
	public Response getAppalti(HttpServletRequest req, Appalto filtro , Long idProgetto) throws UtenteException, Exception {
		String prf = "[AppaltiServiceImpl::getAppalti]";
		LOG.info(prf + " BEGIN");
		
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			AppaltoDTO filtroDTO = beanUtil.transform(filtro, AppaltoDTO.class, MAP_FROM_APPALTO_TO_APPALTODTO);
			filtroDTO.setIdProgetto(idProgetto);
			
			AppaltoDTO[] dto  = appaltiDAO.findAppalti(idUtente, idIride, filtroDTO);
			ArrayList<Appalto> appalti = beanUtil.transformToArrayList(dto, Appalto.class,
					MAP_FROM_APPALTODTO_TO_APPALTO);
	
			LOG.info(prf + " END");
			return Response.ok().entity(appalti).build();
		} catch(Exception e) {
			throw e;
		}
		
	}


	@Override
	public Response getCodiceProgetto(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[AppaltiServiceImpl::getCodiceProgetto]";
		LOG.debug(prf + " BEGIN");
		try {
			if(idProgetto == null) { return inviaErroreBadRequest("Parametro mancato: ?[idProgetto]"); }
			String codiceProgetto = appaltiDAO.findCodiceProgetto(idProgetto);

			LOG.debug(prf + " END");
			return Response.ok().entity(codiceProgetto).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	@Override
	public Response getTipologieAppalti(HttpServletRequest req)
			throws UtenteException, Exception {
		String prf = "[AppaltiServiceImpl::getTipologieAppalti]";
		LOG.debug(prf + " BEGIN");
		
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			List<CodiceDescrizione> tipologieAppalti = appaltiDAO.findTipologieAppalti(userInfo);
			
			LOG.debug(prf + " END");
			return Response.ok().entity(tipologieAppalti).build();
		} catch(Exception e) {
			throw e;
		}
		
	}
	


	@Override
	public Response getTipologieProcedureAggiudicazione(HttpServletRequest req, Long idProgetto)
			throws UtenteException, Exception {
		String prf = "[AppaltiServiceImpl::getTipologieProcedureAggiudicazione]";
		LOG.debug(prf + " BEGIN");
		
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			DatiGeneraliDTO datiGenerali = gestioneDatiGeneraliBusinessImpl.caricaDatiGenerali(userInfo.getIdUtente(), userInfo.getIdIride(), 
					idProgetto, null);
			Long progBandoLinea = datiGenerali.getIdBandoLinea();
			if(progBandoLinea == null) return inviaRispostaVuota("Errore durante il recupero di progBandoLinea dai dati generali.");
			
			List<CodiceDescrizione> tipologieAppalti = appaltiDAO.findTipologieProcedureAggiudicazione(userInfo, progBandoLinea);
			
			LOG.debug(prf + " END");
			return Response.ok().entity(tipologieAppalti).build();
		} catch(Exception e) {
			throw e;
		}
	}



	@Override
	public Response getAllProcedureAggiudicazione(HttpServletRequest req,
			GetProcedureAggiudicazioneRequest getProcAggiudicazRequest) throws UtenteException, Exception {
		String prf = "[AppaltiServiceImpl::getAllProcedureAggiudicazione]";
		LOG.debug(prf + " BEGIN");
		
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			FiltroProcedureAggiudicazione filtro = getProcAggiudicazRequest.getFiltro();
			Long idProgetto = getProcAggiudicazRequest.getIdProgetto();
			ProceduraAggiudicazioneDTO f = new ProceduraAggiudicazioneDTO();
			f.setIdTipologiaAggiudicaz(filtro.getIdTipologiaAggiudicaz());
			f.setCodProcAgg((StringUtil.isEmpty(filtro.getCodProcAgg()) ? null : filtro.getCodProcAgg()));
			f.setCigProcAgg((StringUtil.isEmpty(filtro.getCigProcAgg()) ? null : filtro.getCigProcAgg()));
			f.setIdProgetto(idProgetto);
			
			ProceduraAggiudicazioneDTO[] procedure = appaltiDAO.findAllProcedureAggiudicazione(userInfo.getIdUtente(), 
					userInfo.getIdIride(), f);
			ArrayList<ProceduraAggiudicazione> res = new ArrayList<ProceduraAggiudicazione>();
			Boolean isModificabile = true;
			if (procedure != null) {
				for (ProceduraAggiudicazioneDTO p : procedure) {
					ProceduraAggiudicazione proc = beanUtil.transform(p, ProceduraAggiudicazione.class);
					proc.setIsModificabile(isModificabile);
					res.add(proc);
				} 
			}
			LOG.debug(prf + " END");
			return Response.ok().entity(res).build();
		} catch(Exception e) {
			throw e;
		}
	}


	@Override
	public Response getDettaglioProceduraAggiudicazione(HttpServletRequest req, Long idProcedura)
			throws UtenteException, Exception {
		String prf = "[AppaltiServiceImpl::getDettaglioProceduraAggiudicazione]";
		LOG.debug(prf + " BEGIN");
		
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
					
			ProceduraAggiudicazioneDTO procedura = appaltiDAO.findDettaglioProceduraAggiudicazione(userInfo.getIdUtente(), userInfo.getIdIride(), idProcedura);
			
			ProceduraAggiudicazione proc = beanUtil.transform(procedura, ProceduraAggiudicazione.class);
			proc.setIter(beanUtil.transform(procedura.getIter(), StepAggiudicazione.class));
			List<StepAggiudicazione> res = new ArrayList<StepAggiudicazione>();
			for(StepAggiudicazione dto: procedura.getIter()) {
//				StepAggiudicazione s = new StepAggiudicazione();
//				s.setDescStepAggiudicazione(dto.getDescStepAggiudicazione());
//				s.setIdMotivoScostamento(dto.getIdMotivoScostamento());
//				s.setIdStepAggiudicazione(dto.getIdStepAggiudicazione());
//				s.setImporto(dto.getImporto());
//				s.setDtPrevista(dto.getDtPrevista());
//				s.setDtEffettiva(dto.getDtEffettiva());
//				res.add(s);
				
				LOG.debug( prf + " step : " + dto.getDtPrevista() );
			}
//			StepAggiudicazione[] s = new StepAggiudicazione[res.size()];
//			
//			proc.setIter(res.toArray(s));
//			
//			proc.setIsModificabile(true);
				
			
			LOG.debug(prf + " END");
			return Response.ok().entity(proc).build();
		} catch(Exception e) {
			throw e;
		}
	}


	@Override
	public Response modificaProceduraAggiudicazione(HttpServletRequest req,
			ModificaProcedureAggiudicazioneRequest modificaProcAggRequest) throws UtenteException, Exception {
		String prf = "[AppaltiServiceImpl::modificaProceduraAggiudicazione]";
		LOG.debug(prf + " BEGIN");
		
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			
			ProceduraAggiudicazione dto = modificaProcAggRequest.getProceduraAggiudicazione();
			Long idProgetto = modificaProcAggRequest.getIdProgetto();
			ProceduraAggiudicazioneDTO procedura = beanUtil.transform(dto, ProceduraAggiudicazioneDTO.class);
			
			//StepAggiudicazione[] steps = beanUtil.transform(dto.getIter(), StepAggiudicazione.class, MAP_FROM_STEPAGGIUDICAZIONE_TO_STEPAGGIUDICAZIONEDTO);
			StepAggiudicazione[] steps = dto.getIter();
			
			procedura.setIdProgetto(idProgetto);
			procedura.setIter(steps);					
			
			EsitoProceduraAggiudicazioneDTO esito = new EsitoProceduraAggiudicazioneDTO();
			try {
				esito = appaltiDAO.modificaProceduraAggiudicazione(idUtente, idIride, procedura);
			} catch (Exception e) {
				esito.setEsito(Boolean.FALSE);
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(ErrorMessages.ERRORE_SERVER);
				esito.setMsgs(new MessaggioDTO[] { msg });
			}
			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}


	

	
	@Override
	public Response creaAppalto(HttpServletRequest req, CreaAppaltoRequest creaRequest)
			throws UtenteException, Exception {
		String prf = "[AppaltiServiceImpl::creaAppalto]";
		LOG.debug(prf + " BEGIN");
		
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Appalto appalto = creaRequest.getAppalto();
			Long idProcedura = creaRequest.getIdProceduraAggiudicaz();
			
			AppaltoDTO appaltoDTO = beanUtil.transform(appalto, AppaltoDTO.class, MAP_FROM_APPALTO_TO_APPALTODTO);
			appaltoDTO.setIdProceduraAggiudicaz(idProcedura);
			
			EsitoGestioneAppalti esito = appaltiDAO.creaAppalto(idUtente, idIride, appaltoDTO);
			if (!esito.getEsito()) {
				throw new Exception(esito.getMessage());
			}
			appalto = beanUtil.transform(esito.getAppalto(), Appalto.class, MAP_FROM_APPALTODTO_TO_APPALTO);
			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	
	}




	@Override
	public Response getStepsAggiudicazione(HttpServletRequest req, Long idTipologiaAggiudicaz)
			throws UtenteException, Exception {
		String prf = "[AppaltiServiceImpl::getStepsAggiudicazione]";
		LOG.debug(prf + " BEGIN");
		
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			
			if (idTipologiaAggiudicaz != null) {
				Decodifica[] steps = gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride, gestioneDatiDiDominioBusinessImpl.STEP_AGGIUDICAZIONE, "idTipologiaAggiudicaz", idTipologiaAggiudicaz.toString());
				ArrayList<StepAggiudicazione> result = new ArrayList<StepAggiudicazione>();
				for (Decodifica step : steps) {
					StepAggiudicazione temp = new StepAggiudicazione();
					temp.setId(step.getId().toString());
					temp.setLabel(step.getDescrizione());
					temp.setDescStepAggiudicazione(step.getDescrizione());
					result.add(temp);
				}
				LOG.debug(prf + " END");
				return Response.ok().entity(result).build();
			}
			else {
				LOG.debug(prf + " END");
				return null;
			}
			
				
		} catch(Exception e) {
			throw e;
		}
	}
	

	@Override
	public Response creaProceduraAggiudicazione(HttpServletRequest req, CreaProcAggRequest creaRequest)
			throws UtenteException, Exception {
		String prf = "[AppaltiServiceImpl::creaProceduraAggiudicazione]";
		LOG.debug(prf + " BEGIN");
		
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
					
			ProceduraAggiudicazione dto = creaRequest.getProceduraAggiudicazione();
			Long idProgettto = creaRequest.getIdProgetto();
					
			ExecResults result = new ExecResults();
			EsitoProceduraAggiudicazioneDTO esito = new EsitoProceduraAggiudicazioneDTO();
			// validare i campi della procedura da inserire (obbligatorieta', // lunghezza, ecc)
			//VALIDAZIONE SPOSTATA LATO ANGULAR
//			Map<String, String> errors = validaProceduraAggiudicazione(dto);
//			if (!errors.isEmpty()) {
//				result.setFldErrors(errors);
//				result.setResultCode(INSERISCIPROCEDURAAGGIUDICAZIONE_OUTCOME_CODE__ERROR);
//				esito.setEsito(false);
//				
//				List<MessaggioDTO> msgs = new ArrayList<MessaggioDTO>() ;
//				for (Map.Entry<String, String> entry : errors.entrySet()) {
//					MessaggioDTO msg = new MessaggioDTO();
//					msg.setMsgKey(entry.getValue());
//					msgs.add(msg);
//			    }
//				
//				MessaggioDTO[] s = new MessaggioDTO[msgs.size()];
//				esito.setMsgs( msgs.toArray(s));
//			} else {
				// chiamo il servizio di inserimento
			
			try {
				esito = appaltiDAO.creaProceduraAggiudicazione(idUtente, idIride, dto, idProgettto);
			} catch (Exception e) {
				esito.setEsito(Boolean.FALSE);
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(ErrorMessages.ERRORE_SERVER);
				esito.setMsgs(new MessaggioDTO[] { msg });
			}
//				
//			}
			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();					
			
		} catch(Exception e) {
			throw e;
		}
	}

	
	private Map<String, String> validaProceduraAggiudicazione(ProceduraAggiudicazione dto) {
		String prf = "[AppaltiServiceImpl::validaProceduraAggiudicazione]";
		LOG.debug(prf + " START");
		
		try {
			Map<String, String> errors = new Hashtable<String, String>();
			
			if (dto.getIdTipologiaAggiudicaz() == null || dto.getIdTipologiaAggiudicaz().toString().length() == 0) {
				errors.put("appDataproceduraAggiudicazioneSelezionata.idTipologiaAggiudicaz", ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO);
			}
			
			// }L{ PBANDI-1884 aggiunto codIdGara
			// FIXME usare il Checker (un giorno)
			if (StringUtil.isEmpty(dto.getCodProcAgg()) 
					&& StringUtil.isEmpty(dto.getCigProcAgg())) {
				errors.put("appDataproceduraAggiudicazioneSelezionata.codProcAgg", ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO);
				errors.put("appDataproceduraAggiudicazioneSelezionata.cigProcAgg", ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO);
			} else if (!StringUtil.isEmpty(dto.getCodProcAgg()) && dto.getCodProcAgg().trim().length() > 30) {
				errors.put("appDataproceduraAggiudicazioneSelezionata.codProcAgg",ErrorMessages.DIMENSIONE_CAMPO_STRINGA);
			} else if (!StringUtil.isEmpty(dto.getCigProcAgg()) && dto.getCigProcAgg().trim().length() > 10) {
				errors.put("appDataproceduraAggiudicazioneSelezionata.cigProcAgg", ErrorMessages.DIMENSIONE_CAMPO_STRINGA);
			}
			
			if (dto.getImporto() == null) {
				errors.put("appDataproceduraAggiudicazioneSelezionata.importo", ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO);
			} else if (dto.getImporto() < 0) {
				errors.put("appDataproceduraAggiudicazioneSelezionata.importo", ErrorMessages.IMPORTI_NON_VALIDI);
			} else {
				// VN. Fix PBandi-1586
				Integer maxIntegerDigits = new Integer(15);
				if (!it.csi.pbandi.pbwebrce.util.NumberUtil.isImporto(dto.getImporto().toString(), maxIntegerDigits)) {
					errors.put("appDataproceduraAggiudicazioneSelezionata.importo",ErrorMessages.FORMATO_NUMERO_LUNGHEZZA_ERRATO);
				}
			}
			
			if (StringUtil.isEmpty(dto.getDescProcAgg())) {
				errors.put("appDataproceduraAggiudicazioneSelezionata.descProcAgg", ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO);
			} else if (dto.getDescProcAgg().trim().length() > 255) {
				errors.put("appDataproceduraAggiudicazioneSelezionata.descProcAgg", ErrorMessages.DIMENSIONE_CAMPO_STRINGA);
			}
			
			if(dto.getIter()!=null && dto.getIter().length > 0) {
				List<StepAggiudicazione> iter = Arrays.asList(dto.getIter()) ;
				if(iter != null && iter.size() > 0) {
					for (int i = 0; i<iter.size(); i++) {
						StepAggiudicazione item = iter.get(i);
						if(item.getDtPrevista() == null) {
							errors.put("appDatastepsAggiudicazione["+i+"].dtPrevista",ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO);
						} else if(!it.csi.pbandi.pbwebrce.util.DateUtil.isValidDate(item.getDtPrevista().toString())) {
							errors.put("appDatastepsAggiudicazione["+i+"].dtPrevista", ErrorMessages.FORMATO_DATA);
						}
						if(item.getDtEffettiva() == null) {
							errors.put("appDatastepsAggiudicazione["+i+"].dtEffettiva",ErrorMessages.ERRORE_CAMPO_OBBLIGATORIO);
						} else if(!it.csi.pbandi.pbwebrce.util.DateUtil.isValidDate(item.getDtEffettiva().toString())) {
							errors.put("appDatastepsAggiudicazione["+i+"].dtEffettiva", ErrorMessages.FORMATO_DATA);
						}
					}
				}
			}
		
			
			LOG.debug(prf + " END");
			return errors;
		} catch(Exception e) {
			LOG.error(" Errore durante la validazione della procedura: " + e.getMessage());
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
	private Response inviaRispostaVuota(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.OK).entity(esito).type( MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response eliminaAppalto(HttpServletRequest req, Long idAppalto) throws UtenteException, Exception {
		String prf = "[AppaltiServiceImpl::eliminaAppalto]";
		LOG.debug(prf + " BEGIN");
		try {
			if(idAppalto == null) { return inviaErroreBadRequest("Parametro mancato: ?[idAppalto]"); }			
			EsitoGestioneAppalti esito = appaltiDAO.eliminaAppalto(idAppalto);			
			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	@Override
	public Response eliminazioneAppaltoAbilitata(String codiceRuolo, HttpServletRequest req ) throws UtenteException, Exception {
		String prf = "[AppaltiServiceImpl::eliminazioneAppaltoAbilitata]";
		LOG.debug(prf + " BEGIN");
		LOG.info(prf + " codiceRuolo = "+codiceRuolo);
		try {
			if(StringUtils.isBlank(codiceRuolo)) { return inviaErroreBadRequest("Parametro mancato: ?[codiceRuolo]"); }
			
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			Long idSoggetto = userInfo.getIdSoggetto();
			
			Boolean esito = appaltiDAO.eliminazioneAppaltoAbilitata(idSoggetto, idUtente, codiceRuolo);			
			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}

}
