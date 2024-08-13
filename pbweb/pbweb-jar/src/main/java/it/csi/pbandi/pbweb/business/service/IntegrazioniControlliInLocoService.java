/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.business.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.csi.pbandi.pbservizit.exception.ErroreGestitoException;
import it.csi.pbandi.pbservizit.exception.RecordNotFoundException;
import it.csi.pbandi.pbweb.integration.vo.IntegrazioniControlliInLocoVO;



@Path("/IntegrazioniControlliInLoco")
public interface IntegrazioniControlliInLocoService {

	 
	 @GET
		@Path("/getIntegrazioniList")
		@Produces(MediaType.APPLICATION_JSON)
		Response getIntegrazioniResponse (
				@DefaultValue("") @QueryParam("idProgetto") int idProgetto,
		        @Context HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException;
	 
	 @GET
		@Path("/getAbilitaRichProroga")
		@Produces(MediaType.APPLICATION_JSON)
		Response getAbilitaRichProroga (
				@DefaultValue("") @QueryParam("idIntegrazione") int idIntegrazione,
				@DefaultValue("") @QueryParam("idControllo") int idControllo,
				@DefaultValue("") @QueryParam("idProgetto") int idProgetto,
		        @Context HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException;

	@GET
	@Path("/getLetteraIstruttore")
	@Produces(MediaType.APPLICATION_JSON)
	Response getLetteraIstruttore (
			@DefaultValue("0") @QueryParam("idIntegrazione") int idIntegrazione,
			@Context HttpServletRequest req);

	@GET
		@Path("/getAllegati")
		@Produces(MediaType.APPLICATION_JSON)
		Response getAllegati ( 
				@DefaultValue("0") @QueryParam("idIntegrazione") int idIntegrazione,
		        @Context HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException;
	 
	 @POST
		@Path("/inserisciLetteraAllegato/{idIntegrazione}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public  Response inserisciLetteraAllegato(
				@PathParam("idIntegrazione") Long idIntegrazione,
				List<IntegrazioniControlliInLocoVO> allegati,
				@Context HttpServletRequest req) throws ErroreGestitoException, Exception;


	 @POST
		@Path("/aggiornaIntegrazione/{idIntegrazione}")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public Response aggiornaIntegrazione(@PathParam("idIntegrazione") int idIntegrazione, @Context HttpServletRequest req)
				throws Exception;

	 
	 @POST
		@Path("/deleteAllegato")
	    @Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response deleteAllegato(
				IntegrazioniControlliInLocoVO  cv,
				@Context HttpServletRequest req
				)throws ErroreGestitoException, RecordNotFoundException;
	 
	 @POST
		@Path("/inserisciRichProroga/{idIntegrazione}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public  Response inserisciRichProroga(
				@PathParam("idIntegrazione") Long idIntegrazione,
				IntegrazioniControlliInLocoVO cp,
				@Context HttpServletRequest req) throws ErroreGestitoException, Exception;

}

