package it.csi.pbandi.pbweb.pbandiutil.common;

/**
 * 
 * Classe delle costanti applicative in condivisione tra le miniapp ed il
 * servizio (pbandisrv)
 * 
 */
public interface Constants {

	long ID_UTENTE_FITTIZIO = -1L;

	String DATE_FORMAT = "dd/MM/yyyy";
	String YEAR_FORMAT = "yyyy";
	String TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
	String ORA_FORMAT = "HH:mm:ss";

	String FLAG_FALSE = "N";
	String FLAG_TRUE = "S";

	String DOMINIO_IRIDE = "REG_PMN";

	String MAIL_ADDRESS_NO_REPLY_CSI_IT = "assistenzapiattaforma.bandi@csi.it";

	/*
	 * Nomi di task utilizzati per controlli
	 */
	String TASKNAME_ATTIVITA_PRELIMINARE = "Attivit� preliminare";

	String TASKNAME_WAITME = "WaitME";
	String TASKNAME_WAIT = "Wait";
	String TASKNAME_WAITPR = "WaitPR";
	String TASKNAME_WAITVA = "WaitVA";
	String TASKNAME_WAITRE = "WaitRE";
	String TASKNAME_WAITRC = "WaitRC";

	String TASKNAME_RICHIESTA_EROGAZIONE_PREFIX = "Richiesta di erogazione";
	String TASKNAME_NOTIFICA_PREFIX = "Notifica";

	String TASKNAME_VALIDAZIONE = "Validazione della dichiarazione di spesa";
	String TASKNAME_VALIDAZIONE_FINALE = "Validazione della dichiarazione di spesa finale";
	String TASKNAME_RENDICONTAZIONE = "Dichiarazione di spesa";

	String TASKNAME_PROPOSTA_RIMODULAZIONE = "Proposta di rimodulazione del conto economico";
	String TASKNAME_RICHIESTA_CONTO_ECONOMICO_IN_DOMANDA = "Richiesta del conto economico in domanda";

	String TASKNAME_RIMODULAZIONE_CONTO_ECONOMICO = "Rimodulazione del conto economico";
	String TASKNAME_RIMODULAZIONE_CONTO_ECONOMICO_IN_ISTRUTTORIA = "Rimodulazione del conto economico in istruttoria";

	String TASKNAME_EROGAZIONE = "Erogazione";
	String TASKNAME_MODIFICA_EROGAZIONE = "Modifica erogazione";
	String TASKNAME_RICHIESTA_EROGAZIONE_PRIMO_ANTICIPO = "Richiesta di erogazione primo anticipo";
	String TASKNAME_RICHIESTA_EROGAZIONE_ACCONTO = "Richiesta di erogazione acconto";
	String TASKNAME_RICHIESTA_EROGAZIONE_ULTERIORE_ACCONTO = "Richiesta di erogazione ulteriore acconto";
	String TASKNAME_RICHIESTA_EROGAZIONE_SALDO = "Richiesta di erogazione saldo";

	String TASKNAME_GESTIONE_CHECKLIST = "Gestione checklist del progetto";

	String TASKNAME_RINUNCIA = "Comunicazione di rinuncia";

	String TASKNAME_RETTIFICA = "Gestione spesa validata";
	String TASKNAME_REVOCA = "Revoca";
	String TASKNAME_RECUPERO = "Recupero";
	String TASKNAME_SOPPRESSIONE = "Soppressione";

	String TASKNAME_GESTIONE_FIDEIUSSIONI = "Gestione fideiussioni";

	String TASKNAME_IRREGOLARITA = "Rilevazione irregolarita'";
	String TASKNAME_MODIFICA_REVOCHE = "Modifica revoche";
	String TASKNAME_MODIFICA_RECUPERI = "Modifica recuperi";

	String TASKNAME_GESTIONE_APPALTI = "Gestione appalti";
	
	//
	String TASKNAME_GESTIONE_APPALTI_CONTRATTI_INCARICHI = "Gestione appalti contratti incarichi";
	String TASKNAME_QUADRO_PREVISIONALE = "Quadro previsionale";

	String TASKNAME_GESTIONE_CRONOPROGRAMMA_AVVIO = "Cronoprogramma di avvio";
	String TASKNAME_GESTIONE_CRONOPROGRAMMA = "Cronoprogramma";
	String TASKNAME_RETTIFICA_CRONOPROGRAMMA = "Rettifica cronoprogramma";

	String TASKNAME_GESTIONE_INDICATORI_AVVIO = "Caricamento indicatori di progetto di avvio";
	String TASKNAME_GESTIONE_INDICATORI = "Caricamento indicatori di progetto";
	String TASKNAME_RETTIFICA_INDICATORI = "Rettifica indicatori di progetto";

