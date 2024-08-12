select a.id_rettifica_forfett, a.data_inserimento, a.perc_rett,
o.id_categ_anagrafica, o.desc_categ_anagrafica,
b.id_appalto, 
i.id_procedura_aggiudicaz, i.cig_proc_agg, i.cod_proc_agg, i.desc_proc_agg,
e.id_documento_index, e.nome_file,
g1.id_esito id_esito_intermedio, g1.esito esito_intermedio, g1.flag_rettifica flag_rettifica_intermedio,
g2.id_esito id_esito_definitivo, g2.esito esito_definitivo, g2.flag_rettifica flag_rettifica_definitivo,
h.id_proposta_certificaz, h.dt_ora_creazione,
l.id_progetto, l.codice_visualizzato,
m.id_soggetto as id_soggetto_beneficiario, 
n.denominazione_ente_giuridico denominazione_beneficiario
from pbandi_t_rett_forfett a
join pbandi_t_appalto b on b.id_appalto = a.id_appalto
left join pbandi_t_appalto_checklist c on c.id_appalto_checklist = a.id_appalto_checklist
left join pbandi_t_checklist d on d.id_checklist = c.id_checklist
left join pbandi_t_documento_index e on e.id_documento_index = d.id_documento_index
left join pbandi_r_progetti_appalti f on f.id_appalto = b.id_appalto
left join pbandi_t_esiti_note_affidament g1 on g1.id_checklist = d.id_checklist and g1.fase =1
left join pbandi_t_esiti_note_affidament g2 on g2.id_checklist = d.id_checklist and g2.fase =2
left join pbandi_t_proposta_certificaz h on h.id_proposta_certificaz = a.id_proposta_certificaz
left join pbandi_t_procedura_aggiudicaz i on i.id_procedura_aggiudicaz = b.id_procedura_aggiudicaz
left join pbandi_t_progetto l on l.id_progetto = f.id_progetto
left join pbandi_r_soggetto_progetto m on m.id_progetto = f.id_progetto 
                                          and m.id_tipo_anagrafica =1 
                                          and m.id_tipo_beneficiario !=4
left join pbandi_t_ente_giuridico n on n.id_ente_giuridico=m.id_ente_giuridico
left join pbandi_d_categ_anagrafica o on o.id_categ_anagrafica = a.id_categ_anagrafica