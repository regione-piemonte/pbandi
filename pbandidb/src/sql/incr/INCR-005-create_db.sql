/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


CREATE SEQUENCE seq_pbandi_t_csp_prog_fase_mon
	START WITH 1
	NOCACHE;


CREATE SEQUENCE seq_pbandi_t_csp_progetto
	START WITH 1
	NOCACHE;


CREATE SEQUENCE seq_pbandi_t_csp_soggetto
	START WITH 1
	NOCACHE;

create sequence seq_pbandi_t_csp_prog_sede_int
START WITH 1
	NOCACHE;
	
create sequence seq_pbandi_t_csp_sogg_ruolo_en	
START WITH 1
	NOCACHE;

CREATE TABLE pbandi_d_motivo_scostamento
(
	id_motivo_scostamento  NUMBER(3)  NOT NULL ,
	cod_igrue_T37_T49_T53   NUMBER(2)  NOT NULL ,
	desc_motivo_scostamento  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_motivo_scostamento ON pbandi_d_motivo_scostamento
(id_motivo_scostamento  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_motivo_scostamento
	ADD CONSTRAINT  PK_pbandi_d_motivo_scostamento PRIMARY KEY (id_motivo_scostamento);
	
	
CREATE TABLE pbandi_t_csp_prog_fase_monit
(
	id_csp_prog_fase_monit  NUMBER  NOT NULL ,
	dt_inizio_prevista    DATE  NULL ,
	dt_fine_prevista      DATE  NULL ,
	dt_inizio_effettiva   DATE  NULL ,
	dt_fine_effettiva     DATE  NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_elaborazione       DATE  NULL ,
	id_csp_progetto       NUMBER  NOT NULL ,
	id_fase_monit         NUMBER(3)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	id_motivo_scostamento  NUMBER(3)  NULL
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_csp_prog_fase_moni ON pbandi_t_csp_prog_fase_monit
(id_csp_prog_fase_monit  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_csp_prog_fase_monit
	ADD CONSTRAINT  PK_pbandi_t_csp_prog_fase_moni PRIMARY KEY (id_csp_prog_fase_monit);




CREATE TABLE pbandi_t_csp_progetto
(
	id_csp_progetto       NUMBER  NOT NULL ,
	titolo_progetto       VARCHAR2(255)  NOT NULL ,
	dt_concessione        DATE  NOT NULL,
	dt_comitato           DATE  NULL ,
	flag_cardine          CHAR(1)  NOT NULL ,
	flag_generatore_entrate  CHAR(1)  NOT NULL ,
	flag_legge_obbiettivo  CHAR(1)  NOT NULL ,
	flag_altro_fondo      CHAR(1)  NOT NULL ,
	stato_programma       VARCHAR2(1)   DEFAULT  '1' NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_elaborazione       DATE  NULL ,
	id_tipo_operazione    NUMBER(3)  NOT NULL ,
	id_tipo_aiuto         NUMBER(3)  NULL ,
	id_tipologia_cipe     NUMBER(3)  NULL ,
	id_progetto           NUMBER(8)  NULL ,
	progr_bando_linea_intervento  NUMBER(8)  NOT NULL ,
	id_obiettivo_specif_qsn  NUMBER(3)  NULL ,
	id_settore_cpt        NUMBER(3)  NULL ,
	id_tema_prioritario   NUMBER(3)  NULL ,
	id_ind_risultato_program  NUMBER(3)  NULL ,
	id_indicatore_qsn     NUMBER(3)  NULL ,
	id_categoria_cipe     NUMBER(3)  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	numero_domanda		  VARCHAR2(20),
	id_attivita_ateco     NUMBER(4)  NULL,
	flag_avviabile        CHAR(1)  NOT NULL,
	cup					  VARCHAR2(20),
	id_strumento_attuativo  NUMBER(3)  NULL,
	id_dimensione_territor  NUMBER(3)
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_csp_progetto ON pbandi_t_csp_progetto
(id_csp_progetto  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_t_csp_progetto
	ADD CONSTRAINT  PK_pbandi_t_csp_progetto PRIMARY KEY (id_csp_progetto);


ALTER TABLE pbandi_t_csp_progetto
	MODIFY flag_cardine CONSTRAINT  ck_flag_24 CHECK (flag_cardine in ('S','N'));


ALTER TABLE pbandi_t_csp_progetto
	MODIFY flag_generatore_entrate CONSTRAINT  ck_flag_25 CHECK (flag_generatore_entrate in ('S','N'));


ALTER TABLE pbandi_t_csp_progetto
	MODIFY flag_legge_obbiettivo CONSTRAINT  ck_flag_26 CHECK (flag_legge_obbiettivo in ('S','N'));


ALTER TABLE pbandi_t_csp_progetto
	MODIFY flag_altro_fondo CONSTRAINT  ck_flag_27 CHECK (flag_altro_fondo in ('S','N'));


ALTER TABLE pbandi_t_csp_progetto
	MODIFY flag_avviabile CONSTRAINT  ck_flag_30 CHECK (flag_avviabile in ('S','N'));	
	

ALTER TABLE pbandi_t_csp_progetto
	MODIFY flag_cardine DEFAULT 'N';


ALTER TABLE pbandi_t_csp_progetto
	MODIFY flag_generatore_entrate DEFAULT 'N';


ALTER TABLE pbandi_t_csp_progetto
	MODIFY flag_legge_obbiettivo DEFAULT 'N';


ALTER TABLE pbandi_t_csp_progetto
	MODIFY flag_altro_fondo DEFAULT 'N';


ALTER TABLE pbandi_t_csp_progetto
	MODIFY flag_avviabile DEFAULT 'N';


CREATE TABLE pbandi_t_csp_soggetto
(
	id_csp_soggetto       NUMBER  NOT NULL ,
	codice_fiscale        VARCHAR2(32)  NULL ,
	denominazione         VARCHAR2(150)  NULL ,
	cognome               VARCHAR2(150)  NULL ,
	nome                  VARCHAR2(150)  NULL ,
	dt_nascita            DATE  NULL ,
	sesso                 VARCHAR2(1)  NULL ,
	partita_iva_sede_legale  VARCHAR2(20)  NULL ,
	indirizzo_sede_legale  VARCHAR2(255)  NULL ,
	cap_sede_legale       VARCHAR2(5)  NULL ,
	email_sede_legale     VARCHAR2(128)  NULL ,
	fax_sede_legale       VARCHAR2(20)  NULL ,
	telefono_sede_legale  VARCHAR2(20)  NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_elaborazione       DATE  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	id_forma_giuridica    NUMBER(3)  NULL ,
	id_attivita_ateco     NUMBER(4)  NULL ,
	id_dimensione_impresa  NUMBER(3)  NULL ,
	id_comune_estero_sede_legale  NUMBER(8)  NULL ,
	id_comune_italiano_sede_legale  NUMBER(8)  NULL ,
	id_tipo_anagrafica    NUMBER(3)  NULL ,
	id_tipo_soggetto      NUMBER(3)  NOT NULL ,
	id_tipo_soggetto_correlato  NUMBER(3)  NULL ,
	id_tipo_beneficiario  NUMBER(3)  NULL ,
	indirizzo_pf          VARCHAR2(255)  NULL ,
	cap_pf                VARCHAR2(5)  NULL ,
	email_pf              VARCHAR2(128)  NULL ,
	fax_pf                VARCHAR2(20)  NULL ,
	telefono_pf           VARCHAR2(20)  NULL ,
	id_comune_italiano_nascita  NUMBER(8)  NULL ,
	id_comune_estero_nascita  NUMBER(8)  NULL ,
	id_comune_italiano_residenza  NUMBER(8)  NULL ,
	id_comune_estero_residenza  NUMBER(8)  NULL,
	iban					  VARCHAR2(30)  NULL,
	id_csp_progetto       NUMBER  NOT NULL
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_csp_soggetto ON pbandi_t_csp_soggetto
(id_csp_soggetto  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_t_csp_soggetto
	ADD CONSTRAINT  PK_pbandi_t_csp_soggetto PRIMARY KEY (id_csp_soggetto);


CREATE TABLE pbandi_w_csp_costanti
(
	progr_bando_linea_intervento  NUMBER(8)  NOT NULL ,
	attributo             VARCHAR2(255)  NULL ,
	valore                VARCHAR2(255)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;





ALTER TABLE pbandi_t_csp_prog_fase_monit
	ADD (CONSTRAINT  FK_PBANDI_T_CSP_PROGETTO_04 FOREIGN KEY (id_csp_progetto) REFERENCES pbandi_t_csp_progetto(id_csp_progetto));



ALTER TABLE pbandi_t_csp_prog_fase_monit
	ADD (CONSTRAINT  FK_PBANDI_D_FASE_MONIT_02 FOREIGN KEY (id_fase_monit) REFERENCES pbandi_d_fase_monit(id_fase_monit));



ALTER TABLE pbandi_t_csp_prog_fase_monit
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_172 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_csp_prog_fase_monit
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_173 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));

ALTER TABLE pbandi_t_csp_prog_fase_monit
	ADD (CONSTRAINT  FK_PBANDI_D_MOTIVO_SCOSTAME_04 FOREIGN KEY (id_motivo_scostamento) REFERENCES pbandi_d_motivo_scostamento(id_motivo_scostamento) );
	
	
ALTER TABLE pbandi_t_csp_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_SETTORE_CPT_02 FOREIGN KEY (id_settore_cpt) REFERENCES pbandi_d_settore_cpt(id_settore_cpt) );



ALTER TABLE pbandi_t_csp_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_TEMA_PRIORITAR_03 FOREIGN KEY (id_tema_prioritario) REFERENCES pbandi_d_tema_prioritario(id_tema_prioritario) );



ALTER TABLE pbandi_t_csp_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_IND_RISULT_PROG_03 FOREIGN KEY (id_ind_risultato_program) REFERENCES pbandi_d_ind_risultato_program(id_ind_risultato_program) );



ALTER TABLE pbandi_t_csp_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_INDICATORE_QSN_03 FOREIGN KEY (id_indicatore_qsn) REFERENCES pbandi_d_indicatore_qsn(id_indicatore_qsn) );



ALTER TABLE pbandi_t_csp_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_CATEGORIA_CIPE_03 FOREIGN KEY (id_categoria_cipe) REFERENCES pbandi_d_categoria_cipe(id_categoria_cipe) );



ALTER TABLE pbandi_t_csp_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_73 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_csp_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_74 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) );


