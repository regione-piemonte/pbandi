/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api;


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
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.integration.request.SalvaFasiMonitoraggioGestioneRequest;
import it.csi.pbandi.pbservizit.integration.request.ValidazioneDatiCronoProgrammaRequest;

@Path("/cronoprogramma")
public interface CronoProgrammaApi {

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
	Response getCodiceProgetto( @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto ) throws UtenteException, Exception;
	
	@GET
	@Path("programmazione") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getProgrammazioneByIdProgetto(  @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws Exception;
	
	@GET
	@Path("tipoOperazione") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getTipoOperazione(  @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto, 
																 @QueryParam("programmazione") String programmazione) throws Exception;
	
	@GET
	@Path("fasiMonitoraggioGestione") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getFasiMonitoraggioGestione(  @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto, 
																 @QueryParam("programmazione") String programmazione) throws Exception;
	
	@GET
	@Path("fasiMonitoraggioAvvio") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getFasiMonitoraggioAvvio(  @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto, 
																 @QueryParam("programmazione") String programmazione,
																 @QueryParam("idIter") Long idIter) throws Exception;
	
	@GET
	@Path("dataPresentazioneDomanda") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getDataPresentazioneDomanda(  @Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws Exception;
	
	@GET
	@Path("dataConcessione") 
	@Produces(MediaType.APPLICATION_JSON)
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
	Response getMotivoScostamento(  @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("/iter") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getIter(  @Context HttpServletRequest req,   @QueryParam("idTipoOperazione") Long  idTipoOperazione, @QueryParam("programmazione") String  programmazione ) throws Exception;
	
	/*
	@POST
	@Path("/cronoprogrammaGestione/validazioneDati") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response controllaDatiPerSalvataggioGestione(  @Context HttpServletRequest req, ValidazioneDatiCronoProgrammaRequest validazioneDatiRequest) throws Exception;
	*/
	
	@POST
	@Path("/cronoprogrammaAvvio/validazioneDati") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response controllaDatiPerSalvataggioAvvio(  @Context HttpServletRequest req, ValidazioneDatiCronoProgrammaRequest validazioneDatiRequest) throws Exception;
	
	@POST
	@Path("/fasiMonitoraggioGestione") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaFasiMonitoraggioGestione(  @Context HttpServletRequest req, SalvaFasiMonitoraggioGestioneRequest salvaFasiMonitoraggioGestione) throws Exception;
	
	@POST
	@Path("/fasiMonitoraggioAvvio") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaFasiMonitoraggioAvvio(  @Context HttpServletRequest req, SalvaFasiMonitoraggioGestioneRequest salvaFasiMonitoraggioGestione) throws Exception;
	
}
