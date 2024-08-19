/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business.intf;

public interface MessageKey {

	/*
	 * Messaggi generici
	 */
	public String KEY_MSG_ERRORE_GENERICO = "E.errore_generico";
	public String KEY_OPERAZIONE_NON_ESEGUITA = "E.E002";
	public String KEY_SELEZIONARE_ALMENO_UN_ELEMENTO = "E.N015";
	public String KEY_FILTRO_MANCANTE = "E.N004";
	public String KEY_FILTRO_NESSUN_RISULTATO = "W.N002";
	public String KEY_SALVATAGGIO_ESEGUITO = "M.N011";
	public String KEY_CANCELLAZIONE_AVVENUTA_CON_SUCCESSO = "M.N017";
	public String KEY_CANCELLAZIONE_TUTTI_DOCUMENTI_NON_ESEGUITA = "M.W036";
	public String KEY_ERROR_IMPORTO_NEGATIVO = "E.E079";

	public String KEY_OPERAZIONE_ESEGUITA = "M.G1";

	public String FILO_ARIANNA = "M002";

	public String NESSUN_PAGAMENTO_ASSOCIATO = "W.N018";
	public String RIATTIVAZIONE_FORNITORE = "W.W014";
	public String CONFERMA_ELIMINAZIONE = "W.C002";
	public String FORNITORE_GIA_USATO = "E.E027";
	public String CAMPO_OBBLIGATORIO = "E.N005";

	public String IMPOSSIBILE_ELIMINARE_DOC_DI_SPESA = "E.E037";
	public String IMPOSSIBILE_MODIFICARE_DOC_DI_SPESA = "E.E036";

	public String CODICE_FISCALE_FORMALMENTE_NON_CORRETTO = "E.W027";
	public String CODICE_FISCALE_GIA_ESISTENTE_PER_ALTRO_NOME_COGNOME = "W.C016";

	public String ULTIMO_AGGIORNAMENTO_DATA_ORA = "W.N091";

	/** ATTENZIONE: la funzionalitï¿½ non ï¿½ utilizzabile. */
	public String FUNZIONALITA_NON_UTILIZZABILE = "E.N075";

	// Errore: non e possibile eliminare un documento di spesa in stato

	/*
	 * Message key cdu inserisci documento di spesa
	 */
	public String SELEZIONARE_CODICE_FISCALE_PER_CARICAMENTO_DOCDISPESA_FATTURA = "W.N024";
	public String NESSUN_DOCDISPESA_TIPO_FATTURA_BY_FORNITORE = "W.N025";

	public String CONFERMA_SALVA_FORNITORE_CF_ANOMALO = "W.W031";
	public String CONFERMA_SALVA_DOCUMENTO_DI_SPESA_CEDOLINO_RENDICONTABILE_SUPERIORE = "W.C009";

	public String KEY_NUMERO_DOMANDA_PRESENTE = "E.E100";
	public String KEY_CUP_PRESENTE = "E.E101";

	/*
	 * Message key migrazione processi
	 */
	public String KEY_PROCESSI_NON_MIGRATI = "E.N033";
	public String KEY_PROCESSO_NON_MIGRATO = "E.N034";
	public String KEY_PROCESSI_MIGRATI = "M.N032";
	public String KEY_PROCESSO_MIGRATO = "M.N031";

	public String KEY_SOMMA_IMPORTI_QUOTE_PARTE_MAGGIORE_DEL_VALORE_AMMESSO = "E.W001";
	public String KEY_CANCELLAZIONE_VOCE_DI_SPESA = "W.V007";
	public String KEY_CANCELLAZIONE_VOCE_DI_SPESA_ESEGUITA = "M.V009";
	public String KEY_NESSUNA_VOCE_DI_SPESA_ASSOCIATA = "E.V001";

	public String KEY_CAMPO_OBBLIGATORIO = "E.error.required";
	public String KEY_VALORE_IMPORTO_NON_CORRETTO = "E.error.importo.valore";
	public String KEY_FORMATO_DATA_NON_CORRETTO = "E.error.data.format";

	public String CANCELLAZIONE_DOCUMENTO_NON_ASSOCIATO_PROGETTO_CORRENTE = "E.X001";

	public String KEY_MSG_CREA_DICHIARAZIONE_DI_SPESA = "W.X026";
	public String KEY_MSG_OK_INVIO_DICHIARAZIONE_DI_SPESA = "M.X027";

