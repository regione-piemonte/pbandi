/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

-- PBANDI_T_INTEGRAZIONE_SPESA
ALTER TABLE PBANDI_T_INTEGRAZIONE_SPESA MODIFY DESCRIZIONE VARCHAR2(4000);
-- PBANDI_R_SOGGETTO_PROGETTO
ALTER TABLE PBANDI_R_SOGGETTO_PROGETTO ADD DATA_CESSAZIONE DATE;
-----
-- GET ATTIVITA'
DROP TYPE LISTTASKPROG;
DROP TYPE OBJTASKPROG;

CREATE OR REPLACE TYPE "OBJTASKPROG" AS OBJECT
(ID_PROGETTO NUMBER(8),
 TITOLO_PROGETTO  VARCHAR2(2000),
 CODICE_VISUALIZZATO  VARCHAR2(100),
 DESCR_BREVE_TASK VARCHAR2(100),
 DESCR_TASK  VARCHAR2(2000),
 PROGR_BANDO_LINEA_INTERVENTO  NUMBER(8),
 NOME_BANDO_LINEA VARCHAR2(255),
 FLAG_OPT VARCHAR2(1),
 FLAG_LOCK VARCHAR2(1),
 ACRONIMO_PROGETTO VARCHAR2(2000),
 ID_BUSINESS NUMBER,
 ID_NOTIFICA NUMBER,
 DENOMINAZIONE_LOCK  VARCHAR2(1000),
 FLAG_RICH_ABILITAZ_UTENTE VARCHAR2(1)
 )

/

CREATE OR REPLACE TYPE "LISTTASKPROG" AS TABLE OF OBJTASKPROG;

/
-----

-- PBANDI_T_NEWS
ALTER TABLE PBANDI_T_NEWS MODIFY URL_PAGINA NULL;
----- 
-- PBANDI_T_DOCUMENTO_INDEX_TMP
CREATE TABLE PBANDI_T_DOCUMENTO_INDEX_TMP AS
SELECT * FROM PBANDI_T_DOCUMENTO_INDEX;

-- PK
ALTER TABLE PBANDI_T_DOCUMENTO_INDEX_TMP  ADD 
(CONSTRAINT PK_PBANDI_T_DOC_INDEX_TMP  PRIMARY KEY (id_documento_index)
USING INDEX TABLESPACE PBANDI_IDX);


-- PBANDI_L_LOG_FILE_INDEX_OUTPUT
CREATE TABLE PBANDI_L_LOG_FILE_INDEX_OUTPUT
( nome_file              VARCHAR2(30),
  flag_file_elaborato    VARCHAR2(1) default 'N',
  report_elaborazione    VARCHAR2(2000),
  data_fine_elaborazione DATE,
  PATH_FILES VARCHAR2(255)
);

-----
-- PBANDI_T_NEWS
ALTER TABLE PBANDI_T_NEWS ADD TIPO_NEWS VARCHAR2(10); 
ALTER TABLE PBANDI_T_NEWS ADD CONSTRAINT CHK_TIPO_NEWS CHECK (TIPO_NEWS IN ('WARN', 'INFO'));

-- PBANDI_R_BANDO_INDICATORI
ALTER TABLE PBANDI_R_BANDO_INDICATORI MODIFY INFO_INIZIALE VARCHAR2(500);
ALTER TABLE PBANDI_R_BANDO_INDICATORI MODIFY INFO_FINALE VARCHAR2(500); 

-- PBANDI_T_DOCUMENTO_INDEX
ALTER TABLE PBANDI_T_DOCUMENTO_INDEX ADD acta_indice_classificaz_esteso VARCHAR2(4000);

-- PBANDI_T_RICERCA_ATTIVITA
CREATE TABLE PBANDI_T_RICERCA_ATTIVITA
(ID_RICERCA_ATTIVITA NUMBER(8),
 ID_UTENTE NUMBER(8),
 ID_SOGGETTO_BENEFICIARIO NUMBER(8),
 PROGR_BANDO_LINEA_INTERVENTO NUMBER(8),
 ID_PROGETTO NUMBER(8),
 ATTIVITA VARCHAR2(200),
 DT_RICERCA DATE
);

-- PK
ALTER TABLE PBANDI_T_RICERCA_ATTIVITA  ADD 
(CONSTRAINT PK_PBANDI_T_RICERCA_ATTIVITA  PRIMARY KEY (ID_RICERCA_ATTIVITA)
USING INDEX TABLESPACE PBANDI_IDX);

-- FK
ALTER TABLE PBANDI_T_RICERCA_ATTIVITA ADD (
  CONSTRAINT fk_PBANDI_T_UTENTE_353
  FOREIGN KEY (ID_UTENTE) 
  REFERENCES PBANDI_T_UTENTE (ID_UTENTE));

-- FK
ALTER TABLE PBANDI_T_RICERCA_ATTIVITA ADD (
  CONSTRAINT fk_pbandi_r_bando_linea_int_30
  FOREIGN KEY (PROGR_BANDO_LINEA_INTERVENTO) 
  REFERENCES PBANDI_R_BANDO_LINEA_INTERVENT (PROGR_BANDO_LINEA_INTERVENTO));
  
-- FK
ALTER TABLE PBANDI_T_RICERCA_ATTIVITA ADD (
  CONSTRAINT fk_pbandi_t_soggetto_32
  FOREIGN KEY (ID_SOGGETTO_BENEFICIARIO) 
  REFERENCES PBANDI_T_SOGGETTO (ID_SOGGETTO));

-- FK
ALTER TABLE PBANDI_T_RICERCA_ATTIVITA ADD (
  CONSTRAINT fk_pbandi_t_progetto_75
  FOREIGN KEY (ID_PROGETTO) 
  REFERENCES PBANDI_T_PROGETTO (ID_PROGETTO));


-- IDX
CREATE UNIQUE INDEX AK1_PBANDI_T_RICERCA_ATTIVITA ON PBANDI_T_RICERCA_ATTIVITA (ID_UTENTE) TABLESPACE PBANDI_IDX;
CREATE INDEX IE1_PBANDI_T_RICERCA_ATTIVITA ON PBANDI_T_RICERCA_ATTIVITA (PROGR_BANDO_LINEA_INTERVENTO) TABLESPACE PBANDI_IDX;
CREATE INDEX IE2_PBANDI_T_RICERCA_ATTIVITA ON PBANDI_T_RICERCA_ATTIVITA (ID_SOGGETTO_BENEFICIARIO) TABLESPACE PBANDI_IDX;
CREATE INDEX IE3_PBANDI_T_RICERCA_ATTIVITA ON PBANDI_T_RICERCA_ATTIVITA (ID_PROGETTO) TABLESPACE PBANDI_IDX;

