  select distinct rbl.progr_bando_linea_intervento,
         mp.id_modalita_pagamento, 
         rdmp.id_tipo_documento_spesa, 
         mp.desc_breve_modalita_pagamento, 
         mp.desc_modalita_pagamento,
         tds.desc_breve_tipo_doc_spesa,
         tds.desc_tipo_documento_spesa
  from PBANDI_R_TIPO_DOC_MODALITA_PAG rdmp,
       PBANDI_D_MODALITA_PAGAMENTO mp,
       PBANDI_D_TIPO_DOCUMENTO_SPESA tds,
       PBANDI_R_BANDO_LINEA_INTERVENT rbl
  where rdmp.id_modalita_pagamento = mp.id_modalita_pagamento
    and rdmp.id_tipo_documento_spesa = tds.id_tipo_documento_spesa
    and (rbl.progr_bando_linea_intervento,rdmp.id_tipo_documento_spesa,  mp.id_modalita_pagamento) not in (
      select rebldp.progr_bando_linea_intervento,rebldp.id_tipo_documento_spesa,rebldp.id_modalita_pagamento
      from PBANDI_R_ECCEZ_BAN_LIN_DOC_PAG rebldp
      where rebldp.flag_aggiunta = 'N'
    )
    and nvl(trunc(mp.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)
    and nvl(trunc(tds.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)
UNION
  select distinct rebldp.progr_bando_linea_intervento, 
         rebldp.id_modalita_pagamento, 
         rebldp.id_tipo_documento_spesa,
         mp.desc_breve_modalita_pagamento,
         mp.desc_modalita_pagamento,
         tds.desc_breve_tipo_doc_spesa,
         tds.desc_tipo_documento_spesa
  from PBANDI_R_ECCEZ_BAN_LIN_DOC_PAG rebldp,
       PBANDI_D_MODALITA_PAGAMENTO mp,
       PBANDI_D_TIPO_DOCUMENTO_SPESA tds
  where rebldp.id_tipo_documento_spesa = tds.id_tipo_documento_spesa
    and rebldp.id_modalita_pagamento = mp.id_modalita_pagamento(+)
    and rebldp.flag_aggiunta = 'S'
    and nvl(trunc(mp.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)
    and nvl(trunc(tds.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)
