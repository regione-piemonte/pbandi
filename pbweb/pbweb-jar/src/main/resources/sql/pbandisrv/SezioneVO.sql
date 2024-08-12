select macro.progr_bando_linea_intervento, macro.id_tipo_documento_index,
	   macro.PROGR_BL_TIPO_DOC_SEZ_MOD,
       macro.NUM_ORDINAMENTO_MACRO_SEZIONE, macro.ID_MACRO_SEZIONE_MODULO,
       micro.NUM_ORDINAMENTO_MICRO_SEZIONE, micro.ID_MICRO_SEZIONE_MODULO,
       modulo.DESC_MICRO_SEZIONE,
       modulo.CONTENUTO_MICRO_SEZIONE,
       msm.DESC_MACRO_SEZIONE
from pbandi_r_bl_tipo_doc_sez_mod macro,
     pbandi_d_macro_sezione_modulo msm,
     pbandi_r_bl_tipo_doc_micro_sez micro,
     pbandi_d_micro_sezione_modulo modulo
where msm.ID_MACRO_SEZIONE_MODULO = macro.ID_MACRO_SEZIONE_MODULO
  and micro.progr_bl_tipo_doc_sez_mod = macro.progr_bl_tipo_doc_sez_mod
  and modulo.ID_MICRO_SEZIONE_MODULO = micro.ID_MICRO_SEZIONE_MODULO
  and modulo.DT_FINE_VALIDITA is null
order by macro.NUM_ORDINAMENTO_MACRO_SEZIONE, micro.NUM_ORDINAMENTO_MICRO_SEZIONE