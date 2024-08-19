/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.dto.archivioFile.FileDTO;
import it.csi.pbandi.pbservizit.exception.DaoException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatididominio.GestioneDatiDiDominioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedocumentazione.GestioneDocumentazioneBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.irregolarita.IrregolaritaBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.profilazione.ProfilazioneBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.PropostaCertificazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DatiGeneraliDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedocumentazione.EsitoScaricaDocumentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.ChecklistRettificaForfettariaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.DocumentoIrregolaritaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.EsitoSalvaIrregolaritaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.EsitoSalvaRettificaForfettariaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.IrregolaritaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.ProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.RettificaForfettariaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CodiceDescrizioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.profilazione.Beneficiario;
import it.csi.pbandi.pbservizit.pbandisrv.dto.profilazione.FiltroBeneficiarioProgettoSoggettoRuoloDTO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweberog.business.service.RegistroControlliService;
import it.csi.pbandi.pbweberog.dto.CodiceDescrizione;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.registrocontrolli.Allegato;
import it.csi.pbandi.pbweberog.dto.registrocontrolli.FiltroRicercaIrregolarita;
import it.csi.pbandi.pbweberog.dto.registrocontrolli.Irregolarita;
import it.csi.pbandi.pbweberog.dto.registrocontrolli.ModificaIrregolaritaDefinitiva;
import it.csi.pbandi.pbweberog.dto.registrocontrolli.RettificaForfettaria;
import it.csi.pbandi.pbweberog.integration.dao.RegistroControlliDAO;
import it.csi.pbandi.pbweberog.integration.request.RequestModificaIrregolarita;
import it.csi.pbandi.pbweberog.integration.request.RequestSalvaIrregolarita;
import it.csi.pbandi.pbweberog.util.BeanUtil;
import it.csi.pbandi.pbweberog.util.Constants;
import it.csi.pbandi.pbweberog.util.DateUtil;
import it.csi.pbandi.pbweberog.util.NumberUtil;
import it.csi.pbandi.pbweberog.util.StringUtil;

@Service
public class RegistroControlliServiceImpl implements RegistroControlliService{
	
	@Autowired
	protected BeanUtil beanUtil;
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	RegistroControlliDAO registroControlliDAO;
	
	@Autowired
	GestioneDatiDiDominioBusinessImpl gestioneDatiDiDominioBusiness;
	
	@Autowired
	GestioneDatiGeneraliBusinessImpl gestioneDatiGeneraliBusiness;
	
	@Autowired
	private IrregolaritaBusinessImpl irregolaritaBusinessImpl;
	
	@Autowired
	private GestioneDocumentazioneBusinessImpl gestioneDocumentazioneBusinessImpl;
	
