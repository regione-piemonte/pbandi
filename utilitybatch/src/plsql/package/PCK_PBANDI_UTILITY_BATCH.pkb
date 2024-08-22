/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE OR REPLACE PACKAGE BODY PBANDI.PCK_PBANDI_UTILITY_BATCH AS

/*******************************************************************************
   NAME:       PCK_PBANDI_UTILITY_BATCH
   PURPOSE:    Package di funzioni di utility batch

   REVISIONS:
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.0.0      16/02/2009  Rocco Cambareri  Created this package.


  LAST MODIFY : 16/02/2009
  AUTHOR      : Rocco Cambareri
  DESCRIPTION : Creazione Package

  LAST MODIFY :
  AUTHOR      :
  DESCRIPTION :
*******************************************************************************/

/************************************************************************
  ReturnNumber : Ritorna il valore number della stringa passata

  Parametri input: pStringa - stringa da convertire
*************************************************************************/
FUNCTION ReturnNumber(pStringa  VARCHAR2) RETURN NUMBER IS
  nRet  NUMBER;
BEGIN
  nRet := TO_NUMBER(REPLACE(pStringa,'.',''));

  return nRet;
EXCEPTION
  WHEN OTHERS THEN
    RETURN -1;
END ReturnNumber;

/************************************************************************
  ReturnDate : Ritorna il valore data della stringa passata nel formato dd/mm/yyyy

  Parametri input: pStringa - stringa da convertire
*************************************************************************/
FUNCTION ReturnDate(pStringa  VARCHAR2) RETURN DATE IS
  dDate  DATE;
begin
  ddate := TO_DATE(pstringa,'dd/mm/yyyy');
  return ddate;
exception
  WHEN others then
    return null;
end ReturnDate;


/************************************************************************
  ReturnDateTime : Ritorna il valore datatime della stringa passata nel
                   formato yyyy-mm-ddhh24:mi:ss

  Parametri input: pStringa - stringa da convertire
************************************************************************/
FUNCTION ReturnDateTime(pStringa  VARCHAR2) RETURN DATE is
    dDate  DATE;
begin
  ddate := to_date(replace(pStringa,'T',''),'yyyy-mm-ddhh24:mi:ss');
  return trunc(ddate);
exception
  WHEN others then
    return null;
end ReturnDateTime;

/************************************************************************
  InsProcBatch : Inserisce i dati del processo batch in esecuzione

  Parametri input:  pNomeBatch - nome del batch
  Parametri output: pIdProc    - id processo batch
  *************************************************************************/

FUNCTION InsProcBatch (pNomeBatch IN PBANDI_D_NOME_BATCH.nome_batch%TYPE)
                        RETURN PBANDI_L_PROCESSO_BATCH.id_processo_batch%TYPE is

  PRAGMA AUTONOMOUS_TRANSACTION;
  nIdProc  PBANDI_L_PROCESSO_BATCH.id_processo_batch%TYPE;
BEGIN

  INSERT INTO PBANDI_L_PROCESSO_BATCH
  (ID_PROCESSO_BATCH, ID_NOME_BATCH, DT_INIZIO_ELABORAZIONE)
  VALUES
  (SEQ_PBANDI_L_PROCESSO_BATCH.NEXTVAL,
   (SELECT ID_NOME_BATCH FROM PBANDI_d_nome_batch WHERE nome_batch = pnomebatch),
   SYSDATE ) RETURNING ID_PROCESSO_BATCH INTO nIdProc;

  COMMIT;

  RETURN nIdProc;
END InsProcBatch;

  /************************************************************************
  UpdFineProcBatch : Aggiorna la data fine elaborazione
                     del processo batch in esecuzione

  Parametri input:  pIdProc   - id processo batch
                    pEsito    - esito del processo batch OK o KO
  *************************************************************************/

PROCEDURE UpdFineProcBatch (pIdProc IN PBANDI_L_PROCESSO_BATCH.id_processo_batch%TYPE,
                            pEsito  IN PBANDI_L_PROCESSO_BATCH.flag_esito%TYPE) IS

  PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN
  UPDATE PBANDI_L_PROCESSO_BATCH
  SET    DT_FINE_ELABORAZIONE = SYSDATE,
         flag_esito = pEsito
  WHERE  ID_PROCESSO_BATCH = pIdProc;
  COMMIT;
