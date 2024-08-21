/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

create sequence seq_pbandi_t_scarti_car_ma_pag start with 1 nocache;

CREATE TABLE pbandi_t_scarti_carica_mas_pag
(
	id_scarti_carica_mas_pag  NUMBER  NOT NULL ,
	numero_documento      VARCHAR2(100)  NULL ,
	dt_documento          DATE  NULL ,
	desc_tipo_doc_spesa   VARCHAR2(100)  NULL ,
	codice_fiscale_fornitore  VARCHAR2(32)  NULL ,
	codice_errore         VARCHAR2(10)  NOT NULL ,
	id_carica_mass_doc_spesa  NUMBER  NOT NULL ,
	importo_pagamento     NUMBER(17,2)  NULL ,
	dt_pagamento          DATE  NULL ,
	desc_breve_modalita_pagamento  VARCHAR2(20)  NULL 
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_scarti_carica_mas_ ON pbandi_t_scarti_carica_mas_pag
(id_scarti_carica_mas_pag  ASC)
	TABLESPACE pbandi_small_idx;



ALTER TABLE pbandi_t_scarti_carica_mas_pag
	ADD CONSTRAINT  PK_pbandi_t_scarti_carica_mas_ PRIMARY KEY (id_scarti_carica_mas_pag);



ALTER TABLE pbandi_t_scarti_carica_mas_pag
	ADD (CONSTRAINT  FK_PBANDI_T_CARIC_MA_DOC_SP_03 FOREIGN KEY (id_carica_mass_doc_spesa) REFERENCES pbandi_t_carica_mass_doc_spesa(id_carica_mass_doc_spesa));



ALTER TABLE pbandi_t_scarti_carica_mas_pag
	ADD (CONSTRAINT  FK_PBANDI_D_ERRORE_BATCH_04 FOREIGN KEY (codice_errore) REFERENCES pbandi_d_errore_batch(codice_errore));

create sequence seq_pbandi_w_attivita_pregress start with 1 nocache;

CREATE TABLE pbandi_c_attivita_pregresse
(
	cod_elemento          VARCHAR2(1)  NOT NULL ,
	nome_colonna          VARCHAR2(30)  NOT NULL ,
	nome_etichetta        VARCHAR2(100)  NOT NULL 
)
	TABLESPACE pbandi_small_tbl;



CREATE TABLE pbandi_w_attivita_pregresse
(
	id_attivita_pregresse  NUMBER  NULL ,
	id_progetto           NUMBER(8)  NOT NULL ,
	cod_elemento          VARCHAR2(1)  NOT NULL ,
	desc_elemento         VARCHAR2(200)  NOT NULL ,
	data                  DATE  NOT NULL ,
	id_conto_economico    NUMBER(8)  NULL ,
	id_indicatori         NUMBER(3)  NULL ,
	id_domanda            NUMBER(8)  NULL ,
	id_fase_monit         NUMBER(3)  NULL ,
	importo_ammesso       NUMBER(17,2)  NULL ,
	importo_agevolato     NUMBER(17,2)  NULL ,
	importo_impegno       NUMBER(17,2)  NULL ,
	ribasso_asta          VARCHAR2(500)  NULL ,
	periodi               VARCHAR2(500)  NULL ,
	importo_rendicontato  NUMBER(17,2)  NULL ,
	importo_quietanzato   NUMBER(17,2)  NULL ,
	importo_validato      NUMBER(17,2)  NULL ,
	note                  VARCHAR2(4000)  NULL ,
	motivo                VARCHAR2(500)  NULL ,
	modalita              VARCHAR2(200)  NULL ,
	causale               VARCHAR2(200)  NULL ,
	ente                  VARCHAR2(200)  NULL ,
	importo_saldo         NUMBER(17,2)  NULL ,
	numero_ims            VARCHAR2(200)  NULL ,
	data_ims              VARCHAR2(200)  NULL ,
	soggetto              VARCHAR2(200)  NULL ,
	data_decorrenza       VARCHAR2(200)  NULL ,
	stato                 VARCHAR2(200)  NULL ,
	dt_inserimento        DATE  NOT NULL
)
	TABLESPACE PBANDI_MEDIUM_TBL;



ALTER TABLE pbandi_w_attivita_pregresse
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_32 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_w_attivita_pregresse
	ADD (CONSTRAINT  FK_PBANDI_T_CONTO_ECONOMICO_04 FOREIGN KEY (id_conto_economico) REFERENCES pbandi_t_conto_economico(id_conto_economico));



ALTER TABLE pbandi_w_attivita_pregresse
	ADD (CONSTRAINT  FK_PBANDI_R_DOMANDA_INDICAT_01 FOREIGN KEY (id_domanda,id_indicatori) REFERENCES pbandi_r_domanda_indicatori(id_domanda,id_indicatori));



ALTER TABLE pbandi_w_attivita_pregresse
	ADD (CONSTRAINT  FK_PBANDI_D_FASE_MONIT_03 FOREIGN KEY (id_fase_monit) REFERENCES pbandi_d_fase_monit(id_fase_monit));

alter TABLE pbandi_t_pagamento
add id_carica_mass_doc_spesa  NUMBER  NULL ;

ALTER TABLE pbandi_t_pagamento
	ADD (CONSTRAINT  FK_PBANDI_T_CARIC_MA_DOC_SP_04 FOREIGN KEY (id_carica_mass_doc_spesa) REFERENCES pbandi_t_carica_mass_doc_spesa(id_carica_mass_doc_spesa));

CREATE OR REPLACE VIEW PBANDI_V_PERSONA_FISICA
(
   ID_SOGGETTO,
   COGNOME,
   NOME,
   DT_NASCITA,
   SESSO,
   ID_PERSONA_FISICA,
   DT_INIZIO_VALIDITA,
   DT_FINE_VALIDITA,
   ID_COMUNE_ITALIANO_NASCITA,
   ID_COMUNE_ESTERO_NASCITA,
   ID_NAZIONE_NASCITA,
   ID_UTENTE_INS,
   ID_UTENTE_AGG,
   ID_CITTADINANZA,
   ID_TITOLO_STUDIO,
   ID_OCCUPAZIONE
)
AS
   SELECT ID_SOGGETTO,
          COGNOME,
          NOME,
          DT_NASCITA,
          SESSO,
          ID_PERSONA_FISICA,
          DT_INIZIO_VALIDITA,
          DT_FINE_VALIDITA,
          ID_COMUNE_ITALIANO_NASCITA,
          ID_COMUNE_ESTERO_NASCITA,
          ID_NAZIONE_NASCITA,
          ID_UTENTE_INS,
          ID_UTENTE_AGG,
          ID_CITTADINANZA,
          ID_TITOLO_STUDIO,
          ID_OCCUPAZIONE
     FROM pbandi_t_persona_fisica pf
    WHERE pf.id_persona_fisica IN
             (SELECT FIRST_VALUE (
                        pf1.id_persona_fisica)
                     OVER (
                        ORDER BY
                           pf1.dt_inizio_validita DESC,
                           pf1.id_persona_fisica DESC)
                FROM pbandi_t_persona_fisica pf1
               WHERE pf1.id_soggetto = pf.id_soggetto);
			   
create sequence seq_pbandi_t_flussi_upload start with 1 nocache;

CREATE TABLE pbandi_t_flussi_upload
(
	id_flusso             NUMBER  NOT NULL ,
	nome_flusso           VARCHAR2(255)  NOT NULL ,
	dt_acquisizione       DATE  NOT NULL ,
	id_utente_upload      NUMBER(8)  NOT NULL ,
	id_processo_batch     NUMBER  NULL 
)
	TABLESPACE pbandi_small_tbl;



CREATE UNIQUE INDEX PK_pbandi_t_flussi_upload ON pbandi_t_flussi_upload
(id_flusso  ASC)
	TABLESPACE pbandi_small_idx;



ALTER TABLE pbandi_t_flussi_upload
	ADD CONSTRAINT  PK_pbandi_t_flussi_upload PRIMARY KEY (id_flusso);



ALTER TABLE pbandi_t_flussi_upload
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_244 FOREIGN KEY (id_utente_upload) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_flussi_upload
	ADD (CONSTRAINT  FK_PBANDI_L_PROCESSO_BATCH_02 FOREIGN KEY (id_processo_batch) REFERENCES pbandi_l_processo_batch(id_processo_batch));

create sequence seq_pbandi_t_utenti_upload	 start with 1 nocache;

CREATE TABLE pbandi_t_utenti_upload
(
	id_utenti_upload      NUMBER  NOT NULL ,
	cod_fiscale           VARCHAR2(32)  NOT NULL ,
	nome                  VARCHAR2(150)  NOT NULL ,
	cognome               VARCHAR2(150)  NOT NULL ,
	codice_visualizzato   VARCHAR2(100)  NOT NULL ,
	flag_rappr_legale     CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_66 CHECK (flag_rappr_legale in ('S','N')),
	id_flusso             NUMBER  NOT NULL ,
	dt_acquisizione       DATE  NULL ,
	id_log_batch          NUMBER  NULL 
)
TABLESPACE pbandi_small_tbl;



CREATE UNIQUE INDEX PK_pbandi_t_utenti_upload ON pbandi_t_utenti_upload
(id_utenti_upload  ASC)
TABLESPACE pbandi_small_idx;



ALTER TABLE pbandi_t_utenti_upload
	ADD CONSTRAINT  PK_pbandi_t_utenti_upload PRIMARY KEY (id_utenti_upload);



ALTER TABLE pbandi_t_utenti_upload
	ADD (CONSTRAINT  FK_PBANDI_T_FLUSSI_UPLOAD_01 FOREIGN KEY (id_flusso) REFERENCES pbandi_t_flussi_upload(id_flusso));



ALTER TABLE pbandi_t_utenti_upload
	ADD (CONSTRAINT  FK_PBANDI_L_LOG_BATCH_01 FOREIGN KEY (id_log_batch) REFERENCES pbandi_l_log_batch(id_log_batch));


CREATE TABLE pbandi_c_ente_recapiti
(
	id_ente_competenza    NUMBER(8)  NOT NULL ,
	email                 VARCHAR2(255)  NOT NULL ,
	id_nome_batch         NUMBER(3)  NOT NULL 
)TABLESPACE pbandi_small_tbl;



ALTER TABLE pbandi_c_ente_recapiti
	ADD (CONSTRAINT  FK_PBANDI_T_ENTE_COMPETENZA_11 FOREIGN KEY (id_ente_competenza) REFERENCES pbandi_t_ente_competenza(id_ente_competenza));



ALTER TABLE pbandi_c_ente_recapiti
	ADD (CONSTRAINT  FK_PBANDI_D_NOME_BATCH_02 FOREIGN KEY (id_nome_batch) REFERENCES pbandi_d_nome_batch(id_nome_batch));

	
CREATE INDEX IE1_pbandi_t_utenti_upload ON pbandi_t_utenti_upload
(id_flusso  ASC)
	TABLESPACE pbandi_small_idx;

CREATE TABLE pbandi_r_sogg_ben_associato
(
	id_soggetto           NUMBER(8)  NOT NULL ,
	id_soggetto_beneficiario  NUMBER(8)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE pbandi_small_tbl;



CREATE UNIQUE INDEX PK_pbandi_r_sogg_ben_associato ON pbandi_r_sogg_ben_associato
(id_soggetto  ASC,id_soggetto_beneficiario  ASC)
	TABLESPACE pbandi_small_idx;



ALTER TABLE pbandi_r_sogg_ben_associato
	ADD CONSTRAINT  PK_pbandi_r_sogg_ben_associato PRIMARY KEY (id_soggetto,id_soggetto_beneficiario);



ALTER TABLE pbandi_r_sogg_ben_associato
	ADD (CONSTRAINT  FK_PBANDI_T_SOGGETTO_19 FOREIGN KEY (id_soggetto) REFERENCES pbandi_t_soggetto(id_soggetto));



ALTER TABLE pbandi_r_sogg_ben_associato
	ADD (CONSTRAINT  FK_PBANDI_T_SOGGETTO_20 FOREIGN KEY (id_soggetto_beneficiario) REFERENCES pbandi_t_soggetto(id_soggetto));



ALTER TABLE pbandi_r_sogg_ben_associato
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_245 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_sogg_ben_associato
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_246 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));
	
alter table pbandi_w_attivita_pregresse
add id_documento_index number(8);

ALTER TABLE pbandi_w_attivita_pregresse
 ADD (CONSTRAINT  FK_PBANDI_T_DOCUMENTO_INDEX_03 FOREIGN KEY (id_documento_index) REFERENCES pbandi_t_documento_index(id_documento_index));

alter table PBANDI_T_MANDATO_QUIETANZATO
add anno_esercizio number(4);
 