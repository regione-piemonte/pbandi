/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

----
-- migrazione FINPIS
alter table pbandi_t_gestione_revoca add codice_progetto_agev VARCHAR2(100);
alter table pbandi_t_revoca add codice_progetto_agev VARCHAR2(100);
--
----
ALTER  TABLE PBANDI_R_DOMANDA_INDICATORI ADD  VALORE_DOMANDA  NUMBER(16,5);
DROP INDEX AK_PBANDI_R_TIPO_FIL_AL_ENT;
alter table pbandi_t_escussione add data_erogazione date;
alter table pbandi_t_erogazione add CODICE_PROGETTO_AGEV varchar2(100);
alter table pbandi_t_recupero   add CODICE_PROGETTO_AGEV varchar2(100);

alter table pbandi_d_servizi modify NOME_SERVIZIO varchar2(200);


-- pbandi_r_widget_task
create table pbandi_r_widget_task
(id_widget_task number(8),
 id_widget number(8) not null,
 id_task  number(4) not null,
 dt_inizio_validita date not null,
 dt_fine_validita date,
 CONSTRAINT pk_pbandi_r_widget_task  PRIMARY KEY (id_widget_task) USING INDEX TABLESPACE PBANDI_IDX
);

-- fk
ALTER TABLE pbandi_r_widget_task   ADD CONSTRAINT fk_pbandi_d_widget_02
    FOREIGN KEY (id_widget) REFERENCES pbandi_d_widget (id_widget);    
 ALTER TABLE pbandi_r_widget_task    ADD CONSTRAINT fk_pbandi_d_task_03
    FOREIGN KEY (ID_TASK) REFERENCES pbandi_d_task (ID_TASK);
    
-- idx
CREATE UNIQUE INDEX ak_pbandi_r_widget_task_01 ON pbandi_r_widget_task (id_widget,id_task) TABLESPACE PBANDI_IDX;
CREATE INDEX idx_pbandi_r_widget_task_01 ON pbandi_r_widget_task (id_task)  TABLESPACE PBANDI_IDX;

-- seq
 CREATE SEQUENCE SEQ_pbandi_r_widget_task
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;

ALTER  TABLE PBANDI_R_DOMANDA_INDICATORI ADD  VALORE_DOMANDA  NUMBER(16,5);

--------------------
--------------------
-- MONITORAGGIO  TEMPI---
--------------------
--------------------


CREATE OR REPLACE TRIGGER PBANDI.tg_pbandi_t_rich_integr_au_03
-- 6.1.8.2 da pag 39
   after update of DT_INVIO ON PBANDI.PBANDI_T_RICHIESTA_INTEGRAZ    referencing old as OLD new as NEW
   for each row
WHEN (
NEW.ID_ENTITA = 570  -- Gabriele su richiesta Arangino  5/2/24 aggiunto controllo su Entità controlli in loco
      )
DECLARE
   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
        FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';
   -- Da verificare, non specificato in analisi ???
   CURSOR c_cl (c_id_target NUMBER) is
           select id_progetto
             from PBANDI_R_CAMP_CONTR_LOCO
            where ID_CONTROLLO_LOCO = c_id_target;     
       
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
                 NVL(c.flag_monitoraggio_tempi,'N')
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;

   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
   v_id_progetto                  PBANDI_R_CAMP_CONTR_LOCO.id_progetto%TYPE;
   
   
   
   
