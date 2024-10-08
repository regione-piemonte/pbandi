/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT R.*,
  D.ID_VOCE_DI_SPESA_PADRE
FROM PBANDI_D_VOCE_DI_SPESA D,
  PBANDI_R_BANDO_VOCE_SPESA R
WHERE D.ID_VOCE_DI_SPESA                             = R.ID_VOCE_DI_SPESA
AND NVL(TRUNC(D.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)