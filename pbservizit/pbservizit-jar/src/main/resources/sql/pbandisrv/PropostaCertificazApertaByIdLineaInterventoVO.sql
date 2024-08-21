/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select p.*, 
       s.desc_breve_stato_proposta_cert,
       s.desc_stato_proposta_certif,
       r.id_linea_di_intervento
  from PBANDI_T_PROPOSTA_CERTIFICAZ  p,
       PBANDI_D_STATO_PROPOSTA_CERTIF s,
       PBANDI_R_PROPOSTA_CERTIF_LINEA r
 where p.id_stato_proposta_certif = s.id_stato_proposta_certif
   and r.id_proposta_certificaz = p.id_proposta_certificaz
 order by p.ID_PROPOSTA_CERTIFICAZ