ALTER TABLE pbandi_t_csp_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_OPERAZIONE_04 FOREIGN KEY (id_tipo_operazione) REFERENCES pbandi_d_tipo_operazione(id_tipo_operazione));



ALTER TABLE pbandi_t_csp_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_AIUTO_03 FOREIGN KEY (id_tipo_aiuto) REFERENCES pbandi_d_tipo_aiuto(id_tipo_aiuto) );



ALTER TABLE pbandi_t_csp_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_TIPOLOGIA_CIPE_03 FOREIGN KEY (id_tipologia_cipe) REFERENCES pbandi_d_tipologia_cipe(id_tipologia_cipe) );



ALTER TABLE pbandi_t_csp_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_11 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto) );



ALTER TABLE pbandi_t_csp_progetto
	ADD (CONSTRAINT  FK_PBANDI_R_BANDO_LINEA_INT_10 FOREIGN KEY (progr_bando_linea_intervento) REFERENCES pbandi_r_bando_linea_intervent(progr_bando_linea_intervento));



ALTER TABLE pbandi_t_csp_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_OBIETTIVO_SPECI_03 FOREIGN KEY (id_obiettivo_specif_qsn) REFERENCES pbandi_d_obiettivo_specif_qsn(id_obiettivo_specif_qsn) );

ALTER TABLE pbandi_t_csp_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_STRUMENTO_ATTUA_02 FOREIGN KEY (id_strumento_attuativo) REFERENCES pbandi_d_strumento_attuativo(id_strumento_attuativo) );

ALTER TABLE pbandi_t_csp_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_DIMENSIONE_TERR_02 FOREIGN KEY (id_dimensione_territor) REFERENCES pbandi_d_dimensione_territor(id_dimensione_territor) );	
	
ALTER TABLE pbandi_t_csp_soggetto
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_SOGG_CORRE_02 FOREIGN KEY (id_tipo_soggetto_correlato) REFERENCES pbandi_d_tipo_sogg_correlato(id_tipo_soggetto_correlato) );



ALTER TABLE pbandi_t_csp_soggetto
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_BENEFIC_04 FOREIGN KEY (id_tipo_beneficiario) REFERENCES pbandi_d_tipo_beneficiario(id_tipo_beneficiario) );



ALTER TABLE pbandi_t_csp_soggetto
	ADD (CONSTRAINT  FK_PBANDI_D_COMUNE_06 FOREIGN KEY (id_comune_italiano_nascita) REFERENCES pbandi_d_comune(id_comune) );



ALTER TABLE pbandi_t_csp_soggetto
	ADD (CONSTRAINT  FK_PBANDI_D_COMUNE_ESTERO_05 FOREIGN KEY (id_comune_estero_nascita) REFERENCES pbandi_d_comune_estero(id_comune_estero) );



ALTER TABLE pbandi_t_csp_soggetto
	ADD (CONSTRAINT  FK_PBANDI_D_COMUNE_07 FOREIGN KEY (id_comune_italiano_residenza) REFERENCES pbandi_d_comune(id_comune) );



ALTER TABLE pbandi_t_csp_soggetto
	ADD (CONSTRAINT  FK_PBANDI_D_COMUNE_ESTERO_06 FOREIGN KEY (id_comune_estero_residenza) REFERENCES pbandi_d_comune_estero(id_comune_estero) );



ALTER TABLE pbandi_t_csp_soggetto
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_35 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_csp_soggetto
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_36 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) );



ALTER TABLE pbandi_t_csp_soggetto
	ADD (CONSTRAINT  FK_PBANDI_D_FORMA_GIURIDICA_04 FOREIGN KEY (id_forma_giuridica) REFERENCES pbandi_d_forma_giuridica(id_forma_giuridica) );



ALTER TABLE pbandi_t_csp_soggetto
	ADD (CONSTRAINT  FK_PBANDI_D_ATTIVITA_ATECO_03 FOREIGN KEY (id_attivita_ateco) REFERENCES pbandi_d_attivita_ateco(id_attivita_ateco) );



