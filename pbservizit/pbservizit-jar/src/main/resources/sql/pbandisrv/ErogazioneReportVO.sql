/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select
distinct ID_EROGAZIONE ,
DESC_MODALITA_AGEVOLAZIONE,
IMPORTO_EROGAZIONE ,
e.ID_MODALITA_AGEVOLAZIONE ,
ID_PROGETTO    
from PBANDI_T_EROGAZIONE e,PBANDI_D_MODALITA_AGEVOLAZIONE ma
where e.ID_MODALITA_AGEVOLAZIONE=ma.ID_MODALITA_AGEVOLAZIONE