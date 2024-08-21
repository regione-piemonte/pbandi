/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

------------------------------------------------------
-- PBANDI_D_TEMPLATE_NOTIFICA t;
------------------------------------------------------
INSERT INTO PBANDI_D_TEMPLATE_NOTIFICA
(ID_TEMPLATE_NOTIFICA, DESCR_BREVE_TEMPLATE_NOTIFICA, COMP_SUBJECT, COMP_MESSAGE)
 VALUES (
 16, 'NotificaRespingiAffidamento', 'Notifica respingi affidamento',
 'Si notifica che in data ${data_respingi_affidamento} l''ente competente ha respinto l''affidamento con oggetto "${oggetto_affidamento}" con importo aggiudicato di euro ${imp_aggiudicato_affidamento}.
  <br />Note: ${note_respingi_affidamento}
  <br />Per chiarimenti o informazioni contattare l''assistenza o l''ente competente.'
  );
  
 
 
 INSERT INTO PBANDI_D_TEMPLATE_NOTIFICA
 (ID_TEMPLATE_NOTIFICA, DESCR_BREVE_TEMPLATE_NOTIFICA, COMP_SUBJECT, COMP_MESSAGE)
  VALUES (
 17, 'NotificaRichiestaIntegrazioneAffidamento', 'Notifica richiesta integrazione affidamento',
 'Si notifica che in data ${data_ric_integraz_affidamento} l''ente competente ha richiesto un''integrazione all''affidamento con oggetto ""${oggetto_affidamento}"" con importo aggiudicato di euro ${imp_aggiudicato_affidamento}. 
<br />Note: ${note_ric_integraz_affidamento}
<br />Per chiarimenti o informazioni contattare l''assistenza o l''ente competente.'
);

------------------------------------------------------
--PBANDI_D_METADATA_NOTIFICA t;
------------------------------------------------------
INSERT INTO PBANDI_D_METADATA_NOTIFICA (ID_METADATA_NOTIFICA, NOME_PARAMETRO)
 VALUES (11, 'DATA_RESPINGI_AFFIDAMENTO');
INSERT INTO PBANDI_D_METADATA_NOTIFICA (ID_METADATA_NOTIFICA, NOME_PARAMETRO)
VALUES (12, 'OGGETTO_AFFIDAMENTO');
INSERT INTO PBANDI_D_METADATA_NOTIFICA (ID_METADATA_NOTIFICA, NOME_PARAMETRO)
 VALUES (13, 'IMP_AGGIUDICATO_AFFIDAMENTO');
INSERT INTO PBANDI_D_METADATA_NOTIFICA (ID_METADATA_NOTIFICA, NOME_PARAMETRO)
VALUES (14, 'NOTE_RESPINGI_AFFIDAMENTO');
INSERT INTO PBANDI_D_METADATA_NOTIFICA (ID_METADATA_NOTIFICA, NOME_PARAMETRO)
 VALUES (15, 'DATA_RIC_INTEGRAZ_AFFIDAMENTO');
INSERT INTO PBANDI_D_METADATA_NOTIFICA (ID_METADATA_NOTIFICA, NOME_PARAMETRO)
VALUES (16, 'NOTE_RIC_INTEGRAZ_AFFIDAMENTO');

------------------------------------------------------
--PBANDI_R_PLACEHOLDER_NOTIFICA t;
------------------------------------------------------
INSERT INTO PBANDI_R_PLACEHOLDER_NOTIFICA (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
 VALUES (16, 11,'${data_respingi_affidamento}');
INSERT INTO PBANDI_R_PLACEHOLDER_NOTIFICA (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
VALUES (16, 12,'${oggetto_affidamento}');
INSERT INTO PBANDI_R_PLACEHOLDER_NOTIFICA (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
VALUES (16, 13,'${imp_aggiudicato_affidamento}');
INSERT INTO PBANDI_R_PLACEHOLDER_NOTIFICA (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
VALUES (16, 14,'${note_respingi_affidamento}');
INSERT INTO PBANDI_R_PLACEHOLDER_NOTIFICA (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
 VALUES (17, 15,'${data_ric_integraz_affidamento}');
INSERT INTO PBANDI_R_PLACEHOLDER_NOTIFICA (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
VALUES (17, 12,'${oggetto_affidamento}');
INSERT INTO PBANDI_R_PLACEHOLDER_NOTIFICA (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
VALUES (17, 13,'${imp_aggiudicato_affidamento}');
INSERT INTO PBANDI_R_PLACEHOLDER_NOTIFICA (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
VALUES (17, 16,'${note_ric_integraz_affidamento}');

COMMIT;
--
/*
*** script da eseguire ad ogni rilascio
*/
--
update PBANDI_C_VERSIONE
set VERSIONE_DB = '3.12.0';

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

