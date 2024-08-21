/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

alter table pbandi_t_provvedimento
modify id_tipo_provvedimento null;

alter table pbandi_t_provvedimento
modify id_ente_competenza null;

alter table pbandi_t_provvedimento
modify numero_provvedimento null;

alter table pbandi_t_provvedimento
modify anno_provvedimento null;

alter table pbandi_t_atto_liquidazione
modify dt_emissione_atto null;

alter table pbandi_t_atto_liquidazione
add dt_rifiuto_ragioneria date;

CREATE TABLE pbandi_t_carica_mass_doc_spesa
(
	id_carica_mass_doc_spesa  NUMBER  NOT NULL ,
	file_xml              XMLType  NOT NULL ,
	nome_file             VARCHAR2(255)  NOT NULL ,
	dt_inserimento        DATE  NOT NULL ,
	flag_validato         CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_validato_01 CHECK (flag_validato in ('V','N','P')),
	flag_caricato         CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_64 CHECK (flag_caricato in ('S','N')),
	dt_validazione        DATE  NULL ,
	dt_elaborazione       DATE  NULL ,
	riga_errore           VARCHAR2(255)  NULL ,
	id_progetto           NUMBER(8)  NULL ,
	id_soggetto_beneficiario  NUMBER(8)  NULL ,
	id_utente             NUMBER(8)  NOT NULL ,
	codice_errore         VARCHAR2(10)  NULL ,
	errore_oracle		  VARCHAR2(4000)
)
TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_carica_mass_doc_sp ON pbandi_t_carica_mass_doc_spesa
(id_carica_mass_doc_spesa  ASC)
	TABLESPACE pbandi_small_idx;



ALTER TABLE pbandi_t_carica_mass_doc_spesa
	ADD CONSTRAINT  PK_pbandi_t_carica_mass_doc_sp PRIMARY KEY (id_carica_mass_doc_spesa);



CREATE TABLE pbandi_t_scarti_carica_mass_ds
(
	id_scarti_carica_mass_ds  NUMBER  NOT NULL ,
	numero_documento      VARCHAR2(100)  NULL ,
	dt_documento          DATE  NULL ,
	desc_tipo_doc_spesa   VARCHAR2(100)  NULL ,
	codice_fiscale_fornitore  VARCHAR2(32)  NULL ,
	riga_errore           VARCHAR2(255)  NULL ,
	codice_errore         VARCHAR2(10)  NOT NULL ,
	id_carica_mass_doc_spesa  NUMBER  NOT NULL 
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_scarti_carica_mass ON pbandi_t_scarti_carica_mass_ds
(id_scarti_carica_mass_ds  ASC)
	TABLESPACE pbandi_small_idx;



ALTER TABLE pbandi_t_scarti_carica_mass_ds
	ADD CONSTRAINT  PK_pbandi_t_scarti_carica_mass PRIMARY KEY (id_scarti_carica_mass_ds);



ALTER TABLE pbandi_t_carica_mass_doc_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_31 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_t_carica_mass_doc_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_SOGGETTO_18 FOREIGN KEY (id_soggetto_beneficiario) REFERENCES pbandi_t_soggetto(id_soggetto));



ALTER TABLE pbandi_t_carica_mass_doc_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_241 FOREIGN KEY (id_utente) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_carica_mass_doc_spesa
	ADD (CONSTRAINT  FK_PBANDI_D_ERRORE_BATCH_02 FOREIGN KEY (codice_errore) REFERENCES pbandi_d_errore_batch(codice_errore));



ALTER TABLE pbandi_t_scarti_carica_mass_ds
	ADD (CONSTRAINT  FK_PBANDI_D_ERRORE_BATCH_03 FOREIGN KEY (codice_errore) REFERENCES pbandi_d_errore_batch(codice_errore));



ALTER TABLE pbandi_t_scarti_carica_mass_ds
	ADD (CONSTRAINT  FK_PBANDI_T_CARIC_MA_DOC_SP_02 FOREIGN KEY (id_carica_mass_doc_spesa) REFERENCES pbandi_t_carica_mass_doc_spesa(id_carica_mass_doc_spesa));
	
