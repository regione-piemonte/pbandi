/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT id_progetto, SUM(importo) importo_totale_disimpegni FROM PBANDI_T_REVOCA GROUP BY id_progetto