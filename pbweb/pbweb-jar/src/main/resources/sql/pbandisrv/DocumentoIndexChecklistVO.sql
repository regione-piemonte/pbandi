select
di.id_progetto,
di.id_documento_index,
di.id_target,
di.id_entita,
di.nome_file,
di.versione,
stdi.desc_breve_stato_tip_doc_index,
stdi.desc_stato_tipo_doc_index,
tdi.desc_breve_tipo_doc_index,
tdi.desc_tipo_doc_index,
chl.soggetto_controllore,
chl.flag_irregolarita,
chl.dt_controllo
 from pbandi_T_Documento_Index di,
PBANDI_R_DOCU_INDEX_TIPO_STATO ts,
PBANDI_D_STATO_TIPO_DOC_INDEX stdi,
pbandi_t_checklist chl,
PBANDI_C_TIPO_DOCUMENTO_INDEX tdi,
(select distinct ID_ENTITA, ID_TARGET ,ID_TIPO_DOCUMENTO_INDEX, ID_PROGETTO,max(nvl(versione,0))
over ( partition by  ID_ENTITA, ID_TARGET ,ID_TIPO_DOCUMENTO_INDEX, ID_PROGETTO ) as versione
from
PBANDI_T_DOCUMENTO_INDEX) maxVersione
where di.id_documento_index = ts.id_documento_index 
and di.id_documento_index = chl.id_documento_index 
and di.id_tipo_documento_index = ts.id_tipo_documento_index
and stdi.id_stato_tipo_doc_index = ts.id_stato_tipo_doc_index
and (
(tdi.desc_breve_tipo_doc_index = 'CLIL' and chl.id_checklist = di.id_target) 
OR (tdi.desc_breve_tipo_doc_index = 'CL' and chl.ID_DICHIARAZIONE_SPESA = di.id_target)
)
and nvl(di.versione,0)=nvl(maxVersione.versione,0)
and di.ID_ENTITA=maxVersione.ID_ENTITA
and di.ID_TARGET =maxVersione.ID_TARGET
and di.ID_TIPO_DOCUMENTO_INDEX =maxVersione.ID_TIPO_DOCUMENTO_INDEX
and di.ID_PROGETTO=maxVersione.ID_PROGETTO