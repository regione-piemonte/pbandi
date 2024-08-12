select distinct ds.id_dichiarazione_spesa, 
       ds.id_progetto,
       pdocs.id_documento_di_spesa,
       sum (pagquotdocs.importo_validato) as importo_totale_validato
from PBANDI_T_DICHIARAZIONE_SPESA ds,
     (
      select distinct
           pds.ID_PAGAMENTO,
           first_value(ds.ID_DICHIARAZIONE_SPESA) over (partition by pds.ID_PAGAMENTO order by ds.DT_DICHIARAZIONE desc, ds.ID_DICHIARAZIONE_SPESA desc) as ID_DICHIARAZIONE_SPESA
        from
           PBANDI_R_PAGAMENTO_DICH_SPESA pds,
           PBANDI_T_DICHIARAZIONE_SPESA ds
        where pds.ID_DICHIARAZIONE_SPESA = ds.ID_DICHIARAZIONE_SPESA
     ) pdichs,
     PBANDI_R_PAGAMENTO_DOC_SPESA pdocs,
     PBANDI_T_QUOTA_PARTE_DOC_SPESA qpDocs,
     PBANDI_R_PAG_QUOT_PARTE_DOC_SP pagQuotDocs
where ds.id_dichiarazione_spesa = pdichs.id_dichiarazione_spesa
  and pdichs.id_pagamento = pdocs.id_pagamento
  and qpdocs.id_documento_di_spesa = pdocs.id_documento_di_spesa
  and qpdocs.id_progetto = ds.id_progetto
  and pagQuotDocs.id_pagamento = pdichs.id_pagamento
  and pagquotdocs.id_quota_parte_doc_spesa = qpdocs.id_quota_parte_doc_spesa
  group by ds.id_dichiarazione_spesa, 
       ds.id_progetto,
       pdocs.id_documento_di_spesa