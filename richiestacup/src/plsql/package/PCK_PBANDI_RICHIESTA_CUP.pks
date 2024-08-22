/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE OR REPLACE PACKAGE PBANDI_CUP."PCK_PBANDI_RICHIESTA_CUP" AS

/*******************************************************************************
   NAME:       PCK_PBANDI_RICHIESTA_CUP
   PURPOSE:    Procedura richiesta CUP verso BDU

   REVISIONS:
   Ver        Date        Author                   Description
   ---------  ----------  ---------------          ------------------------------------
   1.0.0      26/08/2009  Benedetto Provvisionato  Created this package.


  LAST MODIFY :
  AUTHOR      : Benedetto Provvisionato
  DESCRIPTION : Creazione Package

  LAST MODIFY :
  AUTHOR      :
  DESCRIPTION :
*******************************************************************************/

  gvNomeBatch        PBANDI_D_NOME_BATCH.NOME_BATCH%TYPE := 'PBAN-PBANDI_RICHIESTA_CUP';
  gnIdProcessoBatch  PBANDI_L_PROCESSO_BATCH.id_processo_batch%TYPE := PCK_PBANDI_UTILITY_BATCH.insprocbatch(gvNomeBatch);
  
  /************************************************************************
  Main : funzione di lancio del processo
  *************************************************************************/
  FUNCTION Main RETURN NUMBER;
  
  /************************************************************************
  TrasformazCodAteco : trasformazione codice ateco
  *************************************************************************/                        
  FUNCTION TrasformazCodAteco(vCodAteco  PBANDI_D_ATTIVITA_ATECO.COD_ATECO%TYPE) RETURN VARCHAR2;

  FUNCTION datiprogetto  return number;
  
  FUNCTION ClassifCup (pIdProgetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE) return number;
  
  FUNCTION pareggio_progetti return number;
  
  FUNCTION controllo_tabelle return number; 
  
   
END PCK_PBANDI_RICHIESTA_CUP;
/

