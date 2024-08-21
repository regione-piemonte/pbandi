CREATE TABLE TMP_UTENTI
(
  CODICE_FISCALE_PERSONA        VARCHAR2(32 BYTE) NOT NULL,
  NOME_PERSONA                  VARCHAR2(150 BYTE) NOT NULL,
  COGNOME_PERSONA               VARCHAR2(150 BYTE) NOT NULL,
  CODICE_FISCALE_BENEFICIARIO   VARCHAR2(32 BYTE),
  FLAG_RAPPRESENTATE_LEGALE     CHAR(1 BYTE),
  DT_CARICAMENTO                DATE,
  ID_SOGGETTO                   NUMBER(8),
  ENTE_COMPETENZA               VARCHAR2(1000 BYTE),
  TIPO_ANAGRAFICA               VARCHAR2(20 BYTE) NOT NULL,
  CODICE_VISUALIZZATO_PROGETTO  VARCHAR2(100 BYTE)
);


ALTER TABLE TMP_UTENTI ADD (
  CONSTRAINT CK_FALG_01
 CHECK (FLAG_RAPPRESENTATE_LEGALE in ('S','N')));


CREATE OR REPLACE PROCEDURE PRC_BANDI_CARICAMENTO_UTENTI IS
/******************************************************************************
   NAME:       PRC_BANDI_CARICAMENTO_UTENTI
   PURPOSE:    Consente di caricare gli utenti a partire dai dati presenti nella
               tabella TMP_UTENTI

   REVISIONS:
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.0        12/05/2009          1. Created this procedure.

   NOTES:

   Automatically available Auto Replace Keywords:
      Object Name:     PRC_BANDI_CARICAMENTO_UTENTI
      Sysdate:         12/05/2009
      Date and Time:   12/05/2009, 11.54.22, and 12/05/2009 11.54.22
      Username:         (set in TOAD Options, Procedure Editor)
      Table Name:       (set in the "New PL/SQL Object" dialog)

******************************************************************************/
  cursor curUtenti is select rowid,ut.*
                      from   tmp_utenti ut     
                      where  ut.DT_CARICAMENTO is null;
  
  TYPE recTbIdProg IS TABLE OF PBANDI_R_SOGGETTO_PROGETTO.ID_PROGETTO%TYPE index by pls_integer;
  
  lstTbIdProg recTbIdProg;
                        
  vNomeBatch          PBANDI_D_NOME_BATCH.NOME_BATCH%TYPE := 'PBAN-SERV-UTENTI';
  nIdProcessoBatch    PBANDI_L_PROCESSO_BATCH.id_processo_batch%TYPE := PCK_PBANDI_UTILITY_BATCH.insprocbatch(vNomeBatch);
  nIdSoggBen          PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE := null;
  nIdSoggPers         PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE;
  nIdSoggUt           PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE;
  nIdUtente           PBANDI_T_UTENTE.ID_UTENTE%TYPE;
  bFound              BOOLEAN := FALSE;
  nIdPersonaFisica    PBANDI_T_PERSONA_FISICA.ID_PERSONA_FISICA%TYPE;
  vCognome            PBANDI_T_PERSONA_FISICA.COGNOME%TYPE;    
  vNome               PBANDI_T_PERSONA_FISICA.NOME%TYPE;
  nProgSoggCorrelati  PBANDI_R_SOGGETTI_CORRELATI.PROGR_SOGGETTI_CORRELATI%TYPE;  
  nProgSoggProg       PBANDI_R_SOGGETTO_PROGETTO.PROGR_SOGGETTO_PROGETTO%TYPE;
  excErrore           EXCEPTION;  
  nCont               PLS_INTEGER;
  nContSta            PLS_INTEGER := 0;
  nContEcs            PLS_INTEGER := 0;
  nIdTipoAnagrafica   PBANDI_D_TIPO_ANAGRAFICA.ID_TIPO_ANAGRAFICA%TYPE;  
  nIdEnte             PBANDI_T_ENTE_COMPETENZA.ID_ENTE_COMPETENZA%TYPE;
  nIdProgetto         PBANDI_T_PROGETTO.ID_PROGETTO%TYPE;
  nPosI               PLS_INTEGER := 0;
  nPosj               PLS_INTEGER := 0;
  vElemCorr           VARCHAR2(100) := null;
  nPosK               PLS_INTEGER := 0;
