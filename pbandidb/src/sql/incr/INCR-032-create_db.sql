/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

ALTER TABLE PBANDI_W_ATTI_LIQUIDAZIONE_DL DISABLE CONSTRAINTS FK_PBANDI_W_ATTI_LIQUIDA_DT_01;
ALTER TABLE PBANDI_W_ATTI_LIQUIDAZIONE_DM DISABLE CONSTRAINTS FK_PBANDI_W_ATTI_LIQUIDA_DT_02;

TRUNCATE TABLE PBANDI_W_ATTI_LIQUIDAZIONE_DL DROP STORAGE;
TRUNCATE TABLE PBANDI_W_ATTI_LIQUIDAZIONE_DM DROP STORAGE;
TRUNCATE TABLE PBANDI_W_ATTI_LIQUIDAZIONE_DT DROP STORAGE;

ALTER TABLE PBANDI_W_ATTI_LIQUIDAZIONE_DL ENABLE CONSTRAINTS FK_PBANDI_W_ATTI_LIQUIDA_DT_01;
ALTER TABLE PBANDI_W_ATTI_LIQUIDAZIONE_DM ENABLE CONSTRAINTS FK_PBANDI_W_ATTI_LIQUIDA_DT_02;

DROP SEQUENCE SEQ_PBANDI_W_ATTI_LIQUIDAZI_DL;
CREATE SEQUENCE SEQ_PBANDI_W_ATTI_LIQUIDAZI_DL
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE;

DROP SEQUENCE SEQ_PBANDI_W_ATTI_LIQUIDAZI_DM; 
CREATE SEQUENCE SEQ_PBANDI_W_ATTI_LIQUIDAZI_DM
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE;

DROP SEQUENCE SEQ_PBANDI_W_ATTI_LIQUIDAZI_DT;
CREATE SEQUENCE SEQ_PBANDI_W_ATTI_LIQUIDAZI_DT
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE;  

DROP  SEQUENCE SEQ_PBANDI_W_IMPEGNI;
CREATE SEQUENCE SEQ_PBANDI_W_IMPEGNI
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE;

-- PBANDI_T_APPALTO
ALTER TABLE PBANDI_T_APPALTO ADD 
(IMP_RIBASSO_ASTA NUMBER (17,2),
 PERC_RIBASSO_ASTA NUMBER (5,2),
 IMP_RENDICONTABILE NUMBER (17,2));

-- PBANDI_D_SOGGETTO_FINANZIATORE
ALTER TABLE PBANDI_D_SOGGETTO_FINANZIATORE ADD COD_IGRUE_TC33 VARCHAR2(10);

-- PBANDI_C_CHECKLIST_MODEL_HTML
CREATE TABLE PBANDI_C_CHECKLIST_MODEL_HTML 
(ID_MODELLO NUMBER(3),
 HTML CLOB);
-- PK
ALTER TABLE PBANDI_C_CHECKLIST_MODEL_HTML  ADD  
 ( 
  CONSTRAINT PK_PBANDI_C_CHECKLIST_MOD_HTML PRIMARY KEY (ID_MODELLO)
  USING INDEX
  TABLESPACE PBANDI_IDX 
 );
-- FK
 ALTER TABLE PBANDI_C_CHECKLIST_MODEL_HTML ADD 
CONSTRAINT FK_PBANDI_C_MODELLO_03
 FOREIGN KEY (ID_MODELLO)
 REFERENCES PBANDI_C_MODELLO (ID_MODELLO);

--
-- PBANDI_T_CHECKLIST_HTML
CREATE TABLE PBANDI_T_CHECKLIST_HTML
(ID_CHECKLIST NUMBER(8),
 HTML CLOB);
-- PK
ALTER TABLE PBANDI_T_CHECKLIST_HTML ADD  
 ( 
  CONSTRAINT PK_PBANDI_T_CHECKLIST_HTML PRIMARY KEY (ID_CHECKLIST)
  USING INDEX
  TABLESPACE PBANDI_IDX 
 );
-- FK
 ALTER TABLE PBANDI_T_CHECKLIST_HTML ADD 
CONSTRAINT FK_PBANDI_T_CHECKLIST_03
 FOREIGN KEY (ID_CHECKLIST)
 REFERENCES PBANDI_T_CHECKLIST (ID_CHECKLIST);
