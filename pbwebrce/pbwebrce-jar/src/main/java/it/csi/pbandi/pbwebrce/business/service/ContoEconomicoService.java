/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.business.service;

import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbwebrce.integration.request.InviaPropostaRimodulazioneRequest;
import it.csi.pbandi.pbwebrce.integration.request.SalvaPropostaRimodulazioneRequest;
import it.csi.pbandi.pbwebrce.integration.request.SalvaRichiestaContoEconomicoRequest;
import it.csi.pbandi.pbwebrce.integration.request.SalvaRimodulazioneConfermataRequest;
import it.csi.pbandi.pbwebrce.integration.request.SalvaRimodulazioneRequest;
import it.csi.pbandi.pbwebrce.integration.request.ValidaRimodulazioneConfermataRequest;

@Path("/contoEconomico")
public interface ContoEconomicoService {

	@GET
	@Path("inizializzaPropostaRimodulazione") 
	@Produces(MediaType.APPLICATION_JSON)
	Response inizializzaPropostaRimodulazione(
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("inizializzaPropostaRimodulazioneInDomanda") 
	@Produces(MediaType.APPLICATION_JSON)
	Response inizializzaPropostaRimodulazioneInDomanda(
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("contoEconomicoLocked") 
	@Produces(MediaType.APPLICATION_JSON)
	Response contoEconomicoLocked(
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("salvaPropostaRimodulazione")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaPropostaRimodulazione(
			SalvaPropostaRimodulazioneRequest salvaPropostaRimodulazioneRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("salvaPropostaRimodulazioneConfermata")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaPropostaRimodulazioneConfermata(
			SalvaPropostaRimodulazioneRequest salvaPropostaRimodulazioneRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("inizializzaConcludiPropostaRimodulazione")
	@Produces(MediaType.APPLICATION_JSON)
	public Response inizializzaConcludiPropostaRimodulazione(
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("associaAllegatiAPropostaRimodulazione")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response associaAllegatiAPropostaRimodulazione(
			AssociaFilesRequest associaFilesRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("disassociaAllegatoPropostaRimodulazione") 
	@Produces(MediaType.APPLICATION_JSON)
	Response disassociaAllegatoPropostaRimodulazione(
			@QueryParam("idDocumentoIndex") Long idDocumentoIndex,
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("allegatiPropostaRimodulazione") 
	@Produces(MediaType.APPLICATION_JSON)
	Response allegatiPropostaRimodulazione(
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("inviaPropostaRimodulazione")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response inviaPropostaRimodulazione(
			InviaPropostaRimodulazioneRequest inviaPropostaRimodulazioneRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("inizializzaUploadFileFirmato") 
	@Produces(MediaType.APPLICATION_JSON)
	Response inizializzaUploadFileFirmato(
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("salvaFileFirmato")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaFileFirmato(
			MultipartFormDataInput multipartFormData,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("verificaFirmaDigitale") 
	@Produces(MediaType.APPLICATION_JSON)
	Response verificaFirmaDigitale(
			@QueryParam("idDocumentoIndex") Long idDocumentoIndex,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("salvaInvioCartaceo") 
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaInvioCartaceo(
			@QueryParam("invioCartaceo") Boolean invioCartaceo,
			@QueryParam("idDocumentoIndex") Long idDocumentoIndex,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("inizializzaRimodulazione") 
	@Produces(MediaType.APPLICATION_JSON)
	Response inizializzaRimodulazione(
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("salvaRimodulazione")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaRimodulazione(
			SalvaRimodulazioneRequest salvaRimodulazioneRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("inizializzaConcludiRimodulazione")
	@Produces(MediaType.APPLICATION_JSON)
	public Response inizializzaConcludiRimodulazione(
			@QueryParam("idProgetto") Long idProgetto,
			@QueryParam("idBandoLinea") Long idBandoLinea,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("salvaRimodulazioneConfermata")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaRimodulazioneConfermata(
			SalvaRimodulazioneConfermataRequest salvaRimodulazioneConfermataRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("validaRimodulazioneConfermata")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response validaRimodulazioneConfermata(
			ValidaRimodulazioneConfermataRequest validaRimodulazioneConfermataRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("scaricaRimodulazione") 
	@Produces(MediaType.APPLICATION_JSON)
	Response scaricaRimodulazione(
			@QueryParam("idProgetto") Long idProgetto,
			@QueryParam("idSoggettoBeneficiario") Long idSoggettoBeneficiario,
			@QueryParam("idContoEconomico") Long idContoEconomico,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("inizializzaRimodulazioneIstruttoria") 
	@Produces(MediaType.APPLICATION_JSON)
	Response inizializzaRimodulazioneIstruttoria(
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("validaRimodulazioneIstruttoria")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response validaRimodulazioneIstruttoria(
			SalvaRimodulazioneRequest salvaRimodulazioneRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("salvaRimodulazioneIstruttoria")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaRimodulazioneIstruttoria(
			SalvaRimodulazioneRequest salvaRimodulazioneRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("inizializzaConcludiRimodulazioneIstruttoria")
	@Produces(MediaType.APPLICATION_JSON)
	public Response inizializzaConcludiRimodulazioneIstruttoria(
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("salvaRimodulazioneIstruttoriaConfermata")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaRimodulazioneIstruttoriaConfermata(
			SalvaRimodulazioneConfermataRequest salvaRimodulazioneConfermataRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("validaPropostaRimodulazioneInDomanda")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response validaPropostaRimodulazioneInDomanda(
			SalvaPropostaRimodulazioneRequest salvaPropostaRimodulazioneRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("salvaPropostaRimodulazioneInDomanda")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaPropostaRimodulazioneInDomanda(
			SalvaPropostaRimodulazioneRequest salvaPropostaRimodulazioneRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("inizializzaConcludiRichiestaContoEconomico")
	@Produces(MediaType.APPLICATION_JSON)
	public Response inizializzaConcludiRichiestaContoEconomico(
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("salvaRichiestaContoEconomico")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaRichiestaContoEconomico(
			SalvaRichiestaContoEconomicoRequest salvaRichiestaContoEconomico,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("getAltriCosti")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAltriCosti(
			@QueryParam("idBandoLinea") Long idBandoLinea,
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("getTipiAllegatiProposta")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTipiAllegatiProposta(
			@QueryParam("idBandoLinea") Long idBandoLinea,
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
}
