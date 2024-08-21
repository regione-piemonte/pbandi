/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SET DEFINE OFF;
Insert into PBANDI_D_STATO_PROPOSTA_CERTIF
   (ID_STATO_PROPOSTA_CERTIF, DESC_BREVE_STATO_PROPOSTA_CERT, DESC_STATO_PROPOSTA_CERTIF, DT_INIZIO_VALIDITA)
 Values
   (8, 'bozza', 'bozza', TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
COMMIT;

SET DEFINE OFF;
Insert into PBANDI_C_TIPO_DOCUMENTO_INDEX
   (ID_TIPO_DOCUMENTO_INDEX, DESC_BREVE_TIPO_DOC_INDEX, DESC_TIPO_DOC_INDEX)
 Values
   (14, 'FPC', 'FILE DELLA PROPOSTA DI CERTIFICAZIONE');
COMMIT;

Insert into pbandi_d_tipo_stato_prop_cert
(id_tipo_stato_prop_cert,desc_breve_tipo_stato_pro_cert,desc_tipo_stato_prop_cert,dt_inizio_validita)
values
(1,'automatico','automatico',TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));

Insert into pbandi_d_tipo_stato_prop_cert
(id_tipo_stato_prop_cert,desc_breve_tipo_stato_pro_cert,desc_tipo_stato_prop_cert,dt_inizio_validita)
values
(2,'adg','adg',TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));