ALTER TABLE pbandi_t_csp_soggetto
	ADD (CONSTRAINT  FK_PBANDI_D_DIMENSIONE_IMPR_03 FOREIGN KEY (id_dimensione_impresa) REFERENCES pbandi_d_dimensione_impresa(id_dimensione_impresa) );



ALTER TABLE pbandi_t_csp_soggetto
	ADD (CONSTRAINT  FK_PBANDI_D_COMUNE_ESTERO_03 FOREIGN KEY (id_comune_estero_sede_legale) REFERENCES pbandi_d_comune_estero(id_comune_estero) );



ALTER TABLE pbandi_t_csp_soggetto
	ADD (CONSTRAINT  FK_PBANDI_D_COMUNE_04 FOREIGN KEY (id_comune_italiano_sede_legale) REFERENCES pbandi_d_comune(id_comune) );



ALTER TABLE pbandi_t_csp_soggetto
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_ANAGRAFICA_05 FOREIGN KEY (id_tipo_anagrafica) REFERENCES pbandi_d_tipo_anagrafica(id_tipo_anagrafica) );



ALTER TABLE pbandi_t_csp_soggetto
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_SOGGETTO_03 FOREIGN KEY (id_tipo_soggetto) REFERENCES pbandi_d_tipo_soggetto(id_tipo_soggetto));


ALTER TABLE pbandi_t_csp_soggetto
	ADD (CONSTRAINT  FK_PBANDI_T_CSP_PROGETTO_06 FOREIGN KEY (id_csp_progetto) REFERENCES pbandi_t_csp_progetto(id_csp_progetto));	
	
ALTER TABLE pbandi_w_csp_costanti
	ADD (CONSTRAINT  FK_PBANDI_R_BANDO_LINEA_INT_11 FOREIGN KEY (progr_bando_linea_intervento) REFERENCES pbandi_r_bando_linea_intervent(progr_bando_linea_intervento));

ALTER TABLE pbandi_t_csp_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_ATTIVITA_ATECO_04 FOREIGN KEY (id_attivita_ateco) REFERENCES pbandi_d_attivita_ateco(id_attivita_ateco) );


CREATE SEQUENCE seq_pbandi_t_csp_prog_indicato
	START WITH 1
	NOCACHE;

CREATE TABLE pbandi_t_csp_prog_indicatori
(
	id_csp_prog_indicatori  NUMBER  NOT NULL ,
	valore_prog_iniziale  NUMBER(13,2)  NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_elaborazione       DATE  NULL ,
	id_csp_progetto       NUMBER  NOT NULL ,
	id_indicatori         NUMBER(3)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_csp_prog_indicator ON pbandi_t_csp_prog_indicatori
(id_csp_prog_indicatori  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_csp_prog_indicatori
	ADD CONSTRAINT  PK_pbandi_t_csp_prog_indicator PRIMARY KEY (id_csp_prog_indicatori);



ALTER TABLE pbandi_t_csp_prog_indicatori
	ADD (CONSTRAINT  FK_PBANDI_T_CSP_PROGETTO_05 FOREIGN KEY (id_csp_progetto) REFERENCES pbandi_t_csp_progetto(id_csp_progetto));



ALTER TABLE pbandi_t_csp_prog_indicatori
	ADD (CONSTRAINT  FK_PBANDI_D_INDICATORI_03 FOREIGN KEY (id_indicatori) REFERENCES pbandi_d_indicatori(id_indicatori));



ALTER TABLE pbandi_t_csp_prog_indicatori
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_174 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_csp_prog_indicatori
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_175 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) );
	
alter TABLE pbandi_t_dati_progetto_monit
add flag_stato_fas   CHAR(1) default '1' NOT NULL;

alter table pbandi_t_indirizzo
modify desc_indirizzo null;

CREATE TABLE pbandi_t_csp_prog_sede_interv
(
	id_csp_prog_sede_interv  NUMBER  NOT NULL ,
	partita_iva           VARCHAR2(20)  NULL ,
	indirizzo             VARCHAR2(255)  NULL ,
	cap                   VARCHAR2(5)  NULL ,
	email                 VARCHAR2(128)  NULL ,
	fax                   VARCHAR2(20)  NULL ,
	telefono              VARCHAR2(20)  NULL ,
	id_csp_progetto       NUMBER  NOT NULL ,
	id_comune_estero      NUMBER(8)  NULL ,
	id_comune             NUMBER(8)  NULL ,
	id_provincia          NUMBER(4)  NULL ,
	id_regione            NUMBER(3)  NULL ,
	id_nazione            NUMBER(6)  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	dt_inizio_validita		DATE NOT NULL,
	dt_elaborazione       DATE  NULL
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_csp_prog_sede_inte ON pbandi_t_csp_prog_sede_interv
(id_csp_prog_sede_interv  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_csp_prog_sede_interv
	ADD CONSTRAINT  PK_pbandi_t_csp_prog_sede_inte PRIMARY KEY (id_csp_prog_sede_interv);



ALTER TABLE pbandi_t_csp_prog_sede_interv
	ADD (CONSTRAINT  FK_PBANDI_T_CSP_PROGETTO_01 FOREIGN KEY (id_csp_progetto) REFERENCES pbandi_t_csp_progetto(id_csp_progetto));



ALTER TABLE pbandi_t_csp_prog_sede_interv
	ADD (CONSTRAINT  FK_PBANDI_D_COMUNE_ESTERO_07 FOREIGN KEY (id_comune_estero) REFERENCES pbandi_d_comune_estero(id_comune_estero) );



ALTER TABLE pbandi_t_csp_prog_sede_interv
	ADD (CONSTRAINT  FK_PBANDI_D_COMUNE_08 FOREIGN KEY (id_comune) REFERENCES pbandi_d_comune(id_comune) );



ALTER TABLE pbandi_t_csp_prog_sede_interv
	ADD (CONSTRAINT  FK_PBANDI_D_PROVINCIA_05 FOREIGN KEY (id_provincia) REFERENCES pbandi_d_provincia(id_provincia) );



ALTER TABLE pbandi_t_csp_prog_sede_interv
	ADD (CONSTRAINT  FK_PBANDI_D_REGIONE_04 FOREIGN KEY (id_regione) REFERENCES pbandi_d_regione(id_regione) );



ALTER TABLE pbandi_t_csp_prog_sede_interv
	ADD (CONSTRAINT  FK_PBANDI_D_NAZIONE_04 FOREIGN KEY (id_nazione) REFERENCES pbandi_d_nazione(id_nazione) );



ALTER TABLE pbandi_t_csp_prog_sede_interv
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_166 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_csp_prog_sede_interv
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_167 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) );
	
alter TABLE pbandi_t_progetto	
add id_settore_cpt        NUMBER(3)  NULL ;
	
alter TABLE pbandi_t_progetto	
add id_strumento_attuativo  NUMBER(3)  NULL ;

ALTER TABLE pbandi_t_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_STRUMENTO_ATTUA_03 FOREIGN KEY (id_strumento_attuativo) REFERENCES pbandi_d_strumento_attuativo(id_strumento_attuativo) );


