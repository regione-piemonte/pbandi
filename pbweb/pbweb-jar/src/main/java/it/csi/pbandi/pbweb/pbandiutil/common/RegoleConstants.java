/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandiutil.common;

/**
 * 
 * Classe delle costanti facenti riferimento alla DESC_BREVE_REGOLA
 * su PBANDI_C_REGOLA
 * 
 */
public interface RegoleConstants {
	
	/** Gestione monte ore e costo annuo per dipendenti */
	public static final String BR01_GESTIONE_ATTRIBUTI_QUALIFICA = "BR01";
	/** Gestione associazione  tra pagamenti e voci di spesa */
	public static final String BR02_VISUALIZZA_VOCI_DI_SPESA = "BR02";
	/** Rilascio in dichiarazione di spesa SOLO di spese quietanzate */
	public static final String BR03_RILASCIO_SPESE_QUIETANZATE = "BR03";
	/** Nei pagamenti gestione data valuta */
	public static final String BR04_VISUALIZZA_DATA_VALUTA = "BR04";
	/** Nei pagamenti gestione data pagamento */
	public static final String BR05_VISUALIZZA_DATA_EFFETTIVA = "BR05";
	/** Export Excel per Regione */
	public static final String BR06_EXPORT_EXCEL = "BR06";
	/** Controllo spese totalmente quietanzate in dichiarazione finale */
	public static final String BR07_CONTROLLO_SPESE_TOTALMENTE_QUIETANZATE = "BR07";
	/** Filtro ricerca su tipologia di beneficiario per documenti di spesa */
	public static final String BR08_FILTRI_RICERCA_BENEFICIARIO_CAPOFILA = "BR08";
	/** Associazione delle quietanze di mandato all'atto di liquidazione. */
	public static final String BR09_ASSOCIAZIONE_QUIETANZE_ATTO_LIQUIDAZIONE = "BR09";
	/** Erogazioni su progetto capofila */
	public static final String BR10_EROGAZIONI_PROGETTO_CAPOFILA = "BR10";
	/** Regola che specifica con quali partner popolare combo (legata alla BR08) */
	public static final String BR11_FILTRO_PARTNER_BR08 = "BR11";
	/** Specifica se i progetti di un bando_linea utilizzano l'attivita' di rimodulazione (Istruttore) */
	public static final String BR12_RIMODULAZIONE_DISPONIBILE = "BR12";
	/** Specifica se i progetti di un bando_linea utilizzano le attivit� di erogazione */
	public static final String BR13_ATTIVITA_EROGAZIONE_DISPONIBILE = "BR13"; 
	/** Specifica se i progetti di un bando_linea utilizzano le attivit� di gestione fideiussioni */
	public static final String BR14_GESTIONE_FIDEIUSSIONE_DISPONIBILE = "BR14";
	/** Specifica se i progetti di un bando_linea utilizzano la attivit� di richiesta di erogazione */
	public static final String BR15_ATTIVITA_RICHIESTA_EROGAZIONE_DISPONIBILE = "BR15";
	/**Nei documenti di spesa gestione campo task */
	public static final String BR16_GESTIONE_CAMPO_TASK = "BR16";
	/** Specifica se per i progetti di un bando_linea � possibile, per il Beneficiario, richiedere nuovi importi per le modalita' di agevolazione */
	public static final String BR17_RICHIESTA_NUOVI_IMPORTI= "BR17";
	/** Specifica se per i progetti di un bando_linea, i controlli in rimodulazione sul superamento dell'importo massimo o delle percentuali ammissibili previsti da bando sono BLOCCANTI */
	public static final String BR18_CHECK_RIMODULAZIONE_IMPORTO_MASSIMO_PERCENTUALE_AMMISSIBILI = "BR18";
	/** Specifica se per i progetti di un bando_linea, i controlli in rimodulazione sull'uso di voci di spesa mai utilizzate in precedenza nel conto economico sono BLOCCANTI */
	public static final String BR19_CHECK_RIMODULAZIONE_VOCI_DI_SPESA_MAI_UTILIZZATE = "BR19";
	/** Specifica se per i progetti di un bando_linea, i controlli in rimodulazione sul totale ultima spesa ammessa sono BLOCCANTI */
	public static final String BR20_CHECK_RIMODULAZIONE_ULTIMA_SPESA_AMMESSA = "BR20";
	/** Specifica se per i progetti di un bando_linea, i controlli in rimodulazione sul superamento dell'importo massimo per la modalita' di agevolazione previsto da bando sono BLOCCANTI */
	public static final String BR21_CHECK_RIMODULAZIONE_IMPORTO_MASSIMO_MODALITA_AGEVOLAZIONE = "BR21";
	/** Specifica se per i progetti di un bando_linea, i controlli in rimodulazione sulla percentuale ammissibile per la modalita' di agevolazione prevista da bando sono BLOCCANTI */
	public static final String BR22_CHECK_RIMODULAZIONE_PERCENTUALE_AMMISSIBILE = "BR22";
	/** Specifica se per i progetti di un bando_linea, i controlli in rimodulazione sull'importo gia' erogato per la modalita' di agevolazione sono BLOCCANTI */
	public static final String BR23_CHECK_RIMODULAZIONE_GIA_EROGATO_MODALITA_AGEVOLAZIONE = "BR23";
	/** Specifica se per i progetti di un bando_linea, i controlli in rimodulazione sul totale dei nuovi importi agevolati rispetto alle fonti finanziarie sono BLOCCANTI */
	public static final String BR24_CHECK_RIMODULAZIONE_NUOVI_IMPORTI_AGEVOLATI_RISPETTO_FONTI_FINANZIARIE = "BR24";
	/** Specifica se per i progetti di un bando_linea, i controlli in rimodulazione sul totale delle nuove quote finanziarie rispetto spesa ammessa in rimodulazione sono BLOCCANTI */
	public static final String BR25_CHECK_RIMODULAZIONE_NUOVE_QUOTE_FINANZIARIE_RISPETTO_SPESA_AMMESSA = "BR25";
	/** Specifica se per i progetti di un bando_linea, i controlli in rimodulazione sul totale dei nuovi importi agevolati richiesti sono BLOCCANTI */
	public static final String BR26_CHECK_RIMODULAZIONE_TOTALE_NUOVI_IMPORTI_AGEVOLATI_RICHIESTI = "BR26";
	/** Specifica se i progetti di un bando_linea utilizzano l'attivita' di proposta di rimodulazione (Beneficiario) */
	public static final String BR27_RIMODULAZIONE_DISPONIBILE = "BR27";
	/** Specifica se i progetti di un bando_linea utilizzano l'attivita' per i Recuperi (Istruttore) */
	public static final String BR28_RECUPERO_DISPONIBILE = "BR28";
	/** Specifica se la Data Ammissibilita' delle spese per i progetti di un bando_linea e' la data di costituzione dell'azienda beneficiaria */
	public static final String BR30_DATA_AMMISSIBILITA_PER_PROGETTTI = "BR30";
	/** Specifica se le voci di spesa sono visibili sulla presentation  */
	public static final String BR31_VOCI_VISIBILI_PER_QUADRO_PREVISIONALE = "BR31";
	/** Specifica se per i progetti di un bando_linea, i controlli nel quadro previsionale sul totale del nuovo preventivo rispetto al totale dell'ultima spesa ammessa sono BLOCCANTI */
	public static final String BR32_CONTROLLO_NUOVO_PREVENTIVO_BLOCCANTE = "BR32";
	/** Richiesta di upload del file della relazione tecnica in invio della dichiarazione di spesa (obbligatorio se dichiarazione finale) */
	public static final String BR33_UPLOAD_FILE_RELAZIONE_TECNICA = "BR33";
	/** Specifica se per i progetti di un bando_linea, e' necessario caricare i dati del cronoprogramma */
	public static final String BR34_CONTROLLO_ATTIVITA_CRONOPROGRAMMA_VALIDA = "BR34";
	/** Specifica se per i progetti di un bando_linea, e' necessario caricare gli indicatori di progetto */
	public static final String BR35_CONTROLLO_ATTIVITA_INDICATORI_VALIDA = "BR35";
	/**Specifica che per i progetti di un bando_linea e' disponibile la dichiarazione di spesa integrativa*/
	public static final String BR36_DICHIARAZIONE_INTEGRATIVA_DISPONIBILE= "BR36";
	/**Attivit� di liquidazione del contributo o attivit� di creazione dell'atto di liquidazione*/
	public static final String BR37_ATTIVITA_LIQUIDAZIONE_CONTRIBUTO = "BR37";
	/**Attivit� di liquidazione del contributo o attivit� di creazione dell'atto di liquidazione*/
	public static final String BR38_RENDICONTAZIONE_AUTOMATIZZATA = "BR38";
	/**Specifica se il bando linea prevede il controllo per cui la quietanza deve essere almeno pari al rendicontabile*/
	public static final String BR39_CONTROLLO_QUIETANZA_NON_INFERIORE_RENDICONTABILE = "BR39";
	/** Specifica se per il bando e' abilitata l'asscociazione del documento a progetto. */
	public static final String BR40_ABILITAZIONE_ASSOCIA_DOCUMENTO = "BR40";
	
