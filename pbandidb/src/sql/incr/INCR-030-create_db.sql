/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

--0
--
---ALTER TABLE PBANDI_T_BANDO ADD NUM_MAX_DOMANDE INTEGER;
---ALTER TABLE PBANDI_T_BANDO ADD FLAG_FINDOM VARCHAR2(1); 
---COMMENT ON COLUMN PBANDI_T_BANDO.FLAG_FINDOM IS 'S=GESTITO SU FINDOM N=NON GESTITO SU FINDOM';

ALTER TABLE PBANDI_R_BANDO_LINEA_ENTE_COMP ADD INDIRIZZO_MAIL_PEC VARCHAR2(100);
ALTER TABLE PBANDI_T_ENTE_COMPETENZA ADD INDIRIZZO_MAIL_PEC VARCHAR2(100);
ALTER TABLE PBANDI_D_SETTORE_ENTE ADD INDIRIZZO_MAIL_PEC VARCHAR2(100);
--


--
--
-- SIF
-- PBANDI_R_BANDO_LINEA_INTERVENT
ALTER TABLE PBANDI_R_BANDO_LINEA_INTERVENT ADD
  (FLAG_SIF VARCHAR2(1), PROGR_BANDO_LINEA_INTERV_SIF NUMBER(8));
  
COMMENT ON COLUMN PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERV_SIF IS 'Bando di tipologia "Strumenti di Ingegneria Finanziaria"';
COMMENT ON COLUMN PBANDI_R_BANDO_LINEA_INTERVENT.FLAG_SIF IS 'S=Determina che il Bando è  di tipologia "Strumenti di Ingegneria Finanziaria"';

CREATE INDEX IE2_PBANDI_R_BANDO_LIN_INTERV ON PBANDI_R_BANDO_LINEA_INTERVENT
(PROGR_BANDO_LINEA_INTERV_SIF)
TABLESPACE PBANDI_IDX;

ALTER TABLE PBANDI_R_BANDO_LINEA_INTERVENT ADD (
  CONSTRAINT CK_PBANDI_R_BAN_LIN_INT_02
  CHECK (FLAG_SIF='S'));

ALTER TABLE PBANDI_R_BANDO_LINEA_INTERVENT ADD (
CONSTRAINT FK_PBANDI_R_BANDO_LINEA_INT_28 
FOREIGN KEY (PROGR_BANDO_LINEA_INTERV_SIF) 
REFERENCES PBANDI_R_BANDO_LINEA_INTERVENT (PROGR_BANDO_LINEA_INTERVENTO));

CREATE OR REPLACE TRIGGER TG_PBANDI_R_BAN_LIN_INTERV_BIU
   before insert or update of PROGR_BANDO_LINEA_INTERV_SIF,FLAG_SIF ON PBANDI_R_BANDO_LINEA_INTERVENT    referencing old as OLD new as NEW
   for each row
DECLARE

    PRAGMA AUTONOMOUS_TRANSACTION;

    v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
	v_flag_sif PBANDI_R_BANDO_LINEA_INTERVENT.flag_sif%TYPE;

   Cursor c1 (c_progr_bando_linea_interv_sif NUMBER) is
    SELECT PROGR_BANDO_LINEA_INTERVENTO
      FROM PBANDI_R_BANDO_LINEA_INTERVENT
      WHERE PROGR_BANDO_LINEA_INTERV_SIF = c_progr_bando_linea_interv_sif;

   Cursor c2 (c_progr_bando_linea_interv_sif NUMBER) is
    SELECT FLAG_SIF
      FROM PBANDI_R_BANDO_LINEA_INTERVENT
      WHERE PROGR_BANDO_LINEA_INTERVENTO = c_progr_bando_linea_interv_sif
	  AND FLAG_SIF = 'S';


