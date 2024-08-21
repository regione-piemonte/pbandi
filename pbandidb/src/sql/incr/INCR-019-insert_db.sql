/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

-- PBANDI_D_SOGGETTO_FINANZIATORE
/*
Insert into PBANDI_D_SOGGETTO_FINANZIATORE
   (ID_SOGGETTO_FINANZIATORE, DESC_BREVE_SOGG_FINANZIATORE, DESC_SOGG_FINANZIATORE, DT_INIZIO_VALIDITA, COD_IGRUE_T25, COD_CIPE_CUP, FLAG_CERTIFICAZIONE, ID_TIPO_SOGG_FINANZIAT, FLAG_AGEVOLATO)
 Values
   (16, 'Altro FSC', 'Altro Pubblico o Privato FSC', TO_DATE('10/10/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    7, '007', 'S', 3, 'N');
*/
/*	
-- Elimino la regola 12 ai BL di Finpis per il PAR-FAS
delete  PBANDI_R_REGOLA_BANDO_LINEA
where progr_bando_linea_intervento in (101,103,123,117,139)
and id_regola = 12;

*/
/*
Insert into PBANDI_D_SOGGETTO_FINANZIATORE
   (ID_SOGGETTO_FINANZIATORE, DESC_BREVE_SOGG_FINANZIATORE, DESC_SOGG_FINANZIATORE, DT_INIZIO_VALIDITA, COD_IGRUE_T25, COD_CIPE_CUP, FLAG_CERTIFICAZIONE, ID_TIPO_SOGG_FINANZIAT, FLAG_AGEVOLATO)
 Values
   (17, 'Altro Pubblico FSC', 'Altro Pubblico FSC', TO_DATE('10/10/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 
    6, '005', 'S', 3, 'S');
*/
-- PBANDI_R_BANDO_SOGG_FINANZIAT
/*

delete PBANDI_R_BANDO_SOGG_FINANZIAT
where id_bando in (84,118,104,98,82);

Insert into PBANDI_R_BANDO_SOGG_FINANZIAT
   (ID_BANDO, ID_SOGGETTO_FINANZIATORE, PERCENTUALE_QUOTA_SOGG_FINANZ, ID_UTENTE_INS)
 Values
   (104, 5, 100, 3181); -- PAR FSC-BEI / STATO FAS  OK
Insert into PBANDI_R_BANDO_SOGG_FINANZIAT
   (ID_BANDO, ID_SOGGETTO_FINANZIATORE, PERCENTUALE_QUOTA_SOGG_FINANZ, ID_UTENTE_INS)
 Values
   (84, 17, 100, 3181); -- PAR-FSC ASSE I.4.1414 PIU' EXPORT VOUCHER SINGOLI 2012 3° EDIZIONE / ALTRO PUBBLICO FSC 
Insert into PBANDI_R_BANDO_SOGG_FINANZIAT
   (ID_BANDO, ID_SOGGETTO_FINANZIATORE, PERCENTUALE_QUOTA_SOGG_FINANZ, ID_UTENTE_INS)
 Values
   (118, 17, 100, 3181); -- PAR-FSC ASSE I.4.1412 Bando per l'erogazione di voucher  per spese di promozione in occasione di fiere internazionali all'estero (voucher singoli) / ALTRO PUBBLICO FSC 

Insert into PBANDI_R_BANDO_SOGG_FINANZIAT
   (ID_BANDO, ID_SOGGETTO_FINANZIATORE, PERCENTUALE_QUOTA_SOGG_FINANZ, ID_UTENTE_INS)
 Values
   (98, 5, 76.19, 3181); -- PAR-FSC ASSE III.6.3611 PIANO ANNUALE DI ATTUAZIONE 2008 / STATO FSC 
Insert into PBANDI_R_BANDO_SOGG_FINANZIAT
   (ID_BANDO, ID_SOGGETTO_FINANZIATORE, PERCENTUALE_QUOTA_SOGG_FINANZ, ID_UTENTE_INS)
 Values
   (98, 14, 23.81, 3181); -- PAR-FSC ASSE III.6.3611 PIANO ANNUALE DI ATTUAZIONE 2008 / REGIONE FSC 
   
Insert into PBANDI_R_BANDO_SOGG_FINANZIAT
   (ID_BANDO, ID_SOGGETTO_FINANZIATORE, PERCENTUALE_QUOTA_SOGG_FINANZ, ID_UTENTE_INS)
 Values
   (82, 5, 100, 3181); -- PAR-FSC ASSE II.3 BANDO REGIME DI AIUTO PER LA QUALIFICAZIONE E IL RAFFORZAMENTO DEL SISTEMA PRODUTTIVO PIEMONTESE BIS / STATO FSC 
*/
--PBANDI_R_BANDO_LINEA_PERIODO   
Insert into PBANDI_R_BANDO_LINEA_PERIODO
   (PROGR_BANDO_LINEA_INTERVENTO, ID_PERIODO)
 Values
   (139, 7); ---- PAR-FSC ASSE I.4.1412 Bando per l'erogazione di voucher / Periodo spec. ANNO 2013

   Insert into PBANDI_R_BANDO_LINEA_PERIODO
   (PROGR_BANDO_LINEA_INTERVENTO, ID_PERIODO)
 Values
   (103, 6); ---- PAR-FSC ASSE I.4.1414 PIU' EXPORT VOUCHER SINGOLI 2012 3° EDIZIONE  / Periodo spec. ANNO 2012

