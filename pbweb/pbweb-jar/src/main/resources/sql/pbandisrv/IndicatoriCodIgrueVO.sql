select ind.*,
  lin.cod_igrue_t13_t14,
  ind.desc_indicatore || nvl2(ind.id_linea_di_intervento,' ('||lin.cod_igrue_t13_t14||')','') as desc_indicatore_linea
from pbandi_d_indicatori ind,
  pbandi_d_linea_di_intervento lin
where ind.id_linea_di_intervento = lin.id_linea_di_intervento(+)

and (trunc(sysdate) >= nvl(trunc(ind.DT_INIZIO_VALIDITA), trunc(sysdate)-1)
and trunc(sysdate) < nvl(trunc(ind.DT_FINE_VALIDITA), trunc(sysdate)+1))

and (trunc(sysdate) >= nvl(trunc(lin.DT_INIZIO_VALIDITA), trunc(sysdate)-1)
and trunc(sysdate) < nvl(trunc(lin.DT_FINE_VALIDITA), trunc(sysdate)+1))