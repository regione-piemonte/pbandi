/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select trce.*, dvde.DESCRIZIONE, dvde.FLAG_EDIT
from pbandi_t_rigo_conto_economico trce, 
     pbandi_d_voce_di_entrata dvde
where trce.ID_VOCE_DI_ENTRATA is not null and trce.DT_FINE_VALIDITA is null
  and dvde.ID_VOCE_DI_ENTRATA = trce.ID_VOCE_DI_ENTRATA
order by trce.ID_CONTO_ECONOMICO, dvde.DESCRIZIONE, dvde.ID_VOCE_DI_ENTRATA, trce.COMPLETAMENTO