BEGIN
/*
  Controllo che non sia già presente un altro record
   con lo stesso Bando Linea SIF
   Non sono considerati i records con Bando Linea SIF non valorizzato
*/
   OPEN c1 (:new.progr_bando_linea_interv_sif);
   FETCH c1 INTO v_progr_bando_linea_intervento;
   IF c1%FOUND THEN
      IF inserting THEN
          RAISE_APPLICATION_ERROR(-20000,'Il Bando linea SIF con numero : '||:new.progr_bando_linea_interv_sif ||' è già presente nel Bando Linea con PROGR:'||v_progr_bando_linea_intervento);
	  ELSE -- Updating
	    IF :new.progr_bando_linea_interv_sif = :old.progr_bando_linea_interv_sif THEN
		   NULL;
		ELSE
          RAISE_APPLICATION_ERROR(-20000,'Il Bando linea SIF con numero : '||:new.progr_bando_linea_interv_sif ||' è già presente nel Bando Linea con PROGR:'||v_progr_bando_linea_intervento);
		END IF;
      END IF;	  
   END IF;
   CLOSE c1;
   
/*
  Controllo che il Bando linea referenziato per il SIF abbia il Flag=S
*/
   IF :new.progr_bando_linea_interv_sif IS NOT NULL THEN 
	   OPEN c2 (:new.progr_bando_linea_interv_sif);
	   FETCH c2 INTO v_flag_sif;
	   IF c2%NOTFOUND THEN
		  RAISE_APPLICATION_ERROR(-20000,'Il Bando linea con numero : '||:new.progr_bando_linea_interv_sif ||' non è un SIF');
	   END IF;
	   CLOSE c2;
   END IF;
   
   -- Controllo che se il FLAG_SIF è assegnato allora il Bando linea SIF deve essere NULL
   IF :NEW.FLAG_SIF = 'S' and :NEW.progr_bando_linea_interv_sif IS NOT NULL 
   THEN
      RAISE_APPLICATION_ERROR(-20000,'Un Bando SIF non può essere referenziato da un Bando SIF');
   END IF;

  

END;
/

-- PBANDI_T_ENTE_GIURIDICO
ALTER TABLE PBANDI_T_ENTE_GIURIDICO ADD
  (FLAG_PUBBLICO_PRIVATO NUMBER(1));
  
COMMENT ON COLUMN PBANDI_T_ENTE_GIURIDICO.FLAG_PUBBLICO_PRIVATO IS '1=Privato, 2=Pubblico';

ALTER TABLE PBANDI_T_ENTE_GIURIDICO ADD (
  CONSTRAINT CK_PBANDI_T_ENTE_GIUR_01
  CHECK (FLAG_PUBBLICO_PRIVATO IN (1,2)));

-- MODIFICHE PER NUOVA CERTIFICAZIONE
ALTER TABLE PBANDI_T_DETT_PROPOSTA_CERTIF
 ADD (IMP_CERTIF_NETTO_PREMODIFICA  NUMBER(17,2),
      CODICE_PROGETTO               VARCHAR2(100),
      DENOMINAZIONE_BENEFICIARIO    VARCHAR2(255));
	  
COMMENT ON COLUMN PBANDI_T_DETT_PROPOSTA_CERTIF.IMP_CERTIF_NETTO_PREMODIFICA IS 'Importo certificazione netto calcolato dalla procedura e non modificabile da on-line';

------------------  
-- VISTE
------------------
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
   DESC_STATO_DOCUMENTO_INDEX
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
                   AND a.id_messaggio_appl = b.id_messaggio(+))
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
          sdi.DESCRIZIONE DESC_STATO_DOCUMENTO_INDEX
     FROM spb,
          tdis,
          PBANDI_T_DOCUMENTO_INDEX di,
          lvf                                        /*Log Validazione Firma*/
             ,
          PBANDI_T_DOC_PROTOCOLLO tdp,
          PBANDI_D_STATO_DOCUMENTO_INDEX sdi
    WHERE     di.id_progetto = spb.id_progetto
          AND di.id_tipo_documento_index = tdis.id_tipo_documento_index
          AND tdis.id_tipo_anagrafica = spb.id_tipo_anagrafica
          AND tdis.id_soggetto = spb.id_soggetto
          AND sdi.ID_STATO_DOCUMENTO(+) = di.ID_STATO_DOCUMENTO
          AND lvf.id_documento_index(+) = di.id_documento_index
          AND tdp.ID_DOCUMENTO_INDEX(+) = di.ID_DOCUMENTO_INDEX;

