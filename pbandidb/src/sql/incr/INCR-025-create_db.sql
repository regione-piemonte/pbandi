/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

-- LISTTASKPROG Array di Task per progetto
DROP TYPE LISTTASKPROG
/

-- Creazione TIPI
--LISTTASKPROG Oggetto Task per progetto
CREATE OR REPLACE TYPE OBJTASKPROG AS OBJECT
(ID_PROGETTO NUMBER(8),
 TITOLO_PROGETTO  VARCHAR2(255),
 CODICE_VISUALIZZATO  VARCHAR2(100),
 DESCR_BREVE_TASK VARCHAR2(100),
 DESCR_TASK  VARCHAR2(2000),
 PROGR_BANDO_LINEA_INTERVENTO  NUMBER(8),
 NOME_BANDO_LINEA VARCHAR2(255),
 FLAG_OPT VARCHAR2(1),
 FLAG_LOCK VARCHAR2(1),
 ACRONIMO_PROGETTO VARCHAR2(20),
 ID_BUSINESS NUMBER,
 ID_NOTIFICA NUMBER,
 DENOMINAZIONE_LOCK  VARCHAR2(1000)
 )
/

-- LISTTASKPROG Array di Task per progetto
CREATE OR REPLACE
TYPE LISTTASKPROG AS TABLE OF OBJTASKPROG
/

--PBANDI_T_NOTIFICA_PROCESSO
ALTER TABLE PBANDI_T_NOTIFICA_PROCESSO
 ADD (ID_TEMPLATE_NOTIFICA  INTEGER);
 
ALTER TABLE PBANDI_T_NOTIFICA_PROCESSO ADD 
CONSTRAINT FK_PBANDI_T_TEPLATE_NOTIFIC_01
 FOREIGN KEY (ID_TEMPLATE_NOTIFICA)
 REFERENCES PBANDI_D_TEMPLATE_NOTIFICA (ID_TEMPLATE_NOTIFICA);
 
CREATE INDEX IE5_PBANDI_T_NOTIFICA_PROCESSO ON PBANDI_T_NOTIFICA_PROCESSO
(ID_TEMPLATE_NOTIFICA)
TABLESPACE PBANDI_MEDIUM_IDX;

--PBANDI_D_SETTORE_ENTE
ALTER TABLE PBANDI_D_SETTORE_ENTE
 ADD (ID_INDIRIZZO  NUMBER(9));

ALTER TABLE PBANDI_D_SETTORE_ENTE ADD 
CONSTRAINT FK_PBANDI_T_INDIRIZZO_15
 FOREIGN KEY (ID_INDIRIZZO)
 REFERENCES PBANDI_T_INDIRIZZO (ID_INDIRIZZO);

CREATE INDEX IE1_PBANDI_D_SETTORE_ENTE ON PBANDI_D_SETTORE_ENTE
(ID_INDIRIZZO)
TABLESPACE PBANDI_SMALL_IDX;

---
--PBANDI_R_BANDO_LINEA_SETTORE
CREATE TABLE PBANDI_R_BANDO_LINEA_SETTORE
(
  PROGR_BANDO_LINEA_SETTORE     NUMBER(8)       NOT NULL,
  PROGR_BANDO_LINEA_INTERVENTO  NUMBER(8)       NOT NULL,
  ID_RUOLO_ENTE_COMPETENZA      NUMBER(3)       NOT NULL,
  ID_SETTORE_ENTE               NUMBER(3)       NOT NULL,
  DT_FINE_VALIDITA              DATE,
  ID_UTENTE_INS                 NUMBER(8)       NOT NULL,
  ID_UTENTE_AGG                 NUMBER(8)
)
TABLESPACE PBANDI_SMALL_TBL;


CREATE INDEX IE1_PBANDI_R_BANDO_LIN_SETTORE ON PBANDI_R_BANDO_LINEA_SETTORE
(ID_RUOLO_ENTE_COMPETENZA)
TABLESPACE PBANDI_SMALL_IDX;


CREATE INDEX IE2_PBANDI_R_BANDO_LIN_SETTORE ON PBANDI_R_BANDO_LINEA_SETTORE
(ID_SETTORE_ENTE)
TABLESPACE PBANDI_SMALL_IDX;


