/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select * 
from (
select i.*,
       pe.desc_periodo_visualizzata, 
       cat.desc_categ_anagrafica,
	   r.DESC_MOTIVO_REVOCA,
       p.codice_visualizzato,
       rsp.id_soggetto as id_soggetto_beneficiario,
       case
     	when rsp.id_persona_fisica is null 
        then (
          select distinct eg.denominazione_ente_giuridico
          from PBANDI_T_ENTE_GIURIDICO eg
          where eg.id_ente_giuridico = rsp.id_ente_giuridico 
        ) else ( 
           select distinct pf.cognome || ' ' || pf.nome
           from PBANDI_T_PERSONA_FISICA pf
           where pf.id_persona_fisica = rsp.id_persona_fisica
        )
      end denominazione_beneficiario
from 
     PBANDI_T_IRREGOLARITA_PROVV i
       LEFT JOIN PBANDI_T_PERIODO pe on i.ID_PERIODO = pe.ID_PERIODO
       LEFT JOIN PBANDI_D_CATEG_ANAGRAFICA cat on i.ID_CATEG_ANAGRAFICA = cat.ID_CATEG_ANAGRAFICA,
     PBANDI_T_PROGETTO p,
     PBANDI_D_MOTIVO_REVOCA r,
     PBANDI_R_SOGGETTO_PROGETTO rsp
where p.id_progetto = i.id_progetto
  and i.ID_MOTIVO_REVOCA = r.ID_MOTIVO_REVOCA
  and p.id_progetto = rsp.id_progetto
  and rsp.id_tipo_anagrafica             = (select dta.id_tipo_anagrafica from pbandi_d_tipo_anagrafica dta where dta.desc_breve_tipo_anagrafica = 'BENEFICIARIO')
  and nvl(rsp.id_tipo_beneficiario,'-1') <> (select dtb.id_tipo_beneficiario from pbandi_d_tipo_beneficiario dtb where dtb.desc_breve_tipo_beneficiario = 'BEN-ASSOCIATO')
  and NVL(TRUNC(i.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
) irr