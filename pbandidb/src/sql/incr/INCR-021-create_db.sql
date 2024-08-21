/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

-- OTTIMIZZAZIONE CHECKLIST
CREATE TABLE PBANDI_R_PROGETTI_APPALTI
(
  ID_PROGETTO  NUMBER(8),
  ID_APPALTO   NUMBER(8)
)
TABLESPACE PBANDI_SMALL_TBL
;

ALTER TABLE PBANDI_R_PROGETTI_APPALTI ADD (
  CONSTRAINT PK_PBANDI_R_PROGETTI_APPALTI
  PRIMARY KEY
  (ID_PROGETTO, ID_APPALTO)
 USING INDEX TABLESPACE PBANDI_SMALL_IDX);
 
 ALTER TABLE PBANDI_R_PROGETTI_APPALTI ADD (
  CONSTRAINT FK_PBANDI_T_PROGETTO_33 
  FOREIGN KEY (ID_PROGETTO) 
  REFERENCES PBANDI_T_PROGETTO (ID_PROGETTO));

ALTER TABLE PBANDI_R_PROGETTI_APPALTI ADD (
  CONSTRAINT FK_PBANDI_T_APPALTO_01 
  FOREIGN KEY (ID_APPALTO) 
  REFERENCES PBANDI_T_APPALTO (ID_APPALTO));
  
  

 CREATE INDEX IE_PBANDI_R_PROG_APPALTI_01 ON PBANDI_R_PROGETTI_APPALTI
(ID_APPALTO)
 TABLESPACE PBANDI_SMALL_IDX;

COMMENT ON TABLE PBANDI_R_PROGETTI_APPALTI IS 'Tabella di associazione degli appalti al progetto per limitare la checklist';

COMMENT ON COLUMN PBANDI_R_PROGETTI_APPALTI.ID_PROGETTO IS 'Chiave esterna PBANDI_T_PROGETTO';

COMMENT ON COLUMN PBANDI_R_PROGETTI_APPALTI.ID_APPALTO IS 'Chiave esterna PBANDI_T_APPALTO';

--PBANDI_W_PROG_SOGG_FIN_LOG
CREATE TABLE PBANDI_W_PROG_SOGG_FIN_LOG
(
  ID_PROGETTO                   NUMBER(8),
  ID_SOGGETTO_FINANZIATORE      NUMBER(3),
  PERC_QUOTA_SOGG_FINANZIATORE  NUMBER(5,2),
  ID_UTENTE_INS                 NUMBER(8),
  ID_UTENTE_AGG                 NUMBER(8),
  ESTREMI_PROVV                 VARCHAR2(200 BYTE),
  PROGR_PROG_SOGG_FINANZIAT     NUMBER(8),
  NOTE                          VARCHAR2(4000 BYTE),
  FLAG_ECONOMIE                 CHAR(1 BYTE),
  ID_NORMA                      NUMBER(3),
  ID_DELIBERA                   NUMBER(3),
  ID_COMUNE                     NUMBER(8),
  ID_PROVINCIA                  NUMBER(4),
  ID_PERIODO                    NUMBER(8),
  ID_SOGGETTO                   NUMBER(8),
  DT_INSERIMENTO                DATE,
  DT_AGGIORNAMENTO              DATE,
  IMP_QUOTA_SOGG_FINANZIATORE   NUMBER(13,2),
  TIPO_OPERAZIONE_LOG           VARCHAR2(1 BYTE),
  DT_OPERAZIONE_LOG             DATE,
  NOME_TERMINALE_LOG            VARCHAR2(1000 BYTE)
)
TABLESPACE PBANDI_SMALL_TBL;



CREATE OR REPLACE TRIGGER TG_PBANDI_R_PROG_SOGG_FIN_ID
   after insert or delete on PBANDI_R_PROG_SOGG_FINANZIAT
   referencing old as OLD new as NEW
   for each row
   
   
