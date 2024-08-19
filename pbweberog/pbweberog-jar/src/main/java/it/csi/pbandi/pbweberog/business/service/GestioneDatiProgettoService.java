/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business.service;

import java.io.FileNotFoundException;
import java.io.IOException;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbweberog.dto.CodiceDescrizione;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.datiprogetto.Comune;
import it.csi.pbandi.pbweberog.dto.datiprogetto.DatiProgetto;
import it.csi.pbandi.pbweberog.dto.datiprogetto.DettaglioSoggettoBeneficiarioDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.DettaglioSoggettoProgettoDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.EsitoScaricaDocumentoDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.FileDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.Recapiti;
import it.csi.pbandi.pbweberog.dto.datiprogetto.SedeProgetto;
import it.csi.pbandi.pbweberog.dto.datiprogetto.SedeProgettoDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.SoggettoProgettoDTO;
import it.csi.pbandi.pbweberog.integration.request.DatiSifRequest;
import it.csi.pbandi.pbweberog.integration.request.InserisciSedeInterventoRequest;
import it.csi.pbandi.pbweberog.integration.request.ModificaSedeInterventoRequest;
import it.csi.pbandi.pbweberog.integration.request.RequestCambiaStatoSoggettoProgetto;
import it.csi.pbandi.pbweberog.integration.request.RequestSalvaCup;
import it.csi.pbandi.pbweberog.integration.request.RequestSalvaRecapito;
import it.csi.pbandi.pbweberog.integration.request.RequestSalvaTitoloProgetto;
import it.csi.pbandi.pbweberog.integration.request.SalvaDatiProgettoRequest;
import it.csi.pbandi.pbweberog.pbandisrv.dto.DatiAggiuntiviDTO;

@Path("/datiProgetto")
@Api(value = "datiProgetto")
public interface GestioneDatiProgettoService {

	@GET
	@Path("/lineaInter")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Controlla se la linea intervento è POR-FESR-14-20 ", response = Boolean.class)
	Response isLineaPORFESR1420(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto)
			throws UtenteException, Exception;

	@GET
	@Path("/{idProgetto}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds all dati progetto by idProgetto", response = DatiProgetto.class)
	Response getDatiProgetto(@Context HttpServletRequest req, @PathParam("idProgetto") Long idProgetto)
			throws UtenteException, Exception;

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Saves dati progetto", response = EsitoOperazioni.class)
	Response salvaDatiProgetto(@Context HttpServletRequest req, SalvaDatiProgettoRequest salvaRequest)
			throws UtenteException, Exception;
	
	////////////////////////////////////////// DATI SIF
	////////////////////////////////////////// ////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@POST
	@Path("/salvaDatiSif/{idProgetto}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Saves dati sif", response = EsitoOperazioni.class)
	Response salvaDatiSif(@Context HttpServletRequest req, @PathParam("idProgetto") Long idProgetto, DatiSifRequest request)
			throws UtenteException, Exception;

	////////////////////////////////////////// ALLEGATI
	////////////////////////////////////////// ////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////

	@GET
	@Path("allegati")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds files associated to project by idProgetto", response = FileDTO.class, responseContainer = "List")
	Response getFilesAssociatedProgetto(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto)
			throws UtenteException, Exception;

	@GET
	@Path("/allegati/{idDocumentoIndex}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Documento (to download file bytes by idDocumentoIndex)", response = EsitoScaricaDocumentoDTO.class)
	Response getDocumento(@Context HttpServletRequest req, @PathParam("idDocumentoIndex") Long idDocumentoIndex)
			throws UtenteException, FileNotFoundException, IOException, Exception;

	////////////////////////////////////////// SEDI
	////////////////////////////////////////// ////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	@GET
	@Path("sedi")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds all sedi projetto by idProgetto", response = SedeProgetto.class, responseContainer = "List")
	Response getAllSediProgetto(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto,
			@QueryParam("codiceRuolo") String codiceRuolo) throws UtenteException, Exception;

	@GET
	@Path("sedi/{idSede}/{idProgetto}/dettaglio")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds dettaglio sede by idProgetto and idSede", response = SedeProgettoDTO.class)
	Response getDettaglioSedeProgetto(@Context HttpServletRequest req, @PathParam("idProgetto") Long idProgetto,
			@PathParam("idSede") Long idSede) throws UtenteException, Exception;

