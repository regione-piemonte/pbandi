select rce.id_conto_economico,
       rce.id_rigo_conto_economico,
       p.id_progetto,
       sce.id_stato_conto_economico,
       sce.desc_breve_stato_conto_econom,
       sce.desc_stato_conto_economico,
       tce.id_tipologia_conto_economico,
       tce.desc_breve_tipologia_conto_eco,
       tce.desc_tipologia_conto_economico,
       rce.importo_ammesso_finanziamento,
       rce.importo_contributo,
       rce.importo_spesa
from pbandi_t_rigo_conto_economico rce,
     pbandi_t_conto_economico ce,
     pbandi_t_progetto p,
     pbandi_d_stato_conto_economico sce,
     pbandi_d_tipologia_conto_econ tce
where ce.id_conto_economico = rce.id_conto_economico 
  and ce.id_domanda = p.id_domanda
  and ce.id_stato_conto_economico = sce.id_stato_conto_economico
  and sce.id_tipologia_conto_economico = tce.id_tipologia_conto_economico
  and nvl(trunc(rce.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)
  and nvl(trunc(ce.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)