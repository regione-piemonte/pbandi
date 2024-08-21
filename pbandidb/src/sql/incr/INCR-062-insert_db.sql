/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/*
*** script da eseguire ad ogni rilascio
*/

update PBANDI_C_VERSIONE
set VERSIONE_DB = '3.28.5';



-------------------
INSERT INTO PBANDI.PBANDI_C_REGOLA (ID_REGOLA, DESC_BREVE_REGOLA, DESC_REGOLA, DT_INIZIO_VALIDITA, DT_FINE_VALIDITA)
VALUES(79, 'BR79', 'Validazione mensilità in Validazione della spesa', TIMESTAMP '2022-05-24 00:00:00.000000', NULL);

insert into pbandi_d_nome_batch (ID_NOME_BATCH, NOME_BATCH, DESC_BATCH)
values (101, 'PBAN-XMLBATWS', 'Caricamento XML Main WS');

INSERT INTO PBANDI.PBANDI_D_NOME_BATCH
(ID_NOME_BATCH, NOME_BATCH, DESC_BATCH)
VALUES(100, 'PBAN-ACQ-DICH-SPESA-WS', 'Procedura per l''acquisizione della dich. di spesa e relativi documenti per il bando Welfare Sanità');

INSERT INTO PBANDI.PBANDI_D_ERRORE_BATCH
(CODICE_ERRORE, DESCRIZIONE, FLAG_NOTIFICA_MAIL)
VALUES('E183', 'Errore nella ricerca del progetto o del soggetto beneficiario', NULL);

INSERT INTO PBANDI.PBANDI_D_ERRORE_BATCH
(CODICE_ERRORE, DESCRIZIONE, FLAG_NOTIFICA_MAIL)
VALUES('W109', 'Il numero delle mensilità rendicontate è superiore al limite consentito', NULL);

INSERT INTO PBANDI.PBANDI_D_ERRORE_BATCH
(CODICE_ERRORE, DESCRIZIONE, FLAG_NOTIFICA_MAIL)
VALUES('E184', 'Errore nell''inserimento della dichiarazione di spesa', NULL);

INSERT INTO PBANDI.PBANDI_D_ERRORE_BATCH
(CODICE_ERRORE, DESCRIZIONE, FLAG_NOTIFICA_MAIL)
uVALUES('E185', 'Errore nell''inserimento del documento di spesa', NULL);

INSERT INTO PBANDI.PBANDI_D_ERRORE_BATCH
(CODICE_ERRORE, DESCRIZIONE, FLAG_NOTIFICA_MAIL)
VALUES('E186', 'Errore nell''inserimento del pagamento', NULL);

INSERT INTO PBANDI.PBANDI_D_ERRORE_BATCH
(CODICE_ERRORE, DESCRIZIONE, FLAG_NOTIFICA_MAIL)
VALUES('E187', 'Errore nell''inserimento dei file relativi al documento di spesa o al pagamento', NULL);

INSERT INTO PBANDI.PBANDI_D_ERRORE_BATCH
(CODICE_ERRORE, DESCRIZIONE, FLAG_NOTIFICA_MAIL)
VALUES('E188', 'Errore nell''inserimento del contratto del fornitore', NULL);



Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK, ID_METODO_TASK)
 Values
   (81, 'DICH-DI-SPESA-CULTURA', 'RENDICONTAZIONE CULTURA', 9, 10);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK, ID_METODO_TASK)
 Values
   (82, 'DICH-SPESA-INTEGRATIVA-CULTURA', 'Rendicontazione integrativa', 9, 11);
COMMIT;


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