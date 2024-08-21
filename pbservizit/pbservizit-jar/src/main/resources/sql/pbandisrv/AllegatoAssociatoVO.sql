/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select r.PROGR_BANDO_LINEA_INTERVENTO, r.ID_ALLEGATO,
       r.FLAG_OBBLIGATORIO, r.FLAG_DIFFERIBILE,
       d.DESCRIZIONE
from pbandi_r_bandi_allegati r, pbandi_d_allegati d
where d.ID_ALLEGATO = r.ID_ALLEGATO