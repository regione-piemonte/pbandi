/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.business.service;

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
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbwebrce.integration.request.SalvaCheckListAffidamentoDocumentaleHtmlRequest;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Path("/checklist")
public interface ChecklistService {

	@GET
	@Path("/modelloAffidamentoHtml") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getModelloCheckListAffidamentoHtml( @Context HttpServletRequest req, 
												@QueryParam("idProgetto") Long idProgetto,
												@QueryParam("idAffidamento") Long idAffidamento,
												@QueryParam("soggettoControllore") String soggettoControllore,
												@QueryParam("codTipoChecklist") String codTipoChecklist) 
			throws UtenteException, Exception;
	
	@GET
	@Path("/salvaLockCheckListValidazione") 
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaLockCheckListValidazione( @Context HttpServletRequest req, 
			@QueryParam("idProgetto") Long idProgetto,
			@QueryParam("idAffidamento") Long idAffidamento) 
				throws UtenteException, Exception;

	@POST
	@Path("/salvaCheckListAffidamentoDocumentaleHtml")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaCheckListAffidamentoDocumentaleHtml(  @Context HttpServletRequest req, 
			MultipartFormDataInput multipartFormData
			) throws UtenteException, Exception;
	
	
	@POST
	@Path("/salvaBozzaCheckListAffidamentoDocumentaleHtml")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaBozzaCheckListAffidamentoDocumentaleHtml(  @Context HttpServletRequest req, 
			SalvaCheckListAffidamentoDocumentaleHtmlRequest salvachechlistaffReq
			) throws UtenteException, Exception;
	
	
	@POST
	@Path("/salvaChecklistInBozzaIntegrazioneAffidamentoDocumentaleHtml")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaChecklistInBozzaIntegrazioneAffidamentoDocumentaleHtml(  @Context HttpServletRequest req, 
			SalvaCheckListAffidamentoDocumentaleHtmlRequest salvachechlistaffReq
			) throws UtenteException, Exception;


	@POST
	@Path("/salvaCheckListAffidamentoDocumentaleHtmlBozza")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvaCheckListAffidamentoDocumentaleHtmlBozza( @Context HttpServletRequest req,
			MultipartFormDataInput multipartFormData ) throws UtenteException, Exception;
	
	@POST
	@Path("/salvaCheckListAffidamentoInLocoHtml")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaCheckListAffidamentoInLocoHtml(  MultipartFormDataInput multipartFormData
			) throws UtenteException, Exception;
	
	@GET
	@Path("/leggiStatoChecklist")
	@Produces(MediaType.APPLICATION_JSON)
	Response leggiStatoChecklist( @Context HttpServletRequest req, 
										@QueryParam("idEntita") Long idEntita,
										@QueryParam("idTarget") Long idTarget, 
										@QueryParam("idTipoDocIndexDoc") Long idTipoDocIndexDoc,
										@QueryParam("idProgetto") Long idProgetto) 
			throws UtenteException, Exception;
	
	@GET
	@Path("/findEntita")
	@Produces(MediaType.APPLICATION_JSON)
	Response findEntita( @Context HttpServletRequest req, 	@QueryParam("nomeEntita") String nomeEntita)
			throws UtenteException, Exception;
	
	
}