SELECT dich.*,
  tipo.desc_breve_tipo_dichiara_spesa,
  tipo.desc_tipo_dichiarazione_spesa
FROM pbandi_t_dichiarazione_spesa dich,
  pbandi_d_tipo_dichiaraz_spesa tipo
WHERE tipo.id_tipo_dichiaraz_spesa = dich.id_tipo_dichiaraz_spesa
AND (TRUNC(sysdate)               >= NVL(TRUNC(tipo.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1)
AND TRUNC(sysdate)                 < NVL(TRUNC(tipo.DT_FINE_VALIDITA), TRUNC(sysdate)  +1)) 