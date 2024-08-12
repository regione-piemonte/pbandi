select distinct rsp.id_progetto
      ,rsp.id_soggetto
      , s.codice_fiscale_soggetto
      ,rsp.progr_soggetto_progetto
/* tipo anagrafica */
      ,rsp.id_tipo_anagrafica
      ,ta.desc_breve_tipo_anagrafica
      ,ta.desc_tipo_anagrafica
/* denominazione */
      ,nvl2( rsp.id_ente_giuridico,
              (select denominazione_ente_giuridico
               from pbandi_t_ente_giuridico
               where id_ente_giuridico = rsp.id_ente_giuridico),
              ( select cognome ||' '|| nome
                from pbandi_t_persona_fisica
                where id_persona_fisica = rsp.id_persona_fisica)
       ) as denominazione
       
/* flag pubblci privato */
       ,(select flag_pubblico_privato 
       from pbandi_t_ente_giuridico
       where rsp.ID_ENTE_GIURIDICO = pbandi_t_ente_giuridico.ID_ENTE_GIURIDICO) as flag_pubblico_privato
       
/* Codice UNI IPA  
       ,(select cod_uni_ipa
       from pbandi_t_ente_giuridico
       where rsp.ID_ENTE_GIURIDICO = pbandi_t_ente_giuridico.ID_ENTE_GIURIDICO) as cod_uni_ipa*/
       
/* tipo soggetto correlato */
       ,sc.id_tipo_soggetto_correlato
       ,rspsc.progr_soggetti_correlati
       ,rspsc.dt_fine_validita
       ,nvl2(sc.id_tipo_soggetto_correlato,
             (select desc_tipo_soggetto_correlato 
              from pbandi_d_tipo_sogg_correlato tsc
              where sc.id_tipo_soggetto_correlato = id_tipo_soggetto_correlato),
              ''
              ) as desc_tipo_soggetto_correlato
/* tipo soggetto */
        ,nvl2(s.id_tipo_soggetto,
              ( select desc_tipo_soggetto 
                from pbandi_d_tipo_soggetto 
                where id_tipo_soggetto = s.id_tipo_soggetto
                ),
              '') as desc_tipo_soggetto
        ,s.id_tipo_soggetto as id_tipo_soggetto
from pbandi_r_soggetto_progetto rsp
     ,pbandi_r_sogg_prog_sogg_correl rspsc
     ,pbandi_t_soggetto s
     ,pbandi_r_soggetti_correlati sc
     ,pbandi_d_tipo_anagrafica ta
where rsp.id_tipo_anagrafica = ta.id_tipo_anagrafica
   /* and rsp.dt_fine_validita is null   ora si visualizzano anche i soggetti annullati */
   and rsp.id_soggetto = s.id_soggetto
   and rsp.progr_soggetto_progetto = rspsc.progr_soggetto_progetto (+)
   and sc.progr_soggetti_correlati (+) = rspsc.progr_soggetti_correlati
   and nvl(rsp.id_tipo_beneficiario, '-1') <> (select dtb.id_tipo_beneficiario from pbandi_d_tipo_beneficiario dtb where dtb.desc_breve_tipo_beneficiario = 'BEN-ASSOCIATO')
