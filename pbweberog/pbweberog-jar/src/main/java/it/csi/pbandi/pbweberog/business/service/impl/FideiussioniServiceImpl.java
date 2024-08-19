/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatididominio.GestioneDatiDiDominioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweberog.business.service.FideiussioniService;
import it.csi.pbandi.pbweberog.dto.CodiceDescrizione;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.fideiussioni.Fideiussione;
import it.csi.pbandi.pbweberog.dto.fideiussioni.FideiussioneDTO;
import it.csi.pbandi.pbweberog.dto.fideiussioni.FideiussioneEsitoGenericoDTO;
import it.csi.pbandi.pbweberog.dto.fideiussioni.FiltroRicercaFideiussione;
import it.csi.pbandi.pbweberog.integration.dao.FideiussioneDAO;
import it.csi.pbandi.pbweberog.integration.request.CercaFideiussioniRequest;
import it.csi.pbandi.pbweberog.integration.request.CreaAggiornaFideiussioneRequest;
import it.csi.pbandi.pbweberog.util.BeanUtil;
import it.csi.pbandi.pbweberog.util.Constants;
import it.csi.pbandi.pbweberog.util.DateUtil;
import it.csi.pbandi.pbweberog.util.ErrorMessages;
import it.csi.pbandi.pbweberog.util.MessageConstants;
import it.csi.pbandi.pbweberog.util.NumberUtil;
import it.csi.pbandi.pbweberog.util.StringUtil;

@Service
public class FideiussioniServiceImpl implements FideiussioniService{

	/* METODI DI UTILITA */
	private class ErrorMap extends HashMap<String, String> {
		private static final long serialVersionUID = 1L;
		private String appDataCode = null;

		ErrorMap(String appDataCode) {
			this.appDataCode = appDataCode;
		}

		void putRequired(String widgetName) {
			super.put(appDataCode + "." + widgetName,ErrorMessages.CAMPO_OBBLIGATORIO);
		}

		void putValue(String widgetName) {
			super.put(appDataCode + "." + widgetName, ErrorMessages.VALORE_IMPORTO_NON_CORRETTO);
		}

		public void putDate(String widgetName) {
			super.put(appDataCode + "." + widgetName, ErrorMessages.FORMATO_DATA_NON_CORRETTO);
		}
	}
	
	@Autowired
	protected BeanUtil beanUtil;
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	FideiussioneDAO fideiussioneDAO;
	
	@Autowired
	GestioneDatiGeneraliBusinessImpl datiGeneraliBusinessImpl;
	
	@Autowired
	GestioneDatiDiDominioBusinessImpl datiDiDominioBusinessImpl;
	
