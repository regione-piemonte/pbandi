/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select pt.*,
rta.id_soggetto,
ta.desc_breve_tipo_anagrafica,ta.desc_tipo_anagrafica, 
p.desc_breve_permesso, p.desc_permesso from 
pbandi_r_permesso_tipo_anagraf pt, 
pbandi_d_tipo_anagrafica ta, 
pbandi_d_permesso p,
pbandi_r_sogg_tipo_anagrafica rta
where p.id_permesso = pt.id_permesso
and ta.id_tipo_anagrafica = pt.id_tipo_anagrafica
and rta.id_tipo_anagrafica = ta.id_tipo_anagrafica
and (TRUNC(sysdate)  >= NVL(TRUNC(p.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1)
and TRUNC(sysdate)   < NVL(TRUNC(p.DT_FINE_VALIDITA), TRUNC(sysdate)  +1))
and (TRUNC(sysdate)  >= NVL(TRUNC(ta.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1)
and TRUNC(sysdate)   < NVL(TRUNC(ta.DT_FINE_VALIDITA), TRUNC(sysdate)  +1))
and (TRUNC(sysdate)  >= NVL(TRUNC(pt.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1)
and TRUNC(sysdate)   < NVL(TRUNC(pt.DT_FINE_VALIDITA), TRUNC(sysdate)  +1))
and (TRUNC(sysdate)  >= NVL(TRUNC(rta.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1)
and TRUNC(sysdate)   < NVL(TRUNC(rta.DT_FINE_VALIDITA), TRUNC(sysdate)  +1))