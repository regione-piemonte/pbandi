/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT dt_tracciamento ts_tracciamento,
  id_tracciamento,
  tipo
  || id_tracciamento AS cod_tracciamento,
  id_utente,
  cognome_nome_codice,
  dt_tracciamento,
  TO_CHAR(dt_tracciamento, 'dd-mm-yyyy hh24:mi:ss') AS tt_tracciamento,
  DESC_LOGICA,
  flag_esito,
  messaggio_errore,
  codice_errore,
  durata,
  tipo
FROM
  (SELECT *
  FROM
    (SELECT tr.dt_tracciamento,
      tr.id_tracciamento,
      ut.id_utente,
      ut.cognome_nome_codice,
      OP.DESC_LOGICA,
      tr.flag_esito,
      tr.messaggio_errore AS MESSAGGIO_ERRORE,
      tr.messaggio_errore AS CODICE_ERRORE,
      tr.durata,
      'J' AS tipo
    FROM pbandi_t_tracciamento tr,
      pbandi_c_operazione op,
      (SELECT u.id_utente,
        p.cognome
        || ' '
        || p.nome
        || ' '
        || u.codice_utente cognome_nome_codice
      FROM pbandi_t_utente u,
        pbandi_t_persona_fisica p,
        (SELECT DISTINCT MAX(id_persona_fisica) over (partition BY id_soggetto) AS id_persona_fisica
        FROM pbandi_t_persona_fisica
        ) last_p_by_soggetto
      WHERE u.id_soggetto                                  = p.id_soggetto
      AND p.id_persona_fisica                              = last_p_by_soggetto.id_persona_fisica
      AND NVL(TRUNC(p.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
      ) ut
    WHERE op.id_operazione                                = tr.id_operazione
    AND ut.id_utente                                      = tr.id_utente
    AND NVL(TRUNC(op.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
    )
  UNION
    (SELECT pb.dt_inizio_elaborazione,
      pb.id_processo_batch,
      (-1),
      'PROCESSO BATCH',
      nb.desc_batch,
      CASE (pb.flag_esito)
        WHEN 'OK'
        THEN 'S'
        ELSE 'N'
      END,
      lb.messaggio_errore,
      eb.descrizione,
      TRUNC((pb.dt_fine_elaborazione - pb.dt_inizio_elaborazione)*24*60*60),
      'B'
    FROM pbandi_l_log_batch lb,
      pbandi_l_processo_batch pb,
      pbandi_d_errore_batch eb,
      pbandi_d_nome_batch nb
    WHERE lb.id_processo_batch (+)= pb.id_processo_batch
    AND nb.id_nome_batch          = pb.id_nome_batch
    AND LB.CODICE_ERRORE          = EB.CODICE_ERRORE
    )
  )