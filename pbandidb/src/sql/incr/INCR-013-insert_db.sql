/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SET DEFINE OFF;
update PBANDI_D_ERRORE_BATCH
set DESCRIZIONE = 'Il documento di spesa e'' gia'' presente nella base dati con pagamenti gia'' inseriti. Sono stati scartati i pagamenti uguali per data e importo a pagamenti gia'' associati.'
where CODICE_ERRORE = 'E129';

Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('E136', 'Sono stati scartati i pagamenti aventi codice modalita'' pagamento non compatibile con il tipo di documento di spesa.');   
   
update PBANDI_D_ERRORE_BATCH
set DESCRIZIONE = 'Il totale delle quietanze e'' superiore all''importo totale del documento di spesa.'
where CODICE_ERRORE = 'E133';   

commit;

insert into pbandi_d_errore_batch (codice_errore,descrizione) values('E137','Il file presenta errori di struttura.');
insert into pbandi_d_errore_batch (codice_errore,descrizione) values('E138','In assenza di importo, e'' obbligatorio valorizzare le ore lavorate.');
insert into pbandi_d_errore_batch (codice_errore,descrizione) values('E139','Il costo orario del fornitore e'' obbligatorio per il cedolino.');
insert into pbandi_d_errore_batch (codice_errore,descrizione) values('E140','L''importo rendicontabile non deve essere superiore all''imponibile.');
insert into pbandi_d_errore_batch (codice_errore,descrizione) values('E141','L''importo dell''IVA a costo non deve essere superiore all''imposta.');
insert into pbandi_d_errore_batch (codice_errore,descrizione) values('E142','L''importo rendicontabile non deve essere superiore all''imponibile piu'' l''IVA a costo.');
commit;

SET DEFINE OFF;
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('B', 'IMPORTO_AMMESSO', 'Importo Ammesso');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('B', 'IMPORTO_AGEVOLATO', 'Totale Importo agevolato');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('B', 'IMPORTO_IMPEGNO', 'Importo Impegno');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('C', 'IMPORTO_AMMESSO', 'Importo Ammesso');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('C', 'IMPORTO_AGEVOLATO', 'Totale Importo agevolato');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('C', 'IMPORTO_IMPEGNO', 'Importo Impegno');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('C', 'RIBASSO_ASTA', 'Ribasso d''asta');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('D', 'IMPORTO_AMMESSO', 'Importo Ammesso Proposto');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('D', 'IMPORTO_AGEVOLATO', 'Totale Importo agevolato richiesto');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('D', 'RIBASSO_ASTA', 'Ribasso d''asta proposto');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('G', 'PERIODI', 'Periodo dichiarazione');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('G', 'IMPORTO_RENDICONTATO', 'Importo Rendicontato');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('G', 'IMPORTO_QUIETANZATO', 'Importo Quietanzato');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('H', 'PERIODI', 'Periodo dichiarazione');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('H', 'IMPORTO_RENDICONTATO', 'Importo Rendicontato');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('H', 'IMPORTO_QUIETANZATO', 'Importo Quietanzato');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('H', 'IMPORTO_VALIDATO', 'Importo Validato');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('H', 'NOTE', 'Note');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('I', 'MOTIVO', 'Motivo');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('K', 'MOTIVO', 'Motivo');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('K', 'NOTE', 'Note');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('I', 'NOTE', 'Note');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('L', 'MODALITA', 'Modalita'' erogazione');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('L', 'NOTE', 'Note');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('L', 'CAUSALE', 'Causale');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('L', 'ENTE', 'Ente erogatore');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('N', 'IMPORTO_RENDICONTATO', 'Importo Rendicontato');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('N', 'IMPORTO_QUIETANZATO', 'Importo Quietanzato');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('N', 'NOTE', 'Note');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('N', 'IMPORTO_SALDO', 'Importo saldo');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('O', 'CAUSALE', 'Causale');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('P', 'NUMERO_IMS', 'Numero IMS');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('P', 'DATA_IMS', 'Data IMS');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('P', 'NOTE', 'Note pratica usata');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('P', 'SOGGETTO', 'Soggetto responsabile');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('Q', 'DATA_DECORRENZA', 'Data Decorrenza');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('Q', 'NOTE', 'Note fideiussione');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('Q', 'ENTE', 'Ente Mittente');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('R', 'NOTE', 'Note');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('R', 'IMPORTO_QUIETANZATO', 'Importo Quietanzato');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('R', 'CAUSALE', 'Causale');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('S', 'NOTE', 'Note');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('J', 'NOTE', 'Note');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('X', 'IMPORTO_AMMESSO', 'Importo Ammesso');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('X', 'IMPORTO_AGEVOLATO', 'Totale Importo agevolato');
Insert into PBANDI_C_ATTIVITA_PREGRESSE
   (COD_ELEMENTO, NOME_COLONNA, NOME_ETICHETTA)
 Values
   ('X', 'IMPORTO_IMPEGNO', 'Importo Impegno');
