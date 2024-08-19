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
import it.csi.pbandi.pbweberog.dto.CodiceDescrizione;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.fideiussioni.Fideiussione;
import it.csi.pbandi.pbweberog.dto.fideiussioni.FideiussioneDTO;
import it.csi.pbandi.pbweberog.dto.fideiussioni.FideiussioneEsitoGenericoDTO;
import it.csi.pbandi.pbweberog.integration.request.CercaFideiussioniRequest;
import it.csi.pbandi.pbweberog.integration.request.CreaAggiornaFideiussioneRequest;

@Path("/fideiussioni")
@Api(value = "fideiussioni")
public interface FideiussioniService {


	@GET
	@Path("/home") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Verify first that the Fideiussione is manageable", response=EsitoOperazioni.class)
	Response isFideiussioneGestibile( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
	
	@GET
	@Path("/tipiDiTrattamento") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Tipi Di Trattamento (load combo tipo trattamento) ", response=CodiceDescrizione.class, responseContainer = "List")
	Response getTipiDiTrattamento( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
	
	@GET
	@Path("/tipiDiTrattamento/{idTipoTrattamento}") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Tipo Di Trattamento by idTipoTrattamento(for dettaglio Fideiussione) ", response=String.class)
	Response getTipoDiTrattamento( @Context HttpServletRequest req, @PathParam("idTipoTrattamento") Long idTipoTrattamento) throws UtenteException, Exception;
	
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Fideiussioni by idProgetto and FiltroRicercaFideiussione", response=Fideiussione.class, responseContainer = "List")
	Response getFideiussioni( @Context HttpServletRequest req, CercaFideiussioniRequest cercaRequest) throws UtenteException, Exception;
	
	
	@DELETE
	@Path("/{idFideiussione}") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Deletes Fideiussione by idFideiussione", response=FideiussioneEsitoGenericoDTO.class)
	Response eliminaFideiussione( @Context HttpServletRequest req, @PathParam("idFideiussione") Long idFideiussione) throws UtenteException, Exception;
	
	

	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Updates Fideiussione", response=FideiussioneEsitoGenericoDTO.class)
	Response creaAggiornaFideiussione( @Context HttpServletRequest req, CreaAggiornaFideiussioneRequest moRequest) throws UtenteException, Exception;
	
	@POST
	@Path("/{idFideiussione}") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds dettaglio fideiussione", response=Fideiussione.class)
	Response dettaglioFideiussione( @Context HttpServletRequest req, @PathParam("idFideiussione") Long idFideiussione) throws UtenteException, Exception;
	
	
	
}
