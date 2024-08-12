package it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedatididominio;

import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.*;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.*;

////{PROTECTED REGION ID(R-788796552) ENABLED START////}
/**
 * Inserire qui la documentazione dell'interfaccia pubblica del servizio
 * gestione_dati_di_dominio. Consigli:
 * <ul>
 * <li>Descrivere qual'� lo scopo generale del servizio
 * <li>Se necessario fornire una overview delle funzioni messe a disposizione
 * eventualmente raggruppandole logicamente. Il dettaglio dei singoli metodi va
 * documentato in corrispondenza dei metodi stessi
 * <li>Se necessario descrivere gli scenari di utilizzo pi� frequenti, ovvero le
 * "coreografie" (nel caso sia necessario richiamare in una sequenza particolare
 * i metodi
 * <li>Inserire informazioni quali il livello di securizzazione A0-A3
 * <li>Inserire varie ed eventuali...
 * </ul>
 * 
 * @generated
 */
////{PROTECTED REGION END////}
public interface GestioneDatiDiDominioSrv {

	////{PROTECTED REGION ID(R1777937592) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo getConfiguration.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.ConfigurationDTO
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.ConfigurationDTO getConfiguration(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R-1346204) ENABLED START////}

	// Nomi delle tabelle di COSTANTI
	final String TIPO_DOCUMENTO_INDEX = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO";
	final String CATEG_ANAGRAFICA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDCategAnagraficaVO";

	// Nomi delle tabelle di DOMINIO
	final String MANCATO_RECUPERO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDMancatoRecuperoVO";
	final String ALIQUOTA_RITENUTA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDAliquotaRitenutaVO";
	final String AREA_SCIENTIFICA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDAreaScientificaVO";
	final String ATENEO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDAteneoVO";
	final String ATTIVITA_ATECO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.AttivitaAtecoVO";
	final String ATTIVITA_DI_PROCESSO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDAttivitaDiProcessoVO";
	final String ATTIVITA_ECONOMICA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDAttivitaEconomicaVO";
	final String ATTIVITA_UIC = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDAttivitaUicVO";
	final String BANCA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDBancaVO";
	final String CARICA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDCaricaVO";
	final String CATEGORIA_CIPE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDCategoriaCipeVO";
	final String CAUSALE_DISIMPEGNO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDCausaleDisimpegnoVO";
	final String CAUSALE_EROGAZIONE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDCausaleErogazioneVO";
	final String CAUSALE_PAGAMENTO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDCausalePagamentoVO";
	final String CLASSIFICAZIONE_ENTE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDClassificazioneEnteVO";
	final String COMUNE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDComuneVO";
	final String COMUNE_ESTERO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDComuneEsteroVO";
	final String DELIBERA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDDeliberaVO";
	final String DIMENSIONE_IMPRESA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDDimensioneImpresaVO";
	final String DIMENSIONE_TERRITOR = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDDimensioneTerritorVO";
	final String DIPARTIMENTO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDDipartimentoVO";
	final String DISPOSIZIONE_COMUNITARIA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDDispComunitariaVO";
	final String ENTE_DI_COMPETENZA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.EnteDiCompetenzaVO";
	final String ERRORE_BATCH = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDErroreBatchVO";
	final String FASE_MONIT = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDFaseMonitVO";
	final String FONTE_INDIRIZZO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDFonteIndirizzoVO";
	final String FORMA_GIURIDICA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDFormaGiuridicaVO";
	final String GRANDE_PROGETTO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDGrandeProgettoVO";

