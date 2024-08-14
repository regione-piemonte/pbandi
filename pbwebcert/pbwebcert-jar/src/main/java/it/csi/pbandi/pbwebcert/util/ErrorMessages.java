/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.util;

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
	public static final String SERVICE_EXCEPTION= "Service it.csi.pbandi.pbwebcert.exception";
	public static final String NO_CONTENT = "nessun dato contenuto";
	public static final String NOT_FOUND = "Dato non trovato";
	
	public static final String ERROR_FOTOGRAFIA_ESISTE = "Esiste già una certificazione intermedia finale per il periodo contabile in corso.";
	public static final String ERROR_FOTOGRAFIA_NESSUN_APPROVATA_TROVATA = "Non esiste una certificazione approvata per il periodo contabile in corso.";
	public static final String ERRORE_GENERICO = "Errore generico durante nella creazione intermedia finale.";
	public static final String ERROR_PROCEDURA_CAMPIONAMENTO_PRESENTE = "Esiste già un campione per la normativa selezionata!";
	public static final String ERROR_UPLOAD_FILE = "Erore durante l'esecuzione upload di uno o piu file nel file storage.";
	public static final String ERROR_ID_DOC_INDEX_NON_GENERATO = "Nuovo ID PBANDI_T_DOCUMENTO_INDEX non generato.";
	public static final String ERROR_FILE_NON_SALVATO_SU_INDEX = "Il file non salvato su PBANDI_T_DOCUMENTO_INDEX.";
	public static final String ERROR_FILE_NON_CANCELLATO_DA_INDEX = "Errore durante la cancellazione di uno o più file da PBANDI_T_DOCUMENTO_INDEX.";
	public static final String ERROR_FILE_NON_CANCELLATO_DA_FS = "Errore durante la cancellazione di uno o più file da File System";
	public static final String ERROR_DESCRIZIONE_ALLEGATO_NON_MODIFICATO = "Errore durante la modificazione descrizione di uno o più file.";
	public static final String ERROR_DESCRIZIONE_TUTTI_ALLEGATI_NON_MODIFICATI = "Errore durante la modificazione descrizione di tutti i file.";
	
	
	public static final String ERRORE_SERVER = "Non é stato possibile portare a termine l'operazione.";
	public static final String ERROR_AGGIORNA_IMPORTO_NETTO_NON_ESEGUITO = "Errore durante l'aggiornamento di Importo Certificazione Netto!";
	public static final String ERRORE_CHIUSURA_CONTI = "Impossibile aggiornare lo stato della proposta di certificazione.";
	public static final String ERRORE_CERTIFICAZIONE_PROPOSTA_GIA_PRESENTE = "E' già presente una richiesta di elaborazione nuova proposta in coda o in fase di esecuzione. <br />Attendere la sua esecuzione o contattare l'assistenza.";
	
	
}