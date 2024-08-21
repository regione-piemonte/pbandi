/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select distinct motivoRevoca.*,
       case
         when motivoRevoca.dt_ultima_revoca is not null then
         (
            select estremi
            from pbandi_t_revoca
            where dt_revoca = motivoRevoca.dt_ultima_revoca
              and id_modalita_agevolazione = motivoRevoca.id_modalita_agevolazione
              and id_progetto = motivoRevoca.id_progetto
              and id_motivo_revoca = motivoRevoca.id_motivo_revoca
              and rownum = 1
         )
       end as estremi_ultima_revoca
from PBANDI_T_REVOCA r,
(
    select rev.id_modalita_agevolazione,
           rev.id_progetto,
           mr.id_motivo_revoca,
           mr.desc_motivo_revoca,
          case  
             when rev.id_modalita_agevolazione is not null and rev.id_progetto is not null
              then ( select max(dt_revoca)
                     from PBANDI_T_REVOCA
                     where id_modalita_agevolazione = rev.id_modalita_agevolazione
                       and id_progetto = rev.id_progetto
                       and id_motivo_revoca = mr.id_motivo_revoca
                   )
            end as dt_ultima_revoca,
           sum(nvl(rev.importo,0)) as totale_importo_revocato
    from pbandi_t_revoca rev,
         pbandi_d_motivo_revoca mr
    where rev.id_motivo_revoca = mr.id_motivo_revoca
    group by (rev.id_modalita_agevolazione,
           rev.id_progetto,
           mr.id_motivo_revoca,
           mr.desc_motivo_revoca,5)
)  motivoRevoca
where r.id_modalita_agevolazione = motivoRevoca.id_modalita_agevolazione
  and r.id_progetto = motivoRevoca.id_progetto
  and r.id_motivo_revoca = motivoRevoca.id_motivo_revoca