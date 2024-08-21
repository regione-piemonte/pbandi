/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE TABLE TMP_UTENTI_NEW
(
  CODICE_FISCALE_PERSONA        VARCHAR2(32 BYTE) NOT NULL,
  NOME_PERSONA                  VARCHAR2(150 BYTE) NOT NULL,
  COGNOME_PERSONA               VARCHAR2(150 BYTE) NOT NULL,
  CODICE_FISCALE_BENEFICIARIO   VARCHAR2(32 BYTE),
  FLAG_RAPPRESENTATE_LEGALE     CHAR(1 BYTE),
  DT_CARICAMENTO                DATE,
  ID_SOGGETTO                   NUMBER(8),
  ENTE_COMPETENZA               VARCHAR2(1000 BYTE),
  TIPO_ANAGRAFICA               VARCHAR2(20 BYTE) NOT NULL,
  CODICE_VISUALIZZATO_PROGETTO  VARCHAR2(100 BYTE)
);

SET DEFINE OFF;
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, CODICE_FISCALE_BENEFICIARIO, FLAG_RAPPRESENTATE_LEGALE, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11K000S', 'DEMO', '30', '03264400965', 'S', -13, 'BENEFICIARIO');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, CODICE_FISCALE_BENEFICIARIO, FLAG_RAPPRESENTATE_LEGALE, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11K000S', 'DEMO', '30', '07028280019', 'S', -13, 'BENEFICIARIO');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, CODICE_FISCALE_BENEFICIARIO, FLAG_RAPPRESENTATE_LEGALE, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11N000V', 'DEMO', '33', '00498920016', 'N', -14, 'BENEFICIARIO');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, CODICE_FISCALE_BENEFICIARIO, FLAG_RAPPRESENTATE_LEGALE, ID_SOGGETTO, TIPO_ANAGRAFICA, CODICE_VISUALIZZATO_PROGETTO)
 Values
   ('AAAAAA00A11J000R', 'DEMO', '29', '00518460019', 'S', -12, 'BENEFICIARIO', '0115000029');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, CODICE_FISCALE_BENEFICIARIO, FLAG_RAPPRESENTATE_LEGALE, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11I000Q', 'DEMO', '28', '02981060045', 'S', -11, 'BENEFICIARIO');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, ENTE_COMPETENZA, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11I000Q', 'DEMO', '28', -11, 'Finpiemonte,', 'OI-IST-MASTER');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11I000Q', 'DEMO', '28', -11, 'BEN-MASTER');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, CODICE_FISCALE_BENEFICIARIO, FLAG_RAPPRESENTATE_LEGALE, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11R000Z', 'DEMO', '35', '05007770018', 'N', -15, 'BENEFICIARIO');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, CODICE_FISCALE_BENEFICIARIO, FLAG_RAPPRESENTATE_LEGALE, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11H000P', 'DEMO', '27', '09249740011', 'N', -10, 'BENEFICIARIO');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, CODICE_FISCALE_BENEFICIARIO, FLAG_RAPPRESENTATE_LEGALE, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11H000P', 'DEMO', '27', '03264400965', 'N', -10, 'BENEFICIARIO');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11H000P', 'DEMO', '27', -10, 'OI-ISTRUTTORE');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11O000W', 'DEMO', '34', -3, 'CSI-ADMIN');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11O000W', 'DEMO', '34', -3, 'BEN-MASTER');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11O000W', 'DEMO', '34', -3, 'ADG-CERT');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11O000W', 'DEMO', '34', -3, 'ADC-CERT');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11O000W', 'DEMO', '34', -3, 'ADA-OPE-MASTER');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, ENTE_COMPETENZA, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11O000W', 'DEMO', '34', -3, 'Finpiemonte,', 'OI-IST-MASTER');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, ENTE_COMPETENZA, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11O000W', 'DEMO', '34', -3, 'REGIONE PIEMONTE,REGIONE PIEMONTE DIREZIONE 05,REGIONE PIEMONTE DIREZIONE 06,REGIONE PIEMONTE DIREZIONE 07,REGIONE PIEMONTE DIREZIONE 08,REGIONE PIEMONTE DIREZIONE 09,REGIONE PIEMONTE DIREZIONE 10,REGIONE PIEMONTE DIREZIONE 11,REGIONE PIEMONTE DIREZIONE 12,REGIONE PIEMONTE DIREZIONE 13,REGIONE PIEMONTE DIREZIONE 14,REGIONE PIEMONTE DIREZIONE 15,REGIONE PIEMONTE DIREZIONE 16,REGIONE PIEMONTE DIREZIONE 17,REGIONE PIEMONTE DIREZIONE 18,REGIONE PIEMONTE DIREZIONE 19,REGIONE PIEMONTE DIREZIONE 20,REGIONE PIEMONTE STRUTTURA SPECIALE SB1,REGIONE PIEMONTE STRUTTURA SPECIALE SB2,', 'ADG-IST-MASTER');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11S000A', 'DEMO', '36', -1, 'CSI-ASSISTENZA');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11S000A', 'DEMO', '36', -1, 'BEN-MASTER');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11S000A', 'DEMO', '36', -1, 'ADG-CERT');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11S000A', 'DEMO', '36', -1, 'ADC-CERT');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11S000A', 'DEMO', '36', -1, 'ADA-OPE-MASTER');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, ENTE_COMPETENZA, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11S000A', 'DEMO', '36', -1, 'Finpiemonte,', 'OI-IST-MASTER');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, ENTE_COMPETENZA, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11S000A', 'DEMO', '36', -1, 'REGIONE PIEMONTE,REGIONE PIEMONTE DIREZIONE 05,REGIONE PIEMONTE DIREZIONE 06,REGIONE PIEMONTE DIREZIONE 07,REGIONE PIEMONTE DIREZIONE 08,REGIONE PIEMONTE DIREZIONE 09,REGIONE PIEMONTE DIREZIONE 10,REGIONE PIEMONTE DIREZIONE 11,REGIONE PIEMONTE DIREZIONE 12,REGIONE PIEMONTE DIREZIONE 13,REGIONE PIEMONTE DIREZIONE 14,REGIONE PIEMONTE DIREZIONE 15,REGIONE PIEMONTE DIREZIONE 16,REGIONE PIEMONTE DIREZIONE 17,REGIONE PIEMONTE DIREZIONE 18,REGIONE PIEMONTE DIREZIONE 19,REGIONE PIEMONTE DIREZIONE 20,REGIONE PIEMONTE STRUTTURA SPECIALE SB1,REGIONE PIEMONTE STRUTTURA SPECIALE SB2,', 'ADG-IST-MASTER');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11T000B', 'DEMO', '37', -2, 'CSI-MONIT');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, ENTE_COMPETENZA, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11C000K', 'DEMO', '22', -17, 'Finpiemonte,', 'OI-IST-MASTER');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11C000K', 'DEMO', '22', -17, 'BEN-MASTER');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11M000U', 'DEMO', '32', -19, 'OI-ISTRUTTORE');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11U000C', 'DEMO', '38', -20, 'OI-ISTRUTTORE');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11L000T', 'DEMO', '31', -18, 'ADG-ISTRUTTORE');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, CODICE_FISCALE_BENEFICIARIO, FLAG_RAPPRESENTATE_LEGALE, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11L000T', 'DEMO', '31', '80087670016-DB0900', 'S', -18, 'BENEFICIARIO');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11L000T', 'DEMO', '31', -18, 'ADC-CERT');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, ENTE_COMPETENZA, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11B000J', 'DEMO', '21', -16, 'REGIONE PIEMONTE,REGIONE PIEMONTE DIREZIONE 05,REGIONE PIEMONTE DIREZIONE 06,REGIONE PIEMONTE DIREZIONE 07,REGIONE PIEMONTE DIREZIONE 08,REGIONE PIEMONTE DIREZIONE 09,REGIONE PIEMONTE DIREZIONE 10,REGIONE PIEMONTE DIREZIONE 11,REGIONE PIEMONTE DIREZIONE 12,REGIONE PIEMONTE DIREZIONE 13,REGIONE PIEMONTE DIREZIONE 14,REGIONE PIEMONTE DIREZIONE 15,REGIONE PIEMONTE DIREZIONE 16,REGIONE PIEMONTE DIREZIONE 17,REGIONE PIEMONTE DIREZIONE 18,REGIONE PIEMONTE DIREZIONE 19,REGIONE PIEMONTE DIREZIONE 20,REGIONE PIEMONTE STRUTTURA SPECIALE SB1,REGIONE PIEMONTE STRUTTURA SPECIALE SB2,', 'ADG-IST-MASTER');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11B000J', 'DEMO', '21', -16, 'BEN-MASTER');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11G000O', 'DEMO', '26', -8, 'ADC-CERT');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11F000N', 'DEMO', '25', -9, 'ADG-CERT');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11E000M', 'DEMO', '24', -7, 'ADA-OPE-MASTER');
