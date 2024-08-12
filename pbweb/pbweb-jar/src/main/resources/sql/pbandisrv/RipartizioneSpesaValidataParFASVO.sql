SELECT r.id_proposta_certificaz,
  r.spesa_validata,
  (SELECT linp.desc_breve_linea
  FROM pbandi_d_linea_di_intervento linp
  WHERE linp.id_linea_di_intervento = lin.id_linea_di_intervento_padre
  AND (TRUNC(sysdate)              >= NVL(TRUNC(lin.DT_INIZIO_VALIDITA), TRUNC(sysdate) -1)
  AND TRUNC(sysdate)                < NVL(TRUNC(lin.DT_FINE_VALIDITA), TRUNC(sysdate)   +1))
  )
  || '-'
  || lin.desc_breve_linea
  || '_'
  || sogg.desc_breve_tipo_sogg_finanziat AS report_key
FROM pbandi_r_prop_cert_lin_sp_val r,
  pbandi_d_linea_di_intervento lin,
  pbandi_d_tipo_sogg_finanziat sogg
WHERE r.id_linea_di_intervento = lin.id_linea_di_intervento
AND r.id_tipo_sogg_finanziat   = sogg.id_tipo_sogg_finanziat
AND (TRUNC(sysdate)           >= NVL(TRUNC(lin.DT_INIZIO_VALIDITA), TRUNC(sysdate)  -1)
AND TRUNC(sysdate)             < NVL(TRUNC(lin.DT_FINE_VALIDITA), TRUNC(sysdate)    +1))
AND (TRUNC(sysdate)           >= NVL(TRUNC(sogg.DT_INIZIO_VALIDITA), TRUNC(sysdate) -1)
AND TRUNC(sysdate)             < NVL(TRUNC(sogg.DT_FINE_VALIDITA), TRUNC(sysdate)   +1))