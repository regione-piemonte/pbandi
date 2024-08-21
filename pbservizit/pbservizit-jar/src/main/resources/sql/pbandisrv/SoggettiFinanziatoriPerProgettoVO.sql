/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT fonti.id_progetto,
  fonti.id_soggetto_finanziatore,
  dsf.desc_breve_sogg_finanziatore,
  dsf.desc_sogg_finanziatore,
  fonti.id_periodo,
  (select desc_periodo from pbandi_t_periodo where pbandi_t_periodo.id_periodo = fonti.id_periodo) as desc_periodo,
  (select desc_periodo_visualizzata from PBANDI_T_PERIODO where PBANDI_T_PERIODO.ID_PERIODO = FONTI.ID_PERIODO) as desc_periodo_visualizzata,
  NVL(fonti.perc_quota_sogg_finanziatore,rbsf.perc_quota_contributo_pub) perc_quota_sogg_finanziatore,
 (select RBSF.PERC_QUOTA_CONTRIBUTO_PUB from PBANDI_R_BANDO_SOGG_FINANZIAT 
         where ID_SOGGETTO_FINANZIATORE= FONTI.ID_SOGGETTO_FINANZIATORE
           and id_bando=rbli.id_bando) as perc_quota_default,
  fonti.imp_quota_sogg_finanziatore,
  fonti.estremi_provv,
  fonti.flag_lvlprj,
  fonti.id_utente_ins,
  fonti.id_utente_agg,
  fonti.progr_prog_sogg_finanziat,
  fonti.flag_economie,
  fonti.id_comune,
  fonti.id_delibera,
  fonti.id_norma,
  fonti.id_provincia,
  fonti.id_soggetto,
  fonti.note,
  dsf.flag_certificazione,
  dsf.flag_agevolato,
  dsf.ID_TIPO_SOGG_FINANZIAT,
  DESC_BREVE_TIPO_SOGG_FINANZIAT
