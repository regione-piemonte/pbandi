/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

with tqp as (select tqp.id_quota_parte_doc_spesa,
                    tqp.id_rigo_conto_economico,
                    tqp.id_documento_di_spesa,
                    tqp.importo_quota_parte_doc_spesa,
                    rce.id_voce_di_entrata,
                    sum(rpqp.importo_quietanzato) as importo_quietanzato,
                    sum(rpqp.importo_validato)    as importo_validato
             from pbandi_t_quota_parte_doc_spesa tqp,
                  pbandi_r_pag_quot_parte_doc_sp rpqp,
                  pbandi_t_rigo_conto_economico rce
             where tqp.id_quota_parte_doc_spesa = rpqp.id_quota_parte_doc_spesa (+)
               and tqp.id_rigo_conto_economico (+) = rce.id_rigo_conto_economico
             group by tqp.id_quota_parte_doc_spesa,
                      tqp.id_rigo_conto_economico,
                      tqp.importo_quota_parte_doc_spesa,
                      tqp.id_documento_di_spesa,
                      rce.id_voce_di_entrata),

     tce as (select tce.*,
                    rbl.id_bando
             from pbandi_t_conto_economico tce,
                  pbandi_t_domanda td,
                  pbandi_r_bando_linea_intervent rbl
             where tce.id_domanda = td.id_domanda
               and td.progr_bando_linea_intervento = rbl.progr_bando_linea_intervento),
     trce as (select trce.*
              from pbandi_t_rigo_conto_economico trce
              where trce.dt_fine_validita is null),
     rbvs as (select tce.*,
                     dvs.descrizione,
                     dvs.descrizione_breve,
                     rbvs.id_voce_di_entrata,
                     dvs.flag_edit
              from pbandi_d_voce_di_entrata dvs,
                   tce,
                   pbandi_r_bando_voce_entrata rbvs
              where dvs.id_voce_di_entrata = rbvs.id_voce_di_entrata
                and tce.id_bando = rbvs.id_bando)

select rbvs.*,
       trce.id_rigo_conto_economico,
       trce.importo_spesa                 as importo_richiesto,
       trce.importo_ammesso_finanziamento as importo_ammesso,
       trce.completamento
from rbvs,
     trce
where rbvs.id_voce_di_entrata = trce.id_voce_di_entrata (+)
  and rbvs.id_conto_economico = trce.id_conto_economico (+)
  and rbvs.id_conto_economico = ?
order by rbvs.id_conto_economico,
         rbvs.descrizione



