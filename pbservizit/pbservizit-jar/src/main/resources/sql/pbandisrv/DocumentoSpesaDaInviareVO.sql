/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/* cerca tutti i documenti non note di credito */
SELECT distinct
  CASE
	WHEN  EXISTS
	 	(select 'x' 
          from PBANDI_T_FILE_ENTITA     
          where PBANDI_T_FILE_ENTITA.ID_TARGET =PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA 
                        and PBANDI_T_FILE_ENTITA.ID_ENTITA=(select ID_ENTITA from PBANDI_C_ENTITA where NOME_ENTITA='PBANDI_T_DOCUMENTO_DI_SPESA')
                        and   doc_prj_totale_quote.id_progetto=PBANDI_T_FILE_ENTITA.id_progetto 
	  	)
	THEN 'S' 
	ELSE 'N'
  END  flag_allegati,
  pbandi_t_documento_di_spesa.dt_emissione_documento      AS data_documento_di_spesa,
  doc_prj_totale_quote.tipo_invio                         AS tipo_invio,
  pbandi_t_documento_di_spesa.flag_elettronico            AS flag_elettronico,
  pbandi_d_tipo_documento_spesa.desc_tipo_documento_spesa AS desc_tipo_documento_di_spesa,
  pbandi_d_tipo_documento_spesa.desc_breve_tipo_doc_spesa AS desc_breve_tipo_doc_spesa,
  pbandi_t_documento_di_spesa.id_documento_di_spesa       AS id_documento_di_spesa,
  pbandi_t_documento_di_spesa.id_doc_riferimento          AS id_doc_di_riferimento,
  pbandi_t_documento_di_spesa.id_fornitore                AS id_fornitore,
  pbandi_r_soggetto_progetto.id_soggetto                  AS id_soggetto,
  pbandi_t_documento_di_spesa.id_tipo_documento_spesa     AS id_tipo_documento_di_spesa,
  pbandi_t_documento_di_spesa.numero_documento            AS numero_documento_di_spesa,
  pbandi_t_documento_di_spesa.importo_totale_documento    AS importo_totale_documento,
  doc_prj_totale_quote.id_progetto                 AS id_progetto,
  doc_prj_totale_quote.importo_rendicontazione     AS importo_rendicontazione,
  doc_prj_totale_quote.task                        AS task,
  tot_pag_documento.totale_importo_pagamenti              AS totale_importo_pagamenti,
  (
    SELECT
      SUM (tds1.importo_totale_documento)
    FROM
      pbandi_t_documento_di_spesa tds1
    WHERE
      tds1.id_doc_riferimento =
      pbandi_t_documento_di_spesa.id_documento_di_spesa
  )                                                       AS totale_note_credito,
  doc_prj_totale_quote.totale_importo_quota_parte,
  pbandi_t_fornitore.codice_fiscale_fornitore             AS codice_fiscale_fornitore,
  pbandi_t_fornitore.nome_fornitore             		  AS nome_fornitore,
  pbandi_t_fornitore.cognome_fornitore            		  AS cognome_fornitore,
  pbandi_t_fornitore.denominazione_fornitore              AS denominazione_fornitore,
  nvl(tot_pag_documento.num_pagamenti_inviabili,0)        AS num_pagamenti_inviabili,
  ''                                                      AS motivazione,
  doc_prj_totale_quote.ID_STATO_DOCUMENTO_SPESA    AS ID_STATO_DOCUMENTO_SPESA
FROM pbandi_t_documento_di_spesa 
     left outer join pbandi_t_fornitore on pbandi_t_documento_di_spesa.id_fornitore = pbandi_t_fornitore.id_fornitore
     left outer join (
        select PAGDOC.ID_DOCUMENTO_DI_SPESA,
               sum(PAG.IMPORTO_PAGAMENTO) as totale_importo_pagamenti,
               pag_inviabili_documento.num_pagamenti_inviabili
        from PBANDI_T_PAGAMENTO pag,
             PBANDI_R_PAGAMENTO_DOC_SPESA pagdoc 
             left outer join
             (
                select PAGDOC.ID_DOCUMENTO_DI_SPESA,
                       count(*) as num_pagamenti_inviabili
                from   pbandi_r_pagamento_doc_spesa pagdoc
                where  pagdoc.id_pagamento not in 
                      (  select pagdich.id_pagamento 
                          from pbandi_r_pagamento_dich_spesa pagdich 
                         where pagdoc.id_pagamento = pagdich.id_pagamento
                      )
                group by PAGDOC.ID_DOCUMENTO_DI_SPESA
             ) pag_inviabili_documento
             on pagdoc.id_documento_di_spesa = pag_inviabili_documento.id_documento_di_spesa
        where PAG.ID_PAGAMENTO = PAGDOC.ID_PAGAMENTO
        group by PAGDOC.ID_DOCUMENTO_DI_SPESA,
                 pag_inviabili_documento.num_pagamenti_inviabili
     ) tot_pag_documento 
     on pbandi_t_documento_di_spesa.ID_DOCUMENTO_DI_SPESA = tot_pag_documento.ID_DOCUMENTO_DI_SPESA, 
     pbandi_d_stato_documento_spesa,
     pbandi_d_tipo_documento_spesa,
     pbandi_r_soggetto_progetto,
     ( select docprj.id_documento_di_spesa,
              docprj.id_progetto,
              docprj.id_stato_documento_spesa,
              docprj.id_stato_documento_spesa_valid,
              docprj.importo_rendicontazione,
              docprj.note_validazione,
              docprj.task,
              docprj.tipo_invio,
              sum(quodoc.importo_quota_parte_doc_spesa) as totale_importo_quota_parte
       from pbandi_r_doc_spesa_progetto docprj
        left outer join 
        pbandi_t_quota_parte_doc_spesa quodoc
         on quodoc.id_documento_di_spesa = docprj.id_documento_di_spesa
        and quodoc.id_progetto = docprj.id_progetto
        group by docprj.id_documento_di_spesa,
              docprj.id_progetto,
              docprj.id_stato_documento_spesa,
              docprj.id_stato_documento_spesa_valid,
              docprj.importo_rendicontazione,
              docprj.note_validazione,
              docprj.task,
              docprj.tipo_invio
     ) doc_prj_totale_quote
  WHERE pbandi_t_documento_di_spesa.id_documento_di_spesa = doc_prj_totale_quote.id_documento_di_spesa
  AND pbandi_t_documento_di_spesa.id_tipo_documento_spesa = pbandi_d_tipo_documento_spesa.id_tipo_documento_spesa
  AND doc_prj_totale_quote.id_stato_documento_spesa = pbandi_d_stato_documento_spesa.id_stato_documento_spesa
  AND doc_prj_totale_quote.id_progetto = pbandi_r_soggetto_progetto.id_progetto
  AND NVL (TRUNC (pbandi_r_soggetto_progetto.dt_fine_validita), TRUNC (SYSDATE +  1) ) > TRUNC (SYSDATE)
  AND NVL (TRUNC (pbandi_d_stato_documento_spesa.dt_fine_validita), TRUNC (  SYSDATE + 1) ) > TRUNC (SYSDATE)
  AND NVL (TRUNC (pbandi_d_tipo_documento_spesa.dt_fine_validita), TRUNC (SYSDATE + 1) ) > TRUNC (SYSDATE)
  AND PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_BREVE_TIPO_DOC_SPESA <> 'NC'

