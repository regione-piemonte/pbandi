/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

-- LISTTASKPROG Array di Task per progetto
DROP TYPE LISTTASKPROG
/

DROP TYPE OBJTASKPROG
/

-- Creazione TIPI
--LISTTASKPROG Oggetto Task per progetto
CREATE OR REPLACE TYPE OBJTASKPROG AS OBJECT
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
 DENOMINAZIONE_LOCK  VARCHAR2(1000)
 )
/

-- LISTTASKPROG Array di Task per progetto
CREATE OR REPLACE
TYPE LISTTASKPROG AS TABLE OF OBJTASKPROG
/

--LISTTASKTOT Oggetto Task per progetto
CREATE OR REPLACE TYPE OBJTASKTOT AS OBJECT
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
 ID_SOGGETTO_BENEFICIARIO  NUMBER(8),
 CODICE_FISCALE_BENEFICIARIO VARCHAR2(32),
 DENOMINAZIONE_BENEFICIARIO  VARCHAR2(2000),
 RUOLO   VARCHAR2(30),
 DESCR_BREVE_REGOLA  VARCHAR2(20)
 )
/

-- LISTTASKPROG Array di Task per progetto
CREATE OR REPLACE
TYPE LISTTASKTOT AS TABLE OF OBJTASKTOT
/

CREATE TABLE PBANDI_C_TODOLIST 
   (RUOLO   VARCHAR2(30) NOT NULL,
    PROGR_BANDO_LINEA_INTERVENTO  NUMBER(8),
    ID_REGOLA  NUMBER(8),
	ID_SOGGETTO_BENEFICIARIO  NUMBER(8),
	ID_PROGETTO NUMBER(8));
	
COMMENT ON TABLE  PBANDI_C_TODOLIST IS 'Parametri per l''esecuzione  della vista PBANDI_V_TODOLIST';
COMMENT ON COLUMN PBANDI_C_TODOLIST.RUOLO IS 'Indicare: ADG-IST-MASTER (Istruttore),BEN-MASTER (Beneficiario),CREATOR (Richiedente),GDF (Controllo),MONITORAGGIO';

ALTER TABLE PBANDI_C_TODOLIST ADD (
  CONSTRAINT CK_RUOLO
  CHECK (ruolo in ('ADG-IST-MASTER','BEN-MASTER','CREATOR','GDF','MONITORAGGIO')));
  
ALTER TABLE PBANDI_C_TODOLIST ADD (
  CONSTRAINT FK_PBANDI_R_BANDO_LINEA_INT_21
  FOREIGN KEY (PROGR_BANDO_LINEA_INTERVENTO) 
  REFERENCES PBANDI_R_BANDO_LINEA_INTERVENT (PROGR_BANDO_LINEA_INTERVENTO)); 

ALTER TABLE PBANDI_C_TODOLIST ADD (
  CONSTRAINT FK_PBANDI_C_REGOLA_03 
  FOREIGN KEY (ID_REGOLA) 
  REFERENCES PBANDI_C_REGOLA (ID_REGOLA));
  
ALTER TABLE PBANDI_C_TODOLIST ADD (
  CONSTRAINT FK_PBANDI_T_SOGGETTO_26
  FOREIGN KEY (ID_SOGGETTO_BENEFICIARIO) 
  REFERENCES PBANDI_T_SOGGETTO (ID_SOGGETTO));

ALTER TABLE PBANDI_C_TODOLIST ADD (
  CONSTRAINT FK_PBANDI_T_PROGETTO_38 
  FOREIGN KEY (ID_PROGETTO) 
  REFERENCES PBANDI_T_PROGETTO (ID_PROGETTO));

-- SEQUENCE
  CREATE SEQUENCE SEQ_PBANDI_D_MICRO_SEZ_MODULO
  START WITH 1000
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;

ALTER TABLE PBANDI_R_BL_TIPO_DOC_MICRO_SEZ
MODIFY(ID_MICRO_SEZIONE_MODULO NUMBER(5));

ALTER TABLE PBANDI_D_MICRO_SEZIONE_MODULO
MODIFY(ID_MICRO_SEZIONE_MODULO NUMBER(6));

CREATE TABLE PBANDI_D_MICRO_SEZIONE_DIN
(
  NOME    VARCHAR2(100),
  VALORE  VARCHAR2(4000)
);
  
