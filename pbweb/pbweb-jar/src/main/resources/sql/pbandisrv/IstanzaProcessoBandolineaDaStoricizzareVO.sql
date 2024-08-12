select 
/* bando linea */
       rb.progr_bando_linea_intervento as id_bandolinea,
       rb.nome_bando_linea as nome_bandolinea,
/* istanze da storicizzare */
       tp.id_storico_migrazione,
       count(*) over ( partition by rb.progr_bando_linea_intervento,tp.uuid_processo ) qty_for_bando_uuid,  
       count(*) over ( partition by rb.progr_bando_linea_intervento ) qty_for_bando,  
       count(*) over ( partition by tp.uuid_processo ) qty_for_uuid,  
       tp.uuid_processo,
       tp.id_istanza_processo,
/* progetto */
       tp.id_progetto,
       tp.codice_visualizzato,
       tp.codice_progetto
 from (                                                                
  select sm.uuid_processo,
        sm.id_storico_migrazione,
         p.id_domanda,       
         p.id_progetto,
         p.codice_visualizzato,
         p.codice_progetto,
         sm.id_istanza_processo                                           
    from pbandi_t_progetto p,
         pbandi_t_storico_migrazione sm
    where p.id_progetto = sm.id_progetto
      and nvl(trunc(sm.dt_fine_validita), trunc(sysdate+1)) > trunc(sysdate)
 ) tp,                                                                 
      pbandi_t_domanda td,                                             
      pbandi_r_bando_linea_intervent rb                                
where tp.id_istanza_processo is not null                               
  and tp.id_domanda = td.id_domanda                                    
  and td.progr_bando_linea_intervento = rb.progr_bando_linea_intervento
