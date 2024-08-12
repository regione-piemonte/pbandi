select dl.desc_breve_linea || ' - ' || dl.desc_linea desc_linea_completa,
       dts.desc_tipo_sogg_finanziatore,
       rpcsa.*
  from pbandi_r_prop_cert_scost_asse rpcsa,
       pbandi_d_linea_di_intervento dl,
       pbandi_d_tipo_sogg_finanziat dts
 where rpcsa.id_linea_di_intervento = dl.id_linea_di_intervento
   and rpcsa.id_tipo_sogg_finanziat = dts.id_tipo_sogg_finanziat
order by rpcsa.id_proposta_certificaz, dl.desc_breve_linea, dts.desc_tipo_sogg_finanziatore