/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business.service;

import java.security.InvalidParameterException;

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

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.ChecklistRettificaForfettariaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.EsitoSalvaIrregolaritaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.EsitoSalvaRettificaForfettariaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.RettificaForfettariaDTO;
import it.csi.pbandi.pbweberog.dto.CodiceDescrizione;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.registrocontrolli.FiltroRicercaIrregolarita;
import it.csi.pbandi.pbweberog.dto.registrocontrolli.Irregolarita;
import it.csi.pbandi.pbweberog.dto.registrocontrolli.ModificaIrregolaritaDefinitiva;
import it.csi.pbandi.pbweberog.dto.registrocontrolli.RettificaForfettaria;
import it.csi.pbandi.pbweberog.integration.request.RequestModificaIrregolarita;
import it.csi.pbandi.pbweberog.integration.request.RequestSalvaIrregolarita;

@Path("/registroControlli")
@Api(value = "registroControlli")
public interface RegistroControlliService {
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////// SERVIZI PER RICERCA /////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@POST
	@Path("/irregolarita") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds irregolarita by FiltroRicercaIrregolarita", response=Irregolarita.class, responseContainer = "List")
	Response getIrregolarita( @Context HttpServletRequest req, FiltroRicercaIrregolarita filtro) throws UtenteException, Exception;	
	
	@POST
	@Path("/esitiRegolari") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Esiti Regolari by FiltroRicercaIrregolarita", response=Irregolarita.class, responseContainer = "List")
	Response getEsitiRegolari( @Context HttpServletRequest req, FiltroRicercaIrregolarita filtro) throws UtenteException, Exception;	
	
	@POST
	@Path("/rettifiche") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Rettifiche by FiltroRicercaIrregolarita", response=RettificaForfettaria.class, responseContainer = "List")
	Response getRettifiche( @Context HttpServletRequest req, FiltroRicercaIrregolarita filtro) throws UtenteException, Exception;	
	
	
	@GET
	@Path("cercabeneficiari") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findBeneficiari( @QueryParam("idU") Long idU,
							  @QueryParam("idSoggetto") Long idSoggetto,
							  @QueryParam("denominazione") String denominazione,
							  @QueryParam("idBeneficiario") Long idBeneficiario,
							  @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("cercaProgetti") 
	@Produces(MediaType.APPLICATION_JSON)
	Response findProgettiByBeneficiario( @QueryParam("idU") Long idU,
										 @QueryParam("idSoggetto") Long idSoggetto,
										 @QueryParam("idSoggettoBeneficiario") Long idSoggettoBeneficiario,
								         @Context HttpServletRequest req) throws Exception;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////// SERVIZI PER DETTAGLIO ///////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@GET
	@Path("/dettaglioIrregolarita/{idIrregolarita}") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Dettaglio Irregolarita by idIrregolaritas", response=Irregolarita.class)
	Response getDettaglioIrregolarita( @Context HttpServletRequest req, @QueryParam("idU") Long idU, @PathParam("idIrregolarita") Long idIrregolarita) throws UtenteException, Exception;	
	
	@GET
	@Path("/dettaglioIrregolaritaProvvisoria/{idIrregolarita}") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Dettaglio Irregolarita by idIrregolaritas", response=Irregolarita.class)
	Response getDettaglioIrregolaritaProvvisoria( @Context HttpServletRequest req, @QueryParam("idU") Long idU, @PathParam("idIrregolarita") Long idIrregolarita) throws UtenteException, Exception;
	
	@GET
	@Path("/dettaglioEsitiRegolari/{idEsitoRegolare}") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Dettaglio Esito Regolare by idEsitoRegolare", response=Irregolarita.class)
	Response getDettaglioEsitoRegolare( @Context HttpServletRequest req, @PathParam("idEsitoRegolare") Long idEsitoRegolare) throws UtenteException, Exception;	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////// SERVIZI PER MODIFICA  ///////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@POST
	@Path("modificaIrregolaritaDefinitiva") 
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	Response modificaIrregolaritaDefinitiva(MultipartFormDataInput multipartFormData) throws InvalidParameterException, Exception;
	
	
//	@PUT
//	@Path("/modificaIrregolaritaDefinitiva2") 
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "Updates irregolarita", response=EsitoSalvaIrregolaritaDTO.class)
//	Response modificaIrregolaritaDefinitiva2( ModificaIrregolaritaDefinitiva modificaIrregolaritaDefinitiva, 
//			                                 @Context HttpServletRequest req) throws UtenteException, Exception;	
	
	
//	@PUT
//	@Path("/modificaIrregolarita") 
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "Updates irregolarita", response=EsitoSalvaIrregolaritaDTO.class)
//	Response modificaIrregolarita( @Context HttpServletRequest req, RequestModificaIrregolarita modificaRequest) throws UtenteException, Exception;	
	
	@PUT
	@Path("/modificaIrregolaritaProvvisoria") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Updates irregolarita", response=EsitoSalvaIrregolaritaDTO.class)
	Response modificaIrregolaritaProvvisoria( @Context HttpServletRequest req, RequestModificaIrregolarita modificaRequest) throws UtenteException, Exception;		
		
	@PUT
	@Path("/modificaEsitiRegolari") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Updates irregolarita regolare",response=EsitoSalvaIrregolaritaDTO.class)
	Response modificaEsitoRegolare( @Context HttpServletRequest req, RequestModificaIrregolarita modificaRequest) throws UtenteException, Exception;	
	
	@GET
	@Path("bloccaSbloccaIrregolarita") 
	@Produces(MediaType.APPLICATION_JSON)
	Response bloccaSbloccaIrregolarita( @QueryParam("idU") Long idU,
								 @QueryParam("idIrregolarita") Long idIrregolarita,
								 @Context HttpServletRequest req) throws Exception;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////// SERVIZI PER INSERIMENTO /////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@POST
	@Path("/inserimentoEsitoRegolare") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Updates irregolarita regolare",response=EsitoSalvaIrregolaritaDTO.class)
	Response inserisciIrregolaritaRegolare( @Context HttpServletRequest req, RequestSalvaIrregolarita salvaRequest) throws UtenteException, Exception;	
	
	@POST
	@Path("/inserimentoIrregolarita") 
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	Response inserisciIrregolarita(MultipartFormDataInput multipartFormData) throws InvalidParameterException, Exception;
	
