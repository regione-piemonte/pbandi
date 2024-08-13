/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.business.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.pbandisrv.business.gestionebackoffice.GestioneBackofficeBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatididominio.GestioneDatiDiDominioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.profilazione.ProfilazioneBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.BeneficiarioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.DatiBeneficiarioProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica;
import it.csi.pbandi.pbservizit.pbandisrv.dto.profilazione.Beneficiario;
import it.csi.pbandi.pbservizit.pbandisrv.dto.profilazione.FiltroBeneficiarioProgettoSoggettoRuoloDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.profilazione.ProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebbo.business.service.CambiaBeneficiarioService;
import it.csi.pbandi.pbwebbo.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbwebbo.util.Constants;

@Service
public class CambiaBeneficiarioServiceImpl implements CambiaBeneficiarioService {
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	private ProfilazioneBusinessImpl profilazioneBusinessImpl;
	
	@Autowired
	private GestioneBackofficeBusinessImpl gestioneBackofficeBusinessImpl;
	
	@Autowired
	private GestioneDatiDiDominioBusinessImpl gestioneDatiDiDominioBusinessImpl;

	@Override
	public Response findBeneficiariByProgetto(Long idU, Long idSoggetto, String ruolo, Long idProgetto, HttpServletRequest req) throws Exception {
		
		String prf = "[CambiaBeneficiarioServiceImpl::findBeneficiariByProgetto]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idSoggetto = "+idSoggetto+", ruolo = "+ruolo);
		
		Beneficiario[] beneficiario = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {
		
			FiltroBeneficiarioProgettoSoggettoRuoloDTO filtro = new FiltroBeneficiarioProgettoSoggettoRuoloDTO();
			filtro.setIdProgetto(idProgetto);
			filtro.setIdSoggetto(idSoggetto);
			filtro.setCodiceRuolo(ruolo);
			beneficiario = profilazioneBusinessImpl.findBeneficiariByProgettoSoggettoRuolo(idU, userInfo.getIdIride(), filtro);

			if(beneficiario != null && beneficiario.length > 0) {
				LOG.debug(prf + "Trovati = " + beneficiario.length+" beneficiari");
			}else {
				LOG.debug(prf + "Non sono stati trovati beneficiari per idU = "+idU);
			}
				
		}catch (Exception e) {	
			LOG.error(prf + e);
			throw e;
		}
			
		LOG.debug(prf + " END");
		
