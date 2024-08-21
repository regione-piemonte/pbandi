/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

-- PBANDI_R_FORNITORE_APPALTO TO
-- PBANDI_R_FORNITORE_AFFIDAMENTO
ALTER TABLE PBANDI_R_FORNITORE_APPALTO DROP CONSTRAINT FK_PBANDI_D_TIPO_PERCETTORE_01;
ALTER TABLE PBANDI_R_FORNITORE_APPALTO DROP CONSTRAINT FK_PBANDI_T_APPALTO_03;
ALTER TABLE PBANDI_R_FORNITORE_APPALTO DROP CONSTRAINT FK_PBANDI_T_FORNITORE_03;
ALTER TABLE PBANDI_R_FORNITORE_APPALTO DROP CONSTRAINT PK_PBANDI_R_FORNITORE_APPALTO;
DROP INDEX IE1_PBANDI_R_FORNITORE_APPALTO;
DROP INDEX IE2_PBANDI_R_FORNITORE_APPALTO;
DROP INDEX PK_PBANDI_R_FORNITORE_APPALTO;
ALTER TABLE PBANDI_R_FORNITORE_APPALTO RENAME  TO PBANDI_R_FORNITORE_AFFIDAMENTO;
-- PK
ALTER TABLE PBANDI_R_FORNITORE_AFFIDAMENTO ADD (
  CONSTRAINT PK_PBANDI_R_FORNITORE_AFFID
  PRIMARY KEY   (ID_FORNITORE, ID_APPALTO,ID_TIPO_PERCETTORE)
  USING INDEX  TABLESPACE PBANDI_IDX); 
--FK
ALTER TABLE PBANDI.PBANDI_R_FORNITORE_AFFIDAMENTO ADD (
  CONSTRAINT FK_PBANDI_D_TIPO_PERCETTORE_01 
  FOREIGN KEY (ID_TIPO_PERCETTORE) 
  REFERENCES PBANDI_D_TIPO_PERCETTORE (ID_TIPO_PERCETTORE),
  CONSTRAINT FK_PBANDI_T_APPALTO_03 
  FOREIGN KEY (ID_APPALTO) 
  REFERENCES PBANDI_T_APPALTO (ID_APPALTO),
  CONSTRAINT FK_PBANDI_T_FORNITORE_03 
  FOREIGN KEY (ID_FORNITORE) 
  REFERENCES PBANDI_T_FORNITORE (ID_FORNITORE)
  );  
 -- INDEX
