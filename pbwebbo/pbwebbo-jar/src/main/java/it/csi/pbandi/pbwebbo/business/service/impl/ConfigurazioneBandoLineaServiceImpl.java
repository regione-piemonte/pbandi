/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.business.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.csi.util.DatiMessaggio;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.business.intf.MessageKey;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionebackoffice.GestioneBackofficeBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatididominio.GestioneDatiDiDominioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionetemplates.GestioneTemplatesBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.AreaScientificaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.CheckListBandoLineaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.CostantiBandoLineaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.DocPagamBandoLineaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.EnteDiCompetenzaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.EnteDiCompetenzaRuoloAssociatoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.EsitoAssociazione;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.GestioneBackOfficeEsitoGenerico;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.IdDescBreveDescEstesaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.LineaDiInterventiDaModificareDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.RegolaAssociataDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.SettoreEnteDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.SezioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.TipoDiAiutoAssociatoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionetemplates.EsitoAnteprimaTemplateDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionetemplates.MessaggioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionetemplates.TemplateDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.SezioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.util.json.JSONArray;
import it.csi.pbandi.pbservizit.pbandiutil.common.util.json.JSONObject;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebbo.business.impl.GestioneBackofficeBusinessWebboImpl;
import it.csi.pbandi.pbwebbo.business.service.ConfigurazioneBandoLineaService;
import it.csi.pbandi.pbwebbo.dto.CodiceDescrizioneDTO;
import it.csi.pbandi.pbwebbo.dto.GenericResponseDTO;
import it.csi.pbandi.pbwebbo.dto.configurazionebandolinea.CheclistDTO;
import it.csi.pbandi.pbwebbo.dto.configurazionebandolinea.DatiModificaTemplate;
import it.csi.pbandi.pbwebbo.dto.configurazionebandolinea.ModelloDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.AnagraficaDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.BancaSuggestVO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.EstremiBancariDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.EstremiBancariEstesiDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.EstremiContoDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.IbanDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.InsertEstremiBancariDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.ModAgevEstremiBancariDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.ModalitaAgevolazioneDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.SendToAmmContDTO;
import it.csi.pbandi.pbwebbo.dto.monitoraggioTempi.ParametriMonitoraggioTempiAssociatiDTO;
import it.csi.pbandi.pbwebbo.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbwebbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbwebbo.util.Constants;
import it.csi.pbandi.pbwebbo.util.ErrorMessages;
import it.csi.pbandi.pbwebbo.integration.dao.impl.ConfigurazioneBandoLineaDAOImpl;

@Service
public class ConfigurazioneBandoLineaServiceImpl implements ConfigurazioneBandoLineaService {
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	public static final String PROGRAMMAZIONE_PRE_2016 = "PRE_2016";
	
	@Autowired
	private GestioneBackofficeBusinessImpl gestioneBackofficeSrv;
	
	@Autowired
	private GestioneDatiDiDominioBusinessImpl gestioneDatiDiDominioSrv;
	
	@Autowired
	private GestioneTemplatesBusinessImpl gestioneTemplatesBusinessImpl;

	@Autowired
	private GestioneBackofficeBusinessWebboImpl gestioneTemplatesBusinessIWebbompl;
		
	@Autowired
	private ConfigurazioneBandoLineaDAOImpl configurazioneBandoDAOImpl;
	
	@Autowired
	private AmministrativoContabileServiceImpl amministrativoContabile;
	
	@Autowired
	private GestioneBackofficeBusinessImpl gestioneBackofficeBusinessImpl;
	
	private static final Long ID_ENTE_COMPETENZA = 15L;
	
	
	private static final Long ID_SERVIZIO = 2L;
	private static final Long ID_ENTITA = 128L;
	private static final String ESITO = "OK" ;
	
	
	
	
	
	
	
	@Override
	public Response findEnti(Long idU, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findEnti]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		EnteDiCompetenzaDTO[]  enteDiCompetenzaDTO = null;
				
		try {
			
			enteDiCompetenzaDTO = gestioneBackofficeSrv.findEntiDiCompetenza(idU, userInfo.getIdIride());
			LOG.debug("Elementi trovati = "+enteDiCompetenzaDTO.length);
			
		}catch (Exception e) {
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			enteDiCompetenzaDTO = new EnteDiCompetenzaDTO[1];
			enteDiCompetenzaDTO[0] = new EnteDiCompetenzaDTO();
			enteDiCompetenzaDTO[0].setIdEnteCompetenza(null);
			enteDiCompetenzaDTO[0].setDescEnte("Errore: " +e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(enteDiCompetenzaDTO).build();
		
	}

	@Override
	public Response findSettore(Long idU, Long idEnte, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findSettore]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idEnte = "+idEnte);
		
		ResponseCodeMessage resp = new ResponseCodeMessage();
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		SettoreEnteDTO[]  settoreEnteDTO = null;
				
		try {
			
			settoreEnteDTO = gestioneBackofficeSrv.getSettori(idU, userInfo.getIdIride(), idEnte);
			if(settoreEnteDTO != null) {
				LOG.debug("Elementi trovati = "+settoreEnteDTO.length);
			}else {
				LOG.debug("Non sono stati trovati settori per l'ente = "+idEnte);
				settoreEnteDTO = new SettoreEnteDTO[1];
				settoreEnteDTO[0] = new SettoreEnteDTO();
				settoreEnteDTO[0].setDescBreveSettore("ko");
				settoreEnteDTO[0].setDescSettore("Non sono stati trovati settori per l'ente = "+idEnte);
			}
			
			
		}catch (Exception e) {
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			resp.setCode("Errore in findSettore");
			resp.setMessage("Errore: " +e.getMessage());
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(settoreEnteDTO).build();
		
	}

	
	@Override
	public Response findRuoli(Long idU, Long idLineaDiIntervento, Long progrBandoLineaIntervento, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findRuoli]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idLineaDiIntervento = "+idLineaDiIntervento+", progrBandoLineaIntervento = "+progrBandoLineaIntervento);
		
		ResponseCodeMessage resp = new ResponseCodeMessage();
		List<Decodifica> ruoliList = new ArrayList<Decodifica>();
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		Decodifica[]  ruoli = null;
				
		try {
			
			if(idLineaDiIntervento == null && progrBandoLineaIntervento != null) {
				LineaDiInterventiDaModificareDTO lineaDiInterventiDaModificareDTO = gestioneBackofficeSrv.findLineaDiIntervento(idU, userInfo.getIdIride(), progrBandoLineaIntervento);
				idLineaDiIntervento = lineaDiInterventiDaModificareDTO.getIdNormativa();
			}
			
			//Se già associati questi ruoli non vanno restituiti
			List<String> ruoliEnteCompetenzaObbligatori = new ArrayList<String>();
			ruoliEnteCompetenzaObbligatori.add("DESTINATARIO");
			ruoliEnteCompetenzaObbligatori.add("PROGRAMMATORE");
			ruoliEnteCompetenzaObbligatori.add("RESPONSABILE");
			
			//Prendo gli enti già associati per controllare se tra di loro sono già presenti i ruoli obbligatori difiniti nella lista "ruoliEnteCompetenzaObbligatori".
			//Se presenti vanno cancellati dalla lista che ritorna al front-end
			EnteDiCompetenzaRuoloAssociatoDTO[]  entiAssociati = gestioneBackofficeSrv.findEntiDiCompetenzaAssociati(idU, userInfo.getIdIride(), progrBandoLineaIntervento);
			LOG.debug("Elementi trovati = "+entiAssociati.length);
			List<EnteDiCompetenzaRuoloAssociatoDTO> entiAssociatiList = Arrays.asList(entiAssociati);
				

			ruoli = gestioneDatiDiDominioSrv.findDecodificheMultiProgr(idU, userInfo.getIdIride(), GestioneDatiDiDominioSrv.RUOLO_ENTE_COMPETENZA, idLineaDiIntervento);
			LOG.debug("Elementi trovati = "+ruoli.length);
			
			ruoliList = new LinkedList<Decodifica>(Arrays.asList(ruoli));
			List<Decodifica> toRemove = new ArrayList<Decodifica>();
			for(EnteDiCompetenzaRuoloAssociatoDTO enteAssociato: entiAssociatiList) {
				
				if (ruoliEnteCompetenzaObbligatori.contains(enteAssociato.getRuolo())) {
					
					for(Decodifica ruolo : ruoliList) {
						if(ruolo.getDescrizioneBreve().equals(enteAssociato.getRuolo())) {
							toRemove.add(ruolo);
						}
					}
					
				}
				
					
			}
			
			ruoliList.removeAll(toRemove);
			
		}catch (UnrecoverableException e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			resp.setCode("Errore in GestioneDatiDiDominioBusinessImpl -> findDecodificheMultiProgr");
			resp.setMessage("Errore: " +e.getMessage());
		}catch (Exception e) {
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			resp.setCode("Errore in findRuoli");
			resp.setMessage("Errore: " +e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(ruoliList).build();
		
	}
	

	@Override
	public Response findEntiDiCompetenzaAssociati(Long idU, Long progrBandoLineaIntervento, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findEntiDiCompetenzaAssociati]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento);
		
		ResponseCodeMessage resp = new ResponseCodeMessage();
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		EnteDiCompetenzaRuoloAssociatoDTO[]  enteDiCompetenzaRuoloAssociatoDTO = null;
				
		try {
			
			enteDiCompetenzaRuoloAssociatoDTO = gestioneBackofficeSrv.findEntiDiCompetenzaAssociati(idU, userInfo.getIdIride(), progrBandoLineaIntervento);
			LOG.debug("Elementi trovati = "+enteDiCompetenzaRuoloAssociatoDTO.length);
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			resp.setCode("Errore in findEntiDiCompetenzaAssociati");
			resp.setMessage("Errore: " +e.getMessage());
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(enteDiCompetenzaRuoloAssociatoDTO).build();
		
	}

	@Override
	public Response eliminaEnteDiCompetenzaAssociato(Long idU, Long progrBandoLineaEnteComp, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: eliminaEnteDiCompetenzaAssociato]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaEnteComp = "+progrBandoLineaEnteComp);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
				
		try {
			
			esito = gestioneBackofficeSrv.eliminaEnteDiCompetenzaAssociato(idU, userInfo.getIdIride(), progrBandoLineaEnteComp);
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			esito.setEsito(false);
			esito.setMessage("Errore in eliminaEnteDiCompetenzaAssociato: "+e.getMessage());
		}
		
		LOG.debug("Esito = "+esito.getEsito() + ", descrizione = "+esito.getMessage());
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}

