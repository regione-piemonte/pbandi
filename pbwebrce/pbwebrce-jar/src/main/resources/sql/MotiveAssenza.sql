select ID_MOTIVO_ASSENZA_CIG as idMotivoAssenzaCig , DESC_MOTIVO_ASSENZA_CIG as descMotivoAssenzaCig ,
 DESC_BREVE_MOTIVO_ASSENZA_CIG as descBreveMotivoAssenzaCig , DT_INIZIO_VALIDITA           
    as dtInizioValidita , DT_FINE_VALIDITA as dtFineValidita , TC22 as tc22 
    from PBANDI_D_MOTIVO_ASSENZA_CIG 
    where  ( trunc(sysdate) >= nvl(trunc(DT_INIZIO_VALIDITA), trunc(sysdate) -1)            
      and trunc(sysdate) < nvl(trunc(DT_FINE_VALIDITA), trunc(sysdate) +1) )  
      ORDER BY DESC_MOTIVO_ASSENZA_CIG ASC
