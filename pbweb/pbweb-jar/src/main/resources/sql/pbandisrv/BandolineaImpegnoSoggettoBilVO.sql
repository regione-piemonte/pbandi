select  imp.id_impegno,
        imp.id_ente_cap,
        imp.id_ente_provv,
        imp.desc_impegno,
        bli.progr_bando_linea_intervento as progr_bandolinea_intervento,
        bli.nome_bando_linea as nome_bandolinea,
        bli.dotazione_finanziaria,
        imp.anno_impegno,
        imp.numero_impegno,
        nvl(imp.importo_attuale_impegno,0) as importo_attuale_impegno,
        nvl(imp.disponibilita_liquidare,0) as disponibilita_liquidare,
        imp.id_tipo_fondo,
        imp.desc_tipo_fondo
from 
/* bandolinea */
(
    select bl.nome_bando_linea,
           bl.progr_bando_linea_intervento,
           b.dotazione_finanziaria,
           ribl.id_impegno
      from pbandi_r_impegno_bando_linea ribl,
           pbandi_r_bando_linea_intervent bl,
           pbandi_t_bando b
     where ribl.progr_bando_linea_intervento = bl.progr_bando_linea_intervento
       and b.id_bando = bl.id_bando
       and NVL(TRUNC(ribl.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
) bli,    
/* impegno */     
(
    select i.id_impegno,
           i.desc_impegno,
           i.anno_impegno,
           i.numero_impegno,
           i.importo_attuale_impegno,
           i.disponibilita_liquidare,
           cap.id_ente_cap,
           cap.id_tipo_fondo_cap as id_tipo_fondo,
           cap.desc_tipo_fondo_cap as desc_tipo_fondo,
           provv.id_ente_competenza as id_ente_provv
      from pbandi_t_impegno i left outer join pbandi_t_provvedimento provv on i.id_provvedimento = provv.id_provvedimento,
      ( /* capitolo */
        select cap.id_capitolo,
               cap.id_ente_competenza as id_ente_cap,
               tipFondo.id_tipo_fondo as id_tipo_fondo_cap,
               tipFondo.desc_tipo_fondo as desc_tipo_fondo_cap
          from pbandi_t_capitolo cap,
               pbandi_d_tipo_fondo tipFondo
         where cap.id_tipo_fondo = tipfondo.id_tipo_fondo
           and NVL(TRUNC(tipFondo.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
      ) cap
    where i.id_capitolo = cap.id_capitolo
) imp
where imp.id_impegno = bli.id_impegno