/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE SEQUENCE seq_pbandi_d_banca
	START WITH 1
	NOCACHE;


CREATE SEQUENCE seq_pbandi_d_comune
	START WITH 1
	NOCACHE;

CREATE SEQUENCE seq_pbandi_d_comune_estero
	START WITH 1
	NOCACHE;	

CREATE SEQUENCE seq_pbandi_d_nazione
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_l_log_batch
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_l_processo_batch
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_r_bando_linea_inter
	START WITH 1
	NOCACHE;

CREATE SEQUENCE seq_pbandi_r_sogg_correlati	
START WITH 1
	NOCACHE;

CREATE SEQUENCE seq_pbandi_r_fornitore_qualif	
START WITH 1
	NOCACHE;
	

CREATE SEQUENCE seq_pbandi_t_agenzia
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_bando
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_conto_economico
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_coordinatore
	START WITH 1
	NOCACHE;


CREATE SEQUENCE seq_pbandi_t_dichiarazione_spe
	START WITH 1
	NOCACHE;	
	

CREATE SEQUENCE seq_pbandi_t_documento
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_documento_index
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_documento_spesa
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_domanda
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_ente_giuridico
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_estremi_banca
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_fornitore
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_geo_riferimento
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_indirizzo
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_iscrizione
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_pagamento
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_persona_fisica
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_piano_finanziario
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_pratica_istrut
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_progetto
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_quot_parte_doc_sp
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_raggruppamento
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_recapiti
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_recupero
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_rigo_conto_econom
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_sede
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_soggetto
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_tracciamento
	START WITH 1
	NOCACHE;



CREATE SEQUENCE seq_pbandi_t_utente
	START WITH 1
	NOCACHE;