alter TABLE pbandi_t_documento_di_spesa
add id_carica_mass_doc_spesa  NUMBER  NULL ;

ALTER TABLE pbandi_t_documento_di_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_CARIC_MA_DOC_SP_01 FOREIGN KEY (id_carica_mass_doc_spesa) REFERENCES pbandi_t_carica_mass_doc_spesa(id_carica_mass_doc_spesa));

create sequence seq_pbandi_t_carica_mass_doc_s start with 1 nocache;

create sequence seq_pbandi_t_scarti_carica_mas start with 1 nocache;	

alter table pbandi_w_impegni
add Flag_PF Varchar2(1);

alter table pbandi_w_impegni
add Cod_Fiscale Varchar2(16);

alter table pbandi_w_impegni
add P_Iva Varchar2(11);

alter table PBANDI_W_ATTI_LIQUIDAZIONE_DL
add AnnoProvImp Varchar2(4);

alter table PBANDI_W_ATTI_LIQUIDAZIONE_DL
add NroProvImp Varchar2(5);

alter table PBANDI_W_ATTI_LIQUIDAZIONE_DL
add TipoProvImp Varchar2(2);

alter table PBANDI_W_ATTI_LIQUIDAZIONE_DL
add DirezioneProvIMP Varchar2(4);

ALTER TABLE PBANDI_T_PROVVEDIMENTO DROP CONSTRAINTS AK1_PBANDI_T_PROVVEDIMENTO;

DROP INDEX AK1_PBANDI_T_PROVVEDIMENTO;

CREATE UNIQUE INDEX AK1_PBANDI_T_PROVVEDIMENTO ON PBANDI_T_PROVVEDIMENTO
(ANNO_PROVVEDIMENTO, NUMERO_PROVVEDIMENTO, ID_TIPO_PROVVEDIMENTO,ID_ENTE_COMPETENZA)
TABLESPACE PBANDI_SMALL_IDX;

alter TABLE pbandi_d_soggetto_finanziatore
add id_tipo_fondo         NUMBER(3)  NULL;

ALTER TABLE pbandi_d_soggetto_finanziatore
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_FONDO_02 FOREIGN KEY (id_tipo_fondo) REFERENCES pbandi_d_tipo_fondo(id_tipo_fondo));
	
alter table pbandi_t_atto_liquidazione
modify id_dati_pagamento_atto null;

alter table pbandi_t_atto_liquidazione
modify nome_dirigente_liquidatore varchar2(50);

alter table PBANDI_T_ESTREMI_BANCARI 
modify iban varchar2(40);

alter table PBANDI_T_CSP_SOGGETTO
modify iban varchar2(40);

alter table pbandi_t_atto_liquidazione
modify importo_atto null;

alter table pbandi_r_liquidazione
add anno_esercizio number(4) NOT NULL;

