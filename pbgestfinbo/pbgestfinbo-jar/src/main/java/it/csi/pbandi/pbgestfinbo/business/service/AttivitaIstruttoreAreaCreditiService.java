/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;


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
import org.springframework.web.bind.annotation.RequestBody;

import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.exception.RecordNotFoundException;
import it.csi.pbandi.pbgestfinbo.integration.vo.IscrizioneRuoloVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.NoteGeneraliVO;

@Path("/attivitaIstruttore")
public interface AttivitaIstruttoreAreaCreditiService {
	
	@GET
	@Path("/getBoxList")
	@Produces(MediaType.APPLICATION_JSON)
	Response getBoxList(
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") Long idModalitaAgevolazione,
			@DefaultValue("1") @QueryParam("idArea") Long idArea,
	        @Context HttpServletRequest req) throws Exception, InvalidParameterException;

	
	@GET
	@Path("/getLavorazioneBox")
	@Produces(MediaType.APPLICATION_JSON)
	Response getLavorazioneBox(
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") Long idModalitaAgevolazione,
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
	        @Context HttpServletRequest req) throws Exception, InvalidParameterException;
	
	
	@POST
	@Path("/salvaAllegati")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaAllegati(
			@QueryParam("idEntita") Long idEntita,
			@QueryParam("idEscussione") Long idEscussione,
			MultipartFormDataInput multipartFormDataInput,
			@Context HttpServletRequest req) throws Exception;
	
	
	// Iscrizione a Ruolo //
	
	@GET
	@Path("/getIscrizioneRuolo")
	@Produces(MediaType.APPLICATION_JSON)
	Response getIscrizioneRuolo(
			@DefaultValue("0") @QueryParam("idProgetto") String idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") Long idModalitaAgevolazione,
	        @Context HttpServletRequest req) throws Exception;
	
	@POST
	@Path("/setIscrizioneRuolo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setIscrizioneRuolo(
			@RequestBody IscrizioneRuoloVO iscrizioneRuolo,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgevolazione,
			@Context HttpServletRequest req)
			throws ErroreGestitoException,  RecordNotFoundException;
	
	
	// Note Generali //
	
	@GET
	@Path("/getNote")
	@Produces(MediaType.APPLICATION_JSON)
	Response getNote(
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("5") @QueryParam("idModalitaAgevolazione") int idModalitaAgevolazione);
	
	@GET
	@Path("/getStoricoNote")
	@Produces(MediaType.APPLICATION_JSON)
	Response getStoricoNote(
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("5") @QueryParam("idModalitaAgevolazione") int idModalitaAgevolazione);
	
	@POST
	@Path("/salvaNota")
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaNota(
			@RequestBody NoteGeneraliVO nuovaNota,
			@DefaultValue("") @QueryParam("isModifica") boolean isModifica,
			@DefaultValue("5") @QueryParam("idModalitaAgevolazione") int idModalitaAgevolazione,
			@Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("/eliminaNota")
	@Produces(MediaType.APPLICATION_JSON)
	Response eliminaNota(
			@DefaultValue("5") @QueryParam("idAnnotazione") int idAnnotazione,
			@DefaultValue("5") @QueryParam("idModalitaAgevolazione") int idModalitaAgevolazione,
			@Context HttpServletRequest req) throws Exception;
	
	@POST
	@Path("/eliminaAllegato")
	@Produces(MediaType.APPLICATION_JSON)
	Response eliminaAllegato(
			@DefaultValue("5") @QueryParam("idAnnotazione") int idAnnotazione,
			@DefaultValue("5") @QueryParam("idModalitaAgevolazione") int idModalitaAgevolazione,
			@Context HttpServletRequest req) throws Exception;
	
	
	
}