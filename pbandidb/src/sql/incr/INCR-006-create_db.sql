/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

create sequence seq_pbandi_t_irregolarita start with 1 nocache;

CREATE TABLE pbandi_d_disp_comunitaria
(
	id_disp_comunitaria   NUMBER(3)  NOT NULL ,
	desc_breve_disp_comunitaria  VARCHAR2(20)  NULL ,
	desc_disp_comunitaria  VARCHAR2(20)  NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_disp_comunitaria ON pbandi_d_disp_comunitaria
(id_disp_comunitaria  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_disp_comunitaria
	ADD CONSTRAINT  PK_pbandi_d_disp_comunitaria PRIMARY KEY (id_disp_comunitaria);



CREATE TABLE pbandi_d_metodo_individuazione
(
	id_metodo_individuazione  NUMBER(3)  NOT NULL ,
	desc_breve_metodo_ind  VARCHAR2(20)  NOT NULL ,
	desc_metodo_ind       VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_metodo_individuazi ON pbandi_d_metodo_individuazione
(id_metodo_individuazione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_metodo_individuazione
	ADD CONSTRAINT  PK_pbandi_d_metodo_individuazi PRIMARY KEY (id_metodo_individuazione);



CREATE TABLE pbandi_d_natura_sanzione
(
	id_natura_sanzione    NUMBER(3)  NOT NULL ,
	desc_breve_natura_sanzione  VARCHAR2(20)  NOT NULL ,
	desc_natura_sanzione  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_natura_sanzione ON pbandi_d_natura_sanzione
(id_natura_sanzione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_natura_sanzione
	ADD CONSTRAINT  PK_pbandi_d_natura_sanzione PRIMARY KEY (id_natura_sanzione);



CREATE TABLE pbandi_d_qualificazione_irreg
(
	id_qualificazione_irreg  NUMBER(3)  NOT NULL ,
	desc_breve_qualific_irreg  VARCHAR2(20)  NOT NULL ,
	desc_qualificazione_irreg  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_qualificazione_irr ON pbandi_d_qualificazione_irreg
(id_qualificazione_irreg  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_qualificazione_irreg
	ADD CONSTRAINT  PK_pbandi_d_qualificazione_irr PRIMARY KEY (id_qualificazione_irreg);



CREATE TABLE pbandi_d_stato_amministrativo
(
	id_stato_amministrativo  NUMBER(3)  NOT NULL ,
	desc_breve_stato_amministrativ  VARCHAR2(20)  NOT NULL ,
	desc_stato_amministrativo  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_stato_amministrati ON pbandi_d_stato_amministrativo
(id_stato_amministrativo  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_stato_amministrativo
	ADD CONSTRAINT  PK_pbandi_d_stato_amministrati PRIMARY KEY (id_stato_amministrativo);



CREATE TABLE pbandi_d_stato_finanziario
(
	id_stato_finanziario  NUMBER(3)  NOT NULL ,
	desc_breve_stato_finanziario  VARCHAR2(20)  NOT NULL ,
	desc_stato_finanziario  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_stato_finanziario ON pbandi_d_stato_finanziario
(id_stato_finanziario  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_stato_finanziario
	ADD CONSTRAINT  PK_pbandi_d_stato_finanziario PRIMARY KEY (id_stato_finanziario);



CREATE TABLE pbandi_d_tipo_irregolarita
(
	id_tipo_irregolarita  NUMBER(3)  NOT NULL ,
	desc_breve_tipo_irregolarita  VARCHAR2(20)  NOT NULL ,
	desc_tipo_irregolarita  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_irregolarita ON pbandi_d_tipo_irregolarita
(id_tipo_irregolarita  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_irregolarita
	ADD CONSTRAINT  PK_pbandi_d_tipo_irregolarita PRIMARY KEY (id_tipo_irregolarita);



CREATE TABLE pbandi_t_irregolarita
(
	id_irregolarita       NUMBER(8)  NOT NULL ,
	id_irregolarita_collegata  NUMBER(8)  NULL ,
	numero_ims            VARCHAR2(30)  NULL ,
	dt_ims                DATE  NULL ,
	dt_comunicazione      DATE  NOT NULL ,
	flag_caso_chiuso      CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_28 CHECK (flag_caso_chiuso in ('S','N')),
	flag_blocco           CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_34 CHECK (flag_blocco in ('S','N')),
	note_pratica_usata    VARCHAR2(4000)  NOT NULL ,
	numero_versione       NUMBER(2)  NOT NULL ,
	soggetto_responsabile  VARCHAR2(300)  NOT NULL ,
	id_progetto           NUMBER(8)  NOT NULL ,
	id_stato_amministrativo  NUMBER(3)  NOT NULL ,
	id_disp_comunitaria   NUMBER(3)  NOT NULL ,
	id_metodo_individuazione  NUMBER(3)  NOT NULL ,
	id_natura_sanzione    NUMBER(3)  NOT NULL ,
	id_tipo_irregolarita  NUMBER(3)  NOT NULL ,
	id_qualificazione_irreg  NUMBER(3)  NOT NULL ,
	id_stato_finanziario  NUMBER(3)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_irregolarita ON pbandi_t_irregolarita
(id_irregolarita  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_irregolarita
	ADD CONSTRAINT  PK_pbandi_t_irregolarita PRIMARY KEY (id_irregolarita);



ALTER TABLE pbandi_t_irregolarita
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_188 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_irregolarita
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_189 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_irregolarita
	ADD (CONSTRAINT  FK_PBANDI_T_IRREGOLARITA_01 FOREIGN KEY (id_irregolarita_collegata) REFERENCES pbandi_t_irregolarita(id_irregolarita));



ALTER TABLE pbandi_t_irregolarita
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_22 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_t_irregolarita
	ADD (CONSTRAINT  FK_PBANDI_D_STATO_AMMINISTR_01 FOREIGN KEY (id_stato_amministrativo) REFERENCES pbandi_d_stato_amministrativo(id_stato_amministrativo));



ALTER TABLE pbandi_t_irregolarita
	ADD (CONSTRAINT  FK_PBANDI_D_DISP_COMUNITARI_01 FOREIGN KEY (id_disp_comunitaria) REFERENCES pbandi_d_disp_comunitaria(id_disp_comunitaria));



ALTER TABLE pbandi_t_irregolarita
	ADD (CONSTRAINT  FK_PBANDI_D_METODO_INDIVIDU_01 FOREIGN KEY (id_metodo_individuazione) REFERENCES pbandi_d_metodo_individuazione(id_metodo_individuazione));



ALTER TABLE pbandi_t_irregolarita
	ADD (CONSTRAINT  FK_PBANDI_D_NATURA_SANZIONE_01 FOREIGN KEY (id_natura_sanzione) REFERENCES pbandi_d_natura_sanzione(id_natura_sanzione));



ALTER TABLE pbandi_t_irregolarita
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_IRREGOLARI_01 FOREIGN KEY (id_tipo_irregolarita) REFERENCES pbandi_d_tipo_irregolarita(id_tipo_irregolarita));



ALTER TABLE pbandi_t_irregolarita
	ADD (CONSTRAINT  FK_PBANDI_D_QUALIFICA_IRREG_01 FOREIGN KEY (id_qualificazione_irreg) REFERENCES pbandi_d_qualificazione_irreg(id_qualificazione_irreg));



ALTER TABLE pbandi_t_irregolarita
	ADD (CONSTRAINT  FK_PBANDI_D_STATO_FINANZIAR_01 FOREIGN KEY (id_stato_finanziario) REFERENCES pbandi_d_stato_finanziario(id_stato_finanziario));

create sequence seq_pbandi_t_rinuncia start with 1 nocache;

CREATE TABLE pbandi_t_rinuncia
(
	id_rinuncia           NUMBER(8)  NOT NULL ,
	numero_rinuncia       VARCHAR2(10)  NOT NULL ,
	importo_da_restituire  NUMBER(15,2)  NOT NULL ,
	dt_rinuncia           DATE  NULL ,
	motivo_rinuncia       VARCHAR2(2000)  NOT NULL ,
	giorni_rinuncia       NUMBER(4)  NOT NULL ,
	id_progetto           NUMBER(8)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_rinuncia ON pbandi_t_rinuncia
(id_rinuncia  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_rinuncia
	ADD CONSTRAINT  PK_pbandi_t_rinuncia PRIMARY KEY (id_rinuncia);



ALTER TABLE pbandi_t_rinuncia
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_23 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_t_rinuncia
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_190 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_rinuncia
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_191 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));

alter table pbandi_t_irregolarita
add dt_fine_validita date;

CREATE TABLE pbandi_t_checklist
(
	id_documento_index    NUMBER(8)  NOT NULL ,
	dt_controllo          DATE  NULL ,
	soggetto_controllore  VARCHAR2(300) NULL ,
	flag_irregolarita     CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_35 CHECK (flag_irregolarita in ('S','N'))
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_checklist ON pbandi_t_checklist
(id_documento_index  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_checklist
	ADD CONSTRAINT  PK_pbandi_t_checklist PRIMARY KEY (id_documento_index);



ALTER TABLE pbandi_t_checklist
	ADD (CONSTRAINT  FK_PBANDI_T_DOCUMENTO_INDEX_02 FOREIGN KEY (id_documento_index) REFERENCES pbandi_t_documento_index(id_documento_index));
	
CREATE TABLE pbandi_r_dett_prop_cert_revoca
(
	id_dett_proposta_certif  NUMBER(8)  NOT NULL ,
	importo_documento     NUMBER(17,2)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	id_revoca             NUMBER(8)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_dett_prop_cert_rev ON pbandi_r_dett_prop_cert_revoca
(id_dett_proposta_certif  ASC,id_revoca  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_dett_prop_cert_revoca
	ADD CONSTRAINT  PK_pbandi_r_dett_prop_cert_rev PRIMARY KEY (id_dett_proposta_certif,id_revoca);



ALTER TABLE pbandi_r_dett_prop_cert_revoca
	ADD (CONSTRAINT  FK_PBANDI_T_DETT_PROP_CERT_05 FOREIGN KEY (id_dett_proposta_certif) REFERENCES pbandi_t_dett_proposta_certif(id_dett_proposta_certif));



ALTER TABLE pbandi_r_dett_prop_cert_revoca
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_192 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_dett_prop_cert_revoca
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_193 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_dett_prop_cert_revoca
	ADD (CONSTRAINT  FK_PBANDI_T_REVOCA_02 FOREIGN KEY (id_revoca) REFERENCES pbandi_t_revoca(id_revoca));

	
	

CREATE TABLE pbandi_r_dett_prop_certif_recu
(
	id_dett_proposta_certif  NUMBER(8)  NOT NULL ,
	id_recupero           NUMBER(8)  NOT NULL ,
	importo_documento     NUMBER(17,2)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	id_tipo_recupero      NUMBER(3)  NOT NULL
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_dett_prop_certif_r ON pbandi_r_dett_prop_certif_recu
(id_dett_proposta_certif  ASC,id_recupero  ASC)
	TABLESPACE PBANDI_SMALL_IDX;


ALTER TABLE pbandi_r_dett_prop_certif_recu
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_RECUPERO_03 FOREIGN KEY (id_tipo_recupero) REFERENCES pbandi_d_tipo_recupero(id_tipo_recupero));
	

ALTER TABLE pbandi_r_dett_prop_certif_recu
	ADD CONSTRAINT  PK_pbandi_r_dett_prop_certif_r PRIMARY KEY (id_dett_proposta_certif,id_recupero);



ALTER TABLE pbandi_r_dett_prop_certif_recu
	ADD (CONSTRAINT  FK_PBANDI_T_DETT_PROP_CERT_06 FOREIGN KEY (id_dett_proposta_certif) REFERENCES pbandi_t_dett_proposta_certif(id_dett_proposta_certif));



ALTER TABLE pbandi_r_dett_prop_certif_recu
	ADD (CONSTRAINT  FK_PBANDI_T_RECUPERO_01 FOREIGN KEY (id_recupero) REFERENCES pbandi_t_recupero(id_recupero));



ALTER TABLE pbandi_r_dett_prop_certif_recu
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_194 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_dett_prop_certif_recu
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_195 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));
	
alter table pbandi_t_dett_proposta_certif add importo_revoche       NUMBER(17,2)  NULL;
alter table pbandi_t_dett_proposta_certif add dt_ultima_revoca      DATE  NULL;
alter table pbandi_t_dett_proposta_certif add importo_da_recuperare  NUMBER(17,2)  NULL;
alter table pbandi_t_dett_proposta_certif add importo_prerecuperi   NUMBER(17,2)  NULL;
alter table pbandi_t_dett_proposta_certif add dt_ultimo_prerecupero  DATE  NULL;
alter table pbandi_t_dett_proposta_certif add importo_recuperi      NUMBER(17,2)  NULL;
alter table pbandi_t_dett_proposta_certif add dt_ultimo_recupero    DATE  NULL;
alter table pbandi_t_dett_proposta_certif add importo_soppressioni  NUMBER(17,2)  NULL;
alter table pbandi_t_dett_proposta_certif add dt_ultima_soppressione  DATE  NULL;
alter table pbandi_t_dett_proposta_certif add importo_non_rilevante_certif  NUMBER(17,2)  NULL;
alter table pbandi_t_dett_proposta_certif add importo_certificazione_netto  NUMBER(17,2)  NULL;
alter table pbandi_t_dett_proposta_certif add identificativi_irregolarita  VARCHAR2(300)  NULL;
alter table pbandi_t_dett_proposta_certif add erogazioni_in_meno_per_errore  NUMBER(17,2)  NULL;
alter table pbandi_t_dett_proposta_certif add fideiussioni_in_meno_per_error  NUMBER(17,2)  NULL;
alter table pbandi_t_dett_proposta_certif add soppressioni_in_meno_per_error  NUMBER(17,2)  NULL;
alter table pbandi_t_dett_proposta_certif add prerecuperi_in_meno_per_error  NUMBER(17,2)  NULL;
alter table pbandi_t_dett_proposta_certif add recuperi_in_meno_per_errore  NUMBER(17,2)  NULL;
alter table pbandi_t_dett_proposta_certif add revoche_in_meno_per_error  NUMBER(17,2)  NULL; 

alter table pbandi_t_dett_proposta_certif add flag_comp CHAR(1)   DEFAULT  'N' NOT NULL CONSTRAINT  ck_flag_36 CHECK (flag_comp in ('S','N'));

alter table pbandi_t_proposta_certificaz add id_proposta_prec  NUMBER(8)  NULL;

ALTER TABLE pbandi_t_proposta_certificaz
	ADD (CONSTRAINT  FK_PBANDI_T_PROPOSTA_CERTIF_03 FOREIGN KEY (id_proposta_prec) REFERENCES pbandi_t_proposta_certificaz(id_proposta_certificaz));

alter TABLE pbandi_r_det_prop_cer_sogg_fin
add perc_tipo_sogg_finanz_fesr  NUMBER(5,2);

alter table pbandi_t_rinuncia
drop (Numero_Rinuncia);

alter TABLE pbandi_d_tipo_sogg_finanziat
add perc_standard         NUMBER(5,2);

CREATE TABLE pbandi_r_prop_cert_scost_asse
(
	id_proposta_certificaz  NUMBER(8)  NOT NULL ,
	id_linea_di_intervento  NUMBER(3)  NOT NULL ,
	id_tipo_sogg_finanziat  NUMBER(3)  NOT NULL ,
	perc_scostamento      NUMBER(5,2)  NOT NULL ,
	val_ass_scostamento   NUMBER(17,2)  NOT NULL ,
	flag_comp             CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_37 CHECK (flag_comp in ('S','N'))
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_prop_cert_scost_as ON pbandi_r_prop_cert_scost_asse
(id_proposta_certificaz  ASC,id_linea_di_intervento  ASC,id_tipo_sogg_finanziat  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_prop_cert_scost_asse
	ADD CONSTRAINT  PK_pbandi_r_prop_cert_scost_as PRIMARY KEY (id_proposta_certificaz,id_linea_di_intervento,id_tipo_sogg_finanziat);



ALTER TABLE pbandi_r_prop_cert_scost_asse
	ADD (CONSTRAINT  FK_PBANDI_T_PROPOSTA_CERTIF_01 FOREIGN KEY (id_proposta_certificaz) REFERENCES pbandi_t_proposta_certificaz(id_proposta_certificaz));



ALTER TABLE pbandi_r_prop_cert_scost_asse
	ADD (CONSTRAINT  FK_PBANDI_D_LINEA_INTERV_07 FOREIGN KEY (id_linea_di_intervento) REFERENCES pbandi_d_linea_di_intervento(id_linea_di_intervento));



ALTER TABLE pbandi_r_prop_cert_scost_asse
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_SOGG_FINAN_03 FOREIGN KEY (id_tipo_sogg_finanziat) REFERENCES pbandi_d_tipo_sogg_finanziat(id_tipo_sogg_finanziat));

alter table pbandi_t_revoca
modify DT_REVOCA not null;

CREATE INDEX IE1_pbandi_t_dett_proposta_cer ON pbandi_t_dett_proposta_certif
(id_proposta_certificaz  ASC)
compute statistics TABLESPACE PBANDI_SMALL_IDX;

CREATE INDEX IE2_pbandi_t_dett_proposta_cer ON pbandi_t_dett_proposta_certif
(id_progetto  ASC,id_proposta_certificaz  ASC)
compute statistics 	TABLESPACE PBANDI_SMALL_IDX;	

CREATE INDEX IE1_pbandi_r_pag_quot_parte_do ON pbandi_r_pag_quot_parte_doc_sp
(id_pagamento  ASC)
compute statistics TABLESPACE PBANDI_SMALL_IDX;


CREATE TABLE pbandi_c_modello
(
	id_modello            NUMBER(3)  NOT NULL ,
	nome_modello          VARCHAR2(255)  NOT NULL ,
	versione_modello      VARCHAR2(255)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_c_modello ON pbandi_c_modello
(id_modello  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_c_modello
	ADD CONSTRAINT  PK_pbandi_c_modello PRIMARY KEY (id_modello);



CREATE TABLE pbandi_r_bl_tipo_doc_index_mod
(
	id_tipo_documento_index  NUMBER(3)  NOT NULL ,
	progr_bando_linea_intervento  NUMBER(8)  NOT NULL ,
	id_modello            NUMBER(3)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_bl_tipo_doc_index_ ON pbandi_r_bl_tipo_doc_index_mod
(id_tipo_documento_index  ASC,progr_bando_linea_intervento  ASC,id_modello  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_bl_tipo_doc_index_mod
	ADD CONSTRAINT  PK_pbandi_r_bl_tipo_doc_index_ PRIMARY KEY (id_tipo_documento_index,progr_bando_linea_intervento,id_modello);



ALTER TABLE pbandi_r_bl_tipo_doc_index_mod
	ADD (CONSTRAINT  FK_PBANDI_R_TP_DOC_IND_BAN_01 FOREIGN KEY (id_tipo_documento_index,progr_bando_linea_intervento) REFERENCES pbandi_r_tp_doc_ind_ban_li_int(id_tipo_documento_index,progr_bando_linea_intervento));



ALTER TABLE pbandi_r_bl_tipo_doc_index_mod
	ADD (CONSTRAINT  FK_PBANDI_C_MODELLO_01 FOREIGN KEY (id_modello) REFERENCES pbandi_c_modello(id_modello));

alter TABLE pbandi_t_documento_index
add id_modello            NUMBER(3);

ALTER TABLE pbandi_t_documento_index
	ADD (CONSTRAINT  FK_PBANDI_C_MODELLO_02 FOREIGN KEY (id_modello) REFERENCES pbandi_c_modello(id_modello));

CREATE TABLE pbandi_t_documento_index_lock
(
	id_documento_index    NUMBER(8)  NOT NULL ,
	id_utente             NUMBER(8)  NOT NULL ,
	dt_lock_documento     DATE  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_documento_index_lo ON pbandi_t_documento_index_lock
(id_documento_index  ASC,id_utente  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_documento_index_lock
	ADD CONSTRAINT  PK_pbandi_t_documento_index_lo PRIMARY KEY (id_documento_index,id_utente);



ALTER TABLE pbandi_t_documento_index_lock
	ADD (CONSTRAINT  FK_PBANDI_T_DOCUMENTO_INDEX_03 FOREIGN KEY (id_documento_index) REFERENCES pbandi_t_documento_index(id_documento_index));



ALTER TABLE pbandi_t_documento_index_lock
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_196 FOREIGN KEY (id_utente) REFERENCES pbandi_t_utente(id_utente));

create sequence seq_pbandi_t_rigo_quadro_previ start with 1 nocache;
create sequence seq_pbandi_t_quadro_previsiona start with 1 nocache;	
	
CREATE TABLE pbandi_t_rigo_quadro_previsio
(
	id_rigo_quadro_previsio  NUMBER(8)  NOT NULL ,
	id_progetto           NUMBER(8)  NOT NULL ,
	importo_preventivo    NUMBER(17,2)  NULL ,
	id_voce_di_spesa      NUMBER(3)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	periodo               VARCHAR2(4)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_rigo_quadro_previs ON pbandi_t_rigo_quadro_previsio
(id_rigo_quadro_previsio  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_rigo_quadro_previsio
	ADD CONSTRAINT  PK_pbandi_t_rigo_quadro_previs PRIMARY KEY (id_rigo_quadro_previsio);



ALTER TABLE pbandi_t_rigo_quadro_previsio
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_25 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_t_rigo_quadro_previsio
	ADD (CONSTRAINT  FK_PBANDI_D_VOCE_DI_SPESA_04 FOREIGN KEY (id_voce_di_spesa) REFERENCES pbandi_d_voce_di_spesa(id_voce_di_spesa));



ALTER TABLE pbandi_t_rigo_quadro_previsio
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_199 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_rigo_quadro_previsio
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_200 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));

	
CREATE TABLE pbandi_t_quadro_previsionale
(
	id_quadro_previsionale  NUMBER(8)  NOT NULL ,
	note                  VARCHAR2(4000)  NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_progetto           NUMBER(8)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_quadro_previsional ON pbandi_t_quadro_previsionale
(id_quadro_previsionale  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_quadro_previsionale
	ADD CONSTRAINT  PK_pbandi_t_quadro_previsional PRIMARY KEY (id_quadro_previsionale);



ALTER TABLE pbandi_t_quadro_previsionale
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_24 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_t_quadro_previsionale
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_197 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_quadro_previsionale
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_198 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));	


