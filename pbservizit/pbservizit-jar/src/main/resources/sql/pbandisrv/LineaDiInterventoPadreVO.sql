/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select dtl.desc_tipo_linea,
       dtl.livello_tipo_linea,
       dl.*,
       rbl.progr_bando_linea_intervento,
       rbl.id_bando
from
  (select connect_by_root dl.id_linea_di_intervento_padre id_linea_radice,
    dl.*
  from pbandi_d_linea_di_intervento dl
  where (trunc(sysdate)                       >= nvl(trunc(dl.DT_INIZIO_VALIDITA), trunc(sysdate) -1)
  and trunc(sysdate)                           < nvl(trunc(dl.DT_FINE_VALIDITA), trunc(sysdate)   +1))
    connect by prior dl.id_linea_di_intervento = dl.id_linea_di_intervento_padre
  
  union
    (select dlradici.id_linea_di_intervento id_linea_radice,
      dlradici.*
    from pbandi_d_linea_di_intervento dlradici
    where dlradici.id_linea_di_intervento_padre is null
    and (trunc(sysdate)                         >= nvl(trunc(dlradici.DT_INIZIO_VALIDITA), trunc(sysdate) -1)
    and trunc(sysdate)                           < nvl(trunc(dlradici.DT_FINE_VALIDITA), trunc(sysdate)   +1))
    )
  ) dlr,
  (select nvl(
    (select rlim.id_linea_di_intervento_attuale
    from pbandi_r_linea_interv_mapping rlim
    where rlim.id_linea_di_intervento_migrata = id_linea_di_intervento
    ), id_linea_di_intervento) id_linea_di_intervento,
    progr_bando_linea_intervento,
    id_bando
  from pbandi_r_bando_linea_intervent
  ) rbl,
  pbandi_d_linea_di_intervento dl,
  pbandi_d_tipo_linea_intervento dtl
where rbl.id_linea_di_intervento = dlr.id_linea_di_intervento
and dl.id_linea_di_intervento    = dlr.id_linea_radice
and dtl.id_tipo_linea_intervento = dl.id_tipo_linea_intervento
and (trunc(sysdate) >= nvl(trunc(dl.DT_INIZIO_VALIDITA), trunc(sysdate)  -1)
and trunc(sysdate)   < nvl(trunc(dl.DT_FINE_VALIDITA), trunc(sysdate)    +1))
and (trunc(sysdate) >= nvl(trunc(dtl.DT_INIZIO_VALIDITA), trunc(sysdate) -1)
and trunc(sysdate)   < nvl(trunc(dtl.DT_FINE_VALIDITA), trunc(sysdate)   +1))
