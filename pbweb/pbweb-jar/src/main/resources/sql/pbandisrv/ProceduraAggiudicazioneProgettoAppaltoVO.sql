select distinct pa.id_procedura_aggiudicaz,
       pa.cod_proc_agg,
       pa.desc_proc_agg,
       pa.importo,
       pa.id_tipologia_aggiudicaz,
       ta.cod_igrue_t47,
       ta.desc_tipologia_aggiudicazione,
       pa.id_progetto,
       pa.iva,
       pa.cig_proc_agg,
       pa.id_motivo_assenza_cig,
       pa.dt_aggiudicazione,
       nvl(pa.cig_proc_agg, pa.cod_proc_agg) as codice
from pbandi_t_procedura_aggiudicaz pa,
  pbandi_d_tipologia_aggiudicaz ta
where pa.id_tipologia_aggiudicaz = ta.id_tipologia_aggiudicaz
and nvl(trunc(pa.dt_fine_validita), trunc(sysdate+1)) > trunc(sysdate)