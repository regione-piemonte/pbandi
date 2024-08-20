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

@Path("/beneficiarioDomanda")
public interface BeneficiarioDomandaService
{
	
	@GET
	@Path("/getLegaleRappr") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getLegaleRappr(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
			@DefaultValue("0") @QueryParam("idDomanda") Long idDomanda, 
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getSedeAmm") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getSedeAmm(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
			@DefaultValue("0") @QueryParam("idDomanda") Long idDomanda, 
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getRecapiti") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getRecapiti(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
			@DefaultValue("0") @QueryParam("idDomanda") Long idDomanda, 
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getConto") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getConto(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
			@DefaultValue("0") @QueryParam("idDomanda") Long idDomanda, 
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getDelegati") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getDelegati(
			@DefaultValue("0") @QueryParam("idDomanda") Long idDomanda, 
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getConsulenti") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getConsulenti(
			@DefaultValue("0") @QueryParam("idDomanda") Long idDomanda, 
	        @Context HttpServletRequest req) throws DaoException;

}
