/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select * 
  from (
select tcp.*,
       nvl2(tcs.denominazione, tcs.denominazione||' - '||tcs.codice_fiscale,
       nvl2(tcs.codice_fiscale, 
                               nvl2(tcs.cognome,
                                                 tcs.cognome||' '||tcs.nome||' - '||tcs.codice_fiscale,
                                                 tcs.codice_fiscale),
                               nvl2(tcs.id_ente_competenza, 
                                                 (select desc_ente from pbandi_t_ente_competenza te where te.id_ente_competenza = tcs.id_ente_competenza),
                                                 nvl2(tcs.id_dipartimento,
                                                         (select dd.desc_dipartimento from pbandi_d_dipartimento dd where dd.id_dipartimento = tcs.id_dipartimento),
                                                         (select da.desc_ateneo from pbandi_d_ateneo da where da.id_ateneo = tcs.id_ateneo)))))
        as beneficiario
  from pbandi_t_csp_progetto tcp,
       (
select * 
  from pbandi_t_csp_soggetto
 where id_tipo_anagrafica = (
select id_tipo_anagrafica 
  from pbandi_d_tipo_anagrafica 
 where desc_breve_tipo_anagrafica = 'BENEFICIARIO'
                            )
       ) tcs
 where tcp.id_csp_progetto = tcs.id_csp_progetto (+)
        )
order by cup, titolo_progetto, beneficiario