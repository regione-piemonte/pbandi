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
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.PermessoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.TipoAnagraficaDTO;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebbo.business.service.AssociazionePermessoRuolo;
import it.csi.pbandi.pbwebbo.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbwebbo.util.Constants;

@Service
public class AssociazionePermessoRuoloImpl implements AssociazionePermessoRuolo {
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	private GestioneBackofficeBusinessImpl gestioneBackofficeSrv;

	@Override
	public Response cercaPermessi(Long idU, String descrizione, String codice, HttpServletRequest req) throws Exception {
		
		String prf = "[AssociazionePermessoRuoloImpl::cercaPermessi]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+ ", descrizione = " +descrizione+", codice = "+codice);
		
		PermessoDTO[] permessi = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
			
		PermessoDTO permessoDTO = new PermessoDTO();
		permessoDTO.setDescBrevePermesso(codice);
		permessoDTO.setDescPermesso(descrizione);
		permessi = gestioneBackofficeSrv.findPermessi(idU, userInfo.getIdIride(), permessoDTO);

		LOG.debug(prf + "Trovate = " + permessi.length+" permessi");
			
		LOG.debug(prf + " END");
		
		return Response.ok().entity(permessi).build();
		
	}

	@Override
	public Response cercaRuoliDaAssociare(Long idU, String codice, HttpServletRequest req) throws Exception {
		
		String prf = "[AssociazionePermessoRuoloImpl::cercaRuoliDaAssociare]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", codice = "+codice);
		
		TipoAnagraficaDTO[] tipoAnagraficaDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		tipoAnagraficaDTO = gestioneBackofficeSrv.findTipiAnagraficaNonAssociatiAPermesso(idU, userInfo.getIdIride(), codice);

		if(tipoAnagraficaDTO != null && tipoAnagraficaDTO.length > 0 ) {
			LOG.debug(prf + "Trovati = " + tipoAnagraficaDTO.length+" ruoli associabili a permesso");
		}else {
			LOG.debug(prf + "Non ci sono ruoli associabili a permesso");
		}
			
		LOG.debug(prf + " END");
		
		return Response.ok().entity(tipoAnagraficaDTO).build();
		
	}

	@Override
	public Response cercaRuoliAssociati(Long idU, String codice, HttpServletRequest req) throws Exception {

		String prf = "[AssociazionePermessoRuoloImpl::cercaRuoliAssociati]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", codice = "+codice);
		
		TipoAnagraficaDTO[] tipoAnagraficaDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		tipoAnagraficaDTO = gestioneBackofficeSrv.findTipiAnagraficaAssociatiAPermesso(idU, userInfo.getIdIride(), codice);

		LOG.debug(prf + "Trovate = " + tipoAnagraficaDTO.length+" associazioni associabili a permesso");
	
		LOG.debug(prf + " END");
		
		return Response.ok().entity(tipoAnagraficaDTO).build();
		
	}

	@Override
	public Response associaRuoli(Long idU, String codice, TipoAnagraficaDTO[] tipoAnagraficaDTO, HttpServletRequest req) throws Exception {
		
		String prf = "[AssociazionePermessoRuoloImpl::associaRuoli]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", codice="+codice+", tipoAnagraficaDTO.length = "+tipoAnagraficaDTO.length);
		
		ResponseCodeMessage resp = new ResponseCodeMessage();
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		gestioneBackofficeSrv.associaTipiAnagraficaAPermesso(idU, userInfo.getIdIride(), codice, tipoAnagraficaDTO);

		resp.setCode("OK");
		resp.setMessage("Associazione ruolo/i avvenuta con successo");
		LOG.debug(prf + "Associazione ruolo/i avvenuta con successo");
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(resp).build();
		
	}

	@Override
	public Response disassociaRuoli(Long idU, String codice, TipoAnagraficaDTO[] tipoAnagraficaDTO, HttpServletRequest req) throws Exception {
		
		String prf = "[AssociazionePermessoRuoloImpl::disassociaRuoli]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", codice="+codice+", tipoAnagraficaDTO.length = "+tipoAnagraficaDTO.length);
		
		ResponseCodeMessage resp = new ResponseCodeMessage();
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		gestioneBackofficeSrv.disassociaTipiAnagraficaDaPermesso(idU, userInfo.getIdIride(), codice, tipoAnagraficaDTO);

		resp.setCode("OK");
		resp.setMessage("Disassociazione ruolo/i avvenuta con successo");
		LOG.debug(prf + "Disassociazione ruolo/i avvenuta con successo");
			
			
		LOG.debug(prf + " END");
		
		return Response.ok().entity(resp).build();
		
	}

