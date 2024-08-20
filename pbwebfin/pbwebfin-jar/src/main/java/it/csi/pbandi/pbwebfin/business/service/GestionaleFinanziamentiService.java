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

import it.csi.pbandi.pbwebfin.dto.beneficiario.ProgettiBeneficiario;
import it.csi.pbandi.pbwebfin.dto.impegni.GestioneImpegniDTO;

@Path("/bilancio")
public interface GestionaleFinanziamentiService {
	
	@GET
	@Path("cercadirezione") //Popola il menù della dropdown del campo "Direzione provvedimento (3)" nella schermata di ricerca iniziale 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaDirezioneRicercaImpegni( @QueryParam("idU") Long idU,
			 							   @QueryParam("idSoggetto") Long idSoggetto,
			 							   @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("cercaannieserciziovalidi") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaAnniEsercizioValidi( @QueryParam("idU") Long idU,
			 						   @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("cercaimpegni") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaImpegni( @QueryParam("idU") Long idU,
						   @QueryParam("annoEsercizio") Long annoEsercizio,
						   @QueryParam("annoImpegno") Long annoImpegno,
						   @QueryParam("annoProvvedimento") Long annoProvvedimento,
						   @QueryParam("numeroProvvedimento") String numeroProvvedimento,
						   @QueryParam("direzioneProvvedimento") String direzioneProvvedimento,
						   @QueryParam("idDirezioneProvvedimento") Long idDirezioneProvvedimento,
						   @QueryParam("numeroCapitolo") Long numeroCapitolo,
						   @QueryParam("ragsoc") String ragsoc,
						   @QueryParam("numeroImpegno") Long numeroImpegno,
						   @QueryParam("idSoggetto") Long idSoggetto,
						   @QueryParam("isImpegniReimputati") Boolean isImpegniReimputati,
			 			   @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("cercadettaglioatto") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaDettaglioImpegno( @QueryParam("idU") Long idU,
									@QueryParam("idSoggetto") Long idSoggetto,
									@QueryParam("idImpegno") Long idImpegno,
			 						@Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("cercaDirezioneconimpegno") //Restituisce una lista di direzioni limitata
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaDirezioneConImpegno( @QueryParam("idU") Long idU,
			 						   @Context HttpServletRequest req) throws Exception;
	
	
	@POST
	@Path("cercalistabandolinea") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaBandolineaConDirezione( @QueryParam("idU") Long idU,
									      Long[] idImpegni,
									      @QueryParam("idEnteCompetenza") Long idEnteCompetenza,
			 						      @Context HttpServletRequest req) throws Exception;
	
	@POST
	@Path("cercalistaprogetti") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaListaProgetti( @QueryParam("idU") Long idU,
							     Long[] idImpegni,
	                             @QueryParam("progrBandolinea") Long progrBandolinea,
	 						     @Context HttpServletRequest req) throws Exception;
	
	@POST
	@Path("cercaprogettiDaassociare") 
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaProgettiDaAssociare( @QueryParam("idU") Long idU,
									   Long[] idImpegni,
			                           @QueryParam("progrBandolinea") Long progrBandolinea,//corrisponde all'id del bando linea selezionato
			                           @QueryParam("idProgetto") Long idProgetto,//id del progetto selezionato nell'ultima drop down
			 						   @Context HttpServletRequest req) throws Exception;
	
	@POST
	@Path("cercabandolineadaassociare") 
	@Produces(MediaType.APPLICATION_JSON)
	Response ceraBandolineaDaAssociare( @QueryParam("idU") Long idU,
									    Long[] idImpegni,
									    @QueryParam("idEnteCompetenza") Long idEnteCompetenza,//corrisponde all'id della Direzione selezionato nella drop down
									    @QueryParam("progrBandolinea") Long progrBandolinea,//corrisponde all'id del bando liena selezionato
			 						    @Context HttpServletRequest req) throws Exception;
	
	@POST
	@Path("associabandolineaimpegni") 
	@Produces(MediaType.APPLICATION_JSON)
	Response associaBandolineaImpegni( @QueryParam("idU") Long idU,
	                                   @QueryParam("idSoggetto") Long idSoggetto,
	                                   GestioneImpegniDTO gestioneImpegniDTO,
	 						           @Context HttpServletRequest req) throws Exception;
	
	@POST
	@Path("associaprogettiimpegni") 
	@Produces(MediaType.APPLICATION_JSON)
	Response associaProgettiImpegni( @QueryParam("idU") Long idU,
			                         GestioneImpegniDTO gestioneImpegniDTO,
	 						         @Context HttpServletRequest req) throws Exception;
	
	
	@POST
	@Path("cercabandolineaassociati") //Una volta selezionati gli impegni dalla ricerca si clicca sul pulsante "Gestisci Associazioni" per ottenere la lista dei bando linea associati
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaBandolineaAssociati( @QueryParam("idU") Long idU,
	                                   @QueryParam("idSoggetto") Long idSoggetto,
							           Long[] listaIdImpegno,
	 						           @Context HttpServletRequest req) throws Exception;
	
	
	@POST
	@Path("cercaprogettiassociati") //Una volta selezionati gli impegni dalla ricerca si clicca sul pulsante "Gestisci Associazioni" per ottenere la lista dei progetti associati
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaProgettiAssociati( @QueryParam("idU") Long idU,
	                                   @QueryParam("idSoggetto") Long idSoggetto,
							           Long[] listaIdImpegno,
							           @QueryParam("annoEsercizio") Long annoEsercizio,
	 						           @Context HttpServletRequest req) throws Exception;
	
	
//	@POST
//	@Path("disassociabandolinea") // Nel nuovo non esiste più
//	@Produces(MediaType.APPLICATION_JSON)
//	Response disassociaBandolinea( @QueryParam("idU") Long idU,
//							       @QueryParam("idImpegno") Long idImpegno,
//							       Long[] progrBandolineaSelezionati,
//	 						       @Context HttpServletRequest req) throws Exception;
//	
//	@POST
//	@Path("disassociaprogetti") // Nel nuovo non esiste più
//	@Produces(MediaType.APPLICATION_JSON)
//	Response disassociaProgetti( @QueryParam("idU") Long idU,
//							       @QueryParam("idImpegno") Long idImpegno,
//							       Long[] progettiSelezionati,
//	 						       @Context HttpServletRequest req) throws Exception;
	
	
	@POST
	@Path("cercaprogettiassociatiperbeneficiario") //Si scatena al click del pulsante "Gestisci associazione per beneficiario"
	@Produces(MediaType.APPLICATION_JSON)
	Response cercaProgettiAssociatiPerBeneficiario( @QueryParam("idU") Long idU,
											        @QueryParam("idSoggetto") Long idSoggetto,
											        Long[] listaIdImpegno,
											        @QueryParam("annoEsercizio") Long annoEsercizio,
					 						        @Context HttpServletRequest req) throws Exception;
	
	@POST
	@Path("disassociaprogettigestass") //Effettuare la ricerca->selezionare gli impegni->cliccare su "Gestisci impegni" -> selezionare i progetti -> cliccare su "eleimina associazione"
	@Produces(MediaType.APPLICATION_JSON)
	Response disassociaProgettiGestAss( @QueryParam("idU") Long idU,
								        ProgettiBeneficiario progettiBeneficiario,
		 						        @Context HttpServletRequest req) throws Exception;
	
	
	@POST
	@Path("disassociabandolineagestass") //Effettuare la ricerca->selezionare gli impegni->cliccare su "Gestisci impegni" -> selezionare i bado linea -> cliccare su "eleimina associazione"
	@Produces(MediaType.APPLICATION_JSON)
	Response disassociaBandolineaGestAss( @QueryParam("idU") Long idU,
			                              GestioneImpegniDTO impegni,
		 						          @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("acquisisciimpegno") 
	@Produces(MediaType.APPLICATION_JSON)
	Response acquisisciImpegno( @QueryParam("idU") Long idU,
			 					@QueryParam("annoEsercizio") Long annoEsercizio,
			 					@QueryParam("annoImpegno") Long annoImpegno,
			 					@QueryParam("numeroImpegno") Long numeroImpegno,
			 					@QueryParam("idSoggetto") Long idSoggetto,
		 						@Context HttpServletRequest req) throws Exception;

}
