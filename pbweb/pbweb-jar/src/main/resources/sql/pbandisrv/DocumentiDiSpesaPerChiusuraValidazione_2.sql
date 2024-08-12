select 
               renAll.ID_DICHIARAZIONE_SPESA,
							 renAll.ID_DOCUMENTO_DI_SPESA,
							 renAll.DESC_TIPO_DOCUMENTO_SPESA as desc_Tipo_Documento,
							 renAll.ID_TIPO_DOCUMENTO_SPESA,
							 renAll.NUMERO_DOCUMENTO,
							 renAll.DT_EMISSIONE_DOCUMENTO,
							 renAll.DESC_STATO_DOCUMENTO_SPESA as desc_Stato_Documento, 
							 renAll.IMPORTO_TOTALE_DOCUMENTO, 
							 renAll.RENDICONTABILE_TOTALE as importo_Rendicontabile, 
							 renAll.NOTE_CREDITO_TOTALE, 
							 renAll.NOTE_CREDITO_REN_TOTALE, 
        			 renAll.NUM_PAGAMENTI as numero_Pagamenti , 
							 RENALL.PAGATO_TOTALE AS TOTALE_IMPORTO_PAGAMENTI , 
							 /*renAll.NUM_PAGAMENTI_APERTI as numero_Pagamenti_Aperti,*/ 
    					 renAll.VALIDATO_TOTALE, 
							 dicOpen.NUM_DICHIARAZIONI_APERTE as numero_Dichiarazioni_Aperte 
					 from 
					 ( 
					     select 
                    docDich.ID_DICHIARAZIONE_SPESA,
					          docDich.ID_DOCUMENTO_DI_SPESA, 
					          docAll.NUMERO_DOCUMENTO, 
					          docAll.DT_EMISSIONE_DOCUMENTO, 
					          docAll.IMPORTO_TOTALE_DOCUMENTO, 
					          docAll.ID_TIPO_DOCUMENTO_SPESA, 
					          docAll.RENDICONTABILE_TOTALE,
                    docAll.NOTE_CREDITO_TOTALE,
                    docAll.NOTE_CREDITO_REN_TOTALE,
					          tipoDoc.DESC_TIPO_DOCUMENTO_SPESA, 
					          statoDoc.DESC_STATO_DOCUMENTO_SPESA, 
      					    docPag.PAGATO_TOTALE, 
			       		    DOCPAG.NUM_PAGAMENTI,
                    /*docPag.NUM_PAGAMENTI_APERTI,*/
                    docPag.VALIDATO_TOTALE
					     from (
                    select docAll.ID_DOCUMENTO_DI_SPESA,
                           docAll.NUMERO_DOCUMENTO, 
                           docAll.DT_EMISSIONE_DOCUMENTO, 
                           docAll.IMPORTO_TOTALE_DOCUMENTO, 
                           docAll.ID_TIPO_DOCUMENTO_SPESA,
                           docProAll.ID_STATO_DOCUMENTO_SPESA,
                           docProAll.RENDICONTABILE_TOTALE,
                           nc.NOTE_CREDITO_TOTALE, 
                           nc.NOTE_CREDITO_REN_TOTALE 
                      from pbandi_t_documento_di_spesa docAll,
                           (
                           select ID_DOCUMENTO_DI_SPESA,
                                  ID_STATO_DOCUMENTO_SPESA,
                                  sum(IMPORTO_RENDICONTAZIONE) RENDICONTABILE_TOTALE
                             from pbandi_r_doc_spesa_progetto
                           group by ID_DOCUMENTO_DI_SPESA, ID_STATO_DOCUMENTO_SPESA
                           ) docProAll,
                           (
                           select ID_DOC_RIFERIMENTO,
                                  sum(IMPORTO_TOTALE_DOCUMENTO) NOTE_CREDITO_TOTALE,
                                  sum(docProAll.RENDICONTABILE_TOTALE) NOTE_CREDITO_REN_TOTALE
                             from pbandi_t_documento_di_spesa d,
                                  (
                                  select ID_DOCUMENTO_DI_SPESA,
                                         sum(IMPORTO_RENDICONTAZIONE) RENDICONTABILE_TOTALE
                                    from pbandi_r_doc_spesa_progetto
                                  group by ID_DOCUMENTO_DI_SPESA
                                  ) docProAll
                            where d.ID_DOCUMENTO_DI_SPESA = docProAll.ID_DOCUMENTO_DI_SPESA
                           group by ID_DOC_RIFERIMENTO
                           ) nc
                     where docAll.ID_DOCUMENTO_DI_SPESA = docProAll.ID_DOCUMENTO_DI_SPESA
                       and docAll.ID_DOCUMENTO_DI_SPESA = nc.ID_DOC_RIFERIMENTO (+)
                    ) docAll,
                    (
                    select pagDic.ID_DICHIARAZIONE_SPESA,
                           pagDoc.ID_DOCUMENTO_DI_SPESA
                      from pbandi_r_pagamento_doc_spesa pagDoc, 
					                 pbandi_r_pagamento_dich_spesa pagDic
                     where pagDic.ID_PAGAMENTO = pagDoc.ID_PAGAMENTO
                     group by pagDic.ID_DICHIARAZIONE_SPESA,
                              pagDoc.ID_DOCUMENTO_DI_SPESA
                     ) docDich,
                    (
                    select pagDoc.ID_DOCUMENTO_DI_SPESA,
                           SUM(PAG.IMPORTO_PAGAMENTO) PAGATO_TOTALE, 
                           COUNT(PAG.ID_PAGAMENTO) NUM_PAGAMENTI,
                          /*sum(decode(statoPag.DESC_BREVE_STATO_VALIDAZ_SPESA, 'I', 1,
                                                                               'T', 1,
                                                0)) NUM_PAGAMENTI_APERTI,*/
                           sum(pagQtp.IMPORTO_VALIDATO) VALIDATO_TOTALE
                      from pbandi_t_pagamento pag,
                           pbandi_r_pagamento_doc_spesa pagDoc,
                           PBANDI_R_PAG_QUOT_PARTE_DOC_SP pagQtp
                           /*,pbandi_d_stato_validaz_spesa statoPag*/
                     where pagDoc.ID_PAGAMENTO = pagQtp.ID_PAGAMENTO (+)
                      /* and pag.ID_STATO_VALIDAZIONE_SPESA = statoPag.ID_STATO_VALIDAZIONE_SPESA */
                       and pagDoc.ID_PAGAMENTO = pag.ID_PAGAMENTO
                    group by pagDoc.ID_DOCUMENTO_DI_SPESA
                    ) docPag,
					          pbandi_d_tipo_documento_spesa tipoDoc, 
					          pbandi_d_stato_documento_spesa statoDoc
					     where docAll.ID_STATO_DOCUMENTO_SPESA = statoDoc.ID_STATO_DOCUMENTO_SPESA 
                 and docAll.ID_TIPO_DOCUMENTO_SPESA = tipoDoc.ID_TIPO_DOCUMENTO_SPESA
					       and docDich.ID_DOCUMENTO_DI_SPESA = docAll.ID_DOCUMENTO_DI_SPESA 
					       and docDich.ID_DOCUMENTO_DI_SPESA = docPag.ID_DOCUMENTO_DI_SPESA 
					 ) renAll, 
					 ( 
					 select rDocDic.ID_DOCUMENTO_DI_SPESA, 
					        count(dic.ID_DICHIARAZIONE_SPESA) NUM_DICHIARAZIONI_APERTE 
					   from (
                  select pagDoc.ID_DOCUMENTO_DI_SPESA,
                         pagDic.ID_DICHIARAZIONE_SPESA
                    from pbandi_r_pagamento_doc_spesa pagDoc, 
					               pbandi_r_pagamento_dich_spesa pagDic 
					         where pagDoc.ID_PAGAMENTO = pagDic.ID_PAGAMENTO 
                  group by pagDoc.ID_DOCUMENTO_DI_SPESA,
                           pagDic.ID_DICHIARAZIONE_SPESA
                  ) rDocDic,
					        pbandi_t_dichiarazione_spesa dic 
					  where rDocDic.ID_DICHIARAZIONE_SPESA= dic.ID_DICHIARAZIONE_SPESA 
					    and dic.DT_CHIUSURA_VALIDAZIONE is null
           group by rDocDic.ID_DOCUMENTO_DI_SPESA
					 ) DICOPEN 
WHERE RENALL.ID_DOCUMENTO_DI_SPESA = DICOPEN.ID_DOCUMENTO_DI_SPESA (+)
