select modagev.id_modalita_agevolazione,
       ce.id_conto_economico,
       p.id_progetto,
       modagev.desc_modalita_agevolazione,
       modagev.desc_breve_modalita_agevolaz,
       rcema.quota_importo_agevolato
from  pbandi_d_modalita_agevolazione modagev,
      pbandi_r_conto_econom_mod_agev rcema,
      pbandi_t_conto_economico ce,
      pbandi_t_progetto p
where rcema.id_modalita_agevolazione = modagev.id_modalita_agevolazione
  and rcema.id_conto_economico = ce.id_conto_economico
  and ce.id_domanda = p.id_domanda
