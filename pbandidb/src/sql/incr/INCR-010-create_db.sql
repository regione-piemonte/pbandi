/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE TABLE pbandi_t_preview_dett_prop_cer
(
	id_preview_dett_prop_cer  NUMBER  NOT NULL ,
	nome_bando_linea      VARCHAR2(255)  NOT NULL ,
	codice_progetto       VARCHAR2(100)  NOT NULL ,
	denominazione_beneficiario  VARCHAR2(255)  NOT NULL ,
	importo_certificazione_netto  NUMBER(17,2)  NULL ,
	importo_revoche       NUMBER(17,2)  NULL ,
	flag_attivo           CHAR(1)   DEFAULT  'S' NOT NULL  CONSTRAINT  ck_flag_53 CHECK (flag_attivo in ('S','N')),
	id_proposta_certificaz  NUMBER(8)  NOT NULL ,
	id_progetto           NUMBER(8)  NOT NULL ,
	importo_pagamenti       NUMBER(17,2)  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	progr_bando_linea_intervento  NUMBER(8)  not NULL,
	id_soggetto_beneficiario NUMBER(8)  not NULL
) TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_preview_dett_prop_ ON pbandi_t_preview_dett_prop_cer
(id_preview_dett_prop_cer  ASC)
	TABLESPACE PBANDI_MEDIUM_IDX;



ALTER TABLE pbandi_t_preview_dett_prop_cer
	ADD CONSTRAINT  PK_pbandi_t_preview_dett_prop_ PRIMARY KEY (id_preview_dett_prop_cer);


ALTER TABLE pbandi_t_preview_dett_prop_cer
	ADD (CONSTRAINT  FK_PBANDI_T_SOGGETTO_08 FOREIGN KEY (id_soggetto_beneficiario) REFERENCES pbandi_t_soggetto(id_soggetto));	
	

ALTER TABLE pbandi_t_preview_dett_prop_cer
	ADD (CONSTRAINT  FK_PBANDI_R_BANDO_LINEA_INT_16 FOREIGN KEY (progr_bando_linea_intervento) REFERENCES pbandi_r_bando_linea_intervent(progr_bando_linea_intervento));	

ALTER TABLE pbandi_t_preview_dett_prop_cer
	ADD (CONSTRAINT  FK_PBANDI_T_PROPOSTA_CERTIF_04 FOREIGN KEY (id_proposta_certificaz) REFERENCES pbandi_t_proposta_certificaz(id_proposta_certificaz));



ALTER TABLE pbandi_t_preview_dett_prop_cer
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_27 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_t_preview_dett_prop_cer
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_213 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_preview_dett_prop_cer
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_214 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));
	
create sequence seq_pbandi_t_preview_dett_prop start with 1 nocache;

alter TABLE pbandi_t_dett_proposta_certif
add importo_interessi_recuperati  NUMBER(17,2)  NULL ;

alter TABLE pbandi_t_dett_proposta_certif
add imp_interessi_recuperati_netti  NUMBER(17,2)  NULL ;
	
alter TABLE pbandi_t_dett_proposta_certif
add imp_certificabile_netto_soppr  NUMBER(17,2)  NULL ;
	
alter TABLE pbandi_t_dett_proposta_certif
add imp_revoche_netto_soppressioni  NUMBER(17,2)  NULL ;
	
alter TABLE pbandi_t_dett_proposta_certif
add imp_certificabile_netto_revoc  NUMBER(17,2)  NULL ;
	
alter TABLE pbandi_t_dett_proposta_certif
add importo_revoche_intermedio  NUMBER(17,2)  NULL ;
	
alter TABLE pbandi_t_dett_proposta_certif
add imp_certificazione_netto_prec  NUMBER(17,2)  NULL ;
	
alter TABLE pbandi_t_dett_proposta_certif
add avanzamento           NUMBER(17,2)  NULL ;
	
alter TABLE pbandi_t_dett_proposta_certif
add importo_soppressioni_netto  NUMBER(17,2)  NULL ;
	
alter TABLE pbandi_t_dett_proposta_certif
add importo_recuperi_prerecuperi  NUMBER(17,2)  NULL ; 

alter TABLE pbandi_t_dett_proposta_certif
add flag_attivo CHAR(1)   DEFAULT  'S' NOT NULL  CONSTRAINT  ck_flag_54 CHECK (flag_attivo in ('S','N'));

alter table pbandi_r_domanda_indicatori
add flag_non_applicabile  CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_55 CHECK (flag_non_applicabile in ('S','N'));

/*
*** inizio bilancio
*/
CREATE SEQUENCE seq_pbandi_t_beneficiario_bila
	START WITH 1
	NOCACHE;

CREATE SEQUENCE seq_pbandi_t_dati_pagamento_at
	START WITH 1
	NOCACHE;

CREATE SEQUENCE seq_pbandi_t_atto_liquidazione
	START WITH 1
	NOCACHE;

CREATE SEQUENCE seq_pbandi_t_impegno
	START WITH 1
	NOCACHE;

CREATE SEQUENCE seq_pbandi_r_liquidazione
	START WITH 1
	NOCACHE;

CREATE SEQUENCE seq_pbandi_t_mandato_quietanza
	START WITH 1
	NOCACHE;

CREATE SEQUENCE seq_pbandi_t_provvedimento
	START WITH 1
	NOCACHE;

CREATE SEQUENCE seq_pbandi_t_capitolo
	START WITH 1
	NOCACHE;
	


