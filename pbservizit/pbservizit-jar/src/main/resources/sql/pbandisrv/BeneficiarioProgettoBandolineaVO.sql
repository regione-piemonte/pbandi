/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select p.codice_visualizzato
      ,rsp.id_progetto
      ,p.id_istanza_processo
      ,bli.nome_bando_linea
      ,bli.progr_bando_linea_intervento
      ,rsp.id_soggetto
      ,nvl2( rsp.id_ente_giuridico,
              (select denominazione_ente_giuridico
               from pbandi_t_ente_giuridico
               where id_ente_giuridico = rsp.id_ente_giuridico),
              ( select cognome || nome
                from pbandi_t_persona_fisica
                where id_persona_fisica = rsp.id_persona_fisica)
       ) as beneficiario
       ,s.codice_fiscale_soggetto
from pbandi_r_soggetto_progetto rsp
     ,pbandi_t_progetto p
     ,pbandi_t_soggetto s
     ,pbandi_t_domanda d
     ,pbandi_r_bando_linea_intervent bli
where rsp.ID_TIPO_ANAGRAFICA in (select dta.id_tipo_anagrafica from pbandi_d_tipo_anagrafica dta
 where (upper(dta.desc_breve_tipo_anagrafica) = 'BENEFICIARIO') OR upper(dta.desc_breve_tipo_anagrafica) = 'PERSONA-FISICA')
  and nvl(rsp.id_tipo_beneficiario, '-1') <> (select dtb.id_tipo_beneficiario from pbandi_d_tipo_beneficiario dtb where dtb.desc_breve_tipo_beneficiario = 'BEN-ASSOCIATO')
  and p.id_progetto = rsp.id_progetto
  and s.id_soggetto = rsp.id_soggetto
  and p.id_domanda = d.id_domanda
  and d.progr_bando_linea_intervento = bli.progr_bando_linea_intervento
  and nvl(trunc(rsp.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)