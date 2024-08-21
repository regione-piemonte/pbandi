/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT pc.*, lpc.ID_LINEA_DI_INTERVENTO
FROM PBANDI_D_PROGETTO_COMPLESSO pc
INNER JOIN PBANDI_R_LINEA_PROG_COMPLESSO lpc
ON lpc.ID_PROGETTO_COMPLESSO    = pc.ID_PROGETTO_COMPLESSO