/* Formatted on 07/11/2016 15:23:51 (QP5 v5.163.1008.3004) */
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
        AS (  SELECT                                         /*+MATERIALIZE */
                    c.ID_DICHIARAZIONE_SPESA ID_TARGET,
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
          di.ID_PROGETTO,
          NOME_FILE,
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
          CASE
             WHEN en.NOME_ENTITA IN
                     ('PBANDI_T_DICHIARAZIONE_SPESA',
                      'PBANDI_T_COMUNICAZ_FINE_PROG')
             THEN
                i.TIPO_INVIO
             ELSE
                NULL                               --Comunicazione di rinuncia
          END
             TIPO_INVIO,
          FLAG_FIRMA_CARTACEA,
          NVL (FLAG_TRASM_DEST, 'N') FLAG_TRASM_DEST,
          --Dichiarazione di spesa(id_target di Id_entita 63 PBANDI_T_DICHIARAZIONE_SPESA)
          --Cumunicazione di fine progetto (id_entita 270  PBANDI_T_COMUNICAZ_FINE_PROG cercare la dichiarazione di spesa di tipo DESC_BREVE_TIPO_DICHIARA_SPESA=FC)
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
          LEFT JOIN SF_INVIO i
             ON (i.ID_TARGET = di.ID_TARGET
                 AND i.ID_PROGETTO = di.ID_PROGETTO)
    WHERE tdi.FLAG_FIRMABILE = 'S'
          AND en.NOME_ENTITA IN
                 ('PBANDI_T_DICHIARAZIONE_SPESA',
                  'PBANDI_T_COMUNICAZ_FINE_PROG',
                  'PBANDI_T_RINUNCIA')
          AND sdi.DESC_BREVE IN ('INVIATO')
          AND NVL (di.FLAG_FIRMA_CARTACEA, 'N') = 'N'
          AND di.DT_MARCA_TEMPORALE IS NOT NULL
          -- AND NVL (di.FLAG_TRASM_DEST, 'N') = 'N'
          AND rec.DESC_BREVE_RUOLO_ENTE = 'DESTINATARIO'
          AND ec.DESC_BREVE_ENTE = 'FIN';
		  
CREATE OR REPLACE FORCE VIEW PBANDI_V_ISTANZA_STEP_PROCESSO
(
   ID_ISTANZA_STEP_PROCESSO,
   ID_STEP_PROCESSO,
   ID_PROGETTO,
   DT_INIZIO,
   DT_FINE,
   FLAG_FORZATURA,
   ID_PROCESSO,
   ID_TASK_PREC,
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
   PROGR_BANDO_LINEA_INTERVENTO
)
AS
   SELECT A.ID_ISTANZA_STEP_PROCESSO,
          A.ID_STEP_PROCESSO,
          A.ID_PROGETTO,
          A.DT_INIZIO,
          A.DT_FINE,
          A.FLAG_FORZATURA,
          B.ID_PROCESSO,
          B.ID_TASK_PREC,
          B.ID_TASK,
          C.DESCR_BREVE_TASK,
          C.DESCR_TASK,
          C.ID_METODO_TASK,
          C.ID_METODO_TASK_INTEGR,
          C.ID_MULTI_TASK,
          C.ID_TIPO_TASK,
          D.DESCR_BREVE_TIPO_TASK,
          D.DESCR_TIPO_TASK,
          D.FLAG_OPT,
          D.FLAG_PUBLIC,
          e.progr_bando_linea_intervento
     FROM PBANDI_T_ISTANZA_STEP_PROCESSO a,
          PBANDI_T_STEP_PROCESSO b,
          PBANDI_D_TASK c,
          PBANDI_D_TIPO_TASK d,
          PBANDI_T_DOMANDA e,
          PBANDI_T_PROGETTO f
    WHERE     A.ID_STEP_PROCESSO = B.ID_STEP_PROCESSO
          AND C.ID_TASK = B.ID_TASK
          AND D.ID_TIPO_TASK = C.ID_TIPO_TASK
          AND F.ID_PROGETTO = A.ID_PROGETTO
          AND F.ID_DOMANDA = E.id_domanda;
		  