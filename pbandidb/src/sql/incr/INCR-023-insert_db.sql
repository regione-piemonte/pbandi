/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

--PBANDI_C_TIPO_DOCUMENTO_INDEX

Insert into PBANDI_C_TIPO_DOCUMENTO_INDEX
   (ID_TIPO_DOCUMENTO_INDEX, DESC_BREVE_TIPO_DOC_INDEX, DESC_TIPO_DOC_INDEX)
 Values
   (23, 'AF', 'ARCHIVIO FILE');
Insert into PBANDI_C_TIPO_DOCUMENTO_INDEX
   (ID_TIPO_DOCUMENTO_INDEX, DESC_BREVE_TIPO_DOC_INDEX, DESC_TIPO_DOC_INDEX)
 Values
   (24, 'RDDS', 'REPORT DETTAGLIO DOCUMENTI DI SPESA');
COMMIT;

--PBANDI_C_COSTANTI   
Insert into PBANDI_C_COSTANTI
   (ATTRIBUTO, VALORE)
 Values
   ('conf.archivioFileSizeLimit', '2048');
Insert into PBANDI_C_COSTANTI
   (ATTRIBUTO, VALORE)
 Values
   ('conf.userSpaceLimit', '1024000');
Insert into PBANDI_C_COSTANTI
   (ATTRIBUTO, VALORE)
 Values
   ('conf.archivioAllowedFileExtensions', 'doc, docx, odt, ods, pdf, xls, xlsx, gif, jpg, jpeg, png, bmp, dwg, dxf');
COMMIT;
   
