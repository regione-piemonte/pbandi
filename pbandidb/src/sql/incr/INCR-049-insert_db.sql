/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/*
*** script da eseguire ad ogni rilascio
*/
--
-- PBANDI_D_NOME_BATCH
Insert into PBANDI_D_NOME_BATCH
   (ID_NOME_BATCH, NOME_BATCH, DESC_BATCH)
 Values
   (22, 'PBAN-CALCOLA-ECONOMIE', 'Procedura che effettua il calcolo delle economie');
COMMIT;

-- PBANDI_D_ERRORE_BATCH
Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('E177', 'Errore nella procedura di calcolo economie');
COMMIT;
--
-- PBANDI_D_NOME_BATCH
Insert into PBANDI_D_NOME_BATCH
   (ID_NOME_BATCH, NOME_BATCH, DESC_BATCH)
 Values
   (23, 'PBAN-NOTIFICHE-ANTICIPI', 'Procedura che invia le notifiche agli istruttori sugli anticipi');
COMMIT;
Insert into PBANDI_D_NOME_BATCH
   (ID_NOME_BATCH, NOME_BATCH, DESC_BATCH)
 Values
   (24, 'PBAN-NOTIFICHE-ANTICIPI-MAIL', 'Procedura che invia le notifiche mail agli istruttori sugli anticipi');
COMMIT;
--
-- PBANDI_D_ERRORE_BATCH
Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('E175', 'Errore nella procedura di crea notifica anticipi');
COMMIT;
--
Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('E176', 'Errore nella procedura di crea notifica anticipi - INVIO MAIL');
COMMIT;
-- PBANDI_C_COSTANTI
Insert into PBANDI_C_COSTANTI
   (ATTRIBUTO, VALORE)
Values
   ('alert_anticipi_mesi','24');
COMMIT;
Insert into PBANDI_C_COSTANTI
   (ATTRIBUTO, VALORE)
Values
   ('alert_mail_oggetto','ALERT -  ANTICIPI NON COPERTI DA SPESA VALIDATA');
COMMIT;
Insert into PBANDI_C_COSTANTI
   (ATTRIBUTO, VALORE)
Values
   ('alert_mail_testo','<p>Si segnala che le operazioni di seguito elencate non risultano ancora aver raggiunto l''integrale copertura con spesa validata dell''importo dell''anticipo coperto da fideiussione, sulla base dei dati contenuti nell''ultima certificazione di spesa trasmessa alla Commissione Europea.</p>');
COMMIT;
/*
Insert into PBANDI_C_COSTANTI
   (ATTRIBUTO, VALORE)
Values
   ('alert_mail_testo_2','<p>Si rammenta che, ai sensi di quanto disposto dall''art. 131, par. 2, lett. c) del Regolamento (UE) n. 1303/2014, gli anticipi oggetto di certificazione sono coperti dalle spese sostenute dai beneficiari nell''attuazione dell''operazione e giustificati da fatture quietanzate o da documenti contabili di valore probatorio equivalente presentati al pi&ugrave; tardi entro tre anni dall''anno in cui &egrave; stato versato l''anticipo o entro il 31 dicembre 2023, se anteriore; in caso contrario la successiva domanda di pagamento &egrave; rettificata di conseguenza.</p>');
COMMIT;
*/
Insert into PBANDI_C_COSTANTI
   (ATTRIBUTO, VALORE)
Values
   ('alert_mail_testo_2','<p>Si rammenta che, ai sensi di quanto disposto dall''art. 131, par. 2, lett. c) del Regolamento (UE) n. 1303/2014, gli anticipi oggetto di certificazione sono coperti dalle spese sostenute dai beneficiari nell''attuazione dell''operazione e giustificati da fatture quietanzate o da documenti contabili di valore probatorio equivalente presentati al piu'' tardi entro tre anni dall''anno in cui e'' stato versato l''anticipo o entro il 31 dicembre 2023, se anteriore; in caso contrario la successiva domanda di pagamento e'' rettificata di conseguenza.</p>');
COMMIT;
Insert into PBANDI_C_COSTANTI
   (ATTRIBUTO, VALORE)
Values
   ('alert_mail_mittente ','assistenzapiattaforma.bandi@csi.it');
COMMIT;   
--
Insert into PBANDI_C_COSTANTI
   (ATTRIBUTO, VALORE)
Values
   ('alert_mail_AdG_AdC_ISTRUTTORI','autorita.certificazione@regione.piemonte.it');
COMMIT;   
--

-- PBANDI_R_PERMESSO_TIPO_ANAGRAF
insert into PBANDI_R_PERMESSO_TIPO_ANAGRAF
 (ID_PERMESSO,ID_TIPO_ANAGRAFICA,DT_INIZIO_VALIDITA,DT_FINE_VALIDITA)
 values (11,4,sysdate,null); 
insert into PBANDI_R_PERMESSO_TIPO_ANAGRAF 
(ID_PERMESSO,ID_TIPO_ANAGRAFICA,DT_INIZIO_VALIDITA,DT_FINE_VALIDITA) 
values 
(15,4,sysdate,null);
COMMIT;
--
update PBANDI_C_VERSIONE
set VERSIONE_DB = '3.20.0';

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

