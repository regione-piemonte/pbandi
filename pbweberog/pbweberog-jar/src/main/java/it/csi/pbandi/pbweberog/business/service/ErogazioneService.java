/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.EsitoReportRichiestaErogazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.EsitoRichiestaErogazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RappresentanteLegaleDTO;
import it.csi.pbandi.pbweberog.dto.CodiceDescrizione;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.erogazione.CodiceDescrizioneCausale;
import it.csi.pbandi.pbweberog.dto.erogazione.DatiCalcolati;
import it.csi.pbandi.pbweberog.dto.erogazione.Erogazione;
import it.csi.pbandi.pbweberog.dto.erogazione.EsitoControllaDati;
import it.csi.pbandi.pbweberog.dto.erogazione.EsitoErogazioneDTO;
import it.csi.pbandi.pbweberog.integration.request.ControllaDatiRequest;
import it.csi.pbandi.pbweberog.integration.request.CreaRichiestaErogazioneRequest;
import it.csi.pbandi.pbweberog.integration.request.GetDatiCalcolatiRequest;
import it.csi.pbandi.pbweberog.integration.request.ModificaErogazioneRequest;
import it.csi.pbandi.pbweberog.integration.request.SalvaErogazioneRequest;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Path("/erogazione")
@Api(value = "erogazione")
public interface ErogazioneService {
  @GET
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Finds erogazione by idProgetto", response = EsitoErogazioneDTO.class)
  Response getErogazione(@QueryParam("idU") Long idU,
                         @QueryParam("idSoggettoBeneficiario") Long idSoggettoBeneficiario,
                         @QueryParam("idProgetto") Long idProgetto,
                         @Context HttpServletRequest req) throws UtenteException, Exception;

