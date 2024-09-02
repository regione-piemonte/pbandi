/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT SOG.DESC_SOGG_FINANZIATORE
  || NVL2(R.PERCENTUALE_QUOTA_SOGG_FINANZ,' - '
  || R.PERCENTUALE_QUOTA_SOGG_FINANZ
  || '%','') AS DESC_TABELLARE,
  SOG.DESC_SOGG_FINANZIATORE,
  R.*
FROM PBANDI_R_BANDO_SOGG_FINANZIAT R,
  PBANDI_D_SOGGETTO_FINANZIATORE SOG
WHERE R.ID_SOGGETTO_FINANZIATORE                       = SOG.ID_SOGGETTO_FINANZIATORE(+)
AND NVL(TRUNC(SOG.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)