	final String IND_RISULTATO_PROGRAM = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDIndRisultatoProgramVO";
	final String INDICATORE_QSN = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDIndicatoreQsnVO";
	final String INDICATORI = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDIndicatoriVO";
	final String INTESA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDIntesaVO";
	final String INVIO_PROPOSTA_CERTIF = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDInvioPropostaCertifVO";
	final String ITER = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDIterVO";
	final String LINEA_DI_INTERVENTO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDLineaDiInterventoVO";
	final String MATERIA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDMateriaVO";
	final String METODO_INDIVIDUAZIONE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDMetodoIndividuazioneVO";
	final String MODALITA_AGEVOLAZIONE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDModalitaAgevolazioneVO";
	final String MODALITA_EROGAZIONE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDModalitaErogazioneVO";
	final String MODALITA_PAGAMENTO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDModalitaPagamentoVO";
	final String MOTIVO_REVOCA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDMotivoRevocaVO";
	final String MOTIVO_SCOSTAMENTO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDMotivoScostamentoVO";
	final String NATURA_CIPE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDNaturaCipeVO";
	final String NATURA_SANZIONE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDNaturaSanzioneVO";
	final String NAZIONE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDNazioneVO";
	final String NOME_BATCH = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDNomeBatchVO";
	final String NORMA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDNormaVO";
	final String NORMATIVA_AFFIDAMENTO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDNormativaAffidamentoVO";
	final String NOTIFICA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDNotificaVO";
	final String OBIETTIVO_GEN_QSN = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDObiettivoGenQsnVO";
	final String OBIETTIVO_SPECIF_QSN = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDObiettivoSpecifQsnVO";
	final String PRIORITA_QSN = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDPrioritaQsnVO";
	final String PROGETTO_COMPLESSO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDProgettoComplessoVO";
	final String PROVINCIA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDProvinciaVO";
	final String QUALIFICA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDQualificaVO";
	final String QUALIFICAZIONE_IRREGOLARITA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDQualificazioneIrregVO";
	final String REGIONE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDRegioneVO";
	final String RUOLO_ENTE_COMPETENZA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDRuoloEnteCompetenzaVO";
	//final String RUOLO_ENTE_COMPETENZA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.RuoloEnteCompetenzaVO";

	final String SETTORE_ATTIVITA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDSettoreAttivitaVO";
	final String SETTORE_CIPE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDSettoreCipeVO";
	final String SETTORE_CPT = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDSettoreCptVO";
	final String SETTORE_ENTE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDSettoreEnteVO";

	final String SITUAZIONE_INPS = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDSituazioneInpsVO";
	final String ATTIVITA_INPS = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDAttivitaInpsVO";
	final String ALTRA_CASSA_PREVIDENZ = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDAltraCassaPrevidenzVO";
	final String TIPO_ALTRA_CASSA_PREV = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoAltraCassaPrevVO";
	final String RISCHIO_INAIL = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDRischioInailVO";
	final String TIPO_PROVVEDIMENTO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoProvvedimentoVO";

