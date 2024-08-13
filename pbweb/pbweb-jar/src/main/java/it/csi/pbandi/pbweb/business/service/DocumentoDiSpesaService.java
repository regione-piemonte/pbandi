/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.business.service;

import java.security.InvalidParameterException;

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

import it.csi.pbandi.pbweb.dto.FiltroRicercaDocumentiSpesa;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.VoceDiSpesaDTO;
//import it.csi.pbandi.pbweb.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbweb.integration.dao.request.AssociaPagamentoRequest;
import it.csi.pbandi.pbweb.integration.dao.request.PagamentiAssociatiRequest;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.DocumentoDiSpesaDTO;

@Path("/documentoDiSpesa")
public interface DocumentoDiSpesaService {

	@GET
	@Path("elencoAffidamenti")
	@Produces(MediaType.APPLICATION_JSON)
	Response elencoAffidamenti(@DefaultValue("0") @QueryParam("idProgetto") long idProgetto,
			@DefaultValue("0") @QueryParam("idFornitoreDocSpesa") long idFornitoreDocSpesa,
			@DefaultValue("") @QueryParam("codiceRuolo") String codiceRuolo)
			throws InvalidParameterException, Exception;

	@GET
	@Path("allegatiFornitore")
	@Produces(MediaType.APPLICATION_JSON)
	Response allegatiFornitore(@DefaultValue("0") @QueryParam("idFornitore") long idFornitore,
			@DefaultValue("0") @QueryParam("idProgetto") long idProgetto,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("allegatiDocumentoDiSpesa")
	@Produces(MediaType.APPLICATION_JSON)
	Response allegatiDocumentoDiSpesa(@DefaultValue("0") @QueryParam("idDocumentoDiSpesa") long idDocumentoDiSpesa,
			@DefaultValue("") @QueryParam("flagDocElettronico") String flagDocElettronico,
			@DefaultValue("0") @QueryParam("idProgetto") long idProgetto,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("disassociaAllegatoPagamento")
	@Produces(MediaType.APPLICATION_JSON)
	Response disassociaAllegatoPagamento(@DefaultValue("0") @QueryParam("idDocumentoIndex") long idDocumentoIndex,
			@DefaultValue("0") @QueryParam("idPagamento") long idPagamento,
			@DefaultValue("0") @QueryParam("idProgetto") long idProgetto, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("disassociaAllegatoDocumentoDiSpesa")
	@Produces(MediaType.APPLICATION_JSON)
	Response disassociaAllegatoDocumentoDiSpesa(
			@DefaultValue("0") @QueryParam("idDocumentoIndex") long idDocumentoIndex,
			@DefaultValue("0") @QueryParam("idDocumentoDiSpesa") long idDocumentoDiSpesa,
			@DefaultValue("0") @QueryParam("idProgetto") long idProgetto,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@POST
	@Path("salvaDocumentoDiSpesa")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaDocumentoDiSpesa(DocumentoDiSpesaDTO documentoDiSpesaDTO,
			@QueryParam("progrBandoLinea") Long progrBandoLinea,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@POST
	@Path("salvaDocumentoDiSpesaCultura")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaDocumentoDiSpesaCultura(DocumentoDiSpesaDTO documentoDiSpesaDTO,
			@QueryParam("progrBandoLinea") Long progrBandoLinea,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente,
			@DefaultValue("1") @QueryParam("tipoBeneficiario") long tipoBeneficiario, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@POST
	@Path("salvaDocumentoDiSpesaValidazione")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaDocumentoDiSpesaValidazione(DocumentoDiSpesaDTO documentoDiSpesaDTO,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("vociDiSpesaAssociateRendicontazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response vociDiSpesaAssociateRendicontazione(
			@DefaultValue("0") @QueryParam("idDocumentoDiSpesa") long idDocumentoDiSpesa,
			@DefaultValue("0") @QueryParam("idProgetto") long idProgetto,
			@DefaultValue("0") @QueryParam("idSoggetto") long idSoggetto,
			@DefaultValue("") @QueryParam("codiceRuolo") String codiceRuolo,
			@DefaultValue("") @QueryParam("tipoOperazioneDocSpesa") String tipoOperazioneDocSpesa,
			@DefaultValue("") @QueryParam("descBreveStatoDocSpesa") String descBreveStatoDocSpesa,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("vociDiSpesaAssociateValidazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response vociDiSpesaAssociateValidazione(
			@DefaultValue("0") @QueryParam("idDocumentoDiSpesa") long idDocumentoDiSpesa,
			@DefaultValue("0") @QueryParam("idProgetto") long idProgetto,
			@DefaultValue("0") @QueryParam("idSoggetto") long idSoggetto,
			@DefaultValue("") @QueryParam("codiceRuolo") String codiceRuolo,
			@DefaultValue("") @QueryParam("tipoOperazioneDocSpesa") String tipoOperazioneDocSpesa,
			@DefaultValue("") @QueryParam("descBreveStatoDocSpesa") String descBreveStatoDocSpesa,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("macroVociDiSpesa")
	@Produces(MediaType.APPLICATION_JSON)
	Response macroVociDiSpesa(@DefaultValue("0") @QueryParam("idDocumentoDiSpesa") long idDocumentoDiSpesa,
			@DefaultValue("0") @QueryParam("idProgetto") long idProgetto,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@POST
	@Path("associaVoceDiSpesa")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response associaVoceDiSpesa(VoceDiSpesaDTO voceDiSpesaDTO,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("disassociaVoceDiSpesa")
	@Produces(MediaType.APPLICATION_JSON)
	Response disassociaVoceDiSpesa(@DefaultValue("0") @QueryParam("idQuotaParteDocSpesa") long idQuotaParteDocSpesa,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("modalitaPagamento")
	@Produces(MediaType.APPLICATION_JSON)
	Response modalitaPagamento(@DefaultValue("0") @QueryParam("idDocumentoDiSpesa") long idDocumentoDiSpesa,
			@DefaultValue("0") @QueryParam("idProgetto") long idProgetto,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("causaliPagamento")
	@Produces(MediaType.APPLICATION_JSON)
	Response causaliPagamento(@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@POST
	@Path("associaPagamento")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response associaPagamento(AssociaPagamentoRequest associaPagamentoRequest, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("disassociaPagamento")
	@Produces(MediaType.APPLICATION_JSON)
	Response disassociaPagamento(@DefaultValue("0") @QueryParam("idPagamento") long idPagamento,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@POST
	@Path("associaAllegatiAPagamento")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response associaAllegatiAPagamento(AssociaFilesRequest associaFilesRequest, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@POST
	@Path("associaAllegatiADocSpesa")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response associaAllegatiADocSpesa(AssociaFilesRequest associaFilesRequest, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("vociDiSpesaRicerca")
	@Produces(MediaType.APPLICATION_JSON)
	Response vociDiSpesaRicerca(@DefaultValue("0") @QueryParam("idProgetto") long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("vociDiSpesaRicercaCultura")
	@Produces(MediaType.APPLICATION_JSON)
	Response vociDiSpesaRicercaCultura(@DefaultValue("0") @QueryParam("idProgetto") long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("partners")
	@Produces(MediaType.APPLICATION_JSON)
	Response partners(@DefaultValue("0") @QueryParam("idProgetto") long idProgetto,
			@DefaultValue("0") @QueryParam("idBandoLinea") long idBandoLinea, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@POST
	@Path("ricercaDocumentiDiSpesa")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response ricercaDocumentiDiSpesa(@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("") @QueryParam("codiceProgettoCorrente") String codiceProgettoCorrente,
			@DefaultValue("0") @QueryParam("idSoggettoBeneficiario") Long idSoggettoBeneficiario,
			@DefaultValue("") @QueryParam("codiceRuolo") String codiceRuolo, FiltroRicercaDocumentiSpesa filtro,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("documentoDiSpesa")
	@Produces(MediaType.APPLICATION_JSON)
	Response documentoDiSpesa(@DefaultValue("0") @QueryParam("idDocumentoDiSpesa") long idDocumentoDiSpesa,
			@DefaultValue("0") @QueryParam("idProgetto") long idProgetto, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("eliminaDocumentoDiSpesa")
	@Produces(MediaType.APPLICATION_JSON)
	Response eliminaDocumentoDiSpesa(@DefaultValue("0") @QueryParam("idDocumentoDiSpesa") long idDocumentoDiSpesa,
			@DefaultValue("0") @QueryParam("idProgetto") long idProgetto, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	/*
	 * @GET
	 * 
	 * @Path("pagamentiAssociati")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) Response pagamentiAssociati(
	 * 
	 * @DefaultValue("0") @QueryParam("idDocumentoDiSpesa") long idDocumentoDiSpesa,
	 * 
	 * @DefaultValue("0") @QueryParam("tipoInvioDocumentoDiSpesa") String
	 * tipoInvioDocumentoDiSpesa,
	 * 
	 * @DefaultValue("") @QueryParam("descBreveStatoDocSpesa") String
	 * descBreveStatoDocSpesa,
	 * 
	 * @DefaultValue("") @QueryParam("tipoOperazioneDocSpesa") String
	 * tipoOperazioneDocSpesa,
	 * 
	 * @DefaultValue("0") @QueryParam("idProgetto") long idProgetto,
	 * 
	 * @DefaultValue("0") @QueryParam("idBandoLinea") long idBandoLinea,
	 * 
	 * @DefaultValue("0") @QueryParam("codiceRuolo") String codiceRuolo,
	 * 
	 * @QueryParam("validazione") boolean validazione,
	 * 
	 * @Context HttpServletRequest req) throws InvalidParameterException, Exception;
	 */

	@POST
	@Path("pagamentiAssociati")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response pagamentiAssociati(PagamentiAssociatiRequest pagamentiAssociatiRequest, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("noteDiCreditoFattura")
	@Produces(MediaType.APPLICATION_JSON)
	Response noteDiCreditoFattura(@DefaultValue("0") @QueryParam("idDocumentoDiSpesa") long idDocumentoDiSpesa,
			@DefaultValue("0") @QueryParam("idProgetto") long idProgetto, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("popolaFormAssociaDocSpesa")
	@Produces(MediaType.APPLICATION_JSON)
	Response popolaFormAssociaDocSpesa(@QueryParam("idDocumentoDiSpesa") Long idDocumentoDiSpesa,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("associaDocumentoDiSpesaAProgetto")
	@Produces(MediaType.APPLICATION_JSON)
	Response associaDocumentoDiSpesaAProgetto(@QueryParam("idDocumentoDiSpesa") Long idDocumentoDiSpesa,
			@QueryParam("idProgetto") Long idProgetto, @QueryParam("idProgettoDocumento") Long idProgettoDocumento,
			@QueryParam("task") String task, @QueryParam("importoRendicontabile") Double importoRendicontabile,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("esitoLetturaXmlFattElett")
	@Produces(MediaType.APPLICATION_JSON)
	Response esitoLetturaXmlFattElett(@QueryParam("idDocumentoIndex") Long idDocumentoIndex,
			@QueryParam("idSoggettoBeneficiario") Long idSoggettoBeneficiario,
			@QueryParam("idTipoOperazioneProgetto") Long idTipoOperazioneProgetto, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("quotaImportoAgevolato")
	@Produces(MediaType.APPLICATION_JSON)
	Response getQuotaImportoAgevolato(@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("documentazioneDaAllegare")
	Response getDocumentazioneDaAllegare(
			@DefaultValue("0") @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
			@DefaultValue("0") @QueryParam("idTipoDocumentoSpesa") Long idTipoDocumentoSpesa,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("tipoBeneficiario")
	@Produces(MediaType.APPLICATION_JSON)
	Response getTipoBeneficiario(@QueryParam("idSoggetto") Long idSoggetto, @QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("parametriCompensi")
	Response getParametriCompensi(@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("importoASaldo")
	@Produces(MediaType.APPLICATION_JSON)
	Response getImportoASaldo(@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("getGiorniMassimiQuietanzaPerBando")
	@Produces(MediaType.APPLICATION_JSON)
	Response getGiorniMassimiQuietanzaPerBando(@DefaultValue("0") @QueryParam("idBandoLinea") Long idBandoLinea,
														@Context HttpServletRequest req) throws InvalidParameterException, Exception;

}
