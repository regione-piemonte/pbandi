CREATE OR REPLACE FUNCTION "FNC_INS_WELFARE_ABITATIVO"
RETURN NUMBER AS


vid_csp_progetto     PBANDI_T_CSP_PROGETTO.ID_CSP_PROGETTO%TYPE;
--vId_Comune           PBANDI_D_COMUNE.ID_COMUNE%TYPE;
--vid_comune_nascita   PBANDI_D_COMUNE.ID_COMUNE%TYPE;
--vid_comune_residenza PBANDI_D_COMUNE.ID_COMUNE%TYPE;
--vId_Provincia        PBANDI_T_CSP_PROG_SEDE_INTERV.Id_Provincia%TYPE;
--vId_Regione          PBANDI_T_CSP_PROG_SEDE_INTERV.Id_Regione%TYPE;
vID_CSP_SOGGETTO     PBANDI_T_CSP_SOGGETTO.ID_CSP_SOGGETTO%TYPE;

BEGIN
  -- Cursore principale
  For Rec_Ins IN
    (Select * from pbandi_w_welfare_abitativo Where DATA_CARICAMENTO is null) --- per generalizzare sarebbe possibile passare il nome della tabella come parametro?
    Loop
-------------------------
-- PBANDI_T_CSP_PROGETTO
-------------------------

      insert into PBANDI_T_CSP_PROGETTO
      (ID_CSP_PROGETTO,
        TITOLO_PROGETTO,
        DT_CONCESSIONE,
        DT_COMITATO,
        FLAG_CARDINE,
        FLAG_GENERATORE_ENTRATE,
        FLAG_LEGGE_OBBIETTIVO,
        FLAG_ALTRO_FONDO,
        STATO_PROGRAMMA,
        DT_INIZIO_VALIDITA,
        DT_ELABORAZIONE,
        ID_TIPO_OPERAZIONE,
        ID_TIPO_AIUTO,
        ID_TIPOLOGIA_CIPE,
        ID_PROGETTO,
        PROGR_BANDO_LINEA_INTERVENTO,
        ID_OBIETTIVO_SPECIF_QSN,
        ID_SETTORE_CPT,
        ID_TEMA_PRIORITARIO,
        ID_IND_RISULTATO_PROGRAM,
        ID_INDICATORE_QSN,
        ID_CATEGORIA_CIPE,
        ID_UTENTE_INS,
        ID_UTENTE_AGG,
        NUMERO_DOMANDA,
        ID_ATTIVITA_ATECO,
        FLAG_AVVIABILE,
        CUP,
        ID_STRUMENTO_ATTUATIVO,
        ID_DIMENSIONE_TERRITOR,
        ID_PROGETTO_COMPLESSO,
        ID_TIPO_STRUMENTO_PROGR,
        FLAG_DETTAGLIO_CUP,
        FLAG_BENEFICIARIO_CUP,
        DT_PRESENTAZIONE_DOMANDA,
        FLAG_RICHIESTA_CUP,
        FLAG_INVIO_MONIT,
        ID_ISTANZA_DOMANDA,
        ID_GRANDE_PROGETTO,
        ID_CLASSIFICAZIONE_RA,
        ID_OBIETTIVO_TEM
      )values
      (seq_pbandi_t_csp_progetto.nextval,     --ID_CSP_PROGETTO,
        rec_Ins.titolo_progetto,              --TITOLO_PROGETTO,
        rec_ins.dt_concessione,               --DT_CONCESSIONE,
        rec_ins.dt_concessione,               --DT_COMITATO,
        rec_ins.flag_cardine,                 --FLAG_CARDINE,
        rec_ins.flag_generatore_entrate,      --FLAG_GENERATORE_ENTRATE,
        rec_ins.flag_legge_obbiettivo,         --FLAG_LEGGE_OBBIETTIVO,--- corregere in obiettivo
        rec_ins.flag_altro_fondo,             --FLAG_ALTRO_FONDO,
        rec_ins.stato_programma,               --STATO_PROGRAMMA,
        SYSDATE,                              --DT_INIZIO_VALIDITA,
        NULL,                                 --DT_ELABORAZIONE,
        rec_ins.id_tipo_operazione,           --ID_TIPO_OPERAZIONE,
        rec_ins.id_tipo_aiuto,                --ID_TIPO_AIUTO,
        rec_ins.id_tipologia_cipe,            --ID_TIPOLOGIA_CIPE,
        NULL,                                 --ID_PROGETTO,
        rec_ins.progr_bando_linea_intervento,  --PROGR_BANDO_LINEA_INTERVENTO,
        rec_ins.id_obiettivo_specif_qsn,      --ID_OBIETTIVO_SPECIF_QSN,
        rec_ins.id_settore_cpt,               --ID_SETTORE_CPT,
        rec_ins.id_tema_prioritario,          --ID_TEMA_PRIORITARIO,
        rec_ins.id_ind_risultato_program,     --ID_IND_RISULTATO_PROGRAM,
        rec_ins.id_indicatore_qsn,            --ID_INDICATORE_QSN,
        rec_ins.id_categoria_cipe,            --ID_CATEGORIA_CIPE,
        -1,                                   --ID_UTENTE_INS,
        NULL,                                 --ID_UTENTE_AGG,
        rec_ins.numero_domanda_pbandi,        --NUMERO_DOMANDA,
        rec_ins.id_attivita_ateco,            --ID_ATTIVITA_ATECO,
        rec_ins.flag_avviabile,               --FLAG_AVVIABILE, ='S'
        rec_ins.cup,                          --CUP,
        rec_ins.id_strumento_attuativo,       --ID_STRUMENTO_ATTUATIVO,
        rec_ins.id_dimensione_territor,   --ID_DIMENSIONE_TERRITOR,
        rec_ins.id_progetto_complesso,        --ID_PROGETTO_COMPLESSO,
        rec_ins.id_tipo_strumento_progr,      --ID_TIPO_STRUMENTO_PROGR,
        rec_ins.flag_dettaglio_cup,           --FLAG_DETTAGLIO_CUP,
        rec_ins.flag_beneficiario_cup,        --FLAG_BENEFICIARIO_CUP,
        rec_ins.dt_presentazione_domanda,     --DT_PRESENTAZIONE_DOMANDA,
        rec_ins.flag_richiesta_cup,           --FLAG_RICHIESTA_CUP,
        rec_ins.flag_invio_monit,             --FLAG_INVIO_MONIT,
        rec_ins.id_istanza_domanda,           --ID_ISTANZA_DOMANDA,
        rec_ins.id_grande_progetto,           --ID_GRANDE_PROGETTO,
        rec_ins.id_classificazione_ra,        --ID_CLASSIFICAZIONE_RA,
        rec_ins.id_obiettivo_tem              --ID_OBIETTIVO_TEM
      ) returning id_csp_progetto INTO vid_csp_progetto;




