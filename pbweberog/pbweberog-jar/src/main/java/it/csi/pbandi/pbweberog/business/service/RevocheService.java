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
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.EsitoSalvaRevocaDTO;
import it.csi.pbandi.pbweberog.dto.CodiceDescrizione;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.disimpegni.RigaModificaRevocaItem;
import it.csi.pbandi.pbweberog.dto.revoca.DettaglioRevoca;
import it.csi.pbandi.pbweberog.dto.revoca.RigaRevocaItem;
import it.csi.pbandi.pbweberog.integration.request.RequestModificaRevoca;
import it.csi.pbandi.pbweberog.integration.request.RequestSalvaRevoche;

@Path("/revoche")
@Api(value = "revoche")
public interface RevocheService {
	
	@GET
	@Path("/motivazioni") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds motivazioni", response=CodiceDescrizione.class, responseContainer = "List")
	Response findAndFilterMotivazioni( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;

	@GET
	@Path("/importoValidatoTotale") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds importo validato Totale", response=Double.class)
	Response getImportoValidatoTotale( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds revoche", response=RigaRevocaItem.class, responseContainer = "List")
	Response getRevoche( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;

	@POST
	@Path("/validazione") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Validate revoche.", response=EsitoSalvaRevocaDTO.class)
	Response checkRevoche( @Context HttpServletRequest req, RequestSalvaRevoche salvaRequest) throws UtenteException, Exception;
	
	@POST
	@Path("/importoRevocaNew") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Calcola ImportoRevoca per ogni revoca.", response=RigaRevocaItem.class, responseContainer = "List")
	Response revocaTutto( @Context HttpServletRequest req, RequestSalvaRevoche salvaRequest) throws UtenteException, Exception;
	
	@POST
	@Path("/all") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Saves revoche.", response=EsitoSalvaRevocaDTO.class)
	Response salvaRevoche( @Context HttpServletRequest req, RequestSalvaRevoche salvaRequest) throws UtenteException, Exception;
	
	////////////////////////////////////// MODIFICA REVOCA ///////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@GET
	@Path("/riepilogoDati") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds dettaglio revoca by idRevoca and idProgetto", response=RigaModificaRevocaItem.class, responseContainer = "List")
	Response getRevochePerModifica( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
	
	@GET
	@Path("/proposta") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Verifica se esiste una proposta di certificazione per il progetto.", response=EsitoOperazioni.class)
	Response checkPropostaCertificazioneProgetto( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
	
	@GET
	@Path("/{idRevoca}/dettagglio") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds dettaglio revoca by idRevoca and idProgetto", response=DettaglioRevoca.class)
	Response getDettaglioRevoca( @Context HttpServletRequest req, @PathParam("idRevoca") Long idRevoca, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
	
	@PUT
	@Path("/all") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Updates revoca.", response=EsitoSalvaRevocaDTO.class)
	Response modificaRevoca( @Context HttpServletRequest req, RequestModificaRevoca modificaRequest) throws UtenteException, Exception;	
	
	@DELETE
	@Path("/{idRevoca}") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Deletes revoca by idRevoca", response=EsitoSalvaRevocaDTO.class)
	Response cancellaRevoca( @Context HttpServletRequest req, @PathParam("idRevoca") Long idRevoca) throws UtenteException, Exception;
	
	

}