	String TASKNAME_GESTIONE_PROGETTO = "Dati del progetto";
	String TASKNAME_DICHIARAZIONE_DI_SPESA_INTEGRATIVA = "Dichiarazione di spesa integrativa";

	String TASKNAME_COMUNICAZIONE_DI_FINE_PROGETTO = "Comunicazione di fine progetto";
	String TASKNAME_CHIUSURA_DEL_PROGETTO = "Chiusura del progetto";
	String TASKNAME_CHIUSURA_DEL_PROGETTO_PER_RINUNCIA = "Chiusura del progetto per rinuncia";
	String TASKNAME_CHIUSURA_D_UFFICIO_DEL_PROGETTO = "Chiusura d'ufficio del progetto";

	String TASKNAME_LIQUIDAZIONE_CONTRIBUTO = "Liquidazione contributo";

	String TASKNAME_CONSULTA_ATTI_LIQUIDAZIONE = "Consulta atti liquidazione";
	
	String TASKNAME_GESTIONE_DISIMPEGNI= "Gestione disimpegni";

	/*
	 * variabili di processo
	 */
	String PROCESS_VARIABLE_SERVICE_ID = "roleMapperServiceId";

	String PROCESS_VARIABLE_ID_PROGETTO = "ID_PROGETTO";
	String PROCESS_VARIABLE_ID_BENEFICIARIO = "ID_BENEFICIARIO";

	String PROCESS_VARIABLE_ALTRO_DA_VALIDARE = "altroDaValidare";
	String PROCESS_VARIABLE_CAUSALE_RICHIESTA_EROGAZIONE = "causaleRichiestaErogazione";
	String PROCESS_VARIABLE_CONTO_ECONOMICO_APPROVATO = "contoEconomicoApprovato";
	String PROCESS_VARIABLE_CRONOPROGRAMMA = "cronoprogramma";
	String PROCESS_VARIABLE_DICHIARAZIONI_DA_VALIDARE_PRESENTI = "dichiarazioniDaValidarePresenti";
	String PROCESS_VARIABLE_EROGAZIONE_LIQUIDAZIONE_PRESENTI = "erogazioneLiquidazionePresenti";
	@Deprecated
	String PROCESS_VARIABLE_EROGAZIONI_DA_MODIFICARE_PRESENTI = "erogazioniDaModificarePresenti";
	String PROCESS_VARIABLE_INDICATORI = "indicatori";
	String PROCESS_VARIABLE_LIQUIDAZIONI = "liquidazioni";
	String PROCESS_VARIABLE_RECUPERI_PRESENTI = "recuperiPresenti";
	String PROCESS_VARIABLE_REVOCHE_DA_RECUPERARE_PRESENTI = "revocheDaRecuperarePresenti";
	String PROCESS_VARIABLE_RINUNCIA_PRESENTE = "rinunciaPresente";
	String PROCESS_VARIABLE_PROGETTO_CHIUSO = "progettoChiuso";
	String PROCESS_VARIABLE_RICHIESTA_DICHIARAZIONE_INTEGRATIVA = "richiestaDichiarazioneIntegrativa";
	String PROCESS_VARIABLE_SCHEDA_PROGETTO = "schedaProgetto";
	String PROCESS_VARIABLE_STATO_CRONOPROGRAMMA = "statoCronoprogramma";
	String PROCESS_VARIABLE_STATO_INDICATORI = "statoIndicatori";
	String PROCESS_VARIABLE_TIPO_ULTIMA_DICHIARAZIONE = "tipoUltimaDichiarazione";
	String PROCESS_VARIABLE_VALIDAZIONI_CHIUSE_PRESENTI = "validazioniChiusePresenti";