	@Autowired
	private ProfilazioneBusinessImpl profilazioneBusinessImpl;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////// SERVIZI PER RICERCA /////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public Response getIrregolarita(HttpServletRequest req, FiltroRicercaIrregolarita filtro)
			throws UtenteException, Exception {
		String prf = "[RegistroControlliServiceImpl::getIrregolarita]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			List<Irregolarita> listIrregolarita = ricercaIrregolarita(userInfo, filtro);
			LOG.debug(prf + " END");
			return Response.ok().entity(listIrregolarita).build();
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public Response getEsitiRegolari(HttpServletRequest req, FiltroRicercaIrregolarita filtro)
			throws UtenteException, Exception {
		String prf = "[RegistroControlliServiceImpl::getEsitiRegolari]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			List<Irregolarita> listEsitiRegolari = ricercaEsitiRegolari(userInfo, filtro);
			LOG.debug(prf + " END");
			return Response.ok().entity(listEsitiRegolari).build();
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public Response getRettifiche(HttpServletRequest req, FiltroRicercaIrregolarita filtro)
			throws UtenteException, Exception {
		String prf = "[RegistroControlliServiceImpl::getRettifiche]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			List<RettificaForfettaria> listEsitiRegolari =  ricercaRettificheForfettarie(userInfo, filtro);
			LOG.debug(prf + " END");
			return Response.ok().entity(listEsitiRegolari).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	
	
	@Override
	public Response findBeneficiari(Long idU, Long idSoggetto, String denominazione, Long idBeneficiario, HttpServletRequest req) throws Exception {
		
		String prf = "[CambiaBeneficiarioServiceImpl::findBeneficiari]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idSoggetto = "+idSoggetto);
		
		Beneficiario[] beneficiario = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {
		
			FiltroBeneficiarioProgettoSoggettoRuoloDTO filtro = new FiltroBeneficiarioProgettoSoggettoRuoloDTO();
			filtro.setIdSoggetto(idSoggetto);
			filtro.setCodiceRuolo(userInfo.getCodiceRuolo());
			beneficiario = profilazioneBusinessImpl.findBeneficiariByProgettoSoggettoRuoloDenominazioneOrIdBen(idU, userInfo.getIdIride(), filtro, denominazione);

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
	public Response findProgettiByBeneficiario(Long idU, Long idSoggetto, Long idSoggettoBeneficiario, HttpServletRequest req) throws Exception {
		
		String prf = "[CambiaBeneficiarioServiceImpl::findProgettiByBeneficiario]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", idSoggetto = "+idSoggetto+", idSoggettoBeneficiario = "+idSoggettoBeneficiario);
		
		it.csi.pbandi.pbservizit.pbandisrv.dto.profilazione.ProgettoDTO[] progettoDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {
		
			FiltroBeneficiarioProgettoSoggettoRuoloDTO filtro = new FiltroBeneficiarioProgettoSoggettoRuoloDTO();
			filtro.setIdSoggettoBeneficiario(idSoggettoBeneficiario);
			filtro.setIdSoggetto(idSoggetto);
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
	
	
	private List<RettificaForfettaria> ricercaRettificheForfettarie(UserInfoSec user,
			FiltroRicercaIrregolarita filtro) throws Exception {
		String prf = "[RegistroControlliServiceImpl::ricercaRettificheForfettarie]";
		LOG.info(prf + " START");
		IrregolaritaDTO filtroSrv = new IrregolaritaDTO();
		filtroSrv.setIdSoggettoBeneficiario(NumberUtil.toNullableLong(filtro.getIdBeneficiario()));
		filtroSrv.setIdProgetto(NumberUtil.toNullableLong(filtro.getIdProgetto()));
		if (!StringUtil.isEmpty(filtro.getDtComunicazioneIrregolarita())) {
			filtroSrv.setDtComunicazione(DateUtil.getDate(filtro.getDtComunicazioneIrregolarita()));
		}
		
		RettificaForfettariaDTO[] elencoRettificheForfettarie = registroControlliDAO.findRettificheForfettarie(user.getIdUtente(), user.getIdIride(), filtroSrv);

		ArrayList<RettificaForfettaria> listaRettificheForfettarie = new ArrayList<RettificaForfettaria>();
		if (elencoRettificheForfettarie != null) {
			for (RettificaForfettariaDTO rf : elencoRettificheForfettarie) {
				RettificaForfettaria rettifica = new RettificaForfettaria();				
				listaRettificheForfettarie.add(beanUtil.transform(rf, RettificaForfettaria.class));
			}
		}
		LOG.debug(prf + " END");
		return listaRettificheForfettarie;
	}

	public java.util.ArrayList<Irregolarita> ricercaIrregolarita(UserInfoSec user, FiltroRicercaIrregolarita filtro) throws Exception{
		String prf = "[RegistroControlliServiceImpl::ricercaIrregolarita]";
		LOG.info(prf + " START");
		IrregolaritaDTO filtroSrv = new IrregolaritaDTO();
		filtroSrv.setIdSoggettoBeneficiario(NumberUtil.toNullableLong(filtro.getIdBeneficiario()));
		filtroSrv.setIdProgetto(NumberUtil.toNullableLong(filtro.getIdProgetto()));
		if (!StringUtil.isEmpty(filtro.getDtComunicazioneIrregolarita())) {
			filtroSrv.setDtComunicazione(DateUtil.getDate(filtro.getDtComunicazioneIrregolarita()));
		}
		if (!StringUtil.isEmpty(filtro.getIdTipoIrregolarita())) {
			filtroSrv.setDescBreveTipoIrregolarita(filtro.getIdTipoIrregolarita());
		}
		if (!StringUtil.isEmpty(filtro.getIdQualificazioneIrregolarita())) {
			filtroSrv.setDescBreveQualificIrreg(filtro.getIdQualificazioneIrregolarita());
		}
		if (!StringUtil.isEmpty(filtro.getIdMetodoIndividuazioneIrregolarita())) {
			filtroSrv.setDescBreveMetodoInd(filtro.getIdMetodoIndividuazioneIrregolarita());
		}
		if (!StringUtil.isEmpty(filtro.getIdStatoAmministrativo())) {
			filtroSrv.setDescBreveStatoAmministrativ(filtro.getIdStatoAmministrativo());
		}
		if (!StringUtil.isEmpty(filtro.getIdStatoFinanziario())) {
			filtroSrv.setDescBreveStatoFinanziario(filtro.getIdStatoFinanziario());
		}
		if (!StringUtil.isEmpty(filtro.getIdDisposizioneComunitariaTrasgredita())) {
			filtroSrv.setDescBreveDispComunitaria(filtro.getIdDisposizioneComunitariaTrasgredita());
		}
		if (!StringUtil.isEmpty(filtro.getIdNaturaSanzioneApplicata())) {
			filtroSrv.setDescBreveNaturaSanzione(filtro.getIdNaturaSanzioneApplicata());
		}

		IrregolaritaDTO[] elencoIrregolarita = registroControlliDAO.findIrregolarita(user.getIdUtente(), user.getIdIride(), filtroSrv);

		ArrayList<Irregolarita> listaIrregolarita = new ArrayList<Irregolarita>();
		if (elencoIrregolarita != null) {
			
			LOG.debug("Trovate elencoIrregolarita.length="+elencoIrregolarita.length );
			
			for (IrregolaritaDTO irr : elencoIrregolarita) {
				Irregolarita irregolarita = new Irregolarita();

				irregolarita.setIdIrregolarita(irr.getIdIrregolarita());
				irregolarita.setDtComunicazioneIrregolarita(DateUtil.formatDate(irr.getDtComunicazione()));
				irregolarita.setCodiceVisualizzatoProgetto(irr.getCodiceVisualizzato());
				irregolarita.setDescrTipoIrregolarita(irr.getDescTipoIrregolarita());
				irregolarita.setDescrQualificazioneIrregolarita(irr.getDescQualificazioneIrreg());
				irregolarita.setDescrDisposizioneComunitariaTrasgredita(irr.getDescDispComunitaria());
				irregolarita.setVersioneIrregolarita(irr.getNumeroVersione());
				irregolarita.setFlagBloccata(irr.getFlagBlocco());
				irregolarita.setFlagProvvedimento(irr.getFlagProvvedimento());
				irregolarita.setRiferimentoIMS(irr.getNumeroIms());
				irregolarita.setDenominazioneBeneficiario(irr.getDenominazioneBeneficiario());
				irregolarita.setImportoIrregolarita(irr.getImportoIrregolarita());
				irregolarita.setImportoAgevolazioneIrreg(irr.getImportoAgevolazioneIrreg());
				irregolarita.setDtIms(DateUtil.formatDate(irr.getDtIms()));

				irregolarita.setIdIrregolaritaProvv(irr.getIdIrregolaritaProvv());
				irregolarita.setDtComunicazioneProvv(DateUtil.formatDate(irr.getDtComunicazioneProvv()));
				irregolarita.setDtFineProvvisoriaProvv(DateUtil.formatDate(irr.getDtFineProvvisoriaProvv()));
				irregolarita.setIdProgettoProvv(irr.getIdProgettoProvv());
				irregolarita.setIdMotivoRevocaProvv(irr.getIdMotivoRevocaProvv());
				irregolarita.setImportoIrregolaritaProvv(irr.getImportoIrregolaritaProvv());
				irregolarita.setImportoAgevolazioneIrregProvv(irr.getImportoAgevolazioneIrregProvv());
				irregolarita.setDtFineValiditaProvv(DateUtil.formatDate(irr.getDtFineValiditaProvv()));

				irregolarita.setDataInizioControlli(DateUtil.formatDate(irr.getDataInizioControlli()));
				irregolarita.setDataFineControlli(DateUtil.formatDate(irr.getDataFineControlli()));
				irregolarita.setTipoControlli(irr.getTipoControlli());
				irregolarita.setDataInizioControlliProvv(DateUtil.formatDate(irr.getDataInizioControlliProvv()));
				irregolarita.setDataFineControlliProvv(DateUtil.formatDate(irr.getDataFineControlliProvv()));
				irregolarita.setTipoControlliProvv(irr.getTipoControlliProvv());
				irregolarita.setDescPeriodoVisualizzata(irr.getDescPeriodoVisualizzata());
				irregolarita.setDescPeriodoVisualizzataProvv(irr.getDescPeriodoVisualizzataProvv());
				irregolarita.setDescCategAnagrafica(irr.getDescCategAnagrafica());
				irregolarita.setDescCategAnagraficaProvv(irr.getDescCategAnagraficaProvv());
				
				Long idIrregolaritaPadre = irr.getIdIrregolaritaCollegata() == null ? irr.getIdIrregolarita() : irr.getIdIrregolaritaCollegata();
				irregolarita.setIdentificativoVersione(idIrregolaritaPadre+"/"+irr.getNumeroVersione());
				
				LOG.debug("irr.getDataCampione()="+irr.getDataCampione());
				irregolarita.setDataCampione(DateUtil.formatDate(irr.getDataCampione()));							
				irregolarita.setDescMotivoRevocaProvv(irr.getDescMotivoRevocaProvv());
				irregolarita.setDescMotivoRevoca(irr.getDescMotivoRevoca());
				
				LOG.debug("NotePraticaUsata()="+irr.getNotePraticaUsata());	
				listaIrregolarita.add(irregolarita);
			}
		}else{
			LOG.debug("Trovato elencoIrregolarita NULL");
		}
		LOG.debug(prf + " END");
		return listaIrregolarita;	
	}
	
	
	public ArrayList<Irregolarita> ricercaEsitiRegolari(UserInfoSec user, FiltroRicercaIrregolarita filtro) throws Exception {		
		String prf = "[RegistroControlliServiceImpl::ricercaEsitiRegolari]";
		LOG.info(prf + " START");
		IrregolaritaDTO filtroSrv = new IrregolaritaDTO();
		filtroSrv.setIdSoggettoBeneficiario(NumberUtil.toNullableLong(filtro.getIdBeneficiario()));
		filtroSrv.setIdProgetto(NumberUtil.toNullableLong(filtro.getIdProgetto()));
		if (!StringUtil.isEmpty(filtro.getDtComunicazioneIrregolarita())) {
			filtroSrv.setDtComunicazione(DateUtil.getDate(filtro.getDtComunicazioneIrregolarita()));
		}
		
		IrregolaritaDTO[] elencoIrregolarita = registroControlliDAO.findEsitiRegolari(user.getIdUtente(), user.getIdIride(), filtroSrv);

		ArrayList<Irregolarita> listaIrregolarita = new ArrayList<Irregolarita>();
		if (elencoIrregolarita != null) {
			LOG.debug("Trovato elencoIrregolarita.length="+elencoIrregolarita.length);
			
			for (IrregolaritaDTO irr : elencoIrregolarita) {
				
				LOG.debug("irr="+irr);
				
				Irregolarita irregolarita = new Irregolarita();

				irregolarita.setIdIrregolarita(irr.getIdEsitoControllo());
				irregolarita.setIdEsitoControllo(irr.getIdEsitoControllo());
				irregolarita.setIdentificativoVersione(irr.getIdEsitoControllo().toString());
				irregolarita.setCodiceVisualizzatoProgetto(irr.getCodiceVisualizzato());
				irregolarita.setDenominazioneBeneficiario(irr.getDenominazioneBeneficiario());
				irregolarita.setEsitoControllo(irr.getEsitoControllo());
				irregolarita.setDescCategAnagrafica(irr.getDescCategAnagrafica());
				irregolarita.setDescPeriodoVisualizzata(irr.getDescPeriodoVisualizzata());
				irregolarita.setTipoControlli(irr.getTipoControlli());
				//new
				irregolarita.setDescrTipoIrregolarita(irr.getDescTipoIrregolarita());
				irregolarita.setDescrBreveTipoIrregolarita(irr.getDescBreveTipoIrregolarita());
				
				irregolarita.setDtComunicazioneIrregolarita(DateUtil.formatDate(irr.getDtComunicazione()));
				irregolarita.setDataInizioControlli(DateUtil.formatDate(irr.getDataInizioControlli()));
				irregolarita.setDataFineControlli(DateUtil.formatDate(irr.getDataFineControlli()));
				irregolarita.setDataCampione(DateUtil.formatDate(irr.getDataCampione()));
				// Jira PBANDI-2921.
				irregolarita.setIdIrregolaritaCollegata(irr.getIdIrregolaritaCollegata());
				
				
				listaIrregolarita.add(irregolarita);
			}
		}
		LOG.debug(prf + " END");
		return listaIrregolarita;
	}


	@Override
	public Response getDettaglioIrregolarita(HttpServletRequest req, Long idU, Long idIrregolarita)
			throws UtenteException, Exception {
		String prf = "[RegistroControlliServiceImpl::getDettaglioIrregolarita]";
		LOG.info(prf + " START");

		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = idU;
		    String idIride = userInfo.getIdIride();	
		    IrregolaritaDTO irregolaritaDTO = irregolaritaBusinessImpl.findDettaglioIrregolarita(idUtente, idIride, idIrregolarita);
//		    IrregolaritaDTO irregolaritaDTO = registroControlliDAO.findDettaglioIrregolarita(idUtente, idIride, idIrregolarita);
		    LOG.debug(prf + " END");
		    return Response.ok().entity(irregolaritaDTO).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	
	@Override
	public Response getDettaglioIrregolaritaProvvisoria(HttpServletRequest req, Long idU, Long idIrregolarita)
			throws UtenteException, Exception {
		String prf = "[RegistroControlliServiceImpl::getDettaglioIrregolarita]";
		LOG.info(prf + " START");

		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = idU;
		    String idIride = userInfo.getIdIride();	
		    IrregolaritaDTO irregolaritaDTO = irregolaritaBusinessImpl.findDettaglioIrregolaritaProvvisoria(idUtente, idIride, idIrregolarita);
		    LOG.debug(prf + " END");
		    return Response.ok().entity(irregolaritaDTO).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	
	@Override
	public Response getIdTipoOperazione(Long idU, Long idProgetto, HttpServletRequest req)
			throws UtenteException, Exception {
		String prf = "[RegistroControlliServiceImpl::getDettaglioIrregolarita]";
		LOG.info(prf + " START");

		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = idU;
		    String idIride = userInfo.getIdIride();	
		    Long idTipoOperazione = null;
		    ProgettoDTO progettoDTO = irregolaritaBusinessImpl.findProgetto(idUtente, idIride, idProgetto);
		    if(progettoDTO != null) {
		    	idTipoOperazione = progettoDTO.getIdTipoOperazione();
		    }
		    LOG.debug(prf + " END");
		    return Response.ok().entity(idTipoOperazione).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	
	@Override
	public Response getDettaglioEsitoRegolare(HttpServletRequest req, Long idEsitoRegolare)
			throws UtenteException, Exception {
		String prf = "[RegistroControlliServiceImpl::getDettaglioEsitoRegolare]";
		LOG.info(prf + " START");

		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
		    String idIride = userInfo.getIdIride();
			IrregolaritaDTO irregolaritaDTO =  registroControlliDAO.findDettaglioEsitoRegolare(idUtente, idIride, idEsitoRegolare);
			LOG.debug(prf + " END");
			return Response.ok().entity(irregolaritaDTO).build();
		} catch(Exception e) {
			throw e;
		}

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////// SERVIZI PER MODIFICA  ///////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public Response modificaIrregolaritaDefinitiva(MultipartFormDataInput multipartFormData) throws InvalidParameterException, Exception {
		
		String prf = "[RegistroControlliServiceImpl::modificaIrregolaritaDefinitiva]";
		LOG.info(prf + " START");
		
		if (multipartFormData == null) {
			throw new InvalidParameterException("multipartFormData non valorizzato.");
		}
		
		String idIride = multipartFormData.getFormDataPart("idIride", String.class, null);
		
		ModificaIrregolaritaDefinitiva modifica =popolaIrregolaria(multipartFormData);
		
		EsitoSalvaIrregolaritaDTO esitoSalvaIrregolaritaDTO  = irregolaritaBusinessImpl.modificaIrregolarita(modifica.getIdU(), idIride, modifica.getIrregolarita(), modifica.isModificaDatiAggiuntivi());
		
		LOG.info(prf + " END");
		return Response.ok().entity(esitoSalvaIrregolaritaDTO).build();
	}
	
	private ModificaIrregolaritaDefinitiva popolaIrregolaria(MultipartFormDataInput multipartFormData) throws IOException, Exception {
		
		String prf = "[RegistroControlliServiceImpl::popolaIrregolaria]";
		LOG.info(prf + " START");
		
		ModificaIrregolaritaDefinitiva modifica = new ModificaIrregolaritaDefinitiva();
		
		try {
			
			if (multipartFormData == null) {
				throw new InvalidParameterException("multipartFormData non valorizzato.");
			}
			
			String descBreveDispComunitaria = multipartFormData.getFormDataPart("descBreveDispComunitaria", String.class, null);
			String descBreveMetodoInd = multipartFormData.getFormDataPart("descBreveMetodoInd", String.class, null);
			String descBreveNaturaSanzione = multipartFormData.getFormDataPart("descBreveNaturaSanzione", String.class, null);
			String descBreveQualificIrreg = multipartFormData.getFormDataPart("descBreveQualificIrreg", String.class, null);
			String descBreveStatoAmministrativ = multipartFormData.getFormDataPart("descBreveStatoAmministrativ", String.class, null);
			String descBreveStatoFinanziario = multipartFormData.getFormDataPart("descBreveStatoFinanziario", String.class, null);
			String descBreveTipoIrregolarita = multipartFormData.getFormDataPart("descBreveTipoIrregolarita", String.class, null);
			String dataComunicazione = multipartFormData.getFormDataPart("dataComunicazione", String.class, null);
			
			Long idU = multipartFormData.getFormDataPart("idU", Long.class, null);	
			Long idIrregolaritaProvv = multipartFormData.getFormDataPart("idIrregolaritaProvv", Long.class, null);	
			boolean modificaDatiAggiuntivi = multipartFormData.getFormDataPart("modificaDatiAggiuntivi", boolean.class, null);
			String tipoControlli = multipartFormData.getFormDataPart("tipoControlli", String.class, null);
			Long idPeriodo = multipartFormData.getFormDataPart("idPeriodo", Long.class, null);
			Long idCategAnagrafica = multipartFormData.getFormDataPart("idCategAnagrafica", Long.class, null);
			String datacampione = multipartFormData.getFormDataPart("datacampione", String.class, null);
			String dataInizioControlli = multipartFormData.getFormDataPart("dataInizioControlli", String.class, null);
			String dataFineControlli = multipartFormData.getFormDataPart("dataFineControlli", String.class, null);
			Double importoIrregolarita = multipartFormData.getFormDataPart("importoIrregolarita", Double.class, null);
			Double importoAgevolazioneIrreg = multipartFormData.getFormDataPart("importoAgevolazioneIrreg", Double.class, null);
			Double quotaImpIrregCertificato = multipartFormData.getFormDataPart("quotaImpIrregCertificato", Double.class, null);
			String flagProvvedimento = multipartFormData.getFormDataPart("flagProvvedimento", String.class, null);
			Long idMotivoRevoca = multipartFormData.getFormDataPart("idMotivoRevoca", Long.class, null);
			String notePraticaUsata = multipartFormData.getFormDataPart("notePraticaUsata", String.class, null);
			String soggettoResponsabile = multipartFormData.getFormDataPart("soggettoResponsabile", String.class, null);
			String note = multipartFormData.getFormDataPart("note", String.class, null);
			
			Long idProgetto = multipartFormData.getFormDataPart("idProgetto", Long.class, null);
			Long idIrregolarita = multipartFormData.getFormDataPart("idIrregolarita", Long.class, null);
			Long idDocumentoIndexOlaf = multipartFormData.getFormDataPart("idDocumentoIndexOlaf", Long.class, null);
			Long idDocumentoIndexDatiAggiuntivi = multipartFormData.getFormDataPart("idDocumentoIndexDatiAggiuntivi", Long.class, null);
			
			
			modifica.setIrregolarita(new IrregolaritaDTO());
			
			modifica.getIrregolarita().setIdIrregolaritaProvv(idIrregolaritaProvv);
			modifica.getIrregolarita().setDescBreveDispComunitaria(descBreveDispComunitaria);
			modifica.getIrregolarita().setDescMetodoInd(descBreveMetodoInd);
			modifica.getIrregolarita().setDescBreveNaturaSanzione(descBreveNaturaSanzione);
			modifica.getIrregolarita().setDescQualificazioneIrreg(descBreveQualificIrreg);
			modifica.getIrregolarita().setDescBreveStatoAmministrativ(descBreveStatoAmministrativ);
			modifica.getIrregolarita().setDescBreveStatoFinanziario(descBreveStatoFinanziario);
			modifica.getIrregolarita().setDescBreveTipoIrregolarita(descBreveTipoIrregolarita);
			modifica.getIrregolarita().setDtComunicazione(null);
			modifica.getIrregolarita().setDtComunicazione(DateUtil.getDate(dataComunicazione));
			
			modifica.setIdU(idU);
			modifica.setModificaDatiAggiuntivi(modificaDatiAggiuntivi);
			modifica.getIrregolarita().setTipoControlli(tipoControlli);
			modifica.getIrregolarita().setIdPeriodo(idPeriodo);
			modifica.getIrregolarita().setIdCategAnagrafica(idCategAnagrafica);
			modifica.getIrregolarita().setDataCampione(DateUtil.getDate(datacampione));
			modifica.getIrregolarita().setDataInizioControlli(DateUtil.getDate(dataInizioControlli));
			modifica.getIrregolarita().setDataFineControlli(DateUtil.getDate(dataFineControlli));
			modifica.getIrregolarita().setImportoIrregolarita(importoIrregolarita);
			modifica.getIrregolarita().setImportoAgevolazioneIrreg(importoAgevolazioneIrreg);
			modifica.getIrregolarita().setQuotaImpIrregCertificato(quotaImpIrregCertificato);
			modifica.getIrregolarita().setFlagProvvedimento(flagProvvedimento);
			modifica.getIrregolarita().setIdMotivoRevoca(idMotivoRevoca);
			modifica.getIrregolarita().setNotePraticaUsata(notePraticaUsata);
			modifica.getIrregolarita().setSoggettoResponsabile(soggettoResponsabile);
			modifica.getIrregolarita().setNote(note);	
			modifica.getIrregolarita().setIdProgetto(idProgetto);
			modifica.getIrregolarita().setIdIrregolarita(idIrregolarita);

			// Estae i file dal multipart.
			Map<String, List<InputPart>> mapOlaf = multipartFormData.getFormDataMap();
			List<InputPart> filemapOlaf = mapOlaf.get("olaf");
			ArrayList<FileDTO> filesDTO = leggiFilesDaMultipart(filemapOlaf);
			modifica.getIrregolarita().setSchedaOLAF(new DocumentoIrregolaritaDTO());
			modifica.getIrregolarita().getSchedaOLAF().setIdDocumentoIndex(idDocumentoIndexOlaf);
			modifica.getIrregolarita().getSchedaOLAF().setBytesDocumento(  filesDTO.get(0).getBytes() );
			modifica.getIrregolarita().getSchedaOLAF().setNomeFile(  filesDTO.get(0).getNomeFile() );
			
			if(modificaDatiAggiuntivi) {
				// Estae i file dal multipart.
				Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
				List<InputPart> files = map.get("datiAggiuntivi");
				ArrayList<FileDTO> fileDtoDatiAggiuntivi = leggiFilesDaMultipart(files);
				modifica.getIrregolarita().setDatiAggiuntivi(new DocumentoIrregolaritaDTO());
				modifica.getIrregolarita().getDatiAggiuntivi().setIdDocumentoIndex(idDocumentoIndexDatiAggiuntivi);
				modifica.getIrregolarita().getDatiAggiuntivi().setBytesDocumento(  fileDtoDatiAggiuntivi.get(0).getBytes() );
				modifica.getIrregolarita().getDatiAggiuntivi().setNomeFile(  fileDtoDatiAggiuntivi.get(0).getNomeFile() );
			}
		} catch (IOException e) {
			LOG.error(e);
			throw e;
		}catch (Exception e) {
			LOG.error(e);
			throw e;
		}
		
		LOG.info(prf + " END");
		
		return modifica;
	}
	
//	@Override
//	public Response modificaIrregolaritaDefinitiva2(ModificaIrregolaritaDefinitiva modifica, HttpServletRequest req ) throws UtenteException, Exception {
//		
//		String prf = "[RegistroControlliServiceImpl::modificaIrregolaritaDefinitiva]";
//		LOG.info(prf + " START");
//		HttpSession session = req.getSession();
//		UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
//		String idIride = userInfo.getIdIride();
//		EsitoSalvaIrregolaritaDTO esitoSalvaIrregolaritaDTO = null;
//		
//
//		byte[] byteFile = FileUtil.getBytesFromFile(modifica.getOlaf());
//		modifica.getIrregolarita().getSchedaOLAF().setBytesDocumento(  byteFile  );
//		
//		if(modifica.getZipFile() != null) {
//			byteFile = FileUtil.getBytesFromFile(modifica.getZipFile());
//			modifica.getIrregolarita().getDatiAggiuntivi().setBytesDocumento(  byteFile  );
//		}
//		
//		esitoSalvaIrregolaritaDTO = irregolaritaBusinessImpl.modificaIrregolarita(modifica.getIdU(), idIride, modifica.getIrregolarita(), modifica.isModificaDatiAggiuntivi());
//		
//		LOG.info(prf + " END");
//		
//		return Response.ok().entity(esitoSalvaIrregolaritaDTO).build();
//		
//	}
	
//	@Override
//	public Response modificaIrregolarita(HttpServletRequest req, RequestModificaIrregolarita modificaRequest)
//			throws UtenteException, Exception {
//		String prf = "[RegistroControlliServiceImpl::modificaIrregolarita]";
//		LOG.info(prf + " START");
//
//		try {
//			HttpSession session = req.getSession();
//			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
//			Long idUtente = userInfo.getIdUtente();
//		    String idIride = userInfo.getIdIride();
//		    Long idBeneficiario = userInfo.getBeneficiarioSelezionato().getIdBeneficiario();
//		    String codiceFiscale = userInfo.getBeneficiarioSelezionato().getCodiceFiscale();
//		    Irregolarita irregolarita = modificaRequest.getIrregolarita();		    
//		    Long idProgetto = modificaRequest.getIdProgetto();
//		    
//		    boolean updateDatiAggiuntivi = false;
//		    IrregolaritaDTO  dto = popolaIrregolaritaDto(irregolarita);
//					
//			dto.setIdProgetto(idProgetto);
//			//dto.setCodiceVisualizzato(theModel.getAppDatadatiGenerali().getProgetto().getCodice());			
//			dto.setIdSoggettoBeneficiario(idBeneficiario);
//			dto.setCfBeneficiario(codiceFiscale);
//			/*
//			 * SchedaOLAF
//			 */
//			if (irregolarita.getSchedaOLAF() != null) {
//				Long idDocumentoIndexSchedaOLAF = irregolarita.getSchedaOLAF().getIdDocumentoIndex();
//				DocumentoIrregolaritaDTO schedaOLAF = new DocumentoIrregolaritaDTO();
//				schedaOLAF.setIdDocumentoIndex(idDocumentoIndexSchedaOLAF);
//				if (irregolarita.getSchedaOLAF() != null) {				
//					schedaOLAF.setBytesDocumento(this.getBytes(irregolarita.getSchedaOLAF()));
//					schedaOLAF.setNomeFile(irregolarita.getSchedaOLAF().getNomeFile());		
//				}
//				dto.setSchedaOLAF(schedaOLAF);
//			}
//			/** Dati Aggiuntivi  */
//			DocumentoIrregolaritaDTO datiAggiuntivi = null;
//			if (irregolarita.getDatiAggiuntivi() != null && 
//					irregolarita.getDatiAggiuntivi().getIdDocumentoIndex() != null) {
//					datiAggiuntivi = new DocumentoIrregolaritaDTO();
//					Long idDocumentoIndexDatiAggiuntivi = irregolarita.getDatiAggiuntivi().getIdDocumentoIndex();
//					datiAggiuntivi.setIdDocumentoIndex(idDocumentoIndexDatiAggiuntivi);
//					datiAggiuntivi.setNomeFile(irregolarita.getDatiAggiuntivi().getNomeFile());
//					datiAggiuntivi.setBytesDocumento(getBytes(irregolarita.getDatiAggiuntivi()));
//					datiAggiuntivi.setNomeFile(irregolarita.getDatiAggiuntivi().getNomeFile());
//			}
//			
//			if (irregolarita.getDatiAggiuntivi() !=  null && irregolarita.getDatiAggiuntivi().getIdDocumentoIndex()!=null) {				
//				updateDatiAggiuntivi = true;
//			} else {
//				datiAggiuntivi = new DocumentoIrregolaritaDTO();
//			}
//			dto.setDatiAggiuntivi(datiAggiuntivi);
//			EsitoSalvaIrregolaritaDTO esito = registroControlliDAO.modificaIrregolarita(idUtente, idIride, dto, updateDatiAggiuntivi);
//		    LOG.debug(prf + " END");
//		    return Response.ok().entity(esito).build();
//		} catch(Exception e) {
//			throw e;
//		}
//	}

	private byte[] getBytes(Allegato schedaOLAF) {
//		FileInputStream fis = new FileInputStream(schedaOLAF.ge);
//		byte []  content = new byte [fis.available()];
//		fis.read(content);
//		return content;
		return null;
	}


	@Override
	public Response modificaIrregolaritaProvvisoria(HttpServletRequest req, RequestModificaIrregolarita modificaRequest)
			throws UtenteException, Exception {
		String prf = "[RegistroControlliServiceImpl::modificaIrregolaritaProvvisoria]";
		LOG.info(prf + " START");

		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
		    String idIride = userInfo.getIdIride();
		    Irregolarita irregolarita = modificaRequest.getIrregolarita();		    
		    Long idProgetto = modificaRequest.getIdProgetto();
		    Long idBeneficiario = userInfo.getBeneficiarioSelezionato().getIdBeneficiario();
		    String codiceFiscale = userInfo.getBeneficiarioSelezionato().getCodiceFiscale();
		    
		    DatiGeneraliDTO datiGenerali = gestioneDatiGeneraliBusiness.caricaDatiGenerali(idUtente, idIride, idProgetto, userInfo.getBeneficiarioSelezionato().getIdBeneficiario());

		    
		    IrregolaritaDTO dto = null;
		    dto = popolaIrregolaritaProvvisoriaDto(irregolarita);
//			dto.setDtFineProvvisoriaProvv(DateUtil.getDataOdiernaSenzaOra());
			
			dto.setIdProgetto(idProgetto);
			dto.setCodiceVisualizzato(datiGenerali.getCodiceProgetto());
			dto.setIdSoggettoBeneficiario(idBeneficiario);
			dto.setCfBeneficiario(codiceFiscale);
			
			EsitoSalvaIrregolaritaDTO esito = registroControlliDAO.modificaIrregolaritaProvvisoria(idUtente, idIride, dto);
		    LOG.debug(prf + " END");
		    return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	private IrregolaritaDTO popolaIrregolaritaDto(Irregolarita irr) {
		IrregolaritaDTO dto = new IrregolaritaDTO();
		dto.setCodiceVisualizzato(irr.getCodiceVisualizzatoProgetto());
		dto.setDescBreveDispComunitaria(irr.getDescrBreveDisposizioneComunitariaTrasgredita());
		dto.setDescBreveMetodoInd(irr.getDescrBreveMetodoIndividuazioneIrregolarita());
		dto.setDescBreveNaturaSanzione(irr.getDescrBreveNaturaSanzioneApplicata());
		dto.setDescBreveQualificIrreg(irr.getDescrBreveQualificazioneIrregolarita());
		dto.setDescBreveStatoAmministrativ(irr.getDescrBreveStatoAmministrativo());
		dto.setDescBreveStatoFinanziario(irr.getDescrBreveStatoFinanziario());
		dto.setDescBreveTipoIrregolarita(irr.getDescrBreveTipoIrregolarita());
		dto.setDtComunicazione(DateUtil.getDate(irr.getDtComunicazioneIrregolarita()));
		dto.setDtIms(DateUtil.getDate(irr.getDtIms()));		

		dto.setFlagProvvedimento(irr.getFlagProvvedimento());
		dto.setTipoControlli(irr.getTipoControlli());
		dto.setDataInizioControlli(DateUtil.getDate(irr.getDataInizioControlli()));
		dto.setDataFineControlli(DateUtil.getDate(irr.getDataFineControlli()));
		dto.setIdMotivoRevoca(irr.getIdMotivoRevoca());
		dto.setImportoAgevolazioneIrreg(irr.getImportoAgevolazioneIrreg());

		dto.setFlagBlocco(irr.getFlagBloccata());
		dto.setIdIrregolarita(irr.getIdIrregolarita());
		dto.setNotePraticaUsata(irr.getNotePraticaUsata());
		dto.setNumeroVersione(NumberUtil.toNullableLong(irr.getNumeroVersione()));
		dto.setNumeroIms(irr.getRiferimentoIMS());
		dto.setSoggettoResponsabile(irr.getSoggettoResponsabile());
		dto.setIdIrregolaritaCollegata(irr.getIdIrregolaritaCollegata());
		dto.setImportoIrregolarita(irr.getImportoIrregolarita());
		dto.setQuotaImpIrregCertificato(irr.getQuotaImpIrregCertificato());
		dto.setIdPeriodo(irr.getIdPeriodo());
		dto.setIdCategAnagrafica(irr.getIdCategAnagrafica());
		dto.setIdEsitoControllo(irr.getIdEsitoControllo());
		dto.setNote(irr.getNote());
		
		dto.setDataCampione(null);
		String y1 = irr.getDataCampione();
		
		if(y1!=null && y1!="nc"){ // "nc" se non viene selezionata nessuna "data campione"
			dto.setDataCampione(DateUtil.getDate(irr.getDataCampione()));
		}
		return dto;
	}

	@Override
	public Response modificaEsitoRegolare(HttpServletRequest req, RequestModificaIrregolarita modificaRequest)
			throws UtenteException, Exception {
		String prf = "[RegistroControlliServiceImpl::modificaEsitoRegolare]";
		LOG.info(prf + " START");

		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = modificaRequest.getIdU();
		    String idIride = userInfo.getIdIride();
		    Irregolarita irregolarita = modificaRequest.getIrregolarita();		    
		    Long idProgetto = modificaRequest.getIdProgetto();
		    Long idBeneficiario = modificaRequest.getIdBeneficiario();
		    String codiceFiscale = modificaRequest.getCodiceFiscale();
		    
		    IrregolaritaDTO dto = null;
		    
			dto = popolaIrregolaritaDto(irregolarita);
			dto.setIdProgetto(idProgetto);
			
			String dataComunicazione = modificaRequest.getIrregolarita().getDtComunicazioneProvv();
			Date dtComunicazione=new SimpleDateFormat("dd/MM/yyyy").parse(dataComunicazione);  
			
			dto.setDtComunicazione(dtComunicazione);
			//dto.setCodiceVisualizzato(theModel.getAppDatadatiGenerali().getProgetto().getCodice());
			
			dto.setIdSoggettoBeneficiario(idBeneficiario);
			dto.setCfBeneficiario(codiceFiscale);
			
			
			EsitoSalvaIrregolaritaDTO esito = registroControlliDAO.modificaEsitoRegolare(idUtente, idIride, dto);
		    LOG.debug(prf + " END");
		    return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	
	@Override
	public Response bloccaSbloccaIrregolarita(Long idU, Long idIrregolarita, HttpServletRequest req) throws Exception {
		
		String prf = "[RegistroControlliServiceImpl::bloccaIrregolarita]";
		LOG.info(prf + " START");
		
		EsitoSalvaIrregolaritaDTO esitoSalvaIrregolaritaDTO = irregolaritaBusinessImpl.bloccaSbloccaIrregolarita(idU, prf, idIrregolarita);
		
		 LOG.debug(prf + " END");
		 
		 return Response.ok().entity(esitoSalvaIrregolaritaDTO).build();
		
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////// SERVIZI PER INSERIMENTO /////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public Response inserisciIrregolaritaRegolare(HttpServletRequest req, RequestSalvaIrregolarita salvaRequest)
			throws UtenteException, Exception {
		String prf = "[CertificazioneServiceImpl::inserisciIrregolaritaRegolare]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = salvaRequest.getIdU();
		    String idIride = userInfo.getIdIride();	
		    
		    Irregolarita irregolarita = salvaRequest.getIrregolarita();
		    Long idProgetto = salvaRequest.getIdProgetto();
		    IrregolaritaDTO dto = popolaIrregolaritaDto(irregolarita);
		    		    
		    DatiGeneraliDTO datiGenerali = gestioneDatiGeneraliBusiness.caricaDatiGenerali(idUtente, idIride, idProgetto, salvaRequest.getIdBeneficiario() );
		   // ProgettoDTO progetto = registroControlliDAO.findProgetto(idUtente, idIride, idProgetto);
		    
		    dto.setIdProgetto(idProgetto);
		    dto.setCodiceVisualizzato(datiGenerali.getCodiceProgetto());
		    dto.setIdSoggettoBeneficiario(salvaRequest.getIdBeneficiario() );
		    dto.setCfBeneficiario(salvaRequest.getCodiceFiscale());
		    
			EsitoSalvaIrregolaritaDTO esito = registroControlliDAO.creaEsitoRegolare(idUtente, idIride, dto);
			LOG.info(prf + " END");
			return Response.ok(esito).build();
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
	}
	
	@Override
	public Response inserisciIrregolarita(MultipartFormDataInput multipartFormData) throws InvalidParameterException, Exception {
		
		String prf = "[RegistroControlliServiceImpl::inserisciIrregolarita]";
		LOG.info(prf + " START");
		
		if (multipartFormData == null) {
			throw new InvalidParameterException("multipartFormData non valorizzato.");
		}
		
		String idIride = multipartFormData.getFormDataPart("idIride", String.class, null);
		
		ModificaIrregolaritaDefinitiva modifica = popolaIrregolaria(multipartFormData);
		modifica.getIrregolarita().setDtComunicazione(DateUtil.getDataOdiernaSenzaOra());
		
		
		EsitoSalvaIrregolaritaDTO esitoSalvaIrregolaritaDTO  = irregolaritaBusinessImpl.creaIrregolarita(modifica.getIdU(), idIride, modifica.getIrregolarita());
		
		LOG.info(prf + " END");
		return Response.ok().entity(esitoSalvaIrregolaritaDTO).build();
		
	}

//	@Override
//	public Response inserisciIrregolarita2(HttpServletRequest req, RequestSalvaIrregolarita salvaRequest)
//			throws UtenteException, Exception {
//		String prf = "[CertificazioneServiceImpl::inserisciIrregolarita2]";
//		LOG.info(prf + " BEGIN");
//		try {
//			HttpSession session = req.getSession();
//			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
//			Long idUtente = userInfo.getIdUtente();
//		    String idIride = userInfo.getIdIride();	
//		    
//		    Irregolarita irregolarita = salvaRequest.getIrregolarita();
//		    Long idProgetto = salvaRequest.getIdProgetto();
//		    IrregolaritaDTO dto = popolaIrregolaritaDto(irregolarita);
//		    
//		    if (Constants.IRREGOLARITA_TRASFORMA_IN_DEFINITIVA.equalsIgnoreCase(irregolarita.getTrasformaIn())) {
//				dto.setIdIrregolaritaProvv(irregolarita.getIdIrregolaritaProvv());
//			}	    		    
//		    DatiGeneraliDTO datiGenerali = gestioneDatiGeneraliBusiness.caricaDatiGenerali(idUtente, idIride, idProgetto, userInfo.getBeneficiarioSelezionato().getIdBeneficiario());
//		   // ProgettoDTO progetto = registroControlliDAO.findProgetto(idUtente, idIride, idProgetto);
//		    
//		    dto.setIdProgetto(idProgetto);
//		    dto.setCodiceVisualizzato(datiGenerali.getCodiceProgetto());
//		    dto.setIdSoggettoBeneficiario(userInfo.getBeneficiarioSelezionato().getIdBeneficiario());
//		    dto.setCfBeneficiario(userInfo.getBeneficiarioSelezionato().getCodiceFiscale());
//		    
//		    DocumentoIrregolaritaDTO schedaOLAF = new DocumentoIrregolaritaDTO();
//			schedaOLAF.setNomeFile(irregolarita.getSchedaOLAF().getNomeFile());
//			schedaOLAF.setBytesDocumento(getBytes(irregolarita.getSchedaOLAF()));
//			dto.setSchedaOLAF(schedaOLAF);
//			
//			if (irregolarita.getDatiAggiuntivi() != null) {
//				DocumentoIrregolaritaDTO datiAggiuntivi = new DocumentoIrregolaritaDTO();
//				datiAggiuntivi.setNomeFile(irregolarita.getDatiAggiuntivi().getNomeFile());
//				datiAggiuntivi.setBytesDocumento(getBytes(irregolarita.getDatiAggiuntivi()));
//				dto.setDatiAggiuntivi(datiAggiuntivi);
//			}
//			
//			EsitoSalvaIrregolaritaDTO esito = registroControlliDAO.creaIrregolarita(idUtente, idIride, dto);
//			LOG.info(prf + " END");
//			return Response.ok(esito).build();
//		} catch (Exception e) {
//			LOG.error(prf + e.getMessage());
//			throw e;
//		}
//	}

	@Override
	public Response inserisciIrregolaritaProvvisoria(HttpServletRequest req, RequestSalvaIrregolarita salvaRequest)
			throws UtenteException, Exception {
		String prf = "[CertificazioneServiceImpl::inserisciIrregolaritaProvvisoria]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = salvaRequest.getIdU();
		    String idIride = userInfo.getIdIride();	
		    
		    Irregolarita irregolarita = salvaRequest.getIrregolarita();
		    Long idProgetto = salvaRequest.getIdProgetto();
		    IrregolaritaDTO dto = popolaIrregolaritaProvvisoriaDto(irregolarita);
		    
		    if (Constants.IRREGOLARITA_TRASFORMA_IN_DEFINITIVA.equalsIgnoreCase(irregolarita.getTrasformaIn())) {
				dto.setIdIrregolaritaProvv(irregolarita.getIdIrregolaritaProvv());
			}	    		    
		    DatiGeneraliDTO datiGenerali = gestioneDatiGeneraliBusiness.caricaDatiGenerali(idUtente, idIride, idProgetto, salvaRequest.getIdBeneficiario());
		   // ProgettoDTO progetto = registroControlliDAO.findProgetto(idUtente, idIride, idProgetto);
		    
		    dto.setIdProgetto(idProgetto);
		    dto.setCodiceVisualizzato(datiGenerali.getCodiceProgetto());
		    dto.setIdSoggettoBeneficiario(salvaRequest.getIdBeneficiario());
		    dto.setCfBeneficiario(salvaRequest.getCodiceFiscale());
		    dto.setIdProgettoProvv(salvaRequest.getIrregolarita().getIdProgettoProvv());		
		    
			EsitoSalvaIrregolaritaDTO esito = registroControlliDAO.creaIrregolaritaProvvisoria(idUtente, idIride, dto);
			LOG.info(prf + " END");
			return Response.ok(esito).build();
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
	}

	private IrregolaritaDTO popolaIrregolaritaProvvisoriaDto(Irregolarita irr) {
		String prf = "[RegistroControlliServiceImpl::popolaIrregolaritaProvvisoriaDto]";
		LOG.info(prf + " START");
		IrregolaritaDTO dto = new IrregolaritaDTO();
		dto.setIdIrregolarita(irr.getIdIrregolarita());
		dto.setIdIrregolaritaProvv(irr.getIdIrregolaritaProvv());
		dto.setDtComunicazioneProvv(DateUtil.getDate(irr.getDtComunicazioneProvv()));
		dto.setDtFineProvvisoriaProvv(null);
		if ("true".equalsIgnoreCase(irr.getFlagIrregolaritaAnnullataProvv())) 
			dto.setFlagIrregolaritaAnnullataProvv("S");
		else 
			dto.setFlagIrregolaritaAnnullataProvv("N");
		dto.setIdMotivoRevocaProvv(irr.getIdMotivoRevocaProvv());
		dto.setImportoIrregolaritaProvv(irr.getImportoIrregolaritaProvv());
		dto.setImportoAgevolazioneIrregProvv(irr.getImportoAgevolazioneIrregProvv());
		dto.setImportoIrregolareCertificatoProvv(irr.getImportoIrregolareCertificatoProvv());
		dto.setDataInizioControlliProvv(DateUtil.getDate(irr.getDataInizioControlliProvv()));
		dto.setDataFineControlliProvv(DateUtil.getDate(irr.getDataFineControlliProvv()));
		dto.setTipoControlliProvv(irr.getTipoControlliProvv());
		dto.setIdPeriodoProvv(irr.getIdPeriodoProvv());
		dto.setIdCategAnagraficaProvv(irr.getIdCategAnagraficaProvv());
		dto.setNoteProvv(irr.getNoteProvv());
		
		LOG.debug("DataCampione : " + irr.getDataCampione());
		LOG.debug("DataCampioneProvv : " + irr.getDataCampioneProvv());
		dto.setDataCampione(DateUtil.getDate(irr.getDataCampioneProvv()));
		
		LOG.debug("IdPeriodoProvv : "+irr.getIdPeriodoProvv()+"; "+dto.getIdPeriodoProvv());
		LOG.debug("IdCategAnagraficaProvv : "+irr.getIdCategAnagraficaProvv()+"; "+dto.getIdCategAnagraficaProvv());
		LOG.debug("noteProvv : "+irr.getNoteProvv()+"; "+dto.getNoteProvv());
		LOG.info(prf + " END");
		return dto;

	}

	@Override
	public Response getContenutoDocumentoById(HttpServletRequest req, Long idDocumentoIndex)
			throws UtenteException, Exception {
		String prf = "[RegistroControlliServiceImpl::getContenutoDocumentoById]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
		    String idIride = userInfo.getIdIride();	
			if (idUtente == null) {
				return Response.serverError().entity("idUtente non valido").build();
			}
//			EsitoGenerazioneReportDTO esito = registroControlliDAO.getContenutoDocumentoById(idUtente, idIride, idDocumentoIndex);
			EsitoScaricaDocumentoDTO esito2 = gestioneDocumentazioneBusinessImpl.scaricaDocumento(idUtente, idIride, idDocumentoIndex);
			LOG.info(prf + " END");
			return Response.ok(esito2.getBytesDocumento()).build();
//			return Response.ok(esito.getReport()).build();
		} catch (Exception e) {
			LOG.error(prf + e.getMessage());
			throw e;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////// SERVIZI PER CANCELLAZIONE ////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public Response cancellaIrregolaritaRegolare(HttpServletRequest req, Long idIrregolarita)
			throws UtenteException, Exception {
		String prf = "[RegistroControlliServiceImpl::cancellaIrregolaritaRegolare]";
		LOG.info(prf + " START");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
		    String idIride = userInfo.getIdIride();
		    
		    EsitoSalvaIrregolaritaDTO esito = registroControlliDAO.cancellaIrregolaritaRegolare(idUtente, idIride, idIrregolarita);
		    LOG.debug(prf + " END");
		    return Response.ok().entity(esito).build();
		   
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public Response cancellaIrregolarita(HttpServletRequest req, Long idIrregolarita)
			throws UtenteException, Exception {
		String prf = "[RegistroControlliServiceImpl::cancellaIrregolarita]";
		LOG.info(prf + " START");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
		    String idIride = userInfo.getIdIride();
		    
		    EsitoSalvaIrregolaritaDTO esito = registroControlliDAO.cancellaIrregolarita(idUtente, idIride, idIrregolarita);
		    LOG.debug(prf + " END");
		    return Response.ok().entity(esito).build();
		   
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public Response cancellaIrregolaritaProvvisoria(HttpServletRequest req, Long idIrregolarita)
			throws UtenteException, Exception {
		String prf = "[RegistroControlliServiceImpl::cancellaIrregolaritaProvvisoria]";
		LOG.info(prf + " START");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
		    String idIride = userInfo.getIdIride();
		    
		    EsitoSalvaIrregolaritaDTO esito = registroControlliDAO.cancellaIrregolaritaProvvisoria(idUtente, idIride, idIrregolarita);
		    LOG.debug(prf + " END");
		    return Response.ok().entity(esito).build();
		   
		} catch(Exception e) {
			throw e;
		}
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////// SERVIZI PER LE COMBO /////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public Response getPeriodi(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[RegistroControlliServiceImpl::getPeriodi]";
		LOG.info(prf + " START");

		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
		    String idIride = userInfo.getIdIride();
		    Decodifica[] decodifiche = gestioneDatiDiDominioBusiness.findDecodificheFiltrato( idUtente, idIride,  GestioneDatiDiDominioSrv.PERIODO, "idTipoPeriodo", "3");
			ArrayList<CodiceDescrizione> codici = new ArrayList<CodiceDescrizione>();
			if (decodifiche != null) {
				for (Decodifica decodifica : decodifiche) {				
					CodiceDescrizione codice = new CodiceDescrizione();
					codice.setCodice(NumberUtil.getStringValue(decodifica.getId()));
					codice.setDescrizione(decodifica.getDescrizione());
					codici.add(codice);
				}
			}
			LOG.debug(prf + " END");
			return Response.ok().entity(codici).build();
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public Response getCategAnagrafica(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[RegistroControlliServiceImpl::getCategAnagrafica]";
		LOG.info(prf + " START");

		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
		    String idIride = userInfo.getIdIride();
		    Decodifica[] decodifiche = gestioneDatiDiDominioBusiness.findDecodifiche(idUtente, idIride,  GestioneDatiDiDominioSrv.CATEG_ANAGRAFICA);
		    ArrayList<CodiceDescrizione> codici = new ArrayList<CodiceDescrizione>();
		    if (decodifiche != null) {
				for (Decodifica decodifica : decodifiche) {				
					CodiceDescrizione codice = new CodiceDescrizione();
					codice.setCodice(NumberUtil.getStringValue(decodifica.getId()));
					codice.setDescrizione(decodifica.getDescrizione());
					codici.add(codice);
				}
		    }
		    LOG.debug(prf + " END");
		    return Response.ok().entity(codici).build();
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public Response getDataCampione(HttpServletRequest req, Long idProgetto, String tipoControlli, Long idPeriodo, Long idCategAnagrafica) throws UtenteException, Exception {
		String prf = "[RegistroControlliServiceImpl::getDataCampione]";
		LOG.info(prf + " START");

		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
		    String idIride = userInfo.getIdIride();
		    CodiceDescrizioneDTO[] dateCampioni = registroControlliDAO.findDataCampione(idUtente, idIride, idProgetto, tipoControlli, idPeriodo, idCategAnagrafica);
		    List<CodiceDescrizione> codici = new ArrayList<CodiceDescrizione>();
		    if (dateCampioni != null) {
				for (CodiceDescrizioneDTO decodifica : dateCampioni) {				
					CodiceDescrizione codice = new CodiceDescrizione();
					codice.setCodice(decodifica.getCodice());
					codice.setDescrizione(decodifica.getDescrizione());
					codici.add(codice);
				}
		    }
		    LOG.debug(prf + " END");
		    return Response.ok().entity(codici).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	
	@Override
	public Response getMotivoIrregolarita(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[RegistroControlliServiceImpl::getMotivoIrregolarita]";
		LOG.info(prf + " START");
		
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
		    String idIride = userInfo.getIdIride();

		    Decodifica[] tipiIrregolarita = gestioneDatiDiDominioBusiness.findDecodifiche(idUtente, idIride, GestioneDatiDiDominioSrv.MOTIVO_REVOCA);
			ArrayList<CodiceDescrizione> comboTipoMotivoRevoca = new ArrayList<CodiceDescrizione>();
			if (tipiIrregolarita != null) {
				for (Decodifica tipoIrregolarita : tipiIrregolarita) {
					if (DateUtil.after(tipoIrregolarita.getDataInizioValidita(), DateUtil.getDate("31/12/2013"))) {
						CodiceDescrizione tipo = new CodiceDescrizione();
						tipo.setCodice(NumberUtil.getStringValue(tipoIrregolarita.getId()));
						tipo.setDescrizione(tipoIrregolarita.getDescrizione());
						comboTipoMotivoRevoca.add(tipo);
					}
				}
			}
			
			LOG.debug(prf + " END");
		    return Response.ok().entity(comboTipoMotivoRevoca).build();
			
		} catch(Exception e) {
			throw e;
		}
	}

	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////// SERVIZI PER RETTIFICHE FORFEITTARIE SU AFFIDAMENTI ////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public Response esistePropostaCertificazioneAperta(HttpServletRequest req, Long idProgetto, Long idBeneficario)
			throws UtenteException, Exception {
		String prf = "[RegistroControlliServiceImpl::esistePropostaCertificazioneAperta]";
		LOG.info(prf + " START");

		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
		    String idIride = userInfo.getIdIride();
		  
		    EsitoOperazioni esito = new EsitoOperazioni();
		    
		    DatiGeneraliDTO datiGenerali = gestioneDatiGeneraliBusiness.caricaDatiGenerali(idUtente, idIride, idProgetto, idBeneficario );
		    Long idLineaDiIntervento = datiGenerali.getIdLineaDiIntervento();
		    PropostaCertificazioneDTO[] lista = registroControlliDAO.findProposteCertificazioneAperteByIdLineaIntervento(idUtente, idIride, idLineaDiIntervento);		   
		    if (lista == null || lista.length == 0) {
		    	esito.setEsito(false);
		    	
		    	
		    }else{
		    	esito.setEsito(true);
		    	String data = DateUtil.getTime(lista[0].getDtOraCreazione());
				String msg = "Attenzione! Esiste la proposta di certificazione: n. "
						+ lista[0].getIdPropostaCertificaz()+ " del " + data
						+ " in stato Aperta. Tutte le rettifiche inserite posteriormente alla data "
						+ data
						+ " verranno prese in considerazione nella proposta di certificazione successiva!";
		    	esito.setMsg(msg);
	    	  
		    }
		    LOG.debug(prf + " END");
		    return Response.ok().entity(esito).build();
		   
		} catch(Exception e) {
			throw e;
		}
	}

	
	@Override
	public Response getChecklistRettifiche(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[RegistroControlliServiceImpl::getChecklistRettifiche]";
		LOG.info(prf + " START");

		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
		    String idIride = userInfo.getIdIride();
		    ChecklistRettificaForfettariaDTO[] lista = registroControlliDAO.findChecklistRettificheForfettarie(idUtente, idIride, idProgetto);
		    LOG.debug(prf + " END");
		    return Response.ok().entity(lista).build();
		   
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public Response salvaRettificaForfettaria(HttpServletRequest req, RettificaForfettariaDTO rettifica)
			throws UtenteException, Exception {
		String prf = "[RegistroControlliServiceImpl::salvaRettificaForfettaria]";
		LOG.info(prf + " START");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
		    String idIride = userInfo.getIdIride();
		    // VALIDATE
		    
		    if (rettifica.getDataInserimento() == null) {
		    	return inviaErroreBadRequest("parametro mancato nel body: ?{RettificaForfettariaDTO.dataInserimento} ");
			}
			if (rettifica.getIdCategAnagrafica() == null) {
				return inviaErroreBadRequest("parametro mancato nel body: Autorita controllante o ?{RettificaForfettariaDTO.idCategAnagrafica} ");	
			}
			if (rettifica.getPercRett() == null) {
				return inviaErroreBadRequest("parametro mancato nel body:  ?{RettificaForfettariaDTO.PercRett} ");	
			}
			
			// SALVA
		    EsitoSalvaRettificaForfettariaDTO esito = registroControlliDAO.salvaRettificaForfettaria(idUtente, idIride, rettifica);
		    LOG.debug(prf + " END");
		    return Response.ok().entity(esito).build();
		   
		} catch(Exception e) {
			throw e;
		}
	}


	@Override
	public Response eliminaRettificaForfettaria(HttpServletRequest req, Long idRettificaForfett)
			throws UtenteException, Exception {
		String prf = "[RegistroControlliServiceImpl::eliminaRettificaForfettaria]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
		    String idIride = userInfo.getIdIride();
		    
		    EsitoSalvaRettificaForfettariaDTO esito = registroControlliDAO.eliminaRettificaForfettaria(idUtente, idIride, idRettificaForfett);
		    LOG.debug(prf + " END");
		    return Response.ok().entity(esito).build();
		   
		} catch(Exception e) {
			throw e;
		}
	}

	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////// SERVIZI PER REGISTRA INVIO ////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Response registraInvioIrregolarita(HttpServletRequest req, Irregolarita irregolarita)
			throws UtenteException, Exception {
		String prf = "[RegistroControlliServiceImpl::registraInvioIrregolarita]";
		LOG.info(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
		    String idIride = userInfo.getIdIride();
		    IrregolaritaDTO  dto = popolaIrregolaritaDto(irregolarita);
		    EsitoSalvaIrregolaritaDTO esito = registroControlliDAO.registraInvioIrregolarita(idUtente, idIride, dto);
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
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	///////////////////////////////////////////// METODI LETTURA FILE DA MULTIPART /////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private ArrayList<FileDTO> leggiFilesDaMultipart(List<InputPart> files ) throws Exception {
		String prf = "[DocumentoManager::leggiFilesDaMultipart] ";
		LOG.info(prf + " BEGIN");
		ArrayList<FileDTO> result = new ArrayList<FileDTO>();
		try {

			String nomeFile = null;
			byte[] content;
			MultivaluedMap<String, String> header;
			
			for (InputPart inputPart : files) {
				
				header = inputPart.getHeaders();				
				nomeFile = getFileName(header);
				content = IOUtils.toByteArray(inputPart.getBody(InputStream.class, null));
								
				FileDTO fileDTO = new FileDTO();
				fileDTO.setBytes(content);
				fileDTO.setNomeFile(nomeFile);				
				fileDTO.setSizeFile(new Long(content.length));
//				fileDTO.setIdFolder(idFolder);	
				LOG.info(prf+"file estratto: nome = " + fileDTO.getNomeFile()+"; size = "+fileDTO.getSizeFile());
			
				result.add(fileDTO);
			}
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE: ", e);
			throw new DaoException("Errore durante la lettura dei file da MultipartFormDataInput.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return result;
	}
	
	private static String getFileName(MultivaluedMap<String, String> header) {
		String[] contentDisposition = header.getFirst(it.csi.pbandi.pbservizit.util.Constants.CONTENT_DISPOSITION).split(";");
		for (String value : contentDisposition) {
			if (value.trim().startsWith(it.csi.pbandi.pbservizit.util.Constants.FILE_NAME_KEY)) {
				String[] name = value.split("=");
				if (name.length > 1) {
					return name[1].trim().replaceAll("\"", "");
				}
			}
		}
		return null;
	}


}
