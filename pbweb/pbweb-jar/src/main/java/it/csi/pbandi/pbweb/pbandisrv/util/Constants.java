package it.csi.pbandi.pbweb.pbandisrv.util;

import static it.csi.pbandi.pbweb.pbandiutil.common.Constants.FLAG_FALSE;
import static it.csi.pbandi.pbweb.pbandiutil.common.Constants.FLAG_TRUE;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Classe delle costanti applicative.
 * </p>
 * 
 */
public interface Constants {
	// long ID_UTENTE_FITTIZIO = -1L;
	String ID_IRIDE_FITTIZIO = "utentefittizio";
	String COD_UTENTE_FITTIZIO = "-1";
	final static String ID_IRIDE_DEMO_30 = "AAAAAA00A11K000S/DEMO 30/CSI PIEMONTE/CSI_NUOVACA/20090420122616/8/2E92RjccMOaEN6PusaN/KA==";
	final static String ID_IRIDE_DEMO_34 = "AAAAAA00A11O000W/DEMO 34/CSI PIEMONTE/CSI_NUOVACA/20100614103714/8/K7lZbXIyUTH530O6WGYfhA==";

	/*
	 * identificativo dell'applicativo.
	 */
	String APPLICATION_CODE = "pbandisrv";
	String STOPWATCH_LOGGER = APPLICATION_CODE;

	String DATE_FORMAT = "dd/MM/yyyy";
	String TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
	String ORA_FORMAT = "HH:mm:ss";
	String TIME_FORMAT_PER_NOME_FILE = "ddMMyyyy";
	String DATEHOUR_FORMAT_PER_NOME_FILE = "ddMMyyyyHHmmss";

	String ESITO_TRACCIAMENTO_OK = "S";
	String ESITO_TRACCIAMENTO_KO = "N";;

	String DATA_EFFETTIVO_PAGAMENTO = "DT_PAGAMENTO";
	String DATA_PRESENTAZIONE_DOMANDA = "DATA_PRESENTAZIONE_DOMANDA";
	String DATA_VALUTA_PAGAMENTO = "DT_VALUTA";

	Long QUALIFICA_NESSUNA = 0L;

	Long ID_DEFINIZIONE_PROCESSO_DEFAULT = 8L;

	String NOME_PACKAGE_JAVA = "it.csi.pbandi";
	String NOME_PACKAGE_UTILITY_ONLINE = "PCK_pbandi_utility_online";
	String NOME_PACKAGE_PROC_BILANCIO = "PCK_PBANDI_BILANCIO";
	String NOME_PACKAGE_PROC_CAR_MASS_DOC_SPESA = "PCK_PBANDI_CAR_MASS_DOC_SPESA";
	String NOME_PACKAGE_PROC_PROCESSO = "pck_pbandi_processo";
	String NOME_PACKAGE_PROC_CAMBIO_BENEFICIARIO= "PCK_PBANDI_CAMBIO_BENEFICIARIO";
	String NOME_PACKAGE_PCK_PBANDI_CERTIFICAZIONE= "PCK_PBANDI_CERTIFICAZIONE";
	String NOME_PACKAGE_PCK_PBANDI_CERTIFICAZIONE_REV= "PCK_PBANDI_CERTIFICAZIONE_REV";
	String NOME_PACKAGE_PCK_BANDI_CAMPIONAMENTO = "PCK_PBANDI_CAMPIONAMENTO";
	String NOME_PACKAGE_PCK_PBANDI_NOTIFICH = "PCK_PBANDI_NOTIFICHE";
	
	String APP_COMPANY_HOME_CM_BANDI = "/app:company_home/cm:bandi";

	
	String COD_STATO_VALIDAZIONE_DICHIARATO = "R";
	String COD_STATO_VALIDAZIONE_VALIDATO = "V";
	String COD_STATO_VALIDAZIONE_PARZIALMENTE_VALIDATO = "P";

	/*
	 * COSTANTI COD CAUSALI EROGAZIONE
	 */

	String COD_CAUSALE_EROGAZIONE_ULTERIORE_ACCONTO = "UA";
	String COD_CAUSALE_EROGAZIONE_SECONDO_ACCONTO = "SA";
	String COD_CAUSALE_EROGAZIONE_PRIMO_ACCONTO = "PA";
	String COD_CAUSALE_EROGAZIONE_SALDO = "SAL";
	String COD_CAUSALE_EROGAZIONE_SALDO_NO_STANDARD = "SAL-INS";
	String COD_CAUSALE_EROGAZIONE_PRIMO_ACCONTO_NO_STANDARD = "PA-INS";
	String COD_CAUSALE_EROGAZIONE_ULTERIORE_ACCONTO_NO_STANDARD = "SA-INS";

