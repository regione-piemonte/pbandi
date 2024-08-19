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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.EsitoSalvaTrasferimentoDTO;
import it.csi.pbandi.pbweberog.dto.trasferimenti.CausaleTrasferimento;
import it.csi.pbandi.pbweberog.dto.trasferimenti.FiltroTrasferimento;
import it.csi.pbandi.pbweberog.dto.trasferimenti.SoggettoTrasferimento;
import it.csi.pbandi.pbweberog.dto.trasferimenti.TrasferimentiItem;
import it.csi.pbandi.pbweberog.dto.trasferimenti.Trasferimento;


@Path("/trasferimenti")
@Api(value = "trasferimenti")
public interface TrasferimentiService {
	
	@GET
	@Path("/beneficiari") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Soggetti trasferimento (Carica combo beneficiari)", response=SoggettoTrasferimento.class, responseContainer = "List")
	Response getSoggettiTrasferimento( @Context HttpServletRequest req) throws UtenteException, Exception;
	
	@GET
	@Path("/causali") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Causali trasferimento (Carica combo causali)", response=CausaleTrasferimento.class, responseContainer = "List")
	Response getCausaliTrasferimento( @Context HttpServletRequest req) throws UtenteException, Exception;
	
	@GET
	@Path("/normativa") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Soggetti trasferimento (Carica combo beneficiari)", response=SoggettoTrasferimento.class, responseContainer = "List")
	Response getNormativa( @Context HttpServletRequest req) throws UtenteException, Exception;
	
	@POST
	@Path("/ricerca") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds elenco trasferimenti by FiltroTrasferimento", response=TrasferimentiItem.class, responseContainer = "List")
	Response ricercaTrasferimenti( @Context HttpServletRequest req, FiltroTrasferimento filtro) throws UtenteException, Exception;
	
	@PUT
	@Path("/trasferimento") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Updates trasferimento by idTrasferimento", response=EsitoSalvaTrasferimentoDTO.class)
	Response modificaTrasferimento( @Context HttpServletRequest req, Trasferimento trasferimento) throws UtenteException, Exception;
	
	@GET
	@Path("/dettaglio/{idTrasferimento}") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds dettaglio trasferimento by idTrasferimento", response=Trasferimento.class)
	Response getDettaglioTrasferimento( @Context HttpServletRequest req, @PathParam("idTrasferimento") Long idTrasferimento) throws UtenteException, Exception;
	
	@DELETE
	@Path("/{idTrasferimento}") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Deletes trasferimento by idTrasferimento", response=EsitoSalvaTrasferimentoDTO.class)
	Response cancellaTrasferimento( @Context HttpServletRequest req, @PathParam("idTrasferimento") Long idTrasferimento) throws UtenteException, Exception;
	
	@POST
	@Path("/trasferimento") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Creates trasferimento", response=EsitoSalvaTrasferimentoDTO.class)
	Response inserisciTrasferimento( @Context HttpServletRequest req, Trasferimento trasferimento) throws UtenteException, Exception;
	
}
