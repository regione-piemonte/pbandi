/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandiutil.commonweb;

//TODO rifattorizzare in MiniAppConstants
public class Constants {

	/* chiavi di sessione */
	public static final String SESSION_KEY_CURRENT_ACTIVITY = "pbandi::CURRENT_ACTIVITY";

	public static final String SESSION_PBANDIUTIL_SCOPE = "pbandiutil::";

	public static final String SESSION_KEY_MINIAPP_PARAMS = SESSION_PBANDIUTIL_SCOPE
			+ "MINIAPP_PARAMS";

	public static final String SESSION_KEY_HAS_PERMESSO_CACHE = SESSION_PBANDIUTIL_SCOPE
			+ "HAS_PERMESSO_CACHE";

	public final static String SESSION_KEY_IDENTITA_IRIDE = "iride2_id";

	public final static String SESSION_KEY_APPDATA_CURRENTUSER = "appDatacurrentUser";
	
	public final static String SESSION_KEY_APPDATA_WEBMODULE = "appData"+SESSION_PBANDIUTIL_SCOPE+"webModule";

	/*
	 * Chiave dell'utente delegato in sessione applicativa della miniapp
	 */
	public static final String KEY_UTENTE_INCARICATO = SESSION_PBANDIUTIL_SCOPE
			+ "utenteIncaricato";

	/*
	 * Chiave del content panel corrente
	 */
	public static final String KEY_CURRENT_CONTENT_PANEL = SESSION_PBANDIUTIL_SCOPE
			+ "CURRENT_CONTENT_PANEL";

	/*
	 * Chiave per verificare se inizializzare il panel corrente.
	 */
	public static final String KEY_INIT_CONTENT_PANEL = SESSION_PBANDIUTIL_SCOPE
			+ "INIT_CONTENT_PANEL";

	public static final String KEY_REDIRECT_APP_URL = SESSION_PBANDIUTIL_SCOPE
			+ "REDIRECT_APPL_URL";

	public static final String KEY_REDIRECT_APP_PARAMS = SESSION_PBANDIUTIL_SCOPE
			+ "REDIRECT_APP_PARAMS";

	/*
	 * Chiave del content panel precedente
	 */
	public static final String KEY_PREVIOUS_CONTENT_PANEL = SESSION_PBANDIUTIL_SCOPE
			+ "PREVIOUS_CONTENT_PANEL";

	/*
	 * Chiave per segnalare l'attivita' di FLUX non disponibile
	 */
	public static final String KEY_FLUX_ACTIVITY_NOT_AVAILABLE = SESSION_PBANDIUTIL_SCOPE
			+ "FLUX_ACTIVITY_NOT_AVAILABLE";

	/*
	 * Parametri passati alle miniApp
	 */
	public static final String PARAM_CODICE_RUOLO = "role";

	public static final String PARAM_IDENTITA_IRIDE = "ident";

	public static final String PARAM_ID_SOGGETTO = "idSg";

	public static final String PARAM_ID_SOGGETTO_BENEFICIARIO = "idSgBen";

	public static final String PARAM_ID_UTENTE = "idU";

	public static final String PARAM_FLAG_INCARICATO = "fInc";

	public static final String AUTH_ID_MARKER = "Shib-Iride-IdentitaDigitale";

	public static final String IRIDE_ID_SESSIONATTR = "iride2_id";

	public static final String PARAM_COD_CAUSALE_EROGAZIONE = "codCEr";

	public static final String PARAM_ID_PROGETTO = "idPrj";

	public static final String PARAM_PROCESS_IDENTITY = "processIdentity";

	public static final String PARAM_PROCESS_COMMAND = "processCommand";

	public static final String PARAM_TASK_IDENTITY = "taskIdentity";
	
	public static final String PARAM_TASK_NAME= "taskName";

	public static final String PARAM_WORKFLOW_ACTION = "wkfAction";

	public static final String PARAM_USER_CREDENTIALS = "userCredentials";

	public static final String PARAM_IS_NEOFLUX = "isNeoflux";
	
	public static final String PARAM_ID_DICHIARAZIONE = "idDich";
	public static final String PARAM_ID_NOTIFICA= "idNotifica";
	public static final String PARAM_WEB_MODULE = "wMod";
	
	public static final String PARAM_ID_SOGGETTO_BENEFICIARIO_DOC_PROG = "idSgBenDocumProgetto";	// Jira PBANDI-2867.
	public static final String PARAM_ID_PROGETTO_DOC_PROG = "idPrjDocumProgetto";					// Jira PBANDI-2867.

	public static final String KEY_WORKSPACE_URL = "workspace.url";
	public static final String KEY_ATTIVITA_URL = "attivita.url";
	public static final String KEY_ARCHIVIO_FILE_URL = "archiviofile.url";
	public static final String KEY_MINIAPP_HOME_ACTION = "miniapp.home.action";
	public static final String KEY_HELP_URL = "infogest.url";
	public static final String WEB_MODULE_BILANCIO = "BIL";
	public static final String WEB_MODULE_RENDICONTAZIONE_MASSIVA = "RENMAS";
	public static final String WEB_MODULE_ARCHIVIO_FILE = "ARCHFILE";
	public static final String WEB_MODULE_GESTIONE_ECONOMIE = "GESTECONOMIE";
	public static final String WEB_MODULE_RILEVAZIONE_E_CONTROLLI = "RILEVAZIONECONTROLLI";


	public static final String CSS_ATTIVITAPREGRESSE_DETTAGLIO = "attivitapregresseDettaglio";
	public static final String CSS_ATTIVITAPREGRESSE_DATA = "attivitapregresseData";
	public static final String CSS_ATTIVITAPREGRESSE_TR_DETTAGLIO = "rowDettaglio";
	public static final String CSS_ATTIVITAPREGRESSE_DOWNLOAD = "doc";
	public static final String ID_ATTIVITAPREGRESSE_TR_TESTATA = "idRowTestata";
	
	
	public static final String COD_ATTIVITA_PREGRESSA_PROPOSTA_CERTIFICAZIONE = "T";
	
	public static final  String IMAGE_ERROR = "<img src=\"/ris/css/finanziamenti/pbandi/im/error.gif\"/>";	
	
	/**
	 * TIPI PERIODO
	 */
	public static final Long TIPO_PERIODO_RANGE = new Long(2);
	public static final Long ID_PERIODO_NUOVA_PROGRAMMAZIONE = new Long(23);
	public static final Long ID_PERIODO_VECCHIA_PROGRAMMAZIONE = new Long(11);

}