	/*
	 * COSTANTI TIPO FIDEIUSSIONI
	 */
	String COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_UNO = "1";
	String COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_DUE = "2";
	String COD_FIDEIUSSIONI_TIPO_TRATTAMENTO_TRE = "3";

	/*
	 * COSTANTI TIPO RECUPERO
	 */
	String COD_FIDEIUSSIONI_TIPO_RECUPERO_RECUPERO = "RE";
	String COD_FIDEIUSSIONI_TIPO_RECUPERO_PRERECUPERO = "PR";
	String COD_FIDEIUSSIONI_TIPO_RECUPERO_SOPPRESSIONE = "SO";

	/*
	 * COSTANTI EROGAZIONE E LIQUIDAZIONE
	 */
	String COD_EROGAZIONE_PRESENTE = "E";
	String COD_LIQUIDAZIONE_PRESENTE = "L";

	 

	/*
	 * CONSTANTS INDEX
	 */
	String OPERAZIONE_CREA_CONTENT = "IndexDAO.creaContent";
	String OPERAZIONE_AGGIORNA_CONTENT = "IndexDAO.aggiornaContent";

	/*
	 * CONSTANTS SIMON
	 */
	String OPERAZIONE_DETTAGLIO_CUP = "SimonDAO.allineaCup";

	String OPERAZIONE_CREA_CONTENT_CHECKLIST_VALIDAZIONE = "IndexDAO.creaContentCheckListValidazione";
	String OPERAZIONE_AGGIORNA_CONTENT_CHECKLIST_VALIDAZIONE = "IndexDAO.aggiornaContentCheckListValidazione";
	String OPERAZIONE_CREA_CONTENT_DICHIARAZIONE = "IndexDAO.creaContentDichiarazioneDiSpesa";
	String OPERAZIONE_CREA_CONTENT_DICHIARAZIONE_FINALE_PROPOSTA_CERTIFICAZIONE = "IndexDAO.creaContentAllegatoDichichiarazioneFinalePropostaCertificazione";
	String OPERAZIONE_CREA_CONTENT_IRREGOLARITA = "IndexDAO.creaContentIrregolarita";
	String OPERAZIONE_CREA_CONTENT_PROPOSTA_CERTIFICAZIONE = "IndexDAO.creaContentPropostaCertificazione";
	String OPERAZIONE_CREA_CONTENT_PROPOSTA_PROGETTO_CERTIFICAZIONE = "IndexDAO.creaContentPropostaProgettoCertificazione";
	String OPERAZIONE_CREA_CONTENT_PROPOSTA_RIMODULAZIONE = "IndexDAO.creaContentPropostaDiRimodulazione";
	String OPERAZIONE_CREA_CONTENT_RIMODULAZIONE = "IndexDAO.creaContentRimodulazione";
	String OPERAZIONE_CREA_CONTENT_REPORT_PROPOSTA_CERTIFICAZIONE = "IndexDAO.creaContentReportPropostaCertificazione";
	String OPERAZIONE_CREA_CONTENT_REPORT_CHIUSURA_CONTI = "IndexDAO.creaContentReportChiusuraConti";
	String OPERAZIONE_CREA_CONTENT_RICHIESTA_EROGAZIONE = "IndexDAO.creaContentRichiestaErogazione";
	String OPERAZIONE_CREA_CONTENT_DICHIARAZIONE_RINUNCIA = "IndexDAO.creaContentDichiarazioneRinuncia";
	String OPERAZIONE_CREA_CONTENT_ARCHIVIO_FILE= "IndexDAO.creaContentArchivioFile";
	String OPERAZIONE_CREA_CONTENT_REPORT_DETTAGLIO_DOC_SPESA= "IndexDAO.creaContentReportDettaglioDocSpesa";
	String OPERAZIONE_CREA_CONTENT_UPLOAD_FILE= "IndexDAO.creaContentUploadFile";
	/*
	 * KEY PATH INDEX CERTIFICAZIONE
	 */
	String KEY_PATH_ALLEGATI_PROPOSTA = "path_allegatiProposta";
	String KEY_PATH_ALLEGATI_PROPOSTA_PROGETTO = "path_allegatiPropostaProgetto";
	String KEY_PATH_ALLEGATI_PROPOSTA_DICH_FINALE = "path_allegatiPropostaDichiarazioneFinale";
	String KEY_PATH_CERTIFICAZIONE = "path_certificazione";
	String KEY_PATH_CHECKLIST_VALIDAZIONE = "path_checklistValidazione";
	String KEY_PATH_CHECKLIST_IN_LOCO = "path_checklistInLoco";
	String KEY_PATH_DICHIARAZIONE = "path_dichiarazione";
	String KEY_PATH_EROGAZIONE = "path_erogazione";
	String KEY_PATH_REPORT_PROPOSTA = "path_reportProposta"; 
	String KEY_PATH_REPORT_CHIUSURA_CONTI = "path_reportChiusuraConti";
	String KEY_PATH_PROPOSTA_RIMODULAZIONE = "path_propostaDiRimodulazione";
	String KEY_PATH_RICHIESTA_EROGAZIONE = "path_richiestaErogazione";
	String KEY_PATH_RIMODULAZIONE = "path_rimodulazione";
	String KEY_PATH_ROOT = "path_root";
	String KEY_PATH_VERBALE_ALLEGATO_CHECKILIST_IN_LOCO = "path_verbaleAllegatoChecklistInLoco";
	String KEY_PATH_DICHIARAZIONE_RINUNCIA = "path_dichiarazioneRinuncia";
	String KEY_PATH_RELAZIONE_TECNICA = "path_relazioneTecnica";
	String KEY_PATH_COMUNICAZIONE_FINE_PROGETTO = "path_comunicazione_fine_progetto";
	String KEY_PATH_ARCHIVIO_FILE= "path_archivio_file";
	String KEY_PATH_REPORT_DETTAGLIO_DOC_SPESA= "path_report_dettaglio_doc_spesa";
	String KEY_PATH_UPLOAD_FILE= "path_upload_file";
	
