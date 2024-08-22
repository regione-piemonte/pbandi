/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE OR REPLACE PACKAGE pck_pbandi_processo AS

  -- Globale
  g_id_processo             PBANDI_T_PROCESSO.ID_PROCESSO%TYPE;
  g_riga_progetti           PLS_INTEGER;
  g_num_progetti_ben_bl     PLS_INTEGER;


   -- Calcola l'id del task a fronte della DESC_BREVE
   CURSOR g1 (c_id_processo PBANDI_T_PROCESSO.ID_PROCESSO%TYPE,
              c_descr_task PBANDI_D_TASK.descr_task%TYPE) IS
      SELECT a.id_task
        FROM PBANDI_D_TASK a
       WHERE a.descr_breve_task = c_descr_task;

    TYPE r_lista_task IS RECORD
    (ID_PROGETTO NUMBER(8),
     TITOLO_PROGETTO  VARCHAR2(2000),
     CODICE_VISUALIZZATO  VARCHAR2(100),
     DESCR_BREVE_TASK  VARCHAR2(30),
     DESCR_TASK  VARCHAR2(2000),
     PROGR_BANDO_LINEA_INTERVENTO  NUMBER(8),
     NOME_BANDO_LINEA VARCHAR2(255),
     ACRONIMO_PROGETTO VARCHAR2(20),
     ID_BUSINESS NUMBER,
     FLAG_OPT VARCHAR2(1),
     FLAG_LOCK VARCHAR2(1),
     ID_NOTIFICA NUMBER
     );

     type task_tab_type is table of r_lista_task
      index by BINARY_INTEGER;

    TYPE r_lista_notifiche IS RECORD
    (ID_PROGETTO NUMBER(8),
     TITOLO_PROGETTO  VARCHAR2(2000),
     CODICE_VISUALIZZATO  VARCHAR2(100),
     ID_NOTIFICA  INTEGER,
     STATO_NOTIFICA  VARCHAR2(20),
     DT_NOTIFICA DATE,
     SUBJECT_NOTIFICA VARCHAR2(4000),
     --MESSAGE_NOTIFICA VARCHAR2(4000),
     MESSAGE_NOTIFICA CLOB,-- mc sviluppo 2019
     DESCR_BREVE_TEMPLATE_NOTIFICA VARCHAR2(100),
     PROGR_BANDO_LINEA_INTERVENTO NUMBER(8),
     NOME_BANDO_LINEA   VARCHAR2(255)
     );

     type notifiche_tab_type is table of r_lista_notifiche
      index by BINARY_INTEGER;


    TYPE metadata_notifiche_processo_ty IS RECORD
    (DESC_DICHIARAZIONE_DI_SPESA VARCHAR2(4000),
     DATA_CHIUSURA_VALIDAZIONE VARCHAR2(10),
     DATA_DICHIARAZIONE_DI_SPESA VARCHAR2(10),
     NUM_DICHIARAZIONE_DI_SPESA VARCHAR2(10),
     DATA_CHIUSURA_RIMODULAZIONE VARCHAR2(10),
     DATA_INVIO_PROPOSTA VARCHAR2(10),
     DATA_RETTIFICA_SPESA_VALIDATA VARCHAR2(10),
     DATA_REVOCA VARCHAR2(10),
     DATA_INVIO_RINUNCIA VARCHAR2(10),
     DATA_CHIUSURA_PROGETTO VARCHAR2(10),
     -- mc sviluppo ottobre 2018
     --NOTE_RESPINGI_AFFIDAMENTO VARCHAR2(4000),
     NOTE_RESPINGI_AFFIDAMENTO CLOB, -- 2019 mc
     IMP_AGGIUDICATO_AFFIDAMENTO  VARCHAR2(20),
     OGGETTO_AFFIDAMENTO VARCHAR2(2000),
     DATA_RESPINGI_AFFIDAMENTO  VARCHAR2(10),
      -- mc sviluppo ottobre 2018
     DATA_RIC_INTEGRAZ_AFFIDAMENTO VARCHAR2(10),
     --NOTE_RIC_INTEGRAZ_AFFIDAMENTO VARCHAR2(4000),
     NOTE_RIC_INTEGRAZ_AFFIDAMENTO CLOB,-- 2019 mc
     -- mc sviluppo febbraio 2019
     DATA_RICHIESTA_INTEGRAZ_DIC VARCHAR2(10),
     --NUM_DICHIARAZIONE_DI_SPESA VARCHAR2(10),
     --DATA_DICHIARAZIONE_DI_SPESA VARCHAR2(10)
     --NOTE_RICHIESTA_INTEGRAZ_DIC VARCHAR2(4000),
     NOTE_RICHIESTA_INTEGRAZ_DIC CLOB,-- 2019 mc
     DATA_INTEGRAZ_DIC  VARCHAR2(10),
   CODICE_VISUALIZZATO VARCHAR2(100),
   ID_PROGETTO VARCHAR2(10),
   DATA_INSERIMENTO_BLOCCO VARCHAR2(10),
   DES_CAUSALE_BLOCCO  CLOB,
   DATA_INSERIMENTO_SBLOCCO VARCHAR2(10),
   DATA_AVVIO_CONTROLLI  VARCHAR2(10),
   ID_CONTROLLO_LOCO  VARCHAR2(10),
   NUM_REVOCA  VARCHAR2(10),
   DATA_NOTIFICA VARCHAR2(10),
   DATA_ATTIVITA VARCHAR2(10)
     );

     v_metadata_notifiche_processo METADATA_NOTIFICHE_PROCESSO_TY;

     TYPE multi_id_ty IS TABLE OF NUMBER;

