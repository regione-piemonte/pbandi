select qpds.id_documento_di_spesa,
       qpds.id_progetto,
       qpds.id_rigo_conto_economico,
       rce.id_voce_di_spesa,
       qpds.id_quota_parte_doc_spesa,
       pds.id_pagamento,
       pds.importo_quietanzato,
       qpds.importo_quota_parte_doc_spesa
from  pbandi_r_pag_quot_parte_doc_sp pds,
      pbandi_t_quota_parte_doc_spesa qpds,
      pbandi_t_rigo_conto_economico rce,
      pbandi_d_voce_di_spesa vds
where pds.id_quota_parte_doc_spesa = qpds.id_quota_parte_doc_spesa
  and qpds.id_rigo_conto_economico = rce.id_rigo_conto_economico
  and rce.id_voce_di_spesa = vds.id_voce_di_spesa
  and nvl(trunc(rce.dt_fine_validita), trunc(sysdate+1)) > trunc(sysdate)
  and nvl(trunc(vds.dt_fine_validita), trunc(sysdate+1)) > trunc(sysdate)