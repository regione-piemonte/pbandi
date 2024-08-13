/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.util;

public class Constants {
	
	public final static String COMPONENT_NAME = "pbweb";
	public static final String USERINFO_SESSIONATTR = "currentUserInfo";
	public static final long ID_UTENTE_FITTIZIO = -1L;
	public static final String ACTIVITY_TOKEN_SEPARATOR = "\u00a3A\u00a3";
	public static final String ACTIVITY_ASSIGNMENT_OPERATOR = "\u00a3a\u00a3";
	public static final int VALORE_TIPO_SOGG_PERSONA_FISICA = 1;
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final String YEAR_FORMAT = "yyyy";
	public static final String TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
	public static final String ORA_FORMAT = "HH:mm:ss";
	public static final String TIME_FORMAT_PER_NOME_FILE = "ddMMyyyy";
	public static final String DATEHOUR_FORMAT_PER_NOME_FILE = "ddMMyyyyHHmmss";	
	public static final String DATE_FORMAT_REQUEST = "yyyy-MM-dd";
	public static final String SESSION_KEY_CURRENT_ACTIVITY = "pbandi::CURRENT_ACTIVITY";
	
	public static final String TASKNAME_VALIDAZIONE = "Validazione della dichiarazione di spesa";
	public static final String TASKNAME_VALIDAZIONE_FINALE = "Validazione della dichiarazione di spesa finale";
	public static final String TASKNAME_RENDICONTAZIONE = "Dichiarazione di spesa";
	public static final String TASKNAME_DICHIARAZIONE_DI_SPESA_INTEGRATIVA = "Dichiarazione di spesa integrativa";
	public static final String TASKNAME_GESTIONE_CHECKLIST = "Gestione checklist del progetto";
	public static final String TASKNAME_RETTIFICA = "Gestione spesa validata";	
	public static final String TASKNAME_DICHDISPESA = "DICH-DI-SPESA";
	public static final String TASKNAME_GESTCHECKLIST = "GEST-CHECKLIST";
	public static final String TASKNAME_ARCHIVIOFILE = "ARCHFILE";
	
	public static final String TASK_IDENTITY_DICHDISPESA = "DICH-DI-SPESA";
	public static final String TASK_IDENTITY_GESTCHECKLIST = "GEST-CHECKLIST";
	public static final String TASK_IDENTITY_ARCHIVIOFILE = "ARCHFILE";
	public static final String TASK_IDENTITY_VALIDAZIONE = "VALID-DICH-SPESA";
	public static final String TASK_IDENTITY_VALIDAZIONE_FINALE = "VALID-DICH-SPESA-FINALE";
	public static final String TASK_IDENTITY_SPESA_VALIDATA = "GEST-SPESA-VALID";
	
	
	
	// OLD
	
	public static final String COVID_MESSAGES_MAP_KEY = "COVID_MESSAGES_MAP_KEY";
	
//	MESSAGE CODES
	public static final Long MESSAGE_CODE_REQUISITI_OK = new Long(1);
	public static final Long MESSAGE_CODE_REQUISITI_DA_RIPIANIFICARE = new Long(2);
	public static final Long MESSAGE_CODE_REQUISITI_DA_INTEGRARE = new Long(5);
	public static final Long MESSAGE_CODE_REQUISITI_DOMANDA_KO = new Long(3);
	public static final Long MESSAGE_CODE_REQUISITI_OK_TOO_EARLY = new Long(6);
	public static final Long MESSAGE_CODE_REQUISITI_OK_TOO_LATE = new Long(7);
	public static final Long MESSAGE_CODE_IDENTIFICATIVO_AZIENDA_NON_VALIDO = new Long(4);
	public static final Long MESSAGE_CODE_EMAIL_NON_VALIDO = new Long(4);
	public static final Long MESSAGE_CODE_CHIAVE_DUPLICATA = new Long(4);
	public static final String TOKEN_DATA_DA = "{data_da}";
	public static final String TOKEN_DATA_A = "{data_a}";
	public static final String NO_PEC = new String("NO_PEC");
	public static final String PEC = new String("PEC");
	public static final Object MESSAGE_CODE_REQUISITI_MAIL_INVIATA_OK = new Long(8);
	public static final String SERVER_MAIL = new String ("mailfarm-app.csi.it");
	public static final String FROM_EMAIL = new String ("no-reply@csi.it");
	public static final String FROM_NAME = new String ("Bonus Piemonte");
	public static final String OGGETTO_MAIL = new String ("Informazioni Bonus Piemonte");

