/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE OR REPLACE PACKAGE BODY PCK_PBANDI_CAMPIONAMENTO AS
/*******************************************************************************
    NAME:       PCK_PBANDI_CAMPIONAMENTO
    PURPOSE:    Package campionamento certificazione

   REVISIONS:
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.0.0    23/01/2019  MC             Created this package.
*******************************************************************************/
vNomeBatch                      PBANDI_D_NOME_BATCH.NOME_BATCH%TYPE := 'PBAN-CAMPIONAMENTO';
nIdProcessoBatch                PBANDI_L_PROCESSO_BATCH.id_processo_batch%TYPE := PCK_PBANDI_UTILITY_BATCH.insprocbatch(vNomeBatch);
vRetVal                         NUMBER:= 0;
vIdCampione                     PBANDI_T_CAMPIONAMENTO.ID_CAMPIONE%TYPE;
v_Min_U2                                 NUMBER; -- progressivo operazione min
v_Max_U2                                 NUMBER; -- progressivo operazione max
v_random                                 NUMBER;
--v_Start                                  NUMBER:=0;
v_Reset                                  NUMBER:=0; -- se uguale a 1 e' necessario rifare la verifica mantenendo
                                                    --solo il primo estratto c2
v_nj                                     NUMBER:=0;
v_primo_estratto_C2                      NUMBER;


FUNCTION fnc_crea_campionamento (pDescBreveLinea PBANDI_D_LINEA_DI_INTERVENTO.DESC_BREVE_LINEA%TYPE, pID_UTENTE_INS NUMBER) RETURN NUMBER AS

            CURSOR  CertificazioneAperta(cDescBreveLinea PBANDI_D_LINEA_DI_INTERVENTO.DESC_BREVE_LINEA%TYPE)
            IS
            SELECT    pc.ID_PROPOSTA_CERTIFICAZ, DT_ORA_CREAZIONE
            FROM      PBANDI_T_PROPOSTA_CERTIFICAZ pc,
                           PBANDI_R_PROPOSTA_CERTIF_LINEA pcl
            WHERE
                       pc.ID_PROPOSTA_CERTIFICAZ =        pcl.ID_PROPOSTA_CERTIFICAZ AND
                       pcl.ID_LINEA_DI_INTERVENTO =        (SELECT ID_LINEA_DI_INTERVENTO
                                                                              FROM    PBANDI_D_LINEA_DI_INTERVENTO
                                                                              WHERE  DESC_BREVE_LINEA=cDescBreveLinea) AND
                       pc.ID_STATO_PROPOSTA_CERTIF =  (SELECT ID_STATO_PROPOSTA_CERTIF
                                                                               FROM PBANDI_D_STATO_PROPOSTA_CERTIF
                                                                               WHERE DESC_BREVE_STATO_PROPOSTA_CERT = '05open') --APERTA
             ORDER BY ID_PROPOSTA_CERTIFICAZ DESC;

             pIdLineaIntervento             PBANDI_D_LINEA_DI_INTERVENTO. ID_LINEA_DI_INTERVENTO%TYPE;
             vIdPropostaCertificaz         PBANDI_T_PROPOSTA_CERTIFICAZ.ID_PROPOSTA_CERTIFICAZ%TYPE; -- id della certificazione --> aperta
             vDataOraCreazione            PBANDI_T_PROPOSTA_CERTIFICAZ.DT_ORA_CREAZIONE%TYPE;
             vProgressivoOperazione     NUMBER:= 0;
             vIdPeriodo                         PBANDI_T_PERIODO.ID_PERIODO%TYPE;
             vIdTipoAnagrafica              PBANDI_D_TIPO_ANAGRAFICA.ID_TIPO_ANAGRAFICA%TYPE;
             vAsse                               PBANDI_D_LINEA_DI_INTERVENTO.DESC_BREVE_LINEA%TYPE;
             vCount                              NUMBER;

BEGIN

-- Recupero l'ultima proposta di certificazione aperta per la normativa PORFESR 2014-2020

            OPEN CertificazioneAperta(pDescBreveLinea);
            FETCH CertificazioneAperta INTO vIdPropostaCertificaz, vDataOraCreazione;
            IF CertificazioneAperta%NOTFOUND THEN
                 PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
                 PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E171', ' ERRORE = NON ESISTE UNA CERTIFICAZIONE CON STATO APERTA');
                 ROLLBACK;
                 --RAISE_APPLICATION_ERROR (-20000,'NON ESISTE UNA CERTIFICAZIONE APERTA');
                 RETURN -3; -- mc jira 3079 -- 22-12-2021

            END IF;

           CLOSE CertificazioneAperta;

 --  Recupero id linea di intervento
      SELECT ID_LINEA_DI_INTERVENTO
      INTO     pIdLineaIntervento
      FROM    PBANDI_D_LINEA_DI_INTERVENTO
      WHERE  DESC_BREVE_LINEA=pDescBreveLinea;


 -- Verifico che per la certificazione aperta non sia giÃ?Â  stato effettuato un campionamento precedente
           SELECT  Count(*)
           INTO    vCount
           FROM    PBANDI_T_CAMPIONAMENTO
           WHERE  ID_PROPOSTA_CERTIFICAZ = vIdPropostaCertificaz;

           IF vCount >0 THEN
                 PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
                 PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E171', ' ERRORE = LA CERTIFICAZIONE E'' GIA'' STATA CAMPIONATA: '||vIdPropostaCertificaz);
              --RAISE_APPLICATION_ERROR (-20000,'LA CERTIFICAZIONE E'' GIA'' STATA CAMPIONATA ');
                ROLLBACK;
                RETURN -2; -- LA CERTIFICAZIONE E'' GIA'' STATA CAMPIONATA
           END IF;

 -- Recupero id ruolo  preposto al campionamento

          SELECT  ID_CATEG_ANAGRAFICA
          INTO      vIdTipoAnagrafica
          FROM     PBANDI_D_CATEG_ANAGRAFICA
          WHERE   DESC_CATEG_ANAGRAFICA = 'AdC';

 -- Recupero il periodo di competenza della certificazione
          SELECT ID_PERIODO
          INTO     vIdPeriodo
          FROM    PBANDI_T_PERIODO
          WHERE  vDataOraCreazione BETWEEN DT_INIZIO_CONTABILE AND DT_FINE_CONTABILE;

