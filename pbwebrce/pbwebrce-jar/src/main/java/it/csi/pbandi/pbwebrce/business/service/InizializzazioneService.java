/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.csi.pbandi.pbservizit.exception.UtenteException;

@Path("/inizializzazione")
public interface InizializzazioneService {
	
	@GET
	@Path("home") 
	@Produces(MediaType.APPLICATION_JSON)
	Response inizializzaHome(
			@DefaultValue("0") @QueryParam("idPrj") Long idPrj,
	        @DefaultValue("0") @QueryParam("idSg") Long idSg,
	        @DefaultValue("0") @QueryParam("idSgBen") Long idSgBen,
	        @DefaultValue("0") @QueryParam("idU") Long idU,
	        @DefaultValue("--") @QueryParam("role") String role,
	        @DefaultValue("--") @QueryParam("taskIdentity") String taskIdentity,
	        @DefaultValue("--") @QueryParam("taskName") String taskName,
	        @DefaultValue("--") @QueryParam("wkfAction") String wkfAction, 
	        @Context HttpServletRequest req) throws UtenteException;

}