END UpdFineProcBatch;

  /************************************************************************
  InsLogBatch : Popola la Tabella di Log

  Parametri input:  pId         - id identificativo batch
                    pCodErr     - codice errore
                    pMessErr    - messaggio errore
  ***************************************************************************/

PROCEDURE InsLogBatch (pIdProc  IN PBANDI_L_LOG_BATCH.id_processo_batch%TYPE,
                       pCodErr  IN PBANDI_L_LOG_BATCH.codice_errore%TYPE,
                       pMessErr IN PBANDI_L_LOG_BATCH.messaggio_errore%TYPE) is

  PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN

  INSERT INTO PBANDI_L_LOG_BATCH
  (ID_LOG_BATCH, ID_PROCESSO_BATCH, DT_INSERIMENTO, CODICE_ERRORE, MESSAGGIO_ERRORE)
  VALUES
  (SEQ_PBANDI_L_LOG_BATCH.NEXTVAL,pIdProc ,SYSDATE , pCodErr, pMessErr );
  COMMIT;

END InsLogBatch;

/************************************************************************
ReturnNumberGeba : Ritorna il valore number della stringa passata

Parametri input: pStringa - stringa da convertire
*************************************************************************/
FUNCTION ReturnNumberGeba(pStringa  VARCHAR2) RETURN NUMBER IS
  nRet  NUMBER;
BEGIN
  nRet := TO_NUMBER(REPLACE(pStringa,'.',','));

  return nRet;
EXCEPTION
  WHEN OTHERS THEN
    RETURN -1;
END ReturnNumberGeba;

/************************************************************************
 ReturnNomeCognomeUtente : Ritorna il nome e cognome dell'utente in base
                           all'id_utente passato in input

 Parametri input: pIdUtente - id dell'utente
 *************************************************************************/
FUNCTION ReturnNomeCognomeUtente(pIdUtente  PBANDI_T_UTENTE.ID_UTENTE%TYPE) RETURN VARCHAR2 IS

  vNomeCognomeUtente  VARCHAR2(2000);
BEGIN
  BEGIN
    SELECT DISTINCT FIRST_VALUE(PF.NOME||' '||PF.COGNOME) OVER (ORDER BY PF.DT_INIZIO_VALIDITA DESC)
    INTO   vNomeCognomeUtente
    FROM   PBANDI_T_UTENTE U,PBANDI_T_SOGGETTO S,PBANDI_T_PERSONA_FISICA PF
    WHERE  U.ID_UTENTE         = pIdUtente
    AND    U.ID_SOGGETTO       = S.ID_SOGGETTO
    AND    S.ID_SOGGETTO       = PF.ID_SOGGETTO
    AND    PF.DT_FINE_VALIDITA IS NULL
    AND    ROWNUM              = 1;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      SELECT CODICE_UTENTE
      INTO   vNomeCognomeUtente
      FROM   PBANDI_T_UTENTE U
      WHERE  U.ID_UTENTE = pIdUtente;
  END;

  RETURN vNomeCognomeUtente;
END ReturnNomeCognomeUtente;

/************************************************************************
  InsLogBatch : Popola la Tabella di Log e ritorna l'id_log_batch

  Parametri input:  pId         - id identificativo batch
                    pCodErr     - codice errore
                    pMessErr    - messaggio errore
 ***************************************************************************/

FUNCTION InsLogBatch (pIdProc  IN PBANDI_L_LOG_BATCH.id_processo_batch%TYPE,
                      pCodErr  IN PBANDI_L_LOG_BATCH.codice_errore%TYPE,
                      pMessErr IN PBANDI_L_LOG_BATCH.messaggio_errore%TYPE) RETURN NUMBER IS

  PRAGMA AUTONOMOUS_TRANSACTION;
  nIdLogBatch  PBANDI_L_LOG_BATCH.ID_LOG_BATCH%TYPE;
