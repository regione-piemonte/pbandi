/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE OR REPLACE PACKAGE PBANDI.PCK_PBANDI_UTILITY_BATCH AS

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
  FUNCTION ReturnNumber(pStringa  VARCHAR2) RETURN NUMBER;

/************************************************************************
  ReturnDate : Ritorna il valore data della stringa passata nel formato dd/mm/yyyy

  Parametri input: pStringa - stringa da convertire
*************************************************************************/
  FUNCTION ReturnDate(pStringa  VARCHAR2) RETURN DATE;


  /************************************************************************
  ReturnDateTime : Ritorna il valore datatime della stringa passata nel
                   formato yyyy-mm-ddTZHhh24:mi:ss

  Parametri input: pStringa - stringa da convertire
*************************************************************************/
  FUNCTION ReturnDateTime(pStringa  VARCHAR2) RETURN DATE;

  /************************************************************************
  InsProcBatch : Inserisce i dati del processo batch in esecuzione

  Parametri input:  pNomeBatch - nome del batch
  Parametri output: pIdProc    - id processo batch
  *************************************************************************/

  FUNCTION InsProcBatch (pNomeBatch IN PBANDI_D_NOME_BATCH.nome_batch%TYPE)
                        RETURN PBANDI_L_PROCESSO_BATCH.id_processo_batch%TYPE ;

  /************************************************************************
  UpdFineProcBatch : Aggiorna la data fine elaborazione
                     del processo batch in esecuzione

  Parametri input:  pIdProc   - id processo batch
                    pEsito    - esito del processo batch OK o KO
  *************************************************************************/

  PROCEDURE UpdFineProcBatch (pIdProc IN PBANDI_L_PROCESSO_BATCH.id_processo_batch%TYPE,
                              pEsito  IN PBANDI_L_PROCESSO_BATCH.flag_esito%TYPE);

  /************************************************************************
  InsLogBatch : Popola la Tabella di Log

  Parametri input:  pId         - id identificativo batch
                    pCodErr     - codice errore
                    pMessErr    - messaggio errore
  ***************************************************************************/

  PROCEDURE InsLogBatch (pIdProc  IN PBANDI_L_LOG_BATCH.id_processo_batch%TYPE,
                         pCodErr  IN PBANDI_L_LOG_BATCH.codice_errore%TYPE,
                         pMessErr IN PBANDI_L_LOG_BATCH.messaggio_errore%TYPE);

  /************************************************************************
  ReturnNumberGeba : Ritorna il valore number della stringa passata

  Parametri input: pStringa - stringa da convertire
*************************************************************************/
  FUNCTION ReturnNumberGeba(pStringa  VARCHAR2) RETURN NUMBER;

  /************************************************************************
  ReturnNomeCognomeUtente : Ritorna il nome e cognome dell'utente in base
                            all'id_utente passato in input

  Parametri input: pIdUtente - id dell'utente
  *************************************************************************/
  FUNCTION ReturnNomeCognomeUtente(pIdUtente  PBANDI_T_UTENTE.ID_UTENTE%TYPE) RETURN VARCHAR2;

  /************************************************************************
  InsLogBatch : Popola la Tabella di Log e ritorna l'id_log_batch

  Parametri input:  pId         - id identificativo batch
                    pCodErr     - codice errore
                    pMessErr    - messaggio errore
  ***************************************************************************/

  FUNCTION InsLogBatch (pIdProc  IN PBANDI_L_LOG_BATCH.id_processo_batch%TYPE,
                        pCodErr  IN PBANDI_L_LOG_BATCH.codice_errore%TYPE,
                        pMessErr IN PBANDI_L_LOG_BATCH.messaggio_errore%TYPE) RETURN NUMBER;


  PROCEDURE plog (p_query in varchar2);
/************************************************************************
  Gestione dei file:
  -- FileOpen
  -- FileRead
  -- FileClose
  -- FileWrite
***************************************************************************/
FUNCTION   FileOpen ( pFilePath  VARCHAR2, pFileName  VARCHAR2, pOpenMode VARCHAR2 := 'W', pLenLine NUMBER := 1024 )RETURN UTL_FILE.FILE_TYPE;
PROCEDURE FileRead( pFile   IN UTL_FILE.FILE_TYPE, pStr OUT VARCHAR2);
PROCEDURE FileClose( pFile   IN OUT UTL_FILE.FILE_TYPE);
PROCEDURE FileWrite ( pFile   IN OUT UTL_FILE.FILE_TYPE, pStr  IN VARCHAR2);
--
-- Procedure per fare truncate di tabella o partizione
PROCEDURE TruncTable(pTable VARCHAR2, pPartition VARCHAR2 DEFAULT NULL);
 
 /************************************************************************
     fn_linea_interv_radice : Ritorna l'id normativa della linea intervento radice
     Parametri input: p_id_progetto
    *************************************************************************/
   FUNCTION fn_linea_interv_radice (p_id_progetto IN NUMBER)
      RETURN NUMBER;

END PCK_PBANDI_UTILITY_BATCH;
/

