/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

-- 
-- PBANDI_R_BANDO_LINEA_FASIMONIT
-- ALTER TABLE PBANDI_R_BANDO_LINEA_FASIMONIT ADD DATA_LIMITE DATE;

-- PBANDI_T_RICHIESTA_EROGAZIONE
/*
alter table PBANDI_T_RICHIESTA_EROGAZIONE
add flag_bollino_verifica_erogaz varchar2(1);
*/

/*
alter table PBANDI_T_RICHIESTA_EROGAZIONE
  add constraint CHK_PBANDI_T_RICH_EROGAZIONE 
  check (flag_bollino_verifica_erogaz IN ('P', 'N'));
 */
 
-- pbandi_t_revoca  
alter table pbandi_t_revoca add DT_DECOR_REVOCA DATE;

-- PBANDI_T_FORNITORE_STRUTTURA
create table PBANDI_T_FORNITORE_STRUTTURA
(COD_STRUTTURA VARCHAR2(20) NOT NULL,
 NUMERO_DOMANDA VARCHAR2(20),
 CODICE_FISCALE_FORNITORE VARCHAR2(16),
 EMAIL VARCHAR2(100),
 IBAN  VARCHAR2(27),
 INTESTATARIO VARCHAR2(1000),
 ID_FORNITORE NUMBER,
 CONSTRAINT PK_PBANDI_R_FORN_STRUTTURA  PRIMARY KEY (COD_STRUTTURA) USING INDEX TABLESPACE PBANDI_IDX
 );