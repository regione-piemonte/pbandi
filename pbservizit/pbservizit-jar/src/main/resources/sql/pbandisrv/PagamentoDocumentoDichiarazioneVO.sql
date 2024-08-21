/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select dich.id_dichiarazione_spesa,
       pagdoc.id_pagamento,
       pagdoc.id_documento_di_spesa,
       docprj.id_progetto,
       pag.importo_pagamento
from pbandi_t_dichiarazione_spesa dich,
     pbandi_r_pagamento_dich_spesa pagdich,
     pbandi_r_pagamento_doc_spesa pagdoc,
     pbandi_r_doc_spesa_progetto docprj,
     pbandi_t_pagamento pag
where dich.id_dichiarazione_spesa = pagdich.id_dichiarazione_spesa
  and pagdoc.id_pagamento = pagdich.id_pagamento
  and dich.id_progetto = docprj.id_progetto
  and pagdoc.id_documento_di_spesa = docprj.id_documento_di_spesa
  and pag.id_pagamento =  pagdich.id_pagamento