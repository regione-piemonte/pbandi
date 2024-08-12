select  bandolinea.nome_bando_linea as nome_bandolinea,
        bandolinea.progr_bando_linea_intervento as progr_bandolinea_intervento,
        bandolinea.id_ente_competenza as id_ente_bandol,
        bandolinea.id_ruolo_ente_competenza as id_ruolo_ente_bandol,
        bandolinea.desc_breve_ruolo_ente as desc_breve_ruolo_ente_bandol,
        bandolinea.desc_ruolo_ente as desc_ruolo_ente_bandol,
        bandolinea.dotazione_finanziaria,
        nvl(impegno.id_impegno,-1) as id_impegno,
        impegno.desc_impegno,
        impegno.anno_impegno,
        impegno.numero_impegno,
        nvl(impegno.importo_attuale_impegno,0) as importo_attuale_impegno,
        nvl(impegno.disponibilita_liquidare,0) as disponibilita_liquidare,
        impegno.id_tipo_fondo,
        impegno.desc_tipo_fondo
from (
/* bandolinea */    
    select distinct bl.nome_bando_linea,
           bl.progr_bando_linea_intervento,
           ble.id_ente_competenza,
           ble.id_ruolo_ente_competenza,
           re.desc_breve_ruolo_ente,
           re.desc_ruolo_ente,
           b.dotazione_finanziaria
      from pbandi_r_bando_linea_intervent bl,
           pbandi_t_bando b,
           pbandi_r_bando_linea_ente_comp ble,
           pbandi_d_ruolo_ente_competenza re
     where b.id_bando = bl.id_bando
       and bl.progr_bando_linea_intervento = ble.progr_bando_linea_intervento
       and ble.id_ruolo_ente_competenza = re.id_ruolo_ente_competenza
) bandolinea LEFT OUTER JOIN 
/* impegni */   
(
    select distinct i.id_impegno,
           ribl.progr_bando_linea_intervento,
           i.desc_impegno,
           i.anno_impegno,
           i.numero_impegno,
           i.importo_attuale_impegno,
           i.totale_liquidato_impegno,
           i.disponibilita_liquidare,
           cap.id_tipo_fondo_cap as id_tipo_fondo,
           cap.desc_tipo_fondo_cap as desc_tipo_fondo
      from pbandi_t_impegno i 
          left outer join pbandi_t_provvedimento provv on i.id_provvedimento = provv.id_provvedimento
          left outer join  pbandi_r_impegno_bando_linea ribl on i.id_impegno = ribl.id_impegno,
      ( /* capitolo */
        select distinct cap.id_capitolo,
               cap.id_ente_competenza as id_ente_cap,
               tipFondo.id_tipo_fondo as id_tipo_fondo_cap,
               tipFondo.desc_tipo_fondo as desc_tipo_fondo_cap
          from pbandi_t_capitolo cap,
               pbandi_d_tipo_fondo tipFondo
         where cap.id_tipo_fondo = tipfondo.id_tipo_fondo
           and NVL(TRUNC(tipFondo.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
      ) cap
    where i.id_capitolo = cap.id_capitolo
    and NVL(TRUNC(ribl.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
) impegno
on bandolinea.progr_bando_linea_intervento = impegno.progr_bando_linea_intervento 
