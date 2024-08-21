/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT DTA.*,
RSTA.ID_SOGGETTO,
DRH.DESC_RUOLO_HELP
FROM
PBANDI_D_TIPO_ANAGRAFICA dta,
PBANDI_D_RUOLO_HELP drh,
PBANDI_R_SOGG_TIPO_ANAGRAFICA rsta
WHERE
 RSTA.ID_TIPO_ANAGRAFICA=DTA.ID_TIPO_ANAGRAFICA
AND DTA.ID_RUOLO_HELP= DRH.ID_RUOLO_HELP(+)
AND NVL(TRUNC(dta.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
AND NVL(TRUNC(rsta.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)