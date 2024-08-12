select ma.id_modalita_agevolazione,
       ma.desc_modalita_agevolazione,
       ma.desc_breve_modalita_agevolaz,
       revoca.id_progetto,
       nvl(revoca.totale_importo_revocato,0) as totale_importo_revocato,
       nvl(recupero.totale_importo_recuperato,0) as totale_importo_recuperato,
       revoca.dt_ultima_revoca,
       case
         when revoca.dt_ultima_revoca is not null then
         (
            select estremi
            from pbandi_t_revoca
            where dt_revoca = revoca.dt_ultima_revoca
              and rownum = 1
         )
       end as estremi_ultima_revoca
from pbandi_d_modalita_agevolazione ma,
(
    SELECT rec.id_progetto                ,
      rec.id_modalita_agevolazione         ,
      rec.id_tipo_recupero                 ,
      SUM(NVL(rec.importo_recupero,0)) AS totale_importo_recuperato
      FROM pbandi_t_recupero rec
      WHERE rec.id_tipo_recupero NOT IN ( select id_tipo_recupero
                                            from pbandi_d_tipo_recupero
                                           where desc_breve_tipo_recupero = 'SO'
                                             and nvl(trunc(dt_fine_validita), trunc(sysdate+1)) > trunc(sysdate)
                                         )
      GROUP BY ( rec.id_progetto,rec.id_modalita_agevolazione, rec.id_tipo_recupero)
) recupero,
(
    select rev.id_modalita_agevolazione,
         rev.id_progetto,
         case  
           when rev.id_modalita_agevolazione is not null and rev.id_progetto is not null
            then ( select max(dt_revoca)
                   from pbandi_t_revoca
                   where id_modalita_agevolazione = rev.id_modalita_agevolazione
                     and id_progetto = rev.id_progetto
                 )
          end as dt_ultima_revoca,
          sum(nvl(rev.importo,0)) as totale_importo_revocato
    from pbandi_t_revoca rev
    group by (rev.id_progetto,rev.id_modalita_agevolazione,3)
) revoca
where ma.id_modalita_agevolazione = revoca.id_modalita_agevolazione(+)
  and revoca.id_modalita_agevolazione = recupero.id_modalita_agevolazione(+)
  and revoca.id_progetto = recupero.id_progetto (+)
  and nvl(trunc(ma.dt_fine_validita), trunc(sysdate     +1)) > trunc(sysdate)