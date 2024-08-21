/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

create table tmp_sogg_corr 
as
(select ID_SOGGETTO,ID_SOGGETTO_ENTE_GIURIDICO,ID_TIPO_soggetto_correlato,
                               nvl(ID_CARICA,0) ID_CARICA,nvl(QUOTA_PARTECIPAZIONE,0) QUOTA_PARTECIPAZIONE
                        from   pbandi_r_soggetti_correlati
                        where  dt_fine_validita is null
                        group by ID_SOGGETTO,ID_SOGGETTO_ENTE_GIURIDICO,ID_TIPO_soggetto_correlato,ID_CARICA,QUOTA_PARTECIPAZIONE
                        having count(*) > 1);
						
CREATE TABLE SOGGETTI_CORRELATI
(
  DT_FINE_VALIDITA            DATE,
  ID_TIPO_SOGGETTO_CORRELATO  NUMBER(3)         NOT NULL,
  ID_CARICA                   NUMBER(3),
  ID_UTENTE_INS               NUMBER(8)         NOT NULL,
  ID_UTENTE_AGG               NUMBER(8),
  QUOTA_PARTECIPAZIONE        NUMBER(17,2),
  ID_SOGGETTO                 NUMBER(8)         NOT NULL,
  ID_SOGGETTO_ENTE_GIURIDICO  NUMBER(8)         NOT NULL,
  DT_INIZIO_VALIDITA          DATE              NOT NULL,
  PROGR_SOGGETTI_CORRELATI    NUMBER(8)         NOT NULL
);

declare
  cursor curSoggCorr is select * from tmp_sogg_corr;
  
  nProgrSoggettiCorrelatiMin pbandi_r_soggetti_correlati.PROGR_SOGGETTI_CORRELATI%type;
  
  cursor cur(pProgrSoggettiCorrelatiMin number) is select PROGR_SOGGETTI_CORRELATI
                                                   from   SOGGETTI_CORRELATI
                                                   where  PROGR_SOGGETTI_CORRELATI != pProgrSoggettiCorrelatiMin;
  nCont  pls_integer := 0;
begin
  for recSoggCorr in curSoggCorr loop
    begin
      
      insert into SOGGETTI_CORRELATI
      (DT_FINE_VALIDITA, ID_TIPO_SOGGETTO_CORRELATO, ID_CARICA, ID_UTENTE_INS, ID_UTENTE_AGG, QUOTA_PARTECIPAZIONE, 
       ID_SOGGETTO, ID_SOGGETTO_ENTE_GIURIDICO, DT_INIZIO_VALIDITA, PROGR_SOGGETTI_CORRELATI)
      (select DT_FINE_VALIDITA, ID_TIPO_SOGGETTO_CORRELATO, ID_CARICA, ID_UTENTE_INS, ID_UTENTE_AGG, QUOTA_PARTECIPAZIONE, 
              ID_SOGGETTO, ID_SOGGETTO_ENTE_GIURIDICO, DT_INIZIO_VALIDITA, PROGR_SOGGETTI_CORRELATI
       from   pbandi_r_soggetti_correlati
       where  ID_SOGGETTO                 = recSoggCorr.ID_SOGGETTO 
       and    ID_SOGGETTO_ENTE_GIURIDICO  = recSoggCorr.ID_SOGGETTO_ENTE_GIURIDICO 
       and    ID_TIPO_soggetto_correlato  = recSoggCorr.ID_TIPO_soggetto_correlato 
       and    nvl(ID_CARICA,0)            = recSoggCorr.ID_CARICA 
       and    nvl(QUOTA_PARTECIPAZIONE,0) = recSoggCorr.QUOTA_PARTECIPAZIONE);
       
       
       select min(PROGR_SOGGETTI_CORRELATI)
       into   nProgrSoggettiCorrelatiMin
       from   SOGGETTI_CORRELATI;
       
       for rec in cur(nProgrSoggettiCorrelatiMin) loop
         select count(*)
         into   nCont
         from   PBANDI_R_SOGG_DOM_SOGG_CORREL
         where  PROGR_SOGGETTI_CORRELATI = rec.PROGR_SOGGETTI_CORRELATI;
         
         if nCont = 0 then
         
           begin
             update PBANDI_R_SOGG_DOM_SOGG_CORREL
             set    PROGR_SOGGETTI_CORRELATI = nProgrSoggettiCorrelatiMin
             where  PROGR_SOGGETTI_CORRELATI = rec.PROGR_SOGGETTI_CORRELATI;
           exception
             when others then
               null;
           end;
         end if;                                        
       end loop;
       
       for rec in cur(nProgrSoggettiCorrelatiMin) loop
         select count(*)
         into   nCont
         from   PBANDI_R_SOGG_PROG_SOGG_CORREL
         where  PROGR_SOGGETTI_CORRELATI = rec.PROGR_SOGGETTI_CORRELATI;
         
         if nCont = 0 then
         
           begin
             update PBANDI_R_SOGG_PROG_SOGG_CORREL
             set    PROGR_SOGGETTI_CORRELATI = nProgrSoggettiCorrelatiMin
             where  PROGR_SOGGETTI_CORRELATI = rec.PROGR_SOGGETTI_CORRELATI;
           exception
             when others then
               null;     
           end;
         end if;    
       end loop;                                            
       
       delete PBANDI_R_SOGG_DOM_SOGG_CORREL
       where  PROGR_SOGGETTI_CORRELATI in (select PROGR_SOGGETTI_CORRELATI
                                           from   SOGGETTI_CORRELATI
                                           where  PROGR_SOGGETTI_CORRELATI != nProgrSoggettiCorrelatiMin);
                                           
       delete PBANDI_R_SOGG_PROG_SOGG_CORREL
       where  PROGR_SOGGETTI_CORRELATI in (select PROGR_SOGGETTI_CORRELATI
                                           from   SOGGETTI_CORRELATI
                                           where  PROGR_SOGGETTI_CORRELATI != nProgrSoggettiCorrelatiMin);                                           
       
       delete pbandi_r_soggetti_correlati
       where  PROGR_SOGGETTI_CORRELATI in (select PROGR_SOGGETTI_CORRELATI
                                           from   SOGGETTI_CORRELATI
                                           where  PROGR_SOGGETTI_CORRELATI != nProgrSoggettiCorrelatiMin);
                                                   
       execute immediate('truncate table SOGGETTI_CORRELATI');
     exception
       when others then
         execute immediate('truncate table SOGGETTI_CORRELATI');
         rollback;
         dbms_output.put_line('errore loop = '||sqlerrm||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE||
                                 ' nProgrSoggettiCorrelatiMin = '||nProgrSoggettiCorrelatiMin);
     end;           
  end loop;
exception
  when others then
    rollback;
    dbms_output.put_line('errore = '||sqlerrm||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);                                           
end;
/
