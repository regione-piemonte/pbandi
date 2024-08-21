/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE TABLE pbandi_d_tipologia_appalto
(
	id_tipologia_appalto  NUMBER(3)  NOT NULL ,
	desc_tipologia_appalto  VARCHAR2(100)  NOT NULL ,
	desc_breve_appalto    VARCHAR2(5)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipologia_appalto ON pbandi_d_tipologia_appalto
(id_tipologia_appalto  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipologia_appalto
	ADD CONSTRAINT  PK_pbandi_d_tipologia_appalto PRIMARY KEY (id_tipologia_appalto);
	
alter TABLE pbandi_t_appalto	
add intervento_pisu       VARCHAR2(100);

alter TABLE pbandi_t_appalto	
add impresa_appaltatrice  VARCHAR2(255);

alter TABLE pbandi_t_appalto	
add id_tipologia_appalto  NUMBER(3)  NULL ;

ALTER TABLE pbandi_t_appalto
	ADD (CONSTRAINT  FK_PBANDI_D_TIPOLOG_APPALTO_01 FOREIGN KEY (id_tipologia_appalto) REFERENCES pbandi_d_tipologia_appalto(id_tipologia_appalto));
	
alter TABLE pbandi_t_procedura_aggiudicaz	
add iva                   NUMBER(13,2);

alter TABLE pbandi_t_procedura_aggiudicaz	
add cig_proc_agg          VARCHAR2(10);

alter TABLE pbandi_t_conto_economico
add contratti_da_stipulare  VARCHAR2(4000);

alter TABLE pbandi_t_dett_proposta_certif
add flag_correzione_cn    CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_63 CHECK (flag_correzione_cn in ('S','N'));

alter table pbandi_r_bl_tipo_doc_sez_mod
drop constraints PK_pbandi_r_bl_tipo_doc_sez_mo;

drop index PK_pbandi_r_bl_tipo_doc_sez_mo;

alter table pbandi_r_bl_tipo_doc_sez_mod
modify num_ordinamento_micro_sezione not null;

CREATE UNIQUE INDEX PK_pbandi_r_bl_tipo_doc_sez_mo ON pbandi_r_bl_tipo_doc_sez_mod
(id_tipo_documento_index  ASC,progr_bando_linea_intervento  ASC,id_micro_sezione_modulo  ASC,num_ordinamento_micro_sezione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;

ALTER TABLE pbandi_r_bl_tipo_doc_sez_mod
	ADD CONSTRAINT  PK_pbandi_r_bl_tipo_doc_sez_mo PRIMARY KEY (id_tipo_documento_index,progr_bando_linea_intervento,id_micro_sezione_modulo,num_ordinamento_micro_sezione);

alter TABLE pbandi_t_csp_progetto
add dt_presentazione_domanda  DATE;

alter TABLE pbandi_t_csp_soggetto	
add dt_costituzione_azienda  DATE;

CREATE TABLE pbandi_d_tipo_template
(
	id_tipo_template      NUMBER(8)  NOT NULL ,
	desc_breve_tipo_template  VARCHAR2(20 BYTE)  NOT NULL ,
	desc_tipo_template    VARCHAR2(255 BYTE)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL
	LOGGING
	NOCACHE
	NOPARALLEL;



CREATE UNIQUE INDEX pk_pbandi_d_tipo_template ON pbandi_d_tipo_template
(id_tipo_template  ASC)
	TABLESPACE pbandi_small_idx;



ALTER TABLE pbandi_d_tipo_template
	ADD CONSTRAINT  pk_pbandi_d_tipo_template PRIMARY KEY (id_tipo_template);

create sequence seq_pbandi_t_template start with 1 nocache;

CREATE TABLE pbandi_t_template
(
	id_template           NUMBER(8)  NOT NULL ,
	id_tipo_documento_index  NUMBER(3)  NOT NULL ,
	progr_bando_linea_intervento  NUMBER(8)  NOT NULL ,
	nome_template         VARCHAR2(255)  NOT NULL ,
	sez_ds_param_name     VARCHAR2(100)  NULL ,
	sez_report_param_name  VARCHAR2(100)  NULL ,
	sub_ds_param_name     VARCHAR2(100)  NULL ,
	sub_report_param_name  VARCHAR2(100)  NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	id_tipo_template      NUMBER(8)  NOT NULL ,
	id_macro_sezione_modulo  NUMBER(3)  NULL ,
	jasperblob            BLOB  NULL ,
	dt_inserimento        DATE  NOT NULL
)
TABLESPACE PBANDI_MEDIUM_TBL
LOB (jasperblob) STORE AS  ( 
	TABLESPACE PBANDI_MEDIUM_LOB);


CREATE UNIQUE INDEX PK_pbandi_t_template ON pbandi_t_template
(id_template  ASC);



ALTER TABLE pbandi_t_template
	ADD CONSTRAINT  PK_pbandi_t_template PRIMARY KEY (id_template);



ALTER TABLE pbandi_t_template
	ADD (CONSTRAINT  FK_PBANDI_C_TIPO_DOC_INDEX_06 FOREIGN KEY (id_tipo_documento_index) REFERENCES pbandi_c_tipo_documento_index(id_tipo_documento_index));



ALTER TABLE pbandi_t_template
	ADD (CONSTRAINT  FK_PBANDI_R_BANDO_LINEA_INT_17 FOREIGN KEY (progr_bando_linea_intervento) REFERENCES pbandi_r_bando_linea_intervent(progr_bando_linea_intervento));



ALTER TABLE pbandi_t_template
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_237 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_template
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_238 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_template
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_TEMPLATE_01 FOREIGN KEY (id_tipo_template) REFERENCES pbandi_d_tipo_template(id_tipo_template));



ALTER TABLE pbandi_t_template
	ADD (CONSTRAINT  FK_PBANDI_D_MACRO_SEZ_MODUL_02 FOREIGN KEY (id_macro_sezione_modulo) REFERENCES pbandi_d_macro_sezione_modulo(id_macro_sezione_modulo));
	
create sequence seq_pbandi_r_bl_tipo_doc_sez_m start with 9367 nocache;

alter table pbandi_r_bl_tipo_doc_sez_mod
add progr_bl_tipo_doc_sez_mod number;


alter table pbandi_r_bl_tipo_doc_sez_mod
modify progr_bl_tipo_doc_sez_mod not null;

CREATE TABLE pbandi_r_bl_tipo_doc_micro_sez
(
	progr_bl_tipo_doc_sez_mod  NUMBER  NOT NULL ,
	id_micro_sezione_modulo  NUMBER(3)  NOT NULL ,
	num_ordinamento_micro_sezione  NUMBER(3)  NOT NULL 
)
	TABLESPACE pbandi_small_tbl;



CREATE UNIQUE INDEX PK_pbandi_r_bl_tipo_doc_micro ON pbandi_r_bl_tipo_doc_micro_sez
(progr_bl_tipo_doc_sez_mod  ASC,id_micro_sezione_modulo  ASC,num_ordinamento_micro_sezione)
	TABLESPACE pbandi_small_idx;



ALTER TABLE pbandi_r_bl_tipo_doc_micro_sez
	ADD CONSTRAINT  PK_pbandi_r_tipo_doc_micro_sez PRIMARY KEY (progr_bl_tipo_doc_sez_mod,id_micro_sezione_modulo,num_ordinamento_micro_sezione);



ALTER TABLE pbandi_r_bl_tipo_doc_micro_sez
	ADD (CONSTRAINT  FK_PBANDI_D_MICRO_SEZI_MOD_02 FOREIGN KEY (id_micro_sezione_modulo) REFERENCES pbandi_d_micro_sezione_modulo(id_micro_sezione_modulo));


alter table pbandi_r_bl_tipo_doc_sez_mod
drop constraints PK_pbandi_r_bl_tipo_doc_sez_mo;

drop index PK_pbandi_r_bl_tipo_doc_sez_mo;

alter table pbandi_r_bl_tipo_doc_sez_mod
drop (id_micro_sezione_modulo);

alter table pbandi_r_bl_tipo_doc_sez_mod
drop (num_ordinamento_micro_sezione);

CREATE UNIQUE INDEX PK_pbandi_r_bl_tipo_doc_sez_mo ON pbandi_r_bl_tipo_doc_sez_mod
(progr_bl_tipo_doc_sez_mod  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_bl_tipo_doc_sez_mod
	ADD CONSTRAINT  PK_pbandi_r_bl_tipo_doc_sez_mo PRIMARY KEY (progr_bl_tipo_doc_sez_mod);


ALTER TABLE pbandi_r_bl_tipo_doc_micro_sez
    ADD (CONSTRAINT  FK_PBANDI_R_BL_T_DOC_SEZ_MO_01 FOREIGN KEY (progr_bl_tipo_doc_sez_mod) REFERENCES pbandi_r_bl_tipo_doc_sez_mod(progr_bl_tipo_doc_sez_mod));

alter TABLE pbandi_r_bl_tipo_doc_sez_mod	
add REPORT_JRXML          VARCHAR2(200);

alter TABLE pbandi_r_bl_tipo_doc_sez_mod	
add TEMPLATE_JRXML        VARCHAR2(200);

alter TABLE pbandi_t_procedura_aggiudicaz	
modify cod_proc_agg null;

CREATE TABLE pbandi_c_batch_at_liquidazione
(
	anno_esercizio        NUMBER(4)  NOT NULL ,
	direzione             VARCHAR2(4)  NOT NULL ,
	flag                  CHAR(1)   DEFAULT  'N' NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;

create table PBANDI_W_ATTI_LIQUIDAZIONE_DT
(TipoRecord					Varchar2(2)  ,
AnnoAtto            Varchar2(4)  ,
NroAtto             Varchar2(5)  ,
DirAtto             Varchar2(4)  ,
SettAtto            Varchar2(2)  ,
ImportoAtto         Number(15, 2),
Descri              Varchar2(150),
Note                Varchar2(70) ,
FlFatture           Varchar2(1)  ,
FlEstrCopiaProv     Varchar2(1)  ,
FlDocgiust          Varchar2(1)  ,
FlDichiaraz         Varchar2(1)  ,
TestoAltro          Varchar2(100),
Stato               Number(1)    ,
CodBen              Number(6)    ,
RagSoc              Varchar2(140),
CodFisc             Varchar2(16) ,
PartIva             Varchar2(11) ,
Via                 Varchar2(30) ,
Cap                 Varchar2(5)  ,
Comune              Varchar2(30) ,
Prov                Varchar2(2)  ,
ProgModPag          Number(3)    ,
CodAccre            Varchar2(2)  ,
DescCodAccre        Varchar2(150),
RagSocAgg           Varchar2(140),
ViaSede             Varchar2(30) ,
CapSede             Varchar2(5)  ,
ComuneSede          Varchar2(30) ,
ProvSede            Varchar2(2)  ,
CIN                 Varchar2(1)  ,
ABI                 Varchar2(5)  ,
DescABI             Varchar2(150),
CAB                 Varchar2(5)  ,
DescCAB             Varchar2(150),
NroCC               Varchar2(13) ,
Quietanzante        Varchar2(80) ,
CodFiscQuiet        Varchar2(16) ,
IBAN                Varchar2(34) ,
BIC                 Varchar2(11) ,
Codben_cedente      Number(6)    ,
Codben_ceduto       Number(6)    ,
Progben_ceduto      Number(3)    ,
DataEmisAtto        Date         ,
DataAggAtto         Date         ,
DataScadenza        Date         ,
DataComplAtto       Date         ,
DataAnnulAtto       Date         ,
DataRicAtto         Date         ,
DataRifAtto         Date         ,
DataRichMod         Date         ,
TestoRichMod        Varchar2(150),
TipoSoggetto        Varchar2(1)  ,
TipoRitenuta        Varchar2(1)  ,
IrpNonSoggette      Number (15,2),
AliqIrpef           Number(5,2)  ,
DatoInps            Number(2)    ,
InpsAltraCassa      Varchar2(1)  ,
CodAttivita         Varchar2(2)  ,
CodAltraCassa       Varchar2(3)  ,
InpsDal             Date         ,
ImpsAl              Date         ,
RischioInail        Varchar2(2)  ,
Nelenco             Number(3)  ,
flag_online CHAR(1)   DEFAULT  'N' NOT NULL,
flagPerFis			varchar2(1),
id_atti_liquidazione_dt  NUMBER  NOT NULL     )
tablespace PBANDI_SMALL_TBL;

CREATE UNIQUE INDEX PK_PBANDI_W_ATTI_LIQUIDAZ_DT ON PBANDI_W_ATTI_LIQUIDAZIONE_DT
(id_atti_liquidazione_dt  ASC)
	TABLESPACE pbandi_small_idx;



ALTER TABLE PBANDI_W_ATTI_LIQUIDAZIONE_DT
	ADD CONSTRAINT  PK_PBANDI_W_ATTI_LIQUIDAZ_DT PRIMARY KEY (id_atti_liquidazione_dt);

create table PBANDI_W_ATTI_LIQUIDAZIONE_DL
(TipoRecord	Varchar2(2)  ,
AnnoEser    Number(4)    ,
AnnoProvv   Varchar2(4)  ,
NroProv     Varchar2(5)  ,
Direzione   Varchar2(4)  ,
NroCapitolo Number(6)    ,
NroArticolo Number(3)    ,
TipoFondo   Varchar2(2)  ,
AnnoImp     Varchar2(4)  ,
NroImp      Number(6)    ,
TrasfTipo   Varchar2(6)  ,
TrasfVoce   Varchar2(15) ,
SubImpegno  Number(6)    ,
Nliq        Number(6)    ,
NliqPrec    Number(6)    ,
AnnoBilRif  Varchar2(4)  ,
Importo     Number(15, 2),
DataAgg     Date         ,
CUPLiq      Varchar2(15) ,
CIGLiq      Varchar2(10) ,
flag_online CHAR(1)   DEFAULT  'N' NOT NULL,
id_atti_liquidazione_dl  NUMBER  NOT NULL,
id_atti_liquidazione_dt  NUMBER  NOT NULL    )
tablespace PBANDI_SMALL_TBL;

CREATE UNIQUE INDEX PK_PBANDI_W_ATTI_LIQUIDAZ_DL ON PBANDI_W_ATTI_LIQUIDAZIONE_DL
(id_atti_liquidazione_dl  ASC)
	TABLESPACE pbandi_small_idx;



ALTER TABLE PBANDI_W_ATTI_LIQUIDAZIONE_DL
	ADD CONSTRAINT  PK_PBANDI_W_ATTI_LIQUIDAZ_DL PRIMARY KEY (id_atti_liquidazione_dl);

alter table PBANDI_T_RICHIESTA_EROGAZIONE
rename column DT_RAGGIUNGIMENTO_30P to DT_RAGGIUNGIMENTO_P30;

alter table PBANDI_T_RICHIESTA_EROGAZIONE
rename column DT_RAGGIUNGIMENTO_90P to DT_RAGGIUNGIMENTO_P90;

alter table PBANDI_T_RICHIESTA_EROGAZIONE
rename column DT_RAGGIUNGIMENTO_100P to DT_RAGGIUNGIMENTO_P100;

create sequence seq_PBANDI_W_ATTI_LIQUIDAZI_DT start with 1 nocache;
create sequence seq_PBANDI_W_ATTI_LIQUIDAZI_DL start with 1 nocache;
create sequence seq_PBANDI_W_ATTI_LIQUIDAZI_DM start with 1 nocache;

create table PBANDI_W_ATTI_LIQUIDAZIONE_DM
(TipoRecord      	Varchar2(2)  	,
AnnoEser          Number(4)     ,
Nliq              Number(6)     ,
NroMand           Number(6)     ,
ImportoMandLordo  Number(15, 2) ,
ImportoRitenute   Number(15, 2) ,
ImportoMandNetto  Number(15, 2) ,
DataQuiet         Date          ,
ImportoQuiet      Number(15, 2) ,
CUPMand           Varchar2(15)  ,
CIGMand           Varchar2(10)  ,
FlagPign          Varchar2(1) ,
flag_online CHAR(1)   DEFAULT  'N' NOT NULL,
id_atti_liquidazione_dm  NUMBER  NOT NULL,
id_atti_liquidazione_dt  NUMBER  NOT NULL   )
tablespace PBANDI_SMALL_TBL;

CREATE UNIQUE INDEX PK_PBANDI_W_ATTI_LIQUIDAZ_DM ON PBANDI_W_ATTI_LIQUIDAZIONE_DM
(id_atti_liquidazione_dm  ASC)
	TABLESPACE pbandi_small_idx;



ALTER TABLE PBANDI_W_ATTI_LIQUIDAZIONE_DM
	ADD CONSTRAINT  PK_PBANDI_W_ATTI_LIQUIDAZ_DM PRIMARY KEY (id_atti_liquidazione_dm);



alter TABLE pbandi_w_impegni
add flag_online CHAR(1)   DEFAULT  'N' NOT NULL;

create sequence seq_pbandi_w_impegni start with 1 nocache;

alter TABLE pbandi_w_impegni
add id_impegni            NUMBER  NOT NULL;

CREATE UNIQUE INDEX PK_pbandi_w_impegni ON pbandi_w_impegni
(id_impegni  ASC)
	TABLESPACE pbandi_small_idx;

ALTER TABLE pbandi_w_impegni
	ADD CONSTRAINT  PK_pbandi_w_impegni PRIMARY KEY (id_impegni);

create sequence seq_pbandi_t_storico_accesso start with 1 nocache;

CREATE TABLE pbandi_t_accesso_upp
(
	id_utente             NUMBER(8)  NOT NULL ,
	dt_primo_accesso      DATE  NOT NULL ,
	dt_blocco_accesso     DATE  NOT NULL 
)
	TABLESPACE pbandi_small_tbl;



CREATE UNIQUE INDEX PK_pbandi_t_accesso_upp ON pbandi_t_accesso_upp
(id_utente  ASC)
	TABLESPACE pbandi_small_idx;



ALTER TABLE pbandi_t_accesso_upp
	ADD CONSTRAINT  PK_pbandi_t_accesso_upp PRIMARY KEY (id_utente);



CREATE TABLE pbandi_t_storico_accesso
(
	id_storico_accesso    NUMBER  NOT NULL ,
	id_utente             NUMBER(8)  NOT NULL ,
	dt_accesso            DATE  NULL ,
	id_tipo_accesso       NUMBER(3)  NOT NULL 
)
	TABLESPACE pbandi_small_tbl;



CREATE UNIQUE INDEX PK_pbandi_t_storico_accesso ON pbandi_t_storico_accesso
(id_storico_accesso  ASC)
	TABLESPACE pbandi_small_idx;



ALTER TABLE pbandi_t_storico_accesso
	ADD CONSTRAINT  PK_pbandi_t_storico_accesso PRIMARY KEY (id_storico_accesso);



ALTER TABLE pbandi_t_accesso_upp
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_239 FOREIGN KEY (id_utente) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_storico_accesso
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_240 FOREIGN KEY (id_utente) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_storico_accesso
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_ACCESSO_02 FOREIGN KEY (id_tipo_accesso) REFERENCES pbandi_d_tipo_accesso(id_tipo_accesso));

alter table pbandi_c_costanti
modify valore varchar2(1000);

alter table pbandi_t_comunicaz_fine_prog
drop (flag_allegato_regime_iva);	

ALTER TABLE PBANDI_W_ATTI_LIQUIDAZIONE_DL
	ADD (CONSTRAINT  FK_PBANDI_W_ATTI_LIQUIDA_DT_01 FOREIGN KEY (id_atti_liquidazione_dt) REFERENCES PBANDI_W_ATTI_LIQUIDAZIONE_DT(id_atti_liquidazione_dt));
	
ALTER TABLE PBANDI_W_ATTI_LIQUIDAZIONE_DM
	ADD (CONSTRAINT  FK_PBANDI_W_ATTI_LIQUIDA_DT_02 FOREIGN KEY (id_atti_liquidazione_dt) REFERENCES PBANDI_W_ATTI_LIQUIDAZIONE_DT(id_atti_liquidazione_dt));

CREATE TABLE pbandi_t_accesso_iride
(
	id_accesso_iride      NUMBER  NOT NULL ,
	shib_id               VARCHAR2(255)  NULL ,
	iride_id              VARCHAR2(255)  NULL ,
	msg                   VARCHAR2(255)  NULL ,
	access_date           DATE  NOT NULL ,
	session_id			  VARCHAR2(255)  NULL	
)
	TABLESPACE pbandi_small_tbl;



CREATE UNIQUE INDEX PK_pbandi_t_accesso_iride ON pbandi_t_accesso_iride
(id_accesso_iride  ASC)
	TABLESPACE pbandi_small_idx;



ALTER TABLE pbandi_t_accesso_iride
	ADD CONSTRAINT  PK_pbandi_t_accesso_iride PRIMARY KEY (id_accesso_iride);

create sequence seq_pbandi_t_accesso_iride start with 1 nocache;
	