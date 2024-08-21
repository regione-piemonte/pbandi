/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

-- d_voce_di_spesa -- plog
ALTER TABLE PBANDI_D_VOCE_DI_SPESA MODIFY ID_VOCE_DI_SPESA NUMBER(8);
ALTER TABLE PBANDI_D_VOCE_DI_SPESA MODIFY ID_VOCE_DI_SPESA_PADRE NUMBER(8);
ALTER TABLE PBANDI_R_BANDO_VOCE_SPESA MODIFY ID_VOCE_DI_SPESA NUMBER(8);
ALTER TABLE PBANDI_T_RIGO_CONTO_ECONOMICO MODIFY ID_VOCE_DI_SPESA NUMBER(8);
--
-- PBANDI_D_LINEA_DI_INTERVENTO
ALTER TABLE PBANDI_D_LINEA_DI_INTERVENTO ADD FLAG_CAMPION_RILEV VARCHAR2(2);
--CHECK
ALTER TABLE PBANDI_D_LINEA_DI_INTERVENTO ADD (
CONSTRAINT CHK_FLAG_CAMPION_RILEV
CHECK (FLAG_CAMPION_RILEV IN ('R', 'C','RC')));
--
-- PBANDI_T_INTEGRAZIONE_SPESA
ALTER TABLE PBANDI_T_INTEGRAZIONE_SPESA  MODIFY DATA_RICHIESTA NOT NULL;
ALTER TABLE PBANDI_T_INTEGRAZIONE_SPESA  MODIFY DESCRIZIONE NOT NULL;
ALTER TABLE PBANDI_T_INTEGRAZIONE_SPESA  MODIFY ID_DICHIARAZIONE_SPESA NOT NULL;
--
-- PBANDI_T_INTEGRAZIONE_SPESA
ALTER TABLE PBANDI_T_INTEGRAZIONE_SPESA ADD
( ID_UTENTE_RICHIESTA NUMBER (8) NOT NULL,
  ID_UTENTE_INVIO NUMBER (8) 
);
--FK
ALTER TABLE PBANDI_T_INTEGRAZIONE_SPESA ADD
CONSTRAINT FK_PBANDI_T_UTENTE_264
FOREIGN KEY (ID_UTENTE_RICHIESTA) 
REFERENCES PBANDI_T_UTENTE (ID_UTENTE);
--FK
ALTER TABLE PBANDI_T_INTEGRAZIONE_SPESA ADD
CONSTRAINT FK_PBANDI_T_UTENTE_265
FOREIGN KEY ( ID_UTENTE_INVIO) 
REFERENCES PBANDI_T_UTENTE (ID_UTENTE);
--INDEX
CREATE INDEX IE2_PBANDI_T_INTEGRAZ_SPESA ON PBANDI_T_INTEGRAZIONE_SPESA (ID_UTENTE_RICHIESTA) TABLESPACE PBANDI_IDX;
CREATE INDEX IE3_PBANDI_T_INTEGRAZ_SPESA ON PBANDI_T_INTEGRAZIONE_SPESA ( ID_UTENTE_INVIO) TABLESPACE PBANDI_IDX;
--
 -- PBANDI_T_IRREGOLARITA
ALTER TABLE  PBANDI_T_IRREGOLARITA ADD
(ID_IRREGOLARITA_PROVV NUMBER(8,0), 
FLAG_PROVVEDIMENTO VARCHAR2(1) DEFAULT 'N' NOT NULL,
TIPO_CONTROLLI VARCHAR2(1), 
DATA_INIZIO_CONTROLLI DATE,
DATA_FINE_CONTROLLI DATE,
IMPORTO_AGEVOLAZIONE_IRREG NUMBER (17,2),
ID_MOTIVO_REVOCA   NUMBER(3)
);  
--FK
ALTER TABLE PBANDI_T_IRREGOLARITA ADD
CONSTRAINT FK_PBANDI_D_MOTIVO_REVOCA_03
FOREIGN KEY (ID_MOTIVO_REVOCA) 
REFERENCES PBANDI_D_MOTIVO_REVOCA (ID_MOTIVO_REVOCA);
--CHECK
ALTER TABLE PBANDI_T_IRREGOLARITA ADD (
CONSTRAINT CHK_IRREGOLARITA
CHECK (FLAG_PROVVEDIMENTO IN ('D', 'S','N')));
--
ALTER TABLE PBANDI_T_IRREGOLARITA ADD (
CONSTRAINT CHK_IRREGOLARITA_02
CHECK (TIPO_CONTROLLI IN ('D', 'L')));
 
 -- PBANDI_T_IRREGOLARITA_PROVV 
 CREATE TABLE PBANDI_T_IRREGOLARITA_PROVV
 (ID_IRREGOLARITA_PROVV NUMBER(8,0) NOT NULL,
  DT_COMUNICAZIONE DATE NOT NULL,
  DT_FINE_PROVVISORIA DATE,
  ID_PROGETTO NUMBER(8,0) NOT NULL,
  ID_MOTIVO_REVOCA NUMBER(3,0) NOT NULL,
  --DT_FINE_PROVVISORIA DATE,
  IMPORTO_IRREGOLARITA NUMBER(17,2),
  IMPORTO_AGEVOLAZIONE_IRREG NUMBER(17,2),
  IMPORTO_IRREGOLARE_CERTIFICATO NUMBER(17,2),
  DT_FINE_VALIDITA DATE,
  TIPO_CONTROLLI VARCHAR2(1) NOT NULL, -- 'D' per Documentale 'L' per In Loco
  DATA_INIZIO_CONTROLLI DATE,
  DATA_FINE_CONTROLLI DATE,
  IRREGOLARITA_ANNULLATA VARCHAR2(1)  --  'S' oppure NULL
); 
 --PK