-- PBANDI_T_APPALTO
ALTER TABLE PBANDI_T_APPALTO ADD (
SOPRA_SOGLIE VARCHAR2(1) ); 
-- check
ALTER TABLE PBANDI_T_APPALTO ADD
(CONSTRAINT CHK_SOPRA_SOGLIE CHECK (SOPRA_SOGLIE IN ('S', 'N', NULL)) );

-- PBANDI_T_ATTO_LIQUIDAZIONE
ALTER TABLE PBANDI_T_ATTO_LIQUIDAZIONE ADD
(NUMERO_DOCUMENTO_SPESA VARCHAR2(200));
--PBANDI_W_ATTI_LIQUIDAZIONE_DT
ALTER TABLE PBANDI_W_ATTI_LIQUIDAZIONE_DT ADD
(NUMERO_DOCUMENTO_SPESA VARCHAR2(200));

--INDEX
CREATE INDEX IE1_PBANDI_T_ATTO_LIQUIDAZIONE ON PBANDI_T_ATTO_LIQUIDAZIONE (NUMERO_DOCUMENTO_SPESA)  TABLESPACE PBANDI_IDX;

--CONSTRAINTS
ALTER TABLE PBANDI_T_ATTO_LIQUIDAZIONE DROP  CONSTRAINT FK_PBANDI_T_BENEFI_BILANCIO_02;
ALTER TABLE PBANDI_T_ATTO_LIQUIDAZIONE DROP   CONSTRAINT FK_PBANDI_T_BENEFI_BILANCIO_03;
ALTER TABLE PBANDI_T_ATTO_LIQUIDAZIONE DROP   CONSTRAINT FK_PBANDI_T_DATI_PAGAM_ATTO_02;

-- PBANDI_L_LOG_STATO_ELAB_DOC
CREATE TABLE PBANDI_L_LOG_STATO_ELAB_DOC
( ID_CHIAMATA NUMBER NOT NULL,
  ID_ATTO_LIQUIDAZIONE NUMBER(8) NOT NULL, -- fk (PBANDI_T_ATTO_LIQUIDAZIONE.id_atto_liquidazione)number not null
  ESITO_ELABORA_DOCUMENTO VARCHAR2(10),
  DT_ELABORA_DOCUMENTO DATE,
  ID_OPERAZIONE_ASINCRONA NUMBER,
  ERRORE_ELABORA_DOCUMENTO VARCHAR2(500),
  ESITO_LEGGI_STATO VARCHAR2(10),
  DT_LEGGI_STATO DATE,
  STATO_ELABORAZIONE VARCHAR2(10),
  ERRORE_LEGGI_STATO VARCHAR2(500));
 --PK
 ALTER TABLE PBANDI_L_LOG_STATO_ELAB_DOC  ADD  
 (CONSTRAINT PK_PBANDI_L_LOG_STATO_ELAB_DOC PRIMARY KEY (ID_CHIAMATA)
  USING INDEX
  TABLESPACE PBANDI_IDX
  );
 --FK
 ALTER TABLE PBANDI_L_LOG_STATO_ELAB_DOC ADD 
CONSTRAINT FK_PBANDI_T_ATTO_LIQUIDAZIO_02
 FOREIGN KEY (ID_ATTO_LIQUIDAZIONE)
 REFERENCES PBANDI_T_ATTO_LIQUIDAZIONE (ID_ATTO_LIQUIDAZIONE);
 -- SEQUENCE
 CREATE SEQUENCE SEQ_PBANDI_L_LOG_ST_ELAB_DOC
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;
 --
 -- PBANDI_D_SOGGETTO_FINANZIATORE
    ALTER TABLE PBANDI_D_SOGGETTO_FINANZIATORE ADD (FLAG_UE VARCHAR2(1));
-- CHECK
    ALTER TABLE PBANDI_D_SOGGETTO_FINANZIATORE ADD
   (CONSTRAINT CHK_FLAG_UE CHECK (FLAG_UE IN ('S', 'N', NULL)) );
