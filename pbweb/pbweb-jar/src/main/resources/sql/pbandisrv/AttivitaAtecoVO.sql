select * from pbandi_d_attivita_ateco
where length(cod_ateco) = 8
AND NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(SYSDATE     +1)) > TRUNC(SYSDATE)
