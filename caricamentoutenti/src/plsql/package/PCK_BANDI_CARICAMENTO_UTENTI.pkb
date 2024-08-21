/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE OR REPLACE PACKAGE BODY PCK_BANDI_CARICAMENTO_UTENTI AS

  gvNomeBatch        PBANDI_D_NOME_BATCH.NOME_BATCH%TYPE := 'PBAN-UTENTI-MASSIVA';
  gnIdProcessoBatch  PBANDI_L_PROCESSO_BATCH.ID_PROCESSO_BATCH%TYPE;
  nIdLogBatch        PBANDI_L_LOG_BATCH.ID_LOG_BATCH%TYPE;

-- Funzione main
FUNCTION Main(pNomeOggDirIn   VARCHAR2,
              pNomeOggDirOut  VARCHAR2,
              pNomeFile       VARCHAR2) RETURN NUMBER IS

  nIdFlusso  PBANDI_T_UTENTI_UPLOAD.ID_FLUSSO%TYPE;
  nFn        PLS_INTEGER;
BEGIN
  gnIdProcessoBatch := PCK_PBANDI_UTILITY_BATCH.insprocbatch(gvNomeBatch);

  UPDATE PBANDI_T_FLUSSI_UPLOAD
  SET    ID_PROCESSO_BATCH = gnIdProcessoBatch
  WHERE  NOME_FLUSSO       = pNomeFile
  RETURNING ID_FLUSSO INTO nIdFlusso;
  
  nFn := CaricamentoCsv(pNomeOggDirIn,
                        pNomeFile,
                        nIdFlusso);

  IF nFn = 1 THEN
    ROLLBACK;
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(gnIdProcessoBatch,'KO');
    RETURN 1;
  END IF;

  nFn := CaricamentoUtenti(nIdFlusso);

  IF nFn = 1 THEN
    ROLLBACK;
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(gnIdProcessoBatch,'KO');
    RETURN 1;
  END IF;

  nFn := FileEsiti(pNomeOggDirOut,
                   pNomeFile,
                   nIdFlusso);

  IF nFn = 1 THEN
    ROLLBACK;
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(gnIdProcessoBatch,'KO');
    RETURN 1;
  END IF;

  COMMIT;
  PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(gnIdProcessoBatch,'OK');
  RETURN 0;
END Main;

-- Riceve in input il file csv da caricare sulla tabella UTENTI_UPLOAD
FUNCTION CaricamentoCsv(pNomeOggDir  VARCHAR2,
                        pNomeFile    VARCHAR2,
                        pIdFlusso    PBANDI_T_UTENTI_UPLOAD.ID_FLUSSO%TYPE) RETURN NUMBER IS

  fpFileOutput             UTL_FILE.FILE_TYPE;
  vRiga                    VARCHAR2(4000);
  vCodFiscale              PBANDI_T_UTENTI_UPLOAD.COD_FISCALE%TYPE;
  vNome                    PBANDI_T_UTENTI_UPLOAD.NOME%TYPE;
  vCognome                 PBANDI_T_UTENTI_UPLOAD.COGNOME%TYPE;
  vCodiceVisualizzato      PBANDI_T_UTENTI_UPLOAD.CODICE_VISUALIZZATO%TYPE;
  vFlagRapprLegale         PBANDI_T_UTENTI_UPLOAD.FLAG_RAPPR_LEGALE%TYPE;
  vCodFiscaleBeneficiario  PBANDI_T_UTENTI_UPLOAD.COD_FISCALE_BENEFICIARIO%TYPE;