	@Override
	public Response isFideiussioneGestibile(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[FideiussioniServiceImpl::isFideiussioneGestibile]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			EsitoOperazioni esito = new EsitoOperazioni();
			esito = fideiussioneDAO.isProgettoGestibile(idUtente, idIride, idProgetto);
			if(!esito.getEsito()) {
				esito.setMsg(ErrorMessages.FIDEIUSSIONE_NON_GESTIBILE);
			}
			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public Response getTipiDiTrattamento(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[FideiussioniServiceImpl::getTipiDiTrattamento]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();			
			ArrayList<CodiceDescrizione> decodifiche = extractDecodifiche(datiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
							GestioneDatiDiDominioSrv.TIPO_TRATTAMENTO));
			LOG.debug(prf + " END");
			return Response.ok().entity(decodifiche).build();
		} catch(Exception e) {
			throw e;
		}
	}
	private ArrayList<CodiceDescrizione> extractDecodifiche (Decodifica[] decodifiche) {
		String prf = "[FideiussioniServiceImpl::extractDecodifiche]";
		LOG.debug(prf + " START");
		ArrayList<CodiceDescrizione> result = new ArrayList<CodiceDescrizione>();
		for (int i = 0; i < decodifiche.length; i++) {
			CodiceDescrizione tempCodiceDescrizione = new CodiceDescrizione();
			tempCodiceDescrizione.setDescrizione(decodifiche[i].getDescrizione());
			tempCodiceDescrizione.setCodice(NumberUtil.getStringValue(decodifiche[i].getId()));
			result.add(tempCodiceDescrizione);
		}
		LOG.debug(prf + " END");
		return result;
	}
	

	@Override
	public Response getTipoDiTrattamento(HttpServletRequest req, Long idTipoTrattamento) throws UtenteException, Exception {
		String prf = "[FideiussioniServiceImpl::getTipoDiTrattamento]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();	
			String descTipoTrattamento = null;
			ArrayList<CodiceDescrizione> decodifiche = extractDecodifiche(datiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
							GestioneDatiDiDominioSrv.TIPO_TRATTAMENTO));
			for(CodiceDescrizione cd: decodifiche) {
				if(cd.getCodice() == idTipoTrattamento.toString()) {
					LOG.info(prf + " descTipoTrattamento trovato: " + cd.getDescrizione());
					descTipoTrattamento = cd.getDescrizione();
				}
			}
			LOG.debug(prf + " END");
			return Response.ok().entity(descTipoTrattamento).build();
		} catch(Exception e) {
			throw e;
		}
	}

	
	
	@Override
	public Response getFideiussioni(HttpServletRequest req, CercaFideiussioniRequest cercaRequest)
			throws UtenteException, Exception {
		String prf = "[FideiussioniServiceImpl::getFideiussioni]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			FiltroRicercaFideiussione  filtroRicerca = cercaRequest.getFiltro();
			Long idProgetto = cercaRequest.getIdProgetto();
			
			//validate filtro ricerca
			if (!StringUtil.isEmpty(filtroRicerca.getDtDecorrenza()) && !DateUtil.isValidDate(filtroRicerca.getDtDecorrenza())) {
				return inviaErroreBadRequest("formato parametro non valido nel body: ?{CercaFideiussioniRequest.FiltroRicercaFideiussione.dtDecorrenza}");
			}
			if (!StringUtil.isEmpty(filtroRicerca.getDtScadenza()) && !DateUtil.isValidDate(filtroRicerca.getDtScadenza())) {
				return inviaErroreBadRequest("formato parametro non valido nel body: ?{CercaFideiussioniRequest.FiltroRicercaFideiussione.dtScadenza}");
			}
			// trasformma request a dto 
			FideiussioneDTO filtro = new FideiussioneDTO();
			filtro.setIdProgetto(idProgetto);
			if (!StringUtil.isEmpty(filtroRicerca.getCodiceRiferimento())) {
				filtro.setCodRiferimentoFideiussione(filtroRicerca.getCodiceRiferimento());
			}
			if (!StringUtil.isEmpty(filtroRicerca.getDtDecorrenza())) {
				filtro.setDtDecorrenza(DateUtil.getDate(filtroRicerca.getDtDecorrenza()));
			}
			if (!StringUtil.isEmpty(filtroRicerca.getDtScadenza())) {
				filtro.setDtScadenza(DateUtil.getDate(filtroRicerca.getDtScadenza()));
			}
			
			FideiussioneDTO[] result = fideiussioneDAO.findFideiussioni(idUtente, idIride, cercaRequest.getIdProgetto(), filtro);
			ArrayList<Fideiussione> fideiussioni = new ArrayList<Fideiussione>();
			for (FideiussioneDTO fideiussioneDTO : result) {
				Fideiussione temp = new Fideiussione();
				fideiussioneDTO.setImportoFideiussione(NumberUtil.toNullableDoubleItalianFormat(fideiussioneDTO.getImportoFideiussione().toString()));;
				LOG.info(prf+ " importo dto: " + fideiussioneDTO.getImportoFideiussione());
				beanUtil.valueCopy(fideiussioneDTO, temp);
				LOG.info(prf+ " importo fid: " + temp.getImportoFideiussione());
				fideiussioni.add(temp);
			}
			LOG.debug(prf + " END");
			return Response.ok().entity(fideiussioni).build();
		} catch(Exception e) {
			throw e;
		}
	}


	@Override
	public Response eliminaFideiussione(HttpServletRequest req, Long idFideiussione) throws UtenteException, Exception {
		String prf = "[FideiussioniServiceImpl::eliminaFideiussione]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
				
			//TO do : controlla se é modificabile
			
			FideiussioneEsitoGenericoDTO esito = fideiussioneDAO.eliminaFideiussione(idUtente, idIride, idFideiussione);
		
			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public Response creaAggiornaFideiussione(HttpServletRequest req, CreaAggiornaFideiussioneRequest moRequest)
			throws UtenteException, Exception {
		String prf = "[FideiussioniServiceImpl::modificaFideiussione]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			
			Long idProgetto = moRequest.getIdProgetto();
			Fideiussione fideiussione = moRequest.getFideiussione();
			fideiussione.setIdProgetto(idProgetto);
			//new - format dates(DtDecorrenza, DtScadenza) as dd/MM/yyyy
//			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//			String dtDecorrenza = sdf.format( fideiussione.getDtDecorrenza() );
//			LOG.info(prf + " DtDecorrenza: " + dtDecorrenza);
//			fideiussione.setDtDecorrenza(sdf.parse(dtDecorrenza));
//			String dtScadenza = sdf.format( fideiussione.getDtScadenza() );
//			LOG.info(prf + " DtScadenza: " + dtScadenza);
//			fideiussione.setDtScadenza(sdf.parse(dtScadenza));
			
			FideiussioneEsitoGenericoDTO esito = new FideiussioneEsitoGenericoDTO();
						
			//validate Fideiussione
			Map<String, String> errorMap = validate(fideiussione);
			
			if (errorMap != null && !errorMap.isEmpty()) {
				esito.setEsito(false);
				esito.setMessage("Errore durante la validazione della fideiussione.");
				Collection<String> err= errorMap.values();
				esito.setParams(err.toArray(new String[err.size()]));
			} else {
				FideiussioneDTO dto = new FideiussioneDTO();
				beanUtil.valueCopy(fideiussione, dto);
				//TO do : controlla se é modificabile
				
				esito = fideiussioneDAO.aggiornaInserisciFideiussione(idUtente, idIride, dto);
			
			}
			
			
		
			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}

	private Map<String, String> validate(Fideiussione fideiussione) {
		String prf = "[FideiussioniServiceImpl::validate]";
		LOG.debug(prf + " START");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		ErrorMap errorMap = new ErrorMap("appDatadatiFideiussione");

		if (fideiussione.getImportoFideiussione() == null) {
			errorMap.putRequired("importoFideiussione");
		} else if (fideiussione.getImportoFideiussione() < 0) {
			errorMap.putValue("importoFideiussione");
		}
		if (StringUtil.isEmpty(fideiussione.getDescEnteEmittente())) {
			errorMap.putRequired("descEnteEmittente");
		}
		if (StringUtil.isEmpty(fideiussione.getDtDecorrenza().toString())) {
			errorMap.putRequired("dtDecorrenza");
		}
		if (fideiussione.getIdTipoTrattamento() == null) {
			errorMap.putRequired("idTipoTrattamento");
		}
		
		if (!StringUtil.isEmpty(fideiussione.getDtDecorrenza().toString()) && 
				!DateUtil.isValidDate( sdf.format(fideiussione.getDtDecorrenza()) ) ) {
			errorMap.putDate("dtDecorrenza");
		}
		if(fideiussione.getDtScadenza()!=null) {
			if ( !DateUtil.isValidDate( sdf.format(fideiussione.getDtScadenza())  ) ) {
				errorMap.putDate("dtScadenza");
			}
		}
		
		if (!StringUtil.isEmpty(fideiussione.getCodRiferimentoFideiussione()) &&
				fideiussione.getCodRiferimentoFideiussione().trim().length() > 20 ) {
			errorMap.put(".codRiferimentoFideiussione", ErrorMessages.ERR_MAX_NUMERO_DI_CARATTERI);
		}
		LOG.debug(prf + " END");
		return errorMap;

	}

	@Override
	public Response dettaglioFideiussione(HttpServletRequest req, Long idFideiussione)
			throws UtenteException, Exception {
		String prf = "[FideiussioniServiceImpl::dettaglioFideiussione]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			FideiussioneDTO fideiussioneDTO = fideiussioneDAO.dettaglioFideiussione(idUtente, idIride, idFideiussione);
			Fideiussione fideiussione = new Fideiussione();
			beanUtil.valueCopy(fideiussioneDTO, fideiussione);
			LOG.debug(prf + " END");
			return Response.ok().entity(fideiussione).build();
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
