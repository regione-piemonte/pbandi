/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SET DEFINE OFF;
Insert into PBANDI_C_TIPO_DOCUMENTO_INDEX
   (ID_TIPO_DOCUMENTO_INDEX, DESC_BREVE_TIPO_DOC_INDEX, DESC_TIPO_DOC_INDEX)
 Values
   (16, 'MVDS', 'MODELLO VALIDAZIONE DELLA SPESA');
COMMIT;

SET DEFINE OFF;
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (2, 0, 16);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (3, 0, 16);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (4, 0, 16);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (5, 0, 16);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (6, 0, 16);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (8, 0, 16);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (10, 0, 16);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (11, 0, 16);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (14, 0, 16);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (16, 0, 16);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (19, 0, 16);
COMMIT;

Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
(ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
(select 16,PROGR_BANDO_LINEA_INTERVENTO, 0
from PBANDI_R_BANDO_LINEA_INTERVENT);

commit;

truncate table PBANDI_R_REGOLA_TIPO_ANAG;
delete PBANDI_R_REGOLA_BANDO_LINEA where ID_REGOLA = 11;
delete PBANDI_C_REGOLA where ID_REGOLA = 11;
commit;
 
insert into PBANDI_C_ENTITA
(ID_ENTITA, NOME_ENTITA, FLAG_DA_TRACCIARE) 
(select seq_PBANDI_C_ENTITA.nextval,tabs.TABLE_NAME,'S'
from all_tables tabs
where tabs.OWNER = (select decode(instr(user,'_RW'),0,user,replace(user,'_RW',null)) from dual)
and tabs.TABLE_NAME like 'PBANDI_%'
and not exists (select 'x' from pbandi_c_entita where tabs.TABLE_NAME = NOME_ENTITA));

update PBANDI_T_RETTIFICA_DI_SPESA
set IMPORTO_RETTIFICA = IMPORTO_RETTIFICA*(-1);

commit;

INSERT INTO PBANDI_D_TIPO_CONFRONTO (ID_TIPO_CONFRONTO, DESC_BREVE_TIPO_CONFRONTO, DESC_TIPO_CONFRONTO,DT_INIZIO_VALIDITA) VALUES (1,'=','uguale',to_date('01/01/2009','dd/mm/yyyy'));
INSERT INTO PBANDI_D_TIPO_CONFRONTO (ID_TIPO_CONFRONTO, DESC_BREVE_TIPO_CONFRONTO, DESC_TIPO_CONFRONTO,DT_INIZIO_VALIDITA) VALUES (2,'<','minore',to_date('01/01/2009','dd/mm/yyyy'));
INSERT INTO PBANDI_D_TIPO_CONFRONTO (ID_TIPO_CONFRONTO, DESC_BREVE_TIPO_CONFRONTO, DESC_TIPO_CONFRONTO,DT_INIZIO_VALIDITA) VALUES (3,'>','maggiore',to_date('01/01/2009','dd/mm/yyyy'));
INSERT INTO PBANDI_D_TIPO_CONFRONTO (ID_TIPO_CONFRONTO, DESC_BREVE_TIPO_CONFRONTO, DESC_TIPO_CONFRONTO,DT_INIZIO_VALIDITA) VALUES (4,'<=','minore o uguale',to_date('01/01/2009','dd/mm/yyyy'));
INSERT INTO PBANDI_D_TIPO_CONFRONTO (ID_TIPO_CONFRONTO, DESC_BREVE_TIPO_CONFRONTO, DESC_TIPO_CONFRONTO,DT_INIZIO_VALIDITA) VALUES (5,'>=','maggiore o uguale',to_date('01/01/2009','dd/mm/yyyy'));
INSERT INTO PBANDI_D_TIPO_CONFRONTO (ID_TIPO_CONFRONTO, DESC_BREVE_TIPO_CONFRONTO, DESC_TIPO_CONFRONTO,DT_INIZIO_VALIDITA) VALUES (6,'<>','diverso',to_date('01/01/2009','dd/mm/yyyy'));
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

