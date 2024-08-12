SELECT PBANDI_T_DOCUMENTO_DI_SPESA.*,
  PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_BREVE_TIPO_DOC_SPESA,
  PBANDI_T_FORNITORE.codice_fiscale_fornitore,
  PBANDI_T_FORNITORE.id_soggetto_fornitore
FROM PBANDI_T_DOCUMENTO_DI_SPESA,
  PBANDI_T_FORNITORE,
  PBANDI_D_TIPO_DOCUMENTO_SPESA
WHERE PBANDI_T_DOCUMENTO_DI_SPESA.id_fornitore                         = PBANDI_T_FORNITORE.id_fornitore
AND PBANDI_T_DOCUMENTO_DI_SPESA.id_tipo_documento_spesa                = PBANDI_D_TIPO_DOCUMENTO_SPESA.id_tipo_documento_spesa
AND nvl(trunc(PBANDI_T_FORNITORE.DT_FINE_VALIDITA), trunc(sysdate           +1)) > trunc(sysdate)
AND nvl(trunc(PBANDI_D_TIPO_DOCUMENTO_SPESA.DT_FINE_VALIDITA), trunc(sysdate+1)) > trunc(sysdate)