-- Si inserisce il record master sulla PBANDI_T_CAMPIONAMENTO

           INSERT INTO PBANDI_T_CAMPIONAMENTO
           (ID_CAMPIONE,
            DATA_CAMPIONAMENTO,
            ID_PROPOSTA_CERTIFICAZ,
            ID_LINEA_DI_INTERVENTO,
            ID_CATEG_ANAGRAFICA,
            ID_PERIODO,
            TIPO_CONTROLLI,
      ID_UTENTE_INS,
            DATA_INSERIMENTO
            ) VALUES
           (SEQ_PBANDI_T_CAMPIONAMENTO.NEXTVAL,
            TRUNC(SYSDATE),
            vIdPropostaCertificaz,
            pIdLineaIntervento,
            vIdTipoAnagrafica ,
            vIdPeriodo,
            'D',
      pID_UTENTE_INS,
            SYSDATE
            ) RETURNING ID_CAMPIONE INTO  vIdCampione;

-- Sulla tabella di lavoro si inseriscono i progetti oggetto dell' algoritmo di campionamento

          FOR  ProgettiCampione IN
          (SELECT   pc.ID_DETT_PROPOSTA_CERTIF,
                          tp.ID_PROGETTO,
                          tp.TITOLO_PROGETTO,
                          tp.CODICE_VISUALIZZATO,
                          pc.AVANZAMENTO
             FROM    PBANDI_T_DETT_PROPOSTA_CERTIF pc,
                          PBANDI_T_PROGETTO tp
             WHERE   tp.ID_PROGETTO = pc.ID_PROGETTO AND
                          ID_PROPOSTA_CERTIFICAZ = vIdPropostaCertificaz AND
                          pc.AVANZAMENTO > 0 AND
                          pc.id_progetto_sif IS NULL -- Jira PBANDI-2940 -- mc svil 2021
             ORDER BY pc.AVANZAMENTO DESC
            )
            LOOP
                  --progressivo operazione
                     vProgressivoOperazione := vProgressivoOperazione +1;
                  --
                  --asse
                  SELECT DESC_BREVE_LINEA
                  INTO     vAsse
                  FROM    PBANDI_D_LINEA_DI_INTERVENTO
                  WHERE   ID_TIPO_LINEA_INTERVENTO = 2 --  ASSE LEGATA AL PROGETTO
                  CONNECT BY PRIOR id_linea_di_intervento_padre = id_linea_di_intervento
                            START WITH id_linea_di_intervento =
                              (SELECT c.id_linea_di_intervento
                                 FROM PBANDI_T_PROGETTO a,
                                      PBANDI_T_DOMANDA b,
                                      PBANDI_R_BANDO_LINEA_INTERVENT c
                                  WHERE a.id_progetto =  ProgettiCampione.ID_PROGETTO
                                      AND b.id_domanda = a.id_domanda
                                      AND c.progr_bando_linea_intervento =
                                             b.progr_bando_linea_intervento);
                     --


                  INSERT INTO PBANDI_W_CAMPIONAMENTO
                  (
                   ID_CAMPIONE,
                   PROGR_OPERAZIONE,
                   ID_DETT_PROPOSTA_CERTIF,
                   ASSE,
                   ID_PROGETTO,
                   TITOLO_PROGETTO,
                   CODICE_VISUALIZZATO,
                   AVANZAMENTO,
                   UNIVERSO,
                   FLAG_ESCLUDI)
                 VALUES
                   (vIdCampione,
                    vProgressivoOperazione,
                    ProgettiCampione.ID_DETT_PROPOSTA_CERTIF ,
                    vAsse,
                    ProgettiCampione.ID_PROGETTO ,
                    ProgettiCampione.TITOLO_PROGETTO ,
                    ProgettiCampione.CODICE_VISUALIZZATO ,
                    ProgettiCampione.AVANZAMENTO ,
                    'U1' ,-- DI DEFAULT, successivamente  i record con importo avanzamento < m75 (soglia) saranno trasformati in U2  con l'elaborazione dell' algoritmo
                    'N'); -- DI DEFAULT, successivamente vengono valorizzati a S i record oggetto di estrazione

            END LOOP;

            vRetVal := 0;
            RETURN vRetVal;

EXCEPTION WHEN OTHERS THEN
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E171', SQLERRM || ' ERRORE SU:  fnc_crea_campionamento  riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
    RAISE_APPLICATION_ERROR (-20000,' ERRORE SU: fnc_crea_campionamento');
END fnc_crea_campionamento;
--

FUNCTION fnc_crea_campionamento_FSE(pID_UTENTE_INS NUMBER) RETURN NUMBER AS

                pIdLineaIntervento            PBANDI_D_LINEA_DI_INTERVENTO. ID_LINEA_DI_INTERVENTO%TYPE:=232; -- 'POR-FSE 2014-2020'
                vIdTipoAnagrafica             PBANDI_D_TIPO_ANAGRAFICA.ID_TIPO_ANAGRAFICA%TYPE;
                vDataOraCreazione             DATE:=TRUNC(SYSDATE);
                vIdCertificazioneFSE          NUMBER;
                vIdPeriodo                    PBANDI_T_PERIODO.ID_PERIODO%TYPE;
                vProgressivoOperazione        NUMBER:= 0;
                vCount                        NUMBER;

