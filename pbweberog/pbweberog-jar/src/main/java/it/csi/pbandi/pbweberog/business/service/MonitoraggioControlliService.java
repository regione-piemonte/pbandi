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
import it.csi.pbandi.pbservizit.pbandisrv.dto.rilevazionicontrolli.EsitoCampionamentoDTO;
import it.csi.pbandi.pbweberog.dto.CodiceDescrizione;
import it.csi.pbandi.pbweberog.dto.EsitoGenerazioneReportDTO;
import it.csi.pbandi.pbweberog.dto.monitoraggiocontrolli.ElenchiProgettiCampionamento;
import it.csi.pbandi.pbweberog.dto.monitoraggiocontrolli.FiltroRilevazione;
import it.csi.pbandi.pbweberog.integration.request.RequestGetProgettiCampione;

@Path("/monitoraggioControlli")
@Api(value = "monitoraggioControlli")
public interface MonitoraggioControlliService {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	//////////////////////////////////////// ESTRAZIONE PROGETTI CAMPIONATI ///////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@GET
	@Path("/normative") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds normative", response=CodiceDescrizione.class, responseContainer = "List")
	Response getNormative( @Context HttpServletRequest req, @QueryParam("isConsultazione") Boolean isConsultazione) throws UtenteException, Exception;
/*										|||||
										|||||
									||||||||||||||
									|||        |||
									|||        |||
									   ||||||||	
									    |||||
									     |||						
									   				*/
    @GET
	@Path("/asse") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds asse by idProgetto and idLineaDiIntervento", response=CodiceDescrizione.class, responseContainer = "List")
	Response getAsse( @Context HttpServletRequest req, @QueryParam("idLineaDiIntervento") Long idLineaDiIntervento, @QueryParam("isConsultazione") Boolean isConsultazione) throws UtenteException, Exception;
	
    /*										|||||
											|||||
										||||||||||||||
										|||        |||
										|||        |||
										   ||||||||		
										   	|||||
									     	 |||					*/
    @GET
   	@Path("/bandi") 
   	@Produces(MediaType.APPLICATION_JSON)
   	@ApiOperation(value = "Finds bandi by idLineaDiIntervento and idAsse", response=CodiceDescrizione.class, responseContainer = "List")
   	Response getBandi( @Context HttpServletRequest req, @QueryParam("idLineaDiIntervento") Long idLineaDiIntervento,
   														@QueryParam("idAsse") Long idAsse, @QueryParam("isConsultazione") Boolean isConsultazione) throws UtenteException, Exception;
    
	@GET
	@Path("/periodi") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds periodi", response=CodiceDescrizione.class, responseContainer = "List")
	Response getAnnoContabili( @Context HttpServletRequest req, @QueryParam("isConsultazione") Boolean isConsultazione) throws UtenteException, Exception;
	
	@GET
	@Path("/autoritaControllo") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Autorita controllo by idProgetto", response=CodiceDescrizione.class, responseContainer = "List")
	Response getAutoritaControllo( @Context HttpServletRequest req, @QueryParam("isConsultazione") Boolean isConsultazione) throws UtenteException, Exception;
	
    @POST
	@Path("/reportCampionamento") 
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
   	@ApiOperation(value = "Generates campionamento report", response=EsitoGenerazioneReportDTO.class)
   	Response generaReportCampionamento( @Context HttpServletRequest req, FiltroRilevazione filtroRilevazione) throws UtenteException, Exception;
    
    

    @POST
	@Path("/progettiCampione") 
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
   	@ApiOperation(value = "Finds elenchi progetti campionamento", response=ElenchiProgettiCampionamento.class)
   	Response getProgettiCampione( @Context HttpServletRequest req, RequestGetProgettiCampione getProgettiCampioneRequest) throws UtenteException, Exception;
    
    
    @POST
   	@Path("/campionamento") 
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
  	@ApiOperation(value = "Salva campionamento progetti", response=EsitoCampionamentoDTO.class)
  	Response registraCampionamentoProgetti( @Context HttpServletRequest req, RequestGetProgettiCampione getProgettiCampioneRequest) throws UtenteException, Exception;
    
    

    
}