--
-- PBANDI_T_BENEFICIARIO_BILANCIO
ALTER TABLE PBANDI_T_BENEFICIARIO_BILANCIO MODIFY CODICE_BENEFICIARIO_BILANCIO NUMBER(9);
-- PBANDI_W_ATTI_LIQUIDAZIONE_DT
ALTER TABLE  PBANDI_W_ATTI_LIQUIDAZIONE_DT MODIFY CODBEN NUMBER(9);
--
-- PBANDI_W_ATTI_LIQUIDAZIONE_DL
ALTER TABLE PBANDI_W_ATTI_LIQUIDAZIONE_DL ADD STATO VARCHAR2(15);
--PBANDI_W_ATTI_LIQUIDAZIONE_DT
ALTER TABLE PBANDI_W_ATTI_LIQUIDAZIONE_DT MODIFY SETTATTO VARCHAR2(3);
--PBANDI_W_ATTI_LIQUIDAZIONE_DT
ALTER TABLE PBANDI_W_ATTI_LIQUIDAZIONE_DT ADD DESC_STATO_DOCUMENTO VARCHAR2(25);
--PBANDI_T_ATTO_LIQUIDAZIONE
ALTER TABLE PBANDI_T_ATTO_LIQUIDAZIONE ADD DESC_STATO_DOCUMENTO VARCHAR2(25);
-- PBANDI_W_ATTI_LIQUIDAZIONE
UPDATE PBANDI_W_ATTI_LIQUIDAZIONE_DT SET STATO = NULL ;
COMMIT;
--
--PBANDI_W_ATTI_LIQUIDAZIONE_DT
ALTER TABLE PBANDI_W_ATTI_LIQUIDAZIONE_DT MODIFY STATO VARCHAR2(2);
--
-- PBANDI_T_CHECKLIST
ALTER TABLE PBANDI_T_CHECKLIST ADD REFERENTE_BENEFICIARIO VARCHAR2(300);

-- PBANDI_R_DICH_SPESA_DOC_ALLEG
CREATE TABLE PBANDI_R_DICH_SPESA_DOC_ALLEG
( ID_DICHIARAZIONE_SPESA  NUMBER(8),
  ID_MICRO_SEZIONE_MODULO  NUMBER(8),
  NUM_ORDINAMENTO_MICRO_SEZIONE NUMBER(3),
  FLAG_ALLEGATO VARCHAR2(1));
  
 --PK
 ALTER TABLE PBANDI_R_DICH_SPESA_DOC_ALLEG  ADD  
 (CONSTRAINT PK_PBANDI_R_DICH_SPESA_DOC_ALL PRIMARY KEY (ID_DICHIARAZIONE_SPESA,ID_MICRO_SEZIONE_MODULO,NUM_ORDINAMENTO_MICRO_SEZIONE)
  USING INDEX
  TABLESPACE PBANDI_IDX
  );
  
 ALTER TABLE PBANDI_R_DICH_SPESA_DOC_ALLEG ADD
   (CONSTRAINT CHK_FLAG_ALLEGATO CHECK (FLAG_ALLEGATO IN ('S', 'N')) );
   
--INDEX
CREATE INDEX IE1_PBANDI_R_DICH_SPES_DOC_ALL ON PBANDI_R_DICH_SPESA_DOC_ALLEG (ID_MICRO_SEZIONE_MODULO)  TABLESPACE PBANDI_IDX;

 --FK
 ALTER TABLE PBANDI_R_DICH_SPESA_DOC_ALLEG ADD 
CONSTRAINT FK_PBANDI_T_DICHIARAZ_SPESA_05
 FOREIGN KEY (ID_DICHIARAZIONE_SPESA)
 REFERENCES PBANDI_T_DICHIARAZIONE_SPESA (ID_DICHIARAZIONE_SPESA);
 
--FK
 ALTER TABLE PBANDI_R_DICH_SPESA_DOC_ALLEG ADD 
CONSTRAINT FK_PBANDI_D_MICRO_SEZI_MOD_03
 FOREIGN KEY (ID_MICRO_SEZIONE_MODULO)
 REFERENCES PBANDI_D_MICRO_SEZIONE_MODULO (ID_MICRO_SEZIONE_MODULO);

