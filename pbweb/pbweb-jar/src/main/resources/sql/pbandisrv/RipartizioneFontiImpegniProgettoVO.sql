SELECT id_impegno,
  id_progetto,
  desc_impegno,
  anno_impegno,
  anno_esercizio,
  numero_impegno,
  disponibilita_liquidare,
  importo_attuale_impegno,
  id_tipo_fondo,
  desc_tipo_fondo,
  desc_sogg_finanziatore,
  dt_provvedimento,
  id_soggetto_finanziatore,
  id_capitolo,
  numero_capitolo,
  cig,
  anno_perente,
  numero_perente
FROM
  ( SELECT DISTINCT i.id_impegno,
    ripr.id_progetto,
    i.desc_impegno,
    i.anno_impegno,
    i.anno_esercizio,
    i.numero_impegno,
    i.anno_perente,
    i.numero_perente,
    i.disponibilita_liquidare,
    i.importo_attuale_impegno,
    i.cig_impegno           AS cig,
    cap.id_tipo_fondo_cap   AS id_tipo_fondo,
    cap.desc_tipo_fondo_cap AS desc_tipo_fondo,
    cap.id_capitolo,
    cap.numero_capitolo,
    fonte.id_soggetto_finanziatore,
    fonte.desc_sogg_finanziatore,
    provv.dt_provvedimento AS dt_provvedimento
  FROM pbandi_t_impegno i,
    pbandi_r_impegno_progetto ripr,
    PBANDI_T_DOMANDA d ,
    PBANDI_R_BANDO_LINEA_INTERVENT rbli,
    PBANDI_T_PROGETTO p,
    (
    /* capitolo */
    SELECT DISTINCT cap.id_capitolo,
      cap.numero_capitolo,
      cap.id_ente_competenza   AS id_ente_cap,
      tipFondo.id_tipo_fondo   AS id_tipo_fondo_cap,
      tipFondo.desc_tipo_fondo AS desc_tipo_fondo_cap
    FROM pbandi_t_capitolo cap,
      pbandi_d_tipo_fondo tipFondo
    WHERE cap.id_tipo_fondo                                     = tipfondo.id_tipo_fondo
    AND NVL(TRUNC(tipFondo.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
    ) cap,
    (
    /* provvedimento */
    SELECT provv.id_provvedimento,
      provv.id_ente_competenza AS id_ente_provv,
      recs.id_soggetto         AS id_soggetto_provv,
      provv.dt_provvedimento
    FROM pbandi_t_provvedimento provv,
      pbandi_r_ente_competenza_sogg recs
    WHERE provv.id_ente_competenza                          = recs.id_ente_competenza
    AND NVL(TRUNC(recs.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
    ) provv,
    (
    /* fonte */
    SELECT bs.id_soggetto_finanziatore,
      desc_sogg_finanziatore,
      id_tipo_fondo,
      bs.id_bando
    FROM pbandi_d_tipo_sogg_finanziat tipoFondo,
      pbandi_d_soggetto_finanziatore fondo,
      pbandi_r_bando_sogg_finanziat bs
    WHERE fondo.id_tipo_sogg_finanziat  = tipoFondo.id_tipo_sogg_finanziat
    AND desc_breve_tipo_sogg_finanziat <> 'COFPOR'
    AND desc_breve_tipo_sogg_finanziat <> 'OTHFIN'
    AND bs.id_soggetto_finanziatore     = fondo.id_soggetto_finanziatore
      --and bs.id_bando = :idBando
    AND NVL(TRUNC(fondo.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
    ) fonte
  WHERE i.id_capitolo                                     = cap.id_capitolo
  AND i.id_provvedimento                                  = provv.id_provvedimento (+)
  AND i.id_impegno                                        = ripr.id_impegno
  AND fonte.id_tipo_fondo                                 = cap.id_tipo_fondo_cap
  and ripr.id_progetto = p.id_progetto
  and p.id_domanda = d.id_domanda
  and d.progr_bando_linea_intervento = rbli.progr_bando_linea_intervento
  AND fonte.id_bando                                      = rbli.id_bando
  AND disponibilita_liquidare                             > 0
  AND NVL(TRUNC(ripr.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
  )
ORDER BY desc_sogg_finanziatore,
  anno_perente,
  numero_perente,
  disponibilita_liquidare,
  anno_esercizio,
  dt_provvedimento ASC