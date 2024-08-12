select DISTINCT ec.ID_ENTE_COMPETENZA, ec.DESC_ENTE  from pbandi_t_ente_competenza ec, pbandi_d_settore_ente se
where ec.id_ente_competenza = se.id_ente_competenza
  and ec.dt_fine_validita is null
  and ec.id_tipo_ente_competenza = 1
  and se.dt_fine_validita is null