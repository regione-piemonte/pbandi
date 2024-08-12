SELECT DISTINCT PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa,
  PBANDI_R_DOC_SPESA_PROGETTO.IMPORTO_RENDICONTAZIONE,
  PBANDI_R_DOC_SPESA_PROGETTO.TASK,
  pbandi_t_documento_di_spesa.id_tipo_documento_spesa,
  PBANDI_R_DOC_SPESA_PROGETTO.id_stato_documento_spesa,
  PBANDI_D_STATO_DOCUMENTO_SPESA.DESC_STATO_DOCUMENTO_SPESA,
  PBANDI_D_STATO_DOCUMENTO_SPESA.DESC_BREVE_STATO_DOC_SPESA,
  (NVL(pbandi_t_documento_di_spesa.imponibile, 0) + NVL(pbandi_t_documento_di_spesa.importo_iva_costo, 0)) massimo_rendicontabile,
  PBANDI_T_PROGETTO.id_progetto,
  PBANDI_T_PROGETTO.codice_progetto,
  PBANDI_T_PROGETTO.CODICE_VISUALIZZATO,
  /*CASE
    WHEN EXISTS
      (SELECT
        CASE
          WHEN (PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa IN
            (SELECT id_documento_di_spesa FROM pbandi_r_pagamento_doc_spesa
            ))
          THEN
            (SELECT 'x'
            FROM pbandi_r_pagamento_doc_spesa rpds,
              pbandi_t_pagamento pag
            WHERE rpds.id_documento_di_spesa        = PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa
            AND pag.id_pagamento                    = rpds.id_pagamento
            AND pag.id_stato_validazione_spesa NOT IN
              (SELECT pbandi_d_stato_validaz_spesa.id_stato_validazione_spesa
              FROM pbandi_d_stato_validaz_spesa
              WHERE pbandi_d_stato_validaz_spesa.desc_breve_stato_validaz_spesa NOT LIKE 'I'
              AND pbandi_d_stato_validaz_spesa.desc_breve_stato_validaz_spesa NOT LIKE 'T'
              )
            )
          ELSE 'x'
        END
      FROM DUAL
      )
    THEN 'S'
    ELSE 'N'
  END flag_pag_modificabili,*/
  CASE
    WHEN EXISTS
      (SELECT 'x'
      FROM pbandi_t_documento_di_spesa doc_rif,
           pbandi_r_doc_spesa_progetto docprj
      WHERE doc_rif.id_doc_riferimento = PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa
        and docprj.id_progetto = PBANDI_R_DOC_SPESA_PROGETTO.id_progetto
        and docprj.id_documento_di_spesa = doc_rif.id_documento_di_spesa
      )
    THEN 'S'
    ELSE 'N'
  END flag_doc_riferito
FROM PBANDI_R_DOC_SPESA_PROGETTO,
  PBANDI_T_PROGETTO,
  pbandi_t_documento_di_spesa,
  PBANDI_D_STATO_DOCUMENTO_SPESA
WHERE PBANDI_R_DOC_SPESA_PROGETTO.ID_PROGETTO         = PBANDI_T_PROGETTO.ID_PROGETTO
AND PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA = PBANDI_R_DOC_SPESA_PROGETTO.ID_DOCUMENTO_DI_SPESA
and PBANDI_D_STATO_DOCUMENTO_SPESA.ID_STATO_DOCUMENTO_SPESA = PBANDI_R_DOC_SPESA_PROGETTO.ID_STATO_DOCUMENTO_SPESA