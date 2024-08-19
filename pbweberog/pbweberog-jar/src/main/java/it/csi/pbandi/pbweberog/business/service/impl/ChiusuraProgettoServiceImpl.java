/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.dto.fineprogetto.EsitoOperazioneChiudiProgetto;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweberog.business.service.ChiusuraProgettoService;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.integration.dao.ChiusuraProgettoDAO;
import it.csi.pbandi.pbweberog.integration.request.RequestChiudiProgetto;
import it.csi.pbandi.pbweberog.util.BeanUtil;
import it.csi.pbandi.pbweberog.util.Constants;
import it.csi.pbandi.pbweberog.util.MessageConstants;

@Service
public class ChiusuraProgettoServiceImpl implements ChiusuraProgettoService{
	@Autowired
	protected BeanUtil beanUtil;
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	ChiusuraProgettoDAO chiusuraProgettoDAO;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	///////////////////////////////////////////// CHIUSURA DEL PROGETTO ///////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public Response isRinunciaPresente(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[ChiusuraProgettoServiceImpl::isRinunciaPresente]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Boolean res = chiusuraProgettoDAO.isRinunciaPresente(idUtente, idIride, idProgetto);
			EsitoOperazioni esito = new EsitoOperazioni();
			esito.setEsito(res);
			if(res)
				esito.setMsg(MessageConstants.WRN_CHIUSURA_PROGETTO_RINUNCIA_PRESENTE);
			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public Response chiudiProgetto(HttpServletRequest req, RequestChiudiProgetto chiudiRequest)
			throws UtenteException, Exception {
		String prf = "[ChiusuraProgettoServiceImpl::chiudiProgetto]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Long idProgetto = chiudiRequest.getIdProgetto();
			String note = chiudiRequest.getNote();
			EsitoOperazioneChiudiProgetto esito = new EsitoOperazioneChiudiProgetto();
			esito.setEsito(Boolean.FALSE);
			esito = chiusuraProgettoDAO.chiudiProgetto(idUtente, idIride, idProgetto, note);
		
			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch(Exception e) {
			throw e;
		}
	}
	
	@Override
	public Response verificaErogazioneSaldo(HttpServletRequest req, Long idProgetto) throws Exception {
		String prf = "[ChiusuraProgettoServiceImpl::verificaErogazioneSaldo]";
		LOG.debug(prf + " BEGIN");
		HttpSession session = req.getSession();
		UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		Long idUtente = userInfo.getIdUtente();
		String idIride = userInfo.getIdIride();
		EsitoOperazioni esito = new EsitoOperazioni();
		String codRegola = "BR37";	
		Long[] listaIdModalitaAgevolazioni = chiusuraProgettoDAO.getListaIdModalitaAgevolazioni(idUtente, idIride, idProgetto);
		// verifico se al BandoLinea d'appartenenza del progetto e' associata la regola BR37
		if(chiusuraProgettoDAO.isProgettoAssociatoRegola(idUtente, idIride, idProgetto, codRegola)){		
			// al BandoLinea d'appartenenza del progetto e' associata la regola BR37
			LOG.debug("regola BR37 associata");		
			for (Long idModAg : listaIdModalitaAgevolazioni) {
				String desc = chiusuraProgettoDAO.getCausaleErogazionePerContoEconomicoLiquidazione(idUtente, idIride, idProgetto, idModAg);				
				LOG.debug("causale trovata="+desc + " per idProgetto="+idProgetto+", IdModalitaAgevolazioni="+idModAg);
				if("SAL".equals(desc) || "SAL-INS".equals(desc)){					
					esito.setEsito(true);		
				} else {
					esito.setEsito(false);	
					break;
				}
			}			
		} else {			
			// al BandoLinea d'appartenenza del progetto non e' associata la regola BR37
			LOG.debug("regola BR37 NON associata");			
			for (Long idModAg : listaIdModalitaAgevolazioni) {
				String desc = chiusuraProgettoDAO.getCausaleErogazionePerContoEconomicoErogazione(idUtente, idIride, idProgetto, idModAg);
				LOG.debug("causale trovata=["+desc + "] per idProgetto="+idProgetto+", IdModalitaAgevolazioni="+idModAg);
				if("SAL".equals(desc) || "SAL-INS".equals(desc)){					
					// verifico se esiste il progetto associato/collegato/a contributo
					// Se non esiste : return TRUE
					// Se esiste faccio il controllo checkProgettoFinanziato
					Long idProgettoAContributo = chiusuraProgettoDAO.getIdProgettoAContributo(idUtente, idIride, idProgetto);
					LOG.debug("idProgettoAContributo="+idProgettoAContributo);					
					if(idProgettoAContributo!=null){
						//  nuovo controllo : solo per i progetti che hanno un progetto "A Contributo" collegato
						// es i progetti +Green
						esito.setEsito(chiusuraProgettoDAO.checkErogazioneSaldoProgettoContributo(idUtente, idIride, idProgettoAContributo));
					} else{
						esito.setEsito(true);
					}
				} else {
					esito.setEsito(false);	
					break;
				}
			}
		}
		if(!esito.getEsito()) {
			esito.setMsg("Attenzione! Il sistema ha verificato che non è stato erogato il saldo. Continuare?");
			return Response.ok().entity(esito).build();
		}
		LOG.debug(prf + " END");
		return Response.ok().entity(esito).build();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	///////////////////////////////////////// CHIUSURA D'UFFICIO DEL PROGETTO /////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Response chiudiProgettoDiUfficio(HttpServletRequest req, RequestChiudiProgetto chiudiRequest)
			throws UtenteException, Exception {
		String prf = "[ChiusuraProgettoServiceImpl::chiudiProgettoDiUfficio]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Long idProgetto = chiudiRequest.getIdProgetto();
			String note = chiudiRequest.getNote();
			EsitoOperazioneChiudiProgetto esito = new EsitoOperazioneChiudiProgetto();
			esito.setEsito(Boolean.FALSE);
			esito = chiusuraProgettoDAO.chiudiProgettoDiUfficio(idUtente, idIride, idProgetto, note);
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
