/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT *
FROM PBANDI_R_BANDO_LINEA_ENTE_COMP RBANDOENTE 
JOIN PBANDI_R_BANDO_LINEA_SETTORE RBANDOSETTORE 
  USING (ID_RUOLO_ENTE_COMPETENZA,PROGR_BANDO_LINEA_INTERVENTO)
JOIN PBANDI_D_SETTORE_ENTE SETTORE 
  USING (ID_SETTORE_ENTE,ID_ENTE_COMPETENZA)