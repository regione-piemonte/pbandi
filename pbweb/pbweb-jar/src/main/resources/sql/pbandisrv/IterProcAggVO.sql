SELECT r.*,
  d.cod_igrue_t48,
  d.desc_step,
  d.id_tipologia_aggiudicaz
FROM pbandi_r_iter_proc_agg r,
  pbandi_d_step_aggiudicazione d
WHERE r.id_step_aggiudicazione                       = d.id_step_aggiudicazione
AND NVL(TRUNC(D.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
AND NVL(TRUNC(R.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