ALTER TABLE pbandi_t_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_SETTORE_CPT_03 FOREIGN KEY (id_settore_cpt) REFERENCES pbandi_d_settore_cpt(id_settore_cpt) );

alter TABLE pbandi_t_sede
add id_dimensione_territor  NUMBER(3);

ALTER TABLE pbandi_t_sede
	ADD (CONSTRAINT  FK_PBANDI_D_DIMENSIONE_TERR_03 FOREIGN KEY (id_dimensione_territor) REFERENCES pbandi_d_dimensione_territor(id_dimensione_territor) );

CREATE TABLE pbandi_t_csp_sogg_ruolo_ente
(
	id_csp_sogg_ruolo_ente  NUMBER  NOT NULL ,
	id_csp_soggetto       NUMBER  NOT NULL ,
	id_ruolo_ente_competenza  NUMBER(3)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	dt_inizio_validita		DATE NOT NULL,
	dt_elaborazione       DATE  NULL
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_csp_sogg_ruolo_ent ON pbandi_t_csp_sogg_ruolo_ente
(id_csp_sogg_ruolo_ente  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_csp_sogg_ruolo_ente
	ADD CONSTRAINT  PK_pbandi_t_csp_sogg_ruolo_ent PRIMARY KEY (id_csp_sogg_ruolo_ente);



ALTER TABLE pbandi_t_csp_sogg_ruolo_ente
	ADD (CONSTRAINT  FK_PBANDI_T_CSP_SOGGETTO_01 FOREIGN KEY (id_csp_soggetto) REFERENCES pbandi_t_csp_soggetto(id_csp_soggetto));



ALTER TABLE pbandi_t_csp_sogg_ruolo_ente
	ADD (CONSTRAINT  FK_PBANDI_D_RUOLO_ENTE_COMP_02 FOREIGN KEY (id_ruolo_ente_competenza) REFERENCES pbandi_d_ruolo_ente_competenza(id_ruolo_ente_competenza));



ALTER TABLE pbandi_t_csp_sogg_ruolo_ente
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_168 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_csp_sogg_ruolo_ente
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_169 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) );

alter TABLE pbandi_d_ruolo_ente_competenza
add flag_visibile         CHAR(1)  DEFAULT  'N'  NOT NULL  CONSTRAINT  ck_flag_31 CHECK (flag_visibile in ('S','N'));	

alter TABLE pbandi_d_tipo_sogg_correlato	
add flag_visibile         CHAR(1)  DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_32 CHECK (flag_visibile in ('S','N'));	

alter TABLE pbandi_r_soggetti_correlati
add id_ruolo_ente_competenza  NUMBER(3);

ALTER TABLE pbandi_r_soggetti_correlati
	ADD (CONSTRAINT  FK_PBANDI_D_RUOLO_ENTE_COMP_03 FOREIGN KEY (id_ruolo_ente_competenza) REFERENCES pbandi_d_ruolo_ente_competenza(id_ruolo_ente_competenza) );
	
CREATE TABLE pbandi_d_progetto_complesso
(
	id_progetto_complesso  NUMBER(3)  NOT NULL ,
	cod_igrue_t9          VARCHAR2(20)  NOT NULL ,
	desc_progetto_complesso  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	cod_tipologia         VARCHAR2(3)  NOT NULL ,
	desc_tipologia        VARCHAR2(255)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_progetto_complesso ON pbandi_d_progetto_complesso
(id_progetto_complesso  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_progetto_complesso
	ADD CONSTRAINT  PK_pbandi_d_progetto_complesso PRIMARY KEY (id_progetto_complesso);

ALTER TABLE pbandi_t_progetto
ADD id_progetto_complesso  NUMBER(3);

ALTER TABLE pbandi_t_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_PROG_COMPLESSO_01 FOREIGN KEY (id_progetto_complesso) REFERENCES pbandi_d_progetto_complesso(id_progetto_complesso) );

alter TABLE pbandi_t_csp_progetto
add id_progetto_complesso  NUMBER(3);

ALTER TABLE pbandi_t_csp_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_PROG_COMPLESSO_02 FOREIGN KEY (id_progetto_complesso) REFERENCES pbandi_d_progetto_complesso(id_progetto_complesso) );



ALTER TABLE pbandi_r_iter_proc_agg	
ADD id_motivo_scostamento  NUMBER(3);

ALTER TABLE pbandi_r_iter_proc_agg
	ADD (CONSTRAINT  FK_PBANDI_D_MOTIVO_SCOSTAME_01 FOREIGN KEY (id_motivo_scostamento) REFERENCES pbandi_d_motivo_scostamento(id_motivo_scostamento) );
	
ALTER TABLE pbandi_r_progetto_fase_monit
ADD id_motivo_scostamento  NUMBER(3);

ALTER TABLE pbandi_r_progetto_fase_monit
	ADD (CONSTRAINT  FK_PBANDI_D_MOTIVO_SCOSTAME_02 FOREIGN KEY (id_motivo_scostamento) REFERENCES pbandi_d_motivo_scostamento(id_motivo_scostamento) );

alter TABLE pbandi_r_iter_proc_att
add id_motivo_scostamento  NUMBER(3);

ALTER TABLE pbandi_r_iter_proc_att
	ADD (CONSTRAINT  FK_PBANDI_D_MOTIVO_SCOSTAME_03 FOREIGN KEY (id_motivo_scostamento) REFERENCES pbandi_d_motivo_scostamento(id_motivo_scostamento) );
	
CREATE TABLE pbandi_r_ribasso_asta
(
	id_progetto           NUMBER(8)  NOT NULL ,
	id_procedura_aggiudicaz  NUMBER(8)  NOT NULL ,
	percentuale           NUMBER(5,2)  NULL ,
	importo               NUMBER(17,2)  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_ribasso_asta ON pbandi_r_ribasso_asta
(id_progetto  ASC,id_procedura_aggiudicaz  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_ribasso_asta
	ADD CONSTRAINT  PK_pbandi_r_ribasso_asta PRIMARY KEY (id_progetto,id_procedura_aggiudicaz);



ALTER TABLE pbandi_r_ribasso_asta
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_20 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_r_ribasso_asta
	ADD (CONSTRAINT  FK_PBANDI_T_PROCEDURA_AGGIU_03 FOREIGN KEY (id_procedura_aggiudicaz) REFERENCES pbandi_t_procedura_aggiudicaz(id_procedura_aggiudicaz));



ALTER TABLE pbandi_r_ribasso_asta
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_170 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_ribasso_asta
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_171 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) );

drop table pbandi_r_progetto_sal cascade constraints;	

alter TABLE pbandi_t_sal
add id_progetto           NUMBER(8)  NOT NULL;

ALTER TABLE pbandi_t_sal
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_12 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));

alter TABLE pbandi_t_sal
modify id_procedura_aggiudicaz null;	

alter table pbandi_t_sospensione
modify motivazione not null;

alter table pbandi_t_sospensione
modify dt_inizio  not null;

alter table pbandi_t_sospensione
modify dt_fine_prevista  not null;

alter table pbandi_r_iter_proc_agg
modify id_procedura_aggiudicaz not null;
	
