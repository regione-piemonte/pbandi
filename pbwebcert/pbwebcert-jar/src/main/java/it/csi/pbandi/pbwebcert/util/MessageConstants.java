/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.util;

import java.math.BigDecimal;

public class MessageConstants {

	public static final String MSG_CANCELLAZIONE_TUTTI_DOCUMENTI_NON_ESEGUITA = "Cancellazione di tutti documenti non eseguita.";
	public static final String MSG_OPERAZIONE_NON_ESEGUITA = "Cancellazione non eseguita.";
	public static final String MSG_CANCELLAZIONE_AVVENUTA_CON_SUCCESSO = "Cancellazione avvenuta con successo.";
	
	public static final String MSG_OPERAZIONE_ESEGUITA_CON_SUCCESSO = "Crea intermedia finale eseguita con successo.";
	
	public static final BigDecimal TIPO_PERIODO_ANNO_CONTABILE = new BigDecimal(3);
	public static final String MSG_UPLOAD_FILE_ESEGUITA_CON_SUCCESSO = "Il file e' stato correttamente salvato.";
	public static final String MSG_DESCRIZIONE_MODIFICATO_CON_SUCCESSO = "Le descrizioni dei file si aggiornano con successo.";
	public static final String KEY_MSG_PROCEDURA_CAMPIONAMENTO_SUCCESSO = "Campionamento estrato con successo.";
	public static final String MSG_AGGIORNAMENTO_STATO_SUCCESSO  = "Salvataggio avvenuto con successo. elaborazione dei progetti da sottoporre ad alert (anticipi non coperti da spesa validata) è stata completata. Non sono stati rilevati progetti da segnalare.";
	public static final String MSG_AGGIORNAMENTO_IMPORTO_SUCCESSO = "Salvataggio avvenuto con successo.";
	public static final String MSG_AGGIORNAMENTO_PROGETTI_INT_FINALE_SUCCESSO = "Aggiornamento avvenuto con successo.";
	public static final String MSG_CHIUSURA_CONTI_AVVENUTA_CON_SUCCESSO = "Aggiornamento proposta di certificazione in Chiusura Conti avvenuta con successo.";
	public static final String MSG_SOSPENSIONI_PROGETTI_SUCCESSO = "Sospensioni dei progetti selezionati avvenuta con successo.";
	public static final String MSG_AMMISSIONI_PROGETTI_SUCCESSO = "Salvataggio avvenuto con successo.";
	public static final String MSG_PROPOSTA_LANCIATA_SUCCESSO  = "La proposta di certificazione è stata lanciata con successo";
	public static final String MSG_INVIA_REPORT_SUCCESSO = "Report inviato con successo.";
}
