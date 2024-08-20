SELECT PBANDI_R_SOGGETTO_PROGETTO.id_soggetto AS id_soggetto,
       CASE
         WHEN PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO IS NOT NULL THEN (SELECT PBANDI_T_ENTE_GIURIDICO.denominazione_ente_giuridico
                                                                             FROM PBANDI_T_ENTE_GIURIDICO
                                                                             WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO)
         ELSE (SELECT PBANDI_T_PERSONA_FISICA.cognome || ' ' || PBANDI_T_PERSONA_FISICA.nome
               FROM PBANDI_T_PERSONA_FISICA
               WHERE PBANDI_T_PERSONA_FISICA.id_persona_fisica = PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica)
       END AS descrizione,
       CASE
         WHEN PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO IS NOT NULL THEN (SELECT PBANDI_T_ENTE_GIURIDICO.id_forma_giuridica
                                                                             FROM PBANDI_T_ENTE_GIURIDICO
                                                                             WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO)
         ELSE NULL
       END AS idFormaGiuridica,
       CASE
         WHEN PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO IS NOT NULL THEN (SELECT PBANDI_T_ENTE_GIURIDICO.id_dimensione_impresa
                                                                             FROM PBANDI_T_ENTE_GIURIDICO
                                                                             WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO)
         ELSE NULL
       END AS idDimensioneImpresa,
       CASE
         WHEN PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO IS NOT NULL THEN (SELECT PBANDI_T_ENTE_GIURIDICO.dt_inizio_validita
                                                                             FROM PBANDI_T_ENTE_GIURIDICO
                                                                             WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO)
         ELSE (SELECT PBANDI_T_PERSONA_FISICA.dt_inizio_validita
               FROM PBANDI_T_PERSONA_FISICA
               WHERE PBANDI_T_PERSONA_FISICA.id_persona_fisica = PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica)
       END AS dataIniziovalidita,
       PBANDI_T_SOGGETTO.CODICE_FISCALE_SOGGETTO AS codiceFiscale,
       PBANDI_R_SOGGETTO_PROGETTO.ID_PROGETTO as id_progetto
FROM PBANDI_R_SOGGETTO_PROGETTO,
     PBANDI_T_SOGGETTO
WHERE PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = 1
AND   NVL(PBANDI_R_SOGGETTO_PROGETTO.id_tipo_beneficiario,-1) <> 4
AND   PBANDI_T_SOGGETTO.id_soggetto = PBANDI_R_SOGGETTO_PROGETTO.id_soggetto
AND   PBANDI_R_SOGGETTO_PROGETTO.id_progetto = ?

