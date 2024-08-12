select distinct 
       entesogg.id_ente_competenza,
       entesogg.id_soggetto,
       ente.desc_breve_ente,
       ente.desc_ente,
       /* tipo ente */
       tipoente.id_tipo_ente_competenza,
       tipoente.desc_breve_tipo_ente_competenz,
       tipoente.desc_tipo_ente_competenza,
       tipoente.cod_igrue_t51
from pbandi_r_ente_competenza_sogg enteSogg, 
     pbandi_t_ente_competenza ente,
     pbandi_d_tipo_ente_competenza tipoEnte
where entesogg.id_ente_competenza = ente.id_ente_competenza
  and ente.id_tipo_ente_competenza = tipoente.id_tipo_ente_competenza
  and NVL(TRUNC(ente.DT_FINE_VALIDITA), TRUNC(SYSDATE     +1)) > TRUNC(SYSDATE)
  and NVL(TRUNC(enteSogg.DT_FINE_VALIDITA), TRUNC(SYSDATE     +1)) > TRUNC(SYSDATE)
  and NVL(TRUNC(tipoente.DT_FINE_VALIDITA), TRUNC(SYSDATE     +1)) > TRUNC(SYSDATE)