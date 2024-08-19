/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business.service.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.profilazione.ProfilazioneBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DatiGeneraliDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DelegatoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RappresentanteLegaleDTO;
import it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweberog.business.service.RinunciaService;
import it.csi.pbandi.pbweberog.dto.CodiceDescrizione;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.erogazione.DichiarazioneDiRinunciaDTO;
import it.csi.pbandi.pbweberog.dto.erogazione.EsitoErogazioneDTO;
import it.csi.pbandi.pbweberog.dto.erogazione.EsitoScaricaDichiarazioneDiRinuncia;
import it.csi.pbandi.pbweberog.dto.rinuncia.DichiarazioneRinuncia;
import it.csi.pbandi.pbweberog.dto.rinuncia.ResponseCreaCommunicazioneRinuncia;
import it.csi.pbandi.pbweberog.integration.dao.ErogazioneDAO;
import it.csi.pbandi.pbweberog.integration.dao.FideiussioneDAO;
import it.csi.pbandi.pbweberog.integration.dao.RinunciaDAO;
import it.csi.pbandi.pbweberog.integration.request.CreaComunicazioneRinunciaRequest;
import it.csi.pbandi.pbweberog.util.BeanUtil;
import it.csi.pbandi.pbweberog.util.Constants;
import it.csi.pbandi.pbweberog.util.ErrorMessages;
import it.csi.pbandi.pbweberog.util.MessageConstants;

@Service
public class RinunciaServiceImpl implements RinunciaService{
	@Autowired
	protected BeanUtil beanUtil;
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	FideiussioneDAO fideiussioneDAO;
	
	@Autowired
	GestioneDatiGeneraliBusinessImpl datiGeneraliBusinessImpl;
	
	@Autowired
	RinunciaDAO rinunciaDAO;
	
	@Autowired
	ProfilazioneBusinessImpl profilazioneBusiness;
	
	@Autowired
	ErogazioneDAO erogazioneDAO;

	

	@Override
	public Response getImportoDaRestituire(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[RinunciaServiceImpl::getImportoDaRestituire]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			DatiGeneraliDTO datiGenerali = datiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride, idProgetto, userInfo.getBeneficiarioSelezionato().getIdBeneficiario());
			Long idBandoLinea = datiGenerali.getIdBandoLinea();
			Long idFormaGiuridica = datiGenerali.getIdFormaGiuridica();
			Long idDimensioneImpresa = datiGenerali.getIdDimensioneImpresa();

			EsitoErogazioneDTO erogazione = erogazioneDAO.findErogazione(idUtente, idIride, idProgetto, idBandoLinea, idFormaGiuridica, idDimensioneImpresa);
			
