/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

ALTER TABLE pbandi_t_csp_soggetto ADD CIVICO varchar2(20);
ALTER TABLE pbandi_t_csp_prog_sede_interv ADD CIVICO varchar2(20);
--
ALTER TABLE PBANDI_R_BANDO_LINEA_INTERVENT MODIFY ID_DEFINIZIONE_PROCESSO NULL;
--
CREATE TABLE PBANDI_D_FREQUENZA 
(
ID_FREQUENZA  INTEGER NOT NULL,
DESCR_FREQUENZA VARCHAR2(20) NOT NULL,
DESCR_BREVE_FREQUENZA VARCHAR2(4000) NOT NULL,
DT_INIZIO_VALIDITA   DATE NOT NULL,
DT_FINE_VALIDITA     DATE NULL
) TABLESPACE PBANDI_SMALL_TBL;
 
ALTER TABLE PBANDI_D_FREQUENZA ADD  
( 
CONSTRAINT PK_PBANDI_D_FREQUENZA PRIMARY KEY ( ID_FREQUENZA)
USING INDEX
TABLESPACE PBANDI_SMALL_IDX 
);

CREATE TABLE PBANDI_D_NOTIFICA_ALERT 
(
ID_NOTIFICA_ALERT    INTEGER NOT NULL,
DESCR_NOTIFICA       VARCHAR2(4000) NOT NULL,
DESCR_BREVE_NOTIFICA VARCHAR2(20) NOT NULL,
DT_INIZIO_VALIDITA   DATE NOT NULL,
DT_FINE_VALIDITA     DATE NULL
) TABLESPACE PBANDI_SMALL_TBL;

ALTER TABLE PBANDI_D_NOTIFICA_ALERT  ADD  
( 
CONSTRAINT PK_PBANDI_D_NOTIFICA_ALERT PRIMARY KEY (ID_NOTIFICA_ALERT)
 USING INDEX  TABLESPACE PBANDI_SMALL_IDX 
);

CREATE TABLE PBANDI_R_NOTIF_ISTR_PROGETTI (
ID_SOGGETTO_NOTIFICA INTEGER NOT NULL,
ID_PROGETTO          NUMBER(8) NOT NULL
) TABLESPACE PBANDI_MEDIUM_TBL;

ALTER TABLE PBANDI_R_NOTIF_ISTR_PROGETTI    ADD 
(CONSTRAINT PK_PBANDI_R_NOTIF_ISTR_PROGETT PRIMARY KEY (ID_SOGGETTO_NOTIFICA, ID_PROGETTO)
USING INDEX TABLESPACE PBANDI_MEDIUM_IDX);

CREATE TABLE PBANDI_T_EMAIL_SOGGETTO (
ID_SOGGETTO NUMBER(8) NOT NULL,
EMAIL_SOGGETTO  VARCHAR2(100) NOT NULL
) TABLESPACE PBANDI_SMALL_TBL ;

ALTER TABLE PBANDI_T_EMAIL_SOGGETTO ADD 
(CONSTRAINT PK_PBANDI_T_EMAIL_SOGGETTO PRIMARY KEY (ID_SOGGETTO) ) ;

CREATE TABLE PBANDI_T_INVIO_MAIL_NOTIFICHE
(
 ID_INVIO_EMAIL       INTEGER NOT NULL,
 DT_INVIO_EMAIL       DATE NOT NULL,
 ID_SOGGETTO_NOTIFICA INTEGER NOT NULL
) TABLESPACE PBANDI_MEDIUM_TBL;

CREATE INDEX IE1_PBANDI_T_INVIO_MAIL_NOTIFI ON PBANDI_T_INVIO_MAIL_NOTIFICHE
(ID_SOGGETTO_NOTIFICA);

ALTER TABLE PBANDI_T_INVIO_MAIL_NOTIFICHE  ADD 
(CONSTRAINT PK_PBANDI_T_INVIO_MAIL_NOTIFIC PRIMARY KEY (ID_INVIO_EMAIL)
USING INDEX  TABLESPACE PBANDI_MEDIUM_IDX );

CREATE TABLE PBANDI_T_NOTIFICHE_ISTRUTTORE
 (
  ID_SOGGETTO_NOTIFICA INTEGER NOT NULL,
  ID_SOGGETTO          NUMBER(8) NULL,
  ID_NOTIFICA_ALERT    INTEGER NOT NULL,
  ID_FREQUENZA         INTEGER NOT NULL,
  DT_INIZIO_VALIDITA   DATE NULL,
  DT_FINE_VALIDITA     DATE NULL
 ) TABLESPACE PBANDI_MEDIUM_TBL;

