/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.util;

public class ErrorMessages {

	 //MESSAGGI 
	public static final String CODE_37_INDEX_INVALID_CREDENTIAL= "37";
	public static final String MESSAGE_37_INDEX_INVALID_CREDENTIAL = "Credenziali Index non valide";

	public static final String CODE_38_INDEX_DELETE_EXCEPTION= "38";
	public static final String MESSAGE_38_INDEX_DELETE_EXCEPTION = "Delete su index error";

	public static final String CODE_39_INDEX_NODE_NON_PRESENTE= "39";
	public static final String MESSAGE_39_INDEX_NODE_NON_PRESENTE = "Node su index non presente";

	public static final String CODE_40_INDEX_PARAMETRI_NON_VALIDI= "40";
	public static final String MESSAGE_40_INDEX_PARAMETRI_NON_VALIDI = "Parametri Index non validi";
	
	public static final String CODE_41_INDEX_ERRORE_TRANSACTION= "41";
	public static final String MESSAGE_41_INDEX_ERRORE_TRANSACTION = "Transazione su Index error";
	
	public static final String CODE_42_INDEX_PERMISSION_DENIED= "42";
	public static final String MESSAGE_42_INDEX_PERMISSION_DENIED = "Permission denied su Index";
	
	public static final String SERVER_NON_DISPONIBILE = "Server indisponibile";
	public static final String FILE_NOT_FOUND = "File not found";
	public static final String MALFORMED_URL = "Malformed URL";
	public static final String SERVICE_EXCEPTION= "Service it.csi.pbandi.pbservizit.exception";
	public static final String NO_CONTENT = "nessun dato contenuto";
	public static final String NOT_FOUND = "Dato non trovato";
	
	
	public static final String ERROR_ZERO_FILE_SIZE = "Caricare un file con dimensione maggiore di 0 byte.";
	public static final String ERROR_MAX_FILE_SIZE = "I file non possono eccedere la dimensione di {0} Mb.";
	public static final String ERROR_USER_SPACE_FULL = "I file per utente non possono eccedere la dimensione globale di {0} Mb";
	public static final String ERROR_INVALID_FILE_EXTENSION = "Estensione di file non valida. (Tipi di file accettati: {0})";
	public static final String ERROR_DUPLICATED_FILE_NAME_PER_FOLDER = "Nella cartella selezionata \u00E8 gi\u00E0 presente un file con lo stesso nome.";

	public static final String ERRORE_CANCELLA_VARIANTE = "Errore durante l'eliminazione della variante.";
	public static final String ERRORE_CANCELLA_FORNITORE = "Errore durante l'eliminazione del fornitore";
	public static final String ERRORE_FORMATO_DATA = "La data deve essere inserita nella forma gg/mm/aaaa.";
	public static final String ERRORE_CAMPO_OBBLIGATORIO = "Campo obbligatorio.";
	
	public static final String ERRORE_VALORE_NON_AMMESSO = "Valore non ammesso.";
	public static final String KEY_ERR_VALORE_INIZIALE_MANCANTE_MON = "Se si inserisce un valore finale, é obbligatorio inserire il corrispondente valore iniziale.";
	public static final String KEY_ERR_VALORE_FINALE_MANCANTE_MON = "Se si inserisce un valore iniziale, é obbligatorio inserire il corrispondente valore finale.";
	public static final String KEY_ERR_VALORE_INIZIALE_MON = "Per ogni tipo di indicatore di Monitoraggio, é obbligatorio inserire almeno un valore iniziale.";
	public static final String KEY_ERR_VALORE_FINALE_MON = "Per almeno un indicatore di Monitoraggio di ciascun tipo, é obbligatorio inserire un valore finale.";
	public static final String ERRORE_SERVER = "Non e' stato possibile portare a termine l'operazione.";
	public static final String ERRORE_PROCEDURA_AGGIUDICAZIONE_CIG_GIA_ASSEGNATO = "Il CIG (codice identificativo gara) é già stato assegnato alla procedura di aggiudicazione del progetto: ";
	public static final String ERRORE_PROCEDURA_AGGIUDICAZIONE_CPA_GIA_ASSEGNATO = "Per il progetto é già stata inserita una procedura di aggiudicazione con questi CPA (codice procedura aggiudicazione).";
	public static final String SALVATAGGIO_AVVENUTO_CON_SUCCESSO = "Salvataggio avvenuto con successo.";
	public static final String DIMENSIONE_CAMPO_STRINGA = "Il campo eccede la dimensione massima consentita.";
	public static final String IMPORTI_NON_VALIDI = "Gli importi evidenziati non sono validi.";
	public static final String FORMATO_NUMERO_LUNGHEZZA_ERRATO = "Formato e/o numero di cifre dell\\u2019importo non corretto. (Max 15 cifre per la parte intera).";
	public static final String FORMATO_DATA = "La data deve essere inserita nella forma gg/mm/aaaa.";
	
	//CRONOPROGRAMMA
	public static final String KEY_MSG_DATE_NON_OBBLIGATORIE_CRONO = "Anche per le fasi non obbligatorie, se si valorizza la data iniziale è comunque necessario valorizzare la data finale e viceversa.";
	public static final String KEY_ERR_DATE_INCOERENTI_CRONO = "Date di inizio/fine incoerenti per la fase.";
	public static final String KEY_MSG_DATA_PREVISTA_FINE_MINORE_INIZIO_CRONO = "Una data fine prevista deve essere uguale o successiva alla data di inizio prevista.";
	public static final String KEY_ERR_DATA_PRECEDENTE_PRESENTAZIONE_CRONO = "Data precedente alla data di presentazione domanda.";
	public static final String KEY_MSG_DATE_PREVISTA_ED_EFFETTIVE_PRIMA_FASE_CRONO = "La data di inizio prevista e la data effettiva devono essere successive alla data di presentazione della domanda.";
	public static final String KEY_MSG_DATE_EFFETTIVE_OBBLIGATORIE_CRONO = "E' necessario inserire la data di inizio e fine effettiva.";
	public static final String KEY_MSG_MANCA_DATA_INIZIO_PREVISTA_CRONO = "Se si valorizza la data di inizio effettiva è necessario valorizzare la data di inizio prevista.";
	public static final String KEY_MSG_MANCA_DATA_FINE_PREVISTA_CRONO = "Se si valorizza la data di fine effettiva è necessario valorizzare la data di fine prevista.";
	public static final String KEY_MSG_DATA_EFFETTIVA_FINE_MINORE_INIZIO_CRONO = "Una data fine effettiva deve essere uguale o successiva alla data di inizio effettiva.";
	public static final String KEY_ERR_DATA_FUTURA_CRONO = "Data successiva alla data odierna.";
	public static final String KEY_MSG_DATE_EFFETIVE_FUTURE_CRONO = "La data di inizio effettiva e la data di fine effettiva non possono essere successive alla data odierna.";
	public static final String KEY_MSG_MANCA_MOTIVO_SCOSTAMENTO_CRONO = "Se le date di inizio/fine effettiva sono sfasate rispetto alle date di inizio/fine prevista è obbligatorio selezionare un motivo di scostamento.";
	public static final String KEY_MSG_MANCA_INDICATORE_CORE_CRONO = "Per completare l'inserimento dati del cronoprogramma è necessario inserire il valore finale per almeno un indicatore di tipo CORE utilizzando l'attività Gestione indicatori.";
	
	public static final String OPERAZIONE_ESEGUITA_CON_SUCCESSO = "Operazione eseguita con successo.";

}