----------------------------------------------------------------------------------------------------------------------------------------
-- mc 12032024
FUNCTION GetAttivitaProgetto_Widget
                           (
                            p_id_utente                     IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE,         -- ID istruttore
                            p_descbrevewidget               IN PBANDI_D_WIDGET.DESC_BREVE_WIDGET%TYPE,  -- attività del widget
                            p_desc_breve_tipo_anagrafica    IN  PBANDI_D_TIPO_ANAGRAFICA.DESC_BREVE_TIPO_ANAGRAFICA%TYPE -- ruolo istruttore
                           ) RETURN LISTTASKPROGWIDBEN;
-----------------------------------------------------------------------------------------------------------------------------------------    


--======================================================================================================================================

    /***********************************************************************************************************************************
    getJobClearLockInterval : Ritorna il valore di intervallo di tempo trascorso dall'utente dall'inizio dell'attività (lock)
    al rilascio della stessa (unlock)      per l'esecuzione della procedura di pulizia dei lock
    ***********************************************************************************************************************************/
  FUNCTION getJobClearLockInterval RETURN VARCHAR2;

--======================================================================================================================================

    /***********************************************************************************************************************************
    ClearLock : Funzione di pulizia dei lock generati dagli utenti che non si scollegano in modo corretto dall'applicativo
    ***********************************************************************************************************************************/
  FUNCTION ClearLock RETURN NUMBER;

--======================================================================================================================================

    /***********************************************************************************************************************************
     PutNotificationMetadata : Assegna alla variabile globale di tipo record "v_metadata_notifiche_processo" il valore per il
                             metadato indicato nel parametro     "p_nome_metadata"
                             La variabile che, contiene i metadati,  sarà utilizzata poi nella funzione SendNotificationMessage
     Parametri input: p_nome_metadata   -- nome del metadato
                      p_valore_metadata -- valore
    ***********************************************************************************************************************************/

  --PROCEDURE PutNotificationMetadata (p_nome_metadata IN VARCHAR2, p_valore_metadata IN VARCHAR2);
  PROCEDURE PutNotificationMetadata (p_nome_metadata IN VARCHAR2, p_valore_metadata IN clob);
--======================================================================================================================================
    /***********************************************************************************************************************************
     SendNotificationMessage : Invia il messaggio di notifica agli utenti destinatari associati al ruolo per il progetto
     Ritorna 0=Positivo,
             1=Template non previsto
             2=Errore scrittura su tabella PBANDI_T_NOTIFICA_PROCESSO
     Parametri input: p_id_progetto                     -- id del progetto
                      p_descr_breve_template_notifica   -- nome del template
                      p_codice_ruolo                    -- Codice del ruolo del destinatario
                      p_id_utente                       -- p_id_utente    - id dell'utente

    ***********************************************************************************************************************************/

  FUNCTION SendNotificationMessage  (p_id_progetto                   IN  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                     p_descr_breve_template_notif    IN  PBANDI_D_TEMPLATE_NOTIFICA.DESCR_BREVE_TEMPLATE_NOTIFICA%TYPE,
                                     p_codice_ruolo                  IN  PBANDI_C_RUOLO_DI_PROCESSO.CODICE%TYPE,
                                     p_id_utente                       IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE,
                                     p_id_target                       IN  PBANDI_T_NOTIFICA_PROCESSO.ID_TARGET%TYPE) RETURN INTEGER;
--======================================================================================================================================
    /***********************************************************************************************************************************
     GetNotificationMessage : Ricava le notifiche per il progetto e per gli utenti del ruolo destinatario
    Parametri input: p_id_notifica  - id della notifica
                      Ritorna una struttura pl/sql con i dati della notifica con le seguenti informazioni:
                              - id_progetto
                              - titolo_progetto
                              - codice_visualizzato
                              - id notifica
                              - stato_notifica (I=Inviata,C=Cancellata, R=Letta)
                              - dt_notifica
                              - subject notifica
                              - message_notifica
     ***********************************************************************************************************************************/

  FUNCTION GetNotificationMessage (p_id_notifica  IN  PBANDI_T_NOTIFICA_PROCESSO.ID_NOTIFICA%TYPE) RETURN OBJNOTIFPROG;
