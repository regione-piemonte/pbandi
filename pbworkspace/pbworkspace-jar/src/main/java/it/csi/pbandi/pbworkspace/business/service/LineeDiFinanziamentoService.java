/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.business.service;

import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import it.csi.pbandi.pbworkspace.exception.UtenteException;
import it.csi.pbandi.pbworkspace.integration.request.AvviaProgettiRequest;
import it.csi.pbandi.pbworkspace.integration.request.RicercaProgettiRequest;

@Path("/lineeDiFinanziamento")
public interface LineeDiFinanziamentoService {
	
	@GET
	@Path("inizializzaLineeDiFinanziamento")
	@Produces(MediaType.APPLICATION_JSON)
	public Response inizializzaLineeDiFinanziamento(
			@QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
			@QueryParam("idSoggetto") Long idSoggetto,
			@QueryParam("codiceRuolo") String codiceRuolo,			
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("lineeDiFinanziamento")
	@Produces(MediaType.APPLICATION_JSON)
	public Response lineeDiFinanziamento(
			@QueryParam("descrizione") String descrizione,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("ricercaProgetti")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response ricercaProgetti(
			RicercaProgettiRequest ricercaProgettiRequest,
			@Context HttpServletRequest req) throws UtenteException, InvalidParameterException, Exception;

	@POST
	@Path("avviaProgetti")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response avviaProgetti(
			AvviaProgettiRequest avviaProgettiRequest,
			@Context HttpServletRequest req) throws UtenteException, InvalidParameterException, Exception;
	
}