	/* veramente sarebbe di attivit� e non di processo */
	String PROCESS_VARIABLE_ID_DICHIARAZIONE = "idDichiarazione";
	String PROCESS_ACTIVITY_VARIABLE_RIMODULAZIONE_DEL_CONTO_ECONOMICO_NTFY_DATA_CHIUSURA_RIMODULAZIONE = "Rimodulazione_del_conto_economico_ntfy_data_chiusura_rimodulazione";
	String PROCESS_ACTIVITY_VARIABLE_PROPOSTA_DI_RIMODULAZIONE_DEL_CONTO_ECONOMICO_NTFY_DATA_CHIUSURA_RIMODULAZIONE = "Proposta_di_rimodulazione_del_conto_economico_ntfy_data_invio_proposta";
	String PROCESS_ACTIVITY_VARIABLE_GESTIONE_SPESA_VALIDATA_NTFY_DATA_RETTIFICA_SPESA_VALIDATA = "Gestione_spesa_validata_ntfy_data_rettifica_spesa_validata";
	String PROCESS_ACTIVITY_VARIABLE_REVOCA_NTFY_DATA_REVOCA = "Revoca_ntfy_data_revoca";
	String PROCESS_ACTIVITY_VARIABLE_COMUNICAZIONE_DI_RINUNCIA_NTFY_DATA_INVIO_RINUNCIA = "Comunicazione_di_rinuncia_ntfy_data_invio_rinuncia";
	String PROCESS_ACTIVITY_VARIABLE_VALIDAZIONE_DELLA_DICHIARAZIONE_DI_SPESA_FINALE_NTFY_DATA_CHIUSURA_VALIDAZIONE = "Validazione_della_dichiarazione_di_spesa_finale_ntfy_data_chiusura_validazione";
	String PROCESS_ACTIVITY_VARIABLE_VALIDAZIONE_DELLA_DICHIARAZIONE_DI_SPESA_FINALE_NTFY_NUM_DICHIARAZIONE_DI_SPESA = "Validazione_della_dichiarazione_di_spesa_finale_ntfy_num_dichiarazione_di_spesa";
	String PROCESS_ACTIVITY_VARIABLE_VALIDAZIONE_DELLA_DICHIARAZIONE_DI_SPESA_FINALE_NTFY_DATA_DICHIARAZIONE_DI_SPESA = "Validazione_della_dichiarazione_di_spesa_finale_ntfy_data_dichiarazione_di_spesa";
	String PROCESS_ACTIVITY_VARIABLE_CHIUSURA_D_UFFICIO_DEL_PROGETTO_NTFY_DATA_CHIUSURA_PROGETTO = "Chiusura_d_ufficio_del_progetto_ntfy_data_chiusura_progetto";
	String PROCESS_ACTIVITY_VARIABLE_CHIUSURA_DEL_PROGETTO_NTFY_DATA_CHIUSURA_PROGETTO = "Chiusura_del_progetto_ntfy_data_chiusura_progetto";
	String PROCESS_ACTIVITY_VARIABLE_CHIUSURA_DEL_PROGETTO_PER_RINUNCIA_NTFY_DATA_CHIUSURA_PROGETTO = "Chiusura_del_progetto_per_rinuncia_ntfy_data_chiusura_progetto";

	/* tipi anagrafica */
	String RUOLO_CSI_ASSISTENZA = "CSI-ASSISTENZA";
	String RUOLO_BENEFICIARIO = "BENEFICIARIO";
	String RUOLO_BEN_MASTER = "BEN-MASTER";
	String RUOLO_ADG_IST_MASTER = "ADG-IST-MASTER";
	String RUOLO_ADG_ISTRUTTORE = "ADG-ISTRUTTORE";
	String RUOLO_OI_ISTRUTTORE = "OI-ISTRUTTORE";
	String RUOLO_INTERMEDIARIO = "INTERMEDIARIO";
	String RUOLO_PERSONA_FISICA = "PERSONA-FISICA";
	String RUOLO_OI_IST_MASTER = "OI-IST-MASTER";
	String RUOLO_CSI_ADMIN = "CSI-ADMIN";
	String RUOLO_ENTE_GIURIDICO = "ENTE-GIURIDICO";
	String RUOLO_CREATOR = "CREATOR";
	String RUOLO_AUTORITA_CERTIFICAZIONE = "ADC-CERT";
	String RUOLO_AUTORITA_GESTIONE = "ADG-CERT";

	/* ruoli ente competenza */
	String RUOLO_ENTE_COMPETENZA_DESTINATARIO = "DESTINATARIO";
	String RUOLO_ENTE_COMPETENZA_PROGRAMMATORE = "PROGRAMMATORE";
	String RUOLO_ENTE_COMPETENZA_RESPONSABILE = "RESPONSABILE";

	String TIPO_DIP_DIR_DU = "DU";
	String TIPO_DIP_DIR_DR = "DR";
	String TIPO_DIP_DIR_NA = "NA";