ALTER TABLE PBANDI_T_IRREGOLARITA_PROVV  ADD 
(CONSTRAINT PK_PBANDI_T_IRREGOLARITA_PROVV  PRIMARY KEY (ID_IRREGOLARITA_PROVV)
USING INDEX  TABLESPACE PBANDI_IDX);
--FK
ALTER TABLE PBANDI_T_IRREGOLARITA_PROVV ADD
CONSTRAINT FK_PBANDI_T_PROGETTO_47
FOREIGN KEY (ID_PROGETTO) 
REFERENCES PBANDI_T_PROGETTO (ID_PROGETTO);
--FK
ALTER TABLE PBANDI_T_IRREGOLARITA_PROVV ADD
CONSTRAINT FK_PBANDI_D_MOTIVO_REVOCA_02
FOREIGN KEY (ID_MOTIVO_REVOCA) 
REFERENCES PBANDI_D_MOTIVO_REVOCA (ID_MOTIVO_REVOCA);
--FK
ALTER TABLE PBANDI_T_IRREGOLARITA ADD
CONSTRAINT FK_PBANDI_T_IRREG_PROVV_01
FOREIGN KEY (ID_IRREGOLARITA_PROVV) 
REFERENCES PBANDI_T_IRREGOLARITA_PROVV (ID_IRREGOLARITA_PROVV);
--INDEX
CREATE INDEX IE1_PBANDI_T_IRREGOLARITA ON PBANDI_T_IRREGOLARITA (ID_IRREGOLARITA_PROVV) TABLESPACE PBANDI_IDX;
CREATE INDEX IE2_PBANDI_T_IRREGOLARITA ON PBANDI_T_IRREGOLARITA  (ID_MOTIVO_REVOCA) TABLESPACE PBANDI_IDX;
CREATE INDEX IE1_PBANDI_T_IRREG_PROVV ON PBANDI_T_IRREGOLARITA_PROVV (ID_PROGETTO) TABLESPACE PBANDI_IDX;
CREATE INDEX IE2_PBANDI_T_IRREG_PROVV ON PBANDI_T_IRREGOLARITA_PROVV  (ID_MOTIVO_REVOCA) TABLESPACE PBANDI_IDX;
--CHECK
ALTER TABLE PBANDI_T_IRREGOLARITA_PROVV ADD (
CONSTRAINT CHK_IRREGOLARITA_PROVV
CHECK (TIPO_CONTROLLI IN ('D', 'L')));
--  
ALTER TABLE PBANDI_T_IRREGOLARITA_PROVV ADD (
CONSTRAINT CHK_IRREGOLARITA_PROVV_02
CHECK (IRREGOLARITA_ANNULLATA IN ('S', NULL)));
-- SEQUENCE
CREATE SEQUENCE SEQ_PBANDI_T_IRREG_PROVV
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;
  
  
-------------------
-- CAMPIONAMENTO --
-------------------
-- PBANDI_T_CAMPIONAMENTO
CREATE TABLE PBANDI_T_CAMPIONAMENTO
(ID_CAMPIONE NUMBER (8),
 DATA_CAMPIONAMENTO DATE,
 ID_PROPOSTA_CERTIFICAZ NUMBER(8),
 ID_LINEA_DI_INTERVENTO  NUMBER (3), -- NORMATIVA 
 ID_CATEG_ANAGRAFICA NUMBER(3),
 ID_PERIODO NUMBER(8),
 TIPO_CONTROLLI VARCHAR2(1)
);
-- PK
ALTER TABLE PBANDI_T_CAMPIONAMENTO ADD 
(CONSTRAINT PK_PBANDI_T_CAMPIONAMENTO PRIMARY KEY (ID_CAMPIONE)
USING INDEX  TABLESPACE PBANDI_IDX);
--FK
--ALTER TABLE PBANDI_T_CAMPIONAMENTO ADD
--CONSTRAINT FK_PBANDI_T_PROPOSTA_CERTIF_09
--FOREIGN KEY (ID_PROPOSTA_CERTIFICAZ) 
--REFERENCES PBANDI_T_PROPOSTA_CERTIFICAZ (ID_PROPOSTA_CERTIFICAZ);
--FK
ALTER TABLE PBANDI_T_CAMPIONAMENTO ADD
CONSTRAINT FK_PBANDI_D_LINEA_INTERV_58
FOREIGN KEY (ID_LINEA_DI_INTERVENTO) 
REFERENCES PBANDI_D_LINEA_DI_INTERVENTO (ID_LINEA_DI_INTERVENTO);
--FK
ALTER TABLE PBANDI_T_CAMPIONAMENTO ADD
CONSTRAINT FK_PBANDI_D_CATEG_ANAG_04
FOREIGN KEY (ID_CATEG_ANAGRAFICA) 
REFERENCES PBANDI_D_CATEG_ANAGRAFICA (ID_CATEG_ANAGRAFICA);
--FK
ALTER TABLE PBANDI_T_CAMPIONAMENTO ADD
CONSTRAINT FK_PBANDI_T_PERIODO_07
FOREIGN KEY (ID_PERIODO) 
REFERENCES PBANDI_T_PERIODO (ID_PERIODO);
-- CHECK
ALTER TABLE PBANDI_T_CAMPIONAMENTO ADD (
CONSTRAINT CHK_PBANDI_T_CAMPIONAMENTO
CHECK (TIPO_CONTROLLI IN ('D', 'L')));
-- PBANDI_R_CAMPIONAMENTO
CREATE TABLE PBANDI_R_CAMPIONAMENTO
(ID_CAMPIONE NUMBER (8),
 PROGR_OPERAZIONE NUMBER (8), 
 UNIVERSO  VARCHAR2(2),
 ID_PROGETTO  NUMBER (8),
 TITOLO_PROGETTO  VARCHAR2(2000),
 AVANZAMENTO NUMBER (17,2),
 ASSE VARCHAR2(20)   
);
-- PK
ALTER TABLE PBANDI_R_CAMPIONAMENTO ADD 
(CONSTRAINT PK_PBANDI_R_CAMPIONAMENTO PRIMARY KEY (ID_CAMPIONE, ID_PROGETTO)
USING INDEX  TABLESPACE PBANDI_IDX);
--FK
ALTER TABLE PBANDI_R_CAMPIONAMENTO ADD
CONSTRAINT FK_PBANDI_T_CAMPIONAMENTO_01
FOREIGN KEY (ID_CAMPIONE) 
REFERENCES PBANDI_T_CAMPIONAMENTO (ID_CAMPIONE);
--INDEX
CREATE INDEX IE1_PBANDI_T_CAMPIONAMENTO ON PBANDI_T_CAMPIONAMENTO (ID_PROPOSTA_CERTIFICAZ) TABLESPACE PBANDI_IDX;
CREATE INDEX IE2_PBANDI_T_CAMPIONAMENTO ON PBANDI_T_CAMPIONAMENTO (ID_LINEA_DI_INTERVENTO) TABLESPACE PBANDI_IDX;
CREATE INDEX IE3_PBANDI_T_CAMPIONAMENTO ON PBANDI_T_CAMPIONAMENTO (ID_CATEG_ANAGRAFICA) TABLESPACE PBANDI_IDX;
CREATE INDEX IE4_PBANDI_T_CAMPIONAMENTO ON PBANDI_T_CAMPIONAMENTO (ID_PERIODO) TABLESPACE PBANDI_IDX;
CREATE INDEX IE1_PBANDI_R_CAMPIONAMENTO ON PBANDI_R_CAMPIONAMENTO (ID_PROGETTO) TABLESPACE PBANDI_IDX;
--
-- PBANDI_W_CAMPIONAMENTO
CREATE TABLE PBANDI_W_CAMPIONAMENTO
(ID_CAMPIONE NUMBER (8),
 PROGR_OPERAZIONE NUMBER (8),
 ID_DETT_PROPOSTA_CERTIF NUMBER (8),
 ASSE VARCHAR2 (20),
 ID_PROGETTO  NUMBER (8), 
 TITOLO_PROGETTO VARCHAR2 (2000),            
 CODICE_VISUALIZZATO VARCHAR2 (100) ,
 AVANZAMENTO NUMBER (17,2),
 UNIVERSO VARCHAR2(2), -- U1 PER LA PRIMA META' - U2 PER LA SECONDA META' 
 TIPO_CAMPIONE VARCHAR2(2), -- C1 - C2 IN BASE AL CALCOLO DELL'ALGORITMO
 FLAG_ESCLUDI VARCHAR2(1),
 FLAG_PRIMO_ESTRATTO VARCHAR2(1) 
);
-- PK
ALTER TABLE PBANDI_W_CAMPIONAMENTO ADD 
(CONSTRAINT PK_PBANDI_W_CAMPIONAMENTO PRIMARY KEY (ID_CAMPIONE, ID_PROGETTO)
USING INDEX  TABLESPACE PBANDI_IDX);

