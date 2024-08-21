/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT D.DESC_TIPO_TRATTAMENTO AS DESC_TABELLARE,
  D.DESC_BREVE_TIPO_TRATTAMENTO,
  D.DESC_TIPO_TRATTAMENTO,
  R.*
FROM PBANDI_R_BANDO_TIPO_TRATTAMENT R,
  PBANDI_D_TIPO_TRATTAMENTO D
WHERE R.ID_TIPO_TRATTAMENTO = D.ID_TIPO_TRATTAMENTO
AND NVL(TRUNC(D.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)