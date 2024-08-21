/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SET DEFINE OFF;

Insert into  PBANDI_D_TIPO_SOGG_FINANZIAT
  (ID_TIPO_SOGG_FINANZIAT,DESC_BREVE_TIPO_SOGG_FINANZIAT,DESC_TIPO_SOGG_FINANZIATORE,DT_INIZIO_VALIDITA,DT_FINE_VALIDITA,PERC_STANDARD)
  values
  (6,'CPUFAS-STA','contributo pubblico PAR-FAS-Stato',TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'),null,75);
  
Insert into  PBANDI_D_TIPO_SOGG_FINANZIAT
  (ID_TIPO_SOGG_FINANZIAT,DESC_BREVE_TIPO_SOGG_FINANZIAT,DESC_TIPO_SOGG_FINANZIATORE,DT_INIZIO_VALIDITA,DT_FINE_VALIDITA,PERC_STANDARD)
  values
  (7,'CPUFAS-REG','contributo pubblico PAR-FAS-Regione',TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'),null,25);
  

commit;

Insert into PBANDI_D_SOGGETTO_FINANZIATORE
   (ID_SOGGETTO_FINANZIATORE,DESC_BREVE_SOGG_FINANZIATORE,DESC_SOGG_FINANZIATORE,DT_INIZIO_VALIDITA,DT_FINE_VALIDITA,COD_IGRUE_T25,COD_CIPE_CUP,FLAG_CERTIFICAZIONE,ID_TIPO_SOGG_FINANZIAT,FLAG_AGEVOLATO,ID_TIPO_FONDO)
 Values
   (14,'Regione FAS','Regione integrazione FAS',TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'),null,3,'002','S',3,'S',2);   
   

commit;   
   
update PBANDI_D_SOGGETTO_FINANZIATORE
set FLAG_CERTIFICAZIONE = 'S'
where DESC_BREVE_SOGG_FINANZIATORE = 'S.F.A.S';   

commit;

update PBANDI_D_SOGGETTO_FINANZIATORE
   set ID_TIPO_SOGG_FINANZIAT = 6
 where DESC_BREVE_SOGG_FINANZIATORE = 'S.F.A.S';
 
update PBANDI_D_SOGGETTO_FINANZIATORE
   set ID_TIPO_SOGG_FINANZIAT = 7
 where DESC_BREVE_SOGG_FINANZIATORE = 'Regione FAS' ;
 
commit;

Insert into PBANDI_D_PERMESSO
   (ID_PERMESSO, DESC_BREVE_PERMESSO, DESC_PERMESSO, DT_INIZIO_VALIDITA)
 Values
   (143, 'OPECRN001-A', 'Editabilita'' date del cronoprogramma per gli istruttori', sysdate);


Insert into PBANDI_R_PERMESSO_TIPO_ANAGRAF
	(ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA)
  Values
  (143,2,sysdate);
  
Insert into PBANDI_R_PERMESSO_TIPO_ANAGRAF
	(ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA)
  Values
  (143,3,sysdate);
  
Insert into PBANDI_R_PERMESSO_TIPO_ANAGRAF
	(ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA)
  Values
  (143,4,sysdate);
 
 COMMIT;

 insert into pbandi_d_errore_batch (codice_errore,descrizione) values('E143','La data del pagamento associato al documento di spesa e'' anteriore alla data del documento.');
 
 COMMIT;

update PBANDI_R_PROG_SOGG_FINANZIAT
set id_soggetto_finanziatore = 14
where id_soggetto_finanziatore = 3 
and PROGR_PROG_SOGG_FINANZIAT in 
(select z.PROGR_PROG_SOGG_FINANZIAT
from PBANDI_R_PROG_SOGG_FINANZIAT z
where z.id_progetto in (  select a.id_progetto
                            from PBANDI_T_PROGETTO a, 
                                 PBANDI_T_DOMANDA b,
                                 PBANDI_R_BANDO_LINEA_INTERVENT c,
                                PBANDI_D_LINEA_DI_INTERVENTO d
                           where a.id_domanda = b.id_domanda
                             and b.PROGR_BANDO_LINEA_INTERVENTO = c.PROGR_BANDO_LINEA_INTERVENTO
                             and c.id_linea_di_intervento = d.id_linea_di_intervento
                             and d.id_linea_di_intervento in ( select   dl.id_linea_di_intervento
                                                                from pbandi_d_linea_di_intervento dl
                                                                connect by prior dl.id_linea_di_intervento = dl.id_linea_di_intervento_padre 
                                                                start with dl.desc_breve_linea = 'PAR-FAS'
                                                             ))
)

 COMMIT;
 
insert into PBANDI_R_SOGG_TIPO_ANAG_LINEA
    (ID_SOGGETTO,
	 ID_TIPO_ANAGRAFICA,
	 ID_LINEA_DI_INTERVENTO,
	 ID_UTENTE_INS,
	 ID_UTENTE_AGG) 
    (select a.ID_SOGGETTO,
            a.ID_TIPO_ANAGRAFICA,
            5, 
            0,
            null
	   from PBANDI_R_SOGG_TIPO_ANAGRAFICA a,PBANDI_D_TIPO_ANAGRAFICA b
	  where a.DT_FINE_VALIDITA is null 
		and a.id_tipo_anagrafica = b.id_tipo_anagrafica
		and b.DESC_BREVE_TIPO_ANAGRAFICA = 'ADG-CERT');

 COMMIT;					   
 
INSERT INTO PBANDI_D_RUOLO_ENTE_COMPETENZA
(ID_RUOLO_ENTE_COMPETENZA,DESC_BREVE_RUOLO_ENTE,DESC_RUOLO_ENTE,DT_INIZIO_VALIDITA,FLAG_VISIBILE) 
values
(8,'RICHIEDENTE CUP','Ente il cui user CIPE richiede il CUP',sysdate,'N');

COMMIT;
 
/*
*** script da eseguire ad ogni rilascio
*/
update PBANDI_C_VERSIONE
set VERSIONE_DB = '2.5.0';

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



