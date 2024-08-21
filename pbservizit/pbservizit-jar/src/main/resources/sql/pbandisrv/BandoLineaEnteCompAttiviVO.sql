/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select *
from PBANDI_V_BANDO_LINEA_ENTE_COMP 
where TRUNC(sysdate) < NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(sysdate) +1)