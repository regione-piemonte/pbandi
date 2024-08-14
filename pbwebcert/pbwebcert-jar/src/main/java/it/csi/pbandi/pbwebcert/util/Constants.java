/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.util;

import java.math.BigDecimal;

public class Constants {
	public final static String COMPONENT_NAME = "pbwebcert";

	public static final String USERINFO_SESSIONATTR = "currentUserInfo";
	public static final String KEY_UTENTE_INCARICATO = "utenteIncaricato";
	public static final long ID_UTENTE_FITTIZIO = -1L;
//	public static final String DESCRIZIONE_BREVE_TIPO_SOGG_PERSONA_FISICA = "PF";
	public static final int VALORE_TIPO_SOGG_PERSONA_FISICA = 1;
//	
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final String YEAR_FORMAT = "yyyy";
	public static final String TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
//	public static final String ORA_FORMAT = "HH:mm:ss";
	public static final String ACTIVITY_TOKEN_SEPARATOR = "\u00a3A\u00a3";
	public static final String ACTIVITY_ASSIGNMENT_OPERATOR = "\u00a3a\u00a3";

	// PACKAGE

	public static final String NOME_PACKAGE_PROC_PROCESSO = "pck_pbandi_processo";

	// ATTIVITA

	public static final String ATTIVITA_IN_USO = "IN USO";
	public static final String ATTIVITA_DISPONIBILE = "DISPONIBILE";

	// OPERAZIONI

	public static final Long ID_OPERAZIONE_SCELTA_BENEFICIARIO = 1L;
	public static final Long ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE = 2L;
	public static final Long ID_OPERAZIONE_LINEE_FINANZIAMENTO = 3L;
	public static final Long ID_OPERAZIONE_CERTIFICAZIONE = 4L;
	public static final Long ID_OPERAZIONE_REGISTRO_CONTROLLI = 5L;
	public static final Long ID_OPERAZIONE_BILANCIO = 6L;
	public static final Long ID_OPERAZIONE_RENDICONTAZIONE_MASSIVA = 7L;
	public static final Long ID_OPERAZIONE_TRASFERIMENTI = 8L;
	public static final Long ID_OPERAZIONE_ARCHIVIO_FILE = 9L;
	public static final Long ID_OPERAZIONE_NOTIFICHE_VIA_MAIL = 10L;
	public static final Long ID_OPERAZIONE_DOCUMENTI_PROGETTO = 11L;
	public static final Long ID_OPERAZIONE_GESTIONE_ECONOMIE = 12L;
	public static final Long ID_OPERAZIONE_AMMINISTRAZIONE = 13L;
	public static final Long ID_OPERAZIONE_RILEVAZIONE_CONTROLLI = 14L;

	// STATO PROPOSTA CERTIFICAZIONE
	public static final String DESC_BREVE_STATO_PROPOSTA_CODA = "00coda";
	public static final String DESC_BREVE_STATO_PROPOSTA_ELABORAZIONE = "03elab";
	public static final String DESC_BREVE_STATO_PROPOSTA_ERRORE_ELABORAZIONE = "04err";
	public static final String DESC_BREVE_STATO_PROPOSTA_APERTA = "05open";
	public static final String DESC_BREVE_STATO_PROPOSTA_ANNULLATA = "06annu";
	public static final String DESC_BREVE_STATO_PROPOSTA_APPROVATA = "07appr";
	public static final String DESC_BREVE_STATO_PROPOSTA_RESPINTA = "09resp";
	public static final String DESC_BREVE_STATO_PROPOSTA_BOZZA = "bozza";
	public static final String DESC_BREVE_STATO_PROPOSTA_CODA_BOZZA = "00coda-bozza";
	public static final String DESC_BREVE_STATO_PROPOSTA_ELABORAZIONE_BOZZA = "03elab-bozza";
	public static final String DESC_BREVE_STATO_PROPOSTA_ERRORE_ELABORAZIONE_BOZZA = "04err-bozza";
	public static final String DESC_BREVE_STATO_PROPOSTA_PREVIEW = "prev";
	public static final String DESC_BREVE_STATO_PROPOSTA_PREVIEW_BOZZA = "prev-bozza";
	public static final String DESC_BREVE_STATO_PROPOSTA_ERRORE_PREVIEW = "err-prev";
	public static final String DESC_BREVE_STATO_PROPOSTA_ERRORE_PREVIEW_BOZZA = "err-prev-bozza";
	public static final String DESC_BREVE_STATO_PROPOSTA_INTERMEDIA_FINALE = "intermedia-finale";
	public static final String DESC_BREVE_STATO_PROPOSTA_CHIUSURA_CONTI = "chiusura-conti";
	public static final String DESC_BREVE_STATO_PROPOSTA_AGGIORNAMENTO_INTERMEDIA_FINALE = "aggiorna-int-finale";