	/*
	 * KEY PATH INDEX IRREGOLARITA
	 */
	String KEY_PATH_IRREGOLARITA = "path_irregolarita";
	String KEY_PATH_SCHEDA_OLAF = "path_schedaOLAF";
	String KEY_PATH_DATI_AGGIUNTIVI = "path_datiAggiuntivi";
	String DOC_SUFFIX_SCHEDA_OLAF = "OLAF";
	String DOC_SUFFIX_DATI_AGGIUNTIVI = "DA";
	/*
	 * MIME TYPE
	 */
	String PREFIX_PBANDI = "pbandi:";
	String MIME_APPLICATION_PDF = "application/pdf";
	String MIME_APPLICATION_XSL = "application/vnd.ms-excel";
	String MIME_APPLICATION_WORD = "application/msword";
	String MIME_TEXT_PLAIN = "text/plain";
	String MIME_TEXT_HTML = "text/html";
	String MIME_APPLICATION_ZIP = "application/x-zip-compressed";

	String SUBJECT_ERRORE_CREAZIONE_PROPOSTA_CERTIFICAZIONE = "Errore creazione proposta certificazione";
	
	String SUBJECT_ERRORE_AVVIO_PROCEDURA_AGGIORNAMENTO_INT_FIN = "Errore nell'avvio della procedura di aggiornamento della proposta di certificazione intermedia finale.";
	String SUBJECT_SUCCESSO_AVVIO_PROCEDURA_AGGIORNAMENTO_INT_FIN = "L'aggiornamento della proposta di certificazione intermedia finale � avvenuto con successo.";

	String LINEA_DI_INTERVENTO_RADICE_POR_FESR = "POR-FESR";
	String LINEA_DI_INTERVENTO_RADICE_PAR_FAS = "PAR-FAS";

	String TIPOLOGIA_CONTO_ECONOMICO_MAIN = "MAIN";
	String TIPOLOGIA_CONTO_ECONOMICO_MASTER = "MASTER";
	String TIPOLOGIA_CONTO_ECONOMICO_COPY_IST = "COPY_IST";
	String TIPOLOGIA_CONTO_ECONOMICO_COPY_BEN = "COPY_BEN";