	/*
	 * cdu dichiarazione di spesa
	 */
	public String KEY_TUTTI_I_DOCUMENTI_SONO_VALIDI = "M.N023";
	public String KEY_ERRORE_CONTROLLO_DATE_DOCUMENTI_DICHIARAZIONE = "E.E033";
	public String KEY_MSG_DOCUMENTI_SCARTATI = "W.N030";
	public String KEY_ERRORE_IMPOSSIBILE_MODIFICARE_VOCE_DI_SPESA = "E.E151";

	/*
	 * CDU accesso al sistema
	 */
	public String KEY_MSG_NESSUN_BENEFICIARIO_DISPONIBILE = "W.ACC001";
	public String KEY_MSG_GESTISCI_PAGAMENTI_DOCUMENTO_NON_ASSOCIATO_A_PROGETTO = "E.E038";
	public String KEY_MSG_ACCESSO_NON_AUTORIZZATO = "E.E007";
	public String KEY_MSG_PASSWORD_SCADUTA = "E.E063";
	public String KEY_MSG_ACCOUNT_SCADUTO = "E.E064";
	public String KEY_MSG_ERROR_ACCESSO_GESTIONE_INCARICHI = "E.E065";
	public String KEY_SCADENZA_DELEGA = "E.E066";
	public String KEY_SCADENZA_PASSWORD = "E.E067";

	/*
	 * Validazione
	 */
	public String KEY_WARNING_OPERAZIONE_AUTOMATICA_VALIDAZIONE_M2 = "W.VAL012";
	public String KEY_WARNING_OPERAZIONE_AUTOMATICA_GESTIONE_SPESA_VALIDATA_M6 = "W.VAL013";

	/*
	 * Chiudi validazione
	 */

	public String KEY_MSG_FORMATO_FILE_NON_VALIDO = "E.M9";
	public String KEY_MSG_NESSUN_FILE_SELEZIONATO = "E.M10";
	public String KEY_MSG_SELEZIONA_ENTRAMBI_I_FILE = "E.M10bis";
	public String KEY_MSG_FILE_SIZE_TOO_LARGE = "E.M11";
	public String ERRORE_VALIDAZIONE_DATA_VALUTA_PAGAMENTO = "E.E029";
	public String ERRORE_VALIDAZIONE_DATA_EFFETTIVO_PAGAMENTO = "E.E011";
	public String ERRORE_MAX_LUNGHEZZA_CAMPO_NOTE = "E.M12";
	public String ERRORE_MAX_LUNGHEZZA_CAMPO_RIFERIMENTO = "E.M12_bis";
	public String ERRORE_MAX_IMPORTO_SUPERATO = "E.M1_bis";

	/*
	 * Gestione Backoffice
	 */
	public String KEY_MSG_ASSOCIA_ESEGUITA = "M.N035";
	public String KEY_MSG_ASSOCIA_NON_ESEGUITA = "E.E039";
	public String KEY_MSG_DISASSOCIA_ESEGUITA = "M.N036";
	public String KEY_MSG_DISASSOCIA_NON_ESEGUITA = "E.W034";
	public String KEY_ERR_CAMPI_OBBLIGATORI = "E.W008";
	public String KEY_ERR_OPERAZIONE_NON_COMPL = "E.E002";
	public String KEY_ERR_NECESSARIO_BANDO_LINEA = "W.N039";
	public String KEY_ERR_OCCORRENZA_GIA_PRESENTE = "E.E050";
	public String KEY_ERR_REGOLA_NON_ASSOCIATA_A_BANDO_LINEA = "E.E059";
	public String KEY_MSG_SOTTO_SETTORE_CIPE_NON_SELEZIONATO = "E.N042";
	public String KEY_MSG_COMUNE_E_PROVINCIA_IGNORATI = "E.N043";
	public String KEY_SWT_NECESSARIO_SALVARE_BANDO = "E.E073";
	public String KEY_SWT_NECESSARIO_SELEZIONARE_TIPO_OPERAZIONE = "E.E074";
	public String KEY_MSG_RICERCA_DOCUMENTI_SELEZIONA_BENEFICIARIO = "E.E999";
	public String KEY_ERR_MACRO_VOCE_GIA_SU_CONTO_ECONOMICO = "E.E075";
	public String KEY_ERR_MACRO_VOCE_CON_MICRO_VOCI = "E.E076";
	public String KEY_ERR_REGOLA_ASSOCIATA_A_TIPI_ANAGRAFICA = "E.E077";
	public String KEY_MSG_CONFERMA_ELIMINA_ISTRUTTORE = "W.C014";
	public String KEY_MSG_CONFERMA_ELIMINA_PERSONA_FISICA = "W.C015";
	public String KEY_ERR_UTENTE_GIA_ASSOCIATO_BENEFICIARIO_PROGETTO = "E.E083";
	public String KEY_ERR_BENEFICIARIO_PROGETTO_INCOERENTI = "E.E086";
	public String KEY_ERR_INSERIRE_ENTI_COMPETENZA_OBBLIGATORI = "W.W072";