BEGIN

    IF inserting THEN

		INSERT INTO PBANDI_W_PROG_SOGG_FIN_LOG
		(
		  ID_PROGETTO,
		  ID_SOGGETTO_FINANZIATORE,
		  PERC_QUOTA_SOGG_FINANZIATORE,
		  ID_UTENTE_INS,
		  ID_UTENTE_AGG,
		  ESTREMI_PROVV,
		  PROGR_PROG_SOGG_FINANZIAT,
		  NOTE,
		  FLAG_ECONOMIE,
		  ID_NORMA,
		  ID_DELIBERA,
		  ID_COMUNE,
		  ID_PROVINCIA,
		  ID_PERIODO,
		  ID_SOGGETTO,
		  DT_INSERIMENTO,
		  DT_AGGIORNAMENTO,
		  IMP_QUOTA_SOGG_FINANZIATORE,
		  TIPO_OPERAZIONE_LOG,
		  DT_OPERAZIONE_LOG,
		  NOME_TERMINALE_LOG)
		VALUES
		 (:NEW.ID_PROGETTO,
		  :NEW.ID_SOGGETTO_FINANZIATORE,
		  :NEW.PERC_QUOTA_SOGG_FINANZIATORE,
		  :NEW.ID_UTENTE_INS,
		  :NEW.ID_UTENTE_AGG,
		  :NEW.ESTREMI_PROVV,
		  :NEW.PROGR_PROG_SOGG_FINANZIAT,
		  :NEW.NOTE,
		  :NEW.FLAG_ECONOMIE,
		  :NEW.ID_NORMA,
		  :NEW.ID_DELIBERA,
		  :NEW.ID_COMUNE,
		  :NEW.ID_PROVINCIA,
		  :NEW.ID_PERIODO,
		  :NEW.ID_SOGGETTO,
		  :NEW.DT_INSERIMENTO,
		  :NEW.DT_AGGIORNAMENTO,
		  :NEW.IMP_QUOTA_SOGG_FINANZIATORE,
		  'I',
		  SYSDATE,
		  sys_context('userenv','terminal'));
   END IF;

    IF deleting THEN

		INSERT INTO PBANDI_W_PROG_SOGG_FIN_LOG
		(
		  ID_PROGETTO,
		  ID_SOGGETTO_FINANZIATORE,
		  PERC_QUOTA_SOGG_FINANZIATORE,
		  ID_UTENTE_INS,
		  ID_UTENTE_AGG,
		  ESTREMI_PROVV,
		  PROGR_PROG_SOGG_FINANZIAT,
		  NOTE,
		  FLAG_ECONOMIE,
		  ID_NORMA,
		  ID_DELIBERA,
		  ID_COMUNE,
		  ID_PROVINCIA,
		  ID_PERIODO,
		  ID_SOGGETTO,
		  DT_INSERIMENTO,
		  DT_AGGIORNAMENTO,
		  IMP_QUOTA_SOGG_FINANZIATORE,
		  TIPO_OPERAZIONE_LOG,
		  DT_OPERAZIONE_LOG,
		  NOME_TERMINALE_LOG)
		VALUES
		 (:OLD.ID_PROGETTO,
		  :OLD.ID_SOGGETTO_FINANZIATORE,
		  :OLD.PERC_QUOTA_SOGG_FINANZIATORE,
		  :OLD.ID_UTENTE_INS,
		  :OLD.ID_UTENTE_AGG,
		  :OLD.ESTREMI_PROVV,
		  :OLD.PROGR_PROG_SOGG_FINANZIAT,
		  :OLD.NOTE,
		  :OLD.FLAG_ECONOMIE,
		  :OLD.ID_NORMA,
		  :OLD.ID_DELIBERA,
		  :OLD.ID_COMUNE,
		  :OLD.ID_PROVINCIA,
		  :OLD.ID_PERIODO,
		  :OLD.ID_SOGGETTO,
		  :OLD.DT_INSERIMENTO,
		  :OLD.DT_AGGIORNAMENTO,
		  :OLD.IMP_QUOTA_SOGG_FINANZIATORE,
		  'D',
		  SYSDATE,
		  sys_context('userenv','terminal'));
   END IF;

END;
/

--PBANDI_W_BANDO_LINEA_SCHEDIN
CREATE TABLE PBANDI_W_BANDO_LINEA_SCHEDIN
(
  ID_LINEA_DI_INTERVENTO        VARCHAR2(10),
  NOME_LINEA                    VARCHAR2(20),
  DESC_LINEA                    VARCHAR2(255),
  ID_LINEA_DI_INTERVENTO_PADRE  VARCHAR2(10),
  PROGR_BANDO_LINEA_INTERVENTO  VARCHAR2(10),
  NOME_BANDO_LINEA              VARCHAR2(255),
  LIVELLO                       VARCHAR2(10)
)
TABLESPACE PBANDI_SMALL_TBL;

COMMENT ON TABLE PBANDI_W_BANDO_LINEA_SCHEDIN IS 'Tabella di work per i bando linea da inviare a SCHEDIN';

--PBANDI_T_PROGETTO
ALTER TABLE PBANDI_T_PROGETTO  ADD FLAG_PRENOT_AVVIO NUMBER(1);
COMMENT ON COLUMN PBANDI_T_PROGETTO.FLAG_PRENOT_AVVIO IS '1= Prenotato per avvio in modalità batch';


