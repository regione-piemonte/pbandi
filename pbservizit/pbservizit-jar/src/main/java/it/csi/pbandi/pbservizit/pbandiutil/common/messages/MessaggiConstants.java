/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandiutil.common.messages;

public interface MessaggiConstants {

	String NESSUN_RISULTATO = "W.N002";

	String SALVATAGGIO_AVVENUTO_CON_SUCCESSO = "M.N011";

	String CANCELLAZIONE_AVVENUTA_CON_SUCCESSO = "M.N017";
	
	String OPERAZIONE_ESEGUITA_CON_SUCCESSO = "M.G1";

	/**
	 * Gestione Fornitori
	 */
	String FORNITORE_INSERITO = "M.N019";
	String FORNITORE_AGGIORNATO = "M.N020";
	String QUALIFICA_FORNITORE_INSERITA = "M.N021";
	String QUALIFICA_FORNITORE_AGGIORNATA = "M.N022";
	String QUALIFICA_FORNITORE_DISATTIVATA= "M.N023b";
	String FORNITORE_STORICIZZATO = "M.N096";
	String FORNITORE_CANCELLATO = "M.N095";

	String PROCESSO_AVVIATO = "M.N026";
	String PROCESSI_AVVIATI = "M.N027";
	String PROCESSI_NON_AVVIATI = "E.N028";
	String PROCESSO_NON_AVVIATO = "E.N029";
	String CSP_PROGETTO_INCOMPLETO = "E.N065";
	String CSP_PROGETTI_INCOMPLETI = "E.N066";
	String TIMEOUT_AVVIO_PROCESSO = "E.N067";
	String TIMEOUT_AVVIO_PROCESSI = "E.N068";

	String VERIFICA_DOCUMENTI_SUPERATA = "M.N023";

	String RENDICONTABILE_NON_COMPLETAMENTE_ASSOCIATO = "E.N077";
	String SPESA_NON_TOTALMENTE_QUIETANZATA = "W.N078";
	String SPESA_FORFETTARIE_OLTRE_MISURA_CONSENTITA = "E.N083";
	String MONTE_ORE_SUPERATO = "E.N084";
	String FATTURA_QUIETANZATO_MAGGIORE_DEL_DOCUMENTO = "E.N079";
	String PAGAMENTI_NON_COMPLETAMENTE_ASSOCIATI_A_VOCI = "E.N082";
	String DOCUMENTO_SENZA_PAGAMENTI = "W.N076";
	String IMPORTO_NON_CORRETTO_VOCI_SPESA_DOCUMENTO_E_NOTE_CREDITO = "E.N080";
	String IMPORTO_NON_CORRETTO_VOCI_SPESA_NOTA_CREDITO_DOCUMENTO = "E.N081";
	String NOTA_DI_CREDITO_ASSOCIATA_INVALIDA = "W.N089";
	String QUIETANZATO_INFERIORE_RENDICONTABILE = "M.N098";
	String DOCUMENTO_SENZA_NUOVI_PAGAMENTI = "W.N099";
	String DOCUMENTO_CON_PAGAMENTI_INSUFFICIENTI = "W.N100";

	
	String CANCELLAZIONE_DOCUMENTO_NON_ASSOCIATO_PROGETTO_CORRENTE = "E.X001";

	/**
	 * Aggiornamento pagamenti e voci di spesa
	 */
	String STATO_DOCUMENTO_DICHIARAZIONE_AGGIORNATO = "M.VAL009";
	String DICHIARAZIONE_CHIUSA = "M.M4";

	String ESITO_CHECK_STATO_PAGAMENTI_OK = "W.M2";
	String MSG_INTESTAZIONE_DOCUMENTO = "M.N097";
	String MSG_WARNING_VOCE_DI_SPESA_SUPERATO_RESIDUO_AMMESSO = "W.W088";

	/*
	 * Certificazione
	 */
	String MSG_CERTIFICAZION_RICHIESTA_SALVATA = "M.N038";

