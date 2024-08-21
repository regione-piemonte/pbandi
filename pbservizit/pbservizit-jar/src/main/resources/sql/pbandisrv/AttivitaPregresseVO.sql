/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select wa.*, 
   ap.nome_colonna, 
   ap.nome_etichetta,
   d.NOME_DOCUMENTO
from pbandi_w_attivita_pregresse wa 
  left join pbandi_c_attivita_pregresse ap on wa.cod_elemento = ap.cod_elemento
  left join pbandi_t_documento_index d ON wa.id_documento_index = d.id_documento_index
order by wa.data desc, ap.cod_elemento asc, ap.nome_etichetta asc