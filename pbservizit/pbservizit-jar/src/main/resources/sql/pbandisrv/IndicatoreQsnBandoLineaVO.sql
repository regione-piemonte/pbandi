/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select diq.*,
       rbliq.progr_bando_linea_intervento
  from pbandi_r_bando_lin_indicat_qsn rbliq,
       pbandi_d_indicatore_qsn diq
 where rbliq.id_indicatore_qsn = diq.id_indicatore_qsn