/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.web.bind.annotation.RequestBody;

import it.csi.pbandi.pbgestfinbo.integration.vo.NoteGeneraliVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.PassaggioPerditaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.TransazioneVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaStoricoEscussioneVO;


@Path("/passaggioPerdita")
public interface PassaggioPerditaService {
	
	@POST
	@Path("/salvaPassaggioPerdita")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaPassaggioPerdita(
			PassaggioPerditaVO passaggioPerdita, 
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgevolazione);
	
	
	@GET
	@Path("/getPassaggioPerdita")
	@Produces(MediaType.APPLICATION_JSON)
	Response getPassaggioPerdita(
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgevolazione);
	
	
	
	@GET
	@Path("/getStorico")
	@Produces(MediaType.APPLICATION_JSON)
	Response getStoricoPassaggioPerdita(
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgevolazione);
	
	
	
	//// Transazioni 
	@POST
	@Path("/salvaTransazione")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaTransazione(
			TransazioneVO transazioneVO,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgevolazione);
	
	
	@GET
	@Path("/getTransazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response getTransazione(
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgevolazione);
	
	
	@GET
	@Path("/getStoricoTransazioni")
	@Produces(MediaType.APPLICATION_JSON)
	Response getStoricoTransazioni(
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgevolazione);
	
	
//	@GET
//	@Path("/getListaBanche")
//	@Produces(MediaType.APPLICATION_JSON)
//	Response getListaBanca();

	@GET
	@Path("/getListaBanche")
	@Produces(MediaType.APPLICATION_JSON)
	Response getListaBanche(@QueryParam("banca") String banca);
	
	//// note
	
		// Spostate in AttivitaIstruttoreAreaCreditiService //
	
	/* Vecchio metodo - guarda EXPsalvaNota per il metodo aggiornato
	@POST
	@Path("/salvaNote")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaNote(
			MultipartFormDataInput multipartFormData, 
			@DefaultValue("") @QueryParam("isModifica") boolean isModifica,
			@DefaultValue("5") @QueryParam("idModalitaAgevolazione") int idModalitaAgevolazione) throws Exception;*/
	
	
	
	/*@GET
	@Path("/getNote")
	@Produces(MediaType.APPLICATION_JSON)
	Response getNote(
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("5") @QueryParam("idModalitaAgevolazione") int idModalitaAgevolazione);*/
	
	/*@GET
	@Path("/getStoricoNote")
	@Produces(MediaType.APPLICATION_JSON)
	Response getStoricoNote(
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("5") @QueryParam("idModalitaAgevolazione") int idModalitaAgevolazione);*/
	
	/*@POST
	@Path("/EXPsalvaNota")
	@Produces(MediaType.APPLICATION_JSON)
	Response EXPsalvaNota(
			@RequestBody NoteGeneraliVO nuovaNota,
			@DefaultValue("") @QueryParam("isModifica") boolean isModifica,
			@DefaultValue("5") @QueryParam("idModalitaAgevolazione") int idModalitaAgevolazione,
			@Context HttpServletRequest req) throws Exception;*/

}
