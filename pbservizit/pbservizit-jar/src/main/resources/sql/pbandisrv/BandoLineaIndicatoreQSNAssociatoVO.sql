/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT D.DESC_INDICATORE_QSN,
  D.COD_IGRUE_T12,
  R.*
FROM PBANDI_R_BANDO_LIN_INDICAT_QSN R,
  PBANDI_D_INDICATORE_QSN D
WHERE R.ID_INDICATORE_QSN = D.ID_INDICATORE_QSN
AND NVL(TRUNC(D.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)