--PBANDI_V_DOC_INDEX_BATCH		  
CREATE OR REPLACE  VIEW PBANDI_V_DOC_INDEX_BATCH
(
   ID_DOCUMENTO_INDEX,
   AZIONE,
   UUID_NODO,
   PROGR_BANDO_LINEA_INTERVENTO,
   ID_PROGETTO,
   CODICE_VISUALIZZATO,
   NOME_FILE,
   DESC_TIPO_DOC_INDEX,
   ID_STATO_DOCUMENTO,
   DESC_BREVE,
   FLAG_FIRMA_CARTACEA,
   DT_VERIFICA_FIRMA,
   DT_MARCA_TEMPORALE,
   DESCRIZIONE,
   NUM_PROTOCOLLO,
   ID_SOGG_DELEGATO,
   ID_SOGG_RAPPR_LEGALE,
   DESC_BREVE_ENTE,
   DESC_BREVE_SETTORE,
   DESC_ENTE_COMPETENZA,
   ID_MESSAGGIO_APPL,
   ENTE,
   SETTORE,
   PAROLA_CHIAVE,
   FEEDBACK_ACTA,
   FASCICOLO_ACTA,
   CLASSIFICAZIONE_ACTA,
   FLAG_REGIONALE,
   DENOMINAZIONE_BENEFICIARIO,
   CODICE_FISCALE_BENEFICIARIO
)
AS
   WITH LVF --Lof verifica Firma digitale
        AS (SELECT ID_DOCUMENTO_INDEX, ID_MESSAGGIO_APPL
              FROM PBANDI_T_LOG_VALIDAZ_FIRMA A
             WHERE ID_LOG =
                      (SELECT MAX (ID_LOG)
                         FROM PBANDI_T_LOG_VALIDAZ_FIRMA
                        WHERE ID_DOCUMENTO_INDEX = A.ID_DOCUMENTO_INDEX)
                   AND METODO = 'VERIFY'
                   AND FLAG_STATO_VALIDAZIONE = 'N'),
        LMT AS  -- Log Marcatura temporale
		    (SELECT ID_DOCUMENTO_INDEX, METODO
              FROM PBANDI_T_LOG_VALIDAZ_FIRMA A
             WHERE ID_LOG =
                      (SELECT MAX (ID_LOG)
                         FROM PBANDI_T_LOG_VALIDAZ_FIRMA
                        WHERE ID_DOCUMENTO_INDEX = A.ID_DOCUMENTO_INDEX)
                   AND METODO = 'TIMESTAMP'),
		BEN AS
			(SELECT A.ID_PROGETTO,B.DENOMINAZIONE_ENTE_GIURIDICO, C.CODICE_FISCALE_SOGGETTO
				FROM PBANDI_R_SOGGETTO_PROGETTO A, PBANDI_T_ENTE_GIURIDICO B, PBANDI_T_SOGGETTO C
				WHERE  A.ID_ENTE_GIURIDICO = B.ID_ENTE_GIURIDICO
				AND C.ID_SOGGETTO = A.ID_SOGGETTO
				AND ID_TIPO_BENEFICIARIO != 4
				AND ID_TIPO_ANAGRAFICA = 1
				AND B.DT_FINE_VALIDITA IS NULL
				AND A.DT_FINE_VALIDITA IS NULL)
   --MAIN
   SELECT ID_DOCUMENTO_INDEX,
          CASE
             WHEN     DESC_BREVE IN ('ACQUISITO')
                  AND FLAG_FIRMA_CARTACEA = 'N'
                  AND DT_VERIFICA_FIRMA IS NULL
                  AND lvf.ID_MESSAGGIO_APPL = 'ERR_GENERIC'
             THEN
                'DA_VALIDARE'
             WHEN     DESC_BREVE IN ('VALIDATO')
                  AND FLAG_FIRMA_CARTACEA = 'N'
                  AND DT_VERIFICA_FIRMA IS NOT NULL
                  AND DT_MARCA_TEMPORALE IS NULL
                  AND lmt.METODO = 'TIMESTAMP'
             THEN
                'DA_MARCARE_TEMPORALMENTE'
             WHEN     DESC_BREVE IN ('INVIATO')
                  AND FLAG_FIRMA_CARTACEA = 'N'
                  AND DT_MARCA_TEMPORALE IS NOT NULL
             THEN
                'DA_CLASSIFICARE'
             WHEN     DESC_BREVE IN ('CLASSIFICATO')
                  AND FLAG_FIRMA_CARTACEA = 'N'
                  AND DT_MARCA_TEMPORALE IS NOT NULL
				  AND DT_CLASSIFICAZIONE IS NOT NULL
             THEN
                'DA_PROTOCOLLARE'
		  END   AZIONE,
          UUID_NODO,
		  PROGR_BANDO_LINEA_INTERVENTO,
          ID_PROGETTO,
		  CODICE_VISUALIZZATO,
          NOME_FILE,
          DESC_TIPO_DOC_INDEX,
          ID_STATO_DOCUMENTO,
          DESC_BREVE,
          FLAG_FIRMA_CARTACEA,
          TO_CHAR (DT_VERIFICA_FIRMA, 'DD/MM/YYYY') AS DT_VERIFICA_FIRMA,
          TO_CHAR (DT_MARCA_TEMPORALE, 'DD/MM/YYYY') AS DT_MARCA_TEMPORALE,
          prot.DESCRIZIONE,
          dp.NUM_PROTOCOLLO,
          ID_SOGG_DELEGATO,
          ID_SOGG_RAPPR_LEGALE,
          blec.DESC_BREVE_ENTE,
          NVL(blec.DESC_BREVE_SETTORE,'000') DESC_BREVE_SETTORE,
		  blec.DESC_ENTE_COMPETENZA,
          ID_MESSAGGIO_APPL,
          blec.DESC_BREVE_ENTE ENTE,
          blec.DESC_BREVE_ENTE||NVL(blec.DESC_BREVE_SETTORE,'000') SETTORE,
		  blec.PAROLA_CHIAVE,
		  blec.FEEDBACK_ACTA,
		  FASCICOLO_ACTA,
		  CLASSIFICAZIONE_ACTA,
		  CASE
             WHEN     DESC_BREVE_TIPO_ENTE_COMPETENZ IN ('ADG','REG')
             THEN
                'S'
			 ELSE 'N'
		  END   FLAG_REGIONALE,
		  ben.DENOMINAZIONE_ENTE_GIURIDICO  DENOMINAZIONE_BENEFICIARIO,
		  ben.CODICE_FISCALE_SOGGETTO CODICE_FISCALE_BENEFICIARIO
     FROM PBANDI_T_DOCUMENTO_INDEX
          JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX
             USING (ID_TIPO_DOCUMENTO_INDEX)
          LEFT JOIN PBANDI_D_STATO_DOCUMENTO_INDEX
             USING (ID_STATO_DOCUMENTO)
          JOIN PBANDI_T_PROGETTO
             USING (ID_PROGETTO)
          JOIN PBANDI_T_DOMANDA
             USING (ID_DOMANDA)
          JOIN PBANDI_R_BANDO_LINEA_INTERVENT bli
             USING (PROGR_BANDO_LINEA_INTERVENTO)
          JOIN PBANDI_R_REGOLA_BANDO_LINEA
             USING (PROGR_BANDO_LINEA_INTERVENTO)
          JOIN PBANDI_V_BANDO_LINEA_ENTE_COMP blec
             USING (PROGR_BANDO_LINEA_INTERVENTO)
          LEFT JOIN PBANDI_T_DOC_PROTOCOLLO dp
             USING (ID_DOCUMENTO_INDEX)
          LEFT JOIN PBANDI_D_SISTEMA_PROTOCOLLO prot
             USING (ID_SISTEMA_PROT)
          LEFT JOIN lvf
             USING (ID_DOCUMENTO_INDEX)
          LEFT JOIN lmt
             USING (ID_DOCUMENTO_INDEX)
   		  LEFT JOIN ben
             USING (ID_PROGETTO)
		  WHERE PBANDI_C_TIPO_DOCUMENTO_INDEX.FLAG_FIRMABILE = 'S'
          AND blec.DESC_BREVE_RUOLO_ENTE =
                 'DESTINATARIO'
          AND PBANDI_R_REGOLA_BANDO_LINEA.ID_REGOLA = 42;


