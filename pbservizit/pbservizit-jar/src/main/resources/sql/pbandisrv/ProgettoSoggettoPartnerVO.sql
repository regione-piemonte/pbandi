/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT prog.id_progetto_padre as id_progetto,
  rsp.id_soggetto as id_soggetto_partner,
  rsp.id_progetto as id_progetto_partner,
  NVL2(rsp.ID_ENTE_GIURIDICO,
  (SELECT PBANDI_T_ENTE_GIURIDICO.DENOMINAZIONE_ENTE_GIURIDICO
  FROM PBANDI_T_ENTE_GIURIDICO
  WHERE rsp.ID_ENTE_GIURIDICO = PBANDI_T_ENTE_GIURIDICO.ID_ENTE_GIURIDICO
  AND (TRUNC(sysdate)        >= NVL(TRUNC(PBANDI_T_ENTE_GIURIDICO.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1)
  AND TRUNC(sysdate)          < NVL(TRUNC(PBANDI_T_ENTE_GIURIDICO.DT_FINE_VALIDITA), TRUNC(sysdate)  +1))
  ),
  (SELECT PBANDI_T_PERSONA_FISICA.COGNOME
    ||' '
    || PBANDI_T_PERSONA_FISICA.NOME
  FROM PBANDI_T_PERSONA_FISICA
  WHERE rsp.ID_PERSONA_FISICA = PBANDI_T_PERSONA_FISICA.ID_PERSONA_FISICA
  AND (TRUNC(sysdate)        >= NVL(TRUNC(PBANDI_T_PERSONA_FISICA.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1)
  AND TRUNC(sysdate)          < NVL(TRUNC(PBANDI_T_PERSONA_FISICA.DT_FINE_VALIDITA), TRUNC(sysdate)  +1))
  ))||' ('|| prog.codice_visualizzato ||')' AS desc_soggetto_partner
FROM PBANDI_R_SOGGETTO_PROGETTO rsp,
  pbandi_t_progetto prog
WHERE rsp.id_tipo_anagrafica =
  (SELECT dta.id_tipo_anagrafica
  FROM pbandi_d_tipo_anagrafica dta
  WHERE dta.desc_breve_tipo_anagrafica = 'BENEFICIARIO'
  )
AND NVL(rsp.id_tipo_beneficiario,'-1') <>
  (SELECT dtb.id_tipo_beneficiario
  FROM pbandi_d_tipo_beneficiario dtb
  WHERE dtb.desc_breve_tipo_beneficiario = 'BEN-ASSOCIATO'
  )
AND prog.id_progetto = rsp.id_progetto
AND (TRUNC(sysdate) >= NVL(TRUNC(rsp.DT_INIZIO_VALIDITA), TRUNC(sysdate)-1)
AND TRUNC(sysdate)   < NVL(TRUNC(rsp.DT_FINE_VALIDITA), TRUNC(sysdate)  +1))