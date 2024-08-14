select distinct ID_PROGETTO as idProgetto , 
CODICE_PROGETTO as codiceProgetto
 from PBANDI_T_PREVIEW_DETT_PROP_CER 
 where ID_PROPOSTA_CERTIFICAZ = ?