select distinct ent.desc_ente, ent.ID_ENTE_COMPETENZA
from pbandi_t_ente_competenza ent,
PBANDI_D_RUOLO_ENTE_COMPETENZA t, 
pbandi_r_bando_linea_ente_comp tt
where ent.id_ente_competenza= tt.id_ente_competenza
and t.id_ruolo_ente_competenza = 7
and ent.id_tipo_ente_competenza=1
and t.id_ruolo_ente_competenza = tt.id_ruolo_ente_competenza
AND NVL(TRUNC(ENT.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)