	@Override
	public Response associaEnteDiCompetenza(Long idU, Long progrBandoLineaIntervento, Long idEnte, Long idRuoloEnte,
											Long idSettoreEnte, String pec, String mail, String tipoProgrammazione, 
											Long conservazioneCorrente, Long conservazioneGenerale, String flagMonitoraggioTempi, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: associaEnteDiCompetenza]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento+", idEnte = "+idEnte+", idRuoloEnte = "+idRuoloEnte+""
				+ ", idSettoreEnte = "+idSettoreEnte+", pec = "+pec + ", mail = "+mail+", tipoProgrammazione = "+tipoProgrammazione+", "
				+ "conservazioneCorrente = "+conservazioneCorrente+", conservazioneGenerale = "+conservazioneGenerale);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		EsitoAssociazione esito = null;
				
		try {
			
			esito = gestioneBackofficeSrv.associaEnteDiCompetenza(idU, userInfo.getIdIride(), progrBandoLineaIntervento, idEnte, idRuoloEnte, idSettoreEnte, pec, 
																  mail, tipoProgrammazione, conservazioneCorrente, conservazioneGenerale);
			boolean saveFlagTempi = false;
			// salvataggio dentro  la tabella r_modalita agev del flag agevolazione
			if(flagMonitoraggioTempi!=null) {				
				 saveFlagTempi = configurazioneBandoDAOImpl.salvaFlagMonitoraggioTempi(progrBandoLineaIntervento, flagMonitoraggioTempi);
			}
			if(saveFlagTempi) {
				LOG.info("salvataggio flag Monitoraggio tempi avvenuto con successo!");
			}
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			esito.setEsito(false);
			esito.setMessage("Errore in associaEnteDiCompetenza: "+e.getMessage());
		}
		
		LOG.debug("Esito = "+esito.getEsito() + ", descrizione = "+esito.getMessage());
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}
	
	
	@Override
	public Response isRuoliEnteObbligatoriAssociati(Long idBandoLinea, ArrayList<EnteDiCompetenzaRuoloAssociatoDTO> ruoliAssociati, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: isRuoliEnteObbligatoriAssociati]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idBandoLinea = "+idBandoLinea+", ruoliAssociati.size() = "+ruoliAssociati.size());

		ResponseCodeMessage resp = new ResponseCodeMessage();
				
		try {
			
			boolean isRuoliAssociati = gestioneBackofficeSrv.isRuoliEnteObbligatoriAssociati(idBandoLinea, ruoliAssociati);
			LOG.debug("isRuoliAssociati = "+isRuoliAssociati);
			if(isRuoliAssociati) {
				resp.setCode("OK");
				resp.setMessage("Ruoli obbligatori associati");
			}else {
				resp.setCode("KO");
				resp.setMessage("Ruoli obbligatori non associati");
			}
			
		}catch (Exception e) {
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			resp.setCode("ERROR");
			resp.setMessage("Message: "+e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(resp).build();
		
	}
	
	
	@Override
	public Response findTipiDiAiutoDaAssociare(Long idU, Long idLineaDiIntervento, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findTipiDiAiutoDaAssociare]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idLineaDiIntervento = "+idLineaDiIntervento);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		Decodifica[]  indicatoriQSN = null;
				
		try {
			
			indicatoriQSN = gestioneDatiDiDominioSrv.findDecodificheMultiProgr(idU, userInfo.getIdIride(), GestioneDatiDiDominioSrv.TIPO_AIUTO, idLineaDiIntervento);
			LOG.debug("Elementi trovati = "+indicatoriQSN.length);
			
		}catch (UnrecoverableException e) {
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			indicatoriQSN = new Decodifica[1];
			indicatoriQSN[0] = new Decodifica();
			indicatoriQSN[0].setDescrizioneBreve("Errore in findDecodificheMultiProgr ");
			indicatoriQSN[0].setDescrizione("Errore: " +e.getMessage());
		}catch (Exception e) {
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			indicatoriQSN = new Decodifica[1];
			indicatoriQSN[0] = new Decodifica();
			indicatoriQSN[0].setDescrizioneBreve("Errore in findTipiDiAiutoDaAssociare ");
			indicatoriQSN[0].setDescrizione("Errore: " +e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(indicatoriQSN).build();
		
	}
	

	@Override
	public Response associaTipiDiAiuto(Long idU, Long progrBandoLineaIntervento, String idTipiDiAiuto, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: associaTipiDiAiuto]";
		LOG.debug(prf + " BEGIN");
		
		String[] idTipiDiAiutoString = idTipiDiAiuto.split(";");
		
		Long[] idTipiDiAiutoArray = new Long[idTipiDiAiutoString.length];
		for(int i = 0; i < idTipiDiAiutoString.length; i++) {
			idTipiDiAiutoArray[i] = Long.parseLong(idTipiDiAiutoString[i]);
		}
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento+", idTipiDiAiuto = "+idTipiDiAiuto);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		EsitoAssociazione esito = null;
				
		try {
			
			esito = gestioneBackofficeSrv.associaTipiDiAiuto(idU, userInfo.getIdIride(), progrBandoLineaIntervento, idTipiDiAiutoArray);
				
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			esito.setEsito(false);
			esito.setMessage("Errore in associaEnteDiCompetenza: "+e.getMessage());
		}
		
		LOG.debug("Esito = "+esito.getEsito() + ", descrizione = "+esito.getMessage());
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}

	@Override
	public Response eliminaTipiDiAiuto(Long idU, Long progrBandoLineaIntervento, String idTipiDiAiuto, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: eliminaTipiDiAiuto]";
		LOG.debug(prf + " BEGIN");
		
		String[] idTipiDiAiutoString = idTipiDiAiuto.split(";");
		
		Long[] idTipiDiAiutoArray = new Long[idTipiDiAiutoString.length];
		for(int i = 0; i < idTipiDiAiutoString.length; i++) {
			idTipiDiAiutoArray[i] = Long.parseLong(idTipiDiAiutoString[i]);
		}
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento+", idTipiDiAiuto = "+idTipiDiAiuto);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		GestioneBackOfficeEsitoGenerico esito = null;
				
		try {
			
			esito = gestioneBackofficeSrv.eliminaTipiDiAiuto(idU, userInfo.getIdIride(), progrBandoLineaIntervento, idTipiDiAiutoArray);
					
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			esito.setEsito(false);
			esito.setMessage("Errore in eliminaTipiDiAiuto: "+e.getMessage());
		}
		
		LOG.debug("Esito = "+esito.getEsito() + ", descrizione = "+esito.getMessage());
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}

	@Override
	public Response findTipiDiAiutoAssociati(Long idU, Long progrBandoLineaIntervento, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findTipiDiAiutoAssociati]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		TipoDiAiutoAssociatoDTO[] tipoDiAiutoAssociatoDTO = null;
				
		try {
			
			tipoDiAiutoAssociatoDTO = gestioneBackofficeSrv.findTipiDiAiutoAssociati(idU, userInfo.getIdIride(), progrBandoLineaIntervento);
			LOG.debug("Elementi trovati = "+tipoDiAiutoAssociatoDTO.length);
					
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			tipoDiAiutoAssociatoDTO = new TipoDiAiutoAssociatoDTO[1];
			tipoDiAiutoAssociatoDTO[0] = new TipoDiAiutoAssociatoDTO();
			tipoDiAiutoAssociatoDTO[0].setDescBreveTipoAiuto("Errore in findTipiDiAiutoAssociati");
			tipoDiAiutoAssociatoDTO[0].setDescBreveTipoAiuto("Errore = "+e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(tipoDiAiutoAssociatoDTO).build();
		
	}

	@Override
	public Response findTemaPrioritario(Long idU, Long idLineaDiIntervento, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findTemaPrioritario]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idLineaDiIntervento = "+idLineaDiIntervento);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		Decodifica[]  temaPrioritario = null;
				
		try {
			
			temaPrioritario = gestioneDatiDiDominioSrv.findDecodificheMultiProgr(idU, userInfo.getIdIride(), GestioneDatiDiDominioSrv.TEMA_PRIORITARIO, idLineaDiIntervento);
			LOG.debug("Elementi trovati = "+temaPrioritario.length);
			
		}catch (UnrecoverableException e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			temaPrioritario = new Decodifica[1];
			temaPrioritario[0] = new Decodifica();
			temaPrioritario[0].setDescrizioneBreve("Errore in GestioneDatiDiDominioBusinessImpl -> findDecodificheMultiProgr");
			temaPrioritario[0].setDescrizione("Errore: " +e.getMessage());
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			temaPrioritario = new Decodifica[1];
			temaPrioritario[0] = new Decodifica();
			temaPrioritario[0].setDescrizioneBreve("Errore in findTemaPrioritario");
			temaPrioritario[0].setDescrizione("Errore: " +e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(temaPrioritario).build();
		
	}
	
	
	@Override
	public Response findTemaPrioritarioAssociato(Long idU, Long progrBandoLineaIntervento, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findTemaPrioritarioAssociato]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		Decodifica[] temaPrioritarioAssociati = null;
				
		try {

			temaPrioritarioAssociati = gestioneDatiDiDominioSrv.findDecodificheFiltrato(idU, userInfo.getIdIride(), GestioneDatiDiDominioSrv.TEMA_PRIORITARIO_BANDO_LINEA, 
																					"progrBandoLineaIntervento", String.valueOf(progrBandoLineaIntervento) );
			LOG.debug("Tema Prioritario associati trovati = "+temaPrioritarioAssociati.length);
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			temaPrioritarioAssociati = new Decodifica[1];
			temaPrioritarioAssociati[0] = new Decodifica();
			temaPrioritarioAssociati[0].setDescrizioneBreve("Errore in findDecodificheFiltrato");
			temaPrioritarioAssociati[0].setDescrizione("Errore: " +e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(temaPrioritarioAssociati).build();
		
	}
	

	@Override
	public Response associaTemaPrioritario(Long idU, Long idTemaPrioritario, Long progrBandoLineaIntervento, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: associaTemaPrioritario]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento+", idTemaPrioritario = "+idTemaPrioritario);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		EsitoAssociazione esito = null;
				
		try {
			
			esito = gestioneBackofficeSrv.associaTemaPrioritario(idU, userInfo.getIdIride(), progrBandoLineaIntervento, idTemaPrioritario);
					
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			esito.setEsito(false);
			esito.setMessage("Errore in associaTemaPrioritario: "+e.getMessage());
		}
		
		LOG.debug("Esito = "+esito.getEsito() + ", descrizione = "+esito.getMessage());
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}

	@Override
	public Response eliminaTemaPrioritarioAssociato(Long idU, Long progrBandoLineaIntervento, Long idTemaPrioritario, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: eliminaTemaPrioritarioAssociato]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento+", idTemaPrioritario = "+idTemaPrioritario);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		GestioneBackOfficeEsitoGenerico esito = null;
				
		try {
			
			esito = gestioneBackofficeSrv.eliminaTemaPrioritarioAssociato(idU, userInfo.getIdIride(), progrBandoLineaIntervento, idTemaPrioritario);
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			esito.setEsito(false);
			esito.setMessage("Errore in eliminaTemaPrioritarioAssociato: "+e.getMessage());
		}
		
		LOG.debug("Esito = "+esito.getEsito() + ", descrizione = "+esito.getMessage());
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}

	@Override
	public Response findIndicatoriQSN(Long idU, Long idLineaDiIntervento, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findIndicatoriQSN]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idLineaDiIntervento = "+idLineaDiIntervento);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		Decodifica[]  indicatoriQSN = null;
				
		try {
			
			indicatoriQSN = gestioneDatiDiDominioSrv.findDecodificheMultiProgr(idU, userInfo.getIdIride(), GestioneDatiDiDominioSrv.INDICATORE_QSN, idLineaDiIntervento);
			LOG.debug("Elementi trovati = "+indicatoriQSN.length);
			
		}catch (UnrecoverableException e) {
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			indicatoriQSN = new Decodifica[1];
			indicatoriQSN[0] = new Decodifica();
			indicatoriQSN[0].setDescrizioneBreve("Errore in findDecodificheMultiProgr ");
			indicatoriQSN[0].setDescrizione("Errore: " +e.getMessage());
		}catch (Exception e) {
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			indicatoriQSN = new Decodifica[1];
			indicatoriQSN[0] = new Decodifica();
			indicatoriQSN[0].setDescrizioneBreve("Errore in findIndicatoriQSN ");
			indicatoriQSN[0].setDescrizione("Errore: " +e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(indicatoriQSN).build();
		
	}
	
	
	@Override
	public Response findIndicatoriQSNAssociati(Long idU, Long progrBandoLineaIntervento, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findIndicatoriQSNAssociati]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		IdDescBreveDescEstesaDTO[]  indicatoriProgramma = null;
				
		try {
			
			indicatoriProgramma = gestioneBackofficeSrv.findIndicatoriQSNAssociati(idU, userInfo.getIdIride(), progrBandoLineaIntervento);
			LOG.debug("Elementi trovati = "+indicatoriProgramma.length);
			
		}catch (Exception e) {
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			indicatoriProgramma = new IdDescBreveDescEstesaDTO[1];
			indicatoriProgramma[0] = new IdDescBreveDescEstesaDTO();
			indicatoriProgramma[0].setDescBreve("Errore in findIndicatoriQSNAssociati ");
			indicatoriProgramma[0].setDescEstesa("Errore: " +e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(indicatoriProgramma).build();
		
	}
	

	@Override
	public Response associaIndicatoreQSN(Long idU, Long progrBandoLineaIntervento, Long idIndicatoreQSN, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: associaIndicatoreQSN]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento+", idIndicatoreQSN = "+idIndicatoreQSN);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		EsitoAssociazione esito = null;
				
		try {
			
			esito = gestioneBackofficeSrv.associaIndicatoreQSN(idU, userInfo.getIdIride(), progrBandoLineaIntervento, idIndicatoreQSN);
					
		}catch (Exception e) {
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			esito.setEsito(false);
			esito.setMessage("Errore in associaIndicatoreQSN: "+e.getMessage());
		}
		
		LOG.debug("Esito = "+esito.getEsito() + ", descrizione = "+esito.getMessage());
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}

	@Override
	public Response eliminaIndicatoreQSNAssociato(Long idU, Long progrBandoLineaIntervento, Long idIndicatoreQSN, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: eliminaIndicatoreQSNAssociato]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento+", idIndicatoreQSN = "+idIndicatoreQSN);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		GestioneBackOfficeEsitoGenerico esito = null;
				
		try {
			
			esito = gestioneBackofficeSrv.eliminaIndicatoreQSNAssociato(idU, userInfo.getIdIride(), progrBandoLineaIntervento, idIndicatoreQSN);
			
		}catch (Exception e) {
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			esito.setEsito(false);
			esito.setMessage("Errore in eliminaIndicatoreQSNAssociato: "+e.getMessage());
		}
		
		LOG.debug("Esito = "+esito.getEsito() + ", descrizione = "+esito.getMessage());
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}

	@Override
	public Response findIndicatoriRisultatoProgramma(Long idU, Long progrBandoLineaIntervento, HttpServletRequest req) throws Exception {
		
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findIndicatoriRisultatoProgramma]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		IdDescBreveDescEstesaDTO[]  indicatoriProgramma = null;
				
		try {
			
			indicatoriProgramma = gestioneBackofficeSrv.findIndicatoriRisultatoProgramma(idU, userInfo.getIdIride(), progrBandoLineaIntervento);
			LOG.debug("Elementi trovati = "+indicatoriProgramma.length);
			
		}catch (UnrecoverableException e) {
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			indicatoriProgramma = new IdDescBreveDescEstesaDTO[1];
			indicatoriProgramma[0] = new IdDescBreveDescEstesaDTO();
			indicatoriProgramma[0].setDescBreve("Errore in findIndicatoriRisultatoProgramma - UnrecoverableException");
			indicatoriProgramma[0].setDescEstesa("Errore: " +e.getMessage());
		}catch (Exception e) {
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			indicatoriProgramma = new IdDescBreveDescEstesaDTO[1];
			indicatoriProgramma[0] = new IdDescBreveDescEstesaDTO();
			indicatoriProgramma[0].setDescBreve("Errore in findIndicatoriRisultatoProgramma ");
			indicatoriProgramma[0].setDescEstesa("Errore: " +e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(indicatoriProgramma).build();
		
	}

	@Override
	public Response associaIndicatoreRisultatoProgramma(Long idU, Long progrBandoLineaIntervento, Long idIndicatoreRisultatoProgramma, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: associaIndicatoreRisultatoProgramma]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento+", idIndicatoreRisultatoProgramma = "+idIndicatoreRisultatoProgramma);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		EsitoAssociazione esito = null;
				
		try {
			
			esito = gestioneBackofficeSrv.associaIndicatoreRisultatoProgramma(idU, userInfo.getIdIride(), progrBandoLineaIntervento, idIndicatoreRisultatoProgramma);
					
		}catch (Exception e) {
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			esito.setEsito(false);
			esito.setMessage("Errore in associaIndicatoreRisultatoProgramma: "+e.getMessage());
		}
		
		LOG.debug("Esito = "+esito.getEsito() + ", descrizione = "+esito.getMessage());
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}

	@Override
	public Response findIndicatoriRisultatoProgrammaAssociati(Long idU, Long progrBandoLineaIntervento, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findIndicatoriRisultatoProgrammaAssociati]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		IdDescBreveDescEstesaDTO[]  indicatoriProgramma = null;
				
		try {
			
			indicatoriProgramma = gestioneBackofficeSrv.findIndicatoriRisultatoProgrammaAssociati(idU, userInfo.getIdIride(), progrBandoLineaIntervento);
			LOG.debug("Elementi trovati = "+indicatoriProgramma.length);
			
		}catch (UnrecoverableException e) {
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			indicatoriProgramma = new IdDescBreveDescEstesaDTO[1];
			indicatoriProgramma[0] = new IdDescBreveDescEstesaDTO();
			indicatoriProgramma[0].setDescBreve("Errore in findIndicatoriRisultatoProgrammaAssociati - UnrecoverableException");
			indicatoriProgramma[0].setDescEstesa("Errore: " +e.getMessage());
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			indicatoriProgramma = new IdDescBreveDescEstesaDTO[1];
			indicatoriProgramma[0] = new IdDescBreveDescEstesaDTO();
			indicatoriProgramma[0].setDescBreve("Errore in findIndicatoriRisultatoProgrammaAssociati ");
			indicatoriProgramma[0].setDescEstesa("Errore: " +e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(indicatoriProgramma).build();
		
	}

	@Override
	public Response eliminaIndicatoreRisultatoProgramma(Long idU, Long progrBandoLineaIntervento, Long idIndicatoreRisultatoProgramma, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: eliminaIndicatoreRisultatoProgramma]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento+", idIndicatoreRisultatoProgramma = "+idIndicatoreRisultatoProgramma);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		GestioneBackOfficeEsitoGenerico esito = null;
				
		try {
			
			esito = gestioneBackofficeSrv.eliminaIndicatoreRisultatoProgrammaAssociato(idU, userInfo.getIdIride(), progrBandoLineaIntervento, idIndicatoreRisultatoProgramma);
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			esito.setEsito(false);
			esito.setMessage("Errore in eliminaIndicatoreRisultatoProgramma: "+e.getMessage());
		}
		
		LOG.debug("Esito = "+esito.getEsito() + ", descrizione = "+esito.getMessage());
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}

	@Override
	public Response tipoPeriodo(Long idU, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: tipoPeriodo]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU);
		
		ResponseCodeMessage resp = new ResponseCodeMessage();
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		Decodifica[]  tipoPeriodo = null;
				
		try {
			
			tipoPeriodo = gestioneDatiDiDominioSrv.findDecodifiche(idU, userInfo.getIdIride(), GestioneDatiDiDominioSrv.TIPO_PERIODO);
			LOG.debug("Elementi trovati = "+tipoPeriodo.length);
			
		}catch (UnrecoverableException e) {
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			resp.setCode("Errore in GestioneDatiDiDominioBusinessImpl -> findDecodifiche");
			resp.setMessage("Errore: " +e.getMessage());
			return Response.ok().entity(resp).build();
		}catch (Exception e) {
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			resp.setCode("Errore in tipoPeriodo");
			resp.setMessage("Errore: " +e.getMessage());
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(tipoPeriodo).build();
		
	}
	
	
	@Override
	public Response tipoPeriodoAssociato(Long idU, Long progrBandoLineaIntervento, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: tipoPeriodoAssociato]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		Decodifica[] tipoPerodoAssociati = null;
				
		try {

			tipoPerodoAssociati = gestioneDatiDiDominioSrv.findDecodificheFiltrato(idU, userInfo.getIdIride(), GestioneDatiDiDominioSrv.BANDO_LIN_TIPO_PERIODO, 
																					"progrBandoLineaIntervento", String.valueOf(progrBandoLineaIntervento) );
			LOG.debug("Tipi periodo associati trovati = "+tipoPerodoAssociati.length);
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			tipoPerodoAssociati = new Decodifica[1];
			tipoPerodoAssociati[0] = new Decodifica();
			tipoPerodoAssociati[0].setDescrizioneBreve("Errore in findDecodificheFiltrato");
			tipoPerodoAssociati[0].setDescrizione("Errore: " +e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(tipoPerodoAssociati).build();
		
	}
	

	@Override
	public Response associaTipoPeriodo(Long idU, Long progrBandoLineaIntervento, Long idTipoPeriodo, String periodoProgrammazione, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: associaTipoPeriodo]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento+", idTipoPeriodo = "+idTipoPeriodo+", periodoProgrammazione = "+periodoProgrammazione);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		EsitoAssociazione esito = null;
				
		try {
//			25-09-2023 idPeriodo non serve piu, lo forzo a 11 per tutti			
			Long idPeriodo = (it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_PERIODO_VECCHIA_PROGRAMMAZIONE).longValue();
			
//			Long idPeriodo = null;
//			
//			if (periodoProgrammazione != null && periodoProgrammazione.equals("2016") ) {
//				idPeriodo = (it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_PERIODO_POR_FESR_14_20).longValue();
//			}else if (periodoProgrammazione != null && periodoProgrammazione.equals("PRE-2016") ) {
//					idPeriodo = (it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_PERIODO_VECCHIA_PROGRAMMAZIONE).longValue();
//				}

			
			esito = gestioneBackofficeSrv.associaTipoPeriodo(idU, userInfo.getIdIride(), progrBandoLineaIntervento, idTipoPeriodo, idPeriodo);
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			esito.setEsito(false);
			esito.setMessage("Errore in associaTipoPeriodo: "+e.getMessage());
		}
		
		LOG.debug("Esito = "+esito.getEsito() + ", descrizione = "+esito.getMessage());
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}
	
	
	@Override
	public Response eliminaTipoPeriodo(Long idU, Long progrBandoLineaIntervento, Long idTipoPeriodo, String periodoProgrammazione, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: eliminaTipoPeriodo]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento+", idTipoPeriodo = "+idTipoPeriodo+", periodoProgrammazione = "+periodoProgrammazione);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		GestioneBackOfficeEsitoGenerico esito = null;
				
		try {
			
			Long idPeriodo = null;
			
			if (periodoProgrammazione != null && periodoProgrammazione.equals("2016") ) {
				idPeriodo = (it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_PERIODO_POR_FESR_14_20).longValue();
			}else if (periodoProgrammazione != null && periodoProgrammazione.equals("PRE-2016") ) {
					idPeriodo = (it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_PERIODO_VECCHIA_PROGRAMMAZIONE).longValue();
				}
			
			esito = gestioneBackofficeSrv.eliminaTipoPeriodoAssociato(idU, userInfo.getIdIride(), progrBandoLineaIntervento, idTipoPeriodo, idPeriodo);
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			esito.setEsito(false);
			esito.setMessage("Errore in eliminaTipoPeriodoAssociato: "+e.getMessage());
		}
		
		LOG.debug("Esito = "+esito.getEsito() + ", descrizione = "+esito.getMessage());
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}
	

	@Override
	public Response findAreaScientifica(Long idU, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findAreaScientifica]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		IdDescBreveDescEstesaDTO[]  areaScientifica = null;
				
		try {
			
			areaScientifica = gestioneBackofficeSrv.findAreeScientifiche(idU, userInfo.getIdIride());
			LOG.debug("Elementi trovati = "+areaScientifica.length);
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			areaScientifica = new IdDescBreveDescEstesaDTO[1];
			areaScientifica[0] = new IdDescBreveDescEstesaDTO();
			areaScientifica[0].setDescBreve("Errore in findAreeScientifiche");
			areaScientifica[0].setDescEstesa("Errore: " +e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(areaScientifica).build();
		
	}

	@Override
	public Response findAreeScientificaAssociate(Long idU, Long progrBandoLineaIntervento, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findAreeScientificaAssociate]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		AreaScientificaDTO[]  areaScientificaDTO = null;
				
		try {
			
			areaScientificaDTO = gestioneBackofficeSrv.findAreeScientificheAssociate(idU, userInfo.getIdIride(), progrBandoLineaIntervento);
			LOG.debug("Elementi trovati = "+areaScientificaDTO.length);
			
		}catch (UnrecoverableException e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			areaScientificaDTO = new AreaScientificaDTO[1];
			areaScientificaDTO[0] = new AreaScientificaDTO();
			areaScientificaDTO[0].setDescBreve("Errore in GestioneDatiDiDominioBusinessImpl -> findAreeScientificheAssociate");
			areaScientificaDTO[0].setDescEstesa("Errore: " +e.getMessage());
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			areaScientificaDTO = new AreaScientificaDTO[1];
			areaScientificaDTO[0] = new AreaScientificaDTO();
			areaScientificaDTO[0].setDescBreve("Errore in findAreeScientificheAssociate");
			areaScientificaDTO[0].setDescEstesa("Errore: " +e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(areaScientificaDTO).build();
		
	}

	@Override
	public Response associaAreaScientifica(Long idU, Long progrBandoLineaIntervento, Long idAreaScientifica, String descrizione, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: associaAreaScientifica]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento+", idAreaScientifica = "+idAreaScientifica+", descrizione = "+descrizione);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		EsitoAssociazione  esito = null;
				
		try {
			
			AreaScientificaDTO areaScientifica = new AreaScientificaDTO();
			areaScientifica.setId(idAreaScientifica);
			areaScientifica.setDescBreve(descrizione);
			areaScientifica.setDescEstesa(descrizione);
			
			
			esito = gestioneBackofficeSrv.associaAreaScientifica(idU, userInfo.getIdIride(), progrBandoLineaIntervento, areaScientifica);
			LOG.debug("Esito associazione scientifica = "+esito.getEsito()+", descrizione = "+esito.getMessage());
			
		}catch (Exception e) {
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			esito = new EsitoAssociazione();
			esito.setEsito(false);
			esito.setMessage("Errore: " +e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}

	@Override
	public Response eliminaAreaScientificaAssociata(Long idU, Long progrBandoLineaIntervento, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: eliminaAreaScientificaAssociata]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		GestioneBackOfficeEsitoGenerico  esito = null;
		
		esito = gestioneBackofficeSrv.eliminaAreaScientificaAssociata(idU, userInfo.getIdIride(), progrBandoLineaIntervento);
		LOG.debug("Esito elimina associazione scientifica = "+esito.getEsito()+", descrizione = "+esito.getMessage());
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}

	@Override
	public Response findRegole(Long idU, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findRegole]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		IdDescBreveDescEstesaDTO[]  idDescBreveDescEstesaDTO = null;
				
		try {
			
			idDescBreveDescEstesaDTO = gestioneBackofficeSrv.findRegole(idU, userInfo.getIdIride());
			LOG.debug("Regole travate = "+idDescBreveDescEstesaDTO.length);
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			idDescBreveDescEstesaDTO = new IdDescBreveDescEstesaDTO[1];
			idDescBreveDescEstesaDTO[0] = new IdDescBreveDescEstesaDTO();
			idDescBreveDescEstesaDTO[0].setDescBreve("Errore in findRegole");
			idDescBreveDescEstesaDTO[0].setDescEstesa("Errore: " +e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(idDescBreveDescEstesaDTO).build();
		
	}

	@Override
	public Response findRegoleAssociate(Long idU, Long progrBandoLineaIntervento, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findRegoleAssociate]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		RegolaAssociataDTO[]  regolaAssociataDTO = null;
				
		try {
			
			regolaAssociataDTO = gestioneBackofficeSrv.findRegoleAssociate(idU, userInfo.getIdIride(), progrBandoLineaIntervento);
			LOG.debug("Regole associate travate = "+regolaAssociataDTO.length);
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("Errore in findRegoleAssociate");
			resp.setMessage("Errore: " +e.getMessage());
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(regolaAssociataDTO).build();
		
	}

	@Override
	public Response associaRegola(Long idU, Long progrBandoLineaIntervento, Long idRegola, Long idTipoAnagrafica, Long idTipoBeneficiario, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: associaRegola]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento+", idRegola = "+idRegola+", "
				  + "idTipoAnagrafica = "+idTipoAnagrafica+", idTipoBeneficiario = "+idTipoBeneficiario);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		EsitoAssociazione  esito = null;
				
		try {
			
			esito = gestioneBackofficeSrv.associaRegola(idU, userInfo.getIdIride(), progrBandoLineaIntervento, idRegola, idTipoAnagrafica, idTipoBeneficiario);
			LOG.debug("Esito associaRegola = "+esito.getEsito()+", descrione = "+esito.getMessage());
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			esito = new EsitoAssociazione();
			esito.setEsito(false);
			esito.setMessage("Errore: " +e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}

	@Override
	public Response eliminaRegola(Long idU, Long progrBandoLineaIntervento, Long idRegola, Long idTipoAnagrafica, Long idTipoBeneficiario, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: eliminaRegola]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento+", idRegola = "+idRegola+", "
				  + "idTipoAnagrafica = "+idTipoAnagrafica+", idTipoBeneficiario = "+idTipoBeneficiario);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		GestioneBackOfficeEsitoGenerico  esito = null;
				
		try {
			
			esito = gestioneBackofficeSrv.eliminaRegolaAssociata(idU, userInfo.getIdIride(), idRegola, progrBandoLineaIntervento, idTipoAnagrafica, idTipoBeneficiario);
			LOG.debug("Esito associaRegola = "+esito.getEsito()+", descrione = "+esito.getMessage());
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			esito = new GestioneBackOfficeEsitoGenerico();
			esito.setEsito(false);
			esito.setMessage("Errore: " +e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}

	@Override
	public Response salvaCostanti(Long idU, HttpServletRequest req, CostantiBandoLineaDTO costantiBandoLinea) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: associaCostantiBandoLinea]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", costantiBandoLinea = "+costantiBandoLinea.toString());
		
		ResponseCodeMessage resp = new ResponseCodeMessage();
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		EsitoAssociazione  esito = null;
		
		try {
			
			// Verifica che la norma incentivazione non sia già associata ad altri bandi linea.
			boolean normaUnivoca = gestioneBackofficeSrv.verificaUnicitaNormaIncentivazione(idU, userInfo.getIdIride(), 
																							costantiBandoLinea.getProgrBandoLineaIntervento(), costantiBandoLinea.getNormaIncentivazione());
			if (!normaUnivoca) {
				resp.setCode("ERROR");
				resp.setMessage("La Norma di Incentivazione &egrave; gi&agrave; assegnata ad un altro bando linea.");
			} else {
				// Salva su db i valori inseriti a video.
				esito = gestioneBackofficeSrv.associaCostantiBandoLinea(idU, userInfo.getIdIride(), costantiBandoLinea);
				LOG.debug("Esito associaCostantiBandoLinea = "+esito.getEsito()+", descrione = "+esito.getMessage());
				
				if (!esito.getEsito()) {
					resp.setCode("ERROR");
					resp.setMessage(esito.getMessage());
				} else {
					resp.setCode("OK");
					resp.setMessage(MessageKey.KEY_OPERAZIONE_ESEGUITA);
				}
				
			}
				
			
		}catch (FormalParameterException e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			resp.setCode("ERROR");
			resp.setMessage("Errore: " +e.getMessage());
			LOG.error(e);
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			resp.setCode("ERROR");
			resp.setMessage("Errore: " +e.getMessage());
			LOG.error(e);
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(resp).build();
	}
	
	
	@Override
	public Response findStatoDomanda(Long idU, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findStatoDomanda]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		Decodifica[]  statoDomnda = null;
				
		try {
			
			statoDomnda = gestioneDatiDiDominioSrv.findDecodifiche(idU, userInfo.getIdIride(), GestioneDatiDiDominioSrv.STATO_DOMANDA);
			LOG.debug("Stato domanda trovati = "+statoDomnda.length);
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			statoDomnda = new Decodifica[1];
			statoDomnda[0] = new Decodifica();
			statoDomnda[0].setDescrizioneBreve("ERROR");
			statoDomnda[0].setDescrizione(e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(statoDomnda).build();
		
	}
	
	
	@Override
	public Response findCostantiBandoLinea(Long idU, Long progrBandoLineaIntervento, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findCostantiBandoLinea]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		CostantiBandoLineaDTO  costantiBandoLineaDTO = null;
				
		try {
			
			costantiBandoLineaDTO = gestioneBackofficeSrv.findCostantiBandoLinea(idU, userInfo.getIdIride(), progrBandoLineaIntervento);
			LOG.debug("costantiBandoLineaDTO = "+costantiBandoLineaDTO.toString());
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERROR");
			resp.setMessage(e.getMessage());
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(costantiBandoLineaDTO).build();
		
	}
	
	
	@Override
	public Response findTipoOperazione(Long idU, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findTipoOperazione]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		Decodifica[]  tipoOPerazione = null;
				
		try {
			
			tipoOPerazione = gestioneDatiDiDominioSrv.findDecodifiche(idU, userInfo.getIdIride(), GestioneDatiDiDominioSrv.TIPO_OPERAZIONE);
			LOG.debug("Tipo operzione trovati = "+tipoOPerazione.length);
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			tipoOPerazione = new Decodifica[1];
			tipoOPerazione[0] = new Decodifica();
			tipoOPerazione[0].setDescrizioneBreve("ERROR");
			tipoOPerazione[0].setDescrizione(e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(tipoOPerazione).build();
		
	}
	

	@Override
	public Response findBandoLinea(Long idU, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findBandoLinea]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		Decodifica[]  bandoLineaDuplicaModelli = null;
				
		try {
			
			bandoLineaDuplicaModelli = gestioneDatiDiDominioSrv.findDecodifiche(idU, userInfo.getIdIride(), GestioneDatiDiDominioSrv.BANDO_LINEA_DUPLICA_MODELLI);
			LOG.debug("Bandolinea da cui duplica i modelli trovati = "+bandoLineaDuplicaModelli.length);
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			bandoLineaDuplicaModelli = new Decodifica[1];
			bandoLineaDuplicaModelli[0] = new Decodifica();
			bandoLineaDuplicaModelli[0].setDescrizioneBreve("ERROR");
			bandoLineaDuplicaModelli[0].setDescrizione(e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(bandoLineaDuplicaModelli).build();
		
	}

	@Override
	public Response findModelliDaAssociare(Long idU, String progrBandoLineaIntervento, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findModelliDaAssociare]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idBandoLinea = "+progrBandoLineaIntervento);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		Decodifica[] modelliDaAssociare = null;
				
		try {
			
			modelliDaAssociare = gestioneDatiDiDominioSrv.findDecodificheFiltrato(idU, userInfo.getIdIride(), GestioneDatiDiDominioSrv.MODELLO_BANDO_LINEA, "progrBandoLineaIntervento", progrBandoLineaIntervento);
			LOG.debug("Trovati = "+modelliDaAssociare.length+" modella da associare per il bandoLinea = "+progrBandoLineaIntervento);
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			modelliDaAssociare = new Decodifica[1];
			modelliDaAssociare[0] = new Decodifica();
			modelliDaAssociare[0].setDescrizioneBreve("ERROR");
			modelliDaAssociare[0].setDescrizione(e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(modelliDaAssociare).build();
		
	}

	@Override
	public Response inserisciModello(ModelloDTO modelloDTO, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: inserisciModello]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+modelloDTO.getIdU()+", progrBandoLineaInterventoOld = "+modelloDTO.getProgrBandoLineaInterventoOld()+", "
				+ "progrBandoLineaInterventoNew = "+modelloDTO.getProgrBandoLineaInterventoNew()+ ", documenti da copiera = "+modelloDTO.getElencoIdTipoDocumentoIndex()+""
				+ ", documenti già associcati = "+modelloDTO.getElencoIdTipoDocumentoIndexAssociato());
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		EsitoAssociazione esitoAssociazione = null;
				
		try {
			
			Long[] elencoIdTipoDocumentoIndex = modelloDTO.getElencoIdTipoDocumentoIndex();				
			Long[] elencoIdTipoDocumentoIndexAssociato = modelloDTO.getElencoIdTipoDocumentoIndexAssociato();
			boolean isDuplicato = false;
			
			// Prima di salvare, verifica che i modelli da aggiungere non siano già associati.
			if(elencoIdTipoDocumentoIndex != null && elencoIdTipoDocumentoIndex.length > 0) {
				for(Long idTipoDocumento: elencoIdTipoDocumentoIndex)
						if(Arrays.asList(elencoIdTipoDocumentoIndexAssociato).contains(idTipoDocumento)) {
							esitoAssociazione = new EsitoAssociazione();
							esitoAssociazione.setEsito(false);
							esitoAssociazione.setMessage("idTipoDocumento = "+idTipoDocumento+" gia' associato");
							isDuplicato = true;
						}

				if(!isDuplicato) {
					
					esitoAssociazione = gestioneBackofficeSrv.associaModelli(modelloDTO.getIdU(), userInfo.getIdIride(), modelloDTO.getProgrBandoLineaInterventoOld(), 
	                        modelloDTO.getProgrBandoLineaInterventoNew(), elencoIdTipoDocumentoIndex);
					LOG.debug("Esito associazione = "+esitoAssociazione.getEsito()+", descrizione = "+esitoAssociazione.getMessage());
							
				}
				
			}
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			esitoAssociazione = new EsitoAssociazione();
			esitoAssociazione.setEsito(false);
			esitoAssociazione.setMessage(e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esitoAssociazione).build();
		
	}

	@Override
	public Response findModelliAssociati(Long idU, Long progrBandoLineaIntervento, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findModelliAssociati]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento);
		
		TemplateDTO[] template = null;
		List<TemplateDTO> templateList = new ArrayList<TemplateDTO>();
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		IdDescBreveDescEstesaDTO[] modelliAssociati = null;
				
		try {
			
			modelliAssociati = gestioneBackofficeSrv.findModelliAssociati(idU, userInfo.getIdIride(), progrBandoLineaIntervento);
			LOG.debug("modelliAssociati trovati = "+modelliAssociati.length);
			
			for(int i = 0; i < modelliAssociati.length; i++) {
				template = gestioneTemplatesBusinessImpl.findModelli(idU, userInfo.getIdIride(), progrBandoLineaIntervento, modelliAssociati[i].getId());
				
				if(template.length <= 0) {
					TemplateDTO temp = new TemplateDTO();
					temp.setIdTipoDocumentoIndex(modelliAssociati[i].getId());
//					temp.setIdTemplate(modelliAssociati[i].getId());
					temp.setDescBreveTipoDocIndex(modelliAssociati[i].getDescBreve());
					temp.setDescTipoDocIndex(modelliAssociati[i].getDescEstesa());
					templateList.add(temp);
				}
				for(TemplateDTO t: template) {
					templateList.add(t);
				}
				
			}
			
			
		}catch (Exception e) {
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			modelliAssociati = new IdDescBreveDescEstesaDTO[1];
			modelliAssociati[0] = new IdDescBreveDescEstesaDTO();
			modelliAssociati[0].setDescBreve("ERROR");
			modelliAssociati[0].setDescEstesa(e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(templateList).build();
		
	}

	@Override
	public Response eliminaModelloAssociato(Long idU, Long progrBandoLineaIntervento, Long idTipoDocumentoIndex, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: eliminaModelloAssociato]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento+", idTipoDocumentoIndex = "+idTipoDocumentoIndex);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		GestioneBackOfficeEsitoGenerico gestioneBackOfficeEsitoGenerico = null;
			
		gestioneBackOfficeEsitoGenerico = gestioneBackofficeSrv.eliminaModelloAssociato(idU, userInfo.getIdIride(), progrBandoLineaIntervento, idTipoDocumentoIndex);
		LOG.debug("Esito eliminazione = "+gestioneBackOfficeEsitoGenerico.getEsito()+", descrzione = "+gestioneBackOfficeEsitoGenerico.getMessage());
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(gestioneBackOfficeEsitoGenerico).build();
		
	}

	@Override
	public Response modificaTipoDocuemtoAssociato(Long idU, Long progrBandoLinea, Long idTipoDocumento, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: modificaTipoDocuemtoAssociato]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLinea = "+progrBandoLinea+", idTipoDocumento = "+idTipoDocumento);
		
		DatiModificaTemplate dati = new DatiModificaTemplate();
		ResponseCodeMessage resp = new ResponseCodeMessage();
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
				
		try {
			
			SezioneDTO[] sezioniDTO = gestioneBackofficeSrv.getSezioni(idU, userInfo.getIdIride(), progrBandoLinea, idTipoDocumento);
			ArrayList<SezioneVO> sezioni = gestioneBackofficeSrv.getBeanUtil().transformToArrayList(sezioniDTO, SezioneVO.class);
			if(sezioni == null || sezioni.size() == 0) {
				LOG.error(prf + "Sezione non trovate");
				resp.setCode("Sezioni non trovate.");
				resp.setMessage("modificaTipoDocuemtoAssociato : num sezioni = "+sezioni.size());
				return Response.ok().entity(resp).build();
			}
			LOG.debug(prf + "modelliAssociati trovati = "+sezioni.size());
			
			// Recupara la descrizione del tipo documento.
			Decodifica decodifiche[] = gestioneDatiDiDominioSrv.findDecodifiche(
					idU, userInfo.getIdIride(),
					GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_INDEX);
			String descTipoDocumento = "";
			for (Decodifica d : decodifiche) {
				if (idTipoDocumento.equals(d.getId()))
					descTipoDocumento = d.getDescrizione();
			}
			LOG.info(prf + "descTipoDocumento = "+descTipoDocumento);
			
			
			// Legge i placeholder associati al tipo di documento.
			ArrayList<String> placeholders = null;
			try {
				String[] elenco = gestioneBackofficeSrv.findPlaceholder(idU, userInfo.getIdIride(), idTipoDocumento);
				placeholders = gestioneBackofficeSrv.getBeanUtil().transformToArrayList(elenco, String.class);
			}catch (Exception e) {	
				// segnalo solo nel log, senza bloccare l'esecuzione del metodo.
				LOG.error("ERRORE in findPlaceholder durante la ricerca dei placeholder: "+e.getMessage(),e);
				placeholders = new ArrayList<String>();
			}
			
			dati.setSezioni(sezioni);
			dati.setDescTipoDocumento(descTipoDocumento);
			dati.setPlaceholders(placeholders);
			
		}catch (Exception e) {	
			e.getStackTrace();
			LOG.error(prf + ": "+e.getMessage());
			resp.setCode("Errore");
			resp.setMessage("Errore in modificaTipoDocuemtoAssociato. Descrizone errore = "+e.getMessage());
			return Response.ok().entity(resp).build();
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(dati).build();
		
	}
	
	@Override
	public Response salvaSezioni(Long idU, String paramSezioni, HttpServletRequest req) throws Exception{
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: salvaSezioni]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", paramSezioni = "+paramSezioni);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		ResponseCodeMessage resp = new ResponseCodeMessage();
		EsitoAssociazione esito = null;
		
		try {
			
			// Trasformo da String a JSONArray.
			JSONArray jsonArray = new JSONArray(paramSezioni);
			LOG.info("salvaSezioni: numero sezioni = "+jsonArray.length());
			
			//Popolo un array con i dati delle sezioni.
			//ArrayList<Sezione> sezioni = new ArrayList<Sezione>();
			SezioneDTO[] sezioni = new SezioneDTO[jsonArray.length()];
			String idMacroCorrente = "";
			int ordinamentoMicro = 0;
			for (int i = 0; i < jsonArray.length(); i++) {
				// Sezione proveniente dalla popup.
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				String idMacro = jsonObj.getString("idMacroSezioneModulo");
				String idMicro = jsonObj.getString("idMicroSezioneModulo");
				String contenuto = jsonObj.getString("contenutoMicroSezione");
				String descrizione = jsonObj.getString("descMicroSezione");
				String progrBlTipoDocSezMod = jsonObj.getString("progrBlTipoDocSezMod");
				String progrBandoLineaIntervento = jsonObj.getString("progrBandoLineaIntervento");
				String idTipoDocumentoIndex = jsonObj.getString("idTipoDocumentoIndex");
				// Sezione da popolare e salvare su db.
				SezioneDTO sezione = new SezioneDTO();
				sezione.setProgrBandoLineaIntervento(new Long(progrBandoLineaIntervento));
				sezione.setIdTipoDocumentoIndex(new Long(idTipoDocumentoIndex));
				sezione.setContenutoMicroSezione(contenuto);
				sezione.setDescMicroSezione(descrizione);
				sezione.setProgrBlTipoDocSezMod(new Long(progrBlTipoDocSezMod));
				sezione.setIdMacroSezioneModulo(new Long(idMacro));				
				if (idMicro == null || idMacro.length() == 0) {
					// se idMicro nullo, si tratta di una microsezione inserita ex novo.
					sezione.setIdMicroSezioneModulo(new Long(idMicro));
				}
				
				// Ricalcola l'ordinamento delle micro sezioni.
				if (!idMacroCorrente.equals(idMacro)) {
					// Inizia una nuova macro sessione: riparto da 1.
					ordinamentoMicro = 1;
					idMacroCorrente = idMacro;
				}
				sezione.setNumOrdinamentoMicroSezione(new Long(ordinamentoMicro));
				ordinamentoMicro = ordinamentoMicro + 1;
				
				sezioni[i] = sezione;
			}
			
			// Un po' di log
			for (int j = 0; j < sezioni.length; j++) {
				LOG.info("\n"+sezioni[j].toString()+"\n");
			}
			
			// Salvo le sezioni su db.
			esito = gestioneBackofficeSrv.salvaSezioni(idU, userInfo.getIdIride(), sezioni);
			if (esito == null || !esito.getEsito()) {
				resp.setCode("salvaSezioni: esito = "+esito);
				resp.setMessage("gestioneBackofficeSrv.salvaSezioni ha restituito null.");
				return Response.ok().entity(resp).build();
			}

			LOG.info("salvaSezioni: esito = "+esito.getEsito());
			
		} catch (Exception e) {
			LOG.error(prf + ": "+e);
			resp.setCode("ERROR");
			resp.setMessage("ERRORE in salvaSezioni: "+e);
			return Response.ok().entity(resp).build();
		}
		
		resp.setCode("Esito = "+esito.getEsito());
		resp.setMessage("Descrizione = "+esito.getMessage());
		
		LOG.debug(prf + " END");
		return Response.ok().entity(resp).build();
	}

	@Override
	public Response generaDocumento(Long idU, Long progrBandolineaIntervento, Long idTipoDocumento, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: generaDocumento]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandolineaIntervento = "+progrBandolineaIntervento+", idTipoDocumento = "+idTipoDocumento);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		gestioneTemplatesBusinessImpl.generaTemplate(idU, userInfo.getIdIride(), progrBandolineaIntervento, idTipoDocumento, new DatiMessaggio());
		LOG.debug(prf + " END");
		return null;
	}
	
	
	@Override
	public Response downloadDocumento(Long idU, Long idTipoDocumentoIndex, Long progrBandolinea, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: downloadDocumento]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idTipoDocumentoIndex = "+idTipoDocumentoIndex+", progrBandolinea = "+progrBandolinea);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
