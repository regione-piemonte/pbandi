/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

 SELECT tce.*,
    dsce.desc_breve_stato_conto_econom,
    dtce.desc_breve_tipologia_conto_eco,
    tp.id_progetto, 
	cema.quota_importo_agevolato
  FROM
    pbandi_t_conto_economico tce,
    pbandi_d_stato_conto_economico dsce,
    pbandi_d_tipologia_conto_econ dtce,
    pbandi_t_progetto tp,
    pbandi_r_conto_econom_mod_agev cema
  WHERE
    tce.id_stato_conto_economico = dsce.id_stato_conto_economico
    AND dsce.id_tipologia_conto_economico = dtce.id_tipologia_conto_economico
    AND tce.id_domanda = tp.id_domanda
    and tce.id_conto_economico = cema.id_conto_economico
  ORDER BY
    tce.id_conto_economico 