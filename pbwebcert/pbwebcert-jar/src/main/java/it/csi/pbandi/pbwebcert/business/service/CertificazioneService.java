/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.business.service;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
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

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.validation.annotation.Validated;

import it.csi.pbandi.filestorage.exceptions.IncorrectUploadPathException;
import it.csi.pbandi.pbservizit.exception.ErroreGestitoException;
import it.csi.pbandi.pbservizit.exception.RecordNotFoundException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbwebcert.dto.certificazione.FiltroRicercaDocumentoDTO;
import it.csi.pbandi.pbwebcert.integration.request.AccodaPropostaRequest;
import it.csi.pbandi.pbwebcert.integration.request.AggiornaDatiIntermediaFinaleRequest;
import it.csi.pbandi.pbwebcert.integration.request.AggiornaDatiRequest;
import it.csi.pbandi.pbwebcert.integration.request.AggiornaImportoNettoRequest;
import it.csi.pbandi.pbwebcert.integration.request.AggiornaStatoRequest;
import it.csi.pbandi.pbwebcert.integration.request.CancellaAllegatiRequest;
import it.csi.pbandi.pbwebcert.integration.request.CreaIntermediaFinaleRequest;
import it.csi.pbandi.pbwebcert.integration.request.CreaPropostaRequest;
import it.csi.pbandi.pbwebcert.integration.request.GestisciPropostaIntermediaFinaleRequest;
import it.csi.pbandi.pbwebcert.integration.request.GestisciPropostaRequest;
import it.csi.pbandi.pbwebcert.integration.request.InviaReportRequest;
import it.csi.pbandi.pbwebcert.integration.request.LanciaPropostaRequest;
import it.csi.pbandi.pbwebcert.integration.request.ModificaAllegatiRequest;
import it.csi.pbandi.pbwebcert.integration.request.PropostaCertifLineaRequest;
import it.csi.pbandi.pbwebcert.integration.request.PropostaCertificazioneAllegatiRequest;
import it.csi.pbandi.pbwebcert.integration.request.ProposteCertificazioneRequest;
import it.csi.pbandi.pbwebcert.integration.request.AmmettiESospendiProgettiRequest;


@Path("/certificazione")
@Validated
public interface CertificazioneService {

	@GET
	@Path("home")
	@Produces(MediaType.APPLICATION_JSON)
	Response getCertificazioneHome(@DefaultValue("0") @QueryParam("idSg") Long idSg,
			@DefaultValue("0") @QueryParam("idSgBen") Long idSgBen, @DefaultValue("0") @QueryParam("idU") Long idU,
			@DefaultValue("--") @QueryParam("role") String role, @Context HttpServletRequest req)
			throws UtenteException, ErroreGestitoException;

	/**************************** SERVIZI DI CHECKLIST E DICHIARAZIONE FINALE ******************************/
	/******************************************************************************************************/
	@POST
	@Path("/proposte")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response getProposteCertificazione(@Context HttpServletRequest req,
		 ProposteCertificazioneRequest proposteCertificazioneRequest) throws RecordNotFoundException, UtenteException, Exception;
	
	@POST
	@Path("proposte/aggiornamentoStato")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response getProposteAggiornamentoStatoCertificazione(@Context HttpServletRequest req,
			ProposteCertificazioneRequest proposteCertificazioneRequest) throws UtenteException, Exception;
	
	@POST
	@Path("proposte/lineeIntervento")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response getLineeDiInterventoFromIdLinee(@Context HttpServletRequest req,
			PropostaCertifLineaRequest propostaCertifLineaRequest) throws UtenteException, Exception;
	
	
	@GET
	@Path("/proposta")
	@Produces(MediaType.APPLICATION_JSON)
	Response getPropostaCertificazione(@Context HttpServletRequest req, @QueryParam("idProposta") BigDecimal idProposta) throws UtenteException, Exception;
	
	@POST
	@Path("/proposta/allegati")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response getAllegatiPropostaCertificazione(@Context HttpServletRequest req, PropostaCertificazioneAllegatiRequest allegatiPropostaRequest) throws UtenteException, Exception;
	
