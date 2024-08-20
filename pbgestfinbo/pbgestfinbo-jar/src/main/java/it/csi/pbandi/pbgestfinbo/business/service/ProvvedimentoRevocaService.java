/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;

import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.ProvvedimentoRevocaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.FiltroRevocaVO;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ambitoRevoche")
public interface ProvvedimentoRevocaService {

    //Ricerca Provvedimento di Revoca
    @POST
    @Path("/getProvvedimentoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response getProvvedimentoRevoca(
            FiltroRevocaVO filtroRevocaVO,
            @Context HttpServletRequest req) throws Exception;

    //Dettaglio Provvedimento di Revoca
    @GET
    @Path("/getDettaglioProvvedimentoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response getDettaglioProvvedimentoRevoca(
            @QueryParam("numeroGestioneRevoca") Long numeroGestioneRevoca,
            @Context HttpServletRequest req) throws Exception;
    @GET
    @Path("getDocumentiProvvedimentoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response getDocumentiProvvedimentoRevoca(
            @QueryParam("numeroGestioneRevoca") Long numeroGestioneRevoca,
            @Context HttpServletRequest req) throws Exception;

    //Modifica Provvedimento di Revoca
    @PUT
    @Path("modificaProvvedimentoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response modificaProvvedimentoRevoca(
            @QueryParam("numeroGestioneRevoca") Long numeroGestioneRevoca,
            ProvvedimentoRevocaVO provvedimentoRevocaVO,
            @Context HttpServletRequest req) throws Exception;
    //Elimina Provvedimento di Revoca
    @DELETE
    @Path("/eliminaProvvedimentoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response eliminaProvvedimentoRevoca(
            @QueryParam("numeroRevoca") Long numeroRevoca,
            @Context HttpServletRequest req) throws Exception;

    //Emetti Provvedimento
    @GET
    @Path("abilitaEmettiProvvedimento")
    @Produces(MediaType.APPLICATION_JSON)
    Response abilitaEmettiProvvedimento(
            @QueryParam("numeroGestioneRevoca") Long numeroGestioneRevoca,
            @Context HttpServletRequest req) throws Exception;
    @POST
    @Path("/emettiProvvedimentoRevoca")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response emettiProvvedimentoRevoca(
            @QueryParam("numeroGestioneRevoca") Long numeroGestioneRevoca,
            @QueryParam("giorniScadenza") Long giorniScadenza,
            @Context HttpServletRequest req) throws Exception;

    //CONFERMA PROVVEDIMENTO
    @POST
    @Path("/confermaProvvedimentoRevoca")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response confermaProvvedimentoRevoca(
            @QueryParam("numeroGestioneRevoca") Long numeroGestioneRevoca,
            @Context HttpServletRequest req) throws Exception;

    //RITIRA IN AUTOTUTELA
    @POST
    @Path("/ritiroInAutotutelaRevoca")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response ritiroInAutotutelaRevoca(
            @QueryParam("numeroGestioneRevoca") Long numeroGestioneRevoca,
            @Context HttpServletRequest req) throws Exception;

    //GESTIONE ALLEGATI
    @POST
    @Path("/aggiungiAllegatoProvvedimento")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response aggiungiAllegato(
            @QueryParam("numeroRevoca") Long numeroRevoca,
            MultipartFormDataInput multipartFormDataInput,
            @Context HttpServletRequest req) throws Exception;
}