-- PBANDI_W_CAMPIONE_FSE 
CREATE TABLE PBANDI_W_CAMPIONE_FSE 
( 
 ID_CERTIFICAZIONE NUMBER,
 DATA_CERTIFICAZIONE DATE,
 ASSE VARCHAR2(20), 
 ID_PROGETTO NUMBER, 
 TITOLO_PROGETTO VARCHAR2(2000),
 AVANZAMENTO NUMBER(17,2)
);
-- PK
ALTER TABLE PBANDI_W_CAMPIONE_FSE  ADD 
(CONSTRAINT PK_PBANDI_W_CAMPIONE_FSE  PRIMARY KEY (ID_PROGETTO)
USING INDEX  TABLESPACE PBANDI_IDX);
--
-- PBANDI_R_DETT_CAMPIONAMENTO
CREATE TABLE PBANDI_R_DETT_CAMPIONAMENTO
(
ID_CAMPIONE NUMBER,
VALORE_MAX_AVANZAMENTO_U NUMBER(17,2),
VALORE_MIN_AVANZAMENTO_U NUMBER(17,2),
VALORE_MED_AVANZAMENTO_U  NUMBER(17,2),
VALORE_M75_AVANZAMENTO_U NUMBER(17,2),
NUMEROSITA_CAMPIONE_U1 NUMBER(8),
NUMERO_UNITA_ESTRATTE_U1 NUMBER(8),
PROGR_OPERAZ_PRIMO_ESTRATTO_U1 NUMBER(8),
AVANZAMENTO_PRIMO_ESTRATTO_U1 NUMBER(17,2),
SOMMA_AVANZAMENTI_CAMPIONE_U1 NUMBER(17,2),
TOTALE_AVANZAMENTI_ESTRATTI_U1 NUMBER(17,2),
RAPPORTO_NUMEROSITA_U1 NUMBER(10,2),
VALORE_MAX_AVANZAMENTO_U2 NUMBER(17,2),
VALORE_MIN_AVANZAMENTO_U2 NUMBER(17,2),
NUMEROSITA_CAMPIONE_U2 NUMBER(8),
NUMERO_UNITA_ESTRATTE_U2 NUMBER(8),
RAPPORTO_NUMEROSITA_U2 NUMBER(10,2),
VALORE_V NUMBER(3),
SOMMA_AVANZAMENTI_CAMPIONE_U2 NUMBER(17,2),
PROGR_OPERAZ_PRIMO_ESTRATTO_U2 NUMBER(8),
TOTALE_AVANZAMENTI_ESTRATTI_U2 NUMBER(17,2),
TOTALE_AVANZAMENTI_ESTRATTI_U NUMBER(17,2),
SOMMA_AVANZAMENTI_CAMPIONE_U NUMBER(17,2),
RAPPORTO_AVANZAMENTI_U NUMBER(10,2),
NUMERO_UNITA_ESTRATTE_U NUMBER(8),
NUMEROSITA_CAMPIONE_U NUMBER(8),
RAPPORTO_NUMEROSITA_U NUMBER(10,2),
AVANZAMENTO_PRIMO_ESTRATTO_U2 NUMBER(17,2)
);
-- PK
ALTER TABLE PBANDI_R_DETT_CAMPIONAMENTO  ADD 
(CONSTRAINT PK_PBANDI_R_DETT_CAMPIONAMENTO  PRIMARY KEY (ID_CAMPIONE)
USING INDEX  TABLESPACE PBANDI_IDX);
-- FK
ALTER TABLE PBANDI_R_DETT_CAMPIONAMENTO ADD
CONSTRAINT FK_PBANDI_T_CAMPIONAMENTO_02
FOREIGN KEY (ID_CAMPIONE) 
REFERENCES PBANDI_T_CAMPIONAMENTO (ID_CAMPIONE);
-- PBANDI_T_REVOCA
ALTER TABLE PBANDI_T_REVOCA ADD
(FLAG_ORDINE_RECUPERO VARCHAR2(1),
ID_MANCATO_RECUPERO  NUMBER(8)
);
-- PBANDI_D_MANCATO_RECUPERO
CREATE TABLE PBANDI_D_MANCATO_RECUPERO
(ID_MANCATO_RECUPERO NUMBER(8),
 DESC_MANCATO_RECUPERO VARCHAR2(4000),
 DT_INIZIO_VALIDITA  DATE NOT NULL, 
 DT_FINE_VALIDITA DATE                
);
--PK
ALTER TABLE PBANDI_D_MANCATO_RECUPERO ADD 
(CONSTRAINT PK_PBANDI_D_MANCATO_RECUPERO PRIMARY KEY (ID_MANCATO_RECUPERO)
USING INDEX  TABLESPACE PBANDI_IDX);
--FK
ALTER TABLE PBANDI_T_REVOCA ADD
CONSTRAINT FK_PBANDI_D_MANCATO_REC_01
FOREIGN KEY (ID_MANCATO_RECUPERO) 
REFERENCES PBANDI_D_MANCATO_RECUPERO (ID_MANCATO_RECUPERO);
--INDEX
CREATE INDEX IE3_PBANDI_T_REVOCA ON PBANDI_T_REVOCA (ID_MANCATO_RECUPERO) TABLESPACE PBANDI_IDX;
--

-- PBANDI_T_ESITO_CONTROLLI
CREATE TABLE PBANDI_T_ESITO_CONTROLLI
(ID_ESITO_CONTROLLO INTEGER NOT NULL,
 ID_PROGETTO NUMBER,
 ESITO_CONTROLLO VARCHAR2(20),
 ID_CATEG_ANAGRAFICA NUMBER(3) NOT NULL,
 ID_PERIODO NUMBER(8) NOT NULL,
 TIPO_CONTROLLI VARCHAR2(1) NOT NULL,  
 DATA_INIZIO_CONTROLLI DATE NOT NULL, 
 DATA_FINE_CONTROLLI DATE NOT NULL,    
 NOTE VARCHAR2(4000),
 DT_COMUNICAZIONE DATE
);

--PK
ALTER TABLE PBANDI_T_ESITO_CONTROLLI ADD 
(CONSTRAINT PK_PBANDI_T_ESITO_CONTROLLI PRIMARY KEY (ID_ESITO_CONTROLLO)
USING INDEX  TABLESPACE PBANDI_IDX);
--FK
ALTER TABLE PBANDI_T_ESITO_CONTROLLI ADD
CONSTRAINT FK_PBANDI_T_PROGETTO_48
FOREIGN KEY (ID_PROGETTO) 
REFERENCES PBANDI_T_PROGETTO (ID_PROGETTO);
--FK
ALTER TABLE PBANDI_T_ESITO_CONTROLLI ADD
CONSTRAINT FK_PBANDI_D_CATEG_ANAG_05
FOREIGN KEY (ID_CATEG_ANAGRAFICA ) 
REFERENCES PBANDI_D_CATEG_ANAGRAFICA (ID_CATEG_ANAGRAFICA );
--FK
ALTER TABLE PBANDI_T_ESITO_CONTROLLI ADD
CONSTRAINT FK_PBANDI_T_PERIODO_08
FOREIGN KEY (ID_PERIODO ) 
REFERENCES PBANDI_T_PERIODO (ID_PERIODO );
--INDEX
CREATE INDEX IE1_PBANDI_T_ESITO_CONTROLLI ON PBANDI_T_ESITO_CONTROLLI (ID_PROGETTO) TABLESPACE PBANDI_IDX;
CREATE INDEX IE2_PBANDI_T_ESITO_CONTROLLI ON PBANDI_T_ESITO_CONTROLLI (ID_CATEG_ANAGRAFICA) TABLESPACE PBANDI_IDX;
CREATE INDEX IE3_PBANDI_T_ESITO_CONTROLLI ON PBANDI_T_ESITO_CONTROLLI (ID_PERIODO) TABLESPACE PBANDI_IDX;

