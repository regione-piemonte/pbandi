/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


--PBANDI_D_TIPOL_VOCE_DI_SPESA su PBANDI_RW
--Ricordarsi per il prossimo rilascio di eliminarla

--
ALTER TABLE PBANDI_D_ALIQUOTA_RITENUTA ADD
(COD_ONERE  Varchar2(10),
 COD_NATURA_ONERE  Varchar2(10),
 DESC_NATURA_ONERE  Varchar2(50),
 PERC_CARICO_ENTE  Number(10,2),
 PERC_CARICO_SOGGETTO  Number(10,2),
 QUADRO_770  Varchar2(10)
);
--
ALTER TABLE PBANDI_D_ALIQUOTA_RITENUTA MODIFY DESC_ALIQUOTA VARCHAR2(1000);
--
ALTER TABLE PBANDI_T_ATTO_LIQUIDAZIONE ADD IMPORTO_IMPONIBILE NUMBER(10,2);


-- PBANDI_T_IRREGOLARITA_PROVV
ALTER TABLE PBANDI_T_IRREGOLARITA_PROVV ADD ID_ESITO_CONTROLLO INTEGER;
-- FK
ALTER TABLE PBANDI_T_IRREGOLARITA_PROVV ADD
CONSTRAINT  FK_PBANDI_T_ESITO_CONTROLLI_01
FOREIGN KEY (ID_ESITO_CONTROLLO) 
REFERENCES  PBANDI_T_ESITO_CONTROLLI (ID_ESITO_CONTROLLO);
-- INDEX
CREATE INDEX IE5_PBANDI_T_IRREG_PROVV ON PBANDI_T_IRREGOLARITA_PROVV (ID_ESITO_CONTROLLO) TABLESPACE PBANDI_IDX;

-- PBANDI_T_CAMPIONAMENTO
ALTER TABLE PBANDI_T_CAMPIONAMENTO ADD
(DATA_INSERIMENTO DATE,
 ID_UTENTE_INS    NUMBER(8),
 DATA_MODIFICA 	  DATE,
 ID_UTENTE_AGG	  NUMBER(8)
);

ALTER TABLE    PBANDI_T_CAMPIONAMENTO
ADD CONSTRAINT FK_PBANDI_T_UTENTE_294
FOREIGN KEY    (ID_UTENTE_INS)
REFERENCES     PBANDI_T_UTENTE (ID_UTENTE);

ALTER TABLE    PBANDI_T_CAMPIONAMENTO
ADD CONSTRAINT FK_PBANDI_T_UTENTE_295
FOREIGN KEY    (ID_UTENTE_AGG)
REFERENCES     PBANDI_T_UTENTE (ID_UTENTE);

--INDEX
CREATE INDEX IE_PBANDI_T_CAMPIONAMENTO_05 ON PBANDI_T_CAMPIONAMENTO (ID_UTENTE_INS) TABLESPACE PBANDI_IDX;
CREATE INDEX IE_PBANDI_T_CAMPIONAMENTO_06 ON PBANDI_T_CAMPIONAMENTO (ID_UTENTE_AGG) TABLESPACE PBANDI_IDX;



-- CULTURA
--
ALTER TABLE  PBANDI_T_BANDO ADD FLAG_MACRO_VOCE_SPESA NUMBER;

ALTER TABLE PBANDI_T_BANDO ADD (
  CONSTRAINT CHK_FLAG_MACRO_VOCE_SPESA
  CHECK (FLAG_MACRO_VOCE_SPESA IN (-1)));
  
COMMENT ON COLUMN PBANDI_T_BANDO.FLAG_MACRO_VOCE_SPESA IS '-1=Non viene considerata la macrovoce nella configurazione delle voci di spesa';

-- PBANDI_D_VOCE_DI_ENTRATA 
CREATE TABLE PBANDI_D_VOCE_DI_ENTRATA
 (
  ID_VOCE_DI_ENTRATA NUMBER(8) NOT NULL,
  DESCRIZIONE_BREVE VARCHAR2(20) NOT NULL,
  DESCRIZIONE VARCHAR2(200) NOT NULL,
  INDICAZIONI VARCHAR2(200),
  FLAG_EDIT  VARCHAR2(1),
  FLAG_RISORSE_PROPRIE NUMBER(1)  DEFAULT 0 NOT NULL,
  DT_INIZIO_VALIDITA DATE,
  DT_FINE_VALIDITA DATE
  )
TABLESPACE PBANDI_TBL;

COMMENT ON TABLE  PBANDI_D_VOCE_DI_ENTRATA IS 'Tabella voci di entrata';
COMMENT ON COLUMN PBANDI_D_VOCE_DI_ENTRATA.FLAG_EDIT IS 'S=Deve essere imputato il campo previsto per il valore della colonna "INDICAZIONI" che dovrà essere definita a livello di Conto Economico (Rigo Conto Economico)';
COMMENT ON COLUMN PBANDI_D_VOCE_DI_ENTRATA.FLAG_RISORSE_PROPRIE IS '0=No risorsa propria, 1=Risorsa propria';

 --PK
ALTER TABLE PBANDI_D_VOCE_DI_ENTRATA  ADD 
(CONSTRAINT PK_PBANDI_D_VOCE_DI_ENTRATA  PRIMARY KEY (ID_VOCE_DI_ENTRATA)
USING INDEX TABLESPACE PBANDI_IDX);

-- CK
ALTER TABLE PBANDI_D_VOCE_DI_ENTRATA ADD (
  CONSTRAINT CHK_FLAG_EDIT
  CHECK (FLAG_EDIT IN ('S')));

ALTER TABLE PBANDI_D_VOCE_DI_ENTRATA ADD (
  CONSTRAINT CHK_FLAG_RISORSE_PROPRIE
  CHECK (FLAG_RISORSE_PROPRIE IN (0,1)));

CREATE SEQUENCE SEQ_PBANDI_D_VOCE_DI_ENTRATA
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;

-- PBANDI_R_BANDO_VOCE_ENTRATA
CREATE TABLE PBANDI_R_BANDO_VOCE_ENTRATA
 (
  ID_BANDO NUMBER(8)  NOT NULL,
  ID_VOCE_DI_ENTRATA NUMBER(8) NOT NULL,
  ID_UTENTE_INS NUMBER(8)  NOT NULL,
  ID_UTENTE_AGG NUMBER(8)
  )
TABLESPACE PBANDI_TBL;

COMMENT ON TABLE  PBANDI_R_BANDO_VOCE_ENTRATA IS 'Tabella associazione Bando - Voci di entrata';

--PK
ALTER TABLE PBANDI_R_BANDO_VOCE_ENTRATA  ADD 
(CONSTRAINT PK_PBANDI_R_BANDO_VOCE_ENTRATA  PRIMARY KEY (ID_BANDO,ID_VOCE_DI_ENTRATA)
USING INDEX TABLESPACE PBANDI_IDX);