	// Id Linea Di Intervento nuova programmazione POR FESR 2014-2020
	// Vedere tabella PBANDI_D_LINEA_INTERVENTO
	public static final long ID_LINEA_INTERVENTO_POR_FESR_2014_2020 = new Long(202);

	public static final String FLAG_CAMPIONIONAMENTO_CERTIFICAZIONE = "C";
	public static final String ENTITA_C_FILE = "PBANDI_T_FILE";
	
	//Costanti di campionamento
	public static final String TEMPLATE_REPORT_CAMPIONI_FOGLIO_PROGETTI = "CampionamentoFoglioProgetto";
	public static final String TEMPLATE_REPORT_CAMPIONI_POR_FESR2014_20 = 	"CampionamentoPOR-FESR-2014-2020";
	public static final String TEMPLATE_REPORT_CAMPIONI_PAR_FAS = 			"CampionamentoPAR-FAS";
	public static final String TEMPLATE_REPORT_CAMPIONI_FOGLIO_OPERAZIONI = "CampionamentoFoglioOperazioni";
	public static final String TEMPLATE_REPORT_CAMPIONI_POR_FSE = 			"CampionamentoPOR-FSE";
	public static final String DATEHOUR_FORMAT_PER_NOME_FILE = "ddMMyyyyHHmmss";
	
	public static final String TIPO_DOCUMENTO_INDEX_DICHIARAZIONE_FINALE_DI_CERTIFICAZIONE = "DFC";
	public static final String TIPO_DOCUMENTO_INDEX_CHECK_LIST_CERTIFICAZIONE_PER_PROPOSTA = "CLC";
	public static final String TIPO_DOCUMENTO_INDEX_CHECK_LIST_CERTIFICAZIONE_PER_PROGETTO = "CLCP";
	public static final String TIPO_DOCUMENTO_INDEX_FILE_DELLA_PROPOSTA_DI_CERTIFICAZIONE = "FPC";

	public static final String TIPO_STATO_PROPOSTA_CERTIFICAZIONE_ADC = "adc";
	public static final String DESC_BREVE_STATO_PROPOSTA_CERTIF_APPROVATA = "07appr";

	public static final String STATO_PROPOSTA_CERTIFICAZIONE_IN_CODA = "00coda";
	public static final String STATO_PROPOSTA_CERTIFICAZIONE_IN_ELABORAZIONE = "03elab";
	public static final String STATO_PROPOSTA_CERTIFICAZIONE_BOZZA_IN_ELABORAZIONE = "03elab-bozza";
	
	public static final String PROPOSTA_CERTIFICAZIONE_TUTTE_LE_LINEE_DI_INTERVENTO = "*";

	//costanti di crea proposta certificazione
	public static final String STATO_PROPOSTA_CERTIFICAZIONE_BOZZA_IN_CODA = "00coda-bozza";
	public static final String STATO_PROPOSTA_CERTIFICAZIONE_BOZZA_PREVIEW = "prev-bozza";
	public static final String STATO_PROPOSTA_CERTIFICAZIONE_PREVIEW = "prev";

	public static final String MSG_CERTIFICAZION_RICHIESTA_SALVATA = "La richiesta di elaborazione \\u00E8 stata salvata.";

	public static final Object NOME_BATCH_AGGIORNA_DATI_INTERMEDIA = "PBAN-PROP-CERT";
	
	//Costanti di DAO
	public static final String SCHEMA_NAME = "PBANDI_RW";
	public static final String SCHEMA_NAME1 = "PBANDI";
	public static final String TABLE_NAME_PREFIX = "pbandi_";
	public static final String P_ID_UTENTE = "pIdUtente";
	public static final String RESULT = "result";
	public static final String ROOT_FILE_SYSTEM = "/pbstorage_online";
	public static final String DIR_PBAN = "/pbstorage_online/PBAN";
	
