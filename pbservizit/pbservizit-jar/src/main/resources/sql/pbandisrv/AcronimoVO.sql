/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select  
 padri.ID_PROGETTO ,
 p.id_domanda id_domanda_padre,
r.ACRONIMO_PROGETTO
 from
 (select distinct
  case when p.id_progetto_padre is null 
    then p.id_progetto
    else p.id_progetto_padre
    end
  id_progetto_padre ,id_progetto
 from  pbandi_t_progetto p) padri,
  pbandi_t_progetto p,
  pbandi_t_raggruppamento r
 where 
 p.ID_PROGETTO=padri.ID_PROGETTO_PADRE
 and r.ID_DOMANDA=p.id_domanda