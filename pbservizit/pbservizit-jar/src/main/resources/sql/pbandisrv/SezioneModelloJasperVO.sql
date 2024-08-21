/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select rbl.progr_bando_linea_intervento as progr_bandolinea_intervento,
       tipo.id_tipo_documento_index,
       tipo.desc_breve_tipo_doc_index,
       rbl.nome_bando_linea as nome_bandolinea, 
       tipo.desc_tipo_doc_index, 
       macro.id_macro_sezione_modulo,
       macro.desc_breve_macro_sezione,
       macro.desc_macro_sezione ,
       relaz.report_jrxml,
       relaz.template_jrxml,
       micro.desc_micro_sezione ,
       micro.contenuto_micro_sezione,
       relaz.num_ordinamento_macro_sezione, 
       relazMicro.num_ordinamento_micro_sezione
from pbandi_d_macro_sezione_modulo macro,
     pbandi_d_micro_sezione_modulo micro,
     pbandi_r_bl_tipo_doc_sez_mod relaz,
     pbandi_c_tipo_documento_index tipo,
     pbandi_r_bando_linea_intervent rbl,
     pbandi_r_bl_tipo_doc_micro_sez relazMicro
where relaz.id_macro_sezione_modulo = macro.id_macro_sezione_modulo
  and relaz.id_tipo_documento_index = tipo.id_tipo_documento_index
  and relaz.progr_bando_linea_intervento = rbl.progr_bando_linea_intervento
  and relaz.progr_bl_tipo_doc_sez_mod = relazMicro.progr_bl_tipo_doc_sez_mod
  and relazMicro.id_micro_sezione_modulo = micro.id_micro_sezione_modulo