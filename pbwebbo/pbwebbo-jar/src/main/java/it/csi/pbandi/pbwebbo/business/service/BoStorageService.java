/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Path("/boStorage")
public interface BoStorageService {

	@GET
	@Path("getTipiDocIndex")
	@Produces(MediaType.APPLICATION_JSON)
	Response getTipiDocIndex(@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("getProgettiByDesc")
	@Produces(MediaType.APPLICATION_JSON)
	Response getProgettiByDesc(@QueryParam("descrizione") String descrizione, @Context HttpServletRequest req)
			throws Exception;

	@GET
	@Path("ricercaDocumenti")
	@Produces(MediaType.APPLICATION_JSON)
	Response ricercaDocumenti(@DefaultValue("") @QueryParam("nomeFile") String nomeFile,
			@DefaultValue("0") @QueryParam("idTipoDocumentoIndex") Long idTipoDocumentoIndex,
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto, @Context HttpServletRequest req)
			throws Exception;

	@POST
	@Path("sostituisciDocumento") 
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	Response sostituisciDocumento(
			MultipartFormDataInput multipartFormData,
						 @Context HttpServletRequest req) throws Exception;

}