CREATE TABLE pbandi_d_stato_liquidazione
(
	id_stato_liquidazione  NUMBER(3)  NOT NULL ,
	desc_breve_stato_liquidazione  VARCHAR2(2)  NOT NULL ,
	desc_stato_liquidazione  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE pbandi_small_tbl;



CREATE UNIQUE INDEX PK_pbandi_d_stato_liquidazione ON pbandi_d_stato_liquidazione
(id_stato_liquidazione  ASC)
	TABLESPACE pbandi_small_idx;



ALTER TABLE pbandi_d_stato_liquidazione
	ADD CONSTRAINT  PK_pbandi_d_stato_liquidazione PRIMARY KEY (id_stato_liquidazione);
	
	
alter table pbandi_r_liquidazione
add id_stato_liquidazione  NUMBER(3)  NOT NULL ;

ALTER TABLE pbandi_r_liquidazione
	ADD (CONSTRAINT  FK_PBANDI_D_ST_LIQUIDAZIONE_01 FOREIGN KEY (id_stato_liquidazione) REFERENCES pbandi_d_stato_liquidazione(id_stato_liquidazione));
	
CREATE TABLE pbandi_r_det_prop_cer_mand_qui
(
	id_dett_proposta_certif  NUMBER(8)  NOT NULL ,
	id_mandato_quietanzato  NUMBER(8)  NOT NULL ,
	importo_quietanzato   NUMBER(15,2)  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE pbandi_small_tbl;



CREATE UNIQUE INDEX PK_pbandi_r_det_prop_cer_mand_ ON pbandi_r_det_prop_cer_mand_qui
(id_dett_proposta_certif  ASC,id_mandato_quietanzato  ASC)
	TABLESPACE pbandi_small_idx;



ALTER TABLE pbandi_r_det_prop_cer_mand_qui
	ADD CONSTRAINT  PK_pbandi_r_det_prop_cer_mand_ PRIMARY KEY (id_dett_proposta_certif,id_mandato_quietanzato);



ALTER TABLE pbandi_r_det_prop_cer_mand_qui
	ADD (CONSTRAINT  FK_PBANDI_T_DETT_PROP_CERT_09 FOREIGN KEY (id_dett_proposta_certif) REFERENCES pbandi_t_dett_proposta_certif(id_dett_proposta_certif));



ALTER TABLE pbandi_r_det_prop_cer_mand_qui
	ADD (CONSTRAINT  FK_PBANDI_T_MANDATO_QUIETAN_01 FOREIGN KEY (id_mandato_quietanzato) REFERENCES pbandi_t_mandato_quietanzato(id_mandato_quietanzato));



ALTER TABLE pbandi_r_det_prop_cer_mand_qui
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_242 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_det_prop_cer_mand_qui
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_243 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));

	
alter TABLE pbandi_d_modalita_erogazione
add flag_bilancio         CHAR(1)  NULL  CONSTRAINT  ck_flag_65 CHECK (flag_bilancio in ('S','N'));	

CREATE INDEX IE1_pbandi_r_soggetto_progetto ON PBANDI_R_SOGGETTO_PROGETTO
(ID_PROGETTO)
LOGGING
compute statistics TABLESPACE PBANDI_SMALL_IDX
NOPARALLEL;


CREATE INDEX IE1_pbandi_t_preview_dett_prop ON PBANDI_T_PREVIEW_DETT_PROP_CER
(ID_PROPOSTA_CERTIFICAZ)
LOGGING
compute statistics TABLESPACE PBANDI_SMALL_IDX
NOPARALLEL;


CREATE INDEX IE1_pbandi_r_prog_sogg_finanzi ON PBANDI_R_PROG_SOGG_FINANZIAT
(ID_PROGETTO)
LOGGING
compute statistics TABLESPACE PBANDI_SMALL_IDX
NOPARALLEL;


CREATE INDEX IE1_pbandi_t_erogazione ON PBANDI_T_EROGAZIONE
(ID_PROGETTO)
LOGGING
compute statistics TABLESPACE PBANDI_SMALL_IDX
NOPARALLEL;


CREATE INDEX IE1_pbandi_t_conto_economico ON PBANDI_T_CONTO_ECONOMICO
(ID_DOMANDA)
LOGGING
compute statistics TABLESPACE PBANDI_SMALL_IDX
NOPARALLEL;



CREATE INDEX IE1_PBANDI_W_ATTI_LIQUIDAZI_DL ON PBANDI_W_ATTI_LIQUIDAZIONE_DL
(AnnoProvv ,NroProv  ,Direzione  )
COMPUTE STATISTICS
TABLESPACE pbandi_small_idx;


CREATE INDEX IE1_PBANDI_W_ATTI_LIQUIDAZI_DM ON PBANDI_W_ATTI_LIQUIDAZIONE_DM
(AnnoEser  ,Nliq  )
COMPUTE STATISTICS
TABLESPACE pbandi_small_idx;
	
	