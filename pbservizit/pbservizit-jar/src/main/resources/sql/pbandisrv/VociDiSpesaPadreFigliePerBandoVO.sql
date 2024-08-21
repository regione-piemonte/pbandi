/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT vds.id_voce_di_spesa,
    NVL(vds.id_voce_di_spesa_padre, vds.id_voce_di_spesa) id_voce_di_spesa_padre,
    vds.desc_voce_di_spesa,
    bvs.id_bando
  FROM pbandi_d_voce_di_spesa vds,
    pbandi_r_bando_voce_spesa bvs
  WHERE vds.id_voce_di_spesa = bvs.id_voce_di_spesa
  AND (TRUNC(sysdate)       >= NVL(TRUNC(vds.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1)
  AND TRUNC(sysdate)         < NVL(TRUNC(vds.DT_FINE_VALIDITA), TRUNC(sysdate)  +1))
