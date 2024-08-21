/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

 SELECT DISTINCT
      dich.id_dichiarazione_spesa,
      dich.id_progetto,
      dich.dt_dichiarazione,
      dich.id_tipo_dichiaraz_spesa,
      dich.periodo_al,
      dich.periodo_dal,
      docprj.id_documento_di_spesa,
      dich.dt_chiusura_validazione,
      dich.note_chiusura_validazione,
      tipo.desc_tipo_dichiarazione_spesa,
      docindex.id_documento_index,
      docindex.nome_file
from  pbandi_t_dichiarazione_spesa dich,
      pbandi_r_pagamento_dich_spesa pagdich,
      pbandi_r_pagamento_doc_spesa pagdoc,
      pbandi_r_doc_spesa_progetto docprj,
      pbandi_d_tipo_dichiaraz_spesa tipo,
      pbandi_t_documento_index docindex
where dich.id_dichiarazione_spesa = pagdich.id_dichiarazione_spesa
  and pagdich.id_pagamento = pagdoc.id_pagamento
  and docprj.id_documento_di_spesa = pagdoc.id_documento_di_spesa
  and docprj.id_progetto = dich.id_progetto
  and tipo.id_tipo_dichiaraz_spesa=dich.id_tipo_dichiaraz_spesa
  and docindex.id_target=dich.id_dichiarazione_spesa
  and docindex.id_Entita=(select id_Entita from pbandi_c_entita where upper(nome_entita) ='PBANDI_T_DICHIARAZIONE_SPESA')