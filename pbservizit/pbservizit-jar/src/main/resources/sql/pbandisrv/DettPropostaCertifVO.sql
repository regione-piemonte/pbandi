/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT dc.*,
  pc.desc_proposta,
  pc.dt_cut_off_erogazioni,
  pc.dt_cut_off_pagamenti,
  pc.dt_cut_off_fideiussioni,
  pc.dt_cut_off_validazioni,
  pc.dt_ora_creazione,
  pc.id_stato_proposta_certif,
  p.codice_visualizzato,
  p.titolo_progetto as titolo_progetto_attuale,
  sc.desc_breve_stato_proposta_cert,
  sc.desc_stato_proposta_certif,
  li.desc_linea as attivita ,
  (SELECT substr(sys_connect_by_path(v.desc_breve_linea,'.'),2)
  FROM PBANDI_D_LINEA_DI_INTERVENTO v
  WHERE v.id_linea_di_intervento               = li.id_linea_di_intervento
    START WITH v.id_linea_di_intervento_padre is null
    CONNECT BY prior v.id_linea_di_intervento  = v.id_linea_di_intervento_padre
  ) as desc_breve_completa_attivita ,
  (SELECT SUM(PBANDI_R_DET_PROP_CER_SOGG_FIN.perc_tipo_sogg_finanziatore)
  FROM PBANDI_R_DET_PROP_CER_SOGG_FIN,
    PBANDI_D_TIPO_SOGG_FINANZIAT
  WHERE PBANDI_R_DET_PROP_CER_SOGG_FIN.id_dett_proposta_certif = dc.id_dett_proposta_certif
  AND PBANDI_R_DET_PROP_CER_SOGG_FIN.id_tipo_sogg_finanziat    = PBANDI_D_TIPO_SOGG_FINANZIAT.id_tipo_sogg_finanziat
  AND PBANDI_D_TIPO_SOGG_FINANZIAT.desc_breve_tipo_sogg_finanziat LIKE 'CPUPOR%'
  ) as PERC_CONTRIBUTO_PUBBLICO ,
  ( select distinct PBANDI_R_DET_PROP_CER_SOGG_FIN.perc_tipo_sogg_finanziatore
  from PBANDI_R_DET_PROP_CER_SOGG_FIN,
    PBANDI_D_TIPO_SOGG_FINANZIAT
  where PBANDI_R_DET_PROP_CER_SOGG_FIN.id_dett_proposta_certif    = dc.id_dett_proposta_certif
  AND PBANDI_R_DET_PROP_CER_SOGG_FIN.id_tipo_sogg_finanziat       = PBANDI_D_TIPO_SOGG_FINANZIAT.id_tipo_sogg_finanziat
  AND PBANDI_D_TIPO_SOGG_FINANZIAT.desc_breve_tipo_sogg_finanziat = 'COFPOR'
  ) as PERC_COFIN_FESR ,
  CASE
    WHEN sp.id_persona_fisica is null
    THEN
      ( SELECT DISTINCT eg.denominazione_ente_giuridico
        || '  '
        || sg.codice_fiscale_soggetto
      FROM PBANDI_T_ENTE_GIURIDICO eg,
        PBANDI_T_SOGGETTO sg
      WHERE eg.id_ente_giuridico = sp.id_ente_giuridico
      AND sg.id_soggetto         = sp.id_soggetto
      )
    ELSE
      ( SELECT DISTINCT pf.cognome
        || ' '
        || pf.nome
        || '  '
        || sg.codice_fiscale_soggetto
      FROM PBANDI_T_PERSONA_FISICA pf,
        PBANDI_T_SOGGETTO sg
      WHERE pf.id_persona_fisica = sp.id_persona_fisica
      AND sg.id_soggetto         = sp.id_soggetto
      )
  END as beneficiario
FROM PBANDI_T_DETT_PROPOSTA_CERTIF dc,
  PBANDI_T_PROGETTO p,
  PBANDI_T_PROPOSTA_CERTIFICAZ pc,
  PBANDI_D_STATO_PROPOSTA_CERTIF sc,
  PBANDI_T_DOMANDA d,
  PBANDI_R_BANDO_LINEA_INTERVENT bli,
  PBANDI_D_LINEA_DI_INTERVENTO li ,
  PBANDI_R_SOGGETTO_PROGETTO sp ,
  PBANDI_D_TIPO_ANAGRAFICA ta ,
  PBANDI_D_TIPO_BENEFICIARIO tb
WHERE dc.id_progetto                 = p.id_progetto
AND pc.id_proposta_certificaz        = dc.id_proposta_certificaz
AND pc.id_stato_proposta_certif      = sc.id_stato_proposta_certif
AND p.id_domanda                     = d.id_domanda
AND bli.PROGR_BANDO_LINEA_INTERVENTO = d.PROGR_BANDO_LINEA_INTERVENTO
AND li.ID_LINEA_DI_INTERVENTO        = bli.ID_LINEA_DI_INTERVENTO
AND sp.id_tipo_anagrafica            = ta.id_tipo_anagrafica
AND sp.id_tipo_beneficiario          = tb.id_tipo_beneficiario
AND ta.desc_breve_tipo_anagrafica    = 'BENEFICIARIO'
AND tb.desc_breve_tipo_beneficiario <> 'BEN-ASSOCIATO'
AND p.id_progetto                    = sp.id_progetto