BEGIN
  FOR recUtenti IN curUtenti LOOP
    BEGIN
    SELECT ta.ID_TIPO_ANAGRAFICA
    INTO   nIdTipoAnagrafica
    FROM   PBANDI_D_TIPO_ANAGRAFICA ta
    WHERE  ta.DESC_BREVE_TIPO_ANAGRAFICA = UPPER(recUtenti.TIPO_ANAGRAFICA);  
  
    BEGIN
      SELECT s.ID_SOGGETTO
      INTO   nIdSoggPers
      FROM   PBANDI_T_SOGGETTO s, PBANDI_D_TIPO_SOGGETTO ts
      WHERE  s.CODICE_FISCALE_SOGGETTO   = recUtenti.CODICE_FISCALE_PERSONA 
      AND    s.ID_TIPO_SOGGETTO          = ts.ID_TIPO_SOGGETTO
      AND    ts.DESC_BREVE_TIPO_SOGGETTO = 'PF';         
      
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        INSERT INTO PBANDI_T_SOGGETTO
        (ID_SOGGETTO, CODICE_FISCALE_SOGGETTO, ID_UTENTE_INS, ID_TIPO_SOGGETTO)
        VALUES
        (decode(recUtenti.ID_SOGGETTO,null,SEQ_PBANDI_T_SOGGETTO.NEXTVAL,recUtenti.ID_SOGGETTO),recUtenti.CODICE_FISCALE_PERSONA,0,1)
        RETURNING ID_SOGGETTO INTO nIdSoggPers;
    END;
    
    BEGIN
      SELECT ID_SOGGETTO,ID_UTENTE
      INTO   nIdSoggUt  ,nIdUtente
      FROM   PBANDI_T_UTENTE
      WHERE  CODICE_UTENTE = recUtenti.CODICE_FISCALE_PERSONA;
        
      if nIdSoggUt != nIdSoggPers then
        PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'W020','COD FISC PERSONA = '||recUtenti.CODICE_FISCALE_PERSONA);
      end if;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        INSERT INTO PBANDI_T_UTENTE
        (ID_UTENTE, CODICE_UTENTE, ID_TIPO_ACCESSO, ID_SOGGETTO )
        VALUES
        (SEQ_PBANDI_T_UTENTE.NEXTVAL,recUtenti.CODICE_FISCALE_PERSONA,5,nIdSoggPers)
        returning ID_UTENTE into nIdUtente;
    END;
    
    BEGIN
      SELECT ID_PERSONA_FISICA,COGNOME, NOME 
      INTO   nIdPersonaFisica,vCognome,vNome  
      FROM   PBANDI_T_PERSONA_FISICA
      WHERE  ID_SOGGETTO                = nIdSoggPers
      AND    UPPER(cognome)             = UPPER(recUtenti.COGNOME_PERSONA)
      AND    NVL(UPPER(nome),'0')       = NVL(UPPER(recUtenti.NOME_PERSONA),'0')
      AND    DT_NASCITA                 IS NULL
      AND    SESSO                      IS NULL
      AND    ID_COMUNE_ITALIANO_NASCITA IS NULL
      AND    ID_COMUNE_ESTERO_NASCITA   IS NULL
      AND    DT_FINE_VALIDITA           IS NULL
      AND    ID_NAZIONE_NASCITA         IS NULL;
    EXCEPTION
      WHEN OTHERS THEN
        INSERT INTO PBANDI_T_PERSONA_FISICA
        (ID_SOGGETTO, COGNOME, NOME,
         ID_PERSONA_FISICA, DT_INIZIO_VALIDITA,ID_UTENTE_INS )
        VALUES
        (nIdSoggPers,recUtenti.COGNOME_PERSONA,recUtenti.NOME_PERSONA,
         SEQ_PBANDI_T_PERSONA_FISICA.NEXTVAL,SYSDATE,0)
        RETURNING ID_PERSONA_FISICA INTO nIdPersonaFisica;
    END;
    
    IF UPPER(recUtenti.TIPO_ANAGRAFICA) NOT IN ('BENEFICIARIO','ADG-ISTRUTTORE','OI-ISTRUTTORE') THEN
      SELECT COUNT(*)
      INTO   nContSta
      FROM   PBANDI_R_SOGG_TIPO_ANAGRAFICA
      WHERE  ID_SOGGETTO        = nIdSoggPers
      AND    ID_TIPO_ANAGRAFICA = nIdTipoAnagrafica; 
      
      IF nContSta = 0 THEN
        INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA
        (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS)
        VALUES
        (nIdSoggPers,nIdTipoAnagrafica,0);
      END IF;  
    END IF;
    
    vElemCorr := NULL;
    nPosI     := 0;
    nPosj     := 0;
    nPosK     := 0;
    
    IF UPPER(recUtenti.TIPO_ANAGRAFICA) IN ('ADG-IST-MASTER','OI-IST-MASTER') THEN
      LOOP
        nPosJ := nPosJ+1;
        nPosI := instr(recUtenti.ENTE_COMPETENZA,',',1,nPosJ);
        EXIT WHEN nPosI=0;

        vElemCorr := SUBSTR(recUtenti.ENTE_COMPETENZA,nPosK+1,(nPosI-1)-nPosK);
        nPosK     := nPosI;
      
        BEGIN
          SELECT ID_ENTE_COMPETENZA
          INTO   nIdEnte
          FROM   PBANDI_T_ENTE_COMPETENZA
          WHERE  DT_FINE_VALIDITA IS NULL
          AND    UPPER(DESC_ENTE) = UPPER(vElemCorr);
        
          SELECT COUNT(*)
          INTO   nContEcs
          FROM   PBANDI_R_ENTE_COMPETENZA_SOGG ecs
          WHERE  ecs.ID_ENTE_COMPETENZA = nIdEnte
          AND    ecs.ID_SOGGETTO        = nIdSoggPers  
          AND    ecs.DT_FINE_VALIDITA   IS NULL;  
        
          IF nContEcs = 0 THEN
            INSERT INTO PBANDI_R_ENTE_COMPETENZA_SOGG
            (ID_ENTE_COMPETENZA, ID_SOGGETTO, ID_UTENTE_INS)
            VALUES
            (nIdEnte,nIdSoggPers,0);
          END IF;  
        EXCEPTION
          WHEN OTHERS THEN
            PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'W054','DESC_ENTE = '||vElemCorr||' '||sqlerrm);
            RAISE excErrore;
        END;
      END LOOP;
    END IF;
    
    IF UPPER(recUtenti.TIPO_ANAGRAFICA) = ('BENEFICIARIO') THEN
      BEGIN
        SELECT ID_SOGGETTO
        INTO   nIdSoggBen
        FROM   PBANDI_T_SOGGETTO
        WHERE  CODICE_FISCALE_SOGGETTO = recUtenti.CODICE_FISCALE_BENEFICIARIO ;
          
        bFound := TRUE;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN 
          begin
            select distinct sp.ID_SOGGETTO
            into   nIdSoggBen
            from   PBANDI_T_SEDE s,PBANDI_R_SOGG_PROGETTO_SEDE ps,
                   PBANDI_R_SOGGETTO_PROGETTO sp
            where  s.PARTITA_IVA              = recUtenti.CODICE_FISCALE_BENEFICIARIO
            and    s.DT_FINE_VALIDITA         is null
            and    ps.ID_SEDE                 = s.ID_SEDE
            and    ps.ID_TIPO_SEDE            = 1  
            and    ps.PROGR_SOGGETTO_PROGETTO = sp.PROGR_SOGGETTO_PROGETTO
            and    sp.DT_FINE_VALIDITA        is null;
              
            bFound := TRUE;
          exception
            when others then
              PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E069','COD FISC BENEF = '||recUtenti.CODICE_FISCALE_BENEFICIARIO);
              bFound     := FALSE;
              nIdSoggBen := null;
          end;    
      END;
      
      IF bFound THEN
        BEGIN
          select PROGR_SOGGETTI_CORRELATI
          into   nProgSoggCorrelati
          from   PBANDI_R_SOGGETTI_CORRELATI
          where  ID_SOGGETTO                = nIdSoggPers
          and    ID_SOGGETTO_ENTE_GIURIDICO = nIdSoggBen 
          and    DT_FINE_VALIDITA           is null   
          and    ID_TIPO_SOGGETTO_CORRELATO = DECODE(recUtenti.FLAG_RAPPRESENTATE_LEGALE,'S',1,6)
          and    ID_CARICA                  is null        
          and    QUOTA_PARTECIPAZIONE       is null
          and    rownum                        = 1;
        exception
          when no_data_found then
            INSERT INTO PBANDI_R_SOGGETTI_CORRELATI
            (ID_TIPO_SOGGETTO_CORRELATO, ID_UTENTE_INS, ID_SOGGETTO, 
             ID_SOGGETTO_ENTE_GIURIDICO, DT_INIZIO_VALIDITA, 
             PROGR_SOGGETTI_CORRELATI)
            VALUES
            (DECODE(recUtenti.FLAG_RAPPRESENTATE_LEGALE,'S',1,6),0,nIdSoggPers,
             nIdSoggBen,sysdate,SEQ_PBANDI_R_SOGG_CORRELATI.NEXTVAL)
            returning PROGR_SOGGETTI_CORRELATI into nProgSoggCorrelati;
          when others then  
            PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E070','nIdSoggPers = '||nIdSoggPers||' '||sqlerrm);
            RAISE excErrore;
        end;
        
        IF recUtenti.CODICE_VISUALIZZATO_PROGETTO IS NULL THEN
          begin
            select distinct id_progetto bulk collect
            into   lstTbIdProg
            from   pbandi_r_soggetto_progetto
            where  id_soggetto        = nIdSoggBen
            and    dt_fine_validita   is null
            and    id_tipo_anagrafica = 1
            and    id_tipo_beneficiario <> 4;
        
            for i in lstTbIdProg.first..lstTbIdProg.last loop
              begin
                select PROGR_SOGGETTO_PROGETTO 
                into   nProgSoggProg
                from   pbandi_r_soggetto_progetto  
                where  ID_PERSONA_FISICA            = nIdPersonaFisica
                and    ID_PROGETTO                  = lstTbIdProg(i)
                and    ID_SOGGETTO                  = nIdSoggPers
                and    ID_TIPO_ANAGRAFICA           = 16
                and    PROGR_SOGGETTO_DOMANDA       is null   
                and    ID_TIPO_BENEFICIARIO         is null  
                and    ID_SOGGETTO_INTERMEDIARIO    is null  
                and    ID_SOGGETTO_CAPOFILA         is null  
                and    ID_RECAPITI_PERSONA_FISICA   is null  
                and    ID_ISCRIZIONE_PERSONA_GIURID is null
                and    ID_INDIRIZZO_PERSONA_FISICA  is null
                and    ID_ESTREMI_BANCARI           is null  
                and    ID_ENTE_GIURIDICO            is null  
                and    ID_DOCUMENTO_PERSONA_FISICA  is null
                and    dt_fine_validita             is null;  
              exception  
                when no_data_found then    
                  insert into pbandi_r_soggetto_progetto
                  (ID_SOGGETTO, ID_PROGETTO, PROGR_SOGGETTO_PROGETTO, 
                   DT_INIZIO_VALIDITA,ID_TIPO_ANAGRAFICA, ID_PERSONA_FISICA, id_utente_ins )
                  values
                  (nIdSoggPers,lstTbIdProg(i),seq_pbandi_r_soggetto_progetto.nextval,
                   sysdate,16,nIdPersonaFisica,0)
                  returning PROGR_SOGGETTO_PROGETTO into nProgSoggProg;
              end;
            
              select count(*)
              into   nCont
              from   pbandi_r_sogg_prog_sogg_correl
              where  PROGR_SOGGETTO_PROGETTO  = nProgSoggProg
              and    PROGR_SOGGETTI_CORRELATI = nProgSoggCorrelati; 
                    
              if nCont = 0 then  
                insert into pbandi_r_sogg_prog_sogg_correl
                (PROGR_SOGGETTO_PROGETTO, PROGR_SOGGETTI_CORRELATI, ID_UTENTE_INS )
                values
                (nProgSoggProg,nProgSoggCorrelati,0);
              end if;
            end loop;
          EXCEPTION
            WHEN NO_DATA_FOUND THEN
              PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E071','nIdSoggBen = '||nIdSoggBen);
              RAISE excErrore;
          end;   
        ELSE
          BEGIN
            SELECT ID_PROGETTO
            INTO   nIdProgetto
            FROM   PBANDI_T_PROGETTO
            WHERE  CODICE_VISUALIZZATO = recUtenti.CODICE_VISUALIZZATO_PROGETTO;
            
            begin
              select PROGR_SOGGETTO_PROGETTO 
              into   nProgSoggProg
              from   pbandi_r_soggetto_progetto  
              where  ID_PERSONA_FISICA            = nIdPersonaFisica
              and    ID_PROGETTO                  = nIdProgetto
              and    ID_SOGGETTO                  = nIdSoggPers
              and    ID_TIPO_ANAGRAFICA           = 16
              and    PROGR_SOGGETTO_DOMANDA       is null   
              and    ID_TIPO_BENEFICIARIO         is null  
              and    ID_SOGGETTO_INTERMEDIARIO    is null  
              and    ID_SOGGETTO_CAPOFILA         is null  
              and    ID_RECAPITI_PERSONA_FISICA   is null  
              and    ID_ISCRIZIONE_PERSONA_GIURID is null
              and    ID_INDIRIZZO_PERSONA_FISICA  is null
              and    ID_ESTREMI_BANCARI           is null  
              and    ID_ENTE_GIURIDICO            is null  
              and    ID_DOCUMENTO_PERSONA_FISICA  is null
              and    dt_fine_validita             is null;  
            exception  
              when no_data_found then    
                insert into pbandi_r_soggetto_progetto
                (ID_SOGGETTO, ID_PROGETTO, PROGR_SOGGETTO_PROGETTO, 
                 DT_INIZIO_VALIDITA,ID_TIPO_ANAGRAFICA, ID_PERSONA_FISICA, id_utente_ins )
                values
                (nIdSoggPers,nIdProgetto,seq_pbandi_r_soggetto_progetto.nextval,
                 sysdate,16,nIdPersonaFisica,0)
                returning PROGR_SOGGETTO_PROGETTO into nProgSoggProg;
            end;
            
            select count(*)
            into   nCont
            from   pbandi_r_sogg_prog_sogg_correl
            where  PROGR_SOGGETTO_PROGETTO  = nProgSoggProg
            and    PROGR_SOGGETTI_CORRELATI = nProgSoggCorrelati; 
                
            if nCont = 0 then  
              insert into pbandi_r_sogg_prog_sogg_correl
              (PROGR_SOGGETTO_PROGETTO, PROGR_SOGGETTI_CORRELATI, ID_UTENTE_INS )
              values
              (nProgSoggProg,nProgSoggCorrelati,0);
            end if;
          EXCEPTION
            WHEN OTHERS THEN
              PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E071','CODICE_VISUALIZZATO = '||recUtenti.CODICE_VISUALIZZATO_PROGETTO);
              RAISE excErrore;
          END; 
          
        END IF;
        
        
      END IF;
    END IF;
    update tmp_utenti
    set    DT_CARICAMENTO = sysdate
    where  rowid          = recUtenti.rowid;
    COMMIT; 
    EXCEPTION
        WHEN excErrore THEN
          ROLLBACK;    
      END;
  END LOOP;
  
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('ERRORE = '||SQLERRM||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;  
END PRC_BANDI_CARICAMENTO_UTENTI;
/
