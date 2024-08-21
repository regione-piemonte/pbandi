/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select distinct micro.desc_micro_sezione , 
    micro.contenuto_micro_sezione  desc_tipo_allegato,
	relaz.id_tipo_documento_index ,
	relaz.progr_bando_linea_intervento  ,
	relaz.num_ordinamento_macro_sezione ,
    relaz2.num_ordinamento_micro_sezione,
     dce.id_causale_erogazione
from pbandi_d_macro_sezione_modulo macro,
     pbandi_d_micro_sezione_modulo micro,
     pbandi_r_bl_tipo_doc_sez_mod relaz,
     pbandi_r_bl_tipo_doc_micro_sez relaz2,
     pbandi_c_tipo_documento_index tipo,
     pbandi_r_bando_linea_intervent rbl,
     PBANDI_D_CAUSALE_EROGAZIONE dce
where
   upper(micro.desc_micro_sezione) like upper(tipo.desc_breve_tipo_doc_index
||'-ALLEGATI%')
  and relaz.id_macro_sezione_modulo = macro.id_macro_sezione_modulo
  and relaz.progr_bl_tipo_doc_sez_mod = relaz2.progr_bl_tipo_doc_sez_mod
  and relaz2.id_micro_sezione_modulo = micro.id_micro_sezione_modulo
  and relaz.id_tipo_documento_index = tipo.id_tipo_documento_index
  and relaz.progr_bando_linea_intervento = rbl.progr_bando_linea_intervento
  and relaz.id_tipo_documento_index = DCE.ID_TIPO_DOCUMENTO_INDEX
  order by relaz.progr_bando_linea_intervento,
  relaz.num_ordinamento_macro_sezione,
  relaz2.num_ordinamento_micro_sezione