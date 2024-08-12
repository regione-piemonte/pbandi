SELECT dstat.id_stato_proposta_certif,
dstat.desc_breve_stato_proposta_cert,
dstat.desc_stato_proposta_certif,
dstat.id_tipo_stato_prop_cert,
  case when dtipo.dt_inizio_validita > dstat.dt_inizio_validita then DTIPO.DT_INIZIO_VALIDITA
  else DSTAT.DT_INIZIO_VALIDITA
  end dt_inizio_validita,
  case when dtipo.dt_fine_validita < nvl(dstat.dt_fine_validita,dtipo.dt_fine_validita+1) then DTIPO.DT_FINE_VALIDITA
  else DSTAT.DT_FINE_VALIDITA
  end dt_fine_validita,
  dtipo.desc_breve_tipo_stato_pro_cert
FROM pbandi_d_stato_proposta_certif dstat,
  pbandi_d_tipo_stato_prop_cert dtipo
WHERE dstat.id_tipo_stato_prop_cert = dtipo.id_tipo_stato_prop_cert
AND (dtipo.desc_breve_tipo_stato_pro_cert LIKE 'adc'
OR dtipo.desc_breve_tipo_stato_pro_cert LIKE 'adg')