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


-- STORICIZZA CERTIFICAZIONE
-- GRANT SU PBANDI_STORAGE
GRANT SELECT ON PBANDI.pbandi_t_proposta_certificaz TO PBANDI_STORAGE;
GRANT SELECT ON PBANDI.pbandi_r_prop_cert_lin_sp_val TO PBANDI_STORAGE;
GRANT SELECT ON PBANDI.pbandi_r_prop_cert_scost_asse TO PBANDI_STORAGE;
GRANT SELECT ON PBANDI.pbandi_r_proposta_certif_linea TO PBANDI_STORAGE;
GRANT SELECT ON PBANDI.pbandi_t_dett_proposta_certif TO PBANDI_STORAGE;
GRANT SELECT ON PBANDI.pbandi_t_preview_dett_prop_cer TO PBANDI_STORAGE;
GRANT SELECT ON PBANDI.pbandi_r_det_prop_cer_checklis  TO PBANDI_STORAGE;
GRANT SELECT ON PBANDI.pbandi_r_det_prop_cer_mand_qui TO PBANDI_STORAGE;
GRANT SELECT ON PBANDI.pbandi_r_det_prop_cer_qp_doc TO PBANDI_STORAGE;
GRANT SELECT ON PBANDI.pbandi_r_det_prop_cer_sogg_fin TO PBANDI_STORAGE;
GRANT SELECT ON PBANDI.pbandi_r_det_prop_ind_sede_int TO PBANDI_STORAGE;
GRANT SELECT ON PBANDI.pbandi_r_dett_prop_cer_fideius TO PBANDI_STORAGE;
GRANT SELECT ON PBANDI.pbandi_r_dett_prop_cer_lin_ant TO PBANDI_STORAGE;
GRANT SELECT ON PBANDI.pbandi_r_dett_prop_cert_erogaz TO PBANDI_STORAGE;
GRANT SELECT ON PBANDI.pbandi_r_dett_prop_certif_recu TO PBANDI_STORAGE;
GRANT SELECT ON PBANDI.pbandi_r_dett_prop_cert_revoca TO PBANDI_STORAGE;
GRANT SELECT ON PBANDI.pbandi_d_stato_proposta_certif TO PBANDI_STORAGE;
-- GRANT SU PBANDI_STORAGE_RW
GRANT SELECT ON PBANDI.pbandi_t_proposta_certificaz TO PBANDI_STORAGE_RW;
GRANT SELECT ON PBANDI.pbandi_r_prop_cert_lin_sp_val TO PBANDI_STORAGE_RW;
GRANT SELECT ON PBANDI.pbandi_r_prop_cert_scost_asse TO PBANDI_STORAGE_RW;
GRANT SELECT ON PBANDI.pbandi_r_proposta_certif_linea TO PBANDI_STORAGE_RW;
GRANT SELECT ON PBANDI.pbandi_t_dett_proposta_certif TO PBANDI_STORAGE_RW;
GRANT SELECT ON PBANDI.pbandi_t_preview_dett_prop_cer TO PBANDI_STORAGE_RW;
GRANT SELECT ON PBANDI.pbandi_r_det_prop_cer_checklis  TO PBANDI_STORAGE_RW;
GRANT SELECT ON PBANDI.pbandi_r_det_prop_cer_mand_qui TO PBANDI_STORAGE_RW;
GRANT SELECT ON PBANDI.pbandi_r_det_prop_cer_qp_doc TO PBANDI_STORAGE_RW;
GRANT SELECT ON PBANDI.pbandi_r_det_prop_cer_sogg_fin TO PBANDI_STORAGE_RW;
GRANT SELECT ON PBANDI.pbandi_r_det_prop_ind_sede_int TO PBANDI_STORAGE_RW;
GRANT SELECT ON PBANDI.pbandi_r_dett_prop_cer_fideius TO PBANDI_STORAGE_RW;
GRANT SELECT ON PBANDI.pbandi_r_dett_prop_cer_lin_ant TO PBANDI_STORAGE_RW;
GRANT SELECT ON PBANDI.pbandi_r_dett_prop_cert_erogaz TO PBANDI_STORAGE_RW;
GRANT SELECT ON PBANDI.pbandi_r_dett_prop_certif_recu TO PBANDI_STORAGE_RW;
GRANT SELECT ON PBANDI.pbandi_r_dett_prop_cert_revoca TO PBANDI_STORAGE_RW;
GRANT SELECT ON PBANDI.pbandi_d_stato_proposta_certif TO PBANDI_STORAGE_RW;
--
