/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


-- PBANDI_T_DOCUMENTO_INDEX
alter table PBANDI_T_DOCUMENTO_INDEX add FLAG_FIRMA_AUTOGRAFA varchar2(1);
alter table PBANDI_T_DOCUMENTO_INDEX
  add constraint CHK_FLAG_FIRMA_AUTOGRAFA
  check (FLAG_FIRMA_AUTOGRAFA IN ('S', NULL));
--

CREATE OR REPLACE FORCE VIEW PBANDI.PBANDI_V_DOC_INDEX_BATCH
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
               WHEN DESC_BREVE IN ('INVIATO') AND FLAG_FIRMA_CARTACEA = 'N'
                    AND (DT_MARCA_TEMPORALE IS NOT NULL
                         OR FLAG_FIRMA_AUTOGRAFA = 'S')
               THEN
                  'DA_CLASSIFICARE'
               WHEN DESC_BREVE IN ('CLASSIFICATO')
                    AND FLAG_FIRMA_CARTACEA = 'N'
                    AND (DT_MARCA_TEMPORALE IS NOT NULL
                         OR FLAG_FIRMA_AUTOGRAFA = 'S')
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
            ben.DENOMINAZIONE_ENTE_GIURIDICO DENOMINAZIONE_BENEFICIARIO,
            ben.CODICE_FISCALE_SOGGETTO CODICE_FISCALE_BENEFICIARIO,
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
                                          PBANDI_T_PROGETTO
                                       USING (ID_PROGETTO)
                                    JOIN
                                       PBANDI_T_DOMANDA
                                    USING (ID_DOMANDA)
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
    WHERE       PBANDI_C_TIPO_DOCUMENTO_INDEX.FLAG_FIRMABILE = 'S'
            AND blec.DESC_BREVE_RUOLO_ENTE = 'DESTINATARIO'
            AND PBANDI_R_REGOLA_BANDO_LINEA.ID_REGOLA = 42;

