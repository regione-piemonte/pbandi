/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT ptcac.ID_T_CE_ALTRI_COSTI AS ID_TCE_ALTRI_COSTI, PTCAC.ID_D_CE_ALTRI_COSTI AS ID_DCE_ALTRI_COSTI, PTCAC.IMP_CE_APPROVATO AS IMP_CE_APPROVATO,
pdcac.DESC_BREVE_CE_ALTRI_COSTI , PTCAC.IMP_CD_PROPMOD , PDCAC.DESC_CE_ALTRI_COSTI , PTCAC .ID_PROGETTO , (PTCAC.IMP_CE_APPROVATO- PTCAC.IMP_CD_PROPMOD) AS delta
FROM PBANDI_T_CE_ALTRI_COSTI ptcac
JOIN PBANDI_D_CE_ALTRI_COSTI pdcac ON ptcac.ID_D_CE_ALTRI_COSTI = pdcac.ID_D_CE_ALTRI_COSTI