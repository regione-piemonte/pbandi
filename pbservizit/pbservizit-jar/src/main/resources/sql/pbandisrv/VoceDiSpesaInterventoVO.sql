/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select r.ID_BANDO, v.COD_TIPO_VOCE_DI_SPESA, v.DESC_VOCE_DI_SPESA, v.DT_FINE_VALIDITA, v.DT_INIZIO_VALIDITA,
       v.ID_VOCE_DI_SPESA, v.ID_VOCE_DI_SPESA_MONIT, v.ID_VOCE_DI_SPESA_PADRE
from PBANDI_R_BANDI_TIPOL_INTERV r, PBANDI_D_TIPOL_INTERVENTI t, PBANDI_D_DETT_TIPOL_INTERVENTI d, PBANDI_D_VOCE_DI_SPESA v
where t.ID_TIPOL_INTERVENTO = r.ID_TIPOL_INTERVENTO
  and d.ID_TIPOL_INTERVENTO(+) = r.ID_TIPOL_INTERVENTO
  and (v.ID_VOCE_DI_SPESA = t.ID_MACRO_VOCE or v.ID_VOCE_DI_SPESA = d.ID_MACRO_VOCE)
  