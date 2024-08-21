/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT docind.id_documento_index,     
       docind.id_progetto,
       docind.nome_file,
       docind.uuid_nodo,
       pag.id_pagamento
FROM pbandi_t_documento_index docInd 
JOIN pbandi_t_pagamento pag
	ON docind.id_target = pag.id_pagamento
JOIN pbandi_c_entita ent
	ON docind.id_entita = ent.id_entita
 	AND ent.nome_entita = 'PBANDI_T_PAGAMENTO'