-- PACKAGE PCK_PBANDI_TODOLIST

CREATE OR REPLACE PACKAGE  PCK_PBANDI_TODOLIST AS

  FUNCTION RETURN_LISTTASKTOT RETURN LISTTASKTOT;
  PROCEDURE INIT;
  
 
  V_LISTTASKTOT LISTTASKTOT;
  
  G_PRIMA_ESECUZIONE BOOLEAN;
  
  
END PCK_PBANDI_TODOLIST;
/


CREATE OR REPLACE PACKAGE  BODY      PCK_PBANDI_TODOLIST AS

--  FUNCTION FN_TODO_LIST		  
FUNCTION FN_TODO_LIST (p_desc_breve_tipo_anagrafica    IN  PBANDI_D_TIPO_ANAGRAFICA.DESC_BREVE_TIPO_ANAGRAFICA%TYPE,
                       p_progr_bando_linea_intervento  IN  NUMBER,
					   p_id_regola IN NUMBER,
					   p_id_soggetto_beneficiario IN NUMBER,
					   p_id_progetto IN NUMBER) RETURN LISTTASKTOT AS

    V_LISTTASKPROG LISTTASKPROG := LISTTASKPROG(NULL);
    V_LISTTASKTOT LISTTASKTOT := LISTTASKTOT(NULL);

    v_x PLS_INTEGER := 0;

    CURSOR c1 IS

        select distinct id_progetto,
		                id_soggetto_beneficiario,
		                codice_fiscale_beneficiario,
						denominazione_beneficiario,
						progr_bando_linea_intervento
                   from PBANDI_V_PROGETTI_BEN_BL
				   where nvl(p_id_soggetto_beneficiario,id_soggetto_beneficiario) = id_soggetto_beneficiario
					 and nvl(p_id_progetto,id_progetto) = id_progetto;
														  
	CURSOR c2 (c_id_regola IN NUMBER,
	           c_progr_bando_linea_intervento IN NUMBER) IS
	   select desc_breve_regola
	     from PBANDI_R_REGOLA_BANDO_LINEA a,
		      PBANDI_C_REGOLA b
		 where a.id_regola = b.id_regola
		   and a.id_regola = c_id_regola
		   and a.progr_bando_linea_intervento = c_progr_bando_linea_intervento;

  v_id_soggetto PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE;
  v_desc_breve_regola VARCHAR2(20);

  BEGIN


     FOR r1 IN c1  LOOP
	 
	     IF p_id_regola IS NOT NULL THEN  -- Se il parametro della regola è valorizzato allora verifico che il bando linea è nella regola
		 
			IF c2%ISOPEN THEN
			  CLOSE c2;
		    END IF;
			
		    OPEN c2 (p_id_regola,
					 r1.progr_bando_linea_intervento);
					  
			 v_desc_breve_regola := '';
			 FETCH c2 INTO v_desc_breve_regola;
			 CONTINUE WHEN c2%NOTFOUND;
			 CLOSE c2;
		 END IF;
		 
		 IF p_progr_bando_linea_intervento IS NOT NULL THEN -- Se il parametro del Bando è valorizzato allora seleziono solo progetti del BL
		    IF r1.progr_bando_linea_intervento != p_progr_bando_linea_intervento THEN
			  CONTINUE;
			END IF;
		 END IF;
	 
         pck_pbandi_processo.g_id_processo := pck_pbandi_processo.GetProcesso(r1.id_progetto);

         V_LISTTASKPROG := pck_pbandi_processo.GetAttivita(r1.id_progetto,1,p_desc_breve_tipo_anagrafica);

         FOR x IN 1..V_LISTTASKPROG.COUNT LOOP

             IF v_x > 0 THEN
                V_LISTTASKTOT.extend;
            END IF;

            v_x := v_x+1;
            V_LISTTASKTOT(v_x) := OBJTASKTOT(V_LISTTASKPROG(x).id_progetto,
                                             V_LISTTASKPROG(x).titolo_progetto,
                                             V_LISTTASKPROG(x).codice_visualizzato,
                                             V_LISTTASKPROG(x).descr_breve_task,
                                             V_LISTTASKPROG(x).descr_task,
                                             V_LISTTASKPROG(x).progr_bando_linea_intervento,
                                             V_LISTTASKPROG(x).nome_bando_linea,
                                             V_LISTTASKPROG(x).flag_opt,
                                             V_LISTTASKPROG(x).flag_lock,
                                             V_LISTTASKPROG(x).acronimo_progetto,
                                             V_LISTTASKPROG(x).id_business,
                                             V_LISTTASKPROG(x).id_notifica,
                                             V_LISTTASKPROG(x).denominazione_lock,
											 r1.id_soggetto_beneficiario,
											 r1.codice_fiscale_beneficiario,
											 r1.denominazione_beneficiario,
                                             p_desc_breve_tipo_anagrafica,
											 v_desc_breve_regola);
        END LOOP;
     END LOOP;


   RETURN V_LISTTASKTOT;
  END FN_TODO_LIST;


