/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT pbandi_t_preview_dett_prop_cer.*,
  pbandi_d_linea_di_intervento.desc_linea,
  pbandi_d_linea_di_intervento.desc_breve_linea
FROM pbandi_t_preview_dett_prop_cer,
  pbandi_d_linea_di_intervento
WHERE pbandi_d_linea_di_intervento.id_linea_di_intervento(+) = pbandi_t_preview_dett_prop_cer.id_linea_di_intervento