		return Response.ok().entity(beneficiario).build();
		
	}

	@Override
	public Response findProgettiByBeneficiario(Long idU, Long idSoggetto, String ruolo, Long idSoggettoBeneficiario, HttpServletRequest req) throws Exception {
		
		String prf = "[CambiaBeneficiarioServiceImpl::findProgettiByBeneficiario]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idSoggetto = "+idSoggetto+", ruolo = "+ruolo);
		
		ProgettoDTO[] progettoDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {
		
			FiltroBeneficiarioProgettoSoggettoRuoloDTO filtro = new FiltroBeneficiarioProgettoSoggettoRuoloDTO();
			filtro.setIdSoggettoBeneficiario(idSoggettoBeneficiario);
			filtro.setIdSoggetto(idSoggetto);
			filtro.setCodiceRuolo(ruolo);
			progettoDTO = profilazioneBusinessImpl.findProgettiByBeneficiarioSoggettoRuolo(idU, userInfo.getIdIride(), filtro);

			if(progettoDTO != null && progettoDTO.length > 0) {
				LOG.debug(prf + "Trovati = " + progettoDTO.length+" progetti");
			}else {
				LOG.debug(prf + "Non sono stati trovati progetti per idU = "+idU);
			}
			
		}catch (Exception e) {	
			LOG.error(prf + e);
			throw e;
		}
			
		LOG.debug(prf + " END");
		
		return Response.ok().entity(progettoDTO).build();
		
	}

	@SuppressWarnings("unused")
	@Override
	public Response findProgettoBeneficiario(Long idU, Long idProgSelez, String idBeneficiario, String codiceVisualizzato, HttpServletRequest req) throws Exception {
		
		String prf = "[CambiaBeneficiarioServiceImpl::findProgettoBeneficiario]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idProgSelez = "+idProgSelez+", idBeneficiario = "+idBeneficiario+", codiceVisualizzato = "+codiceVisualizzato);
		
		BeneficiarioDTO beneficiarioDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {
		
			if (idProgSelez == null) {
				LOG.debug(prf + "Non e' stato selezionato alcun progetto. Selezionarne almeno uno");
			}else {
				beneficiarioDTO = gestioneBackofficeBusinessImpl.findCurrentBeneficiario(idU, userInfo.getIdIride(), codiceVisualizzato);
				LOG.debug(prf + "beneficiario trovato = " + beneficiarioDTO.toString());
			}
			
		}catch (Exception e) {	
			LOG.error(prf + e);
			throw e;
		}
			
		LOG.debug(prf + " END");
		
		return Response.ok().entity(beneficiarioDTO).build();
		
	}

	@Override
	public Response findBeneficiarioSubentrante(Long idU, String beneficiarioSubentrante, BeneficiarioDTO currentBeneficiario, HttpServletRequest req) throws Exception {
		
		String prf = "[CambiaBeneficiarioServiceImpl::findBeneficiarioSubentrante]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", beneficiarioSubentrante = "+beneficiarioSubentrante+", currentBeneficiario = "+currentBeneficiario.toString());

		DatiBeneficiarioProgettoDTO datiBeneficiarioProgettoDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {

			datiBeneficiarioProgettoDTO = gestioneBackofficeBusinessImpl.findBeneficiarioSubentrante(idU, userInfo.getIdIride(), beneficiarioSubentrante);
			
			if(datiBeneficiarioProgettoDTO != null) {
				LOG.debug(prf + " Trovati dati beneficiarioSubentrante = "+datiBeneficiarioProgettoDTO.toString());
			}
			
			if(currentBeneficiario.getAnagraficaAggiornabile() != null || currentBeneficiario.getAnagraficaAggiornabile()){
				// caso regione
				
				if(datiBeneficiarioProgettoDTO == null) {
					LOG.debug(prf + "CAMBIA_BENEF_NOT_FOUND_AGGIORNABILE");
					LOG.debug(prf + "Il soggetto non \\\\u00E8 presente sulla banca dati. Procedere comunque con il Cambio beneficiario. Attendere l'aggiornamento dell'anagrafica tramite flussi batch");
					datiBeneficiarioProgettoDTO = new DatiBeneficiarioProgettoDTO();
					datiBeneficiarioProgettoDTO.setMessage("Il soggetto non \\\\\\\\u00E8 presente sulla banca dati. Procedere comunque con il Cambio beneficiario. Attendere l'aggiornamento dell'anagrafica "
							                             + "tramite flussi batch");

				}else {
					LOG.debug(prf + "CAMBIA_BENEF_FOUND_AGGIORNABILE");
					LOG.debug(prf + "Il soggetto \\\\u00E8 gi\\\\u00E0 presente sulla banca dati. Verificare denominazione e sede legale ed eventualmente correggerli. "
							      + "Poi procedere con il Cambio Beneficiario");
					
					datiBeneficiarioProgettoDTO.setMessage("Il soggetto \\u00E8 gi\\u00E0 presente sulla banca dati. Verificare denominazione e sede legale ed eventualmente correggerli. "
							                             + "Poi procedere con il Cambio Beneficiario");
				}
				
			}else {
				//caso finpiemonte
				
				if(datiBeneficiarioProgettoDTO == null) {
					LOG.debug(prf + "Beneficiario subentrante non trovato per cf ");
					LOG.debug(prf + "Il soggetto con codice fiscale: "+ beneficiarioSubentrante
							      + " non \u00E8   presente sulla banca dati. Inserire denominazione e sede legale. Poi procedere con il Cambio Beneficiario");
					
					datiBeneficiarioProgettoDTO = new DatiBeneficiarioProgettoDTO();
					datiBeneficiarioProgettoDTO.setMessage("Il soggetto con codice fiscale: "+ beneficiarioSubentrante
							                             + " non \u00E8   presente sulla banca dati. Inserire denominazione e sede legale. Poi procedere con il Cambio Beneficiario");


				}else {
					datiBeneficiarioProgettoDTO.setMessage("Il soggetto \\u00E8 gi\\u00E0 presente sulla banca dati. Procedere con il Cambio Beneficiario");
				}
				
			}
			
		}catch (Exception e) {	
			LOG.error(prf + e);
			throw e;
		}
			
		LOG.debug(prf + " END");
		
		return Response.ok().entity(datiBeneficiarioProgettoDTO).build();
		
	}

	@Override
	public Response cambiaBeneficiario(Long idU, DatiBeneficiarioProgettoDTO datiBeneficiarioProgetto, boolean isAggiornabile, HttpServletRequest req) throws Exception {
		
		String prf = "[CambiaBeneficiarioServiceImpl::cambiaBeneficiario]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", BeneficiarioCorrente isAggiornabile = "+isAggiornabile+", datiBeneficiarioProgetto =  = "+datiBeneficiarioProgetto.toString());
		
		ResponseCodeMessage resp = new ResponseCodeMessage();
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {
			
			String caso = "";
			if(isAggiornabile){
				caso = "C";//REGIONE
			}else {
				caso = "A";//FINPIEMONTE
			}
			
			Boolean esito = gestioneBackofficeBusinessImpl.cambiaBeneficiario(idU, userInfo.getIdIride(), datiBeneficiarioProgetto, caso);
			
			if (esito) {
				LOG.debug(prf + "La procedura di Cambio Beneficiario \\u00E8 terminata correttamente" );
				resp.setCode("OK");
				resp.setMessage("La procedura di Cambio Beneficiario \\u00E8 terminata correttamente");
			} else {
				LOG.debug(prf + "La procedura di Cambio Beneficiario \\u00E8 terminata con errore" );
				resp.setCode("KO");
				resp.setMessage("La procedura di Cambio Beneficiario \\u00E8 terminata con errore");
			}
			
		}catch (Exception e) {	
			LOG.error(prf + e);
			throw e;
		}
			
		LOG.debug(prf + " END");
		
		return Response.ok().entity(resp).build();
		
	}
	
	
	@Override
	public Response salvaDatiBeneficiario(Long idU, DatiBeneficiarioProgettoDTO datiBeneficiarioProgetto, HttpServletRequest req) throws Exception {
		
		String prf = "[CambiaBeneficiarioServiceImpl::salvaDatiBeneficiario]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", datiBeneficiarioProgetto = "+datiBeneficiarioProgetto.toString());

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {
		
			LOG.debug(prf + "userInfo = " + userInfo);
			datiBeneficiarioProgetto = gestioneBackofficeBusinessImpl.saveDatiBeneficiario(idU, userInfo.getIdIride(), datiBeneficiarioProgetto);
			datiBeneficiarioProgetto.setMessage("Salvataggio nuovo utente avvenuto con successo");
		}catch (Exception e) {	
			LOG.error(prf + "salvaDatiBeneficiario: Errore durante la il salvataggio  ", e);
			datiBeneficiarioProgetto.setMessage("salvaDatiBeneficiario: Errore durante la il salvataggio");
		}
			
		LOG.debug(prf + " END");
		
		return Response.ok().entity(datiBeneficiarioProgetto).build();
		
	}
	

	@Override
	public Response findRegioni(Long idU, HttpServletRequest req) throws Exception {
		
		String prf = "[CambiaBeneficiarioServiceImpl::findRegioni]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU);

		Decodifica[] regioni = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {
		
			regioni = gestioneDatiDiDominioBusinessImpl.findDecodifiche(idU, userInfo.getIdIride(), GestioneDatiDiDominioSrv.REGIONE);
			
		}catch (Exception e) {	
			LOG.error(prf + e);
			throw e;
		}
			
		LOG.debug(prf + " END");
		
		return Response.ok().entity(regioni).build();
		
	}

	@Override
	public Response findProvince(Long idU, String idComune, HttpServletRequest req) throws Exception {
		
		String prf = "[CambiaBeneficiarioServiceImpl::findProvince]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idProvincia = "+idComune);

		Decodifica[] province = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {
		
			province = gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(idU, userInfo.getIdIride(), GestioneDatiDiDominioSrv.PROVINCIA, "idRegione", idComune);
			
		}catch (Exception e) {	
			LOG.error(prf + e);
			throw e;
		}
			
		LOG.debug(prf + " END");
		
		return Response.ok().entity(province).build();
	}

	@Override
	public Response findComune(Long idU, String idProvincia, HttpServletRequest req) throws Exception {
		
		String prf = "[CambiaBeneficiarioServiceImpl::findComune]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idProvincia = "+idProvincia);

		Decodifica[] comune = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {
		
			comune = gestioneDatiDiDominioBusinessImpl.findDecodificheFiltrato(idU, userInfo.getIdIride(), GestioneDatiDiDominioSrv.COMUNE, "idProvincia", idProvincia);
			
		}catch (Exception e) {	
			LOG.error(prf + e);
			throw e;
		}
			
		LOG.debug(prf + " END");
		
		return Response.ok().entity(comune).build();
		
	}
	

}