FROM pbandi_r_bando_sogg_finanziat rbsf,
  pbandi_t_domanda dom,
  pbandi_d_soggetto_finanziatore dsf,
  pbandi_r_bando_linea_intervent rbli,
  pbandi_t_progetto prog,
  PBANDI_D_TIPO_SOGG_FINANZIAT,
  (SELECT NVL(rpsf.id_progetto, rbsf.id_progetto) id_progetto,
    nvl2(rpsf.progr_prog_sogg_finanziat, 'S', 'N') flag_lvlprj,
    NVL(rpsf.id_periodo, rbsf.id_periodo) id_periodo,
    rpsf.estremi_provv,
    NVL(rpsf.perc_quota_sogg_finanziatore,0) AS perc_quota_sogg_finanziatore,
    rpsf.id_utente_ins,
    rpsf.id_utente_agg,
    rpsf.progr_prog_sogg_finanziat,
    rpsf.flag_economie,
    rpsf.id_comune,
    rpsf.id_delibera,
    rpsf.id_norma,
    rpsf.id_provincia,
    rpsf.id_soggetto,
    rpsf.note,
    rpsf.imp_quota_sogg_finanziatore,
    NVL(rpsf.id_soggetto_finanziatore, rbsf.id_soggetto_finanziatore) id_soggetto_finanziatore
  FROM ( pbandi_r_prog_sogg_finanziat rpsf
  RIGHT OUTER JOIN
    (SELECT rbsf.*,
      tp.id_progetto,
      periodo.id_periodo
    FROM pbandi_r_bando_sogg_finanziat rbsf,
      pbandi_r_bando_linea_intervent rbl,
      pbandi_t_domanda td,
      pbandi_t_progetto tp,
      pbandi_r_bando_lin_tipo_period rbltp,
      pbandi_t_periodo periodo
    WHERE rbl.id_bando                     = rbsf.id_bando
    AND rbl.progr_bando_linea_intervento   = td.progr_bando_linea_intervento
    AND td.id_domanda                      = tp.id_domanda
    AND rbltp.progr_bando_linea_intervento = td.progr_bando_linea_intervento
    AND rbltp.id_tipo_periodo              = periodo.id_tipo_periodo
    AND (TRUNC(sysdate)                   >= NVL(TRUNC(periodo.DT_INIZIO_VALIDITA), TRUNC(sysdate) -1)
    AND TRUNC(sysdate)                     < NVL(TRUNC(periodo.DT_FINE_VALIDITA), TRUNC(sysdate)   +1))
    ) rbsf
  ON rbsf.id_soggetto_finanziatore = rpsf.id_soggetto_finanziatore
  AND rbsf.id_progetto             = rpsf.id_progetto
  AND rbsf.id_periodo              = rpsf.id_periodo )
  UNION
    (SELECT rpsf_periodi.id_progetto,
      nvl2(rpsf.progr_prog_sogg_finanziat, 'S', 'N') flag_lvlprj,
      rpsf_periodi.id_periodo,
      rpsf.estremi_provv,
      NVL(rpsf.perc_quota_sogg_finanziatore,0) AS perc_quota_sogg_finanziatore,
      rpsf.id_utente_ins,
      rpsf.id_utente_agg,
      rpsf.progr_prog_sogg_finanziat,
      rpsf.flag_economie,
      rpsf.id_comune,
      rpsf.id_delibera,
      rpsf.id_norma,
      rpsf.id_provincia,
      rpsf.id_soggetto,
      rpsf.note,
      rpsf.imp_quota_sogg_finanziatore,
      NVL(rpsf.id_soggetto_finanziatore,rpsf_periodi.id_soggetto_finanziatore)
    FROM pbandi_r_prog_sogg_finanziat rpsf
    FULL OUTER JOIN
      (SELECT periodo.id_periodo,
        rpsf.id_soggetto_finanziatore,
        rpsf.id_progetto
      FROM pbandi_t_domanda td,
        pbandi_r_prog_sogg_finanziat rpsf,
        pbandi_t_progetto tp,
        pbandi_r_bando_lin_tipo_period rbltp,
        pbandi_t_periodo periodo
      WHERE tp.id_domanda                 = td.id_domanda
      AND rpsf.id_progetto                = tp.id_progetto
      AND td.progr_bando_linea_intervento = rbltp.progr_bando_linea_intervento
      AND periodo.id_tipo_periodo         = rbltp.id_tipo_periodo
      AND (TRUNC(sysdate)                >= NVL(TRUNC(periodo.DT_INIZIO_VALIDITA), TRUNC(sysdate) -1)
      AND TRUNC(sysdate)                  < NVL(TRUNC(periodo.DT_FINE_VALIDITA), TRUNC(sysdate)   +1))
      ) rpsf_periodi
    ON rpsf.id_periodo                = rpsf_periodi.id_periodo
    AND rpsf.id_progetto              = rpsf_periodi.id_progetto
    AND rpsf.id_soggetto_finanziatore = rpsf_periodi.id_soggetto_finanziatore
    )
  ) fonti
WHERE fonti.id_soggetto_finanziatore = dsf.id_soggetto_finanziatore
AND prog.id_progetto                 = fonti.id_progetto
AND dom.id_domanda                   = prog.id_domanda
AND dom.progr_bando_linea_intervento = rbli.progr_bando_linea_intervento
AND rbsf.id_bando                    = rbli.id_bando
AND rbsf.id_soggetto_finanziatore    = dsf.id_soggetto_finanziatore
AND (TRUNC(sysdate)                 >= NVL(TRUNC(dsf.DT_INIZIO_VALIDITA), TRUNC(sysdate) -1)
AND TRUNC(sysdate)                   < NVL(TRUNC(dsf.DT_FINE_VALIDITA), TRUNC(sysdate)   +1))
AND dsf.ID_TIPO_SOGG_FINANZIAT       =PBANDI_D_TIPO_SOGG_FINANZIAT.ID_TIPO_SOGG_FINANZIAT
AND fonti.flag_lvlprj = 'S'
ORDER BY desc_periodo ASC, DESC_SOGG_FINANZIATORE ASC