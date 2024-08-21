/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select p.id_progetto ,
  periodo.*
from PBANDI_T_PROGETTO p,
  PBANDI_T_DOMANDA d,
  pbandi_r_bando_lin_tipo_period rbltp,
  pbandi_t_periodo periodo
where p.id_domanda                     = d.id_domanda
and rbltp.progr_bando_linea_intervento = d.progr_bando_linea_intervento
and rbltp.id_tipo_periodo              = periodo.id_tipo_periodo