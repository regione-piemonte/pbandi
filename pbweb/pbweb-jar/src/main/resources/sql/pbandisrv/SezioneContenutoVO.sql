select distinct c.CONTENUTO_MICRO_SEZIONE, a.ID_TIPO_DOCUMENTO_INDEX
from PBANDI_R_BL_TIPO_DOC_SEZ_MOD a, PBANDI_R_BL_TIPO_DOC_MICRO_SEZ b, PBANDI_D_MICRO_SEZIONE_MODULO c
where b.PROGR_BL_TIPO_DOC_SEZ_MOD = a.PROGR_BL_TIPO_DOC_SEZ_MOD
  and c.ID_MICRO_SEZIONE_MODULO = b.ID_MICRO_SEZIONE_MODULO