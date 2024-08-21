/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT
    rsp.id_progetto id_progetto,
    tp.codice_visualizzato AS codice_visualizzato_progetto,
    tp.titolo_progetto AS titolo_progetto,
    tp.id_istanza_processo,
    rsp.id_tipo_anagrafica,
    rsp.id_tipo_beneficiario,
    rsp.id_persona_fisica,
    rsp.id_ente_giuridico,
    ts.id_soggetto,
    ts.codice_fiscale_soggetto,
    ts.id_tipo_soggetto,
    ts.id_utente_agg,
    ts.id_utente_ins,
    rsp.dt_inizio_validita,
    rsp.dt_fine_validita,
    bli.nome_bando_linea,
    bli.progr_bando_linea_intervento,
    CASE
      WHEN rsp.id_persona_fisica IS NULL THEN (
      SELECT
        DISTINCT eg.denominazione_ente_giuridico
      FROM
        PBANDI_T_ENTE_GIURIDICO eg
      WHERE
        eg.id_ente_giuridico = rsp.id_ente_giuridico )
      ELSE (
      SELECT
        DISTINCT pf.cognome || ' ' || pf.nome
      FROM
        PBANDI_T_PERSONA_FISICA pf
      WHERE
        pf.id_persona_fisica = rsp.id_persona_fisica )
    END denominazione_beneficiario
  FROM
    PBANDI_T_SOGGETTO ts,
    pbandi_r_soggetto_progetto rsp,
    pbandi_t_progetto tp,
    PBANDI_T_DOMANDA d,
    pbandi_r_bando_linea_intervent bli
  WHERE
    ts.id_soggetto = rsp.id_soggetto
    AND tp.ID_DOMANDA = d.ID_DOMANDA
    AND d.progr_bando_linea_intervento = bli.progr_bando_linea_intervento
    AND rsp.id_tipo_anagrafica = (
      SELECT dta.id_tipo_anagrafica
    FROM
      pbandi_d_tipo_anagrafica dta
    WHERE
      dta.desc_breve_tipo_anagrafica = 'BENEFICIARIO')
    AND NVL(rsp.id_tipo_beneficiario, '-1') <> (
      SELECT dtb.id_tipo_beneficiario
    FROM
      pbandi_d_tipo_beneficiario dtb
    WHERE
      dtb.desc_breve_tipo_beneficiario = 'BEN-ASSOCIATO')
    AND rsp.id_progetto = tp.id_progetto 
