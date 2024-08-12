select 
p.id_progetto,
attol.id_atto_liquidazione,
modagev.id_modalita_agevolazione,
modagev.desc_modalita_agevolazione,
modagev.desc_breve_modalita_agevolaz,
rcema.quota_importo_agevolato,
causaleerog.id_causale_erogazione,
causaleerog.desc_causale,
causaleerog.desc_breve_causale,
attol.dt_emissione_atto,
attol.numero_atto,
liquidazione.importo_liquidato_atto
from pbandi_t_atto_liquidazione attol,
     pbandi_d_causale_erogazione causaleerog,
     pbandi_d_modalita_agevolazione modagev,
     pbandi_t_progetto p,
     pbandi_t_conto_economico ce,
     pbandi_d_stato_conto_economico statoce,
     pbandi_r_conto_econom_mod_agev rcema,
(
    select liq.id_atto_liquidazione, 
       sum(liq.importo_liquidato) as importo_liquidato_atto
  from pbandi_r_liquidazione liq
 where liq.progr_liquidazione_precedente is null
/* stato liquidazione diverso da ANNULLATO */
   and liq.id_stato_liquidazione not in  ( select id_stato_liquidazione 
                                             from pbandi_d_stato_liquidazione
                                            where desc_breve_stato_liquidazione = 3 
                                      )
  and nvl(trunc(liq.dt_fine_validita), trunc(sysdate+1)) > trunc(sysdate)
  group by liq.id_atto_liquidazione
) liquidazione  
where attol.id_causale_erogazione = causaleerog.id_causale_erogazione
  and attol.id_modalita_agevolazione = modagev.id_modalita_agevolazione
  and liquidazione.id_atto_liquidazione = attol.id_atto_liquidazione
  and attol.id_progetto = p.id_progetto
  and p.id_domanda = ce.id_domanda
  /* conto economico MASTER */ 
  and ce.id_stato_conto_economico = statoce.id_stato_conto_economico
  and statoce.id_tipologia_conto_economico in (select id_tipologia_conto_economico from pbandi_d_tipologia_conto_econ where desc_breve_tipologia_conto_eco = 'MASTER')
  /* modalita di agevolazioni associate al conto economico MASTER */
  and rcema.id_modalita_agevolazione = modagev.id_modalita_agevolazione
  and rcema.id_conto_economico = ce.id_conto_economico
  /* stato atto liquidazione diverso da BOZZA */
  and attol.id_stato_atto  not in (select id_stato_atto from pbandi_d_stato_atto where desc_breve_stato_atto = 0)
order by p.id_progetto, modagev.desc_modalita_agevolazione, attol.dt_emissione_atto