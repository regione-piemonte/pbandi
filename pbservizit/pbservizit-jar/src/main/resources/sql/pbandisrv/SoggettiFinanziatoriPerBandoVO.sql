/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT prbli.PROGR_BANDO_LINEA_INTERVENTO, prbsf.ID_SOGGETTO_FINANZIATORE, prbsf.PERCENTUALE_QUOTA_SOGG_FINANZ, 
prbsf.PERC_QUOTA_CONTRIBUTO_PUB,pdsf.DESC_BREVE_SOGG_FINANZIATORE, pdsf.DESC_SOGG_FINANZIATORE, pdsf.FLAG_CERTIFICAZIONE, 
pdsf.ID_TIPO_SOGG_FINANZIAT, pdsf.FLAG_AGEVOLATO 
FROM PBANDI_R_BANDO_SOGG_FINANZIAT prbsf 
JOIN PBANDI_D_SOGGETTO_FINANZIATORE pdsf ON pdsf.ID_SOGGETTO_FINANZIATORE =prbsf.ID_SOGGETTO_FINANZIATORE 
JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.ID_BANDO = prbsf.ID_BANDO