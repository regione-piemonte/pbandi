select DESC_LINEA as descLinea , CODICE_PROGETTO as codiceProgetto , PROGR_BANDO_LINEA_INTERVENTO as progrBandoLineaIntervento , IMPORTO_PAGAMENTI as importoPagamenti , ID_PREVIEW_DETT_PROP_CER as idPreviewDettPropCer , ID_SOGGETTO_BENEFICIARIO as idSoggettoBeneficiario , ID_PROPOSTA_CERTIFICAZ as idPropostaCertificaz , ID_UTENTE_INS as idUtenteIns , ID_UTENTE_AGG as idUtenteAgg , NOME_BANDO_LINEA as nomeBandoLinea , ID_PROGETTO as idProgetto , FLAG_ATTIVO as flagAttivo , IMPORTO_REVOCHE as importoRevoche , DESC_BREVE_LINEA as descBreveLinea , IMPORTO_CERTIFICAZIONE_NETTO as importoCertificazioneNetto , DENOMINAZIONE_BENEFICIARIO as denominazioneBeneficiario from (
SELECT pbandi_t_preview_dett_prop_cer.*,
  pbandi_d_linea_di_intervento.desc_linea,
  pbandi_d_linea_di_intervento.desc_breve_linea
FROM pbandi_t_preview_dett_prop_cer,
  pbandi_d_linea_di_intervento
WHERE pbandi_d_linea_di_intervento.id_linea_di_intervento(+) = pbandi_t_preview_dett_prop_cer.id_linea_di_intervento
) where  (  ( ID_PROPOSTA_CERTIFICAZ = ? )  or  ( ID_PROPOSTA_CERTIFICAZ = ? and FLAG_ATTIVO = 'N' )  )  and  ( ID_PROGETTO_SIF is null  )