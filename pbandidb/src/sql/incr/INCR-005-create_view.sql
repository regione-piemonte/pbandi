/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE OR REPLACE FORCE VIEW PBANDI_V_SOGGETTO_PROGETTO AS 
  select m.id_soggetto
    ||'_'
    ||m.codice_fiscale_soggetto
    ||'@'
    ||dta.desc_breve_tipo_anagrafica
    as cod_utente,
      dta.desc_breve_tipo_anagrafica, 
      m.*
  from pbandi_d_tipo_anagrafica dta,
       (
    SELECT distinct tp.id_progetto,
      rbli.progr_bando_linea_intervento,
      rbli.nome_bando_linea,
      ts.codice_fiscale_soggetto,
      ts.id_soggetto,
      ts.id_tipo_anagrafica,
      ts.dt_inizio_validita,
      ts.dt_fine_validita,
      null progr_soggetto_progetto,
      ts.flag_aggiornato_flux,
      ts.id_tipo_soggetto,
      ts.id_utente_agg,
      ts.id_utente_ins,
      tp.id_istanza_processo,
      tp.codice_visualizzato codice_visualizzato_progetto,
      tp.titolo_progetto
FROM
      (
      select ts1.*,
        tec.id_ente_competenza,
        rsta.id_tipo_anagrafica,
        rsta.dt_inizio_validita,
        rsta.dt_fine_validita,
        rsta.flag_aggiornato_flux
      from pbandi_t_soggetto ts1,
        pbandi_t_ente_competenza tec,
        pbandi_r_sogg_tipo_anagrafica rsta
      where rsta.id_soggetto = ts1.id_soggetto
        and rsta.id_tipo_anagrafica not in (
select dta.id_tipo_anagrafica
  from pbandi_d_tipo_anagrafica dta
 where dta.desc_breve_tipo_anagrafica in ('PERSONA-FISICA', 'OI-ISTRUTTORE', 'ADG-ISTRUTTORE')
        )
        and (rsta.id_tipo_anagrafica not in
              (
select dta.id_tipo_anagrafica
  from pbandi_d_tipo_anagrafica dta
 where dta.desc_breve_tipo_anagrafica in ('BEN-MASTER', 'OI-IST-MASTER', 'ADG-IST-MASTER', 'CREATOR')
              )
           or (not exists (select 'x' from pbandi_r_ente_competenza_sogg recs where recs.id_soggetto = ts1.id_soggetto and nvl(trunc(recs.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate))
            or exists (select 'x' from pbandi_r_ente_competenza_sogg recs where recs.id_soggetto = ts1.id_soggetto and recs.id_ente_competenza = tec.id_ente_competenza and nvl(trunc(recs.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate))
              )
            )
      ) ts,
      (
select td.progr_bando_linea_intervento,
       tp.*
  from pbandi_t_domanda td,
       pbandi_t_progetto tp
 where td.id_domanda = tp.id_domanda
      ) tp,
      pbandi_r_bando_linea_intervent rbli,
      pbandi_r_bando_linea_ente_comp rble
  where rbli.progr_bando_linea_intervento = tp.progr_bando_linea_intervento (+)
    and rble.progr_bando_linea_intervento = rbli.progr_bando_linea_intervento
    and rble.id_ruolo_ente_competenza     =
      (select dre.id_ruolo_ente_competenza
      from pbandi_d_ruolo_ente_competenza dre
      where dre.desc_breve_ruolo_ente = 'DESTINATARIO'
      )
    and ts.id_ente_competenza = rble.id_ente_competenza
union
    SELECT rsp.id_progetto id_progetto,
      rbli.progr_bando_linea_intervento,
      rbli.nome_bando_linea,
      ts.codice_fiscale_soggetto,
      ts.id_soggetto,
      rsp.id_tipo_anagrafica,
      rsp.dt_inizio_validita,
      rsp.dt_fine_validita,
      rsp.progr_soggetto_progetto,
      rsp.flag_aggiornato_flux,
      ts.id_tipo_soggetto,
      ts.id_utente_agg,
      ts.id_utente_ins,
      tp.id_istanza_processo,
      tp.codice_visualizzato codice_visualizzato_progetto,
      tp.titolo_progetto
    FROM PBANDI_T_SOGGETTO ts,
      pbandi_r_soggetto_progetto rsp,
      pbandi_t_domanda td,
      pbandi_r_bando_linea_intervent rbli,
      pbandi_t_progetto tp
    WHERE ts.id_soggetto            = rsp.id_soggetto
    and td.id_domanda                     = tp.id_domanda
    and rbli.progr_bando_linea_intervento = td.progr_bando_linea_intervento
    and tp.id_progetto                                     = rsp.id_progetto
) m
 where m.id_tipo_anagrafica = dta.id_tipo_anagrafica;