CREATE INDEX IE1_PBANDI_T_NOTIFICHE_ISTRUTT ON PBANDI_T_NOTIFICHE_ISTRUTTORE
(ID_FREQUENZA)  TABLESPACE PBANDI_MEDIUM_IDX;
CREATE INDEX IE2_PBANDI_T_NOTIFICHE_ISTRUTT ON PBANDI_T_NOTIFICHE_ISTRUTTORE
(ID_NOTIFICA_ALERT) TABLESPACE PBANDI_MEDIUM_IDX;
CREATE INDEX IE3_PBANDI_T_NOTIFICHE_ISTRUTT ON PBANDI_T_NOTIFICHE_ISTRUTTORE
(ID_SOGGETTO) TABLESPACE PBANDI_MEDIUM_IDX;

ALTER TABLE PBANDI_T_NOTIFICHE_ISTRUTTORE ADD 
(CONSTRAINT PK_PBANDI_T_NOTIF_ISTRUTT PRIMARY KEY (ID_SOGGETTO_NOTIFICA)
USING INDEX  TABLESPACE PBANDI_MEDIUM_IDX );

-- DEFINIZIONE FK
ALTER TABLE PBANDI_T_NOTIFICHE_ISTRUTTORE ADD 
(CONSTRAINT FK_PBANDI_D_FREQUENZA_01  FOREIGN KEY (ID_FREQUENZA) REFERENCES PBANDI_D_FREQUENZA);
ALTER TABLE PBANDI_T_NOTIFICHE_ISTRUTTORE  ADD 
(CONSTRAINT FK_PBANDI_D_NOTIFICA_ALERT_01 FOREIGN KEY (ID_NOTIFICA_ALERT) REFERENCES PBANDI_D_NOTIFICA_ALERT);
ALTER TABLE PBANDI_T_NOTIFICHE_ISTRUTTORE ADD 
(CONSTRAINT FK_PBANDI_T_SOGGETTO_22 FOREIGN KEY (ID_SOGGETTO) REFERENCES PBANDI_T_SOGGETTO );
ALTER TABLE PBANDI_T_INVIO_MAIL_NOTIFICHE  ADD  
(CONSTRAINT FK_PBANDI_T_NOTIF_ISTR_01 FOREIGN KEY (ID_SOGGETTO_NOTIFICA) REFERENCES PBANDI_T_NOTIFICHE_ISTRUTTORE );
ALTER TABLE PBANDI_R_NOTIF_ISTR_PROGETTI   ADD  
(CONSTRAINT FK_PBANDI_T_NOTIF_ISTR_02  FOREIGN KEY (ID_SOGGETTO_NOTIFICA) REFERENCES PBANDI_T_NOTIFICHE_ISTRUTTORE );
ALTER TABLE PBANDI_R_NOTIF_ISTR_PROGETTI  ADD 
(CONSTRAINT FK_PBANDI_T_PROGETTO_36 FOREIGN KEY (ID_PROGETTO)  REFERENCES PBANDI_T_PROGETTO);
ALTER TABLE PBANDI_T_EMAIL_SOGGETTO  ADD 
(CONSTRAINT FK_PBANDI_T_SOGGETTO_23 FOREIGN KEY (ID_SOGGETTO) REFERENCES PBANDI_T_SOGGETTO );

