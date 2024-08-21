/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT 
  SUM( NVL(RPQ.IMPORTO_QUIETANZATO,0))     AS QUIETANZATO,
  SUM( NVL(RPQ.IMPORTO_VALIDATO,0) )       AS IMPORTO_VALIDATO,
  ( QUOTAPARTE.IMPORTO_QUOTA_PARTE_DOC_SPESA  - (  SUM( NVL(RPQ.IMPORTO_QUIETANZATO,0))) ) AS RESIDUO_QUIETANZABILE,
  QUOTAPARTE.IMPORTO_QUOTA_PARTE_DOC_SPESA AS RENDICONTATO,
  QUOTAPARTE.ID_QUOTA_PARTE_DOC_SPESA      AS ID_QUOTA_PARTE_DOC_SPESA,
  QUOTAPARTE.ID_RIGO_CONTO_ECONOMICO       AS ID_RIGO_CONTO_ECONOMICO,
  QUOTAPARTE.ID_DOCUMENTO_DI_SPESA         AS ID_DOCUMENTO_DI_SPESA,
  QUOTAPARTE.ID_PROGETTO                   AS ID_PROGETTO,
  RPAGDOC.ID_PAGAMENTO                     AS ID_PAGAMENTO,  
  DECODE(V1.ID_VOCE_DI_SPESA_PADRE,NULL,'',V2.DESC_VOCE_DI_SPESA  ||' : ')
  || V1.DESC_VOCE_DI_SPESA                 AS DESC_VOCE_DI_SPESA,
  V1.ID_VOCE_DI_SPESA
FROM PBANDI_T_QUOTA_PARTE_DOC_SPESA QUOTAPARTE,
  PBANDI_T_RIGO_CONTO_ECONOMICO rigo,
  PBANDI_D_VOCE_DI_SPESA V1,
  PBANDI_D_VOCE_DI_SPESA V2,
  PBANDI_R_PAG_QUOT_PARTE_DOC_SP RPQ,
  PBANDI_R_PAGAMENTO_DOC_SPESA RPAGDOC,
  PBANDI_R_DOC_SPESA_PROGETTO docprj
WHERE QUOTAPARTE.ID_RIGO_CONTO_ECONOMICO                     = rigo.ID_RIGO_CONTO_ECONOMICO
AND rigo.ID_VOCE_DI_SPESA           = V1.ID_VOCE_DI_SPESA
AND RPAGDOC.ID_DOCUMENTO_DI_SPESA        = QUOTAPARTE.ID_DOCUMENTO_DI_SPESA
AND RPAGDOC.ID_DOCUMENTO_DI_SPESA = QUOTAPARTE.ID_DOCUMENTO_DI_SPESA
AND DOCPRJ.ID_PROGETTO = QUOTAPARTE.ID_PROGETTO
and DOCPRJ.ID_DOCUMENTO_DI_SPESA = RPAGDOC.ID_DOCUMENTO_DI_SPESA
AND V1.ID_VOCE_DI_SPESA_PADRE            = V2.ID_VOCE_DI_SPESA(+)
AND NVL(TRUNC(rigo.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
AND QUOTAPARTE.ID_QUOTA_PARTE_DOC_SPESA = RPQ.ID_QUOTA_PARTE_DOC_SPESA(+) 
GROUP BY 
  QUOTAPARTE.IMPORTO_QUOTA_PARTE_DOC_SPESA,
  QUOTAPARTE.ID_QUOTA_PARTE_DOC_SPESA   ,
  QUOTAPARTE.ID_RIGO_CONTO_ECONOMICO   ,     
  QUOTAPARTE.ID_DOCUMENTO_DI_SPESA    ,    
  QUOTAPARTE.ID_PROGETTO        ,         
  RPAGDOC.ID_PAGAMENTO,
  V1.ID_VOCE_DI_SPESA          ,     
  DECODE(V1.ID_VOCE_DI_SPESA_PADRE,NULL,'',V2.DESC_VOCE_DI_SPESA  ||' : ') || V1.DESC_VOCE_DI_SPESA      