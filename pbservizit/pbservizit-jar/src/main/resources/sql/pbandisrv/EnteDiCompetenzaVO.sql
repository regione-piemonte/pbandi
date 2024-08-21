/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT ENTE.*,
  ente.desc_ente AS DESC_ENTE_DIREZIONE,
  TIPO.desc_breve_tipo_ente_competenz
FROM PBANDI_T_ENTE_COMPETENZA ENTE,
  pbandi_d_tipo_ente_competenza TIPO
WHERE ente.id_tipo_ente_competenza                         = tipo.id_tipo_ente_competenza
AND NVL(TRUNC(ENTE.DT_FINE_VALIDITA), TRUNC(SYSDATE     +1)) > TRUNC(SYSDATE)
AND NVL(TRUNC(TIPO.DT_FINE_VALIDITA), TRUNC(SYSDATE     +1)) > TRUNC(SYSDATE)