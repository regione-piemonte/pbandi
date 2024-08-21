/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

-- SEQ
CREATE SEQUENCE SEQ_PBANDI_W_CARICAMENTO_FILE
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;

CREATE TABLE PBANDI_W_CARICAMENTO_FILE
(ID_CARICAMENTO_FILE NUMBER,
 ID_CARICAMENTO_XML NUMBER,
 FILEB BLOB,
 NOME_FILE VARCHAR2(500),
 NUMERO_DOMANDA  VARCHAR2(20),
 FILE_JSON  CLOB,
 DT_INSERIMENTO DATE,
 FLAG_CARICATO VARCHAR2(2),
 CONSTRAINT PK_PBANDI_W_CARICAMENTO_FILE  PRIMARY KEY (ID_CARICAMENTO_FILE) USING INDEX TABLESPACE PBANDI_IDX
)
LOB (FILEB) STORE AS
      ( TABLESPACE  PBANDI_LOB 
        ENABLE      STORAGE IN ROW
        CHUNK       8192
        RETENTION
        NOCACHE
        INDEX       (
          TABLESPACE PBANDI_IDX))
LOB (FILE_JSON) STORE AS
      ( TABLESPACE  PBANDI_LOB 
        ENABLE      STORAGE IN ROW
        CHUNK       8192
        RETENTION
        NOCACHE
        INDEX       (
          TABLESPACE PBANDI_IDX));


COMMENT ON TABLE PBANDI_W_CARICAMENTO_FILE IS 'Tabella di caricamento file relativo a documenti da associare al caricamento di un progetto';


ALTER TABLE PBANDI_T_FORNITORE ADD (DT_INIZIO_CONTRATTO DATE, DT_FINE_CONTRATTO DATE);

ALTER TABLE  PBANDI_T_NOTIFICA_PROCESSO ADD FLAG_INVIO_MAIL VARCHAR2(1);
COMMENT ON COLUMN PBANDI_T_NOTIFICA_PROCESSO.FLAG_INVIO_MAIL IS 'S=Notifica inviata';
ALTER TABLE PBANDI_T_NOTIFICA_PROCESSO ADD CONSTRAINT CHK_PBANDI_T_NOTIF_PROCESSO_02
    CHECK (FLAG_INVIO_MAIL in ('S'));


CREATE OR REPLACE VIEW PBANDI_V_SIF_INDICATORI_21_27 AS
      select p_sif.id_progetto,
	         d_sif.id_domanda,
			 bi_sif.id_indicatori,
             sum(di_perc.valore_prog_iniziale) as valore_prog_iniziale_sum,
             sum(di_perc.valore_concluso) as valore_concluso_sum,
             sum(di_perc.valore_prog_agg) as valore_prog_agg_sum
        from pbandi_t_progetto p_sif
             join pbandi_t_domanda d_sif on d_sif.id_domanda = p_sif.id_domanda
             join pbandi_r_bando_linea_intervent bli_sif on bli_sif.progr_bando_linea_intervento  = d_sif.progr_bando_linea_intervento
             join pbandi_r_bando_linea_intervent bli_perc on bli_perc.progr_bando_linea_interv_sif = bli_sif.progr_bando_linea_intervento 
             join pbandi_t_domanda d_perc on d_perc.progr_bando_linea_intervento  = bli_perc.progr_bando_linea_intervento
			 join pbandi_r_bando_indicatori bi_sif on bi_sif.id_bando = bli_sif.id_bando
			 left join pbandi_r_domanda_indicatori di_perc on di_perc.id_domanda = d_perc.id_domanda and di_perc.id_indicatori = bi_sif.id_indicatori
		where  bli_sif.flag_sif = 'S'
          and fn_linea_interv_radice (p_sif.id_progetto) = 'POR-FESR-2021-2027'
		  and di_perc.flag_non_applicabile != 'S'
        group by p_sif.id_progetto,d_sif.id_domanda,bi_sif.id_indicatori;
		
