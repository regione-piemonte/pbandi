/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select distinct pa.id_procedura_aggiudicaz,
       pa.cod_proc_agg,
       pa.desc_proc_agg,
       pa.importo,
       pa.id_tipologia_aggiudicaz,
       ta.cod_igrue_t47,
       ta.desc_tipologia_aggiudicazione,
       pa.id_progetto,
       pa.iva,
       pa.cig_proc_agg,
       pa.id_motivo_assenza_cig,
       pa.dt_aggiudicazione,
       nvl(pa.cig_proc_agg, pa.cod_proc_agg) as codice,
       a.oggetto_appalto as oggetto_affidamento
from pbandi_t_procedura_aggiudicaz pa, pbandi_t_appalto a, pbandi_d_tipologia_aggiudicaz ta
where pa.ID_PROCEDURA_AGGIUDICAZ = A.ID_PROCEDURA_AGGIUDICAZ and ta.id_tipologia_aggiudicaz = pa.id_tipologia_aggiudicaz