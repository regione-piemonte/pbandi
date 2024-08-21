/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

(select d.*, t.desc_breve_tipo_doc_index, t.desc_tipo_doc_index 
from PBANDI_T_DOCUMENTO_INDEX d, PBANDI_C_TIPO_DOCUMENTO_INDEX t 
where d.id_tipo_documento_index = t.id_tipo_documento_index 
)