//		gestioneTemplatesBusinessImpl.downloadDocumento(idTipoDocumentoIndex, progrBandolinea);
		TemplateDTO[] modelli = gestioneTemplatesBusinessImpl.findModelli(idU, userInfo.getIdIride(), progrBandolinea, idTipoDocumentoIndex);
		
		// Segnalo errore se non ho trovato alcun idTemplate.
		if (modelli == null || modelli.length == 0
				|| modelli[0].getIdTemplate() == null) {
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("KO");
			resp.setMessage("Nessun template trovato per bandoLinea "+ progrBandolinea + " e idTipoDocumentoIndex " + idTipoDocumentoIndex);
			
			return Response.ok().entity(resp).build();
		}
		
		// Id Template
		String idTemplate = modelli[0].getIdTemplate().toString();
		LOG.info(prf + "visualizzaModelloGiaAssociato: idTemplate = "+ idTemplate);
		
		// Visualizzo il template (copiato da CPBEBackOfficeGenerazioneTemplate.visualizzaModello()).
		EsitoAnteprimaTemplateDTO esitoTemplate = gestioneTemplatesBusinessImpl.visualizzaTemplate(idU, userInfo.getIdIride(), Long.parseLong(idTemplate) );
		
		if (esitoTemplate == null || !esitoTemplate.getEsito()
				|| esitoTemplate.getBytes() == null) {
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("KO");
			MessaggioDTO msg = esitoTemplate.getMsg();
			if (msg != null) {
				resp.setMessage(msg.getKey()+" - "+ msg.getParams());
			}
			
			return Response.ok().entity(resp).build();

		} else {
			
			LOG.info(prf + "esito="+ esitoTemplate.getEsito()+" , nomeFile="+esitoTemplate.getNomeFile());
			
		}
		LOG.debug(prf + " END");
		return Response.ok(esitoTemplate.getBytes()).build();
	}
	

	@Override
	public Response getNomeDocumento(Long idU, Long progrBandolineaIntervento, Long idTipoDocumento, HttpServletRequest req)	throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: getNomeDocumento]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandolineaIntervento = "+progrBandolineaIntervento+", idTipoDocumento = "+idTipoDocumento);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