	String STATO_CONTO_ECONOMICO_APPROVATO = "A";
	String STATO_CONTO_ECONOMICO_APPROVATO_IN_ISTRUTTORIA = "AI";
	String STATO_CONTO_ECONOMICO_DA_APPROVARE = "DA";
	String STATO_CONTO_ECONOMICO_IN_ISTRUTTORIA = "II";
	String STATO_CONTO_ECONOMICO_IN_PROPOSTA_PER_DOMANDA = "IPD";
	String STATO_CONTO_ECONOMICO_IN_PROPOSTA_PER_GESTIONE_OPERATIVA = "IPG";
	String STATO_CONTO_ECONOMICO_IN_RIMODULAZIONE = "IR";
	String STATO_CONTO_ECONOMICO_NUOVA_PROPOSTA_INVIATA = "NPI";
	String STATO_CONTO_ECONOMICO_NUOVA_PROPOSTA = "NP";
	String STATO_CONTO_ECONOMICO_NUOVA_RIMODULAZIONE = "NR";
	String STATO_CONTO_ECONOMICO_NUOVA_RIMODULAZIONE_CONCLUSA = "NRC";
	String STATO_CONTO_ECONOMICO_RICHIESTO = "R";

	String STATO_PROGETTO_CHIUSO = "C";
	String STATO_PROGETTO_RINUNCIA_BENEFICIARIO = "R";
	String STATO_PROGETTO_CHIUSO_DI_UFFICIO = "CU";

	String STATO_CRONOPROGRAMMA_AVVIO = "AVVIO";
	String STATO_CRONOPROGRAMMA_GESTIONE = "GESTIONE";
	String STATO_CRONOPROGRAMMA_RETTIFICA = "RETTIFICA";

	String STATO_INDICATORI_AVVIO = "AVVIO";
	String STATO_INDICATORI_GESTIONE = "GESTIONE";
	String STATO_INDICATORI_RETTIFICA = "RETTIFICA";

	String DESCRIZIONE_BREVE_ENTE_FINPIEMONTE = "FIN";

	String DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_BEN_MASTER = "BEN-MASTER";
	String DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_BENEFICIARIO = "BENEFICIARIO";
	String DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_PERSONA_FISICA = "PERSONA-FISICA";
	String DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_PERSONA_GIURIDICA = "ENTE-GIURIDICO";
	String DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_ISTRUTTORE_AFFIDAMENTO = "ISTR-AFFIDAMENTI";
	String DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_OPERATORE_ISTRUTTORIA_PROGETTI = "OPE-ISTR-PROG";
	
	Long   ID_TIPO_ANAGRAFICA_PERSONA_FISICA = new Long(16);

	String DESCRIZIONE_BREVE_TIPO_SOGG_CORRELATO_RAPPRESENTANTE_LEGALE = "Rappr. Leg.";
	String DESCRIZIONE_BREVE_TIPO_SOGG_CORRELATO_INTERMEDIARIO = "Intermediario";
	String DESCRIZIONE_BREVE_TIPO_SOGG_CORRELATO_ND = "N.D.";
	
	Long   ID_TIPO_SOGG_CORRELATO_RAPPRESENTANTE_LEGALE = new Long(1);
	Long   ID_TIPO_SOGG_CORRELATO_DELEGATO_FIRMA = new Long(21);	

	String DESCRIZIONE_BREVE_TIPO_SOGG_PERSONA_FISICA = "PF";
	String DESCRIZIONE_BREVE_TIPO_SOGG_PERSONA_GIURIDICA = "EG";

	Long   ID_TIPO_SOGG_PERSONA_FISICA = new Long(1);
	Long   ID_TIPO_SOGG_PERSONA_GIURIDICA = new Long(2);
	
	String DESCRIZIONE_BREVE_TIPO_BENEFICIARIO_BEN_SINGOLO = "BEN-SINGOLO";
	String DESCRIZIONE_BREVE_TIPO_BENEFICIARIO_BEN_ASSOCIATO = "BEN-ASSOCIATO";

