/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

alter table PBANDI_R_SOGGETTO_DOMANDA
drop (ID_SOGGETTO_CAPOFILA);

alter table PBANDI_R_SOGGETTO_PROGETTO
drop (ID_SOGGETTO_CAPOFILA);

alter TABLE pbandi_t_dati_progetto_monit
add codice_progetto_cipe  VARCHAR2(20);

CREATE UNIQUE INDEX AK1_pbandi_d_permesso ON pbandi_d_permesso
(desc_breve_permesso  ASC)	TABLESPACE PBANDI_SMALL_IDX;

CREATE TABLE pbandi_d_tipo_confronto
(
	id_tipo_confronto     NUMBER(3)  NOT NULL ,
	desc_breve_tipo_confronto  VARCHAR2(20)  NOT NULL ,
	desc_tipo_confronto   VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_confronto ON pbandi_d_tipo_confronto
(id_tipo_confronto  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_confronto
	ADD CONSTRAINT  PK_pbandi_d_tipo_confronto PRIMARY KEY (id_tipo_confronto);



CREATE TABLE pbandi_r_ban_lin_voce_sp_corr
(
	progr_voce_spesa_correlata  NUMBER(8)  NOT NULL ,
	progr_bando_linea_intervento  NUMBER(8)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_ban_lin_voce_sp_co ON pbandi_r_ban_lin_voce_sp_corr
(progr_voce_spesa_correlata  ASC,progr_bando_linea_intervento  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_ban_lin_voce_sp_corr
	ADD CONSTRAINT  PK_pbandi_r_ban_lin_voce_sp_co PRIMARY KEY (progr_voce_spesa_correlata,progr_bando_linea_intervento);



CREATE TABLE pbandi_r_voce_spesa_correlata
(
	progr_voce_spesa_correlata  NUMBER(8)  NOT NULL ,
	perc_confronto        NUMBER(5,2)  NULL ,
	id_tipo_confronto     NUMBER(3)  NOT NULL ,
	id_voce_di_spesa_di_partenza  NUMBER(3)  NOT NULL ,
	id_voce_di_spesa_correlata  NUMBER(3)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_voce_spesa_correla ON pbandi_r_voce_spesa_correlata
(progr_voce_spesa_correlata  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_voce_spesa_correlata
	ADD CONSTRAINT  PK_pbandi_r_voce_spesa_correla PRIMARY KEY (progr_voce_spesa_correlata);



ALTER TABLE pbandi_r_ban_lin_voce_sp_corr
	ADD (CONSTRAINT  FK_PBANDI_R_VOCE_SPESA_CORR_01 FOREIGN KEY (progr_voce_spesa_correlata) REFERENCES pbandi_r_voce_spesa_correlata(progr_voce_spesa_correlata));



ALTER TABLE pbandi_r_ban_lin_voce_sp_corr
	ADD (CONSTRAINT  FK_PBANDI_R_BANDO_LINEA_INT_14 FOREIGN KEY (progr_bando_linea_intervento) REFERENCES pbandi_r_bando_linea_intervent(progr_bando_linea_intervento));



ALTER TABLE pbandi_r_ban_lin_voce_sp_corr
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_209 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_ban_lin_voce_sp_corr
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_210 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_voce_spesa_correlata
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_CONFRONTO_01 FOREIGN KEY (id_tipo_confronto) REFERENCES pbandi_d_tipo_confronto(id_tipo_confronto));



ALTER TABLE pbandi_r_voce_spesa_correlata
	ADD (CONSTRAINT  FK_PBANDI_D_VOCE_DI_SPESA_05 FOREIGN KEY (id_voce_di_spesa_di_partenza) REFERENCES pbandi_d_voce_di_spesa(id_voce_di_spesa));



ALTER TABLE pbandi_r_voce_spesa_correlata
	ADD (CONSTRAINT  FK_PBANDI_D_VOCE_DI_SPESA_06 FOREIGN KEY (id_voce_di_spesa_correlata) REFERENCES pbandi_d_voce_di_spesa(id_voce_di_spesa));



ALTER TABLE pbandi_r_voce_spesa_correlata
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_207 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_voce_spesa_correlata
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_208 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));

alter table PBANDI_T_APPALTO
modify DT_CONSEGNA_LAVORI null;

alter table PBANDI_T_APPALTO
modify DT_INIZIO_PREVISTA null;	