-- SEQ
CREATE SEQUENCE SEQ_PBANDI_T_RICERCA_ATTIVITA
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;

-- PBANDI_T_RICHIESTA_ABILITAZIONE
CREATE TABLE PBANDI_T_RICHIESTA_ABILITAZ
(PROGR_SOGGETTO_PROGETTO NUMBER(8),
 PROGR_SOGGETTI_CORRELATI NUMBER(8),
 DT_RICHIESTA DATE NOT NULL,
 ESITO VARCHAR2(1),
 DT_ESITO DATE,
 ACCESSO_SISTEMA VARCHAR2(1),
 ID_UTENTE_INS NUMBER(8)NOT NULL,
 ID_UTENTE_AGG NUMBER(8)
);

-- PK
ALTER TABLE PBANDI_T_RICHIESTA_ABILITAZ  ADD 
(CONSTRAINT PK_PBANDI_T_RICHIESTA_ABILITAZ 
 PRIMARY KEY (PROGR_SOGGETTI_CORRELATI,PROGR_SOGGETTO_PROGETTO)
USING INDEX TABLESPACE PBANDI_IDX);

-- FK
ALTER TABLE PBANDI_T_RICHIESTA_ABILITAZ ADD (
  CONSTRAINT fk_pbandi_t_utente_328
  FOREIGN KEY (ID_UTENTE_INS) 
  REFERENCES PBANDI_T_UTENTE (ID_UTENTE));

-- FK
ALTER TABLE PBANDI_T_RICHIESTA_ABILITAZ ADD (
  CONSTRAINT fk_pbandi_t_utente_329
  FOREIGN KEY (ID_UTENTE_AGG) 
  REFERENCES PBANDI_T_UTENTE (ID_UTENTE));

-- CHK
ALTER TABLE PBANDI_T_RICHIESTA_ABILITAZ
ADD CONSTRAINT CHK_ESITO
CHECK (ESITO IN ('S', 'N'));

ALTER TABLE PBANDI_T_RICHIESTA_ABILITAZ
ADD CONSTRAINT CHK_ACCESSO_SISTEMA
CHECK (ACCESSO_SISTEMA IN ('S', 'N'));

-- FK
ALTER TABLE PBANDI_T_RICHIESTA_ABILITAZ ADD (
  CONSTRAINT fk_pbandi_r_sogg_correlati_03
  FOREIGN KEY (PROGR_SOGGETTI_CORRELATI,PROGR_SOGGETTO_PROGETTO) 
  REFERENCES PBANDI_R_SOGG_PROG_SOGG_CORREL (PROGR_SOGGETTI_CORRELATI,PROGR_SOGGETTO_PROGETTO));

-- IDX
CREATE INDEX IE1_PBANDI_T_RICH_ABILITAZ ON PBANDI_T_RICHIESTA_ABILITAZ (PROGR_SOGGETTO_PROGETTO) TABLESPACE PBANDI_IDX;
CREATE INDEX IE2_PBANDI_T_RICH_ABILITAZ ON PBANDI_T_RICHIESTA_ABILITAZ (ID_UTENTE_INS) TABLESPACE PBANDI_IDX;
CREATE INDEX IE3_PBANDI_T_RICH_ABILITAZ ON PBANDI_T_RICHIESTA_ABILITAZ (ID_UTENTE_AGG) TABLESPACE PBANDI_IDX;

-- PBANDI_R_SOGG_BANDO_LIN_INT
CREATE TABLE PBANDI_R_SOGG_BANDO_LIN_INT
(ID_SOGGETTO NUMBER(8,0) NOT NULL,
 PROGR_BANDO_LINEA_INTERVENTO NUMBER(8,0) NOT NULL,
 DT_INIZIO_VALIDITA DATE NOT NULL,
 DT_FINE_VALIDITA DATE, 
 ID_TIPO_ANAGRAFICA NUMBER(3,0) NOT NULL,
 ID_PERSONA_FISICA NUMBER(8,0),
 ID_UTENTE_INS NUMBER(8,0) NOT NULL,
 ID_UTENTE_AGG NUMBER(8,0),
 DT_INSERIMENTO DATE NOT NULL,
 DT_AGGIORNAMENTO  DATE
);

-- PK
ALTER TABLE PBANDI_R_SOGG_BANDO_LIN_INT  ADD 
(CONSTRAINT PK_PBANDI_R_SOGG_BANDO_LIN_INT  
 PRIMARY KEY (ID_SOGGETTO,PROGR_BANDO_LINEA_INTERVENTO)
USING INDEX TABLESPACE PBANDI_IDX);

-- FK
ALTER TABLE PBANDI_R_SOGG_BANDO_LIN_INT ADD (
  CONSTRAINT fk_pbandi_r_bando_linea_int_29
  FOREIGN KEY (PROGR_BANDO_LINEA_INTERVENTO) 
  REFERENCES PBANDI_R_BANDO_LINEA_INTERVENT (PROGR_BANDO_LINEA_INTERVENTO));
/*
-- FK
ALTER TABLE PBANDI_R_SOGG_BANDO_LIN_INT ADD (
  CONSTRAINT fk_pbandi_d_tipo_benefic_05
  FOREIGN KEY (ID_TIPO_BENEFICIARIO) 
  REFERENCES PBANDI_D_TIPO_BENEFICIARIO (ID_TIPO_BENEFICIARIO));
*/
-- FK
ALTER TABLE PBANDI_R_SOGG_BANDO_LIN_INT ADD (
  CONSTRAINT fk_pbandi_t_persona_fisica_09
  FOREIGN KEY (ID_PERSONA_FISICA) 
  REFERENCES PBANDI_T_PERSONA_FISICA (ID_PERSONA_FISICA));

-- FK
ALTER TABLE PBANDI_R_SOGG_BANDO_LIN_INT ADD (
  CONSTRAINT fk_pbandi_t_utente_296
  FOREIGN KEY (ID_UTENTE_INS) 
  REFERENCES PBANDI_T_UTENTE (ID_UTENTE));

-- FK
ALTER TABLE PBANDI_R_SOGG_BANDO_LIN_INT ADD (
  CONSTRAINT fk_pbandi_t_utente_297
  FOREIGN KEY (ID_UTENTE_AGG) 
  REFERENCES PBANDI_T_UTENTE (ID_UTENTE));

-- FK 
  ALTER TABLE PBANDI_R_SOGG_BANDO_LIN_INT ADD (
  CONSTRAINT fk_pbandi_d_tipo_anagrafica_11
  FOREIGN KEY (ID_TIPO_ANAGRAFICA) 
  REFERENCES PBANDI_D_TIPO_ANAGRAFICA (ID_TIPO_ANAGRAFICA));

--