-----------------------------------
-- PBANDI_T_CSP_PROG_SEDE_INTERV
-----------------------------------
/* commento in attesa di avere i dati. Dovrei avere già gli ID
BEGIN
    Select r.id_regione, p.id_provincia, c.id_comune
      Into   vid_regione, vid_provincia, vid_comune
      From   pbandi_d_comune c,
             pbandi_d_regione r,
             pbandi_d_provincia p
      Where  r.id_regione = p.id_regione and
             p.id_provincia = c.id_provincia and
             c.dt_fine_validita is NULL and
             TRIM(UPPER(c.desc_comune)) = TRIM(UPPER(rec_ins.intervento_comune));

EXCEPTION WHEN NO_DATA_FOUND THEN
     vid_regione:=NULL;
     vid_provincia:=NULL;
     vid_comune:=NULL;
END;
*/


    Insert into PBANDI_T_CSP_PROG_SEDE_INTERV
    (ID_CSP_PROG_SEDE_INTERV,
      PARTITA_IVA,
      INDIRIZZO,
      CAP,
      EMAIL,
      FAX  ,
      TELEFONO,
      ID_CSP_PROGETTO,
      ID_COMUNE_ESTERO,
      ID_COMUNE,
      ID_PROVINCIA,
      ID_REGIONE,
      ID_NAZIONE,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      DT_INIZIO_VALIDITA,
      DT_ELABORAZIONE,
      CIVICO
    )values
    (seq_pbandi_t_csp_prog_sede_int.nextval, --ID_CSP_PROG_SEDE_INTERV,
      rec_Ins.partita_iva_sede_legale,    --oppure PARTITA_IVA,
      rec_Ins.Indirizzo_si,                    --INDIRIZZO,
      rec_Ins.Cap_si,                           --CAP,
      rec_Ins.email_si,                                  --EMAIL,
      rec_Ins.fax_si,                                  --FAX  ,
      rec_Ins.telefono_si,                                  --TELEFONO,
      vid_csp_progetto,                      --ID_CSP_PROGETTO,
      NULL,                                   --ID_COMUNE_ESTERO,
      rec_Ins.ID_COMUNE_SI,                              -- ID_COMUNE - vID_COMUNE
      rec_Ins.ID_PROVINCIA_SI,                          --ID_PROVINCIA - vID_PROVINCIA
      rec_Ins.ID_REGIONE_SI,                         -- ID_REGIONE - vID_REGIONE
      rec_Ins.ID_NAZIONE_SI,                                    --ID_NAZIONE,
      -1,                                     --ID_UTENTE_INS,
      NULL,                                   --ID_UTENTE_AGG,
      SYSDATE,                                --DT_INIZIO_VALIDITA,
      NULL,                                   --DT_ELABORAZIONE,
      rec_Ins.CIVICO_SI                                   --CIVICO
    );

