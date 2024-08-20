/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;

import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.ArchivioRevocaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.PropostaRevocaMiniVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.FiltroPropostaRevocaVO;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ambitoRevoche")
public interface PropostaRevocaService {
    @POST
    @Path("/getProposteRevoche")
    @Produces(MediaType.APPLICATION_JSON)
    Response getProposteRevoche(
            FiltroPropostaRevocaVO filtroPropostaRevocaVO,
            @Context HttpServletRequest req) throws Exception;
    
    @GET
    @Path("/getInfoRevoche")
    @Produces(MediaType.APPLICATION_JSON)
    Response getInfoRevoche(
    		@QueryParam("id_gestione_revoca") Long idGestioneRevoca,
    		@QueryParam("id_soggetto") Long idSoggetto,
    		@QueryParam("id_domanda") Long idDomanda,
            @Context HttpServletRequest req) throws Exception;
    
    @GET
    @Path("/getImportiRevoche")
    @Produces(MediaType.APPLICATION_JSON)
    Response getImportiRevoche(
    		@QueryParam("id_gestione_revoca") Long idGestioneRevoca,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/getNotaRevoche")
    @Produces(MediaType.APPLICATION_JSON)
    Response getNotaRevoche(
    		@QueryParam("id_gestione_revoca") Long idGestioneRevoca,
            @Context HttpServletRequest req) throws Exception;
    
    @PUT
    @Path("/updateNotaRevoche")
    @Produces(MediaType.APPLICATION_JSON)
    Response updateNotaRevoche(
    		@QueryParam("id_gestione_revoca") Long idGestioneRevoca,
    		String nota,
            @Context HttpServletRequest req) throws Exception;

    @POST
    @Path("/creaBozzaProcedimentoRevoca")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response creaBozzaProcedimentoRevoca(
            MultipartFormDataInput multipartFormData,
            @Context HttpServletRequest req) throws Exception;

    @POST
    @Path("/creaBozzaProvvedimentoRevoca")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response creaBozzaProvvedimentoRevoca(
            MultipartFormDataInput multipartFormData,
            @Context HttpServletRequest req) throws Exception;

    @POST
    @Path("/archiviaPropostaRevoca")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response archiviaPropostaRevoca(
            ArchivioRevocaVO archivioRevocaVO,
            @QueryParam("idUtente") Long idUtente,
            @Context HttpServletRequest req) throws Exception;

    @POST
    @Path("/creaPropostaRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response creaPropostaRevoca(
            PropostaRevocaMiniVO propostaRevocaMiniVO,
            @Context HttpServletRequest req) throws Exception;
}
