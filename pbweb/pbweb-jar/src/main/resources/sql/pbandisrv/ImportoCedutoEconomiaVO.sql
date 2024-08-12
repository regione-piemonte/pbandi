SELECT SUM (ec.importo_ceduto) AS Importo_ceduto,
  ec.id_progetto_cedente
FROM pbandi_t_economie ec
GROUP BY ec.id_progetto_cedente