/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select ce.nome_entita, ca.*
  from PBANDI_C_ATTRIBUTO ca,
       PBANDI_C_ENTITA ce
 where ce.ID_ENTITA = ca.ID_ENTITA
order by ca.KEY_POSITION_ID