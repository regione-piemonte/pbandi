/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

-- PBANDI_T_DICHIARAZIONE_SPESA
ALTER TABLE PBANDI_T_DICHIARAZIONE_SPESA ADD ID_DICHIARAZIONE_SPESA_COLL NUMBER (8);
-- OBJ TYPE 
DROP TYPE LISTPROG;

CREATE OR REPLACE TYPE OBJPROG AS OBJECT
(ID_PROGETTO NUMBER(8),
 TITOLO_PROGETTO  VARCHAR2(2000),
 CODICE_VISUALIZZATO  VARCHAR2(100),
 ACRONIMO_PROGETTO VARCHAR2(2000) 
 )
/
CREATE OR REPLACE TYPE LISTPROG AS TABLE OF OBJPROG
/

-- PBANDI_D_NORMATIVA_AFFIDAMENTO
CREATE TABLE PBANDI_D_NORMATIVA_AFFIDAMENTO
(ID_NORMA NUMBER(3),
 DESC_NORMA VARCHAR2(1000)
);

ALTER TABLE PBANDI_D_NORMATIVA_AFFIDAMENTO ADD 
(CONSTRAINT PK_PBANDI_D_NORMATIVA_AFFID PRIMARY KEY (ID_NORMA)
 USING INDEX  TABLESPACE PBANDI_IDX);
 
ALTER TABLE PBANDI_T_APPALTO ADD ID_NORMA NUMBER(3);

ALTER TABLE PBANDI_T_APPALTO ADD
CONSTRAINT FK_PBANDI_D_NORMA_AFFID_01
FOREIGN KEY (ID_NORMA) 
REFERENCES PBANDI_D_NORMATIVA_AFFIDAMENTO (ID_NORMA);

-- INDEX
CREATE INDEX IE1_PBANDI_T_APPALTO ON PBANDI_T_APPALTO (ID_NORMA) TABLESPACE PBANDI_IDX;
--
-- PBANDI_T_ENTE_GIURIDICO
ALTER TABLE PBANDI_T_ENTE_GIURIDICO ADD COD_UNI_IPA VARCHAR2(16);

-- PBANDI_R_BANDO_MODALITA_AGEVOL
ALTER TABLE PBANDI_R_BANDO_MODALITA_AGEVOL ADD FLAG_LIQUIDAZIONE VARCHAR2(1);
ALTER TABLE PBANDI_R_BANDO_MODALITA_AGEVOL MODIFY FLAG_LIQUIDAZIONE DEFAULT 'N'; 
ALTER TABLE PBANDI_R_BANDO_MODALITA_AGEVOL ADD (  CONSTRAINT CHK_FLAG_LIQUIDAZIONE  CHECK (FLAG_LIQUIDAZIONE IN ('S', 'N')));

-- PBANDI_R_SOGG_PROGETTO_SEDE
ALTER TABLE PBANDI_R_SOGG_PROGETTO_SEDE ADD FLAG_SEDE_AMMINISTRATIVA VARCHAR2(1);

