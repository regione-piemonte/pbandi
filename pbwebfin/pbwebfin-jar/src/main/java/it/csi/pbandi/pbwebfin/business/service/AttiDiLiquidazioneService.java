/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebfin.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/bilancio/attidiliquidazione")
public interface AttiDiLiquidazioneService {
	
	@GET
	@Path("caricaBeneficiariByDenomOrIdBen") 
	@Produces(MediaType.APPLICATION_JSON)
	Response caricaBeneficiariByDenomOrIdBen( @QueryParam("denominazione") String denominazione,
			@QueryParam("idBeneficiario") Long idBeneficiario, @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("caricaProgettiByIdBen") 
	@Produces(MediaType.APPLICATION_JSON)
	Response caricaProgettiByIdBen( @QueryParam("idBeneficiario") Long idBeneficiario,
			 				 @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("cercadatiatti") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaDatiAttiDiLiquidazione( @QueryParam("idU") Long idU,
										  @QueryParam("idBeneficiario") Long idBeneficiario,
										  @QueryParam("idProgetto") Long idProgetto,
										  @QueryParam("annoEsercizio") String annoEsercizio,
										  @QueryParam("annoImpegno") String annoImpegno,
										  @QueryParam("numeroImpegno") String numeroImpegno,
										  @QueryParam("annoAtto") String annoAtto,
										  @QueryParam("numeroAtto") String numeroAtto,
				 				          @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("cercadettaglioatto") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaDettaglioAttoDiLiquidazione( @QueryParam("idU") Long idU,
											   @QueryParam("idAttoDiLiquidazione") Long idAttoDiLiquidazione,
			 				                   @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("cercariepilogoatti") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaRiepilogoAttiLiquidazione( @QueryParam("idU") Long idU,
											 @QueryParam("idProgetto") Long idProgetto,
			 				                 @Context HttpServletRequest req) throws Exception;
	

}
