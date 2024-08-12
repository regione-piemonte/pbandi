select soggetto.id_soggetto,
       soggetto.codice_fiscale_soggetto,
       nvl(eg.denominazione_ente_giuridico, pf.nome|| nvl2(pf.nome, ' ', '')|| pf.cognome) as denominazione,
       pf.id_persona_fisica,
       eg.id_ente_giuridico
  from
       (
select pbandi_t_soggetto.id_soggetto,
       pbandi_t_soggetto.codice_fiscale_soggetto,
       pbandi_d_tipo_soggetto.desc_breve_tipo_soggetto
  from pbandi_t_soggetto,
       pbandi_d_tipo_soggetto
 where nvl(trunc(pbandi_d_tipo_soggetto.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)
   and pbandi_d_tipo_soggetto.id_tipo_soggetto                                  = pbandi_t_soggetto.id_tipo_soggetto
       ) soggetto,
                (
select distinct teg.id_soggetto, 
       first_value(teg.id_ente_giuridico) over (partition by teg.id_soggetto order by teg.dt_inizio_validita desc) id_ente_giuridico,
       first_value(teg.denominazione_ente_giuridico) over (partition by teg.id_soggetto order by teg.dt_inizio_validita desc) denominazione_ente_giuridico
  from pbandi_t_ente_giuridico teg
  where nvl(trunc(teg.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)
                ) eg,
                (
select distinct tpf.id_soggetto, 
       first_value(tpf.id_persona_fisica) over (partition by tpf.id_soggetto order by tpf.dt_inizio_validita desc) id_persona_fisica,
       first_value(tpf.nome) over (partition by tpf.id_soggetto order by tpf.dt_inizio_validita desc) nome,
       first_value(tpf.cognome) over (partition by tpf.id_soggetto order by tpf.dt_inizio_validita desc) cognome
  from pbandi_t_persona_fisica tpf
 where nvl(trunc(tpf.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)
                ) pf
          where soggetto.id_soggetto = eg.id_soggetto (+)
            and soggetto.id_soggetto = pf.id_soggetto (+)
