/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE OR REPLACE package PBANDI."PCK_PBANDI_RESTITUZIONE_CUP" as

  -- Author  : Benedetto
  -- Created : 27/06/2012 8.39.03
  -- Purpose : RICHIESTA  CUP da BDU 
  
  

  gvNomeBatch        PBANDI_D_NOME_BATCH.NOME_BATCH%TYPE := 'PBAN-PBANDI_RESTITUZIONE_CUP';
  gnIdProcessoBatch  PBANDI_L_PROCESSO_BATCH.id_processo_batch%TYPE := PCK_PBANDI_UTILITY_BATCH.insprocbatch(gvNomeBatch);
  
  function AGGIORNAMENTO_CUP(pNomeDir   VARCHAR2,
                           nome_assegnati  VARCHAR2,
                           nome_scartati VARCHAR2,
                           nome_non_trovati VARCHAR2) return NUMBER;
  
  
  function PROGETTI_CUP(pNomeDir   VARCHAR2,
                           pNomeFile  VARCHAR2) return number ;

FUNCTION sistema_mittente(pIdProgetto          PBANDI_T_PROGETTO.ID_PROGETTO%TYPE)return varchar2 ;

end PCK_PBANDI_RESTITUZIONE_CUP;
/

