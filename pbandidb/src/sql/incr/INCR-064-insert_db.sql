/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/*
*** script da eseguire ad ogni rilascio
*/

update PBANDI_C_VERSIONE
set VERSIONE_DB = '3.30.1';
--
insert into pbandi_d_metodo_task (ID_METODO_TASK, DESCR_BREVE_METODO_TASK, DESCR_METODO_TASK, FLAG_CHIAMATA_END)
values (36, 'FN_VERIF_RICH_EROG', 'Metodo di controllo per il task VERIF-RICH-EROGAZIONE', '');

insert into pbandi_d_multi_task (ID_MULTI_TASK, DESCR_MULTI_TASK, QUERY_MULTI_TASK)
values (4, 'Ritorna l''elenco delle richieste di erogazione presenti per il progetto', 'select id_richiesta_erogazione from pbandi_t_richiesta_erogazione where id_progetto = ${id_progetto}');

insert into pbandi_d_task (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK, ID_METODO_TASK, ID_MULTI_TASK, ID_METODO_TASK_INTEGR)
values (102, 'VERIF-RICH-EROGAZIONE', 'Verifica della richiesta di erogazione', 9, 36, 4, null);

insert into pbandi_t_step_processo (ID_STEP_PROCESSO, ID_TASK_PREC, ID_TASK, ID_PROCESSO)
values (276, 21, 102, 2);

insert into PBANDI_R_STEP_PROCESSO_RUOLO (ID_STEP_PROCESSO, ID_RUOLO_DI_PROCESSO)
values (276, 1);
insert into PBANDI_R_STEP_PROCESSO_RUOLO (ID_STEP_PROCESSO, ID_RUOLO_DI_PROCESSO)
values (276, 3);
--
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