CREATE INDEX IE1_PBANDI_R_FORNITORE_APPALTO ON PBANDI.PBANDI_R_FORNITORE_AFFIDAMENTO (ID_APPALTO);
CREATE INDEX IE2_PBANDI_R_FORNITORE_APPALTO ON PBANDI.PBANDI_R_FORNITORE_AFFIDAMENTO  (ID_TIPO_PERCETTORE);
--
-- PBANDI_R_FORNITORE_AFFIDAMENTO
ALTER TABLE PBANDI_R_FORNITORE_AFFIDAMENTO ADD
(DT_INVIO_VERIFICA_AFFIDAMENTO DATE,
 FLG_INVIO_VERIFICA_AFFIDAMENTO VARCHAR2(1)
);
-- PBANDI_R_FORNITORE_AFFIDAMENTO
ALTER TABLE PBANDI_R_FORNITORE_AFFIDAMENTO MODIFY ID_TIPO_PERCETTORE  NOT NULL; 
/*
--PBANDI_T_VARIANTI_AFFIDAMENTI
ALTER TABLE PBANDI_T_VARIANTI_AFFIDAMENTI ADD
(DT_INVIO_VERIFICA_AFFIDAMENTO DATE,
 FLG_INVIO_VERIFICA_AFFIDAMENTO VARCHAR2(1),
 DT_INSERIMENTO DATE
);
*/
-- PBANDI_C_OPERAZIONE -- PBANDI_T_TRACCIAMENTO
ALTER TABLE PBANDI_C_OPERAZIONE MODIFY ID_OPERAZIONE  NUMBER(6); 
ALTER TABLE PBANDI_T_TRACCIAMENTO MODIFY ID_OPERAZIONE  NUMBER(6); 
--
/*
--PBANDI_T_DOCUMENTO_INDEX
ALTER TABLE PBANDI_T_DOCUMENTO_INDEX ADD
(DT_INVIO_VERIFICA_AFFIDAMENTO DATE,
FLG_INVIO_VERIFICA_AFFIDAMENTO VARCHAR2(1)
);
*/
-- PBANDI_T_FILE_ENTITA
ALTER TABLE PBANDI_T_FILE_ENTITA ADD
(DT_ENTITA DATE,
 FLAG_ENTITA VARCHAR2(1)
);
COMMENT ON COLUMN PBANDI_T_FILE_ENTITA.DT_ENTITA  IS 'il valore dell'' attributo specifica quando  l''entita viene coinvolta dal relativo flusso di business ad es. data associazione file affidamenti' ;
COMMENT ON COLUMN PBANDI_T_FILE_ENTITA.FLAG_ENTITA  IS 'il valore dell'' attributo specifica quando  l''entita viene coinvolta dal relativo flusso di business ad es. invio s o n  file per un affidamento' ;
--
--PBANDI_T_PROPOSTA_CERTIFICAZ
ALTER TABLE PBANDI_T_PROPOSTA_CERTIFICAZ ADD DOMANDA_PAGAMENTO VARCHAR2(99);
--
-- FONDI DI GARANZIA
-- PBANDI_W_FONDI_GARANZIA
CREATE TABLE PBANDI_W_FONDI_GARANZIA
(
CODICE_DOMANDA    VARCHAR2(60),
CODICE_FISCALE    VARCHAR2(16),
FLAG_SOGGETTO_PUBBLICO VARCHAR2(1),
CODICE_UNI_IPA   VARCHAR2(10),
DENOMINAZIONE_SOGGETTO VARCHAR2(255),
FORMA_ECONOMICA    VARCHAR2(10),
SETTORE_ATTIVITA_ECONOMICA    VARCHAR2(120),
TITOLO_PROGETTO    VARCHAR2(500),
TIPO_AIUTO    VARCHAR2(2),
CODICE_REGIONE    VARCHAR2(3),
CODICE_PROVINCIA    VARCHAR2(3),
CODICE_COMUNE    VARCHAR2(3),
FINANZIAMENTO_AMMESSO    NUMBER(13,2),
FINANZIAMENTO_EROGATO    NUMBER(13,2),
IMPORTO_CONCESSO_GARANTI NUMBER(13,2),
IMPORTO_CONCESSO_FONDO    NUMBER(13,2),
IMPORTO_GARANZIA    NUMBER(13,2),
TIPOLOGIA_IMPEGNO    VARCHAR2(5),
CODICE_IMPEGNO    VARCHAR2(20),
IMPORTO_IMPEGNO    NUMBER(13,2),
CODICE_DISIMPEGNO    VARCHAR2(20),
IMPORTO_DISIMPEGNO    NUMBER(13,2),
CAUSALE_DISIMPEGNO VARCHAR2(3),
IMPORTO_ESCUSSO    NUMBER(13,2)
);
-- PBANDI_T_FONDI_GARANZIA
CREATE TABLE PBANDI_T_FONDI_GARANZIA
(
ID_FONDO  NUMBER(10),
CODICE_DOMANDA    VARCHAR2(60),
CODICE_FISCALE    VARCHAR2(16),
FLAG_SOGGETTO_PUBBLICO VARCHAR2(1),
CODICE_UNI_IPA   VARCHAR2(10),
DENOMINAZIONE_SOGGETTO VARCHAR2(255),
FORMA_ECONOMICA    VARCHAR2(10),
SETTORE_ATTIVITA_ECONOMICA    VARCHAR2(120),
TITOLO_PROGETTO    VARCHAR2(500),
TIPO_AIUTO    VARCHAR2(2),
CODICE_REGIONE    VARCHAR2(3),
CODICE_PROVINCIA    VARCHAR2(3),
CODICE_COMUNE    VARCHAR2(3),
FINANZIAMENTO_AMMESSO    NUMBER(13,2),
FINANZIAMENTO_EROGATO    NUMBER(13,2),
IMPORTO_CONCESSO_GARANTI NUMBER(13,2),
IMPORTO_CONCESSO_FONDO    NUMBER(13,2),
IMPORTO_GARANZIA    NUMBER(13,2),
TIPOLOGIA_IMPEGNO    VARCHAR2(5),
CODICE_IMPEGNO    VARCHAR2(20),
IMPORTO_IMPEGNO    NUMBER(13,2),
CODICE_DISIMPEGNO    VARCHAR2(20),
IMPORTO_DISIMPEGNO    NUMBER(13,2),
CAUSALE_DISIMPEGNO VARCHAR2(3),
IMPORTO_ESCUSSO    NUMBER(13,2),
DATA_CREAZIONE  DATE
);
-- SEQUENCE
CREATE SEQUENCE SEQ_PBANDI_T_FONDI_GARANZIA
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;
--
-- CERTIFICAZIONE
ALTER TABLE PBANDI_T_DETT_PROP_CERT_ANNUAL ADD
(AVANZAMENTO_ANNUALE NUMBER (17,2),
 CERTIFICATO_LORDO_CUMULATO NUMBER (17,2),
 CERTIFICATO_NETTO_CUMULATO NUMBER (17,2),
 TOTALE_EROGAZIONI_CUMULATE NUMBER (17,2),
 COLONNA_C NUMBER (17,2)
);
--
-- AFFIDAMENTI
--PBANDI_D_STATO_AFFIDAMENTO
CREATE TABLE PBANDI_D_STATO_AFFIDAMENTO
(ID_STATO_AFFIDAMENTO NUMBER(4),
 DESC_BREVE_STATO_AFFIDAMENTO VARCHAR2(100),
 DESC_STATO_AFFIDAMENTO VARCHAR2(2000)
);
ALTER TABLE PBANDI_D_STATO_AFFIDAMENTO ADD 
(CONSTRAINT PK_PBANDI_D_STATO_AFFIDAMENTO PRIMARY KEY (ID_STATO_AFFIDAMENTO)
USING INDEX  TABLESPACE PBANDI_IDX);
--PBANDI_T_APPALTO
ALTER TABLE PBANDI_T_APPALTO ADD ID_STATO_AFFIDAMENTO NUMBER(4);
--FK
ALTER TABLE PBANDI_T_APPALTO ADD
CONSTRAINT FK_PBANDI_D_STATO_AFFID_01
FOREIGN KEY (ID_STATO_AFFIDAMENTO) 
REFERENCES PBANDI_D_STATO_AFFIDAMENTO (ID_STATO_AFFIDAMENTO);
-- INDEX
CREATE INDEX IE2_PBANDI_T_APPALTO ON PBANDI_T_APPALTO (ID_STATO_AFFIDAMENTO) TABLESPACE PBANDI_IDX;
--PBANDI_D_TIPOLOGIE _VARIANTI
CREATE TABLE PBANDI_D_TIPOLOGIE_VARIANTI
(ID_TIPOLOGIA_VARIANTE NUMBER(4),
DESCRIZIONE VARCHAR2(2000)
);
--PK
ALTER TABLE PBANDI_D_TIPOLOGIE_VARIANTI ADD 
(CONSTRAINT PK_PBANDI_D_TIPOLOGIE_VARIANTI  PRIMARY KEY (ID_TIPOLOGIA_VARIANTE)
USING INDEX  TABLESPACE PBANDI_IDX);
-- PBANDI_T_VARIANTI_AFFIDAMENTI
CREATE TABLE PBANDI_T_VARIANTI_AFFIDAMENTI
(ID_VARIANTE NUMBER(4),
 IMPORTO NUMBER(17,2) NOT NULL,
 ID_TIPOLOGIA_VARIANTE NUMBER(4) NOT NULL,
 ID_APPALTO NUMBER(8) NOT NULL,
 NOTE VARCHAR2(4000), 
 DT_INVIO_VERIFICA_AFFIDAMENTO DATE,
 FLG_INVIO_VERIFICA_AFFIDAMENTO VARCHAR2(1),
 DT_INSERIMENTO DATE NOT NULL 
);
--PK
ALTER TABLE PBANDI_T_VARIANTI_AFFIDAMENTI ADD 
(CONSTRAINT PK_PBANDI_T_VARIANTI_AFFID  PRIMARY KEY (ID_VARIANTE)
USING INDEX  TABLESPACE PBANDI_IDX);
--FK
ALTER TABLE PBANDI_T_VARIANTI_AFFIDAMENTI ADD
CONSTRAINT FK_PBANDI_D_TIPOL_VARIANTI_01
FOREIGN KEY (ID_TIPOLOGIA_VARIANTE) 
REFERENCES PBANDI_D_TIPOLOGIE_VARIANTI (ID_TIPOLOGIA_VARIANTE);
--FK
ALTER TABLE PBANDI_T_VARIANTI_AFFIDAMENTI ADD
CONSTRAINT FK_PBANDI_T_APPALTO_04
FOREIGN KEY (ID_APPALTO) 
REFERENCES PBANDI_T_APPALTO (ID_APPALTO);
-- INDEX
CREATE INDEX IE1_PBANDI_T_VARIANTI_AFFID ON PBANDI_T_VARIANTI_AFFIDAMENTI (ID_TIPOLOGIA_VARIANTE) TABLESPACE PBANDI_IDX;
CREATE INDEX IE2_PBANDI_T_VARIANTI_AFFID ON PBANDI_T_VARIANTI_AFFIDAMENTI (ID_APPALTO) TABLESPACE PBANDI_IDX;
--SEQUENCE
CREATE SEQUENCE SEQ_PBANDI_T_VARIANTI_AFFID
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;
--
-- PBANDI_T_ESITI_NOTE_AFFIDAMENT
CREATE TABLE PBANDI_T_ESITI_NOTE_AFFIDAMENT
(ID_ESITO NUMBER(8),
 FASE  NUMBER,           --  fase 1 o fase 2
 ESITO VARCHAR2(30),-- POSITIVO o NEGATIVO
 ID_CHECKLIST NUMBER(8),
 DATA_INS DATE,
 ID_UTENTE_INS NUMBER(8),
 NOTE VARCHAR2(4000)
 );
 --PK
 ALTER TABLE PBANDI_T_ESITI_NOTE_AFFIDAMENT ADD 
