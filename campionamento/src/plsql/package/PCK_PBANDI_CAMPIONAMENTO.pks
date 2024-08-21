/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE OR REPLACE PACKAGE PCK_PBANDI_CAMPIONAMENTO AS
/*******************************************************************************
   NAME:       PCK_PBANDI_CAMPIONAMENTO
   PURPOSE:    Package campionamento certificazione

   REVISIONS:
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.1.0    25/05/2020      MC             Created this package.
*******************************************************************************/
/************************************************************************
      fnc_MainCampionamento : funzione richiamata dall'online
     Parametri input:                 nessuno
    *************************************************************************/
   FUNCTION fnc_MainCampionamento(pDescBreveLinea PBANDI_D_LINEA_DI_INTERVENTO.DESC_BREVE_LINEA%TYPE  DEFAULT NULL, pID_UTENTE_INS NUMBER) RETURN NUMBER;
   v_Start                                  NUMBER:=0; -- mc sviluppo 2021
   
   v_valore_v pbandi_r_dett_campionamento.valore_v%type;
   

END PCK_PBANDI_CAMPIONAMENTO;
/

