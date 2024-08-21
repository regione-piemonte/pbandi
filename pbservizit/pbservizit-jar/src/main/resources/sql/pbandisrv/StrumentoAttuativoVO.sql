/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT  
R.ID_LINEA_DI_INTERVENTO,
D.ID_STRUMENTO_ATTUATIVO,
D.COD_IGRUE_T21,
D.DESC_STRUMENTO_ATTUATIVO,
D.DT_APPROVAZIONE,
D.DT_FINE_VALIDITA,
D.DT_INIZIO_VALIDITA,
D.ID_STRUMENTO_ATTUATIVO,   
D.RESPONSABILE,
D.TIPOLOGIA         
FROM PBANDI_D_STRUMENTO_ATTUATIVO D, PBANDI_R_LINEA_STRUMENTO_ATT R
WHERE D.ID_STRUMENTO_ATTUATIVO = R.ID_STRUMENTO_ATTUATIVO