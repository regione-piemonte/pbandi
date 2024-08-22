/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE OR REPLACE PACKAGE PCK_PBANDI_RETTIFICHE AS
/*******************************************************************************
   NAME:       PCK_PBANDI_RETTIFICHE
   PURPOSE:    Package rettifiche forfettarie

   REVISIONS:
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.0.0       14/10/2019  MC             Created this package.
*******************************************************************************/
/************************************************************************
      fnc_MainRettifiche : funzione richiamata dal batch unix
      Parametri input:                 nessuno
    *************************************************************************/
   FUNCTION fnc_MainRettifiche RETURN NUMBER;
/************************************************************************/
  

END PCK_PBANDI_RETTIFICHE;
/