select ID_PROCESSO_BATCH as idProcessoBatch , DT_INIZIO_ELABORAZIONE as dtInizioElaborazione ,
 ID_NOME_BATCH as idNomeBatch , DT_FINE_ELABORAZIONE as dtFineElaborazione , 
 FLAG_ESITO as flagEsito 
 from PBANDI_L_PROCESSO_BATCH 
 where ID_NOME_BATCH = ?
  ORDER BY DT_INIZIO_ELABORAZIONE DESC