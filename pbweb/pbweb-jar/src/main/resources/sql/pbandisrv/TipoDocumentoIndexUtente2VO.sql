 select rdita.id_tipo_documento_index ,
       tdi.desc_breve_tipo_doc_index,
       tdi.desc_tipo_doc_index,
       ta.desc_breve_tipo_anagrafica,
       STA.ID_SOGGETTO
from PBANDI_R_DOC_INDEX_TIPO_ANAG rdita,
     PBANDI_D_TIPO_ANAGRAFICA ta,
     PBANDI_C_TIPO_DOCUMENTO_INDEX tdi,
     PBANDI_R_SOGG_TIPO_ANAGRAFICA sta
where ta.id_tipo_anagrafica = rdita.id_tipo_anagrafica
  and tdi.id_tipo_documento_index = rdita.id_tipo_documento_index
  and STA.ID_TIPO_ANAGRAFICA = TA.ID_TIPO_ANAGRAFICA
  and TDI.FLAG_UPLOADABLE = 'S'