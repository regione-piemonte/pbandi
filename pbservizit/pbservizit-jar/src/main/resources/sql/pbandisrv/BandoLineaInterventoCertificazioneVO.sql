/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select id_proposta_certificaz, l.* 
from pbandi_r_proposta_certif_linea t, pbandi_d_linea_di_intervento l
where t.ID_LINEA_DI_INTERVENTO = l.ID_LINEA_DI_INTERVENTO