--------------------------------
---  PBANDI_T_CSP_SOGGETTO
--------------------------------
-- per ogni record presente sul cursore principale inserire due occorenze,
-- una per ente giuridico, una per la persona fisica

-------------------
--  Ente Giuridico
-------------------
--
-- Recupero Id_Comune
--
/*
Begin
Select c.id_comune
      Into   vid_comune_nascita
      From   pbandi_d_comune c
      Where  TRIM(UPPER(c.desc_comune)) = TRIM(UPPER(rec_ins.comune_nascita_leg_rap))
      and c.dt_fine_validita is NULL;
Exception when no_data_found then
  vid_comune_nascita  := NULL;
End;

Begin
Select c.id_comune
      Into   vid_comune_residenza
      From   pbandi_d_comune c
      Where  TRIM(UPPER(c.desc_comune)) = TRIM(UPPER(rec_ins.citta_soggetto_proponente))
      and c.dt_fine_validita is NULL;
Exception when no_data_found then
  vid_comune_residenza  := NULL;
End;
---
*/
     Insert Into PBANDI_T_CSP_SOGGETTO
     (ID_CSP_SOGGETTO,
        CODICE_FISCALE,
        DENOMINAZIONE,
        COGNOME,
        NOME,
        DT_NASCITA,
        SESSO,
        PARTITA_IVA_SEDE_LEGALE,
        INDIRIZZO_SEDE_LEGALE,
        CAP_SEDE_LEGALE,
        EMAIL_SEDE_LEGALE,
        FAX_SEDE_LEGALE,
        TELEFONO_SEDE_LEGALE,
        DT_INIZIO_VALIDITA,
        DT_ELABORAZIONE,
        ID_UTENTE_INS,
        ID_UTENTE_AGG,
        ID_FORMA_GIURIDICA,
        ID_ATTIVITA_ATECO,
        ID_DIMENSIONE_IMPRESA,
        ID_COMUNE_ESTERO_SEDE_LEGALE,
        ID_COMUNE_ITALIANO_SEDE_LEGALE,
        ID_TIPO_ANAGRAFICA,
        ID_TIPO_SOGGETTO,
        ID_TIPO_SOGGETTO_CORRELATO,
        ID_TIPO_BENEFICIARIO,
        INDIRIZZO_PF,
        CAP_PF,
        EMAIL_PF,
        FAX_PF,
        TELEFONO_PF,
        ID_COMUNE_ITALIANO_NASCITA,
        ID_COMUNE_ESTERO_NASCITA,
        ID_COMUNE_ITALIANO_RESIDENZA,
        ID_COMUNE_ESTERO_RESIDENZA,
        IBAN,
        ID_CSP_PROGETTO,
        ID_ENTE_COMPETENZA,
        ID_DIPARTIMENTO,
        ID_ATENEO,
        DT_COSTITUZIONE_AZIENDA  ,
        CIVICO  ,
        ID_SETTORE_ENTE
     )values
     (  seq_pbandi_t_csp_soggetto.nextval,      --ID_CSP_SOGGETTO,
        rec_ins.codice_fiscale,     --CODICE_FISCALE,  x
        rec_ins.denominazione,             --DENOMINAZIONE,  x
        NULL, --rec_ins.cognome_legale_rappresentante,   --COGNOME,    x
        NULL, --rec_ins.nome_legale_rappresentante,      --NOME,    x
        NULL, --rec_ins.dt_nascita_leg_rap,              --DT_NASCITA,  x
        NULL,  --rec_ins.sesso_leg_rap,                   --SESSO,    x
        rec_ins.partita_iva_sede_legale,      --PARTITA_IVA_SEDE_LEGALE,  x
        rec_ins.indirizzo_sede_legale,         --INDIRIZZO_SEDE_LEGALE,    x
        TRIM(rec_ins.cap_sede_legale),         --CAP_SEDE_LEGALE,    x
        rec_ins.email_sede_legale,         --EMAIL_SEDE_LEGALE,  x
        rec_ins.fax_sede_legale,                 --FAX_SEDE_LEGALE,    x
        rec_ins.telefono_sede_legale,    --TELEFONO_SEDE_LEGALE, x
        sysdate,                                 --DT_INIZIO_VALIDITA, x
        NULL,                                    --DT_ELABORAZIONE,    x
        -1,                                      --ID_UTENTE_INS,    x
        NULL,                                    --ID_UTENTE_AGG,    x
        rec_ins.id_forma_giuridica,              --ID_FORMA_GIURIDICA, x
        rec_ins.id_attivita_ateco,               --ID_ATTIVITA_ATECO,  x
        NULL,                                    --ID_DIMENSIONE_IMPRESA,
        NULL,                                    --ID_COMUNE_ESTERO_SEDE_LEGALE,
        rec_ins.id_comune_sede_legale,  --ID_COMUNE_ITALIANO_SEDE_LEGALE,  ???
        rec_ins.id_tipo_anagrafica_pg,              --ID_TIPO_ANAGRAFICA,            X
        rec_ins.id_tipo_soggetto_pg,                 --ID_TIPO_SOGGETTO,              X
        NULL,                                     --ID_TIPO_SOGGETTO_CORRELATO,    X
        rec_ins.id_tipo_beneficiario_pg,             --ID_TIPO_BENEFICIARIO,          X
        NULL,                                     --INDIRIZZO_PF,
        NULL,                                      --CAP_PF
        NULL,                                     --EMAIL_PF,
        NULL,                                     --FAX_PF,
        NULL,                                     --TELEFONO_PF,
        NULL,                                      --ID_COMUNE_ITALIANO_NASCITA,
        NULL,                                     --ID_COMUNE_ESTERO_NASCITA,
        NULL,                                       --ID_COMUNE_ITALIANO_RESIDENZA,
        NULL,                                       --ID_COMUNE_ESTERO_RESIDENZA,
        rec_ins.iban,                               --IBAN,
        vID_CSP_PROGETTO,                           --vID_CSP_PROGETTO,
        NULL,                                       --ID_ENTE_COMPETENZA,
        NULL,                                       --ID_DIPARTIMENTO,
        NULL,                                       --ID_ATENEO,
        NULL,                                       --DT_COSTITUZIONE_AZIENDA  ,
        NULL,                                       --CIVICO  ,
        NULL                                      --ID_SETTORE_ENTE
     )returning ID_CSP_SOGGETTO into vID_CSP_SOGGETTO;

     -------------------------------
     -- PBANDI_T_CSP_SOGG_RUOLO_ENTE
     -------------------------------
   FOR i IN 10..11
      LOOP
     ---
     Insert Into PBANDI_T_CSP_SOGG_RUOLO_ENTE
     (ID_CSP_SOGG_RUOLO_ENTE,
      ID_CSP_SOGGETTO,
      ID_RUOLO_ENTE_COMPETENZA,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      DT_INIZIO_VALIDITA,
      DT_ELABORAZIONE
     )VALUES
     (seq_pbandi_t_csp_sogg_ruolo_en.nextval,    --ID_CSP_SOGG_RUOLO_ENTE,
      vID_CSP_SOGGETTO,           --ID_CSP_SOGGETTO,
      i,                         --ID_RUOLO_ENTE_COMPETENZA,  -- si inseriscono tre record (id = 3,4,5)
      -1,                        --ID_UTENTE_INS,
      NULL,                      --ID_UTENTE_AGG,
      SYSDATE,                   --DT_INIZIO_VALIDITA,
      NULL                       --DT_ELABORAZIONE
     );
     ---
     END LOOP;