--FK
ALTER TABLE PBANDI_R_BANDO_VOCE_ENTRATA ADD (
  CONSTRAINT FK_PBANDI_D_VOCE_DI_ENTRATA_01
  FOREIGN KEY (ID_VOCE_DI_ENTRATA) 
  REFERENCES PBANDI_D_VOCE_DI_ENTRATA (ID_VOCE_DI_ENTRATA),
  CONSTRAINT FK_PBANDI_T_BANDO_10
  FOREIGN KEY (ID_BANDO) 
  REFERENCES PBANDI_T_BANDO (ID_BANDO),
  CONSTRAINT FK_PBANDI_T_UTENTE_266 
  FOREIGN KEY (ID_UTENTE_INS) 
  REFERENCES PBANDI_T_UTENTE (ID_UTENTE),
  CONSTRAINT FK_PBANDI_T_UTENTE_267
  FOREIGN KEY (ID_UTENTE_AGG) 
  REFERENCES PBANDI_T_UTENTE (ID_UTENTE)
  ON DELETE SET NULL);
  
-- INDEX  
CREATE INDEX IE1_PBANDI_R_BANDO_VOCE_ENTRAT ON PBANDI_R_BANDO_VOCE_ENTRATA
(ID_VOCE_DI_ENTRATA)
TABLESPACE PBANDI_IDX;
  
CREATE INDEX IE2_PBANDI_R_BANDO_VOCE_ENTRAT ON PBANDI_R_BANDO_VOCE_ENTRATA
(ID_UTENTE_INS)
TABLESPACE PBANDI_IDX;

CREATE INDEX IE3_PBANDI_R_BANDO_VOCE_ENTRAT ON PBANDI_R_BANDO_VOCE_ENTRATA
(ID_UTENTE_AGG)
TABLESPACE PBANDI_IDX;    
  
  
  
-- PBANDI_D_TIPOL_VOCE_DI_SPESA 
CREATE TABLE PBANDI_D_TIPOL_VOCE_DI_SPESA
 (
  ID_TIPOLOGIA_VOCE_DI_SPESA  NUMBER(3)  NOT NULL,
  DESCRIZIONE VARCHAR2(200) NULL,
  PERC_QUOTA_CONTRIBUTO  NUMBER(5,2)
  )
TABLESPACE PBANDI_TBL;
 
COMMENT ON TABLE  PBANDI_D_TIPOL_VOCE_DI_SPESA IS 'Tipologia voce di spesa';

 --PK
ALTER TABLE PBANDI_D_TIPOL_VOCE_DI_SPESA  ADD 
(CONSTRAINT PK_PBANDI_D_TIPOL_VOCE_SPESA  PRIMARY KEY (ID_TIPOLOGIA_VOCE_DI_SPESA)
USING INDEX TABLESPACE PBANDI_IDX); 

--PBANDI_D_VOCE_DI_SPESA
COMMENT ON COLUMN PBANDI_D_VOCE_DI_SPESA.ID_TIPOLOGIA_VOCE_DI_SPESA IS 'La colonna verrà valorizzata per le voci di spesa dei Bandi Cultura';
COMMENT ON COLUMN PBANDI_D_VOCE_DI_SPESA.FLAG_EDIT IS 'S=Deve essere imputato il campo previsto per il valore della colonna "INDICAZIONI" che dovrà essere definita a livello di Conto Economico (Rigo Conto Economico)';

--FK
ALTER TABLE PBANDI_D_VOCE_DI_SPESA ADD (
  CONSTRAINT FK_PBANDI_D_TIPOL_VOC_SPESA_01
  FOREIGN KEY (ID_TIPOLOGIA_VOCE_DI_SPESA) 
  REFERENCES PBANDI_D_TIPOL_VOCE_DI_SPESA (ID_TIPOLOGIA_VOCE_DI_SPESA));

-- INDEX  
CREATE INDEX IE1_PBANDI_D_VOCE_DI_SPESA ON PBANDI_D_VOCE_DI_SPESA
(ID_TIPOLOGIA_VOCE_DI_SPESA)
TABLESPACE PBANDI_IDX;

--PBANDI_T_DOMANDA
ALTER TABLE PBANDI_T_DOMANDA ADD ID_ALIQUOTA_RITENUTA   NUMBER(3);
--FK
ALTER TABLE PBANDI_T_DOMANDA ADD (
  CONSTRAINT FK_PBANDI_D_ALIQUOTA_RITENU_02
  FOREIGN KEY (ID_ALIQUOTA_RITENUTA) 
  REFERENCES PBANDI_D_ALIQUOTA_RITENUTA (ID_ALIQUOTA_RITENUTA));
  
COMMENT ON COLUMN PBANDI_T_DOMANDA.ID_ALIQUOTA_RITENUTA IS 'Ritenuta IRES';

--PBANDI_T_PROGETTO
ALTER TABLE PBANDI_T_PROGETTO ADD ID_ALIQUOTA_RITENUTA   NUMBER(3);
--FK
ALTER TABLE PBANDI_T_PROGETTO ADD (
  CONSTRAINT FK_PBANDI_D_ALIQUOTA_RITENU_03
  FOREIGN KEY (ID_ALIQUOTA_RITENUTA) 
  REFERENCES PBANDI_D_ALIQUOTA_RITENUTA (ID_ALIQUOTA_RITENUTA));
  
CREATE INDEX IE9_PBANDI_T_PROGETTO ON PBANDI_T_PROGETTO
(ID_ALIQUOTA_RITENUTA)
TABLESPACE PBANDI_IDX;

COMMENT ON COLUMN PBANDI_T_PROGETTO.ID_ALIQUOTA_RITENUTA IS 'Ritenuta IRES';


--PBANDI_T_RIGO_CONTO_ECONOMICO
ALTER TABLE PBANDI_T_RIGO_CONTO_ECONOMICO MODIFY ID_VOCE_DI_SPESA NULL;

ALTER TABLE PBANDI_T_RIGO_CONTO_ECONOMICO ADD 
(ID_VOCE_DI_ENTRATA   NUMBER(8),
 COMPLETAMENTO VARCHAR2(200));
COMMENT ON COLUMN PBANDI_T_RIGO_CONTO_ECONOMICO.ID_VOCE_DI_ENTRATA IS 'La colonna verrà valorizzata per il conto economico dei Bandi Cultura';
COMMENT ON COLUMN PBANDI_T_RIGO_CONTO_ECONOMICO.COMPLETAMENTO IS 'Le voci di entrata che nel file sono marcate con FLAG_EDIT = ‘S’ prevedono un’informazione di completamento, di tipo descrittivo a valorizzazione libera';

--FK
ALTER TABLE PBANDI_T_RIGO_CONTO_ECONOMICO ADD (
  CONSTRAINT FK_PBANDI_D_VOCE_DI_ENTRATA_02
  FOREIGN KEY (ID_VOCE_DI_ENTRATA) 
  REFERENCES PBANDI_D_VOCE_DI_ENTRATA (ID_VOCE_DI_ENTRATA));
  
-- INDEX  
CREATE INDEX IE_PBANDI_T_RIGO_CONTO_ECON_02 ON PBANDI_T_RIGO_CONTO_ECONOMICO
(ID_VOCE_DI_ENTRATA)
TABLESPACE PBANDI_IDX;


ALTER TABLE PBANDI_T_QUOTA_PARTE_DOC_SPESA ADD 
(IMP_VALIDATO   NUMBER(17,2));
COMMENT ON COLUMN PBANDI_T_QUOTA_PARTE_DOC_SPESA.IMP_VALIDATO IS 'La colonna verrà valorizzata per il validato dei Bandi Cultura';

