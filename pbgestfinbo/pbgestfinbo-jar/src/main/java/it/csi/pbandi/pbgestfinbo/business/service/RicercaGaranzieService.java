/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;


import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.web.bind.annotation.RequestBody;

import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.exception.RecordNotFoundException;
import it.csi.pbandi.pbgestfinbo.integration.vo.RichiestaIntegrazioneVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaAllegatiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaStatoCreditoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaStoricoEscussioneVO;

@Path("/ricercaGaranzie")
public interface RicercaGaranzieService {
	
	// Ricerca //
	
	@GET
	@Path("/ricercaGaranzie")
	@Produces(MediaType.APPLICATION_JSON)
	Response ricercaGaranzie(
			@DefaultValue("") @QueryParam("descrizioneBando") String descrizioneBando,
			@DefaultValue("") @QueryParam("codiceProgetto") String codiceProgetto,
			@DefaultValue("") @QueryParam("codiceFiscale") String codiceFiscale,
			@DefaultValue("") @QueryParam("nag") String nag,
			@DefaultValue("") @QueryParam("partitaIva") String partitaIva,
			@DefaultValue("") @QueryParam("denominazioneCognomeNome") String denominazioneCognomeNome,
			@DefaultValue("") @QueryParam("statoEscussione") String statoEscussione,
			@DefaultValue("") @QueryParam("denominazioneBanca") String denominazioneBanca,
			@Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("/getSuggestions")
	@Produces(MediaType.APPLICATION_JSON)
	Response getSuggestions(
			@DefaultValue("000") @QueryParam("value") String value,
			@DefaultValue("1") @QueryParam("id") String id, 
	        @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("/initListaStatiEscussione")
	@Produces(MediaType.APPLICATION_JSON)
	Response initListaStatiEscussione(
			@Context HttpServletRequest req) throws Exception;
	
	
	
	@GET
	@Path("/getGaranzia")
	@Produces(MediaType.APPLICATION_JSON)
	Response getGaranzia(
			@DefaultValue("") @QueryParam("idEscussione") BigDecimal idEscussione,
			@DefaultValue("") @QueryParam("idProgetto") BigDecimal idProgetto,
			@Context HttpServletRequest req) throws Exception;
	
	
	
	
	@GET
	@Path("/getBancaSuggestion")
	@Produces(MediaType.APPLICATION_JSON)
	Response getBancaSuggestion(
			@DefaultValue("000") @QueryParam("value") String value,
			@Context HttpServletRequest req) throws Exception;
	

	
	
	@GET
	@Path("/getListaTipoEscussione")
	@Produces(MediaType.APPLICATION_JSON)
	Response getListaTipoEscussione(
			@Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("/getListaStatiCredito")
	@Produces(MediaType.APPLICATION_JSON)
	Response getListaStatiCredito(
			@Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("/initDialogEscussione")
	@Produces(MediaType.APPLICATION_JSON)
	Response initDialogEscussione(
			@DefaultValue("0") @QueryParam("idStatoEscussione") int idStatoEscussione,
			@Context HttpServletRequest req) throws Exception;
	
	/* Non usato da nessuna parte
	@GET
	@Path("/getVisualizzaDettaglioGaranzia") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getVisualizzaDettaglioGaranzia( 
		    @QueryParam("idProgetto") String idProgetto,
	        @Context HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException;*/
	
	@GET
	@Path("/getVisualizzaRevocaBancaria") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getVisualizzaRevocaBancaria( 
		    @QueryParam("idProgetto") String idProgetto,
	        @Context HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException;
	
	@GET
	@Path("/getVisualizzaAzioniRecuperoBanca") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getVisualizzaAzioniRecuperoBanca( 
		    @QueryParam("idProgetto") String idProgetto,
	        @Context HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException;
	
	@GET
	@Path("/getVisualizzaSaldoStralcio") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getVisualizzaSaldoStralcio( 
		    @QueryParam("idProgetto") String idProgetto,
		    @QueryParam("idAttivitaEsito") int idAttivitaEsito,
	        @Context HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException;
	
	@GET
	@Path("/getVisualizzaDatiAnagrafici") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getVisualizzaDatiAnagrafici( 
		    @QueryParam("idProgetto") String idProgetto,
	        @Context HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException;
	
	@GET
	@Path("/getVisualizzaSezioneDettaglioGaranzia") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getVisualizzaSezioneDettaglioGaranzia( 
		    @QueryParam("idProgetto") String idProgetto,
	        @Context HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException;
	
	@GET
	@Path("/getVisualizzaSatoCreditoeStorico") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getVisualizzaStatoCreditoStorico( 
		    @QueryParam("idProgetto") String idProgetto,
		    @QueryParam("idUtente") int idUtente,
	        @Context HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException;
	@GET
	@Path("/getStatoCredito") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getStatoCredito( 
			@QueryParam("idProgetto") String idProgetto,
			@Context HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException;
	
	@GET
	@Path("/getVisualizzaStoricoEscussione") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getVisualizzaStoricoEscussione( 
		    @QueryParam("idProgetto") String idProgetto,
		    //@QueryParam("codBanca") int codBanca,
		    //@QueryParam("codUtente") String codUtente,
	        @Context HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException;
	
	@GET
	@Path("/getAllegati") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getAllegati( 
		    @QueryParam("idEscussione") Long idEscussione,
	        @Context HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException;
	
	@GET
	@Path("/getAbilitazione") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getAbilitazione( 
		    @QueryParam("ruolo") String ruolo,
	        @Context HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException;
	
	@GET
	@Path("/getRichIntegr") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getRichIntegr( 
		    @QueryParam("idEscussione") String idEscussione,
	        @Context HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException;
	
	
	@POST
	@Path("/updateStatoCredito")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateStatoCredito(
			@Context HttpServletRequest req,
		    @RequestBody VisualizzaStatoCreditoVO vsc)
			throws ErroreGestitoException, RecordNotFoundException;

	
	@POST
	@Path("/insertEscussione")
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertEscussione(
			@Context HttpServletRequest req,
		    @RequestBody VisualizzaStoricoEscussioneVO vse)
			throws ErroreGestitoException, RecordNotFoundException;


	@POST
	@Path("/updateEscussione")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateEscussione(
			@Context HttpServletRequest req,
		    @RequestBody VisualizzaStoricoEscussioneVO vse)
		    //@QueryParam("listaAllegatiPresenti") ArrayList<Long> listaAllegatiPresenti
			throws ErroreGestitoException, RecordNotFoundException;
	
	@POST
	@Path("/updateStatoEscussione")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateStatoEscussione(
			@Context HttpServletRequest req,
		    @RequestBody VisualizzaStoricoEscussioneVO vse)
			throws ErroreGestitoException, RecordNotFoundException;
	
	@POST
	@Path("/updateStatoEscussioneRicIntegrazione")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateStatoEscussioneRicIntegrazione(
			@Context HttpServletRequest req,
		    @RequestBody VisualizzaStoricoEscussioneVO vse)
			throws ErroreGestitoException, RecordNotFoundException;
	
	@POST
	@Path("/salvaUploadAllegato")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public  Response salvaUploadAllegato(
			ArrayList<MultipartFormDataInput> multipartFormData,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
    @Path("/salvaAllegatiGenerici") // NEW
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response salvaAllegatiGenerici(
            @QueryParam("idEscussione") Long idEscussione,
            //@QueryParam("giorniScadenza") Long giorniScadenza,
            MultipartFormDataInput multipartFormDataInput,
            @Context HttpServletRequest req) throws Exception;

	@POST
	@Path("/deleteAllegato")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAllegato(
			@Context HttpServletRequest req,
		    @RequestBody VisualizzaAllegatiVO va)
			throws ErroreGestitoException, RecordNotFoundException;
	
	
	@POST
	@Path("/salvaRichiestaIntELettera") // NEW
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertRichiestaIntegrazELettera(
			@QueryParam("idProgetto") Long idProgetto,
			@QueryParam("idEscussione") Long idEscussione,
			@QueryParam("giorniScadenza") Long giorniScadenza,
			MultipartFormDataInput multipartFormDataInput,
			@Context HttpServletRequest req)
			throws ErroreGestitoException, RecordNotFoundException;
	
	@POST
	@Path("/salvaUploadRichiestaIntegraz")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public  Response salvaUploadRichiestaIntegraz(
			MultipartFormDataInput multipartFormData,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;
	
	@POST
	@Path("/salvaUploadLettera")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public  Response salvaUploadLettera(
			MultipartFormDataInput multipartFormData,
			@Context HttpServletRequest req) throws InvalidParameterException, Exception;

	
	@POST
	@Path("/insertDistinta")
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertDistinta(
			@Context HttpServletRequest req,
		    @RequestBody RichiestaIntegrazioneVO ri)
			throws ErroreGestitoException, RecordNotFoundException;
	
	
	@POST
	@Path("/salvaEsito")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public  Response salvaEsito(
			@QueryParam("idProgetto") Long idProgetto,
			@QueryParam("idEscussione") Long idEscussione,
			@QueryParam("idStatoEscussione") Long idStatoEscussione,
			@QueryParam("idTipoEscussione") int idTipoEscussione,
			MultipartFormDataInput multipartFormData,
			@Context HttpServletRequest req) throws Exception;
}