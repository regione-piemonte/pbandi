SELECT sf.desc_sogg_finanziatore,
    ef.perc_quota_sogg_finanziat,
    sf.flag_agevolato,
    ef.imp_quota_econ_sogg_finanziat,
    eco.ID_PROGETTO_CEDENTE AS id_progetto,
    eco.id_economia,
    ef.ID_SOGGETTO_FINANZIATORE,
    ef.tipologia_progetto
  FROM pbandi_t_economie eco,
    pbandi_r_econom_sogg_finanziat ef ,
    pbandi_d_soggetto_finanziatore sf
  WHERE eco.id_economia           = ef.id_economia
  AND ef.id_soggetto_finanziatore = sf.id_soggetto_finanziatore