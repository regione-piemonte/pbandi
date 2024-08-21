/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.csi.pbandi.pbservizit.exception.UtenteException;

@Path("/datiProgetto")
public interface DatiGeneraliAttivitaPregresseApi {

	@GET
	@Path("/datiGenerali") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getDatiGenerali( @Context HttpServletRequest req,  @QueryParam("idProgetto") Long idProgetto ) throws UtenteException, FileNotFoundException , IOException, Exception;
	
	@GET
	@Path("/attivitaPregresse") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getAttivitaPregresse( @Context HttpServletRequest req,  @QueryParam("idProgetto") Long idProgetto ) throws UtenteException, FileNotFoundException , IOException, Exception;
	
}
