package it.csi.pbandi.pbweb.business.service;

import it.csi.pbandi.pbweb.dto.IntegrazioneRendicontazioneDTO;
import it.csi.pbandi.pbweb.dto.IntegrazioneRevocaDTO;
import it.csi.pbandi.pbweb.dto.rendicontazione.AllegatoDTO;
import it.csi.pbandi.pbweb.integration.vo.AllegatiDichiarazioneSpesaVO;
import it.csi.pbandi.pbweb.integration.vo.VisualizzaIntegrazioniVO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.security.InvalidParameterException;
import java.util.List;

@Path("/gestioneIntegrazioni")
public interface GestioneIntegrazioniService {
    @GET
    @Path("/getAbilitaInviaIntegr")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAbilitaInviaIntegr(
            @DefaultValue("0") @QueryParam("idRichIntegrazione") int idRichIntegrazione,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/getAllegatiIstruttoreRevoche")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllegatiIstruttoreRevoche(
            @DefaultValue("0") @QueryParam("idRichIntegraz") int idRichIntegraz,
            @Context HttpServletRequest req) throws Exception;
    @GET
    @Path("/getAllegati")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllegati(
            @DefaultValue("0") @QueryParam("idRichIntegraz") int idRichIntegraz,
            @Context HttpServletRequest req) throws Exception;
    @GET
    @Path("/getLetteraIstruttore")
    @Produces(MediaType.APPLICATION_JSON)
    Response getLetteraIstruttore(
            @DefaultValue("0") @QueryParam("idRichIntegraz") int idRichIntegraz,
            @Context HttpServletRequest req) throws Exception;

    @POST
    @Path("/salvaUploadLetteraAllegatoIntegr/{idRichIntegraz}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response salvaUploadLetteraAllegatoIntegr(
            @PathParam("idRichIntegraz") Long idRichIntegraz,
            List<VisualizzaIntegrazioniVO> allegati,
            @Context HttpServletRequest req) throws Exception;

    @POST
    @Path("/updateRichIntegrazione")
    @Produces(MediaType.APPLICATION_JSON)
    Response updateRichIntegrazione(
            @Context HttpServletRequest req,
            @RequestBody VisualizzaIntegrazioniVO vc)
            throws Exception;

    @POST
    @Path("/deleteAllegato")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response deleteAllegato(
            VisualizzaIntegrazioniVO acv,
            @Context HttpServletRequest req
    ) throws Exception;

