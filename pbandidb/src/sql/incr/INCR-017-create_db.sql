/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE TABLE PBANDI_D_RUOLO_HELP
(
  ID_RUOLO_HELP    NUMBER(3),
  DESC_RUOLO_HELP  VARCHAR2(30)
)
TABLESPACE PBANDI_SMALL_TBL
;

COMMENT ON TABLE PBANDI_D_RUOLO_HELP IS 'Tabella ruoli per accedere a contenuti di help specializzati';


ALTER TABLE PBANDI_D_RUOLO_HELP ADD (
  CONSTRAINT PK_PBANDI_D_RUOLO_HELP
  PRIMARY KEY
  (ID_RUOLO_HELP)
  USING INDEX TABLESPACE PBANDI_SMALL_IDX)
;

ALTER TABLE PBANDI_D_TIPO_ANAGRAFICA
 ADD (ID_RUOLO_HELP  NUMBER(3))
;


COMMENT ON COLUMN PBANDI_D_TIPO_ANAGRAFICA.ID_RUOLO_HELP IS 'Ruolo per accedere a contenuti di help specializzati';

ALTER TABLE PBANDI_D_TIPO_ANAGRAFICA ADD 
CONSTRAINT FK_BANDI_D_RUOLO_HELP_01
 FOREIGN KEY (ID_RUOLO_HELP)
 REFERENCES PBANDI_D_RUOLO_HELP (ID_RUOLO_HELP)
;

-- Invio dati monitoraggio
ALTER TABLE PBANDI_D_CAUSALE_EROGAZIONE
 ADD (COD_IGRUE  VARCHAR2(1));


COMMENT ON COLUMN PBANDI_D_CAUSALE_EROGAZIONE.COD_IGRUE IS 'Codifica IGRUE per la causale di pagamento';

--PBANDI_R_BANDO_SOGG_FINANZIAT (PER BILANCIO)
ALTER TABLE PBANDI_R_BANDO_SOGG_FINANZIAT ADD PERC_QUOTA_CONTRIBUTO_PUB NUMBER(5,2);

COMMENT ON COLUMN PBANDI_R_BANDO_SOGG_FINANZIAT.PERC_QUOTA_CONTRIBUTO_PUB IS 'Percentuale del solo contributo con flag_agevolato =''S'' (la somma delle % pertando deve dare 100)';

--------------------------------------------------
-- NUOVA GESTIONE DOCUMENTI DI SPESA
--------------------------------------------------
 -- PBANDI_T_DOCUMENTO_DI_SPESA
 -- PBANDI_R_DOC_SPESA_PROGETTO
 
ALTER TABLE PBANDI_T_DOCUMENTO_DI_SPESA
DROP CONSTRAINT FK_PBANDI_D_STATO_DOC_SPESA_02;


ALTER TABLE PBANDI_R_DOC_SPESA_PROGETTO 
ADD ID_STATO_DOCUMENTO_SPESA   NUMBER(3);
 
 ALTER TABLE PBANDI_R_DOC_SPESA_PROGETTO ADD (
  CONSTRAINT FK_PBANDI_D_STATO_DOC_SPESA_02 
  FOREIGN KEY (ID_STATO_DOCUMENTO_SPESA) 
  REFERENCES PBANDI_D_STATO_DOCUMENTO_SPESA (ID_STATO_DOCUMENTO_SPESA));


UPDATE  PBANDI_R_DOC_SPESA_PROGETTO a
SET  ID_STATO_DOCUMENTO_SPESA = (SELECT ID_STATO_DOCUMENTO_SPESA
                                 FROM PBANDI_T_DOCUMENTO_DI_SPESA
								 WHERE ID_DOCUMENTO_DI_SPESA = a.ID_DOCUMENTO_DI_SPESA);
  


ALTER TABLE PBANDI_R_DOC_SPESA_PROGETTO 
MODIFY ID_STATO_DOCUMENTO_SPESA   NOT NULL;


-- PBANDI_R_DOC_SPESA_PROGETTO
 ALTER TABLE PBANDI_R_DOC_SPESA_PROGETTO
 ADD ID_STATO_DOCUMENTO_SPESA_VALID NUMBER(3);
 
COMMENT ON COLUMN PBANDI_R_DOC_SPESA_PROGETTO.ID_STATO_DOCUMENTO_SPESA_VALID IS 'Campo tecnico: Pu� cambiare finch� la Dich. Spesa � aperta; quando si chiude viene copiata in ID_STATO_DOCUMENTO_SPESA';


