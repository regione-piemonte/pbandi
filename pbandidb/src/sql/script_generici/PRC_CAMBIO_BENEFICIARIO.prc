CREATE OR REPLACE PROCEDURE prc_cambio_beneficiario(pNuovoCodFiscale  PBANDI_T_SOGGETTO.CODICE_FISCALE_SOGGETTO%TYPE,
                                                    pIdProgetto       PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                                    pCaso             VARCHAR2) IS  
  
  CURSOR curSoggCorr IS select distinct PROGR_SOGGETTI_CORRELATI
                        from PBANDI_R_SOGG_PROG_SOGG_CORREL
                        where PROGR_SOGGETTO_PROGETTO in (select sp.PROGR_SOGGETTO_PROGETTO
                                                          from   PBANDI_T_PROGETTO p,PBANDI_R_SOGGETTO_PROGETTO sp
                                                          WHERE  p.ID_PROGETTO                 = pIdProgetto
                                                          AND    sp.ID_PROGETTO                = p.ID_PROGETTO
                                                          and    ID_TIPO_ANAGRAFICA   != 1) ;           
  
  vNomeBatch                  PBANDI_D_NOME_BATCH.NOME_BATCH%TYPE := 'PBAN-SERV-BENEFIC';
  nIdProcessoBatch            PBANDI_L_PROCESSO_BATCH.ID_PROCESSO_BATCH%TYPE := PCK_PBANDI_UTILITY_BATCH.insprocbatch(vNomeBatch);
  nIdSoggettoOld              PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE;
  excErrore                   EXCEPTION;
  nIdTipoSoggetto             PBANDI_T_SOGGETTO.ID_TIPO_SOGGETTO%TYPE;
  nIdSoggettoNew              PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE;
  nProgrSoggettoProgetto      PBANDI_R_SOGGETTO_PROGETTO.PROGR_SOGGETTO_PROGETTO%TYPE;    
  nProgrSoggettoProgettoNew   PBANDI_R_SOGGETTO_PROGETTO.PROGR_SOGGETTO_PROGETTO%TYPE;
  nProgrSoggettiCorrelati     PBANDI_R_SOGGETTI_CORRELATI.PROGR_SOGGETTI_CORRELATI%TYPE;
  nIdSoggCorr                 PBANDI_R_SOGGETTI_CORRELATI.ID_SOGGETTO%TYPE; 
  nIdSoggCorrEg               PBANDI_R_SOGGETTI_CORRELATI.ID_SOGGETTO_ENTE_GIURIDICO%TYPE;
  nProgrSoggettiCorrelatiBen  PBANDI_R_SOGGETTI_CORRELATI.PROGR_SOGGETTI_CORRELATI%TYPE;
  nCont                       PLS_INTEGER;          
