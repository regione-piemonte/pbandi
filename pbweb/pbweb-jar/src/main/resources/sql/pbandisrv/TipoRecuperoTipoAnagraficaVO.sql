select tr.desc_breve_tipo_recupero,
       tr.desc_tipo_recupero,
       tr.id_tipo_recupero,
       rtra.id_tipo_anagrafica,
       ta.desc_breve_tipo_anagrafica,
       ta.desc_tipo_anagrafica
from PBANDI_D_TIPO_RECUPERO tr,
     PBANDI_D_TIPO_ANAGRAFICA ta,
     PBANDI_R_TIPO_RECUPERO_ANAGRAF rtra
where tr.id_tipo_recupero = rtra.id_tipo_recupero
  and ta.id_tipo_anagrafica = rtra.id_tipo_anagrafica
  and nvl(trunc(ta.dt_fine_validita), trunc(sysdate     +1)) > trunc(sysdate)
  and nvl(trunc(tr.dt_fine_validita), Trunc(sysdate     +1)) > trunc(sysdate)