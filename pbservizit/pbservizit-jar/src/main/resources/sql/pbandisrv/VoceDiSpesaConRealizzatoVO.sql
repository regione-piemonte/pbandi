/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select
 qp.ID_PROGETTO,
 to_char(qp.DT_PERIODO, 'YYYY') PERIODO,
 qp.ID_RIGO_CONTO_ECONOMICO ID_RIGO_CONTO_ECONOMICO,
 rigo.ID_VOCE_DI_SPESA,
 vds.ID_VOCE_DI_SPESA_PADRE,
 vds.DESC_VOCE_DI_SPESA,
 vdsP.DESC_VOCE_DI_SPESA DESC_VOCE_DI_SPESA_PADRE,
 sum(qp.REALIZZATO) REALIZZATO
from
(
 /* QUERY GENERALE */
 select distinct
  docAll.*,
  case when docAll.R_OR_V = 'R' then R.DT_PERIODO else V.DT_PERIODO end DT_PERIODO,
  case when docAll.R_OR_V = 'R' then R.ID_RIGO_CONTO_ECONOMICO else V.ID_RIGO_CONTO_ECONOMICO end ID_RIGO_CONTO_ECONOMICO,
  case when docAll.R_OR_V = 'R' then R.RIPARTITO else V.VALIDATO end REALIZZATO
 from
  (
  /* QUERY PER DETERMINARE SE PER IL DOC OCCORRE PRENDERE IL RIPARTITO O IL VALIDATO */
  select
   docRV.ID_PROGETTO,
   docRV.ID_DOCUMENTO_DI_SPESA,
   case
    when
     nvl2(docRV.IMPORTO_TOTALE_DOCUMENTO, docRV.IMPORTO_TOTALE_DOCUMENTO, 0) <=
     nvl2(docRV.TOT_PAGAMENTI, docRV.TOT_PAGAMENTI, 0) + nvl2(docRV.IMPORTO_NOTE, docRV.IMPORTO_NOTE, 0) and
     docRV.DESC_BREVE_STATO_DOC_SPESA <> 'I'
    then
     'V'
    else
     'R'
   end R_OR_V
  from
   (
   select distinct
    docNote.*,
    sum(pag.IMPORTO_PAGAMENTO) over (partition by docNote.ID_DOCUMENTO_DI_SPESA) TOT_PAGAMENTI
   from
   (
    select distinct
     docPro.ID_PROGETTO,
     doc.ID_DOCUMENTO_DI_SPESA,
     statoDoc.DESC_BREVE_STATO_DOC_SPESA,
     doc.IMPORTO_TOTALE_DOCUMENTO,
     sum(note.IMPORTO_TOTALE_DOCUMENTO) over (partition by doc.ID_DOCUMENTO_DI_SPESA) IMPORTO_NOTE
    from
     pbandi_t_documento_di_spesa doc,
     pbandi_d_stato_documento_spesa statoDoc,
     pbandi_r_doc_spesa_progetto docPro,
     pbandi_t_documento_di_spesa note
    where
     docPro.ID_STATO_DOCUMENTO_SPESA = statoDoc.ID_STATO_DOCUMENTO_SPESA and
     doc.ID_DOCUMENTO_DI_SPESA = docPro.ID_DOCUMENTO_DI_SPESA and
     doc.ID_TIPO_DOCUMENTO_SPESA <> 4 and
     doc.ID_DOCUMENTO_DI_SPESA = note.ID_DOC_RIFERIMENTO(+)
    ) docNote,
    pbandi_r_pagamento_doc_spesa pagDoc,
    pbandi_t_pagamento pag,
    PBANDI_R_DOC_SPESA_PROGETTO docPrj
   WHERE
    docNote.ID_PROGETTO = docPrj.ID_PROGETTO and
    docNote.ID_DOCUMENTO_DI_SPESA = docPrj.ID_DOCUMENTO_DI_SPESA and
    docNote.ID_DOCUMENTO_DI_SPESA = pagDoc.ID_DOCUMENTO_DI_SPESA(+) and
    PAGDOC.ID_PAGAMENTO = PAG.ID_PAGAMENTO(+)
   ) docRV
  ) docAll,
  (
  /* QUERY PER IL RIPARTITO */
  select distinct
   docQuo.ID_PROGETTO,
   doc.ID_DOCUMENTO_DI_SPESA,
   doc.DT_EMISSIONE_DOCUMENTO DT_PERIODO,
   docQuo.ID_RIGO_CONTO_ECONOMICO,
   sum(nvl2(docQuo.IMPORTO_QUOTA_PARTE_DOC_SPESA, docQuo.IMPORTO_QUOTA_PARTE_DOC_SPESA, 0)) RIPARTITO
  from
   (
   select distinct
    docPro.ID_PROGETTO,
    case when doc.ID_TIPO_DOCUMENTO_SPESA <> 4 then doc.ID_DOCUMENTO_DI_SPESA else doc.ID_DOC_RIFERIMENTO end ID_DOCUMENTO_DI_SPESA,
    quoDoc.ID_RIGO_CONTO_ECONOMICO,
    case when doc.ID_TIPO_DOCUMENTO_SPESA <> 4 then quoDoc.IMPORTO_QUOTA_PARTE_DOC_SPESA else -quoDoc.IMPORTO_QUOTA_PARTE_DOC_SPESA end IMPORTO_QUOTA_PARTE_DOC_SPESA
   from
    pbandi_t_documento_di_spesa doc,
    pbandi_r_doc_spesa_progetto docPro,
    PBANDI_T_QUOTA_PARTE_DOC_SPESA quoDoc
   where
    doc.ID_DOCUMENTO_DI_SPESA = docPro.ID_DOCUMENTO_DI_SPESA and
    docPro.ID_DOCUMENTO_DI_SPESA = quoDoc.ID_DOCUMENTO_DI_SPESA and
    docPro.ID_PROGETTO = quoDoc.ID_PROGETTO
   ) docQuo,
   pbandi_t_documento_di_spesa doc
  where
   docQuo.ID_DOCUMENTO_DI_SPESA = doc.ID_DOCUMENTO_DI_SPESA
  group by
   docQuo.ID_PROGETTO,
   doc.ID_DOCUMENTO_DI_SPESA,
   doc.DT_EMISSIONE_DOCUMENTO,
   docQuo.ID_RIGO_CONTO_ECONOMICO
  ) R,
  (
  /* QUERY PER IL VALIDATO */
  select distinct
   docPrj.ID_PROGETTO,
   docPrj.ID_DOCUMENTO_DI_SPESA,
   nvl2(pag.DT_VALUTA, pag.DT_VALUTA, nvl2(pag.DT_PAGAMENTO, pag.DT_PAGAMENTO, doc.DT_EMISSIONE_DOCUMENTO)) DT_PERIODO,
   quoDoc.ID_RIGO_CONTO_ECONOMICO,
   sum(nvl2(pagQuoDoc.IMPORTO_VALIDATO, pagQuoDoc.IMPORTO_VALIDATO, 0)) VALIDATO
  FROM
   PBANDI_T_DOCUMENTO_DI_SPESA doc,
   PBANDI_R_DOC_SPESA_PROGETTO docPrj,
   PBANDI_R_PAGAMENTO_DOC_SPESA pagDoc,
   PBANDI_T_PAGAMENTO pag,
   PBANDI_T_QUOTA_PARTE_DOC_SPESA quoDoc,
   PBANDI_R_PAG_QUOT_PARTE_DOC_SP pagQuoDoc
  WHERE
   DOC.ID_DOCUMENTO_DI_SPESA = docPrj.ID_DOCUMENTO_DI_SPESA and
   docPrj.ID_DOCUMENTO_DI_SPESA = pagDoc.ID_DOCUMENTO_DI_SPESA and
   pagDoc.ID_PAGAMENTO = pag.ID_PAGAMENTO and
   pagDoc.ID_DOCUMENTO_DI_SPESA = quoDoc.ID_DOCUMENTO_DI_SPESA and
   docPrj.ID_PROGETTO = quoDoc.ID_PROGETTO and
   pagDoc.ID_PAGAMENTO = pagQuoDoc.ID_PAGAMENTO and
   QUODOC.ID_QUOTA_PARTE_DOC_SPESA = PAGQUODOC.ID_QUOTA_PARTE_DOC_SPESA
  group by
   docPrj.ID_PROGETTO,
   docPrj.ID_DOCUMENTO_DI_SPESA,
   nvl2(pag.DT_VALUTA, pag.DT_VALUTA, nvl2(pag.DT_PAGAMENTO, pag.DT_PAGAMENTO, doc.DT_EMISSIONE_DOCUMENTO)),
   QUODOC.ID_RIGO_CONTO_ECONOMICO
  ) V
 where
  docAll.ID_PROGETTO = R.ID_PROGETTO(+) and
  docAll.ID_DOCUMENTO_DI_SPESA = R.ID_DOCUMENTO_DI_SPESA(+) and
  docAll.ID_PROGETTO = V.ID_PROGETTO(+) and
  docAll.ID_DOCUMENTO_DI_SPESA = V.ID_DOCUMENTO_DI_SPESA(+)
 ) qp,
 PBANDI_T_RIGO_CONTO_ECONOMICO rigo,
 PBANDI_D_VOCE_DI_SPESA vds,
 PBANDI_D_VOCE_DI_SPESA vdsP 
where
 qp.ID_RIGO_CONTO_ECONOMICO = rigo.ID_RIGO_CONTO_ECONOMICO and
 rigo.ID_VOCE_DI_SPESA = vds.ID_VOCE_DI_SPESA and
 VDS.ID_VOCE_DI_SPESA_PADRE = VDSP.ID_VOCE_DI_SPESA(+) 
group by
 qp.ID_PROGETTO,
 to_char(qp.DT_PERIODO, 'YYYY'),
 qp.ID_RIGO_CONTO_ECONOMICO,
 rigo.ID_VOCE_DI_SPESA,
 vds.ID_VOCE_DI_SPESA_PADRE,
 vds.DESC_VOCE_DI_SPESA,
 vdsP.DESC_VOCE_DI_SPESA
ORDER BY
 PERIODO, DESC_VOCE_DI_SPESA_PADRE,DESC_VOCE_DI_SPESA