	@PUT
	@Path("sede")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Updates sede progetto by idSede", response = EsitoOperazioni.class)
	Response modificaSedeIntervento(@Context HttpServletRequest req, ModificaSedeInterventoRequest moRequest)
			throws UtenteException, Exception;

	@POST
	@Path("sede")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Inserts new sede progetto", response = EsitoOperazioni.class)
	Response inserisciSedeInterventoProgetto(@Context HttpServletRequest req, InserisciSedeInterventoRequest inRequest)
			throws UtenteException, Exception;

	@DELETE
	@Path("sedi/{progrSoggettoProgettoSede}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Deletes sede by progrSoggettoProgettoSede", response = EsitoOperazioni.class)
	Response cancellaSedeInterventoProgetto(@Context HttpServletRequest req,
			@PathParam("progrSoggettoProgettoSede") Long progrSoggettoProgettoSede) throws UtenteException, Exception;

	////////////////////////////////////////// SOGGETTI
	////////////////////////////////////////// ////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////

	@GET
	@Path("soggetti")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds all soggetti projetto by idProgetto", response = SoggettoProgettoDTO.class, responseContainer = "List")
	Response getSoggettiProgetto(@Context HttpServletRequest req, @QueryParam("idU") Long idU,
			@QueryParam("idProgetto") Long idProgetto, @QueryParam("codiceRuolo") String codiceRuolo)
			throws UtenteException, Exception;

	@GET
	@Path("richiestaAccessoNegata")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Richiesta accesso a sistema negata", response = SoggettoProgettoDTO.class, responseContainer = "int")
	Response richiestaAccessoNegata(@Context HttpServletRequest req, @QueryParam("idU") Long idU,
			@QueryParam("progrSoggettoProgetto") Long progrSoggettoProgetto,
			@QueryParam("progrSoggettiCorrelati") Long progrSoggettiCorrelati) throws UtenteException, Exception;

	@PUT
	@Path("soggetto/stato")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Enables or disables Soggetto progetto", response = Boolean.class)
	Response cambiaStatoSoggettoProgetto(@Context HttpServletRequest req, @QueryParam("idU") Long idU,
			RequestCambiaStatoSoggettoProgetto cambiaStatoRequest) throws UtenteException, Exception;

	@GET
	@Path("soggetto/disabilitatoPermanentemente")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Disables Soggetto progetto", response = EsitoOperazioni.class)
	Response disabilitaSoggettoPermanentemente(@Context HttpServletRequest req,
			@QueryParam("idSoggetto") Long idSoggetto, @QueryParam("idProgetto") Long idProgetto)
			throws UtenteException, Exception;

	@GET
	@Path("soggetti/dettaglio")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds dettaglio soggetto (persona fisica) by progrSoggettoProgetto, idTipoSoggettoCorrelato, progrSoggettiCorrelati", response = DettaglioSoggettoProgettoDTO.class)
	Response getDettaglioSoggettoProgetto(@Context HttpServletRequest req, @QueryParam("idU") Long idU,
			@QueryParam("progrSoggettoProgetto") Long progrSoggettoProgetto,
			@QueryParam("idTipoSoggettoCorrelato") Long idTipoSoggettoCorrelato,
			@QueryParam("progrSoggettiCorrelati") Long progrSoggettiCorrelati,
			@QueryParam("codiceFiscale") String codiceFiscale) throws UtenteException, Exception;

//	@GET
//	@Path("soggettiBeneficiari/dettaglio") 
//	@Produces(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "Finds dettaglio soggetto beneficiario (persona giuridica) by progrSoggettoProgetto", response=DettaglioSoggettoBeneficiarioDTO.class)
//	Response getDettaglioSoggettoBeneficiario( @Context HttpServletRequest req, 
//			@QueryParam("progrSoggettoProgetto") Long progrSoggettoProgetto) throws UtenteException, Exception;

