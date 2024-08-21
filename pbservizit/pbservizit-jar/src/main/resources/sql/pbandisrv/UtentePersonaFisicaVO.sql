/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT u.id_utente,
  p.cognome
  || ' '
  || p.nome
  || ' '
  || u.codice_utente cognome_nome_codice,
  p.dt_inizio_validita,
  p.dt_fine_validita
FROM pbandi_t_utente u,
  pbandi_t_persona_fisica p,
  (SELECT DISTINCT MAX(id_persona_fisica) over (partition BY id_soggetto) AS id_persona_fisica
  FROM pbandi_t_persona_fisica
  ) last_p_by_soggetto
WHERE u.id_soggetto     = p.id_soggetto
AND p.id_persona_fisica = last_p_by_soggetto.id_persona_fisica