	@Override
	public Response cercaRuoli(Long idU, String descrizione, String codice, HttpServletRequest req) throws Exception {
		
		String prf = "[AssociazionePermessoRuoloImpl::cercaRuoli]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+ ", descrizione = " +descrizione+", codice = "+codice);
		
		TipoAnagraficaDTO[] ruoli = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		TipoAnagraficaDTO ruoloDTO = new TipoAnagraficaDTO();
		ruoloDTO.setDescBreveTipoAnagrafica(codice);
		ruoloDTO.setDescTipoAnagrafica(descrizione);
		ruoli = gestioneBackofficeSrv.findTipiAnagraficaFiltrato(idU, userInfo.getIdIride(), ruoloDTO);

		LOG.debug(prf + "Trovati = " + ruoli.length+" ruoli");
			
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(ruoli).build();
		
	}

	@Override
	public Response cercaPermessiDaAssociare(Long idU, String codice, HttpServletRequest req) throws Exception {
		
		String prf = "[AssociazionePermessoRuoloImpl::cercaPermessiDaAssociare]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", codice = "+codice);
		
		PermessoDTO[] permessiAssociabili = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		permessiAssociabili = gestioneBackofficeSrv.findPermessiNonAssociatiATipoAnagrafica(idU, userInfo.getIdIride(), codice);

		if(permessiAssociabili != null && permessiAssociabili.length > 0 ) {
			LOG.debug(prf + "Trovati = " + permessiAssociabili.length+" permessi associabili");
		}else {
			LOG.debug(prf + "Non ci sono permessi associabili a ruoli");
		}
		
	
		LOG.debug(prf + " END");
		
		return Response.ok().entity(permessiAssociabili).build();
		
	}

	@Override
	public Response cercaPermessiAssociati(Long idU, String codice, HttpServletRequest req) throws Exception {
		
		String prf = "[AssociazionePermessoRuoloImpl::cercaPermessiAssociati]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", codice = "+codice);
		
		PermessoDTO[] permessiAssociati = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		permessiAssociati = gestioneBackofficeSrv.findPermessiAssociatiATipoAnagrafica(idU, userInfo.getIdIride(), codice);

		LOG.debug(prf + "Trovati = " + permessiAssociati.length+" permessi associati");
			
		LOG.debug(prf + " END");
		
		return Response.ok().entity(permessiAssociati).build();
		
	}

	@Override
	public Response associaPermessi(Long idU, String codice, PermessoDTO[] permessoDTO, HttpServletRequest req) throws Exception {
		
		String prf = "[AssociazionePermessoRuoloImpl::associaPermessi]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", codice="+codice+", permessoDTO.length = "+permessoDTO.length);
		
		ResponseCodeMessage resp = new ResponseCodeMessage();
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		gestioneBackofficeSrv.associaPermessiATipoAnagrafica(idU, userInfo.getIdIride(), codice, permessoDTO);

		resp.setCode("OK");
		resp.setMessage("Associazione permesso/i avvenuta con successo");
		LOG.debug(prf + "Associazione permesso/i avvenuta con successo");
			
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(resp).build();
		
	}

	@Override
	public Response disassociaPermessi(Long idU, String codice, PermessoDTO[] permessoDTO, HttpServletRequest req) throws Exception {
		
		String prf = "[AssociazionePermessoRuoloImpl::disassociaPermessi]";
		LOG.debug(prf + " BEGIN");
		
		LOG.debug(prf + "Parametri in input -> idU = "+idU+", codice="+codice+", permessoDTO.length = "+permessoDTO.length);
		
		ResponseCodeMessage resp = new ResponseCodeMessage();
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);
		
		gestioneBackofficeSrv.disassociaPermessiDaTipoAnagrafica(idU, userInfo.getIdIride(), codice, permessoDTO);

		resp.setCode("OK");
		resp.setMessage("Disassociazione permesso/i avvenuta con successo");
		LOG.debug(prf + "Disassociazione permesso/i avvenuta con successo");
		
		LOG.debug(prf + " END");
		
		return Response.ok().entity(resp).build();
		
	}

}
