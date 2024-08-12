package it.csi.pbandi.pbweb.util;

public class ErrorMessages {
	
	public static final String SALVATAGGIO_AVVENUTO_CON_SUCCESSO = "Salvataggio avvenuto con successo.";
	public static final String CANCELLAZIONE_AVVENUTA_CON_SUCCESSO = "Cancellazione avvenuta con successo.";
	public static final String OPERAZIONE_ESEGUITA_CON_SUCCESSO = "Operazione eseguita con successo.";
	
	public static final String ERRORE_GENERICO = "Si \u00E8 verificato un errore. Si prega di contattare il servizio assistenza.";
	public static final String ERRORE_SERVER = "Non \u00E8 stato possibile portare a termine l\u2019 operazione.";
	
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
	public static final String SERVICE_EXCEPTION= "Service it.csi.pbandi.pbweb.exception";
	public static final String NO_CONTENT = "nessun dato contenuto";
	public static final String NOT_FOUND = "Dato non trovato";
	
	
	public static final String ERROR_ZERO_FILE_SIZE = "Caricare un file con dimensione maggiore di 0 byte.";
	public static final String ERROR_MAX_FILE_SIZE = "I file non possono eccedere la dimensione di {0} Mb.";
	public static final String ERROR_USER_SPACE_FULL = "I file per utente non possono eccedere la dimensione globale di {0} Mb";
	public static final String ERROR_INVALID_FILE_EXTENSION = "Estensione di file non valida. (Tipi di file accettati: {0})";
	public static final String ERROR_DUPLICATED_FILE_NAME_PER_FOLDER = "Nella cartella selezionata \u00E8 gi\u00E0 presente un file con lo stesso nome.";
	
	public static final String CODICE_FISCALE_FORMALMENTE_SCORRETTO = "Il codice fiscale non \u00E8 formalmente corretto";
	public static final String FORNITORE_ASSOCIATO_AFFIDAMENTO = "IMPOSSIBILE MODIFICARE IL FORNITORE IN QUANTO E&rsquo; LEGATO AD UN AFFIDAMENTO.";	
	public static final String ERRORE_VOCE_DI_SPESA_ASSOCIATA_PAGAMENTI = "Per la voce di spesa scelta, sono stati registrati dei pagamenti. <br />Eliminare i relativi pagamenti prima di eliminare l\u2019associazione alla voce di spesa.";

	public static final String ERRORE_FORNITORE_GIA_USATO = "Esiste gi\u00E0 un fornitore con gli stessi dati.";
	
