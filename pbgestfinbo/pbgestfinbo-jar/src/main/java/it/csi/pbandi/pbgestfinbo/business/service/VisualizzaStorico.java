/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.csi.pbandi.pbgestfinbo.exception.DaoException;

@Path("/storico")
public interface VisualizzaStorico {
	
	@GET
	@Path("/getVistaStoricoDomanda") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getVistaStoricoDomanda(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
			@DefaultValue("0") @QueryParam("idDomanda") Long idDomanda,
	        @Context HttpServletRequest req) throws DaoException;
	
	
	@GET
	@Path("/getVistaStoricoProgetto") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getVistaStoricoProgetto(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getVistaStoricoDomandaPF") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getVistaStoricoDomandaPF(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
			@DefaultValue("0") @QueryParam("idDomanda") Long idDomanda,
	        @Context HttpServletRequest req) throws DaoException;
	
	
	@GET
	@Path("/getVistaStoricoProgettoPF") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getVistaStoricoProgettoPF(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
	        @Context HttpServletRequest req) throws DaoException;

}
