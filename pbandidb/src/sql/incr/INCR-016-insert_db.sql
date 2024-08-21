/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

-- Automazione rendicontazione

insert into PBANDI_C_REGOLA
(ID_REGOLA, DESC_BREVE_REGOLA, DESC_REGOLA, DT_INIZIO_VALIDITA)
values
(38,'BR38',' Specifica se il bando linea prevede la modalita'' di rendicontazione automatizzata',sysdate);

Insert into PBANDI_R_REGOLA_BANDO_LINEA
   (ID_REGOLA, PROGR_BANDO_LINEA_INTERVENTO)
 Values
   (38, 111);

   Insert into PBANDI_R_REGOLA_BANDO_LINEA
   (ID_REGOLA, PROGR_BANDO_LINEA_INTERVENTO)
 Values
   (38, 112);

insert into PBANDI_D_TIPO_DOCUMENTO_SPESA
(ID_TIPO_DOCUMENTO_SPESA, DESC_BREVE_TIPO_DOC_SPESA, DESC_TIPO_DOCUMENTO_SPESA, DT_INIZIO_VALIDITA)
values
(30,'CO','Contributo forfettario erogato',sysdate);


insert into PBANDI_R_ECCEZ_BAN_LIN_DOC_PAG
(ID_MODALITA_PAGAMENTO, ID_TIPO_DOCUMENTO_SPESA, FLAG_AGGIUNTA, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS, PROGR_ECCEZ_BAN_LIN_DOC_PAG, DT_INSERIMENTO)
(select 5,30,'S',PROGR_BANDO_LINEA_INTERVENTO,0,SEQ_PBANDI_R_ECC_BAN_LIN_DOC_P.nextval,sysdate
 from PBANDI_R_REGOLA_BANDO_LINEA rbl,PBANDI_C_REGOLA r
 where RBL.ID_REGOLA                    = R.ID_REGOLA
 AND   R.DESC_BREVE_REGOLA              = 'BR38');
 

insert into PBANDI_D_NOME_BATCH
(ID_NOME_BATCH, NOME_BATCH, DESC_BATCH)
values
(15,'PBAN-AUTOMAZ-RENDIC','Procedura automazione rendicontazione');

Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('E148', 'Errore nella procedura di automazione della rendicontazione');
   
Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('W089', 'Stato Atto proveniente da bilancio non associabile ad anagrafica D_STATO_ATTO');

insert into PBANDI_T_UTENTE
(ID_UTENTE, CODICE_UTENTE, ID_TIPO_ACCESSO, DT_INSERIMENTO)
values
(-6,'RENDICONTAZIONE',5,SYSDATE);


Insert into PBANDI_C_COSTANTI
   (ATTRIBUTO, VALORE)
 Values
   ('conf.serverConfiguration.indirizzoEmailRendicontazioneAutomatica', 'AssistenzaPiattaforma.Bandi@csi.it');
Insert into PBANDI_C_COSTANTI
   (ATTRIBUTO, VALORE)
 Values
   ('conf.serverConfiguration.indirizzoEmailMittente', 'AssistenzaPiattaforma.Bandi@csi.it');

Insert into PBANDI_C_COSTANTI
   (ATTRIBUTO, VALORE)
 Values
   ('conf.serverConfiguration.maxNumProgettiRendicontazioneAutomatica', '100');
   

-- Richiesta Cup massivo
Update PBANDI_D_LINEA_DI_INTERVENTO
set NUM_DELIBERA = 36,
    ANNO_DELIBERA = 2007
Where COD_IGRUE_T13_T14 in ('2007IT052PO011','2007IT162PO011','2007CB163PO034');

Update PBANDI_D_LINEA_DI_INTERVENTO
set NUM_DELIBERA = 166,
    ANNO_DELIBERA = 2007
Where COD_IGRUE_T13_T14 in ('2007PI002FA011');


-- Fidejussioni 
update pbandi_t_fideiussione a
set COD_RIFERIMENTO_FIDEIUSSIONE = (select NOSTRO_RIFERIMENTO
                                    from pbandi_rw.tmp_fideiussioni
                                    where substr(NOSTRO_RIFERIMENTO,1,instr(NOSTRO_RIFERIMENTO,'-')-1) = substr(a.COD_RIFERIMENTO_FIDEIUSSIONE,1,instr(a.COD_RIFERIMENTO_FIDEIUSSIONE,'-')-1)
                                    )
where id_utente_ins = 2
and   exists
(select  null
from pbandi_rw.tmp_fideiussioni
where substr(NOSTRO_RIFERIMENTO,1,instr(NOSTRO_RIFERIMENTO,'-')-1) = substr(a.COD_RIFERIMENTO_FIDEIUSSIONE,1,instr(a.COD_RIFERIMENTO_FIDEIUSSIONE,'-')-1));


-- Bilancio
Insert into PBANDI_D_STATO_ATTO
   (ID_STATO_ATTO, DESC_STATO_ATTO, DESC_BREVE_STATO_ATTO, DT_INIZIO_VALIDITA)
 Values
   (3, 'PRESO IN CARICO DALLA RAGIONERIA', '2', TO_DATE('01/01/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_STATO_ATTO
   (ID_STATO_ATTO, DESC_STATO_ATTO, DESC_BREVE_STATO_ATTO, DT_INIZIO_VALIDITA)
 Values
   (4, 'ANNULLATO', '3', TO_DATE('01/01/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_STATO_ATTO
   (ID_STATO_ATTO, DESC_STATO_ATTO, DESC_BREVE_STATO_ATTO, DT_INIZIO_VALIDITA)
 Values
   (5, 'PARZIALMENTE QUIETANZATO', '4', TO_DATE('01/01/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_STATO_ATTO
   (ID_STATO_ATTO, DESC_STATO_ATTO, DESC_BREVE_STATO_ATTO, DT_INIZIO_VALIDITA)
 Values
   (6, 'TOTALMENTE QUIETANZATO', '5', TO_DATE('01/01/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_STATO_ATTO
   (ID_STATO_ATTO, DESC_STATO_ATTO, DESC_BREVE_STATO_ATTO, DT_INIZIO_VALIDITA)
 Values
   (7, 'COMPLETATO', '6', TO_DATE('01/01/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_STATO_ATTO
   (ID_STATO_ATTO, DESC_STATO_ATTO, DESC_BREVE_STATO_ATTO, DT_INIZIO_VALIDITA)
 Values
   (8, 'RICHIESTA MODIFICA', '7', TO_DATE('01/01/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));

update PBANDI_T_ATTO_LIQUIDAZIONE a
set importo_atto = (select sum(importo_liquidato)
                             from PBANDI_R_LIQUIDAZIONE
                            where id_atto_liquidazione= a.id_atto_liquidazione)
where importo_atto IS NULL ;

COMMIT;
/*
*** script da eseguire ad ogni rilascio
*/
update PBANDI_C_VERSIONE
set VERSIONE_DB = '2.7.0';

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