(CONSTRAINT PK_PBANDI_T_ESITI_NOTE_AFFID  PRIMARY KEY (ID_ESITO)
USING INDEX  TABLESPACE PBANDI_IDX);
-- FK
ALTER TABLE PBANDI_T_ESITI_NOTE_AFFIDAMENT ADD
CONSTRAINT FK_PBANDI_T_CHECKLIST_04
FOREIGN KEY ( ID_CHECKLIST) 
REFERENCES PBANDI_T_CHECKLIST ( ID_CHECKLIST);
-- INDEX
CREATE INDEX IE1_PBANDI_T_ESIT_NOT_AFFID ON  PBANDI_T_ESITI_NOTE_AFFIDAMENT (ID_CHECKLIST) TABLESPACE PBANDI_IDX;
-- SEQUENCE
CREATE SEQUENCE SEQ_PBANDI_T_ESIT_NOT_AFFID
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;
-- PBANDI_R_DOC_SPESA_PROGETTO
ALTER TABLE PBANDI_R_DOC_SPESA_PROGETTO ADD ID_APPALTO NUMBER(8);
--FK
ALTER TABLE PBANDI_R_DOC_SPESA_PROGETTO ADD
CONSTRAINT FK_PBANDI_T_APPALTO_05
FOREIGN KEY (ID_APPALTO) 
REFERENCES PBANDI_T_APPALTO (ID_APPALTO);
-- INDEX
CREATE INDEX IE1_PBANDI_R_DOC_SPESA_PROG ON  PBANDI_R_DOC_SPESA_PROGETTO (ID_APPALTO) TABLESPACE PBANDI_IDX;
--
--
-- CHECKLIST
--PBANDI_C_CHECKLIST_MODEL_HTML
ALTER TABLE PBANDI_C_CHECKLIST_MODEL_HTML ADD DESCRIZIONE VARCHAR2(2000);
--
--
-- PBANDI_T_AFFIDAMENTO_CHECKLIST
CREATE TABLE PBANDI_T_AFFIDAMENTO_CHECKLIST 
(ID_AFFIDAMENTO_CHECKLIST NUMBER(8),
 ID_NORMA NUMBER(3),
 ID_TIPO_AFFIDAMENTO INTEGER,
 ID_TIPO_APPALTO  NUMBER(3),
 ID_TIPOLOGIA_AGGIUDICAZ NUMBER(3),
 SOPRA_SOGLIA VARCHAR2(1),
 ID_MODELLO_CD NUMBER(3),
 ID_MODELLO_CL NUMBER(3),
 RIF_CHECKLIST_AFFIDAMENTI VARCHAR2(1000)
);
--PK
ALTER TABLE PBANDI_T_AFFIDAMENTO_CHECKLIST ADD 
(CONSTRAINT PK_PBANDI_T_AFFIDAMENTO_CHECK PRIMARY KEY (ID_AFFIDAMENTO_CHECKLIST)
USING INDEX  TABLESPACE PBANDI_IDX);
--FK
ALTER TABLE  PBANDI_T_AFFIDAMENTO_CHECKLIST ADD
CONSTRAINT FK_PBANDI_D_TIPOL_APPALTO_01
FOREIGN KEY (ID_TIPO_APPALTO) 
REFERENCES PBANDI_D_TIPOLOGIA_APPALTO (ID_TIPOLOGIA_APPALTO );
--FK
ALTER TABLE  PBANDI_T_AFFIDAMENTO_CHECKLIST ADD
CONSTRAINT FK_PBANDI_D_NORMAT_AFFID_01
FOREIGN KEY (ID_NORMA) 
REFERENCES PBANDI_D_NORMATIVA_AFFIDAMENTO (ID_NORMA);
--FK
ALTER TABLE  PBANDI_T_AFFIDAMENTO_CHECKLIST ADD
CONSTRAINT FK_PBANDI_D_TIPO_AFFIDAMEN_02
FOREIGN KEY (ID_TIPO_AFFIDAMENTO) 
REFERENCES PBANDI_D_TIPO_AFFIDAMENTO (ID_TIPO_AFFIDAMENTO);
--FK
ALTER TABLE  PBANDI_T_AFFIDAMENTO_CHECKLIST ADD
CONSTRAINT FK_PBANDI_D_TIPOL_AGGIUDIC_01
FOREIGN KEY (ID_TIPOLOGIA_AGGIUDICAZ) 
REFERENCES PBANDI_D_TIPOLOGIA_AGGIUDICAZ (ID_TIPOLOGIA_AGGIUDICAZ );
--FK
ALTER TABLE  PBANDI_T_AFFIDAMENTO_CHECKLIST ADD
CONSTRAINT FK_PBANDI_C_MODELLO_04
FOREIGN KEY (ID_MODELLO_CD) 
REFERENCES PBANDI_C_MODELLO (ID_MODELLO);
--FK
ALTER TABLE  PBANDI_T_AFFIDAMENTO_CHECKLIST ADD
CONSTRAINT FK_PBANDI_C_MODELLO_05
FOREIGN KEY (ID_MODELLO_CL) 
REFERENCES PBANDI_C_MODELLO (ID_MODELLO);
-- INDEX
CREATE UNIQUE INDEX AK1_PBANDI_T_AFFID_CHECKLIST ON PBANDI_T_AFFIDAMENTO_CHECKLIST 
(ID_NORMA, ID_TIPO_AFFIDAMENTO, ID_TIPO_APPALTO, ID_TIPOLOGIA_AGGIUDICAZ, SOPRA_SOGLIA ) TABLESPACE PBANDI_IDX;

