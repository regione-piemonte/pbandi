select distinct 
       max(tce.DT_FINE_VALIDITA)  over (partition by dsce.desc_breve_stato_conto_econom ,
       dtce.desc_breve_tipologia_conto_eco,
       tp.id_progetto) DT_FINE_VALIDITA,
       first_value(tra.importo) over (partition by dsce.desc_breve_stato_conto_econom,
       dtce.desc_breve_tipologia_conto_eco,
       tp.id_progetto order by tce.DT_FINE_VALIDITA desc) importo_ribasso_asta,
       first_value(tra.percentuale) over (partition by dsce.desc_breve_stato_conto_econom,
       dtce.desc_breve_tipologia_conto_eco,
       tp.id_progetto order by tce.DT_FINE_VALIDITA desc) perc_ribasso_asta,
       dsce.desc_breve_stato_conto_econom,
       dtce.desc_breve_tipologia_conto_eco,
       tp.id_progetto,
       first_value(tra.id_procedura_aggiudicaz)  over (partition by dsce.desc_breve_stato_conto_econom,
       dtce.desc_breve_tipologia_conto_eco,
       tp.id_progetto order by tce.DT_FINE_VALIDITA desc) id_procedura_aggiudicaz
  from pbandi_t_conto_economico tce,
       pbandi_d_stato_conto_economico dsce,
       pbandi_d_tipologia_conto_econ dtce,
       pbandi_t_progetto tp,
       pbandi_t_ribasso_asta tra
 where tce.id_stato_conto_economico = dsce.id_stato_conto_economico
   and dsce.id_tipologia_conto_economico = dtce.id_tipologia_conto_economico
   and tce.id_domanda = tp.id_domanda
   and tce.id_conto_economico = tra.id_conto_economico (+)
   and tce.DT_FINE_VALIDITA is not null
order by DT_FINE_VALIDITA desc