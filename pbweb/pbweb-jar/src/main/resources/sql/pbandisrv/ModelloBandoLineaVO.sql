select distinct c.id_tipo_documento_index, c.desc_tipo_doc_index, 
c.desc_breve_tipo_doc_index, t.progr_bando_linea_intervento 
from PBANDI_T_TEMPLATE t, pbandi_c_tipo_documento_index c
where t.id_tipo_documento_index = c.id_tipo_documento_index
