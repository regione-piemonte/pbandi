/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.util;

import java.math.BigDecimal;

public class Constants {
	public final static String COMPONENT_NAME = "pbwebrce";

	public static final String USERINFO_SESSIONATTR = "currentUserInfo";
//	public static final String KEY_UTENTE_INCARICATO = "utenteIncaricato";
//	public static final long ID_UTENTE_FITTIZIO = -1L;
//	public static final String DESCRIZIONE_BREVE_TIPO_SOGG_PERSONA_FISICA = "PF";
//	public static final int VALORE_TIPO_SOGG_PERSONA_FISICA = 1;
//	
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final String YEAR_FORMAT = "yyyy";
	public static final String TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
	public static final String ORA_FORMAT = "HH:mm:ss";
//	public static final String ACTIVITY_TOKEN_SEPARATOR = "\u00a3A\u00a3";
//	public static final String ACTIVITY_ASSIGNMENT_OPERATOR = "\u00a3a\u00a3";

	public static final String TASKNAME_DICHDISPESA = "DICH-DI-SPESA";
	public static final String TASKNAME_GESTCHECKLIST = "GEST-CHECKLIST";
	public static final String TASKNAME_GESTAFFIDAMENTI = "GEST-AFFIDAMENTI";

	public static final String ROOT_FILE_SYSTEM = "/pbstorage_online";
	public static final String PATH_FILE_SQL = "sql/pbandisrv/";

	public static final String DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_BEN_MASTER = "BEN-MASTER";

	public static final String DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_PERSONA_FISICA = "PERSONA-FISICA";

	public static final String NOME_ENTITA_APPALTO = "PBANDI_T_APPALTO";

	public static final Long ID_TIPO_DOCUMENTO_INDEX_DICHIARAZIONE_AFFIDAMENTO = 26L;

	public static final String COD_TIPO_DOCUMENTO_INDEX_PROPOSTA_RIMODULAZIONE = "PR";

	public static final String NOME_SEQ_APPLATO = "SEQ_PBANDI_T_APPALTO";

	public static final String NOME_SEQ_PROCEDURA_AGGIUDI = "SEQ_PBANDI_T_PROCEDURA_AGGIUDI";

	public static final int ID_STATO_AFFIDAMENTO_DAINVIARE = 1;
	public static final int ID_STATO_AFFIDAMENTO_INVERIFICA = 2;
	public static final int ID_STATO_AFFIDAMENTO_VPARZIALE = 3;
	public static final int ID_STATO_AFFIDAMENTO_VDEFINITIVA = 4;
	public static final int ID_STATO_AFFIDAMENTO_RICINTEGRAZ = 5;

	public static final String COSTANTE_INDIRIZZO_EMAIL_MITTENTE = "conf.serverConfiguration.indirizzoEmailMittente";
	public static final String COSTANTE_INDIRIZZO_EMAIL_AFFIDAMENTI = "conf.serverConfiguration.indirizzoEmailAffidamenti";

	public static final String MAIL_ADDRESSES_SEPARATOR = ",";

	public static final Long ID_TIPO_SOGG_PERSONA_FISICA = 2L;

	// TIPO_PERCETTORE
	public final static String TIPO_PERCETTORE = "it.csi.pbandi.pbandisrv.integration.db.vo.PbandiDTipoPercettoreVO";

	public static final String MSG_SALVA_SUCCESSO = "Salvataggio avvenuto con successo.";

	public static final long ID_ENTITA_PBANDI_T_APPALTO = 262L;
	public static final long ID_ENTITA_QTES_HTML_PROGETTO = 747L;

	public static final String PROGRAMMAZIONE_PRE_2016 = "PRE_2016";

	public static final String PROGRAMMAZIONE_2016 = "2016";

	public static final String VALUE_IMPORTO_ULTIMO_CERTIFICATO_NON_PRESENTE = "n.c.";

	public static final String APPLICATION_CODE = "pbwebrce";

	public static final String N_A = "N/A";

	public static final int MAX_SCALE_ALTRI_INDICATORI = 5;
	public static final int MAX_SCALE_INDICATORI_MONIT = 2;

	public static final String CONFERMA_SALVATAGGIO = null;

	public static final String RUOLO_BEN_MASTER = "BEN-MASTER";

	public static final String RUOLO_PERSONA_FISICA = "PERSONA-FISICA";

	public static final int MAX_CIFRE_INTERE = 11;

	public static final String FLAG_TRUE = "S";
	public static final String FLAG_FALSE = "N";