ALTER TABLE PBANDI_R_BANDO_LINEA_SETTORE ADD (
  CONSTRAINT PK_PBANDI_R_BANDO_LIN_SETTORE
  PRIMARY KEY
  (PROGR_BANDO_LINEA_SETTORE)
   USING INDEX TABLESPACE PBANDI_SMALL_IDX);
   
ALTER TABLE PBANDI_R_BANDO_LINEA_SETTORE ADD (
  CONSTRAINT AK1_PBANDI_R_BANDO_LIN_SETTORE
  UNIQUE
  (PROGR_BANDO_LINEA_INTERVENTO,
   ID_RUOLO_ENTE_COMPETENZA,
   ID_SETTORE_ENTE)
   USING INDEX TABLESPACE PBANDI_SMALL_IDX);
   
ALTER TABLE PBANDI_R_BANDO_LINEA_SETTORE ADD (
  CONSTRAINT FK_PBANDI_D_RUOLO_ENTE_COMP_05 
  FOREIGN KEY (ID_RUOLO_ENTE_COMPETENZA) 
  REFERENCES PBANDI_D_RUOLO_ENTE_COMPETENZA (ID_RUOLO_ENTE_COMPETENZA),
  CONSTRAINT FK_PBANDI_R_BANDO_LINEA_INT_20 
  FOREIGN KEY (PROGR_BANDO_LINEA_INTERVENTO) 
  REFERENCES PBANDI_R_BANDO_LINEA_INTERVENT (PROGR_BANDO_LINEA_INTERVENTO),
  CONSTRAINT FK_PBANDI_D_SETTORE_ENTE_02 
  FOREIGN KEY (ID_SETTORE_ENTE) 
  REFERENCES PBANDI_D_SETTORE_ENTE (ID_SETTORE_ENTE),
  CONSTRAINT FK_PBANDI_T_UTENTE_261 
  FOREIGN KEY (ID_UTENTE_INS) 
  REFERENCES PBANDI_T_UTENTE (ID_UTENTE),
  CONSTRAINT FK_PBANDI_T_UTENTE_262 
  FOREIGN KEY (ID_UTENTE_AGG) 
  REFERENCES PBANDI_T_UTENTE (ID_UTENTE)
  ON DELETE SET NULL);

--PBANDI_R_BANDO_LINEA_ENTE_COMP
DROP INDEX IE3_PBANDI_R_BAN_LIN_ENT_COMP;

ALTER TABLE PBANDI_R_BANDO_LINEA_ENTE_COMP ADD (
  CONSTRAINT AK1_PBANDI_R_BANDO_LIN_EN_COMP
  UNIQUE
  (PROGR_BANDO_LINEA_INTERVENTO,
   ID_RUOLO_ENTE_COMPETENZA,
   ID_ENTE_COMPETENZA)
   USING INDEX TABLESPACE PBANDI_SMALL_IDX);

CREATE SEQUENCE SEQ_PBANDI_R_BANDO_LIN_SETT
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;

CREATE OR REPLACE TRIGGER TG_PBANDI_R_BANDO_LIN_SET_BIU
   before insert or update of id_settore_ente on PBANDI_R_BANDO_LINEA_SETTORE
   referencing old as OLD new as NEW
   for each row

DECLARE

    PRAGMA AUTONOMOUS_TRANSACTION;

    v_id_settore_ente PBANDI_D_SETTORE_ENTE.id_settore_ente%TYPE;

   Cursor c1 (c_progr_bando_linea_intervento NUMBER,
              c_id_ruolo_ente_competenza NUMBER,
			  c_id_settore_ente NUMBER) is
    SELECT /*+FIRST_ROWS */ b1.id_settore_ente
      FROM PBANDI_R_BANDO_LINEA_SETTORE a,
           PBANDI_D_SETTORE_ENTE b,
           PBANDI_T_ENTE_COMPETENZA c,
           PBANDI_D_SETTORE_ENTE b1
      WHERE a.progr_bando_linea_intervento = c_progr_bando_linea_intervento
      AND a.id_ruolo_ente_competenza = c_id_ruolo_ente_competenza
      AND b1.id_settore_ente = c_id_settore_ente
      AND a.id_settore_ente = b.id_settore_ente
      AND c.id_ente_competenza = b.id_ente_competenza
      AND b1.id_ente_competenza = c.id_ente_competenza;   
 


