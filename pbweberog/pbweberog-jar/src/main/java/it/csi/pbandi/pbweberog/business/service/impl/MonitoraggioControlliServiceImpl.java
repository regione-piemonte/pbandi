/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CodiceDescrizioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.rilevazionicontrolli.EsitoCampionamentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.rilevazionicontrolli.EsitoElencoProgettiCampione;
import it.csi.pbandi.pbservizit.pbandisrv.dto.rilevazionicontrolli.EsitoGenerazioneReportDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.rilevazionicontrolli.FiltroRilevazioniDTO;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweberog.business.service.MonitoraggioControlliService;
import it.csi.pbandi.pbweberog.dto.CodiceDescrizione;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.monitoraggiocontrolli.ElenchiProgettiCampionamento;
import it.csi.pbandi.pbweberog.dto.monitoraggiocontrolli.FiltroRilevazione;
import it.csi.pbandi.pbweberog.dto.monitoraggiocontrolli.ProgettoCampione;
import it.csi.pbandi.pbweberog.integration.dao.MonitoraggioControlliDAO;
import it.csi.pbandi.pbweberog.integration.request.RequestGetProgettiCampione;
import it.csi.pbandi.pbweberog.util.BeanUtil;
import it.csi.pbandi.pbweberog.util.Constants;
import it.csi.pbandi.pbweberog.util.DateUtil;
import it.csi.pbandi.pbweberog.util.StringUtil;

@Service
public class MonitoraggioControlliServiceImpl implements MonitoraggioControlliService{
	@Autowired
	protected BeanUtil beanUtil;
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	
	@Autowired
	MonitoraggioControlliDAO monitoraggioControlliDAO;

