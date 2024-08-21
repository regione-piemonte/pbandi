/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SET DEFINE OFF;
Insert into PBANDI_C_REGOLA
(ID_REGOLA,DESC_BREVE_REGOLA,DESC_REGOLA,DT_INIZIO_VALIDITA)
values
(SEQ_PBANDI_C_REGOLA.nextval,'BR16','Nei documenti di spesa gestione campo task',to_date('01/01/2009','dd/mm/yyyy'));

commit;

DECLARE
  cursor curAteco is SELECT S.ID_SEDE,AA_2007.ID_ATTIVITA_ATECO
                     FROM   TMP_RACCORDO_ATECO_2002_2007 TMP,PBANDI_D_ATTIVITA_ATECO AA,PBANDI_T_SEDE S,
                            PBANDI_D_ATTIVITA_ATECO AA_2007
                     WHERE  TMP.COD_CATEGORIA_02 = AA.COD_ATECO
                     AND    AA.ID_ATTIVITA_ATECO = S.ID_ATTIVITA_ATECO
                     AND    TMP.COD_CATEGORIA_07 = AA_2007.COD_ATECO;
BEGIN
  FOR i IN 1..2 LOOP
    FOR recAteco IN curAteco LOOP
      update PBANDI_T_SEDE
      set    ID_ATTIVITA_ATECO = recAteco.ID_ATTIVITA_ATECO
	  where  id_sede			 = recAteco.ID_SEDE;
    END LOOP;
  END LOOP;
  COMMIT;
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('ERRORE = '||SQLERRM);
END;	
/

delete PBANDI_D_ATTIVITA_ATECO
where ID_ATTIVITA_ATECO in (
SELECT distinct ID_ATTIVITA_ATECO
FROM   TMP_RACCORDO_ATECO_2002_2007 TMP,PBANDI_D_ATTIVITA_ATECO AA
WHERE  TMP.COD_CATEGORIA_02 = AA.COD_ATECO);

commit;   

DELETE PBANDI_D_TIPO_ANAGRAFICA
WHERE DESC_BREVE_TIPO_ANAGRAFICA IN ('ADA-OPERATORE','ADMIN');

Insert into PBANDI_D_TIPO_ANAGRAFICA
   (ID_TIPO_ANAGRAFICA, DESC_BREVE_TIPO_ANAGRAFICA, DESC_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA)
 Values
   (19, 'BEN-MASTER', 'Utente operatore per tutti i Beneficiari', TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));

update PBANDI_D_TIPO_ANAGRAFICA
set DESC_BREVE_TIPO_ANAGRAFICA = 'MONITORAGGIO',
	DESC_TIPO_ANAGRAFICA       = 'Utente Monitoraggio CSI'
where  ID_TIPO_ANAGRAFICA = 18;  
   
SET DEFINE OFF;
Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('W050', 'Impossibile risalire all''indicatore qsn');
Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('W051', 'Impossibile risalire al tema prioritario');
Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('W052', 'Impossibile risalire all''indicatore risultato programma');
Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('W053', 'Codice ateco convertito automaticamente su 6 livelli');
Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('W054', 'Ente di Competenza non censito sul sistema');   
Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('W055', 'Errore nel popolamento delle fasi del monitoraggio del flusso GEFO');      
COMMIT;

SET DEFINE OFF;
Insert into PBANDI_C_RUOLO_DI_PROCESSO
   (ID_RUOLO_DI_PROCESSO, CODICE, DESC_RUOLO_DI_PROCESSO, ID_DEFINIZIONE_PROCESSO)
 Values
   (4, 'Beneficiario_Autorita', 'Ruolo di processo preposto alla visione delle attivita'' in capo agli utenti dei beneficiari e alle autorita'' di controllo e gestione', 9);
Insert into PBANDI_C_RUOLO_DI_PROCESSO
   (ID_RUOLO_DI_PROCESSO, CODICE, DESC_RUOLO_DI_PROCESSO, ID_DEFINIZIONE_PROCESSO)
 Values
   (1, 'Istruttore', 'Ruolo di processo preposto alla visione delle attività prorie di un istruttore', 9);
Insert into PBANDI_C_RUOLO_DI_PROCESSO
   (ID_RUOLO_DI_PROCESSO, CODICE, DESC_RUOLO_DI_PROCESSO, ID_DEFINIZIONE_PROCESSO)
 Values
   (2, 'Beneficiario', 'Ruolo di processo preposto alla visione delle attività prorie di un utente che opera per conto di uno o più beneficiari', 9);
Insert into PBANDI_C_RUOLO_DI_PROCESSO
   (ID_RUOLO_DI_PROCESSO, CODICE, DESC_RUOLO_DI_PROCESSO, ID_DEFINIZIONE_PROCESSO)
 Values
   (3, 'Istruttore_Autorita', 'Ruolo di processo preposto alla visione delle attivita'' in capo agli istruttori e alle autorita'' di controllo e gestione', 9);
COMMIT;