	final String SOGGETTO_FINANZIATORE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDSoggettoFinanziatoreVO";
	final String SOTTO_SETTORE_CIPE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDSottoSettoreCipeVO";
	final String STATO_AMMINISTRATIVO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDStatoAmministrativoVO";
	final String STATO_ATTO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDStatoAttoVO";
	final String STATO_CONTO_ECONOMICO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDStatoContoEconomicoVO";
	final String STATO_DOCUMENTO_SPESA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDStatoDocumentoSpesaVO";
	final String STATO_DOCUMENTO_INDEX = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDStatoDocumentoIndexVO";
	final String STATO_DOMANDA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDStatoDomandaVO";
	final String STATO_FINANZIARIO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDStatoFinanziarioVO";
	final String STATO_INVIO_DOMANDA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDStatoInvioDomandaVO";
	final String STATO_LIQUIDAZIONE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDStatoLiquidazioneVO";
	final String STATO_PROGETTO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDStatoProgettoVO";
	final String STATO_PROPOSTA_CERTIF = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDStatoPropostaCertifVO";
	final String STATO_TIPO_DOC_INDEX = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDStatoTipoDocIndexVO";
	final String STATO_VALIDAZ_SPESA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDStatoValidazSpesaVO";
	final String STEP_AGGIUDICAZIONE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDStepAggiudicazioneVO";
	final String STEP_ATTIVAZIONE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDStepAttivazioneVO";
	final String STRUMENTO_ATTUATIVO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDStrumentoAttuativoVO";
	//final String STRUMENTO_ATTUATIVO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.StrumentoAttuativoVO";
	final String TEMA_PRIORITARIO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTemaPrioritarioVO";
	final String TEMATICA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTematicaVO";
	final String TIPO_ACCESSO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoAccessoVO";
	final String TIPO_AIUTO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoAiutoVO";
	final String TIPO_ALLEGATO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoAllegatoVO";
	final String TIPO_ANAGRAFICA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoAnagraficaVO";
	final String TIPO_BENEFICIARIO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoBeneficiarioVO";
	final String TIPO_DICHIARAZ_SPESA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoDichiarazSpesaVO";
	final String TIPO_DOCUMENTO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoDocumentoVO";
	final String TIPO_DOCUMENTO_SPESA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoDocumentoSpesaVO";
	final String TIPO_ENTE_COMPETENZA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoEnteCompetenzaVO";
	final String TIPO_FONDO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoFondoVO";
	final String TIPO_INDICATORE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoIndicatoreVO";
	final String TIPO_INS_COORDINATE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoInsCoordinateVO";
	final String TIPO_IRREGOLARITA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoIrregolaritaVO";
	final String TIPO_ISCRIZIONE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoIscrizioneVO";
	final String TIPO_LINEA_INTERVENTO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoLineaInterventoVO";
	final String TIPO_OGGETTO_ATTIVITA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoOggettoAttivitaVO";
	final String TIPO_OPERAZIONE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoOperazioneVO";
	final String TIPO_PERIODO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoPeriodoVO";
	final String PERIODO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTPeriodoVO";
	final String TIPO_REVOCA_RINUNCIA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoRevocaRinunciaVO";
	final String TIPO_RITENUTA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoRitenutaVO";
	final String TIPO_SEDE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoSedeVO";
	final String TIPO_SOGG_CORRELATO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoSoggCorrelatoVO";
	final String TIPO_SOGG_FINANZIAT = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoSoggFinanziatVO";
	final String TIPO_SOGGETTO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoSoggettoVO";
	final String TIPO_SOGGETTO_RITENUTA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoSoggRitenutaVO";
	final String TIPO_STRUMENTO_PROGR = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoStrumentoProgrVO";
	final String TIPO_TEMPLATE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoTemplateVO";
	final String TIPO_TRATTAMENTO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoTrattamentoVO";
	final String TIPOLOGIA_AGGIUDICAZ = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipologiaAggiudicazVO";
	final String TIPOLOGIA_ATTIVAZIONE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipologiaAttivazioneVO";
	final String TIPOLOGIA_CIPE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipologiaCipeVO";
	final String TIPOLOGIA_CONTO_ECON = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipologiaContoEconVO";
	final String TIPOLOGIA_ENTE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipologiaEnteVO";
	final String TIPOLOGIA_APPALTO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipologiaAppaltoVO";
	final String UNITA_MISURA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDUnitaMisuraVO";
	final String UNITA_ORGANIZZATIVA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDUnitaOrganizzativaVO";
	final String VOCE_DI_SPESA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDVoceDiSpesaVO";
	final String VOCE_DI_SPESA_MONIT = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDVoceDiSpesaMonitVO";
	
	// Cultura.
	final String TIPOLOGIA_VOCE_DI_SPESA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipolVoceDiSpesaVO";
	final String VOCE_DI_ENTRATA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDVoceDiEntrataVO";

	// JIRA PBANDI-1390 
	final String PROCESSO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTProcessoVO";
	// JIRA PBANDI-1390 fine

