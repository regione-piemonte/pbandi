/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

--
-- PBANDI_T_ESITI_NOTE_AFFIDAMENT
ALTER TABLE PBANDI_T_ESITI_NOTE_AFFIDAMENT ADD FLAG_RETTIFICA VARCHAR2(1);
-- CHECK
ALTER TABLE PBANDI_T_ESITI_NOTE_AFFIDAMENT ADD (
CONSTRAINT CHK_FLAG_RETTIFICA
CHECK (FLAG_RETTIFICA IN ('S',NULL)));

-- PBANDI_T_NOTIFICA_PROCESSO
ALTER TABLE PBANDI_T_NOTIFICA_PROCESSO RENAME COLUMN MESSAGE_NOTIFICA TO MESSAGE_NOTIFICA2;
ALTER TABLE PBANDI_T_NOTIFICA_PROCESSO ADD (MESSAGE_NOTIFICA CLOB) LOB (MESSAGE_NOTIFICA) 
STORE AS MESSAGE_NOTIFICA (TABLESPACE PBANDI_LOB INDEX (TABLESPACE PBANDI_LOB));
UPDATE PBANDI_T_NOTIFICA_PROCESSO SET MESSAGE_NOTIFICA = TO_CLOB(MESSAGE_NOTIFICA2);
COMMIT;
--
DROP TYPE LISTNOTIFPROG;
DROP TYPE OBJNOTIFPROG;
 
 CREATE OR REPLACE TYPE "OBJNOTIFPROG" AS OBJECT
(ID_PROGETTO NUMBER(8),
 TITOLO_PROGETTO  VARCHAR2(255),
 CODICE_VISUALIZZATO  VARCHAR2(100),
 ID_NOTIFICA INTEGER,
 STATO_NOTIFICA  VARCHAR2(1),
 DT_NOTIFICA  DATE,
 SUBJECT_NOTIFICA VARCHAR2(4000),
 --MESSAGE_NOTIFICA VARCHAR2(4000)
 MESSAGE_NOTIFICA CLOB
 )
 /
 
 CREATE OR REPLACE TYPE "LISTNOTIFPROG" AS TABLE OF OBJNOTIFPROG
 /

-- PBANDI_R_BANDO_VOCE_SP_TIPO_DOC
CREATE TABLE PBANDI_R_BANDO_VOCE_SP_TIP_DOC
(ID_BANDO NUMBER(8), 
 ID_VOCE_DI_SPESA NUMBER(8), 
 ID_TIPO_DOCUMENTO_SPESA NUMBER(3),
 DT_INIZIO_VALIDITA DATE,
 DT_FINE_VALIDITA DATE
);
 --PK
ALTER TABLE PBANDI_R_BANDO_VOCE_SP_TIP_DOC  ADD 
(CONSTRAINT PK_PBANDI_R_BAN_VOC_SP_TIP_DOC PRIMARY KEY (ID_BANDO,ID_VOCE_DI_SPESA,ID_TIPO_DOCUMENTO_SPESA)
USING INDEX  TABLESPACE PBANDI_IDX);
--FK
ALTER TABLE PBANDI_R_BANDO_VOCE_SP_TIP_DOC ADD
CONSTRAINT FK_PBANDI_R_BANDO_VC_SP_01
FOREIGN KEY (ID_BANDO,ID_VOCE_DI_SPESA) 
REFERENCES PBANDI_R_BANDO_VOCE_SPESA (ID_BANDO,ID_VOCE_DI_SPESA);
--FK
ALTER TABLE PBANDI_R_BANDO_VOCE_SP_TIP_DOC ADD
CONSTRAINT FK_PBANDI_D_TIPO_DOCUMENTO_02
FOREIGN KEY (ID_TIPO_DOCUMENTO_SPESA) 
REFERENCES PBANDI_D_TIPO_DOCUMENTO_SPESA (ID_TIPO_DOCUMENTO_SPESA);
--INDEX
CREATE INDEX IE1_PBANDI_R_BANDO_VC_SP_TI_DO ON PBANDI_R_BANDO_VOCE_SP_TIP_DOC (ID_VOCE_DI_SPESA) TABLESPACE PBANDI_IDX;
CREATE INDEX IE2_PBANDI_R_BANDO_VC_SP_TI_DO ON PBANDI_R_BANDO_VOCE_SP_TIP_DOC (ID_TIPO_DOCUMENTO_SPESA) TABLESPACE PBANDI_IDX;

-- PBANDI_T_DETT_PROPOSTA_CERTIF 
ALTER TABLE PBANDI_T_DETT_PROPOSTA_CERTIF 
ADD IMPORTO_PAG_VALIDATI_ORIG NUMBER(13,2);

--pbandi_r_det_prop_cer_qp_doc
ALTER TABLE pbandi_r_det_prop_cer_qp_doc ADD
(IMPORTO_VALIDATO_RETT NUMBER (17,2) 
);

-- PBANDI_R_PAG_QUOT_PARTE_DOC_SP
ALTER TABLE PBANDI_R_PAG_QUOT_PARTE_DOC_SP ADD
(IMPORTO_VALIDATO_RETT NUMBER (17,2),
 ID_PROPOSTA_CERTIFICAZ NUMBER(8) 
 );
--FK
ALTER TABLE PBANDI_R_PAG_QUOT_PARTE_DOC_SP ADD
CONSTRAINT FK_PBANDI_T_PROPOSTA_CERTIF_10
FOREIGN KEY (ID_PROPOSTA_CERTIFICAZ) 
REFERENCES PBANDI_T_PROPOSTA_CERTIFICAZ (ID_PROPOSTA_CERTIFICAZ);
--INDEX
CREATE INDEX IE1_PBANDI_R_PAG_QUOT_P_DOC_SP ON PBANDI_R_PAG_QUOT_PARTE_DOC_SP (ID_PROPOSTA_CERTIFICAZ) TABLESPACE PBANDI_IDX;