--PBANDI_C_REGOLA
Insert into PBANDI_C_REGOLA
   (ID_REGOLA, DESC_BREVE_REGOLA, DESC_REGOLA, DT_INIZIO_VALIDITA)
 Values
   (42, 'BR42', '(DEMATERIALIZZAZIONE) Si possono associare file di tipo "invio elettronico o da scansione" solo se il progetto afferisce ad un bando linea intervento associato alla regola ', TO_DATE('04/23/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
COMMIT;

--PBANDI_R_DOC_INDEX_TIPO_ANAG
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (1, 0, 24);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (2, 0, 24);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (3, 0, 24);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (4, 0, 24);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (8, 0, 24);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (16, 0, 24);
COMMIT;

--PBANDI_R_TP_DOC_IND_BAN_LI_INT
-- !!!!!!!!!!!!!!!!!!!!!!!!!  RICORDARSI DI Configurare i BANDO LINEA PER "REPORT DETTAGLIO DOCUMENTI DI SPESA"
-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
select 24,PROGR_BANDO_LINEA_INTERVENTO,0
from PBANDI_R_BANDO_LINEA_INTERVENT;

COMMIT;

--PBANDI_D_PERMESSO
Insert into PBANDI_D_PERMESSO
   (ID_PERMESSO, DESC_BREVE_PERMESSO, DESC_PERMESSO, DT_INIZIO_VALIDITA)
 Values
   (148, 'OPEARC001', 'Menu Archivio file', SYSDATE);

--PBANDI_R_PERMESSO_TIPO_ANAGRAF
Insert into PBANDI_R_PERMESSO_TIPO_ANAGRAF
   (ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA)
 Values
   (148, 1, SYSDATE);
Insert into PBANDI_R_PERMESSO_TIPO_ANAGRAF
   (ID_PERMESSO, ID_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA)
 Values
   (148, 19, SYSDATE);

Insert into PBANDI_D_TIPO_AFFIDAMENTO
   (ID_TIPO_AFFIDAMENTO, DESC_TIPO_AFFIDAMENTO)
 Values
   (3, 'Incarico');
Insert into PBANDI_D_TIPO_AFFIDAMENTO
   (ID_TIPO_AFFIDAMENTO, DESC_TIPO_AFFIDAMENTO)
 Values
   (2, 'Contratto');
Insert into PBANDI_D_TIPO_AFFIDAMENTO
   (ID_TIPO_AFFIDAMENTO, DESC_TIPO_AFFIDAMENTO)
 Values
   (1, 'Appalto');
COMMIT;


--PBANDI_D_ERRORE_BATCH
MERGE INTO PBANDI_D_ERRORE_BATCH A USING
 (SELECT
  'W088' as CODICE_ERRORE,
  'I dati relativi a beneficiario e progetto sono incoerenti, impossibile generare l''associazione.In piattaforma il progetto è associato all''azienda con ' as DESCRIZIONE
  FROM DUAL) B
ON (A.CODICE_ERRORE = B.CODICE_ERRORE)
WHEN MATCHED THEN
UPDATE SET 
  A.DESCRIZIONE = B.DESCRIZIONE;

--PBANDI_R_PROGETTI_APPALTI

UPDATE PBANDI_R_PROGETTI_APPALTI
SET ID_TIPO_DOCUMENTO_INDEX = 2;


-- Nuova gestione processo
--PBANDI_D_MULTI_TASK
Insert into PBANDI_D_MULTI_TASK
   (ID_MULTI_TASK, DESCR_MULTI_TASK, QUERY_MULTI_TASK)
 Values
   (1, 'Ritorna l''elenco delle dichiarazioni di spesa non ancora validate per il progetto', 'select ID_DICHIARAZIONE_SPESA
FROM PBANDI_T_DICHIARAZIONE_SPESA
WHERE ID_PROGETTO = ${id_progetto}
AND DT_CHIUSURA_VALIDAZIONE IS NULL');
COMMIT;

--PBANDI_D_TIPO_TASK
Insert into PBANDI_D_TIPO_TASK
   (ID_TIPO_TASK, DESCR_BREVE_TIPO_TASK, DESCR_TIPO_TASK)
 Values
   (1, 'GA', 'Gataway AND (Il processo va avanti se tutti i task precedenti sono conclusi)');
Insert into PBANDI_D_TIPO_TASK
   (ID_TIPO_TASK, DESCR_BREVE_TIPO_TASK, DESCR_TIPO_TASK)
 Values
   (2, 'GO', 'Gateway OR (Il processo va avanti se uno dei task precedenti è concluso)');
Insert into PBANDI_D_TIPO_TASK
   (ID_TIPO_TASK, DESCR_BREVE_TIPO_TASK, DESCR_TIPO_TASK)
 Values
   (3, 'GE', 'Gateway chiusura (Se il task è concluso allora il processo è terminato)');
Insert into PBANDI_D_TIPO_TASK
   (ID_TIPO_TASK, DESCR_BREVE_TIPO_TASK, DESCR_TIPO_TASK, FLAG_PUBLIC)
 Values
   (4, 'TA', 'Task normale', 'S');
Insert into PBANDI_D_TIPO_TASK
   (ID_TIPO_TASK, DESCR_BREVE_TIPO_TASK, DESCR_TIPO_TASK, FLAG_PUBLIC)
 Values
   (5, 'TC', 'Task con controllo di esecuzione', 'S');
Insert into PBANDI_D_TIPO_TASK
   (ID_TIPO_TASK, DESCR_BREVE_TIPO_TASK, DESCR_TIPO_TASK)
 Values
   (6, 'TF', 'Task fittizio');
Insert into PBANDI_D_TIPO_TASK
   (ID_TIPO_TASK, DESCR_BREVE_TIPO_TASK, DESCR_TIPO_TASK, FLAG_OPT, FLAG_PUBLIC)
 Values
   (7, 'TO', 'Task opzionale', 'S', 'S');
Insert into PBANDI_D_TIPO_TASK
   (ID_TIPO_TASK, DESCR_BREVE_TIPO_TASK, DESCR_TIPO_TASK)
 Values
   (8, 'GS', 'Gateway di selezione (task di selezione di un ramo del processo)');
Insert into PBANDI_D_TIPO_TASK
   (ID_TIPO_TASK, DESCR_BREVE_TIPO_TASK, DESCR_TIPO_TASK, FLAG_OPT, FLAG_PUBLIC)
 Values
   (9, 'TOC', 'Task opzionale con controllo', 'S', 'S');
COMMIT;

--PBANDI_D_METODO_TASK
Insert into PBANDI_D_METODO_TASK
   (ID_METODO_TASK, DESCR_BREVE_METODO_TASK, DESCR_METODO_TASK)
 Values
   (1, 'FN_PROG_2014_REP_2007', 'Metodo che determina il ramo del processo AVVIO-SCH-PROG o AVVIO-ALTRO-PROG');
Insert into PBANDI_D_METODO_TASK
   (ID_METODO_TASK, DESCR_BREVE_METODO_TASK, DESCR_METODO_TASK)
 Values
   (3, 'FN_CARICAM_INDIC_AVVIO', 'Metodo di controllo per il task CARICAM-INDIC-AVVIO');
Insert into PBANDI_D_METODO_TASK
   (ID_METODO_TASK, DESCR_BREVE_METODO_TASK, DESCR_METODO_TASK)
 Values
   (2, 'FN_EROG_LIQUID', 'Metodo che determina il ramo del processo (LIQUIDAZIONE/LIQUID-FLUSSI o EROGAZIONE/EROG-FLUSSI)');
Insert into PBANDI_D_METODO_TASK
   (ID_METODO_TASK, DESCR_BREVE_METODO_TASK, DESCR_METODO_TASK)
 Values
   (4, 'FN_CRONOPROG_AVVIO', 'Metodo di controllo per il task CRONOPROG-AVVIO');
Insert into PBANDI_D_METODO_TASK
   (ID_METODO_TASK, DESCR_BREVE_METODO_TASK, DESCR_METODO_TASK)
 Values
   (5, 'FN_CARICAM_INDIC_PROG', 'Metodo di controllo per il task CARICAM-INDIC-PROG');
Insert into PBANDI_D_METODO_TASK
   (ID_METODO_TASK, DESCR_BREVE_METODO_TASK, DESCR_METODO_TASK)
 Values
   (6, 'FN_CRONOPROGRAMMA', 'Metodo di controllo per il task CRONOPROGRAMMA');
Insert into PBANDI_D_METODO_TASK
   (ID_METODO_TASK, DESCR_BREVE_METODO_TASK, DESCR_METODO_TASK)
 Values
   (7, 'FN_RET_INDIC_PROG', 'Metodo di controllo per il task RET-INDIC-PROG');
Insert into PBANDI_D_METODO_TASK
   (ID_METODO_TASK, DESCR_BREVE_METODO_TASK, DESCR_METODO_TASK)
 Values
   (8, 'FN_RET_CRONOPROG', 'Metodo di controllo per il task RET-CRONOPROG');
Insert into PBANDI_D_METODO_TASK
   (ID_METODO_TASK, DESCR_BREVE_METODO_TASK, DESCR_METODO_TASK, FLAG_CHIAMATA_END)
 Values
   (9, 'FN_COMUNIC_RINUNCIA', 'Metodo di controllo per il task COMUNIC-RINUNCIA', 'S');
COMMIT;

--PBANDI_D_TASK
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (10, 'AND-IND-CRONO-AVV', 'AND Indicatori e Cronoprogramma d''avvio', 1);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK, ID_METODO_TASK)
 Values
   (39, 'RET-INDIC-PROG', 'Rettifica indicatori di progetto', 5, 7);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK, ID_METODO_TASK)
 Values
   (40, 'RET-CRONOPROG', 'Rettifica cronoprogramma', 5, 8);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (52, 'EROG-FITTIZIO', 'Erogazione (fittizio)', 6);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (53, 'LIQUID-FITTIZIO', 'Liquidazione (fittizio)', 6);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (45, 'VALID-DICH-SPESA-FINALE', 'Validazione dichiarazione di spesa finale', 9);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (46, 'DICH-SPESA-INTEGRATIVA', 'Dichiarazione di spesa integrativa', 9);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (47, 'VALID-DICH-SPESA', 'Validazione della dichiarazione di spesa', 9);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK, ID_METODO_TASK)
 Values
   (11, 'CARICAM-INDIC-PROG', 'Caricamento degli indicatori di progetto', 5, 5);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK, ID_METODO_TASK)
 Values
   (12, 'CRONOPROGRAMMA', 'Cronoprogramma', 5, 6);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (13, 'QUADRO-PREVISIO', 'Quadro previsionale', 7);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK, ID_METODO_TASK)
 Values
   (14, 'COMUNIC-RINUNCIA', 'Comunicazione di rinuncia', 5, 9);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (15, 'CHIUS-PROG-RINUNCIA', 'Chiusura del progetto per rinuncia', 4);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (16, 'CHIUS-UFF-PROG', 'Chiusura d''ufficio del progetto', 5);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (17, 'RILEV-IRREGOLAR', 'Rilevazione irregolarità', 4);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (18, 'GEST-CHECKLIST', 'Gestione checklist del progetto', 4);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (19, 'GEST-APPALTI', 'Gestione appalti', 5);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (20, 'RIM-CE', 'Rimodulazione del conto economico', 5);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (21, 'RICH-EROG-CALC-CAU', 'Richiesta erogazione - calcolo causale', 9);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK, ID_METODO_TASK)
 Values
   (100, 'PROG-2014-CASO-TEST', 'PROGRAMMAZIONE 2014-20120 CASO TEST', 8, 1);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (34, 'REVOCA', 'Revoca', 9);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (35, 'CONSULTA-LIQUID', 'Consultazione atti di liquidazione', 9);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (36, 'MOD-REVOCA', 'Modifica revoca', 9);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (1, 'AVVIO-SCH-PROG', 'Avvio scheda progetto', 6);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (2, 'AVVIO-ALTRO-PROG', 'Avvio progetto da flussi', 6);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (3, 'RICH-CE-DOM', 'Richiesta del conto economico in domanda', 4);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (4, 'RIM-CE-ISTR', 'Rimodulazione del conto economico in istruttoria', 4);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK, ID_METODO_TASK)
 Values
   (9, 'PROG-2014-REP-2007', 'PROGRAMMAZIONE 2014-2020 REPLICA 2007-2013', 8, 1);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (22, 'GEST-FIDEIUSSIONI', 'Gestione fideiussioni', 5);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK, ID_METODO_TASK)
 Values
   (5, 'CARICAM-INDIC-AVVIO', 'Caricamento degli indicatori d''avvio', 5, 3);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK, ID_METODO_TASK)
 Values
   (6, 'CRONOPROG-AVVIO', 'Cronoprogramma d''avvio', 5, 4);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (7, 'DATI-PROG', 'Dati del progetto', 7);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (8, 'OR', 'OR avvio', 2);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (23, 'DICH-DI-SPESA', 'Dichiarazione di spesa', 5);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (24, 'COMUNIC-FINE-PROG', 'Comunicazione di fine progetto', 4);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (25, 'PROP-RIM-CE', 'Proposta di rimodulazione del conto economico', 5);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (26, 'AND-CRONO-IND', 'AND Cronoprogramma e Indicatori', 1);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (27, 'SOPPRESSIONE', 'Soppressione', 7);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK, ID_METODO_TASK)
 Values
   (28, 'EROG-LIQUID', 'Erogazione o Liquidazione', 8, 2);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (29, 'EROGAZIONE', 'Erogazione', 7);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (30, 'EROG-FLUSSI', 'Erogazione da flussi', 6);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (31, 'LIQUIDAZIONE', 'Liquidazione', 7);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (32, 'LIQUID-FLUSSI', 'Liquidazione da flussi', 6);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (33, 'MOD-EROG', 'Modifica erogazione', 9);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (37, 'RECUPERO', 'Recupero', 9);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (38, 'MOD-RECUPERO', 'Modifica recupero', 9);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (49, 'OR-CHIUSURA-PROG-UFF-RINU-DICH', 'OR Chiusura del progetto d''ufficio - per rinuncia - fine dichiarazioni spesa', 2);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (50, 'CHIUSURA-PROGETTO', 'Chiusura del progetto', 3);
