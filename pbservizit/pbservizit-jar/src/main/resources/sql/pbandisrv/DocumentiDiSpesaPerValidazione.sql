/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select DISTINCT 
       PBANDI_T_FORNITORE.CODICE_FISCALE_FORNITORE as codiceFiscaleFornitore,
       nvl(denominazione_fornitore, cognome_fornitore|| ' ' ||nome_fornitore) as denominazioneFornitore,
       PBANDI_T_PROGETTO.CODICE_VISUALIZZATO as codiceProgetto,
       PBANDI_T_DOCUMENTO_DI_SPESA.DT_EMISSIONE_DOCUMENTO as dataDocumentoDiSpesa , 
       PBANDI_D_STATO_DOCUMENTO_SPESA.DESC_STATO_DOCUMENTO_SPESA as descStatoDocumentoSpesa, 
       CASE
           WHEN NOT EXISTS (SELECT 1 FROM pbandi_t_pagamento tp, pbandi_r_pagamento_doc_spesa rpds WHERE tp.id_pagamento = rpds.id_pagamento AND tp.id_stato_validazione_spesa IN
							 (SELECT id_stato_validazione_spesa FROM pbandi_d_stato_validaz_spesa WHERE desc_breve_stato_validaz_spesa <> 'R')
							 AND rpds.id_documento_di_spesa = PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA) THEN 'IN VALIDAZIONE'
           WHEN EXISTS (SELECT 1 FROM pbandi_t_pagamento tp, pbandi_r_pagamento_doc_spesa rpds WHERE tp.id_pagamento = rpds.id_pagamento AND tp.id_stato_validazione_spesa =
							 (SELECT id_stato_validazione_spesa FROM pbandi_d_stato_validaz_spesa WHERE desc_breve_stato_validaz_spesa = 'S')
							 AND rpds.id_documento_di_spesa = PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA)THEN 'SOSPESO'
           ELSE 'ELABORATO'
       END AS descStatoDocumentoSpesa, 
       DECODE(pbandi_t_fornitore.id_fornitore,null,null,pbandi_d_tipo_soggetto.desc_tipo_soggetto) as descTipologiaFornitore,
       DECODE(pbandi_t_fornitore.id_fornitore,null,null,pbandi_d_tipo_soggetto.id_tipo_soggetto) as idTipoFornitore,
       PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_TIPO_DOCUMENTO_SPESA as descTipologiaDocumentoDiSpesa,
       PBANDI_T_DOCUMENTO_DI_SPESA.DESC_DOCUMENTO as  descrizioneDocumentoDiSpesa,
       PBANDI_T_DOCUMENTO_DI_SPESA.DESTINAZIONE_TRASFERTA as destinazioneTrasferta ,
       PBANDI_T_DOCUMENTO_DI_SPESA.DURATA_TRASFERTA as  durataTrasferta, 
       PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOC_RIFERIMENTO as  idDocRiferimento,
       PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA as idDocumentoDiSpesa ,
       PBANDI_T_DOCUMENTO_DI_SPESA.ID_FORNITORE as  idFornitore, 
       PBANDI_T_DOCUMENTO_DI_SPESA.ID_SOGGETTO as  idSoggetto, 
       PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_DOCUMENTO_SPESA as idTipoDocumentoDiSpesa , 
       PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_OGGETTO_ATTIVITA as idTipoOggettoAttivita , 
       PBANDI_T_DOCUMENTO_DI_SPESA.ID_UTENTE_AGG as idUtenteAgg , 
       PBANDI_T_DOCUMENTO_DI_SPESA.ID_UTENTE_INS as idUtenteIns , 
       PBANDI_T_DOCUMENTO_DI_SPESA.IMPONIBILE as  imponibile, 
       PBANDI_T_DOCUMENTO_DI_SPESA.IMPORTO_IVA as importoIva , 
       PBANDI_T_DOCUMENTO_DI_SPESA.IMPORTO_IVA_COSTO as importoIvaCosto , 
       PBANDI_T_DOCUMENTO_DI_SPESA.IMPORTO_TOTALE_DOCUMENTO as importoTotaleDocumentoIvato,
       PBANDI_T_DOCUMENTO_DI_SPESA.NUMERO_DOCUMENTO as numeroDocumento, 
       /* TODO			+ colonnaTask, */
       totPagDocSpesa.importo_totale_validato as importoTotaleValidato
  from PBANDI_D_STATO_VALIDAZ_SPESA,
       PBANDI_D_TIPO_DOCUMENTO_SPESA,
       PBANDI_D_STATO_DOCUMENTO_SPESA,
       PBANDI_D_TIPO_SOGGETTO,
       PBANDI_T_DOCUMENTO_DI_SPESA,
       PBANDI_T_FORNITORE,
       PBANDI_T_PROGETTO,
       PBANDI_T_PAGAMENTO,
       PBANDI_T_DICHIARAZIONE_SPESA,
       PBANDI_R_DOC_SPESA_PROGETTO,
       PBANDI_R_PAGAMENTO_DICH_SPESA,
       (
					 select distinct dichSp.id_progetto, 
					        dichSp.id_dichiarazione_spesa, 
					        docSp.id_documento_di_spesa,0 as importo_totale_validato 
					 from PBANDI_T_DICHIARAZIONE_SPESA dichSp, 
					      PBANDI_R_PAGAMENTO_DICH_SPESA pagDichSp, 
					      PBANDI_T_DOCUMENTO_DI_SPESA docSp, 
					      PBANDI_R_PAG_QUOT_PARTE_DOC_SP pagQuotDocs, 
					      PBANDI_R_PAGAMENTO_DOC_SPESA pDocs 
					 where pagDichSp.id_dichiarazione_spesa = dichSp.id_dichiarazione_spesa 
					   and pagQuotDocs.id_pagamento (+)           = pagDichSp.id_pagamento 
					   and pDocs.id_pagamento =  pagDichSp.id_pagamento 
					   and pDocs.id_progetto = dichSp.id_progetto 
					   and pDocs.id_documento_di_spesa = docSp.id_doc_riferimento 
					UNION 
					 select distinct ds.ID_PROGETTO    , 
					        ds.ID_DICHIARAZIONE_SPESA      , 
					        pDocs.ID_DOCUMENTO_DI_SPESA   , 
					        nvl(sum (pagQuotDocs.importo_validato) over (partition by ds.id_progetto,ds.id_dichiarazione_spesa,pDocs.id_documento_di_spesa),0) AS importo_totale_validato 
					 from PBANDI_T_DICHIARAZIONE_SPESA ds, 
					      PBANDI_R_PAGAMENTO_DICH_SPESA pds, 
					      PBANDI_R_PAG_QUOT_PARTE_DOC_SP pagQuotDocs, 
					      PBANDI_R_PAGAMENTO_DOC_SPESA pDocs 
					 where ds.ID_DICHIARAZIONE_SPESA            = pds.ID_DICHIARAZIONE_SPESA 
					   and pds.ID_PAGAMENTO                     = pagQuotDocs.ID_PAGAMENTO (+) 
					   and pDocs.id_pagamento 					 = pds.ID_PAGAMENTO 
					   and pDocs.id_progetto 					 = ds.id_progetto 
					 ) totPagDocSpesa,
       (
					 select ds.id_documento_di_spesa, 
					        rpagdocspesa.id_pagamento, 
					        rpagdocspesa.id_progetto, 
					        rpagdocspesa.id_utente_agg, 
					        rpagdocspesa.id_utente_ins 
					 from PBANDI_R_PAGAMENTO_DOC_SPESA rpagdocspesa, 
					      PBANDI_T_DOCUMENTO_DI_SPESA ds 
					 where ds.id_doc_riferimento = rpagdocspesa.id_documento_di_spesa 
					UNION ALL   select id_documento_di_spesa, 
					        id_pagamento,          id_progetto,
					        id_utente_agg,         id_utente_ins 
					 from PBANDI_R_PAGAMENTO_DOC_SPESA 
					) PAGAMENTO_DOC_SPESA
 where PBANDI_R_DOC_SPESA_PROGETTO.ID_STATO_DOCUMENTO_SPESA = PBANDI_D_STATO_DOCUMENTO_SPESA.ID_STATO_DOCUMENTO_SPESA
   and PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA = PBANDI_R_DOC_SPESA_PROGETTO.ID_DOCUMENTO_DI_SPESA
	 and PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_DOCUMENTO_SPESA = PBANDI_D_TIPO_DOCUMENTO_SPESA.ID_TIPO_DOCUMENTO_SPESA
	 and PBANDI_T_DOCUMENTO_DI_SPESA.ID_FORNITORE = PBANDI_T_FORNITORE.ID_FORNITORE(+)
	 and PBANDI_R_DOC_SPESA_PROGETTO.ID_PROGETTO = PBANDI_T_PROGETTO.ID_PROGETTO
	 and PAGAMENTO_DOC_SPESA.id_pagamento = PBANDI_R_PAGAMENTO_DICH_SPESA.id_pagamento
	 and PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA = PAGAMENTO_DOC_SPESA.ID_DOCUMENTO_DI_SPESA
	 and PBANDI_T_PAGAMENTO.ID_STATO_VALIDAZIONE_SPESA = PBANDI_D_STATO_VALIDAZ_SPESA.ID_STATO_VALIDAZIONE_SPESA
	 and PBANDI_T_PAGAMENTO.ID_PAGAMENTO = PBANDI_R_PAGAMENTO_DICH_SPESA.ID_PAGAMENTO
	 and ( pbandi_t_documento_di_spesa.id_fornitore is null or pbandi_d_tipo_soggetto.id_tipo_soggetto = pbandi_t_fornitore.id_tipo_soggetto )
	 and PBANDI_T_DICHIARAZIONE_SPESA.id_dichiarazione_spesa = PBANDI_R_PAGAMENTO_DICH_SPESA.id_dichiarazione_spesa
   and totPagDocSpesa.ID_PROGETTO = PBANDI_T_PROGETTO.ID_PROGETTO 
	 and totPagDocSpesa.ID_DOCUMENTO_DI_SPESA = PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA
	 and totPagDocSpesa.ID_DICHIARAZIONE_SPESA = PBANDI_T_DICHIARAZIONE_SPESA.id_dichiarazione_spesa
order by codiceFiscaleFornitore, descTipologiaDocumentoDiSpesa, numeroDocumento