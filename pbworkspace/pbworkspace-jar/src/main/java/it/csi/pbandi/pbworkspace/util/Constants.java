/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.util;

public class Constants {
	public final static String COMPONENT_NAME = "pbworkspace";

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

	public static final Long ID_OPERAZIONE_CERTIFICAZIONE = 1L;
	public static final Long ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE = 2L;
	public static final Long ID_OPERAZIONE_LINEE_FINANZIAMENTO = 3L;
	public static final Long ID_OPERAZIONE_RILEVAZIONE_CONTROLLI = 4L;
	public static final Long ID_OPERAZIONE_REGISTRO_CONTROLLI = 5L;
	public static final Long ID_OPERAZIONE_BILANCIO = 6L;
	public static final Long ID_OPERAZIONE_RENDICONTAZIONE_MASSIVA = 7L;
	public static final Long ID_OPERAZIONE_TRASFERIMENTI = 8L;
	public static final Long ID_OPERAZIONE_ARCHIVIO_FILE = 9L;
	public static final Long ID_OPERAZIONE_NOTIFICHE_VIA_MAIL = 10L;
	public static final Long ID_OPERAZIONE_DOCUMENTI_PROGETTO = 11L;
	public static final Long ID_OPERAZIONE_GESTIONE_ECONOMIE = 12L;
	public static final Long ID_OPERAZIONE_AMMINISTRAZIONE = 13L;
	
	public static final String DESC_BREVE_NAZIONE_ITALIA = "000";
	public static final String DESC_BREVE_TIPO_ENTE_COMPETENZ_DIR_REG = "ADG";
	public static final String DESC_BREVE_TIPO_ENTE_COMPETENZ_PA = "PA";
	public static final String KEY_FLAG_VISIBILE = "S";
	
	public static final String TIPO_DIP_DIR_DU = "DU";
	public static final String TIPO_DIP_DIR_DR = "DR";
	public static final String TIPO_DIP_DIR_NA = "NA";
	public static final String TIPO_DIP_DIR_PA = "PA";
	
	public static final String ID_PROCESSO_NUOVA_PROGRAMMAZIONE = "2";
	public static final String ID_PROCESSO_CULTURA = "3";
	public static final String ID_PROCESSO_WELFARE_ABITATIVO = "4";
	public static final Long ID_LINEA_INTERVENTO_NUOVA_PROGRAMMAZIONE = new Long(202);
	
	public static final String DESC_BREVE_NORMATIVA_21_27 = "POR-FESR-2021-2027";
	
	public static final String DESC_BREVE_PERMESSO_DASHBOARD = "DASHBOARD";

}
