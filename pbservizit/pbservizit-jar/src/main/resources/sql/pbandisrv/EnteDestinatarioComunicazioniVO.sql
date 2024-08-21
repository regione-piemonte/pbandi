/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select ec.id_ente_competenza, ec.desc_ente, ec.desc_breve_ente, p.id_progetto
from pbandi_t_progetto p, pbandi_t_domanda d, 
     pbandi_r_bando_linea_ente_comp bec, pbandi_t_ente_competenza ec
where p.id_domanda = d.id_domanda
  and d.progr_bando_linea_intervento = bec.progr_bando_linea_intervento
  and bec.id_ente_competenza = ec.id_ente_competenza
  and bec.id_ruolo_ente_competenza = 1
