/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select distinct 
      rsp.id_progetto
      ,rsp.id_soggetto as id_soggetto_beneficiario
      ,rsps.id_sede
      ,rsps.progr_soggetto_progetto_sede
      ,rsps.flag_sede_amministrativa
/* tipo sede */      
      ,rsps.id_tipo_sede
      ,ts.desc_breve_tipo_sede
      ,ts.desc_tipo_sede
/* partita iva */
      ,s.partita_iva
/* denominazione sede */
	  ,s.denominazione
/* dt_inizio_validita sede */
	  ,s.dt_inizio_validita
/* attivita ateco */
	  ,s.id_attivita_ateco
/* dimensione territoriale */
	  ,s.id_dimensione_territor
/* nazione */
    ,nvl2(i.id_nazione,
           i.id_nazione,
           ( nvl2(i.id_comune_estero,
                 ( select id_nazione 
                    from pbandi_d_comune_estero 
                   where id_comune_estero = i.id_comune_estero 
                     and nvl(trunc(dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)
                  ),
                  ( select id_nazione
                    from pbandi_d_nazione
                    where desc_nazione like 'ITALIA'
                  )
                )
           )
       ) as id_nazione
      ,nvl2(i.id_nazione,
           ( select desc_nazione 
             from pbandi_d_nazione 
            where id_nazione = i.id_nazione 
              and  nvl(trunc(dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)
           ),
           ( nvl2( i.id_comune_estero,
                   ( select n.desc_nazione 
                      from pbandi_d_comune_estero ce, pbandi_d_nazione n 
                     where ce.id_comune_estero = i.id_comune_estero 
                       and n.id_nazione = ce.id_nazione
                       and nvl(trunc(ce.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)
                    ),
                    'ITALIA'
                  )
           )
        ) as desc_nazione   
/* regione */
    ,nvl2( i.id_regione ,
           i.id_regione,
           nvl2( i.id_provincia,
                 ( select prov.id_regione
                   from pbandi_d_regione reg, pbandi_d_provincia prov
                    where prov.id_regione = reg.id_regione
                      and prov.id_provincia = i.id_provincia
                      AND NVL(TRUNC(reg.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
                      AND NVL(TRUNC(prov.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
                 ), 
                 nvl2( i.id_comune_estero,
                   null,
                   (  select b.id_regione
                      from pbandi_d_comune a, pbandi_d_provincia b
                      where a.id_comune = i.id_comune
                        and b.id_provincia = a.id_provincia
                        AND NVL(TRUNC(a.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
                        AND NVL(TRUNC(b.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
                   )
                )
           )
     ) AS id_regione
      ,nvl2( i.id_regione,
             (SELECT desc_regione
                FROM pbandi_d_regione
                WHERE id_regione                                  = i.id_regione
                  AND NVL(TRUNC(dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
             ), 
             nvl2( i.id_provincia,
                   ( select reg.desc_regione
                     from pbandi_d_regione reg, pbandi_d_provincia prov
                      where prov.id_regione = reg.id_regione
                        and prov.id_provincia = i.id_provincia
                        AND NVL(TRUNC(reg.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
                        AND NVL(TRUNC(prov.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
                   ), 
                   nvl2( i.id_comune_estero,
                     null,
                     (  select c.desc_regione
                        from pbandi_d_comune a, pbandi_d_provincia b, pbandi_d_regione c
                        where a.id_comune = i.id_comune
                          and b.id_provincia = a.id_provincia
                          and b.id_regione = c.id_regione
                          AND NVL(TRUNC(a.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
                          AND NVL(TRUNC(b.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
                          AND NVL(TRUNC(c.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
                     )
                  )
            )
      ) AS desc_regione
/* provincia */
        ,nvl2( i.id_provincia,
               i.id_provincia,
               nvl2( i.id_comune_estero,
                     null,
                     (  select id_provincia
                        from pbandi_d_comune
                        where id_comune = i.id_comune
                        AND NVL(TRUNC(dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
                     )
                )
         ) id_provincia
        ,nvl2( i.id_provincia, 
              ( select desc_provincia
                 from pbandi_d_provincia
                where id_provincia = i.id_provincia 
                  and  nvl(trunc(dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)),
              nvl2( i.id_comune_estero,
                     null,
                     (  select b.desc_provincia
                        from pbandi_d_comune a, pbandi_d_provincia b
                        where a.id_comune = i.id_comune
                          and b.id_provincia = a.id_provincia
                          AND NVL(TRUNC(a.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
                          AND NVL(TRUNC(b.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
                     )
                )
        ) as desc_provincia
        ,nvl2( i.id_provincia, 
              ( select  sigla_provincia
                 from pbandi_d_provincia
                where id_provincia = i.id_provincia 
                  and  nvl(trunc(dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)),
              nvl2( i.id_comune_estero,
                     null,
                     (  select b.sigla_provincia
                        from pbandi_d_comune a, pbandi_d_provincia b
                        where a.id_comune = i.id_comune
                          and b.id_provincia = a.id_provincia
                          AND NVL(TRUNC(a.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
                          AND NVL(TRUNC(b.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
                     )
                )
        ) as sigla_provincia
/* comune */
        ,nvl2(i.id_comune,i.id_comune, i.id_comune_estero) as id_comune,
        NVL2( I.ID_COMUNE, 
              ( select cod_Istat_Comune
                 from pbandi_d_comune
                where id_comune = i.id_comune 
                  AND  NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(SYSDATE +1)) > TRUNC(SYSDATE))
       ,null ) as cod_istat_comune
        ,nvl2( i.id_comune, 
              ( select desc_comune
                 from pbandi_d_comune
                where id_comune = i.id_comune 
                  and  nvl(trunc(dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)),
              nvl2( i.id_comune_estero,
                    ( select desc_comune_estero
                        from pbandi_d_comune_estero
                       where id_comune_estero = i.id_comune_estero 
                         and  nvl(trunc(dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)),
                   null
              )
        ) as desc_comune
/* indirizzo */
        ,i.id_indirizzo
        ,i.desc_indirizzo
        ,i.civico
        ,i.cap
/* email */
        , rsps.id_recapiti
        , r.email
/* fax */
       ,r.fax
/* telefono */
        ,r.telefono
from pbandi_r_sogg_progetto_sede rsps
     ,pbandi_r_soggetto_progetto rsp
     , pbandi_t_indirizzo i
     , pbandi_t_recapiti r
     , pbandi_t_sede s
     , pbandi_d_tipo_sede ts
where rsps.progr_soggetto_progetto = rsp.progr_soggetto_progetto 
  and rsps.id_tipo_sede = ts.id_tipo_sede
  and rsp.ID_TIPO_ANAGRAFICA = (select dta.id_tipo_anagrafica from pbandi_d_tipo_anagrafica dta where dta.desc_breve_tipo_anagrafica = 'BENEFICIARIO')
  and nvl(rsp.id_tipo_beneficiario, '-1') <> (select dtb.id_tipo_beneficiario from pbandi_d_tipo_beneficiario dtb where dtb.desc_breve_tipo_beneficiario = 'BEN-ASSOCIATO')
  and rsps.id_indirizzo = i.id_indirizzo (+)
  and rsps.id_recapiti = r.id_recapiti (+)
  and rsps.id_sede = s.id_sede
  /* vengono considerate valide le sedi con dt_fine_validita non valorizzata */
  and s.dt_fine_validita is null
  and nvl(trunc(rsp.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)
  and nvl(trunc(ts.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)
  and nvl(trunc(r.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)
  and nvl(trunc(i.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)