-- Fuzione che restituisce la lista dei task
FUNCTION RETURN_LISTTASKTOT  RETURN LISTTASKTOT   IS
 
	 v_ruolo  VARCHAR2(30);
	 v_progr_bando_linea_intervento NUMBER(8);
	 v_id_regola NUMBER(8);
	 v_id_soggetto_beneficiario NUMBER(8);
	 v_id_progetto              NUMBER(8);

BEGIN
   IF G_PRIMA_ESECUZIONE THEN
      BEGIN
	     SELECT  RUOLO,
                 PROGR_BANDO_LINEA_INTERVENTO,
                 ID_REGOLA,
                 ID_SOGGETTO_BENEFICIARIO,
                 ID_PROGETTO
		  INTO   v_ruolo,
		         v_progr_bando_linea_intervento,
				 v_id_regola,
				 v_id_soggetto_beneficiario,
				 v_id_progetto
		  FROM PBANDI_C_TODOLIST;
	  
		  V_LISTTASKTOT := FN_TODO_LIST(v_ruolo,v_progr_bando_linea_intervento,v_id_regola,v_id_soggetto_beneficiario,v_id_progetto);
		  G_PRIMA_ESECUZIONE := FALSE;
	  
	  EXCEPTION
	     WHEN OTHERS THEN
		    RAISE_APPLICATION_ERROR(-20000,sqlerrm||' - MANCA CONFIGURAZIONE O CONFIGURAZIONE ERRATA SU TABELLA PBANDI_C_TODOLIST');
	  
	  END;
   END IF;


  RETURN V_LISTTASKTOT;
  
