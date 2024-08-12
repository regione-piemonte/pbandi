select dirp.*,
       rblirp.progr_bando_linea_intervento
  from pbandi_r_bando_lin_ind_ris_pro rblirp,
       pbandi_d_ind_risultato_program dirp
 where rblirp.id_ind_risultato_program = dirp.id_ind_risultato_program