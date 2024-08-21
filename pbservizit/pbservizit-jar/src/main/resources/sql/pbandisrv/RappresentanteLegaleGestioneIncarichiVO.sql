/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select DISTINCT PBANDI_T_SOGGETTO.id_soggetto as id_soggetto_beneficiario,
       PBANDI_R_SOGGETTI_CORRELATI.id_soggetto as id_soggetto_delegante,
       PBANDI_D_TIPO_SOGG_CORRELATO.desc_breve_tipo_sogg_correlato as desc_breve_tipo_sogg_correlato,
       PBANDI_D_TIPO_SOGG_CORRELATO.desc_tipo_soggetto_correlato as desc_tipo_soggetto_correlato,
       PBANDI_D_TIPO_SOGG_CORRELATO.id_tipo_soggetto_correlato as id_tipo_soggetto_correlato,
       PBANDI_T_SOGGETTO.codice_fiscale_soggetto as codice_fiscale_beneficiario
from PBANDI_T_SOGGETTO, PBANDI_D_TIPO_SOGGETTO,
     PBANDI_R_SOGGETTI_CORRELATI, PBANDI_D_TIPO_SOGG_CORRELATO
where PBANDI_T_SOGGETTO.id_tipo_soggetto = PBANDI_D_TIPO_SOGGETTO.id_tipo_soggetto
  and PBANDI_D_TIPO_SOGGETTO.desc_breve_tipo_soggetto = 'EG'
  and PBANDI_R_SOGGETTI_CORRELATI.id_soggetto_ente_giuridico = PBANDI_T_SOGGETTO.id_soggetto
  and PBANDI_R_SOGGETTI_CORRELATI.id_tipo_soggetto_correlato = PBANDI_D_TIPO_SOGG_CORRELATO.id_tipo_soggetto_correlato
  and PBANDI_D_TIPO_SOGG_CORRELATO.desc_breve_tipo_sogg_correlato = 'Rappr. Leg.'
  and nvl(trunc(PBANDI_R_SOGGETTI_CORRELATI.DT_FINE_VALIDITA), trunc(sysdate+1)) > trunc(sysdate)
  