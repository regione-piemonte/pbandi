select distinct DENOMINAZIONE_BENEFICIARIO as denominazioneBeneficiario ,
 ID_SOGGETTO_BENEFICIARIO as idSoggettoBeneficiario
  from PBANDI_T_PREVIEW_DETT_PROP_CER
   where ID_PROPOSTA_CERTIFICAZ = ?
   