-- INDEX
CREATE INDEX IE1_PBANDI_R_SOGG_BAN_LIN_INT ON PBANDI_R_SOGG_BANDO_LIN_INT (PROGR_BANDO_LINEA_INTERVENTO) TABLESPACE PBANDI_IDX;
-- PBANDI_T_DOCUMENTO_INDEX
CREATE INDEX IE4_PBANDI_T_DOCUMENTO_INDEX on PBANDI_T_DOCUMENTO_INDEX (ID_PROGETTO) TABLESPACE PBANDI_IDX;

--CREATE INDEX IE2_PBANDI_R_SOGG_BAN_LIN_INT ON PBANDI_R_SOGG_BANDO_LIN_INT (ID_TIPO_BENEFICIARIO) TABLESPACE PBANDI_IDX;
CREATE INDEX IE3_PBANDI_R_SOGG_BAN_LIN_INT ON PBANDI_R_SOGG_BANDO_LIN_INT (ID_PERSONA_FISICA) TABLESPACE PBANDI_IDX;
CREATE INDEX IE4_PBANDI_R_SOGG_BAN_LIN_INT ON PBANDI_R_SOGG_BANDO_LIN_INT (ID_UTENTE_INS) TABLESPACE PBANDI_IDX;
CREATE INDEX IE5_PBANDI_R_SOGG_BAN_LIN_INT ON PBANDI_R_SOGG_BANDO_LIN_INT (ID_UTENTE_AGG) TABLESPACE PBANDI_IDX;
CREATE INDEX IE6_PBANDI_R_SOGG_BAN_LIN_INT ON PBANDI_R_SOGG_BANDO_LIN_INT (ID_TIPO_ANAGRAFICA) TABLESPACE PBANDI_IDX;
--
ALTER TABLE  PBANDI_D_PERMESSO ADD  DESC_MENU  VARCHAR2(255);
ALTER TABLE PBANDI_R_PERMESSO_TIPO_ANAGRAF ADD FLAG_MENU VARCHAR2(1);

-- ALTER PBANDI_T_DATI_PROGETTO_MONIT
ALTER TABLE PBANDI_T_DATI_PROGETTO_MONIT ADD FLAG_ESCLUSO_CERTIFICAZ VARCHAR2(1);
ALTER TABLE PBANDI_T_DATI_PROGETTO_MONIT ADD CONSTRAINT CK_PBANDI_T_DATI_PROG_MONIT_01 
    CHECK (FLAG_ESCLUSO_CERTIFICAZ='S');
COMMENT ON COLUMN PBANDI_T_DATI_PROGETTO_MONIT.FLAG_ESCLUSO_CERTIFICAZ IS 'Se ''S'' esclude dalla certificazione il progetto se non è già entrato in una approvata';


