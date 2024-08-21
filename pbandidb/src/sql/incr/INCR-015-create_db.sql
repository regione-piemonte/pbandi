/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


ALTER TABLE PBANDI_T_DATI_PROGETTO_MONIT 
 ADD FLAG_RICHIESTA_CUP_INVIATA  CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_68 CHECK (flag_richiesta_cup_inviata in ('S','N'));
 
alter table PBANDI_R_FORNITORE_QUALIFICA
drop column MONTE_ORE;

alter table PBANDI_R_FORNITORE_QUALIFICA
drop column COSTO_RISORSA;

alter table PBANDI_R_FORNITORE_QUALIFICA
add NOTE_QUALIFICA varchar2(255) null;

alter table pbandi_t_revoca 
add INTERESSI_REVOCA NUMBER(17,2);

alter TABLE pbandi_t_recupero
add id_revoca             NUMBER(8)  NULL;

ALTER TABLE pbandi_t_recupero
	ADD (CONSTRAINT  FK_PBANDI_T_REVOCA_01 FOREIGN KEY (id_revoca) REFERENCES pbandi_t_revoca(id_revoca));
	
alter table pbandi_t_fideiussione
add DT_SVINCOLO date;

alter table pbandi_t_erogazione
add id_fideiussione       NUMBER(8)  NULL;

ALTER TABLE pbandi_t_erogazione
	ADD (CONSTRAINT  FK_PBANDI_T_FIDEIUSSIONE_02 FOREIGN KEY (id_fideiussione) REFERENCES pbandi_t_fideiussione(id_fideiussione));

CREATE TABLE pbandi_r_linea_tipo_sogg_fin
(
    id_linea_di_intervento  NUMBER(3)  NOT NULL ,
    id_tipo_sogg_finanziat  NUMBER(3)  NOT NULL ,
    perc_tipo_sogg_fin    NUMBER(5,2)  NOT NULL ,
    id_utente_ins         NUMBER(8)  NOT NULL ,
    id_utente_agg         NUMBER(8)  NULL 
)
    TABLESPACE pbandi_small_tbl;



CREATE UNIQUE INDEX PK_pbandi_r_linea_tipo_sogg_fi ON pbandi_r_linea_tipo_sogg_fin
(id_linea_di_intervento  ASC,id_tipo_sogg_finanziat  ASC)
    TABLESPACE pbandi_small_idx;



ALTER TABLE pbandi_r_linea_tipo_sogg_fin
    ADD CONSTRAINT  PK_pbandi_r_linea_tipo_sogg_fi PRIMARY KEY (id_linea_di_intervento,id_tipo_sogg_finanziat);



CREATE TABLE pbandi_r_prop_cert_lin_sp_val
(
    id_proposta_certificaz  NUMBER(8)  NOT NULL ,
    id_linea_di_intervento  NUMBER(3)  NOT NULL ,
    id_tipo_sogg_finanziat  NUMBER(3)  NOT NULL ,
    spesa_validata        NUMBER(13,2)  NOT NULL 
)
    TABLESPACE pbandi_small_tbl;



CREATE UNIQUE INDEX PK_pbandi_r_prop_cert_lin_sp_v ON pbandi_r_prop_cert_lin_sp_val
(id_proposta_certificaz  ASC,id_linea_di_intervento  ASC,id_tipo_sogg_finanziat  ASC)
    TABLESPACE pbandi_small_idx;



ALTER TABLE pbandi_r_prop_cert_lin_sp_val
    ADD CONSTRAINT  PK_pbandi_r_prop_cert_lin_sp_v PRIMARY KEY (id_proposta_certificaz,id_linea_di_intervento,id_tipo_sogg_finanziat);



ALTER TABLE pbandi_r_linea_tipo_sogg_fin
    ADD (CONSTRAINT  FK_PBANDI_D_LINEA_INTERV_17 FOREIGN KEY (id_linea_di_intervento) REFERENCES pbandi_d_linea_di_intervento(id_linea_di_intervento));



ALTER TABLE pbandi_r_linea_tipo_sogg_fin
    ADD (CONSTRAINT  FK_PBANDI_D_TIPO_SOGG_FINAN_04 FOREIGN KEY (id_tipo_sogg_finanziat) REFERENCES pbandi_d_tipo_sogg_finanziat(id_tipo_sogg_finanziat));



ALTER TABLE pbandi_r_linea_tipo_sogg_fin
    ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_253 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_linea_tipo_sogg_fin
    ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_254 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_prop_cert_lin_sp_val
    ADD (CONSTRAINT  FK_PBANDI_T_PROPOSTA_CERTIF_06 FOREIGN KEY (id_proposta_certificaz) REFERENCES pbandi_t_proposta_certificaz(id_proposta_certificaz));



ALTER TABLE pbandi_r_prop_cert_lin_sp_val
    ADD (CONSTRAINT  FK_PBANDI_D_LINEA_INTERV_18 FOREIGN KEY (id_linea_di_intervento) REFERENCES pbandi_d_linea_di_intervento(id_linea_di_intervento));



ALTER TABLE pbandi_r_prop_cert_lin_sp_val
    ADD (CONSTRAINT  FK_PBANDI_D_TIPO_SOGG_FINAN_05 FOREIGN KEY (id_tipo_sogg_finanziat) REFERENCES pbandi_d_tipo_sogg_finanziat(id_tipo_sogg_finanziat));

alter table PBANDI_T_RECUPERO
drop (ID_REVOCA);

alter table pbandi_t_utenti_upload
add cod_fiscale_beneficiario varchar2(32);

update 	pbandi_t_utenti_upload
set cod_fiscale_beneficiario = 'XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX';

COMMIT;

alter table pbandi_t_utenti_upload
modify cod_fiscale_beneficiario not null;

alter table PBANDI_R_DET_PROP_CER_QP_DOC
drop constraints FK_PBANDI_R_QP_DOC_SPESA_01;

alter table PBANDI_R_DET_PROP_CER_QP_DOC
drop constraints FK_PBANDI_T_DICHIARAZ_SPESA_02;
