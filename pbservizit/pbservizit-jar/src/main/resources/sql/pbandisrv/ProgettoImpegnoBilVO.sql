/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select progetto.id_progetto,
       progetto.progr_bandolinea_intervento,
       progetto.codice_visualizzato,
       progetto.titolo_progetto,
       progetto.denominazione_beneficiario,
       nvl(impegno.id_impegno,-1) as id_impegno,
       impegno.desc_impegno,
       impegno.anno_impegno,
       impegno.numero_impegno,
       nvl(impegno.importo_attuale_impegno,0) as importo_attuale_impegno,
       nvl(impegno.disponibilita_liquidare,0) as disponibilita_liquidare,
       impegno.id_tipo_fondo,
       impegno.desc_tipo_fondo,
       contoEconomico.id_modalita_agevolazione,
       ma.desc_breve_modalita_agevolaz,
       ma.desc_modalita_agevolazione,
       contoEconomico.quota_importo_agevolato
from
/* progetto */
(
    select p.id_progetto,
           bl.id_bando,
           bl.progr_bando_linea_intervento as progr_bandolinea_intervento,
           p.codice_visualizzato,
           p.titolo_progetto,
           p.id_domanda,
           sp.id_ente_giuridico,
           sp.id_persona_fisica,
           case when sp.id_ente_giuridico is not null
            then ( select denominazione_ente_giuridico from pbandi_t_ente_giuridico where id_ente_giuridico = sp.id_ente_giuridico and NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE))
            else ( select cognome||' '||nome from pbandi_t_persona_fisica where id_persona_fisica = sp.id_persona_fisica and NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE))
           end denominazione_beneficiario
    from pbandi_t_domanda d,
         pbandi_t_progetto p,
         pbandi_r_bando_linea_intervent bl,
         pbandi_r_soggetto_progetto sp
    where p.id_domanda = d.id_domanda
      and d.progr_bando_linea_intervento = bl.progr_bando_linea_intervento
      and sp.id_progetto = p.id_progetto
      and sp.id_tipo_anagrafica in (select id_tipo_anagrafica from pbandi_d_tipo_anagrafica where desc_breve_tipo_anagrafica = 'BENEFICIARIO')
      and sp.id_tipo_beneficiario in (select id_tipo_beneficiario from pbandi_d_tipo_beneficiario where desc_breve_tipo_beneficiario <> 'BEN-ASSOCIATO' )
      and NVL(TRUNC(sp.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
) progetto LEFT OUTER JOIN
/* impegno */
(
      select distinct i.id_impegno,
           rip.id_progetto,
           i.desc_impegno,
           i.anno_impegno,
           i.numero_impegno,
           i.importo_attuale_impegno,
           i.disponibilita_liquidare,
           cap.id_tipo_fondo_cap as id_tipo_fondo,
           cap.desc_tipo_fondo_cap as desc_tipo_fondo
     from pbandi_r_impegno_progetto rip,
    ( /* capitolo */
      select cap.id_capitolo,
             cap.id_ente_competenza as id_ente_cap,
             recs.id_soggetto as id_soggetto_cap,
             tipFondo.id_tipo_fondo as id_tipo_fondo_cap,
             tipFondo.desc_tipo_fondo as desc_tipo_fondo_cap
        from pbandi_t_capitolo cap,
             pbandi_r_ente_competenza_sogg recs,
             pbandi_d_tipo_fondo tipFondo
       where cap.id_ente_competenza = recs.id_ente_competenza
         and cap.id_tipo_fondo = tipfondo.id_tipo_fondo
         and NVL(TRUNC(recs.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
         and NVL(TRUNC(tipFondo.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
    ) cap,
    pbandi_t_impegno i,
    /* provvedimento */
    ( 
      select provv.id_provvedimento,
             provv.id_ente_competenza as id_ente_provv,
             recs.id_soggetto as id_soggetto_provv
        from pbandi_t_provvedimento provv,
             pbandi_r_ente_competenza_sogg recs
       where provv.id_ente_competenza = recs.id_ente_competenza
        and NVL(TRUNC(recs.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
    ) provv 
    where i.id_impegno = rip.id_impegno (+)
      and i.id_capitolo = cap.id_capitolo
      and i.id_provvedimento = provv.id_provvedimento (+)
      and NVL(TRUNC(rip.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
) impegno
ON progetto.id_progetto = impegno.id_progetto,
/* conto ecomomico master */
( 
    select ce.id_conto_economico,
           ce.id_domanda,
           rcema.id_modalita_agevolazione,
           rcema.quota_importo_agevolato
      from pbandi_d_stato_conto_economico sce,
           pbandi_d_tipologia_conto_econ tce,
           pbandi_t_conto_economico ce LEFT JOIN pbandi_r_conto_econom_mod_agev rcema 
                                        ON ce.id_conto_economico = rcema.id_conto_economico
     where sce.id_stato_conto_economico = ce.id_stato_conto_economico
       and sce.id_tipologia_conto_economico = tce.id_tipologia_conto_economico
       and upper(tce.desc_breve_tipologia_conto_eco) = 'MASTER'  
       and NVL(TRUNC(ce.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
       and NVL(TRUNC(sce.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
       and NVL(TRUNC(tce.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
)  contoEconomico,
pbandi_d_modalita_agevolazione ma,
pbandi_r_bando_modalita_agevol rbma
where ma.id_modalita_agevolazione = contoeconomico.id_modalita_agevolazione
  and contoEconomico.id_domanda = progetto.id_domanda
  and rbma.id_bando = progetto.id_bando
  and rbma.flag_liquidazione = 'S'