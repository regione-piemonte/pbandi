/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT NC.ID_PROGETTO,
  ID_DOC_RIFERIMENTO,
  NC.ID_DOCUMENTO_DI_SPESA AS id_Nota_Di_Credito ,
  NC.NUMERO_DOCUMENTO,
  NC.DT_EMISSIONE_DOCUMENTO,
  NC.DESC_STATO_DOCUMENTO_SPESA,
  NC.IMPORTO_TOTALE_DOCUMENTO,
  nvl2 ( VDSP . DESC_VOCE_DI_SPESA , VDSP . DESC_VOCE_DI_SPESA
  || ': ' , '' )
  || VDS . DESC_VOCE_DI_SPESA desc_Voce_Di_Spesa ,
  QPD.IMPORTO_QUOTA_PARTE_DOC_SPESA AS importo_Voce_Di_Spesa
FROM
  (SELECT DSP.ID_PROGETTO,
    D.ID_DOC_RIFERIMENTO,
    D.ID_DOCUMENTO_DI_SPESA,
    D.NUMERO_DOCUMENTO,
    D.DT_EMISSIONE_DOCUMENTO,
    SD.DESC_STATO_DOCUMENTO_SPESA,
    D.IMPORTO_TOTALE_DOCUMENTO
  FROM PBANDI_T_DOCUMENTO_DI_SPESA D,
    PBANDI_D_STATO_DOCUMENTO_SPESA SD,
    PBANDI_R_DOC_SPESA_PROGETTO DSP
  WHERE DSP.ID_STATO_DOCUMENTO_SPESA                       = SD.ID_STATO_DOCUMENTO_SPESA
  AND D.ID_DOCUMENTO_DI_SPESA                            = DSP.ID_DOCUMENTO_DI_SPESA
  and nvl(trunc(sd.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)
  ) NC,
  PBANDI_T_QUOTA_PARTE_DOC_SPESA QPD,
  PBANDI_T_RIGO_CONTO_ECONOMICO RCE,
  PBANDI_D_VOCE_DI_SPESA VDS,
  PBANDI_D_VOCE_DI_SPESA VDSP
WHERE NC.ID_DOCUMENTO_DI_SPESA                          = QPD.ID_DOCUMENTO_DI_SPESA (+)
AND NC.ID_PROGETTO                                      = QPD.ID_PROGETTO (+)
AND QPD.ID_RIGO_CONTO_ECONOMICO                         = RCE.ID_RIGO_CONTO_ECONOMICO (+)
AND RCE.ID_VOCE_DI_SPESA                                = VDS.ID_VOCE_DI_SPESA (+)
AND VDS.ID_VOCE_DI_SPESA_PADRE                          = VDSP.ID_VOCE_DI_SPESA (+)
and nvl(trunc(rce.dt_fine_validita), trunc(sysdate  +1)) > trunc(sysdate)
and nvl(trunc(vds.dt_fine_validita), trunc(sysdate  +1)) > trunc(sysdate)
and nvl(trunc(vdsp.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)
ORDER BY dt_emissione_documento, id_Nota_Di_Credito, desc_voce_di_spesa