BEGIN

    -- Recupero id ruolo  preposto al campionamento

          SELECT  ID_CATEG_ANAGRAFICA
          INTO      vIdTipoAnagrafica
          FROM     PBANDI_D_CATEG_ANAGRAFICA
          WHERE   DESC_CATEG_ANAGRAFICA = 'AdC';

    -- Recupero il periodo di competenza della certificazione

          SELECT DISTINCT DATA_CERTIFICAZIONE,
                          ID_CERTIFICAZIONE
          INTO            vDataOraCreazione,
                          vIdCertificazioneFSE
          FROM            PBANDI_W_CAMPIONE_FSE;

          SELECT ID_PERIODO
          INTO     vIdPeriodo
          FROM    PBANDI_T_PERIODO
          WHERE  vDataOraCreazione BETWEEN DT_INIZIO_CONTABILE AND DT_FINE_CONTABILE;

     -- Verifico che per la certificazione FSE non sia giÃ?Â  stato effettuato un campionamento precedente
           SELECT  Count(*)
           INTO    vCount
           FROM    PBANDI_T_CAMPIONAMENTO
           WHERE   ID_PROPOSTA_CERTIFICAZ =  vIdCertificazioneFSE;

           IF vCount >0 THEN
                 PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
                 PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E171', ' ERRORE = LA CERTIFICAZIONE E'' GIA'' STATA CAMPIONATA: '||vIdCertificazioneFSE);
                 RETURN -2; -- LA CERTIFICAZIONE E'' GIA'' STATA CAMPIONATA
           END IF;



     -- Si inserisce il record master sulla PBANDI_T_CAMPIONAMENTO

           INSERT INTO PBANDI_T_CAMPIONAMENTO
           (ID_CAMPIONE,
            DATA_CAMPIONAMENTO,
            ID_PROPOSTA_CERTIFICAZ,
            ID_LINEA_DI_INTERVENTO,
            ID_CATEG_ANAGRAFICA,
            ID_PERIODO,
            TIPO_CONTROLLI,
            DATA_INSERIMENTO,
            ID_UTENTE_INS
            ) VALUES
           (SEQ_PBANDI_T_CAMPIONAMENTO.NEXTVAL,
            TRUNC(sysdate),
            vIdCertificazioneFSE, --vIdPropostaCertificaz,
            pIdLineaIntervento,
            vIdTipoAnagrafica ,
            vIdPeriodo,
            'D',
      sysdate,
            pID_UTENTE_INS
            ) RETURNING ID_CAMPIONE INTO  vIdCampione;

     -- Sulla tabella di lavoro si inseriscono i progetti oggetto dell'algoritmo di campionamento

              FOR  ProgettiCampione IN
              (SELECT    *
               FROM       PBANDI_W_CAMPIONE_FSE
               WHERE     AVANZAMENTO > 0
               ORDER BY AVANZAMENTO DESC
              )
              LOOP
                          --progressivo operazione
                             vProgressivoOperazione := vProgressivoOperazione +1;
                          --
                          INSERT INTO PBANDI_W_CAMPIONAMENTO
                          (
                           ID_CAMPIONE,
                           PROGR_OPERAZIONE,
                           ID_DETT_PROPOSTA_CERTIF,
                           ASSE,
                           ID_PROGETTO,
                           TITOLO_PROGETTO,
                           CODICE_VISUALIZZATO,
                           AVANZAMENTO,
                           UNIVERSO,
                           FLAG_ESCLUDI)
                         VALUES
                           (vIdCampione,
                            vProgressivoOperazione,
                            NULL, --ProgettiCampione.ID_DETT_PROPOSTA_CERTIF ,
                            ProgettiCampione.ASSE,
                            ProgettiCampione.ID_PROGETTO,
                            ProgettiCampione.TITOLO_PROGETTO ,
                            NULL, --ProgettiCampione.CODICE_VISUALIZZATO ,
                            ProgettiCampione.AVANZAMENTO ,
                            'U1' ,-- DI DEFAULT, successivamente  i record con importo avanzamento < m75 (soglia) saranno trasformati in U2  con l'elaborazione dell' algoritmo
                            'N'); -- DI DEFAULT, successivamente vengono valorizzati a S i record oggetto di estrazione

              END LOOP;

        -- svuoto la cartella di lavoro
        -- DELETE FROM PBANDI_W_CAMPIONE_FSE;
            --
            vRetVal := 0;
            RETURN vRetVal;

EXCEPTION WHEN OTHERS THEN
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E171', SQLERRM|| ' ERRORE SU:  fnc_crea_campionamento_FSE riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
    RAISE_APPLICATION_ERROR (-20000, 'ERRORE SU: fnc_crea_campionamento_FSE');
END fnc_crea_campionamento_FSE;

-- la funzione fnc_crea_campione_c1 viene utilizzata dalla FUNCTION fnc_elabora_campionamento per definire il campione C1
FUNCTION fnc_crea_campione_c1(pSogliaSommaU1 PBANDI_T_DETT_PROPOSTA_CERTIF.AVANZAMENTO%TYPE, pMax NUMBER ) RETURN VARCHAR2 AS

v_random  NUMBER;
v_return    VARCHAR2(2);
v_somma_avanzamento_C PBANDI_T_DETT_PROPOSTA_CERTIF.AVANZAMENTO%TYPE; -- campioni c1 estratti
v_Count    NUMBER;

BEGIN

                     -- estraggo a sorte un numero operazione compreso fra 1 e 5 -- per le prime due estrazioni
                     -- estraggo a sorte un numero operazione fra 1 e numero totale record presenti in u1 -- successive estrazioni
                       SELECT  ROUND (DBMS_RANDOM.VALUE(1, pMax))
                       INTO      v_random
                       FROM     DUAL;
                      --
                      --   definisco il campione
                     UPDATE PBANDI_W_CAMPIONAMENTO
                     SET       TIPO_CAMPIONE = 'C1',
                                  FLAG_ESCLUDI = 'S'
                     WHERE   UNIVERSO = 'U1' AND
                                   ID_CAMPIONE = vIdCampione AND
                                   PROGR_OPERAZIONE  =   v_random;

                     -- FLAG_PRIMO_ESTRATTO
                      SELECT COUNT(*)
                      INTO     v_Count
                      FROM    PBANDI_W_CAMPIONAMENTO
                      WHERE  UNIVERSO = 'U1' AND
                                   ID_CAMPIONE = vIdCampione AND
                                   FLAG_PRIMO_ESTRATTO = 'S';

                      IF  v_Count  = 0 THEN
                           --
                              UPDATE PBANDI_W_CAMPIONAMENTO
                              SET        FLAG_PRIMO_ESTRATTO = 'S'
                              WHERE   UNIVERSO = 'U1' AND
                                            ID_CAMPIONE = vIdCampione AND
                                            PROGR_OPERAZIONE  =   v_random;
                             --
                      END IF;
                      --
                       --
                       -- avanzamento campione elaborato

                       SELECT SUM (AVANZAMENTO)
                       INTO     v_somma_avanzamento_C
                       FROM    PBANDI_W_CAMPIONAMENTO
                       WHERE  UNIVERSO = 'U1' AND
                                    TIPO_CAMPIONE = 'C1' AND
                                    FLAG_ESCLUDI = 'S'      AND
                                    ID_CAMPIONE = vIdCampione;
                       --

                -- si confronta l?importo dell' operazione/i estratte: se Ã?Â¨ superiore o uguale al 5%
                -- della sommatoria di tutti gli importi di U1 ci si ferma

                         IF      v_somma_avanzamento_C  >=  pSogliaSommaU1 THEN
                                   v_return :=  'OK';
                                   RETURN   v_return ;
                        ELSE
                                   v_return :=  'KO';
                                   RETURN   v_return ;
                        END IF;

EXCEPTION
WHEN OTHERS THEN
PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
 PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E171', SQLERRM ||' ERRORE SU:  fnc_crea_campione_c1 riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
END fnc_crea_campione_c1;
--
FUNCTION fnc_crea_campione_c2( pMinU2 NUMBER ,
                               pMaxU2 NUMBER,
                               pVal NUMBER)     RETURN VARCHAR2 AS

