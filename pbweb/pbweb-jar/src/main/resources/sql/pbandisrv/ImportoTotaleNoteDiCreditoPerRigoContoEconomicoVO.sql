select qpds.id_quota_parte_doc_spesa id_quota_parte_doc_spesa,
  ndc.id_rigo_conto_economico id_rigo_conto_economico,
  ndc.totale_note_di_credito totale_note_di_credito
from
  (select notecredito.id_doc_riferimento,
    qtp.id_rigo_conto_economico,
    sum(qtp.importo_quota_parte_doc_spesa) totale_note_di_credito
  from pbandi_t_documento_di_spesa notecredito,
    pbandi_t_quota_parte_doc_spesa qtp
  where qtp.id_documento_di_spesa = notecredito.id_documento_di_spesa
  group by notecredito.id_doc_riferimento,
    qtp.id_rigo_conto_economico
  ) ndc ,
  pbandi_t_quota_parte_doc_spesa qpds
where ndc.id_doc_riferimento = qpds.id_documento_di_spesa