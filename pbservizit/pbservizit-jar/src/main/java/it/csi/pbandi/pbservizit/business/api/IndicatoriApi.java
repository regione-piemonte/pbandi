/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.integration.request.SalvaIndicatoriRequest;
import it.csi.pbandi.pbservizit.integration.request.ValidazioneDatiIndicatoriRequest;

@Path("/indicatori")
public interface IndicatoriApi {

	@GET
	@Path("/inizializzaIndicatori")
	@Produces(MediaType.APPLICATION_JSON)
	Response inizializzaIndicatori(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto)
			throws UtenteException, Exception;

	@GET
	@Path("/progetto")
	@Produces(MediaType.APPLICATION_JSON)
	Response getCodiceProgetto(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto)
			throws UtenteException, Exception;

	@GET
	@Path("/indicatoriGestione")
	@Produces(MediaType.APPLICATION_JSON)
	Response getIndicatori(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto,
			@QueryParam("isBandoSif") Boolean isBandoSif) throws UtenteException, Exception;

	@GET
	@Path("/indicatoriAvvio")
	@Produces(MediaType.APPLICATION_JSON)
	Response getIndicatoriAvvio(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto)
			throws UtenteException, Exception;

	/*
	 * @POST
	 * 
	 * @Path("/indicatoriGestione/validazioneDati")
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) Response
	 * controllaDatiPerSalvataggioGestione( @Context HttpServletRequest req,
	 * ValidazioneDatiIndicatoriRequest validazioneRequest) throws UtenteException,
	 * Exception;
	 */

	@POST
	@Path("/indicatoriAvvio/validazioneDati")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response controllaDatiPerSalvataggioAvvio(@Context HttpServletRequest req,
			ValidazioneDatiIndicatoriRequest validazioneRequest) throws UtenteException, Exception;

	@POST
	@Path("/indicatoriGestione")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response saveIndicatoriGestione(@Context HttpServletRequest req, SalvaIndicatoriRequest salvaRequest)
			throws UtenteException, Exception;

	@POST
	@Path("/indicatoriAvvio")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response saveIndicatoriAvvio(@Context HttpServletRequest req, SalvaIndicatoriRequest salvaRequest)
			throws UtenteException, Exception;

}