--v_random                 NUMBER;
v_ProgrOperazione        NUMBER;
v_ProgrOperazione_Max    NUMBER:=0;
v_Importo_Avanzamento PBANDI_T_DETT_PROPOSTA_CERTIF.AVANZAMENTO%TYPE;
v_return                                   VARCHAR2(2);
v_count                                    NUMBER;
v_fine  NUMBER:=0; -- MC SVILUPPO GEN 2021

BEGIN


          -- Se necessario RESET  del calcolo del campione c2
          IF v_reset = 1 THEN
             
             --dbms_output.put_line('RESET_CAMPIONE_C2');

              UPDATE  PBANDI_W_CAMPIONAMENTO
              SET     TIPO_CAMPIONE = null,
                      FLAG_ESCLUDI = null
              WHERE   UNIVERSO = 'U2' AND
                      ID_CAMPIONE = vIdCampione AND
                      PROGR_OPERAZIONE  <> v_primo_estratto_C2;

              v_reset := 0;
              v_nj    := 0;

          END IF;
          --


          -- verifico che l'estrazione venga effettuata solo la prima volta
          IF  v_Start = 0 THEN

                     --
                     v_Start:= 1;
                     --

                   -- estraggo a sorte un numero operazione fra quelle presenti in u2

                    SELECT  ROUND (DBMS_RANDOM.VALUE (pMinU2, pMaxU2))
                    INTO    v_random
                    FROM    DUAL;

                   --  v_random:= 22;
                   --  v_random:= 237;
                   --  v_random:= 370;





                    v_primo_estratto_C2:= v_random;

                   -- recupero i dati di partenza

                    SELECT      PROGR_OPERAZIONE,
                                AVANZAMENTO
                    INTO        v_ProgrOperazione, -- n1
                                v_Importo_Avanzamento -- i1
                    FROM        PBANDI_W_CAMPIONAMENTO
                    WHERE       UNIVERSO = 'U2'   AND
                                FLAG_ESCLUDI= 'N' AND
                                TIPO_CAMPIONE IS NULL AND
                                PROGR_OPERAZIONE   =  v_random AND
                                ID_CAMPIONE = vIdCampione;

                   --

                   -- avvio elaborazione campione c2
                   -- prendo il primo record  estratto che
                   -- corrisponde al primo elemento c2

                        UPDATE  PBANDI_W_CAMPIONAMENTO
                        SET     TIPO_CAMPIONE = 'C2',
                                FLAG_ESCLUDI = 'S',
                                FLAG_PRIMO_ESTRATTO = 'S'
                        WHERE   UNIVERSO = 'U2' AND
                                ID_CAMPIONE = vIdCampione AND
                                PROGR_OPERAZIONE  = v_random;

                   --
                      -- Nx = Nx + (n1 + 100);
                        v_ProgrOperazione_Max:= v_ProgrOperazione + pVal;    -- pVal sarÃ?Â  uguale a 100 prima di effettuare la verifica
                                                                             -- successivamente passerÃ?Â  da 99,98,97 fino al raggiungimento del campione definitivo

           ELSE

                        v_ProgrOperazione_Max:= v_primo_estratto_C2 + pVal;
           END IF;
          ---
          ---
          ---

           FOR i IN 1.. 1000 LOOP
                      --
                       IF v_ProgrOperazione_Max <=  pMaxU2 THEN  -- Ã?Â¨ compreso nel campione ?

                                    -- se Ã?Â¨ compreso nel campione u2
                                     UPDATE PBANDI_W_CAMPIONAMENTO
                                     SET    TIPO_CAMPIONE = 'C2',
                                            FLAG_ESCLUDI = 'S'
                                     WHERE  UNIVERSO = 'U2' AND
                                            ID_CAMPIONE = vIdCampione AND
                                            PROGR_OPERAZIONE  =   v_ProgrOperazione_Max;

                                    --

                                     v_ProgrOperazione_Max := v_ProgrOperazione_Max + pVal;

                                     --

                                     -- se _ProgrOperazione_Max  Ã?Â¨ maggiore del primo estratto
                                     -- se hai giÃ?Â  calcolato nj una volta
                                     -- se _ProgrOperazione_Max rientra nel campione


                                     IF v_ProgrOperazione_Max > v_primo_estratto_C2 AND
                                        v_nj > 0 AND
                                        v_ProgrOperazione_Max < pMaxU2 THEN  --

                                     -- inserisco il campione
                                       UPDATE PBANDI_W_CAMPIONAMENTO
                                       SET    TIPO_CAMPIONE = 'C2',
                                              FLAG_ESCLUDI = 'S'
                                       WHERE  UNIVERSO = 'U2' AND
                                              ID_CAMPIONE = vIdCampione AND
                                              PROGR_OPERAZIONE  = v_ProgrOperazione_Max;
                                     --
                                     -- sottopongo il campione a verifica
                                       v_return :=  'OK';
                                       RETURN   v_return ;
                                       EXIT;
                                     --
                                     END IF;


                                     -- SE v_nj Ã?Â¨ giÃ?Â  stato calcolato e il progressivo Ã?Â¨ fuori campione
                                     -- allora procedi con la verifica
                                     -- ricacalcola nj, inserisci campione e sottoponi a verifica


                                     IF v_ProgrOperazione_Max > pMaxU2 AND v_nj > 0 THEN
                                       -- ricalcolo nj
                                        v_nj:= v_ProgrOperazione_Max - pVal;
                                        v_ProgrOperazione_Max :=  v_nj + (pVal-pMaxU2)+(pMinU2-1); -- formula v02
                                       -- inserisco il campione
                                       UPDATE PBANDI_W_CAMPIONAMENTO
                                       SET    TIPO_CAMPIONE = 'C2',
                                              FLAG_ESCLUDI = 'S'
                                       WHERE  UNIVERSO = 'U2' AND
                                              ID_CAMPIONE = vIdCampione AND
                                              PROGR_OPERAZIONE  =   v_ProgrOperazione_Max;
                                       -- sottopongo il campione a verifica
                                         v_return :=  'OK';
                                         RETURN   v_return ;
                                         EXIT;
                                         --
                                     END IF;
                                     --


                       ELSE

                              v_fine:= v_fine +1; -- MC GEN 21
               -- mc sviluppo maggio 2020
                             v_nj:= v_ProgrOperazione_Max - pVal;
                             v_ProgrOperazione_Max :=  v_nj + (pVal-pMaxU2)+(pMinU2-1); -- formula v02
                             --

                               UPDATE PBANDI_W_CAMPIONAMENTO
                               SET    TIPO_CAMPIONE = 'C2',
                                      FLAG_ESCLUDI = 'S'
                               WHERE  UNIVERSO = 'U2' AND
                                      ID_CAMPIONE = vIdCampione AND
                                      PROGR_OPERAZIONE  =   v_ProgrOperazione_Max;

                              -- MC GEN 21
                               if v_fine = 2 THEN
                                     v_return :=  'OK';
                                     RETURN   v_return ;
                                     EXIT;
                               END IF;
                               --

                -- mc sviluppo giugno 2020
                              IF v_ProgrOperazione_Max > v_primo_estratto_C2 THEN
                                 v_return :=  'OK';
                                 RETURN   v_return ;
                                 EXIT;
                              END IF;
                              --

                              v_ProgrOperazione_Max := v_ProgrOperazione_Max + pVal;

                              IF v_ProgrOperazione_Max >  v_primo_estratto_C2 -- THEN
                              AND v_ProgrOperazione_Max < pMaxU2 THEN --MC GEN 21 Ã¨ nel range
                                  --
                                     UPDATE PBANDI_W_CAMPIONAMENTO
                                     SET    TIPO_CAMPIONE = 'C2',
                                            FLAG_ESCLUDI = 'S'
                                     WHERE  UNIVERSO = 'U2' AND
                                            ID_CAMPIONE = vIdCampione AND
                                            PROGR_OPERAZIONE  =   v_ProgrOperazione_Max;
                                  --
                                     v_return :=  'OK';
                                     RETURN   v_return ;
                                     EXIT;
                                  --
                              END IF;

                             -- v_ProgrOperazione_Max := v_ProgrOperazione_Max + pVal;

                       END IF;
           END LOOP;
                       --


           v_return :=  'OK';
           RETURN   v_return ;
           --

