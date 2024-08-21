/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT PBANDI_T_PERSONA_FISICA.id_persona_fisica AS id_persona_fisica,
PBANDI_T_PERSONA_FISICA.id_soggetto AS id_soggetto,
PBANDI_T_PERSONA_FISICA.cognome AS cognome,
PBANDI_T_PERSONA_FISICA.nome AS nome,
PBANDI_T_PERSONA_FISICA.dt_nascita AS dt_nascita ,
PBANDI_T_SOGGETTO.codice_fiscale_soggetto as codice_fiscale
FROM 
(
  SELECT PBANDI_T_SOGGETTO.id_soggetto AS id_soggetto
          ,max(PBANDI_T_PERSONA_FISICA.DT_INIZIO_VALIDITA) as dt_inizio_validita
          ,max(PBANDI_T_PERSONA_FISICA.id_persona_fisica) as id_persona_fisica
  FROM PBANDI_T_SOGGETTO,
       PBANDI_D_TIPO_SOGGETTO,
       PBANDI_T_PERSONA_FISICA
  WHERE PBANDI_T_SOGGETTO.id_tipo_soggetto             = PBANDI_D_TIPO_SOGGETTO.id_tipo_soggetto
  AND PBANDI_D_TIPO_SOGGETTO.desc_breve_tipo_soggetto   = 'PF'
  AND PBANDI_T_PERSONA_FISICA.id_soggetto               = PBANDI_T_SOGGETTO.id_soggetto
  AND NVL(TRUNC(PBANDI_D_TIPO_SOGGETTO.DT_FINE_VALIDITA), TRUNC(sysdate+1)) > TRUNC(sysdate)
  AND NVL(TRUNC(PBANDI_T_PERSONA_FISICA.DT_FINE_VALIDITA), TRUNC(sysdate+1)) > TRUNC(sysdate)
  group by (PBANDI_T_SOGGETTO.id_soggetto)
) soggPersFisica,
PBANDI_T_PERSONA_FISICA,
PBANDI_T_SOGGETTO
where PBANDI_T_PERSONA_FISICA.id_persona_fisica = soggPersFisica.id_persona_fisica
  and PBANDI_T_SOGGETTO.id_soggetto = soggPersFisica.id_soggetto
