/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT DISTINCT PBANDI_T_FORNITORE.codice_fiscale_fornitore                                 	AS CODICE_FISCALE_FORNITORE      	,
    PBANDI_T_FORNITORE.partita_iva_fornitore                                                    AS PARTITA_IVA_FORNITORE         	,
    PBANDI_T_DOCUMENTO_DI_SPESA.id_fornitore                                                    AS ID_FORNITORE                  	,
    DECODE(pbandi_t_fornitore.id_fornitore,NULL,NULL,pbandi_d_tipo_soggetto.desc_tipo_soggetto) AS DESC_TIPOLOGIA_FORNITORE      	,
    DECODE(pbandi_t_fornitore.id_fornitore,NULL,NULL,pbandi_d_tipo_soggetto.id_tipo_soggetto)   AS ID_TIPO_FORNITORE             	,
    PBANDI_T_FORNITORE.nome_fornitore                                                           AS NOME_FORNITORE                	,
    PBANDI_T_FORNITORE.cognome_fornitore                                                        AS COGNOME_FORNITORE             	,
    PBANDI_T_FORNITORE.denominazione_fornitore                                                  AS DENOMINAZIONE_FORNITORE       	,
    PBANDI_T_DOCUMENTO_DI_SPESA.id_documento_di_spesa                                           AS ID_DOCUMENTO_DI_SPESA         	,
    PBANDI_T_DOCUMENTO_DI_SPESA.dt_emissione_documento                                          AS DATA_DOCUMENTO_DI_SPESA          ,
    PBANDI_D_STATO_DOCUMENTO_SPESA.desc_stato_documento_spesa                                   AS DESC_STATO_DOCUMENTO_SPESA    	,
    PBANDI_D_TIPO_DOCUMENTO_SPESA.desc_tipo_documento_spesa                                     AS DESC_TIPO_DOCUMENTO_DI_SPESA 	,
    PBANDI_D_TIPO_DOCUMENTO_SPESA.desc_breve_tipo_doc_spesa                                     AS DESC_BREVE_TIPO_DOC_SPESA    	,
    PBANDI_T_DOCUMENTO_DI_SPESA.desc_documento                                                  AS DESCRIZIONE_DOCUMENTO_DI_SPESA   ,
    PBANDI_T_DOCUMENTO_DI_SPESA.destinazione_trasferta                                          AS DESTINAZIONE_TRASFERTA        	,
    PBANDI_T_DOCUMENTO_DI_SPESA.durata_trasferta                                                AS DURATA_TRASFERTA              	,
    PBANDI_T_DOCUMENTO_DI_SPESA.id_doc_riferimento                                              AS ID_DOC_RIFERIMENTO            	,
    rdsp.id_soggetto                                                                            AS ID_SOGGETTO                   	,
    PBANDI_T_DOCUMENTO_DI_SPESA.id_tipo_documento_spesa                                         AS ID_TIPO_DOCUMENTO_DI_SPESA       ,
    PBANDI_T_DOCUMENTO_DI_SPESA.id_tipo_oggetto_attivita                                        AS ID_TIPO_OGGETTO_ATTIVITA      	,
    PBANDI_T_DOCUMENTO_DI_SPESA.id_utente_agg                                                   AS ID_UTENTE_AGG                 	,
    PBANDI_T_DOCUMENTO_DI_SPESA.id_utente_ins                                                   AS ID_UTENTE_INS                 	,
    PBANDI_T_DOCUMENTO_DI_SPESA.imponibile                                                      AS IMPONIBILE                    	,
    PBANDI_T_DOCUMENTO_DI_SPESA.importo_iva                                                     AS IMPORTO_IVA                   	,
    PBANDI_T_DOCUMENTO_DI_SPESA.importo_iva_costo                                               AS IMPORTO_IVA_COSTO             	,
    PBANDI_T_DOCUMENTO_DI_SPESA.importo_totale_documento                                        AS IMPORTO_TOTALE_DOCUMENTO_IVATO	,
    PBANDI_T_DOCUMENTO_DI_SPESA.numero_documento                                                AS NUMERO_DOCUMENTO              	,
    NVL2(rdsp.ID_ENTE_GIURIDICO,
      (SELECT PBANDI_T_ENTE_GIURIDICO.DENOMINAZIONE_ENTE_GIURIDICO
         FROM PBANDI_T_ENTE_GIURIDICO
        WHERE rdsp.ID_ENTE_GIURIDICO                                              = PBANDI_T_ENTE_GIURIDICO.ID_ENTE_GIURIDICO
          AND NVL(TRUNC(PBANDI_T_ENTE_GIURIDICO.DT_FINE_VALIDITA), TRUNC(SYSDATE +1)) > TRUNC(SYSDATE)
      ),
      (SELECT PBANDI_T_PERSONA_FISICA.COGNOME
        ||' '
        || PBANDI_T_PERSONA_FISICA.NOME
         FROM PBANDI_T_PERSONA_FISICA
        WHERE rdsp.ID_PERSONA_FISICA                                              = PBANDI_T_PERSONA_FISICA.ID_PERSONA_FISICA
          AND NVL(TRUNC(PBANDI_T_PERSONA_FISICA.DT_FINE_VALIDITA), TRUNC(SYSDATE +1)) > TRUNC(SYSDATE)
      ) 
    )                          																  AS PARTNER               		,
    rdsp.id_progetto             															  AS ID_PROGETTO           		,
   (select codice_progetto from pbandi_t_progetto where id_progetto = rdsp.id_progetto)       AS CODICE_PROGETTO       		,
    rdsp.task                    															  AS TASK                  		,
    rdsp.flag_gest_in_progetto   															  AS FLAG_GEST_IN_PROGETTO 		,
    rdsp.importo_totale_validato 															  AS IMPORTO_TOTALE_VALIDATO
