/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.business.service.impl;

import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebbo.business.service.GestioneNews;
import it.csi.pbandi.pbwebbo.dto.gestionenews.AvvisoDTO;
import it.csi.pbandi.pbwebbo.integration.dao.GestioneNewsDAO;
import it.csi.pbandi.pbwebbo.util.Constants;


@Service
public class GestioneNewsImpl implements GestioneNews {
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	private GestioneNewsDAO gestioneNewsDAO;
	
	@Override
	public Response inizializzaGestioneNews(HttpServletRequest req) throws InvalidParameterException, Exception {		
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(gestioneNewsDAO.inizializzaGestioneNews(userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
	
	@Override
	public Response aggiornaAvviso(AvvisoDTO avvisoDTO, HttpServletRequest req) throws InvalidParameterException, Exception {		
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(gestioneNewsDAO.aggiornaAvviso(avvisoDTO, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response eliminaAvviso(Long idNews, HttpServletRequest req) throws InvalidParameterException, Exception {		
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(gestioneNewsDAO.eliminaAvviso(idNews, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}
	
}
