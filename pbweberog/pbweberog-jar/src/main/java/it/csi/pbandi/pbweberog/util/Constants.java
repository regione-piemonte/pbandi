/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.util;

import java.math.BigDecimal;



public class Constants {
	public final static String COMPONENT_NAME = "pbweberog";
	
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

	public static final String ROOT_FILE_SYSTEM = "/pbstorage_online";
	//public static final String ROOT_FILE_SYSTEM = "conf.rootFileSystem";
	
	public static final String PATH_FILE_SQL = "sql/pbandisrv/";

	public static final String APPLICATION_CODE = "pbweberog";

	public static final String TASKNAME_DICHDISPESA = "DICH-DI-SPESA";
	public static final String TASKNAME_GESTCHECKLIST = "GEST-CHECKLIST";
	public static final String TASKNAME_GESTAFFIDAMENTI = "GEST-AFFIDAMENTI";

	public static final String COD_TIPO_TRASFERIMENTO_PRIVATO = "1";
	public static final String COD_TIPO_TRASFERIMENTO_PUBBLICO = "2";

	public static final String TIPOLOGIA_CONTO_ECONOMICO_MASTER = "MASTER";
	
	/** Specifica se i progetti di un bando_linea utilizzano le attivitï¿½ di erogazione */
	public static final String BR13_ATTIVITA_EROGAZIONE_DISPONIBILE = "BR13";

	public static final String ERRORE_SERVER = "Non e' stato possibile portare a termine l'operazione."; 
	
	//Erogazione
	public static final String COD_CAUSALE_EROGAZIONE_SALDO = "SAL-INS";
	public static final String COD_CAUSALE_EROGAZIONE_PRIMO_ACCONTO = "PA-INS";
	public static final String COD_CAUSALE_EROGAZIONE_SECONDO_ACCONTO = "SA";
	public static final String COD_CAUSALE_EROGAZIONE_ULTERIORE_ACCONTO = "SA-INS";
	public static final String DESC_CAUSALE_EROGAZIONE_SALDO = "saldo - iter non standard";
	public static final String DESC_CAUSALE_EROGAZIONE_PRIMO_ACCONTO = "primo acconto - iter non standard";
	public static final String DESC_CAUSALE_EROGAZIONE_ULTERIORE_ACCONTO = "successivo acconto - iter non standard";
	
	public static final String FLAG_FALSE = "N";
	public static final String FLAG_TRUE = "S";

	public static final String MODALITA_EROGAZIONE = "it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDModalitaErogazioneVO";

	public static final String RUOLO_ADG_IST_MASTER = "ADG-IST-MASTER";

	public static final String CANCELLAZIONE_AVVENUTA_CON_SUCCESSO = "Cancellazione avvenuta con successo.";

	/*
	 * COSTANTI TIPO FIDEIUSSIONI
	 */
	public static final String COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_UNO = "1";
	public static final String COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_DUE = "2";
	public static final String COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_TRE = "3";
	
	public static final String COD_RICHIESTA_ANTICIPAZIONE = "RA";
	public static final String COD_RICHIESTA_II_ACCONTO = "R2A";
	public static final String COD_RICHIESTA_ULTERIORE_ACCONTO = "RUA";
	public static final String COD_RICHIESTA_SALDO = "SLD";
	
	//Rinuncia
	public static final String STATO_PROGETTO_RINUNCIA_BENEFICIARIO = "R";

	public static final String STATO_CONTO_ECONOMICO_IN_PROPOSTA_PER_GESTIONE_OPERATIVA = "IPG";

	public static final String STATO_CONTO_ECONOMICO_APPROVATO = "A";

	public static final String NOME_FILE_RINUNCIA_PREFIX = "ComunicazioneDiRinuncia";

	public static final String TIPO_DOCUMENTO_INDEX_COMUNICAZIONE_DI_RINUNCIA = "RIN";

	public static final String DIR_PBAN = "/pbstorage_online/PBAN";
	
	//Dati del progetto
	public static final String RUOLO_ENTE_GIURIDICO = "ENTE-GIURIDICO";

	public static final String RUOLO_PERSONA_FISICA = "PERSONA-FISICA";

	public static final String RUOLO_BENEFICIARIO = "BENEFICIARIO";

	public static final String TIPO_SEDE_INTERVENTO = "SI";

	public static final String DESC_NAZIONE_ITALIA = "ITALIA";

	public static final String FILTER_FIELD_ID_BANDO_LINEA = "progrBandoLineaIntervento";

	public static final String GESTIONE_PROGETTO_STATO_PROGRAMMA_ATTIVO = "1";

	public static final String GESTIONE_PROGETTO_STATO_PROGRAMMA_NON_ATTIVO = "2";

	public static final String COD_IGRUE_2007PI002FA011 = "2007PI002FA011";

	public static final long ID_PROCESSO_PROGRAMMAZIONE_2014_20 = 2L;

