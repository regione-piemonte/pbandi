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
	
    tUser(2).nomeUser := 'PBANDI_RO';
    tUser(2).comando  := 'grant select on ';
	
	tUser(3).nomeUser := 'PBANDI_MONIT2';
	tUser(3).comando  := 'grant select on ';

	tUser(4).nomeUser := 'PBANDI_MONIT2_RW';
	tUser(4).comando  := 'grant select on ';
	
	tUser(5).nomeUser := 'PBANDI_MONIT';
	tUser(5).comando  := 'grant select on ';

	tUser(6).nomeUser := 'PBANDI_MONIT_RW';
	tUser(6).comando  := 'grant select on ';
	
	FOR i IN tUser.FIRST..tUser.LAST LOOP
	--grant sulle tabelle
    for c in ( 
       select tUser(i).comando||tb.table_name||' to '||tUser(i).nomeUser cmd from user_tables tb 
	   where table_name NOT IN ('TAB_PAGAMENTI_NT','PBANDI_W_PROG_SOGG_FIN_LOG')
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
	    where object_type != 'TRIGGER'
    ) loop
      execute immediate c.cmd;
    end loop;
	
    -- Grant su Tipi
    for c in ( 
       select distinct 'grant execute on '||ty.type_name||' to '||tUser(i).nomeUser cmd from user_types ty
    ) loop
      execute immediate c.cmd;
    end loop;    
    end loop;
  end;                          
begin
  CreateGrantToUser;
end;
/
REVOKE INSERT,UPDATE,DELETE ON PBANDI_D_TIPO_TASK FROM PBANDI_RW;
REVOKE INSERT,UPDATE,DELETE ON PBANDI_D_TASK FROM PBANDI_RW;
REVOKE INSERT,UPDATE,DELETE ON PBANDI_D_MULTI_TASK FROM PBANDI_RW;
REVOKE INSERT,UPDATE,DELETE ON PBANDI_D_METODO_TASK FROM PBANDI_RW;
REVOKE INSERT,UPDATE,DELETE ON PBANDI_T_STEP_PROCESSO FROM PBANDI_RW;
REVOKE INSERT,UPDATE,DELETE ON PBANDI_R_STEP_PROCESSO_RUOLO FROM PBANDI_RW;