	/*
	 * Certificazione
	 */
	public String KEY_PROPOSTA_CERTIFICAZIONE_VALIDA_OK = "M.N037";
	public String KEY_PROPOSTA_CERTIFICAZIONE_OK = "M.N038";
	public String KEY_PROPOSTA_CERTIFICAZIONE_CONFERMA = "W.C010";
	public String KEY_PROPOSTA_CERTIFICAZIONE_RINUNCIA = "W.C011";
	public String KEY_GESTIONE_CHECKLIST_PROPOSTA_CONFERMA_CANCELLAZIONE = "W.C012";
	public String KEY_REPORT_POST_GESTIONE_INVIATO = "W.C017";
	
	public String KEY_ERROR_PROPOSTA_CERTIFICAZIONE_DATA_SUPERIORE_OGGI = "E.E044";
	public String KEY_ERROR_PROPOSTA_CERTIFICAZIONE_NON_TROVATA = "E.E046";
	public String KEY_ERROR_PROPOSTA_CERTIFICAZIONE_PER_RIAPERTURA_NON_TROVATA = "E.E047";
	public String KEY_ERROR_REPORT_POST_GESTIONE_NON_INVIATO = "E.E600";


	/*
	 * Erogazione
	 */
	public String IMPORTO_DA_EROGARE_SUPERIORE_A_RESIDUO_E048 = "E.E048";
	public String IMPORTO_DA_EROGARE_SUPERIORE_A_RESIDUO_E049 = "E.E049";
	public String EROGAZIONE_DA_ASSOCIARE_SALVATA = "M.N040";
	public String NECESSARIO_ASSOCIARE_EROGAZIONE = "W.N046";
	public String NESSUNA_ASSOCIAZIONE_DISPONIBILE = "W.N041";
	public String ATTIVITA_EROGAZIONE_NON_UTILIZZABILE = "W.N050";
	/*
	 * Fideiussione
	 */
	public String FIDEIUSSIONE_NON_ELIMINABILE = "E.E061";
	public String CONFERMA_ELIMINA_FIDEIUSSIONE = "W.W037";
	public String FIDEIUSSIONE_NON_GESTIBILE = "W.W038";

	/*
	 * Richiesta Erogazione
	 */
	public String POSSIBILE_PROSEGUIRE_CREA_RICHIESTA_EROGAZIONE = "M.N047";
	public String IMPOSSIBILE_PROSEGUIRE_CREA_RICHIESTA_EROGAZIONE = "E.E973";

	public String MSG_INVIA_RICHIESTA_EROGAZIONE = "M.N048";
	public String MSG_SCARICA_RICHIESTA_EROGAZIONE = "M.N049";
	public String ATTIVITA_RICHIESTA_EROGAZIONE_NON_UTILIZZABILE = "W.N051";
	public String PERCENTUALE_EROGAZIONE_MODIFICABILE = "W.N053";
	/*
	 * Rimodulazione Conto Economico
	 */
	public String KEY_WARNING_CONCLUDI_PROPOSTA_M5 = "W.W039";
	public String KEY_WARNING_CONCLUDI_PROPOSTA_M6 = "W.W040";
	public String KEY_WARNING_CONCLUDI_PROPOSTA_M7 = "W.W041";
	public String KEY_WARNING_CONCLUDI_PROPOSTA_M8 = "W.W042";
	public String KEY_WARNING_CONCLUDI_PROPOSTA_M9 = "W.W043";
	public String KEY_MSG_CONCLUDI_PROPOSTA_CONFERMA = "W.C013";

