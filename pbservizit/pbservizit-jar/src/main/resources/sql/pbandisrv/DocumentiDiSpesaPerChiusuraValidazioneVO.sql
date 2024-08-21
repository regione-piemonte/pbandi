/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select distinct documento.id_documento_di_spesa,
       documentidichiarazione.id_dichiarazione_spesa,
       documentidichiarazione.id_progetto,
       documentidichiarazione.id_stato_documento_spesa,
       documentidichiarazione.desc_breve_stato_doc_spesa,
       documentidichiarazione.desc_stato_documento_spesa,
       documentidichiarazione.id_stato_documento_spesa_valid,
        documentidichiarazione.desc_breve_stato_doc_valid,
       documentidichiarazione.desc_stato_documento_valid,
       documento.numero_documento,
       documento.dt_emissione_documento, 
       documento.importo_totale_documento, 
       documento.id_tipo_documento_spesa,
       documento.desc_breve_tipo_doc_spesa,
       documento.desc_tipo_documento_spesa,
       documento.note_credito_totale,
       documento.note_credito_totale_ren,
       documento.totale_rendicontabili,
       documento.importo_rendicontazione,
       pagvalidati.totale_validato,
       pagvalidati.totale_validato_prj,
       pagamenti.totale_pagamenti
from 
(
  select docprj.id_documento_di_spesa,
         docprj.id_progetto,
         doc.numero_documento,
         doc.DT_EMISSIONE_DOCUMENTO, 
         doc.IMPORTO_TOTALE_DOCUMENTO, 
         doc.ID_TIPO_DOCUMENTO_SPESA,
         tipodoc.desc_breve_tipo_doc_spesa,
         tipodoc.desc_tipo_documento_spesa,
         note.note_credito_totale,
         note.note_credito_totale_ren,
         sum(docprj.importo_rendicontazione) over (partition by docprj.id_documento_di_spesa)  as totale_rendicontabili,
         docprj.importo_rendicontazione
  from pbandi_r_doc_spesa_progetto docprj,
       pbandi_d_tipo_documento_spesa tipodoc,
       pbandi_t_documento_di_spesa doc 
        left outer join (
          select nc.id_doc_riferimento,
                 sum( nc.importo_totale_documento) as note_credito_totale,
                  sum( nvl(nc.imponibile,0) + nvl(nc.importo_iva_costo,0)) as note_credito_totale_ren
          from pbandi_t_documento_di_spesa nc
          where nc.id_doc_riferimento is not null   
          group by  nc.id_doc_riferimento
        ) note
        on doc.id_documento_di_spesa = note.id_doc_riferimento
  where docprj.id_documento_di_spesa = doc.id_documento_di_spesa
    and doc.id_tipo_documento_spesa = tipodoc.id_tipo_documento_spesa
    /*
  group by docprj.id_documento_di_spesa,
         doc.numero_documento,
         doc.DT_EMISSIONE_DOCUMENTO, 
         doc.IMPORTO_TOTALE_DOCUMENTO, 
         doc.ID_TIPO_DOCUMENTO_SPESA,
         tipodoc.desc_breve_tipo_doc_spesa,
         tipodoc.desc_tipo_documento_spesa,
          note.note_credito_totale,
         note.note_credito_totale_ren
         */
) documento,
(
    select quodoc.id_documento_di_spesa,
           quodoc.id_progetto,
          sum(pagquodoc.importo_validato) over (partition by quodoc.id_documento_di_spesa, quodoc.id_progetto)  as totale_validato_prj,
          sum(pagquodoc.importo_validato) over (partition by quodoc.id_documento_di_spesa)  as totale_validato
    from pbandi_t_quota_parte_doc_spesa quodoc,
         pbandi_r_pag_quot_parte_doc_sp pagquodoc
    where pagquodoc.id_quota_parte_doc_spesa = quodoc.id_quota_parte_doc_spesa
    /*group by quodoc.id_documento_di_spesa,quodoc.id_progetto*/
) pagvalidati,
(
    select pagdoc.id_documento_di_spesa,
        sum(pag.importo_pagamento) as totale_pagamenti
    from pbandi_r_pagamento_doc_spesa pagdoc,
         pbandi_t_pagamento pag
    where pagdoc.id_pagamento = pag.id_pagamento
    group by  pagdoc.id_documento_di_spesa
) pagamenti,
(
 select distinct pagdich.id_dichiarazione_spesa,
         dich.id_progetto,
         pagdoc.id_documento_di_spesa,
         docprj.id_stato_documento_spesa,
         statodoc.desc_breve_stato_doc_spesa,
         statodoc.desc_stato_documento_spesa,
         docprj.id_stato_documento_spesa_valid,
         statodocval.desc_breve_stato_doc_spesa as desc_breve_stato_doc_valid,
         statodocval.desc_stato_documento_spesa as desc_stato_documento_valid
    from pbandi_r_pagamento_dich_spesa pagdich,
         pbandi_r_pagamento_doc_spesa pagdoc,
         pbandi_r_doc_spesa_progetto docprj 
           left outer join pbandi_d_stato_documento_spesa statodocval
           on docprj.id_stato_documento_spesa_valid = statodocval.id_stato_documento_spesa,
         pbandi_t_dichiarazione_spesa dich,
         pbandi_d_stato_documento_spesa statodoc
    where pagdich.id_pagamento = pagdoc.id_pagamento
      and docprj.id_progetto = dich.id_progetto
      and docprj.id_documento_di_spesa = pagdoc.id_documento_di_spesa
      and pagdich.id_dichiarazione_spesa = dich.id_dichiarazione_spesa
      and statodoc.id_stato_documento_spesa = docprj.id_stato_documento_spesa
    -- and  pagdich.id_dichiarazione_spesa = :idDichiarazioneSpesa
) documentidichiarazione
where documento.id_documento_di_spesa = pagvalidati.id_documento_di_spesa
  and documento.id_documento_di_spesa = pagamenti.id_documento_di_spesa
  and documento.id_documento_di_spesa = documentidichiarazione.id_documento_di_spesa
  and documento.id_progetto = pagvalidati.id_progetto
  and documentidichiarazione.id_progetto = documento.id_progetto

