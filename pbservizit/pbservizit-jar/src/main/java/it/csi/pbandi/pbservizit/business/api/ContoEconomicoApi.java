/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api;

import java.security.InvalidParameterException;
import java.util.Map;

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

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.web.bind.annotation.RequestBody;

@Path("/contoEconomico")
public interface ContoEconomicoApi {
	
	@GET
	@Path("inizializzaVisualizzaContoEconomico") 
	@Produces(MediaType.APPLICATION_JSON)
	Response inizializzaVisualizzaContoEconomico(
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("aggiornaVisualizzaContoEconomico") 
	@Produces(MediaType.APPLICATION_JSON)
	Response aggiornaVisualizzaContoEconomico(
			@QueryParam("idProgetto") Long idProgetto,
			@QueryParam("tipoRicerca") String tipoRicerca,
			@QueryParam("idPartner") Long idPartner,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("getSpesaAmmessaContoEconomico") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getSpesaAmmessaContoEconomico(
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("vociDiSpesaCultura")
	@Produces(MediaType.APPLICATION_JSON)
	Response vociDiSpesaCultura(
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("vociDiEntrataCultura")
	@Produces(MediaType.APPLICATION_JSON)
	Response vociDiEntrataCultura(
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@POST
	@Path("salvaVociDiEntrataCultura")
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaVociDiEntrataCultura(
			@RequestBody Map<String, Object>  requestData,
			@Context HttpServletRequest req)
			throws InvalidParameterException, Exception ;

	@GET
	@Path("contoEconomicoProposta")
	@Produces(MediaType.APPLICATION_JSON)
	Response contoEconomicoProposta(
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req)
			throws InvalidParameterException, Exception ;

	@GET
	@Path("percImportoAgevolato")
	@Produces(MediaType.APPLICATION_JSON)
	Response percImportoAgevolato(
			@QueryParam("idBando") Long idBando,
			@Context HttpServletRequest req)
			throws InvalidParameterException, Exception ;


	@POST
	@Path("salvaSpesePreventivate")
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaSpesePreventivate(
			@RequestBody Map<String, Object>  requestData,
			@Context HttpServletRequest req)
			throws InvalidParameterException, Exception ;
	
	@POST
	@Path("inviaDichiarazioneDiSpesaCultura")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response inviaDichiarazioneDiSpesaCultura(
			MultipartFormDataInput multipartFormData,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("vociDiEntrataPerRimodulazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response vociDiEntrataPerRimodulazione(
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
}