	public String KEY_WARNING_CONCLUDI_RIMODULAZIONE_M5 = "W.W044";
	public String KEY_WARNING_CONCLUDI_RIMODULAZIONE_M6 = "W.W045";
	public String KEY_WARNING_CONCLUDI_RIMODULAZIONE_M7 = "W.W046";
	public String KEY_WARNING_CONCLUDI_RIMODULAZIONE_M8 = "W.W047";
	public String KEY_WARNING_CONCLUDI_RIMODULAZIONE_M9 = "W.W048";
	public String KEY_WARNING_CONCLUDI_RIMODULAZIONE_M11 = "W.W049";
	public String KEY_WARNING_CONCLUDI_RIMODULAZIONE_M12 = "W.W050";
	public String KEY_WARNING_CONCLUDI_RIMODULAZIONE_M13 = "W.W051";
	public String KEY_WARNING_CONCLUDI_PROPOSTA_M14 = "W.W052";
	public String KEY_WARNING_CONCLUDI_PROPOSTA_M15 = "W.W053";
	public String KEY_ERROR_CONCLUDI_RIMODULAZIONE_QUOTA_UE = "E.E127";

	public String KEY_MSG_RIMODULAZIONE_VALORI_NON_COERENTI = "E.N052";
	public String KEY_MSG_PROPOSTA_RIMODULAZIONE_INVIATA = "M.N054";
	public String KEY_MSG_RIMODULAZIONE_CONCLUSA = "M.N055";
	public String KEY_ERROR_RIMODULAZIONE_IMPORTO_NEGATIVO = "E.E079";

	public String KEY_MSG_FONTE_FINANZIARIA_SALVATA = "M.MX34";

	/*
	 * Workspace
	 */
	public String KEY_ERROR_ATTIVITA_NON_DISPONIBILE = "E.E081";
	public String KEY_ERROR_ATTIVITA_IN_ESECUZIONE = "E.E082";

	public String KEY_ERROR_DT_PAGAMENTO_SUCC_DT_DOMANDA_PREC_DT_DOC_DI_SPESA = "E.E1000";

	/*
	 * Rettifica spesa validata
	 */

	public String KEY_MSG_RETTIFICA_NESSUN_DATO_MODIFICATO = "W.N059";
	public String KEY_ERROR_RETTIFICA_LUNGHEZZA_RIFERIMENTO = "E.N060";
	public String KEY_WRN_RETTIFICA_PRESENZA_CERTIFICAZIONE = "W.W058";

	/*
	 * Gestione Revoca
	 */
	public String KEY_MSG_REVOCA_LUNGHEZZA_NOTE = "E.N056";
	public String KEY_MSG_REVOCA_LUNGHEZZA_ESTREMI_DETERMINA = "E.N057";
	public String KEY_MSG_REVOCA_NESSUNA_REVOCA_MODIFICATA = "W.N062";
	public String KEY_WRN_REVOCA_DATA_REVOCA = "E.W056";
	public String KEY_MSG_DISIMPEGNO_NESSUNA_REVOCA_MODIFICATA = "W.D.N062";
	public String KEY_WRN_DISIMPEGNO_DATA_REVOCA = "E.D.W056";
	public String KEY_MSG_REVOCA_REVOCA_TUTTO_ESEGUITO = "W.N063";

	// mofifica revoca
	public String KEY_MSG_REVOCA_NON_SELEZIONATA = "N070";
	public String KEY_WRN_REVOCA_ESISTE_PROPOSTA_CERTIFICAZIONE = "W.W061";

	/*
	 * Gestione Recuperi
	 */
	public String KEY_MSG_RECUPERO_LUNGHEZZA_ESTREMI_DETERMINA = "E.N057";
	public String KEY_MSG_RECUPERO_NESSUN_RECUPERO_MODIFICATO = "W.N061";
	public String KEY_MSG_RECUPERO_LUNGHEZZA_NOTE = "E.N056";
	public String KEY_WRN_RECUPERO_DATA_RECUPERO = "E.W057";
	public String KEY_MSG_RECUPERO_RECUPERA_TUTTO_ESEGUITO = "W.N064";
	public String KEY_MSG_RECUPERO_NON_SELEZIONATO = "E.N071";

	public String KEY_MSG_RICHIESTA_CONTOECONOMICO_INVIATA = "M.MX33";

	public String ERRORE_TIMEOUT_DICHIARAZIONE_DI_SPESA = "E.E900";

	public String KEY_ERR_MAX_NUMERO_DI_CARATTERI = "E.E112";

	/*
	 * Gestione scheda progetto
	 */
	public String KEY_ERRORI_TAB_SCHEDA_PROGETTO = "E.E200";
	public String ALLINEAMENTO_CUP_AVVENUTO_CON_SUCCESSO = "E.E202";

