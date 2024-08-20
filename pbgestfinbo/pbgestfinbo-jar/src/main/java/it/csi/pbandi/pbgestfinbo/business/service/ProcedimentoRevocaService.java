/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;

import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.ProcedimentoRevocaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.FiltroProcedimentoRevocaVO;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("/ambitoRevoche")
public interface ProcedimentoRevocaService {

    /*Ricerca Procedimento di Revoca*/
    @POST
    @Path("/getProcedimentoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response getProcedimentoRevoca(
            FiltroProcedimentoRevocaVO filtroProcedimentoRevocaVO,
            @Context HttpServletRequest req) throws Exception;

    /*Dettaglio Procedimento di Revoca*/
    @GET
    @Path("/getDettaglioProcedimentoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response getDettaglioProcedimentoRevoca(
            @QueryParam("idProcedimentoRevoca") Long idProcedimentoRevoca,
            @Context HttpServletRequest req) throws Exception;
    @GET
    @Path("getDocumentiProcedimentoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response getDocumentiProcedimentoRevoca(
            @QueryParam("numeroRevoca") Long numeroRevoca,
            @Context HttpServletRequest req) throws Exception;

    //Modifica Procedimento di Revoca
    @PUT
    @Path("/updateProcedimentoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response updateProcedimentoRevoca(
            ProcedimentoRevocaVO procedimentoRevocaVO,
            @Context HttpServletRequest req) throws Exception;

    /*GESTIONE PROCEDIMENTO DI REVOCA*/
    @DELETE
    @Path("/eliminaProcedimentoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response eliminaProcedimentoRevoca(
            @QueryParam("numeroProcedimentoRevoca") Long numeroProcedimentoRevoca,
            @Context HttpServletRequest req) throws Exception;
    @GET
    @Path("/verificaImporti")
    @Produces(MediaType.APPLICATION_JSON)
    Response verificaImporti(
            @QueryParam("idGestioneRevoca") Long idGestioneRevoca,
            @Context HttpServletRequest req) throws Exception;
    @POST
    @Path("/avviaProcedimentoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response avviaProcedimentoRevoca(
            @QueryParam("numeroProcedimentoRevoca") Long numeroProcedimentoRevoca,
            @QueryParam("giorniScadenza") Long giorniScadenza,
            @Context HttpServletRequest req) throws Exception;
    @POST
    @Path("/inviaIncaricoAErogazione")
    @Produces(MediaType.APPLICATION_JSON)
    Response inviaIncaricoAErogazione(
            @QueryParam("numeroProcedimentoRevoca") Long numeroProcedimentoRevoca,
            @QueryParam("numeroDichiarazioneSpesa") Long numeroDichiarazioneSpesa,
            @QueryParam("importoDaErogareContributo") BigDecimal importoDaErogareContributo,
            @QueryParam("importoDaErogareFinanziamento") BigDecimal importoDaErogareFinanziamento,
            @QueryParam("importoIres") BigDecimal importoIres,
            @QueryParam("giorniScadenza") Long giorniScadenza,
            @Context HttpServletRequest req) throws Exception;
    @POST
    @Path("/creaBozzaProvvedimentoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response creaBozzaProvvedimentoRevoca(
            @QueryParam("idGestioneRevoca") Long idGestioneRevoca,
            @Context HttpServletRequest req) throws Exception;
    @POST
    @Path("/archiviaProcedimentoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response archiviaProcedimentoRevoca(
            @QueryParam("numeroProcedimentoRevoca") Long numeroProcedimentoRevoca,
            @QueryParam("note") String note,
            @Context HttpServletRequest req) throws Exception;

    /*PROROGHE*/
    @GET
    @Path("/getRichiestaProroga")
    @Produces(MediaType.APPLICATION_JSON)
    Response getRichiestaProroga(
            @QueryParam("numeroProcedimentoRevoca") Long numeroProcedimentoRevoca,
            @Context HttpServletRequest req) throws Exception;
    @PUT
    @Path("/updateRichiestaProroga")
    @Produces(MediaType.APPLICATION_JSON)
    Response updateRichiestaProroga(
            @QueryParam("numeroProcedimentoRevoca") Long numeroProcedimentoRevoca,
            @QueryParam("esitoRichiestaProroga") Boolean esitoRichiestaProroga,
            @QueryParam("giorniApprovati") Long giorniApprovati,
            @Context HttpServletRequest req) throws Exception;

    /*INTEGRAZIONI*/
    @GET
    @Path("/abilitaRichiediIntegrazione")
    @Produces(MediaType.APPLICATION_JSON)
    Response abilitaRichiediIntegrazione(
            @QueryParam("idGestioneRevoca") Long idGestioneRevoca,
            @Context HttpServletRequest req) throws Exception;
    @POST
    @Path("/creaRichiestaIntegrazione")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response creaRichiestaIntegrazione(
            @QueryParam("idGestioneRevoca") Long idGestioneRevoca,
            @QueryParam("numGiorniScadenza") Long numGiorniScadenza,
            @Context HttpServletRequest req) throws Exception;
    @GET
    @Path("/abilitaChiudiIntegrazione")
    @Produces(MediaType.APPLICATION_JSON)
    Response abilitaChiudiIntegrazione(
            @QueryParam("idGestioneRevoca") Long idGestioneRevoca,
            @Context HttpServletRequest req) throws Exception;
    @PUT
    @Path("/chiudiRichiestaIntegrazione")
    @Produces(MediaType.APPLICATION_JSON)
    Response chiudiRichiestaIntegrazione(
            @QueryParam("idGestioneRevoca") Long idGestioneRevoca,
            @Context HttpServletRequest req) throws Exception;

    /*UTILS*/
    @POST
    @Path("/aggiungiAllegatoRevoche")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response aggiungiAllegato(
            @QueryParam("idGestioneRevoca") Long idGestioneRevoca,
            MultipartFormDataInput multipartFormDataInput,
            @Context HttpServletRequest req) throws Exception;

    @POST
    @Path("eliminaAllegato")
    @Produces(MediaType.APPLICATION_JSON)
    Response eliminaAllegato(
            @QueryParam("idDocumentoIndex") Long idDocumentoIndex,
            @Context HttpServletRequest req);

    @POST
    @Path("salvaNoteArchiviazione")
    @Produces(MediaType.APPLICATION_JSON)
    Response salvaNoteArchiviazione(
        @QueryParam("idGestioneRevoca") Long idGestioneRevoca,
        String note,
        @Context HttpServletRequest req);
}
