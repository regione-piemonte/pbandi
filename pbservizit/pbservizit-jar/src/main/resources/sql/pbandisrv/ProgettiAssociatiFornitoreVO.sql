/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT rbli.progr_bando_linea_intervento,
  rbli.nome_bando_linea,
  prog.id_progetto,
  prog.codice_visualizzato,
  prog.titolo_progetto,
  forn.id_fornitore
FROM pbandi_t_fornitore forn,
  pbandi_r_soggetto_progetto rsp,
  pbandi_t_progetto prog,
  pbandi_t_domanda dom,
  pbandi_r_bando_linea_intervent rbli
WHERE forn.id_soggetto_fornitore     = rsp.id_soggetto
AND rsp.id_tipo_anagrafica           = 1
AND rsp.id_tipo_beneficiario        <> 4
AND prog.id_progetto                 = rsp.id_progetto
AND prog.id_domanda                  = dom.id_domanda
AND dom.progr_bando_linea_intervento = rbli.progr_bando_linea_intervento