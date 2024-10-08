/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

-- mc gestione widget
CREATE OR REPLACE TYPE "OBJTASKPROGWIDBEN" AS OBJECT
(ID_PROGETTO NUMBER(8),
 TITOLO_PROGETTO  VARCHAR2(2000),
 CODICE_VISUALIZZATO  VARCHAR2(100),
 DESCR_BREVE_TASK VARCHAR2(100),
 DESCR_TASK  VARCHAR2(2000),
 PROGR_BANDO_LINEA_INTERVENTO  NUMBER(8),
 NOME_BANDO_LINEA VARCHAR2(255),
 FLAG_OPT VARCHAR2(1),
 FLAG_LOCK VARCHAR2(1),
 ACRONIMO_PROGETTO VARCHAR2(2000),
 ID_BUSINESS NUMBER,
 ID_NOTIFICA NUMBER,
 DENOMINAZIONE_LOCK  VARCHAR2(1000),
 FLAG_RICH_ABILITAZ_UTENTE VARCHAR2(1),
 DESC_BREVE_WIDGET  VARCHAR2(100),
 ID_SOGGETTO_BENEFICIARIO NUMBER(8),
 DENOM_BENEFIARIO varchar2(1000),
 DETT_BUSINESS  varchar2(100)
 )
/

CREATE OR REPLACE TYPE "LISTTASKPROGWIDBEN" AS TABLE OF OBJTASKPROGWIDBEN
/

--

alter table PBANDI_R_TIPO_FILE_ALLEG_ENT drop column ID_DOCUMENTO_INDEX;
alter table PBANDI_C_TIPO_FILE_ALLEGATO drop column ID_TIPO_DOCUMENTO_INDEX;


--
--
drop table PBANDI_R_BANDO_LIN_SOGG_WIDGET;
drop sequence SEQ_PBANDI_R_BAN_LIN_SOGG_WID;
--PBANDI_R_BANDO_LIN_SOGG_WIDGET
CREATE TABLE PBANDI_R_PROG_SOGG_WIDGET
(id_prog_sogg_widget NUMBER(8) NOT NULL,
 id_soggetto_widget NUMBER(8) NOT NULL ,
 id_progetto        NUMBER(8) NOT NULL,
 id_utente_ins      NUMBER(8) NOT NULL,
 dt_inserimento      DATE ,
CONSTRAINT PK_PBANDI_R_PROG_SOGG_WIDGET PRIMARY KEY (id_prog_sogg_widget) USING INDEX TABLESPACE PBANDI_IDX 
);
-- FK
ALTER TABLE PBANDI_R_PROG_SOGG_WIDGET   ADD CONSTRAINT fk_PBANDI_R_SOGGETTO_WIDGET_01
    FOREIGN KEY (id_soggetto_widget) REFERENCES PBANDI_R_SOGGETTO_WIDGET (id_soggetto_widget);
ALTER TABLE PBANDI_R_PROG_SOGG_WIDGET   ADD CONSTRAINT fk_pbandi_t_utente_462
    FOREIGN KEY (ID_UTENTE_INS) REFERENCES PBANDI_T_UTENTE (ID_UTENTE);    
ALTER TABLE PBANDI_R_PROG_SOGG_WIDGET   ADD CONSTRAINT fk_pbandi_t_progetto_105
    FOREIGN KEY (ID_PROGETTO) REFERENCES PBANDI_T_PROGETTO(ID_progetto);    
-- IDX
CREATE INDEX IDX_PBANDI_R_PROG_SOGG_WID_01 ON PBANDI_R_PROG_SOGG_WIDGET (id_soggetto_widget)  TABLESPACE PBANDI_IDX;
CREATE INDEX IDX_PBANDI_R_PROG_SOGG_WID_02 ON PBANDI_R_PROG_SOGG_WIDGET (ID_UTENTE_INS)  TABLESPACE PBANDI_IDX;
CREATE INDEX IDX_PBANDI_R_PROG_SOGG_WID_03 ON PBANDI_R_PROG_SOGG_WIDGET (id_progetto)  TABLESPACE PBANDI_IDX;
CREATE UNIQUE INDEX AK_PBANDI_R_PROG_SOGG_WID_01 ON PBANDI_R_PROG_SOGG_WIDGET (id_soggetto_widget, id_progetto) TABLESPACE PBANDI_IDX;
-- SEQ_PBANDI_R_SOGGETTO_WIDGET
CREATE SEQUENCE SEQ_PBANDI_R_PROG_SOGG_WID
    START WITH 1
    INCREMENT BY 1
    NOMINVALUE
    NOMAXVALUE
    NOCACHE
    nocycle
    noorder; 






-- PBANDI_D_LINEA_DI_INTERVENTO
ALTER TABLE PBANDI_D_LINEA_DI_INTERVENTO ADD FLAG_FAS_SOGG_FINANZ          VARCHAR2(1);
ALTER TABLE PBANDI_D_LINEA_DI_INTERVENTO ADD CONSTRAINT CHK_FLAG_FAS_SOGG_FINANZ CHECK (FLAG_FAS_SOGG_FINANZ='S');
COMMENT ON COLUMN PBANDI_D_LINEA_DI_INTERVENTO.FLAG_FAS_SOGG_FINANZ IS 'Se=S viene eseguita la procedura  InitProgSoggFinanzGefoFAS';

--PBANDI_T_CONTROLLO_LOCO 
ALTER TABLE PBANDI_T_CONTROLLO_LOCO ADD CTRL_IN_ITINERE   varchar2(1);
ALTER TABLE PBANDI_T_CONTROLLO_LOCO ADD CTRL_EX_POST      varchar2(1);
ALTER TABLE PBANDI_T_CONTROLLO_LOCO ADD CONSTRAINT CHK_CTRL_IN_ITINERE CHECK (CTRL_IN_ITINERE='1');
ALTER TABLE PBANDI_T_CONTROLLO_LOCO ADD CONSTRAINT CHK_CTRL_EX_POST CHECK (CTRL_EX_POST='1');

--PBANDI_T_CONTROLLO_LOCO_ALTRI 
ALTER TABLE PBANDI_T_CONTROLLO_LOCO_ALTRI ADD CTRL_IN_ITINERE   varchar2(1);
ALTER TABLE PBANDI_T_CONTROLLO_LOCO_ALTRI ADD CTRL_EX_POST      varchar2(1);
ALTER TABLE PBANDI_T_CONTROLLO_LOCO_ALTRI ADD CONSTRAINT CHK_ALTRI_CTRL_IN_ITINERE CHECK (CTRL_IN_ITINERE='1');
ALTER TABLE PBANDI_T_CONTROLLO_LOCO_ALTRI ADD CONSTRAINT CHK_ALTRI_CTRL_EX_POST CHECK (CTRL_EX_POST='1');

-- Eliminata perch� la tabella corrispondente era stata gia eliminata
drop sequence SEQ_PBANDI_T_TEMPLATE_ITER;
