/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE OR REPLACE PACKAGE PCK_BANDI_CARICAMENTO_UTENTI AS

  -- Funzione main
  FUNCTION Main(pNomeOggDirIn   VARCHAR2,
                pNomeOggDirOut  VARCHAR2,
                pNomeFile       VARCHAR2) RETURN NUMBER;

  -- Riceve in input il file csv da caricare sulla tabella UTENTI_UPLOAD
  FUNCTION CaricamentoCsv(pNomeOggDir  VARCHAR2,
                          pNomeFile    VARCHAR2,
                          pIdFlusso    PBANDI_T_UTENTI_UPLOAD.ID_FLUSSO%TYPE) RETURN NUMBER;

  -- Caricamento utenti
  FUNCTION CaricamentoUtenti(pIdFlusso  PBANDI_T_UTENTI_UPLOAD.ID_FLUSSO%TYPE) RETURN NUMBER;

  -- Creazione file csv degli esiti
  FUNCTION FileEsiti(pNomeOggDir  VARCHAR2,
                     pNomeFile    VARCHAR2,
                     pIdFlusso    PBANDI_T_UTENTI_UPLOAD.ID_FLUSSO%TYPE) RETURN NUMBER;

  -- Viene creato un file con all'interno gli indirizzi mail a cui spedire il file degli esiti
  FUNCTION IndirizziMail(pNomeDir   VARCHAR2,
                         pNomeFile  VARCHAR2,
                         pIdFlusso  PBANDI_T_UTENTI_UPLOAD.ID_FLUSSO%TYPE) RETURN NUMBER;

END PCK_BANDI_CARICAMENTO_UTENTI;
/
