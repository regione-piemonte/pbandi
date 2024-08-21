/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select RSP.ID_PROGETTO,
       eg.*
from PBANDI_R_SOGGETTO_PROGETTO RSP,
     PBANDI_T_ENTE_GIURIDICO eg
where RSP.ID_ENTE_GIURIDICO is not null
  and RSP.ID_ENTE_GIURIDICO = EG.ID_ENTE_GIURIDICO
  and RSP.ID_SOGGETTO = EG.ID_SOGGETTO
  and rsp.ID_TIPO_ANAGRAFICA = (select dta.id_tipo_anagrafica from pbandi_d_tipo_anagrafica dta where dta.desc_breve_tipo_anagrafica = 'BENEFICIARIO')
  and nvl(rsp.id_tipo_beneficiario, '-1') <> (select dtb.id_tipo_beneficiario from pbandi_d_tipo_beneficiario dtb where dtb.desc_breve_tipo_beneficiario = 'BEN-ASSOCIATO')
  and nvl(trunc(rsp.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)