/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select impProgetto.id_impegno,
       impprogetto.id_progetto,
       impprogetto.id_ente_cap,
       impprogetto.id_ente_provv,
       impProgetto.progr_bandolinea_intervento,
       impProgetto.nome_bando_linea,
       impProgetto.desc_impegno,
       impProgetto.anno_impegno,
       impprogetto.numero_impegno,
       impProgetto.anno_perente,
       impprogetto.numero_perente,
       impProgetto.anno_esercizio,
       nvl(impprogetto.importo_attuale_impegno,0) as importo_attuale_impegno,
       nvl(impprogetto.disponibilita_liquidare,0) as disponibilita_liquidare,
       impProgetto.id_tipo_fondo,
       impProgetto.desc_tipo_fondo,
       impProgetto.codice_visualizzato,
       impProgetto.titolo_progetto,
       impProgetto.denominazione_beneficiario,
       contoEconomico.quota_importo_agevolato
from 
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
/* impegno progetto */
(
  select rip.id_impegno,
       p.id_progetto,
       p.id_domanda,
       bli.progr_bando_linea_intervento as progr_bandolinea_intervento,
       bli.nome_bando_linea,
       cap.id_ente_cap,
       provv.id_ente_competenza as id_ente_provv,
       i.desc_impegno,
       i.anno_impegno,
       i.numero_impegno,
       i.anno_perente,
       i.numero_perente,
       i.anno_esercizio,
       i.importo_attuale_impegno,
       i.disponibilita_liquidare,
       cap.id_tipo_fondo_cap as id_tipo_fondo,
       cap.desc_tipo_fondo_cap as desc_tipo_fondo,
       p.codice_visualizzato,
       p.titolo_progetto,
       bli.id_bando,
       case when sp.id_ente_giuridico is not null
          then ( select denominazione_ente_giuridico from pbandi_t_ente_giuridico where id_ente_giuridico = sp.id_ente_giuridico and NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE))
          else ( select cognome||' '||nome from pbandi_t_persona_fisica where id_persona_fisica = sp.id_persona_fisica and NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE))
       end denominazione_beneficiario
 from pbandi_r_impegno_progetto rip,
      pbandi_t_progetto p,
      pbandi_t_domanda d,
      pbandi_r_bando_linea_intervent bli,
      pbandi_r_soggetto_progetto sp,
( /* capitolo */
  select cap.id_capitolo,
         cap.id_ente_competenza as id_ente_cap,
         tipFondo.id_tipo_fondo as id_tipo_fondo_cap,
         tipFondo.desc_tipo_fondo as desc_tipo_fondo_cap
    from pbandi_t_capitolo cap,
         pbandi_d_tipo_fondo tipFondo
   where cap.id_tipo_fondo = tipfondo.id_tipo_fondo
     and NVL(TRUNC(tipFondo.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
) cap,
pbandi_t_impegno i LEFT JOIN pbandi_t_provvedimento provv ON i.id_provvedimento = provv.id_provvedimento
where rip.id_progetto = p.id_progetto
  and rip.id_impegno = i.id_impegno
  and i.id_capitolo = cap.id_capitolo
  and p.id_domanda = d.id_domanda
  and d.progr_bando_linea_intervento = bli.progr_bando_linea_intervento
  and sp.id_progetto = p.id_progetto
  and sp.id_tipo_anagrafica in (select id_tipo_anagrafica from pbandi_d_tipo_anagrafica where desc_breve_tipo_anagrafica = 'BENEFICIARIO')
  and sp.id_tipo_beneficiario in (select id_tipo_beneficiario from pbandi_d_tipo_beneficiario where desc_breve_tipo_beneficiario <> 'BEN-ASSOCIATO' )
  and NVL(TRUNC(sp.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
  and NVL(TRUNC(rip.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
) impProgetto,
pbandi_d_modalita_agevolazione ma,
pbandi_r_bando_modalita_agevol rbma
where ma.id_modalita_agevolazione = contoEconomico.id_modalita_agevolazione
  and rbma.id_bando = impProgetto.id_bando
  and rbma.flag_liquidazione = 'S'
  and contoEconomico.id_domanda = impProgetto.id_domanda
/* data fine validita */
  and NVL(TRUNC(ma.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)