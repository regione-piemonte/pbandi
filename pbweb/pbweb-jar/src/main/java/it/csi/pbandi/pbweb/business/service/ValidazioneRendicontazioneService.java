package it.csi.pbandi.pbweb.business.service;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.pbandi.pbweb.integration.dao.request.CercaDocumentiDiSpesaValidazioneRequest;
import it.csi.pbandi.pbweb.integration.dao.request.OperazioneMassivaRequest;
import it.csi.pbandi.pbweb.integration.dao.request.ProseguiChiudiValidazioneRequest;
import it.csi.pbandi.pbweb.integration.dao.request.RichiediIntegrazioneRequest;
import it.csi.pbandi.pbweb.integration.dao.request.SalvaCheckListValidazioneDocumentaleHtmlRequest;
import it.csi.pbandi.pbweb.integration.dao.request.ValidaMensilitaRequest;
import it.csi.pbandi.pbweb.integration.dao.request.ValidaParzialmenteDocumentoRequest;
import it.csi.pbandi.pbweb.integration.dao.request.VerificaOperazioneMassivaRequest;

@Path("/validazioneRendicontazione")
public interface ValidazioneRendicontazioneService {

	@GET
	@Path("inizializzaValidazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response inizializzaValidazione(@QueryParam("idDichiarazioneDiSpesa") Long idDichiarazioneDiSpesa,
			@QueryParam("idProgetto") Long idProgetto, @QueryParam("idBandoLinea") Long idBandoLinea,
			@QueryParam("codiceRuolo") String codiceRuolo, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("initIntegrazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response initIntegrazione(@QueryParam("idDichiarazioneSpesa") Long idDichiarazioneSpesa,
			@Context HttpServletRequest req) throws Exception;

	/*
	 * @GET
	 * 
	 * @Path("getSvilConstant")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) Response getSvilConstant(@Context
	 * HttpServletRequest req) throws InvalidParameterException, Exception;
	 */

	@GET
	@Path("bandoIsEnteCompetenzaFinpiemonte")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response bandoIsEnteCompetenzaFinpiemonte(@QueryParam("progBandoLineaIntervento") Long progBandoLineaIntervento,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("inizializzaPopupChiudiValidazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response inizializzaPopupChiudiValidazione(@QueryParam("idDichiarazioneDiSpesa") Long idDichiarazioneDiSpesa,
			@QueryParam("idProgetto") Long idProgetto, @QueryParam("idBandoLinea") Long idBandoLinea,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@POST
	@Path("cercaDocumentiDiSpesaValidazione")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cercaDocumentiDiSpesaValidazione(
			CercaDocumentiDiSpesaValidazioneRequest cercaDocumentiDiSpesaValidazioneRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("recuperaMensilitaDichiarazioneSpesa")
	@Produces(MediaType.APPLICATION_JSON)
	Response recuperaMensilitaDichiarazioneSpesa(@QueryParam("idDichiarazioneSpesa") Long idDichiarazioneDiSpesa,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("validaMensilita")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response validaMensilita(List<ValidaMensilitaRequest> validaMensilitaRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;


	@GET
	@Path("dettaglioDocumentoDiSpesaPerValidazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response dettaglioDocumentoDiSpesaPerValidazione(
			@QueryParam("idDichiarazioneDiSpesa") Long idDichiarazioneDiSpesa,
			@QueryParam("idDocumentoDiSpesa") Long idDocumentoDiSpesa,
			@QueryParam("idProgetto") Long idProgetto,
			@QueryParam("idBandoLinea") Long idBandoLinea,
			@Context HttpServletRequest req) throws Exception;
	@GET
	@Path("dettaglioDocumentoDiSpesaPerValidazioneOld")
	@Produces(MediaType.APPLICATION_JSON)
	Response dettaglioDocumentoDiSpesaPerValidazioneOld(
			@QueryParam("idDichiarazioneDiSpesa") Long idDichiarazioneDiSpesa,
			@QueryParam("idDocumentoDiSpesa") Long idDocumentoDiSpesa,
			@QueryParam("idProgetto") Long idProgetto,
			@QueryParam("idBandoLinea") Long idBandoLinea,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("setDataNotifica")
	@Produces(MediaType.APPLICATION_JSON)
	Response setDataNotifica(@QueryParam("idIntegrazioneSpesa") Long idIntegrazioneSpesa,
			@QueryParam("dataNotifica") Date dataNotifica, @Context HttpServletRequest req) throws Exception;

	@GET
	@Path("sospendiDocumento")
	@Produces(MediaType.APPLICATION_JSON)
	Response sospendiDocumento(@QueryParam("idDichiarazioneDiSpesa") Long idDichiarazioneDiSpesa,
			@QueryParam("idDocumentoDiSpesa") Long idDocumentoDiSpesa, @QueryParam("idProgetto") Long idProgetto,
			@QueryParam("noteValidazione") String noteValidazione, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("respingiDocumento")
	@Produces(MediaType.APPLICATION_JSON)
	Response respingiDocumento(@QueryParam("idDichiarazioneDiSpesa") Long idDichiarazioneDiSpesa,
			@QueryParam("idDocumentoDiSpesa") Long idDocumentoDiSpesa, @QueryParam("idProgetto") Long idProgetto,
			@QueryParam("noteValidazione") String noteValidazione, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("invalidaDocumento")
	@Produces(MediaType.APPLICATION_JSON)
	Response invalidaDocumento(@QueryParam("idDichiarazioneDiSpesa") Long idDichiarazioneDiSpesa,
			@QueryParam("idDocumentoDiSpesa") Long idDocumentoDiSpesa, @QueryParam("idProgetto") Long idProgetto,
			@QueryParam("noteValidazione") String noteValidazione, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("validaDocumento")
	@Produces(MediaType.APPLICATION_JSON)
	Response validaDocumento(@QueryParam("idDichiarazioneDiSpesa") Long idDichiarazioneDiSpesa,
			@QueryParam("idDocumentoDiSpesa") Long idDocumentoDiSpesa, @QueryParam("idProgetto") Long idProgetto,
			@QueryParam("noteValidazione") String noteValidazione, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@POST
	@Path("validaParzialmenteDocumento")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response validaParzialmenteDocumento(ValidaParzialmenteDocumentoRequest validaParzialmenteDocumentoRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("reportDettaglioDocumentoDiSpesa")
	@Produces(MediaType.APPLICATION_JSON)
	Response reportDettaglioDocumentoDiSpesa(@QueryParam("idDichiarazioneDiSpesa") Long idDichiarazioneDiSpesa,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@POST
	@Path("verificaOperazioneMassiva")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response verificaOperazioneMassiva(VerificaOperazioneMassivaRequest verificaOperazioneMassivaRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@POST
	@Path("operazioneMassiva")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response operazioneMassiva(OperazioneMassivaRequest operazioneMassivaRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@POST
	@Path("richiediIntegrazione")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response richiediIntegrazione(RichiediIntegrazioneRequest richiediIntegrazioneRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@POST
	@Path("newRichiediIntegrazione")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newRichiediIntegrazione(
			MultipartFormDataInput multipartFormData,
			@Context HttpServletRequest req) throws Exception;

	@POST
	@Path("chiudiRichiestaIntegrazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response chiudiRichiestaIntegrazione(
			@QueryParam("idIntegrazione") Long idIntegrazione,
			@Context HttpServletRequest req);
	
	@GET
	@Path("chiudiValidazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response chiudiValidazione(@QueryParam("idDichiarazioneDiSpesa") Long idDichiarazioneDiSpesa,
			@QueryParam("idDocumentoIndex") Long idDocumentoIndex, @QueryParam("idBandoLinea") Long idBandoLinea,
			@QueryParam("invioExtraProcedura") Boolean invioExtraProcedura, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@POST
	@Path("proseguiChiudiValidazione")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response proseguiChiudiValidazione(ProseguiChiudiValidazioneRequest proseguiChiudiValidazioneRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("salvaProtocollo")
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaProtocollo(@QueryParam("idDocumentoIndex") Long idDocumentoIndex,
			@QueryParam("protocollo") String protocollo, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("getModuloCheckListValidazioneHtml")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getModuloCheckListValidazioneHtml(@QueryParam("idProgetto") String idProgetto,
			@QueryParam("idDichiarazioneDiSpesa") String idDichiarazioneDiSpesa, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@POST
	@Path("saveCheckListDocumentaleHtml")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveCheckListDocumentaleHtml(@Context HttpServletRequest req,
			SalvaCheckListValidazioneDocumentaleHtmlRequest salvachechlistaffReq)
			throws InvalidParameterException, Exception;

	@POST
	@Path("chiudiValidazioneChecklistHtmlOld")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response chiudiValidazioneChecklistHtmlOld(@Context HttpServletRequest req,
			SalvaCheckListValidazioneDocumentaleHtmlRequest salvachechlistaffReq)
			throws InvalidParameterException, Exception;

	@POST
	@Path("chiudiValidazioneChecklistHtml")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response chiudiValidazioneChecklistHtml(@Context HttpServletRequest req,
			MultipartFormDataInput multipartFormData) throws InvalidParameterException, Exception;

	@GET
	@Path("initDropDownCL")
	@Produces(MediaType.APPLICATION_JSON)
	public Response initDropDownCL(@DefaultValue("0")  @QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws Exception;


	@POST
	@Path("/chiudiValidazioneEsito")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	Response chiudiValidazioneEsito(MultipartFormDataInput multipartFormData, @Context HttpServletRequest req)
			throws Exception;

	@GET
	@Path("getSumImportoErogProgettiPercettori")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSumImportoErogProgettiPercettori(
			@QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
			@QueryParam("idDichiarazioneDiSpesa") Long idDichiarazioneDiSpesa, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@POST
	@Path("/chiudiValidazioneUfficio")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response chiudiValidazioneUfficio(
			@QueryParam("idDichiarazioneSpesa") Long idDichiarazioneSpesa,
			@Context HttpServletRequest req) throws Exception;
}
