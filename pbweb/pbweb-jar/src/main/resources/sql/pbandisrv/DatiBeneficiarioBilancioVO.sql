SELECT atto.id_atto_liquidazione,
  bb.codice_beneficiario_bilancio,
  soggetto.codice_fiscale_soggetto,
  sede.partita_iva,
  bb.dt_inserimento,
  bb.dt_aggiornamento,
  CASE
    WHEN bb.id_persona_fisica IS NULL
    THEN
      ( SELECT DISTINCT eg.denominazione_ente_giuridico
      FROM PBANDI_T_ENTE_GIURIDICO eg
      WHERE eg.id_ente_giuridico = bb.id_ente_giuridico
      )
    ELSE
      ( SELECT DISTINCT pf.cognome
        || ' '
        || pf.nome
      FROM PBANDI_T_PERSONA_FISICA pf
      WHERE pf.id_persona_fisica = bb.id_persona_fisica
      )
  END denominazione,
  sedeDatiPagamento.denominazione AS denominazione_ragione_sociale,
  indirizzo.desc_indirizzo,
  comune.desc_comune,
  provincia.desc_provincia
  || nvl2(provincia.sigla_provincia , '('
  || provincia.sigla_provincia
  || ')','') AS provincia,
  recapiti.fax,
  recapiti.email,
  recapiti.telefono,
  indirizzo_secondaria.desc_indirizzo AS desc_indirizzo_secondaria,
  comune_secondaria.desc_comune       AS desc_comune_secondaria,
  provincia_secondaria.desc_provincia
  || nvl2(provincia_secondaria.sigla_provincia , '('
  || provincia_secondaria.sigla_provincia
  || ')','') AS provincia_secondaria
FROM pbandi_t_atto_liquidazione atto,
  pbandi_t_beneficiario_bilancio bb,
  pbandi_t_soggetto soggetto,
  pbandi_t_sede sede,
  pbandi_t_indirizzo indirizzo,
  pbandi_d_comune comune,
  pbandi_d_provincia provincia,
  pbandi_t_recapiti recapiti,
  pbandi_t_dati_pagamento_atto dati,
  pbandi_t_indirizzo indirizzo_secondaria,
  pbandi_d_comune comune_secondaria,
  pbandi_d_provincia provincia_secondaria,
  pbandi_t_sede sedeDatiPagamento 
WHERE atto.id_beneficiario_bilancio      = bb.id_beneficiario_bilancio
and sededatipagamento.id_sede(+) 		 = dati.id_sede
AND soggetto.id_soggetto(+)              = bb.id_soggetto
AND sede.id_sede(+)                      = bb.id_sede
AND (TRUNC(sysdate)                     >= NVL(TRUNC(sede.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1)
AND TRUNC(sysdate)                       < NVL(TRUNC(sede.DT_FINE_VALIDITA), TRUNC(sysdate)  +1))
AND indirizzo.id_indirizzo(+)            = bb.id_indirizzo
AND (TRUNC(sysdate)                     >= NVL(TRUNC(indirizzo.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1)
AND TRUNC(sysdate)                       < NVL(TRUNC(indirizzo.DT_FINE_VALIDITA), TRUNC(sysdate)  +1))
AND comune.id_comune(+)                  = indirizzo.id_comune
AND (TRUNC(sysdate)                     >= NVL(TRUNC(comune.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1)
AND TRUNC(sysdate)                       < NVL(TRUNC(comune.DT_FINE_VALIDITA), TRUNC(sysdate)  +1))
AND provincia.id_provincia(+)            = indirizzo.id_provincia
AND (TRUNC(sysdate)                     >= NVL(TRUNC(provincia.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1)
AND TRUNC(sysdate)                       < NVL(TRUNC(provincia.DT_FINE_VALIDITA), TRUNC(sysdate)  +1))
AND recapiti.id_recapiti(+)              = bb.id_recapiti
AND (TRUNC(sysdate)                     >= NVL(TRUNC(recapiti.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1)
AND TRUNC(sysdate)                       < NVL(TRUNC(recapiti.DT_FINE_VALIDITA), TRUNC(sysdate)  +1))
AND dati.id_dati_pagamento_atto(+)       = atto.id_dati_pagamento_atto
AND (TRUNC(sysdate)                     >= NVL(TRUNC(recapiti.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1)
AND TRUNC(sysdate)                       < NVL(TRUNC(recapiti.DT_FINE_VALIDITA), TRUNC(sysdate)  +1))
AND indirizzo_secondaria.id_indirizzo(+) = dati.id_indirizzo
AND (TRUNC(sysdate)                     >= NVL(TRUNC(indirizzo_secondaria.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1)
AND TRUNC(sysdate)                       < NVL(TRUNC(indirizzo_secondaria.DT_FINE_VALIDITA), TRUNC(sysdate)  +1))
AND comune_secondaria.id_comune(+)       = indirizzo_secondaria.id_comune
AND (TRUNC(sysdate)                     >= NVL(TRUNC(comune_secondaria.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1)
AND TRUNC(sysdate)                       < NVL(TRUNC(comune_secondaria.DT_FINE_VALIDITA), TRUNC(sysdate)  +1))
AND provincia_secondaria.id_provincia(+) = indirizzo_secondaria.id_provincia
AND (TRUNC(sysdate)                     >= NVL(TRUNC(provincia_secondaria.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1)
AND TRUNC(sysdate)                       < NVL(TRUNC(provincia_secondaria.DT_FINE_VALIDITA), TRUNC(sysdate)  +1))