CREATE TABLE pbandi_r_sogg_prog_ruolo_ente
(
	progr_soggetto_progetto  NUMBER(8)  NOT NULL ,
	id_ruolo_ente_competenza  NUMBER(3)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_sogg_prog_ruolo_en ON pbandi_r_sogg_prog_ruolo_ente
(progr_soggetto_progetto  ASC,id_ruolo_ente_competenza  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_sogg_prog_ruolo_ente
	ADD CONSTRAINT  PK_pbandi_r_sogg_prog_ruolo_en PRIMARY KEY (progr_soggetto_progetto,id_ruolo_ente_competenza);



ALTER TABLE pbandi_r_sogg_prog_ruolo_ente
	ADD (CONSTRAINT  FK_PBANDI_R_SOGG_PROGETTO_03 FOREIGN KEY (progr_soggetto_progetto) REFERENCES pbandi_r_soggetto_progetto(progr_soggetto_progetto));



ALTER TABLE pbandi_r_sogg_prog_ruolo_ente
	ADD (CONSTRAINT  FK_PBANDI_D_RUOLO_ENTE_COMP_04 FOREIGN KEY (id_ruolo_ente_competenza) REFERENCES pbandi_d_ruolo_ente_competenza(id_ruolo_ente_competenza));



ALTER TABLE pbandi_r_sogg_prog_ruolo_ente
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_180 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_sogg_prog_ruolo_ente
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_181 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) );

alter table pbandi_r_soggetti_correlati
drop (id_ruolo_ente_competenza);	

alter table pbandi_t_ente_competenza
add codice_fiscale_ente varchar2(32);

alter table pbandi_t_ente_competenza
drop (id_settore);

drop table pbandi_d_settore cascade constraints;

alter TABLE pbandi_t_erogazione
add id_ente_competenza    NUMBER(8);

ALTER TABLE pbandi_t_erogazione
	ADD (CONSTRAINT  FK_PBANDI_T_ENTE_COMPETENZA_05 FOREIGN KEY (id_ente_competenza) REFERENCES pbandi_t_ente_competenza(id_ente_competenza) );

update pbandi_t_erogazione e
set e.id_ente_competenza = (select ec.id_ente_competenza
							from   pbandi_t_ente_competenza ec
							where  e.id_direzione_regionale = ec.id_direzione_regionale);
							
commit;

ALTER TABLE pbandi_t_erogazione
drop (id_direzione_regionale);

update PBANDI_T_ENTE_COMPETENZA ec
set ec.DESC_BREVE_ENTE = (select dr.DESC_BREVE_DIREZIONE_REGIONALE
                          from   PBANDI_D_DIREZIONE_REGIONALE dr    
                          where  ec.ID_DIREZIONE_REGIONALE = dr.ID_DIREZIONE_REGIONALE)
where ec.ID_DIREZIONE_REGIONALE is not null;

update PBANDI_T_ENTE_COMPETENZA ec
set ec.DESC_ENTE = (select dr.DESC_DIREZIONE_REGIONALE
                      from   PBANDI_D_DIREZIONE_REGIONALE dr    
                    where  ec.ID_DIREZIONE_REGIONALE = dr.ID_DIREZIONE_REGIONALE)
where ec.ID_DIREZIONE_REGIONALE is not null;

update PBANDI_T_ENTE_COMPETENZA set codice_fiscale_ente = '80087670016-DB0100' where id_direzione_regionale = 1 ;
update PBANDI_T_ENTE_COMPETENZA set codice_fiscale_ente = '80087670016-DB0200' where id_direzione_regionale = 2 ;
update PBANDI_T_ENTE_COMPETENZA set codice_fiscale_ente = '80087670016-DB0300' where id_direzione_regionale = 3 ;
update PBANDI_T_ENTE_COMPETENZA set codice_fiscale_ente = '80087670016-DB0400' where id_direzione_regionale = 4 ;
update PBANDI_T_ENTE_COMPETENZA set codice_fiscale_ente = '80087670016-DB0500' where id_direzione_regionale = 5	;
update PBANDI_T_ENTE_COMPETENZA set codice_fiscale_ente = '80087670016-DB0600' where id_direzione_regionale = 6 ;
update PBANDI_T_ENTE_COMPETENZA set codice_fiscale_ente = '80087670016-DB0700' where id_direzione_regionale = 7 ;
update PBANDI_T_ENTE_COMPETENZA set codice_fiscale_ente = '80087670016-DB0800' where id_direzione_regionale = 8 ;
update PBANDI_T_ENTE_COMPETENZA set codice_fiscale_ente = '80087670016-DB0900' where id_direzione_regionale = 9 ;
update PBANDI_T_ENTE_COMPETENZA set codice_fiscale_ente = '80087670016-DB1000' where id_direzione_regionale = 10;
update PBANDI_T_ENTE_COMPETENZA set codice_fiscale_ente = '80087670016-DB1100' where id_direzione_regionale = 11;
update PBANDI_T_ENTE_COMPETENZA set codice_fiscale_ente = '80087670016-DB1200' where id_direzione_regionale = 12;
update PBANDI_T_ENTE_COMPETENZA set codice_fiscale_ente = '80087670016-DB1300' where id_direzione_regionale = 13;
update PBANDI_T_ENTE_COMPETENZA set codice_fiscale_ente = '80087670016-DB1400' where id_direzione_regionale = 14;
update PBANDI_T_ENTE_COMPETENZA set codice_fiscale_ente = '80087670016-DB1500' where id_direzione_regionale = 15;
update PBANDI_T_ENTE_COMPETENZA set codice_fiscale_ente = '80087670016-DB1600' where id_direzione_regionale = 16;
update PBANDI_T_ENTE_COMPETENZA set codice_fiscale_ente = '80087670016-DB1700' where id_direzione_regionale = 17;
update PBANDI_T_ENTE_COMPETENZA set codice_fiscale_ente = '80087670016-DB1800' where id_direzione_regionale = 18;

commit;

alter table PBANDI_T_ENTE_COMPETENZA
drop (id_direzione_regionale);

drop table PBANDI_D_DIREZIONE_REGIONALE cascade constraints;

alter TABLE pbandi_t_csp_soggetto
add id_ente_competenza    NUMBER(8)  NULL;

ALTER TABLE pbandi_t_csp_soggetto
	ADD (CONSTRAINT  FK_PBANDI_T_ENTE_COMPETENZA_06 FOREIGN KEY (id_ente_competenza) REFERENCES pbandi_t_ente_competenza(id_ente_competenza) );
	
alter table pbandi_t_csp_progetto	
modify dt_concessione null;

