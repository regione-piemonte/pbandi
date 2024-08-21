/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.RequestBody;

import it.csi.pbandi.pbservizit.dto.help.HelpDTO;

@Path("/help")
public interface HelpApi {

	@GET
	@Path("/flagEditHelpUser")
	@Produces(MediaType.APPLICATION_JSON)
	Response getFlagEditHelpUser(@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("/hasTipoAnagHelp")
	@Produces(MediaType.APPLICATION_JSON)
	Response hasTipoAnagHelp(@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("/esisteHelpByPaginaAndTipoAnag")
	@Produces(MediaType.APPLICATION_JSON)
	Response esisteHelpByPaginaAndTipoAnag(@QueryParam("descBrevePagina") String descBrevePagina,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("/helpByPaginaAndTipoAnag")
	@Produces(MediaType.APPLICATION_JSON)
	Response getHelpByPaginaAndTipoAnag(@QueryParam("descBrevePagina") String descBrevePagina,
			@Context HttpServletRequest req) throws Exception;

	@POST
	@Path("/saveHelp")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response saveHelp(@QueryParam("descBrevePagina") String descBrevePagina, @RequestBody HelpDTO helpDTO,
			@Context HttpServletRequest req) throws Exception;

	@DELETE
	@Path("/deleteHelp")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response deleteHelp(@QueryParam("idHelp") Long idHelp, @Context HttpServletRequest req) throws Exception;

}