	public static final Long MESSAGE_CODE_REQUISITI_KO_FASEI = new Long(9);
	
	public static final int ID_TIPO_DOC_SPESA_CEDOLINO = 1;
	public static final int ID_TIPO_DOC_SPESA_CEDOLINO_A_PROGETTO = 23;
	public static final int ID_TIPO_DOC_SPESA_CEDOLINO_COSTI_STANDARD = 57;
	public static final int ID_TIPO_DOC_SPESA_NOTA_SPESE = 5;
	
	public static final String TIPOLOGIA_PERSONA_FISICA    = "PF";
	public static final String TIPOLOGIA_PERSONA_GIURIDICA = "EG";
		
	public static final int ID_TIPOLOGIA_PERSONA_FISICA    = 1;
	public static final int ID_TIPOLOGIA_PERSONA_GIURIDICA = 2;
	
	public static final Long FLAG_FORMA_GIURIDICA_PUBBLICA = 2L;
	public static final Long FLAG_FORMA_GIURIDICA_PRIVATA  = 1L;
	
	public static final Long ID_TIPO_DOCUMENTO_INDEX_DICHIARAZIONE_AFFIDAMENTO = 26L;
	
	public static final String DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_BEN_MASTER = "BEN-MASTER";
	public static final String DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_BENEFICIARIO = "BENEFICIARIO";
	public static final String DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_PERSONA_FISICA = "PERSONA-FISICA";
	public static final String DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_PERSONA_GIURIDICA = "ENTE-GIURIDICO";
	public static final String DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_ISTRUTTORE_AFFIDAMENTO = "ISTR-AFFIDAMENTI";
	public static final String DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_OPERATORE_ISTRUTTORIA_PROGETTI = "OPE-ISTR-PROG";
	public static final String DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_ADG_ISTRUTTORE = "ADG-ISTRUTTORE";
	public static final String DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_OI_ISTRUTTORE = "OI-ISTRUTTORE";
	
	public static final int ID_STATO_AFFIDAMENTO_DAINVIARE = 1;
	public static final int ID_STATO_AFFIDAMENTO_INVERIFICA = 2;
	public static final int ID_STATO_AFFIDAMENTO_VPARZIALE = 3;
	public static final int ID_STATO_AFFIDAMENTO_VDEFINITIVA = 4;
	public static final int ID_STATO_AFFIDAMENTO_RICINTEGRAZ = 5;
	
	// Jira PBANDI-2829.
	public static final String COD_TIPO_CHEKCLIST_IN_LOCO = "CLIL";
	public static final String COD_TIPO_CHEKCLIST_DOCUMENTALE = "CL";
	public static final String COD_STATO_TIPO_DOC_INVIATO = "I";
	public static final String COD_STATO_TIPO_DOC_DEFINITIVO = "D";
	// Jira Pbandi-2859
	public static final String COD_TIPO_CHEKCLIST_AFFIDAMENTO_IN_LOCO = "CLILA";
	public static final String COD_TIPO_CHEKCLIST_AFFIDAMENTO_DOCUMENTALE = "CLA";
	
	public static final String COSTANTE_USER_SPACE_LIMIT = "conf.userSpaceLimit";
	public static final String COSTANTE_ARCHIVIO_FILE_SIZE_LIMIT = "conf.archivioFileSizeLimit";
	public static final String COSTANTE_ARCHIVIO_ALLOWED_FILE_EXT = "conf.archivioAllowedFileExtensions";
	public static final String COSTANTE_DIGITAL_SIGN_SIZE_LIMIT = "conf.digitalSignFileSizeLimit";
	public static final String COSTANTE_ACTA_REPOSITORYNAME = "conf.actaRepositoryName";
	public static final String COSTANTE_ACTA_UTENTEAPPKEY = "conf.actaUtenteAppkey";
	public static final String COSTANTE_ACTA_UTENTECF = "conf.actaUtenteCodfiscale";
	
