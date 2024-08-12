SELECT   pbandi_t_documento_di_spesa.dt_emissione_documento
         AS data_documento_di_spesa,
         pbandi_r_doc_spesa_progetto.tipo_invio,
         pbandi_d_tipo_documento_spesa.desc_tipo_documento_spesa
         AS desc_tipo_documento_di_spesa,
         pbandi_d_tipo_documento_spesa.desc_breve_tipo_doc_spesa
         AS desc_breve_tipo_doc_spesa,  
         pbandi_t_documento_di_spesa.id_documento_di_spesa
         AS id_documento_di_spesa,
         pbandi_t_documento_di_spesa.id_doc_riferimento
         AS id_doc_di_riferimento,
         pbandi_t_documento_di_spesa.flag_elettronico AS flag_elettronico,
         pbandi_t_documento_di_spesa.id_fornitore 
         AS id_fornitore,
         pbandi_r_soggetto_progetto.id_soggetto 
         AS id_soggetto,
         pbandi_t_documento_di_spesa.id_tipo_documento_spesa
         AS id_tipo_documento_di_spesa,
         pbandi_t_documento_di_spesa.numero_documento
         AS numero_documento_di_spesa,
         pbandi_t_documento_di_spesa.importo_totale_documento
         AS importo_totale_documento,
         pbandi_r_doc_spesa_progetto.id_progetto 
         AS id_progetto,
         pbandi_r_doc_spesa_progetto.importo_rendicontazione
         AS importo_rendicontazione,
         pbandi_r_doc_spesa_progetto.task
         AS task,
		 '' AS flag_allegati,
         '' AS totale_importo_pagamenti,
         '' AS totale_note_credito,
           (SELECT SUM
                    (pbandi_t_quota_parte_doc_spesa.importo_quota_parte_doc_spesa
                    )
            FROM pbandi_t_quota_parte_doc_spesa
            WHERE pbandi_t_quota_parte_doc_spesa.id_documento_di_spesa =
                             pbandi_t_documento_di_spesa.id_documento_di_spesa
            AND pbandi_t_quota_parte_doc_spesa.id_progetto =
                                       pbandi_r_doc_spesa_progetto.id_progetto)
         AS totale_importo_quota_parte,
         pbandi_t_fornitore.codice_fiscale_fornitore  AS codice_fiscale_fornitore,
         pbandi_t_fornitore.denominazione_fornitore,
         pbandi_t_fornitore.cognome_fornitore,
         pbandi_t_fornitore.nome_fornitore,        
           '' AS totale_tutti_pagamenti,
           '' AS num_pagamenti_inviabili,
           '' as motivazione,
           PBANDI_R_DOC_SPESA_PROGETTO.ID_STATO_DOCUMENTO_SPESA
    FROM pbandi_t_documento_di_spesa,   
         pbandi_d_stato_documento_spesa,
         pbandi_d_tipo_documento_spesa,
         pbandi_r_soggetto_progetto,
         pbandi_r_doc_spesa_progetto,                  
         pbandi_t_fornitore
   WHERE 
     pbandi_t_documento_di_spesa.id_documento_di_spesa =
                             pbandi_r_doc_spesa_progetto.id_documento_di_spesa
     AND pbandi_t_documento_di_spesa.id_tipo_documento_spesa =
                         pbandi_d_tipo_documento_spesa.id_tipo_documento_spesa
     AND pbandi_r_doc_spesa_progetto.id_stato_documento_spesa =
                       pbandi_d_stato_documento_spesa.id_stato_documento_spesa
     AND pbandi_r_doc_spesa_progetto.id_progetto=
                                        pbandi_r_soggetto_progetto.id_progetto                                                                                 
     AND pbandi_t_documento_di_spesa.id_fornitore = pbandi_t_fornitore.id_fornitore(+)
     AND NVL (TRUNC (pbandi_r_soggetto_progetto.dt_fine_validita),
              TRUNC (SYSDATE + 1)
             ) > TRUNC (SYSDATE)
     AND NVL (TRUNC (pbandi_d_stato_documento_spesa.dt_fine_validita),
              TRUNC (SYSDATE + 1)
             ) > TRUNC (SYSDATE)
     AND NVL (TRUNC (pbandi_d_tipo_documento_spesa.dt_fine_validita),
              TRUNC (SYSDATE + 1)
             ) > TRUNC (SYSDATE)    
     AND pbandi_d_tipo_documento_spesa.DESC_BREVE_TIPO_DOC_SPESA = 'NC'     
GROUP BY pbandi_t_documento_di_spesa.dt_emissione_documento,
         pbandi_d_tipo_documento_spesa.desc_tipo_documento_spesa,
         pbandi_d_tipo_documento_spesa.desc_breve_tipo_doc_spesa,  
         pbandi_t_documento_di_spesa.id_documento_di_spesa,
         pbandi_t_documento_di_spesa.id_doc_riferimento,
         pbandi_t_documento_di_spesa.flag_elettronico,
         pbandi_t_documento_di_spesa.id_fornitore,
         pbandi_r_soggetto_progetto.id_soggetto,
         pbandi_t_documento_di_spesa.id_tipo_documento_spesa,
         pbandi_t_documento_di_spesa.numero_documento,
         pbandi_t_documento_di_spesa.importo_totale_documento,
         pbandi_r_doc_spesa_progetto.id_progetto,
         pbandi_r_doc_spesa_progetto.importo_rendicontazione,
         pbandi_r_doc_spesa_progetto.task,
         PBANDI_R_DOC_SPESA_PROGETTO.ID_STATO_DOCUMENTO_SPESA,
         pbandi_t_fornitore.codice_fiscale_fornitore,
		 pbandi_t_fornitore.denominazione_fornitore,
         pbandi_t_fornitore.cognome_fornitore,
         pbandi_t_fornitore.nome_fornitore,    
         pbandi_r_doc_spesa_progetto.tipo_invio