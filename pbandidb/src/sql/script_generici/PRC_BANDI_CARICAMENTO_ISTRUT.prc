CREATE OR REPLACE PROCEDURE PRC_BANDI_CARICAMENTO_ISTRUT IS
/******************************************************************************
   NAME:       PRC_BANDI_CARICAMENTO_ISTRUT
   PURPOSE:    Consente di caricare gli utenti a partire dai dati presenti nella
               tabella TMP_ISTRUTTORI

   REVISIONS:
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.0        25/06/2009          1. Created this procedure.

   NOTES:

   Automatically available Auto Replace Keywords:
      Object Name:     PRC_BANDI_CARICAMENTO_ISTRUT
      Sysdate:         25/06/2009
      Date and Time:   25/06/2009, 11.54.22, and 25/06/2009 11.54.22
      Username:         (set in TOAD Options, Procedure Editor)
      Table Name:       (set in the "New PL/SQL Object" dialog)

******************************************************************************/
  cursor curIstrutt is select rowid,ist.*
                       from   tmp_istruttori ist     
                       where  ist.DT_CARICAMENTO is null;
  
  vNomeBatch          PBANDI_D_NOME_BATCH.NOME_BATCH%TYPE := 'PBAN-SERV-ISTRUTT';
  nIdProcessoBatch    PBANDI_L_PROCESSO_BATCH.id_processo_batch%TYPE := PCK_PBANDI_UTILITY_BATCH.insprocbatch(vNomeBatch);
  nIdEnteComp         PBANDI_T_ENTE_COMPETENZA.ID_ENTE_COMPETENZA%TYPE;  
  excErrore           EXCEPTION;
  nIdDirezione        PBANDI_D_DIREZIONE_REGIONALE.ID_DIREZIONE_REGIONALE%TYPE;
  nIdEnte             PBANDI_T_ENTE_COMPETENZA.ID_ENTE_COMPETENZA%TYPE;
  nIdSoggPers         PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE; 
  nIdTipoAnagrafica   PBANDI_D_TIPO_ANAGRAFICA.ID_TIPO_ANAGRAFICA%TYPE; 
  nIdSoggUtente       PBANDI_T_UTENTE.ID_SOGGETTO%TYPE;
  nIdSoggettoEnte     PBANDI_R_ENTE_COMPETENZA_SOGG.ID_SOGGETTO%TYPE;
  nCont               PLS_INTEGER;
