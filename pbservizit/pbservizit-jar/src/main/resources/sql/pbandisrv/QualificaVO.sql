/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT DQ.DESC_QUALIFICA,Q.* 
FROM PBANDI_R_FORNITORE_QUALIFICA Q
join PBANDI_D_QUALIFICA dq 
on q.id_qualifica=DQ.ID_QUALIFICA