CREATE TABLE pbandi_d_ateneo
(
	id_ateneo             NUMBER(3)  NOT NULL ,
	desc_breve_ateneo     VARCHAR2(20)  NOT NULL ,
	desc_ateneo           VARCHAR2(255)  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	codice_fiscale_ateneo  VARCHAR2(32)  NULL,
	id_forma_giuridica    NUMBER(3)  NULL ,
	id_attivita_ateco     NUMBER(4)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_ateneo ON pbandi_d_ateneo
(id_ateneo  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_ateneo
	ADD CONSTRAINT  PK_pbandi_d_ateneo PRIMARY KEY (id_ateneo);



CREATE TABLE pbandi_d_dipartimento
(
	id_dipartimento       NUMBER(3)  NOT NULL ,
	desc_breve_dipartimento  VARCHAR2(30)  NOT NULL ,
	desc_dipartimento     VARCHAR2(255)  NOT NULL ,
	codice_fiscale_dipartimento  VARCHAR2(32)  NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_ateneo             NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_dipartimento ON pbandi_d_dipartimento
(id_dipartimento  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_dipartimento
	ADD CONSTRAINT  PK_pbandi_d_dipartimento PRIMARY KEY (id_dipartimento);



ALTER TABLE pbandi_d_dipartimento
	ADD (CONSTRAINT  FK_PBANDI_D_ATENEO_01 FOREIGN KEY (id_ateneo) REFERENCES pbandi_d_ateneo(id_ateneo));
	
ALTER TABLE pbandi_t_csp_soggetto
ADD id_dipartimento       NUMBER(3)  NULL;

ALTER TABLE pbandi_t_csp_soggetto
	ADD (CONSTRAINT  FK_PBANDI_D_DIPARTIMENTO_01 FOREIGN KEY (id_dipartimento) REFERENCES pbandi_d_dipartimento(id_dipartimento) );

	
CREATE TABLE pbandi_d_delibera
(
	id_delibera           NUMBER(3)  NOT NULL ,
	cod_igrue_T27         NUMBER(5)  NOT NULL ,
	tipo_quota            VARCHAR2(5)  NULL ,
	desc_quota            VARCHAR2(255)  NULL ,
	numero          NUMBER(5)  NOT NULL ,
	anno            NUMBER(4)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_delibera ON pbandi_d_delibera
(id_delibera  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_delibera
	ADD CONSTRAINT  PK_pbandi_d_delibera PRIMARY KEY (id_delibera);



CREATE TABLE pbandi_d_norma
(
	id_norma              NUMBER(3)  NOT NULL ,
	cod_igrue_T26         NUMBER(5)  NOT NULL ,
	tipologia_norma       VARCHAR2(3)  NOT NULL ,
	desc_norma            VARCHAR2(255)  NOT NULL ,
	numero_norma          NUMBER(5)  NOT NULL ,
	anno_norma            NUMBER(4)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_norma ON pbandi_d_norma
(id_norma  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_norma
	ADD CONSTRAINT  PK_pbandi_d_norma PRIMARY KEY (id_norma);

	
create sequence seq_pbandi_r_prog_sogg_finanzi start with 1 nocache;

ALTER TABLE PBANDI_R_PROG_SOGG_FINANZIAT
 DROP PRIMARY KEY CASCADE;

drop INDEX PK_pbandi_r_prog_sogg_finanzia;
 
alter TABLE pbandi_r_prog_sogg_finanziat 
add progr_prog_sogg_finanziat  NUMBER(8);
	
alter TABLE pbandi_r_prog_sogg_finanziat 
add note                  VARCHAR2(4000)  NULL;
	
alter TABLE pbandi_r_prog_sogg_finanziat 
add flag_economie         CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_33 CHECK (flag_economie in ('S','N'));
	
alter TABLE pbandi_r_prog_sogg_finanziat 
add id_norma              NUMBER(3)  NULL ;
	
alter TABLE pbandi_r_prog_sogg_finanziat 
add id_delibera           NUMBER(3)  NULL ;
	
alter TABLE pbandi_r_prog_sogg_finanziat 
add id_comune             NUMBER(8)  NULL ;
	
alter TABLE pbandi_r_prog_sogg_finanziat 
add id_provincia          NUMBER(4)  NULL ;
	
alter TABLE pbandi_r_prog_sogg_finanziat 
add id_periodo            NUMBER(8)  NULL ;
	
alter TABLE pbandi_r_prog_sogg_finanziat 
add id_soggetto           NUMBER(8)  NULL ;

update pbandi_r_prog_sogg_finanziat 
set progr_prog_sogg_finanziat = seq_pbandi_r_prog_sogg_finanzi.nextval;

commit;

alter TABLE pbandi_r_prog_sogg_finanziat 
modify progr_prog_sogg_finanziat not null;

CREATE UNIQUE INDEX PK_pbandi_r_prog_sogg_finanzia ON pbandi_r_prog_sogg_finanziat
(progr_prog_sogg_finanziat  ASC)
	TABLESPACE PBANDI_SMALL_IDX;

ALTER TABLE pbandi_r_prog_sogg_finanziat
	ADD CONSTRAINT  PK_pbandi_r_prog_sogg_finanzia PRIMARY KEY (progr_prog_sogg_finanziat);

alter table pbandi_r_bando_sogg_finanziat
drop (id_comune);	

alter table pbandi_r_bando_sogg_finanziat
drop (id_provincia);

ALTER TABLE pbandi_r_prog_sogg_finanziat
	ADD (CONSTRAINT  FK_PBANDI_T_SOGGETTO_07 FOREIGN KEY (id_soggetto) REFERENCES pbandi_t_soggetto(id_soggetto) );



ALTER TABLE pbandi_r_prog_sogg_finanziat
	ADD (CONSTRAINT  FK_PBANDI_D_NORMA_01 FOREIGN KEY (id_norma) REFERENCES pbandi_d_norma(id_norma) );



ALTER TABLE pbandi_r_prog_sogg_finanziat
	ADD (CONSTRAINT  FK_PBANDI_D_DELIBERA_01 FOREIGN KEY (id_delibera) REFERENCES pbandi_d_delibera(id_delibera) );



ALTER TABLE pbandi_r_prog_sogg_finanziat
	ADD (CONSTRAINT  FK_PBANDI_D_COMUNE_09 FOREIGN KEY (id_comune) REFERENCES pbandi_d_comune(id_comune) );



ALTER TABLE pbandi_r_prog_sogg_finanziat
	ADD (CONSTRAINT  FK_PBANDI_D_PROVINCIA_06 FOREIGN KEY (id_provincia) REFERENCES pbandi_d_provincia(id_provincia) );



ALTER TABLE pbandi_r_prog_sogg_finanziat
	ADD (CONSTRAINT  FK_PBANDI_T_PERIODO_01 FOREIGN KEY (id_periodo) REFERENCES pbandi_t_periodo(id_periodo));


ALTER TABLE PBANDI_T_DATI_PROGETTO_MONIT
RENAME COLUMN FLAG_STATO_FAS TO STATO_FAS;

ALTER TABLE PBANDI_T_DATI_PROGETTO_MONIT
MODIFY(STATO_FAS VARCHAR2(1 BYTE));

ALTER TABLE PBANDI_T_DATI_PROGETTO_MONIT
MODIFY(STATO_FAS  DEFAULT '2');

update PBANDI_T_DATI_PROGETTO_MONIT
set STATO_FAS = '2';

commit;

alter table pbandi_d_tipo_linea_intervento
add desc_tipo_linea_fas varchar2(255);

update pbandi_d_tipo_linea_intervento
set desc_tipo_linea_fas = 'Normativa'
where ID_TIPO_LINEA_INTERVENTO = 1;

update pbandi_d_tipo_linea_intervento
set desc_tipo_linea_fas = 'Asse'
where ID_TIPO_LINEA_INTERVENTO = 2;

update pbandi_d_tipo_linea_intervento
set desc_tipo_linea_fas = 'Linea'
where ID_TIPO_LINEA_INTERVENTO = 3;

update pbandi_d_tipo_linea_intervento
set desc_tipo_linea_fas = 'Azione'
where ID_TIPO_LINEA_INTERVENTO = 4;

commit;

alter table pbandi_d_tipo_linea_intervento
modify desc_tipo_linea_fas not null;

alter TABLE pbandi_t_progetto
add id_tipo_strumento_progr  NUMBER(3);

ALTER TABLE pbandi_t_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_STRUM_PROG_02 FOREIGN KEY (id_tipo_strumento_progr) REFERENCES pbandi_d_tipo_strumento_progr(id_tipo_strumento_progr));
	
alter TABLE pbandi_t_csp_progetto
add	 id_tipo_strumento_progr  NUMBER(3);

ALTER TABLE pbandi_t_csp_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_STRUM_PROG_03 FOREIGN KEY (id_tipo_strumento_progr) REFERENCES pbandi_d_tipo_strumento_progr(id_tipo_strumento_progr) );

create sequence seq_pbandi_t_rettifica_spesa start with 1 nocache;

CREATE TABLE pbandi_d_tipo_recupero
(
	id_tipo_recupero      NUMBER(3)  NOT NULL ,
	desc_breve_tipo_recupero  VARCHAR2(20)  NOT NULL ,
	desc_tipo_recupero    VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_recupero ON pbandi_d_tipo_recupero
(id_tipo_recupero  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_recupero
	ADD CONSTRAINT  PK_pbandi_d_tipo_recupero PRIMARY KEY (id_tipo_recupero);


CREATE TABLE pbandi_t_rettifica_di_spesa
(
	id_rettifica_di_spesa  NUMBER(8)  NOT NULL ,
	dt_rettifica          DATE  NOT NULL ,
	importo_rettifica     NUMBER(15,2)  NOT NULL ,
	riferimento           VARCHAR2(255)  NULL ,
	progr_pag_quot_parte_doc_sp  NUMBER(8)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_rettifica_di_spesa ON pbandi_t_rettifica_di_spesa
(id_rettifica_di_spesa  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_rettifica_di_spesa
	ADD CONSTRAINT  PK_pbandi_t_rettifica_di_spesa PRIMARY KEY (id_rettifica_di_spesa);



ALTER TABLE pbandi_t_rettifica_di_spesa
	ADD (CONSTRAINT  FK_PBANDI_R_QP_DOC_SPESA_02 FOREIGN KEY (progr_pag_quot_parte_doc_sp) REFERENCES pbandi_r_pag_quot_parte_doc_sp(progr_pag_quot_parte_doc_sp));



ALTER TABLE pbandi_t_rettifica_di_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_182 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_rettifica_di_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_183 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) );
	
alter TABLE pbandi_t_persona_fisica
add id_cittadinanza       NUMBER(6);


ALTER TABLE pbandi_t_persona_fisica
	ADD (CONSTRAINT  FK_PBANDI_D_NAZIONE_05 FOREIGN KEY (id_cittadinanza) REFERENCES pbandi_d_nazione(id_nazione) );
	
CREATE TABLE pbandi_d_titolo_studio
(
	id_titolo_studio      NUMBER(3)  NOT NULL ,
	cod_igrue_T43         VARCHAR2(2)  NOT NULL ,
	desc_titolo_studio    VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_titolo_studio ON pbandi_d_titolo_studio
(id_titolo_studio  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_titolo_studio
	ADD CONSTRAINT  PK_pbandi_d_titolo_studio PRIMARY KEY (id_titolo_studio);

ALTER TABLE pbandi_t_persona_fisica
ADD id_titolo_studio      NUMBER(3);

ALTER TABLE pbandi_t_persona_fisica
	ADD (CONSTRAINT  FK_PBANDI_D_TITOLO_STUDIO_01 FOREIGN KEY (id_titolo_studio) REFERENCES pbandi_d_titolo_studio(id_titolo_studio) );

CREATE TABLE pbandi_d_occupazione
(
	id_occupazione        NUMBER(3)  NOT NULL ,
	cod_igrue_T44         VARCHAR2(1)  NOT NULL ,
	desc_occupazione      VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_occupazione ON pbandi_d_occupazione
(id_occupazione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_occupazione
	ADD CONSTRAINT  PK_pbandi_d_occupazione PRIMARY KEY (id_occupazione);

ALTER TABLE pbandi_t_persona_fisica
ADD id_occupazione        NUMBER(3);

ALTER TABLE pbandi_t_persona_fisica
	ADD (CONSTRAINT  FK_PBANDI_D_OCCUPAZIONE_01 FOREIGN KEY (id_occupazione) REFERENCES pbandi_d_occupazione(id_occupazione) );

ALTER TABLE pbandi_d_ateneo
	ADD (CONSTRAINT  FK_PBANDI_D_FORMA_GIURIDICA_05 FOREIGN KEY (id_forma_giuridica) REFERENCES pbandi_d_forma_giuridica(id_forma_giuridica) );

ALTER TABLE pbandi_d_ateneo
	ADD (CONSTRAINT  FK_PBANDI_D_ATTIVITA_ATECO_05 FOREIGN KEY (id_attivita_ateco) REFERENCES pbandi_d_attivita_ateco(id_attivita_ateco) );

alter TABLE pbandi_t_csp_soggetto	
add id_ateneo             NUMBER(3);

ALTER TABLE pbandi_t_csp_soggetto
	ADD (CONSTRAINT  FK_PBANDI_D_ATENEO_02 FOREIGN KEY (id_ateneo) REFERENCES pbandi_d_ateneo(id_ateneo) );

alter table PBANDI_W_CARICAMENTO_XML
add dt_inserimento date default sysdate;

alter TABLE pbandi_r_soggetti_correlati
add flag_richiesta_contributo char(1) null;

alter table pbandi_t_revoca_rinuncia rename to pbandi_t_revoca;

ALTER TABLE pbandi_t_revoca
RENAME COLUMN DT_REVOCA_RINUNCIA TO DT_REVOCA;	

ALTER TABLE pbandi_t_revoca
add note varchar2(4000);

ALTER TABLE pbandi_t_revoca
add id_modalita_agevolazione  NUMBER(3) null;

ALTER TABLE pbandi_t_revoca
	ADD (CONSTRAINT  FK_PBANDI_D_MODALITA_AGEVOL_05 FOREIGN KEY (id_modalita_agevolazione) REFERENCES pbandi_d_modalita_agevolazione(id_modalita_agevolazione));

update pbandi_t_revoca
set id_modalita_agevolazione = 1;

commit;
	
ALTER TABLE pbandi_t_revoca
modify id_modalita_agevolazione  not null;	
	
ALTER TABLE pbandi_t_revoca
drop (id_tipo_revoca_rinuncia);
	
drop table pbandi_d_tipo_revoca_rinuncia
cascade constraints;	

create sequence seq_pbandi_t_recupero start with 1 nocache;

CREATE TABLE pbandi_t_recupero
(
	id_recupero           NUMBER(8)  NOT NULL ,
	importo_recupero      NUMBER(13,2)  NOT NULL ,
	dt_recupero           DATE  NOT NULL ,
	note                  VARCHAR2(4000)  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	cod_riferimento_recupero  VARCHAR2(20)  NULL,
	id_modalita_agevolazione  NUMBER(3)  NOT NULL,
	id_tipo_recupero      NUMBER(3)  NOT NULL,
	id_progetto			  NUMBER(8)  NOT NULL	,
	estremi_recupero	  VARCHAR2(255)	NULL
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_recupero ON pbandi_t_recupero
(id_recupero  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_recupero
	ADD CONSTRAINT  PK_pbandi_t_recupero PRIMARY KEY (id_recupero);



ALTER TABLE pbandi_t_recupero
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_184 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_recupero
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_185 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) );


ALTER TABLE pbandi_t_recupero
	ADD (CONSTRAINT  FK_PBANDI_D_MODALITA_AGEVOL_06 FOREIGN KEY (id_modalita_agevolazione) REFERENCES pbandi_d_modalita_agevolazione(id_modalita_agevolazione));
	
ALTER TABLE pbandi_t_recupero
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_RECUPERO_01 FOREIGN KEY (id_tipo_recupero) REFERENCES pbandi_d_tipo_recupero(id_tipo_recupero));

ALTER TABLE pbandi_t_recupero
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_21 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));

	
CREATE TABLE pbandi_c_costanti
(
	attributo             VARCHAR2(255)  NOT NULL ,
	valore                VARCHAR2(255)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;
	
CREATE TABLE pbandi_r_bando_lin_tipo_period
(
	progr_bando_linea_intervento  NUMBER(8)  NOT NULL ,
	id_tipo_periodo       NUMBER(3)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_bando_lin_tipo_per ON pbandi_r_bando_lin_tipo_period
(progr_bando_linea_intervento  ASC,id_tipo_periodo  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_bando_lin_tipo_period
	ADD CONSTRAINT  PK_pbandi_r_bando_lin_tipo_per PRIMARY KEY (progr_bando_linea_intervento,id_tipo_periodo);



ALTER TABLE pbandi_r_bando_lin_tipo_period
	ADD (CONSTRAINT  FK_PBANDI_R_BANDO_LINEA_INT_13 FOREIGN KEY (progr_bando_linea_intervento) REFERENCES pbandi_r_bando_linea_intervent(progr_bando_linea_intervento));



ALTER TABLE pbandi_r_bando_lin_tipo_period
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_PERIODO_01 FOREIGN KEY (id_tipo_periodo) REFERENCES pbandi_d_tipo_periodo(id_tipo_periodo));



ALTER TABLE pbandi_r_bando_lin_tipo_period
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_186 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_bando_lin_tipo_period
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_187 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) );

CREATE TABLE pbandi_r_tipo_recupero_anagraf
(
	id_tipo_recupero      NUMBER(3)  NOT NULL ,
	id_tipo_anagrafica    NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_tipo_recupero_anag ON pbandi_r_tipo_recupero_anagraf
(id_tipo_recupero  ASC,id_tipo_anagrafica  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_tipo_recupero_anagraf
	ADD CONSTRAINT  PK_pbandi_r_tipo_recupero_anag PRIMARY KEY (id_tipo_recupero,id_tipo_anagrafica);



ALTER TABLE pbandi_r_tipo_recupero_anagraf
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_RECUPERO_02 FOREIGN KEY (id_tipo_recupero) REFERENCES pbandi_d_tipo_recupero(id_tipo_recupero));



ALTER TABLE pbandi_r_tipo_recupero_anagraf
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_ANAGRAFICA_08 FOREIGN KEY (id_tipo_anagrafica) REFERENCES pbandi_d_tipo_anagrafica(id_tipo_anagrafica));

drop table PBANDI_R_ATTIVITA_NOTIFICA cascade constraints;
drop table PBANDI_D_ATTIVITA_DI_PROCESSO cascade constraints;
drop table PBANDI_D_NOTIFICA cascade constraints;

alter table pbandi_t_bando
drop (id_processo);

alter TABLE pbandi_t_documento_index
add dt_aggiornamento_index  DATE;


CREATE TABLE pbandi_d_stato_tipo_doc_index
(
	id_stato_tipo_doc_index  NUMBER(3)  NOT NULL ,
	desc_breve_stato_tip_doc_index  VARCHAR2(20)  NOT NULL ,
	desc_stato_tipo_doc_index  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_stato_tipo_doc_ind ON pbandi_d_stato_tipo_doc_index
(id_stato_tipo_doc_index  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_stato_tipo_doc_index
	ADD CONSTRAINT  PK_pbandi_d_stato_tipo_doc_ind PRIMARY KEY (id_stato_tipo_doc_index);



CREATE TABLE pbandi_r_docu_index_tipo_stato
(
	id_documento_index    NUMBER(8)  NOT NULL ,
	id_stato_tipo_doc_index  NUMBER(3)  NOT NULL ,
	id_tipo_documento_index  NUMBER(3)  NOT NULL 
)
TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_docu_index_tipo_st ON pbandi_r_docu_index_tipo_stato
(id_documento_index  ASC,id_stato_tipo_doc_index  ASC,id_tipo_documento_index  ASC)
TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_docu_index_tipo_stato
	ADD CONSTRAINT  PK_pbandi_r_docu_index_tipo_st PRIMARY KEY (id_documento_index,id_stato_tipo_doc_index,id_tipo_documento_index);



CREATE TABLE pbandi_r_stato_tipo_doc_index
(
	id_stato_tipo_doc_index  NUMBER(3)  NOT NULL ,
	id_tipo_documento_index  NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_stato_tipo_doc_ind ON pbandi_r_stato_tipo_doc_index
(id_stato_tipo_doc_index  ASC,id_tipo_documento_index  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_stato_tipo_doc_index
	ADD CONSTRAINT  PK_pbandi_r_stato_tipo_doc_ind PRIMARY KEY (id_stato_tipo_doc_index,id_tipo_documento_index);



ALTER TABLE pbandi_r_docu_index_tipo_stato
	ADD (CONSTRAINT  FK_PBANDI_T_DOCUMENTO_INDEX_01 FOREIGN KEY (id_documento_index) REFERENCES pbandi_t_documento_index(id_documento_index));



ALTER TABLE pbandi_r_docu_index_tipo_stato
	ADD (CONSTRAINT  FK_PBANDI_R_ST_TIP_DOC_INDE_01 FOREIGN KEY (id_stato_tipo_doc_index,id_tipo_documento_index) REFERENCES pbandi_r_stato_tipo_doc_index(id_stato_tipo_doc_index,id_tipo_documento_index));



ALTER TABLE pbandi_r_stato_tipo_doc_index
	ADD (CONSTRAINT  FK_PBANDI_D_ST_TIP_DOC_INDE_01 FOREIGN KEY (id_stato_tipo_doc_index) REFERENCES pbandi_d_stato_tipo_doc_index(id_stato_tipo_doc_index));



ALTER TABLE pbandi_r_stato_tipo_doc_index
	ADD (CONSTRAINT  FK_PBANDI_C_TIPO_DOC_INDEX_04 FOREIGN KEY (id_tipo_documento_index) REFERENCES pbandi_c_tipo_documento_index(id_tipo_documento_index));

alter table pbandi_t_documento_index
add versione number(3);
	
	