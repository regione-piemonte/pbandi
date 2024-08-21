/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select distinct
       imp.id_impegno,
       imp.anno_impegno,
       imp.numero_impegno,
       imp.anno_esercizio,
       imp.desc_impegno,
       imp.importo_iniziale_impegno,
       imp.importo_attuale_impegno,
       imp.totale_liquidato_impegno,
       imp.disponibilita_liquidare,
       imp.totale_quietanzato_impegno,
       imp.dt_inserimento,
       imp.dt_aggiornamento, 
       imp.cup_impegno,
       imp.cig_impegno,
       imp.numero_capitolo_origine,       
       imp.codice_fiscale,
       imp.ragsoc,
       /* stato impegno */
       stimp.id_stato_impegno,
       stimp.desc_breve_stato_impegno,
       stimp.desc_stato_impegno,
       /* provvedimento */
       provv.id_provvedimento,
       provv.anno_provvedimento,
       provv.numero_provvedimento,
       provv.dt_provvedimento,
       /* tipo provvedimento */
       provv.id_tipo_provvedimento,
       provv.desc_breve_tipo_provvedimento,
       provv.desc_tipo_provvedimento,
       /* ente competenza provvedimento */
       provv.id_ente_competenza as id_ente_competenza_provv,
       provv.desc_ente as desc_ente_provv,
       provv.desc_breve_ente as desc_breve_ente_provv,
       provv.id_tipo_ente_competenza as id_tipo_ente_provv,
       provv.desc_tipo_ente_competenza as desc_tipo_ente_provv,
       provv.desc_breve_tipo_ente_competenz as desc_breve_tipo_ente_provv,
       /* capitolo */
       cap.id_capitolo,
       cap.numero_capitolo,
       cap.numero_articolo,
       cap.desc_capitolo,
       /* tipo fondo capitolo */
       cap.id_tipo_fondo,
       cap.desc_breve_tipo_fondo,
       cap.desc_tipo_fondo ,
       /* ente competenza capitolo */
       cap.id_ente_competenza_cap,
       cap.desc_ente_cap,
       cap.desc_breve_ente_cap,
       cap.id_tipo_ente_competenza as id_tipo_ente_cap,
       cap.desc_tipo_ente_competenza as desc_tipo_ente_cap,
       cap.desc_breve_tipo_ente_competenz as desc_breve_tipo_ente_cap,
       /* perente */
       imp.anno_perente,
       imp.numero_perente
from pbandi_t_impegno imp, 
     pbandi_d_stato_impegno stImp,
(
  select distinct
         prov.id_provvedimento,
         prov.anno_provvedimento,
         prov.numero_provvedimento,
         prov.dt_provvedimento,
         /* tipo provvedimento */
         tipprov.id_tipo_provvedimento,
         tipprov.desc_breve_tipo_provvedimento,
         tipprov.desc_tipo_provvedimento,
         /* ente competenza provvedimento */
         ente.id_ente_competenza,
         ente.desc_ente,
         ente.desc_breve_ente,
         ente.id_tipo_ente_competenza,
         tipoEnte.desc_tipo_ente_competenza,
         tipoEnte.desc_breve_tipo_ente_competenz
  from pbandi_t_provvedimento prov 
  			 left outer join pbandi_d_tipo_provvedimento tipProv on  prov.id_tipo_provvedimento = tipProv.id_tipo_provvedimento,
       pbandi_t_ente_competenza ente,
       pbandi_d_tipo_ente_competenza tipoEnte
  where prov.id_ente_competenza = ente.id_ente_competenza
    and ente.id_tipo_ente_competenza = tipoente.id_tipo_ente_competenza
    and NVL(TRUNC(tipProv.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
    and NVL(TRUNC(tipoente.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
) provv,
(
  select distinct cap.id_capitolo,
       cap.numero_capitolo,
       cap.numero_articolo,
       cap.desc_capitolo,
       tipfondo.id_tipo_fondo,
       tipfondo.desc_breve_tipo_fondo,
       tipfondo.desc_tipo_fondo,
       ente.id_ente_competenza as id_ente_competenza_cap,
       ente.desc_ente as desc_ente_cap,
       ente.desc_breve_ente as desc_breve_ente_cap,
       ente.id_tipo_ente_competenza,
       tipoente.desc_tipo_ente_competenza,
       tipoEnte.desc_breve_tipo_ente_competenz
from  pbandi_t_capitolo cap left outer join pbandi_t_ente_competenza ente on  cap.id_ente_competenza = ente.id_ente_competenza,
      pbandi_d_tipo_fondo tipfondo,
       pbandi_d_tipo_ente_competenza tipoEnte
where cap.id_tipo_fondo = tipfondo.id_tipo_fondo 
  and ente.id_tipo_ente_competenza = tipoente.id_tipo_ente_competenza
  and NVL(TRUNC(tipFondo.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
) cap
where 
  /* capitolo */
  imp.id_capitolo = cap.id_capitolo
  /* stato impegno */
  and imp.id_stato_impegno = stImp.id_stato_impegno
  /* provvedimento */
  and imp.id_provvedimento = provv.id_provvedimento (+)
  /* data fine validita */
  and NVL(TRUNC(stimp.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)