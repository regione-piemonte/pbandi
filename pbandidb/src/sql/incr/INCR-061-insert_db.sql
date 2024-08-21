/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/*
*** script da eseguire ad ogni rilascio
*/

update PBANDI_C_VERSIONE
set VERSIONE_DB = '3.28.4';



-------------------
insert into PBANDI_C_ENTITA (ID_ENTITA, NOME_ENTITA, FLAG_DA_TRACCIARE)
values (742, 'PBANDI_R_SUBCONTRATTO_AFFID', 'S');
-------------------
insert into PBANDI_D_ERRORE_BATCH (CODICE_ERRORE, DESCRIZIONE, FLAG_NOTIFICA_MAIL)
values ('E182', 'Errore su caricamento xml per WS - Manca il fornitore su xml', '');
-------------------
insert into PBANDI_T_UTENTE (ID_UTENTE, LOGIN, PASSWORD, CODICE_UTENTE, ID_TIPO_ACCESSO, ID_SOGGETTO, DT_INSERIMENTO, DT_AGGIORNAMENTO, FLAG_CONSENSO_MAIL, EMAIL_CONSENSO, FLAG_AMMIN, FLAG_EDIT_HELP)
values (-20, '', '', 'WS_WELFARE', 5, null, to_date('17-05-2023', 'dd-mm-yyyy'), null, '', '', '', '');





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