CREATE TABLE pbandi_d_aliquota_ritenuta
(
	id_tipo_ritenuta      NUMBER(3)  NOT NULL ,
	id_tipo_sogg_ritenuta  NUMBER(3)  NOT NULL ,
	id_aliquota_ritenuta  NUMBER(3)  NOT NULL ,
	desc_aliquota         VARCHAR2(50)  NOT NULL ,
	perc_aliquota         NUMBER(5,2)  NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_aliquota_ritenuta ON pbandi_d_aliquota_ritenuta
(id_aliquota_ritenuta  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_aliquota_ritenuta
	ADD CONSTRAINT  PK_pbandi_d_aliquota_ritenuta PRIMARY KEY (id_aliquota_ritenuta);



CREATE TABLE pbandi_d_altra_cassa_previdenz
(
	id_altra_cassa_previdenz  NUMBER(3)  NOT NULL ,
	desc_altra_cassa      VARCHAR2(200)  NOT NULL ,
	cod_altra_cassa       VARCHAR2(3)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_altra_cassa_previd ON pbandi_d_altra_cassa_previdenz
(id_altra_cassa_previdenz  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_altra_cassa_previdenz
	ADD CONSTRAINT  PK_pbandi_d_altra_cassa_previd PRIMARY KEY (id_altra_cassa_previdenz);



CREATE TABLE pbandi_d_attivita_inps
(
	id_attivita_inps      NUMBER(3)  NOT NULL ,
	desc_attivita_inps    VARCHAR2(200)  NOT NULL ,
	cod_attivita_inps     VARCHAR2(3)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_attivita_inps ON pbandi_d_attivita_inps
(id_attivita_inps  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_attivita_inps
	ADD CONSTRAINT  PK_pbandi_d_attivita_inps PRIMARY KEY (id_attivita_inps);





CREATE TABLE pbandi_d_provenienza_capitolo
(
	id_provenienza_capitolo  NUMBER(3)  NOT NULL ,
	desc_provenienza_capitolo  VARCHAR2(30)  NOT NULL ,
	desc_breve_provenienza_capitol  VARCHAR2(2)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_provenienza_capito ON pbandi_d_provenienza_capitolo
(id_provenienza_capitolo  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_provenienza_capitolo
	ADD CONSTRAINT  PK_pbandi_d_provenienza_capito PRIMARY KEY (id_provenienza_capitolo);



CREATE TABLE pbandi_d_rischio_inail
(
	id_rischio_inail      NUMBER(3)  NOT NULL ,
	desc_rischio_inail    VARCHAR2(100)  NOT NULL ,
	desc_breve_rischio_inail  VARCHAR2(3)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_rischio_inail ON pbandi_d_rischio_inail
(id_rischio_inail  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_rischio_inail
	ADD CONSTRAINT  PK_pbandi_d_rischio_inail PRIMARY KEY (id_rischio_inail);



CREATE TABLE pbandi_d_settore_ente
(
	id_settore_ente       NUMBER(3)  NOT NULL ,
	desc_breve_settore    VARCHAR2(2)  NOT NULL ,
	desc_settore          VARCHAR2(50)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_ente_competenza    NUMBER(8)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_settore_ente ON pbandi_d_settore_ente
(id_settore_ente  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_settore_ente
	ADD CONSTRAINT  PK_pbandi_d_settore_ente PRIMARY KEY (id_settore_ente);



CREATE TABLE pbandi_d_situazione_inps
(
	id_situazione_inps    NUMBER(3)  NOT NULL ,
	desc_situazione_inps  VARCHAR2(50)  NOT NULL ,
	desc_breve_situazione_inps  VARCHAR2(2)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_situazione_inps ON pbandi_d_situazione_inps
(id_situazione_inps  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_situazione_inps
	ADD CONSTRAINT  PK_pbandi_d_situazione_inps PRIMARY KEY (id_situazione_inps);



CREATE TABLE pbandi_d_stato_atto
(
	id_stato_atto         NUMBER(3)  NOT NULL ,
	desc_stato_atto       VARCHAR2(100)  NOT NULL ,
	desc_breve_stato_atto  VARCHAR2(2)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_stato_atto ON pbandi_d_stato_atto
(id_stato_atto  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_stato_atto
	ADD CONSTRAINT  PK_pbandi_d_stato_atto PRIMARY KEY (id_stato_atto);



CREATE TABLE pbandi_d_stato_impegno
(
	id_stato_impegno      NUMBER(3)  NOT NULL ,
	desc_stato_impegno    VARCHAR2(40)  NOT NULL ,
	desc_breve_stato_impegno  VARCHAR2(2)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_stato_impegno ON pbandi_d_stato_impegno
(id_stato_impegno  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_stato_impegno
	ADD CONSTRAINT  PK_pbandi_d_stato_impegno PRIMARY KEY (id_stato_impegno);



CREATE TABLE pbandi_d_tipo_altra_cassa_prev
(
	id_tipo_altra_cassa_prev  NUMBER(3)  NOT NULL ,
	desc_tipo_altra_cassa  VARCHAR2(150)  NOT NULL ,
	desc_breve_tipo_altra_cassa  VARCHAR2(3)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_altra_cassa_p ON pbandi_d_tipo_altra_cassa_prev
(id_tipo_altra_cassa_prev  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_altra_cassa_prev
	ADD CONSTRAINT  PK_pbandi_d_tipo_altra_cassa_p PRIMARY KEY (id_tipo_altra_cassa_prev);



CREATE TABLE pbandi_d_tipo_fondo
(
	id_tipo_fondo         NUMBER(3)  NOT NULL ,
	desc_tipo_fondo       VARCHAR2(30)  NOT NULL ,
	desc_breve_tipo_fondo  VARCHAR2(3)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_fondo ON pbandi_d_tipo_fondo
(id_tipo_fondo  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_fondo
	ADD CONSTRAINT  PK_pbandi_d_tipo_fondo PRIMARY KEY (id_tipo_fondo);



CREATE TABLE pbandi_d_tipo_provvedimento
(
	id_tipo_provvedimento  NUMBER(3)  NOT NULL ,
	desc_breve_tipo_provvedimento  VARCHAR2(2)  NOT NULL ,
	desc_tipo_provvedimento  VARCHAR2(50)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_provvedimento ON pbandi_d_tipo_provvedimento
(id_tipo_provvedimento  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_provvedimento
	ADD CONSTRAINT  PK_pbandi_d_tipo_provvedimento PRIMARY KEY (id_tipo_provvedimento);



CREATE TABLE pbandi_d_tipo_ritenuta
(
	id_tipo_ritenuta      NUMBER(3)  NOT NULL ,
	desc_tipo_ritenuta    VARCHAR2(50)  NOT NULL ,
	desc_breve_tipo_ritenuta  VARCHAR2(2)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_ritenuta ON pbandi_d_tipo_ritenuta
(id_tipo_ritenuta  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_ritenuta
	ADD CONSTRAINT  PK_pbandi_d_tipo_ritenuta PRIMARY KEY (id_tipo_ritenuta);



CREATE TABLE pbandi_d_tipo_sogg_ritenuta
(
	id_tipo_sogg_ritenuta  NUMBER(3)  NOT NULL ,
	desc_tipo_sogg_ritenuta  VARCHAR2(50)  NOT NULL ,
	desc_breve_tipo_sogg_ritenuta  VARCHAR2(2)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_tipo_sogg_ritenuta ON pbandi_d_tipo_sogg_ritenuta
(id_tipo_sogg_ritenuta  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_tipo_sogg_ritenuta
	ADD CONSTRAINT  PK_pbandi_d_tipo_sogg_ritenuta PRIMARY KEY (id_tipo_sogg_ritenuta);



CREATE TABLE pbandi_r_impegno_bando_linea
(
	id_impegno            NUMBER(8)  NOT NULL ,
	progr_bando_linea_intervento  NUMBER(8)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_impegno_bando_line ON pbandi_r_impegno_bando_linea
(id_impegno  ASC,progr_bando_linea_intervento  ASC)
TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_impegno_bando_linea
	ADD CONSTRAINT  PK_pbandi_r_impegno_bando_line PRIMARY KEY (id_impegno,progr_bando_linea_intervento);



CREATE TABLE pbandi_r_impegno_progetto
(
	id_impegno            NUMBER(8)  NOT NULL ,
	id_progetto           NUMBER(8)  NOT NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_impegno_progetto ON pbandi_r_impegno_progetto
(id_impegno  ASC,id_progetto  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_impegno_progetto
	ADD CONSTRAINT  PK_pbandi_r_impegno_progetto PRIMARY KEY (id_impegno,id_progetto);



CREATE TABLE pbandi_r_liquidazione
(
	progr_liquidazione    NUMBER(8)  NOT NULL ,
	progr_liquidazione_precedente  NUMBER(8)  NULL ,
	id_atto_liquidazione  NUMBER(8)  NOT NULL ,
	id_impegno            NUMBER(8)  NOT NULL ,
	num_bilancio_liquidazione  NUMBER(6)  NULL ,
	importo_liquidato     NUMBER(15,2)  NOT NULL ,
	cup_liquidazione      VARCHAR2(15)  NULL ,
	cig_liquidazione      VARCHAR2(10)  NULL ,
	dt_agg_bilancio_liquidazione  DATE  NULL ,
	dt_inserimento        DATE  NOT NULL ,
	dt_aggiornamento      DATE  NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	flag_rimossa_bilancio  CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_60 CHECK (flag_rimossa_bilancio in ('S','N')),
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	dt_rifiuto_ragioneria date
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_liquidazione ON pbandi_r_liquidazione
(progr_liquidazione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_liquidazione
	ADD CONSTRAINT  PK_pbandi_r_liquidazione PRIMARY KEY (progr_liquidazione);



CREATE TABLE pbandi_r_tip_altr_cas_sogg_rit
(
	id_tipo_sogg_ritenuta  NUMBER(3)  NOT NULL ,
	id_tipo_altra_cassa_prev  NUMBER(3)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_tip_altr_cas_sogg_ ON pbandi_r_tip_altr_cas_sogg_rit
(id_tipo_sogg_ritenuta  ASC,id_tipo_altra_cassa_prev  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_tip_altr_cas_sogg_rit
	ADD CONSTRAINT  PK_pbandi_r_tip_altr_cas_sogg_ PRIMARY KEY (id_tipo_sogg_ritenuta,id_tipo_altra_cassa_prev);



CREATE TABLE pbandi_t_atto_liquidazione
(
	id_atto_liquidazione  NUMBER(8)  NOT NULL ,
	anno_atto             NUMBER(4)  NOT NULL ,
	numero_atto           VARCHAR2(5)  NULL ,
	desc_atto             VARCHAR2(150)  NULL ,
	importo_atto          NUMBER(15,2)  NOT NULL ,
	dt_emissione_atto     DATE  NOT NULL ,
	dt_aggiornamento_bilancio  DATE  NULL ,
	dt_scadenza_atto      DATE  NULL ,
	dt_completamento_atto  DATE  NULL ,
	dt_annulamento_atto   DATE  NULL ,
	dt_ricezione_atto     DATE  NULL ,
	dt_richiesta_modifica  DATE  NULL ,
	testo_richiesta_modifica  VARCHAR2(150)  NULL ,
	note_atto             VARCHAR2(70)  NULL ,
	flag_allegati_fatture  CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_56 CHECK (flag_allegati_fatture in ('S','N')),
	flag_allegati_estratto_prov  CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_57 CHECK (flag_allegati_estratto_prov in ('S','N')),
	flag_allegati_doc_giustificat  CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_58 CHECK (flag_allegati_doc_giustificat in ('S','N')),
	flag_allegati_dichiarazione  CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_59 CHECK (flag_allegati_dichiarazione in ('S','N')),
	testo_allegati_altro  VARCHAR2(100)  NULL ,
	imp_non_soggetto_ritenuta  NUMBER(15,2)  NULL ,
	dt_inps_dal            DATE  NULL ,
	dt_inps_al             DATE  NULL ,
	dt_inserimento        DATE  NOT NULL ,
	dt_aggiornamento      DATE  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	id_progetto           NUMBER(8)  NULL ,
	id_stato_atto         NUMBER(3)  NOT NULL ,
	id_beneficiario_bilancio  NUMBER(8)  NOT NULL ,
	id_benefic_bilancio_cedente  NUMBER(8)  NULL ,
	id_benefic_bilancio_ceduto  NUMBER(8)  NULL ,
	id_dati_pagamento_atto  NUMBER(8)  NOT NULL ,
	id_dati_pagam_atto_ben_ceduto  NUMBER(8)  NULL ,
	id_situazione_inps    NUMBER(3)  NULL ,
	id_attivita_inps      NUMBER(3)  NULL ,
	id_rischio_inail      NUMBER(3)  NULL ,
	id_altra_cassa_previdenz  NUMBER(3)  NULL ,
	id_tipo_altra_cassa_prev  NUMBER(3)  NULL ,
	id_aliquota_ritenuta  NUMBER(3)  NULL ,
	id_modalita_agevolazione  NUMBER(3)  NOT NULL ,
	id_causale_erogazione  NUMBER(3)  NOT NULL ,
	id_settore_ente       NUMBER(3)  NOT NULL ,
	numero_telefono_liquidatore  VARCHAR2(10)  NULL ,
	nome_liquidatore      VARCHAR2(50)  NULL ,
	nome_dirigente_liquidatore  VARCHAR2(20)  NULL
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_atto_liquidazione ON pbandi_t_atto_liquidazione
(id_atto_liquidazione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_atto_liquidazione
	ADD CONSTRAINT  PK_pbandi_t_atto_liquidazione PRIMARY KEY (id_atto_liquidazione);



CREATE TABLE pbandi_t_beneficiario_bilancio
(
	id_beneficiario_bilancio  NUMBER(8)  NOT NULL ,
	codice_beneficiario_bilancio  NUMBER(6)  NULL ,
	quietanzante             VARCHAR2(80)  NULL ,
	cod_fisc_quietanzante  VARCHAR2(16)  NULL ,
	dt_inserimento        DATE  NOT NULL ,
	dt_aggiornamento      DATE  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	id_soggetto           NUMBER(8)  NULL ,
	id_ente_giuridico     NUMBER(8)  NULL ,
	id_persona_fisica     NUMBER(8)  NULL ,
	id_recapiti           NUMBER(8)  NULL ,
	id_sede               NUMBER(8)  NULL ,
	id_indirizzo          NUMBER(9)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_beneficiario_bilan ON pbandi_t_beneficiario_bilancio
(id_beneficiario_bilancio  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_beneficiario_bilancio
	ADD CONSTRAINT  PK_pbandi_t_beneficiario_bilan PRIMARY KEY (id_beneficiario_bilancio);



CREATE TABLE pbandi_t_capitolo
(
	id_capitolo           NUMBER(8)  NOT NULL ,
	numero_capitolo       NUMBER(6)  NOT NULL ,
	numero_articolo       NUMBER(3)  NOT NULL ,
	desc_capitolo         VARCHAR2(600)  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	id_tipo_fondo         NUMBER(3)  NOT NULL ,
	id_provenienza_capitolo  NUMBER(3)  NULL ,
	id_ente_competenza    NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_capitolo ON pbandi_t_capitolo
(id_capitolo  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_capitolo
	ADD CONSTRAINT  PK_pbandi_t_capitolo PRIMARY KEY (id_capitolo);



CREATE TABLE pbandi_t_dati_pagamento_atto
(
	id_dati_pagamento_atto  NUMBER(8)  NOT NULL ,
	cod_mod_pag_bilancio  NUMBER(3) NULL ,
	dt_inserimento        DATE  NOT NULL ,
	dt_aggiornamento      DATE  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	id_sede               NUMBER(8)  NULL ,
	id_indirizzo          NUMBER(9)  NULL ,
	id_estremi_bancari    NUMBER(8)  NULL ,
	id_modalita_erogazione  NUMBER(3)  NULL ,
	id_recapiti           NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_dati_pagamento_att ON pbandi_t_dati_pagamento_atto
(id_dati_pagamento_atto  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_dati_pagamento_atto
	ADD CONSTRAINT  PK_pbandi_t_dati_pagamento_att PRIMARY KEY (id_dati_pagamento_atto);



CREATE TABLE pbandi_t_impegno
(
	id_impegno            NUMBER(8)  NOT NULL ,
	numero_impegno        NUMBER(6)  NOT NULL ,
	anno_impegno          NUMBER(4)  NOT NULL ,
	anno_esercizio        NUMBER(4)  NOT NULL ,
	desc_impegno          VARCHAR2(450)  NULL ,
	importo_iniziale_impegno  NUMBER(15,2)  NULL ,
	importo_attuale_impegno  NUMBER(15,2)  NULL ,
	totale_liquidato_impegno  NUMBER(15,2)  NULL ,
	totale_quietanzato_impegno  NUMBER(15,2)  NULL ,
	disponibilita_liquidare  NUMBER(15,2)  NULL ,
	cup_impegno           VARCHAR2(15)  NULL ,
	cig_impegno           VARCHAR2(10)  NULL ,
	dt_inserimento_bilancio  DATE  NOT NULL ,
	dt_aggiornamento_bilancio  DATE  NULL ,
	anno_perente          NUMBER(4)  NULL ,
	numero_perente        NUMBER(6)  NULL ,
	numero_capitolo_origine  NUMBER(6)  NULL ,
	tipologia_beneficiario  VARCHAR2(6)  NULL ,
	desc_tipologia_beneficiario  VARCHAR2(150)  NULL ,
	dt_inserimento        DATE  NOT NULL ,
	dt_aggiornamento      DATE  NULL ,
	id_beneficiario_bilancio  NUMBER(8)  NULL ,
	id_stato_impegno      NUMBER(3)  NOT NULL ,
	id_capitolo           NUMBER(8)  NOT NULL ,
	id_ente_competenza_delegato  NUMBER(8)  NULL ,
	id_provvedimento      NUMBER(8)  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_impegno ON pbandi_t_impegno
(id_impegno  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_impegno
	ADD CONSTRAINT  PK_pbandi_t_impegno PRIMARY KEY (id_impegno);

ALTER TABLE pbandi_t_impegno
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_235 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_impegno
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_236 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));
	

CREATE TABLE pbandi_t_mandato_quietanzato
(
	id_mandato_quietanzato  NUMBER(8)  NOT NULL ,
	numero_mandato        NUMBER(6)  NULL ,
	importo_mandato_lordo  NUMBER(15,2)  NULL ,
	importo_ritenute      NUMBER(15,2)  NULL ,
	importo_mandato_netto  NUMBER(15,2)  NULL ,
	cup_mandato           VARCHAR2(15)  NULL ,
	cig_mandato           VARCHAR2(10)  NULL ,
	flag_pignoramento     CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_61 CHECK (flag_pignoramento in ('S','N')),
	importo_quietanzato   NUMBER(15,2)  NULL ,
	dt_quietanza          DATE  NULL ,
	dt_inserimento        DATE  NOT NULL ,
	dt_aggiornamento      DATE  NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	progr_liquidazione    NUMBER(8)  NOT NULL 
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_mandato_quietanzat ON pbandi_t_mandato_quietanzato
(id_mandato_quietanzato  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_mandato_quietanzato
	ADD CONSTRAINT  PK_pbandi_t_mandato_quietanzat PRIMARY KEY (id_mandato_quietanzato);



CREATE TABLE pbandi_t_provvedimento
(
	id_provvedimento      NUMBER(8)  NOT NULL ,
	numero_provvedimento  VARCHAR2(5)  NOT NULL ,
	anno_provvedimento    NUMBER(4)  NOT NULL ,
	dt_provvedimento      DATE  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	id_tipo_provvedimento  NUMBER(3)  NOT NULL ,
	id_ente_competenza    NUMBER(8)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_provvedimento ON pbandi_t_provvedimento
(id_provvedimento  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_provvedimento
	ADD CONSTRAINT  PK_pbandi_t_provvedimento PRIMARY KEY (id_provvedimento);



ALTER TABLE pbandi_d_aliquota_ritenuta
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_RITENUTA_01 FOREIGN KEY (id_tipo_ritenuta) REFERENCES pbandi_d_tipo_ritenuta(id_tipo_ritenuta));



ALTER TABLE pbandi_d_aliquota_ritenuta
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_SOGG_RITEN_02 FOREIGN KEY (id_tipo_sogg_ritenuta) REFERENCES pbandi_d_tipo_sogg_ritenuta(id_tipo_sogg_ritenuta));



ALTER TABLE pbandi_d_settore_ente
	ADD (CONSTRAINT  FK_PBANDI_T_ENTE_COMPETENZA_07 FOREIGN KEY (id_ente_competenza) REFERENCES pbandi_t_ente_competenza(id_ente_competenza));



ALTER TABLE pbandi_r_impegno_bando_linea
	ADD (CONSTRAINT  FK_PBANDI_T_IMPEGNO_02 FOREIGN KEY (id_impegno) REFERENCES pbandi_t_impegno(id_impegno));



ALTER TABLE pbandi_r_impegno_bando_linea
	ADD (CONSTRAINT  FK_PBANDI_R_BANDO_LINEA_INT_15 FOREIGN KEY (progr_bando_linea_intervento) REFERENCES pbandi_r_bando_linea_intervent(progr_bando_linea_intervento));



ALTER TABLE pbandi_r_impegno_bando_linea
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_223 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_impegno_bando_linea
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_224 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_impegno_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_IMPEGNO_01 FOREIGN KEY (id_impegno) REFERENCES pbandi_t_impegno(id_impegno));



ALTER TABLE pbandi_r_impegno_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_29 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_r_impegno_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_221 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_impegno_progetto
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_222 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_liquidazione
	ADD (CONSTRAINT  FK_PBANDI_T_ATTO_LIQUIDAZIO_01 FOREIGN KEY (id_atto_liquidazione) REFERENCES pbandi_t_atto_liquidazione(id_atto_liquidazione));



ALTER TABLE pbandi_r_liquidazione
	ADD (CONSTRAINT  FK_PBANDI_T_IMPEGNO_03 FOREIGN KEY (id_impegno) REFERENCES pbandi_t_impegno(id_impegno));



ALTER TABLE pbandi_r_liquidazione
	ADD (CONSTRAINT  FK_PBANDI_R_LIQUIDAZIONE_01 FOREIGN KEY (progr_liquidazione_precedente) REFERENCES pbandi_r_liquidazione(progr_liquidazione));



ALTER TABLE pbandi_r_liquidazione
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_229 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_liquidazione
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_230 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_r_tip_altr_cas_sogg_rit
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_SOGG_RITEN_01 FOREIGN KEY (id_tipo_sogg_ritenuta) REFERENCES pbandi_d_tipo_sogg_ritenuta(id_tipo_sogg_ritenuta));



ALTER TABLE pbandi_r_tip_altr_cas_sogg_rit
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_ALTRA_CASS_02 FOREIGN KEY (id_tipo_altra_cassa_prev) REFERENCES pbandi_d_tipo_altra_cassa_prev(id_tipo_altra_cassa_prev));



ALTER TABLE pbandi_t_atto_liquidazione
	ADD (CONSTRAINT  FK_PBANDI_D_SITUAZIONE_INPS_01 FOREIGN KEY (id_situazione_inps) REFERENCES pbandi_d_situazione_inps(id_situazione_inps));



ALTER TABLE pbandi_t_atto_liquidazione
	ADD (CONSTRAINT  FK_PBANDI_D_ATTIVITA_INPS_01 FOREIGN KEY (id_attivita_inps) REFERENCES pbandi_d_attivita_inps(id_attivita_inps));



ALTER TABLE pbandi_t_atto_liquidazione
	ADD (CONSTRAINT  FK_PBANDI_D_RISCHIO_INAIL_01 FOREIGN KEY (id_rischio_inail) REFERENCES pbandi_d_rischio_inail(id_rischio_inail));



ALTER TABLE pbandi_t_atto_liquidazione
	ADD (CONSTRAINT  FK_PBANDI_D_ALTRA_CASSA_PRE_01 FOREIGN KEY (id_altra_cassa_previdenz) REFERENCES pbandi_d_altra_cassa_previdenz(id_altra_cassa_previdenz));



ALTER TABLE pbandi_t_atto_liquidazione
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_ALTRA_CASS_01 FOREIGN KEY (id_tipo_altra_cassa_prev) REFERENCES pbandi_d_tipo_altra_cassa_prev(id_tipo_altra_cassa_prev));



ALTER TABLE pbandi_t_atto_liquidazione
	ADD (CONSTRAINT  FK_PBANDI_D_ALIQUOTA_RITENU_01 FOREIGN KEY (id_aliquota_ritenuta) REFERENCES pbandi_d_aliquota_ritenuta(id_aliquota_ritenuta));



ALTER TABLE pbandi_t_atto_liquidazione
	ADD (CONSTRAINT  FK_PBANDI_D_MODALITA_AGEVOL_07 FOREIGN KEY (id_modalita_agevolazione) REFERENCES pbandi_d_modalita_agevolazione(id_modalita_agevolazione));



ALTER TABLE pbandi_t_atto_liquidazione
	ADD (CONSTRAINT  FK_PBANDI_D_CAUSALE_EROGAZI_03 FOREIGN KEY (id_causale_erogazione) REFERENCES pbandi_d_causale_erogazione(id_causale_erogazione));



ALTER TABLE pbandi_t_atto_liquidazione
	ADD (CONSTRAINT  FK_PBANDI_D_SETTORE_ENTE_01 FOREIGN KEY (id_settore_ente) REFERENCES pbandi_d_settore_ente(id_settore_ente));



ALTER TABLE pbandi_t_atto_liquidazione
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_215 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_atto_liquidazione
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_216 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_atto_liquidazione
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_28 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_t_atto_liquidazione
	ADD (CONSTRAINT  FK_PBANDI_D_STATO_ATTO_01 FOREIGN KEY (id_stato_atto) REFERENCES pbandi_d_stato_atto(id_stato_atto));



ALTER TABLE pbandi_t_atto_liquidazione
	ADD (CONSTRAINT  FK_PBANDI_T_BENEFI_BILANCIO_01 FOREIGN KEY (id_beneficiario_bilancio) REFERENCES pbandi_t_beneficiario_bilancio(id_beneficiario_bilancio));



ALTER TABLE pbandi_t_atto_liquidazione
	ADD (CONSTRAINT  FK_PBANDI_T_BENEFI_BILANCIO_02 FOREIGN KEY (id_benefic_bilancio_ceduto) REFERENCES pbandi_t_beneficiario_bilancio(id_beneficiario_bilancio));



ALTER TABLE pbandi_t_atto_liquidazione
	ADD (CONSTRAINT  FK_PBANDI_T_BENEFI_BILANCIO_03 FOREIGN KEY (id_benefic_bilancio_cedente) REFERENCES pbandi_t_beneficiario_bilancio(id_beneficiario_bilancio));



ALTER TABLE pbandi_t_atto_liquidazione
	ADD (CONSTRAINT  FK_PBANDI_T_DATI_PAGAM_ATTO_01 FOREIGN KEY (id_dati_pagamento_atto) REFERENCES pbandi_t_dati_pagamento_atto(id_dati_pagamento_atto));



ALTER TABLE pbandi_t_atto_liquidazione
	ADD (CONSTRAINT  FK_PBANDI_T_DATI_PAGAM_ATTO_02 FOREIGN KEY (id_dati_pagam_atto_ben_ceduto) REFERENCES pbandi_t_dati_pagamento_atto(id_dati_pagamento_atto));



ALTER TABLE pbandi_t_beneficiario_bilancio
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_217 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_beneficiario_bilancio
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_218 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_beneficiario_bilancio
	ADD (CONSTRAINT  FK_PBANDI_T_SOGGETTO_09 FOREIGN KEY (id_soggetto) REFERENCES pbandi_t_soggetto(id_soggetto));



ALTER TABLE pbandi_t_beneficiario_bilancio
	ADD (CONSTRAINT  FK_PBANDI_T_ENTE_GIURIDICO_06 FOREIGN KEY (id_ente_giuridico) REFERENCES pbandi_t_ente_giuridico(id_ente_giuridico));



ALTER TABLE pbandi_t_beneficiario_bilancio
	ADD (CONSTRAINT  FK_PBANDI_T_PERSONA_FISICA_07 FOREIGN KEY (id_persona_fisica) REFERENCES pbandi_t_persona_fisica(id_persona_fisica));



ALTER TABLE pbandi_t_beneficiario_bilancio
	ADD (CONSTRAINT  FK_PBANDI_T_RECAPITI_07 FOREIGN KEY (id_recapiti) REFERENCES pbandi_t_recapiti(id_recapiti));



ALTER TABLE pbandi_t_beneficiario_bilancio
	ADD (CONSTRAINT  FK_PBANDI_T_SEDE_06 FOREIGN KEY (id_sede) REFERENCES pbandi_t_sede(id_sede));



ALTER TABLE pbandi_t_beneficiario_bilancio
	ADD (CONSTRAINT  FK_PBANDI_T_INDIRIZZO_13 FOREIGN KEY (id_indirizzo) REFERENCES pbandi_t_indirizzo(id_indirizzo));



ALTER TABLE pbandi_t_capitolo
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_225 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_capitolo
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_226 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_capitolo
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_FONDO_01 FOREIGN KEY (id_tipo_fondo) REFERENCES pbandi_d_tipo_fondo(id_tipo_fondo));



ALTER TABLE pbandi_t_capitolo
	ADD (CONSTRAINT  FK_PBANDI_D_PROVEN_CAPITOLO_01 FOREIGN KEY (id_provenienza_capitolo) REFERENCES pbandi_d_provenienza_capitolo(id_provenienza_capitolo));



ALTER TABLE pbandi_t_capitolo
	ADD (CONSTRAINT  FK_PBANDI_T_ENTE_COMPETENZA_08 FOREIGN KEY (id_ente_competenza) REFERENCES pbandi_t_ente_competenza(id_ente_competenza));



ALTER TABLE pbandi_t_dati_pagamento_atto
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_219 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_dati_pagamento_atto
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_220 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_dati_pagamento_atto
	ADD (CONSTRAINT  FK_PBANDI_T_SEDE_07 FOREIGN KEY (id_sede) REFERENCES pbandi_t_sede(id_sede));



ALTER TABLE pbandi_t_dati_pagamento_atto
	ADD (CONSTRAINT  FK_PBANDI_T_INDIRIZZO_14 FOREIGN KEY (id_indirizzo) REFERENCES pbandi_t_indirizzo(id_indirizzo));



ALTER TABLE pbandi_t_dati_pagamento_atto
	ADD (CONSTRAINT  FK_PBANDI_T_ESTREMI_BANCARI_04 FOREIGN KEY (id_estremi_bancari) REFERENCES pbandi_t_estremi_bancari(id_estremi_bancari));



ALTER TABLE pbandi_t_dati_pagamento_atto
	ADD (CONSTRAINT  FK_PBANDI_D_MODALITA_EROGAZ_02 FOREIGN KEY (id_modalita_erogazione) REFERENCES pbandi_d_modalita_erogazione(id_modalita_erogazione));


ALTER TABLE pbandi_t_dati_pagamento_atto
	ADD (CONSTRAINT  FK_PBANDI_T_RECAPITI_08 FOREIGN KEY (id_recapiti) REFERENCES pbandi_t_recapiti(id_recapiti));
	

ALTER TABLE pbandi_t_impegno
	ADD (CONSTRAINT  FK_PBANDI_T_BENEFI_BILANCIO_04 FOREIGN KEY (id_beneficiario_bilancio) REFERENCES pbandi_t_beneficiario_bilancio(id_beneficiario_bilancio));



ALTER TABLE pbandi_t_impegno
	ADD (CONSTRAINT  FK_PBANDI_D_STATO_IMPEGNO_01 FOREIGN KEY (id_stato_impegno) REFERENCES pbandi_d_stato_impegno(id_stato_impegno));



ALTER TABLE pbandi_t_impegno
	ADD (CONSTRAINT  FK_PBANDI_T_CAPITOLO_01 FOREIGN KEY (id_capitolo) REFERENCES pbandi_t_capitolo(id_capitolo));



ALTER TABLE pbandi_t_impegno
	ADD (CONSTRAINT  FK_PBANDI_T_ENTE_COMPETENZA_10 FOREIGN KEY (id_ente_competenza_delegato) REFERENCES pbandi_t_ente_competenza(id_ente_competenza));



ALTER TABLE pbandi_t_impegno
	ADD (CONSTRAINT  FK_PBANDI_T_PROVVEDIMENTO_01 FOREIGN KEY (id_provvedimento) REFERENCES pbandi_t_provvedimento(id_provvedimento));



ALTER TABLE pbandi_t_mandato_quietanzato
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_231 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_mandato_quietanzato
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_232 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_mandato_quietanzato
	ADD (CONSTRAINT  FK_PBANDI_R_LIQUIDAZIONE_02 FOREIGN KEY (progr_liquidazione) REFERENCES pbandi_r_liquidazione(progr_liquidazione));



ALTER TABLE pbandi_t_provvedimento
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_227 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_provvedimento
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_228 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_provvedimento
	ADD (CONSTRAINT  FK_PBANDI_D_TIPO_PROVVEDIME_01 FOREIGN KEY (id_tipo_provvedimento) REFERENCES pbandi_d_tipo_provvedimento(id_tipo_provvedimento));



ALTER TABLE pbandi_t_provvedimento
	ADD (CONSTRAINT  FK_PBANDI_T_ENTE_COMPETENZA_09 FOREIGN KEY (id_ente_competenza) REFERENCES pbandi_t_ente_competenza(id_ente_competenza));

/*
*** fine bilancio
*/

alter TABLE pbandi_d_soggetto_finanziatore
add flag_agevolato        CHAR(1)   DEFAULT  'N' NOT NULL  CONSTRAINT  ck_flag_62 CHECK (flag_agevolato in ('S','N'));

alter TABLE pbandi_r_prog_sogg_finanziat
add imp_quota_sogg_finanziatore  NUMBER(13,2);

alter TABLE pbandi_t_conto_economico	
add importo_impegno_vincolante  NUMBER(13,2);

alter table pbandi_r_det_prop_cer_sogg_fin
add imp_quota_sogg_finanziatore  NUMBER(13,2);

alter TABLE pbandi_w_prog_sogg_finanziat
add imp_quota_sogg_finanziatore  NUMBER(13,2);

alter table pbandi_t_recupero
modify id_modalita_agevolazione null;

alter table pbandi_t_recupero
add interessi_recupero NUMBER(13,2);

alter table pbandi_t_conto_economico
add importo_impegno_contabile NUMBER(13,2);

alter table PBANDI_R_DETT_PROP_CERTIF_RECU
add interessi_recupero NUMBER(13,2);

CREATE TABLE pbandi_d_macro_sezione_modulo
(
	id_macro_sezione_modulo  NUMBER(3)  NOT NULL ,
	desc_breve_macro_sezione  VARCHAR2(20)  NOT NULL ,
	desc_macro_sezione    VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_macro_sezione_modu ON pbandi_d_macro_sezione_modulo
(id_macro_sezione_modulo  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_macro_sezione_modulo
	ADD CONSTRAINT  PK_pbandi_d_macro_sezione_modu PRIMARY KEY (id_macro_sezione_modulo);



CREATE TABLE pbandi_d_micro_sezione_modulo
(
	id_micro_sezione_modulo  NUMBER(3)  NOT NULL ,
	desc_micro_sezione    VARCHAR2(50)  NOT NULL ,
	contenuto_micro_sezione  VARCHAR2(4000)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_d_micro_sezione_modu ON pbandi_d_micro_sezione_modulo
(id_micro_sezione_modulo  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_d_micro_sezione_modulo
	ADD CONSTRAINT  PK_pbandi_d_micro_sezione_modu PRIMARY KEY (id_micro_sezione_modulo);


CREATE TABLE pbandi_r_bl_tipo_doc_sez_mod
(
	id_micro_sezione_modulo  NUMBER(3)  NOT NULL ,
	id_macro_sezione_modulo  NUMBER(3)  NULL ,
	id_tipo_documento_index  NUMBER(3)  NOT NULL ,
	progr_bando_linea_intervento  NUMBER(8)  NOT NULL ,
	num_ordinamento_micro_sezione  NUMBER(3)  NULL ,
	num_ordinamento_macro_sezione  NUMBER(3)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_r_bl_tipo_doc_sez_mo ON pbandi_r_bl_tipo_doc_sez_mod
(id_tipo_documento_index  ASC,progr_bando_linea_intervento  ASC,id_micro_sezione_modulo  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_r_bl_tipo_doc_sez_mod
	ADD CONSTRAINT  PK_pbandi_r_bl_tipo_doc_sez_mo PRIMARY KEY (id_tipo_documento_index,progr_bando_linea_intervento,id_micro_sezione_modulo);



ALTER TABLE pbandi_r_bl_tipo_doc_sez_mod
	ADD (CONSTRAINT  FK_PBANDI_D_MICRO_SEZI_MOD_01 FOREIGN KEY (id_micro_sezione_modulo) REFERENCES pbandi_d_micro_sezione_modulo(id_micro_sezione_modulo));



ALTER TABLE pbandi_r_bl_tipo_doc_sez_mod
	ADD (CONSTRAINT  FK_PBANDI_D_MACRO_SEZ_MODUL_01 FOREIGN KEY (id_macro_sezione_modulo) REFERENCES pbandi_d_macro_sezione_modulo(id_macro_sezione_modulo));



ALTER TABLE pbandi_r_bl_tipo_doc_sez_mod
	ADD (CONSTRAINT  FK_PBANDI_R_TP_DOC_IND_BAN_01 FOREIGN KEY (id_tipo_documento_index,progr_bando_linea_intervento) REFERENCES pbandi_r_tp_doc_ind_ban_li_int(id_tipo_documento_index,progr_bando_linea_intervento));



alter table pbandi_r_domanda_indicatori
modify VALORE_CONCLUSO number (16,5);

alter table pbandi_r_domanda_indicatori
modify VALORE_PROG_AGG number (16,5);

alter table pbandi_r_domanda_indicatori
modify VALORE_PROG_INIZIALE number (16,5);

create sequence seq_pbandi_t_storico_migrazion start with 1 nocache;

CREATE TABLE pbandi_t_storico_migrazione
(
	id_storico_migrazione  NUMBER(8)  NOT NULL ,
	id_progetto           NUMBER(8)  NOT NULL ,
	id_istanza_processo   VARCHAR2(255)  NOT NULL ,
	id_nuova_istanza      VARCHAR2(255)  NULL ,
	uuid_processo         VARCHAR2(255)  NOT NULL ,
	dt_inizio_validita    DATE  NOT NULL ,
	dt_fine_validita      DATE  NULL ,
	id_utente_ins         NUMBER(8)  NOT NULL ,
	id_utente_agg         NUMBER(8)  NULL ,
	dt_inizio_storicizzazione  DATE  NULL ,
	dt_fine_storicizzazione  DATE  NULL 
)
	TABLESPACE PBANDI_MEDIUM_TBL;



CREATE UNIQUE INDEX PK_pbandi_t_storico_migrazione ON pbandi_t_storico_migrazione
(id_storico_migrazione  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_t_storico_migrazione
	ADD CONSTRAINT  PK_pbandi_t_storico_migrazione PRIMARY KEY (id_storico_migrazione);



ALTER TABLE pbandi_t_storico_migrazione
	ADD (CONSTRAINT  FK_PBANDI_T_PROGETTO_30 FOREIGN KEY (id_progetto) REFERENCES pbandi_t_progetto(id_progetto));



ALTER TABLE pbandi_t_storico_migrazione
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_233 FOREIGN KEY (id_utente_ins) REFERENCES pbandi_t_utente(id_utente));



ALTER TABLE pbandi_t_storico_migrazione
	ADD (CONSTRAINT  FK_PBANDI_T_UTENTE_234 FOREIGN KEY (id_utente_agg) REFERENCES pbandi_t_utente(id_utente));

alter TABLE pbandi_t_richiesta_erogazione	
add dt_inizio_lavori      DATE  NULL ;

alter TABLE pbandi_t_richiesta_erogazione	
add dt_stipulazione_contratti  DATE  NULL ;

alter TABLE pbandi_t_richiesta_erogazione	
add direttore_lavori      VARCHAR2(255)  NULL ;

alter TABLE pbandi_t_richiesta_erogazione	
add residenza_direttore_lavori  VARCHAR2(1000);

alter TABLE pbandi_t_progetto
add soglia_spesa_calc_erogazioni  NUMBER(13,2);

alter TABLE pbandi_t_richiesta_erogazione
add dt_raggiungimento_30p  DATE  NULL ;
	
alter TABLE pbandi_t_richiesta_erogazione
add dt_raggiungimento_90p  DATE  NULL ;
	
alter TABLE pbandi_t_richiesta_erogazione
add dt_raggiungimento_100p  DATE  NULL ;

CREATE TABLE pbandi_c_batch_impegni
(
	anno_esercizio        NUMBER(4)  NOT NULL ,
	direzione             VARCHAR2(4)  NOT NULL ,
	numero_capitolo       NUMBER(6)  NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE TABLE pbandi_w_impegni
(
	AnnoEsercizio         NUMBER(4)  NULL ,
	AnnoImp               VARCHAR2(4)  NULL ,
	NroImp                NUMBER(6)  NULL ,
	DescImpegno           VARCHAR2(420)  NULL ,
	Stato                 VARCHAR2(1)  NULL ,
	AnnoProvv             VARCHAR2(4)  NULL ,
	NroProv               VARCHAR2(5)  NULL ,
	TipoProv              VARCHAR2(2)  NULL ,
	DataProv              DATE  NULL ,
	Direzione             VARCHAR2(4)  NULL ,
	DirezioneDel          VARCHAR2(4)  NULL ,
	NroCapitolo           NUMBER(6)  NULL ,
	DescrCapitolo         VARCHAR2(600)  NULL ,
	NroArticolo           NUMBER(3)  NULL ,
	NroCapitoloOrig       NUMBER(6)  NULL ,
	TipoFondo             VARCHAR2(2)  NULL ,
	ProvCap               VARCHAR2(2)  NULL ,
	ImportoIniziale       NUMBER(15,2)  NULL ,
	ImportoAttuale        NUMBER(15,2)  NULL ,
	TotaleLiq             NUMBER(15,2)  NULL ,
	DispLiq               NUMBER(15,2)  NULL ,
	TotalePagato          NUMBER(15,2)  NULL ,
	ConteggioMandati      NUMBER(6)  NULL ,
	CUP                   VARCHAR2(15)  NULL ,
	CIG                   VARCHAR2(10)  NULL ,
	DataIns               DATE  NULL ,
	DataAgg               DATE  NULL ,
	FlagNoProv            VARCHAR2(1)  NULL ,
	AnnoPerente           VARCHAR2(4)  NULL ,
	NroPerente            NUMBER(6)  NULL ,
	Codben                NUMBER(6)  NULL ,
	RagSoc                Varchar2(140)  NULL ,
	TipoForn              VARCHAR2(6)  NULL ,
	DescTipoForn          Varchar2(150)  NULL ,
	TrasfTipo             VARCHAR2(6)  NULL ,
	TrasfVoce             VARCHAR2(15)  NULL ,
	DirezioneProvenienzaCapitolo   VARCHAR2(4 BYTE)
)
	TABLESPACE pbandi_small_tbl;
	
CREATE UNIQUE INDEX AK1_pbandi_t_capitolo ON pbandi_t_capitolo
(numero_capitolo  ASC,numero_articolo  ASC)
	TABLESPACE PBANDI_SMALL_IDX;

ALTER TABLE pbandi_t_capitolo
ADD CONSTRAINT  AK1_pbandi_t_capitolo UNIQUE (numero_capitolo,numero_articolo);
	
CREATE UNIQUE INDEX AK1_pbandi_t_provvedimento ON pbandi_t_provvedimento
(anno_provvedimento  ASC,numero_provvedimento  ASC,id_tipo_provvedimento  ASC)
	TABLESPACE PBANDI_SMALL_IDX;

ALTER TABLE pbandi_t_provvedimento
ADD CONSTRAINT  AK1_pbandi_t_provvedimento UNIQUE (anno_provvedimento,numero_provvedimento,id_tipo_provvedimento);
	
	
CREATE UNIQUE INDEX AK1_pbandi_t_impegno ON pbandi_t_impegno
(anno_esercizio  ASC,anno_impegno  ASC,numero_impegno  ASC)
	TABLESPACE PBANDI_SMALL_IDX;

ALTER TABLE pbandi_t_impegno
ADD CONSTRAINT  AK1_pbandi_t_impegno UNIQUE (anno_esercizio,anno_impegno,numero_impegno);	

	
	
