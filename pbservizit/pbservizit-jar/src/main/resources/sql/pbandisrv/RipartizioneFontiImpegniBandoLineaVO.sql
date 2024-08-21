/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select id_impegno,
progr_bando_linea_intervento,
desc_impegno,
anno_impegno,
anno_esercizio,
numero_impegno,
anno_perente,
numero_perente,
disponibilita_liquidare,
importo_attuale_impegno,
id_tipo_fondo,
desc_tipo_fondo,
desc_sogg_finanziatore,
dt_provvedimento,
id_soggetto_finanziatore,
id_capitolo,
numero_capitolo,
cig
from (
    select distinct i.id_impegno,
           ribl.progr_bando_linea_intervento,
           i.desc_impegno,
           i.anno_impegno,
           i.anno_esercizio,
           i.numero_impegno,
           i.anno_perente,
           i.numero_perente,
           i.disponibilita_liquidare,
           i.importo_attuale_impegno,
           i.cig_impegno as cig,
           cap.id_tipo_fondo_cap as id_tipo_fondo,
           cap.id_capitolo,
           cap.numero_capitolo,
           cap.desc_tipo_fondo_cap as desc_tipo_fondo,
           fonte.id_soggetto_finanziatore, 
           fonte.desc_sogg_finanziatore,
           provv.dt_provvedimento as dt_provvedimento
      from pbandi_t_impegno i,
           pbandi_r_impegno_bando_linea ribl,
           PBANDI_R_BANDO_LINEA_INTERVENT rbli,
      ( /* capitolo */
        select distinct 
               cap.id_capitolo,
        	   cap.numero_capitolo,
               cap.id_ente_competenza as id_ente_cap,
               tipFondo.id_tipo_fondo as id_tipo_fondo_cap,
               tipFondo.desc_tipo_fondo as desc_tipo_fondo_cap
          from pbandi_t_capitolo cap,
               pbandi_d_tipo_fondo tipFondo
         where cap.id_tipo_fondo = tipfondo.id_tipo_fondo
           and NVL(TRUNC(tipFondo.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
      ) cap,
      ( /* provvedimento */
        select provv.id_provvedimento,
               provv.id_ente_competenza as id_ente_provv,
               recs.id_soggetto as id_soggetto_provv,
               provv.dt_provvedimento
          from pbandi_t_provvedimento provv,
               pbandi_r_ente_competenza_sogg recs
         where provv.id_ente_competenza = recs.id_ente_competenza
          and NVL(TRUNC(recs.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
      ) provv,
      ( /* fonte */
          select fondo.id_soggetto_finanziatore, desc_sogg_finanziatore, id_tipo_fondo, id_bando
          from pbandi_d_tipo_sogg_finanziat tipoFondo,
               pbandi_d_soggetto_finanziatore fondo, 
               pbandi_r_bando_sogg_finanziat soggfin
          where fondo.id_tipo_sogg_finanziat = tipoFondo.id_tipo_sogg_finanziat
          and desc_breve_tipo_sogg_finanziat <> 'COFPOR' 
          and desc_breve_tipo_sogg_finanziat <> 'OTHFIN' 
          and NVL(TRUNC(fondo.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
          and fondo.id_soggetto_finanziatore = soggfin.id_soggetto_finanziatore
      ) fonte 
    where i.id_capitolo = cap.id_capitolo
      and i.id_provvedimento = provv.id_provvedimento (+)
      and i.id_impegno = ribl.id_impegno
      and rbli.PROGR_BANDO_LINEA_INTERVENTO = ribl.PROGR_BANDO_LINEA_INTERVENTO
      and fonte.id_bando = rbli.id_bando
      and fonte.id_tipo_fondo = cap.id_tipo_fondo_cap
      and disponibilita_liquidare > 0 
      and NVL(TRUNC(ribl.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
) order by desc_sogg_finanziatore, anno_perente, numero_perente, disponibilita_liquidare, anno_esercizio, dt_provvedimento asc