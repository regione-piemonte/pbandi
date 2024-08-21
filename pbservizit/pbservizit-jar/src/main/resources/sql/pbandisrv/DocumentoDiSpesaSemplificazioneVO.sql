/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT distinct nvl2(pbandi_t_fornitore.partita_iva_fornitore, PBANDI_T_FORNITORE.DENOMINAZIONE_FORNITORE
    || chr(10)
    || PBANDI_T_FORNITORE.PARTITA_IVA_FORNITORE,
                     nvl2(PBANDI_T_FORNITORE.DENOMINAZIONE_FORNITORE, PBANDI_T_FORNITORE.DENOMINAZIONE_FORNITORE
                         || chr(10)
                         || PBANDI_T_FORNITORE.codice_fiscale_fornitore, PBANDI_T_FORNITORE.cognome_fornitore
                              || ' '
                              || PBANDI_T_FORNITORE.nome_fornitore
                              || chr(10)
                              || PBANDI_T_FORNITORE.codice_fiscale_fornitore))                  AS FORNITORE_TABELLA,

                PBANDI_T_DOCUMENTO_DI_SPESA.DT_EMISSIONE_DOCUMENTO
                    || chr(10)
                    || PBANDI_T_DOCUMENTO_DI_SPESA.NUMERO_DOCUMENTO                             AS ESTREMI_TABELLA,
                PBANDI_T_FORNITORE.codice_fiscale_fornitore                                     AS CODICE_FISCALE_FORNITORE,
                ''                                                                              AS IMPORTI_TOTALI_DOCUMENTI_IVATI,
                PBANDI_T_DOCUMENTO_DI_SPESA.id_fornitore                                        AS ID_FORNITORE,
                rdsp.tipo_invio                                                                 AS TIPO_INVIO,
                PBANDI_D_VOCE_DI_SPESA.ID_TIPOLOGIA_VOCE_DI_SPESA                               as ID_CATEGORIA,
                PBANDI_T_DOCUMENTO_DI_SPESA.id_documento_di_spesa                               AS ID_DOCUMENTO_DI_SPESA,
                PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOC_RIFERIMENTO                                  AS ID_DOC_RIFERIMENTO,
                PBANDI_T_DOCUMENTO_DI_SPESA.dt_emissione_documento                              AS DATA_DOCUMENTO_DI_SPESA,
                PBANDI_D_STATO_DOCUMENTO_SPESA.id_stato_documento_spesa                         AS ID_STATO_DOCUMENTO_SPESA,
                PBANDI_D_STATO_DOCUMENTO_SPESA.desc_breve_stato_doc_spesa                       AS DESC_BREVE_STATO_DOC_SPESA,
                PBANDI_D_STATO_DOCUMENTO_SPESA.DESC_STATO_DOCUMENTO_SPESA                       AS DESC_STATO_DOCUMENTO_SPESA,
                PBANDI_D_TIPO_DOCUMENTO_SPESA.desc_tipo_documento_spesa                         AS DESC_TIPO_DOCUMENTO_DI_SPESA,
                rdsp.id_soggetto                                                                AS ID_SOGGETTO,
                PBANDI_T_DOCUMENTO_DI_SPESA.id_tipo_documento_spesa                             AS ID_TIPO_DOCUMENTO_DI_SPESA,
                PBANDI_T_DOCUMENTO_DI_SPESA.importo_totale_documento                            AS IMPORTO_TOTALE_DOCUMENTO_IVATO,
                PBANDI_T_DOCUMENTO_DI_SPESA.numero_documento                                    AS NUMERO_DOCUMENTO,
                rdsp.id_progetto                                                                AS ID_PROGETTO,
                (SELECT codice_progetto
                 FROM pbandi_t_progetto
                 WHERE id_progetto = rdsp.id_progetto)                                          AS CODICE_PROGETTO,
                rdsp.flag_gest_in_progetto                                                      AS FLAG_GEST_IN_PROGETTO,
                rdsp.importo_totale_validato                                                    AS IMPORTO_TOTALE_VALIDATO,
                pbandi_t_rigo_conto_economico.id_voce_di_spesa                                  AS ID_VOCE_DI_SPESA,
                'S'                                                                             AS FLAG_MODIFICABILE,
                DECODE(PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_BREVE_TIPO_DOC_SPESA, 'NC', 'N', 'S') AS FLAG_CLONABILE,
                'S'                                                                             AS FLAG_ELIMINABILE,
                'S'                                                                             AS FLAG_ASSOCIABILE,
                'S'                                                                             AS FLAG_ASSOCIATO,
                CASE
                    WHEN EXISTS
                        (select 'x'
                         from PBANDI_T_FILE_ENTITA
                         where PBANDI_T_FILE_ENTITA.ID_TARGET = PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA
                           and PBANDI_T_FILE_ENTITA.ID_ENTITA =
                               (select ID_ENTITA from PBANDI_C_ENTITA where NOME_ENTITA = 'PBANDI_T_DOCUMENTO_DI_SPESA')
                           and rdsp.id_progetto = PBANDI_T_FILE_ENTITA.id_progetto)
                        THEN 'S'
                    ELSE 'N'
                    END                                                                            flag_allegati
        ,
                nvl((SELECT SUM(tp1.importo_pagamento)
                     FROM pbandi_t_pagamento tp1,
                          pbandi_r_pagamento_doc_spesa rds1
                     WHERE tp1.id_pagamento = rds1.id_pagamento
                       AND RDS1.ID_DOCUMENTO_DI_SPESA =
                           PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA), 0)
                                                                                                AS TOTALE_PAGAMENTI,
                nvl((SELECT SUM(tds1.importo_totale_documento)
                     FROM pbandi_t_documento_di_spesa tds1
                     WHERE TDS1.ID_DOC_RIFERIMENTO =
                           PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA), 0)
                                                                                                AS totale_note_credito