	@GET
	@Path("soggettiBeneficiari/dettaglio")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds dettaglio soggetto beneficiario (persona giuridica) by progrSoggettoProgetto", response = DettaglioSoggettoBeneficiarioDTO.class)
	Response getDettaglioSoggettoBeneficiario(@Context HttpServletRequest req, @QueryParam("idU") Long idU,
			@QueryParam("idProgetto") Long idProgetto, @QueryParam("codiceRuolo") String codiceRuolo)
			throws UtenteException, Exception;

	@GET
	@Path("ruoli")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds tipi soggetti correlati (per popolare la combo ruolo)", response = CodiceDescrizione.class, responseContainer = "List")
	Response getTipiSoggettiCorrelati(@Context HttpServletRequest req) throws UtenteException, Exception;

	@GET
	@Path("sedeLegale")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds sede legale", response = String.class)
	Response getSedeLegale(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto)
			throws UtenteException, Exception;

	@GET
	@Path("recapito")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds recapito", response = Recapiti.class)
	Response getRecapito(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto)
			throws UtenteException, Exception;

	@PUT
	@Path("recapito")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Saves recapito", response = EsitoOperazioni.class)
	Response salvaRecapito(@Context HttpServletRequest req, @QueryParam("idU") Long idU,
			RequestSalvaRecapito salvaRequest) throws UtenteException, Exception;
	
	@PUT
	@Path("recapitoPec")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Saves recapito pec", response = EsitoOperazioni.class)
	Response salvaRecapitoPec(@Context HttpServletRequest req, @QueryParam("idU") Long idU,
			RequestSalvaRecapito salvaRequest) throws UtenteException, Exception;

	@PUT
	@Path("cup")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Saves cup", response = EsitoOperazioni.class)
	Response saveCup(@Context HttpServletRequest req, RequestSalvaCup salvaRequest) throws UtenteException, Exception;

	@PUT
	@Path("titoloProgetto")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Saves cup", response = EsitoOperazioni.class)
	Response saveTitoloProgetto(@Context HttpServletRequest req, RequestSalvaTitoloProgetto salvaRequest)
			throws UtenteException, Exception;

	@PUT
	@Path("soggetti/dettaglio")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Saves dettglio soggetto progetto", response = EsitoOperazioni.class) // Nuovo Soggettop
	Response salvaDettaglioSoggettoProgetto(@Context HttpServletRequest req, @QueryParam("idU") Long idU,
			DettaglioSoggettoProgettoDTO dettaglioSoggettoProgetto) throws UtenteException, Exception;

	@PUT
	@Path("soggettiBeneficiari/dettaglio")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Saves dettaglio soggetto beneficiario", response = EsitoOperazioni.class)
	Response salvaSoggettoBeneficiario(@Context HttpServletRequest req,
			DettaglioSoggettoBeneficiarioDTO dettaglioSoggettoBeneficiario) throws UtenteException, Exception;

	//////////////////////////////// SERVIZI PER LE COMBO
	//////////////////////////////// /////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	@GET
	@Path("settoreAttivita")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds settoreAttivita", response = CodiceDescrizione.class, responseContainer = "List")
	Response getSettoreAttivita(@Context HttpServletRequest req) throws UtenteException, Exception;

	@GET
	@Path("attivitaAteco")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds attivitaAteco", response = CodiceDescrizione.class, responseContainer = "List")
	Response getAttivitaAteco(@Context HttpServletRequest req, @QueryParam("idSettoreAttivita") Long idSettoreAttivita)
			throws UtenteException, Exception;

	@GET
	@Path("tipoOperazione")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds tipoOperazione", response = CodiceDescrizione.class, responseContainer = "List")
	Response getTipoOperazione(@Context HttpServletRequest req) throws UtenteException, Exception;

	@GET
	@Path("strumentoAttuativo")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds StrumentoAttuativo", response = CodiceDescrizione.class, responseContainer = "List")
	Response getStrumentoAttuativo(@Context HttpServletRequest req) throws UtenteException, Exception;

	@GET
	@Path("settoreCpt")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds settoreCpt", response = CodiceDescrizione.class, responseContainer = "List")
	Response getSettoreCpt(@Context HttpServletRequest req) throws UtenteException, Exception;

