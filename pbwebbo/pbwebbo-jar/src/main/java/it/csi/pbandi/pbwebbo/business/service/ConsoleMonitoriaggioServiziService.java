/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/consoleMonitoriaggioServizi")
public interface ConsoleMonitoriaggioServiziService {
	
	//Popola la drop down "Servizi"
	@GET
	@Path("findErrorMessage") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findErrorMessage(@Context HttpServletRequest req) throws Exception;
	
	
	//Popola la drop down "Servizi"
		@GET
		@Path("getServizi") 
		@Produces(MediaType.APPLICATION_JSON)
		Response findServizi(@Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("getEsitoChiamate") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getEsitoChiamate( @QueryParam("dataInizio") String dataInizio,
								  @QueryParam("dataFine") String dataFine,
								  @QueryParam("codiceErrore") String codiceErrore,
								  @QueryParam("idServizio") Long idServizio,
								  @Context HttpServletRequest req) throws Exception;
	
	
	
	@GET
	@Path("getStatus") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getStatusServizio( @QueryParam("dataInizio") String dataInizio,
								  @QueryParam("dataFine") String dataFine,
								  @QueryParam("codiceErrore") String codiceErrore,
								  @QueryParam("idServizio") Long idServizio,
								  @Context HttpServletRequest req) throws Exception;

}
