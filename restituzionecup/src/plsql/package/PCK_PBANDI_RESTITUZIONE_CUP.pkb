/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE OR REPLACE package body PBANDI.PCK_PBANDI_RESTITUZIONE_CUP as

 
  function AGGIORNAMENTO_CUP(pNomeDir   VARCHAR2,
                           nome_assegnati  VARCHAR2,
                           nome_scartati VARCHAR2,
                           nome_non_trovati VARCHAR2) return NUMBER is
--
--DALLO SCRIPT CHE RESTITUISCE I CUP DEI PROGETTI RICHIESTI 
 --VIENE RICHIAMATA LA FUNZIONE AGGIORNAMENTO_CUP
 --PARAMETRI INPUT :
 --pNomeDir        (directory dove risiedono i file generati dalla shell JAVA)
 --nome_assegnati  (nome del file che contiene i progetti con CUP ASSEGNATI)
 --nome_scartati   (nome del file che contiene i progetti SCARTATI)
 --nome_non_trovati (nome del file che contiene i progetti NON TROVATI)
 --
--la funzione analizza i file :
--nome_assegnati e aggiorna CUP su T_PROGETTO
--nome_scartati  e produce log per ogni progetto
--nome_non_trovati e produce log per ogni progetto
--
fpFileInput_ass  UTL_FILE.FILE_TYPE;
fpFileInput_sca  UTL_FILE.FILE_TYPE;
fpFileInput_non  UTL_FILE.FILE_TYPE;