	/* tipi di documento index */
	String TIPO_DOCUMENTO_INDEX_DICHIARAZIONE_DI_SPESA = "DS";
	String TIPO_DOCUMENTO_INDEX_CHECK_LIST_VALIDAZIONE = "CL";
	String TIPO_DOCUMENTO_INDEX_CHECK_LIST_CERTIFICAZIONE_PER_PROPOSTA = "CLC";
	String TIPO_DOCUMENTO_INDEX_CHECK_LIST_CERTIFICAZIONE_PER_PROGETTO = "CLCP";
	String TIPO_DOCUMENTO_INDEX_DICHIARAZIONE_FINALE_DI_CERTIFICAZIONE = "DFC";
	String TIPO_DOCUMENTO_INDEX_RICHIESTA_EROGAZIONE = "RE";
	String TIPO_DOCUMENTO_INDEX_VERBALE_CONTROLLO_VALIDAZIONE = "VCV";
	String TIPO_DOCUMENTO_INDEX_PROPOSTA_DI_RIMODULAZIONE = "PR";
	String TIPO_DOCUMENTO_INDEX_RIMODULAZIONE = "RM";
	String TIPO_DOCUMENTO_INDEX_CHECK_LIST_IN_LOCO = "CLIL";
	String TIPO_DOCUMENTO_INDEX_SCHEDA_OLAF_IRREGOLARITA = "SOI";
	String TIPO_DOCUMENTO_INDEX_DATI_AGGIUNTIVI_IRREGOLARITA = "DAI";
	String TIPO_DOCUMENTO_INDEX_COMUNICAZIONE_DI_RINUNCIA = "RIN";
	String TIPO_DOCUMENTO_INDEX_FILE_DELLA_PROPOSTA_DI_CERTIFICAZIONE = "FPC";
	String TIPO_DOCUMENTO_INDEX_COMUNICAZIONE_DI_FINE_PROGETTO = "CFP";
	String TIPO_DOCUMENTO_INDEX_FILE_DELLA_CHIUSURA_CONTI = "RCC";
	
	/* tipo stato proposta certificazione */
	String TIPO_STATO_PROPOSTA_CERTIFICAZIONE_ADC = "adc";

	/* stati della proposta di certificazione */
	String STATO_PROPOSTA_CERTIFICAZIONE_IN_CODA = "00coda";
	String STATO_PROPOSTA_CERTIFICAZIONE_BOZZA_IN_CODA = "00coda-bozza";
	String STATO_PROPOSTA_CERTIFICAZIONE_IN_ELABORAZIONE = "03elab";
	String STATO_PROPOSTA_CERTIFICAZIONE_BOZZA_IN_ELABORAZIONE = "03elab-bozza";
	String STATO_PROPOSTA_CERTIFICAZIONE_ERRATA = "04err";
	String STATO_PROPOSTA_CERTIFICAZIONE_BOZZA_ERRATA = "04err-bozza";
	String STATO_PROPOSTA_CERTIFICAZIONE_APERTA = "05open";
	String STATO_PROPOSTA_CERTIFICAZIONE_BOZZA = "bozza";
	String STATO_PROPOSTA_CERTIFICAZIONE_ANNULLATA = "06annu";
	String STATO_PROPOSTA_CERTIFICAZIONE_APPROVATA = "07appr";
	String STATO_PROPOSTA_CERTIFICAZIONE_RESPINTA = "09resp";
	String STATO_PROPOSTA_CERTIFICAZIONE_PREVIEW = "prev";
	String STATO_PROPOSTA_CERTIFICAZIONE_BOZZA_PREVIEW = "prev-bozza";
	String STATO_PROPOSTA_CERTIFICAZIONE_INTERMEDIA_FINALE = "intermedia-finale";
	String STATO_PROPOSTA_CERTIFICAZIONE_CHIUSURA_CONTI = "chiusura-conti";

	String DESC_BREVE_TIPO_DICHIARAZIONE_FINALE = "F";
	String DESC_BREVE_TIPO_DICHIARAZIONE_INTERMEDIA = "I";
	String DESC_BREVE_TIPO_DICHIARAZIONE_INTEGRATIVA = "IN";
	String DESC_BREVE_TIPO_DICHIARAZIONE_FINALE_CON_COMUNICAZIONE = "FC";
	String DESC_BREVE_TIPO_INDICATORE_CORE = "CO";

	/* stati validazione dei pagamenti */
	enum StatiValidazionePagamenti {
		DA_RESPINGERE {
			public String getDescBreve() {
				return "G";
			}
		},
		DICHIARATO {
			public String getDescBreve() {
				return "R";
			}
		},
		INSERITO {
			public String getDescBreve() {
				return "I";
			}
		},
		NON_VALIDATO {
			public String getDescBreve() {
				return "N";
			}
		},
		PARZIALMENTE_VALIDATO {
			public String getDescBreve() {
				return "P";
			}
		},
		RESPINTO {
			public String getDescBreve() {
				return "T";
			}
		},
		SOSPESO {
			public String getDescBreve() {
				return "S";
			}
		},
		VALIDATO {
			public String getDescBreve() {
				return "V";
			}
		};
		public abstract String getDescBreve();
	}

