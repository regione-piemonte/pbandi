/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/dashboard")
public interface DashboardService {

	@GET
	@Path("isDashboardVisible")
	@Produces(MediaType.APPLICATION_JSON)
	public Response isDashboardVisible(@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("areWidgetsConfigured")
	@Produces(MediaType.APPLICATION_JSON)
	public Response areWidgetsConfigured(@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("widgets")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWidgets(@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("bandiDaAssociare")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBandiDaAssociare(@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("bandiAssociati")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBandiAssociati(@QueryParam("idWidget") Long idWidget, @Context HttpServletRequest req)
			throws Exception;

	@POST
	@Path("widgetAttivo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeWidgetAttivo(@QueryParam("idWidget") Long idWidget,
			@QueryParam("flagWidgetAttivo") Boolean flagWidgetAttivo, @Context HttpServletRequest req) throws Exception;

	@POST
	@Path("associaBandoAWidget")
	@Produces(MediaType.APPLICATION_JSON)
	public Response associaBandoAWidget(@QueryParam("idWidget") Long idWidget,
			@QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento, @Context HttpServletRequest req)
			throws Exception;

	@DELETE
	@Path("disassociaBandoAWidget")
	@Produces(MediaType.APPLICATION_JSON)
	public Response disassociaBandoAWidget(@QueryParam("idBandoLinSoggWidget") Long idBandoLinSoggWidget,
			@Context HttpServletRequest req) throws Exception;

}
