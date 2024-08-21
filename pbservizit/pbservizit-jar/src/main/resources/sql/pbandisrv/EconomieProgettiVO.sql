/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT te.*,
    cedente.*,
    CASE
      WHEN te.id_progetto_ricevente IS NOT NULL
      THEN
        (SELECT tp.CODICE_VISUALIZZATO AS codice_visualizzato_ricevente
        FROM pbandi_t_progetto tp
        WHERE tp.ID_PROGETTO = te.ID_PROGETTO_RICEVENTE
        )
      ELSE NULL
    END AS codice_visualizzato_ricevente
  FROM pbandi_t_economie te
    INNER JOIN (SELECT ID_PROGETTO, CODICE_VISUALIZZATO AS codice_visualizzato_cedente 
    FROM pbandi_t_progetto) cedente
    ON cedente.ID_PROGETTO = te.ID_PROGETTO_CEDENTE