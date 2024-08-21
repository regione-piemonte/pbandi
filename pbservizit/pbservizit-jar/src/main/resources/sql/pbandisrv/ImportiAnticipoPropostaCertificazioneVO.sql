/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT T.ID_PROPOSTA_CERTIFICAZ,
  d.desc_breve_linea, d.id_linea_di_intervento,
  SUM(r.importo_anticipo) importo_anticipo
FROM pbandi_t_dett_proposta_certif t,
  pbandi_r_dett_prop_cer_lin_ant r,
  pbandi_d_linea_di_intervento d
WHERE t.id_dett_proposta_certif = r.id_dett_proposta_certif
AND R.ID_LINEA_DI_INTERVENTO    = D.ID_LINEA_DI_INTERVENTO
AND (TRUNC(sysdate)            >= NVL(TRUNC(d.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1)
AND TRUNC(sysdate)              < NVL(TRUNC(d.DT_FINE_VALIDITA), TRUNC(sysdate)  +1))
GROUP BY d.desc_breve_linea,
  T.ID_PROPOSTA_CERTIFICAZ,
  d.ID_LINEA_DI_INTERVENTO