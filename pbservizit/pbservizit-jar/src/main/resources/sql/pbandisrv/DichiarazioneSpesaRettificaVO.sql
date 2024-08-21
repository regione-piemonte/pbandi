/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select distinct ds.*
from PBANDI_T_DICHIARAZIONE_SPESA ds
where ds.dt_chiusura_validazione is not null