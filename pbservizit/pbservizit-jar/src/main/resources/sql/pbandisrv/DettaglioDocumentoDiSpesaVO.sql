/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT DISTINCT PBANDI_T_DOCUMENTO_DI_SPESA.*,
  PBANDI_T_FORNITORE.CODICE_FISCALE_FORNITORE,
  PBANDI_R_DOC_SPESA_PROGETTO.ID_PROGETTO,
  PBANDI_T_PROGETTO.CODICE_VISUALIZZATO,
  PBANDI_T_FORNITORE.ID_TIPO_SOGGETTO,
  PBANDI_D_STATO_DOCUMENTO_SPESA.DESC_STATO_DOCUMENTO_SPESA,
  PBANDI_R_DOC_SPESA_PROGETTO.ID_STATO_DOCUMENTO_SPESA,
  PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_TIPO_DOCUMENTO_SPESA,
  PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_BREVE_TIPO_DOC_SPESA,
  PBANDI_R_DOC_SPESA_PROGETTO.IMPORTO_RENDICONTAZIONE,
  DECODE (PBANDI_T_DOCUMENTO_DI_SPESA.ID_FORNITORE,NULL,NULL,PBANDI_D_TIPO_SOGGETTO.DESC_TIPO_SOGGETTO) AS desc_tipologia_fornitore,
  PBANDI_T_FORNITORE.PARTITA_IVA_FORNITORE,
  PBANDI_R_DOC_SPESA_PROGETTO.TASK,
  PBANDI_R_DOC_SPESA_PROGETTO.NOTE_VALIDAZIONE ,
  PBANDI_R_DOC_SPESA_PROGETTO.TIPO_INVIO,
  PBANDI_R_DOC_SPESA_PROGETTO.ID_APPALTO
FROM PBANDI_T_DOCUMENTO_DI_SPESA,
  PBANDI_D_TIPO_DOCUMENTO_SPESA,
  PBANDI_D_STATO_DOCUMENTO_SPESA,
  PBANDI_D_TIPO_SOGGETTO,
  PBANDI_R_DOC_SPESA_PROGETTO,
  PBANDI_R_SOGGETTO_PROGETTO,
  PBANDI_T_FORNITORE,
  PBANDI_T_PROGETTO
WHERE PBANDI_R_DOC_SPESA_PROGETTO.ID_STATO_DOCUMENTO_SPESA = PBANDI_D_STATO_DOCUMENTO_SPESA.ID_STATO_DOCUMENTO_SPESA
AND PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA      = PBANDI_R_DOC_SPESA_PROGETTO.ID_DOCUMENTO_DI_SPESA
AND PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_DOCUMENTO_SPESA    = PBANDI_D_TIPO_DOCUMENTO_SPESA.ID_TIPO_DOCUMENTO_SPESA
AND PBANDI_T_DOCUMENTO_DI_SPESA.ID_FORNITORE               = PBANDI_T_FORNITORE.ID_FORNITORE(+)
AND PBANDI_R_DOC_SPESA_PROGETTO.ID_PROGETTO                =PBANDI_T_PROGETTO.ID_PROGETTO
AND PBANDI_R_SOGGETTO_PROGETTO.id_progetto                 =PBANDI_T_PROGETTO.id_progetto
AND ( PBANDI_T_DOCUMENTO_DI_SPESA.ID_FORNITORE            IS NULL
OR PBANDI_D_TIPO_SOGGETTO.ID_TIPO_SOGGETTO                 =PBANDI_T_FORNITORE.ID_TIPO_SOGGETTO)
AND (TRUNC(sysdate)                                       >= NVL(TRUNC(PBANDI_R_SOGGETTO_PROGETTO.DT_INIZIO_VALIDITA), TRUNC(sysdate)    -1)
AND TRUNC(sysdate)                                         < NVL(TRUNC(PBANDI_R_SOGGETTO_PROGETTO.DT_FINE_VALIDITA), TRUNC(sysdate)      +1))
AND (TRUNC(sysdate)                                       >= NVL(TRUNC(PBANDI_D_TIPO_SOGGETTO.DT_INIZIO_VALIDITA), TRUNC(sysdate)        -1)
AND TRUNC(sysdate)                                         < NVL(TRUNC(PBANDI_D_TIPO_SOGGETTO.DT_FINE_VALIDITA), TRUNC(sysdate)          +1))
AND (TRUNC(sysdate)                                       >= NVL(TRUNC(PBANDI_D_STATO_DOCUMENTO_SPESA.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1)
AND TRUNC(sysdate)                                         < NVL(TRUNC(PBANDI_D_STATO_DOCUMENTO_SPESA.DT_FINE_VALIDITA), TRUNC(sysdate)  +1))
AND (TRUNC(sysdate)                                       >= NVL(TRUNC(PBANDI_D_TIPO_DOCUMENTO_SPESA.DT_INIZIO_VALIDITA), TRUNC(sysdate) -1)
AND TRUNC(sysdate)                                         < NVL(TRUNC(PBANDI_D_TIPO_DOCUMENTO_SPESA.DT_FINE_VALIDITA), TRUNC(sysdate)   +1))