	// Salvataggio documento di spesa.
	public static final String ERRORE_DOCUMENTO_DI_SPESA_DATA_SUPERIORE_OGGI = "La data del documento di spesa non pu\u00F2 essere successiva alla data odierna.";
	public static final String ERRORE_DOCUMENTO_DI_SPESA_GIA_INSERITO = "Il documento di spesa \u00E8 gi\u00E0 stato salvato nella banca dati.";	
	public static final String ERRORE_DOCUMENTO_DI_SPESA_DATA_SUPERIORE_DOMANDA = "La data del documento di spesa deve essere successiva alla data di presentazione della domanda.";
	public static final String ERRORE_DOCUMENTO_DI_SPESA_NOTA_DI_CREDITO_NON_ASSOCIABILE_PER_PAGAMENTI_PRESENTI = "Non e\u2019 possibile associare la nota di credito poiche\u2019 sono stati gia\u2019 inseriti dei pagamenti per la fattura di riferimento.";
	public static final String ERRORE_DOCUMENTI_DI_SPESA_TOTALE_FATTURA_MINORE_TOTALE_RENDICONTABILI = "L\u2019 imponibile piu\u2019 l\u2019iva a costo della fattura di riferimento, al netto delle note di credito, risulta essere minore del totale dei rendicontabili.";
	public static final String ERRORE_DOCUMENTO_DI_SPESA_IMPORTO_RENDICONTABILE_NOTA_CREDITO = "L\u2019importo rendicontabile della nota di credito deve essere minore o uguale al rendicontabile sul progetto del documento di riferimento al netto di eventuali altre note di credito gi\u00E0 esistenti.";
	public static final String ERRORE_DOCUMENTI_DI_SPESA_TOTALE_DOCUMENTO_MINORE_TOTALE_RENDICONTABILI = "Il rendicontabile risulta essere maggiore dell\u2019 imponibile piu\u2019 l\u2019iva a costo del documento, al netto delle note di credito.";
	public static final String ERRORE_DOCUMENTI_DI_SPESA_TOTALE_DOCUMENTO_MINORE_TOTALE_RENDICONTABILI_NO_IVA_COSTO = "Il rendicontabile risulta essere maggiore dell\u2019 imponibile del documento, al netto delle note di credito.";
	public static final String ERRORE_DOCUMENTO_DI_SPESA_IVA_A_COSTO_SUPERIORE = "L\u2019importo dell\u2019IVA a costo non deve essere superiore all\u2019IVA.";
	public static final String ERRORE_DOCUMENTO_DI_SPESA_CEDOLINO_O_AUTODICHIARAZIONE_SOCI_IMPORTO_RENDICONTABILE_SUPERIORE = "L\u2019importo rendicontabile non deve essere superiore all\u2019imponibile.";
	public static final String ERRORE_DOCUMENTO_DI_SPESA_IMPORTO_RENDICONTABILE_SUPERIORE_NO_IVA_COSTO = "L\u2019importo rendicontabile non deve essere superiore all\u2019imponibile.";
	public static final String ERRORE_DOCUMENTO_DI_SPESA_IMPORTO_RENDICONTABILE_SUPERIORE = "L\u2019importo rendicontabile non deve essere superiore alla somma dell\u2019imponibile pi\u00F9 l\u2019IVA a costo.";
	public static final String WARNING_DOCUMENTO_DI_SPESA_TOTALE_PAGAMENTI_MAGGIORE_TOTALE_DOCUMENTO = "La somma del quietanzato \u00E8 maggiore del totale del documento di spesa al netto delle note di credito.";
	public static final String WARNING_DOCUMENTO_DI_SPESA_TOTALE_QUOTE_PARTE_MAGGIORE_RENDICONTABILE = "La somma degli importi gi\u00E0 associati alle voci di spesa per il documento, risulta maggiore all\u2019 importo rendicontabile indicato.";
	public static final String WARNING_DOCUMENTO_DI_SPESA_IMPORTO_RENDICONTABILE_MAGGIORE_TOTALE = "Il totale del rendicontabile associato ai progetti \u00E8 maggiore dell\u2019importo massimo rendicontabile del documento di spesa.";
	public static final String WARNING_DOCUMENTO_DI_SPESA_DATA_DOCUMENTO_INFERIORE_DATA_COSTITUZIONE = "La data del documento di spesa deve essere successiva alla data di costituzione dell\u2019 azienda.";
	
	// Cancellazione documento di spesa.
	public static final String ERRORE_DOCUMENTO_DI_SPESA_NON_ELIMINABILE_NON_ASSOCIATO_AL_PROGETTO = "Non e\u2019 possibile eliminare il documento di spesa poiche\u2019 non risulta associato al progetto.";
	public static final String ERRORE_DOCUMENTO_DI_SPESA_NON_ELIMINABILE = "E\u2019 possibile eliminare un documento di spesa solo se il suo stato \u00E8 <b>INSERITO</b>.";
	public static final String ERRORE_DOCUMENTO_DI_SPESA_RIFERITO_A_NOTA_DI_CREDITO = "Il documento di spesa non pu\u00F2 essere cancellato perch\u00E9 \u00E8 riferito da una nota di credito.";
	public static final String ERRORE_DOCUMENTO_DI_SPESA_PAGAMENTI_NON_ELIMINABILI = "ERRORE: il documento di spesa non puo\u2019 essere cancellato perche\u2019 e\u2019 associato a uno o piu\u2019 pagamenti inviati in dichiarazioni di spesa.";

	// Ricerca documenti di spesa.
	public static final String ERRORE_FORNITORI_TROVAI_TROPPI_RISULTATI = "Trovati troppi risultati. E\u2019 necessario inserire almeno un altro campo di ricerca.";
	
	
	
}