select * from
( 
  select distinct rbl.progr_bando_linea_intervento,
         rdmp.id_tipo_documento_spesa, 
         tds.desc_breve_tipo_doc_spesa,
         tds.desc_tipo_documento_spesa
  from PBANDI_R_TIPO_DOC_MODALITA_PAG rdmp,
       PBANDI_D_TIPO_DOCUMENTO_SPESA tds,
       PBANDI_R_BANDO_LINEA_INTERVENT rbl
  where rdmp.id_tipo_documento_spesa = tds.id_tipo_documento_spesa
    and (rbl.progr_bando_linea_intervento,rdmp.id_tipo_documento_spesa) not in (
      	  select rebldp.progr_bando_linea_intervento, rebldp.id_tipo_documento_spesa
	      from PBANDI_R_ECCEZ_BAN_LIN_DOC_PAG rebldp
	      where rebldp.flag_aggiunta = 'N'
	      and rebldp.id_modalita_pagamento is null
	    )
    and nvl(trunc(tds.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)
UNION
  select distinct rebldp.progr_bando_linea_intervento, 
         rebldp.id_tipo_documento_spesa,
         tds.desc_breve_tipo_doc_spesa,
         tds.desc_tipo_documento_spesa
  from PBANDI_R_ECCEZ_BAN_LIN_DOC_PAG rebldp,
       PBANDI_D_TIPO_DOCUMENTO_SPESA tds
  where rebldp.id_tipo_documento_spesa = tds.id_tipo_documento_spesa
    and rebldp.id_modalita_pagamento is null
    and rebldp.flag_aggiunta = 'S'
    and nvl(trunc(tds.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)
UNION
  select distinct rbli.progr_bando_linea_intervento,
         dtds.id_tipo_documento_spesa,
         dtds.desc_breve_tipo_doc_spesa,
         dtds.desc_tipo_documento_spesa
  from PBANDI_R_BANDO_LINEA_INTERVENT rbli,
       PBANDI_R_BANDO_VOCE_SPESA rbvs,
       PBANDI_R_BANDO_VOCE_SP_TIP_DOC rbvstd,
       PBANDI_D_TIPO_DOCUMENTO_SPESA dtds
  where rbvs.id_bando = rbli.id_bando
    and rbvstd.id_bando = rbvs.id_bando
    and rbvstd.id_voce_di_spesa = rbvs.id_voce_di_spesa
    and nvl(trunc(rbvstd.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)
    and dtds.id_tipo_documento_spesa = rbvstd.id_tipo_documento_spesa
 )
 WHERE PROGR_BANDO_LINEA_INTERVENTO = ?
 order by DESC_TIPO_DOCUMENTO_SPESA asc