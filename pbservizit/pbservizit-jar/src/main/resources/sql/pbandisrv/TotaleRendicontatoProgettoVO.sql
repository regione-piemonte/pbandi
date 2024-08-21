/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


select dsp.id_progetto,
       sum (case when tipodoc.desc_breve_tipo_doc_spesa = 'NC'
         then -1*qp.IMPORTO_QUOTA_PARTE_DOC_SPESA
         else qp.IMPORTO_QUOTA_PARTE_DOC_SPESA
       end  )as IMPORTO_RENDICONTATO
from pbandi_r_doc_spesa_progetto dsp,
     pbandi_t_documento_di_spesa ds,
     pbandi_d_tipo_documento_spesa tipodoc,
     PBANDI_T_QUOTA_PARTE_DOC_SPESA qp
where dsp.id_documento_di_spesa = ds.id_documento_di_spesa
  and ds.id_tipo_documento_spesa = tipodoc.id_tipo_documento_spesa
  and nvl(trunc(tipodoc.dt_fine_validita), trunc(sysdate+1)) > trunc(sysdate)
  and qp.ID_PROGETTO = dsp.ID_PROGETTO
  and qp.ID_DOCUMENTO_DI_SPESA = dsp.ID_DOCUMENTO_DI_SPESA
  group by dsp.id_progetto