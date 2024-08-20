/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;

import it.csi.pbandi.pbgestfinbo.integration.vo.search.DatiDistintaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.FiltroDistinteVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.FiltroProposteErogazioneDistVO;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/distinte")
public interface DistinteService {

    @GET
    @Path("/suggestCodiceFondoFinpis")
    @Produces(MediaType.APPLICATION_JSON)
    Response suggestCodiceFondoFinpis(
            @DefaultValue("") @QueryParam("value") String value,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/suggestDenominazione")
    @Produces(MediaType.APPLICATION_JSON)
    Response suggestDenominazione(
            @DefaultValue("") @QueryParam("value") String value,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/suggestProgetto")
    @Produces(MediaType.APPLICATION_JSON)
    Response suggestProgetto(
            @DefaultValue("") @QueryParam("value") String value,
            @DefaultValue("-1") @QueryParam("idBando") Long idBando,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/suggestBando")
    @Produces(MediaType.APPLICATION_JSON)
    Response suggestBando(
            @DefaultValue("") @QueryParam("value") String value,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/suggestAgevolazione")
    @Produces(MediaType.APPLICATION_JSON)
    Response suggestAgevolazione(
            @DefaultValue("") @QueryParam("value") String value,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/suggestDistintaRespinta")
    @Produces(MediaType.APPLICATION_JSON)
    Response suggestDistintaRespinta(
            @DefaultValue("") @QueryParam("value") String value,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/suggestDistinta")
    @Produces(MediaType.APPLICATION_JSON)
    Response suggestDistinta(
            @DefaultValue("") @QueryParam("value") String value,
            @Context HttpServletRequest req) throws Exception;

    @GET    // return true se posso aggiungere nuova distinta con idBando e idModalitaAgevolazione
    @Path("/nuovaDistinta")
    @Produces(MediaType.APPLICATION_JSON)
    Response nuovaDistinta(
            @DefaultValue("") @QueryParam("idBando") Long idBando,
            @DefaultValue("") @QueryParam("idModalitaAgevolazione") Long idModalitaAgevolazione,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/existsDistinta")
    @Produces(MediaType.APPLICATION_JSON)
    Response existsDistinta(
            @DefaultValue("") @QueryParam("idDistinta") Long idDistinta,
            @Context HttpServletRequest req) throws Exception;

    @GET    // return DettaglioDistintaVO
    @Path("/getNuovaDistinta")
    @Produces(MediaType.APPLICATION_JSON)
    Response getNuovaDistinta(
            @DefaultValue("") @QueryParam("idBando") Long idBando,
            @DefaultValue("") @QueryParam("idModalitaAgevolazione") Long idModalitaAgevolazione,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/copiaDistinta")
    @Produces(MediaType.APPLICATION_JSON)
    Response copiaDistinta(
            @DefaultValue("") @QueryParam("idDistinta") Long idDistinta,
            @Context HttpServletRequest req) throws Exception;

    @PUT
    @Path("/modificaDistinta")
    @Produces(MediaType.APPLICATION_JSON)
    Response modificaDistinta(
            @DefaultValue("") @QueryParam("idDistinta") Long idDistinta,
            DatiDistintaVO datiDistintaVO,
            @Context HttpServletRequest req) throws Exception;

    @POST
    @Path("/getProposteErogazione")
    @Produces(MediaType.APPLICATION_JSON)
    Response getProposteErogazione(
            FiltroProposteErogazioneDistVO filtroProposteErogazioneDistVO,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/getDettaglioDistinta")
    @Produces(MediaType.APPLICATION_JSON)
    Response getDettaglioDistinta(
            @DefaultValue("") @QueryParam("idDistinta") Long idDistinta,
            @Context HttpServletRequest req) throws Exception;

    @POST
    @Path("/salvaInBozza")
    @Produces(MediaType.APPLICATION_JSON)
    Response salvaInBozza(
            DatiDistintaVO datiDistintaVO,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/isAbilitatoAvvioIter")
    @Produces(MediaType.APPLICATION_JSON)
    Response isAbilitatoAvviaIter(
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/avviaIterAutorizzativo")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response avviaIterAutorizzativo(
            @DefaultValue("") @QueryParam("idDistinta") Long idDistinta,
            @Context HttpServletRequest req) throws Exception;

    @POST
    @Path("/salvaAllegato")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response salvaAllegato(
            MultipartFormDataInput multipartFormData,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/updateVisibilita")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response updateVisibilita(
            @DefaultValue("") @QueryParam("idDocumentoIndex") Long idDocumentoIndex,
            @DefaultValue("") @QueryParam("visibilita") String visibilita,
            @Context HttpServletRequest req) throws Exception;

    @DELETE
    @Path("/eliminaAllegato")
    @Produces(MediaType.APPLICATION_JSON)
    Response eliminaAllegato(
            @DefaultValue("") @QueryParam("idDocumentoIndex") Long idDocumentoIndex,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/checkAllegati")
    @Produces(MediaType.APPLICATION_JSON)
    Response checkAllegati(
            @DefaultValue("") @QueryParam("idDistinta") Long idDistinta,
            @Context HttpServletRequest req) throws Exception;

    @POST
    @Path("/filterDistinte")
    @Produces(MediaType.APPLICATION_JSON)
    Response filterDistinte(
            FiltroDistinteVO filtroDistinteVO,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/esportaDettaglioDistinta")
	//@Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    Response esportaDettaglioDistinta(
            @QueryParam("idDistinta") Long idDistinta,
            @Context HttpServletRequest req) throws Exception;

}
