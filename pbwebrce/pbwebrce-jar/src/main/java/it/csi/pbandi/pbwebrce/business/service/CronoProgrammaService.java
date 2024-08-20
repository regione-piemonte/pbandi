/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.business.service;

import java.util.Date;
import java.util.List;

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
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma.EsitoSaveFasiMonitoraggio;
import it.csi.pbandi.pbwebrce.dto.ExecResults;
import it.csi.pbandi.pbwebrce.dto.cronoprogramma.CronoprogrammaItem;
import it.csi.pbandi.pbwebrce.dto.cronoprogramma.Iter;
import it.csi.pbandi.pbwebrce.dto.cronoprogramma.MotivoScostamento;
import it.csi.pbandi.pbwebrce.dto.cronoprogramma.ResponseGetFasiMonit;
import it.csi.pbandi.pbwebrce.integration.request.SalvaFasiMonitoraggioGestioneRequest;
import it.csi.pbandi.pbwebrce.integration.request.ValidazioneDatiCronoProgrammaRequest;

@Path("/cronoprogramma")
@Api(value = "cronoprogramma")
public interface CronoProgrammaService {
	
//	@GET
//	@Path("home") 
//	@Produces(MediaType.APPLICATION_JSON)
//	Response getCronoProgrammaHome(@DefaultValue("0") @QueryParam("idPrj") Long idPrj,
//	        @DefaultValue("0") @QueryParam("idSg") Long idSg,
//	        @DefaultValue("0") @QueryParam("idSgBen") Long idSgBen,
//	        @DefaultValue("0") @QueryParam("idU") Long idU,
//	        @DefaultValue("--") @QueryParam("role") String role,
//	        @DefaultValue("--") @QueryParam("taskIdentity") String taskIdentity,
//	        @DefaultValue("--") @QueryParam("taskName") String taskName,
//	        @DefaultValue("--") @QueryParam("wkfAction") String wkfAction, 
//	        @Context HttpServletRequest req) throws UtenteException, ErroreGestitoException;
	
	
	@GET
	@Path("/progetto") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Codice Progetto by idProgetto", response = String.class)
	Response getCodiceProgetto( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto ) throws UtenteException, Exception;
	
	@GET
	@Path("programmazione") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds programmazione by idProgetto", response = String.class)
	Response getProgrammazioneByIdProgetto(  @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws Exception;
	
	@GET
	@Path("tipoOperazione") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds tipo operazione by idProgetto and programmazione", response = String.class)
	Response getTipoOperazione(  @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto, 
																 @QueryParam("programmazione") String programmazione) throws Exception;
	
	@GET
	@Path("fasiMonitoraggioGestione") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds list of cronoprogramma monitoraggio gestione by idProgetto and programmazione", response = ResponseGetFasiMonit.class)
	Response getFasiMonitoraggioGestione(  @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto, 
																 @QueryParam("programmazione") String programmazione) throws Exception;
	
	@GET
	@Path("fasiMonitoraggioAvvio") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds list of cronoprogramma monitoraggio avvio by idProgetto and programmazione", response = CronoprogrammaItem.class, responseContainer = "List")
	Response getFasiMonitoraggioAvvio(  @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto, 
																 @QueryParam("programmazione") String programmazione,
																 @QueryParam("idIter") Long idIter) throws Exception;
	
	@GET
	@Path("dataPresentazioneDomanda") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds data presentazione domanda of the project by idProgetto", response = Date.class)
	Response getDataPresentazioneDomanda(  @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws Exception;
	
	@GET
	@Path("dataConcessione") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds data presentazione concessione of the project by idProgetto", response = Date.class)
	Response getDataConcessione(  @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws Exception;
	
	
//	@GET
//	@Path("fasiMonitoraggio") 
//	@Produces(MediaType.APPLICATION_JSON)
//	Response getFasiMonitoraggioGestione(  @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto, 
//																 @QueryParam("programmazione") String programmazione) throws Exception;
//	
	
	@GET
	@Path("/motivoScostamento") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds all motivo scostamento", response = MotivoScostamento.class, responseContainer = "List")
	Response getMotivoScostamento(  @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("/iter") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds iter by programmazione and idTipoOperazione", response = Iter.class, responseContainer = "List")
	Response getIter(  @Context HttpServletRequest req,   @QueryParam("idTipoOperazione") Long  idTipoOperazione, @QueryParam("programmazione") String  programmazione ) throws Exception;
	
	@POST
	@Path("/cronoprogrammaGestione/validazioneDati") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Checks if cronoprogramma gestione data is valid for saving", response = ExecResults.class)
	Response controllaDatiPerSalvataggioGestione(  @Context HttpServletRequest req, ValidazioneDatiCronoProgrammaRequest validazioneDatiRequest) throws Exception;
	
	@POST
	@Path("/cronoprogrammaAvvio/validazioneDati") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Checks if cronoprogramma avvio data is valid for saving", response = ExecResults.class)
	Response controllaDatiPerSalvataggioAvvio(  @Context HttpServletRequest req, ValidazioneDatiCronoProgrammaRequest validazioneDatiRequest) throws Exception;
	
	@POST
	@Path("/fasiMonitoraggioGestione") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Saves validated cronoprogramma data to database", response = EsitoSaveFasiMonitoraggio.class)
	Response salvaFasiMonitoraggioGestione(  @Context HttpServletRequest req, SalvaFasiMonitoraggioGestioneRequest salvaFasiMonitoraggioGestione) throws Exception;
	
	@POST
	@Path("/fasiMonitoraggioAvvio") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Saves validated cronoprogramma data to database", response = EsitoSaveFasiMonitoraggio.class)
	Response salvaFasiMonitoraggioAvvio(  @Context HttpServletRequest req, SalvaFasiMonitoraggioGestioneRequest salvaFasiMonitoraggioGestione) throws Exception;
	
	
}
