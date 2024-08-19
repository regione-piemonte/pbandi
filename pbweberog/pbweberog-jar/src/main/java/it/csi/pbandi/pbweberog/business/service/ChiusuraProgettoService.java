/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business.service;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.dto.fineprogetto.EsitoOperazioneChiudiProgetto;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.integration.request.RequestChiudiProgetto;

@Path("/chiusuraProgetto")
@Api(value = "chiusuraProgetto")
public interface ChiusuraProgettoService {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	///////////////////////////////////////////// CHIUSURA DEL PROGETTO ///////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@GET
	@Path("/rinuncia") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Verifica se presente una rinuncia per il progetto.", response=Boolean.class)
	Response isRinunciaPresente( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
	
	@GET
	@Path("/erogazioneSaldo") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Verifica  se esiste il progetto associato/collegato/a contributo.", response=EsitoOperazioni.class)
	Response verificaErogazioneSaldo( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
	
	
	@POST
	@Path("/progetto") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Chiude la gestione operativa del progetto.", response=EsitoOperazioneChiudiProgetto.class)
	Response chiudiProgetto( @Context HttpServletRequest req, RequestChiudiProgetto chiudiRequest) throws UtenteException, Exception;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	///////////////////////////////////////// CHIUSURA D'UFFICIO DEL PROGETTO /////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@POST
	@Path("/ufficio") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Chiude progetto di ufficio.", response=EsitoOperazioneChiudiProgetto.class)
	Response chiudiProgettoDiUfficio( @Context HttpServletRequest req, RequestChiudiProgetto chiudiRequest) throws UtenteException, Exception; 
	
}