--======================================================================================================================================


    /***********************************************************************************************************************************
     CancelNotificationMessage : Elimina logicamente la notifica
                       Ritorna error_code  (0=Positivo,1=Negativo)
     Parametri input: p_id_notifica  - id del Progetto
                      p_id_utente     - id dell'utente
    ***********************************************************************************************************************************/


  FUNCTION CancelNotificationMessage (p_id_notifica  IN  PBANDI_T_NOTIFICA_PROCESSO.ID_PROGETTO%TYPE,
                                      p_id_utente    IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE) RETURN INTEGER;

--======================================================================================================================================


    /***********************************************************************************************************************************
     GetProcesso : Ritorna l'id del Processo per il Progetto.
                   Se null significa che il progetto deve essere gestito con  il sevizio di FLUX
     Parametri input: p_id_progetto  - id del Progetto
    ***********************************************************************************************************************************/

  FUNCTION GetProcesso (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE) RETURN INTEGER;

--======================================================================================================================================
    /***********************************************************************************************************************************
     GetProcessoBL : Ritorna l'id del Processo per il Bando linea.
                   Se null significa che il progetto deve essere gestito con  il sevizio di FLUX
     Parametri input: p_progr_bando_linea_intervento  - progr. del bando linea
    ***********************************************************************************************************************************/

  FUNCTION GetProcessoBL (p_progr_bando_linea_intervento  IN  PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO%TYPE) RETURN INTEGER;

--======================================================================================================================================
  -- ********* PROCEDURA DI DEMO ****************************
  PROCEDURE GetAttivitaQuery (p_id_progetto                                      IN  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                              p_id_utente                                        IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE,
                              p_desc_breve_tipo_anagrafica                         IN  PBANDI_D_TIPO_ANAGRAFICA.DESC_BREVE_TIPO_ANAGRAFICA%TYPE,
                              p_filtro_attivita                                    IN  VARCHAR2 DEFAULT NULL,
                              p_tab_task                                         IN OUT task_tab_type);
--======================================================================================================================================
  -- ********* PROCEDURA DI DEMO ****************************
  PROCEDURE GetNotificheQuery (p_id_notifica    IN  PBANDI_T_NOTIFICA_PROCESSO.ID_NOTIFICA%TYPE,
                               p_tab_notifiche  IN OUT r_lista_notifiche);
--======================================================================================================================================
    /***********************************************************************************************************************************
     GetAttivita : Naviga gli step del processo per ricavare le attività disponibili per il progetto e per le abilitazioni dell'utente
                      Ritorna una struttura pl/sql con i dati delle attività (TASK) con le seguenti informazioni:
                              - id_progetto
                              - titolo_progetto
                              - codice_visualizzato
                              - acronimo_progetto
                              - descrizione breve del task
                              - descrizione del task
                              - progr. bando linea intervento
                              - nome bando linea
                              - flag opzionale (S=Task opzionale sempre disponibile)
                              - flag lock (S=Il task è lockato da un altro utente)
                              - acronimo progetto (informazione presente sul raggruppamento della domanda master)
                              - id business (per il task multi-istanziato identifica l'ID della tabella di business, per la notifica l'ID della notifica)
                              - id_notifica (Se valorizzato dati sono relativi alla notifica, NULL= I dati sono del Task)
    Parametri input:  p_id_progetto  - id del Progetto
                      p_id_utente    - id dell'utente
                      p_desc_breve_tipo_anagrafica - nome del tipo anagrafica
                      p_filtro_attivita - se valorizzato vengono filtrate le attività che iniziano con p_filtro_attivita
    ***********************************************************************************************************************************/


  FUNCTION GetAttivita (p_id_progetto                      IN  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                        p_id_utente                        IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE,
                        p_desc_breve_tipo_anagrafica    IN  PBANDI_D_TIPO_ANAGRAFICA.DESC_BREVE_TIPO_ANAGRAFICA%TYPE,
                        p_filtro_attivita                  IN  VARCHAR2 DEFAULT NULL) RETURN LISTTASKPROG;


