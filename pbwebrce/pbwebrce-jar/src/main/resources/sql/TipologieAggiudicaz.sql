select alias0.*
 from  ( select * from PBANDI_D_TIPOLOGIA_AGGIUDICAZ
  where  ( trunc(sysdate) >= nvl(trunc(DT_INIZIO_VALIDITA), trunc(sysdate) -1) 
  and trunc(sysdate) < nvl(trunc(DT_FINE_VALIDITA), trunc(sysdate) +1) )  
  ORDER BY DESC_TIPOLOGIA_AGGIUDICAZIONE , DESC_TIPOLOGIA_AGGIUDICAZIONE ASC ) alias0 , 
   ( select * from PBANDI_R_LINEA_TIPOLOGIA_AGG where ID_LINEA_DI_INTERVENTO = ? ) 
   alias1 where alias0.ID_TIPOLOGIA_AGGIUDICAZ = alias1.ID_TIPOLOGIA_AGGIUDICAZ