SELECT pbandi_r_doc_spesa_progetto.id_progetto,
  nvl2(pbandi_t_fornitore.partita_iva_fornitore, PBANDI_T_FORNITORE.DENOMINAZIONE_FORNITORE
  || ' - '
  || PBANDI_T_FORNITORE.PARTITA_IVA_FORNITORE, nvl2(PBANDI_T_FORNITORE.DENOMINAZIONE_FORNITORE, PBANDI_T_FORNITORE.DENOMINAZIONE_FORNITORE
  || ' - '
  || PBANDI_T_FORNITORE.codice_fiscale_fornitore, PBANDI_T_FORNITORE.cognome_fornitore
  || ' '
  || PBANDI_T_FORNITORE.nome_fornitore
  || ' - '
  || PBANDI_T_FORNITORE.codice_fiscale_fornitore)) AS FORNITORE_TABELLA,
  PBANDI_T_FORNITORE.*
FROM pbandi_t_fornitore,
  pbandi_t_documento_di_spesa,
  pbandi_r_doc_spesa_progetto
WHERE pbandi_t_fornitore.id_fornitore                 = pbandi_t_documento_di_spesa.id_fornitore
AND pbandi_r_doc_spesa_progetto.ID_DOCUMENTO_DI_SPESA = pbandi_t_documento_di_spesa.ID_DOCUMENTO_DI_SPESA
