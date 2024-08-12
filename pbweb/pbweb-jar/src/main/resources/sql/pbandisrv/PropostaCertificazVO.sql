select p.*, 
       s.desc_breve_stato_proposta_cert,
       s.desc_stato_proposta_certif
  from PBANDI_T_PROPOSTA_CERTIFICAZ  p,
       PBANDI_D_STATO_PROPOSTA_CERTIF s
 where p.id_stato_proposta_certif = s.id_stato_proposta_certif
 order by p.ID_PROPOSTA_CERTIFICAZ
