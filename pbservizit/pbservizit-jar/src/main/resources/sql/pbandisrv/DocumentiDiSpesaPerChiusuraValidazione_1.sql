/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

(SELECT DISTINCT renAll.ID_DOCUMENTO_DI_SPESA AS idDocumentoDiSpesa,
  renAll.DESC_TIPO_DOCUMENTO_SPESA            AS descTipoDocumento,
  renAll.ID_TIPO_DOCUMENTO_SPESA              AS idTipoDocumentoSpesa ,
  renAll.NUMERO_DOCUMENTO                     AS numeroDocumento,
  renAll.DT_EMISSIONE_DOCUMENTO               AS dtEmissioneDocumento,
  renAll.DESC_STATO_DOCUMENTO_SPESA           AS descStatoDocumento,
  renAll.IMPORTO_TOTALE_DOCUMENTO             AS importoTotaleDocumento,
  notAll.NOTE_CREDITO_TOTALE                  AS noteCreditoTotale,
  pagAll.PAGATO_TOTALE                        AS totaleImportoPagamenti ,
  renAll.RENDICONTABILE_TOTALE                AS importoRendicontabile,
  notAll.NOTE_CREDITO_REN_TOTALE              AS noteCreditoRenTotale,
  pagAllQtp.VALIDATO_TOTALE                   AS validatoTotale,
  pagAll.NUM_PAGAMENTI                        AS numeroPagamenti ,
  pagOpen.NUM_PAGAMENTI_APERTI                AS numeroPagamentiAperti,
  dicOpen.NUM_DICHIARAZIONI_APERTE            AS numeroDichiarazioniAperte
FROM
  (SELECT renAllIn.ID_DOCUMENTO_DI_SPESA,
    renAllIn.DESC_TIPO_DOCUMENTO_SPESA,
    renAllIn.ID_TIPO_DOCUMENTO_SPESA,
    renAllIn.NUMERO_DOCUMENTO,
    renAllIn.DT_EMISSIONE_DOCUMENTO,
    renAllIn.DESC_STATO_DOCUMENTO_SPESA,
    renAllIn.IMPORTO_TOTALE_DOCUMENTO,
    SUM(renAllIn.RENDICONTABILE_PARZIALE) RENDICONTABILE_TOTALE
  FROM
    ( SELECT DISTINCT docAll.ID_DOCUMENTO_DI_SPESA,
      tipoDoc.DESC_TIPO_DOCUMENTO_SPESA,
      tipoDoc.ID_TIPO_DOCUMENTO_SPESA,
      docAll.NUMERO_DOCUMENTO,
      docAll.DT_EMISSIONE_DOCUMENTO,
      statoDoc.DESC_STATO_DOCUMENTO_SPESA,
      docAll.IMPORTO_TOTALE_DOCUMENTO,
      docProAll.IMPORTO_RENDICONTAZIONE RENDICONTABILE_PARZIALE,
      docProAll.ID_PROGETTO
    FROM pbandi_t_documento_di_spesa docAll,
      pbandi_r_doc_spesa_progetto docProAll,
      pbandi_d_tipo_documento_spesa tipoDoc,
      pbandi_d_stato_documento_spesa statoDoc,
      pbandi_r_pagamento_doc_spesa pagDoc,
      pbandi_r_pagamento_dich_spesa pagDic
    WHERE docAll.ID_DOCUMENTO_DI_SPESA  = docProAll.ID_DOCUMENTO_DI_SPESA
    AND docAll.ID_TIPO_DOCUMENTO_SPESA  = tipoDoc.ID_TIPO_DOCUMENTO_SPESA
    AND docProAll.ID_STATO_DOCUMENTO_SPESA = statoDoc.ID_STATO_DOCUMENTO_SPESA
    AND docAll.ID_DOCUMENTO_DI_SPESA    = pagDoc.ID_DOCUMENTO_DI_SPESA
    AND pagDoc.ID_PAGAMENTO             = pagDic.ID_PAGAMENTO
    AND pagDic.ID_DICHIARAZIONE_SPESA   = :idDichiarazione
    ) renAllIn
  GROUP BY renAllIn.ID_DOCUMENTO_DI_SPESA,
    renAllIn.DESC_TIPO_DOCUMENTO_SPESA,
    renAllIn.ID_TIPO_DOCUMENTO_SPESA,
    renAllIn.NUMERO_DOCUMENTO,
    renAllIn.DT_EMISSIONE_DOCUMENTO,
    renAllIn.DESC_STATO_DOCUMENTO_SPESA,
    renAllIn.IMPORTO_TOTALE_DOCUMENTO
  ) renAll,
  ( SELECT DISTINCT pagDocAll.ID_DOCUMENTO_DI_SPESA,
    SUM(pag.IMPORTO_PAGAMENTO) over (partition BY pagDocAll.ID_DOCUMENTO_DI_SPESA ) PAGATO_TOTALE,
    COUNT(pag.ID_PAGAMENTO) over (partition BY pagDocAll.ID_DOCUMENTO_DI_SPESA ) NUM_PAGAMENTI
  FROM pbandi_r_pagamento_doc_spesa pagDocAll,
    pbandi_t_pagamento pag
  WHERE pagDocAll.ID_PAGAMENTO = pag.ID_PAGAMENTO pagAll,
    ( SELECT DISTINCT pagDocAll.ID_DOCUMENTO_DI_SPESA,
      COUNT(pag.ID_PAGAMENTO) over (partition BY pagDocAll.ID_DOCUMENTO_DI_SPESA ) NUM_PAGAMENTI_APERTI
    FROM pbandi_r_pagamento_doc_spesa pagDocAll,
      pbandi_t_pagamento pag,
      pbandi_d_stato_validaz_spesa statoPag
    WHERE pagDocAll.ID_PAGAMENTO                 = pag.ID_PAGAMENTO
    AND pag.ID_STATO_VALIDAZIONE_SPESA           = statoPag.ID_STATO_VALIDAZIONE_SPESA
    AND statoPag.DESC_BREVE_STATO_VALIDAZ_SPESA IN ('I', 'T')
    ) pagOpen,
    ( SELECT DISTINCT pagDocAll.ID_DOCUMENTO_DI_SPESA,
      SUM(pagQtp.IMPORTO_VALIDATO) over (partition BY pagDocAll.ID_DOCUMENTO_DI_SPESA ) VALIDATO_TOTALE
    FROM pbandi_r_pagamento_doc_spesa pagDocAll,
      pbandi_t_pagamento pag,
      PBANDI_R_PAG_QUOT_PARTE_DOC_SP pagQtp
    WHERE pagDocAll.ID_PAGAMENTO = pag.ID_PAGAMENTO
    AND pag.ID_PAGAMENTO         = pagQtp.ID_PAGAMENTO
    ) pagAllQtp,
    (SELECT noteAll.ID_DOC_RIFERIMENTO AS ID_DOCUMENTO_DI_SPESA,
      SUM(noteAll.IMPORTO_TOTALE_DOCUMENTO) over (partition BY noteAll.ID_DOC_RIFERIMENTO ) NOTE_CREDITO_TOTALE,
      SUM(noteRenAll.IMPORTO_RENDICONTAZIONE) over (partition BY noteAll.ID_DOC_RIFERIMENTO ) NOTE_CREDITO_REN_TOTALE
    FROM pbandi_t_documento_di_spesa noteAll,
      pbandi_r_doc_spesa_progetto noteRenAll
    WHERE noteAll.ID_DOCUMENTO_DI_SPESA = noteRenAll.ID_DOCUMENTO_DI_SPESA
    AND noteAll.ID_DOC_RIFERIMENTO     IS NOT NULL
    ) notAll,
    ( SELECT DISTINCT pagDoc.ID_DOCUMENTO_DI_SPESA,
      COUNT(dic.ID_DICHIARAZIONE_SPESA) over (partition BY pagDoc.ID_DOCUMENTO_DI_SPESA, pagDic.ID_PAGAMENTO ) NUM_DICHIARAZIONI_APERTE
    FROM pbandi_r_pagamento_doc_spesa pagDoc,
      pbandi_r_pagamento_dich_spesa pagDic,
      pbandi_t_dichiarazione_spesa dic
    WHERE pagDoc.ID_PAGAMENTO        = pagDic.ID_PAGAMENTO
    AND pagDic.ID_DICHIARAZIONE_SPESA= dic.ID_DICHIARAZIONE_SPESA
    AND dic.DT_CHIUSURA_VALIDAZIONE IS NULL
    ) dicOpen
  WHERE renAll.ID_DOCUMENTO_DI_SPESA = pagAll.ID_DOCUMENTO_DI_SPESA(+)
  AND renAll.ID_DOCUMENTO_DI_SPESA   = pagOpen.ID_DOCUMENTO_DI_SPESA(+)
  AND renAll.ID_DOCUMENTO_DI_SPESA   = pagAllQtp.ID_DOCUMENTO_DI_SPESA(+)
  AND renAll.ID_DOCUMENTO_DI_SPESA   = notAll.ID_DOCUMENTO_DI_SPESA(+)
  AND renAll.ID_DOCUMENTO_DI_SPESA   = dicOpen.ID_DOCUMENTO_DI_SPESA(+)
  ) ;
