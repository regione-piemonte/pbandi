select 
sog.codice_fiscale_soggetto, 
eb.iban, 
eg.denominazione_ente_giuridico, 
eg.dt_costituzione, 
fg.id_forma_giuridica, 
fg.desc_forma_giuridica,
sa.id_settore_attivita, 
sa.desc_settore, 
aa.id_attivita_ateco, 
aa.desc_ateco,
se.partita_iva, 
indi.desc_indirizzo, indi.cap,
naz.id_nazione, naz.desc_nazione, 
reg.id_regione, reg.desc_regione,
prov.id_provincia, prov.desc_provincia,
com.id_comune, com.desc_comune,
comest.id_comune_estero, comest.desc_comune_estero,
rec.email, rec.telefono, rec.fax
from pbandi_t_soggetto sog, pbandi_t_ente_giuridico eg, pbandi_d_forma_giuridica fg, pbandi_d_attivita_ateco aa,
pbandi_d_settore_attivita sa, pbandi_r_soggetto_progetto sp, pbandi_t_estremi_bancari eb, pbandi_t_sede se, 
pbandi_t_indirizzo indi, pbandi_d_provincia prov, pbandi_r_sogg_progetto_sede sps,
pbandi_d_nazione naz, pbandi_d_regione reg, pbandi_d_comune com, pbandi_d_comune_estero comest,
pbandi_t_recapiti rec
where 
sog.id_soggetto = eg.id_soggetto(+)
and eg.id_forma_giuridica = fg.id_forma_giuridica(+)
and eg.id_attivita_uic = aa.id_attivita_ateco(+)
and aa.id_settore_attivita = sa.id_settore_attivita(+)
and eg.id_ente_giuridico = sp.id_ente_giuridico(+)
and sp.id_estremi_bancari = eb.id_estremi_bancari(+)
AND SP.PROGR_SOGGETTO_PROGETTO = SPS.PROGR_SOGGETTO_PROGETTO
AND se.id_sede = sps.id_sede(+)
and sps.id_indirizzo = indi.id_indirizzo(+)
and sps.id_tipo_sede = 1
and indi.id_nazione = naz.id_nazione(+)
and indi.id_provincia = prov.id_provincia(+)
and indi.id_regione = reg.id_regione(+)
and indi.id_comune = com.id_comune(+)
and indi.id_comune_estero = comest.id_comune_estero(+)
and sps.id_recapiti = rec.id_recapiti(+)
group by sog.codice_fiscale_soggetto, 
eb.iban, 
eg.denominazione_ente_giuridico, 
eg.dt_costituzione, 
fg.id_forma_giuridica, fg.desc_forma_giuridica,
sa.id_settore_attivita, sa.desc_settore, 
aa.id_attivita_ateco, aa.desc_ateco,
se.partita_iva, 
indi.desc_indirizzo, indi.cap,
naz.id_nazione, naz.desc_nazione, 
reg.id_regione, reg.desc_regione,
prov.id_provincia, prov.desc_provincia,
com.id_comune, com.desc_comune,
comest.id_comune_estero, comest.desc_comune_estero,
rec.email, rec.telefono, rec.fax
order by eg.denominazione_ente_giuridico, eb.iban, eg.dt_costituzione, fg.desc_forma_giuridica, sa.desc_settore, aa.desc_ateco