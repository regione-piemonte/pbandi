/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/*
*** script da eseguire ad ogni rilascio
*/


-- AGGIORNA flag_modol
update pbandi_t_checklist ck set ck.flag_modol = 'S'
where ck.id_checklist in 
(
select ck.id_checklist from pbandi_t_documento_index di, pbandi_t_progetto prg, pbandi_t_domanda dom, pbandi_r_bando_linea_intervent bli, pbandi_t_bando b,
pbandi_t_checklist ck
where di.id_progetto = prg.id_progetto
and prg.id_domanda = dom.id_domanda
and dom.progr_bando_linea_intervento = bli.progr_bando_linea_intervento
and bli.id_bando = b.id_bando
and di.id_documento_index = ck.id_documento_index
and b.id_linea_di_intervento is null);
--
COMMIT;
--
Insert into PBANDI_D_MOTIVO_REVOCA
   (ID_MOTIVO_REVOCA, DESC_MOTIVO_REVOCA, COD_IGRUE_T36, DT_INIZIO_VALIDITA)
 Values
   (16, 'Dimensione d''impresa', '99', TO_DATE('01/01/2014 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_MOTIVO_REVOCA
   (ID_MOTIVO_REVOCA, DESC_MOTIVO_REVOCA, COD_IGRUE_T36, DT_INIZIO_VALIDITA)
 Values
   (17, 'Documentazione giustificativa mancante/non conforme/carente', '99', TO_DATE('01/01/2014 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_MOTIVO_REVOCA
   (ID_MOTIVO_REVOCA, DESC_MOTIVO_REVOCA, COD_IGRUE_T36, DT_INIZIO_VALIDITA)
 Values
   (18, 'Effetto incentivazione', '99', TO_DATE('01/01/2014 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_MOTIVO_REVOCA
   (ID_MOTIVO_REVOCA, DESC_MOTIVO_REVOCA, COD_IGRUE_T36, DT_INIZIO_VALIDITA)
 Values
   (19, 'Frode sospetta', '99', TO_DATE('01/01/2014 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_MOTIVO_REVOCA
   (ID_MOTIVO_REVOCA, DESC_MOTIVO_REVOCA, COD_IGRUE_T36, DT_INIZIO_VALIDITA)
 Values
   (20, 'Impresa in difficoltà', '99', TO_DATE('01/01/2014 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_MOTIVO_REVOCA
   (ID_MOTIVO_REVOCA, DESC_MOTIVO_REVOCA, COD_IGRUE_T36, DT_INIZIO_VALIDITA)
 Values
   (21, 'Investimento realizzato fuori dal territorio ammissibile al POR FESR', '99', TO_DATE('01/01/2014 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_MOTIVO_REVOCA
   (ID_MOTIVO_REVOCA, DESC_MOTIVO_REVOCA, COD_IGRUE_T36, DT_INIZIO_VALIDITA)
 Values
   (22, 'Principio di stabilità', '99', TO_DATE('01/01/2014 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_MOTIVO_REVOCA
   (ID_MOTIVO_REVOCA, DESC_MOTIVO_REVOCA, COD_IGRUE_T36, DT_INIZIO_VALIDITA)
 Values
   (23, 'Progetto non completato/non conforme a quello ammesso ad agevolazione', '99', TO_DATE('01/01/2014 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_MOTIVO_REVOCA
   (ID_MOTIVO_REVOCA, DESC_MOTIVO_REVOCA, COD_IGRUE_T36, DT_INIZIO_VALIDITA)
 Values
   (24, 'Progetto non funzionante', '99', TO_DATE('01/01/2014 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_MOTIVO_REVOCA
   (ID_MOTIVO_REVOCA, DESC_MOTIVO_REVOCA, COD_IGRUE_T36, DT_INIZIO_VALIDITA)
 Values
   (25, 'Procedure concorsuali', '99', TO_DATE('01/01/2014 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_MOTIVO_REVOCA
   (ID_MOTIVO_REVOCA, DESC_MOTIVO_REVOCA, COD_IGRUE_T36, DT_INIZIO_VALIDITA)
 Values
   (26, 'Regole di Cumulo', '99', TO_DATE('01/01/2014 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_MOTIVO_REVOCA
   (ID_MOTIVO_REVOCA, DESC_MOTIVO_REVOCA, COD_IGRUE_T36, DT_INIZIO_VALIDITA)
 Values
   (27, 'Spese non ammissibili', '99', TO_DATE('01/01/2014 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_MOTIVO_REVOCA
   (ID_MOTIVO_REVOCA, DESC_MOTIVO_REVOCA, COD_IGRUE_T36, DT_INIZIO_VALIDITA)
 Values
   (28, 'Superamento soglia De Minimis', '99', TO_DATE('01/01/2014 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_MOTIVO_REVOCA
   (ID_MOTIVO_REVOCA, DESC_MOTIVO_REVOCA, COD_IGRUE_T36, DT_INIZIO_VALIDITA)
 Values
   (29, 'Violazione principio contabilità separata ', '99', TO_DATE('01/01/2014 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_MOTIVO_REVOCA
   (ID_MOTIVO_REVOCA, DESC_MOTIVO_REVOCA, COD_IGRUE_T36, DT_INIZIO_VALIDITA)
 Values
   (30, 'Violazione normativa appalti ', '99', TO_DATE('01/01/2014 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_MOTIVO_REVOCA
   (ID_MOTIVO_REVOCA, DESC_MOTIVO_REVOCA, COD_IGRUE_T36, DT_INIZIO_VALIDITA)
 Values
   (31, 'Mancato rispetto delle disposizioni in tema di costituzione ed esecuzione dello strumento finanziario', '99', TO_DATE('01/01/2014 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_MOTIVO_REVOCA
   (ID_MOTIVO_REVOCA, DESC_MOTIVO_REVOCA, COD_IGRUE_T36, DT_INIZIO_VALIDITA)
 Values
   (32, 'Rimodulazione della dotazione finanziaria dello strumento finanziario', '99', TO_DATE('01/01/2014 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
COMMIT;
--

update PBANDI_C_VERSIONE
set VERSIONE_DB = '3.11.0';

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