	// Nomi delle tabelle fittizie rappresentate da VO custom
	final String DELIBERA_CON_DESCRIZIONE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DeliberaVO";
	final String PERIODO_PROGETTO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.PeriodoProgettoVO";
	final String PROGETTO_SOGGETTI_PRIVATI = "it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ProgettoSoggettiPrivatiVO";
	final String PRIORITA_QSN_LINEA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.PrioritaQsnLineaVO";
	final String TEMA_PRIORITARIO_BANDO_LINEA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaTemaPrioritarioAssociatoVO";
	final String IND_RISULTATO_PROGRAM_BANDO_LINEA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.IndRisultatoProgramBandoLineaVO";
	final String INDICATORE_QSN_BANDO_LINEA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.IndicatoreQsnBandoLineaVO";
	final String TIPO_AIUTO_BANDO_LINEA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.TipoAiutoBandoLineaVO";
	final String STATO_PROPOSTA_CERTIFICAZIONE_ADC_ADG = "it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.StatoPropostaCertificazioneAdcAdgVO";
	final String UTENTE_PERSONA_FISICA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.UtentePersonaFisicaVO";
	// CDU-14 V04 inizio
	final String BANDO_LIN_TIPO_PERIODO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaTipoPeriodoAssociatoVO";
	final String BANDO_LINEA_DUPLICA_MODELLI = "it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaDaCuiDuplicareModelliVO";
	final String MODELLO_BANDO_LINEA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.ModelloBandoLineaVO";
	// CDU-14 V04 fine
	// CDU-14 V07: inizio
	final String ALLEGATO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDAllegatiVO";
	// CDU-14 V07: fine
	// CDU-13 V07: inizio
	final String CAMPI_INTERVENTO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDCampiInterventoVO";
	final String ATECO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDAtecoVO";
	// CDU-13 V07: fine
	// CDU-13 V08: inizio
	final String BANDO_LINEA_INTERVENTO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoLineaInterventVO";
	final String TIPO_LOCALIZZAZIONE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoLocalizzazioneVO";
	final String LIVELLO_ISTITUZIONE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDLivelloIstituzioneVO";
	final String CLASSIFICAZIONE_MET = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDClassificazioneMetVO";
	final String OBIETTIVO_TEMATICO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDObiettivoTemVO";
	final String CLASSIFICAZIONE_RA = "it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ClassificazioneRaVO";
	// CDU-13 V08: fine
	final String SCHEMA_FATT_ELETT = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiCSchemaFattElettVO";

	//24.03 M.E. AFFIDAMENTI
	final String TIPO_AFFIDAMENTO = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoAffidamentoVO";
	//MOTIVO_ASSENZA_CIG
	final String MOTIVO_ASSENZA_CIG = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDMotivoAssenzaCigVO";
	//TIPO_PERCETTORE
	final String TIPO_PERCETTORE = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoPercettoreVO";

	final String TIPOLOGIE_VARIANTI = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipologieVariantiVO";
	final String TIPO_PROCEDURA_ORIG = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoProceduraOrigVO";

	final String CLASS_REVOCA_IRREG = "it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDClassRevocaIrregVO";

	String COD_TIPO_SOGGETTO_PERSONA_FISICA = "PF";
	String COD_TIPO_SOGGETTO_ENTE_GIURIDICO = "EG";

	String COD_TIPO_ANAGRAFICA_BENEFICIARIO = "BENEFICIARIO";
	String COD_TIPO_BENEFICIARIO_PROPONENTE = "BEN-PROPONENTE";

	String COD_TIPO_DOCUMENTO_INDEX_DICHIARAZIONE_DI_SPESA = "DS";
	String COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_DOCUMENTALE = "CL";
	/**
	 * FIXME: VN
	 * E' uguale alla precedente. Bohhh.
	 * La devo inserire perche' altrimenti pbandiweb va in errore
	 */
	String COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_VALIDAZIONE = "CL";
	String COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO = "CLIL";
	String COD_TIPO_DOCUMENTO_INDEX_VERBALE_CONTROLLO_VALIDAZIONE = "VCV";
	String COD_TIPO_DOCUMENTO_INDEX_VERBALE_CHECKLIST_CL = "VCD";
	String COD_TIPO_DOCUMENTO_INDEX_PROPOSTA_RIMODULAZIONE = "PR";
	String COD_TIPO_DOCUMENTO_INDEX_RIMODULAZIONE = "RM";
	String COD_TIPO_DOCUMENTO_INDEX_RELAZIONE_TECNICA = "RT";
	String COD_TIPO_DOCUMENTO_INDEX_COMUNICAZIONE_FINE_PROGETTO = "CFP";
	String COD_TIPO_DOCUMENTO_INDEX_ARCHIVIO_FILE = "AF";
	String COD_TIPO_DOCUMENTO_INDEX_RDDS = "RDDS";
	String COD_TIPO_DOCUMENTO_INDEX_FILE_CAMPIONAMENTO_CERT = "FCC";
	String COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_AFFIDAMENTO_VALIDAZIONE = "CLA";			// Jira Pbandi-2859
	String COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_AFFIDAMENTO_IN_LOCO = "CLILA";			// Jira Pbandi-2859