	@Override
	public Response getNormative(HttpServletRequest req, Boolean isConsultazione) throws UtenteException, Exception {
		String prf = "[MonitoraggioControlliServiceImpl::getNormative]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			// Tab - Estrazione progetti campionati => Boolean isConsultazione = true
			// Tab - Acquistazione progetti campionati => Boolean isConsultazione = false
			CodiceDescrizioneDTO[] normative = monitoraggioControlliDAO.findNormative(idUtente, idIride, isConsultazione, Constants.FLAG_RILEVAZIONE_CONTROLLI);		
			LOG.debug(prf + " END");
			return Response.ok().entity(beanUtil.transformToArrayList(normative, CodiceDescrizione.class)).build();
		} catch(Exception e) {
			throw e;
		}
	}
	@Override
	public Response getAsse(HttpServletRequest req,  Long idLineaDiIntervento, Boolean isConsultazione)
			throws UtenteException, Exception {
		String prf = "[MonitoraggioControlliServiceImpl::getAsse]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			// Tab - Estrazione progetti campionati => Boolean isConsultazione = true
			// Tab - Acquistazione progetti campionati => Boolean isConsultazione = false
			FiltroRilevazione filtro = new FiltroRilevazione();
			filtro.setIdLineaIntervento(idLineaDiIntervento.toString());
			
			FiltroRilevazioniDTO filtroDTO = beanUtil.transform(filtro, FiltroRilevazioniDTO.class);
			CodiceDescrizioneDTO[] asse = monitoraggioControlliDAO.findAsse(idUtente, idIride, isConsultazione, filtroDTO);		
			LOG.debug(prf + " END");
			return Response.ok().entity(beanUtil.transformToArrayList(asse, CodiceDescrizione.class)).build();
		} catch(Exception e) {
			throw e;
		}
	}
	@Override
	public Response getBandi(HttpServletRequest req, Long idLineaDiIntervento, Long idAsse, Boolean isConsultazione)
			throws UtenteException, Exception {
		String prf = "[MonitoraggioControlliServiceImpl::getBandi]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			// Tab - Estrazione progetti campionati => Boolean isConsultazione = true
			// Tab - Acquistazione progetti campionati => Boolean isConsultazione = false
			FiltroRilevazione filtro = new FiltroRilevazione();
			filtro.setIdLineaIntervento(idLineaDiIntervento.toString());
			filtro.setIdAsse(idAsse.toString());
			FiltroRilevazioniDTO filtroDTO = beanUtil.transform(filtro, FiltroRilevazioniDTO.class);
			CodiceDescrizioneDTO[] bandi = monitoraggioControlliDAO.findBandi(idUtente, idIride, isConsultazione, filtroDTO);		
			LOG.debug(prf + " END");
			return Response.ok().entity(beanUtil.transformToArrayList(bandi, CodiceDescrizione.class)).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	@Override
	public Response getAnnoContabili(HttpServletRequest req, Boolean isConsultazione) throws UtenteException, Exception {
		String prf = "[MonitoraggioControlliServiceImpl::getAnnoContabili]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			CodiceDescrizioneDTO[] periodi = monitoraggioControlliDAO.findAnnoContabili(idUtente, idIride, isConsultazione);		
			LOG.debug(prf + " END");
			return Response.ok().entity(beanUtil.transformToArrayList(periodi, CodiceDescrizione.class)).build();
		} catch(Exception e) {
			throw e;
		}
	}
	@Override
	public Response getAutoritaControllo(HttpServletRequest req, Boolean isConsultazione) throws UtenteException, Exception {
		String prf = "[MonitoraggioControlliServiceImpl::getAutoritaControllo]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			
			CodiceDescrizioneDTO[] autoritaControlli = monitoraggioControlliDAO.findAutoritaControlli(idUtente, idIride, isConsultazione);		
			LOG.debug(prf + " END");
			
			LOG.debug(prf + " END");
			return Response.ok().entity(autoritaControlli).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	@Override
	public Response generaReportCampionamento(HttpServletRequest req, FiltroRilevazione filtroConsultazione)
			throws UtenteException, Exception {
		String prf = "[MonitoraggioControlliServiceImpl::generaReportCampionamento]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			FiltroRilevazioniDTO filtroDTO = beanUtil.transform(filtroConsultazione, FiltroRilevazioniDTO.class);
			EsitoGenerazioneReportDTO esito = monitoraggioControlliDAO.generaReportCampionamento(idUtente, idIride, filtroDTO);		
			LOG.debug(prf + " END");
			return Response.ok(esito.getReport()).build();
		} catch(Exception e) {
			throw e;
		}
	}
	@Override
	public Response getProgettiCampione(HttpServletRequest req, RequestGetProgettiCampione getProgettiCampioneRequest)
			throws UtenteException, Exception {
		String prf = "[MonitoraggioControlliServiceImpl::getProgettiCampione]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			FiltroRilevazione filtro = getProgettiCampioneRequest.getFiltro();
			
			// VALIDATE FILTRO
			if (filtro.getIdLineaIntervento() == null || StringUtil.isEmpty(filtro.getIdLineaIntervento())) 
				return inviaErroreBadRequest("parametro mancato nel body: ?{RequestGetProgettiCampione.filtro.idLineaIntervento}");

			if (filtro.getIdAnnoContabile() == null || StringUtil.isEmpty(filtro.getIdAnnoContabile()))
				return inviaErroreBadRequest("parametro mancato nel body: ?{RequestGetProgettiCampione.filtro.idAnnoContabile}");

			if (filtro.getIdAutoritaControllante() == null || StringUtil.isEmpty(filtro.getIdAutoritaControllante()))
				return inviaErroreBadRequest("parametro mancato nel body: ?{RequestGetProgettiCampione.filtro.idAutoritaControllante}");
			
			if (filtro.getElencoProgetti() == null || StringUtil.isEmpty(filtro.getElencoProgetti()))
				return inviaErroreBadRequest("parametro mancato nel body: ?{RequestGetProgettiCampione.filtro.elencoProgetti}");
			
			if (filtro.getTipoControllo() == null || StringUtil.isEmpty(filtro.getTipoControllo()))
				return inviaErroreBadRequest("parametro mancato nel body: ?{RequestGetProgettiCampione.filtro.tipoControllo}");
			
			// Se tipo Controllo = documentale, il campo Data Campione viene nascosto a video,  quindi non serve nessun controllo.
			if (!"D".equalsIgnoreCase(filtro.getTipoControllo())) {
				if (filtro.getDataCampione() == null || StringUtil.isEmpty(filtro.getDataCampione())) {
					return inviaErroreBadRequest("parametro mancato nel body: ?{RequestGetProgettiCampione.filtro.dataCampione}");
				} else {
					Date d = DateUtil.getDate(filtro.getDataCampione());					
					Date oggi = DateUtil.getDataOdiernaSenzaOra();
					try {
						if (DateUtil.before(oggi, d)) {
							return inviaErroreBadRequest("La data non può essere successiva alla data odierna.");
						}
					} catch (Exception e) {
						LOG.error("Errore nel confronto tra data campione e data odierna: " + e);
						throw e;
					}
				}
			}
			
			// VALIDATE ELENCO PROGETTI
			String progettiToParse = filtro.getElencoProgetti();
			String[] idProgetti = progettiToParse.trim().split(";");
			ArrayList<Long> idProgettiList = new ArrayList<Long>();
			for (String id : idProgetti) {
				try {
					idProgettiList.add(Long.parseLong(id));
				} catch (Exception e) {
					LOG.error("Errore di parsificazione dei codici progetto.", e);
					return inviaErroreBadRequest("RequestGetProgettiCampione.filtro.elencoProgetti L'elenco dei progetti campionati non &egrave; scritto correttamente.");
				}
			}
			
			//Rimozione duplicati
			Set<Long> set = new HashSet<>(idProgettiList);
			idProgettiList = new ArrayList<Long>();
			idProgettiList.addAll(set);
			
			// CARICA PROGETTI CAMPIONE
			FiltroRilevazioniDTO filtroDTO = beanUtil.transform(filtro, FiltroRilevazioniDTO.class);
			EsitoElencoProgettiCampione esito = monitoraggioControlliDAO.caricaProgettiCampione(idUtente, idIride, idProgettiList.toArray(new Long[0]) , 
					filtroDTO);
			
			ElenchiProgettiCampionamento elenchi = new ElenchiProgettiCampionamento();
			elenchi.setProgettiGiaPresenti(beanUtil.transformToArrayList(esito.getProgettiGiaPresenti(), ProgettoCampione.class));
			elenchi.setProgettiDaAggiungere(beanUtil.transformToArrayList(esito.getProgettiDaAggiungere(), ProgettoCampione.class));
			elenchi.setProgettiScartati(esito.getProgettiScartati());
			
			elenchi.setProgettiDelCampione(beanUtil.transformToArrayList(esito.getProgettiDelCampione(), ProgettoCampione.class));
			LOG.debug(prf + " END");
			return Response.ok().entity(elenchi).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	@Override
	public Response registraCampionamentoProgetti(HttpServletRequest req,
			RequestGetProgettiCampione getProgettiCampioneRequest) throws UtenteException, Exception {
		String prf = "[MonitoraggioControlliServiceImpl::registraCampionamentoProgetti]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			FiltroRilevazione filtro = getProgettiCampioneRequest.getFiltro();
			String progettiToParse = filtro.getElencoProgetti();
			String[] idProgetti = progettiToParse.trim().split(";");
			
			ArrayList<Long> idProgettiList = new ArrayList<Long>();
			for (String id : idProgetti) {
				try {
					idProgettiList.add(Long.parseLong(id));
				} catch (Exception e) {
					LOG.error("Errore di parsificazione dei codici progetto.", e);
					return inviaErroreBadRequest("RequestGetProgettiCampione.filtro.elencoProgetti L'elenco dei progetti campionati non &egrave; scritto correttamente.");
				}
			}
			
			FiltroRilevazioniDTO filtroDTO = beanUtil.transform(filtro, FiltroRilevazioniDTO.class);
			EsitoCampionamentoDTO esito = monitoraggioControlliDAO.registraCampionamentoProgetti(idUtente, idIride, filtroDTO, idProgettiList.toArray(new Long[0]));
			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
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