--
-- VISTA
-- PBANDI_V_SOGGETTO_PROGETTO
CREATE OR REPLACE FORCE VIEW PBANDI.PBANDI_V_SOGGETTO_PROGETTO
(
   COD_UTENTE,
   DESC_BREVE_TIPO_ANAGRAFICA,
   ID_PROGETTO,
   PROGR_BANDO_LINEA_INTERVENTO,
   NOME_BANDO_LINEA,
   CODICE_FISCALE_SOGGETTO,
   ID_SOGGETTO,
   ID_TIPO_ANAGRAFICA,
   DT_INIZIO_VALIDITA,
   DT_FINE_VALIDITA,
   PROGR_SOGGETTO_PROGETTO,
   FLAG_AGGIORNATO_FLUX,
   ID_TIPO_SOGGETTO,
   ID_UTENTE_AGG,
   ID_UTENTE_INS,
   ID_ISTANZA_PROCESSO,
   CODICE_VISUALIZZATO_PROGETTO,
   TITOLO_PROGETTO,
   ID_PROCESSO
)
AS
   SELECT    m.id_soggetto
          || '_'
          || m.codice_fiscale_soggetto
          || '@'
          || dta.desc_breve_tipo_anagrafica
             AS cod_utente,
          dta.desc_breve_tipo_anagrafica,
          m.ID_PROGETTO,
          m.PROGR_BANDO_LINEA_INTERVENTO,
          m.NOME_BANDO_LINEA,
          m.CODICE_FISCALE_SOGGETTO,
          m.ID_SOGGETTO,
          m.ID_TIPO_ANAGRAFICA,
          m.DT_INIZIO_VALIDITA,
          m.DT_FINE_VALIDITA,
          m.PROGR_SOGGETTO_PROGETTO,
          m.FLAG_AGGIORNATO_FLUX,
          m.ID_TIPO_SOGGETTO,
          m.ID_UTENTE_AGG,
          m.ID_UTENTE_INS,
          m.ID_ISTANZA_PROCESSO,
          m.CODICE_VISUALIZZATO_PROGETTO,
          m.TITOLO_PROGETTO,
          m.ID_PROCESSO
     FROM pbandi_d_tipo_anagrafica dta,
          (SELECT tp.id_progetto,
                  rbli.progr_bando_linea_intervento,
                  rbli.nome_bando_linea,
                  ts.codice_fiscale_soggetto,
                  ts.id_soggetto,
                  ts.id_tipo_anagrafica,
                  ts.dt_inizio_validita,
                  ts.dt_fine_validita,
                  NULL progr_soggetto_progetto,
                  ts.flag_aggiornato_flux,
                  ts.id_tipo_soggetto,
                  ts.id_utente_agg,
                  ts.id_utente_ins,
                  tp.id_istanza_processo,
                  tp.codice_visualizzato codice_visualizzato_progetto,
                  tp.titolo_progetto,
                  rbli.id_processo
             FROM (SELECT ts1.*,
                          tec.id_ente_competenza,
                          rsta.id_tipo_anagrafica,
                          rsta.dt_inizio_validita,
                          rsta.dt_fine_validita,
                          rsta.flag_aggiornato_flux
                     FROM pbandi_t_soggetto ts1,
                          pbandi_t_ente_competenza tec,
                          pbandi_r_sogg_tipo_anagrafica rsta
                    WHERE rsta.id_soggetto = ts1.id_soggetto
                          AND rsta.id_tipo_anagrafica NOT IN
                                 (SELECT dta.id_tipo_anagrafica
                                    FROM pbandi_d_tipo_anagrafica dta
                                   WHERE dta.desc_breve_tipo_anagrafica IN
                                            ('PERSONA-FISICA',
                                             'OI-ISTRUTTORE',
                                             'ADG-ISTRUTTORE'))
                          AND (rsta.id_tipo_anagrafica NOT IN
                                  (SELECT dta.id_tipo_anagrafica
                                     FROM pbandi_d_tipo_anagrafica dta
                                    WHERE dta.desc_breve_tipo_anagrafica IN
                                             ('BEN-MASTER',
                                              'OI-IST-MASTER',
                                              'ADG-IST-MASTER',
                                              'CREATOR'))
                               OR (NOT EXISTS
                                          (SELECT 'x'
                                             FROM pbandi_r_ente_competenza_sogg recs
                                            WHERE recs.id_soggetto =
                                                     ts1.id_soggetto
                                                  AND NVL (
                                                         TRUNC (
                                                            recs.dt_fine_validita),
                                                         TRUNC (SYSDATE + 1)) >
                                                         TRUNC (SYSDATE))
                                   OR EXISTS
                                         (SELECT 'x'
                                            FROM pbandi_r_ente_competenza_sogg recs
                                           WHERE recs.id_soggetto =
                                                    ts1.id_soggetto
                                                 AND recs.id_ente_competenza =
                                                        tec.id_ente_competenza
                                                 AND NVL (
                                                        TRUNC (
                                                           recs.dt_fine_validita),
                                                        TRUNC (SYSDATE + 1)) >
                                                        TRUNC (SYSDATE))))) ts,
                  (SELECT td.progr_bando_linea_intervento, tp.*
                     FROM pbandi_t_domanda td, pbandi_t_progetto tp
                    WHERE td.id_domanda = tp.id_domanda) tp,
                  pbandi_r_bando_linea_intervent rbli,
                  pbandi_r_bando_linea_ente_comp rble
            WHERE rbli.progr_bando_linea_intervento =
                     tp.progr_bando_linea_intervento(+)
                  AND rble.progr_bando_linea_intervento =
                         rbli.progr_bando_linea_intervento
                  AND rble.id_ruolo_ente_competenza =
                         (SELECT dre.id_ruolo_ente_competenza
                            FROM pbandi_d_ruolo_ente_competenza dre
                           WHERE dre.desc_breve_ruolo_ente = 'DESTINATARIO')
                  AND ts.id_ente_competenza = rble.id_ente_competenza
           UNION ALL
           SELECT rsp.id_progetto id_progetto,
                  rbli.progr_bando_linea_intervento,
                  rbli.nome_bando_linea,
                  ts.codice_fiscale_soggetto,
                  ts.id_soggetto,
                  rsp.id_tipo_anagrafica,
                  rsp.dt_inizio_validita,
                  rsp.dt_fine_validita,
                  rsp.progr_soggetto_progetto,
                  rsp.flag_aggiornato_flux,
                  ts.id_tipo_soggetto,
                  ts.id_utente_agg,
                  ts.id_utente_ins,
                  tp.id_istanza_processo,
                  tp.codice_visualizzato codice_visualizzato_progetto,
                  tp.titolo_progetto,
                  rbli.id_processo
             FROM PBANDI_T_SOGGETTO ts,
                  pbandi_r_soggetto_progetto rsp,
                  pbandi_t_domanda td,
                  pbandi_r_bando_linea_intervent rbli,
                  pbandi_t_progetto tp
            WHERE ts.id_soggetto = rsp.id_soggetto
                  AND td.id_domanda = tp.id_domanda
                  AND rbli.progr_bando_linea_intervento =
                         td.progr_bando_linea_intervento
                  AND tp.id_progetto = rsp.id_progetto) m
    WHERE m.id_tipo_anagrafica = dta.id_tipo_anagrafica;

