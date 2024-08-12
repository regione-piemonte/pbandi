select e.id_causale_erogazione,
       e.id_modalita_agevolazione,
       e.id_progetto,
       e.id_erogazione,
       ce.desc_breve_causale,
       ce.desc_causale,
       e.importo_erogazione,
       e.dt_contabile,
       e.cod_riferimento_erogazione
from PBANDI_T_EROGAZIONE e,
     PBANDI_D_CAUSALE_EROGAZIONE ce
where e.id_causale_erogazione = ce.id_causale_erogazione