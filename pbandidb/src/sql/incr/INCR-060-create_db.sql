/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

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
   DESC_BREVE_SETTORE,
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
   CODICE_FISCALE_BENEFICIARIO,
   CONSERVAZIONE_CORRENTE,
   CONSERVAZIONE_GENERALE,
   NOME_DOCUMENTO,
   NOME_DOCUMENTO_FIRMATO,
   NOME_DOCUMENTO_MARCATO,
   REPOSITORY,
   FLAG_FIRMA_AUTOGRAFA
)
AS
   WITH LVF                                      --Lof verifica Firma digitale
          AS (SELECT   ID_DOCUMENTO_INDEX, ID_MESSAGGIO_APPL
                FROM   PBANDI_T_LOG_VALIDAZ_FIRMA A
               WHERE   ID_LOG =
                          (SELECT   MAX (ID_LOG)
                             FROM   PBANDI_T_LOG_VALIDAZ_FIRMA
                            WHERE   ID_DOCUMENTO_INDEX = A.ID_DOCUMENTO_INDEX)
                       AND METODO = 'VERIFY'
                       AND FLAG_STATO_VALIDAZIONE = 'N'),
       LMT
          AS                                        -- Log Marcatura temporale
            (SELECT   ID_DOCUMENTO_INDEX, METODO
               FROM   PBANDI_T_LOG_VALIDAZ_FIRMA A
              WHERE   ID_LOG =
                         (SELECT   MAX (ID_LOG)
                            FROM   PBANDI_T_LOG_VALIDAZ_FIRMA
                           WHERE   ID_DOCUMENTO_INDEX = A.ID_DOCUMENTO_INDEX)
                      AND METODO = 'TIMESTAMP'),
       BEN
          AS (SELECT   A.ID_PROGETTO,
                       B.DENOMINAZIONE_ENTE_GIURIDICO,
                       C.CODICE_FISCALE_SOGGETTO
                FROM   PBANDI_R_SOGGETTO_PROGETTO A,
                       PBANDI_T_ENTE_GIURIDICO B,
                       PBANDI_T_SOGGETTO C
               WHERE       A.ID_ENTE_GIURIDICO = B.ID_ENTE_GIURIDICO
                       AND C.ID_SOGGETTO = A.ID_SOGGETTO
                       AND ID_TIPO_BENEFICIARIO != 4
                       AND ID_TIPO_ANAGRAFICA = 1
                       AND B.DT_FINE_VALIDITA IS NULL
                       AND A.DT_FINE_VALIDITA IS NULL),
       BEN_PRIV
          AS (SELECT   A.ID_PROGETTO,
                       B.COGNOME||' '||B.NOME AS DENOMINAZIONE_PERSONA_FISICA,
                       C.CODICE_FISCALE_SOGGETTO
                FROM   PBANDI_R_SOGGETTO_PROGETTO A,
                       PBANDI_T_PERSONA_FISICA B,
                       PBANDI_T_SOGGETTO C
               WHERE       A.ID_PERSONA_FISICA = B.ID_PERSONA_FISICA
                       AND C.ID_SOGGETTO = A.ID_SOGGETTO
                       AND ID_TIPO_BENEFICIARIO != 4
                       AND ID_TIPO_ANAGRAFICA = 1
                       AND B.DT_FINE_VALIDITA IS NULL
                       AND A.DT_FINE_VALIDITA IS NULL)
--MAIN
   SELECT   ID_DOCUMENTO_INDEX,
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
                    AND (DT_MARCA_TEMPORALE IS NOT NULL OR FLAG_FIRMA_AUTOGRAFA = 'S')
               THEN
                  'DA_CLASSIFICARE'
               WHEN     DESC_BREVE IN ('CLASSIFICATO')
                    AND FLAG_FIRMA_CARTACEA = 'N'
                    AND (DT_MARCA_TEMPORALE IS NOT NULL OR FLAG_FIRMA_AUTOGRAFA = 'S')
                    AND DT_CLASSIFICAZIONE IS NOT NULL
               THEN
                  'DA_PROTOCOLLARE'
            END
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
            TO_CHAR (DT_VERIFICA_FIRMA, 'DD/MM/YYYY') AS DT_VERIFICA_FIRMA,
            TO_CHAR (DT_MARCA_TEMPORALE, 'DD/MM/YYYY') AS DT_MARCA_TEMPORALE,
            prot.DESCRIZIONE,
            dp.NUM_PROTOCOLLO,
            ID_SOGG_DELEGATO,
            ID_SOGG_RAPPR_LEGALE,
            blec.DESC_BREVE_ENTE,
            CASE
               WHEN blec.DESC_SETTORE IS NOT NULL THEN '000'
               ELSE NVL (blec.DESC_BREVE_SETTORE, '000')
            END
               DESC_BREVE_SETTORE,
            blec.DESC_ENTE_COMPETENZA,
            ID_MESSAGGIO_APPL,
            blec.DESC_BREVE_ENTE ENTE,
            blec.DESC_BREVE_ENTE
            || NVL (blec.DESC_SETTORE, NVL (blec.DESC_BREVE_SETTORE, '000'))
               SETTORE,
            blec.PAROLA_CHIAVE,
            blec.FEEDBACK_ACTA,
            FASCICOLO_ACTA,
            CLASSIFICAZIONE_ACTA,
            CASE
               WHEN DESC_BREVE_TIPO_ENTE_COMPETENZ IN ('ADG', 'REG') THEN 'S'
               ELSE 'N'
            END
               FLAG_REGIONALE,
            NVL(ben.DENOMINAZIONE_ENTE_GIURIDICO, ben_priv.DENOMINAZIONE_PERSONA_FISICA) DENOMINAZIONE_BENEFICIARIO,
            NVL(ben.CODICE_FISCALE_SOGGETTO, ben_priv.CODICE_FISCALE_SOGGETTO) CODICE_FISCALE_BENEFICIARIO,
            blec.CONSERVAZIONE_CORRENTE,
            blec.CONSERVAZIONE_GENERALE,
            NOME_DOCUMENTO,
            NOME_DOCUMENTO_FIRMATO,
            NOME_DOCUMENTO_MARCATO,
            REPOSITORY,
			FLAG_FIRMA_AUTOGRAFA
     FROM                                       PBANDI_T_DOCUMENTO_INDEX
                                             JOIN
                                                PBANDI_C_TIPO_DOCUMENTO_INDEX
                                             USING (ID_TIPO_DOCUMENTO_INDEX)
                                          LEFT JOIN
                                             PBANDI_D_STATO_DOCUMENTO_INDEX
                                          USING (ID_STATO_DOCUMENTO)
                                       JOIN
                                          PBANDI_T_PROGETTO p
                                       USING (ID_PROGETTO)
                                    JOIN
                                       PBANDI_T_DOMANDA d ON d.ID_DOMANDA = p.ID_DOMANDA
                                   -- USING (ID_DOMANDA)
                                 JOIN
                                    PBANDI_R_BANDO_LINEA_INTERVENT bli
                                 USING (PROGR_BANDO_LINEA_INTERVENTO)
                              JOIN
                                 PBANDI_R_REGOLA_BANDO_LINEA
                              USING (PROGR_BANDO_LINEA_INTERVENTO)
                           JOIN
                              PBANDI_V_BANDO_LINEA_ENTE_COMP blec
                           USING (PROGR_BANDO_LINEA_INTERVENTO)
                        LEFT JOIN
                           PBANDI_T_DOC_PROTOCOLLO dp
                        USING (ID_DOCUMENTO_INDEX)
                     LEFT JOIN
                        PBANDI_D_SISTEMA_PROTOCOLLO prot
                     USING (ID_SISTEMA_PROT)
                  LEFT JOIN
                     lvf
                  USING (ID_DOCUMENTO_INDEX)
               LEFT JOIN
                  lmt
               USING (ID_DOCUMENTO_INDEX)
             LEFT JOIN
               ben
             USING (ID_PROGETTO)
            LEFT JOIN
               ben_priv
            USING (ID_PROGETTO)
    WHERE       PBANDI_C_TIPO_DOCUMENTO_INDEX.FLAG_FIRMABILE = 'S'
            AND blec.DESC_BREVE_RUOLO_ENTE = 'DESTINATARIO'
            AND PBANDI_R_REGOLA_BANDO_LINEA.ID_REGOLA = 42;


