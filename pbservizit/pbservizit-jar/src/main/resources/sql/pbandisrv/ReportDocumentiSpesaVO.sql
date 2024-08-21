/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT *
FROM
  (SELECT DISTINCT TRUNC(ds.DT_DICHIARAZIONE) dt_dich_spesa,
    TRUNC(ds.DT_CHIUSURA_VALIDAZIONE) dt_validazione,
    ds.NOTE_CHIUSURA_VALIDAZIONE,
    PBANDI_T_FORNITORE.CODICE_FISCALE_FORNITORE,
    DECODE(pbandi_t_fornitore.id_fornitore,NULL,NULL,pbandi_d_tipo_soggetto.desc_tipo_soggetto) AS desc_tipologia_fornitore,
    DECODE(pbandi_t_fornitore.id_fornitore,NULL,NULL,pbandi_d_tipo_soggetto.id_tipo_soggetto)   AS id_tipo_fornitore,
    PBANDI_T_FORNITORE.NOME_FORNITORE,
    PBANDI_T_FORNITORE.COGNOME_FORNITORE,
    PBANDI_T_FORNITORE.DENOMINAZIONE_FORNITORE,
    DECODE(DECODE(pbandi_t_fornitore.id_fornitore,NULL,NULL,pbandi_d_tipo_soggetto.id_tipo_soggetto),1,PBANDI_T_FORNITORE.COGNOME_FORNITORE
    || ' '
    || PBANDI_T_FORNITORE.NOME_FORNITORE,2,PBANDI_T_FORNITORE.DENOMINAZIONE_FORNITORE) AS desc_fornitore,
    PBANDI_D_STATO_DOCUMENTO_SPESA.DESC_STATO_DOCUMENTO_SPESA,
    PBANDI_D_STATO_DOCUMENTO_SPESA.ID_STATO_DOCUMENTO_SPESA,
    PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_TIPO_DOCUMENTO_SPESA,
    PBANDI_D_TIPO_DOCUMENTO_SPESA.desc_breve_tipo_doc_spesa,
    PBANDI_T_DOCUMENTO_DI_SPESA.DT_EMISSIONE_DOCUMENTO,
    PBANDI_T_DOCUMENTO_DI_SPESA.DESC_DOCUMENTO,
    PBANDI_T_DOCUMENTO_DI_SPESA.DESTINAZIONE_TRASFERTA,
    PBANDI_T_DOCUMENTO_DI_SPESA.DURATA_TRASFERTA,
    PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOC_RIFERIMENTO,
    PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA,
    PBANDI_T_DOCUMENTO_DI_SPESA.ID_FORNITORE,
    PBANDI_T_DOCUMENTO_DI_SPESA.ID_SOGGETTO,
    PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_DOCUMENTO_SPESA,
    PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_OGGETTO_ATTIVITA,
    PBANDI_T_DOCUMENTO_DI_SPESA.ID_UTENTE_AGG,
    PBANDI_T_DOCUMENTO_DI_SPESA.ID_UTENTE_INS,
    PBANDI_T_DOCUMENTO_DI_SPESA.IMPONIBILE,
    PBANDI_T_DOCUMENTO_DI_SPESA.IMPORTO_IVA,
    PBANDI_T_DOCUMENTO_DI_SPESA.IMPORTO_IVA_COSTO,
    PBANDI_T_DOCUMENTO_DI_SPESA.IMPORTO_TOTALE_DOCUMENTO,
    PBANDI_T_DOCUMENTO_DI_SPESA.NUMERO_DOCUMENTO,
    rdsp.task,
    rdsp.NOTE_VALIDAZIONE,
    tqpds.id_rigo_conto_economico,
    ( SELECT DISTINCT NVL2(P.DESC_VOCE_DI_SPESA_PADRE, P.DESC_VOCE_DI_SPESA_PADRE
      || ' - '
      || V.DESC_VOCE_DI_SPESA, V.DESC_VOCE_DI_SPESA) DESC_VOCE_DI_SPESA_COMPOSTA
    FROM PBANDI_R_BANDO_VOCE_SPESA R,
      PBANDI_D_VOCE_DI_SPESA V,
      pbandi_t_rigo_conto_economico tr,
      (SELECT DESC_VOCE_DI_SPESA AS DESC_VOCE_DI_SPESA_PADRE,
        ID_VOCE_DI_SPESA
      FROM PBANDI_D_VOCE_DI_SPESA
      WHERE NVL(TRUNC(PBANDI_D_VOCE_DI_SPESA.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
      ) P
    WHERE V.ID_VOCE_DI_SPESA          = R.ID_VOCE_DI_SPESA
    AND P.ID_VOCE_DI_SPESA(+)         = V.ID_VOCE_DI_SPESA_PADRE
    AND TQPDS.ID_RIGO_CONTO_ECONOMICO = TR.ID_RIGO_CONTO_ECONOMICO
    AND TR.ID_VOCE_DI_SPESA           = V.ID_VOCE_DI_SPESA
    ) voce_di_spesa,
    rdsp.id_progetto,
    rpds.id_dichiarazione_spesa,
    rpqpds.totale_rettifiche,
    SUM(rpqpds.importo_validato) importo_validato_doc,
    SUM(rpqpds.importo_quietanzato) importo_quietanzato_voce
  FROM PBANDI_T_DOCUMENTO_DI_SPESA,
    PBANDI_D_TIPO_DOCUMENTO_SPESA,
    PBANDI_D_TIPO_SOGGETTO,
    PBANDI_T_FORNITORE,
    PBANDI_D_STATO_DOCUMENTO_SPESA,
    PBANDI_R_PAGAMENTO_DICH_SPESA rpds,
    PBANDI_R_DOC_SPESA_PROGETTO rdsp,
    PBANDI_R_PAGAMENTO_DOC_SPESA rpag,
    PBANDI_T_QUOTA_PARTE_DOC_SPESA tqpds,
    (select rpqpds.ID_PAGAMENTO,
    		rpqpds.ID_QUOTA_PARTE_DOC_SPESA,
    		rpqpds.progr_pag_quot_parte_doc_sp,
    		rpqpds.importo_validato,
    		rpqpds.importo_quietanzato,
    		sum(rett.importo_rettifica) as totale_rettifiche
     from PBANDI_R_PAG_QUOT_PARTE_DOC_SP rpqpds 
    	left outer join pbandi_t_rettifica_di_spesa rett 
    	on rpqpds.progr_pag_quot_parte_doc_sp = rett.progr_pag_quot_parte_doc_sp
    group by rpqpds.ID_PAGAMENTO,
    	rpqpds.ID_QUOTA_PARTE_DOC_SPESA,
    	rpqpds.progr_pag_quot_parte_doc_sp,
    	rpqpds.importo_validato,
    	rpqpds.importo_quietanzato
    ) rpqpds,
    PBANDI_T_DICHIARAZIONE_SPESA ds
  WHERE rdsp.ID_STATO_DOCUMENTO_SPESA                 = PBANDI_D_STATO_DOCUMENTO_SPESA.ID_STATO_DOCUMENTO_SPESA
  AND PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA                      = rdsp.ID_DOCUMENTO_DI_SPESA
  AND rpds.id_pagamento (+)                                                  = rpag.id_pagamento
  AND tqpds.id_documento_di_spesa                                            = rdsp.id_documento_di_spesa
  AND tqpds.id_progetto                                                      = rdsp.id_progetto
  AND rpqpds.ID_PAGAMENTO                                                    = rpag.id_pagamento
  AND rpqpds.ID_QUOTA_PARTE_DOC_SPESA                                        = tqpds.ID_QUOTA_PARTE_DOC_SPESA
  AND PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_DOCUMENTO_SPESA                    = PBANDI_D_TIPO_DOCUMENTO_SPESA.ID_TIPO_DOCUMENTO_SPESA
  AND PBANDI_T_DOCUMENTO_DI_SPESA.ID_FORNITORE                               = PBANDI_T_FORNITORE.ID_FORNITORE (+)
  AND ds.ID_DICHIARAZIONE_SPESA                                              =rpds.ID_DICHIARAZIONE_SPESA
   and rdsp.id_progetto                                                      = ds.id_progetto
  AND ( pbandi_t_documento_di_spesa.id_fornitore                            IS NULL
  OR pbandi_d_tipo_soggetto.id_tipo_soggetto                                 = pbandi_t_fornitore.id_tipo_soggetto )
  AND NVL(TRUNC(PBANDI_D_TIPO_SOGGETTO.DT_FINE_VALIDITA), TRUNC(sysdate        +1)) > TRUNC(sysdate)
  AND NVL(TRUNC(PBANDI_D_STATO_DOCUMENTO_SPESA.DT_FINE_VALIDITA), TRUNC(sysdate+1)) > TRUNC(sysdate)
  AND NVL(TRUNC(PBANDI_D_TIPO_DOCUMENTO_SPESA.DT_FINE_VALIDITA), TRUNC(sysdate +1)) > TRUNC(sysdate)
  GROUP BY rpds.id_dichiarazione_spesa,
    PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA,
    rdsp.id_progetto,
    TQPDS.ID_RIGO_CONTO_ECONOMICO,
    rdsp.task,
    rdsp.note_validazione,
    PBANDI_D_STATO_DOCUMENTO_SPESA.DESC_STATO_DOCUMENTO_SPESA,
    PBANDI_D_STATO_DOCUMENTO_SPESA.ID_STATO_DOCUMENTO_SPESA,
    PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_TIPO_DOCUMENTO_SPESA,
    PBANDI_D_TIPO_DOCUMENTO_SPESA.desc_breve_tipo_doc_spesa,
    PBANDI_T_DOCUMENTO_DI_SPESA.DT_EMISSIONE_DOCUMENTO,
    PBANDI_T_DOCUMENTO_DI_SPESA.DESC_DOCUMENTO,
    PBANDI_T_DOCUMENTO_DI_SPESA.DESTINAZIONE_TRASFERTA,
    PBANDI_T_DOCUMENTO_DI_SPESA.DURATA_TRASFERTA,
    PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOC_RIFERIMENTO,
    PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA,
    PBANDI_T_DOCUMENTO_DI_SPESA.ID_FORNITORE,
    PBANDI_T_DOCUMENTO_DI_SPESA.ID_SOGGETTO,
    PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_DOCUMENTO_SPESA,
    PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_OGGETTO_ATTIVITA,
    PBANDI_T_DOCUMENTO_DI_SPESA.ID_UTENTE_AGG,
    PBANDI_T_DOCUMENTO_DI_SPESA.ID_UTENTE_INS,
    PBANDI_T_DOCUMENTO_DI_SPESA.IMPONIBILE,
    PBANDI_T_DOCUMENTO_DI_SPESA.IMPORTO_IVA,
    PBANDI_T_DOCUMENTO_DI_SPESA.IMPORTO_IVA_COSTO,
    PBANDI_T_DOCUMENTO_DI_SPESA.IMPORTO_TOTALE_DOCUMENTO,
    PBANDI_T_DOCUMENTO_DI_SPESA.NUMERO_DOCUMENTO,
    pbandi_t_fornitore.id_fornitore,
    pbandi_d_tipo_soggetto.id_tipo_soggetto,
    PBANDI_T_FORNITORE.COGNOME_FORNITORE,
    PBANDI_T_FORNITORE.NOME_FORNITORE,
    PBANDI_T_FORNITORE.DENOMINAZIONE_FORNITORE,
    pbandi_d_tipo_soggetto.desc_tipo_soggetto,
    PBANDI_T_FORNITORE.CODICE_FISCALE_FORNITORE,
    ds.DT_DICHIARAZIONE,
    ds.DT_CHIUSURA_VALIDAZIONE,
    ds.NOTE_CHIUSURA_VALIDAZIONE,
    rpqpds.totale_rettifiche
  ) tab1,
  (SELECT id_documento_di_spesa_  AS id_documento_di_spesa_,
    id_dichiarazione_spesa_       AS id_dichiarazione_spesa_,
    id_ultima_dichiarazione_spesa AS id_ultima_dichiarazione_spesa,
    SUM(importo_validato_voce)    AS importo_validato_voce
  FROM
    (SELECT rpds.id_documento_di_spesa id_documento_di_spesa_,
      rqpds.importo_validato AS importo_validato_voce,
      rpdis.id_dichiarazione_spesa id_dichiarazione_spesa_,
      MAX(rpdis.id_dichiarazione_spesa) OVER (partition BY rpds.id_pagamento, ds.id_progetto) id_ultima_dichiarazione_spesa,
      rqpds.id_pagamento
    FROM PBANDI_R_PAG_QUOT_PARTE_DOC_SP rqpds,
      PBANDI_R_PAGAMENTO_DOC_SPESA rpds,
      PBANDI_R_PAGAMENTO_DICH_SPESA rpdis,
       pbandi_t_dichiarazione_spesa ds
    WHERE RQPDS.ID_PAGAMENTO    = RPDS.ID_PAGAMENTO
    AND rpds.id_pagamento       = rpdis.id_pagamento
    AND rqpds.importo_validato IS NOT NULL
    and ds.id_dichiarazione_spesa = rpdis.id_dichiarazione_spesa
    )
  GROUP BY (id_documento_di_spesa_, id_dichiarazione_spesa_,id_ultima_dichiarazione_spesa)
  HAVING id_ultima_dichiarazione_spesa = id_dichiarazione_spesa_
  ) tab2
WHERE tab2.id_documento_di_spesa_ = tab1.id_documento_di_spesa
AND tab2.id_dichiarazione_spesa_  = tab1.id_dichiarazione_spesa
