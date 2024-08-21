/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select id_irregolarita_padre, max(numero_versione) max_versione
  from (
    select id_irregolarita id_irregolarita_padre, numero_versione
    from pbandi_t_irregolarita
    where NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
    and id_irregolarita_collegata is  null
    
    union
    
    select distinct id_irregolarita_collegata id_irregolarita_padre,
    max(numero_versione) over (partition by id_irregolarita_collegata) numero_versione
    from pbandi_t_irregolarita
    where NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
      and id_irregolarita_collegata is not null
  ) temp
  group by id_irregolarita_padre