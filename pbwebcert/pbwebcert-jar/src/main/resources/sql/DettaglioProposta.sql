select IMPORTO_PAGAMENTI_VALIDATI as importoPagamentiValidati , DT_ULTIMA_CHECKLIST_IN_LOCO as dtUltimaChecklistInLoco , CODICE_PROGETTO as codiceProgetto , DT_ULTIMO_PRERECUPERO as dtUltimoPrerecupero , IMPORTO_NON_RILEVANTE_CERTIF as importoNonRilevanteCertif , FLAG_CHECK_LIST_CERTIFICAZIONE as flagCheckListCertificazione , FIDEIUSSIONI_IN_MENO_PER_ERROR as fideiussioniInMenoPerError , IDENTIFICATIVI_IRREGOLARITA as identificativiIrregolarita , ID_PROPOSTA_CERTIFICAZ as idPropostaCertificaz , DT_ULTIMO_RECUPERO as dtUltimoRecupero , NOTA as nota , TITOLO_PROGETTO as titoloProgetto , IMPORTO_SOPPRESSIONI_NETTO as importoSoppressioniNetto , ID_TIPO_OPERAZIONE as idTipoOperazione , AVANZAMENTO as avanzamento , REVOCHE_IN_MENO_PER_ERROR as revocheInMenoPerError , IMPORTO_DA_RECUPERARE as importoDaRecuperare , DESC_BREVE_COMPLETA_ATTIVITA as descBreveCompletaAttivita , DT_CUT_OFF_VALIDAZIONI as dtCutOffValidazioni , DESC_BREVE_STATO_PROPOSTA_CERT as descBreveStatoPropostaCert , IMP_INTERESSI_RECUPERATI_NETTI as impInteressiRecuperatiNetti , IMP_CERTIFICABILE_NETTO_REVOC as impCertificabileNettoRevoc , DT_CUT_OFF_FIDEIUSSIONI as dtCutOffFideiussioni , ID_STATO_PROPOSTA_CERTIF as idStatoPropostaCertif , ID_UTENTE_INS as idUtenteIns , RECUPERI_IN_MENO_PER_ERRORE as recuperiInMenoPerErrore , COSTO_AMMESSO as costoAmmesso , FLAG_ATTIVO as flagAttivo , CONTRIBUTO_PUBBLICO_CONCESSO as contributoPubblicoConcesso , DT_ULTIMA_SOPPRESSIONE as dtUltimaSoppressione , FLAG_CHECK_LIST_IN_LOCO as flagCheckListInLoco , IMPORTO_RENDICONTATO as importoRendicontato , SOPPRESSIONI_IN_MENO_PER_ERROR as soppressioniInMenoPerError , ID_DIMENSIONE_IMPRESA as idDimensioneImpresa , PRERECUPERI_IN_MENO_PER_ERROR as prerecuperiInMenoPerError , IMPORTO_EROGAZIONI as importoErogazioni , IMPORTO_FIDEIUSSIONI as importoFideiussioni , DT_ORA_CREAZIONE as dtOraCreazione , IMP_REVOCHE_NETTO_SOPPRESSIONI as impRevocheNettoSoppressioni , IMP_CERTIFICAZIONE_NETTO_PREC as impCertificazioneNettoPrec , PERC_CONTRIBUTO_PUBBLICO as percContributoPubblico , DESC_STATO_PROPOSTA_CERTIF as descStatoPropostaCertif , EROGAZIONI_IN_MENO_PER_ERRORE as erogazioniInMenoPerErrore , ID_LINEA_DI_INTERVENTO as idLineaDiIntervento , DT_CUT_OFF_PAGAMENTI as dtCutOffPagamenti , ID_PERSONA_FISICA as idPersonaFisica , ID_INDIRIZZO_SEDE_LEGALE as idIndirizzoSedeLegale , ID_UTENTE_AGG as idUtenteAgg , IMP_CERTIFICABILE_NETTO_SOPPR as impCertificabileNettoSoppr , DENOMINAZIONE_BENEFICIARIO as denominazioneBeneficiario , IMPORTO_ECCENDENZE_VALIDAZIONE as importoEccendenzeValidazione , SPESA_CERTIFICABILE_LORDA as spesaCertificabileLorda , IMP_CERTIF_NETTO_PREMODIFICA as impCertifNettoPremodifica , IMPORTO_PRERECUPERI as importoPrerecuperi , CODICE_VISUALIZZATO as codiceVisualizzato , IMPORTO_RECUPERI_PRERECUPERI as importoRecuperiPrerecuperi , ID_PROGETTO as idProgetto , NOME_BANDO_LINEA as nomeBandoLinea , IMPORTO_RECUPERI as importoRecuperi , IMPORTO_INTERESSI_RECUPERATI as importoInteressiRecuperati , IMPORTO_REVOCHE as importoRevoche , ID_DETT_PROPOSTA_CERTIF as idDettPropostaCertif , BENEFICIARIO as beneficiario , PERC_COFIN_FESR as percCofinFesr , DT_CUT_OFF_EROGAZIONI as dtCutOffErogazioni , DT_ULTIMA_REVOCA as dtUltimaRevoca , ATTIVITA as attivita , DESC_PROPOSTA as descProposta , IMPORTO_REVOCHE_INTERMEDIO as importoRevocheIntermedio , IMPORTO_SOPPRESSIONI as importoSoppressioni , FLAG_COMP as flagComp , TITOLO_PROGETTO_ATTUALE as titoloProgettoAttuale , ID_ENTE_GIURIDICO as idEnteGiuridico , IMPORTO_CERTIFICAZIONE_NETTO as importoCertificazioneNetto , ID_STATO_PROGETTO as idStatoProgetto from (
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
) where ID_DETT_PROPOSTA_CERTIF = ?