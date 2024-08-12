select distinct tt.progr_bando_linea_intervento, tt.nome_bando_linea
from PBANDI_T_TEMPLATE t, pbandi_r_bando_linea_intervent tt, pbandi_c_tipo_documento_index c
where t.progr_bando_linea_intervento = tt.progr_bando_linea_intervento
and  t.id_tipo_documento_index = c.id_tipo_documento_index