-- PBANDI_T_IRREGOLARITA_PROVV
-- PBANDI_T_IRREGOLARITA
ALTER TABLE  PBANDI_T_IRREGOLARITA_PROVV ADD
 (ID_CATEG_ANAGRAFICA NUMBER(3), 
 ID_PERIODO NUMBER(8)
 ); 
 
ALTER TABLE  PBANDI_T_IRREGOLARITA ADD 
(ID_CATEG_ANAGRAFICA NUMBER(3), 
ID_PERIODO NUMBER(8)
);


--FK
ALTER TABLE  PBANDI_T_IRREGOLARITA_PROVV ADD
CONSTRAINT FK_PBANDI_D_CATEG_ANAG_06
FOREIGN KEY (ID_CATEG_ANAGRAFICA ) 
REFERENCES PBANDI_D_CATEG_ANAGRAFICA (ID_CATEG_ANAGRAFICA );
--FK
ALTER TABLE  PBANDI_T_IRREGOLARITA_PROVV ADD
CONSTRAINT FK_PBANDI_T_PERIODO_09
FOREIGN KEY (ID_PERIODO ) 
REFERENCES PBANDI_T_PERIODO (ID_PERIODO );
--FK
ALTER TABLE  PBANDI_T_IRREGOLARITA ADD
CONSTRAINT FK_PBANDI_D_CATEG_ANAG_07
FOREIGN KEY (ID_CATEG_ANAGRAFICA ) 
REFERENCES PBANDI_D_CATEG_ANAGRAFICA (ID_CATEG_ANAGRAFICA );
--FK
ALTER TABLE  PBANDI_T_IRREGOLARITA ADD
CONSTRAINT FK_PBANDI_T_PERIODO_10
FOREIGN KEY (ID_PERIODO ) 
REFERENCES PBANDI_T_PERIODO (ID_PERIODO );
---
---
ALTER TABLE PBANDI_T_IRREGOLARITA ADD NOTE VARCHAR2(4000);
ALTER TABLE PBANDI_T_IRREGOLARITA_PROVV ADD NOTE VARCHAR2(4000);
ALTER TABLE PBANDI_T_IRREGOLARITA MODIFY ID_STATO_AMMINISTRATIVO NULL;
ALTER TABLE PBANDI_T_IRREGOLARITA MODIFY ID_DISP_COMUNITARIA NULL;
ALTER TABLE PBANDI_T_IRREGOLARITA MODIFY ID_METODO_INDIVIDUAZIONE NULL;
ALTER TABLE PBANDI_T_IRREGOLARITA MODIFY ID_NATURA_SANZIONE NULL;
ALTER TABLE PBANDI_T_IRREGOLARITA MODIFY ID_TIPO_IRREGOLARITA NULL;
ALTER TABLE PBANDI_T_IRREGOLARITA MODIFY ID_QUALIFICAZIONE_IRREG NULL;
ALTER TABLE PBANDI_T_IRREGOLARITA MODIFY ID_STATO_FINANZIARIO NULL;
---
---
--INDEX
CREATE INDEX IE3_PBANDI_T_IRREG_PROVV ON PBANDI_T_IRREGOLARITA_PROVV (ID_CATEG_ANAGRAFICA) TABLESPACE PBANDI_IDX;
CREATE INDEX IE4_PBANDI_T_IRREG_PROVV ON PBANDI_T_IRREGOLARITA_PROVV (ID_PERIODO) TABLESPACE PBANDI_IDX;
CREATE INDEX IE3_PBANDI_T_IRREGOLARITA ON PBANDI_T_IRREGOLARITA (ID_CATEG_ANAGRAFICA) TABLESPACE PBANDI_IDX;
CREATE INDEX IE4_PBANDI_T_IRREGOLARITA ON PBANDI_T_IRREGOLARITA (ID_PERIODO) TABLESPACE PBANDI_IDX;
--

-- PBANDI_D_CLASSIFICAZIONI
CREATE TABLE PBANDI_D_CLASSIFICAZIONI
(ID_CLASSIFICAZIONI  NUMBER(3),
 COD_CLASSIFICAZIONI VARCHAR2(200),
 DESCR_CLASSIFICAZIONI VARCHAR2(1000),
 TIPO_CLASS VARCHAR2(10),
 COD_RAGGRUPPAMENTO  VARCHAR2(10),
 DESCR_RAGGRUPPAMENTO VARCHAR2(1000)
);
-- PK
ALTER TABLE PBANDI_D_CLASSIFICAZIONI  ADD 
(CONSTRAINT PK_PBANDI_D_CLASSIFICAZIONI  PRIMARY KEY (ID_CLASSIFICAZIONI)
USING INDEX  TABLESPACE PBANDI_IDX);  
-- PBANDI_T_PROGETTO
ALTER TABLE PBANDI_T_PROGETTO ADD ID_CLASSIFICAZIONI NUMBER(3);
-- FK
ALTER TABLE PBANDI_T_PROGETTO ADD
CONSTRAINT FK_PBANDI_D_CLASSIFICAZIONI_01
FOREIGN KEY (ID_CLASSIFICAZIONI) 
REFERENCES PBANDI_D_CLASSIFICAZIONI (ID_CLASSIFICAZIONI);
-- INDEX
CREATE INDEX IE8_PBANDI_T_PROGETTO ON PBANDI_T_PROGETTO(ID_CLASSIFICAZIONI) TABLESPACE PBANDI_IDX;

