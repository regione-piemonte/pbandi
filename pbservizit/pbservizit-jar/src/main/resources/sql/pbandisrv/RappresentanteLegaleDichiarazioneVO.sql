/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT DISTINCT pf.cognome as cognome     ,
            pf.nome        as nome        ,
            s.id_soggetto as id_soggetto,
            pf.dt_nascita  as data_nascita,
            pf.dt_inizio_validita,
            pf.id_persona_fisica as id_persona_fisica,
            rsp.id_progetto,
            rsp.id_indirizzo_persona_fisica,
            s.codice_fiscale_soggetto     AS codice_fiscale_soggetto,
      CASE
        WHEN pf.id_comune_italiano_nascita IS NOT NULL
        THEN
          (SELECT PBANDI_D_COMUNE.desc_comune
            || '('
            ||PBANDI_D_PROVINCIA.sigla_provincia
            ||')'
             FROM PBANDI_D_COMUNE,
            PBANDI_D_PROVINCIA
            WHERE PBANDI_D_COMUNE.id_comune   = pf.id_comune_italiano_nascita
          AND PBANDI_D_PROVINCIA.id_provincia = PBANDI_D_COMUNE.id_provincia
          )
        ELSE
          (SELECT PBANDI_D_COMUNE_ESTERO.desc_comune_estero
            || '('
            || PBANDI_D_NAZIONE.desc_nazione
            || ')'
             FROM PBANDI_D_COMUNE_ESTERO,
            PBANDI_D_NAZIONE
            WHERE PBANDI_D_COMUNE_ESTERO.id_comune_estero = pf.id_comune_estero_nascita
          AND PBANDI_D_NAZIONE.id_nazione                 = pf.id_nazione_nascita
          )
      END                               AS luogo_nascita,
      i.desc_indirizzo AS indirizzo_residenza,
      i.cap            AS cap_residenza,
      CASE
        WHEN i.id_comune IS NOT NULL
        THEN
          (SELECT PBANDI_D_COMUNE.desc_comune
             FROM PBANDI_D_COMUNE
            WHERE PBANDI_D_COMUNE.id_comune = i.id_comune
          )
        ELSE
          (SELECT PBANDI_D_COMUNE_ESTERO.desc_comune_estero
             FROM PBANDI_D_COMUNE_ESTERO
            WHERE PBANDI_D_COMUNE_ESTERO.id_comune_estero = i.id_comune_estero
          )
      END AS comune_residenza,
     CASE
        WHEN i.id_comune IS NOT NULL
        THEN
          (SELECT PBANDI_D_PROVINCIA.sigla_provincia
             FROM PBANDI_D_PROVINCIA
            WHERE PBANDI_D_PROVINCIA.id_provincia = i.id_provincia
          )
        ELSE
          (SELECT PBANDI_D_NAZIONE.desc_nazione
             FROM PBANDI_D_NAZIONE
            WHERE PBANDI_D_NAZIONE.id_nazione = i.id_nazione
          )
       END AS provincia_Residenza
      FROM PBANDI_R_SOGGETTO_PROGETTO     rsp    ,
           PBANDI_R_SOGG_PROG_SOGG_CORREL rspsc  ,
           PBANDI_R_SOGGETTI_CORRELATI    rsc    ,
           PBANDI_T_PERSONA_FISICA        pf     ,
           PBANDI_T_INDIRIZZO             i      ,
           PBANDI_T_SOGGETTO              s
    WHERE rsp.id_tipo_anagrafica                            = (select id_tipo_anagrafica from PBANDI_D_TIPO_ANAGRAFICA where desc_breve_tipo_anagrafica = 'PERSONA-FISICA')
      AND rspsc.progr_soggetto_progetto                     = rsp.progr_soggetto_progetto
      AND rsc.progr_soggetti_correlati                      = rspsc.progr_soggetti_correlati
      AND rsc.id_tipo_soggetto_correlato                    = (select id_tipo_soggetto_correlato from PBANDI_D_TIPO_SOGG_CORRELATO where PBANDI_D_TIPO_SOGG_CORRELATO.desc_breve_tipo_sogg_correlato = 'Rappr. Leg.')
      AND pf.id_persona_fisica                              = rsp.id_persona_fisica
      AND i.id_indirizzo                                    = rsp.id_indirizzo_persona_fisica
      AND s.id_soggetto                                     = rsp.id_soggetto
      AND rsc.id_soggetto = rsp.id_soggetto 
      AND NVL(TRUNC(rsp.DT_FINE_VALIDITA), TRUNC(sysdate  +1)) > TRUNC(sysdate)
      AND NVL(TRUNC(rspsc.DT_FINE_VALIDITA), TRUNC(sysdate +1))  > TRUNC(sysdate)
      AND NVL(TRUNC(pf.DT_FINE_VALIDITA), TRUNC(sysdate   +1)) > TRUNC(sysdate)
     