/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT d.*, rl.ID_LINEA_DI_INTERVENTO
FROM PBANDI_D_DIMENSIONE_TERRITOR d
INNER JOIN PBANDI_R_LINEA_DIMENSIONE_TER rl
ON rl.ID_DIMENSIONE_TERRITOR    = d.ID_DIMENSIONE_TERRITOR