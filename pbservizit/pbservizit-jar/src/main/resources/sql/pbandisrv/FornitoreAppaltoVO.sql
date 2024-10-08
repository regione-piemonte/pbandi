/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT f.DENOMINAZIONE_FORNITORE as DENOMINAZIONE_FORNITORE,
  f.CODICE_FISCALE_FORNITORE as CODICE_FISCALE_FORNITORE,
  f.NOME_FORNITORE as NOME_FORNITORE,
  f.COGNOME_FORNITORE as COGNOME_FORNITORE,
  f.ID_TIPO_SOGGETTO as ID_TIPO_SOGGETTO,
  tp.DESC_TIPO_PERCETTORE as DESC_TIPO_PERCETTORE,
  ts.DESC_TIPO_SOGGETTO as DESC_TIPO_SOGGETTO,
  fa.ID_APPALTO as ID_APPALTO,
  fa.ID_FORNITORE as ID_FORNITORE,
  fa.ID_TIPO_PERCETTORE as ID_TIPO_PERCETTORE
FROM pbandi_t_fornitore f
JOIN PBANDI_R_FORNITORE_APPALTO fa
ON f.ID_FORNITORE = fa.ID_FORNITORE
JOIN Pbandi_D_tipo_percettore tp
ON fa.ID_TIPO_PERCETTORE = tp.ID_TIPO_PERCETTORE
JOIN pbandi_d_tipo_soggetto ts
ON f.ID_TIPO_SOGGETTO = ts.ID_TIPO_SOGGETTO