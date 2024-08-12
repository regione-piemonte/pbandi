select 
 (  select COUNT(ID_ENTITA)  from PBANDI_T_FILE_ENTITA where ID_FILE=FI.ID_FILE ) entities_associated,
 case when exists ( select 'x' 
          from PBANDI_T_FILE_ENTITA     
            join 
               PBANDI_T_DOCUMENTO_DI_SPESA
                     on PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA = PBANDI_T_FILE_ENTITA.ID_TARGET 
                        and PBANDI_T_FILE_ENTITA.ID_ENTITA=(select ID_ENTITA from PBANDI_C_ENTITA where NOME_ENTITA='PBANDI_T_DOCUMENTO_DI_SPESA')
            join
               PBANDI_R_DOC_SPESA_PROGETTO using(ID_DOCUMENTO_DI_SPESA)    
                  where PBANDI_T_FILE_ENTITA.ID_FILE=FI.ID_FILE  
                        and PBANDI_R_DOC_SPESA_PROGETTO.ID_STATO_DOCUMENTO_SPESA in (select ID_STATO_DOCUMENTO_SPESA 
                                                                       from PBANDI_D_STATO_DOCUMENTO_SPESA 
                                                                       where 
                                                                       DESC_BREVE_STATO_DOC_SPESA not in ('I','R')))
            or
            exists ( select 'x' 
            from PBANDI_T_FILE_ENTITA     
              join 
                 PBANDI_R_PAGAMENTO_DOC_SPESA
                       on pbandi_r_pagamento_doc_spesa.ID_PAGAMENTO= PBANDI_T_FILE_ENTITA.ID_TARGET 
                          and PBANDI_T_FILE_ENTITA.ID_ENTITA=(select ID_ENTITA from PBANDI_C_ENTITA where NOME_ENTITA='PBANDI_T_PAGAMENTO')
              join
                 PBANDI_R_DOC_SPESA_PROGETTO using(ID_DOCUMENTO_DI_SPESA)    
                    where PBANDI_T_FILE_ENTITA.ID_FILE=FI.ID_FILE  
                          and PBANDI_R_DOC_SPESA_PROGETTO.ID_STATO_DOCUMENTO_SPESA in (select ID_STATO_DOCUMENTO_SPESA 
                                                                         from PBANDI_D_STATO_DOCUMENTO_SPESA 
                                                                         where 
                                                                         DESC_BREVE_STATO_DOC_SPESA not in ('I','R')))
         OR EXISTS
        (SELECT 'x'
        FROM PBANDI_T_FILE_ENTITA
        WHERE id_progetto is not null 
        	  and PBANDI_T_FILE_ENTITA.ID_FILE =FI.ID_FILE 
        	  and (ID_ENTITA IN(SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA='PBANDI_T_COMUNICAZ_FINE_PROG')
        		 OR  ID_ENTITA IN(SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA='PBANDI_T_DICHIARAZIONE_SPESA')
        		 OR ID_ENTITA IN(SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA='PBANDI_T_RICHIESTA_EROGAZIONE')
        		 OR ID_ENTITA IN(SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA='PBANDI_T_CONTO_ECONOMICO') )      
        )                                                          
         then 'S'
       else
        'N' end islocked,
fi.nome_file,
fo.dt_inserimento as dt_inserimento_folder,
fi.dt_inserimento as dt_inserimento_file,
FO.DT_AGGIORNAMENTO as DT_AGGIORNAMENTO_FOLDER, 
fi.dt_aggiornamento as  dt_aggiornamento_file,
fi.id_file,
fi.size_file ,
id_folder ,
fo.id_padre,
fo.nome_folder,
fo.id_soggetto_ben,
fo.id_progetto as id_progetto_folder,
id_documento_index,
docindex.id_entita,
docindex.id_target,
DOCINDEX.ID_PROGETTO, 
docindex.num_protocollo,
null as id_file_entita,
null as flag_entita,
null as desc_stato_tipo_doc_index
from pbandi_t_folder fo 
left join pbandi_t_file fi using (id_folder)
left join PBANDI_T_DOCUMENTO_INDEX DOCINDEX using (ID_DOCUMENTO_INDEX)