	public static final String P_DESC_BREVE_LINEA = "pDescBreveLinea";
	public static final String P_ID_UTENTE_INS = "pID_UTENTE_INS";
	public static final String ID_CAMPIONE = "vRetVal";
	public static final String P_ID_PROPOSTA_CERTIFICAZ = "pIdPropostaCertificaz";

	public static final String P_ID_PROPOSTA_CERTIFICAZIONE = "pIdPropostaCertificazione";	
	public static final String ID_PROGETTO = "pIdProgetto";

	//Costanti di Gestione proposte
	public static final int STATO_PROPOSTA_CERTIFICAZIONE_CHIUSURA_CONTI = 17;
	public static final String TEMPLATE_REPORT_CHIUSURA_CONTI = "nReportChiusuraConti";

	public static final String TIPO_DOCUMENTO_INDEX_REPORT_CHIUSURA_CONTI = "RCC";

	public static final String STATO_PROPOSTA_CERTIFICAZIONE_BOZZA = "00coda-bozza";

	public static final String STATO_PROPOSTA_CERTIFICAZIONE_APERTA = "05open";

	public static final String MAIL_ADDRESS_NO_REPLY_CSI_IT = "assistenzapiattaforma.bandi@csi.it";

	public static final String LINEA_DI_INTERVENTO_RADICE_POR_FESR = "POR-FESR";

	public static final String FLAG_FALSE = "N";
	public static final String FLAG_TRUE = "S";

	public static final String MIME_APPLICATION_XSL = "application/vnd.ms-excel";

	public static final String LINEA_DI_INTERVENTO_RADICE_PAR_FAS = "PAR-FAS";

	public static final String LINEA_DI_INTERVENTO_RADICE_POR_FESR_2014_20 = "POR-FESR-2014-2020";

	public static final String LINEA_DI_INTERVENTO_RADICE_POR_FESR_2021_27 = "POR-FESR-2021-2027";

	public static final String TEMPLATE_PROPOSTA_CERTIFICAZIONE_POR_FESR = "reportPropostaCertificazionePorFesr";

	public static final String TEMPLATE_PROPOSTA_CERTIFICAZIONE_PAR_FAS = "reportPropostaCertificazioneParFas";

	public static final String TEMPLATE_PROPOSTA_CERTIFICAZIONE_POR_FESR2014_20 = "nReportPropostaCertificazionePorFesr2014_20";

	public static final String TEMPLATE_PROPOSTA_CERTIFICAZIONE_POR_FESR2021_27 = "nReportPropostaCertificazionePorFesr2021_27";

	public static final String TEMPLATE_PROPOSTA_CERTIFICAZIONE_POR_FESR_PERCETTORI = "nReportPropostaCertificazionePorFesrPercettori";

	public static final BigDecimal ID_LINEA_DI_INTERVENTO_ASSE_I = new BigDecimal(203);

	public static final BigDecimal ID_LINEA_DI_INTERVENTO_ASSE_II = new BigDecimal(236);

	public static final BigDecimal ID_LINEA_DI_INTERVENTO_ASSE_III = new BigDecimal(204);

	public static final BigDecimal ID_LINEA_DI_INTERVENTO_ASSE_IV = new BigDecimal(226);

	public static final BigDecimal ID_LINEA_DI_INTERVENTO_ASSE_V = new BigDecimal(242);

	public static final BigDecimal ID_LINEA_DI_INTERVENTO_ASSE_VI = new BigDecimal(247);

	public static final BigDecimal ID_LINEA_DI_INTERVENTO_ASSE_VII = new BigDecimal(254);

	public static final String STATO_DOCUMENTO_INDEX_GENERATO = "GENERATO";

	public static final String MAIL_ADDRESSES_SEPARATOR = ",";

	public static final String ENTITA_C_PROPOSTA = "PBANDI_T_PROPOSTA_CERTIFICAZ";

	public static final String ENTITA_C_PROPOSTA_DETT = "PBANDI_T_DETT_PROPOSTA_CERTIF";

	public static final String SUBJECT_SUCCESSO_AVVIO_PROCEDURA_AGGIORNAMENTO_INT_FIN = "L'aggiornamento della proposta di certificazione intermedia finale è avvenuto con successo.";

	public static final String SUBJECT_ERRORE_AVVIO_PROCEDURA_AGGIORNAMENTO_INT_FIN = "Errore nell'avvio della procedura di aggiornamento della proposta di certificazione intermedia finale.";


	
	
}