-------------------
-- Persona Fisica
-------------------

Insert Into PBANDI_T_CSP_SOGGETTO
     (ID_CSP_SOGGETTO,
        CODICE_FISCALE,
        DENOMINAZIONE,
        COGNOME,
        NOME,
        DT_NASCITA,
        SESSO,
        PARTITA_IVA_SEDE_LEGALE,
        INDIRIZZO_SEDE_LEGALE,
        CAP_SEDE_LEGALE,
        EMAIL_SEDE_LEGALE,
        FAX_SEDE_LEGALE,
        TELEFONO_SEDE_LEGALE,
        DT_INIZIO_VALIDITA,
        DT_ELABORAZIONE,
        ID_UTENTE_INS,
        ID_UTENTE_AGG,
        ID_FORMA_GIURIDICA,
        ID_ATTIVITA_ATECO,
        ID_DIMENSIONE_IMPRESA,
        ID_COMUNE_ESTERO_SEDE_LEGALE,
        ID_COMUNE_ITALIANO_SEDE_LEGALE,
        ID_TIPO_ANAGRAFICA,
        ID_TIPO_SOGGETTO,
        ID_TIPO_SOGGETTO_CORRELATO,
        ID_TIPO_BENEFICIARIO,
        INDIRIZZO_PF,
        CAP_PF,
        EMAIL_PF,
        FAX_PF,
        TELEFONO_PF,
        ID_COMUNE_ITALIANO_NASCITA,
        ID_COMUNE_ESTERO_NASCITA,
        ID_COMUNE_ITALIANO_RESIDENZA,
        ID_COMUNE_ESTERO_RESIDENZA,
        IBAN,
        ID_CSP_PROGETTO,
        ID_ENTE_COMPETENZA,
        ID_DIPARTIMENTO,
        ID_ATENEO,
        DT_COSTITUZIONE_AZIENDA  ,
        CIVICO  ,
        ID_SETTORE_ENTE
     )values
     (  seq_pbandi_t_csp_soggetto.nextval,      --ID_CSP_SOGGETTO,
        rec_ins.codice_fiscale_pf,     --CODICE_FISCALE,    x
        NULL,                                  --DENOMINAZIONE,  X
        rec_ins.cognome,          --COGNOME,    X
        rec_ins.nome,      --NOME,    X
        rec_ins.dt_nascita,           --DT_NASCITA,X
        rec_ins.sesso,                --SESSO,    X
        NULL,                                    --PARTITA_IVA_SEDE_LEGALE,
        NULL,                                    --INDIRIZZO_SEDE_LEGALE,
        NULL,                                    --CAP_SEDE_LEGALE,
        NULL,                                    --EMAIL_SEDE_LEGALE,
        NULL,                                    --FAX_SEDE_LEGALE,
        NULL,                                    --TELEFONO_SEDE_LEGALE,
        sysdate,                                 --DT_INIZIO_VALIDITA,
        NULL,                                    --DT_ELABORAZIONE,
        -1,                                      --ID_UTENTE_INS,
        NULL,                                    --ID_UTENTE_AGG,
        NULL,                                    --ID_FORMA_GIURIDICA,
        NULL,                                    --ID_ATTIVITA_ATECO,
        NULL,                                    --ID_DIMENSIONE_IMPRESA,
        NULL,                                    --ID_COMUNE_ESTERO_SEDE_LEGALE,
        NULL,                                    --ID_COMUNE_ITALIANO_SEDE_LEGALE,
        16,                                       --ID_TIPO_ANAGRAFICA,            --??
        1,                                        --ID_TIPO_SOGGETTO,              --??
        1,                                        --ID_TIPO_SOGGETTO_CORRELATO,    --??
        NULL,                                     --ID_TIPO_BENEFICIARIO,          --??
        rec_ins.indirizzo_pf,                     --INDIRIZZO_PF,
        rec_ins.cap_pf,                            --CAP_PF
        NULL,                                      --EMAIL_PF,
        NULL,                                      --FAX_PF,
        NULL,                                      --TELEFONO_PF,
        NULL,                                      --ID_COMUNE_ITALIANO_NASCITA,
        NULL,                                      --ID_COMUNE_ESTERO_NASCITA,
        rec_ins.id_comune_italiano_residenza,                      --ID_COMUNE_ITALIANO_RESIDENZA,
        NULL,                                       --ID_COMUNE_ESTERO_RESIDENZA,
        NULL,                                       --IBAN,
        vID_CSP_PROGETTO,                           --vID_CSP_PROGETTO,
        NULL,                                       --ID_ENTE_COMPETENZA,
        NULL,                                       --ID_DIPARTIMENTO,
        NULL,                                       --ID_ATENEO,
        NULL,                                       --DT_COSTITUZIONE_AZIENDA  ,
        NULL,                                       --CIVICO  ,
        NULL                                        --ID_SETTORE_ENTE
     )RETURNING ID_CSP_SOGGETTO INTO vID_CSP_SOGGETTO ;

     -------------------------------
     -- PBANDI_T_CSP_SOGG_RUOLO_ENTE
     -------------------------------
   FOR i IN 10..11
      LOOP
     ---
     Insert Into PBANDI_T_CSP_SOGG_RUOLO_ENTE
     (ID_CSP_SOGG_RUOLO_ENTE,
      ID_CSP_SOGGETTO,
      ID_RUOLO_ENTE_COMPETENZA,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      DT_INIZIO_VALIDITA,
      DT_ELABORAZIONE
     )VALUES
     (seq_pbandi_t_csp_sogg_ruolo_en.nextval,    --ID_CSP_SOGG_RUOLO_ENTE,
      vID_CSP_SOGGETTO,           --ID_CSP_SOGGETTO,
      i,                         --ID_RUOLO_ENTE_COMPETENZA,  -- si inseriscono tre record (id = 3,4,5)
      -1,                        --ID_UTENTE_INS,
      NULL,                      --ID_UTENTE_AGG,
      SYSDATE,                   --DT_INIZIO_VALIDITA,
      NULL                    --DT_ELABORAZIONE
     );
     ---
     End Loop;

     UPDATE PBANDI_W_WELFARE_ABITATIVO
     SET DATA_CARICAMENTO = SYSDATE
     WHERE numero_domanda_pbandi = rec_ins.numero_domanda_pbandi;

END LOOP;

 COMMIT;
 RETURN 0;

Exception when others then
  Dbms_output.put_line('ERRORE = '||SQLERRM||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
  Rollback;
  Return - 1;
END FNC_INS_WELFARE_ABITATIVO;
/
