/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.business.service;

import java.security.InvalidParameterException;

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

import it.csi.pbandi.pbwebrce.dto.QteProgettoDTO;

@Path("/contoEconomicoWa")
public interface ContoEconomicoWaService {

	@GET
	@Path("modelloQte")
	@Produces(MediaType.APPLICATION_JSON)
	Response getModelloQte(@DefaultValue("0") @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("qteProgetto")
	@Produces(MediaType.APPLICATION_JSON)
	Response getQteProgetto(@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@POST
	@Path("salvaQteProgetto")
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaQteProgetto(QteProgettoDTO request, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("colonneModelloQte")
	@Produces(MediaType.APPLICATION_JSON)
	Response getColonneModelloQte(
			@DefaultValue("0") @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("datiCCC")
	@Produces(MediaType.APPLICATION_JSON)
	Response getDatiCCC(@DefaultValue("0") @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@POST
	@Path("salvaCCCDefinitivo")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaCCCDefinitivo(MultipartFormDataInput multipartFormData, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("idDocumentoIndexCCC")
	@Produces(MediaType.APPLICATION_JSON)
	Response getIdDocumentoIndexCCC(@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idQtesHtmlProgetto") Long idQtesHtmlProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
}