SET DEFINE OFF;
Insert into PBANDI_D_ATTIVITA_DI_PROCESSO
   (ID_ATTIVITA_DI_PROCESSO, DESC_BREVE_ATTIVITA_DI_PROCES, DESC_ATTIVITA_DI_PROCESSO, DT_INIZIO_VALIDITA)
 Values
   (1, 'Richiesta_di_erogazione_acconto', 'Richiesta di erogazione acconto', TO_DATE('01/28/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_ATTIVITA_DI_PROCESSO
   (ID_ATTIVITA_DI_PROCESSO, DESC_BREVE_ATTIVITA_DI_PROCES, DESC_ATTIVITA_DI_PROCESSO, DT_INIZIO_VALIDITA)
 Values
   (2, 'Richiesta_di_erogazione_primo_anticipo', 'Richiesta di erogazione primo anticipo', TO_DATE('01/28/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_ATTIVITA_DI_PROCESSO
   (ID_ATTIVITA_DI_PROCESSO, DESC_BREVE_ATTIVITA_DI_PROCES, DESC_ATTIVITA_DI_PROCESSO, DT_INIZIO_VALIDITA)
 Values
   (3, 'Dichiarazione_di_spesa', 'Dichiarazione di spesa', TO_DATE('01/28/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_ATTIVITA_DI_PROCESSO
   (ID_ATTIVITA_DI_PROCESSO, DESC_BREVE_ATTIVITA_DI_PROCES, DESC_ATTIVITA_DI_PROCESSO, DT_INIZIO_VALIDITA)
 Values
   (4, 'Richiesta_di_erogazione_ulteriore_acconto', 'Richiesta di erogazione ulteriore acconto', TO_DATE('01/28/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_ATTIVITA_DI_PROCESSO
   (ID_ATTIVITA_DI_PROCESSO, DESC_BREVE_ATTIVITA_DI_PROCES, DESC_ATTIVITA_DI_PROCESSO, DT_INIZIO_VALIDITA)
 Values
   (5, 'Richiesta_di_erogazione_saldo', 'Richiesta di erogazione saldo', TO_DATE('01/28/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_ATTIVITA_DI_PROCESSO
   (ID_ATTIVITA_DI_PROCESSO, DESC_BREVE_ATTIVITA_DI_PROCES, DESC_ATTIVITA_DI_PROCESSO, DT_INIZIO_VALIDITA)
 Values
   (6, 'Proposta_di_rimodulazione_del_conto_economico', 'Proposta di rimodulazione del conto economico', TO_DATE('01/28/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_ATTIVITA_DI_PROCESSO
   (ID_ATTIVITA_DI_PROCESSO, DESC_BREVE_ATTIVITA_DI_PROCES, DESC_ATTIVITA_DI_PROCESSO, DT_INIZIO_VALIDITA)
 Values
   (7, 'Rimodulazione_del_conto_economico', 'Rimodulazione del conto economico', TO_DATE('01/28/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_ATTIVITA_DI_PROCESSO
   (ID_ATTIVITA_DI_PROCESSO, DESC_BREVE_ATTIVITA_DI_PROCES, DESC_ATTIVITA_DI_PROCESSO, DT_INIZIO_VALIDITA)
 Values
   (8, 'Gestione_fideiussioni', 'Gestione fideiussioni', TO_DATE('01/28/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_ATTIVITA_DI_PROCESSO
   (ID_ATTIVITA_DI_PROCESSO, DESC_BREVE_ATTIVITA_DI_PROCES, DESC_ATTIVITA_DI_PROCESSO, DT_INIZIO_VALIDITA)
 Values
   (9, 'Modifica_erogazione', 'Modifica erogazione', TO_DATE('01/28/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_ATTIVITA_DI_PROCESSO
   (ID_ATTIVITA_DI_PROCESSO, DESC_BREVE_ATTIVITA_DI_PROCES, DESC_ATTIVITA_DI_PROCESSO, DT_INIZIO_VALIDITA)
 Values
   (10, 'V1', 'Validazione della dichiarazione di spesa', TO_DATE('01/28/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_ATTIVITA_DI_PROCESSO
   (ID_ATTIVITA_DI_PROCESSO, DESC_BREVE_ATTIVITA_DI_PROCES, DESC_ATTIVITA_DI_PROCESSO, DT_INIZIO_VALIDITA)
 Values
   (11, 'Erogazione', 'Erogazione', TO_DATE('01/28/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
COMMIT;

SET DEFINE OFF;
Insert into PBANDI_D_NOTIFICA
   (ID_NOTIFICA, DESC_BREVE_NOTIFICA, DESC_NOTIFICA, MESSAGGIO, DT_INIZIO_VALIDITA)
 Values
   (1, 'NRCE', 'Notifica rimodulazione del conto economico', 'Si notifica che sono state rimodulate dall''ente competente la spesa ammessa e/o le modalità di contribuzione del conto economico di questo progetto. E'' possibile visionare gli importi della spesa ammessa nel conto economico. Per ogni ulteriore necessità o richiesta è possibile contattare l''assistenza o l''ente competente.', TO_DATE('01/28/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_NOTIFICA
   (ID_NOTIFICA, DESC_BREVE_NOTIFICA, DESC_NOTIFICA, MESSAGGIO, DT_INIZIO_VALIDITA)
 Values
   (2, 'NVDS', 'Notifica validazione della dichiarazione di spesa', 'Si notifica che è stata conclusa dall''ente competente la validazione della dichiarazione di spesa di questo progetto. E'' possibile visionare gli importi della validazione complessiva finora compiuta per il progetto nel sottostante prospetto del conto economico. Per ogni ulteriore necessità o richiesta è possibile contattare l''assistenza o l''ente competente.', TO_DATE('01/28/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_NOTIFICA
   (ID_NOTIFICA, DESC_BREVE_NOTIFICA, DESC_NOTIFICA, MESSAGGIO, DT_INIZIO_VALIDITA)
 Values
   (3, 'NE', 'Notifica erogazione', 'Si notifica che l¿ente competente ha erogato una quota del contributo concesso per questo progetto. E'' possibile visionare l¿importo totale già erogato nel riepilogo della ¿modalità di agevolazione¿ del conto economico. Per ogni ulteriore necessità o richiesta è possibile contattare l''assistenza o l''ente competente.', TO_DATE('01/28/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_NOTIFICA
   (ID_NOTIFICA, DESC_BREVE_NOTIFICA, DESC_NOTIFICA, MESSAGGIO, DT_INIZIO_VALIDITA)
 Values
   (4, 'NREP', 'Notifica richiesta di erogazione primo anticipo', 'Si notifica che il beneficiario ha inviato la richiesta di erogazione del primo anticipo del contributo concesso. E'' possibile visionare il documento con la funzione di utilità per la ricerca e la stampa dei documenti collegati ad un progetto. Per ogni ulteriore necessità o richiesta è possibile contattare l''assistenza o l''ente competente.', TO_DATE('01/28/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_NOTIFICA
   (ID_NOTIFICA, DESC_BREVE_NOTIFICA, DESC_NOTIFICA, MESSAGGIO, DT_INIZIO_VALIDITA)
 Values
   (5, 'NREA', 'Notifica richiesta di erogazione acconto', 'Si notifica che il beneficiario ha inviato la richiesta di erogazione dell''acconto del contributo concesso. E'' possibile visionare il documento con la funzione di utilità per la ricerca e la stampa dei documenti collegati ad un progetto. Per ogni ulteriore necessità o richiesta è possibile contattare l''assistenza o l''ente competente.', TO_DATE('01/28/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_NOTIFICA
   (ID_NOTIFICA, DESC_BREVE_NOTIFICA, DESC_NOTIFICA, MESSAGGIO, DT_INIZIO_VALIDITA)
 Values
   (6, 'NREU', 'Notifica richiesta di erogazione ulteriore acconto', 'Si notifica che il beneficiario ha inviato la richiesta di erogazione dell''ulteriore acconto del contributo concesso. E'' possibile visionare il documento con la funzione di utilità per la ricerca e la stampa dei documenti collegati ad un progetto. Per ogni ulteriore necessità o richiesta è possibile contattare l''assistenza o l''ente competente.', TO_DATE('01/28/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_NOTIFICA
   (ID_NOTIFICA, DESC_BREVE_NOTIFICA, DESC_NOTIFICA, MESSAGGIO, DT_INIZIO_VALIDITA)
 Values
   (7, 'NRES', 'Notifica richiesta di erogazione saldo', 'Si notifica che il beneficiario ha inviato la richiesta di erogazione del saldo del contributo concesso. E'' possibile visionare il documento con la funzione di utilità per la ricerca e la stampa dei documenti collegati ad un progetto. Per ogni ulteriore necessità o richiesta è possibile contattare l''assistenza o l''ente competente.', TO_DATE('01/28/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
COMMIT;

SET DEFINE OFF;
Insert into PBANDI_R_ATTIVITA_NOTIFICA
   (ID_ATTIVITA_DI_PROCESSO, ID_NOTIFICA, ID_RUOLO_DI_PROCESSO)
 Values
   (1, 5, 1);
Insert into PBANDI_R_ATTIVITA_NOTIFICA
   (ID_ATTIVITA_DI_PROCESSO, ID_NOTIFICA, ID_RUOLO_DI_PROCESSO)
 Values
   (2, 4, 1);
Insert into PBANDI_R_ATTIVITA_NOTIFICA
   (ID_ATTIVITA_DI_PROCESSO, ID_NOTIFICA, ID_RUOLO_DI_PROCESSO)
 Values
   (4, 6, 1);
Insert into PBANDI_R_ATTIVITA_NOTIFICA
   (ID_ATTIVITA_DI_PROCESSO, ID_NOTIFICA, ID_RUOLO_DI_PROCESSO)
 Values
   (5, 7, 1);
Insert into PBANDI_R_ATTIVITA_NOTIFICA
   (ID_ATTIVITA_DI_PROCESSO, ID_NOTIFICA, ID_RUOLO_DI_PROCESSO)
 Values
   (11, 3, 2);
Insert into PBANDI_R_ATTIVITA_NOTIFICA
   (ID_ATTIVITA_DI_PROCESSO, ID_NOTIFICA, ID_RUOLO_DI_PROCESSO)
 Values
   (10, 2, 2);
Insert into PBANDI_R_ATTIVITA_NOTIFICA
   (ID_ATTIVITA_DI_PROCESSO, ID_NOTIFICA, ID_RUOLO_DI_PROCESSO)
 Values
   (7, 1, 2);
COMMIT;

SET DEFINE OFF;
Insert into PBANDI_R_RUOLO_TIPO_ANAGRAFICA
   (ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_TIPO_SOGGETTO_CORRELATO, ID_RUOLO_TIPO_ANAGRAFICA)
 Values
   (2, 16, 1, 19);
Insert into PBANDI_R_RUOLO_TIPO_ANAGRAFICA
   (ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_TIPO_SOGGETTO_CORRELATO, ID_RUOLO_TIPO_ANAGRAFICA)
 Values
   (4, 16, 6, 22);
Insert into PBANDI_R_RUOLO_TIPO_ANAGRAFICA
   (ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_RUOLO_TIPO_ANAGRAFICA)
 Values
   (1, 2, 1);
Insert into PBANDI_R_RUOLO_TIPO_ANAGRAFICA
   (ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_RUOLO_TIPO_ANAGRAFICA)
 Values
   (3, 2, 2);
Insert into PBANDI_R_RUOLO_TIPO_ANAGRAFICA
   (ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_RUOLO_TIPO_ANAGRAFICA)
 Values
   (1, 4, 3);
Insert into PBANDI_R_RUOLO_TIPO_ANAGRAFICA
   (ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_RUOLO_TIPO_ANAGRAFICA)
 Values
   (3, 4, 4);
Insert into PBANDI_R_RUOLO_TIPO_ANAGRAFICA
   (ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_RUOLO_TIPO_ANAGRAFICA)
 Values
   (2, 19, 5);
Insert into PBANDI_R_RUOLO_TIPO_ANAGRAFICA
   (ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_RUOLO_TIPO_ANAGRAFICA)
 Values
   (4, 19, 6);
Insert into PBANDI_R_RUOLO_TIPO_ANAGRAFICA
   (ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_RUOLO_TIPO_ANAGRAFICA)
 Values
   (3, 6, 7);
Insert into PBANDI_R_RUOLO_TIPO_ANAGRAFICA
   (ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_RUOLO_TIPO_ANAGRAFICA)
 Values
   (4, 6, 8);
Insert into PBANDI_R_RUOLO_TIPO_ANAGRAFICA
   (ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_RUOLO_TIPO_ANAGRAFICA)
 Values
   (3, 10, 9);
Insert into PBANDI_R_RUOLO_TIPO_ANAGRAFICA
   (ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_RUOLO_TIPO_ANAGRAFICA)
 Values
   (4, 10, 10);
Insert into PBANDI_R_RUOLO_TIPO_ANAGRAFICA
   (ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_RUOLO_TIPO_ANAGRAFICA)
 Values
   (3, 11, 11);
Insert into PBANDI_R_RUOLO_TIPO_ANAGRAFICA
   (ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_RUOLO_TIPO_ANAGRAFICA)
 Values
   (4, 11, 12);
Insert into PBANDI_R_RUOLO_TIPO_ANAGRAFICA
   (ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_RUOLO_TIPO_ANAGRAFICA)
 Values
   (3, 18, 13);
Insert into PBANDI_R_RUOLO_TIPO_ANAGRAFICA
   (ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_RUOLO_TIPO_ANAGRAFICA)
 Values
   (4, 18, 14);
Insert into PBANDI_R_RUOLO_TIPO_ANAGRAFICA
   (ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_RUOLO_TIPO_ANAGRAFICA)
 Values
   (1, 3, 15);
Insert into PBANDI_R_RUOLO_TIPO_ANAGRAFICA
   (ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_RUOLO_TIPO_ANAGRAFICA)
 Values
   (3, 3, 16);
Insert into PBANDI_R_RUOLO_TIPO_ANAGRAFICA
   (ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_RUOLO_TIPO_ANAGRAFICA)
 Values
   (1, 5, 17);
Insert into PBANDI_R_RUOLO_TIPO_ANAGRAFICA
   (ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_RUOLO_TIPO_ANAGRAFICA)
 Values
   (3, 5, 18);
Insert into PBANDI_R_RUOLO_TIPO_ANAGRAFICA
   (ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_TIPO_SOGGETTO_CORRELATO, ID_RUOLO_TIPO_ANAGRAFICA)
 Values
   (2, 16, 6, 20);
Insert into PBANDI_R_RUOLO_TIPO_ANAGRAFICA
   (ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_TIPO_SOGGETTO_CORRELATO, ID_RUOLO_TIPO_ANAGRAFICA)
 Values
   (4, 16, 1, 21);
COMMIT;

SET DEFINE OFF;
Insert into PBANDI_T_ENTE_COMPETENZA
   (ID_ENTE_COMPETENZA, DESC_ENTE, DT_INIZIO_VALIDITA, ID_TIPO_ENTE_COMPETENZA, ID_INDIRIZZO, ID_UTENTE_INS, COD_CIPE, USER_CIPE, ID_ATTIVITA_ATECO, ID_FORMA_GIURIDICA, DESC_BREVE_ENTE)
 Values
   (SEQ_PBANDI_T_ENTE_COMPETENZA.nextval, 'MUSEO DELL''AUTOMOBILE CARLO BISCARETTI DI RUFFIA', TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 7, 7030, 1, '30504', 'valentina.foggini', 2951, 26, 'MUSAUT');
Insert into PBANDI_D_UNITA_ORGANIZZATIVA
   (ID_UNITA_ORGANIZZATIVA, DESC_UNITA_ORGANIZZATIVA, COD_CIPE_UO, DT_INIZIO_VALIDITA, ID_ENTE_COMPETENZA)
 Values
   (21, 'AMMINISTRAZIONE', '70280', TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), SEQ_PBANDI_T_ENTE_COMPETENZA.currval);
   
   
Insert into PBANDI_T_ENTE_COMPETENZA
   (ID_ENTE_COMPETENZA, DESC_ENTE, DT_INIZIO_VALIDITA, ID_TIPO_ENTE_COMPETENZA, ID_INDIRIZZO, ID_UTENTE_INS, COD_CIPE, USER_CIPE, ID_ATTIVITA_ATECO, ID_FORMA_GIURIDICA, DESC_BREVE_ENTE)
 Values
   (SEQ_PBANDI_T_ENTE_COMPETENZA.nextval, 'MUSEO NAZIONALE DEL RISORGIMENTO ITALIANO ', TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 7, 7040, 1, '30515', 'marco.dioguardi', 2749, 26, 'MUSRIS');

Insert into PBANDI_D_UNITA_ORGANIZZATIVA
   (ID_UNITA_ORGANIZZATIVA, DESC_UNITA_ORGANIZZATIVA, COD_CIPE_UO, DT_INIZIO_VALIDITA, ID_ENTE_COMPETENZA)
 Values
   (22, 'AMMINISTRAZIONE', '70190', TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), SEQ_PBANDI_T_ENTE_COMPETENZA.currval);

COMMIT;

INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'FNGNRG77C05L219S');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SRLLSN76L13L219M');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'MSNFBA80P07A479H');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'BNCLNE82P55L304S');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'CMRVNB76H57L219L');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PSCFNC81B61L219N');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'CRLGRG83M64F152M');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'NVOLLN77D66L219O');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'DTTMNL83R49D205D');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'STVMRC77E20L219H');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'GHNMRA67P52G684F');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'MRZFPP75E26I726P');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PZZNGL78H46L328I');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'BRTLSE75E67L219U');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'GCMFNC76E69A485V');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'RGGVNT82S63L219R');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'MLTMRA66R46L219T');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'GRSSFN74R42L219I');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,2 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'MTSGNN75L05L219J');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,2 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'DNDSRN67P44L219M');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,2 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'ZFFNTN80R18L727T');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,2 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SRNCRL75M03L219Z');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,2 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'MRRPRZ54P43B966J');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,2 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'DMRVCN53E26L858G');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,2 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'CSTMGS57A46E379F');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,2 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'DMIGPP69S08L219Z');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,6 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'CLRMRN65P27C665F');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,6 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'TRCCRN66T57F604P');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,10,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'LSCPLG43D27D969L');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,10,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'ZNLFRZ59L25H620O');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,10,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'FRNPLA79R11E379N');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,10,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'MRNFRZ60T59L219E');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,10,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PLLMSM80T04H727J');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,11,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'BNDGPP48A25B285A');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,11,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'DCNMHL66T43D292H');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,18,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PRTMTR80A01L219F');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,8 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PVSMRA69P17L219J');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,8 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SCCPLA81S04C133N');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,8 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'CRZNGL71T42F979X');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,8 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SNSGRD72H08Z404W');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,6 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PCLFNC78D59L219C');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,6 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'DMAFNC62M52A385Q');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,6 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PRVBDT53L19L219H');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,6 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'GMBCRL64R67A479F');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,6 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SRGMRA74C48L219V');

INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'FNGNRG77C05L219S');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SRLLSN76L13L219M');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'MSNFBA80P07A479H');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'BNCLNE82P55L304S');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'CMRVNB76H57L219L');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PSCFNC81B61L219N');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'CRLGRG83M64F152M');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'NVOLLN77D66L219O');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'DTTMNL83R49D205D');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'STVMRC77E20L219H');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'GHNMRA67P52G684F');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'MRZFPP75E26I726P');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PZZNGL78H46L328I');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'BRTLSE75E67L219U');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'GCMFNC76E69A485V');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'RGGVNT82S63L219R');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'MLTMRA66R46L219T');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'GRSSFN74R42L219I');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'MTSGNN75L05L219J');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'DNDSRN67P44L219M');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'ZFFNTN80R18L727T');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SRNCRL75M03L219Z');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'MRRPRZ54P43B966J');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'DMRVCN53E26L858G');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'CSTMGS57A46E379F');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'DMIGPP69S08L219Z');

INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,6,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PVSMRA69P17L219J');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,6,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SCCPLA81S04C133N');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,6,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'CRZNGL71T42F979X');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,6,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SNSGRD72H08Z404W');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PCLFNC78D59L219C');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'DMAFNC62M52A385Q');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PRVBDT53L19L219H');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'GMBCRL64R67A479F');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SRGMRA74C48L219V');

INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PVSMRA69P17L219J');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SCCPLA81S04C133N');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'CRZNGL71T42F979X');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,4,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SNSGRD72H08Z404W');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,2,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PCLFNC78D59L219C');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,2,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'DMAFNC62M52A385Q');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,2,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PRVBDT53L19L219H');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,2,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'GMBCRL64R67A479F');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,2,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SRGMRA74C48L219V');

INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,2 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PVSMRA69P17L219J');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,2 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SCCPLA81S04C133N');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,2 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'CRZNGL71T42F979X');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,2 ,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SNSGRD72H08Z404W');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PCLFNC78D59L219C');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'DMAFNC62M52A385Q');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PRVBDT53L19L219H');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'GMBCRL64R67A479F');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SRGMRA74C48L219V');

INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PVSMRA69P17L219J');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SCCPLA81S04C133N');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'CRZNGL71T42F979X');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,19,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SNSGRD72H08Z404W');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,14,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PCLFNC78D59L219C');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,14,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'DMAFNC62M52A385Q');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,14,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PRVBDT53L19L219H');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,14,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'GMBCRL64R67A479F');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,14,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SRGMRA74C48L219V');

INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,11,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PVSMRA69P17L219J');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,11,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SCCPLA81S04C133N');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,11,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'CRZNGL71T42F979X');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,11,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SNSGRD72H08Z404W');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,11,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PCLFNC78D59L219C');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,11,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'DMAFNC62M52A385Q');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,11,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PRVBDT53L19L219H');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,11,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'GMBCRL64R67A479F');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,11,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SRGMRA74C48L219V');

INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,10,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PVSMRA69P17L219J');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,10,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SCCPLA81S04C133N');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,10,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'CRZNGL71T42F979X');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,10,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SNSGRD72H08Z404W');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,10,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PCLFNC78D59L219C');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,10,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'DMAFNC62M52A385Q');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,10,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'PRVBDT53L19L219H');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,10,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'GMBCRL64R67A479F');
INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,10,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'SRGMRA74C48L219V');

INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS,DT_INIZIO_VALIDITA) (select id_soggetto,11,id_utente_ins,to_date('01/01/2010','dd/mm/yyyy') from pbandi_t_soggetto where codice_fiscale_soggetto = 'DNDSRN67P44L219M');

commit;

update pbandi_r_bando_linea_intervent set MESI_DURATA_DA_DT_CONCESSIONE = 24 where PROGR_BANDO_LINEA_INTERVENTO = 1 ;
update pbandi_r_bando_linea_intervent set MESI_DURATA_DA_DT_CONCESSIONE = 36 where PROGR_BANDO_LINEA_INTERVENTO = 2 ;
update pbandi_r_bando_linea_intervent set MESI_DURATA_DA_DT_CONCESSIONE = 24 where PROGR_BANDO_LINEA_INTERVENTO = 3 ;
update pbandi_r_bando_linea_intervent set MESI_DURATA_DA_DT_CONCESSIONE = 24 where PROGR_BANDO_LINEA_INTERVENTO = 4 ;
update pbandi_r_bando_linea_intervent set MESI_DURATA_DA_DT_CONCESSIONE = 18 where PROGR_BANDO_LINEA_INTERVENTO = 5 ;
update pbandi_r_bando_linea_intervent set MESI_DURATA_DA_DT_CONCESSIONE = 18 where PROGR_BANDO_LINEA_INTERVENTO = 6 ;
update pbandi_r_bando_linea_intervent set MESI_DURATA_DA_DT_CONCESSIONE = 24 where PROGR_BANDO_LINEA_INTERVENTO = 7 ;
update pbandi_r_bando_linea_intervent set MESI_DURATA_DA_DT_CONCESSIONE = 36 where PROGR_BANDO_LINEA_INTERVENTO = 8 ;
update pbandi_r_bando_linea_intervent set MESI_DURATA_DA_DT_CONCESSIONE = 36 where PROGR_BANDO_LINEA_INTERVENTO = 9 ;
update pbandi_r_bando_linea_intervent set MESI_DURATA_DA_DT_CONCESSIONE = 24 where PROGR_BANDO_LINEA_INTERVENTO = 10;
update pbandi_r_bando_linea_intervent set MESI_DURATA_DA_DT_CONCESSIONE = 36 where PROGR_BANDO_LINEA_INTERVENTO = 11;
update pbandi_r_bando_linea_intervent set MESI_DURATA_DA_DT_CONCESSIONE = 24 where PROGR_BANDO_LINEA_INTERVENTO = 12;
update pbandi_r_bando_linea_intervent set MESI_DURATA_DA_DT_CONCESSIONE = 24 where PROGR_BANDO_LINEA_INTERVENTO = 23;
update pbandi_r_bando_linea_intervent set MESI_DURATA_DA_DT_CONCESSIONE = 12 where PROGR_BANDO_LINEA_INTERVENTO = 27;

