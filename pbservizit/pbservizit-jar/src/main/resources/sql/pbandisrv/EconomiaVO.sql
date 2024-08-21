/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT e.*,
    cedente.CODICE_PROGETTO AS codice_progetto_cedente,
    cedente.CODICE_VISUALIZZATO AS codice_visualizzato_cedente,
    ricevente.CODICE_PROGETTO AS codice_progetto_ricevente,
    ricevente.CODICE_VISUALIZZATO AS codice_visualizzato_ricevente
  FROM pbandi_t_economie e, pbandi_t_progetto ricevente, pbandi_t_progetto cedente
  WHERE cedente.ID_PROGETTO = e.ID_PROGETTO_CEDENTE
    AND ricevente.ID_PROGETTO(+) = e.ID_PROGETTO_RICEVENTE