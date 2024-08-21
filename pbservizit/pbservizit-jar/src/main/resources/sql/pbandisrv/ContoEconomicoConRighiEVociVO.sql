/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select rbvs.*,
       trce.id_rigo_conto_economico,
       trce.importo_spesa importo_richiesto,
       trce.importo_ammesso_finanziamento importo_ammesso,
       trce.importo_rendicontato,
       trce.importo_quietanzato,
       trce.importo_validato,
       sp.IMPORTO_SPESA_PREVENTIVATA,
       trce.completamento
from
 PBANDI_T_SPESA_PREVENTIVATA sp,
    (
        select tce.*,
               rbvs.massimo_importo_ammissibile,
               rbvs.progr_ordinamento,
               dvs.desc_voce_di_spesa,
               rbvs.id_voce_di_spesa,
               tvs.id_tipologia_voce_di_spesa,
               tvs.descrizione as desc_tipologia_voce_di_spesa,
               tvs.perc_quota_contributo,
               dvs.FLAG_EDIT,
               dvs.id_voce_di_spesa_padre
        from pbandi_d_voce_di_spesa dvs,
             pbandi_d_tipol_voce_di_spesa tvs,
             (
                 select tce.*,
                        rbl.id_bando
                 from pbandi_t_conto_economico tce,
                      pbandi_t_domanda td,
                      pbandi_r_bando_linea_intervent rbl
                 where tce.id_domanda = td.id_domanda
                   and td.progr_bando_linea_intervento = rbl.progr_bando_linea_intervento
             ) tce,
             pbandi_r_bando_voce_spesa rbvs
        where dvs.id_voce_di_spesa = rbvs.id_voce_di_spesa
          and tce.id_bando = rbvs.id_bando
          and tvs.id_tipologia_voce_di_spesa (+) = dvs.id_tipologia_voce_di_spesa
    ) rbvs,
    (
        select trce.*,
               qp.importo_rendicontato,
               qp.importo_quietanzato,
               qp.importo_validato
        from pbandi_t_rigo_conto_economico trce,
             (
                 select id_rigo_conto_economico,
                        sum(importo_quota_parte_doc_spesa) importo_rendicontato,
                        sum(importo_quietanzato) importo_quietanzato,
                        sum(importo_validato) importo_validato
                 from
                     (
                         select tqp.id_rigo_conto_economico,
                                case when dtds.desc_breve_tipo_doc_spesa = 'NC'
                                         then -1 * tqp.importo_quota_parte_doc_spesa
                                     else tqp.importo_quota_parte_doc_spesa
                                    end importo_quota_parte_doc_spesa,
                                tqp.importo_quietanzato,
                                tqp.importo_validato
                         from
                             (
                                 select tqp.id_quota_parte_doc_spesa,
                                        tqp.id_rigo_conto_economico,
                                        tqp.id_documento_di_spesa,
                                        tqp.importo_quota_parte_doc_spesa,
                                        sum(rpqp.importo_quietanzato) importo_quietanzato,
                                        sum(rpqp.importo_validato) importo_validato
                                 from pbandi_t_quota_parte_doc_spesa tqp,
                                      pbandi_r_pag_quot_parte_doc_sp rpqp
                                 where tqp.id_quota_parte_doc_spesa = rpqp.id_quota_parte_doc_spesa (+)
                                 group by tqp.id_quota_parte_doc_spesa,
                                          tqp.id_rigo_conto_economico,
                                          tqp.importo_quota_parte_doc_spesa,
                                          tqp.id_documento_di_spesa
                             ) tqp,
                             pbandi_t_documento_di_spesa tds,
                             pbandi_d_tipo_documento_spesa dtds
                         where tds.id_documento_di_spesa = tqp.id_documento_di_spesa
                           and dtds.id_tipo_documento_spesa = tds.id_tipo_documento_spesa
                     )
                 group by id_rigo_conto_economico
             ) qp
        where trce.id_rigo_conto_economico = qp.id_rigo_conto_economico (+)
          and trce.dt_fine_validita is null
    ) trce
where rbvs.id_voce_di_spesa = trce.id_voce_di_spesa (+)
  and rbvs.id_conto_economico = trce.id_conto_economico (+)
  and sp.id_rigo_conto_economico (+) = trce.id_rigo_conto_economico
order by rbvs.id_conto_economico,
         rbvs.id_voce_di_spesa_padre desc nulls first,
         rbvs.progr_ordinamento,
         rbvs.desc_voce_di_spesa