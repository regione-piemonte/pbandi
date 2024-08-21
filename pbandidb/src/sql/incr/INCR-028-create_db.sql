/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

--CONTABILIA
ALTER TABLE PBANDI_W_IMPEGNI ADD (FONTE VARCHAR2(10), DATA_INSERIMENTO DATE);

ALTER TABLE  PBANDI_W_ATTI_LIQUIDAZIONE_DT ADD (FONTE VARCHAR2(10), DATA_INSERIMENTO DATE);

ALTER TABLE  PBANDI_W_ATTI_LIQUIDAZIONE_DL ADD (FONTE VARCHAR2(10), DATA_INSERIMENTO DATE);

ALTER TABLE  PBANDI_W_ATTI_LIQUIDAZIONE_DM ADD (FONTE VARCHAR2(10), DATA_INSERIMENTO DATE);

--DEMATERIALIZZAZIONE FASE 2

-- TABELLE
--ALTER TABLE PBANDI_T_DOCUMENTO_INDEX
--RENAME COLUMN NUM_PROTOCOLLO TO TIMBRO;






ALTER TABLE PBANDI_T_DOCUMENTO_INDEX
ADD (FLAG_FIRMA_CARTACEA VARCHAR2(1), MESSAGE_DIGEST VARCHAR2(40), ID_STATO_DOCUMENTO INTEGER, 
     DT_VERIFICA_FIRMA DATE, DT_MARCA_TEMPORALE DATE, ID_SOGG_RAPPR_LEGALE  NUMBER(8),ID_SOGG_DELEGATO NUMBER(8),FLAG_TRASM_DEST VARCHAR2(1)
	 );
COMMENT ON COLUMN PBANDI_T_DOCUMENTO_INDEX.MESSAGE_DIGEST IS 'Impronta digitale del documento';
COMMENT ON COLUMN PBANDI_T_DOCUMENTO_INDEX.FLAG_TRASM_DEST IS 'Documento trasmesso a destinatario';
COMMENT ON COLUMN PBANDI_T_DOCUMENTO_INDEX.FLAG_FIRMA_CARTACEA IS 'S=Documento cartaceo, N=Digitale';

ALTER TABLE PBANDI_C_TIPO_DOCUMENTO_INDEX 
ADD FLAG_FIRMABILE VARCHAR2(1);

ALTER TABLE PBANDI_T_PROGETTO ADD FASCICOLO_ACTA VARCHAR2(256);

--ALTER TABLE  PBANDI_R_BANDO_LINEA_INTERVENT ADD PAROLA_CHIAVE  VARCHAR2(30);

ALTER TABLE  PBANDI_R_BANDO_LINEA_ENTE_COMP ADD (PAROLA_CHIAVE  VARCHAR2(30), FEEDBACK_ACTA VARCHAR2(100));

ALTER TABLE PBANDI_R_SOGG_PROG_SOGG_CORREL ADD (DT_FINE_VALIDITA  DATE);

ALTER TABLE PBANDI_D_CAUSALE_EROGAZIONE  ADD (ID_TIPO_DOCUMENTO_INDEX  NUMBER(3));

ALTER TABLE PBANDI_D_CAUSALE_EROGAZIONE ADD 
CONSTRAINT FK_PBANDI_C_TIPO_DOC_INDEX_08
 FOREIGN KEY (ID_TIPO_DOCUMENTO_INDEX)
 REFERENCES PBANDI_C_TIPO_DOCUMENTO_INDEX (ID_TIPO_DOCUMENTO_INDEX);
 
CREATE INDEX IE1_PBANDI_D_CAUSALE_EROGAZ ON PBANDI_D_CAUSALE_EROGAZIONE
(ID_TIPO_DOCUMENTO_INDEX) TABLESPACE PBANDI_IDX;



--
-- MESSAGGI APPLICATIVI

CREATE TABLE PBANDI_D_FUNZIONI_APPL
(ID_FUNZIONE   INTEGER NOT NULL,   
COD_FUNZIONE VARCHAR2(20) NOT NULL, 
DESCR_FUNZIONE VARCHAR2(100) NOT NULL
);

CREATE TABLE PBANDI_T_MESSAGGI_APPL
(
ID_MESSAGGIO  VARCHAR2(20) NOT NULL,
ID_FUNZIONE  INTEGER NOT NULL, 
PROGR_FUNZIONE INTEGER NOT NULL,
MESSAGGIO VARCHAR2(4000) NOT NULL
);

-- DEFINIZIONE PK
ALTER TABLE  PBANDI_D_FUNZIONI_APPL ADD  
( 
CONSTRAINT PK_PBANDI_D_FUNZIONI_APPL PRIMARY KEY (ID_FUNZIONE)
USING INDEX
TABLESPACE PBANDI_IDX 
);
ALTER TABLE PBANDI_T_MESSAGGI_APPL ADD  
( 
CONSTRAINT PK_PBANDI_T_MESSAGGI_APPL PRIMARY KEY (ID_MESSAGGIO)
USING INDEX
TABLESPACE PBANDI_IDX
);

-- DEFINIZIONE FK

ALTER TABLE PBANDI_T_MESSAGGI_APPL ADD 
(CONSTRAINT FK_PBANDI_D_FUNZIONI_APPL_01  FOREIGN KEY (ID_FUNZIONE) REFERENCES PBANDI_D_FUNZIONI_APPL);


-- DEFINIZIONE INDICI

