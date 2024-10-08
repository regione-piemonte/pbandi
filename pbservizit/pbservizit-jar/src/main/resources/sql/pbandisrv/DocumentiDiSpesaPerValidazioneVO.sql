/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT DISTINCT 
  /*PBANDI_D_STATO_VALIDAZ_SPESA.DESC_BREVE_STATO_VALIDAZ_SPESA AS DESC_BREVE_STATO_VALIDAZ_SPESA,*/
  PBANDI_T_DICHIARAZIONE_SPESA.id_dichiarazione_spesa                       AS ID_DICHIARAZIONE_SPESA,
  PBANDI_T_DOCUMENTO_DI_SPESA.dt_emissione_documento                        AS data_Documento_Di_Spesa ,
  PBANDI_D_TIPO_DOCUMENTO_SPESA.desc_tipo_documento_spesa                   AS DESC_TIPO_DOCUMENTO_SPESA,
  PBANDI_T_DOCUMENTO_DI_SPESA.id_documento_di_spesa                         AS id_Documento_Di_Spesa ,
  PBANDI_T_DOCUMENTO_DI_SPESA.id_doc_riferimento                            AS id_Doc_Di_Riferimento,
  PBANDI_T_DOCUMENTO_DI_SPESA.id_fornitore                                  AS id_Fornitore,
  PBANDI_T_DOCUMENTO_DI_SPESA.id_tipo_documento_spesa                       AS id_Tipo_Documento_Di_Spesa ,
  PBANDI_T_DOCUMENTO_DI_SPESA.numero_documento                              AS numero_Documento_Di_Spesa,
  PBANDI_T_DOCUMENTO_DI_SPESA.importo_totale_documento                      AS importo_Totale_Documento,
  PBANDI_R_DOC_SPESA_PROGETTO.id_progetto                                   AS id_Progetto,
  PBANDI_R_DOC_SPESA_PROGETTO.importo_rendicontazione                       AS importo_Rendicontabile,
  PBANDI_R_DOC_SPESA_PROGETTO.id_stato_documento_spesa,
  PBANDI_D_STATO_DOCUMENTO_SPESA.desc_breve_stato_doc_spesa,
  PBANDI_D_STATO_DOCUMENTO_SPESA.desc_stato_documento_spesa,
  PBANDI_R_DOC_SPESA_PROGETTO.id_stato_documento_spesa_valid,
  (SELECT SUM(TDS1.IMPORTO_TOTALE_DOCUMENTO)
  FROM PBANDI_T_DOCUMENTO_DI_SPESA TDS1
  WHERE TDS1.ID_DOC_RIFERIMENTO = PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA
  ) AS totale_Note_Credito,
  (SELECT SUM(PBANDI_T_QUOTA_PARTE_DOC_SPESA.importo_quota_parte_doc_spesa)
  FROM PBANDI_T_QUOTA_PARTE_DOC_SPESA
  WHERE PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_documento_di_spesa = PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA
  AND PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_progetto             = PBANDI_R_DOC_SPESA_PROGETTO.ID_PROGETTO
  ) AS totale_Importo_Quota_Parte ,
  (SELECT SUM (tp1.importo_pagamento)
  FROM pbandi_t_pagamento tp1,
    pbandi_r_pagamento_doc_spesa rds1
  WHERE tp1.id_pagamento        = rds1.id_pagamento
  AND rds1.id_documento_di_spesa=pbandi_t_documento_di_spesa.id_documento_di_spesa
  ) AS totale_tutti_pagamenti
FROM PBANDI_T_DOCUMENTO_DI_SPESA,
  PBANDI_D_STATO_DOCUMENTO_SPESA,
  PBANDI_D_TIPO_DOCUMENTO_SPESA,
  PBANDI_R_SOGGETTO_PROGETTO,
  PBANDI_R_DOC_SPESA_PROGETTO,
  PBANDI_T_PAGAMENTO ,
  PBANDI_T_DICHIARAZIONE_SPESA,
  /*PBANDI_D_STATO_VALIDAZ_SPESA ,*/
  PBANDI_R_PAGAMENTO_DOC_SPESA,
  PBANDI_R_PAGAMENTO_DICH_SPESA