--VISTA 
--
-- PBANDI_V_RILEVAZIONI
/* Formatted on 19/03/2019 08:34:52 (QP5 v5.163.1008.3004) */
CREATE OR REPLACE VIEW PBANDI_V_RILEVAZIONI AS
(SELECT DISTINCT
           ptp.ID_PERIODO,
           ptp.DESC_PERIODO_VISUALIZZATA,
           -- tc.ID_CAMPIONE,
           -- id normativa
           (    SELECT ID_LINEA_DI_INTERVENTO
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 1 --  NORMATIVA LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              ID_NORMATIVA,
           -- normativa
           (    SELECT DESC_BREVE_LINEA
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 1 --  NORMATIVA LEGATA AL PROGETTO
                CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              NORMATIVA,
           -- id asse
           (    SELECT ID_LINEA_DI_INTERVENTO
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 2 --  ASSE LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              ID_ASSE,
           --asse
           (    SELECT DESC_BREVE_LINEA || '-' || DESC_LINEA
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 2 --  ASSE LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              ASSE,
           -- id misura
           (    SELECT ID_LINEA_DI_INTERVENTO
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 3 --  MISURA LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              ID_MISURA,
           --misura
           (    SELECT DESC_BREVE_LINEA || '-' || DESC_LINEA
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 3 --  MISURA LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              MISURA,
           --id linea
           (    SELECT ID_LINEA_DI_INTERVENTO
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 4 --  LINEA LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              ID_AZIONE,
           -- linea
           (    SELECT DESC_BREVE_LINEA || '-' || DESC_LINEA
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 4 --  LINEA LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              AZIONE,
           bli.PROGR_BANDO_LINEA_INTERVENTO,
           rc.ID_PROGETTO,
           bli.NOME_BANDO_LINEA,
           tp.CODICE_VISUALIZZATO,
           dca.ID_CATEG_ANAGRAFICA,
           dca.DESC_CATEG_ANAGRAFICA,
           teg.DENOMINAZIONE_ENTE_GIURIDICO,
           tc.TIPO_CONTROLLI,
           ti.ID_IRREGOLARITA,
           ti.ID_IRREGOLARITA_PROVV AS ID_IRREGOLARITA_COLLEG_PROVV,
           ti.DATA_INIZIO_CONTROLLI,
           ti.DATA_FINE_CONTROLLI,
           tip.ID_IRREGOLARITA_PROVV AS ID_IRREGOLARITA_PROVV,              --
           tip.DATA_INIZIO_CONTROLLI AS DATA_INIZIO_CONTROLLI_PROVV,        --
           tip.DATA_FINE_CONTROLLI AS DATA_FINE_CONTROLLI_PROVV,            --
           tip.IRREGOLARITA_ANNULLATA AS IRREGOLARITA_ANNULLATA_PROVV,      --
           tip.IMPORTO_IRREGOLARITA AS IMPORTO_IRREGOLARITA_PROVV,          --
           tip.IMPORTO_AGEVOLAZIONE_IRREG AS IMPORTO_AGEVOL_IRREG_PROVV,    --
           tip.IMPORTO_IRREGOLARE_CERTIFICATO AS IMPORTO_IRREG_CERTIF_PROVV, --
           (SELECT DESC_MOTIVO_REVOCA
              FROM PBANDI_D_MOTIVO_REVOCA
             WHERE id_motivo_revoca = tip.id_motivo_revoca)
              AS DESC_MOTIVO_REVOCA_PROVV,
           ti.IMPORTO_IRREGOLARITA,
           ti.IMPORTO_AGEVOLAZIONE_IRREG,
           ti.QUOTA_IMP_IRREG_CERTIFICATO,
           mr.DESC_MOTIVO_REVOCA,
           ti.NUMERO_IMS,
           (dcd.DESC_CAUSALE_DISIMPEGNO || ' - ' || mr.DESC_MOTIVO_REVOCA)
              AS TIPO_PROVVEDIMENTO,
           tr.ESTREMI,
           tr.IMPORTO,
           (SELECT SUM (trp.IMPORTO_RECUPERO)
              FROM PBANDI_T_RECUPERO trp
             WHERE trp.ID_PROGETTO = rc.ID_PROGETTO)
              IMPORTO_RECUPERO,
           (SELECT MAX (trp.DT_RECUPERO)
              FROM PBANDI_T_RECUPERO trp
             WHERE trp.ID_PROGETTO = rc.ID_PROGETTO)
              DATA_RECUPERO,
           dmr.DESC_MANCATO_RECUPERO,
           NULL AS  ID_ESITO_CONTROLLO, --mc
           NULL AS  DT_INIZIO_CONTROLLI_REGOLARI, --mc
           NULL AS  DT_FINE_CONTROLLI_REGOLARI, --mc
           ti.NOTE AS NOTE_IRREGOLARITA, -- mc
           tip.NOTE AS  NOTE_IRREGOLARITA_PROVVISORIA, -- mc
           NULL AS NOTE_ESITO_REGOLARE --mc
      FROM PBANDI_T_CAMPIONAMENTO tc,
           PBANDI_R_CAMPIONAMENTO rc,
           PBANDI_T_PROGETTO tp,
           PBANDI_R_BANDO_LINEA_INTERVENT bli,
           PBANDI_T_DOMANDA td,
           PBANDI_T_PERIODO ptp,
           PBANDI_D_CATEG_ANAGRAFICA dca,
           PBANDI_R_SOGGETTO_PROGETTO rsp,
           PBANDI_T_ENTE_GIURIDICO teg,
           PBANDI_T_IRREGOLARITA ti,
           PBANDI_T_IRREGOLARITA_PROVV tip,
           PBANDI_D_MOTIVO_REVOCA mr,
           PBANDI_T_REVOCA tr,
           PBANDI_D_CAUSALE_DISIMPEGNO dcd,
           PBANDI_D_MANCATO_RECUPERO dmr,
           PBANDI_T_SOGGETTO ts,
           --PBANDI_T_RECUPERO trec,
           PBANDI_R_REVOCA_IRREG rri
     WHERE     ti.ID_IRREGOLARITA_PROVV = tip.ID_IRREGOLARITA_PROVV(+)
           AND tc.ID_CAMPIONE = rc.ID_CAMPIONE
           AND rc.ID_PROGETTO = tp.ID_PROGETTO
           AND tc.ID_PERIODO = ptp.ID_PERIODO
           AND tc.ID_CATEG_ANAGRAFICA = dca.ID_CATEG_ANAGRAFICA
           AND tp.ID_PROGETTO = rsp.ID_PROGETTO
           AND rsp.ID_ENTE_GIURIDICO = teg.ID_ENTE_GIURIDICO
           AND ts.ID_SOGGETTO = teg.id_SOGGETTO
           AND rsp.ID_TIPO_ANAGRAFICA = 1
           AND rsp.ID_TIPO_BENEFICIARIO <> 4
           AND tp.ID_PROGETTO = ti.ID_PROGETTO(+)
           -- AND tp.ID_PROGETTO = tip.ID_PROGETTO(+)
           --AND tp.ID_PROGETTO = trec.ID_PROGETTO(+)
           AND tr.ID_MOTIVO_REVOCA = mr.ID_MOTIVO_REVOCA(+)
           AND ti.ID_IRREGOLARITA = rri.ID_IRREGOLARITA(+)
           AND rri.ID_REVOCA = tr.ID_REVOCA(+)
           AND tr.ID_CAUSALE_DISIMPEGNO = dcd.ID_CAUSALE_DISIMPEGNO(+)
           AND tr.ID_MANCATO_RECUPERO = dmr.ID_MANCATO_RECUPERO(+)
           AND tp.ID_DOMANDA = td.ID_DOMANDA
           AND td.PROGR_BANDO_LINEA_INTERVENTO =
                  bli.PROGR_BANDO_LINEA_INTERVENTO
           AND ti.DT_FINE_VALIDITA IS NULL
           AND tc.id_categ_anagrafica = ti.id_categ_anagrafica -- mc
           AND tc.id_periodo = ti.id_periodo -- mc
           AND tc.tipo_controlli = ti.tipo_controlli -- mc
           --DA NON FAR vedere se ha una provvisoria senza definitiva, questa casistica è gestita dalla seconda union , per evitare duplicazioni
           -- DA FAR vedere se ha la definitiva con la provvisoria collegata
           -- DA FAR vedere se la definitiva e la provvisoria non sono collegate ma sono validate entrambe (data fine null)
           AND (tp.id_progetto NOT IN
                   (SELECT DISTINCT id_progetto
                      FROM PBANDI_T_IRREGOLARITA_PROVV)
                AND tp.id_progetto IN (SELECT DISTINCT id_progetto
                                         FROM PBANDI_T_IRREGOLARITA
                                        WHERE DT_FINE_VALIDITA IS NULL)
                OR (tp.id_progetto IN
                       (SELECT DISTINCT ti.id_progetto
                          FROM PBANDI_T_IRREGOLARITA ti,
                               PBANDI_T_IRREGOLARITA_PROVV tip
                         WHERE ti.ID_IRREGOLARITA_PROVV =
                                  tip.ID_IRREGOLARITA_PROVV))
                OR (tp.id_progetto IN (SELECT DISTINCT ti.id_progetto
                                         FROM PBANDI_T_IRREGOLARITA ti
                                        WHERE ti.DT_FINE_VALIDITA IS NULL)
                    OR tp.id_progetto IN
                          (SELECT DISTINCT tip.id_progetto
                             FROM PBANDI_T_IRREGOLARITA_PROVV tip
                            WHERE tip.DT_FINE_VALIDITA IS NULL)))
    )
    UNION
    (SELECT DISTINCT
           ptp.ID_PERIODO,
           ptp.DESC_PERIODO_VISUALIZZATA,
           -- tc.ID_CAMPIONE,
           -- id normativa
           (    SELECT ID_LINEA_DI_INTERVENTO
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 1 --  NORMATIVA LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              ID_NORMATIVA,
           -- normativa
           (    SELECT DESC_BREVE_LINEA
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 1 --  NORMATIVA LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              NORMATIVA,
           -- id asse
           (    SELECT ID_LINEA_DI_INTERVENTO
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 2 --  ASSE LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              ID_ASSE,
           --asse
           (    SELECT DESC_BREVE_LINEA || '-' || DESC_LINEA
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 2 --  ASSE LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              ASSE,
           -- id misura
           (    SELECT ID_LINEA_DI_INTERVENTO
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 3 --  MISURA LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              ID_MISURA,
           --misura
           (    SELECT DESC_BREVE_LINEA || '-' || DESC_LINEA
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 3 --  MISURA LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              MISURA,
           --id linea
           (    SELECT ID_LINEA_DI_INTERVENTO
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 4 --  LINEA LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              ID_AZIONE,
           -- linea
           (    SELECT DESC_BREVE_LINEA || '-' || DESC_LINEA
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 4 --  LINEA LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              AZIONE,
           bli.PROGR_BANDO_LINEA_INTERVENTO,
           rc.ID_PROGETTO,
           bli.NOME_BANDO_LINEA,
           tp.CODICE_VISUALIZZATO,
           dca.ID_CATEG_ANAGRAFICA,
           dca.DESC_CATEG_ANAGRAFICA,
           teg.DENOMINAZIONE_ENTE_GIURIDICO,
           tc.TIPO_CONTROLLI,
           NULL AS ID_IRREGOLARITA,
           NULL AS ID_IRREGOLARITA_COLLEG_PROVV,
           NULL AS DATA_INIZIO_CONTROLLI,
           NULL AS DATA_FINE_CONTROLLI,
           tip.ID_IRREGOLARITA_PROVV,
           tip.DATA_INIZIO_CONTROLLI AS DATA_INIZIO_CONTROLLI_PROVV,
           tip.DATA_FINE_CONTROLLI AS DATA_FINE_CONTROLLI_PROVV,
           tip.IRREGOLARITA_ANNULLATA AS IRREGOLARITA_ANNULLATA_PROVV,
           tip.IMPORTO_IRREGOLARITA AS IMPORTO_IRREGOLARITA_PROVV,
           tip.IMPORTO_AGEVOLAZIONE_IRREG AS IMPORTO_AGEVOL_IRREG_PROVV,
           tip.IMPORTO_IRREGOLARE_CERTIFICATO AS IMPORTO_IRREG_CERTIF_PROVV,
           mr.DESC_MOTIVO_REVOCA AS DESC_MOTIVO_REVOCA_PROVV,
           NULL AS IMPORTO_IRREGOLARITA,
           NULL AS IMPORTO_AGEVOLAZIONE_IRREG,
           NULL AS QUOTA_IMP_IRREG_CERTIFICATO,
           NULL AS DESC_MOTIVO_REVOCA,
           NULL AS NUMERO_IMS,
           NULL AS TIPO_PROVVEDIMENTO,
           NULL AS ESTREMI,
           NULL AS IMPORTO,
           NULL AS IMPORTO_RECUPERO,
           NULL AS DATA_RECUPERO,
           NULL AS DESC_MANCATO_RECUPERO,
           NULL AS ID_ESITO_CONTROLLO, --mc
           NULL AS  DT_INIZIO_CONTROLLI_REGOLARI, --mc
           NULL AS  DT_FINE_CONTROLLI_REGOLARI, --mc
           NULL AS NOTE_IRREGOLARITA, -- mc
           tip.NOTE AS  NOTE_IRREGOLARITA_PROVVISORIA, -- mc
           NULL AS NOTE_ESITO_REGOLARE --mc
      FROM PBANDI_T_CAMPIONAMENTO tc,
           PBANDI_R_CAMPIONAMENTO rc,
           PBANDI_T_PROGETTO tp,
           PBANDI_R_BANDO_LINEA_INTERVENT bli,
           PBANDI_T_DOMANDA td,
           PBANDI_T_PERIODO ptp,
           PBANDI_D_CATEG_ANAGRAFICA dca,
           PBANDI_R_SOGGETTO_PROGETTO rsp,
           PBANDI_T_ENTE_GIURIDICO teg,
           PBANDI_T_IRREGOLARITA_PROVV tip,
           PBANDI_D_MOTIVO_REVOCA mr,
           PBANDI_T_SOGGETTO ts
     WHERE     tc.ID_CAMPIONE = rc.ID_CAMPIONE
           AND rc.ID_PROGETTO = tp.ID_PROGETTO
           AND tc.ID_PERIODO = ptp.ID_PERIODO
           AND tc.ID_CATEG_ANAGRAFICA = dca.ID_CATEG_ANAGRAFICA
           AND tp.ID_PROGETTO = rsp.ID_PROGETTO
           AND rsp.ID_ENTE_GIURIDICO = teg.ID_ENTE_GIURIDICO
           AND ts.ID_SOGGETTO = teg.id_SOGGETTO
           AND rsp.ID_TIPO_ANAGRAFICA = 1
           AND rsp.ID_TIPO_BENEFICIARIO <> 4
           AND tp.ID_PROGETTO = tip.ID_PROGETTO(+)
           AND tip.ID_MOTIVO_REVOCA = mr.ID_MOTIVO_REVOCA(+)
           AND tp.ID_DOMANDA = td.ID_DOMANDA
           AND td.PROGR_BANDO_LINEA_INTERVENTO =
                  bli.PROGR_BANDO_LINEA_INTERVENTO
           AND tc.id_categ_anagrafica = tip.id_categ_anagrafica -- mc
           AND tc.id_periodo = tip.id_periodo -- mc
           AND tc.tipo_controlli = tip.tipo_controlli -- mc
           AND tip.ID_IRREGOLARITA_PROVV NOT IN
                  (SELECT ti.ID_IRREGOLARITA_PROVV
                     FROM PBANDI_T_IRREGOLARITA ti
                    WHERE     ti.DT_FINE_VALIDITA IS NULL
                          AND ID_PROGETTO = tp.ID_PROGETTO
                          AND ID_IRREGOLARITA_PROVV IS NOT NULL)
           AND tip.DT_FINE_VALIDITA IS NULL)
UNION
    -- esiti_regolari
    (SELECT DISTINCT
           ptp.ID_PERIODO,
           ptp.DESC_PERIODO_VISUALIZZATA,
           -- tc.ID_CAMPIONE,
           -- id normativa
           (    SELECT ID_LINEA_DI_INTERVENTO
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 1 --  NORMATIVA LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              ID_NORMATIVA,
           -- normativa
           (    SELECT DESC_BREVE_LINEA
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 1 --  NORMATIVA LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              NORMATIVA,
           -- id asse
           (    SELECT ID_LINEA_DI_INTERVENTO
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 2 --  ASSE LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              ID_ASSE,
           --asse
           (    SELECT DESC_BREVE_LINEA || '-' || DESC_LINEA
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 2 --  ASSE LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              ASSE,
           -- id misura
           (    SELECT ID_LINEA_DI_INTERVENTO
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 3 --  MISURA LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              ID_MISURA,
           --misura
           (    SELECT DESC_BREVE_LINEA || '-' || DESC_LINEA
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 3 --  MISURA LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              MISURA,
           --id linea
           (    SELECT ID_LINEA_DI_INTERVENTO
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 4 --  LINEA LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              ID_AZIONE,
           -- linea
           (    SELECT DESC_BREVE_LINEA || '-' || DESC_LINEA
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 4 --  LINEA LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              AZIONE,
           bli.PROGR_BANDO_LINEA_INTERVENTO,
           rc.ID_PROGETTO,
           bli.NOME_BANDO_LINEA,
           tp.CODICE_VISUALIZZATO,
           dca.ID_CATEG_ANAGRAFICA,
           dca.DESC_CATEG_ANAGRAFICA,
           teg.DENOMINAZIONE_ENTE_GIURIDICO,
           tc.TIPO_CONTROLLI,
           NULL AS ID_IRREGOLARITA,
           NULL AS ID_IRREGOLARITA_COLLEG_PROVV,
           NULL AS DATA_INIZIO_CONTROLLI,
           NULL AS DATA_FINE_CONTROLLI,
           NULL AS ID_IRREGOLARITA_PROVV,
           NULL AS  DATA_INIZIO_CONTROLLI_PROVV,
           NULL AS DATA_FINE_CONTROLLI_PROVV,
           NULL AS  IRREGOLARITA_ANNULLATA_PROVV,
           NULL AS IMPORTO_IRREGOLARITA_PROVV,
           NULL AS  IMPORTO_AGEVOL_IRREG_PROVV,
           NULL AS  IMPORTO_IRREG_CERTIF_PROVV,
           NULL AS  DESC_MOTIVO_REVOCA_PROVV,
           NULL AS IMPORTO_IRREGOLARITA,
           NULL AS IMPORTO_AGEVOLAZIONE_IRREG,
           NULL AS QUOTA_IMP_IRREG_CERTIFICATO,
           NULL AS DESC_MOTIVO_REVOCA,
           NULL AS NUMERO_IMS,
           NULL AS TIPO_PROVVEDIMENTO,
           NULL AS ESTREMI,
           NULL AS IMPORTO,
           NULL AS IMPORTO_RECUPERO,
           NULL AS DATA_RECUPERO,
           NULL AS DESC_MANCATO_RECUPERO,
           tec.ID_ESITO_CONTROLLO AS ID_ESITO_CONTROLLO, --mc
           tec.DATA_INIZIO_CONTROLLI AS  DT_INIZIO_CONTROLLI_REGOLARI, --mc
           tec.DATA_FINE_CONTROLLI AS  DT_FINE_CONTROLLI_REGOLARI, --mc
           NULL AS NOTE_IRREGOLARITA, -- mc
           NULL AS   NOTE_IRREGOLARITA_PROVVISORIA, -- mc
           tec.NOTE AS NOTE_ESITO_REGOLARE --mc
      FROM PBANDI_T_CAMPIONAMENTO tc,
           PBANDI_R_CAMPIONAMENTO rc,
           PBANDI_T_PROGETTO tp,
           PBANDI_R_BANDO_LINEA_INTERVENT bli,
           PBANDI_T_DOMANDA td,
           PBANDI_T_PERIODO ptp,
           PBANDI_D_CATEG_ANAGRAFICA dca,
           PBANDI_R_SOGGETTO_PROGETTO rsp,
           PBANDI_T_ENTE_GIURIDICO teg,
           PBANDI_T_SOGGETTO ts,
           PBANDI_T_ESITO_CONTROLLI tec
     WHERE  tc.ID_CAMPIONE = rc.ID_CAMPIONE
           AND rc.ID_PROGETTO = tp.ID_PROGETTO
           AND tp.ID_PROGETTO = tec.ID_PROGETTO -- mc
           AND tc.ID_PERIODO = ptp.ID_PERIODO
           AND tc.ID_CATEG_ANAGRAFICA = dca.ID_CATEG_ANAGRAFICA
           AND tp.ID_PROGETTO = rsp.ID_PROGETTO
           AND rsp.ID_ENTE_GIURIDICO = teg.ID_ENTE_GIURIDICO
           AND ts.ID_SOGGETTO = teg.id_SOGGETTO
           AND rsp.ID_TIPO_ANAGRAFICA = 1
           AND rsp.ID_TIPO_BENEFICIARIO <> 4
           AND tp.ID_DOMANDA = td.ID_DOMANDA
           AND td.PROGR_BANDO_LINEA_INTERVENTO =
                  bli.PROGR_BANDO_LINEA_INTERVENTO
           AND tc.id_categ_anagrafica = tec.id_categ_anagrafica -- mc
           AND tc.id_periodo = tec.id_periodo -- mc
           AND tc.tipo_controlli = tec.tipo_controlli
       UNION
       -- tutti i progetti senza esiti
       (SELECT DISTINCT
           ptp.ID_PERIODO,
           ptp.DESC_PERIODO_VISUALIZZATA,
           -- tc.ID_CAMPIONE,
           -- id normativa
           (    SELECT ID_LINEA_DI_INTERVENTO
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 1 --  NORMATIVA LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              ID_NORMATIVA,
           -- normativa
           (    SELECT DESC_BREVE_LINEA
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 1 --  NORMATIVA LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              NORMATIVA,
           -- id asse
           (    SELECT ID_LINEA_DI_INTERVENTO
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 2 --  ASSE LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              ID_ASSE,
           --asse
           (    SELECT DESC_BREVE_LINEA || '-' || DESC_LINEA
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 2 --  ASSE LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              ASSE,
           -- id misura
           (    SELECT ID_LINEA_DI_INTERVENTO
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 3 --  MISURA LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              ID_MISURA,
           --misura
           (    SELECT DESC_BREVE_LINEA || '-' || DESC_LINEA
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 3 --  MISURA LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              MISURA,
           --id linea
           (    SELECT ID_LINEA_DI_INTERVENTO
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 4 --  LINEA LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              ID_AZIONE,
           -- linea
           (    SELECT DESC_BREVE_LINEA || '-' || DESC_LINEA
                  FROM PBANDI_D_LINEA_DI_INTERVENTO
                 WHERE ID_TIPO_LINEA_INTERVENTO = 4 --  LINEA LEGATA AL PROGETTO
            CONNECT BY PRIOR id_linea_di_intervento_padre =
                          id_linea_di_intervento
            START WITH id_linea_di_intervento =
                          (SELECT c.id_linea_di_intervento
                             FROM PBANDI_T_PROGETTO a,
                                  PBANDI_T_DOMANDA b,
                                  PBANDI_R_BANDO_LINEA_INTERVENT c
                            WHERE a.id_progetto = rc.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              AZIONE,
           bli.PROGR_BANDO_LINEA_INTERVENTO,
           rc.ID_PROGETTO,
           bli.NOME_BANDO_LINEA,
           tp.CODICE_VISUALIZZATO,
           dca.ID_CATEG_ANAGRAFICA,
           dca.DESC_CATEG_ANAGRAFICA,
           teg.DENOMINAZIONE_ENTE_GIURIDICO,
           tc.TIPO_CONTROLLI,
           NULL AS ID_IRREGOLARITA,
           NULL AS ID_IRREGOLARITA_COLLEG_PROVV,
           NULL AS DATA_INIZIO_CONTROLLI,
           NULL AS DATA_FINE_CONTROLLI,
           NULL AS ID_IRREGOLARITA_PROVV,
           NULL AS  DATA_INIZIO_CONTROLLI_PROVV,
           NULL AS DATA_FINE_CONTROLLI_PROVV,
           NULL AS  IRREGOLARITA_ANNULLATA_PROVV,
           NULL AS IMPORTO_IRREGOLARITA_PROVV,
           NULL AS  IMPORTO_AGEVOL_IRREG_PROVV,
           NULL AS  IMPORTO_IRREG_CERTIF_PROVV,
           NULL AS  DESC_MOTIVO_REVOCA_PROVV,
           NULL AS IMPORTO_IRREGOLARITA,
           NULL AS IMPORTO_AGEVOLAZIONE_IRREG,
           NULL AS QUOTA_IMP_IRREG_CERTIFICATO,
           NULL AS DESC_MOTIVO_REVOCA,
           NULL AS NUMERO_IMS,
           NULL AS TIPO_PROVVEDIMENTO,
           NULL AS ESTREMI,
           NULL AS IMPORTO,
           NULL AS IMPORTO_RECUPERO,
           NULL AS DATA_RECUPERO,
           NULL AS DESC_MANCATO_RECUPERO,
           NULL AS ID_ESITO_CONTROLLO, 
           NULL AS DT_INIZIO_CONTROLLI_REGOLARI, 
           NULL AS DT_FINE_CONTROLLI_REGOLARI, 
           NULL AS NOTE_IRREGOLARITA, 
           NULL AS NOTE_IRREGOLARITA_PROVVISORIA, 
           NULL AS NOTE_ESITO_REGOLARE 
      FROM PBANDI_T_CAMPIONAMENTO tc,
           PBANDI_R_CAMPIONAMENTO rc,
           PBANDI_T_PROGETTO tp,
           PBANDI_R_BANDO_LINEA_INTERVENT bli,
           PBANDI_T_DOMANDA td,
           PBANDI_T_PERIODO ptp,
           PBANDI_D_CATEG_ANAGRAFICA dca,
           PBANDI_R_SOGGETTO_PROGETTO rsp,
           PBANDI_T_ENTE_GIURIDICO teg,
           PBANDI_T_SOGGETTO ts
     WHERE  tc.ID_CAMPIONE = rc.ID_CAMPIONE
           AND rc.ID_PROGETTO = tp.ID_PROGETTO           
           AND tc.ID_PERIODO = ptp.ID_PERIODO
           AND tc.ID_CATEG_ANAGRAFICA = dca.ID_CATEG_ANAGRAFICA
           AND tp.ID_PROGETTO = rsp.ID_PROGETTO
           AND rsp.ID_ENTE_GIURIDICO = teg.ID_ENTE_GIURIDICO
           AND ts.ID_SOGGETTO = teg.id_SOGGETTO
           AND rsp.ID_TIPO_ANAGRAFICA = 1
           AND rsp.ID_TIPO_BENEFICIARIO <> 4
           AND tp.ID_DOMANDA = td.ID_DOMANDA
           AND td.PROGR_BANDO_LINEA_INTERVENTO =
               bli.PROGR_BANDO_LINEA_INTERVENTO
           AND (rc.ID_PROGETTO NOT IN (SELECT rc1.ID_PROGETTO 
                                      FROM   PBANDI_T_CAMPIONAMENTO tc1,
                                             PBANDI_R_CAMPIONAMENTO rc1,
                                             PBANDI_T_IRREGOLARITA_PROVV tip1 
                                      WHERE  tc1.id_campione = rc1.id_campione 
                                      AND    rc1.id_progetto = tip1.Id_Progetto                      
                                      AND    tc1.id_categ_anagrafica = tip1.id_categ_anagrafica 
                                      AND    tc1.id_periodo = tip1.id_periodo 
                                      AND    tc1.tipo_controlli = tip1.tipo_controlli)
                 OR
                rc.ID_PROGETTO NOT IN (SELECT rc1.ID_PROGETTO 
                                      FROM   PBANDI_T_CAMPIONAMENTO tc1,
                                             PBANDI_R_CAMPIONAMENTO rc1,
                                             PBANDI_T_IRREGOLARITA  ti1 
                                      WHERE  tc1.id_campione = rc1.id_campione
                                      AND    rc1.id_progetto = ti1.Id_Progetto
                                      AND    tc1.id_categ_anagrafica = ti1.id_categ_anagrafica 
                                      AND    tc1.id_periodo = ti1.id_periodo 
                                      AND    tc1.tipo_controlli = ti1.tipo_controlli)
                 OR                 
                rc.ID_PROGETTO NOT IN (SELECT rc1.ID_PROGETTO 
                                      FROM   PBANDI_T_CAMPIONAMENTO tc1,
                                             PBANDI_R_CAMPIONAMENTO rc1,
                                             PBANDI_T_ESITO_CONTROLLI tec1
                                      WHERE  tc1.id_campione = rc1.id_campione
                                      AND    rc1.id_progetto = tec1.Id_Progetto
                                      AND    tc1.id_categ_anagrafica = tec1.id_categ_anagrafica 
                                      AND    tc1.id_periodo =          tec1.id_periodo 
                                      AND    tc1.tipo_controlli =      tec1.tipo_controlli))));
-- SEQUENCE
CREATE SEQUENCE SEQ_PBANDI_T_CAMPIONAMENTO
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;
--
CREATE SEQUENCE SEQ_PBANDI_W_CAMPIONAMENTO
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;
--
CREATE SEQUENCE SEQ_PBANDI_T_REVOCA
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;
--  
CREATE SEQUENCE SEQ_PBANDI_T_ESITO_CONTROLLI
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;           
  
-------------------