	String DESCRIZIONE_BREVE_STATO_VALIDAZIONE_SPESA_DICHIARATO = "R";
	String DESCRIZIONE_BREVE_STATO_VALIDAZIONE_SPESA_VALIDATO = "V";
	String DESCRIZIONE_BREVE_STATO_VALIDAZIONE_SPESA_NON_VALIDATO = "N";
	String DESCRIZIONE_BREVE_STATO_VALIDAZIONE_SPESA_DA_RESPINGERE = "G";
	String DESCRIZIONE_BREVE_STATO_VALIDAZIONE_SPESA_PARZIALMENTE_VALIDATO = "P";
	String DESCRIZIONE_BREVE_STATO_VALIDAZIONE_SPESA_SOSPESO = "S";
	String DESCRIZIONE_BREVE_STATO_VALIDAZIONE_SPESA_INSERITO = "I";
	String DESCRIZIONE_BREVE_STATO_VALIDAZIONE_SPESA_RESPINTO = "T";

	String DESCRIZIONE_BREVE_STATO_LIQUIDAZIONE_EMESSO = "1";
	String DESC_TIPO_LINEA_NORMATIVA = "Normativa";
	String DESC_TIPO_LINEA_ASSE = "Asse";
	String DESC_TIPO_LINEA_MISURA = "Misura";
	String DESC_TIPO_LINEA_LINEA = "Linea";

	String CODICE_TIPO_ACCESSO_CERTIFICATO = "8";

	String CODICE_RUOLO_ENTE_DESTINATARIO = "DESTINATARIO";
	
	String DESC_BREVE_TIPO_ENTE_COMPETENZA_DIREZIONE_REGIONALE = "ADG";

	String[] SPRING_XML_CONFIG_RESOURCES = new String[] { "classpath*:META-INF/beanContext.xml" };

	String NAZIONE_ITALIA = "ITALIA";
	Long   ID_NAZIONE_ITALIA = new Long(118);
	
	String COD_IGRUE_2007PI002FA011 = "2007PI002FA011";
	String RUOLO_ENTE_COMPETENZA_ATTUATORE = "ATTUATORE";
	String RUOLO_ENTE_COMPETENZA_ATTUATORENEW = "ATTUATORENEW";
	String NOME_FILE_RINUNCIA_PREFIX = "ComunicazioneDiRinuncia";

	Long CUP_SCHEDA_PROGETTO_LUNGHEZZA_CAMPO = 15L; // NTH verifica diretta
													// della dimensione del
													// campo sul db

	String DESCRIZIONE_BREVE_TIPO_OPERAZIONE_OPERE_PUBBLICHE = "01";
	String DESCRIZIONE_BREVE_TIPO_OPERAZIONE_BENI_E_SERVIZI = "02";

	/*
	 * COSTANTI TIPO SEDE
	 */
	String TIPO_SEDE_INTERVENTO = "SI";
	String TIPO_SEDE_LEGALE = "LE";

	String COD_RICHIESTA_ANTICIPAZIONE = "RA";
	String COD_RICHIESTA_II_ACCONTO = "R2A";
	String COD_RICHIESTA_ULTERIORE_ACCONTO = "RUA";
	String COD_RICHIESTA_SALDO = "SLD";

	/*
	 * COSTANTI TIPO DI TEMPLATE
	 */
	String TIPO_TEMPLATE_MASTER = "MASTER";
	String TIPO_TEMPLATE_SUB_REPORT = "SUB";

	/*
	 * MODALITA AGEVOLAZIONE
	 */

	String DESCRIZIONE_BREVE_MODALITA_AGEOLAZIONE_CONTRIBUTO = "Contributo";

