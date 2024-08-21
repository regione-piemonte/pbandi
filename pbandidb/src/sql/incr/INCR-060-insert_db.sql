/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

Insert into PBANDI_D_PERMESSO
   (ID_PERMESSO, DESC_BREVE_PERMESSO, DESC_PERMESSO, DT_INIZIO_VALIDITA, DESC_MENU)
 Values
   (199, 'MENUBOSTORAGE', 'Menu BO Storage', TO_DATE('02/23/2022 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 'BO Storage');
   
Insert into PBANDI_R_PERMESSO_TIPO_ANAGRAF
   (ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA, FLAG_MENU)
 Values
   (199, 14, TO_DATE('11/14/2011 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 'S');
   
   
--PBANDI_D_METODO_TASK
Insert into PBANDI_D_METODO_TASK
   (ID_METODO_TASK, DESCR_BREVE_METODO_TASK, DESCR_METODO_TASK)
 Values
   (35, 'FN_VISUALIZZA_INDICATORI', 'Metodo di controllo per il task VISUALIZZA-INDICATORI');


--PBANDI_D_TASK (Visualizza indicatori di progetto)
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK, ID_METODO_TASK)
 Values
   (80, 'VISUALIZZA-INDICATORI', 'Visualizza indicatori di progetto', 9, 35);
   
-- Inserisco il task nel processo sotto il gateway OR in modo da essere subito disponibile 
Insert into PBANDI_T_STEP_PROCESSO
   (ID_STEP_PROCESSO, ID_TASK_PREC, ID_TASK, ID_PROCESSO)
 Values
   (266, 8, 80, 2);
  
-- Inserisco il ruolo per rendere disponibile l'attività al  Beneficiario e all'Istruttore
Insert into PBANDI_R_STEP_PROCESSO_RUOLO
   (ID_STEP_PROCESSO, ID_RUOLO_DI_PROCESSO)
 Values
   (266, 6);



 
COMMIT;

/*
*** script da eseguire ad ogni rilascio
*/

update PBANDI_C_VERSIONE
set VERSIONE_DB = '3.28.3';

insert into PBANDI_C_ENTITA
(ID_ENTITA, NOME_ENTITA, FLAG_DA_TRACCIARE) 
(select seq_PBANDI_C_ENTITA.nextval,tabs.TABLE_NAME,'S'
from all_tables tabs
where tabs.OWNER = (select decode(instr(user,'_RW'),0,user,replace(user,'_RW',null)) from dual)
and tabs.TABLE_NAME like 'PBANDI_%'
and not exists (select 'x' from pbandi_c_entita where tabs.TABLE_NAME = NOME_ENTITA));

commit;

declare
  cursor curAttr is select col.COLUMN_NAME,en.id_ENTITA,col.TABLE_NAME
                    from all_tab_cols col,PBANDI_C_ENTITA en
                    where col.owner = (select decode(instr(user,'_RW'),0,user,replace(user,'_RW',null)) from dual)
                    and col.TABLE_NAME like 'PBANDI_%'
                    and col.COLUMN_NAME not like 'SYS_%'
                    and col.TABLE_NAME = en.NOME_ENTITA
                    and not exists (select 'x' from PBANDI_C_ATTRIBUTO att 
                                    where col.COLUMN_NAME = att.NOME_ATTRIBUTO
                                    and att.id_entita = en.id_ENTITA);
                                    
  nPosiz  PLS_INTEGER := NULL;                                                                          
begin
  for recAttr in curAttr loop

    BEGIN
      SELECT POSITION
      INTO   nPosiz 
      FROM   all_CONSTRAINTS A,all_CONS_COLUMNS B
      WHERE  A.CONSTRAINT_TYPE = 'P'
      AND    A.TABLE_NAME      = recAttr.TABLE_NAME
      AND    A.CONSTRAINT_NAME = B.CONSTRAINT_NAME
      AND    COLUMN_NAME       = recAttr.COLUMN_NAME;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        nPosiz := NULL;
    END;
    
    insert into PBANDI_C_ATTRIBUTO
    (ID_ATTRIBUTO, 
     NOME_ATTRIBUTO, 
     FLAG_DA_TRACCIARE, 
     ID_ENTITA,
     KEY_POSITION_ID) 
    values
    (SEQ_PBANDI_C_ATTRIBUTO.NEXTVAL,
     recAttr.COLUMN_NAME,
     'N',
     recAttr.id_ENTITA,
     nPosiz);
  end loop;
  COMMIT;
end;
/