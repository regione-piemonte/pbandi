/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select vBandoEnte.PROGR_BANDO_LINEA_ENTE_COMP,
 vBandoEnte.PROGR_BANDO_LINEA_INTERVENTO,
 ENTE.desc_ente
  ||' - '
  || nvl2(SETT.DESC_SETTORE,(SETT.DESC_SETTORE||' - '),'')
  ||RUOLO.desc_ruolo_ente AS DESCRIZIONE,
  ENTE.desc_breve_ente,
  ENTE.DESC_ENTE,
  RUOLO.DESC_BREVE_RUOLO_ENTE as RUOLO,
  vBandoEnte.CONSERVAZIONE_CORRENTE,
  vBandoEnte.CONSERVAZIONE_GENERALE,
  vBandoEnte.PAROLA_CHIAVE,
  vBandoEnte.FEEDBACK_ACTA,
  ecomp.indirizzo_mail_pec, 
  ecomp.indirizzo_mail
FROM PBANDI_V_BANDO_LINEA_ENTE_COMP vBandoEnte,
  PBANDI_T_ENTE_COMPETENZA ENTE,
  PBANDI_D_SETTORE_ENTE   SETT,
  PBANDI_D_RUOLO_ENTE_COMPETENZA RUOLO
 , PBANDI_R_BANDO_LINEA_ENTE_COMP ECOMP
WHERE vBandoEnte.ID_ENTE_COMPETENZA     = ENTE.ID_ENTE_COMPETENZA
and vBandoEnte.ID_RUOLO_ENTE_COMPETENZA = RUOLO.ID_RUOLO_ENTE_COMPETENZA
and vBandoEnte.ID_SETTORE_ENTE    = SETT.ID_SETTORE_ENTE(+) 
and ecomp.progr_bando_linea_intervento= vBandoEnte.PROGR_BANDO_LINEA_INTERVENTO
and ecomp.id_ente_competenza= vBandoEnte.ID_ENTE_COMPETENZA
and ecomp.id_ente_competenza=ente.id_ente_competenza
and ecomp.id_ruolo_ente_competenza= vBandoEnte.ID_RUOLO_ENTE_COMPETENZA
AND TRUNC(sysdate)             < NVL(TRUNC(vBandoEnte.DT_FINE_VALIDITA), TRUNC(sysdate) +1)
AND (TRUNC(sysdate)           >= NVL(TRUNC(ENTE.DT_INIZIO_VALIDITA), TRUNC(sysdate)  -1)
AND TRUNC(sysdate)             < NVL(TRUNC(ENTE.DT_FINE_VALIDITA), TRUNC(sysdate)    +1))
and (TRUNC(sysdate)           >= NVL(TRUNC(RUOLO.DT_INIZIO_VALIDITA), TRUNC(sysdate) -1)
and TRUNC(sysdate)             < NVL(TRUNC(RUOLO.DT_FINE_VALIDITA), TRUNC(sysdate)   +1))
and (TRUNC(sysdate)           >= NVL(TRUNC(SETT.DT_INIZIO_VALIDITA), TRUNC(sysdate) -1)
and TRUNC(sysdate)             < NVL(TRUNC(SETT.DT_FINE_VALIDITA), TRUNC(sysdate)   +1))