-- PBANDI_V_DOC_INDEX
CREATE OR REPLACE VIEW PBANDI_V_DOC_INDEX AS
WITH ts
        AS (SELECT ts1.*,
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
                     'ISTR-AFFIDAMENTI'))
                        OR (NOT EXISTS
                               (SELECT 'x'
                                  FROM pbandi_r_ente_competenza_sogg recs
                                 WHERE recs.id_soggetto = ts1.id_soggetto)
                            OR EXISTS
                                  (SELECT 'x'
                                     FROM pbandi_r_ente_competenza_sogg recs
                                    WHERE recs.id_soggetto = ts1.id_soggetto
                                          AND recs.id_ente_competenza =
                                                 tec.id_ente_competenza)))
            UNION ALL
            SELECT DISTINCT ts1.*,
                            rblec.id_ente_competenza,
                            rsta.id_tipo_anagrafica,
                            rsta.dt_inizio_validita,
                            rsta.dt_fine_validita,
                            rsta.flag_aggiornato_flux
              FROM pbandi_t_soggetto ts1,
                   pbandi_r_sogg_tipo_anagrafica rsta,
                   pbandi_r_soggetto_progetto rsp,
                   pbandi_t_progetto tp,
                   pbandi_t_domanda td,
                   pbandi_r_bando_linea_ente_comp rblec
             WHERE rsta.id_soggetto = ts1.id_soggetto
                   AND rsta.id_tipo_anagrafica IN
                          (SELECT dta.id_tipo_anagrafica
                             FROM pbandi_d_tipo_anagrafica dta
                            WHERE dta.desc_breve_tipo_anagrafica IN
                                     ('PERSONA-FISICA',
                                      'OI-ISTRUTTORE',
                                      'ADG-ISTRUTTORE'))
                   AND rsp.id_soggetto = ts1.id_soggetto
                   AND tp.id_progetto = rsp.id_progetto
                   AND td.id_domanda = tp.id_domanda
                   AND rblec.progr_bando_linea_intervento =
                          td.progr_bando_linea_intervento),
        tsa
        AS (SELECT ts1.*, tec.id_ente_competenza, rsta.id_tipo_anagrafica
              FROM pbandi_t_soggetto ts1,
                   pbandi_t_ente_competenza tec,
                   pbandi_r_sogg_tipo_anagrafica rsta
             WHERE rsta.id_soggetto = ts1.id_soggetto
                   AND NVL (TRUNC (rsta.dt_fine_validita),
                            TRUNC (SYSDATE + 1)) > TRUNC (SYSDATE)
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
                     'ISTR-AFFIDAMENTI'))
                        OR (NOT EXISTS
                               (SELECT 'x'
                                  FROM pbandi_r_ente_competenza_sogg recs
                                 WHERE recs.id_soggetto = ts1.id_soggetto)
                            OR EXISTS
                                  (SELECT 'x'
                                     FROM pbandi_r_ente_competenza_sogg recs
                                    WHERE recs.id_soggetto = ts1.id_soggetto
                                          AND recs.id_ente_competenza =
                                                 tec.id_ente_competenza)))),
        ben
        AS (SELECT rsp.id_soggetto id_soggetto_beneficiario,
                   rsp.id_progetto,
                   rsp.id_ente_giuridico,
                   rsp.id_persona_fisica
              FROM pbandi_r_soggetto_progetto rsp
             WHERE rsp.ID_TIPO_ANAGRAFICA =
                      (SELECT dta.id_tipo_anagrafica
                         FROM pbandi_d_tipo_anagrafica dta
                        WHERE dta.desc_breve_tipo_anagrafica = 'BENEFICIARIO')
                   AND NVL (TRUNC (rsp.dt_fine_validita),
                            TRUNC (SYSDATE + 1)) > TRUNC (SYSDATE)
                   AND NVL (rsp.id_tipo_beneficiario, '-1') <>
                          (SELECT dtb.id_tipo_beneficiario
                             FROM pbandi_d_tipo_beneficiario dtb
                            WHERE dtb.desc_breve_tipo_beneficiario =
                                     'BEN-ASSOCIATO')),
        m
        AS (SELECT tp.id_progetto,
                   rbli.progr_bando_linea_intervento,
                   NULL progr_soggetto_progetto,
                   tsa.codice_fiscale_soggetto,
                   tsa.id_soggetto,
                   tsa.id_tipo_anagrafica,
                   tp.codice_visualizzato codice_visualizzato_progetto
              FROM tsa,
                   pbandi_t_progetto tp,
                   pbandi_t_domanda td,
                   pbandi_r_bando_linea_intervent rbli,
                   pbandi_r_bando_linea_ente_comp rble
             WHERE td.id_domanda = tp.id_domanda
                   AND rbli.progr_bando_linea_intervento =
                          td.progr_bando_linea_intervento
                   AND rble.progr_bando_linea_intervento =
                          rbli.progr_bando_linea_intervento
                   AND rble.id_ruolo_ente_competenza =
                          (SELECT dre.id_ruolo_ente_competenza
                             FROM pbandi_d_ruolo_ente_competenza dre
                            WHERE dre.desc_breve_ruolo_ente = 'DESTINATARIO')
                   AND tsa.id_ente_competenza = rble.id_ente_competenza
            UNION ALL
            SELECT DISTINCT
                   rsp.id_progetto id_progetto,
                   rbli.progr_bando_linea_intervento,
                   rsp.progr_soggetto_progetto,
                   tsogg.codice_fiscale_soggetto,
                   tsogg.id_soggetto,
                   rsp.id_tipo_anagrafica,
                   tp.codice_visualizzato codice_visualizzato_progetto
              FROM PBANDI_T_SOGGETTO tsogg,
                   pbandi_r_soggetto_progetto rsp,
                   pbandi_t_progetto tp,
                   pbandi_t_domanda td,
                   pbandi_r_bando_linea_intervent rbli,
                   pbandi_r_bando_linea_ente_comp rble
             WHERE     tsogg.id_soggetto = rsp.id_soggetto
                   AND NVL (TRUNC (rsp.dt_fine_validita),
                            TRUNC (SYSDATE + 1)) > TRUNC (SYSDATE)
                   AND tp.id_progetto = rsp.id_progetto
                   AND td.id_domanda = tp.id_domanda
                   AND rbli.progr_bando_linea_intervento =
                          td.progr_bando_linea_intervento
                   AND rble.progr_bando_linea_intervento =
                          rbli.progr_bando_linea_intervento
                   AND rble.id_ruolo_ente_competenza =
                          (SELECT dre.id_ruolo_ente_competenza
                             FROM pbandi_d_ruolo_ente_competenza dre
                            WHERE dre.desc_breve_ruolo_ente = 'DESTINATARIO')),
        spb
        AS (SELECT DISTINCT m.id_progetto,
                            m.progr_bando_linea_intervento,
                            m.id_soggetto,
                            m.id_tipo_anagrafica,
                            m.codice_visualizzato_progetto,
                            ben.id_soggetto_beneficiario,
                            ben.id_ente_giuridico,
                            ben.id_persona_fisica
              FROM ben, m, pbandi_d_tipo_anagrafica dta
             WHERE m.id_tipo_anagrafica = dta.id_tipo_anagrafica
                   AND ben.id_progetto = m.id_progetto
                   AND (dta.desc_breve_tipo_anagrafica <> 'PERSONA-FISICA'
                        OR ben.id_soggetto_beneficiario IN
                              (SELECT rsc.id_soggetto_ente_giuridico
                                 FROM pbandi_r_sogg_prog_sogg_correl rspsc,
                                      pbandi_r_soggetti_correlati rsc
                                WHERE rsc.progr_soggetti_correlati =
                                         rspsc.progr_soggetti_correlati
                                      AND NVL (TRUNC (rsc.dt_fine_validita),
                                               TRUNC (SYSDATE + 1)) >
                                             TRUNC (SYSDATE)
                                      AND rspsc.progr_soggetto_progetto =
                                             m.progr_soggetto_progetto
                                      AND rsc.id_tipo_soggetto_correlato IN
                                             (SELECT rrta.id_tipo_soggetto_correlato
                                                FROM pbandi_r_ruolo_tipo_anagrafica rrta
                                               WHERE rrta.id_tipo_anagrafica =
                                                        m.id_tipo_anagrafica)))),
        docindex_anagrafica
        AS (SELECT rdita.id_tipo_documento_index,
                   rdita.id_tipo_anagrafica,
                   tdi.desc_breve_tipo_doc_index,
                   tdi.desc_tipo_doc_index,
                   ta.desc_breve_tipo_anagrafica,
                   tdi.flag_firmabile
              FROM PBANDI_R_DOC_INDEX_TIPO_ANAG rdita,
                   PBANDI_D_TIPO_ANAGRAFICA ta,
                   PBANDI_C_TIPO_DOCUMENTO_INDEX tdi
             WHERE ta.id_tipo_anagrafica = rdita.id_tipo_anagrafica
                   AND tdi.id_tipo_documento_index =
                          rdita.id_tipo_documento_index),
        docindex_ente_soggetto
        AS (SELECT rtdib.id_tipo_documento_index,
                   rtdib.progr_bando_linea_intervento,
                   ts.id_ente_competenza,
                   ts.id_soggetto
              FROM PBANDI_R_TP_DOC_IND_BAN_LI_INT rtdib,
                   PBANDI_R_BANDO_LINEA_ENTE_COMP rblec,
                   ts
             WHERE ts.id_ente_competenza = rblec.id_ente_competenza
                   AND rtdib.progr_bando_linea_intervento =
                          rblec.progr_bando_linea_intervento),
        tdis
        AS (SELECT DISTINCT
                   docindex_anagrafica.*,
                   docindex_ente_soggetto.id_ente_competenza,
                   docindex_ente_soggetto.id_soggetto
              FROM docindex_anagrafica, docindex_ente_soggetto
             WHERE docindex_anagrafica.id_tipo_documento_index =
                      docindex_ente_soggetto.id_tipo_documento_index),
        lvf                                            --Log Validazione Firma
        AS (SELECT id_documento_index, codice_errore, b.messaggio
              FROM PBANDI_T_LOG_VALIDAZ_FIRMA a, PBANDI_T_MESSAGGI_APPL b
             WHERE id_log =
                      (SELECT MAX (id_log)
                         FROM PBANDI_T_LOG_VALIDAZ_FIRMA
                        WHERE id_documento_index = a.id_documento_index)
                   AND metodo = 'VERIFY'
                   AND flag_stato_validazione = 'N'
                   AND a.id_messaggio_appl = b.id_messaggio(+)),
        SF_ENTE_GIURIDICO
        AS                                                -- Dati beneficiario
          (SELECT                                            /*+MATERIALIZE */
                 a.id_ente_giuridico,
                  b.DENOMINAZIONE_ENTE_GIURIDICO,
                  b.id_soggetto,
                  d.codice_fiscale_soggetto
             FROM (  SELECT MAX (ID_ENTE_GIURIDICO) id_ente_giuridico
                       FROM PBANDI_T_ENTE_GIURIDICO a
                      WHERE dt_fine_validita IS NULL
                   GROUP BY id_soggetto) a,
                  PBANDI_T_ENTE_GIURIDICO b,
                  PBANDI_T_SOGGETTO d
            WHERE A.ID_ENTE_GIURIDICO = b.id_ente_giuridico
                  AND d.id_soggetto = b.id_soggetto)
   -- MAIN
   SELECT                                        /*+NO_QUERY_TRANSFORMATION */
         DISTINCT
          di.id_documento_index,
          di.id_target,
          di.uuid_nodo,
          di.repository,
          di.dt_inserimento_index,
          di.id_tipo_documento_index,
          di.id_entita,
          di.id_utente_ins,
          di.id_utente_agg,
          di.nome_file,
          di.note_documento_index,
          di.id_progetto,
          di.dt_aggiornamento_index,
          di.versione,
          di.id_modello,
          spb.progr_bando_linea_intervento,
          spb.codice_visualizzato_progetto AS codice_visualizzato,
          spb.id_soggetto,
          spb.id_tipo_anagrafica,
          spb.id_soggetto_beneficiario,
          tdis.desc_breve_tipo_anagrafica,
          tdis.desc_breve_tipo_doc_index,
          tdis.desc_tipo_doc_index,
          CASE
             WHEN spb.id_persona_fisica IS NULL
             THEN
                (SELECT DISTINCT eg.denominazione_ente_giuridico
                   FROM PBANDI_T_ENTE_GIURIDICO eg
                  WHERE eg.id_ente_giuridico = spb.id_ente_giuridico)
             ELSE
                (SELECT DISTINCT pf.cognome || ' ' || pf.nome
                   FROM PBANDI_T_PERSONA_FISICA pf
                  WHERE pf.id_persona_fisica = spb.id_persona_fisica)
          END
             AS beneficiario,
          CASE
             WHEN spb.id_soggetto_beneficiario IS NOT NULL
             THEN
                (SELECT DISTINCT s.codice_fiscale_soggetto
                   FROM PBANDI_T_SOGGETTO s
                  WHERE s.id_soggetto = spb.id_soggetto_beneficiario)
          END
             AS codice_fiscale_beneficiario,
          di.DT_VERIFICA_FIRMA,
          di.DT_MARCA_TEMPORALE,
          di.FLAG_FIRMA_CARTACEA,
          di.ID_STATO_DOCUMENTO,
          tdis.flag_firmabile,
          CASE
             WHEN spb.progr_bando_linea_intervento IN
                     (SELECT DISTINCT progr_bando_linea_intervento
                        FROM PBANDI_R_REGOLA_BANDO_LINEA
                       WHERE ID_REGOLA = 42)
             THEN
                'S'
          END
             AS FLAG_REGOLA_DEMAT,
          lvf.codice_errore,
          lvf.messaggio,
          tdp.NUM_PROTOCOLLO,
          sdi.DESCRIZIONE DESC_STATO_DOCUMENTO_INDEX,
          NULL ID_CATEG_ANAGRAFICA_MITT,
          NULL DESC_CATEG_ANAGRAFICA_MITT,
          di.flag_trasm_dest -- mc 30092021
     FROM spb,
          tdis,
          PBANDI_T_DOCUMENTO_INDEX di,
          lvf,
          PBANDI_T_DOC_PROTOCOLLO tdp,
          PBANDI_D_STATO_DOCUMENTO_INDEX sdi
    WHERE     di.id_progetto = spb.id_progetto
          AND di.id_tipo_documento_index = tdis.id_tipo_documento_index
          AND tdis.id_tipo_anagrafica = spb.id_tipo_anagrafica
          AND tdis.id_soggetto = spb.id_soggetto
          AND sdi.ID_STATO_DOCUMENTO(+) = di.ID_STATO_DOCUMENTO
          AND lvf.id_documento_index(+) = di.id_documento_index
          AND tdp.ID_DOCUMENTO_INDEX(+) = di.ID_DOCUMENTO_INDEX
          AND di.ID_CATEG_ANAGRAFICA_MITT IS NULL
   UNION ALL --documenti inseriti dalla nuova funzione di upload, solo per gli utenti autorizzati a vederli
   SELECT di.id_documento_index,
          di.id_target,
          di.uuid_nodo,
          di.repository,
          di.dt_inserimento_index,
          di.id_tipo_documento_index,
          di.id_entita,
          di.id_utente_ins,
          di.id_utente_agg,
          di.nome_file,
          di.note_documento_index,
          di.id_progetto,
          di.dt_aggiornamento_index,
          di.versione,
          di.id_modello,
          pb.progr_bando_linea_intervento,
          pb.codice_visualizzato,
          sta.id_soggetto,
          ta.id_tipo_anagrafica,
          sp.id_soggetto id_soggetto_beneficiario,
          ta.desc_breve_tipo_anagrafica,
          tdi.desc_breve_tipo_doc_index,
          tdi.desc_tipo_doc_index,
          eg.denominazione_ente_giuridico beneficiario,
          eg.codice_fiscale_soggetto codice_fiscale_beneficiario,
          di.DT_VERIFICA_FIRMA,
          di.DT_MARCA_TEMPORALE,
          di.FLAG_FIRMA_CARTACEA,
          di.ID_STATO_DOCUMENTO,
          tdi.flag_firmabile,
          CASE
             WHEN pb.progr_bando_linea_intervento IN
                     (SELECT DISTINCT progr_bando_linea_intervento
                        FROM PBANDI_R_REGOLA_BANDO_LINEA
                       WHERE ID_REGOLA = 42)
             THEN
                'S'
          END
             AS FLAG_REGOLA_DEMAT,
          lvf.codice_errore,
          lvf.messaggio,
          tdp.NUM_PROTOCOLLO,
          sdi.DESCRIZIONE DESC_STATO_DOCUMENTO_INDEX,
          di.ID_CATEG_ANAGRAFICA_MITT,
          ca.DESC_CATEG_ANAGRAFICA DESC_CATEG_ANAGRAFICA_MITT,
          di.flag_trasm_dest -- mc 30092021
     FROM PBANDI_T_DOCUMENTO_INDEX di,
          PBANDI_D_CATEG_ANAGRAFICA ca,
          PBANDI_T_DOC_PROTOCOLLO tdp,
          PBANDI_D_STATO_DOCUMENTO_INDEX sdi,
          PBANDI_C_TIPO_DOCUMENTO_INDEX tdi,
          lvf,
          PBANDI_V_PROGETTI_BANDO pb,
          PBANDI_R_CATEG_ANAG_DOC_INDEX cadi,
          PBANDI_D_TIPO_ANAGRAFICA ta,
          PBANDI_R_SOGGETTO_PROGETTO sp,
          SF_ENTE_GIURIDICO eg,
          PBANDI_R_SOGG_TIPO_ANAGRAFICA sta
    WHERE     di.ID_CATEG_ANAGRAFICA_MITT = ca.ID_CATEG_ANAGRAFICA
          AND tdp.ID_DOCUMENTO_INDEX(+) = di.ID_DOCUMENTO_INDEX
          AND sdi.ID_STATO_DOCUMENTO(+) = di.ID_STATO_DOCUMENTO
          AND lvf.id_documento_index(+) = di.id_documento_index
          AND tdi.ID_TIPO_DOCUMENTO_INDEX = di.ID_TIPO_DOCUMENTO_INDEX
          AND pb.ID_PROGETTO = di.ID_PROGETTO
          AND cadi.ID_DOCUMENTO_INDEX = di.ID_DOCUMENTO_INDEX
          AND ta.ID_CATEG_ANAGRAFICA = cadi.ID_CATEG_ANAGRAFICA
          AND sp.id_progetto = di.id_progetto
          AND sp.id_tipo_beneficiario != 4
          AND sp.id_tipo_anagrafica = 1
          AND eg.id_soggetto = sp.id_soggetto
          AND sta.id_tipo_anagrafica = ta.id_tipo_anagrafica;


