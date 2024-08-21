/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT SUM (ec.importo_ceduto) AS Importo_ceduto,
  ec.id_progetto_cedente
FROM pbandi_t_economie ec
GROUP BY ec.id_progetto_cedente