	public static final String COSTANTE_ACTA_IDAOO = "conf.actaIdAOO";
	public static final String COSTANTE_ACTA_IDNODO = "conf.actaIdNodo";
	public static final String COSTANTE_ACTA_IDSTRUTTURA = "conf.actaIdStruttura";
	
	public static final String COD_TIPO_DOCUMENTO_INDEX_ARCHIVIO_FILE = "AF";
	public static final String COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO = "CLIL";
	public static final String COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_VALIDAZIONE = "CL";
	
	public static final String ENTITA_C_FILE = "PBANDI_T_FILE";
	public static final String ENTITA_C_FOLDER = "PBANDI_T_FOLDER";
	public static final String ENTITA_C_CHECKLIST = "PBANDI_T_CHECKLIST";
	public static final String ENTITA_T_FORNITORE = "PBANDI_T_FORNITORE";
	public static final String ENTITA_T_DOCUMENTO_DI_SPESA = "PBANDI_T_DOCUMENTO_DI_SPESA";
	public static final String ENTITA_T_COMUNICAZ_FINE_PROG = "PBANDI_T_COMUNICAZ_FINE_PROG";
	public static final String ENTITA_T_CONTO_ECONOMICO = "PBANDI_T_CONTO_ECONOMICO";
	public static final String ENTITA_T_DICH_SPESA = "PBANDI_T_DICHIARAZIONE_SPESA";
	public static final String ENTITA_T_PAGAMENTO = "PBANDI_T_PAGAMENTO";
	public static final String ENTITA_T_RICHIESTA_EROGAZIONE = "PBANDI_T_RICHIESTA_EROGAZIONE";
	public static final String ENTITA_T_INTEGRAZIONE_SPESA = "PBANDI_T_INTEGRAZIONE_SPESA";
	public static final String ENTITA_R_FORNITORE_QUALIFICA = "PBANDI_R_FORNITORE_QUALIFICA";
	
	public static final String CONTENT_DISPOSITION = "Content-Disposition";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String FILE_NAME_KEY = "filename";
	
	public static final String PATH_FILE_SQL = "sql/pbandisrv/";
	public static final String PATH_FILE_JASPER = "report/";
	
	// Esiti della lettura dell'xml di una fattura elettronica.
	public static final String FATT_ELETT_ERRORE_NON_PREVISTO = "ERRORE_NON_PREVISTO";
	public static final String FATT_ELETT_NESSUN_XML_SELEZIONATO = "NESSUN_XML_SELEZIONATO";
	public static final String FATT_ELETT_XML_NON_CORRETTO = "XML_NON_CORRETTO";
	public static final String FATT_ELETT_FORNITORE_NUOVO = "FORNITORE_NUOVO";
	public static final String FATT_ELETT_FORNITORE_NUOVO_TIPO_OP_3 = "FORNITORE_NUOVO_TIPO_OP_3";
	public static final String FATT_ELETT_FORNITORE_UGUALE = "FORNITORE_UGUALE";
	public static final String FATT_ELETT_FORNITORE_DIVERSO = "FORNITORE_DIVERSO";
	public static final String FATT_ELETT_FORNITORE_DIVERSO_TIPO_OP_3 = "FORNITORE_DIVERSO_TIPO_OP_3";	
	public static final String FATT_ELETT_DOC_SPESA = "FATT_ELETT_DOC_SPESA";
	public static final String FATT_ELETT_ESITO = "FATT_ELETT_ESITO";
	