--PBANDI_V_TASK_RUOLO
CREATE OR REPLACE FORCE VIEW PBANDI.PBANDI_V_TASK_RUOLO
(
   ID_RUOLO_DI_PROCESSO,
   CODICE,
   ID_TIPO_ANAGRAFICA,
   DESC_BREVE_TIPO_ANAGRAFICA,
   DESC_TIPO_ANAGRAFICA,
   ID_STEP_PROCESSO,
   ID_PROCESSO,
   DESCR_PROCESSO,
   ID_TASK,
   DESCR_TASK,
   DESCR_BREVE_TASK
)
AS
   SELECT a.ID_RUOLO_DI_PROCESSO,
          a.CODICE,
          c.ID_TIPO_ANAGRAFICA,
          c.DESC_BREVE_TIPO_ANAGRAFICA,
          c.DESC_TIPO_ANAGRAFICA,
          h.id_step_processo,
          m.id_processo,
          m.descr_processo,
          i.id_task,
          l.DESCR_TASK,
          l.descr_breve_task
     FROM PBANDI_C_RUOLO_DI_PROCESSO a,
          PBANDI_R_RUOLO_TIPO_ANAGRAFICA b,
          PBANDI_D_TIPO_ANAGRAFICA c,
          PBANDI_R_STEP_PROCESSO_RUOLO h,
          PBANDI_T_STEP_PROCESSO i,
          PBANDI_D_TASK l,
          PBANDI_T_PROCESSO m
    WHERE     a.id_ruolo_di_processo = b.id_ruolo_di_processo
          AND b.id_tipo_anagrafica = c.id_tipo_anagrafica
          AND h.id_ruolo_di_processo = b.id_ruolo_di_processo
          AND i.id_step_processo = h.id_step_processo
          AND l.id_task = i.id_task
          AND m.id_processo = i.id_processo;
		  