EXCEPTION
WHEN OTHERS THEN
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E171',SQLERRM|| ' ERRORE SU:  fnc_crea_campione_c2 riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);

END fnc_crea_campione_c2;

--la funzione fnc_elabora_campionamento si occupa di estrarre il campione secondo l'algoritmo previsto --> richiama le funzioni _c1 e _c2
--
FUNCTION fnc_elabora_campionamento RETURN NUMBER AS

--u1
v_m75                           PBANDI_T_DETT_PROPOSTA_CERTIF.AVANZAMENTO%TYPE;  --Ã?Â¨ la soglia per la quale se un elemento ha un importo = a tale valore farÃ?Â  parte dell?universo U1, viceversa a U2
v_max                           PBANDI_T_DETT_PROPOSTA_CERTIF.AVANZAMENTO%TYPE;  --Ã?Â¨ l?importo maggiore che compare nell?elenco
v_min                            PBANDI_T_DETT_PROPOSTA_CERTIF.AVANZAMENTO%TYPE;  --Ã?Â¨ l?importo minore che compare nell?elenco
v_Somma_U1                 PBANDI_T_DETT_PROPOSTA_CERTIF.AVANZAMENTO%TYPE;
v_Somma_CampioneC     PBANDI_T_DETT_PROPOSTA_CERTIF.AVANZAMENTO%TYPE;
v_Somma_Avanzamento PBANDI_T_DETT_PROPOSTA_CERTIF.AVANZAMENTO%TYPE;
v_MaxU1                        NUMBER; -- numero di record presenti nell'universo U1
--
--u2
v_ProgrOperazione                NUMBER;
v_ProgrOperazioneEstratto     NUMBER;
--v_Min_U2                             NUMBER; -- progressivo operazione min
--v_Max_U2                            NUMBER; -- progressivo operazione max
--
v_Avanzamento_Campione    PBANDI_T_DETT_PROPOSTA_CERTIF.AVANZAMENTO%TYPE;
v_Avanzamento_Totale           PBANDI_T_DETT_PROPOSTA_CERTIF.AVANZAMENTO%TYPE;
v_Campione_Totale                NUMBER;
v_Val                                     NUMBER;
v_Count                                 NUMBER;
vReturn                                 VARCHAR2(2);
--

