/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT R.*,
  D.DESC_BREVE_TIPO_AIUTO,
  D.DESC_TIPO_AIUTO,
  D.cod_igrue_t1
FROM PBANDI_D_TIPO_AIUTO D,
  PBANDI_R_BANDO_LIN_TIPO_AIUTO R
WHERE D.ID_TIPO_AIUTO                                = R.ID_TIPO_AIUTO
AND NVL(TRUNC(D.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
AND NVL(TRUNC(R.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)