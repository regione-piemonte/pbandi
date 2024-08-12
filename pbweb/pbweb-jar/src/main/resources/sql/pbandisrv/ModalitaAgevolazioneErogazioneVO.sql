select distinct
       ma.id_modalita_agevolazione,
       e.id_progetto,
       ma.desc_breve_modalita_agevolaz,
       ma.desc_modalita_agevolazione,
       nvl(modalitaRecupero.importo_totale_recupero,0) as importo_totale_recupero,
       nvl(modalitaErogazione.importo_totale_erogazioni,0) as importo_totale_erogazioni
from PBANDI_T_EROGAZIONE e,
     PBANDI_D_MODALITA_AGEVOLAZIONE ma,
    (
        select id_progetto,
               id_modalita_agevolazione,
               sum(importo_recupero) as importo_totale_recupero
        from PBANDI_T_RECUPERO
        group by (id_progetto, id_modalita_agevolazione) 
    ) modalitaRecupero,
    (
      select id_progetto,
             id_modalita_agevolazione,
             sum(importo_erogazione) importo_totale_erogazioni
      from PBANDI_T_EROGAZIONE
       group by (id_progetto, id_modalita_agevolazione) 
    ) modalitaErogazione
where e.id_modalita_agevolazione = modalitaRecupero.id_modalita_agevolazione (+)
  and e.id_progetto = modalitaRecupero.id_progetto (+)
  and e.id_modalita_agevolazione = ma.id_modalita_agevolazione
  and e.id_progetto = modalitaErogazione.id_progetto
  and e.id_modalita_agevolazione =  modalitaErogazione.id_modalita_agevolazione