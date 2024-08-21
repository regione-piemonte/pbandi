/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select attoliq.id_atto_liquidazione,
             attoliq.anno_atto,
             attoliq.id_settore_ente,
             substr(ec.desc_breve_ente, 0, 4) as dir_atto, 
             se.desc_breve_settore as settore_atto,
             soggriten.desc_breve_tipo_sogg_ritenuta as tipo_soggetto,
             tiporiten.desc_breve_tipo_ritenuta as tipo_ritenuta,
             attoliq.imp_non_soggetto_ritenuta as imp_non_soggette,
             tiporiten.perc_aliquota as aliq_irpef,
             situazinps.desc_breve_situazione_inps as dato_inps,
             altracassaprev.desc_breve_tipo_altra_cassa as inps_altra_cassa,
             attivitainps.cod_attivita_inps as cod_attivita,
             altracassa.cod_altra_cassa,
             attoliq.dt_inps_dal as inps_dal,
             attoliq.dt_inps_al as inps_al,
             rischioinail.desc_breve_rischio_inail as rischio_inail,
             'E' as divisa,
             attoliq.note_atto as note,
             attoliq.flag_allegati_fatture as fl_fatture,
             attoliq.flag_allegati_estratto_prov as fl_estr_copia_prov,
             attoliq.flag_allegati_doc_giustificat as fl_doc_giustif,
             attoliq.flag_allegati_dichiarazione as fl_dichiaraz,
             attoliq.testo_allegati_altro as all_altro,
             attoliq.dt_scadenza_atto as data_scadenza,
             attoliq.numero_telefono_liquidatore as nro_tel_liq,
             attoliq.nome_liquidatore as nome_liq,
             attoliq.nome_dirigente_liquidatore as nome_dir,
             attoliq.id_beneficiario_bilancio,
             attoliq.id_dati_pagamento_atto,
             attoliq.desc_atto,
             causaleerog.desc_causale,
             attoliq.importo_imponibile,
             attoliq.imp_non_soggetto_ritenuta,
             attoliq.id_aliquota_ritenuta
 from   pbandi_t_atto_liquidazione  attoliq,
         (
         select id_situazione_inps, desc_situazione_inps, desc_breve_situazione_inps from pbandi_d_situazione_inps
          where NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
         ) situazinps,
         (
         select id_tipo_altra_cassa_prev, desc_tipo_altra_cassa, desc_breve_tipo_altra_cassa from pbandi_d_tipo_altra_cassa_prev
          where NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
         ) altracassaprev,
         (
         select id_attivita_inps, desc_attivita_inps, cod_attivita_inps from pbandi_d_attivita_inps        
          where NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
         ) attivitainps,
         (
         select id_rischio_inail, desc_rischio_inail, desc_breve_rischio_inail from pbandi_d_rischio_inail        
          where NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
         ) rischioinail,
         (
         select id_aliquota_ritenuta, desc_breve_tipo_sogg_ritenuta
          from pbandi_d_aliquota_ritenuta al, pbandi_d_tipo_sogg_ritenuta ts
          where
          al.id_tipo_sogg_ritenuta = ts.id_tipo_sogg_ritenuta and        
           NVL(TRUNC(al.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE) and
           NVL(TRUNC(ts.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
         ) soggriten,
         (
         select id_aliquota_ritenuta, desc_breve_tipo_ritenuta, perc_aliquota
          from pbandi_d_aliquota_ritenuta al, pbandi_d_tipo_ritenuta ts
          where
          al.id_tipo_ritenuta = ts.id_tipo_ritenuta and       
          NVL(TRUNC(al.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE) and
          NVL(TRUNC(ts.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
         ) tiporiten,
        (
        select cod_altra_cassa, id_altra_cassa_previdenz from pbandi_d_altra_cassa_previdenz
        ) altracassa,
        pbandi_t_ente_competenza ec,
        pbandi_d_settore_ente se,
        (
        select id_causale_erogazione, desc_causale from pbandi_d_causale_erogazione
        ) causaleerog
   where
     situazinps.id_situazione_inps (+)= attoliq.id_situazione_inps and
     altracassaprev.id_tipo_altra_cassa_prev (+) = attoliq.id_tipo_altra_cassa_prev and
     attivitainps.id_attivita_inps (+) =  attoliq.id_attivita_inps and
     rischioinail.id_rischio_inail (+) =  attoliq.id_rischio_inail  and
     soggriten.id_aliquota_ritenuta (+) =  attoliq.id_aliquota_ritenuta and 
     tiporiten.id_aliquota_ritenuta (+) =  attoliq.id_aliquota_ritenuta and
     causaleerog.id_causale_erogazione (+) =  attoliq.id_causale_erogazione and
     altracassa.id_altra_cassa_previdenz (+) =  attoliq.id_altra_cassa_previdenz and
     attoliq.id_settore_ente = se.id_settore_ente and
     se.id_ente_competenza = ec.id_ente_competenza