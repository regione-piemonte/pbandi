/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;

import it.csi.pbandi.pbgestfinbo.integration.vo.search.FiltroRevocaVO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ambitoRevoche")
public interface SuggestRevocheService {

    @GET
    @Path("/newNumeroRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response newNumeroRevoca(@Context HttpServletRequest req);

    @POST
    @Path("/suggestNumeroRevoche")
    @Produces(MediaType.APPLICATION_JSON)
    Response suggestNumeroRevoche(
            FiltroRevocaVO filtroRevocaVO,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/suggestBeneficiario")
    @Produces(MediaType.APPLICATION_JSON)
    Response suggestBeneficiario(
            @DefaultValue("") @QueryParam("value") String nomeBeneficiario,
            @DefaultValue("") @QueryParam("progrBandoLineaIntervent") Long progrBandoLineaIntervent,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/suggestBando")
    @Produces(MediaType.APPLICATION_JSON)
    Response suggestBandoLineaIntervent(
            @DefaultValue("") @QueryParam("value") String nomeBandoLinea,
            @DefaultValue("") @QueryParam("idSoggetto") Long idSoggetto,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/suggestProgetto")
    @Produces(MediaType.APPLICATION_JSON)
    Response suggestProgetto(
            @DefaultValue("") @QueryParam("value") String codiceVisualizzatoProgetto,
            @DefaultValue("") @QueryParam("idSoggetto") Long idSoggetto,
            @DefaultValue("") @QueryParam("progrBandoLineaIntervent") Long progrBandoLineaIntervent,
            @Context HttpServletRequest req) throws Exception;

    @POST
    @Path("/suggestCausaRevoche")
    @Produces(MediaType.APPLICATION_JSON)
    Response suggestCausaRevoche(
            FiltroRevocaVO filtroRevocaVO,
            @Context HttpServletRequest req) throws Exception;

    @POST
    @Path("/suggestStatoRevoche")
    @Produces(MediaType.APPLICATION_JSON)
    Response suggestStatoRevoche(
            FiltroRevocaVO filtroRevocaVO,
            @Context HttpServletRequest req) throws Exception;

    @POST
    @Path("/suggestAttivitaRevoche")
    @Produces(MediaType.APPLICATION_JSON)
    Response suggestAttivitaRevoche(
            FiltroRevocaVO filtroRevocaVO,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/suggestAllCauseRevoche")
    @Produces(MediaType.APPLICATION_JSON)
    Response suggestAllCausaRevoca(
            @Context HttpServletRequest req);

    @GET
    @Path("/suggestAllAutoritaControllanti")
    @Produces(MediaType.APPLICATION_JSON)
    Response suggestAllAutoritaControllante(
            @Context HttpServletRequest req);

    @GET
    @Path("/getModalitaRecupero")
    @Produces(MediaType.APPLICATION_JSON)
    Response getModalitaRecupero(
            @Context HttpServletRequest req);

    @GET
    @Path("/getMotivoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response getMotivoRevoca(
            @Context HttpServletRequest req);

}
