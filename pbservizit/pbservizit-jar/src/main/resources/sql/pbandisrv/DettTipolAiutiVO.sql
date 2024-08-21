/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select d.ID_DETT_TIPOL_AIUTI, d.DESCRIZIONE, d.CODICE,
       d.ID_TIPO_AIUTO, t.COD_IGRUE_T1||' - '||t.DESC_TIPO_AIUTO as DESCRIZIONE_TIPO_AIUTO,
       d.FLAG_FITTIZIO, d.LINK, r.PROGR_BANDO_LINEA_INTERVENTO
from PBANDI_R_BANDO_LIN_TIPO_AIUTO r, PBANDI_D_DETT_TIPOL_AIUTI d, PBANDI_D_TIPO_AIUTO t
where d.ID_TIPO_AIUTO = r.ID_TIPO_AIUTO
  and t.ID_TIPO_AIUTO = d.ID_TIPO_AIUTO
order by d.CODICE, d.DESCRIZIONE