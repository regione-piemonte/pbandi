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
public interface StoricoBeneficiario {
	
	@GET
	@Path("/getStorico") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getStorico(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto,
	        @Context HttpServletRequest req) throws DaoException;
	
	@GET
	@Path("/getStoricoFisico") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getStoricoFisico(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
	        @Context HttpServletRequest req) throws DaoException;
	
	
	@GET
	@Path("/getVersioni") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getVersioni(
			@DefaultValue("0") @QueryParam("idSoggetto") Long idSoggetto, 
	        @Context HttpServletRequest req) throws DaoException;

}