-- PBANDI_R_PAG_QTCL_PARTE_DOC_SP
CREATE TABLE PBANDI_R_PAG_QTCL_PARTE_DOC_SP
 (
  ID_PAGAMENTO NUMBER(8)  NOT NULL,
  ID_QUOTA_PARTE_DOC_SPESA NUMBER(8) NOT NULL,
  ID_UTENTE_INS NUMBER(8)  NOT NULL,
  ID_UTENTE_AGG NUMBER(8)
  )
TABLESPACE PBANDI_TBL;

COMMENT ON TABLE  PBANDI_R_PAG_QTCL_PARTE_DOC_SP IS 'Per Beneficiari Pubblici, i pagamenti (atti di liquidazione) verranno associati direttamente alle voci di spesa';

--PK
ALTER TABLE PBANDI_R_PAG_QTCL_PARTE_DOC_SP  ADD 
(CONSTRAINT PK_PBANDI_R_PAG_QTCL_PT_DOC_SP  PRIMARY KEY (ID_PAGAMENTO,ID_QUOTA_PARTE_DOC_SPESA)
USING INDEX TABLESPACE PBANDI_IDX);

--FK
ALTER TABLE PBANDI_R_PAG_QTCL_PARTE_DOC_SP ADD (
  CONSTRAINT FK_PBANDI_T_PAGAMENTO_04
  FOREIGN KEY (ID_PAGAMENTO) 
  REFERENCES PBANDI_T_PAGAMENTO (ID_PAGAMENTO),
  CONSTRAINT FK_PBANDI_T_QT_PARTE_DOC_SP_02
  FOREIGN KEY (ID_QUOTA_PARTE_DOC_SPESA) 
  REFERENCES PBANDI_T_QUOTA_PARTE_DOC_SPESA (ID_QUOTA_PARTE_DOC_SPESA),
  CONSTRAINT FK_PBANDI_T_UTENTE_268 
  FOREIGN KEY (ID_UTENTE_INS) 
  REFERENCES PBANDI_T_UTENTE (ID_UTENTE),
  CONSTRAINT FK_PBANDI_T_UTENTE_269
  FOREIGN KEY (ID_UTENTE_AGG) 
  REFERENCES PBANDI_T_UTENTE (ID_UTENTE)
  ON DELETE SET NULL);
  
 CREATE INDEX IE1_PBANDI_R_PAG_QTCL_PT_DC_SP ON PBANDI_R_PAG_QTCL_PARTE_DOC_SP
(ID_QUOTA_PARTE_DOC_SPESA)
TABLESPACE PBANDI_IDX;

--PBANDI_T_RETTIFICA_QUOTA_PARTE
CREATE TABLE PBANDI_T_RETTIFICA_QUOTA_PARTE
(
  ID_RETTIFICA_QUOTA_PARTE     NUMBER(8)        NOT NULL,
  DT_RETTIFICA                 DATE             NOT NULL,
  IMPORTO_RETTIFICA            NUMBER(15,2)     NOT NULL,
  RIFERIMENTO                  VARCHAR2(255),
  ID_QUOTA_PARTE_DOC_SPESA               NUMBER(8)        NOT NULL,
  ID_UTENTE_INS                NUMBER(8)        NOT NULL,
  ID_UTENTE_AGG                NUMBER(8)
)
TABLESPACE PBANDI_TBL;

COMMENT ON TABLE  PBANDI_T_RETTIFICA_QUOTA_PARTE IS 'Rettifiche di spesa per bandi Cultura';


ALTER TABLE PBANDI_T_RETTIFICA_QUOTA_PARTE ADD (
  CONSTRAINT PK_PBANDI_T_RETTIF_QUOTA_PARTE
  PRIMARY KEY
  (ID_RETTIFICA_QUOTA_PARTE)
  USING INDEX TABLESPACE PBANDI_IDX);

ALTER TABLE PBANDI_T_RETTIFICA_QUOTA_PARTE ADD (
  CONSTRAINT FK_PBANDI_T_QT_PARTE_DOC_SP_03 
  FOREIGN KEY (ID_QUOTA_PARTE_DOC_SPESA) 
  REFERENCES PBANDI_T_QUOTA_PARTE_DOC_SPESA (ID_QUOTA_PARTE_DOC_SPESA),
  CONSTRAINT FK_PBANDI_T_UTENTE_270 
  FOREIGN KEY (ID_UTENTE_INS) 
  REFERENCES PBANDI_T_UTENTE (ID_UTENTE),
  CONSTRAINT FK_PBANDI_T_UTENTE_271 
  FOREIGN KEY (ID_UTENTE_AGG) 
  REFERENCES PBANDI_T_UTENTE (ID_UTENTE));

CREATE INDEX IE1_PBANDI_T_RETT_QUOTA_PARTE ON PBANDI_T_RETTIFICA_QUOTA_PARTE
(ID_QUOTA_PARTE_DOC_SPESA)
TABLESPACE PBANDI_IDX;

--PBANDI_T_CONTO_ECONOMICO
ALTER TABLE PBANDI_T_CONTO_ECONOMICO ADD 
(PERC_SP_GEN_FUNZ   NUMBER(5,2));

--PBANDI_T_PREVIEW_DETT_PROP_CER
ALTER TABLE PBANDI_T_PREVIEW_DETT_PROP_CER ADD ID_PROGETTO_SIF NUMBER(8);

COMMENT ON COLUMN PBANDI_T_PREVIEW_DETT_PROP_CER.ID_PROGETTO_SIF IS 'ID del progetto SIF al quale il progetto percettore corrente afferisce';

--FK
ALTER TABLE PBANDI_T_PREVIEW_DETT_PROP_CER ADD (
  CONSTRAINT FK_PBANDI_T_PROGETTO_56
  FOREIGN KEY (ID_PROGETTO_SIF) 
  REFERENCES PBANDI_T_PROGETTO (ID_PROGETTO));
  

--PBANDI_T_PREVIEW_DETT_PROP_REV
ALTER TABLE PBANDI_T_PREVIEW_DETT_PROP_REV ADD ID_PROGETTO_SIF NUMBER(8);

COMMENT ON COLUMN PBANDI_T_PREVIEW_DETT_PROP_REV.ID_PROGETTO_SIF IS 'ID del progetto SIF al quale il progetto percettore corrente afferisce';

--FK
ALTER TABLE PBANDI_T_PREVIEW_DETT_PROP_REV ADD (
  CONSTRAINT FK_PBANDI_T_PROGETTO_57
  FOREIGN KEY (ID_PROGETTO_SIF) 
  REFERENCES PBANDI_T_PROGETTO (ID_PROGETTO));
  

--PBANDI_T_DETT_PROPOSTA_CERTIF
ALTER TABLE PBANDI_T_DETT_PROPOSTA_CERTIF ADD ID_PROGETTO_SIF NUMBER(8);

COMMENT ON COLUMN PBANDI_T_DETT_PROPOSTA_CERTIF.ID_PROGETTO_SIF IS 'ID del progetto SIF al quale il progetto percettore corrente afferisce';

--FK
ALTER TABLE PBANDI_T_DETT_PROPOSTA_CERTIF ADD (
  CONSTRAINT FK_PBANDI_T_PROGETTO_58
  FOREIGN KEY (ID_PROGETTO_SIF) 
  REFERENCES PBANDI_T_PROGETTO (ID_PROGETTO));

