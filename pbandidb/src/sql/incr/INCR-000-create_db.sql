/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

alter table pbandi_t_dichiarazione_spesa
add dt_chiusura_validazione date;

alter table pbandi_t_dichiarazione_spesa
add note_chiusura_validazione varchar2(4000);

alter table PBANDI_T_QUOTA_PARTE_DOC_SPESA
drop (IMPORTO_QUOTA_PARTE_QUIETANZ);

alter table PBANDI_T_QUOTA_PARTE_DOC_SPESA
drop (IMPORTO_VALIDATO);

CREATE TABLE pbandi_d_tipo_periodo
(
	id_tipo_periodo       NUMBER(3)  NOT NULL ,
	desc_breve_tipo_periodo  VARCHAR2(20)  NOT NULL ,
	desc_tipo_periodo     VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_periodo ON pbandi_d_tipo_periodo
(id_tipo_periodo  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_periodo
	ADD CONSTRAINT  PK_pbandi_d_tipo_periodo PRIMARY KEY (id_tipo_periodo);



CREATE TABLE pbandi_t_periodo
(
	id_periodo            NUMBER(8)  NOT NULL ,
	id_tipo_periodo       NUMBER(3)  NOT NULL ,
	desc_periodo          VARCHAR2(20)  NULL ,
	desc_periodo_visualizzata  VARCHAR2(255)  NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_periodo ON pbandi_t_periodo
(id_periodo  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_periodo
	ADD CONSTRAINT  PK_pbandi_t_periodo PRIMARY KEY (id_periodo);



ALTER TABLE pbandi_t_periodo
	ADD (CONSTRAINT  FK_PBANDI_D_PERIODO_01 FOREIGN KEY (id_tipo_periodo) REFERENCES pbandi_d_tipo_periodo(id_tipo_periodo));



ALTER TABLE pbandi_t_periodo
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_114 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_periodo
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_115 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);
	
ALTER TABLE pbandi_t_bando
ADD id_periodo            NUMBER(8)  NULL;

ALTER TABLE pbandi_t_bando
	ADD (CONSTRAINT  FK_PBANDI_T_PERIODO_01 FOREIGN KEY (id_periodo) REFERENCES pbandi_t_periodo(id_periodo) ON DELETE SET NULL);

ALTER TABLE PBANDI_D_TIPO_ANAGRAFICA
drop (RUOLO_IRIDE); 

ALTER TABLE PBANDI_D_TIPO_BENEFICIARIO
drop (RUOLO_IRIDE);

alter table pbandi_t_tracciamento
drop (valore_precedente);

alter table pbandi_t_tracciamento
drop (valore_successivo);

alter table pbandi_t_tracciamento
drop (id_entita);

alter table pbandi_t_tracciamento
drop (id_attributo);

alter table pbandi_t_tracciamento
drop (id_target);


create sequence seq_pbandi_t_trac_entita start with 1 nocache;

CREATE TABLE pbandi_t_tracciamento_entita
(
	id_tracciamento_entita  NUMBER  NOT NULL ,
	desc_attivita         VARCHAR2(50)  NOT NULL ,
	valore_precedente     VARCHAR2(255)  NULL ,
	valore_successivo     VARCHAR2(255)  NULL ,
	id_tracciamento       NUMBER  NOT NULL ,
	id_entita             NUMBER(3)  NOT NULL ,
	id_attributo          NUMBER(5)  NULL ,
	target		          VARCHAR2(50) NOT NULL
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_tracciamento_entit ON pbandi_t_tracciamento_entita
(id_tracciamento_entita  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_t_tracciamento_entita
	ADD CONSTRAINT  PK_pbandi_t_tracciamento_entit PRIMARY KEY (id_tracciamento_entita);



ALTER TABLE pbandi_t_tracciamento_entita
	ADD (CONSTRAINT  FK_PBANDI_T_TRACCIAMENTO_01 FOREIGN KEY (id_tracciamento) REFERENCES pbandi_t_tracciamento(id_tracciamento));



ALTER TABLE pbandi_t_tracciamento_entita
	ADD (CONSTRAINT  FK_PBANDI_C_ENTITA_01 FOREIGN KEY (id_entita) REFERENCES pbandi_c_entita(id_entita));



ALTER TABLE pbandi_t_tracciamento_entita
	ADD (CONSTRAINT  FK_PBANDI_C_ATTRIBUTO_01 FOREIGN KEY (id_attributo) REFERENCES pbandi_c_attributo(id_attributo) ON DELETE SET NULL);
	
create sequence seq_PBANDI_C_ENTITA start with 2 nocache;
create sequence seq_PBANDI_C_ATTRIBUTO start with 1 nocache;
	
alter table pbandi_c_attributo
add key_position_id number(1);

-----------------------------------------
ALTER TABLE PBANDI_R_PAGAMENTO_DOC_SPESA ADD ID_PROGETTO NUMBER(8);

ALTER TABLE PBANDI_R_PAGAMENTO_DOC_SPESA
  ADD CONSTRAINT FK_PBANDI_T_PROGETTO_09 FOREIGN KEY (ID_PROGETTO)
  REFERENCES PBANDI_T_PROGETTO (ID_PROGETTO);

begin 
  update PBANDI_R_PAGAMENTO_DOC_SPESA PDS
  set    ID_PROGETTO = (
      select id_progetto from pbandi_r_doc_spesa_progetto SP 
      where SP.ID_DOCUMENTO_DI_SPESA = PDS.ID_DOCUMENTO_DI_SPESA)
  where  PDS.ID_DOCUMENTO_DI_SPESA in (   
      select   ds.id_documento_di_spesa 
      from     pbandi_r_doc_spesa_progetto ds
      group by ds.id_documento_di_spesa
      having   count(*) = 1
  );


  -- Forzatura per mettere il primo progetto su tutte le righe  
  for c in (
      select   ds.id_documento_di_spesa, count(*) cnt
      from     pbandi_r_doc_spesa_progetto ds
      where    exists(
                 select * 
                 from PBANDI_R_PAGAMENTO_DOC_SPESA PDS 
                 where ds.id_documento_di_spesa = PDS.ID_DOCUMENTO_DI_SPESA
               )
      group by ds.id_documento_di_spesa
      having   count(*) > 1
  ) loop 
    insert into PBANDI_L_LOG_BATCH
	(ID_LOG_BATCH,ID_PROCESSO_BATCH,DT_INSERIMENTO,CODICE_ERRORE,MESSAGGIO_ERRORE)
	values
	(SEQ_PBANDI_L_LOG_BATCH.nextval,1,sysdate,'W001','documento (id='||c.id_documento_di_spesa||') con '||c.cnt||' progetti!');
    
    update PBANDI_R_PAGAMENTO_DOC_SPESA PDS
    set    ID_PROGETTO = (
        select min(id_progetto) from pbandi_r_doc_spesa_progetto SP 
        where SP.ID_DOCUMENTO_DI_SPESA = PDS.ID_DOCUMENTO_DI_SPESA)
    where  PDS.ID_DOCUMENTO_DI_SPESA = c.id_documento_di_spesa;    
  end loop;
  commit;
END;
/

ALTER TABLE PBANDI_R_PAGAMENTO_DOC_SPESA MODIFY ID_PROGETTO NOT NULL;
  

------------------------------------------------
ALTER TABLE PBANDI_T_DOCUMENTO_DI_SPESA ADD ID_STATO_DOCUMENTO_SPESA NUMBER(3);

ALTER TABLE pbandi_t_documento_di_spesa
ADD (CONSTRAINT  FK_PBANDI_D_STATO_DOC_SPESA_02 FOREIGN KEY (id_stato_documento_spesa) 
REFERENCES pbandi_d_stato_documento_spesa(id_stato_documento_spesa));
 
  
  update PBANDI_T_DOCUMENTO_DI_SPESA DOC
  set    ID_STATO_DOCUMENTO_SPESA = 
         (
             select max(ID_STATO_DOCUMENTO_SPESA) 
			 from PBANDI_R_DOC_SPESA_PROGETTO RDP 
			 where RDP.ID_DOCUMENTO_DI_SPESA = DOC.ID_DOCUMENTO_DI_SPESA
         );
  commit;
  
ALTER TABLE PBANDI_T_DOCUMENTO_DI_SPESA MODIFY ID_STATO_DOCUMENTO_SPESA NOT NULL;


-- Drop columns 
alter table PBANDI_R_DOC_SPESA_PROGETTO drop column ID_STATO_DOCUMENTO_SPESA;
