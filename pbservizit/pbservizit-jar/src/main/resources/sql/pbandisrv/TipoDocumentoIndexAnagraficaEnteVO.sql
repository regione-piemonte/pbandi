/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select distinct docindex_anagrafica.*, 
docindex_ente_soggetto.id_ente_competenza,
docindex_ente_soggetto.id_soggetto,
docindex_ente_soggetto.progr_bando_linea_intervento
from (
  select rdita.id_tipo_documento_index, 
       rdita.id_tipo_anagrafica,
       tdi.desc_breve_tipo_doc_index, 
       tdi.desc_tipo_doc_index,
       ta.desc_breve_tipo_anagrafica
from PBANDI_R_DOC_INDEX_TIPO_ANAG rdita, 
     PBANDI_D_TIPO_ANAGRAFICA ta, 
     PBANDI_C_TIPO_DOCUMENTO_INDEX tdi
where ta.id_tipo_anagrafica = rdita.id_tipo_anagrafica
  and tdi.id_tipo_documento_index = rdita.id_tipo_documento_index
) docindex_anagrafica,
(
  select rtdib.id_tipo_documento_index, 
       rtdib.progr_bando_linea_intervento,
       ts.id_ente_competenza,
       ts.id_soggetto
from PBANDI_R_TP_DOC_IND_BAN_LI_INT rtdib,
     PBANDI_R_BANDO_LINEA_ENTE_COMP rblec,
   (
    select ts1.*,
        tec.id_ente_competenza,
        rsta.id_tipo_anagrafica,
        rsta.dt_inizio_validita,
        rsta.dt_fine_validita,
        rsta.flag_aggiornato_flux
      from pbandi_t_soggetto ts1,
        pbandi_t_ente_competenza tec,
        pbandi_r_sogg_tipo_anagrafica rsta
      where rsta.id_soggetto = ts1.id_soggetto
        and rsta.id_tipo_anagrafica not in 
           (
              select dta.id_tipo_anagrafica
                from pbandi_d_tipo_anagrafica dta
               where dta.desc_breve_tipo_anagrafica in ('PERSONA-FISICA', 'OI-ISTRUTTORE', 'ADG-ISTRUTTORE')
            )
         and ( rsta.id_tipo_anagrafica not in 
              (
                select dta.id_tipo_anagrafica
                  from pbandi_d_tipo_anagrafica dta
                 where dta.desc_breve_tipo_anagrafica in ('BEN-MASTER', 'OI-IST-MASTER', 'ADG-IST-MASTER')
              )
              or ( not exists (select 'x' from pbandi_r_ente_competenza_sogg recs where recs.id_soggetto = ts1.id_soggetto)
                   or exists (select 'x' from pbandi_r_ente_competenza_sogg recs where recs.id_soggetto = ts1.id_soggetto and recs.id_ente_competenza = tec.id_ente_competenza)
                 )
             )
    union
      select distinct ts1.*,
         rblec.id_ente_competenza,
         rsta.id_tipo_anagrafica,
         rsta.dt_inizio_validita,
         rsta.dt_fine_validita,
         rsta.flag_aggiornato_flux
      from pbandi_t_soggetto ts1,
           pbandi_r_sogg_tipo_anagrafica rsta,
           pbandi_r_soggetto_progetto rsp,
           pbandi_t_progetto tp,
           pbandi_t_domanda td,
           pbandi_r_bando_linea_ente_comp rblec
      where rsta.id_soggetto = ts1.id_soggetto
        and rsta.id_tipo_anagrafica  in 
         (
             select dta.id_tipo_anagrafica
              from pbandi_d_tipo_anagrafica dta
             where dta.desc_breve_tipo_anagrafica  in ('PERSONA-FISICA', 'OI-ISTRUTTORE', 'ADG-ISTRUTTORE')
          )
        and rsp.id_soggetto = ts1.id_soggetto
        and tp.id_progetto = rsp.id_progetto
        and td.id_domanda = tp.id_domanda
        and rblec.progr_bando_linea_intervento = td.progr_bando_linea_intervento
	 union
     -- Soggetti abilitati per bando linea
	 SELECT ts1.*,
	        rblec.id_ente_competenza,
	        rsta.id_tipo_anagrafica,
            rsta.dt_inizio_validita,
            rsta.dt_fine_validita,
            rsta.flag_aggiornato_flux
	   FROM pbandi_t_soggetto ts1,  
	        PBANDI_R_SOGG_BANDO_LIN_INT sbli,
			pbandi_r_sogg_tipo_anagrafica rsta,
           pbandi_r_bando_linea_ente_comp rblec
	  WHERE sbli.id_soggetto = ts1.id_soggetto
	    AND rsta.id_soggetto = ts1.id_soggetto
		and rblec.progr_bando_linea_intervento = sbli.progr_bando_linea_intervento
   )  ts
where 
   ts.id_ente_competenza = rblec.id_ente_competenza
  and rtdib.progr_bando_linea_intervento = rblec.progr_bando_linea_intervento
)  docindex_ente_soggetto
where docindex_anagrafica.id_tipo_documento_index = docindex_ente_soggetto.id_tipo_documento_index