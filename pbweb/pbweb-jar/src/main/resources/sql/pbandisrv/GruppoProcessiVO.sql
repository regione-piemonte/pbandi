select rb.progr_bando_linea_intervento||'+'||tp.versione_processo id, count(*) qty, rb.nome_bando_linea nome, tp.versione_processo old_ver, rb.progr_bando_linea_intervento id_Bando_Linea
 from (                                                                
  select substr(id_istanza_processo,                                   
                instr(id_istanza_processo, '$', -1, 1) -3,             
                3) versione_processo,                                  
         id_domanda,                                                   
         id_istanza_processo                                           
    from pbandi_t_progetto                                             
 ) tp,                                                                 
      pbandi_t_domanda td,                                             
      pbandi_r_bando_linea_intervent rb                                
where tp.id_istanza_processo is not null                               
  and tp.id_domanda = td.id_domanda                                    
  and td.progr_bando_linea_intervento = rb.progr_bando_linea_intervento
group by rb.nome_bando_linea, rb.progr_bando_linea_intervento, tp.versione_processo
order by count(*)