BEGIN

   -- Verifica se e' attivo il monitoraggio a livello di sistema
   
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;
   
   -- Recupero il progetto accedendo a PBANDI_R_CAMP_CONTR_LOCO
   OPEN c_cl (:NEW.ID_TARGET);
   FETCH c_cl INTO v_id_progetto;
   IF c_cl%NOTFOUND THEN
     RAISE_APPLICATION_ERROR(-20000,'ERRORE ACCESSO PBANDI_R_CAMP_CONTR_LOCO - ID_CONTROLLO_LOCO='||:NEW.ID_TARGET);
   END IF;
   CLOSE c_cl;
   
   -- Verifico se e'¨ attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (:NEW.ID_target);
      FETCH c2 INTO v_progr_bando_linea_intervento,
                    v_monit_tempi;
      IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
   
   IF v_monit_tempi = 'S' THEN
      -- Aggiornamento della data di chiusura della dichiarazione di spesa
      UPDATE PBANDI_T_MONIT_TEMPI
         SET DT_FINE = :NEW.DT_INVIO
        WHERE ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_RICHIESTA_INTEGRAZ')
          AND ID_TARGET = :NEW.ID_RICHIESTA_INTEGRAZ;
   END IF;

END;
/


CREATE OR REPLACE VIEW PBANDI_V_NOTE_GENERALI AS
SELECT ID_NOTA,
       ID_PROGETTO,
       NOME_ENTITA_PROVENIENZA,
       NOTE,
       ID_UTENTE_INS,
       ID_UTENTE_AGG,
       DT_INSERIMENTO,
       DT_AGGIORNAMENTO