	/*
	 * Erogazione
	 */

	String MSG_EROGAZIONE_SALVATA = "M.N044";
	String MSG_ASSOCIAZIONE_CREATA = "M.N045";

	/*
	 * Richiesta erogazione
	 */

	String MSG_PROSEGUI_RICHIESTA_EROGAZIONE = "M.N047";

	/*
	 * Workspace
	 */
	String WRKSPC_ATTIVITA_NON_DISP = "E.E081";
	String WRKSPC_ATTIVITA_IN_EXEC = "E.E082";
	String WRKSPC_ATTIVITA_NOT_REFRESHED = "W.E085";
	String WRKSPC_PROGETTI_IN_MIGRAZIONE = "W.W087";

	/*
	 * BackOffice
	 */
	String BKO_AGGIORNARE_IRIDE = "E.IRIDE003";
	String BKO_RIMOSSO_ENTE_COMPETENZA_OBBLIGATORIO = "E.W073";

	/*
	 * Rettifica spesa validata
	 */
	String MSG_RETTIFICA_AVVENUTA_CON_SUCCESSO = "W.N058";
	String MSG_RETTIFICA_AVVENUTA_CON_SUCCESSO_MODIFICA_CHECKLIST = "W.N058bis";
	String MSG_RETTIFICA_NESSUN_DATO_MODIFICATO = "W.N059";
	
	String MSG_CONFERMA_SALVA_CHECKLIST_BOZZA="W.M0";
	String MSG_CONFERMA_SALVA_CHECKLIST_DEFINITIVO="W.M0def";
	String MSG_CHECKLIST_SALVATA="M.M00";
	String MSG_CONFERMA_CHIUSURA_VALIDAZIONE_E_SALVATAGGIO_CHECKLIST_DEFINITIVO="W.M1";
/*	M0=Attenzione: la checkList verr\u00E0 salvata in bozza. Continuare?
			M00=La checkList \u00E8 stata salvata correttamente.
			M1=Attenzione: i dati della dichiarazione di spesa corrente verranno chiusi; confermare la chiusura della validazione sulla dichiarazione di spesa corrente e il salvataggio della checklist?
			*/
	
	/*
	 * Checklist ---
	 * 
	 */
	public String KEY_CHECKLIST_NON_POSSIBILE_ELIMINARE="W.M2cl";
	public String KEY_CHECKLIST_NON_POSSIBILE_ELIMINARE_STATO="W.M2c2";
	public String KEY_CHECKLIST_CONFERMA_ELIMINAZIONE="W.M2c3";
	public String KEY_CHECKLIST_SALVATA_CORRETTAMENTE="M.M9cl"; 
	public String KEY_CHECKLIST_NON_SELEZIONATA="E.M8cl";
	public String KEY_CHECKLIST_LOCCATA="E.M10cl";
	

	String MSG_CONFERMA_CANCELLAZIONE_IRREGOLARITA="W.N069";
	
	
	/*
	 * Revoca
	 */
	String MSG_REVOCA_SELEZIONARE_REVOCA = "E.N070";
	
	/*
	 * Recupero
	 */
	String MSG_RECUPERO_SELEZIONARE_RECUPERO = "E.N071";
	
	/*
	 * Gestione Indicatori
	 */
	public static final String MSG_SAVE_OK_INDICATORI_AVVIO = "M.N072";
	
	
	/*
	 * Gestione Cronoprogramma
	 */
	public static final String MSG_SAVE_OK_CRONOPROGRAMMA_AVVIO = "M.N073";
	
	public static final String MSG_CONFERMA_CHIUSURA_ATTIVITA = "W.MX57";
	
	/*
	 * Operazioni automatiche validazione
	 */
	public static final String MSG_RIEPILOGO_OPERAZIONE_AUTOMATICA_VALIDAZIONE = "M.MX_VAL";
	public static final String MSG_RIEPILOGO_OPERAZIONE_AUTOMATICA_GESTIONE_SPESA_VALIDATA = "M.MX_GSV";
	