-- PBANDI_V_PROGETTI_BANDO
CREATE OR REPLACE FORCE VIEW PBANDI.PBANDI_V_PROGETTI_BANDO
(
   PROGR_BANDO_LINEA_INTERVENTO,
   ID_BANDO,
   ID_LINEA_DI_INTERVENTO,
   ID_PROGETTO,
   CODICE_PROGETTO,
   CODICE_VISUALIZZATO,
   DT_AGGIORNAMENTO,
   ID_DOMANDA,
   NUMERO_DOMANDA,
   ID_PROGETTO_FINANZ
)
AS
   SELECT   c.progr_bando_linea_intervento,
            c.id_bando,
            c.id_linea_di_intervento,
            a.id_progetto,
            a.codice_progetto,
            a.codice_visualizzato,
            a.dt_aggiornamento,
            b.id_domanda,
            b.numero_domanda,
            a.id_progetto_finanz
     FROM   PBANDI_T_PROGETTO a,
            PBANDI_T_DOMANDA b,
            PBANDI_R_BANDO_LINEA_INTERVENT c
    WHERE   b.id_domanda = a.id_domanda(+)
            AND c.progr_bando_linea_intervento =
                  b.progr_bando_linea_intervento;
				  
CREATE OR REPLACE FORCE VIEW PBANDI.PBANDI_V_PROCESSO_BEN_BL
(
   CODICE_FISCALE_SOGGETTO,
   ID_SOGGETTO,
   ID_TIPO_ANAGRAFICA,
   DESC_BREVE_TIPO_ANAGRAFICA,
   ID_SOGGETTO_BENEFICIARIO,
   CODICE_FISCALE_BENEFICIARIO,
   DENOMINAZIONE_BENEFICIARIO,
   ID_PROGETTO,
   PROGR_BANDO_LINEA_INTERVENTO,
   NOME_BANDO_LINEA,
   PROCESSO
)
AS
   SELECT   m.codice_fiscale_soggetto,
            m.id_soggetto,
            m.id_tipo_anagrafica,
            dta.desc_breve_tipo_anagrafica,
            ben.id_soggetto_BENEFICIARIO,
            ben.codice_fiscale_BENEFICIARIO,
            ben.DENOMINAZIONE_BENEFICIARIO,
            ben.id_progetto,
            ben.progr_bando_linea_intervento,
            ben.nome_bando_linea,
            pck_pbandi_processo.getprocessobl (
               ben.progr_bando_linea_intervento
            )
               processo
     FROM   (SELECT   rsp.id_soggetto id_soggetto_beneficiario,
                      codice_fiscale_beneficiario,
                      denominazione_beneficiario,
                      rsp.id_progetto,
                      dom.progr_bando_linea_intervento,
                      bl.nome_bando_linea
               FROM   pbandi_r_soggetto_progetto rsp,
                      (SELECT   soggetto.id_soggetto,
                                soggetto.codice_fiscale_soggetto
                                   codice_fiscale_beneficiario,
                                NVL (
                                   eg.denominazione_ente_giuridico,
                                   NVL2 (pf.denominazione_persona_fisica,
                                         ' ',
                                         '')
                                )
                                   AS denominazione_beneficiario
                         FROM   (SELECT   pbandi_t_soggetto.id_soggetto,
                                          pbandi_t_soggetto.codice_fiscale_soggetto,
                                          pbandi_d_tipo_soggetto.desc_breve_tipo_soggetto
                                   FROM   pbandi_t_soggetto,
                                          pbandi_d_tipo_soggetto
                                  WHERE   NVL (
                                             TRUNC(pbandi_d_tipo_soggetto.dt_fine_validita),
                                             TRUNC (SYSDATE + 1)
                                          ) > TRUNC (SYSDATE)
                                          AND pbandi_d_tipo_soggetto.id_tipo_soggetto =
                                                pbandi_t_soggetto.id_tipo_soggetto)
                                soggetto,
                                (SELECT   DISTINCT
                                          rsp.id_soggetto,
                                          FIRST_VALUE(teg.denominazione_ente_giuridico)
                                             OVER (
                                                PARTITION BY teg.id_soggetto
                                                ORDER BY
                                                   teg.dt_inizio_validita DESC,
                                                   teg.id_ente_giuridico DESC
                                             )
                                             denominazione_ente_giuridico
                                   FROM   pbandi_t_ente_giuridico teg,
                                          pbandi_r_soggetto_progetto rsp
                                  WHERE   rsp.id_soggetto = teg.id_soggetto
                                          AND rsp.id_ente_giuridico =
                                                teg.id_ente_giuridico
                                          AND rsp.ID_TIPO_ANAGRAFICA =
                                                (SELECT   dta.id_tipo_anagrafica
                                                   FROM   pbandi_d_tipo_anagrafica dta
                                                  WHERE   dta.desc_breve_tipo_anagrafica =
                                                             'BENEFICIARIO')
                                          AND NVL (rsp.id_tipo_beneficiario,
                                                   '-1') <>
                                                (SELECT   dtb.id_tipo_beneficiario
                                                   FROM   pbandi_d_tipo_beneficiario dtb
                                                  WHERE   dtb.desc_breve_tipo_beneficiario =
                                                             'BEN-ASSOCIATO')
                                          AND NVL (
                                                TRUNC (rsp.dt_fine_validita),
                                                TRUNC (SYSDATE + 1)
                                             ) > TRUNC (SYSDATE)) eg,
                                (SELECT   DISTINCT
                                          rsp.id_soggetto,
                                          FIRST_VALUE(   tpf.cognome
                                                      || ' '
                                                      || tpf.nome)
                                             OVER (
                                                PARTITION BY tpf.id_soggetto
                                                ORDER BY
                                                   tpf.dt_inizio_validita DESC,
                                                   tpf.id_persona_fisica DESC
                                             )
                                             denominazione_persona_fisica
                                   FROM   pbandi_t_persona_fisica tpf,
                                          pbandi_r_soggetto_progetto rsp
                                  WHERE   rsp.id_soggetto = tpf.id_soggetto
                                          AND rsp.id_persona_fisica =
                                                tpf.id_persona_fisica
                                          AND rsp.ID_TIPO_ANAGRAFICA =
                                                (SELECT   dta.id_tipo_anagrafica
                                                   FROM   pbandi_d_tipo_anagrafica dta
                                                  WHERE   dta.desc_breve_tipo_anagrafica =
                                                             'BENEFICIARIO')
                                          AND NVL (rsp.id_tipo_beneficiario,
                                                   '-1') <>
                                                (SELECT   dtb.id_tipo_beneficiario
                                                   FROM   pbandi_d_tipo_beneficiario dtb
                                                  WHERE   dtb.desc_breve_tipo_beneficiario =
                                                             'BEN-ASSOCIATO')
                                          AND NVL (
                                                TRUNC (rsp.dt_fine_validita),
                                                TRUNC (SYSDATE + 1)
                                             ) > TRUNC (SYSDATE)) pf
                        WHERE   soggetto.id_soggetto = eg.id_soggetto(+)
                                AND soggetto.id_soggetto = pf.id_soggetto(+))
                      benInfo,
                      pbandi_t_progetto prog,
                      pbandi_t_domanda dom,
                      pbandi_r_bando_linea_intervent bl
              WHERE   rsp.id_soggetto = benInfo.id_soggetto
                      AND rsp.ID_TIPO_ANAGRAFICA =
                            (SELECT   dta.id_tipo_anagrafica
                               FROM   pbandi_d_tipo_anagrafica dta
                              WHERE   dta.desc_breve_tipo_anagrafica =
                                         'BENEFICIARIO')
                      AND NVL (rsp.id_tipo_beneficiario, '-1') <>
                            (SELECT   dtb.id_tipo_beneficiario
                               FROM   pbandi_d_tipo_beneficiario dtb
                              WHERE   dtb.desc_breve_tipo_beneficiario =
                                         'BEN-ASSOCIATO')
                      AND rsp.id_progetto = prog.id_progetto
                      AND bl.progr_bando_linea_intervento =
                            dom.progr_bando_linea_intervento
                      AND prog.id_domanda = dom.id_domanda
                      AND NVL (TRUNC (rsp.dt_fine_validita),
                               TRUNC (SYSDATE + 1)) > TRUNC (SYSDATE)) ben,
            (SELECT                                                -- DISTINCT
                   tp .id_progetto,
                      ts.codice_fiscale_soggetto,
                      ts.id_soggetto,
                      ts.id_tipo_anagrafica,
                      NULL progr_soggetto_progetto
               FROM   (SELECT   ts1.*,
                                tec.id_ente_competenza,
                                rsta.id_tipo_anagrafica
                         FROM   pbandi_t_soggetto ts1,
                                pbandi_t_ente_competenza tec,
                                pbandi_r_sogg_tipo_anagrafica rsta
                        WHERE   rsta.id_soggetto = ts1.id_soggetto
                                AND NVL (TRUNC (rsta.dt_fine_validita),
                                         TRUNC (SYSDATE + 1)) >
                                      TRUNC (SYSDATE)
                                AND rsta.id_tipo_anagrafica NOT IN
                                         (SELECT   dta.id_tipo_anagrafica
                                            FROM   pbandi_d_tipo_anagrafica dta
                                           WHERE   dta.desc_breve_tipo_anagrafica IN
                                                         ('PERSONA-FISICA',
                                                          'OI-ISTRUTTORE',
                                                          'ADG-ISTRUTTORE'))
                                AND (rsta.id_tipo_anagrafica NOT IN
                                           (SELECT   dta.id_tipo_anagrafica
                                              FROM   pbandi_d_tipo_anagrafica dta
                                             WHERE   dta.desc_breve_tipo_anagrafica IN
                                                           ('BEN-MASTER',
                                                            'OI-IST-MASTER',
                                                            'ADG-IST-MASTER'))
                                     OR (NOT EXISTS
                                            (SELECT   'x'
                                               FROM   pbandi_r_ente_competenza_sogg recs
                                              WHERE   recs.id_soggetto =
                                                         ts1.id_soggetto)
                                         OR EXISTS
                                              (SELECT   'x'
                                                 FROM   pbandi_r_ente_competenza_sogg recs
                                                WHERE   recs.id_soggetto =
                                                           ts1.id_soggetto
                                                        AND recs.id_ente_competenza =
                                                              tec.id_ente_competenza))))
                      ts,
                      pbandi_t_progetto tp,
                      pbandi_t_domanda td,
                      pbandi_r_bando_linea_intervent rbli,
                      pbandi_r_bando_linea_ente_comp rble
              WHERE   td.id_domanda = tp.id_domanda
                      AND rbli.progr_bando_linea_intervento =
                            td.progr_bando_linea_intervento
                      AND rble.progr_bando_linea_intervento =
                            rbli.progr_bando_linea_intervento
                      AND rble.id_ruolo_ente_competenza =
                            (SELECT   dre.id_ruolo_ente_competenza
                               FROM   pbandi_d_ruolo_ente_competenza dre
                              WHERE   dre.desc_breve_ruolo_ente =
                                         'DESTINATARIO')
                      AND ts.id_ente_competenza = rble.id_ente_competenza
             UNION ALL
             SELECT   rsp.id_progetto id_progetto,
                      ts.codice_fiscale_soggetto,
                      ts.id_soggetto,
                      rsp.id_tipo_anagrafica,
                      rsp.progr_soggetto_progetto
               FROM   PBANDI_T_SOGGETTO ts,
                      pbandi_r_soggetto_progetto rsp,
                      pbandi_t_progetto tp
              WHERE       ts.id_soggetto = rsp.id_soggetto
                      AND NVL (TRUNC (rsp.dt_fine_validita),
                               TRUNC (SYSDATE + 1)) > TRUNC (SYSDATE)
                      AND tp.id_progetto = rsp.id_progetto
			  -- Soggetti abilitati per bando linea
			  UNION
			  SELECT p.id_progetto,
					 ts.codice_fiscale_soggetto,
					 ts.id_soggetto,
					 sbli.id_tipo_anagrafica,
					 NULL as progr_soggetto_progetto
				FROM PBANDI_R_SOGG_BANDO_LIN_INT sbli,
					 PBANDI_T_SOGGETTO ts,
					 PBANDI_T_DOMANDA d,
					 PBANDI_T_PROGETTO p
			   WHERE ts.id_soggetto    								= sbli.id_soggetto
				 AND d.progr_bando_linea_intervento					= sbli.progr_bando_linea_intervento
				 AND p.id_domanda         							= d.id_domanda
				 AND NVL(TRUNC(sbli.dt_fine_validita), TRUNC(sysdate+1)) > TRUNC(sysdate)
			) m,
            pbandi_d_tipo_anagrafica dta
    WHERE   m.id_tipo_anagrafica = dta.id_tipo_anagrafica
            AND ben.id_progetto = m.id_progetto
            AND (dta.desc_breve_tipo_anagrafica <> 'PERSONA-FISICA'
                 OR ben.id_soggetto_beneficiario IN
                         (SELECT   rsc.id_soggetto_ente_giuridico
                            FROM   pbandi_r_sogg_prog_sogg_correl rspsc,
                                   pbandi_r_soggetti_correlati rsc
                           WHERE   rsc.progr_soggetti_correlati =
                                      rspsc.progr_soggetti_correlati
                                   AND NVL (TRUNC (rsc.dt_fine_validita),
                                            TRUNC (SYSDATE + 1)) >
                                         TRUNC (SYSDATE)
                                   AND rspsc.progr_soggetto_progetto =
                                         m.progr_soggetto_progetto
                                   AND rsc.id_tipo_soggetto_correlato IN
                                            (SELECT   rrta.id_tipo_soggetto_correlato
                                               FROM   pbandi_r_ruolo_tipo_anagrafica rrta
                                              WHERE   rrta.id_tipo_anagrafica =
                                                         m.id_tipo_anagrafica)));