--PBANDI_T_CSP_PROGETTO
ALTER TABLE PBANDI_T_CSP_PROGETTO  ADD ID_ISTANZA_DOMANDA NUMBER;
COMMENT ON COLUMN PBANDI_T_CSP_PROGETTO.ID_ISTANZA_DOMANDA IS 'Riferimento a istanza della domanda proveniente da SCHEDIN';

ALTER TABLE PBANDI_T_CSP_PROGETTO ADD (
  CONSTRAINT AK1_PBANDI_T_CSP_PROGETTO
  UNIQUE (ID_ISTANZA_DOMANDA)
  USING INDEX TABLESPACE PBANDI_SMALL_IDX );
  
  
--PBANDI_W_CSP_PROGETTO_SOGGETTO
CREATE TABLE PBANDI_W_CSP_PROGETTO_SOGGETTO
(
  ID_ISTANZA_DOMANDA            NUMBER          NOT NULL,
  DT_PRESENTAZIONE_DOMANDA      DATE,
  DT_INIZIO_VALIDITA            DATE,
  PROGR_BANDO_LINEA_INTERVENTO  NUMBER(8)       NOT NULL,
  TITOLO_PROGETTO               VARCHAR2(255)   NOT NULL,
  CUP                           VARCHAR2(20),
  PARTITA_IVA_SEDE_LEGALE       VARCHAR2(20),
  CODICE_FISCALE                VARCHAR2(32),
  DENOMINAZIONE                 VARCHAR2(150),
  CAP_SEDE_LEGALE               VARCHAR2(5),
  INDIRIZZO_SEDE_LEGALE         VARCHAR2(255),
  TELEFONO_SEDE_LEGALE          VARCHAR2(20),
  FAX_SEDE_LEGALE               VARCHAR2(20),
  EMAIL_SEDE_LEGALE             VARCHAR2(128),
  COGNOME                       VARCHAR2(150),
  NOME                          VARCHAR2(150),
  CODICE_FISCALE_LR             VARCHAR2(32),
  DT_NASCITA                    DATE,
  CAP_PF                        VARCHAR2(5),
  INDIRIZZO_PF                  VARCHAR2(255),
  FAX_PF                        VARCHAR2(20),
  TELEFONO_PF                   VARCHAR2(20),
  EMAIL_PF                      VARCHAR2(128),
  COD_TIPO_OPERAZIONE           VARCHAR2(3)      NOT NULL,
  COD_ISTAT_COMUNE_SEDE_LEGALE VARCHAR2(8),
  COD_ISTAT_COMUNE_RESIDENZA  VARCHAR2(8)
)
TABLESPACE PBANDI_SMALL_TBL;

COMMENT ON TABLE PBANDI_W_CSP_PROGETTO_SOGGETTO IS 'Tabella di work per il caricamento delle istanze di domanda da SCHEDIN';

--PBANDI_W_CSP_PROG_SEDE_INTERV
CREATE TABLE PBANDI_W_CSP_PROG_SEDE_INTERV
(
  ID_ISTANZA_DOMANDA       NUMBER          NOT NULL,
  CAP                      VARCHAR2(5),
  INDIRIZZO                VARCHAR2(255),
  COD_ISTAT_NAZIONE        VARCHAR2(6),
  COD_ISTAT_REGIONE        VARCHAR2(6),
  COD_ISTAT_PROVINCIA      VARCHAR2(4),
  COD_ISTAT_COMUNE         VARCHAR2(8),
  COD_ISTAT_COMUNE_ESTERO  VARCHAR2(8)
)
TABLESPACE PBANDI_SMALL_TBL;

COMMENT ON TABLE PBANDI_W_CSP_PROG_SEDE_INTERV IS 'Tabella di work per il caricamento delle istanze di domanda - sedi intervento da SCHEDIN';

--PBANDI_R_BANDO_LINEA_INTERVENT
ALTER TABLE PBANDI_R_BANDO_LINEA_INTERVENT
 ADD (FLAG_SCHEDIN  VARCHAR2(1));
 
ALTER TABLE PBANDI_R_BANDO_LINEA_INTERVENT
 ADD CONSTRAINT CK_PBANDI_R_BAN_LIN_INT_01
  CHECK (FLAG_SCHEDIN='S');

COMMENT ON COLUMN PBANDI_R_BANDO_LINEA_INTERVENT.FLAG_SCHEDIN IS 'S=Determina se il bando linea intervento deve essere passato all''applicazione SCHEDIN per essere associata all''istanza di domanda';


