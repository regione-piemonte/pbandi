/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

with tqp as (SELECT tqp.id_quota_parte_doc_spesa,
                    tqp.id_rigo_conto_economico,
                    tqp.id_documento_di_spesa,
                    tqp.importo_quota_parte_doc_spesa,
                    rce.id_voce_di_entrata,
                    SUM(rpqp.importo_quietanzato) AS importo_quietanzato,
                    SUM(rpqp.importo_validato)    AS importo_validato
             FROM pbandi_t_quota_parte_doc_spesa tqp
                      LEFT JOIN
                  pbandi_r_pag_quot_parte_doc_sp rpqp ON tqp.id_quota_parte_doc_spesa = rpqp.id_quota_parte_doc_spesa
                      LEFT JOIN
                  pbandi_t_rigo_conto_economico rce ON tqp.id_rigo_conto_economico = rce.id_rigo_conto_economico
             GROUP BY tqp.id_quota_parte_doc_spesa,
                      tqp.id_rigo_conto_economico,
                      tqp.importo_quota_parte_doc_spesa,
                      tqp.id_documento_di_spesa,
                      rce.id_voce_di_entrata),

     tce as (select tce.*,
                    rbl.id_bando
             from pbandi_t_conto_economico tce,
                  pbandi_t_domanda td,
                  pbandi_r_bando_linea_intervent rbl
             where tce.id_domanda = td.id_domanda
               and td.progr_bando_linea_intervento = rbl.progr_bando_linea_intervento),
     trce as (select trce.*
              from pbandi_t_rigo_conto_economico trce
              where trce.dt_fine_validita is null),
     rbvs as (select tce.*,
                     dvs.descrizione,
                     dvs.descrizione_breve,
                     rbvs.id_voce_di_entrata,
                     dvs.flag_edit
              from pbandi_d_voce_di_entrata dvs,
                   tce,
                   pbandi_r_bando_voce_entrata rbvs
              where dvs.id_voce_di_entrata = rbvs.id_voce_di_entrata
                and tce.id_bando = rbvs.id_bando),
     righe as (select rbvs.*,
                      trce.id_rigo_conto_economico,
                      trce.importo_spesa                 as importo_richiesto,
                      trce.importo_ammesso_finanziamento as importo_ammesso,
                      trce.completamento
               from rbvs
                        left join trce on trce.id_voce_di_entrata = rbvs.id_voce_di_entrata and
                                          trce.id_conto_economico = rbvs.id_conto_economico
               order by rbvs.id_conto_economico,
                        rbvs.descrizione),

     master as (SELECT *
                FROM (SELECT ce.id_conto_economico
                      FROM PBANDI_T_CONTO_ECONOMICO ce
                               JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ce.ID_DOMANDA
                               JOIN PBANDI_T_PROGETTO ptp ON
                              ptp.ID_DOMANDA = ptd.ID_DOMANDA
                               JOIN PBANDI_D_STATO_CONTO_ECONOMICO pdsce
                                    ON PDSCE.ID_STATO_CONTO_ECONOMICO = ce.ID_STATO_CONTO_ECONOMICO
                               JOIN PBANDI_D_TIPOLOGIA_CONTO_ECON tce
                                    ON tce.ID_TIPOLOGIA_CONTO_ECONOMICO = pdsce.ID_TIPOLOGIA_CONTO_ECONOMICO
                      WHERE ptp.ID_PROGETTO = :idProgetto
                        AND tce.DESC_BREVE_TIPOLOGIA_CONTO_ECO = 'MASTER'
                      ORDER BY ce.DT_FINE_VALIDITA DESC)
                WHERE ROWNUM <= 1),
     main as (SELECT *
              FROM (SELECT ce.id_conto_economico
                    FROM PBANDI_T_CONTO_ECONOMICO ce
                             JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ce.ID_DOMANDA
                             JOIN PBANDI_T_PROGETTO ptp ON
                            ptp.ID_DOMANDA = ptd.ID_DOMANDA
                             JOIN PBANDI_D_STATO_CONTO_ECONOMICO pdsce
                                  ON PDSCE.ID_STATO_CONTO_ECONOMICO = ce.ID_STATO_CONTO_ECONOMICO
                             JOIN PBANDI_D_TIPOLOGIA_CONTO_ECON tce
                                  ON tce.ID_TIPOLOGIA_CONTO_ECONOMICO = pdsce.ID_TIPOLOGIA_CONTO_ECONOMICO
                    WHERE ptp.ID_PROGETTO = :idProgetto
                      AND tce.DESC_BREVE_TIPOLOGIA_CONTO_ECO = 'MAIN'
                    ORDER BY ce.DT_FINE_VALIDITA DESC)
              WHERE ROWNUM <= 1),
     copyist as (SELECT *
                 FROM (SELECT ce.id_conto_economico
                       FROM PBANDI_T_CONTO_ECONOMICO ce
                                JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ce.ID_DOMANDA
                                JOIN PBANDI_T_PROGETTO ptp ON
                               ptp.ID_DOMANDA = ptd.ID_DOMANDA
                                JOIN PBANDI_D_STATO_CONTO_ECONOMICO pdsce
                                     ON PDSCE.ID_STATO_CONTO_ECONOMICO = ce.ID_STATO_CONTO_ECONOMICO
                                JOIN PBANDI_D_TIPOLOGIA_CONTO_ECON tce
                                     ON tce.ID_TIPOLOGIA_CONTO_ECONOMICO = pdsce.ID_TIPOLOGIA_CONTO_ECONOMICO
                       WHERE ptp.ID_PROGETTO = :idProgetto
                         AND tce.DESC_BREVE_TIPOLOGIA_CONTO_ECO = 'COPY_IST'
                       ORDER BY ce.DT_FINE_VALIDITA DESC)
                 WHERE ROWNUM <= 1)