FROM
    (--SELECT NULL AS ID_NOTA,
      --  ID_PROGETTO,
      -- 'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
      -- to_char(NOTE) as NOTE,
      -- NULL AS ID_UTENTE_INS,
      -- ID_UTENTE_AGG,
      -- DT_INSERIMENTO,
      -- DT_AGGIORNAMENTO
      -- FROM PBANDI_T_DICH_MENS_WS
      -- UNION ALL
       --SELECT NULL AS ID_NOTA,
       --ID_PROGETTO,
       --'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       --NOTE,
       --ID_UTENTE_INS,
       --ID_UTENTE_AGG,
       --DT_INSERIMENTO,
       --DT_AGGIORNAMENTO
       --FROM PBANDI_T_SERVICER
       --UNION ALL
       --SELECT NULL AS ID_NOTA,
       --ID_PROGETTO,
       --'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       --NOTE,
       --ID_UTENTE_INS,
       --ID_UTENTE_AGG,
       --DT_INSERIMENTO,
       --DT_AGGIORNAMENTO
       --FROM PBANDI_R_PROG_SOGG_FINANZIAT
       --UNION ALL
       --SELECT NULL AS ID_NOTA,
       --ID_PROGETTO,
       --'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       --NOTE,
       --ID_UTENTE_INS,
       --ID_UTENTE_AGG,
       --DT_INSERIMENTO,
       --DT_AGGIORNAMENTO
       --FROM PBANDI_W_PROG_SOGG_FIN_LOG
       --UNION ALL
       --SELECT NULL AS ID_NOTA,
       --ID_PROGETTO,
       --'TUTTE LE AREE' AS NOME_ENTITA_PROVENIENZA,
       --NOTE,
       --NULL AS ID_UTENTE_INS,
       --NULL AS ID_UTENTE_AGG,
       --DT_INSERIMENTO,
       --NULL AS DT_AGGIORNAMENTO
       --FROM PBANDI_W_ATTIVITA_PREGRESSE
       --UNION ALL
       SELECT NULL AS ID_NOTA,
       ID_PROGETTO,
       'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       NOTE,
       ID_UTENTE_INS,
       ID_UTENTE_AGG,
       DT_INSERIMENTO,
       DT_AGGIORNAMENTO
       FROM PBANDI_T_RECUPERO
       /*
       UNION ALL
       SELECT ID_PROGETTO,
       'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       NOTE_CHIUSURA_PROGETTO AS NOTE,
       ID_UTENTE_INS,
       ID_UTENTE_AGG,
       DT_INSERIMENTO,
       DT_AGGIORNAMENTO
       FROM PBANDI_T_PROGETTO_AI
       */
       UNION ALL
       SELECT NULL AS ID_NOTA,
       ID_PROGETTO,
       'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       NOTE_CHIUSURA_PROGETTO  AS NOTE,
       ID_UTENTE_INS,
       ID_UTENTE_AGG,
       DT_INSERIMENTO,
       DT_AGGIORNAMENTO
       FROM PBANDI_T_PROGETTO
       --UNION ALL
       --SELECT NULL AS ID_NOTA,
       --ID_PROGETTO,
       --'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       --NOTE_ATTO AS NOTE,
       --ID_UTENTE_INS,
       --ID_UTENTE_AGG,
       --DT_INSERIMENTO,
       --DT_AGGIORNAMENTO
       --FROM PBANDI_T_ATTO_LIQUIDAZIONE
       UNION ALL
       SELECT NULL AS ID_NOTA,
       ID_PROGETTO,
       'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       NOTE_COMUNICAZ_FINE_PROGETTO AS NOTE,
       ID_UTENTE_INS,
       ID_UTENTE_AGG,
       DT_COMUNICAZIONE AS DT_INSERIMENTO,
       NULL AS DT_AGGIORNAMENTO
       FROM PBANDI_T_COMUNICAZ_FINE_PROG
       --UNION ALL -- PBANDI_T_DETT_CHECK_APPALTI
       --SELECT DISTINCT NULL AS ID_NOTA,
       --ptpa.ID_PROGETTO,
       --'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       --ptdca.NOTE,
       --NULL AS ID_UTENTE_INS,
       --NULL AS ID_UTENTE_AGG,
       --NULL AS DT_INSERIMENTO,
       --NULL AS DT_AGGIORNAMENTO
       --FROM PBANDI_T_PROCEDURA_AGGIUDICAZ ptpa
       --INNER JOIN PBANDI_T_APPALTO pta ON PTPA.ID_PROCEDURA_AGGIUDICAZ = pta.ID_PROCEDURA_AGGIUDICAZ
       --INNER JOIN PBANDI_T_APPALTO_CHECKLIST ptac ON PTA.ID_APPALTO = PTAC.ID_APPALTO
       --INNER JOIN PBANDI_T_DETT_CHECK_APPALTI ptdca ON PTAC.ID_APPALTO_CHECKLIST = PTDCA.ID_APPALTO_CHECKLIST
       UNION ALL
       SELECT NULL AS ID_NOTA,
       ID_PROGETTO,
       'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       NOTE,
       NULL AS ID_UTENTE_INS,
       NULL AS ID_UTENTE_AGG,
       DATA_CAMPIONE AS DT_INSERIMENTO,
       NULL AS DT_AGGIORNAMENTO
       FROM PBANDI_T_IRREGOLARITA_PROVV
       --UNION ALL -- PBANDI_T_INTEGRAZIONE_APPALTO
       --SELECT DISTINCT NULL AS ID_NOTA,
       --ptpa.ID_PROGETTO,
       --'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       --PTIA.NOTE_RICHIESTA AS NOTE,
       --NULL AS ID_UTENTE_INS,
       --NULL AS ID_UTENTE_AGG,
       --PTIA.DT_INVIO AS DT_INSERIMENTO,
       --NULL AS DT_AGGIORNAMENTO
       --FROM PBANDI_T_PROCEDURA_AGGIUDICAZ ptpa
       --INNER JOIN PBANDI_T_APPALTO pta ON PTPA.ID_PROCEDURA_AGGIUDICAZ = pta.ID_PROCEDURA_AGGIUDICAZ
       --INNER JOIN PBANDI_T_INTEGRAZIONE_APPALTO PTIA ON PTPA.ID_PROCEDURA_AGGIUDICAZ = PTA.ID_PROCEDURA_AGGIUDICAZ
       --UNION ALL -- PBANDI_T_ESITI_NOTE_AFFIDAMENT
       --SELECT
       --DISTINCT
       --NULL AS ID_NOTA,
       --ptdi.ID_PROGETTO,
       --'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       --ptena.NOTE,
       --ptena.ID_UTENTE_INS,
       --NULL AS ID_UTENTE_AGG,
       --ptena.DATA_INS AS DT_INSERIMENTO,
       --NULL AS DT_AGGIORNAMENTO
       --FROM PBANDI_T_DOCUMENTO_INDEX ptdi
       --INNER JOIN PBANDI_T_CHECKLIST ptc ON PTDI.ID_DOCUMENTO_INDEX = PTC.ID_DOCUMENTO_INDEX
       --INNER JOIN PBANDI_T_ESITI_NOTE_AFFIDAMENT ptena ON PTC.ID_CHECKLIST = PTENA.ID_CHECKLIST
       --UNION ALL -- PBANDI_T_VARIANTI_AFFIDAMENTI
       --SELECT DISTINCT NULL AS ID_NOTA,
       --ptpa.ID_PROGETTO,
       --'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       --ptva.NOTE,
       --NULL AS  ID_UTENTE_INS,
       --NULL AS ID_UTENTE_AGG,
       --ptva.DT_INSERIMENTO,
       --NULL AS DT_AGGIORNAMENTO
       --FROM PBANDI_T_PROCEDURA_AGGIUDICAZ ptpa
       --INNER JOIN PBANDI_T_APPALTO pta ON PTPA.ID_PROCEDURA_AGGIUDICAZ = PTA.ID_PROCEDURA_AGGIUDICAZ
       --INNER JOIN PBANDI_T_VARIANTI_AFFIDAMENTI ptva ON PTA.ID_APPALTO = PTVA.ID_APPALTO
       --UNION ALL
       --SELECT NULL AS ID_NOTA,
       --ID_PROGETTO_CEDENTE AS ID_PROGETTO,
       --'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       --NOTE_RICEZIONE AS NOTE,
       --ID_UTENTE_INS,
       --ID_UTENTE_AGG,
       --DATA_INSERIMENTO AS DT_INSERIMENTO,
       --DATA_MODIFICA  AS DT_AGGIORNAMENTO
       --FROM PBANDI_T_ECONOMIE
       --UNION ALL
       --SELECT NULL AS ID_NOTA,
       --ID_PROGETTO_CEDENTE AS ID_PROGETTO,
       --'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       --NOTE_CESSIONE  AS NOTE,
       --ID_UTENTE_INS,
       --ID_UTENTE_AGG,
       --DATA_INSERIMENTO AS DT_INSERIMENTO,
       --DATA_MODIFICA  AS DT_AGGIORNAMENTO
       --FROM PBANDI_T_ECONOMIE
       --UNION ALL -- PBANDI_R_PAG_QUOT_PARTE_DOC_SP
       --SELECT DISTINCT NULL AS ID_NOTA,
       --ptqpds.ID_PROGETTO,
       --'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       --prpqpds.NOTE_VALIDAZIONE  AS NOTE,
       --prpqpds.ID_UTENTE_INS,
       --prpqpds.ID_UTENTE_AGG,
       --prpqpds.DT_INSERIMENTO,
       --prpqpds.DT_AGGIORNAMENTO
       --FROM PBANDI_T_QUOTA_PARTE_DOC_SPESA ptqpds
       --INNER JOIN PBANDI_R_PAG_QUOT_PARTE_DOC_SP prpqpds ON PTQPDS.ID_QUOTA_PARTE_DOC_SPESA =PRPQPDS.ID_QUOTA_PARTE_DOC_SPESA
       UNION ALL
       SELECT NULL AS ID_NOTA,
       ID_PROGETTO,
       'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       NOTE_CHIUSURA_VALIDAZIONE  AS NOTE,
       ID_UTENTE_INS,
       ID_UTENTE_AGG,
       DT_DICHIARAZIONE AS DT_INSERIMENTO,
       NULL AS DT_AGGIORNAMENTO
       FROM PBANDI_T_DICHIARAZIONE_SPESA
       UNION ALL
       SELECT NULL AS ID_NOTA,
       ID_PROGETTO,
       'GESTIONE REVOCHE' AS NOME_ENTITA_PROVENIENZA,
       NOTE,
       ID_UTENTE_INS,
       ID_UTENTE_AGG,
       DT_INSERIMENTO,
       DT_AGGIORNAMENTO
       FROM PBANDI_T_REVOCA
       --UNION ALL
       --SELECT NULL AS ID_NOTA,
       --ID_PROGETTO,
       --'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       --NOTE_VALIDAZIONE AS NOTE,
       --ID_UTENTE_INS,
       --ID_UTENTE_AGG,
       --NULL AS DT_INSERIMENTO,
       --NULL AS DT_AGGIORNAMENTO
       --FROM PBANDI_R_DOC_SPESA_PROGETTO
       --UNION ALL -- PBANDI_R_FORNITORE_QUALIFICA
       --SELECT DISTINCT NULL AS ID_NOTA,
       --prsp.ID_PROGETTO,
       --'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       --prfq.NOTE_QUALIFICA AS NOTE,
       --NULL AS ID_UTENTE_INS,
       --NULL AS ID_UTENTE_AGG,
       --prfq.DT_INIZIO_VALIDITA AS DT_INSERIMENTO,
       --NULL AS DT_AGGIORNAMENTO
       --FROM PBANDI_R_SOGGETTO_PROGETTO prsp
       --INNER JOIN PBANDI_T_FORNITORE ptf ON PRSP.ID_SOGGETTO = PTF.ID_SOGGETTO_FORNITORE
       --INNER JOIN PBANDI_R_FORNITORE_QUALIFICA prfq ON PTF.ID_FORNITORE = PRFQ.ID_FORNITORE
       --UNION ALL
       --SELECT NULL AS ID_NOTA,
       --ID_PROGETTO,
       --'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       --NOTE_FIDEIUSSIONE AS NOTE,
       --ID_UTENTE_INS,
       --ID_UTENTE_AGG,
       --DT_INSERIMENTO,
       --DT_AGGIORNAMENTO
       --FROM PBANDI_T_FIDEIUSSIONE
       UNION ALL
       SELECT NULL AS ID_NOTA,
       ID_PROGETTO,
       'GESTIONE REVOCHE' AS NOME_ENTITA_PROVENIENZA,
       NOTE,
       ID_UTENTE_INS,
       ID_UTENTE_AGG,
       DT_INSERIMENTO,
       DT_AGGIORNAMENTO
       FROM PBANDI_T_GESTIONE_REVOCA
       UNION ALL
       SELECT NULL AS ID_NOTA,
       ID_PROGETTO,
       'GESTIONE GARANZIE' AS NOME_ENTITA_PROVENIENZA,
       NOTE,
       ID_UTENTE_INS,
       ID_UTENTE_AGG,
       DT_INSERIMENTO,
       DT_AGGIORNAMENTO
       FROM PBANDI_T_ESCUSSIONE
       UNION ALL -- PBANDI_T_CONTO_ECONOMICO
       SELECT DISTINCT NULL AS ID_NOTA,
       ptp.ID_PROGETTO,
       'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       ptce.NOTE_CONTO_ECONOMICO AS NOTE,
       ptce.ID_UTENTE_INS,
       ptce.ID_UTENTE_AGG,
       ptce.DT_INIZIO_VALIDITA AS DT_INSERIMENTO,
       NULL AS DT_AGGIORNAMENTO
       FROM PBANDI_T_PROGETTO ptp
       INNER JOIN PBANDI_T_DOMANDA ptd ON PTP.ID_DOMANDA = PTD.ID_DOMANDA
       INNER JOIN PBANDI_T_CONTO_ECONOMICO ptce ON PTP.ID_DOMANDA = PTCE.ID_DOMANDA
       UNION ALL
       SELECT NULL AS ID_NOTA,
       ID_PROGETTO,
       'CONTROLLI IN LOCO' AS NOME_ENTITA_PROVENIENZA,
       NOTE,
       NULL AS ID_UTENTE_INS,
       NULL AS ID_UTENTE_AGG,
       DATA_CAMPIONE AS DT_INSERIMENTO,
       NULL AS DT_AGGIORNAMENTO
       FROM PBANDI_T_ESITO_CONTROLLI
       UNION ALL
       SELECT NULL AS ID_NOTA,
       ID_PROGETTO,
       'EROGAZIONE' AS NOME_ENTITA_PROVENIENZA,
       NOTE_EROGAZIONE AS NOTE,
       ID_UTENTE_INS,
       ID_UTENTE_AGG,
       DT_INSERIMENTO,
       DT_AGGIORNAMENTO
       FROM PBANDI_T_EROGAZIONE
       --UNION ALL
       --SELECT NULL AS ID_NOTA,
       --ID_PROGETTO,
       --'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       --NOTE,
       --ID_UTENTE_INS,
       --ID_UTENTE_AGG,
       --NULL AS DT_INSERIMENTO,
       --NULL AS DT_AGGIORNAMENTO
       --FROM PBANDI_W_PROG_SOGG_FINANZIAT
       --UNION ALL
       --SELECT NULL AS ID_NOTA,
       --ID_PROGETTO,
       --'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       --NOTE,
       --ID_UTENTE_INS,
       --ID_UTENTE_AGG,
       --DT_INIZIO_VALIDITA AS DT_INSERIMENTO,
       --DT_AGGIORNAMENTO
       --FROM PBANDI_T_QUADRO_PREVISIONALE
       UNION ALL
       SELECT NULL AS ID_NOTA,
       ID_PROGETTO,
       'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       NOTE_PRATICA_USATA AS NOTE,
       ID_UTENTE_INS,
       ID_UTENTE_AGG,
       DT_INIZIO_VALIDITA AS DT_INSERIMENTO,
       NULL AS DT_AGGIORNAMENTO
       FROM PBANDI_T_IRREGOLARITA
       UNION ALL
       SELECT NULL AS ID_NOTA,
       ID_PROGETTO,
       'RENDICONTAZIONE' AS NOME_ENTITA_PROVENIENZA,
       NOTE,
       ID_UTENTE_INS,
       ID_UTENTE_AGG,
       DT_INIZIO_VALIDITA AS DT_INSERIMENTO,
       NULL AS DT_AGGIORNAMENTO
       FROM PBANDI_T_IRREGOLARITA
       UNION ALL
       SELECT NULL AS ID_NOTA,
       ID_PROGETTO,
       'AREA CREDITI' AS NOME_ENTITA_PROVENIENZA,
       NOTE,
       ID_UTENTE_INS,
       ID_UTENTE_AGG,
       DT_INSERIMENTO,
       DT_AGGIORNAMENTO
       FROM PBANDI_T_ANNOTAZ_GENERALI
       UNION ALL
       SELECT NULL AS ID_NOTA,
       ID_PROGETTO,
           'AREA CREDITI' AS NOME_ENTITA_PROVENIENZA,
           NOTE,
           a.ID_UTENTE_INS,
           a.ID_UTENTE_AGG,
           a.DT_INSERIMENTO,
           a.DT_AGGIORNAMENTO
      FROM PBANDI_V_NOTE_MONITORAGGIO a
      UNION ALL
      SELECT ID_NOTA,
            ID_PROGETTO,
           'GESTIONE NOTE' AS NOME_ENTITA_PROVENIENZA,
           NOTE,
           ID_UTENTE_INS,
           ID_UTENTE_AGG,
           DT_INSERIMENTO,
           DT_AGGIORNAMENTO
      FROM PBANDI_T_NOTE_GENERALI
      ) WHERE NOTE IS NOT NULL;
