/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/*
*** script da eseguire ad ogni rilascio
*/

update PBANDI_C_VERSIONE
set VERSIONE_DB = '3.32.0';
--

Insert into PBANDI_D_PERMESSO
   (ID_PERMESSO, DESC_BREVE_PERMESSO, DESC_PERMESSO, DT_INIZIO_VALIDITA)
 Values
   (201, 'DASHBOARD', 'Dashboard', TO_DATE('12/14/2023 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_R_PERMESSO_TIPO_ANAGRAF
   (ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA)
 Values
   (201, 2, TO_DATE('12/14/2023 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_R_PERMESSO_TIPO_ANAGRAF
   (ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA)
 Values
   (201, 3, TO_DATE('12/14/2023 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_R_PERMESSO_TIPO_ANAGRAF
   (ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA)
 Values
   (201, 4, TO_DATE('12/14/2023 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_R_PERMESSO_TIPO_ANAGRAF
   (ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA)
 Values
   (201, 5, TO_DATE('12/14/2023 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_R_PERMESSO_TIPO_ANAGRAF
   (ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA)
 Values
   (201, 10, TO_DATE('12/14/2023 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_R_PERMESSO_TIPO_ANAGRAF
   (ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA)
 Values
   (201, 11, TO_DATE('12/14/2023 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_R_PERMESSO_TIPO_ANAGRAF
   (ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA)
 Values
   (201, 23, TO_DATE('12/14/2023 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_R_PERMESSO_TIPO_ANAGRAF
   (ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA)
 Values
   (201, 24, TO_DATE('12/14/2023 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_R_PERMESSO_TIPO_ANAGRAF
   (ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA)
 Values
   (201, 25, TO_DATE('12/14/2023 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_R_PERMESSO_TIPO_ANAGRAF
   (ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA)
 Values
   (201, 26, TO_DATE('12/14/2023 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));

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