//		gestioneTemplatesBusinessImpl.downloadDocumento(idTipoDocumentoIndex, progrBandolinea);
		TemplateDTO[] modelli = gestioneTemplatesBusinessImpl.findModelli(idU, userInfo.getIdIride(), progrBandolineaIntervento, idTipoDocumento);
		
		// Segnalo errore se non ho trovato alcun idTemplate.
		if (modelli == null || modelli.length == 0
				|| modelli[0].getIdTemplate() == null) {
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("KO");
			resp.setMessage("Nessun template trovato per bandoLinea "+ progrBandolineaIntervento + " e idTipoDocumentoIndex " + idTipoDocumento);
			
			return Response.ok().entity(resp).build();
		}
		
		// Id Template
		String idTemplate = modelli[0].getIdTemplate().toString();
		LOG.info("visualizzaModelloGiaAssociato: idTemplate = "+ idTemplate);
		
		// Visualizzo il template (copiato da CPBEBackOfficeGenerazioneTemplate.visualizzaModello()).
		EsitoAnteprimaTemplateDTO esitoTemplate = gestioneTemplatesBusinessImpl.visualizzaTemplate(idU, userInfo.getIdIride(), Long.parseLong(idTemplate) );
		
		if (esitoTemplate == null || !esitoTemplate.getEsito()
				|| esitoTemplate.getBytes() == null) {
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("KO");
			MessaggioDTO msg = esitoTemplate.getMsg();
			if (msg != null) {
				resp.setMessage(msg.getKey()+" - "+ msg.getParams());
			}
			
			return Response.ok().entity(resp).build();

		} else {
			
			LOG.info(prf + "esito="+ esitoTemplate.getEsito()+" , nomeFile="+esitoTemplate.getNomeFile());
			
		}
		
	    String nomeDocumentoFile = esitoTemplate.getNomeFile();
	    LOG.debug(prf + "nomeDocumentoFile = "+nomeDocumentoFile);
	    
	    LOG.debug(prf + "END");
		return Response.ok().entity(nomeDocumentoFile).build();
	}
	

	@Override
	public Response findCheckListAssociate(Long idU, Long progrBandoLineaIntervento, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findCheckListAssociate]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		CheckListBandoLineaDTO[] checkListBandoLineaDTO = null;
				
		try {
			
			checkListBandoLineaDTO = gestioneBackofficeSrv.findCheckListAssociate(idU, userInfo.getIdIride(), progrBandoLineaIntervento);
			LOG.debug("CheckList associate trovati = "+checkListBandoLineaDTO.length);
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			checkListBandoLineaDTO = new CheckListBandoLineaDTO[1];
			checkListBandoLineaDTO[0] = new CheckListBandoLineaDTO();
			checkListBandoLineaDTO[0].setDescTipoDocIndex(e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(checkListBandoLineaDTO).build();
		
	}

	@Override
	public Response findCheckListAssociabili(Long idU, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findCheckListAssociabili]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		CheckListBandoLineaDTO[] checkListBandoLineaDTO = null;
				
		try {
			
			checkListBandoLineaDTO = gestioneBackofficeSrv.findCheckListAssociabili(idU, userInfo.getIdIride());
			LOG.debug("CheckList associate trovati = "+checkListBandoLineaDTO.length);
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			checkListBandoLineaDTO = new CheckListBandoLineaDTO[1];
			checkListBandoLineaDTO[0] = new CheckListBandoLineaDTO();
			checkListBandoLineaDTO[0].setDescTipoDocIndex(e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(checkListBandoLineaDTO).build();
		
	}

	@Override
	public Response associaCheklist(CheclistDTO checklistDTO, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: associaCheklist]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> checklist = "+checklistDTO.toString() );
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		EsitoAssociazione esito = null;
				
		try {
			
			Long[] idTipoDocumentoIndexArray = checklistDTO.getIdTipoDocumentoIndexArray(); 
			Long[] idModelloArray = checklistDTO.getIdModelloArray();
			
			for(int i = 0; i< idTipoDocumentoIndexArray.length; i++) {
				esito = gestioneBackofficeSrv.associaCheckList(checklistDTO.getIdU(), userInfo.getIdIride(), checklistDTO.getProgrBandoLineaIntervento(), idTipoDocumentoIndexArray[i], idModelloArray[i]);
				if (esito == null || !esito.getEsito())
					break;
			}
			if (esito == null) {
				LOG.debug(prf + "Esito associaCheckList = " + esito+". Non è stato possibile associare le checkList");
				esito = new EsitoAssociazione();
				esito.setEsito(false);
				esito.setMessage(MessageKey.KEY_ERR_OPERAZIONE_NON_COMPL);
			} else if (!esito.getEsito()) {
				LOG.debug(prf + "Esito associaCheckList = " + esito+". Non è stato possibile associare le checkList. Causa = "+esito.getMessage());
				esito.setEsito(false);
				esito.setMessage(esito.getMessage());
			} else {
				LOG.debug(prf + "Esito associaCheckList = " + esito+". Salvataggio avvenuto con successo");
				esito.setEsito(true);
				esito.setMessage(MessageKey.KEY_OPERAZIONE_ESEGUITA);
			}
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			esito = new EsitoAssociazione();
			esito.setEsito(false);
			esito.setMessage("ERRORE = "+e);
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}

	@Override
	public Response eliminaCheklistAssociata(Long idU, Long progrBandoLineaIntervento, Long idTipoDocumentoIndex, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: eliminaCheklistAssociata]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento+", idTipoDocumentoIndex = "+idTipoDocumentoIndex);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
			
		GestioneBackOfficeEsitoGenerico esito = gestioneBackofficeSrv.eliminaCheckListAssociata(idU, userInfo.getIdIride(), progrBandoLineaIntervento, idTipoDocumentoIndex);
		
		if (esito == null) {
			LOG.debug(prf + " Non è stato possibile eliminare la checkList selezionata");
			esito = new GestioneBackOfficeEsitoGenerico();
			esito.setEsito(false);
			esito.setMessage("Non è stato possibile eliminare la checkList selezionata");

		} else {
			LOG.debug(prf + "Esito eliminazione checkList = "+esito.getEsito()+", descrizion = "+esito.getMessage());
		}
		
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}
	
	
	@Override
	public Response findTipiDocumento(Long idU, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findTipiDocumento]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		Decodifica[] tipiDidDocumento = null;
				
		try {
			
			tipiDidDocumento = gestioneDatiDiDominioSrv.findDecodifiche(idU, userInfo.getIdIride(), GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_SPESA);
			LOG.debug("Trovati = "+tipiDidDocumento.length+" tipi di docuemento da associare");
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			tipiDidDocumento = new Decodifica[1];
			tipiDidDocumento[0] = new Decodifica();
			tipiDidDocumento[0].setDescrizioneBreve("ERROR");
			tipiDidDocumento[0].setDescrizione(e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(tipiDidDocumento).build();
		
	}
	
	
	@Override
	public Response findModalitaPagamento(Long idU, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findModalitaPagamento]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		Decodifica[] tipiDidDocumento = null;
				
		try {
			
			tipiDidDocumento = gestioneDatiDiDominioSrv.findDecodifiche(idU, userInfo.getIdIride(), GestioneDatiDiDominioSrv.MODALITA_PAGAMENTO);
			LOG.debug("Trovati = "+tipiDidDocumento.length+" tipi di docuemento da associare");
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			tipiDidDocumento = new Decodifica[1];
			tipiDidDocumento[0] = new Decodifica();
			tipiDidDocumento[0].setDescrizioneBreve("ERROR");
			tipiDidDocumento[0].setDescrizione(e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(tipiDidDocumento).build();
		
	}

	

	@Override
	public Response findDocPagamAssociati(Long idU, Long progrBandoLineaIntervento, HttpServletRequest req)throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findDocPagamAssociati]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		DocPagamBandoLineaDTO[] docPagamBandoLineaDTO = null;
				
		try {
			
			docPagamBandoLineaDTO = gestioneBackofficeSrv.findDocPagamAssociati(idU, userInfo.getIdIride(), progrBandoLineaIntervento);
			LOG.debug("Documenti associati trovati = "+docPagamBandoLineaDTO.length);
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			docPagamBandoLineaDTO = new DocPagamBandoLineaDTO[1];
			docPagamBandoLineaDTO[0] = new DocPagamBandoLineaDTO();
			docPagamBandoLineaDTO[0].setDescBreveTipoDocumento(e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(docPagamBandoLineaDTO).build();
		
	}

	@Override
	public Response findDocPagamAssociatiATuttiBL(Long idU, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: findDocPagamAssociatiATuttiBL]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		DocPagamBandoLineaDTO[] docPagamBandoLineaDTO = null;
				
		try {
			
			docPagamBandoLineaDTO = gestioneBackofficeSrv.findDocPagamAssociatiATuttiBL(idU, userInfo.getIdIride());
			LOG.debug("Documenti associati trovati = "+docPagamBandoLineaDTO.length);
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			docPagamBandoLineaDTO = new DocPagamBandoLineaDTO[1];
			docPagamBandoLineaDTO[0] = new DocPagamBandoLineaDTO();
			docPagamBandoLineaDTO[0].setDescBreveTipoDocumento(e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(docPagamBandoLineaDTO).build();
	}

	@Override
	public Response eliminaDocPagamAssociato(Long idU, Long progrEccezBanLinDocPag, HttpServletRequest req)throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: eliminaDocPagamAssociato]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrEccezBanLinDocPag = "+progrEccezBanLinDocPag);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		GestioneBackOfficeEsitoGenerico esito = null;
				
		try {
			
			esito = gestioneBackofficeSrv.eliminaDocPagamAssociato(idU, userInfo.getIdIride(), progrEccezBanLinDocPag);
			LOG.debug("Esito documento eliminato = "+esito.getEsito()+". Record modificati = "+esito.getMessage());
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			esito = new GestioneBackOfficeEsitoGenerico();
			esito.setEsito(false);
			esito.setMessage(e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}

	@Override
	public Response eliminaDocPagamAssociatoATuttiBL(Long idU, Long idTipoDocumento, Long idModalitaPagamento, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: eliminaDocPagamAssociatoATuttiBL]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idTipoDocumento = "+idTipoDocumento+", idModalitaPagamento = "+idModalitaPagamento);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		GestioneBackOfficeEsitoGenerico esito = null;
				
		try {
			
			esito = gestioneBackofficeSrv.eliminaDocPagamAssociatoATuttiBL(idU, userInfo.getIdIride(), idTipoDocumento, idModalitaPagamento);
			LOG.debug("Esito documento eliminato = "+esito.getEsito()+". Record modificati = "+esito.getMessage());
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			esito = new GestioneBackOfficeEsitoGenerico();
			esito.setEsito(false);
			esito.setMessage(e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}

	@Override
	public Response associaDocPagam(Long idU, Long progrBandoLineaIntervento, Long idTipoDocumento, Long idModalitaPagamento, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: associaDocPagam]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", progrBandoLineaIntervento = "+progrBandoLineaIntervento+", idTipoDocumento = "+idTipoDocumento+", idModalitaPagamento = "+idModalitaPagamento);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		EsitoAssociazione esito = null;
				
		try {
			
			esito = gestioneBackofficeSrv.associaDocPagam(idU, userInfo.getIdIride(), progrBandoLineaIntervento, idTipoDocumento, idModalitaPagamento);

			if (esito == null) {
				LOG.debug(prf + "Esito associaDocumentoPagamento = " + esito+". Non è stato possibile associare il documento di pagamento");
				esito = new EsitoAssociazione();
				esito.setEsito(false);
				esito.setMessage(MessageKey.KEY_ERR_OPERAZIONE_NON_COMPL);
			} else if (!esito.getEsito()) {
				LOG.debug(prf + "Esito associaDocumentoPagamento = " + esito+". Non è stato possibile associare il documento di pagamento. Causa = "+esito.getMessage());
				esito.setEsito(false);
				esito.setMessage(esito.getMessage());
			} else {
				LOG.debug(prf + "Esito associaDocumentoPagamento = " + esito+". Documento di pagamento associato con successo");
				esito.setEsito(true);
				esito.setMessage(MessageKey.KEY_OPERAZIONE_ESEGUITA);
			}
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			esito = new EsitoAssociazione();
			esito.setEsito(false);
			esito.setMessage("ERRORE = "+e);
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}

	@Override
	public Response associaDocPagamATuttiBL(Long idU, Long idTipoDocumento, Long idModalitaPagamento, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: associaDocPagamATuttiBL]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idTipoDocumento = "+idTipoDocumento+", idModalitaPagamento = "+idModalitaPagamento);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		EsitoAssociazione esito = null;
				
		try {
			
			esito = gestioneBackofficeSrv.associaDocPagamATuttiBL(idU, userInfo.getIdIride(), idTipoDocumento, idModalitaPagamento);

			if (esito == null) {
				LOG.debug(prf + "Esito associaDocumentoPagamento = " + esito+". Non è stato possibile associare il documento di pagamento");
				esito = new EsitoAssociazione();
				esito.setEsito(false);
				esito.setMessage(MessageKey.KEY_ERR_OPERAZIONE_NON_COMPL);
			} else if (!esito.getEsito()) {
				LOG.debug(prf + "Esito associaDocumentoPagamento = " + esito+". Non è stato possibile associare il documento di pagamento. Causa = "+esito.getMessage());
				esito.setEsito(false);
				esito.setMessage(esito.getMessage());
			} else {
				LOG.debug(prf + "Esito associaDocumentoPagamento = " + esito+". Documento di pagamento associato con successo");
				esito.setEsito(true);
				esito.setMessage(MessageKey.KEY_OPERAZIONE_ESEGUITA);
			}
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			esito = new EsitoAssociazione();
			esito.setEsito(false);
			esito.setMessage("ERRORE = "+e);
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}

	@Override
	public Response inserisciTipoDocumentoSpesa(Long idU, String descrizione, String descrizioneBreve, HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: inserisciTipoDocumentoSpesa]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", descrizione = "+descrizione+", descrizioneBreve = "+descrizioneBreve);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		EsitoAssociazione esito = null;
				
		try {
			
			esito = gestioneBackofficeSrv.inserisciTipoDocumentoSpesa(idU, userInfo.getIdIride(), descrizione, descrizioneBreve.toUpperCase());
			LOG.debug("Esito inserisci nuovo tipo documento = "+esito.getEsito()+". Descrizione = "+esito.getMessage());
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			esito = new EsitoAssociazione();
			esito.setEsito(false);
			esito.setMessage(e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}

	@Override
	public Response inserisciModalitaPagamento(Long idU, String descrizione, String descrizioneBreve, HttpServletRequest req) throws Exception {
		

		String prf = "[ConfigurazioneBandoLineaServiceImpl:: inserisciModalitaPagamento]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", descrizione = "+descrizione+", descrizioneBreve = "+descrizioneBreve);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		EsitoAssociazione esito = null;
				
		try {
			
			esito = gestioneBackofficeSrv.inserisciModalitaPagamento(idU, userInfo.getIdIride(), descrizione, descrizioneBreve.toUpperCase());
			LOG.debug("Esito inserisci nuovo tipo documento = "+esito.getEsito()+". Descrizione = "+esito.getMessage());
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			esito = new EsitoAssociazione();
			esito.setEsito(false);
			esito.setMessage(e.getMessage());
		}
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
		
	}

	@Override
	public Response verificaParolaChiaveActa(Long idU, Long progrBandoLineaEnteCompetenza,
			HttpServletRequest req) throws Exception {
		
		String prf = "[ConfigurazioneBandoLineaServiceImpl:: verificaParolaChiaveActa]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + " idU = "+idU);
		LOG.debug(prf + "progrBandoLineaEnteCompetenza = "+progrBandoLineaEnteCompetenza);
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		GestioneBackOfficeEsitoGenerico esito = null;
		
		try {
		
			//esito = gestioneBackofficeSrv.verificaParolaChiaveActa(idU, userInfo.getIdIride(), progrBandoLineaEnteCompetenza);
			esito = gestioneTemplatesBusinessIWebbompl.verificaParolaChiaveActa(idU, userInfo.getIdIride(), progrBandoLineaEnteCompetenza);
		
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			esito = new GestioneBackOfficeEsitoGenerico();
			esito.setEsito(false);
			esito.setMessage(e.getMessage());
		}
		LOG.debug(prf + " END");
		
		return Response.ok().entity(esito).build();
	}
	
	

	
	///////////////////////////////   TAB ESTREMI BANCARI //////////////////////////////////////	
	
	@Override
	public Response getEstremiBancari(Long idBando, HttpServletRequest req) throws Exception { 
		

		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");	
		
		//TODO da togliere
		
		

		List<ModAgevEstremiBancariDTO> response = new ArrayList<ModAgevEstremiBancariDTO>();
				
		try {
			
			 List<ModalitaAgevolazioneDTO> modAgevList = configurazioneBandoDAOImpl.getModalitaAgevolazioneByidBando(idBando);
			 
			 for(ModalitaAgevolazioneDTO temp : modAgevList) {
				 
				 List<EstremiBancariEstesiDTO> estremiBancariestesiList = configurazioneBandoDAOImpl.getEstremiBancari(idBando, temp.getIdModalitaAgevolazione());
				 //TODO andare su amministrativo contabile e fare i check illustrati nel algoritmo 2.6.4
				 List<EstremiBancariDTO> estremiBancariList = EstremiBancariDTO.extractData(estremiBancariestesiList);
				 
				 response.add( new ModAgevEstremiBancariDTO(temp, estremiBancariList) );
				 
			 }
			 
			 
			 
			 
			
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			
		}
		
		
		LOG.info(prf + " END");	
		
		return Response.ok().entity(response).build();
		
	}

	@Override
	public Response getBancheByDesc(String descrizione, HttpServletRequest req) throws Exception {


		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");			
		

		List<BancaSuggestVO> response = new ArrayList<BancaSuggestVO>();
				
		try {
			
			descrizione = descrizione.toUpperCase();
			response = configurazioneBandoDAOImpl.getBancheByDesc(descrizione);				 
			
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			
		}
		
		
		LOG.info(prf + " END");	
		
		return Response.ok().entity(response).build();
	}

	@Override
	@Transactional
	public Response insertEstremiBancari(InsertEstremiBancariDTO insertEstremiBancariDTO, HttpServletRequest req) throws Exception {
		

		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.debug(prf + " BEGIN");	
		
		
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		

		ResponseCodeMessage response = null; 
				
		
		try {
		
			//Controllo che il nome bando line sia null, altrimenti lancio un errore perchè puo esserci solo un associazione bando linea-iban 
			String nomeBandoLinea = configurazioneBandoDAOImpl.checkBandoLineaByIban(insertEstremiBancariDTO.getIban());
			if(nomeBandoLinea != null) {
				String msgErr = ErrorMessages.COD_EB02;
				it.csi.pbandi.pbwebbo.util.StringUtil.changeCharset(msgErr, "<iban che si sta configurando>", insertEstremiBancariDTO.getIban());
				it.csi.pbandi.pbwebbo.util.StringUtil.changeCharset(msgErr, "<NOME_BANDO_LINEA recuperato al paragrafo 2.6.5>", insertEstremiBancariDTO.getIban());
				throw new ErroreGestitoException(msgErr);
			}		
				
			
			Long idEstremiBancari = configurazioneBandoDAOImpl.insertEstremiBancari(
					insertEstremiBancariDTO.getIdBanca(),
					insertEstremiBancariDTO.getIban(),
					userInfo.getIdUtente()
			);
			
			configurazioneBandoDAOImpl.insertAssociazioneBandoModAgevEstrBanc(
					insertEstremiBancariDTO.getIdModalitaAgevolazione(), 
					insertEstremiBancariDTO.getIdBando(), 
					idEstremiBancari, 
					insertEstremiBancariDTO.getMoltiplicatore(), 
					userInfo.getIdUtente(), 
					insertEstremiBancariDTO.getTipologiaConto()
			);
			
			
			response = new ResponseCodeMessage("OK", "Inserimento avvenuto con successo");
			
		}
		//Eccezione associazione bandoLinea-iban
		catch (ErroreGestitoException e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			response = new ResponseCodeMessage("KO", e.getMessage());
			return Response.status(500).entity(response).build();
			
		}
			
		catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			response = new ResponseCodeMessage("KO", e.getMessage());
			return Response.status(500).entity(response).build();
			
		}
		
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(response).build();
	}

	@Override
	@Transactional
	public Response removeAssociazioneIban(Long idBando, Long idModalitaAgevolazione, Long idBanca,
			EstremiContoDTO EstremiContoDTO, HttpServletRequest req) throws Exception {
		

		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.debug(prf + " BEGIN");	

		ResponseCodeMessage response = null; 		
				
		
		try {
		
			
			SendToAmmContDTO sendToAmmContDTO = configurazioneBandoDAOImpl.getSendToAmmContDTO(idBando, idModalitaAgevolazione, idBanca, EstremiContoDTO.getIban(), ID_SERVIZIO, ID_ENTITA, ESITO);
			
			if(sendToAmmContDTO.isSendToAmCont())
				throw new Exception("non e' possibile eliminare l'associazione dell' iban poiche' e' gia' stato inviato a amministrativo contanile");
			
			
			configurazioneBandoDAOImpl.removeBandoModAgEstrBan(EstremiContoDTO.getIdEstremiBancari());
			configurazioneBandoDAOImpl.removeEstremiBancari(EstremiContoDTO.getIdEstremiBancari());
			
			response = new ResponseCodeMessage("OK", "Cancellazione avvenuta con successo");
			
		}
		//Eccezione associazione bandoLinea-iban
		catch (ErroreGestitoException e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			response = new ResponseCodeMessage("KO", e.getMessage());
			return Response.status(500).entity(response).build();
			
		}
			
		catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			response = new ResponseCodeMessage("KO", e.getMessage());
			return Response.status(500).entity(response).build();
			
		}
		
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(response).build();
	}
	
	@Override
	public Response sendToAmministrativoContabile(Long idU, Long idBando, HttpServletRequest req) throws Exception {


		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");	
		
		//TODO da togliere
	
		
		List<EstremiBancariEstesiDTO> estremiBancari = null;

		
				
		try {
			
			estremiBancari  = configurazioneBandoDAOImpl.getEstremiBancari(idBando);
			LOG.info(prf + " estremiBancari send to amministrativo contabile" + estremiBancari);
			
			for( EstremiBancariEstesiDTO estremo : estremiBancari) {
				
				AnagraficaDTO angrafica =  configurazioneBandoDAOImpl.getAnagrafica(idBando, ID_ENTE_COMPETENZA);
				
				IbanDTO iban = configurazioneBandoDAOImpl.getIbanInfo(idBando);
				
				String divisor = "_______________________________________________________________________\n";
				
				LOG.info(prf + "\n\n" + divisor + " DATI:\n" + estremo + "\n" + angrafica + "\n" + iban + "\n" + divisor + "\n\n"  );
				
				
				//Le srtringhe vuote sono le stringhe sono i campi che nell' analisi sono segnati come NA
				
				if(angrafica != null) {
					amministrativoContabile.callToAnagraficaFondoAnagrafica(estremo.getIdBando(), angrafica.getTitoloBando(), "", 
							0, angrafica.getIdEnteCompetenza(), "", idU);
				}
				
				if(iban != null) {
					amministrativoContabile.callToAnagraficaFondoIbanFondo (estremo.getIdBando(), estremo.getIban(), estremo.getIdModalitaAgevolazione().intValue(),
							estremo.getTipologiaConto(), estremo.getMoltiplicatore(), "", "", idU);
				}
				
							
			}
			
			
			
			
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			ResponseCodeMessage rcm = new ResponseCodeMessage("KO", e.getMessage());
			return Response.status(500).entity(rcm).build();
			
		}
		
		
		LOG.info(prf + " END");	
		
		return Response.ok().entity(estremiBancari).build();
		
	}

	
	///////////////////////////////   TAB MONITORAGGIO TEMPI //////////////////////////////////////	
	
	
	@Override
	public Response getParametriMonitoraggioTempi(HttpServletRequest req) throws Exception { 
		

		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");	

		List<GenericResponseDTO> response = new ArrayList<GenericResponseDTO>();
		
				
		try {
			 
			response = configurazioneBandoDAOImpl.getParametriMonitoraggioTempi();
			
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			
		}
		
		
		LOG.info(prf + " END");	
		
		return Response.ok().entity(response).build();
		
	}
	
	@Override
	public Response getParametriMonitoraggioTempiAssociati(Long idBandoLinea, HttpServletRequest req) throws Exception { 
		

		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");	

		List<ParametriMonitoraggioTempiAssociatiDTO> response = new ArrayList<ParametriMonitoraggioTempiAssociatiDTO>();
				
		try {
			 
			response = configurazioneBandoDAOImpl.getParametriMonitoraggioTempiAssociatiByBando(idBandoLinea);
			
			
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			
		}
		
		
		LOG.info(prf + " END");	
		
		return Response.ok().entity(response).build();
		
	}

	@Override
	@Transactional
	public Response insertParametriMonitoraggioTempiAssociati(ParametriMonitoraggioTempiAssociatiDTO parMonitTempiInput, HttpServletRequest req) throws Exception{
		
		
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.debug(prf + " BEGIN");	

		ResponseCodeMessage response = null; 
				
		
		try {						
			
			if (parMonitTempiInput.getIdParametroMonit() == null)
				throw new ErroreGestitoException ("IdParametroMonit non valorezzato ");
			
			if (parMonitTempiInput.getProgrBandoLineaIntervento() == null)
				throw new ErroreGestitoException ("ProgrBandoLineaIntervent non valorezzato ");
			
			if (parMonitTempiInput.getNumGiorni() != null && parMonitTempiInput.getNumGiorni() < 0)
				throw new ErroreGestitoException ("Inserito il numero giorni negativo, inserire un numero positivo ");
			
			List<ParametriMonitoraggioTempiAssociatiDTO> listElem = configurazioneBandoDAOImpl.getParametriMonitoraggioTempiAssociati(parMonitTempiInput.getIdParametroMonit(), parMonitTempiInput.getProgrBandoLineaIntervento());
			if(listElem.size() > 0)
				throw new ErroreGestitoException ("Associazione gia' presente ");
			
			
			configurazioneBandoDAOImpl.insertParametriMonitoraggioTempiAssociati(parMonitTempiInput);				
			response = new ResponseCodeMessage ("OK", "Inserimento riuscito con successo");	
			
		}
		//Eccezione associazione bandoLinea-iban
		catch (ErroreGestitoException e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			response = new ResponseCodeMessage("KO", e.getMessage());
			return Response.status(500).entity(response).build();
			
		}
			
		catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			response = new ResponseCodeMessage("KO", e.getMessage());
			return Response.status(500).entity(response).build();
			
		}
		
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(response).build();
		
		
	}

	@Override
	@Transactional
	public Response updateParametriMonitoraggioTempiAssociati(ParametriMonitoraggioTempiAssociatiDTO parMonitTempiInput, HttpServletRequest req) throws Exception{
		
		
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.debug(prf + " BEGIN");	

		ResponseCodeMessage response = null; 
				
		
		try {
			
			
			
			ParametriMonitoraggioTempiAssociatiDTO parametriMonitoraggioTempi = configurazioneBandoDAOImpl.getParametriMonitoraggioTempiAssociatiById(parMonitTempiInput.getIdParamMonitBandoLinea());
			
			if(parametriMonitoraggioTempi == null)
				throw new ErroreGestitoException("Record non trovato");
			
			if (parametriMonitoraggioTempi.getDtFineValidita() != null)
				throw new ErroreGestitoException("Data fine validita gia' valorizzata");				
			
			configurazioneBandoDAOImpl.logicDeleteParametriMonitoraggioTempiAssociati(parMonitTempiInput);
			
			configurazioneBandoDAOImpl.insertParametriMonitoraggioTempiAssociati(parMonitTempiInput);
			
			response = new ResponseCodeMessage ("OK", "Update riuscito con successo");
		
			
			
			
		}
		//Eccezione associazione bandoLinea-iban
		catch (ErroreGestitoException e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			response = new ResponseCodeMessage("KO", e.getMessage());
			return Response.status(500).entity(response).build();
			
		}
			
		catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			response = new ResponseCodeMessage("KO", e.getMessage());
			return Response.status(500).entity(response).build();
			
		}
		
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(response).build();
		
		
	}
	
	@Override
	@Transactional
	public Response deleteUpdateParametriMonitoraggioTempiAssociati(Long idParamMonitBandoLinea, HttpServletRequest req) throws Exception{
		
		
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.debug(prf + " BEGIN");	

		ResponseCodeMessage response = null; 
				
		
		try {		
		
			if(idParamMonitBandoLinea == null)
				throw new ErroreGestitoException("Id non valorizzato");
				
			ParametriMonitoraggioTempiAssociatiDTO parametriMonitoraggioTempi = configurazioneBandoDAOImpl.getParametriMonitoraggioTempiAssociatiById(idParamMonitBandoLinea);
			
			
			if(parametriMonitoraggioTempi == null)
				throw new ErroreGestitoException("Record non trovato");
			
			if (parametriMonitoraggioTempi.getDtFineValidita() != null)
				throw new ErroreGestitoException("Data fine validita gia' valorizzata");				
			
			configurazioneBandoDAOImpl.logicDeleteParametriMonitoraggioTempiAssociati(parametriMonitoraggioTempi);
			
			response = new ResponseCodeMessage ("OK", "Delete riuscito con successo");
				
			
		
			
			
			
		}
		//Eccezione associazione bandoLinea-iban
		catch (ErroreGestitoException e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			response = new ResponseCodeMessage("KO", e.getMessage());
			return Response.status(500).entity(response).build();
			
		}
			
		catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			response = new ResponseCodeMessage("KO", e.getMessage());
			return Response.status(500).entity(response).build();
			
		}
		
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(response).build();
		
		
	}
	
	
	///////////////////////////////////////////////////////////////
	
	@Override
	public Response bandoIsEnteCompetenzaFinpiemonte(Long progBandoLineaIntervento, HttpServletRequest req) throws Exception { 
	
	
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");	
		
		Boolean response = null;
		
		try {
		
			response = gestioneBackofficeBusinessImpl.bandoIsEnteCompetenzaFinpiemonte(progBandoLineaIntervento);
		
		
		}catch (Exception e) {	
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			throw e;
		
		}
		
		
		LOG.info(prf + " END");	
		
		return Response.ok().entity(response).build();
	
	}
	
	

	

	
	
	

}
