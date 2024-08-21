/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT app.*,
  agg.cod_proc_agg,
  nvl(agg.cig_proc_agg, agg.cod_proc_agg) as cod_visualizzato_proc_agg,
  agg.id_progetto,
  agg.desc_proc_agg,
  tipoProc.DESC_TIPOLOGIA_AGGIUDICAZIONE ,
  tipoApp.DESC_TIPOLOGIA_APPALTO,
  agg.IMPORTO,
  agg.IVA,
  rPrAp.ID_TIPO_DOCUMENTO_INDEX,
  rib.importo as importo_ribasso_asta,
  rib.percentuale as percentuale_ribasso_asta
FROM pbandi_t_appalto app,
  pbandi_t_procedura_aggiudicaz agg,
  PBANDI_D_TIPOLOGIA_AGGIUDICAZ tipoProc,
  PBANDI_D_TIPOLOGIA_APPALTO tipoApp,
  PBANDI_R_PROGETTI_APPALTI rPrAp,
  pbandi_t_ribasso_asta rib
WHERE app.id_procedura_aggiudicaz = agg.id_procedura_aggiudicaz
AND (TRUNC(sysdate)              >= NVL(TRUNC(agg.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1)
AND TRUNC(sysdate)                < NVL(TRUNC(agg.DT_FINE_VALIDITA), TRUNC(sysdate)  +1))
AND tipoProc.ID_TIPOLOGIA_AGGIUDICAZ=agg.ID_TIPOLOGIA_AGGIUDICAZ
AND app.ID_TIPOLOGIA_APPALTO =tipoApp.ID_TIPOLOGIA_APPALTO
and rPrAp.id_appalto=app.id_appalto
and rib.id_procedura_aggiudicaz(+) = app.id_procedura_aggiudicaz
ORDER BY app.id_appalto
