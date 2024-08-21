/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select rs.id_rettifica_di_spesa,
       rs.progr_pag_quot_parte_doc_sp,
       vds.id_voce_di_spesa,
       rpqpds.id_pagamento,
       rpqpds.id_quota_parte_doc_spesa,
       qpds.id_documento_di_spesa,
       qpds.id_progetto,
       p.dt_valuta,
       p.dt_pagamento,
       mp.desc_modalita_pagamento,
       p.importo_pagamento,
       vds.desc_voce_di_spesa,
       rs.dt_rettifica,
       rs.importo_rettifica,
       rs.riferimento, 
       rs.id_utente_ins,
       u.codice_utente as codice_fiscale_soggetto
from PBANDI_T_RETTIFICA_DI_SPESA rs,
     PBANDI_R_PAG_QUOT_PARTE_DOC_SP rpqpds,
     PBANDI_T_QUOTA_PARTE_DOC_SPESA qpds,
     PBANDI_T_RIGO_CONTO_ECONOMICO rce,
     PBANDI_D_VOCE_DI_SPESA vds,
     PBANDI_T_UTENTE u,
     /*PBANDI_T_SOGGETTO s,*/
     PBANDI_T_PAGAMENTO p,
     PBANDI_D_MODALITA_PAGAMENTO mp
where rs.progr_pag_quot_parte_doc_sp = rpqpds.progr_pag_quot_parte_doc_sp
  and rpqpds.id_quota_parte_doc_spesa = qpds.id_quota_parte_doc_spesa
  and rce.id_rigo_conto_economico = qpds.id_rigo_conto_economico
  and vds.id_voce_di_spesa = rce.id_voce_di_spesa
  and u.id_utente = rs.id_utente_ins
 /* and s.id_soggetto = u.id_soggetto */
  and p.id_pagamento = rpqpds.id_pagamento
  and mp.id_modalita_pagamento = p.id_modalita_pagamento