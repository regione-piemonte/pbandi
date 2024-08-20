SELECT DT_AGGIUDICAZIONE as dtAggiudicazione , IMPORTO as importo , ID_MOTIVO_ASSENZA_CIG as idMotivoAssenzaCig ,
 	ID_PROCEDURA_AGGIUDICAZ as idProceduraAggiudicaz , IVA as iva , ID_TIPOLOGIA_AGGIUDICAZ as idTipologiaAggiudicaz ,
  	COD_PROC_AGG as codProcAgg , CIG_PROC_AGG as cigProcAgg , ID_UTENTE_INS as idUtenteIns , DESC_PROC_AGG as descProcAgg ,
   	ID_UTENTE_AGG as idUtenteAgg , ID_PROGETTO as idProgetto , DT_INIZIO_VALIDITA as dtInizioValidita ,
   	 DT_FINE_VALIDITA as dtFineValidita from PBANDI_T_PROCEDURA_AGGIUDICAZ 
WHERE ID_PROCEDURA_AGGIUDICAZ = ?