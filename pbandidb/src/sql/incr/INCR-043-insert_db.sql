/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/*
-- RET-INDIC-PROG
update pbandi_d_task set id_metodo_task = null where id_task = 39;
update pbandi_d_task set id_tipo_task = 7 where id_task = 39;
update pbandi_t_step_processo set id_task_prec = 10  where id_task = 39;

COMMIT;
*/
---- PBANDI_D_METODO_TASK
Insert into PBANDI_D_METODO_TASK
   (ID_METODO_TASK, DESCR_BREVE_METODO_TASK, DESCR_METODO_TASK)
 Values
   (31, 'FN_RICH_INTEGRAZ_DICH_SPESA', 'Metodo di controllo per il task RICH_INTEGRAZ_DICH_SPESA');
-- PBANDI_D_TASK
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK, ID_METODO_TASK)
 Values
   (56, 'RICH_INTEGRAZ_DICH_SPESA', 'Integrazione dichiarazione di spesa', 5, 31);
-- PBANDI_T_STEP_PROCESSO
Insert into PBANDI_T_STEP_PROCESSO
   (ID_STEP_PROCESSO, ID_TASK_PREC, ID_TASK, ID_PROCESSO)
 Values
   (172, 10, 56, 2);
-- PBANDI_R_STEP_PROCESSO_RUOLO
Insert into PBANDI_R_STEP_PROCESSO_RUOLO
   (ID_STEP_PROCESSO, ID_RUOLO_DI_PROCESSO)
 Values
   (172, 2);
Insert into PBANDI_R_STEP_PROCESSO_RUOLO
   (ID_STEP_PROCESSO, ID_RUOLO_DI_PROCESSO)
 Values
   (172, 4);
Insert into PBANDI_R_STEP_PROCESSO_RUOLO
   (ID_STEP_PROCESSO, ID_RUOLO_DI_PROCESSO)
 Values
   (172, 9);

COMMIT;
--

-- PBANDI_D_TEMPLATE_NOTIFICA 
 INSERT INTO PBANDI_D_TEMPLATE_NOTIFICA
 (ID_TEMPLATE_NOTIFICA, DESCR_BREVE_TEMPLATE_NOTIFICA, COMP_SUBJECT, COMP_MESSAGE)
  VALUES (
 18, 'NotificaRichiestaIntegrazioneDichiarazione', 'Notifica richiesta integrazione dichiarazione di spesa',
 'Si notifica che in data ${data_richiesta_integraz_dic} l''ente competente ha richiesto un''integrazione alla dichiarazione di spesa num. ${num_dichiarazione_di_spesa} del ${data_dichiarazione_di_spesa}. 
 <br />Note: ${note_richiesta_integraz_dic}
<br />Per chiarimenti o informazioni contattare l''assistenza o l''ente competente.'
);
--PBANDI_D_METADATA_NOTIFICA
INSERT INTO PBANDI_D_METADATA_NOTIFICA (ID_METADATA_NOTIFICA, NOME_PARAMETRO)
 VALUES (17, 'DATA_RICHIESTA_INTEGRAZ_DIC');
 INSERT INTO PBANDI_D_METADATA_NOTIFICA (ID_METADATA_NOTIFICA, NOME_PARAMETRO)
 VALUES (18, 'NOTE_RICHIESTA_INTEGRAZ_DIC');

--PBANDI_R_PLACEHOLDER_NOTIFICA
INSERT INTO PBANDI_R_PLACEHOLDER_NOTIFICA (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
 VALUES (18, 17,'${data_richiesta_integraz_dic}');
INSERT INTO PBANDI_R_PLACEHOLDER_NOTIFICA (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
VALUES (18, 4,'${num_dichiarazione_di_spesa}');
INSERT INTO PBANDI_R_PLACEHOLDER_NOTIFICA (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
VALUES (18, 3,'${data_dichiarazione_di_spesa}');
INSERT INTO PBANDI_R_PLACEHOLDER_NOTIFICA (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
VALUES (18, 18,'${note_richiesta_integraz_dic}');
--
-- PBANDI_D_TEMPLATE_NOTIFICA
INSERT INTO PBANDI_D_TEMPLATE_NOTIFICA
 (ID_TEMPLATE_NOTIFICA, DESCR_BREVE_TEMPLATE_NOTIFICA, COMP_SUBJECT, COMP_MESSAGE)
  VALUES (
 19, 'NotificaIntegrazioneDichiarazione', 'Notifica integrazione dichiarazione di spesa',
 'Si notifica che in data ${data_integraz_dic} il beneficiario ha inoltrato un''integrazione alla dichiarazione di spesa num. ${num_dichiarazione_di_spesa} del ${data_dichiarazione_di_spesa}.' 
);
-- PBANDI_D_METADATA_NOTIFICA
INSERT INTO PBANDI_D_METADATA_NOTIFICA (ID_METADATA_NOTIFICA, NOME_PARAMETRO)
 VALUES (19, 'DATA_INTEGRAZ_DIC');
-- PBANDI_R_PLACEHOLDER_NOTIFICA
INSERT INTO PBANDI_R_PLACEHOLDER_NOTIFICA (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
 VALUES (19, 19,'${data_integraz_dic}');
INSERT INTO PBANDI_R_PLACEHOLDER_NOTIFICA (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
VALUES (19, 4,'${num_dichiarazione_di_spesa}');
INSERT INTO PBANDI_R_PLACEHOLDER_NOTIFICA (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
VALUES (19, 3,'${data_dichiarazione_di_spesa}');
--
--
-- PBANDI_D_NOME_BATCH
Insert into PBANDI_D_NOME_BATCH
   (ID_NOME_BATCH, NOME_BATCH, DESC_BATCH)
 Values
   (20, 'PBAN-CAMPIONAMENTO', 'Procedura che effettua il campionamento progetti certificazione');
COMMIT;

-- PBANDI_D_ERRORE_BATCH
Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('E171', 'Errore nella procedura di campionamento');
COMMIT;

-- PBANDI_C_TIPO_DOCUMENTO_INDEX
Insert into PBANDI_C_TIPO_DOCUMENTO_INDEX
   (ID_TIPO_DOCUMENTO_INDEX, DESC_BREVE_TIPO_DOC_INDEX, DESC_TIPO_DOC_INDEX)
 Values
   (27, 'FCC', 'FILE CAMPIONAMENTO CERTIFICAZIONE');
COMMIT;


--
/*
*** script da eseguire ad ogni rilascio
*/
--
update PBANDI_C_VERSIONE
set VERSIONE_DB = '3.14.0';

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

