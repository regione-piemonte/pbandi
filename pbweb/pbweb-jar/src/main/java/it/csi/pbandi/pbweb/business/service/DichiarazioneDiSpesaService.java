/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.business.service;

import java.security.InvalidParameterException;
import java.util.ArrayList;

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

import it.csi.pbandi.pbweb.integration.dao.request.AnteprimaDichiarazioneDiSpesaCulturaRequest;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

//import it.csi.pbandi.pbweb.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.TipoAllegatoDTO;
import it.csi.pbandi.pbweb.integration.dao.request.AnteprimaDichiarazioneDiSpesaRequest;
import it.csi.pbandi.pbweb.integration.dao.request.VerificaDichiarazioneDiSpesaRequest;

@Path("/dichiarazioneDiSpesa")
public interface DichiarazioneDiSpesaService {
	
	
	@GET
	@Path("getIsBeneficiarioPrivatoCittadino") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getIsBeneficiarioPrivatoCittadino(
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("isBottoneConsuntivoVisibile") 
	@Produces(MediaType.APPLICATION_JSON)
	Response isBottoneConsuntivoVisibile(
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	
	@GET
	@Path("rappresentantiLegali") 
	@Produces(MediaType.APPLICATION_JSON)
	Response rappresentantiLegali(
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("verificaDichiarazioneDiSpesa")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response verificaDichiarazioneDiSpesa(
			VerificaDichiarazioneDiSpesaRequest verificaDichiarazioneDiSpesaRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("delegatiCombo") 
	@Produces(MediaType.APPLICATION_JSON)
	Response delegatiCombo(
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("iban") 
	@Produces(MediaType.APPLICATION_JSON)
	Response iban(
			@QueryParam("idProgetto") Long idProgetto,
			@QueryParam("idSoggettoBeneficiario") Long idSoggettoBeneficiario,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("tipoAllegati") 
	@Produces(MediaType.APPLICATION_JSON)
	Response tipoAllegati(
			@QueryParam("idBandoLinea") Long idBandoLinea,
			@QueryParam("codiceTipoDichiarazioneDiSpesa") String codiceTipoDichiarazioneDiSpesa,
			@QueryParam("idDichiarazione") Long idDichiarazione,
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("salvaTipoAllegati") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaTipoAllegati(
			ArrayList<TipoAllegatoDTO> listaTipoAllegati,
			@QueryParam("codiceTipoDichiarazioneDiSpesa") String codiceTipoDichiarazioneDiSpesa,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("allegatiDichiarazioneDiSpesaPerIdProgetto") 
	@Produces(MediaType.APPLICATION_JSON)
	Response allegatiDichiarazioneDiSpesaPerIdProgetto(
			@QueryParam("codiceTipoDichiarazioneDiSpesa") String codiceTipoDichiarazioneDiSpesa,
			@QueryParam("idProgetto") Long idProgetto,			
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("allegatiDichiarazioneDiSpesa")
	@Produces(MediaType.APPLICATION_JSON)
	Response allegatiDichiarazioneDiSpesa(
			@QueryParam("idDichiarazioneDiSpesa") Long idDichiarazioneDiSpesa,
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	@GET
	@Path("allegatiDichiarazioneDiSpesaIntegrazioni")
	@Produces(MediaType.APPLICATION_JSON)
	Response allegatiDichiarazioneDiSpesaIntegrazioni(
			@QueryParam("idDichiarazioneDiSpesa") Long idDichiarazioneDiSpesa,
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("disassociaAllegatoDichiarazioneDiSpesa") 
	@Produces(MediaType.APPLICATION_JSON)
	Response disassociaAllegatoDichiarazioneDiSpesa(
			@QueryParam("idDocumentoIndex") Long idDocumentoIndex,
			@QueryParam("idDichiarazioneDiSpesa") Long idDichiarazioneDiSpesa,
			@QueryParam("idProgetto") Long idProgetto,
			@QueryParam("tipoDichiarazione") String tipoDichiarazione,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("disassociaAllegatoIntegrazioneDiSpesa") 
	@Produces(MediaType.APPLICATION_JSON)
	Response disassociaAllegatoIntegrazioneDiSpesa(
			@QueryParam("idDocumentoIndex") Long idDocumentoIndex,
			@QueryParam("idIntegrazioneDiSpesa") Long idIntegrazioneDiSpesa,
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("associaAllegatiADichiarazioneDiSpesa")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response associaAllegatiADichiarazioneDiSpesa(
			AssociaFilesRequest associaFilesRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("associaAllegatiADichiarazioneDiSpesaFinale")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response associaAllegatiADichiarazioneDiSpesaFinale(
			AssociaFilesRequest associaFilesRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("associaAllegatiAIntegrazioneDiSpesa")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response associaAllegatiAIntegrazioneDiSpesa(
			AssociaFilesRequest associaFilesRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("inizializzaInvioDichiarazioneDiSpesa") 
	@Produces(MediaType.APPLICATION_JSON)
	Response inizializzaInvioDichiarazioneDiSpesa(
			@QueryParam("idProgetto") Long idProgetto,
			@QueryParam("idBandoLinea") Long idBandoLinea,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("inizializzaIntegrazioneDiSpesa") 
	@Produces(MediaType.APPLICATION_JSON)
	Response inizializzaIntegrazioneDiSpesa(
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("anteprimaDichiarazioneDiSpesa")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response anteprimaDichiarazioneDiSpesa(
			AnteprimaDichiarazioneDiSpesaRequest anteprimaDichiarazioneDiSpesaRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@POST
	@Path("anteprimaDichiarazioneDiSpesaCultura")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response anteprimaDichiarazioneDiSpesaCultura(
			AnteprimaDichiarazioneDiSpesaCulturaRequest anteprimaDichiarazioneDiSpesaRequest,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("inviaDichiarazioneDiSpesa")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response inviaDichiarazioneDiSpesa(
			MultipartFormDataInput multipartFormData,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("salvaInvioCartaceo") 
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaInvioCartaceo(
			@QueryParam("invioCartaceo") Boolean invioCartaceo,
			@QueryParam("idDocumentoIndex") Long idDocumentoIndex,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("salvaFileFirmato")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaFileFirmato(
			MultipartFormDataInput multipartFormData,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("salvaFileFirmaAutografa")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaFileFirmaAutografa(
			MultipartFormDataInput multipartFormData,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("verificaFirmaDigitale") 
	@Produces(MediaType.APPLICATION_JSON)
	Response verificaFirmaDigitale(
			@QueryParam("idDocumentoIndex") Long idDocumentoIndex,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("integrazioniSpesaByIdProgetto") 
	@Produces(MediaType.APPLICATION_JSON)
	Response integrazioniSpesaByIdProgetto(
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("marcaComeDichiarazioneDiIntegrazione") 
	@Produces(MediaType.APPLICATION_JSON)
	Response marcaComeDichiarazioneDiIntegrazione(
			@QueryParam("idDocumentoIndex") Long idDocumentoIndex,
			@QueryParam("idIntegrazioneDiSpesa") Long idIntegrazioneDiSpesa,
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	 
	@GET
	@Path("inviaIntegrazioneDiSpesaAIstruttore") 
	@Produces(MediaType.APPLICATION_JSON)
	Response inviaIntegrazioneDiSpesaAIstruttore(
			@QueryParam("idIntegrazioneDiSpesa") Long idIntegrazioneDiSpesa,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	@GET
	@Path("isBandoCultura")
	@Produces(MediaType.APPLICATION_JSON)
	Response isBandoCultura(
			@QueryParam("idBandoLinea") Long idBandoLinea,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	/*
	@GET
	@Path("testAnteprimaDichiarazioneDiSpesa")
	@Produces(MediaType.APPLICATION_JSON)
	public Response testAnteprimaDichiarazioneDiSpesa(
			@QueryParam("idBandoLinea") Long idBandoLinea,
			@QueryParam("idProgetto") Long idProgetto,
			@QueryParam("idSoggetto") Long idSoggetto,
			@QueryParam("idSoggettoBeneficiario") Long idSoggettoBeneficiario,
			@QueryParam("idRappresentanteLegale") Long idRappresentanteLegale,
			@QueryParam("codiceTipoDichiarazioneDiSpesa") String codiceTipoDichiarazioneDiSpesa,			
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	*/
}

