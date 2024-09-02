/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT DICH.ID_DICHIARAZIONE_SPESA AS ID_DICHIARAZIONE_SPESA,
      /* DESC_BREVE_STATO_VALIDAZ_SPESA as DESC_BREVE_STATO_VALIDAZ_SPESA,*/
       dich.ID_PROGETTO as ID_PROGETTO,
       pag.id_pagamento id_pagamento,
       pag.*
from PBANDI_T_DICHIARAZIONE_SPESA dich
    ,PBANDI_R_PAGAMENTO_DICH_SPESA dichpag
    ,PBANDI_T_PAGAMENTO PAG
    /*,PBANDI_D_STATO_VALIDAZ_SPESA statoPag*/
where 
dich.ID_DICHIARAZIONE_SPESA=dichpag.ID_DICHIARAZIONE_SPESA
AND DICHPAG.ID_PAGAMENTO=PAG.ID_PAGAMENTO
/* and pag.ID_STATO_VALIDAZIONE_SPESA=statoPag.ID_STATO_VALIDAZIONE_SPESA*/