COMMIT;

SET DEFINE OFF;
Insert into PBANDI_D_NOME_BATCH
   (ID_NOME_BATCH, NOME_BATCH, DESC_BATCH)
 Values
   (12, 'PBAN-UTENTI-MASSIVA', 'Procedura per l''acquisizione massiva degli utenti beneficiario');
COMMIT;

INSERT INTO PBANDI_T_UTENTE 
(CODICE_UTENTE, DT_AGGIORNAMENTO, DT_INSERIMENTO, ID_SOGGETTO, ID_TIPO_ACCESSO, ID_UTENTE,  LOGIN, PASSWORD) 
VALUES 
('UTENTE_MASSIVA',NULL,SYSDATE,NULL,5,-5,NULL,NULL);
COMMIT;

Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('W081', 'Progetto inesistente, soggetto inserito ma non associato');   
COMMIT;   

update PBANDI_D_ERRORE_BATCH
set DESCRIZIONE = 'Il documento di spesa e'' gia'' presente nella base dati. Sono stati acquisiti i pagamenti associati (se congruenti).'
where CODICE_ERRORE = 'W077';

update PBANDI_D_ERRORE_BATCH
set DESCRIZIONE = 'Il documento di spesa e'' gia'' stato inserito nella base dati per un progetto diverso. Sono stati acquisiti per il progetto corrente: l''importo rendicontabile, le eventuali associazioni con le voci di spesa, gli eventuali pagamenti (se congruenti).'
where CODICE_ERRORE = 'W078';

update PBANDI_D_ERRORE_BATCH
set DESCRIZIONE = 'La data del pagamento associato al documento di spesa e'' successiva alla data corrente di elaborazione.'
where CODICE_ERRORE = 'E123';

update PBANDI_D_ERRORE_BATCH
set DESCRIZIONE = 'La data del pagamento associato al documento di spesa non e'' successiva alla data di costituzione dell''azienda.'
where CODICE_ERRORE = 'E127';

update PBANDI_D_ERRORE_BATCH
set DESCRIZIONE = 'La data del pagamento associato al documento di spesa non e'' successiva alla data di presentazione della domanda.'
where CODICE_ERRORE = 'E128';

update PBANDI_D_ERRORE_BATCH
set DESCRIZIONE = 'Il documento di spesa e'' gia'' presente nella base dati con pagamenti gia'' inseriti. Il pagamento e'' stato scartato in quanto uguale per data e importo a un pagamento gia'' associato.'
where CODICE_ERRORE = 'E129';

update PBANDI_D_ERRORE_BATCH
set DESCRIZIONE = 'Il pagamento e'' stato scartato: il codice modalita'' pagamento non e'' compatibile con il tipo di documento di spesa.'
where CODICE_ERRORE = 'E136';

INSERT INTO PBANDI_D_TIPO_ANAGRAFICA 
(ID_TIPO_ANAGRAFICA, DESC_BREVE_TIPO_ANAGRAFICA, DESC_TIPO_ANAGRAFICA,DT_INIZIO_VALIDITA) 
VALUES 
(22 , 'RENDIC-MASSIVA','Operatore abilitato alla rendicontazione massiva',TO_DATE('01/01/2009', 'MM/DD/YYYY'));

commit;

Insert into PBANDI_D_PERMESSO
   (ID_PERMESSO, DESC_BREVE_PERMESSO, DESC_PERMESSO, DT_INIZIO_VALIDITA)
 Values
   (140, 'TRSRENMAS01', 'Rendicontazione massiva', TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));


insert into PBANDI_R_PERMESSO_TIPO_ANAGRAF
(ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA)
(select ID_PERMESSO,ID_TIPO_ANAGRAFICA,to_date('01/01/2009','dd/mm/yyyy')
 from PBANDI_D_PERMESSO, PBANDI_D_TIPO_ANAGRAFICA
 where ID_PERMESSO in (140)
 and DESC_BREVE_TIPO_ANAGRAFICA = 'RENDIC-MASSIVA');
 
 COMMIT;
 
 delete PBANDI_R_DET_PROP_CER_MAND_QUI;
delete PBANDI_T_MANDATO_QUIETANZATO;
commit;

drop sequence SEQ_PBANDI_T_MANDATO_QUIETANZA;
create sequence SEQ_PBANDI_T_MANDATO_QUIETANZA start with 1 nocache;


/*
*** script da eseguire ad ogni rilascio
*/
update PBANDI_C_VERSIONE
set VERSIONE_DB = '2.4.0';

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

