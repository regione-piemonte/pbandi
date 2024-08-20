/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebfin.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.DatiIntegrativiDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.DettaglioBeneficiarioBilancioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.LiquidazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.RipartizioneImpegniLiquidazioneDTO;
import it.csi.pbandi.pbwebfin.dto.beneficiario.ImpegniLiquidazioneDTO;

@Path("/liquidazione")
public interface LiquidazioneContributiService {
	
	@GET
	@Path("caricadatigenerali") 
	@Produces(MediaType.APPLICATION_JSON)
	Response caricaDatiGenerali( @QueryParam("idU") Long idU,
								 @QueryParam("idSoggettoBeneficiario") Long idSoggettoBeneficiario,
								 @QueryParam("idBandoLinea") Long idBandoLinea,  //id del bando linea selezionato dopo la scelta del beneficiario
								 @QueryParam("idProgetto") Long idProgetto,    //id del progetto selezionato dopo aver scelto il bando lina    
			 				     @Context HttpServletRequest req) throws Exception;
	
	
	@POST
	@Path("riepilogofondi") 
	@Produces(MediaType.APPLICATION_JSON)
	Response avantiTabLiquidazione( @QueryParam("idU") Long idU,
					                 LiquidazioneDTO liquidazione,
									 @QueryParam("idSoggetto") Long idSoggetto,
									 @QueryParam("idSoggettoBeneficiario") Long idSoggettoBeneficiario,
									 @QueryParam("idBandoLinea") Long idBandoLinea,
					 				 @Context HttpServletRequest req) throws Exception;
	
	
	@POST
	@Path("beneficiario") 
	@Produces(MediaType.APPLICATION_JSON)
	Response avantiTabRiepilogoFondi( @QueryParam("idU") Long idU,
			                          ImpegniLiquidazioneDTO impegniLiquidazioneDTO,
									  @QueryParam("idSoggetto") Long idSoggetto,
									  @QueryParam("idSoggettoBeneficiario") Long idSoggettoBeneficiario,
									  @QueryParam("idBandoLinea") Long idBandoLinea,
					 				  @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("apridettagliobeneficiario") 
	@Produces(MediaType.APPLICATION_JSON)
	Response apriDettaglioBeneficiario( @QueryParam("idU") Long idU,
										@QueryParam("idProvincia") Long idProvincia,  // Corrisponde all'elemento "idProvinciaSede" dell'oggetto "beneficiarioBilancioDTO" presente nel json
										@QueryParam("iban") String iban,
			 				            @Context HttpServletRequest req) throws Exception;
	
	@POST
	@Path("aggiornabeneficiario") 
	@Produces(MediaType.APPLICATION_JSON)
	Response aggiornaBeneficiario( @QueryParam("idU") Long idU,
								   @QueryParam("idProgetto") Long idProgetto,
								   @QueryParam("idAttoLiquidazione") Long idAttoLiquidazione,
								   DettaglioBeneficiarioBilancioDTO dettaglioBeneficiarioBilancioDTO,
			 			           @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("datiintegrativi") 
	@Produces(MediaType.APPLICATION_JSON)
	Response avantiTabBeneficiario( @QueryParam("idU") Long idU,
									@QueryParam("idAttoLiquidazione") Long idAttoLiquidazione,
									@QueryParam("idSoggetto") Long idSoggetto,
					 			    @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("cercasettoreappartenenza") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaSettoreAppartenenza( @QueryParam("idU") Long idU,
									   @QueryParam("idSoggetto") Long idSoggetto,
									   @QueryParam("idEnte") Long idEnte,
									   @Context HttpServletRequest req) throws Exception;
	
	@POST
	@Path("ritenute") 
	@Produces(MediaType.APPLICATION_JSON)
	Response avantiTabDatiIntegrativi( @QueryParam("idU") Long idU,
							           @QueryParam("idProgetto") Long idProgetto,
								       DatiIntegrativiDTO datiIntegrativiDTO,
							 		   @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("tabcreaatto") 
	@Produces(MediaType.APPLICATION_JSON)
	Response avantiTabRitenute( @QueryParam("idU") Long idU,
		 						@QueryParam("idAttoLiquidazione") Long idAttoLiquidazione,
		 						@QueryParam("imponibile") Double imponibile,
		 						@QueryParam("sommeNonImponibili") Double sommeNonImponibili,
		 						@QueryParam("idAliquotaRitenuta") Long idAliquotaRitenuta,
		 			            @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("creaatto") 
	@Produces(MediaType.APPLICATION_JSON)
	Response creaAtto( @QueryParam("idU") Long idU,
					   @QueryParam("idAttoLiquidazione") Long idAttoLiquidazione,
					   @QueryParam("idProgetto") Long idProgetto,
		               @Context HttpServletRequest req) throws Exception;
	

}
