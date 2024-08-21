/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT p.id_progetto ,
  dpm.flag_richiesta_cup ,
  dpm.flag_invio_monit ,
  dpm.FLAG_PPP,
  dpm.FLAG_STRATEGICO,
  p.dt_firma_accordo,
  p.dt_completamento_valutazione,
  --set_ateco_dimterr.id_soggetto_beneficiario ,
  rsp.id_soggetto AS id_soggetto_beneficiario ,
  set_ateco_dimterr.progr_soggetto_progetto ,
  set_ateco_dimterr.progr_soggetto_progetto_sede ,
  p.cup ,
  d.numero_domanda ,
  d.id_domanda ,
  p.titolo_progetto ,
  p.codice_visualizzato ,
  p.codice_progetto ,
  p.dt_comitato ,
  p.dt_concessione ,
  set_ateco_dimterr.id_sede_intervento,
  /* settore attivita */
  set_ateco_dimterr.id_settore_attivita ,
  set_ateco_dimterr.desc_settore,
  /* attivita ateco */
  set_ateco_dimterr.id_attivita_ateco ,
  set_ateco_dimterr.desc_ateco,
  /* tipo procedura orig - PBANDI-2734 */
  p.id_tipo_procedura_orig ,
  nvl2( p.id_tipo_procedura_orig,
  (SELECT desc_tipo_procedura_orig
  /* tipo operazione */
  FROM pbandi_d_tipo_procedura_orig 
  WHERE id_tipo_procedura_orig = p.id_tipo_procedura_orig
  --AND NVL(TRUNC(dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
  ), NULL ) AS desc_tipo_procedura_orig
  ,
  p.id_tipo_operazione ,
  nvl2( p.id_tipo_operazione,
  (SELECT desc_tipo_operazione
  FROM pbandi_d_tipo_operazione 
  WHERE id_tipo_operazione                            = p.id_tipo_operazione
  --AND NVL(TRUNC(dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
  ), NULL ) AS desc_tipo_operazione,
  /* priorita qsn */
  obgen_obspec_prior_qsn.id_priorita_qsn,
  obgen_obspec_prior_qsn.desc_priorita_qsn,
  /* obiettivo generale qsn */
  obgen_obspec_prior_qsn.id_obiettivo_gen_qsn ,
  obgen_obspec_prior_qsn.desc_obiettivo_generale_qsn,
  /* obiettivo specifico qsn */
  p.id_obiettivo_specif_qsn ,
  obgen_obspec_prior_qsn.desc_obiettivo_specifico_qsn,
  /* strumento attuativo */
  p.id_strumento_attuativo ,
  nvl2( id_strumento_attuativo,
  (SELECT desc_strumento_attuativo
  FROM pbandi_d_strumento_attuativo 
  WHERE id_strumento_attuativo                        = p.id_strumento_attuativo
  --AND NVL(TRUNC(dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
  ), NULL ) AS desc_strumento_attuativo,
  /* settore cpt */
  p.id_settore_cpt ,
  nvl2( p.id_settore_cpt,
  (SELECT desc_settore_cpt
  FROM pbandi_d_settore_cpt 
  WHERE id_settore_cpt                                = p.id_settore_cpt
  --AND NVL(TRUNC(dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
  ), NULL ) AS desc_settore_cpt,
  /* tema prioritario */
  p.id_tema_prioritario ,
  nvl2( p.id_tema_prioritario,
  (SELECT desc_tema_prioritario
  FROM pbandi_d_tema_prioritario 
  WHERE id_tema_prioritario                           = p.id_tema_prioritario
  --AND NVL(TRUNC(dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
  ), NULL ) AS desc_tema_prioritario,
  /* indicatore risultato programma */
  p.id_ind_risultato_program ,
  nvl2( p.id_ind_risultato_program,
  (SELECT desc_ind_risultato_programma
  FROM pbandi_d_ind_risultato_program 
  WHERE id_ind_risultato_program                      = p.id_ind_risultato_program
  --AND NVL(TRUNC(dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
  ), NULL ) AS desc_ind_risultato_programma,
  /* indicatore qsn */
  p.id_indicatore_qsn ,
  nvl2( p.id_indicatore_qsn,
  (SELECT desc_indicatore_qsn
  FROM pbandi_d_indicatore_qsn 
  WHERE id_indicatore_qsn                             = p.id_indicatore_qsn
  --AND NVL(TRUNC(dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
  ), NULL ) AS desc_indicatore_qsn,
  /* tipo aiuto */
  ta.id_tipo_aiuto ,
  ta.desc_tipo_aiuto,
  /* tipo strumento programmazione */
  p.id_tipo_strumento_progr ,
  nvl2( p.id_tipo_strumento_progr,
  (SELECT desc_strumento
  FROM pbandi_d_tipo_strumento_progr 
  WHERE id_tipo_strumento_progr                       = p.id_tipo_strumento_progr
  --AND NVL(TRUNC(dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
  ), NULL ) AS desc_tipo_strumento,
  /* dimensione territoriale */
  set_ateco_dimterr.id_dimensione_territor ,
  set_ateco_dimterr.desc_dimensione_territoriale,
  /* progetto complesso */
  p.id_progetto_complesso ,
  nvl2( p.id_progetto_complesso,
  (SELECT desc_progetto_complesso
  FROM pbandi_d_progetto_complesso
  WHERE id_progetto_complesso                         = p.id_progetto_complesso
  --AND NVL(TRUNC(dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
  ), NULL ) AS desc_progetto_complesso,
  /* grande progetto */
  p.id_grande_progetto ,
  nvl2( p.id_grande_progetto,
  (SELECT desc_grande_progetto
  FROM pbandi_d_grande_progetto
  WHERE id_grande_progetto                         = p.id_grande_progetto
  --AND NVL(TRUNC(dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
  ), NULL ) AS desc_grande_progetto,
  /* Obiettivo Tematico */
  p.id_obiettivo_tem ,
  nvl2( p.id_obiettivo_tem ,
  (SELECT descr_obiettivo_tem
  FROM pbandi_d_obiettivo_tem 
  WHERE id_obiettivo_tem                         = p.id_obiettivo_tem 
  --AND NVL(TRUNC(dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
  ), NULL ) AS desc_obiettivo_tem,
    /* Obiettivo Tematico */
  p.ID_CLASSIFICAZIONE_RA ,
  nvl2( p.id_CLASSIFICAZIONE_RA ,
  (SELECT descr_CLASSIFICAZIONE_RA
  FROM pbandi_d_CLASSIFICAZIONE_RA 
  WHERE id_CLASSIFICAZIONE_RA                        = p.id_CLASSIFICAZIONE_RA 
  --AND NVL(TRUNC(dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
  ), NULL ) AS desc_CLASSIFICAZIONE_RA,
  /* settore cipe */
  cipe.id_settore_cipe ,
  cipe.desc_settore_cipe,
  /* sotto settore cipe */ 
  cipe.id_sotto_settore_cipe ,
  cipe.desc_sotto_settore_cipe,
  /* categoria cipe */
  p.id_categoria_cipe ,
  cipe.desc_categoria_cipe,
  /* natura cipe */
  natura_cipe.id_natura_cipe ,
  natura_cipe.desc_natura_cipe,
  /* tipologia cipe */
  p.id_tipologia_cipe ,
  natura_cipe.desc_tipologia_cipe,
  /* flag cardine */
  dpm.flag_cardine,
  /* flag generatore entrate */
  dpm.flag_generatore_entrate,
  /* flag legge obiettivo */
  dpm.flag_leg_obiettivo,
  /* flag altro fondo */
  dpm.flag_altro_fondo,
  /* importo entrate*/
  dpm.importo_entrate
  ,
  CASE
    WHEN dpm.stato_fas = 1
    OR dpm.stato_fs    = 1
    THEN 'S'
    ELSE 'N'
  END stato_progetto_programma,
  /* codice progetto cipe */
  dpm.codice_progetto_cipe
FROM pbandi_t_progetto p,
  pbandi_t_domanda d,
  pbandi_d_tipo_aiuto ta,
  pbandi_t_dati_progetto_monit dpm,
  pbandi_r_soggetto_progetto rsp,
  ( SELECT DISTINCT rsp.id_progetto ,
    rsp.id_soggetto AS id_soggetto_beneficiario ,
    rsp.progr_soggetto_progetto ,
    rsps.progr_soggetto_progetto_sede ,
    first_value(sede.id_sede) over (partition BY rsp.id_progetto order by sede.dt_inizio_validita DESC) AS id_sede_intervento ,
    sede.id_attivita_ateco ,
    aa.desc_ateco ,
    aa.id_settore_attivita ,
    sa.desc_settore ,
    dt.id_dimensione_territor ,
    dt.desc_dimensione_territoriale
  FROM pbandi_r_sogg_progetto_sede rsps ,
    pbandi_r_soggetto_progetto rsp ,
    pbandi_t_sede sede ,
    pbandi_d_attivita_ateco aa ,
    pbandi_d_settore_attivita sa ,
    pbandi_d_dimensione_territor dt
  WHERE rsp.progr_soggetto_progetto = rsps.progr_soggetto_progetto
  AND rsps.id_tipo_sede             =
    (SELECT id_tipo_sede
    FROM pbandi_d_tipo_sede
    WHERE desc_breve_tipo_sede = 'SI'
    )
  AND sede.id_sede               = rsps.id_sede
  AND sede.id_attivita_ateco     = aa.id_attivita_ateco (+)
  AND sa.id_settore_attivita (+) = aa.id_settore_attivita
  AND rsp.ID_TIPO_ANAGRAFICA     =
    (SELECT dta.id_tipo_anagrafica
    FROM pbandi_d_tipo_anagrafica dta
    WHERE dta.desc_breve_tipo_anagrafica = 'BENEFICIARIO'
    )
  AND NVL(rsp.id_tipo_beneficiario, '-1') <>
    (SELECT dtb.id_tipo_beneficiario
    FROM pbandi_d_tipo_beneficiario dtb
    WHERE dtb.desc_breve_tipo_beneficiario = 'BEN-ASSOCIATO'
    )
  AND sede.id_dimensione_territor = dt.id_dimensione_territor (+)
    /* vengono considerate valide le sedi con dt_fine_validita non valorizzata */
  AND sede.dt_fine_validita                              IS NULL
  AND NVL(TRUNC(rsp.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
  AND NVL(TRUNC(aa.dt_fine_validita), TRUNC(sysdate  +1)) > TRUNC(sysdate)
  AND NVL(TRUNC(sa.dt_fine_validita), TRUNC(sysdate  +1)) > TRUNC(sysdate)
  AND NVL(TRUNC(dt.dt_fine_validita), TRUNC(sysdate  +1)) > TRUNC(sysdate)
  ) set_ateco_dimterr,
  ( SELECT DISTINCT osqsn.id_obiettivo_specif_qsn ,
    osqsn.desc_obiettivo_specifico_qsn ,
    ogQsn.id_obiettivo_gen_qsn ,
    ogqsn.desc_obiettivo_generale_qsn ,
    pqsn.id_priorita_qsn ,
    pqsn.desc_priorita_qsn
  FROM pbandi_d_obiettivo_gen_qsn ogQsn ,
    pbandi_d_obiettivo_specif_qsn osQsn ,
    pbandi_d_priorita_qsn pQsn
  WHERE osqsn.id_obiettivo_gen_qsn                          = ogQsn.id_obiettivo_gen_qsn (+)
  AND ogqsn.id_priorita_qsn                                 = pqsn.id_priorita_qsn (+)
  AND NVL(TRUNC(ogqsn.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
  AND NVL(TRUNC(osqsn.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
  AND NVL(TRUNC(pqsn.dt_fine_validita), TRUNC(sysdate  +1)) > TRUNC(sysdate)
  ) obgen_obspec_prior_qsn,
  ( SELECT DISTINCT cc.id_categoria_cipe ,
    cc.desc_categoria_cipe ,
    ssc.id_sotto_settore_cipe ,
    ssc.desc_sotto_settore_cipe ,
    sc.id_settore_cipe ,
    sc.desc_settore_cipe
  FROM pbandi_d_categoria_cipe cc,
    pbandi_d_sotto_settore_cipe ssc,
    pbandi_d_settore_cipe sc
  WHERE cc.id_sotto_settore_cipe                         = ssc.id_sotto_settore_cipe(+)
  AND sc.id_settore_cipe (+)                             = ssc.id_settore_cipe
  AND NVL(TRUNC(cc.dt_fine_validita), TRUNC(sysdate  +1)) > TRUNC(sysdate)
  AND NVL(TRUNC(sc.dt_fine_validita), TRUNC(sysdate  +1)) > TRUNC(sysdate)
  AND NVL(TRUNC(ssc.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
  ) cipe,
  (SELECT tc.id_tipologia_cipe,
    tc.desc_tipologia_cipe,
    nc.id_natura_cipe,
    nc.desc_natura_cipe
  FROM pbandi_d_tipologia_cipe tc,
    pbandi_d_natura_cipe nc
  WHERE tc.id_natura_cipe                                = nc.id_natura_cipe
  AND NVL(TRUNC(tc.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
  AND NVL(TRUNC(nc.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
  ) natura_cipe
WHERE p.id_progetto = set_ateco_dimterr.id_progetto (+)
AND p.id_obiettivo_specif_qsn                          = obgen_obspec_prior_qsn.id_obiettivo_specif_qsn (+)
AND p.id_categoria_cipe                                = cipe.id_categoria_cipe (+)
AND p.id_tipologia_cipe                                = natura_cipe.id_tipologia_cipe(+)
AND p.id_domanda                                       = d.id_domanda
AND d.id_tipo_aiuto                                    = ta.id_tipo_aiuto (+)
AND p.id_progetto                                      = dpm.id_progetto (+)
AND p.id_progetto                                      = rsp.id_progetto
AND rsp.ID_TIPO_ANAGRAFICA     =
  (SELECT dta.id_tipo_anagrafica
  FROM pbandi_d_tipo_anagrafica dta
  WHERE dta.desc_breve_tipo_anagrafica = 'BENEFICIARIO'
  )
AND NVL(rsp.id_tipo_beneficiario, '-1') <>
  (SELECT dtb.id_tipo_beneficiario
  FROM pbandi_d_tipo_beneficiario dtb
  WHERE dtb.desc_breve_tipo_beneficiario = 'BEN-ASSOCIATO'
  )
AND NVL(TRUNC(ta.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)