	@POST
	@Path("/proposta/allegati/deleteList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response cancellaAllegati(@Context HttpServletRequest req, CancellaAllegatiRequest cancellaAllegatiRequest) 
			throws UtenteException, Exception;
	
	@POST
	@Path("/proposte/{idProposta}/allegato")
	@Consumes("multipart/form-data")
	@Produces(MediaType.APPLICATION_JSON)
	Response allegaFileProposta(@Context HttpServletRequest req, @PathParam("idProposta")Long idProposta, MultipartFormDataInput file) 
			throws IOException, IncorrectUploadPathException, Exception;
	
	@PUT
	@Path("/proposte/{idProposta}/allegato")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response modificaAllegati(@Context HttpServletRequest req, @PathParam("idProposta")Long idProposta, ModificaAllegatiRequest modificaRequest) throws UtenteException, Exception;
	
	@GET
	@Path("/proposta/{idLineaIntervento}/attivita")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAttivitaLineaIntervento(@Context HttpServletRequest req, @PathParam("idLineaIntervento") Long idLineaIntervento) throws UtenteException, Exception;
	
	
	@GET
	@Path("/proposte/{idProposta}/progetti")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAllProgetti(@Context HttpServletRequest req, @PathParam("idProposta") Long idProposta,
			 @QueryParam("idLineaIntervento") Long idLineaIntervento,
			 @QueryParam("idBeneficiario") Long idBeneficiario,
			 @QueryParam("nomeBandoLinea") String nomeBandoLinea,
			 @QueryParam("denominazioneBeneficiario") String denominazioneBeneficiario) throws UtenteException, Exception;
	
	@GET
	@Path("/proposte/{idProposta}/dettaglio")
	@Produces(MediaType.APPLICATION_JSON)
	Response getDettaglioProposta(@Context HttpServletRequest req, @PathParam("idProposta") Long idProposta) throws UtenteException, Exception;
	
	@GET
	@Path("/proposte/{idProposta}/beneficiari")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAllBeneficiari(@Context HttpServletRequest req, @PathParam("idProposta") Long idProposta,
			 @QueryParam("idLineaIntervento") Long idLineaIntervento,  @QueryParam("nomeBandoLinea") String nomeBandoLinea) throws UtenteException, Exception;
	
	@GET
	@Path("/progetti/{idProposta}/proposta")
	@Produces(MediaType.APPLICATION_JSON)
	Response getProgettiProposta(@Context HttpServletRequest req,  @PathParam("idProposta") Long idProposta, 
								 @QueryParam("idProgetto") Long idProgetto,
								 @QueryParam("idLineaIntervento") Long idLineaIntervento,
								 @QueryParam("idBeneficiario") Long idBeneficiario) throws UtenteException, Exception;
	
	/****************************************** SERVIZI DI GESTIONE PROPOSTE *******************************/
	/******************************************************************************************************/
								/*===== Intermedia Finale =====*/
	@POST
	@Path("/proposta/intermediaFinale")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response creaIntermediaFinale(@Context HttpServletRequest req, CreaIntermediaFinaleRequest creaIntermediaFinaleRequest) 
			throws it.csi.pbandi.pbwebcert.exception.CertificazioneException, Exception;
	@GET
	@Path("/proposte/{idProposta}/bandoLinea/intermediaFinale")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAllBandoLineaIntermediaFinale(@Context HttpServletRequest req,  @PathParam("idProposta") Long idProposta) 
			throws it.csi.pbandi.pbwebcert.exception.CertificazioneException, Exception;
	
	@GET
	@Path("/proposte/{idProposta}/beneficiari/intermediaFinale")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAllBeneficiariIntermediaFinale(@Context HttpServletRequest req,  @PathParam("idProposta") Long idProposta,
												@QueryParam("nomeBandoLinea") String nomeBandoLinea) 
			throws it.csi.pbandi.pbwebcert.exception.CertificazioneException, Exception;
	
	@GET
	@Path("/proposte/{idProposta}/progetti/intermediaFinale")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAllProgettiIntermediaFinale(@Context HttpServletRequest req,  @PathParam("idProposta") Long idProposta,
											@QueryParam("nomeBandoLinea") String nomeBandoLinea,
											@QueryParam("denominazioneBeneficiario") String denominazioneBeneficiario)
			throws it.csi.pbandi.pbwebcert.exception.CertificazioneException, Exception;
	
	@POST
	@Path("/proposte/{idProposta}/progettiDaGestire/intermediaFinale")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response getGestisciPropostaIntermediaFinale(@Context HttpServletRequest req,  @PathParam("idProposta") Long idProposta,
												GestisciPropostaIntermediaFinaleRequest gestisciPropostaRequest) 
			throws it.csi.pbandi.pbwebcert.exception.CertificazioneException, Exception;
	
	@PUT
	@Path("/proposta/aggiornamentoProgetti/intermediaFinale")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response modificaProgettiIntermediaFinale(@Context HttpServletRequest req, AggiornaDatiIntermediaFinaleRequest aggiornaRequest) throws UtenteException, Exception;
	
								/*===== Aperta =====*/
	@GET
	@Path("/proposta/statiSelezionabili")
	@Produces(MediaType.APPLICATION_JSON)
	Response getStatiSelezionabili(@Context HttpServletRequest req) 
			throws it.csi.pbandi.pbwebcert.exception.CertificazioneException, Exception;
	
	@PUT
	@Path("/proposta/aggiornamentoStato")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response aggiornaStatoProposta(@Context HttpServletRequest req, AggiornaStatoRequest aggiornaStatoRequest) throws UtenteException, Exception;
	
								/*===== Approvata =====*/
	@GET
	@Path("/proposte/{idProposta}/bandoLinea")
	@Produces(MediaType.APPLICATION_JSON)
	Response getBandoLineaDaProposta(@Context HttpServletRequest req,  @PathParam("idProposta") Long idProposta) 
			throws it.csi.pbandi.pbwebcert.exception.CertificazioneException, Exception;
	
	
	@POST
	@Path("/proposte/{idProposta}/progettiDaGestire")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response getGestisciProposta(@Context HttpServletRequest req,  @PathParam("idProposta") Long idProposta, GestisciPropostaRequest gestisciPropostaRequest) 
			throws it.csi.pbandi.pbwebcert.exception.CertificazioneException, Exception;
	
	@PUT
	@Path("/proposta/aggiornamentoImporto")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response aggiornaImportoNetto(@Context HttpServletRequest req, AggiornaImportoNettoRequest aggiornaImportoNettoRequest) throws UtenteException, Exception;
	
	@PUT
	@Path("/proposta/procedura/aggiornamentoTerminata")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response checkProceduraAggiornamentoTerminata(@Context HttpServletRequest req) throws UtenteException, Exception;
	
	
	@PUT
	@Path("/proposte/{idProposta}/chiusuraConti")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response chiusuraContiPropostaIntermediaFinale(@Context HttpServletRequest req, @PathParam("idProposta") Long idProposta) throws UtenteException, Exception;
	
	@POST
	@Path("/proposta/report")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response inviaReportPostGestione(@Context HttpServletRequest req, InviaReportRequest inviaReportRequest) throws UtenteException, Exception;
	
	@PUT
	@Path("/proposte/{idProposta}/dati")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response aggiornaDatiIntermediaFinale(@Context HttpServletRequest req, @PathParam("idProposta") Long idProposta) throws UtenteException, Exception;
	
	/****************************************** SERVIZI DI CAMPIONAMENTO *******************************/
	/******************************************************************************************************/
	
	@GET
	@Path("/campionamento/annoContabile")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAnnoContabile(@Context HttpServletRequest req) throws UtenteException, Exception;
	
	@GET
	@Path("/campionamento/normative")
	@Produces(MediaType.APPLICATION_JSON)
	Response getLineeInterventoNormative(@Context HttpServletRequest req, @DefaultValue("false") @QueryParam("isConsultazione") Boolean isConsultazione) 
			throws UtenteException, Exception;
	
	@GET
	@Path("/campionamento/normative/{idNormativa}/estrazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response eseguiEstrazioneCampionamento(@Context HttpServletRequest req,  @PathParam("idNormativa") Long idNormativa) throws UtenteException, Exception;
	
	@GET
	@Path("/campionamento/normative/elencoReport")
	@Produces(MediaType.APPLICATION_JSON)
	Response getElencoReportCampionamento(@Context HttpServletRequest req,  
			@QueryParam("idNormativa") Long idNormativa,  @QueryParam("idAnnoContabile") Long idAnnoContabile) throws UtenteException, Exception;
	
	@GET
	@Path("/documenti/{idDocumentoIndex}/contenuto")
	@Produces(MediaType.APPLICATION_JSON)
	Response getContenutoDocumentoById(@Context HttpServletRequest req, @PathParam("idDocumentoIndex") Long idDocumentoIndex) throws Exception;
	
	/***************************************** SERVIZI DI RICERCA DOCUMENTI*********************************/
	/******************************************************************************************************/
	
	@POST
	@Path("/documenti/ricerca")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response ricercaDocumenti(@Context HttpServletRequest req, FiltroRicercaDocumentoDTO filtroRicerca) throws Exception;
	
	
	/********************************* SERVIZI DI CREA ANTEPRIMA PROPOSTA *********************************/
	/******************************************************************************************************/
	@POST
	@Path("/proposta/anteprima")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response creaAnteprimaPropostaCertificazione(@Context HttpServletRequest req, CreaPropostaRequest creaPropostaRequest) throws Exception;
	
	@GET
	@Path("/proposte/{idProposta}/bandoLinea/anteprima")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAllBandoLineaPerAnteprima(@Context HttpServletRequest req, @PathParam("idProposta") Long idProposta) throws Exception;
	
	@GET
	@Path("/proposte/{idProposta}/beneficiari/anteprima")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAllBeneficiariPerAnteprima(@Context HttpServletRequest req, @PathParam("idProposta") Long idProposta,
													@QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento) throws Exception;
	
	@GET
	@Path("/proposte/{idProposta}/progetti/anteprima")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAllProgettiPerAnteprima(@Context HttpServletRequest req, @PathParam("idProposta") Long idProposta,
													@QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
													@QueryParam("idBeneficiario") Long idBeneficiario) throws Exception;
	@GET
	@Path("/lineeDiIntervento")
	@Produces(MediaType.APPLICATION_JSON)
	Response getLineeDiInterventoDisponibili(@Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("/proposte/{idProposta}/anteprima")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAntePrimaProposta(@Context HttpServletRequest req, @PathParam("idProposta") Long idProposta,
													@QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento,
													@QueryParam("idBeneficiario") Long idBeneficiario,
													@QueryParam("idProgetto") Long idProgetto,
													@QueryParam("idLineaDiIntervento") Long idLineaDiIntervento) throws Exception;
	@POST
	@Path("/proposta/progetti/sospensione")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response sospendiProgettiProposta(@Context HttpServletRequest req, AmmettiESospendiProgettiRequest sospendiRequest) throws Exception;
	
	@POST
	@Path("/proposta/progetti/ammissioni")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response ammettiProgettiProposta(@Context HttpServletRequest req, AmmettiESospendiProgettiRequest ammettiRequest) throws Exception;
	
	@POST
	@Path("/proposta/accoda")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response accodaPropostaCertificazione(@Context HttpServletRequest req, AccodaPropostaRequest accodaRequest) throws Exception;
	
	@POST
	@Path("/proposta/lancia")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response lanciaCreazioneProposta(@Context HttpServletRequest req, LanciaPropostaRequest lanciaRequest) throws Exception;
	
	@POST
	@Path("/propostePerRicercaDocumenti")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response propostePerRicercaDocumenti(@Context HttpServletRequest req,
		 ProposteCertificazioneRequest proposteCertificazioneRequest) throws RecordNotFoundException, UtenteException, Exception;
	
}
