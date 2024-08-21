/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.util;

public class Constants {

	public final static String COMPONENT_NAME = "pbservrest";

	public static final String USERINFO_SESSIONATTR = "currentUserInfo";
	
	public static class SQL
	{
	    public static int SQL_TIMEOUT_EXCEPTION_CODE = 1013;
	    public static int DEFAULT_QUERY_TIMEOUT      = 290;
	}
	
	
	public static class ESITO
	{
		public static String KO      = "KO";
		public static String OK      = "OK";
	}
	
	public static class MESSAGGI
	{
		public static class ERRORE
		{
			public static String CODICE_FISCALE_ERRATO = "Il codice fiscale del beneficiario non corrisponde alla domanda inviata";
		    public static String PARAMETRI_INVALIDI = "Parametri di ricerca non corretti";
		    public static String ERRORE_GENERICO = "Errore generico";
		    public static String DATI_ESISTENTI = "Dati esistenti";
		    public static String ERRORE_CONESSIONE_DATABASE = "Il data base non risponde";
		    public static String MATCH_BENEFICIARIO_DOMANDA = "Non esiste un match tra beneficiario e domanda sul sistema PBANDI";
		    public static String PARAMETRI_OBBLIGATORI = "I parametri passati dalla chiamata di (FINISTR) sono tutti obbligatori";
		    public static String NO_DATO_SELEZIONATO = "Nessun dato selezionato";
		    public static String ERRORE_INSERIMENTO_UTENTE = "Non é stato possibile inserire utente a sistema!";
		    public static String ERRORE_INSERIMENTO_RICHIESTA = "Non é stato possibile inserire richiesta a sistema!";
		    public static String ERRORE_INSERIMENTO_SOGGETTO = "Non é stato possibile inserire soggetto a sistema!";
		    // msg per CupApi
		    public static String PARAMETRI_TUTTI_OBBLIGATORI = "I parametri passati dalla chiamata sono tutti obbligatori";
		    public static String DOMANDA_NON_TROVATA = "La domanda indicata non è presente nella base dati.";
		    public static String AGGIORNAMENTO_CUP_FALLITO = "L'aggiornamento del codice cup è fallito.";
		}
	}
}
