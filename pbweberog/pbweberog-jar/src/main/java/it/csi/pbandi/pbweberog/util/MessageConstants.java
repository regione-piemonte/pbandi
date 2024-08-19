/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.util;


public class MessageConstants {

	//Erogazione
	public static final String ERRORE_FORNITORI_TROVAI_TROPPI_RISULTATI = "Trovati troppi risultati. E' necessario inserire almeno un altro campo di ricerca.";
	public static final String MSG_EROGAZIONE_SALVATA = "L'erogazione della quota di contributo é stata salvata correttamente.";
	public static final String EROGAZIONE_DA_ASSOCIARE_SALVATA = "L'erogazione della quota di contributo é stata salvata correttamente. Associare le quietanze di mandato.";
	public static final String SALVATAGGIO_AVVENUTO_CON_SUCCESSO = "Salvataggio avvenuto con successo.";
	public static final String ERRORE_SALVAGGIO_RECAPITO = "Impossibile ggiornamre  email, fax e telefono";
	public static final String CANCELLAZIONE_AVVENUTA_CON_SUCCESSO = "Cancellazione avvenuta con successo.";
	public static final String ATTIVITA_EROGAZIONE_NON_UTILIZZABILE = "Per il progetto corrente, l'attività di erogazione non é abilitata.";
	public static final String ERRORE_SOMMA_QUOTE_EROGATE_SUPERIORE_A_IMPORTO_AGEVOLATO = "Per il progetto, la somma delle quote erogate al netto dei recuperi é superiore all'importo agevolato.";
	public static final String ERRORE_SOMMA_EROGAZIONI_PER_MOD_AGEVOLAZ_SUPERIORE_IMPORTO_AGEVOLATO_CONTO_ECONOMICO = "Per la modalità di agevolazione, la somma delle erogazioni al netto dei recuperi è maggiore della quota dell'importo agevolato.";
	
	//Rinuncia
	public static final String MSG_RINUNCIA_ELABORATA_PREDISPOSTA = "La COMUNICAZIONE DI RINUNCIA è stata correttamente elaborata e predisposta per essere inviata.";
	
	//Gestione dati del progetto
	public static final String MSG_RICHIESTA_AUTOMATICA_DEL_CUP_IMPOSTATA_A_NO = "Il campo CUP è stato valorizzato. La richiesta automatica del CUP è stata impostata a No.";
	public static final String GESTPROGETTO_SOGGETTI_RUOLO_ERR = "Il ruolo corrente non e' abilitato alla modifica del recapito del beneficiario selezionato.";

	
	//GEstione disimpegni
	public static final String WRN_REVOCA_ESISTE_PROPOSTA_CERTIFICAZIONE = "Per il progetto è stata creata almeno una proposta di certificazione. Eventuali modifiche alle revoche del progetto potranno avere effetto sugli importi certificati.";
	public static final String MSG_DISIMPEGNO_NESSUNA_REVOCA_MODIFICATA = "Indicare l'importo del disimpegno.";
	
	//Registro controlli
	public static final String ERRORE_IRREGOLARITA_BLOCCATA_MOD = "L'irregolarità è bloccata. Non è possibile procedere alla modifica. Rivolgersi alla Regione Piemonte per lo sblocco.";
	
	//Recupero
	public static final String WARN_RECUPERO_FUNZIONALITA_NON_ABILITATA = "Per il bando del progetto corrente, la funzionalità di proposta di recupero, come previsto, non è stata abilitata. Contattare l'autorità di controllo di riferimento per maggiori informazioni.";
	public static final String MSG_RECUPERO_NESSUN_RECUPERO_MODIFICATO = "Nessun recupero modificato.";
	
	//Chiusura del progetto
	public static final String WRN_CHIUSURA_PROGETTO_RINUNCIA_PRESENTE = "NB: il beneficiario ha presentato comunicazione di rinuncia al finanziamento per il presente progetto.";
	
	//Monitoraggio controlli
	public static final String MSG_ACQUISIZIONE_CAMPIONE_SUCCESSO = "L'elaborazione è terminata correttamente. Prendere visione nella tabella dei progetti acquisiti! I seguenti progetti sono stati scartati : ";	
	
	// Revoca
	public static final String MSG_REVOCA_NESSUN_REVOCA_MODIFICATO = "Nessun revoca modificata.";
	public static final String WARN_REVOCA_REVOCA_CERTIFICATA = "La revoca risulta certificata. Si consiglia di inserire una motivazione alla modifica eseguita nel campo denominato note.";
	public static final String WARN_REVOCA_CONFERMA_SALVATAGGIO = "Confermare il salvataggio dei dati.";
	
}
