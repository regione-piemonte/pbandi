/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.business.service;

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

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.CausaleDiErogazioneAssociataDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.DatiBandoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.LineaDiInterventiDaAssociareDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.LineaDiInterventiDaModificareDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.gestionebackoffice.GestioneBackOfficeException;

@Path("/configurazonebando")
public interface ConfigurazioneBandoService {

	@GET
	@Path("/findnormative")
	@Produces({ "application/json" })
	public Response findNormative(@QueryParam("idU") Long idU, @Context HttpServletRequest httpRequest)
			throws GestioneBackOfficeException, SystemException, UnrecoverableException, CSIException;

	@GET
	@Path("/findnormativepost2016")
	@Produces({ "application/json" })
	public Response findNormativePost2016(@QueryParam("idU") Long idU, @Context HttpServletRequest httpRequest)
			throws GestioneBackOfficeException, SystemException, UnrecoverableException, CSIException;

	@GET
	@Path("ricercabandi")
	@Produces(MediaType.APPLICATION_JSON)
	Response ricercaBandi(@DefaultValue("0") @QueryParam("idU") Long idU, @QueryParam("titoloBando") String titoloBando,
			@QueryParam("nomeBandoLinea") String nomeBandoLinea, @QueryParam("normativa") String normativa,
			@QueryParam("idLineaDiIntervento") Long idLineaDiIntervento, // corrisponde all'id della normativa
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/cercadatibando")
	@Produces(MediaType.APPLICATION_JSON)
	Response datibando(@DefaultValue("0") @QueryParam("idU") Long idU, @QueryParam("titoloBando") String titoloBando,
			@QueryParam("nomeBandoLinea") String nomeBandoLinea, @QueryParam("normativa") String normativa,
			@QueryParam("idLineaDiIntervento") Long idLineaDiIntervento, @QueryParam("idBando") Long idBando,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("getIdProcessoByProgrBandoLineaIntervento")
	@Produces(MediaType.APPLICATION_JSON)
	Response getIdProcessoByProgrBandoLineaIntervento(
			@QueryParam("progrLineaLineaIntervento") Long progrLineaLineaIntervento, @Context HttpServletRequest req)
			throws Exception;

	// INIZIO TAB DATIBANDO

	@GET
	@Path("configurabando/datibando/codiceintesaistituzionale")
	@Produces(MediaType.APPLICATION_JSON)
	Response datibandoCodiceIntesaIstituzionale(@DefaultValue("0") @QueryParam("idU") Long idU,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/datibando/settorecipe")
	@Produces(MediaType.APPLICATION_JSON)
	Response datibandoSettoreCipe(@DefaultValue("0") @QueryParam("idU") Long idU, @Context HttpServletRequest req)
			throws Exception;

	@GET
	@Path("configurabando/datibando/sottoSettorecipe")
	@Produces(MediaType.APPLICATION_JSON)
	Response datibandoSottoSettoreCipe(@DefaultValue("0") @QueryParam("idU") Long idU,
			@DefaultValue("0") @QueryParam("idSettoreCipe") Long idSettoreCipe, @Context HttpServletRequest req)
			throws Exception;

	@GET
	@Path("configurabando/datibando/naturacipe")
	@Produces(MediaType.APPLICATION_JSON)
	Response datibandoNaturaCipe(@DefaultValue("0") @QueryParam("idU") Long idU, @Context HttpServletRequest req)
			throws Exception;

	@GET
	@Path("configurabando/datibando/materiadiriferimento")
	@Produces(MediaType.APPLICATION_JSON)
	Response datibandoMateriaDiRiferimento(@QueryParam("idU") Long idU, @Context HttpServletRequest req)
			throws Exception;

	@GET
	@Path("configurabando/datibando/tipologiadiattivazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response datibandoTipologiadiattivazione(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idLineaDiIntervento") Long idLineaDiIntervento, // corrisponde all'id della normativa
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/datibando/tipooperazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response datibandoTipoOperazione(@DefaultValue("0") @QueryParam("idU") Long idU, @Context HttpServletRequest req)
			throws Exception;

	@GET
	@Path("configurabando/datibando/settorecpt")
	@Produces(MediaType.APPLICATION_JSON)
	Response datibandoSettoreCPT(@DefaultValue("0") @QueryParam("idU") Long idU, @Context HttpServletRequest req)
			throws Exception;

	@POST
	@Path("configurabando/datibando/salvaDatiBando")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response salvaDatiBando(@Context HttpServletRequest req, DatiBandoDTO datiBandoReq)
			throws UtenteException, Exception;

	// FINO TAB DATIBANDO

	// FINO TAB LINEADIINTERVENTO
	@GET
	@Path("configurabando/lineadiintervento/normative")
	@Produces(MediaType.APPLICATION_JSON)
	Response lineeDiInterventoNormative(@DefaultValue("0") @QueryParam("idU") Long idU, @Context HttpServletRequest req)
			throws Exception;

	@GET
	@Path("configurabando/lineadiintervento/asse")
	@Produces(MediaType.APPLICATION_JSON)
	Response lineeDiInterventoAsse(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idLineaDiIntervento") Long idLineaDiIntervento, // corrisponde all'id della normativa
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/lineadiintervento/misura")
	@Produces(MediaType.APPLICATION_JSON)
	Response lineeDiInterventoMiusra(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idLineaPadre") Long idLineaPadreAsse, @Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/lineadiintervento/linea")
	@Produces(MediaType.APPLICATION_JSON)
	Response lineeDiInterventoLinea(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idLineaPadreMisura") Long idLineaPadreMisura, @Context HttpServletRequest req)
			throws Exception;

	@GET
	@Path("configurabando/lineadiintervento/categoriacipe")
	@Produces(MediaType.APPLICATION_JSON)
	Response lineeDiInterventoCategoriaCipe(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idSottoSettoreCipe") Long idSottoSettoreCipe, @Context HttpServletRequest req)
			throws Exception;

	@GET
	@Path("configurabando/lineadiintervento/tipologiacipe")
	@Produces(MediaType.APPLICATION_JSON)
	Response lineeDiInterventoTipologiaCipe(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idNaturaCipe") String idNaturaCipe, @Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/lineadiintervento/obiettivotematico")
	@Produces(MediaType.APPLICATION_JSON)
	Response lineeDiInterventoObiettivoTematico(@DefaultValue("0") @QueryParam("idU") Long idU,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/lineadiintervento/classificazionera")
	@Produces(MediaType.APPLICATION_JSON)
	Response lineeDiInterventoClassificazioneRA(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idObiettivoTematico") String idObiettivoTematico, @Context HttpServletRequest req)
			throws Exception;

	@GET
	@Path("configurabando/lineadiintervento/processointerno")
	@Produces(MediaType.APPLICATION_JSON)
	Response lineeDiInterventoProcessoInterno(@DefaultValue("0") @QueryParam("idU") Long idU,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/lineadiintervento/elencobandisif")
	@Produces(MediaType.APPLICATION_JSON)
	Response lineeDiInterventoElencoBandiSIF(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("progrBandoLinea") Long progrBandoLinea, @Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/lineadiintervento/livelloIstruzione")
	@Produces(MediaType.APPLICATION_JSON)
	Response lineeDiInterventoLivelloIstruzione(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idLineaDiIntervento") Long idLineaDiIntervento, // corrisponde all'id della normativa
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/lineadiintervento/tipolocalizzazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response lineeDiInterventoTipoLocalizzazione(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idLineaDiIntervento") Long idLineaDiIntervento, // corrisponde all'id della normativa
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/lineadiintervento/progettocomplesso")
	@Produces(MediaType.APPLICATION_JSON)
	Response lineeDiInterventoProgettoComplesso(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idLineaDiIntervento") Long idLineaDiIntervento, // corrisponde all'id della normativa
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/lineadiintervento/met")
	@Produces(MediaType.APPLICATION_JSON)
	Response lineeDiInterventoClassificazioneMeccanismiErogazioneTerritoriale(
			@DefaultValue("0") @QueryParam("idU") Long idU, @QueryParam("idLineaDiIntervento") Long idLineaDiIntervento, // corrisponde
																															// all'id
																															// della
																															// normativa
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/lineadiintervento/prioritaqsn")
	@Produces(MediaType.APPLICATION_JSON)
	Response lineeDiInterventoPrioritaQSN(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idAsse") String idAsse, @Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/lineadiintervento/obiettivogeneraleqsn")
	@Produces(MediaType.APPLICATION_JSON)
	Response lineeDiInterventoObiettivoGeneraleQSN(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idPrioritaQsn") String idPrioritaQsn, @Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/lineadiintervento/obiettivospecificoqsn")
	@Produces(MediaType.APPLICATION_JSON)
	Response lineeDiInterventoObiettivoSpecificoQSN(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idObiettivoGenQsn") String idObiettivoGenQsn, @Context HttpServletRequest req)
			throws Exception;

	@GET
	@Path("configurabando/lineadiintervento/lineeassociate")
	@Produces(MediaType.APPLICATION_JSON)
	Response lineeDiInterventoAssociate(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idBando") Long idBando, @Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/lineadiintervento/modificalineadiintervento")
	@Produces(MediaType.APPLICATION_JSON)
	Response modificaLineaDiIntervento(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento, @Context HttpServletRequest req)
			throws Exception;

	@POST
	@Path("configurabando/lineadiintervento/associalineaintervento")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response associaLineaIntervento(@Context HttpServletRequest req,
			LineaDiInterventiDaAssociareDTO dettaglioLineaDiIntervento) throws UtenteException, Exception;

	@GET
	@Path("configurabando/lineadiintervento/eliminalineadiintervento")
	@Produces(MediaType.APPLICATION_JSON)
	Response eliminaLineaDiIntervento(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("progrBandoLineaIntervento") Long progrBandoLineaIntervento, @Context HttpServletRequest req)
			throws Exception;

	@POST
	@Path("configurabando/lineadiintervento/salvamodifiche")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response lineaDiInterventoSalvaModifiche(@Context HttpServletRequest req,
			LineaDiInterventiDaModificareDTO dettaglioLineaDiIntervento) throws UtenteException, Exception;

	// FINE TAB LINEA DI INTERVENTO

	// INIZO TAB VOCIDISPESA
	@GET
	@Path("configurabando/vocidispesa/vocedispesa")
	@Produces(MediaType.APPLICATION_JSON)
	Response vociDiSpesaVoceDiSpesa(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idVoceDiSpesaPadre") Long idVoceDiSpesaPadre, @QueryParam("idBando") Long idBando,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/vocidispesa/vocedispesaMonitoraggio")
	@Produces(MediaType.APPLICATION_JSON)
	Response vociDiSpesaVoceDiSpesaMonitoraggio(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idTipoOperazione") Long idTipoOperazione, @QueryParam("idNaturaCipe") Long idNaturaCipe,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/vocidispesa/sottovoce")
	@Produces(MediaType.APPLICATION_JSON)
	Response vociDiSpesaVoceSottovoce(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idVoceDiSpesaPadre") Long idVoceDiSpesaPadre, @QueryParam("idNaturaCipe") Long idBando,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/vocidispesa/vociassociate")
	@Produces(MediaType.APPLICATION_JSON)
	Response vociDiSpesaVociAssociate(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idBando") Long idBando, @Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/vocidispesa/eliminavoceassociata")
	@Produces(MediaType.APPLICATION_JSON)
	Response vociDiSpesaEliminaVoceAssociata(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idBando") Long idBando, @QueryParam("idVoceDiSpesa") Long idVoceDiSpesa,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/vocidispesa/associavocedispesa")
	@Produces(MediaType.APPLICATION_JSON)
	Response vociDiSpesaVoceAssociaVoce(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idBando") Long idBando, @QueryParam("idVoceDiSpesa") Long idVoceDiSpesa,
			@QueryParam("descVoceDiSpesa") String descVoceDiSpesa, @QueryParam("idSottovoce") Long idSottovoce,
			@QueryParam("descSottovoce") String descSottovoce,
			@QueryParam("idVoceDiSpesaMonit") Long idVoceDiSpesaMonit,
			@QueryParam("codTipoVoceDiSpesaMacro") String codTipoVoceDiSpesaMacro,
			@QueryParam("codTipoVoceDiSpesaMicro") String codTipoVoceDiSpesaMicro,
			@QueryParam("flagSpeseGestioneMacro") String flagSpeseGestioneMacro,
			@QueryParam("flagSpeseGestioneMicro") String flagSpeseGestioneMicro,
			@Context HttpServletRequest req)
			throws Exception;

	// FINE TAB VOCIDISPESA

	// INZIO TAB DATIAGGIUNTIVI
	@GET
	@Path("configurabando/datiaggiuntivi/modalitadiagevolazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response datiaggiuntiviModalitaDiAgevolazione(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idLineaDiIntervento") Long idLineaDiIntervento, // corrisponde all'id della normativa
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/datiaggiuntivi/modalitaassociata")
	@Produces(MediaType.APPLICATION_JSON)
	Response datiaggiuntiviModalitaAssociata(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idBando") Long idBando, @Context HttpServletRequest req) throws Exception;
	
	@GET
	@Path("configurabando/datiaggiuntivi/modalitaAssociata")
	@Produces(MediaType.APPLICATION_JSON)
	Response datiaggiuntiviModalitaAssociata(
			@QueryParam("idBando") Long idBando, 
			@Context HttpServletRequest req) throws Exception;
	
	
	@GET
	@Path("configurabando/datiaggiuntivi/associamodalitadiagevolazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response datiaggiuntiviAssociaModalitaDiAgevolazione(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idBando") Long idBando, @QueryParam("idModalitaDiAgevolazione") Long idModalitaDiAgevolazione,
			@QueryParam("importoAgevolato") Double importoAgevolato,
			@QueryParam("massimoImportoAgevolato") Double massimoImportoAgevolato,
			@QueryParam("flagLiquidazione") String flagLiquidazione, 
			@QueryParam("periodoStabilita") Double periodoStabilita,
			@QueryParam("importoDaErogare") Double importoDaErogare,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/datiaggiuntivi/eliminaModalitadiagevolazioneAssociata")
	@Produces(MediaType.APPLICATION_JSON)
	Response datiaggiuntiviEliminaModalitaDiAgevolazioneAssociata(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idBando") Long idBando, @QueryParam("idModalitaDiAgevolazione") Long idModalitaDiAgevolazione,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/datiaggiuntivi/tipotrattamento")
	@Produces(MediaType.APPLICATION_JSON)
	Response findTipoTrattamento(@QueryParam("idU") Long idU, @Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/datiaggiuntivi/tipotrattamentoassociato")
	@Produces(MediaType.APPLICATION_JSON)
	Response datiaggiuntiviTipoTrattamentoAssociato(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idBando") Long idBando, @Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/datiaggiuntivi/associatipotrattamento")
	@Produces(MediaType.APPLICATION_JSON)
	Response datiaggiuntiviAssociaTipoTrattamento(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idBando") Long idBando, @QueryParam("idTipoDiTrattamento") Long idTipoDiTrattamento,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/datiaggiuntivi/eliminatipotrattamentoassociato")
	@Produces(MediaType.APPLICATION_JSON)
	Response datiaggiuntiviEliminaTipoTrattamentoAssociato(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idBando") Long idBando, @QueryParam("idTipoTrattamento") Long idTipoTrattamento,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/datiaggiuntivi/tipotrattamentoassociatopredefinito")
	@Produces(MediaType.APPLICATION_JSON)
	Response datiaggiuntiviTipoTrattamentoAssociatoPredefinito(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idBando") Long idBando, @QueryParam("idTipoTrattamento") Long idTipoTrattamento,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/datiaggiuntivi/soggettifinanziatori")
	@Produces(MediaType.APPLICATION_JSON)
	Response datiaggiuntiviSoggettiFinanziatori(@DefaultValue("0") @QueryParam("idU") Long idU,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/datiaggiuntivi/associasoggettofinanziatore")
	@Produces(MediaType.APPLICATION_JSON)
	Response datiaggiuntiviAssociaSoggettoFinanziatore(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idBando") Long idBando, @QueryParam("soggettoFinanziatore") String soggettoFinanziatore,
			@QueryParam("idSoggettoFinanziatore") Long idSoggettoFinanziatore,
			@QueryParam("percentuale") Double percentuale, @Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/datiaggiuntivi/eliminasoggettofinanziatore")
	@Produces(MediaType.APPLICATION_JSON)
	Response datiaggiuntiviEliminaSoggettoFinanziatore(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idBando") Long idBando, @QueryParam("idSoggettoFinanziatore") Long idSoggettoFinanziatore,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/datiaggiuntivi/soggettifinanziatoreAssociato")
	@Produces(MediaType.APPLICATION_JSON)
	Response datiaggiuntiviSoggettiFinanziatoreAssociato(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idBando") Long idBando, @Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/datiaggiuntivi/causaleerogazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response datiaggiuntiviCausaleErogazione(@DefaultValue("0") @QueryParam("idU") Long idU,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/datiaggiuntivi/causaleerogazioneassociata")
	@Produces(MediaType.APPLICATION_JSON)
	Response datiaggiuntiviCausaleErogazioneAssociata(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idBando") Long idBando, @Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/datiaggiuntivi/dimensioneimpresa")
	@Produces(MediaType.APPLICATION_JSON)
	Response datiaggiuntiviDimensioneImpresa(@DefaultValue("0") @QueryParam("idU") Long idU,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/datiaggiuntivi/formagiuridica")
	@Produces(MediaType.APPLICATION_JSON)
	Response datiaggiuntiviFormaGiuridica(@DefaultValue("0") @QueryParam("idU") Long idU,
			@Context HttpServletRequest req) throws Exception;

	@POST
	@Path("configurabando/datiaggiuntivi/associacasuale")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response datiAggiuntiviAssociaCausale(@Context HttpServletRequest req, @QueryParam("idU") Long idU,
			CausaleDiErogazioneAssociataDTO causaleDiErogazione) throws UtenteException, Exception;

	@GET
	@Path("configurabando/datiaggiuntivi/eliminacausaleerogazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response datiaggiuntivEliminaCausaleErogazione(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("progrBandoCausaleErogaz") Long progrBandoCausaleErogaz, @Context HttpServletRequest req)
			throws Exception;

	@GET
	@Path("configurabando/datiaggiuntivi/tipoindicatore")
	@Produces(MediaType.APPLICATION_JSON)
	Response datiaggiuntiviTipoIndicatore(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idLineaDiIntervento") Long idLineaDiIntervento, // corrisponde all'id della normativa
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/datiaggiuntivi/indicatore")
	@Produces(MediaType.APPLICATION_JSON)
	Response datiaggiuntiviIndicatore(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idTipoIndicatore") Long idTipoIndicatore, @Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/datiaggiuntivi/indicatoreassociato")
	@Produces(MediaType.APPLICATION_JSON)
	Response datiaggiuntiviIndicatoreAssociato(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idBando") Long idBando, @Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/datiaggiuntivi/associaindicatore")
	@Produces(MediaType.APPLICATION_JSON)
	Response datiaggiuntiviAssociaIndicatore(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idBando") Long idBando, @QueryParam("idIndicatore") Long idIndicatore,
			@QueryParam("infoIniziale") String infoIniziale,
			@QueryParam("infoFinale") String infoFinale,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/datiaggiuntivi/eliminaindicatore")
	@Produces(MediaType.APPLICATION_JSON)
	Response datiaggiuntiviEliminaIndicatore(@DefaultValue("0") @QueryParam("idU") Long idU,
			@QueryParam("idBando") Long idBando, @QueryParam("idIndicatore") Long idIndicatore,
			@Context HttpServletRequest req) throws Exception;

	@GET
	@Path("configurabando/datiaggiuntivi/modalitaAgevErogazione")
	@Produces(MediaType.APPLICATION_JSON)
	Response getModalitaAgevErogazione(@Context HttpServletRequest req) throws Exception;

	// FINE TAB DATIAGGIUNTIVI

//	@GET
//	@Path("/modelloAffidamentoHtml") 
//	@Produces(MediaType.APPLICATION_JSON)
//	Response getModelloCheckListAffidamentoHtml( @Context HttpServletRequest req, 
//												@QueryParam("idProgetto") Long idProgetto,
//												@QueryParam("idAffidamento") Long idAffidamento,
//												@QueryParam("soggettoControllore") String soggettoControllore,
//												@QueryParam("codTipoChecklist") String codTipoChecklist) 
//			throws UtenteException, Exception;
}
