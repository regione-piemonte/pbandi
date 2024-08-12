  select e.id_causale_erogazione,
       e.id_modalita_agevolazione,
       e.id_progetto,
       e.id_atto_liquidazione,
       ce.desc_breve_causale,
       ce.desc_causale,
       e.importo_atto
from PBANDI_T_ATTO_LIQUIDAZIONE e,
     PBANDI_D_CAUSALE_EROGAZIONE ce,
     pbandi_d_stato_atto sda
where e.id_causale_erogazione = ce.id_causale_erogazione
       and e.id_stato_atto = sda.id_stato_atto
       and sda.desc_breve_stato_atto <> '0' 		
       and sda.desc_breve_stato_atto <> '3'