WHERE PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA                        = PBANDI_R_DOC_SPESA_PROGETTO.ID_DOCUMENTO_DI_SPESA
AND PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_DOCUMENTO_SPESA                        = PBANDI_D_TIPO_DOCUMENTO_SPESA.ID_TIPO_DOCUMENTO_SPESA
AND PBANDI_R_DOC_SPESA_PROGETTO.ID_STATO_DOCUMENTO_SPESA                       = PBANDI_D_STATO_DOCUMENTO_SPESA.ID_STATO_DOCUMENTO_SPESA
AND PBANDI_R_DOC_SPESA_PROGETTO.ID_PROGETTO                                    = PBANDI_R_SOGGETTO_PROGETTO.id_progetto
AND PBANDI_R_PAGAMENTO_DOC_SPESA.ID_DOCUMENTO_DI_SPESA                         = PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA
AND PBANDI_T_PAGAMENTO.ID_PAGAMENTO                                            = PBANDI_R_PAGAMENTO_DOC_SPESA.ID_PAGAMENTO
/*AND PBANDI_T_PAGAMENTO.ID_STATO_VALIDAZIONE_SPESA_BCK                        = PBANDI_D_STATO_VALIDAZ_SPESA.ID_STATO_VALIDAZIONE_SPESA*/
AND PBANDI_R_PAGAMENTO_DICH_SPESA.ID_PAGAMENTO                                 = PBANDI_T_PAGAMENTO.ID_PAGAMENTO
AND pbandi_t_dichiarazione_spesa.id_dichiarazione_spesa                        = pbandi_r_pagamento_dich_spesa.id_dichiarazione_spesa
and pbandi_t_dichiarazione_spesa.id_progetto                                   = PBANDI_R_DOC_SPESA_PROGETTO.ID_PROGETTO
AND NVL(TRUNC(PBANDI_R_SOGGETTO_PROGETTO.DT_FINE_VALIDITA), TRUNC(sysdate    +1)) > TRUNC(sysdate)
AND NVL(TRUNC(PBANDI_D_STATO_DOCUMENTO_SPESA.DT_FINE_VALIDITA), TRUNC(sysdate+1)) > TRUNC(sysdate)
AND NVL(TRUNC(PBANDI_D_TIPO_DOCUMENTO_SPESA.DT_FINE_VALIDITA), TRUNC(SYSDATE +1)) > TRUNC(SYSDATE)
/*AND NVL(TRUNC(PBANDI_D_STATO_VALIDAZ_SPESA.DT_FINE_VALIDITA), TRUNC(SYSDATE  +1)) > TRUNC(SYSDATE)*/
GROUP BY PBANDI_T_DOCUMENTO_DI_SPESA.DT_EMISSIONE_DOCUMENTO,
  PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_TIPO_DOCUMENTO_SPESA,
  PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA,
  PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOC_RIFERIMENTO,
  PBANDI_T_DOCUMENTO_DI_SPESA.ID_FORNITORE,
  PBANDI_R_SOGGETTO_PROGETTO.ID_SOGGETTO,
  PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_DOCUMENTO_SPESA,
  PBANDI_T_DOCUMENTO_DI_SPESA.NUMERO_DOCUMENTO,
  PBANDI_T_DOCUMENTO_DI_SPESA.IMPORTO_TOTALE_DOCUMENTO,
  PBANDI_R_DOC_SPESA_PROGETTO.ID_PROGETTO,
  PBANDI_R_DOC_SPESA_PROGETTO.IMPORTO_RENDICONTAZIONE,
  PBANDI_T_DICHIARAZIONE_SPESA.ID_DICHIARAZIONE_SPESA,
  PBANDI_R_DOC_SPESA_PROGETTO.ID_STATO_DOCUMENTO_SPESA,
  PBANDI_D_STATO_DOCUMENTO_SPESA.DESC_BREVE_STATO_DOC_SPESA,
  PBANDI_D_STATO_DOCUMENTO_SPESA.DESC_STATO_DOCUMENTO_SPESA,
  PBANDI_R_DOC_SPESA_PROGETTO.id_stato_documento_spesa_valid
  /*,PBANDI_D_STATO_VALIDAZ_SPESA.DESC_BREVE_STATO_VALIDAZ_SPESA*/