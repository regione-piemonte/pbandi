/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT c.*,
    rpcl.ID_LINEA_DI_INTERVENTO AS ID_LINEA_INTERVENTO_PADRE
  FROM
    (SELECT DISTINCT d.*,
      NULL                      AS codice_progetto_visualizzato,
      NULL                      AS id_linea_di_intervento,
      NULL                      AS id_soggetto_beneficiario,
      pc.id_proposta_certificaz AS id_proposta_certificaz,
      dstat.desc_breve_stato_proposta_cert,
      CASE d.desc_breve_tipo_doc_index
        WHEN 'FPC'
        THEN d.desc_tipo_doc_index
          ||' ('
          || dstat.desc_stato_proposta_certif
          ||')'
        ELSE d.desc_tipo_doc_index
      END desc_tipo_doc_index_stato
    FROM
      (SELECT PBANDI_T_DOCUMENTO_INDEX.*,
        PBANDI_C_TIPO_DOCUMENTO_INDEX.desc_breve_tipo_doc_index,
        PBANDI_C_TIPO_DOCUMENTO_INDEX.desc_tipo_doc_index
      FROM PBANDI_T_DOCUMENTO_INDEX,
        PBANDI_C_ENTITA,
        PBANDI_C_TIPO_DOCUMENTO_INDEX
      WHERE PBANDI_T_DOCUMENTO_INDEX.id_entita             = PBANDI_C_ENTITA.id_entita
      AND PBANDI_C_ENTITA.nome_entita                     IN ('PBANDI_T_PROPOSTA_CERTIFICAZ')
      AND PBANDI_T_DOCUMENTO_INDEX.id_tipo_documento_index = PBANDI_C_TIPO_DOCUMENTO_INDEX.id_tipo_documento_index
      ) d,
      PBANDI_T_PROPOSTA_CERTIFICAZ pc,
      pbandi_d_stato_proposta_certif dstat
    WHERE d.id_target               = pc.id_proposta_certificaz
    AND pc.id_stato_proposta_certif = dstat.id_stato_proposta_certif
    UNION
    SELECT DISTINCT d.*,
      p.codice_visualizzato      AS codice_progetto_visualizzato,
      p.id_linea_di_intervento   AS id_linea_di_intervento,
      p.id_soggetto              AS id_soggetto_beneficiario,
      dpc.id_proposta_certificaz AS id_proposta_certificaz,
      dstat.desc_breve_stato_proposta_cert,
      CASE d.desc_breve_tipo_doc_index
        WHEN 'FPC'
        THEN d.desc_tipo_doc_index
          ||' ('
          || dstat.desc_stato_proposta_certif
          ||')'
        ELSE d.desc_tipo_doc_index
      END desc_tipo_doc_index_stato
    FROM
      (SELECT PBANDI_T_DOCUMENTO_INDEX.*,
        PBANDI_C_TIPO_DOCUMENTO_INDEX.desc_breve_tipo_doc_index,
        PBANDI_C_TIPO_DOCUMENTO_INDEX.desc_tipo_doc_index
      FROM PBANDI_T_DOCUMENTO_INDEX,
        PBANDI_C_ENTITA,
        PBANDI_C_TIPO_DOCUMENTO_INDEX
      WHERE PBANDI_T_DOCUMENTO_INDEX.id_entita             = PBANDI_C_ENTITA.id_entita
      AND PBANDI_C_ENTITA.nome_entita                     IN ('PBANDI_T_DETT_PROPOSTA_CERTIF')
      AND PBANDI_T_DOCUMENTO_INDEX.id_tipo_documento_index = PBANDI_C_TIPO_DOCUMENTO_INDEX.id_tipo_documento_index
      ) d,
      PBANDI_T_DETT_PROPOSTA_CERTIF dpc,
      PBANDI_T_PROPOSTA_CERTIFICAZ pc,
      pbandi_d_stato_proposta_certif dstat,
      ( SELECT DISTINCT p.codice_visualizzato AS codice_visualizzato,
        p.id_progetto                         AS id_progetto,
        l.id_linea_di_intervento              AS id_linea_di_intervento,
        sp.id_soggetto                        AS id_soggetto
      FROM PBANDI_T_PROGETTO p,
        PBANDI_R_BANDO_LINEA_INTERVENT bl,
        (SELECT PBANDI_R_LINEA_INTERV_MAPPING.id_linea_di_intervento_migrata AS id_linea_di_intervento
        FROM PBANDI_R_LINEA_INTERV_MAPPING
        UNION
        SELECT PBANDI_D_LINEA_DI_INTERVENTO.id_linea_di_intervento
        FROM PBANDI_D_LINEA_DI_INTERVENTO
        ) l,
        PBANDI_T_DOMANDA d,
        PBANDI_R_SOGGETTO_PROGETTO sp,
        PBANDI_D_TIPO_ANAGRAFICA ta,
        PBANDI_D_TIPO_BENEFICIARIO tb,
        PBANDI_T_DETT_PROPOSTA_CERTIF dpc
      WHERE bl.id_linea_di_intervento      = l.id_linea_di_intervento
      AND bl.progr_bando_linea_intervento  = d.progr_bando_linea_intervento
      AND p.id_domanda                     = d.id_domanda
      AND sp.id_progetto                   = p.id_progetto
      AND sp.id_tipo_anagrafica            = ta.id_tipo_anagrafica
      AND ta.desc_breve_tipo_anagrafica    = 'BENEFICIARIO'
      AND sp.id_tipo_beneficiario          = tb.id_tipo_beneficiario
      AND tb.desc_breve_tipo_beneficiario <> 'BEN-ASSOCIATO'
      AND dpc.id_progetto                  = p.id_progetto
      ) p
    WHERE p.id_progetto                = dpc.id_progetto
    AND d.id_target                    = dpc.id_dett_proposta_certif
    AND d.id_progetto                  = dpc.id_progetto
    AND dpc.id_proposta_certificaz     = pc.id_proposta_certificaz
    AND dstat.id_stato_proposta_certif = pc.id_stato_proposta_certif
    ) c,
    pbandi_r_proposta_certif_linea rpcl
  WHERE c.id_proposta_certificaz = rpcl.ID_PROPOSTA_CERTIFICAZ