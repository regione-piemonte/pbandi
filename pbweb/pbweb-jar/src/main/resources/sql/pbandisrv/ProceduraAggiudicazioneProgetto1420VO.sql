select distinct paz.id_procedura_aggiudicaz,
       paz.cod_proc_agg,
       paz.desc_proc_agg,
       paz.importo,
       paz.id_tipologia_aggiudicaz,
       ta.cod_igrue_t47,
       ta.desc_tipologia_aggiudicazione,
       paz.id_progetto,
       paz.iva,
       paz.cig_proc_agg,
       paz.id_motivo_assenza_cig,
       paz.dt_aggiudicazione,
       nvl(paz.cig_proc_agg, paz.cod_proc_agg) as codice,
       a.oggetto_appalto as oggetto_affidamento
from pbandi_t_appalto a, pbandi_r_progetti_appalti pa, pbandi_t_procedura_aggiudicaz paz, pbandi_d_tipologia_aggiudicaz ta
where PA.ID_APPALTO = A.ID_APPALTO
and A.ID_PROCEDURA_AGGIUDICAZ = PAZ.ID_PROCEDURA_AGGIUDICAZ
and ta.id_tipologia_aggiudicaz = paz.id_tipologia_aggiudicaz
and PA.ID_TIPO_DOCUMENTO_INDEX = 26
