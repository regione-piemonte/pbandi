/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select tce.*,
       dsce.desc_breve_stato_conto_econom,
       dtce.desc_breve_tipologia_conto_eco,
       tp.id_progetto
  from pbandi_t_conto_economico tce,
       pbandi_d_stato_conto_economico dsce,
       pbandi_d_tipologia_conto_econ dtce,
       pbandi_t_progetto tp
 where tce.id_stato_conto_economico = dsce.id_stato_conto_economico
   and dsce.id_tipologia_conto_economico = dtce.id_tipologia_conto_economico
   and tce.id_domanda = tp.id_domanda
order by tce.id_conto_economico