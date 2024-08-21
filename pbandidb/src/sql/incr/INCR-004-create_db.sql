/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

alter table PBANDI_R_DOC_SPESA_PROGETTO
add TASK varchar2(100);

CREATE UNIQUE INDEX AK1_pbandi_r_pag_quot_parte_do ON pbandi_r_pag_quot_parte_doc_sp
(id_quota_parte_doc_spesa  ASC,id_pagamento  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_pag_quot_parte_doc_sp
ADD CONSTRAINT  AK1_pbandi_r_pag_quot_parte_do UNIQUE (id_quota_parte_doc_spesa,id_pagamento);


CREATE TABLE pbandi_r_sogg_tipo_anagrafica
(
	id_soggetto           NUMBER(8)  NOT NULL ,
	id_tipo_anagrafica    NUMBER(3)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	flag_aggiornato_flux  CHAR(1)  NOT NULL ,
	dt_inizio_validita	  date not null,
	dt_fine_validita	  date null
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_sogg_tipo_anagrafi ON pbandi_r_sogg_tipo_anagrafica
(id_soggetto  ASC,id_tipo_anagrafica  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_sogg_tipo_anagrafica
	ADD CONSTRAINT  PK_pbandi_r_sogg_tipo_anagrafi PRIMARY KEY (id_soggetto,id_tipo_anagrafica);



ALTER TABLE pbandi_r_sogg_tipo_anagrafica
	ADD (CONSTRAINT  FK_PBANDI_T_SOGGETTO_17 FOREIGN KEY (id_soggetto) REFERENCES pbandi_t_soggetto(id_soggetto));



ALTER TABLE pbandi_r_sogg_tipo_anagrafica
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_ANAGRAFICA_06 FOREIGN KEY (id_tipo_anagrafica) REFERENCES pbandi_d_tipo_anagrafica(id_tipo_anagrafica));


ALTER TABLE pbandi_r_sogg_tipo_anagrafica
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_156 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));

ALTER TABLE pbandi_r_sogg_tipo_anagrafica
	MODIFY flag_aggiornato_flux CONSTRAINT  ck_flag_23 CHECK (flag_aggiornato_flux in ('S','N'));


ALTER TABLE pbandi_r_sogg_tipo_anagrafica
	MODIFY flag_aggiornato_flux DEFAULT 'N';

ALTER TABLE pbandi_r_sogg_tipo_anagrafica
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_157 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);
	
alter TABLE pbandi_r_soggetto_progetto
add flag_aggiornato_flux  VARCHAR2(1) DEFAULT 'N' NOT NULL;

alter table pbandi_c_ruolo_processo
drop (id_tipo_beneficiario);	

ALTER TABLE pbandi_c_ruolo_processo
ADD id_tipo_soggetto_correlato  NUMBER(3);

ALTER TABLE pbandi_c_ruolo_processo
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_SOGG_CORRE_01 FOREIGN KEY (id_tipo_soggetto_correlato) REFERENCES pbandi_d_tipo_sogg_correlato(id_tipo_soggetto_correlato) ON DELETE SET NULL);

alter TABLE pbandi_t_conto_economico	
add note_conto_economico    VARCHAR2(4000);

alter TABLE pbandi_t_conto_economico	
add riferimento           VARCHAR2(255);

alter table pbandi_t_dati_progetto_monit
add flag_progetto_di_completamento  CHAR(1) DEFAULT 'N' NOT NULL;

ALTER TABLE pbandi_t_dati_progetto_monit
	MODIFY flag_progetto_di_completamento CONSTRAINT  ck_flag_17 CHECK (flag_progetto_di_completamento in ('S','N'));
	