CREATE TABLE pbandi_c_attributo
(
	id_attributo	  NUMBER(5)  NOT NULL ,
	nome_attributo	  VARCHAR2(30)  NOT NULL ,
	flag_da_tracciare  CHAR(1)  NOT NULL  CONSTRAINT  ck_flag_12 CHECK (flag_da_tracciare in ('S','N')),
	id_entita	  NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_c_attributo ON pbandi_c_attributo
(id_attributo  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_c_attributo
	ADD CONSTRAINT  PK_pbandi_c_attributo PRIMARY KEY (id_attributo);



CREATE TABLE pbandi_c_entita
(
	id_entita	  NUMBER(3)  NOT NULL ,
	nome_entita	  VARCHAR2(30)  NOT NULL ,
	flag_da_tracciare  CHAR(1)  NOT NULL  CONSTRAINT  ck_flag_11 CHECK (flag_da_tracciare in ('S','N')),
	flag_index	  CHAR(1)  NOT NULL  CONSTRAINT  ck_flag_17 CHECK (flag_index in ('S','N'))
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_c_entita ON pbandi_c_entita
(id_entita  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_c_entita
	ADD CONSTRAINT  PK_pbandi_c_entita PRIMARY KEY (id_entita);



CREATE TABLE pbandi_c_operazione
(
	id_operazione	  NUMBER(3)  NOT NULL ,
	desc_logica	  VARCHAR2(255)  NULL ,
	desc_fisica	  VARCHAR2(255)  NULL ,
	flag_da_tracciare  CHAR(1)  NOT NULL  CONSTRAINT  ck_flag_10 CHECK (flag_da_tracciare in ('S','N')),
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_c_operazione ON pbandi_c_operazione
(id_operazione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_c_operazione
	ADD CONSTRAINT  PK_pbandi_c_operazione PRIMARY KEY (id_operazione);



CREATE TABLE pbandi_c_tipo_documento_index
(
	id_tipo_documento_index  NUMBER(3)  NOT NULL ,
	desc_breve_tipo_doc_index  VARCHAR2(20)  NOT NULL ,
	desc_tipo_doc_index  VARCHAR2(100)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_c_tipo_documento_ind ON pbandi_c_tipo_documento_index
(id_tipo_documento_index  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_c_tipo_documento_index
	ADD CONSTRAINT  PK_pbandi_c_tipo_documento_ind PRIMARY KEY (id_tipo_documento_index);



CREATE TABLE pbandi_d_area_scientifica
(
	id_area_scientifica  NUMBER(3)  NOT NULL ,
	cod_area_scientifica  VARCHAR2(20)  NOT NULL ,
	desc_area_scientifica  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_area_scientifica ON pbandi_d_area_scientifica
(id_area_scientifica  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_area_scientifica
	ADD CONSTRAINT  PK_pbandi_d_area_scientifica PRIMARY KEY (id_area_scientifica);



CREATE TABLE pbandi_d_attivita_ateco
(
	id_attivita_ateco  NUMBER(4)  NOT NULL ,
	cod_ateco	  VARCHAR2(20)  NOT NULL ,
	desc_ateco	  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	id_settore_attivita  NUMBER(3)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_attivita_ateco ON pbandi_d_attivita_ateco
(id_attivita_ateco  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_attivita_ateco
	ADD CONSTRAINT  PK_pbandi_d_attivita_ateco PRIMARY KEY (id_attivita_ateco);



CREATE TABLE pbandi_d_attivita_uic
(
	id_attivita_uic	  NUMBER(3)  NOT NULL ,
	cod_attivita_uic  VARCHAR2(20)  NOT NULL ,
	desc_attivita_uic  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_attivita_uic ON pbandi_d_attivita_uic
(id_attivita_uic  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_attivita_uic
	ADD CONSTRAINT  PK_pbandi_d_attivita_uic PRIMARY KEY (id_attivita_uic);



CREATE TABLE pbandi_d_banca
(
	id_banca	  NUMBER(6)  NOT NULL ,
	cod_banca	  VARCHAR2(5)  NOT NULL ,
	desc_banca	  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	id_indirizzo	  NUMBER(9)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_banca ON pbandi_d_banca
(id_banca  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_banca
	ADD CONSTRAINT  PK_pbandi_d_banca PRIMARY KEY (id_banca);



CREATE TABLE pbandi_d_carica
(
	id_carica	  NUMBER(3)  NOT NULL ,
	desc_breve_carica  VARCHAR2(20)  NOT NULL ,
	desc_estesa_carica  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_carica ON pbandi_d_carica
(id_carica  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_carica
	ADD CONSTRAINT  PK_pbandi_d_carica PRIMARY KEY (id_carica);



CREATE TABLE pbandi_d_classificazione_ente
(
	id_classificazione_ente  NUMBER(3)  NOT NULL ,
	desc_breve_classificaz_ente  VARCHAR2(20)  NOT NULL ,
	desc_classificazione_ente  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	id_tipologia_ente  NUMBER(3)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_classificazione_en ON pbandi_d_classificazione_ente
(id_classificazione_ente  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_classificazione_ente
	ADD CONSTRAINT  PK_pbandi_d_classificazione_en PRIMARY KEY (id_classificazione_ente);



CREATE TABLE pbandi_d_comune
(
	id_comune  NUMBER(8)  NOT NULL ,
	cod_istat_comune  VARCHAR2(6)  NULL ,
	desc_comune  VARCHAR2(255)  NOT NULL ,
	cap		  VARCHAR2(5)  NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	id_provincia	  NUMBER(4)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_comune ON pbandi_d_comune
(id_comune  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_comune
	ADD CONSTRAINT  PK_pbandi_d_comune PRIMARY KEY (id_comune);


CREATE TABLE pbandi_d_comune_estero
(
	id_comune_estero  NUMBER(8)  NOT NULL ,
	desc_comune_estero  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	id_nazione	  NUMBER(6)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_comune_estero ON pbandi_d_comune_estero
(id_comune_estero  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_comune_estero
	ADD CONSTRAINT  PK_pbandi_d_comune_estero PRIMARY KEY (id_comune_estero);




CREATE TABLE pbandi_d_dimensione_impresa
(
	id_dimensione_impresa  NUMBER(3)  NOT NULL ,
	cod_dimensione_impresa  VARCHAR2(20)  NOT NULL ,
	desc_dimensione_impresa  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	cod_gefo varchar2(20) null
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_dimensione_impresa ON pbandi_d_dimensione_impresa
(id_dimensione_impresa  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_dimensione_impresa
	ADD CONSTRAINT  PK_pbandi_d_dimensione_impresa PRIMARY KEY (id_dimensione_impresa);



CREATE TABLE pbandi_d_direzione_regionale
(
	id_direzione_regionale  NUMBER(3)  NOT NULL ,
	desc_breve_direzione_regionale  VARCHAR2(20)  NOT NULL ,
	desc_direzione_regionale  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_direzione_regional ON pbandi_d_direzione_regionale
(id_direzione_regionale  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_direzione_regionale
	ADD CONSTRAINT  PK_pbandi_d_direzione_regional PRIMARY KEY (id_direzione_regionale);



CREATE TABLE pbandi_d_errore_batch
(
	codice_errore	  VARCHAR2(10)  NOT NULL ,
	descrizione	  VARCHAR2(300)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_errore_batch ON pbandi_d_errore_batch
(codice_errore  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_errore_batch
	ADD CONSTRAINT  PK_pbandi_d_errore_batch PRIMARY KEY (codice_errore);



CREATE TABLE pbandi_d_fonte_indirizzo
(
	id_fonte_indirizzo  NUMBER(3)  NOT NULL ,
	desc_breve_fonte_indirizzo  VARCHAR2(20)  NOT NULL ,
	desc_fonte_indirizzo  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_fonte_indirizzo ON pbandi_d_fonte_indirizzo
(id_fonte_indirizzo  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_fonte_indirizzo
	ADD CONSTRAINT  PK_pbandi_d_fonte_indirizzo PRIMARY KEY (id_fonte_indirizzo);



CREATE TABLE pbandi_d_forma_giuridica
(
	id_forma_giuridica  NUMBER(3)  NOT NULL ,
	cod_forma_giuridica  VARCHAR2(20)  NOT NULL ,
	desc_forma_giuridica  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	cod_gefo varchar2(20) null
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_forma_giuridica ON pbandi_d_forma_giuridica
(id_forma_giuridica  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_forma_giuridica
	ADD CONSTRAINT  PK_pbandi_d_forma_giuridica PRIMARY KEY (id_forma_giuridica);



CREATE TABLE pbandi_d_linea_di_intervento
(
	id_linea_di_intervento  NUMBER(3)  NOT NULL ,
	id_linea_di_intervento_padre  NUMBER(3)  NULL ,
	desc_breve_linea  VARCHAR2(20)  NOT NULL ,
	desc_linea	  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	id_tipo_linea_intervento  NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_linea_di_intervent ON pbandi_d_linea_di_intervento
(id_linea_di_intervento  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_linea_di_intervento
	ADD CONSTRAINT  PK_pbandi_d_linea_di_intervent PRIMARY KEY (id_linea_di_intervento);


CREATE TABLE pbandi_d_materia
(
	id_materia	  NUMBER(3)  NOT NULL ,
	desc_breve_materia  VARCHAR2(20)  NOT NULL ,
	desc_materia	  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_materia ON pbandi_d_materia
(id_materia  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_materia
	ADD CONSTRAINT  PK_pbandi_d_materia PRIMARY KEY (id_materia);



CREATE TABLE pbandi_d_modalita_agevolazione
(
	id_modalita_agevolazione  NUMBER(3)  NOT NULL ,
	desc_breve_modalita_agevolaz  VARCHAR2(20)  NOT NULL ,
	desc_modalita_agevolazione  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_modalita_agevolazi ON pbandi_d_modalita_agevolazione
(id_modalita_agevolazione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_modalita_agevolazione
	ADD CONSTRAINT  PK_pbandi_d_modalita_agevolazi PRIMARY KEY (id_modalita_agevolazione);



CREATE TABLE pbandi_d_modalita_attivazione
(
	id_modalita_attivazione  NUMBER(3)  NOT NULL ,
	desc_modalita_attivazione  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_modalita_attivazio ON pbandi_d_modalita_attivazione
(id_modalita_attivazione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_modalita_attivazione
	ADD CONSTRAINT  PK_pbandi_d_modalita_attivazio PRIMARY KEY (id_modalita_attivazione);



CREATE TABLE pbandi_d_modalita_pagamento
(
	id_modalita_pagamento  NUMBER(3)  NOT NULL ,
	desc_breve_modalita_pagamento  VARCHAR2(20)  NOT NULL ,
	desc_modalita_pagamento  VARCHAR2(100)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_modalita_pagamento ON pbandi_d_modalita_pagamento
(id_modalita_pagamento  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_modalita_pagamento
	ADD CONSTRAINT  PK_pbandi_d_modalita_pagamento PRIMARY KEY (id_modalita_pagamento);



CREATE TABLE pbandi_d_nazione
(
	id_nazione	  NUMBER(6)  NOT NULL ,
	cod_istat_nazione  VARCHAR2(3)  NULL ,
	desc_nazione	  VARCHAR2(255)  NOT NULL ,
	flag_appartenenza_ue  CHAR(1)  NOT NULL  CONSTRAINT  ck_flag_01 CHECK (flag_appartenenza_ue in ('S','N')),
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_nazione ON pbandi_d_nazione
(id_nazione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_nazione
	ADD CONSTRAINT  PK_pbandi_d_nazione PRIMARY KEY (id_nazione);



CREATE TABLE pbandi_d_nome_batch
(
	id_nome_batch	  NUMBER(3)  NOT NULL ,
	nome_batch	  VARCHAR2(300)  NOT NULL ,
	desc_batch	  VARCHAR2(100)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_nome_batch ON pbandi_d_nome_batch
(id_nome_batch  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_nome_batch
	ADD CONSTRAINT  PK_pbandi_d_nome_batch PRIMARY KEY (id_nome_batch);



CREATE TABLE pbandi_d_provincia
(
	id_provincia	  NUMBER(4)  NOT NULL ,
	cod_istat_provincia  VARCHAR2(3)  NULL ,
	sigla_provincia	  VARCHAR2(4)  NOT NULL ,
	desc_provincia	  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	id_regione	  NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_provincia ON pbandi_d_provincia
(id_provincia  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_provincia
	ADD CONSTRAINT  PK_pbandi_d_provincia PRIMARY KEY (id_provincia);



CREATE TABLE pbandi_d_regione
(
	id_regione	  NUMBER(3)  NOT NULL ,
	cod_istat_regione  VARCHAR2(2)  NULL ,
	desc_regione	  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_regione ON pbandi_d_regione
(id_regione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_regione
	ADD CONSTRAINT  PK_pbandi_d_regione PRIMARY KEY (id_regione);



CREATE TABLE pbandi_d_settore_attivita
(
	id_settore_attivita  NUMBER(3)  NOT NULL ,
	cod_settore	  VARCHAR2(20)  NOT NULL ,
	desc_settore	  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_settore_attivita ON pbandi_d_settore_attivita
(id_settore_attivita  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_settore_attivita
	ADD CONSTRAINT  PK_pbandi_d_settore_attivita PRIMARY KEY (id_settore_attivita);



CREATE TABLE pbandi_d_soggetto_finanziatore
(
	id_soggetto_finanziatore  NUMBER(3)  NOT NULL ,
	desc_breve_sogg_finanziatore  VARCHAR2(20)  NOT NULL ,
	desc_sogg_finanziatore  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_soggetto_finanziat ON pbandi_d_soggetto_finanziatore
(id_soggetto_finanziatore  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_soggetto_finanziatore
	ADD CONSTRAINT  PK_pbandi_d_soggetto_finanziat PRIMARY KEY (id_soggetto_finanziatore);



CREATE TABLE pbandi_d_stato_conto_economico
(
	id_stato_conto_economico  NUMBER(3)  NOT NULL ,
	desc_breve_stato_conto_econom  VARCHAR2(20)  NOT NULL ,
	desc_stato_conto_economico  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_stato_conto_econom ON pbandi_d_stato_conto_economico
(id_stato_conto_economico  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_stato_conto_economico
	ADD CONSTRAINT  PK_pbandi_d_stato_conto_econom PRIMARY KEY (id_stato_conto_economico);


CREATE TABLE pbandi_d_stato_documento_spesa
(
	id_stato_documento_spesa  NUMBER(3)  NOT NULL ,
	desc_breve_stato_doc_spesa  VARCHAR2(20)  NOT NULL ,
	desc_stato_documento_spesa  VARCHAR2(100)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_stato_documento_sp ON pbandi_d_stato_documento_spesa
(id_stato_documento_spesa  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_stato_documento_spesa
	ADD CONSTRAINT  PK_pbandi_d_stato_documento_sp PRIMARY KEY (id_stato_documento_spesa);	
	

CREATE TABLE pbandi_d_stato_domanda
(
	id_stato_domanda  NUMBER(3)  NOT NULL ,
	desc_breve_stato_domanda  VARCHAR2(20)  NOT NULL ,
	desc_stato_domanda  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	cod_fase_gefo	  VARCHAR2(20)  NULL ,
	COD_FASE_GEBARIC varchar2(20) null
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_stato_domanda ON pbandi_d_stato_domanda
(id_stato_domanda  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_stato_domanda
	ADD CONSTRAINT  PK_pbandi_d_stato_domanda PRIMARY KEY (id_stato_domanda);



CREATE TABLE pbandi_d_stato_invio_domanda
(
	id_stato_invio_domanda  NUMBER(3)  NOT NULL ,
	desc_breve_stato_invio_domanda  VARCHAR2(20)  NOT NULL ,
	desc_stato_invio_domanda  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_stato_invio_domand ON pbandi_d_stato_invio_domanda
(id_stato_invio_domanda  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_stato_invio_domanda
	ADD CONSTRAINT  PK_pbandi_d_stato_invio_domand PRIMARY KEY (id_stato_invio_domanda);



CREATE TABLE pbandi_d_stato_progetto
(
	id_stato_progetto  NUMBER(3)  NOT NULL ,
	desc_breve_stato_progetto  VARCHAR2(20)  NOT NULL ,
	desc_stato_progetto  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	cod_fase_gefo	  VARCHAR2(20)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_stato_progetto ON pbandi_d_stato_progetto
(id_stato_progetto  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_stato_progetto
	ADD CONSTRAINT  PK_pbandi_d_stato_progetto PRIMARY KEY (id_stato_progetto);



CREATE TABLE pbandi_d_stato_validaz_spesa
(
	id_stato_validazione_spesa  NUMBER(3)  NOT NULL ,
	desc_breve_stato_validaz_spesa  VARCHAR2(20)  NOT NULL ,
	desc_stato_validazione_spesa  VARCHAR2(100)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_stato_validaz_spes ON pbandi_d_stato_validaz_spesa
(id_stato_validazione_spesa  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_stato_validaz_spesa
	ADD CONSTRAINT  PK_pbandi_d_stato_validaz_spes PRIMARY KEY (id_stato_validazione_spesa);



CREATE TABLE pbandi_d_tematica
(
	id_tematica	  NUMBER(3)  NOT NULL ,
	cod_tematica	  VARCHAR2(20)  NOT NULL ,
	desc_tematica	  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tematica ON pbandi_d_tematica
(id_tematica  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tematica
	ADD CONSTRAINT  PK_pbandi_d_tematica PRIMARY KEY (id_tematica);



CREATE TABLE pbandi_d_tipo_accesso
(
	id_tipo_accesso	  NUMBER(3)  NOT NULL ,
	cod_tipo_accesso  VARCHAR2(20)  NOT NULL ,
	desc_tipo_accesso  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_accesso ON pbandi_d_tipo_accesso
(id_tipo_accesso  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_accesso
	ADD CONSTRAINT  PK_pbandi_d_tipo_accesso PRIMARY KEY (id_tipo_accesso);



CREATE TABLE pbandi_d_tipo_anagrafica
(
	id_tipo_anagrafica  NUMBER(3)  NOT NULL ,
	desc_breve_tipo_anagrafica  VARCHAR2(20)  NOT NULL ,
	desc_tipo_anagrafica  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	ruolo_iride VARCHAR2(100) null
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_anagrafica ON pbandi_d_tipo_anagrafica
(id_tipo_anagrafica  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_anagrafica
	ADD CONSTRAINT  PK_pbandi_d_tipo_anagrafica PRIMARY KEY (id_tipo_anagrafica);



CREATE TABLE pbandi_d_tipo_beneficiario
(
	id_tipo_beneficiario  NUMBER(3)  NOT NULL ,
	desc_breve_tipo_beneficiario  VARCHAR2(20)  NOT NULL ,
	desc_tipo_beneficiario  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	ruolo_iride VARCHAR2(100) null
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_beneficiario ON pbandi_d_tipo_beneficiario
(id_tipo_beneficiario  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_beneficiario
	ADD CONSTRAINT  PK_pbandi_d_tipo_beneficiario PRIMARY KEY (id_tipo_beneficiario);



CREATE TABLE pbandi_d_tipo_documento
(
	id_tipo_documento  NUMBER(3)  NOT NULL ,
	desc_breve_tipo_documento  VARCHAR2(20)  NOT NULL ,
	desc_tipo_documento  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	cod_gefo varchar2(20) null
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_documento ON pbandi_d_tipo_documento
(id_tipo_documento  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_documento
	ADD CONSTRAINT  PK_pbandi_d_tipo_documento PRIMARY KEY (id_tipo_documento);



CREATE TABLE pbandi_d_tipo_documento_spesa
(
	id_tipo_documento_spesa  NUMBER(3)  NOT NULL ,
	desc_breve_tipo_doc_spesa  VARCHAR2(20)  NOT NULL ,
	desc_tipo_documento_spesa  VARCHAR2(100)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_documento_spe ON pbandi_d_tipo_documento_spesa
(id_tipo_documento_spesa  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_documento_spesa
	ADD CONSTRAINT  PK_pbandi_d_tipo_documento_spe PRIMARY KEY (id_tipo_documento_spesa);



CREATE TABLE pbandi_d_tipo_ins_coordinate
(
	id_tipo_ins_coordinate  NUMBER(3)  NOT NULL ,
	desc_breve_tipo_ins_coordinate  VARCHAR2(20)  NOT NULL ,
	desc_tipo_ins_coordinate  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_ins_coordinat ON pbandi_d_tipo_ins_coordinate
(id_tipo_ins_coordinate  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_ins_coordinate
	ADD CONSTRAINT  PK_pbandi_d_tipo_ins_coordinat PRIMARY KEY (id_tipo_ins_coordinate);



CREATE TABLE pbandi_d_tipo_iscrizione
(
	id_tipo_iscrizione  NUMBER(3)  NOT NULL ,
	cod_tipo_iscrizione  VARCHAR2(20)  NOT NULL ,
	desc_tipo_iscrizione  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_iscrizione ON pbandi_d_tipo_iscrizione
(id_tipo_iscrizione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_iscrizione
	ADD CONSTRAINT  PK_pbandi_d_tipo_iscrizione PRIMARY KEY (id_tipo_iscrizione);



CREATE TABLE pbandi_d_tipo_linea_intervento
(
	id_tipo_linea_intervento  NUMBER(3)  NOT NULL ,
	cod_tipo_linea	  VARCHAR2(20)  NOT NULL ,
	desc_tipo_linea	  VARCHAR2(255)  NOT NULL ,
	livello_tipo_linea  NUMBER(3)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_linea_interve ON pbandi_d_tipo_linea_intervento
(id_tipo_linea_intervento  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_linea_intervento
	ADD CONSTRAINT  PK_pbandi_d_tipo_linea_interve PRIMARY KEY (id_tipo_linea_intervento);




CREATE TABLE pbandi_d_tipo_oggetto_attivita
(
	id_tipo_oggetto_attivita  NUMBER(3)  NOT NULL ,
	desc_breve_tipo_oggetto_attiv  VARCHAR2(20)  NOT NULL ,
	desc_tipo_oggetto_attivita  VARCHAR2(100)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_oggetto_attiv ON pbandi_d_tipo_oggetto_attivita
(id_tipo_oggetto_attivita  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_oggetto_attivita
	ADD CONSTRAINT  PK_pbandi_d_tipo_oggetto_attiv PRIMARY KEY (id_tipo_oggetto_attivita);



CREATE TABLE pbandi_d_tipo_operazione
(
	id_tipo_operazione  NUMBER(3)  NOT NULL ,
	desc_breve_tipo_operazione  VARCHAR2(20)  NOT NULL ,
	desc_tipo_operazione  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_operazione ON pbandi_d_tipo_operazione
(id_tipo_operazione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_operazione
	ADD CONSTRAINT  PK_pbandi_d_tipo_operazione PRIMARY KEY (id_tipo_operazione);



CREATE TABLE pbandi_d_tipo_sogg_correlato
(
	id_tipo_soggetto_correlato  NUMBER(3)  NOT NULL ,
	desc_breve_tipo_sogg_correlato  VARCHAR2(20)  NOT NULL ,
	desc_tipo_soggetto_correlato  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	cod_gefo              VARCHAR2(20)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_sogg_correlat ON pbandi_d_tipo_sogg_correlato
(id_tipo_soggetto_correlato  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_sogg_correlato
	ADD CONSTRAINT  PK_pbandi_d_tipo_sogg_correlat PRIMARY KEY (id_tipo_soggetto_correlato);


CREATE TABLE pbandi_d_tipo_sede
(
	id_tipo_sede	  NUMBER(3)  NOT NULL ,
	desc_breve_tipo_sede  VARCHAR2(20)  NOT NULL ,
	desc_tipo_sede	  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_sede ON pbandi_d_tipo_sede
(id_tipo_sede  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_sede
	ADD CONSTRAINT  PK_pbandi_d_tipo_sede PRIMARY KEY (id_tipo_sede);



CREATE TABLE pbandi_d_tipo_soggetto
(
	id_tipo_soggetto  NUMBER(3)  NOT NULL ,
	desc_breve_tipo_soggetto  VARCHAR2(20)  NOT NULL ,
	desc_tipo_soggetto  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_soggetto ON pbandi_d_tipo_soggetto
(id_tipo_soggetto  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_soggetto
	ADD CONSTRAINT  PK_pbandi_d_tipo_soggetto PRIMARY KEY (id_tipo_soggetto);



CREATE TABLE pbandi_d_tipologia_ente
(
	id_tipologia_ente  NUMBER(3)  NOT NULL ,
	desc_breve_tipologia_ente  VARCHAR2(20)  NOT NULL ,
	desc_tipologia_ente  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipologia_ente ON pbandi_d_tipologia_ente
(id_tipologia_ente  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipologia_ente
	ADD CONSTRAINT  PK_pbandi_d_tipologia_ente PRIMARY KEY (id_tipologia_ente);



CREATE TABLE pbandi_d_voce_di_spesa
(
	id_voce_di_spesa  NUMBER(3)  NOT NULL ,
	id_voce_di_spesa_padre  NUMBER(3)  NULL ,
	desc_voce_di_spesa  VARCHAR2(255)  NOT NULL ,
	progr_ordinamento  NUMBER(3)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	cod_tipo_voce_di_spesa  VARCHAR2(20)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_voce_di_spesa ON pbandi_d_voce_di_spesa
(id_voce_di_spesa  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_voce_di_spesa
	ADD CONSTRAINT  PK_pbandi_d_voce_di_spesa PRIMARY KEY (id_voce_di_spesa);



CREATE TABLE pbandi_l_log_batch
(
	id_log_batch	  NUMBER  NOT NULL ,
	id_processo_batch  NUMBER  NOT NULL ,
	dt_inserimento	  DATE  NOT NULL ,
	codice_errore	  VARCHAR2(10)  NOT NULL ,
	messaggio_errore  VARCHAR2(1000)  NULL 
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_l_log_batch ON pbandi_l_log_batch
(id_log_batch  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_l_log_batch
	ADD CONSTRAINT  PK_pbandi_l_log_batch PRIMARY KEY (id_log_batch);



CREATE TABLE pbandi_l_processo_batch
(
	id_processo_batch  NUMBER  NOT NULL ,
	id_nome_batch	  NUMBER(3)  NOT NULL ,
	dt_inizio_elaborazione  DATE  NOT NULL ,
	dt_fine_elaborazione  DATE  NULL ,
	flag_esito	  CHAR(2)  NULL 
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_l_processo_batch ON pbandi_l_processo_batch
(id_processo_batch  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_l_processo_batch
	ADD CONSTRAINT  PK_pbandi_l_processo_batch PRIMARY KEY (id_processo_batch);



CREATE TABLE pbandi_r_bando_linea_intervent
(
	id_linea_di_intervento  NUMBER(3)  NOT NULL ,
	id_bando	  NUMBER(8)  NOT NULL ,
	progr_bando_linea_intervento  NUMBER(8)  NOT NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL ,
	id_area_scientifica   NUMBER(3)  NULL,
	nome_bando_linea  varchar2(255)
)TABLESPACE PBANDI_small_tbl;



CREATE UNIQUE INDEX PK_pbandi_r_bando_linea_interv ON pbandi_r_bando_linea_intervent
(progr_bando_linea_intervento  ASC)TABLESPACE PBANDI_small_idx;



ALTER TABLE pbandi_r_bando_linea_intervent
	ADD CONSTRAINT  PK_pbandi_r_bando_linea_interv PRIMARY KEY (progr_bando_linea_intervento);



CREATE UNIQUE INDEX AK1_pbandi_r_bando_linea_inter ON pbandi_r_bando_linea_intervent
(id_linea_di_intervento  ASC,id_bando  ASC)TABLESPACE PBANDI_small_idx;



CREATE TABLE pbandi_r_bando_modalita_agevol
(
	id_modalita_agevolazione  NUMBER(3)  NOT NULL ,
	id_bando	  NUMBER(8)  NOT NULL ,
	percentuale_importo_agevolato  NUMBER(5,2)  NULL ,
	massimo_importo_agevolato  NUMBER(17,2)  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL 
)TABLESPACE PBANDI_small_tbl;



CREATE UNIQUE INDEX PK_pbandi_r_bando_modalita_age ON pbandi_r_bando_modalita_agevol
(id_bando  ASC,id_modalita_agevolazione  ASC)TABLESPACE PBANDI_small_idx;



ALTER TABLE pbandi_r_bando_modalita_agevol
	ADD CONSTRAINT  PK_pbandi_r_bando_modalita_age PRIMARY KEY (id_bando,id_modalita_agevolazione);



CREATE TABLE pbandi_r_bando_sogg_finanziat
(
	id_bando	  NUMBER(8)  NOT NULL ,
	id_soggetto_finanziatore  NUMBER(3)  NOT NULL ,
	percentuale_quota_sogg_finanz  NUMBER(5,2)  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL 
)TABLESPACE PBANDI_small_tbl;



CREATE UNIQUE INDEX PK_pbandi_r_bando_sogg_finanzi ON pbandi_r_bando_sogg_finanziat
(id_bando  ASC,id_soggetto_finanziatore  ASC)TABLESPACE PBANDI_small_idx;



ALTER TABLE pbandi_r_bando_sogg_finanziat
	ADD CONSTRAINT  PK_pbandi_r_bando_sogg_finanzi PRIMARY KEY (id_bando,id_soggetto_finanziatore);



CREATE TABLE pbandi_r_bando_voce_spesa
(
	id_bando	  NUMBER(8)  NOT NULL ,
	id_voce_di_spesa  NUMBER(3)  NOT NULL ,
	percentuale_imp_ammissibile  NUMBER(5,2)  NULL ,
	massimo_importo_ammissibile  NUMBER(17,2)  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL 
)TABLESPACE PBANDI_small_tbl;



CREATE UNIQUE INDEX PK_pbandi_r_bando_voce_spesa ON pbandi_r_bando_voce_spesa
(id_bando  ASC,id_voce_di_spesa  ASC)TABLESPACE PBANDI_small_idx;



ALTER TABLE pbandi_r_bando_voce_spesa
	ADD CONSTRAINT  PK_pbandi_r_bando_voce_spesa PRIMARY KEY (id_bando,id_voce_di_spesa);



CREATE TABLE pbandi_r_conto_econom_mod_agev
(
	id_modalita_agevolazione  NUMBER(3)  NOT NULL ,
	id_conto_economico  NUMBER(8)  NOT NULL ,
	quota_importo_agevolato  NUMBER(17,2)  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL ,
	quota_importo_richiesto  NUMBER(17,2)  NULL ,
	quota_imp_ammesso_finanziament  NUMBER(17,2)  NULL 
)TABLESPACE PBANDI_small_tbl;



CREATE UNIQUE INDEX PK_pbandi_r_conto_econom_mod_a ON pbandi_r_conto_econom_mod_agev
(id_conto_economico  ASC,id_modalita_agevolazione  ASC)TABLESPACE PBANDI_small_idx;



ALTER TABLE pbandi_r_conto_econom_mod_agev
	ADD CONSTRAINT  PK_pbandi_r_conto_econom_mod_a PRIMARY KEY (id_conto_economico,id_modalita_agevolazione);



CREATE TABLE pbandi_r_doc_persona_fisica
(
	id_documento	  NUMBER(8)  NOT NULL ,
	id_persona_fisica  NUMBER(8)  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_doc_persona_fisica ON pbandi_r_doc_persona_fisica
(id_documento  ASC,id_persona_fisica  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_doc_persona_fisica
	ADD CONSTRAINT  PK_pbandi_r_doc_persona_fisica PRIMARY KEY (id_documento,id_persona_fisica);



CREATE TABLE pbandi_r_ente_giuridico_iscr
(
	id_ente_giuridico  NUMBER(8)  NOT NULL ,
	id_iscrizione	  NUMBER(8)  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_ente_giuridico_isc ON pbandi_r_ente_giuridico_iscr
(id_ente_giuridico  ASC,id_iscrizione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_ente_giuridico_iscr
	ADD CONSTRAINT  PK_pbandi_r_ente_giuridico_isc PRIMARY KEY (id_ente_giuridico,id_iscrizione);



CREATE TABLE pbandi_r_ente_giuridico_sede
(
	id_sede               NUMBER(8)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	dt_fine_validita      DATE  NULL ,
	id_tipo_sede          NUMBER(3)  NOT NULL ,
	id_ente_giuridico     NUMBER(8)  NOT NULL ,
	progr_ente_giuridico_sede  NUMBER(8)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_ente_giuridico_sed ON pbandi_r_ente_giuridico_sede
(progr_ente_giuridico_sede  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_ente_giuridico_sede
	ADD CONSTRAINT  PK_pbandi_r_ente_giuridico_sed PRIMARY KEY (progr_ente_giuridico_sede);



CREATE TABLE pbandi_r_pagamento_doc_spesa
(
	id_pagamento	  NUMBER(8)  NOT NULL ,
	id_documento_di_spesa  NUMBER(8)  NOT NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL 
)
TABLESPACE PBANDI_SMALL_TBL;




CREATE UNIQUE INDEX PK_pbandi_r_pagamento_doc_spes ON pbandi_r_pagamento_doc_spesa
(id_pagamento  ASC,id_documento_di_spesa  ASC)
TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_pagamento_doc_spesa
	ADD CONSTRAINT  PK_pbandi_r_pagamento_doc_spes PRIMARY KEY (id_pagamento,id_documento_di_spesa);



CREATE TABLE pbandi_r_soggetti_correlati
(
	dt_fine_validita      DATE  NULL ,
	id_tipo_soggetto_correlato  NUMBER(3)  NOT NULL ,
	id_carica             NUMBER(3)  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	quota_partecipazione  NUMBER(17,2)  NULL ,
	id_soggetto           NUMBER(8)  NOT NULL ,
	id_soggetto_ente_giuridico  NUMBER(8)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	progr_soggetti_correlati  NUMBER(8)  NOT NULL 
)TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_soggetti_correlati ON pbandi_r_soggetti_correlati
(progr_soggetti_correlati  ASC)TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_soggetti_correlati
	ADD CONSTRAINT  PK_pbandi_r_soggetti_correlati PRIMARY KEY (progr_soggetti_correlati);



CREATE TABLE pbandi_r_persona_fisica_indir
(
	id_indirizzo  NUMBER(9)  NOT NULL ,
	id_persona_fisica  NUMBER(8)  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_persona_fisica_ind ON pbandi_r_persona_fisica_indir
(id_persona_fisica  ASC,id_indirizzo  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_r_persona_fisica_indir
	ADD CONSTRAINT  PK_pbandi_r_persona_fisica_ind PRIMARY KEY (id_persona_fisica,id_indirizzo);



CREATE TABLE pbandi_r_persona_fisica_recap
(
	id_persona_fisica  NUMBER(8)  NOT NULL ,
	id_recapiti	  NUMBER(8)  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_persona_fisica_rec ON pbandi_r_persona_fisica_recap
(id_persona_fisica  ASC,id_recapiti  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_persona_fisica_recap
	ADD CONSTRAINT  PK_pbandi_r_persona_fisica_rec PRIMARY KEY (id_persona_fisica,id_recapiti);



CREATE TABLE pbandi_r_piano_finanz_soggetto
(
	id_soggetto_finanziatore  NUMBER(3)  NOT NULL ,
	id_piano_finanziario  NUMBER(8)  NOT NULL ,
	percentuale_quota_sogg_finanz  NUMBER(5,2)  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL 
)TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_piano_finanz_sogge ON pbandi_r_piano_finanz_soggetto
(id_piano_finanziario  ASC,id_soggetto_finanziatore  ASC)TABLESPACE PBANDI_SMALL_idx;



ALTER TABLE pbandi_r_piano_finanz_soggetto
	ADD CONSTRAINT  PK_pbandi_r_piano_finanz_sogge PRIMARY KEY (id_piano_finanziario,id_soggetto_finanziatore);



CREATE TABLE pbandi_r_prog_sogg_finanziat
(
	id_progetto	  NUMBER(8)  NOT NULL ,
	id_soggetto_finanziatore  NUMBER(3)  NOT NULL ,
	perc_quota_sogg_finanziatore  NUMBER(5,2)  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL 
)TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_prog_sogg_finanzia ON pbandi_r_prog_sogg_finanziat
(id_progetto  ASC,id_soggetto_finanziatore  ASC)TABLESPACE PBANDI_SMALL_idx;



ALTER TABLE pbandi_r_prog_sogg_finanziat
	ADD CONSTRAINT  PK_pbandi_r_prog_sogg_finanzia PRIMARY KEY (id_progetto,id_soggetto_finanziatore);



CREATE TABLE pbandi_r_progetto_utente_cipe
(
	id_progetto	  NUMBER(8)  NOT NULL ,
	id_utente_cipe	  NUMBER(8)  NOT NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL 
)TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_progetto_utente_ci ON pbandi_r_progetto_utente_cipe
(id_progetto  ASC,id_utente_cipe  ASC)TABLESPACE PBANDI_SMALL_idx;



ALTER TABLE pbandi_r_progetto_utente_cipe
	ADD CONSTRAINT  PK_pbandi_r_progetto_utente_ci PRIMARY KEY (id_progetto,id_utente_cipe);



CREATE TABLE pbandi_r_sede_indirizzo
(
	id_sede		  NUMBER(8)  NOT NULL ,
	id_indirizzo	  NUMBER(9)  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_sede_indirizzo ON pbandi_r_sede_indirizzo
(id_sede  ASC,id_indirizzo  ASC)TABLESPACE PBANDI_SMALL_idx;



ALTER TABLE pbandi_r_sede_indirizzo
	ADD CONSTRAINT  PK_pbandi_r_sede_indirizzo PRIMARY KEY (id_sede,id_indirizzo);



CREATE TABLE pbandi_r_sede_recapiti
(
	id_sede		  NUMBER(8)  NOT NULL ,
	id_recapiti	  NUMBER(8)  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_sede_recapiti ON pbandi_r_sede_recapiti
(id_sede  ASC,id_recapiti  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_sede_recapiti
	ADD CONSTRAINT  PK_pbandi_r_sede_recapiti PRIMARY KEY (id_sede,id_recapiti);



CREATE TABLE pbandi_r_sogg_estremi_bancari
(
	id_soggetto	  NUMBER(8)  NOT NULL ,
	id_estremi_bancari  NUMBER(8)  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL 
)TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_sogg_estremi_banca ON pbandi_r_sogg_estremi_bancari
(id_soggetto  ASC,id_estremi_bancari  ASC)TABLESPACE PBANDI_SMALL_idx;



ALTER TABLE pbandi_r_sogg_estremi_bancari
	ADD CONSTRAINT  PK_pbandi_r_sogg_estremi_banca PRIMARY KEY (id_soggetto,id_estremi_bancari);



CREATE TABLE pbandi_r_soggetto_domanda
(
	id_soggetto	  NUMBER(8)  NOT NULL ,
	id_soggetto_intermediario  NUMBER(8)  NULL ,
	id_soggetto_capofila  NUMBER(8)  NULL ,
	id_domanda	  NUMBER(8)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	progr_soggetto_domanda  NUMBER(8)  NOT NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL ,
	id_tipo_beneficiario  NUMBER(3)  NULL ,
	id_estremi_bancari  NUMBER(8)  NULL ,
	id_tipo_anagrafica  NUMBER(3)  NOT NULL ,
	id_persona_fisica  NUMBER(8)  NULL ,
	id_ente_giuridico  NUMBER(8)  NULL ,
	id_indirizzo_persona_fisica  NUMBER(9)  NULL ,
	id_recapiti_persona_fisica  NUMBER(8)  NULL ,
	id_documento_persona_fisica  NUMBER(8)  NULL ,
	id_iscrizione_persona_giurid  NUMBER(8)  NULL
)TABLESPACE PBANDI_small_tbl;



CREATE UNIQUE INDEX PK_pbandi_r_soggetto_domanda ON pbandi_r_soggetto_domanda
(progr_soggetto_domanda  ASC)TABLESPACE PBANDI_small_idx;



ALTER TABLE pbandi_r_soggetto_domanda
	ADD CONSTRAINT  PK_pbandi_r_soggetto_domanda PRIMARY KEY (progr_soggetto_domanda);



CREATE TABLE pbandi_r_soggetto_progetto
(
	id_soggetto	  NUMBER(8)  NOT NULL ,
	id_soggetto_intermediario  NUMBER(8)  NULL ,
	id_soggetto_capofila  NUMBER(8)  NULL ,
	id_progetto	  NUMBER(8)  NOT NULL ,
	progr_soggetto_progetto  NUMBER(8)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	id_estremi_bancari  NUMBER(8)  NULL ,
	id_tipo_beneficiario  NUMBER(3)  NULL ,
	id_tipo_anagrafica  NUMBER(3)  NOT NULL ,
	id_persona_fisica  NUMBER(8)  NULL ,
	id_ente_giuridico  NUMBER(8)  NULL ,
	id_indirizzo_persona_fisica  NUMBER(9)  NULL ,
	id_recapiti_persona_fisica  NUMBER(8)  NULL ,
	id_documento_persona_fisica  NUMBER(8)  NULL ,
	id_iscrizione_persona_giurid  NUMBER(8)  NULL,
	progr_soggetto_domanda  NUMBER(8)  NULL
)TABLESPACE PBANDI_small_tbl;



CREATE UNIQUE INDEX PK_pbandi_r_soggetto_progetto ON pbandi_r_soggetto_progetto
(progr_soggetto_progetto  ASC)TABLESPACE PBANDI_small_idx;



ALTER TABLE pbandi_r_soggetto_progetto
	ADD CONSTRAINT  PK_pbandi_r_soggetto_progetto PRIMARY KEY (progr_soggetto_progetto);



CREATE TABLE pbandi_t_agenzia
(
	id_agenzia	  NUMBER(8)  NOT NULL ,
	cod_agenzia	  VARCHAR2(6)  NOT NULL ,
	desc_agenzia	  VARCHAR2(255)  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL ,
	id_banca	  NUMBER(6)  NOT NULL ,
	id_indirizzo	  NUMBER(9)  NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_agenzia ON pbandi_t_agenzia
(id_agenzia  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_agenzia
	ADD CONSTRAINT  PK_pbandi_t_agenzia PRIMARY KEY (id_agenzia);



CREATE TABLE pbandi_t_bando
(
	id_bando	  NUMBER(8)  NOT NULL ,
	titolo_bando	  VARCHAR2(255)  NULL ,
	dt_pubblicazione_bando  DATE  NULL ,
	dt_inizio_presentaz_domande  DATE  NULL ,
	dt_scadenza_bando  DATE  NULL ,
	stato_bando	  VARCHAR2(255)  NULL ,
	flag_allegato	  CHAR(1)  NOT NULL  CONSTRAINT  ck_flag_05 CHECK (flag_allegato in ('S','N')),
	flag_graduatoria  CHAR(1)  NOT NULL  CONSTRAINT  ck_flag_06 CHECK (flag_graduatoria in ('S','N')),
	dotazione_finanziaria  NUMBER(17,2)  NULL ,
	costo_totale_min_ammissibile  NUMBER(17,2)  NULL ,
	flag_quietanza	  CHAR(1)  NOT NULL  CONSTRAINT  ck_flag_07 CHECK (flag_quietanza in ('S','N')),
	percentuale_premialita  NUMBER(5,2)  NULL ,
	importo_premialita  NUMBER(17,2)  NULL ,
	punteggio_premialita  NUMBER  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL ,
	id_materia	  NUMBER(3)  NOT NULL ,
	id_modalita_attivazione  NUMBER(3)  NOT NULL ,
	id_processo	  VARCHAR2(255)  NULL 
)
	TABLESPACE PBANDI_MEDIUM_TBL;



COMMENT ON COLUMN pbandi_t_bando.id_processo IS 'serve per mantenere i riferimenti ai processi di Flux';



CREATE UNIQUE INDEX PK_pbandi_t_bando ON pbandi_t_bando
(id_bando  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_t_bando
	ADD CONSTRAINT  PK_pbandi_t_bando PRIMARY KEY (id_bando);



CREATE TABLE pbandi_t_conto_economico
(
	id_conto_economico  NUMBER(8)  NOT NULL ,
	costo_tot_ammesso_finanziamen  NUMBER(17,2)  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	id_domanda	  NUMBER(8)  NOT NULL ,
	id_stato_conto_economico  NUMBER(3)  NOT NULL ,
	costo_Totale_Recuperato number(17,2) null,
	importo_finanziamento_banca  NUMBER(17,2)  NULL,
	IMPORTO_FINANZ_BANCA_RICHIESTO  number(17,2)
)
	TABLESPACE PBANDI_MEDIUM_TBL;






CREATE UNIQUE INDEX PK_pbandi_t_conto_economico ON pbandi_t_conto_economico
(id_conto_economico  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_t_conto_economico
	ADD CONSTRAINT  PK_pbandi_t_conto_economico PRIMARY KEY (id_conto_economico);



CREATE TABLE pbandi_t_coordinatore
(
	id_coordinatore	  NUMBER(8)  NOT NULL ,
	desc_coordinatore  VARCHAR2(100)  NOT NULL ,
	id_indirizzo	  NUMBER(9)  NOT NULL ,
	id_recapiti	  NUMBER(8)  NOT NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_coordinatore ON pbandi_t_coordinatore
(id_coordinatore  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_t_coordinatore
	ADD CONSTRAINT  PK_pbandi_t_coordinatore PRIMARY KEY (id_coordinatore);



CREATE TABLE pbandi_t_documento
(
	id_documento	  NUMBER(8)  NOT NULL ,
	numero_documento  VARCHAR2(20)  NULL ,
	documento_rilasciato_da  VARCHAR2(255)  NULL ,
	dt_rilascio_documento  DATE  NULL ,
	dt_scadenza_documento  DATE  NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL ,
	id_tipo_documento  NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_documento ON pbandi_t_documento
(id_documento  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_documento
	ADD CONSTRAINT  PK_pbandi_t_documento PRIMARY KEY (id_documento);



CREATE TABLE pbandi_t_documento_di_spesa
(
	id_documento_di_spesa  NUMBER(8)  NOT NULL ,
	id_doc_riferimento    NUMBER(8)  NULL ,
	numero_documento  VARCHAR2(100)  NULL ,
	dt_emissione_documento  DATE  NOT NULL ,
	desc_documento	  VARCHAR2(200)  NULL ,
	imponibile	  NUMBER(17,2)  NULL ,
	importo_iva	  NUMBER(17,2)  NULL ,
	importo_iva_costo  NUMBER(17,2)  NULL ,
	importo_ritenuta  NUMBER(17,2)  NULL ,
	importo_totale_documento  NUMBER(17,2)  NOT NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL ,
	id_tipo_documento_spesa  NUMBER(3)  NOT NULL ,
	id_soggetto	  NUMBER(8)  NOT NULL ,
	id_fornitore	  NUMBER(8)  NULL ,
	durata_trasferta  NUMBER(4,2)  NULL ,
	destinazione_trasferta  VARCHAR2(300)  NULL ,
	id_tipo_oggetto_attivita  NUMBER(3)  NULL
)
	TABLESPACE PBANDI_MEDIUM_TBL;



COMMENT ON TABLE pbandi_t_documento_di_spesa IS 'La validazione si applica a livello di certificato di spesa (ad es. una fattura). Il documento viene validato in virtu'' della sua correttezza formale e della sua pertinenza in relazione alle voci di spesa riconosciute come ammissibili nell''ambito del progetto (almeno una delle singole voci di spesa associate al documento deve essere "pertinente" in quest''accezione).';



CREATE UNIQUE INDEX PK_pbandi_t_documento_di_spesa ON pbandi_t_documento_di_spesa
(id_documento_di_spesa  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_t_documento_di_spesa
	ADD CONSTRAINT  PK_pbandi_t_documento_di_spesa PRIMARY KEY (id_documento_di_spesa);



CREATE TABLE pbandi_t_documento_index
(
	id_documento_index  NUMBER(8)  NOT NULL ,
	id_target	  NUMBER(8)  NOT NULL ,
	uuid_nodo	  VARCHAR2(36)  NOT NULL ,
	repository	  VARCHAR2(40)  NOT NULL ,
	dt_inserimento_index  DATE  NOT NULL ,
	id_tipo_documento_index  NUMBER(3)  NOT NULL ,
	id_entita	  NUMBER(3)  NOT NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_documento_index ON pbandi_t_documento_index
(id_documento_index  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_t_documento_index
	ADD CONSTRAINT  PK_pbandi_t_documento_index PRIMARY KEY (id_documento_index);



CREATE TABLE pbandi_t_domanda
(
	id_domanda	  NUMBER(8)  NOT NULL ,
	id_domanda_padre  NUMBER(8)  NULL ,
	num_protocollo_domanda  VARCHAR2(20)  NULL ,
	dt_presentazione_domanda  DATE  NULL ,
	ora_presentazione_domanda  VARCHAR2(8)  NULL ,
	dt_protocollazione_domanda  DATE  NULL ,
	dt_ora_invio	  DATE  NULL ,
	titolo_progetto	  VARCHAR2(255)  NULL ,
	durata_progetto	  NUMBER(3)  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL ,
	id_stato_domanda  NUMBER(3)  NOT NULL ,
	id_stato_invio_domanda  NUMBER(3)  NULL ,
	flag_domanda_master  CHAR(1)  NOT NULL  CONSTRAINT  ck_flag_13 CHECK (flag_domanda_master in ('S','N')),
	numero_domanda	  VARCHAR2(20)  NULL ,
	progr_bando_linea_intervento  NUMBER(8)  NOT NULL ,
	acronimo_progetto  VARCHAR2(20)  NULL,
	numero_Domanda_GEFO number(8),
	dt_inizio_progetto_prevista  DATE  NULL ,
    dt_fine_progetto_prevista  DATE  NULL
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_domanda ON pbandi_t_domanda
(id_domanda  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_t_domanda
	ADD CONSTRAINT  PK_pbandi_t_domanda PRIMARY KEY (id_domanda);



CREATE TABLE pbandi_t_ente_giuridico
(
	id_soggetto	  NUMBER(8)  NOT NULL ,
	denominazione_ente_giuridico  VARCHAR2(255)  NOT NULL ,
	dt_costituzione	  DATE  NULL ,
	capitale_sociale  NUMBER(20,2)  NULL ,
	id_attivita_uic	  NUMBER(3)  NULL ,
	id_forma_giuridica  NUMBER(3)  NULL ,
	id_classificazione_ente  NUMBER(3)  NULL ,
	id_dimensione_impresa  NUMBER(3)  NULL ,
	id_ente_giuridico  NUMBER(8)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	dt_ultimo_esercizio_chiuso date null,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_ente_giuridico ON pbandi_t_ente_giuridico
(id_ente_giuridico  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_t_ente_giuridico
	ADD CONSTRAINT  PK_pbandi_t_ente_giuridico PRIMARY KEY (id_ente_giuridico);



CREATE TABLE pbandi_t_estremi_bancari
(
	id_estremi_bancari  NUMBER(8)  NOT NULL ,
	numero_conto	  VARCHAR2(20)  NULL ,
	cin		  VARCHAR2(1)  NULL ,
	abi		  VARCHAR2(5)  NULL ,
	cab		  VARCHAR2(5)  NULL ,
	iban		  VARCHAR2(27)  NOT NULL ,
	bic		  VARCHAR2(14)  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL ,
	id_agenzia	  NUMBER(8)  NULL ,
	id_banca	  NUMBER(6)  NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_estremi_bancari ON pbandi_t_estremi_bancari
(id_estremi_bancari  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_estremi_bancari
	ADD CONSTRAINT  PK_pbandi_t_estremi_bancari PRIMARY KEY (id_estremi_bancari);



CREATE TABLE pbandi_t_fornitore
(
	id_fornitore	  NUMBER(8)  NOT NULL ,
	codice_fiscale_fornitore  VARCHAR2(32)  NOT NULL ,
	partita_iva_fornitore  VARCHAR2(11)  NULL ,
	denominazione_fornitore  VARCHAR2(255)  NULL ,
	cognome_fornitore  VARCHAR2(150)  NULL ,
	nome_fornitore	  VARCHAR2(150)  NULL ,
	id_soggetto_fornitore  NUMBER(8)  NOT NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL ,
	id_tipo_soggetto  NUMBER(3)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_fornitore ON pbandi_t_fornitore
(id_fornitore  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_t_fornitore
	ADD CONSTRAINT  PK_pbandi_t_fornitore PRIMARY KEY (id_fornitore);



CREATE TABLE pbandi_t_geo_riferimento
(
	id_geo_riferimento  NUMBER(9)  NOT NULL ,
	strutturale  VARCHAR2(1)  NULL,
	coord_x		  NUMBER(9)  NOT NULL ,
	coord_y		  NUMBER(9)  NOT NULL ,
	id_tipo_ins_coordinate  NUMBER(3)  NOT NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL ,
	id_zona_censuaria  NUMBER(7)  NULL ,
	dt_ultima_valid_zona_censuaria  DATE  NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_geo_riferimento ON pbandi_t_geo_riferimento
(id_geo_riferimento  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_geo_riferimento
	ADD CONSTRAINT  PK_pbandi_t_geo_riferimento PRIMARY KEY (id_geo_riferimento);



CREATE TABLE pbandi_t_indirizzo
(
	id_indirizzo	  NUMBER(9)  NOT NULL ,
	desc_indirizzo	  VARCHAR2(255)  NOT NULL ,
	cap		  VARCHAR2(5)  NULL ,
	id_nazione	  NUMBER(6)  NULL ,
	id_comune	  NUMBER(8)  NULL ,
	id_provincia	  NUMBER(4)  NULL ,
	id_via_l2	  NUMBER(9)  NULL ,
	civico		  VARCHAR2(20)  NULL ,
	bis		  VARCHAR2(4)  NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL ,
	id_regione	  NUMBER(3)  NULL ,
	id_fonte_indirizzo  NUMBER(3)  NULL ,
	id_geo_riferimento  NUMBER(9)  NULL,
    id_comune_estero      NUMBER(8)  NULL	
)
	TABLESPACE PBANDI_MEDIUM_TBL;



COMMENT ON COLUMN pbandi_t_indirizzo.id_comune IS 'nel caso di acquisizione da tope equivale al comune gestito da tope';



COMMENT ON COLUMN pbandi_t_indirizzo.id_provincia IS 'nel caso di acquisizione da tope equivale alla provincia gestita da tope';



CREATE UNIQUE INDEX PK_pbandi_t_indirizzo ON pbandi_t_indirizzo
(id_indirizzo  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_indirizzo
	ADD CONSTRAINT  PK_pbandi_t_indirizzo PRIMARY KEY (id_indirizzo);



CREATE TABLE pbandi_t_iscrizione
(
	id_iscrizione	  NUMBER(8)  NOT NULL ,
	num_iscrizione	  VARCHAR2(20)  NULL ,
	dt_iscrizione	  DATE  NULL ,
	flag_iscrizione_in_corso  CHAR(1)  NOT NULL  CONSTRAINT  ck_flag_04 CHECK (flag_iscrizione_in_corso in ('S','N')),
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL ,
	id_tipo_iscrizione  NUMBER(3)  NOT NULL ,
	id_regione	  NUMBER(3)  NULL ,
	id_provincia	  NUMBER(4)  NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_iscrizione ON pbandi_t_iscrizione
(id_iscrizione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_iscrizione
	ADD CONSTRAINT  PK_pbandi_t_iscrizione PRIMARY KEY (id_iscrizione);



CREATE TABLE pbandi_t_pagamento
(
	id_pagamento	  NUMBER(8)  NOT NULL ,
	importo_pagamento  NUMBER(17,2)  NOT NULL ,
	dt_pagamento	  DATE  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL ,
	id_soggetto	  NUMBER(8)  NULL ,
	id_modalita_pagamento  NUMBER(3)  NOT NULL ,
	id_stato_validazione_spesa  NUMBER(3)  NOT NULL ,
	dt_valuta date null
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_pagamento ON pbandi_t_pagamento
(id_pagamento  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_t_pagamento
	ADD CONSTRAINT  PK_pbandi_t_pagamento PRIMARY KEY (id_pagamento);



CREATE TABLE pbandi_t_persona_fisica
(
	id_soggetto	  NUMBER(8)  NOT NULL ,
	cognome		  VARCHAR2(150)  NOT NULL ,
	nome		  VARCHAR2(150)  NULL ,
	dt_nascita	  DATE  NULL ,
	sesso		  CHAR(1)  NULL ,
	id_persona_fisica  NUMBER(8)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	id_comune_italiano_nascita  NUMBER(8)  NULL ,
	id_comune_estero_nascita  NUMBER(8)  NULL ,
	id_nazione_nascita  NUMBER(6)  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_persona_fisica ON pbandi_t_persona_fisica
(id_persona_fisica  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_t_persona_fisica
	ADD CONSTRAINT  PK_pbandi_t_persona_fisica PRIMARY KEY (id_persona_fisica);



CREATE TABLE pbandi_t_piano_finanziario
(
	id_piano_finanziario  NUMBER(8)  NOT NULL ,
	costo_totale_rendicontabile  NUMBER(17,2)  NULL ,
	costo_docup	  NUMBER(17,2)  NULL ,
	costo_totale_spesa_pubblica  NUMBER(17,2)  NULL ,
	importo_risorse_private  NUMBER(17,2)  NULL ,
	importo_altri_privati  NUMBER(17,2)  NULL ,
	totale_investimenti  NUMBER(17,2)  NULL ,
	id_linea_di_intervento  NUMBER(3)  NOT NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_piano_finanziario ON pbandi_t_piano_finanziario
(id_piano_finanziario  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_t_piano_finanziario
	ADD CONSTRAINT  PK_pbandi_t_piano_finanziario PRIMARY KEY (id_piano_finanziario);



CREATE TABLE pbandi_t_pratica_istruttoria
(
	id_pratica_istruttoria  NUMBER(8)  NOT NULL ,
	dt_apertura_pratica  DATE  NULL ,
	dt_chiusura_pratica  DATE  NULL ,
	flag_esito_istruttoria  CHAR(1)  NOT NULL  CONSTRAINT  flag_1686034153 CHECK (flag_esito_istruttoria in ('S','N')),
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL ,
	id_domanda	  NUMBER(8)  NOT NULL 
)
	TABLESPACE PBANDI_MEDIUM_TBL;



COMMENT ON TABLE pbandi_t_pratica_istruttoria IS 'La pratica di Istruttoria nasce quando la domanda viene protocollata';



CREATE UNIQUE INDEX PK_pbandi_t_pratica_istruttori ON pbandi_t_pratica_istruttoria
(id_pratica_istruttoria  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_t_pratica_istruttoria
	ADD CONSTRAINT  PK_pbandi_t_pratica_istruttori PRIMARY KEY (id_pratica_istruttoria);



CREATE TABLE pbandi_t_progetto
(
	id_progetto	  NUMBER(8)  NOT NULL ,
	id_progetto_padre  NUMBER(8)  NULL ,
	titolo_progetto	  VARCHAR2(255)  NOT NULL ,
	acronimo_progetto  VARCHAR2(20)  NULL ,
	dt_inizio_progetto_effettiva  DATE  NULL ,
	dt_fine_progetto_effettiva  DATE  NULL ,
	durata_progetto	  NUMBER(3)  NULL ,
	cup		  VARCHAR2(20)  NULL ,
	flag_progetto_master  CHAR(1)  NOT NULL  CONSTRAINT  ck_flag_02 CHECK (flag_progetto_master in ('S','N')),
	percentuale_premialita  NUMBER(5,2)  NULL ,
	importo_premialita  NUMBER(17,2)  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL ,
	id_istanza_processo  VARCHAR2(255)  NULL ,
	id_domanda	  NUMBER(8)  NOT NULL ,
	id_tipo_operazione  NUMBER(3)  NOT NULL ,
	id_stato_progetto  NUMBER(3)  NOT NULL ,
	dt_comitato	  DATE  NULL ,
	dt_concessione	  DATE  NULL ,
	codice_progetto varchar2(100) not null,
	CODICE_VISUALIZZATO varchar2(100) not null
)
	TABLESPACE PBANDI_MEDIUM_TBL;



COMMENT ON COLUMN pbandi_t_progetto.cup IS 'Codice Unico del Progetto rilasciato dal CIPE. Inizialmente non valorizzato.';



COMMENT ON COLUMN pbandi_t_progetto.id_istanza_processo IS 'serve per mantenere i riferimenti ai processi di Flux';



CREATE UNIQUE INDEX PK_pbandi_t_progetto ON pbandi_t_progetto
(id_progetto  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_t_progetto
	ADD CONSTRAINT  PK_pbandi_t_progetto PRIMARY KEY (id_progetto);



CREATE TABLE pbandi_t_quota_parte_doc_spesa
(
	id_quota_parte_doc_spesa  NUMBER(8)  NOT NULL ,
	importo_quota_parte_doc_spesa  NUMBER(17,2)  NOT NULL ,
	ore_lavorate	  NUMBER(7,2)  NULL ,
	work_packages	  NUMBER(8)  NULL ,
	costo_orario	  NUMBER(17,2)  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL ,
	id_documento_di_spesa  NUMBER(8)  NOT NULL ,
	id_progetto	  NUMBER(8)  NOT NULL ,
	id_rigo_conto_economico  NUMBER(8)  NOT NULL
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_quota_parte_doc_sp ON pbandi_t_quota_parte_doc_spesa
(id_quota_parte_doc_spesa  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_t_quota_parte_doc_spesa
	ADD CONSTRAINT  PK_pbandi_t_quota_parte_doc_sp PRIMARY KEY (id_quota_parte_doc_spesa);



CREATE TABLE pbandi_t_raggruppamento
(
	id_raggruppamento  NUMBER(8)  NOT NULL ,
	acronimo_progetto  VARCHAR2(20)  NOT NULL ,
	nome_completo_progetto  VARCHAR2(255)  NULL ,
	organizzazione	  VARCHAR2(100)  NULL ,
	raggruppamento_ats  VARCHAR2(250)  NULL ,
	atto_costitutivo_ats  VARCHAR2(250)  NULL ,
	dt_atto_costitutivo_ats  DATE  NULL ,
	costo_totale_progetto  NUMBER(17,2)  NULL ,
	durata_progetto	  NUMBER(3)  NULL ,
	richiesta_contributo  NUMBER(17,2)  NULL ,
	totale_persone_mese  NUMBER(17,2)  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL ,
	id_domanda	  NUMBER(8)  NOT NULL ,
	id_area_scientifica  NUMBER(3)  NULL ,
	id_tematica	  NUMBER(3)  NULL ,
	id_coordinatore	  NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_raggruppamento ON pbandi_t_raggruppamento
(id_raggruppamento  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_t_raggruppamento
	ADD CONSTRAINT  PK_pbandi_t_raggruppamento PRIMARY KEY (id_raggruppamento);



CREATE TABLE pbandi_t_recapiti
(
	id_recapiti	  NUMBER(8)  NOT NULL ,
	email		  VARCHAR2(128)  NULL ,
	fax		  VARCHAR2(20)  NULL ,
	telefono	  VARCHAR2(20)  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_recapiti ON pbandi_t_recapiti
(id_recapiti  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_recapiti
	ADD CONSTRAINT  PK_pbandi_t_recapiti PRIMARY KEY (id_recapiti);



CREATE TABLE pbandi_t_rigo_conto_economico
(
	id_rigo_conto_economico  NUMBER(8)  NOT NULL ,
	importo_spesa  NUMBER(17,2)  NULL ,
	importo_ammesso_finanziamento  NUMBER(17,2)  NULL ,
	IMPORTO_CONTRIBUTO  NUMBER(17,2)  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	id_conto_economico  NUMBER(8)  NOT NULL ,
	id_voce_di_spesa  NUMBER(3)  NOT NULL
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_rigo_conto_economi ON pbandi_t_rigo_conto_economico
(id_rigo_conto_economico  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_t_rigo_conto_economico
	ADD CONSTRAINT  PK_pbandi_t_rigo_conto_economi PRIMARY KEY (id_rigo_conto_economico);



CREATE TABLE pbandi_t_sede
(
	id_sede		  NUMBER(8)  NOT NULL ,
	partita_iva	  VARCHAR2(20)  NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL ,
	id_attivita_ateco  NUMBER(4)  NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL ,
	denominazione	  VARCHAR2(255)  NULL 
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_sede ON pbandi_t_sede
(id_sede  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_sede
	ADD CONSTRAINT  PK_pbandi_t_sede PRIMARY KEY (id_sede);



CREATE TABLE pbandi_t_soggetto
(
	id_soggetto	  NUMBER(8)  NOT NULL ,
	codice_fiscale_soggetto  VARCHAR2(32)  NOT NULL ,
	id_utente_ins	  NUMBER(8)  NOT NULL ,
	id_utente_agg	  NUMBER(8)  NULL ,
	id_tipo_soggetto  NUMBER(3)  NOT NULL,
	id_tipo_anagrafica    NUMBER(3)  NULL 	
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_soggetto ON pbandi_t_soggetto
(id_soggetto  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_t_soggetto
	ADD CONSTRAINT  PK_pbandi_t_soggetto PRIMARY KEY (id_soggetto);



CREATE INDEX IE1_pbandi_t_soggetto ON pbandi_t_soggetto
(codice_fiscale_soggetto  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



CREATE TABLE pbandi_t_tracciamento
(
	id_tracciamento	  NUMBER  NOT NULL ,
	valore_precedente  VARCHAR2(255)  NULL ,
	valore_successivo  VARCHAR2(255)  NULL ,
	dt_tracciamento	  TIMESTAMP  NOT NULL ,
	id_operazione	  NUMBER(3)  NOT NULL ,
	id_entita	  NUMBER(3)  NULL ,
	id_attributo	  NUMBER(5)  NULL ,
	id_target	  NUMBER(8)  NULL ,
	flag_esito	  CHAR(1)  NOT NULL  CONSTRAINT  ck_flag_15 CHECK (flag_esito in ('S','N')),
	messaggio_errore  VARCHAR2(255)  NULL ,
	id_utente  NUMBER(8)  NOT NULL ,
	durata                NUMBER(7,3)  NULL 
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_tracciamento ON pbandi_t_tracciamento
(id_tracciamento  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_t_tracciamento
	ADD CONSTRAINT  PK_pbandi_t_tracciamento PRIMARY KEY (id_tracciamento);



CREATE TABLE pbandi_t_utente
(
	id_utente	  NUMBER(8)  NOT NULL ,
	login		  VARCHAR2(256)  NULL ,
	password	  VARCHAR2(256)  NULL ,
	codice_utente	  VARCHAR2(256)  NOT NULL ,
	id_tipo_accesso	  NUMBER(3)  NOT NULL ,
	id_soggetto	  NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_utente ON pbandi_t_utente
(id_utente  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_utente
	ADD CONSTRAINT  PK_pbandi_t_utente PRIMARY KEY (id_utente);



CREATE TABLE pbandi_t_utente_cipe
(
	id_utente_cipe	  NUMBER(8)  NOT NULL ,
	utente_cipe	  VARCHAR2(256)  NULL ,
	password_utente_cipe  VARCHAR2(256)  NULL ,
	ruolo_utente_cipe  VARCHAR2(20)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_utente_cipe ON pbandi_t_utente_cipe
(id_utente_cipe  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_utente_cipe
	ADD CONSTRAINT  PK_pbandi_t_utente_cipe PRIMARY KEY (id_utente_cipe);



CREATE TABLE pbandi_w_caricamento_xml
(
	id_caricamento	  NUMBER  NOT NULL ,
	file_xml	  XMLType  NOT NULL ,
	nome_file	  VARCHAR2(255)  NOT NULL ,
	flag_valido	  VARCHAR2(2)  NULL ,
	flag_caricato	  VARCHAR2(2)  NULL ,
	numero_domanda	  VARCHAR2(20)  NULL, 
	fonte			  VARCHAR2(20)  not NULL 
)
XMLTYPE file_xml STORE AS CLOB
(TABLESPACE PBANDI_MEDIUM_LOB)
	TABLESPACE PBANDI_MEDIUM_TBL;

CREATE TABLE pbandi_s_caricamento_xml
(
	file_xml              XMLType  NOT NULL ,
	nome_file             VARCHAR2(255)  NOT NULL ,
	numero_domanda        VARCHAR2(20)  NULL ,
	dt_inserimento        DATE  NOT NULL ,
	fonte			  VARCHAR2(20)  not NULL 
)
XMLTYPE file_xml STORE AS CLOB
(TABLESPACE PBANDI_MEDIUM_LOB)
	TABLESPACE PBANDI_MEDIUM_TBL;



ALTER TABLE pbandi_s_caricamento_xml
	MODIFY dt_inserimento DEFAULT sysdate;


CREATE TABLE pbandi_w_costanti
(
	titolo_bando	  VARCHAR2(255)  NOT NULL ,
	desc_breve_linea  VARCHAR2(20)  NOT NULL ,
	attributo	  VARCHAR2(255)  NOT NULL ,
	valore		  VARCHAR2(255)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



ALTER TABLE pbandi_c_attributo
	ADD (CONSTRAINT  FK_PBANDI_C_ENTITA_02 FOREIGN KEY (id_entita) REFERENCES pbandi_c_entita(id_entita));



ALTER TABLE pbandi_d_attivita_ateco
	ADD (CONSTRAINT  FK_PBANDI_D_SETTORE_ATTIV_01 FOREIGN KEY (id_settore_attivita) REFERENCES pbandi_d_settore_attivita(id_settore_attivita) ON DELETE SET NULL);



ALTER TABLE pbandi_d_banca
	ADD (CONSTRAINT  FK_PBANDI_T_INDIRIZZO_05 FOREIGN KEY (id_indirizzo) REFERENCES pbandi_t_indirizzo(id_indirizzo) ON DELETE SET NULL);



ALTER TABLE pbandi_d_classificazione_ente
	ADD (CONSTRAINT  FK_PBANDI_D_TIPOLOGIA_ENTE_01 FOREIGN KEY (id_tipologia_ente) REFERENCES pbandi_d_tipologia_ente(id_tipologia_ente) ON DELETE SET NULL);




ALTER TABLE pbandi_d_comune
	ADD (CONSTRAINT  FK_PBANDI_D_PROVINCIA_01 FOREIGN KEY (id_provincia) REFERENCES pbandi_d_provincia(id_provincia));

ALTER TABLE pbandi_d_comune_estero
	ADD (CONSTRAINT  FK_PBANDI_D_NAZIONE_01 FOREIGN KEY (id_nazione) REFERENCES pbandi_d_nazione(id_nazione));	


ALTER TABLE pbandi_d_linea_di_intervento
	ADD (CONSTRAINT  FK_PBANDI_D_LINEA_INTERV_01 FOREIGN KEY (id_linea_di_intervento_padre) REFERENCES pbandi_d_linea_di_intervento(id_linea_di_intervento) ON DELETE SET NULL);



ALTER TABLE pbandi_d_linea_di_intervento
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_LINEA_INT_01 FOREIGN KEY (id_tipo_linea_intervento) REFERENCES pbandi_d_tipo_linea_intervento(id_tipo_linea_intervento));


ALTER TABLE pbandi_d_provincia
	ADD (CONSTRAINT  FK_PBANDI_D_REGIONE_01 FOREIGN KEY (id_regione) REFERENCES pbandi_d_regione(id_regione));



ALTER TABLE pbandi_d_voce_di_spesa
	ADD (CONSTRAINT  FK_PBANDI_D_VOCE_DI_SPESA_01 FOREIGN KEY (id_voce_di_spesa_padre) REFERENCES pbandi_d_voce_di_spesa(id_voce_di_spesa) ON DELETE SET NULL);



ALTER TABLE pbandi_l_log_batch
	ADD (CONSTRAINT  FK_PBANDI_L_PROCESSO_BATCH_01 FOREIGN KEY (id_processo_batch) REFERENCES pbandi_l_processo_batch(id_processo_batch));



ALTER TABLE pbandi_l_log_batch
	ADD (CONSTRAINT  FK_PBANDI_D_ERRORE_BATCH_01 FOREIGN KEY (codice_errore) REFERENCES pbandi_d_errore_batch(codice_errore));



ALTER TABLE pbandi_l_processo_batch
	ADD (CONSTRAINT  FK_PBANDI_D_NOME_BATCH_01 FOREIGN KEY (id_nome_batch) REFERENCES pbandi_d_nome_batch(id_nome_batch));



ALTER TABLE pbandi_r_bando_linea_intervent
	ADD (CONSTRAINT  FK_PBANDI_D_LINEA_INTERV_02 FOREIGN KEY (id_linea_di_intervento) REFERENCES pbandi_d_linea_di_intervento(id_linea_di_intervento));



ALTER TABLE pbandi_r_bando_linea_intervent
	ADD (CONSTRAINT  FK_PBANDI_T_BANDO_05 FOREIGN KEY (id_bando) REFERENCES pbandi_t_bando(id_bando));



ALTER TABLE pbandi_r_bando_linea_intervent
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_61 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_bando_linea_intervent
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_62 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);


ALTER TABLE pbandi_r_bando_linea_intervent
	ADD (CONSTRAINT  FK_PBANDI_D_AREA_SCIENTIF_02 FOREIGN KEY (id_area_scientifica) REFERENCES pbandi_d_area_scientifica(id_area_scientifica) ON DELETE SET NULL);

	
ALTER TABLE pbandi_r_bando_modalita_agevol
	ADD (CONSTRAINT  FK_PBANDI_D_MODALITA_AGEVOL_01 FOREIGN KEY (id_modalita_agevolazione) REFERENCES pbandi_d_modalita_agevolazione(id_modalita_agevolazione));



ALTER TABLE pbandi_r_bando_modalita_agevol
	ADD (CONSTRAINT  FK_PBANDI_T_BANDO_01 FOREIGN KEY (id_bando) REFERENCES pbandi_t_bando(id_bando));



ALTER TABLE pbandi_r_bando_modalita_agevol
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_27 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_bando_modalita_agevol
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_28 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_bando_sogg_finanziat
	ADD (CONSTRAINT  FK_PBANDI_T_BANDO_03 FOREIGN KEY (id_bando) REFERENCES pbandi_t_bando(id_bando));



ALTER TABLE pbandi_r_bando_sogg_finanziat
	ADD (CONSTRAINT  FK_PBANDI_D_SOGG_FINANZIAT_01 FOREIGN KEY (id_soggetto_finanziatore) REFERENCES pbandi_d_soggetto_finanziatore(id_soggetto_finanziatore));



ALTER TABLE pbandi_r_bando_sogg_finanziat
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_31 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_bando_sogg_finanziat
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_32 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_bando_voce_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_BANDO_02 FOREIGN KEY (id_bando) REFERENCES pbandi_t_bando(id_bando));



ALTER TABLE pbandi_r_bando_voce_spesa
	ADD (CONSTRAINT  FK_PBANDI_D_VOCE_DI_SPESA_02 FOREIGN KEY (id_voce_di_spesa) REFERENCES pbandi_d_voce_di_spesa(id_voce_di_spesa));



ALTER TABLE pbandi_r_bando_voce_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_29 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_bando_voce_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_30 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_conto_econom_mod_agev
	ADD (CONSTRAINT  FK_PBANDI_D_MODALITA_AGEVOL_02 FOREIGN KEY (id_modalita_agevolazione) REFERENCES pbandi_d_modalita_agevolazione(id_modalita_agevolazione));



ALTER TABLE pbandi_r_conto_econom_mod_agev
	ADD (CONSTRAINT  FK_PBANDI_T_CONTO_ECONOMICO_01 FOREIGN KEY (id_conto_economico) REFERENCES pbandi_t_conto_economico(id_conto_economico));



ALTER TABLE pbandi_r_conto_econom_mod_agev
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_45 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_conto_econom_mod_agev
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_46 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_doc_persona_fisica
	ADD (CONSTRAINT  FK_PBANDI_T_DOCUMENTO_01 FOREIGN KEY (id_documento) REFERENCES pbandi_t_documento(id_documento));



ALTER TABLE pbandi_r_doc_persona_fisica
	ADD (CONSTRAINT  FK_PBANDI_T_PERSONA_FISICA_01 FOREIGN KEY (id_persona_fisica) REFERENCES pbandi_t_persona_fisica(id_persona_fisica));



ALTER TABLE pbandi_r_doc_persona_fisica
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_79 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_doc_persona_fisica
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_80 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_ente_giuridico_iscr
	ADD (CONSTRAINT  FK_PBANDI_T_ENTE_GIURIDICO_03 FOREIGN KEY (id_ente_giuridico) REFERENCES pbandi_t_ente_giuridico(id_ente_giuridico));



ALTER TABLE pbandi_r_ente_giuridico_iscr
	ADD (CONSTRAINT  FK_PBANDI_T_ISCRIZIONE_01 FOREIGN KEY (id_iscrizione) REFERENCES pbandi_t_iscrizione(id_iscrizione));



ALTER TABLE pbandi_r_ente_giuridico_iscr
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_89 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_ente_giuridico_iscr
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_90 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_ente_giuridico_sede
	ADD (CONSTRAINT  FK_PBANDI_T_ENTE_GIURIDICO_02 FOREIGN KEY (id_ente_giuridico) REFERENCES pbandi_t_ente_giuridico(id_ente_giuridico));



ALTER TABLE pbandi_r_ente_giuridico_sede
	ADD (CONSTRAINT  FK_PBANDI_T_SEDE_01 FOREIGN KEY (id_sede) REFERENCES pbandi_t_sede(id_sede));



ALTER TABLE pbandi_r_ente_giuridico_sede
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_07 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_ente_giuridico_sede
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_08 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_ente_giuridico_sede
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_SEDE_01 FOREIGN KEY (id_tipo_sede) REFERENCES pbandi_d_tipo_sede(id_tipo_sede));


ALTER TABLE pbandi_r_pagamento_doc_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_PAGAMENTO_01 FOREIGN KEY (id_pagamento) REFERENCES pbandi_t_pagamento(id_pagamento));



ALTER TABLE pbandi_r_pagamento_doc_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_DOC_DI_SPESA_01 FOREIGN KEY (id_documento_di_spesa) REFERENCES pbandi_t_documento_di_spesa(id_documento_di_spesa));



ALTER TABLE pbandi_r_pagamento_doc_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_69 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_pagamento_doc_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_70 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_soggetti_correlati
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_PF_01 FOREIGN KEY (id_tipo_soggetto_correlato) REFERENCES pbandi_d_tipo_sogg_correlato(id_tipo_soggetto_correlato));



ALTER TABLE pbandi_r_soggetti_correlati
	ADD (CONSTRAINT  FK_PBANDI_D_CARICA_01 FOREIGN KEY (id_carica) REFERENCES pbandi_d_carica(id_carica) ON DELETE SET NULL);



ALTER TABLE pbandi_r_soggetti_correlati
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_05 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_soggetti_correlati
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_06 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_soggetti_correlati
	ADD (CONSTRAINT  FK_PBANDI_T_SOGGETTO_14 FOREIGN KEY (id_soggetto) REFERENCES pbandi_t_soggetto(id_soggetto));



ALTER TABLE pbandi_r_soggetti_correlati
	ADD (CONSTRAINT  FK_PBANDI_T_SOGGETTO_15 FOREIGN KEY (id_soggetto_ente_giuridico) REFERENCES pbandi_t_soggetto(id_soggetto));


ALTER TABLE pbandi_r_persona_fisica_indir
	ADD (CONSTRAINT  FK_PBANDI_T_INDIRIZZO_02 FOREIGN KEY (id_indirizzo) REFERENCES pbandi_t_indirizzo(id_indirizzo));



ALTER TABLE pbandi_r_persona_fisica_indir
	ADD (CONSTRAINT  FK_PBANDI_T_PERSONA_FISICA_02 FOREIGN KEY (id_persona_fisica) REFERENCES pbandi_t_persona_fisica(id_persona_fisica));



ALTER TABLE pbandi_r_persona_fisica_indir
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_81 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_persona_fisica_indir
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_82 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_persona_fisica_recap
	ADD (CONSTRAINT  FK_PBANDI_T_PERSONA_FISICA_04 FOREIGN KEY (id_persona_fisica) REFERENCES pbandi_t_persona_fisica(id_persona_fisica));



ALTER TABLE pbandi_r_persona_fisica_recap
	ADD (CONSTRAINT  FK_PBANDI_T_RECAPITI_02 FOREIGN KEY (id_recapiti) REFERENCES pbandi_t_recapiti(id_recapiti));



ALTER TABLE pbandi_r_persona_fisica_recap
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_87 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_persona_fisica_recap
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_88 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_piano_finanz_soggetto
	ADD (CONSTRAINT  FK_PBANDI_D_SOGG_FINANZIAT_02 FOREIGN KEY (id_soggetto_finanziatore) REFERENCES pbandi_d_soggetto_finanziatore(id_soggetto_finanziatore));



ALTER TABLE pbandi_r_piano_finanz_soggetto
	ADD (CONSTRAINT  FK_PBANDI_T_PIANO_FINANZ_01 FOREIGN KEY (id_piano_finanziario) REFERENCES pbandi_t_piano_finanziario(id_piano_finanziario));



ALTER TABLE pbandi_r_piano_finanz_soggetto
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_35 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_piano_finanz_soggetto
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_36 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_prog_sogg_finanziat
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_04 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_r_prog_sogg_finanziat
	ADD (CONSTRAINT  FK_PBANDI_D_SOGG_FINANZIAT_03 FOREIGN KEY (id_soggetto_finanziatore) REFERENCES pbandi_d_soggetto_finanziatore(id_soggetto_finanziatore));



ALTER TABLE pbandi_r_prog_sogg_finanziat
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_53 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_prog_sogg_finanziat
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_54 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_progetto_utente_cipe
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_03 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_r_progetto_utente_cipe
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_CIPE_01 FOREIGN KEY (id_utente_cipe) REFERENCES pbandi_t_utente_cipe(id_utente_cipe));



ALTER TABLE pbandi_r_progetto_utente_cipe
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_51 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_progetto_utente_cipe
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_52 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_sede_indirizzo
	ADD (CONSTRAINT  FK_PBANDI_T_SEDE_04 FOREIGN KEY (id_sede) REFERENCES pbandi_t_sede(id_sede));



ALTER TABLE pbandi_r_sede_indirizzo
	ADD (CONSTRAINT  FK_PBANDI_T_INDIRIZZO_03 FOREIGN KEY (id_indirizzo) REFERENCES pbandi_t_indirizzo(id_indirizzo));



ALTER TABLE pbandi_r_sede_indirizzo
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_83 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_sede_indirizzo
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_84 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_sede_recapiti
	ADD (CONSTRAINT  FK_PBANDI_T_SEDE_05 FOREIGN KEY (id_sede) REFERENCES pbandi_t_sede(id_sede));



ALTER TABLE pbandi_r_sede_recapiti
	ADD (CONSTRAINT  FK_PBANDI_T_RECAPITI_01 FOREIGN KEY (id_recapiti) REFERENCES pbandi_t_recapiti(id_recapiti));



ALTER TABLE pbandi_r_sede_recapiti
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_85 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_sede_recapiti
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_86 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_sogg_estremi_bancari
	ADD (CONSTRAINT  FK_PBANDI_T_SOGGETTO_04 FOREIGN KEY (id_soggetto) REFERENCES pbandi_t_soggetto(id_soggetto));



ALTER TABLE pbandi_r_sogg_estremi_bancari
	ADD (CONSTRAINT  FK_PBANDI_T_ESTREMI_BANCARI_01 FOREIGN KEY (id_estremi_bancari) REFERENCES pbandi_t_estremi_bancari(id_estremi_bancari));



ALTER TABLE pbandi_r_sogg_estremi_bancari
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_59 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_sogg_estremi_bancari
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_60 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_soggetto_domanda
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_39 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_soggetto_domanda
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_40 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_soggetto_domanda
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_BENEFIC_01 FOREIGN KEY (id_tipo_beneficiario) REFERENCES pbandi_d_tipo_beneficiario(id_tipo_beneficiario) ON DELETE SET NULL);



ALTER TABLE pbandi_r_soggetto_domanda
	ADD (CONSTRAINT  FK_PBANDI_T_ESTREMI_BANCARI_02 FOREIGN KEY (id_estremi_bancari) REFERENCES pbandi_t_estremi_bancari(id_estremi_bancari) ON DELETE SET NULL);



ALTER TABLE pbandi_r_soggetto_domanda
	ADD (CONSTRAINT  FK_PBANDI_T_SOGGETTO_08 FOREIGN KEY (id_soggetto_capofila) REFERENCES pbandi_t_soggetto(id_soggetto) ON DELETE SET NULL);



ALTER TABLE pbandi_r_soggetto_domanda
	ADD (CONSTRAINT  FK_PBANDI_T_SOGGETTO_07 FOREIGN KEY (id_soggetto_intermediario) REFERENCES pbandi_t_soggetto(id_soggetto) ON DELETE SET NULL);



ALTER TABLE pbandi_r_soggetto_domanda
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_ANAGRAFICA_01 FOREIGN KEY (id_tipo_anagrafica) REFERENCES pbandi_d_tipo_anagrafica(id_tipo_anagrafica));



ALTER TABLE pbandi_r_soggetto_domanda
	ADD (CONSTRAINT  FK_PBANDI_T_PERSONA_FISICA_05 FOREIGN KEY (id_persona_fisica) REFERENCES pbandi_t_persona_fisica(id_persona_fisica) ON DELETE SET NULL);



ALTER TABLE pbandi_r_soggetto_domanda
	ADD (CONSTRAINT  FK_PBANDI_T_SOGGETTO_05 FOREIGN KEY (id_soggetto) REFERENCES pbandi_t_soggetto(id_soggetto));



ALTER TABLE pbandi_r_soggetto_domanda
	ADD (CONSTRAINT  FK_PBANDI_T_ENTE_GIURIDICO_01 FOREIGN KEY (id_ente_giuridico) REFERENCES pbandi_t_ente_giuridico(id_ente_giuridico) ON DELETE SET NULL);



ALTER TABLE pbandi_r_soggetto_domanda
	ADD (CONSTRAINT  FK_PBANDI_T_INDIRIZZO_09 FOREIGN KEY (id_indirizzo_persona_fisica) REFERENCES pbandi_t_indirizzo(id_indirizzo) ON DELETE SET NULL);


ALTER TABLE pbandi_r_soggetto_domanda
	ADD (CONSTRAINT  FK_PBANDI_T_RECAPITI_06 FOREIGN KEY (id_recapiti_persona_fisica) REFERENCES pbandi_t_recapiti(id_recapiti) ON DELETE SET NULL);



ALTER TABLE pbandi_r_soggetto_domanda
	ADD (CONSTRAINT  FK_PBANDI_T_DOCUMENTO_02 FOREIGN KEY (id_documento_persona_fisica) REFERENCES pbandi_t_documento(id_documento) ON DELETE SET NULL);



ALTER TABLE pbandi_r_soggetto_domanda
	ADD (CONSTRAINT  FK_PBANDI_T_ISCRIZIONE_02 FOREIGN KEY (id_iscrizione_persona_giurid) REFERENCES pbandi_t_iscrizione(id_iscrizione) ON DELETE SET NULL);



ALTER TABLE pbandi_r_soggetto_domanda
	ADD (CONSTRAINT  FK_PBANDI_T_DOMANDA_01 FOREIGN KEY (id_domanda) REFERENCES pbandi_t_domanda(id_domanda));




ALTER TABLE pbandi_r_soggetto_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_BENEFIC_02 FOREIGN KEY (id_tipo_beneficiario) REFERENCES pbandi_d_tipo_beneficiario(id_tipo_beneficiario) ON DELETE SET NULL);



ALTER TABLE pbandi_r_soggetto_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_SOGGETTO_10 FOREIGN KEY (id_soggetto_capofila) REFERENCES pbandi_t_soggetto(id_soggetto) ON DELETE SET NULL);



ALTER TABLE pbandi_r_soggetto_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_SOGGETTO_09 FOREIGN KEY (id_soggetto_intermediario) REFERENCES pbandi_t_soggetto(id_soggetto) ON DELETE SET NULL);



ALTER TABLE pbandi_r_soggetto_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_ANAGRAFICA_02 FOREIGN KEY (id_tipo_anagrafica) REFERENCES pbandi_d_tipo_anagrafica(id_tipo_anagrafica));



ALTER TABLE pbandi_r_soggetto_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_PERSONA_FISICA_06 FOREIGN KEY (id_persona_fisica) REFERENCES pbandi_t_persona_fisica(id_persona_fisica) ON DELETE SET NULL);



ALTER TABLE pbandi_r_soggetto_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_ENTE_GIURIDICO_04 FOREIGN KEY (id_ente_giuridico) REFERENCES pbandi_t_ente_giuridico(id_ente_giuridico) ON DELETE SET NULL);


ALTER TABLE pbandi_r_soggetto_progetto
    ADD (CONSTRAINT  FK_PBANDI_R_SOGG_DOMANDA_03 FOREIGN KEY (progr_soggetto_domanda) REFERENCES pbandi_r_soggetto_domanda(progr_soggetto_domanda) ON DELETE SET NULL);


ALTER TABLE pbandi_r_soggetto_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_SOGGETTO_06 FOREIGN KEY (id_soggetto) REFERENCES pbandi_t_soggetto(id_soggetto));



ALTER TABLE pbandi_r_soggetto_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_INDIRIZZO_12 FOREIGN KEY (id_indirizzo_persona_fisica) REFERENCES pbandi_t_indirizzo(id_indirizzo) ON DELETE SET NULL);





ALTER TABLE pbandi_r_soggetto_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_RECAPITI_09 FOREIGN KEY (id_recapiti_persona_fisica) REFERENCES pbandi_t_recapiti(id_recapiti) ON DELETE SET NULL);



ALTER TABLE pbandi_r_soggetto_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_DOCUMENTO_03 FOREIGN KEY (id_documento_persona_fisica) REFERENCES pbandi_t_documento(id_documento) ON DELETE SET NULL);



ALTER TABLE pbandi_r_soggetto_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_ISCRIZIONE_03 FOREIGN KEY (id_iscrizione_persona_giurid) REFERENCES pbandi_t_iscrizione(id_iscrizione) ON DELETE SET NULL);



ALTER TABLE pbandi_r_soggetto_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_02 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_r_soggetto_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_ESTREMI_BANCARI_03 FOREIGN KEY (id_estremi_bancari) REFERENCES pbandi_t_estremi_bancari(id_estremi_bancari) ON DELETE SET NULL);



ALTER TABLE pbandi_t_agenzia
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_23 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_agenzia
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_24 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_agenzia
	ADD (CONSTRAINT  FK_PBANDI_D_BANCA_01 FOREIGN KEY (id_banca) REFERENCES pbandi_d_banca(id_banca));



ALTER TABLE pbandi_t_agenzia
	ADD (CONSTRAINT  FK_PBANDI_T_INDIRIZZO_04 FOREIGN KEY (id_indirizzo) REFERENCES pbandi_t_indirizzo(id_indirizzo) ON DELETE SET NULL);


ALTER TABLE pbandi_t_bando
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_25 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_bando
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_26 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_bando
	ADD (CONSTRAINT  FK_PBANDI_D_MATERIA_01 FOREIGN KEY (id_materia) REFERENCES pbandi_d_materia(id_materia));



ALTER TABLE pbandi_t_bando
	ADD (CONSTRAINT  FK_PBANDI_D_MODALITA_ATT_01 FOREIGN KEY (id_modalita_attivazione) REFERENCES pbandi_d_modalita_attivazione(id_modalita_attivazione));


ALTER TABLE pbandi_t_conto_economico
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_43 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_conto_economico
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_44 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_conto_economico
	ADD (CONSTRAINT  FK_PBANDI_T_DOMANDA_03 FOREIGN KEY (id_domanda) REFERENCES pbandi_t_domanda(id_domanda));



ALTER TABLE pbandi_t_conto_economico
	ADD (CONSTRAINT  FK_PBANDI_D_STATO_CONTO_EC_01 FOREIGN KEY (id_stato_conto_economico) REFERENCES pbandi_d_stato_conto_economico(id_stato_conto_economico));



ALTER TABLE pbandi_t_coordinatore
	ADD (CONSTRAINT  FK_PBANDI_T_INDIRIZZO_06 FOREIGN KEY (id_indirizzo) REFERENCES pbandi_t_indirizzo(id_indirizzo));



ALTER TABLE pbandi_t_coordinatore
	ADD (CONSTRAINT  FK_PBANDI_T_RECAPITI_03 FOREIGN KEY (id_recapiti) REFERENCES pbandi_t_recapiti(id_recapiti));



ALTER TABLE pbandi_t_coordinatore
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_57 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_coordinatore
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_58 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);


ALTER TABLE pbandi_t_documento
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_77 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_documento
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_78 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_documento
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_DOCUMENTO_01 FOREIGN KEY (id_tipo_documento) REFERENCES pbandi_d_tipo_documento(id_tipo_documento));



ALTER TABLE pbandi_t_documento_di_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_65 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_documento_di_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_66 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_documento_di_spesa
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_DOC_SPESA_01 FOREIGN KEY (id_tipo_documento_spesa) REFERENCES pbandi_d_tipo_documento_spesa(id_tipo_documento_spesa));



ALTER TABLE pbandi_t_documento_di_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_SOGGETTO_12 FOREIGN KEY (id_soggetto) REFERENCES pbandi_t_soggetto(id_soggetto));



ALTER TABLE pbandi_t_documento_di_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_FORNITORE_01 FOREIGN KEY (id_fornitore) REFERENCES pbandi_t_fornitore(id_fornitore) ON DELETE SET NULL);



ALTER TABLE pbandi_t_documento_di_spesa
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_OGGETTO_AT_01 FOREIGN KEY (id_tipo_oggetto_attivita) REFERENCES pbandi_d_tipo_oggetto_attivita(id_tipo_oggetto_attivita) ON DELETE SET NULL);

ALTER TABLE pbandi_t_documento_di_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_DOC_DI_SPESA_04 FOREIGN KEY (id_doc_riferimento) REFERENCES pbandi_t_documento_di_spesa(id_documento_di_spesa) ON DELETE SET NULL);
		

ALTER TABLE pbandi_t_documento_index
	ADD (CONSTRAINT  FK_PBANDI_C_TIPO_DOC_INDEX_01 FOREIGN KEY (id_tipo_documento_index) REFERENCES pbandi_c_tipo_documento_index(id_tipo_documento_index));



ALTER TABLE pbandi_t_documento_index
	ADD (CONSTRAINT  FK_PBANDI_C_ENTITA_03 FOREIGN KEY (id_entita) REFERENCES pbandi_c_entita(id_entita));



ALTER TABLE pbandi_t_documento_index
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_75 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_documento_index
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_76 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_domanda
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_37 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_domanda
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_38 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_domanda
	ADD (CONSTRAINT  FK_PBANDI_D_STATO_DOMANDA_01 FOREIGN KEY (id_stato_domanda) REFERENCES pbandi_d_stato_domanda(id_stato_domanda));



ALTER TABLE pbandi_t_domanda
	ADD (CONSTRAINT  FK_PBANDI_D_STATO_INVIO_DOM_01 FOREIGN KEY (id_stato_invio_domanda) REFERENCES pbandi_d_stato_invio_domanda(id_stato_invio_domanda) ON DELETE SET NULL);


ALTER TABLE pbandi_t_domanda
	ADD (CONSTRAINT  FK_PBANDI_T_DOMANDA_05 FOREIGN KEY (id_domanda_padre) REFERENCES pbandi_t_domanda(id_domanda) ON DELETE SET NULL);



ALTER TABLE pbandi_t_domanda
	ADD (CONSTRAINT  FK_PBANDI_R_BANDO_LINEA_INT_01 FOREIGN KEY (progr_bando_linea_intervento) REFERENCES pbandi_r_bando_linea_intervent(progr_bando_linea_intervento));



ALTER TABLE pbandi_t_ente_giuridico
	ADD (CONSTRAINT  FK_PBANDI_D_CLASSIF_ENTE_01 FOREIGN KEY (id_classificazione_ente) REFERENCES pbandi_d_classificazione_ente(id_classificazione_ente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_ente_giuridico
	ADD (CONSTRAINT  FK_PBANDI_D_DIMENSIONE_IMPR_01 FOREIGN KEY (id_dimensione_impresa) REFERENCES pbandi_d_dimensione_impresa(id_dimensione_impresa) ON DELETE SET NULL);



ALTER TABLE pbandi_t_ente_giuridico
	ADD (CONSTRAINT  FK_PBANDI_D_ATTIVITA_UIC_01 FOREIGN KEY (id_attivita_uic) REFERENCES pbandi_d_attivita_uic(id_attivita_uic) ON DELETE SET NULL);



ALTER TABLE pbandi_t_ente_giuridico
	ADD (CONSTRAINT  FK_PBANDI_T_SOGGETTO_02 FOREIGN KEY (id_soggetto) REFERENCES pbandi_t_soggetto(id_soggetto));



ALTER TABLE pbandi_t_ente_giuridico
	ADD (CONSTRAINT  FK_PBANDI_D_FORMA_GIURIDICA_01 FOREIGN KEY (id_forma_giuridica) REFERENCES pbandi_d_forma_giuridica(id_forma_giuridica) ON DELETE SET NULL);


ALTER TABLE pbandi_t_ente_giuridico
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_110 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_ente_giuridico
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_111 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);	
	

ALTER TABLE pbandi_t_estremi_bancari
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_15 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_estremi_bancari
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_16 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_estremi_bancari
	ADD (CONSTRAINT  FK_PBANDI_T_AGENZIA_01 FOREIGN KEY (id_agenzia) REFERENCES pbandi_t_agenzia(id_agenzia) ON DELETE SET NULL);



ALTER TABLE pbandi_t_estremi_bancari
	ADD (CONSTRAINT  FK_PBANDI_D_BANCA_02 FOREIGN KEY (id_banca) REFERENCES pbandi_d_banca(id_banca) ON DELETE SET NULL);



ALTER TABLE pbandi_t_fornitore
	ADD (CONSTRAINT  FK_PBANDI_T_SOGGETTO_01 FOREIGN KEY (id_soggetto_fornitore) REFERENCES pbandi_t_soggetto(id_soggetto));



ALTER TABLE pbandi_t_fornitore
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_01 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_fornitore
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_02 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_fornitore
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_SOGGETTO_02 FOREIGN KEY (id_tipo_soggetto) REFERENCES pbandi_d_tipo_soggetto(id_tipo_soggetto));



ALTER TABLE pbandi_t_geo_riferimento
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_INS_COORD_01 FOREIGN KEY (id_tipo_ins_coordinate) REFERENCES pbandi_d_tipo_ins_coordinate(id_tipo_ins_coordinate));



ALTER TABLE pbandi_t_geo_riferimento
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_21 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_geo_riferimento
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_22 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_indirizzo
	ADD (CONSTRAINT  FK_PBANDI_D_NAZIONE_02 FOREIGN KEY (id_nazione) REFERENCES pbandi_d_nazione(id_nazione) ON DELETE SET NULL);



ALTER TABLE pbandi_t_indirizzo
	ADD (CONSTRAINT  FK_PBANDI_D_COMUNE_02 FOREIGN KEY (id_comune) REFERENCES pbandi_d_comune(id_comune) ON DELETE SET NULL);



ALTER TABLE pbandi_t_indirizzo
	ADD (CONSTRAINT  FK_PBANDI_D_PROVINCIA_02 FOREIGN KEY (id_provincia) REFERENCES pbandi_d_provincia(id_provincia) ON DELETE SET NULL);



ALTER TABLE pbandi_t_indirizzo
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_17 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_indirizzo
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_18 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_indirizzo
	ADD (CONSTRAINT  FK_PBANDI_D_REGIONE_02 FOREIGN KEY (id_regione) REFERENCES pbandi_d_regione(id_regione) ON DELETE SET NULL);



ALTER TABLE pbandi_t_indirizzo
	ADD (CONSTRAINT  FK_PBANDI_D_FONTE_INDIRIZZO_01 FOREIGN KEY (id_fonte_indirizzo) REFERENCES pbandi_d_fonte_indirizzo(id_fonte_indirizzo) ON DELETE SET NULL);



ALTER TABLE pbandi_t_indirizzo
	ADD (CONSTRAINT  FK_PBANDI_T_GEO_RIFERIMENTO_01 FOREIGN KEY (id_geo_riferimento) REFERENCES pbandi_t_geo_riferimento(id_geo_riferimento) ON DELETE SET NULL);

ALTER TABLE pbandi_t_indirizzo
	ADD (CONSTRAINT  FK_PBANDI_D_COMUNE_ESTERO_02 FOREIGN KEY (id_comune_estero) REFERENCES pbandi_d_comune_estero(id_comune_estero) ON DELETE SET NULL);


ALTER TABLE pbandi_t_iscrizione
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_13 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_iscrizione
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_14 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_iscrizione
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_ISCRIZIONE_01 FOREIGN KEY (id_tipo_iscrizione) REFERENCES pbandi_d_tipo_iscrizione(id_tipo_iscrizione));



ALTER TABLE pbandi_t_iscrizione
	ADD (CONSTRAINT  FK_PBANDI_D_REGIONE_03 FOREIGN KEY (id_regione) REFERENCES pbandi_d_regione(id_regione) ON DELETE SET NULL);



ALTER TABLE pbandi_t_iscrizione
	ADD (CONSTRAINT  FK_PBANDI_D_PROVINCIA_03 FOREIGN KEY (id_provincia) REFERENCES pbandi_d_provincia(id_provincia) ON DELETE SET NULL);



ALTER TABLE pbandi_t_pagamento
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_63 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_pagamento
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_64 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_pagamento
	ADD (CONSTRAINT  FK_PBANDI_T_SOGGETTO_11 FOREIGN KEY (id_soggetto) REFERENCES pbandi_t_soggetto(id_soggetto) ON DELETE SET NULL);



ALTER TABLE pbandi_t_pagamento
	ADD (CONSTRAINT  FK_PBANDI_D_MOD_PAGAMENTO_01 FOREIGN KEY (id_modalita_pagamento) REFERENCES pbandi_d_modalita_pagamento(id_modalita_pagamento));


ALTER TABLE pbandi_t_pagamento
	ADD (CONSTRAINT  FK_PBANDI_D_STATO_VAL_SPESA_02 FOREIGN KEY (id_stato_validazione_spesa) REFERENCES pbandi_d_stato_validaz_spesa(id_stato_validazione_spesa));



ALTER TABLE pbandi_t_persona_fisica
	ADD (CONSTRAINT  FK_PBANDI_T_SOGGETTO_03 FOREIGN KEY (id_soggetto) REFERENCES pbandi_t_soggetto(id_soggetto));


ALTER TABLE pbandi_t_persona_fisica
	ADD (CONSTRAINT  FK_PBANDI_D_COMUNE_01 FOREIGN KEY (id_comune_italiano_nascita) REFERENCES pbandi_d_comune(id_comune) ON DELETE SET NULL);



ALTER TABLE pbandi_t_persona_fisica
	ADD (CONSTRAINT  FK_PBANDI_D_COMUNE_ESTERO_01 FOREIGN KEY (id_comune_estero_nascita) REFERENCES pbandi_d_comune_estero(id_comune_estero) ON DELETE SET NULL);



ALTER TABLE pbandi_t_persona_fisica
	ADD (CONSTRAINT  FK_PBANDI_D_NAZIONE_03 FOREIGN KEY (id_nazione_nascita) REFERENCES pbandi_d_nazione(id_nazione) ON DELETE SET NULL);	
	
ALTER TABLE pbandi_t_persona_fisica
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_108 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_persona_fisica
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_109 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);
	
	
ALTER TABLE pbandi_t_piano_finanziario
	ADD (CONSTRAINT  FK_PBANDI_D_LINEA_INTERV_03 FOREIGN KEY (id_linea_di_intervento) REFERENCES pbandi_d_linea_di_intervento(id_linea_di_intervento));



ALTER TABLE pbandi_t_piano_finanziario
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_33 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_piano_finanziario
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_34 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_pratica_istruttoria
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_41 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_pratica_istruttoria
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_42 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_pratica_istruttoria
	ADD (CONSTRAINT  FK_PBANDI_T_DOMANDA_02 FOREIGN KEY (id_domanda) REFERENCES pbandi_t_domanda(id_domanda));



ALTER TABLE pbandi_t_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_49 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_50 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_01 FOREIGN KEY (id_progetto_padre) REFERENCES pbandi_t_progetto(id_progetto) ON DELETE SET NULL);



ALTER TABLE pbandi_t_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_DOMANDA_04 FOREIGN KEY (id_domanda) REFERENCES pbandi_t_domanda(id_domanda));



ALTER TABLE pbandi_t_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_OPERAZIONE_01 FOREIGN KEY (id_tipo_operazione) REFERENCES pbandi_d_tipo_operazione(id_tipo_operazione));



ALTER TABLE pbandi_t_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_STATO_PROGETTO_01 FOREIGN KEY (id_stato_progetto) REFERENCES pbandi_d_stato_progetto(id_stato_progetto));



ALTER TABLE pbandi_t_quota_parte_doc_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_67 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_quota_parte_doc_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_68 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_quota_parte_doc_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_DOC_DI_SPESA_02 FOREIGN KEY (id_documento_di_spesa) REFERENCES pbandi_t_documento_di_spesa(id_documento_di_spesa));



ALTER TABLE pbandi_t_quota_parte_doc_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_05 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_t_quota_parte_doc_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_RIGO_CONTO_ECON_01 FOREIGN KEY (id_rigo_conto_economico) REFERENCES pbandi_t_rigo_conto_economico(id_rigo_conto_economico));



ALTER TABLE pbandi_t_raggruppamento
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_55 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_raggruppamento
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_56 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_raggruppamento
	ADD (CONSTRAINT  FK_PBANDI_T_DOMANDA_06 FOREIGN KEY (id_domanda) REFERENCES pbandi_t_domanda(id_domanda));



ALTER TABLE pbandi_t_raggruppamento
	ADD (CONSTRAINT  FK_PBANDI_D_AREA_SCIENTIF_01 FOREIGN KEY (id_area_scientifica) REFERENCES pbandi_d_area_scientifica(id_area_scientifica) ON DELETE SET NULL);



ALTER TABLE pbandi_t_raggruppamento
	ADD (CONSTRAINT  FK_PBANDI_D_TEMATICA_01 FOREIGN KEY (id_tematica) REFERENCES pbandi_d_tematica(id_tematica) ON DELETE SET NULL);



ALTER TABLE pbandi_t_raggruppamento
	ADD (CONSTRAINT  FK_PBANDI_T_COORDINATORE_01 FOREIGN KEY (id_coordinatore) REFERENCES pbandi_t_coordinatore(id_coordinatore) ON DELETE SET NULL);



ALTER TABLE pbandi_t_recapiti
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_11 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_recapiti
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_12 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_rigo_conto_economico
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_47 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_rigo_conto_economico
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_48 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_rigo_conto_economico
	ADD (CONSTRAINT  FK_PBANDI_T_CONTO_ECONOMICO_02 FOREIGN KEY (id_conto_economico) REFERENCES pbandi_t_conto_economico(id_conto_economico));



ALTER TABLE pbandi_t_rigo_conto_economico
	ADD (CONSTRAINT  FK_PBANDI_D_VOCE_DI_SPESA_03 FOREIGN KEY (id_voce_di_spesa) REFERENCES pbandi_d_voce_di_spesa(id_voce_di_spesa));



ALTER TABLE pbandi_t_sede
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_09 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_sede
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_10 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_sede
	ADD (CONSTRAINT  FK_PBANDI_D_ATTIVITA_ATECO_01 FOREIGN KEY (id_attivita_ateco) REFERENCES pbandi_d_attivita_ateco(id_attivita_ateco) ON DELETE SET NULL);



ALTER TABLE pbandi_t_soggetto
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_03 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_soggetto
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_04 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_soggetto
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_SOGGETTO_01 FOREIGN KEY (id_tipo_soggetto) REFERENCES pbandi_d_tipo_soggetto(id_tipo_soggetto));

ALTER TABLE pbandi_t_soggetto
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_ANAGRAFICA_05 FOREIGN KEY (id_tipo_anagrafica) REFERENCES pbandi_d_tipo_anagrafica(id_tipo_anagrafica) ON DELETE SET NULL);


ALTER TABLE pbandi_t_tracciamento
    ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_97 FOREIGN KEY (id_utente) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_tracciamento
	ADD (CONSTRAINT  FK_PBANDI_C_OPERAZIONE_01 FOREIGN KEY (id_operazione) REFERENCES pbandi_c_operazione(id_operazione));



ALTER TABLE pbandi_t_tracciamento
	ADD (CONSTRAINT  FK_PBANDI_C_ENTITA_01 FOREIGN KEY (id_entita) REFERENCES pbandi_c_entita(id_entita) ON DELETE SET NULL);



ALTER TABLE pbandi_t_tracciamento
	ADD (CONSTRAINT  FK_PBANDI_C_ATTRIBUTO_01 FOREIGN KEY (id_attributo) REFERENCES pbandi_c_attributo(id_attributo) ON DELETE SET NULL);



ALTER TABLE pbandi_t_utente
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_ACCESSO_01 FOREIGN KEY (id_tipo_accesso) REFERENCES pbandi_d_tipo_accesso(id_tipo_accesso));



ALTER TABLE pbandi_t_utente
	ADD (CONSTRAINT  FK_PBANDI_T_SOGGETTO_13 FOREIGN KEY (id_soggetto) REFERENCES pbandi_t_soggetto(id_soggetto) ON DELETE SET NULL);

CREATE TABLE pbandi_d_qualifica
(
	id_qualifica	  NUMBER(3)  NOT NULL ,
	desc_breve_qualifica  VARCHAR2(20)  NOT NULL ,
	desc_qualifica	  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita  DATE  NOT NULL ,
	dt_fine_validita  DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_qualifica ON pbandi_d_qualifica
(id_qualifica  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_qualifica
	ADD CONSTRAINT  PK_pbandi_d_qualifica PRIMARY KEY (id_qualifica);



CREATE TABLE pbandi_r_fornitore_qualifica
(
	id_fornitore          NUMBER(8)  NOT NULL ,
	id_qualifica          NUMBER(3)  NOT NULL ,
	monte_ore             NUMBER(8,2)  NULL ,
	costo_orario          NUMBER(17,2)  NULL ,
	dt_fine_validita      DATE  NULL ,
	costo_risorsa         NUMBER(17,2)  NULL ,
	progr_fornitore_qualifica  NUMBER(8)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_fornitore_qualific ON pbandi_r_fornitore_qualifica
(progr_fornitore_qualifica  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_fornitore_qualifica
	ADD CONSTRAINT  PK_pbandi_r_fornitore_qualific PRIMARY KEY (progr_fornitore_qualifica);



ALTER TABLE pbandi_r_fornitore_qualifica
	ADD (CONSTRAINT  FK_PBANDI_T_FORNITORE_02 FOREIGN KEY (id_fornitore) REFERENCES pbandi_t_fornitore(id_fornitore));



ALTER TABLE pbandi_r_fornitore_qualifica
	ADD (CONSTRAINT  FK_PBANDI_D_QUALIFICA_01 FOREIGN KEY (id_qualifica) REFERENCES pbandi_d_qualifica(id_qualifica));


CREATE TABLE pbandi_d_tipo_ente_competenza
(
	id_tipo_ente_competenza  NUMBER(3)  NOT NULL ,
	desc_breve_tipo_ente_competenz  VARCHAR2(20)  NOT NULL ,
	desc_tipo_ente_competenza  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_ente_competen ON pbandi_d_tipo_ente_competenza
(id_tipo_ente_competenza  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_ente_competenza
	ADD CONSTRAINT  PK_pbandi_d_tipo_ente_competen PRIMARY KEY (id_tipo_ente_competenza);

CREATE TABLE pbandi_d_settore
(
	id_settore            NUMBER(3)  NOT NULL ,
	desc_breve_settore    VARCHAR2(20)  NOT NULL ,
	desc_settore          VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_direzione_regionale  NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_settore ON pbandi_d_settore
(id_settore  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_settore
	ADD CONSTRAINT  PK_pbandi_d_settore PRIMARY KEY (id_settore);



ALTER TABLE pbandi_d_settore
	ADD (CONSTRAINT  FK_PBANDI_D_DIREZ_REGIONALE_01 FOREIGN KEY (id_direzione_regionale) REFERENCES pbandi_d_direzione_regionale(id_direzione_regionale));

CREATE TABLE pbandi_d_ruolo_ente_competenza
(
	id_ruolo_ente_competenza  NUMBER(3)  NOT NULL ,
	desc_breve_ruolo_ente  VARCHAR2(20)  NOT NULL ,
	desc_ruolo_ente       VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_ruolo_ente_compete ON pbandi_d_ruolo_ente_competenza
(id_ruolo_ente_competenza  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_ruolo_ente_competenza
	ADD CONSTRAINT  PK_pbandi_d_ruolo_ente_compete PRIMARY KEY (id_ruolo_ente_competenza);

create sequence seq_pbandi_t_ente_competenza start with 1 nocache;	

CREATE TABLE pbandi_t_ente_competenza
(
	id_ente_competenza    NUMBER(8)  NOT NULL ,
	desc_ente             VARCHAR2(255)  NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_tipo_ente_competenza  NUMBER(3)  NULL ,
	id_direzione_regionale  NUMBER(3)  NULL ,
	id_settore            NUMBER(3)  NULL ,
	id_indirizzo          NUMBER(9)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_ente_competenza ON pbandi_t_ente_competenza
(id_ente_competenza  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_ente_competenza
	ADD CONSTRAINT  PK_pbandi_t_ente_competenza PRIMARY KEY (id_ente_competenza);



ALTER TABLE pbandi_t_ente_competenza
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_ENTE_COMPE_01 FOREIGN KEY (id_tipo_ente_competenza) REFERENCES pbandi_d_tipo_ente_competenza(id_tipo_ente_competenza) ON DELETE SET NULL);



ALTER TABLE pbandi_t_ente_competenza
	ADD (CONSTRAINT  FK_PBANDI_D_DIREZ_REGIONALE_02 FOREIGN KEY (id_direzione_regionale) REFERENCES pbandi_d_direzione_regionale(id_direzione_regionale) ON DELETE SET NULL);



ALTER TABLE pbandi_t_ente_competenza
	ADD (CONSTRAINT  FK_PBANDI_D_SETTORE_01 FOREIGN KEY (id_settore) REFERENCES pbandi_d_settore(id_settore) ON DELETE SET NULL);



ALTER TABLE pbandi_t_ente_competenza
	ADD (CONSTRAINT  FK_PBANDI_T_INDIRIZZO_01 FOREIGN KEY (id_indirizzo) REFERENCES pbandi_t_indirizzo(id_indirizzo));



ALTER TABLE pbandi_t_ente_competenza
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_19 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_ente_competenza
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_20 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);

create sequence seq_pbandi_r_bando_lin_ente_co start with 1 nocache;

CREATE TABLE pbandi_r_bando_linea_ente_comp
(
	dt_fine_validita      DATE  NULL ,
	id_ruolo_ente_competenza  NUMBER(3)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	id_ente_competenza    NUMBER(8)  NOT NULL ,
	progr_bando_linea_intervento  NUMBER(8)  NOT NULL ,
	progr_bando_linea_ente_comp  NUMBER(8)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_bando_linea_ente_c ON pbandi_r_bando_linea_ente_comp
(progr_bando_linea_ente_comp  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_bando_linea_ente_comp
	ADD CONSTRAINT  PK_pbandi_r_bando_linea_ente_c PRIMARY KEY (progr_bando_linea_ente_comp);



ALTER TABLE pbandi_r_bando_linea_ente_comp
	ADD (CONSTRAINT  FK_PBANDI_D_RUOLO_ENTE_COMP_01 FOREIGN KEY (id_ruolo_ente_competenza) REFERENCES pbandi_d_ruolo_ente_competenza(id_ruolo_ente_competenza));



ALTER TABLE pbandi_r_bando_linea_ente_comp
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_91 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_bando_linea_ente_comp
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_92 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_bando_linea_ente_comp
	ADD (CONSTRAINT  FK_PBANDI_T_ENTE_COMPETENZA_01 FOREIGN KEY (id_ente_competenza) REFERENCES pbandi_t_ente_competenza(id_ente_competenza));



ALTER TABLE pbandi_r_bando_linea_ente_comp
	ADD (CONSTRAINT  FK_PBANDI_R_BANDO_LINEA_INT_02 FOREIGN KEY (progr_bando_linea_intervento) REFERENCES pbandi_r_bando_linea_intervent(progr_bando_linea_intervento));


create sequence seq_pbandi_r_ente_giurid_sede start with 1 nocache;
create sequence seq_pbandi_r_sogg_domanda_sede start with 1 nocache;

CREATE TABLE pbandi_r_soggetto_domanda_sede
(
	progr_soggetto_domanda_sede  NUMBER(8)  NOT NULL ,
	progr_soggetto_domanda  NUMBER(8)  NOT NULL ,
	id_sede               NUMBER(8)  NOT NULL ,
	id_indirizzo          NUMBER(9)  NULL ,
	id_tipo_sede          NUMBER(3)  NOT NULL ,
	id_recapiti           NUMBER(8)  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_sogg_domanda_sede ON pbandi_r_soggetto_domanda_sede
(progr_soggetto_domanda_sede  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_r_soggetto_domanda_sede
	ADD CONSTRAINT  PK_pbandi_r_sogg_domanda_sede PRIMARY KEY (progr_soggetto_domanda_sede);



ALTER TABLE pbandi_r_soggetto_domanda_sede
	ADD (CONSTRAINT  FK_PBANDI_R_SOGG_DOMANDA_01 FOREIGN KEY (progr_soggetto_domanda) REFERENCES pbandi_r_soggetto_domanda(progr_soggetto_domanda));



ALTER TABLE pbandi_r_soggetto_domanda_sede
	ADD (CONSTRAINT  FK_PBANDI_T_SEDE_02 FOREIGN KEY (id_sede) REFERENCES pbandi_t_sede(id_sede));



ALTER TABLE pbandi_r_soggetto_domanda_sede
	ADD (CONSTRAINT  FK_PBANDI_T_INDIRIZZO_07 FOREIGN KEY (id_indirizzo) REFERENCES pbandi_t_indirizzo(id_indirizzo));



ALTER TABLE pbandi_r_soggetto_domanda_sede
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_SEDE_02 FOREIGN KEY (id_tipo_sede) REFERENCES pbandi_d_tipo_sede(id_tipo_sede));



ALTER TABLE pbandi_r_soggetto_domanda_sede
	ADD (CONSTRAINT  FK_PBANDI_T_RECAPITI_04 FOREIGN KEY (id_recapiti) REFERENCES pbandi_t_recapiti(id_recapiti) ON DELETE SET NULL);



ALTER TABLE pbandi_r_soggetto_domanda_sede
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_93 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_soggetto_domanda_sede
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_94 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);

create sequence seq_pbandi_r_sogg_prog_sede start with 1 nocache;

CREATE TABLE pbandi_r_sogg_progetto_sede
(
	progr_soggetto_progetto_sede  NUMBER(8)  NOT NULL ,
	progr_soggetto_progetto  NUMBER(8)  NOT NULL ,
	id_sede               NUMBER(8)  NOT NULL ,
	id_indirizzo          NUMBER(9)  NULL ,
	id_tipo_sede          NUMBER(3)  NOT NULL ,
	id_recapiti           NUMBER(8)  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_sogg_progetto_sede ON pbandi_r_sogg_progetto_sede
(progr_soggetto_progetto_sede  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_r_sogg_progetto_sede
	ADD CONSTRAINT  PK_pbandi_r_sogg_progetto_sede PRIMARY KEY (progr_soggetto_progetto_sede);



ALTER TABLE pbandi_r_sogg_progetto_sede
	ADD (CONSTRAINT  FK_PBANDI_R_SOGG_PROGETTO_01 FOREIGN KEY (progr_soggetto_progetto) REFERENCES pbandi_r_soggetto_progetto(progr_soggetto_progetto));



ALTER TABLE pbandi_r_sogg_progetto_sede
	ADD (CONSTRAINT  FK_PBANDI_T_SEDE_03 FOREIGN KEY (id_sede) REFERENCES pbandi_t_sede(id_sede));



ALTER TABLE pbandi_r_sogg_progetto_sede
	ADD (CONSTRAINT  FK_PBANDI_T_INDIRIZZO_08 FOREIGN KEY (id_indirizzo) REFERENCES pbandi_t_indirizzo(id_indirizzo));



ALTER TABLE pbandi_r_sogg_progetto_sede
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_SEDE_03 FOREIGN KEY (id_tipo_sede) REFERENCES pbandi_d_tipo_sede(id_tipo_sede));



ALTER TABLE pbandi_r_sogg_progetto_sede
	ADD (CONSTRAINT  FK_PBANDI_T_RECAPITI_05 FOREIGN KEY (id_recapiti) REFERENCES pbandi_t_recapiti(id_recapiti) ON DELETE SET NULL);



ALTER TABLE pbandi_r_sogg_progetto_sede
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_95 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_sogg_progetto_sede
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_96 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);

CREATE SEQUENCE seq_pbandi_r_soggetto_domanda
	START WITH 1
	NOCACHE;	
	



CREATE TABLE pbandi_r_doc_spesa_progetto
(
	id_documento_di_spesa  NUMBER(8)  NOT NULL ,
	id_progetto           NUMBER(8)  NOT NULL ,
	importo_rendicontazione  NUMBER(17,2)  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	id_stato_documento_spesa  NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_doc_spesa_progetto ON pbandi_r_doc_spesa_progetto
(id_documento_di_spesa  ASC,id_progetto  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_doc_spesa_progetto
	ADD CONSTRAINT  PK_pbandi_r_doc_spesa_progetto PRIMARY KEY (id_documento_di_spesa,id_progetto);



ALTER TABLE pbandi_r_doc_spesa_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_DOC_DI_SPESA_03 FOREIGN KEY (id_documento_di_spesa) REFERENCES pbandi_t_documento_di_spesa(id_documento_di_spesa));



ALTER TABLE pbandi_r_doc_spesa_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_06 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_r_doc_spesa_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_98 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_doc_spesa_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_99 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);

ALTER TABLE pbandi_r_doc_spesa_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_STATO_DOC_SPESA_01 FOREIGN KEY (id_stato_documento_spesa) REFERENCES pbandi_d_stato_documento_spesa(id_stato_documento_spesa));	

CREATE TABLE pbandi_r_sogg_dom_sogg_correl
(
	progr_soggetto_domanda  NUMBER(8)  NOT NULL ,
	progr_soggetti_correlati  NUMBER(8)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_sogg_dom_correl ON pbandi_r_sogg_dom_sogg_correl
(progr_soggetto_domanda  ASC,progr_soggetti_correlati  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_sogg_dom_sogg_correl
	ADD CONSTRAINT  PK_pbandi_r_sogg_dom_correl PRIMARY KEY (progr_soggetto_domanda,progr_soggetti_correlati);



ALTER TABLE pbandi_r_sogg_dom_sogg_correl
	ADD (CONSTRAINT  FK_PBANDI_R_SOGG_DOMANDA_02 FOREIGN KEY (progr_soggetto_domanda) REFERENCES pbandi_r_soggetto_domanda(progr_soggetto_domanda));



ALTER TABLE pbandi_r_sogg_dom_sogg_correl
	ADD (CONSTRAINT  FK_PBANDI_R_SOGG_CORRELATI_01 FOREIGN KEY (progr_soggetti_correlati) REFERENCES pbandi_r_soggetti_correlati(progr_soggetti_correlati));



ALTER TABLE pbandi_r_sogg_dom_sogg_correl
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_100 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_sogg_dom_sogg_correl
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_101 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);	
	

CREATE TABLE pbandi_r_sogg_prog_sogg_correl
(
	progr_soggetto_progetto  NUMBER(8)  NOT NULL ,
	progr_soggetti_correlati  NUMBER(8)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_sogg_prog_sogg_cor ON pbandi_r_sogg_prog_sogg_correl
(progr_soggetto_progetto  ASC,progr_soggetti_correlati  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_sogg_prog_sogg_correl
	ADD CONSTRAINT  PK_pbandi_r_sogg_prog_sogg_cor PRIMARY KEY (progr_soggetto_progetto,progr_soggetti_correlati);



ALTER TABLE pbandi_r_sogg_prog_sogg_correl
	ADD (CONSTRAINT  FK_PBANDI_R_SOGG_PROGETTO_02 FOREIGN KEY (progr_soggetto_progetto) REFERENCES pbandi_r_soggetto_progetto(progr_soggetto_progetto));



ALTER TABLE pbandi_r_sogg_prog_sogg_correl
	ADD (CONSTRAINT  FK_PBANDI_R_SOGG_CORRELATI_02 FOREIGN KEY (progr_soggetti_correlati) REFERENCES pbandi_r_soggetti_correlati(progr_soggetti_correlati));



ALTER TABLE pbandi_r_sogg_prog_sogg_correl
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_102 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_sogg_prog_sogg_correl
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_103 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);	


CREATE TABLE pbandi_t_revoca
(
	id_revoca             NUMBER(8)  NOT NULL ,
	importo_revocato      NUMBER(17,2)  NULL ,
	dt_revoca             DATE  NULL ,
	id_progetto           NUMBER(8)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_revoca ON pbandi_t_revoca
(id_revoca  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_revoca
	ADD CONSTRAINT  PK_pbandi_t_revoca PRIMARY KEY (id_revoca);



ALTER TABLE pbandi_t_revoca
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_07 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_t_revoca
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_104 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_revoca
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_105 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);

create sequence seq_pbandi_t_revoca start with 1 nocache;	

CREATE TABLE pbandi_r_ente_competenza_sogg
(
	id_ente_competenza    NUMBER(8)  NOT NULL ,
	id_soggetto           NUMBER(8)  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_ente_competenza_so ON pbandi_r_ente_competenza_sogg
(id_ente_competenza  ASC,id_soggetto  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_ente_competenza_sogg
	ADD CONSTRAINT  PK_pbandi_r_ente_competenza_so PRIMARY KEY (id_ente_competenza,id_soggetto);



ALTER TABLE pbandi_r_ente_competenza_sogg
	ADD (CONSTRAINT  FK_PBANDI_T_ENTE_COMPETENZA_02 FOREIGN KEY (id_ente_competenza) REFERENCES pbandi_t_ente_competenza(id_ente_competenza));



ALTER TABLE pbandi_r_ente_competenza_sogg
	ADD (CONSTRAINT  FK_PBANDI_T_SOGGETTO_16 FOREIGN KEY (id_soggetto) REFERENCES pbandi_t_soggetto(id_soggetto));



ALTER TABLE pbandi_r_ente_competenza_sogg
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_106 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_ente_competenza_sogg
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_107 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);

create sequence seq_pbandi_r_soggetto_progetto start with 1 nocache;	

CREATE TABLE pbandi_t_dichiarazione_spesa
(
	id_dichiarazione_spesa  NUMBER(8)  NOT NULL ,
	dt_dichiarazione      DATE  NOT NULL ,
	periodo_dal           DATE  NOT NULL ,
	periodo_al            DATE  NOT NULL ,
	id_progetto           NUMBER(8)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	dt_chiusura_validazione date,
	note_chiusura_validazione varchar2(4000)
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_dichiarazione_spes ON pbandi_t_dichiarazione_spesa
(id_dichiarazione_spesa  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_dichiarazione_spesa
	ADD CONSTRAINT  PK_pbandi_t_dichiarazione_spes PRIMARY KEY (id_dichiarazione_spesa);



ALTER TABLE pbandi_t_dichiarazione_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_08 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_t_dichiarazione_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_112 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_dichiarazione_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_113 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);

CREATE TABLE pbandi_r_pagamento_dich_spesa
(
	id_pagamento          NUMBER(8)  NOT NULL ,
	id_dichiarazione_spesa  NUMBER(8)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_pagamento_dich_spe ON pbandi_r_pagamento_dich_spesa
(id_pagamento  ASC,id_dichiarazione_spesa  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_pagamento_dich_spesa
	ADD CONSTRAINT  PK_pbandi_r_pagamento_dich_spe PRIMARY KEY (id_pagamento,id_dichiarazione_spesa);



ALTER TABLE pbandi_r_pagamento_dich_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_PAGAMENTO_03 FOREIGN KEY (id_pagamento) REFERENCES pbandi_t_pagamento(id_pagamento));



ALTER TABLE pbandi_r_pagamento_dich_spesa
	ADD (CONSTRAINT  FK_PBANDI_T_DICHIARAZ_SPESA_01 FOREIGN KEY (id_dichiarazione_spesa) REFERENCES pbandi_t_dichiarazione_spesa(id_dichiarazione_spesa));

CREATE TABLE pbandi_c_regola
(
	id_regola             NUMBER(8)  NOT NULL ,
	desc_breve_regola     VARCHAR2(20)  NOT NULL ,
	desc_regola           VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_c_regola ON pbandi_c_regola
(id_regola  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_c_regola
	ADD CONSTRAINT  PK_pbandi_c_regola PRIMARY KEY (id_regola);



CREATE TABLE pbandi_c_ruolo_processo
(
	id_ruolo_processo     NUMBER(8)  NOT NULL ,
	codice_ruolo          VARCHAR2(255)  NOT NULL ,
	definizione_processo  VARCHAR2(255)  NOT NULL ,
	id_tipo_anagrafica    NUMBER(3)  NOT NULL ,
	id_tipo_beneficiario  NUMBER(3)  NULL
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_c_ruolo_processo ON pbandi_c_ruolo_processo
(id_ruolo_processo  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_c_ruolo_processo
	ADD CONSTRAINT  PK_pbandi_c_ruolo_processo PRIMARY KEY (id_ruolo_processo);


ALTER TABLE pbandi_c_ruolo_processo
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_ANAGRAFICA_03 FOREIGN KEY (id_tipo_anagrafica) REFERENCES pbandi_d_tipo_anagrafica(id_tipo_anagrafica));

ALTER TABLE pbandi_c_ruolo_processo
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_BENEFIC_04 FOREIGN KEY (id_tipo_beneficiario) REFERENCES pbandi_d_tipo_beneficiario(id_tipo_beneficiario) ON DELETE SET NULL);	
	
CREATE TABLE pbandi_r_regola_bando_linea
(
	id_regola             NUMBER(8)  NOT NULL ,
	progr_bando_linea_intervento  NUMBER(8)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_regola_bando_linea ON pbandi_r_regola_bando_linea
(progr_bando_linea_intervento  ASC,id_regola  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_regola_bando_linea
	ADD CONSTRAINT  PK_pbandi_r_regola_bando_linea PRIMARY KEY (progr_bando_linea_intervento,id_regola);



ALTER TABLE pbandi_r_regola_bando_linea
	ADD (CONSTRAINT  FK_PBANDI_C_REGOLA_01 FOREIGN KEY (id_regola) REFERENCES pbandi_c_regola(id_regola));



ALTER TABLE pbandi_r_regola_bando_linea
	ADD (CONSTRAINT  FK_PBANDI_R_BANDO_LINEA_INT_03 FOREIGN KEY (progr_bando_linea_intervento) REFERENCES pbandi_r_bando_linea_intervent(progr_bando_linea_intervento));

CREATE TABLE pbandi_r_regola_tipo_anag
(
	id_regola             NUMBER(8)  NOT NULL ,
	id_tipo_anagrafica    NUMBER(3)  NOT NULL ,
	id_tipo_beneficiario  NUMBER(3)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX AK1_pbandi_r_regola_tipo_anag ON pbandi_r_regola_tipo_anag
(id_tipo_anagrafica  ASC,id_regola  ASC,id_tipo_beneficiario  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_regola_tipo_anag
ADD CONSTRAINT  AK1_pbandi_r_regola_tipo_anag UNIQUE (id_tipo_anagrafica,id_regola,id_tipo_beneficiario);



ALTER TABLE pbandi_r_regola_tipo_anag
	ADD (CONSTRAINT  FK_PBANDI_C_REGOLA_02 FOREIGN KEY (id_regola) REFERENCES pbandi_c_regola(id_regola));



ALTER TABLE pbandi_r_regola_tipo_anag
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_ANAGRAFICA_04 FOREIGN KEY (id_tipo_anagrafica) REFERENCES pbandi_d_tipo_anagrafica(id_tipo_anagrafica));



ALTER TABLE pbandi_r_regola_tipo_anag
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_BENEFIC_03 FOREIGN KEY (id_tipo_beneficiario) REFERENCES pbandi_d_tipo_beneficiario(id_tipo_beneficiario) ON DELETE SET NULL);


CREATE TABLE pbandi_r_pag_quot_parte_doc_sp
(
	id_pagamento          NUMBER(8)  NOT NULL ,
	id_quota_parte_doc_spesa  NUMBER(8)  NOT NULL ,
	importo_quietanzato   NUMBER(17,2)  NOT NULL ,
	importo_validato      NUMBER(17,2)  NULL ,
	flag_monit            CHAR(1)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_pag_quot_parte_doc ON pbandi_r_pag_quot_parte_doc_sp
(id_pagamento  ASC,id_quota_parte_doc_spesa  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_pag_quot_parte_doc_sp
	ADD CONSTRAINT  PK_pbandi_r_pag_quot_parte_doc PRIMARY KEY (id_pagamento,id_quota_parte_doc_spesa);


ALTER TABLE pbandi_r_pag_quot_parte_doc_sp
	MODIFY flag_monit CONSTRAINT  ck_flag_09 CHECK (flag_monit in ('S','N'));


ALTER TABLE pbandi_r_pag_quot_parte_doc_sp
	MODIFY flag_monit DEFAULT 'N';



CREATE TABLE pbandi_t_recupero
(
	id_recupero           NUMBER(8)  NOT NULL ,
	importo_recupero      NUMBER(17,2)  NULL ,
	flag_monit            CHAR(1)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	id_pagamento          NUMBER(8)  NOT NULL ,
	id_quota_parte_doc_spesa  NUMBER(8)  NOT NULL 
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_recupero ON pbandi_t_recupero
(id_recupero  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_t_recupero
	ADD CONSTRAINT  PK_pbandi_t_recupero PRIMARY KEY (id_recupero);


ALTER TABLE pbandi_t_recupero
	MODIFY flag_monit CONSTRAINT  ck_flag_16 CHECK (flag_monit in ('S','N'));



ALTER TABLE pbandi_r_pag_quot_parte_doc_sp
	ADD (CONSTRAINT  FK_PBANDI_T_PAGAMENTO_02 FOREIGN KEY (id_pagamento) REFERENCES pbandi_t_pagamento(id_pagamento));



ALTER TABLE pbandi_r_pag_quot_parte_doc_sp
	ADD (CONSTRAINT  FK_PBANDI_T_QT_PARTE_DOC_SP_01 FOREIGN KEY (id_quota_parte_doc_spesa) REFERENCES pbandi_t_quota_parte_doc_spesa(id_quota_parte_doc_spesa));



ALTER TABLE pbandi_r_pag_quot_parte_doc_sp
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_71 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_pag_quot_parte_doc_sp
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_72 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_recupero
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_73 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_recupero
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_74 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_t_recupero
	ADD (CONSTRAINT  FK_PBANDI_R_PG_QT_PR_DOC_SP_01 FOREIGN KEY (id_pagamento,id_quota_parte_doc_spesa) REFERENCES pbandi_r_pag_quot_parte_doc_sp(id_pagamento,id_quota_parte_doc_spesa));

CREATE TABLE pbandi_d_tipo_aiuto
(
	id_tipo_aiuto         NUMBER(3)  NOT NULL ,
	desc_breve_tipo_aiuto  VARCHAR2(20)  NOT NULL ,
	desc_tipo_aiuto       VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	cod_igrue_t1          VARCHAR2(10)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_aiuto ON pbandi_d_tipo_aiuto
(id_tipo_aiuto  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_aiuto
	ADD CONSTRAINT  PK_pbandi_d_tipo_aiuto PRIMARY KEY (id_tipo_aiuto);



CREATE TABLE pbandi_r_bando_tipo_aiuto
(
	id_bando              NUMBER(8)  NOT NULL ,
	id_tipo_aiuto         NUMBER(3)  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_bando_tipo_aiuto ON pbandi_r_bando_tipo_aiuto
(id_bando  ASC,id_tipo_aiuto  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_bando_tipo_aiuto
	ADD CONSTRAINT  PK_pbandi_r_bando_tipo_aiuto PRIMARY KEY (id_bando,id_tipo_aiuto);



ALTER TABLE pbandi_r_bando_tipo_aiuto
	ADD (CONSTRAINT  FK_PBANDI_T_BANDO_04 FOREIGN KEY (id_bando) REFERENCES pbandi_t_bando(id_bando));



ALTER TABLE pbandi_r_bando_tipo_aiuto
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_AIUTO_01 FOREIGN KEY (id_tipo_aiuto) REFERENCES pbandi_d_tipo_aiuto(id_tipo_aiuto));
	
alter table pbandi_t_progetto
add id_tipo_aiuto number(3);

ALTER TABLE pbandi_t_progetto
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_AIUTO_02 FOREIGN KEY (id_tipo_aiuto) REFERENCES pbandi_d_tipo_aiuto(id_tipo_aiuto) ON DELETE SET NULL);


alter table PBANDI_W_COSTANTI
drop (TITOLO_BANDO);	

alter table PBANDI_W_COSTANTI
drop (DESC_BREVE_LINEA);

alter table PBANDI_W_COSTANTI
add NORMA_INCENTIVAZIONE varchar2(255);

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
	target		  		  VARCHAR2(50) NOT NULL
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
	(ID_LOG_BATCH,ID_PROCESSO_BATCH,,DT_INSERIMENTO,CODICE_ERRORE,MESSAGGIO_ERRORE)
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

