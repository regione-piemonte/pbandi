/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT DISTINCT NVL2(P.DESC_VOCE_DI_SPESA_PADRE, P.DESC_VOCE_DI_SPESA_PADRE
  ||' - '
  ||V.DESC_VOCE_DI_SPESA, V.DESC_VOCE_DI_SPESA) DESC_VOCE_DI_SPESA_COMPOSTA,
  V.cod_tipo_voce_di_spesa AS cod_tipo_voce_di_spesa,
  NVL2(P.DESC_VOCE_DI_SPESA_PADRE, '2', '1') AS livello,
  NVL2(V.ID_TIPOLOGIA_VOCE_DI_SPESA, (select t.DESCRIZIONE from PBANDI_D_TIPOL_VOCE_DI_SPESA t where t.ID_TIPOLOGIA_VOCE_DI_SPESA=v.ID_TIPOLOGIA_VOCE_DI_SPESA), ' ') as desc_tipologia_voce_di_spesa,
  NVL2(V.FLAG_EDIT, V.FLAG_EDIT, ' ') as flag_edit,
  NVL2(V.FLAG_SPESE_GESTIONE , V.FLAG_SPESE_GESTIONE, ' ') as FLAG_SPESE_GESTIONE,
  R.*
FROM PBANDI_R_BANDO_VOCE_SPESA R,
  PBANDI_D_VOCE_DI_SPESA V,
  (SELECT DESC_VOCE_DI_SPESA AS DESC_VOCE_DI_SPESA_PADRE,
    ID_VOCE_DI_SPESA
  FROM PBANDI_D_VOCE_DI_SPESA
  WHERE NVL(TRUNC(PBANDI_D_VOCE_DI_SPESA.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
  ) P
WHERE V.ID_VOCE_DI_SPESA                             = R.ID_VOCE_DI_SPESA
AND P.ID_VOCE_DI_SPESA(+)                            = V.ID_VOCE_DI_SPESA_PADRE
AND NVL(TRUNC(V.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)