SELECT DISTINCT 
       ENTESOGG.ID_ENTE_COMPETENZA ,
       RSP.ID_SOGGETTO,
       ENTESOGG.ID_SOGGETTO idSoggOperante,
       pr.id_progetto,
       CODICE_VISUALIZZATO,
       DESC_BREVE_TIPO_ENTE_COMPETENZ
from pbandi_r_ente_competenza_sogg enteSogg, 
     PBANDI_T_ENTE_COMPETENZA ENTE,
     PBANDI_D_TIPO_ENTE_COMPETENZA TIPOENTE,
     PBANDI_R_BANDO_LINEA_ENTE_COMP BANDOENTE,
     PBANDI_D_RUOLO_ENTE_COMPETENZA RUOLOENTE,
     PBANDI_R_SOGGETTO_PROGETTO RSP ,
     PBANDI_T_PROGETTO PR,
     PBANDI_T_DOMANDA DOM
where entesogg.id_ente_competenza = ente.id_ente_competenza
  and ente.id_tipo_ente_competenza = tipoente.id_tipo_ente_competenza
  and NVL(TRUNC(ente.DT_FINE_VALIDITA), TRUNC(SYSDATE     +1)) > TRUNC(SYSDATE)
  and NVL(TRUNC(enteSogg.DT_FINE_VALIDITA), TRUNC(SYSDATE     +1)) > TRUNC(SYSDATE)
  AND NVL(TRUNC(TIPOENTE.DT_FINE_VALIDITA), TRUNC(SYSDATE     +1)) > TRUNC(SYSDATE)
  and entesogg.id_ente_competenza =bandoente.id_ente_competenza
  AND RUOLOENTE.ID_RUOLO_ENTE_COMPETENZA=BANDOENTE.ID_RUOLO_ENTE_COMPETENZA
  AND SYSDATE BETWEEN NVL(RUOLOENTE.DT_INIZIO_VALIDITA, SYSDATE -1) AND NVL(RUOLOENTE.DT_FINE_VALIDITA, SYSDATE +1)
  AND SYSDATE < NVL(BANDOENTE.DT_FINE_VALIDITA, SYSDATE +1)
  AND SYSDATE BETWEEN NVL(RSP.DT_INIZIO_VALIDITA, SYSDATE -1)
  AND NVL(rsp.DT_FINE_VALIDITA, SYSDATE +1)
  AND RSP.ID_PROGETTO= PR.ID_PROGETTO 
  AND PR.ID_DOMANDA = DOM.ID_DOMANDA
  AND DOM.PROGR_BANDO_LINEA_INTERVENTO=BANDOENTE.PROGR_BANDO_LINEA_INTERVENTO
  AND RUOLOENTE.DESC_BREVE_RUOLO_ENTE='DESTINATARIO'
