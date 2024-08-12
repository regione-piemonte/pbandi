select distinct
       f.id_fornitore,
       f.id_soggetto_fornitore,
       f.id_tipo_soggetto,
       f.id_forma_giuridica,
       ts.desc_breve_tipo_soggetto,
       ts.desc_tipo_soggetto,
       f.partita_iva_fornitore,
       f.denominazione_fornitore,
       f.codice_fiscale_fornitore,
       f.cognome_fornitore,
       f.nome_fornitore,
       f.dt_fine_validita as dt_fine_validita_fornitore,
       case when fq.progr_fornitore_qualifica is null
          then 'N'
          else 'S'
       end as flag_has_qualifica
from pbandi_t_fornitore f 
    left outer join pbandi_r_fornitore_qualifica fq on f.id_fornitore = fq.id_fornitore
    left outer join pbandi_d_qualifica q on q.id_qualifica = fq.id_qualifica,
    pbandi_d_tipo_soggetto ts
where f.id_tipo_soggetto = ts.id_tipo_soggetto