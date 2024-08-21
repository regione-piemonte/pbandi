CREATE OR REPLACE FUNCTION FNC_CRONOPROGRAMMA_WA
RETURN NUMBER AS

  vId_progetto      pbandi_t_progetto.id_progetto%type;
  vId_Iter          number:= Null;
  vId_Progetto_k    number:= null;

Begin

For Rec_Cronoprogramma in
  (Select * from PBANDI_W_CRONOPROGRAMMA_WA 
    order by NUMERO_DOMANDA_PBANDI,ID_ITER , ID_FASE_MONIT)
   Loop

     Select t.id_progetto
     Into   vId_progetto
     From   pbandi_t_progetto t,
            pbandi_t_domanda  d
     where  t.id_domanda = d.id_domanda and
           -- t.id_domanda = Rec_Cronoprogramma.Numero_Domanda_Mitt and -- corretto Carla 24/05/2023
            d.numero_domanda = Rec_Cronoprogramma.Numero_Domanda_Pbandi and -- corretto Carla 24/05/2023
            d.progr_bando_linea_intervento = Rec_Cronoprogramma.Progr_Bando_Linea_Intervento;

     Insert Into PBANDI_R_PROGETTO_FASE_MONIT
     (ID_PROGETTO,
        ID_FASE_MONIT,
        DT_INIZIO_PREVISTA,
        DT_FINE_PREVISTA,
        DT_INIZIO_EFFETTIVA,
        DT_FINE_EFFETTIVA,
        ID_UTENTE_INS,
        ID_UTENTE_AGG,
        ID_MOTIVO_SCOSTAMENTO,
        DT_INSERIMENTO,
        DT_AGGIORNAMENTO,
        MOTIVO_SCOSTAMENTO
     ) values
     ( vId_progetto,
       Rec_Cronoprogramma.Id_Fase_Monit,
       NULL,
       Rec_Cronoprogramma.Data_Prevista,
       NULL,
       NULL,
       -1,
       NULL,
       NULL,
       SYSDATE,
       NULL,
       NULL
     );
-- da impostare la rottura di chiave su coppia valori iter/progetto 
--
    UPDATE PBANDI_W_CRONOPROGRAMMA_WA
     SET DATA_CARICAMENTO = SYSDATE
     WHERE NUMERO_DOMANDA_PBANDI = Rec_Cronoprogramma.Numero_Domanda_Pbandi
     and ID_FASE_MONIT=Rec_Cronoprogramma.Id_Fase_Monit;
     
-- se cambia id_iter inserisco sempre     
if vId_Iter <> Rec_Cronoprogramma.Id_Iter then
  
          Insert into PBANDI_R_PROGETTO_ITER 
          (ID_PROGETTO_ITER,
           ID_PROGETTO,
           ID_ITER,
           FLAG_FASE_CHIUSA,
           ID_DICHIARAZIONE_SPESA
          ) values
          (seq_pbandi_r_progetto_iter.nextval,
           vId_progetto,
           vId_Iter,
           NULL,
           NULL
          );
else
-- se id_iter è uguale, verifico se è cambiato il progetto
   if vId_Progetto_k <> vId_Progetto then
      
      Insert into PBANDI_R_PROGETTO_ITER 
          (ID_PROGETTO_ITER,
           ID_PROGETTO,
           ID_ITER,
           FLAG_FASE_CHIUSA,
           ID_DICHIARAZIONE_SPESA
          ) values
          (seq_pbandi_r_progetto_iter.nextval,
           vId_progetto,
           vId_Iter,
           NULL,
           NULL
          );
   end if;
--
end if;      
--
  vId_Iter          := Rec_Cronoprogramma.Id_Iter;
  vId_Progetto_k    := vId_Progetto;
--        
          
      

   End Loop;
   ---

   RETURN 0;
   COMMIT;

   ---

Exception when others then
  Dbms_output.put_line('ERRORE = '||SQLERRM||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
  Rollback;
  Return - 1;
End FNC_CRONOPROGRAMMA_WA;
/