			LOG.debug(prf + " END");
			return Response.ok().entity(erogazione.getErogazione().getImportoTotaleErogato()).build();
		} catch(Exception e) {
			throw e;
		}
	}


	
	@Override
	public Response getRappresentantiLegali(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[RinunciaServiceImpl::getRappresentantiLegali]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			RappresentanteLegaleDTO[] rappresentantiDTO = rinunciaDAO.findRappresentantiLegali(idUtente, idIride, idProgetto, null);
			ArrayList<CodiceDescrizione> rappresentanti = new ArrayList<CodiceDescrizione>();
			if (rappresentantiDTO != null) {
				for (RappresentanteLegaleDTO rapp : rappresentantiDTO){
					CodiceDescrizione rappresentante = new CodiceDescrizione();
					rappresentante.setCodice(String.valueOf(rapp.getIdSoggetto()));
					rappresentante.setDescrizione(rapp.getCognome()+" "+rapp.getNome()+" - "+rapp.getCodiceFiscaleSoggetto());
					rappresentanti.add(rappresentante);
				}
			}
			
			LOG.debug(prf + " END");
			return Response.ok().entity(rappresentanti).build();
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public Response getDelegati(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[RinunciaServiceImpl::getDelegati]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			DelegatoDTO[] delegati = datiGeneraliBusinessImpl.findDelegati(idUtente, idIride , idProgetto);
			
			LOG.debug(prf + " END");
			return Response.ok().entity(delegati).build();
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public Response creaComunicazioneRinuncia(HttpServletRequest req, CreaComunicazioneRinunciaRequest creaRequest)
			throws UtenteException, Exception {
		String prf = "[RinunciaServiceImpl::creaComunicazioneRinuncia]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			
			LOG.info(prf + "!!!!!LOG!!!!! session --------->>>>>" + session + " \n");
			System.out.println("!!!!!LOG!!!!! session --------->>>>>" + session + " \n");
			
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			
			LOG.info(prf + "!!!!!LOG!!!!! userInfo --------->>>>>" + userInfo + " \n");
			System.out.println("!!!!!LOG!!!!! userInfo --------->>>>>" + userInfo + " \n");
			
			Long idUtente = userInfo.getIdUtente();
			
			LOG.info(prf + "!!!!!LOG!!!!! idUtente --------->>>>>" + idUtente + " \n");
			System.out.println("!!!!!LOG!!!!! idUtente --------->>>>>" + idUtente + " \n");
			
			
			String idIride = userInfo.getIdIride();
			
			Long idProgetto = creaRequest.getIdProgetto();
			DichiarazioneRinuncia dichiarazioneRinuncia = creaRequest.getDichiarazioneRinuncia();
			Long idDelegato = null;
			if(creaRequest.getIdDelegato()!=null)
				idDelegato = creaRequest.getIdDelegato();

			//validate oggetto DichiarazioneDiRinuncia
			if(dichiarazioneRinuncia.getDtComunicazioneRinuncia()==null)
				return inviaErroreBadRequest("parametro mancato nel body: ?{dichiarazioneRinuncia.dtComunicazioneRinuncia}");
			if(dichiarazioneRinuncia.getGiorniRestituzione()==null)
				return inviaErroreBadRequest("parametro mancato nel body: ?{dichiarazioneRinuncia.giorniRestituzione}");
			if(dichiarazioneRinuncia.getImportoDaRestituire()==null)
				return inviaErroreBadRequest("parametro mancato nel body: ?{dichiarazioneRinuncia.importoDaRestituire}");
			if(dichiarazioneRinuncia.getMotivazione()==null)
				return inviaErroreBadRequest("parametro mancato nel body: ?{dichiarazioneRinuncia.motivazione}");
			if(dichiarazioneRinuncia.getIdRappresentanteLegale()==null)
				return inviaErroreBadRequest("parametro mancato nel body: ?{dichiarazioneRinuncia.idRappresentanteLegale}");
			if( !DateUtil.isValidDate(dichiarazioneRinuncia.getDtComunicazioneRinuncia()) ) {
				return inviaErroreBadRequest("formato del parametro non valido: ?{dichiarazioneRinuncia.dtComunicazioneRinuncia}");
			}
			if (DateUtil.getDate(dichiarazioneRinuncia.getDtComunicazioneRinuncia()).after(
					DateUtil.getDataOdiernaSenzaOra())) {
				return inviaErroreBadRequest(ErrorMessages.ERR_RINUNCIA_DATA_MAGGIORE_ODIERNA) ;
			} else {
				//invia Dichiarazione Di Rinuncia
				@SuppressWarnings("unchecked")
				DichiarazioneDiRinunciaDTO dichiarazioneDTO = beanUtil.transform(
						dichiarazioneRinuncia, DichiarazioneDiRinunciaDTO.class, new HashMap() {
							{
								put("dtComunicazioneRinuncia","dataRinuncia");
								put("giorniRestituzione","giorniRinuncia");
								put("idDelegato","idDelegato");
								put("idRappresentanteLegale","idRappresentanteLegale");
								put("importoDaRestituire","importoDaRestituire");
								put("motivazione","motivoRinuncia");
								put("uuidDocumentoIndex","uuidNodoIndex");
							}
						});
				dichiarazioneDTO.setIdProgetto(idProgetto);
				LOG.info(prf + "XXX idDelegato: "+idDelegato);
				dichiarazioneDTO.setIdDelegato(idDelegato);
				
				DichiarazioneDiRinunciaDTO resDTO = rinunciaDAO.inviaDichiarazioneDiRinuncia(idUtente, idIride, dichiarazioneDTO);
				Boolean invioDigitale = profilazioneBusiness.isRegolaApplicabileForProgetto(idUtente, idIride, idProgetto, RegoleConstants.BR42_ABILITAZIONE_ALLEGA_FILE);
				
				ResponseCreaCommunicazioneRinuncia result = new ResponseCreaCommunicazioneRinuncia();
				result.setDichiarazioneRinuncia(resDTO);
				result.setInvioDigitale(invioDigitale);
				result.setMessage(MessageConstants.MSG_RINUNCIA_ELABORATA_PREDISPOSTA);
				
				LOG.debug(prf + " END");
				return Response.ok().entity(result).build();
			}
			
			
		} catch(Exception e) {
			throw e;
		}
	}


	@Override
	public Response scaricaDichiarazioneDiRinuncia(HttpServletRequest req, Long idDocumentoIndex)
			throws UtenteException, Exception {
		String prf = "[RinunciaServiceImpl::scaricaDichiarazioneDiRinuncia]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			EsitoScaricaDichiarazioneDiRinuncia esito = rinunciaDAO.scaricaDichiarazioneDiRinuncia(idUtente, idIride, idDocumentoIndex);
			
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

	private Response inviaErroreNotFound(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.NOT_FOUND).entity(esito).type( MediaType.APPLICATION_JSON).build();
	}

	private Response inviaErroreUnauthorized(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.UNAUTHORIZED).entity(esito).type( MediaType.APPLICATION_JSON).build();
	}

	private Response inviaRispostaVuota(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.OK).entity(esito).type( MediaType.APPLICATION_JSON).build();
	}

	private Response inviaErroreInternalServer(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(esito).type( MediaType.APPLICATION_JSON).build();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	///////////////////////////////////////////// METODI DI RESPONSE HTTP fine ////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public Response salvaFileFirmato(MultipartFormDataInput multipartFormData, HttpServletRequest req) throws InvalidParameterException, Exception {
		
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(rinunciaDAO.salvaFileFirmato(multipartFormData, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
	
	@Override
	public Response salvaFileFirmaAutografa(MultipartFormDataInput multipartFormData, HttpServletRequest req) throws InvalidParameterException, Exception {
		
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(rinunciaDAO.salvaFileFirmaAutografa(multipartFormData, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
	
	@Override
	public Response verificaFirmaDigitale(Long idDocumentoIndex, HttpServletRequest req) throws InvalidParameterException, Exception {		
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(rinunciaDAO.verificaFirmaDigitale(idDocumentoIndex, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
	
	@Override
	public Response salvaInvioCartaceo(Boolean invioCartaceo, Long idDocumentoIndex, HttpServletRequest req) throws InvalidParameterException, Exception {		
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(rinunciaDAO.salvaInvioCartaceo(invioCartaceo, idDocumentoIndex, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}



	@Override
	public Response getIsBeneficiarioPrivatoCittadino(Long idProgetto, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(rinunciaDAO.getIsBeneficiarioPrivatoCittadino(idProgetto, userInfo.getIdUtente(), userInfo.getIdIride() )).build();
	}



	@Override
	public Response getIsRegolaApplicabileForProgetto(Long idProgetto, String codiceRegola, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(rinunciaDAO.getIsRegolaApplicabileForProgetto(idProgetto,codiceRegola, userInfo.getIdUtente(), userInfo.getIdIride() )).build();
	}

}
