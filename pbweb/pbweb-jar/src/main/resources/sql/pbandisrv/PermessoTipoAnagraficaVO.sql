select distinct pt.*,
ta.desc_breve_tipo_anagrafica,ta.desc_tipo_anagrafica, 
p.desc_breve_permesso, p.desc_permesso from 
pbandi_r_permesso_tipo_anagraf pt, 
pbandi_d_tipo_anagrafica ta, 
pbandi_d_permesso p
where p.id_permesso = pt.id_permesso
and ta.id_tipo_anagrafica = pt.id_tipo_anagrafica
and (TRUNC(sysdate)  >= NVL(TRUNC(p.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1)
and TRUNC(sysdate)   < NVL(TRUNC(p.DT_FINE_VALIDITA), TRUNC(sysdate)  +1))
and (TRUNC(sysdate)  >= NVL(TRUNC(ta.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1)
and TRUNC(sysdate)   < NVL(TRUNC(ta.DT_FINE_VALIDITA), TRUNC(sysdate)  +1))
and (TRUNC(sysdate)  >= NVL(TRUNC(pt.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1)
and TRUNC(sysdate)   < NVL(TRUNC(pt.DT_FINE_VALIDITA), TRUNC(sysdate)  +1))