/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select distinct ID_BANDO ,ID_PROGETTO,PROGR_BANDO_LINEA_INTERVENTO,TITOLO_BANDO
from PBANDI_T_PROGETTO
join PBANDI_T_DOMANDA using(ID_DOMANDA)
join PBANDI_R_BANDO_LINEA_INTERVENT using(PROGR_BANDO_LINEA_INTERVENTO)
join PBANDI_T_BANDO using(ID_BANDO)