    @GET
    @Path("/getAllegatiDichSpesa")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllegatiDichSpesa(
            @DefaultValue("0") @QueryParam("idDichSpesa") int idDichSpesa,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/getDocumentiSpesaSospesi")
    @Produces(MediaType.APPLICATION_JSON)
    Response getDocumentiSpesaSospesi(
            @DefaultValue("0") @QueryParam("idProgetto") int idProgetto,
            @Context HttpServletRequest req) throws Exception;

    @POST
    @Path("salvaAllegatiGenerici")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response salvaAllegatiGenerici(
            @DefaultValue("0") @QueryParam("idDichiarazioneSpesa") Long idDichiarazioneSpesa,
            @DefaultValue("0") @QueryParam("idIntegrazioneSpesa") Long idIntegrazioneSpesa,
            List<AllegatiDichiarazioneSpesaVO> allegati,
            @Context HttpServletRequest req) throws Exception;

    //INTEGRAZIONE ALLA REVOCA
    @GET
    @Path("getIntegrazioniRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response getIntegrazioniRevoca(
            @DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
            @DefaultValue("0") @QueryParam("idBandoLinea") Long idBandoLinea,
            @Context HttpServletRequest req) throws Exception;
    @GET
    @Path("getRegoleIntegrazione")
    @Produces(MediaType.APPLICATION_JSON)
    Response getRegoleIntegrazione(
            @DefaultValue("0") @QueryParam("idBandoLinea") Long idBandoLinea,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/getRichProroga")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response getRichProroga(
            @DefaultValue("0") @QueryParam("idIntegrazione") Long idIntegrazione,
            @Context HttpServletRequest req) throws Exception;

    @POST
    @Path("/inserisciRichProroga/{idIntegrazione}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response inserisciRichProroga(
            @PathParam("idIntegrazione") Long idIntegrazione,
            IntegrazioneRevocaDTO integrazioneRevocaDTO,
            @Context HttpServletRequest req) throws Exception;

    @POST
    @Path("/inviaIntegrazione/{idIntegrazione}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response inviaIntegrazione(
            @PathParam("idIntegrazione") Long idIntegrazione,
            @Context HttpServletRequest req) throws Exception;

    //INTEGRAZIONE ALLA RENDICONTAZIONE
    @GET
    @Path("/getIntegrazioniRendicontazione")
    @Produces(MediaType.APPLICATION_JSON)
    Response getIntegrazioniRendicontazione(
            @DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/getRichProrogaRendicontazione")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response getRichProrogaRendicontazione(
            @DefaultValue("0") @QueryParam("idIntegrazione") Long idIntegrazione,
            @Context HttpServletRequest req) throws Exception;

    @POST
    @Path("/inserisciRichProrogaRendicontazione/{idIntegrazione}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response inserisciRichProrogaRendicontazione(
            @PathParam("idIntegrazione") Long idIntegrazione,
            IntegrazioneRendicontazioneDTO integrazioneRendicontazioneDTO,
            @Context HttpServletRequest req) throws Exception;

    @POST
    @Path("/inviaIntegrazioneRendicontazione/{idIntegrazione}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response inviaIntegrazioneRendicontazione(
            @PathParam("idIntegrazione") Long idIntegrazione,
            @Context HttpServletRequest req) throws Exception;

    //ALLEGATI
    @GET
    @Path("/getLetteraAccIntegrazRendicont")
    @Produces(MediaType.APPLICATION_JSON)
    Response getLetteraAccIntegrazRendicont(
            @DefaultValue("0") @QueryParam("idIntegrazione") Long idIntegrazione,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/getReportValidazione")
    @Produces(MediaType.APPLICATION_JSON)
    Response getReportValidazione(
            @DefaultValue("0") @QueryParam("idIntegrazione") Long idIntegrazione,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/getAllegatiDichiarazioneSpesa")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllegatiDichiarazioneSpesa(
            @DefaultValue("0") @QueryParam("idDichiarazioneSpesa") Long idDichiarazioneSpesa,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/getAllegatiIntegrazioneDS")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllegatiIntegrazioneDS(
            @DefaultValue("0") @QueryParam("idIntegrazione") Long idIntegrazione,
            @DefaultValue("0") @QueryParam("idDichiarazioneSpesa") Long idDichiarazioneSpesa,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/getAllegatiNuovaIntegrazioneDS")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllegatiNuovaIntegrazioneDS(
            @DefaultValue("0") @QueryParam("idIntegrazione") Long idIntegrazione,
            @DefaultValue("0") @QueryParam("idDichiarazioneSpesa") Long idDichiarazioneSpesa,
            @Context HttpServletRequest req) throws Exception;

    //TAB Documenti di spesa sospesi
    @GET
    @Path("/getDocumentiDiSpesaSospesi")
    @Produces(MediaType.APPLICATION_JSON)
    Response getDocumentiDiSpesaSospesi(
            @DefaultValue("0") @QueryParam("idDichiarazioneSpesa") Long idDichiarazioneSpesa,
            @DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
            @Context HttpServletRequest req) throws Exception;
    @GET
    @Path("/getDocumentiDiSpesaIntegrati")
    @Produces(MediaType.APPLICATION_JSON)
    Response getDocumentiDiSpesaIntegrati(
            @DefaultValue("0") @QueryParam("idIntegrazione") Long idIntegrazione,
            @DefaultValue("0") @QueryParam("idDichiarazioneSpesa") Long idDichiarazioneSpesa,
            @DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/getAllegatiDocumentoSpesa")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllegatiDocumentoSpesa(
            @DefaultValue("0") @QueryParam("idDichiarazioneSpesa") Long idDichiarazioneSpesa,
            @DefaultValue("0") @QueryParam("idDocumentoDiSpesa") Long idDocumentoSpesa,
            @Context HttpServletRequest req) throws Exception;
    @GET
    @Path("/getAllegatiIntegrazioneDocS")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllegatiIntegrazioneDocS(
            @DefaultValue("0") @QueryParam("idIntegrazione") Long idIntegrazione,
            @DefaultValue("0") @QueryParam("idDichiarazioneSpesa") Long idDichiarazioneSpesa,
            @DefaultValue("0") @QueryParam("idDocumentoDiSpesa") Long idDocumentoSpesa,
            @Context HttpServletRequest req) throws Exception;
    @GET
    @Path("/getAllegatiNuovaIntegrazioneDocS")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllegatiNuovaIntegrazioneDocS(
            @DefaultValue("0") @QueryParam("idIntegrazione") Long idIntegrazione,
            @DefaultValue("0") @QueryParam("idDocumentoDiSpesa") Long idDocumentoSpesa,
            @Context HttpServletRequest req) throws Exception;


    @GET
    @Path("/getQuietanze")
    @Produces(MediaType.APPLICATION_JSON)
    Response getQuietanze(
            @DefaultValue("0") @QueryParam("idDocumentoSpesa") Long idDocumentoSpesa,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/getAllegatiQuietanza")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllegatiQuietanza(
            @DefaultValue("0") @QueryParam("idDichiarazioneSpesa") Long idDichiarazioneSpesa,
            @DefaultValue("0") @QueryParam("idQuietanza") Long idQuietanza,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/getAllegatiIntegrazioneQuietanza")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllegatiIntegrazioneQuietanza(
            @DefaultValue("0") @QueryParam("idIntegrazione") Long idIntegrazione,
            @DefaultValue("0") @QueryParam("idDichiarazioneSpesa") Long idDichiarazioneSpesa,
            @DefaultValue("0") @QueryParam("idQuietanza") Long idQuietanza,
            @Context HttpServletRequest req) throws Exception;

    @GET
    @Path("/getAllegatiNuovaIntegrazioneQuietanza")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllegatiNuovaIntegrazioneQuietanza(
            @DefaultValue("0") @QueryParam("idIntegrazione") Long idIntegrazione,
            @DefaultValue("0") @QueryParam("idQuietanza") Long idQuietanza,
            @Context HttpServletRequest req) throws Exception;

    //AGGIUNGI/RIMUOVI ALLEGATI
    @POST
    @Path("salvaAllegati")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response salvaAllegati(
            List<AllegatoDTO> allegati,
            @Context HttpServletRequest req) throws Exception;
    @DELETE
    @Path("/rimuoviAllegato")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response rimuoviAllegato(
            @DefaultValue("0") @QueryParam("idFileEntita") Long idFileEntita,
            @Context HttpServletRequest req) throws Exception;
    
    @GET
	@Path("allegatiDocSpesaQuietanzeDaInviare")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllegatiDocSpesaQuietanzeDaInviare(@QueryParam("idIntegrazioneSpesa") Long idIntegrazioneSpesa,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
}
