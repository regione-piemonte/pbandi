/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select revoca.id_progetto,
      nvl(revoca.totale_importo_revocato,0) as totale_importo_revocato
from 
(
  select id_progetto,    
         sum(nvl(importo,0)) as totale_importo_revocato
  from pbandi_t_revoca 
  group by (id_progetto)
)  revoca