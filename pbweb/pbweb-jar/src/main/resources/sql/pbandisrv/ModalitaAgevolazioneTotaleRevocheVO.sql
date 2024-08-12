select ma.id_modalita_agevolazione,
      ma.desc_breve_modalita_agevolaz,
      ma.desc_modalita_agevolazione,
      revoca.id_progetto,
      nvl(recupero.totale_importo_recuperato,0) as totale_importo_recuperato,
      nvl(revoca.totale_importo_revocato,0) as totale_importo_revocato
from  pbandi_d_modalita_agevolazione ma,
(
  select id_modalita_agevolazione,
         id_progetto,    
         sum(nvl(importo,0)) as totale_importo_revocato
  from pbandi_t_revoca 
  group by (id_modalita_agevolazione,id_progetto)
  
)  revoca,
(
  select id_modalita_agevolazione,
         id_progetto,
          sum(nvl(importo_recupero,0)) as totale_importo_recuperato
  from pbandi_t_recupero 
  group by (id_modalita_agevolazione,id_progetto)
  
) recupero
where ma.id_modalita_agevolazione = revoca.id_modalita_agevolazione (+)
  and revoca.id_modalita_agevolazione = recupero.id_modalita_agevolazione (+)
  and revoca.id_progetto = recupero.id_progetto(+)
  and nvl(trunc(ma.dt_fine_validita), trunc(sysdate     +1)) > trunc(sysdate)