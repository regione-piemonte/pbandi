/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select distinct p.id_progetto,
       nvl2(gefo.cod_fondo_gefo,'S','N') as flag_gefo
from TMP_NUM_DOMANDA_GEFO gefo,
     pbandi_t_domanda d,
     pbandi_t_progetto p
where d.progr_bando_linea_intervento = gefo.progr_bando_linea_intervento(+) 
  and p.id_domanda = d.id_domanda