	String STATO_TIPO_DOC_INDEX_BOZZA = "B";
	String STATO_TIPO_DOC_INDEX_DA_AGGIORNARE = "DA";
	String STATO_TIPO_DOC_INDEX_DEFINITIVO = "D";
	String STATO_TIPO_DOC_INDEX_INVIATO = "I";
	String STATO_TIPO_DOC_INDEX_INVIATO_PREGRESSO = "IP";

	String STATO_DOCUMENTO_INDEX_ACQUISITO = "ACQUISITO";
	String STATO_DOCUMENTO_INDEX_CLASSIFICATO = "CLASSIFICATO";
	String STATO_DOCUMENTO_INDEX_GENERATO = "GENERATO";
	String STATO_DOCUMENTO_INDEX_INVIATO = "INVIATO";
	String STATO_DOCUMENTO_INDEX_NON_VALIDATO = "NON-VALIDATO";
	String STATO_DOCUMENTO_INDEX_PROTOCOLLATO = "PROTOCOLLATO";
	String STATO_DOCUMENTO_INDEX_VALIDATO = "VALIDATO";

	String COD_TIPO_DOCUMENTO_INDEX_RICHIESTA_EROGAZIONE = "RE";

	/*
	 * TIPO SEDE 
	 */
	String COD_TIPO_SEDE_SEDE_LEGALE = "LE";
	String COD_TIPO_SEDE_SEDE_INTERVENTO = "SI";

	/*
	 * 
	 */
	String DESC_BREVE_MACRO_SEZ_ALLEGATI = "ALL";

	/*
	 * STATI DOCUMENTO DI SPESA
	 */
	String STATO_DOCUMENTO_DI_SPESA_DICHIARABILE = "I";
	String STATO_DOCUMENTO_DI_SPESA_IN_VALIDAZIONE = "D";
	String STATO_DOCUMENTO_DI_SPESA_SOSPESO = "S";
	String STATO_DOCUMENTO_DI_SPESA_PARZIALMENTE_VALIDATO = "P";
	String STATO_DOCUMENTO_DI_SPESA_VALIDATO = "V";
	String STATO_DOCUMENTO_DI_SPESA_NON_VALIDATO = "N";
	String STATO_DOCUMENTO_DI_SPESA_RESPINTO = "R";
	String STATO_DOCUMENTO_DI_SPESA_DA_COMPLETARE = "DC";

	/*
	 * TIPO DOCUMENTI DI SPESA
	 */

	String TIPO_DOCUMENTO_DI_SPESA_CEDOLINO = "CD";
	String TIPO_DOCUMENTO_DI_SPESA_FATTURA = "FT";
	String TIPO_DOCUMENTO_DI_SPESA_FATTURA_FIDEIUSSORIA = "FF";
	String TIPO_DOCUMENTO_DI_SPESA_NOTA_DI_CREDITO = "NC";
	String TIPO_DOCUMENTO_DI_SPESA_NOTA_SPESE = "NS";
	String TIPO_DOCUMENTO_DI_SPESA_QUOTA_DI_AMMORTAMENTO = "QA";
	String TIPO_DOCUMENTO_DI_SPESA_SPESE_GENERALI_FORFETTARIE = "SF";
	String TIPO_DOCUMENTO_DI_SPESA_SPESE_GENERALI_DIRETTE = "SG";
	String TIPO_DOCUMENTO_DI_SPESA_FATTURA_LEASING = "FL";
	String TIPO_DOCUMENTO_DI_SPESA_RICEVUTA_DI_LOCAZIONE = "RL";
	String TIPO_DOCUMENTO_DI_SPESA_ATTO_COSTITUZIONE_FONDO = "CF";
	String TIPO_DOCUMENTO_DI_SPESA_AUTODICHIARAZIONE_COMPENSO_SOCI = "CS";
	String TIPO_DOCUMENTO_DI_SPESA_CONTRIBUTO_FORFETTARIO_IN_CONTO_ESERCIZIO = "CE";
	String TIPO_DOCUMENTO_DI_SPESA_EXTRA_AFFIDAMENTO = "SXA";

