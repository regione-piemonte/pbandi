/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
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
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneappalti.AppaltoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneappalti.EsitoGestioneAppalti;
import it.csi.pbandi.pbwebrce.dto.CodiceDescrizione;
import it.csi.pbandi.pbwebrce.dto.EsitoProceduraAggiudicazioneDTO;
import it.csi.pbandi.pbwebrce.dto.ModificaProcedureAggiudicazioneRequest;
import it.csi.pbandi.pbwebrce.dto.ProceduraAggiudicazione;
import it.csi.pbandi.pbwebrce.dto.StepAggiudicazione;
import it.csi.pbandi.pbwebrce.dto.affidamenti.Appalto;
import it.csi.pbandi.pbwebrce.integration.request.CreaAppaltoRequest;
import it.csi.pbandi.pbwebrce.integration.request.CreaProcAggRequest;
import it.csi.pbandi.pbwebrce.integration.request.GetProcedureAggiudicazioneRequest;

@Path("/applati")
@Api(value = "appalti")
public interface AppaltiService {
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds appalti by idProgetto e altri filtri di ricerca", response = Appalto.class, responseContainer = "List")
	Response getAppalti( @Context HttpServletRequest req, Appalto appalto ,  @QueryParam("idProgetto") Long idProgetto) 
			throws UtenteException, Exception;
	
	@GET
	@Path("/progetto") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Codice Progetto by idProgetto", response = String.class)
	Response getCodiceProgetto( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto ) throws UtenteException, Exception;
	
	
	@GET
	@Path("/tipologieAppalti") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Tipologie Appalti", response = CodiceDescrizione.class, responseContainer = "List")
	Response getTipologieAppalti( @Context HttpServletRequest req) 
			throws UtenteException, Exception;
	
	@GET
	@Path("/tipologieProcedureAggiudicazione") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Tipologie Procedure Aggiudicazione by idProgetto", response = CodiceDescrizione.class, responseContainer = "List")
	Response getTipologieProcedureAggiudicazione( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) 
			throws UtenteException, Exception;
	
	@POST
	@Path("/procedureAggiudicazione") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds All Procedure Aggiudicazione by idProgetto", response = ProceduraAggiudicazione.class, responseContainer = "List")
	Response getAllProcedureAggiudicazione( @Context HttpServletRequest req, GetProcedureAggiudicazioneRequest getProcAggiudicazRequest) 
			throws UtenteException, Exception;
	
	@GET
	@Path("/procedureAggiudicazione/{idProcedura}") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds a Procedura Aggiudicazione by idProcedura", response = ProceduraAggiudicazione.class)
	Response getDettaglioProceduraAggiudicazione( @Context HttpServletRequest req, @PathParam("idProcedura") Long idProcedura) 
			throws UtenteException, Exception;
	
	@PUT
	@Path("/proceduraAggiudicazione") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Updates a Procedura Aggiudicazione by idProgetto", response = EsitoProceduraAggiudicazioneDTO.class)
	Response modificaProceduraAggiudicazione( @Context HttpServletRequest req, ModificaProcedureAggiudicazioneRequest modificaProcAggRequest) 
			throws UtenteException, Exception;
	
	@POST
	@Path("/proceduraAggiudicazione") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Crea Procedura Aggiudicazione", response = EsitoProceduraAggiudicazioneDTO.class)
	Response creaProceduraAggiudicazione( @Context HttpServletRequest req, CreaProcAggRequest creaRequest) 
			throws UtenteException, Exception;
	
	@POST
	@Path("/appalto") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Crea appalto", response = EsitoGestioneAppalti.class)
	Response creaAppalto( @Context HttpServletRequest req, CreaAppaltoRequest creaRequest) 
			throws UtenteException, Exception;
	
	@GET
	@Path("/stepsAggiudicazione/{idTipologiaAggiudicaz}") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds List of Step Aggiudicazione by IdTipologiaAggiudicaz", response = StepAggiudicazione.class, responseContainer = "List")
	Response getStepsAggiudicazione( @Context HttpServletRequest req, @PathParam("idTipologiaAggiudicaz") Long idTipologiaAggiudicaz) 
			throws UtenteException, Exception;
	
	@GET
	@Path("/eliminaAppalto") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Elimina un appalto", response = EsitoGestioneAppalti.class)
	Response eliminaAppalto( @Context HttpServletRequest req, @QueryParam("idAppalto") Long idAppalto) 
			throws UtenteException, Exception;
	
	@GET
	@Path("/eliminazioneAppaltoAbilitata") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Verifica abilitazione eliminazione appalto", response = EsitoGestioneAppalti.class)
	Response eliminazioneAppaltoAbilitata( @QueryParam("codiceRuolo") String codiceRuolo, @Context HttpServletRequest req) 
			throws UtenteException, Exception;
	
}
