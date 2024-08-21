/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select pr.id_progetto, imp.id_impegno
from pbandi_t_impegno imp,
     pbandi_t_soggetto sog,
     pbandi_t_ente_giuridico giu,
     pbandi_r_soggetto_progetto spr,
     pbandi_t_progetto pr
where sog.codice_fiscale_soggetto = imp.codice_fiscale
  and giu.id_soggetto = sog.id_soggetto
  and spr.id_ente_giuridico = giu.ID_ENTE_GIURIDICO
  and spr.ID_TIPO_ANAGRAFICA = 1
  and spr.ID_TIPO_BENEFICIARIO <> 4
  and spr.ID_PROGETTO = pr.id_progetto
order by pr.codice_visualizzato