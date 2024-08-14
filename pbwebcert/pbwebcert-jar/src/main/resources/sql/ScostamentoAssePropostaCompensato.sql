select count(*)
  from pbandi_r_prop_cert_scost_asse rpcsa,
       pbandi_d_linea_di_intervento dl,
       pbandi_d_tipo_sogg_finanziat dts
 where rpcsa.id_linea_di_intervento = dl.id_linea_di_intervento
   and rpcsa.id_tipo_sogg_finanziat = dts.id_tipo_sogg_finanziat
   and rpcsa.ID_PROPOSTA_CERTIFICAZ = ? and flag_comp = 'N'
order by rpcsa.id_proposta_certificaz, dl.desc_breve_linea, dts.desc_tipo_sogg_finanziatore