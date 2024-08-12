select dta.id_tipo_aiuto,
       dta.cod_igrue_t1,
       dta.desc_breve_tipo_aiuto,
       dta.desc_tipo_aiuto,
       least(nvl(dta.dt_fine_validita, sysdate +1), nvl(rblta.dt_fine_validita, sysdate +1)) dt_fine_validita,
       dta.dt_inizio_validita,
       rblta.progr_bando_linea_intervento
  from pbandi_d_tipo_aiuto dta,
       pbandi_r_bando_lin_tipo_aiuto rblta
 where dta.id_tipo_aiuto = rblta.id_tipo_aiuto
