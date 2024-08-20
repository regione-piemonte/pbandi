select ID_FILE_ENTITA as idFileEntita , ID_ENTITA as idEntita , 
ID_TARGET as idTarget , ID_PROGETTO as idProgetto , 
ID_FILE as idFile , DT_ENTITA as dtEntita , 
FLAG_ENTITA as flagEntita from 
PBANDI_T_FILE_ENTITA where ID_FILE_ENTITA = ?
