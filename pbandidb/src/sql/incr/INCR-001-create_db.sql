/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

create sequence seq_pbandi_c_definiz_processo start with 1 nocache;

CREATE TABLE pbandi_c_definizione_processo
(
	id_definizione_processo  NUMBER(8)  NOT NULL ,
	uuid_processo         VARCHAR2(255)  NOT NULL 
)
	TABLESPACE PBANDI_SMALL_TBL;



CREATE UNIQUE INDEX PK_pbandi_c_definizione_proces ON pbandi_c_definizione_processo
(id_definizione_processo  ASC)
	TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE pbandi_c_definizione_processo
	ADD CONSTRAINT  PK_pbandi_c_definizione_proces PRIMARY KEY (id_definizione_processo);

ALTER TABLE pbandi_c_ruolo_processo
ADD id_definizione_processo  NUMBER(8)  NULL ;

ALTER TABLE pbandi_c_ruolo_processo
	ADD (CONSTRAINT  FK_PBANDI_C_DEFINIZ_PROCES_01 FOREIGN KEY (id_definizione_processo) REFERENCES pbandi_c_definizione_processo(id_definizione_processo));
	
ALTER TABLE pbandi_r_bando_linea_intervent	
ADD id_definizione_processo  NUMBER(8)  NULL ;

ALTER TABLE pbandi_r_bando_linea_intervent
	ADD (CONSTRAINT  FK_PBANDI_C_DEFINIZ_PROCES_02 FOREIGN KEY (id_definizione_processo) REFERENCES pbandi_c_definizione_processo(id_definizione_processo));
	
create sequence seq_PBANDI_D_AREA_SCIENTIFICA start with 16 nocache;

drop index AK1_pbandi_r_bando_linea_inter;

create sequence seq_PBANDI_C_REGOLA start with 9 nocache;	
