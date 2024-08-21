/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT DISTINCT
	r.ID_NORMATIVA,
	r.NORMATIVA,
	li.*
FROM
	PBANDI_V_RILEVAZIONI r,
	PBANDI_D_LINEA_DI_INTERVENTO li
WHERE 
	r.ID_NORMATIVA = li.ID_LINEA_DI_INTERVENTO
	