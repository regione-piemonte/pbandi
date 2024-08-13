/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.business.service;

import java.util.ArrayList;
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

import org.springframework.web.bind.annotation.RequestBody;

import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.CostantiBandoLineaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.EnteDiCompetenzaRuoloAssociatoDTO;
import it.csi.pbandi.pbwebbo.dto.configurazionebandolinea.CheclistDTO;
import it.csi.pbandi.pbwebbo.dto.configurazionebandolinea.ModelloDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.EstremiContoDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.InsertEstremiBancariDTO;
import it.csi.pbandi.pbwebbo.dto.monitoraggioTempi.ParametriMonitoraggioTempiAssociatiDTO;
import jakarta.ws.rs.PUT;

@Path("/configurazonebando/configurazonebandolinea")
public interface ConfigurazioneBandoLineaService {
	
	@GET
	@Path("entedicompetenza/ente") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findEnti( @QueryParam("idU") Long idU,
				       @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("entedicompetenza/settore") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findSettore(@QueryParam("idU") Long idU,
					     @QueryParam("idEnte") Long idEnte,
				         @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("entedicompetenza/ruoli") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findRuoli(@QueryParam("idU") Long idU,
					   @QueryParam("idLineaDiIntervento") Long idLineaDiIntervento,
					   @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
				       @Context HttpServletRequest req) throws Exception;
	

	@GET
	@Path("entedicompetenza/entidicompetenzaassociati") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findEntiDiCompetenzaAssociati(@QueryParam("idU") Long idU,
					  					   @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
					  					   @Context HttpServletRequest req) throws Exception;
	
	
	
	@GET
	@Path("entedicompetenza/eliminaentedicompetenzaassociato") 
	@Produces(MediaType.APPLICATION_JSON)
	Response eliminaEnteDiCompetenzaAssociato(@QueryParam("idU") Long idU,
										  	  @QueryParam("progrBandoLineaEnteComp") Long progrBandoLineaEnteComp,
									      	  @Context HttpServletRequest req) throws Exception;

	
	@GET
	@Path("entedicompetenza/associaentedicompetenza")
	@Produces(MediaType.APPLICATION_JSON)
	Response associaEnteDiCompetenza(@QueryParam("idU") Long idU,
								  	 @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
								  	 @QueryParam("idEnte") Long idEnte,
								  	 @QueryParam("idRuoloEnte") Long idRuoloEnte,
								  	 @QueryParam("idSettoreEnte") Long idSettoreEnte,
								  	 @QueryParam("pec") String pec,
								  	 @QueryParam("mail") String mail,
								  	 @QueryParam("tipoProgrammazione") String tipoProgrammazione, //da passare a null se il processo è ti tipo uno altrimneti passare "2016"
								  	 @QueryParam("conservazioneCorrente") Long conservazioneCorrente,
								  	 @QueryParam("conservazioneGenerale") Long conservazioneGenerale,
								  	 @QueryParam("flagMonitoraggioTempi") String flagMonitoraggioTempi,
							      	 @Context HttpServletRequest req) throws Exception;
	
	
	@POST
	@Path("entedicompetenza/isruoliobbligatoriassociati") 
	@Produces(MediaType.APPLICATION_JSON)
	Response isRuoliEnteObbligatoriAssociati(@QueryParam("idBandoLinea") Long idBandoLinea,
										  	 ArrayList<EnteDiCompetenzaRuoloAssociatoDTO> ruoliAssociati,
									      	 @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("entedicompetenza/verificaparolachiaveacta")
	@Produces(MediaType.APPLICATION_JSON)
	Response verificaParolaChiaveActa(@QueryParam("idU") Long idU,
									 @QueryParam("progrBandoLineaEnteCompetenza") Long progrBandoLineaEnteCompetenza,
								  	 @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("datiaggiuntivi/tipidiaiutodaassociare") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findTipiDiAiutoDaAssociare(@QueryParam("idU") Long idU,
		  					    @QueryParam("idLineaDiIntervento") Long idLineaDiIntervento,
		  					    @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("datiaggiuntivi/associatipidiaiuto") 
	@Produces(MediaType.APPLICATION_JSON)
	Response associaTipiDiAiuto(@QueryParam("idU") Long idU,
		  					    @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
		  					    @QueryParam("idTipiDiAiuto") String idTipiDiAiuto,
		  					    @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("datiaggiuntivi/eliminatipidiaiuto") 
	@Produces(MediaType.APPLICATION_JSON)
	Response eliminaTipiDiAiuto(@QueryParam("idU") Long idU,
		  					    @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
		  					    @QueryParam("idTipiDiAiuto") String idTipiDiAiuto,
		  					    @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("datiaggiuntivi/findtipidiaiutoassociati") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findTipiDiAiutoAssociati(@QueryParam("idU") Long idU,
			  					      @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
			  					      @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("datiaggiuntivi/temaprioritario") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findTemaPrioritario(@QueryParam("idU") Long idU,
					   			 @QueryParam("idLineaDiIntervento") Long idLineaDiIntervento,
					   			 @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("datiaggiuntivi/temaprioritarioassociato") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findTemaPrioritarioAssociato(@QueryParam("idU") Long idU,
					   			 @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
					   			 @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("datiaggiuntivi/associatemaprioritario") 
	@Produces(MediaType.APPLICATION_JSON)
	Response associaTemaPrioritario(@QueryParam("idU") Long idU,
								   	@QueryParam("idTemaPrioritario") Long idTemaPrioritario,
								   	@QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
								   	@Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("datiaggiuntivi/eliminatemaprioritarioassociato") 
	@Produces(MediaType.APPLICATION_JSON)
	Response eliminaTemaPrioritarioAssociato(@QueryParam("idU") Long idU,
										   	@QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
										   	@QueryParam("idTemaPrioritario") Long idTemaPrioritario,
										   	@Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("datiaggiuntivi/indicatoriqsn") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findIndicatoriQSN(@QueryParam("idU") Long idU,
							   @QueryParam("idLineaDiIntervento") Long idLineaDiIntervento,
						       @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("datiaggiuntivi/indicatoriqsnassociati") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findIndicatoriQSNAssociati(@QueryParam("idU") Long idU,
							   @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
						       @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("datiaggiuntivi/associaindicatoreqsn") 
	@Produces(MediaType.APPLICATION_JSON)
	Response associaIndicatoreQSN(@QueryParam("idU") Long idU,
								   @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
								   @QueryParam("idIndicatoreQSN") Long idIndicatoreQSN,
							       @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("datiaggiuntivi/eliminaindicatoreqsnassociato") 
	@Produces(MediaType.APPLICATION_JSON)
	Response eliminaIndicatoreQSNAssociato(@QueryParam("idU") Long idU,
								   @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
								   @QueryParam("idIndicatoreQSN") Long idIndicatoreQSN,
							       @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("datiaggiuntivi/indicatoririsultatoprogramma") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findIndicatoriRisultatoProgramma(@QueryParam("idU") Long idU,
							   @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
						       @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("datiaggiuntivi/associaindicatorerisultatoprogramma") 
	@Produces(MediaType.APPLICATION_JSON)
	Response associaIndicatoreRisultatoProgramma(@QueryParam("idU") Long idU,
								   @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
								   @QueryParam("idIndicatoreRisultatoProgramma") Long idIndicatoreRisultatoProgramma,
							       @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("datiaggiuntivi/indicatoririsultatoprogrammaassociati") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findIndicatoriRisultatoProgrammaAssociati(@QueryParam("idU") Long idU,
							   @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
						       @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("datiaggiuntivi/eliminaindicatorerisultatoprogramma") 
	@Produces(MediaType.APPLICATION_JSON)
	Response eliminaIndicatoreRisultatoProgramma(@QueryParam("idU") Long idU,
								   @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
								   @QueryParam("idIndicatoreRisultatoProgramma") Long idIndicatoreRisultatoProgramma,
							       @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("datiaggiuntivi/tipoperiodo") 
	@Produces(MediaType.APPLICATION_JSON)
	Response tipoPeriodo(@QueryParam("idU") Long idU,
							       @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("datiaggiuntivi/tipoperiodoassociato") 
	@Produces(MediaType.APPLICATION_JSON)
	Response tipoPeriodoAssociato(@QueryParam("idU") Long idU,
							    @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
						        @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("datiaggiuntivi/associatipoperiodo") 
	@Produces(MediaType.APPLICATION_JSON)
	Response associaTipoPeriodo(@QueryParam("idU") Long idU,
							    @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
							    @QueryParam("idTipoPeriodo") Long idTipoPeriodo,
							    @QueryParam("periodoProgrammazione") String periodoProgrammazione,  //PRE-2016 per i porocessi di tipo 1 Per tutti gli altri 2016
						        @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("datiaggiuntivi/eliminatipoperiodo") 
	@Produces(MediaType.APPLICATION_JSON)
	Response eliminaTipoPeriodo(@QueryParam("idU") Long idU,
							    @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
							    @QueryParam("idTipoPeriodo") Long idTipoPeriodo,
							    @QueryParam("periodoProgrammazione") String periodoProgrammazione,  //PRE-2016 per i porocessi di tipo 1 Per tutti gli altri 2016
						        @Context HttpServletRequest req) throws Exception;
	
	
	
	@GET
	@Path("areascientifica/areascientifica") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findAreaScientifica(@QueryParam("idU") Long idU,
						        @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("areascientifica/areescientificheassociate") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findAreeScientificaAssociate(@QueryParam("idU") Long idU,
			 							  @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
			 							  @Context HttpServletRequest req) throws Exception;
	
	
	
	@GET
	@Path("areascientifica/associaareascientifica") 
	@Produces(MediaType.APPLICATION_JSON)
	Response associaAreaScientifica(@QueryParam("idU") Long idU,
	 							    @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
	 							    @QueryParam("idAreaScientifica") Long idAreaScientifica,
	 							    @QueryParam("descrizione") String descrizione,
	 							    @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("areascientifica/eliminaareascientificaassociata") 
	@Produces(MediaType.APPLICATION_JSON)
	Response eliminaAreaScientificaAssociata(@QueryParam("idU") Long idU,
			 							     @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
			 							     @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("regole/cercaregole") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findRegole(@QueryParam("idU") Long idU,
			 		    @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("regole/regoleassociate") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findRegoleAssociate(@QueryParam("idU") Long idU,
			 					 @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
			 					 @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("regole/associaregola") 
	@Produces(MediaType.APPLICATION_JSON)
	Response associaRegola(@QueryParam("idU") Long idU,
			 		       @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
			 		       @QueryParam("idRegola") Long idRegola,
			 		       @QueryParam("idTipoAnagrafica") Long idTipoAnagrafica,
			 		       @QueryParam("idTipoBeneficiario") Long idTipoBeneficiario,
			 			   @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("regole/eliminaregola") 
	@Produces(MediaType.APPLICATION_JSON)
	Response eliminaRegola(@QueryParam("idU") Long idU,
			 		       @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
			 		       @QueryParam("idRegola") Long idRegola,
			 		       @QueryParam("idTipoAnagrafica") Long idTipoAnagrafica,
			 		       @QueryParam("idTipoBeneficiario") Long idTipoBeneficiario,
			 			   @Context HttpServletRequest req) throws Exception;
	
	
	@POST
	@Path("costanti/salvacostanti")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaCostanti(@QueryParam("idU") Long idU,
									   @Context HttpServletRequest req, 
									   CostantiBandoLineaDTO costantiBandoLinea
						    ) throws Exception;
	
	
	@GET
	@Path("costanti/tipooperazione") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findTipoOperazione(@QueryParam("idU") Long idU,
			 			        @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("costanti/statodomanda") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findStatoDomanda(@QueryParam("idU") Long idU,
			 			      @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("costanti/costantibandolinea") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findCostantiBandoLinea(@QueryParam("idU") Long idU,
									@QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
									@Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("modellididocumento/findbandolinea")                                            //Bando-Linea da cui duplicare i modelli di documento
	@Produces(MediaType.APPLICATION_JSON)
	Response findBandoLinea(@QueryParam("idU") Long idU,
			 				@Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("modellididocumento/findmodellidaassociare")                                    //Visualizza i modelli di documento da duplicare
	@Produces(MediaType.APPLICATION_JSON)
	Response findModelliDaAssociare(@QueryParam("idU") Long idU,
									@QueryParam("progrBandoLineaIntervento") String progrBandoLineaIntervento,      //idBando selezionato da cui duplicare i documenti
			 					 	@Context HttpServletRequest req) throws Exception;
	
	
	
	@POST
	@Path("modellididocumento/inseriscimodello") 
	@Produces(MediaType.APPLICATION_JSON)
	Response inserisciModello( ModelloDTO modelloDTO,
	 					 	   @Context HttpServletRequest req) throws Exception;
	
	
	
	@GET
	@Path("modellididocumento/findmodelliassociati")                                    //Visualizza i modelli di documento gia' associati
	@Produces(MediaType.APPLICATION_JSON)
	Response findModelliAssociati(@QueryParam("idU") Long idU,
								  @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,     
			 					  @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("modellididocumento/eliminamodelloassociato")                                   
	@Produces(MediaType.APPLICATION_JSON)
	Response eliminaModelloAssociato(@QueryParam("idU") Long idU,
									 @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,     
									 @QueryParam("idTipoDocumentoIndex") Long idTipoDocumentoIndex,     
			 					 	 @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("modellididocumento/modificatipodocumento")                                   
	@Produces(MediaType.APPLICATION_JSON)
	Response modificaTipoDocuemtoAssociato(@QueryParam("idU") Long idU,
										   @QueryParam("progrBandoLinea") Long progrBandoLinea,     
										   @QueryParam("idTipoDocumento") Long idTipoDocumento,     
					 					   @Context HttpServletRequest req) throws Exception;

	@POST
	@Path("modellididocumento/salvamoficheodocumento")                                   
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaSezioni(@QueryParam("idU") Long idU,
						  String paramSezioni,         
					 	  @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("modellididocumento/generadocumento")                                   
	@Produces(MediaType.APPLICATION_JSON)
	Response generaDocumento(@QueryParam("idU") Long idU,
						     @QueryParam("progrBandolineaIntervento") Long progrBandolineaIntervento,    
						     @QueryParam("idTipoDocumento") Long idTipoDocumento,    
					 	     @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("modellididocumento/getnomedocumento")                                   
	@Produces(MediaType.APPLICATION_JSON)
	Response getNomeDocumento( @QueryParam("idU") Long idU,
							   @QueryParam("progrBandolineaIntervento") Long progrBandolineaIntervento,    
							   @QueryParam("idTipoDocumento") Long idTipoDocumento,    
					 	       @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("modellididocumento/downloaddocumento")                                   
	@Produces(MediaType.APPLICATION_JSON)
	Response downloadDocumento(@QueryParam("idU") Long idU,
						     @QueryParam("idTipoDocumentoIndex") Long idTipoDocumentoIndex,    
						     @QueryParam("progrBandolinea") Long progrBandolinea,    
					 	     @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("checklist/checklistassociate")                                   
	@Produces(MediaType.APPLICATION_JSON)
	Response findCheckListAssociate(@QueryParam("idU") Long idU,
						  			@QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,         
						  			@Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("checklist/checklistassociabili")                                   
	@Produces(MediaType.APPLICATION_JSON)
	Response findCheckListAssociabili(@QueryParam("idU") Long idU,    
					 	  			  @Context HttpServletRequest req) throws Exception;
	
	
	@POST
	@Path("checklist/associachecklist")                                   
	@Produces(MediaType.APPLICATION_JSON)
	Response associaCheklist( CheclistDTO checklistDTO,  
						 	  @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("checklist/eliminachecklist")                                   
	@Produces(MediaType.APPLICATION_JSON)
	Response eliminaCheklistAssociata(@QueryParam("idU") Long idU,
									  @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,  
									  @QueryParam("idTipoDocumentoIndex") Long idTipoDocumentoIndex,   
								 	  @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("pagamento/tipidocumento")                                   
	@Produces(MediaType.APPLICATION_JSON)
	Response findTipiDocumento(@QueryParam("idU") Long idU,
							   @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("pagamento/modalitapagamento")                                   
	@Produces(MediaType.APPLICATION_JSON)
	Response findModalitaPagamento(@QueryParam("idU") Long idU,
							   @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("pagamento/docpagamentiassociati")                                   
	@Produces(MediaType.APPLICATION_JSON)
	Response findDocPagamAssociati(@QueryParam("idU") Long idU,
								   @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,    
							 	   @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("pagamento/docpagamentiassociatituttibl")                                   
	@Produces(MediaType.APPLICATION_JSON)
	Response findDocPagamAssociatiATuttiBL(@QueryParam("idU") Long idU,
					 	  				   @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("pagamento/eliminadocpagamassociato")                                   
	@Produces(MediaType.APPLICATION_JSON)
	Response eliminaDocPagamAssociato(@QueryParam("idU") Long idU,
									  @QueryParam("progrEccezBanLinDocPag") Long progrEccezBanLinDocPag,
					 	  			  @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("pagamento/eliminadocpagamassociatotuttibl")                                   
	@Produces(MediaType.APPLICATION_JSON)
	Response eliminaDocPagamAssociatoATuttiBL(@QueryParam("idU") Long idU,
											  @QueryParam("idTipoDocumento") Long idTipoDocumento,
											  @QueryParam("idModalitaPagamento") Long idModalitaPagamento,
							 	  			  @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("pagamento/associadocpagamento")                                   
	@Produces(MediaType.APPLICATION_JSON)
	Response associaDocPagam(@QueryParam("idU") Long idU,
							 @QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
							 @QueryParam("idTipoDocumento") Long idTipoDocumento,
							 @QueryParam("idModalitaPagamento") Long idModalitaPagamento,
		 	  			  	 @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("pagamento/associadocpagamtuttibl")                                   
	@Produces(MediaType.APPLICATION_JSON)
	Response associaDocPagamATuttiBL(@QueryParam("idU") Long idU,
							 @QueryParam("idTipoDocumento") Long idTipoDocumento,
							 @QueryParam("idModalitaPagamento") Long idModalitaPagamento,
		 	  			  	 @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("pagamento/inseriscitipodocumentospesa")                                   
	@Produces(MediaType.APPLICATION_JSON)
	Response inserisciTipoDocumentoSpesa(@QueryParam("idU") Long idU,
							 @QueryParam("descrizione") String descrizione,
							 @QueryParam("descrizioneBreve") String descrizioneBreve,
		 	  			  	 @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("pagamento/inseriscimodalitapèagamento")                                   
	@Produces(MediaType.APPLICATION_JSON)
	Response inserisciModalitaPagamento(@QueryParam("idU") Long idU,
							 @QueryParam("descrizione") String descrizione,
							 @QueryParam("descrizioneBreve") String descrizioneBreve,
		 	  			  	 @Context HttpServletRequest req) throws Exception;
	
	
	
	
	///////////////////////////////   TAB ESTREMI BANCARI //////////////////////////////////////
	
	@GET
	@Path("estremibancari/getestremibancari") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getEstremiBancari(
			@QueryParam("idBando") Long idBando,
			@Context HttpServletRequest req) throws Exception;
		
	@GET
	@Path("estremibancari/getBancheByDesc") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getBancheByDesc(
			@QueryParam("descrizione") String descrizione,
			@Context HttpServletRequest req) throws Exception;
		
	@POST
	@Path("estremibancari/insertEstremiBancari") 
	Response insertEstremiBancari(
			@RequestBody InsertEstremiBancariDTO insertEstremiBancariDTO,
			@Context HttpServletRequest req) throws Exception;
	
	@POST
	@Path("estremibancari/sendToAmministrativoContabile") 
	Response sendToAmministrativoContabile(
			@QueryParam("idU") Long idU,
			@QueryParam("idBando") Long idBando,
			@Context HttpServletRequest req) throws Exception;
		
	@POST
	@Path("estremibancari/removeAssociazioneIban") 
	Response removeAssociazioneIban(
			@QueryParam("idBando") Long idBando,
			@QueryParam("idModalitaAgevolazione") Long idModalitaAgevolazione,
			@QueryParam("idBanca") Long idBanca,
			@RequestBody EstremiContoDTO EstremiContoDTO,
			@Context HttpServletRequest req) throws Exception;
		
	
	///////////////////////////////   TAB MONITORAGGIO TEMPI //////////////////////////////////////	
	
	
	@GET
	@Path("monitoraggioTempi/getParametriMonitoraggioTempi") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	Response getParametriMonitoraggioTempi(
			@Context HttpServletRequest req) throws Exception;
			
	@GET
	@Path("monitoraggioTempi/getParametriMonitoraggioTempiAssociati") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	Response getParametriMonitoraggioTempiAssociati(
			@QueryParam("idBandoLinea") Long idBandoLinea,
			@Context HttpServletRequest req) throws Exception;
			
	@POST
	@Path("monitoraggioTempi/insertParametriMonitoraggioTempiAssociati") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	Response insertParametriMonitoraggioTempiAssociati(
			@RequestBody ParametriMonitoraggioTempiAssociatiDTO parMonitTempiInput,
			@Context HttpServletRequest req) throws Exception;
	
	@POST
	@Path("monitoraggioTempi/updateParametriMonitoraggioTempiAssociati") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	Response updateParametriMonitoraggioTempiAssociati(
			@RequestBody ParametriMonitoraggioTempiAssociatiDTO parMonitTempiInput,
			@Context HttpServletRequest req) throws Exception;
	
	@POST
	@Path("monitoraggioTempi/deleteParametriMonitoraggioTempiAssociati") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	Response deleteUpdateParametriMonitoraggioTempiAssociati(
			@QueryParam("idParamMonitBandoLinea") Long idParamMonitBandoLinea,
			@Context HttpServletRequest req) throws Exception;

	
	////////////////////////////////////////////////////////////
	
	
	@GET
	@Path("bandoIsEnteCompetenzaFinpiemonte") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	Response bandoIsEnteCompetenzaFinpiemonte(
			@QueryParam("progBandoLineaIntervento") Long progBandoLineaIntervento,
			@Context HttpServletRequest req) throws Exception;

	
	
}



