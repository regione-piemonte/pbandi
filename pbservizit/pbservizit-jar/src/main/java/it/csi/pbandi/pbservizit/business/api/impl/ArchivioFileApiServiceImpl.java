/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.business.api.ArchivioFileApi;
import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoLeggiFile;
import it.csi.pbandi.pbservizit.integration.dao.ArchivioFileDAO;

@Service
public class ArchivioFileApiServiceImpl implements ArchivioFileApi {
	
	@Autowired
	private ArchivioFileDAO archivioFileDAO;
	
	@Override
	public Response inizializzaArchivioFile(
			@DefaultValue("0") @QueryParam("idUtente") long idUtente,
			@DefaultValue("0") @QueryParam("idSoggetto") long idSoggetto,
			@DefaultValue("0") @QueryParam("idSoggettoBeneficiario") long idSoggettoBeneficiario,
			@DefaultValue("")  @QueryParam("codiceRuolo") String codiceRuolo,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception {
	
		return Response.ok().entity(archivioFileDAO.inizializzaArchivioFile(idSoggetto, idSoggettoBeneficiario, codiceRuolo, idUtente, req)).build();
	}
	
	@Override
	public Response spazioDisco(
			@DefaultValue("0") @QueryParam("idSoggettoBeneficiario") long idSoggettoBeneficiario) throws InvalidParameterException, Exception {
	
		return Response.ok().entity(archivioFileDAO.spazioDisco(idSoggettoBeneficiario)).build();
	}

	@Override
	public Response leggiRoot(
			@DefaultValue("0") @QueryParam("idSoggetto") long idSoggetto,
			@DefaultValue("0") @QueryParam("idSoggettoBeneficiario") long idSoggettoBeneficiario,
			@DefaultValue("")  @QueryParam("codiceRuolo") String codiceRuolo) throws InvalidParameterException, Exception {
	
		return Response.ok().entity(archivioFileDAO.leggiRoot(idSoggetto,idSoggettoBeneficiario,codiceRuolo)).build();
	}
	
	@Override
	public Response leggiFolder(
			@DefaultValue("0") @QueryParam("idFolder") long idFolder,
			@DefaultValue("0") @QueryParam("idSoggetto") long idSoggetto,
			@DefaultValue("")  @QueryParam("codiceRuolo") String codiceRuolo) throws InvalidParameterException, Exception {
	
		return Response.ok().entity(archivioFileDAO.leggiFolder(idFolder,idSoggetto,codiceRuolo)).build();
	}
	
	
	/*
	@POST
	@Path("salvaFiles") 
	@Consumes({ "multipart/form-data" })
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaFiles(
			MultipartFormDataInput multipartFormData,
			@DefaultValue("0") @QueryParam("idFolder") long idFolder,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente,
			@DefaultValue("0") @QueryParam("idSoggettoBeneficiario") long idSoggettoBeneficiario) throws InvalidParameterException, Exception {
	
		return Response.ok().entity(archivioFileDAO.salvaFiles(multipartFormData, idFolder,idUtente,idSoggettoBeneficiario)).build();
	}
	*/
	@POST
	@Path("salvaFiles") 
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaFiles(MultipartFormDataInput multipartFormData) throws InvalidParameterException, Exception {
	
		return Response.ok().entity(archivioFileDAO.salvaFiles(multipartFormData)).build();
	}
	
	
	@GET
	@Path("leggiFile") 
	@Produces(MediaType.APPLICATION_JSON)
	public Response leggiFile(
			@DefaultValue("0") @QueryParam("idDocumentoIndex") long idDocumentoIndex) throws InvalidParameterException, Exception {
		EsitoLeggiFile esito = archivioFileDAO.leggiFile(idDocumentoIndex);
		return Response.ok().header("header-nome-file", esito.getNomeFile()).entity(esito.getBytes()).build();
	}	
	
	@GET
	@Path("leggiFileFirmato") 
	@Produces(MediaType.APPLICATION_JSON)
	public Response leggiFileFirmato(
			@DefaultValue("0") @QueryParam("idDocumentoIndex") long idDocumentoIndex) throws InvalidParameterException, Exception {
		EsitoLeggiFile esito = archivioFileDAO.leggiFileFirmato(idDocumentoIndex);
		return Response.ok().header("header-nome-file", esito.getNomeFile()).entity(esito.getBytes()).build();
	}	
	
	@GET
	@Path("leggiFileFirmaAutografa") 
	@Produces(MediaType.APPLICATION_JSON)
	public Response leggiFileFirmaAutografa(
			@DefaultValue("0") @QueryParam("idDocumentoIndex") long idDocumentoIndex) throws InvalidParameterException, Exception {
		EsitoLeggiFile esito = archivioFileDAO.leggiFileFirmaAutografa(idDocumentoIndex);
		return Response.ok().header("header-nome-file", esito.getNomeFile()).entity(esito.getBytes()).build();
	}	
	
	@GET
	@Path("leggiFileMarcato") 
	@Produces(MediaType.APPLICATION_JSON)
	public Response leggiFileMarcato(
			@DefaultValue("0") @QueryParam("idDocumentoIndex") long idDocumentoIndex) throws InvalidParameterException, Exception {
		EsitoLeggiFile esito = archivioFileDAO.leggiFileMarcato(idDocumentoIndex);
		return Response.ok().header("header-nome-file", esito.getNomeFile()).entity(esito.getBytes()).build();
	}	
	
	@GET
	@Path("creaFolder") 
	@Produces(MediaType.APPLICATION_JSON)
	public Response creaFolder(
			@DefaultValue("") @QueryParam("nomeFolder") String nomeFolder,
			@DefaultValue("0") @QueryParam("idFolderPadre") long idFolderPadre,
			@DefaultValue("0") @QueryParam("idSoggettoBeneficiario") long idSoggettoBeneficiario,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente) throws InvalidParameterException, Exception {
		
		return Response.ok().entity(archivioFileDAO.creaFolder(nomeFolder,idFolderPadre,idSoggettoBeneficiario,idUtente)).build();
	}
	
	@GET
	@Path("rinominaFolder")
	@Produces(MediaType.APPLICATION_JSON)
	public Response rinominaFolder(
			@DefaultValue("") @QueryParam("nomeFolder") String nomeFolder,
			@DefaultValue("0") @QueryParam("idFolder") long idFolder,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente) throws InvalidParameterException, Exception {
		
		return Response.ok().entity(archivioFileDAO.rinominaFolder(nomeFolder,idFolder,idUtente)).build();
	}
	
	
	@GET
	@Path("spostaFolder")
	@Produces(MediaType.APPLICATION_JSON)
	public Response spostaFolder(
			@DefaultValue("0") @QueryParam("idFolder") long idFolder,
			@DefaultValue("0") @QueryParam("idFolderDestinazione") long idFolderDestinazione,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente) throws InvalidParameterException, Exception {
		
		return Response.ok().entity(archivioFileDAO.spostaFolder(idFolder,idFolderDestinazione,idUtente)).build();
	}
	
	@GET
	@Path("cancellaFolder")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancellaFolder(
			@DefaultValue("0") @QueryParam("idFolder") long idFolder,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente) throws InvalidParameterException, Exception {
		
		return Response.ok().entity(archivioFileDAO.cancellaFolder(idFolder,idUtente)).build();
	}
	
	@GET
	@Path("rinominaFile")
	@Produces(MediaType.APPLICATION_JSON)
	public Response rinominaFile(
			@DefaultValue("") @QueryParam("nomeFile") String nomeFile,
			@DefaultValue("0") @QueryParam("idDocumentoIndex") long idDocumentoIndex,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente) throws InvalidParameterException, Exception {
		
		return Response.ok().entity(archivioFileDAO.rinominaFile(nomeFile,idDocumentoIndex,idUtente)).build();
	}
	
	@GET
	@Path("spostaFile")
	@Produces(MediaType.APPLICATION_JSON)
	public Response spostaFile(
			@DefaultValue("0") @QueryParam("idDocumentoIndex") long idDocumentoIndex,
			@DefaultValue("0") @QueryParam("idFolder") long idFolder,
			@DefaultValue("0") @QueryParam("idFolderDestinazione") long idFolderDestinazione,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente) throws InvalidParameterException, Exception {
		
		return Response.ok().entity(archivioFileDAO.spostaFile(idDocumentoIndex,idFolder,idFolderDestinazione,idUtente)).build();
	}
	
	@GET
	@Path("cancellaFile")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancellaFile(
			@DefaultValue("0") @QueryParam("idDocumentoIndex") long idDocumentoIndex,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente) throws InvalidParameterException, Exception {
		
		return Response.ok().entity(archivioFileDAO.cancellaFile(idDocumentoIndex,idUtente)).build();
	}
	
	@Override
	public Response infoFile(
			@DefaultValue("0") @QueryParam("idDocumentoIndex") long idDocumentoIndex,
			@DefaultValue("0") @QueryParam("idFolder") long idFolder,
			@DefaultValue("0") @QueryParam("idUtente") long idUtente,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception {
		
		return Response.ok().entity(archivioFileDAO.infoFile(idDocumentoIndex,idFolder,idUtente,req)).build();
	}
}
