/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select r.PROGR_BANDO_LINEA_INTERVENTO, r.ID_DETT_TIPOL_AIUTI,
       d.DESCRIZIONE, d.CODICE, d.ID_TIPO_AIUTO,
       d.LINK, d.FLAG_FITTIZIO,
       t.COD_IGRUE_T1||' - '||t.DESC_TIPO_AIUTO DESCRIZIONE_TIPO_AIUTO
from pbandi_r_bandi_dett_tip_aiuti r, pbandi_d_dett_tipol_aiuti d, PBANDI_D_TIPO_AIUTO t
where d.ID_DETT_TIPOL_AIUTI = r.ID_DETT_TIPOL_AIUTI
  and t.ID_TIPO_AIUTO = d.ID_TIPO_AIUTO