Insert into TMP_UTENTI_NEW
   (CODICE_FISCALE_PERSONA, NOME_PERSONA, COGNOME_PERSONA, ID_SOGGETTO, TIPO_ANAGRAFICA)
 Values
   ('AAAAAA00A11D000L', 'DEMO', '23', -21, 'CSI-ADMIN');
COMMIT;

CREATE OR REPLACE PROCEDURE PRC_BANDI_CARICAMENTO_UT_NEW IS
/******************************************************************************
   NAME:       PRC_BANDI_CARICAMENTO_UTENTI
   PURPOSE:    Consente di caricare gli utenti a partire dai dati presenti nella
               tabella TMP_UTENTI

   REVISIONS:
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.0        12/05/2009          1. Created this procedure.

   NOTES:

   Automatically available Auto Replace Keywords:
      Object Name:     PRC_BANDI_CARICAMENTO_UTENTI
      Sysdate:         12/05/2009
      Date and Time:   12/05/2009, 11.54.22, and 12/05/2009 11.54.22
      Username:         (set in TOAD Options, Procedure Editor)
      Table Name:       (set in the "New PL/SQL Object" dialog)

******************************************************************************/
  cursor curUtenti is select rowid,ut.*
                      from   tmp_utenti_new ut     
                      where  ut.DT_CARICAMENTO is null;
  
  TYPE recTbIdProg IS TABLE OF PBANDI_R_SOGGETTO_PROGETTO.ID_PROGETTO%TYPE index by pls_integer;
  
  lstTbIdProg recTbIdProg;
                        
  vNomeBatch          PBANDI_D_NOME_BATCH.NOME_BATCH%TYPE := 'PBAN-SERV-UTENTI';
  nIdProcessoBatch    PBANDI_L_PROCESSO_BATCH.id_processo_batch%TYPE := PCK_PBANDI_UTILITY_BATCH.insprocbatch(vNomeBatch);
  nIdSoggBen          PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE := null;
  nIdSoggPers         PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE;
  nIdSoggUt           PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE;
  nIdUtente           PBANDI_T_UTENTE.ID_UTENTE%TYPE;
  bFound              BOOLEAN := FALSE;
  nIdPersonaFisica    PBANDI_T_PERSONA_FISICA.ID_PERSONA_FISICA%TYPE;
  vCognome            PBANDI_T_PERSONA_FISICA.COGNOME%TYPE;    
  vNome               PBANDI_T_PERSONA_FISICA.NOME%TYPE;
  nProgSoggCorrelati  PBANDI_R_SOGGETTI_CORRELATI.PROGR_SOGGETTI_CORRELATI%TYPE;  
  nProgSoggProg       PBANDI_R_SOGGETTO_PROGETTO.PROGR_SOGGETTO_PROGETTO%TYPE;
  excErrore           EXCEPTION;  
  nCont               PLS_INTEGER;
  nContSta            PLS_INTEGER := 0;
  nContEcs            PLS_INTEGER := 0;
  nIdTipoAnagrafica   PBANDI_D_TIPO_ANAGRAFICA.ID_TIPO_ANAGRAFICA%TYPE;  
  nIdEnte             PBANDI_T_ENTE_COMPETENZA.ID_ENTE_COMPETENZA%TYPE;
  nIdProgetto         PBANDI_T_PROGETTO.ID_PROGETTO%TYPE;
  nPosI               PLS_INTEGER := 0;
  nPosj               PLS_INTEGER := 0;
  vElemCorr           VARCHAR2(100) := null;
  nPosK               PLS_INTEGER := 0;
