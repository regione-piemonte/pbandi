/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.util;

public class Constants {

	public final static String COMPONENT_NAME = "pbservwelfare";

	public static final String USERINFO_SESSIONATTR = "currentUserInfo";

	public static final String[] STATO_PROGETTO_NON_ABILITATO = { "S", "R", "C", "CU", "SA" };
	
	public static final Integer CONTRODEDUZIONE_INVIATA = 2;
	
	public static final String DOCUMENTAZIONE_INTEGRAZIONE_AUTORIZZATA = "1";
	
	public static final String CODICE_BANDO_DOMICILIARITA = "DOM";

	public static final String CODICE_BANDO_RESIDENZIALITA = "RES";

	public static class SQL {
		public static int SQL_TIMEOUT_EXCEPTION_CODE = 1013;
		public static int DEFAULT_QUERY_TIMEOUT = 290;
	}

	public static class ESITO {
		public static String KO = "KO";
		public static String OK = "OK";
	}

	public static class MESSAGGI {
		public static class ERRORE {
			public static String CODICE_FISCALE_ERRATO = "Il codice fiscale del beneficiario non corrisponde alla domanda inviata";
			public static String PARAMETRI_INVALIDI = "Parametri di ricerca non corretti";
			public static String PROGETTO_NON_PRESENTE = "Il progetto non è presente";
			public static String SOGGETTO_NON_ASSOCIATO_AL_PROGETTO = "Il soggetto non risulta associato al progetto indicato";
			public static String SOGGETTO_NON_CENSITO_COME_FORNITORE = "Il soggetto non è censito come fornitore del progetto indicato";
			public static String PROGETTO_NON_ABILITATO = "Progetto non abilitato alla rendicontazione";
			public static String PARAMETRI_INSUFFICIENTI = "Parametri non corretti/insufficienti";
			public static String CONTRODEDUZIONE_IN_ELABORAZIONE = "La controdeduzione è in elaborazione";
			public static String NESSUN_DATO_SELEZIONATO = "Nessun dato selezionato";
			public static String RICHIESTA_NON_IN_ELABORAZIONE = "La richiesta di integrazione non è in elaborazione";
			public static String RICHIESTA_INTEGRAZIONE_CHIUSA = "La richiesta di integrazione è chiusa";
			public static String RICHIESTA_INTEGRAZIONE_SCADUTA = "La richiesta di integrazione è scaduta";
			public static String GENERICO = "Errore generico";
			public static String CONNESSIONE_DATABASE = "Il data base non risponde";
			public static String DOCUMENTAZIONE_NON_ACQUISITA = "Non è stato possibile acquisire la documentazione";
			public static String NESSUN_FILE_TROVATO_FORNITORE = "Attenzione! Non è stato passato alcun file relativo al contratto per il nuovo fornitore";
			public static String SERVIZIO_NON_ATTIVO = "Il servizio non è attivo";
		}

	}

}