BEGIN
  BEGIN
    SELECT sp.PROGR_SOGGETTO_PROGETTO,sp.ID_SOGGETTO,s.ID_TIPO_SOGGETTO
    INTO   nProgrSoggettoProgetto,nIdSoggettoOld,nIdTipoSoggetto
    FROM   PBANDI_R_SOGGETTO_PROGETTO sp,PBANDI_T_SOGGETTO s
    WHERE  sp.ID_PROGETTO           = pIdProgetto
    AND    sp.DT_FINE_VALIDITA      IS NULL
    AND    sp.ID_TIPO_BENEFICIARIO != 4 
    AND    sp.ID_TIPO_ANAGRAFICA    = 1
    AND    sp.ID_SOGGETTO           = s.ID_SOGGETTO;  
  EXCEPTION
    WHEN OTHERS THEN
      PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E090','PROGETTO = '||pIdProgetto||' '||sqlerrm);
      RAISE excErrore;
  END;
    
  BEGIN
    SELECT s.ID_SOGGETTO
    INTO   nIdSoggettoNew
    FROM   PBANDI_T_SOGGETTO s
    WHERE  s.CODICE_FISCALE_SOGGETTO = pNuovoCodFiscale
    AND    s.ID_TIPO_SOGGETTO        = nIdTipoSoggetto;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      INSERT INTO PBANDI_T_SOGGETTO
      (ID_SOGGETTO, CODICE_FISCALE_SOGGETTO, ID_UTENTE_INS, ID_TIPO_SOGGETTO)
      VALUES
      (SEQ_PBANDI_T_SOGGETTO.NEXTVAL,pNuovoCodFiscale,-2,nIdTipoSoggetto)
      RETURNING ID_SOGGETTO INTO nIdSoggettoNew;
        
      IF nIdTipoSoggetto = 1 THEN
        INSERT INTO PBANDI_T_UTENTE
        (ID_UTENTE, CODICE_UTENTE, ID_TIPO_ACCESSO)
        VALUES
        (SEQ_PBANDI_T_UTENTE.NEXTVAL,pNuovoCodFiscale,5);
      END IF;
    WHEN OTHERS THEN
      PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E090','SOGGETTO = '||pIdProgetto||' '||sqlerrm);
      RAISE excErrore;
  END;
  
  SELECT COUNT(*)
  INTO   nCont
  FROM   PBANDI_T_PROGETTO
  WHERE  ID_PROGETTO_PADRE = pIdProgetto;
  
  IF nCont > 0 THEN
    UPDATE PBANDI_R_SOGGETTO_PROGETTO
    SET    ID_UTENTE_AGG        = -2,
           ID_SOGGETTO_CAPOFILA = nIdSoggettoNew 
    WHERE  ID_PROGETTO          IN (SELECT ID_PROGETTO
                                    FROM   PBANDI_T_PROGETTO
                                    WHERE  ID_PROGETTO_PADRE = pIdProgetto)
    AND    ID_TIPO_ANAGRAFICA   = 1; 
  END IF;                                       
  
  IF pCaso = 'A' THEN
    
    UPDATE PBANDI_R_SOGGETTO_PROGETTO
    SET    ID_UTENTE_AGG           = -2,
           ID_SOGGETTO             = nIdSoggettoNew 
    WHERE  PROGR_SOGGETTO_PROGETTO = nProgrSoggettoProgetto;
    
    
    BEGIN
      FOR recSoggCorr IN curSoggCorr LOOP
        BEGIN
          SELECT PROGR_SOGGETTI_CORRELATI
          INTO   nProgrSoggettiCorrelati
          FROM   PBANDI_R_SOGGETTI_CORRELATI
          WHERE  (ID_SOGGETTO,ID_SOGGETTO_ENTE_GIURIDICO,ID_TIPO_soggetto_correlato,nvl(ID_CARICA,0),nvl(QUOTA_PARTECIPAZIONE,0)) IN
             (SELECT ID_SOGGETTO,nIdSoggettoNew,ID_TIPO_soggetto_correlato,nvl(ID_CARICA,0),nvl(QUOTA_PARTECIPAZIONE,0)
              FROM   PBANDI_R_SOGGETTI_CORRELATI
              WHERE  PROGR_SOGGETTI_CORRELATI = recSoggCorr.PROGR_SOGGETTI_CORRELATI)
          AND  ROWNUM = 1;
        EXCEPTION
          WHEN NO_DATA_FOUND THEN
            SELECT SEQ_PBANDI_R_SOGG_CORRELATI.NEXTVAL
            INTO   nProgrSoggettiCorrelati
            FROM   DUAL;
        
            INSERT INTO PBANDI_R_SOGGETTI_CORRELATI
            (ID_TIPO_SOGGETTO_CORRELATO, ID_CARICA, ID_UTENTE_INS, QUOTA_PARTECIPAZIONE, 
             ID_SOGGETTO, ID_SOGGETTO_ENTE_GIURIDICO, DT_INIZIO_VALIDITA, 
             PROGR_SOGGETTI_CORRELATI)
            (select ID_TIPO_SOGGETTO_CORRELATO,ID_CARICA, -2,QUOTA_PARTECIPAZIONE, 
                    ID_SOGGETTO,nIdSoggettoNew,DT_INIZIO_VALIDITA, 
                    nProgrSoggettiCorrelati
             FROM   PBANDI_R_SOGGETTI_CORRELATI
             WHERE  PROGR_SOGGETTI_CORRELATI = recSoggCorr.PROGR_SOGGETTI_CORRELATI);
        END;
        
        begin
        UPDATE PBANDI_R_SOGG_PROG_SOGG_CORREL
        SET    PROGR_SOGGETTI_CORRELATI = nProgrSoggettiCorrelati,
               ID_UTENTE_AGG            = -2 
        WHERE  PROGR_SOGGETTO_PROGETTO  IN (SELECT PROGR_SOGGETTO_PROGETTO
                                           FROM   PBANDI_R_SOGG_PROG_SOGG_CORREL
                                           WHERE  PROGR_SOGGETTI_CORRELATI = recSoggCorr.PROGR_SOGGETTI_CORRELATI
                                           AND    PROGR_SOGGETTO_PROGETTO in (select sp.PROGR_SOGGETTO_PROGETTO
                                                                              from   PBANDI_T_PROGETTO p,PBANDI_R_SOGGETTO_PROGETTO sp
                                                                              WHERE  p.ID_PROGETTO                 = pIdProgetto
                                                                              AND    sp.ID_PROGETTO                = p.ID_PROGETTO
                                                                              AND    ID_TIPO_ANAGRAFICA           != 1));
        exception
          when others then
            null;
        end;                                                                                  
      END LOOP;
      
      BEGIN
        select distinct PROGR_SOGGETTI_CORRELATI
        into   nProgrSoggettiCorrelatiBen
        from   PBANDI_R_SOGG_PROG_SOGG_CORREL
        where  PROGR_SOGGETTO_PROGETTO in (select sp.PROGR_SOGGETTO_PROGETTO
                                           from   PBANDI_T_PROGETTO p,PBANDI_R_SOGGETTO_PROGETTO sp
                                           WHERE  p.ID_PROGETTO         = pIdProgetto
                                           AND    sp.ID_PROGETTO        = p.ID_PROGETTO
                                           and    ID_TIPO_BENEFICIARIO != 4   
                                           and    ID_TIPO_ANAGRAFICA    = 1);
        
        BEGIN
          SELECT PROGR_SOGGETTI_CORRELATI
          INTO   nProgrSoggettiCorrelati
          FROM   PBANDI_R_SOGGETTI_CORRELATI
          WHERE  (ID_SOGGETTO,ID_SOGGETTO_ENTE_GIURIDICO,ID_TIPO_soggetto_correlato,nvl(ID_CARICA,0),nvl(QUOTA_PARTECIPAZIONE,0)) IN
             (SELECT nIdSoggettoNew,ID_SOGGETTO_ENTE_GIURIDICO,ID_TIPO_soggetto_correlato,nvl(ID_CARICA,0),nvl(QUOTA_PARTECIPAZIONE,0)
              FROM   PBANDI_R_SOGGETTI_CORRELATI
              WHERE  PROGR_SOGGETTI_CORRELATI = nProgrSoggettiCorrelatiBen)
          AND  ROWNUM = 1;
        EXCEPTION
          WHEN NO_DATA_FOUND THEN
            SELECT SEQ_PBANDI_R_SOGG_CORRELATI.NEXTVAL
            INTO   nProgrSoggettiCorrelati
            FROM   DUAL;
        
            INSERT INTO PBANDI_R_SOGGETTI_CORRELATI
            (ID_TIPO_SOGGETTO_CORRELATO, ID_CARICA, ID_UTENTE_INS, QUOTA_PARTECIPAZIONE, 
             ID_SOGGETTO, ID_SOGGETTO_ENTE_GIURIDICO, DT_INIZIO_VALIDITA, 
             PROGR_SOGGETTI_CORRELATI)
            (select ID_TIPO_SOGGETTO_CORRELATO,ID_CARICA, -2,QUOTA_PARTECIPAZIONE, 
                    nIdSoggettoNew,ID_SOGGETTO_ENTE_GIURIDICO,DT_INIZIO_VALIDITA, 
                    nProgrSoggettiCorrelati
             FROM   PBANDI_R_SOGGETTI_CORRELATI
             WHERE  PROGR_SOGGETTI_CORRELATI = nProgrSoggettiCorrelatiBen);
        END;
        
        UPDATE PBANDI_R_SOGG_PROG_SOGG_CORREL
        SET    PROGR_SOGGETTI_CORRELATI = nProgrSoggettiCorrelati,
               ID_UTENTE_AGG            = -2
        WHERE  PROGR_SOGGETTO_PROGETTO  IN (SELECT PROGR_SOGGETTO_PROGETTO
                                           FROM   PBANDI_R_SOGG_PROG_SOGG_CORREL
                                           WHERE  PROGR_SOGGETTI_CORRELATI = nProgrSoggettiCorrelatiBen
                                           AND    PROGR_SOGGETTO_PROGETTO in (select sp.PROGR_SOGGETTO_PROGETTO
                                                                              from   PBANDI_T_PROGETTO p,PBANDI_R_SOGGETTO_PROGETTO sp
                                                                              WHERE  p.ID_PROGETTO         = pIdProgetto
                                                                              AND    sp.ID_PROGETTO        = p.ID_PROGETTO
                                                                              and    ID_TIPO_BENEFICIARIO != 4   
                                                                              and    ID_TIPO_ANAGRAFICA    = 1));        
        
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          nProgrSoggettiCorrelatiBen := NULL;
      END;    
    EXCEPTION
      WHEN OTHERS THEN
        PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E090','PBANDI_R_SOGGETTI_CORRELATI = '||pIdProgetto||' '||sqlerrm||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
        RAISE excErrore;
    END;
    
  ELSIF pCaso = 'B' THEN
   
    UPDATE PBANDI_R_SOGGETTO_PROGETTO
    SET    ID_SOGGETTO                  = nIdSoggettoNew,
           ID_UTENTE_AGG                = -2, 
           DT_FINE_VALIDITA             = NULL,
           ID_ESTREMI_BANCARI           = NULL,
           ID_PERSONA_FISICA            = NULL, 
           ID_ENTE_GIURIDICO            = NULL, 
           ID_INDIRIZZO_PERSONA_FISICA  = NULL,
           ID_RECAPITI_PERSONA_FISICA   = NULL, 
           ID_DOCUMENTO_PERSONA_FISICA  = NULL,
           ID_ISCRIZIONE_PERSONA_GIURID = NULL
    WHERE  PROGR_SOGGETTO_PROGETTO = nProgrSoggettoProgetto;
    
    DELETE PBANDI_R_SOGG_PROG_SOGG_CORREL
    WHERE  PROGR_SOGGETTO_PROGETTO IN (SELECT PROGR_SOGGETTO_PROGETTO
                                       FROM   PBANDI_R_SOGGETTO_PROGETTO
                                       WHERE  ID_PROGETTO       = pIdProgetto
                                       AND    ID_SOGGETTO      != nIdSoggettoNew
                                       AND    ID_TIPO_ANAGRAFICA IN (15,16,17));
    
    DELETE PBANDI_R_SOGGETTO_PROGETTO
    WHERE  ID_PROGETTO       = pIdProgetto
    AND    ID_SOGGETTO      != nIdSoggettoNew
    AND    ID_TIPO_ANAGRAFICA IN (15,16,17);
    
    DELETE PBANDI_R_SOGG_PROG_SOGG_CORREL
    WHERE  PROGR_SOGGETTO_PROGETTO = nProgrSoggettoProgetto;
    

  END IF;
  
  DELETE PBANDI_R_SOGGETTI_CORRELATI SC
  WHERE NOT EXISTS (SELECT 'X' 
                    FROM   PBANDI_R_SOGG_PROG_SOGG_CORREL SPSC
                    WHERE  SC.PROGR_SOGGETTI_CORRELATI = SPSC.PROGR_SOGGETTI_CORRELATI)
  AND   NOT EXISTS (SELECT 'X' 
                    FROM   PBANDI_R_SOGG_DOM_SOGG_CORREL SDSC
                    WHERE  SC.PROGR_SOGGETTI_CORRELATI = SDSC.PROGR_SOGGETTI_CORRELATI);                 
  
  UPDATE PBANDI_R_PROG_SOGG_FINANZIAT 
  SET    ID_SOGGETTO = nIdSoggettoNew
  WHERE  ID_PROGETTO = pIdProgetto
  AND    ID_SOGGETTO = nIdSoggettoOld;
  
  COMMIT;
EXCEPTION
  WHEN excErrore THEN
    ROLLBACK;
  WHEN OTHERS THEN
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E090','ERRORE = '||SQLERRM||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;          
END prc_cambio_beneficiario;
/