CREATE INDEX IE1_PBANDI_T_MESSAGGI_APPL ON PBANDI_T_MESSAGGI_APPL
(ID_FUNZIONE)  TABLESPACE PBANDI_IDX;

ALTER TABLE PBANDI_D_FUNZIONI_APPL ADD 
(
CONSTRAINT AK1_PBANDI_D_FUNZIONI_APPL
UNIQUE (COD_FUNZIONE)
USING INDEX TABLESPACE PBANDI_IDX
);


 
CREATE TABLE PBANDI_D_STATO_DOCUMENTO_INDEX 
(ID_STATO_DOCUMENTO INTEGER,
DESC_BREVE VARCHAR2(20),
DESCRIZIONE VARCHAR2(200)
);

CREATE TABLE PBANDI_D_SISTEMA_PROTOCOLLO
(
ID_SISTEMA_PROT INTEGER NOT NULL,
DESCRIZIONE VARCHAR2(50) NOT NULL
);

--CREATE TABLE PBANDI_T_DOC_PROTOCOLLO
CREATE TABLE PBANDI_T_DOC_PROTOCOLLO
(
ID_DOCUMENTO_INDEX NUMBER(8) NOT NULL,
ID_SISTEMA_PROT INTEGER,
NUM_PROTOCOLLO VARCHAR2(100),
DT_PROTOCOLLO DATE,
DT_CLASSIFICAZIONE DATE,
ID_UTENTE_INS NUMBER(8),
ID_UTENTE_AGG NUMBER(8),
DT_INSERIMENTO DATE,
DT_AGGIORNAMENTO DATE,
CLASSIFICAZIONE_ACTA VARCHAR2(256)
);

COMMENT ON COLUMN PBANDI_T_DOC_PROTOCOLLO.CLASSIFICAZIONE_ACTA IS 'Oggetto restituito da Acta';

CREATE OR REPLACE TRIGGER TG_PBANDI_T_DOC_PROTOCOLLO_BIU
   before insert or update of ID_SISTEMA_PROT,NUM_PROTOCOLLO on PBANDI_T_DOC_PROTOCOLLO
   referencing old as OLD new as NEW
   for each row
   
DECLARE

    PRAGMA AUTONOMOUS_TRANSACTION;

    v_id_documento_index PBANDI_T_DOC_PROTOCOLLO.id_documento_index%TYPE;

   Cursor c1 (c_id_sistema_prot NUMBER,
              c_num_protocollo VARCHAR2) is
    SELECT id_documento_index
      FROM PBANDI_T_DOC_PROTOCOLLO
      WHERE id_sistema_prot = c_id_sistema_prot
      AND   num_protocollo = c_num_protocollo;



BEGIN
/*
  Controllo che non sia già presente un altro record
   con lo stesso numero di protocollo nell'ambito dello stesso sistema
   Non sono considerati i records con numero protocollo non valorizzato
*/

   OPEN c1 (:new.id_sistema_prot,
            :new.num_protocollo);
   FETCH c1 INTO v_id_documento_index;
   IF c1%FOUND THEN
      RAISE_APPLICATION_ERROR(-20000,'Il protocollo con numero : '||:new.num_protocollo ||' è già presente nel documento con ID:'||v_id_documento_index);
   END IF;
   CLOSE c1;

END;
/


CREATE TABLE PBANDI_T_LOG_VALIDAZ_FIRMA
(ID_LOG INTEGER NOT NULL,
MESSAGGIO VARCHAR2(4000) NOT NULL,
DT_LOG DATE,
FLAG_STATO_VALIDAZIONE VARCHAR2(1),
ID_DOCUMENTO_INDEX NUMBER(8),
ID_UTENTE NUMBER,
DURATION NUMBER(12,3),
METODO VARCHAR2(200),
CODICE_ERRORE VARCHAR2(10),
ID_MESSAGGIO_APPL  VARCHAR2(20)
);

CREATE TABLE PBANDI_T_LOG_PROTOCOLLAZIONE
(ID_LOG INTEGER NOT NULL,
MESSAGGIO VARCHAR2(4000) NOT NULL,
DT_LOG DATE,
METODO VARCHAR2(200),
ID_DOCUMENTO_INDEX NUMBER(8),
ID_UTENTE NUMBER,
DURATION            NUMBER(12,3),
FLAG_ESITO          VARCHAR2(1)
);

-- PK
ALTER TABLE PBANDI_D_STATO_DOCUMENTO_INDEX ADD
(
CONSTRAINT PK_PBANDI_D_STATO_DOC_INDEX PRIMARY KEY (ID_STATO_DOCUMENTO)
USING INDEX
TABLESPACE PBANDI_IDX 
);


ALTER TABLE PBANDI_D_SISTEMA_PROTOCOLLO ADD  
( 
CONSTRAINT PK_PBANDI_D_SISTEMA_PROTOCOLLO PRIMARY KEY (ID_SISTEMA_PROT)
USING INDEX
TABLESPACE PBANDI_IDX 
);
ALTER TABLE PBANDI_T_DOC_PROTOCOLLO ADD  
( 
CONSTRAINT PK_PBANDI_T_DOC_PROTOCOLLO PRIMARY KEY (ID_DOCUMENTO_INDEX)
USING INDEX
TABLESPACE PBANDI_IDX 
);
ALTER TABLE PBANDI_T_LOG_VALIDAZ_FIRMA ADD  
( 
CONSTRAINT PK_PBANDI_T_LOG_VALIDAZ_FIRMA PRIMARY KEY (ID_LOG)
USING INDEX
TABLESPACE PBANDI_IDX 
);

