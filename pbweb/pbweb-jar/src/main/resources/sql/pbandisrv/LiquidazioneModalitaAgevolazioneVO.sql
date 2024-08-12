select atto.id_progetto,
       liq.progr_liquidazione,
       atto.id_modalita_agevolazione,
       atto.id_causale_erogazione,
       ce.desc_breve_causale,
       ce.desc_causale,
       ma.desc_breve_modalita_agevolaz,
       ma.desc_modalita_agevolazione,
       mandquiet.importo_quietanzato
from pbandi_t_atto_liquidazione atto,
     pbandi_d_causale_erogazione ce,
     pbandi_d_modalita_agevolazione ma,
      pbandi_r_liquidazione liq,
      PBANDI_T_MANDATO_QUIETANZATO mandQuiet
where atto.id_causale_erogazione = ce.id_causale_erogazione
  and atto.id_modalita_agevolazione = ma.id_modalita_agevolazione
  and atto.id_atto_liquidazione = liq.id_atto_liquidazione
  and liq.progr_liquidazione = mandquiet.progr_liquidazione
  AND TRUNC(sysdate) < NVL(TRUNC(liq.DT_FINE_VALIDITA), TRUNC(sysdate)+1)
  AND TRUNC(sysdate) < NVL(TRUNC(mandquiet.DT_FINE_VALIDITA), TRUNC(sysdate)+1)