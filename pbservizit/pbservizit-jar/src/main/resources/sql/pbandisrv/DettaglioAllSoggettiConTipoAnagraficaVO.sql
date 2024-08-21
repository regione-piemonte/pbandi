/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT DISTINCT     
    TPF.COGNOME, 
    TPF.NOME, 
    TS.CODICE_FISCALE_SOGGETTO AS CODICE_FISCALE, 
    TPF.ID_SOGGETTO, 
    TPF.ID_PERSONA_FISICA,  
    RSTA.ID_TIPO_ANAGRAFICA, 
    DTA.DESC_TIPO_ANAGRAFICA, 
    DTA.DESC_BREVE_TIPO_ANAGRAFICA, 
    TS.DT_INSERIMENTO  AS DATA_INSERIMENTO,   
    RSC.ID_TIPO_SOGGETTO_CORRELATO AS ID_RELAZIONE_BENEFICIARIO,
    (
	SELECT
		COUNT(*)
	FROM
		PBANDI_R_SOGGETTO_PROGETTO
	WHERE
		TPF.ID_SOGGETTO = PBANDI_R_SOGGETTO_PROGETTO.ID_SOGGETTO
		AND SYSDATE < NVL(PBANDI_R_SOGGETTO_PROGETTO.DT_FINE_VALIDITA, SYSDATE + 1) ) PROGETTI_VALIDI,
	(
	SELECT
		COUNT(*)
	FROM
		PBANDI_R_SOGGETTO_PROGETTO
	WHERE
		TPF.ID_SOGGETTO = PBANDI_R_SOGGETTO_PROGETTO.ID_SOGGETTO
		AND PBANDI_R_SOGGETTO_PROGETTO.DT_FINE_VALIDITA <SYSDATE) PROGETTI_NON_VALIDI,
	tu.ID_UTENTE
FROM
	PBANDI_T_UTENTE TU,
    PBANDI_T_SOGGETTO TS,
    PBANDI_V_PERSONA_FISICA TPF,
    PBANDI_R_SOGG_TIPO_ANAGRAFICA RSTA, 
    PBANDI_D_TIPO_ANAGRAFICA DTA ,
    PBANDI_R_SOGGETTI_CORRELATI RSC        
WHERE
   TS.ID_SOGGETTO     = TPF.ID_SOGGETTO   
   AND TS.ID_SOGGETTO = TU.ID_SOGGETTO (+)
   AND TS.ID_SOGGETTO = RSTA.ID_SOGGETTO
   AND TS.ID_SOGGETTO =   RSC.ID_SOGGETTO(+) 
   AND RSTA.ID_TIPO_ANAGRAFICA  = DTA.ID_TIPO_ANAGRAFICA     
   AND SYSDATE < NVL(DTA.DT_FINE_VALIDITA, SYSDATE +1) 
   AND SYSDATE < NVL(RSTA.DT_FINE_VALIDITA, SYSDATE +1)     
   AND SYSDATE < NVL(RSC.DT_FINE_VALIDITA, SYSDATE +1)  