/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select tdpc.id_proposta_certificaz, t.*
  from pbandi_t_dett_proposta_certif tdpc,
       pbandi_r_det_prop_cer_qp_doc t
 where tdpc.id_dett_proposta_certif = t.id_dett_proposta_certif
