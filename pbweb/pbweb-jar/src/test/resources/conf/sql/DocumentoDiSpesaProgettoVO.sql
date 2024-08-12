SELECT docsp.id_documento_di_spesa,
  docSpPrj.tipo_invio,
  docSp.destinazione_trasferta,
  docSp.durata_trasferta,
  docSp.id_doc_riferimento,
  docSpPrj.id_progetto,
  docSpPrj.note_validazione,
  docSp.id_tipo_documento_spesa,
  docSp.id_tipo_oggetto_attivita,
  docspprj.id_stato_documento_spesa,
  docSp.id_fornitore,
  docSp.flag_elettronico,
  docSp.flag_elett_xml,
  CASE
    WHEN docSp.id_fornitore IS NOT NULL
    THEN
      (SELECT id_tipo_soggetto
      FROM pbandi_t_fornitore
      WHERE id_fornitore = docSp.id_fornitore
      )
  END id_tipo_fornitore,
  tipDocSp.desc_tipo_documento_spesa,
  tipDocSp.desc_breve_tipo_doc_spesa,
  docSp.numero_documento,
  docSp.dt_emissione_documento,
  docSp.desc_documento,
  docSpPrj.task,
  CASE
    WHEN docSp.id_fornitore IS NOT NULL
    THEN
      (SELECT NVL(nome_fornitore,' ')
      FROM pbandi_t_fornitore
      WHERE id_fornitore = docSp.id_fornitore
      )
    ELSE ' '
  END nome_fornitore,
  CASE
    WHEN docSp.id_fornitore IS NOT NULL
    THEN
      (SELECT NVL(cognome_fornitore,' ')
      FROM pbandi_t_fornitore
      WHERE id_fornitore = docSp.id_fornitore
      )
    ELSE ' '
  END cognome_fornitore,
  CASE
    WHEN docSp.id_fornitore IS NOT NULL
    THEN
      (SELECT NVL(denominazione_fornitore,' ')
      FROM pbandi_t_fornitore
      WHERE id_fornitore = docSp.id_fornitore
      )
    ELSE ' '
  END denominazione_fornitore,
  CASE
    WHEN docSp.id_fornitore IS NOT NULL
    THEN
      (SELECT codice_fiscale_fornitore
      FROM pbandi_t_fornitore
      WHERE id_fornitore = docSp.id_fornitore
      )
    ELSE ' '
  END codice_fiscale_fornitore,
  CASE
    WHEN docSp.id_fornitore IS NOT NULL
    THEN
      (SELECT partita_iva_fornitore
      FROM pbandi_t_fornitore
      WHERE id_fornitore = docSp.id_fornitore
      )
  END partita_iva_fornitore,
  NVL(docSp.importo_totale_documento,0) AS importo_totale_documento,
  statoDocSp.desc_breve_stato_doc_spesa,
  statodocsp.desc_stato_documento_spesa,
  NVL(docspprj.importo_rendicontazione,0)            AS importo_rendicontazione,
  NVL(totrendicontato.importo_totale_rendicontato,0) AS importo_totale_rendicontato,
  NVL(totquietanzato.importo_totale_quietanzato,0)   AS importo_totale_quietanzato,
  docsp.progr_fornitore_qualifica,
  NVL(docsp.imponibile,0)        AS imponibile,
  NVL(docsp.importo_iva,0)       AS importo_iva,
  NVL(docsp.importo_iva_costo,0) AS importo_iva_costo,
  docsp.id_soggetto,
  TRQ.RENDICONTABILE_QUIETANZATO,
  docspprj.id_appalto,
  CASE
    WHEN docspprj.id_appalto IS NOT NULL
    THEN
      (SELECT PBANDI_T_PROCEDURA_AGGIUDICAZ.COD_PROC_AGG
      FROM PBANDI_T_APPALTO, PBANDI_T_PROCEDURA_AGGIUDICAZ
      WHERE PBANDI_T_APPALTO.ID_APPALTO = docspprj.id_appalto AND
            PBANDI_T_PROCEDURA_AGGIUDICAZ.ID_PROCEDURA_AGGIUDICAZ = PBANDI_T_APPALTO.ID_PROCEDURA_AGGIUDICAZ
      )
  END descrizione_appalto
FROM pbandi_t_documento_di_spesa docSp,
  pbandi_d_tipo_documento_spesa tipDocSp,
  pbandi_d_stato_documento_spesa statodocsp,
  pbandi_r_doc_spesa_progetto docspprj
LEFT OUTER JOIN
  (SELECT id_documento_di_spesa,
    id_progetto,
    SUM(importo_quota_parte_doc_spesa) AS importo_totale_rendicontato
  FROM pbandi_t_quota_parte_doc_spesa
  GROUP BY (id_documento_di_spesa,id_progetto)
  ) totrendicontato
ON docspprj.id_documento_di_spesa = totrendicontato.id_documento_di_spesa
AND docspprj.id_progetto          = totrendicontato.id_progetto
LEFT OUTER JOIN
  (SELECT pds.id_documento_di_spesa,
    SUM (pag.importo_pagamento) AS importo_totale_quietanzato
  FROM pbandi_t_pagamento pag,
    pbandi_r_pagamento_doc_spesa pds
  WHERE pag.id_pagamento = pds.id_pagamento
  GROUP BY pds.id_documento_di_spesa
  ) totquietanzato
ON docspprj.id_documento_di_spesa = totquietanzato.id_documento_di_spesa
LEFT OUTER JOIN
  (SELECT id_documento_di_spesa,
    id_progetto,
    SUM (RPAGQP.IMPORTO_QUIETANZATO) AS RENDICONTABILE_QUIETANZATO
  FROM PBANDI_T_QUOTA_PARTE_DOC_SPESA,
    PBANDI_R_PAG_QUOT_PARTE_DOC_SP RPAGQP
  WHERE RPAGQP.ID_QUOTA_PARTE_DOC_SPESA=PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_QUOTA_PARTE_DOC_SPESA
  GROUP BY (ID_DOCUMENTO_DI_SPESA,ID_PROGETTO)
  ) TRQ
ON docspprj.id_documento_di_spesa                            = TRQ.id_documento_di_spesa
AND DOCSPPRJ.ID_PROGETTO                                     = TRQ.ID_PROGETTO
WHERE docSp.id_documento_di_spesa                            = docSpPrj.id_documento_di_spesa
AND docSp.id_tipo_documento_spesa                            = tipDocSp.id_tipo_documento_spesa
AND docspprj.id_stato_documento_spesa                        = statodocsp.id_stato_documento_spesa
AND NVL(TRUNC(tipdocsp.dt_fine_validita), TRUNC(sysdate   +1)) > TRUNC(sysdate)
AND NVL(TRUNC(statodocsp.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)