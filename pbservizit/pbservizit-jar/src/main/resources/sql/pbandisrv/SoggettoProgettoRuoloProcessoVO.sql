/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT m.*,
  ben.id_soggetto_BENEFICIARIO,
  ben.codice_fiscale_BENEFICIARIO,
  ben.DENOMINAZIONE_BENEFICIARIO,
  CASE
    WHEN ben.id_soggetto_beneficiario IN
      (SELECT rsc.id_soggetto_ente_giuridico
      FROM pbandi_r_sogg_prog_sogg_correl rspsc,
        pbandi_r_soggetti_correlati rsc,
        pbandi_d_tipo_sogg_correlato dtsc
      WHERE rsc.progr_soggetti_correlati                      = rspsc.progr_soggetti_correlati
      AND rspsc.progr_soggetto_progetto                       = m.progr_soggetto_progetto
      AND rsc.id_tipo_soggetto_correlato                      = dtsc.id_tipo_soggetto_correlato
      AND dtsc.desc_breve_tipo_sogg_correlato                 = 'Rappr. Leg.'
      AND NVL(TRUNC(rsc.dt_fine_validita), TRUNC(sysdate  +1)) > TRUNC(sysdate)
      AND NVL(TRUNC(dtsc.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
      )
    AND m.desc_breve_tipo_anagrafica = 'PERSONA-FISICA'
    THEN 'S'
    ELSE 'N'
  END flag_rappresentante_legale
FROM
  (SELECT rsp.id_soggetto id_soggetto_beneficiario,
    benInfo.codice_fiscale_soggetto codice_fiscale_beneficiario,
    NVL(teg.denominazione_ente_giuridico, tpf.nome
    || nvl2(tpf.nome, ' ', '')
    || tpf.cognome) AS denominazione_beneficiario,
    rsp.id_progetto
  FROM pbandi_r_soggetto_progetto rsp,
    (SELECT pbandi_t_soggetto.id_soggetto,
      pbandi_t_soggetto.codice_fiscale_soggetto,
      pbandi_d_tipo_soggetto.desc_breve_tipo_soggetto
    FROM pbandi_t_soggetto,
      pbandi_d_tipo_soggetto
    WHERE NVL(TRUNC(pbandi_d_tipo_soggetto.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
    AND pbandi_d_tipo_soggetto.id_tipo_soggetto                                  = pbandi_t_soggetto.id_tipo_soggetto
    )benInfo,
    pbandi_t_ente_giuridico teg,
    pbandi_t_persona_fisica tpf
  WHERE rsp.id_soggetto      = benInfo.id_soggetto
  AND rsp.ID_TIPO_ANAGRAFICA =
    (SELECT m.id_tipo_anagrafica
    FROM pbandi_d_tipo_anagrafica m
    WHERE m.desc_breve_tipo_anagrafica = 'BENEFICIARIO'
    )
  AND NVL(TRUNC(rsp.dt_fine_validita), TRUNC(sysdate+1)) > TRUNC(sysdate)
  AND NVL(rsp.id_tipo_beneficiario, '-1')               <>
    (SELECT dtb.id_tipo_beneficiario
    FROM pbandi_d_tipo_beneficiario dtb
    WHERE dtb.desc_breve_tipo_beneficiario = 'BEN-ASSOCIATO'
    )
  AND NVL(TRUNC(teg.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
  AND NVL(TRUNC(tpf.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
  AND rsp.ID_PERSONA_FISICA                               = tpf.ID_PERSONA_FISICA (+)
  AND rsp.id_ente_giuridico                               = teg.id_ente_giuridico (+)
  ) ben,
  ( SELECT DISTINCT m1.*,
    crp.codice_ruolo,
    cdp.uuid_processo definizione_processo,
    NULL AS dt_aggiornamento,
    NULL AS dt_inserimento
  FROM pbandi_v_soggetto_progetto m1,
    (SELECT crp.codice codice_ruolo,
      rrta.id_tipo_anagrafica,
      crp.id_definizione_processo
    FROM pbandi_c_ruolo_di_processo crp,
      pbandi_r_ruolo_tipo_anagrafica rrta
    WHERE crp.id_ruolo_di_processo = rrta.id_ruolo_di_processo
    ) crp,
    pbandi_c_definizione_processo cdp
  WHERE cdp.id_definizione_processo = crp.id_definizione_processo
  AND m1.id_tipo_anagrafica         = crp.id_tipo_anagrafica
  ) m
WHERE ben.id_progetto              = m.id_progetto
AND (m.desc_breve_tipo_anagrafica <> 'PERSONA-FISICA'
OR ben.id_soggetto_beneficiario   IN
  (SELECT rsc.id_soggetto_ente_giuridico
  FROM pbandi_r_sogg_prog_sogg_correl rspsc,
    pbandi_r_soggetti_correlati rsc
  WHERE rsc.progr_soggetti_correlati                     = rspsc.progr_soggetti_correlati
  AND NVL(TRUNC(rsc.dt_fine_validita), TRUNC(sysdate+1)) > TRUNC(sysdate)
  AND rspsc.progr_soggetto_progetto                      = m.progr_soggetto_progetto
  AND rsc.id_tipo_soggetto_correlato                    IN
    (SELECT rrta.id_tipo_soggetto_correlato
    FROM pbandi_r_ruolo_tipo_anagrafica rrta
    WHERE rrta.id_tipo_anagrafica = m.id_tipo_anagrafica
    )
  ) )