riga_lett varchar2(500);
pos_1 number;
pos_2 number;
pos_3 number;
id_prog number;
cod_cup varchar2(30);
stato_rich varchar2(1);
mess_scarto varchar2(500);
mess_scarto_log varchar2(500);
conteggio_ass number:=0;
num_rec_ass number;
begin
  
    ----------------------    
    --CUP ASSEGNATI
    ----------------------
    begin

    
    fpFileInput_ass := UTL_FILE.fopen(pNomeDir,nome_assegnati, 'R',32767);
    
       BEGIN
  
          for linea in 1..500 loop
 
          UTL_FILE.GET_LINE (fpFileInput_ass,riga_lett);
  
          if linea=1 and riga_lett <> 'HH|0|BDUR' then
    
 --            dbms_output.put_line ('manca HEADER = HH|0|BDUR'); 
 
             return 1;
          else
             ---testo EOF
             exit when SUBSTR(riga_lett,1,2)= 'FF';
  
             if linea <> 1 then
                conteggio_ass:=conteggio_ass+1;
                --
                --ricavo ID_PROGETTO E CUP 
                --
                pos_1:=instr (riga_lett,'|',1,1); 
                pos_2:=instr (riga_lett,'|',1,2); 
  
                id_prog := to_number(substr(riga_lett,pos_1 + 1,pos_2-pos_1-1));
                cod_cup:=substr(riga_lett,pos_2+1,length(riga_lett));
                
                UPDATE PBANDI_T_PROGETTO
                       SET CUP=cod_cup 
                       WHERE
                       ID_PROGETTO=id_prog;
                
                COMMIT; 
    
  
             end if;
  
          end if;
  
   
          end loop;
          num_rec_ass:=to_number(SUBSTR(riga_lett,4,length(riga_lett)));
          
          if conteggio_ass = num_rec_ass then
           
         
            
             PCK_PBANDI_UTILITY_BATCH.inslogbatch(
             gnIdProcessoBatch,'W084',
             'ELABORATI CORRETTAMENTE '|| conteggio_ass ||'  CUP');
          
                
          ELSE
    
       
          PCK_PBANDI_UTILITY_BATCH.inslogbatch(
          gnIdProcessoBatch,'W084',
          'ELABORATI CORRETTAMENTE SOLO '|| conteggio_ass 
          ||' su  '||num_rec_ass||' cup. Verificare i dati');
    
       end if;
          
          
          
          IF UTL_FILE.IS_open(fpFileInput_ass) THEN
             UTL_FILE.fclose(fpFileInput_ass);
          END IF;
          
 
       EXCEPTION
         
          WHEN OTHERS THEN
             PCK_PBANDI_UTILITY_BATCH.inslogbatch(
             gnIdProcessoBatch,'E144',
             'Errore imprevisto durante l¿elaborazione del file '||nome_assegnati||
             ' verificare i dati manualmente '||
             sqlerrm||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
             
             PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(gnIdProcessoBatch,'KO');
         return 1;                
               
       END;



   exception
       

       
       WHEN UTL_FILE.invalid_operation THEN
         
         PCK_PBANDI_UTILITY_BATCH.inslogbatch(gnIdProcessoBatch,'W087',nome_assegnati);
         
 
         
       WHEN OTHERS THEN
         
             PCK_PBANDI_UTILITY_BATCH.inslogbatch(
             gnIdProcessoBatch,'E144',
             'Errore imprevisto durante l¿elaborazione del file '||nome_assegnati||
             ' verificare i dati manualmente '||
             sqlerrm||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
             
             PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(gnIdProcessoBatch,'KO');
         return 1; 


   end;
   
   
 --
--CUP SCARTATI
--       

  begin

fpFileInput_sca := UTL_FILE.fopen(pNomeDir,nome_scartati, 'R',32767);
   BEGIN
     for linea in 1..500 loop
 
         UTL_FILE.GET_LINE (fpFileInput_sca,riga_lett);
  
         if linea=1 and riga_lett <> 'HH|0|BDUR' then
    
--            dbms_output.put_line ('manca HEADER = HH|0|BDUR'); 
 
            return 1;
         else
            exit when SUBSTR(riga_lett,1,2)= 'FF';
  
            if linea <> 1 then
                pos_1:=instr (riga_lett,'|',1,1); 
                pos_2:=instr (riga_lett,'|',1,2); 
                pos_3:=instr (riga_lett,'|',1,3);
                
                id_prog := to_number(substr(riga_lett,pos_1 + 1,pos_2-pos_1-1));
                stato_rich:= substr(riga_lett,pos_2 + 1,pos_3-pos_2-1);
                mess_scarto:=substr(riga_lett,pos_3 + 1,length(riga_lett));
               
               mess_scarto_log:='';
               
               


           CASE stato_rich
               WHEN 'E' THEN 
                 
               PCK_PBANDI_UTILITY_BATCH.inslogbatch(
               gnIdProcessoBatch,'E145','Per il progetto '||id_prog ||
               ' la richiesta del CUP è stata scartata per la seguente motivazione '||mess_scarto );
               WHEN 'A' THEN
               PCK_PBANDI_UTILITY_BATCH.inslogbatch(
               gnIdProcessoBatch,'W085','Per il progetto '||id_prog
               ||' la richiesta del CUP è stata inoltrata dalla BDU ed è in attesa di restituzione ' );
               ELSE 
                 mess_scarto_log:='';
            END CASE;
               
               
               
               
    
  
            end if;
  
         end if;
  
   
     end loop;
  
     IF UTL_FILE.IS_open(fpFileInput_sca) THEN
        UTL_FILE.fclose(fpFileInput_sca);
     END IF;
     
 
   EXCEPTION
 

     WHEN OTHERS THEN
             PCK_PBANDI_UTILITY_BATCH.inslogbatch(
             gnIdProcessoBatch,'E144',
             'Errore imprevisto durante l¿elaborazione del file '||nome_scartati||
             ' verificare i dati manualmente '||
             sqlerrm||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
             
             PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(gnIdProcessoBatch,'KO');
         return 1; 

    END;


exception
  
    WHEN UTL_FILE.invalid_operation THEN
         
         PCK_PBANDI_UTILITY_BATCH.inslogbatch(gnIdProcessoBatch,'W087',nome_scartati);

WHEN OTHERS THEN
             PCK_PBANDI_UTILITY_BATCH.inslogbatch(
             gnIdProcessoBatch,'E144',
             'Errore imprevisto durante l¿elaborazione del file '||nome_scartati||
             ' verificare i dati manualmente '||
             sqlerrm||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
             
             PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(gnIdProcessoBatch,'KO');
         return 1; 


end;




-- CUP NON TROVATI

begin


fpFileInput_non := UTL_FILE.fopen(pNomeDir,nome_non_trovati, 'R',32767);



BEGIN
  for linea in 1..500 loop
 
         UTL_FILE.GET_LINE (fpFileInput_non,riga_lett);
  
         if linea=1 and riga_lett <> 'HH|0|BDUR' then
    
            dbms_output.put_line ('manca HEADER = HH|0|BDUR'); 
 
            return 1;
         else
            exit when SUBSTR(riga_lett,1,2)= 'FF';
  
            if linea <> 1 then
              
                pos_1:=instr (riga_lett,'|',1,1); 
                pos_2:=instr (riga_lett,'|',1,2); 
               
                
                id_prog := to_number(substr(riga_lett,pos_1 + 1,pos_2-pos_1-1));
                stato_rich:= substr(riga_lett,pos_2 + 1,pos_3-pos_2-1);
                
   
                
               PCK_PBANDI_UTILITY_BATCH.inslogbatch(
                         gnIdProcessoBatch,'W086','Il progetto '||
                id_prog|| ' non è stato trovato in BDU. Verificare manualmente i dati' ); 
 
            end if;
  
         end if;
  
   
     end loop;
  
     IF UTL_FILE.IS_open(fpFileInput_non) THEN
        UTL_FILE.fclose(fpFileInput_non);
     END IF;
   exception
   WHEN OTHERS THEN     
             PCK_PBANDI_UTILITY_BATCH.inslogbatch(
             gnIdProcessoBatch,'E144',
             'Errore imprevisto durante l¿elaborazione del file '||nome_non_trovati||
             ' verificare i dati manualmente '||
             sqlerrm||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
             
             PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(gnIdProcessoBatch,'KO');

END;




exception
WHEN UTL_FILE.invalid_operation THEN  
PCK_PBANDI_UTILITY_BATCH.inslogbatch(gnIdProcessoBatch,'W087',nome_non_trovati);


WHEN OTHERS THEN
             PCK_PBANDI_UTILITY_BATCH.inslogbatch(
             gnIdProcessoBatch,'E144',
             'Errore imprevisto durante l¿elaborazione del file '||nome_non_trovati||
             ' verificare i dati manualmente '||
             sqlerrm||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
             
             PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(gnIdProcessoBatch,'KO');
         return 1; 

end;
PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(gnIdProcessoBatch,'OK');

return 0;
 
end AGGIORNAMENTO_CUP;
      







---
--LO SCRIPT XXXXXXX RICHIEDE ELENCO PROGETTI 
--CHE VERRANNO PASSATI ALLA SHELL java
--PER LA RESTITUZIONE DEI CUP
--VIENE quindi  RICHIAMATA LA FUNZIONE PROGETTI_CUP

--PARAMETRI INPUT : pNomeDir (cartella dove verra creato il file che contiene 
--                  l'elenco degli ID_PROGETTO)
--       
--                  pNomeFile (nome file da creare)
---

--OUTPUT  :LA FUNZIONE CREA FILE pNomeFile SU  pNomeDir   
--         che contiene i progetti per cui viene richiesto il CUP    
--

function PROGETTI_CUP(pNomeDir   VARCHAR2,
                           pNomeFile  VARCHAR2) return number is
  

CURSOR curprog(sist_mitt varchar2) IS SELECT p.id_progetto,sistema_mittente(p.id_progetto)
                    FROM   pbandi_t_progetto p,
                           pbandi_t_dati_progetto_monit mon
                           where 
                           p.cup is null 
                           and mon.flag_richiesta_cup='S'
                           and p.id_progetto=mon.id_progetto
                           and sistema_mittente(p.id_progetto)=sist_mitt
                           order by p.id_progetto;
                    
elenco_prog varchar2(32700);
conteggio number:=1;
linea number:=1;
costante_max number :=400;
vmaxrecord number:=0;
Smittente varchar2(10);

fpFileOutput  UTL_FILE.FILE_TYPE;
                  
begin
  
  elenco_prog:='ELENCO_PROGETTI'||to_char(linea)||'#';
  fpFileOutput := UTL_FILE.fopen(pNomeDir,pNomeFile, 'W',32767);
  
  Smittente:=SUBSTR(pNomeFile,1,INSTR(pNomeFile,'_')-1);

for rec_prog in curprog(Smittente) loop
  vmaxrecord:=vmaxrecord+1;
  
  exit when vmaxrecord >costante_max;

elenco_prog:=elenco_prog||rec_prog.id_progetto||',';

conteggio:=conteggio+1;


if conteggio = 50 then

  UTL_FILE.PUT_LINE(fpFileOutput,elenco_prog);
  linea:=linea + 1;
  elenco_prog:='ELENCO_PROGETTI'||to_char(linea)||'#';
  conteggio:=1;
end if;

end loop;

UTL_FILE.PUT_LINE(fpFileOutput,substr(elenco_prog,1,length(elenco_prog)-1));

-- provvisoria genera il file NON TROVATI dalla shell JAVA
--UTL_FILE.PUT_LINE(fpFileOutput,substr(elenco_prog,1,length(elenco_prog)-1)||'xxxx');


UTL_FILE.PUT_LINE(fpFileOutput,'NUMERO_RIGHE#'||to_char(linea));

 UTL_FILE.fflush(fpFileOutput);
 
 IF UTL_FILE.IS_open(fpFileOutput) THEN
    UTL_FILE.fclose(fpFileOutput);
 END IF;
 
 
 
 return 0;
  
  EXCEPTION
  WHEN OTHERS THEN
    dbms_output.put_line (SQLERRM||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
  
return 1;
end PROGETTI_CUP;



FUNCTION sistema_mittente
                   (pIdProgetto          PBANDI_T_PROGETTO.ID_PROGETTO%TYPE) return varchar2 is

  nIdLineaDiIntervento     PBANDI_R_BANDO_LINEA_INTERVENT.ID_LINEA_DI_INTERVENTO%TYPE;
  nIdLineaInterventoPadre  PBANDI_D_LINEA_DI_INTERVENTO.ID_LINEA_DI_INTERVENTO_PADRE%TYPE;
  nIdTipoLineaIntervento   PBANDI_D_LINEA_DI_INTERVENTO.ID_TIPO_LINEA_INTERVENTO%TYPE;
  nIdLineaDiInterventoOb   PBANDI_R_BANDO_LINEA_INTERVENT.ID_LINEA_DI_INTERVENTO%TYPE;
  nIdSistemaMittente       varchar2(10);
  vCodIgrueT13T14          pbandi_d_linea_di_intervento.cod_igrue_t13_t14%TYPE;


begin
 
 select l_i.id_linea_di_intervento
  into nIdLineaDiIntervento
   from 
  pbandi_t_progetto p,
  pbandi_t_domanda d,
  pbandi_r_bando_linea_intervent l_i
  where
  p.id_domanda=d.id_domanda and
  d.progr_bando_linea_intervento=l_i.progr_bando_linea_intervento and
  p.id_progetto=pIdProgetto;
  
    nIdLineaInterventoPadre := nIdLineaDiIntervento;
    nIdLineaDiInterventoOb  := nIdLineaDiIntervento;
    nIdTipoLineaIntervento  := NULL;
  
    loop
      exit when ((nIdLineaInterventoPadre is null) or (nIdTipoLineaIntervento = 1));
      begin
        SELECT 
               ID_TIPO_LINEA_INTERVENTO,
               ID_LINEA_DI_INTERVENTO_PADRE,
               LDI.COD_IGRUE_T13_T14
        INTO   nIdTipoLineaIntervento,nIdLineaInterventoPadre,
               vCodIgrueT13T14
        FROM   PBANDI_D_LINEA_DI_INTERVENTO ldi,
               PBANDI_D_TIPO_STRUMENTO_PROGR tsp
        WHERE  ldi.ID_LINEA_DI_INTERVENTO  = nIdLineaDiInterventoOb
        AND    ldi.ID_TIPO_STRUMENTO_PROGR = tsp.ID_TIPO_STRUMENTO_PROGR(+);


        if nIdTipoLineaIntervento != 1 AND nIdLineaInterventoPadre is NOT null then
          nIdLineaDiInterventoOb := nIdLineaInterventoPadre;
        end if;

      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          vCodIgrueT13T14 := NULL;

      END;

    END LOOP;





    IF vCodIgrueT13T14 IS NULL THEN
      
      BEGIN
        SELECT 
               LDI.COD_IGRUE_T13_T14
        INTO   vCodIgrueT13T14
        FROM   PBANDI_R_LINEA_INTERV_MAPPING lim,PBANDI_D_LINEA_DI_INTERVENTO ldi,
               PBANDI_D_TIPO_STRUMENTO_PROGR tsp
        WHERE  lim.ID_LINEA_DI_INTERVENTO_MIGRATA = nIdLineaDiInterventoOb
        AND    lim.ID_LINEA_DI_INTERVENTO_ATTUALE = ldi.ID_LINEA_DI_INTERVENTO
        AND    ldi.ID_TIPO_STRUMENTO_PROGR        = tsp.ID_TIPO_STRUMENTO_PROGR(+);

      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          vCodIgrueT13T14:=null;

      end;

    end if;



case   vCodIgrueT13T14
  --FAS
  when '2007PI002FA011' then  nIdSistemaMittente:='PBAN-FAS' ;
  --FSE
  when '2007IT052PO011' then  nIdSistemaMittente:='PBAN-';
  --FESR
  when '2007IT162PO011' then  nIdSistemaMittente:='PBAN-';
   
    else
      nIdSistemaMittente:='????-';
end case;


RETURN nIdSistemaMittente;




end  sistema_mittente;



 
end PCK_PBANDI_RESTITUZIONE_CUP;
/

