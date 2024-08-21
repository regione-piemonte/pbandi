/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.manager;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.*;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.util.beanlocatorfactory.ServiceBeanLocator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.incrementer.AbstractDataFieldMaxValueIncrementer;
/**
 * La classe SequenceManager memorizza in una struttura dati per le tabelle del db interessate
 * il legame tra il campo della tabella (rappresentata da un VO)
 * e la sequence che serve a incrementarlo 
 * @author 70228
 *
 */
public class SequenceManager {

	@Autowired
	public SequenceManager(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	DataSource dataSource;
	
	@Autowired
	private BeanUtil beanUtil;
	
	@Autowired
	private LoggerUtil logger;

	public void setBeanUtil(BeanUtil beanUtil) {
		this.beanUtil = beanUtil;
	}

	public BeanUtil getBeanUtil() {
		return beanUtil;
	}

	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}
	
	public LoggerUtil getLogger() {
		return logger;
	}
	

	
	
	private  static Map<String, String[]> mapVoSequenced=new HashMap<String, String[]>();
	static {
		mapVoSequenced.put(PbandiCRegolaVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_C_REGOLA", "idRegola"});
		mapVoSequenced.put(PbandiDAreaScientificaVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_D_AREA_SCIENTIFICA", "idAreaScientifica"});
		mapVoSequenced.put(PbandiDVoceDiSpesaVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_D_VOCE_DI_SPESA", "idVoceDiSpesa"});
		mapVoSequenced.put(PbandiRBandoCausaleErogazVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_R_BANDO_CAUSALE_ERO", "progrBandoCausaleErogaz"});
		mapVoSequenced.put(PbandiRBandoLineaEnteCompVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_R_BANDO_LIN_ENTE_CO", "progrBandoLineaEnteComp"});
		mapVoSequenced.put(PbandiRBandoLineaInterventVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_R_BANDO_LINEA_INTER", "progrBandoLineaIntervento"});
		mapVoSequenced.put(PbandiREnteGiuridicoSedeVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_R_ENTE_GIURID_SEDE", "progrEnteGiuridicoSede"});
		mapVoSequenced.put(PbandiRFornitoreQualificaVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_R_FORNITORE_QUALIF", "progrFornitoreQualifica"});
		mapVoSequenced.put(PbandiRPagQuotParteDocSpVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_R_PAG_QU_PAR_DOC_SP", "progrPagQuotParteDocSp"});
		mapVoSequenced.put(PbandiRProgSoggFinanziatVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_R_PROG_SOGG_FINANZI", "progrProgSoggFinanziat"});
		mapVoSequenced.put(PbandiRSoggettiCorrelatiVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_R_SOGG_CORRELATI", "progrSoggettiCorrelati"});
		mapVoSequenced.put(PbandiRSoggettoDomandaSedeVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_R_SOGG_DOMANDA_SEDE", "progrSoggettoDomandaSede"});
		mapVoSequenced.put(PbandiRSoggProgettoSedeVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_R_SOGG_PROG_SEDE", "progrSoggettoProgettoSede"});
		mapVoSequenced.put(PbandiRSoggettoDomandaVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_R_SOGGETTO_DOMANDA", "progrSoggettoDomanda"});
		mapVoSequenced.put(PbandiRSoggettoProgettoVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_R_SOGGETTO_PROGETTO", "progrSoggettoProgetto"});
		mapVoSequenced.put(PbandiTAccessoIrideVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_ACCESSO_IRIDE", "idAccessoIride"});
		mapVoSequenced.put(PbandiTAgenziaVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_AGENZIA", "idAgenzia"});
		mapVoSequenced.put(PbandiTAppaltoVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_APPALTO", "idAppalto"});
		mapVoSequenced.put(PbandiTAttoLiquidazioneVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_ATTO_LIQUIDAZIONE", "idAttoLiquidazione"});
		mapVoSequenced.put(PbandiRLiquidazioneVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_R_LIQUIDAZIONE", "progrLiquidazione"});
		mapVoSequenced.put(PbandiTBandoVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_BANDO", "idBando"});
		mapVoSequenced.put(PbandiTBeneficiarioBilancioVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_BENEFICIARIO_BILA", "idBeneficiarioBilancio"});
		mapVoSequenced.put(PbandiTCaricaMassDocSpesaVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_CARICA_MASS_DOC_S", "idCaricaMassDocSpesa"});
		mapVoSequenced.put(PbandiTChecklistVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_CHECKLIST", "idChecklist"});
		mapVoSequenced.put(PbandiTComunicazFineProgVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_COMUNIC_FINE_PROG", "idComunicazFineProg"});
		mapVoSequenced.put(PbandiTConsuntivoEntrataVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_CONS_ENTRATA", "idConsuntivoEntrata"});
		mapVoSequenced.put(PbandiTContoEconomicoVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_CONTO_ECONOMICO", "idContoEconomico"});
		mapVoSequenced.put(PbandiTCoordinatoreVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_COORDINATORE", "idCoordinatore"});
		mapVoSequenced.put(PbandiTCspProgSedeIntervVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_CSP_PROG_SEDE_INT", "idCspProgSedeInterv"});
		mapVoSequenced.put(PbandiTCspProgettoVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_CSP_PROGETTO", "idCspProgetto"});
		mapVoSequenced.put(PbandiTCspSoggettoVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_CSP_SOGGETTO", "idCspSoggetto"});
		mapVoSequenced.put(PbandiTCspSoggRuoloEnteVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_CSP_SOGG_RUOLO_EN", "idCspSoggRuoloEnte"});
		mapVoSequenced.put(PbandiTDatiPagamentoAttoVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_DATI_PAGAMENTO_AT", "idDatiPagamentoAtto"});
		mapVoSequenced.put(PbandiTDettPropostaCertifVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_DETT_PROPOSTA_CER", "idDettPropostaCertif"});
		mapVoSequenced.put(PbandiTDichiarazioneSpesaVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_DICHIARAZIONE_SPE", "idDichiarazioneSpesa"});
		mapVoSequenced.put(PbandiTDocumentoVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_DOCUMENTO", "idDocumento"});
		mapVoSequenced.put(PbandiTDocumentoIndexVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_DOCUMENTO_INDEX", "idDocumentoIndex"});
		mapVoSequenced.put(PbandiTDocumentoDiSpesaVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_DOCUMENTO_SPESA", "idDocumentoDiSpesa"});
		mapVoSequenced.put(PbandiTDomandaVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_DOMANDA", "idDomanda"});
		mapVoSequenced.put(PbandiTEnteCompetenzaVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_ENTE_COMPETENZA", "idEnteCompetenza"});
		mapVoSequenced.put(PbandiTEnteGiuridicoVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_ENTE_GIURIDICO", "idEnteGiuridico"});
		mapVoSequenced.put(PbandiTErogazioneVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_EROGAZIONE", "idErogazione"});
		mapVoSequenced.put(PbandiTEstremiBancariVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_ESTREMI_BANCA", "idEstremiBancari"});
		mapVoSequenced.put(PbandiTFideiussioneVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_FIDEIUSSIONE", "idFideiussione"});
		mapVoSequenced.put(PbandiTFornitoreVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_FORNITORE", "idFornitore"});
		mapVoSequenced.put(PbandiTGeoRiferimentoVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_GEO_RIFERIMENTO", "idGeoRiferimento"});
		mapVoSequenced.put(PbandiTIndirizzoVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_INDIRIZZO", "idIndirizzo"});
		mapVoSequenced.put(PbandiTIrregolaritaVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_IRREGOLARITA", "idIrregolarita"});
		mapVoSequenced.put(PbandiTIrregolaritaProvvVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_IRREG_PROVV", "idIrregolaritaProvv"});
		mapVoSequenced.put(PbandiTIscrizioneVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_ISCRIZIONE", "idIscrizione"});
		mapVoSequenced.put(PbandiTPagamentoVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_PAGAMENTO", "idPagamento"});
		mapVoSequenced.put(PbandiTPersonaFisicaVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_PERSONA_FISICA", "idPersonaFisica"});
		mapVoSequenced.put(PbandiTPraticaIstruttoriaVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_PRATICA_ISTRUT", "idPraticaIstruttoria"});
		mapVoSequenced.put(PbandiTProceduraAggiudicazVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_PROCEDURA_AGGIUDI", "idProceduraAggiudicaz"});
		mapVoSequenced.put(PbandiTProgettoVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_PROGETTO", "idProgetto"});
		mapVoSequenced.put(PbandiTPropostaCertificazVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_PROPOSTA_CERTIF", "idPropostaCertificaz"});
		mapVoSequenced.put(PbandiTQuadroPrevisionaleVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_QUADRO_PREVISIONA", "idQuadroPrevisionale"});
		mapVoSequenced.put(PbandiTQuotaParteDocSpesaVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_QUOT_PARTE_DOC_SP", "idQuotaParteDocSpesa"});
		mapVoSequenced.put(PbandiTRaggruppamentoVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_RAGGRUPPAMENTO", "idRaggruppamento"});
		mapVoSequenced.put(PbandiTRecapitiVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_RECAPITI", "idRecapiti"});
		mapVoSequenced.put(PbandiTRettificaDiSpesaVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_RETTIFICA_SPESA", "idRettificaDiSpesa"});
		mapVoSequenced.put(PbandiTRecuperoVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_RECUPERO", "idRecupero"});
		mapVoSequenced.put(PbandiTRevocaVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_REVOCA_RINUNCIA", "idRevoca"});
		mapVoSequenced.put(PbandiTRibassoAstaVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_RIBASSO_ASTA", "idRibassoAsta"});
		mapVoSequenced.put(PbandiTRichiestaErogazioneVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_RICHIESTA_EROGAZ", "idRichiestaErogazione"});
		mapVoSequenced.put(PbandiTRigoContoEconomicoVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_RIGO_CONTO_ECONOM", "idRigoContoEconomico"});
		mapVoSequenced.put(PbandiTRigoQuadroPrevisioVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_RIGO_QUADRO_PREVI", "idRigoQuadroPrevisio"});
		mapVoSequenced.put(PbandiTRinunciaVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_RINUNCIA", "idRinuncia"});
		mapVoSequenced.put(PbandiTScartiCaricaMassDsVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_CARICA_MASS_DOC_S", "idScartiCaricaMassDs"});
		mapVoSequenced.put(PbandiTSedeVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_SEDE", "idSede"});
		mapVoSequenced.put(PbandiTSoggettoVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_SOGGETTO", "idSoggetto"});
		mapVoSequenced.put(PbandiTSpesaPreventivataVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_SPESA_PREVENTIV", "idSpesaPreventivata"});
		mapVoSequenced.put(PbandiTStoricoAccessoVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_STORICO_ACCESSO", "idStoricoAccesso"});
		mapVoSequenced.put(PbandiTStoricoMigrazioneVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_STORICO_MIGRAZION", "idStoricoMigrazione"});
		mapVoSequenced.put(PbandiTTracciamentoEntitaVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_TRAC_ENTITA", "idTracciamentoEntita"});
		mapVoSequenced.put(PbandiTTracciamentoVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_TRACCIAMENTO", "idTracciamento"});
		mapVoSequenced.put(PbandiTTemplateVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_TEMPLATE", "idTemplate"});
		mapVoSequenced.put(PbandiTUtenteVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_UTENTE", "idUtente"});
		mapVoSequenced.put(PbandiWAttiLiquidazioneDlVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_W_ATTI_LIQUIDAZI_DL", "IdAttiLiquidazioneDl"});
		mapVoSequenced.put(PbandiWAttiLiquidazioneDmVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_W_ATTI_LIQUIDAZI_DM", "IdAttiLiquidazioneDm"});
		mapVoSequenced.put(PbandiWAttiLiquidazioneDtVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_W_ATTI_LIQUIDAZI_DT", "IdAttiLiquidazioneDt"});
		mapVoSequenced.put(PbandiWImpegniVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_W_IMPEGNI", "idImpegni"});
		mapVoSequenced.put(PbandiTFlussiUploadVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_FLUSSI_UPLOAD", "idFlusso"});
		mapVoSequenced.put(PbandiTFolderVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_FOLDER", "idFolder"});
		mapVoSequenced.put(PbandiRBandoLineaSettoreVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_R_BANDO_LIN_SETT", "progrBandoLineaSettore"});
		mapVoSequenced.put(PbandiTAppaltoChecklistVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_APPALTO_CHECKLIST", "idAppaltoChecklist"});
		mapVoSequenced.put(PbandiTDettCheckAppaltiVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_DET_CHECK_APPALTI", "idDettCheckAppalti"});
		mapVoSequenced.put(PbandiTFileVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_FILE", "idFile"});
		mapVoSequenced.put(PbandiTDettCheckAppItemVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_DETT_CHK_APP_ITEM", "idDettCheckAppItem"});
		mapVoSequenced.put(PbandiTFileEntitaVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_FILE_ENTITA", "idFileEntita"});
		mapVoSequenced.put(PbandiTInvioMailNotificheVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_INVIO_MAIL_NOT", "idInvioEmail"});	
		mapVoSequenced.put(PbandiTNotificheIstruttoreVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_NOTIFICHE_ISTR", "idSoggettoNotifica"});
		// CDU-14 V04 inizio
		mapVoSequenced.put(PbandiRBlTipoDocSezModVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_R_BL_TIPO_DOC_SEZ_M", "progrBlTipoDocSezMod"});
		mapVoSequenced.put(PbandiREccezBanLinDocPagVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_R_ECC_BAN_LIN_DOC_P", "progrEccezBanLinDocPag"});
		// CDU-14 V04 fine
		mapVoSequenced.put(PbandiTLogProtocollazioneVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_LOG_PROTOCOLL", "idLog"});
		mapVoSequenced.put(PbandiTLogValidazFirmaVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_LOG_VALIDAZ_FIRMA", "idLog"});
		mapVoSequenced.put(PbandiDMicroSezioneModuloVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_D_MICRO_SEZ_MODULO", "idMicroSezioneModulo"});
		// CDU-14 V07: inizio
		/*	codice che legge i dati da db commentato causa collegamento con findom sospeso.
		mapVoSequenced.put(PbandiTSportelliBandiVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_SPORTELLI_BANDI", "idSportelloBando"});
		mapVoSequenced.put(PbandiDPremialitaVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_D_PREMIALITA", "idPremialita"});
		mapVoSequenced.put(PbandiDAllegatiVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_D_ALLEGATI", "idAllegato"});
		mapVoSequenced.put(PbandiDDettTipolAiutiVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_D_DETT_TIPOL_AIUTI", "idDettTipolAiuti"});
		*/
		// CDU-14 V07: fine
		// CDU-13 V07: inizio
		/* commentato causa sospensione collegamento con findom.
		mapVoSequenced.put(PbandiDCampiInterventoVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_D_CAMPI_INTERVENTO", "idCampoIntervento"});
		mapVoSequenced.put(PbandiDTipolInterventiVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_D_TIPOL_INTERVENTI", "idTipolIntervento"});
		mapVoSequenced.put(PbandiDDettTipolInterventiVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_D_DETT_TIPOL_INTERV", "idDettTipolIntervento"});
		*/
		// CDU-13 V07: fine
		// CDU-110-V03: inizio
		mapVoSequenced.put(PbandiLLogStatoElabDocVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_L_LOG_ST_ELAB_DOC", "idChiamata"});
		// CDU-110-V03: fine
		mapVoSequenced.put(PbandiTTrasferimentoVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_TRASFERIMENTO", "idTrasferimento"});
		mapVoSequenced.put(PbandiTEconomieVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_ECONOMIE", "idEconomia"});
		//Affidamenti
		mapVoSequenced.put(PbandiTVariantiAffidamentiVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_VARIANTI_AFFID", "idVariante"});
		mapVoSequenced.put(PbandiTEsitiNoteAffidamentVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_ESIT_NOT_AFFID", "idEsito"});
		// Irregolarita
		mapVoSequenced.put(PbandiTDettRevocaIrregVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_DETT_REVOCA_IRREG", "idDettRevocaIrreg"});
		// Integrazione di Spesa
		mapVoSequenced.put(PbandiTIntegrazioneSpesaVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_INTEGRAZ_SPESA", "idIntegrazioneSpesa"});
		mapVoSequenced.put(PbandiTCampionamentoVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_CAMPIONAMENTO", "idCampione"});
		mapVoSequenced.put(PbandiTEsitoControlliVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_ESITO_CONTROLLI", "idEsitoControllo"});
		// PBANDI_T_RETT_FORFETT
		mapVoSequenced.put(PbandiTRettForfettVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_RETT_FORFETT", "idRettificaForfett"});
		// Cultura
		mapVoSequenced.put(PbandiDVoceDiEntrataVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_D_VOCE_DI_ENTRATA", "idVoceDiEntrata"});
		mapVoSequenced.put(PbandiTAffidServtecArVO.class.getSimpleName(),new String[]{"SEQ_PBANDI_T_AFFID_SERVTEC_AR", "idAffidServtec"});
	
	}

	
	
/**
 * Valorizza il campo del VO che deve essere inserito sul db 
 * invocando la sequence relativa
 * @param valueObject
 * @throws Exception
 */
	
	public void setKeyFromSequence(GenericVO valueObject) throws Exception{
		String[]sequenceProperty=null;
		sequenceProperty=mapVoSequenced.get(valueObject.getClass().getSimpleName());
		if(sequenceProperty!=null){
			String nomeSequence=sequenceProperty[0];
			String propertySequenced=sequenceProperty[1];
			BigDecimal sequenceValue = getSequenceValue(nomeSequence);
			BeanUtil.setPropertyValueByName(valueObject,propertySequenced,sequenceValue);
		}
		
	}
	
	
	private BigDecimal getSequenceValue(String nomeSequence) {
		AbstractDataFieldMaxValueIncrementer sequence=new org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer();
		sequence.setIncrementerName(nomeSequence);
		logger.debug("dataSource="+dataSource);
		//DataSource dataSource=(DataSource)ServiceBeanLocator.getBeanByName("dataSource");
		sequence.setDataSource(dataSource);
		Long sequenceValue=sequence.nextLongValue();
		return new BigDecimal(sequenceValue.longValue());
	}


	public boolean hasSequence(GenericVO valueObject) {
			String []sequenceProperty=mapVoSequenced.get(valueObject.getClass().getSimpleName());
			if(sequenceProperty!=null)
				return true;
			return false;
	}
}