SELECT
    righeMaster.ID_CONTO_ECONOMICO AS id_Conto_Economico_Master,
    righeCopyIst.ID_CONTO_ECONOMICO as id_Conto_Economico_Copy_Ist,
    righeMaster.importo_richiesto as importo_Nuova_Proposta,
    righeMaster.importo_ammesso as importo_Ultimo_Ammesso,
    righeCopyIst.importo_richiesto as importo_Richiesto_Copy_Ist,
    righeCopyIst.importo_ammesso as importo_Ammesso_Rimodulazione,
    righeMain.*
FROM righe righeMain
         LEFT OUTER JOIN righe righeMaster ON
        (righeMaster.ID_CONTO_ECONOMICO IN (SELECT id_conto_economico FROM master) OR righeMaster.ID_CONTO_ECONOMICO IS NULL) AND
        righeMaster.ID_Voce_Di_Entrata = righeMain.ID_Voce_Di_Entrata AND
        ((righeMaster.FLAG_EDIT IS NOT NULL AND righeMaster.FLAG_EDIT = righeMain.FLAG_EDIT) OR righeMaster.FLAG_EDIT IS NULL) AND
        righeMaster.DESCRIZIONE = righeMain.DESCRIZIONE AND
        ((righeMaster.COMPLETAMENTO IS NOT NULL AND righeMaster.COMPLETAMENTO = righeMain.COMPLETAMENTO) OR righeMaster.COMPLETAMENTO IS NULL)
         LEFT OUTER JOIN righe righeCopyIst ON
        (righeCopyIst.ID_CONTO_ECONOMICO IN (SELECT id_conto_economico FROM copyist) OR righeCopyIst.ID_CONTO_ECONOMICO IS NULL) AND
        ((righeCopyIst.ID_Voce_Di_Entrata IS NOT NULL AND righeCopyIst.ID_Voce_Di_Entrata = righeMain.ID_Voce_Di_Entrata) OR righeCopyIst.ID_Voce_Di_Entrata IS NULL) AND
        ((righeCopyIst.FLAG_EDIT IS NOT NULL AND righeCopyIst.FLAG_EDIT = righeMain.FLAG_EDIT) OR righeCopyIst.FLAG_EDIT IS NULL) AND
        ((righeCopyIst.DESCRIZIONE IS NOT NULL AND righeCopyIst.DESCRIZIONE = righeMain.DESCRIZIONE) OR righeCopyIst.DESCRIZIONE IS NULL) AND
        ((righeCopyIst.COMPLETAMENTO IS NOT NULL AND righeCopyIst.COMPLETAMENTO = righeMain.COMPLETAMENTO) OR righeCopyIst.COMPLETAMENTO IS NULL)
WHERE righeMain.ID_CONTO_ECONOMICO IN (SELECT id_conto_economico FROM main)




