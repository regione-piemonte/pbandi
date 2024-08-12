SELECT DISTINCT PBANDI_T_DICHIARAZIONE_SPESA.id_dichiarazione_spesa,
  (SELECT SUM(pbandi_t_pagamento.importo_pagamento)
  FROM pbandi_t_pagamento,
    pbandi_r_pagamento_doc_spesa tot_doc
  WHERE tot_doc.id_documento_di_spesa = pbandi_r_pagamento_doc_spesa.id_documento_di_spesa
  AND tot_doc.id_pagamento            = pbandi_t_pagamento.id_pagamento
  ) AS TOTALE_PAGAMENTI,
  (SELECT pbandi_t_fornitore.codice_fiscale_fornitore
  FROM pbandi_t_fornitore
  WHERE pbandi_t_fornitore.id_fornitore = doc.id_fornitore
  ) AS codice_fiscale_fornitore,
  pbandi_r_doc_spesa_progetto.task,
  pbandi_r_doc_spesa_progetto.importo_rendicontazione,
  pbandi_d_tipo_documento_spesa.desc_tipo_documento_spesa,
  doc.*
FROM PBANDI_T_DICHIARAZIONE_SPESA,
  PBANDI_R_PAGAMENTO_DICH_SPESA,
  PBANDI_R_PAGAMENTO_DOC_SPESA,
  pbandi_t_documento_di_spesa doc,
  pbandi_r_doc_spesa_progetto,
  pbandi_d_tipo_documento_spesa
WHERE PBANDI_R_PAGAMENTO_DICH_SPESA.id_dichiarazione_spesa   = PBANDI_T_DICHIARAZIONE_SPESA.id_dichiarazione_spesa
AND PBANDI_R_PAGAMENTO_DOC_SPESA.id_pagamento                = PBANDI_R_PAGAMENTO_DICH_SPESA.id_pagamento
AND doc.id_documento_di_spesa                                = pbandi_r_pagamento_doc_spesa.id_documento_di_spesa
AND doc.id_documento_di_spesa                                = pbandi_r_doc_spesa_progetto.id_documento_di_spesa
AND pbandi_r_doc_spesa_progetto.id_progetto                  = PBANDI_T_DICHIARAZIONE_SPESA.id_progetto
AND pbandi_d_tipo_documento_spesa.id_tipo_documento_spesa(+) = doc.id_tipo_documento_spesa