--======================================================================================================================================
    /***********************************************************************************************************************************
     GetAttivitaBL : Naviga gli step del processo per ricavare le attività disponibili per tutti i progetti del Bando Linea e per le abilitazioni dell'utente
                      Ritorna una struttura pl/sql con i dati delle attività (TASK)
     Parametri input: p_progr_bando_linea_intervento  - Progr del Bando Linea
                      p_id_utente    - id dell'utente
                      p_desc_breve_tipo_anagrafica - nome del tipo anagrafica
                      p_start        - Riga del progetto per paginare
    ***********************************************************************************************************************************/

  FUNCTION GetAttivitaBL (p_progr_bando_linea_intervento  IN  PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO%TYPE,
                          p_id_utente    IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE,
                          p_desc_breve_tipo_anagrafica     IN  PBANDI_D_TIPO_ANAGRAFICA.DESC_BREVE_TIPO_ANAGRAFICA%TYPE,
                          p_start        IN  PLS_INTEGER DEFAULT 0) RETURN LISTTASKPROG;

--======================================================================================================================================
    /***********************************************************************************************************************************
     GetAttivitaBEN : Naviga gli step del processo per ricavare le attività disponibili per tutti i progetti del Beneficiario e per le abilitazioni dell'utente
                      Ritorna una struttura pl/sql con i dati delle attività (TASK) con le seguenti informazioni:
                              - id_progetto
                              - titolo_progetto
                              - codice_visualizzato
                              - acronimo_progetto
                              - descrizione breve del task
                              - descrizione del task
                              - progr. bando linea intervento
                              - nome bando linea
                              - flag opzionale (S=Task opzionale sempre disponibile)
                              - flag lock (S=Il task è lockato da un altro utente)
                              - acronimo progetto (informazione presente sul raggruppamento della domanda master)
                              - id business (per il task multi-istanziato identifica l'ID della tabella di business)
                              - id notifica ( se valorizzato allora è una notifica e non un task)

     Parametri input: p_progr_bando_linea_intervento      - Progr del Bando Linea
                      p_id_soggetto_ben                  - id del soggetto Beneficiario
                      p_id_utente                        - id dell'utente
                      p_desc_breve_tipo_anagrafica         - nome del tipo anagrafica
                      p_start                            - Riga del progetto per paginare
                      p_id_progetto                      - Opzionale Se NULL tutti i progetti
                      p_filtro_attivita                 - Opzionale Se valorizzato viene applicato un filtro sulla parte iniziale della descrizione del task
    ***********************************************************************************************************************************/

  FUNCTION GetAttivitaBEN (p_progr_bando_linea_intervento      IN  PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO%TYPE,
                           p_id_soggetto_ben                  IN  PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE,
                           p_id_utente                        IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE,
                           p_desc_breve_tipo_anagrafica     IN  PBANDI_D_TIPO_ANAGRAFICA.DESC_BREVE_TIPO_ANAGRAFICA%TYPE,
                           p_start                            IN  PLS_INTEGER DEFAULT 0,
                           p_id_progetto                       IN  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE DEFAULT NULL,
                           p_filtro_attivita                   IN  VARCHAR2 DEFAULT NULL)    RETURN LISTTASKPROG;


--======================================================================================================================================
    /***********************************************************************************************************************************
     CountProgettiBEN : Conta i progetti del Beneficiario per un Bando Linea
                         Ritorna il numero di progetti
     Parametri input: p_progr_bando_linea_intervento  - Progr del Bando Linea
                      p_id_soggetto_ben  - id del soggetto Beneficiario
                      p_id_utente          - id dell'utente
    ***********************************************************************************************************************************/

  FUNCTION CountProgettiBEN (p_progr_bando_linea_intervento  IN  PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO%TYPE,
                             p_id_soggetto_ben               IN  PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE,
                             p_id_utente                     IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE) RETURN INTEGER;


--======================================================================================================================================
    /***********************************************************************************************************************************
     GetProgetti : Elabora i progetti del Beneficiario per un Bando Linea
                         Ritorna la lista dei progetti con le seguenti informazioni:
                              - id_progetto
                              - titolo_progetto
                              - codice_visualizzato
                              - acronimo_progetto
     Parametri input: p_progr_bando_linea_intervento      - Progr del Bando Linea
                      p_id_soggetto_ben                    - id del soggetto Beneficiario
                      p_id_utente                          - id dell'utente
    ***********************************************************************************************************************************/

  FUNCTION GetProgetti (p_progr_bando_linea_intervento  IN  PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO%TYPE,
                        p_id_soggetto_ben               IN  PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE,
                        p_id_utente                     IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE) RETURN LISTPROG;

--======================================================================================================================================
    /***********************************************************************************************************************************
     IsLocked :      Verifica se il task è lockato da altro utente
                     Ritorna il nome dell'utente se il task è lockato
     Parametri input: p_id_progetto        - id del Progetto
                      p_descr_breve_task   - nome del task/attività
                      p_id_utente          - id dell'utente
    ***********************************************************************************************************************************/
  FUNCTION IsLocked      (p_id_progetto       IN  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                          p_descr_breve_task  IN  PBANDI_D_TASK.DESCR_BREVE_TASK%TYPE,
                          p_id_utente         IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE) RETURN VARCHAR2;


