/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/* Gi� applicata in produzione 
  
CREATE INDEX IE_PBANDI_T_RIGO_CONTO_ECON_01 ON PBANDI_T_RIGO_CONTO_ECONOMICO
(ID_CONTO_ECONOMICO)
TABLESPACE PBANDI_MEDIUM_IDX;
*/

--PBANDI_R_BANDO_LINEA_PERIODO
CREATE TABLE PBANDI_R_BANDO_LINEA_PERIODO
(
  PROGR_BANDO_LINEA_INTERVENTO  NUMBER (8),
  ID_PERIODO 					NUMBER (8)
)
TABLESPACE PBANDI_SMALL_TBL;

ALTER TABLE PBANDI_R_BANDO_LINEA_PERIODO ADD (
  CONSTRAINT PK_PBANDI_R_BANDO_LINEA_PERIOD
  PRIMARY KEY
  (PROGR_BANDO_LINEA_INTERVENTO,ID_PERIODO)
  USING INDEX TABLESPACE PBANDI_SMALL_IDX);

ALTER TABLE PBANDI_R_BANDO_LINEA_PERIODO ADD (
  CONSTRAINT FK_PBANDI_R_BANDO_LINEA_INT_18
  FOREIGN KEY (PROGR_BANDO_LINEA_INTERVENTO) 
  REFERENCES PBANDI_R_BANDO_LINEA_INTERVENT (PROGR_BANDO_LINEA_INTERVENTO));

ALTER TABLE PBANDI_R_BANDO_LINEA_PERIODO ADD (
  CONSTRAINT FK_PBANDI_T_PERIODO_02
  FOREIGN KEY (ID_PERIODO) 
  REFERENCES PBANDI_T_PERIODO (ID_PERIODO));
  
CREATE INDEX IE1_PBANDI_R_BANDO_LINEA_PER ON PBANDI_R_BANDO_LINEA_PERIODO
  (ID_PERIODO)
  TABLESPACE PBANDI_SMALL_IDX;