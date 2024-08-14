select CERTIFICATO_LORDO_CUMULATO as certificatoLordoCumulato , IMPORTO_SOPPRESSIONI_CUM as importoSoppressioniCum , 
ID_PROPOSTA_CERTIFICAZ as idPropostaCertificaz , ID_UTENTE_AGG as idUtenteAgg , COLONNA_C as colonnaC , ID_DETT_PROPOSTA_CERTIF as idDettPropostaCertif ,
 IMPORTO_RECUPERI_CUM as importoRecuperiCum , ID_DETT_PROP_CERT_ANNUAL as idDettPropCertAnnual , IMPORTO_EROGAZIONI_CUM as importoErogazioniCum ,
  IMPORTO_CERTIF_NETTO_ANNUAL as importoCertifNettoAnnual , CERTIFICATO_NETTO_CUMULATO as certificatoNettoCumulato , IMPORTO_PAGAM_VALID_CUM as importoPagamValidCum , 
  DATA_AGG as dataAgg , IMPORTO_REVOCHE_RILEV_CUM as importoRevocheRilevCum 
  from PBANDI_T_DETT_PROP_CERT_ANNUAL where ID_DETT_PROP_CERT_ANNUAL = ?
