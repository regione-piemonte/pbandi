/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT 
CODICE_FISCALE_SOGGETTO,     
DENOMINAZIONE_ENTE_GIURIDICO DENOMINAZIONE_BENEFICIARIO,
DT_INIZIO_VALIDITA ,           
E.ID_ENTE_GIURIDICO ,           
T.ID_SOGGETTO    
FROM PBANDI.PBANDI_T_SOGGETTO T 
JOIN PBANDI_T_ENTE_GIURIDICO E
ON T.ID_SOGGETTO = E.ID_SOGGETTO
AND TRUNC(sysdate)  < NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(sysdate)  +1)
ORDER BY DT_INIZIO_VALIDITA DESC