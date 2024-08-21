/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.intf;


public class MessageConstants {

	//Affidamenti
	public static final String ERRORE_FORNITORI_TROVAI_TROPPI_RISULTATI = "Trovati troppi risultati. E' necessario inserire almeno un altro campo di ricerca.";
	public static final String MSG_CANCELLA_VARIANTE_SUCCESSO = "Variante eliminata con successo.";
	public static final String MSG_CANCELLA_FORNITORE_SUCCESSO = "Fornitore eliminato con successo.";
	public static final String MSG_CANCELLA_AFFIDAMENTO_SUCCESSO = "Affidamento eliminato con successo.";
	public static final String MSG_INVIA_IN_VERIFICA_SUCCESSO = "La verifica affidamento è stata inviata tramite e-mail.";
	public static final String MSG_SALVA_SUCCESSO = "Salvataggio avvento con successo.";
	public static final String MSG_AFFIDAMENTO_RESPINTO = "Affidamento respinto con successo";
	
	//CronoProgramma
	public static final String CONFERMA_SALVATAGGIO = "Confermare il salvataggio dei dati.";
	
	
	//Indicatori
	public static final String CONFERMA_SALVATAGGIO1 = "Confermare il salvataggio dei dati inseriti.";
	
	//quadro previsionale
	public static final String ERRORE_MAX_LUNGHEZZA_CAMPO_NOTE = "Nel campo note inserire al massimo 4000 caratteri.";
	public static final String IMPORTI_NON_VALIDI = "Gli importi evidenziati non sono validi.";
	public static final String KEY_MSG_INFERIORE_REALIZZATO = "Gli importi contrassegnati con ( a ) sono inferiori rispetto al relativo realizzato.";
	public static final String KEY_MSG_SUPERIORE_ULTIMO_PREVENTIVO = "Gli importi contrassegnati con ( b ) sono superiori rispetto al relativo ultimo preventivo.";
	public static final String KEY_MSG_INFERIORE_ULTIMO_PREVENTIVO = "Gli importi contrassegnati con ( c ) sono inferiori rispetto al relativo ultimo preventivo.";
	public static final String KEY_MSG_NUOVO_PREVENTIVO_DIVERSO_DAL_REALIZZATO = "Gli importi contrassegnati con ( g ) relativi a periodi gi\\u00E0 trascorsi sono diversi dal relativo realizzato.";
	public static final String KEY_MSG_NUOVO_PREVENTIVO_SUPERIORE_ULTIMA_SPESA_AMMESSA = "Il totale del nuovo preventivo é maggiore rispetto al totale dell'ultima spesa ammessa.";
	public static final String KEY_MSG_NUOVO_PREVENTIVO_INFERIORE_ULTIMA_SPESA_AMMESSA = "Il totale del nuovo preventivo é minore rispetto al totale dell'ultima spesa ammessa.";
	public static final String KEY_MSG_TOTALE_NUOVO_PREVENTIVO_INFERIORE_TOTALE_REALIZZATO = "Il totale del nuovo preventivo é minore rispetto al totale del realizzato.";
	public static final String KEY_MSG_TOTALI_NUOVO_PREVENTIVO_PER_VDS_INFERIORI_TOTALE_REALIZZATO = "I totali del nuovo preventivo per voce di spesa contrassegnati con ( f ) sono inferiori rispetto al relativo totale del realizzato.";
	public static final String KEY_MSG_TOTALI_NUOVO_PREVENTIVO_PER_VDS_SUPERIORI_ULTIMA_SPESA_AMMESSA = "I totali del nuovo preventivo per voce di spesa contrassegnati con ( d ) sono superiori rispetto alla relativa ultima spesa ammessa.";
	public static final String KEY_MSG_TOTALI_NUOVO_PREVENTIVO_PER_VDS_INFERIORI_ULTIMA_SPESA_AMMESSA = "I totali del nuovo preventivo per voce di spesa contrassegnati con ( e ) sono inferiori rispetto alla relativa ultima spesa ammessa.";
	
}