Insert into pbandi_d_tipo_stato_prop_cert
(id_tipo_stato_prop_cert,desc_breve_tipo_stato_pro_cert,desc_tipo_stato_prop_cert,dt_inizio_validita)
values
(3,'adc','adc',TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
COMMIT;

UPDATE pbandi_d_stato_proposta_certif	
SET id_tipo_stato_prop_cert = 1
WHERE DESC_BREVE_STATO_PROPOSTA_CERT IN ('00coda','03elab','04err');

UPDATE pbandi_d_stato_proposta_certif	
SET id_tipo_stato_prop_cert = 2
WHERE DESC_BREVE_STATO_PROPOSTA_CERT IN ('05open','bozza');

UPDATE pbandi_d_stato_proposta_certif	
SET id_tipo_stato_prop_cert = 3
WHERE DESC_BREVE_STATO_PROPOSTA_CERT IN ('06annu','07appr','09resp');

COMMIT;

ALTER TABLE pbandi_d_stato_proposta_certif	
MODIFY id_tipo_stato_prop_cert  NOT NULL ;

update pbandi_d_fase_monit
set flag_obbligatorio = 'N'
where COD_IGRUE_T35 IN ('A00','A01','A02','A07');

update pbandi_d_fase_monit
set flag_obbligatorio = 'S'
where COD_IGRUE_T35 NOT IN ('A00','A01','A02','A07');

alter table pbandi_d_fase_monit
modify flag_obbligatorio not null;

update pbandi_d_fase_monit
set flag_controllo_indicat = 'S'
where COD_IGRUE_T35 IN ('A04','B02','C02','D02','E02','F02','G02');

update pbandi_d_fase_monit
set flag_controllo_indicat = 'N'
where COD_IGRUE_T35 NOT IN ('A04','B02','C02','D02','E02','F02','G02');

commit;

alter table pbandi_d_fase_monit
modify flag_controllo_indicat not null;

SET DEFINE OFF;
Insert into PBANDI_C_TIPO_DOCUMENTO_INDEX
   (ID_TIPO_DOCUMENTO_INDEX, DESC_BREVE_TIPO_DOC_INDEX, DESC_TIPO_DOC_INDEX)
 Values
   (10, 'CLIL', 'CHECK LIST IN LOCO');
COMMIT;


SET DEFINE OFF;
Insert into PBANDI_D_STATO_PROPOSTA_CERTIF
   (ID_STATO_PROPOSTA_CERTIF, DESC_BREVE_STATO_PROPOSTA_CERT, DESC_STATO_PROPOSTA_CERTIF, DT_INIZIO_VALIDITA, ID_TIPO_STATO_PROP_CERT)
 Values
   (9, '00coda-bozza', 'in coda (bozza)', TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 1);
Insert into PBANDI_D_STATO_PROPOSTA_CERTIF
   (ID_STATO_PROPOSTA_CERTIF, DESC_BREVE_STATO_PROPOSTA_CERT, DESC_STATO_PROPOSTA_CERTIF, DT_INIZIO_VALIDITA, ID_TIPO_STATO_PROP_CERT)
 Values
   (10, '03elab-bozza', 'in elaborazione (bozza)', TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 1);
Insert into PBANDI_D_STATO_PROPOSTA_CERTIF
   (ID_STATO_PROPOSTA_CERTIF, DESC_BREVE_STATO_PROPOSTA_CERT, DESC_STATO_PROPOSTA_CERTIF, DT_INIZIO_VALIDITA, ID_TIPO_STATO_PROP_CERT)
 Values
   (11, '04err-bozza', 'errore durante l''elaborazione (bozza)', TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 1);
COMMIT;

Insert into PBANDI_C_REGOLA
(ID_REGOLA,DESC_BREVE_REGOLA,DESC_REGOLA,DT_INIZIO_VALIDITA)
values
(SEQ_PBANDI_C_REGOLA.nextval,'BR32','Specifica se per i progetti di un bando_linea, i controlli nel quadro previsionale sul totale del nuovo preventivo rispetto al totale dell''ultima spesa ammessa sono BLOCCANTI',to_date('01/01/2009','dd/mm/yyyy'));
COMMIT;

SET DEFINE OFF;
Insert into PBANDI_C_TIPO_DOCUMENTO_INDEX
   (ID_TIPO_DOCUMENTO_INDEX, DESC_BREVE_TIPO_DOC_INDEX, DESC_TIPO_DOC_INDEX)
 Values
   (15, 'RT', 'RELAZIONE TECNICA');
COMMIT;

SET DEFINE OFF;
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (2, 1, 15);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (3, 1, 15);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (4, 1, 15);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (5, 1, 15);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (6, 1, 15);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (8, 1, 15);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (10, 1, 15);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (11, 1, 15);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (14, 1, 15);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (16, 1, 15);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (18, 1, 15);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (19, 1, 15);
COMMIT;

Insert into PBANDI_C_REGOLA
(ID_REGOLA,DESC_BREVE_REGOLA,DESC_REGOLA,DT_INIZIO_VALIDITA)
values
(SEQ_PBANDI_C_REGOLA.nextval,'BR33','Richiesta di upload del file della relazione tecnica in invio della dichiarazione di spesa (obbligatorio se dichiarazione finale)',to_date('01/01/2009','dd/mm/yyyy'));
COMMIT;

update PBANDI_C_REGOLA
set DESC_REGOLA = 'Filtro ricerca su tipologia di beneficiario per documenti di spesa e visualizzazione conto economico'
where ID_REGOLA = 8;
commit;

INSERT INTO PBANDI_D_TIPO_ANAGRAFICA 
(ID_TIPO_ANAGRAFICA, DESC_BREVE_TIPO_ANAGRAFICA, DESC_TIPO_ANAGRAFICA,DT_INIZIO_VALIDITA) 
VALUES 
(21 , 'GDF','Operatore della Guardia di Finanza',TO_DATE('01/01/2009', 'MM/DD/YYYY'));

INSERT INTO PBANDI_R_RUOLO_TIPO_ANAGRAFICA 
(ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_RUOLO_TIPO_ANAGRAFICA) 
VALUES 
(4 ,21 ,42 );

INSERT INTO PBANDI_R_RUOLO_TIPO_ANAGRAFICA 
(ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_RUOLO_TIPO_ANAGRAFICA) 
VALUES 
(3 ,21 ,43 );

INSERT INTO PBANDI_R_RUOLO_TIPO_ANAGRAFICA 
(ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_RUOLO_TIPO_ANAGRAFICA) 
VALUES 
(7 ,21 ,44 );

commit;

Insert into PBANDI_C_REGOLA
(ID_REGOLA,DESC_BREVE_REGOLA,DESC_REGOLA,DT_INIZIO_VALIDITA)
values
(SEQ_PBANDI_C_REGOLA.nextval,'BR34','Specifica se per i progetti di un bando_linea, e'' necessario caricare i dati del cronoprogramma',to_date('01/01/2009','dd/mm/yyyy'));
Insert into PBANDI_C_REGOLA
(ID_REGOLA,DESC_BREVE_REGOLA,DESC_REGOLA,DT_INIZIO_VALIDITA)
values
(SEQ_PBANDI_C_REGOLA.nextval,'BR35','Specifica se per i progetti di un bando_linea, e'' necessario caricare gli indicatori di progetto',to_date('01/01/2009','dd/mm/yyyy'));
COMMIT;

insert into PBANDI_R_STATO_TIPO_DOC_INDEX
(ID_STATO_TIPO_DOC_INDEX,ID_TIPO_DOCUMENTO_INDEX)
values
(4,10);
commit;

declare
  nIdContoEconomicoMaster  PBANDI_T_CONTO_ECONOMICO.ID_CONTO_ECONOMICO%type;
begin
  for recCe IN (select distinct CE.ID_CONTO_ECONOMICO,CE.ID_DOMANDA
                from pbandi_t_conto_economico ce
                where CE.ID_STATO_CONTO_ECONOMICO = 11
                and not exists (select 'x' from pbandi_t_rigo_conto_economico rce
                                where CE.ID_CONTO_ECONOMICO = RCE.ID_CONTO_ECONOMICO)) loop
    
    select CE.ID_CONTO_ECONOMICO
    into   nIdContoEconomicoMaster
    from   PBANDI_T_CONTO_ECONOMICO ce,PBANDI_D_STATO_CONTO_ECONOMICO sce,
           PBANDI_D_TIPOLOGIA_CONTO_ECON tce 
    where  CE.id_domanda                             = recCe.ID_DOMANDA
    and    CE.dt_fine_validita                       is null
    and    CE.ID_STATO_CONTO_ECONOMICO               = SCE.ID_STATO_CONTO_ECONOMICO
    and    SCE.ID_TIPOLOGIA_CONTO_ECONOMICO          = TCE.ID_TIPOLOGIA_CONTO_ECONOMICO
    and    UPPER(TCE.DESC_BREVE_TIPOLOGIA_CONTO_ECO) = 'MASTER';    
    
    INSERT INTO PBANDI_T_RIGO_CONTO_ECONOMICO
    (ID_RIGO_CONTO_ECONOMICO, IMPORTO_SPESA, 
     IMPORTO_AMMESSO_FINANZIAMENTO, IMPORTO_CONTRIBUTO, 
     ID_UTENTE_INS, DT_INIZIO_VALIDITA, 
     DT_FINE_VALIDITA, ID_CONTO_ECONOMICO, ID_VOCE_DI_SPESA)
    (SELECT SEQ_PBANDI_T_RIGO_CONTO_ECONOM.NEXTVAL,IMPORTO_SPESA, 
            IMPORTO_AMMESSO_FINANZIAMENTO, IMPORTO_CONTRIBUTO, 
            2, DT_INIZIO_VALIDITA, 
            DT_FINE_VALIDITA, recCe.ID_CONTO_ECONOMICO, ID_VOCE_DI_SPESA
     FROM   PBANDI_T_RIGO_CONTO_ECONOMICO
     WHERE  ID_CONTO_ECONOMICO = nIdContoEconomicoMaster);    
  end loop;
  commit;
end;
/  

insert into PBANDI_D_STATO_TIPO_DOC_INDEX
(ID_STATO_TIPO_DOC_INDEX, DESC_BREVE_STATO_TIP_DOC_INDEX, DESC_STATO_TIPO_DOC_INDEX, DT_INIZIO_VALIDITA)
values
(5,'IP','INVIATO (PREGRESSO)',to_date('01/01/2009','dd/mm/yyyy'));

DECLARE  
  TYPE recTbTarget IS TABLE OF PBANDI_T_DOCUMENTO_INDEX.ID_TARGET%TYPE index by pls_integer;
  
  vTbTarget  recTbTarget;
  
  nMaxId  PBANDI_T_DOCUMENTO_INDEX.ID_DOCUMENTO_INDEX%TYPE;  
BEGIN
  select ID_TARGET
  BULK COLLECT INTO vTbTarget
  from PBANDI_T_DOCUMENTO_INDEX
  where ID_TIPO_DOCUMENTO_INDEX = 2
  group by ID_TARGET
  having count(*) > 1;
  
  for i in vTbTarget.first..vTbTarget.last loop
    select max(ID_DOCUMENTO_INDEX)
    into   nMaxId
    from   PBANDI_T_DOCUMENTO_INDEX
    where  ID_TIPO_DOCUMENTO_INDEX = 2
    and    ID_TARGET               = vTbTarget(i);
    
    delete PBANDI_T_DOCUMENTO_INDEX
    where  ID_DOCUMENTO_INDEX      != nMaxId
    and    ID_TIPO_DOCUMENTO_INDEX = 2
    and    ID_TARGET               = vTbTarget(i);
  end loop;  
  commit;
END;  
/

declare
  vCn  VARCHAR2(300);
begin
  for rec in (select * from PBANDI_T_DOCUMENTO_INDEX
		      where ID_TIPO_DOCUMENTO_INDEX = 2) loop
  
    begin
	select cognomeNome 
	into vCn
	from (select pf.COGNOME||' '||pf.NOME cognomeNome
		  from pbandi_t_utente u,PBANDI_T_PERSONA_FISICA pf
		  where u.id_utente = rec.id_utente_ins
		  and   u.id_soggetto = pf.id_soggetto
		  order by id_persona_fisica desc)
	where rownum = 1;
  
    insert into PBANDI_T_CHECKLIST
    (DT_CONTROLLO, SOGGETTO_CONTROLLORE, ID_CHECKLIST, ID_DICHIARAZIONE_SPESA,  
     ID_DOCUMENTO_INDEX)
    values
	(trunc(rec.DT_INSERIMENTO_INDEX),vCn,seq_PBANDI_T_CHECKLIST.nextval,rec.id_target,
	 rec.ID_DOCUMENTO_INDEX);
	exception
    when others then
    null;
    end;	
  end loop;
  commit;
end;
/

insert into PBANDI_R_STATO_TIPO_DOC_INDEX 
(ID_STATO_TIPO_DOC_INDEX, ID_TIPO_DOCUMENTO_INDEX) 
values
(5,2);
 
insert into PBANDI_R_DOCU_INDEX_TIPO_STATO
(ID_DOCUMENTO_INDEX,ID_STATO_TIPO_DOC_INDEX,ID_TIPO_DOCUMENTO_INDEX)
(select ID_DOCUMENTO_INDEX,5,2
 from PBANDI_T_CHECKLIST);
 
commit; 

insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(1  ,'GESINC'     ,'Abilitazione Gestione incarichi',                         to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(2  ,'GUIBKO'     ,'Voce con la guida backoffice (pdf)',                        to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(3  ,'GUIASS'     ,'Voce con la guida associazioni progetto-istruttori (pdf)',to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(4  ,'GUIBEN'     ,'Voce con la guida per il beneficiario (pdf)',             to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(5  ,'GUIIST'     ,'Voce con la guida per l''istruttore (pdf)',               to_date('01/01/2009','dd/mm/yyyy')); 
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(6  ,'GUICER'     ,'Voce con la guida della certificazione (pdf)',            to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(7  ,'GUIUTE'     ,'Voce con la guida dell''inserimento utenti (pdf)',        to_date('01/01/2009','dd/mm/yyyy')); 
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(8  ,'GUIAVV'     ,'Voce con la guida per l''avvio dei progetti (pdf)',         to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(9  ,'GUIIRR'     ,'Voce con la guida per la gestione irregolarita'' ADG',    to_date('01/01/2009','dd/mm/yyyy')); 
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(10 ,'MENUAMM'    ,'Voce di menu amministrazione',                            to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(11 ,'MENUCERT'   ,'Voce di menu certtificazione',                            to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(12 ,'MENUWKSBKO' ,'Voce di menu workspace backoffice',                       to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(13 ,'OPECER010'  ,'Elenco proposte cert',                                    to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(14 ,'OPECER011'  ,'Nuova Proposta cert',                                     to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(15 ,'OPECER012'  ,'Ricerca allegati e proposte ',                            to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(16 ,'OPECER023'  ,'Aggiorna stato proposta cert',                            to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(17 ,'OPECER025'  ,'Crea allegato proposta cert',                             to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(18 ,'OPECER027'  ,'Visualizza dati proposta cert',                           to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(19 ,'OPECON001'  ,'Proposta di rimodulazione ce',                            to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(20 ,'OPECON002'  ,'Rimodulazione ce',                                        to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(21 ,'OPECON003'  ,'Invia proposta rimodulazione',                            to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(22 ,'OPECON004'  ,'Concludi rimodulazione ce',                               to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(23 ,'OPECON005'  ,'Visiona modalità agevolazione',                           to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(24 ,'OPECON006'  ,'Visiualizza quadro previsionale',                         to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(25 ,'OPECON007'  ,'Modifica quadro previsionale',                            to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(26 ,'OPEREN001'  ,'Visualizza conto economico',                              to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(27 ,'OPEREN010'  ,'Invia dichiarazione di spesa',                            to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(28 ,'OPEREN011'  ,'Visualizza elenco documenti',                             to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(29 ,'OPEREN012'  ,'Modifica documento di spesa',                             to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(30 ,'OPEREN013'  ,'Elimina documento di spesa',                              to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(31 ,'OPEREN014'  ,'Inserisci documento di spesa',                            to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(32 ,'OPEREN015'  ,'Visualizza elenco pagamenti',                             to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(33 ,'OPEREN016'  ,'Modifica pagamento',                                      to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(34 ,'OPEREN017'  ,'Inserisci pagamento',                                     to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(35 ,'OPEREN019'  ,'Elimina pagamento',                                       to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(36 ,'OPEREN022'  ,'Rettifica spesa validata',                                to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(37 ,'OPEREN023'  ,'Valida i pagamenti del documento di spesa',               to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(38 ,'OPEREN024'  ,'Ricerca fornitore',                                       to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(39 ,'OPEREN025'  ,'Inserisci Fornitore',                                     to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(40 ,'OPEREN026'  ,'Modifica Fornitore',                                      to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(41 ,'OPEREN027'  ,'Elimina Fornitore',                                       to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(42 ,'OPEREN028'  ,'Avvia gestione progetto',                                 to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(43 ,'OPEREN029'  ,'Dettaglio documento di spesa',                            to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(44 ,'OPEREN030'  ,'Export excel ricerca',                                    to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(45 ,'OPEREN032'  ,'Visualizza elenco voci di spesa associate al documento',  to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(46 ,'OPEREN033'  ,'Associa nuova voce di spesa al documento',                to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(47 ,'OPEREN034'  ,'Modifica associazione documento alla voce di spesa',      to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(48 ,'OPEREN035'  ,'Elimina associazione documento alla voce di spesa',       to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(49 ,'OPEREN036'  ,'Dettaglio Fornitore',                                     to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(50 ,'OPEREN039'  ,'Chiudi validazione dichiarazione spesa',                  to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(51 ,'OPEREN040'  ,'Operazioni automatiche su pagamenti',                     to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(52 ,'OPEROG001'  ,'Richiesta erogazione',                                    to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(53 ,'OPEROG003'  ,'Inserisci erogazione',                                    to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(54 ,'OPEROG004'  ,'Modifica erogazione',                                     to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(55 ,'OPEROG006'  ,'Ricerca fideiussione',                                    to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(56 ,'OPEROG007'  ,'Dettaglio fideiussione',                                  to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(57 ,'OPEROG008'  ,'Inserisci fideiussione',                                  to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(58 ,'OPEROG009'  ,'Modifica fideiussione',                                   to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(59 ,'OPEROG010'  ,'Elimina fideiussione',                                    to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(60 ,'OPEROG011'  ,'Inserisci revoche',                                       to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(61 ,'OPEROG012'  ,'Inserisci recuperi',                                      to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(62 ,'OPEROG013'  ,'Modifica revoca',                                         to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(63 ,'OPEROG014'  ,'Modifica recuperi',                                       to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(64 ,'RICPDFDIC'  ,'Ricrea Dichiarazione di spesa',                           to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(65 ,'TRSACC001'  ,'Accesso al sistema',                                      to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(66 ,'TRSACC002'  ,'Scelta Beneficiario',                                     to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(67 ,'TRSBKO001'  ,'Ricerca istruttore',                                      to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(68 ,'TRSBKO002'  ,'Dettaglio istruttore',                                    to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(69 ,'TRSBKO003'  ,'Associa istruttore',                                      to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(70 ,'TRSBKO004'  ,'Disassocia istruttore',                                   to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(71 ,'TRSBKO005'  ,'Configurazione Bando',                                    to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(72 ,'TRSBKO006'  ,'Configurazione Bando-Linea',                              to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(73 ,'TRSBKO008'  ,'Ricerca Bando',                                           to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(74 ,'TRSBKO009'  ,'Gestione associazioni tab',                               to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(75 ,'TRSBKO010'  ,'Ricerca documentazione progetto',                         to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(76 ,'TRSWKS001'  ,'Visione elenco processi',                                 to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(77 ,'TRSWKS002'  ,'Avvia istanza di processo',                               to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(78 ,'TRSWKS003'  ,'Visione elenco attivita''',                               to_date('01/01/2009','dd/mm/yyyy'));  
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(79 ,'TRSWKS004'  ,'Avvia esecuzione attivita''',                             to_date('01/01/2009','dd/mm/yyyy'));  
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(80 ,'TRSWKS005'  ,'Esegue l''attivita''',                                    to_date('01/01/2009','dd/mm/yyyy'));   
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(81 ,'TRSNOT001'  ,'Visualizza Notifica',                                     to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(82 ,'TRSBKO011'  ,'Dettaglio Utente',                                        to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(83 ,'TRSBKO012'  ,'Gestione associazioni beneficiari-progetto',              to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(84 ,'TRSBKO013'  ,'Migrazione istanze processi',                             to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(85 ,'TRSBKO014'  ,'Aggiorna candidati processo',                             to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(86 ,'TRSBKO016'  ,'Ricerca Utente',                                          to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(87 ,'TRSBKO017'  ,'Aggiungi Utente',                                         to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(88 ,'TRSBKO018'  ,'Modifica Utente',                                         to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(89 ,'TRSBKO019'  ,'Elimina Utente',                                          to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(90 ,'TRSCSP001'  ,'Caricamento scheda progetto',                             to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(91 ,'OPEIRR001'  ,'Visualizza elenco irregolarita''',                        to_date('01/01/2009','dd/mm/yyyy'));  
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(92 ,'OPEIRR001-1','Visibilita'' combo progetto/beneficiario',                to_date('01/01/2009','dd/mm/yyyy'));  
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(93 ,'OPEIRR002'  ,'Inserisci irregolarita''',                                to_date('01/01/2009','dd/mm/yyyy'));  
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(94 ,'OPEIRR003'  ,'Modifica irregolarita''',                                 to_date('01/01/2009','dd/mm/yyyy'));  
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(95 ,'OPEIRR004'  ,'Dettaglio irregolarita''',                                to_date('01/01/2009','dd/mm/yyyy'));  
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(96 ,'OPEIRR005'  ,'Elimina irregolarita''',                                  to_date('01/01/2009','dd/mm/yyyy'));  
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(97 ,'OPEIRR006'  ,'Registra invio',                                          to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(98 ,'OPEIRR007'  ,'Blocca irregolarita''',                                   to_date('01/01/2009','dd/mm/yyyy'));  
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(99 ,'OPEREN049'  ,'Comunicazione rinuncia',                                  to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(100,'OPEREN043'  ,'Crea nuova checklist',                                    to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(101,'OPEREN044'  ,'Visualizza elenco checklist',                             to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(102,'OPEREN047'  ,'Modifica Checklist',                                      to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(103,'OPEREN048'  ,'Elimina checklist',                                       to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(104,'OPEAPP001'  ,'Visualizza Appalti',                                      to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(105,'OPEAPP002'  ,'Inserisci Appalto',                                       to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(106,'OPEAPP003'  ,'Modifica Appalto',                                        to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(107,'OPEAPP004'  ,'Elimina Appalto',                                         to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(108,'OPEAGG001'  ,'Visualizza Procedure Aggiudicazione',                     to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(109,'OPEAGG002'  ,'Inserisci P. Agg.',                                       to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(110,'OPEAGG003'  ,'Modifica P.Agg.',                                         to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(111,'OPEIND001'  ,'Gestione indicatori',                                     to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(112,'OPEIND002'  ,'Esporta elenco indicatori',                               to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_permesso(id_permesso,desc_breve_permesso,desc_permesso,dt_inizio_validita) values(113,'OPECRN001'  ,'Gestione cronoprogramma',                                 to_date('01/01/2009','dd/mm/yyyy'));

insert into pbandi_r_permesso_tipo_anagraf
(id_permesso,id_tipo_anagrafica,dt_inizio_validita)
(select id_permesso,id_tipo_anagrafica,to_date('01/01/2009','dd/mm/yyyy')
 from pbandi_d_permesso,pbandi_d_tipo_anagrafica
 where desc_breve_permesso in ('GUIBKO',
'GUIUTE',
'MENUAMM',
'MENUWKSBKO',
'OPEREN028',
'RICPDFDIC',
'TRSACC001',
'TRSBKO005',
'TRSBKO006',
'TRSBKO008',
'TRSBKO009',
'TRSBKO010',
'TRSWKS001',
'TRSWKS002',
'TRSBKO011',
'TRSBKO012',
'TRSBKO013',
'TRSBKO014',
'TRSBKO016',
'TRSBKO017',
'TRSBKO018',
'TRSBKO019')
 and DESC_BREVE_TIPO_ANAGRAFICA = 'CSI-ADMIN');
 
insert into pbandi_r_permesso_tipo_anagraf
(id_permesso,id_tipo_anagrafica,dt_inizio_validita)
(select id_permesso,id_tipo_anagrafica,to_date('01/01/2009','dd/mm/yyyy')
 from pbandi_d_permesso,pbandi_d_tipo_anagrafica
 where desc_breve_permesso in ('GUIBKO',
'GUIUTE',
'MENUAMM',
'MENUWKSBKO',
'OPEREN028',
'TRSACC001',
'TRSBKO005',
'TRSBKO006',
'TRSBKO008',
'TRSBKO009',
'TRSBKO010',
'TRSWKS001',
'TRSWKS002',
'TRSBKO011',
'TRSBKO012',
'TRSBKO014',
'TRSBKO016',
'TRSBKO017',
'TRSBKO018',
'TRSBKO019')
 and DESC_BREVE_TIPO_ANAGRAFICA = 'CSI-ASSISTENZA');
 
insert into pbandi_r_permesso_tipo_anagraf
(id_permesso,id_tipo_anagrafica,dt_inizio_validita)
(select id_permesso,id_tipo_anagrafica,to_date('01/01/2009','dd/mm/yyyy')
 from pbandi_d_permesso,pbandi_d_tipo_anagrafica
 where desc_breve_permesso in ('GESINC',
'GUIBEN',
'MENUAMM',
'OPECON001',
'OPECON003',
'OPECON005',
'OPECON006',
'OPECON007',
'OPEREN001',
'OPEREN010',
'OPEREN011',
'OPEREN012',
'OPEREN013',
'OPEREN014',
'OPEREN015',
'OPEREN016',
'OPEREN017',
'OPEREN019',
'OPEREN024',
'OPEREN025',
'OPEREN026',
'OPEREN027',
'OPEREN029',
'OPEREN032',
'OPEREN033',
'OPEREN034',
'OPEREN035',
'OPEREN036',
'OPEROG001',
'OPEROG006',
'OPEROG007',
'OPEROG008',
'OPEROG009',
'OPEROG010',
'TRSACC001',
'TRSACC002',
'TRSBKO010',
'TRSWKS003',
'TRSWKS004',
'TRSWKS005',
'TRSNOT001',
'OPEREN049',
'OPEAGG001',
'OPEAGG002',
'OPEIND001',
'OPECRN001')
 and DESC_BREVE_TIPO_ANAGRAFICA = 'PERSONA-FISICA'); 
 
insert into pbandi_r_permesso_tipo_anagraf
(id_permesso,id_tipo_anagrafica,dt_inizio_validita)
(select id_permesso,id_tipo_anagrafica,to_date('01/01/2009','dd/mm/yyyy')
 from pbandi_d_permesso,pbandi_d_tipo_anagrafica
 where desc_breve_permesso in ('GESINC',   
'GUIBEN',   
'MENUAMM',  
'OPECON001',
'OPECON003',
'OPECON005',
'OPECON006',
'OPECON007',
'OPEREN001',
'OPEREN010',
'OPEREN011',
'OPEREN012',
'OPEREN013',
'OPEREN014',
'OPEREN015',
'OPEREN016',
'OPEREN017',
'OPEREN019',
'OPEREN024',
'OPEREN025',
'OPEREN026',
'OPEREN027',
'OPEREN029',
'OPEREN032',
'OPEREN033',
'OPEREN034',
'OPEREN035',
'OPEREN036',
'OPEROG001',
'OPEROG006',
'OPEROG007',
'OPEROG008',
'OPEROG009',
'OPEROG010',
'TRSACC001',
'TRSACC002',
'TRSBKO010',
'TRSWKS003',
'TRSWKS004',
'TRSWKS005',
'TRSNOT001',
'OPEREN049',
'OPEAGG001',
'OPEAGG002',
'OPEIND001',
'OPECRN001')
 and DESC_BREVE_TIPO_ANAGRAFICA = 'BEN-MASTER');  
 
insert into pbandi_r_permesso_tipo_anagraf
(id_permesso,id_tipo_anagrafica,dt_inizio_validita)
(select id_permesso,id_tipo_anagrafica,to_date('01/01/2009','dd/mm/yyyy')
 from pbandi_d_permesso,pbandi_d_tipo_anagrafica
 where desc_breve_permesso in ('GUIBEN',   
'GUIIST',   
'MENUAMM',  
'OPEREN001',
'OPEREN011',
'OPEREN015',
'OPEREN024',
'OPEREN029',
'OPEREN032',
'OPEREN036',
'TRSACC001',
'TRSACC002',
'TRSBKO010',
'TRSWKS003',
'TRSWKS004',
'TRSWKS005')
 and DESC_BREVE_TIPO_ANAGRAFICA = 'MONITORAGGIO'); 

insert into pbandi_r_permesso_tipo_anagraf
(id_permesso,id_tipo_anagrafica,dt_inizio_validita)
(select id_permesso,id_tipo_anagrafica,to_date('01/01/2009','dd/mm/yyyy')
 from pbandi_d_permesso,pbandi_d_tipo_anagrafica
 where desc_breve_permesso in ('GUIASS',   
'GUIIST',   
'MENUAMM',  
'OPECON002',
'OPECON004',
'OPECON005',
'OPECON006',
'OPECON007',
'OPEREN001',
'OPEREN011',
'OPEREN012',
'OPEREN015',
'OPEREN016',
'OPEREN022',
'OPEREN023',
'OPEREN029',
'OPEREN032',
'OPEREN033',
'OPEREN034',
'OPEREN035',
'OPEREN039',
'OPEREN040',
'OPEROG003',
'OPEROG004',
'OPEROG011',
'OPEROG012',
'OPEROG013',
'OPEROG014',
'TRSACC001',
'TRSACC002',
'TRSBKO001',
'TRSBKO002',
'TRSBKO003',
'TRSBKO004',
'TRSBKO010',
'TRSWKS003',
'TRSWKS004',
'TRSWKS005',
'TRSNOT001',
'OPEIRR001',
'OPEIRR002',
'OPEIRR003',
'OPEIRR004',
'OPEIRR005',
'OPEREN043',
'OPEREN044',
'OPEREN047',
'OPEREN048',
'OPEAPP001',
'OPEAPP002',
'OPEAPP003',
'OPEAPP004',
'OPEAGG001',
'OPEAGG002',
'OPEAGG003',
'OPEIND001',
'OPEIND002',
'OPECRN001')
 and DESC_BREVE_TIPO_ANAGRAFICA = 'ADG-IST-MASTER');  

insert into pbandi_r_permesso_tipo_anagraf
(id_permesso,id_tipo_anagrafica,dt_inizio_validita)
(select id_permesso,id_tipo_anagrafica,to_date('01/01/2009','dd/mm/yyyy')
 from pbandi_d_permesso,pbandi_d_tipo_anagrafica
 where desc_breve_permesso in ('GUIIST',   
'MENUAMM',  
'OPECON002',
'OPECON004',
'OPECON005',
'OPECON006',
'OPECON007',
'OPEREN001',
'OPEREN011',
'OPEREN012',
'OPEREN015',
'OPEREN016',
'OPEREN022',
'OPEREN023',
'OPEREN029',
'OPEREN032',
'OPEREN033',
'OPEREN034',
'OPEREN035',
'OPEREN039',
'OPEREN040',
'OPEROG003',
'OPEROG004',
'OPEROG011',
'OPEROG012',
'OPEROG013',
'OPEROG014',
'TRSACC001',
'TRSACC002',
'TRSBKO010',
'TRSWKS003',
'TRSWKS004',
'TRSWKS005',
'TRSNOT001',
'OPEIRR001',
'OPEIRR002',
'OPEIRR003',
'OPEIRR004',
'OPEIRR005',
'OPEREN043',
'OPEREN044',
'OPEREN047',
'OPEREN048',
'OPEAPP001',
'OPEAPP002',
'OPEAPP003',
'OPEAPP004',
'OPEAGG001',
'OPEAGG002',
'OPEAGG003',
'OPEIND001',
'OPEIND002',
'OPECRN001')
 and DESC_BREVE_TIPO_ANAGRAFICA = 'ADG-ISTRUTTORE');  

insert into pbandi_r_permesso_tipo_anagraf
(id_permesso,id_tipo_anagrafica,dt_inizio_validita)
(select id_permesso,id_tipo_anagrafica,to_date('01/01/2009','dd/mm/yyyy')
 from pbandi_d_permesso,pbandi_d_tipo_anagrafica
 where desc_breve_permesso in ('GUIASS',   
'GUIIST',   
'MENUAMM',  
'OPECON002',
'OPECON004',
'OPECON005',
'OPECON006',
'OPECON007',
'OPEREN001',
'OPEREN011',
'OPEREN012',
'OPEREN015',
'OPEREN016',
'OPEREN022',
'OPEREN023',
'OPEREN029',
'OPEREN032',
'OPEREN033',
'OPEREN034',
'OPEREN035',
'OPEREN039',
'OPEREN040',
'OPEROG003',
'OPEROG004',
'OPEROG011',
'OPEROG012',
'OPEROG013',
'OPEROG014',
'TRSACC001',
'TRSACC002',
'TRSBKO001',
'TRSBKO002',
'TRSBKO003',
'TRSBKO004',
'TRSBKO010',
'TRSWKS003',
'TRSWKS004',
'TRSWKS005',
'TRSNOT001',
'OPEIRR001',
'OPEIRR002',
'OPEIRR003',
'OPEIRR004',
'OPEIRR005',
'OPEREN043',
'OPEREN044',
'OPEREN047',
'OPEREN048',
'OPEAPP001',
'OPEAPP002',
'OPEAPP003',
'OPEAPP004',
'OPEAGG001',
'OPEAGG002',
'OPEAGG003',
'OPEIND001',
'OPEIND002',
'OPECRN001')
 and DESC_BREVE_TIPO_ANAGRAFICA = 'OI-IST-MASTER'); 

insert into pbandi_r_permesso_tipo_anagraf
(id_permesso,id_tipo_anagrafica,dt_inizio_validita)
(select id_permesso,id_tipo_anagrafica,to_date('01/01/2009','dd/mm/yyyy')
 from pbandi_d_permesso,pbandi_d_tipo_anagrafica
 where desc_breve_permesso in ('GUIIST',   
'MENUAMM',  
'OPECON005',
'OPECON006',
'OPEREN001',
'OPEREN011',
'OPEREN015',
'OPEREN029',
'OPEREN032',
'TRSACC001',
'TRSACC002',
'TRSBKO010',
'TRSWKS003',
'TRSWKS004',
'TRSWKS005',
'TRSNOT001',
'OPEIRR001',
'OPEIRR004',
'OPEREN043',
'OPEREN044',
'OPEREN048',
'OPEAPP001',
'OPEIND002')
 and DESC_BREVE_TIPO_ANAGRAFICA = 'OI-ISTRUTTORE');  

insert into pbandi_r_permesso_tipo_anagraf
(id_permesso,id_tipo_anagrafica,dt_inizio_validita)
(select id_permesso,id_tipo_anagrafica,to_date('01/01/2009','dd/mm/yyyy')
 from pbandi_d_permesso,pbandi_d_tipo_anagrafica
 where desc_breve_permesso in ('GUIBEN',   
'GUIIST',   
'GUICER',   
'MENUAMM',  
'MENUCERT', 
'OPECER012',
'OPECER025',
'OPECON005',
'OPECON006',
'OPEREN001',
'OPEREN011',
'OPEREN015',
'OPEREN024',
'OPEREN029',
'OPEREN032',
'OPEREN036',
'TRSACC001',
'TRSACC002',
'TRSBKO010',
'TRSWKS003',
'TRSWKS004',
'TRSWKS005',
'OPEAPP001')
 and DESC_BREVE_TIPO_ANAGRAFICA = 'ADA-OPE-MASTER');
 
insert into pbandi_r_permesso_tipo_anagraf
(id_permesso,id_tipo_anagrafica,dt_inizio_validita)
(select id_permesso,id_tipo_anagrafica,to_date('01/01/2009','dd/mm/yyyy')
 from pbandi_d_permesso,pbandi_d_tipo_anagrafica
 where desc_breve_permesso in ('GUIBEN',   
'GUIIST',   
'GUICER',   
'MENUAMM',  
'MENUCERT', 
'OPECER010',
'OPECER012',
'OPECER023',
'OPECER025',
'OPECER027',
'OPECON005',
'OPECON006',
'OPEREN001',
'OPEREN011',
'OPEREN015',
'OPEREN024',
'OPEREN029',
'OPEREN032',
'OPEREN036',
'TRSACC001',
'TRSACC002',
'TRSBKO010',
'TRSWKS003',
'TRSWKS004',
'TRSWKS005',
'OPEAPP001')
 and DESC_BREVE_TIPO_ANAGRAFICA = 'ADC-CERT'); 

insert into pbandi_r_permesso_tipo_anagraf
(id_permesso,id_tipo_anagrafica,dt_inizio_validita)
(select id_permesso,id_tipo_anagrafica,to_date('01/01/2009','dd/mm/yyyy')
 from pbandi_d_permesso,pbandi_d_tipo_anagrafica
 where desc_breve_permesso in ('GUIBEN',     
'GUIIST',     
'GUICER',     
'GUIIRR',     
'MENUAMM',    
'MENUCERT',   
'OPECER011',  
'OPECER012',  
'OPECON005',  
'OPECON006',  
'OPEREN001',  
'OPEREN011',  
'OPEREN015',  
'OPEREN024',  
'OPEREN029',  
'OPEREN032',  
'OPEREN036',  
'OPEROG012',  
'TRSACC001',  
'TRSACC002',  
'TRSBKO010',  
'TRSWKS003',  
'TRSWKS004',  
'TRSWKS005',  
'OPEIRR001',  
'OPEIRR001-1',
'OPEIRR004',  
'OPEIRR006',  
'OPEIRR007',  
'OPEAPP001')
 and DESC_BREVE_TIPO_ANAGRAFICA = 'ADG-CERT');  
 
insert into pbandi_r_permesso_tipo_anagraf
(id_permesso,id_tipo_anagrafica,dt_inizio_validita)
(select id_permesso,id_tipo_anagrafica,to_date('01/01/2009','dd/mm/yyyy')
 from pbandi_d_permesso,pbandi_d_tipo_anagrafica
 where desc_breve_permesso in ('GUIBEN',   
'GUIIST',   
'GUICER',   
'MENUAMM',  
'MENUCERT', 
'OPECER012',
'OPECER025',
'OPECON005',
'OPECON006',
'OPEREN001',
'OPEREN011',
'OPEREN015',
'OPEREN024',
'OPEREN029',
'OPEREN032',
'OPEREN036',
'TRSACC001',
'TRSACC002',
'TRSBKO010',
'TRSWKS003',
'TRSWKS004',
'TRSWKS005',
'OPEAPP001')
 and DESC_BREVE_TIPO_ANAGRAFICA = 'GDF'); 

insert into pbandi_r_permesso_tipo_anagraf
(id_permesso,id_tipo_anagrafica,dt_inizio_validita)
(select id_permesso,id_tipo_anagrafica,to_date('01/01/2009','dd/mm/yyyy')
 from pbandi_d_permesso,pbandi_d_tipo_anagrafica
 where desc_breve_permesso in ('GUIAVV',   
'TRSACC002',
'TRSWKS003',
'TRSWKS004',
'TRSWKS005',
'TRSCSP001')
 and DESC_BREVE_TIPO_ANAGRAFICA = 'CREATOR');  

commit;

SET DEFINE OFF;
Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('W059', 'Arrotondamento sulla PRG_PIANO_COSTI per congruita'' rispetto all''ammesso');
COMMIT;

update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 127 DEL 24/12/2007'      where id_bando = 1	;
update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 183 DEL 08/03/2010'      where id_bando = 2	;
update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 498 DEL 30/07/2010'      where id_bando = 3	;
update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 125 DEL 21/12/2007'      where id_bando = 5	;
update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 44 DEL 08/04/2008'       where id_bando = 6	;
update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 230 DEL 17/09/2008'      where id_bando = 7	;
update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 110 DEL 11/04/2007'      where id_bando = 8	;
update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 123 DEL 20/12/2007'      where id_bando = 9	;
update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 164 DEL 10/07/2008'      where id_bando = 10;
update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 228 DEL 18/11/2008'      where id_bando = 14;
update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 37-10799 DEL 16/02/2009' where id_bando = 16;
update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 228 DEL 18/11/2008'      where id_bando = 17;
update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 72 DEL 07/04/2009'       where id_bando = 18;
update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 161 DEL 06/07/2009'      where id_bando = 19;
update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 370 DEL 16/12/2008'      where id_bando = 20;
update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 386 DEL 23/12/2009'      where id_bando = 21;
update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 158 DEL 16/06/2010'      where id_bando = 22;
update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 464 DEL 16/09/2009'      where id_bando = 23;
update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 464 DEL 16/09/2009'      where id_bando = 24;
update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 464 DEL 16/09/2009'      where id_bando = 25;
update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 464 DEL 16/09/2009'      where id_bando = 26;
update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 464 DEL 16/09/2009'      where id_bando = 27;
update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 464 DEL 16/09/2009'      where id_bando = 29;
update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 166 DEL 14/07/2009'      where id_bando = 30;
update pbandi_t_bando set DETERMINA_APPROVAZIONE = 'Determina 53 DEL 23/06/2010'       where id_bando = 32;
commit;

INSERT INTO PBANDI_R_DOC_INDEX_TIPO_ANAG (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX) VALUES (1,0,1 );
INSERT INTO PBANDI_R_DOC_INDEX_TIPO_ANAG (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX) VALUES (1,0,2 );
INSERT INTO PBANDI_R_DOC_INDEX_TIPO_ANAG (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX) VALUES (1,0,3 );
INSERT INTO PBANDI_R_DOC_INDEX_TIPO_ANAG (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX) VALUES (1,0,4 );
INSERT INTO PBANDI_R_DOC_INDEX_TIPO_ANAG (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX) VALUES (1,0,5 );
INSERT INTO PBANDI_R_DOC_INDEX_TIPO_ANAG (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX) VALUES (1,0,6 );
INSERT INTO PBANDI_R_DOC_INDEX_TIPO_ANAG (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX) VALUES (1,0,8 );
INSERT INTO PBANDI_R_DOC_INDEX_TIPO_ANAG (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX) VALUES (1,0,7 );
INSERT INTO PBANDI_R_DOC_INDEX_TIPO_ANAG (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX) VALUES (1,0,9 );
INSERT INTO PBANDI_R_DOC_INDEX_TIPO_ANAG (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX) VALUES (1,0,10);
INSERT INTO PBANDI_R_DOC_INDEX_TIPO_ANAG (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX) VALUES (1,0,13);
INSERT INTO PBANDI_R_DOC_INDEX_TIPO_ANAG (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX) VALUES (1,0,15);

INSERT INTO PBANDI_R_DOC_INDEX_TIPO_ANAG (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX) VALUES (3,0,10);
INSERT INTO PBANDI_R_DOC_INDEX_TIPO_ANAG (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX) VALUES (3,0,14);

INSERT INTO PBANDI_R_DOC_INDEX_TIPO_ANAG (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX) VALUES (5,0,10);
INSERT INTO PBANDI_R_DOC_INDEX_TIPO_ANAG (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX) VALUES (5,0,14);

INSERT INTO PBANDI_R_DOC_INDEX_TIPO_ANAG (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX) VALUES (14,0,10);
INSERT INTO PBANDI_R_DOC_INDEX_TIPO_ANAG (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX) VALUES (14,0,14);

INSERT INTO PBANDI_R_DOC_INDEX_TIPO_ANAG (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX) VALUES (10,0,3 );
INSERT INTO PBANDI_R_DOC_INDEX_TIPO_ANAG (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX) VALUES (10,0,5 );
INSERT INTO PBANDI_R_DOC_INDEX_TIPO_ANAG (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX) VALUES (10,0,10);
INSERT INTO PBANDI_R_DOC_INDEX_TIPO_ANAG (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX) VALUES (10,0,11);
INSERT INTO PBANDI_R_DOC_INDEX_TIPO_ANAG (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX) VALUES (10,0,14);
commit;

insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
(ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
(select 10,PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS
 from PBANDI_R_BANDO_LINEA_INTERVENT);

insert into PBANDI_R_REGOLA_BANDO_LINEA
(ID_REGOLA, PROGR_BANDO_LINEA_INTERVENTO)
(select ID_REGOLA, PROGR_BANDO_LINEA_INTERVENTO
 from pbandi_c_regola,PBANDI_R_BANDO_LINEA_INTERVENT
 where DESC_BREVE_REGOLA = 'BR31'
 and PROGR_BANDO_LINEA_INTERVENTO in (8, 40, 45, 46));

insert into PBANDI_R_REGOLA_BANDO_LINEA
(ID_REGOLA, PROGR_BANDO_LINEA_INTERVENTO)
(select ID_REGOLA, PROGR_BANDO_LINEA_INTERVENTO
 from pbandi_c_regola,PBANDI_R_BANDO_LINEA_INTERVENT
 where DESC_BREVE_REGOLA = 'BR33'
 and PROGR_BANDO_LINEA_INTERVENTO in (8, 40, 45));
 
 insert into PBANDI_R_REGOLA_BANDO_LINEA
(ID_REGOLA, PROGR_BANDO_LINEA_INTERVENTO)
(select ID_REGOLA, PROGR_BANDO_LINEA_INTERVENTO
 from pbandi_c_regola,PBANDI_R_BANDO_LINEA_INTERVENT
 where DESC_BREVE_REGOLA = 'BR34');

insert into PBANDI_R_REGOLA_BANDO_LINEA
(ID_REGOLA, PROGR_BANDO_LINEA_INTERVENTO)
(select ID_REGOLA, PROGR_BANDO_LINEA_INTERVENTO
 from pbandi_c_regola,PBANDI_R_BANDO_LINEA_INTERVENT
 where DESC_BREVE_REGOLA = 'BR35'
 and PROGR_BANDO_LINEA_INTERVENTO not in (34,35,36,37,38,39,40,45)); 
 
commit; 

delete PBANDI_C_MODELLO;

INSERT INTO PBANDI_C_MODELLO (ID_MODELLO, NOME_MODELLO, VERSIONE_MODELLO,CODICE_MODELLO, CODICE_MODULO) VALUES (1,'checklistAiutiDocumentale',     '1','checklistAiutiDocumentale','checklistAiutiDocumentaleV1' );
INSERT INTO PBANDI_C_MODELLO (ID_MODELLO, NOME_MODELLO, VERSIONE_MODELLO,CODICE_MODELLO, CODICE_MODULO) VALUES (2,'checklistATServiziDocumentale', '1','checklistATServiziDocumentale','checklistATServiziDocumentaleV1' );
INSERT INTO PBANDI_C_MODELLO (ID_MODELLO, NOME_MODELLO, VERSIONE_MODELLO,CODICE_MODELLO, CODICE_MODULO) VALUES (3,'checklistIncubatoriDocumentale','1','checklistIncubatoriDocumentale','checklistIncubatoriDocumentaleV1' );
INSERT INTO PBANDI_C_MODELLO (ID_MODELLO, NOME_MODELLO, VERSIONE_MODELLO,CODICE_MODELLO, CODICE_MODULO) VALUES (4,'checklistOOPPDocumentale',      '1','checklistOOPPDocumentale','checklistOOPPDocumentaleV1' );
INSERT INTO PBANDI_C_MODELLO (ID_MODELLO, NOME_MODELLO, VERSIONE_MODELLO,CODICE_MODELLO, CODICE_MODULO) VALUES (5,'checklistPOR-FSEDocumentale',   '1','checklistPOR-FSEDocumentale','checklistPOR-FSEDocumentaleV1' );
INSERT INTO PBANDI_C_MODELLO (ID_MODELLO, NOME_MODELLO, VERSIONE_MODELLO,CODICE_MODELLO, CODICE_MODULO) VALUES (6,'checklistAiutiInLoco',          '1','checklistAiutiInLoco'    ,'checklistAiutiInLocoV1');
INSERT INTO PBANDI_C_MODELLO (ID_MODELLO, NOME_MODELLO, VERSIONE_MODELLO,CODICE_MODELLO, CODICE_MODULO) VALUES (7,'checklistATServiziInLoco',      '1','checklistATServiziInLoco'    ,'checklistATServiziInLocoV1');
INSERT INTO PBANDI_C_MODELLO (ID_MODELLO, NOME_MODELLO, VERSIONE_MODELLO,CODICE_MODELLO, CODICE_MODULO) VALUES (8,'checklistIncubatoriInLoco',     '1','checklistIncubatoriInLoco'    ,'checklistIncubatoriInLocoV1');
INSERT INTO PBANDI_C_MODELLO (ID_MODELLO, NOME_MODELLO, VERSIONE_MODELLO,CODICE_MODELLO, CODICE_MODULO) VALUES (9,'checklistOOPPInLoco',           '1','checklistOOPPInLoco'    ,'checklistOOPPInLocoV1');
INSERT INTO PBANDI_C_MODELLO (ID_MODELLO, NOME_MODELLO, VERSIONE_MODELLO,CODICE_MODELLO, CODICE_MODULO) VALUES (10,'checklistPOR-FSEInloco',        '1','checklistPOR-FSEInloco'    ,'checklistPOR-FSEInlocoV1');
commit;


update PBANDI_R_TP_DOC_IND_BAN_LI_INT
set id_modello = 1
where ID_TIPO_DOCUMENTO_INDEX = 2 
and  PROGR_BANDO_LINEA_INTERVENTO in (1,2,3,4,5,6,7,8,9,10,11,12,23,26,27,30,32,33,40,41,45,47);

update PBANDI_R_TP_DOC_IND_BAN_LI_INT
set id_modello = 6
where ID_TIPO_DOCUMENTO_INDEX = 10 
and  PROGR_BANDO_LINEA_INTERVENTO in (1,2,3,4,5,6,7,8,9,10,11,12,23,26,27,30,32,33,40,41,45,47);

update PBANDI_R_TP_DOC_IND_BAN_LI_INT
set id_modello = (select id_modello from pbandi_c_modello where NOME_MODELLO = 'checklistPOR-FSEDocumentale')
where ID_TIPO_DOCUMENTO_INDEX = 2 
AND   PROGR_BANDO_LINEA_INTERVENTO in (34,35);

update PBANDI_R_TP_DOC_IND_BAN_LI_INT
set id_modello = (select id_modello from pbandi_c_modello where NOME_MODELLO = 'checklistPOR-FSEInloco')
where ID_TIPO_DOCUMENTO_INDEX = 10 
AND   PROGR_BANDO_LINEA_INTERVENTO in (34,35);

update PBANDI_R_TP_DOC_IND_BAN_LI_INT
set id_modello = (select id_modello from pbandi_c_modello where NOME_MODELLO = 'checklistOOPPDocumentale')
where ID_TIPO_DOCUMENTO_INDEX = 2 
AND   PROGR_BANDO_LINEA_INTERVENTO in (24,28,29);

update PBANDI_R_TP_DOC_IND_BAN_LI_INT
set id_modello = (select id_modello from pbandi_c_modello where NOME_MODELLO = 'checklistOOPPInLoco')
where ID_TIPO_DOCUMENTO_INDEX = 10 
AND   PROGR_BANDO_LINEA_INTERVENTO in (24,28,29);

update PBANDI_R_TP_DOC_IND_BAN_LI_INT
set id_modello = (select id_modello from pbandi_c_modello where NOME_MODELLO = 'checklistIncubatoriDocumentale')
where ID_TIPO_DOCUMENTO_INDEX = 2 
AND   PROGR_BANDO_LINEA_INTERVENTO in (36,37,38,39);

update PBANDI_R_TP_DOC_IND_BAN_LI_INT
set id_modello = (select id_modello from pbandi_c_modello where NOME_MODELLO = 'checklistIncubatoriInLoco')
where ID_TIPO_DOCUMENTO_INDEX = 10 
AND   PROGR_BANDO_LINEA_INTERVENTO in (36,37,38,39);

update PBANDI_R_TP_DOC_IND_BAN_LI_INT
set id_modello = (select id_modello from pbandi_c_modello where NOME_MODELLO = 'checklistATServiziDocumentale')
where ID_TIPO_DOCUMENTO_INDEX = 2
AND   PROGR_BANDO_LINEA_INTERVENTO in (46,13,15,16,14,25,18,17,19,20,21,22,44);

update PBANDI_R_TP_DOC_IND_BAN_LI_INT
set id_modello = (select id_modello from pbandi_c_modello where NOME_MODELLO = 'checklistATServiziInLoco')
where ID_TIPO_DOCUMENTO_INDEX = 10
AND   PROGR_BANDO_LINEA_INTERVENTO in (46,13,15,16,14,25,18,17,19,20,21,22,44);
 
INSERT INTO PBANDI_D_PERMESSO 
(ID_PERMESSO, DESC_BREVE_PERMESSO, DESC_PERMESSO,DT_INIZIO_VALIDITA) 
VALUES 
(114,'OPEREN047D','Modifica Checklist Documentali',to_date('01/01/2009','dd/mm/yyyy'));

INSERT INTO PBANDI_D_PERMESSO 
(ID_PERMESSO, DESC_BREVE_PERMESSO, DESC_PERMESSO,DT_INIZIO_VALIDITA) 
VALUES 
(115,'OPEREN047D','Modifica Checklist Documentali',to_date('01/01/2009','dd/mm/yyyy'));

INSERT INTO PBANDI_R_PERMESSO_TIPO_ANAGRAF 
(ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA) 
VALUES 
(114,2,to_date('01/01/2009','dd/mm/yyyy'));

INSERT INTO PBANDI_R_PERMESSO_TIPO_ANAGRAF 
(ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA) 
VALUES 
(114,3,to_date('01/01/2009','dd/mm/yyyy'));

INSERT INTO PBANDI_R_PERMESSO_TIPO_ANAGRAF 
(ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA) 
VALUES 
(114,4,to_date('01/01/2009','dd/mm/yyyy'));

INSERT INTO PBANDI_R_PERMESSO_TIPO_ANAGRAF 
(ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA) 
VALUES 
(115,2,to_date('01/01/2009','dd/mm/yyyy'));

INSERT INTO PBANDI_R_PERMESSO_TIPO_ANAGRAF 
(ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA) 
VALUES 
(115,3,to_date('01/01/2009','dd/mm/yyyy'));

INSERT INTO PBANDI_R_PERMESSO_TIPO_ANAGRAF 
(ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA) 
VALUES 
(115,4,to_date('01/01/2009','dd/mm/yyyy'));

INSERT INTO PBANDI_R_PERMESSO_TIPO_ANAGRAF 
(ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA) 
VALUES 
(115,5,to_date('01/01/2009','dd/mm/yyyy'));

INSERT INTO PBANDI_R_PERMESSO_TIPO_ANAGRAF 
(ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA) 
VALUES 
(102,5,to_date('01/01/2009','dd/mm/yyyy')); 
 
insert into PBANDI_C_ENTITA
(ID_ENTITA, NOME_ENTITA, FLAG_DA_TRACCIARE) 
(select seq_PBANDI_C_ENTITA.nextval,tabs.TABLE_NAME,'S'
from all_tables tabs
where tabs.OWNER = (select decode(instr(user,'_RW'),0,user,replace(user,'_RW',null)) from dual)
and tabs.TABLE_NAME like 'PBANDI_%'
and not exists (select 'x' from pbandi_c_entita where tabs.TABLE_NAME = NOME_ENTITA));

update PBANDI_T_RETTIFICA_DI_SPESA
set IMPORTO_RETTIFICA = IMPORTO_RETTIFICA*(-1);

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

