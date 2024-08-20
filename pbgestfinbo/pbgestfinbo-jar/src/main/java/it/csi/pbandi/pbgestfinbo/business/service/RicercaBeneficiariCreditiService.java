/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service;

import java.security.InvalidParameterException;

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
import it.csi.pbandi.pbgestfinbo.integration.vo.CessioneQuotaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.EscussioneConfidiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.IscrizioneRuoloVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.LiberazioneBancaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.LiberazioneGaranteVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.PianoRientroVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.SaveSchedaClienteAllVO;


@Path("/ricercaBeneficiariCrediti")
public interface RicercaBeneficiariCreditiService {
	
		// RICERCA BENEFICIARIO //
	
	@GET
	@Path("/getElencoBeneficiari") 
	@Produces(MediaType.APPLICATION_JSON)
	Response getElencoBeneficiari(
			@DefaultValue("") @QueryParam("codiceFiscale") String codiceFiscale,
			@DefaultValue("") @QueryParam("nag") String nag,
			@DefaultValue("") @QueryParam("partitaIva") String partitaIva, 
			@DefaultValue("") @QueryParam("denominazione") String denominazione, 
			@DefaultValue("") @QueryParam("descBando") String descBando, 
			@DefaultValue("") @QueryParam("codiceProgetto") String codiceProgetto, 
	        @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("/getSuggestion")
	@Produces(MediaType.APPLICATION_JSON)
	Response getSuggestion(
			@DefaultValue("000") @QueryParam("value") String value,
			@DefaultValue("1") @QueryParam("id") String id, 
	        @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("/getRevocaAmministrativa")
	@Produces(MediaType.APPLICATION_JSON)
	Response getRevocaAmministrativa(
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgevolazione,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazioneRif") int idModalitaAgevolazioneRif,
			@DefaultValue("0") @QueryParam("revoche") String listRevoche,
	        @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("/getCreditoResiduoEAgevolazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response getCreditoResiduoEAgevolazione(
			@DefaultValue("0") @QueryParam("idProgetto") int idProgetto,
			@DefaultValue("0") @QueryParam("idBando") int idBando,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazioneOrig") int idModalitaAgevolazioneOrig,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazioneRif") int idModalitaAgevolazioneRif,
	        @Context HttpServletRequest req) throws Exception;
	
	
		// MODIFICA BENEFICIARIO //
	
	@GET
	@Path("/getSchedaCliente")
	@Produces(MediaType.APPLICATION_JSON)
	Response getSchedaCliente(
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") Long idModalitaAgevolazione,
	        @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("/getListBanche")
	@Produces(MediaType.APPLICATION_JSON)
	Response getListBanche(
			@DefaultValue("000") @QueryParam("value") String value,
			@Context HttpServletRequest req) throws Exception;
	
	
	@POST
	@Path("/setSchedaCliente")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setSchedaCliente(
			@RequestBody SaveSchedaClienteAllVO schedaCliente,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") Long idModalitaAgevolazione,
			@DefaultValue("false") @QueryParam("flagStatoAzienda") Boolean flagStatoAzienda,
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@Context HttpServletRequest req)
			throws ErroreGestitoException,  RecordNotFoundException;
	
	
		// ISTRUTTORE AREA CREDITI //
	
	
	@GET
	@Path("/getLiberazioneGarante")
	@Produces(MediaType.APPLICATION_JSON)
	Response getLiberazioneGarante(
			@DefaultValue("0") @QueryParam("idProgetto") String value,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") Long idModalitaAgevolazione,
	        @Context HttpServletRequest req) throws Exception;
	
	@POST
	@Path("/setLiberazioneGarante")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setLiberazioneGarante(
			@RequestBody LiberazioneGaranteVO liberazioneGarante,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") Long idModalitaAgevolazione,
			@Context HttpServletRequest req)
			throws ErroreGestitoException,  RecordNotFoundException;

	@GET
	@Path("/getEscussioneConfidi")
	@Produces(MediaType.APPLICATION_JSON)
	Response getEscussioneConfidi(
			@DefaultValue("0") @QueryParam("idProgetto") String idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") Long idModalitaAgevolazione,
	        @Context HttpServletRequest req) throws Exception;
	
	@POST
	@Path("/setEscussioneConfidi")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setEscussioneConfidi(
			@RequestBody EscussioneConfidiVO escussioneConfidi,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") Long idModalitaAgevolazione,
			@Context HttpServletRequest req)
			throws ErroreGestitoException,  RecordNotFoundException;

	
	@GET
	@Path("/getPianoRientro")
	@Produces(MediaType.APPLICATION_JSON)
	Response getPianoRientro(
			@DefaultValue("0") @QueryParam("idProgetto") String idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") Long idModalitaAgevolazione,
	        @Context HttpServletRequest req) throws Exception;
	
	@POST
	@Path("/setPianoRientro")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setPianoRientro(
			@RequestBody PianoRientroVO pianoRientro,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") Long idModalitaAgevolazione,
			@Context HttpServletRequest req)
			throws ErroreGestitoException,  RecordNotFoundException;
	
	
	@GET
	@Path("/getLiberazioneBanca")
	@Produces(MediaType.APPLICATION_JSON)
	Response getLiberazioneBanca(
			@DefaultValue("0") @QueryParam("idProgetto") String idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") Long idModalitaAgevolazione,
	        @Context HttpServletRequest req) throws Exception;
	
	@POST
	@Path("/setLiberazioneBanca")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setLiberazioneBanca(
			@RequestBody LiberazioneBancaVO liberazioneBanca,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") Long idModalitaAgevolazione,
			@Context HttpServletRequest req)
			throws ErroreGestitoException,  RecordNotFoundException;
	
	/*@GET
	@Path("/getIscrizioneRuolo")
	@Produces(MediaType.APPLICATION_JSON)
	Response getIscrizioneRuolo(
			@DefaultValue("0") @QueryParam("idProgetto") String idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") Long idModalitaAgevolazione,
	        @Context HttpServletRequest req) throws Exception;
	
	@POST
	@Path("/setIscrizioneRuolo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setIscrizioneRuolo(
			@RequestBody IscrizioneRuoloVO iscrizioneRuolo,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") Long idModalitaAgevolazione,
			@Context HttpServletRequest req)
			throws ErroreGestitoException,  RecordNotFoundException;*/
	
	
	@GET
	@Path("/getCessioneQuota")
	@Produces(MediaType.APPLICATION_JSON)
	Response getCessioneQuota(
			@DefaultValue("0") @QueryParam("idProgetto") String idProgetto,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") Long idModalitaAgevolazione,
	        @Context HttpServletRequest req) throws Exception;
	
	@POST
	@Path("/setCessioneQuota")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setCessioneQuota(
			@RequestBody CessioneQuotaVO cessioneQuota,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") Long idModalitaAgevolazione,
			@Context HttpServletRequest req)
			throws ErroreGestitoException,  RecordNotFoundException;
	
	
	/*@POST
	@Path("/salvaAllegati")
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaAllegati(
			@QueryParam("idEntita") Long idEntita,
			@QueryParam("idEscussione") Long idEscussione,
			MultipartFormDataInput multipartFormDataInput,
			@Context HttpServletRequest req)
			throws Exception;*/
	
	
		// AMMINISTRATIVO CONTABILE //
	
	@GET
	@Path("/getEstrattoConto")
	@Produces(MediaType.APPLICATION_JSON)
	Response getEstrattoConto(
			@DefaultValue("0") @QueryParam("idUtente") Long idUtente,
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("codiceVisualizzato") String codiceVisualizzato,
			@DefaultValue("0") @QueryParam("ndg") Long ndg,
			@DefaultValue("0") @QueryParam("idAgevolazione") Long idAgevolazione,
			@DefaultValue("0") @QueryParam("idAgevolazioneRif") Long idAgevolazionerif,	
	        @Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("/getPianoAmmortamento")
	@Produces(MediaType.APPLICATION_JSON)
	Response getPianoAmmortamento(
			@DefaultValue("0") @QueryParam("idUtente") Long idUtente,
			@DefaultValue("0") @QueryParam("idProgetto") Long idProgetto,
			@DefaultValue("0") @QueryParam("codiceVisualizzato") String codiceVisualizzato,
			@DefaultValue("0") @QueryParam("ndg") Long ndg,
			@DefaultValue("0") @QueryParam("idAgevolazione") Long idAgevolazione,
			@DefaultValue("0") @QueryParam("idAgevolazioneRif") Long idAgevolazionerif,
	        @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("/getDebitoResiduo")
	@Produces(MediaType.APPLICATION_JSON)
	Response getDebitoResiduo(
			@DefaultValue("0") @QueryParam("idProgetto") int idProgetto,
			@DefaultValue("0") @QueryParam("idBando") int idBando,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgevolazione,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazioneRif") int idModalitaAgevolazioneRif,	
	        @Context HttpServletRequest req) throws Exception;
	
	/*@GET
	@Path("/getRecuperoRevoca")
	@Produces(MediaType.APPLICATION_JSON)
	Response getRecuperoRevoca(
			@DefaultValue("0") @QueryParam("idProgetto") int idProgetto,
			@DefaultValue("0") @QueryParam("idBando") int idBando,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazione") int idModalitaAgevolazione,
			@DefaultValue("0") @QueryParam("idModalitaAgevolazioneRif") int idModalitaAgevolazioneRif,	
			@DefaultValue("0") @QueryParam("idRevoca") int idRevoca,	
	        @Context HttpServletRequest req) throws Exception;*/
	
}
