select PBANDI_R_DOC_SPESA_PROGETTO.id_progetto,
  PBANDI_R_PAGAMENTO_DOC_SPESA.id_documento_di_spesa,
  pbandi_r_pagamento_dich_spesa.id_dichiarazione_spesa,
  PBANDI_T_PAGAMENTO.*
FROM PBANDI_R_PAGAMENTO_DOC_SPESA,
  PBANDI_T_PAGAMENTO,
  PBANDI_R_DOC_SPESA_PROGETTO,
  pbandi_r_pagamento_dich_spesa
WHERE PBANDI_R_PAGAMENTO_DOC_SPESA.id_documento_di_spesa = PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa
AND PBANDI_R_PAGAMENTO_DOC_SPESA.id_pagamento            = PBANDI_T_PAGAMENTO.id_pagamento
and pbandi_r_pagamento_dich_spesa.id_pagamento(+)        = PBANDI_T_PAGAMENTO.id_pagamento