BEGIN
/*
  Controllo che non sia gia presente un'associazione 
   PROGR_BANDO_LINEA_INTERVENTO,
   ID_RUOLO_ENTE_COMPETENZA,
   con un ID_SETTORE_ENTE appartenente a  stesso ID_ENTE_COMPETENZA
*/

   OPEN c1 (:new.progr_bando_linea_intervento,
            :new.id_ruolo_ente_competenza,
			:new.id_settore_ente);
   FETCH c1 INTO v_id_settore_ente;
   IF c1%FOUND THEN
      RAISE_APPLICATION_ERROR(-20000,'Esiste associazione bandolinea-ruolo con settore appartenente allo stesso Ente di competenza '||v_id_settore_ente);
   END IF;
   CLOSE c1;

END;
/
  
--PBANDI_V_BANDO_LINEA_ENTE_COMP
CREATE OR REPLACE  VIEW PBANDI_V_BANDO_LINEA_ENTE_COMP
(  PROGR_BANDO_LINEA_ENTE_COMP,
   ID_RUOLO_ENTE_COMPETENZA,
   ID_ENTE_COMPETENZA,
   PROGR_BANDO_LINEA_INTERVENTO,
   ID_SETTORE_ENTE,
   ID_INDIRIZZO,
   DT_FINE_VALIDITA
)
AS
   SELECT PROGR_BANDO_LINEA_ENTE_COMP,
          ID_RUOLO_ENTE_COMPETENZA,
          ID_ENTE_COMPETENZA,
          PROGR_BANDO_LINEA_INTERVENTO,
          ID_SETTORE_ENTE,
          NVL (b1.ID_INDIRIZZO, a1.ID_INDIRIZZO) ID_INDIRIZZO,
          a.DT_FINE_VALIDITA
     FROM    (   PBANDI_R_BANDO_LINEA_ENTE_COMP a
              JOIN
                 PBANDI_T_ENTE_COMPETENZA a1
              USING (ID_ENTE_COMPETENZA))
          LEFT JOIN
             (   PBANDI_R_BANDO_LINEA_SETTORE b
              JOIN
                 PBANDI_D_SETTORE_ENTE b1
              USING (ID_SETTORE_ENTE))
          USING (PROGR_BANDO_LINEA_INTERVENTO,
                 ID_RUOLO_ENTE_COMPETENZA,
                 ID_ENTE_COMPETENZA);