CREATE INDEX IE3_PBANDI_T_DETT_PROPOSTA_CER ON PBANDI_T_DETT_PROPOSTA_CERTIF
(ID_PROGETTO_SIF, ID_PROPOSTA_CERTIFICAZ)
TABLESPACE PBANDI_IDX;

--PBANDI_T_DETT_PROPOSTA_REV
ALTER TABLE PBANDI_T_DETT_PROPOSTA_REV ADD ID_PROGETTO_SIF NUMBER(8);

COMMENT ON COLUMN PBANDI_T_DETT_PROPOSTA_REV.ID_PROGETTO_SIF IS 'ID del progetto SIF al quale il progetto percettore corrente afferisce';

--FK
ALTER TABLE PBANDI_T_DETT_PROPOSTA_REV ADD (
  CONSTRAINT FK_PBANDI_T_PROGETTO_59
  FOREIGN KEY (ID_PROGETTO_SIF) 
  REFERENCES PBANDI_T_PROGETTO (ID_PROGETTO));

CREATE INDEX IE3_PBANDI_T_DETT_PROPOSTA_REV ON PBANDI_T_DETT_PROPOSTA_REV
(ID_PROGETTO_SIF, ID_PROPOSTA_CERTIFICAZ)
TABLESPACE PBANDI_IDX;

--Type
DROP TYPE LISTCREACERTIF;

CREATE OR REPLACE TYPE OBJCREACERTIF AS OBJECT
(
ID_DETT_PROPOSTA_CERTIF NUMBER(8),
ID_PROPOSTA_CERTIFICAZ NUMBER(8),
COSTO_AMMESSO NUMBER(13,2),
IMPORTO_PAGAMENTI_VALIDATI  NUMBER(13,2),
IMPORTO_PAG_VALIDATI_ORIG  NUMBER(13,2),
IMPORTO_EROGAZIONI NUMBER(13,2),
IMPORTO_FIDEIUSSIONI NUMBER(13,2),
SPESA_CERTIFICABILE_LORDA NUMBER(13,2),
ID_PROGETTO NUMBER(8),
ID_UTENTE_INS NUMBER(8),
ID_UTENTE_AGG NUMBER(8),
IMPORTO_ECCENDENZE_VALIDAZIONE NUMBER(13,2),
TITOLO_PROGETTO VARCHAR2(2000),
NOME_BANDO_LINEA VARCHAR2(255),
FLAG_CHECK_LIST_IN_LOCO VARCHAR(1),
FLAG_CHECK_LIST_CERTIFICAZIONE VARCHAR(1),
ID_STATO_PROGETTO NUMBER(3),
ID_TIPO_OPERAZIONE  NUMBER(3),
DT_ULTIMA_CHECKLIST_IN_LOCO DATE,
IMPORTO_RENDICONTATO NUMBER(17,2),
FLAG_ATTIVO VARCHAR(1),
ID_LINEA_DI_INTERVENTO NUMBER(3),
ID_PROGETTO_SIF NUMBER(8)
)
/

CREATE OR REPLACE TYPE LISTCREACERTIF AS TABLE OF OBJCREACERTIF
/

--VIEW
CREATE OR REPLACE FORCE VIEW PBANDI_V_PROGETTI_BANDO
(
   PROGR_BANDO_LINEA_INTERVENTO,
   ID_BANDO,
   ID_LINEA_DI_INTERVENTO,
   ID_PROGETTO,
   CODICE_PROGETTO,
   CODICE_VISUALIZZATO,
   DT_AGGIORNAMENTO,
   ID_DOMANDA,
   NUMERO_DOMANDA,
   ID_PROGETTO_FINANZ
)
AS
   SELECT c.progr_bando_linea_intervento,
          c.id_bando,
          c.id_linea_di_intervento,
          a.id_progetto,
		  a.codice_progetto,
          a.codice_visualizzato,
          a.dt_aggiornamento,
          b.id_domanda,
          b.numero_domanda,
		  a.id_progetto_finanz
     FROM PBANDI_T_PROGETTO a,
          PBANDI_T_DOMANDA b,
          PBANDI_R_BANDO_LINEA_INTERVENT c
    WHERE b.id_domanda = a.id_domanda
          AND c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;