commit;

DECLARE
  CURSOR curProg IS SELECT P.ID_PROGETTO,
                           D.DT_PRESENTAZIONE_DOMANDA,
                           NVL(NVL(P.DT_CONCESSIONE,P.DT_COMITATO),d.DT_PRESENTAZIONE_DOMANDA) DATA_PROGETTO,
                           NVL(ADD_MONTHS(NVL(P.DT_CONCESSIONE,P.DT_COMITATO),BLI.MESI_DURATA_DA_DT_CONCESSIONE),
                               TO_DATE('31/12/2013','dd/mm/yyyy')) DTF 
                    FROM   PBANDI_T_PROGETTO p,PBANDI_T_DOMANDA d,
                           pbandi_r_bando_linea_intervent bli
                    WHERE  P.ID_TIPO_OPERAZIONE           = 3
                    AND    P.ID_DOMANDA                   = D.ID_DOMANDA
                    AND    D.PROGR_BANDO_LINEA_INTERVENTO = BLI.PROGR_BANDO_LINEA_INTERVENTO;
  
  CURSOR curFase IS SELECT FM.ID_FASE_MONIT,FM.DESC_FASE_MONIT,FM.COD_IGRUE_T35
                    FROM   PBANDI_D_ITER i,PBANDI_D_FASE_MONIT fm
                    WHERE  I.ID_TIPO_OPERAZIONE = 3
                    AND    UPPER(I.DESC_ITER)   = 'AIUTI ALLE IMPRESE'
                    AND    I.ID_ITER            = FM.ID_ITER;
  
  nIdFaseMonit        PBANDI_R_PROGETTO_FASE_MONIT.ID_FASE_MONIT%TYPE;
  dDtInizioPrevista   PBANDI_R_PROGETTO_FASE_MONIT.DT_INIZIO_PREVISTA%TYPE;
  dDtFinePrevista     PBANDI_R_PROGETTO_FASE_MONIT.DT_FINE_PREVISTA%TYPE;
  dDtInizioEffettiva  PBANDI_R_PROGETTO_FASE_MONIT.DT_INIZIO_EFFETTIVA%TYPE;
  dDtFineEffettiva    PBANDI_R_PROGETTO_FASE_MONIT.DT_FINE_EFFETTIVA%TYPE;
  nCont               PLS_INTEGER := 0;