--
CREATE OR REPLACE FORCE VIEW PBANDI_V_PROCESSO_BEN_BL
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
   SELECT m.codice_fiscale_soggetto,
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
             ben.progr_bando_linea_intervento)
             processo
     FROM (SELECT rsp.id_soggetto id_soggetto_beneficiario,
                  codice_fiscale_beneficiario,
                  denominazione_beneficiario,
                  rsp.id_progetto,
                  dom.progr_bando_linea_intervento,
                  bl.nome_bando_linea
             FROM pbandi_r_soggetto_progetto rsp,
                  (SELECT soggetto.id_soggetto,
                          soggetto.codice_fiscale_soggetto
                             codice_fiscale_beneficiario,
                          NVL (
                             eg.denominazione_ente_giuridico,
                             NVL2 (pf.denominazione_persona_fisica, ' ', ''))
                             AS denominazione_beneficiario
                     FROM (SELECT pbandi_t_soggetto.id_soggetto,
                                  pbandi_t_soggetto.codice_fiscale_soggetto,
                                  pbandi_d_tipo_soggetto.desc_breve_tipo_soggetto
                             FROM pbandi_t_soggetto, pbandi_d_tipo_soggetto
                            WHERE NVL (
                                     TRUNC (
                                        pbandi_d_tipo_soggetto.dt_fine_validita),
                                     TRUNC (SYSDATE + 1)) > TRUNC (SYSDATE)
                                  AND pbandi_d_tipo_soggetto.id_tipo_soggetto =
                                         pbandi_t_soggetto.id_tipo_soggetto) soggetto,
                          (SELECT DISTINCT
                                  rsp.id_soggetto,
                                  FIRST_VALUE (
                                     teg.denominazione_ente_giuridico)
                                  OVER (
                                     PARTITION BY teg.id_soggetto
                                     ORDER BY
                                        teg.dt_inizio_validita DESC,
                                        teg.id_ente_giuridico DESC)
                                     denominazione_ente_giuridico
                             FROM pbandi_t_ente_giuridico teg,
                                  pbandi_r_soggetto_progetto rsp
                            WHERE rsp.id_soggetto = teg.id_soggetto
                                  AND rsp.id_ente_giuridico =
                                         teg.id_ente_giuridico
                                  AND rsp.ID_TIPO_ANAGRAFICA =
                                         (SELECT dta.id_tipo_anagrafica
                                            FROM pbandi_d_tipo_anagrafica dta
                                           WHERE dta.desc_breve_tipo_anagrafica =
                                                    'BENEFICIARIO')
                                  AND NVL (rsp.id_tipo_beneficiario, '-1') <>
                                         (SELECT dtb.id_tipo_beneficiario
                                            FROM pbandi_d_tipo_beneficiario dtb
                                           WHERE dtb.desc_breve_tipo_beneficiario =
                                                    'BEN-ASSOCIATO')
                                  AND NVL (TRUNC (rsp.dt_fine_validita),
                                           TRUNC (SYSDATE + 1)) >
                                         TRUNC (SYSDATE)) eg,
                          (SELECT DISTINCT
                                  rsp.id_soggetto,
                                  FIRST_VALUE (
                                     tpf.cognome || ' ' || tpf.nome)
                                  OVER (
                                     PARTITION BY tpf.id_soggetto
                                     ORDER BY
                                        tpf.dt_inizio_validita DESC,
                                        tpf.id_persona_fisica DESC)
                                     denominazione_persona_fisica
                             FROM pbandi_t_persona_fisica tpf,
                                  pbandi_r_soggetto_progetto rsp
                            WHERE rsp.id_soggetto = tpf.id_soggetto
                                  AND rsp.id_persona_fisica =
                                         tpf.id_persona_fisica
                                  AND rsp.ID_TIPO_ANAGRAFICA =
                                         (SELECT dta.id_tipo_anagrafica
                                            FROM pbandi_d_tipo_anagrafica dta
                                           WHERE dta.desc_breve_tipo_anagrafica =
                                                    'BENEFICIARIO')
                                  AND NVL (rsp.id_tipo_beneficiario, '-1') <>
                                         (SELECT dtb.id_tipo_beneficiario
                                            FROM pbandi_d_tipo_beneficiario dtb
                                           WHERE dtb.desc_breve_tipo_beneficiario =
                                                    'BEN-ASSOCIATO')
                                  AND NVL (TRUNC (rsp.dt_fine_validita),
                                           TRUNC (SYSDATE + 1)) >
                                         TRUNC (SYSDATE)) pf
                    WHERE soggetto.id_soggetto = eg.id_soggetto(+)
                          AND soggetto.id_soggetto = pf.id_soggetto(+)) benInfo,
                  pbandi_t_progetto prog,
                  pbandi_t_domanda dom,
                  pbandi_r_bando_linea_intervent bl
            WHERE rsp.id_soggetto = benInfo.id_soggetto
                  AND rsp.ID_TIPO_ANAGRAFICA =
                         (SELECT dta.id_tipo_anagrafica
                            FROM pbandi_d_tipo_anagrafica dta
                           WHERE dta.desc_breve_tipo_anagrafica =
                                    'BENEFICIARIO')
                  AND NVL (rsp.id_tipo_beneficiario, '-1') <>
                         (SELECT dtb.id_tipo_beneficiario
                            FROM pbandi_d_tipo_beneficiario dtb
                           WHERE dtb.desc_breve_tipo_beneficiario =
                                    'BEN-ASSOCIATO')
                  AND rsp.id_progetto = prog.id_progetto
                  AND bl.progr_bando_linea_intervento =
                         dom.progr_bando_linea_intervento
                  AND prog.id_domanda = dom.id_domanda
                  AND NVL (TRUNC (rsp.dt_fine_validita), TRUNC (SYSDATE + 1)) >
                         TRUNC (SYSDATE)) ben,
          (SELECT                                                  -- DISTINCT
                 tp.id_progetto,
                  ts.codice_fiscale_soggetto,
                  ts.id_soggetto,
                  ts.id_tipo_anagrafica,
                  NULL progr_soggetto_progetto
             FROM (SELECT ts1.*,
                          tec.id_ente_competenza,
                          rsta.id_tipo_anagrafica
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
                                            WHERE recs.id_soggetto =
                                                     ts1.id_soggetto)
                                   OR EXISTS
                                         (SELECT 'x'
                                            FROM pbandi_r_ente_competenza_sogg recs
                                           WHERE recs.id_soggetto =
                                                    ts1.id_soggetto
                                                 AND recs.id_ente_competenza =
                                                        tec.id_ente_competenza)))) ts,
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
                  AND ts.id_ente_competenza = rble.id_ente_competenza
           UNION ALL
           SELECT rsp.id_progetto id_progetto,
                  ts.codice_fiscale_soggetto,
                  ts.id_soggetto,
                  rsp.id_tipo_anagrafica,
                  rsp.progr_soggetto_progetto
             FROM PBANDI_T_SOGGETTO ts,
                  pbandi_r_soggetto_progetto rsp,
                  pbandi_t_progetto tp
            WHERE ts.id_soggetto = rsp.id_soggetto
                  AND NVL (TRUNC (rsp.dt_fine_validita), TRUNC (SYSDATE + 1)) >
                         TRUNC (SYSDATE)
                  AND tp.id_progetto = rsp.id_progetto) m,
          pbandi_d_tipo_anagrafica dta
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
                                      TRUNC (SYSDATE + 1)) > TRUNC (SYSDATE)
                             AND rspsc.progr_soggetto_progetto =
                                    m.progr_soggetto_progetto
                             AND rsc.id_tipo_soggetto_correlato IN
                                    (SELECT rrta.id_tipo_soggetto_correlato
                                       FROM pbandi_r_ruolo_tipo_anagrafica rrta
                                      WHERE rrta.id_tipo_anagrafica =
                                               m.id_tipo_anagrafica)));

