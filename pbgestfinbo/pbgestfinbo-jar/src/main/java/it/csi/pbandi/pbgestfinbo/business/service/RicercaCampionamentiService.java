/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;

import java.security.InvalidParameterException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.RicercaCampionamentiVO;

@Path("/ricerCampionamenti")
public interface RicercaCampionamentiService {
	
	@POST
	@Path("/getListaCampionamenti")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response getListaCampio(RicercaCampionamentiVO campionamentiVO)
			throws InvalidParameterException, Exception;
	
	
	@GET
	@Path("/getListaTipologie")
	@Produces(MediaType.APPLICATION_JSON)
	Response getListaTipologie() throws DaoException;
	
	@GET
	@Path("/getListaStati")
	@Produces(MediaType.APPLICATION_JSON)
	Response getListaStati() throws DaoException;
	
	@GET
	@Path("/getListaUtenti")
	@Produces(MediaType.APPLICATION_JSON)
	Response getListaUtenti(@QueryParam("string") String string) throws DaoException;
	
	@GET
	@Path("/getListaBandi")
	@Produces(MediaType.APPLICATION_JSON)
	Response getListaBandi(@QueryParam("string") String string) throws DaoException;
	
	
	@GET
	@Path("/tipologieDichiaSpesa")
	@Produces(MediaType.APPLICATION_JSON)
	Response getTipoDichiaraziSpesa() throws DaoException;
	

}
