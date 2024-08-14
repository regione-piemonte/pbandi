select count(*) from (
select tdpc.id_proposta_certificaz, t.*
  from pbandi_t_dett_proposta_certif tdpc,
       pbandi_r_dett_prop_cer_fideius t
 where tdpc.id_dett_proposta_certif = t.id_dett_proposta_certif
) where ID_PROPOSTA_CERTIFICAZ = ?