	/** Specifica se per il bando si possono allegare file dell'archivio  */
	public static final String BR42_ABILITAZIONE_ALLEGA_FILE = "BR42";

	/** Specifica se per il bando si  deve specificare la causale pagamento  */
	public static final String BR43_CAUSALE_PAGAMENTO= "BR43";
	
	/** Specifica se per il bando si  deve specificare la causale pagamento  */
	public static final String BR44_BANDO_SIF= "BR44";
	
	/** Specifica se per il bando ha la possibilit� di generare e gestire un'economia  */
	public static final String BR46_BANDO_ECONOMIA= "BR46";
	
	/* Regola per la dematerializzazione integrale (blocca l'invio cartaceo) */
	public static final String BR48_DEMATERIALIZZAZIONE_INTEGRALE = "BR48";
	
	/* Specifica se il Bando-Linea prevede l’upload di allegati ai documenti di spesa */
	public static final String BR51_UPLOAD_ALLEGATI_SPESA = "BR51";
	
	/* Specifica se il Bando-Linea prevede l’upload di allegati alle quietanze di pagamento */
	public static final String BR52_UPLOAD_ALLEGATI_QUIETANZA = "BR52";
	
	/* Specifica se il Bando-Linea prevede l’upload di allegati generici in Invio dichiarazione di spesa */
	public static final String BR53_UPLOAD_ALLEGATI_GENERICI = "BR53";
	
	/* Specifica se il Bando-Linea prevede Consente di inserire esclusivamente la dichiarazione di spesa finale. 
	 * Non presentare il radio button per la selezione del tipo di dichiarazione e impostare il tipo direttamente come FINALE */
	public static final String BR54_ATTIVA_DICHIARAZIONE_FINALE = "BR54";

	// Specifica se si applica il controllo del PAREGGIO DI BILANCIO ossia: saldo contabile = totale spese effettive – totale entrate effettive = 0
	public static final String BR60_ATTIVA_DICHIARAZIONE_FINALE = "BR60";

	// Specifica se per i progetti di un bandoLinea, è necessario caricare i dati della DECLARATORIA.
	public static final String BR61_ATTIVA_DICHIARAZIONE_FINALE = "BR61";

	// Specifica se per i progetti di un bando_linea e' possibile inviare la dichiarazione di spesa con un quietanzato uguale a zero
	public static final String BR62_DICHIARAZIONE_CON_QUIETANZA_ZERO = "BR62";
	
	// Attiva sul bando il ruolo della Ragioneria Delegata
	public static final String BR63_RAGIONERIA_DELEGATA = "BR63";

}
