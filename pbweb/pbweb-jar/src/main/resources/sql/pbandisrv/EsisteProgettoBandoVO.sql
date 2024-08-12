select p.id_progetto, bl.id_bando, bl.progr_bando_linea_intervento
from PBANDI_R_BANDO_LINEA_INTERVENT bl, PBANDI_T_DOMANDA d, PBANDI_T_PROGETTO p
where d.PROGR_BANDO_LINEA_INTERVENTO = bl.PROGR_BANDO_LINEA_INTERVENTO
and p.ID_DOMANDA = d.ID_DOMANDA