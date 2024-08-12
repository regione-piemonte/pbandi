select ble.id_ente_competenza,
       ble.progr_bando_linea_intervento as progr_bandolinea_intervento,
       bli.nome_bando_linea as nome_bandolinea,
       ble.id_ruolo_ente_competenza,
       re.desc_breve_ruolo_ente,
       re.desc_ruolo_ente
from pbandi_r_bando_linea_ente_comp ble,
     pbandi_d_ruolo_ente_competenza re,
     pbandi_r_bando_linea_intervent bli
where ble.id_ruolo_ente_competenza = re.id_ruolo_ente_competenza
and bli.progr_bando_linea_intervento = ble.progr_bando_linea_intervento
and trunc(sysdate) < nvl(trunc(re.DT_FINE_VALIDITA), trunc(sysdate)+1)
and trunc(sysdate) < nvl(trunc(ble.DT_FINE_VALIDITA), trunc(sysdate)+1)