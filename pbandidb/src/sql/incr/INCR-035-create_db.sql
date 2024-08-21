/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

-- CERTIFICAZIONE
-- PBANDI_T_DETT_PROP_CERT_ANNUAL
-- MEMO attenzione al package certificazione --  
ALTER TABLE PBANDI_T_DETT_PROP_CERT_ANNUAL ADD DATA_AGG DATE;
ALTER TABLE PBANDI_T_DETT_PROP_CERT_ANNUAL ADD ID_UTENTE_AGG NUMBER(8);
--
-- OBJ TYPE 
DROP TYPE LISTPROPCERTANNUAL;
DROP TYPE OBJPROPCERTANNUAL;

CREATE OR REPLACE TYPE OBJPROPCERTANNUAL AS OBJECT
(idProgetto                      NUMBER(8),
 IdDettPropostaCertif NUMBER (8),  
 Importo_Recuperi                NUMBER(17,2),
 Importo_Revoche                 NUMBER(17,2),
 Importo_Soppressioni            NUMBER(17,2)
)
/
CREATE OR REPLACE TYPE PBANDI.LISTPROPCERTANNUAL AS TABLE OF OBJPROPCERTANNUAL
/
--
ALTER TABLE PBANDI_T_PROPOSTA_CERTIFICAZ ADD ID_PROP_CERT_RIF_AC NUMBER(8);             
ALTER TABLE PBANDI_T_DETT_PROPOSTA_CERTIF ADD IMPORTO_CERT_NET_ANNO_PREC NUMBER(13,2);
ALTER TABLE PBANDI_T_DETT_PROPOSTA_CERTIF ADD IMPORTO_CERT_NET_ANNO_IN_CORSO NUMBER(13,2);
--
ALTER TABLE PBANDI_T_PROPOSTA_CERTIFICAZ ADD
CONSTRAINT FK_PBANDI_T_PROPOSTA_CERTIF_08
FOREIGN KEY (ID_PROP_CERT_RIF_AC) 
REFERENCES PBANDI_T_PROPOSTA_CERTIFICAZ (ID_PROPOSTA_CERTIFICAZ);
--
-- GESTIONE DOCUMENTI
ALTER TABLE PBANDI_C_TIPO_DOCUMENTO_INDEX ADD FLAG_UPLOADABLE VARCHAR2(1);
ALTER TABLE PBANDI_D_TIPO_ANAGRAFICA ADD ID_CATEG_ANAGRAFICA NUMBER(3);
ALTER TABLE PBANDI_T_DOCUMENTO_INDEX ADD ID_CATEG_ANAGRAFICA_MITT NUMBER(3);
--
CREATE TABLE PBANDI_D_CATEG_ANAGRAFICA
(ID_CATEG_ANAGRAFICA NUMBER(3),
 DESC_CATEG_ANAGRAFICA VARCHAR2(1000)
);

ALTER TABLE PBANDI_D_CATEG_ANAGRAFICA ADD 
(CONSTRAINT PK_PBANDI_D_CATEG_ANAGRAFICA PRIMARY KEY (ID_CATEG_ANAGRAFICA)
 USING INDEX  TABLESPACE PBANDI_IDX);

ALTER TABLE PBANDI_D_TIPO_ANAGRAFICA ADD
CONSTRAINT FK_PBANDI_D_CATEG_ANAG_01
FOREIGN KEY (ID_CATEG_ANAGRAFICA) 
REFERENCES PBANDI_D_CATEG_ANAGRAFICA (ID_CATEG_ANAGRAFICA);
--
CREATE TABLE PBANDI_R_CATEG_ANAG_DOC_INDEX
(ID_CATEG_ANAGRAFICA NUMBER(3),
  ID_DOCUMENTO_INDEX NUMBER(8)
);
ALTER TABLE PBANDI_R_CATEG_ANAG_DOC_INDEX ADD 
(CONSTRAINT PK_PBANDI_R_CATEG_ANAG_DOC_IN PRIMARY KEY (ID_CATEG_ANAGRAFICA,ID_DOCUMENTO_INDEX)
USING INDEX  TABLESPACE PBANDI_IDX);

ALTER TABLE PBANDI_R_CATEG_ANAG_DOC_INDEX ADD
CONSTRAINT FK_PBANDI_T_DOCUMENTO_INDEX_06
FOREIGN KEY (ID_DOCUMENTO_INDEX) 
REFERENCES PBANDI_T_DOCUMENTO_INDEX (ID_DOCUMENTO_INDEX);

ALTER TABLE PBANDI_R_CATEG_ANAG_DOC_INDEX ADD
CONSTRAINT FK_PBANDI_D_CATEG_ANAG_02
FOREIGN KEY (ID_CATEG_ANAGRAFICA) 
REFERENCES PBANDI_D_CATEG_ANAGRAFICA (ID_CATEG_ANAGRAFICA);