CREATE OR REPLACE FORCE VIEW PBANDI.PBANDI_V_SOGGETTO_PROGETTO
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
   SELECT      m.id_soggetto
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
     FROM   pbandi_d_tipo_anagrafica dta,
            (SELECT   tp.id_progetto,
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
               FROM   (SELECT   ts1.*,
                                tec.id_ente_competenza,
                                rsta.id_tipo_anagrafica,
                                rsta.dt_inizio_validita,
                                rsta.dt_fine_validita,
                                rsta.flag_aggiornato_flux
                         FROM   pbandi_t_soggetto ts1,
                                pbandi_t_ente_competenza tec,
                                pbandi_d_tipo_anagrafica dta,
                                pbandi_r_sogg_tipo_anagrafica rsta
                        WHERE   rsta.id_soggetto = ts1.id_soggetto
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
                                            (SELECT   'x'
                                               FROM   pbandi_r_ente_competenza_sogg recs
                                              WHERE   recs.id_soggetto =
                                                         ts1.id_soggetto
                                                      AND NVL (
                                                            TRUNC(recs.dt_fine_validita),
                                                            TRUNC (
                                                               SYSDATE + 1
                                                            )
                                                         ) > TRUNC (SYSDATE))
                                         OR EXISTS
                                              (SELECT   'x'
                                                 FROM   pbandi_r_ente_competenza_sogg recs
                                                WHERE   recs.id_soggetto =
                                                           ts1.id_soggetto
                                                        AND recs.id_ente_competenza =
                                                              tec.id_ente_competenza
                                                        AND NVL (
                                                              TRUNC(recs.dt_fine_validita),
                                                              TRUNC (
                                                                 SYSDATE + 1
                                                              )
                                                           ) >
                                                              TRUNC (SYSDATE)))))
                      ts,
                      (SELECT   td.progr_bando_linea_intervento, tp.*
                         FROM   pbandi_t_domanda td, pbandi_t_progetto tp
                        WHERE   td.id_domanda = tp.id_domanda) tp,
                      pbandi_r_bando_linea_intervent rbli,
                      pbandi_r_bando_linea_ente_comp rble
              WHERE   rbli.progr_bando_linea_intervento =
                         tp.progr_bando_linea_intervento(+)
                      AND rble.progr_bando_linea_intervento =
                            rbli.progr_bando_linea_intervento
                      AND rble.id_ruolo_ente_competenza =
                            (SELECT   dre.id_ruolo_ente_competenza
                               FROM   pbandi_d_ruolo_ente_competenza dre
                              WHERE   dre.desc_breve_ruolo_ente =
                                         'DESTINATARIO')
                      AND ts.id_ente_competenza = rble.id_ente_competenza
             UNION ALL
             SELECT   rsp.id_progetto id_progetto,
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
               FROM   PBANDI_T_SOGGETTO ts,
                      pbandi_r_soggetto_progetto rsp,
                      pbandi_t_domanda td,
                      pbandi_r_bando_linea_intervent rbli,
                      pbandi_t_progetto tp
              WHERE   ts.id_soggetto = rsp.id_soggetto
                      AND td.id_domanda = tp.id_domanda
                      AND rbli.progr_bando_linea_intervento =
                            td.progr_bando_linea_intervento
                      AND tp.id_progetto = rsp.id_progetto
              -- Soggetti abilitati per bando linea
              UNION
              SELECT p.id_progetto,
                     p.id_progetto_finanz,
                     rbli.progr_bando_linea_intervento,
                     rbli.nome_bando_linea,
                     ts.codice_fiscale_soggetto,
                     ts.id_soggetto,
                     sbli.id_tipo_anagrafica,
                     sbli.dt_inizio_validita,
                     sbli.dt_fine_validita,
                     NULL as progr_soggetto_progetto,
                     NULL as flag_aggiornato_flux,
                     ts.id_tipo_soggetto,
                     ts.id_utente_agg,
                     ts.id_utente_ins,
                     p.id_istanza_processo,
                     p.codice_visualizzato codice_visualizzato_progetto,
                     p.titolo_progetto,
                     rbli.id_processo
                FROM PBANDI_R_SOGG_BANDO_LIN_INT sbli,
                     PBANDI_R_BANDO_LINEA_INTERVENT rbli,
                     PBANDI_T_SOGGETTO ts,
                     PBANDI_T_DOMANDA d,
                     PBANDI_T_PROGETTO p
               WHERE ts.id_soggetto                                    = sbli.id_soggetto
                 AND d.progr_bando_linea_intervento                    = sbli.progr_bando_linea_intervento
                 AND rbli.progr_bando_linea_intervento                = sbli.progr_bando_linea_intervento
                 AND p.id_domanda                                     = d.id_domanda                      
            ) m
    WHERE   m.id_tipo_anagrafica = dta.id_tipo_anagrafica;
