SELECT DISTINCT
			DOC_SPESA_DICHIARAZIONE.tipo_invio,
         DOC_SPESA_DICHIARAZIONE.ID_DICHIARAZIONE_SPESA ,
         PBANDI_T_FORNITORE.CODICE_FISCALE_FORNITORE,
         PBANDI_T_FORNITORE.NOME_FORNITORE,
         PBANDI_T_FORNITORE.COGNOME_FORNITORE,
         PBANDI_T_FORNITORE.PARTITA_IVA_FORNITORE,
         NVL (denominazione_fornitore,cognome_fornitore || ' ' || nome_fornitore) AS denominazione_fornitore,
         PBANDI_T_DOCUMENTO_DI_SPESA.DT_EMISSIONE_DOCUMENTO,
         PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_DOCUMENTO_SPESA AS ID_TIPO_DOCUMENTO_DI_SPESA,
         PBANDI_T_DOCUMENTO_DI_SPESA.DT_EMISSIONE_DOCUMENTO AS DATA_DOCUMENTO_DI_SPESA,    
         PBANDI_D_STATO_DOCUMENTO_SPESA.DESC_STATO_DOCUMENTO_SPESA,
         DECODE (pbandi_t_fornitore.id_fornitore,NULL, NULL, pbandi_d_tipo_soggetto.desc_tipo_soggetto) AS desc_Tipologia_Fornitore,
         DECODE (pbandi_t_fornitore.id_fornitore,NULL, NULL,pbandi_d_tipo_soggetto.id_tipo_soggetto) AS id_Tipo_Fornitore,
         PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_TIPO_DOCUMENTO_SPESA AS DESC_TIPO_DOCUMENTO_DI_SPESA,   
         PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_BREVE_TIPO_DOC_SPESA AS DESC_BREVE_TIPO_DOC_DI_SPESA,     
         PBANDI_T_DOCUMENTO_DI_SPESA.DESC_DOCUMENTO,
         PBANDI_T_DOCUMENTO_DI_SPESA.DESTINAZIONE_TRASFERTA  ,
         PBANDI_T_DOCUMENTO_DI_SPESA.DURATA_TRASFERTA ,
         PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOC_RIFERIMENTO ,
         PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA ,
         PBANDI_T_DOCUMENTO_DI_SPESA.ID_FORNITORE  ,
         PBANDI_T_DOCUMENTO_DI_SPESA.ID_SOGGETTO  ,
         DOC_SPESA_DICHIARAZIONE.ID_STATO_DOCUMENTO_SPESA_VALID as ID_STATO_DOCUMENTO_DI_SPESA,
         PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_DOCUMENTO_SPESA  ,
         PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_OGGETTO_ATTIVITA ,
         PBANDI_T_DOCUMENTO_DI_SPESA.ID_UTENTE_AGG ,
         PBANDI_T_DOCUMENTO_DI_SPESA.ID_UTENTE_INS  ,
         PBANDI_T_DOCUMENTO_DI_SPESA.IMPONIBILE  ,
         PBANDI_T_DOCUMENTO_DI_SPESA.IMPORTO_IVA  ,
         PBANDI_T_DOCUMENTO_DI_SPESA.IMPORTO_IVA_COSTO IMPORTO_IVA_ACOSTO ,
         PBANDI_T_DOCUMENTO_DI_SPESA.IMPORTO_TOTALE_DOCUMENTO importo_Totale_Documento_Ivato ,
         PBANDI_T_DOCUMENTO_DI_SPESA.NUMERO_DOCUMENTO ,
         (SELECT rdsp.TASK
            FROM PBANDI_R_DOC_SPESA_PROGETTO rdsp
           WHERE rdsp.id_documento_di_spesa =DOC_SPESA_DICHIARAZIONE.id_documento_di_spesa
             AND rdsp.id_progetto = DOC_SPESA_DICHIARAZIONE.id_progetto
          ) AS task_Id_Progetto,
            DOC_SPESA_DICHIARAZIONE.task,
            DOC_SPESA_DICHIARAZIONE.importo_totale_validato,
            PBANDI_T_PROGETTO.id_progetto ,
            PBANDI_T_PROGETTO.CODICE_PROGETTO ,
            PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_BREVE_TIPO_DOC_SPESA
    FROM PBANDI_T_DOCUMENTO_DI_SPESA,
         PBANDI_D_TIPO_DOCUMENTO_SPESA,
         PBANDI_D_TIPO_SOGGETTO,
         PBANDI_T_FORNITORE,
         PBANDI_D_STATO_DOCUMENTO_SPESA,
         PBANDI_T_PROGETTO,
         PBANDI_R_SOGGETTO_PROGETTO,
         (  
             /* NOTE DI CREDITO */
             select docprj.id_documento_di_spesa,
                    docprj.id_progetto,
                    dich.id_dichiarazione_spesa,
                    docprj.id_stato_documento_spesa_valid,
                    docprj.id_stato_documento_spesa,
                    docprj.task,
                    0 as importo_totale_validato,
                    docprj.tipo_invio
              from pbandi_t_documento_di_spesa ds,
                   pbandi_r_pagamento_dich_spesa pagDich,
                   pbandi_r_pagamento_doc_spesa pagdoc,
                   pbandi_r_doc_spesa_progetto docprj,
                   pbandi_t_dichiarazione_spesa dich
              where ds.id_doc_riferimento = pagdoc.id_documento_di_spesa
                and pagDich.id_pagamento = pagdoc.id_pagamento
                and ds.id_documento_di_spesa = docprj.id_documento_di_spesa
                and dich.id_dichiarazione_spesa = pagDich.id_dichiarazione_spesa
                and dich.id_progetto = docprj.id_progetto
              union 
              select docprj.id_documento_di_spesa,
                     docprj.id_progetto,
                     dich.id_dichiarazione_spesa,
                     docprj.id_stato_documento_spesa_valid,
                     docprj.id_stato_documento_spesa,
                     docprj.task,
                     nvl(sum( pagquodoc.importo_validato),0) as importo_totale_validato,
                     docprj.tipo_invio
              from pbandi_r_pagamento_dich_spesa pagdich
                   left outer join
                   pbandi_r_pag_quot_parte_doc_sp pagquodoc
                   on pagdich.id_dichiarazione_spesa = pagquodoc.id_dichiarazione_spesa And pagdich.id_pagamento = pagquodoc.id_pagamento,
                   pbandi_r_pagamento_doc_spesa pagdoc,
                   pbandi_r_doc_spesa_progetto docprj,
                   pbandi_t_dichiarazione_spesa dich
              where pagdich.id_pagamento = pagdoc.id_pagamento
                and pagdich.id_dichiarazione_spesa = dich.id_dichiarazione_spesa 
                and dich.id_progetto = docprj.id_progetto
                and pagdoc.id_documento_di_spesa = docprj.id_documento_di_spesa
              group by docprj.id_documento_di_spesa,
                       docprj.id_progetto,
                       dich.id_dichiarazione_spesa,
                       docprj.id_stato_documento_spesa_valid,
                       docprj.id_stato_documento_spesa,
                       docprj.task,
                       docprj.tipo_invio) DOC_SPESA_DICHIARAZIONE
   WHERE DOC_SPESA_DICHIARAZIONE.id_stato_documento_spesa_valid = PBANDI_D_STATO_DOCUMENTO_SPESA.id_stato_documento_spesa
     AND DOC_SPESA_DICHIARAZIONE.id_documento_di_spesa = PBANDI_T_DOCUMENTO_DI_SPESA.id_documento_di_spesa 
     and DOC_SPESA_DICHIARAZIONE.id_progetto = PBANDI_T_PROGETTO.id_progetto
     AND PBANDI_T_DOCUMENTO_DI_SPESA.id_tipo_documento_spesa = PBANDI_D_TIPO_DOCUMENTO_SPESA.id_tipo_documento_spesa
     AND PBANDI_T_DOCUMENTO_DI_SPESA.id_fornitore = PBANDI_T_FORNITORE.id_fornitore(+)
     AND PBANDI_R_SOGGETTO_PROGETTO.id_progetto = PBANDI_T_PROGETTO.id_progetto    
     AND (pbandi_t_documento_di_spesa.id_fornitore IS NULL 
        OR pbandi_d_tipo_soggetto.id_tipo_soggetto = pbandi_t_fornitore.id_tipo_soggetto
          )
     AND NVL (TRUNC (PBANDI_D_TIPO_SOGGETTO.DT_FINE_VALIDITA), TRUNC (SYSDATE + 1)) > TRUNC (SYSDATE)
     AND NVL (TRUNC (PBANDI_D_STATO_DOCUMENTO_SPESA.DT_FINE_VALIDITA), TRUNC (SYSDATE + 1)) > TRUNC (SYSDATE)       
     AND NVL (TRUNC (PBANDI_R_SOGGETTO_PROGETTO.DT_FINE_VALIDITA), TRUNC (SYSDATE + 1)) > TRUNC (SYSDATE)
     AND NVL (TRUNC (PBANDI_D_TIPO_DOCUMENTO_SPESA.DT_FINE_VALIDITA),TRUNC (SYSDATE + 1)) > TRUNC (SYSDATE)
ORDER BY codice_fiscale_fornitore,
         DESC_TIPO_DOCUMENTO_SPESA,
         NUMERO_DOCUMENTO 