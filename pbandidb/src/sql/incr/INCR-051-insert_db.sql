/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

-----------------------------
--REVISIONE CERTIFICAZIONE --
-----------------------------

Insert into PBANDI_T_DETT_PROPOSTA_REV 
(select * from PBANDI_T_DETT_PROPOSTA_CERTIF
where id_proposta_certificaz = 957) -- DA VERIFICARE L'ULTIMA APPROVATA IN PRODUZIONE
/

Insert into PBANDI_R_DETT_PROP_REV_FIDEIUS 
  (select * from PBANDI_R_DETT_PROP_CER_FIDEIUS 
  where id_dett_proposta_certif In
  (select id_dett_proposta_certif from pbandi_t_dett_proposta_certif
  where id_proposta_certificaz = 957)) -- DA VERIFICARE L'ULTIMA APPROVATA IN PRODUZIONE
/ 
insert into pbandi_c_costanti (ATTRIBUTO, VALORE)
values ('indirizzoEmailReportCertificazioneRev', 'claudio.marchisio@regione.piemonte.it, paolo.furno@regione.piemonte.it, franco.caporale@regione.piemonte.it, silvia.bontempo@regione.piemonte.it, marino.clara@regione.piemonte, assistenzapiattaforma.bandi@csi.it');




/*
*** script da eseguire ad ogni rilascio
*/

update PBANDI_C_VERSIONE
set VERSIONE_DB = '3.22.0';

insert into PBANDI_C_ENTITA
(ID_ENTITA, NOME_ENTITA, FLAG_DA_TRACCIARE) 
(select seq_PBANDI_C_ENTITA.nextval,tabs.TABLE_NAME,'S'
from all_tables tabs
where tabs.OWNER = (select decode(instr(user,'_RW'),0,user,replace(user,'_RW',null)) from dual)
and tabs.TABLE_NAME like 'PBANDI_%'
and not exists (select 'x' from pbandi_c_entita where tabs.TABLE_NAME = NOME_ENTITA));

commit;

declare
  cursor curAttr is select col.COLUMN_NAME,en.id_ENTITA,col.TABLE_NAME
                    from all_tab_cols col,PBANDI_C_ENTITA en
                    where col.owner = (select decode(instr(user,'_RW'),0,user,replace(user,'_RW',null)) from dual)
                    and col.TABLE_NAME like 'PBANDI_%'
                    and col.COLUMN_NAME not like 'SYS_%'
                    and col.TABLE_NAME = en.NOME_ENTITA
                    and not exists (select 'x' from PBANDI_C_ATTRIBUTO att 
                                    where col.COLUMN_NAME = att.NOME_ATTRIBUTO
                                    and att.id_entita = en.id_ENTITA);
                                    
  nPosiz  PLS_INTEGER := NULL;                                                                          
begin
  for recAttr in curAttr loop

    BEGIN
      SELECT POSITION
      INTO   nPosiz 
      FROM   all_CONSTRAINTS A,all_CONS_COLUMNS B
      WHERE  A.CONSTRAINT_TYPE = 'P'
      AND    A.TABLE_NAME      = recAttr.TABLE_NAME
      AND    A.CONSTRAINT_NAME = B.CONSTRAINT_NAME
      AND    COLUMN_NAME       = recAttr.COLUMN_NAME;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        nPosiz := NULL;
    END;
    
    insert into PBANDI_C_ATTRIBUTO
    (ID_ATTRIBUTO, 
     NOME_ATTRIBUTO, 
     FLAG_DA_TRACCIARE, 
     ID_ENTITA,
     KEY_POSITION_ID) 
    values
    (SEQ_PBANDI_C_ATTRIBUTO.NEXTVAL,
     recAttr.COLUMN_NAME,
     'N',
     recAttr.id_ENTITA,
     nPosiz);
  end loop;
  COMMIT;
end;
/

