/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.business.service;

import java.security.InvalidParameterException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.csi.pbandi.pbweb.dto.spesaValidata.MensilitaProgettoDTO;
import it.csi.pbandi.pbweb.integration.dao.request.CercaDocumentiSpesaValidataRequest;
import it.csi.pbandi.pbweb.integration.dao.request.ConfermaSalvaSpesaValidataRequest;
import it.csi.pbandi.pbweb.integration.dao.request.SalvaSpesaValidataRequest;

@Path("/spesaValidata")
public interface SpesaValidataService {

	@GET
	@Path("inizializzaSpesaValidata")
	@Produces(MediaType.APPLICATION_JSON)
	Response inizializzaSpesaValidata(@QueryParam("idProgetto") Long idProgetto, @QueryParam("isFP") String isFP,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("inizializzaModificaSpesaValidata")
	@Produces(MediaType.APPLICATION_JSON)
	Response inizializzaModificaSpesaValidata(@QueryParam("idDocumentoDiSpesa") Long idDocumentoDiSpesa,
			@QueryParam("numDichiarazione") String numDichiarazione, @QueryParam("idProgetto") Long idProgetto,
			@QueryParam("codiceRuolo") String codiceRuolo, @QueryParam("idBandoLinea") Long idBandoLinea,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@POST
	@Path("cercaDocumentiSpesaValidata")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cercaDocumentiSpesaValidata(CercaDocumentiSpesaValidataRequest cercaDocumentiSpesaValidataRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("recuperaMensilitaProgetto")
	@Produces(MediaType.APPLICATION_JSON)
	Response recuperaMensilitaProgetto(@QueryParam("idProgetto") Long idProgetto, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@POST
	@Path("validaMensilitaProgetto")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response validaMensilitaProgetto(List<MensilitaProgettoDTO> listaMensilitaProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("reportDettaglioDocumentoDiSpesa")
	@Produces(MediaType.APPLICATION_JSON)
	Response reportDettaglioDocumentoDiSpesa(@QueryParam("idProgetto") Long idProgetto, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("dichiarazioniDocumentoDiSpesa")
	@Produces(MediaType.APPLICATION_JSON)
	Response dichiarazioniDocumentoDiSpesa(@QueryParam("idDocumentoDiSpesa") Long idDocumentoDiSpesa,
			@QueryParam("idProgetto") Long idProgetto, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("voceDiSpesa")
	@Produces(MediaType.APPLICATION_JSON)
	Response voceDiSpesa(@QueryParam("idQuotaParte") Long idQuotaParte,
			@QueryParam("idDocumentoDiSpesa") Long idDocumentoDiSpesa, @QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@POST
	@Path("salvaSpesaValidata")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaSpesaValidata(SalvaSpesaValidataRequest salvaSpesaValidataRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@POST
	@Path("confermaSalvaSpesaValidata")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response confermaSalvaSpesaValidata(ConfermaSalvaSpesaValidataRequest confermaSalvaSpesaValidataRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("dettaglioRettifiche")
	@Produces(MediaType.APPLICATION_JSON)
	Response dettaglioRettifiche(@QueryParam("progQuotaParte") Long progQuotaParte, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@GET
	@Path("sospendiDocumentoSpesaValid")
	@Produces(MediaType.APPLICATION_JSON)
	Response sospendiDocumentoSpesaValid(@QueryParam("idDichiarazioneDiSpesa") Long idDichiarazioneDiSpesa,
			@QueryParam("idDocumentoDiSpesa") Long idDocumentoDiSpesa, @QueryParam("idProgetto") Long idProgetto,
			@QueryParam("noteValidazione") String noteValidazione, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@PUT
	@Path("salvaRilievoDS")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaRilievoDS(@QueryParam("idDichiarazioneDiSpesa") Long idDichiarazioneDiSpesa,
			@QueryParam("note") String note, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@PUT
	@Path("setNullaDaRilevare")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setNullaDaRilevare(@QueryParam("idDichiarazioneDiSpesa") Long idDichiarazioneDiSpesa,
			@QueryParam("idProgetto") Long idProgetto, List<Long> idDocumentiConRilievo,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@PUT
	@Path("chiudiRilievi")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response chiudiRilievi(@QueryParam("idDichiarazioneDiSpesa") Long idDichiarazioneDiSpesa,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("rilievoDocSpesa")
	@Produces(MediaType.APPLICATION_JSON)
	Response getRilievoDocSpesa(@QueryParam("idDocumentoDiSpesa") Long idDocumentoDiSpesa,
			@QueryParam("idProgetto") Long idProgetto,
			@QueryParam("idDichiarazioneDiSpesa") Long idDichiarazioneDiSpesa, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@PUT
	@Path("salvaRilievoDocSpesa")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaRilievoDocSpesa(@QueryParam("idDocumentoDiSpesa") Long idDocumentoDiSpesa,
			@QueryParam("idProgetto") Long idProgetto, @QueryParam("note") String note, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@PUT
	@Path("deleteRilievoDS")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRilievoDS(@QueryParam("idDichiarazioneDiSpesa") Long idDichiarazioneDiSpesa,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@PUT
	@Path("deleteRilievoDocSpesa")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRilievoDocSpesa(@QueryParam("idDocumentoDiSpesa") Long idDocumentoDiSpesa,
			@QueryParam("idProgetto") Long idProgetto, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

	@PUT
	@Path("confermaValiditaRilievi")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response confermaValiditaRilievi(@QueryParam("idDichiarazioneDiSpesa") Long idDichiarazioneDiSpesa,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("annullaSospendiDocumentoSpesaValid")
	@Produces(MediaType.APPLICATION_JSON)
	Response annullaSospendiDocumentoSpesaValid(@QueryParam("idDichiarazioneDiSpesa") Long idDichiarazioneDiSpesa,
			@QueryParam("idDocumentoDiSpesa") Long idDocumentoDiSpesa, @QueryParam("idProgetto") Long idProgetto,
			@QueryParam("noteValidazione") String noteValidazione, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;
}