ALTER TABLE PBANDI_R_DOC_SPESA_PROGETTO ADD (
  CONSTRAINT FK_PBANDI_D_STATO_DOC_SPESA_03
  FOREIGN KEY (ID_STATO_DOCUMENTO_SPESA_VALID) 
  REFERENCES PBANDI_D_STATO_DOCUMENTO_SPESA (ID_STATO_DOCUMENTO_SPESA));

 -- PBANDI_T_DOCUMENTO_DI_SPESA
ALTER TABLE PBANDI_T_DOCUMENTO_DI_SPESA
DROP COLUMN  ID_STATO_DOCUMENTO_SPESA;

 
  
 -- PBANDI_R_PAGAMENTO_DOC_SPESA
 
ALTER TABLE PBANDI_R_PAGAMENTO_DOC_SPESA
RENAME COLUMN ID_PROGETTO TO ID_PROGETTO_BCK;

ALTER TABLE PBANDI_R_PAGAMENTO_DOC_SPESA
MODIFY(ID_PROGETTO_BCK  NULL);

ALTER TABLE PBANDI_R_PAGAMENTO_DOC_SPESA DROP CONSTRAINT FK_PBANDI_T_PROGETTO_09;
--
CREATE TYPE t_number_array AS TABLE OF NUMBER;
/

--PBANDI_T_QUOTA_PARTE_DOC_SPESA
ALTER TABLE PBANDI_T_QUOTA_PARTE_DOC_SPESA 
DROP CONSTRAINT  FK_PBANDI_T_DOC_DI_SPESA_02;

ALTER TABLE PBANDI_T_QUOTA_PARTE_DOC_SPESA 
DROP CONSTRAINT  FK_PBANDI_T_PROGETTO_05;

ALTER TABLE PBANDI_T_QUOTA_PARTE_DOC_SPESA ADD (
  CONSTRAINT FK_PBANDI_R_DOC_SPESA_PROGE_01
  FOREIGN KEY (ID_DOCUMENTO_DI_SPESA,ID_PROGETTO) 
  REFERENCES PBANDI_R_DOC_SPESA_PROGETTO (ID_DOCUMENTO_DI_SPESA,ID_PROGETTO));

CREATE INDEX IE2_PBANDI_T_QUOTA_PARTE_DOC_S ON PBANDI_T_QUOTA_PARTE_DOC_SPESA
(ID_PROGETTO)
TABLESPACE PBANDI_SMALL_IDX;

CREATE INDEX IE3_PBANDI_T_QUOTA_PARTE_DOC_S ON PBANDI_T_QUOTA_PARTE_DOC_SPESA
(ID_RIGO_CONTO_ECONOMICO)
TABLESPACE PBANDI_SMALL_IDX;

 -- PBANDI_T_PAGAMENTO
 
ALTER TABLE PBANDI_T_PAGAMENTO
RENAME COLUMN ID_STATO_VALIDAZIONE_SPESA TO ID_STATO_VALIDAZIONE_SPESA_BCK;

ALTER TABLE PBANDI_T_PAGAMENTO
MODIFY(ID_STATO_VALIDAZIONE_SPESA_BCK  NULL);

ALTER TABLE PBANDI_T_PAGAMENTO DROP CONSTRAINT FK_PBANDI_D_STATO_VAL_SPESA_02;

--PBANDI_R_PAG_QUOT_PARTE_DOC_SP

ALTER TABLE PBANDI_R_PAG_QUOT_PARTE_DOC_SP
ADD ID_DICHIARAZIONE_SPESA NUMBER(8);

ALTER TABLE PBANDI_R_PAG_QUOT_PARTE_DOC_SP ADD (
  CONSTRAINT FK_PBANDI_R_PAG_DICH_SPESA_01
  FOREIGN KEY (ID_PAGAMENTO,ID_DICHIARAZIONE_SPESA) 
  REFERENCES PBANDI_R_PAGAMENTO_DICH_SPESA (ID_PAGAMENTO,ID_DICHIARAZIONE_SPESA));



ALTER TABLE PBANDI_R_PAG_QUOT_PARTE_DOC_SP 
DROP CONSTRAINT  AK1_PBANDI_R_PAG_QUOT_PARTE_DO;

DROP INDEX AK1_PBANDI_R_PAG_QUOT_PARTE_DO;

