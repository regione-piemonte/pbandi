/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT PBANDI_T_QUOTA_PARTE_DOC_SPESA.*,
       PBANDI_D_TIPO_DOCUMENTO_SPESA.desc_breve_tipo_doc_spesa,
       PBANDI_D_TIPO_DOCUMENTO_SPESA.id_tipo_documento_spesa,
       PBANDI_T_DOCUMENTO_DI_SPESA.id_fornitore
FROM PBANDI_T_DOCUMENTO_DI_SPESA,
     PBANDI_T_QUOTA_PARTE_DOC_SPESA,
     PBANDI_D_TIPO_DOCUMENTO_SPESA
WHERE PBANDI_T_DOCUMENTO_DI_SPESA.id_tipo_documento_spesa = PBANDI_D_TIPO_DOCUMENTO_SPESA.id_tipo_documento_spesa
  AND PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_documento_di_spesa = PBANDI_T_DOCUMENTO_DI_SPESA.id_documento_di_spesa