	@GET
	@Path("temaPrioritario")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Tema Prioritario", response = CodiceDescrizione.class, responseContainer = "List")
	Response getTemaPrioritario(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto)
			throws UtenteException, Exception;

	@GET
	@Path("indicatoreRisultatoProgramma")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Indicatore Risultato Programma", response = CodiceDescrizione.class, responseContainer = "List")
	Response getIndicatoreRisultatoProgramma(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto)
			throws UtenteException, Exception;

	@GET
	@Path("indicatoreQsn")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Indicatore Qsn by idProgetto", response = CodiceDescrizione.class, responseContainer = "List")
	Response getIndicatoreQsn(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto)
			throws UtenteException, Exception;

	@GET
	@Path("tipoAiuto")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Tipo Aiuto by idProgetto", response = CodiceDescrizione.class, responseContainer = "List")
	Response getTipoAiuto(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto)
			throws UtenteException, Exception;

	@GET
	@Path("tipoStrumentoProgrammazione")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Tipo Aiuto", response = CodiceDescrizione.class, responseContainer = "List")
	Response getStrumentoProgrammazione(@Context HttpServletRequest req) throws UtenteException, Exception;

	@GET
	@Path("dimensioneTerritoriale")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Dimensione Territoriale", response = CodiceDescrizione.class, responseContainer = "List")
	Response getDimensioneTerritoriale(@Context HttpServletRequest req) throws UtenteException, Exception;

	@GET
	@Path("ProgettoComplesso")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Progetto Complesso", response = CodiceDescrizione.class, responseContainer = "List")
	Response getProgettoComplesso(@Context HttpServletRequest req) throws UtenteException, Exception;

	@GET
	@Path("settoreCipe")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Settore Cipe", response = CodiceDescrizione.class, responseContainer = "List")
	Response getSettoreCipe(@Context HttpServletRequest req) throws UtenteException, Exception;

	@GET
	@Path("sottoSettoreCipe")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Sotto Settore Cipe by idSettoreCipe", response = CodiceDescrizione.class, responseContainer = "List")
	Response getSottoSettoreCipe(@Context HttpServletRequest req, @QueryParam("idSettoreCipe") Long idSettoreCipe)
			throws UtenteException, Exception;

	@GET
	@Path("categoriaCipe")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Categoria Cipe by idSottoSettoreCipe", response = CodiceDescrizione.class, responseContainer = "List")
	Response getCategoriaCipe(@Context HttpServletRequest req,
			@QueryParam("idSottoSettoreCipe") Long idSottoSettoreCipe) throws UtenteException, Exception;

	@GET
	@Path("naturaCipe")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Natura Cipe", response = CodiceDescrizione.class, responseContainer = "List")
	Response getNaturaCipe(@Context HttpServletRequest req) throws UtenteException, Exception;

	@GET
	@Path("tipologiaCipe")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Tipologia Cipe by idNaturaCipe", response = CodiceDescrizione.class, responseContainer = "List")
	Response getTipologiaCipe(@Context HttpServletRequest req, @QueryParam("idNaturaCipe") Long idNaturaCipe)
			throws UtenteException, Exception;

	// PBANDI-2734
	@GET
	@Path("tipoProceduraOriginaria")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Tipo Procedura Originaria", response = CodiceDescrizione.class, responseContainer = "List")
	Response getTipoProceduraOriginaria(@Context HttpServletRequest req) throws UtenteException, Exception;

	@GET
	@Path("nazioni")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds nazioni", response = CodiceDescrizione.class, responseContainer = "List")
	Response getNazioni(@Context HttpServletRequest req) throws UtenteException, Exception;

	@GET
	@Path("regioni")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds regioni", response = CodiceDescrizione.class, responseContainer = "List")
	Response getRegioni(@Context HttpServletRequest req) throws UtenteException, Exception;

	@GET
	@Path("province")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds province", response = CodiceDescrizione.class, responseContainer = "List")
	Response getProvince(@Context HttpServletRequest req, @QueryParam("idRegione") Long idRegione)
			throws UtenteException, Exception;

	@GET
	@Path("comuniEsterni")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds comuni esterni", response = CodiceDescrizione.class, responseContainer = "List")
	Response getComuniEsterni(@Context HttpServletRequest req, @QueryParam("idNazione") Long idNazione)
			throws UtenteException, Exception;