ALTER TABLE PBANDI_R_PAG_QUOT_PARTE_DOC_SP ADD (
  CONSTRAINT AK1_PBANDI_R_PAG_QUOT_PARTE_DO
  UNIQUE (ID_QUOTA_PARTE_DOC_SPESA,ID_PAGAMENTO,ID_DICHIARAZIONE_SPESA)
  USING INDEX TABLESPACE PBANDI_SMALL_IDX);

  
CREATE INDEX IE2_PBANDI_R_PAG_QUOT_PARTE_DO ON PBANDI_R_PAG_QUOT_PARTE_DOC_SP
(ID_DICHIARAZIONE_SPESA,ID_PAGAMENTO)
 TABLESPACE PBANDI_SMALL_IDX;
 
-- PBANDI_S_DICH_DOC_SPESA
CREATE TABLE PBANDI_S_DICH_DOC_SPESA
(
  ID_DICHIARAZIONE_SPESA  NUMBER(8),
  ID_DOCUMENTO_DI_SPESA   NUMBER(8),
  ID_PAGAMENTO_NT         T_NUMBER_ARRAY
)
NESTED TABLE ID_PAGAMENTO_NT STORE AS TAB_PAGAMENTI_NT (TABLESPACE PBANDI_SMALL_TBL)
TABLESPACE PBANDI_SMALL_TBL
;


CREATE INDEX IE1_PBANDI_S_DICH_DOC_SPESA ON PBANDI_S_DICH_DOC_SPESA
(ID_DOCUMENTO_DI_SPESA)
TABLESPACE PBANDI_SMALL_IDX;



ALTER TABLE PBANDI_S_DICH_DOC_SPESA ADD (
  CONSTRAINT PK_S_DICH_DOC_SPESA
  PRIMARY KEY
  (ID_DICHIARAZIONE_SPESA, ID_DOCUMENTO_DI_SPESA)
  USING INDEX TABLESPACE PBANDI_SMALL_IDX);

ALTER TABLE PBANDI_S_DICH_DOC_SPESA ADD (
  CONSTRAINT FK_PBANDI_T_DICHIARAZ_SPESA_04 
  FOREIGN KEY (ID_DICHIARAZIONE_SPESA) 
  REFERENCES PBANDI_T_DICHIARAZIONE_SPESA (ID_DICHIARAZIONE_SPESA)
  ON DELETE CASCADE,
  CONSTRAINT FK_PBANDI_T_DOC_DI_SPESA_05 
  FOREIGN KEY (ID_DOCUMENTO_DI_SPESA) 
  REFERENCES PBANDI_T_DOCUMENTO_DI_SPESA (ID_DOCUMENTO_DI_SPESA)
  ON DELETE CASCADE);

-- VISTA DICHIARAZIONI-DOCUMENTI DI SPESA PAGAMENTI
create or replace view pbandi_v_DICH_DOC_SPESA as
         select id_dichiarazione_spesa, id_documento_di_spesa,  b.column_value id_pagamento
         from PBANDI_S_DICH_DOC_SPESA a, TABLE(id_pagamento_nt) b;
		 
