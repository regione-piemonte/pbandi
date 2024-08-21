/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

--PBANDI_D_SOGGETTO_FINANZIATORE
UPDATE PBANDI_D_SOGGETTO_FINANZIATORE
 SET FLAG_AMM_FESR = 'S'
 WHERE ID_SOGGETTO_FINANZIATORE IN (1,2,12);
 
COMMIT; 

-- PBANDI_C_REGOLA
Insert into PBANDI_C_REGOLA
   (ID_REGOLA, DESC_BREVE_REGOLA, DESC_REGOLA, DT_INIZIO_VALIDITA)
 Values
   (41, 'BR41', 'Nel calcolo di importo pagamento ammesso per il monitoraggio, se nel calcolo di importo_pagamento_ammesso per il monitoraggio, se importo_agevolato = 0, viene utilizzata la spesa pubblica', TO_DATE('04/09/2014 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));

--PBANDI_R_REGOLA_BANDO_LINEA
Insert into PBANDI_R_REGOLA_BANDO_LINEA
   (ID_REGOLA, PROGR_BANDO_LINEA_INTERVENTO)
 Values
   (41, 63);
Insert into PBANDI_R_REGOLA_BANDO_LINEA
   (ID_REGOLA, PROGR_BANDO_LINEA_INTERVENTO)
 Values
   (41, 64);
COMMIT;


-- Bonifica PBANDI_R_DOMANDA_INDICATORI 
update PBANDI_R_DOMANDA_INDICATORI
set dt_aggiornamento = dt_inserimento,
id_utente_agg = id_utente_ins
where (id_domanda,id_indicatori) IN
(
select a.id_domanda,id_indicatori  from PBANDI_R_DOMANDA_INDICATORI a,
PBANDI_T_PROGETTO b
where valore_concluso = 1
and b.id_domanda = a.id_domanda
and a.dt_aggiornamento IS NULL
and a.id_utente_ins = 2
and pbandi_rw.IsFse2(b.id_progetto) = 0);

COMMIT;
/*
*** script da eseguire ad ogni rilascio
*/
update PBANDI_C_VERSIONE
set VERSIONE_DB = '2.13.0';

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