	public static final String TASKNAME_RETTIFICA_INDICATORI = "Rettifica indicatori di progetto";
	public static String CRONOPROG_AVVIO = "CRONOPROG-AVVIO";

	public static String CARICAM_INDIC_AVVIO = "CARICAM-INDIC-AVVIO";

	public static final String COD_TIPO_CHEKCLIST_AFFIDAMENTO_IN_LOCO = "CLILA";
	public static final String COD_TIPO_CHEKCLIST_AFFIDAMENTO_DOCUMENTALE = "CLA";

	public static final String COD_TIPO_CHEKCLIST_IN_LOCO = "CLIL";

	// quadro previsionale
	public static final String STATO_CONTO_ECONOMICO_NUOVA_RIMODULAZIONE_CONCLUSA = "NRC";

	public static final String STATO_CONTO_ECONOMICO_APPROVATO_IN_ISTRUTTORIA = "AI";

	/** Specifica se le voci di spesa sono visibili sulla presentation */
	public static final String BR31_VOCI_VISIBILI_PER_QUADRO_PREVISIONALE = "BR31";

	/**
	 * Specifica se per i progetti di un bando_linea, i controlli nel quadro
	 * previsionale sul totale del nuovo preventivo rispetto al totale dell'ultima
	 * spesa ammessa sono BLOCCANTI
	 */
	public static final String BR32_CONTROLLO_NUOVO_PREVENTIVO_BLOCCANTE = "BR32";

	public static final String TASK_QUADRO_PREVISIO = "QUADRO-PREVISIO";

	public static final String CONTROLLADATIPERSALVATAGGIO_OUTCOME_CODE__ERROR = "ERROR";
	public static final String CONTROLLADATIPERSALVATAGGIO_OUTCOME_CODE__OK = "OK";

	public static final String IMPORTO = "IMPORTO";

	public static final String MSG_PROPOSTA_RIMODULAZIONE_TOTALE = "Totale";
	public static final String MSG_RIMODULAZIONE_TOTALE = "Totale";
	public static final String MSG_RIMODULAZIONE_TOTALE_COMPLESSIVO = "Totale complessivo";
	public static final String MSG_RIMODULAZIONE_SUBTOTALE = "Subtotale finanziamenti";
	public static final String MSG_RIMODULAZIONE_SUBTOTALE_COMPLESSIVO = "Subtotale complessivo";
	public static final Double MAX_IMPORTO_AMMMESSO = 999999999999999.99d;
	public static final int MAX_LUNGHEZZA_NOTE = 4000;
	public static final int MAX_LUNGHEZZA_RIFERIMENTO = 250;
	public static final int MAX_LUNGHEZZA_ESTREMI_PROVVEDIMENTO_FONTE_FINANZIARIA = 200;
	public static final int MAX_LUNGHEZZA_OGGETTO_APPALTO = 255;
	public static final int MAX_LUNGHEZZA_IMPRESA_APPALTATRICE = 255;
	public static final int MAX_LUNGHEZZA_IDENTIFICATIVO_INTERVENTO = 100;
	public static final String IS_IMPORTI_MODIFICABILI = "IS_IMPORTI_MODIFICABILI";

	public static final String TIPO_SOGGETTO_FINANZIATORE_COFPOR = "COFPOR";
	public static final String SOGGETTO_FINANZIATORE_UE = "UE";

	public static final String DESC_BREVE_RUOLO_ENTE = "RESP.AFFIDAMENTI";
	public static final String MAIL_NON_TROVATA_COD = "Mail non trovata";
	public static final String MAIL_NON_TROVATA_MSG = "ATTENZIONE! Impossibile inviare l'affidamento in verifica all'istruttore in mancanza della casella di posta a cui recapitare la mail. Contattare il servizio di assistenza";

	public static final String ENTITA_PBANDI_R_PROGETTO_ITER = "PBANDI_R_PROGETTO_ITER";

	public static final String DESC_BREVE_RUOLO_ENTE_COMPETENZA_DESTINATARIO = "DESTINATARIO";

	public static final String DESC_BREVE_TIPO_SEDE_INTERVENTO = "SI";

	public static final String COD_IGRUE_EMISSIONE_POSITIVO_CERTIFICATO_COLLAUDO = "WC32";

	public static final String DESC_BREVE_TIPO_DOC_INDEX_CCC = "CCC";
	
	public static final String DESC_BREVE_STATO_DOCUMENTO_INDEX_GENERATO = "GENERATO";
}