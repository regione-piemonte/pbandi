/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SET DEFINE OFF;
INSERT INTO PBANDI_D_TIPO_DICHIARAZ_SPESA 
(ID_TIPO_DICHIARAZ_SPESA, DESC_BREVE_TIPO_DICHIARA_SPESA, DESC_TIPO_DICHIARAZIONE_SPESA, 
 DT_INIZIO_VALIDITA, DT_FINE_VALIDITA) 
VALUES 
(3,'FC','FINALE CON COMUNICAZIONE',to_date('01/01/2009','dd/mm/yyyy'),sysdate);

INSERT INTO PBANDI_D_TIPO_DICHIARAZ_SPESA 
(ID_TIPO_DICHIARAZ_SPESA, DESC_BREVE_TIPO_DICHIARA_SPESA, DESC_TIPO_DICHIARAZIONE_SPESA, 
 DT_INIZIO_VALIDITA, DT_FINE_VALIDITA) 
VALUES 
(4,'IN','INTEGRATIVA',to_date('01/01/2009','dd/mm/yyyy'),sysdate);

INSERT INTO PBANDI_C_TIPO_DOCUMENTO_INDEX 
(ID_TIPO_DOCUMENTO_INDEX, DESC_BREVE_TIPO_DOC_INDEX, DESC_TIPO_DOC_INDEX) 
VALUES 
(17,'CFP','COMUNICAZIONE DI FINE PROGETTO');


update pbandi_d_tipo_dichiaraz_spesa
set flag_selezionabile = 'S'
where ID_TIPO_DICHIARAZ_SPESA in (1,2);

insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
(ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
(select ID_TIPO_ANAGRAFICA,0,17
 from PBANDI_D_TIPO_ANAGRAFICA
 where  ID_TIPO_ANAGRAFICA not in (1,17));
 
insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT 
(ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
(select 17,PROGR_BANDO_LINEA_INTERVENTO, 0
 from PBANDI_R_BANDO_LINEA_INTERVENT);
 
commit;

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

