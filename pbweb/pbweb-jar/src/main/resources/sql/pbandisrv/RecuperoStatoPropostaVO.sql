SELECT ultima_proposta_per_stato.id_proposta_certificaz,
  ultima_proposta_per_stato.desc_breve_stato_proposta_cert,
  rdr.id_recupero
FROM pbandi_t_dett_proposta_certif dett,
  pbandi_r_dett_prop_certif_recu rdr,
  (SELECT prop.id_proposta_certificaz,
    stato.desc_breve_stato_proposta_cert
  FROM pbandi_t_proposta_certificaz prop,
    pbandi_d_stato_proposta_certif stato,
    (SELECT pbandi_t_proposta_certificaz.id_stato_proposta_certif,
      MAX(pbandi_t_proposta_certificaz.dt_ora_creazione) dt_ora_creazione
    FROM pbandi_t_proposta_certificaz
    GROUP BY pbandi_t_proposta_certificaz.id_stato_proposta_certif
    ) ultimo_stato_proposta
  WHERE prop.dt_ora_creazione       = ultimo_stato_proposta.dt_ora_creazione
  AND prop.id_stato_proposta_certif = stato.id_stato_proposta_certif
  ) ultima_proposta_per_stato
WHERE ultima_proposta_per_stato.id_proposta_certificaz = dett.id_proposta_certificaz
AND rdr.id_dett_proposta_certif                        = dett.id_dett_proposta_certif
