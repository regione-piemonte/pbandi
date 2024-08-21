/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

DECLARE
  CURSOR curCons IS SELECT TABLE_NAME, CONSTRAINT_NAME
  		    FROM   USER_CONSTRAINTS
		    WHERE  CONSTRAINT_TYPE = 'R'; 

  vComando  VARCHAR2(4000);
BEGIN
  FOR recCons IN curCons LOOP
    BEGIN
	  vComando := 'ALTER TABLE '||recCons.TABLE_NAME||' DISABLE CONSTRAINT '||recCons.CONSTRAINT_NAME;
      EXECUTE IMMEDIATE (vComando);
	EXCEPTION
      WHEN OTHERS THEN
        NULL;
    END;		
  END LOOP;
END;
/

