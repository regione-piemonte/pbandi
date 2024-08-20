/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import it.csi.pbandi.pbgestfinbo.business.service.GestioneRiassicurazioniService;
import it.csi.pbandi.pbgestfinbo.dto.EsitoDTO;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.exception.RecordNotFoundException;
import it.csi.pbandi.pbgestfinbo.integration.dao.AreaControlliDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.GestioneRiassicurazioniDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.IterAutorizzativiDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.PropostaRevocaDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.RicercaGaranzieDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.SuggestRevocheDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.ModificaEscussioneRiassicurazioniDTO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.security.UserInfoSec;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class GestioneRiassicurazioniServiceImpl implements GestioneRiassicurazioniService {

	/*@Autowired
	AreaControlliDAO areaControlliDao; 
	@Autowired
	IterAutorizzativiDAO iterAutoDao; 
	@Autowired
	SuggestRevocheDAO suggestRevocaDao; 
	@Autowired 
	PropostaRevocaDAO propostaRevocaDao;*/
	@Autowired
	protected GestioneRiassicurazioniDAO riassicurazioniDAO;
	
	@Override
	public Response getTest(HttpServletRequest req) {
		return Response.ok("Test OK.").build();
	}
	
	// Cerca Riassicurazioni //

	@Override
	public Response ricercaRiassicurazioni(String descrizioneBando, String codiceProgetto, String codiceFiscale, String ndg, String partitaIva, String denominazioneCognomeNome, String statoEscussione, String denominazioneBanca, HttpServletRequest req) throws Exception {
		
		return Response.ok().entity(riassicurazioniDAO.ricercaBeneficiariRiassicurazioni(descrizioneBando, codiceProgetto, codiceFiscale, ndg, partitaIva, denominazioneCognomeNome, statoEscussione, denominazioneBanca)).build();
		
	}

	@Override
	public Response getDettaglioRiassicurazioni(Long idProgetto, Long idRiassicurazione, Boolean getStorico, HttpServletRequest req) throws Exception {
		
		return Response.ok().entity(riassicurazioniDAO.getDettaglioRiassicurazioni(idProgetto, idRiassicurazione, getStorico)).build();
		
	}

	
	@Override
	public Response modificaRiassicurazione(Long idRiassicurazione, String importoEscusso, java.util.Date dataEscussione, java.util.Date dataScarico, HttpServletRequest req) throws Exception {

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		
		return Response.ok().entity(riassicurazioniDAO.modificaRiassicurazione(idRiassicurazione, importoEscusso, dataEscussione, dataScarico, userInfo.getIdUtente())).build();
	}
	
	// Gestione Escussione Riassicurazioni //
	
	
	@Override
	public Response initGestioneEscussioneRiassicurazioni(Long idProgetto, HttpServletRequest req) throws Exception {
		
		return Response.ok(riassicurazioniDAO.initGestioneEscussioneRiassicurazioni(idProgetto)).build();
	}

	@Override
	public Response modificaEscussioneRiassicurazioni(ModificaEscussioneRiassicurazioniDTO dati, Boolean inserisciNuovo, HttpServletRequest req) throws Exception {

		//EsitoDTO esito = new EsitoDTO();

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		
		return Response.ok().entity(riassicurazioniDAO.modificaEscussioneRiassicurazioni(dati, inserisciNuovo, userInfo.getIdUtente())).build();
	}
}
