/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;

import it.csi.pbandi.pbgestfinbo.dto.RicercaControlliDTO;
import it.csi.pbandi.pbgestfinbo.integration.vo.iterAutorizzativi.IterListVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.iterAutorizzativi.SuggestIterVO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("/iterAutorizzativi")
public interface IterAutorizzativiService {

	//GET ITER
	@POST
	@Path("/cercaIterAutorizzativi")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	Response getIterAutorizzativiResponse(
			RicercaControlliDTO searchVO,
			@Context HttpServletRequest req)throws Exception;

	//GET DETTAGLIO ITER
	@GET
	@Path("/getDettaglioIter")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	Response getDettaglioIterResponse(
			@QueryParam("idWorkFlow") BigDecimal idWorkflow,
			@Context HttpServletRequest req) throws Exception;

	//GET ALLEGATI ITER
	@GET
	@Path("/getAllegatiIter")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	Response getAllegatiIterResponse(
			@QueryParam("idWorkFlow") BigDecimal idWorkflow,
			@Context HttpServletRequest req) throws Exception;
	@GET
	@Path("reportDettaglioDocumentoDiSpesa")
	@Produces(MediaType.APPLICATION_JSON)
	Response reportDettaglioDocumentoDiSpesa(
			@QueryParam("idDichiarazioneDiSpesa") Long idDichiarazioneDiSpesa,
			@Context HttpServletRequest req) throws Exception;

	//GESTIONE ITER
	@POST
	@Path("/respingiIter")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response respingiIter(
			IterListVO iterListVO,
			@Context HttpServletRequest req) throws Exception;
	@POST
	@Path("/autorizzaIter")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response autorizzaIter(
			IterListVO iterListVO,
			@Context HttpServletRequest req) throws Exception;

	//SUGGEST
	@POST
	@Path("/suggestIter")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	Response suggestIter(
			SuggestIterVO sugVO,
			@Context HttpServletRequest req) throws Exception;
	@GET
    @Path("/suggestBeneficiario")
    @Produces(MediaType.APPLICATION_JSON)
    Response suggestBeneficiario(
            @DefaultValue("") @QueryParam("value") String value,
            @Context HttpServletRequest req) throws Exception;
    @GET
    @Path("/suggestBando")
    @Produces(MediaType.APPLICATION_JSON)
    Response suggestBando(
            @DefaultValue("") @QueryParam("value") String value,
            @DefaultValue("") @QueryParam("idSoggetto") Long idSoggetto,
            @Context HttpServletRequest req) throws Exception;
    @GET
    @Path("/suggestProgetto")
    @Produces(MediaType.APPLICATION_JSON)
    Response suggestProgetto(
            @DefaultValue("") @QueryParam("value") String value,
            @DefaultValue("") @QueryParam("idSoggetto") Long idSoggetto,
            @DefaultValue("") @QueryParam("idBando") Long idBando,
            @Context HttpServletRequest req) throws Exception;
    @GET
    @Path("/getTendinaBando")
    @Produces(MediaType.APPLICATION_JSON)
    Response getTendinaBando(
    		@Context HttpServletRequest req) throws Exception;
    @GET
    @Path("/getTendinaProgetto")
    @Produces(MediaType.APPLICATION_JSON)
    Response getTendinaProgetto(
    		@Context HttpServletRequest req) throws Exception;
    @GET
    @Path("/getTendinaBeneficiario")
    @Produces(MediaType.APPLICATION_JSON)
    Response getTendinaBeneficiario(
    		@Context HttpServletRequest req) throws Exception;
	@GET
	@Path("/getStatoIter")
	@Produces(MediaType.APPLICATION_JSON)
	Response getStatoIterResponse(
			@Context HttpServletRequest req)
			throws Exception;
}