END RETURN_LISTTASKTOT;

PROCEDURE INIT AS
BEGIN
  G_PRIMA_ESECUZIONE := TRUE;
  
  --RAISE_APPLICATION_ERROR(-20000,'INIT');
END INIT;

BEGIN
    G_PRIMA_ESECUZIONE := TRUE;
END PCK_PBANDI_TODOLIST;
/
----------------------------------------------------------------------------------------------------------------


CREATE OR REPLACE FORCE VIEW PBANDI_V_TODOLIST
AS
   SELECT a.DESCR_BREVE_TASK,
          a.DESCR_TASK,
          a.ID_BUSINESS,
		  a.ID_PROGETTO,
          a.TITOLO_PROGETTO,
          a.CODICE_VISUALIZZATO,
          a.ACRONIMO_PROGETTO,
          a.PROGR_BANDO_LINEA_INTERVENTO,
          a.NOME_BANDO_LINEA,
          a.ID_SOGGETTO_BENEFICIARIO,
          a.CODICE_FISCALE_BENEFICIARIO,
          a.DENOMINAZIONE_BENEFICIARIO,
		  a.RUOLO,
		  a.DESCR_BREVE_REGOLA
     FROM TABLE (PCK_PBANDI_TODOLIST.RETURN_LISTTASKTOT) a;
	 

-- TRIGGER
-------------------------------------------------
CREATE OR REPLACE TRIGGER TG_PBANDI_C_TODOLIST_BU
   before update on PBANDI_C_TODOLIST
   referencing old as OLD new as NEW
   for each row

BEGIN
/*
  Rimetto lo stato di proma esecuzione  del package a TRUE
 */
  
  IF :NEW.PROGR_BANDO_LINEA_INTERVENTO IS NOT NULL 
    AND :NEW.ID_REGOLA IS NOT NULL THEN
	RAISE_APPLICATION_ERROR(-20000,'Valori incompatibili: Impostare Bando Linea o Regola');
  END IF;
  
  IF :NEW.ID_SOGGETTO_BENEFICIARIO IS NOT NULL 
    AND :NEW.ID_PROGETTO IS NOT NULL THEN
	RAISE_APPLICATION_ERROR(-20000,'Valori incompatibili: Impostare Beneficiario o Progetto');
  END IF; 
  
  PCK_PBANDI_TODOLIST.INIT;


END;
/


