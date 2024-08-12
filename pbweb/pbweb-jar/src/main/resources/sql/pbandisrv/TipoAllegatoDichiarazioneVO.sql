SELECT DISTINCT micro.desc_micro_sezione ,
    micro.contenuto_micro_sezione desc_tipo_allegato,
    relaz.id_tipo_documento_index ,
    relaz.progr_bando_linea_intervento ,
    RELAZ.NUM_ORDINAMENTO_MACRO_SEZIONE ,
    relaz2.num_ordinamento_micro_sezione,
    flag_allegati.FLAG_ALLEGATO,
    flag_allegati.ID_DICHIARAZIONE_SPESA,
    flag_allegati.ID_MICRO_SEZIONE_MODULO
  FROM pbandi_d_macro_sezione_modulo macro,
    pbandi_d_micro_sezione_modulo micro,
    pbandi_r_bl_tipo_doc_sez_mod relaz,
    pbandi_r_bl_tipo_doc_MICRO_sez relaz2,
    pbandi_c_tipo_documento_index tipo,
    pbandi_r_bando_linea_intervent rbl,
    PBANDI_R_DICH_SPESA_DOC_ALLEG flag_allegati
  WHERE (micro.desc_micro_sezione LIKE '%ALLEGATI%'
  OR micro.desc_micro_sezione LIKE tipo.desc_breve_tipo_doc_index)
  AND relaz.id_macro_sezione_modulo      = macro.id_macro_sezione_modulo
  AND relaz.progr_bl_tipo_doc_sez_mod    = relaz2.progr_bl_tipo_doc_sez_mod
  AND relaz2.id_micro_sezione_modulo     = micro.id_micro_sezione_modulo
  AND relaz.id_tipo_documento_index      = tipo.id_tipo_documento_index
  AND relaz.progr_bando_linea_intervento = rbl.progr_bando_linea_intervento
  AND flag_allegati.ID_MICRO_SEZIONE_MODULO = micro.ID_MICRO_SEZIONE_MODULO
  AND flag_allegati.NUM_ORDINAMENTO_MICRO_SEZIONE = relaz2.num_ordinamento_micro_sezione
  ORDER BY RELAZ.PROGR_BANDO_LINEA_INTERVENTO,
    relaz.num_ordinamento_macro_sezione,
    relaz2.num_ordinamento_micro_sezione