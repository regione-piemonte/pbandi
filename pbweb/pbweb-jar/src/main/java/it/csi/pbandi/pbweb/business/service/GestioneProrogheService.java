/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/gestioneProroghe")
public interface GestioneProrogheService {
    @GET
    @Path("/getProrogaIntegrazione")
    @Produces(MediaType.APPLICATION_JSON)
    Response getProrogaIntegrazione(
            @DefaultValue("0") @QueryParam("idDichiarazioneSpesa") Long idDichiarazioneSpesa,
            @Context HttpServletRequest req) throws Exception;
    @GET
    @Path("/getStoricoProrogheIntegrazione")
    @Produces(MediaType.APPLICATION_JSON)
    Response getStoricoProrogheDS(
            @DefaultValue("0") @QueryParam("idDichiarazioneSpesa") Long idDichiarazioneSpesa,
            @Context HttpServletRequest req) throws Exception;
    @PUT
    @Path("/approvaProroga")
    @Produces(MediaType.APPLICATION_JSON)
    Response approvaProroga(
            @DefaultValue("0") @QueryParam("idProroga") Long idProroga,
            @DefaultValue("0") @QueryParam("numGiorni") Long numGiorni,
            @Context HttpServletRequest req) throws Exception;
    @PUT
    @Path("/respingiProroga")
    @Produces(MediaType.APPLICATION_JSON)
    Response respingiProroga(
            @DefaultValue("0") @QueryParam("idProroga") Long idProroga,
            @Context HttpServletRequest req) throws Exception;
    @GET
    @Path("/getStepProroga")
    @Produces(MediaType.APPLICATION_JSON)
    Response getStepProroga(
            @DefaultValue("0") @QueryParam("idDichiarazioneSpesa") Long idDichiarazioneSpesa,
            @Context HttpServletRequest req) throws Exception;
}