	/*
	 * Comunicazione rinuncia
	 */
	public String KEY_WRN_RINUNCIA_CONFERMA_INVIO = "W.W059";
	public String KEY_MSG_RINUNCIA_INVIATA = "M.W060";
	public String KEY_ERR_RINUNCIA_DATA_MAGGIORE_ODIERNA = "E.E128";

	/*
	 * Comunicazione di fine progetto
	 */
	public String KEY_WRN_INVIO_COMUNICAZIONE_FINE_PROGETTO = "W.W074";
	public String KEY_MSG_COMUNICAZIONE_FINE_PROGETTO_INVIATA = "M.W075";

	public String CONFERMA_SALVATAGGIO = "W.MX0";
	public String KEY_MSG_QUADRO_PREVISIONALE_SALVATO = "M.MX35";
	public String KEY_MSG_INFERIORE_REALIZZATO = "W.MX36";
	public String KEY_MSG_SUPERIORE_ULTIMO_PREVENTIVO = "W.MX37";
	public String KEY_MSG_INFERIORE_ULTIMO_PREVENTIVO = "W.MX38";
	public String KEY_MSG_NUOVO_PREVENTIVO_SUPERIORE_ULTIMA_SPESA_AMMESSA = "W.MX39";
	public String KEY_MSG_NUOVO_PREVENTIVO_INFERIORE_ULTIMA_SPESA_AMMESSA = "W.MX40";
	public String KEY_MSG_TOTALE_NUOVO_PREVENTIVO_INFERIORE_TOTALE_REALIZZATO = "W.MX41";
	public String KEY_MSG_TOTALI_NUOVO_PREVENTIVO_PER_VDS_SUPERIORI_ULTIMA_SPESA_AMMESSA = "W.MX42";
	public String KEY_MSG_TOTALI_NUOVO_PREVENTIVO_PER_VDS_INFERIORI_ULTIMA_SPESA_AMMESSA = "W.MX43";
	public String KEY_MSG_TOTALI_NUOVO_PREVENTIVO_PER_VDS_INFERIORI_TOTALE_REALIZZATO = "W.MX44";
	public String KEY_MSG_NUOVO_PREVENTIVO_DIVERSO_DAL_REALIZZATO = "W.MX56";

	public String CONTO_ECONOMICO_LOCKED_PER_RIMODULAZIONE = "W.MX11";
	public String CONTO_ECONOMICO_LOCKED_PER_PROPOSTA = "W.MX16";
	public String IMPORTI_NON_VALIDI = "E.MX4";

	public String RIMODULAZIONE_SALVATA = "M.MX00";

	public String IMPORTO_INFERIORE_RENDICONTATO = "W.MX5";
	public String IMPORTO_INFERIORE_QUIETANZIATO = "W.MX6";
	public String IMPORTO_INFERIORE_VALIDATO = "W.MX7";
	public String IMPORTO_INFERIORE_ULTIMA_PROPOSTA = "W.MX8";
	public String IMPORTO_SUPERIORE_AMMISSIBILE = "W.MX9";

	public String IMPORTO_SUPERIORE_RICHIESTO_IN_DOMANDA = "W.MX30";
	public String IMPORTO_INFERIORE_RICHIESTO_IN_DOMANDA = "W.MX31";

	public String PROPOSTA_SALVATA = "M.MX14";
	public String FUNZIONALITA_RIMODULAZIONE_NON_ABILITATA = "W.MX17";
	public String FUNZIONALITA_PROPOSTA_RIMODULAZIONENON_ABILITATA = "W.MX29";

	public String IMPORTO_SUPERIORE_ULTIMA_PROPOSTA = "W.MX19";

	public String TOTALE_NUOVA_PROPOSTA_SUPERIORE_ULTIMA_SPESA_AMMESSA = "W.MX20";
	public String TOTALE_NUOVA_PROPOSTA_INFERIORE_ULTIMA_SPESA_AMMESSA = "W.MX21";

	public String IMPORTO_SUPERIORE_AGEVOLATO = "W.MX22";
	public String IMPORTO_INFERIORE_AGEVOLATO = "W.MX23";

	public String IMPORTO_SUPERIORE_ULTIMA_SPESA_AMMESSA = "W.MX24";
	public String IMPORTO_INFERIORE_ULTIMA_SPESA_AMMESSA = "W.MX25";

	public String TOTALE_RIMODULAZIONE_SUPERIORE_ULTIMA_SPESA_AMMESSA = "W.MX26";
	public String TOTALE_RIMODULAZIONE_INFERIORE_ULTIMA_SPESA_AMMESSA = "W.MX27";

