select t.id_template,       
       t.id_tipo_documento_index,
       tdi.desc_breve_tipo_doc_index,
       tdi.desc_tipo_doc_index,
       t.progr_bando_linea_intervento as progr_bandolinea_intervento,
       bli.nome_bando_linea as nome_bandolinea,
       t.nome_template,
       t.id_macro_sezione_modulo,
       m.desc_breve_macro_sezione,
       t.sez_ds_param_name,
       t.sez_report_param_name,
       t.sub_ds_param_name,
       t.sub_report_param_name,
       t.id_tipo_template,
       tt.desc_breve_tipo_template,
       t.dt_inserimento,
       t.jasperblob
from pbandi_t_template t 
       left outer join  
     pbandi_d_macro_sezione_modulo m
     on t.id_macro_sezione_modulo = m.id_macro_sezione_modulo, 
     pbandi_c_tipo_documento_index tdi,
     pbandi_d_tipo_template tt,
     pbandi_r_bando_linea_intervent bli
where t.id_tipo_documento_index = tdi.id_tipo_documento_index
  and t.id_tipo_template = tt.id_tipo_template
  and t.progr_bando_linea_intervento = bli.progr_bando_linea_intervento
  AND NVL(TRUNC(t.dt_fine_validita), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
  AND NVL(TRUNC(tt.dt_fine_validita), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
  AND NVL(TRUNC(m.dt_fine_validita), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)