	@GET
	@Path("comuni")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds comuni di italia", response = Comune.class, responseContainer = "List")
	Response getComuni(@Context HttpServletRequest req, @QueryParam("idProvincia") Long idProvincia)
			throws UtenteException, Exception;

	@GET
	@Path("isFinpiemonte")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Verifica se il progetto è regione o finpiemonte")
	Response isFinpiemonte(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto)
			throws UtenteException, Exception;

	//////////////////////////////// SERVIZI PER LE COMBO 1420
	//////////////////////////////// /////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	@GET
	@Path("1420/obiettivoTematico")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds obiettivoTematico", response = CodiceDescrizione.class, responseContainer = "List")
	Response getObiettivoTematico(@Context HttpServletRequest req) throws UtenteException, Exception;

	@GET
	@Path("1420/classificazioneRA")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds classificazioneRA", response = CodiceDescrizione.class, responseContainer = "List")
	Response getClassificazioneRA(@Context HttpServletRequest req) throws UtenteException, Exception;

	@GET
	@Path("1420/grandeProgetto")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Grande Progetto", response = CodiceDescrizione.class, responseContainer = "List")
	Response getGrandeProgetto(@Context HttpServletRequest req) throws UtenteException, Exception;

	@GET
	@Path("1420/prioritaQsn")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds PrioritaQsn", response = CodiceDescrizione.class, responseContainer = "List")
	Response getPrioritaQsn(@Context HttpServletRequest req,
			@QueryParam("idLineaInterventoAsse") Long idLineaInterventoAsse) throws UtenteException, Exception;

	@GET
	@Path("1420/obiettivoGeneraleQsn")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds PrioritaQsn", response = CodiceDescrizione.class, responseContainer = "List")
	Response getObiettivoGeneraleQsn(@Context HttpServletRequest req, @QueryParam("idPriorityQsn") Long idPriorityQsn)
			throws UtenteException, Exception;

	@GET
	@Path("1420/obiettivoSpecificoQsn")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds PrioritaQsn", response = CodiceDescrizione.class, responseContainer = "List")
	Response getObiettivoSpecificoQsn(@Context HttpServletRequest req,
			@QueryParam("idObiettivoGenerale") Long idObiettivoGenerale) throws UtenteException, Exception;

	@GET
	@Path("1420/dimensioneTerritoriale")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds Dimensione Territoriale", response = CodiceDescrizione.class, responseContainer = "List")
	Response getDimensioneTerritoriale1420(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto)
			throws UtenteException, Exception;

	@GET
	@Path("leggiEmailBeneficiarioPF")
	@Produces(MediaType.APPLICATION_JSON)
	Response leggiEmailBeneficiarioPF(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto,
			@QueryParam("idSoggettoBen") Long idSoggettoBen) throws UtenteException, Exception;

	@GET
	@Path("salvaEmailBeneficiarioPF")
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaEmailBeneficiarioPF(@Context HttpServletRequest req, @QueryParam("idProgetto") Long idProgetto,
			@QueryParam("idSoggettoBen") Long idSoggettoBen, @QueryParam("email") String email)
			throws UtenteException, Exception;

	@POST
	@Path("associaAllegatiAProgetto")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response associaAllegatiAProgetto(AssociaFilesRequest associaFilesRequest, @Context HttpServletRequest request)
			throws InvalidParameterException, Exception;

	@GET
	@Path("disassociaAllegatoProgetto")
	@Produces(MediaType.APPLICATION_JSON)
	Response disassociaAllegatoProgetto(@QueryParam("idDocumentoIndex") Long idDocumentoIndex,
			@QueryParam("idProgetto") Long idProgetto, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

//////////////////////////////////////////DATI AGGIUNTIVI
////////////////////////////////////////// ////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////
	@GET
	@Path("datiAggiuntivi/{idProgetto}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Finds all dati aggiuntivi by idProgetto", response = DatiAggiuntiviDTO.class)
	Response getDatiAggiuntivi(@Context HttpServletRequest req, @PathParam("idProgetto") Long idProgetto)
			throws UtenteException, Exception;
}
