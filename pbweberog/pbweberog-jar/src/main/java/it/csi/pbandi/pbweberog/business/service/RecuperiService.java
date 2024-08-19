/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.EsitoSalvaRecuperoDTO;
import it.csi.pbandi.pbweberog.dto.CodiceDescrizione;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.recupero.DettaglioRecupero;
import it.csi.pbandi.pbweberog.dto.recupero.RigaModificaRecuperoItem;
import it.csi.pbandi.pbweberog.dto.recupero.RigaRecuperoItem;
import it.csi.pbandi.pbweberog.dto.recupero.Soppressione;
import it.csi.pbandi.pbweberog.integration.request.RequestModificaRecupero;
import it.csi.pbandi.pbweberog.integration.request.RequestSalvaRecuperi;

@Path("/recuperi")
@Api(value = "recuperi")
public interface RecuperiService {
	
	@GET
	@Path("/regolaBR28") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Verifica se è associata la regola BR28 per il progetto.", response=EsitoOperazioni.class)
	Response isRecuperoAccessibile( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
	
	@GET
	@Path("/tipologieRecuperi") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Tipologie recuperi.", response=CodiceDescrizione.class, responseContainer = "List")
	Response getTipologieRecuperi( @Context HttpServletRequest req) throws UtenteException, Exception;
	
	@GET
	@Path("/annoContabili") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Anno Contabili (for processo 2).", response=CodiceDescrizione.class, responseContainer = "List")
	Response getAnnoContabili( @Context HttpServletRequest req) throws UtenteException, Exception;
	
	
	@GET
	@Path("/processo") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds processo , if processo = 2 => nuova programmazione"
			+ "( enable disable combo anno contabile).", response=Long.class)
	Response getProcesso( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
	
	
	@GET
	@Path("/") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds recuperi.", response=RigaRecuperoItem.class, responseContainer = "List")
	Response getRecuperi( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
	
	@POST
	@Path("/validazione") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Validate recuperi.", response=EsitoSalvaRecuperoDTO.class)
	Response checkRecuperi( @Context HttpServletRequest req, RequestSalvaRecuperi salvaRequest) throws UtenteException, Exception;
	
	@POST
	@Path("/all") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Saves recuperi.", response=EsitoSalvaRecuperoDTO.class)
	Response salvaRecuperi( @Context HttpServletRequest req, RequestSalvaRecuperi salvaRequest) throws UtenteException, Exception;	
	
	////////////////////////////////////// MODIFICA RECUPERO //////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	@GET
	@Path("/riepilogoDati") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds riepilogo recupero.", response=RigaModificaRecuperoItem.class, responseContainer = "List")
	Response getRecuperiPerModifica( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
	
	@GET
	@Path("/proposta") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Verifica se esiste una proposta di certificazione per il progetto.", response=Boolean.class)
	Response checkPropostaCertificazioneProgetto( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
	
	@GET
	@Path("/dettaglio") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds dettaglio recupero by idProgetto and idRecupero", response=DettaglioRecupero.class)
	Response getDettaglioRecupero( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto, @QueryParam("idRecupero") Long idRecupero) throws UtenteException, Exception;
	
	@PUT
	@Path("/all") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Updates recupero.", response=EsitoSalvaRecuperoDTO.class)
	Response modificaRecupero( @Context HttpServletRequest req, RequestModificaRecupero modificaRequest) throws UtenteException, Exception;	
	
	@DELETE
	@Path("/{idRecupero}") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Deletes recupero by idRecupero", response=EsitoSalvaRecuperoDTO.class)
	Response cancellaRecupero( @Context HttpServletRequest req, @PathParam("idRecupero") Long idRecupero) throws UtenteException, Exception;
	
	@GET
	@Path("/inizializzaSoppressioni") 
	@Produces(MediaType.APPLICATION_JSON)
	Response inizializzaSoppressioni( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
	
	@POST
	@Path("/salvaSoppressione") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaSoppressione( @Context HttpServletRequest req, Soppressione soppressione) throws UtenteException, Exception;	
	
}
