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

import it.csi.pbandi.pbservizit.exception.ErroreGestitoException;

@Path("/gestionetemplates")
public interface GestioneTemplatesService {

	@GET
	@Path("bandiLinea")
	@Produces(MediaType.APPLICATION_JSON)
	Response findBandiLinea(@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("tipididocumento")
	@Produces(MediaType.APPLICATION_JSON)
	Response findTipiDocumento(@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("modelli")
	@Produces(MediaType.APPLICATION_JSON)
	Response findModelli(@QueryParam("progrBandolinea") Long progrBandolinea,
			@QueryParam("idTipoDocumento") Long idTipoDocumento, @Context HttpServletRequest req) throws Exception;

	@GET
	@Path("anteprimaTemplate")
	@Produces(MediaType.APPLICATION_JSON)
	Response anteprimaTemplate(@QueryParam("progrBandolinea") Long progrBandolinea,
			@QueryParam("idTipoDocumento") Long idTipoDocumento, @Context HttpServletRequest req)
			throws Exception, ErroreGestitoException;

}
