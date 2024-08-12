select  sum(importo_quietanzato) as totale_importo_rendicontato, 
sum(importo_validato) as totale_importo_validato, 
pagDich.id_dichiarazione_spesa as id_dichiarazione_spesa 
from pbandi_r_pagamento_dich_spesa pagDich,
pbandi_r_pag_quot_parte_doc_sp  rQuotParte, 
PBANDI_T_QUOTA_PARTE_DOC_SPESA quotParte,
PBANDI_T_DICHIARAZIONE_SPESA dich
where pagdich.id_pagamento = rquotparte.id_pagamento
and RQUOTPARTE.ID_QUOTA_PARTE_DOC_SPESA = QUOTPARTE.ID_QUOTA_PARTE_DOC_SPESA
and QUOTPARTE.ID_PROGETTO = DICH.ID_PROGETTO
and DICH.ID_DICHIARAZIONE_SPESA = PAGDICH.ID_DICHIARAZIONE_SPESA
and PAGDICH.ID_DICHIARAZIONE_SPESA = rQuotParte.id_dichiarazione_spesa
group by pagDich.id_dichiarazione_spesa