  @GET
  @Path("/causali")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Finds erogazione by idProgetto", response = CodiceDescrizioneCausale.class, responseContainer = "List")
  Response getCausaliErogazioni(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;


  @GET
  @Path("/modalitaAgevolazione")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Finds All Modalita Agevolazione Conto Economico by idProgetto", response = CodiceDescrizione.class, responseContainer = "List")
  Response getAllModalitaAgevolazioneContoEconomico(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;

  @GET
  @Path("/getModalitaAgevolazioneDaVisualizzare")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Finds the right Modalita Agevolazione ")
  Response getModalitaAgevolazioneDaVisualizzare(@Context HttpServletRequest req, @QueryParam("idModalitaAgevolazione") Long idModalitaAgev) throws UtenteException, Exception;

  @GET
  @Path("/modalitaErogazione")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Finds All Modalita Erogazione", response = CodiceDescrizione.class, responseContainer = "List")
  Response getAllModalitaErogazione(@Context HttpServletRequest req) throws UtenteException, Exception;

  @GET
  @Path("/codiciDirezione")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Finds All Codici Direzione", response = CodiceDescrizione.class, responseContainer = "List")
  Response getAllCodiciDirezione(@Context HttpServletRequest req) throws UtenteException, Exception;

  @POST
  @Path("/datiCalcolati")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Finds dati calcolati", response = DatiCalcolati.class)
  Response getDatiCalcolati(@Context HttpServletRequest req, GetDatiCalcolatiRequest getDatiCalcolatiRequest) throws UtenteException, Exception;

  @POST
  @Path("/causale/dati")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Controlla dati on select causale erogazione:  "
      + "ControllaDatiRequest.importoCalcolato = Erogazione.spesaProgetto.importoAmmessoContributo,"
      + "ControllaDatiRequest.importoResiduoSpettante = DatiCalcolati.importoResiduoSpettante,"
      + "ControllaDatiRequest.importoErogazioneEffettiva = DatiCalcolati.importoErogazioneEffettiva.", response = EsitoControllaDati.class)
  Response controllaDatiOnSelectCausaleErogazione(@Context HttpServletRequest req, ControllaDatiRequest controllaDatiRequest) throws UtenteException, Exception;

  @GET
  @Path("/dettaglioErogazione")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Finds dettaglioErogazione", response = Erogazione.class)
  Response getDettaglioErogazione(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto, @QueryParam("idErogazione") Long idErogazione) throws UtenteException, Exception;


  @POST
  @Path("/erogazione")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Creates Erogazione", response = EsitoErogazioneDTO.class)
  Response inserisciErogazione(@Context HttpServletRequest req, SalvaErogazioneRequest salvaRequest) throws UtenteException, Exception;


  @DELETE
  @Path("/erogazione")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Deletes Erogazione by idErogazione", response = EsitoOperazioni.class)
  Response eliminaErogazione(@Context HttpServletRequest req, @QueryParam("idErogazione") Long idErogazione) throws UtenteException, Exception;

  @PUT
  @Path("/erogazione")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Deletes Erogazione by idErogazione", response = EsitoOperazioni.class)
  Response modificaErogazione(@Context HttpServletRequest req, ModificaErogazioneRequest moRequest) throws UtenteException, Exception;


  //////////////////////////////////////////////////////// Avvio lavori / Richiesta erogazione acconto /////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @GET
  @Path("/richiesta")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Finds Dati Riepilogo Richiesta Erogazione per l'attività avvio lavori/Richiesta erogazione acconto", response = EsitoRichiestaErogazioneDTO.class)
  Response getDatiRiepilogoRichiestaErogazione(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto, @QueryParam("codCausale") String codCausale) throws UtenteException, Exception;

  @GET
  @Path("/richiesta/rappresentantiLegali")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Finds Rappresentanti Legali per l'attività avvio lavori/Richiesta erogazione acconto", response = RappresentanteLegaleDTO.class, responseContainer = "List")
  Response getRappresentantiLegali(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;

  @GET
  @Path("/richiesta/delegati")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Finds delegati by idProgetto", response = CodiceDescrizione.class, responseContainer = "List")
  Response getDelegati(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;

  @POST
  @Path("/richiesta")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "crea richiesta erogazione", response = EsitoReportRichiestaErogazioneDTO.class)
  Response creaRichiestaErogazione(@Context HttpServletRequest req, CreaRichiestaErogazioneRequest creaRequest) throws UtenteException, Exception;


  @POST
  @Path("/associaAllegatiARichiestaErogazione")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Finds allegati", response = EsitoAssociaFilesDTO.class)
  Response associaAllegatiARichiestaErogazione(@Context HttpServletRequest req, AssociaFilesRequest associaFilesRequest) throws UtenteException, Exception;

  @GET
  @Path("/getFilesAssociatedRichiestaErogazioneByIdProgetto")
  @Produces(MediaType.APPLICATION_JSON)
  Response getFilesAssociatedRichiestaErogazioneByIdProgetto(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;

  @GET
  @Path("/disassociaAllegatiARichiestaErogazione")
  @Produces(MediaType.APPLICATION_JSON)
  Response disassociaAllegatiARichiestaErogazione(
      @Context HttpServletRequest req,
      @QueryParam("idDocumentoIndex") Long idDocumentoIndex,
      @QueryParam("idErogazione") Long idErogazione,
      @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;

  @GET
  @Path("/inizializzaErogazione")
  @Produces(MediaType.APPLICATION_JSON)
  Response inizializzaErogazione(
      @Context HttpServletRequest req,
      @QueryParam("idProgetto") Long idProgetto) throws UtenteException, Exception;


  @GET
  @Path("/dettaglioVerificaErogazione")
  @Produces(MediaType.APPLICATION_JSON)
  Response getVerificaErogazione(
      @Context HttpServletRequest req,
      @QueryParam("idErogazione") Long idErogazione,
      @QueryParam("idProgetto") Long idProgetto) throws Exception;

  @POST
  @Path("/verificaErogazione")
  @Produces(MediaType.APPLICATION_JSON)
  Response verificaErogazione(
      @Context HttpServletRequest req,
      @RequestBody Map<String, Object> requestData
  ) throws Exception;


}
