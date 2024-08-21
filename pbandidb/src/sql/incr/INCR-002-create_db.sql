/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


CREATE TABLE pbandi_d_categoria_cipe
(
	id_categoria_cipe     NUMBER(3)  NOT NULL ,
	cod_categoria_cipe    VARCHAR2(20)  NOT NULL ,
	desc_categoria_cipe   VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_sotto_settore_cipe  NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_categoria_cipe ON pbandi_d_categoria_cipe
(id_categoria_cipe  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_categoria_cipe
	ADD CONSTRAINT  PK_pbandi_d_categoria_cipe PRIMARY KEY (id_categoria_cipe);



CREATE TABLE pbandi_d_intesa
(
	id_intesa             NUMBER(3)  NOT NULL ,
	cod_igrue_t11        NUMBER(10)  NOT NULL ,
	desc_intesa           VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_intesa ON pbandi_d_intesa
(id_intesa  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_intesa
	ADD CONSTRAINT  PK_pbandi_d_intesa PRIMARY KEY (id_intesa);



CREATE TABLE pbandi_d_natura_cipe
(
	id_natura_cipe        NUMBER(3)  NOT NULL ,
	cod_natura_cipe       VARCHAR2(20)  NOT NULL ,
	desc_natura_cipe      VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_natura_cipe ON pbandi_d_natura_cipe
(id_natura_cipe  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_natura_cipe
	ADD CONSTRAINT  PK_pbandi_d_natura_cipe PRIMARY KEY (id_natura_cipe);



CREATE TABLE pbandi_d_settore_cipe
(
	id_settore_cipe       NUMBER(3)  NOT NULL ,
	cod_settore_cipe      VARCHAR2(20)  NOT NULL ,
	desc_settore_cipe     VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_settore_cipe ON pbandi_d_settore_cipe
(id_settore_cipe  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_settore_cipe
	ADD CONSTRAINT  PK_pbandi_d_settore_cipe PRIMARY KEY (id_settore_cipe);



CREATE TABLE pbandi_d_sotto_settore_cipe
(
	id_sotto_settore_cipe  NUMBER(3)  NOT NULL ,
	cod_sotto_settore_cipe  VARCHAR2(20)  NOT NULL ,
	desc_sotto_settore_cipe  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_settore_cipe       NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_sotto_settore_cipe ON pbandi_d_sotto_settore_cipe
(id_sotto_settore_cipe  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_sotto_settore_cipe
	ADD CONSTRAINT  PK_pbandi_d_sotto_settore_cipe PRIMARY KEY (id_sotto_settore_cipe);



CREATE TABLE pbandi_d_tipologia_cipe
(
	id_tipologia_cipe     NUMBER(3)  NOT NULL ,
	cod_tipologia_cipe    VARCHAR2(20)  NOT NULL ,
	desc_tipologia_cipe   VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_natura_cipe        NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipologia_cipe ON pbandi_d_tipologia_cipe
(id_tipologia_cipe  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipologia_cipe
	ADD CONSTRAINT  PK_pbandi_d_tipologia_cipe PRIMARY KEY (id_tipologia_cipe);



ALTER TABLE pbandi_d_categoria_cipe
	ADD (CONSTRAINT  FK_PBANDI_D_SOTTO_SETT_CIPE_01 FOREIGN KEY (id_sotto_settore_cipe) REFERENCES pbandi_d_sotto_settore_cipe(id_sotto_settore_cipe));



ALTER TABLE pbandi_d_sotto_settore_cipe
	ADD (CONSTRAINT  FK_PBANDI_D_SETTORE_CIPE_01 FOREIGN KEY (id_settore_cipe) REFERENCES pbandi_d_settore_cipe(id_settore_cipe));



ALTER TABLE pbandi_d_tipologia_cipe
	ADD (CONSTRAINT  FK_PBANDI_D_NATURA_CIPE_01 FOREIGN KEY (id_natura_cipe) REFERENCES pbandi_d_natura_cipe(id_natura_cipe));

alter table pbandi_t_bando
add id_intesa NUMBER(3)  NULL;

alter table pbandi_t_bando
add id_sotto_settore_cipe  NUMBER(3) NULL;

alter table pbandi_t_bando
add id_natura_cipe NUMBER(3) NULL ;

alter TABLE pbandi_t_bando
add id_tipo_operazione    NUMBER(3) NULL;


ALTER TABLE pbandi_t_bando
	ADD (CONSTRAINT  FK_PBANDI_D_INTESA_01 FOREIGN KEY (id_intesa) REFERENCES pbandi_d_intesa(id_intesa));



ALTER TABLE pbandi_t_bando
	ADD (CONSTRAINT  FK_PBANDI_D_SOTTO_SETT_CIPE_02 FOREIGN KEY (id_sotto_settore_cipe) REFERENCES pbandi_d_sotto_settore_cipe(id_sotto_settore_cipe));

ALTER TABLE pbandi_t_bando
	ADD (CONSTRAINT  FK_PBANDI_D_NATURA_CIPE_02 FOREIGN KEY (id_natura_cipe) REFERENCES pbandi_d_natura_cipe(id_natura_cipe));

ALTER TABLE pbandi_t_bando
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_OPERAZIONE_05 FOREIGN KEY (id_tipo_operazione) REFERENCES pbandi_d_tipo_operazione(id_tipo_operazione));	

CREATE TABLE pbandi_d_priorita_qsn
(
	id_priorita_qsn       NUMBER(3)  NOT NULL ,
	cod_igrue_t12         NUMBER(3)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	desc_priorita_qsn     VARCHAR2(255)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_priorita_qsn ON pbandi_d_priorita_qsn
(id_priorita_qsn  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_priorita_qsn
	ADD CONSTRAINT  PK_pbandi_d_priorita_qsn PRIMARY KEY (id_priorita_qsn);



CREATE TABLE pbandi_d_indicatore_qsn
(
	id_indicatore_qsn     NUMBER(3)  NOT NULL ,
	cod_igrue_t12         NUMBER(3)  NOT NULL ,
	desc_indicatore_qsn   VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL,
    id_priorita_qsn       NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_indicatore_qsn ON pbandi_d_indicatore_qsn
(id_indicatore_qsn  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_indicatore_qsn
	ADD CONSTRAINT  PK_pbandi_d_indicatore_qsn PRIMARY KEY (id_indicatore_qsn);

ALTER TABLE pbandi_d_indicatore_qsn
	ADD (CONSTRAINT  FK_PBANDI_D_PRIORITA_QSN_01 FOREIGN KEY (id_priorita_qsn) REFERENCES pbandi_d_priorita_qsn(id_priorita_qsn));

CREATE TABLE pbandi_d_settore_cpt
(
	id_settore_cpt        NUMBER(3)  NOT NULL ,
	cod_igrue_t3          VARCHAR2(2)  NOT NULL ,
	desc_settore_cpt      VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_settore_cpt ON pbandi_d_settore_cpt
(id_settore_cpt  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_settore_cpt
	ADD CONSTRAINT  PK_pbandi_d_settore_cpt PRIMARY KEY (id_settore_cpt);
	
alter table pbandi_t_bando
add id_settore_cpt        NUMBER(3)  NULL;
	
ALTER TABLE pbandi_t_bando
	ADD (CONSTRAINT  FK_PBANDI_D_SETTORE_CPT_01 FOREIGN KEY (id_settore_cpt) REFERENCES pbandi_d_settore_cpt(id_settore_cpt) ON DELETE SET NULL);

CREATE TABLE pbandi_d_attivita_economica
(
	id_attivita_economica  NUMBER(3)  NOT NULL ,
	cod_igrue_t6          VARCHAR2(2)  NOT NULL ,
	desc_attivita_economica  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_attivita_economica ON pbandi_d_attivita_economica
(id_attivita_economica  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_attivita_economica
	ADD CONSTRAINT  PK_pbandi_d_attivita_economica PRIMARY KEY (id_attivita_economica);





ALTER TABLE pbandi_t_progetto
ADD id_categoria_cipe     NUMBER(3)  NULL;	

ALTER TABLE pbandi_t_progetto
ADD id_tipologia_cipe     NUMBER(3)  NULL;

ALTER TABLE pbandi_t_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_CATEGORIA_CIPE_01 FOREIGN KEY (id_categoria_cipe) REFERENCES pbandi_d_categoria_cipe(id_categoria_cipe));



ALTER TABLE pbandi_t_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_TIPOLOGIA_CIPE_01 FOREIGN KEY (id_tipologia_cipe) REFERENCES pbandi_d_tipologia_cipe(id_tipologia_cipe));


ALTER TABLE pbandi_t_progetto
ADD id_indicatore_qsn     NUMBER(3)  NULL;


ALTER TABLE pbandi_t_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_INDICATORE_QSN_02 FOREIGN KEY (id_indicatore_qsn) REFERENCES pbandi_d_indicatore_qsn(id_indicatore_qsn) ON DELETE SET NULL);
	


CREATE TABLE pbandi_t_dati_progetto_monit
(
	id_progetto           NUMBER(8)  NOT NULL ,
	flag_cardine          CHAR(1)  NOT NULL ,
	flag_generatore_entrate  CHAR(1)  NOT NULL ,
	flag_leg_obiettivo    CHAR(1)  NOT NULL ,
	flag_altro_fondo      CHAR(1)  NOT NULL ,
	stato_fs              VARCHAR2(1)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	flag_invio_monit      CHAR(1)  NOT NULL
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_dati_progetto_moni ON pbandi_t_dati_progetto_monit
(id_progetto  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_dati_progetto_monit
	ADD CONSTRAINT  PK_pbandi_t_dati_progetto_moni PRIMARY KEY (id_progetto);


ALTER TABLE pbandi_t_dati_progetto_monit
	MODIFY flag_cardine CONSTRAINT  ck_flag_03 CHECK (flag_cardine in ('S','N'));


ALTER TABLE pbandi_t_dati_progetto_monit
	MODIFY flag_generatore_entrate CONSTRAINT  ck_flag_08 CHECK (flag_generatore_entrate in ('S','N'));


ALTER TABLE pbandi_t_dati_progetto_monit
	MODIFY flag_leg_obiettivo CONSTRAINT  ck_flag_14 CHECK (flag_leg_obiettivo in ('S','N'));


ALTER TABLE pbandi_t_dati_progetto_monit
	MODIFY flag_altro_fondo CONSTRAINT  ck_flag_18 CHECK (flag_altro_fondo in ('S','N'));

ALTER TABLE pbandi_t_dati_progetto_monit
	MODIFY flag_invio_monit CONSTRAINT  CK_FLAG_21 CHECK (flag_invio_monit in ('S','N'));	

ALTER TABLE pbandi_t_dati_progetto_monit
	MODIFY flag_cardine DEFAULT 'N';


ALTER TABLE pbandi_t_dati_progetto_monit
	MODIFY flag_generatore_entrate DEFAULT 'N';


ALTER TABLE pbandi_t_dati_progetto_monit
	MODIFY flag_leg_obiettivo DEFAULT 'N';


ALTER TABLE pbandi_t_dati_progetto_monit
	MODIFY flag_altro_fondo DEFAULT 'N';


ALTER TABLE pbandi_t_dati_progetto_monit
	MODIFY stato_fs DEFAULT '1';
	

ALTER TABLE pbandi_t_dati_progetto_monit
	MODIFY flag_invio_monit DEFAULT 'S';	

CREATE TABLE pbandi_d_indicatori
(
	id_indicatori         NUMBER(3)  NOT NULL ,
	cod_igrue             VARCHAR2(20)  NOT NULL ,
	desc_indicatore       VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_tipo_indicatore    NUMBER(3)  NOT NULL ,
	id_unita_misura       NUMBER(3)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_indicatori ON pbandi_d_indicatori
(id_indicatori  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_indicatori
	ADD CONSTRAINT  PK_pbandi_d_indicatori PRIMARY KEY (id_indicatori);



CREATE TABLE pbandi_d_tipo_indicatore
(
	id_tipo_indicatore    NUMBER(3)  NOT NULL ,
	desc_breve_tipo_indicatore  VARCHAR2(20)  NOT NULL ,
	desc_tipo_indicatore  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_indicatore ON pbandi_d_tipo_indicatore
(id_tipo_indicatore  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_indicatore
	ADD CONSTRAINT  PK_pbandi_d_tipo_indicatore PRIMARY KEY (id_tipo_indicatore);



CREATE TABLE pbandi_d_unita_misura
(
	id_unita_misura       NUMBER(3)  NOT NULL ,
	desc_breve_unita_misura  VARCHAR2(20)  NOT NULL ,
	desc_unita_misura     VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_unita_misura ON pbandi_d_unita_misura
(id_unita_misura  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_unita_misura
	ADD CONSTRAINT  PK_pbandi_d_unita_misura PRIMARY KEY (id_unita_misura);



ALTER TABLE pbandi_d_indicatori
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_INDICATORE_01 FOREIGN KEY (id_tipo_indicatore) REFERENCES pbandi_d_tipo_indicatore(id_tipo_indicatore));



ALTER TABLE pbandi_d_indicatori
	ADD (CONSTRAINT  FK_PBANDI_D_UNITA_MISURA_01 FOREIGN KEY (id_unita_misura) REFERENCES pbandi_d_unita_misura(id_unita_misura) ON DELETE SET NULL);


CREATE TABLE pbandi_r_domanda_indicatori
(
	id_indicatori         NUMBER(3)  NOT NULL ,
	valore_prog_iniziale  NUMBER(13,2)  NOT NULL ,
	valore_concluso       NUMBER(13,2)  NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	id_domanda            NUMBER(8)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_domanda_indicatori ON pbandi_r_domanda_indicatori
(id_domanda  ASC,id_indicatori  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_domanda_indicatori
	ADD CONSTRAINT  PK_pbandi_r_domanda_indicatori PRIMARY KEY (id_domanda,id_indicatori);



ALTER TABLE pbandi_r_domanda_indicatori
	ADD (CONSTRAINT  FK_PBANDI_D_INDICATORI_01 FOREIGN KEY (id_indicatori) REFERENCES pbandi_d_indicatori(id_indicatori));



ALTER TABLE pbandi_r_domanda_indicatori
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_120 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_domanda_indicatori
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_121 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_domanda_indicatori
	ADD (CONSTRAINT  FK_PBANDI_T_DOMANDA_07 FOREIGN KEY (id_domanda) REFERENCES pbandi_t_domanda(id_domanda));


create sequence seq_pbandi_t_sal start with 1 nocache;
create sequence seq_pbandi_t_procedura_aggiudi start with 1 nocache;
	
CREATE TABLE pbandi_d_tipologia_aggiudicaz
(
	id_tipologia_aggiudicaz  NUMBER(3)  NOT NULL ,
	cod_igrue_t47         NUMBER(1)  NOT NULL ,
	desc_tipologia_aggiudicazione  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipologia_aggiudic ON pbandi_d_tipologia_aggiudicaz
(id_tipologia_aggiudicaz  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipologia_aggiudicaz
	ADD CONSTRAINT  PK_pbandi_d_tipologia_aggiudic PRIMARY KEY (id_tipologia_aggiudicaz);



CREATE TABLE pbandi_r_progetto_sal
(
	id_progetto           NUMBER(8)  NOT NULL ,
	id_sal                NUMBER(8)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_progetto_sal ON pbandi_r_progetto_sal
(id_progetto  ASC,id_sal  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_progetto_sal
	ADD CONSTRAINT  PK_pbandi_r_progetto_sal PRIMARY KEY (id_progetto,id_sal);



CREATE TABLE pbandi_t_procedura_aggiudicaz
(
	id_procedura_aggiudicaz  NUMBER(8)  NOT NULL ,
	desc_proc_agg         VARCHAR2(255)  NOT NULL ,
	importo               NUMBER(13,2)  NOT NULL ,
	cod_proc_agg          VARCHAR2(30)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	id_tipologia_aggiudicaz  NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_procedura_aggiudic ON pbandi_t_procedura_aggiudicaz
(id_procedura_aggiudicaz  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_procedura_aggiudicaz
	ADD CONSTRAINT  PK_pbandi_t_procedura_aggiudic PRIMARY KEY (id_procedura_aggiudicaz);



CREATE TABLE pbandi_t_sal
(
	id_sal                NUMBER(8)  NOT NULL ,
	desc_sal              VARCHAR2(1000)  NOT NULL ,
	importo_sal           NUMBER(13,2)  NOT NULL ,
	dt_sal                DATE  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	id_procedura_aggiudicaz  NUMBER(8)  not NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_sal ON pbandi_t_sal
(id_sal  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_sal
	ADD CONSTRAINT  PK_pbandi_t_sal PRIMARY KEY (id_sal);



ALTER TABLE pbandi_r_progetto_sal
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_12 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_r_progetto_sal
	ADD (CONSTRAINT  FK_PBANDI_T_SAL_01 FOREIGN KEY (id_sal) REFERENCES pbandi_t_sal(id_sal));



ALTER TABLE pbandi_t_procedura_aggiudicaz
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_122 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_procedura_aggiudicaz
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_123 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_procedura_aggiudicaz
	ADD (CONSTRAINT  FK_PBANDI_D_TIPOLOGIA_AGG_01 FOREIGN KEY (id_tipologia_aggiudicaz) REFERENCES pbandi_d_tipologia_aggiudicaz(id_tipologia_aggiudicaz));



ALTER TABLE pbandi_t_sal
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_124 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_sal
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_125 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_sal
	ADD (CONSTRAINT  FK_PBANDI_T_PROCEDURA_AGGIU_01 FOREIGN KEY (id_procedura_aggiudicaz) REFERENCES pbandi_t_procedura_aggiudicaz(id_procedura_aggiudicaz));
	
CREATE TABLE pbandi_d_step_aggiudicazione
(
	id_step_aggiudicazione  NUMBER(3)  NOT NULL ,
	cod_igrue_t48         NUMBER(3)  NOT NULL ,
	desc_step             VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_tipologia_aggiudicaz  NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_step_aggiudicazion ON pbandi_d_step_aggiudicazione
(id_step_aggiudicazione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_step_aggiudicazione
	ADD CONSTRAINT  PK_pbandi_d_step_aggiudicazion PRIMARY KEY (id_step_aggiudicazione);



CREATE TABLE pbandi_r_iter_proc_agg
(
	id_progetto           NUMBER(8)  NOT NULL ,
	id_step_aggiudicazione  NUMBER(3)  NOT NULL ,
	dt_prevista           DATE  NOT NULL ,
	dt_effettiva          DATE  NOT NULL ,
	importo_step          NUMBER(13,2)  NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	id_procedura_aggiudicaz  NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_iter_proc_agg ON pbandi_r_iter_proc_agg
(id_progetto  ASC,id_step_aggiudicazione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_iter_proc_agg
	ADD CONSTRAINT  PK_pbandi_r_iter_proc_agg PRIMARY KEY (id_progetto,id_step_aggiudicazione);



ALTER TABLE pbandi_d_step_aggiudicazione
	ADD (CONSTRAINT  FK_PBANDI_D_TIPOLOGIA_AGG_02 FOREIGN KEY (id_tipologia_aggiudicaz) REFERENCES pbandi_d_tipologia_aggiudicaz(id_tipologia_aggiudicaz));

ALTER TABLE pbandi_r_iter_proc_agg
	ADD (CONSTRAINT  FK_PBANDI_T_PROCEDURA_AGGIU_02 FOREIGN KEY (id_procedura_aggiudicaz) REFERENCES pbandi_t_procedura_aggiudicaz(id_procedura_aggiudicaz) ON DELETE SET NULL);


ALTER TABLE pbandi_r_iter_proc_agg
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_13 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_r_iter_proc_agg
	ADD (CONSTRAINT  FK_PBANDI_D_STEP_AGGIUDICAZ_01 FOREIGN KEY (id_step_aggiudicazione) REFERENCES pbandi_d_step_aggiudicazione(id_step_aggiudicazione));



ALTER TABLE pbandi_r_iter_proc_agg
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_126 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_iter_proc_agg
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_127 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);

CREATE TABLE pbandi_d_step_attivazione
(
	id_step_attivazione   NUMBER(3)  NOT NULL ,
	desc_step_attivazione  VARCHAR2(255)  NOT NULL ,
	cod_igrue_t52         NUMBER(1)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_step_attivazione ON pbandi_d_step_attivazione
(id_step_attivazione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_step_attivazione
	ADD CONSTRAINT  PK_pbandi_d_step_attivazione PRIMARY KEY (id_step_attivazione);



CREATE TABLE pbandi_d_tipologia_attivazione
(
	id_tipologia_attivazione  NUMBER(3)  NOT NULL ,
	desc_tipologia_attivazione  VARCHAR2(255)  NOT NULL ,
	cod_igrue_t50         NUMBER(2)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipologia_attivazi ON pbandi_d_tipologia_attivazione
(id_tipologia_attivazione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipologia_attivazione
	ADD CONSTRAINT  PK_pbandi_d_tipologia_attivazi PRIMARY KEY (id_tipologia_attivazione);


CREATE TABLE pbandi_r_iter_proc_att
(
	id_step_attivazione   NUMBER(3)  NOT NULL ,
	dt_prevista           DATE  NOT NULL ,
	dt_effettiva          DATE  NULL ,
	importo_finale        NUMBER(13,2)  NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_bando              NUMBER(8)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_iter_proc_att ON pbandi_r_iter_proc_att
(id_bando  ASC,id_step_attivazione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_iter_proc_att
	ADD CONSTRAINT  PK_pbandi_r_iter_proc_att PRIMARY KEY (id_bando,id_step_attivazione);



ALTER TABLE pbandi_r_iter_proc_att
	ADD (CONSTRAINT  FK_PBANDI_D_STEP_ATTIVAZION_01 FOREIGN KEY (id_step_attivazione) REFERENCES pbandi_d_step_attivazione(id_step_attivazione));



ALTER TABLE pbandi_r_iter_proc_att
	ADD (CONSTRAINT  FK_PBANDI_T_BANDO_06 FOREIGN KEY (id_bando) REFERENCES pbandi_t_bando(id_bando));


alter table pbandi_d_tipo_operazione
add cod_igrue_t0 varchar2(1);

CREATE TABLE pbandi_d_iter
(
	id_iter               NUMBER(3)  NOT NULL ,
	cod_igrue_t35         VARCHAR2(4)  NOT NULL ,
	desc_iter             VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_tipo_operazione    NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_iter ON pbandi_d_iter
(id_iter  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_iter
	ADD CONSTRAINT  PK_pbandi_d_iter PRIMARY KEY (id_iter);



ALTER TABLE pbandi_d_iter
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_OPERAZIONE_02 FOREIGN KEY (id_tipo_operazione) REFERENCES pbandi_d_tipo_operazione(id_tipo_operazione));

CREATE TABLE pbandi_d_fase_monit
(
	id_fase_monit         NUMBER(3)  NOT NULL ,
	cod_igrue_t35         VARCHAR2(3)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	desc_fase_monit       VARCHAR2(255)  NOT NULL ,
	id_iter               NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_fase_monit ON pbandi_d_fase_monit
(id_fase_monit  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_fase_monit
	ADD CONSTRAINT  PK_pbandi_d_fase_monit PRIMARY KEY (id_fase_monit);



ALTER TABLE pbandi_d_fase_monit
	ADD (CONSTRAINT  FK_PBANDI_D_ITER_01 FOREIGN KEY (id_iter) REFERENCES pbandi_d_iter(id_iter));

CREATE TABLE pbandi_r_progetto_fase_monit
(
	id_progetto           NUMBER(8)  NOT NULL ,
	id_fase_monit         NUMBER(3)  NOT NULL ,
	dt_inizio_prevista    DATE  NOT NULL ,
	dt_fine_prevista      DATE  NOT NULL ,
	dt_inizio_effettiva   DATE  NOT NULL ,
	dt_fine_effettiva     DATE  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_progetto_fase_moni ON pbandi_r_progetto_fase_monit
(id_progetto  ASC,id_fase_monit  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_progetto_fase_monit
	ADD CONSTRAINT  PK_pbandi_r_progetto_fase_moni PRIMARY KEY (id_progetto,id_fase_monit);



ALTER TABLE pbandi_r_progetto_fase_monit
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_14 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_r_progetto_fase_monit
	ADD (CONSTRAINT  FK_PBANDI_D_FASE_MONIT_01 FOREIGN KEY (id_fase_monit) REFERENCES pbandi_d_fase_monit(id_fase_monit));



ALTER TABLE pbandi_r_progetto_fase_monit
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_128 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_progetto_fase_monit
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_129 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);

CREATE TABLE pbandi_d_tema_prioritario
(
	id_tema_prioritario   NUMBER(3)  NOT NULL ,
	cod_igrue_t4          VARCHAR2(2)  NOT NULL ,
	desc_tema_prioritario  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tema_prioritario ON pbandi_d_tema_prioritario
(id_tema_prioritario  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tema_prioritario
	ADD CONSTRAINT  PK_pbandi_d_tema_prioritario PRIMARY KEY (id_tema_prioritario);

CREATE TABLE pbandi_r_ban_linea_int_tem_pri
(
	id_tema_prioritario   NUMBER(3)  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	progr_bando_linea_intervento  NUMBER(8)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_ban_linea_int_tem_ ON pbandi_r_ban_linea_int_tem_pri
(progr_bando_linea_intervento  ASC,id_tema_prioritario  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_ban_linea_int_tem_pri
	ADD CONSTRAINT  PK_pbandi_r_ban_linea_int_tem_ PRIMARY KEY (progr_bando_linea_intervento,id_tema_prioritario);



ALTER TABLE pbandi_r_ban_linea_int_tem_pri
	ADD (CONSTRAINT  FK_PBANDI_D_TEMA_PRIORITAR_01 FOREIGN KEY (id_tema_prioritario) REFERENCES pbandi_d_tema_prioritario(id_tema_prioritario));



ALTER TABLE pbandi_r_ban_linea_int_tem_pri
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_130 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_ban_linea_int_tem_pri
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_131 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_ban_linea_int_tem_pri
	ADD (CONSTRAINT  FK_PBANDI_R_BANDO_LINEA_INT_06 FOREIGN KEY (progr_bando_linea_intervento) REFERENCES pbandi_r_bando_linea_intervent(progr_bando_linea_intervento));

alter table pbandi_t_progetto
add id_tema_prioritario   NUMBER(3)  NULL ;

ALTER TABLE pbandi_t_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_TEMA_PRIORITAR_02 FOREIGN KEY (id_tema_prioritario) REFERENCES pbandi_d_tema_prioritario(id_tema_prioritario) ON DELETE SET NULL);

CREATE TABLE pbandi_d_obiettivo_gen_qsn
(
	id_obiettivo_gen_qsn  NUMBER(3)  NOT NULL ,
	cod_igrue_t2          VARCHAR2(6)  NOT NULL ,
	desc_obiettivo_generale_qsn  VARCHAR2(500)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_priorita_qsn       NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_obiettivo_gen_qsn ON pbandi_d_obiettivo_gen_qsn
(id_obiettivo_gen_qsn  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_obiettivo_gen_qsn
	ADD CONSTRAINT  PK_pbandi_d_obiettivo_gen_qsn PRIMARY KEY (id_obiettivo_gen_qsn);



ALTER TABLE pbandi_d_obiettivo_gen_qsn
	ADD (CONSTRAINT  FK_PBANDI_D_PRIORITA_QSN_02 FOREIGN KEY (id_priorita_qsn) REFERENCES pbandi_d_priorita_qsn(id_priorita_qsn));
	
CREATE TABLE pbandi_d_obiettivo_specif_qsn
(
	id_obiettivo_specif_qsn  NUMBER(3)  NOT NULL ,
	cod_igrue_t2          VARCHAR2(6)  NOT NULL ,
	desc_obiettivo_specifico_qsn  VARCHAR2(500)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_obiettivo_gen_qsn  NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_obiettivo_specif_q ON pbandi_d_obiettivo_specif_qsn
(id_obiettivo_specif_qsn  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_obiettivo_specif_qsn
	ADD CONSTRAINT  PK_pbandi_d_obiettivo_specif_q PRIMARY KEY (id_obiettivo_specif_qsn);



ALTER TABLE pbandi_d_obiettivo_specif_qsn
	ADD (CONSTRAINT  FK_PBANDI_D_PRIORITA_QSN_03 FOREIGN KEY (id_obiettivo_gen_qsn) REFERENCES pbandi_d_obiettivo_gen_qsn(id_obiettivo_gen_qsn));

alter table pbandi_t_progetto
add id_obiettivo_specif_qsn  NUMBER(3)  NULL;

ALTER TABLE pbandi_t_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_OBIETTIVO_SPECI_01 FOREIGN KEY (id_obiettivo_specif_qsn) REFERENCES pbandi_d_obiettivo_specif_qsn(id_obiettivo_specif_qsn) ON DELETE SET NULL);

CREATE TABLE pbandi_r_linea_priorita_qsn
(
	id_linea_di_intervento  NUMBER(3)  NOT NULL ,
	id_priorita_qsn       NUMBER(3)  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_linea_priorita_qsn ON pbandi_r_linea_priorita_qsn
(id_linea_di_intervento  ASC,id_priorita_qsn  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_linea_priorita_qsn
	ADD CONSTRAINT  PK_pbandi_r_linea_priorita_qsn PRIMARY KEY (id_linea_di_intervento,id_priorita_qsn);



ALTER TABLE pbandi_r_linea_priorita_qsn
	ADD (CONSTRAINT  FK_PBANDI_D_LINEA_INTERV_06 FOREIGN KEY (id_linea_di_intervento) REFERENCES pbandi_d_linea_di_intervento(id_linea_di_intervento));



ALTER TABLE pbandi_r_linea_priorita_qsn
	ADD (CONSTRAINT  FK_PBANDI_D_PRIORITA_QSN_04 FOREIGN KEY (id_priorita_qsn) REFERENCES pbandi_d_priorita_qsn(id_priorita_qsn));



ALTER TABLE pbandi_r_linea_priorita_qsn
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_132 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_linea_priorita_qsn
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_133 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);
	


CREATE TABLE pbandi_d_ind_risultato_program
(
	id_ind_risultato_program  NUMBER(3)  NOT NULL ,
	cod_igrue_t20         NUMBER(3)  NOT NULL ,
	desc_ind_risultato_programma  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_ind_risultato_prog ON pbandi_d_ind_risultato_program
(id_ind_risultato_program  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_ind_risultato_program
	ADD CONSTRAINT  PK_pbandi_d_ind_risultato_prog PRIMARY KEY (id_ind_risultato_program);





CREATE TABLE pbandi_d_tipo_strumento_progr
(
	id_tipo_strumento_progr  NUMBER(3)  NOT NULL ,
	cod_strumento         VARCHAR2(20)  NOT NULL ,
	desc_strumento        VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_strumento_pro ON pbandi_d_tipo_strumento_progr
(id_tipo_strumento_progr  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_strumento_progr
	ADD CONSTRAINT  PK_pbandi_d_tipo_strumento_pro PRIMARY KEY (id_tipo_strumento_progr);
	
ALTER TABLE pbandi_d_linea_di_intervento
ADD id_tipo_strumento_progr  NUMBER(3);

ALTER TABLE pbandi_d_linea_di_intervento
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_STRUM_PROG_01 FOREIGN KEY (id_tipo_strumento_progr) REFERENCES pbandi_d_tipo_strumento_progr(id_tipo_strumento_progr));
	


CREATE TABLE pbandi_r_linea_interv_mod_agev
(
	id_linea_di_intervento  NUMBER(3)  NOT NULL ,
	id_modalita_agevolazione  NUMBER(3)  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_linea_interv_mod_a ON pbandi_r_linea_interv_mod_agev
(id_linea_di_intervento  ASC,id_modalita_agevolazione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_linea_interv_mod_agev
	ADD CONSTRAINT  PK_pbandi_r_linea_interv_mod_a PRIMARY KEY (id_linea_di_intervento,id_modalita_agevolazione);



ALTER TABLE pbandi_r_linea_interv_mod_agev
	ADD (CONSTRAINT  FK_PBANDI_D_LINEA_INTERV_09 FOREIGN KEY (id_linea_di_intervento) REFERENCES pbandi_d_linea_di_intervento(id_linea_di_intervento));



ALTER TABLE pbandi_r_linea_interv_mod_agev
	ADD (CONSTRAINT  FK_PBANDI_D_MODALITA_AGEVOL_03 FOREIGN KEY (id_modalita_agevolazione) REFERENCES pbandi_d_modalita_agevolazione(id_modalita_agevolazione));



ALTER TABLE pbandi_r_linea_interv_mod_agev
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_134 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_linea_interv_mod_agev
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_135 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);
	
	CREATE TABLE pbandi_d_voce_di_spesa_monit
(
	id_voce_di_spesa_monit  NUMBER(3)  NOT NULL ,
	cod_igrue_t28         NUMBER(2)  NOT NULL ,
	desc_voce_spesa_monit  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_tipo_operazione    NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_voce_di_spesa_moni ON pbandi_d_voce_di_spesa_monit
(id_voce_di_spesa_monit  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_voce_di_spesa_monit
	ADD CONSTRAINT  PK_pbandi_d_voce_di_spesa_moni PRIMARY KEY (id_voce_di_spesa_monit);



ALTER TABLE pbandi_d_voce_di_spesa_monit
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_OPERAZIONE_03 FOREIGN KEY (id_tipo_operazione) REFERENCES pbandi_d_tipo_operazione(id_tipo_operazione));
	
ALTER TABLE pbandi_d_voce_di_spesa
ADD id_voce_di_spesa_monit NUMBER(3);


ALTER TABLE pbandi_d_voce_di_spesa
	ADD (CONSTRAINT  FK_PBANDI_D_VOCE_SPES_MONIT_01 FOREIGN KEY (id_voce_di_spesa_monit) REFERENCES pbandi_d_voce_di_spesa_monit(id_voce_di_spesa_monit));

CREATE TABLE pbandi_d_dimensione_territor
(
	id_dimensione_territor  NUMBER(3)  NOT NULL ,
	cod_igrue_t7          VARCHAR2(2)  NOT NULL ,
	desc_dimensione_territoriale  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_dimensione_territo ON pbandi_d_dimensione_territor
(id_dimensione_territor  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_dimensione_territor
	ADD CONSTRAINT  PK_pbandi_d_dimensione_territo PRIMARY KEY (id_dimensione_territor);
	
ALTER TABLE pbandi_d_comune
ADD id_dimensione_territor  NUMBER(3)  NULL ;

ALTER TABLE pbandi_d_comune
	ADD (CONSTRAINT  FK_PBANDI_D_DIMENSIONE_TERR_01 FOREIGN KEY (id_dimensione_territor) REFERENCES pbandi_d_dimensione_territor(id_dimensione_territor) ON DELETE SET NULL);


alter table pbandi_r_prog_sogg_finanziat
add estremi_provv varchar2(200);	
	
alter table pbandi_d_linea_di_intervento
add cod_igrue_t13_t14 varchar2(20);	
	
alter table pbandi_d_tipo_ente_competenza
add cod_igrue_t51 number(4);	
	
alter table pbandi_t_ente_competenza
add cod_cipe VARCHAR2(10);	
	
alter table pbandi_t_ente_competenza
add user_cipe VARCHAR2(30);	
	
alter table pbandi_t_ente_competenza
add pass_cipe VARCHAR2(30);	
	
CREATE TABLE pbandi_d_unita_organizzativa
(
	id_unita_organizzativa  NUMBER(3)  NOT NULL ,
	desc_ente             VARCHAR2(255)  NOT NULL ,
	cod_cipe_uo           VARCHAR2(20)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_ente_competenza    NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_unita_organizzativ ON pbandi_d_unita_organizzativa
(id_unita_organizzativa  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_unita_organizzativa
	ADD CONSTRAINT  PK_pbandi_d_unita_organizzativ PRIMARY KEY (id_unita_organizzativa);



ALTER TABLE pbandi_d_unita_organizzativa
	ADD (CONSTRAINT  FK_PBANDI_T_ENTE_COMPETENZA_03 FOREIGN KEY (id_ente_competenza) REFERENCES pbandi_t_ente_competenza(id_ente_competenza) ON DELETE SET NULL);	

ALTER TABLE pbandi_r_bando_linea_intervent
ADD id_unita_organizzativa NUMBER(3);

ALTER TABLE pbandi_r_bando_linea_intervent
	ADD (CONSTRAINT  FK_PBANDI_D_UNITA_ORGANIZZ_01 FOREIGN KEY (id_unita_organizzativa) REFERENCES pbandi_d_unita_organizzativa(id_unita_organizzativa) ON DELETE SET NULL);

alter table pbandi_t_ente_competenza
add id_attivita_ateco NUMBER(4);

ALTER TABLE pbandi_t_ente_competenza
	ADD (CONSTRAINT  FK_PBANDI_D_ATTIVITA_ATECO_02 FOREIGN KEY (id_attivita_ateco) REFERENCES pbandi_d_attivita_ateco(id_attivita_ateco));

alter table pbandi_t_ente_competenza
add id_forma_giuridica NUMBER(3); 

ALTER TABLE pbandi_t_ente_competenza
	ADD (CONSTRAINT  FK_PBANDI_D_FORMA_GIURIDICA_02 FOREIGN KEY (id_forma_giuridica) REFERENCES pbandi_d_forma_giuridica(id_forma_giuridica));

drop sequence seq_pbandi_t_revoca;	

create sequence seq_pbandi_t_revoca_rinuncia start with 2 nocache;	

alter table pbandi_t_revoca rename to pbandi_t_revoca_rinuncia;

ALTER TABLE PBANDI_T_REVOCA_RINUNCIA
RENAME COLUMN IMPORTO_REVOCATO TO IMPORTO;

ALTER TABLE PBANDI_T_REVOCA_RINUNCIA
RENAME COLUMN DT_REVOCA TO DT_REVOCA_RINUNCIA;

ALTER TABLE PBANDI_T_REVOCA_RINUNCIA
add estremi varchar2(250);

drop table PBANDI_R_PROGETTO_UTENTE_CIPE cascade constraint;

drop table PBANDI_T_UTENTE_CIPE cascade constraint;

alter table pbandi_t_bando
drop (id_modalita_attivazione);

drop table pbandi_d_modalita_attivazione cascade constraint; 

CREATE TABLE pbandi_d_motivo_revoca
(
	id_motivo_revoca      NUMBER(3)  NOT NULL ,
	desc_motivo_revoca    VARCHAR2(255)  NOT NULL ,
	cod_igrue_t36         VARCHAR2(2)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_motivo_revoca ON pbandi_d_motivo_revoca
(id_motivo_revoca  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_motivo_revoca
	ADD CONSTRAINT  PK_pbandi_d_motivo_revoca PRIMARY KEY (id_motivo_revoca);

ALTER TABLE pbandi_t_revoca_rinuncia
ADD id_motivo_revoca NUMBER(3);


ALTER TABLE pbandi_t_revoca_rinuncia
	ADD (CONSTRAINT  FK_PBANDI_D_MOTIVO_REVOCA_01 FOREIGN KEY (id_motivo_revoca) REFERENCES pbandi_d_motivo_revoca(id_motivo_revoca));	

CREATE TABLE pbandi_d_tipo_revoca_rinuncia
(
	id_tipo_revoca_rinuncia  NUMBER(3)  NOT NULL ,
	desc_breve_tipo_revoc_rinuncia  VARCHAR2(20)  NOT NULL ,
	desc_tipo_revoca_rinuncia  VARCHAR2(255)  NOT NULL ,
	cod_igrue             NUMBER(1)  NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_revoca_rinunc ON pbandi_d_tipo_revoca_rinuncia
(id_tipo_revoca_rinuncia  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_revoca_rinuncia
	ADD CONSTRAINT  PK_pbandi_d_tipo_revoca_rinunc PRIMARY KEY (id_tipo_revoca_rinuncia);
	
ALTER TABLE pbandi_t_revoca_rinuncia
ADD id_tipo_revoca_rinuncia NUMBER(3);

ALTER TABLE pbandi_t_revoca_rinuncia
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_REVOCA_RIN_01 FOREIGN KEY (id_tipo_revoca_rinuncia) REFERENCES pbandi_d_tipo_revoca_rinuncia(id_tipo_revoca_rinuncia));	

create sequence seq_pbandi_t_sospensione start with 1 nocache;

CREATE TABLE pbandi_t_sospensione
(
	id_sospensione        NUMBER(8)  NOT NULL ,
	motivazione           VARCHAR2(255)  NULL ,
	dt_inizio             DATE  NULL ,
	dt_fine_prevista      DATE  NULL ,
	dt_fine               DATE  NULL ,
	id_progetto           NUMBER(8)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_sospensione ON pbandi_t_sospensione
(id_sospensione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_sospensione
	ADD CONSTRAINT  PK_pbandi_t_sospensione PRIMARY KEY (id_sospensione);



ALTER TABLE pbandi_t_sospensione
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_03 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_t_sospensione
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_158 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_sospensione
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_159 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);	
	
	
	


-- modifiche Alessandro
CREATE TABLE pbandi_d_causale_erogazione
(
	id_causale_erogazione  NUMBER(3)  NOT NULL ,
	desc_causale          VARCHAR2(255)  NOT NULL ,
	desc_breve_causale    VARCHAR2(20)  NOT NULL ,
	flag_certificazione   CHAR(1)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_causale_erogazione ON pbandi_d_causale_erogazione
(id_causale_erogazione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_causale_erogazione
	ADD CONSTRAINT  PK_pbandi_d_causale_erogazione PRIMARY KEY (id_causale_erogazione);


ALTER TABLE pbandi_d_causale_erogazione
	MODIFY flag_certificazione CONSTRAINT  ck_flag_19 CHECK (flag_certificazione in ('S','N'));

CREATE TABLE pbandi_d_modalita_erogazione
(
	id_modalita_erogazione  NUMBER(3)  NOT NULL ,
	desc_breve_modalita_erogazione  VARCHAR2(20)  NOT NULL ,
	desc_modalita_erogazione  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_modalita_erogazion ON pbandi_d_modalita_erogazione
(id_modalita_erogazione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_modalita_erogazione
	ADD CONSTRAINT  PK_pbandi_d_modalita_erogazion PRIMARY KEY (id_modalita_erogazione);	
	
	
create sequence seq_pbandi_t_erogazione start with 1 nocache;

CREATE TABLE pbandi_t_erogazione
(
	id_erogazione         NUMBER(8)  NOT NULL ,
	importo_erogazione    NUMBER(13,2)  NOT NULL ,
	cod_riferimento_erogazione  VARCHAR2(20)  NULL ,
	dt_contabile          DATE  not NULL ,
	note_erogazione       VARCHAR2(4000)  NULL ,
	id_causale_erogazione  NUMBER(3)  NOT NULL ,
	id_modalita_agevolazione  NUMBER(3)  NOT NULL ,
	id_progetto           NUMBER(8)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL,
	id_modalita_erogazione  NUMBER(3)  NOT NULL,
	id_direzione_regionale  NUMBER(3)  NULL
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_erogazione ON pbandi_t_erogazione
(id_erogazione  ASC);



ALTER TABLE pbandi_t_erogazione
	ADD CONSTRAINT  PK_pbandi_t_erogazione PRIMARY KEY (id_erogazione);



ALTER TABLE pbandi_t_erogazione
	ADD (CONSTRAINT  FK_PBANDI_D_CAUSALE_EROGAZI_02 FOREIGN KEY (id_causale_erogazione) REFERENCES pbandi_d_causale_erogazione(id_causale_erogazione));


ALTER TABLE pbandi_t_erogazione
	ADD (CONSTRAINT  FK_PBANDI_D_MODALITA_AGEVOL_04 FOREIGN KEY (id_modalita_agevolazione) REFERENCES pbandi_d_modalita_agevolazione(id_modalita_agevolazione));



ALTER TABLE pbandi_t_erogazione
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_15 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));

ALTER TABLE pbandi_t_erogazione
	ADD (CONSTRAINT  FK_PBANDI_D_MODALITA_EROGAZ_01 FOREIGN KEY (id_modalita_erogazione) REFERENCES pbandi_d_modalita_erogazione(id_modalita_erogazione));

ALTER TABLE pbandi_t_erogazione
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_138 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));

ALTER TABLE pbandi_t_erogazione
	ADD (CONSTRAINT  FK_PBANDI_D_DIREZ_REGIONALE_03 FOREIGN KEY (id_direzione_regionale) REFERENCES pbandi_d_direzione_regionale(id_direzione_regionale));

ALTER TABLE pbandi_t_erogazione
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_139 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);

	

CREATE TABLE pbandi_d_tipo_trattamento
(
	id_tipo_trattamento   NUMBER(3)  NOT NULL ,
	desc_breve_tipo_trattamento  VARCHAR2(20)  NOT NULL ,
	desc_tipo_trattamento  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_trattamento ON pbandi_d_tipo_trattamento
(id_tipo_trattamento  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_trattamento
	ADD CONSTRAINT  PK_pbandi_d_tipo_trattamento PRIMARY KEY (id_tipo_trattamento);

create sequence seq_pbandi_t_fideiussione start with 1 nocache;	

CREATE TABLE pbandi_t_fideiussione
(
	id_fideiussione       NUMBER(8)  NOT NULL ,
	importo_fideiussione  NUMBER(13,2)  NOT NULL ,
	cod_riferimento_fideiussione  VARCHAR2(20)  NULL ,
	dt_decorrenza         DATE  NOT NULL ,
	dt_scadenza           DATE  NULL ,
	desc_ente_emittente   VARCHAR2(255)  NULL ,
	note_fideiussione     VARCHAR2(4000)  NULL ,
	id_tipo_trattamento   NUMBER(3)  NOT NULL ,
	id_progetto           NUMBER(8)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_fideiussione ON pbandi_t_fideiussione
(id_fideiussione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_fideiussione
	ADD CONSTRAINT  PK_pbandi_t_fideiussione PRIMARY KEY (id_fideiussione);



ALTER TABLE pbandi_t_fideiussione
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_TRATTAMENT_01 FOREIGN KEY (id_tipo_trattamento) REFERENCES pbandi_d_tipo_trattamento(id_tipo_trattamento));



ALTER TABLE pbandi_t_fideiussione
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_16 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_t_fideiussione
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_142 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_fideiussione
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_143 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);

CREATE TABLE pbandi_d_tipo_sogg_finanziat
(
	id_tipo_sogg_finanziat  NUMBER(3)  NOT NULL ,
	desc_breve_tipo_sogg_finanziat  VARCHAR2(20)  NOT NULL ,
	desc_tipo_sogg_finanziatore  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_sogg_finanzia ON pbandi_d_tipo_sogg_finanziat
(id_tipo_sogg_finanziat  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_sogg_finanziat
	ADD CONSTRAINT  PK_pbandi_d_tipo_sogg_finanzia PRIMARY KEY (id_tipo_sogg_finanziat);
	
alter TABLE pbandi_d_soggetto_finanziatore
add cod_igrue_t25 NUMBER(2);

alter TABLE pbandi_d_soggetto_finanziatore
add cod_cipe_cup varchar2(3);

alter TABLE pbandi_d_soggetto_finanziatore
add flag_certificazione   CHAR(1);

ALTER TABLE pbandi_d_soggetto_finanziatore
	MODIFY flag_certificazione CONSTRAINT  ck_flag_20 CHECK (flag_certificazione in ('S','N'));
	
alter table pbandi_d_soggetto_finanziatore
add id_tipo_sogg_finanziat NUMBER(3); 	

ALTER TABLE pbandi_d_soggetto_finanziatore
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_SOGG_FINAN_01 FOREIGN KEY (id_tipo_sogg_finanziat) REFERENCES pbandi_d_tipo_sogg_finanziat(id_tipo_sogg_finanziat));
	



alter table pbandi_d_modalita_agevolazione
add cod_igrue_t8 VARCHAR2(2);

alter table pbandi_d_modalita_agevolazione
add flag_certificazione CHAR(1);
	


CREATE TABLE pbandi_d_invio_proposta_certif
(
	id_invio_proposta_certif  NUMBER(3)  NOT NULL ,
	email                 VARCHAR2(128)  NULL ,
	id_linea_di_intervento  NUMBER(3)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_invio_proposta_cer ON pbandi_d_invio_proposta_certif
(id_invio_proposta_certif  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_invio_proposta_certif
	ADD CONSTRAINT  PK_pbandi_d_invio_proposta_cer PRIMARY KEY (id_invio_proposta_certif);



ALTER TABLE pbandi_d_invio_proposta_certif
	ADD (CONSTRAINT  FK_PBANDI_D_LINEA_INTERV_10 FOREIGN KEY (id_linea_di_intervento) REFERENCES pbandi_d_linea_di_intervento(id_linea_di_intervento));
	
CREATE TABLE pbandi_r_bando_tipo_trattament
(
	id_bando              NUMBER(8)  NOT NULL ,
	id_tipo_trattamento   NUMBER(3)  NOT NULL ,
	flag_associazione_default  CHAR(1)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_bando_tipo_trattam ON pbandi_r_bando_tipo_trattament
(id_bando  ASC,id_tipo_trattamento  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_bando_tipo_trattament
	ADD CONSTRAINT  PK_pbandi_r_bando_tipo_trattam PRIMARY KEY (id_bando,id_tipo_trattamento);


ALTER TABLE pbandi_r_bando_tipo_trattament
	MODIFY flag_associazione_default CONSTRAINT  ck_flag_22 CHECK (flag_associazione_default in ('S','N'));



ALTER TABLE pbandi_r_bando_tipo_trattament
	ADD (CONSTRAINT  FK_PBANDI_T_BANDO_07 FOREIGN KEY (id_bando) REFERENCES pbandi_t_bando(id_bando));



ALTER TABLE pbandi_r_bando_tipo_trattament
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_TRATTAMENT_02 FOREIGN KEY (id_tipo_trattamento) REFERENCES pbandi_d_tipo_trattamento(id_tipo_trattamento));



ALTER TABLE pbandi_r_bando_tipo_trattament
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_144 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_bando_tipo_trattament
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_145 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);

create sequence seq_pbandi_r_bando_causale_ero start with 1 nocache;	
	
CREATE TABLE pbandi_r_bando_causale_erogaz
(
	id_bando              NUMBER(8)  NOT NULL ,
	id_causale_erogazione  NUMBER(3)  NOT NULL ,
	perc_soglia_spesa_quietanzata  NUMBER(5,2)  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	progr_bando_causale_erogaz  NUMBER(8)  NOT NULL ,
	perc_erogazione       NUMBER(5,2)  NULL ,
	perc_limite           NUMBER(5,2)  NULL ,
	id_dimensione_impresa  NUMBER(3)  NULL ,
	id_forma_giuridica    NUMBER(3)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_bando_causale_erog ON pbandi_r_bando_causale_erogaz
(progr_bando_causale_erogaz  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_bando_causale_erogaz
	ADD CONSTRAINT  PK_pbandi_r_bando_causale_erog PRIMARY KEY (progr_bando_causale_erogaz);



ALTER TABLE pbandi_r_bando_causale_erogaz
	ADD (CONSTRAINT  FK_PBANDI_T_BANDO_08 FOREIGN KEY (id_bando) REFERENCES pbandi_t_bando(id_bando));



ALTER TABLE pbandi_r_bando_causale_erogaz
	ADD (CONSTRAINT  FK_PBANDI_D_CAUSALE_EROGAZI_04 FOREIGN KEY (id_causale_erogazione) REFERENCES pbandi_d_causale_erogazione(id_causale_erogazione));



ALTER TABLE pbandi_r_bando_causale_erogaz
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_146 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_bando_causale_erogaz
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_147 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_bando_causale_erogaz
	ADD (CONSTRAINT  FK_PBANDI_D_DIMENSIONE_IMPR_02 FOREIGN KEY (id_dimensione_impresa) REFERENCES pbandi_d_dimensione_impresa(id_dimensione_impresa) ON DELETE SET NULL);



ALTER TABLE pbandi_r_bando_causale_erogaz
	ADD (CONSTRAINT  FK_PBANDI_D_FORMA_GIURIDICA_03 FOREIGN KEY (id_forma_giuridica) REFERENCES pbandi_d_forma_giuridica(id_forma_giuridica) ON DELETE SET NULL);

	
CREATE TABLE pbandi_d_stato_proposta_certif
(
	id_stato_proposta_certif  NUMBER(3)  NOT NULL ,
	desc_breve_stato_proposta_cert  VARCHAR2(20)  NOT NULL ,
	desc_stato_proposta_certif  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_stato_proposta_cer ON pbandi_d_stato_proposta_certif
(id_stato_proposta_certif  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_stato_proposta_certif
	ADD CONSTRAINT  PK_pbandi_d_stato_proposta_cer PRIMARY KEY (id_stato_proposta_certif);

create sequence seq_pbandi_t_proposta_certif start with 1 nocache;

CREATE TABLE pbandi_t_proposta_certificaz
(
	id_proposta_certificaz  NUMBER(8)  NOT NULL ,
	dt_ora_creazione      DATE  NULL ,
	desc_proposta         VARCHAR2(255)  NULL ,
	dt_cut_off_pagamenti  DATE  NOT NULL ,
	dt_cut_off_validazioni  DATE  NOT NULL ,
	dt_cut_off_erogazioni  DATE  NOT NULL ,
	dt_cut_off_fideiussioni  DATE  NOT NULL ,
	id_stato_proposta_certif  NUMBER(3)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_proposta_certifica ON pbandi_t_proposta_certificaz
(id_proposta_certificaz  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_proposta_certificaz
	ADD CONSTRAINT  PK_pbandi_t_proposta_certifica PRIMARY KEY (id_proposta_certificaz);



ALTER TABLE pbandi_t_proposta_certificaz
	ADD (CONSTRAINT  FK_PBANDI_D_STATO_PROP_CERT_01 FOREIGN KEY (id_stato_proposta_certif) REFERENCES pbandi_d_stato_proposta_certif(id_stato_proposta_certif));



ALTER TABLE pbandi_t_proposta_certificaz
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_148 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_proposta_certificaz
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_149 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);
	

create sequence seq_pbandi_t_dett_proposta_cer start with 1 nocache;

CREATE TABLE pbandi_t_dett_proposta_certif
(
	id_dett_proposta_certif  NUMBER(8)  NOT NULL ,
	id_proposta_certificaz  NUMBER(8)  NOT NULL ,
	costo_ammesso         NUMBER(13,2)  NULL ,
	importo_pagamenti_validati  NUMBER(13,2)  NULL ,
	importo_erogazioni    NUMBER(13,2)  NULL ,
	importo_fideiussioni  NUMBER(13,2)  NULL ,
	spesa_certificabile_lorda  NUMBER(13,2)  NULL ,
	id_progetto           NUMBER(8)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	importo_eccendenze_validazione  NUMBER(13,2)
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_dett_proposta_cert ON pbandi_t_dett_proposta_certif
(id_dett_proposta_certif  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_dett_proposta_certif
	ADD CONSTRAINT  PK_pbandi_t_dett_proposta_cert PRIMARY KEY (id_dett_proposta_certif);



ALTER TABLE pbandi_t_dett_proposta_certif
	ADD (CONSTRAINT  FK_PBANDI_T_PROPOSTA_CERTIF_02 FOREIGN KEY (id_proposta_certificaz) REFERENCES pbandi_t_proposta_certificaz(id_proposta_certificaz));



ALTER TABLE pbandi_t_dett_proposta_certif
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_17 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_t_dett_proposta_certif
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_152 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_dett_proposta_certif
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_153 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);

	


alter TABLE pbandi_t_pagamento
add id_erogazione         NUMBER(8)  NULL ;

ALTER TABLE pbandi_t_pagamento
	ADD (CONSTRAINT  FK_PBANDI_T_EROGAZIONE_02 FOREIGN KEY (id_erogazione) REFERENCES pbandi_t_erogazione(id_erogazione) ON DELETE SET NULL);
	


CREATE TABLE pbandi_d_tipo_allegato
(
	id_tipo_allegato      NUMBER(3)  NOT NULL ,
	desc_breve_tipo_allegato  VARCHAR2(20)  NOT NULL ,
	desc_tipo_allegato    VARCHAR2(4000)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_allegato ON pbandi_d_tipo_allegato
(id_tipo_allegato  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_allegato
	ADD CONSTRAINT  PK_pbandi_d_tipo_allegato PRIMARY KEY (id_tipo_allegato);


CREATE TABLE pbandi_r_bando_caus_er_tip_all
(
	id_tipo_allegato      NUMBER(3)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	id_causale_erogazione  NUMBER(3)  NOT NULL ,
	id_bando              NUMBER(8)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_bando_caus_er_tip_ ON pbandi_r_bando_caus_er_tip_all
(id_bando  ASC,id_causale_erogazione  ASC,id_tipo_allegato  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_bando_caus_er_tip_all
	ADD CONSTRAINT  PK_pbandi_r_bando_caus_er_tip_ PRIMARY KEY (id_bando,id_causale_erogazione,id_tipo_allegato);



ALTER TABLE pbandi_r_bando_caus_er_tip_all
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_ALLEGATO_01 FOREIGN KEY (id_tipo_allegato) REFERENCES pbandi_d_tipo_allegato(id_tipo_allegato));



ALTER TABLE pbandi_r_bando_caus_er_tip_all
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_154 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_bando_caus_er_tip_all
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_155 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_bando_caus_er_tip_all
	ADD (CONSTRAINT  FK_PBANDI_D_CAUSALE_EROGAZI_01 FOREIGN KEY (id_causale_erogazione) REFERENCES pbandi_d_causale_erogazione(id_causale_erogazione));


drop table PBANDI_R_BANDO_TIPO_AIUTO cascade constraints;	

ALTER TABLE pbandi_r_bando_caus_er_tip_all
	ADD (CONSTRAINT  FK_PBANDI_T_BANDO_04 FOREIGN KEY (id_bando) REFERENCES pbandi_t_bando(id_bando));
	
	
CREATE TABLE pbandi_t_richiesta_erogazione
(
	id_richiesta_erogazione  NUMBER(8)  NOT NULL ,
	numero_richiesta_erogazione  NUMBER(8)  NOT NULL ,
	dt_richiesta_erogazione  DATE  NOT NULL ,
	importo_erogazione_richiesto  NUMBER(17,2)  NOT NULL ,
	id_progetto           NUMBER(8)  NOT NULL ,
	id_causale_erogazione  NUMBER(3)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_richiesta_erogazio ON pbandi_t_richiesta_erogazione
(id_richiesta_erogazione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_richiesta_erogazione
	ADD CONSTRAINT  PK_pbandi_t_richiesta_erogazio PRIMARY KEY (id_richiesta_erogazione);



ALTER TABLE pbandi_t_richiesta_erogazione
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_19 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_t_richiesta_erogazione
	ADD (CONSTRAINT  FK_PBANDI_D_CAUSALE_EROGAZI_05 FOREIGN KEY (id_causale_erogazione) REFERENCES pbandi_d_causale_erogazione(id_causale_erogazione));



ALTER TABLE pbandi_t_richiesta_erogazione
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_51 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_richiesta_erogazione
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_52 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);

create sequence seq_pbandi_t_richiesta_erogaz start with 1 nocache;

ALTER TABLE pbandi_r_soggetto_progetto
add id_utente_ins number(8);

ALTER TABLE pbandi_r_soggetto_progetto
add id_utente_agg number(8);

ALTER TABLE pbandi_r_soggetto_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_116 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_soggetto_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_117 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));


update pbandi_r_soggetto_progetto a 
set id_utente_ins = (select distinct b.id_utente_ins from PBANDI_R_SOGG_PROGETTO_SEDE b    
                    where a.PROGR_SOGGETTO_PROGETTO = b.PROGR_SOGGETTO_PROGETTO)
where id_utente_ins is null;

update pbandi_r_soggetto_progetto a 
set id_utente_ins = (select distinct b.id_utente_ins from pbandi_r_sogg_prog_sogg_correl b    
                    where a.PROGR_SOGGETTO_PROGETTO = b.PROGR_SOGGETTO_PROGETTO)
where id_utente_ins is null;



update pbandi_r_soggetto_progetto a 
set id_utente_agg = (select distinct b.id_utente_agg from PBANDI_R_SOGG_PROGETTO_SEDE b    
                    where a.PROGR_SOGGETTO_PROGETTO = b.PROGR_SOGGETTO_PROGETTO)
where id_utente_agg is null;

update pbandi_r_soggetto_progetto a 
set id_utente_agg = (select distinct b.id_utente_agg from pbandi_r_sogg_prog_sogg_correl b    
                    where a.PROGR_SOGGETTO_PROGETTO = b.PROGR_SOGGETTO_PROGETTO)
where id_utente_agg is null;

commit;

ALTER TABLE pbandi_r_soggetto_progetto
modify id_utente_ins not null;

alter table pbandi_t_raggruppamento
add SPESA_AMMESSA number(17,2);

CREATE TABLE TMP_RACCORDO_ATECO_2002_2007
(
  ID_ATECO_02       NUMBER,
  COD_CATEGORIA_02  VARCHAR2(20 BYTE),
  TIPO              VARCHAR2(20 BYTE),
  ID_ATECO_07       NUMBER,
  COD_CATEGORIA_07  VARCHAR2(20 BYTE),
  DT_LOAD           DATE
)
TABLESPACE PBANDI_SMALL_TBL;

CREATE TABLE TMP_SPESE_GEBARIC_GEFO
(
  CODICE_GEBARIC  VARCHAR2(10 BYTE),
  CODICE_SPESA    VARCHAR2(10 BYTE)
)
TABLESPACE PBANDI_SMALL_TBL;

ALTER TABLE pbandi_t_documento_di_spesa
ADD progr_fornitore_qualifica NUMBER(8);

ALTER TABLE pbandi_t_documento_di_spesa
	ADD (CONSTRAINT  FK_PBANDI_R_FORNITORE_QUALI_01 FOREIGN KEY (progr_fornitore_qualifica) REFERENCES pbandi_r_fornitore_qualifica(progr_fornitore_qualifica) ON DELETE SET NULL);

alter table pbandi_t_ente_competenza	
add desc_breve_ente varchar2(20);

alter table PBANDI_D_RUOLO_ENTE_COMPETENZA
add cod_igrue number(4);

alter TABLE pbandi_r_bando_sogg_finanziat	
add id_provincia          NUMBER(4);

alter TABLE pbandi_r_bando_sogg_finanziat	
add id_comune             NUMBER(8);

ALTER TABLE pbandi_r_bando_sogg_finanziat
	ADD (CONSTRAINT  FK_PBANDI_D_PROVINCIA_04 FOREIGN KEY (id_provincia) REFERENCES pbandi_d_provincia(id_provincia) ON DELETE SET NULL);



ALTER TABLE pbandi_r_bando_sogg_finanziat
	ADD (CONSTRAINT  FK_PBANDI_D_COMUNE_03 FOREIGN KEY (id_comune) REFERENCES pbandi_d_comune(id_comune) ON DELETE SET NULL);

drop table pbandi_r_piano_finanz_soggetto cascade constraints;	
drop table pbandi_t_piano_finanziario cascade constraints;

drop sequence seq_pbandi_t_piano_finanziario;

alter table pbandi_t_bando
drop (id_periodo);
	
alter table pbandi_r_conto_econom_mod_agev
add perc_importo_agevolato  NUMBER(5,2)  NULL ;	

CREATE TABLE pbandi_r_det_prop_cer_sogg_fin
(
	id_dett_proposta_certif  NUMBER(8)  NOT NULL ,
	id_tipo_sogg_finanziat  NUMBER(3)  NOT NULL ,
	perc_tipo_sogg_finanziatore  NUMBER(5,2)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_det_prop_cer_sogg_ ON pbandi_r_det_prop_cer_sogg_fin
(id_dett_proposta_certif  ASC,id_tipo_sogg_finanziat  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_det_prop_cer_sogg_fin
	ADD CONSTRAINT  PK_pbandi_r_det_prop_cer_sogg_ PRIMARY KEY (id_dett_proposta_certif,id_tipo_sogg_finanziat);



ALTER TABLE pbandi_r_det_prop_cer_sogg_fin
	ADD (CONSTRAINT  FK_PBANDI_T_DETT_PROP_CERT_01 FOREIGN KEY (id_dett_proposta_certif) REFERENCES pbandi_t_dett_proposta_certif(id_dett_proposta_certif));



ALTER TABLE pbandi_r_det_prop_cer_sogg_fin
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_SOGG_FINAN_02 FOREIGN KEY (id_tipo_sogg_finanziat) REFERENCES pbandi_d_tipo_sogg_finanziat(id_tipo_sogg_finanziat));



ALTER TABLE pbandi_r_det_prop_cer_sogg_fin
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_136 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_det_prop_cer_sogg_fin
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_137 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);
	
ALTER TABLE pbandi_d_attivita_ateco
ADD id_attivita_economica  NUMBER(3) ;

ALTER TABLE pbandi_d_attivita_ateco
	ADD (CONSTRAINT  FK_PBANDI_D_ATTIVITA_ECONOM_01 FOREIGN KEY (id_attivita_economica) REFERENCES pbandi_d_attivita_economica(id_attivita_economica));

alter table pbandi_t_documento_index
add nome_file varchar2(255);
	
alter table pbandi_r_doc_spesa_progetto
add note_validazione varchar2(4000);	

CREATE TABLE pbandi_r_linea_interv_mapping
(
	id_linea_di_intervento_attuale  NUMBER(3)  NOT NULL ,
	id_linea_di_intervento_migrata  NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_linea_interv_mappi ON pbandi_r_linea_interv_mapping
(id_linea_di_intervento_attuale  ASC,id_linea_di_intervento_migrata  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_linea_interv_mapping
	ADD CONSTRAINT  PK_pbandi_r_linea_interv_mappi PRIMARY KEY (id_linea_di_intervento_attuale,id_linea_di_intervento_migrata);



ALTER TABLE pbandi_r_linea_interv_mapping
	ADD (CONSTRAINT  FK_PBANDI_D_LINEA_INTERV_03 FOREIGN KEY (id_linea_di_intervento_attuale) REFERENCES pbandi_d_linea_di_intervento(id_linea_di_intervento));



ALTER TABLE pbandi_r_linea_interv_mapping
	ADD (CONSTRAINT  FK_PBANDI_D_LINEA_INTERV_11 FOREIGN KEY (id_linea_di_intervento_migrata) REFERENCES pbandi_d_linea_di_intervento(id_linea_di_intervento));

alter table pbandi_t_progetto
drop (iD_TIPO_AIUTO);

alter TABLE pbandi_t_domanda
add id_tipo_aiuto         NUMBER(3) ;

ALTER TABLE pbandi_t_domanda
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_AIUTO_02 FOREIGN KEY (id_tipo_aiuto) REFERENCES pbandi_d_tipo_aiuto(id_tipo_aiuto) ON DELETE SET NULL);
	
CREATE TABLE pbandi_r_bando_indicatori
(
	id_bando              NUMBER(8)  NOT NULL ,
	id_indicatori         NUMBER(3)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_bando_indicatori ON pbandi_r_bando_indicatori
(id_bando  ASC,id_indicatori  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_bando_indicatori
	ADD CONSTRAINT  PK_pbandi_r_bando_indicatori PRIMARY KEY (id_bando,id_indicatori);



ALTER TABLE pbandi_r_bando_indicatori
	ADD (CONSTRAINT  FK_PBANDI_T_BANDO_09 FOREIGN KEY (id_bando) REFERENCES pbandi_t_bando(id_bando));



ALTER TABLE pbandi_r_bando_indicatori
	ADD (CONSTRAINT  FK_PBANDI_D_INDICATORI_02 FOREIGN KEY (id_indicatori) REFERENCES pbandi_d_indicatori(id_indicatori));



ALTER TABLE pbandi_r_bando_indicatori
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_160 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_bando_indicatori
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_161 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



CREATE TABLE pbandi_r_bando_lin_tipo_aiuto
(
	id_tipo_aiuto         NUMBER(3)  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	progr_bando_linea_intervento  NUMBER(8)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_bando_lin_tipo_aiu ON pbandi_r_bando_lin_tipo_aiuto
(progr_bando_linea_intervento  ASC,id_tipo_aiuto  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_bando_lin_tipo_aiuto
	ADD CONSTRAINT  PK_pbandi_r_bando_lin_tipo_aiu PRIMARY KEY (progr_bando_linea_intervento,id_tipo_aiuto);



ALTER TABLE pbandi_r_bando_lin_tipo_aiuto
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_AIUTO_01 FOREIGN KEY (id_tipo_aiuto) REFERENCES pbandi_d_tipo_aiuto(id_tipo_aiuto));



ALTER TABLE pbandi_r_bando_lin_tipo_aiuto
	ADD (CONSTRAINT  FK_PBANDI_R_BANDO_LINEA_INT_04 FOREIGN KEY (progr_bando_linea_intervento) REFERENCES pbandi_r_bando_linea_intervent(progr_bando_linea_intervento));

CREATE TABLE pbandi_r_bando_lin_ind_ris_pro
(
	progr_bando_linea_intervento  NUMBER(8)  NOT NULL ,
	id_ind_risultato_program  NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_bando_lin_ind_ris_ ON pbandi_r_bando_lin_ind_ris_pro
(progr_bando_linea_intervento  ASC,id_ind_risultato_program  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_bando_lin_ind_ris_pro
	ADD CONSTRAINT  PK_pbandi_r_bando_lin_ind_ris_ PRIMARY KEY (progr_bando_linea_intervento,id_ind_risultato_program);



ALTER TABLE pbandi_r_bando_lin_ind_ris_pro
	ADD (CONSTRAINT  FK_PBANDI_R_BANDO_LINEA_INT_05 FOREIGN KEY (progr_bando_linea_intervento) REFERENCES pbandi_r_bando_linea_intervent(progr_bando_linea_intervento));



ALTER TABLE pbandi_r_bando_lin_ind_ris_pro
	ADD (CONSTRAINT  FK_PBANDI_D_IND_RISULT_PROG_01 FOREIGN KEY (id_ind_risultato_program) REFERENCES pbandi_d_ind_risultato_program(id_ind_risultato_program));

alter table pbandi_t_progetto
add id_ind_risultato_program   NUMBER(3)  NULL ;	

ALTER TABLE pbandi_t_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_IND_RISULT_PROG_02 FOREIGN KEY (id_ind_risultato_program) REFERENCES pbandi_d_ind_risultato_program(id_ind_risultato_program) ON DELETE SET NULL);
	
CREATE TABLE pbandi_r_bando_lin_indicat_qsn
(
	progr_bando_linea_intervento  NUMBER(8)  NOT NULL ,
	id_indicatore_qsn     NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_bando_lin_indicat_ ON pbandi_r_bando_lin_indicat_qsn
(progr_bando_linea_intervento  ASC,id_indicatore_qsn  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_bando_lin_indicat_qsn
	ADD CONSTRAINT  PK_pbandi_r_bando_lin_indicat_ PRIMARY KEY (progr_bando_linea_intervento,id_indicatore_qsn);



ALTER TABLE pbandi_r_bando_lin_indicat_qsn
	ADD (CONSTRAINT  FK_PBANDI_R_BANDO_LINEA_INT_07 FOREIGN KEY (progr_bando_linea_intervento) REFERENCES pbandi_r_bando_linea_intervent(progr_bando_linea_intervento));



ALTER TABLE pbandi_r_bando_lin_indicat_qsn
	ADD (CONSTRAINT  FK_PBANDI_D_INDICATORE_QSN_01 FOREIGN KEY (id_indicatore_qsn) REFERENCES pbandi_d_indicatore_qsn(id_indicatore_qsn));


CREATE TABLE pbandi_c_cert_counter
(
	msgid                 VARCHAR2(500)  NOT NULL ,
	targetid              VARCHAR2(100)  NOT NULL ,
	num_attempts          NUMBER  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_c_cert_counter ON pbandi_c_cert_counter
(msgid  ASC,targetid  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_c_cert_counter
	ADD CONSTRAINT  PK_pbandi_c_cert_counter PRIMARY KEY (msgid,targetid);



CREATE TABLE pbandi_c_wsbo_counter
(
	msgid                 VARCHAR2(500)  NOT NULL ,
	targetid              VARCHAR2(100)  NOT NULL ,
	num_attempts          NUMBER  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_c_wsbo_counter ON pbandi_c_wsbo_counter
(msgid  ASC,targetid  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_c_wsbo_counter
	ADD CONSTRAINT  PK_pbandi_c_wsbo_counter PRIMARY KEY (msgid,targetid);

drop sequence seq_pbandi_t_recupero;

drop table PBANDI_T_RECUPERO cascade constraints;

alter table pbandi_r_pag_quot_parte_doc_sp
drop (flag_monit);

ALTER TABLE pbandi_t_bando
ADD id_tipologia_attivazione  NUMBER(3)  ;

ALTER TABLE pbandi_t_bando
	ADD (CONSTRAINT  FK_PBANDI_D_TIPOLOGIA_ATTIV_01 FOREIGN KEY (id_tipologia_attivazione) REFERENCES pbandi_d_tipologia_attivazione(id_tipologia_attivazione));

alter table pbandi_t_documento_index
add note_documento_index varchar2(255);

CREATE TABLE pbandi_c_versione
(
	versione_db           VARCHAR2(20)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;

alter table pbandi_d_causale_erogazione
add id_tipo_trattamento   NUMBER(3);

ALTER TABLE pbandi_d_causale_erogazione
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_TRATTAMENT_03 FOREIGN KEY (id_tipo_trattamento) REFERENCES pbandi_d_tipo_trattamento(id_tipo_trattamento) ON DELETE SET NULL);

create sequence seq_pbandi_r_pag_qu_par_doc_sp start with 1 nocache;
	
alter table pbandi_r_pag_quot_parte_doc_sp
drop constraints PK_pbandi_r_pag_quot_parte_doc;

drop index PK_pbandi_r_pag_quot_parte_doc;

alter table pbandi_r_pag_quot_parte_doc_sp
add progr_pag_quot_parte_doc_sp number(8);

update pbandi_r_pag_quot_parte_doc_sp
set progr_pag_quot_parte_doc_sp = seq_pbandi_r_pag_qu_par_doc_sp.nextval;

commit;

alter table pbandi_r_pag_quot_parte_doc_sp
modify progr_pag_quot_parte_doc_sp not null;

CREATE UNIQUE INDEX PK_pbandi_r_pag_quot_parte_doc ON pbandi_r_pag_quot_parte_doc_sp
(progr_pag_quot_parte_doc_sp  ASC)
	TABLESPACE PBANDI_SMALL_IDX;

ALTER TABLE pbandi_r_pag_quot_parte_doc_sp
	ADD CONSTRAINT  PK_pbandi_r_pag_quot_parte_doc PRIMARY KEY (progr_pag_quot_parte_doc_sp);

CREATE TABLE pbandi_r_dett_prop_cer_fideius
(
	id_dett_proposta_certif  NUMBER(8)  NOT NULL ,
	id_fideiussione       NUMBER(8)  NOT NULL ,
	importo_documento     NUMBER(13,2)  NOT NULL ,
	importo_utilizzato    NUMBER(13,2)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_dett_prop_cer_fide ON pbandi_r_dett_prop_cer_fideius
(id_dett_proposta_certif  ASC,id_fideiussione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_dett_prop_cer_fideius
	ADD CONSTRAINT  PK_pbandi_r_dett_prop_cer_fide PRIMARY KEY (id_dett_proposta_certif,id_fideiussione);



ALTER TABLE pbandi_r_dett_prop_cer_fideius
	ADD (CONSTRAINT  FK_PBANDI_T_DETT_PROP_CERT_02 FOREIGN KEY (id_dett_proposta_certif) REFERENCES pbandi_t_dett_proposta_certif(id_dett_proposta_certif));



ALTER TABLE pbandi_r_dett_prop_cer_fideius
	ADD (CONSTRAINT  FK_PBANDI_T_FIDEIUSSIONE_01 FOREIGN KEY (id_fideiussione) REFERENCES pbandi_t_fideiussione(id_fideiussione));



ALTER TABLE pbandi_r_dett_prop_cer_fideius
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_140 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_dett_prop_cer_fideius
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_141 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);

CREATE TABLE pbandi_r_det_prop_cer_qp_doc
(
	id_dett_proposta_certif  NUMBER(8)  NOT NULL ,
	progr_pag_quot_parte_doc_sp  NUMBER(8)  NOT NULL ,
	importo_documento     NUMBER(13,2)  NOT NULL ,
	importo_utilizzato    NUMBER(13,2)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_det_prop_cer_qp_do ON pbandi_r_det_prop_cer_qp_doc
(id_dett_proposta_certif  ASC,progr_pag_quot_parte_doc_sp  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_det_prop_cer_qp_doc
	ADD CONSTRAINT  PK_pbandi_r_det_prop_cer_qp_do PRIMARY KEY (id_dett_proposta_certif,progr_pag_quot_parte_doc_sp);



ALTER TABLE pbandi_r_det_prop_cer_qp_doc
	ADD (CONSTRAINT  FK_PBANDI_T_DETT_PROP_CERT_03 FOREIGN KEY (id_dett_proposta_certif) REFERENCES pbandi_t_dett_proposta_certif(id_dett_proposta_certif));



ALTER TABLE pbandi_r_det_prop_cer_qp_doc
	ADD (CONSTRAINT  FK_PBANDI_R_QP_DOC_SPESA_01 FOREIGN KEY (progr_pag_quot_parte_doc_sp) REFERENCES pbandi_r_pag_quot_parte_doc_sp(progr_pag_quot_parte_doc_sp));



ALTER TABLE pbandi_r_det_prop_cer_qp_doc
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_150 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_det_prop_cer_qp_doc
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_151 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);

CREATE TABLE pbandi_r_dett_prop_cert_erogaz
(
	id_dett_proposta_certif  NUMBER(8)  NOT NULL ,
	id_erogazione         NUMBER(8)  NOT NULL ,
	importo_documento     NUMBER(13,2)  NOT NULL ,
	importo_utilizzato    NUMBER(13,2)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_dett_prop_cert_ero ON pbandi_r_dett_prop_cert_erogaz
(id_dett_proposta_certif  ASC,id_erogazione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_dett_prop_cert_erogaz
	ADD CONSTRAINT  PK_pbandi_r_dett_prop_cert_ero PRIMARY KEY (id_dett_proposta_certif,id_erogazione);



ALTER TABLE pbandi_r_dett_prop_cert_erogaz
	ADD (CONSTRAINT  FK_PBANDI_T_DETT_PROP_CERT_04 FOREIGN KEY (id_dett_proposta_certif) REFERENCES pbandi_t_dett_proposta_certif(id_dett_proposta_certif));



ALTER TABLE pbandi_r_dett_prop_cert_erogaz
	ADD (CONSTRAINT  FK_PBANDI_T_EROGAZIONE_01 FOREIGN KEY (id_erogazione) REFERENCES pbandi_t_erogazione(id_erogazione));



ALTER TABLE pbandi_r_dett_prop_cert_erogaz
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_33 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_dett_prop_cert_erogaz
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_34 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);

CREATE TABLE pbandi_d_strumento_attuativo
(
	id_strumento_attuativo  NUMBER(3)  NOT NULL ,
	tipologia             VARCHAR2(255)  NOT NULL ,
	desc_strumento_attuativo  VARCHAR2(255)  NOT NULL ,
	responsabile          VARCHAR2(255)  NOT NULL ,
	cod_igrue_t21         VARCHAR2(20)  NOT NULL ,
	dt_approvazione       DATE  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_strumento_attuativ ON pbandi_d_strumento_attuativo
(id_strumento_attuativo  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_strumento_attuativo
	ADD CONSTRAINT  PK_pbandi_d_strumento_attuativ PRIMARY KEY (id_strumento_attuativo);


ALTER TABLE pbandi_d_linea_di_intervento
ADD id_strumento_attuativo  NUMBER(3);

ALTER TABLE pbandi_d_linea_di_intervento
	ADD (CONSTRAINT  FK_PBANDI_D_STRUMENTO_ATTUA_01 FOREIGN KEY (id_strumento_attuativo) REFERENCES pbandi_d_strumento_attuativo(id_strumento_attuativo) ON DELETE SET NULL);

ALTER TABLE pbandi_d_stato_progetto
ADD flag_certificazione   CHAR(1)  NULL	;

ALTER TABLE pbandi_d_stato_progetto
	MODIFY flag_certificazione CONSTRAINT  CK_FLAG_16 CHECK (flag_certificazione in ('S','N'));

alter table pbandi_t_bando
modify PUNTEGGIO_PREMIALITA number(17,2);

CREATE OR REPLACE TYPE objIdVoceSpesaFiglia AS OBJECT
(
  IdVoceSpesaFiglia  number
);
/

CREATE OR REPLACE TYPE LISTVOCESPESAFIGLIA AS TABLE OF objIdVoceSpesaFiglia;
/

CREATE TABLE TMP_NUM_DOMANDA_GEFO
(PROGR_BANDO_LINEA_INTERVENTO NUMBER(8),	
 COD_FONDO_GEFO               VARCHAR2(4))
	TABLESPACE PBANDI_SMALL_TBL;

CREATE OR REPLACE TYPE objIdCaricamento AS OBJECT
(
  IdCaricamento  number
);
/

CREATE OR REPLACE TYPE LISTCaricamento  AS TABLE OF objIdCaricamento ;
/

alter table pbandi_t_documento
modify numero_documento varchar2(40);

alter table pbandi_r_det_prop_cer_qp_doc
add id_dichiarazione_spesa  NUMBER(8)  NOT NULL;

ALTER TABLE pbandi_r_det_prop_cer_qp_doc
	ADD (CONSTRAINT  FK_PBANDI_T_DICHIARAZ_SPESA_02 FOREIGN KEY (id_dichiarazione_spesa) REFERENCES pbandi_t_dichiarazione_spesa(id_dichiarazione_spesa));


	