BEGIN
  FOR recIstrutt IN curIstrutt LOOP
    BEGIN
      IF UPPER(recIstrutt.ENTE_COMPETENZA) = 'REGIONE PIEMONTE' THEN
        BEGIN
          SELECT ID_DIREZIONE_REGIONALE
          INTO   nIdDirezione
          FROM   PBANDI_D_DIREZIONE_REGIONALE
          WHERE  UPPER(DESC_BREVE_DIREZIONE_REGIONALE) = UPPER(recIstrutt.DIREZIONE);
        EXCEPTION
          WHEN OTHERS THEN
            PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E074','DIREZIONE = '||recIstrutt.DIREZIONE||' '||sqlerrm);
            RAISE excErrore;
        END;
      ELSE
        nIdDirezione := NULL;
      END IF;
      
      BEGIN
        SELECT ID_ENTE_COMPETENZA
        INTO   nIdEnte
        FROM   PBANDI_T_ENTE_COMPETENZA
        WHERE  DT_FINE_VALIDITA              IS NULL
        AND    UPPER(DESC_ENTE)              like UPPER(recIstrutt.ENTE_COMPETENZA)||'%'
        AND    NVL(ID_DIREZIONE_REGIONALE,0) = NVL(nIdDirezione,0) 
        AND    ID_SETTORE                    IS NULL;
      EXCEPTION
        WHEN OTHERS THEN
          PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E074','nIdEnteComp = '||nIdEnteComp||' nIdDirezione = '||nIdDirezione||' '||sqlerrm);
          RAISE excErrore;
      END;
      
      BEGIN
        SELECT ID_SOGGETTO
        INTO   nIdSoggPers
        FROM   PBANDI_T_SOGGETTO
        WHERE  CODICE_FISCALE_SOGGETTO = recIstrutt.CODICE_FISCALE_ISTRUTTORE
        AND    ID_TIPO_SOGGETTO        = 1;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          IF UPPER(recIstrutt.ENTE_COMPETENZA) = 'FINPIEMONTE' AND recIstrutt.FLAG_MASTER = 'S' THEN
            nIdTipoAnagrafica := 4;
          ELSIF UPPER(recIstrutt.ENTE_COMPETENZA) = 'FINPIEMONTE' AND recIstrutt.FLAG_MASTER = 'N' THEN
            nIdTipoAnagrafica := null;
          ELSIF UPPER(recIstrutt.ENTE_COMPETENZA) = 'REGIONE PIEMONTE' AND recIstrutt.FLAG_MASTER = 'S' THEN
            nIdTipoAnagrafica := 2;  
          ELSIF UPPER(recIstrutt.ENTE_COMPETENZA) = 'REGIONE PIEMONTE' AND recIstrutt.FLAG_MASTER = 'N' THEN
            nIdTipoAnagrafica := null;
          ELSE
            nIdTipoAnagrafica := NULL;
          END IF; 
                    
          INSERT INTO PBANDI_T_SOGGETTO
          (ID_SOGGETTO, CODICE_FISCALE_SOGGETTO, 
           ID_UTENTE_INS, ID_TIPO_SOGGETTO,ID_TIPO_ANAGRAFICA)
          VALUES
          (decode(recIstrutt.Id_soggetto,null,SEQ_PBANDI_T_SOGGETTO.NEXTVAL,recIstrutt.Id_soggetto),recIstrutt.CODICE_FISCALE_ISTRUTTORE,
           0,1,nIdTipoAnagrafica)
          returning ID_SOGGETTO into nIdSoggPers;
      END;    
      
      BEGIN
        SELECT ID_SOGGETTO
        INTO   nIdSoggUtente
        FROM   PBANDI_T_UTENTE
        WHERE  CODICE_UTENTE = recIstrutt.CODICE_FISCALE_ISTRUTTORE;
        
        IF nIdSoggUtente != nIdSoggPers THEN
          PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'W036','CODICE_FISCALE_ISTRUTTORE = '||recIstrutt.CODICE_FISCALE_ISTRUTTORE||' nIdSoggUtente = '||nIdSoggUtente||' nIdSoggPers = '||nIdSoggPers); 
        END IF;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          INSERT INTO PBANDI_T_UTENTE
          (ID_UTENTE, CODICE_UTENTE, ID_TIPO_ACCESSO, ID_SOGGETTO)
          VALUES
          (SEQ_PBANDI_T_UTENTE.NEXTVAL,recIstrutt.CODICE_FISCALE_ISTRUTTORE,5,nIdSoggPers);
      END;
            
      SELECT COUNT(*) 
      INTO   nCont
      FROM   PBANDI_T_PERSONA_FISICA
      WHERE  ID_SOGGETTO                = nIdSoggPers
      and    upper(cognome)             = upper(recIstrutt.COGNOME_ISTRUTTORE)
      and    nvl(upper(nome),'0')       = nvl(upper(recIstrutt.NOME_ISTRUTTORE),'0')
      and    DT_NASCITA                 is null
      and    SESSO                      is null
      and    ID_COMUNE_ITALIANO_NASCITA is null
      and    ID_COMUNE_ESTERO_NASCITA   is null
      and    ID_NAZIONE_NASCITA         is null;          
      
      IF nCont != 1 THEN           
        INSERT INTO PBANDI_T_PERSONA_FISICA
        (ID_SOGGETTO, COGNOME, NOME, 
         ID_PERSONA_FISICA, DT_INIZIO_VALIDITA,ID_UTENTE_INS )
            VALUES
        (nIdSoggPers,recIstrutt.COGNOME_ISTRUTTORE,recIstrutt.NOME_ISTRUTTORE,
         SEQ_PBANDI_T_PERSONA_FISICA.NEXTVAL,SYSDATE,0);
      END IF;
      
      BEGIN
        SELECT ID_SOGGETTO
        into   nidsoggettoente
        FROM   PBANDI_R_ENTE_COMPETENZA_SOGG
        WHERE  ID_SOGGETTO = nIdSoggPers
        AND    ID_ENTE_COMPETENZA = nIdEnte
        AND    dt_fine_validita is null;
        
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          INSERT INTO PBANDI_R_ENTE_COMPETENZA_SOGG
          (ID_ENTE_COMPETENZA, ID_SOGGETTO, ID_UTENTE_INS)
          VALUES
          (nIdEnte,nIdSoggPers,0);
      END;
    
      update tmp_istruttori
      set    DT_CARICAMENTO = sysdate
      where  rowid          = recIstrutt.rowid;
        
      commit; 
    
    EXCEPTION
      WHEN excErrore THEN
        ROLLBACK;    
    END;
  END LOOP;
  COMMIT;
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('ERRORE = '||SQLERRM||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;  
END PRC_BANDI_CARICAMENTO_ISTRUT;
/