COMMENT ON TABLE  pbandi_v_DICH_DOC_SPESA IS 'DICHIARAZIONI-DOCUMENTI DI SPESA PAGAMENTI';        
		 
 -- VISTA Pagamenti Progetto Documenti di spesa PBANDI_V_PAGAM_PROG_DOC_SPESA
 CREATE OR REPLACE VIEW PBANDI_V_PAGAM_PROG_DOC_SPESA AS
 WITH W_PAGAMENTO_DOC_SPESA AS  (
								SELECT  ID_DOCUMENTO_DI_SPESA,
								a.ID_PAGAMENTO,
								SUM(c.IMPORTO_QUIETANZATO) IMPORTO_QUIETANZATO,
								SUM(c.IMPORTO_VALIDATO) IMPORTO_VALIDATO
								FROM PBANDI_R_PAGAMENTO_DOC_SPESA a  LEFT JOIN  PBANDI_T_QUOTA_PARTE_DOC_SPESA b USING (ID_DOCUMENTO_DI_SPESA)
								LEFT JOIN PBANDI_R_PAG_QUOT_PARTE_DOC_SP c 
								ON c.ID_QUOTA_PARTE_DOC_SPESA  = b.ID_QUOTA_PARTE_DOC_SPESA
								AND c.ID_PAGAMENTO  = a.ID_PAGAMENTO
								GROUP BY ID_DOCUMENTO_DI_SPESA,
								a.ID_PAGAMENTO),
      W_RENDICONTATO_NOTA_CREDITO AS (
									SELECT NOTECREDITO.ID_DOC_RIFERIMENTO,DSP.ID_PROGETTO,
									SUM(DSP.IMPORTO_RENDICONTAZIONE) IMPORTO_RENDICONTAZIONE_NC
									FROM   PBANDI_T_DOCUMENTO_DI_SPESA NOTECREDITO,
											PBANDI_R_DOC_SPESA_PROGETTO DSP
									WHERE  DSP.ID_DOCUMENTO_DI_SPESA = NOTECREDITO.ID_DOCUMENTO_DI_SPESA
									AND ID_DOC_RIFERIMENTO IS NOT NULL
									GROUP BY NOTECREDITO.ID_DOC_RIFERIMENTO,DSP.ID_PROGETTO)
    SELECT  a.ID_DOCUMENTO_DI_SPESA,
           b.ID_PROGETTO,
           a.ID_PAGAMENTO,
           c.IMPORTO_PAGAMENTO IP,
           a.IMPORTO_QUIETANZATO IQ,
           NVL(a.IMPORTO_VALIDATO,0) IV,
           (c.IMPORTO_PAGAMENTO - NVL(a.IMPORTO_QUIETANZATO,0)) DELTA_I,
           SUM (c.IMPORTO_PAGAMENTO - NVL(a.IMPORTO_QUIETANZATO,0)) OVER (PARTITION BY a.ID_DOCUMENTO_DI_SPESA,b.ID_PROGETTO) TOT_DELTA_I,
           (b.IMPORTO_RENDICONTAZIONE - NVL(d.IMPORTO_RENDICONTAZIONE_NC,0)) IR,
           d.IMPORTO_RENDICONTAZIONE_NC  IR_NC,
           (CASE WHEN (SUM (c.IMPORTO_PAGAMENTO - NVL(a.IMPORTO_QUIETANZATO,0)) OVER (PARTITION BY a.ID_DOCUMENTO_DI_SPESA,b.ID_PROGETTO) - (b.IMPORTO_RENDICONTAZIONE - NVL(d.IMPORTO_RENDICONTAZIONE_NC,0))) >= 0 
                THEN 'S'
                ELSE 'N'
            END) FLAG_RQ
    FROM W_PAGAMENTO_DOC_SPESA a,
         PBANDI_R_DOC_SPESA_PROGETTO b,
         PBANDI_T_PAGAMENTO c,
         W_RENDICONTATO_NOTA_CREDITO d
    WHERE a.ID_DOCUMENTO_DI_SPESA     = b.ID_DOCUMENTO_DI_SPESA
      AND c.ID_PAGAMENTO             = a.ID_PAGAMENTO
      AND d.ID_DOC_RIFERIMENTO(+)      = b.ID_DOCUMENTO_DI_SPESA
      AND d.ID_PROGETTO(+)             = b.id_progetto;
	  

COMMENT ON TABLE PBANDI_V_PAGAM_PROG_DOC_SPESA IS 'Pagamenti Progetto Documenti di spesa';

COMMENT ON COLUMN PBANDI_V_PAGAM_PROG_DOC_SPESA.IP IS 'Importo pagamento';

COMMENT ON COLUMN PBANDI_V_PAGAM_PROG_DOC_SPESA.IQ IS 'Importo quietanziato';

COMMENT ON COLUMN PBANDI_V_PAGAM_PROG_DOC_SPESA.IV IS 'Importo validato';

COMMENT ON COLUMN PBANDI_V_PAGAM_PROG_DOC_SPESA.DELTA_I IS 'Delta  Importo pagamento-Importo quietanziato';

COMMENT ON COLUMN PBANDI_V_PAGAM_PROG_DOC_SPESA.TOT_DELTA_I IS 'Totale Delta  Importo pagamento-Importo quietanziato su documento/progetto';

COMMENT ON COLUMN PBANDI_V_PAGAM_PROG_DOC_SPESA.IR IS 'Importo rendicontazione compreso delle note di credito';

COMMENT ON COLUMN PBANDI_V_PAGAM_PROG_DOC_SPESA.IR_NC IS 'Importo rendicontazione solo note credito';

COMMENT ON COLUMN PBANDI_V_PAGAM_PROG_DOC_SPESA.FLAG_RQ IS '"S" se rendicontabile � coperto da quietanza altrimenti "N"';