-- PBANDI_V_RILEVAZIONI
-- PBANDI_V_RILEVAZIONI
CREATE OR REPLACE VIEW PBANDI_V_RILEVAZIONI AS
WITH beneficiario AS
(SELECT     teg.DENOMINAZIONE_ENTE_GIURIDICO,
            rsp.id_progetto
 FROM       PBANDI_T_ENTE_GIURIDICO teg,
            PBANDI_R_SOGGETTO_PROGETTO rsp
 WHERE      teg.id_soggetto = rsp.id_soggetto and
            rsp.ID_TIPO_ANAGRAFICA = 1  and
            rsp.ID_TIPO_BENEFICIARIO <> 4 AND
            teg.id_ente_giuridico =
            (SELECT MAX(id_ente_giuridico)
             FROM   PBANDI_T_ENTE_GIURIDICO t
             WHERE  t.id_soggetto = teg.id_soggetto)
)
(SELECT DISTINCT
           --1 as n_query,
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
           BEN.DENOMINAZIONE_ENTE_GIURIDICO,
           ti.data_campione DATA_CAMPIONAMENTO,
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
      FROM BENEFICIARIO BEN,
           PBANDI_T_CAMPIONAMENTO tc,
           PBANDI_R_CAMPIONAMENTO rc,
           PBANDI_T_PROGETTO tp,
           PBANDI_R_BANDO_LINEA_INTERVENT bli,
           PBANDI_T_DOMANDA td,
           PBANDI_T_PERIODO ptp,
           PBANDI_D_CATEG_ANAGRAFICA dca,
           PBANDI_T_IRREGOLARITA ti,
           PBANDI_T_IRREGOLARITA_PROVV tip,
           PBANDI_D_MOTIVO_REVOCA mr,
           PBANDI_T_REVOCA tr,
           PBANDI_D_CAUSALE_DISIMPEGNO dcd,
           PBANDI_D_MANCATO_RECUPERO dmr,
           PBANDI_R_REVOCA_IRREG rri
     WHERE ben.id_progetto = tp.ID_PROGETTO
           AND ti.ID_IRREGOLARITA_PROVV = tip.ID_IRREGOLARITA_PROVV(+)
           AND tc.ID_CAMPIONE = rc.ID_CAMPIONE
           AND rc.ID_PROGETTO = tp.ID_PROGETTO
           AND tc.ID_PERIODO = ptp.ID_PERIODO
           AND tc.ID_CATEG_ANAGRAFICA = dca.ID_CATEG_ANAGRAFICA
           --AND tp.ID_PROGETTO = rsp.ID_PROGETTO
           --AND rsp.ID_ENTE_GIURIDICO = teg.ID_ENTE_GIURIDICO
           --AND ts.ID_SOGGETTO = teg.id_SOGGETTO
           --AND ts.ID_SOGGETTO = rsp.id_SOGGETTO --mc gen 2021
           --AND rsp.ID_TIPO_ANAGRAFICA = 1
           --AND rsp.ID_TIPO_BENEFICIARIO <> 4
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
           -- DA NON FAR vedere se ha una provvisoria senza definitiva, questa casistica è gestita dalla seconda union , per evitare duplicazioni
           -- DA FAR vedere se ha la definitiva con la provvisoria collegata
           -- DA FAR vedere se la definitiva e la provvisoria non sono collegate ma sono validate entrambe (data fine null)
           AND  tip.Id_Esito_Controllo IS NULL -- mc gen 2021
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
    (SELECT DISTINCT -- progetti non campionati
          --1bis as n_query,
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
                            WHERE a.id_progetto = ti.ID_PROGETTO
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
                            WHERE a.id_progetto = ti.ID_PROGETTO
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
                            WHERE a.id_progetto = ti.ID_PROGETTO
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
                            WHERE a.id_progetto = ti.ID_PROGETTO
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
                            WHERE a.id_progetto = ti.ID_PROGETTO
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
                            WHERE a.id_progetto = ti.ID_PROGETTO
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
                            WHERE a.id_progetto = ti.ID_PROGETTO
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
                            WHERE a.id_progetto = ti.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              AZIONE,
           bli.PROGR_BANDO_LINEA_INTERVENTO,
           NVL(ti.ID_PROGETTO,tip.id_progetto) ID_PROGETTO,
           bli.NOME_BANDO_LINEA,
           tp.CODICE_VISUALIZZATO,
           dca.ID_CATEG_ANAGRAFICA,
           dca.DESC_CATEG_ANAGRAFICA,
           --teg.DENOMINAZIONE_ENTE_GIURIDICO,
           BEN.DENOMINAZIONE_ENTE_GIURIDICO,
           ti.data_campione DATA_CAMPIONAMENTO,
           NVL (ti.tipo_controlli,tip.tipo_controlli) TIPO_CONTROLLI, -- tc.TIPO_CONTROLLI,
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
             WHERE trp.ID_PROGETTO = ti.ID_PROGETTO)
              IMPORTO_RECUPERO,
           (SELECT MAX (trp.DT_RECUPERO)
              FROM PBANDI_T_RECUPERO trp
             WHERE trp.ID_PROGETTO = ti.ID_PROGETTO)
              DATA_RECUPERO,
           dmr.DESC_MANCATO_RECUPERO,
           NULL AS  ID_ESITO_CONTROLLO, --mc
           NULL AS  DT_INIZIO_CONTROLLI_REGOLARI, --mc
           NULL AS  DT_FINE_CONTROLLI_REGOLARI, --mc
           ti.NOTE AS NOTE_IRREGOLARITA, -- mc
           tip.NOTE AS  NOTE_IRREGOLARITA_PROVVISORIA, -- mc
           NULL AS NOTE_ESITO_REGOLARE --mc
      FROM BENEFICIARIO BEN,
           --PBANDI_T_CAMPIONAMENTO tc,
           --PBANDI_R_CAMPIONAMENTO rc,
           PBANDI_T_PROGETTO tp,
           PBANDI_R_BANDO_LINEA_INTERVENT bli,
           PBANDI_T_DOMANDA td,
           PBANDI_T_PERIODO ptp,
           PBANDI_D_CATEG_ANAGRAFICA dca,
           --PBANDI_R_SOGGETTO_PROGETTO rsp,
           --PBANDI_T_ENTE_GIURIDICO teg,
           PBANDI_T_IRREGOLARITA ti,
           PBANDI_T_IRREGOLARITA_PROVV tip,
           PBANDI_D_MOTIVO_REVOCA mr,
           PBANDI_T_REVOCA tr,
           PBANDI_D_CAUSALE_DISIMPEGNO dcd,
           PBANDI_D_MANCATO_RECUPERO dmr,
           --PBANDI_T_SOGGETTO ts,
           --PBANDI_T_RECUPERO trec,
           PBANDI_R_REVOCA_IRREG rri
     WHERE ben.id_progetto = tp.ID_PROGETTO AND
           ti.ID_IRREGOLARITA_PROVV = tip.ID_IRREGOLARITA_PROVV(+)
           --AND tc.ID_CAMPIONE = rc.ID_CAMPIONE
           AND ti.ID_PROGETTO = tp.ID_PROGETTO --
           AND ti.ID_PERIODO = ptp.ID_PERIODO
           AND ti.ID_CATEG_ANAGRAFICA = dca.ID_CATEG_ANAGRAFICA
           --AND tp.ID_PROGETTO = rsp.ID_PROGETTO
          --AND rsp.ID_ENTE_GIURIDICO = teg.ID_ENTE_GIURIDICO
           --AND ts.ID_SOGGETTO = teg.id_SOGGETTO
           --AND ts.ID_SOGGETTO = rsp.id_SOGGETTO --mc gen 2021
           --AND rsp.ID_TIPO_ANAGRAFICA = 1
           --AND rsp.ID_TIPO_BENEFICIARIO <> 4
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
           -- AND tc.id_categ_anagrafica = ti.id_categ_anagrafica -- mc
           -- AND tc.id_periodo = ti.id_periodo -- mc
           -- AND tc.tipo_controlli = ti.tipo_controlli -- mc
           -- DA NON FAR vedere se ha una provvisoria senza definitiva, questa casistica è gestita dalla seconda union , per evitare duplicazioni
           -- DA FAR vedere se ha la definitiva con la provvisoria collegata
           -- DA FAR vedere se la definitiva e la provvisoria non sono collegate ma sono validate entrambe (data fine null)
           AND  tip.Id_Esito_Controllo IS NULL -- mc gen 2021
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
                            WHERE tip.DT_FINE_VALIDITA IS NULL)))   )
    UNION
    (SELECT DISTINCT
         -- 2 as n_query,
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
           --teg.DENOMINAZIONE_ENTE_GIURIDICO,
           BEN.DENOMINAZIONE_ENTE_GIURIDICO,
           tip.data_campione DATA_CAMPIONAMENTO, -- mc svuluppo settempre 2020
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
      FROM BENEFICIARIO BEN,
           PBANDI_T_CAMPIONAMENTO tc,
           PBANDI_R_CAMPIONAMENTO rc,
           PBANDI_T_PROGETTO tp,
           PBANDI_R_BANDO_LINEA_INTERVENT bli,
           PBANDI_T_DOMANDA td,
           PBANDI_T_PERIODO ptp,
           PBANDI_D_CATEG_ANAGRAFICA dca,
           --PBANDI_R_SOGGETTO_PROGETTO rsp,
           --PBANDI_T_ENTE_GIURIDICO teg,
           PBANDI_T_IRREGOLARITA_PROVV tip,
           PBANDI_D_MOTIVO_REVOCA mr
           --PBANDI_T_SOGGETTO ts
     WHERE ben.id_progetto = tp.ID_PROGETTO
           AND tc.ID_CAMPIONE = rc.ID_CAMPIONE
           AND rc.ID_PROGETTO = tp.ID_PROGETTO
           AND tc.ID_PERIODO = ptp.ID_PERIODO
           AND tc.ID_CATEG_ANAGRAFICA = dca.ID_CATEG_ANAGRAFICA
           --AND tp.ID_PROGETTO = rsp.ID_PROGETTO
           --AND rsp.ID_ENTE_GIURIDICO = teg.ID_ENTE_GIURIDICO
          -- AND ts.ID_SOGGETTO = teg.id_SOGGETTO
          -- AND ts.ID_SOGGETTO = rsp.id_SOGGETTO --mc gen 2021
           --AND rsp.ID_TIPO_ANAGRAFICA = 1
           --AND rsp.ID_TIPO_BENEFICIARIO <> 4
           AND tp.ID_PROGETTO = tip.ID_PROGETTO(+)
           AND tip.ID_MOTIVO_REVOCA = mr.ID_MOTIVO_REVOCA(+)
           AND tp.ID_DOMANDA = td.ID_DOMANDA
           AND td.PROGR_BANDO_LINEA_INTERVENTO =
                  bli.PROGR_BANDO_LINEA_INTERVENTO
           AND tc.id_categ_anagrafica = tip.id_categ_anagrafica -- mc
           AND tc.id_periodo = tip.id_periodo -- mc
           AND tc.tipo_controlli = tip.tipo_controlli -- mc
           AND  tip.Id_Esito_Controllo IS NULL -- mc gen 2021
           AND tip.ID_IRREGOLARITA_PROVV NOT IN
                  (SELECT ti.ID_IRREGOLARITA_PROVV
                     FROM PBANDI_T_IRREGOLARITA ti
                    WHERE     ti.DT_FINE_VALIDITA IS NULL
                          AND ID_PROGETTO = tp.ID_PROGETTO
                          AND ID_IRREGOLARITA_PROVV IS NOT NULL)
           AND tip.DT_FINE_VALIDITA IS NULL)