	public static final String COD_TIPO_DOC_DI_SPESA_CEDOLINO = "CD";
	public static final String COD_TIPO_DOC_DI_SPESA_CEDOLINO_COCOPRO = "CC";
	public static final String COD_TIPO_DOC_DI_SPESA_CEDOLINO_COSTI_STANDARD = "CDCS";
	public static final String COD_TIPO_DOC_DI_SPESA_DOCUMENTO_GENERICO = "DG";
	public static final String COD_TIPO_DOC_DI_SPESA_ATTO_DI_LIQUIDAZIONE = "ADL";
	public static final String COD_TIPO_DOC_DI_SPESA_SPESE_FORFETTARIE = "SF";
	public static final String COD_TIPO_DOC_DI_SPESA_SPESE_GENERALI_FORFETTARIE_COSTI_STANDARD = "SGFCS";
	public static final String COD_TIPO_DOC_DI_SPESA_SPESE_EXTRA_AFFIDAMENTO = "SXA";
	public static final String COD_TIPO_DOC_DI_SPESA_NOTA_CREDITO = "NC";
	public static final String COD_TIPO_DOC_DI_SPESA_AUTOCERTIFICAZIONE_SPESE = "AS";
	public static final String COD_TIPO_DOC_DI_SPESA_SAL_SENZA_QUIETANZA = "SALSQ";
	public static final String COD_TIPO_DOC_DI_SPESA_SAL_CON_QUIETANZA = "SALCQ";
	public static final String COD_TIPO_DOC_DI_SPESA_COMPENSO_MENSILE_TIROCINANTE = "CMT";
	public static final String COD_TIPO_DOC_DI_SPESA_COMPENSO_IMPRESA_ARTIGIANA = "CIA";
	public static final String COD_TIPO_DOC_DI_SPESA_COMPENSO_SOGGETTO_GESTORE = "CSG";
	
	public static final String TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_CLONA = "clona";
	public static final String TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_INSERISCI = "inserisci";
	public static final String TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_MODIFICA = "modifica";
	public static final String TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_DETTAGLIO = "dettaglio";
	public static final String TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_CANCELLA = "cancella";
	public static final String TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_ASSOCIA = "associa";
	
	public static final String DESCRIZIONE_STATO_DOCUMENTO_DICHIARABILE = "DICHIARABILE";
	public static final String CODICE_STATO_DOCUMENTO_DICHIARABILE = "I";
	public static final String CODICE_STATO_DOCUMENTO_IN_VALIDAZIONE = "D";
	public static final String CODICE_STATO_DOCUMENTO_SOSPESO = "S";
	public static final String CODICE_STATO_DOCUMENTO_PARZIALMENTE_VALIDATO = "P";
	public static final String CODICE_STATO_DOCUMENTO_VALIDATO = "V";
	public static final String CODICE_STATO_DOCUMENTO_NON_VALIDATO = "N";
	public static final String CODICE_STATO_DOCUMENTO_RESPINTO = "R";
	public static final String CODICE_STATO_DOCUMENTO_DA_COMPLETARE = "DC";
	
	public static final String SEPARATORE_SOGGETTO_PROGETTO_PARTNER = ";";
	
	public final static String VALUE_RADIO_RENDICONTAZIONE_CAPOFILA = "rbTipoCapofila";
	public final static String VALUE_RADIO_RENDICONTAZIONE_TUTTI = "rbTipoTutti";
	public final static String VALUE_RADIO_RENDICONTAZIONE_PARTNERS = "rbTipoPartners";
	public final static String VALUE_RADIO_DOCUMENTI_DI_SPESA_GESTITI = "rbGestiti";
	public final static String VALUE_RADIO_DOCUMENTI_DI_SPESA_TUTTI = "rbTutti";
	
	public final static String TIPO_INVIO_DIGITALE = "D";
	public final static String TIPO_INVIO_CARTACEO = "C";
	
	public static final String CODICE_TIPO_DICHIARAZIONE_DI_SPESA_INTERMEDIA = "I";
	public static final String CODICE_TIPO_DICHIARAZIONE_DI_SPESA_FINALE = "F";
	public static final String CODICE_TIPO_DICHIARAZIONE_DI_SPESA_INTEGRATIVA = "IN";
	public static final String CODICE_TIPO_DICHIARAZIONE_DI_SPESA_FINALE_CON_COMUNICAZIONE = "FC";
	
