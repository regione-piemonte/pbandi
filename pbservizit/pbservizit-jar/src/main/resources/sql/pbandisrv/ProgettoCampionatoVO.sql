/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select alias0.ID_TIPO_BENEFICIARIO,
       alias0.CODICE_FISCALE_SOGGETTO,
       alias0.PROGR_BANDO_LINEA_INTERVENTO,
       alias0.ID_TIPO_SOGGETTO,
       alias0.ID_PERSONA_FISICA,
       alias0.ID_ISTANZA_PROCESSO,
       alias0.ID_UTENTE_INS,
       alias0.NOME_BANDO_LINEA,
       alias0.ID_PROGETTO,
       alias0.ID_UTENTE_AGG,
       alias0.TITOLO_PROGETTO,
       alias0.DT_INIZIO_VALIDITA,
       alias0.CODICE_VISUALIZZATO_PROGETTO,
       alias0.ID_SOGGETTO,
       alias0.ID_ENTE_GIURIDICO,
       alias0.DT_FINE_VALIDITA,
       alias0.DENOMINAZIONE_BENEFICIARIO,
       alias0.ID_TIPO_ANAGRAFICA,
       alias1.ID_LINEA_DI_INTERVENTO,
       alias1.DESC_BREVE_LINEA,
       alias1.DESC_TIPO_LINEA,
       alias1.ID_LINEA_DI_INTERVENTO_PADRE
  from (select *
          from (SELECT rsp.id_progetto id_progetto,
                       tp.codice_visualizzato AS codice_visualizzato_progetto,
                       tp.titolo_progetto AS titolo_progetto,
                       tp.id_istanza_processo,
                       rsp.id_tipo_anagrafica,
                       rsp.id_tipo_beneficiario,
                       rsp.id_persona_fisica,
                       rsp.id_ente_giuridico,
                       ts.id_soggetto,
                       ts.codice_fiscale_soggetto,
                       ts.id_tipo_soggetto,
                       ts.id_utente_agg,
                       ts.id_utente_ins,
                       rsp.dt_inizio_validita,
                       rsp.dt_fine_validita,
                       bli.nome_bando_linea,
                       bli.progr_bando_linea_intervento,
                       CASE
                         WHEN rsp.id_persona_fisica IS NULL THEN
                          (SELECT DISTINCT eg.denominazione_ente_giuridico
                             FROM PBANDI_T_ENTE_GIURIDICO eg
                            WHERE eg.id_ente_giuridico = rsp.id_ente_giuridico)
                         ELSE
                          (SELECT DISTINCT pf.cognome || ' ' || pf.nome
                             FROM PBANDI_T_PERSONA_FISICA pf
                            WHERE pf.id_persona_fisica = rsp.id_persona_fisica)
                       END denominazione_beneficiario
                  FROM PBANDI_T_SOGGETTO              ts,
                       pbandi_r_soggetto_progetto     rsp,
                       pbandi_t_progetto              tp,
                       PBANDI_T_DOMANDA               d,
                       pbandi_r_bando_linea_intervent bli
                 WHERE ts.id_soggetto = rsp.id_soggetto
                   AND tp.ID_DOMANDA = d.ID_DOMANDA
                   AND d.progr_bando_linea_intervento =
                       bli.progr_bando_linea_intervento
                   AND rsp.id_tipo_anagrafica =
                       (SELECT dta.id_tipo_anagrafica
                          FROM pbandi_d_tipo_anagrafica dta
                         WHERE dta.desc_breve_tipo_anagrafica = 'BENEFICIARIO')
                   AND NVL(rsp.id_tipo_beneficiario, '-1') <>
                       (SELECT dtb.id_tipo_beneficiario
                          FROM pbandi_d_tipo_beneficiario dtb
                         WHERE dtb.desc_breve_tipo_beneficiario =
                               'BEN-ASSOCIATO')
                   AND rsp.id_progetto = tp.id_progetto)) alias0,
       (select *
          from (select dtl.desc_tipo_linea,
                       dtl.livello_tipo_linea,
                       dl.*,
                       rbl.progr_bando_linea_intervento,
                       rbl.id_bando
                  from (select connect_by_root dl.id_linea_di_intervento_padre id_linea_radice,
                               dl.*
                          from pbandi_d_linea_di_intervento dl
                         where (trunc(sysdate) >=
                               nvl(trunc(dl.DT_INIZIO_VALIDITA),
                                    trunc(sysdate) - 1) and
                               trunc(sysdate) <
                               nvl(trunc(dl.DT_FINE_VALIDITA),
                                    trunc(sysdate) + 1))
                        connect by prior dl.id_linea_di_intervento =
                                    dl.id_linea_di_intervento_padre
                        
                        union (select dlradici.id_linea_di_intervento id_linea_radice,
                                     dlradici.*
                                from pbandi_d_linea_di_intervento dlradici
                               where dlradici.id_linea_di_intervento_padre is null
                                 and (trunc(sysdate) >=
                                     nvl(trunc(dlradici.DT_INIZIO_VALIDITA),
                                          trunc(sysdate) - 1) and
                                     trunc(sysdate) <
                                     nvl(trunc(dlradici.DT_FINE_VALIDITA),
                                          trunc(sysdate) + 1)))) dlr,
                       (select nvl((select rlim.id_linea_di_intervento_attuale
                                     from pbandi_r_linea_interv_mapping rlim
                                    where rlim.id_linea_di_intervento_migrata =
                                          id_linea_di_intervento),
                                   id_linea_di_intervento) id_linea_di_intervento,
                               progr_bando_linea_intervento,
                               id_bando
                          from pbandi_r_bando_linea_intervent) rbl,
                       pbandi_d_linea_di_intervento dl,
                       pbandi_d_tipo_linea_intervento dtl
                 where rbl.id_linea_di_intervento =
                       dlr.id_linea_di_intervento
                   and dl.id_linea_di_intervento = dlr.id_linea_radice
                   and dtl.id_tipo_linea_intervento =
                       dl.id_tipo_linea_intervento
                   and (trunc(sysdate) >=
                       nvl(trunc(dl.DT_INIZIO_VALIDITA), trunc(sysdate) - 1) and
                       trunc(sysdate) <
                       nvl(trunc(dl.DT_FINE_VALIDITA), trunc(sysdate) + 1))
                   and (trunc(sysdate) >=
                       nvl(trunc(dtl.DT_INIZIO_VALIDITA), trunc(sysdate) - 1) and
                       trunc(sysdate) <
                       nvl(trunc(dtl.DT_FINE_VALIDITA), trunc(sysdate) + 1)))
         where trunc(sysdate) >=
               nvl(trunc(DT_INIZIO_VALIDITA), trunc(sysdate) - 1)
           and trunc(sysdate) <
               nvl(trunc(DT_FINE_VALIDITA), trunc(sysdate) + 1)) alias1
 where alias0.PROGR_BANDO_LINEA_INTERVENTO =
       alias1.PROGR_BANDO_LINEA_INTERVENTO
 and alias1.desc_tipo_linea = 'Normativa'
      