-- PBANDI_T_APPALTO
ALTER TABLE PBANDI_T_APPALTO RENAME COLUMN SOPRA_SOGLIE TO  SOPRA_SOGLIA;

-- VIEW
/* Formatted on 15/02/2018 14:43:31 (QP5 v5.163.1008.3004) */
CREATE OR REPLACE FORCE VIEW PBANDI.PBANDI_V_CHECKLIST_MODEL_HTML
(
   ID_MODELLO,
   HTML,
   DESCRIZIONE,
   DESCR_TIPO_MODELLO
)
AS
   (SELECT PBANDI_C_CHECKLIST_MODEL_HTML."ID_MODELLO",
           PBANDI_C_CHECKLIST_MODEL_HTML."HTML",
           PBANDI_C_CHECKLIST_MODEL_HTML."DESCRIZIONE",
           PBANDI_D_TIPO_MODELLO.DESCR_TIPO_MODELLO
      FROM PBANDI_C_CHECKLIST_MODEL_HTML,
           PBANDI_C_MODELLO,
           PBANDI_D_TIPO_MODELLO
     WHERE PBANDI_C_CHECKLIST_MODEL_HTML.id_modello =
              PBANDI_C_MODELLO.id_modello
           AND PBANDI_C_MODELLO.id_tipo_modello =
                  PBANDI_D_TIPO_MODELLO.id_tipo_modello);
				  
