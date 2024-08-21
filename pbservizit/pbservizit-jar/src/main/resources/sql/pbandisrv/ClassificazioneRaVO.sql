/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT D.ID_CLASSIFICAZIONE_RA ID_CLASSIFICAZIONE_RA,
       D.COD_CLASSIFICAZIONE_RA COD_CLASSIFICAZIONE_RA,
       D.DESCR_CLASSIFICAZIONE_RA DESCR_CLASSIFICAZIONE_RA,
       R.ID_OBIETTIVO_TEM
FROM PBANDI_D_CLASSIFICAZIONE_RA D, PBANDI_R_OBTEM_CLASSRA R
WHERE D.ID_CLASSIFICAZIONE_RA = R.ID_CLASSIFICAZIONE_RA