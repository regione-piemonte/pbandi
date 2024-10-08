SELECT SF.DESC_BREVE_SOGG_FINANZIATORE, ESF.IMP_QUOTA_ECON_SOGG_FINANZIAT, EC.ID_PROGETTO_CEDENTE
FROM PBANDI_T_ECONOMIE ec, PBANDI_R_ECONOM_SOGG_FINANZIAT esf, PBANDI_D_SOGGETTO_FINANZIATORE sf
WHERE ESF.ID_ECONOMIA = EC.ID_ECONOMIA
  AND ESF.ID_SOGGETTO_FINANZIATORE = SF.ID_SOGGETTO_FINANZIATORE
ORDER BY ESF.ID_SOGGETTO_FINANZIATORE asc