-- PBANDI_T_RETT_FORFETT
CREATE TABLE PBANDI_T_RETT_FORFETT
(ID_RETTIFICA_FORFETT NUMBER(8),
 ID_APPALTO NUMBER(8), 
 PERC_RETT NUMBER(5,2),
 ID_CATEG_ANAGRAFICA NUMBER(3),
 FLAG_MODIFICA VARCHAR2(1),
 DATA_INSERIMENTO DATE,
 ID_APPALTO_CHECKLIST INTEGER,
 ID_PROPOSTA_CERTIFICAZ NUMBER(8)
);
 --PK
ALTER TABLE PBANDI_T_RETT_FORFETT ADD 
(CONSTRAINT PK_PBANDI_T_RETT_FORFETT PRIMARY KEY (ID_RETTIFICA_FORFETT)
USING INDEX  TABLESPACE PBANDI_IDX);
--FK
ALTER TABLE PBANDI_T_RETT_FORFETT ADD
CONSTRAINT FK_PBANDI_T_APPALTO_06
FOREIGN KEY (ID_APPALTO) 
REFERENCES PBANDI_T_APPALTO (ID_APPALTO);
--FK
ALTER TABLE PBANDI_T_RETT_FORFETT ADD
CONSTRAINT fk_pbandi_d_categ_anag_08
FOREIGN KEY (ID_CATEG_ANAGRAFICA) 
REFERENCES pbandi_d_categ_anagrafica (ID_CATEG_ANAGRAFICA);
--FK
ALTER TABLE PBANDI_T_RETT_FORFETT ADD
CONSTRAINT fk_pbandi_t_appalto_chk_02
FOREIGN KEY (ID_APPALTO_CHECKLIST) 
REFERENCES PBANDI_T_APPALTO_CHECKLIST (ID_APPALTO_CHECKLIST);
--FK
ALTER TABLE PBANDI_T_RETT_FORFETT ADD
CONSTRAINT FK_PBANDI_T_PROPOSTA_CERTIF_11
FOREIGN KEY (ID_PROPOSTA_CERTIFICAZ) 
REFERENCES PBANDI_T_PROPOSTA_CERTIFICAZ (ID_PROPOSTA_CERTIFICAZ);

CREATE INDEX IE1_PBANDI_T_RETT_FORFETT ON PBANDI_T_RETT_FORFETT (ID_APPALTO) TABLESPACE PBANDI_IDX;
CREATE INDEX IE2_PBANDI_T_RETT_FORFETT ON PBANDI_T_RETT_FORFETT (ID_CATEG_ANAGRAFICA) TABLESPACE PBANDI_IDX;
CREATE INDEX IE3_PBANDI_T_RETT_FORFETT ON PBANDI_T_RETT_FORFETT (ID_APPALTO_CHECKLIST) TABLESPACE PBANDI_IDX;
CREATE INDEX IE4_PBANDI_T_RETT_FORFETT ON PBANDI_T_RETT_FORFETT (ID_PROPOSTA_CERTIFICAZ) TABLESPACE PBANDI_IDX;
-- PBANDI_D_STATO_AFFIDAMENTO
ALTER TABLE PBANDI_D_STATO_AFFIDAMENTO ADD ORDINAMENTO INTEGER; 

-- PBANDI_T_INTEGRAZIONE_APPALTO
CREATE TABLE PBANDI_T_INTEGRAZIONE_APPALTO
(ID_INTEGRAZIONE INTEGER,
ID_APPALTO NUMBER(8),
DT_INVIO DATE,
DT_RICEZIONE DATE,
NOTE_RICHIESTA VARCHAR2(4000)
);
--PK
ALTER TABLE PBANDI_T_INTEGRAZIONE_APPALTO ADD 
(CONSTRAINT PK_PBANDI_T_INTEGRAZIONE_APP PRIMARY KEY (ID_INTEGRAZIONE)
USING INDEX  TABLESPACE PBANDI_IDX);
--FK
ALTER TABLE PBANDI_T_INTEGRAZIONE_APPALTO ADD
CONSTRAINT FK_PBANDI_T_APPALTO_07
FOREIGN KEY (ID_APPALTO) 
REFERENCES PBANDI_T_APPALTO (ID_APPALTO);

--INDEX
CREATE INDEX IE1_PBANDI_T_INTEGRAZIONE_APP ON PBANDI_T_INTEGRAZIONE_APPALTO (ID_APPALTO) TABLESPACE PBANDI_IDX;



-- SEQUENCE
CREATE SEQUENCE SEQ_PBANDI_T_RETT_FORFETT
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;
--
CREATE SEQUENCE SEQ_PBANDI_T_INTEGRAZIONE_APP
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;

-- manca la vista di salvatore ?

-- VISTE
CREATE OR REPLACE VIEW PBANDI_V_RILEVAZIONI AS
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
           --2 as n_query,
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
           --3 as n_query,
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
           --4 as n_query,
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
                                      WHERE
                                             tc1.id_campione = rc1.id_campione AND
                                             rc1.id_progetto = tip1.Id_Progetto
                                      AND    tc1.id_categ_anagrafica = tip1.id_categ_anagrafica
                                      AND    tc1.id_periodo = tip1.id_periodo
                                      AND    tc1.tipo_controlli = tip1.tipo_controlli)
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
                                      AND    tc1.tipo_controlli = ti1.tipo_controlli)
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
                                      AND    tc1.tipo_controlli =      tec1.tipo_controlli))));
