/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import java.security.InvalidParameterException;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbgestfinbo.business.service.RicercaCampionamentiService;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.RicercaCampionamentiDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.RicercaCampionamentiVO;

@Service
public class RicercaCampionamentiServiceImpl implements RicercaCampionamentiService {

	@Autowired
	RicercaCampionamentiDAO campionamentiDAO; 
	
	@Override
	public Response getListaCampio(RicercaCampionamentiVO campionamentiVO) throws InvalidParameterException, Exception {
		return Response.ok(campionamentiDAO.getListaCampionamenti(campionamentiVO)).build();
	}

	@Override
	public Response getListaTipologie() throws DaoException {
		return Response.ok(campionamentiDAO.getListaTipolgie()).build();
	}

	@Override
	public Response getListaStati() throws DaoException {
		return Response.ok(campionamentiDAO.getListaStati()).build();
	}

	@Override
	public Response getListaUtenti(String string) throws DaoException {
		return Response.ok(campionamentiDAO.getListaUtenti(string)).build();
	}

	@Override
	public Response getListaBandi(String string) throws DaoException {
		return Response.ok(campionamentiDAO.getListaBandi(string)).build();	}

	@Override
	public Response getTipoDichiaraziSpesa() throws DaoException {
		return Response.ok(campionamentiDAO.getTipoDichiaraziSpesa()).build();
	}

}
