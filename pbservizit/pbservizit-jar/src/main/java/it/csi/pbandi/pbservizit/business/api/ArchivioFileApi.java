/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api;

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

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;


@Path("/archivioFile")
public interface ArchivioFileApi {
	
	@GET
	@Path("inizializzaArchivioFile") 
	@Produces(MediaType.APPLICATION_JSON)
	Response inizializzaArchivioFile(
			@DefaultValue("0") @QueryParam("idUtente") long idUtente,
			@DefaultValue("0") @QueryParam("idSoggetto") long idSoggetto,
			@DefaultValue("0") @QueryParam("idSoggettoBeneficiario") long idSoggettoBeneficiario,
			@DefaultValue("")  @QueryParam("codiceRuolo") String codiceRuolo,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("spazioDisco") 
	@Produces(MediaType.APPLICATION_JSON)
	Response spazioDisco(
			@DefaultValue("0") @QueryParam("idSoggettoBeneficiario") long idSoggettoBeneficiario) throws InvalidParameterException, Exception;
	
	@GET
	@Path("leggiRoot") 
	@Produces(MediaType.APPLICATION_JSON)
	public Response leggiRoot(
			@DefaultValue("0") @QueryParam("idSoggetto") long idSoggetto,
			@DefaultValue("0") @QueryParam("idSoggettoBeneficiario") long idSoggettoBeneficiario,
			@DefaultValue("")  @QueryParam("codiceRuolo") String codiceRuolo) throws InvalidParameterException, Exception;
	
	@GET
	@Path("leggiFolder") 
	@Produces(MediaType.APPLICATION_JSON)
	public Response leggiFolder(
			@DefaultValue("0") @QueryParam("idFolder") long idFolder,
			@DefaultValue("0") @QueryParam("idSoggetto") long idSoggetto,
			@DefaultValue("")  @QueryParam("codiceRuolo") String codiceRuolo) throws InvalidParameterException, Exception;

	/*
	@POST
	@Path("salvaFiles")
	@Consumes({ "multipart/form-data" })
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaFiles(
			MultipartFormDataInput multipartFormData,
			@DefaultValue("0") @QueryParam("idFolder") long idFolder,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente,
			@DefaultValue("0") @QueryParam("idSoggettoBeneficiario") long idSoggettoBeneficiario) throws InvalidParameterException, Exception;
	*/
	@POST
	@Path("salvaFiles")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaFiles(
			MultipartFormDataInput multipartFormData) throws InvalidParameterException, Exception;
	
	
	@GET
	@Path("leggiFile") 
	@Produces(MediaType.APPLICATION_JSON)
	public Response leggiFile(
			@DefaultValue("0") @QueryParam("idDocumentoIndex") long idDocumentoIndex) throws InvalidParameterException, Exception;
	
	@GET
	@Path("leggiFileFirmato") 
	@Produces(MediaType.APPLICATION_JSON)
	public Response leggiFileFirmato(
			@DefaultValue("0") @QueryParam("idDocumentoIndex") long idDocumentoIndex) throws InvalidParameterException, Exception;
	
	@GET
	@Path("leggiFileFirmaAutografa") 
	@Produces(MediaType.APPLICATION_JSON)
	public Response leggiFileFirmaAutografa(
			@DefaultValue("0") @QueryParam("idDocumentoIndex") long idDocumentoIndex) throws InvalidParameterException, Exception;
	
	
	@GET
	@Path("leggiFileMarcato") 
	@Produces(MediaType.APPLICATION_JSON)
	public Response leggiFileMarcato(
			@DefaultValue("0") @QueryParam("idDocumentoIndex") long idDocumentoIndex) throws InvalidParameterException, Exception;
	
	@GET
	@Path("creaFolder") 
	@Produces(MediaType.APPLICATION_JSON)
	public Response creaFolder(
			@DefaultValue("") @QueryParam("nomeFolder") String nomeFolder,
			@DefaultValue("0") @QueryParam("idFolderPadre") long idFolderPadre,
			@DefaultValue("0") @QueryParam("idSoggettoBeneficiario") long idSoggettoBeneficiario,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente) throws InvalidParameterException, Exception;
	
	@GET
	@Path("rinominaFolder")
	@Produces(MediaType.APPLICATION_JSON)
	public Response rinominaFolder(
			@DefaultValue("") @QueryParam("nomeFolder") String nomeFolder,
			@DefaultValue("0") @QueryParam("idFolder") long idFolder,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente) throws InvalidParameterException, Exception;
	
	@GET
	@Path("spostaFolder")
	@Produces(MediaType.APPLICATION_JSON)
	public Response spostaFolder(
			@DefaultValue("0") @QueryParam("idFolder") long idFolder,
			@DefaultValue("0") @QueryParam("idFolderDestinazione") long idFolderDestinazione,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente) throws InvalidParameterException, Exception;
	
	@GET
	@Path("cancellaFolder")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancellaFolder(
			@DefaultValue("0") @QueryParam("idFolder") long idFolder,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente) throws InvalidParameterException, Exception;
	
	@GET
	@Path("rinominaFile")
	@Produces(MediaType.APPLICATION_JSON)
	public Response rinominaFile(
			@DefaultValue("") @QueryParam("nomeFile") String nomeFile,
			@DefaultValue("0") @QueryParam("idDocumentoIndex") long idDocumentoIndex,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente) throws InvalidParameterException, Exception;
	
	@GET
	@Path("spostaFile")
	@Produces(MediaType.APPLICATION_JSON)
	public Response spostaFile(
			@DefaultValue("0") @QueryParam("idDocumentoIndex") long idDocumentoIndex,
			@DefaultValue("0") @QueryParam("idFolder") long idFolder,
			@DefaultValue("0") @QueryParam("idFolderDestinazione") long idFolderDestinazione,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente) throws InvalidParameterException, Exception;
	
	@GET
	@Path("cancellaFile")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancellaFile(
			@DefaultValue("0") @QueryParam("idDocumentoIndex") long idDocumentoIndex,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente) throws InvalidParameterException, Exception;
	
	@GET
	@Path("infoFile")
	@Produces(MediaType.APPLICATION_JSON)
	public Response infoFile(
			@DefaultValue("0") @QueryParam("idDocumentoIndex") long idDocumentoIndex,
			@DefaultValue("0") @QueryParam("idFolder") long idFolder,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
}
