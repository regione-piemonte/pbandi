/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT D.DESC_BREVE_TIPO_PERIODO,
  D.DESC_TIPO_PERIODO,
  R.*
FROM PBANDI_R_BANDO_LIN_TIPO_PERIOD R,
  PBANDI_D_TIPO_PERIODO D
WHERE R.ID_TIPO_PERIODO = D.ID_TIPO_PERIODO