BEGIN

  INSERT INTO PBANDI_L_LOG_BATCH
  (ID_LOG_BATCH, ID_PROCESSO_BATCH, DT_INSERIMENTO, CODICE_ERRORE, MESSAGGIO_ERRORE)
  VALUES
  (SEQ_PBANDI_L_LOG_BATCH.NEXTVAL,pIdProc ,SYSDATE , pCodErr, pMessErr )
  RETURNING ID_LOG_BATCH INTO nIdLogBatch;
  COMMIT;

  RETURN nIdLogBatch;
END InsLogBatch;

/*****************************************************************************/
PROCEDURE plog (p_query in varchar2) as
begin
   execute immediate p_query;
end plog;

/************************************************************************
  Gestione dei file:
  -- FileOpen - FileRead - FileClose - FileWrite
***************************************************************************/
----------------------
------FILEOPEN-----
----------------------
 FUNCTION FileOpen
   ( pFilePath      VARCHAR2, pFileName VARCHAR2,  pOpenMode  VARCHAR2 := 'W', pLenLine NUMBER := 1024 )   RETURN UTL_FILE.FILE_TYPE
   IS
  BEGIN

     RETURN UTL_FILE.FOPEN( pFilePath, pFileName, pOpenMode, pLenLine );

  EXCEPTION
       WHEN UTL_FILE.INVALID_PATH THEN RAISE_APPLICATION_ERROR(-20000, 'FILEOPEN: <<INVALID_PATH>>' );
       WHEN UTL_FILE.INVALID_MODE THEN RAISE_APPLICATION_ERROR(-20000, 'FILEOPEN: <<INVALID_MODE>>' );
       WHEN UTL_FILE.INVALID_OPERATION THEN RAISE_APPLICATION_ERROR(-20000, 'FILEOPEN: <<INVALID_OPERATION>>' );
       WHEN UTL_FILE.INVALID_MAXLINESIZE THEN RAISE_APPLICATION_ERROR(-20000, 'FILEOPEN: <<INVALID_MAXLINESIZE>>' );
       WHEN OTHERS THEN  RAISE_APPLICATION_ERROR(-20000, 'FILEOPEN: '||SQLERRM );

  END FileOpen;

----------------------
------FILEREAD-----
----------------------
  PROCEDURE FileRead    (PFILE   IN UTL_FILE.FILE_TYPE, pStr    OUT VARCHAR2)
  IS
  BEGIN
         UTL_FILE.GET_LINE( PFILE, pStr );
  EXCEPTION
       WHEN UTL_FILE.INVALID_FILEHANDLE THEN RAISE_APPLICATION_ERROR(-20000, 'FILEREAD: <<INVALID_FILEHANDLE>>' );
       WHEN UTL_FILE.INVALID_OPERATION THEN RAISE_APPLICATION_ERROR(-20000, 'FILEREAD: <<INVALID_OPERATION>>' );
       WHEN UTL_FILE.READ_ERROR THEN RAISE_APPLICATION_ERROR(-20000, 'FILEREAD: <<READ_ERROR>>' );
       WHEN OTHERS THEN  RAISE_APPLICATION_ERROR(-20000, 'FILEREAD: '||SQLERRM );

  END FileRead;
 ---------------
 -- FILECLOSE --
 ---------------
  PROCEDURE FileClose  ( PFILE   IN OUT UTL_FILE.FILE_TYPE )
  IS
  BEGIN
     UTL_FILE.FCLOSE( PFILE );
  EXCEPTION
       WHEN UTL_FILE.INVALID_FILEHANDLE THEN RAISE_APPLICATION_ERROR(-20000,'FILECLOSE: <<INVALID_FILEHANDLE>>' );
       WHEN UTL_FILE.WRITE_ERROR THEN  RAISE_APPLICATION_ERROR(-20000, 'FILECLOSE: <<WRITE_ERROR>>' );
       WHEN OTHERS THEN  RAISE_APPLICATION_ERROR(-20000,'FILECLOSE: '||SQLERRM );
  END FileClose;

 ---------------
 -- FILEWRITE --
 ---------------
  PROCEDURE FileWrite (PFILE   IN OUT UTL_FILE.FILE_TYPE, pStr    IN VARCHAR2 )
  IS
  BEGIN
     UTL_FILE.PUT_LINE( PFILE, pStr );
  EXCEPTION
       WHEN UTL_FILE.INVALID_FILEHANDLE THEN RAISE_APPLICATION_ERROR(-20000, 'FILEWRITE: <<INVALID_FILEHANDLE>>' );
       WHEN UTL_FILE.INVALID_OPERATION THEN RAISE_APPLICATION_ERROR(-20000, 'FILEWRITE: <<INVALID_OPERATION>>' );
       WHEN UTL_FILE.WRITE_ERROR THEN RAISE_APPLICATION_ERROR(-20000, 'FILEWRITE: <<WRITE_ERROR>>' );
        WHEN OTHERS THEN  RAISE_APPLICATION_ERROR(-20000,'FILEWRITE: '||SQLERRM );
  END FileWrite;

  -- ***************************************************************************
  -- NAME:      TruncTable
  -- PURPOSE:   Fa truncate di tabella o partizione
  --
  -- REVISIONS:
  -- Ver        Date        Author           Description
  -- ---------  ----------  ---------------  -----------------------------------
  -- 1.0        28/06/2016  S. Gabriele          Prima implementazione.
  -- ***************************************************************************
  PROCEDURE TruncTable(pTable VARCHAR2, pPartition VARCHAR2 DEFAULT NULL) IS
     v_sql VARCHAR2(4000);
  BEGIN
     IF pPartition IS NULL THEN
	    v_sql := 'truncate table '||pTable||' drop storage';
	 ELSE
	    v_sql := 'alter table '||pTable||' truncate partition '||pPartition||' drop storage  update indexes';
	 END IF;

	 EXECUTE IMMEDIATE v_sql;
  EXCEPTION
     WHEN OTHERS THEN
	    RAISE_APPLICATION_ERROR(-20000,'Errore in utility PCK_INFOTPL_UTIL.TruncTable '||sqlerrm);
  END;  
