SELECT vBandoEnte.PROGR_BANDO_LINEA_ENTE_COMP,
 vBandoEnte.PROGR_BANDO_LINEA_INTERVENTO,
 ENTE.desc_ente
  ||' - '
  || nvl2(SETT.DESC_SETTORE,(SETT.DESC_SETTORE||' - '),'')
  ||RUOLO.desc_ruolo_ente AS DESCRIZIONE,
  ENTE.desc_breve_ente,
  ENTE.DESC_ENTE,
  RUOLO.DESC_BREVE_RUOLO_ENTE as RUOLO,
  vBandoEnte.PAROLA_CHIAVE,
  vBandoEnte.FEEDBACK_ACTA
FROM PBANDI_V_BANDO_LINEA_ENTE_COMP vBandoEnte,
  PBANDI_T_ENTE_COMPETENZA ENTE,
  PBANDI_D_SETTORE_ENTE   SETT,
  PBANDI_D_RUOLO_ENTE_COMPETENZA RUOLO
WHERE vBandoEnte.ID_ENTE_COMPETENZA     = ENTE.ID_ENTE_COMPETENZA
and vBandoEnte.ID_RUOLO_ENTE_COMPETENZA = RUOLO.ID_RUOLO_ENTE_COMPETENZA
and vBandoEnte.ID_SETTORE_ENTE    = SETT.ID_SETTORE_ENTE(+) 
AND TRUNC(sysdate)             < NVL(TRUNC(vBandoEnte.DT_FINE_VALIDITA), TRUNC(sysdate) +1)
AND (TRUNC(sysdate)           >= NVL(TRUNC(ENTE.DT_INIZIO_VALIDITA), TRUNC(sysdate)  -1)
AND TRUNC(sysdate)             < NVL(TRUNC(ENTE.DT_FINE_VALIDITA), TRUNC(sysdate)    +1))
and (TRUNC(sysdate)           >= NVL(TRUNC(RUOLO.DT_INIZIO_VALIDITA), TRUNC(sysdate) -1)
and TRUNC(sysdate)             < NVL(TRUNC(RUOLO.DT_FINE_VALIDITA), TRUNC(sysdate)   +1))
and (TRUNC(sysdate)           >= NVL(TRUNC(SETT.DT_INIZIO_VALIDITA), TRUNC(sysdate) -1)
and TRUNC(sysdate)             < NVL(TRUNC(SETT.DT_FINE_VALIDITA), TRUNC(sysdate)   +1))