BEGIN

    -- Estrazione del campione C1
     --
     SELECT MAX(AVANZAMENTO)
     INTO     v_max
     FROM    PBANDI_W_CAMPIONAMENTO
     WHERE  ID_CAMPIONE = vIdCampione;
     --
     SELECT MIN(AVANZAMENTO)
     INTO    v_min
     FROM    PBANDI_W_CAMPIONAMENTO
     WHERE  ID_CAMPIONE = vIdCampione;

     -- formula per determinare il valore soglia
     v_m75 :=  v_min + ( ((v_max + v_min)/2)* 1.5);
     --
     UPDATE  PBANDI_W_CAMPIONAMENTO
     SET       UNIVERSO = 'U2' -- di default i record sono impostati a U1
     WHERE  AVANZAMENTO <   v_m75  AND
                 ID_CAMPIONE = vIdCampione;
    --
    -- si determina il valore soglia
    SELECT  SUM(AVANZAMENTO)
    INTO      v_Somma_Avanzamento
    FROM    PBANDI_W_CAMPIONAMENTO
    WHERE  UNIVERSO = 'U1' AND
                 TIPO_CAMPIONE IS NULL AND
                 ID_CAMPIONE = vIdCampione;

      v_Somma_U1 := v_Somma_Avanzamento* 0.05;
     --

     --
    -- per n tentativi  per definire il campione C1 --
      FOR i IN 1.. 1000 LOOP

         --- per la prima estrazioni, la base dei record sui quali estrarne uno a sorte rientrano fra i primi cinque
         --  in ordine di avanzamente decrescente
         -- la funzione fnc_crea_campione_c1 non considera comunque il record precedentemente ESTRATTO
              IF I < 2 THEN
                      --Il primo elemento chiave si ottiene estraendo a sorte una delle prime 5 unitÃ?Â  dell?universo U1
                      -- (se le unitÃ?Â  di U1 sono inferiori a 5 si esegue l?estrazione fra loro).
                      SELECT Count(*)
                      INTO     v_Count
                      FROM    PBANDI_W_CAMPIONAMENTO
                      WHERE  ID_CAMPIONE = vIdCampione AND
                                   UNIVERSO = 'U1';

                      IF v_Count >= 5 THEN
                          v_MaxU1:= 5;
                      ELSE
                           v_MaxU1:= v_Count;
                      END IF;

              END IF;
         -- per le successive estrazioni, i record sui quali estrarne uno rientrano fra tutti i record presenti sull' U1
         -- la funzione fnc_crea_campione_c1 non considera comunque i record precedentemente ESTRATTI
             IF i  >= 2 THEN

                      SELECT COUNT(*)
                      INTO     v_MaxU1
                      FROM    PBANDI_W_CAMPIONAMENTO
                      WHERE  ID_CAMPIONE = vIdCampione AND
                                   UNIVERSO = 'U1';
             END IF;


             -- si richiama la funzione che gestiisce la predisposione del campione C1
             -- verificando prima la presenza di elementi da campionare
             SELECT COUNT(*)
             INTO     v_Count
             FROM   PBANDI_W_CAMPIONAMENTO
             WHERE   UNIVERSO = 'U1' AND
                           ID_CAMPIONE = vIdCampione AND
                           TIPO_CAMPIONE IS NULL;

              IF    v_Count > 0 THEN
                     vReturn := fnc_crea_campione_c1(v_Somma_U1,  v_MaxU1);
              ELSE
                     EXIT;
              END IF;

               IF     vReturn = 'OK' THEN
                       EXIT;
               END IF;

      END LOOP;

    --
    --
    -- Estrazione del campione C2
    --
     SELECT  MIN(PROGR_OPERAZIONE)
     INTO      v_min_U2 -- corrisponde al progressivo dell'importo massimo dell'universo U2
     FROM     PBANDI_W_CAMPIONAMENTO
     WHERE   ID_CAMPIONE = vIdCampione AND
                  UNIVERSO = 'U2';
     --
     SELECT  Max(PROGR_OPERAZIONE)
     INTO      v_Max_U2    -- corrisponde al progressivo dell'importo minimo dell'universo U2 e anche al numero di record presenti per campionamento
     FROM     PBANDI_W_CAMPIONAMENTO
     WHERE   ID_CAMPIONE = vIdCampione AND
                  UNIVERSO = 'U2';
     -- la prima volta che si richiama la procedura _c2 vale sempre 100
      -- se non si supera la verifica del campione il valore di decrementa di 1 unitÃ?Â 
       v_Val := 100;
       --
       FOR i IN 1.. 1000 LOOP
        -- si richiama la funzione che gestiisce la predisposione del campione C2
              vReturn := fnc_crea_campione_c2 (v_min_U2 , v_Max_U2, v_Val);


               IF vReturn = 'OK' THEN
                   -- avvia procedura di verifica del campione
                   -- gli importi del campione devono superare (o eguagliare) il 5% dell?avanzamento totale della spesa;

                          SELECT SUM(AVANZAMENTO)
                          INTO     v_Avanzamento_Campione
                          FROM    PBANDI_W_CAMPIONAMENTO
                          WHERE  TIPO_CAMPIONE IN ('C1','C2') AND
                                       ID_CAMPIONE = vIdCampione;
                    --
                           SELECT SUM(AVANZAMENTO)
                           INTO     v_Avanzamento_Totale
                           FROM    PBANDI_W_CAMPIONAMENTO
                           WHERE  ID_CAMPIONE = vIdCampione;
                    --
                           SELECT COUNT(*)
                           INTO     v_Campione_Totale
                           FROM    PBANDI_W_CAMPIONAMENTO
                           WHERE  TIPO_CAMPIONE IN ('C1','C2') AND
                                        ID_CAMPIONE = vIdCampione;
                    --
                         IF   v_Avanzamento_Campione >= (v_Avanzamento_Totale * 0.05) THEN
                               -- superata la prima verifica
                               -- la numerosita del campione deve essere maggiore o uguale all? 1% della numerositÃ?Â  totale dell?universo
                                            IF v_Campione_Totale >=  (v_Max_U2 * 0.01) THEN
                      v_valore_v := v_Val; -- mc sviluppo luglio 2020
                      --
                                               --
                                               EXIT;

                                            ELSE
                                               --
                                                v_Val:= v_Val - 1;
                                                v_reset:= 1;

                                            END IF;
                         ELSE
                                 --
                                 v_Val:= v_Val - 1;
                                 v_reset:= 1;

                         END IF;
               ELSE
                                 --
                                 v_Val:= v_Val - 1;
                                 v_reset:= 1;

               END IF;

       END LOOP;

  -- ULTIMA FASE
  -- inserimento campione individuato
  FOR rec_InserisciCampione IN
  (SELECT *
  FROM PBANDI_W_CAMPIONAMENTO
 WHERE  TIPO_CAMPIONE IN ('C1','C2') AND
             ID_CAMPIONE = vIdCampione

    )LOOP

            INSERT INTO PBANDI_R_CAMPIONAMENTO
            (ID_CAMPIONE,
             ID_PROGETTO,
             TITOLO_PROGETTO,
             PROGR_OPERAZIONE,
             UNIVERSO,
             AVANZAMENTO,
             ASSE
            ) VALUES
            (rec_InserisciCampione.ID_CAMPIONE,
            rec_InserisciCampione.ID_PROGETTO,
            rec_InserisciCampione.TITOLO_PROGETTO,
            rec_InserisciCampione.PROGR_OPERAZIONE,
            rec_InserisciCampione.UNIVERSO,
            rec_InserisciCampione.AVANZAMENTO,
            rec_InserisciCampione.ASSE
            );

    END LOOP;



   --COMMIT;

   RETURN vRetVal;

EXCEPTION WHEN OTHERS THEN
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E171',SQLERRM|| ' ERRORE SU:  fnc_elabora_campionamento riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
    RAISE_APPLICATION_ERROR (-20000,' ERRORE SU: fnc_elabora_campionamento');
END fnc_elabora_campionamento;
-- dettaglio del campionamento per definire il report in excel
FUNCTION fnc_elabora_dett_campionamento RETURN NUMBER AS


