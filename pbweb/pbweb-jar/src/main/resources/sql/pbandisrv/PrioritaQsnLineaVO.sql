SELECT rlpq.id_linea_di_intervento AS id_linea_di_intervento,
  dpq.*
FROM pbandi_d_priorita_qsn dpq,
  pbandi_r_linea_priorita_qsn rlpq
WHERE dpq.id_priorita_qsn = rlpq.id_priorita_qsn

AND (TRUNC(sysdate)      >= NVL(TRUNC(dpq.DT_INIZIO_VALIDITA), TRUNC(sysdate) -1)
AND TRUNC(sysdate)        < NVL(TRUNC(dpq.DT_FINE_VALIDITA), TRUNC(sysdate)   +1))
AND TRUNC(sysdate)        < NVL(TRUNC(rlpq.DT_FINE_VALIDITA), TRUNC(sysdate)  +1)

UNION

SELECT rlim.id_linea_di_intervento_migrata AS id_linea_di_intervento,
  dpq.*
FROM pbandi_d_priorita_qsn dpq,
  pbandi_r_linea_interv_mapping rlim,
  pbandi_r_linea_priorita_qsn rlpq
WHERE rlim.id_linea_di_intervento_attuale    = rlpq.id_linea_di_intervento
AND dpq.id_priorita_qsn                      = rlpq.id_priorita_qsn
AND rlim.id_linea_di_intervento_migrata NOT IN
  (SELECT id_linea_di_intervento FROM pbandi_r_linea_priorita_qsn
  )

AND (TRUNC(sysdate) >= NVL(TRUNC(dpq.DT_INIZIO_VALIDITA), TRUNC(sysdate) -1)
AND TRUNC(sysdate)   < NVL(TRUNC(dpq.DT_FINE_VALIDITA), TRUNC(sysdate)   +1))
AND TRUNC(sysdate)   < NVL(TRUNC(rlpq.DT_FINE_VALIDITA), TRUNC(sysdate)  +1)
