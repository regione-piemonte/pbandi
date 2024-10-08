/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT 'Modalit� '
  || D.ID_MODALITA_AGEVOLAZIONE AS MODALITA,
  D.DESC_MODALITA_AGEVOLAZIONE
  || NVL2(R.PERCENTUALE_IMPORTO_AGEVOLATO,' - '
  ||R.PERCENTUALE_IMPORTO_AGEVOLATO
  ||'%','')
  || NVL2(R.MINIMO_IMPORTO_AGEVOLATO,' - '
  ||R.MINIMO_IMPORTO_AGEVOLATO
  ||' &'
  ||'#8364;','') 
  || NVL2(R.MASSIMO_IMPORTO_AGEVOLATO,' - '
  ||R.MASSIMO_IMPORTO_AGEVOLATO
  ||' &'
  ||'#8364;','')   
  || NVL2(R.PERIODO_STABILITA,' - Periodo Stabilita: '||R.PERIODO_STABILITA,'')
  || NVL2(R.FLAG_LIQUIDAZIONE,' - Modalita di liquidazione: '||R.FLAG_LIQUIDAZIONE,' - Modalita di liquidazione: N')  
  AS MODALITA_AGEVOLAZIONE_COMPOSTA,
  R.*
FROM PBANDI_D_MODALITA_AGEVOLAZIONE D,
  PBANDI_R_BANDO_MODALITA_AGEVOL R
WHERE D.ID_MODALITA_AGEVOLAZIONE                     = R.ID_MODALITA_AGEVOLAZIONE
AND NVL(TRUNC(D.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)