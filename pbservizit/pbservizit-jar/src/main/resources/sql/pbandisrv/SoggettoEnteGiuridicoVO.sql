/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT  
SOGG.CODICE_FISCALE_SOGGETTO CODICE_FISCALE,
ENTE.DENOMINAZIONE_ENTE_GIURIDICO DENOMINAZIONE,
ENTE.ID_ENTE_GIURIDICO,
SOGG.ID_SOGGETTO
FROM 
PBANDI.PBANDI_T_SOGGETTO SOGG,
PBANDI_T_ENTE_GIURIDICO ENTE  
where SOGG.ID_SOGGETTO  = ENTE.ID_SOGGETTO
order by SOGG.ID_SOGGETTO desc