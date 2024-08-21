/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select * from pbandi_d_attivita_ateco
where length(cod_ateco) = 8
AND NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(SYSDATE     +1)) > TRUNC(SYSDATE)
