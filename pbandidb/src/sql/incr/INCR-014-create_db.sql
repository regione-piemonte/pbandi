/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE TABLE pbandi_r_sogg_tipo_anag_linea
(
	id_soggetto           NUMBER(8)  NOT NULL ,
	id_tipo_anagrafica    NUMBER(3)  NOT NULL ,
	id_linea_di_intervento  NUMBER(3)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_sogg_tipo_anag_lin ON pbandi_r_sogg_tipo_anag_linea
(id_soggetto  ASC,id_tipo_anagrafica  ASC,id_linea_di_intervento  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_sogg_tipo_anag_linea
	ADD CONSTRAINT  PK_pbandi_r_sogg_tipo_anag_lin PRIMARY KEY (id_soggetto,id_tipo_anagrafica,id_linea_di_intervento);



ALTER TABLE pbandi_r_sogg_tipo_anag_linea
	ADD (CONSTRAINT  FK_PBANDI_R_SOGG_TIPO_ANAGR_01 FOREIGN KEY (id_soggetto,id_tipo_anagrafica) REFERENCES pbandi_r_sogg_tipo_anagrafica(id_soggetto,id_tipo_anagrafica));



ALTER TABLE pbandi_r_sogg_tipo_anag_linea
	ADD (CONSTRAINT  FK_PBANDI_D_LINEA_INTERV_12 FOREIGN KEY (id_linea_di_intervento) REFERENCES pbandi_d_linea_di_intervento(id_linea_di_intervento));



ALTER TABLE pbandi_r_sogg_tipo_anag_linea
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_248 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_sogg_tipo_anag_linea
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_247 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));



CREATE TABLE pbandi_r_proposta_certif_linea
(
	id_proposta_certificaz  NUMBER(8)  NOT NULL ,
	id_linea_di_intervento  NUMBER(3)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_proposta_certif_li ON pbandi_r_proposta_certif_linea
(id_proposta_certificaz  ASC,id_linea_di_intervento  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_proposta_certif_linea
	ADD CONSTRAINT  PK_pbandi_r_proposta_certif_li PRIMARY KEY (id_proposta_certificaz,id_linea_di_intervento);



ALTER TABLE pbandi_r_proposta_certif_linea
	ADD (CONSTRAINT  FK_PBANDI_T_PROPOSTA_CERTIF_05 FOREIGN KEY (id_proposta_certificaz) REFERENCES pbandi_t_proposta_certificaz(id_proposta_certificaz));



ALTER TABLE pbandi_r_proposta_certif_linea
	ADD (CONSTRAINT  FK_PBANDI_D_LINEA_INTERV_13 FOREIGN KEY (id_linea_di_intervento) REFERENCES pbandi_d_linea_di_intervento(id_linea_di_intervento));



ALTER TABLE pbandi_r_proposta_certif_linea
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_250 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_proposta_certif_linea
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_249 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));

ALTER TABLE pbandi_t_preview_dett_prop_cer
add id_linea_di_intervento  NUMBER(3)  NULL;

ALTER TABLE pbandi_t_dett_proposta_certif
add id_linea_di_intervento  NUMBER(3)  NULL;

ALTER TABLE pbandi_t_preview_dett_prop_cer
	ADD (CONSTRAINT  FK_PBANDI_D_LINEA_INTERV_15 FOREIGN KEY (id_linea_di_intervento) REFERENCES pbandi_d_linea_di_intervento(id_linea_di_intervento));
  
ALTER TABLE pbandi_t_dett_proposta_certif
	ADD (CONSTRAINT  FK_PBANDI_D_LINEA_INTERV_16 FOREIGN KEY (id_linea_di_intervento) REFERENCES pbandi_d_linea_di_intervento(id_linea_di_intervento));

CREATE TABLE pbandi_r_dett_prop_cer_lin_ant
(
	id_dett_proposta_certif  NUMBER(8)  NOT NULL ,
	id_linea_di_intervento  NUMBER(3)  NOT NULL ,
	importo_anticipo      NUMBER(17,2)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_dett_prop_cer_l_an ON pbandi_r_dett_prop_cer_lin_ant
(id_dett_proposta_certif  ASC,id_linea_di_intervento  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_dett_prop_cer_lin_ant
	ADD CONSTRAINT  PK_pbandi_r_dett_prop_cer_l_an PRIMARY KEY (id_dett_proposta_certif,id_linea_di_intervento);



ALTER TABLE pbandi_r_dett_prop_cer_lin_ant
	ADD (CONSTRAINT  FK_PBANDI_T_DETT_PROP_CERT_10 FOREIGN KEY (id_dett_proposta_certif) REFERENCES pbandi_t_dett_proposta_certif(id_dett_proposta_certif));



ALTER TABLE pbandi_r_dett_prop_cer_lin_ant
	ADD (CONSTRAINT  FK_PBANDI_D_LINEA_INTERV_14 FOREIGN KEY (id_linea_di_intervento) REFERENCES pbandi_d_linea_di_intervento(id_linea_di_intervento));



ALTER TABLE pbandi_r_dett_prop_cer_lin_ant
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_251 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_dett_prop_cer_lin_ant
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_252 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));


ALTER TABLE PBANDI_T_ESTREMI_BANCARI
MODIFY iban null;

ALTER TABLE PBANDI_T_DATI_PROGETTO_MONIT 
 ADD FLAG_RICHIESTA_CUP  CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_67 CHECK (flag_richiesta_cup in ('S','N'));
 
ALTER TABLE PBANDI_T_CSP_PROGETTO 
 ADD FLAG_RICHIESTA_CUP char(1)    DEFAULT  'N' NOT NULL;
 
ALTER TABLE PBANDI_T_CSP_PROGETTO 
 ADD  FLAG_INVIO_MONIT char(1)    DEFAULT  'S' NOT NULL;