	/*
	 * Validazione
	 */
	public static final String KEY_WARNING_FATTURE_INVALIDATE_PER_NOTA_DI_CREDITO_MODIFICATA = "W.VAL014";

	
	
	public String KEY_MSG_CONFERMA_CHIUSURA_RENDICONTAZIONE = "W.N026a";

	public String KEY_MSG_POSSIBILE_DICHIARARE_CONCLUSA_DICHIARAZIONE_SENZA_DOC= "W.E034a";
	
	public String KEY_MSG_CONFERMA_INVIO_DICHIARAZIONE= "W.N026b";
	
	public String KEY_MSG_DICHIARAZIONE_CONCLUSA_CON_SUCCESSO= "M.N027a";
	
	public String KEY_MSG_NECESSARIA_COMUNICAZIONE_FINE_LAVORI= "W.N027b";
	
	public String KEY_MSG_NECESSARI_INDICATORI_FINALI= "W.N027c";
	
	public String KEY_MSG_NECESSARIO_CRONOPROGRAMMA= "W.N027d";
	
	public String KEY_MSG_SI_CONSIGLIA_CORREZIONE_DOCUMENTI= "W.N030";
	

	public String KEY_MSG_PAGAMENTI_DA_RESPINGERE_DICH_FINALE_GIA_INVIATA="W.MX59";//Attenzione: esistono pagamenti con lo stato da respingere e il beneficiario ha gi� inviato una dichiarazione di spesa  finale."; 
	public String KEY_MSG_PAGAMENTI_DA_RESPINGERE="W.MX60";//Attenzione: per la validazione corrente esistono pagamenti con lo stato \"da respingere\";
	public String KEY_MSG_PAGAMENTI_RESPINTI="W.MX61";//Attenzione: per il progetto esistono pagamenti con lo stato \"respinto\";
	public String KEY_MSG_NECESSARIA_DICH_INTEGRATIVA="W.MX62";//Potrebbe essere necessaria una dichiarazione di spesa integrativa";
	public String KEY_MSG_CHIUSURA_PROGETTO_DICH_SPESA_CON_VALIDAZIONE_NON_CHIUSA = "W.MX64"; //Per il progetto corrente, esiste almeno una dichiarazione di spesa intermedia la cui validazione non � stata ancora chiusa

	
	public String KEY_MSG_ISTANZA_STORICIZZATA = "M.N086";
	public String KEY_MSG_ISTANZA_NON_STORICIZZATA = "M.N087";
	
	public String KEY_MSG_IMPEGNO_DISASSOCIA_CONCLUSA = "M.N088";
	
	public String KEY_MSG_IMPEGNO_AGGIORNA = "N090";
	
	public String KEY_MSG_RICHIESTA_AUTOMATICA_DEL_CUP_IMPOSTATA_A_NO = "M.N094";

	public String DOC_PRIVO_QUIETANZE ="E.N085";
	
	public String KEY_MSG_DICHIARAZIONE_FINALE_NESSUN_DOCUMENTO = "M.N101";
	
	public String KEY_MSG_APPALTI_ASSOCIATI_CHECKLIST = "M.M01";
	
	public String KEY_MSG_CERTIFICAZIONE_CHIUSURA_CONTI_SUCCESSO = "M.CIF01";
	String KEY_MSG_CERTIFICAZIONE_AGGIORNAMENTO_DATI = "M.CIF02";
	
	public String KEY_MSG_ACQUISIZIONE_CAMPIONE_SUCCESSO = "M.RC001";
	public String KEY_MSG_ACQUISIZIONE_CAMPIONE_PRESENTE = "M.RC002";
	
	public String KEY_MSG_PROCEDURA_CAMPIONAMENTO_SUCCESSO = "M.PC001";
	
}