CREATE OR REPLACE FORCE VIEW PBANDI.PBANDI_V_DOC_INDEX_BATCH_FINP
(
   ID_DOCUMENTO_INDEX,
   UUID_NODO,
   ID_PROGETTO,
   NOME_FILE,
   NOME_FILE_FIRMATO,
   DT_MARCA_TEMPORALE,
   TIPO_INVIO,
   FLAG_FIRMA_CARTACEA,
   FLAG_TRASM_DEST,
   ID_DICHIARAZIONE_SPESA,
   DESC_BREVE_TIPO_DOC_INDEX,
   ID_INTEGRAZIONE_SPESA,
   NOME_DOCUMENTO,
   NOME_DOCUMENTO_FIRMATO,
   NOME_DOCUMENTO_MARCATO,
   REPOSITORY
)
AS
   WITH SF_INVIO
          AS (  SELECT   c.ID_DICHIARAZIONE_SPESA,
                         c.ID_PROGETTO,
                         CASE
                            WHEN MIN(TIPO_INVIO) || MAX(TIPO_INVIO) = 'CC'
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
                  FROM   PBANDI_S_DICH_DOC_SPESA a,
                         PBANDI_R_DOC_SPESA_PROGETTO b,
                         PBANDI_T_DICHIARAZIONE_SPESA c
                 WHERE   a.ID_DICHIARAZIONE_SPESA = c.ID_DICHIARAZIONE_SPESA
                         AND a.ID_DOCUMENTO_DI_SPESA = b.ID_DOCUMENTO_DI_SPESA
                         AND b.ID_PROGETTO = c.ID_PROGETTO
              GROUP BY   c.ID_DICHIARAZIONE_SPESA, c.ID_PROGETTO)
   SELECT   ID_DOCUMENTO_INDEX,
            UUID_NODO,
            x.ID_PROGETTO,
            NOME_FILE,
            NOME_FILE_FIRMATO,
            DT_MARCA_TEMPORALE,
            CASE
               WHEN x.NOME_ENTITA IN
                          ('PBANDI_T_DICHIARAZIONE_SPESA',
                           'PBANDI_T_COMUNICAZ_FINE_PROG')
               THEN
                  i.TIPO_INVIO
               ELSE
                  NULL                             --Comunicazione di rinuncia
            END
               TIPO_INVIO,
            FLAG_FIRMA_CARTACEA,
            FLAG_TRASM_DEST,
            x.ID_DICHIARAZIONE_SPESA,
            DESC_BREVE_TIPO_DOC_INDEX,
            ID_INTEGRAZIONE_SPESA,
            NOME_DOCUMENTO,                                               --mc
            NOME_DOCUMENTO_FIRMATO,                                       --mc
            NOME_DOCUMENTO_MARCATO,                                       --mc
            REPOSITORY                                                    --mc
     FROM      (SELECT   ID_DOCUMENTO_INDEX,
                         UUID_NODO,
                         di.ID_PROGETTO,
                         p.ID_PROGETTO_FINANZ,
                         NOME_FILE,
                         NOME_ENTITA,
                         CASE
                            WHEN FLAG_FIRMA_CARTACEA = 'N'
                            THEN
                               NOME_FILE
                               || (SELECT   valore
                                     FROM   PBANDI_C_COSTANTI
                                    WHERE   attributo =
                                               'conf.firmaDigitaleFileExtension')
                            ELSE
                               NULL
                         END
                            NOME_FILE_FIRMATO,
                         DT_MARCA_TEMPORALE,
                         FLAG_FIRMA_CARTACEA,
                         NVL (FLAG_TRASM_DEST, 'N') FLAG_TRASM_DEST,
                         --Dichiarazione di spesa(id_target di Id_entita 63 PBANDI_T_DICHIARAZIONE_SPESA)
                         --Comunicazione di fine progetto (id_entita 270  PBANDI_T_COMUNICAZ_FINE_PROG cercare la dichiarazione di spesa di tipo DESC_BREVE_TIPO_DICHIARA_SPESA=FC)
                         --Comunicazione di rinuncia (id_entita 249 PBANDI_T_RINUNCIA id_dichiarazione di spesa=NULL
                         CASE
                            WHEN en.NOME_ENTITA =
                                    'PBANDI_T_DICHIARAZIONE_SPESA'
                            THEN
                               di.ID_TARGET
                            WHEN en.NOME_ENTITA =
                                    'PBANDI_T_COMUNICAZ_FINE_PROG'
                            THEN
                               (SELECT   MAX (id_dichiarazione_spesa)
                                  FROM   PBANDI_T_DICHIARAZIONE_SPESA dich
                                 -- id_progetto = di.id_progetto
                                 WHERE   id_progetto =
                                            NVL (p.id_progetto_finanz,
                                                 p.id_progetto)
                                         AND id_tipo_dichiaraz_spesa IN
                                                  (SELECT   id_tipo_dichiaraz_spesa
                                                     FROM   PBANDI_D_TIPO_DICHIARAZ_SPESA
                                                    WHERE   DESC_BREVE_TIPO_DICHIARA_SPESA =
                                                               'FC')) -- Finale con comunicazione
                            ELSE
                               NULL                --Comunicazione di rinuncia
                         END
                            ID_DICHIARAZIONE_SPESA,
                         tdi.DESC_BREVE_TIPO_DOC_INDEX,
                         NULL ID_INTEGRAZIONE_SPESA,
                         NOME_DOCUMENTO,                                  --mc
                         NOME_DOCUMENTO_FIRMATO,                          --mc
                         NOME_DOCUMENTO_MARCATO,                          --mc
                         REPOSITORY                                       --mc
                  FROM                              PBANDI_T_DOCUMENTO_INDEX di
                                                 JOIN
                                                    PBANDI_C_TIPO_DOCUMENTO_INDEX tdi
                                                 USING (ID_TIPO_DOCUMENTO_INDEX)
                                              JOIN
                                                 PBANDI_C_ENTITA en
                                              USING (ID_ENTITA)
                                           LEFT JOIN
                                              PBANDI_D_STATO_DOCUMENTO_INDEX sdi
                                           USING (ID_STATO_DOCUMENTO)
                                        JOIN
                                           PBANDI_T_PROGETTO p
                                        ON p.ID_PROGETTO = di.ID_PROGETTO
                                     JOIN
                                       PBANDI_T_DOMANDA d ON d.ID_DOMANDA = p.ID_DOMANDA
                                     --USING (ID_DOMANDA)
                                  JOIN
                                     PBANDI_R_BANDO_LINEA_INTERVENT bli
                                  USING (PROGR_BANDO_LINEA_INTERVENTO)
                               JOIN
                                  PBANDI_R_BANDO_LINEA_ENTE_COMP blec
                               USING (PROGR_BANDO_LINEA_INTERVENTO)
                            JOIN
                               PBANDI_D_RUOLO_ENTE_COMPETENZA rec
                            USING (ID_RUOLO_ENTE_COMPETENZA)
                         JOIN
                            PBANDI_T_ENTE_COMPETENZA ec
                         USING (ID_ENTE_COMPETENZA)
                 WHERE   tdi.FLAG_FIRMABILE = 'S'
                         AND en.NOME_ENTITA IN
                                  ('PBANDI_T_DICHIARAZIONE_SPESA',
                                   'PBANDI_T_COMUNICAZ_FINE_PROG',
                                   'PBANDI_T_RINUNCIA')
                         -- Se Dematerializzato allora deve essere inviato altrimenti no
                         AND ( (NVL (di.FLAG_FIRMA_CARTACEA, 'N') = 'N'
                                AND sdi.DESC_BREVE = 'INVIATO')
                              OR ( (di.FLAG_FIRMA_CARTACEA = 'S'
                                    AND sdi.DESC_BREVE != 'INVIATO')))
                         -- Se Dematerializzato allora deve essere marcato temporalmente altrimenti no
                         AND ( (NVL (di.FLAG_FIRMA_CARTACEA, 'N') = 'N'
                                AND di.DT_MARCA_TEMPORALE IS NOT NULL)
                              OR ( (di.FLAG_FIRMA_CARTACEA = 'S'
                                    AND di.DT_MARCA_TEMPORALE IS NULL)))
                         AND rec.DESC_BREVE_RUOLO_ENTE = 'DESTINATARIO'
                         AND ec.DESC_BREVE_ENTE = 'FIN') x
            LEFT JOIN
               SF_INVIO i
            ON (i.ID_DICHIARAZIONE_SPESA = x.ID_DICHIARAZIONE_SPESA
                AND i.ID_PROGETTO = NVL (x.ID_PROGETTO_FINANZ, x.ID_PROGETTO))
   -- Integrazione Dichiarazione di Spesa
   UNION ALL
   SELECT   di.ID_DOCUMENTO_INDEX,
            di.UUID_NODO,
            p.ID_PROGETTO,
            di.NOME_FILE,
            CASE
               WHEN FLAG_FIRMA_CARTACEA = 'N'
               THEN
                  di.NOME_FILE
                  || (SELECT   valore
                        FROM   PBANDI_C_COSTANTI
                       WHERE   attributo = 'conf.firmaDigitaleFileExtension')
               ELSE
                  NULL
            END
               NOME_FILE_FIRMATO,
            NULL DT_MARCA_TEMPORALE,
            NULL TIPO_INVIO,
            NULL FLAG_FIRMA_CARTACEA,
            NVL (FLAG_TRASM_DEST, 'N') FLAG_TRASM_DEST,
            a.ID_DICHIARAZIONE_SPESA,
            'INT-DS' DESC_BREVE_TIPO_DOC_INDEX,
            a.ID_INTEGRAZIONE_SPESA,
            NOME_DOCUMENTO,                                              -- mc
            NOME_DOCUMENTO_FIRMATO,                                      -- mc
            NOME_DOCUMENTO_MARCATO,                                      -- mc
            REPOSITORY                                                   -- mc
     FROM                                    PBANDI_T_INTEGRAZIONE_SPESA a
                                          JOIN
                                             PBANDI_T_FILE_ENTITA b
                                          ON b.id_target =
                                                a.ID_INTEGRAZIONE_SPESA
                                       JOIN
                                          PBANDI_T_FILE f
                                       ON f.ID_FILE = b.ID_FILE
                                    JOIN
                                       PBANDI_C_ENTITA en
                                    ON en.ID_ENTITA = b.ID_ENTITA
                                 JOIN
                                    PBANDI_T_DICHIARAZIONE_SPESA c
                                 ON c.ID_DICHIARAZIONE_SPESA =
                                       a.ID_DICHIARAZIONE_SPESA
                              JOIN
                                 PBANDI_T_DOCUMENTO_INDEX di
                              ON di.ID_DOCUMENTO_INDEX = f.ID_DOCUMENTO_INDEX
                           JOIN
                              PBANDI_T_PROGETTO p
                           ON p.ID_PROGETTO = c.ID_PROGETTO
                        JOIN
                           PBANDI_T_DOMANDA d
                        ON d.ID_DOMANDA = p.ID_DOMANDA
                     JOIN
                        PBANDI_R_BANDO_LINEA_INTERVENT bli
                     ON BLI.PROGR_BANDO_LINEA_INTERVENTO =
                           d.PROGR_BANDO_LINEA_INTERVENTO
                  JOIN
                     PBANDI_R_BANDO_LINEA_ENTE_COMP blec
                  ON blec.PROGR_BANDO_LINEA_INTERVENTO =
                        bli.PROGR_BANDO_LINEA_INTERVENTO
               JOIN
                  PBANDI_D_RUOLO_ENTE_COMPETENZA rec
               ON rec.ID_RUOLO_ENTE_COMPETENZA =
                     blec.ID_RUOLO_ENTE_COMPETENZA
            JOIN
               PBANDI_T_ENTE_COMPETENZA ec
            ON ec.ID_ENTE_COMPETENZA = blec.ID_ENTE_COMPETENZA
    WHERE       en.NOME_ENTITA = 'PBANDI_T_INTEGRAZIONE_SPESA'
            AND rec.DESC_BREVE_RUOLO_ENTE = 'DESTINATARIO'
            AND ec.DESC_BREVE_ENTE = 'FIN'
            -- Integrazione DS
            AND b.FLAG_ENTITA = 'I';



-- PBANDI_T_DOCUMENTO_INDEX
ALTER TABLE PBANDI_T_DOCUMENTO_INDEX ADD ID_DOMANDA NUMBER(8);
ALTER TABLE PBANDI_T_DOCUMENTO_INDEX ADD ID_SOGG_BENEFICIARIO NUMBER(8);

-- FK
ALTER TABLE PBANDI_T_DOCUMENTO_INDEX ADD (
  CONSTRAINT fk_pbandi_t_domanda_11
  FOREIGN KEY (ID_DOMANDA) 
  REFERENCES PBANDI_T_DOMANDA (ID_DOMANDA));

-- FK
ALTER TABLE PBANDI_T_DOCUMENTO_INDEX ADD (
  CONSTRAINT fk_pbandi_t_soggetto_37
  FOREIGN KEY (ID_SOGG_BENEFICIARIO) 
  REFERENCES PBANDI_T_SOGGETTO (ID_SOGGETTO));
  
-- IDX
CREATE INDEX ie_pbandi_t_documento_index_04 ON PBANDI_T_DOCUMENTO_INDEX (ID_DOMANDA) TABLESPACE PBANDI_IDX;
CREATE INDEX ie_pbandi_t_documento_index_05 ON PBANDI_T_DOCUMENTO_INDEX (ID_SOGG_BENEFICIARIO) TABLESPACE PBANDI_IDX;

--IDX PBANDI_R_SOGG_PROGETTO_SEDE -
CREATE INDEX IE1_PBANDI_R_SOGG_PROG_SEDE ON PBANDI_R_SOGG_PROGETTO_SEDE
(PROGR_SOGGETTO_PROGETTO)
TABLESPACE PBANDI_IDX;


CREATE INDEX IE2_PBANDI_R_SOGG_PROG_SEDE ON PBANDI_R_SOGG_PROGETTO_SEDE
(ID_INDIRIZZO)
TABLESPACE PBANDI_IDX;

-- PBANDI_T_DATI_PROGETTO_MONIT
ALTER TABLE PBANDI_T_DATI_PROGETTO_MONIT ADD ID_PROGETTO_FSC number(8);

/*
-- PBANDI_T_AFFIDAMENTO_CHECKLIST
ALTER TABLE PBANDI_T_AFFIDAMENTO_CHECKLIST ADD ID_LINEA_DI_INTERVENTO NUMBER(3);
-- FK
ALTER TABLE PBANDI_T_AFFIDAMENTO_CHECKLIST ADD (
  CONSTRAINT FK_PBANDI_D_LINEA_INTERV_65
  FOREIGN KEY (ID_LINEA_DI_INTERVENTO) 
  REFERENCES PBANDI_D_LINEA_DI_INTERVENTO (ID_LINEA_DI_INTERVENTO));
*/  
-- PBANDI_R_BANDO_LINEA_ENTE_COMP
ALTER TABLE PBANDI_R_BANDO_LINEA_ENTE_COMP ADD indirizzo_mail VARCHAR2(200);

-- PBANDI_R_SOGG_TIP_ANAG_EN_COMP;
--drop table PBANDI_R_SOGG_TIP_ANAG_EN_COMP;

--
--
-- PBANDI_D_ITER
ALTER TABLE PBANDI_D_ITER ADD ORDINAMENTO NUMBER(8);
 
-- PBANDI_D_FASE_MONIT
ALTER TABLE PBANDI_D_FASE_MONIT ADD 
(ORDINAMENTO NUMBER(8), 
 DATA_LIMITE DATE);
 
-- PBANDI_R_BANDO_LINEA_FASIMONIT
CREATE TABLE PBANDI_R_BANDO_LINEA_FASIMONIT
(PROGR_BANDO_LINEA_INTERVENTO NUMBER(8),
 ID_FASE_MONIT number(3)
);

-- pk
ALTER TABLE PBANDI_R_BANDO_LINEA_FASIMONIT  ADD 
(CONSTRAINT PK_PBANDI_R_BANDO_LIN_FMON  PRIMARY KEY (PROGR_BANDO_LINEA_INTERVENTO,ID_FASE_MONIT)
USING INDEX TABLESPACE PBANDI_IDX);

-- fk
ALTER TABLE PBANDI_R_BANDO_LINEA_FASIMONIT ADD (
  CONSTRAINT fk_pbandi_r_bando_linea_int_33
  FOREIGN KEY (PROGR_BANDO_LINEA_INTERVENTO) 
  REFERENCES PBANDI_R_BANDO_LINEA_INTERVENT (PROGR_BANDO_LINEA_INTERVENTO));
  
-- fk
ALTER TABLE PBANDI_R_BANDO_LINEA_FASIMONIT ADD (
  CONSTRAINT fk_pbandi_d_fase_monit_05
  FOREIGN KEY (ID_FASE_MONIT) 
  REFERENCES Pbandi_d_Fase_Monit (ID_FASE_MONIT));

-- idx
CREATE INDEX IE1_PBANDI_R_BANDO_LIN_FMON ON PBANDI_R_BANDO_LINEA_FASIMONIT (ID_FASE_MONIT) TABLESPACE PBANDI_IDX;

/*
-- PBANDI_R_PROGETTO_ITER_FILES
CREATE TABLE PBANDI_R_PROGETTO_ITER_FILES
(ID_PROGETTO_ITER number(8),
 ID_PROGETTO  NUMBER(8) NOT NULL,
 ID_FASE_MONIT NUMBER(3)NOT NULL,
 ID_FILE_ENTITA INTEGER NOT NULL
);

-- pk
ALTER TABLE PBANDI_R_PROGETTO_ITER_FILES  ADD 
(CONSTRAINT PK_PBANDI_R_PROGETTO_IT_FIL  PRIMARY KEY (ID_PROGETTO_ITER_FILES)
USING INDEX TABLESPACE PBANDI_IDX);

-- fk
ALTER TABLE PBANDI_R_PROGETTO_ITER_FILES ADD (
  CONSTRAINT fk_pbandi_t_progetto_89
  FOREIGN KEY (ID_PROGETTO) 
  REFERENCES PBANDI_T_PROGETTO (ID_PROGETTO));

-- fk
ALTER TABLE PBANDI_R_PROGETTO_ITER_FILES ADD (
  CONSTRAINT fk_pbandi_d_fase_monit_06
  FOREIGN KEY (ID_FASE_MONIT) 
  REFERENCES Pbandi_d_Fase_Monit (ID_FASE_MONIT));

-- fk

ALTER TABLE PBANDI_R_PROGETTO_ITER_FILES ADD (
  CONSTRAINT fk_PBANDI_T_FILE_ENTITA_01
  FOREIGN KEY (id_file_entita) 
  REFERENCES PBANDI_T_FILE_ENTITA (id_file_entita));
  
 -- idx
CREATE INDEX IE1_PBANDI_R_PROGETTO_IT_FIL ON PBANDI_R_PROGETTO_ITER_FILES (ID_PROGETTO) TABLESPACE PBANDI_IDX;
CREATE INDEX IE2_PBANDI_R_PROGETTO_IT_FIL ON PBANDI_R_PROGETTO_ITER_FILES (ID_FASE_MONIT) TABLESPACE PBANDI_IDX;
CREATE INDEX IE3_PBANDI_R_PROGETTO_IT_FIL ON PBANDI_R_PROGETTO_ITER_FILES (id_file_entita) TABLESPACE PBANDI_IDX;

-- SEQ
CREATE SEQUENCE SEQ_PBANDI_R_PROGETTO_ITER_FIL
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;
*/

-- PBANDI_R_PROGETTO_FASE_MONIT
ALTER TABLE PBANDI_R_PROGETTO_FASE_MONIT ADD MOTIVO_SCOSTAMENTO VARCHAR2(350);


CREATE SEQUENCE SEQ_PBANDI_R_PROGETTO_ITER
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;

-- PBANDI_R_PROGETTO_ITER
CREATE TABLE PBANDI_R_PROGETTO_ITER
(
  ID_PROGETTO_ITER        NUMBER(8),
  ID_PROGETTO             NUMBER(8)             NOT NULL,
  ID_ITER                 NUMBER(3)             NOT NULL,
  FLAG_FASE_CHIUSA        INTEGER,
  ID_DICHIARAZIONE_SPESA  NUMBER(8)
);

-- FK
  
ALTER TABLE PBANDI_R_PROGETTO_ITER ADD (
  CONSTRAINT FK_PBANDI_T_PROGETTO_89 
 FOREIGN KEY (ID_PROGETTO) 
 REFERENCES PBANDI_T_PROGETTO (ID_PROGETTO),
  CONSTRAINT FK_PBANDI_D_ITER_03 
 FOREIGN KEY (ID_ITER) 
 REFERENCES PBANDI_D_ITER (ID_ITER),
  CONSTRAINT FK_PBANDI_T_DICHIARAZ_SPESA_09 
 FOREIGN KEY (ID_DICHIARAZIONE_SPESA) 
 REFERENCES PBANDI_T_DICHIARAZIONE_SPESA (ID_DICHIARAZIONE_SPESA));
  
-- IDX
CREATE INDEX IE1_PBANDI_R_PROGETTO_ITER ON PBANDI_R_PROGETTO_ITER (ID_PROGETTO) TABLESPACE PBANDI_IDX;
CREATE INDEX IE3_PBANDI_R_PROGETTO_ITER ON PBANDI_R_PROGETTO_ITER (ID_DICHIARAZIONE_SPESA) TABLESPACE PBANDI_IDX;
CREATE INDEX IE2_PBANDI_R_PROGETTO_ITER ON PBANDI_R_PROGETTO_ITER (ID_ITER)  TABLESPACE PBANDI_IDX;
/*
DROP INDEX AK1_PBANDI_T_AFFID_CHECKLIST;
CREATE UNIQUE INDEX AK1_PBANDI_T_AFFID_CHECKLIST ON PBANDI_T_AFFIDAMENTO_CHECKLIST
(ID_LINEA_DI_INTERVENTO,ID_NORMA, ID_TIPO_AFFIDAMENTO, ID_TIPO_APPALTO, ID_TIPOLOGIA_AGGIUDICAZ, SOPRA_SOGLIA)
TABLESPACE PBANDI_IDX;
*/

-- richiesta di Carla
alter table PBANDI_R_PROGETTO_ITER modify FLAG_FASE_CHIUSA integer CONSTRAINT CHK_FLAG_FASE_CHIUSA check (FLAG_FASE_CHIUSA IN (0,1,NULL));


-- Correzione query Accesso come Istruttore ADG con abilitazione a bando /Documenti di progetto
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
   DESC_CATEG_ANAGRAFICA_MITT,
   FLAG_TRASM_DEST
)
AS
   WITH ts
          AS (SELECT   ts1.*,
                       tec.id_ente_competenza,
                       rsta.id_tipo_anagrafica,
                       rsta.dt_inizio_validita,
                       rsta.dt_fine_validita,
                       rsta.flag_aggiornato_flux
                FROM   pbandi_t_soggetto ts1,
                       pbandi_t_ente_competenza tec,
                       pbandi_r_sogg_tipo_anagrafica rsta
               WHERE   rsta.id_soggetto = ts1.id_soggetto
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
                                                   'ADG-IST-MASTER',
                                                   'ISTR-AFFIDAMENTI'))
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
                                                     tec.id_ente_competenza)))
              UNION ALL
              SELECT   DISTINCT ts1.*,
                                rblec.id_ente_competenza,
                                rsta.id_tipo_anagrafica,
                                rsta.dt_inizio_validita,
                                rsta.dt_fine_validita,
                                rsta.flag_aggiornato_flux
                FROM   pbandi_t_soggetto ts1,
                       pbandi_r_sogg_tipo_anagrafica rsta,
                       pbandi_r_soggetto_progetto rsp,
                       pbandi_t_progetto tp,
                       pbandi_t_domanda td,
                       pbandi_r_bando_linea_ente_comp rblec
               WHERE   rsta.id_soggetto = ts1.id_soggetto
                       AND rsta.id_tipo_anagrafica IN
                                (SELECT   dta.id_tipo_anagrafica
                                   FROM   pbandi_d_tipo_anagrafica dta
                                  WHERE   dta.desc_breve_tipo_anagrafica IN
                                                ('PERSONA-FISICA',
                                                 'OI-ISTRUTTORE',
                                                 'ADG-ISTRUTTORE'))
                       AND rsp.id_soggetto = ts1.id_soggetto
                       AND tp.id_progetto = rsp.id_progetto
                       AND td.id_domanda = tp.id_domanda
                       AND rblec.progr_bando_linea_intervento =
                             td.progr_bando_linea_intervento
				),
       tsa
          AS (SELECT   ts1.*, tec.id_ente_competenza, rsta.id_tipo_anagrafica
                FROM   pbandi_t_soggetto ts1,
                       pbandi_t_ente_competenza tec,
                       pbandi_r_sogg_tipo_anagrafica rsta
               WHERE   rsta.id_soggetto = ts1.id_soggetto
                       AND NVL (TRUNC (rsta.dt_fine_validita),
                                TRUNC (SYSDATE + 1)) > TRUNC (SYSDATE)
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
                                                   'ADG-IST-MASTER',
                                                   'ISTR-AFFIDAMENTI'))
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
                                                     tec.id_ente_competenza)))
						),
       ben
          AS (SELECT   rsp.id_soggetto id_soggetto_beneficiario,
                       rsp.id_progetto,
                       rsp.id_ente_giuridico,
                       rsp.id_persona_fisica
                FROM   pbandi_r_soggetto_progetto rsp
               WHERE   rsp.ID_TIPO_ANAGRAFICA =
                          (SELECT   dta.id_tipo_anagrafica
                             FROM   pbandi_d_tipo_anagrafica dta
                            WHERE   dta.desc_breve_tipo_anagrafica =
                                       'BENEFICIARIO')
                       AND NVL (TRUNC (rsp.dt_fine_validita),
                                TRUNC (SYSDATE + 1)) > TRUNC (SYSDATE)
                       AND NVL (rsp.id_tipo_beneficiario, '-1') <>
                             (SELECT   dtb.id_tipo_beneficiario
                                FROM   pbandi_d_tipo_beneficiario dtb
                               WHERE   dtb.desc_breve_tipo_beneficiario =
                                          'BEN-ASSOCIATO')),
       m
          AS (SELECT   tp.id_progetto,
                       rbli.progr_bando_linea_intervento,
                       NULL progr_soggetto_progetto,
                       tsa.codice_fiscale_soggetto,
                       tsa.id_soggetto,
                       tsa.id_tipo_anagrafica,
                       tp.codice_visualizzato codice_visualizzato_progetto
                FROM   tsa,
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
                       AND tsa.id_ente_competenza = rble.id_ente_competenza
              UNION ALL
              SELECT   DISTINCT
                       rsp.id_progetto id_progetto,
                       rbli.progr_bando_linea_intervento,
                       rsp.progr_soggetto_progetto,
                       tsogg.codice_fiscale_soggetto,
                       tsogg.id_soggetto,
                       rsp.id_tipo_anagrafica,
                       tp.codice_visualizzato codice_visualizzato_progetto
                FROM   PBANDI_T_SOGGETTO tsogg,
                       pbandi_r_soggetto_progetto rsp,
                       pbandi_t_progetto tp,
                       pbandi_t_domanda td,
                       pbandi_r_bando_linea_intervent rbli,
                       pbandi_r_bando_linea_ente_comp rble
               WHERE       tsogg.id_soggetto = rsp.id_soggetto
                       AND NVL (TRUNC (rsp.dt_fine_validita),
                                TRUNC (SYSDATE + 1)) > TRUNC (SYSDATE)
                       AND tp.id_progetto = rsp.id_progetto
                       AND td.id_domanda = tp.id_domanda
                       AND rbli.progr_bando_linea_intervento =
                             td.progr_bando_linea_intervento
                       AND rble.progr_bando_linea_intervento =
                             rbli.progr_bando_linea_intervento
                       AND rble.id_ruolo_ente_competenza =
                             (SELECT   dre.id_ruolo_ente_competenza
                                FROM   pbandi_d_ruolo_ente_competenza dre
                               WHERE   dre.desc_breve_ruolo_ente =
                                          'DESTINATARIO')),
       spb
          AS (SELECT   DISTINCT m.id_progetto,
                                m.progr_bando_linea_intervento,
                                m.id_soggetto,
                                m.id_tipo_anagrafica,
                                m.codice_visualizzato_progetto,
                                ben.id_soggetto_beneficiario,
                                ben.id_ente_giuridico,
                                ben.id_persona_fisica
                FROM   ben, m, pbandi_d_tipo_anagrafica dta
               WHERE   m.id_tipo_anagrafica = dta.id_tipo_anagrafica
                       AND ben.id_progetto = m.id_progetto
                       AND (dta.desc_breve_tipo_anagrafica <>
                               'PERSONA-FISICA'
                            OR ben.id_soggetto_beneficiario IN
                                    (SELECT   rsc.id_soggetto_ente_giuridico
                                       FROM   pbandi_r_sogg_prog_sogg_correl rspsc,
                                              pbandi_r_soggetti_correlati rsc
                                      WHERE   rsc.progr_soggetti_correlati =
                                                 rspsc.progr_soggetti_correlati
                                              AND NVL (
                                                    TRUNC (
                                                       rsc.dt_fine_validita
                                                    ),
                                                    TRUNC (SYSDATE + 1)
                                                 ) > TRUNC (SYSDATE)
                                              AND rspsc.progr_soggetto_progetto =
                                                    m.progr_soggetto_progetto
                                              AND rsc.id_tipo_soggetto_correlato IN
                                                       (SELECT   rrta.id_tipo_soggetto_correlato
                                                          FROM   pbandi_r_ruolo_tipo_anagrafica rrta
                                                         WHERE   rrta.id_tipo_anagrafica =
                                                                    m.id_tipo_anagrafica)))
		),
       docindex_anagrafica
          AS (SELECT   rdita.id_tipo_documento_index,
                       rdita.id_tipo_anagrafica,
                       tdi.desc_breve_tipo_doc_index,
                       tdi.desc_tipo_doc_index,
                       ta.desc_breve_tipo_anagrafica,
                       tdi.flag_firmabile
                FROM   PBANDI_R_DOC_INDEX_TIPO_ANAG rdita,
                       PBANDI_D_TIPO_ANAGRAFICA ta,
                       PBANDI_C_TIPO_DOCUMENTO_INDEX tdi
               WHERE   ta.id_tipo_anagrafica = rdita.id_tipo_anagrafica
                       AND tdi.id_tipo_documento_index =
                             rdita.id_tipo_documento_index),
       docindex_ente_soggetto
          AS (SELECT   rtdib.id_tipo_documento_index,
                       rtdib.progr_bando_linea_intervento,
                       ts.id_ente_competenza,
                       ts.id_soggetto
                FROM   PBANDI_R_TP_DOC_IND_BAN_LI_INT rtdib,
                       PBANDI_R_BANDO_LINEA_ENTE_COMP rblec,
                       ts
               WHERE   ts.id_ente_competenza = rblec.id_ente_competenza
                       AND rtdib.progr_bando_linea_intervento =
                             rblec.progr_bando_linea_intervento),
       tdis
          AS (SELECT   DISTINCT
                       docindex_anagrafica.*,
                       docindex_ente_soggetto.id_ente_competenza,
                       docindex_ente_soggetto.id_soggetto
                FROM   docindex_anagrafica, docindex_ente_soggetto
               WHERE   docindex_anagrafica.id_tipo_documento_index =
                          docindex_ente_soggetto.id_tipo_documento_index),
       lvf                                             --Log Validazione Firma
          AS (SELECT   id_documento_index, codice_errore, b.messaggio
                FROM   PBANDI_T_LOG_VALIDAZ_FIRMA a, PBANDI_T_MESSAGGI_APPL b
               WHERE   id_log =
                          (SELECT   MAX (id_log)
                             FROM   PBANDI_T_LOG_VALIDAZ_FIRMA
                            WHERE   id_documento_index = a.id_documento_index)
                       AND metodo = 'VERIFY'
                       AND flag_stato_validazione = 'N'
                       AND a.id_messaggio_appl = b.id_messaggio(+)),
       SF_ENTE_GIURIDICO
          AS                                              -- Dati beneficiario
            (SELECT                                          /*+MATERIALIZE */
                   a.id_ente_giuridico as id_riferimento,
                      b.DENOMINAZIONE_ENTE_GIURIDICO,
                      b.id_soggetto,
                      d.codice_fiscale_soggetto
               FROM   (  SELECT   MAX (ID_ENTE_GIURIDICO) id_ente_giuridico
                           FROM   PBANDI_T_ENTE_GIURIDICO a
                          WHERE   dt_fine_validita IS NULL
                       GROUP BY   id_soggetto) a,
                      PBANDI_T_ENTE_GIURIDICO b,
                      PBANDI_T_SOGGETTO d
              WHERE   A.ID_ENTE_GIURIDICO = b.id_ente_giuridico
                      AND d.id_soggetto = b.id_soggetto
			  UNION ALL
              SELECT   a.id_persona_fisica as id_riferimento,
                      b.cognome||' '||b.nome as DENOMINAZIONE_ENTE_GIURIDICO,
                      b.id_soggetto,
                      d.codice_fiscale_soggetto
               FROM   PBANDI_R_SOGGETTO_PROGETTO a,
                      PBANDI_T_PERSONA_FISICA b,
                      PBANDI_T_SOGGETTO d
              WHERE   A.id_persona_fisica = b.id_persona_fisica
                      AND d.id_soggetto = b.id_soggetto			  ),
       SF_ENTE_GIURIDICO_OLD
          AS                                              -- Dati beneficiario
            (SELECT                                          /*+MATERIALIZE */
                   a  .id_ente_giuridico,
                      b.DENOMINAZIONE_ENTE_GIURIDICO,
                      b.id_soggetto,
                      d.codice_fiscale_soggetto
               FROM   (  SELECT   MAX (ID_ENTE_GIURIDICO) id_ente_giuridico
                           FROM   PBANDI_T_ENTE_GIURIDICO a
                          WHERE   dt_fine_validita IS NULL
                       GROUP BY   id_soggetto) a,
                      PBANDI_T_ENTE_GIURIDICO b,
                      PBANDI_T_SOGGETTO d
              WHERE   A.ID_ENTE_GIURIDICO = b.id_ente_giuridico
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
                  (SELECT   DISTINCT eg.denominazione_ente_giuridico
                     FROM   PBANDI_T_ENTE_GIURIDICO eg
                    WHERE   eg.id_ente_giuridico = spb.id_ente_giuridico)
               ELSE
                  (SELECT   DISTINCT pf.cognome || ' ' || pf.nome
                     FROM   PBANDI_T_PERSONA_FISICA pf
                    WHERE   pf.id_persona_fisica = spb.id_persona_fisica)
            END
               AS beneficiario,
            CASE
               WHEN spb.id_soggetto_beneficiario IS NOT NULL
               THEN
                  (SELECT   DISTINCT s.codice_fiscale_soggetto
                     FROM   PBANDI_T_SOGGETTO s
                    WHERE   s.id_soggetto = spb.id_soggetto_beneficiario)
            END
               AS codice_fiscale_beneficiario,
            di.DT_VERIFICA_FIRMA,
            di.DT_MARCA_TEMPORALE,
            di.FLAG_FIRMA_CARTACEA,
            di.ID_STATO_DOCUMENTO,
            tdis.flag_firmabile,
            CASE
               WHEN spb.progr_bando_linea_intervento IN
                          (SELECT   DISTINCT progr_bando_linea_intervento
                             FROM   PBANDI_R_REGOLA_BANDO_LINEA
                            WHERE   ID_REGOLA IN (42, 51, 52, 53))
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
            di.flag_trasm_dest                                  -- mc 30092021
     FROM   spb,
            tdis,
            PBANDI_T_DOCUMENTO_INDEX di,
            lvf,
            PBANDI_T_DOC_PROTOCOLLO tdp,
            PBANDI_D_STATO_DOCUMENTO_INDEX sdi
    WHERE       di.id_progetto = spb.id_progetto
            AND di.id_tipo_documento_index = tdis.id_tipo_documento_index
            AND tdis.id_tipo_anagrafica = spb.id_tipo_anagrafica
            AND tdis.id_soggetto = spb.id_soggetto
            AND sdi.ID_STATO_DOCUMENTO(+) = di.ID_STATO_DOCUMENTO
            AND lvf.id_documento_index(+) = di.id_documento_index
            AND tdp.ID_DOCUMENTO_INDEX(+) = di.ID_DOCUMENTO_INDEX
            --AND di.ID_CATEG_ANAGRAFICA_MITT IS NULL
   UNION   --documenti inseriti dalla nuova funzione di upload, solo per gli utenti autorizzati a vederli
   SELECT   di.id_documento_index,
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
                          (SELECT   DISTINCT progr_bando_linea_intervento
                             FROM   PBANDI_R_REGOLA_BANDO_LINEA
                            WHERE   ID_REGOLA IN (42, 51, 52, 53))
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
            --di.ID_CATEG_ANAGRAFICA_MITT,
            --ca.DESC_CATEG_ANAGRAFICA DESC_CATEG_ANAGRAFICA_MITT,
            di.flag_trasm_dest                                  -- mc 30092021
     FROM   PBANDI_T_DOCUMENTO_INDEX di,
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
    WHERE       di.ID_CATEG_ANAGRAFICA_MITT = ca.ID_CATEG_ANAGRAFICA
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
            AND sta.id_tipo_anagrafica = ta.id_tipo_anagrafica
   UNION ALL --	-- Soggetti abilitati per bando linea
   SELECT   di.id_documento_index,
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
            sbli.progr_bando_linea_intervento,
            p.codice_visualizzato,
            sbli.id_soggetto,
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
               WHEN sbli.progr_bando_linea_intervento IN
                          (SELECT   DISTINCT progr_bando_linea_intervento
                             FROM   PBANDI_R_REGOLA_BANDO_LINEA
                            WHERE   ID_REGOLA IN (42, 51, 52, 53))
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
            di.flag_trasm_dest
     FROM   PBANDI_T_DOCUMENTO_INDEX di,
            PBANDI_T_DOC_PROTOCOLLO tdp,
            PBANDI_D_STATO_DOCUMENTO_INDEX sdi,
            PBANDI_C_TIPO_DOCUMENTO_INDEX tdi,
            lvf,
            PBANDI_D_TIPO_ANAGRAFICA ta,
            PBANDI_R_SOGGETTO_PROGETTO sp,
            SF_ENTE_GIURIDICO eg,
            PBANDI_R_SOGG_BANDO_LIN_INT sbli,
			PBANDI_T_DOMANDA d,
			PBANDI_T_PROGETTO p
    WHERE   tdp.ID_DOCUMENTO_INDEX(+) = di.ID_DOCUMENTO_INDEX
            AND sdi.ID_STATO_DOCUMENTO(+) = di.ID_STATO_DOCUMENTO
            AND lvf.id_documento_index(+) = di.id_documento_index
            AND tdi.ID_TIPO_DOCUMENTO_INDEX = di.ID_TIPO_DOCUMENTO_INDEX
            AND p.ID_PROGETTO = di.ID_PROGETTO
            AND sp.id_progetto = di.id_progetto
            AND sp.id_tipo_beneficiario != 4
            AND sp.id_tipo_anagrafica = 1
            AND eg.id_soggetto = sp.id_soggetto
            AND sbli.id_tipo_anagrafica = ta.id_tipo_anagrafica
			AND d.progr_bando_linea_intervento = sbli.progr_bando_linea_intervento
			AND p.id_domanda = d.id_domanda;



/* ---------------------------------------------------------------------- */
/* GESTIONE HELP ONLINE                                       */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Alter table "PBANDI_T_UTENTE"                                        */
/* ---------------------------------------------------------------------- */

ALTER TABLE PBANDI_T_UTENTE ADD FLAG_EDIT_HELP VARCHAR2(1);
ALTER TABLE PBANDI_T_UTENTE ADD CONSTRAINT CHK_PBANDI_T_UTEN_FLG_EDT_HELP 
    CHECK (FLAG_EDIT_HELP in ('S'));

COMMENT ON COLUMN PBANDI_T_UTENTE.FLAG_EDIT_HELP IS 'Il soggetto è abilitato ad editare l''help on  line';

/* ---------------------------------------------------------------------- */
/* Add table "PBANDI_D_GROUP_HELP"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE PBANDI_D_GROUP_HELP (
    ID_GROUP_HELP NUMBER(3),
    DESC_BREVE_GROUP_HELP VARCHAR2(20) NOT NULL,
    CONSTRAINT PK_PBANDI_D_GROUP_HELP PRIMARY KEY (ID_GROUP_HELP)
);

/* ---------------------------------------------------------------------- */
/* Add table "PBANDI_D_PAGINA_HELP"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE PBANDI_D_PAGINA_HELP (
    ID_PAGINA_HELP NUMBER(6) NOT NULL,
    DESC_BREVE_PAGINA_HELP VARCHAR2(20) NOT NULL,
    DESC_PAGINA_HELP VARCHAR2(200),
    CONSTRAINT PK_PBANDI_D_PAGINA_HELP PRIMARY KEY (ID_PAGINA_HELP)
);

/* ---------------------------------------------------------------------- */
/* Add table "PBANDI_T_HELP"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE PBANDI_T_HELP (
    ID_HELP NUMBER(6) CONSTRAINT NN_PBANDI_T_HELP_ID_HELP NOT NULL,
    ID_PAGINA_HELP NUMBER(6) NOT NULL,
    ID_GROUP_HELP NUMBER(3),
    TESTO_HELP CLOB,
    DT_INSERIMENTO DATE NOT NULL,
    DT_AGGIORNAMENTO DATE,
    ID_UTENTE_AGG NUMBER(8),
    CONSTRAINT PK_PBANDI_T_HELP PRIMARY KEY (ID_HELP)
);

CREATE INDEX IE1_PBANDI_T_HELP ON PBANDI_T_HELP (ID_PAGINA_HELP);

CREATE INDEX IE2_PBANDI_T_HELP ON PBANDI_T_HELP (ID_GROUP_HELP);

CREATE INDEX IE3_PBANDI_T_HELP ON PBANDI_T_HELP (ID_UTENTE_AGG);

/* ---------------------------------------------------------------------- */
/* Add table "PBANDI_R_GROUP_HELP_TIPO_ANAG"                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE PBANDI_R_GROUP_HELP_TIPO_ANAG (
    ID_GROUP_HELP NUMBER(3) NOT NULL,
    ID_TIPO_ANAGRAFICA NUMBER(3) NOT NULL,
    CONSTRAINT PK_PBANDI_R_GROUP_HELP_TIP_ANA PRIMARY KEY (ID_GROUP_HELP, ID_TIPO_ANAGRAFICA)
);

CREATE UNIQUE INDEX IE1_PBANDI_R_GROUP_HLP_TIP_ANA ON PBANDI_R_GROUP_HELP_TIPO_ANAG (ID_TIPO_ANAGRAFICA);

/* ---------------------------------------------------------------------- */
/* Foreign key constraints                                                */
/* ---------------------------------------------------------------------- */

ALTER TABLE PBANDI_T_HELP ADD CONSTRAINT FK_PBANDI_D_PAGINA_HELP_01 
    FOREIGN KEY (ID_PAGINA_HELP) REFERENCES PBANDI_D_PAGINA_HELP (ID_PAGINA_HELP);

ALTER TABLE PBANDI_T_HELP ADD CONSTRAINT FK_PBANDI_D_GROUP_HELP_02 
    FOREIGN KEY (ID_GROUP_HELP) REFERENCES PBANDI_D_GROUP_HELP (ID_GROUP_HELP);

ALTER TABLE PBANDI_T_HELP ADD CONSTRAINT FK_PBANDI_T_UTENTE_409 
    FOREIGN KEY (ID_UTENTE_AGG) REFERENCES PBANDI.PBANDI_T_UTENTE (ID_UTENTE);

ALTER TABLE PBANDI_R_GROUP_HELP_TIPO_ANAG ADD CONSTRAINT FK_PBANDI_D_GROUP_HELP_01 
    FOREIGN KEY (ID_GROUP_HELP) REFERENCES PBANDI_D_GROUP_HELP (ID_GROUP_HELP);

ALTER TABLE PBANDI_R_GROUP_HELP_TIPO_ANAG ADD CONSTRAINT FK_PBANDI_D_TIPO_ANAGRAFICA_13 
    FOREIGN KEY (ID_TIPO_ANAGRAFICA) REFERENCES PBANDI.PBANDI_D_TIPO_ANAGRAFICA (ID_TIPO_ANAGRAFICA);


-- SEQ
CREATE SEQUENCE SEQ_PBANDI_T_HELP
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;
			
-- 	PBANDI_D_ITER		
ALTER TABLE PBANDI_D_ITER ADD ID_TIPO_DICHIARAZ_SPESA number(3);
-- FK
ALTER TABLE PBANDI_D_ITER ADD (
  CONSTRAINT fk_pbandi_d_tipo_dich_spesa_02
  FOREIGN KEY (ID_TIPO_DICHIARAZ_SPESA) 
  REFERENCES PBANDI_D_TIPO_DICHIARAZ_SPESA (ID_TIPO_DICHIARAZ_SPESA));
-- IDX
CREATE INDEX ie_PBANDI_D_ITER_01 ON PBANDI_D_ITER (ID_TIPO_DICHIARAZ_SPESA) TABLESPACE PBANDI_IDX;

-- PBANDI_D_VOCE_DI_SPESA
ALTER TABLE PBANDI_D_VOCE_DI_SPESA ADD FLAG_SPESE_GESTIONE VARCHAR2(1);

-- PBANDI_D_FASE_MONIT
alter table PBANDI_D_FASE_MONIT drop column data_limite;


CREATE OR REPLACE VIEW PBANDI_V_SIF_INDICATORI_21_27 AS
      select p_sif.id_progetto,
	         d_sif.id_domanda,
			 bi_sif.id_indicatori,
             sum(di_perc.valore_prog_iniziale) as valore_prog_iniziale_sum,
             sum(di_perc.valore_concluso) as valore_concluso_sum,
             sum(di_perc.valore_prog_agg) as valore_prog_agg_sum
        from pbandi_t_progetto p_sif
             join pbandi_t_domanda d_sif on d_sif.id_domanda = p_sif.id_domanda
             join pbandi_r_bando_linea_intervent bli_sif on bli_sif.progr_bando_linea_intervento  = d_sif.progr_bando_linea_intervento
             join pbandi_r_bando_linea_intervent bli_perc on bli_perc.progr_bando_linea_interv_sif = bli_sif.progr_bando_linea_intervento 
             join pbandi_t_domanda d_perc on d_perc.progr_bando_linea_intervento  = bli_perc.progr_bando_linea_intervento
			 join pbandi_r_bando_indicatori bi_sif on bi_sif.id_bando = bli_sif.id_bando
			 left join pbandi_r_domanda_indicatori di_perc on di_perc.id_domanda = d_perc.id_domanda and di_perc.id_indicatori = bi_sif.id_indicatori
		where  bli_sif.flag_sif = 'S'
          and fn_linea_interv_radice (p_sif.id_progetto) = 'POR-FESR 2021-2027'
		  and di_perc.flag_non_applicabile != 'S'
        group by p_sif.id_progetto,d_sif.id_domanda,bi_sif.id_indicatori;

-- SEQ
CREATE SEQUENCE SEQ_PBANDI_T_AFFID_SERVTEC_AR
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;
  
/* ---------------------------------------------------------------------- */
/* Add table "PBANDI_T_AFFID_SERVTEC_AR"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE PBANDI_T_AFFID_SERVTEC_AR (
    ID_AFFID_SERVTEC NUMBER(8) NOT NULL,
    ID_RICHIESTA_EROGAZIONE NUMBER(8) NOT NULL,
    FLAG_AFFID_SERVTEC  VARCHAR2(1) NOT NULL,
    FORNITORE VARCHAR2(1000),
    SERVIZIO_AFFIDATO VARCHAR2(1000),
    DOCUMENTO  VARCHAR2(1000),
    NOME_FILE   VARCHAR2(4000),
    CONSTRAINT PK_PBANDI_T_AFFID_SERVTEC_AR PRIMARY KEY (ID_AFFID_SERVTEC)
);

CREATE INDEX IE1_PBANDI_T_AFFID_SERVTEC_AR ON PBANDI_T_AFFID_SERVTEC_AR (ID_RICHIESTA_EROGAZIONE);

ALTER TABLE PBANDI_T_AFFID_SERVTEC_AR ADD CONSTRAINT FK_PBANDI_T_RICH_EROGAZIONE_01 
    FOREIGN KEY (ID_RICHIESTA_EROGAZIONE) REFERENCES PBANDI_T_RICHIESTA_EROGAZIONE (ID_RICHIESTA_EROGAZIONE);

ALTER TABLE PBANDI_T_AFFID_SERVTEC_AR ADD CONSTRAINT CHK_PBANDI_T_AFF_SERVTEC_AR_1
    CHECK (FLAG_AFFID_SERVTEC in ('S','L'));

COMMENT ON TABLE PBANDI_T_AFFID_SERVTEC_AR IS 'Tabella affidamenti specifica per atchitetture rurali';
COMMENT ON COLUMN PBANDI_T_AFFID_SERVTEC_AR.FLAG_AFFID_SERVTEC IS 'S=Servizi tecnici, L=Lavori, servizi, forniture';



-------------------------------------------------
-- pbandi_d_ce_altri_costi
create table pbandi_d_ce_altri_costi
(id_d_ce_altri_costi number(6) not null,
 desc_breve_ce_altri_costi varchar2(100) not null,
 desc_ce_altri_costi varchar2(1000) not null,
 dt_inzio_validita date not null,
 dt_fine_validita date
);

-- pk
ALTER TABLE pbandi_d_ce_altri_costi  ADD 
(CONSTRAINT PK_pbandi_d_ce_altri_costi  PRIMARY KEY (id_d_ce_altri_costi)
USING INDEX TABLESPACE PBANDI_IDX);

-- seq
CREATE SEQUENCE SEQ_pbandi_d_ce_altri_costi
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;

-- pbandi_r_bando_ce_altri_costi
create table pbandi_r_bando_ce_altri_costi
(id_bando number(8) not null,
 id_d_ce_altri_costi number(6) not null
);

-- pk
ALTER TABLE pbandi_r_bando_ce_altri_costi  ADD 
(CONSTRAINT PK_pbandi_r_bando_ce_al_cost  PRIMARY KEY (id_bando,id_d_ce_altri_costi)
USING INDEX TABLESPACE PBANDI_IDX); 


-- FK
ALTER TABLE pbandi_r_bando_ce_altri_costi ADD (
  CONSTRAINT fk_pbandi_t_bando_11
  FOREIGN KEY (ID_BANDO) 
  REFERENCES PBANDI_T_BANDO(ID_BANDO));
  
-- FK
ALTER TABLE pbandi_r_bando_ce_altri_costi ADD (
  CONSTRAINT fk_pbandi_d_ce_altri_costi_01
  FOREIGN KEY (id_d_ce_altri_costi) 
  REFERENCES  pbandi_d_ce_altri_costi(id_d_ce_altri_costi));
  
-- IDX
CREATE INDEX IE1_pbandi_r_bando_ce_al_cost ON pbandi_r_bando_ce_altri_costi (id_d_ce_altri_costi) TABLESPACE PBANDI_IDX;
  
  
-- pbandi_t_ce_altri_costi
create table pbandi_t_ce_altri_costi
(
id_t_ce_altri_costi number(6) not null, 
id_progetto number(8) not null,
id_d_ce_altri_costi number(6) not null, 
imp_ce_approvato number (17,2),
imp_cd_propmod number (17,2)
);

-- pk
ALTER TABLE pbandi_t_ce_altri_costi  ADD 
(CONSTRAINT PK_pbandi_t_ce_altri_costi  PRIMARY KEY (id_t_ce_altri_costi)
USING INDEX TABLESPACE PBANDI_IDX); 

-- FK
ALTER TABLE pbandi_t_ce_altri_costi ADD (
  CONSTRAINT fk_pbandi_t_progetto_93
  FOREIGN KEY (ID_PROGETTO) 
  REFERENCES pbandi_t_progetto(ID_PROGETTO));
-- FK
ALTER TABLE pbandi_t_ce_altri_costi ADD (
  CONSTRAINT fk_pbandi_d_ce_altri_costi_02
  FOREIGN KEY (id_d_ce_altri_costi) 
  REFERENCES pbandi_d_ce_altri_costi(id_d_ce_altri_costi));

-- IDX
CREATE INDEX IE1_pbandi_t_ce_altri_costi ON pbandi_t_ce_altri_costi (id_progetto) TABLESPACE PBANDI_IDX;
CREATE INDEX IE2_pbandi_t_ce_altri_costi ON pbandi_t_ce_altri_costi (id_d_ce_altri_costi) TABLESPACE PBANDI_IDX;

-- seq
CREATE SEQUENCE SEQ_pbandi_t_ce_altri_costi
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;

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
                                                          'ADG-ISTRUTTORE',
                                                          'ISTR-AFFIDAMENTI'))
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
             SELECT   p.id_progetto,
                      ts.codice_fiscale_soggetto,
                      ts.id_soggetto,
                      sbli.id_tipo_anagrafica,
                      NULL AS progr_soggetto_progetto
               FROM   PBANDI_R_SOGG_BANDO_LIN_INT sbli,
                      PBANDI_T_SOGGETTO ts,
                      PBANDI_T_DOMANDA d,
                      PBANDI_T_PROGETTO p
              WHERE   ts.id_soggetto = sbli.id_soggetto
                      AND d.progr_bando_linea_intervento =
                            sbli.progr_bando_linea_intervento
                      AND p.id_domanda = d.id_domanda
                      AND NVL (TRUNC (sbli.dt_fine_validita),
                               TRUNC (SYSDATE + 1)) > TRUNC (SYSDATE)) m,
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
