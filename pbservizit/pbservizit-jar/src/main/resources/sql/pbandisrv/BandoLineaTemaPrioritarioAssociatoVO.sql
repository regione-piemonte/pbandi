/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT D.DESC_TEMA_PRIORITARIO,
  D.COD_IGRUE_T4,
  R.*
FROM PBANDI_R_BAN_LINEA_INT_TEM_PRI R,
  PBANDI_D_TEMA_PRIORITARIO D
WHERE R.ID_TEMA_PRIORITARIO = D.ID_TEMA_PRIORITARIO
AND NVL(TRUNC(D.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
AND NVL(TRUNC(R.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