--======================================================================================================================================
    /***********************************************************************************************************************************
     StartAttivita : Istanzia-prosegue l'attività per il progetto
                     Ritorna error_code  (0=Positivo,
                                          1=Lock)
     Parametri input: p_id_progetto        - id del Progetto
                      p_descr_breve_task   - nome del task/attività
                      p_id_utente          - id dell'utente
    ***********************************************************************************************************************************/
  FUNCTION StartAttivita (p_id_progetto       IN  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                          p_descr_breve_task  IN  PBANDI_D_TASK.DESCR_BREVE_TASK%TYPE,
                          p_id_utente         IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE) RETURN INTEGER;
--======================================================================================================================================
    /***********************************************************************************************************************************
     UnlockAttivita : Rilascia l'attività iniziata dall'utente
                     Ritorna error_code  (0=Positivo,
                                          1=Lock di altro utente,
                                          2=Attività non ancora istanziata per il progetto,
                                          3=Attività non lockata)
     Parametri input: p_id_progetto        - id del Progetto
                      p_descr_breve_task   - nome del task/attività
                      p_id_utente          - id dell'utente
    ***********************************************************************************************************************************/
  FUNCTION UnlockAttivita (p_id_progetto       IN  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                          p_descr_breve_task  IN  PBANDI_D_TASK.DESCR_BREVE_TASK%TYPE,
                          p_id_utente         IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE) RETURN INTEGER;
--======================================================================================================================================
    /***********************************************************************************************************************************
     EndAttivita : Chiude l'attività
                     Ritorna error_code  (0=Positivo,
                                          1=Lock di altro utente,
                                          2=Attività non ancora istanziata per il progetto,
                                          3=Chiusura non possibile per dati non congruenti del businness)
     Parametri input: p_id_progetto        - id del Progetto
                      p_descr_breve_task   - nome del task/attività
                      p_id_utente          - id dell'utente
                      p_flag_forzatura = 'S' -- Apro d'ufficio l'attività per poi chiuderla
    ***********************************************************************************************************************************/
  FUNCTION EndAttivita (p_id_progetto       IN  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                        p_descr_breve_task  IN  PBANDI_D_TASK.DESCR_BREVE_TASK%TYPE,
                        p_id_utente         IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE,
                        p_flag_forzatura    IN VARCHAR2 DEFAULT NULL) RETURN INTEGER;

  -- mc sviluppo settembre 2021
  -- clone della fnc Endattivita che non prevede il commit
  -- (che sarà gestito dalla procedura chiamante)
  FUNCTION EndAttivitaNoCommit
                       (p_id_progetto       IN  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                        p_descr_breve_task  IN  PBANDI_D_TASK.DESCR_BREVE_TASK%TYPE,
                        p_id_utente         IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE,
                        p_flag_forzatura    IN VARCHAR2 DEFAULT NULL) RETURN INTEGER;

--======================================================================================================================================


    /***********************************************************************************************************************************
     IsAbilitato : calcola se un utente è abilitato al task del processo . Ritorna (TRUE/FALSE)
     Parametri input: p_id_processo  - id del processo
                      p_id_utente    - id dell'utente
                      p_id_task      - id del task
                      p_desc_breve_tipo_anagrafica - nome del tipo anagrafica
    ***********************************************************************************************************************************/

  FUNCTION IsAbilitato (p_id_processo IN PBANDI_T_PROCESSO.ID_PROCESSO%TYPE,
                         p_id_task     IN PBANDI_D_TASK.ID_TASK%TYPE,
                         p_desc_breve_tipo_anagrafica     IN  PBANDI_D_TIPO_ANAGRAFICA.DESC_BREVE_TIPO_ANAGRAFICA%TYPE)   RETURN BOOLEAN;

--======================================================================================================================================
    /***********************************************************************************************************************************
     GetGatewayBindTask :Ritorna il tipo di GATEWAY (AND,OR) che condiziona il task (Appartenenza del task ad una condizione di AND/OR)
     Parametri input: p_id_processo  - id della Progetto associata al progetto
                      p_id_task      - id del task
    ***********************************************************************************************************************************/
  FUNCTION GetGatewayBindTask (p_id_processo IN PBANDI_T_PROCESSO.ID_PROCESSO%TYPE,
                               p_id_task IN PBANDI_D_TASK.ID_TASK%TYPE) RETURN INTEGER;
--======================================================================================================================================

    /***********************************************************************************************************************************
     Regola : calcola  se un progetto appartiene ad una regola associata al Bando Linea (TRUE/FALSE)

     Parametri input: pIdProgetto  - id della Progetto
                      pRegola      - regola da applicare
    ***********************************************************************************************************************************/
  FUNCTION Regola (pIdProgetto  IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                    pRegola     IN PBANDI_C_REGOLA.DESC_BREVE_REGOLA%TYPE)   RETURN BOOLEAN;