	public String IMPORTI_TROPPO_GRANDI = "E.MX28";

	public String RICHIESTO_IN_DOMANDA_SALVATO = "M.MX32";
	public String MESSAGE_CONFERMA_SALVA_IMPORTO_VALIDATO_SUPERIORE_AMMESSO = "W.MX3";

	/*
	 * Chiusura progetto
	 */

	public String KEY_WRN_CHIUSURA_PROGETTO_RINUNCIA_PRESENTE = "W.W076";
	public String KEY_MSG_CHIUSURA_PROGETTO_CONFERMA = "W.MX63";
	public String KEY_MSG_CHIUSURA_PROGETTO_AVVENUTA = "M.N085";

	public String IMPORTO_GIURIDICO_MINORE_DI_ZERO = "E.MX65";
	public String IMPORTO_GIURIDICO_MAGGIORE_AMMESSO = "E.MX66";
	public String IMPORTO_AGEVOLATO_MINORE_DI_ULTIMO_IMPORTO_NETTO_CERTIFICATO = "E.MX67";

	/*
	 * Soppressioni
	 */
	public String CONFERMA_ELIMINA_SOPPRESSIONE = "W.W080";

	/*
	 * Mail
	 */
	public String MAIL_VALIDAZIONE_TIMEOUT = "mail.validazione.timeout";

	/*
	 * Caricamento massivo
	 */
	public String KEY_CARICAMENTO_FILE_OK_ESITO_DOMANI = "M.N092";

	/*
	 * Caricamento file
	 */
	public String KEY_CARICAMENTO_FILE_OK = "M.N093";
	public String KEY_CARICAMENTO_FILE_TROPPE_RIGHE = "E.E250";
	public String KEY_CARICAMENTO_FILE_NUM_CAMPI_ERRATO = "E.E251";
	public String KEY_CARICAMENTO_FILE_CF_ERRATO = "E.E252";
	public String KEY_CARICAMENTO_FILE_FLAG_ERRATO = "E.E253";
	public String KEY_CARICAMENTO_FILE_NOME_ERRATO = "E.E254";
	public String KEY_CARICAMENTO_FILE_COGNOME_ERRATO = "E.E255";
	public String KEY_CARICAMENTO_FILE_CODPROG_ERRATO = "E.E256";
	public String KEY_CARICAMENTO_FILE_CODPROG_INESISTENTE = "E.E257";
	public String KEY_CARICAMENTO_FILE_NON_VALIDO= "E.E258";
	public String KEY_CARICAMENTO_FILE_CF_BENEFICIARIO_ERRATO = "E.E269";

	public String ERRORE_VOCE_DI_SPESA_GIA_ASSOCIATA = "W.V005";
	public String ERRORE_VOCE_DI_SPESA_IMPORTO_MAGGIORE_RESIDUO_DOCUMENTO = "W.V003";
	public String ERRORE_VOCE_DI_SPESA_IMPORTO_MAGGIORE_RESIDUO_VOCE = "W.V004";
	public String ERRORE_VOCE_DI_SPESA_ASSOCIATA_DOCUMENTI = "W.V005";
	public String ERRORE_VOCE_DI_SPESA_IMPORTO_MINORE_PAGAMENTI = "W.V006";
	public String ERRORE_VOCE_DI_SPESA_ASSOCIATA_PAGAMENTI = "W.V008";
	public String ERRORE_VOCE_DI_SPESA_RESIDUO_DISPONIBILE_NULLO = "W.V002";

	public String ERRORE_SERVIZIO_IRIDE = "E.IRIDE001";
	public String ERRORE_GENERICO_IRIDE = "E.IRIDE002";
	
	public String KEY_MSG_RICHIESTA_AUTOMATICA_DEL_CUP_IMPOSTATA_A_NO = "M.N094";
	public String KEY_AVVIO_PRENOTATO="M.PAP";
	

	public String ERROR_FOTOGRAFIA_ESISTE = "E.E290";
	public String ERROR_FOTOGRAFIA_NESSUN_APPROVATA_TROVATA = "E.E291";
	
	public String KEY_MSG_ACQUISIZIONE_CAMPIONE_SUCCESSO = "M.RC001";
	public String KEY_MSG_ACQUISIZIONE_CAMPIONE_PRESENTE = "M.RC002";
	public String KEY_MSG_NESSUN_PROGETTO_SELEZIONATO = "E.RC003";
}
