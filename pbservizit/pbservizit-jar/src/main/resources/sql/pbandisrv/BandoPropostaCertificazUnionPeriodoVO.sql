/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT t.ID_PROPOSTA_CERTIFICAZ, 
	t.DESC_PROPOSTA, 
	t.DT_ORA_CREAZIONE, 
	ID_STATO_PROPOSTA_CERTIF,  
	p.ID_PERIODO, 
	ID_TIPO_PERIODO, 
	DESC_PERIODO,
	DESC_PERIODO_VISUALIZZATA, 
	DT_INIZIO_VALIDITA, 
	DT_FINE_VALIDITA
from PBANDI_T_PROPOSTA_CERTIFICAZ t, PBANDI_T_PERIODO p 
 WHERE p.ID_PERIODO = t.ID_PERIODO