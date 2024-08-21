/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE TABLE pbandi_d_tipo_stato_prop_cert
(
	id_tipo_stato_prop_cert  NUMBER(3)  NOT NULL ,
	desc_breve_tipo_stato_pro_cert  VARCHAR2(20)  NOT NULL ,
	desc_tipo_stato_prop_cert  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_stato_prop_ce ON pbandi_d_tipo_stato_prop_cert
(id_tipo_stato_prop_cert  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_stato_prop_cert
	ADD CONSTRAINT  PK_pbandi_d_tipo_stato_prop_ce PRIMARY KEY (id_tipo_stato_prop_cert);
	
ALTER TABLE pbandi_d_stato_proposta_certif	
ADD id_tipo_stato_prop_cert  NUMBER(3)  NULL ;

ALTER TABLE pbandi_d_stato_proposta_certif
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_ST_PR_CERT_01 FOREIGN KEY (id_tipo_stato_prop_cert) REFERENCES pbandi_d_tipo_stato_prop_cert(id_tipo_stato_prop_cert));
	
ALTER TABLE pbandi_d_invio_proposta_certif
ADD id_stato_proposta_certif  NUMBER(3) NULL;

ALTER TABLE pbandi_d_invio_proposta_certif
	ADD (CONSTRAINT  FK_PBANDI_D_STATO_PROP_CERT_02 FOREIGN KEY (id_stato_proposta_certif) REFERENCES pbandi_d_stato_proposta_certif(id_stato_proposta_certif));
	
drop table pbandi_t_documento_index_lock cascade constraints;	

CREATE TABLE pbandi_t_documento_index_lock
(
	id_utente             NUMBER(8)  NOT NULL ,
	dt_lock_documento     DATE  NOT NULL ,
	id_entita             NUMBER(3)  NOT NULL ,
	id_target             NUMBER(8)  NOT NULL ,
	id_progetto           NUMBER(8)  NOT NULL ,
	id_tipo_documento_index  NUMBER(3)  NOT NULL ,
	flag_lock_valido      CHAR(1)   DEFAULT  'S' NOT NULL  CONSTRAINT  ck_flag_38 CHECK (flag_lock_valido in ('S','N'))
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_documento_index_lo ON pbandi_t_documento_index_lock
(id_entita  ASC,id_target  ASC,id_progetto  ASC,id_tipo_documento_index  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_documento_index_lock
	ADD CONSTRAINT  PK_pbandi_t_documento_index_lo PRIMARY KEY (id_entita,id_target,id_progetto,id_tipo_documento_index);



ALTER TABLE pbandi_t_documento_index_lock
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_196 FOREIGN KEY (id_utente) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_documento_index_lock
	ADD (CONSTRAINT  FK_PBANDI_C_ENTITA_04 FOREIGN KEY (id_entita) REFERENCES pbandi_c_entita(id_entita));



ALTER TABLE pbandi_t_documento_index_lock
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_26 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_t_documento_index_lock
	ADD (CONSTRAINT  FK_PBANDI_C_TIPO_DOC_INDEX_05 FOREIGN KEY (id_tipo_documento_index) REFERENCES pbandi_c_tipo_documento_index(id_tipo_documento_index));


alter TABLE pbandi_r_domanda_indicatori
add valore_prog_agg       NUMBER(13,2);
	
alter TABLE pbandi_d_indicatori
add flag_obbligatorio     CHAR(1)  NULL  CONSTRAINT  ck_flag_39 CHECK (flag_obbligatorio in ('S','N'));

update pbandi_d_indicatori
set flag_obbligatorio = 'N';

commit;

alter TABLE pbandi_d_indicatori
modify flag_obbligatorio not null;

alter TABLE pbandi_d_tipo_indicatore
add flag_monit            CHAR(1)  NULL  CONSTRAINT  ck_flag_40 CHECK (flag_monit in ('S','N'));

update pbandi_d_tipo_indicatore
set flag_monit = 'S';

commit;

alter TABLE pbandi_d_tipo_indicatore
modify flag_monit not null;

alter TABLE pbandi_d_fase_monit
add flag_obbligatorio     CHAR(1)  NULL  CONSTRAINT  ck_flag_41 CHECK (flag_obbligatorio in ('S','N'));

alter TABLE pbandi_d_fase_monit
add flag_controllo_indicat  CHAR(1)  NULL  CONSTRAINT  ck_flag_42 CHECK (flag_controllo_indicat in ('S','N'));

alter TABLE pbandi_t_conto_economico
add flag_ribasso_asta     CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_43 CHECK (flag_ribasso_asta in ('S','N'));

alter TABLE pbandi_t_conto_economico
add id_procedura_aggiudicaz  NUMBER(8)  NULL ;

ALTER TABLE pbandi_t_conto_economico
	ADD (CONSTRAINT  FK_PBANDI_T_PROCEDURA_AGGIU_04 FOREIGN KEY (id_procedura_aggiudicaz) REFERENCES pbandi_t_procedura_aggiudicaz(id_procedura_aggiudicaz));
	
alter TABLE pbandi_t_dett_proposta_certif
add titolo_progetto       VARCHAR2(255)  NULL;
	
alter TABLE pbandi_t_dett_proposta_certif
add 	nome_bando_linea      VARCHAR2(255)  NULL;
	
alter TABLE pbandi_t_dett_proposta_certif
add 	contributo_pubblico_concesso  NUMBER(17,2)  NULL ;
	
alter TABLE pbandi_t_dett_proposta_certif
add flag_check_list_in_loco  CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_44 CHECK (flag_check_list_in_loco in ('S','N'));
	
alter TABLE pbandi_t_dett_proposta_certif
add 	flag_check_list_certificazione  CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_45 CHECK (flag_check_list_certificazione in ('S','N'));
	
alter TABLE pbandi_t_dett_proposta_certif
add 	id_stato_progetto     NUMBER(3)  NULL ;
	
alter TABLE pbandi_t_dett_proposta_certif
add 	id_tipo_operazione    NUMBER(3)  NULL ;
	
alter TABLE pbandi_t_dett_proposta_certif
add 	id_indirizzo_sede_legale  NUMBER(9)  NULL ;
	
alter TABLE pbandi_t_dett_proposta_certif
add 	id_persona_fisica     NUMBER(8)  NULL ;
	
alter TABLE pbandi_t_dett_proposta_certif
add 	id_ente_giuridico     NUMBER(8)  NULL ;
	
alter TABLE pbandi_t_dett_proposta_certif
add 	id_dimensione_impresa  NUMBER(3)  NULL ;	

ALTER TABLE pbandi_t_dett_proposta_certif
	ADD (CONSTRAINT  FK_PBANDI_T_ENTE_GIURIDICO_05 FOREIGN KEY (id_ente_giuridico) REFERENCES pbandi_t_ente_giuridico(id_ente_giuridico) );



ALTER TABLE pbandi_t_dett_proposta_certif
	ADD (CONSTRAINT  FK_PBANDI_D_DIMENSIONE_IMPR_04 FOREIGN KEY (id_dimensione_impresa) REFERENCES pbandi_d_dimensione_impresa(id_dimensione_impresa) );

ALTER TABLE pbandi_t_dett_proposta_certif
	ADD (CONSTRAINT  FK_PBANDI_D_STATO_PROGETTO_02 FOREIGN KEY (id_stato_progetto) REFERENCES pbandi_d_stato_progetto(id_stato_progetto) );



ALTER TABLE pbandi_t_dett_proposta_certif
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_OPERAZIONE_06 FOREIGN KEY (id_tipo_operazione) REFERENCES pbandi_d_tipo_operazione(id_tipo_operazione) );



ALTER TABLE pbandi_t_dett_proposta_certif
	ADD (CONSTRAINT  FK_PBANDI_T_INDIRIZZO_10 FOREIGN KEY (id_indirizzo_sede_legale) REFERENCES pbandi_t_indirizzo(id_indirizzo) );


ALTER TABLE pbandi_t_dett_proposta_certif
	ADD (CONSTRAINT  FK_PBANDI_T_PERSONA_FISICA_03 FOREIGN KEY (id_persona_fisica) REFERENCES pbandi_t_persona_fisica(id_persona_fisica) );


CREATE TABLE pbandi_r_det_prop_ind_sede_int
(
	id_dett_proposta_certif  NUMBER(8)  NOT NULL ,
	id_indirizzo          NUMBER(9)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_det_prop_ind_sede_ ON pbandi_r_det_prop_ind_sede_int
(id_dett_proposta_certif  ASC,id_indirizzo  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_det_prop_ind_sede_int
	ADD CONSTRAINT  PK_pbandi_r_det_prop_ind_sede_ PRIMARY KEY (id_dett_proposta_certif,id_indirizzo);



ALTER TABLE pbandi_r_det_prop_ind_sede_int
	ADD (CONSTRAINT  FK_PBANDI_T_DETT_PROP_CERT_07 FOREIGN KEY (id_dett_proposta_certif) REFERENCES pbandi_t_dett_proposta_certif(id_dett_proposta_certif));



ALTER TABLE pbandi_r_det_prop_ind_sede_int
	ADD (CONSTRAINT  FK_PBANDI_T_INDIRIZZO_11 FOREIGN KEY (id_indirizzo) REFERENCES pbandi_t_indirizzo(id_indirizzo));



ALTER TABLE pbandi_r_det_prop_ind_sede_int
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_201 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_det_prop_ind_sede_int
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_202 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));

create sequence seq_pbandi_t_checklist start with 1 nocache;

drop TABLE pbandi_t_checklist cascade constraints;

CREATE TABLE pbandi_t_checklist
(
	dt_controllo          DATE  NULL ,
	soggetto_controllore  VARCHAR2(300)  NULL ,
	flag_irregolarita     CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_35 CHECK (flag_irregolarita in ('S','N')),
	id_checklist          NUMBER(8)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_checklist ON pbandi_t_checklist
(id_checklist  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_checklist
	ADD CONSTRAINT  PK_pbandi_t_checklist PRIMARY KEY (id_checklist);

alter table pbandi_t_bando
add determina_approvazione  VARCHAR2(1000);

alter TABLE pbandi_t_checklist
add id_dichiarazione_spesa  NUMBER(8)  NULL;

ALTER TABLE pbandi_t_checklist
	ADD (CONSTRAINT  FK_PBANDI_T_DICHIARAZ_SPESA_03 FOREIGN KEY (id_dichiarazione_spesa) REFERENCES pbandi_t_dichiarazione_spesa(id_dichiarazione_spesa));

CREATE TABLE PBANDI_W_PROG_SOGG_FINANZIAT
(
  ID_PROGETTO                   NUMBER(8)       NOT NULL,
  ID_SOGGETTO_FINANZIATORE      NUMBER(3)       NOT NULL,
  PERC_QUOTA_SOGG_FINANZIATORE  NUMBER(5,2),
  ID_UTENTE_INS                 NUMBER(8)       NOT NULL,
  ID_UTENTE_AGG                 NUMBER(8),
  ESTREMI_PROVV                 VARCHAR2(200 BYTE),
  PROGR_PROG_SOGG_FINANZIAT     NUMBER(8)       NOT NULL,
  NOTE                          VARCHAR2(4000 BYTE),
  FLAG_ECONOMIE                 CHAR(1 BYTE)    DEFAULT 'N'                   NOT NULL,
  ID_NORMA                      NUMBER(3),
  ID_DELIBERA                   NUMBER(3),
  ID_COMUNE                     NUMBER(8),
  ID_PROVINCIA                  NUMBER(4),
  ID_PERIODO                    NUMBER(8)       NOT NULL,
  ID_SOGGETTO                   NUMBER(8)
)
TABLESPACE PBANDI_SMALL_TBL;

alter TABLE pbandi_t_checklist
add versione              NUMBER(3);

CREATE TABLE pbandi_r_det_prop_cer_checklis
(
	id_dett_proposta_certif  NUMBER(8)  NOT NULL ,
	id_checklist          NUMBER(8)  NOT NULL ,
	versione              NUMBER(3)  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_det_prop_cer_check ON pbandi_r_det_prop_cer_checklis
(id_dett_proposta_certif  ASC,id_checklist  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_det_prop_cer_checklis
	ADD CONSTRAINT  PK_pbandi_r_det_prop_cer_check PRIMARY KEY (id_dett_proposta_certif,id_checklist);



ALTER TABLE pbandi_r_det_prop_cer_checklis
	ADD (CONSTRAINT  FK_PBANDI_T_DETT_PROP_CERT_08 FOREIGN KEY (id_dett_proposta_certif) REFERENCES pbandi_t_dett_proposta_certif(id_dett_proposta_certif));



ALTER TABLE pbandi_r_det_prop_cer_checklis
	ADD (CONSTRAINT  FK_PBANDI_T_CHECKLIST_01 FOREIGN KEY (id_checklist) REFERENCES pbandi_t_checklist(id_checklist));



ALTER TABLE pbandi_r_det_prop_cer_checklis
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_203 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_det_prop_cer_checklis
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_204 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));

alter TABLE pbandi_t_dett_proposta_certif
add dt_ultima_checklist_in_loco  DATE;

alter TABLE pbandi_t_dett_proposta_certif	
add importo_rendicontato  NUMBER(17,2);

alter table pbandi_t_quadro_previsionale
add dt_aggiornamento date;

---------------------------------------------------------------------------------------------------------
create sequence seq_pbandi_t_appalto start with 1 nocache;

CREATE TABLE pbandi_t_appalto
(
	id_appalto            NUMBER(8)  NOT NULL ,
	oggetto_appalto       VARCHAR2(150)  NOT NULL ,
	importo_contratto     NUMBER(17,2)  NOT NULL ,
	dt_inizio_prevista    DATE  NOT NULL ,
	dt_consegna_lavori    DATE  NOT NULL ,
	dt_firma_contratto    DATE  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	id_procedura_aggiudicaz  NUMBER(8)  NOT NULL ,
	dt_inserimento        DATE  NOT NULL ,
	dt_guue               DATE  NULL ,
	dt_guri               DATE  NULL ,
	dt_quot_nazionali     DATE  NULL ,
	dt_web_staz_appaltante  DATE  NULL ,
	dt_web_osservatorio   DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_appalto ON pbandi_t_appalto
(id_appalto  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_appalto
	ADD CONSTRAINT  PK_pbandi_t_appalto PRIMARY KEY (id_appalto);



ALTER TABLE pbandi_t_appalto
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_205 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_appalto
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_206 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_appalto
	ADD (CONSTRAINT  FK_PBANDI_T_PROCEDURA_AGGIU_05 FOREIGN KEY (id_procedura_aggiudicaz) REFERENCES pbandi_t_procedura_aggiudicaz(id_procedura_aggiudicaz));
	
drop table PBANDI_R_RIBASSO_ASTA cascade constraints;

create sequence seq_pbandi_t_ribasso_asta start with 1 nocache;

CREATE TABLE pbandi_t_ribasso_asta
(
	percentuale           NUMBER(5,2)  NULL ,
	importo               NUMBER(17,2)  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	id_ribasso_asta       NUMBER(8)  NOT NULL ,
	id_procedura_aggiudicaz  NUMBER(8)  NULL ,
	id_conto_economico    NUMBER(8)  NOT NULL
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_ribasso_asta ON pbandi_t_ribasso_asta
(id_ribasso_asta  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_ribasso_asta
	ADD CONSTRAINT  PK_pbandi_t_ribasso_asta PRIMARY KEY (id_ribasso_asta);



ALTER TABLE pbandi_t_ribasso_asta
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_170 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_ribasso_asta
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_171 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));

ALTER TABLE pbandi_t_ribasso_asta
	ADD (CONSTRAINT  FK_PBANDI_T_CONTO_ECONOMICO_03 FOREIGN KEY (id_conto_economico) REFERENCES pbandi_t_conto_economico(id_conto_economico));


ALTER TABLE pbandi_t_ribasso_asta
	ADD (CONSTRAINT  FK_PBANDI_T_PROCEDURA_AGGIU_03 FOREIGN KEY (id_procedura_aggiudicaz) REFERENCES pbandi_t_procedura_aggiudicaz(id_procedura_aggiudicaz));

alter TABLE pbandi_t_procedura_aggiudicaz
add id_progetto           NUMBER(8)  NOT NULL;

ALTER TABLE pbandi_t_procedura_aggiudicaz
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_20 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));

drop table pbandi_r_iter_proc_agg cascade constraints;

CREATE TABLE pbandi_r_iter_proc_agg
(
	id_step_aggiudicazione  NUMBER(3)  NOT NULL ,
	dt_prevista           DATE  NOT NULL ,
	dt_effettiva          DATE  NOT NULL ,
	importo_step          NUMBER(13,2)  NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	id_procedura_aggiudicaz  NUMBER(8)  NOT NULL ,
	id_motivo_scostamento  NUMBER(3)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_iter_proc_agg ON pbandi_r_iter_proc_agg
(id_step_aggiudicazione  ASC,id_procedura_aggiudicaz  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_iter_proc_agg
	ADD CONSTRAINT  PK_pbandi_r_iter_proc_agg PRIMARY KEY (id_step_aggiudicazione,id_procedura_aggiudicaz);



ALTER TABLE pbandi_r_iter_proc_agg
	ADD (CONSTRAINT  FK_PBANDI_D_STEP_AGGIUDICAZ_01 FOREIGN KEY (id_step_aggiudicazione) REFERENCES pbandi_d_step_aggiudicazione(id_step_aggiudicazione));



ALTER TABLE pbandi_r_iter_proc_agg
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_126 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_iter_proc_agg
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_127 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_iter_proc_agg
	ADD (CONSTRAINT  FK_PBANDI_T_PROCEDURA_AGGIU_02 FOREIGN KEY (id_procedura_aggiudicaz) REFERENCES pbandi_t_procedura_aggiudicaz(id_procedura_aggiudicaz));



ALTER TABLE pbandi_r_iter_proc_agg
	ADD (CONSTRAINT  FK_PBANDI_D_MOTIVO_SCOSTAME_01 FOREIGN KEY (id_motivo_scostamento) REFERENCES pbandi_d_motivo_scostamento(id_motivo_scostamento));
	
alter table pbandi_t_conto_economico
drop (flag_ribasso_asta);

alter table pbandi_t_conto_economico
drop (id_procedura_aggiudicaz);

alter TABLE pbandi_c_modello
add	codice_modello        VARCHAR2(255)  NULL;
	
alter TABLE pbandi_c_modello
add	codice_modulo         VARCHAR2(255)  NULL ;

update pbandi_c_modello
set codice_modello = 'X', codice_modulo  = 'X';

commit;

alter TABLE pbandi_c_modello
modify	codice_modello not  NULL;
	
alter TABLE pbandi_c_modello
modify	codice_modulo not  NULL ;

alter TABLE pbandi_t_checklist
add id_documento_index    NUMBER(8);

ALTER TABLE pbandi_t_checklist
	ADD (CONSTRAINT  FK_PBANDI_T_DOCUMENTO_INDEX_02 FOREIGN KEY (id_documento_index) REFERENCES pbandi_t_documento_index(id_documento_index));

alter TABLE pbandi_t_csp_progetto
add flag_dettaglio_cup    CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_46 CHECK (flag_dettaglio_cup in ('S','N'));

drop table PBANDI_R_BL_TIPO_DOC_INDEX_MOD cascade constraints;	

alter TABLE pbandi_r_tp_doc_ind_ban_li_int
add id_modello            NUMBER(3);

ALTER TABLE pbandi_r_tp_doc_ind_ban_li_int
	ADD (CONSTRAINT  FK_PBANDI_C_MODELLO_01 FOREIGN KEY (id_modello) REFERENCES pbandi_c_modello(id_modello));

alter TABLE pbandi_t_appalto
add bilancio_preventivo   NUMBER(17,2);

alter table pbandi_r_domanda_indicatori
drop (dt_inizio_validita);	

alter table pbandi_r_domanda_indicatori
drop (dt_fine_validita);

alter TABLE pbandi_r_domanda_indicatori
add dt_inserimento        DATE;
	
alter TABLE pbandi_r_domanda_indicatori
add dt_aggiornamento      DATE;

update pbandi_r_domanda_indicatori
set dt_inserimento = to_date('01/01/2010','dd/mm/yyyy');

update pbandi_r_domanda_indicatori
set dt_aggiornamento = to_date('01/01/2010','dd/mm/yyyy')
where id_UTENTE_INS != 2;

commit;

alter TABLE pbandi_r_domanda_indicatori
modify dt_inserimento not null;

alter TABLE pbandi_r_progetto_fase_monit
add dt_inserimento        DATE;
	
alter TABLE pbandi_r_progetto_fase_monit
add dt_aggiornamento      DATE;

update pbandi_r_progetto_fase_monit
set dt_inserimento = to_date('01/01/2010','dd/mm/yyyy');

commit;

alter TABLE pbandi_r_progetto_fase_monit
modify dt_inserimento not null;

alter TABLE pbandi_t_csp_progetto
add flag_beneficiario_cup  CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_47 CHECK (flag_beneficiario_cup in ('S','N'));

alter table pbandi_t_documento_index_lock
drop (flag_lock_valido);

CREATE TABLE pbandi_d_permesso
(
	id_permesso           NUMBER(3)  NOT NULL ,
	desc_breve_permesso   VARCHAR2(30)  NOT NULL ,
	desc_permesso         VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_permesso ON pbandi_d_permesso
(id_permesso  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_permesso
	ADD CONSTRAINT  PK_pbandi_d_permesso PRIMARY KEY (id_permesso);



CREATE TABLE pbandi_r_permesso_tipo_anagraf
(
	id_permesso           NUMBER(3)  NOT NULL ,
	id_tipo_anagrafica    NUMBER(3)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_permesso_tipo_anag ON pbandi_r_permesso_tipo_anagraf
(id_permesso  ASC,id_tipo_anagrafica  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_permesso_tipo_anagraf
	ADD CONSTRAINT  PK_pbandi_r_permesso_tipo_anag PRIMARY KEY (id_permesso,id_tipo_anagrafica);



ALTER TABLE pbandi_r_permesso_tipo_anagraf
	ADD (CONSTRAINT  FK_PBANDI_D_PERMESSO_01 FOREIGN KEY (id_permesso) REFERENCES pbandi_d_permesso(id_permesso));



ALTER TABLE pbandi_r_permesso_tipo_anagraf
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_ANAGRAFICA_09 FOREIGN KEY (id_tipo_anagrafica) REFERENCES pbandi_d_tipo_anagrafica(id_tipo_anagrafica));
	
alter table PBANDI_R_DOMANDA_INDICATORI 
modify VALORE_PROG_INIZIALE	null;

alter table pbandi_t_quota_parte_doc_spesa
drop (costo_orario);

alter table pbandi_t_checklist
modify dt_controllo not null;

alter table PBANDI_R_PROGETTO_FASE_MONIT
modify DT_INIZIO_PREVISTA null;

alter table PBANDI_R_PROGETTO_FASE_MONIT
modify DT_FINE_PREVISTA null;

alter table PBANDI_T_PROCEDURA_AGGIUDICAZ
modify importo number(17,2);

