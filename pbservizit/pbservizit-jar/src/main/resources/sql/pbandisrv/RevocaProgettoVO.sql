/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select distinct r.*,
mr.desc_motivo_revoca,
ma.desc_modalita_agevolazione,
ma.desc_breve_modalita_agevolaz,
p.codice_visualizzato
from PBANDI_T_REVOCA r,
     PBANDI_D_MOTIVO_REVOCA mr,
     PBANDI_D_MODALITA_AGEVOLAZIONE ma,
     PBANDI_T_PROGETTO p
where r.id_motivo_revoca = mr.id_motivo_revoca(+)
  and r.id_modalita_agevolazione = ma.id_modalita_agevolazione
  and r.id_progetto = p.id_progetto