v_valore_max_avanzamento_u             pbandi_r_dett_campionamento.valore_max_avanzamento_u%type;
v_valore_min_avanzamento_u              pbandi_r_dett_campionamento.valore_min_avanzamento_u%type;
v_valore_med_avanzamento_u             pbandi_r_dett_campionamento.valore_med_avanzamento_u%type;
v_valore_m75_avanzamento_u             pbandi_r_dett_campionamento.valore_m75_avanzamento_u%type;
v_numerosita_campione_u1                  pbandi_r_dett_campionamento.numerosita_campione_u1%type;
v_numero_unita_estratte_u1                 pbandi_r_dett_campionamento.numero_unita_estratte_u1%type;
v_progr_operaz_primo_estrat_u1           pbandi_r_dett_campionamento.progr_operaz_primo_estratto_u1%type;
v_avanzamento_primo_estrat_u1           pbandi_r_dett_campionamento.avanzamento_primo_estratto_u1%type;
v_somma_avanz_campione_u1              pbandi_r_dett_campionamento.somma_avanzamenti_campione_u1%type;
v_totale_avanz_estratti_u1                    pbandi_r_dett_campionamento.totale_avanzamenti_estratti_u1%type;
v_rapporto_numerosita_u1                    pbandi_r_dett_campionamento.rapporto_numerosita_u1%type;
v_valore_max_avanzamento_u2            pbandi_r_dett_campionamento.valore_max_avanzamento_u2%type;
v_valore_min_avanzamento_u2             pbandi_r_dett_campionamento.valore_min_avanzamento_u2%type;
v_numerosita_campione_u2                  pbandi_r_dett_campionamento.numerosita_campione_u2%type;
v_numero_unita_estratte_u2                 pbandi_r_dett_campionamento.numero_unita_estratte_u2%type;
v_rapporto_numerosita_u2                    pbandi_r_dett_campionamento.rapporto_numerosita_u2%type;
v_valore_v                                            pbandi_r_dett_campionamento.valore_v%type;
v_somma_avanz_campione_u2              pbandi_r_dett_campionamento.somma_avanzamenti_campione_u2%type;
v_progr_operaz_primo_estrat_u2           pbandi_r_dett_campionamento.progr_operaz_primo_estratto_u2%type;
v_totale_avanzamenti_estrat_u2            pbandi_r_dett_campionamento.totale_avanzamenti_estratti_u2%type;
v_totale_avanzamenti_estrat_u             pbandi_r_dett_campionamento.totale_avanzamenti_estratti_u%type;
v_somma_avanzamenti_campione_u     pbandi_r_dett_campionamento.somma_avanzamenti_campione_u%type;
v_rapporto_avanzamenti_u                    pbandi_r_dett_campionamento.rapporto_avanzamenti_u%type;
v_numero_unita_estratte_u                   pbandi_r_dett_campionamento.numero_unita_estratte_u%type;
v_numerosita_campione_u                    pbandi_r_dett_campionamento.numerosita_campione_u%type;
v_rapporto_numerosita_u                      pbandi_r_dett_campionamento.rapporto_numerosita_u%type;
v_avanz_primo_estratto_u2                   pbandi_r_dett_campionamento.avanzamento_primo_estratto_u2%type;


--
BEGIN
   ---------------
   --    U      --
   ---------------
    ---
    SELECT  MAX(AVANZAMENTO),
                 MIN(AVANZAMENTO)
    INTO      v_valore_max_avanzamento_u,
                  v_valore_min_avanzamento_u
    FROM     PBANDI_W_CAMPIONAMENTO
    WHERE  ID_CAMPIONE = vIdCampione;
    ---
    v_valore_med_avanzamento_u :=   ((v_valore_max_avanzamento_u + v_valore_min_avanzamento_u) / 2);
    ---
    v_valore_m75_avanzamento_u :=   v_valore_min_avanzamento_u + ( ((v_valore_max_avanzamento_u +  v_valore_min_avanzamento_u)/2)* 1.5);
    ---
    ---------------
   --    U1     --
   ---------------
     SELECT   COUNT(*)
     INTO      v_numerosita_campione_u1
     FROM     PBANDI_W_CAMPIONAMENTO
     WHERE   ID_CAMPIONE = vIdCampione
     AND        UNIVERSO = 'U1';
    ---
     SELECT   COUNT(*)
     INTO       v_numero_unita_estratte_u1
     FROM     PBANDI_R_CAMPIONAMENTO
     WHERE   ID_CAMPIONE = vIdCampione
     AND        UNIVERSO = 'U1';
     ---

    v_rapporto_numerosita_u1 := (v_numero_unita_estratte_u1/v_numerosita_campione_u1)*100;
     --
      SELECT  PROGR_OPERAZIONE,
                   AVANZAMENTO
      INTO      v_progr_operaz_primo_estrat_u1,
                    v_avanzamento_primo_estrat_u1
      FROM     PBANDI_W_CAMPIONAMENTO
      WHERE   ID_CAMPIONE = vIdCampione
      AND        UNIVERSO = 'U1'
      AND        FLAG_PRIMO_ESTRATTO = 'S';

     ---
      SELECT   SUM(AVANZAMENTO)
      INTO       v_totale_avanz_estratti_u1
      FROM      PBANDI_R_CAMPIONAMENTO
      WHERE    ID_CAMPIONE = vIdCampione
      AND         UNIVERSO = 'U1';
      ---
      SELECT   SUM(AVANZAMENTO)
      INTO       v_somma_avanz_campione_u1
      FROM      PBANDI_W_CAMPIONAMENTO
      WHERE    ID_CAMPIONE = vIdCampione
      AND         UNIVERSO = 'U1';
      ---
       ---------------
       --    U2    --
       ---------------
       SELECT  MAX(AVANZAMENTO),
                    MIN(AVANZAMENTO)
        INTO     v_valore_max_avanzamento_u2,
                     v_valore_min_avanzamento_u2
        FROM    PBANDI_W_CAMPIONAMENTO
       WHERE   ID_CAMPIONE = vIdCampione
       AND       UNIVERSO = 'U2';
       --
       SELECT   COUNT(*)
       INTO       v_numerosita_campione_u2
       FROM      PBANDI_W_CAMPIONAMENTO
       WHERE    ID_CAMPIONE = vIdCampione
       AND         UNIVERSO = 'U2';
       --
       SELECT   COUNT(*)
       INTO       v_numero_unita_estratte_u2
       FROM      PBANDI_R_CAMPIONAMENTO
       WHERE    ID_CAMPIONE = vIdCampione
       AND         UNIVERSO = 'U2';
       --
        v_rapporto_numerosita_u2 :=  (v_numero_unita_estratte_u2/v_numerosita_campione_u2) *100;
        --
        --v_valore_v:= 100; -- valore fisso
        --
        SELECT   SUM(AVANZAMENTO)
        INTO       v_somma_avanz_campione_u2
        FROM      PBANDI_W_CAMPIONAMENTO
        WHERE    ID_CAMPIONE = vIdCampione
        AND         UNIVERSO = 'U2';
        --               
       SELECT  PROGR_OPERAZIONE
       INTO    v_progr_operaz_primo_estrat_u2
       FROM    PBANDI_W_CAMPIONAMENTO
       WHERE   ID_CAMPIONE = vIdCampione
       AND     UNIVERSO = 'U2'
       AND     FLAG_PRIMO_ESTRATTO = 'S';
       --
       SELECT  AVANZAMENTO
       INTO      v_avanz_primo_estratto_u2
       FROM     PBANDI_W_CAMPIONAMENTO
       WHERE   ID_CAMPIONE = vIdCampione
       AND        UNIVERSO = 'U2'
       AND        FLAG_PRIMO_ESTRATTO = 'S';
        --
        SELECT   SUM(AVANZAMENTO)
        INTO       v_totale_avanzamenti_estrat_u2
        FROM      PBANDI_R_CAMPIONAMENTO
        WHERE    ID_CAMPIONE = vIdCampione
        AND         UNIVERSO = 'U2';
        --
       ---------------
       --     U     --
       ---------------

        SELECT   SUM(AVANZAMENTO)
        INTO       v_totale_avanzamenti_estrat_u
        FROM      PBANDI_R_CAMPIONAMENTO
        WHERE    ID_CAMPIONE = vIdCampione;
        --
        SELECT   SUM(AVANZAMENTO)
        INTO       v_somma_avanzamenti_campione_u
        FROM      PBANDI_W_CAMPIONAMENTO
        WHERE    ID_CAMPIONE = vIdCampione;
        --
        v_rapporto_avanzamenti_u := (v_totale_avanzamenti_estrat_u/v_somma_avanzamenti_campione_u) * 100;
        --
        SELECT   COUNT(*)
        INTO       v_numero_unita_estratte_u
        FROM      PBANDI_R_CAMPIONAMENTO
        WHERE    ID_CAMPIONE = vIdCampione;
        --
        SELECT   COUNT(*)
        INTO        v_numerosita_campione_u
        FROM      PBANDI_W_CAMPIONAMENTO
        WHERE    ID_CAMPIONE = vIdCampione;
        --
        v_rapporto_numerosita_u:=  (v_numero_unita_estratte_u/ v_numerosita_campione_u) * 100;
        --

    INSERT INTO PBANDI_R_DETT_CAMPIONAMENTO
       (id_campione,
        valore_max_avanzamento_u,
        valore_min_avanzamento_u,
        valore_med_avanzamento_u,
        valore_m75_avanzamento_u,
        numerosita_campione_u1,
        numero_unita_estratte_u1,
        progr_operaz_primo_estratto_u1,
        avanzamento_primo_estratto_u1,
        somma_avanzamenti_campione_u1,
        totale_avanzamenti_estratti_u1,
        rapporto_numerosita_u1,
        valore_max_avanzamento_u2,
        valore_min_avanzamento_u2,
        numerosita_campione_u2,
        numero_unita_estratte_u2,
        rapporto_numerosita_u2,
        valore_v,
        somma_avanzamenti_campione_u2,
        progr_operaz_primo_estratto_u2,
        totale_avanzamenti_estratti_u2,
        totale_avanzamenti_estratti_u,
        somma_avanzamenti_campione_u,
        rapporto_avanzamenti_u,
        numero_unita_estratte_u,
        numerosita_campione_u,
        rapporto_numerosita_u,
        avanzamento_primo_estratto_u2
    )
    VALUES
       (vIdCampione, --
        v_valore_max_avanzamento_u, --
        v_valore_min_avanzamento_u,  --
        v_valore_med_avanzamento_u, --
        v_valore_m75_avanzamento_u, --
        v_numerosita_campione_u1,--
        v_numero_unita_estratte_u1, --
        v_progr_operaz_primo_estrat_u1, -- DA FARE
        v_avanzamento_primo_estrat_u1 , -- DA FARE
        v_somma_avanz_campione_u1,--
        v_totale_avanz_estratti_u1,--
        v_rapporto_numerosita_u1,--
        v_valore_max_avanzamento_u2,--
        v_valore_min_avanzamento_u2,--
        v_numerosita_campione_u2, --
        v_numero_unita_estratte_u2, --
        v_rapporto_numerosita_u2, --
        v_valore_v, --
        v_somma_avanz_campione_u2, --
        v_progr_operaz_primo_estrat_u2,
        v_totale_avanzamenti_estrat_u2,--
        v_totale_avanzamenti_estrat_u,--
        v_somma_avanzamenti_campione_u,--
        v_rapporto_avanzamenti_u,--
        v_numero_unita_estratte_u,--
        v_numerosita_campione_u,--
        v_rapporto_numerosita_u,--
        v_avanz_primo_estratto_u2--
       );


    RETURN vRetVal;