	public static final Map<String, String> MAP_SIMON_TIPO_OPERAZIONE = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				{
					put("descrizioneDettaglioCup.lavoriPubblici", "1");
					put("descrizioneDettaglioCup.realizzAcquistoServiziFormazione",
							"2");
					put("descrizioneDettaglioCup.concessioneIncentiviUnitaProduttive",
							"3");
					put("descrizioneDettaglioCup.realizzAcquistoServiziNoFormazioneRicerca",
							"2");
					put("descrizioneDettaglioCup.realizzAquistoServiziRicerca",
							"2");
					put("descrizioneDettaglioCup.concessioneContributiNoUnitaProduttive",
							"3");
					put("descrizioneDettaglioCup.acquistoBeni", "2");
				}
			});

	public static final Map<String, String> MAP_DETTAGLIO_CUP_STATO_PROGRAMMA = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				{
					put("ATTIVO", FLAG_TRUE);
					put("REVOCATO", FLAG_FALSE);
					put("CHIUSO", FLAG_FALSE);
					put("CANCELLATO", FLAG_FALSE);
				}
			});

	/*
	 * CODICI ERRORE CONSULTAZIONE ATTO DI BILANCIO
	 */

	int CONSULTAZIONE_ATTO_BILANCIO_CODICE_PROBLEMA_TECNICO = -3;
	int CONSULTAZIONE_ATTO_BILANCIO_CODICE_NESSUN_ERRORE = 0;
	int CONSULTAZIONE_ATTO_BILANCIO_CODICE_ANNO_ATTO_NON_VALIDO = 1;
	int CONSULTAZIONE_ATTO_BILANCIO_CODICE_NUMERO_ATTO_NON_VALIDO = 2;
	int CONSULTAZIONE_ATTO_BILANCIO_CODICE_DIREZIONE_NON_VALIDA = 3;
	int CONSULTAZIONE_ATTO_BILANCIO_CODICE_ATTO_INESISTENTE = 4;
	int CONSULTAZIONE_ATTO_BILANCIO_CODICE_ERRORE_ORACLE = 90;

	/*
	 * CODICI TABELLE DI APPOGGIO PER IL BILANCIO
	 */

	String TIPO_RECORD_DT = "DT";
	String TIPO_RECORD_DM = "DM";
	String TIPO_RECORD_DL = "DL";

	/*
	 * SOGGETTI FINANZIATORI
	 */
	String COD_SOGGETTO_FINANZIATORE_PRIVATO = "Privato";
	String COD_SOGGETTO_FINANZIATORE_PRIVATO_FESR = "Privato FESR";

	/*
	 * XLS TEMPLATES
	 */
	String TEMPLATE_PROPOSTA_CERTIFICAZIONE_POR_FESR = "reportPropostaCertificazionePorFesr";
	String TEMPLATE_PROPOSTA_CERTIFICAZIONE_PAR_FAS = "reportPropostaCertificazioneParFas";
	String TEMPLATE_PROPOSTA_CERTIFICAZIONE_POR_FESR2014_20 = "nReportPropostaCertificazionePorFesr2014_20";
	String TEMPLATE_PROPOSTA_CERTIFICAZIONE_POR_FESR_PERCETTORI = "nReportPropostaCertificazionePorFesrPercettori";
	String TEMPLATE_REPORT_PROGETTI_CAMPIONI = "reportProgettiCampionati";
	String TEMPLATE_REPORT_CAMPIONI_POR_FSE = 			"CampionamentoPOR-FSE";
	String TEMPLATE_REPORT_CAMPIONI_PAR_FAS = 			"CampionamentoPAR-FAS";
	String TEMPLATE_REPORT_CAMPIONI_POR_FESR2014_20 = 	"CampionamentoPOR-FESR-2014-2020";
	String TEMPLATE_REPORT_CAMPIONI_FOGLIO_PROGETTI = "CampionamentoFoglioProgetto";
	String TEMPLATE_REPORT_CAMPIONI_FOGLIO_OPERAZIONI = "CampionamentoFoglioOperazioni";
	String TEMPLATE_REPORT_CHIUSURA_CONTI = "nReportChiusuraConti";
	
	// CDU-14 V04
	/*
	 * Valori ammessi nel campo ATTRIBUTO della tabella PBANDI_W_COSTANTI.
	 */
	String PBANDI_W_COSTANTI_ATTRIBUTO_CODICE_PROGETTO_NORMATIVA = "CODICE_PROGETTO_NORMATIVA";
	String PBANDI_W_COSTANTI_ATTRIBUTO_PROGR_BANDO_LINEA_INTERVENTO = "PROGR_BANDO_LINEA_INTERVENTO";
	String PBANDI_W_COSTANTI_ATTRIBUTO_STATO_DOMANDA = "STATO_DOMANDA";
	String PBANDI_W_COSTANTI_ATTRIBUTO_STATO_INVIO_DOMANDA = "STATO_INVIO_DOMANDA";
	String PBANDI_W_COSTANTI_ATTRIBUTO_STATO_PROGETTO = "STATO_PROGETTO";
	String PBANDI_W_COSTANTI_ATTRIBUTO_TIPO_OPERAZIONE = "TIPO_OPERAZIONE";
	String PBANDI_W_COSTANTI_ATTRIBUTO_STATO_CONTO_ECONOMICO = "STATO_CONTO_ECONOMICO";
	// CDU-14 V04 fine
	
	// WEB SERVICE CONTABILIA
	String CONTABILIA_CODICE_ENTE = "REGP";
	String CONTABILIA_CODICE_FRUITORE = "PBANDI";
	//String CONTABILIA_COD_TIPO_STRUTTURA_CDR = "CDR";
	String CONTABILIA_STATO_VALIDO = "VALIDO";
	String CONTABILIA_COD_PERSONA_GIURIDICA_NO_IVA = "PG";
	String CONTABILIA_COD_PERSONA_GIURIDICA_IVA = "PGI";
	String CONTABILIA_CODICE_TIPO_DOCUMENTO = "DOCUMENTO_SPESA";
	String CONTABILIA_CODICE_TIPO_PROVVEDIMENTO_ALG = "ALG";
	String CONTABILIA_ESITO_SUCCESSO = "SUCCESSO";
	String CONTABILIA_ESITO_FALLIMENTO = "FALLIMENTO";
	String CONTABILIA_CODICE_STATO_INCOMPLETO = "I";
	String CONTABILIA_STATO_ELAB_AVVIATA = "AVVIATA";
	String CONTABILIA_STATO_ELAB_CONCLUSA = "CONCLUSA";
	String CONTABILIA_STATO_ELAB_ERRORE = "ERRORE";
	String CONTABILIA_TIPO_DOC_PBANDI = "PBANDI";
	String CONTABILIA_TIPO_DOC_ALG = "ALG";
	
	//Parola chiave acta
	String FEEDBACK_ACTA_VERIFICATA = "VERIFICATA";
	String FEEDBACK_ACTA_NON_VERIFICATA = "NON_VERIFICATA";
	
	// Tipi di programmazione.
	String PROGRAMMAZIONE_PRE_2016 = "PRE_2016";
	String PROGRAMMAZIONE_2016 = "2016";
	
	String LINEA_DI_INTERVENTO_RADICE_POR_FESR_2014_20 = "POR-FESR-2014-2020";
	String COD_TIPO_CAUSALE_DISIMPEGNO_REVOCA = "REV";
	String FLAG_FALSE = "N";
	String FLAG_TRUE = "S";
	
	int ID_PROCESSO_CULTURA = 3;
	int ID_PROCESSO_2014_20 = 2;
	BigDecimal ID_PROCESSO_VECCHIA_PROGRAMMAZIONE = new BigDecimal(1);
	
	//***** Id Linea Di Intervento *********//
	BigDecimal ID_LINEA_DI_INTERVENTO_ASSE_I = new BigDecimal(203);
	BigDecimal ID_LINEA_DI_INTERVENTO_ASSE_II = new BigDecimal(236);
	BigDecimal ID_LINEA_DI_INTERVENTO_ASSE_III = new BigDecimal(204);
	BigDecimal ID_LINEA_DI_INTERVENTO_ASSE_IV = new BigDecimal(226);
	BigDecimal ID_LINEA_DI_INTERVENTO_ASSE_V = new BigDecimal(242);
	BigDecimal ID_LINEA_DI_INTERVENTO_ASSE_VI = new BigDecimal(247);
	BigDecimal ID_LINEA_DI_INTERVENTO_ASSE_VII = new BigDecimal(254);
	
	String ID_STATO_ATTO_BOZZA = "1";
	String ID_STATO_ATTO_EMESSO = "2";
	String ID_STATO_ATTO_PRESO_IN_CARICO = "3";
	String ID_STATO_ATTO_ANNULLATO = "4";
	String ID_STATO_ATTO_PARZ_QUIETANZATO = "5";
	String ID_STATO_ATTO_TOTAL_QUIETANZATO = "6";
	String ID_STATO_ATTO_COMPLETATO = "7";
	String ID_STATO_ATTO_RICHIESTA_MOD = "8";
	String ID_STATO_ATTO_RIFIUTATO = "9";
	String ID_STATO_ATTO_PARZ_CONVALIDATO = "10";
	String ID_STATO_ATTO_IN_LAVORAZIONE = "11";
	String ID_STATO_ATTO_BLOCCATO = "12";
	
	String DESC_BREVE_STATO_ATTO_BOZZA = "0";
	String DESC_BREVE_STATO_ATTO_EMESSO = "1";
	String DESC_BREVE_STATO_ATTO_PRESO_IN_CARICO = "2";
	String DESC_BREVE_STATO_ATTO_ANNULLATO = "3";
	String DESC_BREVE_STATO_ATTO_PARZ_QUIETANZATO = "4";
	String DESC_BREVE_STATO_ATTO_TOTAL_QUIETANZATO = "5";
	String DESC_BREVE_STATO_ATTO_COMPLETATO = "6";
	String DESC_BREVE_STATO_ATTO_RICHIESTA_MOD = "7";
	String DESC_BREVE_STATO_ATTO_RIFIUTATO = "8";
	String DESC_BREVE_STATO_ATTO_PARZ_CONVALIDATO = "9";
	String DESC_BREVE_STATO_ATTO_IN_LAVORAZIONE = "10";
	String DESC_BREVE_STATO_ATTO_BLOCCATO = "11";
	
	// TIPO INVIO DICHIARAZIONE DI SPESA
	String TIPO_INVIO_DS_CARTACEO = "C";
	String TIPO_INVIO_DS_ELETTRONICO = "E";
	
	// TIPO_DOCUMENTO_INDEX
	String TIPO_DOC_INDEX_DS = "DS";
	String TIPO_DOC_INDEX_CFP = "CFP";
	Long ID_TIPO_DOCUMENTO_INDEX_DICHIARAZIONE_AFFIDAMENTO = 26L;
	
	Long ID_TIPO_DICHIARAZIONE_INTEGRATIVA = 4L;
	
	int ID_STATO_AFFIDAMENTO_DAINVIARE = 1;
	int ID_STATO_AFFIDAMENTO_INVERIFICA = 2;
	int ID_STATO_AFFIDAMENTO_VPARZIALE = 3;
	int ID_STATO_AFFIDAMENTO_VDEFINITIVA = 4;
	int ID_STATO_AFFIDAMENTO_RICINTEGRAZ = 5;

	
	Long ID_ENTITA_PBANDI_T_APPALTO = 262L;
	String NOME_ENTITA_PBANDI_T_INTEGRAZIONE_SPESA = "PBANDI_T_INTEGRAZIONE_SPESA";
	
	String COSTANTE_INDIRIZZO_EMAIL_MITTENTE = "conf.serverConfiguration.indirizzoEmailMittente";
    String COSTANTE_INDIRIZZO_EMAIL_AFFIDAMENTI = "conf.serverConfiguration.indirizzoEmailAffidamenti";
    
    Long ID_ESITO_CHECKLIST_AFFIDAMENTO_PARZIALE = 1L;			// PBANDI_T_ESITI_NOTE_AFFIDAMENT.FASE
    Long ID_ESITO_CHECKLIST_AFFIDAMENTO_DEFINITIVO = 2L;		// PBANDI_T_ESITI_NOTE_AFFIDAMENT.FASE
    String ESITO_CHECKLIST_AFFIDAMENTO_POSITIVO = "POSITIVO";
    
    
    BigDecimal ID_LINEA_DI_INTERVENTO_POR_FESR_14_20 = new BigDecimal(202);
    BigDecimal ID_PERIODO_POR_FESR_14_20 = new BigDecimal(23);
    BigDecimal ID_PERIODO_VECCHIA_PROGRAMMAZIONE = new BigDecimal(11);
    
	String TIPOLOGIA_PROGETTO_CEDENTE = "C";
	String TIPOLOGIA_PROGETTO_RICEVENTE = "R";
	
	String NOME_BATCH_AGGIORNA_DATI_INTERMEDIA = "PBAN-PROP-CERT";
	BigDecimal TIPO_PERIODO_ANNO_CONTABILE = new BigDecimal(3);
	BigDecimal ID_TIPO_DOCUMENTO_INDEX_CHECKLIST_VALIDAZIONE = new BigDecimal(2);
	
	String DESC_BREVE_TIPO_SEDE_INTERVENTO = "SI";
	
	// Jira PBANDI-2829.
	String COD_TIPO_CHEKCLIST_IN_LOCO = "CLIL";
	String COD_TIPO_CHEKCLIST_DOCUMENTALE = "CL";
	
	// Jira Pbandi-2859
	public static final String COD_TIPO_CHEKCLIST_AFFIDAMENTO_IN_LOCO = "CLILA";
	public static final String COD_TIPO_CHEKCLIST_AFFIDAMENTO_DOCUMENTALE = "CLA";
	
	public static final String DESC_BREVE_STATO_PROPOSTA_CERTIF_APPROVATA = "07appr";
	
} 
