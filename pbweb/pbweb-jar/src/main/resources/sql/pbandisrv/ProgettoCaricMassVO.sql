SELECT pr.id_progetto,codice_visualizzato,id_soggetto_beneficiario,dt_validazione
FROM PBANDI_T_CARICA_MASS_DOC_SPESA cms 
JOIN pbandi_t_progetto pr
ON  cms.id_progetto=pr.id_progetto