//	@POST
//	@Path("/inserimentoIrregolarita2") 
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "Updates irregolarita regolare",response=EsitoSalvaIrregolaritaDTO.class)
//	Response inserisciIrregolarita2( @Context HttpServletRequest req, RequestSalvaIrregolarita salvaRequest) throws UtenteException, Exception;	
	
	@POST
	@Path("/inserimentoIrregolaritaProvv") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Updates irregolarita regolare",response=EsitoSalvaIrregolaritaDTO.class)
	Response inserisciIrregolaritaProvvisoria( @Context HttpServletRequest req, RequestSalvaIrregolarita salvaRequest) throws UtenteException, Exception;	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////// SERVIZI PER CANCELLAZIONE ////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@DELETE
	@Path("/cancellazioneEsitiRegolari/{idIrregolarita}") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Dettaglio Irregolarita by idIrregolaritas", response=EsitoSalvaIrregolaritaDTO.class)
	Response cancellaIrregolaritaRegolare( @Context HttpServletRequest req, @PathParam("idIrregolarita") Long idIrregolarita) throws UtenteException, Exception;	
	
	@DELETE
	@Path("/cancellazioneIrregolarita/{idIrregolarita}") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Dettaglio Irregolarita by idIrregolaritas", response=EsitoSalvaIrregolaritaDTO.class)
	Response cancellaIrregolarita( @Context HttpServletRequest req, @PathParam("idIrregolarita") Long idIrregolarita) throws UtenteException, Exception;	
	
	@DELETE
	@Path("/cancellazioneIrregolaritaProvv/{idIrregolarita}") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Dettaglio Irregolarita by idIrregolaritas", response=EsitoSalvaIrregolaritaDTO.class)
	Response cancellaIrregolaritaProvvisoria( @Context HttpServletRequest req, @PathParam("idIrregolarita") Long idIrregolarita) throws UtenteException, Exception;	
		
	
	
	

	@GET
	@Path("/documenti/{idDocumentoIndex}/contenuto") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds anno contabili", response = Byte.class, responseContainer = "List")
	Response getContenutoDocumentoById( @Context HttpServletRequest req, @PathParam("idDocumentoIndex") Long idDocumentoIndex) throws UtenteException, Exception;	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////// SERVIZI PER LE COMBO /////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@GET
	@Path("/periodi") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds anno contabili", response=CodiceDescrizione.class, responseContainer = "List")
	Response getPeriodi( @Context HttpServletRequest req) throws UtenteException, Exception;	
	
	@GET
	@Path("/idTipoOperazione") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds anno contabili", response=CodiceDescrizione.class, responseContainer = "List")
	Response getIdTipoOperazione(@QueryParam("idU") Long idU, @QueryParam("idProgetto") Long idProgetto, @Context HttpServletRequest req) throws UtenteException, Exception;	
	
	@GET
	@Path("/categAnagrafica") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds anno contabili", response=CodiceDescrizione.class, responseContainer = "List")
	Response getCategAnagrafica( @Context HttpServletRequest req) throws UtenteException, Exception;	
	
	
	
	@GET
	@Path("/dataCampione") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds data campione", response=CodiceDescrizione.class, responseContainer = "List")
	Response getDataCampione( @Context HttpServletRequest req,  @QueryParam("idProgetto") Long idProgetto , @QueryParam("tipoControlli") String tipoControlli,
			@QueryParam("idPeriodo") Long idPeriodo, @QueryParam("idCategAnagrafica")  Long idCategAnagrafica) throws UtenteException, Exception;
	
	
	
	@GET
	@Path("/motivoIrregolarita") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds motivo irregolarita", response=CodiceDescrizione.class, responseContainer = "List")
	Response getMotivoIrregolarita( @Context HttpServletRequest req) throws UtenteException, Exception;
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////// SERVIZI PER RETTIFICHE FORFEITTARIE SU AFFIDAMENTI ////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@GET
	@Path("/propostaAperta") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Controlla se esiste una proposta certificazione apperta per un progetto", response = EsitoOperazioni.class)
	Response esistePropostaCertificazioneAperta( @Context HttpServletRequest req,  @QueryParam("idProgetto") Long idProgetto, 
			                                                                       @QueryParam("idBeneficario") Long idBeneficario) throws UtenteException, Exception;	
	
	@GET
	@Path("/checkListRettifiche") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Checklist Rettifiche (per popolare la tabella checlist rettifiche)", response = ChecklistRettificaForfettariaDTO.class)
	Response getChecklistRettifiche( @Context HttpServletRequest req,  @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;	
	
	

	@POST
	@Path("/rettifica") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Saves Rettifica Forfettaria",response=EsitoSalvaRettificaForfettariaDTO.class)
	Response salvaRettificaForfettaria( @Context HttpServletRequest req, RettificaForfettariaDTO rettifica) throws UtenteException, Exception;	
	
	@DELETE
	@Path("/rettificheForfettarie/{idRettificaForfett}") 
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Dettaglio Irregolarita by idIrregolaritas", response=EsitoSalvaIrregolaritaDTO.class)
	Response eliminaRettificaForfettaria( @Context HttpServletRequest req, @PathParam("idRettificaForfett") Long idRettificaForfett) throws UtenteException, Exception;	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////// SERVIZI PER REGISTRA INVIO ////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@POST
	@Path("/salvaRegistroInvio") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Modifica una nuova irregolarita inserendo le informazioni relative a IMS.",response=EsitoSalvaRettificaForfettariaDTO.class)
	Response registraInvioIrregolarita( @Context HttpServletRequest req, Irregolarita irregolarita) throws UtenteException, Exception;	
	
	
}