	public static final String DESC_BREVE_STATO_DOCUMENTO_INDEX_ACQUISITO = "ACQUISITO";
	public static final String DESC_BREVE_STATO_DOCUMENTO_INDEX_VALIDATO = "VALIDATO";
	public static final String DESC_BREVE_STATO_DOCUMENTO_INDEX_NON_VALIDATO = "NON-VALIDATO";
	public static final String DESC_BREVE_STATO_DOCUMENTO_INDEX_INVIATO = "INVIATO";
	public static final String DESC_BREVE_STATO_DOCUMENTO_INDEX_CLASSIFICATO = "CLASSIFICATO";
	public static final String DESC_BREVE_STATO_DOCUMENTO_INDEX_PROTOCOLLATO = "PROTOCOLLATO";
	
	public static final String VALUE_RADIO_OPERAZIONE_AUTOMATICA_VALIDAZIONE_VALIDARE = "VALIDARE";
	public static final String VALUE_RADIO_OPERAZIONE_AUTOMATICA_VALIDAZIONE_INVALIDARE = "INVALIDARE";
	public static final String VALUE_RADIO_OPERAZIONE_AUTOMATICA_VALIDAZIONE_RESPINGERE = "RESPINGERE";

	public static final String STATO_TIPO_DOC_INDEX_BOZZA = "B";
	public static final String STATO_TIPO_DOC_INDEX_DA_AGGIORNARE = "DA";
	public static final String STATO_TIPO_DOC_INDEX_DEFINITIVO = "D";
	public static final String STATO_TIPO_DOC_INDEX_INVIATO = "I";
	public static final String STATO_TIPO_DOC_INDEX_INVIATO_PREGRESSO = "IP";
	
	public static final String DESC_BREVE_TIPO_DOC_INDEX_DS  = "DS";
	public static final String DESC_BREVE_TIPO_DOC_INDEX_CFP = "CFP";
	public static final String DESC_BREVE_TIPO_DOC_INDEX_RA = "RA";
	public static final String DESC_BREVE_TIPO_DOC_INDEX_PR = "PR";
	public static final String DESC_BREVE_TIPO_DOC_INDEX_RE = "RE";
	public static final String DESC_BREVE_TIPO_DOC_INDEX_R2A = "R2A";
	public static final String DESC_BREVE_TIPO_DOC_INDEX_SLD = "SLD";
	public static final String DESC_BREVE_TIPO_DOC_INDEX_RUA = "RUA";

	public static final String HEADER_NOME_CHIAMANTE = "invocante";
	public static final Object HEADER_VALORE_CHIAMANTE = "PBANDI-PBWEB";
	
	public static final String DESC_BREVE_COLONNA_QTES_AGG_DEF  = "QTE_AGGI_2";
	
	public static final String DESC_BREVE_SOGGETTO_FINANZIATORE_FONDO_COMPLEMENTARE  = "Fondo_Complementare";
	
	public static final String BUONO_DOMICILIARITA = "BUONO DOMICILIARITA";
	public static final String BUONO_RESIDENZIALITA = "BUONO RESIDENZIALITA";
	public static final String BUONO_DOMICILIARITA_ENDPOINT = "buonodomcallbanEndpoint";
	public static final String BUONO_RESIDENZIALITA_ENDPOINT = "buonorescallbanEndpoint";
	public static final String USER_DOMICILIARITA = "userDomiciliarita";
	public static final String PASS_DOMICILIARITA = "passDomiciliarita";
	public static final String USER_RESIDENZIALITA = "userResidenzialita";
	public static final String PASS_RESIDENZIALITA = "passResidenzialita";
	public static final String ERRORE_SERVIZIO_SANITA = "L'esito della validazione non è stato comunicato al sistema per la rendicontazione del beneficiario";
	
	public static final String DESC_BREVE_TIPO_ANAGRAFICA_PERSONA_FISICA  = "PERSONA-FISICA";
	
	public static final String DESC_BREVE_CAUSALE_EROGAZIONE_PRIMO_ACCONTO  = "PA";
	public static final String DESC_BREVE_CAUSALE_EROGAZIONE_ULTERIORE_ACCONTO  = "UA";
	public static final String DESC_BREVE_CAUSALE_EROGAZIONE_SALDO  = "SAL";
	
	public static final String MAIL_ADDRESSES_SEPARATOR = ",";
	
	public static final String DESC_BREVE_RUOLO_ENTE_COMP_RAGIONERIA_DELEGATA = "RAGIONERIA DELEGATA";
		
}