--======================================================================================================================================

    /***********************************************************************************************************************************
     FN_PROG_2014_REP_2007 :  -- Metodo associato al task di selezione PROG_2014_REP_2007 (ROOT DEL PROCESSO)
                                 Ritorna il task che deve essere processato successivamente
                                 Funzione per determinare la selezione del ramo (SCHEDA PROGETTO o ALTRO PROGETTO)

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_processo  - id del Processo
    ***********************************************************************************************************************************/

  FUNCTION FN_PROG_2014_REP_2007 (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                  p_id_processo IN PBANDI_T_PROCESSO.ID_PROCESSO%TYPE) RETURN INTEGER;
--======================================================================================================================================

    /***********************************************************************************************************************************
     FN_EROG_LIQUID :  -- Metodo associato al task di selezione EROG_LIQUID. Ritorna il task che deve essere processato successivamente
                          Funzione per determinare la selezione del ramo delle Erogazioni o Liquidazioni

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_processo  - id del Processo
    ***********************************************************************************************************************************/
  FUNCTION FN_EROG_LIQUID (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                           p_id_processo IN PBANDI_T_PROCESSO.ID_PROCESSO%TYPE) RETURN INTEGER;
--======================================================================================================================================

    /***********************************************************************************************************************************
     FN_CARICAM_INDIC_AVVIO :  -- Metodo associato al task CARICAM-INDIC-AVVIO. Ritorna S se il task deve essere processato altrimenti N

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita
    ***********************************************************************************************************************************/
  FUNCTION FN_CARICAM_INDIC_AVVIO (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE  DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
  RETURN VARCHAR2;
--======================================================================================================================================
    /***********************************************************************************************************************************
     FN_VISUALIZZA_INDICATORI :  -- Metodo associato al task VISUALIZZA-INDICATORI. Ritorna S se il task deve essere processato altrimenti N

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita
    ***********************************************************************************************************************************/
  FUNCTION FN_VISUALIZZA_INDICATORI (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE  DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
  RETURN VARCHAR2;

    /***********************************************************************************************************************************
     FN_CRONOPROG_AVVIO :  -- Metodo associato al task CRONOPROG_AVVIO Ritorna S se il task deve essere processato altrimenti N

      Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita
    ***********************************************************************************************************************************/
  FUNCTION FN_CRONOPROG_AVVIO (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                               p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                               p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )

  RETURN VARCHAR2;
--======================================================================================================================================

    /***********************************************************************************************************************************
     FN_CARICAM_INDIC_PROG :  -- Metodo associato al task CARICAM-INDIC-PROG Ritorna S se il task deve essere processato  o la chiusura da esito positivo
                                 altrimenti N

      Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita
     ***********************************************************************************************************************************/
  FUNCTION FN_CARICAM_INDIC_PROG (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                  p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                  p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
  RETURN VARCHAR2;
--======================================================================================================================================

    /***********************************************************************************************************************************
     FN_CRONOPROGRAMMA :  -- Metodo associato al task CRONOPROGRAMMA Ritorna S se il task deve essere processato  o la chiusura da esito positivo
                             altrimenti N

      Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita
    ***********************************************************************************************************************************/
  FUNCTION FN_CRONOPROGRAMMA (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                              p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                              p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
  RETURN VARCHAR2;
--======================================================================================================================================


  /***********************************************************************************************************************************
     FN_RET_INDIC_PROG :  -- Metodo associato al task INDICATORI DI PROGETTO (RETTIFICA) Ritorna S se il task deve essere processato  o la chiusura da esito positivo
                             altrimenti N

      Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita
    ***********************************************************************************************************************************/



  FUNCTION FN_RET_INDIC_PROG (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                              p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                              p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
  RETURN VARCHAR2;


  /***********************************************************************************************************************************
     FN_RET_CRONOPROG :  -- Metodo associato al task CRONOPROGRAMMA (RETTIFICA) Ritorna S se il task deve essere processato  o la chiusura da esito positivo
                             altrimenti N

      Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita
    ***********************************************************************************************************************************/

  FUNCTION FN_RET_CRONOPROG (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                             p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                             p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
  RETURN VARCHAR2;


/***********************************************************************************************************************************
    FN_COMUNIC_RINUNCIA: -- Metodo associato al task COMUNIC_RINUNCIA  Ritorna 'S'

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita

 ***********************************************************************************************************************************/

  FUNCTION FN_COMUNIC_RINUNCIA (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
  RETURN VARCHAR2;



/***********************************************************************************************************************************
    FN_DICH_DI_SPESA: -- Metodo associato al task task DICH-DI-SPESA

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita

 ***********************************************************************************************************************************/

FUNCTION FN_DICH_DI_SPESA      (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )   RETURN VARCHAR2;


/***********************************************************************************************************************************
    FN_DICH_SPESA_INTEGRATIVA: -- Metodo associato al task task DICH-SPESA-INTEGRATIVA  RITORNA S SE DEVE VISULAIZZARE IL TASK

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita

 ***********************************************************************************************************************************/
FUNCTION FN_DICH_SPESA_INTEGRATIVA (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                    p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                    p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
                                    RETURN VARCHAR2;

/***********************************************************************************************************************************
    FN_VALID_DICH_SPESA: -- Metodo associato al task task VALID-DICH-SPESA  RITORNA S SE DEVE VISULAIZZARE IL TASK

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita

 ***********************************************************************************************************************************/
FUNCTION FN_VALID_DICH_SPESA       (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                    p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                    p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
                                    RETURN VARCHAR2;
/***********************************************************************************************************************************
    FN_VALID_DICH_SPESA_FINALE: -- Metodo associato al task task VALID-DICH-SPESA-FINALE  RITORNA S SE DEVE VISULAIZZARE IL TASK

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita

 ***********************************************************************************************************************************/
FUNCTION FN_VALID_DICH_SPESA_FINALE    (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                        p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                        p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
                                        RETURN VARCHAR2;

/***********************************************************************************************************************************
    FN_RICH_EROG_CALC_CAU: -- Metodo associato al task VRICH-EROG-CALC-CAU  RITORNA LA DESCRIZIONE DELLA CAUSALE DA INTEGRARE ALLA DESCR. DEL TASK

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita

 ***********************************************************************************************************************************/


FUNCTION FN_RICH_EROG_CALC_CAU         (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                        p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                        p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
                                        RETURN VARCHAR2;



/***********************************************************************************************************************************
     FN_RICH_EROG_CALC_CAU_02 :  -- Metodo associato al task RICH-EROG-CALC-CAU. Ritorna S se il task deve essere processato altrimenti N

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita
    ***********************************************************************************************************************************/
  FUNCTION FN_RICH_EROG_CALC_CAU_02 (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                     p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE  DEFAULT NULL,
                                     p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
  RETURN VARCHAR2;

/***********************************************************************************************************************************
     FN_VERIF_RICH_EROG :  -- Metodo associato al task VERIF_RICH_EROG. Ritorna S se il task deve essere processato altrimenti N

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita
    ***********************************************************************************************************************************/

  FUNCTION FN_VERIF_RICH_EROG            (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                          p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                          p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
  RETURN VARCHAR2;
--

/***********************************************************************************************************************************
     FN_GEST_FIDEIUSSIONI:  -- Metodo associato al task GEST-FIDEIUSSIONI. Ritorna S se il task deve essere processato altrimenti N

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita
    ***********************************************************************************************************************************/
  FUNCTION FN_GEST_FIDEIUSSIONI    (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                     p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE  DEFAULT NULL,
                                     p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
  RETURN VARCHAR2;


/***********************************************************************************************************************************
    FN_CHIUSURA_PROGETTO: -- Metodo associato al task CHIUSURA-PROGETTO

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita

 ***********************************************************************************************************************************/

FUNCTION FN_CHIUSURA_PROGETTO      (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )   RETURN VARCHAR2;

/***********************************************************************************************************************************
    FN_CHIUSURA_PROG_RINUNCIA: -- Metodo associato al task CHIUSURA-PROG-RINUNCIA

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita

 ***********************************************************************************************************************************/

FUNCTION FN_CHIUSURA_PROG_RINUNCIA      (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                         p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                         p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )   RETURN VARCHAR2;

/***********************************************************************************************************************************
    FN_CHIUS_UFF_PROG: -- Metodo associato al task CHIUS-UFF-PROG

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita

 ***********************************************************************************************************************************/

FUNCTION FN_CHIUS_UFF_PROG      (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                         p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                         p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )   RETURN VARCHAR2;


--======================================================================================================================================

    /***********************************************************************************************************************************
     FN_PROP_RIM_CE:  -- Metodo associato al task PROP-RIM-CE. Ritorna S se il task deve essere processato altrimenti N

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita
    ***********************************************************************************************************************************/
  FUNCTION FN_PROP_RIM_CE (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE  DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
  RETURN VARCHAR2;
--======================================================================================================================================

/***********************************************************************************************************************************
     FN_RIM_CE:  -- Metodo associato al task RIM-CE. Ritorna S se il task deve essere processato altrimenti N

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita
    ***********************************************************************************************************************************/
  FUNCTION FN_RIM_CE (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE  DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
  RETURN VARCHAR2;
--======================================================================================================================================

/***********************************************************************************************************************************
     FN_MOD_EROG:  -- Metodo associato al task MOD-EROG:. Ritorna S se il task deve essere processato altrimenti N

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita
    ***********************************************************************************************************************************/
  FUNCTION FN_MOD_EROG (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE  DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
  RETURN VARCHAR2;
--======================================================================================================================================
/***********************************************************************************************************************************
     FN_CONSULTA_LIQUID:  -- Metodo associato al task CONSULTA-LIQUID:. Ritorna S se il task deve essere processato altrimenti N

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita
    ***********************************************************************************************************************************/
  FUNCTION FN_CONSULTA_LIQUID (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE  DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
  RETURN VARCHAR2;
--======================================================================================================================================
/***********************************************************************************************************************************
     FN_REVOCA:  -- Metodo associato al task REVOCA:. Ritorna S se il task deve essere processato altrimenti N

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita
    ***********************************************************************************************************************************/
  FUNCTION FN_REVOCA              (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE  DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
  RETURN VARCHAR2;
--======================================================================================================================================

/***********************************************************************************************************************************
     FN_MOD_REVOCA:  -- Metodo associato al task MOD_REVOCA:. Ritorna S se il task deve essere processato altrimenti N

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita
    ***********************************************************************************************************************************/
  FUNCTION FN_MOD_REVOCA          (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE  DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
  RETURN VARCHAR2;
--======================================================================================================================================

/***********************************************************************************************************************************
     FN_RECUPERO:  -- Metodo associato al task RECUPERO: Ritorna S se il task deve essere processato altrimenti N

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita
    ***********************************************************************************************************************************/
  FUNCTION FN_RECUPERO            (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE  DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
  RETURN VARCHAR2;
--======================================================================================================================================
/***********************************************************************************************************************************
     FN_MOD_RECUPERO:  -- Metodo associato al task MOD_RECUPERO: Ritorna S se il task deve essere processato altrimenti N

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita
    ***********************************************************************************************************************************/
FUNCTION FN_MOD_RECUPERO            (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE  DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
RETURN VARCHAR2;
--======================================================================================================================================
/***********************************************************************************************************************************
     FN_GEST_SPESA_VALID:  -- Metodo associato al task GEST-SPESA-VALID: Ritorna S se il task deve essere processato altrimenti N

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita
    ***********************************************************************************************************************************/
FUNCTION FN_GEST_SPESA_VALID            (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE  DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
RETURN VARCHAR2;
--======================================================================================================================================
FUNCTION FN_GEST_APPALTI                (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE  DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
RETURN VARCHAR2;
--======================================================================================================================================
/***********************************************************************************************************************************
    FN_DESC_DICH_SPESA_FIN_INT: -- Metodo associato al task VALID-DICH-SPESA-FINALE : RITORNA LA DESCRIZIONE DEL TIPO DI DICHIARAZIONE ALLA DESCR. DEL TASK

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita

 ***********************************************************************************************************************************/


FUNCTION FN_DESC_DICH_SPESA_FIN_INT         (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                             p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                             p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
                                             RETURN VARCHAR2;

FUNCTION FN_RICH_INTEGRAZ_DICH_SPESA (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
                                                                   RETURN VARCHAR2;

FUNCTION FN_RICH_INTEGRAZ (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
                                                                   RETURN VARCHAR2;


FUNCTION FN_CALCOLO_ECONOMIE (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                              p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL)
                              RETURN NUMBER;

/***********************************************************************************************************************************
     FN_GEST_CONTRODEDUZ:  -- Metodo associato al task GEST-CONTRODEDUZ:. Ritorna S se il task deve essere processato altrimenti N

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita
    ***********************************************************************************************************************************/
FUNCTION FN_GEST_CONTRODEDUZ (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                              p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                              p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
RETURN VARCHAR2;

/***********************************************************************************************************************************
     FN_GEST_CONTESTAZ:  -- Metodo associato al task GEST-CONTESTAZ:. Ritorna S se il task deve essere processato altrimenti N

     Parametri input: p_id_progetto  - id del Progetto
                      p_id_utente   - id dell' utente
                      p_funzione_chiamante : GET se il metodo è chiamato da GetAttivita (DEFAULT)
                                             END se il metodo è chiamato da EndAttivita
    ***********************************************************************************************************************************/
FUNCTION FN_GEST_CONTESTAZ (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                              p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                              p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
RETURN VARCHAR2;

FUNCTION FN_REL_TEC_I (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
                                                                   RETURN VARCHAR2;

FUNCTION FN_REL_TEC_F (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
                                                                   RETURN VARCHAR2;

FUNCTION FN_VAL_REL_TEC_I (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
                                                                   RETURN VARCHAR2;

FUNCTION FN_VAL_REL_TEC_F (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
                                                                   RETURN VARCHAR2;

END pck_pbandi_processo;
/