FUNCTION fn_linea_interv_radice (p_id_progetto IN NUMBER) return NUMBER as
       cursor c1 (c_id_progetto NUMBER) is
          select c.id_linea_di_intervento
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;
          
        cursor c2 (c_id_linea_di_intervento NUMBER) is
           select id_linea_di_intervento,desc_breve_linea
           from PBANDI_D_LINEA_DI_INTERVENTO
           where id_linea_di_intervento_padre IS NULL
           connect by prior id_linea_di_intervento_padre = id_linea_di_intervento
           start with id_linea_di_intervento = c_id_linea_di_intervento;
           
        cursor c3 (c_id_linea_di_intervento NUMBER) is 
           select id_linea_di_intervento_attuale,
                  desc_breve_linea
             from PBANDI_R_LINEA_INTERV_MAPPING a,
                  PBANDI_D_LINEA_DI_INTERVENTO b
            where id_linea_di_intervento_migrata = c_id_linea_di_intervento
              and b.id_linea_di_intervento = a.id_linea_di_intervento_attuale;
              
            r_attuale  c3%ROWTYPE;
           
         v_desc_breve_linea_radice VARCHAR2(20);
         v_id_linea_intervento INTEGER;
         v_id_linea_intervento_radice INTEGER;
     
    BEGIN
       OPEN c1 (p_id_progetto);
       FETCH c1 into v_id_linea_intervento;
       IF c1%NOTFOUND THEN
         v_id_linea_intervento := NULL;
       END IF;
       CLOSE c1;
       
       IF v_id_linea_intervento IS NOT NULL THEN
          OPEN c2(v_id_linea_intervento);
          FETCH c2 INTO v_id_linea_intervento_radice,v_desc_breve_linea_radice;
          IF c2%NOTFOUND THEN
             v_id_linea_intervento_radice := NULL;
          ELSE
            -- Controllo se è una linea di intervento migrata
             OPEN c3(v_id_linea_intervento_radice);
             FETCH c3 into r_attuale;
             IF c3%FOUND THEN
                v_id_linea_intervento_radice := r_attuale.id_linea_di_intervento_attuale; -- se migrata allora uso l'attuale
             END IF;
             CLOSE c3;
          END IF;
          CLOSE c2;
       END IF;

       RETURN v_id_linea_intervento_radice;
    END fn_linea_interv_radice;

END PCK_PBANDI_UTILITY_BATCH;
/