ALTER TABLE PBANDI_T_LOG_PROTOCOLLAZIONE ADD  
( 
CONSTRAINT PK_PBANDI_T_LOG_PROT PRIMARY KEY (ID_LOG)
USING INDEX
TABLESPACE PBANDI_IDX 
);


-- DEFINIZIONE FK
ALTER TABLE PBANDI_T_DOCUMENTO_INDEX ADD
(CONSTRAINT FK_PBANDI_D_STATO_DOC_INDEX_01 FOREIGN KEY (ID_STATO_DOCUMENTO) REFERENCES PBANDI_D_STATO_DOCUMENTO_INDEX);
ALTER TABLE PBANDI_T_DOC_PROTOCOLLO ADD
(CONSTRAINT FK_PBANDI_T_DOCUMENTO_INDEX_05  FOREIGN KEY (ID_DOCUMENTO_INDEX) REFERENCES PBANDI_T_DOCUMENTO_INDEX);
ALTER TABLE PBANDI_T_DOC_PROTOCOLLO ADD 
(CONSTRAINT FK_PBANDI_D_SISTEMA_PROT_01  FOREIGN KEY (ID_SISTEMA_PROT) REFERENCES PBANDI_D_SISTEMA_PROTOCOLLO);
ALTER TABLE PBANDI_T_LOG_VALIDAZ_FIRMA ADD 
(CONSTRAINT FK_PBANDI_T_DOC_FIRMA_DIGIT_01  FOREIGN KEY (ID_DOCUMENTO_INDEX) REFERENCES PBANDI_T_DOCUMENTO_INDEX);
ALTER TABLE PBANDI_T_LOG_VALIDAZ_FIRMA ADD 
(CONSTRAINT FK_PBANDI_T_MESSAGGI_APPL_01  FOREIGN KEY (ID_MESSAGGIO_APPL) REFERENCES PBANDI_T_MESSAGGI_APPL (ID_MESSAGGIO));
ALTER TABLE PBANDI_T_LOG_PROTOCOLLAZIONE ADD 
(CONSTRAINT FK_PBANDI_T_DOC_FIRMA_DIGIT_02  FOREIGN KEY (ID_DOCUMENTO_INDEX) REFERENCES PBANDI_T_DOCUMENTO_INDEX);
ALTER TABLE PBANDI_T_DOCUMENTO_INDEX ADD
(CONSTRAINT FK_PBANDI_T_SOGGETTO_24 FOREIGN KEY (ID_SOGG_RAPPR_LEGALE) REFERENCES PBANDI_T_SOGGETTO(ID_SOGGETTO));
ALTER TABLE PBANDI_T_DOCUMENTO_INDEX ADD
(CONSTRAINT FK_PBANDI_T_SOGGETTO_25 FOREIGN KEY (ID_SOGG_DELEGATO) REFERENCES PBANDI_T_SOGGETTO(ID_SOGGETTO));

-- DEFINIZIONE INDICI
CREATE INDEX IE1_PBANDI_T_DOC_INDEX ON PBANDI_T_DOCUMENTO_INDEX
(ID_STATO_DOCUMENTO) TABLESPACE PBANDI_IDX;
CREATE INDEX IE2_PBANDI_T_DOC_INDEX ON PBANDI_T_DOCUMENTO_INDEX
(ID_SOGG_RAPPR_LEGALE) TABLESPACE PBANDI_IDX;
CREATE INDEX IE3_PBANDI_T_DOC_INDEX ON PBANDI_T_DOCUMENTO_INDEX
(ID_SOGG_DELEGATO) TABLESPACE PBANDI_IDX;
CREATE INDEX IE1_PBANDI_T_DOC_PROTOCOLLO ON PBANDI_T_DOC_PROTOCOLLO
(ID_SISTEMA_PROT)  TABLESPACE PBANDI_IDX;
CREATE INDEX IE1_PBANDI_T_LOG_VALIDAZ_FIRMA ON PBANDI_T_LOG_VALIDAZ_FIRMA
(ID_DOCUMENTO_INDEX)  TABLESPACE PBANDI_IDX;
CREATE INDEX IE1_PBANDI_T_LOG_PROTOC ON PBANDI_T_LOG_PROTOCOLLAZIONE
(ID_DOCUMENTO_INDEX)  TABLESPACE PBANDI_IDX;
CREATE INDEX IE2_PBANDI_T_LOG_VALIDAZ_FIRMA ON PBANDI_T_LOG_VALIDAZ_FIRMA
(ID_MESSAGGIO_APPL)  TABLESPACE PBANDI_IDX;


-- DEFINIZIONE SEQUENCE
CREATE SEQUENCE SEQ_PBANDI_T_LOG_VALIDAZ_FIRMA
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;
CREATE SEQUENCE SEQ_PBANDI_T_LOG_PROTOCOLL
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;

