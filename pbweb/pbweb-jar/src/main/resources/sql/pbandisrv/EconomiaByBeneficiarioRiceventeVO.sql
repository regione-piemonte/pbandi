SELECT e.*,
    cedente.CODICE_PROGETTO AS codice_progetto_cedente,
    cedente.CODICE_VISUALIZZATO AS codice_visualizzato_cedente,
    ricevente.CODICE_PROGETTO AS codice_progetto_ricevente,
    ricevente.CODICE_VISUALIZZATO AS codice_visualizzato_ricevente,
    eg.denominazione_ente_giuridico
FROM pbandi_t_economie e,
     pbandi_t_progetto cedente,
     pbandi_t_progetto ricevente,
     pbandi_r_soggetto_progetto sp,
     pbandi_t_ente_giuridico eg,
     pbandi_t_soggetto s     
WHERE cedente.id_progetto = e.id_progetto_cedente
  and ricevente.id_progetto(+) = e.id_progetto_ricevente
  and sp.id_progetto = ricevente.id_progetto
  and (sp.id_tipo_anagrafica = 1 AND sp.id_tipo_beneficiario <> 4)
  and eg.id_ente_giuridico = sp.id_ente_giuridico
  and s.id_soggetto = eg.id_soggetto