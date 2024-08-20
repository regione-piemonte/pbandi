/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;

import it.csi.pbandi.pbgestfinbo.integration.vo.AllegatiContestazioniVO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/contestazioni")
public interface ContestazioniService {


    @GET
    @Path("/getContestazioni")
    @Produces(MediaType.APPLICATION_JSON)
    Response getContestazioni(
            @DefaultValue("") @QueryParam("idProgetto") Long idProgetto,
            @Context HttpServletRequest req);

    @GET
    @Path("/getCodiceProgetto")
    @Produces(MediaType.APPLICATION_JSON)
    Response getCodiceProgetto(
            @DefaultValue("") @QueryParam("idProgetto") Long idProgetto,
            @Context HttpServletRequest req);

    @GET
    @Path("/getAllegati")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllegati(
            @DefaultValue("0") @QueryParam("idContestazione") Long idContestazione,
            @Context HttpServletRequest req);

    @POST
    @Path("/inserisciLetteraAllegato/{idContestazione}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	Response inserisciLetteraAllegato(
            @PathParam("idContestazione") Long idContestazione,
            List<AllegatiContestazioniVO> allegati,
            @Context HttpServletRequest req);

    @POST
    @Path("/deleteAllegato/{idFileEntita}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response deleteAllegato(
            @PathParam("idFileEntita") Long idFileEntita,
            @Context HttpServletRequest req
    );

    @POST
    @Path("/inserisciContestazione/{idGestioneRevoca}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	Response inserisciContestazione(
            @PathParam("idGestioneRevoca") Long idGestioneRevoca,
			@Context HttpServletRequest req);

    @POST
    @Path("/inviaContestazione/{idContestazione}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	Response inviaContestazione(
            @PathParam("idContestazione") Long idContestazione,
			@Context HttpServletRequest req);

    @POST
    @Path("/eliminaContestazione/{idContestazione}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	Response eliminaContestazione(
            @PathParam("idContestazione") Long idContestazione,
			@Context HttpServletRequest req);

}