-- Tavole MDD
CREATE TABLE GESTIONETEMPLATES_COUNTER ( 
  MSGID         VARCHAR2 (500)  NOT NULL, 
  TARGETID      VARCHAR2 (100)  NOT NULL, 
  NUM_ATTEMPTS  NUMBER, 
  CONSTRAINT  GESTIONETEMPLATES_COUNTER_PK 
  PRIMARY KEY ( MSGID, TARGETID ) USING INDEX TABLESPACE PBANDI_IDX
 ); 
 
 CREATE TABLE DIGITALSIGN_COUNTER ( 
  MSGID         VARCHAR2 (500)  NOT NULL, 
  TARGETID      VARCHAR2 (100)  NOT NULL, 
  NUM_ATTEMPTS  NUMBER, 
  CONSTRAINT  DIGITALSIGN_COUNTER_PK
  PRIMARY KEY ( MSGID, TARGETID ) USING INDEX TABLESPACE PBANDI_IDX
 ); 

 CREATE TABLE CARICAMENTOMASSIVODOC_COUNTER ( 
  MSGID         VARCHAR2 (500)  NOT NULL, 
  TARGETID      VARCHAR2 (100)  NOT NULL, 
  NUM_ATTEMPTS  NUMBER, 
  CONSTRAINT CARICAMASSIVODOC_COUNTER_PK
  PRIMARY KEY ( MSGID, TARGETID ) USING INDEX TABLESPACE PBANDI_IDX
 ); 
 
--

-- INDICI

CREATE INDEX IE1_PBANDI_R_ENTE_COMPET_SOGG ON PBANDI_R_ENTE_COMPETENZA_SOGG
(ID_SOGGETTO)
TABLESPACE PBANDI_IDX;

CREATE INDEX IE2_PBANDI_R_SOGGETTO_PROGETTO ON PBANDI_R_SOGGETTO_PROGETTO
(ID_SOGGETTO)
TABLESPACE PBANDI_IDX;


CREATE INDEX IE3_PBANDI_R_SOGGETTO_PROGETTO ON PBANDI_R_SOGGETTO_PROGETTO
(ID_ENTE_GIURIDICO)
TABLESPACE PBANDI_IDX;

-- VISTE

--PBANDI_V_SOGGETTO_PROGETTO
CREATE OR REPLACE  VIEW PBANDI_V_SOGGETTO_PROGETTO
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