-- PBANDI_V_GRCH_LINEA_INTERVENTO
CREATE OR REPLACE VIEW PBANDI_V_GRCH_LINEA_INTERVENTO
(
   LIVELLO,
   ID_LINEA_DI_INTERVENTO_PADRE,
   ID_LINEA_DI_INTERVENTO,
   NOME_LINEA,
   DESC_LINEA,
   PROGR_BANDO_LINEA_INTERVENTO,
   NOME_BANDO_LINEA,
   FLAG_SCHEDIN
)
AS
   WITH alb_linea
        AS (    SELECT LEVEL livello,
                       id_linea_di_intervento,
                       id_linea_di_intervento_padre,
                       desc_breve_linea nome_linea,
                       desc_linea
                  FROM (SELECT id_linea_di_intervento,
                               id_linea_di_intervento_padre,
                               desc_breve_linea,
                               desc_linea
                          FROM PBANDI_D_LINEA_DI_INTERVENTO
                        UNION
                        SELECT (progr_bando_linea_intervento + 1000) --Si genera un id_linea_di_intervento fittizio che non esiste su PBANDI_D_LINEA_DI_INTERVENTO
                                  id_linea_di_intervento,
                               id_linea_di_intervento
                                  id_linea_di_intervento_padre,
                               '*BL*',
                               NULL
                          FROM PBANDI_R_BANDO_LINEA_INTERVENT)
            CONNECT BY PRIOR id_linea_di_intervento =
                          id_linea_di_intervento_padre
            START WITH id_linea_di_intervento_padre IS NULL)
     SELECT DISTINCT
            cast(livello as VARCHAR2(10)) livello,
            cast(b.id_linea_di_intervento_padre as VARCHAR2(10)) id_linea_di_intervento_padre,
            DECODE (b.nome_linea, '*BL*', NULL, b.id_linea_di_intervento)
               id_linea_di_intervento,
            DECODE (b.nome_linea, '*BL*', NULL, b.nome_linea) nome_linea,
            b.desc_linea,
            cast(progr_bando_linea_intervento as VARCHAR2(10)) progr_bando_linea_intervento ,
            nome_bando_linea,
			flag_schedin
       FROM PBANDI_R_BANDO_LINEA_INTERVENT a, alb_linea b
      WHERE a.id_linea_di_intervento(+) = b.id_linea_di_intervento_padre
   ORDER BY id_linea_di_intervento_padre NULLS FIRST;
   
COMMENT ON TABLE PBANDI_V_GRCH_LINEA_INTERVENTO IS 'Vista che descrive la gerarchia delle linee di intervento';


CREATE OR REPLACE  VIEW PBANDI_V_DOC_INDEX
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
            UNION 
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
        AS (SELECT   ts1.*, tec.id_ente_competenza, rsta.id_tipo_anagrafica
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
        AS (SELECT DISTINCT
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
            UNION 
            SELECT  DISTINCT
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
             WHERE tsogg.id_soggetto = rsp.id_soggetto
                   AND NVL (TRUNC (rsp.dt_fine_validita),
                            TRUNC (SYSDATE + 1)) > TRUNC (SYSDATE)
                   AND tp.id_progetto = rsp.id_progetto),
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
                   ta.desc_breve_tipo_anagrafica
              FROM PBANDI_R_DOC_INDEX_TIPO_ANAG rdita,
                   PBANDI_D_TIPO_ANAGRAFICA ta,
                   PBANDI_C_TIPO_DOCUMENTO_INDEX tdi
             WHERE ta.id_tipo_anagrafica = rdita.id_tipo_anagrafica
                   AND tdi.id_tipo_documento_index =
                          rdita.id_tipo_documento_index),
        docindex_ente_soggetto
        AS (SELECT  rtdib.id_tipo_documento_index,
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
        AS (SELECT    DISTINCT
                   docindex_anagrafica.*,
                   docindex_ente_soggetto.id_ente_competenza,
                   docindex_ente_soggetto.id_soggetto
              FROM docindex_anagrafica, docindex_ente_soggetto
             WHERE docindex_anagrafica.id_tipo_documento_index = docindex_ente_soggetto.id_tipo_documento_index)
     -- MAIN
   SELECT /*+NO_QUERY_TRANSFORMATION */   DISTINCT di.*,
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
                      AS codice_fiscale_beneficiario
              FROM spb, tdis, PBANDI_T_DOCUMENTO_INDEX di
             WHERE di.id_progetto = spb.id_progetto
                   AND di.id_tipo_documento_index =
                          tdis.id_tipo_documento_index
                   AND tdis.id_tipo_anagrafica = spb.id_tipo_anagrafica
                   AND tdis.id_soggetto = spb.id_soggetto;

COMMENT ON TABLE PBANDI_V_DOC_INDEX IS 'Vista che estrae i dati dei documenti su INDEX';


				   