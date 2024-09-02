/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT DISTINCT CODICE_FISCALE_SOGGETTO codice_fiscale ,DESC_BREVE_TIPO_ANAGRAFICA,rsta.ID_SOGGETTO, BENASS.ID_SOGGETTO_BENEFICIARIO,
                           FIRST_VALUE(ENTE.DENOMINAZIONE_ENTE_GIURIDICO)
                           OVER (PARTITION BY ENTE.ID_SOGGETTO ORDER BY 
                           ENTE.DT_INIZIO_VALIDITA DESC, ENTE.ID_ENTE_GIURIDICO DESC
                           ) DENOMINAZIONE_ENTE_GIURIDICO
                    FROM PBANDI_T_ENTE_GIURIDICO ENTE
                     JOIN  PBANDI_R_SOGG_BEN_ASSOCIATO BENASS
                          ON BENASS.ID_SOGGETTO_BENEFICIARIO = ENTE.ID_SOGGETTO
                     JOIN PBANDI_R_SOGG_TIPO_ANAGRAFICA RSTA 
                          ON  RSTA.ID_SOGGETTO=BENASS.ID_SOGGETTO
                     JOIN PBANDI_D_TIPO_ANAGRAFICA DTIPOANAG 
                          ON RSTA.ID_TIPO_ANAGRAFICA=DTIPOANAG.ID_TIPO_ANAGRAFICA  
                     JOIN PBANDI_T_SOGGETTO SOGG 
                          ON ENTE.ID_SOGGETTO=sogg.ID_SOGGETTO   
                     WHERE
                      NVL(TRUNC(RSTA.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE) and   
                      NVL(TRUNC(ENTE.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)              