BEGIN
  FOR recUtenti IN curUtenti LOOP
    BEGIN
    SELECT ta.ID_TIPO_ANAGRAFICA
    INTO   nIdTipoAnagrafica
    FROM   PBANDI_D_TIPO_ANAGRAFICA ta
    WHERE  ta.DESC_BREVE_TIPO_ANAGRAFICA = UPPER(recUtenti.TIPO_ANAGRAFICA);  
  
    BEGIN
      SELECT s.ID_SOGGETTO
      INTO   nIdSoggPers
      FROM   PBANDI_T_SOGGETTO s, PBANDI_D_TIPO_SOGGETTO ts
      WHERE  s.CODICE_FISCALE_SOGGETTO   = UPPER(recUtenti.CODICE_FISCALE_PERSONA) 
      AND    s.ID_TIPO_SOGGETTO          = ts.ID_TIPO_SOGGETTO
      AND    ts.DESC_BREVE_TIPO_SOGGETTO = 'PF';         
      
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        INSERT INTO PBANDI_T_SOGGETTO
        (ID_SOGGETTO, CODICE_FISCALE_SOGGETTO, ID_UTENTE_INS, ID_TIPO_SOGGETTO)
        VALUES
        (decode(recUtenti.ID_SOGGETTO,null,SEQ_PBANDI_T_SOGGETTO.NEXTVAL,recUtenti.ID_SOGGETTO),UPPER(recUtenti.CODICE_FISCALE_PERSONA),0,1)
        RETURNING ID_SOGGETTO INTO nIdSoggPers;
    END;
    
    BEGIN
      SELECT ID_SOGGETTO,ID_UTENTE
      INTO   nIdSoggUt  ,nIdUtente
      FROM   PBANDI_T_UTENTE
      WHERE  CODICE_UTENTE = UPPER(recUtenti.CODICE_FISCALE_PERSONA);
        
      if nIdSoggUt != nIdSoggPers then
        PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'W020','COD FISC PERSONA = '||UPPER(recUtenti.CODICE_FISCALE_PERSONA));
      end if;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        INSERT INTO PBANDI_T_UTENTE
        (ID_UTENTE, CODICE_UTENTE, ID_TIPO_ACCESSO, ID_SOGGETTO )
        VALUES
        (SEQ_PBANDI_T_UTENTE.NEXTVAL,UPPER(recUtenti.CODICE_FISCALE_PERSONA),5,nIdSoggPers)
        returning ID_UTENTE into nIdUtente;
    END;
    
    BEGIN
      SELECT ID_PERSONA_FISICA,COGNOME, NOME
      INTO   nIdPersonaFisica,vCognome,vNome
      FROM (
      SELECT ID_PERSONA_FISICA,COGNOME, NOME 
      FROM   PBANDI_T_PERSONA_FISICA
      WHERE  ID_SOGGETTO          = nIdSoggPers
      AND    UPPER(cognome)       = UPPER(recUtenti.COGNOME_PERSONA)
      AND    NVL(UPPER(nome),'0') = NVL(UPPER(recUtenti.NOME_PERSONA),'0')
      ORDER BY ID_PERSONA_FISICA DESC)
      WHERE ROWNUM = 1;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        INSERT INTO PBANDI_T_PERSONA_FISICA
        (ID_SOGGETTO, COGNOME, NOME,
         ID_PERSONA_FISICA, DT_INIZIO_VALIDITA,ID_UTENTE_INS )
        VALUES
        (nIdSoggPers,recUtenti.COGNOME_PERSONA,recUtenti.NOME_PERSONA,
         SEQ_PBANDI_T_PERSONA_FISICA.NEXTVAL,SYSDATE,0)
        RETURNING ID_PERSONA_FISICA INTO nIdPersonaFisica;
    END;
    
    SELECT COUNT(*)
    INTO   nContSta
    FROM   PBANDI_R_SOGG_TIPO_ANAGRAFICA
    WHERE  ID_SOGGETTO        = nIdSoggPers
    AND    ID_TIPO_ANAGRAFICA = nIdTipoAnagrafica
    AND    DT_FINE_VALIDITA   IS NULL; 
      
    IF nContSta = 0 THEN
      INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA
      (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA)
      VALUES
      (nIdSoggPers,nIdTipoAnagrafica,0,SYSDATE);
    END IF;  
    
    vElemCorr := NULL;
    nPosI     := 0;
    nPosj     := 0;
    nPosK     := 0;
    
    IF UPPER(recUtenti.TIPO_ANAGRAFICA) IN ('ADG-IST-MASTER','OI-IST-MASTER',
                                            'ADG-ISTRUTTORE','OI-ISTRUTTORE',
                                            'CREATOR') THEN
      LOOP
        nPosJ := nPosJ+1;
        nPosI := instr(recUtenti.ENTE_COMPETENZA,',',1,nPosJ);
        EXIT WHEN nPosI=0;

        vElemCorr := SUBSTR(recUtenti.ENTE_COMPETENZA,nPosK+1,(nPosI-1)-nPosK);
        nPosK     := nPosI;
      
        BEGIN
          SELECT ID_ENTE_COMPETENZA
          INTO   nIdEnte
          FROM   PBANDI_T_ENTE_COMPETENZA
          WHERE  DT_FINE_VALIDITA IS NULL
          AND    UPPER(DESC_BREVE_ENTE) = UPPER(vElemCorr);
        
          SELECT COUNT(*)
          INTO   nContEcs
          FROM   PBANDI_R_ENTE_COMPETENZA_SOGG ecs
          WHERE  ecs.ID_ENTE_COMPETENZA = nIdEnte
          AND    ecs.ID_SOGGETTO        = nIdSoggPers  
          AND    ecs.DT_FINE_VALIDITA   IS NULL;  
        
          IF nContEcs = 0 THEN
            INSERT INTO PBANDI_R_ENTE_COMPETENZA_SOGG
            (ID_ENTE_COMPETENZA, ID_SOGGETTO, ID_UTENTE_INS)
            VALUES
            (nIdEnte,nIdSoggPers,0);
          END IF;  
        EXCEPTION
          WHEN OTHERS THEN
            PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'W054','DESC_ENTE = '||vElemCorr||' '||sqlerrm);
            RAISE excErrore;
        END;
      END LOOP;
    END IF;
    
    IF UPPER(recUtenti.TIPO_ANAGRAFICA) = ('PERSONA-FISICA') THEN
      BEGIN
        SELECT ID_SOGGETTO
        INTO   nIdSoggBen
        FROM   PBANDI_T_SOGGETTO
        WHERE  CODICE_FISCALE_SOGGETTO = UPPER(recUtenti.CODICE_FISCALE_BENEFICIARIO) ;
          
        bFound := TRUE;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN 
          begin
            select distinct sp.ID_SOGGETTO
            into   nIdSoggBen
            from   PBANDI_T_SEDE s,PBANDI_R_SOGG_PROGETTO_SEDE ps,
                   PBANDI_R_SOGGETTO_PROGETTO sp
            where  s.PARTITA_IVA              = UPPER(recUtenti.CODICE_FISCALE_BENEFICIARIO)
            and    s.DT_FINE_VALIDITA         is null
            and    ps.ID_SEDE                 = s.ID_SEDE
            and    ps.ID_TIPO_SEDE            = 1  
            and    ps.PROGR_SOGGETTO_PROGETTO = sp.PROGR_SOGGETTO_PROGETTO
            and    sp.DT_FINE_VALIDITA        is null;
              
            bFound := TRUE;
          exception
            when others then
              PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E069','COD FISC BENEF = '||recUtenti.CODICE_FISCALE_BENEFICIARIO);
              bFound     := FALSE;
              nIdSoggBen := null;
          end;    
      END;
      
      IF bFound THEN
        BEGIN
          select PROGR_SOGGETTI_CORRELATI
          into   nProgSoggCorrelati
          from   PBANDI_R_SOGGETTI_CORRELATI
          where  ID_SOGGETTO                = nIdSoggPers
          and    ID_SOGGETTO_ENTE_GIURIDICO = nIdSoggBen 
          and    DT_FINE_VALIDITA           is null   
          and    ID_TIPO_SOGGETTO_CORRELATO = DECODE(recUtenti.FLAG_RAPPRESENTATE_LEGALE,'S',1,6)
          and    rownum                     = 1;
        exception
          when no_data_found then
            INSERT INTO PBANDI_R_SOGGETTI_CORRELATI
            (ID_TIPO_SOGGETTO_CORRELATO, ID_UTENTE_INS, ID_SOGGETTO, 
             ID_SOGGETTO_ENTE_GIURIDICO, DT_INIZIO_VALIDITA, 
             PROGR_SOGGETTI_CORRELATI)
            VALUES
            (DECODE(recUtenti.FLAG_RAPPRESENTATE_LEGALE,'S',1,6),0,nIdSoggPers,
             nIdSoggBen,sysdate,SEQ_PBANDI_R_SOGG_CORRELATI.NEXTVAL)
            returning PROGR_SOGGETTI_CORRELATI into nProgSoggCorrelati;
          when others then  
            PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E070','nIdSoggPers = '||nIdSoggPers||' '||sqlerrm);
            RAISE excErrore;
        end;
        
        IF recUtenti.CODICE_VISUALIZZATO_PROGETTO IS NULL THEN
          begin
            select distinct id_progetto bulk collect
            into   lstTbIdProg
            from   pbandi_r_soggetto_progetto
            where  id_soggetto        = nIdSoggBen
            and    dt_fine_validita   is null
            and    id_tipo_anagrafica = 1
            and    id_tipo_beneficiario <> 4;
        
            for i in lstTbIdProg.first..lstTbIdProg.last loop
              begin
                select PROGR_SOGGETTO_PROGETTO 
                into   nProgSoggProg
                from   pbandi_r_soggetto_progetto  
                where  ID_PERSONA_FISICA  = nIdPersonaFisica
                and    ID_PROGETTO        = lstTbIdProg(i)
                and    ID_SOGGETTO        = nIdSoggPers
                and    ID_TIPO_ANAGRAFICA = (select ID_TIPO_ANAGRAFICA from PBANDI_D_TIPO_ANAGRAFICA WHERE DESC_BREVE_TIPO_ANAGRAFICA = recUtenti.TIPO_ANAGRAFICA)
                and    rownum             = 1;
              exception  
                when no_data_found then    
                  insert into pbandi_r_soggetto_progetto
                  (ID_SOGGETTO, ID_PROGETTO, PROGR_SOGGETTO_PROGETTO, 
                   DT_INIZIO_VALIDITA,ID_TIPO_ANAGRAFICA, ID_PERSONA_FISICA, id_utente_ins )
                  values
                  (nIdSoggPers,lstTbIdProg(i),seq_pbandi_r_soggetto_progetto.nextval,
                   sysdate,(select ID_TIPO_ANAGRAFICA from PBANDI_D_TIPO_ANAGRAFICA WHERE DESC_BREVE_TIPO_ANAGRAFICA = recUtenti.TIPO_ANAGRAFICA),nIdPersonaFisica,0)
                  returning PROGR_SOGGETTO_PROGETTO into nProgSoggProg;
              end;
            
              select count(*)
              into   nCont
              from   pbandi_r_sogg_prog_sogg_correl
              where  PROGR_SOGGETTO_PROGETTO  = nProgSoggProg
              and    PROGR_SOGGETTI_CORRELATI = nProgSoggCorrelati; 
                    
              if nCont = 0 then  
                insert into pbandi_r_sogg_prog_sogg_correl
                (PROGR_SOGGETTO_PROGETTO, PROGR_SOGGETTI_CORRELATI, ID_UTENTE_INS )
                values
                (nProgSoggProg,nProgSoggCorrelati,0);
              end if;
            end loop;
          EXCEPTION
            WHEN NO_DATA_FOUND THEN
              PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E071','nIdSoggBen = '||nIdSoggBen);
              RAISE excErrore;
          end;   
        ELSE
          BEGIN
            SELECT ID_PROGETTO
            INTO   nIdProgetto
            FROM   PBANDI_T_PROGETTO
            WHERE  CODICE_VISUALIZZATO = recUtenti.CODICE_VISUALIZZATO_PROGETTO;
            
            begin
              select PROGR_SOGGETTO_PROGETTO 
              into   nProgSoggProg
              from   pbandi_r_soggetto_progetto  
              where  ID_PERSONA_FISICA  = nIdPersonaFisica
              and    ID_PROGETTO        = nIdProgetto
              and    ID_SOGGETTO        = nIdSoggPers
              and    ID_TIPO_ANAGRAFICA = (select ID_TIPO_ANAGRAFICA from PBANDI_D_TIPO_ANAGRAFICA WHERE DESC_BREVE_TIPO_ANAGRAFICA = recUtenti.TIPO_ANAGRAFICA)
              and    rownum             = 1;
            exception  
              when no_data_found then    
                insert into pbandi_r_soggetto_progetto
                (ID_SOGGETTO, ID_PROGETTO, PROGR_SOGGETTO_PROGETTO, 
                 DT_INIZIO_VALIDITA,ID_TIPO_ANAGRAFICA, ID_PERSONA_FISICA, id_utente_ins )
                values
                (nIdSoggPers,nIdProgetto,seq_pbandi_r_soggetto_progetto.nextval,
                 sysdate,(select ID_TIPO_ANAGRAFICA from PBANDI_D_TIPO_ANAGRAFICA WHERE DESC_BREVE_TIPO_ANAGRAFICA = recUtenti.TIPO_ANAGRAFICA),nIdPersonaFisica,0)
                returning PROGR_SOGGETTO_PROGETTO into nProgSoggProg;
            end;
            
            select count(*)
            into   nCont
            from   pbandi_r_sogg_prog_sogg_correl
            where  PROGR_SOGGETTO_PROGETTO  = nProgSoggProg
            and    PROGR_SOGGETTI_CORRELATI = nProgSoggCorrelati; 
                
            if nCont = 0 then  
              insert into pbandi_r_sogg_prog_sogg_correl
              (PROGR_SOGGETTO_PROGETTO, PROGR_SOGGETTI_CORRELATI, ID_UTENTE_INS )
              values
              (nProgSoggProg,nProgSoggCorrelati,0);
            end if;
          EXCEPTION
            WHEN OTHERS THEN
              PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E071','CODICE_VISUALIZZATO = '||recUtenti.CODICE_VISUALIZZATO_PROGETTO);
              RAISE excErrore;
          END; 
          
        END IF;
        
        
      END IF;
    END IF;
    update tmp_utenti_new
    set    DT_CARICAMENTO = sysdate
    where  rowid          = recUtenti.rowid;
    COMMIT; 
    EXCEPTION
        WHEN excErrore THEN
          ROLLBACK;    
      END;
  END LOOP;
  
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('ERRORE = '||SQLERRM||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;  
END PRC_BANDI_CARICAMENTO_UT_NEW;
/



