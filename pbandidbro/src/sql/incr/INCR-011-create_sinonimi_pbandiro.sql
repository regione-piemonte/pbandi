/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

DECLARE
  CURSOR curSin IS SELECT DISTINCT OWNER,TABLE_NAME FROM USER_TAB_PRIVS
				   WHERE OWNER IN ('PBANDI')
				   AND TABLE_NAME != 'FN_LINEA_INTERV_RADICE';

  vComando  VARCHAR2(1000);
BEGIN
  FOR recSin IN curSin LOOP
    BEGIN
	  vComando := 'CREATE OR REPLACE SYNONYM '||recSin.TABLE_NAME||' FOR '||recSin.OWNER||'.'||recSin.TABLE_NAME;
	  EXECUTE IMMEDIATE vComando;
	EXCEPTION
      WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('ERRORE = '||recSin.TABLE_NAME||' '||SQLERRM);
	END; 	
  END LOOP;
END;
/