-- pbandi_t_dati_progetto_monit
alter table pbandi_t_dati_progetto_monit add FLAG_PPP varchar2(2) CHECK (FLAG_PPP IN ('SI','NO',NULL));
alter table pbandi_t_dati_progetto_monit add FLAG_STRATEGICO varchar2(2)CHECK (FLAG_STRATEGICO IN ('SI',NULL));	
-- PBANDI_T_PROGETTO
alter table PBANDI_T_PROGETTO add DT_FIRMA_ACCORDO date;
alter table PBANDI_T_PROGETTO add DT_COMPLETAMENTO_VALUTAZIONE date;
-- PBANDI_T_CSP_PROGETTO
alter table PBANDI_T_CSP_PROGETTO add
(FLAG_PPP varchar2(2) CHECK (FLAG_PPP IN ('SI','NO',NULL)),
 FLAG_STRATEGICO varchar2(2) CHECK (FLAG_STRATEGICO IN ('SI',NULL)),
 DT_FIRMA_ACCORDO date,
 DT_COMPLETAMENTO_VALUTAZIONE date
);
-- PBANDI_T_CSP_SOGGETTO
alter table PBANDI_T_CSP_SOGGETTO add PEC_SEDE_LEGALE varchar2(100);

-- PBANDI_D_LINEA_DI_INTERVENTO
ALTER TABLE PBANDI_D_LINEA_DI_INTERVENTO ADD SOGLIA_IMPORTO_MASSIMO NUMBER(17,2);

-- PBANDI_T_APPALTI
ALTER TABLE PBANDI_T_APPALTO ADD FLAG_SUBCONTRAENTI VARCHAR2(2) DEFAULT 'NO';

alter table PBANDI_T_APPALTO
  add constraint CHK_FLAG_SUBCONTRAENTI
  check (FLAG_SUBCONTRAENTI IN ('SI', 'NO'));
  
-- PBANDI_R_SUBCONTRATTO_AFFIDAMENTO
CREATE TABLE PBANDI_R_SUBCONTRATTO_AFFID
(
ID_FORNITORE NUMBER(8) NOT NULL,
ID_APPALTO NUMBER(8) NOT NULL,
DT_SUBCONTRATTO DATE NOT NULL,
NOME_SUBCONTRATTO VARCHAR2(500) NOT NULL,
RIFERIMENTO_SUBCONTRATTO VARCHAR2(100) NOT NULL,
IMPORTO_SUBCONTRATTO NUMBER(17,2) NOT NULL,
DT_INVIO_VERIFICA_AFFIDAMENTO DATE,
FLAG_INVIO_VERIFICA_AFFID VARCHAR2(2)
);

alter table PBANDI_R_SUBCONTRATTO_AFFID
  add constraint CHK_FLAG_INVIO_VERIFICA_AFFID
  check (FLAG_INVIO_VERIFICA_AFFID IN ('SI', NULL));

-- PK
ALTER TABLE PBANDI_R_SUBCONTRATTO_AFFID ADD 
(CONSTRAINT PK_PBANDI_R_SUBCONTRATTO_AFFID 
 PRIMARY KEY (ID_FORNITORE,ID_APPALTO)
USING INDEX TABLESPACE PBANDI_IDX);
-- FK
ALTER TABLE PBANDI_R_SUBCONTRATTO_AFFID ADD (
  CONSTRAINT fk_pbandi_t_fornitore_04
  FOREIGN KEY (ID_FORNITORE) 
  REFERENCES pbandi_t_fornitore (ID_FORNITORE));
-- FK
ALTER TABLE PBANDI_R_SUBCONTRATTO_AFFID ADD (
  CONSTRAINT fk_pbandi_t_appalto_08
  FOREIGN KEY (ID_APPALTO) 
  REFERENCES pbandi_t_appalto(ID_APPALTO));

-- IDX
CREATE INDEX IE1_PBANDI_R_SUBCONTRAT_AFFID  ON PBANDI_R_SUBCONTRATTO_AFFID (ID_APPALTO) TABLESPACE PBANDI_IDX;