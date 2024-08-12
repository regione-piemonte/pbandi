select di.*,
       rbi.id_bando,
       du.desc_unita_misura 
  from pbandi_d_indicatori di,
       pbandi_d_unita_misura du, 
       pbandi_r_bando_indicatori rbi
 where di.id_indicatori = rbi.id_indicatori
   and du.id_unita_misura = di.id_unita_misura
order by desc_indicatore