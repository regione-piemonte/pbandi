/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select distinct b.id_appalto, b.oggetto_appalto, l.id_progetto, c.id_appalto_checklist, 
m.id_tipologia_appalto, m.desc_tipologia_appalto,  
i.id_procedura_aggiudicaz, i.cig_proc_agg, i.cod_proc_agg, i.importo, b.importo_contratto,
g1.id_esito id_esito_intermedio, g1.esito esito_intermedio, g1.flag_rettifica flag_rettifica_intermedio, 
g2.id_esito id_esito_definitivo, g2.esito esito_definitivo, g2.flag_rettifica flag_rettifica_definitivo, 
d.id_checklist, e.id_documento_index, e.nome_file, n.ID_STATO_TIPO_DOC_INDEX
from  pbandi_t_appalto b
join pbandi_t_appalto_checklist c on c.id_appalto= b.id_appalto
join pbandi_t_checklist d on d.id_checklist = c.id_checklist
join pbandi_t_documento_index e on e.id_documento_index = d.id_documento_index
left join pbandi_r_progetti_appalti f on f.id_appalto = b.id_appalto
left join pbandi_t_esiti_note_affidament g1 on g1.id_checklist = d.id_checklist and g1.fase =1
left join pbandi_t_esiti_note_affidament g2 on g2.id_checklist = d.id_checklist and g2.fase =2
left join pbandi_t_procedura_aggiudicaz i on i.id_procedura_aggiudicaz = b.id_procedura_aggiudicaz
left join pbandi_t_progetto l on l.id_progetto = f.id_progetto
left join pbandi_d_tipologia_appalto m on m.id_tipologia_appalto = b.id_tipologia_appalto
join PBANDI_R_DOCU_INDEX_TIPO_STATO n on n.id_documento_index = d.id_documento_index
order by b.id_appalto, d.id_checklist