CREATE OR REPLACE FORCE VIEW PBANDI_V_DOC_INDEX
AS
   WITH ts
        AS (SELECT  ts1.*,
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
                                       'ADG-IST-MASTER'))
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
        AS (SELECT  ts1.*, tec.id_ente_competenza, rsta.id_tipo_anagrafica
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
                                       'ADG-IST-MASTER'))
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
        AS (SELECT  rsp.id_soggetto id_soggetto_beneficiario,
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
        AS (SELECT 
                   tp.id_progetto,
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
                   NULL progr_bando_linea_intervento,
                   rsp.progr_soggetto_progetto,
                   tsogg.codice_fiscale_soggetto,
                   tsogg.id_soggetto,
                   rsp.id_tipo_anagrafica,
                   tp.codice_visualizzato codice_visualizzato_progetto
              FROM PBANDI_T_SOGGETTO tsogg,
                   pbandi_r_soggetto_progetto rsp,
                   pbandi_t_progetto tp
             WHERE     tsogg.id_soggetto = rsp.id_soggetto
                   AND NVL (TRUNC (rsp.dt_fine_validita),
                            TRUNC (SYSDATE + 1)) > TRUNC (SYSDATE)
                   AND tp.id_progetto = rsp.id_progetto),
        spb
        AS (SELECT  DISTINCT m.id_progetto,
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
        lvf --Log Validazione Firma
        AS  (select id_documento_index,codice_errore, b.messaggio
                   from PBANDI_T_LOG_VALIDAZ_FIRMA a, PBANDI_T_MESSAGGI_APPL b
                  where id_log =
                    (select max(id_log) 
                       from   PBANDI_T_LOG_VALIDAZ_FIRMA
                      where id_documento_index = a.id_documento_index)
                        and metodo = 'VERIFY'
                        and flag_stato_validazione = 'N'
                        and a.id_messaggio_appl = b.id_messaggio(+))
   -- MAIN
   SELECT  /*+NO_QUERY_TRANSFORMATION */
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
                        WHERE ID_REGOLA= 42)
                THEN
                        'S'             
          END
             AS FLAG_REGOLA_DEMAT,
          lvf.codice_errore,
          lvf.messaggio,
          tdp.NUM_PROTOCOLLO,
          sdi.DESCRIZIONE DESC_STATO_DOCUMENTO_INDEX		  
    FROM spb, tdis, PBANDI_T_DOCUMENTO_INDEX di,
        	lvf /*Log Validazione Firma*/,
			PBANDI_T_DOC_PROTOCOLLO tdp,
			PBANDI_D_STATO_DOCUMENTO_INDEX sdi
    WHERE     di.id_progetto = spb.id_progetto
          AND di.id_tipo_documento_index = tdis.id_tipo_documento_index
          AND tdis.id_tipo_anagrafica = spb.id_tipo_anagrafica
          AND tdis.id_soggetto = spb.id_soggetto
		  AND sdi.ID_STATO_DOCUMENTO(+) = di.ID_STATO_DOCUMENTO
          AND lvf.id_documento_index(+) = di.id_documento_index
          AND tdp.ID_DOCUMENTO_INDEX(+) = di.ID_DOCUMENTO_INDEX;

--PBANDI_V_BANDO_LINEA_ENTE_COMP	
CREATE OR REPLACE VIEW PBANDI_V_BANDO_LINEA_ENTE_COMP
(
   PROGR_BANDO_LINEA_ENTE_COMP,
   ID_RUOLO_ENTE_COMPETENZA,
   DESC_BREVE_RUOLO_ENTE,
   ID_ENTE_COMPETENZA,
   DESC_ENTE_COMPETENZA,
   DESC_BREVE_ENTE,
   DESC_BREVE_TIPO_ENTE_COMPETENZ,
   PROGR_BANDO_LINEA_INTERVENTO,
   ID_SETTORE_ENTE,
   DESC_BREVE_SETTORE,
   DESC_ENTE_SETTORE,
   ID_INDIRIZZO,
   DT_FINE_VALIDITA,
   PAROLA_CHIAVE,
   FEEDBACK_ACTA
)
AS
   SELECT PROGR_BANDO_LINEA_ENTE_COMP,
          ID_RUOLO_ENTE_COMPETENZA,
          DESC_BREVE_RUOLO_ENTE,
          ID_ENTE_COMPETENZA,
		  a1.DESC_ENTE DESC_ENTE_COMPETENZA,
		  DESC_BREVE_ENTE,
		  DESC_BREVE_TIPO_ENTE_COMPETENZ,
          PROGR_BANDO_LINEA_INTERVENTO,
          ID_SETTORE_ENTE,
		  DESC_BREVE_SETTORE,
		  CASE 
		     WHEN DESC_BREVE_RUOLO_ENTE = 'DESTINATARIO' THEN
		       DESC_BREVE_ENTE||NVL(DESC_BREVE_SETTORE,'000')
          ELSE
              NULL
          END DESC_ENTE_SETTORE,
          NVL (b1.ID_INDIRIZZO, a1.ID_INDIRIZZO) ID_INDIRIZZO,
          a.DT_FINE_VALIDITA,
		  a.PAROLA_CHIAVE,
		  a.FEEDBACK_ACTA
     FROM    (
	          PBANDI_R_BANDO_LINEA_ENTE_COMP a
				  JOIN PBANDI_T_ENTE_COMPETENZA a1
				  USING (ID_ENTE_COMPETENZA)
				  JOIN PBANDI_D_RUOLO_ENTE_COMPETENZA
				  USING (ID_RUOLO_ENTE_COMPETENZA)
				  JOIN PBANDI_D_TIPO_ENTE_COMPETENZA
				  USING (ID_TIPO_ENTE_COMPETENZA)
			  )
          LEFT JOIN
             (   PBANDI_R_BANDO_LINEA_SETTORE b
              JOIN
                 PBANDI_D_SETTORE_ENTE b1
              USING (ID_SETTORE_ENTE))
          USING (PROGR_BANDO_LINEA_INTERVENTO,
                 ID_RUOLO_ENTE_COMPETENZA,
                 ID_ENTE_COMPETENZA);
		  
--PBANDI_V_DOC_INDEX_BATCH		  
CREATE OR REPLACE  VIEW PBANDI_V_DOC_INDEX_BATCH
(
   ID_DOCUMENTO_INDEX,
   AZIONE,
   UUID_NODO,
   PROGR_BANDO_LINEA_INTERVENTO,
   ID_PROGETTO,
   CODICE_VISUALIZZATO,
   NOME_FILE,
   DESC_TIPO_DOC_INDEX,
   ID_STATO_DOCUMENTO,
   DESC_BREVE,
   FLAG_FIRMA_CARTACEA,
   DT_VERIFICA_FIRMA,
   DT_MARCA_TEMPORALE,
   DESCRIZIONE,
   NUM_PROTOCOLLO,
   ID_SOGG_DELEGATO,
   ID_SOGG_RAPPR_LEGALE,
   DESC_BREVE_ENTE,
   DESC_ENTE_COMPETENZA,
   ID_MESSAGGIO_APPL,
   ENTE,
   SETTORE,
   PAROLA_CHIAVE,
   FEEDBACK_ACTA,
   FASCICOLO_ACTA,
   CLASSIFICAZIONE_ACTA,
   FLAG_REGIONALE,
   DENOMINAZIONE_BENEFICIARIO,
   CODICE_FISCALE_BENEFICIARIO
)
AS
   WITH LVF --Lof verifica Firma digitale
        AS (SELECT ID_DOCUMENTO_INDEX, ID_MESSAGGIO_APPL
              FROM PBANDI_T_LOG_VALIDAZ_FIRMA A
             WHERE ID_LOG =
                      (SELECT MAX (ID_LOG)
                         FROM PBANDI_T_LOG_VALIDAZ_FIRMA
                        WHERE ID_DOCUMENTO_INDEX = A.ID_DOCUMENTO_INDEX)
                   AND METODO = 'VERIFY'
                   AND FLAG_STATO_VALIDAZIONE = 'N'),
        LMT AS  -- Log Marcatura temporale
		    (SELECT ID_DOCUMENTO_INDEX, METODO
              FROM PBANDI_T_LOG_VALIDAZ_FIRMA A
             WHERE ID_LOG =
                      (SELECT MAX (ID_LOG)
                         FROM PBANDI_T_LOG_VALIDAZ_FIRMA
                        WHERE ID_DOCUMENTO_INDEX = A.ID_DOCUMENTO_INDEX)
                   AND METODO = 'TIMESTAMP'),
		BEN AS
			(SELECT A.ID_PROGETTO,B.DENOMINAZIONE_ENTE_GIURIDICO, C.CODICE_FISCALE_SOGGETTO
				FROM PBANDI_R_SOGGETTO_PROGETTO A, PBANDI_T_ENTE_GIURIDICO B, PBANDI_T_SOGGETTO C
				WHERE  A.ID_ENTE_GIURIDICO = B.ID_ENTE_GIURIDICO
				AND C.ID_SOGGETTO = A.ID_SOGGETTO
				AND ID_TIPO_BENEFICIARIO != 4
				AND ID_TIPO_ANAGRAFICA = 1
				AND B.DT_FINE_VALIDITA IS NULL
				AND A.DT_FINE_VALIDITA IS NULL)
   --MAIN
   SELECT ID_DOCUMENTO_INDEX,
          CASE
             WHEN     DESC_BREVE IN ('ACQUISITO')
                  AND FLAG_FIRMA_CARTACEA = 'N'
                  AND DT_VERIFICA_FIRMA IS NULL
                  AND lvf.ID_MESSAGGIO_APPL = 'ERR_GENERIC'
             THEN
                'DA_VALIDARE'
             WHEN     DESC_BREVE IN ('VALIDATO')
                  AND FLAG_FIRMA_CARTACEA = 'N'
                  AND DT_VERIFICA_FIRMA IS NOT NULL
                  AND DT_MARCA_TEMPORALE IS NULL
                  AND lmt.METODO = 'TIMESTAMP'
             THEN
                'DA_MARCARE_TEMPORALMENTE'
             WHEN     DESC_BREVE IN ('INVIATO')
                  AND FLAG_FIRMA_CARTACEA = 'N'
                  AND DT_MARCA_TEMPORALE IS NOT NULL
             THEN
                'DA_CLASSIFICARE'
             WHEN     DESC_BREVE IN ('CLASSIFICATO')
                  AND FLAG_FIRMA_CARTACEA = 'N'
                  AND DT_MARCA_TEMPORALE IS NOT NULL
				  AND DT_CLASSIFICAZIONE IS NOT NULL
             THEN
                'DA_PROTOCOLLARE'
		  END   AZIONE,
          UUID_NODO,
		  PROGR_BANDO_LINEA_INTERVENTO,
          ID_PROGETTO,
		  CODICE_VISUALIZZATO,
          NOME_FILE,
          DESC_TIPO_DOC_INDEX,
          ID_STATO_DOCUMENTO,
          DESC_BREVE,
          FLAG_FIRMA_CARTACEA,
          TO_CHAR (DT_VERIFICA_FIRMA, 'DD/MM/YYYY') AS DT_VERIFICA_FIRMA,
          TO_CHAR (DT_MARCA_TEMPORALE, 'DD/MM/YYYY') AS DT_MARCA_TEMPORALE,
          prot.DESCRIZIONE,
          dp.NUM_PROTOCOLLO,
          ID_SOGG_DELEGATO,
          ID_SOGG_RAPPR_LEGALE,
          blec.DESC_BREVE_ENTE,
		  blec.DESC_ENTE_COMPETENZA,
          ID_MESSAGGIO_APPL,
          blec.DESC_BREVE_ENTE ENTE,
          blec.DESC_BREVE_ENTE||NVL(blec.DESC_BREVE_SETTORE,'000') SETTORE,
		  blec.PAROLA_CHIAVE,
		  blec.FEEDBACK_ACTA,
		  FASCICOLO_ACTA,
		  CLASSIFICAZIONE_ACTA,
		  CASE
             WHEN     DESC_BREVE_TIPO_ENTE_COMPETENZ IN ('ADG','REG')
             THEN
                'S'
			 ELSE 'N'
		  END   FLAG_REGIONALE,
		  ben.DENOMINAZIONE_ENTE_GIURIDICO  DENOMINAZIONE_BENEFICIARIO,
		  ben.CODICE_FISCALE_SOGGETTO CODICE_FISCALE_BENEFICIARIO
     FROM PBANDI_T_DOCUMENTO_INDEX
          JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX
             USING (ID_TIPO_DOCUMENTO_INDEX)
          LEFT JOIN PBANDI_D_STATO_DOCUMENTO_INDEX
             USING (ID_STATO_DOCUMENTO)
          JOIN PBANDI_T_PROGETTO
             USING (ID_PROGETTO)
          JOIN PBANDI_T_DOMANDA
             USING (ID_DOMANDA)
          JOIN PBANDI_R_BANDO_LINEA_INTERVENT bli
             USING (PROGR_BANDO_LINEA_INTERVENTO)
          JOIN PBANDI_R_REGOLA_BANDO_LINEA
             USING (PROGR_BANDO_LINEA_INTERVENTO)
          JOIN PBANDI_V_BANDO_LINEA_ENTE_COMP blec
             USING (PROGR_BANDO_LINEA_INTERVENTO)
          LEFT JOIN PBANDI_T_DOC_PROTOCOLLO dp
             USING (ID_DOCUMENTO_INDEX)
          LEFT JOIN PBANDI_D_SISTEMA_PROTOCOLLO prot
             USING (ID_SISTEMA_PROT)
          LEFT JOIN lvf
             USING (ID_DOCUMENTO_INDEX)
          LEFT JOIN lmt
             USING (ID_DOCUMENTO_INDEX)
   		  LEFT JOIN ben
             USING (ID_PROGETTO)
		  WHERE PBANDI_C_TIPO_DOCUMENTO_INDEX.FLAG_FIRMABILE = 'S'
          AND blec.DESC_BREVE_RUOLO_ENTE =
                 'DESTINATARIO'
          AND PBANDI_R_REGOLA_BANDO_LINEA.ID_REGOLA = 42;

--PBANDI_V_DOC_INDEX_BATCH_FINP
CREATE OR REPLACE VIEW PBANDI_V_DOC_INDEX_BATCH_FINP
(
   ID_DOCUMENTO_INDEX,
   UUID_NODO,
   ID_PROGETTO,
   NOME_FILE,
   NOME_FILE_FIRMATO,
   TIPO_INVIO,
   FLAG_FIRMA_CARTACEA,
   FLAG_TRASM_DEST
)
AS
   WITH SF_INVIO AS
        (SELECT /*+MATERIALIZE */ c.ID_DICHIARAZIONE_SPESA ID_TARGET,c.ID_PROGETTO, CASE WHEN MIN(TIPO_INVIO)||MAX(TIPO_INVIO) = 'CC' THEN 'C' 
                       WHEN MIN(TIPO_INVIO)||MAX(TIPO_INVIO) = 'DD'  THEN 'E'
                       ELSE 'I'
                  END TIPO_INVIO
				FROM PBANDI_S_DICH_DOC_SPESA a, PBANDI_R_DOC_SPESA_PROGETTO b, PBANDI_T_DICHIARAZIONE_SPESA c
				WHERE a.ID_DICHIARAZIONE_SPESA = c.ID_DICHIARAZIONE_SPESA
				  AND a.ID_DOCUMENTO_DI_SPESA = b.ID_DOCUMENTO_DI_SPESA
				  AND b.ID_PROGETTO = c.ID_PROGETTO
            GROUP BY c.ID_DICHIARAZIONE_SPESA,c.ID_PROGETTO
		)
   SELECT ID_DOCUMENTO_INDEX,
          UUID_NODO,
          ID_PROGETTO,
          NOME_FILE,
		    CASE WHEN di.FLAG_FIRMA_CARTACEA = 'N' THEN 
		              NOME_FILE||(SELECT valore FROM PBANDI_C_COSTANTI WHERE attributo = 'conf.firmaDigitaleFileExtension') 
			ELSE NULL
			END NOME_FILE_FIRMATO,
		  TIPO_INVIO,
		  di.FLAG_FIRMA_CARTACEA,
		  NVL (di.FLAG_TRASM_DEST, 'N') FLAG_TRASM_DEST
     FROM PBANDI_T_DOCUMENTO_INDEX di
          JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX tdi
             USING (ID_TIPO_DOCUMENTO_INDEX)
          LEFT JOIN PBANDI_D_STATO_DOCUMENTO_INDEX sdi
             USING (ID_STATO_DOCUMENTO)
          JOIN PBANDI_T_PROGETTO
             USING (ID_PROGETTO)
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
		  LEFT JOIN SF_INVIO i
		     USING (ID_TARGET,ID_PROGETTO)
    WHERE     tdi.FLAG_FIRMABILE = 'S'
          AND sdi.DESC_BREVE IN ('INVIATO')
          AND NVL(di.FLAG_FIRMA_CARTACEA,'N')  = 'N'
          AND di.DT_MARCA_TEMPORALE IS NOT NULL
         -- AND NVL (di.FLAG_TRASM_DEST, 'N') = 'N'
          AND rec.DESC_BREVE_RUOLO_ENTE = 'DESTINATARIO'
          AND ec.DESC_BREVE_ENTE = 'FIN';

CREATE OR REPLACE FUNCTION fn_linea_interv_radice (p_id_progetto IN NUMBER) return varchar2 as
       cursor c1 (c_id_progetto NUMBER) is
          select c.id_linea_di_intervento
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;
          
        cursor c2 (c_id_linea_di_intervento NUMBER) is
           select id_linea_di_intervento,desc_breve_linea
           from PBANDI_D_LINEA_DI_INTERVENTO
           where id_linea_di_intervento_padre IS NULL
           connect by prior id_linea_di_intervento_padre = id_linea_di_intervento
           start with id_linea_di_intervento = c_id_linea_di_intervento;
           
        cursor c3 (c_id_linea_di_intervento NUMBER) is 
           select id_linea_di_intervento_attuale,
                  desc_breve_linea
             from PBANDI_R_LINEA_INTERV_MAPPING a,
                  PBANDI_D_LINEA_DI_INTERVENTO b
            where id_linea_di_intervento_migrata = c_id_linea_di_intervento
              and b.id_linea_di_intervento = a.id_linea_di_intervento_attuale;
              
            r_attuale  c3%ROWTYPE;
           
         v_desc_breve_linea_radice VARCHAR2(20);
         v_id_linea_intervento INTEGER;
         v_id_linea_intervento_radice INTEGER;
     
    BEGIN
       OPEN c1 (p_id_progetto);
       FETCH c1 into v_id_linea_intervento;
       IF c1%NOTFOUND THEN
         v_id_linea_intervento := NULL;
       END IF;
       CLOSE c1;
       
       IF v_id_linea_intervento IS NOT NULL THEN
          OPEN c2(v_id_linea_intervento);
          FETCH c2 INTO v_id_linea_intervento_radice,v_desc_breve_linea_radice;
          IF c2%NOTFOUND THEN
             v_desc_breve_linea_radice := NULL;
          ELSE
            -- Controllo se è una linea di intervento migrata
             OPEN c3(v_id_linea_intervento_radice);
             FETCH c3 into r_attuale;
             IF c3%FOUND THEN
                v_desc_breve_linea_radice := r_attuale.desc_breve_linea; -- se migrata allora uso l'attuale
             END IF;
             CLOSE c3;
          END IF;
          CLOSE c2;
       END IF;

       RETURN v_desc_breve_linea_radice;
END fn_linea_interv_radice;
/
  
CREATE OR REPLACE VIEW PBANDI_V_PERC_SOGG_FINANZIAT
(
   ID_PROGETTO,
   ID_TIPO_SOGG_FINANZIAT,
   PERC_TIPO_SOGG_FINANZIATORE,
   TOT_QUOTA_SOGG_FINANZIAT,
   TOT_QUOTA_PROGETTO,
   TOT_AMMESSO,
   NORMATIVA
)
AS
   WITH TAM
        AS (  SELECT rce.ID_CONTO_ECONOMICO,
                     t.ID_PROGETTO,
                     DECODE (SUM (NVL (rce.IMPORTO_AMMESSO_FINANZIAMENTO, 0)),
                             0, 1,
                             SUM (rce.IMPORTO_AMMESSO_FINANZIAMENTO)) -- Per evitare errore Zero Divide
                        TOT_AMMESSO
                FROM PBANDI_T_RIGO_CONTO_ECONOMICO rce,
                     PBANDI_T_CONTO_ECONOMICO ce,
                     PBANDI_T_PROGETTO t,
					 PBANDI_D_STATO_CONTO_ECONOMICO sce,
					 PBANDI_D_TIPOLOGIA_CONTO_ECON tce
               WHERE     rce.ID_CONTO_ECONOMICO = ce.ID_CONTO_ECONOMICO
                     AND t.ID_DOMANDA = ce.ID_DOMANDA
					 AND ce.ID_STATO_CONTO_ECONOMICO = sce.ID_STATO_CONTO_ECONOMICO
					 AND sce.ID_TIPOLOGIA_CONTO_ECONOMICO = tce.ID_TIPOLOGIA_CONTO_ECONOMICO
					 AND tce.desc_breve_tipologia_conto_eco = 'MASTER'
                     AND rce.DT_FINE_VALIDITA IS NULL
                     AND ce.DT_FINE_VALIDITA IS NULL
            GROUP BY rce.ID_CONTO_ECONOMICO, t.ID_PROGETTO),
        TQP
        AS (  SELECT ID_PROGETTO,
                     DECODE (SUM (IMP_QUOTA_SOGG_FINANZIATORE),
                             0, 1,
                             SUM (IMP_QUOTA_SOGG_FINANZIATORE)) -- Per evitare errore Zero Divide
                        TOT_QUOTA_PROGETTO
                FROM PBANDI_R_PROG_SOGG_FINANZIAT a,
                     PBANDI_D_SOGGETTO_FINANZIATORE b,
					 PBANDI_D_TIPO_SOGG_FINANZIAT c
               WHERE a.ID_SOGGETTO_FINANZIATORE = b.ID_SOGGETTO_FINANZIATORE
			     AND c.ID_TIPO_SOGG_FINANZIAT = b.ID_TIPO_SOGG_FINANZIAT
				 AND (B.FLAG_CERTIFICAZIONE       = 'S' OR B.ID_TIPO_SOGG_FINANZIAT   = 3)
                 AND SYSDATE BETWEEN NVL(C.DT_INIZIO_VALIDITA, SYSDATE -1) AND NVL(C.DT_FINE_VALIDITA, SYSDATE +1)	
				 GROUP BY ID_PROGETTO),
        TQS
        AS (  SELECT ID_PROGETTO,
                     b.ID_TIPO_SOGG_FINANZIAT,
                     SUM (IMP_QUOTA_SOGG_FINANZIATORE) TOT_QUOTA_SOGG_FINANZIAT
                FROM PBANDI_R_PROG_SOGG_FINANZIAT a,
                     PBANDI_D_SOGGETTO_FINANZIATORE b,
					 PBANDI_D_TIPO_SOGG_FINANZIAT c
               WHERE a.ID_SOGGETTO_FINANZIATORE = b.ID_SOGGETTO_FINANZIATORE
			     AND c.ID_TIPO_SOGG_FINANZIAT = b.ID_TIPO_SOGG_FINANZIAT
				 AND (B.FLAG_CERTIFICAZIONE       = 'S' OR B.ID_TIPO_SOGG_FINANZIAT   = 3)
                 AND SYSDATE BETWEEN NVL(C.DT_INIZIO_VALIDITA, SYSDATE -1) AND NVL(C.DT_FINE_VALIDITA, SYSDATE +1)			
            GROUP BY ID_PROGETTO, b.ID_TIPO_SOGG_FINANZIAT)
   --MAIN
   SELECT a.ID_PROGETTO,
          ID_TIPO_SOGG_FINANZIAT,
          ROUND ( (a.TOT_QUOTA_SOGG_FINANZIAT * 100 / c.TOT_AMMESSO), 2)
             PERC_TIPO_SOGG_FINANZIATORE,
		  TOT_QUOTA_SOGG_FINANZIAT,
          TOT_QUOTA_PROGETTO,
          TOT_AMMESSO,
          fn_linea_interv_radice (a.ID_PROGETTO) NORMATIVA
     FROM TQS a, TQP b, TAM c
    WHERE a.ID_PROGETTO = b.ID_PROGETTO AND a.ID_PROGETTO = c.ID_PROGETTO;