-- PBANDI_W_PROGETTO_DOC_ALLEG
CREATE TABLE PBANDI_W_PROGETTO_DOC_ALLEG
( ID_PROGETTO  NUMBER(8),
  ID_MICRO_SEZIONE_MODULO  NUMBER(8),
  NUM_ORDINAMENTO_MICRO_SEZIONE NUMBER(3),
  FLAG_ALLEGATO VARCHAR2(1));
  
 --PK
 ALTER TABLE PBANDI_W_PROGETTO_DOC_ALLEG  ADD  
 (CONSTRAINT PK_PBANDI_W_PROGETTO_DOC_ALLEG PRIMARY KEY (ID_PROGETTO,ID_MICRO_SEZIONE_MODULO,NUM_ORDINAMENTO_MICRO_SEZIONE)
  USING INDEX
  TABLESPACE PBANDI_IDX
  );
  
 ALTER TABLE PBANDI_W_PROGETTO_DOC_ALLEG ADD
   (CONSTRAINT CHK_FLAG_ALLEGATOW CHECK (FLAG_ALLEGATO IN ('S', 'N')) );
   
--INDEX
CREATE INDEX IE1_PBANDI_W_PROG_DOC_ALLEG ON PBANDI_W_PROGETTO_DOC_ALLEG (ID_MICRO_SEZIONE_MODULO)  TABLESPACE PBANDI_IDX;

 --FK
 ALTER TABLE PBANDI_W_PROGETTO_DOC_ALLEG ADD 
CONSTRAINT FK_PBANDI_T_PROGETTO_43
 FOREIGN KEY (ID_PROGETTO)
 REFERENCES PBANDI_T_PROGETTO (ID_PROGETTO);
   
--FK
 ALTER TABLE PBANDI_W_PROGETTO_DOC_ALLEG ADD 
CONSTRAINT FK_PBANDI_D_MICRO_SEZI_MOD_04
 FOREIGN KEY (ID_MICRO_SEZIONE_MODULO)
 REFERENCES PBANDI_D_MICRO_SEZIONE_MODULO (ID_MICRO_SEZIONE_MODULO);
 
 -- PBANDI_D_STATO_ATTO
ALTER TABLE PBANDI_D_STATO_ATTO ADD COD_STATO_CONTABILIA VARCHAR2(2);
ALTER TABLE PBANDI_D_STATO_ATTO ADD DESCR_STATO_CONTABILIA VARCHAR2(50);
--PBANDI_W_IMPEGNI
ALTER TABLE PBANDI_W_IMPEGNI MODIFY DIREZIONEPROVENIENZACAPITOLO VARCHAR2(6);
 
 -- VISTE
 CREATE OR REPLACE FORCE VIEW PBANDI_V_SOGGETTO_PROGETTO
