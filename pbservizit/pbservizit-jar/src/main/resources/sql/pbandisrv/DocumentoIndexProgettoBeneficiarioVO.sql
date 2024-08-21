/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT DISTINCT di.*,
  spb.progr_bando_linea_intervento,
  spb.codice_visualizzato_progetto AS codice_visualizzato,
  spb.id_soggetto,
  spb.id_tipo_anagrafica,
  spb.id_soggetto_beneficiario,
  tdis.desc_breve_tipo_anagrafica,
  tdis.desc_breve_tipo_doc_index,
  tdis.desc_tipo_doc_index,
  CASE
    WHEN spb.id_persona_fisica IS NULL
    THEN
      ( SELECT DISTINCT eg.denominazione_ente_giuridico
      FROM PBANDI_T_ENTE_GIURIDICO eg
      WHERE eg.id_ente_giuridico = spb.id_ente_giuridico
      )
    ELSE
      ( SELECT DISTINCT pf.cognome
        || ' '
        || pf.nome
      FROM PBANDI_T_PERSONA_FISICA pf
      WHERE pf.id_persona_fisica = spb.id_persona_fisica
      )
  END AS beneficiario,
  CASE
    WHEN spb.id_soggetto_beneficiario IS NOT NULL
    THEN
      ( SELECT DISTINCT s.codice_fiscale_soggetto
      FROM PBANDI_T_SOGGETTO s
      WHERE s.id_soggetto = spb.id_soggetto_beneficiario
      )
  END AS codice_fiscale_beneficiario
