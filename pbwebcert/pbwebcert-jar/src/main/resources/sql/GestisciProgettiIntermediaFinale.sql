select CODICE_PROGETTO as codiceProgetto , DESC_STATO_PROPOSTA_CERTIF as descStatoPropostaCertif , ID_LINEA_DI_INTERVENTO as idLineaDiIntervento , ID_PROPOSTA_CERTIFICAZ as idPropostaCertificaz , ID_UTENTE_AGG as idUtenteAgg , COLONNA_C as colonnaC , IMPORTO_CERTIF_NETTO_ANNUAL as importoCertifNettoAnnual , IMPORTO_PAGAM_VALID_CUM as importoPagamValidCum , DATA_AGG as dataAgg , IMPORTO_REVOCHE_RILEV_CUM as importoRevocheRilevCum , DESC_BREVE_STATO_PROPOSTA_CERT as descBreveStatoPropostaCert , CERTIFICATO_LORDO_CUMULATO as certificatoLordoCumulato , IMPORTO_SOPPRESSIONI_CUM as importoSoppressioniCum , ID_STATO_PROPOSTA_CERTIF as idStatoPropostaCertif , ID_PROGETTO as idProgetto , NOME_BANDO_LINEA as nomeBandoLinea , ID_DETT_PROPOSTA_CERTIF as idDettPropostaCertif , IMPORTO_RECUPERI_CUM as importoRecuperiCum , ID_DETT_PROP_CERT_ANNUAL as idDettPropCertAnnual , IMPORTO_EROGAZIONI_CUM as importoErogazioniCum , BENEFICIARIO as beneficiario , DESC_PROPOSTA as descProposta , CERTIFICATO_NETTO_CUMULATO as certificatoNettoCumulato , ASSE as asse from (
SELECT
    annual.*,
    dc.NOME_BANDO_LINEA,
    dc.CODICE_PROGETTO,
    pc.desc_proposta,
    sp.id_progetto,
    pc.id_stato_proposta_certif,
    p.codice_visualizzato,
    p.titolo_progetto AS titolo_progetto_attuale,
    sc.desc_breve_stato_proposta_cert,
    sc.desc_stato_proposta_certif,
    li.desc_linea AS attivita ,
    pcl.id_linea_di_intervento,
    CASE
      WHEN sp.id_persona_fisica IS NULL
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
    END AS beneficiario,
    (SELECT desc_breve_linea
     FROM (SELECT connect_by_root dl.id_linea_di_intervento_padre id_linea_radice, dl.id_linea_di_intervento
           FROM  pbandi_d_linea_di_intervento dl
                 CONNECT BY PRIOR dl.id_linea_di_intervento = dl.id_linea_di_intervento_padre) dlr,
                 (SELECT NVL((SELECT rlim.id_linea_di_intervento_attuale FROM pbandi_r_linea_interv_mapping rlim WHERE rlim.id_linea_di_intervento_migrata = id_linea_di_intervento), id_linea_di_intervento) id_linea_di_intervento,
                         progr_bando_linea_intervento
                  FROM pbandi_r_bando_linea_intervent) rbl, pbandi_d_linea_di_intervento dl, pbandi_d_tipo_linea_intervento dtl
                  WHERE rbl.id_linea_di_intervento = dlr.id_linea_di_intervento
                    AND dl.id_linea_di_intervento = dlr.id_linea_radice
                    AND dtl.id_tipo_linea_intervento = dl.id_tipo_linea_intervento
                    AND dtl.livello_tipo_linea = (SELECT PBANDI_D_TIPO_LINEA_INTERVENTO.LIVELLO_TIPO_LINEA
                                                  FROM  pbandi_d_tipo_linea_intervento
                                                  WHERE cod_tipo_linea = '02')
                    AND d.progr_bando_linea_intervento = rbl.progr_bando_linea_intervento) as asse
  FROM PBANDI_T_DETT_PROPOSTA_CERTIF dc,
    pbandi_t_dett_prop_cert_annual annual,
    PBANDI_T_PROGETTO p,
    PBANDI_T_PROPOSTA_CERTIFICAZ pc,
    PBANDI_D_STATO_PROPOSTA_CERTIF sc,
    PBANDI_T_DOMANDA d,
    PBANDI_R_BANDO_LINEA_INTERVENT bli,
    PBANDI_D_LINEA_DI_INTERVENTO li ,
    PBANDI_R_SOGGETTO_PROGETTO sp ,
    PBANDI_D_TIPO_ANAGRAFICA ta ,
    PBANDI_D_TIPO_BENEFICIARIO tb,
    PBANDI_R_PROPOSTA_CERTIF_LINEA pcl
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
  AND annual.ID_DETT_PROPOSTA_CERTIF   = dc.ID_DETT_PROPOSTA_CERTIF
  AND pcl.id_proposta_certificaz = pc.id_proposta_certificaz
 ORDER BY asse, beneficiario

) where ID_PROPOSTA_CERTIFICAZ = ?