ALTER TABLE PBANDI_T_DOCUMENTO_INDEX  ADD
CONSTRAINT FK_PBANDI_D_CATEG_ANAG_03
FOREIGN KEY (ID_CATEG_ANAGRAFICA_MITT) 
REFERENCES PBANDI_D_CATEG_ANAGRAFICA (ID_CATEG_ANAGRAFICA);
--
ALTER TABLE PBANDI_D_IND_RISULTATO_PROGRAM
MODIFY COD_IGRUE_T20 VARCHAR(80);
-- PBANDI_T_DATI_PROGETTO_MONIT
ALTER TABLE PBANDI_T_DATI_PROGETTO_MONIT ADD IMPORTO_ENTRATE NUMBER(17,2);
-- INDEX
CREATE INDEX IE1_PBANDI_D_TIPO_ANAGRAFICA ON PBANDI_D_TIPO_ANAGRAFICA (ID_CATEG_ANAGRAFICA) TABLESPACE PBANDI_IDX;
CREATE INDEX IE1_PBANDI_T_DOCUMENTO_INDEX ON PBANDI_T_DOCUMENTO_INDEX (ID_CATEG_ANAGRAFICA_MITT) TABLESPACE PBANDI_IDX;
CREATE INDEX IE1_PBANDI_R_CAT_ANA_DOC_INDEX ON PBANDI_R_CATEG_ANAG_DOC_INDEX (ID_DOCUMENTO_INDEX) TABLESPACE PBANDI_IDX;

-----------------
--VISTE
-----------------
CREATE OR REPLACE FORCE VIEW PBANDI_V_PROGETTI_BANDO
(
   PROGR_BANDO_LINEA_INTERVENTO,
   ID_LINEA_DI_INTERVENTO,
   ID_PROGETTO,
   CODICE_VISUALIZZATO,
   DT_AGGIORNAMENTO,
   ID_DOMANDA,
   NUMERO_DOMANDA
)
AS
   SELECT c.progr_bando_linea_intervento,
          c.id_linea_di_intervento,
          a.id_progetto,
          a.codice_visualizzato,
          a.dt_aggiornamento,
          b.id_domanda,
          b.numero_domanda
     FROM PBANDI_T_PROGETTO a,
          PBANDI_T_DOMANDA b,
          PBANDI_R_BANDO_LINEA_INTERVENT c
    WHERE b.id_domanda = a.id_domanda
          AND c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;

	  
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
		  SF_ENTE_GIURIDICO AS  -- Dati beneficiario
			  (SELECT                                         /*+MATERIALIZE */ 
                a.id_ente_giuridico, 
                b.DENOMINAZIONE_ENTE_GIURIDICO, 
                b.id_soggetto,
                d.codice_fiscale_soggetto
                FROM
                (SELECT  max(ID_ENTE_GIURIDICO) id_ente_giuridico
                   FROM PBANDI_T_ENTE_GIURIDICO a
                  WHERE dt_fine_validita IS NULL
                  group by id_soggetto) a,
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
	WHERE di.ID_CATEG_ANAGRAFICA_MITT = ca.ID_CATEG_ANAGRAFICA
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
--
-- 	PBANDI_V_CERTIF_CUM_ANNI_PREC
	CREATE OR REPLACE FORCE VIEW PBANDI.PBANDI_V_CERTIF_CUM_ANNI_PREC
(
   ID_PROGETTO,
   IMPORTO_REVOCHE,
   IMPORTO_RECUPERI,
   IMPORTO_SOPPRESSIONI,
   IMPORTO_EROGAZIONI,
   IMPORTO_PAGAMENTI_VALIDATI,
   IMPORTO_CERTIFICAZIONE_NETTO
)
AS
     SELECT ID_PROGETTO,
            SUM (NVL (pca.IMPORTO_REVOCHE, 0)),
            SUM (NVL (pca.IMPORTO_RECUPERI, 0)),
            SUM (NVL (pca.IMPORTO_SOPPRESSIONI, 0)),
            SUM (NVL (pca.IMPORTO_EROGAZIONI, 0)),
            SUM (NVL (pca.IMPORTO_PAGAMENTI_VALIDATI, 0)),
            SUM (NVL (pca.IMPORTO_CERTIFICAZIONE_NETTO, 0))
       FROM PBANDI_T_DETT_PROP_CERT_ANNUAL pca,
            PBANDI_T_PROPOSTA_CERTIFICAZ pc,
            PBANDI_T_DETT_PROPOSTA_CERTIF dpc
      WHERE     pca.ID_PROPOSTA_CERTIFICAZ = pc.ID_PROPOSTA_CERTIFICAZ
            AND dpc.ID_DETT_PROPOSTA_CERTIF = pca.ID_DETT_PROPOSTA_CERTIF
            AND pc.ID_PERIODO IS NOT NULL
            AND pc.ID_STATO_PROPOSTA_CERTIF IN (16, 17)
   GROUP BY ID_PROGETTO;
-- 	   
