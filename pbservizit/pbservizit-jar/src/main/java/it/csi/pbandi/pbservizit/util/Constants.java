/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.util;

public class Constants {
	public final static String COMPONENT_NAME = "pbservizit";

	public static final String USERINFO_SESSIONATTR = "currentUserInfo";
//	public static final String KEY_UTENTE_INCARICATO = "utenteIncaricato";

	public static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final String YEAR_FORMAT = "yyyy";
	public static final String TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
	public static final String ORA_FORMAT = "HH:mm:ss";

	public static final int VALORE_TIPO_SOGG_PERSONA_FISICA = 1;

//	MESSAGE CODES
//	public static final Long MESSAGE_CODE_REQUISITI_OK = new Long(1);
//	public static final Long MESSAGE_CODE_REQUISITI_DA_RIPIANIFICARE = new Long(2);
//	public static final Long MESSAGE_CODE_REQUISITI_DA_INTEGRARE = new Long(5);
//	public static final Long MESSAGE_CODE_REQUISITI_DOMANDA_KO = new Long(3);
//	public static final Long MESSAGE_CODE_REQUISITI_OK_TOO_EARLY = new Long(6);
//	public static final Long MESSAGE_CODE_REQUISITI_OK_TOO_LATE = new Long(7);
//	public static final Long MESSAGE_CODE_IDENTIFICATIVO_AZIENDA_NON_VALIDO = new Long(4);
//	public static final Long MESSAGE_CODE_EMAIL_NON_VALIDO = new Long(4);
//	public static final Long MESSAGE_CODE_CHIAVE_DUPLICATA = new Long(4);
//	public static final String TOKEN_DATA_DA = "{data_da}";
//	public static final String TOKEN_DATA_A = "{data_a}";
//	public static final String NO_PEC = new String("NO_PEC");
//	public static final String PEC = new String("PEC");
//	public static final Object MESSAGE_CODE_REQUISITI_MAIL_INVIATA_OK = new Long(8);
//	public static final String SERVER_MAIL = new String ("mailfarm-app.csi.it");
//	public static final String FROM_EMAIL = new String ("no-reply@csi.it");
//	public static final String FROM_NAME = new String ("Bonus Piemonte");
//	public static final String OGGETTO_MAIL = new String ("Informazioni Bonus Piemonte");
//
//	public static final Long MESSAGE_CODE_REQUISITI_KO_FASEI = new Long(9);

	// OPERAZIONI
	// per creare/aggiungere queste costanti, verificare la presenza di esse nelle
	// costanti sul frontend
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
	public static final Long ID_OPERAZIONE_CONFIGURAZIONE_BANDO = 13L;
	public static final Long ID_OPERAZIONE_ASSOCIAZIONE_PERMESSO_RUOLO = 14L;
	public static final Long ID_OPERAZIONE_ASSOCIAZIONE_RUOLO_PERMESSO = 15L;
	public static final Long ID_OPERAZIONE_GESTIONE_TEMPLATES = 16L;
	public static final Long ID_OPERAZIONE_GESTIONE_UTENTI = 17L;
	public static final Long ID_OPERAZIONE_VERIFICA_SERVIZI = 18L;
	public static final Long ID_OPERAZIONE_CONSOLE_APPLICATIVA = 19L;
	public static final Long ID_OPERAZIONE_CAMBIA_BENEFICIARIO = 30L;
	public static final Long ID_OPERAZIONE_GESTIONE_ASSOCIAZIONE_ISTRUTTORE_PROGETTI = 31L;
	public static final Long ID_OPERAZIONE_GESTIONE_ANAGRAFICA = 44L;
	public static final Long ID_OPERAZIONE_MONITORAGGIO_CREDITI = 64L;
	public static final Long ID_OPERAZIONE_GESTIONE_RICHIESTE = 65L;
	public static final Long ID_OPERAZIONE_GESTIONE_NEWS = 66L;
	public static final Long ID_OPERAZIONE_PROPOSTE_VARIAZIONI_STATO_CREDITI = 69L;
	public static final Long ID_OPERAZIONE_CAMPIONAMENTO = 70L;
	public static final Long ID_OPERAZIONE_GESTIONE_GARANZIE = 73L;
	public static final Long ID_OPERAZIONE_AMBITO_EROGAZIONI = 74L;
	public static final Long ID_OPERAZIONE_GESTIONE_REVOCHE = 78L;
	public static final Long ID_OPERAZIONE_AREA_CONTROLLI_LOCO = 82L;
	public static final Long ID_OPERAZIONE_ITER_AUTORIZZATIVI = 86L;
	public static final Long ID_OPERAZIONE_BO_STORAGE = 88L;
	public static final Long ID_OPERAZIONE_GESTIONE_RIASSICURAZIONI = 97L;

	public static final String PATH_FILE_JASPER = "report/";
	public static final String PATH_FILE_SQL = "sql/pbandisrv/";

	public static final String COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO = "CLIL";
	public static final String COD_TIPO_DOCUMENTO_INDEX_ARCHIVIO_FILE = "AF";

	// Cronoprogramma
	public static final String PROGRAMMAZIONE_PRE_2016 = "PRE_2016";
	public static final String PROGRAMMAZIONE_2016 = "2016";
	public static final String MSG_SALVA_SUCCESSO = "Salvataggio avvento con successo.";
	public static final String CRONOPROG_AVVIO = "CRONOPROG-AVVIO";

	public static final String DESC_BREVE_STATO_DOCUMENTO_INDEX_ACQUISITO = "ACQUISITO";
	public static final String DESC_BREVE_STATO_DOCUMENTO_INDEX_VALIDATO = "VALIDATO";
	public static final String DESC_BREVE_STATO_DOCUMENTO_INDEX_NON_VALIDATO = "NON-VALIDATO";
	public static final String DESC_BREVE_STATO_DOCUMENTO_INDEX_INVIATO = "INVIATO";

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

	public static final String CONTENT_DISPOSITION = "Content-Disposition";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String FILE_NAME_KEY = "filename";

	// indicatori
	public static final String ROOT_FILE_SYSTEM = "/pbstorage_online";

	public static final String N_A = "N/A";
	public static final int MAX_SCALE_ALTRI_INDICATORI = 5;
	public static final int MAX_SCALE_INDICATORI_MONIT = 2;

	public static final String RUOLO_BEN_MASTER = "BEN-MASTER";
	public static final String RUOLO_PERSONA_FISICA = "PERSONA-FISICA";
	public static final int MAX_CIFRE_INTERE = 11;
	public static final String FLAG_TRUE = "S";
	public static final String FLAG_FALSE = "N";

	public static final String ENTITA_PBANDI_R_PROGETTO_ITER = "PBANDI_R_PROGETTO_ITER";
}