UNION
(SELECT DISTINCT
         -- 2bis as n_query, -- progetti non campionati
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
                            WHERE a.id_progetto = tip.ID_PROGETTO
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
                            WHERE a.id_progetto = tip.ID_PROGETTO
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
                            WHERE a.id_progetto = tip.ID_PROGETTO
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
                            WHERE a.id_progetto = tip.ID_PROGETTO
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
                            WHERE a.id_progetto = tip.ID_PROGETTO
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
                            WHERE a.id_progetto = tip.ID_PROGETTO
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
                            WHERE a.id_progetto = tip.ID_PROGETTO
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
                            WHERE a.id_progetto = tip.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              AZIONE,
           bli.PROGR_BANDO_LINEA_INTERVENTO,
           tip.ID_PROGETTO,
           bli.NOME_BANDO_LINEA,
           tp.CODICE_VISUALIZZATO,
           dca.ID_CATEG_ANAGRAFICA,
           dca.DESC_CATEG_ANAGRAFICA,
          -- teg.DENOMINAZIONE_ENTE_GIURIDICO,
           BEN.DENOMINAZIONE_ENTE_GIURIDICO,
           tip.data_campione DATA_CAMPIONAMENTO, -- mc svuluppo settempre 2020
           tip.TIPO_CONTROLLI,
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
      FROM BENEFICIARIO BEN,
           --PBANDI_T_CAMPIONAMENTO tc,
           --PBANDI_R_CAMPIONAMENTO rc,
           PBANDI_T_PROGETTO tp,
           PBANDI_R_BANDO_LINEA_INTERVENT bli,
           PBANDI_T_DOMANDA td,
           PBANDI_T_PERIODO ptp,
           PBANDI_D_CATEG_ANAGRAFICA dca,
           --PBANDI_R_SOGGETTO_PROGETTO rsp,
           --PBANDI_T_ENTE_GIURIDICO teg,
           PBANDI_T_IRREGOLARITA_PROVV tip,
           PBANDI_D_MOTIVO_REVOCA mr
           --PBANDI_T_SOGGETTO ts
     WHERE ben.id_progetto = tp.ID_PROGETTO
            --tc.ID_CAMPIONE = rc.ID_CAMPIONE
           AND tip.ID_PROGETTO = tp.ID_PROGETTO
           AND tip.ID_PERIODO = ptp.ID_PERIODO
           AND tip.ID_CATEG_ANAGRAFICA = dca.ID_CATEG_ANAGRAFICA
           --AND tp.ID_PROGETTO = rsp.ID_PROGETTO
           --AND rsp.ID_ENTE_GIURIDICO = teg.ID_ENTE_GIURIDICO
           --AND ts.ID_SOGGETTO = teg.id_SOGGETTO
           --AND ts.ID_SOGGETTO = rsp.id_SOGGETTO -- mc gen 2021
           --AND rsp.ID_TIPO_ANAGRAFICA = 1
           --AND rsp.ID_TIPO_BENEFICIARIO <> 4
           AND tp.ID_PROGETTO = tip.ID_PROGETTO(+)
           AND tip.ID_MOTIVO_REVOCA = mr.ID_MOTIVO_REVOCA(+)
           AND tp.ID_DOMANDA = td.ID_DOMANDA
           AND td.PROGR_BANDO_LINEA_INTERVENTO =
                  bli.PROGR_BANDO_LINEA_INTERVENTO
           AND tip.id_categ_anagrafica = tip.id_categ_anagrafica -- mc
           AND tip.id_periodo = tip.id_periodo -- mc
           AND tip.tipo_controlli = tip.tipo_controlli -- mc
           AND tip.Id_Esito_Controllo IS NULL -- mc gen 2021
           AND tip.ID_IRREGOLARITA_PROVV NOT IN
                  (SELECT ti.ID_IRREGOLARITA_PROVV
                     FROM PBANDI_T_IRREGOLARITA ti
                    WHERE     ti.DT_FINE_VALIDITA IS NULL
                          AND ID_PROGETTO = tp.ID_PROGETTO
                          AND ID_IRREGOLARITA_PROVV IS NOT NULL)
           AND tip.DT_FINE_VALIDITA IS NULL
)
UNION
    -- esiti_regolari
    (SELECT DISTINCT
          -- 3 as n_query,
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
           --teg.DENOMINAZIONE_ENTE_GIURIDICO,
           BEN.DENOMINAZIONE_ENTE_GIURIDICO,
           tec.data_campione DATA_CAMPIONAMENTO, -- mc svuluppo settempre 2020
           tc.TIPO_CONTROLLI,
           NULL AS ID_IRREGOLARITA,
           NULL AS ID_IRREGOLARITA_COLLEG_PROVV,
           NULL AS DATA_INIZIO_CONTROLLI,
           NULL AS DATA_FINE_CONTROLLI,
           tip.ID_IRREGOLARITA_PROVV AS ID_IRREGOLARITA_PROVV,--NULL AS ID_IRREGOLARITA_PROVV,
           tip.DATA_INIZIO_CONTROLLI AS DATA_INIZIO_CONTROLLI_PROVV, --NULL AS  DATA_INIZIO_CONTROLLI_PROVV,
           tip.DATA_FINE_CONTROLLI AS DATA_FINE_CONTROLLI_PROVV, --NULL AS DATA_FINE_CONTROLLI_PROVV,
           tip.IRREGOLARITA_ANNULLATA AS IRREGOLARITA_ANNULLATA_PROVV, --NULL AS  IRREGOLARITA_ANNULLATA_PROVV,
           tip.IMPORTO_IRREGOLARITA AS IMPORTO_IRREGOLARITA_PROVV,--NULL AS IMPORTO_IRREGOLARITA_PROVV,
           tip.IMPORTO_AGEVOLAZIONE_IRREG AS IMPORTO_AGEVOL_IRREG_PROVV,--NULL AS  IMPORTO_AGEVOL_IRREG_PROVV,
           tip.IMPORTO_IRREGOLARE_CERTIFICATO AS IMPORTO_IRREG_CERTIF_PROVV,--NULL AS  IMPORTO_IRREG_CERTIF_PROVV,
           --NULL AS  DESC_MOTIVO_REVOCA_PROVV,
           (SELECT DESC_MOTIVO_REVOCA
              FROM PBANDI_D_MOTIVO_REVOCA
             WHERE id_motivo_revoca = tip.id_motivo_revoca)
              AS DESC_MOTIVO_REVOCA_PROVV,
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
           tip.NOTE AS  NOTE_IRREGOLARITA_PROVVISORIA,--NULL AS   NOTE_IRREGOLARITA_PROVVISORIA, -- mc
           tec.NOTE AS NOTE_ESITO_REGOLARE --mc
      FROM BENEFICIARIO BEN,
           PBANDI_T_CAMPIONAMENTO tc,
           PBANDI_R_CAMPIONAMENTO rc,
           PBANDI_T_PROGETTO tp,
           PBANDI_R_BANDO_LINEA_INTERVENT bli,
           PBANDI_T_DOMANDA td,
           PBANDI_T_PERIODO ptp,
           PBANDI_D_CATEG_ANAGRAFICA dca,
           --PBANDI_R_SOGGETTO_PROGETTO rsp,
           --PBANDI_T_ENTE_GIURIDICO teg,
           --PBANDI_T_SOGGETTO ts,
           PBANDI_T_ESITO_CONTROLLI tec,
           PBANDI_T_IRREGOLARITA_PROVV tip
     WHERE tec.ID_ESITO_CONTROLLO = tip.id_esito_controllo(+) -- mc gen 2021
           AND ben.id_progetto = tp.ID_PROGETTO
           AND tc.ID_CAMPIONE = rc.ID_CAMPIONE
           AND rc.ID_PROGETTO = tp.ID_PROGETTO
           AND tp.ID_PROGETTO = tec.ID_PROGETTO -- mc
           AND tc.ID_PERIODO = ptp.ID_PERIODO
           AND tc.ID_CATEG_ANAGRAFICA = dca.ID_CATEG_ANAGRAFICA
           --AND tp.ID_PROGETTO = rsp.ID_PROGETTO
           --AND rsp.ID_ENTE_GIURIDICO = teg.ID_ENTE_GIURIDICO
           --AND ts.ID_SOGGETTO = teg.id_SOGGETTO
           --AND ts.ID_SOGGETTO = rsp.id_SOGGETTO --mc gen 2021
           --AND rsp.ID_TIPO_ANAGRAFICA = 1
           --AND rsp.ID_TIPO_BENEFICIARIO <> 4
           AND tp.ID_DOMANDA = td.ID_DOMANDA
           AND td.PROGR_BANDO_LINEA_INTERVENTO =
                  bli.PROGR_BANDO_LINEA_INTERVENTO
           AND tc.id_categ_anagrafica = tec.id_categ_anagrafica -- mc
           AND tc.id_periodo = tec.id_periodo -- mc
           AND tc.tipo_controlli = tec.tipo_controlli
       UNION
       -- tutti i progetti con esito ma non campionati
       (SELECT DISTINCT
          -- 3BIS as n_query,
           ptp.ID_PERIODO,
           ptp.DESC_PERIODO_VISUALIZZATA,
           --ID_CAMPIONE,
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
                            WHERE a.id_progetto = tec.ID_PROGETTO
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
                            WHERE a.id_progetto = tec.ID_PROGETTO
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
                            WHERE a.id_progetto = tec.ID_PROGETTO
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
                            WHERE a.id_progetto = tec.ID_PROGETTO
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
                            WHERE a.id_progetto = tec.ID_PROGETTO
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
                            WHERE a.id_progetto = tec.ID_PROGETTO
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
                            WHERE a.id_progetto = tec.ID_PROGETTO
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
                            WHERE a.id_progetto = tec.ID_PROGETTO
                                  AND b.id_domanda = a.id_domanda
                                  AND c.progr_bando_linea_intervento =
                                         b.progr_bando_linea_intervento))
              AZIONE,
           bli.PROGR_BANDO_LINEA_INTERVENTO,
           tec.ID_PROGETTO,
           bli.NOME_BANDO_LINEA,
           tp.CODICE_VISUALIZZATO,
           dca.ID_CATEG_ANAGRAFICA,
           dca.DESC_CATEG_ANAGRAFICA,
           --teg.DENOMINAZIONE_ENTE_GIURIDICO,
           BEN.DENOMINAZIONE_ENTE_GIURIDICO,
           tec.data_campione DATA_CAMPIONAMENTO, -- mc svuluppo settempre 2020
           tec.TIPO_CONTROLLI,
           NULL AS ID_IRREGOLARITA,
           NULL AS ID_IRREGOLARITA_COLLEG_PROVV,
           NULL AS DATA_INIZIO_CONTROLLI,
           NULL AS DATA_FINE_CONTROLLI,
           tip.ID_IRREGOLARITA_PROVV AS ID_IRREGOLARITA_PROVV, --NULL AS ID_IRREGOLARITA_PROVV,
           tip.DATA_INIZIO_CONTROLLI AS DATA_INIZIO_CONTROLLI_PROVV,--NULL AS  DATA_INIZIO_CONTROLLI_PROVV,
           tip.DATA_FINE_CONTROLLI AS DATA_FINE_CONTROLLI_PROVV, --NULL AS DATA_FINE_CONTROLLI_PROVV,
           tip.IRREGOLARITA_ANNULLATA AS IRREGOLARITA_ANNULLATA_PROVV,--NULL AS  IRREGOLARITA_ANNULLATA_PROVV,
           tip.IMPORTO_IRREGOLARITA AS IMPORTO_IRREGOLARITA_PROVV, --NULL AS IMPORTO_IRREGOLARITA_PROVV,
           tip.IMPORTO_AGEVOLAZIONE_IRREG AS IMPORTO_AGEVOL_IRREG_PROVV, --NULL AS  IMPORTO_AGEVOL_IRREG_PROVV,
           tip.IMPORTO_IRREGOLARE_CERTIFICATO AS IMPORTO_IRREG_CERTIF_PROVV,--NULL AS  IMPORTO_IRREG_CERTIF_PROVV,
           --NULL AS  DESC_MOTIVO_REVOCA_PROVV,
           (SELECT DESC_MOTIVO_REVOCA
              FROM PBANDI_D_MOTIVO_REVOCA
             WHERE id_motivo_revoca = tip.id_motivo_revoca)
              AS DESC_MOTIVO_REVOCA_PROVV,
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
           tip.NOTE AS  NOTE_IRREGOLARITA_PROVVISORIA,--NULL AS   NOTE_IRREGOLARITA_PROVVISORIA, -- mc
           tec.NOTE AS NOTE_ESITO_REGOLARE --mc
      FROM BENEFICIARIO BEN,
           --PBANDI_T_CAMPIONAMENTO tc,
           --PBANDI_R_CAMPIONAMENTO rc,
           PBANDI_T_PROGETTO tp,
           PBANDI_R_BANDO_LINEA_INTERVENT bli,
           PBANDI_T_DOMANDA td,
           PBANDI_T_PERIODO ptp,
           PBANDI_D_CATEG_ANAGRAFICA dca,
           --PBANDI_R_SOGGETTO_PROGETTO rsp,
           --PBANDI_T_ENTE_GIURIDICO teg,
           --PBANDI_T_SOGGETTO ts,
           PBANDI_T_ESITO_CONTROLLI tec,
           PBANDI_T_IRREGOLARITA_PROVV tip
     WHERE tec.ID_ESITO_CONTROLLO = tip.id_esito_controllo(+) -- mc gen 2021
           AND ben.id_progetto = tp.ID_PROGETTO
           -- tc.ID_CAMPIONE = rc.ID_CAMPIONE
           -- AND rc.ID_PROGETTO = tp.ID_PROGETTO
           AND tp.ID_PROGETTO = tec.ID_PROGETTO -- mc
           AND tec.ID_PERIODO = ptp.ID_PERIODO
           AND tec.ID_CATEG_ANAGRAFICA = dca.ID_CATEG_ANAGRAFICA
          -- AND tec.ID_PROGETTO = rsp.ID_PROGETTO
          -- AND rsp.ID_ENTE_GIURIDICO = teg.ID_ENTE_GIURIDICO
           --AND ts.ID_SOGGETTO = teg.id_SOGGETTO
           --AND ts.ID_SOGGETTO = rsp.id_SOGGETTO --mc gen 2021
          -- AND rsp.ID_TIPO_ANAGRAFICA = 1
          -- AND rsp.ID_TIPO_BENEFICIARIO <> 4
           AND tp.ID_DOMANDA = td.ID_DOMANDA
           AND td.PROGR_BANDO_LINEA_INTERVENTO =
                  bli.PROGR_BANDO_LINEA_INTERVENTO
           AND tec.id_progetto NOT IN (select ID_PROGETTO
                                       from pbandi_t_campionamento tc,
                                            pbandi_r_campionamento rc
                                       where tc.id_campione = rc.id_campione
                                       AND   tc.id_categ_anagrafica = tec.id_categ_anagrafica
                                       AND   tc.id_periodo = tec.id_periodo
                                       AND   tc.tipo_controlli = tec.tipo_controlli))
       UNION
       -- tutti i progetti senza esiti
       (SELECT DISTINCT
          -- 4 as n_query,
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
           --teg.DENOMINAZIONE_ENTE_GIURIDICO,
           BEN.DENOMINAZIONE_ENTE_GIURIDICO,
           tc.DATA_CAMPIONAMENTO,
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
      FROM BENEFICIARIO BEN,
           PBANDI_T_CAMPIONAMENTO tc,
           PBANDI_R_CAMPIONAMENTO rc,
           PBANDI_T_PROGETTO tp,
           PBANDI_R_BANDO_LINEA_INTERVENT bli,
           PBANDI_T_DOMANDA td,
           PBANDI_T_PERIODO ptp,
           PBANDI_D_CATEG_ANAGRAFICA dca
           --PBANDI_R_SOGGETTO_PROGETTO rsp,
           --PBANDI_T_ENTE_GIURIDICO teg,
           --PBANDI_T_SOGGETTO ts
     WHERE ben.id_progetto = tp.ID_PROGETTO
           AND tc.ID_CAMPIONE = rc.ID_CAMPIONE
           AND rc.ID_PROGETTO = tp.ID_PROGETTO
           AND tc.ID_PERIODO = ptp.ID_PERIODO
           AND tc.ID_CATEG_ANAGRAFICA = dca.ID_CATEG_ANAGRAFICA
           --AND tp.ID_PROGETTO = rsp.ID_PROGETTO
           --AND rsp.ID_ENTE_GIURIDICO = teg.ID_ENTE_GIURIDICO
           --AND ts.ID_SOGGETTO = teg.id_SOGGETTO
           --AND ts.ID_SOGGETTO = rsp.id_SOGGETTO --mc gen 2021
          -- AND rsp.ID_TIPO_ANAGRAFICA = 1
           --AND rsp.ID_TIPO_BENEFICIARIO <> 4
           AND tp.ID_DOMANDA = td.ID_DOMANDA
           AND td.PROGR_BANDO_LINEA_INTERVENTO =
               bli.PROGR_BANDO_LINEA_INTERVENTO
           AND (rc.ID_PROGETTO NOT IN (SELECT rc1.ID_PROGETTO
                                      FROM   PBANDI_T_CAMPIONAMENTO tc1,
                                             PBANDI_R_CAMPIONAMENTO rc1,
                                             PBANDI_T_IRREGOLARITA_PROVV tip1
                                      WHERE
                                             tc1.id_campione = rc1.id_campione AND
                                             rc1.id_progetto = tip1.Id_Progetto
                                      AND    tc1.id_categ_anagrafica = tip1.id_categ_anagrafica
                                      AND    tc1.id_periodo = tip1.id_periodo
                                      AND    tc1.tipo_controlli = tip1.tipo_controlli
                                      AND    tip1.id_categ_anagrafica = tc.id_categ_anagrafica
                                      AND    tip1.id_periodo = tc.id_periodo
                                      AND    tip1.tipo_controlli = tc.tipo_controlli
                                      AND    tc1.data_campionamento = tc.data_campionamento)
                AND
                rc.ID_PROGETTO NOT IN (SELECT rc1.ID_PROGETTO
                                      FROM   PBANDI_T_CAMPIONAMENTO tc1,
                                             PBANDI_R_CAMPIONAMENTO rc1,
                                             PBANDI_T_IRREGOLARITA  ti1
                                      WHERE
                                             tc1.id_campione = rc1.id_campione AND
                                             rc1.id_progetto = ti1.Id_Progetto
                                      AND    tc1.id_categ_anagrafica = ti1.id_categ_anagrafica
                                      AND    tc1.id_periodo = ti1.id_periodo
                                      AND    tc1.tipo_controlli = ti1.tipo_controlli
                                      AND    ti1.id_categ_anagrafica = tc.id_categ_anagrafica
                                      AND    ti1.id_periodo = tc.id_periodo
                                      AND    ti1.tipo_controlli = tc.tipo_controlli
                                      AND    tc1.data_campionamento = tc.data_campionamento)
                 AND
                rc.ID_PROGETTO NOT IN (SELECT rc1.ID_PROGETTO
                                      FROM   PBANDI_T_CAMPIONAMENTO tc1,
                                             PBANDI_R_CAMPIONAMENTO rc1,
                                             PBANDI_T_ESITO_CONTROLLI tec1
                                      WHERE
                                             tc1.id_campione = rc1.id_campione AND
                                             rc1.id_progetto = tec1.Id_Progetto
                                      AND    tc1.id_categ_anagrafica = tec1.id_categ_anagrafica
                                      AND    tc1.id_periodo =          tec1.id_periodo
                                      AND    tc1.tipo_controlli =      tec1.tipo_controlli
                                      AND    tec1.id_categ_anagrafica = tc.id_categ_anagrafica
                                      AND    tec1.id_periodo = tc.id_periodo
                                      AND    tec1.tipo_controlli = tc.tipo_controlli
                                      AND    tc1.data_campionamento = tc.data_campionamento))));



