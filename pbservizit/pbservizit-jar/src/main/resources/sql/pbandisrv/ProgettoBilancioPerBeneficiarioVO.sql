/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select p.id_progetto,
       p.codice_visualizzato, 
       p.titolo_progetto,
       sp.id_soggetto,
       case when sp.id_ente_giuridico is not null
            then ( select denominazione_ente_giuridico from pbandi_t_ente_giuridico where id_ente_giuridico = sp.id_ente_giuridico and NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE))
            else ( select cognome||' '||nome from pbandi_t_persona_fisica where id_persona_fisica = sp.id_persona_fisica and NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE))
       end denominazione_beneficiario,
       contoEconomico.quota_importo_agevolato,
       bli.nome_bando_linea
from  pbandi_t_progetto p,
      pbandi_r_soggetto_progetto sp,
      pbandi_t_domanda d,
      pbandi_r_bando_linea_intervent bli,
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
)  contoEconomico
where sp.id_progetto = p.id_progetto
  and sp.id_tipo_anagrafica in (select id_tipo_anagrafica from pbandi_d_tipo_anagrafica where desc_breve_tipo_anagrafica = 'BENEFICIARIO')
  and sp.id_tipo_beneficiario in (select id_tipo_beneficiario from pbandi_d_tipo_beneficiario where desc_breve_tipo_beneficiario <> 'BEN-ASSOCIATO' )
  and NVL(TRUNC(sp.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
  and contoEconomico.id_domanda = p.id_domanda
  and d.id_domanda = p.id_domanda
  and bli.progr_bando_linea_intervento = d.progr_bando_linea_intervento