FROM
  ( SELECT DISTINCT m.id_progetto,
    m.progr_bando_linea_intervento,
    m.id_soggetto,
    m.id_tipo_anagrafica,
    m.codice_visualizzato_progetto,
    ben.id_soggetto_beneficiario,
    ben.id_ente_giuridico,
    ben.id_persona_fisica
  FROM
    (SELECT rsp.id_soggetto id_soggetto_beneficiario,
      rsp.id_progetto,
      rsp.id_ente_giuridico,
      rsp.id_persona_fisica
    FROM pbandi_r_soggetto_progetto rsp
    WHERE rsp.ID_TIPO_ANAGRAFICA =
      (SELECT dta.id_tipo_anagrafica
      FROM pbandi_d_tipo_anagrafica dta
      WHERE dta.desc_breve_tipo_anagrafica = 'BENEFICIARIO'
      )
    AND NVL(TRUNC(rsp.dt_fine_validita), TRUNC(sysdate+1)) > TRUNC(sysdate)
    AND NVL(rsp.id_tipo_beneficiario, '-1')               <>
      (SELECT dtb.id_tipo_beneficiario
      FROM pbandi_d_tipo_beneficiario dtb
      WHERE dtb.desc_breve_tipo_beneficiario = 'BEN-ASSOCIATO'
      )
    ) ben,
    ( SELECT DISTINCT tp.id_progetto,
      rbli.progr_bando_linea_intervento,
      NULL progr_soggetto_progetto,
      ts.codice_fiscale_soggetto,
      ts.id_soggetto,
      ts.id_tipo_anagrafica,
      tp.codice_visualizzato codice_visualizzato_progetto
    FROM
      (SELECT ts1.*,
        tec.id_ente_competenza,
        rsta.id_tipo_anagrafica
      FROM pbandi_t_soggetto ts1,
        pbandi_t_ente_competenza tec,
        pbandi_r_sogg_tipo_anagrafica rsta
      WHERE rsta.id_soggetto                                   = ts1.id_soggetto
      AND NVL(TRUNC(rsta.dt_fine_validita), TRUNC(sysdate      +1)) > TRUNC(sysdate)
      AND rsta.id_tipo_anagrafica NOT                         IN
        (SELECT dta.id_tipo_anagrafica
        FROM pbandi_d_tipo_anagrafica dta
        WHERE dta.desc_breve_tipo_anagrafica IN ('PERSONA-FISICA', 'OI-ISTRUTTORE', 'ADG-ISTRUTTORE')
        )
      AND (rsta.id_tipo_anagrafica NOT IN
        (SELECT dta.id_tipo_anagrafica
        FROM pbandi_d_tipo_anagrafica dta
        WHERE dta.desc_breve_tipo_anagrafica IN ('BEN-MASTER', 'OI-IST-MASTER', 'ADG-IST-MASTER')
        )
      OR ( NOT EXISTS
        (SELECT 'x'
        FROM pbandi_r_ente_competenza_sogg recs
        WHERE recs.id_soggetto = ts1.id_soggetto
        )
      OR EXISTS
        (SELECT 'x'
        FROM pbandi_r_ente_competenza_sogg recs
        WHERE recs.id_soggetto      = ts1.id_soggetto
        AND recs.id_ente_competenza = tec.id_ente_competenza
        ) ) )
      ) ts,
      pbandi_t_progetto tp,
      pbandi_t_domanda td,
      pbandi_r_bando_linea_intervent rbli,
      pbandi_r_bando_linea_ente_comp rble
    WHERE td.id_domanda                   = tp.id_domanda
    AND rbli.progr_bando_linea_intervento = td.progr_bando_linea_intervento
    AND rble.progr_bando_linea_intervento = rbli.progr_bando_linea_intervento
    AND rble.id_ruolo_ente_competenza     =
      (SELECT dre.id_ruolo_ente_competenza
      FROM pbandi_d_ruolo_ente_competenza dre
      WHERE dre.desc_breve_ruolo_ente = 'DESTINATARIO'
      )
    AND ts.id_ente_competenza = rble.id_ente_competenza
    UNION
    SELECT rsp.id_progetto id_progetto,
      NULL progr_bando_linea_intervento,
      rsp.progr_soggetto_progetto,
      ts.codice_fiscale_soggetto,
      ts.id_soggetto,
      rsp.id_tipo_anagrafica,
      tp.codice_visualizzato codice_visualizzato_progetto
    FROM PBANDI_T_SOGGETTO ts,
      pbandi_r_soggetto_progetto rsp,
      pbandi_t_progetto tp
    WHERE ts.id_soggetto                                   = rsp.id_soggetto
    AND NVL(TRUNC(rsp.dt_fine_validita), TRUNC(sysdate+1)) > TRUNC(sysdate)
    AND tp.id_progetto                                     = rsp.id_progetto
    ) m,
    pbandi_d_tipo_anagrafica dta
  WHERE m.id_tipo_anagrafica           = dta.id_tipo_anagrafica
  AND ben.id_progetto                  = m.id_progetto
  AND (dta.desc_breve_tipo_anagrafica <> 'PERSONA-FISICA'
  OR ben.id_soggetto_beneficiario     IN
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
  ) spb,
  ( SELECT DISTINCT docindex_anagrafica.*,
    docindex_ente_soggetto.id_ente_competenza,
    docindex_ente_soggetto.id_soggetto
  FROM
    (SELECT rdita.id_tipo_documento_index,
      rdita.id_tipo_anagrafica,
      tdi.desc_breve_tipo_doc_index,
      tdi.desc_tipo_doc_index,
      ta.desc_breve_tipo_anagrafica
    FROM PBANDI_R_DOC_INDEX_TIPO_ANAG rdita,
      PBANDI_D_TIPO_ANAGRAFICA ta,
      PBANDI_C_TIPO_DOCUMENTO_INDEX tdi
    WHERE ta.id_tipo_anagrafica     = rdita.id_tipo_anagrafica
    AND tdi.id_tipo_documento_index = rdita.id_tipo_documento_index
    ) docindex_anagrafica,
    (SELECT rtdib.id_tipo_documento_index,
      rtdib.progr_bando_linea_intervento,
      ts.id_ente_competenza,
      ts.id_soggetto
    FROM PBANDI_R_TP_DOC_IND_BAN_LI_INT rtdib,
      PBANDI_R_BANDO_LINEA_ENTE_COMP rblec,
      (SELECT ts1.*,
        tec.id_ente_competenza,
        rsta.id_tipo_anagrafica,
        rsta.dt_inizio_validita,
        rsta.dt_fine_validita,
        rsta.flag_aggiornato_flux
      FROM pbandi_t_soggetto ts1,
        pbandi_t_ente_competenza tec,
        pbandi_r_sogg_tipo_anagrafica rsta
      WHERE rsta.id_soggetto           = ts1.id_soggetto
      AND rsta.id_tipo_anagrafica NOT IN
        (SELECT dta.id_tipo_anagrafica
        FROM pbandi_d_tipo_anagrafica dta
        WHERE dta.desc_breve_tipo_anagrafica IN ('PERSONA-FISICA', 'OI-ISTRUTTORE', 'ADG-ISTRUTTORE')
        )
      AND ( rsta.id_tipo_anagrafica NOT IN
        (SELECT dta.id_tipo_anagrafica
        FROM pbandi_d_tipo_anagrafica dta
        WHERE dta.desc_breve_tipo_anagrafica IN ('BEN-MASTER', 'OI-IST-MASTER', 'ADG-IST-MASTER')
        )
      OR ( NOT EXISTS
        (SELECT 'x'
        FROM pbandi_r_ente_competenza_sogg recs
        WHERE recs.id_soggetto = ts1.id_soggetto
        )
      OR EXISTS
        (SELECT 'x'
        FROM pbandi_r_ente_competenza_sogg recs
        WHERE recs.id_soggetto      = ts1.id_soggetto
        AND recs.id_ente_competenza = tec.id_ente_competenza
        ) ) )
      UNION
      SELECT DISTINCT ts1.*,
        rblec.id_ente_competenza,
        rsta.id_tipo_anagrafica,
        rsta.dt_inizio_validita,
        rsta.dt_fine_validita,
        rsta.flag_aggiornato_flux
      FROM pbandi_t_soggetto ts1,
        pbandi_r_sogg_tipo_anagrafica rsta,
        pbandi_r_soggetto_progetto rsp,
        pbandi_t_progetto tp,
        pbandi_t_domanda td,
        pbandi_r_bando_linea_ente_comp rblec
      WHERE rsta.id_soggetto       = ts1.id_soggetto
      AND rsta.id_tipo_anagrafica IN
        (SELECT dta.id_tipo_anagrafica
        FROM pbandi_d_tipo_anagrafica dta
        WHERE dta.desc_breve_tipo_anagrafica IN ('PERSONA-FISICA', 'OI-ISTRUTTORE', 'ADG-ISTRUTTORE')
        )
      AND rsp.id_soggetto                    = ts1.id_soggetto
      AND tp.id_progetto                     = rsp.id_progetto
      AND td.id_domanda                      = tp.id_domanda
      AND rblec.progr_bando_linea_intervento = td.progr_bando_linea_intervento
      ) ts
    WHERE ts.id_ente_competenza            = rblec.id_ente_competenza
    AND rtdib.progr_bando_linea_intervento = rblec.progr_bando_linea_intervento
    ) docindex_ente_soggetto
  WHERE docindex_anagrafica.id_tipo_documento_index = docindex_ente_soggetto.id_tipo_documento_index
  ) tdis,
  PBANDI_T_DOCUMENTO_INDEX di
WHERE di.id_progetto           = spb.id_progetto
AND di.id_tipo_documento_index = tdis.id_tipo_documento_index
AND tdis.id_tipo_anagrafica    = spb.id_tipo_anagrafica
AND tdis.id_soggetto           = spb.id_soggetto