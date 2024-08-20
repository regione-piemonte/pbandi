select ID_TIPO_AFFIDAMENTO as idTipoAffidamento , ID_TIPOLOGIA_AGGIUDICAZ as idTipologiaAggiudicaz ,
 ID_NORMA as idNorma , ID_MODELLO_CD as idModelloCd , RIF_CHECKLIST_AFFIDAMENTI as rifChecklistAffidamenti , 
 ID_AFFIDAMENTO_CHECKLIST as idAffidamentoChecklist , ID_TIPO_APPALTO as idTipoAppalto ,
  ID_MODELLO_CL as idModelloCl , SOPRA_SOGLIA as sopraSoglia, ID_LINEA_DI_INTERVENTO as idLineaDiIntervento from PBANDI_T_AFFIDAMENTO_CHECKLIST 
  where ID_TIPO_AFFIDAMENTO = ? and ID_TIPOLOGIA_AGGIUDICAZ = ? 
  and ID_NORMA = ?
  and ID_TIPO_APPALTO = ?