	/*
	 * Gestione Progetto
	 */
	String GESTIONE_PROGETTO_STATO_PROGRAMMA_ATTIVO = "1";
	String GESTIONE_PROGETTO_STATO_PROGRAMMA_NON_ATTIVO = "2";

	String DESC_NAZIONE_ITALIA = "ITALIA";
	String DESC_REGIONE_PIEMONTE = "PIEMONTE";

	/*
	 * Rimodulazione conto economico
	 */
	String CODICE_TIPO_SOGG_FINANZIATORE_COFPOR = "COFPOR";
	String CODICE_TIPO_SOGG_FINANZIATORE_OTHFIN = "OTHFIN";

	/*
	 * Stati conto economico
	 */
	String STATO_CONTO_ECONOMICO_IN_PROPOSTA_GESTIONE_OPERATIVA = "IPG";

	/**
	 * Erogazione di finanziamenti e aiuti a imprese e individui
	 */
	String TIPO_OPERAZIONE_03 = "03";

	String DATA_WRAPPER_TYPE_NAME_JSON = "json";

	String UC_CONSOLE = "CONSOLE";

	/*
	 * 
	 * Bilancio: stato atto liquidazione
	 */
	String STATO_ATTO_LIQUIDAZIONE_BOZZA = "0";

	String STATO_ATTO_LIQUIDAZIONE_EMESSO = "1";

	String STATO_ATTO_LIQUIDAZIONE_CANCELLATO = "3";

	String CODICE_BILANCIO_PROVVISORIO = "000000";

	/*
	 * Caricamento massivo
	 */

	String CARICAMENTO_MASSIVO_XML_VALIDATO = "V"; // solo per rocco
	String CARICAMENTO_MASSIVO_XML_NON_VALIDATO = "N"; // se ci sono errori in
														// validatorService.validate
	String CARICAMENTO_MASSIVO_XML_PARZIALMENTE_VALIDATO = "P"; // se non sono
																// errori in
																// validatorService.validate

	/*
	 * MODALITA' DI EROGAZIONE
	 */

	String DESC_BREVE_MODALITA_EROGAZIONE_GIRO_FONDI = "GF";
	String DESC_BREVE_MODALITA_EROGAZIONE_CONTO_CORRENTE_POSTALE = "CP";
	String DESC_BREVE_MODALITA_EROGAZIONE_ASSEGNO_DI_BONIFICO = "AB";
	String DESC_BREVE_MODALITA_EROGAZIONE_ASSEGNO_CIRCOLARE = "AC";

	String CODICE_IGRUE_T13_T14_FSE = "2007IT052PO011";

	/*
	 * TIPI DI DOCUMENTO DI SPESA
	 */
	String TIPO_DOCUMENTO_DI_SPESA_ATTO_COSTITUZIONE_FONDO = "CF";
	String TIPO_DOCUMENTO_DI_SPESA_FATTURA_LEASING = "FL";
	String TIPO_DOCUMENTO_DI_SPESA_RICEVUTA_LOCAZIONE = "RL";
	String TIPO_DOCUMENTO_DI_SPESA_CEDOLINO = "CD";
	String TIPO_DOCUMENTO_DI_SPESA_CEDOLINO_COCOPRO = "CC";
	String TIPO_DOCUMENTO_DI_SPESA_FATTURA = "FT";
	String TIPO_DOCUMENTO_DI_SPESA_FATTURA_FIDEIUSSORIA = "FF";
	String TIPO_DOCUMENTO_DI_SPESA_NOTA_CREDITO = "NC";
	String TIPO_DOCUMENTO_DI_SPESA_NOTA_SPESE = "NS";
	String TIPO_DOCUMENTO_DI_SPESA_QUOTA_AMMORTAMENTO = "QA";
	String TIPO_DOCUMENTO_DI_SPESA_SPESE_FORFETTARIE = "SF";
	String TIPO_DOCUMENTO_DI_SPESA_SPESE_GENERALI = "SG";
	String TIPO_DOCUMENTO_DI_SPESA_AUTODICHIARAZIONE_COMPENSO_SOCIE = "CS";
	String TIPO_DOCUMENTO_DI_SPESA_CONTRIBUTO_FORFETTARIO = "CE";
	String TIPO_DOCUMENTO_DI_SPESA_DOCUMENTO_GENERICO = "DG";
	String TIPO_DOCUMENTO_CONTRIBUTO_FORFETTARIE_EROGATO = "CO";

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

	String HELP_URL = "HELP_URL";

	/*
	 * PERIODI
	 */
	String PERIODO_2007_2013 = "9999";
}