BEGIN
  FOR recProg IN curProg LOOP
    FOR recFase IN curFase LOOP
      IF recFase.COD_IGRUE_T35 = 'E01' THEN
        nIdFaseMonit       := recFase.ID_FASE_MONIT;
        dDtInizioPrevista  := recProg.DT_PRESENTAZIONE_DOMANDA;
        dDtFinePrevista    := recProg.DATA_PROGETTO;
        dDtInizioEffettiva := recProg.DT_PRESENTAZIONE_DOMANDA;
        dDtFineEffettiva   := recProg.DATA_PROGETTO;  
      ELSIF recFase.COD_IGRUE_T35 = 'E02' THEN
        nIdFaseMonit       := recFase.ID_FASE_MONIT;
        dDtInizioPrevista  := recProg.DATA_PROGETTO;
		dDtInizioEffettiva := recProg.DATA_PROGETTO;
        dDtFinePrevista    := recProg.DTF;
        IF TRUNC(recProg.DTF) < TRUNC(SYSDATE) THEN
          dDtFineEffettiva := recProg.DTF;
        ELSE
          dDtFineEffettiva := NULL;
        END IF;
      ELSIF recFase.COD_IGRUE_T35 = 'E03' THEN
        nIdFaseMonit       := recFase.ID_FASE_MONIT;
        dDtInizioPrevista  := recProg.DTF;
        dDtFinePrevista    := recProg.DTF;
        IF TRUNC(recProg.DTF) < TRUNC(SYSDATE) THEN
          dDtInizioEffettiva := recProg.DTF;
          dDtFineEffettiva   := recProg.DTF;
        ELSE
          dDtInizioEffettiva := NULL;
          dDtFineEffettiva   := NULL;
        END IF;
      END IF;
      
      SELECT COUNT(*)
      INTO   nCont
      FROM   PBANDI_R_PROGETTO_FASE_MONIT
      WHERE  ID_PROGETTO   = recProg.ID_PROGETTO
      AND    ID_FASE_MONIT = nIdFaseMonit;
      
      BEGIN
        IF nCont = 0 THEN
          INSERT INTO PBANDI_R_PROGETTO_FASE_MONIT
          (ID_PROGETTO, ID_FASE_MONIT, DT_INIZIO_PREVISTA, DT_FINE_PREVISTA, 
           DT_INIZIO_EFFETTIVA, DT_FINE_EFFETTIVA, ID_UTENTE_INS)
          VALUES
          (recProg.ID_PROGETTO,nIdFaseMonit,dDtInizioPrevista,dDtFinePrevista,
           dDtInizioEffettiva,dDtFineEffettiva,3);
        ELSE
          UPDATE PBANDI_R_PROGETTO_FASE_MONIT
          SET    DT_INIZIO_PREVISTA  = dDtInizioPrevista, 
                 DT_FINE_PREVISTA    = dDtFinePrevista, 
                 DT_INIZIO_EFFETTIVA = dDtInizioEffettiva, 
                 DT_FINE_EFFETTIVA   = dDtFineEffettiva,
                 ID_UTENTE_AGG       = 3
          WHERE  ID_PROGETTO         = recProg.ID_PROGETTO
          AND    ID_FASE_MONIT       = nIdFaseMonit;
        END IF;
      EXCEPTION
        WHEN OTHERS THEN
          dbms_output.put_line('Errore = '||sqlerrm||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
      END; 
    END LOOP;
  END LOOP;
  COMMIT;
END;
/

insert into PBANDI_C_REGOLA (ID_REGOLA,DESC_BREVE_REGOLA,DESC_REGOLA,DT_INIZIO_VALIDITA) values(SEQ_PBANDI_C_REGOLA.nextval,'BR17','Specifica se per i progetti di un bando_linea è possibile, per il Beneficiario, richiedere nuovi importi per le modalita'' di agevolazione',                                           to_date('01/01/2009','dd/mm/yyyy'));
insert into PBANDI_C_REGOLA (ID_REGOLA,DESC_BREVE_REGOLA,DESC_REGOLA,DT_INIZIO_VALIDITA) values(SEQ_PBANDI_C_REGOLA.nextval,'BR18','Specifica se per i progetti di un bando_linea, i controlli in rimodulazione sul superamento dell''importo massimo o delle percentuali ammissibili previsti da bando sono BLOCCANTI',   to_date('01/01/2009','dd/mm/yyyy'));
insert into PBANDI_C_REGOLA (ID_REGOLA,DESC_BREVE_REGOLA,DESC_REGOLA,DT_INIZIO_VALIDITA) values(SEQ_PBANDI_C_REGOLA.nextval,'BR19','Specifica se per i progetti di un bando_linea, i controlli in rimodulazione sull''uso di voci di spesa mai utilizzate in precedenza nel conto economico sono BLOCCANTI',               to_date('01/01/2009','dd/mm/yyyy'));
insert into PBANDI_C_REGOLA (ID_REGOLA,DESC_BREVE_REGOLA,DESC_REGOLA,DT_INIZIO_VALIDITA) values(SEQ_PBANDI_C_REGOLA.nextval,'BR20','Specifica se per i progetti di un bando_linea, i controlli in rimodulazione sul totale ultima spesa ammessa sono BLOCCANTI',                                                           to_date('01/01/2009','dd/mm/yyyy'));
insert into PBANDI_C_REGOLA (ID_REGOLA,DESC_BREVE_REGOLA,DESC_REGOLA,DT_INIZIO_VALIDITA) values(SEQ_PBANDI_C_REGOLA.nextval,'BR21','Specifica se per i progetti di un bando_linea, i controlli in rimodulazione sul superamento dell''importo massimo per la modalita'' di agevolazione previsto da bando sono BLOCCANTI', to_date('01/01/2009','dd/mm/yyyy'));
insert into PBANDI_C_REGOLA (ID_REGOLA,DESC_BREVE_REGOLA,DESC_REGOLA,DT_INIZIO_VALIDITA) values(SEQ_PBANDI_C_REGOLA.nextval,'BR22','Specifica se per i progetti di un bando_linea, i controlli in rimodulazione sulla percentuale ammissibile per la modalita'' di agevolazione prevista da bando sono BLOCCANTI',         to_date('01/01/2009','dd/mm/yyyy'));
insert into PBANDI_C_REGOLA (ID_REGOLA,DESC_BREVE_REGOLA,DESC_REGOLA,DT_INIZIO_VALIDITA) values(SEQ_PBANDI_C_REGOLA.nextval,'BR23','Specifica se per i progetti di un bando_linea, i controlli in rimodulazione sull''importo gia'' erogato per la modalita'' di agevolazione sono BLOCCANTI',                             to_date('01/01/2009','dd/mm/yyyy'));
insert into PBANDI_C_REGOLA (ID_REGOLA,DESC_BREVE_REGOLA,DESC_REGOLA,DT_INIZIO_VALIDITA) values(SEQ_PBANDI_C_REGOLA.nextval,'BR24','Specifica se per i progetti di un bando_linea, i controlli in rimodulazione sul totale dei nuovi importi agevolati rispetto alle fonti finanziarie sono BLOCCANTI',                    to_date('01/01/2009','dd/mm/yyyy'));
insert into PBANDI_C_REGOLA (ID_REGOLA,DESC_BREVE_REGOLA,DESC_REGOLA,DT_INIZIO_VALIDITA) values(SEQ_PBANDI_C_REGOLA.nextval,'BR25','Specifica se per i progetti di un bando_linea, i controlli in rimodulazione sul totale delle nuove quote finanziarie rispetto spesa ammessa in rimodulazione sono BLOCCANTI',          to_date('01/01/2009','dd/mm/yyyy'));
insert into PBANDI_C_REGOLA (ID_REGOLA,DESC_BREVE_REGOLA,DESC_REGOLA,DT_INIZIO_VALIDITA) values(SEQ_PBANDI_C_REGOLA.nextval,'BR26','Specifica se per i progetti di un bando_linea, i controlli in rimodulazione sul totale dei nuovi importi agevolati richiesti sono BLOCCANTI',                                          to_date('01/01/2009','dd/mm/yyyy'));
insert into PBANDI_C_REGOLA (ID_REGOLA,DESC_BREVE_REGOLA,DESC_REGOLA,DT_INIZIO_VALIDITA) values(SEQ_PBANDI_C_REGOLA.nextval,'BR27','Specifica se i progetti di un bando_linea utilizzano l''attivita'' di proposta di rimodulazione (Beneficiario)',                                          								to_date('01/01/2009','dd/mm/yyyy'));

update PBANDI_C_REGOLA
set    DESC_REGOLA = 'Specifica se i progetti di un bando_linea utilizzano l''attivita'' di rimodulazione (Istruttore)'
where  DESC_BREVE_REGOLA = 'BR12';

commit;

update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 1   ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 2   ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 3   ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 4   ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 5   ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 6   ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 7   ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 8   ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 9   ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 10  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 11  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 12  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 13  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 14  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 15  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 16  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 17  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 18  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 19  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 20  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 21  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 22  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 23  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 24  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 25  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 26  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 5  where id_ind_risultato_program = 27  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 28  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 29  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 30  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 31  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 32  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 33  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 34  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 35  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 36  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 37  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 38  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 39  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 40  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 41  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 42  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 43  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 44  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 45  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 46  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 47  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 48  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 49  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 50  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 51  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 52  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 53  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 54  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 55  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 56  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 57  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 58  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 59  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 60  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 61  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 62  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 63  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 64  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 65  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 66  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 67  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 68  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 69  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 70  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 55 where id_ind_risultato_program = 71  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 56 where id_ind_risultato_program = 72  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 56 where id_ind_risultato_program = 73  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 56 where id_ind_risultato_program = 74  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 56 where id_ind_risultato_program = 75  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 56 where id_ind_risultato_program = 76  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 56 where id_ind_risultato_program = 77  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 56 where id_ind_risultato_program = 78  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 56 where id_ind_risultato_program = 79  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 56 where id_ind_risultato_program = 80  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 56 where id_ind_risultato_program = 81  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 56 where id_ind_risultato_program = 82  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 56 where id_ind_risultato_program = 83  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 56 where id_ind_risultato_program = 84  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 56 where id_ind_risultato_program = 85  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 56 where id_ind_risultato_program = 86  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 56 where id_ind_risultato_program = 87  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 56 where id_ind_risultato_program = 88  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 56 where id_ind_risultato_program = 89  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 56 where id_ind_risultato_program = 90  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 91  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 92  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 93  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 94  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 95  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 96  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 97  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 98  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 99  ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 100 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 101 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 102 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 103 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 104 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 105 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 106 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 107 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 108 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 109 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 110 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 111 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 112 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 113 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 114 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 115 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 116 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 117 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 118 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 119 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 120 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 121 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 122 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 123 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 124 ;
update pbandi_d_ind_risultato_program set ID_LINEA_DI_INTERVENTO = 57 where id_ind_risultato_program = 125 ;

update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 45 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 46 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 47 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 48 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 49 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 50 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 51 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 52 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 53 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 54 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 55 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 56 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 57 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 58 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 59 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 60 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 61 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 62 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 63 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 64 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 65 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 66 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 67 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 68 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 69 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 70 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 71 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 72 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 73 ;
update pbandi_d_indicatori set id_linea_di_intervento = 5  where id_indicatori = 74 ;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 77 ;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 78 ;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 79 ;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 80 ;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 81 ;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 82 ;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 83 ;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 84 ;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 85 ;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 86 ;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 87 ;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 88 ;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 89 ;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 90 ;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 91 ;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 92 ;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 93 ;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 94 ;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 95 ;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 96 ;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 97 ;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 98 ;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 99 ;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 100;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 101;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 102;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 103;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 104;
update pbandi_d_indicatori set id_linea_di_intervento = 55 where id_indicatori = 105;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 106;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 107;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 108;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 109;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 110;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 111;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 112;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 113;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 114;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 115;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 116;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 117;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 118;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 119;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 120;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 121;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 122;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 123;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 124;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 125;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 126;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 127;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 128;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 129;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 130;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 131;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 132;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 133;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 134;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 135;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 136;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 137;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 138;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 139;
update pbandi_d_indicatori set id_linea_di_intervento = 57 where id_indicatori = 140;

commit;

alter table pbandi_d_ind_risultato_program modify id_linea_di_intervento not null;

SET DEFINE OFF;


Insert into PBANDI_C_TIPO_DOCUMENTO_INDEX
   (ID_TIPO_DOCUMENTO_INDEX, DESC_BREVE_TIPO_DOC_INDEX, DESC_TIPO_DOC_INDEX)
 Values
   (7, 'VCV', 'VERBALE CONTROLLO VALIDAZIONE');
Insert into PBANDI_C_TIPO_DOCUMENTO_INDEX
   (ID_TIPO_DOCUMENTO_INDEX, DESC_BREVE_TIPO_DOC_INDEX, DESC_TIPO_DOC_INDEX)
 Values
   (8, 'PR', 'PROPOSTA DI RIMODULAZIONE');   
Insert into PBANDI_C_TIPO_DOCUMENTO_INDEX
   (ID_TIPO_DOCUMENTO_INDEX, DESC_BREVE_TIPO_DOC_INDEX, DESC_TIPO_DOC_INDEX)
 Values
   (9, 'RM', 'RIMODULAZIONE');   
COMMIT;

SET DEFINE OFF;
Insert into PBANDI_D_NOTIFICA
   (ID_NOTIFICA, DESC_BREVE_NOTIFICA, DESC_NOTIFICA, MESSAGGIO, DT_INIZIO_VALIDITA)
 Values
   (8, 'NPRC', 'Notifica proposta rimodulazione del conto economico', 'Si notifica che il beneficiario ha inviato una proposta di rimodulazione del conto economico di questo progetto. E'' possibile visionare gli importi della nuova richiesta nel conto economico. Per ogni ulteriore necessità o richiesta è possibile contattare l''assistenza o l''ente competente.', TO_DATE('01/28/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
COMMIT;

SET DEFINE OFF;
Insert into PBANDI_R_ATTIVITA_NOTIFICA
   (ID_ATTIVITA_DI_PROCESSO, ID_NOTIFICA, ID_RUOLO_DI_PROCESSO)
 Values
   (6, 8, 2);
COMMIT;

insert into pbandi_d_tipo_dichiaraz_spesa
(id_tipo_dichiaraz_spesa,desc_breve_tipo_dichiara_spesa,desc_tipo_dichiarazione_spesa,dt_inizio_validita)
values
(1,'I','intermedia',to_date('01/01/2009','dd/mm/yyyy'));

insert into pbandi_d_tipo_dichiaraz_spesa
(id_tipo_dichiaraz_spesa,desc_breve_tipo_dichiara_spesa,desc_tipo_dichiarazione_spesa,dt_inizio_validita)
values
(2,'F','finale',to_date('01/01/2009','dd/mm/yyyy'));

update pbandi_t_dichiarazione_spesa
set    id_tipo_dichiaraz_spesa = 1;



ALTER TABLE pbandi_t_dichiarazione_spesa
modify id_tipo_dichiaraz_spesa not null;



declare
  cursor cur is select DISTINCT ds.id_progetto,DI.ID_DOCUMENTO_INDEX
                from   PBANDI_T_DICHIARAZIONE_SPESA DS,pbandi_c_entita e,pbandi_t_documento_index di
                where  di.id_entita  = e.id_entita
                and    e.nome_entita = 'PBANDI_T_DICHIARAZIONE_SPESA';
begin
  for rec in cur loop
    update pbandi_t_documento_index
    set    id_progetto        = rec.id_progetto
    where  ID_DOCUMENTO_INDEX = rec.ID_DOCUMENTO_INDEX;
  end loop;
  commit;
end;
/    

SET DEFINE OFF;
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (2, 1, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (3, 1, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (1, 1, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (5, 1, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (7, 1, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (11, 1, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (1, 2, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (2, 2, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (3, 2, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (1, 3, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (5, 2, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (7, 2, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (10, 2, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (11, 2, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (2, 3, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (3, 3, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (1, 4, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (5, 3, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (7, 3, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (10, 3, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (11, 3, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (2, 4, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (3, 4, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (1, 6, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (7, 4, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (10, 4, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (11, 4, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (5, 5, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (8, 5, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (9, 5, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (2, 7, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (1, 8, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (5, 7, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (2, 9, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (5, 9, 0);
Insert into PBANDI_R_TIPO_DOC_MODALITA_PAG
   (ID_TIPO_DOCUMENTO_SPESA, ID_MODALITA_PAGAMENTO, ID_UTENTE_INS)
 Values
   (4, 1, 1);
COMMIT;

update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 196, id_tipologia_cipe = 42, id_obiettivo_specif_qsn = 14 where progr_bando_linea_intervento = 1 ; 
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 149, id_tipologia_cipe = 32, id_obiettivo_specif_qsn = 18 where progr_bando_linea_intervento = 2 ;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 195, id_tipologia_cipe = 38, id_obiettivo_specif_qsn = 18 where progr_bando_linea_intervento = 3 ;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 65 , id_tipologia_cipe = 38, id_obiettivo_specif_qsn = 14 where progr_bando_linea_intervento = 4 ;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 199, id_tipologia_cipe = 37, id_obiettivo_specif_qsn = 14 where progr_bando_linea_intervento = 5 ;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 199, id_tipologia_cipe = 37, id_obiettivo_specif_qsn = 14 where progr_bando_linea_intervento = 6 ;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 199, id_tipologia_cipe = 37, id_obiettivo_specif_qsn = 14 where progr_bando_linea_intervento = 7 ;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 199, id_tipologia_cipe = 42, id_obiettivo_specif_qsn = 14 where progr_bando_linea_intervento = 8 ;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 176, id_tipologia_cipe = 26, id_obiettivo_specif_qsn = 14 where progr_bando_linea_intervento = 9 ;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 199, id_tipologia_cipe = 42, id_obiettivo_specif_qsn = 14 where progr_bando_linea_intervento = 10;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 199, id_tipologia_cipe = 42, id_obiettivo_specif_qsn = 14 where progr_bando_linea_intervento = 11;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 190, id_tipologia_cipe = 42, id_obiettivo_specif_qsn = 14 where progr_bando_linea_intervento = 12;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 247, id_tipologia_cipe = 4 , id_obiettivo_specif_qsn = 39 where progr_bando_linea_intervento = 13;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 247, id_tipologia_cipe = 4 , id_obiettivo_specif_qsn = 39 where progr_bando_linea_intervento = 14;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 247, id_tipologia_cipe = 4 , id_obiettivo_specif_qsn = 39 where progr_bando_linea_intervento = 15;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 247, id_tipologia_cipe = 4 , id_obiettivo_specif_qsn = 39 where progr_bando_linea_intervento = 16;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 247, id_tipologia_cipe = 9 , id_obiettivo_specif_qsn = 39 where progr_bando_linea_intervento = 17;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 248, id_tipologia_cipe = 4 , id_obiettivo_specif_qsn = 39 where progr_bando_linea_intervento = 18;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 247, id_tipologia_cipe = 9 , id_obiettivo_specif_qsn = 40 where progr_bando_linea_intervento = 19;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 253, id_tipologia_cipe = 9 , id_obiettivo_specif_qsn = 39 where progr_bando_linea_intervento = 20;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 228, id_tipologia_cipe = 9 , id_obiettivo_specif_qsn = 39 where progr_bando_linea_intervento = 21;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 228, id_tipologia_cipe = 9 , id_obiettivo_specif_qsn = 39 where progr_bando_linea_intervento = 22;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 226, id_tipologia_cipe = 42, id_obiettivo_specif_qsn = 16 where progr_bando_linea_intervento = 23;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 91 , id_tipologia_cipe = 13, id_obiettivo_specif_qsn = 23 where progr_bando_linea_intervento = 24;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 247, id_tipologia_cipe = 4 , id_obiettivo_specif_qsn = 39 where progr_bando_linea_intervento = 25;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 223, id_tipologia_cipe = 44, id_obiettivo_specif_qsn = 30 where progr_bando_linea_intervento = 26;
update pbandi_r_bando_linea_intervent set ID_CATEGORIA_CIPE = 226, id_tipologia_cipe = 37, id_obiettivo_specif_qsn = 16 where progr_bando_linea_intervento = 27;

commit;

SET DEFINE OFF;
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (2, 0, 7);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (3, 0, 7);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (4, 0, 7);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (5, 0, 7);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (2, 0, 1);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (3, 0, 1);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (4, 0, 1);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (5, 0, 1);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (6, 0, 1);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (8, 0, 1);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (10, 0, 1);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (11, 0, 1);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (14, 0, 1);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (16, 0, 1);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (18, 0, 1);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (19, 0, 1);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (2, 0, 2);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (3, 0, 2);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (4, 0, 2);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (5, 0, 2);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (6, 0, 2);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (8, 0, 2);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (10, 0, 2);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (11, 0, 2);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (14, 0, 2);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (6, 0, 4);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (8, 0, 4);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (10, 0, 4);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (11, 0, 4);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (14, 0, 4);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (2, 0, 6);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (3, 0, 6);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (4, 0, 6);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (5, 0, 6);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (6, 0, 6);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (8, 0, 6);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (10, 0, 6);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (11, 0, 6);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (14, 0, 6);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (16, 0, 6);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (18, 0, 6);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (19, 0, 6);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (2, 0, 8);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (3, 0, 8);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (4, 0, 8);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (5, 0, 8);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (6, 0, 8);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (8, 0, 8);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (10, 0, 8);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (11, 0, 8);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (14, 0, 8);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (16, 0, 8);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (18, 0, 8);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (19, 0, 8);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (6, 0, 7);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (8, 0, 7);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (10, 0, 7);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (11, 0, 7);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (14, 0, 7);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (2, 0, 9);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (3, 0, 9);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (4, 0, 9);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (5, 0, 9);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (6, 0, 9);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (8, 0, 9);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (10, 0, 9);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (11, 0, 9);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (14, 0, 9);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (16, 0, 9);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (18, 0, 9);
Insert into PBANDI_R_DOC_INDEX_TIPO_ANAG
   (ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, ID_TIPO_DOCUMENTO_INDEX)
 Values
   (19, 0, 9);
COMMIT;

SET DEFINE OFF;
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 1, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 2, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 3, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 4, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 5, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 6, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 7, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 8, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 9, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 10, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 11, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 12, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 13, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 14, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 15, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 16, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 17, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 18, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 19, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 20, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 21, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 22, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 23, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 24, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 25, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 26, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (7, 27, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 1, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 2, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 3, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 4, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 5, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 6, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 7, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 8, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 9, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 10, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 11, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 12, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 13, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 14, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 15, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 16, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 17, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 18, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 19, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 20, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 21, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 22, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 23, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 24, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 25, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 26, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (8, 27, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 1, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 2, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 3, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 4, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 5, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 6, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 7, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 8, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 9, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 10, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 11, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 12, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 13, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 14, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 15, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 16, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 17, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 18, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 19, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 20, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 21, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 22, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 23, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 24, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 25, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 26, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (9, 27, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 11, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 13, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 14, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 15, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 16, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 17, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 18, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 20, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 21, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 23, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 25, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 11, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 13, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 14, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 15, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 16, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 17, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 18, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 20, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 21, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 23, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 25, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 18, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 20, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 21, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 23, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 25, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 1, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 4, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 26, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 1, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 2, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 3, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 4, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 5, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 6, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 7, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 8, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 9, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 10, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 12, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 19, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 22, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 24, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 26, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (1, 27, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 1, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 3, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 4, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 5, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 6, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 7, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 8, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 9, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 10, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 12, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 19, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 22, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 24, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 26, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (2, 27, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 3, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 5, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 6, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 7, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 8, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 9, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 10, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 12, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 19, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 22, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 24, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 27, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 1, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 3, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 4, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 5, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 6, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 7, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 8, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 9, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 10, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 12, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 19, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 22, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 24, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 26, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 27, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 11, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 13, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 14, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 15, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 16, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 17, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 18, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 20, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 21, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 23, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (4, 25, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 11, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 13, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 14, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 15, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 16, 0);
Insert into PBANDI_R_TP_DOC_IND_BAN_LI_INT
   (ID_TIPO_DOCUMENTO_INDEX, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS)
 Values
   (6, 17, 0);
COMMIT;


Insert into PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO,ID_TIPO_ANAGRAFICA,ID_UTENTE_INS,ID_UTENTE_AGG,FLAG_AGGIORNATO_FLUX,DT_INIZIO_VALIDITA,DT_FINE_VALIDITA) values (4760,3,0,null,'N',to_date('01/01/2010','DD/mm/yyyy'),null);
Insert into PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO,ID_TIPO_ANAGRAFICA,ID_UTENTE_INS,ID_UTENTE_AGG,FLAG_AGGIORNATO_FLUX,DT_INIZIO_VALIDITA,DT_FINE_VALIDITA) values (4761,3,0,null,'N',to_date('01/01/2010','DD/mm/yyyy'),null);
Insert into PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO,ID_TIPO_ANAGRAFICA,ID_UTENTE_INS,ID_UTENTE_AGG,FLAG_AGGIORNATO_FLUX,DT_INIZIO_VALIDITA,DT_FINE_VALIDITA) values (4762,3,0,null,'N',to_date('01/01/2010','DD/mm/yyyy'),null);
Insert into PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO,ID_TIPO_ANAGRAFICA,ID_UTENTE_INS,ID_UTENTE_AGG,FLAG_AGGIORNATO_FLUX,DT_INIZIO_VALIDITA,DT_FINE_VALIDITA) values (4758,3,0,null,'N',to_date('01/01/2010','DD/mm/yyyy'),null);
Insert into PBANDI_R_SOGG_TIPO_ANAGRAFICA (ID_SOGGETTO,ID_TIPO_ANAGRAFICA,ID_UTENTE_INS,ID_UTENTE_AGG,FLAG_AGGIORNATO_FLUX,DT_INIZIO_VALIDITA,DT_FINE_VALIDITA) values (4763,3,0,null,'N',to_date('01/01/2010','DD/mm/yyyy'),null);
commit;

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

