/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT R.*,
  D.COD_IGRUE,
  D.DESC_INDICATORE,
  D.desc_indicatore || nvl2(D.id_linea_di_intervento,' ('||lin.cod_igrue_t13_t14||')','') as desc_indicatore_linea
FROM PBANDI_R_BANDO_INDICATORI R,
  PBANDI_D_INDICATORI D,
  pbandi_d_linea_di_intervento lin
WHERE R.ID_INDICATORI = D.ID_INDICATORI
and D.id_linea_di_intervento = lin.id_linea_di_intervento(+)

and (trunc(sysdate) >= nvl(trunc(D.DT_INIZIO_VALIDITA), trunc(sysdate)-1)
and trunc(sysdate) < nvl(trunc(D.DT_FINE_VALIDITA), trunc(sysdate)+1))

and (trunc(sysdate) >= nvl(trunc(lin.DT_INIZIO_VALIDITA), trunc(sysdate)-1)
and trunc(sysdate) < nvl(trunc(lin.DT_FINE_VALIDITA), trunc(sysdate)+1))