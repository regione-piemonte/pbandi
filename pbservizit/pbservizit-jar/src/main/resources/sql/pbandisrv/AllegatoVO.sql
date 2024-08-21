/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select
CONTENUTO_MICRO_SEZIONE ,
NUM_ORDINAMENTO_MICRO_SEZIONE ,
ID_MACRO_SEZIONE_MODULO,
ID_TIPO_DOCUMENTO_INDEX ,
PROGR_BANDO_LINEA_INTERVENTO,
micro.ID_MICRO_SEZIONE_MODULO
from PBANDI_D_MICRO_SEZIONE_MODULO micro ,
PBANDI_R_BL_TIPO_DOC_SEZ_MOD relMacro,
PBANDI_R_BL_TIPO_DOC_MICRO_SEZ relMicro
where relMacro.PROGR_BL_TIPO_DOC_SEZ_MOD=relMicro.PROGR_BL_TIPO_DOC_SEZ_MOD
and relMicro.ID_MICRO_SEZIONE_MODULO=micro.ID_MICRO_SEZIONE_MODULO
