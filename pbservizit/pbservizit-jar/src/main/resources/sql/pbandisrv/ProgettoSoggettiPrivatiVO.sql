/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select rsp.*,
  case
    when rsp.id_persona_fisica is null
    then
      ( select distinct sogg.codice_fiscale_soggetto
        || ' ('
        || eg.denominazione_ente_giuridico
        || ')'
      from PBANDI_T_ENTE_GIURIDICO eg,
        PBANDI_T_SOGGETTO sogg
      where eg.id_ente_giuridico = rsp.id_ente_giuridico
      and eg.id_soggetto         = sogg.id_soggetto
      and (trunc(sysdate)       >= nvl(trunc(eg.DT_INIZIO_VALIDITA), trunc(sysdate) -1)
      and trunc(sysdate)         < nvl(trunc(eg.DT_FINE_VALIDITA), trunc(sysdate)   +1))
      )
    else
      ( select distinct sogg.codice_fiscale_soggetto
        || ' ('
        || pf.cognome
        || ' '
        || pf.nome
        || ')'
      from PBANDI_T_PERSONA_FISICA pf,
        PBANDI_T_SOGGETTO sogg
      where pf.id_persona_fisica = rsp.id_persona_fisica
      and pf.id_soggetto         = sogg.id_soggetto
      and (trunc(sysdate)       >= nvl(trunc(pf.DT_INIZIO_VALIDITA), trunc(sysdate) -1)
      and trunc(sysdate)         < nvl(trunc(pf.DT_FINE_VALIDITA), trunc(sysdate)   +1))
      )
  end denominazione_soggetto_privato
from pbandi_r_soggetto_progetto rsp,
  pbandi_d_tipo_anagrafica da
where da.id_tipo_anagrafica         = rsp.id_tipo_anagrafica
and ( da.desc_breve_tipo_anagrafica = 'BENEFICIARIO'
or da.desc_breve_tipo_anagrafica    = 'ENTE-GIURIDICO' )
and (trunc(sysdate)                >= nvl(trunc(da.DT_INIZIO_VALIDITA), trunc(sysdate) -1)
and trunc(sysdate)                  < nvl(trunc(da.DT_FINE_VALIDITA), trunc(sysdate)   +1))
and (trunc(sysdate)                >= nvl(trunc(rsp.DT_INIZIO_VALIDITA), trunc(sysdate)-1)
and trunc(sysdate)                  < nvl(trunc(rsp.DT_FINE_VALIDITA), trunc(sysdate)  +1))