(
   COD_UTENTE,
   DESC_BREVE_TIPO_ANAGRAFICA,
   ID_PROGETTO,
   ID_PROGETTO_FINANZ,
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
		  m.ID_PROGETTO_FINANZ,
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
		          tp.id_progetto_finanz,
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
                          pbandi_d_tipo_anagrafica dta,
                          pbandi_r_sogg_tipo_anagrafica rsta
                    WHERE rsta.id_soggetto = ts1.id_soggetto
                          AND rsta.id_tipo_anagrafica =
                                 dta.id_tipo_anagrafica
                          AND dta.desc_breve_tipo_anagrafica NOT IN
                                 ('PERSONA-FISICA',
                                  'OI-ISTRUTTORE',
                                  'ADG-ISTRUTTORE')
                          AND (dta.desc_breve_tipo_anagrafica NOT IN
                                  ('BEN-MASTER',
                                   'OI-IST-MASTER',
                                   'ADG-IST-MASTER',
                                   'CREATOR')
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
		          tp.id_progetto_finanz,
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

CREATE OR REPLACE FORCE VIEW PBANDI_V_PROGETTI_BEN_BL
(
   ID_PROGETTO,
   PROGR_BANDO_LINEA_INTERVENTO,
   NOME_BANDO_LINEA,
   CODICE_FISCALE_SOGGETTO,
   ID_SOGGETTO,
   CODICE_VISUALIZZATO_PROGETTO,
   TITOLO_PROGETTO,
   ID_SOGGETTO_BENEFICIARIO,
   CODICE_FISCALE_BENEFICIARIO,
   DENOMINAZIONE_BENEFICIARIO,
   FLAG_RAPPRESENTANTE_LEGALE,
   FLAG_PUBBLICO_PRIVATO,
   CODICE_RUOLO
)
AS
   SELECT                                       /*+NO_QUERY_TRANSFORMATION  */
         m.ID_PROGETTO,
          m.PROGR_BANDO_LINEA_INTERVENTO,
          m.NOME_BANDO_LINEA,
          m.CODICE_FISCALE_SOGGETTO,
          m.ID_SOGGETTO,
          m.CODICE_VISUALIZZATO_PROGETTO,
          m.TITOLO_PROGETTO,
          ben.id_soggetto_BENEFICIARIO,
          ben.codice_fiscale_BENEFICIARIO,
          ben.DENOMINAZIONE_BENEFICIARIO,
          CASE
             WHEN ben.id_soggetto_beneficiario IN
                     (SELECT rsc.id_soggetto_ente_giuridico
                        FROM pbandi_r_sogg_prog_sogg_correl rspsc,
                             pbandi_r_soggetti_correlati rsc,
                             pbandi_d_tipo_sogg_correlato dtsc
                       WHERE rsc.progr_soggetti_correlati =
                                rspsc.progr_soggetti_correlati
                             AND rspsc.progr_soggetto_progetto =
                                    m.progr_soggetto_progetto
                             AND rsc.id_tipo_soggetto_correlato =
                                    dtsc.id_tipo_soggetto_correlato
                             AND dtsc.desc_breve_tipo_sogg_correlato =
                                    'Rappr. Leg.'
                             AND NVL (TRUNC (rsc.dt_fine_validita),
                                      TRUNC (SYSDATE + 1)) > TRUNC (SYSDATE)
                             AND NVL (TRUNC (dtsc.dt_fine_validita),
                                      TRUNC (SYSDATE + 1)) > TRUNC (SYSDATE))
                  AND m.desc_breve_tipo_anagrafica = 'PERSONA-FISICA'
             THEN
                'S'
             ELSE
                'N'
          END
             flag_rappresentante_legale,
          ben.flag_pubblico_privato,
          m.codice_ruolo
     FROM (SELECT rsp.id_soggetto id_soggetto_beneficiario,
                  benInfo.codice_fiscale_soggetto codice_fiscale_beneficiario,
                  NVL (teg.denominazione_ente_giuridico,
                       tpf.nome || NVL2 (tpf.nome, ' ', '') || tpf.cognome)
                     AS denominazione_beneficiario,
                  teg.flag_pubblico_privato,
                  rsp.id_progetto
             FROM pbandi_r_soggetto_progetto rsp,
                  (SELECT pbandi_t_soggetto.id_soggetto,
                          pbandi_t_soggetto.codice_fiscale_soggetto,
                          pbandi_d_tipo_soggetto.desc_breve_tipo_soggetto
                     FROM pbandi_t_soggetto, pbandi_d_tipo_soggetto
                    WHERE NVL (
                             TRUNC (pbandi_d_tipo_soggetto.dt_fine_validita),
                             TRUNC (SYSDATE + 1)) > TRUNC (SYSDATE)
                          AND pbandi_d_tipo_soggetto.id_tipo_soggetto =
                                 pbandi_t_soggetto.id_tipo_soggetto) benInfo,
                  pbandi_t_ente_giuridico teg,
                  pbandi_t_persona_fisica tpf
            WHERE rsp.id_soggetto = benInfo.id_soggetto
                  AND rsp.ID_TIPO_ANAGRAFICA =
                         (SELECT m.id_tipo_anagrafica
                            FROM pbandi_d_tipo_anagrafica m
                           WHERE m.desc_breve_tipo_anagrafica =
                                    'BENEFICIARIO')
                  AND NVL (TRUNC (rsp.dt_fine_validita), TRUNC (SYSDATE + 1)) >
                         TRUNC (SYSDATE)
                  AND NVL (rsp.id_tipo_beneficiario, '-1') <>
                         (SELECT dtb.id_tipo_beneficiario
                            FROM pbandi_d_tipo_beneficiario dtb
                           WHERE dtb.desc_breve_tipo_beneficiario =
                                    'BEN-ASSOCIATO')
                  AND NVL (TRUNC (teg.dt_fine_validita), TRUNC (SYSDATE + 1)) >
                         TRUNC (SYSDATE)
                  AND NVL (TRUNC (tpf.dt_fine_validita), TRUNC (SYSDATE + 1)) >
                         TRUNC (SYSDATE)
                  AND rsp.ID_PERSONA_FISICA = tpf.ID_PERSONA_FISICA(+)
                  AND rsp.id_ente_giuridico = teg.id_ente_giuridico(+)) ben,
          (SELECT m1.*,
                  crp.codice_ruolo,
                  NULL AS dt_aggiornamento,
                  NULL AS dt_inserimento
             FROM pbandi_v_soggetto_progetto m1,
                  (SELECT crp.codice codice_ruolo,
                          rrta.id_tipo_anagrafica,
                          crp.id_definizione_processo
                     FROM pbandi_c_ruolo_di_processo crp,
                          pbandi_r_ruolo_tipo_anagrafica rrta
                    WHERE crp.id_ruolo_di_processo =
                             rrta.id_ruolo_di_processo) crp
            WHERE  m1.id_tipo_anagrafica = crp.id_tipo_anagrafica
			  AND m1.id_progetto_finanz IS NULL) m -- I progetti relativi a domande di Contributo (id_progetto_finanz valorizzato) sono scartati dal processo (todo list)
    WHERE ben.id_progetto = m.id_progetto
          AND (m.desc_breve_tipo_anagrafica <> 'PERSONA-FISICA'
               OR ben.id_soggetto_beneficiario IN
                     (SELECT rsc.id_soggetto_ente_giuridico
                        FROM pbandi_r_sogg_prog_sogg_correl rspsc,
                             pbandi_r_soggetti_correlati rsc
                       WHERE rsc.progr_soggetti_correlati =
                                rspsc.progr_soggetti_correlati
                             AND NVL (TRUNC (rsc.dt_fine_validita),
                                      TRUNC (SYSDATE + 1)) > TRUNC (SYSDATE)
                             AND rspsc.progr_soggetto_progetto =
                                    m.progr_soggetto_progetto
                             AND rsc.id_tipo_soggetto_correlato IN
                                    (SELECT rrta.id_tipo_soggetto_correlato
                                       FROM pbandi_r_ruolo_tipo_anagrafica rrta
                                      WHERE rrta.id_tipo_anagrafica =
                                               m.id_tipo_anagrafica)));

CREATE OR REPLACE FORCE VIEW PBANDI_V_DOC_INDEX_BATCH_FINP
(
   ID_DOCUMENTO_INDEX,
   UUID_NODO,
   ID_PROGETTO,
   NOME_FILE,
   NOME_FILE_FIRMATO,
   TIPO_INVIO,
   FLAG_FIRMA_CARTACEA,
   FLAG_TRASM_DEST,
   ID_DICHIARAZIONE_SPESA,
   DESC_BREVE_TIPO_DOC_INDEX
)
AS
   WITH SF_INVIO
        AS (  SELECT c.ID_DICHIARAZIONE_SPESA,
                     c.ID_PROGETTO,
                     CASE
                        WHEN MIN (TIPO_INVIO) || MAX (TIPO_INVIO) = 'CC'
                        THEN
                           'C'
                        WHEN MIN (TIPO_INVIO) || MAX (TIPO_INVIO) = 'DD'
                        THEN
                           'E'
                        WHEN MIN (TIPO_INVIO) || MAX (TIPO_INVIO) = 'CD'
                        THEN
                           'I'
                        ELSE
                           NULL
                     END
                        TIPO_INVIO
                FROM PBANDI_S_DICH_DOC_SPESA a,
                     PBANDI_R_DOC_SPESA_PROGETTO b,
                     PBANDI_T_DICHIARAZIONE_SPESA c
               WHERE     a.ID_DICHIARAZIONE_SPESA = c.ID_DICHIARAZIONE_SPESA
                     AND a.ID_DOCUMENTO_DI_SPESA = b.ID_DOCUMENTO_DI_SPESA
                     AND b.ID_PROGETTO = c.ID_PROGETTO
            GROUP BY c.ID_DICHIARAZIONE_SPESA, c.ID_PROGETTO)
   SELECT ID_DOCUMENTO_INDEX,
          UUID_NODO,
		  x.ID_PROGETTO,
		  NOME_FILE,
		  NOME_FILE_FIRMATO,
          CASE
             WHEN x.NOME_ENTITA IN
                     ('PBANDI_T_DICHIARAZIONE_SPESA',
                      'PBANDI_T_COMUNICAZ_FINE_PROG')
             THEN
                i.TIPO_INVIO
             ELSE
                NULL                               --Comunicazione di rinuncia
          END
             TIPO_INVIO,
		  FLAG_FIRMA_CARTACEA,
		  FLAG_TRASM_DEST,
		  x.ID_DICHIARAZIONE_SPESA,
		  DESC_BREVE_TIPO_DOC_INDEX
   FROM
   (
   SELECT ID_DOCUMENTO_INDEX,
          UUID_NODO,
          di.ID_PROGETTO,
          NOME_FILE,
		  NOME_ENTITA,
          CASE
             WHEN FLAG_FIRMA_CARTACEA = 'N'
             THEN
                NOME_FILE
                || (SELECT valore
                      FROM PBANDI_C_COSTANTI
                     WHERE attributo = 'conf.firmaDigitaleFileExtension')
             ELSE
                NULL
          END
             NOME_FILE_FIRMATO,
          FLAG_FIRMA_CARTACEA,
          NVL (FLAG_TRASM_DEST, 'N') FLAG_TRASM_DEST,
          --Dichiarazione di spesa(id_target di Id_entita 63 PBANDI_T_DICHIARAZIONE_SPESA)
          --Comunicazione di fine progetto (id_entita 270  PBANDI_T_COMUNICAZ_FINE_PROG cercare la dichiarazione di spesa di tipo DESC_BREVE_TIPO_DICHIARA_SPESA=FC)
          --Comunicazione di rinuncia (id_entita 249 PBANDI_T_RINUNCIA id_dichiarazione di spesa=NULL
          CASE
             WHEN en.NOME_ENTITA = 'PBANDI_T_DICHIARAZIONE_SPESA'
             THEN
                di.ID_TARGET
             WHEN en.NOME_ENTITA = 'PBANDI_T_COMUNICAZ_FINE_PROG'
             THEN
                (SELECT MAX (id_dichiarazione_spesa)
                   FROM PBANDI_T_DICHIARAZIONE_SPESA dich
                  WHERE id_progetto = di.id_progetto
                        AND id_tipo_dichiaraz_spesa IN
                               (SELECT id_tipo_dichiaraz_spesa
                                  FROM PBANDI_D_TIPO_DICHIARAZ_SPESA
                                 WHERE DESC_BREVE_TIPO_DICHIARA_SPESA = 'FC')) -- Finale con comunicazione
             ELSE
                NULL                               --Comunicazione di rinuncia
          END
             ID_DICHIARAZIONE_SPESA,
          tdi.DESC_BREVE_TIPO_DOC_INDEX
     FROM PBANDI_T_DOCUMENTO_INDEX di
          JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX tdi
             USING (ID_TIPO_DOCUMENTO_INDEX)
          JOIN PBANDI_C_ENTITA en
             USING (ID_ENTITA)
          LEFT JOIN PBANDI_D_STATO_DOCUMENTO_INDEX sdi
             USING (ID_STATO_DOCUMENTO)
          JOIN PBANDI_T_PROGETTO p
             ON p.ID_PROGETTO = di.ID_PROGETTO
          JOIN PBANDI_T_DOMANDA
             USING (ID_DOMANDA)
          JOIN PBANDI_R_BANDO_LINEA_INTERVENT bli
             USING (PROGR_BANDO_LINEA_INTERVENTO)
          JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP blec
             USING (PROGR_BANDO_LINEA_INTERVENTO)
          JOIN PBANDI_D_RUOLO_ENTE_COMPETENZA rec
             USING (ID_RUOLO_ENTE_COMPETENZA)
          JOIN PBANDI_T_ENTE_COMPETENZA ec
             USING (ID_ENTE_COMPETENZA)
    WHERE tdi.FLAG_FIRMABILE = 'S'
          AND en.NOME_ENTITA IN
                 ('PBANDI_T_DICHIARAZIONE_SPESA',
                  'PBANDI_T_COMUNICAZ_FINE_PROG',
                  'PBANDI_T_RINUNCIA')
          AND sdi.DESC_BREVE IN ('INVIATO')
          AND NVL (di.FLAG_FIRMA_CARTACEA, 'N') = 'N'
          AND di.DT_MARCA_TEMPORALE IS NOT NULL
          AND rec.DESC_BREVE_RUOLO_ENTE = 'DESTINATARIO'
          AND ec.DESC_BREVE_ENTE = 'FIN') x
          LEFT JOIN SF_INVIO i
             ON (i.ID_DICHIARAZIONE_SPESA = x.ID_DICHIARAZIONE_SPESA
                 AND i.ID_PROGETTO = x.ID_PROGETTO);
