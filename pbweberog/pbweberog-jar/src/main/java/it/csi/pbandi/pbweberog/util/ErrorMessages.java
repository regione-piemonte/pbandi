/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.util;

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
	public static final String SERVICE_EXCEPTION= "Service it.csi.pbandi.pbweberog.exception";
	public static final String NO_CONTENT = "nessun dato contenuto";
	public static final String NOT_FOUND = "Dato non trovato";
	
	//Erogazione
	public static final String IMPORTO_DA_EROGARE_SUPERIORE_A_RESIDUO_E049 = "La quota da erogare é superiore al residuo spettante.";
	public static final String ERRORE_SOMMA_EROGAZIONI_PER_MOD_AGEVOLAZ_SUPERIORE_IMPORTO_AGEVOLATO_CONTO_ECONOMICO = "Per la modalità di agevolazione, la somma delle erogazioni al netto dei recuperi é maggiore della quota dell'importo agevolato.";
	public static final String IMPORTO_DA_EROGARE_SUPERIORE_A_RESIDUO_E048 = "La quota da erogare è superiore al residuo spettante. Usare una causale relativa all'iter finanziario non standard.";
	public static final String ERRORE_IMPOSSIBILE_CREARE_PDF = "Impossibile produrre il file PDF.";
	
	//Fideiussione
	public static final String FIDEIUSSIONE_NON_ELIMINABILE = "La fideiussione é stata usata in una erogazione. Non é possibile procedere con l'eliminazione.";
	public static final String CAMPO_OBBLIGATORIO = "Campo obbligatorio.";
	public static final String VALORE_IMPORTO_NON_CORRETTO = "L'importo deve essere maggiore di 0.";
	public static final String FORMATO_DATA_NON_CORRETTO = "Formato della data non corretto.";
	public static final String ERR_MAX_NUMERO_DI_CARATTERI = "Il campo non può contenere più di 20 caratteri.";
	public static final String ERRORE_SERVER = " Non e' stato possibile portare a termine l'operazione.";
	public static final String FIDEIUSSIONE_NON_GESTIBILE = "L'attività di gestione delle fideiussioni non é abilitata.";

	//Rinuncia
	public static final String ERR_RINUNCIA_DATA_MAGGIORE_ODIERNA = "La data della dichiarazione non può essere maggiore alla data odierna.";
	
	//Richiesta erogazione
	public static final String ERRORE_BANDO_NON_GESTISCE_RICHIESTA_EROGAZIONE = "Per il progetto corrente, l'attività di richiesta erogazione non è stata abilitata.";
	public static final String ERRORE_EROGAZIONE_IMPORTO_AGEVOLATO_NULLO = "L'importo agevolato è uguale a zero. Impossibile procedere con la richiesta di erogazione.";
	public static final String ERRORE_EROGAZIONE_SPESA_SOSTENUTA_ZERO = "La spesa sostenuta risulta pari a 0 Euro e non è supportata da fideiussione bancaria. Non sarebbe possibile procedere con l'invio della richiesta di erogazione.";
	public static final String ERRORE_EROGAZIONE_IMPORTO_FIDEIUSSIONE_MINORE_ANTICIPO = "L'importo della fideiussione è minore dell'importo che si sta richiedendo. Non sarebbe possibile procedere con l'invio della richiesta di erogazione.";
	public static final String ERRORE_EROGAZIONE_SPESA_SOSTENTUTA_MINORE_SPESA_SOGLIA_BANDO_SENZA_FIDEIUSSIONE = "La spesa sostenuta non raggiunge la soglia prevista dal bando e non è supportata da fideiussione bancaria. Non sarebbe possibile procedere con l'invio della richiesta di erogazione.";
	public static final String ERROR_ID_DOC_INDEX_NON_GENERATO = "Nuovo ID PBANDI_T_DOCUMENTO_INDEX non generato.";
	
	//Gestione disimpegni
	public static final String DATA_REVOCA = "La data della revoca non può essere successiva alla data odierna.";
	public static final String MSG_REVOCA_LUNGHEZZA_NOTE = "Il campo note non può contenere più di 4000 caratteri.";
	public static final String MSG_REVOCA_LUNGHEZZA_ESTREMI_DETERMINA = "Il campo Estremi determina non può contenere più di 255 caratteri.";
	public static final String ERROR_DISIMPEGNO_IMPORTO_REVOCA_NEGATIVO = "Il nuovo importo del disimpegno deve essere maggiore o uguale a zero.";
	public static final String ERROR_DISIMPEGNO_RECUPERATO_MAGGIORE_REVOCATO = "Per le modalità di agevolazione, il totale recuperato è superiore al totale revocato.";
	public static final String ERROR_REVOCA_TOTALE_REVOCATO_SUPERIORE_AGEVOLATO = "Per la modalità di agevolazione, la somma degli importi disimpegnati è superiore all'importo agevolato. Correggere l'importo della nuova revoca.";
	public static final String ERROR_REVOCA_IMPORTO_REVOCA_NEGATIVO = "Il nuovo importo della revoca deve essere maggiore o uguale a zero.";
	public static final String ERROR_REVOCA_IMPORTO_GIA_EROGATO_NON_PRESENTE = "Per la modalità di agevolazione non ci sono erogazioni. Non è possibile revocare.";
	public static final String ERROR_REVOCA_RECUPERATO_MAGGIORE_REVOCATO = "Per le modalità di agevolazione, il totale recuperato è superiore al totale revocato.";
	public static final String ERROR_REVOCA_TOTALE_REVOCATO_SUPERIORE_EROGAZIONI = "Per la modalità di agevolazione, la somma degli importi disimpegnati è superiore all'importo agevolato. Correggere l'importo della nuova revoca.";
	
	// Registro controlli
	public static final String ERRORE_IRREGOLARITA_BLOCCATA_MOD = "L'irregolarita è bloccata. Non è possibile procedere alla modifica. Rivolgersi alla Regione Piemonte per lo sblocco.";
	public static final String ERRORE_IRREGOLARITA_VERSIONE_MOD = "E' possibile aggiornare solo l'ultima versione dell'irregolarità.";
	public static final String ERRORE_IRREGOLARITA_INVIATA_CANC = "L'irregolarità risulta INVIATA. Non è possibile procedere con l'eliminazione.";
	public static final String ERRORE_IRREGOLARITA_BLOCCATA_CANC = "L'irregolarità risulta BLOCCATA. Non è possibile procedere con l'eliminazione.";
	public static final String ERRORE_IRREGOLARITA_PROVV_LEGATA_A_DEF = "L'irregolarità provvisoria è legata ad una irregolarità definitiva. Non è possibile procedere con l'eliminazione.";
	public static final String ERRORE_IRREGOLARITA_PROVV_LEGATA_A_REG = "L'irregolarità provvisoria è legata ad un esito regolare. Non è possibile procedere con l'eliminazione.";
	
	//Recuperi
	public static final String WRN_RECUPERO_DATA_RECUPERO = "La data del recupero non può essere successiva alla data odierna.";
	public static final String ERRORE_RECUPERO_NESSUNA_REVOCA = "Per la modalità di agevolazione non ci sono revoche. Non è possibile un recupero.";
	public static final String ERRORE_RECUPERO_IMPORTO_RECUPERO_NEGATIVO = "Il nuovo importo del recupero deve essere maggiore o uguale a zero.";
	public static final String ERRORE_RECUPERO_TOTALE_MAGGIORE_REVOCHE = "Per la modalità di agevolazione, la somma degli importi recuperati è superiore all'importo degli importi revocati. Correggere l'importo del nuovo recupero.";
	public static final String ERRORE_RECUPERO_TOTALE_PROGETTO_MAGGIORE_REVOCHE = "Per il progetto, la somma degli importi recuperati è superiore alla somma degli importi revocati. Correggere l'importo del nuovo recupero.";
	public static final String WARN_RECUPERO_TOTALE_RECUPERATO_SUPERIORE_TOTALE_REVOCATO = "Per la modalità di agevolazione, il totale recuperato è superiore al totale revocato. Correggere l'importo del recupero.";
	public static final String WARN_RECUPERO_RECUPERO_CERTIFICATO = "Il recupero risulta certificato. Si consiglia di inserire una motivazione alla modifica eseguita nel campo denominato note.";
	public static final String ERRORE_SOPPRESSIONE_PRESENTE_CERTIFICAZIONE_RECUPERO = "La soppressione è stata certificata. Non è possibile eliminarla.";
	public static final String ERRORE_RECUPERO_PRESENTE_CERTIFICAZIONE_RECUPERO = "Il recupero è stato certificato. Non è possibile eliminarlo.";
	
	
	//Revoche
	public static final String WRN_REVOCA_DATA_RECUPERO = "La data del revoca non può essere successiva alla data odierna.";
	public static final String ERROR_REVOCA_CANCELLAZIONE_TOTALE_REVOCATO_INFERIORE_TOTALE_RECUPERATO = "Per la modalita' di agevolazione, con l'eliminazione della revoca selezionata, si ottiene un totale revocato inferiore al totale recuperato. Non e' possibile eliminare la revoca.";
	public static final String ERROR_REVOCA_PRESENTE_CERTIFICAZIONE_REVOCA = "La revoca è stata certificata. Non è possibile eliminarla.";
	public static final String ERROR_REVOCA_RIDURRE_IMPORTO_REVOCA = "Ridurre l'importo della nuova revoca.";
	public static final String ERROR_ESISTE_PROP_CERTIF = "Per il progetto è stata creata almeno una proposta di certificazione. Eventuali modifiche alle revoche del progetto potranno avere effetto sugli importi certificati.";

	
	
}