select
re.id_progetto,
re.id_richiesta_erogazione,
ce.id_causale_erogazione,
ce.desc_breve_causale,
ce.desc_causale as desc_causale_erogazione,
re.numero_richiesta_erogazione,
re.dt_richiesta_erogazione as data_richiesta_erogazione,
re.importo_erogazione_richiesto as importo_richiesta_erogazione
from pbandi_t_richiesta_erogazione re,
     pbandi_d_causale_erogazione ce
where re.id_causale_erogazione = ce.id_causale_erogazione
order by re.id_progetto,re.dt_richiesta_erogazione