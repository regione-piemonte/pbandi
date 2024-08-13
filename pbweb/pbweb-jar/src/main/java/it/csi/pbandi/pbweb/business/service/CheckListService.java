/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.business.service;

import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.pbandi.pbweb.dto.CercaChecklistRequestDTO;



@Path("/checklist")
public interface CheckListService {
		
	@GET
	@Path("getModuloCheckList")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getModuloCheckList( @QueryParam("idProgetto") String idProgetto, 
			@Context HttpServletRequest req) 
					throws InvalidParameterException, Exception;
	
	@GET
	@Path("getCheckListInLocoHtml")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCheckListInLocoHtml( @QueryParam("idDocumentoIndex") String idDocumentoIndex, 
			@Context HttpServletRequest req) 
					throws InvalidParameterException, Exception;
	
	@GET
	@Path("getCheckListValidazioneHtml")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCheckListValidazioneHtml( @QueryParam("idDocumentoIndex") String idDocumentoIndex, 
			@QueryParam("idDichiarazione") String idDichiarazione,
			@QueryParam("idProgetto") String idProgetto,
			@Context HttpServletRequest req) 
					throws InvalidParameterException, Exception;

	
	/**
	 * @GET
	@Path("tipologieDocumento") 
	@Produces(MediaType.APPLICATION_JSON)
	Response ottieniTipologieDocumentiDiSpesa(@DefaultValue("0") @QueryParam("idBandoLinea") int idBandoLinea);
	 */
	
	
	@GET
	@Path("caricaDichiarazioneDiSpesa")
	@Produces(MediaType.APPLICATION_JSON)
	public Response caricaDichiarazioneDiSpesa(
			@QueryParam("idProgetto") String idProgetto,
			@QueryParam("isFP") String isFP,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@GET
	@Path("caricaStatoSoggetto")
	@Produces(MediaType.APPLICATION_JSON)
	public Response caricaStatoSoggetto( @QueryParam("idUtente") String idUtente, 
										@QueryParam("idIride") String idIride, 
										@Context HttpServletRequest req) 
					throws InvalidParameterException, Exception;
	
	
	@POST
	@Path("cercaChecklist")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cercaChecklist( CercaChecklistRequestDTO request,
									@Context HttpServletRequest req) 
					throws InvalidParameterException, Exception;
	
	@GET
	@Path("inizializzaGestioneChecklist")
	@Produces(MediaType.APPLICATION_JSON)
	public Response inizializzaGestioneChecklist(@QueryParam("idProgetto") Long idProgetto,
												 @Context HttpServletRequest req) 
					throws InvalidParameterException, Exception;
	
	@GET
	@Path("eliminaChecklist")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminaChecklist(@QueryParam("idDocumentoIndex") Long idDocumentoIndex, 
									 @Context HttpServletRequest req) 
					throws InvalidParameterException, Exception;
	
	@GET
	@Path("salvaLockCheckListInLoco")
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaLockCheckListInLoco(
			@QueryParam("idProgetto") Long idProgetto, 
			@QueryParam("idDocumentoIndex") Long idDocumentoIndex,
			@Context HttpServletRequest req) 
					throws InvalidParameterException, Exception;
	
	@GET
	@Path("salvaLockCheckListValidazione")
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaLockCheckListValidazione(
			@QueryParam("idProgetto") Long idProgetto, 
			@QueryParam("idDichiarazione") Long idDichiarazione,
			@Context HttpServletRequest req) 
					throws InvalidParameterException, Exception;
	
	@POST
	@Path("salvaCheckListInLoco")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response salvaCheckListInLoco( MultipartFormDataInput multipartFormData) 
					throws InvalidParameterException, Exception;
	
	@POST
	@Path("saveCheckListDocumentaleHtml")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response saveCheckListDocumentaleHtml( MultipartFormDataInput multipartFormData) 
					throws InvalidParameterException, Exception;
	
	
	// Chiamato quando da Gestione Checklist si salva una checklist in loco definitiva (bottone 'salva definitiva').
	@POST
	@Path("saveCheckListDefinitivo")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response saveCheckListDefinitivo(  MultipartFormDataInput multipartFormData) 
					throws InvalidParameterException, Exception;

}
