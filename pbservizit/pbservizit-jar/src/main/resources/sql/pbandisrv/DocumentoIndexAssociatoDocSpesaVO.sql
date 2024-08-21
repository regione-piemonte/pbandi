/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select doc.id_documento_di_spesa,
		 docind.id_documento_index,
 		 file_entita.id_progetto, 
       docind.nome_file,
       docind.num_protocollo,
       docind.uuid_nodo      
from pbandi_t_documento_index docind 
join pbandi_t_file tfile 
  on tfile.id_documento_index=docind.id_documento_index
join pbandi_t_file_entita file_entita 
  on tfile.id_file=file_entita.id_file
join pbandi_t_documento_di_spesa doc
  on file_entita.id_target = doc.id_documento_di_spesa
join pbandi_c_entita ent 
  on file_entita.id_entita = ent.id_entita
  and ent.nome_entita = upper('pbandi_t_documento_di_spesa')