--   
CREATE OR REPLACE VIEW PBANDI_V_CAND_FLUX_TRASV_LIM
(
   CODICE_RUOLO,
   ID_SOGGETTO,
   ID_PROGETTO,
   ID_ISTANZA_PROCESSO,
   ID_TIPO_ANAGRAFICA,
   DT_FINE_VALIDITA
)
AS
   WITH F_SOGG_TIPO_ANAG_FLUX_L
        AS                                                         -- Limitato
          (SELECT id_soggetto,id_tipo_anagrafica
             FROM (--Sono selezionati in modo distinti i soggetti/ruoli per essere poi limitati 
					SELECT DISTINCT id_soggetto,id_tipo_anagrafica
					  FROM PBANDI_V_PROGETTI_FLUX
					 WHERE     flag_aggiornato_flux = 'N'
					   AND PROGR_SOGGETTO_PROGETTO IS NULL
					   AND id_istanza_processo IS NOT NULL)
            WHERE ROWNUM <=
                     (SELECT valore
                        FROM PBANDI_C_COSTANTI
                       WHERE attributo = 'conf.maxNumCandidatiBatchFluxTrasv'))
   SELECT codice_ruolo,
          a.id_soggetto,
          id_progetto,
          a.id_istanza_processo,
          a.id_tipo_anagrafica,
		  dt_fine_validita
     FROM PBANDI_V_CAND_FLUX_TRASV a, F_SOGG_TIPO_ANAG_FLUX_L b
    WHERE a.id_soggetto = b.id_soggetto
	  AND a.id_tipo_anagrafica = b.id_tipo_anagrafica;