/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT D.DESC_IND_RISULTATO_PROGRAMMA,
  D.COD_IGRUE_T20,
  R.*
FROM PBANDI_R_BANDO_LIN_IND_RIS_PRO R,
  PBANDI_D_IND_RISULTATO_PROGRAM D
WHERE R.ID_IND_RISULTATO_PROGRAM = D.ID_IND_RISULTATO_PROGRAM
AND NVL(TRUNC(D.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)