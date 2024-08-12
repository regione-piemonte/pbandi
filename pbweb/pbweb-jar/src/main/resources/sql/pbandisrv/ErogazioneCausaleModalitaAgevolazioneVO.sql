select e.*,
       ce.desc_breve_causale,
       ce.desc_causale,
       ma.desc_breve_modalita_agevolaz,
       ma.desc_modalita_agevolazione
from pbandi_t_erogazione e,
     pbandi_d_causale_erogazione ce,
     pbandi_d_modalita_agevolazione ma
where e.id_causale_erogazione = ce.id_causale_erogazione
  and ma.id_modalita_agevolazione = e.id_modalita_agevolazione