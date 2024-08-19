/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business.service;

import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.Query;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DelegatoDTO;
import it.csi.pbandi.pbweberog.dto.CodiceDescrizione;
import it.csi.pbandi.pbweberog.dto.erogazione.EsitoScaricaDichiarazioneDiRinuncia;
import it.csi.pbandi.pbweberog.dto.rinuncia.DichiarazioneRinuncia;
import it.csi.pbandi.pbweberog.dto.rinuncia.ResponseCreaCommunicazioneRinuncia;
import it.csi.pbandi.pbweberog.integration.request.CreaComunicazioneRinunciaRequest;

@Path("/rinuncia")
@Api(value = "rinuncia")
public interface RinunciaService {
	
	@GET
	@Path("/importoDaRestituire") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds ImportoTotaleErogato by idProgetto", response=Double.class)
	Response getImportoDaRestituire( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
	
	@GET
	@Path("/rappresentantiLegali/{idProgetto}") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds rappresentanti legali by idProgetto", response=CodiceDescrizione.class, responseContainer = "List")
	Response getRappresentantiLegali( @Context HttpServletRequest req, @PathParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
	
	@GET
	@Path("/delegati/{idProgetto}") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds delegati by idProgetto", response=DelegatoDTO.class, responseContainer = "List")
	Response getDelegati( @Context HttpServletRequest req, @PathParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
	
	@POST
	@Path("/comunicazioneRinuncia") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Creates and sends comunicazione di rinuncia", response=ResponseCreaCommunicazioneRinuncia.class)
	Response creaComunicazioneRinuncia( @Context HttpServletRequest req, CreaComunicazioneRinunciaRequest creaRequest) throws UtenteException, Exception;

	@GET
	@Path("/dichiarazioneDiRinuncia/{idDocumentoIndex}") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds report content of Dichiarazione Di Rinuncia by idDocumentoIndex", response=EsitoScaricaDichiarazioneDiRinuncia.class)
	Response scaricaDichiarazioneDiRinuncia( @Context HttpServletRequest req, @PathParam("idDocumentoIndex") Long idDocumentoIndex) throws UtenteException, Exception;
	
	@POST
	@Path("/salvaFileFirmato")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaFileFirmato(
			MultipartFormDataInput multipartFormData,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("/salvaFileFirmaAutografa")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaFileFirmaAutografa(
			MultipartFormDataInput multipartFormData,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("/verificaFirmaDigitale") 
	@Produces(MediaType.APPLICATION_JSON)
	Response verificaFirmaDigitale(
			@QueryParam("idDocumentoIndex") Long idDocumentoIndex,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("/salvaInvioCartaceo") 
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaInvioCartaceo(
			@QueryParam("invioCartaceo") Boolean invioCartaceo,
			@QueryParam("idDocumentoIndex") Long idDocumentoIndex,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("getIsBeneficiarioPrivatoCittadino") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getIsBeneficiarioPrivatoCittadino(
			@QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("getIsRegolaApplicabileForProgetto") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getIsRegolaApplicabileForProgetto(
			@QueryParam("idProgetto") Long idProgetto,
			@QueryParam("codiceRegola") String codiceRegola,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	
}