-- PBANDI_V_DOC_INDEX
CREATE OR REPLACE FORCE VIEW PBANDI_V_DOC_INDEX
(
   ID_DOCUMENTO_INDEX,
   ID_TARGET,
   UUID_NODO,
   REPOSITORY,
   DT_INSERIMENTO_INDEX,
   ID_TIPO_DOCUMENTO_INDEX,
   ID_ENTITA,
   ID_UTENTE_INS,
   ID_UTENTE_AGG,
   NOME_FILE,
   NOTE_DOCUMENTO_INDEX,
   ID_PROGETTO,
   DT_AGGIORNAMENTO_INDEX,
   VERSIONE,
   ID_MODELLO,
   PROGR_BANDO_LINEA_INTERVENTO,
   CODICE_VISUALIZZATO,
   ID_SOGGETTO,
   ID_TIPO_ANAGRAFICA,
   ID_SOGGETTO_BENEFICIARIO,
   DESC_BREVE_TIPO_ANAGRAFICA,
   DESC_BREVE_TIPO_DOC_INDEX,
   DESC_TIPO_DOC_INDEX,
   BENEFICIARIO,
   CODICE_FISCALE_BENEFICIARIO,
   DT_VERIFICA_FIRMA,
   DT_MARCA_TEMPORALE,
   FLAG_FIRMA_CARTACEA,
   ID_STATO_DOCUMENTO,
   FLAG_FIRMABILE,
   FLAG_REGOLA_DEMAT,
   CODICE_ERRORE,
   MESSAGGIO,
   NUM_PROTOCOLLO,
   DESC_STATO_DOCUMENTO_INDEX,
   ID_CATEG_ANAGRAFICA_MITT,
   DESC_CATEG_ANAGRAFICA_MITT
)
AS
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
          NULL DESC_CATEG_ANAGRAFICA_MITT
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
          ca.DESC_CATEG_ANAGRAFICA DESC_CATEG_ANAGRAFICA_MITT
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

 
 







