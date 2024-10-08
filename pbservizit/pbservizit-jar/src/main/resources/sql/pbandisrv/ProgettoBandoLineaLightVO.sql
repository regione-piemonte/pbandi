/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT TP.*,
       RB.PROGR_BANDO_LINEA_INTERVENTO ID_BANDO_LINEA,
       RB.NOME_BANDO_LINEA DESCRIZIONE_BANDO,flag_sif
from   PBANDI_T_PROGETTO TP,
       PBANDI_T_DOMANDA TD,
       PBANDI_R_BANDO_LINEA_INTERVENT RB  
where  TP.ID_DOMANDA = TD.ID_DOMANDA
  and RB.PROGR_BANDO_LINEA_INTERVENTO = TD.PROGR_BANDO_LINEA_INTERVENTO
  