Insert into PBANDI_D_TASK
   (ID_TASK, DESCR_BREVE_TASK, DESCR_TASK, ID_TIPO_TASK)
 Values
   (51, 'GEST-SPESA-VALID', 'Gestione spesa validata', 9);
COMMIT;

--PBANDI_D_TEMPLATE_NOTIFICA
Insert into PBANDI_D_TEMPLATE_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, DESCR_BREVE_TEMPLATE_NOTIFICA, COMP_SUBJECT, COMP_MESSAGE)
 Values
   (1, 'NotificaValidazione', 'Notifica validazione della dichiarazione di spesa ${desc_dichiarazione_di_spesa}', 'Si notifica che in data ${data_chiusura_validazione} e'' stata conclusa dall''ente competente 
la validazione della dichiarazione di spesa ${num_dichiarazione_di_spesa} del 
${data_dichiarazione_di_spesa} di questo progetto. E'' possibile visionare gli importi della 
validazione complessiva finora compiuta per il progetto nel conto economico. Per ogni 
ulteriore necessita'' o richiesta e'' possibile contattare l''assistenza o l''ente competente.');
Insert into PBANDI_D_TEMPLATE_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, DESCR_BREVE_TEMPLATE_NOTIFICA, COMP_SUBJECT, COMP_MESSAGE)
 Values
   (2, 'NotificaRimodulazione', 'Notifica rimodulazione del conto economico', 'Si notifica che in data ${data_chiusura_rimodulazione} sono state rimodulate dall''ente 
competente la spesa ammessa e/o le modalita'' di contribuzione del conto economico di questo 
progetto. E'' possibile visionare gli importi della spesa ammessa  per la rimodulazione 
compiuta nel conto economico. Per ogni ulteriore necessita'' o richiesta e'' possibile contattare l''assistenza o l''ente competente.');
Insert into PBANDI_D_TEMPLATE_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, DESCR_BREVE_TEMPLATE_NOTIFICA, COMP_SUBJECT, COMP_MESSAGE)
 Values
   (3, 'NotificaErogazione', 'Notifica erogazione per il progetto', 'Si notifica che l''ente competente ha erogato una quota del contributo concesso per questo 
progetto. Per ogni ulteriore necessita'' o richiesta e'' possibile contattare l''assistenza 
o l''ente competente.');
Insert into PBANDI_D_TEMPLATE_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, DESCR_BREVE_TEMPLATE_NOTIFICA, COMP_SUBJECT, COMP_MESSAGE)
 Values
   (4, 'NotificaRichiestaErogazionePrimoAnticipo', 'Notifica richiesta di erogazione primo anticipo', 'Si notifica che il beneficiario ha inviato la richiesta di erogazione del primo anticipo 
del contributo concesso. E'' possibile visionare il documento con la funzione di utilita'' 
per la ricerca e la stampa dei documenti collegati ad un progetto. Per ogni ulteriore 
necessita'' o richiesta e'' possibile contattare l''assistenza o l''ente competente.');
Insert into PBANDI_D_TEMPLATE_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, DESCR_BREVE_TEMPLATE_NOTIFICA, COMP_SUBJECT, COMP_MESSAGE)
 Values
   (5, 'NotificaRichiestaErogazioneAcconto', 'Notifica richiesta di erogazione acconto', 'Si notifica che il beneficiario ha inviato la richiesta di erogazione dell''acconto del 
contributo concesso. E'' possibile visionare il documento con la funzione di utilita'' per la 
ricerca e la stampa dei documenti collegati ad un progetto. Per ogni ulteriore necessita'' o 
richiesta e'' possibile contattare l''assistenza o l''ente competente.');
Insert into PBANDI_D_TEMPLATE_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, DESCR_BREVE_TEMPLATE_NOTIFICA, COMP_SUBJECT, COMP_MESSAGE)
 Values
   (6, 'NotificaRichiestaErogazioneUlterioreAcconto', 'Notifica richiesta di erogazione ulteriore acconto', 'Si notifica che il beneficiario ha inviato la richiesta di erogazione dell''ulteriore 
acconto del contributo concesso. E'' possibile visionare il documento con la funzione di 
utilita'' per la ricerca e la stampa dei documenti collegati ad un progetto. Per ogni 
ulteriore necessita'' o richiesta e'' possibile contattare l''assistenza o l''ente competente.');
Insert into PBANDI_D_TEMPLATE_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, DESCR_BREVE_TEMPLATE_NOTIFICA, COMP_SUBJECT, COMP_MESSAGE)
 Values
   (7, 'NotificaRichiestaErogazioneSaldo', 'Notifica richiesta di erogazione saldo', 'Si notifica che il beneficiario ha inviato la richiesta di erogazione del saldo del 
contributo concesso. E'' possibile visionare il documento con la funzione di utilita'' per la 
ricerca e la stampa dei documenti collegati ad un progetto. Per ogni ulteriore necessita'' o 
richiesta e'' possibile contattare l''assistenza o l''ente competente.');
Insert into PBANDI_D_TEMPLATE_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, DESCR_BREVE_TEMPLATE_NOTIFICA, COMP_SUBJECT, COMP_MESSAGE)
 Values
   (8, 'NotificaPropostaRimodulazione', 'Notifica proposta di rimodulazione del conto economico', 'Si notifica che in data ${data_invio_proposta} il beneficiario ha inviato una proposta di 
rimodulazione del conto economico di questo progetto. Per ogni ulteriore necessita'' o 
richiesta e'' possibile contattare l''assistenza o l''ente competente.');
Insert into PBANDI_D_TEMPLATE_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, DESCR_BREVE_TEMPLATE_NOTIFICA, COMP_SUBJECT, COMP_MESSAGE)
 Values
   (9, 'NotificaGestioneSpesaValidata', 'Notifica della rettifica della spesa validata', 'Si notifica che in data ${data_rettifica_spesa_validata} e'' stata conclusa dall''ente 
competente la rettifica della spesa validata di questo progetto. E'' possibile visionare i 
nuovi importi della validazione per il progetto nel conto economico. Per ogni ulteriore 
necessita'' o richiesta e'' possibile contattare l''assistenza o l''ente competente.');
Insert into PBANDI_D_TEMPLATE_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, DESCR_BREVE_TEMPLATE_NOTIFICA, COMP_SUBJECT, COMP_MESSAGE)
 Values
   (10, 'NotificaDiRevoca', 'Notifica della revoca per il progetto', 'Si notifica che in data ${data_revoca} l''ente competente ha revocato una quota del 
contributo concesso per questo progetto. Per ogni ulteriore necessita'' o richiesta e'' 
possibile contattare l''assistenza o l''ente competente.');
Insert into PBANDI_D_TEMPLATE_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, DESCR_BREVE_TEMPLATE_NOTIFICA, COMP_SUBJECT, COMP_MESSAGE)
 Values
   (11, 'NotificaComunicazioneRinuncia', 'Notifica della rinuncia per il progetto', 'Si notifica che in data ${data_invio_rinuncia} e'' stata inviata da parte del beneficiario 
del progetto una comunicazione di rinuncia al contributo. E'' possibile visionare la 
dichiarazione con la funzione di utilita'' per la ricerca e la stampa dei documenti 
collegati ad un progetto. Si ricorda di inserire a sistema l''atto di revoca.Per ogni 
ulteriore necessita'' o richiesta e'' possibile contattare l''assistenza o l''ente competente.');
Insert into PBANDI_D_TEMPLATE_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, DESCR_BREVE_TEMPLATE_NOTIFICA, COMP_SUBJECT, COMP_MESSAGE)
 Values
   (12, 'NotificaValidazioneDiSpesaFinale', 'Notifica validazione della dichiarazione di spesa finale', 'Si notifica che in data ${data_chiusura_validazione} e'' stata conclusa dall''ente competente 
la validazione della dichiarazione di spesa finale ${num_dichiarazione_di_spesa} del 
${data_dichiarazione_di_spesa} di questo progetto. E'' possibile visionare gli importi della 
validazione complessiva per il progetto nel conto economico. Per ogni ulteriore necessita'' 
o richiesta e'' possibile contattare l''assistenza o l''ente competente.');
Insert into PBANDI_D_TEMPLATE_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, DESCR_BREVE_TEMPLATE_NOTIFICA, COMP_SUBJECT, COMP_MESSAGE)
 Values
   (13, 'NotificaChiusuraDelProgetto', 'Notifica chiusura del progetto', 'Si notifica che in data ${data_chiusura_progetto} e'' stata chiusa dall''ente competente la 
gestione operativa di questo progetto. Per ogni ulteriore necessita'' o richiesta e'' 
possibile contattare l''assistenza o l''ente competente.');
Insert into PBANDI_D_TEMPLATE_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, DESCR_BREVE_TEMPLATE_NOTIFICA, COMP_SUBJECT, COMP_MESSAGE)
 Values
   (14, 'NotificaChiusuraDelProgettoPerRinuncia', 'Notifica chiusura del progetto per rinuncia', 'Si notifica che in data ${data_chiusura_progetto} e'' stata chiusa dall''ente competente la 
gestione operativa di questo progetto. Per ogni ulteriore necessita'' o richiesta e'' 
possibile contattare l''assistenza o l''ente competente.');
Insert into PBANDI_D_TEMPLATE_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, DESCR_BREVE_TEMPLATE_NOTIFICA, COMP_SUBJECT, COMP_MESSAGE)
 Values
   (15, 'NotificaChiusuraDUfficioDelProgetto', 'Notifica chiusura d''ufficio del progetto', 'Si notifica che in data ${data_chiusura_progetto} e'' stata chiusa d''ufficio dall''ente 
competente la gestione operativa di questo progetto. Per ogni ulteriore necessita'' o 
richiesta e'' possibile contattare l''assistenza o l''ente competente.');
COMMIT;

--PBANDI_D_METADATA_NOTIFICA
Insert into PBANDI_D_METADATA_NOTIFICA
   (ID_METADATA_NOTIFICA, NOME_PARAMETRO)
 Values
   (1, 'DESC_DICHIARAZIONE_DI_SPESA');
Insert into PBANDI_D_METADATA_NOTIFICA
   (ID_METADATA_NOTIFICA, NOME_PARAMETRO)
 Values
   (2, 'DATA_CHIUSURA_VALIDAZIONE');
Insert into PBANDI_D_METADATA_NOTIFICA
   (ID_METADATA_NOTIFICA, NOME_PARAMETRO)
 Values
   (3, 'DATA_DICHIARAZIONE_DI_SPESA');
Insert into PBANDI_D_METADATA_NOTIFICA
   (ID_METADATA_NOTIFICA, NOME_PARAMETRO)
 Values
   (4, 'NUM_DICHIARAZIONE_DI_SPESA');
Insert into PBANDI_D_METADATA_NOTIFICA
   (ID_METADATA_NOTIFICA, NOME_PARAMETRO)
 Values
   (5, 'DATA_CHIUSURA_RIMODULAZIONE');
Insert into PBANDI_D_METADATA_NOTIFICA
   (ID_METADATA_NOTIFICA, NOME_PARAMETRO)
 Values
   (6, 'DATA_INVIO_PROPOSTA');
Insert into PBANDI_D_METADATA_NOTIFICA
   (ID_METADATA_NOTIFICA, NOME_PARAMETRO)
 Values
   (7, 'DATA_RETTIFICA_SPESA_VALIDATA');
Insert into PBANDI_D_METADATA_NOTIFICA
   (ID_METADATA_NOTIFICA, NOME_PARAMETRO)
 Values
   (8, 'DATA_REVOCA');
Insert into PBANDI_D_METADATA_NOTIFICA
   (ID_METADATA_NOTIFICA, NOME_PARAMETRO)
 Values
   (9, 'DATA_INVIO_RINUNCIA');
Insert into PBANDI_D_METADATA_NOTIFICA
   (ID_METADATA_NOTIFICA, NOME_PARAMETRO)
 Values
   (10, 'DATA_CHIUSURA_PROGETTO');
COMMIT;

--PBANDI_R_PLACEHOLDER_NOTIFICA
Insert into PBANDI_R_PLACEHOLDER_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
 Values
   (1, 1, '${desc_dichiarazione_di_spesa}');
Insert into PBANDI_R_PLACEHOLDER_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
 Values
   (1, 2, '${data_chiusura_validazione}');
Insert into PBANDI_R_PLACEHOLDER_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
 Values
   (1, 4, '${num_dichiarazione_di_spesa}');
Insert into PBANDI_R_PLACEHOLDER_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
 Values
   (1, 3, '${data_dichiarazione_di_spesa}');
Insert into PBANDI_R_PLACEHOLDER_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
 Values
   (2, 5, '${data_chiusura_rimodulazione}');
Insert into PBANDI_R_PLACEHOLDER_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
 Values
   (8, 6, '${data_invio_proposta}');
Insert into PBANDI_R_PLACEHOLDER_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
 Values
   (9, 7, '${data_rettifica_spesa_validata}');
Insert into PBANDI_R_PLACEHOLDER_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
 Values
   (10, 8, '${data_revoca}');
Insert into PBANDI_R_PLACEHOLDER_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
 Values
   (11, 9, '${data_invio_rinuncia}');
Insert into PBANDI_R_PLACEHOLDER_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
 Values
   (12, 2, '${data_chiusura_validazione}');
Insert into PBANDI_R_PLACEHOLDER_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
 Values
   (12, 4, '${num_dichiarazione_di_spesa}');
Insert into PBANDI_R_PLACEHOLDER_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
 Values
   (12, 3, '${data_dichiarazione_di_spesa}');
Insert into PBANDI_R_PLACEHOLDER_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
 Values
   (13, 10, '${data_chiusura_progetto}');
Insert into PBANDI_R_PLACEHOLDER_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
 Values
   (14, 10, '${data_chiusura_progetto}');
Insert into PBANDI_R_PLACEHOLDER_NOTIFICA
   (ID_TEMPLATE_NOTIFICA, ID_METADATA_NOTIFICA, PLACEHOLDER)
 Values
   (15, 10, '${data_chiusura_progetto}');
COMMIT;

--PBANDI_C_COSTANTI   
Insert into PBANDI_C_COSTANTI
   (ATTRIBUTO, VALORE)
 Values
   ('PCK_PBANDI_PROCESSO.NUM_PROGETTI_BEN_BL', '20');
COMMIT;


/*
*** script da eseguire ad ogni rilascio
*/
update PBANDI_C_VERSIONE
set VERSIONE_DB = '2.14.0';

insert into PBANDI_C_ENTITA
(ID_ENTITA, NOME_ENTITA, FLAG_DA_TRACCIARE) 
(select seq_PBANDI_C_ENTITA.nextval,tabs.TABLE_NAME,'S'
from all_tables tabs
where tabs.OWNER = (select decode(instr(user,'_RW'),0,user,replace(user,'_RW',null)) from dual)
and tabs.TABLE_NAME like 'PBANDI_%'
and not exists (select 'x' from pbandi_c_entita where tabs.TABLE_NAME = NOME_ENTITA));

commit;

declare
  cursor curAttr is select col.COLUMN_NAME,en.id_ENTITA,col.TABLE_NAME
                    from all_tab_cols col,PBANDI_C_ENTITA en
                    where col.owner = (select decode(instr(user,'_RW'),0,user,replace(user,'_RW',null)) from dual)
                    and col.TABLE_NAME like 'PBANDI_%'
                    and col.COLUMN_NAME not like 'SYS_%'
                    and col.TABLE_NAME = en.NOME_ENTITA
                    and not exists (select 'x' from PBANDI_C_ATTRIBUTO att 
                                    where col.COLUMN_NAME = att.NOME_ATTRIBUTO
                                    and att.id_entita = en.id_ENTITA);
                                    
  nPosiz  PLS_INTEGER := NULL;                                                                          
begin
  for recAttr in curAttr loop

    BEGIN
      SELECT POSITION
      INTO   nPosiz 
      FROM   all_CONSTRAINTS A,all_CONS_COLUMNS B
      WHERE  A.CONSTRAINT_TYPE = 'P'
      AND    A.TABLE_NAME      = recAttr.TABLE_NAME
      AND    A.CONSTRAINT_NAME = B.CONSTRAINT_NAME
      AND    COLUMN_NAME       = recAttr.COLUMN_NAME;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        nPosiz := NULL;
    END;
    
    insert into PBANDI_C_ATTRIBUTO
    (ID_ATTRIBUTO, 
     NOME_ATTRIBUTO, 
     FLAG_DA_TRACCIARE, 
     ID_ENTITA,
     KEY_POSITION_ID) 
    values
    (SEQ_PBANDI_C_ATTRIBUTO.NEXTVAL,
     recAttr.COLUMN_NAME,
     'N',
     recAttr.id_ENTITA,
     nPosiz);
  end loop;
  COMMIT;
end;
/