	/* Gestione progetto 14 20 */

	/**
	 * Inserire qui la documentazione del metodo findDecodifica. Descrivere
	 * dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri).
	 * E'importante elencare casi particolari (dati non trovati, ecc...)
	 * <li>i singoli parametri (nelle sezioni apposite sottostanti): �
	 * importante indicare:
	 * <ul>
	 * <li>se il parametro � obbligatorio
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 * di corrispondenza con una particolare codifica, che pu� essere prefissata
	 * (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 * di un'applicazione)
	 * </ul>
	 * <li>le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li>il tipo di ritorno
	 * </ul>
	 * 
	 * @param idUtente
	 *            [documentazione del parametro (vedere sopra per consigli sulla
	 *            documentazione)]
	 * 
	 * 
	 * @param identitaDigitale
	 *            [documentazione del parametro (vedere sopra per consigli sulla
	 *            documentazione)]
	 * 
	 * 
	 * @param id
	 *            [documentazione del parametro (vedere sopra per consigli sulla
	 *            documentazione)]
	 * 
	 * 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica
	 * 
	 * @throws GestioneDatiDiDominioException
	 * 
	 * @throws CSIException
	 *             (eccezione base rilanciata dall'infrastruttura di
	 *             cooperazione)
	 * @throws SystemException
	 *             in caso di errore di sistema (connessione, comunicazione,
	 *             ecc.)
	 * @throws UnrecoverableException
	 *             in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica findDecodifica(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long id,

	java.lang.String chiave

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R-41732006) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findDecodifiche. Descrivere
	 * dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri).
	 * E'importante elencare casi particolari (dati non trovati, ecc...)
	 * <li>i singoli parametri (nelle sezioni apposite sottostanti): �
	 * importante indicare:
	 * <ul>
	 * <li>se il parametro � obbligatorio
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 * di corrispondenza con una particolare codifica, che pu� essere prefissata
	 * (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 * di un'applicazione)
	 * </ul>
	 * <li>le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li>il tipo di ritorno
	 * </ul>
	 * 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[]
	 * 
	 * @throws GestioneDatiDiDominioException
	 * 
	 * @throws CSIException
	 *             (eccezione base rilanciata dall'infrastruttura di
	 *             cooperazione)
	 * @throws SystemException
	 *             in caso di errore di sistema (connessione, comunicazione,
	 *             ecc.)
	 * @throws UnrecoverableException
	 *             in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[] findDecodifiche(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.String chiave

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R318801495) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findDecodificheStorico.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param chiave [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[]
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[] findDecodificheStorico(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.String chiave

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R-1145165969) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findDecodificheFiltrato.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param chiave [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param nomeCampo [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param valore [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[]
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[] findDecodificheFiltrato(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.String chiave,

	java.lang.String nomeCampo,

	java.lang.String valore

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R2112054690) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findDecodificheFiltratoStorico.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param chiave [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param nomeCampo [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param valore [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[]
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[] findDecodificheFiltratoStorico(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.String chiave,

	java.lang.String nomeCampo,

	java.lang.String valore

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R-600839506) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo
	 * findDecodificaPerDescrizioneBreve. Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri).
	 * E'importante elencare casi particolari (dati non trovati, ecc...)
	 * <li>i singoli parametri (nelle sezioni apposite sottostanti): �
	 * importante indicare:
	 * <ul>
	 * <li>se il parametro � obbligatorio
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 * di corrispondenza con una particolare codifica, che pu� essere prefissata
	 * (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 * di un'applicazione)
	 * </ul>
	 * <li>le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li>il tipo di ritorno
	 * </ul>
	 * 
	 * @param idUtente
	 *            [documentazione del parametro (vedere sopra per consigli sulla
	 *            documentazione)]
	 * 
	 * 
	 * @param identitaDigitale
	 *            [documentazione del parametro (vedere sopra per consigli sulla
	 *            documentazione)]
	 * 
	 * 
	 * @param descrizioneBreve
	 *            [documentazione del parametro (vedere sopra per consigli sulla
	 *            documentazione)]
	 * 
	 * 
	 * @param chiave
	 *            [documentazione del parametro (vedere sopra per consigli sulla
	 *            documentazione)]
	 * 
	 * 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica
	 * 
	 * @throws GestioneDatiDiDominioException
	 * 
	 * @throws CSIException
	 *             (eccezione base rilanciata dall'infrastruttura di
	 *             cooperazione)
	 * @throws SystemException
	 *             in caso di errore di sistema (connessione, comunicazione,
	 *             ecc.)
	 * @throws UnrecoverableException
	 *             in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica findDecodificaPerDescrizioneBreve(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.String descrizioneBreve,

	java.lang.String chiave

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R-1242395074) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findDecodificheConEsclusioni.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri).
	 * E'importante elencare casi particolari (dati non trovati, ecc...)
	 * <li>i singoli parametri (nelle sezioni apposite sottostanti): �
	 * importante indicare:
	 * <ul>
	 * <li>se il parametro � obbligatorio
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 * di corrispondenza con una particolare codifica, che pu� essere prefissata
	 * (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 * di un'applicazione)
	 * </ul>
	 * <li>le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li>il tipo di ritorno
	 * </ul>
	 * 
	 * @param idUtente
	 *            [documentazione del parametro (vedere sopra per consigli sulla
	 *            documentazione)]
	 * 
	 * 
	 * @param identitaDigitale
	 *            [documentazione del parametro (vedere sopra per consigli sulla
	 *            documentazione)]
	 * 
	 * 
	 * @param chiave
	 *            [documentazione del parametro (vedere sopra per consigli sulla
	 *            documentazione)]
	 * 
	 * 
	 * @param codiciDaEscludere
	 *            [documentazione del parametro (vedere sopra per consigli sulla
	 *            documentazione)]
	 * 
	 * 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[]
	 * 
	 * @throws GestioneDatiDiDominioException
	 * 
	 * @throws CSIException
	 *             (eccezione base rilanciata dall'infrastruttura di
	 *             cooperazione)
	 * @throws SystemException
	 *             in caso di errore di sistema (connessione, comunicazione,
	 *             ecc.)
	 * @throws UnrecoverableException
	 *             in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[] findDecodificheConEsclusioni(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.String chiave,

	java.lang.String[] codiciDaEscludere

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R1377384271) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findEntiDiCompetenzaPerTipo.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param descBreveTipoAnagrafica [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[]
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[] findEntiDiCompetenzaPerTipo(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.String descBreveTipoAnagrafica

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R1154104704) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findProvincie.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.ProvinciaDTO[]
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.ProvinciaDTO[] findProvincie(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R-1188089760) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findComuni.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param idProvincia [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.ComuneDTO[]
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.ComuneDTO[] findComuni(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idProvincia

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R-1687596905) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findNazioni.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param idProvincia [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[]
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[] findNazioni(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R1470799510) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findComuniEsteri.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param idNazione [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[]
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[] findComuniEsteri(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idNazione

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R509987734) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo checkIBAN.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param iban [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return void
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.CoordinateBancarieDTO checkIBAN(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.String iban

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R-881517484) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findModalitaErogazioneFiltratePerBilancio.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[]
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[] findModalitaErogazioneFiltratePerBilancio(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R-1385915561) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findEntiDiCompetenzaFiltrato.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param descBreveTipoAnagrafica [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param idSoggetto [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param ruolo [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[]
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[] findEntiDiCompetenzaFiltrato(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.String descBreveTipoAnagrafica,

	java.lang.Long idSoggetto,

	java.lang.String ruolo

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R526515422) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findCodIgrueT13T14.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param progrBandoLineaIntervento [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return java.lang.String
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public java.lang.String findCodIgrueT13T14(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long progrBandoLineaIntervento

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R-671628054) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findCodIgrueT13T14ByIdBando.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param idBando [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return java.lang.String
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public java.lang.String findCodIgrueT13T14ByIdBando(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idBando

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R-1131557796) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findEntita.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param nomeEntita [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Entita
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Entita findEntita(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.String nomeEntita

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R68559794) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo getLineaDiIntervento.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param progrBandoLineaIntervento [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.InfoLineaDiInterventoDTO[]
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.InfoLineaDiInterventoDTO[] getLineaDiIntervento(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long progrBandoLineaIntervento

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R-232353374) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo getContextSuffix.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return java.lang.String
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getContextSuffix(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R1438468761) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findDecodificheMultiProgr.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param chiave [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param idLineaDiIntervento [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[]
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[] findDecodificheMultiProgr(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.String chiave,

	java.lang.Long idLineaDiIntervento

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R-853793938) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findDecodificheFiltratoMultiProgr.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param chiave [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param nomeCampo [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param valore [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param idLineaDiIntervento [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[]
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[] findDecodificheFiltratoMultiProgr(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.String chiave,

	java.lang.String nomeCampo,

	java.lang.String valore,

	java.lang.Long idLineaDiIntervento

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R-392507263) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findFormeGiuridiche.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param flagPrivato [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.FormaGiuridicaDTO[]
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.FormaGiuridicaDTO[] findFormeGiuridiche(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.String flagPrivato

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R-1126955163) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findAttivitaAteco.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param flagPrivato [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.AttivitaAtecoDTO[]
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.AttivitaAtecoDTO[] findAttivitaAteco(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R-644456710) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findNazioniCompleto.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.NazioneDTO[]
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.NazioneDTO[] findNazioniCompleto(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R2032682522) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findDecodificheByProgrBandoLinea.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param chiave [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param progrBandoLineaIntervento [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[]
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[] findDecodificheByProgrBandoLinea(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.String chiave,

	java.lang.Long progrBandoLineaIntervento

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R138462117) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findDecodificheFiltratoByProgrBandoLinea.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param chiave [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param nomeCampo [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param valore [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param progrBandoLineaIntervento [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[]
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[] findDecodificheFiltratoByProgrBandoLinea(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.String chiave,

	java.lang.String nomeCampo,

	java.lang.String valore,

	java.lang.Long progrBandoLineaIntervento

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R1220644991) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findTipologieProcedureAggiudicazione.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param progrBandoLinea [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[]
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica[] findTipologieProcedureAggiudicazione(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long progrBandoLinea

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

	////{PROTECTED REGION ID(R194538589) ENABLED START////}
	/**
	 * Inserire qui la documentazione del metodo findPeriodo.
	 * Descrivere dettagliatamente:
	 * <ul>
	 * <li>la semantica del metodo (facendo riferimento ai parametri). E'importante 
	 *     elencare casi particolari (dati non trovati, ecc...)
	 * <li> i singoli parametri (nelle sezioni apposite sottostanti): � importante
	 *      indicare:
	 *      <ul>
	 *      <li>se il parametro � obbligatorio
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      </ul>
	 * <li> le eccezioni rilanciate (nelle sezioni apposite sottostanti)
	 * <li> il tipo di ritorno
	 * </ul>
	 
	 * @param idUtente [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param identitaDigitale [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @param idTipoPeriodo [documentazione del parametro (vedere sopra per consigli sulla documentazione)]
	 	
	 
	 * @return it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.PeriodoDTO[]
	 
	 * @throws GestioneDatiDiDominioException
	 
	 * @throws CSIException (eccezione base rilanciata dall'infrastruttura di cooperazione)
	 * @throws SystemException in caso di errore di sistema (connessione, comunicazione, ecc.)
	 * @throws UnrecoverableException in caso di errore imprevisto e non recuperabile
	 * @generated
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.PeriodoDTO[] findPeriodo(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idTipoPeriodo

	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

}
