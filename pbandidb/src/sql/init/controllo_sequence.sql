/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

DECLARE
  TYPE tpSeqTab IS RECORD (nomeSequence  VARCHAR2(30),
                                   nomeTabella   VARCHAR2(30));
                           
  TYPE typTbSeqTab IS TABLE OF tpSeqTab INDEX BY BINARY_INTEGER;                           
  
  seqErrate  typTbSeqTab;
  
  -- Cursore che seleziona tutte le sequence presenti. Tutte le sequence soddisfano
  -- la nomenclatura seq_NomeTabella
  CURSOR curSeq IS SELECT REPLACE(SEQUENCE_NAME,'SEQ_','') NAME,LAST_NUMBER 
                      FROM   SEQ;

  nCont         PLS_INTEGER;
  nContTab      PLS_INTEGER;
  vCol             VARCHAR2(30);
  vGrantee        USER_TAB_PRIVS_MADE.GRANTEE%TYPE;          
  vPrivilege    USER_TAB_PRIVS_MADE.PRIVILEGE%TYPE;
  nSeq            PLS_INTEGER := 18;
  vNomeSeq        VARCHAR2(30);
  vNomeTab        VARCHAR2(30);     
  R_NUM_IND     NUMBER;
  D_NUM_IND     NUMBER;
  N_NUM_IND     NUMBER;
  NUM_FAM        NUMBER;
  NUM_FAM_AIRE  NUMBER;
  
BEGIN
  -- Esistono delle sequence che non seguono la nomeclatura seq_NomeTabella
  seqErrate(1).nomeSequence := 'PBANDI_T_ESTREMI_BANCA';        
  seqErrate(2).nomeSequence := 'PBANDI_T_PRATICA_ISTRUT';        
  seqErrate(3).nomeSequence := 'PBANDI_T_RIGO_CONTO_ECONOM';  
  seqErrate(4).nomeSequence := 'PBANDI_R_BANDO_LINEA_INTER'; 
  seqErrate(5).nomeSequence := 'PBANDI_T_DOCUMENTO_SPESA';
  seqErrate(6).nomeSequence := 'PBANDI_T_QUOT_PARTE_DOC_SP';
  seqErrate(7).nomeSequence := 'PBANDI_R_SOGG_CORRELATI';
  seqErrate(8).nomeSequence := 'PBANDI_R_BANDO_LIN_ENTE_CO';
  seqErrate(9).nomeSequence := 'PBANDI_R_ENTE_GIURID_SEDE';
  seqErrate(10).nomeSequence := 'PBANDI_R_SOGG_DOMANDA_SEDE';
  seqErrate(11).nomeSequence := 'PBANDI_R_SOGG_PROG_SEDE';
  seqErrate(12).nomeSequence := 'PBANDI_T_DICHIARAZIONE_SPE';
  seqErrate(13).nomeSequence := 'PBANDI_R_FORNITORE_QUALIF';
  
  seqErrate(1).nomeTabella := 'PBANDI_T_ESTREMI_BANCARI';     
  seqErrate(2).nomeTabella := 'PBANDI_T_PRATICA_ISTRUTTORIA';   
  seqErrate(3).nomeTabella := 'PBANDI_T_RIGO_CONTO_ECONOMICO';     
  seqErrate(4).nomeTabella := 'PBANDI_R_BANDO_LINEA_INTERVENT';
  seqErrate(5).nomeTabella := 'PBANDI_T_DOCUMENTO_DI_SPESA';
  seqErrate(6).nomeTabella := 'PBANDI_T_QUOTA_PARTE_DOC_SPESA';
  seqErrate(7).nomeTabella := 'PBANDI_R_SOGGETTI_CORRELATI';
  seqErrate(8).nomeTabella := 'PBANDI_R_BANDO_LINEA_ENTE_COMP';
  seqErrate(9).nomeTabella := 'PBANDI_R_ENTE_GIURIDICO_SEDE';
  seqErrate(10).nomeTabella := 'PBANDI_R_SOGGETTO_DOMANDA_SEDE';
  seqErrate(11).nomeTabella := 'PBANDI_R_SOGG_PROGETTO_SEDE';
  seqErrate(12).nomeTabella := 'PBANDI_T_DICHIARAZIONE_SPESA'; 
  seqErrate(13).nomeTabella := 'PBANDI_R_FORNITORE_QUALIFICA';
  
  FOR recSeq IN curSeq LOOP
    vNomeSeq := NULL;
    BEGIN
      -- Controllo se la sequence segue la nomeclatura seq_NomeTabella
      SELECT COUNT(*)
      INTO   nContTab
      FROM   TABS
      WHERE  TABLE_NAME = recSeq.NAME;
        
      IF nContTab = 0 THEN
        -- Se non segue la nomeclatura leggo il record Type
        FOR i IN seqErrate.first..seqErrate.last LOOP
          IF seqErrate(i).nomeSequence = recSeq.NAME THEN
            vNomeSeq := seqErrate(i).nomeTabella;  
          END IF;        
        END LOOP;
      ELSE
        vNomeSeq := recSeq.NAME;
      END IF;  
      -- Le sequence vengono utilizzate per valorizzare la pk di una tabella, come questa select
      -- si ha il nome della colonna pk della tabella che stiamo processando
        EXECUTE IMMEDIATE ('SELECT COLUMN_NAME FROM ('||
                           'SELECT COLUMN_NAME '|| 
                               'FROM USER_CONSTRAINTS A,USER_CONS_COLUMNS B '||
                               'WHERE  A.CONSTRAINT_TYPE = ''P'' '||
                           'AND    A.TABLE_NAME = '||''''||vNomeSeq||''''||' '||
                           'AND    A.CONSTRAINT_NAME = B.CONSTRAINT_NAME '||
                           'ORDER BY POSITION DESC) '||
                           'WHERE ROWNUM = 1') INTO vCol;              
      
      
      -- Si prende il valore massimo della colonna pk della tabella che stiamo processando
      EXECUTE IMMEDIATE ('SELECT NVL(MAX('||vCol||'),''0'') FROM '||vNomeSeq) INTO nCont;
      
      -- La sequence risulta disallineata se il valore massimo della colonna pk é diversa dall'ultimo
      -- numero della sequence - 1
      IF nCont != (recSeq.LAST_NUMBER - 1) THEN
        -- Per alterare la sequence, bisogna prima dropparla e poi ricrearla facendola partire dal valore massimo
        -- della pk + 1
        EXECUTE IMMEDIATE('DROP SEQUENCE SEQ_'||recSeq.NAME);
        EXECUTE IMMEDIATE('CREATE SEQUENCE SEQ_'||recSeq.NAME||' START WITH '||(nCont + 1)||' NOCACHE');
        
       -- Controllo se la sequence fornisce delle grant ad un utente Oracle 
       FOR recGrant IN (SELECT GRANTEE,PRIVILEGE
                           FROM   USER_TAB_PRIVS_MADE
                          WHERE  TABLE_NAME = 'SEQ_'||recSeq.NAME) LOOP
         
         -- Nel caso in cui la sequence dà le grant ad un altro utente, bisogna creare tale script
         EXECUTE IMMEDIATE('GRANT '||recGrant.PRIVILEGE||' ON SEQ_'||recSeq.NAME||' TO '||recGrant.GRANTEE);
       END LOOP;             
      END IF;
    EXCEPTION
      WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('TABELLA = '||recSeq.NAME||' ERRORE = '||SQLERRM);
    END;      
  END LOOP;
  
  
END;
/      