BEGIN
  fpFileOutput := UTL_FILE.fopen(pNomeOggDir,pNomeFile, 'R',32767);

  LOOP
    BEGIN
      UTL_FILE.GET_LINE(fpFileOutput,vRiga,32767);

      IF (LENGTH(vRiga)>0) THEN
        vRiga := vRiga||';';

        vCodFiscale             := SUBSTR(vRiga,1,INSTR(vRiga,';',1,1)-1);
        vNome                   := SUBSTR(vRiga,INSTR(vRiga,';',1,1)+1,INSTR(vRiga,';',1,2) - INSTR(vRiga,';',1,1) -1);
        vCognome                := SUBSTR(vRiga,INSTR(vRiga,';',1,2)+1,INSTR(vRiga,';',1,3) - INSTR(vRiga,';',1,2) -1);
        vCodiceVisualizzato     := SUBSTR(vRiga,INSTR(vRiga,';',1,3)+1,INSTR(vRiga,';',1,4) - INSTR(vRiga,';',1,3) -1);
        vFlagRapprLegale        := SUBSTR(vRiga,INSTR(vRiga,';',1,4)+1,INSTR(vRiga,';',1,5) - INSTR(vRiga,';',1,4) -1);
        vCodFiscaleBeneficiario := REPLACE(SUBSTR(vRiga,INSTR(vRiga,';',1,5)+1,INSTR(vRiga,';',1,6) - INSTR(vRiga,';',1,5) -1), CHR(13), '');

        INSERT INTO PBANDI_T_UTENTI_UPLOAD
        (ID_UTENTI_UPLOAD, COD_FISCALE, NOME, COGNOME, CODICE_VISUALIZZATO, FLAG_RAPPR_LEGALE, 
         COD_FISCALE_BENEFICIARIO,ID_FLUSSO)
        VALUES
        (SEQ_PBANDI_T_UTENTI_UPLOAD.NEXTVAL,vCodFiscale,vNome,vCognome,vCodiceVisualizzato,UPPER(vFlagRapprLegale),
         vCodFiscaleBeneficiario,pIdFlusso);
      END IF;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        EXIT;
    END;
  END LOOP;

  RETURN 0;
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Errore = '||SQLERRM||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
    nIdLogBatch := PCK_PBANDI_UTILITY_BATCH.inslogbatch(gnIdProcessoBatch,'E149','Errore = '||SQLERRM||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
    RETURN 1;
END CaricamentoCsv;


-- Caricamento utenti
FUNCTION CaricamentoUtenti(pIdFlusso  PBANDI_T_UTENTI_UPLOAD.ID_FLUSSO%TYPE) RETURN NUMBER IS

  nIdSoggPers             PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE;
  nIdSoggUt               PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE;
  nIdUtente               PBANDI_T_UTENTE.ID_UTENTE%TYPE;
  nIdPersonaFisica        PBANDI_T_PERSONA_FISICA.ID_PERSONA_FISICA%TYPE;
  nContSta                PLS_INTEGER := 0;
  nIdProgetto             PBANDI_T_PROGETTO.ID_PROGETTO%TYPE;
  nIdSoggBen              PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE := null;
  nProgSoggCorrelati      PBANDI_R_SOGGETTI_CORRELATI.PROGR_SOGGETTI_CORRELATI%TYPE;
  nProgSoggProg           PBANDI_R_SOGGETTO_PROGETTO.PROGR_SOGGETTO_PROGETTO%TYPE;
  vCodiceFiscaleSoggetto  PBANDI_T_SOGGETTO.CODICE_FISCALE_SOGGETTO%TYPE;
  vCodiceFiscaleSoggettoRL  PBANDI_T_SOGGETTO.CODICE_FISCALE_SOGGETTO%TYPE;
  nCont                   PLS_INTEGER;
BEGIN
  FOR recUtenti IN (SELECT ROWID,UU.*
                    FROM   PBANDI_T_UTENTI_UPLOAD UU
                    WHERE  UU.ID_FLUSSO       = pIdFlusso
                    AND    UU.DT_ACQUISIZIONE IS NULL) LOOP

    BEGIN
      SELECT s.ID_SOGGETTO
      INTO   nIdSoggPers
      FROM   PBANDI_T_SOGGETTO s, PBANDI_D_TIPO_SOGGETTO ts
      WHERE  s.CODICE_FISCALE_SOGGETTO   = UPPER(recUtenti.COD_FISCALE)
      AND    s.ID_TIPO_SOGGETTO          = ts.ID_TIPO_SOGGETTO
      AND    ts.DESC_BREVE_TIPO_SOGGETTO = 'PF';
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        INSERT INTO PBANDI_T_SOGGETTO
        (ID_SOGGETTO, CODICE_FISCALE_SOGGETTO, ID_UTENTE_INS, ID_TIPO_SOGGETTO,DT_INSERIMENTO)
        VALUES
        (SEQ_PBANDI_T_SOGGETTO.NEXTVAL,UPPER(recUtenti.COD_FISCALE),-5,1,SYSDATE)
        RETURNING ID_SOGGETTO INTO nIdSoggPers;
    END;

     BEGIN
      SELECT ID_SOGGETTO,ID_UTENTE
      INTO   nIdSoggUt  ,nIdUtente
      FROM   PBANDI_T_UTENTE
      WHERE  CODICE_UTENTE = UPPER(recUtenti.COD_FISCALE);

      IF nIdSoggUt != nIdSoggPers THEN
        nIdLogBatch := PCK_PBANDI_UTILITY_BATCH.inslogbatch(gnIdProcessoBatch,'W020','COD FISC PERSONA = '||UPPER(recUtenti.COD_FISCALE));

        UPDATE PBANDI_T_UTENTI_UPLOAD
        SET    ID_LOG_BATCH = nIdLogBatch
        WHERE  ROWID        = recUtenti.ROWID;
      END IF;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        INSERT INTO PBANDI_T_UTENTE
        (ID_UTENTE, CODICE_UTENTE, ID_TIPO_ACCESSO, ID_SOGGETTO,DT_INSERIMENTO )
        VALUES
        (SEQ_PBANDI_T_UTENTE.NEXTVAL,UPPER(recUtenti.COD_FISCALE),5,nIdSoggPers,SYSDATE)
        RETURNING ID_UTENTE INTO nIdUtente;
    END;

    BEGIN
      SELECT ID_PERSONA_FISICA
      INTO   nIdPersonaFisica
      FROM   PBANDI_T_PERSONA_FISICA
      WHERE  ID_SOGGETTO          = nIdSoggPers
      AND    UPPER(COGNOME)       = UPPER(recUtenti.COGNOME)
      AND    NVL(UPPER(NOME),'0') = NVL(UPPER(recUtenti.NOME),'0')
      AND    ROWNUM               = 1
      AND    DT_FINE_VALIDITA     IS NULL;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        INSERT INTO PBANDI_T_PERSONA_FISICA
        (ID_SOGGETTO, COGNOME, NOME,
         ID_PERSONA_FISICA, DT_INIZIO_VALIDITA,ID_UTENTE_INS )
        VALUES
        (nIdSoggPers,UPPER(recUtenti.COGNOME),UPPER(recUtenti.NOME),
         SEQ_PBANDI_T_PERSONA_FISICA.NEXTVAL,SYSDATE,-5)
        RETURNING ID_PERSONA_FISICA INTO nIdPersonaFisica;
    END;

    SELECT COUNT(*)
    INTO   nContSta
    FROM   PBANDI_R_SOGG_TIPO_ANAGRAFICA
    WHERE  ID_SOGGETTO        = nIdSoggPers
    AND    ID_TIPO_ANAGRAFICA = 16
    AND    DT_FINE_VALIDITA   IS NULL;

    IF nContSta = 0 THEN
	  BEGIN
		  INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA
		  (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA)
		  VALUES
		  (nIdSoggPers,16,-5,SYSDATE);
      EXCEPTION
         WHEN DUP_VAL_ON_INDEX THEN
           nIdLogBatch := PCK_PBANDI_UTILITY_BATCH.inslogbatch(gnIdProcessoBatch,'W090','COD FISC PERSONA = '||UPPER(recUtenti.COD_FISCALE));
	  END;
    END IF;


    BEGIN
      SELECT P.ID_PROGETTO,SP.ID_SOGGETTO
      INTO   nIdProgetto,nIdSoggBen
      FROM   PBANDI_T_PROGETTO p,PBANDI_R_SOGGETTO_PROGETTO SP
      WHERE  P.CODICE_VISUALIZZATO    = recUtenti.CODICE_VISUALIZZATO
      AND    P.ID_PROGETTO            = SP.ID_PROGETTO
      AND    SP.ID_TIPO_ANAGRAFICA    = 1
      AND    SP.ID_TIPO_BENEFICIARIO != 4
      AND    SP.DT_FINE_VALIDITA      IS NULL;
 
/* 
      SELECT CODICE_FISCALE_SOGGETTO
      INTO   vCodiceFiscaleSoggetto
      FROM   PBANDI_T_SOGGETTO
      WHERE  ID_SOGGETTO = nIdSoggBen;
*/

      SELECT DISTINCT a.CODICE_FISCALE_SOGGETTO, c.CODICE_FISCALE_SOGGETTO CODICE_FISCALE_SOGGETTO_RL
      INTO   vCodiceFiscaleSoggetto, vCodiceFiscaleSoggettoRL
      FROM   PBANDI_T_SOGGETTO a,
             PBANDI_R_SOGGETTI_CORRELATI b,
             PBANDI_T_SOGGETTO c
      WHERE  a.ID_SOGGETTO = nIdSoggBen
         AND b.ID_SOGGETTO_ENTE_GIURIDICO = a.ID_SOGGETTO
         AND c.ID_SOGGETTO = b.ID_SOGGETTO
         AND b.ID_TIPO_SOGGETTO_CORRELATO = 1
         AND ROWNUM = 1;
		 
      IF (vCodiceFiscaleSoggetto != recUtenti.COD_FISCALE_BENEFICIARIO) AND
	     (vCodiceFiscaleSoggettoRL  != recUtenti.COD_FISCALE_BENEFICIARIO) THEN
        --nIdLogBatch := PCK_PBANDI_UTILITY_BATCH.inslogbatch(gnIdProcessoBatch,'W088','C.F. = '||UPPER(recUtenti.COD_FISCALE));
        nIdLogBatch := PCK_PBANDI_UTILITY_BATCH.inslogbatch(gnIdProcessoBatch,'W088','C.F. = '||UPPER(vCodiceFiscaleSoggetto));

        UPDATE PBANDI_T_UTENTI_UPLOAD
        SET    ID_LOG_BATCH = nIdLogBatch
        WHERE  ROWID        = recUtenti.ROWID;
      ELSE  
        
        BEGIN
          SELECT PROGR_SOGGETTI_CORRELATI
          INTO   nProgSoggCorrelati
          FROM   PBANDI_R_SOGGETTI_CORRELATI
          WHERE  ID_SOGGETTO                = nIdSoggPers
          AND    ID_SOGGETTO_ENTE_GIURIDICO = nIdSoggBen
          AND    DT_FINE_VALIDITA           IS NULL
          AND    ID_TIPO_SOGGETTO_CORRELATO = DECODE(recUtenti.FLAG_RAPPR_LEGALE,'S',1,6)
          AND    ROWNUM                     = 1;
        EXCEPTION
          WHEN NO_DATA_FOUND THEN
            INSERT INTO PBANDI_R_SOGGETTI_CORRELATI
            (ID_TIPO_SOGGETTO_CORRELATO, ID_UTENTE_INS, ID_SOGGETTO,
             ID_SOGGETTO_ENTE_GIURIDICO, DT_INIZIO_VALIDITA,
             PROGR_SOGGETTI_CORRELATI)
            VALUES
            (DECODE(recUtenti.FLAG_RAPPR_LEGALE,'S',1,6),-5,nIdSoggPers,
             nIdSoggBen,SYSDATE,SEQ_PBANDI_R_SOGG_CORRELATI.NEXTVAL)
            RETURNING PROGR_SOGGETTI_CORRELATI INTO nProgSoggCorrelati;
        END;

        BEGIN
          SELECT PROGR_SOGGETTO_PROGETTO
          INTO   nProgSoggProg
          FROM   PBANDI_R_SOGGETTO_PROGETTO
          WHERE  ID_PROGETTO        = nIdProgetto
          AND    ID_SOGGETTO        = nIdSoggPers
          AND    ID_TIPO_ANAGRAFICA = 16
          AND    ROWNUM             = 1;
        EXCEPTION
          WHEN NO_DATA_FOUND THEN
            INSERT INTO PBANDI_R_SOGGETTO_PROGETTO
            (ID_SOGGETTO, ID_PROGETTO, PROGR_SOGGETTO_PROGETTO,
             DT_INIZIO_VALIDITA,ID_TIPO_ANAGRAFICA, ID_PERSONA_FISICA, id_utente_ins,DT_INSERIMENTO )
            VALUES
            (nIdSoggPers,nIdProgetto,SEQ_PBANDI_R_SOGGETTO_PROGETTO.NEXTVAL,
             SYSDATE,16,nIdPersonaFisica,-5,SYSDATE)
            RETURNING PROGR_SOGGETTO_PROGETTO INTO nProgSoggProg;
        END;

        SELECT COUNT(*)
        INTO   nCont
        FROM   PBANDI_R_SOGG_PROG_SOGG_CORREL
        WHERE  PROGR_SOGGETTO_PROGETTO  = nProgSoggProg
        AND    PROGR_SOGGETTI_CORRELATI = nProgSoggCorrelati;
 
        IF nCont = 0 THEN
          INSERT INTO PBANDI_R_SOGG_PROG_SOGG_CORREL
          (PROGR_SOGGETTO_PROGETTO, PROGR_SOGGETTI_CORRELATI, ID_UTENTE_INS,DT_INSERIMENTO )
          VALUES
          (nProgSoggProg,nProgSoggCorrelati,-5,SYSDATE);
        END IF;
      END IF;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        nIdLogBatch := PCK_PBANDI_UTILITY_BATCH.inslogbatch(gnIdProcessoBatch,'W081','COD FISC PERSONA = '||UPPER(recUtenti.COD_FISCALE));

        UPDATE PBANDI_T_UTENTI_UPLOAD
        SET    ID_LOG_BATCH = nIdLogBatch
        WHERE  ROWID        = recUtenti.ROWID;
    END;

  END LOOP;

  UPDATE PBANDI_T_UTENTI_UPLOAD
  SET    DT_ACQUISIZIONE = SYSDATE
  WHERE  ID_FLUSSO       = pIdFlusso
  AND    DT_ACQUISIZIONE IS NULL;

  RETURN 0;
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Errore = '||SQLERRM||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
    nIdLogBatch := PCK_PBANDI_UTILITY_BATCH.inslogbatch(gnIdProcessoBatch,'E150','Errore = '||SQLERRM||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
    RETURN 1;
END CaricamentoUtenti;

-- Creazione file csv degli esiti
FUNCTION FileEsiti(pNomeOggDir  VARCHAR2,
                   pNomeFile    VARCHAR2,
                   pIdFlusso    PBANDI_T_UTENTI_UPLOAD.ID_FLUSSO%TYPE) RETURN NUMBER IS

  nCont         PLS_INTEGER;
  fpFileOutput  UTL_FILE.FILE_TYPE;
  nFn           PLS_INTEGER;
BEGIN
  nFn := IndirizziMail(pNomeOggDir,
                       REPLACE(pNomeFile,'.csv','')||'_mail.txt',
                       pIdFlusso);

  IF nFn = 1 THEN
    RETURN 1;
  END IF;

  SELECT COUNT(*)
  INTO   nCont
  FROM   PBANDI_T_UTENTI_UPLOAD
  WHERE  ID_FLUSSO    = pIdFlusso
  AND    ID_LOG_BATCH IS NOT NULL;

  IF nCont != 0 THEN
    fpFileOutput := UTL_FILE.fopen(pNomeOggDir,REPLACE(pNomeFile,'.csv','')||'_esito.csv', 'W',32767);

    FOR recEsiti IN (SELECT COD_FISCALE, NOME, COGNOME, CODICE_VISUALIZZATO, FLAG_RAPPR_LEGALE, DT_ACQUISIZIONE,
                            EB.DESCRIZIONE||LB.MESSAGGIO_ERRORE DESCRIZIONE,COD_FISCALE_BENEFICIARIO
                     FROM   PBANDI_T_UTENTI_UPLOAD UU,PBANDI_D_ERRORE_BATCH EB,PBANDI_L_LOG_BATCH LB
                     WHERE  ID_FLUSSO        = pIdFlusso
                     AND    UU.ID_LOG_BATCH  = LB.ID_LOG_BATCH
                     AND    LB.CODICE_ERRORE = EB.CODICE_ERRORE) LOOP

      UTL_FILE.PUT(fpFileOutput,recEsiti.COD_FISCALE);
      UTL_FILE.PUT(fpFileOutput,';'||recEsiti.NOME);
      UTL_FILE.PUT(fpFileOutput,';'||recEsiti.COGNOME);
      UTL_FILE.PUT(fpFileOutput,';'||recEsiti.CODICE_VISUALIZZATO);
      UTL_FILE.PUT(fpFileOutput,';'||recEsiti.COD_FISCALE_BENEFICIARIO);
      UTL_FILE.PUT(fpFileOutput,';'||recEsiti.FLAG_RAPPR_LEGALE);
      UTL_FILE.PUT(fpFileOutput,';'||TO_CHAR(recEsiti.DT_ACQUISIZIONE,'DD/MM/YYYY'));
      UTL_FILE.PUT(fpFileOutput,';'||recEsiti.DESCRIZIONE);
      UTL_FILE.NEW_LINE(fpFileOutput);
    END LOOP;
    UTL_FILE.fflush(fpFileOutput);

    IF UTL_FILE.IS_open(fpFileOutput) THEN
      UTL_FILE.fclose(fpFileOutput);
    END IF;
  END IF;

  RETURN 0;
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Errore = '||SQLERRM||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
    nIdLogBatch := PCK_PBANDI_UTILITY_BATCH.inslogbatch(gnIdProcessoBatch,'E151','Errore = '||SQLERRM||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
    RETURN 1;
END FileEsiti;

-- Viene creato un file con all'interno gli indirizzi mail a cui spedire il file degli esiti
FUNCTION IndirizziMail(pNomeDir   VARCHAR2,
                       pNomeFile  VARCHAR2,
                       pIdFlusso  PBANDI_T_UTENTI_UPLOAD.ID_FLUSSO%TYPE) RETURN NUMBER IS

  CURSOR curMail IS SELECT DISTINCT EMAIL
                    FROM   PBANDI_T_UTENTE U,PBANDI_R_ENTE_COMPETENZA_SOGG ECS,PBANDI_C_ENTE_RECAPITI EC,
                           PBANDI_D_NOME_BATCH NB,PBANDI_T_FLUSSI_UPLOAD FU
                    WHERE  FU.ID_FLUSSO           = pIdFlusso
                    AND    U.ID_UTENTE            = FU.ID_UTENTE_UPLOAD
                    AND    U.ID_SOGGETTO          = ECS.ID_SOGGETTO
                    AND    ECS.DT_FINE_VALIDITA   IS NULL
                    AND    ECS.ID_ENTE_COMPETENZA = EC.ID_ENTE_COMPETENZA
                    AND    EC.ID_NOME_BATCH       = NB.ID_NOME_BATCH
                    AND    NB.NOME_BATCH          = gvNomeBatch;

  fpFileOutput  UTL_FILE.FILE_TYPE;
  vIndMail      VARCHAR2(4000);
BEGIN
  fpFileOutput := UTL_FILE.fopen(pNomeDir,pNomeFile, 'W',32767);

  FOR recMail IN curMail LOOP

     IF curMail%ROWCOUNT = 1 THEN
      vIndMail := recMail.EMAIL;
    ELSE
      vIndMail := vIndMail||','||recMail.EMAIL;
    END IF;
  END LOOP;

  UTL_FILE.PUT_LINE(fpFileOutput,vIndMail);

  UTL_FILE.fflush(fpFileOutput);

  IF UTL_FILE.IS_open(fpFileOutput) THEN
    UTL_FILE.fclose(fpFileOutput);
  END IF;

  RETURN 0;
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Errore = '||SQLERRM||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
    nIdLogBatch := PCK_PBANDI_UTILITY_BATCH.inslogbatch(gnIdProcessoBatch,'E152','Errore = '||SQLERRM||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
    RETURN 1;
END IndirizziMail;

END PCK_BANDI_CARICAMENTO_UTENTI;
/
