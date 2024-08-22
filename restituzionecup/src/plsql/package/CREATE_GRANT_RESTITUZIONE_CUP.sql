/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

declare
   
  Procedure CreateGrantToUser is
  
  vUser  VARCHAR2(30) := 'PBANDI_RW';
  
  begin
	-- Grant su Procedure/Funzioni/Package
    for c in ( 
       select distinct 'grant execute on '||pr.object_name||' to '||vUser cmd from user_procedures pr 
    ) loop
      execute immediate c.cmd;
    end loop;
	
  end;                          
begin
  CreateGrantToUser;
end;
/

