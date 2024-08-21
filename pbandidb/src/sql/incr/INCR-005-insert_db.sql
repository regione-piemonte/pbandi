/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SET DEFINE OFF;
Insert into PBANDI_D_NOME_BATCH
   (ID_NOME_BATCH, NOME_BATCH, DESC_BATCH)
 Values
   (7, 'PBAN-CSP', 'Procedura di caricamento scheda progetto');

Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('E101', 'Errore nel caricamento scheda progetto');

insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(1 ,'LI013','2007IT162PO005 - VILLE E GIARDINI STORICI DELLA PROVINCIA DI GENOVA - Recuperi, restauri, interventi di valorizzazione del sistema delle ville storiche.',                                                  to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(2 ,'LI010','2007IT162PO005 RIQUALIFICAZIONE DEL TESSUTO URBANO - spazi infrastrutturati per attivita'' ludiche, percorso ciclo-pedonale, rafforzamento di piccoli impianti sportivi, incremento del verde',             to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(3 ,'LI015','2007IT162PO005-TERRE DI CASTELLI E DIMORE DIFENSIVE IN PROVINCIA DI GENOVA-Restauri conservativi,recuperi e realizzazione di infrastrutture espositive nei Castelli della famiglia nobiliare dei Fieschi',  to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(4 ,'LI016','2007IT162PO005 - MUSEI IN RETE: IL LAVORO DELL''UOMO E LE TRASFORMAZIONI DEL TERRITORIO-Valorizzazione e strutturaz. di musei locali in prov. di Genova nel contesto della conservazione delle tradizioni', to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(5 ,'LI017','2007IT162PO005 - SISTEMA DEI MUSEI DELLA PROVINCIA DI SAVONA: UN CALEIDOSCOPIO D''ARTE,STORIA E CULTURA - Ristruttrazione, recupero e valorizzazione di musei locali in chiave turistica e di fruizione.',  to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(6 ,'LI018','2007IT162PO005 - I SISTEMI DIFENSIVI DEI MARCHESATI DI CLAVESANA E DEL CARRETTO - Recupero,restauro e valorizzazione dei castelli e delle mura di strutture offensive e difensive in provincia di Savona',  to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(7 ,'LI019','2007IT162PO005 - PASSAGGIO NELLA TERRA DELLA LUNA: CASTELLI E FORTIFICAZIONI - Recuperi funzionali, restauri conservativi,valorizzazione dei castelli,torri e sistemi difensivi in provincia della Spezia', to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(8 ,'LI020','2007IT162PO005 - PASSAGGIO NELLA TERRA DELLA LUNA: ITINERARI E SITI ARCHEOLOGICI - Riqualificazioni territoriali e valorizzazione di siti, realizzazione musei,centri culturali ed aree archeologiche.',    to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(9 ,'LI021','2007IT162PO005 - SISTEMA DELLE VILLE E GIARDINI DEL PONENTE LIGURE - Valorizzazione, recupero e completamento di percorsi storico botanici negli antichi giardini nelle ville storiche dell''imperiese',    to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(10,'LI022','2007IT162PO005 - SISTEMA DIFENSIVO NELLA STORIA DEL PONENTE LIGURE - Restauro, valorizzazione, rifunzionalizzazione del sistema difensivo imperiesi',                                                       to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(11,'LI023','2007IT162PO005 - VALORIZZAZIONE DELLA RETE E SISTEMA DEI MUSEI DI ECCELLENZA DELLA PROVINCIA DI IMPERIA - Realizzazione o allestimenti di musei presso i palazzi e ville storiche recuperate.',             to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(12,'LI014','2007IT162PO005 - DIMORE E PARCHI DELLA GRANDE GENOVA - Restauri e sistemazioni dei parchi e giardini delle antiche dimore genovesi per svilupparne la fruizione e la contestualizzazione.',                 to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(13,'LI009','2007IT162PO005 PIGNA MARE - recupero di edifici storici compromessi dall''abbandono e dal degrado; pedonalizzazione, riqualificazione e ridisegno di piazze/spazi/assi commerciali',                        to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(14,'LI008','2007IT162PO005 CENTRO CITTA'' - recupero di immobili da destinare a servizi pubblici di interesse sociale/culturale, pedonalizzazione di spazi pubblici, riorganizzazione in chiave sostenibile del trasp',  to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(15,'TO001','2007IT162PO012 - Altavaldelsa: Citta'' Di Citta'' - Comuni: Colle Val D''elsa (coordinatore), Poggibonsi',                                                                                                  to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(16,'TO002','2007IT162PO012 - Da Via Regia a Viareggio - Comune: Viareggio',                                                                                                                                             to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(17,'TO003','2007IT162PO012 - La Citta'' Dei Saperi - Comuni: Firenze (coordinatore), Campi Bisenzio, Scandicci',                                                                                                        to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(18,'TO004','2007IT162PO012 - Livorno Citta'' delle Opportunita'' -  Comune: Livorno',                                                                                                                                   to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');       
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(19,'TO005','2007IT162PO012 - Lucca Dentro - Comune: Lucca',                                                                                                                                                             to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(20,'TO006','2007IT162PO012 - Parco Expo'' - Comune: Prato',                                                                                                                                                             to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(21,'TO007','2007IT162PO012 - Piano integrato di sviluppo urbano sostenibile per la citta'' di Arezzo Comune:  Arezzo',                                                                                                  to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(22,'TO008','2007IT162PO012 - Piombino 2015: Progetto Citta'' Futura Comune: Piombino',                                                                                                                                  to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(23,'TO009','2007IT162PO012 - Pisa2: Pisa X Patrimonio Culturale, Innovazione, Saperi e Accoglienza Comune: Pisa',                                                                                                       to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(24,'TO010','2007IT162PO012 - Piuss Carrara e Massa. Un Territorio da Rivivere',                                                                                                                                         to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(25,'TO011','2007IT162PO012 - Piuss Comune di Pistoia. Dall''antico centro storico al nuovo centro urbano Comune: Pistoia',                                                                                              to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(26,'TO012','2007IT162PO012 - Realizzazione Parco Centrale Comune: Follonica',                                                                                                                                           to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(27,'TO013','2007IT162PO012 - ''GROSSETO CITTA'' CULTURA" - Comune: Grosseto',                                                                                                                                           to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(28,'TO014','2007IT162PO012 - "Montevarchi citta'' del Valdarno e porta del Chianti: il centro commerciale naturale luogo di incontro e di funzioni pregiate" - Comune: Montevarchi',                                    to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(29,'TO015','2007IT162PO012 - Quarrata sara'' - Comune: Quarrata',                                                                                                                                                       to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(30,'TO016','2007IT162PO012 - CASCINA s''impegna per P.R.I.M.A. - Comune: Cascina',                                                                                                                                      to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(31,'LI011','2007LI002FA005 - Progetto integrato Sistema Parchi e Alta Via Monti Liguri',                                                                                                                                to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(32,'LI012','2007LI002FA005 - Programma triennale per la ricerca e innovazione: progetti integrati ad alta tecnologia',                                                                                                  to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(33,'LI003','2007IT162PO005 MADDALENA - recupero/riconversione di alcuni immobili per il ritorno di attivita'' e funzioni pubbliche di interesse socio-culturale; riqualificazione di percorsi per maggiore sicurezza',  to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(34,'LI001','2007IT162PO005 PRA'' MARINA - trasformazione delle aree abbandonate comprese tra la linea ferroviaria dismessa ed il canale di calma in un grande parco verde',                                             to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(35,'LI002','2007IT162PO005 QUARTIERI COSTIERI DEL LEVANTE - miglioramento collegamenti pedonali/viari,potenziamento/integrazione servizio di trasporto pubblico,recupero di aree/volumi produttivi abbandonati/degra',  to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(36,'LI004','2007IT162PO005 DAL PARASIO AL MARE - completamento sistema di ascensori pubblici per eliminare le barriere architettoniche; pedonalizzazione di spazi pubblici;riconversione del mercato in centro socio',  to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(37,'LI005','2007IT162PO005 PIU'' SAVONA - riqualificazione zona compresa tra la fortezza del Priamar e la foce del Letimbro  recuperando aree e volumi degradati/abbandonati; costruzione nuova piscina',               to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(38,'LI006','2007IT162PO005 SAMPIERDARENA - pedonalizzazioni e sistemazioni lungo cortine commerciali; rinnovo dell''ascensore per l''ospedale; rafforzamento funzioni del municipio; nuovi servizi rivolti alle fasce',  to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(39,'LI007','2007IT162PO005 MOLASSANA - riorganizzazione sistema di viabilita'' (nuovi percorsi/piazze pubbliche/pedonalizzazioni),valorizzazione acquedotto storico,parcheggio di interscambio,linea trasporto pubblic',to_date('01/01/2010','dd/mm/yyyy'),'1','progetto integrato');     
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(40,'CB001','2007CB163PO033 progetto di cooperazione transfrontaliera: operazione bilaterale o plurilaterale promossa da un partenariato composto da soggetti dell''area di cooperazione',                               to_date('01/01/2010','dd/mm/yyyy'),'5','progetto interregionale');
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(41,'CB003','2007CB163PO036 Spese assistenza tecnica temporanea',                                                                                                                                                        to_date('01/01/2010','dd/mm/yyyy'),'5','progetto interregionale');
insert into pbandi_d_progetto_complesso(id_progetto_complesso,cod_igrue_t9,desc_progetto_complesso,dt_inizio_validita,cod_tipologia,desc_tipologia) values(42,'CB002','2007CB163PO036 spese assistenza tecnica anno 2008',                                                                                                                                                         to_date('01/01/2010','dd/mm/yyyy'),'5','progetto interregionale');

insert into pbandi_d_motivo_scostamento(id_motivo_scostamento,cod_igrue_T37_T49_T53,desc_motivo_scostamento,dt_inizio_validita) values(1,1,'Problemi Amministrativi',to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_motivo_scostamento(id_motivo_scostamento,cod_igrue_T37_T49_T53,desc_motivo_scostamento,dt_inizio_validita) values(2,2,'Problematiche Tecniche',to_date('01/01/2010','dd/mm/yyyy'));

delete PBANDI_R_SOGG_PROG_SOGG_CORREL
where PROGR_SOGGETTI_CORRELATI in (select PROGR_SOGGETTI_CORRELATI
								   from   PBANDI_R_SOGGETTI_CORRELATI
								   where ID_TIPO_SOGGETTO_CORRELATO in (15,16));

delete PBANDI_R_SOGG_DOM_SOGG_CORREL
where PROGR_SOGGETTI_CORRELATI in (select PROGR_SOGGETTI_CORRELATI
								   from   PBANDI_R_SOGGETTI_CORRELATI
								   where ID_TIPO_SOGGETTO_CORRELATO in (15,16));

delete PBANDI_R_SOGGETTI_CORRELATI
where ID_TIPO_SOGGETTO_CORRELATO in (15,16);

delete PBANDI_D_TIPO_SOGG_CORRELATO
where ID_TIPO_SOGGETTO_CORRELATO in (15,16);
   
COMMIT;

SET DEFINE OFF;
Insert into PBANDI_D_TIPO_PERIODO
   (ID_TIPO_PERIODO, DESC_BREVE_TIPO_PERIODO, DESC_TIPO_PERIODO, DT_INIZIO_VALIDITA)
 Values
   (1, 'A', 'Anno', TO_DATE('03/22/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into PBANDI_D_TIPO_PERIODO
   (ID_TIPO_PERIODO, DESC_BREVE_TIPO_PERIODO, DESC_TIPO_PERIODO, DT_INIZIO_VALIDITA)
 Values
   (2, 'R', 'Range', TO_DATE('03/22/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
COMMIT;

SET DEFINE OFF;
Insert into PBANDI_T_PERIODO
   (ID_PERIODO, ID_TIPO_PERIODO, DESC_PERIODO, DESC_PERIODO_VISUALIZZATA, DT_INIZIO_VALIDITA, ID_UTENTE_INS)
 Values
   (1, 1, '2007', '2007', TO_DATE('03/22/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 0);
Insert into PBANDI_T_PERIODO
   (ID_PERIODO, ID_TIPO_PERIODO, DESC_PERIODO, DESC_PERIODO_VISUALIZZATA, DT_INIZIO_VALIDITA, ID_UTENTE_INS)
 Values
   (2, 1, '2008', '2008', TO_DATE('03/22/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 0);
Insert into PBANDI_T_PERIODO
   (ID_PERIODO, ID_TIPO_PERIODO, DESC_PERIODO, DESC_PERIODO_VISUALIZZATA, DT_INIZIO_VALIDITA, ID_UTENTE_INS)
 Values
   (3, 1, '2009', '2009', TO_DATE('03/22/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 0);
Insert into PBANDI_T_PERIODO
   (ID_PERIODO, ID_TIPO_PERIODO, DESC_PERIODO, DESC_PERIODO_VISUALIZZATA, DT_INIZIO_VALIDITA, ID_UTENTE_INS)
 Values
   (4, 1, '2010', '2010', TO_DATE('03/22/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 0);
Insert into PBANDI_T_PERIODO
   (ID_PERIODO, ID_TIPO_PERIODO, DESC_PERIODO, DESC_PERIODO_VISUALIZZATA, DT_INIZIO_VALIDITA, ID_UTENTE_INS)
 Values
   (5, 1, '2011', '2011', TO_DATE('03/22/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 0);
Insert into PBANDI_T_PERIODO
   (ID_PERIODO, ID_TIPO_PERIODO, DESC_PERIODO, DESC_PERIODO_VISUALIZZATA, DT_INIZIO_VALIDITA, ID_UTENTE_INS)
 Values
   (6, 1, '2012', '2012', TO_DATE('03/22/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 0);
Insert into PBANDI_T_PERIODO
   (ID_PERIODO, ID_TIPO_PERIODO, DESC_PERIODO, DESC_PERIODO_VISUALIZZATA, DT_INIZIO_VALIDITA, ID_UTENTE_INS)
 Values
   (7, 1, '2013', '2013', TO_DATE('03/22/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 0);
Insert into PBANDI_T_PERIODO
   (ID_PERIODO, ID_TIPO_PERIODO, DESC_PERIODO, DESC_PERIODO_VISUALIZZATA, DT_INIZIO_VALIDITA, ID_UTENTE_INS)
 Values
   (8, 1, '2014', '2014', TO_DATE('03/22/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 0);
Insert into PBANDI_T_PERIODO
   (ID_PERIODO, ID_TIPO_PERIODO, DESC_PERIODO, DESC_PERIODO_VISUALIZZATA, DT_INIZIO_VALIDITA, ID_UTENTE_INS)
 Values
   (9, 1, '2015', '2015', TO_DATE('03/22/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 0);
Insert into PBANDI_T_PERIODO
   (ID_PERIODO, ID_TIPO_PERIODO, DESC_PERIODO, DESC_PERIODO_VISUALIZZATA, DT_INIZIO_VALIDITA, ID_UTENTE_INS)
 Values
   (10, 1, '2016', '2016', TO_DATE('03/22/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 0);
Insert into PBANDI_T_PERIODO
   (ID_PERIODO, ID_TIPO_PERIODO, DESC_PERIODO, DESC_PERIODO_VISUALIZZATA, DT_INIZIO_VALIDITA, ID_UTENTE_INS)
 Values
   (11, 2, '9999', '2007-2013', TO_DATE('03/22/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 0);
COMMIT;

insert into pbandi_d_norma
(id_norma,cod_igrue_T26,tipologia_norma,desc_norma,
 numero_norma,anno_norma,dt_inizio_validita) 
values
(1,444,'L','Attivazione delle risorse preordinate dalla legge finanziaria per l''anno 1998 al fine di realizzare interventi nelle aree depresse. Istituzione di un fondo rotativo per il finanziamento dei programmi di promozione imprenditoriale nelle aree depresse.',
 208,1998,to_date('01/01/2010','dd/mm/yyyy'));

insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(1  ,1    ,'QO','Quota Ordinaria',                                                     		275  ,1996,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(2  ,2    ,'AD','Assegnazione delle risorse a carico delle aree depresse',                70   ,1998,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(3  ,3    ,'AD','Assegnazione delle risorse a carico delle aree depresse',                132  ,1998,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(4  ,4    ,'AD','Patti Territoriali generalisti',                                         4    ,1999,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(5  ,5    ,'AD','Economie Patti Territoriali generalisti',                                4    ,1999,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(6  ,6    ,'AD','Assegnazione delle risorse a carico delle aree depresse',                4    ,1999,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(7  ,7    ,'CO','Completamenti',                                                          52   ,1999,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(8  ,8    ,'SF','Studi di Fattibilita''',                                                 106  ,1999,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(9  ,9    ,'SF','Studi di Fattibilita''',                                                 135  ,1999,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(10 ,10   ,'PR','Punto 1.1 - Accantonamenti',                                             142  ,1999,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(11 ,11   ,'QO','Quota Ordinaria',                                                        142  ,1999,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(12 ,12   ,'QO','Quota Ordinaria',                                                        14   ,2000,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(13 ,13   ,'QC','Quota Compensativa',                                                     84   ,2000,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(14 ,14   ,'QO','Quota Ordinaria',                                                        84   ,2000,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(15 ,15   ,'TE','Quota Terremoto',                                                        84   ,2000,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(16 ,16   ,'AD','Assegnazione delle risorse a carico delle aree depresse',                102  ,2000,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(17 ,17   ,'AD','Economie Patti Territoriali nei settori agricoltura e pesca',            138  ,2000,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(18 ,18   ,'AD','Patti Territoriali nei settori agricoltura e pesca',                     138  ,2000,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(19 ,19   ,'AD','Quota Contratti di Programma',                                           138  ,2000,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(20 ,20   ,'AD','Patti territoriali generalisti',                                         138  ,2000,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(21 ,21   ,'QC','Quota Compensativa',                                                     138  ,2000,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(22 ,22   ,'QO','Quota Ordinaria',                                                        138  ,2000,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(23 ,23   ,'RS','Quota ricerca, formazione e politiche del lavoro',                       138  ,2000,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(24 ,24   ,'TE','Quota Terremoto',                                                        138  ,2000,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(25 ,25   ,'AD','Assegnazione delle risorse a carico delle aree depresse',                151  ,2000,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(26 ,26   ,'AD','Assegnazione delle risorse a carico delle aree depresse',                154  ,2000,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(27 ,27   ,'AD','Assegnazione delle risorse a carico delle aree depresse',                39   ,2001,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(28 ,28   ,'AD','Assegnazione delle risorse a carico delle aree depresse',                30   ,2002,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(29 ,29   ,'AC','Quota D.1.b - Amministrazioni Centrali',                                 36   ,2002,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(30 ,30   ,'AD','Altre destinazioni',                                                     36   ,2002,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(31 ,31   ,'QO','Quota E4 - Regioni Mezzogiorno',                                         36   ,2002,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(32 ,32   ,'QO','Quota E2 - Regioni Centro Nord',                                         36   ,2002,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(33 ,33   ,'RS','Quota D2 - Ricerca e formazione Centro Nord',                            36   ,2002,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(34 ,34   ,'TE','Quota E.1 - Terremoto Marche e Umbria del 1997',                         36   ,2002,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(35 ,35   ,'AD','Assegnazione delle risorse a carico delle aree depresse',                48   ,2002,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(36 ,36   ,'AD','Assegnazione delle risorse a carico delle aree depresse',                50   ,2002,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(37 ,37   ,'AD','Programma Nazionale Irriguo.',                                           133  ,2002,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(38 ,38   ,'AD','Quota F.2 - Contratti di programma',                                     16   ,2003,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(39 ,39   ,'CL','F.2.1 Contratti di localizzazione',                                      16   ,2003,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(40 ,40   ,'AC','Quota B (punto 1.1)',                                                    17   ,2003,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(41 ,41   ,'AC','Quota E.2.5.a - Ministero Beni Culturali',                               17   ,2003,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(42 ,42   ,'AC','Quota E.3.1 - Risorse gestite dal MAE per programmi di cooperazione',    17   ,2003,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(43 ,43   ,'AD','Altre destinazioni',                                                     17   ,2003,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(44 ,44   ,'CA','Quota F.2.4 - Eventi alluvionali Nov./Dic. 2002',                        17   ,2003,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(45 ,45   ,'CA','Quota F.2.3 - Eventi vulcanici 2002 - Area Etnea',                       17   ,2003,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(46 ,46   ,'PM','Quota C - Progetto Monitoraggio',                                        17   ,2003,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(47 ,47   ,'QO','Quota F.4 - Regioni Mezzogiorno',                                        17   ,2003,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(48 ,48   ,'QO','Quota F.3 - Regioni Centro Nord',                                        17   ,2003,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(49 ,49   ,'RS','Quota E.1 - Ricerca e Societa'' dell''Informazione Mezzogiorno',         17   ,2003,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(50 ,50   ,'RS','Quota F.1.2 - Ricerca Centro Nord',                                      17   ,2003,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(51 ,51   ,'RS','Quota F.1.3 - Soc. dell''Informazione Centro Nord',                      17   ,2003,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(52 ,52   ,'TE','Quota F.2.2.b - Terremoto Umbria del 1997',                              17   ,2003,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(53 ,53   ,'TE','Quota F.2.2.a - Terremoto Marche del 1997',                              17   ,2003,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(54 ,54   ,'TE','Quota F.2.1 - Terremoto Molise del 2002',                                17   ,2003,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(55 ,55   ,'AC','Fondi MAP per patti territoriali',                                       26   ,2003,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(56 ,56   ,'AD','P.2 Contratti di Programma-Risorse al netto ex  Del.16 e 17/2003',       34   ,2003,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(57 ,57   ,'AD','Primo Programma Opere strategiche - Fin Autostrada Messina-Palermo',     68   ,2003,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(58 ,58   ,'AC','Quota Amm.ni Centrali (punto 1.1 Del. CIPE 17/03)',                      83   ,2003,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(59 ,59   ,'CA','Quota F.3.1 CIPE Trasimeno',                                             19   ,2004,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(60 ,60   ,'CC','Quota F.3 Programma accelerazione Regioni Centro-Nord',                  19   ,2004,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(61 ,61   ,'CC','Quota F.2.1 Accelerazione programma infrastrutture strategiche',         19   ,2004,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(62 ,62   ,'CC','Quota E.4 RFI',                                                          19   ,2004,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(63 ,63   ,'SI','Quota F.4 Societa'' dell''Informazione',                                 19   ,2004,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(64 ,64   ,'AC','Quota D.3.3/D.3.4/D.3.5/D.3.6 Risorse per altri interventi Amm.ni',      20   ,2004,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(65 ,65   ,'AC','Quota D.2. - Risorse gestite da AACC per azioni di sistema',             20   ,2004,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(66 ,66   ,'AC','Quota D.2.6 - Ministero Politiche Agricole e Forestali',                 20   ,2004,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(67 ,67   ,'AU','Quota E.2 Risorse Regioni Mezzogiorno Interventi Aree Urbane',           20   ,2004,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(68 ,68   ,'CA','Quota C.2 Eventi Vulcani Area Etnea (2002)',                             20   ,2004,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(69 ,69   ,'CL','Quota E.4.1 Riserva infrastrutture complementari contratti localizz.',   20   ,2004,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(70 ,70   ,'PZ','Quota B.2  progetti di qualita''',                                       20   ,2004,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(71 ,71   ,'QO','Quota E.3 - Regioni Centro Nord',                                        20   ,2004,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(72 ,72   ,'QO','Quota E.4 Risorse Regioni Mezzogiorno',                                  20   ,2004,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(73 ,73   ,'RI','Quota D.1.1 AACC Ricerca',                                               20   ,2004,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(74 ,74   ,'RS','Quota E.1.2/E.1.3 Ricerca e Societa'' dell''Inform. Regioni Centro Nord',20   ,2004,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(75 ,75   ,'SI','Quota D.1.2 AACC Societa'' dell''Informazione',                          20   ,2004,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(76 ,76   ,'TE','Quota C.3b Completamenti Terremoto Umbria(Sisma 1997)',                  20   ,2004,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(77 ,77   ,'TE','Quota C.3a Completamenti Terremoto Marche(Sisma 1997)',                  20   ,2004,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(78 ,78   ,'TE','Quota C.1 Terremoto Molise (2002)',                                      20   ,2004,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(79 ,79   ,'CC','Quota 2.2 - Accantonamento per interv. infrast. regioni centro-nord ',   34   ,2005,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(80 ,80   ,'CC','Quota R 1 - Ulteriore finanz.- Programma accelerazione- L.F 2004',       34   ,2005,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(81 ,81   ,'AC','Quota C.3 - Altri interventi pilota o sperimentali AACC',                35   ,2005,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(82 ,82   ,'AC','Quota C.2 - Risorse gestite da AACC per azioni di sistema',              35   ,2005,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(83 ,83   ,'AU','Quota D.3 - Aree urbane - Regioni Centro-Nord',                          35   ,2005,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(84 ,84   ,'AU','Quota D.2 - Aree urbane - Regioni Mezzogiorno',                          35   ,2005,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(85 ,85   ,'QO','Quota D.5 - Regioni Mezzogiorno',                                        35   ,2005,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(86 ,86   ,'QO','Quota D.4 - Regioni Centro-Nord',                                        35   ,2005,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(87 ,87   ,'RS','Quota D.1 - Ricerca e societa'' informazione - Regioni Centro-Nord',     35   ,2005,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(88 ,88   ,'RS','Quota C.1 - Programmi AACC nel Mezzogiorno in ricerca e società inf.',   35   ,2005,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(89 ,89   ,'TE','Quota B.1 - Terremoto Molise',                                           35   ,2005,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(90 ,90   ,'AD','Secondo riparto FAS 2005',                                               1    ,2006,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(91 ,91   ,'AC','Quota O.1 - Progr. Calabria (Min Interno) - Sicurezza e ambiente',       2    ,2006,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(92 ,92   ,'AC','Quota B.2 - Risorse gestite da AACC per azioni di sistema',              3    ,2006,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(93 ,93   ,'AC','Quota B.3 - Altri interventi pilota o strategici',                       3    ,2006,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(94 ,94   ,'QO','Quota C.3 - Regioni Mezzogiorno',                                        3    ,2006,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(95 ,95   ,'QO','Quota C.2 - Regioni Centro-Nord',                                        3    ,2006,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(96 ,96   ,'RS','Quota B.1.1 - Ricerca - Regioni Mezzogiorno',                            3    ,2006,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(97 ,97   ,'RS','Quota C.1 - Ricerca e societa'' dell''informazione Centro - Nord',       3    ,2006,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(98 ,98   ,'RS','Quota B.1.2 - Societa'' dell''informazione - Regioni Mezzogiorno',       3    ,2006,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(99 ,99   ,'D3','Assegnazione FAS Economie Sviluppo Italia Turismo',                      26   ,2006,to_date('01/01/2010','dd/mm/yyyy'));
insert into pbandi_d_delibera(id_delibera,cod_igrue_T27,tipo_quota,desc_quota,numero,anno,dt_inizio_validita) values(100,99999,null,null,                                                                     99999,9999,to_date('01/01/2010','dd/mm/yyyy'));

update PBANDI_D_TIPO_ENTE_COMPETENZA
set DESC_TIPO_ENTE_COMPETENZA = 'Direzione Regionale'
where DESC_BREVE_TIPO_ENTE_COMPETENZ = 'ADG';

SET DEFINE OFF;
Insert into PBANDI_D_TIPO_ENTE_COMPETENZA
   (ID_TIPO_ENTE_COMPETENZA, DESC_BREVE_TIPO_ENTE_COMPETENZ, DESC_TIPO_ENTE_COMPETENZA, DT_INIZIO_VALIDITA, COD_IGRUE_T51)
 Values
   (8, 'REG', 'Regione', TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 1);

update PBANDI_T_ENTE_COMPETENZA
set ID_TIPO_ENTE_COMPETENZA = 8
where DESC_BREVE_ENTE = 'REG';

insert into pbandi_r_sogg_prog_ruolo_ente
(progr_soggetto_progetto,id_ruolo_ente_competenza,id_utente_ins)
(select progr_soggetto_progetto,3,id_utente_ins
 from   PBANDI_R_SOGGETTO_PROGETTO
 where  ID_TIPO_ANAGRAFICA = 1
 and    ID_TIPO_BENEFICIARIO != 4);
 
 insert into pbandi_r_sogg_prog_ruolo_ente
(progr_soggetto_progetto,id_ruolo_ente_competenza,id_utente_ins)
(select progr_soggetto_progetto,4,id_utente_ins
 from   PBANDI_R_SOGGETTO_PROGETTO
 where  ID_TIPO_ANAGRAFICA = 1
 and    ID_TIPO_BENEFICIARIO != 4);
 
insert into pbandi_r_sogg_prog_ruolo_ente
(progr_soggetto_progetto,id_ruolo_ente_competenza,id_utente_ins)
(select progr_soggetto_progetto,5,id_utente_ins
 from   PBANDI_R_SOGGETTO_PROGETTO
 where  ID_TIPO_ANAGRAFICA = 1
 and    ID_TIPO_BENEFICIARIO != 4); 

COMMIT;

update PBANDI_T_PROGETTO p
set    p.ID_STRUMENTO_ATTUATIVO = 2
where  p.ID_PROGETTO IN (SELECT dpm.ID_PROGETTO
						 FROM   PBANDI_T_DATI_PROGETTO_MONIT dpm
						 WHERE  dpm.FLAG_PROGETTO_DI_COMPLETAMENTO = 'S');

update PBANDI_T_PROGETTO p
set    p.ID_STRUMENTO_ATTUATIVO = 1						 
WHERE  p.ID_STRUMENTO_ATTUATIVO IS NULL;

COMMIT;

ALTER TABLE PBANDI_T_PROGETTO
MODIFY ID_STRUMENTO_ATTUATIVO NOT NULL;

SET DEFINE OFF;
Insert into PBANDI_D_TIPO_SOGG_CORRELATO
   (ID_TIPO_SOGGETTO_CORRELATO, DESC_BREVE_TIPO_SOGG_CORRELATO, DESC_TIPO_SOGGETTO_CORRELATO, DT_INIZIO_VALIDITA, FLAG_VISIBILE)
 Values
   (15, 'Impresa Appaltatrice', 'Impresa Appaltatrice', TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 'N');
Insert into PBANDI_D_TIPO_SOGG_CORRELATO
   (ID_TIPO_SOGGETTO_CORRELATO, DESC_BREVE_TIPO_SOGG_CORRELATO, DESC_TIPO_SOGGETTO_CORRELATO, DT_INIZIO_VALIDITA, FLAG_VISIBILE)
 Values
   (17, 'Intermediario', 'Intermediario', TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 'N');   
COMMIT;

declare
  cursor curSd IS select sd.PROGR_SOGGETTO_DOMANDA,sd.ID_SOGGETTO,decode(TS.DESC_BREVE_TIPO_SOGGETTO,'EG',17,16) TIPO_ANA,
                         SD.ID_DOMANDA,SD.ID_UTENTE_INS,SD.DT_INIZIO_VALIDITA
                  from   pbandi_r_soggetto_domanda sd,pbandi_t_soggetto s,pbandi_d_tipo_soggetto ts,
                         PBANDI_D_TIPO_ANAGRAFICA ta
                  where  sd.id_tipo_anagrafica         = TA.ID_TIPO_ANAGRAFICA
                  and    TA.DESC_BREVE_TIPO_ANAGRAFICA = 'INTERMEDIARIO'
                  and    SD.ID_SOGGETTO                = S.ID_SOGGETTO
                  and    S.ID_TIPO_SOGGETTO            = TS.ID_TIPO_SOGGETTO;
  
  nIdSoggBene              PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE;  
  nProgrSoggettiCorrelati  PBANDI_R_SOGGETTI_CORRELATI.PROGR_SOGGETTI_CORRELATI%TYPE;
  nCont                    PLS_INTEGER; 
begin
  for recSd in curSd loop
    update pbandi_r_soggetto_domanda
    set    ID_TIPO_ANAGRAFICA     = recSd.TIPO_ANA
    where  PROGR_SOGGETTO_DOMANDA = recSd.PROGR_SOGGETTO_DOMANDA;
    
    SELECT ID_SOGGETTO
    INTO   nIdSoggBene
    FROM   pbandi_r_soggetto_domanda
    WHERE  ID_DOMANDA            = recSd.ID_DOMANDA
    AND    ID_TIPO_ANAGRAFICA    = 1
    AND    ID_TIPO_BENEFICIARIO != 4; 
    
    BEGIN
      SELECT PROGR_SOGGETTI_CORRELATI
      INTO   nProgrSoggettiCorrelati
      FROM   PBANDI_R_SOGGETTI_CORRELATI
      WHERE  ID_TIPO_SOGGETTO_CORRELATO = 17
      AND    ID_SOGGETTO                = recSd.ID_SOGGETTO
      AND    ID_SOGGETTO_ENTE_GIURIDICO = nIdSoggBene;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        INSERT INTO PBANDI_R_SOGGETTI_CORRELATI
        (ID_TIPO_SOGGETTO_CORRELATO, ID_UTENTE_INS, ID_SOGGETTO, ID_SOGGETTO_ENTE_GIURIDICO, 
         DT_INIZIO_VALIDITA, PROGR_SOGGETTI_CORRELATI)
        VALUES
        (17,recSd.ID_UTENTE_INS,recSd.ID_SOGGETTO,nIdSoggBene,
         recSd.DT_INIZIO_VALIDITA,SEQ_PBANDI_R_SOGG_CORRELATI.NEXTVAL)
        RETURNING PROGR_SOGGETTI_CORRELATI INTO nProgrSoggettiCorrelati;
    END;
    
    SELECT COUNT(*)
    INTO   nCont
    FROM   PBANDI_R_SOGG_DOM_SOGG_CORREL
    WHERE  PROGR_SOGGETTO_DOMANDA   = recSd.PROGR_SOGGETTO_DOMANDA
    AND    PROGR_SOGGETTI_CORRELATI = nProgrSoggettiCorrelati;
    
    IF nCont = 0 THEN
      INSERT INTO PBANDI_R_SOGG_DOM_SOGG_CORREL
      (PROGR_SOGGETTO_DOMANDA, PROGR_SOGGETTI_CORRELATI, ID_UTENTE_INS)
      VALUES
      (recSd.PROGR_SOGGETTO_DOMANDA,nProgrSoggettiCorrelati,recSd.ID_UTENTE_INS);
    END IF;
  END LOOP;
END;
/ 
      
declare
  cursor curSd IS select sp.PROGR_SOGGETTO_PROGETTO,sp.ID_SOGGETTO,decode(TS.DESC_BREVE_TIPO_SOGGETTO,'EG',17,16) TIPO_ANA,
                         Sp.ID_PROGETTO,SP.ID_UTENTE_INS,SP.DT_INIZIO_VALIDITA
                  from   pbandi_r_soggetto_progetto sp,pbandi_t_soggetto s,pbandi_d_tipo_soggetto ts,
                         PBANDI_D_TIPO_ANAGRAFICA ta
                  where  sp.id_tipo_anagrafica         = TA.ID_TIPO_ANAGRAFICA
                  and    TA.DESC_BREVE_TIPO_ANAGRAFICA = 'INTERMEDIARIO'
                  and    Sp.ID_SOGGETTO                = S.ID_SOGGETTO
                  and    S.ID_TIPO_SOGGETTO            = TS.ID_TIPO_SOGGETTO;
  
  nIdSoggBene              PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE;  
  nProgrSoggettiCorrelati  PBANDI_R_SOGGETTI_CORRELATI.PROGR_SOGGETTI_CORRELATI%TYPE;
  nCont                    PLS_INTEGER; 
begin
  for recSd in curSd loop
    update pbandi_r_soggetto_progetto
    set    ID_TIPO_ANAGRAFICA      = recSd.TIPO_ANA
    where  PROGR_SOGGETTO_PROGETTO = recSd.PROGR_SOGGETTO_PROGETTO;
    
    SELECT ID_SOGGETTO
    INTO   nIdSoggBene
    FROM   pbandi_r_soggetto_PROGETTO
    WHERE  ID_PROGETTO            = recSd.ID_PROGETTO
    AND    ID_TIPO_ANAGRAFICA    = 1
    AND    ID_TIPO_BENEFICIARIO != 4; 
    
    BEGIN
      SELECT PROGR_SOGGETTI_CORRELATI
      INTO   nProgrSoggettiCorrelati
      FROM   PBANDI_R_SOGGETTI_CORRELATI
      WHERE  ID_TIPO_SOGGETTO_CORRELATO = 17
      AND    ID_SOGGETTO                = recSd.ID_SOGGETTO
      AND    ID_SOGGETTO_ENTE_GIURIDICO = nIdSoggBene;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        INSERT INTO PBANDI_R_SOGGETTI_CORRELATI
        (ID_TIPO_SOGGETTO_CORRELATO, ID_UTENTE_INS, ID_SOGGETTO, ID_SOGGETTO_ENTE_GIURIDICO, 
         DT_INIZIO_VALIDITA, PROGR_SOGGETTI_CORRELATI)
        VALUES
        (17,recSd.ID_UTENTE_INS,recSd.ID_SOGGETTO,nIdSoggBene,
         recSd.DT_INIZIO_VALIDITA,SEQ_PBANDI_R_SOGG_CORRELATI.NEXTVAL)
        RETURNING PROGR_SOGGETTI_CORRELATI INTO nProgrSoggettiCorrelati;
    END;
    
    SELECT COUNT(*)
    INTO   nCont
    FROM   PBANDI_R_SOGG_PROG_SOGG_CORREL
    WHERE  PROGR_SOGGETTO_PROGETTO   = recSd.PROGR_SOGGETTO_PROGETTO
    AND    PROGR_SOGGETTI_CORRELATI = nProgrSoggettiCorrelati;
    
    IF nCont = 0 THEN
      INSERT INTO PBANDI_R_SOGG_PROG_SOGG_CORREL
      (PROGR_SOGGETTO_PROGETTO, PROGR_SOGGETTI_CORRELATI, ID_UTENTE_INS)
      VALUES
      (recSd.PROGR_SOGGETTO_PROGETTO,nProgrSoggettiCorrelati,recSd.ID_UTENTE_INS);
    END IF;
  END LOOP;
END;
/

delete PBANDI_D_TIPO_ANAGRAFICA 
where  DESC_BREVE_TIPO_ANAGRAFICA = 'INTERMEDIARIO';

update pbandi_t_progetto
set id_tipo_strumento_progr = 1;

commit;

alter TABLE pbandi_t_progetto
modify id_tipo_strumento_progr  not null;
 
SET DEFINE OFF;
Insert into PBANDI_D_TIPO_ANAGRAFICA
   (ID_TIPO_ANAGRAFICA, DESC_BREVE_TIPO_ANAGRAFICA, DESC_TIPO_ANAGRAFICA, DT_INIZIO_VALIDITA)
 Values
   (20, 'CREATOR', 'Operatore abilitato al caricamento progetti ', TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
COMMIT;

UPDATE PBANDI_R_PROG_SOGG_FINANZIAT
SET    ID_PERIODO    = (SELECT ID_PERIODO FROM PBANDI_T_PERIODO WHERE DESC_PERIODO_VISUALIZZATA = '2007-2013'),
       ESTREMI_PROVV = 'Estremi non disponibili';

declare
  cursor curSoggFin IS SELECT *
					   FROM   PBANDI_R_PROG_SOGG_FINANZIAT
					   WHERE  ID_SOGGETTO_FINANZIATORE = 13;
  
  nIdSoggetto  PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE;
begin
  for recSoggFin in curSoggFin loop
    SELECT DISTINCT ID_SOGGETTO
    INTO   nIdSoggetto 
    FROM   PBANDI_R_SOGGETTO_PROGETTO 
    WHERE  ID_PROGETTO           = recSoggFin.Id_Progetto 
    AND    ID_TIPO_ANAGRAFICA    = 1
    AND    ID_TIPO_BENEFICIARIO != 4
    AND    DT_FINE_VALIDITA      IS NULL;
    
	update PBANDI_R_PROG_SOGG_FINANZIAT
	set    ID_SOGGETTO 			     = nIdSoggetto
	where  PROGR_PROG_SOGG_FINANZIAT = recSoggFin.PROGR_PROG_SOGG_FINANZIAT;
  end loop;
end;  
/

commit;

alter table PBANDI_R_PROG_SOGG_FINANZIAT
modify id_periodo not null;
   	
insert into PBANDI_D_ATENEO(ID_ATENEO,DESC_BREVE_ATENEO,DESC_ATENEO,DT_INIZIO_VALIDITA,CODICE_FISCALE_ATENEO,id_forma_giuridica,id_attivita_ateco) values(1,'Universita'' Torino','Universita'' degli Studi di Torino',to_date('01/01/2009','dd/mm/yyyy'),'80088230018',50,2829);
insert into PBANDI_D_ATENEO(ID_ATENEO,DESC_BREVE_ATENEO,DESC_ATENEO,DT_INIZIO_VALIDITA,CODICE_FISCALE_ATENEO,id_forma_giuridica,id_attivita_ateco) values(2,'Politecnico','Politecnico di Torino',to_date('01/01/2009','dd/mm/yyyy'),'00518460019',50,2829);
insert into PBANDI_D_ATENEO(ID_ATENEO,DESC_BREVE_ATENEO,DESC_ATENEO,DT_INIZIO_VALIDITA,CODICE_FISCALE_ATENEO,id_forma_giuridica,id_attivita_ateco) values(3,'Piemonte Orientale','Universita'' del Piemonte Orientale',to_date('01/01/2009','dd/mm/yyyy'),'94021400026',50,2829);
insert into PBANDI_D_ATENEO(ID_ATENEO,DESC_BREVE_ATENEO,DESC_ATENEO,DT_INIZIO_VALIDITA,CODICE_FISCALE_ATENEO,id_forma_giuridica,id_attivita_ateco) values(4,'Scien. Gastronomiche','Universita'' degli Studi di Scienze Gastronomiche',to_date('01/01/2009','dd/mm/yyyy'),'03079180042',50,2829);

insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(1, 'DAUIN',    'DAUIN - Automatica e Informatica',                                                                              '00518460019-DIP22',to_date('01/01/2009','dd/mm/yyyy'),2);     
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(2, 'DELEN',    'DELEN - Elettronica',                                                                                                        '00518460019-DIP7',to_date('01/01/2009','dd/mm/yyyy'),2);    
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(3, 'DELET',    'DELET - Ingegneria Elettrica',                                                                                      '00518460019-DIP23',to_date('01/01/2009','dd/mm/yyyy'),2);    
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(4, 'DENER',    'DENER - Energetica',                                                                                                          '00518460019-DIP11',to_date('01/01/2009','dd/mm/yyyy'),2);    
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(5, 'DIASP',    'DIASP - Ingegneria Aeronautica e Spaziale',                                                            '00518460019-DIP18',to_date('01/01/2009','dd/mm/yyyy'),2);    
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(6, 'DICAS',    'DICAS - Casa-Citta''',                                                                                                        '00518460019-DIP24',to_date('01/01/2009','dd/mm/yyyy'),2);      
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(7, 'DIFIS',    'DIFIS - Fisica',                                                                                                                  '00518460019-DIP25',to_date('01/01/2009','dd/mm/yyyy'),2);    
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(8, 'DIMAT',    'DIMAT - Matematica',                                                                                                          '00518460019-DIP26',to_date('01/01/2009','dd/mm/yyyy'),2);    
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(9, 'DIMEC',    'DIMEC - Meccanica',                                                                                                            '00518460019-DIP9',to_date('01/01/2009','dd/mm/yyyy'),2);    
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(10,'DINSE',    'DINSE - Scienze e Tecniche per i Processi di Insediamento',                            '00518460019-DIP27',to_date('01/01/2009','dd/mm/yyyy'),2);    
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(11,'DIPRADI','DIPRADI - Progettazione Architettonica e Disegno Industriale',                      '00518460019-DIP28',to_date('01/01/2009','dd/mm/yyyy'),2);    
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(12,'DISET',    'DISET - Ingegneria dei Sistemi Edilizi e Territoriali',                                    '00518460019-DIP29',to_date('01/01/2009','dd/mm/yyyy'),2);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(13,'DISMIC', 'DISMIC - Scienza dei Materiali e Ingegneria Chimica',                                        '00518460019-DIP8',to_date('01/01/2009','dd/mm/yyyy'),2); 
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(14,'DISPEA', 'DISPEA - Sistemi di Produzione ed Economia dell''Azienda',                                '00518460019-DIP10',to_date('01/01/2009','dd/mm/yyyy'),2); 
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(15,'DISTR',    'DISTR - Ingegneria Strutturale e Geotecnica',                                                        '00518460019-DIP30',to_date('01/01/2009','dd/mm/yyyy'),2);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(16,'DITAG',    'DITAG - Ingegneria del Territorio, dell''Ambiente e delle Geotecnologie','00518460019-DIP31',to_date('01/01/2009','dd/mm/yyyy'),2); 
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(17,'DITER',    'DITER - Interateneo Territorio',                                                                                  '00518460019-DIP32',to_date('01/01/2009','dd/mm/yyyy'),2);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(18,'DITIC',    'DITIC - Idraulica, Trasporti ed Infrastrutture Civili',                                    '00518460019-DIP17',to_date('01/01/2009','dd/mm/yyyy'),2);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(19,'CESAL',    'CESAL - Centro Servizi Alessandria',                                                                          '00518460019-DIP19',to_date('01/01/2009','dd/mm/yyyy'),2);

insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(20,'DI',			'DI - Informatica',																										  				  '94021400026-DIP35',to_date('01/01/2009','dd/mm/yyyy'),3); 	
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(21,'MCS',		'MCS - Medicina Clinica e Sperimentale',																					'94021400026-DIP36',to_date('01/01/2009','dd/mm/yyyy'),3);	
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(22,'POLIS',	'POLIS - Politiche Pubbliche e Scelte Collettive',															  '94021400026-DIP37',to_date('01/01/2009','dd/mm/yyyy'),3);	
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(23,'DRS',		'DRS - Ricerca sociale',																												  '94021400026-DIP38',to_date('01/01/2009','dd/mm/yyyy'),3);	
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(24,'DiSCAFF','DiSCAFF - Scienze Chimiche Alimentari Farmaceutiche e Farmacologiche', 					'94021400026-DIP39',to_date('01/01/2009','dd/mm/yyyy'),3);	
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(25,'DiSAV',	'DiSAV - Scienze dell''ambiente e della vita',																		'94021400026-DIP1' ,to_date('01/01/2009','dd/mm/yyyy'),3);  	
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(26,'SEMeQ',	'SEMeQ - Scienze Economiche e Metodi Quantitativi',										  				  '94021400026-DIP40',to_date('01/01/2009','dd/mm/yyyy'),3);	
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(27,'DiSTA',	'DiSTA - Scienze e Tecnologie Avanzate',																				  '94021400026-DIP41',to_date('01/01/2009','dd/mm/yyyy'),3);	
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(28,'DiSGE',	'DiSGE - Scienze Giuridiche ed Economiche',														  					'94021400026-DIP42',to_date('01/01/2009','dd/mm/yyyy'),3);	
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(29,'SM',			'SM - Scienze Mediche',																								  					'94021400026-DIP43',to_date('01/01/2009','dd/mm/yyyy'),3);	
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(30,'SIT',		'SIT - Studi per l''Impresa e il Territorio',													  				  '94021400026-DIP44',to_date('01/01/2009','dd/mm/yyyy'),3);	
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(31,'ST',			'ST - Studi Umanistici',																													'94021400026-DIP45',to_date('01/01/2009','dd/mm/yyyy'),3);
        
commit;

SET DEFINE OFF;
Insert into PBANDI_C_RUOLO_DI_PROCESSO
   (ID_RUOLO_DI_PROCESSO, CODICE, DESC_RUOLO_DI_PROCESSO, ID_DEFINIZIONE_PROCESSO)
 Values
   (5, 'Richiedente', 'Ruolo di processo preposto alla creazione dei progetti attraverso la scheda progetto e dell''attività di Richiesta del conto economico in domanda', 9);
COMMIT;

SET DEFINE OFF;
Insert into PBANDI_R_RUOLO_TIPO_ANAGRAFICA
   (ID_RUOLO_DI_PROCESSO, ID_TIPO_ANAGRAFICA, ID_RUOLO_TIPO_ANAGRAFICA)
 Values
   (5, 20, 23);
COMMIT;

SET DEFINE OFF;
Insert into pbandi_d_tipo_recupero
   (ID_TIPO_recupero, DESC_BREVE_TIPO_recupero, DESC_TIPO_recupero, DT_INIZIO_VALIDITA)
 Values
   (1, 'SO', 'Soppressione', TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into pbandi_d_tipo_recupero
   (ID_TIPO_recupero, DESC_BREVE_TIPO_recupero, DESC_TIPO_recupero, DT_INIZIO_VALIDITA)
 Values
   (2, 'RE', 'Recupero', TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into pbandi_d_tipo_recupero
   (ID_TIPO_recupero, DESC_BREVE_TIPO_recupero, DESC_TIPO_recupero, DT_INIZIO_VALIDITA)
 Values
   (3, 'PR', 'PreRecupero', TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
COMMIT;

insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(32,'ASGT',		'Dip. di agronomia, selvicoltura e gestione del territorio',							'80088230018-DIP2', to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(33,'AFML',		'Dip. di anatomia, farmacologia e medicina legale',											  '80088230018-DIP3', to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(34,'DBA',		'Dip. di biologia animale e dell''uomo',																	'80088230018-DIP4', to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(35,'BIVE',		'Dip. di biologia vegetale',																							'80088230018-DIP5', to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(36,'DCAN',		'Dip. di chimica analitica',																							'80088230018-DIP6', to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(37,'CGCO',		'Dip. di chimica generale e chimica organica',														'80088230018-DIP7', to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(38,'I.F.M.', 'Dip. di chimica inorganica, fisica e dei materiali',										  '80088230018-DIP8', to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(39,'DCA',		'Dip. di colture arboree',																								'80088230018-DIP9', to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(40,'DIEC',		'Dip. di diritto dell''economia',																					'80088230018-DIP10',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(41,'DAMS',		'Dip. di discipline artistiche, musicali e dello spettacolo',						  '80088230018-DIP11',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(42,'DDGO',		'Dip. di discipline ginecologiche e ostetriche',													'80088230018-DIP12',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(43,'DDMC',		'Dip. di discipline medico-chirurgiche',																	'80088230018-DIP13',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(44,'DECM',		'Dip. di economia "S. Cognetti de Martiis"',															'80088230018-DIP14',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(45,'ECOAZ',	'Dip. di economia aziendale',																						  '80088230018-DIP15',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(46,'DEIAFA',	'Dip. di economia ed ingegneria agraria, forestale ed ambientale',				'80088230018-DIP16',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(47,'DFL',		'Dip. di Filologia Linguistica e Tradizione Classica "Augusto Rostagni"', '80088230018-DIP17',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(48,'FILO',		'Dip. di filosofia',																											'80088230018-DIP18',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(49,'DFG',		'Dip. di fisica generale',																								'80088230018-DIP19',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(50,'DFS',		'Dip. di fisica sperimentale',																						'80088230018-DIP20',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(51,'FISTEO',	'Dip. di fisica teorica',																								  '80088230018-DIP21',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(52,'FISIC',	'Dip. di fisiopatologia clinica',																				  '80088230018-DIP22',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(53,'DGBB',		'Dip. di Genetica, Biologia e Biochimica',																'80088230018-DIP23',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(54,'DI',			'Dip. di informatica',																										'80088230018-DIP24',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(55,'DM',			'Dip. di Matematica "Giuseppe Peano"',																		'80088230018-DIP25',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(56,'DMOS',		'Dip. di medicina ed oncologia sperimentale',														  '80088230018-DIP26',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(57,'DMI',		'Dip. di medicina interna',																							  '80088230018-DIP27',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(58,'DMV',		'Dip. di morfofisiologia veterinaria',																		'80088230018-DIP28',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(59,'NEURO',	'Dip. di neuroscienze',																									  '80088230018-DIP29',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(60,'ORIE',		'Dip. di orientalistica',																								  '80088230018-DIP30',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(61,'PAN',		'Dip. di patologia animale',																							'80088230018-DIP31',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(62,'PAE',		'Dip. di produzioni animali, epidemiologia ed ecologia',									'80088230018-DIP32',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(63,'PSIC',		'Dip. di psicologia',																										  '80088230018-DIP33',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(64,'SPM',		'Dip. di Sanita'' Pubblica e Microbiologia',															'80088230018-DIP34',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(65,'DSTF',		'Dip. di scienza e tecnologia del farmaco',															  '80088230018-DIP35',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(66,'SAAST',	'Dip. di scienze antropologiche, archeologiche e storico territoriali',	  '80088230018-DIP36',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(67,'DSBOU',		'Dip. di scienze biomediche ed oncologia umana',													'80088230018-DIP37',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(68,'DSCB',   'Dip. di scienze cliniche e biologiche',																	'80088230018-DIP1', to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(69,'DSL',		'Dip. di scienze del linguaggio e letterature moderne e comparate',			  '80088230018-DIP38',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(70,'DISEF',	'Dip. di scienze dell''educazione e della formazione',										'80088230018-DIP39',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(71,'DST',		'Dip. di scienze della terra',																						'80088230018-DIP40',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(72,'DSEF',		'Dip. di scienze economiche e finanziarie "G. Prato"',										'80088230018-DIP41',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(73,'DSG',		'Dip. di Scienze Giuridiche',																						  '80088230018-DIP42',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(74,'FIELE',	'Dip. di scienze letterarie e filologiche',															  '80088230018-DIP43',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(75,'DMER',		'Dip. di scienze merceologiche',																					'80088230018-DIP44',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(76,'DSMP',		'Dip. di scienze mineralogiche e petrologiche',													  '80088230018-DIP45',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(77,'IRCC',		'Dip. di scienze oncologiche',																						'80088230018-DIP46',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(78,'SPAD',		'Dip. di scienze pediatriche e dell''adolescenza',												'80088230018-DIP47',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(79,'DSS',		'Dip. di scienze sociali',																								'80088230018-DIP48',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(80,'DSZ',		'Dip. di scienze zootecniche',																						'80088230018-DIP49',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(81,'MATH',		'Dip. di Statistica e Matematica "Diego de Castro"',											'80088230018-DIP50',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(82,'DIST',		'Dip. di storia',																												  '80088230018-DIP51',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(83,'DSP',		'Dip. di studi politici',																								  '80088230018-DIP52',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(84,'TOML',	'Dip. di traumatologia, ortopedia e medicina del lavoro',								  '80088230018-DIP53',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(85,'DIVAPRA','Dip. di valorizzazione e protezione delle risorse agroforestali',				'80088230018-DIP54',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(86,'DITER',	'Dip. interateneo territorio',																						'80088230018-DIP55',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(87,'CBM',		'Scuola Universitaria per le Biotecnologie Molecolari', 									'80088230018-DIP56',to_date('01/01/2009','dd/mm/yyyy'),1);
insert into PBANDI_D_DIPARTIMENTO(ID_DIPARTIMENTO,DESC_BREVE_DIPARTIMENTO,DESC_DIPARTIMENTO,CODICE_FISCALE_DIPARTIMENTO,DT_INIZIO_VALIDITA,ID_ATENEO) values(88,'CISRA',	'Centro Interdipartimentale Servizio Ricovero Animali', 									'80088230018-DIP57',to_date('01/01/2009','dd/mm/yyyy'),1);

commit;

CREATE TABLE TMP_NAZIONE
(
  COD_ISTAT_NAZIONE  VARCHAR2(3 BYTE),
  DESC_NAZIONE       VARCHAR2(255 BYTE)
);

SET DEFINE OFF;
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('301', 'AFGHANISTAN');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('201', 'ALBANIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('401', 'ALGERIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('202', 'ANDORRA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('402', 'ANGOLA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('503', 'ANTIGUA E BARBUDA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('999', 'APOLIDE');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('302', 'ARABIA SAUDITA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('602', 'ARGENTINA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('358', 'ARMENIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('701', 'AUSTRALIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('203', 'AUSTRIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('359', 'AZERBAIGIAN');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('505', 'BAHAMAS');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('304', 'BAHREIN');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('305', 'BANGLADESH');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('506', 'BARBADOS');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('206', 'BELGIO');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('507', 'BELIZE');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('406', 'BENIN (EX DAHOMEY)');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('306', 'BHUTAN');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('256', 'BIELORUSSIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('604', 'BOLIVIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('252', 'BOSNIA-ERZEGOVINA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('408', 'BOTSWANA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('605', 'BRASILE');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('309', 'BRUNEI');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('209', 'BULGARIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('409', 'BURKINA FASO (EX ALTO VOLTA)');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('410', 'BURUNDI');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('310', 'CAMBOGIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('411', 'CAMERUN');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('509', 'CANADA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('413', 'CAPO VERDE');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('257', 'CECA, REPUBBLICA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('414', 'CENTRAFRICANA, REPUBBLICA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('415', 'CIAD');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('606', 'CILE');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('314', 'CINESE, REPUBBLICA POPOLARE');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('315', 'CIPRO');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('608', 'COLOMBIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('417', 'COMORE');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('418', 'CONGO (REPUBBLICA DEL)');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('463', 'CONGO, REPUBBLICA DEMOCRATICA DEL (EX ZAIRE)');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('320', 'COREA, REPUBBLICA (COREA DEL SUD)');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('319', 'COREA, REPUBBLICA POPOLARE DEMOCRATICA (COREA DEL NORD)');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('404', 'COSTA D''AVORIO');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('513', 'COSTA RICA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('250', 'CROAZIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('514', 'CUBA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('212', 'DANIMARCA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('515', 'DOMINICA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('516', 'DOMINICANA, REPUBBLICA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('609', 'ECUADOR');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('419', 'EGITTO');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('517', 'EL SALVADOR');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('322', 'EMIRATI ARABI UNITI');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('466', 'ERITREA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('247', 'ESTONIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('420', 'ETIOPIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('703', 'FIGI');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('323', 'FILIPPINE');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('214', 'FINLANDIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('215', 'FRANCIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('421', 'GABON');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('422', 'GAMBIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('360', 'GEORGIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('216', 'GERMANIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('423', 'GHANA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('518', 'GIAMAICA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('326', 'GIAPPONE');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('424', 'GIBUTI');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('327', 'GIORDANIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('220', 'GRECIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('519', 'GRENADA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('523', 'GUATEMALA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('425', 'GUINEA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('426', 'GUINEA BISSAU');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('427', 'GUINEA EQUATORIALE');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('612', 'GUYANA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('524', 'HAITI');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('525', 'HONDURAS');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('330', 'INDIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('331', 'INDONESIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('332', 'IRAN, REPUBBLICA ISLAMICA DEL');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('333', 'IRAQ');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('221', 'IRLANDA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('223', 'ISLANDA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('334', 'ISRAELE');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('000', 'ITALIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('356', 'KAZAKHSTAN');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('428', 'KENYA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('361', 'KIRGHIZISTAN');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('708', 'KIRIBATI');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('335', 'KUWAIT');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('336', 'LAOS');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('429', 'LESOTHO');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('248', 'LETTONIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('337', 'LIBANO');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('430', 'LIBERIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('431', 'LIBIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('225', 'LIECHTENSTEIN');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('249', 'LITUANIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('226', 'LUSSEMBURGO');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('253', 'MACEDONIA, EX REPUBBLICA JUGOSLAVA DI');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('432', 'MADAGASCAR');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('434', 'MALAWI');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('340', 'MALAYSIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('339', 'MALDIVE');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('435', 'MALI');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('227', 'MALTA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('436', 'MAROCCO');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('712', 'MARSHALL, ISOLE');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('437', 'MAURITANIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('438', 'MAURITIUS');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('527', 'MESSICO');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('713', 'MICRONESIA, STATI FEDERATI');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('254', 'MOLDOVA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('229', 'MONACO');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('341', 'MONGOLIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('270', 'MONTENEGRO');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('440', 'MOZAMBICO');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('307', 'MYANMAR (EX BIRMANIA)');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('441', 'NAMIBIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('715', 'NAURU');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('342', 'NEPAL');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('529', 'NICARAGUA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('442', 'NIGER');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('443', 'NIGERIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('231', 'NORVEGIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('719', 'NUOVA ZELANDA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('343', 'OMAN');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('232', 'PAESI BASSI');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('344', 'PAKISTAN');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('720', 'PALAU');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('530', 'PANAMA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('721', 'PAPUA NUOVA GUINEA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('614', 'PARAGUAY');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('615', 'PERU''');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('233', 'POLONIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('234', 'PORTOGALLO');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('345', 'QATAR');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('219', 'REGNO UNITO');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('235', 'ROMANIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('446', 'RUANDA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('245', 'RUSSA, FEDERAZIONE');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('534', 'SAINT KITTS E NEVIS');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('532', 'SAINT LUCIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('533', 'SAINT VINCENT E GRENADINE');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('725', 'SALOMONE, ISOLE');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('727', 'SAMOA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('236', 'SAN MARINO');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('246', 'SANTA SEDE');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('448', 'SAO TOME'' E PRINCIPE');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('450', 'SENEGAL');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('271', 'SERBIA, REPUBBLICA DI');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('449', 'SEYCHELLES');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('451', 'SIERRA LEONE');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('346', 'SINGAPORE');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('348', 'SIRIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('255', 'SLOVACCHIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('251', 'SLOVENIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('453', 'SOMALIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('239', 'SPAGNA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('311', 'SRI LANKA (EX CEYLON)');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('536', 'STATI UNITI D''AMERICA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('454', 'SUD AFRICA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('455', 'SUDAN');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('616', 'SURINAME');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('240', 'SVEZIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('241', 'SVIZZERA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('456', 'SWAZILAND');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('362', 'TAGIKISTAN');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('363', 'TAIWAN (EX FORMOSA)');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('457', 'TANZANIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('324', 'TERRITORI DELL''AUTONOMIA PALESTINESE');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('349', 'THAILANDIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('338', 'TIMOR ORIENTALE');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('458', 'TOGO');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('730', 'TONGA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('617', 'TRINIDAD E TOBAGO');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('460', 'TUNISIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('351', 'TURCHIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('364', 'TURKMENISTAN');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('731', 'TUVALU');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('243', 'UCRAINA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('461', 'UGANDA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('244', 'UNGHERIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('618', 'URUGUAY');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('357', 'UZBEKISTAN');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('732', 'VANUATU');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('619', 'VENEZUELA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('353', 'VIETNAM');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('354', 'YEMEN');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('464', 'ZAMBIA');
Insert into TMP_NAZIONE
   (COD_ISTAT_NAZIONE, DESC_NAZIONE)
 Values
   ('465', 'ZIMBABWE (EX RHODESIA)');
COMMIT;

INSERT INTO PBANDI_D_NAZIONE 
(ID_NAZIONE, COD_ISTAT_NAZIONE, DESC_NAZIONE,FLAG_APPARTENENZA_UE, DT_INIZIO_VALIDITA) 
(select SEQ_PBANDI_D_NAZIONE.nextval,COD_ISTAT_NAZIONE,DESC_NAZIONE,'N',to_date('01/01/2009','dd/mm/yyyy')
 from TMP_NAZIONE tmp
 where not exists (select 'x' from PBANDI_D_NAZIONE n where n.COD_ISTAT_NAZIONE = tmp.COD_ISTAT_NAZIONE));

commit;

update PBANDI_D_COMUNE_ESTERO
set  ID_NAZIONE = 64
where ID_NAZIONE = 227;

update PBANDI_D_COMUNE_ESTERO
set  ID_NAZIONE = 25
where ID_NAZIONE = 152;

update PBANDI_D_COMUNE_ESTERO
set  ID_NAZIONE = 173
where ID_NAZIONE = 177;

update PBANDI_D_COMUNE_ESTERO
set  ID_NAZIONE = 213
where ID_NAZIONE = 214;

update PBANDI_D_COMUNE_ESTERO
set  ID_NAZIONE = 176
where ID_NAZIONE = 236;

update PBANDI_D_COMUNE_ESTERO
set  ID_NAZIONE = 118
where ID_NAZIONE = 120;

update PBANDI_D_COMUNE_ESTERO
set  ID_NAZIONE = 10
where ID_NAZIONE = 4;

update PBANDI_T_INDIRIZZO
set  ID_NAZIONE = 64
where ID_NAZIONE = 227;

update PBANDI_T_INDIRIZZO
set  ID_NAZIONE = 25
where ID_NAZIONE = 152;

update PBANDI_T_INDIRIZZO
set  ID_NAZIONE = 173
where ID_NAZIONE = 177;

update PBANDI_T_INDIRIZZO
set  ID_NAZIONE = 213
where ID_NAZIONE = 214;

update PBANDI_T_INDIRIZZO
set  ID_NAZIONE = 176
where ID_NAZIONE = 236;

update PBANDI_T_INDIRIZZO
set  ID_NAZIONE = 118
where ID_NAZIONE = 120;

update PBANDI_T_INDIRIZZO
set  ID_NAZIONE = 10
where ID_NAZIONE = 4;

update PBANDI_T_PERSONA_FISICA
set  ID_NAZIONE_NASCITA = 64
where ID_NAZIONE_NASCITA = 227;

update PBANDI_T_PERSONA_FISICA
set  ID_NAZIONE_NASCITA = 25
where ID_NAZIONE_NASCITA = 152;

update PBANDI_T_PERSONA_FISICA
set  ID_NAZIONE_NASCITA = 173
where ID_NAZIONE_NASCITA = 177;

update PBANDI_T_PERSONA_FISICA
set  ID_NAZIONE_NASCITA = 213
where ID_NAZIONE_NASCITA = 214;

update PBANDI_T_PERSONA_FISICA
set  ID_NAZIONE_NASCITA = 176
where ID_NAZIONE_NASCITA = 236;

update PBANDI_T_PERSONA_FISICA
set  ID_NAZIONE_NASCITA = 118
where ID_NAZIONE_NASCITA = 120;

update PBANDI_T_PERSONA_FISICA
set  ID_NAZIONE_NASCITA = 10
where ID_NAZIONE_NASCITA = 4;

UPDATE pbandi_d_nazione
set COD_ISTAT_NAZIONE = '000'
where id_nazione = 118;

update PBANDI_D_COMUNE_ESTERO
set  ID_NAZIONE = 68
where ID_NAZIONE = 119;

update PBANDI_T_INDIRIZZO
set  ID_NAZIONE = 68
where ID_NAZIONE = 119;

update PBANDI_T_PERSONA_FISICA
set  ID_NAZIONE_NASCITA = 68
where ID_NAZIONE_NASCITA = 119;

update PBANDI_D_COMUNE_ESTERO
set  ID_NAZIONE = 16
where ID_NAZIONE = 17;

update PBANDI_T_INDIRIZZO
set  ID_NAZIONE = 16
where ID_NAZIONE = 17;

update PBANDI_T_PERSONA_FISICA
set  ID_NAZIONE_NASCITA = 16
where ID_NAZIONE_NASCITA = 17;

delete pbandi_d_nazione
where id_nazione in (227,152,177,214,236,120,4,119,17,17,119);

commit;

declare
  cursor curNaz is select tmp.desc_nazione,n.id_nazione
                   from   TMP_NAZIONE tmp,pbandi_d_nazione n
				   where  n.COD_ISTAT_NAZIONE = tmp.COD_ISTAT_NAZIONE
				   and    tmp.desc_nazione != n.desc_nazione;
begin
  for recNaz in curNaz loop
    update pbandi_d_nazione
    set    desc_nazione = recNaz.desc_nazione	
	where  id_nazione   = recNaz.id_nazione;
  end loop;
  commit;
exception
  when others then
    dbms_output.put_line('errore = '||sqlerrm);
    rollback;	
end;  
/

update pbandi_d_nazione n
set dt_fine_validita = to_date('01/01/2009','dd/mm/yyyy') 
where  not exists (select 'x' from TMP_NAZIONE tmp where tmp.desc_nazione = n.desc_nazione) ;

commit;

drop table TMP_NAZIONE;

INSERT INTO PBANDI_D_TIPO_SOGG_CORRELATO 
(ID_TIPO_SOGGETTO_CORRELATO, DESC_BREVE_TIPO_SOGG_CORRELATO, DESC_TIPO_SOGGETTO_CORRELATO, 
 DT_INIZIO_VALIDITA, COD_GEFO) 
VALUES 
(18,'L','Socio Lavoratore',
 to_date('01/01/2009','dd/mm/yyyy'),null);
 
 INSERT INTO PBANDI_D_TIPO_SOGG_CORRELATO 
(ID_TIPO_SOGGETTO_CORRELATO, DESC_BREVE_TIPO_SOGG_CORRELATO, DESC_TIPO_SOGGETTO_CORRELATO, 
 DT_INIZIO_VALIDITA, COD_GEFO) 
VALUES 
(19,'C','Socio Capitale',
 to_date('01/01/2009','dd/mm/yyyy'),null);
 
commit;
 
insert into pbandi_d_titolo_studio(id_titolo_studio,cod_igrue_T43,desc_titolo_studio,dt_inizio_validita) values(1,'01','Nessun titolo',                                                                 to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_titolo_studio(id_titolo_studio,cod_igrue_T43,desc_titolo_studio,dt_inizio_validita) values(2,'02','Licenza elementare',                                                            to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_titolo_studio(id_titolo_studio,cod_igrue_T43,desc_titolo_studio,dt_inizio_validita) values(3,'03','Licenza media',                                                                 to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_titolo_studio(id_titolo_studio,cod_igrue_T43,desc_titolo_studio,dt_inizio_validita) values(4,'04','Dipl. qualifica di 2-3 anni, non consente accesso univ.ta''',                   to_date('01/01/2009','dd/mm/yyyy')); 
insert into pbandi_d_titolo_studio(id_titolo_studio,cod_igrue_T43,desc_titolo_studio,dt_inizio_validita) values(5,'05','Dipl. scuola secondaria superiore 4-5 anni (accesso univ.ta'')',                to_date('01/01/2009','dd/mm/yyyy')); 
insert into pbandi_d_titolo_studio(id_titolo_studio,cod_igrue_T43,desc_titolo_studio,dt_inizio_validita) values(6,'06','Alta Form. Artistica e Musicale (AFAM) e equipollenti',                         to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_titolo_studio(id_titolo_studio,cod_igrue_T43,desc_titolo_studio,dt_inizio_validita) values(7,'07','Diploma universitario o di scuola diretta fini speciali (vecchio ordinamento)', to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_titolo_studio(id_titolo_studio,cod_igrue_T43,desc_titolo_studio,dt_inizio_validita) values(8,'08','Laurea triennale (nuovo ordinamento)',                                          to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_titolo_studio(id_titolo_studio,cod_igrue_T43,desc_titolo_studio,dt_inizio_validita) values(9,'09','Master post laurea triennale (o master di I livello)',                          to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_titolo_studio(id_titolo_studio,cod_igrue_T43,desc_titolo_studio,dt_inizio_validita) values(10,'10','Laurea specialistica(3+2) /laurea v. o./laurea a ciclo unico',                  to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_titolo_studio(id_titolo_studio,cod_igrue_T43,desc_titolo_studio,dt_inizio_validita) values(11,'11','Master post laurea specialistica (o  v. o.)',                                   to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_titolo_studio(id_titolo_studio,cod_igrue_T43,desc_titolo_studio,dt_inizio_validita) values(12,'12','Specializzazione post laurea-compresi corsi perfezionamento',                   to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_titolo_studio(id_titolo_studio,cod_igrue_T43,desc_titolo_studio,dt_inizio_validita) values(13,'13','Dottorato di ricerca',                                                          to_date('01/01/2009','dd/mm/yyyy'));

insert into pbandi_d_occupazione(id_occupazione,cod_igrue_T44,desc_occupazione,dt_inizio_validita) values(1,'1','In cerca di prima occupazione',                                                                                                                   to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_occupazione(id_occupazione,cod_igrue_T44,desc_occupazione,dt_inizio_validita) values(2,'2','Occupato (compreso chi ha un''occupazione saltuaria/atipica e chi e'' in CIG)',                                                                   to_date('01/01/2009','dd/mm/yyyy'));   
insert into pbandi_d_occupazione(id_occupazione,cod_igrue_T44,desc_occupazione,dt_inizio_validita) values(3,'3','Disoccupato alla ricerca di nuova occupazione (o iscritto alle liste di mobilita'')',                                                             to_date('01/01/2009','dd/mm/yyyy'));  
insert into pbandi_d_occupazione(id_occupazione,cod_igrue_T44,desc_occupazione,dt_inizio_validita) values(4,'4','Studente',                                                                                                                                        to_date('01/01/2009','dd/mm/yyyy'));
insert into pbandi_d_occupazione(id_occupazione,cod_igrue_T44,desc_occupazione,dt_inizio_validita) values(5,'5','Inattivo diverso da studente (casalinga/o, ritirato/a dal lavoro, inabile al lavoro, in servizio di leva o servizio civile, in altra condizione)',to_date('01/01/2009','dd/mm/yyyy'));

update PBANDI_W_CARICAMENTO_XML
set dt_inserimento = to_date('01/01/2009','dd/mm/yyyy'); 

Insert into PBANDI_C_REGOLA
(ID_REGOLA,DESC_BREVE_REGOLA,DESC_REGOLA,DT_INIZIO_VALIDITA)
values
(SEQ_PBANDI_C_REGOLA.nextval,'BR28','Specifica se i progetti di un bando_linea utilizzano l''attivita'' per i Recuperi (Istruttore)',to_date('01/01/2009','dd/mm/yyyy'));


insert into pbandi_r_bando_lin_tipo_period
(progr_bando_linea_intervento,id_tipo_periodo,id_utente_ins)
(select progr_bando_linea_intervento,id_tipo_periodo,id_utente_ins
 from   pbandi_r_bando_linea_intervent,pbandi_d_tipo_periodo
 where  desc_breve_tipo_periodo = 'R');

insert into pbandi_r_tipo_recupero_anagraf
(id_tipo_recupero,id_tipo_anagrafica) 
values
(1,11);

insert into pbandi_r_tipo_recupero_anagraf
(id_tipo_recupero,id_tipo_anagrafica) 
values
(2,2);
 
insert into pbandi_r_tipo_recupero_anagraf
(id_tipo_recupero,id_tipo_anagrafica) 
values
(2,3);

insert into pbandi_r_tipo_recupero_anagraf
(id_tipo_recupero,id_tipo_anagrafica) 
values
(2,4);

insert into pbandi_r_tipo_recupero_anagraf
(id_tipo_recupero,id_tipo_anagrafica) 
values
(2,5);

insert into pbandi_r_tipo_recupero_anagraf
(id_tipo_recupero,id_tipo_anagrafica) 
values
(3,2);
 
insert into pbandi_r_tipo_recupero_anagraf
(id_tipo_recupero,id_tipo_anagrafica) 
values
(3,3);

insert into pbandi_r_tipo_recupero_anagraf
(id_tipo_recupero,id_tipo_anagrafica) 
values
(3,4);

insert into pbandi_r_tipo_recupero_anagraf
(id_tipo_recupero,id_tipo_anagrafica) 
values
(3,5);
 
commit;

declare
cursor c1 is
SELECT distinct pbandi_t_bando.ID_settore_cpt,pbandi_t_progetto.ID_PROGETTO
  FROM pbandi_t_progetto,
       pbandi_t_domanda,
       pbandi_r_bando_linea_intervent,
       pbandi_t_bando
 WHERE pbandi_t_domanda.id_domanda = pbandi_t_progetto.id_domanda
 AND  pbandi_r_bando_linea_intervent.progr_bando_linea_intervento = pbandi_t_domanda.progr_bando_linea_intervento
 AND  pbandi_t_bando.id_bando = pbandi_r_bando_linea_intervent.id_bando;
begin
  for r1 in c1 loop
    update  pbandi_t_progetto
    set ID_settore_cpt = r1.ID_settore_cpt
    where ID_PROGETTO = r1.ID_PROGETTO;
  end loop;
  commit;
end;    
/

SET DEFINE OFF;
Insert into PBANDI_D_ERRORE_BATCH
   (CODICE_ERRORE, DESCRIZIONE)
 Values
   ('E102', 'Errore nella gestione del recupero per il flusso GEFO');
COMMIT;

update PBANDI_D_RUOLO_ENTE_COMPETENZA
set flag_visibile = 'S'
where DESC_BREVE_RUOLO_ENTE in ('ATTUATORE','DEST. FINANZIAMENTO','REALIZZATORE');

update PBANDI_D_TIPO_SOGG_CORRELATO
set flag_visibile = 'S'
where DESC_BREVE_TIPO_SOGG_CORRELATO in ('Rappr. Leg.','Socio','Referente','Altro Delegato','Titolare','Resp. vic.','Ricercatore','Responsabile','Ref. vic.','Aggregato','Amministrativo','Tecnico','Impresa Appaltatrice','Altro','L','C');

commit;

SET DEFINE OFF;
Insert into PBANDI_D_DIPARTIMENTO
   (ID_DIPARTIMENTO, DESC_BREVE_DIPARTIMENTO, DESC_DIPARTIMENTO, CODICE_FISCALE_DIPARTIMENTO, DT_INIZIO_VALIDITA, ID_ATENEO)
 Values
   (89, 'Dip. Ch. G. Org. Ap.', 'Dipartimento di chimica generale ed organica applicata', '80088230018-DIP58', TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 1);
Insert into PBANDI_D_DIPARTIMENTO
   (ID_DIPARTIMENTO, DESC_BREVE_DIPARTIMENTO, DESC_DIPARTIMENTO, CODICE_FISCALE_DIPARTIMENTO, DT_INIZIO_VALIDITA, ID_ATENEO)
 Values
   (90, 'NIS', 'UniTo (Centro Interdipartimentale di Eccellenza NIS)', '80088230018-DIP61', TO_DATE('01/01/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 1);


update PBANDI_D_DIPARTIMENTO set DESC_BREVE_DIPARTIMENTO = 'Dip. Informatica' where ID_DIPARTIMENTO = 20;
update PBANDI_D_DIPARTIMENTO set DESC_BREVE_DIPARTIMENTO = 'Dipsit' where ID_DIPARTIMENTO = 30;
update PBANDI_D_DIPARTIMENTO set DESC_BREVE_DIPARTIMENTO = 'Dip. Biologia An. Um.' where ID_DIPARTIMENTO = 34;
update PBANDI_D_DIPARTIMENTO set DESC_BREVE_DIPARTIMENTO = 'Dip. Biologia Veg.' where ID_DIPARTIMENTO = 35;
update PBANDI_D_DIPARTIMENTO set DESC_BREVE_DIPARTIMENTO = 'Dip. Colture Arboree' where ID_DIPARTIMENTO = 39;
update PBANDI_D_DIPARTIMENTO set DESC_BREVE_DIPARTIMENTO = 'Dip. Fisica Sp.' where ID_DIPARTIMENTO = 50;
update PBANDI_D_DIPARTIMENTO set DESC_BREVE_DIPARTIMENTO = 'Dip. Gen. Biol. Bioc' where ID_DIPARTIMENTO = 53;
update PBANDI_D_DIPARTIMENTO set DESC_BREVE_DIPARTIMENTO = 'Dip. Informatica' where ID_DIPARTIMENTO = 54;
update PBANDI_D_DIPARTIMENTO set DESC_BREVE_DIPARTIMENTO = 'Dip. Patologia Anim.' where ID_DIPARTIMENTO = 61;
update PBANDI_D_DIPARTIMENTO set DESC_BREVE_DIPARTIMENTO = 'Dip. Psicologia' where ID_DIPARTIMENTO = 63;
update PBANDI_D_DIPARTIMENTO set DESC_BREVE_DIPARTIMENTO = 'SCIENZE CLINICHE' where ID_DIPARTIMENTO = 68;
update PBANDI_D_DIPARTIMENTO set DESC_BREVE_DIPARTIMENTO = 'Dip. Sc.Ec. F.' where ID_DIPARTIMENTO = 72;
update PBANDI_D_DIPARTIMENTO set DESC_BREVE_DIPARTIMENTO = 'Dip. Sc. Oncologiche' where ID_DIPARTIMENTO = 77;
update PBANDI_D_DIPARTIMENTO set DESC_BREVE_DIPARTIMENTO = 'CIBM' where ID_DIPARTIMENTO = 87;
update PBANDI_D_DIPARTIMENTO set DESC_BREVE_DIPARTIMENTO = 'I.F.M.' where ID_DIPARTIMENTO = 38;
update PBANDI_D_DIPARTIMENTO set DESC_BREVE_DIPARTIMENTO = 'DSCB' where ID_DIPARTIMENTO = 68;
update PBANDI_D_DIPARTIMENTO set DESC_BREVE_DIPARTIMENTO = 'CBM' where ID_DIPARTIMENTO = 87;

COMMIT;

SET DEFINE OFF;
update PBANDI_D_DIMENSIONE_IMPRESA
set DT_FINE_VALIDITA = TO_DATE('05/20/2010 00:00:00', 'MM/DD/YYYY HH24:MI:SS')
where ID_DIMENSIONE_IMPRESA = 5;
COMMIT;

SET DEFINE OFF;
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (1, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (2, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (3, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (4, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (5, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (6, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (7, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (8, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (9, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (10, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (11, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (12, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (13, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (14, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (15, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (16, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (17, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (18, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (19, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (20, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (21, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (22, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (23, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (24, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (25, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (26, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (27, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (28, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (29, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (30, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (32, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (33, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (34, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (35, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (36, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (37, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (38, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (39, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (40, 'STATO_DOMANDA', '2');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (1, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (2, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (3, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (4, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (5, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (6, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (7, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (8, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (9, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (10, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (11, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (12, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (13, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (14, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (15, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (16, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (17, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (18, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (19, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (20, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (21, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (22, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (23, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (24, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (25, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (26, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (27, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (28, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (29, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (30, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (32, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (33, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (34, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (35, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (36, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (37, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (38, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (39, 'STATO_PROGETTO', '1');
Insert into PBANDI_W_CSP_COSTANTI
   (PROGR_BANDO_LINEA_INTERVENTO, ATTRIBUTO, VALORE)
 Values
   (40, 'STATO_PROGETTO', '1');
COMMIT;

update PBANDI_D_TIPO_SOGG_CORRELATO
set flag_visibile = 'N'
where DESC_BREVE_TIPO_SOGG_CORRELATO in ('Rappr. Leg.');

commit;

INSERT INTO PBANDI_R_LINEA_PRIORITA_QSN (ID_LINEA_DI_INTERVENTO, ID_PRIORITA_QSN, ID_UTENTE_INS) VALUES (68,1,1);
INSERT INTO PBANDI_R_LINEA_PRIORITA_QSN (ID_LINEA_DI_INTERVENTO, ID_PRIORITA_QSN, ID_UTENTE_INS) VALUES (68,2,1);
INSERT INTO PBANDI_R_LINEA_PRIORITA_QSN (ID_LINEA_DI_INTERVENTO, ID_PRIORITA_QSN, ID_UTENTE_INS) VALUES (68,4,1);
INSERT INTO PBANDI_R_LINEA_PRIORITA_QSN (ID_LINEA_DI_INTERVENTO, ID_PRIORITA_QSN, ID_UTENTE_INS) VALUES (68,7,1);
INSERT INTO PBANDI_R_LINEA_PRIORITA_QSN (ID_LINEA_DI_INTERVENTO, ID_PRIORITA_QSN, ID_UTENTE_INS) VALUES (68,8,1);
INSERT INTO PBANDI_R_LINEA_PRIORITA_QSN (ID_LINEA_DI_INTERVENTO, ID_PRIORITA_QSN, ID_UTENTE_INS) VALUES (69,3,1);
INSERT INTO PBANDI_R_LINEA_PRIORITA_QSN (ID_LINEA_DI_INTERVENTO, ID_PRIORITA_QSN, ID_UTENTE_INS) VALUES (69,6,1);
INSERT INTO PBANDI_R_LINEA_PRIORITA_QSN (ID_LINEA_DI_INTERVENTO, ID_PRIORITA_QSN, ID_UTENTE_INS) VALUES (70,3,1);
INSERT INTO PBANDI_R_LINEA_PRIORITA_QSN (ID_LINEA_DI_INTERVENTO, ID_PRIORITA_QSN, ID_UTENTE_INS) VALUES (70,4,1);
INSERT INTO PBANDI_R_LINEA_PRIORITA_QSN (ID_LINEA_DI_INTERVENTO, ID_PRIORITA_QSN, ID_UTENTE_INS) VALUES (70,5,1);
INSERT INTO PBANDI_R_LINEA_PRIORITA_QSN (ID_LINEA_DI_INTERVENTO, ID_PRIORITA_QSN, ID_UTENTE_INS) VALUES (70,6,1);
INSERT INTO PBANDI_R_LINEA_PRIORITA_QSN (ID_LINEA_DI_INTERVENTO, ID_PRIORITA_QSN, ID_UTENTE_INS) VALUES (70,7,1);
INSERT INTO PBANDI_R_LINEA_PRIORITA_QSN (ID_LINEA_DI_INTERVENTO, ID_PRIORITA_QSN, ID_UTENTE_INS) VALUES (70,8,1);
INSERT INTO PBANDI_R_LINEA_PRIORITA_QSN (ID_LINEA_DI_INTERVENTO, ID_PRIORITA_QSN, ID_UTENTE_INS) VALUES (70,9,1);
INSERT INTO PBANDI_R_LINEA_PRIORITA_QSN (ID_LINEA_DI_INTERVENTO, ID_PRIORITA_QSN, ID_UTENTE_INS) VALUES (71,1,1);
INSERT INTO PBANDI_R_LINEA_PRIORITA_QSN (ID_LINEA_DI_INTERVENTO, ID_PRIORITA_QSN, ID_UTENTE_INS) VALUES (71,4,1);
INSERT INTO PBANDI_R_LINEA_PRIORITA_QSN (ID_LINEA_DI_INTERVENTO, ID_PRIORITA_QSN, ID_UTENTE_INS) VALUES (72,10,1);
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