--BANDI_V_TASK_RUOLO_UTENTE
CREATE OR REPLACE FORCE VIEW PBANDI.PBANDI_V_TASK_RUOLO_UTENTE
(
   ID_RUOLO_DI_PROCESSO,
   CODICE,
   ID_UTENTE,
   ID_TIPO_SOGGETTO_CORRELATO,
   DESC_BREVE_TIPO_SOGG_CORRELATO,
   ID_TIPO_ANAGRAFICA,
   DESC_BREVE_TIPO_ANAGRAFICA,
   DESC_TIPO_ANAGRAFICA,
   CODICE_FISCALE_SOGGETTO,
   ID_SOGGETTO,
   ID_STEP_PROCESSO,
   ID_PROCESSO,
   DESCR_PROCESSO,
   ID_TASK,
   DESCR_TASK
)
AS
   SELECT a.ID_RUOLO_DI_PROCESSO,
          a.CODICE,
          f.ID_UTENTE,
          b.ID_TIPO_SOGGETTO_CORRELATO,
          g.DESC_BREVE_TIPO_SOGG_CORRELATO,
          c.ID_TIPO_ANAGRAFICA,
          c.DESC_BREVE_TIPO_ANAGRAFICA,
          c.DESC_TIPO_ANAGRAFICA,
          e.CODICE_FISCALE_SOGGETTO,
          f.ID_SOGGETTO,
          h.id_step_processo,
          m.id_processo,
          m.descr_processo,
          i.id_task,
          l.DESCR_TASK
     FROM PBANDI_C_RUOLO_DI_PROCESSO a,
          PBANDI_R_RUOLO_TIPO_ANAGRAFICA b,
          PBANDI_D_TIPO_ANAGRAFICA c,
          PBANDI_R_SOGG_TIPO_ANAGRAFICA d,
          PBANDI_T_SOGGETTO e,
          PBANDI_T_UTENTE f,
          PBANDI_D_TIPO_SOGG_CORRELATO g,
          PBANDI_R_STEP_PROCESSO_RUOLO h,
          PBANDI_T_STEP_PROCESSO i,
          PBANDI_D_TASK l,
          PBANDI_T_PROCESSO m
    WHERE     f.id_soggetto = e.id_soggetto
          AND e.id_soggetto = d.id_soggetto
          AND d.DT_FINE_VALIDITA IS NULL
          AND d.id_tipo_anagrafica = c.id_tipo_anagrafica
          AND b.id_tipo_anagrafica = c.id_tipo_anagrafica
          AND a.id_ruolo_di_processo = b.id_ruolo_di_processo
          AND g.id_tipo_soggetto_correlato(+) = b.id_tipo_soggetto_correlato
          AND h.id_ruolo_di_processo(+) = a.id_ruolo_di_processo
          AND i.id_step_processo(+) = h.id_step_processo
          AND l.id_task(+) = i.id_task
          AND m.id_processo(+) = i.id_processo;

--	PBANDI_V_STEP_PROCESSO	  
CREATE OR REPLACE FORCE VIEW PBANDI.PBANDI_V_STEP_PROCESSO
(
   ID_STEP_PROCESSO,
   ID_PROCESSO,
   ID_TASK,
   DESCR_BREVE_TASK,
   DESCR_TASK,
   ID_METODO_TASK,
   ID_METODO_TASK_INTEGR,
   ID_MULTI_TASK,
   ID_TIPO_TASK,
   DESCR_BREVE_TIPO_TASK,
   DESCR_TIPO_TASK,
   FLAG_OPT,
   FLAG_PUBLIC,
   ID_TASK_PREC,
   DESCR_BREVE_TASK_PREC,
   DESCR_TASK_PREC
)
AS
   SELECT a.id_step_processo,
          A.ID_PROCESSO,
          A.ID_TASK,
          B.DESCR_BREVE_TASK,
          B.DESCR_TASK,
          B.ID_METODO_TASK,
          B.ID_METODO_TASK_INTEGR,
          B.ID_MULTI_TASK,
          B.ID_TIPO_TASK,
          C.DESCR_BREVE_TIPO_TASK,
          C.DESCR_TIPO_TASK,
          C.FLAG_OPT,
          C.FLAG_PUBLIC,
          A.ID_TASK_PREC,
          D.DESCR_BREVE_TASK DESCR_BREVE_TASK_PREC,
          D.DESCR_TASK DESCR_TASK_PREC
     FROM pbandi_t_step_processo a,
          pbandi_d_task b,
          pbandi_d_tipo_task c,
          pbandi_d_task d
    WHERE     b.id_task = a.id_task
          AND c.id_tipo_task = b.id_tipo_task
          AND d.ID_TASK(+) = A.ID_TASK_PREC;		  

--
-- SEQUENCE
CREATE SEQUENCE SEQ_PBANDI_D_FREQUENZA
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;
CREATE SEQUENCE SEQ_PBANDI_D_NOTIFICA_ALERT
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;  
CREATE SEQUENCE SEQ_PBANDI_T_NOTIFICHE_ISTR
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;
CREATE SEQUENCE SEQ_PBANDI_T_INVIO_MAIL_NOT
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;
-- 