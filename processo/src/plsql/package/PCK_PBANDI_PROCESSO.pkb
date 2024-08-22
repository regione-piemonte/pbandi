/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE OR REPLACE PACKAGE BODY pck_pbandi_processo AS
  -------------------------------------------------------------------------------------------------------------------------------------
  --  1.
  -- Dalla tabella delle costanti recupera il parametro che indica il tempo previsto impostato in input
  -- per togliere i lock sulle attività bloccate dall'utente
  FUNCTION getJobClearLockInterval RETURN VARCHAR2 AS
    v_ritorno VARCHAR2(1000);
  BEGIN
    BEGIN
       SELECT valore
         INTO v_ritorno
         FROM PBANDI_C_COSTANTI
        WHERE attributo = 'PCK_PBANDI_PROCESSO.JOB_CLEAR_LOCK_INTERVAL';
    EXCEPTION
       WHEN OTHERS THEN
         RAISE_APPLICATION_ERROR(-20000,'Errore accesso a parametro <PCK_PBANDI_PROCESSO.JOB_CLEAR_LOCK_INTERVAL> su PBANDI_C_COSTANTI '||SQLERRM);
    END;
    RETURN v_ritorno;
  END;

  -- FUNZIONE PER FARE UNA REPLACE SU UN CLOB
   -------------------------------------------------------------------------------------------------------------------------------------
  FUNCTION REPLACE_WITH_CLOB	  (P_SOURCE IN CLOB,
								   P_SEARCH IN VARCHAR2,
								   P_REPLACE IN CLOB) RETURN CLOB IS
	  L_POS PLS_INTEGER;
	BEGIN
	  L_POS := INSTR(P_SOURCE, P_SEARCH);

	  IF L_POS > 0 THEN
		RETURN SUBSTR(P_SOURCE, 1, L_POS-1)
			|| P_REPLACE
			|| SUBSTR(P_SOURCE, L_POS+LENGTH(P_SEARCH));
	  ELSE
		RETURN P_SOURCE;
	  END IF;

  END REPLACE_WITH_CLOB;
  -------------------------------------------------------------------------------------------------------------------------------------
  -- 2.
  -- Cicla sui task che sono rimaste in lock e per il periodo di tempo recuperato al punto 1
  -- imposta la data fine a sysdate


  FUNCTION ClearLock  RETURN NUMBER AS
     CURSOR c1 IS
        SELECT id_task_action
          FROM PBANDI_T_TASK_ACTION
         WHERE dt_fine IS NULL
         AND dt_inizio < sysdate - (pck_pbandi_processo.getJobClearLockInterval/1440)
         FOR UPDATE OF dt_fine;
  BEGIN
     FOR r1 IN c1 LOOP
       UPDATE PBANDI_T_TASK_ACTION
          SET dt_fine = sysdate
        WHERE CURRENT OF c1;
     END LOOP;
     COMMIT;
     RETURN 0;
  END;

  -------------------------------------------------------------------------------------------------------------------------------------
  --GESTIONE DELLE NOTIFICHE

  --
  -- La funzione serve per settare il parametro dei metadati passato in input
  --

  --PROCEDURE PutNotificationMetadata (p_nome_metadata IN VARCHAR2, p_valore_metadata IN VARCHAR2) AS
  PROCEDURE PutNotificationMetadata (p_nome_metadata IN VARCHAR2, p_valore_metadata IN CLOB) AS -- mc settembre 2019

     --v_query VARCHAR2(4000);
     v_query clob; -- mc settembre 2019

    BEGIN
         v_query := 'DECLARE'||chr(10)||
                       'v_dummy varchar2(1);'||chr(10)||
                      'CURSOR c1 IS'||chr(10)||
                          'SELECT NULL FROM PBANDI_D_METADATA_NOTIFICA WHERE NOME_PARAMETRO = '''||p_nome_metadata||''';'||chr(10)||
                    'BEGIN'||chr(10)||
                       'OPEN c1;'||chr(10)||
                        'FETCH c1 INTO v_dummy;'||chr(10)||
                        'IF c1%NOTFOUND THEN RAISE_APPLICATION_ERROR(-20000,''PARAMETRO '||p_nome_metadata||' NON PREVISTO NEI METADATI'');'||chr(10)||
                        'END IF;'||chr(10)||
                       'CLOSE c1;'||chr(10)||
                      -- ' pck_pbandi_processo.v_metadata_notifiche_processo.'||p_nome_metadata||' := '''||p_valore_metadata||''';'||chr(10)||
                      ' pck_pbandi_processo.v_metadata_notifiche_processo.'||p_nome_metadata||' := '''||replace(p_valore_metadata,'''','''''')||''';'||chr(10)||
                      --' pck_pbandi_processo.v_metadata_notifiche_processo.'||p_nome_metadata||' := '''||REPLACE_WITH_CLOB(p_valore_metadata,'''','''''')||''';'||chr(10)||
                    'END;';



     EXECUTE IMMEDIATE v_query;

    END;
 --------------------------------------------------------------------------------------------------------------------------------------

 --
 -- la funzione invia la notifica all'utente -->stato notifica = I (inviata)
 -- in modo parametrico il testo viene ostituito in base ai dati del progetto/notifica
 -- il record viene inserito sulla tavola PBANDI_T_NOTIFICA_PROCESSO
 --
  FUNCTION SendNotificationMessage  (p_id_progetto                   IN  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                     p_descr_breve_template_notif    IN  PBANDI_D_TEMPLATE_NOTIFICA.DESCR_BREVE_TEMPLATE_NOTIFICA%TYPE,
                                     p_codice_ruolo                  IN  PBANDI_C_RUOLO_DI_PROCESSO.CODICE%TYPE,
                                     p_id_utente                     IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE,
                                     p_id_target                     IN  PBANDI_T_NOTIFICA_PROCESSO.ID_TARGET%TYPE )  RETURN INTEGER AS --mc novembre 2018

     CURSOR c1 (c_descr_breve_template_notif PBANDI_D_TEMPLATE_NOTIFICA.DESCR_BREVE_TEMPLATE_NOTIFICA%TYPE) IS
        SELECT comp_subject,
               comp_message,
               id_template_notifica,
               id_entita --mc novembre 2018
          FROM PBANDI_D_TEMPLATE_NOTIFICA
         WHERE descr_breve_template_notifica = c_descr_breve_template_notif;

     r1 c1%ROWTYPE;

     CURSOR c2 (c_id_template_notifica PBANDI_D_TEMPLATE_NOTIFICA.ID_TEMPLATE_NOTIFICA%TYPE) IS
        SELECT nome_parametro,
               placeholder
          FROM PBANDI_R_PLACEHOLDER_NOTIFICA a,
               PBANDI_D_METADATA_NOTIFICA b
         WHERE a.id_template_notifica = c_id_template_notifica
           AND b.id_metadata_notifica = a.id_metadata_notifica;


     v_subject_notifica     VARCHAR2(4000);--
     --v_message_notifica     VARCHAR2(6000);-- mc sviluppo settembre 2019
     v_message_notifica     CLOB;-- mc sviluppo settembre 2019
     v_ritorno INTEGER := 0;
     --v_statement_replace VARCHAR2(32000);
     v_statement_replace CLOB;

     v_id_ruolo  PBANDI_C_RUOLO_DI_PROCESSO.ID_RUOLO_DI_PROCESSO%TYPE;
     -- mc sviluppo 25052021
     v_mittente VARCHAR2(500);
     v_destinatario VARCHAR2(500);
     v_GDPR     VARCHAR2(1000);
     vNomeBatch PBANDI_D_NOME_BATCH.NOME_BATCH%TYPE;
     nIdProcessoBatch PBANDI_L_PROCESSO_BATCH.id_processo_batch%TYPE;
     v_CodiceVisualizzato PBANDI_T_PROGETTO.Codice_Visualizzato%TYPE;
     v_TitoloProgetto  PBANDI_T_PROGETTO.TITOLO_PROGETTO%TYPE;
     v_TitoloBando PBANDI_R_BANDO_LINEA_INTERVENT.Nome_Bando_Linea%TYPE;
     --

  BEGIN
     OPEN c1(p_descr_breve_template_notif);
     FETCH c1 into r1;
     IF c1%NOTFOUND THEN
        v_ritorno := 1; -- Template di notifica non previsto
     ELSE
            v_subject_notifica := r1.comp_subject;
            v_message_notifica := r1.comp_message;
            FOR r2 in c2(r1.id_template_notifica)
            LOOP
            v_statement_replace :=

                'DECLARE'||chr(10)||
                   'v_subject_notifica     VARCHAR2(4000);'||chr(10)||
                   'v_message_notifica     CLOB;'||chr(10)|| ---- mc sviluppo settembre 2019
                   'v_placeholder          PBANDI_R_PLACEHOLDER_NOTIFICA.PLACEHOLDER%TYPE;'||chr(10)||
                  -- 'v_nome_parametro       PBANDI_D_METADATA_NOTIFICA.NOME_PARAMETRO%TYPE;'||chr(10)||
                  -- 'v_nome_parametro        VARCHAR2(4000);'||chr(10)||
                  'v_nome_parametro        CLOB;'||chr(10)||
                'BEGIN'||chr(10)||
                   'v_subject_notifica     := '''||replace(v_subject_notifica,'''','''''')||''';'||chr(10)||
                   'v_message_notifica     := '''||replace(v_message_notifica,'''','''''')||''';'||chr(10)||
                   'v_placeholder          := '''||r2.placeholder||''';'||chr(10)||
                   'v_nome_parametro       :=   pck_pbandi_processo.v_metadata_notifiche_processo.'||replace(r2.nome_parametro,'''','''''')||';'||chr(10)||
                   ':v_subject_notifica := replace(v_subject_notifica,v_placeholder,v_nome_parametro);'||chr(10)||
                   ':v_message_notifica := replace(v_message_notifica,v_placeholder,v_nome_parametro);'||chr(10)||
                 'END;';

                   EXECUTE IMMEDIATE v_statement_replace USING OUT v_subject_notifica, OUT v_message_notifica;


            END LOOP;

            BEGIN
               SELECT id_ruolo_di_processo
                 INTO v_id_ruolo
                 FROM PBANDI_C_RUOLO_DI_PROCESSO
                WHERE codice = p_codice_ruolo;
            EXCEPTION
              WHEN OTHERS THEN RAISE_APPLICATION_ERROR(-20000,'ERRORE ACCESSO PBANDI_C_RUOLO_DI_PROCESSO '||p_codice_ruolo||SQLERRM);
            END;

            BEGIN
               INSERT INTO PBANDI_T_NOTIFICA_PROCESSO
                (STATO_NOTIFICA,
                 SUBJECT_NOTIFICA,
                 MESSAGE_NOTIFICA,
                 ID_RUOLO_DI_PROCESSO_DEST,
                 ID_UTENTE_MITT,
                 ID_PROGETTO,
                 DT_NOTIFICA,
                 ID_UTENTE_AGG,
                 DT_AGG_STATO_NOTIFICA,
                 ID_TEMPLATE_NOTIFICA,
                 ID_ENTITA, --mc novembre 2018
                 ID_TARGET) --mc novembre 2018
                VALUES
                (
                'I', --Inviata
                V_SUBJECT_NOTIFICA,
                V_MESSAGE_NOTIFICA,
                v_id_ruolo, -- Ruolo destinatari
                P_ID_UTENTE,
                P_ID_PROGETTO,
                SYSDATE, --DT_NOTIFICA
                NULL, --ID_UTENTE_AGG
                NULL, --DT_AGG_STATO_NOTIFICA
                r1.ID_TEMPLATE_NOTIFICA,
                r1.ID_ENTITA, --mc novembre 2018
                P_ID_TARGET --mc novembre 2018
                );
                COMMIT;
              EXCEPTION
                WHEN OTHERS THEN
                v_ritorno := 2; --Errore scrittura su tabella notifiche
            END;

     -- mc sviluppo 27052021


         BEGIN

           SELECT VALORE
            INTO   v_mittente
            FROM   PBANDI_C_COSTANTI
            WHERE  ATTRIBUTO = 'beneficiario_mail_mittente';

           SELECT VALORE
            INTO   v_GDPR
            FROM   PBANDI_C_COSTANTI
            WHERE  ATTRIBUTO = 'beneficiario_mail_mittente_GDPR';

           SELECT c.nome_bando_linea,
                  a.codice_visualizzato,
                  a.titolo_progetto
            INTO  v_TitoloBando,
                  v_CodiceVisualizzato,
                  v_TitoloProgetto
            FROM PBANDI_T_PROGETTO a,
                 PBANDI_T_DOMANDA b,
                 PBANDI_R_BANDO_LINEA_INTERVENT c
            WHERE a.id_progetto = p_id_progetto
            AND   b.id_domanda = a.id_domanda
            AND   c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;



           -- recupera mail destinatario
           -- invia mail

                 FOR RecInvioMail IN
                   (SELECT r.email
                    FROM   pbandi_t_recapiti r,
                           pbandi_r_soggetto_progetto sp,
                           pbandi_r_sogg_progetto_sede sps
                    WHERE  sp.id_progetto = p_id_progetto
                       and sp.progr_soggetto_progetto= sps.progr_soggetto_progetto
                       and r.id_recapiti= sps.id_recapiti
                       and sp.id_tipo_anagrafica=1
                       and sp.id_tipo_beneficiario <>4
                       and sps.id_tipo_sede =1
                       and sps.flag_email_confermata ='S'
                   )LOOP

                       v_destinatario:= RecInvioMail.email;

                       V_SUBJECT_NOTIFICA:= V_SUBJECT_NOTIFICA||
                                            ' '||
                                            'Progetto: '||
                                            v_CodiceVisualizzato;

                       V_MESSAGE_NOTIFICA:= 'Bando: '||v_TitoloBando||
                                            '<br>'||
                                            'Titolo del progetto: '||
                                            v_TitoloProgetto ||'<br/>'||
                                            '<br>'||
                                            V_MESSAGE_NOTIFICA ||'<br/>'||
                                            chr(10)||
                                            v_GDPR;

                       IF v_destinatario IS NOT NULL AND
                           v_id_ruolo = 2 THEN -- corrisponde a BENEFICIARIO

                         BEGIN
                           UTL_MAIL.SEND
                            (sender =>       v_mittente,
                             recipients =>   v_destinatario,
                             subject =>      V_SUBJECT_NOTIFICA,
                             message =>      V_MESSAGE_NOTIFICA,
                             mime_type =>    'text/html; charset=us-ascii'
                             );
                          EXCEPTION WHEN OTHERS THEN
                             vNomeBatch         := 'PBAN-INVIO-MAIL-BENEFICIARI';
                             nIdProcessoBatch   := PCK_PBANDI_UTILITY_BATCH.insprocbatch(vNomeBatch);
                             PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
                             PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'W108', SQLERRM ||' WARNING SU:  SendNotificationMessage progetto '||p_id_progetto|| ' mail '||v_destinatario);

                          END;

                       END IF;

                    END LOOP;

         END;


     --

     END IF;
     CLOSE c1;

    RETURN v_ritorno;
  END SendNotificationMessage;

  --------------------------------------------------------------------------------------------------------------------------------------
  -- la funzione in base all'id notifica passata in input costruisce un oggetto contenente tutte le informazioni
  -- da mostrare a video per l'utente
  FUNCTION GetNotificationMessage (p_id_notifica  IN  PBANDI_T_NOTIFICA_PROCESSO.ID_NOTIFICA%TYPE) RETURN OBJNOTIFPROG AS

    CURSOR c1 (c_id_notifica   PBANDI_T_NOTIFICA_PROCESSO.ID_NOTIFICA%TYPE) IS

       SELECT a.id_progetto,
              titolo_progetto,
              codice_visualizzato,
              id_notifica,
              stato_notifica,
              dt_notifica,
              subject_notifica,
              message_notifica
        FROM PBANDI_T_NOTIFICA_PROCESSO a,
             PBANDI_T_PROGETTO b
       WHERE a.id_notifica = c_id_notifica
         AND a.id_progetto = b.id_progetto
         AND stato_notifica != 'C';

    V_OBJNOTIFPROG OBJNOTIFPROG;

  BEGIN
     FOR rec_prog  IN c1(p_id_notifica) LOOP


      V_OBJNOTIFPROG := OBJNOTIFPROG  (rec_prog.id_progetto,
                                       rec_prog.titolo_progetto,
                                       rec_prog.codice_visualizzato,
                                       rec_prog.id_notifica,
                                       rec_prog.stato_notifica,
                                       rec_prog.dt_notifica,
                                       rec_prog.subject_notifica,
                                       rec_prog.message_notifica);



  END LOOP;


  RETURN V_OBJNOTIFPROG;

  END GetNotificationMessage;

  --------------------------------------------------------------------------------------------------------------------------------------
 -- GESTISCE LA CANCELLAZIONE LOGICA DELLE NOTIFICHE
 -- SET A 'C'
  FUNCTION CancelNotificationMessage (p_id_notifica  IN  PBANDI_T_NOTIFICA_PROCESSO.ID_PROGETTO%TYPE,
                                      p_id_utente    IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE) RETURN INTEGER AS
       v_ritorno INTEGER := 0;

  BEGIN
     UPDATE PBANDI_T_NOTIFICA_PROCESSO
        SET stato_notifica = 'C',
            id_utente_agg = p_id_utente,
            dt_agg_stato_notifica = sysdate
      WHERE id_notifica = p_id_notifica
        AND stato_notifica != 'C'; -- Non considera se cancellato

      IF sql%ROWCOUNT = 0 THEN
         v_ritorno := 1;
      END IF;

  RETURN v_ritorno;
  END CancelNotificationMessage;
  --------------------------------------------------------------------------------------------------------------------------------------

  -- FINE GESTIONE NOTIFICHE

  --
  -- La funzione viene richiamata dalla navigazione del processo, si verifica se il task/progetto in questione
  -- è già stato istanziato sulla tabella PBANDI_T_STEP_PROCESSO da un utente diverso da quello passato in input
  --

  FUNCTION IsLocked      (p_id_progetto       IN  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                          p_descr_breve_task  IN  PBANDI_D_TASK.DESCR_BREVE_TASK%TYPE,
                          p_id_utente         IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE) RETURN VARCHAR2 AS

    CURSOR c1 (c_id_processo        PBANDI_T_PROCESSO.ID_PROCESSO%TYPE,
               c_descr_breve_task   PBANDI_D_TASK.DESCR_BREVE_TASK%TYPE) IS

       SELECT id_step_processo
         FROM PBANDI_D_TASK a,
              PBANDI_T_STEP_PROCESSO b
        WHERE a.id_task = b.id_task
         AND descr_breve_task = c_descr_breve_task
         AND b.id_processo = c_id_processo;

    CURSOR c2 (c_id_progetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
               c_id_step_processo PBANDI_T_STEP_PROCESSO.id_step_processo%TYPE) IS

       SELECT id_istanza_step_processo
         FROM PBANDI_T_ISTANZA_STEP_PROCESSO
        WHERE id_progetto = c_id_progetto
          AND id_step_processo = c_id_step_processo;

    CURSOR c3 (c_id_istanza_step_processo PBANDI_T_ISTANZA_STEP_PROCESSO.id_istanza_step_processo%TYPE) IS
       SELECT id_task_action, a.id_utente, pf.nome||nvl2(pf.nome, ' ', '')|| pf.cognome denominazione
         FROM PBANDI_T_TASK_ACTION a,
              PBANDI_T_UTENTE b,
             PBANDI_T_PERSONA_FISICA pf
        WHERE id_istanza_step_processo = c_id_istanza_step_processo
         AND a.dt_fine IS NULL
         AND b.id_utente = a.id_utente
         AND pf.id_soggetto = b.id_soggetto
         AND PF.DT_FINE_VALIDITA IS NULL
         ORDER  BY pf.id_persona_fisica DESC ;

    v_ritorno VARCHAR2(1000) := '';
    v_id_step_processo PBANDI_T_STEP_PROCESSO.id_step_processo%TYPE;
    v_id_istanza_step_processo PBANDI_T_ISTANZA_STEP_PROCESSO.id_step_processo%TYPE;
    v_id_task_action PBANDI_T_TASK_ACTION.id_task_action%TYPE;
    v_id_utente_lock PBANDI_T_UTENTE.ID_UTENTE%TYPE;
    v_denominazione_lock  VARCHAR2(1000);

  BEGIN


      IF g_id_processo IS NULL THEN
        g_id_processo := GetProcesso(p_id_progetto);
      END IF;

      IF g_id_processo IS NULL THEN
        RAISE_APPLICATION_ERROR (-20000, 'Processo non valorizzato - Eseguire metodo GET_PROCESSO per il progetto '||p_id_progetto);
      END IF;

      OPEN c1(g_id_processo,p_descr_breve_task);
      FETCH c1 INTO v_id_step_processo;
      IF c1%NOTFOUND THEN
        RAISE_APPLICATION_ERROR (-20000, 'Errore accesso  PBANDI_T_STEP_PROCESSO Processo: '||g_id_processo||' Task : '||p_descr_breve_task);
      END IF;
      CLOSE c1;

      OPEN c2 (p_id_progetto,
               v_id_step_processo);
      FETCH c2 INTO v_id_istanza_step_processo;
      IF c2%FOUND THEN -- Inizio dell'attività
         -- Continuazione dell'attività (verifico che il task non abbia un lock di un altro utente
         OPEN c3 (v_id_istanza_step_processo);
         FETCH c3 INTO v_id_task_action, v_id_utente_lock,v_denominazione_lock;
         IF c3%FOUND THEN
            IF p_id_utente != v_id_utente_lock THEN
                 v_ritorno := v_denominazione_lock; -- Task lockato
            END IF;
         END IF;
         CLOSE c3;
      END IF;
      CLOSE c2;
  RETURN v_ritorno;
  END IsLocked;

--
-- La funzione verifica se l'utente è abilitato per operare  su quel processo/task per il ruolo dell'utente che si collega
--
   FUNCTION IsAbilitato (p_id_processo  IN PBANDI_T_PROCESSO.ID_PROCESSO%TYPE,
                         p_id_task      IN PBANDI_D_TASK.ID_TASK%TYPE,
                         p_desc_breve_tipo_anagrafica     IN  PBANDI_D_TIPO_ANAGRAFICA.DESC_BREVE_TIPO_ANAGRAFICA%TYPE)   RETURN BOOLEAN
    IS

      vDummy           VARCHAR2(1);
      vRitorno         BOOLEAN;
      CURSOR c1 (c_id_processo                  PBANDI_T_PROCESSO.ID_PROCESSO%TYPE,
                 c_id_task                      PBANDI_D_TASK.ID_TASK%TYPE,
                 c_desc_breve_tipo_anagrafica  PBANDI_D_TIPO_ANAGRAFICA.DESC_BREVE_TIPO_ANAGRAFICA%TYPE) IS
        SELECT NULL
          FROM PBANDI_V_TASK_RUOLO a
         WHERE id_processo = c_id_processo
           AND id_task = c_id_task
           AND desc_breve_tipo_anagrafica = c_desc_breve_tipo_anagrafica;

   BEGIN
      OPEN c1(p_id_processo,p_id_task,p_desc_breve_tipo_anagrafica);
      FETCH c1 INTO vDummy;
      IF c1%NOTFOUND THEN
         vRitorno := FALSE;
      ELSE
         vRitorno := TRUE;
      END IF;
      CLOSE c1;

      RETURN vRitorno;
   END IsAbilitato;
  --------------------------------------------------------------------------------------------------------------------------------------
 -- funzione  spesso richiamata dai Task di controllo e dai Task opzionali di controllo per rendere o mneo disponibile un'attività
 -- verifica se  il progetto si sottopone alla regola  passata in input
   FUNCTION Regola (pIdProgetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                    pRegola     IN PBANDI_C_REGOLA.DESC_BREVE_REGOLA%TYPE)
      RETURN BOOLEAN
   IS
      vDummy           VARCHAR2(1);
      vRitorno         BOOLEAN;

      CURSOR c1 (cIdProgetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                 cRegola    PBANDI_C_REGOLA.DESC_BREVE_REGOLA%TYPE) IS
            SELECT NULL
              FROM PBANDI_R_REGOLA_BANDO_LINEA rbl,
                   PBANDI_C_REGOLA r,
                   PBANDI_T_DOMANDA d,
                   PBANDI_T_PROGETTO p
             WHERE P.ID_PROGETTO = cIdProgetto
                   AND D.ID_DOMANDA = P.ID_DOMANDA
                   AND RBL.PROGR_BANDO_LINEA_INTERVENTO =
                          D.PROGR_BANDO_LINEA_INTERVENTO
                   AND RBL.ID_REGOLA = R.ID_REGOLA
                   AND R.DESC_BREVE_REGOLA = cRegola
                   AND r.DT_FINE_VALIDITA IS NULL;

   BEGIN
      OPEN c1 (pIdProgetto,pRegola);
      FETCH c1 INTO vDummy;
      IF c1%FOUND THEN
         vRitorno := TRUE;
      ELSE
         vRitorno := FALSE;
      END IF;
      CLOSE c1;

      RETURN vRitorno;
   END Regola;

  --------------------------------------------------------------------------------------------------------------------------------------
-- La funzionalita, quando è presente un gateway di AND o di OR ,verifica se il gruppo di task afferenti al gateway son stati risolti (and) oppure ne basta uno (OR)
-- restituisce l'id del task successivo con il quale partire per continuare la navigazione
--
  FUNCTION GetGatewayBindTask (p_id_processo IN PBANDI_T_PROCESSO.ID_PROCESSO%TYPE,
                               p_id_task     IN PBANDI_D_TASK.ID_TASK%TYPE) RETURN INTEGER IS

       CURSOR c1 (c_id_processo PBANDI_T_PROCESSO.ID_PROCESSO%TYPE,
                  c_id_task PBANDI_D_TASK.ID_TASK%TYPE) IS
          SELECT b.id_task,descr_breve_tipo_task
            FROM PBANDI_D_TIPO_TASK a,
                 PBANDI_D_TASK b,
                 PBANDI_T_STEP_PROCESSO c
            WHERE c.id_task_prec =  c_id_task
            AND c.id_processo = c_id_processo
            AND c.id_task = b.id_task
            AND a.id_tipo_task = b.id_tipo_task;

       v_descr_breve_tipo_task PBANDI_D_TIPO_TASK.DESCR_BREVE_TIPO_TASK%TYPE;
       v_ritorno INTEGER;

    BEGIN

       OPEN c1(p_id_processo,p_id_task);
       FETCH c1 INTO v_ritorno,v_descr_breve_tipo_task;
       --DBMS_OUTPUT.PUT_LINE('v_ritorno '||v_ritorno);
       --DBMS_OUTPUT.PUT_LINE('v_descr_breve_tipo_task '||v_descr_breve_tipo_task);
       IF v_descr_breve_tipo_task NOT IN ('GA','GO') THEN  -- Il task  non ha dipendenza  con un gataway di AND/OR
          v_ritorno := '';
       END IF;
       CLOSE c1;

       RETURN v_ritorno;
    END GetGatewayBindTask;

  --------------------------------------------------------------------------------------------------------------------------------------
 -- La funzionalità viene richiamata sia dalla funzione java sia internamente al flusso plsql
 -- Se restituisce null si tratta del vecchio FLUX
 -- Se restituisce un integer relativo al processo, si tratta del nuovo flusso 'THEFLUX'

    FUNCTION GetProcesso (p_id_progetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE) RETURN INTEGER IS

       cursor c0 (c_id_progetto NUMBER) is
          select id_processo
          FROM PBANDI_V_PROCESSO_PROGETTO
          where id_progetto = c_id_progetto;

       cursor c1 (c_id_progetto NUMBER) is
          select c.id_linea_di_intervento,
                 c.id_processo
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;

        cursor c2 (c_id_linea_di_intervento NUMBER) is
           select id_processo
           from PBANDI_D_LINEA_DI_INTERVENTO
           connect by prior id_linea_di_intervento_padre = id_linea_di_intervento
           start with id_linea_di_intervento = c_id_linea_di_intervento;

         v_id_linea_intervento INTEGER;
         v_id_processo PBANDI_T_PROCESSO.ID_PROCESSO%TYPE;

    BEGIN

       OPEN c0 (p_id_progetto); -- PROCESSO: Se già istanziato per il progetto lo utilizzo
       FETCH c0 into v_id_processo;
       IF c0%NOTFOUND THEN
          v_id_processo := NULL;
       END IF;
       CLOSE c0;

       IF v_id_processo IS NULL THEN
           OPEN c1 (p_id_progetto);
           FETCH c1 into v_id_linea_intervento,
                         v_id_processo;
           IF c1%NOTFOUND THEN
             v_id_processo := NULL;
             v_id_linea_intervento := NULL;
           END IF;
           CLOSE c1;
       END IF;

       IF v_id_processo IS NULL AND v_id_linea_intervento IS NOT NULL THEN
          OPEN c2(v_id_linea_intervento);
          LOOP
          FETCH c2 INTO v_id_processo;
          EXIT WHEN c2%NOTFOUND;
          IF v_id_processo IS NOT NULL THEN
            EXIT;
          END IF;
          END LOOP;
          CLOSE c2;
       END IF;

       g_id_processo := v_id_processo;
       RETURN v_id_processo;
    END GetProcesso;
  --------------------------------------------------------------------------------------------------------------------------------------------
  -- La funzionalità viene utilizzata dall'applicativo java, a fronte di un progressivo bando linea viene calcolato il processo di riferimento
  --
  FUNCTION GetProcessoBL (p_progr_bando_linea_intervento  IN  PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO%TYPE) RETURN INTEGER AS

       cursor c1 (c_progr_bando_linea_intervento NUMBER) is
          select id_linea_di_intervento,
                 id_processo
          from PBANDI_R_BANDO_LINEA_INTERVENT
          where progr_bando_linea_intervento = c_progr_bando_linea_intervento;

        cursor c2 (c_id_linea_di_intervento NUMBER) is
           select id_processo
           from PBANDI_D_LINEA_DI_INTERVENTO
           connect by prior id_linea_di_intervento_padre = id_linea_di_intervento
           start with id_linea_di_intervento = c_id_linea_di_intervento;

         v_id_linea_intervento INTEGER;
         v_id_processo PBANDI_T_PROCESSO.ID_PROCESSo%TYPE;

    BEGIN

       OPEN c1 (p_progr_bando_linea_intervento);
       FETCH c1 into v_id_linea_intervento,
                     v_id_processo;
       IF c1%NOTFOUND THEN
         v_id_processo := NULL;
         v_id_linea_intervento := NULL;
       END IF;
       CLOSE c1;

       IF v_id_processo IS NULL AND v_id_linea_intervento IS NOT NULL THEN
          OPEN c2(v_id_linea_intervento);
          LOOP
          FETCH c2 INTO v_id_processo;
          EXIT WHEN c2%NOTFOUND;
          IF v_id_processo IS NOT NULL THEN
            EXIT;
          END IF;
          END LOOP;
          CLOSE c2;
       END IF;

       g_id_processo := v_id_processo;
       RETURN v_id_processo;
    END GetProcessoBL;

  --------------------------------------------------------------------------------------------------------------------------------------

  -- ********* PROCEDURA DI DEMO ****************************
  PROCEDURE GetAttivitaQuery (p_id_progetto                                      IN  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                              p_id_utente                                         IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE,
                              p_desc_breve_tipo_anagrafica                         IN  PBANDI_D_TIPO_ANAGRAFICA.DESC_BREVE_TIPO_ANAGRAFICA%TYPE,
                              p_filtro_attivita                                    IN  VARCHAR2 DEFAULT NULL,
                              p_tab_task                                         IN OUT task_tab_type) AS

    V_LISTTASKPROG LISTTASKPROG := LISTTASKPROG(NULL);
    V_LISTTASKPROGORD LISTTASKPROG := LISTTASKPROG(NULL);

    v_x PLS_INTEGER := 0;


  BEGIN

         g_id_processo := pck_pbandi_processo.getprocesso(p_id_progetto);

         V_LISTTASKPROG := GetAttivita(p_id_progetto,p_id_utente,p_desc_breve_tipo_anagrafica,p_filtro_attivita);


      -- Ordinamento task
      DECLARE
         cursor c1 is
            SELECT a.* from TABLE (V_LISTTASKPROG) a
              ORDER BY a.DESCR_TASK, a.ID_PROGETTO;
         v_x PLS_INTEGER := 0;

      BEGIN
         FOR r1 IN c1 LOOP
            IF v_x > 0 THEN
              V_LISTTASKPROGORD.extend;
            END IF;
              v_x := v_x+1;
              V_LISTTASKPROGORD(v_x) := OBJTASKPROG(r1.id_progetto,
                                               r1.titolo_progetto,
                                               r1.codice_visualizzato,
                                               r1.descr_breve_task,
                                               r1.descr_task,
                                               r1.progr_bando_linea_intervento,
                                               r1.nome_bando_linea,
                                               r1.flag_opt,
                                               r1.flag_lock,
                                               r1.acronimo_progetto,
                                               r1.id_business,
                                               r1.id_notifica,
                                               r1.denominazione_lock,
                                               r1.FLAG_RICH_ABILITAZ_UTENTE  --mc 02/2022
                                               );
         END LOOP;
      END;


         FOR x IN 1..V_LISTTASKPROGORD.COUNT LOOP


            v_x := v_x+1;
            p_tab_task(v_x).id_progetto  := V_LISTTASKPROGORD(x).id_progetto;
            p_tab_task(v_x).titolo_progetto  := V_LISTTASKPROGORD(x).titolo_progetto;
            p_tab_task(v_x).codice_visualizzato  := V_LISTTASKPROGORD(x).codice_visualizzato;
            p_tab_task(v_x).descr_breve_task  := V_LISTTASKPROGORD(x).descr_breve_task;
            p_tab_task(v_x).descr_task  := V_LISTTASKPROGORD(x).descr_task;
            p_tab_task(v_x).progr_bando_linea_intervento  := V_LISTTASKPROGORD(x).progr_bando_linea_intervento;
            p_tab_task(v_x).nome_bando_linea  := V_LISTTASKPROGORD(x).nome_bando_linea;
            p_tab_task(v_x).flag_opt  := V_LISTTASKPROGORD(x).flag_opt;
            p_tab_task(v_x).flag_lock  := V_LISTTASKPROGORD(x).flag_lock;
            p_tab_task(v_x).acronimo_progetto  := V_LISTTASKPROGORD(x).acronimo_progetto;
            p_tab_task(v_x).id_business  := V_LISTTASKPROGORD(x).id_business;
            p_tab_task(v_x).id_notifica  := V_LISTTASKPROGORD(x).id_notifica;



        END LOOP;
  END GetAttivitaQuery;
  --------------------------------------------------------------------------------------------------------------------------------------

  -- ********* PROCEDURA DI DEMO ****************************
  PROCEDURE GetNotificheQuery (p_id_notifica    IN  PBANDI_T_NOTIFICA_PROCESSO.ID_NOTIFICA%TYPE,
                               p_tab_notifiche  IN OUT r_lista_notifiche) AS

    V_OBJNOTIFPROG OBJNOTIFPROG;
    v_stato_notifica VARCHAR2(20);



  BEGIN


         V_OBJNOTIFPROG := GetNotificationMessage(p_id_notifica);



            select DECODE(V_OBJNOTIFPROG.stato_notifica,'I','Inviata','R','Letta') INTO v_stato_notifica
            from dual;
            p_tab_notifiche.id_progetto  := V_OBJNOTIFPROG.id_progetto;
            p_tab_notifiche.titolo_progetto  := V_OBJNOTIFPROG.titolo_progetto;
            p_tab_notifiche.codice_visualizzato  := V_OBJNOTIFPROG.codice_visualizzato;
            p_tab_notifiche.id_notifica  := V_OBJNOTIFPROG.id_notifica;
            p_tab_notifiche.stato_notifica  := v_stato_notifica;
            p_tab_notifiche.dt_notifica  := V_OBJNOTIFPROG.dt_notifica;
            p_tab_notifiche.subject_notifica  := V_OBJNOTIFPROG.subject_notifica;
            p_tab_notifiche.message_notifica  := V_OBJNOTIFPROG.message_notifica;

  END GetNotificheQuery;
  --------------------------------------------------------------------------------------------------------------------------------------
  -- febbraio 2015 - Al momento non viene utilizzata --
  FUNCTION GetAttivitaBL   (p_progr_bando_linea_intervento  IN  PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO%TYPE,
                            p_id_utente                     IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE,
                            p_desc_breve_tipo_anagrafica    IN  PBANDI_D_TIPO_ANAGRAFICA.DESC_BREVE_TIPO_ANAGRAFICA%TYPE,
                            p_start                         IN  PLS_INTEGER DEFAULT 0) RETURN LISTTASKPROG AS

    V_LISTTASKBL LISTTASKPROG   := LISTTASKPROG(NULL);
    V_LISTTASKPROG LISTTASKPROG := LISTTASKPROG(NULL);

    v_x PLS_INTEGER := 0;

    CURSOR c1 (c_progr_bando_linea_intervento  IN  PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO%TYPE,
               c_riga PLS_INTEGER) IS

      select id_progetto,riga
      from
      (
       select id_progetto,rownum riga
        from(
                select   a.id_progetto
                      from PBANDI_T_PROGETTO a,
                           PBANDI_T_DOMANDA b,
                           PBANDI_R_BANDO_LINEA_INTERVENT c
                      where b.id_domanda = a.id_domanda
                      and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento
                      and  c.progr_bando_linea_intervento = c_progr_bando_linea_intervento
                      order by  a.id_progetto ))
        where riga between c_riga and c_riga + 19;

  BEGIN

     IF p_start = 1 THEN
        g_riga_progetti := 1;
     END IF;

     FOR r1 IN c1 (p_progr_bando_linea_intervento,g_riga_progetti) LOOP
         g_id_processo := GetProcesso(r1.id_progetto);

         V_LISTTASKPROG := GetAttivita(r1.id_progetto,p_id_utente,p_desc_breve_tipo_anagrafica);

         FOR x IN 1..V_LISTTASKPROG.COUNT LOOP

             IF v_x > 0 THEN
                V_LISTTASKBL.extend;
            END IF;

            v_x := v_x+1;
            V_LISTTASKBL(v_x) := OBJTASKPROG(V_LISTTASKPROG(x).id_progetto,
                                             V_LISTTASKPROG(x).titolo_progetto,
                                             V_LISTTASKPROG(x).codice_visualizzato,
                                             V_LISTTASKPROG(x).descr_breve_task,
                                             V_LISTTASKPROG(x).descr_task,
                                             V_LISTTASKPROG(x).progr_bando_linea_intervento,
                                             V_LISTTASKPROG(x).nome_bando_linea,
                                             V_LISTTASKPROG(x).flag_opt,
                                             V_LISTTASKPROG(x).flag_lock,
                                             V_LISTTASKPROG(x).acronimo_progetto,
                                             V_LISTTASKPROG(x).id_business,
                                             V_LISTTASKPROG(x).id_notifica,
                                             V_LISTTASKPROG(x).denominazione_lock ,
                                             V_LISTTASKPROG(x).FLAG_RICH_ABILITAZ_UTENTE --mc 02/2022
                                             );
        END LOOP;
     END LOOP;
     g_riga_progetti := g_riga_progetti + 20;
  RETURN V_LISTTASKBL;
  END GetAttivitaBL;

--------------------------------------------------------------------------------------------------------------------------------------
--
-- La funzionalita, in base al beneficiario di riferimento, vengono elencate le attività di riferimento (nell'ambito del bando linea di riferimento
-- nel sottoinsieme dei progetti collegati)
-- Il sistema prevede una scelta d aparte dell'utente, i seguneti parametri sono opzionali:
-- p_id_progetto  - Opzionale Se NULL tutti i progetti
-- p_filtro_attivita  - Opzionale Se valorizzato viene applicato un filtro sulla parte iniziale della descrizione del task
  FUNCTION GetAttivitaBEN (p_progr_bando_linea_intervento  IN  PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO%TYPE,
                           p_id_soggetto_ben               IN  PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE,
                           p_id_utente                     IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE,
                           p_desc_breve_tipo_anagrafica    IN  PBANDI_D_TIPO_ANAGRAFICA.DESC_BREVE_TIPO_ANAGRAFICA%TYPE,
                           p_start                         IN  PLS_INTEGER DEFAULT 0,
                           p_id_progetto                   IN  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE DEFAULT NULL,
                           p_filtro_attivita               IN  VARCHAR2 DEFAULT NULL) RETURN LISTTASKPROG AS

    V_LISTTASKBEN LISTTASKPROG   := LISTTASKPROG(NULL);
    V_LISTTASKPROG LISTTASKPROG := LISTTASKPROG(NULL);
    V_LISTTASKPROGORD LISTTASKPROG := LISTTASKPROG(NULL);

    v_x PLS_INTEGER := 0;

    CURSOR c1 (c_progr_bando_linea_intervento   PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO%TYPE,
               c_id_soggetto_ben                PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE,
               c_id_soggetto                    PBANDI_T_UTENTE.ID_UTENTE%TYPE,
               c_id_progetto                    PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
               c_riga PLS_INTEGER) IS

      select id_progetto,riga
      from
      (
       select id_progetto,rownum riga
        from(
                select distinct id_progetto
                      from PBANDI_V_PROGETTI_BEN_BL
                      where progr_bando_linea_intervento = c_progr_bando_linea_intervento
                      and id_soggetto_beneficiario = c_id_soggetto_ben
                      and id_soggetto = c_id_soggetto
                      and id_progetto = nvl(c_id_progetto,id_progetto)
                      order by  id_progetto ))
        where riga between c_riga and c_riga + g_num_progetti_ben_bl;

  v_id_soggetto PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE;

  BEGIN

    BEGIN
       SELECT id_soggetto
         INTO v_id_soggetto
         FROM PBANDI_T_UTENTE
        WHERE id_utente = p_id_utente;
    EXCEPTION
      WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20000,'Errore accesso PBANDI_T_UTENTE '||p_id_utente||sqlerrm);
    END;

     IF p_start = 1 THEN
        g_riga_progetti := 0;
     END IF;

     FOR r1 IN c1 (p_progr_bando_linea_intervento,p_id_soggetto_ben,v_id_soggetto,p_id_progetto,g_riga_progetti) LOOP
         g_id_processo := GetProcesso(r1.id_progetto);

         V_LISTTASKPROG := GetAttivita(r1.id_progetto,p_id_utente,p_desc_breve_tipo_anagrafica,p_filtro_attivita);

         FOR x IN 1..V_LISTTASKPROG.COUNT LOOP

             IF v_x > 0 THEN
                V_LISTTASKBEN.extend;
            END IF;

            v_x := v_x+1;
            V_LISTTASKBEN(v_x) := OBJTASKPROG(V_LISTTASKPROG(x).id_progetto,
                                             V_LISTTASKPROG(x).titolo_progetto,
                                             V_LISTTASKPROG(x).codice_visualizzato,
                                             V_LISTTASKPROG(x).descr_breve_task,
                                             V_LISTTASKPROG(x).descr_task,
                                             V_LISTTASKPROG(x).progr_bando_linea_intervento,
                                             V_LISTTASKPROG(x).nome_bando_linea,
                                             V_LISTTASKPROG(x).flag_opt,
                                             V_LISTTASKPROG(x).flag_lock,
                                             V_LISTTASKPROG(x).acronimo_progetto,
                                             V_LISTTASKPROG(x).id_business,
                                             V_LISTTASKPROG(x).id_notifica,
                                             V_LISTTASKPROG(x).denominazione_lock,
                                             V_LISTTASKPROG(x).FLAG_RICH_ABILITAZ_UTENTE --mc 02/2022
                                             );
        END LOOP;
     END LOOP;
     g_riga_progetti := g_riga_progetti + g_num_progetti_ben_bl + 1;

      -- Ordinamento task
      DECLARE
         cursor c1 is
            SELECT a.* from TABLE (V_LISTTASKBEN) a
              ORDER BY a.DESCR_TASK, a.ID_PROGETTO;
         v_x PLS_INTEGER := 0;

      BEGIN
         FOR r1 IN c1 LOOP
            IF v_x > 0 THEN
              V_LISTTASKPROGORD.extend;
            END IF;
              v_x := v_x+1;
              V_LISTTASKPROGORD(v_x) := OBJTASKPROG(r1.id_progetto,
                                               r1.titolo_progetto,
                                               r1.codice_visualizzato,
                                               r1.descr_breve_task,
                                               r1.descr_task,
                                               r1.progr_bando_linea_intervento,
                                               r1.nome_bando_linea,
                                               r1.flag_opt,
                                               r1.flag_lock,
                                               r1.acronimo_progetto,
                                               r1.id_business,
                                               r1.id_notifica,
                                               r1.denominazione_lock,
                                               r1.FLAG_RICH_ABILITAZ_UTENTE --mc 02/2022
                                               );
         END LOOP;
      END;

   RETURN V_LISTTASKPROGORD;
  END GetAttivitaBEN;

--------------------------------------------------------------------------------------------------------------------------------------
-- mc 12032024
FUNCTION GetAttivitaProgetto_Widget
                           (
                            p_id_utente                     IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE,         -- ID istruttore
                            p_descbrevewidget               IN PBANDI_D_WIDGET.DESC_BREVE_WIDGET%TYPE,  -- attività del widget
                            p_desc_breve_tipo_anagrafica    IN  PBANDI_D_TIPO_ANAGRAFICA.DESC_BREVE_TIPO_ANAGRAFICA%TYPE -- ruolo istruttore
                           ) RETURN LISTTASKPROGWIDBEN AS




    V_LISTTASKPROG          LISTTASKPROG:= LISTTASKPROG(NULL);
    V_LISTTASKPROGWIDBEN    LISTTASKPROGWIDBEN:= LISTTASKPROGWIDBEN(NULL); -- obj per accogliere GetAttivitaBEN + beneficiario
    V_LISTTASKPROGWIDBENORD LISTTASKPROGWIDBEN:= LISTTASKPROGWIDBEN(NULL);

    v_x PLS_INTEGER := 0;
    z_y PLS_INTEGER := 0;
    
    vCount           INTEGER;
    v_dett_business  VARCHAR2(100);
    v_DenomBen       VARCHAR2(1000);
   



    CURSOR c1 (p_id_utente   IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE               
               ) IS
                      Select distinct
                               lswidget.id_progetto,
                               rsp.id_persona_fisica,
                               rsp.id_ente_giuridico,
                               rsp.id_soggetto id_soggetto_beneficiario --> beneficiario 
                        From     PBANDI_D_WIDGET widget
                          Join   PBANDI_R_SOGGETTO_WIDGET swidget ON swidget.id_widget = widget.id_widget
                          Join   PBANDI_R_PROG_SOGG_WIDGET lswidget on lswidget.id_soggetto_widget = swidget.id_soggetto_widget
                          Join   PBANDI_T_UTENTE tut on tut.id_soggetto = swidget.id_soggetto
                          join   pbandi_r_soggetto_progetto rsp  on rsp.id_progetto = lswidget.id_progetto -->
                        Where    widget.desc_breve_widget = p_descbrevewidget  --> ad es. 'VALID' --> parametro in input: attivita widget
                          and    tut.id_utente =  p_id_utente                  --> parametro in input: istruttore
                          and    swidget.flag_widget_attivo = 'S'
                          and    rsp.id_tipo_anagrafica = 1
                          and    rsp.id_tipo_beneficiario !=4
                 ;
                   




  BEGIN
    
       
       FOR r1 IN c1 (p_id_utente) LOOP
       
         pck_pbandi_processo.g_id_processo := pck_pbandi_processo.GetProcesso(r1.id_progetto);

         V_LISTTASKPROG := pck_pbandi_processo.GetAttivita(r1.id_progetto,p_id_utente,p_desc_breve_tipo_anagrafica);

         FOR x IN 1..V_LISTTASKPROG.COUNT LOOP

             IF v_x > 0 THEN
                V_LISTTASKPROGWIDBEN.extend; --
            END IF;

            v_x := v_x+1;

            -- seleziono solamente i task associati al widget passato in input
              ---
              Select count(*)
              Into   vCount
              from   pbandi_d_widget w
              JOIN   pbandi_r_widget_task wk ON w.id_widget = wk.id_widget
              JOIN   pbandi_d_task dt ON dt.id_task = wk.id_task
              Where  w.desc_breve_widget = p_descbrevewidget
               and   dt.descr_breve_task = V_LISTTASKPROG(x).descr_breve_task;
              --

             If vCount > 0   then
               
               
               if r1.id_persona_fisica is not null then
                 
                 Select cognome ||' '||nome
                 Into v_DenomBen 
                 From pbandi_t_persona_fisica
                 Where id_persona_fisica = r1.id_persona_fisica;  
               
               end if;
               
               if r1.id_ente_giuridico is not null then
               
                  select DENOMINAZIONE_ENTE_GIURIDICO
                  into   v_DenomBen 
                  from   pbandi_t_ente_giuridico
                  where  id_ente_giuridico = r1.id_ente_giuridico;  
               
               end if;
             
             
             

               IF p_descbrevewidget = 'VALID' then

                  --select to_char(DT_DICHIARAZIONE)
                  select to_char(DT_DICHIARAZIONE,'dd/mm/yyyy')
                  into   v_dett_business
                  from   PBANDI_T_DICHIARAZIONE_SPESA
                  where  ID_DICHIARAZIONE_SPESA = V_LISTTASKPROG(x).id_business;

               end if;

                z_y:= z_y +1;
                V_LISTTASKPROGWIDBEN(z_y) := OBJTASKPROGWIDBEN
                                            (V_LISTTASKPROG(x).id_progetto,
                                             V_LISTTASKPROG(x).titolo_progetto,
                                             V_LISTTASKPROG(x).codice_visualizzato,
                                             V_LISTTASKPROG(x).descr_breve_task,
                                             V_LISTTASKPROG(x).descr_task,
                                             V_LISTTASKPROG(x).progr_bando_linea_intervento,
                                             V_LISTTASKPROG(x).nome_bando_linea,
                                             V_LISTTASKPROG(x).flag_opt,
                                             V_LISTTASKPROG(x).flag_lock,
                                             V_LISTTASKPROG(x).acronimo_progetto,
                                             V_LISTTASKPROG(x).id_business,
                                             V_LISTTASKPROG(x).id_notifica,
                                             V_LISTTASKPROG(x).denominazione_lock ,
                                             V_LISTTASKPROG(x).FLAG_RICH_ABILITAZ_UTENTE,
                                             p_descbrevewidget,
                                             r1.id_soggetto_beneficiario,    -- Rec_Attivita_Widget.Id_Soggetto,
                                             v_DenomBen ,  -- v_denominaz_ben
                                             v_dett_business
                                             );

             End if;
        END LOOP; --FOR
        pck_pbandi_processo.g_riga_progetti := pck_pbandi_processo.g_riga_progetti + 1;

     END LOOP; --C1






     -- Ordinamento task e filtro dei task
      DECLARE
         cursor c2 is
            SELECT a.* from TABLE (V_LISTTASKPROGWIDBEN) a
            Where a.ID_PROGETTO IS NOT NULL
              ORDER BY a.DESCR_TASK, a.ID_PROGETTO;
         v_x PLS_INTEGER := 0;

      BEGIN
         FOR r2 IN c2 LOOP
            IF v_x > 0 THEN
              V_LISTTASKPROGWIDBENORD.extend;
            END IF;
              v_x := v_x+1;
              V_LISTTASKPROGWIDBENORD(v_x) := OBJTASKPROGWIDBEN(r2.id_progetto,
                                               r2.titolo_progetto,
                                               r2.codice_visualizzato,
                                               r2.descr_breve_task,
                                               r2.descr_task,
                                               r2.progr_bando_linea_intervento,
                                               r2.nome_bando_linea,
                                               r2.flag_opt,
                                               r2.flag_lock,
                                               r2.acronimo_progetto,
                                               r2.id_business,
                                               r2.id_notifica,
                                               r2.denominazione_lock,
                                               r2.FLAG_RICH_ABILITAZ_UTENTE,
                                               r2.desc_breve_widget,
                                               r2.id_soggetto_beneficiario,
                                               r2.denom_benefiario,
                                               r2.dett_business
                                               );

         END LOOP;
      END;


RETURN V_LISTTASKPROGWIDBENORD;
END GetAttivitaProgetto_Widget;

------------------------------------------------------------------------------------------------------------------------

--
-- La funzionalità viene richiamata da java
-- Il get attività deve continuare a ciclare fino a quando la get progetti ritorna dei rprogetti
--
  FUNCTION GetProgetti (p_progr_bando_linea_intervento  IN  PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO%TYPE,
                        p_id_soggetto_ben               IN  PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE,
                        p_id_utente                     IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE) RETURN LISTPROG AS

    CURSOR c1 (c_progr_bando_linea_intervento   PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO%TYPE,
               c_id_soggetto_ben                PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE,
               c_id_soggetto                    PBANDI_T_UTENTE.ID_UTENTE%TYPE) IS

        select distinct id_progetto
              from PBANDI_V_PROGETTI_BEN_BL
              where progr_bando_linea_intervento = c_progr_bando_linea_intervento
              and id_soggetto_beneficiario = c_id_soggetto_ben
              and id_soggetto = c_id_soggetto
              order by  id_progetto;

    CURSOR c2 (c_id_progetto   PBANDI_T_PROGETTO.ID_PROGETTO%TYPE) IS
           select a.titolo_progetto,
                  a.codice_visualizzato,
                  d.acronimo_progetto
            from   PBANDI_T_PROGETTO a,
                   PBANDI_T_DOMANDA b,
                   PBANDI_T_RAGGRUPPAMENTO d
              where a.id_progetto = c_id_progetto
              and b.id_domanda = a.id_domanda
              and d.id_domanda(+) = nvl(b.id_domanda_padre,b.id_domanda);

     v_id_soggetto PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE;
     V_LISTPROG LISTPROG := LISTPROG(NULL);
     rec_prog c2%ROWTYPE;
     v_x PLS_INTEGER := 0;

  BEGIN
    BEGIN
       SELECT id_soggetto
         INTO v_id_soggetto
         FROM PBANDI_T_UTENTE
        WHERE id_utente = p_id_utente;
    EXCEPTION
      WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20000,'Errore accesso PBANDI_T_UTENTE '||p_id_utente||sqlerrm);
    END;

    FOR r1 IN c1 (p_progr_bando_linea_intervento,
                   p_id_soggetto_ben,
                   v_id_soggetto) LOOP
          -- Dati di progetto
          OPEN c2(r1.id_progetto);
          FETCH c2 into rec_prog;
          IF c2%NOTFOUND THEN
             RAISE_APPLICATION_ERROR(-20000,'Errore accesso ai dati del progetto '||r1.id_progetto);
          END IF;
          CLOSE c2;

          IF v_x > 0 THEN
            V_LISTPROG.extend;
          END IF;
          v_x := v_x+1;
          V_LISTPROG(v_x) := OBJPROG(r1.id_progetto,
                                     rec_prog.titolo_progetto,
                                     rec_prog.codice_visualizzato,
                                     rec_prog.acronimo_progetto);
     END LOOP;

     RETURN V_LISTPROG;
  END GetProgetti;
--------------------------------------------------------------------------------------------------------------------------------------
--
-- La funzionalità richiamata dal java per avere il numero di progetti
--

  FUNCTION CountProgettiBEN (p_progr_bando_linea_intervento  IN  PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO%TYPE,
                             p_id_soggetto_ben               IN  PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE,
                             p_id_utente                     IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE) RETURN INTEGER AS


    v_ritorno INTEGER := 0;
    v_id_soggetto PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE;

    CURSOR c1 (c_progr_bando_linea_intervento  IN  PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO%TYPE,
               c_id_soggetto_ben               IN  PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE,
               c_id_soggetto                   IN  PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE) IS


                select count(distinct id_progetto)
                      from PBANDI_V_PROGETTI_BEN_BL
                      where progr_bando_linea_intervento = c_progr_bando_linea_intervento
                      and id_soggetto_beneficiario = c_id_soggetto_ben
                      and id_soggetto = c_id_soggetto;
  BEGIN

    BEGIN
       SELECT id_soggetto
         INTO v_id_soggetto
         FROM PBANDI_T_UTENTE
        WHERE id_utente = p_id_utente;
    EXCEPTION
      WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20000,'Errore accesso PBANDI_T_UTENTE '||p_id_utente||sqlerrm);
    END;

    OPEN c1 (p_progr_bando_linea_intervento,p_id_soggetto_ben,v_id_soggetto);
    FETCH c1 INTO v_ritorno;
    CLOSE c1;

   RETURN v_ritorno;
 END CountProgettiBEN;

 ------------------------------------------------------------------------------------------------------------------------------
 -- la funzionalità sul progetto dato in input restituisce l'elenco dei task in base all'avanzamento del progetto.
 -- viene richiamata dal getattivitàben
 -- questa funzionalità costituisce il motore dell'applicativo
 --

 FUNCTION GetAttivita (p_id_progetto                       IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                       p_id_utente                         IN PBANDI_T_UTENTE.ID_UTENTE%TYPE,
                       p_desc_breve_tipo_anagrafica        IN PBANDI_D_TIPO_ANAGRAFICA.DESC_BREVE_TIPO_ANAGRAFICA%TYPE,
                       p_filtro_attivita                   IN VARCHAR2 DEFAULT NULL) RETURN LISTTASKPROG AS

    V_LISTTASKPROG LISTTASKPROG := LISTTASKPROG(NULL);
    v_id_step_processo_succ  INTEGER;

    CURSOR c1 (c_id_progetto   PBANDI_T_PROGETTO.ID_PROGETTO%TYPE) IS
       select a.titolo_progetto,
              a.codice_visualizzato,
              c.progr_bando_linea_intervento,
              c.nome_bando_linea,
              d.acronimo_progetto
        from   PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c,
               PBANDI_T_RAGGRUPPAMENTO d
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and d.id_domanda(+) = nvl(b.id_domanda_padre,b.id_domanda)
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;

    rec_prog c1%ROWTYPE;

    CURSOR c2 (c_id_processo PBANDI_T_STEP_PROCESSO.ID_STEP_PROCESSO%TYPE,
               c_id_task_prec PBANDI_D_TASK.ID_TASK%TYPE) IS
        SELECT a.id_task,
               b.descr_breve_task,
               b.descr_task,
               c.flag_opt,
               c.flag_public,
               c.descr_breve_tipo_task
          FROM PBANDI_T_STEP_PROCESSO a,
               PBANDI_D_TASK b,
               PBANDI_D_TIPO_TASK c
         WHERE a.id_processo = c_id_processo
           AND b.id_task = a.id_task
           AND c.id_tipo_task = b.id_tipo_task
           AND a.id_task_prec = c_id_task_prec;

         rec_task c2%ROWTYPE;

         v_id_task_prec PBANDI_D_TASK.ID_TASK%TYPE;


    v_x PLS_INTEGER := 0;
    v_id_task_succ PBANDI_D_TASK.ID_TASK%TYPE;
    v_descr_breve_tipo_task PBANDI_D_TIPO_TASK.DESCR_BREVE_TIPO_TASK%TYPE;

    v_controllo_tipo_task INTEGER;
    v_flag_continue VARCHAR2(1);

    TYPE CurTyp  IS REF CURSOR;
    v_cursor    CurTyp;
    invalid_identifier EXCEPTION;
    PRAGMA EXCEPTION_INIT(invalid_identifier, -904);

    v_cursor_notifica SYS_REFCURSOR;
    r_cursor_notifica   r_lista_notifiche;

    -- mc 02/2022
    vCount NUMBER;
    vFLAG_RICH_ABILITAZ_UTENTE VARCHAR2(1);
    --

-- Richiamata dal get attività
-- La funzione stabilisce il task di partenza -- sfruttando il metodo associato al task FN_PROG_2014_REP_2007
    FUNCTION RunRoot (p_id_processo   PBANDI_T_PROCESSO.ID_PROCESSO%TYPE,
                      p_id_progetto   PBANDI_T_PROGETTO.ID_PROGETTO%TYPE)  RETURN INTEGER AS


       CURSOR c1 (c_id_processo   PBANDI_T_PROCESSO.ID_PROCESSO%TYPE) IS
          SELECT descr_breve_metodo_task
            FROM PBANDI_T_STEP_PROCESSO a,
                 PBANDI_D_TASK b,
                 PBANDI_D_METODO_TASK c
            WHERE a.id_processo = c_id_processo
              AND a.id_task_prec IS NULL
              AND b.id_task = a.id_task
              AND c.id_metodo_task = b.id_metodo_task;

          v_nome_metodo VARCHAR2(30);
          v_id_step_processo_succ  INTEGER;


          v_stmt_str      VARCHAR2(200);

    BEGIN
       OPEN c1 (p_id_processo);
       FETCH c1 INTO v_nome_metodo;
       IF c1%NOTFOUND THEN
          RAISE_APPLICATION_ERROR (-20000, 'Manca metodo per il task di start '||p_id_processo);
       END IF;
       CLOSE c1;

       v_stmt_str := 'select pck_pbandi_processo.'||v_nome_metodo||' ('||p_id_progetto||','||p_id_processo||')  from dual';

       OPEN  v_cursor FOR v_stmt_str;
       FETCH v_cursor INTO v_id_task_succ;
       CLOSE v_cursor;
       RETURN     v_id_task_succ;

    EXCEPTION
       WHEN invalid_identifier THEN
          RAISE_APPLICATION_ERROR(-20000,'Metodo non previsto '||v_nome_metodo);
    END RunRoot;

--
-- Questa procedura nella navigazione esamina ogni task per determinare la disponibilità del task stesso

    PROCEDURE controllo_task(p_id_task IN PBANDI_D_TASK.ID_TASK%TYPE,
                             p_id_step_processo IN PBANDI_T_STEP_PROCESSO.ID_STEP_PROCESSO%TYPE,
                             p_descr_breve_tipo_task IN PBANDI_D_TIPO_TASK.DESCR_BREVE_TIPO_TASK%TYPE,
                             p_flag_da_processare IN VARCHAR2, --(S,N)
                             p_flag_opt IN PBANDI_D_TIPO_TASK.FLAG_OPT%TYPE,
                             p_id_processo   IN PBANDI_T_PROCESSO.ID_PROCESSO%TYPE,
                             p_id_progetto   IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                             p_flag_disponibile IN OUT NUMBER,  --Ritorna 1 se il task è disponibile
                             p_flag_break IN OUT NUMBER)  IS    -- Ritorna 1 se il ramo del processo deve essere interrotto poichè il task deve essere concluso



        CURSOR c2 (c_id_step_processo PBANDI_T_STEP_PROCESSO.ID_STEP_PROCESSO%TYPE,
                   c_id_progetto   PBANDI_T_PROGETTO.ID_PROGETTO%TYPE) IS
           SELECT dt_fine
             FROM PBANDI_T_ISTANZA_STEP_PROCESSO
            WHERE id_step_processo = c_id_step_processo
              AND id_progetto = c_id_progetto;

        rec_ist  c2%ROWTYPE;

       v_ritorno INTEGER := 1;
       v_id_task_GA_GO PBANDI_D_TASK.ID_TASK%TYPE;
--
--  Elaborazione della procedure controllo task
        FUNCTION FN_TIPO_TASK (p_id_task IN PBANDI_D_TASK.ID_TASK%TYPE) RETURN VARCHAR2 IS
           CURSOR c1 (c_id_task   PBANDI_D_TASK.ID_TASK%TYPE) IS
              SELECT descr_breve_tipo_task
                FROM PBANDI_D_TIPO_TASK a,
                     PBANDI_D_TASK b
                WHERE a.id_tipo_task = b.id_tipo_task
                AND b.id_task = c_id_task;

            v_ritorno  PBANDI_D_TIPO_TASK.DESCR_BREVE_TIPO_TASK%TYPE;

        BEGIN
           OPEN c1 (p_id_task);
           FETCH c1 INTO v_ritorno;
           CLOSE c1;
           RETURN v_ritorno;
        END FN_TIPO_TASK;

-- inizio procedura controllo task
    BEGIN
       -- Da non processare stabilito da metodo associato al task
       IF p_flag_da_processare    = 'N' THEN
          p_flag_break := 0; -- La navigazione continua
          p_flag_disponibile := 0; -- L'attività però non è disponibile
          RETURN;
       END IF;


       v_id_task_GA_GO := GetGatewayBindTask(g_id_processo,p_id_task);


       IF v_id_task_GA_GO IS NOT NULL  THEN -- TAsk vincolati al Gataway AND/OR
          OPEN c2(p_id_step_processo,   -- Verifico che l' attività  sia stata completata
                  p_id_progetto);
          FETCH c2 INTO rec_ist;
          IF c2%NOTFOUND OR rec_ist.dt_fine IS NULL THEN  -- Non è completata
                p_flag_break := 1; -- Basta che una sola attività del gruppo AND non sia completata
                p_flag_disponibile := 1;
          ELSE  -- L'attività è completata
               IF FN_TIPO_TASK(v_id_task_GA_GO) = 'GO' THEN
                p_flag_break := 0; -- Basta che una sola attività del gruppo OR  sia completata
             END IF;
               p_flag_disponibile := 0;
          END IF;
          CLOSE c2;
       ELSE -- TASK non vincolati
          OPEN c2(p_id_step_processo,
                  p_id_progetto);
          FETCH c2 INTO rec_ist;
          IF c2%NOTFOUND OR rec_ist.dt_fine IS NULL THEN
            p_flag_break := 1;
            p_flag_disponibile := 1;
          ELSE
            p_flag_break := 0;
            p_flag_disponibile := 0;
          END IF;
          CLOSE c2;

       END IF;

       -- Opzionali (Va sempre avanti nel processo)
       IF p_flag_opt = 'S' THEN
            p_flag_break := 0;
       END IF;

    END controllo_task;

--
--  Na

  PROCEDURE naviga_processo (p_id_processo  IN PBANDI_T_PROCESSO.ID_PROCESSO%TYPE,
                             p_id_task_prec IN PBANDI_D_TASK.ID_TASK%TYPE,
                             p_id_utente    IN PBANDI_T_UTENTE.ID_UTENTE%TYPE,
                             p_id_progetto  IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE) IS


  CURSOR c0 (c_id_processo PBANDI_T_STEP_PROCESSO.ID_STEP_PROCESSO%TYPE,
               c_id_task_prec PBANDI_D_TASK.ID_TASK%TYPE) IS
        select pck_pbandi_processo.GetGatewayBindTask(c_id_processo,a.id_task) group_task
        from PBANDI_T_STEP_PROCESSO a
         WHERE a.id_processo = c_id_processo
           AND a.id_task_prec = c_id_task_prec
           group by pck_pbandi_processo.GetGatewayBindTask(c_id_processo,a.id_task);

   CURSOR c1 (c_id_processo PBANDI_T_STEP_PROCESSO.ID_STEP_PROCESSO%TYPE,
               c_id_task_prec PBANDI_D_TASK.ID_TASK%TYPE,
               c_group_task PBANDI_D_TASK.ID_TASK%TYPE) IS
        SELECT a.id_task,
               a.id_step_processo,
               b.descr_breve_task,
               b.descr_task,
               c.flag_opt,
               c.flag_public,
               c.descr_breve_tipo_task,
               e.descr_breve_metodo_task,
               g.descr_breve_metodo_task descr_breve_metodo_task_integr,
               f.query_multi_task
          FROM PBANDI_T_STEP_PROCESSO a,
               PBANDI_D_TASK b,
               PBANDI_D_TIPO_TASK c,
               PBANDI_D_METODO_TASK e,
               PBANDI_D_MULTI_TASK f,
               PBANDI_D_METODO_TASK g
         WHERE a.id_processo = c_id_processo
           AND b.id_task = a.id_task
           AND c.id_tipo_task = b.id_tipo_task
           AND a.id_task_prec = c_id_task_prec
           AND e.id_metodo_task(+) = b.id_metodo_task
           AND g.id_metodo_task(+) = b.id_metodo_task_integr
           AND f.id_multi_task(+) = b.id_multi_task
           AND NVL(pck_pbandi_processo.GetGatewayBindTask(c_id_processo,a.id_task),0) = NVL(c_group_task,0);

         rec_task c1%ROWTYPE;

         v_id_task_prec PBANDI_D_TASK.ID_TASK%TYPE;
         v_flag_disponibile VARCHAR2(1);
         v_flag_break VARCHAR2(1);
         v_nome_metodo VARCHAR2(30);
         v_stmt_str      VARCHAR2(2000);

         v_da_processare VARCHAR2(1);

         v_tab_multi_id multi_id_ty;
         v_multi      NUMBER;

         v_query_multi_task VARCHAR2(2000);

         v_descr_task_integr VARCHAR2(2000);

         v_denominazione_lock VARCHAR2(2000);
         v_flag_lock          VARCHAR2(1);
 --  Funzione che filtra l'attività in base all'input dell'utente
 --
      FUNCTION filtro_task (p_descr_task      IN PBANDI_D_TASK.DESCR_TASK%TYPE,
                            p_filtro_attivita IN VARCHAR2) RETURN BOOLEAN IS
         v_return BOOLEAN;
      BEGIN
         IF p_filtro_attivita IS NULL THEN -- Nessun filtro
            v_return := TRUE;
         ELSE
            IF instr(UPPER(p_descr_task),UPPER(p_filtro_attivita)) = 1 THEN -- Inizia con
               v_return := TRUE;
            ELSE
               v_return := FALSE;
            END IF;
         END IF;
         RETURN v_return;
      END filtro_task;

   -- inizio naviga processo
      BEGIN
         FOR r0 IN c0 (g_id_processo,p_id_task_prec)  LOOP
         v_flag_break := 0; -- Inizializzo il flag che determina l'interruzione del processo
         OPEN c1 (g_id_processo,p_id_task_prec,r0.group_task);
         LOOP
            FETCH c1 INTO rec_task;
            EXIT WHEN c1%NOTFOUND;

            IF rec_task.descr_breve_tipo_task = 'GS'  THEN  --Gateway di scelta (Scelta del task successivo)

               IF rec_task.descr_breve_metodo_task IS NULL THEN
                  RAISE_APPLICATION_ERROR (-20000, 'Manca metodo per il task di scelta '||rec_task.id_task);
               ELSE
                  v_nome_metodo := rec_task.descr_breve_metodo_task;
                  v_stmt_str := 'select pck_pbandi_processo.'||v_nome_metodo||' ('||p_id_progetto||','||p_id_processo||')  from dual';

                  OPEN  v_cursor FOR v_stmt_str;
                  FETCH v_cursor INTO v_id_task_prec;
                  CLOSE v_cursor;
               END IF;

             naviga_processo (g_id_processo,v_id_task_prec,p_id_utente,p_id_progetto); --Ricorsiva

            ELSE
               v_da_processare := 'S'; -- Viene settato preventivamente a 'S' e sottoposto ad una procedura di controllo che potrebbe impostarlo a 'N'
               IF rec_task.descr_breve_tipo_task IN('TC','TOC')  THEN  --Task di controllo (viene eseguito un metodo per verificare se il task deve essere processato)
                  IF rec_task.descr_breve_metodo_task IS NOT NULL THEN -- Se c'è un metodo associato
                      v_nome_metodo := rec_task.descr_breve_metodo_task;
                      /*
                      v_stmt_str := 'select pck_pbandi_processo.'||v_nome_metodo||' ('||p_id_progetto||')  from dual';

                      OPEN  v_cursor FOR v_stmt_str;
                      FETCH v_cursor INTO v_da_processare;
                      CLOSE v_cursor;
                      */
                      --
                v_stmt_str :=
                    'BEGIN'||chr(10)||
                       ':v_da_processare := pck_pbandi_processo.'||v_nome_metodo||' ('||p_id_progetto||');'||chr(10)||
                    'END;';

                   EXECUTE IMMEDIATE v_stmt_str USING OUT v_da_processare;

                   --DBMS_OUTPUT.PUT_LINE(v_nome_metodo||'v_da_processare '||v_da_processare);

                      --

                  END IF;
                END IF;


               IF rec_task.flag_public = 'S'   -- Task pubblici per il processo (esclude i gateway e i task fittizi)
                    THEN

               -- Procedura che determina se il task è disponibile e se il ramo del processo deve essere interrotto poichè il task deve essere ancora concluso
                 controllo_task (rec_task.id_task,
                                rec_task.id_step_processo,
                                rec_task.descr_breve_tipo_task,
                                v_da_processare, -- se il task è da processare (METODO DI CONTROLLO)
                                rec_task.flag_opt,
                                g_id_processo,
                                p_id_progetto,
                                v_flag_disponibile,
                                v_flag_break);

                    IF v_flag_disponibile = 1
                       AND IsAbilitato(g_id_processo,rec_task.id_task,p_desc_breve_tipo_anagrafica)  -- se il task è abilitato per l'utente
                       AND filtro_task(rec_task.descr_task,p_filtro_attivita)  --Se c'è un filtro sulla descrizione
                       THEN

                        -- Integrazione descrizione task con parte specifica (Es. Richiesta di erogazione con causale)
                        IF rec_task.descr_breve_metodo_task_integr IS NOT NULL THEN -- Se c'è un metodo associato
                          v_nome_metodo := rec_task.descr_breve_metodo_task_integr;

                            v_stmt_str :=
                                'BEGIN'||chr(10)||
                                    ':v_descr_task_integr := pck_pbandi_processo.'||v_nome_metodo||' ('||p_id_progetto||');'||chr(10)||
                                'END;';

                               EXECUTE IMMEDIATE v_stmt_str USING OUT v_descr_task_integr;
                        ELSE
                           v_descr_task_integr := NULL;
                        END IF;

                        IF rec_task.query_multi_task IS NOT NULL THEN
                          v_query_multi_task := replace(rec_task.query_multi_task,'${id_progetto}',p_id_progetto);
                          v_query_multi_task := replace(v_query_multi_task,'${id_utente}',p_id_utente);
                          EXECUTE IMMEDIATE v_query_multi_task BULK COLLECT into v_tab_multi_id;
                          v_multi := v_tab_multi_id.COUNT; -- Multi-istanza
                        ELSE
                          v_multi := 1; -- Mono-Istanza
                          v_tab_multi_id := multi_id_ty(NULL);
                           v_tab_multi_id(1) := NULL;
                        END IF;

                        FOR x IN 1..v_multi  LOOP-- Gestisce la multi-istanza se prevista
                          IF v_x > 0 THEN
                            V_LISTTASKPROG.extend;
                          END IF;
                          v_x := v_x+1;
                          v_denominazione_lock := isLocked(p_id_progetto,rec_task.descr_breve_task,p_id_utente);

                          IF v_denominazione_lock IS NOT NULL THEN
                             v_flag_lock := 'S';
                          ELSE
                             v_flag_lock := '';
                          END IF;
                       -- mc 02/2022
                       -- integrazione per determinare se si tratta di un progetto
                       -- in cui ci sia in corso una richiesta di abilitazione utente

                          SELECT count(*)
                          INTO   vCount
                          FROM   pbandi_r_soggetto_progetto sp,
                                 pbandi_t_richiesta_abilitaz ra
                          WHERE  sp.progr_soggetto_progetto = ra.progr_soggetto_progetto
                          AND    ra.esito is null
                          AND    sp.id_progetto = p_id_progetto;

                          IF vCount > 0 THEN
                             vFLAG_RICH_ABILITAZ_UTENTE := 'S';
                          ELSE
                             vFLAG_RICH_ABILITAZ_UTENTE := 'N';
                          END IF;
                       --

                          V_LISTTASKPROG(v_x) := OBJTASKPROG(p_id_progetto,
                                                           rec_prog.titolo_progetto,
                                                           rec_prog.codice_visualizzato,
                                                           rec_task.descr_breve_task,
                                                           rec_task.descr_task||' '||v_descr_task_integr,
                                                           rec_prog.progr_bando_linea_intervento,
                                                           rec_prog.nome_bando_linea,
                                                           rec_task.flag_opt,
                                                           v_flag_lock, --flag_lock
                                                           rec_prog.acronimo_progetto,-- Acronimo_progetto
                                                           v_tab_multi_id(x),
                                                           NULL,  -- Id_notifica
                                                           v_denominazione_lock, --denominazione_lock
                                                           vFLAG_RICH_ABILITAZ_UTENTE -- mc 02/2022
                                                           );
                        END LOOP;
                    END IF;


                END IF;

            END IF;

            IF r0.group_task IS NULL  AND rec_task.descr_breve_tipo_task != 'GS' THEN -- Se il task non fa parte di un gruppo (AND/OR) navigo per ciascun task (Se non è un task di selezione)
                IF v_flag_break = 0 THEN
                  naviga_processo (g_id_processo,
                                   rec_task.id_task,
                                   p_id_utente,
                                   p_id_progetto); --Ricorsiva
               END IF;
            END IF;

         END LOOP;
         CLOSE c1;

        IF r0.group_task IS NOT  NULL THEN -- Se il task  fa parte di un gruppo (AND/OR) navigo nel processo dopo aver elaborato l'intero gruppo
          -- Interrompe il ramo del processo (deve essere concluso il task corrente per procedere al successivo)
               DBMS_OUTPUT.PUT_LINE('TASK GROUP'||r0.group_task);
           IF v_flag_break = 0 THEN
               naviga_processo (g_id_processo,
                               r0.group_task,
                               p_id_utente,
                               p_id_progetto); --Ricorsiva
           END IF;
        END IF;

        END LOOP; -- end loop GROUP

    EXCEPTION
       WHEN invalid_identifier THEN
          RAISE_APPLICATION_ERROR(-20000,'Metodo non previsto '||v_nome_metodo);
      END naviga_processo;

-- La funzionalità integra nella todo list i dati della notifica
--
      FUNCTION GetNotifica (p_id_progetto                 IN  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                            p_desc_breve_tipo_anagrafica  IN  PBANDI_D_TIPO_ANAGRAFICA.DESC_BREVE_TIPO_ANAGRAFICA%TYPE) RETURN SYS_REFCURSOR AS

        R_CURNOTIFICHE SYS_REFCURSOR;


      BEGIN

         OPEN R_CURNOTIFICHE FOR SELECT a.id_progetto,
                                        b.titolo_progetto,
                                        codice_visualizzato,
                                        id_notifica,
                                        stato_notifica,
                                        dt_notifica,
                                        subject_notifica,
                                        message_notifica,
                                        descr_breve_template_notifica,
                                        e.PROGR_BANDO_LINEA_INTERVENTO,
                                        NOME_BANDO_LINEA
                                FROM PBANDI_T_NOTIFICA_PROCESSO a,
                                         PBANDI_T_PROGETTO b,
                                         PBANDI_D_TEMPLATE_NOTIFICA c,
                                         PBANDI_T_DOMANDA d,
                                         PBANDI_R_BANDO_LINEA_INTERVENT e
                               WHERE D.ID_DOMANDA = b.id_domanda
                                 AND e.PROGR_BANDO_LINEA_INTERVENTO = D.PROGR_BANDO_LINEA_INTERVENTO
                                 AND a.id_progetto = p_id_progetto
                                 AND a.id_progetto = b.id_progetto
                                 AND c.id_template_notifica = a.id_template_notifica
                                 AND a.id_ruolo_di_processo_dest IN (SELECT id_ruolo_di_processo
                                                                       FROM PBANDI_R_RUOLO_TIPO_ANAGRAFICA a,
                                                                            PBANDI_D_TIPO_ANAGRAFICA b
                                                                      WHERE a.id_tipo_anagrafica = b.id_tipo_anagrafica
                                                                        AND desc_breve_tipo_anagrafica = p_desc_breve_tipo_anagrafica)
                                 AND stato_notifica != 'C';





      RETURN R_CURNOTIFICHE;

      END GetNotifica;
    --------------------
    -- MAIN GET_ATTIVITA
    --------------------
    BEGIN

      g_id_processo := pck_pbandi_processo.getprocesso(p_id_progetto);

      IF g_id_processo IS NULL THEN
        RAISE_APPLICATION_ERROR (-20000, 'Processo non valorizzato - Eseguire metodo GET_PROCESSO per il progetto '||p_id_progetto);
      END IF;

      -- Dati di progetto
      OPEN c1(p_id_progetto);
      FETCH c1 into rec_prog;
      IF c1%NOTFOUND THEN
         RAISE_APPLICATION_ERROR(-20000,'Errore accesso ai dati del progetto '||p_id_progetto);
      END IF;
      CLOSE c1;

      -- Cerco il task successivo a quello di start
      BEGIN
          SELECT id_task
            INTO v_id_task_succ
            FROM PBANDI_T_STEP_PROCESSO a
            WHERE a.id_processo = g_id_processo
              AND a.id_task_prec = (SELECT id_task
                                    FROM PBANDI_T_STEP_PROCESSO
                                    WHERE id_task_prec IS NULL
                                    AND id_processo = g_id_processo);
      EXCEPTION -- se ce n'è più di uno allora eseguo il metodo associato al task di scelta.
         WHEN TOO_MANY_ROWS THEN

            v_id_task_succ := RunRoot(g_id_processo,p_id_progetto);
      END;
      DBMS_OUTPUT.PUT_LINE('START '||v_id_task_succ);
      naviga_processo(g_id_processo,
                      v_id_task_succ,
                      p_id_utente,
                      p_id_progetto);


      -- Integrazione delle Notifiche
       v_cursor_notifica := GetNotifica (p_id_progetto,p_desc_breve_tipo_anagrafica);
        LOOP
          FETCH v_cursor_notifica INTO r_cursor_notifica;
          EXIT WHEN v_cursor_notifica%NOTFOUND;

          IF v_x > 0 THEN
            V_LISTTASKPROG.extend;
          END IF;
             v_x := v_x+1;
          V_LISTTASKPROG(v_x) := OBJTASKPROG(p_id_progetto,
                                           r_cursor_notifica.titolo_progetto,
                                           r_cursor_notifica.codice_visualizzato,
                                           r_cursor_notifica.descr_breve_template_notifica, -- descr_breve_task
                                           r_cursor_notifica.subject_notifica, --descr_task
                                           r_cursor_notifica.PROGR_BANDO_LINEA_INTERVENTO,
                                           r_cursor_notifica.NOME_BANDO_LINEA,
                                           NULL, --flag_opt,
                                           NULL, --flag_lock
                                           NULL, -- Acronimo_progetto
                                           NULL, -- Id_business
                                           r_cursor_notifica.id_notifica,
                                           NULL,--Denominazione_lock
                                           NULL --mc 02/2022
                                           );
        END LOOP;


  IF v_x = 0 THEN
     V_LISTTASKPROG.DELETE;
  END IF;


  RETURN V_LISTTASKPROG;
  END GetAttivita;

  --------------------------------------------------------------------------------------------------------------------------------------
  -- La funzione quando un utente clicca su una nuova attività registra il record su PBANDI_T_ISTANZA_STEP_PROCESSO
  -- e PBANDI_T_TASK_ACTION. Vengono gestiti i lock su attività aperte da altri utenti. Sulla task action, per l'attività
  -- definita dallo stesso utente sullo stesso task viene gestita  la continuazione creando un nuovo record.
  FUNCTION StartAttivita (p_id_progetto       IN  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                          p_descr_breve_task  IN  PBANDI_D_TASK.DESCR_BREVE_TASK%TYPE,
                          p_id_utente         IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE) RETURN INTEGER AS

    CURSOR c1 (c_id_processo        PBANDI_T_PROCESSO.ID_PROCESSO%TYPE,
               c_descr_breve_task   PBANDI_D_TASK.DESCR_BREVE_TASK%TYPE) IS

       SELECT id_step_processo
         FROM PBANDI_D_TASK a,
              PBANDI_T_STEP_PROCESSO b
        WHERE a.id_task = b.id_task
         AND descr_breve_task = c_descr_breve_task
         AND b.id_processo = c_id_processo;

    CURSOR c2 (c_id_progetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
               c_id_step_processo PBANDI_T_STEP_PROCESSO.id_step_processo%TYPE) IS

       SELECT id_istanza_step_processo
         FROM PBANDI_T_ISTANZA_STEP_PROCESSO
        WHERE id_progetto = c_id_progetto
          AND id_step_processo = c_id_step_processo;

    CURSOR c3 (c_id_istanza_step_processo PBANDI_T_ISTANZA_STEP_PROCESSO.id_istanza_step_processo%TYPE) IS

       SELECT id_task_action, id_utente
         FROM PBANDI_T_TASK_ACTION
        WHERE id_istanza_step_processo = c_id_istanza_step_processo
         AND dt_fine IS NULL;

    v_ritorno INTEGER := 0;
    v_id_step_processo PBANDI_T_STEP_PROCESSO.id_step_processo%TYPE;
    v_id_istanza_step_processo PBANDI_T_ISTANZA_STEP_PROCESSO.id_step_processo%TYPE;
    v_id_task_action PBANDI_T_TASK_ACTION.id_task_action%TYPE;
    v_id_utente_lock PBANDI_T_UTENTE.ID_UTENTE%TYPE;

  BEGIN

      g_id_processo := pck_pbandi_processo.getprocesso(p_id_progetto);

      IF g_id_processo IS NULL THEN
        RAISE_APPLICATION_ERROR (-20000, 'Processo non valorizzato - Eseguire metodo GET_PROCESSO per il progetto '||p_id_progetto);
      END IF;

      OPEN c1(g_id_processo,p_descr_breve_task);
      FETCH c1 INTO v_id_step_processo;
      IF c1%NOTFOUND THEN
        RAISE_APPLICATION_ERROR (-20000, 'Errore accesso  PBANDI_T_STEP_PROCESSO Processo: '||g_id_processo||' Task : '||p_descr_breve_task);
      END IF;
      CLOSE c1;

      OPEN c2 (p_id_progetto,
               v_id_step_processo);
      FETCH c2 INTO v_id_istanza_step_processo;
      IF c2%NOTFOUND THEN -- Inizio dell'attività
         BEGIN
             INSERT INTO PBANDI_T_ISTANZA_STEP_PROCESSO (ID_PROGETTO,ID_STEP_PROCESSO,DT_INIZIO)
             VALUES (p_id_progetto,v_id_step_processo,SYSDATE)
             RETURNING id_istanza_step_processo INTO v_id_istanza_step_processo;
         END;
         BEGIN
            INSERT INTO PBANDI_T_TASK_ACTION (ID_ISTANZA_STEP_PROCESSO,ID_UTENTE,DT_INIZIO)
            VALUES (v_id_istanza_step_processo,p_id_utente,SYSDATE);
         END;
      ELSE
         -- Continuazione dell'attività (verifico che il task non abbia un lock di un altro utente
         OPEN c3 (v_id_istanza_step_processo);
         FETCH c3 INTO v_id_task_action, v_id_utente_lock;
         IF c3%NOTFOUND THEN
            BEGIN
               INSERT INTO PBANDI_T_TASK_ACTION (ID_ISTANZA_STEP_PROCESSO,ID_UTENTE,DT_INIZIO)
               VALUES (v_id_istanza_step_processo,p_id_utente,SYSDATE);
            END;
         ELSE
            IF p_id_utente != v_id_utente_lock THEN
               v_ritorno := 1; -- Task lockato
            ELSE
               -- Se l'utente è lo stesso allora traccio il nuovo accesso
               UPDATE PBANDI_T_TASK_ACTION
                 SET dt_fine = sysdate
               WHERE id_task_action = v_id_task_action;

               INSERT INTO PBANDI_T_TASK_ACTION (ID_ISTANZA_STEP_PROCESSO,ID_UTENTE,DT_INIZIO)
               VALUES (v_id_istanza_step_processo,p_id_utente,SYSDATE);

            END IF;
         END IF;
         CLOSE c3;
      END IF;
      CLOSE c2;
      COMMIT;
  RETURN v_ritorno;
  END StartAttivita;
  --------------------------------------------------------------------------------------------------------------------------------------
  --
  -- La funzione viene richiamata dal java quando intercetta un 'abbondono attività' ad esempio quando l'utente clicca
  -- sul tasto 'torna elenco attività' in questo caso l'attività precedentemente lockata viene chiusa (data fine = sysdate) e rilasciata
  -- disponibile per altri utenti
  --
  FUNCTION UnlockAttivita (p_id_progetto       IN  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                           p_descr_breve_task  IN  PBANDI_D_TASK.DESCR_BREVE_TASK%TYPE,
                           p_id_utente         IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE) RETURN INTEGER AS

    CURSOR c1 (c_id_processo        PBANDI_T_PROCESSO.ID_PROCESSO%TYPE,
               c_descr_breve_task   PBANDI_D_TASK.DESCR_BREVE_TASK%TYPE) IS

       SELECT id_step_processo
         FROM PBANDI_D_TASK a,
              PBANDI_T_STEP_PROCESSO b
        WHERE a.id_task = b.id_task
         AND descr_breve_task = c_descr_breve_task
         AND b.id_processo = c_id_processo;

    CURSOR c2 (c_id_progetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
               c_id_step_processo PBANDI_T_STEP_PROCESSO.id_step_processo%TYPE) IS

       SELECT id_istanza_step_processo
         FROM PBANDI_T_ISTANZA_STEP_PROCESSO
        WHERE id_progetto = c_id_progetto
          AND id_step_processo = c_id_step_processo;

    CURSOR c3 (c_id_istanza_step_processo PBANDI_T_ISTANZA_STEP_PROCESSO.id_istanza_step_processo%TYPE) IS

       SELECT id_task_action, id_utente
         FROM PBANDI_T_TASK_ACTION
        WHERE id_istanza_step_processo = c_id_istanza_step_processo
         AND dt_fine IS NULL;

    v_ritorno INTEGER := 0;
    v_id_step_processo PBANDI_T_STEP_PROCESSO.id_step_processo%TYPE;
    v_id_istanza_step_processo PBANDI_T_ISTANZA_STEP_PROCESSO.id_step_processo%TYPE;
    v_id_task_action PBANDI_T_TASK_ACTION.id_task_action%TYPE;
    v_id_utente_lock PBANDI_T_UTENTE.ID_UTENTE%TYPE;

  BEGIN

      g_id_processo := pck_pbandi_processo.getprocesso(p_id_progetto);

      IF g_id_processo IS NULL THEN
        RAISE_APPLICATION_ERROR (-20000, 'Processo non valorizzato - Eseguire metodo GET_PROCESSO ');
      END IF;

      OPEN c1(g_id_processo,p_descr_breve_task);
      FETCH c1 INTO v_id_step_processo;
      IF c1%NOTFOUND THEN
        RAISE_APPLICATION_ERROR (-20000, 'Errore accesso  PBANDI_T_STEP_PROCESSO Processo: '||g_id_processo||' Task : '||p_descr_breve_task);
      END IF;
      CLOSE c1;

      OPEN c2 (p_id_progetto,
               v_id_step_processo);
      FETCH c2 INTO v_id_istanza_step_processo;
      IF c2%NOTFOUND THEN -- Attività non esistente
         v_ritorno := 2; -- Attività non ancora istanziata per il processo
      ELSE
         -- Verifico che il task non abbia un lock di un altro utente
         OPEN c3 (v_id_istanza_step_processo);
         FETCH c3 INTO v_id_task_action, v_id_utente_lock;
         IF c3%NOTFOUND THEN
            v_ritorno := 3; -- Attività non lockata
         ELSE
            IF p_id_utente != v_id_utente_lock THEN
               v_ritorno := 1; -- Task lockato da altro utente
            ELSE
               UPDATE PBANDI_T_TASK_ACTION
                 SET dt_fine = sysdate
               WHERE id_task_action = v_id_task_action;
            END IF;
         END IF;
         CLOSE c3;
      END IF;
      CLOSE c2;
      COMMIT;
  RETURN v_ritorno;
  END UnlockAttivita;
  --------------------------------------------------------------------------------------------------------------------------------------
  --
  --LA  funzione viene richiamata per chiudere l'attività
  --SE PER IL TASK DA CHIUDERE E' PRESENTE UN METODO DI CONTROLLO ASSOCIATO CON FLAG_END_ATTIVITA' = S
  --ALLORA ESEGUO IL METODO. sOLITAMENTE CHIUDE ALTRI TASK
  --SE L'ATTIVITA' NON E' PRESENTE LA APRO E LA CHIUDO D'UFFICIO. LA FUNZIONE EFFETTUA ANCHE CONTROLLI SUI LOCK PRESENTI.
  --
  FUNCTION EndAttivita (p_id_progetto       IN  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                        p_descr_breve_task  IN  PBANDI_D_TASK.DESCR_BREVE_TASK%TYPE,
                        p_id_utente         IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE,
                        p_flag_forzatura    IN VARCHAR2 DEFAULT NULL) RETURN INTEGER AS

    CURSOR c1 (c_id_processo        PBANDI_T_PROCESSO.ID_PROCESSO%TYPE,
               c_descr_breve_task   PBANDI_D_TASK.DESCR_BREVE_TASK%TYPE,
               c_id_progetto        PBANDI_T_PROGETTO.ID_PROGETTO%TYPE) IS

       SELECT b.id_step_processo,
              descr_breve_metodo_task,
              flag_chiamata_end,
              d.dt_fine
         FROM PBANDI_D_TASK a,
              PBANDI_T_STEP_PROCESSO b,
              PBANDI_D_METODO_TASK c,
              PBANDI_T_ISTANZA_STEP_PROCESSO d
        WHERE a.id_task = b.id_task
         AND descr_breve_task = c_descr_breve_task
         AND b.id_processo = c_id_processo
         AND c.id_metodo_task(+) = a.id_metodo_task
         AND d.id_progetto (+) = c_id_progetto
         AND d.id_step_processo(+) = b.id_step_processo;

    rec_task c1%ROWTYPE;



    CURSOR c2 (c_id_progetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
               c_id_step_processo PBANDI_T_STEP_PROCESSO.id_step_processo%TYPE) IS

       SELECT id_istanza_step_processo,
              dt_fine
         FROM PBANDI_T_ISTANZA_STEP_PROCESSO
        WHERE id_progetto = c_id_progetto
          AND id_step_processo = c_id_step_processo;

    CURSOR c3 (c_id_istanza_step_processo PBANDI_T_ISTANZA_STEP_PROCESSO.id_istanza_step_processo%TYPE) IS

       SELECT id_task_action, id_utente
         FROM PBANDI_T_TASK_ACTION
        WHERE id_istanza_step_processo = c_id_istanza_step_processo
         AND dt_fine IS NULL;

    v_ritorno INTEGER := 0;
    v_id_step_processo PBANDI_T_STEP_PROCESSO.id_step_processo%TYPE;
    v_dt_fine PBANDI_T_ISTANZA_STEP_PROCESSO.dt_fine%TYPE;
    v_id_istanza_step_processo PBANDI_T_ISTANZA_STEP_PROCESSO.id_step_processo%TYPE;
    v_id_task_action PBANDI_T_TASK_ACTION.id_task_action%TYPE;
    v_id_utente_lock PBANDI_T_UTENTE.ID_UTENTE%TYPE;
    v_nome_metodo VARCHAR2(30);

    TYPE CurTyp  IS REF CURSOR;
    v_cursor    CurTyp;
    v_stmt_str      VARCHAR2(200);

    v_da_processare VARCHAR2(1) := 'S';

  BEGIN

      g_id_processo := pck_pbandi_processo.getprocesso(p_id_progetto);

      IF g_id_processo IS NULL THEN
        RAISE_APPLICATION_ERROR (-20000, 'Processo non valorizzato - Eseguire metodo GET_PROCESSO per il progetto '||p_id_progetto);
      END IF;

      OPEN c1(g_id_processo,p_descr_breve_task,p_id_progetto);
      FETCH c1 INTO rec_task;
      IF c1%FOUND THEN
--    IF c1%NOTFOUND THEN
--        RAISE_APPLICATION_ERROR (-20000, 'Errore accesso  PBANDI_T_STEP_PROCESSO Processo: '||g_id_processo||' Task : '||p_descr_breve_task);

		  --Se presente esegue un metodo di controllo associato al task
		  IF rec_task.descr_breve_metodo_task IS NOT NULL
			 AND rec_task.flag_chiamata_end = 'S'
			  AND rec_task.dt_fine IS NULL  -- Se c'è un metodo associato ed è eseguibile da EndAttivita e l'attività non è già stata chiusa
			   AND p_flag_forzatura IS NULL THEN -- Solo se il flag non è Forzatura per evitare i Loop di EndAttivita
			  v_nome_metodo := rec_task.descr_breve_metodo_task;
			  /*
			  v_stmt_str := 'select pck_pbandi_processo.'||v_nome_metodo||' ('||p_id_progetto||','||p_id_utente||',''END'')  from dual';

			  OPEN  v_cursor FOR v_stmt_str;
			  FETCH v_cursor INTO v_da_processare; --S= L'attività si può chiudere
			  CLOSE v_cursor;
			 */
				v_stmt_str :=
					'BEGIN'||chr(10)||
					   ':v_da_processare := pck_pbandi_processo.'||v_nome_metodo||' ('||p_id_progetto||','||p_id_utente||',''END'');'||chr(10)||
					'END;';

			  EXECUTE IMMEDIATE v_stmt_str USING OUT v_da_processare;
		  END IF;
		  --


		  v_id_step_processo := rec_task.id_step_processo;

		  OPEN c2 (p_id_progetto,
				   v_id_step_processo);
		  FETCH c2 INTO v_id_istanza_step_processo,
						v_dt_fine;
		  IF c2%NOTFOUND THEN -- Attività non esistente
			 IF p_flag_forzatura = 'S' THEN -- Apro d'ufficio l'attività per poi chiuderla

				 BEGIN
					 INSERT INTO PBANDI_T_ISTANZA_STEP_PROCESSO (ID_PROGETTO,ID_STEP_PROCESSO,DT_INIZIO,DT_FINE,FLAG_FORZATURA)
					 VALUES (p_id_progetto,v_id_step_processo,SYSDATE,SYSDATE,p_flag_forzatura)
					 RETURNING id_istanza_step_processo INTO v_id_istanza_step_processo;
				 END;
				 BEGIN
					INSERT INTO PBANDI_T_TASK_ACTION (ID_ISTANZA_STEP_PROCESSO,ID_UTENTE,DT_INIZIO,DT_FINE)
					VALUES (v_id_istanza_step_processo,p_id_utente,SYSDATE,SYSDATE);
				 END;

			 ELSE
				v_ritorno := 2; -- Attività non ancora istanziata per il progetto
			 END IF;

		  ELSIF v_dt_fine IS NOT NULL THEN
			NULL; -- Nessuna azione per task già chiuso
		  ELSE

			 IF v_da_processare = 'S'  THEN  -- Se da processare
			 -- Verifico che il task non abbia un lock di un altro utente
				 OPEN c3 (v_id_istanza_step_processo);
				 FETCH c3 INTO v_id_task_action, v_id_utente_lock;
				 IF c3%NOTFOUND THEN -- Attività non lockata
					UPDATE PBANDI_T_ISTANZA_STEP_PROCESSO
					   SET dt_fine = sysdate
					 WHERE id_istanza_step_processo = v_id_istanza_step_processo;
				 ELSE
					IF (p_id_utente != v_id_utente_lock) AND (p_flag_forzatura is null) THEN
					   v_ritorno := 1; -- Task lockato da altro utente
					ELSE
					   UPDATE PBANDI_T_TASK_ACTION
						 SET dt_fine = sysdate
					   WHERE id_task_action = v_id_task_action;

						UPDATE PBANDI_T_ISTANZA_STEP_PROCESSO
						   SET dt_fine = sysdate
						 WHERE id_istanza_step_processo = v_id_istanza_step_processo;
					END IF;
				 END IF;
				 CLOSE c3;
			 ELSE -- Non processato per dati del businness con cogruenti con la chiusura
				IF v_da_processare = 'E' THEN
				   v_ritorno := 4; -- errore sul calcolo delle economie -- mc sviluppo marzo 2020
				ELSE
				   v_ritorno := 3;
				END IF;
			 END IF;
		  END IF;
		  CLOSE c2;
		  COMMIT;
      END IF;
      CLOSE c1;
  RETURN v_ritorno;
  END EndAttivita;
  --------------------------------------------------------------------------------------------------------------------------------------
  --------------------------------------------------------------------------------------------------------------------------------------
  --
  --LA  funzione viene richiamata per chiudere l'attività
  --SE PER IL TASK DA CHIUDERE E' PRESENTE UN METODO DI CONTROLLO ASSOCIATO CON FLAG_END_ATTIVITA' = S
  --ALLORA ESEGUO IL METODO. sOLITAMENTE CHIUDE ALTRI TASK
  --SE L'ATTIVITA' NON E' PRESENTE LA APRO E LA CHIUDO D'UFFICIO. LA FUNZIONE EFFETTUA ANCHE CONTROLLI SUI LOCK PRESENTI.
  -- mc sviluppo settembre 2021
      -- si tratta di un clone della fnc EndAttività senza il commit in quanto la procedura chiamante
      -- deve verificare l'esito delle singole chiusure a cascata prima di committare
  --
  FUNCTION EndAttivitaNoCommit
                       (p_id_progetto       IN  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                        p_descr_breve_task  IN  PBANDI_D_TASK.DESCR_BREVE_TASK%TYPE,
                        p_id_utente         IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE,
                        p_flag_forzatura    IN VARCHAR2 DEFAULT NULL) RETURN INTEGER AS

    CURSOR c1 (c_id_processo        PBANDI_T_PROCESSO.ID_PROCESSO%TYPE,
               c_descr_breve_task   PBANDI_D_TASK.DESCR_BREVE_TASK%TYPE,
               c_id_progetto        PBANDI_T_PROGETTO.ID_PROGETTO%TYPE) IS

       SELECT b.id_step_processo,
              descr_breve_metodo_task,
              flag_chiamata_end,
              d.dt_fine
         FROM PBANDI_D_TASK a,
              PBANDI_T_STEP_PROCESSO b,
              PBANDI_D_METODO_TASK c,
              PBANDI_T_ISTANZA_STEP_PROCESSO d
        WHERE a.id_task = b.id_task
         AND descr_breve_task = c_descr_breve_task
         AND b.id_processo = c_id_processo
         AND c.id_metodo_task(+) = a.id_metodo_task
         AND d.id_progetto (+) = c_id_progetto
         AND d.id_step_processo(+) = b.id_step_processo;

    rec_task c1%ROWTYPE;



    CURSOR c2 (c_id_progetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
               c_id_step_processo PBANDI_T_STEP_PROCESSO.id_step_processo%TYPE) IS

       SELECT id_istanza_step_processo,
              dt_fine
         FROM PBANDI_T_ISTANZA_STEP_PROCESSO
        WHERE id_progetto = c_id_progetto
          AND id_step_processo = c_id_step_processo;

    CURSOR c3 (c_id_istanza_step_processo PBANDI_T_ISTANZA_STEP_PROCESSO.id_istanza_step_processo%TYPE) IS

       SELECT id_task_action, id_utente
         FROM PBANDI_T_TASK_ACTION
        WHERE id_istanza_step_processo = c_id_istanza_step_processo
         AND dt_fine IS NULL;

    v_ritorno INTEGER := 0;
    v_id_step_processo PBANDI_T_STEP_PROCESSO.id_step_processo%TYPE;
    v_dt_fine PBANDI_T_ISTANZA_STEP_PROCESSO.dt_fine%TYPE;
    v_id_istanza_step_processo PBANDI_T_ISTANZA_STEP_PROCESSO.id_step_processo%TYPE;
    v_id_task_action PBANDI_T_TASK_ACTION.id_task_action%TYPE;
    v_id_utente_lock PBANDI_T_UTENTE.ID_UTENTE%TYPE;
    v_nome_metodo VARCHAR2(30);

    TYPE CurTyp  IS REF CURSOR;
    v_cursor    CurTyp;
    v_stmt_str      VARCHAR2(200);

    v_da_processare VARCHAR2(1) := 'S';

  BEGIN

      g_id_processo := pck_pbandi_processo.getprocesso(p_id_progetto);

      IF g_id_processo IS NULL THEN
        RAISE_APPLICATION_ERROR (-20000, 'Processo non valorizzato - Eseguire metodo GET_PROCESSO per il progetto '||p_id_progetto);
      END IF;

      OPEN c1(g_id_processo,p_descr_breve_task,p_id_progetto);
      FETCH c1 INTO rec_task;
      IF c1%FOUND THEN
--    IF c1%NOTFOUND THEN
--        RAISE_APPLICATION_ERROR (-20000, 'Errore accesso  PBANDI_T_STEP_PROCESSO Processo: '||g_id_processo||' Task : '||p_descr_breve_task);

		  --Se presente esegue un metodo di controllo associato al task
		  IF rec_task.descr_breve_metodo_task IS NOT NULL
			 AND rec_task.flag_chiamata_end = 'S'
			  AND rec_task.dt_fine IS NULL  -- Se c'è un metodo associato ed è eseguibile da EndAttivita e l'attività non è già stata chiusa
			   AND p_flag_forzatura IS NULL THEN -- Solo se il flag non è Forzatura per evitare i Loop di EndAttivita
			  v_nome_metodo := rec_task.descr_breve_metodo_task;
			  /*
			  v_stmt_str := 'select pck_pbandi_processo.'||v_nome_metodo||' ('||p_id_progetto||','||p_id_utente||',''END'')  from dual';

			  OPEN  v_cursor FOR v_stmt_str;
			  FETCH v_cursor INTO v_da_processare; --S= L'attività si può chiudere
			  CLOSE v_cursor;
			 */
				v_stmt_str :=
					'BEGIN'||chr(10)||
					   ':v_da_processare := pck_pbandi_processo.'||v_nome_metodo||' ('||p_id_progetto||','||p_id_utente||',''END'');'||chr(10)||
					'END;';

			  EXECUTE IMMEDIATE v_stmt_str USING OUT v_da_processare;
		  END IF;
		  --


		  v_id_step_processo := rec_task.id_step_processo;

		  OPEN c2 (p_id_progetto,
				   v_id_step_processo);
		  FETCH c2 INTO v_id_istanza_step_processo,
						v_dt_fine;
		  IF c2%NOTFOUND THEN -- Attività non esistente
			 IF p_flag_forzatura = 'S' THEN -- Apro d'ufficio l'attività per poi chiuderla

				 BEGIN
					 INSERT INTO PBANDI_T_ISTANZA_STEP_PROCESSO (ID_PROGETTO,ID_STEP_PROCESSO,DT_INIZIO,DT_FINE,FLAG_FORZATURA)
					 VALUES (p_id_progetto,v_id_step_processo,SYSDATE,SYSDATE,p_flag_forzatura)
					 RETURNING id_istanza_step_processo INTO v_id_istanza_step_processo;
				 END;
				 BEGIN
					INSERT INTO PBANDI_T_TASK_ACTION (ID_ISTANZA_STEP_PROCESSO,ID_UTENTE,DT_INIZIO,DT_FINE)
					VALUES (v_id_istanza_step_processo,p_id_utente,SYSDATE,SYSDATE);
				 END;

			 ELSE
				v_ritorno := 2; -- Attività non ancora istanziata per il progetto
			 END IF;

		  ELSIF v_dt_fine IS NOT NULL THEN
			NULL; -- Nessuna azione per task già chiuso
		  ELSE

			 IF v_da_processare = 'S'  THEN  -- Se da processare
			 -- Verifico che il task non abbia un lock di un altro utente
				 OPEN c3 (v_id_istanza_step_processo);
				 FETCH c3 INTO v_id_task_action, v_id_utente_lock;
				 IF c3%NOTFOUND THEN -- Attività non lockata
					UPDATE PBANDI_T_ISTANZA_STEP_PROCESSO
					   SET dt_fine = sysdate
					 WHERE id_istanza_step_processo = v_id_istanza_step_processo;
				 ELSE
					IF (p_id_utente != v_id_utente_lock) AND (p_flag_forzatura is null) THEN
					   v_ritorno := 1; -- Task lockato da altro utente
					ELSE
					   UPDATE PBANDI_T_TASK_ACTION
						 SET dt_fine = sysdate
					   WHERE id_task_action = v_id_task_action;

						UPDATE PBANDI_T_ISTANZA_STEP_PROCESSO
						   SET dt_fine = sysdate
						 WHERE id_istanza_step_processo = v_id_istanza_step_processo;
					END IF;
				 END IF;
				 CLOSE c3;
			 ELSE -- Non processato per dati del businness con cogruenti con la chiusura
				IF v_da_processare = 'E' THEN
				   v_ritorno := 4; -- errore sul calcolo delle economie -- mc sviluppo marzo 2020
				ELSE
				   v_ritorno := 3;
				END IF;
			 END IF;
		  END IF;
		  CLOSE c2;
		  -- COMMIT;
      END IF;
      CLOSE c1;
  RETURN v_ritorno;
  END EndAttivitaNoCommit;
  --------------------------------------------------------------------------------------------------------------------------------------
  FUNCTION FN_CALCOLO_ECONOMIE (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL)RETURN NUMBER AS
   -- ATTIVITA' LEGATA AL TASK CHIUSURA-PROGETTO
   -- PRIMA DI CHIUDERE I TASK VIENE EFFETTUATO IL CALCOLO DELLE ECONOMIE
   -- SE LA PROCEDURA VA IN ERRORE LA CHIUSURA DEI TASK NON VIENE EFFETTUATA
   -- VIENE RESTITUITO UN MESSAGGIO ALL'UTENTE
   v_return NUMBER:= 0;
   vNomeBatch                      PBANDI_D_NOME_BATCH.NOME_BATCH%TYPE := 'PBAN-CALCOLA-ECONOMIE';
   nIdProcessoBatch                PBANDI_L_PROCESSO_BATCH.id_processo_batch%TYPE := PCK_PBANDI_UTILITY_BATCH.insprocbatch(vNomeBatch);
   v_Flag_Aiuti                    varchar2(1);
   v_ImportoEconomia               NUMBER(17,2);
   v_ImportoAmmesso                PBANDI_R_PROG_SOGG_FINANZIAT.IMP_QUOTA_SOGG_FINANZIATORE%TYPE;
   v_ImportoValidato               PBANDI_R_PAG_QUOT_PARTE_DOC_SP.IMPORTO_VALIDATO%TYPE;
   v_ImportoRecupero               PBANDI_T_RECUPERO.IMPORTO_RECUPERO%TYPE;
   v_IdEconomia                    PBANDI_T_ECONOMIE.ID_ECONOMIA%TYPE;
   -- mc sviluppo maggio 2020
   v_max_IdSoggettoFinanziatore    PBANDI_R_PROG_SOGG_FINANZIAT.ID_SOGGETTO_FINANZIATORE%TYPE;
   v_temp_SommaPercSoggFinanz      PBANDI_R_ECONOM_SOGG_FINANZIAT.IMP_QUOTA_ECON_SOGG_FINANZIAT%TYPE;
   v_temp_SottrazioneUltimoImp     PBANDI_R_ECONOM_SOGG_FINANZIAT.IMP_QUOTA_ECON_SOGG_FINANZIAT%TYPE;
   v_count                         NUMBER;
   --

  BEGIN


     SELECT DECODE (tipoOp.DESC_BREVE_TIPO_OPERAZIONE, '03', 'S', 'N')
     INTO v_Flag_Aiuti
     FROM PBANDI_T_PROGETTO prog,
          PBANDI_D_TIPO_OPERAZIONE tipoOp
     WHERE prog.id_tipo_operazione = tipoOp.Id_Tipo_Operazione and
           prog.id_progetto = p_id_progetto;

     IF v_Flag_Aiuti = 'S' THEN
       -- v_ImportoEconomia = v_ImportoAmmesso ? v_ImportoValidato
       Begin
         Select NVL(SUM (IMP_QUOTA_SOGG_FINANZIATORE),0)
         INTO   v_ImportoAmmesso
         FROM   PBANDI_R_PROG_SOGG_FINANZIAT
         WHERE  id_progetto = p_id_progetto;
       Exception when no_data_found then
         v_ImportoAmmesso:= 0;
       End;
       --
       Begin
          Select NVL(SUM (IMPORTO_VALIDATO ),0)
          INTO   v_ImportoValidato
          FROM   PBANDI_R_PAG_QUOT_PARTE_DOC_SP pqpds,
                 PBANDI_T_QUOTA_PARTE_DOC_SPESA pds
          WHERE  pds.id_progetto = p_id_progetto
          AND    pds.id_quota_parte_doc_spesa = pqpds.id_quota_parte_doc_spesa;
       Exception when no_data_found then
         v_ImportoValidato:= 0;
       End;
       --
       v_ImportoEconomia := v_ImportoAmmesso - v_ImportoValidato;

       --
     ELSE
       --
       Begin
         SELECT SUM(IMPORTO_RECUPERO)
         INTO  v_ImportoRecupero
         FROM  PBANDI_T_RECUPERO
         WHERE ID_PROGETTO = p_id_progetto
         AND   ID_TIPO_RECUPERO = 1;
       Exception when no_data_found then
         v_ImportoRecupero:= 0;
       End;
       --
       v_ImportoEconomia := v_ImportoRecupero;
       --
     END IF;

     IF v_ImportoEconomia > 0 THEN
        --
         INSERT INTO PBANDI_T_ECONOMIE
          (ID_ECONOMIA,
           ID_PROGETTO_CEDENTE,
           DATA_INSERIMENTO,
           ID_UTENTE_INS)
         VALUES
          (SEQ_PBANDI_T_ECONOMIE.NEXTVAL,
           p_id_progetto,
           sysdate,
           p_id_utente)returning ID_ECONOMIA into v_IdEconomia;
        --
        -- gestione calcolo dei decimali
        Begin
            SELECT NVL(MAX(ID_SOGGETTO_FINANZIATORE),0)
            INTO   v_max_IdSoggettoFinanziatore
            FROM   PBANDI_R_PROG_SOGG_FINANZIAT
            WHERE  ID_PROGETTO = p_id_progetto;
        Exception When no_data_found then
            v_max_IdSoggettoFinanziatore:= 0;
        End;
        --
         For Rec_SoggFinanz IN
           (SELECT ID_SOGGETTO_FINANZIATORE,
                   PERC_QUOTA_SOGG_FINANZIATORE
            FROM   PBANDI_R_PROG_SOGG_FINANZIAT
            WHERE  ID_PROGETTO = p_id_progetto
            ORDER BY ID_SOGGETTO_FINANZIATORE)
            LOOP

            If Rec_SoggFinanz.ID_SOGGETTO_FINANZIATORE <> v_max_IdSoggettoFinanziatore then

              INSERT INTO PBANDI_R_ECONOM_SOGG_FINANZIAT
                  ( ID_ECONOMIA,
                    ID_SOGGETTO_FINANZIATORE ,
                    PERC_QUOTA_SOGG_FINANZIAT,
                    IMP_QUOTA_ECON_SOGG_FINANZIAT,
                    ID_UTENTE_INS ,
                    DT_INSERIMENTO,
                    TIPOLOGIA_PROGETTO)
                  VALUES
                    (v_IdEconomia,
                     Rec_SoggFinanz.ID_SOGGETTO_FINANZIATORE,
                     Rec_SoggFinanz.PERC_QUOTA_SOGG_FINANZIATORE,
                     (v_ImportoEconomia * Rec_SoggFinanz.PERC_QUOTA_SOGG_FINANZIATORE) / 100,
                     p_id_utente,
                     sysdate,
                     'C'
                    );

               v_temp_SommaPercSoggFinanz:= NVL(v_temp_SommaPercSoggFinanz,0) + ((v_ImportoEconomia * Rec_SoggFinanz.PERC_QUOTA_SOGG_FINANZIATORE) / 100);

            else

              SELECT count(*)
              INTO   v_count
              FROM   PBANDI_R_PROG_SOGG_FINANZIAT
              WHERE  ID_PROGETTO = p_id_progetto;

              IF v_count > 1 then
               v_temp_SottrazioneUltimoImp := v_ImportoEconomia - v_temp_SommaPercSoggFinanz;
              else
               v_temp_SottrazioneUltimoImp := (v_ImportoEconomia * Rec_SoggFinanz.PERC_QUOTA_SOGG_FINANZIATORE) / 100;
              end if;

               INSERT INTO PBANDI_R_ECONOM_SOGG_FINANZIAT
                  ( ID_ECONOMIA,
                    ID_SOGGETTO_FINANZIATORE ,
                    PERC_QUOTA_SOGG_FINANZIAT,
                    IMP_QUOTA_ECON_SOGG_FINANZIAT,
                    ID_UTENTE_INS ,
                    DT_INSERIMENTO,
                    TIPOLOGIA_PROGETTO)
                  VALUES
                    (v_IdEconomia,
                     Rec_SoggFinanz.ID_SOGGETTO_FINANZIATORE,
                     Rec_SoggFinanz.PERC_QUOTA_SOGG_FINANZIATORE,
                     --(v_ImportoEconomia * Rec_SoggFinanz.PERC_QUOTA_SOGG_FINANZIATORE) / 100,
                     v_temp_SottrazioneUltimoImp,
                     p_id_utente,
                     sysdate,
                     'C'
                    );

             End if;

            END LOOP;



     END IF;

    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'OK');
    RETURN v_return;

  EXCEPTION WHEN OTHERS THEN
   PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
   PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E177', SQLERRM ||' ERRORE SU:  FN_CALCOLO_ECONOMIE ID PROGETTO '||p_id_progetto|| ' riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
   --RAISE_APPLICATION_ERROR(-20000,'Si è verificato un errore nel calcolo delle economie del progetto, pertanto non è stato possibile chiudere il progetto. Contattare l''Assistenza.');
   v_return:= -1;
   RETURN v_return;
  END FN_CALCOLO_ECONOMIE;
  --------------------------------------------------------------------------------------------------------------------------------------
  FUNCTION FN_PROG_2014_REP_2007 (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                  p_id_processo IN PBANDI_T_PROCESSO.ID_PROCESSO%TYPE) RETURN INTEGER AS

       v_nome_task            VARCHAR2(30);
       v_id_task_succ  PBANDI_D_TASK.ID_TASK%TYPE;



       FUNCTION isSchedaProgetto (p_id_progetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE) RETURN BOOLEAN IS
          v_count NUMBER;
       BEGIN
          SELECT COUNT(*)
          INTO v_count
          FROM PBANDI_T_CSP_PROGETTO
          where ID_PROGETTO = p_id_progetto;

          IF v_count > 0 THEN
             RETURN TRUE;
          ELSE
             RETURN FALSE;
          END IF;
       END isSchedaProgetto;


  BEGIN

     IF isSchedaProgetto (p_id_progetto)  THEN
        v_nome_task :=  'AVVIO-SCH-PROG';
     ELSE
        v_nome_task :=  'AVVIO-ALTRO-PROG';
     END IF;


     IF p_id_processo IS NULL THEN
        RAISE_APPLICATION_ERROR (-20000, 'Processo non valorizzato - Eseguire metodo GET_PROCESSO ');
     END IF;

     -- Determino lo step successivo in caso di ramificazioni del processo (SCHEDA PROGETTO o ALTRO PROGETTO)
     OPEN g1(p_id_processo, v_nome_task);
     FETCH g1 into v_id_task_succ;
     IF g1%NOTFOUND THEN
        RAISE_APPLICATION_ERROR (-20000, 'Errore accesso PBANDI_T_STEP_PROCESSO TASK= '||v_nome_task||' PROCESSO= '||p_id_processo);
     END IF;
     CLOSE g1;

     RETURN v_id_task_succ;

  END FN_PROG_2014_REP_2007;
------------------------------------------------------------------------------------------------------------------------------------------
  -- METODI DI CONTROLLO
  -- NELLA NAVIGAZIONE DEL PROCESSO PER I TASK DI CONTROLLO (TC) E PER I TASK OPZIONALI DI CONTROLLO (TOC)
  -- NELLA TABELLA PBANDI_D_TASK SE VALORIZZATO IL CAMPO ID_METODO_TASK  VIENE ATTIVATO IL RELATIVO CONTROLLO PER
  -- DEFINIRE SE RENDERE O MENO VISIBILE L'ATTIVITA' ALL'UTENTE
  -- LA LOGICA DI BUSINESS  (COSA DEVE FARE CIASCUN CONTROLLO) E' BEN SPECIFICATA NEL DOCUMENTO DI ANALISI


  FUNCTION FN_EROG_LIQUID (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                           p_id_processo IN PBANDI_T_PROCESSO.ID_PROCESSO%TYPE) RETURN INTEGER AS
  -- Funzione per determinare la selezione del ramo delle Erogazioni o Liquidazioni
  bFoundBr37                     BOOLEAN; -- Liquidazioni/Erogazioni
  v_nome_task                     VARCHAR2(30);
  v_id_task_succ  PBANDI_D_TASK.ID_TASK%TYPE;

  BEGIN
      bFoundBr37 := Regola(p_id_progetto,'BR37');



    IF     bFoundBr37  THEN -- Liquidazione
            v_nome_task :=  'LIQUID-FITTIZIO';

    ELSE  v_nome_task :=  'EROG-FITTIZIO';


    END IF;

     IF p_id_processo IS NULL THEN
        RAISE_APPLICATION_ERROR (-20000, 'Processo non valorizzato - Eseguire metodo GET_PROCESSO ');
     END IF;

     -- Determino lo step successivo in caso di ramificazioni del processo (LIQUIDAZIONE/LIQUID-FLUSSI o EROGAZIONE/EROG-FLUSSI)
     IF v_nome_task IS NOT NULL THEN
         OPEN g1(p_id_processo, v_nome_task);
         FETCH g1 into v_id_task_succ;
         IF g1%NOTFOUND THEN
            RAISE_APPLICATION_ERROR (-20000, 'Errore accesso PBANDI_T_STEP_PROCESSO TASK= '||v_nome_task||' PROCESSO= '||p_id_processo);
         END IF;
         CLOSE g1;

     ELSE
        v_id_task_succ:= NULL;
     END IF;



     RETURN v_id_task_succ;


  END FN_EROG_LIQUID;
 ------------------------------------------------------------------------------------------------------------------------------------------

  FUNCTION FN_CARICAM_INDIC_AVVIO (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE, p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' ) RETURN VARCHAR2 AS
  -- Controllo per il task CARICAM-INDIC-AVVIO
  -- Ritorna S se il task deve essere processato altrimenti N
     v_return VARCHAR2(1);
     bFound                    BOOLEAN;

  BEGIN

    bFound := Regola(p_id_progetto,'BR35');
    IF bFound THEN
       v_return := 'S';
    ELSE
       v_return := 'N';
    END IF;
	
	IF v_return = 'S' THEN
	
	   --Indicatore di SIF della programmazione 2021-2027: Il task non deve essere disponibile
		BEGIN
		   SELECT 'N'
			 INTO v_return
			 FROM DUAL
			 WHERE EXISTS
				(SELECT NULL
				   FROM  PBANDI_V_SIF_INDICATORI_21_27
				  WHERE ID_PROGETTO = p_id_progetto);
				  
			
		EXCEPTION
		   WHEN NO_DATA_FOUND THEN
			  NULL;
		END;

	END IF;

    RETURN v_return;
  END FN_CARICAM_INDIC_AVVIO;

 ------------------------------------------------------------------------------------------------------------------------------------------
  FUNCTION FN_VISUALIZZA_INDICATORI (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE, p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' ) RETURN VARCHAR2 AS
  -- Controllo per il task VISUALIZZA-INDICATORI
  -- Ritorna S se il task deve essere processato altrimenti N
     v_return VARCHAR2(1) := 'N';

  BEGIN
    --Indicatore di SIF della programmazione 2021-2027
    BEGIN
	   SELECT 'S'
	     INTO v_return
		 FROM DUAL
		 WHERE EXISTS
		    (SELECT NULL
			   FROM  PBANDI_V_SIF_INDICATORI_21_27
		      WHERE ID_PROGETTO = p_id_progetto);
			  
		
	EXCEPTION
	   WHEN NO_DATA_FOUND THEN
	      v_return := 'N';
	END;

    RETURN v_return;
  END FN_VISUALIZZA_INDICATORI;

 ------------------------------------------------------------------------------------------------------------------------------------------

  FUNCTION FN_CRONOPROG_AVVIO (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE, p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' ) RETURN VARCHAR2 AS
  -- Controllo per il task CRONOPROG_AVVIO
  -- Ritorna S se il task deve essere processato altrimenti N
     v_return           VARCHAR2(1);
     bFound             BOOLEAN;
     v_count            integer;
     v_id_utente_batch  integer;
     v_endattivita      integer;
  BEGIN
        bFound := Regola(p_id_progetto,'BR34');
        IF bFound THEN
           v_return := 'S';
        ELSE
           v_return := 'N';
        END IF;

        --===================================
        -- Implementare logica
        -- Se su PBANDI_R_PROGETTO_FASE_MONIT c'è una data di aggiornamento  (GESTIONE e non AVVIO)
        -- generata dal batch e non controllabile dunque da on-line con funzionalità di chiusura
        -- allora bisogna chiudere l'attivita passando il flag di forzatura
        -- passando l'ID_UTENTE batch (GEFO)
        -- e  mettere V_RETURN = 'N'
        --============================================
        /*
        IF v_return = 'S' THEN

          SELECT COUNT(*) INTO v_count
          FROM   PBANDI_R_PROGETTO_FASE_MONIT a
          WHERE  a.ID_PROGETTO = p_id_progetto
          AND    a.DT_AGGIORNAMENTO IS NOT NULL;

                  -- se la data di aggiornamento è presente significa  che il progetto è in fase di gestione
                  -- quindi è necessario chiudere l'attività
                  IF v_count > 0 THEN
                         select id_utente into v_id_utente_batch
                         from   pbandi_t_utente
                         where  codice_utente = 'GEFO';
                         v_endattivita:= EndAttivita(p_id_progetto,'CRONOPROG-AVVIO',v_id_utente_batch,'S');
                         v_return := 'N';
                  ELSE
                         v_return := 'S';

                  END IF;

        END IF;
      */
  RETURN v_return;

  END FN_CRONOPROG_AVVIO;

 ------------------------------------------------------------------------------------------------------------------------------------------

  FUNCTION FN_CARICAM_INDIC_PROG (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE, p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
                                  RETURN VARCHAR2 AS
  -- Controllo per il task CARICAM-INDIC-PROG
  -- Ritorna S se il task deve essere processato altrimenti N
     v_return VARCHAR2(1);
     bFound                    BOOLEAN;

  BEGIN

    bFound := Regola(p_id_progetto,'BR35');
    IF bFound THEN
       v_return := 'S';
    ELSE
       v_return := 'N';
    END IF;

	IF v_return = 'S' THEN
	
	   --Indicatore di SIF della programmazione 2021-2027: Il task non deve essere disponibile
		BEGIN
		   SELECT 'N'
			 INTO v_return
			 FROM DUAL
			 WHERE EXISTS
				(SELECT NULL
				   FROM  PBANDI_V_SIF_INDICATORI_21_27
				  WHERE ID_PROGETTO = p_id_progetto);
				  
			
		EXCEPTION
		   WHEN NO_DATA_FOUND THEN
			  NULL;
		END;

	END IF;
	
    RETURN v_return;

  END FN_CARICAM_INDIC_PROG;

------------------------------------------------------------------------------------------------------------------------------------------

  FUNCTION FN_CRONOPROGRAMMA (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET'
                              ) RETURN VARCHAR2 AS
  -- Controllo per il task CRONOPROGRAMMA
  -- Ritorna S se il task deve essere processato altrimenti N
     v_return VARCHAR2(1);
     bFound                    BOOLEAN;

  BEGIN
    bFound := Regola(p_id_progetto,'BR34');
    IF bFound THEN
       v_return := 'S';
    ELSE
       v_return := 'N';
    END IF;

    RETURN v_return;
    dbms_output.put_line(v_return);

  END FN_CRONOPROGRAMMA;

------------------------------------------------------------------------------------------------------------------------------------------

FUNCTION FN_RET_INDIC_PROG (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET'
                              ) RETURN VARCHAR2 AS
  -- Controllo per il task RET-INDIC-PROG
  -- Ritorna S se il task deve essere processato altrimenti N
     v_return VARCHAR2(1);
     bFound                    BOOLEAN;

  BEGIN
    bFound := Regola(p_id_progetto,'BR35');
    IF bFound THEN
       v_return := 'S';
    ELSE
       v_return := 'N';
    END IF;

	IF v_return = 'S' THEN
	
	   --Indicatore di SIF della programmazione 2021-2027: Il task non deve essere disponibile
		BEGIN
		   SELECT 'N'
			 INTO v_return
			 FROM DUAL
			 WHERE EXISTS
				(SELECT NULL
				   FROM  PBANDI_V_SIF_INDICATORI_21_27
				  WHERE ID_PROGETTO = p_id_progetto);
				  
			
		EXCEPTION
		   WHEN NO_DATA_FOUND THEN
			  NULL;
		END;

	END IF;
	
    RETURN v_return;

END FN_RET_INDIC_PROG;
------------------------------------------------------------------------------------------------------------------------------------------

FUNCTION FN_RET_CRONOPROG (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET'
                              ) RETURN VARCHAR2 AS
  -- Controllo per il task RET-INDIC-CRONOPROG
  -- Ritorna S se il task deve essere processato altrimenti N
     v_return VARCHAR2(1);
     bFound                    BOOLEAN;

  BEGIN
    bFound := Regola(p_id_progetto,'BR34');
    IF bFound THEN
       v_return := 'S';
    ELSE
       v_return := 'N';
    END IF;

    RETURN v_return;

END FN_RET_CRONOPROG;
------------------------------------------------------------------------------------------------------------------------------------------

FUNCTION FN_COMUNIC_RINUNCIA (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                              p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET'
                             ) RETURN VARCHAR2 AS

    v_return VARCHAR2(1):='S';
    v_endattivita      integer;

     /* quanto viene attivata la rinuncia, la parte web richiama la funzione 'endattività'
        'endattività' verifica la presenza di metodi associati al task -->flag_chiamata_end = 'S'
        se = 'S' richiama il metodo associato alla funzione
        In questo caso la logica applicativa prevede , quando viene chiuso il task comunica-rinuncia
        di chiudere i seguenti task attivando la funzione 'endattività' con flag di forzatura.

    */
    BEGIN

    IF p_funzione_chiamante = 'END' THEN
            v_endattivita:= EndAttivita(p_id_progetto,'GEST-FIDEIUSSIONI',p_id_utente,'S');
            v_endattivita:= EndAttivita(p_id_progetto,'RICH-EROG-CALC-CAU',p_id_utente,'S');
            v_endattivita:= EndAttivita(p_id_progetto,'PROP-RIM-CE',p_id_utente,'S');
           -- v_endattivita:= EndAttivita(p_id_progetto,'DICH-DI-SPESA',p_id_utente,'S'); mc 07062022 -- jira3500
            v_endattivita:= EndAttivita(p_id_progetto,'COMUNIC-FINE-PROG',p_id_utente,'S');
            v_endattivita:= EndAttivita(p_id_progetto,'DICH-SPESA-INTEGRATIVA',p_id_utente,'S');
            v_endattivita:= EndAttivita(p_id_progetto,'CARICAM-INDIC-AVVIO',p_id_utente,'S');
            v_endattivita:= EndAttivita(p_id_progetto,'CRONOPROG-AVVIO',p_id_utente,'S');
            v_endattivita:= EndAttivita(p_id_progetto,'QUADRO-PREVISIO',p_id_utente,'S');
            v_endattivita:= EndAttivita(p_id_progetto,'CARICAM-INDIC-PROG',p_id_utente,'S');
            v_endattivita:= EndAttivita(p_id_progetto,'CRONOPROGRAMMA',p_id_utente,'S');
    END IF;

    RETURN v_return;


END FN_COMUNIC_RINUNCIA;

------------------------------------------------------------------------------------------------------------------------------------------

FUNCTION FN_DICH_DI_SPESA      (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )   RETURN VARCHAR2 AS

 v_return VARCHAR2(1):='S';
 v_endattivita      integer;

         /* quando la dichiarazione di spesa è di tipo finale., la parte web richiama la funzione 'endattività'
        'endattività' verifica la presenza di metodi associati al task -->flag_chiamata_end = 'S'
         se = 'S' richiama il metodo associato alla funzione
        In questo caso la logica applicativa prevede , quando viene richiamato il task
        di chiudere i seguenti task attivando la funzione 'endattività' con flag di forzatura.
         */

 BEGIN

 IF p_funzione_chiamante = 'END' THEN

            v_endattivita:= EndAttivita(p_id_progetto,'GEST-FIDEIUSSIONI',p_id_utente,'S');
            v_endattivita:= EndAttivita(p_id_progetto,'RICH-EROG-CALC-CAU',p_id_utente,'S');
            v_endattivita:= EndAttivita(p_id_progetto,'PROP-RIM-CE',p_id_utente,'S');
         --   v_endattivita:= EndAttivita(p_id_progetto,'QUADRO-PREVISIO',p_id_utente,'S'); -- Da approfondire se chiudere


 END IF;


RETURN v_return;


END FN_DICH_DI_SPESA;
------------------------------------------------------------------------------------------------------------------------------------------

FUNCTION FN_DICH_SPESA_INTEGRATIVA (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                    p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                    p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
                                    RETURN VARCHAR2 AS
v_return VARCHAR2(1):='N';

CURSOR c1  (c_id_progetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE )IS
           SELECT FLAG_RICHIESTA_INTEGRATIVA
           FROM PBANDI_T_DICHIARAZIONE_SPESA
           WHERE ID_PROGETTO = c_id_progetto
           ORDER BY DT_DICHIARAZIONE desc;

r1 c1%ROWTYPE;

 BEGIN

 -- il controllo veine effettuato sul progetto in input verificando che il FLAG_RICHIESTA_INTEGRATIVA sia = 'S'
 -- in questo caso viene reso disponibile il task iun questione

       OPEN c1 (p_id_progetto);
       FETCH c1 into r1;
       IF c1%NOTFOUND THEN

          v_return := 'N';

       ELSIF r1.FLAG_RICHIESTA_INTEGRATIVA = 'S' THEN

          v_return := 'S';

       END IF;
       CLOSE c1;

 RETURN v_return;

END FN_DICH_SPESA_INTEGRATIVA;
------------------------------------------------------------------------------------------------------------------------------------------

FUNCTION FN_VALID_DICH_SPESA       (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                    p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                    p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
                                    RETURN VARCHAR2 AS
v_return VARCHAR2(1):='N';

CURSOR c1  (c_id_progetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE )IS
           SELECT DT_CHIUSURA_VALIDAZIONE
           FROM PBANDI_T_DICHIARAZIONE_SPESA a, PBANDI_D_TIPO_DICHIARAZ_SPESA b
           WHERE ID_PROGETTO = c_id_progetto AND
                 b.ID_TIPO_DICHIARAZ_SPESA = a.ID_TIPO_DICHIARAZ_SPESA AND
                 a.DT_CHIUSURA_VALIDAZIONE IS NULL AND
                 b.DESC_BREVE_TIPO_DICHIARA_SPESA = 'I' AND
                 b.DT_FINE_VALIDITA IS NULL;


r1 c1%ROWTYPE;

BEGIN

--  il controllo veine effettuato sul progetto in input verificando che IDESC_TIPO_DICHIARAZIONE_SPESA = 'I AND
--  DT_CHIUSURA_VALIDAZIONE IS NULL -->  in questo caso, se c'è anche solo un record viene reso disponibile il task iun questione

       OPEN c1 (p_id_progetto);
       FETCH c1 into r1;
       IF       c1%NOTFOUND THEN
                v_return := 'N';
       ELSE
                v_return := 'S';

       END IF;

       CLOSE c1;

 RETURN v_return;

END FN_VALID_DICH_SPESA;
------------------------------------------------------------------------------------------------------------------------------------------

FUNCTION FN_VALID_DICH_SPESA_FINALE    (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                        p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                        p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
                                        RETURN VARCHAR2 AS
v_return VARCHAR2(1):='S';
v_endattivita integer;
CURSOR c1  (c_id_progetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE )IS
           SELECT DT_CHIUSURA_VALIDAZIONE,b.DESC_BREVE_TIPO_DICHIARA_SPESA, a.FLAG_RICHIESTA_INTEGRATIVA
           FROM PBANDI_T_DICHIARAZIONE_SPESA a, PBANDI_D_TIPO_DICHIARAZ_SPESA b
           WHERE ID_PROGETTO = c_id_progetto AND
                 b.ID_TIPO_DICHIARAZ_SPESA = a.ID_TIPO_DICHIARAZ_SPESA AND
                 a.DT_CHIUSURA_VALIDAZIONE IS NULL  AND
                 b.DT_FINE_VALIDITA IS NULL
                 ORDER BY DT_DICHIARAZIONE desc;


r1 c1%ROWTYPE;

--  il controllo veine effettuato sul progetto in input verificando che se DESC_TIPO_DICHIARAZIONE_SPESA IN ('FC','IN') AND
--  DT_CHIUSURA_VALIDAZIONE IS NULL -->  in questo caso, se c'è anche solo un record viene reso disponibile il task

BEGIN
IF p_funzione_chiamante = 'GET' THEN

         OPEN c1 (p_id_progetto);
               FETCH c1 into r1;

               IF       c1%NOTFOUND THEN
                        v_return := 'N';
               ELSIF    r1.DESC_BREVE_TIPO_DICHIARA_SPESA IN ('FC','IN') THEN v_return := 'S';
                       -- LOOP
                        -- il cursore verifica che non ci siano dichiarazioni di spesa intermedie non ancora validate
                       -- FETCH c1 into r1;
                       -- EXIT WHEN c1%NOTFOUND;
                       -- IF r1.DESC_BREVE_TIPO_DICHIARA_SPESA = ('I') THEN v_return := 'N';
                       -- EXIT;
                        --END IF;

                        --END LOOP;
               END IF;

           CLOSE c1;


-- END
ELSIF p_funzione_chiamante = 'END' THEN



            OPEN c1 (p_id_progetto);

                   FETCH c1 into r1;
                   IF       c1%NOTFOUND THEN
                            raise_application_error(-20000,'Errore accesso tabella pbandi_t_dichiarazione_spesa - id progetto = '||p_id_progetto);
                   ELSIF    r1.DESC_BREVE_TIPO_DICHIARA_SPESA IN ('FC','IN') AND r1.FLAG_RICHIESTA_INTEGRATIVA = 'N' THEN
                            -- senon troviamo piu richieste di integrative si chiude il task
                            v_endattivita:= EndAttivita(p_id_progetto,'DICH-SPESA-INTEGRATIVA',p_id_utente,'S');


                   END IF;

             v_endattivita:= EndAttivita(p_id_progetto,'VALID-DICH-SPESA',p_id_utente,'S');

            CLOSE c1;
END IF;

RETURN v_return;


END FN_VALID_DICH_SPESA_FINALE;
------------------------------------------------------------------------------------------------------------------------------------------
FUNCTION FN_RICH_EROG_CALC_CAU         (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                        p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                        p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
                                        RETURN VARCHAR2 AS
    v_return VARCHAR2(2000);
    v_count  NUMBER;
    v_sum_importo_agevolato NUMBER;
    v_sum_importo_erogazione_rich NUMBER;
    v_sum_perc_erog NUMBER;
    v_perc NUMBER;
    -- USATA LA SEGUENTE VISTA --> PBANDI_V_PROC_EROG_CAU
    CURSOR c1  (c_id_progetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE )IS
               SELECT A.*,B.ID_CAUSALE_EROGAZIONE as ID_CAUSALE_EROGAZIONE_RICH
               FROM  pbandi_v_processo_erog_cau A ,PBANDI_T_RICHIESTA_EROGAZIONE B
               WHERE
               A.ID_PROGETTO = c_id_progetto AND
               A.ID_PROGETTO = B.ID_PROGETTO(+) AND
               A.ID_CAUSALE_EROGAZIONE = B.ID_CAUSALE_EROGAZIONE(+)
               ORDER BY A.PROGR_ORDINAMENTO;

    r1 c1%ROWTYPE;

BEGIN

     OPEN c1(p_id_progetto);
     LOOP
     FETCH c1 INTO r1;

     IF c1%NOTFOUND THEN v_return:= NULL;
           EXIT;
           --RAISE_APPLICATION_ERROR (-20000, 'ERRORE DI ACCESSO SU TABELLA CONFIGURAZIONE CAUSALI RICHIESTA EROGAZIONE - ID_PROGETTO '||p_id_progetto);
     ELSIF r1.DESC_BREVE_CAUSALE = 'PA' AND r1.ID_CAUSALE_EROGAZIONE_RICH IS NULL THEN v_return := 'PA'; -- primo acconto
           EXIT;
     ELSIF r1.DESC_BREVE_CAUSALE = 'SA' AND r1.ID_CAUSALE_EROGAZIONE_RICH IS NULL THEN v_return := 'SA';
           EXIT;
     ELSIF r1.DESC_BREVE_CAUSALE = 'UA' AND r1.ID_CAUSALE_EROGAZIONE_RICH IS NULL AND NVL(r1.PERC_LIMITE,0) > 0 THEN

           -- 2.1.1
           SELECT SUM(NVL(QUOTA_IMPORTO_AGEVOLATO,0)) INTO v_sum_importo_agevolato
           FROM PBANDI_R_CONTO_ECONOM_MOD_AGEV
           WHERE ID_CONTO_ECONOMICO = r1.ID_CONTO_ECONOMICO;

           -- 2.1.2
           SELECT SUM(NVL(IMPORTO_EROGAZIONE_RICHIESTO,0)) INTO v_sum_importo_erogazione_rich
           FROM PBANDI_T_RICHIESTA_EROGAZIONE
           WHERE ID_PROGETTO = r1.ID_PROGETTO;

           -- 2.1.3
           SELECT SUM(NVL(PERC_EROGAZIONE,0)) INTO v_sum_perc_erog
           FROM PBANDI_R_BANDO_CAUSALE_EROGAZ A,PBANDI_D_CAUSALE_EROGAZIONE B
           WHERE A.ID_BANDO = r1.ID_BANDO AND
                 A.ID_CAUSALE_EROGAZIONE = B.ID_CAUSALE_EROGAZIONE AND
                 B.PROGR_ORDINAMENTO < r1.PROGR_ORDINAMENTO;

           -- 2.1.4
           v_perc := v_sum_perc_erog + NVL(r1.PERC_LIMITE,0);

           --  2.1.5
           -- P/100 > R/A
           IF   (V_PERC/100) > (v_sum_importo_erogazione_rich/v_sum_importo_agevolato) THEN
                dbms_output.put_line('v_perc ' ||v_perc||' importo agevolato '||v_sum_importo_agevolato||' importo erogazione '||v_sum_importo_erogazione_rich);
                --(V_PERC/100) > (v_sum_importo_agevolato/v_sum_importo_erogazione_rich) THEN
                 v_return := 'UA';
                 EXIT;
           END IF;

     ELSIF r1.DESC_BREVE_CAUSALE = 'UA' AND r1.ID_CAUSALE_EROGAZIONE_RICH IS NULL AND NVL(r1.PERC_LIMITE,0) = 0 THEN
           v_return := 'UA';
           EXIT;

     ELSIF r1.DESC_BREVE_CAUSALE = 'SAL' AND r1.ID_CAUSALE_EROGAZIONE_RICH IS NULL THEN
           v_return := 'SAL';
           EXIT;

     ELSE
           v_return:= NULL;


     END IF;



     END LOOP;

     CLOSE c1;

   IF   v_return IS NOT NULL THEN

        SELECT A.DESC_CAUSALE INTO  v_return
        FROM   PBANDI_D_CAUSALE_EROGAZIONE A
        WHERE  A.DESC_BREVE_CAUSALE = v_return;

   END IF;

   RETURN v_return;

END FN_RICH_EROG_CALC_CAU;
------------------------------------------------------------------------------------------------------------------------------------------

FUNCTION FN_RICH_EROG_CALC_CAU_02 (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET'
                                 ) RETURN VARCHAR2 AS

  -- Controllo per il task RICH-EROG-CALC-CAU
  -- Ritorna S se il task deve essere processato altrimenti N
     v_return VARCHAR2(1);
     bFound                    BOOLEAN;

  BEGIN
    bFound := Regola(p_id_progetto,'BR15');
    IF bFound THEN
       v_return := 'S';
    ELSE
       v_return := 'N';
    END IF;

    RETURN v_return;

END FN_RICH_EROG_CALC_CAU_02;
------------------------------------------------------------------------------------------------------------------------------------------

FUNCTION FN_VERIF_RICH_EROG       (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET'
                                 ) RETURN VARCHAR2 AS
  -- Controllo per il task VERIF-RICH-EROGAZIONE
v_return VARCHAR2(1):='N';

CURSOR c1  (c_id_progetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE )IS
           SELECT ID_RICHIESTA_EROGAZIONE
           FROM   pbandi_t_richiesta_erogazione a
           WHERE  a.ID_PROGETTO = c_id_progetto;


r1 c1%ROWTYPE;

BEGIN

--  il controllo veine effettuato sul progetto in input verificando che ci sia almeno un record presente sulla t_richiesta_erogazione
--  in questo caso, se c'è anche solo un record viene reso disponibile il task in questione

       OPEN c1 (p_id_progetto);
       FETCH c1 into r1;
       IF       c1%NOTFOUND THEN
                v_return := 'N';
       ELSE
                v_return := 'S';

       END IF;

       CLOSE c1;

 RETURN v_return;

END FN_VERIF_RICH_EROG;
-- mc --

------------------------------------------------------------------------------------------------------------------------------------------

FUNCTION FN_GEST_FIDEIUSSIONI (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET'
                                 ) RETURN VARCHAR2 AS
  -- Controllo per il task GEST-FIDEIUSSIONI
  -- Ritorna S se il task deve essere processato altrimenti N
     v_return VARCHAR2(1);
     bFound                    BOOLEAN;

  BEGIN
    bFound := Regola(p_id_progetto,'BR14');
    IF bFound THEN
       v_return := 'S';
    ELSE
       v_return := 'N';
    END IF;

    RETURN v_return;

END FN_GEST_FIDEIUSSIONI;

------------------------------------------------------------------------------------------------------------------------------------------

PROCEDURE PRC_CHIUSURA_PROGETTO(p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL)  AS

-- questa procedura viene richiamata dai metodi:  FN_CHIUSURA_PROGETTO -- FN_CHIUSURA_PROG_RINUNCIA -- FN_CHIUS_UFF_PROG
-- i metodi che richiamano la procedura chiudono gli stessi task

v_endattivita      integer;

BEGIN

        v_endattivita:= EndAttivita(p_id_progetto,'CHIUS-PROG-RINUNCIA',p_id_utente,'S');
        v_endattivita:= EndAttivita(p_id_progetto,'CHIUS-UFF-PROG',p_id_utente,'S');
        v_endattivita:= EndAttivita(p_id_progetto,'CHIUSURA-PROGETTO',p_id_utente,'S');
        v_endattivita:= EndAttivita(p_id_progetto,'COMUNIC-FINE-PROG',p_id_utente,'S');
        v_endattivita:= EndAttivita(p_id_progetto,'DICH-SPESA-INTEGRATIVA',p_id_utente,'S');
        v_endattivita:= EndAttivita(p_id_progetto,'CRONOPROG-AVVIO',p_id_utente,'S');
        v_endattivita:= EndAttivita(p_id_progetto,'CRONOPROGRAMMA',p_id_utente,'S');
        v_endattivita:= EndAttivita(p_id_progetto,'GEST-FIDEIUSSIONI',p_id_utente,'S');
        v_endattivita:= EndAttivita(p_id_progetto,'CARICAM-INDIC-AVVIO',p_id_utente,'S');
        v_endattivita:= EndAttivita(p_id_progetto,'CARICAM-INDIC-PROG',p_id_utente,'S');
        v_endattivita:= EndAttivita(p_id_progetto,'PROP-RIM-CE',p_id_utente,'S');
        v_endattivita:= EndAttivita(p_id_progetto,'QUADRO-PREVISIO',p_id_utente,'S');
       -- v_endattivita:= EndAttivita(p_id_progetto,'DICH-DI-SPESA',p_id_utente,'S'); mc 07062022 -- jira3500
        v_endattivita:= EndAttivita(p_id_progetto,'RICH-EROG-CALC-CAU',p_id_utente,'S');
        v_endattivita:= EndAttivita(p_id_progetto,'COMUNIC-RINUNCIA',p_id_utente,'S');
        v_endattivita:= EndAttivita(p_id_progetto,'FINE-PROCESSO',p_id_utente,'S');
        -- mc sviluppo marzo 2020
       -- v_endattivita:= EndAttivita(p_id_progetto,'GEST-AFFIDAMENTI',p_id_utente,'S'); -- jira 3422 -- mc 05052022
        v_endattivita:= EndAttivita(p_id_progetto,'RIM-CE',p_id_utente,'S');
        --

--EXCEPTION
--WHEN OTHERS THEN
--    RAISE_APPLICATION_ERROR(-20000,'ERRORE DELLA PROCEDURA PRC_CHIUSURA - ID PROGETTO '||p_id_progetto||' Err '||SQLERRM);
END PRC_CHIUSURA_PROGETTO;

------------------------------------------------------------------------------------------------------------------------------------------


FUNCTION FN_CHIUSURA_PROGETTO      (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                    p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                    p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )   RETURN VARCHAR2 AS

 v_return VARCHAR2(1):='S';
 -- mc sviluppo marzo 2020
 v_ret_economie NUMBER;
 --


         /* la parte web richiama la funzione 'endattività'
        'endattività' verifica la presenza di metodi associati al task --> flag_chiamata_end = 'S'
         se = 'S' richiama il metodo associato alla funzione
         In questo caso la logica applicativa prevede , quando viene richiamato il task
         di richiamare la procedura PRC_CHIUDI_TASK per chiudere i task associati attivando la funzione 'endattività' con flag di forzatura.
         */

 BEGIN

             IF p_funzione_chiamante = 'END' THEN
             -- mc sviluppo marzo 2020
                      v_ret_economie := FN_CALCOLO_ECONOMIE(p_id_progetto, p_id_utente);
                      IF v_ret_economie = 0 THEN
             --
                        PRC_CHIUSURA_PROGETTO(p_id_progetto, p_id_utente);
                      ELSE
                        v_return:='E'; -- errore sul calcolo economie
                      END IF;

             END IF;


RETURN v_return;


END FN_CHIUSURA_PROGETTO;
------------------------------------------------------------------------------------------------------------------------------------------


FUNCTION FN_CHIUSURA_PROG_RINUNCIA      (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                         p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                         p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )   RETURN VARCHAR2 AS

 v_return VARCHAR2(1):='S';


         /* la parte web richiama la funzione 'endattività'
        'endattività' verifica la presenza di metodi associati al task --> flag_chiamata_end = 'S'
         se = 'S' richiama il metodo associato alla funzione
         In questo caso la logica applicativa prevede , quando viene richiamato il task
         di richiamare la procedura PRC_CHIUDI_TASK per chiudere i task associati attivando la funzione 'endattività' con flag di forzatura.
         */

 BEGIN

             IF p_funzione_chiamante = 'END' THEN

                        PRC_CHIUSURA_PROGETTO(p_id_progetto, p_id_utente);

             END IF;


RETURN v_return;


END FN_CHIUSURA_PROG_RINUNCIA;
------------------------------------------------------------------------------------------------------------------------------------------


FUNCTION FN_CHIUS_UFF_PROG              (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                         p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                         p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )   RETURN VARCHAR2 AS

 v_return VARCHAR2(1):='S';


         /* la parte web richiama la funzione 'endattività'
        'endattività' verifica la presenza di metodi associati al task --> flag_chiamata_end = 'S'
         se = 'S' richiama il metodo associato alla funzione
         In questo caso la logica applicativa prevede , quando viene richiamato il task
         di richiamare la procedura PRC_CHIUDI_TASK per chiudere i task associati attivando la funzione 'endattività' con flag di forzatura.
         */

 BEGIN

             IF p_funzione_chiamante = 'END' THEN

                        PRC_CHIUSURA_PROGETTO(p_id_progetto, p_id_utente);

             END IF;


RETURN v_return;

END FN_CHIUS_UFF_PROG;


FUNCTION FN_PROP_RIM_CE (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE  DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )   RETURN VARCHAR2 AS

v_return VARCHAR2(1):='N';
bFound   BOOLEAN;
v_npi_data date;
v_nrc_data date;
v_count INTEGER:=0;

  BEGIN

    bFound := Regola(p_id_progetto,'BR27');

 IF bFound THEN

    BEGIN
        WITH PROP_RIM AS
       (
       SELECT tp.ID_PROGETTO, ce.ID_STATO_CONTO_ECONOMICO, sce.DESC_BREVE_STATO_CONTO_ECONOM, ce.DT_INIZIO_VALIDITA, CE.DT_FINE_VALIDITA
       FROM   PBANDI_T_CONTO_ECONOMICO ce, PBANDI_T_PROGETTO tp, PBANDI_D_STATO_CONTO_ECONOMICO sce
       WHERE ce.ID_DOMANDA = tp.ID_DOMANDA AND
                   ce.ID_STATO_CONTO_ECONOMICO = sce.ID_STATO_CONTO_ECONOMICO
                   --('NRC', 'NPI') --> 11,13 Nuova Rimodulazione Conclusa, Nuova Proposta Inviata
        )
        --MAIN
       SELECT NRC_DATA, NPI_DATA, NPI_COUNT
       INTO v_NRC_DATA,v_NPI_DATA, v_COUNT
       FROM

       (SELECT NVL(MAX(pr.DT_INIZIO_VALIDITA), TO_DATE('01011900','ddmmyyyy'))   AS NRC_DATA
       FROM   PROP_RIM pr
       WHERE pr.id_progetto = p_id_progetto AND
             pr.DESC_BREVE_STATO_CONTO_ECONOM = 'NRC'), --11

       (SELECT NVL(MAX(pr.DT_INIZIO_VALIDITA), TO_DATE('01011900','ddmmyyyy')) AS NPI_DATA, COUNT(*) as NPI_COUNT--13
       FROM   PROP_RIM pr
       WHERE pr.id_progetto = p_id_progetto AND
             pr.DESC_BREVE_STATO_CONTO_ECONOM = 'NPI')
        ;

       IF      v_count = 0 THEN  v_return := 'S';
       ELSE
               IF v_npi_data < v_nrc_data THEN  v_return := 'S';  END IF;
       END IF;

    END;
 END IF;
 RETURN  v_return;

END FN_PROP_RIM_CE;
FUNCTION FN_RIM_CE (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE  DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )   RETURN VARCHAR2 AS

v_return VARCHAR2(1):='N';
bFound   BOOLEAN;
vCount   NUMBER;

  BEGIN

    bFound := Regola(p_id_progetto,'BR12');

    IF   bFound THEN

         SELECT COUNT(*) INTO vCount
         FROM  PBANDI_T_PROGETTO TP, PBANDI_T_DOMANDA TD,
               PBANDI_T_CONTO_ECONOMICO CE, PBANDI_D_TIPOLOGIA_CONTO_ECON TCE,
               PBANDI_D_STATO_CONTO_ECONOMICO SCE
         WHERE TP.ID_PROGETTO = p_id_progetto AND
               TP.ID_DOMANDA =  TD.ID_DOMANDA AND
               TD.ID_DOMANDA =  CE.ID_DOMANDA AND
               CE.ID_STATO_CONTO_ECONOMICO = SCE.ID_STATO_CONTO_ECONOMICO AND
               SCE.ID_TIPOLOGIA_CONTO_ECONOMICO = TCE.ID_TIPOLOGIA_CONTO_ECONOMICO AND
               TCE.DESC_BREVE_TIPOLOGIA_CONTO_ECO = 'MASTER' AND
               --SCE.DESC_BREVE_STATO_CONTO_ECONOM != 'DA' AND
               CE.DT_FINE_VALIDITA IS NULL;


         IF vcount > 0 THEN
            v_return := 'S';
         ELSE
            v_return := 'N';
         END IF;

    END IF;

    RETURN v_return;


END FN_RIM_CE;


FUNCTION FN_MOD_EROG (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE  DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )   RETURN VARCHAR2 AS

v_return VARCHAR2(1):='N';
bFound   BOOLEAN;
vCount   NUMBER;

  BEGIN

    bFound := Regola(p_id_progetto,'BR13');

    IF   bFound THEN

         SELECT COUNT(*) INTO vCount
         FROM  PBANDI_T_EROGAZIONE TE
         WHERE TE.ID_PROGETTO = p_id_progetto;


         IF vcount > 0 THEN

            v_return := 'S';

         ELSE

            v_return := 'N';

         END IF;

    END IF;

    RETURN v_return;


END FN_MOD_EROG;

FUNCTION FN_CONSULTA_LIQUID       (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE  DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' ) RETURN VARCHAR2 AS

v_return VARCHAR2(1):= 'N';
bFound   BOOLEAN;
vcount   NUMBER;

  BEGIN

    bFound := Regola(p_id_progetto,'BR37');

    IF bFound THEN

       SELECT COUNT(*) into vcount
       FROM PBANDI_T_ATTO_LIQUIDAZIONE
       WHERE ID_PROGETTO = p_id_progetto;

       IF vcount > 0 THEN
            v_return := 'S';
       ELSE
            v_return := 'N';
       END IF;

    END IF;

    RETURN v_return;

END FN_CONSULTA_LIQUID;

FUNCTION FN_REVOCA                (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE  DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' ) RETURN VARCHAR2 AS

v_return VARCHAR2(1):= 'N';

bFound37   BOOLEAN;
vcount   NUMBER;
vcount37   NUMBER;
bFound69   BOOLEAN;

BEGIN


    bFound69 := Regola(p_id_progetto,'BR69');
    bFound37 := Regola(p_id_progetto,'BR37');

	if bFound69 then
	   v_return := 'S';
	else

		SELECT COUNT(*) into vcount
		FROM PBANDI_T_EROGAZIONE
		WHERE ID_PROGETTO = p_id_progetto;

		SELECT COUNT(*) into vcount37
		FROM PBANDI_T_ATTO_LIQUIDAZIONE
		WHERE ID_PROGETTO = p_id_progetto;

		IF     vcount > 0 OR (bFound37 AND vcount37 > 0) THEN

				v_return := 'S';
		ELSE

				v_return := 'N';

		END IF;
	END IF;

RETURN v_return;

END FN_REVOCA;

FUNCTION FN_MOD_REVOCA                (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE  DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' ) RETURN VARCHAR2 AS

v_return VARCHAR2(1):= 'N';
vcount NUMBER;


BEGIN

SELECT COUNT(*) into vcount
FROM PBANDI_T_REVOCA
WHERE ID_PROGETTO = p_id_progetto;

    IF vcount > 0 THEN

       v_return := 'S';

    ELSE

       v_return := 'N';

    END IF;

    RETURN v_return;


END FN_MOD_REVOCA;

FUNCTION FN_RECUPERO              (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE  DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' ) RETURN VARCHAR2 AS

v_return VARCHAR2(1):= 'N';
vcount NUMBER;


BEGIN





     SELECT COUNT(*) into vcount
     FROM PBANDI_T_REVOCA
     WHERE ID_PROGETTO = p_id_progetto;

     IF vcount > 0 THEN
       v_return := 'S';
     ELSE
       v_return := 'N';
     END IF;



    RETURN v_return;


END FN_RECUPERO;
FUNCTION FN_MOD_RECUPERO            (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE  DEFAULT NULL,
                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' ) RETURN VARCHAR2 AS

v_return VARCHAR2(1):= 'N';
vcount NUMBER;
bFound28   BOOLEAN;

BEGIN

bFound28 := Regola(p_id_progetto,'BR28');

SELECT COUNT(*) INTO vcount
FROM PBANDI_T_RECUPERO TR, PBANDI_D_TIPO_RECUPERO DTR
WHERE ID_PROGETTO = p_id_progetto AND
      TR.ID_TIPO_RECUPERO = DTR.ID_TIPO_RECUPERO AND
      DTR.DESC_TIPO_RECUPERO <> 'SO';

     IF     vcount > 0 AND bFound28 THEN
            v_return := 'S';
     ELSE
            v_return := 'N';
     END IF;

    RETURN v_return;

END FN_MOD_RECUPERO;

FUNCTION FN_GEST_SPESA_VALID            (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                         p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE  DEFAULT NULL,
                                         p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' ) RETURN VARCHAR2 AS

v_return VARCHAR2(1):= 'N';
vcount NUMBER;

BEGIN

SELECT COUNT(*) INTO vcount
FROM PBANDI_T_DICHIARAZIONE_SPESA
WHERE ID_PROGETTO = p_id_progetto AND
      DT_CHIUSURA_VALIDAZIONE IS NOT NULL;

     IF     vcount > 0 THEN
            v_return := 'S';
     ELSE
            v_return := 'N';
     END IF;

    RETURN v_return;

END FN_GEST_SPESA_VALID;

FUNCTION FN_GEST_APPALTI                    (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                         p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE  DEFAULT NULL,
                                         p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' ) RETURN VARCHAR2 AS

v_return VARCHAR2(1):= 'N';
vcount NUMBER;

BEGIN

SELECT COUNT(*) INTO vcount
FROM PBANDI_T_PROGETTO TP, PBANDI_D_TIPO_OPERAZIONE TIO
WHERE TP.ID_TIPO_OPERAZIONE = TIO.ID_TIPO_OPERAZIONE AND
      ID_PROGETTO = p_id_progetto AND
      TIO.DESC_BREVE_TIPO_OPERAZIONE IN ('01','02');

     IF     vcount > 0 THEN
            v_return := 'S';
     ELSE
            v_return := 'N';
     END IF;

    RETURN v_return;

END FN_GEST_APPALTI;

------------------------------------------------------------------------------------------------------------------------------------------
FUNCTION FN_DESC_DICH_SPESA_FIN_INT         (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                             p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                             p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
                                             RETURN VARCHAR2 AS
    v_return VARCHAR2(2000);
    CURSOR c1  (c_id_progetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE )IS
           SELECT DT_CHIUSURA_VALIDAZIONE,b.DESC_BREVE_TIPO_DICHIARA_SPESA, a.FLAG_RICHIESTA_INTEGRATIVA,b.DESC_TIPO_DICHIARAZIONE_SPESA
           FROM PBANDI_T_DICHIARAZIONE_SPESA a, PBANDI_D_TIPO_DICHIARAZ_SPESA b
           WHERE ID_PROGETTO = c_id_progetto AND
                 b.ID_TIPO_DICHIARAZ_SPESA = a.ID_TIPO_DICHIARAZ_SPESA AND
                 b.DESC_BREVE_TIPO_DICHIARA_SPESA IN ('F','FC','IN') AND
                 a.DT_CHIUSURA_VALIDAZIONE IS NULL  AND
                 b.DT_FINE_VALIDITA IS NULL
                 ORDER BY DT_DICHIARAZIONE;

    r1 c1%ROWTYPE;

BEGIN

     OPEN c1(p_id_progetto);
     FETCH c1 INTO r1;

     IF c1%NOTFOUND THEN v_return:= NULL;

     ELSE
           v_return:= r1.DESC_TIPO_DICHIARAZIONE_SPESA;
     END IF;


     CLOSE c1;


   RETURN v_return;

END FN_DESC_DICH_SPESA_FIN_INT;

------------------------------------------------------------------------------------------------------------------------------------------
FUNCTION FN_RICH_INTEGRAZ_DICH_SPESA      (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                           p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                           p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )   RETURN VARCHAR2 AS

 v_return VARCHAR2(1):='N';
 v_count     integer;
 v_valore    VARCHAR2(1);

BEGIN
           -- Questo task è reso disponibile su TEST
           BEGIN
			   SELECT valore
				 INTO v_valore
				 FROM PBANDI_C_COSTANTI
				 WHERE attributo = 'SVILUPPO_FINP';

			   --(valore=1 Database di Test; valore=2 Database Beta test) 
			   IF v_valore = '1' THEN
				 -- v_return := 'S';
				  v_return := 'N';  -- 29/11/2023  MERGE
			   ELSE
				  v_return := 'N';  --Non viene reso disponibile 
			   END IF;	
			   
		   EXCEPTION
		      WHEN NO_DATA_FOUND THEN
			    v_return := 'S'; -- se manca la  costante allora è ininfluente
		   END;
		   
		   IF v_return = 'S' THEN

			   SELECT COUNT(*)
			   INTO     v_count
			   FROM   PBANDI_T_DICHIARAZIONE_SPESA ds,
						   PBANDI_T_INTEGRAZIONE_SPESA tis
			   WHERE  ID_PROGETTO = p_id_progetto AND
							ds.ID_DICHIARAZIONE_SPESA= tis.ID_DICHIARAZIONE_SPESA; 
			   -- AND          DATA_INVIO IS NULL; --JIRA 3554

			   IF v_count > 0 THEN
				  v_return := 'S';
			   ELSE
				  v_return := 'N';
			   END IF;
			   
		   END IF;

RETURN v_return;

END FN_RICH_INTEGRAZ_DICH_SPESA;

------------------------------------------------------------------------------------------------------------------------------------------
FUNCTION FN_RICH_INTEGRAZ      (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )   RETURN VARCHAR2 AS

 v_return VARCHAR2(1):='N';
 v_count     integer;
 v_valore    VARCHAR2(1);

    CURSOR c1  (c_id_progetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE )IS
		SELECT 'S'
		FROM PBANDI_T_GESTIONE_REVOCA a
		JOIN PBANDI_D_TIPOLOGIA_REVOCA b on b.id_tipologia_revoca = a.id_tipologia_revoca
		JOIN PBANDI_D_STATO_REVOCA c on c.id_stato_revoca = a.id_stato_revoca
		WHERE a.ID_PROGETTO = c_id_progetto
		AND b.DESC_BREVE_TIPOLOGIA_REVOCA =  'PROC_REV'  -- Procedimento di revoca
		AND c.DESC_BREVE_STATO_REVOCA = 'SOSPESO'; -- Sospeso
		
    CURSOR c2  (c_id_progetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE )IS
		SELECT 'S'
		FROM PBANDI_R_CAMP_CONTR_LOCO a
		JOIN PBANDI_T_CONTROLLO_LOCO b on b.id_controllo_loco = a.id_controllo_loco  --570 PBANDI_T_CONTROLLO_LOCO
		JOIN PBANDI_T_RICHIESTA_INTEGRAZ c on c.id_entita = 570 and c.id_target = b.id_controllo_loco
		JOIN PBANDI_D_STATO_RICH_INTEGRAZ d on d.id_stato_richiesta = c.id_stato_richiesta
		WHERE a.ID_PROGETTO = c_id_progetto
		  AND d.DESC_BREVE_STATO_RICHIESTA IN ('AUTORIZZATA','INVIATA','CHIUSA_UFFICIO');

   CURSOR c3  (c_id_progetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE )IS
		SELECT 'S'
		FROM PBANDI_T_CONTROLLO_LOCO_ALTRI a --608 PBANDI_T_CONTROLLO_LOCO_ALTRI
		JOIN PBANDI_T_RICHIESTA_INTEGRAZ b on b.id_entita = 608 and b.id_target = a.id_controllo
		JOIN PBANDI_D_STATO_RICH_INTEGRAZ c on c.id_stato_richiesta = b.id_stato_richiesta
		WHERE a.ID_PROGETTO = c_id_progetto
		  AND c.DESC_BREVE_STATO_RICHIESTA IN ('AUTORIZZATA','INVIATA','CHIUSA_UFFICIO');

BEGIN
           -- Questo task è reso disponibile su BETA TEST
           BEGIN
			   SELECT valore
				 INTO v_valore
				 FROM PBANDI_C_COSTANTI
				 WHERE attributo = 'SVILUPPO_FINP';

			   --(valore=1 Database di Test; valore=2 Database Beta test) 
			   IF v_valore = '2' THEN
				  v_return := 'S';
			   ELSE
				  --v_return := 'N';  --Non viene reso disponibile 
				  v_return := 'S';  --29/11/2023 MERGE 
			   END IF;	
			   
		   EXCEPTION
		      WHEN NO_DATA_FOUND THEN
			    v_return := 'S'; -- se manca la  costante allora è ininfluente
		   END;
		   
		   IF v_return = 'S' THEN
               -- Verifica se c'è almeno una integrazione di spesa
			   SELECT COUNT(*)
			   INTO     v_count
			   FROM   PBANDI_T_DICHIARAZIONE_SPESA ds,
						   PBANDI_T_INTEGRAZIONE_SPESA tis
			   WHERE  ID_PROGETTO = p_id_progetto AND
							ds.ID_DICHIARAZIONE_SPESA= tis.ID_DICHIARAZIONE_SPESA; 
			   -- AND          DATA_INVIO IS NULL; --JIRA 3554

			   IF v_count > 0 THEN
				  v_return := 'S';
			   ELSE
				  v_return := 'N';
			   END IF;
			   
			  IF  v_return = 'N' THEN
			    -- Verifica se c'è una richiesta di integrazione per la revoca
			    OPEN c1 (p_id_progetto);
			    FETCH c1 into v_return;
			    IF c1%FOUND THEN
				   v_return := 'S';
			    END IF;
			    CLOSE c1;

			  END IF;
			  
			  IF  v_return = 'N' THEN
			    -- Verifica se c'è una richiesta di integrazione per i controlli in loco
			    OPEN c2 (p_id_progetto);
			    FETCH c2 into v_return;
			    IF c2%FOUND THEN
				   v_return := 'S';
			    END IF;
			    CLOSE c2;
				
			  END IF;
			  
			  IF  v_return = 'N' THEN
			    -- Verifica se c'è una richiesta di integrazione per i controlli in loco altri
			    OPEN c3 (p_id_progetto);
			    FETCH c3 into v_return;
			    IF c3%FOUND THEN
				   v_return := 'S';
			    END IF;
			    CLOSE c3;
				
			  END IF;
			   
		   END IF;

RETURN v_return;

END FN_RICH_INTEGRAZ;
------------------------------------------------------------------------------------------------------------------------------------------

FUNCTION FN_GEST_CONTRODEDUZ (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                              p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                              p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
																					RETURN VARCHAR2 AS

    v_return VARCHAR2(1):='N';
	
    CURSOR c1  (c_id_progetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE )IS
		SELECT 'S'
		FROM PBANDI_T_GESTIONE_REVOCA a
		JOIN PBANDI_D_TIPOLOGIA_REVOCA b on b.id_tipologia_revoca = a.id_tipologia_revoca
		JOIN PBANDI_D_STATO_REVOCA c on c.id_stato_revoca = a.id_stato_revoca
		WHERE a.ID_PROGETTO = c_id_progetto
		AND b.DESC_BREVE_TIPOLOGIA_REVOCA =  'PROC_REV'  -- Procedimento di revoca
		AND c.DESC_BREVE_STATO_REVOCA = 'ATT_CONTRODED'; -- In attesa di controdeduzione
BEGIN
   OPEN c1 (p_id_progetto);
   FETCH c1 into v_return;
   IF c1%NOTFOUND THEN
      v_return := 'N';
   END IF;
   CLOSE c1;
RETURN v_return;

END FN_GEST_CONTRODEDUZ;

------------------------------------------------------------------------------------------------------------------------------------------
FUNCTION FN_GEST_CONTESTAZ (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                              p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                              p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
																					RETURN VARCHAR2 AS
    v_return VARCHAR2(1):='N';
	
    CURSOR c1  (c_id_progetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE )IS
		SELECT 'S'
		FROM PBANDI_T_GESTIONE_REVOCA a
		JOIN PBANDI_D_TIPOLOGIA_REVOCA b on b.id_tipologia_revoca = a.id_tipologia_revoca
		JOIN PBANDI_D_STATO_REVOCA c on c.id_stato_revoca = a.id_stato_revoca
		WHERE a.ID_PROGETTO = c_id_progetto
		AND b.DESC_BREVE_TIPOLOGIA_REVOCA =  'PROV_REV'  -- Provvedimento di revoca
		AND c.DESC_BREVE_STATO_REVOCA = 'PROV_REV'; -- Emesso provvedimento di revoca
BEGIN
   OPEN c1 (p_id_progetto);
   FETCH c1 into v_return;
   IF c1%NOTFOUND THEN
      v_return := 'N';
   END IF;
   CLOSE c1;
RETURN v_return;

END FN_GEST_CONTESTAZ;
------------------------------------------------------------------------------------------------------------------------------------------
-- Relazione tecnica DS intermedia
-------------------------------------------------
FUNCTION FN_REL_TEC_I (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
                                                                   RETURN VARCHAR2 AS
    v_return VARCHAR2(1):='N';
	
    CURSOR c1  (c_id_progetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE )IS
		SELECT 'S'
	      FROM PBANDI_T_DICHIARAZIONE_SPESA a
		  JOIN PBANDI_D_TIPO_DICHIARAZ_SPESA b on b.id_tipo_dichiaraz_spesa = a.id_tipo_dichiaraz_spesa
         WHERE a.id_progetto = c_id_progetto
		   AND dt_chiusura_validazione IS NULL
		   AND b.desc_breve_tipo_dichiara_spesa = 'I'; -- Intermedia
	
																   
BEGIN

   OPEN c1 (p_id_progetto);
   FETCH c1 into v_return;
   IF c1%NOTFOUND THEN
      v_return := 'N';
   END IF;
   CLOSE c1;
   
RETURN v_return;

END FN_REL_TEC_I;


------------------------------------------------------------------------------------------------------------------------------------------
-- Relazione tecnica DS Finale
-------------------------------------------------

																   
FUNCTION FN_REL_TEC_F (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
                                                                   RETURN VARCHAR2 AS
    v_return VARCHAR2(1):='N';
	
    CURSOR c1  (c_id_progetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE )IS
		SELECT 'S'
	      FROM PBANDI_T_DICHIARAZIONE_SPESA a
		  JOIN PBANDI_D_TIPO_DICHIARAZ_SPESA b on b.id_tipo_dichiaraz_spesa = a.id_tipo_dichiaraz_spesa
         WHERE a.id_progetto = c_id_progetto
		   AND dt_chiusura_validazione IS NULL
		   AND b.desc_breve_tipo_dichiara_spesa IN ('F','FC'); -- Finale e Finale con comunicazione
	
																   
BEGIN

   OPEN c1 (p_id_progetto);
   FETCH c1 into v_return;
   IF c1%NOTFOUND THEN
      v_return := 'N';
   END IF;
   CLOSE c1;
																   

RETURN v_return;

END FN_REL_TEC_F;	
															   
------------------------------------------------------------------------------------------------------------------------------------------
-- Validazione Relazione tecnica DS intermedia
-------------------------------------------------

FUNCTION FN_VAL_REL_TEC_I (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
                                                                   RETURN VARCHAR2 AS
    v_return VARCHAR2(1):='N';
	
    CURSOR c1  (c_id_progetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE )IS
		SELECT 'S'
	      FROM PBANDI_T_DICHIARAZIONE_SPESA a
		  JOIN PBANDI_D_TIPO_DICHIARAZ_SPESA b on b.id_tipo_dichiaraz_spesa = a.id_tipo_dichiaraz_spesa
         WHERE a.id_progetto = c_id_progetto
		   --AND dt_chiusura_validazione IS NOT NULL --Validata
		   AND b.desc_breve_tipo_dichiara_spesa = 'I'; -- Intermedia
	
																   
BEGIN

   OPEN c1 (p_id_progetto);
   FETCH c1 into v_return;
   IF c1%NOTFOUND THEN
      v_return := 'N';
   END IF;
   CLOSE c1;
																   
RETURN v_return;

END FN_VAL_REL_TEC_I;

------------------------------------------------------------------------------------------------------------------------------------------
-- Validazione Relazione tecnica DS Finale
-------------------------------------------------

FUNCTION FN_VAL_REL_TEC_F (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                                                   p_id_utente IN  PBANDI_T_UTENTE.ID_UTENTE%TYPE DEFAULT NULL,
                                                                   p_funzione_chiamante IN VARCHAR2 DEFAULT 'GET' )
                                                                   RETURN VARCHAR2 AS

    v_return VARCHAR2(1):='N';
	
    CURSOR c1  (c_id_progetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE )IS
		SELECT 'S'
	      FROM PBANDI_T_DICHIARAZIONE_SPESA a
		  JOIN PBANDI_D_TIPO_DICHIARAZ_SPESA b on b.id_tipo_dichiaraz_spesa = a.id_tipo_dichiaraz_spesa
         WHERE a.id_progetto = c_id_progetto
		  -- AND dt_chiusura_validazione IS NOT NULL -- Validata
		   AND b.desc_breve_tipo_dichiara_spesa IN ('F','FC'); -- Finale e Finale con comunicazione
	
																   
BEGIN

   OPEN c1 (p_id_progetto);
   FETCH c1 into v_return;
   IF c1%NOTFOUND THEN
      v_return := 'N';
   END IF;
   CLOSE c1;																   

RETURN v_return;

END FN_VAL_REL_TEC_F;
------------------------------------------------------------------------------------------------------------------------------------------

BEGIN -- Corpo del Package
   BEGIN
       SELECT valore
         INTO g_num_progetti_ben_bl
         FROM PBANDI_C_COSTANTI
        WHERE attributo = 'PCK_PBANDI_PROCESSO.NUM_PROGETTI_BEN_BL';
    EXCEPTION
       WHEN OTHERS THEN
         RAISE_APPLICATION_ERROR(-20000,'Errore accesso a parametro <PCK_PBANDI_PROCESSO.NUM_PROGETTI_BEN_BL> su PBANDI_C_COSTANTI '||SQLERRM);
    END;

END pck_pbandi_processo; 
/