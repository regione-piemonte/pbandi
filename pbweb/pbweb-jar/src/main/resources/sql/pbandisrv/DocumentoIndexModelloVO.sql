select desc_breve_tipo_doc_index, 
       id_progetto,
       nome_modello,
       codice_modello,
       codice_modulo,
       id_tipo_modello,
       max(versione_modello) as versione_modello
  FROM ( 
SELECT * 
  FROM pbandi_r_tp_doc_ind_ban_li_int di, 
       pbandi_c_modello cm, 
       PBANDI_C_TIPO_DOCUMENTO_INDEX tdi,
       pbandi_t_domanda td,
       pbandi_t_progetto tp
 WHERE di.id_modello = cm.id_modello
   and tdi.id_tipo_documento_index = di.id_tipo_documento_index
   and td.PROGR_BANDO_LINEA_INTERVENTO = di.PROGR_BANDO_LINEA_INTERVENTO
   and td.id_domanda = tp.id_domanda
       ) t
group by desc_breve_tipo_doc_index, 
         id_progetto,
         nome_modello,
         codice_modello,
         codice_modulo,
         id_tipo_modello