Insert into PBANDI_R_BANDO_LINEA_PERIODO
   (PROGR_BANDO_LINEA_INTERVENTO, ID_PERIODO)
 Values
   (123, 6); ---- PAR FSC-BEI / Periodo spec. ANNO 2012

Insert into PBANDI_R_BANDO_LINEA_PERIODO
   (PROGR_BANDO_LINEA_INTERVENTO, ID_PERIODO)
 Values
   (117, 6); ---- PAR-FSC ASSE III.6.3611 PIANO ANNUALE DI ATTUAZIONE 2008 / Periodo spec. ANNO 2012

Insert into PBANDI_R_BANDO_LINEA_PERIODO
   (PROGR_BANDO_LINEA_INTERVENTO, ID_PERIODO)
 Values
   (117, 7); ---- PAR-FSC ASSE III.6.3611 PIANO ANNUALE DI ATTUAZIONE 2008 / Periodo spec. ANNO 2013

Insert into PBANDI_R_BANDO_LINEA_PERIODO
   (PROGR_BANDO_LINEA_INTERVENTO, ID_PERIODO)
 Values
   (117, 8); ---- PAR-FSC ASSE III.6.3611 PIANO ANNUALE DI ATTUAZIONE 2008 / Periodo spec. ANNO 2014
Insert into PBANDI_R_BANDO_LINEA_PERIODO
   (PROGR_BANDO_LINEA_INTERVENTO, ID_PERIODO)
 Values
   (117, 9); ---- PAR-FSC ASSE III.6.3611 PIANO ANNUALE DI ATTUAZIONE 2008 / Periodo spec. ANNO 2015
Insert into PBANDI_R_BANDO_LINEA_PERIODO
   (PROGR_BANDO_LINEA_INTERVENTO, ID_PERIODO)
 Values
   (117, 10); ---- PAR-FSC ASSE III.6.3611 PIANO ANNUALE DI ATTUAZIONE 2008 / Periodo spec. ANNO 2016
Insert into PBANDI_R_BANDO_LINEA_PERIODO
   (PROGR_BANDO_LINEA_INTERVENTO, ID_PERIODO)
 Values
   (101, 6); ---- PAR-FSC ASSE II.3 BANDO REGIME DI AIUTO PER LA QUALIFICAZIONE E IL RAFFORZAMENTO DEL SISTEMA PRODUTTIVO PIEMONTESE BIS  / Periodo spec. ANNO 2012
Insert into PBANDI_R_BANDO_LINEA_PERIODO
   (PROGR_BANDO_LINEA_INTERVENTO, ID_PERIODO)
 Values
   (101, 7); ---- PAR-FSC ASSE II.3 BANDO REGIME DI AIUTO PER LA QUALIFICAZIONE E IL RAFFORZAMENTO DEL SISTEMA PRODUTTIVO PIEMONTESE BIS  / Periodo spec. ANNO 2013
Insert into PBANDI_R_BANDO_LINEA_PERIODO
   (PROGR_BANDO_LINEA_INTERVENTO, ID_PERIODO)
 Values
   (101, 8); ---- PAR-FSC ASSE II.3 BANDO REGIME DI AIUTO PER LA QUALIFICAZIONE E IL RAFFORZAMENTO DEL SISTEMA PRODUTTIVO PIEMONTESE BIS  / Periodo spec. ANNO 2014
   
 /*  
-- PBANDI_D_ERRORE_BATCH
Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('W091', 'Manca record su Sogg. Finanziat. per "Altro FSC"  PBANDI_D_SOGGETTO_FINANZIATORE');
   
Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('W092', 'Manca record su Bando Linea-Tipo periodo"  PBANDI_R_BANDO_LIN_TIPO_PERIOD');

Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('W093', 'Non esistono periodi specifici. Sono state inserite le annualità standard.');
   
COMMIT;
*/   
/*
*** script da eseguire ad ogni rilascio
*/
update PBANDI_C_VERSIONE
set VERSIONE_DB = '2.9.0';

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