EXCEPTION WHEN OTHERS THEN
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E171',SQLERRM|| ' ERRORE SU:  fnc_elabora_dett_campionamento riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
    RAISE_APPLICATION_ERROR (-20000,' ERRORE SU: fnc_elabora_dett_campionamento');
END fnc_elabora_dett_campionamento;
--la funzione fnc_MainCampionamento viene richiamata dall'online
FUNCTION fnc_MainCampionamento(pDescBreveLinea PBANDI_D_LINEA_DI_INTERVENTO.DESC_BREVE_LINEA%TYPE DEFAULT NULL, pID_UTENTE_INS NUMBER)
                RETURN NUMBER AS
 --
BEGIN

   -- la funzione fnc_crea_campionamento si occupa di predisporre i dati oggetto dell'elaborazione del campione da estrarre
   IF pDescBreveLinea IS NOT NULL THEN
       vRetVal:= fnc_crea_campionamento(pDescBreveLinea,pID_UTENTE_INS);
       ---
       IF vRetVal = -2 then -- certificazione giÃ?Â  campionata
           RETURN vRetVal;
       END IF;
       --
       
       --- mc jira 3079
       IF vRetVal = -3 then -- non esite certificazione in stato aperto
           RETURN vRetVal;
       END IF;
       --
       
       
       
   ELSE

       vRetVal:= fnc_crea_campionamento_FSE(pID_UTENTE_INS);

       ---
       IF vRetVal = -2 then -- certificazione FSE giÃ?Â  campionata
           RETURN vRetVal;
       END IF;
       --
   END IF;

   -- la funzione fnc_elabora_campionamento si occupa di estrarre il campione secondo l'algoritmo previsto   
   vRetVal:= fnc_elabora_campionamento;

   -- la funzione fnc_elabora_dett_campionamento si occupa di definire il dettaglio del campionamento
   vRetVal:= fnc_elabora_dett_campionamento;
   /*
           --
           v_Start := 0;
           v_Reset := 0;
           v_nj    := 0;
           --
  */
   -- chiusura del processo sulla LOG_BATCH
   PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'OK');

   COMMIT;

   -- Restituisce l'ID del campione non appena creato da passare lato java per la creazione del report
     RETURN vIdCampione;

END  fnc_MainCampionamento;

END PCK_PBANDI_CAMPIONAMENTO;
/