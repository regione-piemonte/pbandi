/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

Select rsp.ID_SOGGETTO, 
         rsp.ID_PROGETTO,
         rsp.PROGR_SOGGETTO_PROGETTO,  
         eb.*
From     PBANDI_R_SOGGETTO_PROGETTO rsp, 
            PBANDI_T_ESTREMI_BANCARI eb
Where  rsp.ID_ESTREMI_BANCARI = eb.ID_ESTREMI_BANCARI 
 and rsp.ID_TIPO_ANAGRAFICA = (select dta.id_tipo_anagrafica from pbandi_d_tipo_anagrafica dta where dta.desc_breve_tipo_anagrafica = 'BENEFICIARIO')
  and nvl(rsp.id_tipo_beneficiario, '-1') <> (select dtb.id_tipo_beneficiario from pbandi_d_tipo_beneficiario dtb where dtb.desc_breve_tipo_beneficiario = 'BEN-ASSOCIATO')