CREATE TABLE pbandi_r_doc_index_tipo_anag
(
	id_tipo_anagrafica    NUMBER(3)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	id_tipo_documento_index  NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_doc_index_tipo_ana ON pbandi_r_doc_index_tipo_anag
(id_tipo_documento_index  ASC,id_tipo_anagrafica  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_doc_index_tipo_anag
	ADD CONSTRAINT  PK_pbandi_r_doc_index_tipo_ana PRIMARY KEY (id_tipo_documento_index,id_tipo_anagrafica);


CREATE TABLE pbandi_r_tp_doc_ind_ban_li_int
(
	id_tipo_documento_index  NUMBER(3)  NOT NULL ,
	progr_bando_linea_intervento  NUMBER(8)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_tp_doc_ind_ban_li_ ON pbandi_r_tp_doc_ind_ban_li_int
(id_tipo_documento_index  ASC,progr_bando_linea_intervento  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_tp_doc_ind_ban_li_int
	ADD CONSTRAINT  PK_pbandi_r_tp_doc_ind_ban_li_ PRIMARY KEY (id_tipo_documento_index,progr_bando_linea_intervento);



ALTER TABLE pbandi_r_doc_index_tipo_anag
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_ANAGRAFICA_07 FOREIGN KEY (id_tipo_anagrafica) REFERENCES pbandi_d_tipo_anagrafica(id_tipo_anagrafica));



ALTER TABLE pbandi_r_doc_index_tipo_anag
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_164 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_doc_index_tipo_anag
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_165 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);



ALTER TABLE pbandi_r_doc_index_tipo_anag
	ADD (CONSTRAINT  FK_PBANDI_C_TIPO_DOC_INDEX_03 FOREIGN KEY (id_tipo_documento_index) REFERENCES pbandi_c_tipo_documento_index(id_tipo_documento_index));





ALTER TABLE pbandi_r_tp_doc_ind_ban_li_int
	ADD (CONSTRAINT  FK_PBANDI_C_TIPO_DOC_INDEX_02 FOREIGN KEY (id_tipo_documento_index) REFERENCES pbandi_c_tipo_documento_index(id_tipo_documento_index));



ALTER TABLE pbandi_r_tp_doc_ind_ban_li_int
	ADD (CONSTRAINT  FK_PBANDI_R_BANDO_LINEA_INT_09 FOREIGN KEY (progr_bando_linea_intervento) REFERENCES pbandi_r_bando_linea_intervent(progr_bando_linea_intervento));



ALTER TABLE pbandi_r_tp_doc_ind_ban_li_int
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_162 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_tp_doc_ind_ban_li_int
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_163 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);

drop table pbandi_c_ruolo_processo cascade constraints;

CREATE TABLE pbandi_c_ruolo_di_processo
(
	id_ruolo_di_processo  NUMBER(5)  NOT NULL ,
	codice                VARCHAR2(50)  NOT NULL ,
	desc_ruolo_di_processo  VARCHAR2(255)  NULL ,
	id_definizione_processo  NUMBER(8)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_c_ruolo_di_processo ON pbandi_c_ruolo_di_processo
(id_ruolo_di_processo  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_c_ruolo_di_processo
	ADD CONSTRAINT  PK_pbandi_c_ruolo_di_processo PRIMARY KEY (id_ruolo_di_processo);



CREATE TABLE pbandi_d_attivita_di_processo
(
	id_attivita_di_processo  NUMBER(3)  NOT NULL ,
	desc_breve_attivita_di_proces  VARCHAR2(100)  NOT NULL ,
	desc_attivita_di_processo  VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_attivita_di_proces ON pbandi_d_attivita_di_processo
(id_attivita_di_processo  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_attivita_di_processo
	ADD CONSTRAINT  PK_pbandi_d_attivita_di_proces PRIMARY KEY (id_attivita_di_processo);



CREATE TABLE pbandi_d_notifica
(
	id_notifica           NUMBER(3)  NOT NULL ,
	desc_breve_notifica   VARCHAR2(100)  NOT NULL ,
	desc_notifica         VARCHAR2(255)  NOT NULL ,
	messaggio             VARCHAR2(4000)  NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_notifica ON pbandi_d_notifica
(id_notifica  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_notifica
	ADD CONSTRAINT  PK_pbandi_d_notifica PRIMARY KEY (id_notifica);



CREATE TABLE pbandi_r_attivita_notifica
(
	id_attivita_di_processo  NUMBER(3)  NOT NULL ,
	id_notifica           NUMBER(3)  NOT NULL ,
	id_ruolo_di_processo  NUMBER(5)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_attivita_notifica ON pbandi_r_attivita_notifica
(id_attivita_di_processo  ASC,id_notifica  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_attivita_notifica
	ADD CONSTRAINT  PK_pbandi_r_attivita_notifica PRIMARY KEY (id_attivita_di_processo,id_notifica);



CREATE TABLE pbandi_r_ruolo_tipo_anagrafica
(
	id_ruolo_di_processo  NUMBER(5)  NOT NULL ,
	id_tipo_anagrafica    NUMBER(3)  NOT NULL ,
	id_tipo_soggetto_correlato  NUMBER(3)  NULL ,
	id_ruolo_tipo_anagrafica  NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_ruolo_tipo_anagraf ON pbandi_r_ruolo_tipo_anagrafica
(id_ruolo_tipo_anagrafica  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_ruolo_tipo_anagrafica
	ADD CONSTRAINT  PK_pbandi_r_ruolo_tipo_anagraf PRIMARY KEY (id_ruolo_tipo_anagrafica);



ALTER TABLE pbandi_c_ruolo_di_processo
	ADD (CONSTRAINT  FK_PBANDI_C_DEFINIZ_PROCES_01 FOREIGN KEY (id_definizione_processo) REFERENCES pbandi_c_definizione_processo(id_definizione_processo));



ALTER TABLE pbandi_r_attivita_notifica
	ADD (CONSTRAINT  FK_PBANDI_D_ATT_DI_PROCESSO_01 FOREIGN KEY (id_attivita_di_processo) REFERENCES pbandi_d_attivita_di_processo(id_attivita_di_processo));



ALTER TABLE pbandi_r_attivita_notifica
	ADD (CONSTRAINT  FK_PBANDI_D_NOTIFICA_01 FOREIGN KEY (id_notifica) REFERENCES pbandi_d_notifica(id_notifica));



ALTER TABLE pbandi_r_attivita_notifica
	ADD (CONSTRAINT  FK_PBANDI_C_RUOLO_DI_PROCES_02 FOREIGN KEY (id_ruolo_di_processo) REFERENCES pbandi_c_ruolo_di_processo(id_ruolo_di_processo));



ALTER TABLE pbandi_r_ruolo_tipo_anagrafica
	ADD (CONSTRAINT  FK_PBANDI_C_RUOLO_DI_PROCES_01 FOREIGN KEY (id_ruolo_di_processo) REFERENCES pbandi_c_ruolo_di_processo(id_ruolo_di_processo));



ALTER TABLE pbandi_r_ruolo_tipo_anagrafica
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_ANAGRAFICA_03 FOREIGN KEY (id_tipo_anagrafica) REFERENCES pbandi_d_tipo_anagrafica(id_tipo_anagrafica));



ALTER TABLE pbandi_r_ruolo_tipo_anagrafica
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_SOGG_CORRE_01 FOREIGN KEY (id_tipo_soggetto_correlato) REFERENCES pbandi_d_tipo_sogg_correlato(id_tipo_soggetto_correlato) ON DELETE SET NULL);

alter TABLE pbandi_t_dati_progetto_monit
add id_sogg_titolare_cipe  NUMBER(8);

ALTER TABLE pbandi_t_dati_progetto_monit
	ADD (CONSTRAINT  FK_PBANDI_T_ENTE_COMPETENZA_04 FOREIGN KEY (id_sogg_titolare_cipe) REFERENCES pbandi_t_ente_competenza(id_ente_competenza) ON DELETE SET NULL);

---------------------------------
	
alter table pbandi_t_progetto
drop (DT_INIZIO_PROGETTO_EFFETTIVA);

alter table pbandi_t_progetto
drop (DT_FINE_PROGETTO_EFFETTIVA);

alter table pbandi_t_progetto
drop (DURATA_PROGETTO);

alter table pbandi_t_domanda
drop (DURATA_PROGETTO);

alter table pbandi_r_bando_linea_intervent
add mesi_durata_da_dt_concessione  NUMBER(3);

	
alter TABLE pbandi_r_bando_linea_intervent
add id_obiettivo_specif_qsn  NUMBER(3);	

alter TABLE pbandi_r_bando_linea_intervent
add id_categoria_cipe     NUMBER(3);

alter TABLE pbandi_r_bando_linea_intervent
add id_tipologia_cipe     NUMBER(3);


ALTER TABLE pbandi_r_bando_linea_intervent
	ADD (CONSTRAINT  FK_PBANDI_D_OBIETTIVO_SPECI_02 FOREIGN KEY (id_obiettivo_specif_qsn) REFERENCES pbandi_d_obiettivo_specif_qsn(id_obiettivo_specif_qsn) ON DELETE SET NULL);



ALTER TABLE pbandi_r_bando_linea_intervent
	ADD (CONSTRAINT  FK_PBANDI_D_CATEGORIA_CIPE_02 FOREIGN KEY (id_categoria_cipe) REFERENCES pbandi_d_categoria_cipe(id_categoria_cipe) ON DELETE SET NULL);


ALTER TABLE pbandi_r_bando_linea_intervent
	ADD (CONSTRAINT  FK_PBANDI_D_TIPOLOGIA_CIPE_02 FOREIGN KEY (id_tipologia_cipe) REFERENCES pbandi_d_tipologia_cipe(id_tipologia_cipe) ON DELETE SET NULL);

alter table pbandi_t_soggetto
drop (id_tipo_anagrafica);

alter table pbandi_r_progetto_fase_monit
modify DT_INIZIO_EFFETTIVA null; 

alter table pbandi_r_progetto_fase_monit
modify DT_FINE_EFFETTIVA null;

update PBANDI_T_DOCUMENTO_DI_SPESA
set IMPONIBILE = nvl(IMPONIBILE,0) + nvl(IMPORTO_RITENUTA,0);

commit;

alter table PBANDI_T_DOCUMENTO_DI_SPESA
drop (IMPORTO_RITENUTA);

alter TABLE pbandi_d_ind_risultato_program
add id_linea_di_intervento  NUMBER(3);

ALTER TABLE pbandi_d_ind_risultato_program
	ADD (CONSTRAINT  FK_PBANDI_D_LINEA_INTERV_04 FOREIGN KEY (id_linea_di_intervento) REFERENCES pbandi_d_linea_di_intervento(id_linea_di_intervento));
	
alter TABLE pbandi_d_indicatori
add id_linea_di_intervento  NUMBER(3);

ALTER TABLE pbandi_d_indicatori
	ADD (CONSTRAINT  FK_PBANDI_D_LINEA_INTERV_05 FOREIGN KEY (id_linea_di_intervento) REFERENCES pbandi_d_linea_di_intervento(id_linea_di_intervento) ON DELETE SET NULL);

create sequence seq_pbandi_r_ecc_ban_lin_doc_p start with 1 nocache;

CREATE TABLE pbandi_r_eccez_ban_lin_doc_pag
(
	id_modalita_pagamento  NUMBER(3)  NULL ,
	id_tipo_documento_spesa  NUMBER(3)  NOT NULL ,
	flag_aggiunta         CHAR(1)  NOT NULL ,
	progr_bando_linea_intervento  NUMBER(8)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	progr_eccez_ban_lin_doc_pag  NUMBER(8)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_eccez_ban_lin_doc_ ON pbandi_r_eccez_ban_lin_doc_pag
(progr_eccez_ban_lin_doc_pag  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_eccez_ban_lin_doc_pag
	ADD CONSTRAINT  PK_pbandi_r_eccez_ban_lin_doc_ PRIMARY KEY (progr_eccez_ban_lin_doc_pag);


ALTER TABLE pbandi_r_eccez_ban_lin_doc_pag
	MODIFY flag_aggiunta CONSTRAINT  ck_flag_29 CHECK (flag_aggiunta in ('S','N'));



ALTER TABLE pbandi_r_eccez_ban_lin_doc_pag
	ADD (CONSTRAINT  FK_PBANDI_D_MOD_PAGAMENTO_02 FOREIGN KEY (id_modalita_pagamento) REFERENCES pbandi_d_modalita_pagamento(id_modalita_pagamento) ON DELETE SET NULL);



ALTER TABLE pbandi_r_eccez_ban_lin_doc_pag
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_DOC_SPESA_02 FOREIGN KEY (id_tipo_documento_spesa) REFERENCES pbandi_d_tipo_documento_spesa(id_tipo_documento_spesa));



ALTER TABLE pbandi_r_eccez_ban_lin_doc_pag
	ADD (CONSTRAINT  FK_PBANDI_R_BANDO_LINEA_INT_12 FOREIGN KEY (progr_bando_linea_intervento) REFERENCES pbandi_r_bando_linea_intervent(progr_bando_linea_intervento));



ALTER TABLE pbandi_r_eccez_ban_lin_doc_pag
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_176 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_eccez_ban_lin_doc_pag
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_177 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);
		
	

CREATE TABLE pbandi_r_tipo_doc_modalita_pag
(
	id_tipo_documento_spesa  NUMBER(3)  NOT NULL ,
	id_modalita_pagamento  NUMBER(3)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_tipo_doc_modalita_ ON pbandi_r_tipo_doc_modalita_pag
(id_tipo_documento_spesa  ASC,id_modalita_pagamento  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_tipo_doc_modalita_pag
	ADD CONSTRAINT  PK_pbandi_r_tipo_doc_modalita_ PRIMARY KEY (id_tipo_documento_spesa,id_modalita_pagamento);



ALTER TABLE pbandi_r_tipo_doc_modalita_pag
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_DOC_SPESA_03 FOREIGN KEY (id_tipo_documento_spesa) REFERENCES pbandi_d_tipo_documento_spesa(id_tipo_documento_spesa));



ALTER TABLE pbandi_r_tipo_doc_modalita_pag
	ADD (CONSTRAINT  FK_PBANDI_D_MOD_PAGAMENTO_03 FOREIGN KEY (id_modalita_pagamento) REFERENCES pbandi_d_modalita_pagamento(id_modalita_pagamento));



ALTER TABLE pbandi_r_tipo_doc_modalita_pag
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_178 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_tipo_doc_modalita_pag
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_179 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente) ON DELETE SET NULL);


CREATE TABLE pbandi_d_tipo_dichiaraz_spesa
(
	id_tipo_dichiaraz_spesa  NUMBER(3)  NOT NULL ,
	desc_breve_tipo_dichiara_spesa  VARCHAR2(20)  NOT NULL ,
	desc_tipo_dichiarazione_spesa  VARCHAR2(100)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_dichiaraz_spe ON pbandi_d_tipo_dichiaraz_spesa
(id_tipo_dichiaraz_spesa  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_dichiaraz_spesa
	ADD CONSTRAINT  PK_pbandi_d_tipo_dichiaraz_spe PRIMARY KEY (id_tipo_dichiaraz_spesa);

ALTER TABLE pbandi_t_dichiarazione_spesa
ADD id_tipo_dichiaraz_spesa  NUMBER(3);

ALTER TABLE pbandi_t_dichiarazione_spesa
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_DICH_SPESA_01 FOREIGN KEY (id_tipo_dichiaraz_spesa) REFERENCES pbandi_d_tipo_dichiaraz_spesa(id_tipo_dichiaraz_spesa));

alter table pbandi_t_documento_index
add id_progetto           NUMBER(8);

ALTER TABLE pbandi_t_documento_index
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_18 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto) ON DELETE SET NULL);

delete pbandi_t_dati_progetto_monit a
where not exists (select 'x' from pbandi_t_progetto b where a.id_progetto = b.id_progetto);

commit;
	
ALTER TABLE pbandi_t_dati_progetto_monit
    ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_10 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));

	
alter table pbandi_r_soggetto_progetto
drop (id_soggetto_intermediario);

alter table pbandi_r_soggetto_domanda
drop (id_soggetto_intermediario);

alter table pbandi_t_conto_economico
drop (costo_tot_ammesso_finanziamen);

alter table pbandi_t_conto_economico
drop (costo_totale_recuperato);

alter table PBANDI_R_CONTO_ECONOM_MOD_AGEV
drop (QUOTA_IMP_AMMESSO_FINANZIAMENT);
