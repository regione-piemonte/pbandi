/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

--INDICI PBANDI_R_BANDO_LINEA_ENTE_COMP

CREATE INDEX IE1_PBANDI_R_BAN_LIN_ENT_COMP ON PBANDI_R_BANDO_LINEA_ENTE_COMP
(ID_RUOLO_ENTE_COMPETENZA)
TABLESPACE PBANDI_SMALL_IDX;


CREATE INDEX IE2_PBANDI_R_BAN_LIN_ENT_COMP ON PBANDI_R_BANDO_LINEA_ENTE_COMP
(ID_ENTE_COMPETENZA)
TABLESPACE PBANDI_SMALL_IDX;


CREATE INDEX IE3_PBANDI_R_BAN_LIN_ENT_COMP ON PBANDI_R_BANDO_LINEA_ENTE_COMP
(PROGR_BANDO_LINEA_INTERVENTO)
TABLESPACE PBANDI_SMALL_IDX;

--PBANDI_T_PROGETTO
CREATE INDEX IE2_PBANDI_T_PROGETTO ON PBANDI_T_PROGETTO
(ID_DOMANDA)
TABLESPACE PBANDI_MEDIUM_IDX;

--PBANDI_R_SOGGETTO_PROGETTO
ALTER TABLE PBANDI_R_SOGGETTO_PROGETTO ADD (DT_AGGIORNAMENTO_FLUX  DATE);

--PBANDI_R_SOGG_TIPO_ANAGRAFICA
ALTER TABLE PBANDI_R_SOGG_TIPO_ANAGRAFICA ADD (DT_AGGIORNAMENTO_FLUX  DATE);

--PBANDI_V_SOGGETTO_PROGETTO
CREATE OR REPLACE VIEW PBANDI_V_SOGGETTO_PROGETTO
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
   TITOLO_PROGETTO
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
          m.TITOLO_PROGETTO
     FROM pbandi_d_tipo_anagrafica dta,
          (SELECT --DISTINCT
		          tp.id_progetto,
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
                  tp.titolo_progetto
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
		   --UNION
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
                  tp.titolo_progetto
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


--PBANDI_V_SOGG_PROG_DEFLUX	
CREATE OR REPLACE  VIEW PBANDI_V_SOGG_PROG_DEFLUX
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
   TITOLO_PROGETTO
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
          m.TITOLO_PROGETTO
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
                  tp.titolo_progetto
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
                 -- (SELECT td.progr_bando_linea_intervento, tp.*
                 --    FROM pbandi_t_domanda td, pbandi_t_progetto tp
                 --   WHERE td.id_domanda = tp.id_domanda) tp,
				  pbandi_t_domanda td,
				  pbandi_t_progetto tp,
                  pbandi_r_bando_linea_intervent rbli,
                  pbandi_r_bando_linea_ente_comp rble
            WHERE rbli.progr_bando_linea_intervento =
                     td.progr_bando_linea_intervento
				  AND td.id_domanda = tp.id_domanda
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
                  tp.titolo_progetto
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

	
-- 1 Vista candidati flux non trasversali
CREATE OR REPLACE VIEW PBANDI_V_CAND_FLUX_NOTRASV
(
   CODICE_RUOLO,
   ID_SOGGETTO,
   ID_PROGETTO,
   PROGR_SOGGETTO_PROGETTO,
   ID_ISTANZA_PROCESSO,
   ID_TIPO_ANAGRAFICA,
   DT_FINE_VALIDITA
)
AS
   SELECT DISTINCT cod_utente codice_ruolo,
                   id_soggetto,
                   id_progetto,
                   progr_soggetto_progetto,
                   id_istanza_processo,
                   id_tipo_anagrafica,
				   dt_fine_validita
     FROM PBANDI_V_PROGETTI_FLUX
    WHERE     flag_aggiornato_flux = 'N'
          AND PROGR_SOGGETTO_PROGETTO IS NOT NULL -- Non trasversali
          AND id_istanza_processo IS NOT NULL;
		  
-- 2 Vista candidati flux trasversali
CREATE OR REPLACE FORCE VIEW PBANDI_V_CAND_FLUX_TRASV
(
   CODICE_RUOLO,
   ID_SOGGETTO,
   ID_PROGETTO,
   ID_ISTANZA_PROCESSO,
   ID_TIPO_ANAGRAFICA,
   DT_FINE_VALIDITA
)
AS
   SELECT DISTINCT cod_utente codice_ruolo,
                   id_soggetto,
                   id_progetto,
                   id_istanza_processo,
                   id_tipo_anagrafica,
				   dt_fine_validita
     FROM PBANDI_V_PROGETTI_FLUX
    WHERE     flag_aggiornato_flux = 'N'
          AND PROGR_SOGGETTO_PROGETTO IS NULL -- Trasversali
          AND id_istanza_processo IS NOT NULL;
		  
-- 3 Vista candidati flux trasversali limitata

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
          (SELECT id_istanza_processo
             FROM (--Sono selezionati in modo distinti i soggetti/ruoli per essere poi limitati 
					SELECT DISTINCT id_istanza_processo
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
    WHERE a.id_istanza_processo = b.id_istanza_processo;
		  
-- 4 Vista candidati flux non trasversali limitata
CREATE OR REPLACE VIEW PBANDI_V_CAND_FLUX_NOTRASV_LIM
(
   CODICE_RUOLO,
   ID_SOGGETTO,
   ID_PROGETTO,
   PROGR_SOGGETTO_PROGETTO,
   ID_ISTANZA_PROCESSO,
   ID_TIPO_ANAGRAFICA,
   DT_FINE_VALIDITA
)
AS
   WITH F_SOGG_PROG_FLUX_L
        AS                                                         -- Limitato
          (SELECT id_soggetto
             FROM (--Sono selezionati in modo distinti i soggetti per essere poi limitati
			      SELECT DISTINCT id_soggetto
                   FROM PBANDI_V_PROGETTI_FLUX
                   WHERE flag_aggiornato_flux = 'N'
                     AND PROGR_SOGGETTO_PROGETTO IS NOT NULL
                     AND id_istanza_processo IS NOT NULL)
            WHERE ROWNUM <=
                     (SELECT valore
                        FROM PBANDI_C_COSTANTI
                       WHERE attributo = 'conf.maxNumCandidatiBatchFluxNoTrasv'))
   SELECT          codice_ruolo,
                   a.id_soggetto,
                   a.id_progetto,
                   progr_soggetto_progetto,
                   id_istanza_processo,
                   id_tipo_anagrafica,
		           dt_fine_validita
     FROM PBANDI_V_CAND_FLUX_NOTRASV a, F_SOGG_PROG_FLUX_L b
    WHERE a.id_soggetto = b.id_soggetto;

