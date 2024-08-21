/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

declare
  cursor curModAg is select modag5.ID_CONTO_ECONOMICO,modag2.QUOTA_IMPORTO_RICHIESTO 
                     from PBANDI_R_CONTO_ECONOM_MOD_AGEV modag2,PBANDI_R_CONTO_ECONOM_MOD_AGEV modag5
                     where modag2.ID_MODALITA_AGEVOLAZIONE = 2
                     and   modag5.ID_MODALITA_AGEVOLAZIONE = 5
                     and   modag2.ID_CONTO_ECONOMICO       = modag5.ID_CONTO_ECONOMICO;
                     
  cursor curModAg1 is select modag1.ID_CONTO_ECONOMICO,modag2.QUOTA_IMPORTO_RICHIESTO 
                     from PBANDI_R_CONTO_ECONOM_MOD_AGEV modag2,PBANDI_R_CONTO_ECONOM_MOD_AGEV modag1
                     where modag2.ID_MODALITA_AGEVOLAZIONE = 2
                     and   modag1.ID_MODALITA_AGEVOLAZIONE = 1
                     and   modag2.ID_CONTO_ECONOMICO       = modag1.ID_CONTO_ECONOMICO;                     
  nCont PLS_INTEGER;
begin
  select count(*)
  into   nCont
  from   PBANDI_T_EROGAZIONE   
  where  ID_MODALITA_AGEVOLAZIONE = 2;  
  
  if nCont != 0 then
    dbms_output.put_line('erogazione presente');
  else
  for recModAg in curModAg loop
    update  PBANDI_R_CONTO_ECONOM_MOD_AGEV
    set     QUOTA_IMPORTO_RICHIESTO  = nvl(QUOTA_IMPORTO_RICHIESTO,0) + nvl(recModAg.QUOTA_IMPORTO_RICHIESTO,0) ,
            id_utente_agg            = 0                  
    where   ID_MODALITA_AGEVOLAZIONE = 5
    and     ID_CONTO_ECONOMICO       = recModAg.ID_CONTO_ECONOMICO;
  end loop;
  
  for recModAg1 in curModAg1 loop
    update  PBANDI_R_CONTO_ECONOM_MOD_AGEV
    set     QUOTA_IMPORTO_RICHIESTO  = nvl(QUOTA_IMPORTO_RICHIESTO,0) + nvl(recModAg1.QUOTA_IMPORTO_RICHIESTO,0) ,
            id_utente_agg            = 0                  
    where   ID_MODALITA_AGEVOLAZIONE = 1
    and     ID_CONTO_ECONOMICO       = recModAg1.ID_CONTO_ECONOMICO;
  end loop;

  delete PBANDI_R_BANDO_MODALITA_AGEVOL	
  where  ID_MODALITA_AGEVOLAZIONE = 2;

  delete PBANDI_R_CONTO_ECONOM_MOD_AGEV   
  where  ID_MODALITA_AGEVOLAZIONE = 2;
  
  delete PBANDI_R_LINEA_INTERV_MOD_AGEV   
  where  ID_MODALITA_AGEVOLAZIONE = 2;
	
  delete PBANDI_T_EROGAZIONE   
  where  ID_MODALITA_AGEVOLAZIONE = 2;

  delete PBANDI_D_MODALITA_AGEVOLAZIONE
  where  ID_MODALITA_AGEVOLAZIONE = 2;
  end if;
exception
  when others then
    rollback;
    dbms_output.put_line('errore = '||sqlerrm||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
end;  
  