FROM PBANDI_T_DOCUMENTO_DI_SPESA,
     PBANDI_D_TIPO_DOCUMENTO_SPESA,
     PBANDI_D_TIPO_SOGGETTO,
     PBANDI_T_FORNITORE,
     PBANDI_D_STATO_DOCUMENTO_SPESA,
     (SELECT DISTINCT rsp1.id_progetto,
                      docProgBen.tipo_invio,
                      docProgBen.id_documento_di_spesa,
                      DOCPROGBEN.ID_SOGGETTO,
                      docProgBen.ID_STATO_DOCUMENTO_SPESA,
                      CASE
                          WHEN NOT EXISTS
                              (SELECT 'x'
                               FROM pbandi_r_doc_spesa_progetto r1
                               WHERE r1.id_progetto = docProgBen.id_progetto
                                 AND r1.id_documento_di_spesa = docProgBen.id_documento_di_spesa
                                 and docProgBen.id_progetto = rsp1.id_progetto)
                              THEN 'N'
                          ELSE 'S'
                          END flag_gest_in_progetto,
                      CASE
                          WHEN NOT EXISTS
                              (SELECT 'd'
                               FROM pbandi_r_doc_spesa_progetto r1
                               WHERE r1.id_progetto = docProgBen.id_progetto
                                 AND r1.id_documento_di_spesa = docProgBen.id_documento_di_spesa
                                 and docProgBen.id_progetto = rsp1.id_progetto)
                              THEN NULL
                          ELSE NVL(
                                  (SELECT SUM(pagquotdocs.importo_validato) AS importo_totale_validato
                                   FROM PBANDI_R_PAGAMENTO_DOC_SPESA pdocs,
                                        PBANDI_T_QUOTA_PARTE_DOC_SPESA qpDocs,
                                        PBANDI_R_PAG_QUOT_PARTE_DOC_SP pagQuotDocs
                                   WHERE qpdocs.id_documento_di_spesa = pdocs.id_documento_di_spesa
                                     AND qpdocs.id_progetto = docProgBen.id_progetto
                                     AND pagquotdocs.id_quota_parte_doc_spesa = qpdocs.id_quota_parte_doc_spesa
                                     AND pagquotdocs.id_pagamento = pdocs.id_pagamento
                                     AND pdocs.id_documento_di_spesa (+) = docProgBen.id_documento_di_spesa
                                   GROUP BY docProgBen.id_progetto,
                                            docProgBen.id_documento_di_spesa), 0)
                          END importo_totale_validato
      FROM (SELECT rdsp.id_documento_di_spesa,
                   rdsp.tipo_invio,
                   rdsp.id_progetto,
                   rdsp.task,
                   rsp.id_soggetto,
                   RSP.ID_ENTE_GIURIDICO,
                   RSP.ID_PERSONA_FISICA,
                   RDSP.ID_STATO_DOCUMENTO_SPESA
            FROM pbandi_r_doc_spesa_progetto rdsp,
                 pbandi_r_soggetto_progetto rsp
            WHERE rdsp.id_progetto = rsp.id_progetto
              AND rsp.id_tipo_beneficiario <> 4
              AND NVL(TRUNC(rsp.DT_FINE_VALIDITA), TRUNC(sysdate + 1)) > TRUNC(sysdate)) docProgBen,
           pbandi_r_soggetto_progetto rsp1
      WHERE docProgBen.id_soggetto = rsp1.id_soggetto) rdsp,
     pbandi_t_quota_parte_doc_spesa,
     PBANDI_T_RIGO_CONTO_ECONOMICO,
     PBANDI_D_VOCE_DI_SPESA
WHERE rdsp.ID_STATO_DOCUMENTO_SPESA                 = PBANDI_D_STATO_DOCUMENTO_SPESA.ID_STATO_DOCUMENTO_SPESA
AND PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA                      = rdsp.ID_DOCUMENTO_DI_SPESA
AND PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_DOCUMENTO_SPESA                    = PBANDI_D_TIPO_DOCUMENTO_SPESA.ID_TIPO_DOCUMENTO_SPESA
AND PBANDI_T_DOCUMENTO_DI_SPESA.ID_FORNITORE                               = PBANDI_T_FORNITORE.ID_FORNITORE(+)
AND ( pbandi_t_documento_di_spesa.id_fornitore                            IS NULL
OR pbandi_d_tipo_soggetto.id_tipo_soggetto                                 = pbandi_t_fornitore.id_tipo_soggetto )
AND pbandi_t_quota_parte_doc_spesa.id_documento_di_spesa (+)               = rdsp.ID_DOCUMENTO_DI_SPESA
AND PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_rigo_conto_economico                 = PBANDI_T_RIGO_CONTO_ECONOMICO.id_rigo_conto_economico (+)
AND NVL(TRUNC(PBANDI_D_TIPO_SOGGETTO.DT_FINE_VALIDITA), TRUNC(sysdate        +1)) > TRUNC(sysdate)
AND NVL(TRUNC(PBANDI_D_STATO_DOCUMENTO_SPESA.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
AND NVL(TRUNC(PBANDI_D_TIPO_DOCUMENTO_SPESA.DT_FINE_VALIDITA), TRUNC(SYSDATE +1)) > TRUNC(SYSDATE)
AND PBANDI_T_RIGO_CONTO_ECONOMICO.ID_VOCE_DI_SPESA = PBANDI_D_VOCE_DI_SPESA.ID_VOCE_DI_SPESA (+)

