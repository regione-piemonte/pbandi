/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT D.*, R.ID_BANDO
  FROM PBANDI_R_BANDO_VOCE_ENTRATA R, PBANDI_D_VOCE_DI_ENTRATA D
 WHERE D.ID_VOCE_DI_ENTRATA = R.ID_VOCE_DI_ENTRATA