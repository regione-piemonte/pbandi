select       
             attoliq.id_atto_liquidazione as id_atto_liquidazione,
             benefsogg.codice_fiscale_soggetto as codice_fiscale,
             benefsede.partita_iva as part_iva,
             beneficiario.codice_beneficiario_bilancio as cod_ben,
             beneficiario.denominazione_beneficiario as rag_soc,
             indirizzo.desc_indirizzo as indirizzo,
             indirizzo.cap as cap,
             comune.desc_comune as comune,
             provincia.sigla_provincia as prov,
             indirizzo.email as mail,
             beneficiario.sesso,
             beneficiario.dt_nascita,
             beneficiario.comuneNascita as comune_nascita,
             beneficiario.provinciaNascita as prov_nascita,
             beneficiario.id_ente_giuridico,
             beneficiario.id_persona_fisica
from
pbandi_t_atto_liquidazione  attoliq,
(select codice_fiscale_soggetto, id_beneficiario_bilancio from pbandi_t_beneficiario_bilancio tb, 
              pbandi_t_soggetto so
              where so.id_soggetto = tb.id_soggetto
        ) benefsogg,
        (select partita_iva, id_beneficiario_bilancio from pbandi_t_beneficiario_bilancio tb, 
              pbandi_t_sede se
              where se.id_sede = tb.id_sede
        ) benefsede,
        (
        select codice_beneficiario_bilancio, id_beneficiario_bilancio, id_ente_giuridico, id_persona_fisica ,
         case when tb.id_ente_giuridico is not null
            then ( select denominazione_ente_giuridico from pbandi_t_ente_giuridico where id_ente_giuridico = tb.id_ente_giuridico and NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE))
            else ( select cognome||'.'||nome from pbandi_t_persona_fisica where id_persona_fisica = tb.id_persona_fisica and NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE))
           end denominazione_beneficiario,
          case when tb.id_ente_giuridico is not null
            then ( select '' from dual)
            else ( select sesso from pbandi_t_persona_fisica where id_persona_fisica = tb.id_persona_fisica and NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE))
          end sesso,
          case when tb.id_ente_giuridico is not null
            then ( select null from dual)
            else ( select dt_nascita from pbandi_t_persona_fisica where id_persona_fisica = tb.id_persona_fisica and NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE))
          end dt_nascita,
          case when tb.id_ente_giuridico is not null
            then ( select '' from dual)
            else ( 
            select case when tpf.id_comune_italiano_nascita IS NOT NULL 
              then (
              SELECT dc.desc_comune
               FROM PBANDI_D_COMUNE dc
               WHERE dc.id_comune   = tpf.id_comune_italiano_nascita 
              ) 
              else 
              (
              SELECT ce.desc_comune_estero
                 FROM PBANDI_D_COMUNE_ESTERO ce,
                PBANDI_D_NAZIONE dn
                WHERE ce.id_comune_estero = tpf.id_comune_estero_nascita
                AND dn.id_nazione = tpf.id_nazione_nascita 
              )
            end  desc_comune_nascita			
            from pbandi_t_persona_fisica tpf
            where tpf.id_persona_fisica = tb.id_persona_fisica
            )
          end comuneNascita,
	         case when tb.id_ente_giuridico is not null
            then ( select '' from dual)
            else ( 
            select case when tpf.id_comune_italiano_nascita IS NOT NULL 
              then (
              SELECT dp.sigla_provincia
               FROM PBANDI_D_COMUNE dc, PBANDI_D_PROVINCIA dp
               WHERE dc.id_comune   = tpf.id_comune_italiano_nascita 
					and dc.id_provincia = dp.id_provincia
              ) 
            end  desc_comune_nascita			
            from pbandi_t_persona_fisica tpf
            where tpf.id_persona_fisica = tb.id_persona_fisica
            )
          end provinciaNascita
             from pbandi_t_beneficiario_bilancio tb
        ) beneficiario,
        (
        select desc_indirizzo, id_beneficiario_bilancio, cap, email from pbandi_t_indirizzo ti,
         pbandi_t_beneficiario_bilancio tb , pbandi_t_recapiti tr
          where tb.id_indirizzo  = ti.id_indirizzo (+) and tb.id_recapiti = tr.id_recapiti (+)
        ) indirizzo,
        (
        select desc_comune, tb.id_beneficiario_bilancio
          from pbandi_t_indirizzo ti,
          pbandi_t_beneficiario_bilancio tb,
          pbandi_d_comune dc where
          ti.id_comune = dc.id_comune and 
          tb.id_indirizzo = ti.id_indirizzo 
      ) comune, 
        (
        select sigla_provincia, tb.id_beneficiario_bilancio
          from pbandi_t_indirizzo ti,
          pbandi_t_beneficiario_bilancio tb,
          pbandi_d_provincia dc where
          ti.id_provincia = dc.id_provincia and 
          tb.id_indirizzo = ti.id_indirizzo 
         ) provincia
where benefsogg.id_beneficiario_bilancio (+) = attoliq.id_beneficiario_bilancio and
      benefsede.id_beneficiario_bilancio (+) = attoliq.id_beneficiario_bilancio and
      beneficiario.id_beneficiario_bilancio (+) = attoliq.id_beneficiario_bilancio and
      indirizzo.id_beneficiario_bilancio (+) = attoliq.id_beneficiario_bilancio and
      comune.id_beneficiario_bilancio (+) = attoliq.id_beneficiario_bilancio and
      provincia.id_beneficiario_bilancio (+) = attoliq.id_beneficiario_bilancio
