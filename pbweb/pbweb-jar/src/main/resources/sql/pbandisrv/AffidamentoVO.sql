select rpa.id_progetto, app.*, tipoapp.desc_tipologia_appalto, agg.cig_proc_agg, agg.cod_proc_agg, stato.desc_stato_affidamento, rpa.id_tipo_documento_index
from pbandi_r_progetti_appalti rpa, pbandi_t_appalto app, 
     pbandi_d_tipologia_appalto tipoapp, pbandi_t_procedura_aggiudicaz agg,
     pbandi_d_stato_affidamento stato
where id_tipo_documento_index = 26
  and app.id_appalto = rpa.id_appalto
  and tipoapp.id_tipologia_appalto = app.id_tipologia_appalto
  and agg.id_procedura_aggiudicaz = app.id_procedura_aggiudicaz
  and stato.id_stato_affidamento(+) = app.id_stato_affidamento
order by stato.ordinamento, app.id_appalto