--VIEW
CREATE OR REPLACE FORCE VIEW PBANDI_V_DOC_INDEX_BATCH_FINP
(
   ID_DOCUMENTO_INDEX,
   UUID_NODO,
   ID_PROGETTO,
   NOME_FILE,
   NOME_FILE_FIRMATO,
   TIPO_INVIO,
   FLAG_FIRMA_CARTACEA,
   FLAG_TRASM_DEST,
   ID_DICHIARAZIONE_SPESA,
   DESC_BREVE_TIPO_DOC_INDEX
)
AS
   WITH SF_INVIO
        AS (  SELECT c.ID_DICHIARAZIONE_SPESA,
                     c.ID_PROGETTO,
                     CASE
                        WHEN MIN (TIPO_INVIO) || MAX (TIPO_INVIO) = 'CC'
                        THEN
                           'C'
                        WHEN MIN (TIPO_INVIO) || MAX (TIPO_INVIO) = 'DD'
                        THEN
                           'E'
                        WHEN MIN (TIPO_INVIO) || MAX (TIPO_INVIO) = 'CD'
                        THEN
                           'I'
                        ELSE
                           NULL
                     END
                        TIPO_INVIO
                FROM PBANDI_S_DICH_DOC_SPESA a,
                     PBANDI_R_DOC_SPESA_PROGETTO b,
                     PBANDI_T_DICHIARAZIONE_SPESA c
               WHERE     a.ID_DICHIARAZIONE_SPESA = c.ID_DICHIARAZIONE_SPESA
                     AND a.ID_DOCUMENTO_DI_SPESA = b.ID_DOCUMENTO_DI_SPESA
                     AND b.ID_PROGETTO = c.ID_PROGETTO
            GROUP BY c.ID_DICHIARAZIONE_SPESA, c.ID_PROGETTO)
   SELECT ID_DOCUMENTO_INDEX,
          UUID_NODO,
          x.ID_PROGETTO,
          NOME_FILE,
          NOME_FILE_FIRMATO,
          CASE
             WHEN x.NOME_ENTITA IN
                     ('PBANDI_T_DICHIARAZIONE_SPESA',
                      'PBANDI_T_COMUNICAZ_FINE_PROG')
             THEN
                i.TIPO_INVIO
             ELSE
                NULL                               --Comunicazione di rinuncia
          END
             TIPO_INVIO,
          FLAG_FIRMA_CARTACEA,
          FLAG_TRASM_DEST,
          x.ID_DICHIARAZIONE_SPESA,
          DESC_BREVE_TIPO_DOC_INDEX
     FROM    (SELECT ID_DOCUMENTO_INDEX,
                     UUID_NODO,
                     di.ID_PROGETTO,
					 p.ID_PROGETTO_FINANZ,
                     NOME_FILE,
                     NOME_ENTITA,
                     CASE
                        WHEN FLAG_FIRMA_CARTACEA = 'N'
                        THEN
                           NOME_FILE
                           || (SELECT valore
                                 FROM PBANDI_C_COSTANTI
                                WHERE attributo =
                                         'conf.firmaDigitaleFileExtension')
                        ELSE
                           NULL
                     END
                        NOME_FILE_FIRMATO,
                     FLAG_FIRMA_CARTACEA,
                     NVL (FLAG_TRASM_DEST, 'N') FLAG_TRASM_DEST,
                     --Dichiarazione di spesa(id_target di Id_entita 63 PBANDI_T_DICHIARAZIONE_SPESA)
                     --Comunicazione di fine progetto (id_entita 270  PBANDI_T_COMUNICAZ_FINE_PROG cercare la dichiarazione di spesa di tipo DESC_BREVE_TIPO_DICHIARA_SPESA=FC)
                     --Comunicazione di rinuncia (id_entita 249 PBANDI_T_RINUNCIA id_dichiarazione di spesa=NULL
                     CASE
                        WHEN en.NOME_ENTITA = 'PBANDI_T_DICHIARAZIONE_SPESA'
                        THEN
                           di.ID_TARGET
                        WHEN en.NOME_ENTITA = 'PBANDI_T_COMUNICAZ_FINE_PROG'
                        THEN
                           (SELECT MAX (id_dichiarazione_spesa)
                              FROM PBANDI_T_DICHIARAZIONE_SPESA dich
                             -- id_progetto = di.id_progetto
                             WHERE id_progetto = NVL(p.id_progetto_finanz,p.id_progetto)
                                   AND id_tipo_dichiaraz_spesa IN
                                          (SELECT id_tipo_dichiaraz_spesa
                                             FROM PBANDI_D_TIPO_DICHIARAZ_SPESA
                                            WHERE DESC_BREVE_TIPO_DICHIARA_SPESA =
                                                     'FC')) -- Finale con comunicazione
                        ELSE
                           NULL                    --Comunicazione di rinuncia
                     END
                        ID_DICHIARAZIONE_SPESA,
                     tdi.DESC_BREVE_TIPO_DOC_INDEX
                FROM PBANDI_T_DOCUMENTO_INDEX di
                     JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX tdi
                        USING (ID_TIPO_DOCUMENTO_INDEX)
                     JOIN PBANDI_C_ENTITA en
                        USING (ID_ENTITA)
                     LEFT JOIN PBANDI_D_STATO_DOCUMENTO_INDEX sdi
                        USING (ID_STATO_DOCUMENTO)
                     JOIN PBANDI_T_PROGETTO p
                        ON p.ID_PROGETTO = di.ID_PROGETTO
                     JOIN PBANDI_T_DOMANDA
                        USING (ID_DOMANDA)
                     JOIN PBANDI_R_BANDO_LINEA_INTERVENT bli
                        USING (PROGR_BANDO_LINEA_INTERVENTO)
                     JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP blec
                        USING (PROGR_BANDO_LINEA_INTERVENTO)
                     JOIN PBANDI_D_RUOLO_ENTE_COMPETENZA rec
                        USING (ID_RUOLO_ENTE_COMPETENZA)
                     JOIN PBANDI_T_ENTE_COMPETENZA ec
                        USING (ID_ENTE_COMPETENZA)
               WHERE tdi.FLAG_FIRMABILE = 'S'
                     AND en.NOME_ENTITA IN
                            ('PBANDI_T_DICHIARAZIONE_SPESA',
                             'PBANDI_T_COMUNICAZ_FINE_PROG',
                             'PBANDI_T_RINUNCIA')
                     AND sdi.DESC_BREVE IN ('INVIATO')
                     AND NVL (di.FLAG_FIRMA_CARTACEA, 'N') = 'N'
                     AND di.DT_MARCA_TEMPORALE IS NOT NULL
                     AND rec.DESC_BREVE_RUOLO_ENTE = 'DESTINATARIO'
                     AND ec.DESC_BREVE_ENTE = 'FIN') x
          LEFT JOIN
             SF_INVIO i
          ON (i.ID_DICHIARAZIONE_SPESA = x.ID_DICHIARAZIONE_SPESA
              AND i.ID_PROGETTO = NVL(x.ID_PROGETTO_FINANZ,x.ID_PROGETTO));
