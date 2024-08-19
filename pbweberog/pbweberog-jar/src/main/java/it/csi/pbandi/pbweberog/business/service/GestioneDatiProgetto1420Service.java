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
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.datiprogetto.DatiProgetto;
import it.csi.pbandi.pbweberog.dto.datiprogetto.DettaglioSoggettoProgettoDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.DocumentoAllegato;
import it.csi.pbandi.pbweberog.dto.datiprogetto.FileDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.SedeProgettoDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.SoggettoProgettoDTO;
import it.csi.pbandi.pbweberog.dto.erogazione.EsitoErogazioneDTO;
import it.csi.pbandi.pbweberog.integration.request.InserisciSedeInterventoRequest;
import it.csi.pbandi.pbweberog.integration.request.ModificaSedeInterventoRequest;

@Path("/datiProgetto1420")
@Api(value = "datiProgetto1420")
public interface GestioneDatiProgetto1420Service {
//	@GET
//	@Path("/") 
//	@Produces(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "Finds all dati progetto by idProgetto", response=DatiProgetto.class)
//	Response getDatiProgetto( @Context HttpServletRequest req, @PathParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
//	
//	////////////////////////////////////////// ALLEGATI ////////////////////////////////////////////////
//	///////////////////////////////////////////////////////////////////////////////////////////////
//	
//	@GET
//	@Path("allegati") 
//	@Produces(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "Finds files associated to project by idProgetto", response=FileDTO.class, responseContainer = "List")
//	Response getFilesAssociatedProgetto( @Context HttpServletRequest req, @PathParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
//
//	////////////////////////////////////////// SEDI ////////////////////////////////////////////////
//	///////////////////////////////////////////////////////////////////////////////////////////////
//	@GET
//	@Path("sedi") 
//	@Produces(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "Finds all sedi projetto by idProgetto", response=SedeProgettoDTO.class, responseContainer = "List")
//	Response getAllSediProgetto( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
//
//	@GET
//	@Path("sedi/{idSede}/{idProgetto}/dettaglio") 
//	@Produces(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "Finds dettaglio sede by idProgetto and idSede", response=SedeProgettoDTO.class)
//	Response getDettaglioSedeProgetto( @Context HttpServletRequest req, @PathParam("idProgetto") Long idProgetto, 
//			@PathParam("idSede") Long idSede) throws UtenteException, Exception;
//
//	@PUT
//	@Path("sede") 
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "Updates sede progetto by idSede", response=EsitoOperazioni.class)
//	Response modificaSedeIntervento( @Context HttpServletRequest req, ModificaSedeInterventoRequest moRequest) throws UtenteException, Exception;
//
//
//	@POST
//	@Path("sede") 
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "Inserts new sede progetto", response=EsitoOperazioni.class)
//	Response inserisciSedeInterventoProgetto( @Context HttpServletRequest req, InserisciSedeInterventoRequest inRequest) throws UtenteException, Exception;
//
//	@DELETE
//	@Path("sedi/{progrSoggettoProgettoSede}") 
//	@Produces(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "Deletes sede by progrSoggettoProgettoSede", response=EsitoOperazioni.class)
//	Response cancellaSedeInterventoProgetto( @Context HttpServletRequest req, @PathParam("progrSoggettoProgettoSede") Long progrSoggettoProgettoSede) throws UtenteException, Exception;
//
//	
//	
//	////////////////////////////////////////// SOGGETTI ////////////////////////////////////////////////
//	///////////////////////////////////////////////////////////////////////////////////////////////
//	
//	@GET
//	@Path("soggetti") 
//	@Produces(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "Finds all soggetti projetto by idProgetto", response=SoggettoProgettoDTO.class, responseContainer = "List")
//	Response getSoggettiProgetto( @Context HttpServletRequest req, @PathParam("idProgetto") Long idProgetto) throws UtenteException, Exception;
//
//	@GET
//	@Path("soggetti/dettaglio") 
//	@Produces(MediaType.APPLICATION_JSON)
//	@ApiOperation(
//			value = "Finds dettaglio soggetto by progrSoggettoProgetto, idTipoSoggettoCorrelato, progrSoggettiCorrelati", 
//			response=DettaglioSoggettoProgettoDTO.class
//			)
//	Response getDettaglioSoggettoProgetto( @Context HttpServletRequest req, 
//			@QueryParam("progrSoggettoProgetto") Long progrSoggettoProgetto, 
//			@QueryParam("idTipoSoggettoCorrelato") Long idTipoSoggettoCorrelato,
//			@QueryParam("progrSoggettiCorrelati") Long progrSoggettiCorrelati) throws UtenteException, Exception;

	
}