FROM PBANDI_T_DOCUMENTO_DI_SPESA 	  ,
     PBANDI_D_TIPO_DOCUMENTO_SPESA     ,
     PBANDI_D_TIPO_SOGGETTO            ,
     PBANDI_T_FORNITORE                ,
     PBANDI_D_STATO_DOCUMENTO_SPESA    ,
     ( SELECT DISTINCT rsp1.id_progetto,
              docProgBen.id_documento_di_spesa,
              docProgBen.id_soggetto          ,
              docProgBen.id_ente_giuridico    ,
              docProgBen.id_persona_fisica    ,
              DOCPROGBEN.TASK                 ,
              docProgBen.ID_STATO_DOCUMENTO_SPESA,
              CASE
                WHEN NOT EXISTS
                  (SELECT 'x'
                     FROM pbandi_r_doc_spesa_progetto r1
                    WHERE r1.id_progetto       = rsp1.id_progetto
                  AND r1.id_documento_di_spesa = docProgBen.id_documento_di_spesa
                  )
                THEN 'N'
                ELSE 'S'
              END flag_gest_in_progetto,
              CASE
                WHEN NOT EXISTS
                  (SELECT 'd'
                     FROM pbandi_r_doc_spesa_progetto r1
                    WHERE r1.id_progetto       = rsp1.id_progetto
                  AND r1.id_documento_di_spesa = docProgBen.id_documento_di_spesa
                  )
                THEN NULL
                ELSE NVL(
                  ( SELECT SUM (pagquotdocs.importo_validato) AS importo_totale_validato
                    FROM PBANDI_R_PAGAMENTO_DOC_SPESA pdocs,
                       PBANDI_T_QUOTA_PARTE_DOC_SPESA qpDocs   ,
                       PBANDI_R_PAG_QUOT_PARTE_DOC_SP pagQuotDocs
                    WHERE qpdocs.id_documento_di_spesa     = pdocs.id_documento_di_spesa
                    AND qpdocs.id_progetto                   = docProgBen.id_progetto
                    AND pagquotdocs.id_quota_parte_doc_spesa = qpdocs.id_quota_parte_doc_spesa
                    AND pagquotdocs.id_pagamento             = pdocs.id_pagamento
                    AND pdocs.id_documento_di_spesa (+)      = docProgBen.id_documento_di_spesa
                  GROUP BY docProgBen.id_progetto,
                    docProgBen.id_documento_di_spesa
                  ), 0)
              END importo_totale_validato
      FROM
        ( SELECT rdsp.id_documento_di_spesa,
                 rdsp.id_progetto                ,
                 rdsp.task                       ,
                 rsp.id_soggetto                 ,
                 RSP.ID_ENTE_GIURIDICO           ,
                 RSP.ID_PERSONA_FISICA           ,
                 RDSP.ID_STATO_DOCUMENTO_SPESA
            FROM pbandi_r_doc_spesa_progetto rdsp,
                 pbandi_r_soggetto_progetto rsp
           WHERE rdsp.id_progetto                                = rsp.id_progetto
             AND rsp.id_tipo_beneficiario                           <> 4
             AND NVL(TRUNC(rsp.DT_FINE_VALIDITA), TRUNC(sysdate +1)) > TRUNC(sysdate)
        ) docProgBen,
        pbandi_r_soggetto_progetto rsp1
      WHERE docProgBen.id_soggetto = rsp1.id_soggetto
    ) rdsp
WHERE rdsp.ID_STATO_DOCUMENTO_SPESA = PBANDI_D_STATO_DOCUMENTO_SPESA.ID_STATO_DOCUMENTO_SPESA
  AND PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA        = rdsp.ID_DOCUMENTO_DI_SPESA
  AND PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_DOCUMENTO_SPESA      = PBANDI_D_TIPO_DOCUMENTO_SPESA.ID_TIPO_DOCUMENTO_SPESA
  AND PBANDI_T_DOCUMENTO_DI_SPESA.ID_FORNITORE                 = PBANDI_T_FORNITORE.ID_FORNITORE(+)
  AND ( pbandi_t_documento_di_spesa.id_fornitore              IS NULL
        OR pbandi_d_tipo_soggetto.id_tipo_soggetto             = pbandi_t_fornitore.id_tipo_soggetto )
  AND NVL(TRUNC(PBANDI_D_TIPO_SOGGETTO.DT_FINE_VALIDITA), TRUNC(sysdate        +1)) > TRUNC(sysdate)
  AND NVL(TRUNC(PBANDI_D_STATO_DOCUMENTO_SPESA.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
  AND NVL(TRUNC(PBANDI_D_TIPO_DOCUMENTO_SPESA.DT_FINE_VALIDITA), TRUNC(SYSDATE +1)) > TRUNC(SYSDATE)