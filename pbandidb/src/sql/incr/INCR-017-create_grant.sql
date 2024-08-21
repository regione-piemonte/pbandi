/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

declare
  
  Procedure CreateGrantToUser is
  TYPE tpUser IS RECORD (nomeUser  VARCHAR2(30),
  	   		   		     Comando   VARCHAR2(300));
						   
  TYPE typTbUser IS TABLE OF tpUser INDEX BY BINARY_INTEGER;						   
  
  tUser    typTbUser;
  
  begin
    tUser(1).nomeUser := 'PBANDI_RW';
    tUser(1).comando  := 'grant select,insert, update, delete on ';
	
	tUser(2).nomeUser := 'PBANDI_MONIT';
	tUser(2).comando  := 'grant select,insert, update, delete on ';

	tUser(3).nomeUser := 'PBANDI_CUP';
	tUser(3).comando  := 'grant select, insert, update, delete on ';
	
	FOR i IN tUser.FIRST..tUser.LAST LOOP
	--grant sulle tabelle
    for c in ( 
       select tUser(i).comando||tb.table_name||' to '||tUser(i).nomeUser cmd from user_tables tb 
    ) loop
      execute immediate c.cmd;
    end loop;

    -- Grant sulle sequence
    for c in ( 
       select 'grant select on '||se.sequence_name||' to '||tUser(i).nomeUser cmd from user_sequences se
    ) loop 
      execute immediate c.cmd;
    end loop;

	    -- Grant sulle viste
    for c in ( 
       select 'grant select on '||vi.view_name||' to '||tUser(i).nomeUser cmd from user_views vi
    ) loop 
      execute immediate c.cmd;
    end loop;

    -- Grant su Procedure/Funzioni/Package
    for c in ( 
       select distinct 'grant execute on '||pr.object_name||' to '||tUser(i).nomeUser cmd from user_procedures pr 
    ) loop
      execute immediate c.cmd;
    end loop;
    
    end loop;
  end;                          
begin
  CreateGrantToUser;
end;
/

GRANT EXECUTE ON T_NUMBER_ARRAY TO PBANDI_RW;