	public static final BigDecimal ID_TIPO_PERIODO_ANNO_CONTABILE =  new BigDecimal(3);

	public static final String TIPO_DOCUMENTO_INDEX_RICHIESTA_EROGAZIONE = "RE";

	public static final String ENTITA_C_RICHIESTA_EROGAZIONE = "PBANDI_T_RICHIESTA_EROGAZIONE";

	public static final String TIME_FORMAT_PER_NOME_FILE = "ddMMyyyy";

	public static final String COD_CAUSALE_EROGAZIONE_SALDO_NO_STANDARD = "SAL-INS";
	public static final String COD_CAUSALE_EROGAZIONE_PRIMO_ACCONTO_NO_STANDARD = "PA-INS";
	public static final String COD_CAUSALE_EROGAZIONE_ULTERIORE_ACCONTO_NO_STANDARD = "SA-INS";

	public static final int MAX_LENGHT_CAMPO_NOTE_4000 = 4000;

	public static final String COD_TIPO_CAUSALE_DISIMPEGNO_REVOCA = "REV";

	public static final String WARN_CONFERMA_SALVATAGGIO = "Confermare il salvataggio dei dati.";

	public static final String STATO_PROPOSTA_CERTIFICAZIONE_APERTA = "05open";


	public static final long ID_TIPO_SOGG_PERSONA_FISICA = new Long(1);

	public static final long ID_TIPO_SOGG_CORRELATO_RAPPRESENTANTE_LEGALE = new Long(1);

	public static final long ID_TIPO_SOGG_CORRELATO_DELEGATO_FIRMA = new Long(21);	
	
	public static final BigDecimal ID_NAZIONE_ITALIA = new BigDecimal(118);
	
	// Registro controlli
	public static final String IRREGOLARITA_TRASFORMA_IN_DEFINITIVA = "D";
	public static final String IRREGOLARITA_TRASFORMA_IN_REGOLARE = "r";

	public static final java.lang.String TIPO_DOCUMENTO_INDEX_SCHEDA_OLAF_IRREGOLARITA = "SOI";

	public static final String TIPO_DOCUMENTO_INDEX_DATI_AGGIUNTIVI_IRREGOLARITA = "DAI";

	
	//Recuperi
	public static final String COD_FIDEIUSSIONI_TIPO_RECUPERO_SOPPRESSIONE = "SO";

	// Chiusura del progetto
	public static final String STATO_PROGETTO_CHIUSO = "C";
	public static final String STATO_PROGETTO_CHIUSO_DI_UFFICIO = "CU";

	//Monitoraggio controlli
	public static final BigDecimal TIPO_PERIODO_ANNO_CONTABILE = new BigDecimal(3);

	public static final String TEMPLATE_REPORT_PROGETTI_CAMPIONI = "reportProgettiCampionati";

	public static final String FLAG_RILEVAZIONE_CONTROLLI = "R";

	public static final String DESC_TIPO_LINEA_NORMATIVA = "Normativa";

	public static final String FLAG_CAMPIONIONAMENTO_CERTIFICAZIONE = "C";

	public static final String DESC_TIPO_LINEA_ASSE = "Asse";

	public static final BigDecimal ID_LINEA_DI_INTERVENTO_POR_FESR_14_20 = new BigDecimal(202);

	public static final long ID_TIPO_ANAGRAFICA_PERSONA_FISICA = new Long(16);

	public static final String SCENARIO_CUP_NO_GEFO_NO = "CUP_NO_GEFO_NO";

	public static final String SCENARIO_CUP_SI_GEFO_SI = "CUP_SI_GEFO_SI";

	public static final String SCENARIO_CUP_NO_GEFO_SI = "CUP_NO_GEFO_SI";

	public static final String SCENARIO_CUP_SI_GEFO_NO = "CUP_SI_GEFO_NO";

	public static final String CONTENT_DISPOSITION = "Content-Disposition";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String FILE_NAME_KEY = "filename";

	public static final String COSTANTE_INDIRIZZO_EMAIL_MITTENTE = "conf.serverConfiguration.indirizzoEmailMittente";

	public static final String DESC_BREVE_LINEA_BANDIREGP = "BANDIREGP";
	
	public static final String BUONO_DOMICILIARITA = "BUONO DOMICILIARITA";
	public static final String BUONO_RESIDENZIALITA = "BUONO RESIDENZIALITA";
	public static final String BUONO_DOMICILIARITA_ENDPOINT = "buonodomcallbanEndpoint";
	public static final String BUONO_RESIDENZIALITA_ENDPOINT = "buonorescallbanEndpoint";
	public static final String USER_DOMICILIARITA = "userDomiciliarita";
	public static final String PASS_DOMICILIARITA = "passDomiciliarita";
	public static final String USER_RESIDENZIALITA = "userResidenzialita";
	public static final String PASS_RESIDENZIALITA = "passResidenzialita";
	public static final String TEMPLATE_MAIL_REVOCA = "ws_email_testo_b.";

}


