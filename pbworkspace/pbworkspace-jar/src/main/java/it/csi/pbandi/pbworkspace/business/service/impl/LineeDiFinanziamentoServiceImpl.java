/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.business.service.impl;

import java.security.InvalidParameterException;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbworkspace.business.service.LineeDiFinanziamentoService;
import it.csi.pbandi.pbworkspace.integration.dao.LineeDiFinanziamentoDAO;
import it.csi.pbandi.pbworkspace.integration.request.AvviaProgettiRequest;
import it.csi.pbandi.pbworkspace.integration.request.RicercaProgettiRequest;
import it.csi.pbandi.pbworkspace.util.Constants;


@Service
public class LineeDiFinanziamentoServiceImpl implements LineeDiFinanziamentoService {

	//private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	LineeDiFinanziamentoDAO lineeDiFinanziamentoDAO;

	@Override
	public Response inizializzaLineeDiFinanziamento(Long progrBandoLineaIntervento, Long idSoggetto, String codiceRuolo, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(lineeDiFinanziamentoDAO.inizializzaLineeDiFinanziamento(progrBandoLineaIntervento,idSoggetto, codiceRuolo, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	} 
	
	@Override
	public Response lineeDiFinanziamento(String descrizione, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(lineeDiFinanziamentoDAO.lineeDiFinanziamento(descrizione, userInfo)).build();
	}

	@Override
	public Response ricercaProgetti(RicercaProgettiRequest ricercaProgettiRequest, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(lineeDiFinanziamentoDAO.ricercaProgetti(ricercaProgettiRequest, userInfo)).build();		
	}
	
	@Override
	public Response avviaProgetti(AvviaProgettiRequest avviaProgettiRequest, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		return Response.ok().entity(lineeDiFinanziamentoDAO.avviaProgetti(avviaProgettiRequest, userInfo)).build();		
	}
	
}
