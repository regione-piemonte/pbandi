SELECT atto.anno_atto
  ||'/'
  ||atto.numero_atto
  ||'/'
  ||
  (SELECT SUBSTR(EC.DESC_BREVE_ENTE, 1, 4)
  FROM PBANDI_T_ENTE_COMPETENZA EC,
    PBANDI_D_SETTORE_ENTE SE
  WHERE EC.ID_ENTE_COMPETENZA = SE.ID_ENTE_COMPETENZA
  AND SE.ID_SETTORE_ENTE      = atto.id_settore_ente
  )                        AS estremi_atto,
  prog.codice_visualizzato AS codice_visualizzato_progetto,
  CASE
    WHEN benb.id_persona_fisica IS NULL
    THEN
      ( SELECT DISTINCT eg.denominazione_ente_giuridico
      FROM PBANDI_T_ENTE_GIURIDICO eg
      WHERE eg.id_ente_giuridico = benb.id_ente_giuridico
      )
    ELSE
      ( SELECT DISTINCT pf.cognome
        || ' '
        || pf.nome
      FROM PBANDI_T_PERSONA_FISICA pf
      WHERE pf.id_persona_fisica = benb.id_persona_fisica
      )
  END
  || ' - '
  ||
  (SELECT CODICE_FISCALE_SOGGETTO
  FROM PBANDI_T_SOGGETTO sog
  WHERE sog.ID_SOGGETTO = benb.ID_SOGGETTO
  ) denominazione_beneficiario_bil,
  benb.id_soggetto,
  imp.numero_impegno,
  imp.anno_esercizio AS anno_esercizio_impegno,
  imp.anno_impegno,
  imp.anno_esercizio
  ||'/'
  ||imp.numero_impegno AS impegno,
  stato.desc_stato_atto,
  stato.desc_breve_stato_atto,
  atto.*,
  dce.desc_causale,
  tipo_sogg.desc_tipo_sogg_ritenuta
FROM pbandi_t_atto_liquidazione atto,
  pbandi_t_progetto prog,
  pbandi_t_beneficiario_bilancio benb,
  pbandi_r_liquidazione liq,
  pbandi_t_impegno imp,
  pbandi_d_stato_atto stato,
  pbandi_d_causale_erogazione dce,
  pbandi_d_aliquota_ritenuta aliq,
  pbandi_d_tipo_sogg_ritenuta tipo_sogg 
WHERE atto.id_progetto                = prog.id_progetto(+)
AND benb.id_beneficiario_bilancio     = atto.id_beneficiario_bilancio
AND imp.id_impegno                    = liq.id_impegno
AND atto.id_atto_liquidazione         = liq.id_atto_liquidazione
AND atto.id_stato_atto                = stato.id_stato_atto
AND dce.id_causale_erogazione         = atto.id_causale_erogazione
AND atto.id_aliquota_ritenuta         = aliq.id_aliquota_ritenuta(+)
AND tipo_sogg.id_tipo_sogg_ritenuta(+)= aliq.id_tipo_sogg_ritenuta
AND (TRUNC(sysdate)                  >= NVL(TRUNC(aliq.DT_INIZIO_VALIDITA), TRUNC(sysdate)      -1)
AND TRUNC(sysdate)                    < NVL(TRUNC(aliq.DT_FINE_VALIDITA), TRUNC(sysdate)        +1))
AND (TRUNC(sysdate)                  >= NVL(TRUNC(tipo_sogg.DT_INIZIO_VALIDITA), TRUNC(sysdate) -1)
AND TRUNC(sysdate)                    < NVL(TRUNC(tipo_sogg.DT_FINE_VALIDITA), TRUNC(sysdate)   +1))
AND (TRUNC(sysdate)                  >= NVL(TRUNC(dce.DT_INIZIO_VALIDITA), TRUNC(sysdate)       -1)
AND TRUNC(sysdate)                    < NVL(TRUNC(dce.DT_FINE_VALIDITA), TRUNC(sysdate)         +1))
AND (TRUNC(sysdate)                  >= NVL(TRUNC(stato.DT_INIZIO_VALIDITA), TRUNC(sysdate)     -1)
AND TRUNC(sysdate)                    < NVL(TRUNC(stato.DT_FINE_VALIDITA), TRUNC(sysdate)       +1))