/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE OR REPLACE PACKAGE BODY PCK_PBANDI_UTILITY_ONLINE AS

/*******************************************************************************
   NAME:       PCK_PBANDI_UTILITY_ONLINE
   PURPOSE:    Package di funzioni di utility batch

   REVISIONS:
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.0.0      16/02/2009  Rocco Cambareri  Created this package.
   2.4.1      22/03/21    mc

  LAST MODIFY : 16/02/2009
  AUTHOR      : Rocco Cambareri
  DESCRIPTION : Creazione Package

  LAST MODIFY :
  AUTHOR      :
  DESCRIPTION :
*******************************************************************************/
  --************************************************************************************
  -- Sezione dedicata alle viste utilizzate dall'online
  --************************************************************************************

  --Metodi
  FUNCTION  GetProgetto RETURN INTEGER IS
  BEGIN
     IF g_id_progetto IS NULL THEN
	    RAISE_APPLICATION_ERROR(-20000,'PARAMETRO:ID_PROGETTO non settato');
	 ELSE
       RETURN g_id_progetto;
	 END IF;
  END;

  PROCEDURE SetParameterProgetto (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE) IS
  BEGIN
     g_id_progetto := p_id_progetto;
  END;

/************************************************************************
  RipartizionePagamenti : ripartizione dei pagamenti. Richiamata dell'on-line

  Parametri input:  pIdProgetto  - Id del progetto
                    pElencoDoc   - Elenco dei documenti di spesa
					pIdDichiarazioneSpesa - Dichiarazione di spesa
					pIdUtenteIns - Utente on line  che esegue l'operazione
*************************************************************************/
PROCEDURE RipartizionePagamenti(pIdProgetto   PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                pElencoDoc    VARCHAR2,
								pIdDichiarazioneSpesa PBANDI_T_DICHIARAZIONE_SPESA.ID_DICHIARAZIONE_SPESA%TYPE,
                                pIdUtenteIns  PBANDI_T_UTENTE.ID_UTENTE%TYPE) IS

  cursor curPagamenti(pIdDocSpesa PBANDI_R_PAGAMENTO_DOC_SPESA.ID_DOCUMENTO_DI_SPESA%type) is
  select distinct pag.id_pagamento,importo_pagamento,
				NVL((select sum(importo_quietanzato)
                       from PBANDI_R_PAG_QUOT_PARTE_DOC_SP
                      where id_pagamento =pag.id_pagamento ),0) importo_quietanziato,
		   doc.ID_DOCUMENTO_DI_SPESA,
           docSpesa.IMPORTO_QUOTA_PARTE_DOC_SPESA,docSpesa.ID_QUOTA_PARTE_DOC_SPESA,
           nvl(ndc.totale_note_di_credito,0) totale_note_di_credito
    from   PBANDI_T_PAGAMENTO pag,PBANDI_R_PAGAMENTO_DOC_SPESA doc,
           PBANDI_T_QUOTA_PARTE_DOC_SPESA docSpesa,
           (select notecredito.id_doc_riferimento,
                   qtp.id_rigo_conto_economico,
                   sum(qtp.importo_quota_parte_doc_spesa) totale_note_di_credito
            from   pbandi_t_documento_di_spesa notecredito,
                   pbandi_t_quota_parte_doc_spesa qtp
            where  qtp.id_documento_di_spesa = notecredito.id_documento_di_spesa
            group by notecredito.id_doc_riferimento,
                     qtp.id_rigo_conto_economico) ndc
    where  pag.id_pagamento               = doc.id_pagamento
    AND    docSpesa.ID_PROGETTO                = pIdProgetto
    AND    DOC.ID_DOCUMENTO_DI_SPESA      = pIdDocSpesa
    and    docSpesa.ID_DOCUMENTO_DI_SPESA = doc.ID_DOCUMENTO_DI_SPESA
    and    Ndc.ID_DOC_RIFERIMENTO(+)      = DOCSPESA.ID_DOCUMENTO_DI_SPESA
    and    ndc.id_rigo_conto_economico(+) = DOCSPESA.ID_RIGO_CONTO_ECONOMICO
    and    not exists (select 'x' from PBANDI_R_PAG_QUOT_PARTE_DOC_SP parte
                       where  pag.id_pagamento = parte.id_pagamento
					   and    docSpesa.ID_QUOTA_PARTE_DOC_SPESA = parte.ID_QUOTA_PARTE_DOC_SPESA) -- Aggiunto 15/3/2013 per gestire caso di stesso pagamento su quote parte/progetti diversi (possibili con nuova evolutiva)
    order by pag.id_pagamento;


  nImpQ                       PBANDI_R_PAG_QUOT_PARTE_DOC_SP.IMPORTO_QUIETANZATO%TYPE;
  nResiduoQ                   NUMBER;
  nPagRes                     NUMBER;
  nIdPag                      PBANDI_T_PAGAMENTO.ID_PAGAMENTO%TYPE := 0;
  nPosI                       PLS_INTEGER := 0;
  nPosj                       PLS_INTEGER := 0;
  vElemCorr                   VARCHAR2(100);
  nPosK                       PLS_INTEGER := 0;
begin

  loop
    nPosJ:=nPosJ+1;
    nPosI:=instr(pElencoDoc,',',1,nPosJ);
    EXIT WHEN nPosI=0;

    vElemCorr:=SUBSTR(pElencoDoc,nPosK+1,(nPosI-1)-nPosK);
    nPosK := nPosI;

    for recPagamenti in curPagamenti(to_number(vElemCorr)) loop
      if nIdPag != recPagamenti.ID_PAGAMENTO then
        nIdPag  := recPagamenti.ID_PAGAMENTO;
        nPagRes := recPagamenti.importo_pagamento - recPagamenti.importo_quietanziato;
      end if;

      if nPagRes > 0 then
        begin
          select sum(IMPORTO_QUIETANZATO)
          into   nImpQ
          from   PBANDI_R_PAG_QUOT_PARTE_DOC_SP
          where  ID_QUOTA_PARTE_DOC_SPESA = recPagamenti.ID_QUOTA_PARTE_DOC_SPESA
          group by ID_QUOTA_PARTE_DOC_SPESA;
        exception
          when no_data_found then
            nImpQ := 0;
        end;

        nResiduoQ := recPagamenti.IMPORTO_QUOTA_PARTE_DOC_SPESA - recPagamenti.totale_note_di_credito - nImpQ;

        if nResiduoQ > 0 then
          if nPagRes > nResiduoQ then
            insert into PBANDI_R_PAG_QUOT_PARTE_DOC_SP
            (PROGR_PAG_QUOT_PARTE_DOC_SP,ID_PAGAMENTO, ID_DICHIARAZIONE_SPESA, ID_QUOTA_PARTE_DOC_SPESA, IMPORTO_QUIETANZATO, ID_UTENTE_INS,
            DT_INSERIMENTO)
            values
            (seq_pbandi_r_pag_qu_par_doc_sp.nextval,recPagamenti.ID_PAGAMENTO, pIdDichiarazioneSpesa, recPagamenti.ID_QUOTA_PARTE_DOC_SPESA,nResiduoQ,pIdUtenteIns,
             SYSDATE);

            nPagRes := nPagRes - nResiduoQ;
          else
            insert into PBANDI_R_PAG_QUOT_PARTE_DOC_SP
            (PROGR_PAG_QUOT_PARTE_DOC_SP,ID_PAGAMENTO, ID_DICHIARAZIONE_SPESA, ID_QUOTA_PARTE_DOC_SPESA, IMPORTO_QUIETANZATO, ID_UTENTE_INS,DT_INSERIMENTO)
            values
            (seq_pbandi_r_pag_qu_par_doc_sp.nextval,recPagamenti.ID_PAGAMENTO, pIdDichiarazioneSpesa, recPagamenti.ID_QUOTA_PARTE_DOC_SPESA,nPagRes,pIdUtenteIns,
            SYSDATE);

            nPagRes := 0;
          end if;
        end if;
      end if;
    end loop;
  end loop;
end RipartizionePagamenti;

/************************************************************************
  Revert RipartizionePagamenti : ripartizione dei pagamenti. Richiamata dell'on-line

  Parametri input:  pIdProgetto  - Id del progetto
                    pElencoDoc   - Elenco dei documenti di spesa
					pIdDichiarazioneSpesa - Dichiarazione di spesa
					pIdUtenteIns - Utente on line  che esegue l'operazione
*************************************************************************/
PROCEDURE RevRipartizionePagamenti(pIdProgetto   PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                   pElencoDoc    T_NUMBER_ARRAY,
								   pIdDichiarazioneSpesa PBANDI_T_DICHIARAZIONE_SPESA.ID_DICHIARAZIONE_SPESA%TYPE,
                                   pIdUtenteIns  PBANDI_T_UTENTE.ID_UTENTE%TYPE) IS

  cursor curPagamenti(pIdDocSpesa PBANDI_R_PAGAMENTO_DOC_SPESA.ID_DOCUMENTO_DI_SPESA%type) is
    select rpq.PROGR_PAG_QUOT_PARTE_DOC_SP, docSpesa.ID_QUOTA_PARTE_DOC_SPESA, rpq.id_pagamento
	from   PBANDI_T_QUOTA_PARTE_DOC_SPESA docSpesa, PBANDI_R_PAG_QUOT_PARTE_DOC_SP rpq
	where  docSpesa.ID_PROGETTO           = pIdProgetto
	AND    docSpesa.ID_DOCUMENTO_DI_SPESA = pIdDocSpesa
	AND    rpq.ID_DICHIARAZIONE_SPESA     = pIdDichiarazioneSpesa
	AND    rpq.id_quota_parte_doc_spesa   = docSpesa.ID_QUOTA_PARTE_DOC_SPESA;


  cursor curPagDich(cIdDichiarazioneSpesa NUMBER,
                  cIdDocspesa NUMBER) is
    select id_pagamento
	from PBANDI_R_PAGAMENTO_DICH_SPESA
	where id_dichiarazione_spesa = cIdDichiarazioneSpesa
	and id_pagamento IN (select id_pagamento
	                     from PBANDI_R_PAGAMENTO_DOC_SPESA
						 where id_documento_di_spesa = cIdDocspesa
						 );


  nImpQ                       PBANDI_R_PAG_QUOT_PARTE_DOC_SP.IMPORTO_QUIETANZATO%TYPE;
  nResiduoQ                   NUMBER;
  nPagRes                     NUMBER;
  nIdPag                      PBANDI_T_PAGAMENTO.ID_PAGAMENTO%TYPE := 0;
  nPosI                       PLS_INTEGER := 0;
  nPosj                       PLS_INTEGER := 0;
  vElemCorr                   VARCHAR2(100);
  nPosK                       PLS_INTEGER := 0;
begin

  for x IN pElencoDoc.FIRST..pElencoDoc.LAST loop

	 /* Pulizia dati generati da release precedente (Gestione documenti di spesa) per dichiarazioni di spesa chiuse*/
 	for recPagDich in curPagDich (pIdDichiarazioneSpesa,pElencoDoc(x)) loop


	   delete PBANDI_R_PAG_QUOT_PARTE_DOC_SP
	   where id_pagamento = recPagDich.id_pagamento
	   and id_quota_parte_doc_spesa in (select id_quota_parte_doc_spesa
	                                   from PBANDI_T_QUOTA_PARTE_DOC_SPESA
									   where id_progetto = pIdProgetto
									   and id_documento_di_spesa = pElencoDoc(x))
	   and id_dichiarazione_spesa is null;

	   delete PBANDI_R_PAGAMENTO_DICH_SPESA
	   where id_pagamento = recPagDich.id_pagamento
	   and id_dichiarazione_spesa in (select id_dichiarazione_spesa
										from PBANDI_T_DICHIARAZIONE_SPESA
										where id_progetto = pIdProgetto
										and id_dichiarazione_spesa != pIdDichiarazioneSpesa
										and dt_chiusura_validazione is not null);
	end loop;

	-- Fine pulizia

    for recPagamenti in curPagamenti(pElencoDoc(x)) loop

		delete PBANDI_R_PAG_QUOT_PARTE_DOC_SP
		where PROGR_PAG_QUOT_PARTE_DOC_SP  =  recPagamenti.PROGR_PAG_QUOT_PARTE_DOC_SP;

    end loop;

	delete PBANDI_R_PAGAMENTO_DICH_SPESA
	where id_dichiarazione_spesa = pIdDichiarazioneSpesa
	and id_pagamento IN (select id_pagamento
	                     from PBANDI_R_PAGAMENTO_DOC_SPESA
						 where id_documento_di_spesa = pElencoDoc(x)
						 );

  end loop;


end RevRipartizionePagamenti;

 /*Inizializzazione archivio file */
PROCEDURE InitArchivioFile (p_id_progetto IN NUMBER,
                            p_codice_visualizzato IN VARCHAR2,
							p_id_soggetto_ben IN NUMBER,
							p_id_utente IN NUMBER) IS
   v_id_folder NUMBER;
   v_dummy VARCHAR2(1);
BEGIN

   BEGIN
      SELECT id_folder
	    INTO v_id_folder
		FROM PBANDI_T_FOLDER
		WHERE id_soggetto_ben = p_id_soggetto_ben
		  AND nome_folder = '/root';
		  
	  -- Verifico folder di progetto
	  BEGIN
		  SELECT null
			INTO v_dummy
			FROM PBANDI_T_FOLDER
			WHERE id_soggetto_ben = p_id_soggetto_ben
			  AND id_progetto = p_id_progetto;
			  
	  EXCEPTION
		  WHEN NO_DATA_FOUND THEN
		 -- Inserisco folder di progetto
			 INSERT INTO PBANDI_T_FOLDER
				(ID_FOLDER, ID_PADRE, NOME_FOLDER, ID_SOGGETTO_BEN, ID_UTENTE_INS, DT_INSERIMENTO, ID_PROGETTO)
			 VALUES
				(SEQ_PBANDI_T_FOLDER.NEXTVAL, v_id_folder, '/'||p_codice_visualizzato,  p_id_soggetto_ben, p_id_utente, SYSDATE, p_id_progetto);
     
	  END;
	  
   EXCEPTION
      WHEN NO_DATA_FOUND THEN
	     INSERT INTO PBANDI_T_FOLDER
		    (ID_FOLDER, NOME_FOLDER, ID_SOGGETTO_BEN, ID_UTENTE_INS, DT_INSERIMENTO)
	     VALUES
		    (SEQ_PBANDI_T_FOLDER.NEXTVAL, '/root', p_id_soggetto_ben, p_id_utente, SYSDATE)
		 RETURNING ID_FOLDER INTO v_id_folder;
		 
		 -- Inserisco folder di progetto
		 INSERT INTO PBANDI_T_FOLDER
			(ID_FOLDER, ID_PADRE, NOME_FOLDER, ID_SOGGETTO_BEN, ID_UTENTE_INS, DT_INSERIMENTO, ID_PROGETTO)
		 VALUES
			(SEQ_PBANDI_T_FOLDER.NEXTVAL, v_id_folder, '/'||p_codice_visualizzato,  p_id_soggetto_ben, p_id_utente, SYSDATE, p_id_progetto);
 END;    
END InitArchivioFile;

/************************************************************************
CaricamentoSchedaProgetto : ripartizione dei pagamenti. Richiamata dell'on-line

Parametri input:  pIdCspProgetto - Id del progetto csp
*************************************************************************/
FUNCTION CaricamentoSchedaProgetto(pIdCspProgetto  PBANDI_T_CSP_PROGETTO.ID_CSP_PROGETTO%TYPE) RETURN NUMBER IS

  CURSOR curSoggetto IS SELECT cs.*,TS.DESC_BREVE_TIPO_SOGGETTO,
                               TA.DESC_BREVE_TIPO_ANAGRAFICA,
                               SE.CODICE_FISCALE as CODICE_FISCALE_SETTORE, SE.DESC_SETTORE
                        FROM   PBANDI_T_CSP_SOGGETTO cs,PBANDI_D_TIPO_SOGGETTO ts,PBANDI_D_SETTORE_ENTE se,  -- Settori Enti
                               PBANDI_D_TIPO_ANAGRAFICA TA
                        WHERE  ID_CSP_PROGETTO       = pIdCspProgetto
                        AND    CS.ID_TIPO_SOGGETTO   = TS.ID_TIPO_SOGGETTO
                        AND    CS.ID_TIPO_ANAGRAFICA = TA.ID_TIPO_ANAGRAFICA(+)
                        AND    CS.ID_SETTORE_ENTE = SE.ID_SETTORE_ENTE(+)
                        AND    DT_ELABORAZIONE       IS NULL
                        ORDER  BY CS.ID_TIPO_ANAGRAFICA;						

  CURSOR curSede IS SELECT *
                    FROM   PBANDI_T_CSP_PROG_SEDE_INTERV
                    WHERE  ID_CSP_PROGETTO = pIdCspProgetto
                    AND    DT_ELABORAZIONE IS NULL;

  CURSOR curRuoloEnte(pIdCspSoggetto  PBANDI_T_CSP_SOGG_RUOLO_ENTE.ID_CSP_SOGGETTO%TYPE) IS
                    SELECT *
                    FROM   PBANDI_T_CSP_SOGG_RUOLO_ENTE
                    WHERE  ID_CSP_SOGGETTO = pIdCspSoggetto
                    AND    DT_ELABORAZIONE IS NULL;

  TYPE recIntervento IS RECORD (idSedeInt       PBANDI_T_SEDE.ID_SEDE%TYPE,
                                idIndirizzoInt  PBANDI_T_INDIRIZZO.ID_INDIRIZZO%TYPE,
                                idRecapitiInt   PBANDI_T_RECAPITI.ID_RECAPITI%TYPE);

  TYPE recTbIntervento IS TABLE OF recIntervento INDEX BY PLS_INTEGER;

  vTbIntervento  recTbIntervento;


  vNomeBatch                 PBANDI_D_NOME_BATCH.NOME_BATCH%TYPE := 'PBAN-CSP';
  nIdProcessoBatch           PBANDI_L_PROCESSO_BATCH.id_processo_batch%TYPE := PCK_PBANDI_UTILITY_BATCH.insprocbatch(vNomeBatch);
  recCspProgetto             PBANDI_T_CSP_PROGETTO%ROWTYPE;
  recCspProgFaseMonit        PBANDI_T_CSP_PROG_FASE_MONIT%ROWTYPE;
  nIdDomanda                 PBANDI_T_DOMANDA.ID_DOMANDA%TYPE;
  vNumeroDomanda             PBANDI_T_DOMANDA.NUMERO_DOMANDA%TYPE;
  dDtInizioProgettoPrevista  PBANDI_T_DOMANDA.DT_INIZIO_PROGETTO_PREVISTA%TYPE;
  dDtFineProgettoPrevista    PBANDI_T_DOMANDA.DT_FINE_PROGETTO_PREVISTA%TYPE;
  nIdContoEconomicoMain      PBANDI_T_CONTO_ECONOMICO.ID_CONTO_ECONOMICO%TYPE;
  nIdContoEconomicoMaster    PBANDI_T_CONTO_ECONOMICO.ID_CONTO_ECONOMICO%TYPE;
  nIdProgetto                PBANDI_T_PROGETTO.ID_PROGETTO%TYPE;
  vCodProgetto               PBANDI_T_PROGETTO.CODICE_PROGETTO%TYPE;
  nIdSedeIntervento          PBANDI_T_SEDE.ID_SEDE%TYPE;
  nIdIndirizzoSedeInterven   PBANDI_T_INDIRIZZO.ID_INDIRIZZO%TYPE;
  nIdRecapitiSedeIntervento  PBANDI_T_RECAPITI.ID_RECAPITI%TYPE;
  nIdSoggetto                PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE;
  nIdEnteGiuridico           PBANDI_T_ENTE_GIURIDICO.ID_ENTE_GIURIDICO%TYPE;
  vCodIgrueT13T14            PBANDI_D_LINEA_DI_INTERVENTO.COD_IGRUE_T13_T14%TYPE;
  vStatoFs                   PBANDI_T_DATI_PROGETTO_MONIT.STATO_FS%TYPE;
  vFlagStatoFas              PBANDI_T_DATI_PROGETTO_MONIT.STATO_FAS%TYPE;
  nIdEstremiBancari          PBANDI_T_ESTREMI_BANCARI.ID_ESTREMI_BANCARI%TYPE;
  nIdSedeLegale              PBANDI_T_SEDE.ID_SEDE%TYPE;
  nIdIndirizzoSedeLegale     PBANDI_T_INDIRIZZO.ID_INDIRIZZO%TYPE;
  nIdRecapitiSedeLegale      PBANDI_T_RECAPITI.ID_RECAPITI%TYPE;
  nIdPersonaFisica           PBANDI_T_PERSONA_FISICA.ID_PERSONA_FISICA%TYPE;
  nIdIndirizzoPf             PBANDI_T_INDIRIZZO.ID_INDIRIZZO%TYPE;
  nIdRecapitiPf              PBANDI_T_RECAPITI.ID_RECAPITI%TYPE;
  nProgrSoggettiCorrelati    PBANDI_R_SOGGETTI_CORRELATI.PROGR_SOGGETTI_CORRELATI%TYPE;
  nIdSoggettoBeneficiario    PBANDI_T_SOGGETTO.ID_SOGGETTO%TYPE;
  vCodiceFiscale             PBANDI_T_SOGGETTO.CODICE_FISCALE_SOGGETTO%TYPE;
  nProgrSoggettoDomanda      PBANDI_R_SOGGETTO_DOMANDA.PROGR_SOGGETTO_DOMANDA%TYPE;
  nProgrSoggettoProgetto     PBANDI_R_SOGGETTO_PROGETTO.PROGR_SOGGETTO_PROGETTO%TYPE;
  vDenominazEnteGiuridico    PBANDI_T_ENTE_GIURIDICO.DENOMINAZIONE_ENTE_GIURIDICO%TYPE;
  nIdFormaGiuridica          PBANDI_T_ENTE_GIURIDICO.ID_FORMA_GIURIDICA%TYPE;
  nIdAttivitaAteco           PBANDI_T_SEDE.ID_ATTIVITA_ATECO%TYPE;
  nIdLineaDiIntervento       PBANDI_R_BANDO_LINEA_INTERVENT.ID_LINEA_DI_INTERVENTO%TYPE;
  nIdLineaInterventoPadre    PBANDI_D_LINEA_DI_INTERVENTO.ID_LINEA_DI_INTERVENTO_PADRE%TYPE;
  nIdTipoLineaIntervento     PBANDI_D_LINEA_DI_INTERVENTO.ID_TIPO_LINEA_INTERVENTO%TYPE;
  nIdLineaDiInterventoOb     PBANDI_R_BANDO_LINEA_INTERVENT.ID_LINEA_DI_INTERVENTO%TYPE;
  vDescBreveLinea            PBANDI_D_LINEA_DI_INTERVENTO.DESC_BREVE_LINEA%TYPE;
  nContSedeLegale            PLS_INTEGER := 0;
  nContSoggRuolo             PLS_INTEGER := 0;
  nIdIndirizzoEnteComp       PBANDI_T_ENTE_COMPETENZA.ID_INDIRIZZO%TYPE;
  vCodiceProgettoCipe        PBANDI_T_DATI_PROGETTO_MONIT.CODICE_PROGETTO_CIPE%TYPE;
  nContUtente                PLS_INTEGER := 0;
  nContAna                   PLS_INTEGER := 0;
  vFlagPubblicoPrivato       NUMBER(1);
  
  -- mc svil 17032021 
  vIdBando            PBANDI_R_BANDO_LINEA_INTERVENT.ID_BANDO%TYPE;         
  vProgrBandoLineaInt PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
  vIdProgetto         PBANDI_T_PROGETTO.ID_PROGETTO%TYPE;
  vCount NUMBER;
  vCodIgrue           PBANDI_D_SOGGETTO_FINANZIATORE.COD_IGRUE_T25%TYPE;
  vIdSoggettoBeneficiario PBANDI_R_SOGGETTO_PROGETTO.ID_SOGGETTO%TYPE;
  --
  
   
-- MAIN  
BEGIN
  SELECT *
  INTO   recCspProgetto
  FROM   PBANDI_T_CSP_PROGETTO
  WHERE  ID_CSP_PROGETTO = pIdCspProgetto
  AND    DT_ELABORAZIONE IS NULL;

  /*SELECT DISTINCT FIRST_VALUE(CPFM.DT_INIZIO_PREVISTA) OVER (ORDER BY FM.COD_IGRUE_T35 ASC)
  INTO   dDtInizioProgettoPrevista
  FROM   PBANDI_T_CSP_PROG_FASE_MONIT cpfm,PBANDI_D_FASE_MONIT fm
  WHERE  CPFM.ID_CSP_PROGETTO = pIdCspProgetto
  AND    CPFM.ID_FASE_MONIT   = FM.ID_FASE_MONIT
  AND    DT_ELABORAZIONE      IS NULL;

  SELECT DISTINCT FIRST_VALUE(CPFM.DT_FINE_PREVISTA) OVER (ORDER BY FM.COD_IGRUE_T35 DESC)
  INTO   dDtFineProgettoPrevista
  FROM   PBANDI_T_CSP_PROG_FASE_MONIT cpfm,PBANDI_D_FASE_MONIT fm
  WHERE  CPFM.ID_CSP_PROGETTO = pIdCspProgetto
  AND    CPFM.ID_FASE_MONIT   = FM.ID_FASE_MONIT
  AND    DT_ELABORAZIONE      IS NULL;*/

  IF recCspProgetto.NUMERO_DOMANDA IS NULL THEN
    SELECT 'CSP'||LPAD(NVL(MAX(SUBSTR(NUMERO_DOMANDA,4)),'000') + 1,3,'0')
    INTO   vNumeroDomanda
    FROM   PBANDI_T_DOMANDA
    WHERE  NUMERO_DOMANDA LIKE 'CSP%';
  ELSE
    vNumeroDomanda := recCspProgetto.NUMERO_DOMANDA;
  END IF;

  INSERT INTO PBANDI_T_DOMANDA
  (ID_DOMANDA,DT_PRESENTAZIONE_DOMANDA,
   TITOLO_PROGETTO,NUMERO_DOMANDA,ID_UTENTE_INS,
   PROGR_BANDO_LINEA_INTERVENTO,
   ID_STATO_DOMANDA,
   FLAG_DOMANDA_MASTER,ID_TIPO_AIUTO,DT_INIZIO_PROGETTO_PREVISTA,
   DT_FINE_PROGETTO_PREVISTA,DT_INSERIMENTO)
  VALUES
  (SEQ_PBANDI_T_DOMANDA.NEXTVAL,NVL(recCspProgetto.DT_PRESENTAZIONE_DOMANDA,recCspProgetto.DT_INIZIO_VALIDITA),
   recCspProgetto.TITOLO_PROGETTO,vNumeroDomanda,recCspProgetto.ID_UTENTE_INS,
   recCspProgetto.PROGR_BANDO_LINEA_INTERVENTO,
   (SELECT VALORE FROM PBANDI_W_CSP_COSTANTI WHERE ATTRIBUTO = 'STATO_DOMANDA' and PROGR_BANDO_LINEA_INTERVENTO = recCspProgetto.PROGR_BANDO_LINEA_INTERVENTO),
   'N',recCspProgetto.ID_TIPO_AIUTO,dDtInizioProgettoPrevista,
   dDtFineProgettoPrevista,SYSDATE)
  RETURNING ID_DOMANDA INTO nIdDomanda;

  SELECT bli.ID_LINEA_DI_INTERVENTO
  INTO   nIdLineaDiIntervento
  FROM   PBANDI_R_BANDO_LINEA_INTERVENT bli
  WHERE  bli.PROGR_BANDO_LINEA_INTERVENTO = recCspProgetto.PROGR_BANDO_LINEA_INTERVENTO;

  nIdLineaInterventoPadre := nIdLineaDiIntervento;
  nIdLineaDiInterventoOb  := nIdLineaDiIntervento;
  nIdTipoLineaIntervento  := NULL;

  loop
  exit when ((nIdLineaInterventoPadre is null) or (nIdTipoLineaIntervento = 1));
    begin
      SELECT LDI.COD_IGRUE_T13_T14,LDI.DESC_BREVE_LINEA,ID_LINEA_DI_INTERVENTO_PADRE,ID_TIPO_LINEA_INTERVENTO
      INTO   vCodIgrueT13T14,vDescBreveLinea,nIdLineaInterventoPadre,nIdTipoLineaIntervento
      FROM   PBANDI_D_LINEA_DI_INTERVENTO ldi
      WHERE  ldi.ID_LINEA_DI_INTERVENTO = nIdLineaDiInterventoOb;

      if nIdLineaInterventoPadre is not null and nIdTipoLineaIntervento != 1 then
        nIdLineaDiInterventoOb := nIdLineaInterventoPadre;
      end if;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        vDescBreveLinea := null;
        vCodIgrueT13T14 := null;
    end;
  end loop;

  if vDescBreveLinea is null then
    BEGIN
      SELECT LDI.COD_IGRUE_T13_T14,ldi.DESC_BREVE_LINEA
      INTO   vCodIgrueT13T14,vDescBreveLinea
      FROM   PBANDI_R_LINEA_INTERV_MAPPING lim,PBANDI_D_LINEA_DI_INTERVENTO ldi
      WHERE  lim.ID_LINEA_DI_INTERVENTO_MIGRATA = nIdLineaDiInterventoOb
      AND    lim.ID_LINEA_DI_INTERVENTO_ATTUALE = ldi.ID_LINEA_DI_INTERVENTO;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        vDescBreveLinea := null;
        vCodIgrueT13T14 := null;
    end;
  end if;

  SELECT DESC_BREVE_ENTE||'_'||
         TO_CHAR(NVL(recCspProgetto.DT_CONCESSIONE,SYSDATE),'YYYY')||'_'||
         vNumeroDomanda
  INTO   vCodProgetto
  FROM (
  SELECT DISTINCT ec.DESC_BREVE_ENTE
  FROM   PBANDI_T_ENTE_COMPETENZA ec,
         PBANDI_R_BANDO_LINEA_ENTE_COMP blec
  WHERE  blec.ID_ENTE_COMPETENZA           = ec.ID_ENTE_COMPETENZA
  AND    blec.PROGR_BANDO_LINEA_INTERVENTO = recCspProgetto.PROGR_BANDO_LINEA_INTERVENTO
  AND    blec.ID_RUOLO_ENTE_COMPETENZA     = 2) a;

  INSERT INTO PBANDI_T_PROGETTO
  (ID_PROGETTO, TITOLO_PROGETTO, FLAG_PROGETTO_MASTER,
   ID_UTENTE_INS,ID_DOMANDA, ID_TIPO_OPERAZIONE,
   ID_STATO_PROGETTO,
   CODICE_PROGETTO, CODICE_VISUALIZZATO,DT_CONCESSIONE,DT_COMITATO,
   ID_CATEGORIA_CIPE,ID_TIPOLOGIA_CIPE,
   ID_INDICATORE_QSN,ID_TEMA_PRIORITARIO,
   ID_OBIETTIVO_SPECIF_QSN,ID_IND_RISULTATO_PROGRAM,
   CUP,ID_SETTORE_CPT, ID_STRUMENTO_ATTUATIVO,
   ID_PROGETTO_COMPLESSO,ID_TIPO_STRUMENTO_PROGR,DT_INSERIMENTO,
   ID_CLASSIFICAZIONE_RA,ID_GRANDE_PROGETTO,ID_OBIETTIVO_TEM)
  VALUES
  (SEQ_PBANDI_T_PROGETTO.NEXTVAL,recCspProgetto.TITOLO_PROGETTO,'N',
   recCspProgetto.ID_UTENTE_INS,nIdDomanda,recCspProgetto.ID_TIPO_OPERAZIONE,
   (SELECT VALORE FROM PBANDI_W_CSP_COSTANTI WHERE ATTRIBUTO = 'STATO_PROGETTO' and PROGR_BANDO_LINEA_INTERVENTO = recCspProgetto.PROGR_BANDO_LINEA_INTERVENTO),
   vCodProgetto,vCodProgetto,NVL(recCspProgetto.DT_CONCESSIONE,recCspProgetto.DT_INIZIO_VALIDITA),recCspProgetto.DT_COMITATO,
   recCspProgetto.ID_CATEGORIA_CIPE,recCspProgetto.ID_TIPOLOGIA_CIPE,
   recCspProgetto.ID_INDICATORE_QSN,recCspProgetto.ID_TEMA_PRIORITARIO,
   recCspProgetto.ID_OBIETTIVO_SPECIF_QSN,recCspProgetto.ID_IND_RISULTATO_PROGRAM,
   recCspProgetto.CUP,recCspProgetto.ID_SETTORE_CPT, recCspProgetto.ID_STRUMENTO_ATTUATIVO,
   recCspProgetto.ID_PROGETTO_COMPLESSO,recCspProgetto.ID_TIPO_STRUMENTO_PROGR,SYSDATE,
   recCspProgetto.ID_CLASSIFICAZIONE_RA,recCspProgetto.ID_GRANDE_PROGETTO,recCspProgetto.ID_OBIETTIVO_TEM)
  RETURNING ID_PROGETTO INTO nIdProgetto;

  IF vCodIgrueT13T14 = '2007PI002FA011' THEN
    vStatoFs      := '2';
    vFlagStatoFas := recCspProgetto.STATO_PROGRAMMA;
  ELSE
    vStatoFs      := recCspProgetto.STATO_PROGRAMMA;
    vFlagStatoFas := '2';
  END IF;

  IF recCspProgetto.NUMERO_DOMANDA     IS NOT NULL AND
     recCspProgetto.CUP                IS NOT NULL AND
     recCspProgetto.FLAG_DETTAGLIO_CUP IS NOT NULL THEN

    vCodiceProgettoCipe := recCspProgetto.NUMERO_DOMANDA;
  ELSE
    vCodiceProgettoCipe := NULL;
  END IF;

  INSERT INTO PBANDI_T_DATI_PROGETTO_MONIT
  (ID_PROGETTO, FLAG_CARDINE, FLAG_GENERATORE_ENTRATE,
   FLAG_LEG_OBIETTIVO,FLAG_ALTRO_FONDO,
   STATO_FS,STATO_FAS, ID_UTENTE_INS,CODICE_PROGETTO_CIPE,FLAG_RICHIESTA_CUP)
  VALUES
  (nIdProgetto,recCspProgetto.FLAG_CARDINE, recCspProgetto.FLAG_GENERATORE_ENTRATE,
   recCspProgetto.FLAG_LEGGE_OBBIETTIVO,recCspProgetto.FLAG_ALTRO_FONDO,
   vStatoFs, vFlagStatoFas,recCspProgetto.ID_UTENTE_INS,vCodiceProgettoCipe,recCspProgetto.FLAG_RICHIESTA_CUP);

  vTbIntervento.DELETE;

  FOR recSede IN curSede LOOP

    BEGIN
      SELECT ID_SEDE
      INTO   nIdSedeIntervento
      FROM   PBANDI_T_SEDE
      WHERE  NVL(PARTITA_IVA,'0')          = NVL(recSede.PARTITA_IVA,'0')
      AND    NVL(ID_ATTIVITA_ATECO,0)      = NVL(recCspProgetto.ID_ATTIVITA_ATECO,0)
      --AND    NVL(ID_DIMENSIONE_TERRITOR,0) = NVL(recCspProgetto.ID_DIMENSIONE_TERRITOR,0)
      AND    ROWNUM                        = 1;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        INSERT INTO PBANDI_T_SEDE
        (ID_SEDE, PARTITA_IVA,
         ID_UTENTE_INS, ID_ATTIVITA_ATECO,
         DT_INIZIO_VALIDITA,ID_DIMENSIONE_TERRITOR)
        VALUES
        (SEQ_PBANDI_T_SEDE.NEXTVAL,recSede.PARTITA_IVA,
         recSede.ID_UTENTE_INS,recCspProgetto.ID_ATTIVITA_ATECO,
         recSede.DT_INIZIO_VALIDITA,recCspProgetto.ID_DIMENSIONE_TERRITOR)
        RETURNING ID_SEDE INTO nIdSedeIntervento;
    END;

    vTbIntervento(curSede%ROWCOUNT).idSedeInt := nIdSedeIntervento;

    BEGIN
      SELECT ID_INDIRIZZO
      INTO   nIdIndirizzoSedeInterven
      FROM   PBANDI_T_INDIRIZZO
      WHERE  NVL(DESC_INDIRIZZO,'0') = NVL(recSede.INDIRIZZO,'0')
      AND    NVL(ID_COMUNE,0)        = NVL(recSede.ID_COMUNE,'0')
      AND    NVL(ID_COMUNE_ESTERO,0) = NVL(recSede.ID_COMUNE_ESTERO,'0')
      AND    NVL(CAP,'0')            = NVL(recSede.CAP,'0')
      AND    NVL(ID_PROVINCIA,0)     = NVL(recSede.ID_PROVINCIA,0)
      AND    NVL(ID_REGIONE,0)       = NVL(recSede.ID_REGIONE,0)
      AND    NVL(ID_NAZIONE,0)       = NVL(recSede.ID_NAZIONE,0)
      AND    ROWNUM                  = 1;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        INSERT INTO PBANDI_T_INDIRIZZO
        (ID_INDIRIZZO, DESC_INDIRIZZO,
         CAP, ID_COMUNE,
         DT_INIZIO_VALIDITA,ID_UTENTE_INS,
         ID_COMUNE_ESTERO,ID_PROVINCIA,ID_REGIONE,
         ID_NAZIONE)
        VALUES
        (SEQ_PBANDI_T_INDIRIZZO.NEXTVAL,recSede.INDIRIZZO,
         recSede.CAP,recSede.ID_COMUNE,
         recSede.DT_INIZIO_VALIDITA,recSede.ID_UTENTE_INS,
         recSede.ID_COMUNE_ESTERO,recSede.ID_PROVINCIA,recSede.ID_REGIONE,
         recSede.ID_NAZIONE)
        RETURNING ID_INDIRIZZO INTO nIdIndirizzoSedeInterven;
    END;

    vTbIntervento(curSede%ROWCOUNT).idIndirizzoInt := nIdIndirizzoSedeInterven;

    BEGIN
      SELECT ID_RECAPITI
      INTO   nIdRecapitiSedeIntervento
      FROM   PBANDI_T_RECAPITI
      WHERE  NVL(EMAIL,'0')    = NVL(recSede.EMAIL,'0')
      AND    NVL(FAX,'0')      = NVL(recSede.FAX,'0')
      AND    NVL(TELEFONO,'0') = NVL(recSede.TELEFONO,'0')
      AND    ROWNUM            = 1;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        INSERT INTO PBANDI_T_RECAPITI
        (ID_RECAPITI, EMAIL,
         FAX, TELEFONO,
         ID_UTENTE_INS, DT_INIZIO_VALIDITA)
        VALUES
        (SEQ_PBANDI_T_RECAPITI.NEXTVAL,recSede.EMAIL,
         recSede.FAX,recSede.TELEFONO,
         recSede.ID_UTENTE_INS,recSede.DT_INIZIO_VALIDITA)
        RETURNING ID_RECAPITI INTO nIdRecapitiSedeIntervento;
    END;

    vTbIntervento(curSede%ROWCOUNT).idRecapitiInt := nIdRecapitiSedeIntervento;
  END LOOP;

  /*INSERT INTO PBANDI_R_PROGETTO_FASE_MONIT
  (ID_PROGETTO, ID_FASE_MONIT, DT_INIZIO_PREVISTA, DT_FINE_PREVISTA,
   DT_INIZIO_EFFETTIVA, DT_FINE_EFFETTIVA, ID_UTENTE_INS)
  (SELECT nIdProgetto, ID_FASE_MONIT, DT_INIZIO_PREVISTA, DT_FINE_PREVISTA,
          DT_INIZIO_EFFETTIVA, DT_FINE_EFFETTIVA, ID_UTENTE_INS
   FROM   PBANDI_T_CSP_PROG_FASE_MONIT
   WHERE  ID_CSP_PROGETTO = pIdCspProgetto
   AND    DT_ELABORAZIONE IS NULL);*/


  /*INSERT INTO PBANDI_R_DOMANDA_INDICATORI
  (ID_INDICATORI, VALORE_PROG_INIZIALE, DT_INIZIO_VALIDITA,
   ID_UTENTE_INS, ID_DOMANDA)
  (SELECT ID_INDICATORI, VALORE_PROG_INIZIALE, DT_INIZIO_VALIDITA,
          ID_UTENTE_INS, nIdDomanda
   FROM   PBANDI_T_CSP_PROG_INDICATORI
   WHERE  ID_CSP_PROGETTO = pIdCspProgetto
   AND    DT_ELABORAZIONE IS NULL);*/


  FOR recSoggetto IN curSoggetto LOOP
    nIdEstremiBancari      := NULL;
    nIdSedeLegale          := NULL;
    nIdIndirizzoSedeLegale := NULL;
    nIdRecapitiSedeLegale  := NULL;
    nIdEnteGiuridico       := NULL;
    nIdPersonaFisica       := NULL;
    nIdIndirizzoPf         := NULL;
    nIdRecapitiPf          := NULL;

    IF recSoggetto.CODICE_FISCALE IS NOT NULL THEN
      vCodiceFiscale := recSoggetto.CODICE_FISCALE;
    ELSE
      BEGIN
        SELECT CODICE_FISCALE_DIPARTIMENTO
        INTO   vCodiceFiscale
        FROM   PBANDI_D_DIPARTIMENTO
        WHERE  ID_DIPARTIMENTO = recSoggetto.ID_DIPARTIMENTO;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          BEGIN
            SELECT CODICE_FISCALE_ATENEO
            INTO   vCodiceFiscale
            FROM   PBANDI_D_ATENEO
            WHERE  ID_ATENEO = recSoggetto.ID_ATENEO;
          EXCEPTION
            WHEN NO_DATA_FOUND THEN
			  IF recSoggetto.CODICE_FISCALE_SETTORE IS NOT NULL THEN  -- Settori Enti
			    vCodiceFiscale := recSoggetto.CODICE_FISCALE_SETTORE;
			  ELSE
				  BEGIN
					  SELECT CODICE_FISCALE_ENTE
					  INTO   vCodiceFiscale
					  FROM   PBANDI_T_ENTE_COMPETENZA
					  WHERE  ID_ENTE_COMPETENZA = recSoggetto.ID_ENTE_COMPETENZA;
				 EXCEPTION
				    WHEN NO_DATA_FOUND THEN vCodiceFiscale := '';
			     END;
			  END IF;
          END;
      END;
    END IF;

    BEGIN
      SELECT ID_SOGGETTO
      INTO   nIdSoggetto
      FROM   PBANDI_T_SOGGETTO
      WHERE  CODICE_FISCALE_SOGGETTO = vCodiceFiscale
      AND    ID_TIPO_SOGGETTO        = recSoggetto.ID_TIPO_SOGGETTO
      AND    ROWNUM                  = 1;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        INSERT INTO PBANDI_T_SOGGETTO
        (ID_SOGGETTO, CODICE_FISCALE_SOGGETTO,
         ID_UTENTE_INS, ID_TIPO_SOGGETTO,DT_INSERIMENTO)
        VALUES
        (SEQ_PBANDI_T_SOGGETTO.NEXTVAL,vCodiceFiscale,
         recSoggetto.ID_UTENTE_INS,recSoggetto.ID_TIPO_SOGGETTO,SYSDATE)
        RETURNING ID_SOGGETTO INTO nIdSoggetto;
    END;

    IF recSoggetto.DESC_BREVE_TIPO_ANAGRAFICA = 'BENEFICIARIO' THEN
      nIdSoggettoBeneficiario := nIdSoggetto;
    END IF;

    IF recSoggetto.DESC_BREVE_TIPO_SOGGETTO   = 'EG' THEN
      IF recSoggetto.ID_DIPARTIMENTO IS NOT NULL THEN
	  	--la denominazione si calcola concatenando la descrizione estesa dell’Ateneo + “-“ + la descrizione estesa del Dipartimento
        SELECT SUBSTR(A.DESC_ATENEO||' - '||D.DESC_DIPARTIMENTO,1,255),A.ID_FORMA_GIURIDICA,A.ID_ATTIVITA_ATECO
        INTO   vDenominazEnteGiuridico,nIdFormaGiuridica,nIdAttivitaAteco
        FROM   PBANDI_D_DIPARTIMENTO d,PBANDI_D_ATENEO a
        WHERE  D.ID_DIPARTIMENTO = recSoggetto.ID_DIPARTIMENTO
        AND    D.ID_ATENEO       = A.ID_ATENEO;
		
      ELSIF recSoggetto.ID_ATENEO IS NOT NULL THEN
        SELECT DESC_ATENEO,ID_FORMA_GIURIDICA,ID_ATTIVITA_ATECO
        INTO   vDenominazEnteGiuridico,nIdFormaGiuridica,nIdAttivitaAteco
        FROM   PBANDI_D_ATENEO
        WHERE  ID_ATENEO = recSoggetto.ID_ATENEO;
		
      ELSIF recSoggetto.ID_SETTORE_ENTE IS NOT NULL THEN  -- Settori Enti
	    --la denominazione concatenando la descrizione estesa dell’ente di competenza + “-“ + la descrizione estesa del Settore Ente, l'ID Forma giuridica dall'Ente di Competenza
		vDenominazEnteGiuridico := recSoggetto.DESC_SETTORE;
        SELECT SUBSTR(DESC_ENTE||' - '||vDenominazEnteGiuridico,1,255), ID_FORMA_GIURIDICA,ID_ATTIVITA_ATECO,
               ID_INDIRIZZO
        INTO   vDenominazEnteGiuridico,nIdFormaGiuridica,nIdAttivitaAteco,
               nIdIndirizzoEnteComp
        FROM   PBANDI_T_ENTE_COMPETENZA
        WHERE  ID_ENTE_COMPETENZA = recSoggetto.ID_ENTE_COMPETENZA;
		
      ELSIF recSoggetto.ID_ENTE_COMPETENZA IS NOT NULL THEN
        SELECT DESC_ENTE, ID_FORMA_GIURIDICA,ID_ATTIVITA_ATECO,
               ID_INDIRIZZO
        INTO   vDenominazEnteGiuridico,nIdFormaGiuridica,nIdAttivitaAteco,
               nIdIndirizzoEnteComp
        FROM   PBANDI_T_ENTE_COMPETENZA
        WHERE  ID_ENTE_COMPETENZA = recSoggetto.ID_ENTE_COMPETENZA;
      ELSE
        vDenominazEnteGiuridico := recSoggetto.DENOMINAZIONE;
        nIdFormaGiuridica       := recSoggetto.ID_FORMA_GIURIDICA;
        nIdAttivitaAteco        := recSoggetto.ID_ATTIVITA_ATECO;
      END IF;

	  IF nIdFormaGiuridica IS NOT NULL THEN
	     BEGIN
		    SELECT DECODE(FLAG_PRIVATO,'S',1,2)
			  INTO vFlagPubblicoPrivato
			  FROM PBANDI_D_FORMA_GIURIDICA
			 WHERE id_forma_giuridica = nIdFormaGiuridica;
		 EXCEPTION
		   WHEN NO_DATA_FOUND THEN
		   vFlagPubblicoPrivato := '';
		 END;


	  END IF;

      BEGIN
        SELECT ID_ENTE_GIURIDICO
        INTO   nIdEnteGiuridico
        FROM   PBANDI_T_ENTE_GIURIDICO EG
        WHERE  EG.ID_SOGGETTO                  = nIdSoggetto
        AND    EG.DENOMINAZIONE_ENTE_GIURIDICO = vDenominazEnteGiuridico
        AND    NVL(EG.DT_COSTITUZIONE,SYSDATE) = NVL(recSoggetto.DT_COSTITUZIONE_AZIENDA,SYSDATE)
        AND    EG.CAPITALE_SOCIALE             IS NULL
        AND    EG.ID_ATTIVITA_UIC              IS NULL
        AND    NVL(EG.ID_FORMA_GIURIDICA,0)    = NVL(nIdFormaGiuridica,0)
        AND    EG.ID_CLASSIFICAZIONE_ENTE      IS NULL
        AND    NVL(EG.ID_DIMENSIONE_IMPRESA,0) = NVL(recSoggetto.ID_DIMENSIONE_IMPRESA,0)
        AND    EG.DT_ULTIMO_ESERCIZIO_CHIUSO   IS NULL
        AND    ROWNUM                          = 1;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          INSERT INTO PBANDI_T_ENTE_GIURIDICO
          (ID_SOGGETTO, DENOMINAZIONE_ENTE_GIURIDICO, ID_FORMA_GIURIDICA,
           ID_DIMENSIONE_IMPRESA, ID_ENTE_GIURIDICO,
           DT_INIZIO_VALIDITA,ID_UTENTE_INS,DT_COSTITUZIONE,FLAG_PUBBLICO_PRIVATO)
          VALUES
          (nIdSoggetto,vDenominazEnteGiuridico,nIdFormaGiuridica,
           recSoggetto.ID_DIMENSIONE_IMPRESA,SEQ_PBANDI_T_ENTE_GIURIDICO.NEXTVAL,
           recSoggetto.DT_INIZIO_VALIDITA,recSoggetto.ID_UTENTE_INS,recSoggetto.DT_COSTITUZIONE_AZIENDA,vFlagPubblicoPrivato)
          RETURNING ID_ENTE_GIURIDICO INTO nIdEnteGiuridico;
      END;

      IF recSoggetto.IBAN IS NULL THEN
        nIdEstremiBancari := NULL;
      ELSE
        BEGIN
          SELECT ID_ESTREMI_BANCARI
          INTO   nIdEstremiBancari
          FROM   PBANDI_T_ESTREMI_BANCARI
          WHERE  IBAN   = recSoggetto.IBAN
          AND    ROWNUM = 1;
        EXCEPTION
          WHEN NO_DATA_FOUND THEN
            INSERT INTO PBANDI_T_ESTREMI_BANCARI
            (ID_ESTREMI_BANCARI, IBAN,
             ID_UTENTE_INS, DT_INIZIO_VALIDITA)
            VALUES
            (SEQ_PBANDI_T_ESTREMI_BANCA.NEXTVAL,recSoggetto.IBAN,
             recSoggetto.ID_UTENTE_INS, recSoggetto.DT_INIZIO_VALIDITA)
            RETURNING ID_ESTREMI_BANCARI INTO nIdEstremiBancari;
        END;
      END IF;

      BEGIN
        SELECT ID_SEDE
        INTO   nIdSedeLegale
        FROM   PBANDI_T_SEDE
        WHERE  NVL(PARTITA_IVA,'0')     = NVL(recSoggetto.PARTITA_IVA_SEDE_LEGALE,'0')
        AND    NVL(ID_ATTIVITA_ATECO,0) = NVL(nIdAttivitaAteco,0)
        AND    ROWNUM                   = 1;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          INSERT INTO PBANDI_T_SEDE
          (ID_SEDE, PARTITA_IVA,
           ID_UTENTE_INS, ID_ATTIVITA_ATECO,
           DT_INIZIO_VALIDITA)
          VALUES
          (SEQ_PBANDI_T_SEDE.NEXTVAL,recSoggetto.PARTITA_IVA_SEDE_LEGALE,
           recSoggetto.ID_UTENTE_INS,nIdAttivitaAteco,
           recSoggetto.DT_INIZIO_VALIDITA)
          RETURNING ID_SEDE INTO nIdSedeLegale;
      END;

      IF recSoggetto.ID_ENTE_COMPETENZA IS NOT NULL OR 
	   recSoggetto.ID_SETTORE_ENTE IS NOT NULL THEN  -- Settori Enti
         nIdIndirizzoSedeLegale := nIdIndirizzoEnteComp;
      ELSE
        BEGIN
          SELECT ID_INDIRIZZO
          INTO   nIdIndirizzoSedeLegale
          FROM   PBANDI_T_INDIRIZZO
          WHERE  NVL(DESC_INDIRIZZO,'0') = NVL(recSoggetto.INDIRIZZO_SEDE_LEGALE,'0')
          AND    NVL(CAP,'0')            = NVL(recSoggetto.CAP_SEDE_LEGALE,'0')
          AND    NVL(ID_COMUNE,0)        = NVL(recSoggetto.ID_COMUNE_ITALIANO_SEDE_LEGALE,'0')
          AND    NVL(ID_COMUNE_ESTERO,0) = NVL(recSoggetto.ID_COMUNE_ESTERO_SEDE_LEGALE,'0')
          AND    ROWNUM                  = 1;
        EXCEPTION
          WHEN NO_DATA_FOUND THEN
            INSERT INTO PBANDI_T_INDIRIZZO
            (ID_INDIRIZZO, DESC_INDIRIZZO,
             CAP, ID_COMUNE,
             DT_INIZIO_VALIDITA,ID_UTENTE_INS,
             ID_COMUNE_ESTERO)
            VALUES
            (SEQ_PBANDI_T_INDIRIZZO.NEXTVAL,recSoggetto.INDIRIZZO_SEDE_LEGALE,
             recSoggetto.CAP_SEDE_LEGALE,recSoggetto.ID_COMUNE_ITALIANO_SEDE_LEGALE,
             recSoggetto.DT_INIZIO_VALIDITA,recSoggetto.ID_UTENTE_INS,
             recSoggetto.ID_COMUNE_ESTERO_SEDE_LEGALE)
            RETURNING ID_INDIRIZZO INTO nIdIndirizzoSedeLegale;
        END;
      END IF;

      BEGIN
        SELECT ID_RECAPITI
        INTO   nIdRecapitiSedeLegale
        FROM   PBANDI_T_RECAPITI
        WHERE  NVL(EMAIL,'0')    = NVL(recSoggetto.EMAIL_SEDE_LEGALE,'0')
        AND    NVL(FAX,'0')      = NVL(recSoggetto.FAX_SEDE_LEGALE,'0')
        AND    NVL(TELEFONO,'0') = NVL(recSoggetto.TELEFONO_SEDE_LEGALE,'0')
        AND    ROWNUM            = 1;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          INSERT INTO PBANDI_T_RECAPITI
          (ID_RECAPITI, EMAIL,
           FAX, TELEFONO,
           ID_UTENTE_INS, DT_INIZIO_VALIDITA)
          VALUES
          (SEQ_PBANDI_T_RECAPITI.NEXTVAL,recSoggetto.EMAIL_SEDE_LEGALE,
           recSoggetto.FAX_SEDE_LEGALE,recSoggetto.TELEFONO_SEDE_LEGALE,
           recSoggetto.ID_UTENTE_INS,recSoggetto.DT_INIZIO_VALIDITA)
          RETURNING ID_RECAPITI INTO nIdRecapitiSedeLegale;
      END;

      INSERT INTO PBANDI_R_SOGGETTO_DOMANDA
      (ID_SOGGETTO,ID_DOMANDA, DT_INIZIO_VALIDITA,
       PROGR_SOGGETTO_DOMANDA, ID_UTENTE_INS,
       ID_TIPO_BENEFICIARIO,ID_ESTREMI_BANCARI, ID_TIPO_ANAGRAFICA,
       ID_PERSONA_FISICA, ID_ENTE_GIURIDICO,ID_INDIRIZZO_PERSONA_FISICA,
       ID_RECAPITI_PERSONA_FISICA, ID_DOCUMENTO_PERSONA_FISICA,ID_ISCRIZIONE_PERSONA_GIURID,
       DT_INSERIMENTO)
      VALUES
      (nIdSoggetto,nIdDomanda,recSoggetto.DT_INIZIO_VALIDITA,
       SEQ_PBANDI_R_SOGGETTO_DOMANDA.NEXTVAL,recSoggetto.ID_UTENTE_INS,
       recSoggetto.ID_TIPO_BENEFICIARIO,nIdEstremiBancari,recSoggetto.ID_TIPO_ANAGRAFICA,
       NULL,nIdEnteGiuridico,NULL,
       NULL,NULL,NULL,SYSDATE)
      RETURNING PROGR_SOGGETTO_DOMANDA INTO nProgrSoggettoDomanda;

      -- sede legale
      INSERT INTO PBANDI_R_SOGGETTO_DOMANDA_SEDE
      (PROGR_SOGGETTO_DOMANDA_SEDE, PROGR_SOGGETTO_DOMANDA, ID_SEDE,
       ID_INDIRIZZO,ID_TIPO_SEDE, ID_RECAPITI, ID_UTENTE_INS)
      VALUES
      (SEQ_PBANDI_R_SOGG_DOMANDA_SEDE.NEXTVAL,nProgrSoggettoDomanda,nIdSedeLegale,
       nIdIndirizzoSedeLegale,1,nIdRecapitiSedeLegale,recSoggetto.ID_UTENTE_INS);

      IF recSoggetto.DESC_BREVE_TIPO_ANAGRAFICA = 'BENEFICIARIO' THEN
        -- sede intervento
        FOR i IN vTbIntervento.FIRST..vTbIntervento.LAST LOOP
          INSERT INTO PBANDI_R_SOGGETTO_DOMANDA_SEDE
          (PROGR_SOGGETTO_DOMANDA_SEDE, PROGR_SOGGETTO_DOMANDA,
           ID_SEDE,ID_INDIRIZZO,ID_TIPO_SEDE,
           ID_RECAPITI, ID_UTENTE_INS)
          VALUES
          (SEQ_PBANDI_R_SOGG_DOMANDA_SEDE.NEXTVAL,nProgrSoggettoDomanda,
           vTbIntervento(i).IdSedeInt,vTbIntervento(i).IdIndirizzoInt,2,
           vTbIntervento(i).IdRecapitiInt,recSoggetto.ID_UTENTE_INS);
        END LOOP;
      END IF;

      IF recSoggetto.DESC_BREVE_TIPO_ANAGRAFICA = 'BENEFICIARIO' THEN
        INSERT INTO PBANDI_R_SOGGETTO_PROGETTO
        (ID_SOGGETTO, ID_PROGETTO, PROGR_SOGGETTO_PROGETTO, DT_INIZIO_VALIDITA,
         ID_ESTREMI_BANCARI, ID_TIPO_BENEFICIARIO, ID_TIPO_ANAGRAFICA,
         ID_PERSONA_FISICA, ID_ENTE_GIURIDICO, ID_INDIRIZZO_PERSONA_FISICA,
         ID_RECAPITI_PERSONA_FISICA, ID_DOCUMENTO_PERSONA_FISICA,
         ID_ISCRIZIONE_PERSONA_GIURID, PROGR_SOGGETTO_DOMANDA, ID_UTENTE_INS,
         DT_INSERIMENTO)
        VALUES
        (nIdSoggetto,nIdProgetto,SEQ_PBANDI_R_SOGGETTO_PROGETTO.NEXTVAL,recSoggetto.DT_INIZIO_VALIDITA,
         nIdEstremiBancari,recSoggetto.ID_TIPO_BENEFICIARIO,recSoggetto.ID_TIPO_ANAGRAFICA,
         NULL,nIdEnteGiuridico,NULL,
         NULL,NULL,
         NULL,nProgrSoggettoDomanda,recSoggetto.ID_UTENTE_INS,SYSDATE)
        RETURNING PROGR_SOGGETTO_PROGETTO INTO nProgrSoggettoProgetto;
		
		-- Inizializza archivio File
		InitArchivioFile (nIdProgetto,
                          vCodProgetto,
					      nIdSoggetto,
						  recSoggetto.ID_UTENTE_INS);

      ELSE
        BEGIN
          SELECT PROGR_SOGGETTO_PROGETTO
          INTO   nProgrSoggettoProgetto
          FROM   PBANDI_R_SOGGETTO_PROGETTO
          WHERE  ID_SOGGETTO        = nIdSoggetto
          AND    ID_PROGETTO        = nIdProgetto
          AND    ID_TIPO_ANAGRAFICA = 1;
        EXCEPTION
          WHEN NO_DATA_FOUND THEN
            INSERT INTO PBANDI_R_SOGGETTO_PROGETTO
            (ID_SOGGETTO, ID_PROGETTO, PROGR_SOGGETTO_PROGETTO, DT_INIZIO_VALIDITA,
             ID_ESTREMI_BANCARI, ID_TIPO_BENEFICIARIO, ID_TIPO_ANAGRAFICA,
             ID_PERSONA_FISICA, ID_ENTE_GIURIDICO, ID_INDIRIZZO_PERSONA_FISICA,
             ID_RECAPITI_PERSONA_FISICA, ID_DOCUMENTO_PERSONA_FISICA,
             ID_ISCRIZIONE_PERSONA_GIURID, PROGR_SOGGETTO_DOMANDA, ID_UTENTE_INS,
             DT_INSERIMENTO)
            VALUES
            (nIdSoggetto,nIdProgetto,SEQ_PBANDI_R_SOGGETTO_PROGETTO.NEXTVAL,recSoggetto.DT_INIZIO_VALIDITA,
             nIdEstremiBancari,recSoggetto.ID_TIPO_BENEFICIARIO,recSoggetto.ID_TIPO_ANAGRAFICA,
             NULL,nIdEnteGiuridico,NULL,
             NULL,NULL,
             NULL,nProgrSoggettoDomanda,recSoggetto.ID_UTENTE_INS,SYSDATE)
            RETURNING PROGR_SOGGETTO_PROGETTO INTO nProgrSoggettoProgetto;
        END;
      END IF;

      IF nIdSedeLegale IS NOT NULL THEN
        SELECT COUNT(*)
        INTO   nContSedeLegale
        FROM   PBANDI_R_SOGG_PROGETTO_SEDE
        WHERE  PROGR_SOGGETTO_PROGETTO = nProgrSoggettoProgetto
        AND    ID_TIPO_SEDE            = 1;

        IF nContSedeLegale = 0 THEN
          -- sede legale
          INSERT INTO PBANDI_R_SOGG_PROGETTO_SEDE
          (PROGR_SOGGETTO_PROGETTO_SEDE, PROGR_SOGGETTO_PROGETTO, ID_SEDE,
           ID_INDIRIZZO, ID_TIPO_SEDE, ID_RECAPITI, ID_UTENTE_INS)
          VALUES
          (SEQ_PBANDI_R_SOGG_PROG_SEDE.NEXTVAL,nProgrSoggettoProgetto,nIdSedeLegale,
           nIdIndirizzoSedeLegale,1,nIdRecapitiSedeLegale,recSoggetto.ID_UTENTE_INS);
        END IF;
      END IF;

      IF recSoggetto.DESC_BREVE_TIPO_ANAGRAFICA = 'BENEFICIARIO' THEN
        -- sede intervento
        FOR i IN vTbIntervento.FIRST..vTbIntervento.LAST LOOP
          INSERT INTO PBANDI_R_SOGG_PROGETTO_SEDE
          (PROGR_SOGGETTO_PROGETTO_SEDE, PROGR_SOGGETTO_PROGETTO,
           ID_SEDE,ID_INDIRIZZO,ID_TIPO_SEDE,
           ID_RECAPITI, ID_UTENTE_INS)
          VALUES
          (SEQ_PBANDI_R_SOGG_PROG_SEDE.NEXTVAL,nProgrSoggettoProgetto,
           vTbIntervento(i).IdSedeInt,vTbIntervento(i).IdIndirizzoInt,2,
           vTbIntervento(i).IdRecapitiInt,recSoggetto.ID_UTENTE_INS);
        END LOOP;
      END IF;

    ELSE  -- PERSONA FISICA
      SELECT COUNT(*)
      INTO   nContUtente
      FROM   PBANDI_T_UTENTE
      WHERE  ID_SOGGETTO = nIdSoggetto;

      IF nContUtente = 0 THEN
        INSERT INTO PBANDI_T_UTENTE
        (ID_UTENTE,
         CODICE_UTENTE,
         ID_TIPO_ACCESSO, ID_SOGGETTO,DT_INSERIMENTO)
        VALUES
        (SEQ_PBANDI_T_UTENTE.NEXTVAL,
         (SELECT CODICE_FISCALE_SOGGETTO FROM PBANDI_T_SOGGETTO WHERE  ID_SOGGETTO = nIdSoggetto),
         5,nIdSoggetto,SYSDATE);
      END IF;

      BEGIN
        SELECT ID_PERSONA_FISICA
        INTO   nIdPersonaFisica
        FROM   PBANDI_T_PERSONA_FISICA
        WHERE  ID_SOGGETTO                       = nIdSoggetto
        AND    COGNOME                           = recSoggetto.COGNOME
        AND    NVL(NOME,'0')                     = NVL(recSoggetto.NOME,'0')
        AND    NVL(DT_NASCITA,SYSDATE)           = NVL(recSoggetto.DT_NASCITA,SYSDATE)
        AND    NVL(SESSO,'0')                    = NVL(recSoggetto.SESSO,'0')
        AND    NVL(ID_COMUNE_ITALIANO_NASCITA,0) = NVL(recSoggetto.ID_COMUNE_ITALIANO_NASCITA,0)
        AND    NVL(ID_COMUNE_ESTERO_NASCITA,0)   = NVL(recSoggetto.ID_COMUNE_ESTERO_NASCITA,0)
        AND    ROWNUM                            = 1;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          INSERT INTO PBANDI_T_PERSONA_FISICA
          (ID_SOGGETTO, COGNOME, NOME, DT_NASCITA,
           SESSO, ID_PERSONA_FISICA,
           DT_INIZIO_VALIDITA, ID_COMUNE_ITALIANO_NASCITA,
           ID_COMUNE_ESTERO_NASCITA,ID_UTENTE_INS)
          VALUES
          (nIdSoggetto, recSoggetto.COGNOME, recSoggetto.NOME, recSoggetto.DT_NASCITA,
           recSoggetto.SESSO, SEQ_PBANDI_T_PERSONA_FISICA.NEXTVAL,
           recSoggetto.DT_INIZIO_VALIDITA, recSoggetto.ID_COMUNE_ITALIANO_NASCITA,
           recSoggetto.ID_COMUNE_ESTERO_NASCITA,recSoggetto.ID_UTENTE_INS)
          RETURNING ID_PERSONA_FISICA INTO nIdPersonaFisica;
      END;

      BEGIN
        SELECT ID_INDIRIZZO
        INTO   nIdIndirizzoPf
        FROM   PBANDI_T_INDIRIZZO
        WHERE  NVL(DESC_INDIRIZZO,'0') = NVL(recSoggetto.INDIRIZZO_PF,'0')
        AND    NVL(CAP,'0')            = NVL(recSoggetto.CAP_PF,'0')
        AND    NVL(ID_COMUNE,0)        = NVL(recSoggetto.ID_COMUNE_ITALIANO_RESIDENZA,'0')
        AND    NVL(ID_COMUNE_ESTERO,0) = NVL(recSoggetto.ID_COMUNE_ESTERO_RESIDENZA,'0')
        AND    ROWNUM                  = 1;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          INSERT INTO PBANDI_T_INDIRIZZO
          (ID_INDIRIZZO, DESC_INDIRIZZO,
           CAP, ID_COMUNE,
           DT_INIZIO_VALIDITA,ID_UTENTE_INS,
           ID_COMUNE_ESTERO)
          VALUES
          (SEQ_PBANDI_T_INDIRIZZO.NEXTVAL,recSoggetto.INDIRIZZO_PF,
           recSoggetto.CAP_PF,recSoggetto.ID_COMUNE_ITALIANO_RESIDENZA,
           recSoggetto.DT_INIZIO_VALIDITA,recSoggetto.ID_UTENTE_INS,
           recSoggetto.ID_COMUNE_ESTERO_RESIDENZA)
          RETURNING ID_INDIRIZZO INTO nIdIndirizzoPf;
      END;

      BEGIN
        SELECT ID_RECAPITI
        INTO   nIdRecapitiPf
        FROM   PBANDI_T_RECAPITI
        WHERE  NVL(EMAIL,'0')    = NVL(recSoggetto.EMAIL_PF,'0')
        AND    NVL(FAX,'0')      = NVL(recSoggetto.FAX_PF,'0')
        AND    NVL(TELEFONO,'0') = NVL(recSoggetto.TELEFONO_PF,'0')
        AND    ROWNUM            = 1;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          INSERT INTO PBANDI_T_RECAPITI
          (ID_RECAPITI, EMAIL,
           FAX, TELEFONO,
           ID_UTENTE_INS, DT_INIZIO_VALIDITA)
          VALUES
          (SEQ_PBANDI_T_RECAPITI.NEXTVAL,recSoggetto.EMAIL_PF,
           recSoggetto.FAX_PF,recSoggetto.TELEFONO_PF,
           recSoggetto.ID_UTENTE_INS,recSoggetto.DT_INIZIO_VALIDITA)
          RETURNING ID_RECAPITI INTO nIdRecapitiPf;
      END;

      INSERT INTO PBANDI_R_SOGGETTO_DOMANDA
      (ID_SOGGETTO, ID_DOMANDA, DT_INIZIO_VALIDITA,
       PROGR_SOGGETTO_DOMANDA, ID_UTENTE_INS,
       ID_TIPO_BENEFICIARIO,ID_ESTREMI_BANCARI, ID_TIPO_ANAGRAFICA,
       ID_PERSONA_FISICA, ID_ENTE_GIURIDICO,ID_INDIRIZZO_PERSONA_FISICA,
       ID_RECAPITI_PERSONA_FISICA, ID_DOCUMENTO_PERSONA_FISICA,ID_ISCRIZIONE_PERSONA_GIURID,
       DT_INSERIMENTO)
      VALUES
      (nIdSoggetto,nIdDomanda,recSoggetto.DT_INIZIO_VALIDITA,
       SEQ_PBANDI_R_SOGGETTO_DOMANDA.NEXTVAL,recSoggetto.ID_UTENTE_INS,
       recSoggetto.ID_TIPO_BENEFICIARIO,NULL,recSoggetto.ID_TIPO_ANAGRAFICA,
       nIdPersonaFisica,NULL,nIdIndirizzoPf,
       nIdRecapitiPf,NULL,NULL,SYSDATE)
      RETURNING PROGR_SOGGETTO_DOMANDA INTO nProgrSoggettoDomanda;

      IF recSoggetto.DESC_BREVE_TIPO_ANAGRAFICA = 'BENEFICIARIO' THEN
        -- sede intervento
        FOR i IN vTbIntervento.FIRST..vTbIntervento.LAST LOOP
          INSERT INTO PBANDI_R_SOGGETTO_DOMANDA_SEDE
          (PROGR_SOGGETTO_DOMANDA_SEDE, PROGR_SOGGETTO_DOMANDA,
           ID_SEDE,ID_INDIRIZZO,ID_TIPO_SEDE,
           ID_RECAPITI, ID_UTENTE_INS)
          VALUES
          (SEQ_PBANDI_R_SOGG_DOMANDA_SEDE.NEXTVAL,nProgrSoggettoDomanda,
           vTbIntervento(i).IdSedeInt,vTbIntervento(i).IdIndirizzoInt,2,
           vTbIntervento(i).IdRecapitiInt,recSoggetto.ID_UTENTE_INS);
        END LOOP;
      END IF;

      IF recSoggetto.DESC_BREVE_TIPO_ANAGRAFICA = 'BENEFICIARIO' THEN
        INSERT INTO PBANDI_R_SOGGETTO_PROGETTO
        (ID_SOGGETTO, ID_PROGETTO, PROGR_SOGGETTO_PROGETTO, DT_INIZIO_VALIDITA,
         ID_ESTREMI_BANCARI, ID_TIPO_BENEFICIARIO, ID_TIPO_ANAGRAFICA,
         ID_PERSONA_FISICA, ID_ENTE_GIURIDICO, ID_INDIRIZZO_PERSONA_FISICA,
         ID_RECAPITI_PERSONA_FISICA, ID_DOCUMENTO_PERSONA_FISICA,
         ID_ISCRIZIONE_PERSONA_GIURID, PROGR_SOGGETTO_DOMANDA, ID_UTENTE_INS,
         DT_INSERIMENTO)
        VALUES
        (nIdSoggetto,nIdProgetto,SEQ_PBANDI_R_SOGGETTO_PROGETTO.NEXTVAL,recSoggetto.DT_INIZIO_VALIDITA,
         NULL,recSoggetto.ID_TIPO_BENEFICIARIO,recSoggetto.ID_TIPO_ANAGRAFICA,
         nIdPersonaFisica,NULL,nIdIndirizzoPf,
         nIdRecapitiPF,NULL,
         NULL,nProgrSoggettoDomanda,recSoggetto.ID_UTENTE_INS,SYSDATE)
        RETURNING PROGR_SOGGETTO_PROGETTO INTO nProgrSoggettoProgetto;
		
							
      ELSE
        BEGIN
          SELECT PROGR_SOGGETTO_PROGETTO
          INTO   nProgrSoggettoProgetto
          FROM   PBANDI_R_SOGGETTO_PROGETTO
          WHERE  ID_SOGGETTO        = nIdSoggetto
          AND    ID_PROGETTO        = nIdProgetto
          AND    ID_TIPO_ANAGRAFICA = 1;
        EXCEPTION
          WHEN NO_DATA_FOUND THEN
            IF recSoggetto.ID_TIPO_SOGGETTO_CORRELATO = 1 THEN
              INSERT INTO PBANDI_R_SOGGETTO_PROGETTO
              (ID_SOGGETTO, ID_PROGETTO, PROGR_SOGGETTO_PROGETTO, DT_INIZIO_VALIDITA,
               ID_ESTREMI_BANCARI, ID_TIPO_BENEFICIARIO, ID_TIPO_ANAGRAFICA,
               ID_PERSONA_FISICA, ID_ENTE_GIURIDICO, ID_INDIRIZZO_PERSONA_FISICA,
               ID_RECAPITI_PERSONA_FISICA, ID_DOCUMENTO_PERSONA_FISICA,
               ID_ISCRIZIONE_PERSONA_GIURID, PROGR_SOGGETTO_DOMANDA, ID_UTENTE_INS,DT_INSERIMENTO)
              VALUES
              (nIdSoggetto,nIdProgetto,SEQ_PBANDI_R_SOGGETTO_PROGETTO.NEXTVAL,recSoggetto.DT_INIZIO_VALIDITA,
               NULL,recSoggetto.ID_TIPO_BENEFICIARIO,recSoggetto.ID_TIPO_ANAGRAFICA,
               nIdPersonaFisica,NULL,nIdIndirizzoPf,
               nIdRecapitiPF,NULL,
               NULL,nProgrSoggettoDomanda,recSoggetto.ID_UTENTE_INS,SYSDATE)
              RETURNING PROGR_SOGGETTO_PROGETTO INTO nProgrSoggettoProgetto;
            ELSE
              BEGIN
                SELECT PROGR_SOGGETTO_PROGETTO
                INTO   nProgrSoggettoProgetto
                FROM   PBANDI_R_SOGGETTO_PROGETTO
                WHERE  ID_SOGGETTO = nIdSoggetto
                AND    ID_PROGETTO = nIdProgetto;
              EXCEPTION
                WHEN NO_DATA_FOUND THEN
                  INSERT INTO PBANDI_R_SOGGETTO_PROGETTO
                  (ID_SOGGETTO, ID_PROGETTO, PROGR_SOGGETTO_PROGETTO, DT_INIZIO_VALIDITA,
                   ID_ESTREMI_BANCARI, ID_TIPO_BENEFICIARIO, ID_TIPO_ANAGRAFICA,
                   ID_PERSONA_FISICA, ID_ENTE_GIURIDICO, ID_INDIRIZZO_PERSONA_FISICA,
                   ID_RECAPITI_PERSONA_FISICA, ID_DOCUMENTO_PERSONA_FISICA,
                   ID_ISCRIZIONE_PERSONA_GIURID, PROGR_SOGGETTO_DOMANDA, ID_UTENTE_INS,DT_INSERIMENTO)
                  VALUES
                  (nIdSoggetto,nIdProgetto,SEQ_PBANDI_R_SOGGETTO_PROGETTO.NEXTVAL,recSoggetto.DT_INIZIO_VALIDITA,
                   NULL,recSoggetto.ID_TIPO_BENEFICIARIO,recSoggetto.ID_TIPO_ANAGRAFICA,
                   nIdPersonaFisica,NULL,nIdIndirizzoPf,
                   nIdRecapitiPF,NULL,
                   NULL,nProgrSoggettoDomanda,recSoggetto.ID_UTENTE_INS,SYSDATE)
                  RETURNING PROGR_SOGGETTO_PROGETTO INTO nProgrSoggettoProgetto;
              END;
            END IF;
        END;
      END IF;

      IF recSoggetto.DESC_BREVE_TIPO_ANAGRAFICA = 'BENEFICIARIO' THEN
        -- sede intervento
        FOR i IN vTbIntervento.FIRST..vTbIntervento.LAST LOOP
          INSERT INTO PBANDI_R_SOGG_PROGETTO_SEDE
          (PROGR_SOGGETTO_PROGETTO_SEDE, PROGR_SOGGETTO_PROGETTO,
           ID_SEDE,ID_INDIRIZZO,ID_TIPO_SEDE,
           ID_RECAPITI, ID_UTENTE_INS)
          VALUES
          (SEQ_PBANDI_R_SOGG_PROG_SEDE.NEXTVAL,nProgrSoggettoProgetto,
           vTbIntervento(i).IdSedeInt,vTbIntervento(i).IdIndirizzoInt,2,
           vTbIntervento(i).IdRecapitiInt,recSoggetto.ID_UTENTE_INS);
        END LOOP;
      END IF;
    END IF;

    IF recSoggetto.ID_TIPO_SOGGETTO_CORRELATO IS NOT NULL THEN
      BEGIN
        SELECT PROGR_SOGGETTI_CORRELATI
        INTO   nProgrSoggettiCorrelati
        FROM   PBANDI_R_SOGGETTI_CORRELATI
        WHERE  DT_FINE_VALIDITA           IS NULL
        AND    ID_TIPO_SOGGETTO_CORRELATO = recSoggetto.ID_TIPO_SOGGETTO_CORRELATO
        AND    ID_SOGGETTO                = nIdSoggetto
        AND    ID_SOGGETTO_ENTE_GIURIDICO = nIdSoggettoBeneficiario
        AND    ROWNUM                     = 1;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          INSERT INTO PBANDI_R_SOGGETTI_CORRELATI
          (ID_TIPO_SOGGETTO_CORRELATO, ID_UTENTE_INS,
           ID_SOGGETTO,ID_SOGGETTO_ENTE_GIURIDICO, DT_INIZIO_VALIDITA,
           PROGR_SOGGETTI_CORRELATI)
          VALUES
          (recSoggetto.ID_TIPO_SOGGETTO_CORRELATO,recSoggetto.ID_UTENTE_INS,
           nIdSoggetto,nIdSoggettoBeneficiario ,recSoggetto.DT_INIZIO_VALIDITA,
           SEQ_PBANDI_R_SOGG_CORRELATI.NEXTVAL)
          RETURNING PROGR_SOGGETTI_CORRELATI INTO nProgrSoggettiCorrelati;
      END;

      INSERT INTO PBANDI_R_SOGG_DOM_SOGG_CORREL
      (PROGR_SOGGETTO_DOMANDA, PROGR_SOGGETTI_CORRELATI, ID_UTENTE_INS,DT_INSERIMENTO)
      VALUES
      (nProgrSoggettoDomanda,nProgrSoggettiCorrelati,recSoggetto.ID_UTENTE_INS,SYSDATE);

      INSERT INTO PBANDI_R_SOGG_PROG_SOGG_CORREL
      (PROGR_SOGGETTO_PROGETTO, PROGR_SOGGETTI_CORRELATI, ID_UTENTE_INS,DT_INSERIMENTO)
      VALUES
      (nProgrSoggettoProgetto,nProgrSoggettiCorrelati,recSoggetto.ID_UTENTE_INS,SYSDATE);

      IF recSoggetto.ID_TIPO_SOGGETTO_CORRELATO = 1 THEN
        SELECT COUNT(*)
        INTO   nContAna
        FROM   PBANDI_R_SOGG_TIPO_ANAGRAFICA
        WHERE  ID_SOGGETTO        = nIdSoggetto
        AND    ID_TIPO_ANAGRAFICA = 16;

        IF nContAna = 0 THEN
          INSERT INTO PBANDI_R_SOGG_TIPO_ANAGRAFICA
          (ID_SOGGETTO, ID_TIPO_ANAGRAFICA, ID_UTENTE_INS, FLAG_AGGIORNATO_FLUX,
           DT_INIZIO_VALIDITA)
          VALUES
          (nIdSoggetto,16,recSoggetto.ID_UTENTE_INS,'N',
           recSoggetto.DT_INIZIO_VALIDITA);
        ELSE
          UPDATE PBANDI_R_SOGG_TIPO_ANAGRAFICA
          SET    ID_UTENTE_AGG        = recSoggetto.ID_UTENTE_INS,
                 FLAG_AGGIORNATO_FLUX = 'N'
          WHERE  ID_SOGGETTO          = nIdSoggetto
          AND    ID_TIPO_ANAGRAFICA   = 16;
        END IF;
      END IF;

    END IF;

    FOR recRuoloEnte IN curRuoloEnte(recSoggetto.ID_CSP_SOGGETTO) LOOP
      SELECT COUNT(*)
      INTO   nContSoggRuolo
      FROM   PBANDI_R_SOGG_PROG_RUOLO_ENTE
      WHERE  PROGR_SOGGETTO_PROGETTO  = nProgrSoggettoProgetto
      AND    ID_RUOLO_ENTE_COMPETENZA = recRuoloEnte.ID_RUOLO_ENTE_COMPETENZA;

      IF nContSoggRuolo = 0 THEN
        INSERT INTO PBANDI_R_SOGG_PROG_RUOLO_ENTE
        (PROGR_SOGGETTO_PROGETTO, ID_RUOLO_ENTE_COMPETENZA,
         ID_UTENTE_INS)
        VALUES
        (nProgrSoggettoProgetto,recRuoloEnte.ID_RUOLO_ENTE_COMPETENZA,
         recRuoloEnte.ID_UTENTE_INS);
      END IF;
    END LOOP;
  END LOOP;

  /*UPDATE PBANDI_T_CSP_PROG_FASE_MONIT
  SET    DT_ELABORAZIONE = SYSDATE
  WHERE  ID_CSP_PROGETTO = pIdCspProgetto;*/

  /*UPDATE PBANDI_T_CSP_PROG_INDICATORI
  SET    DT_ELABORAZIONE = SYSDATE
  WHERE  ID_CSP_PROGETTO = pIdCspProgetto;*/

  UPDATE PBANDI_T_CSP_PROG_SEDE_INTERV
  SET    DT_ELABORAZIONE = SYSDATE
  WHERE  ID_CSP_PROGETTO = pIdCspProgetto;

  UPDATE PBANDI_T_CSP_SOGG_RUOLO_ENTE
  SET    DT_ELABORAZIONE = SYSDATE
  WHERE  ID_CSP_SOGGETTO IN (SELECT ID_CSP_SOGGETTO
                             FROM   PBANDI_T_CSP_SOGGETTO
                             WHERE  ID_CSP_PROGETTO = pIdCspProgetto
                             AND    DT_ELABORAZIONE IS NULL);

  UPDATE PBANDI_T_CSP_PROGETTO
  SET    DT_ELABORAZIONE = SYSDATE,
         ID_PROGETTO     = nIdProgetto
  WHERE  ID_CSP_PROGETTO = pIdCspProgetto;

  UPDATE PBANDI_T_CSP_SOGGETTO
  SET    DT_ELABORAZIONE = SYSDATE
  WHERE  ID_CSP_PROGETTO = pIdCspProgetto;
  
  -- mc svil 17032021
  select progr_bando_linea_intervento,
         id_progetto
  into   vProgrBandoLineaInt,
         vIdProgetto
  from   pbandi_t_csp_progetto
  where  ID_CSP_PROGETTO = pIdCspProgetto;  
  
  select id_bando
  into   vIdBando
  from   pbandi_r_bando_linea_intervent
  where  PROGR_BANDO_LINEA_INTERVENTO = vProgrBandoLineaInt;
  
  SELECT SP.ID_SOGGETTO
  INTO   vIdSoggettoBeneficiario
  FROM   PBANDI_R_SOGGETTO_PROGETTO sp
  WHERE  SP.ID_PROGETTO             = vIdProgetto
  AND    sp.ID_TIPO_ANAGRAFICA      = 1
  AND    sp.ID_TIPO_BENEFICIARIO   != 4
  AND    sp.DT_FINE_VALIDITA        IS NULL;
  
  
  select count(*)
  into   vCount
  from   pbandi_r_bando_linea_periodo t
  where  t.progr_bando_linea_intervento = vProgrBandoLineaInt;
  
  For Rec_SoggFinanziat IN
            (select *
             from   pbandi_r_bando_sogg_finanziat t 
             where t.id_bando = vIdBando)
             
             LOOP
               
               Begin
                 select T.COD_IGRUE_T25 
                 into   vCodIgrue
                 from   PBANDI_D_SOGGETTO_FINANZIATORE T
                 where  T.ID_SOGGETTO_FINANZIATORE = rec_SoggFinanziat.ID_SOGGETTO_FINANZIATORE;
               exception when no_data_found then
                 vCodIgrue := NULL;
               end;
               
               INSERT INTO pbandi_r_prog_sogg_finanziat 
               (ID_PROGETTO, 
                ID_SOGGETTO_FINANZIATORE, 
                PERC_QUOTA_SOGG_FINANZIATORE, 
                ID_UTENTE_INS, 
                ID_UTENTE_AGG, 
                ESTREMI_PROVV, 
                PROGR_PROG_SOGG_FINANZIAT, 
                NOTE,
                FLAG_ECONOMIE, --
                ID_NORMA, 
                ID_DELIBERA, 
                ID_COMUNE, 
                ID_PROVINCIA, 
                ID_PERIODO, 
                ID_SOGGETTO, 
                DT_INSERIMENTO, 
                DT_AGGIORNAMENTO, 
                IMP_QUOTA_SOGG_FINANZIATORE
                )
               VALUES
                (
                 vIdProgetto,
                 Rec_SoggFinanziat.ID_SOGGETTO_FINANZIATORE,
                 NULL,
                 recCspProgetto.ID_UTENTE_INS,
                 NULL,
                 NULL,
                 seq_pbandi_r_prog_sogg_finanzi.nextval,
                 NULL,
                 'N',--
                 NULL,
                 NULL,
                 NULL,
                 NULL,
                 DECODE(vCount,0,23,11),              
                 DECODE(vCodIgrue,7,vIdSoggettoBeneficiario, NULL), -- dove prendo il soggetto beneficiario ???
                 SYSDATE,
                 NULL,
                 NULL
                 );
                 
              END LOOP;
  
  --

  PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,
                                            'OK');

  RETURN 0;
EXCEPTION
  WHEN OTHERS THEN
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,
                                              'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E101','pIdCspProgetto = '||pIdCspProgetto||
                                         ' ERRORE = '||SQLERRM||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
    RETURN 1;
END CaricamentoSchedaProgetto;

/************************************************************************
  ReturnIndirSedeIntervento : ritorna l'indirizzo della sede intervento

  Parametri input:  pIdDettPropostaCertificaz - Id del dettaglio della proposta di certificazione
*************************************************************************/
FUNCTION ReturnIndirSedeIntervento(pIdDettPropostaCertificaz  PBANDI_T_DETT_PROPOSTA_CERTIF.ID_DETT_PROPOSTA_CERTIF%TYPE) RETURN VARCHAR2 IS

  CURSOR curIndir IS SELECT IND.ID_COMUNE,IND.ID_COMUNE_ESTERO,IND.ID_PROVINCIA,
                            IND.ID_REGIONE,IND.ID_NAZIONE
                     FROM   pbandi_t_indirizzo ind,pbandi_r_det_prop_ind_sede_int dpisi
                     WHERE  DPISI.ID_DETT_PROPOSTA_CERTIF = pIdDettPropostaCertificaz
                     and    ind.id_indirizzo = dpisi.id_indirizzo;


  vIndSedeIntervento  VARCHAR2(4000) := NULL;
  vDescComune         VARCHAR2(4000) := NULL;
  vSiglaProv          VARCHAR2(4000) := NULL;
  vComuni             VARCHAR2(4000) := NULL;
  vProvincie          VARCHAR2(4000) := NULL;

  TYPE recComuni IS RECORD (descComune  VARCHAR2(4000),
                            siglaProv   VARCHAR2(4000));

  TYPE recTbComuni IS TABLE OF recComuni INDEX BY PLS_INTEGER;

  vTbComuni  recTbComuni;
BEGIN
  FOR recIndir IN curIndir LOOP
    IF recIndir.ID_COMUNE IS NOT NULL THEN
      SELECT DESC_COMUNE,SIGLA_PROVINCIA
      INTO   vDescComune,vSiglaProv
      FROM   PBANDI_D_COMUNE C,PBANDI_D_PROVINCIA P
      WHERE  C.ID_COMUNE = recIndir.ID_COMUNE
      AND    C.ID_PROVINCIA = P.ID_PROVINCIA;
    ELSE
      IF recIndir.ID_COMUNE_ESTERO IS NOT NULL THEN
        SELECT DESC_COMUNE_ESTERO,DESC_NAZIONE
        INTO   vDescComune,vSiglaProv
        FROM   PBANDI_D_COMUNE_ESTERO CE,PBANDI_D_NAZIONE N
        WHERE  CE.ID_COMUNE_ESTERO = recIndir.ID_COMUNE_ESTERO
        AND    CE.ID_NAZIONE       = N.ID_NAZIONE;
      ELSE
        IF recIndir.ID_PROVINCIA IS NOT NULL THEN
          SELECT NULL,SIGLA_PROVINCIA
          INTO   vDescComune,vSiglaProv
          FROM   PBANDI_D_PROVINCIA P
          WHERE  P.ID_PROVINCIA = recIndir.ID_PROVINCIA;
        ELSE
          IF recIndir.ID_REGIONE IS NOT NULL THEN
            SELECT NULL,DESC_REGIONE
            INTO   vDescComune,vSiglaProv
            FROM   PBANDI_D_REGIONE R
            WHERE  R.ID_REGIONE = recIndir.ID_REGIONE;
          ELSE
            IF recIndir.ID_NAZIONE IS NOT NULL THEN
              SELECT NULL,DESC_NAZIONE
              INTO   vDescComune,vSiglaProv
              FROM   PBANDI_D_NAZIONE N
              WHERE  N.ID_NAZIONE = recIndir.ID_NAZIONE;
            ELSE
              vDescComune := NULL;
              vSiglaProv  := NULL;
            END IF;
          END IF;
        END IF;
      END IF;
    END IF;

    vTbComuni(curIndir%ROWCOUNT).descComune := vDescComune;
    vTbComuni(curIndir%ROWCOUNT).siglaProv  := vSiglaProv;

  END LOOP;

  FOR i IN vTbComuni.FIRST..vTbComuni.LAST LOOP
    IF i = 1 THEN
      vComuni := vTbComuni(i).descComune;
    ELSE
      vComuni := vComuni||','||vTbComuni(i).descComune;
    END IF;
  END LOOP;

  FOR j IN vTbComuni.FIRST..vTbComuni.LAST LOOP
    IF j = 1 THEN
      vProvincie := vTbComuni(j).siglaProv;
    ELSE
      vProvincie := vProvincie||','||vTbComuni(j).siglaProv;
    END IF;
  END LOOP;

  vIndSedeIntervento := vComuni||';'||vProvincie;

  RETURN vIndSedeIntervento;
EXCEPTION
  WHEN OTHERS THEN
    RETURN NULL;
END ReturnIndirSedeIntervento;

/************************************************************************
  AttivitaPregresse : funzione per la gestione delle attività pregresse

  Parametri input: pIdProgetto - Id del progetto
 *************************************************************************/
FUNCTION AttivitaPregresse(pIdProgetto  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE) RETURN NUMBER IS

  nIdAttivitaPregresse  PBANDI_W_ATTIVITA_PREGRESSE.ID_ATTIVITA_PREGRESSE%TYPE;
  nCont37               PLS_INTEGER := 0;
BEGIN
  SELECT SEQ_PBANDI_W_ATTIVITA_PREGRESS.NEXTVAL
  INTO   nIdAttivitaPregresse
  FROM   DUAL;

  -- Ammissione Progetto
  INSERT INTO PBANDI_W_ATTIVITA_PREGRESSE
  (ID_ATTIVITA_PREGRESSE, ID_PROGETTO, COD_ELEMENTO, DESC_ELEMENTO, DATA, DT_INSERIMENTO)
  (SELECT nIdAttivitaPregresse,pIdProgetto,'A','Ammissione Progetto',NVL(DT_CONCESSIONE,DT_COMITATO),SYSDATE
   FROM   PBANDI_T_PROGETTO
   WHERE  ID_PROGETTO = pIdProgetto);

  -- Conto economico main
  INSERT INTO PBANDI_W_ATTIVITA_PREGRESSE
  (ID_ATTIVITA_PREGRESSE, ID_PROGETTO, COD_ELEMENTO, DESC_ELEMENTO, DATA, DT_INSERIMENTO,
   ID_CONTO_ECONOMICO, ID_DOMANDA, IMPORTO_AMMESSO,
   IMPORTO_AGEVOLATO, IMPORTO_IMPEGNO)
  (SELECT nIdAttivitaPregresse,pIdProgetto,'B','Conto economico'||' '||DESC_STATO_CONTO_ECONOMICO,CE.DT_INIZIO_VALIDITA,SYSDATE,
          CE.ID_CONTO_ECONOMICO,D.ID_DOMANDA,
          (SELECT NVL(SUM(rce.IMPORTO_AMMESSO_FINANZIAMENTO),0)
           FROM   PBANDI_T_RIGO_CONTO_ECONOMICO rce
           WHERE  rce.ID_CONTO_ECONOMICO = CE.ID_CONTO_ECONOMICO
           AND    rce.DT_FINE_VALIDITA   IS NULL
		   AND    rce.ID_VOCE_DI_SPESA IS NOT NULL
           GROUP BY RCE.ID_CONTO_ECONOMICO),
          NVL(SUM(cema.QUOTA_IMPORTO_AGEVOLATO),0),NVL(IMPORTO_IMPEGNO_VINCOLANTE,0)
   FROM   PBANDI_T_PROGETTO P,PBANDI_T_DOMANDA D,PBANDI_T_CONTO_ECONOMICO ce,PBANDI_D_STATO_CONTO_ECONOMICO sce,
          PBANDI_D_TIPOLOGIA_CONTO_ECON tce ,PBANDI_R_CONTO_ECONOM_MOD_AGEV cema
   WHERE  P.ID_PROGETTO                             = pIdProgetto
   AND    P.ID_DOMANDA                              = D.ID_DOMANDA
   AND    CE.ID_DOMANDA                             = D.ID_DOMANDA
   AND    CE.ID_STATO_CONTO_ECONOMICO               = SCE.ID_STATO_CONTO_ECONOMICO
   AND    SCE.ID_TIPOLOGIA_CONTO_ECONOMICO          = TCE.ID_TIPOLOGIA_CONTO_ECONOMICO
   AND    UPPER(TCE.DESC_BREVE_TIPOLOGIA_CONTO_ECO) = 'MAIN'
   AND    cema.ID_CONTO_ECONOMICO(+)                = ce.ID_CONTO_ECONOMICO
   GROUP BY CE.ID_CONTO_ECONOMICO,CE.DT_INIZIO_VALIDITA,D.ID_DOMANDA,DESC_STATO_CONTO_ECONOMICO,IMPORTO_IMPEGNO_VINCOLANTE);

  -- Rimodulazione Conto economico
  INSERT INTO PBANDI_W_ATTIVITA_PREGRESSE
  (ID_ATTIVITA_PREGRESSE, ID_PROGETTO, COD_ELEMENTO,
   DESC_ELEMENTO, DATA, DT_INSERIMENTO,
   ID_CONTO_ECONOMICO, ID_DOMANDA, IMPORTO_AMMESSO,
   IMPORTO_AGEVOLATO, IMPORTO_IMPEGNO,RIBASSO_ASTA)
  (SELECT nIdAttivitaPregresse,pIdProgetto,'C',
          'Rimodulazione del conto economico eseguita dall''utente '||PCK_PBANDI_UTILITY_BATCH.ReturnNomeCognomeUtente(CE.ID_UTENTE_INS),
          CE.DT_INIZIO_VALIDITA,SYSDATE,
          CE.ID_CONTO_ECONOMICO,D.ID_DOMANDA,
          (SELECT NVL(SUM(rce.IMPORTO_AMMESSO_FINANZIAMENTO),0)
           FROM   PBANDI_T_RIGO_CONTO_ECONOMICO rce
           WHERE  rce.ID_CONTO_ECONOMICO = CE.ID_CONTO_ECONOMICO
           AND    rce.DT_FINE_VALIDITA   IS NULL
		   AND    rce.ID_VOCE_DI_SPESA IS NOT NULL
           GROUP BY RCE.ID_CONTO_ECONOMICO),
          NVL(SUM(cema.QUOTA_IMPORTO_AGEVOLATO),0),NVL(IMPORTO_IMPEGNO_VINCOLANTE,0),
          (SELECT ' di '||ABS(NVL(IMPORTO,0))||' pari al '||ABS(NVL(PERCENTUALE,0))||'% dell''ammesso'
           FROM   PBANDI_T_RIBASSO_ASTA
           WHERE  ID_CONTO_ECONOMICO = CE.ID_CONTO_ECONOMICO)
   FROM   PBANDI_T_PROGETTO P,PBANDI_T_DOMANDA D,PBANDI_T_CONTO_ECONOMICO ce,PBANDI_D_STATO_CONTO_ECONOMICO sce,
          PBANDI_R_CONTO_ECONOM_MOD_AGEV cema
   WHERE  P.ID_PROGETTO                     = pIdProgetto
   AND    P.ID_DOMANDA                      = D.ID_DOMANDA
   AND    CE.ID_DOMANDA                     = D.ID_DOMANDA
   AND    CE.ID_STATO_CONTO_ECONOMICO       = SCE.ID_STATO_CONTO_ECONOMICO
   AND    SCE.DESC_BREVE_STATO_CONTO_ECONOM = 'NRC'
   AND    cema.ID_CONTO_ECONOMICO(+)        = ce.ID_CONTO_ECONOMICO
   GROUP BY CE.DT_INIZIO_VALIDITA,CE.ID_CONTO_ECONOMICO,D.ID_DOMANDA,IMPORTO_IMPEGNO_VINCOLANTE,CE.ID_UTENTE_INS);

  -- Proposta di Rimodulazione Conto economico
  INSERT INTO PBANDI_W_ATTIVITA_PREGRESSE
  (ID_ATTIVITA_PREGRESSE, ID_PROGETTO, COD_ELEMENTO,
   DESC_ELEMENTO, DATA, DT_INSERIMENTO,
   ID_CONTO_ECONOMICO, ID_DOMANDA, IMPORTO_AMMESSO,
   IMPORTO_AGEVOLATO, RIBASSO_ASTA)
  (SELECT nIdAttivitaPregresse,pIdProgetto,'D',
          'Proposta di Rimodulazione del conto economico eseguita dall''utente '||PCK_PBANDI_UTILITY_BATCH.ReturnNomeCognomeUtente(CE.ID_UTENTE_INS),
          CE.DT_INIZIO_VALIDITA,SYSDATE,
          CE.ID_CONTO_ECONOMICO,D.ID_DOMANDA,
          (SELECT NVL(SUM(rce.IMPORTO_AMMESSO_FINANZIAMENTO),0)
           FROM   PBANDI_T_RIGO_CONTO_ECONOMICO rce
           WHERE  rce.ID_CONTO_ECONOMICO = CE.ID_CONTO_ECONOMICO
           AND    rce.DT_FINE_VALIDITA   IS NULL
		   AND    rce.ID_VOCE_DI_SPESA IS NOT NULL
           GROUP BY RCE.ID_CONTO_ECONOMICO),
          NVL(SUM(cema.QUOTA_IMPORTO_RICHIESTO),0),
          (SELECT ' di '||ABS(NVL(IMPORTO,0))||' pari al '||ABS(NVL(PERCENTUALE,0))||'% dell''ammesso'
           FROM   PBANDI_T_RIBASSO_ASTA
           WHERE  ID_CONTO_ECONOMICO = CE.ID_CONTO_ECONOMICO)
   FROM   PBANDI_T_PROGETTO P,PBANDI_T_DOMANDA D,PBANDI_T_CONTO_ECONOMICO ce,PBANDI_D_STATO_CONTO_ECONOMICO sce,
          PBANDI_R_CONTO_ECONOM_MOD_AGEV cema
   WHERE  P.ID_PROGETTO                     = pIdProgetto
   AND    P.ID_DOMANDA                      = D.ID_DOMANDA
   AND    CE.ID_DOMANDA                     = D.ID_DOMANDA
   AND    CE.ID_STATO_CONTO_ECONOMICO       = SCE.ID_STATO_CONTO_ECONOMICO
   AND    SCE.DESC_BREVE_STATO_CONTO_ECONOM = 'NPI'
   AND    cema.ID_CONTO_ECONOMICO(+)        = ce.ID_CONTO_ECONOMICO
   GROUP BY CE.DT_INIZIO_VALIDITA,CE.ID_CONTO_ECONOMICO,D.ID_DOMANDA,CE.ID_UTENTE_INS);

  -- Caricamento Indicatori di Avvio
  INSERT INTO PBANDI_W_ATTIVITA_PREGRESSE
  (ID_ATTIVITA_PREGRESSE, ID_PROGETTO, COD_ELEMENTO,
   DESC_ELEMENTO,
   DATA,
   DT_INSERIMENTO,ID_DOMANDA)
  (SELECT DISTINCT  nIdAttivitaPregresse,pIdProgetto,'E',
          'Caricamento Indicatori di Avvio da parte di '||PCK_PBANDI_UTILITY_BATCH.ReturnNomeCognomeUtente(DI.ID_UTENTE_INS),
          FIRST_VALUE(DI.DT_INSERIMENTO) OVER (ORDER BY DI.DT_INSERIMENTO ASC),
          SYSDATE,DI.ID_DOMANDA
   FROM   PBANDI_T_PROGETTO P,PBANDI_T_DOMANDA D,PBANDI_R_DOMANDA_INDICATORI DI
   WHERE  P.ID_PROGETTO       = pIdProgetto
   AND    P.ID_DOMANDA        = D.ID_DOMANDA
   AND    DI.ID_DOMANDA       = D.ID_DOMANDA
   AND    DI.DT_AGGIORNAMENTO IS NOT NULL
   AND    ROWNUM              = 1);

  -- Caricamento Cronoprogramma di Avvio
  INSERT INTO PBANDI_W_ATTIVITA_PREGRESSE
  (ID_ATTIVITA_PREGRESSE, ID_PROGETTO, COD_ELEMENTO,
   DESC_ELEMENTO,
   DATA,
   DT_INSERIMENTO,ID_DOMANDA)
  (SELECT DISTINCT  nIdAttivitaPregresse,pIdProgetto,'F','Caricamento Cronoprogramma di Avvio',
          FIRST_VALUE(PFM.DT_INSERIMENTO) OVER (ORDER BY PFM.DT_INSERIMENTO ASC),
          SYSDATE,P.ID_DOMANDA
   FROM   PBANDI_T_PROGETTO P,PBANDI_R_PROGETTO_FASE_MONIT pfm
   WHERE  P.ID_PROGETTO   = pIdProgetto
   AND    PFM.ID_PROGETTO = P.ID_PROGETTO
   AND    ROWNUM          = 1);

  -- Dichiarazione di spesa
  INSERT INTO PBANDI_W_ATTIVITA_PREGRESSE
  (ID_ATTIVITA_PREGRESSE, ID_PROGETTO, COD_ELEMENTO,
   DESC_ELEMENTO,
   DATA,DT_INSERIMENTO,ID_DOMANDA,
   PERIODI,
   IMPORTO_RENDICONTATO,
   IMPORTO_QUIETANZATO,
   ID_DOCUMENTO_INDEX)
  (SELECT nIdAttivitaPregresse,pIdProgetto,'G',
          'Dichiarazione di spesa '||TDS.DESC_TIPO_DICHIARAZIONE_SPESA||' n° '||DS.ID_DICHIARAZIONE_SPESA||' presentata dall''utente '||
          PCK_PBANDI_UTILITY_BATCH.ReturnNomeCognomeUtente(DS.ID_UTENTE_INS),
          DS.DT_DICHIARAZIONE,SYSDATE,P.ID_DOMANDA,
          ' dal '||TO_CHAR(DS.PERIODO_DAL,'DD/MM/YYYY')||' al '||TO_CHAR(DS.PERIODO_AL,'DD/MM/YYYY'),
          --IMPORTO_RENDICONTATO
          (SELECT NVL(SUM(QPDS.IMPORTO_QUOTA_PARTE_DOC_SPESA),0)
           FROM  PBANDI_T_QUOTA_PARTE_DOC_SPESA QPDS
           WHERE  QPDS.ID_PROGETTO = DS.ID_PROGETTO ),
		   --IMPORTO_QUIETANZATO
          (SELECT NVL(SUM(PQPDS.IMPORTO_QUIETANZATO),0)
           FROM  PBANDI_R_PAG_QUOT_PARTE_DOC_SP PQPDS,
                      PBANDI_T_QUOTA_PARTE_DOC_SPESA QPDS,
                      PBANDI_S_DICH_DOC_SPESA DDS
           WHERE  DDS.ID_DICHIARAZIONE_SPESA     = DS.ID_DICHIARAZIONE_SPESA
           AND PQPDS.ID_QUOTA_PARTE_DOC_SPESA = QPDS.ID_QUOTA_PARTE_DOC_SPESA
		   AND PQPDS.ID_DICHIARAZIONE_SPESA = DDS.ID_DICHIARAZIONE_SPESA
           AND QPDS.ID_DOCUMENTO_DI_SPESA = DDS.ID_DOCUMENTO_DI_SPESA
           AND QPDS.ID_PROGETTO = DS.ID_PROGETTO ),
           --ID_DOCUMENTO_INDEX
          (SELECT DI.ID_DOCUMENTO_INDEX
           FROM   PBANDI_T_DOCUMENTO_INDEX di,PBANDI_C_ENTITA E
           WHERE  DI.ID_ENTITA  = E.ID_ENTITA
           AND    E.NOME_ENTITA = 'PBANDI_T_DICHIARAZIONE_SPESA'
           AND    DI.ID_TARGET  = DS.ID_DICHIARAZIONE_SPESA
           AND    ID_TIPO_DOCUMENTO_INDEX = 1)
   FROM   PBANDI_T_PROGETTO P,PBANDI_T_DICHIARAZIONE_SPESA DS,PBANDI_D_TIPO_DICHIARAZ_SPESA TDS
   WHERE  P.ID_PROGETTO                      = pIdProgetto
   AND    P.ID_PROGETTO                      = DS.ID_PROGETTO
   AND    DS.ID_TIPO_DICHIARAZ_SPESA         = TDS.ID_TIPO_DICHIARAZ_SPESA
   AND    TDS.DESC_BREVE_TIPO_DICHIARA_SPESA IN ('I','IN'));

  -- Validazione dichiarazione di spesa
  INSERT INTO PBANDI_W_ATTIVITA_PREGRESSE
  (ID_ATTIVITA_PREGRESSE, ID_PROGETTO, COD_ELEMENTO,
   DESC_ELEMENTO,
   DATA,DT_INSERIMENTO,ID_DOMANDA,
   PERIODI,
   IMPORTO_RENDICONTATO,
   IMPORTO_QUIETANZATO,
   IMPORTO_VALIDATO, NOTE,ID_DOCUMENTO_INDEX)
  (SELECT nIdAttivitaPregresse,pIdProgetto,'H',
          'Validazione della Dichiarazione di spesa '||TDS.DESC_TIPO_DICHIARAZIONE_SPESA||' n° '||DS.ID_DICHIARAZIONE_SPESA||
          ' validata dall''utente '||
          PCK_PBANDI_UTILITY_BATCH.ReturnNomeCognomeUtente(DS.ID_UTENTE_AGG),
          DS.DT_CHIUSURA_VALIDAZIONE,SYSDATE,P.ID_DOMANDA,
          ' dal '||TO_CHAR(DS.PERIODO_DAL,'DD/MM/YYYY')||' al '||TO_CHAR(DS.PERIODO_AL,'DD/MM/YYYY'),
		  --IMPORTO_RENDICONTATO
          (SELECT NVL(SUM(QPDS.IMPORTO_QUOTA_PARTE_DOC_SPESA),0)
           FROM  PBANDI_T_QUOTA_PARTE_DOC_SPESA QPDS
           WHERE  QPDS.ID_PROGETTO = DS.ID_PROGETTO ),
           --IMPORTO_QUIETANZATO
          (SELECT NVL(SUM(PQPDS.IMPORTO_QUIETANZATO),0)
           FROM  PBANDI_R_PAG_QUOT_PARTE_DOC_SP PQPDS,
                      PBANDI_T_QUOTA_PARTE_DOC_SPESA QPDS,
                      PBANDI_S_DICH_DOC_SPESA DDS
           WHERE  DDS.ID_DICHIARAZIONE_SPESA     = DS.ID_DICHIARAZIONE_SPESA
           AND PQPDS.ID_QUOTA_PARTE_DOC_SPESA = QPDS.ID_QUOTA_PARTE_DOC_SPESA
		   AND PQPDS.ID_DICHIARAZIONE_SPESA = DDS.ID_DICHIARAZIONE_SPESA
           AND QPDS.ID_DOCUMENTO_DI_SPESA = DDS.ID_DOCUMENTO_DI_SPESA
           AND QPDS.ID_PROGETTO = DS.ID_PROGETTO ),
          --IMPORTO_VALIDATO
          (SELECT NVL(SUM(PQPDS.IMPORTO_VALIDATO),0)
           FROM  PBANDI_R_PAG_QUOT_PARTE_DOC_SP PQPDS,
                      PBANDI_T_QUOTA_PARTE_DOC_SPESA QPDS,
                      PBANDI_S_DICH_DOC_SPESA DDS
           WHERE  DDS.ID_DICHIARAZIONE_SPESA     = DS.ID_DICHIARAZIONE_SPESA
           AND PQPDS.ID_QUOTA_PARTE_DOC_SPESA = QPDS.ID_QUOTA_PARTE_DOC_SPESA
		   AND PQPDS.ID_DICHIARAZIONE_SPESA = DDS.ID_DICHIARAZIONE_SPESA
           AND  QPDS.ID_DOCUMENTO_DI_SPESA = DDS.ID_DOCUMENTO_DI_SPESA
           AND QPDS.ID_PROGETTO = DS.ID_PROGETTO ),
		   DS.NOTE_CHIUSURA_VALIDAZIONE,
		   --ID_DOCUMENTO_INDEX
          (SELECT DI.ID_DOCUMENTO_INDEX
           FROM   PBANDI_T_DOCUMENTO_INDEX di,PBANDI_C_ENTITA E
           WHERE  DI.ID_ENTITA  = E.ID_ENTITA
           AND    E.NOME_ENTITA = 'PBANDI_T_DICHIARAZIONE_SPESA'
           AND    DI.ID_TARGET  = DS.ID_DICHIARAZIONE_SPESA
           AND    ID_TIPO_DOCUMENTO_INDEX = 2)
   FROM   PBANDI_T_PROGETTO P,PBANDI_T_DICHIARAZIONE_SPESA DS,PBANDI_D_TIPO_DICHIARAZ_SPESA TDS
   WHERE  P.ID_PROGETTO              = pIdProgetto
   AND    P.ID_PROGETTO              = DS.ID_PROGETTO
   AND    DS.DT_CHIUSURA_VALIDAZIONE IS NOT NULL
   AND    DS.ID_TIPO_DICHIARAZ_SPESA = TDS.ID_TIPO_DICHIARAZ_SPESA);

  -- Rinuncia
  INSERT INTO PBANDI_W_ATTIVITA_PREGRESSE
  (ID_ATTIVITA_PREGRESSE, ID_PROGETTO, COD_ELEMENTO,
   DESC_ELEMENTO,
   DATA,DT_INSERIMENTO,ID_DOMANDA,MOTIVO,ID_DOCUMENTO_INDEX)
  (SELECT nIdAttivitaPregresse,pIdProgetto,'I',
          'L''utente '||PCK_PBANDI_UTILITY_BATCH.ReturnNomeCognomeUtente(R.ID_UTENTE_INS)||' rinuncia al progetto e si impegna a restituire '||
          NVL(DECODE(R.IMPORTO_DA_RESTITUIRE,0,'0,00',TO_CHAR(R.IMPORTO_DA_RESTITUIRE,'9G999G999D99')),0)||' entro '||R.GIORNI_RINUNCIA||' giorni',
          R.DT_RINUNCIA,SYSDATE,P.ID_DOMANDA,R.MOTIVO_RINUNCIA,
          (SELECT DI.ID_DOCUMENTO_INDEX
           FROM   PBANDI_T_DOCUMENTO_INDEX di,PBANDI_C_ENTITA E
           WHERE  DI.ID_ENTITA  = E.ID_ENTITA
           AND    E.NOME_ENTITA = 'PBANDI_T_RINUNCIA'
           AND    DI.ID_TARGET  = R.ID_RINUNCIA)
   FROM   PBANDI_T_PROGETTO P,PBANDI_T_RINUNCIA R
   WHERE  P.ID_PROGETTO   = pIdProgetto
   AND    P.ID_PROGETTO   = R.ID_PROGETTO);

  -- Soppressione
  INSERT INTO PBANDI_W_ATTIVITA_PREGRESSE
  (ID_ATTIVITA_PREGRESSE, ID_PROGETTO, COD_ELEMENTO,
   DESC_ELEMENTO,
   DATA,DT_INSERIMENTO,ID_DOMANDA,NOTE)
  (SELECT nIdAttivitaPregresse,pIdProgetto,'J',
          'Soppressione eseguita da '||PCK_PBANDI_UTILITY_BATCH.ReturnNomeCognomeUtente(R.ID_UTENTE_INS)||' per l''importo di '||NVL(DECODE(R.IMPORTO_RECUPERO,0,'0,00',TO_CHAR(R.IMPORTO_RECUPERO,'9G999G999D99')),0),
          R.DT_RECUPERO,SYSDATE,P.ID_DOMANDA,R.NOTE
   FROM   PBANDI_T_PROGETTO P,PBANDI_T_RECUPERO R
   WHERE  P.ID_PROGETTO   = pIdProgetto
   AND    P.ID_PROGETTO   = R.ID_PROGETTO);

  -- Revoca
  INSERT INTO PBANDI_W_ATTIVITA_PREGRESSE
  (ID_ATTIVITA_PREGRESSE, ID_PROGETTO, COD_ELEMENTO,
   DESC_ELEMENTO,
   DATA,DT_INSERIMENTO,ID_DOMANDA,MOTIVO,NOTE)
  (SELECT nIdAttivitaPregresse,pIdProgetto,'K',
          'L''utente '||PCK_PBANDI_UTILITY_BATCH.ReturnNomeCognomeUtente(R.ID_UTENTE_INS)||' ha revocato l''importo di '||NVL(DECODE(R.IMPORTO,0,'0,00',TO_CHAR(R.IMPORTO,'9G999G999D99')),0)||' come '||MA.DESC_MODALITA_AGEVOLAZIONE,
          R.DT_REVOCA,SYSDATE,P.ID_DOMANDA,MR.DESC_MOTIVO_REVOCA,R.NOTE
   FROM   PBANDI_T_PROGETTO P,PBANDI_T_REVOCA R,
          PBANDI_D_MODALITA_AGEVOLAZIONE MA,PBANDI_D_MOTIVO_REVOCA MR
   WHERE  P.ID_PROGETTO              = pIdProgetto
   AND    P.ID_PROGETTO              = R.ID_PROGETTO
   AND    R.ID_MODALITA_AGEVOLAZIONE = MA.ID_MODALITA_AGEVOLAZIONE
   AND    R.ID_MOTIVO_REVOCA         = MR.ID_MOTIVO_REVOCA(+));

  -- Recupero
  INSERT INTO PBANDI_W_ATTIVITA_PREGRESSE
  (ID_ATTIVITA_PREGRESSE, ID_PROGETTO, COD_ELEMENTO,
   DESC_ELEMENTO,
   DATA,DT_INSERIMENTO,ID_DOMANDA,NOTE)
  (SELECT nIdAttivitaPregresse,pIdProgetto,'I',
          TR.DESC_TIPO_RECUPERO||' di '||NVL(DECODE(R.IMPORTO_RECUPERO,0,'0,00',TO_CHAR(R.IMPORTO_RECUPERO,'9G999G999D99')),0)||' come '||MA.DESC_MODALITA_AGEVOLAZIONE,
          R.DT_RECUPERO,SYSDATE,P.ID_DOMANDA,R.NOTE
   FROM   PBANDI_T_PROGETTO P,PBANDI_T_RECUPERO R,PBANDI_D_TIPO_RECUPERO TR,PBANDI_D_MODALITA_AGEVOLAZIONE MA
   WHERE  P.ID_PROGETTO                = pIdProgetto
   AND    P.ID_PROGETTO                = R.ID_PROGETTO
   AND    R.ID_TIPO_RECUPERO           = TR.ID_TIPO_RECUPERO
   AND    TR.DESC_BREVE_TIPO_RECUPERO != 'SO'
   AND    R.ID_MODALITA_AGEVOLAZIONE   = MA.ID_MODALITA_AGEVOLAZIONE(+));


  SELECT COUNT(*)
  INTO   nCont37
  FROM   PBANDI_R_REGOLA_BANDO_LINEA rbl,PBANDI_C_REGOLA r,PBANDI_T_DOMANDA dOM,PBANDI_T_PROGETTO P
  WHERE  P.ID_PROGETTO                    = pIdProgetto
  AND    DOM.ID_DOMANDA                   = P.id_domanda
  AND    RBL.PROGR_BANDO_LINEA_INTERVENTO = DOM.PROGR_BANDO_LINEA_INTERVENTO
  AND    RBL.ID_REGOLA                    = R.ID_REGOLA
  AND    R.DESC_BREVE_REGOLA              = 'BR37';

  IF nCont37 = 0 THEN
    -- Erogazione
    INSERT INTO PBANDI_W_ATTIVITA_PREGRESSE
    (ID_ATTIVITA_PREGRESSE, ID_PROGETTO, COD_ELEMENTO,
     DESC_ELEMENTO,
     DATA,DT_INSERIMENTO,ID_DOMANDA,MODALITA,NOTE,CAUSALE, ENTE)
    (SELECT nIdAttivitaPregresse,pIdProgetto,'L',
            'Erogazione '||E.COD_RIFERIMENTO_EROGAZIONE||' di '||NVL(DECODE(E.IMPORTO_EROGAZIONE,0,'0,00',TO_CHAR(E.IMPORTO_EROGAZIONE,'9G999G999D99')),0)||' come '||MA.DESC_MODALITA_AGEVOLAZIONE,
            E.DT_CONTABILE,SYSDATE,P.ID_DOMANDA,ME.DESC_MODALITA_EROGAZIONE,E.NOTE_EROGAZIONE,CE.DESC_CAUSALE,EC.DESC_ENTE
     FROM   PBANDI_T_PROGETTO P,PBANDI_T_EROGAZIONE E,PBANDI_D_MODALITA_AGEVOLAZIONE MA,PBANDI_D_MODALITA_EROGAZIONE ME,
            PBANDI_D_CAUSALE_EROGAZIONE CE,PBANDI_T_ENTE_COMPETENZA EC
     WHERE  P.ID_PROGETTO              = pIdProgetto
     AND    P.ID_PROGETTO              = E.ID_PROGETTO
     AND    E.ID_MODALITA_AGEVOLAZIONE = MA.ID_MODALITA_AGEVOLAZIONE
     AND    E.ID_MODALITA_EROGAZIONE   = ME.ID_MODALITA_EROGAZIONE
     AND    E.ID_CAUSALE_EROGAZIONE    = CE.ID_CAUSALE_EROGAZIONE
     AND    E.ID_ENTE_COMPETENZA       = EC.ID_ENTE_COMPETENZA(+));
  END IF;

  -- Controllo in loco
  INSERT INTO PBANDI_W_ATTIVITA_PREGRESSE
  (ID_ATTIVITA_PREGRESSE, ID_PROGETTO, COD_ELEMENTO,
   DESC_ELEMENTO,
   DATA,DT_INSERIMENTO,ID_DOMANDA,ID_DOCUMENTO_INDEX)
  (SELECT nIdAttivitaPregresse,pIdProgetto,'M',
          'Controllo in loco da parte di '||C.SOGGETTO_CONTROLLORE,C.DT_CONTROLLO,SYSDATE,P.ID_DOMANDA,DI.ID_DOCUMENTO_INDEX
   FROM   PBANDI_T_PROGETTO P,PBANDI_T_DOCUMENTO_INDEX DI,PBANDI_C_TIPO_DOCUMENTO_INDEX TDI,PBANDI_T_CHECKLIST C
   WHERE  P.ID_PROGETTO                 = pIdProgetto
   AND    P.ID_PROGETTO                 = DI.ID_PROGETTO
   AND    DI.ID_TIPO_DOCUMENTO_INDEX    = TDI.ID_TIPO_DOCUMENTO_INDEX
   AND    TDI.DESC_BREVE_TIPO_DOC_INDEX = 'CLIL'
   AND    C.ID_DOCUMENTO_INDEX          = DI.ID_DOCUMENTO_INDEX);

  -- Comunicazione fine progetto
  INSERT INTO PBANDI_W_ATTIVITA_PREGRESSE
  (ID_ATTIVITA_PREGRESSE, ID_PROGETTO, COD_ELEMENTO,
   DESC_ELEMENTO,DATA,DT_INSERIMENTO,ID_DOMANDA,
   IMPORTO_RENDICONTATO,
   IMPORTO_QUIETANZATO, NOTE,
   IMPORTO_SALDO,ID_DOCUMENTO_INDEX)
  (SELECT nIdAttivitaPregresse,pIdProgetto,'N',
          'Comunicazione di fine progetto riferita alla dich. finale n° '||DS.ID_DICHIARAZIONE_SPESA||
          ' presentata dall''utente '||PCK_PBANDI_UTILITY_BATCH.ReturnNomeCognomeUtente(CFP.ID_UTENTE_INS),CFP.DT_COMUNICAZIONE,SYSDATE,P.ID_DOMANDA,
          --IMPORTO_RENDICONTATO
          (SELECT NVL(SUM(QPDS.IMPORTO_QUOTA_PARTE_DOC_SPESA),0)
           FROM  PBANDI_T_QUOTA_PARTE_DOC_SPESA QPDS
           WHERE  QPDS.ID_PROGETTO = DS.ID_PROGETTO ),
          --IMPORTO_QUIETANZATO
          (SELECT NVL(SUM(PQPDS.IMPORTO_QUIETANZATO),0)
           FROM  PBANDI_R_PAG_QUOT_PARTE_DOC_SP PQPDS,
                      PBANDI_T_QUOTA_PARTE_DOC_SPESA QPDS,
                      PBANDI_S_DICH_DOC_SPESA DDS
           WHERE  DDS.ID_DICHIARAZIONE_SPESA     = DS.ID_DICHIARAZIONE_SPESA
           AND PQPDS.ID_QUOTA_PARTE_DOC_SPESA = QPDS.ID_QUOTA_PARTE_DOC_SPESA
		   AND PQPDS.ID_DICHIARAZIONE_SPESA = DDS.ID_DICHIARAZIONE_SPESA
           AND QPDS.ID_DOCUMENTO_DI_SPESA = DDS.ID_DOCUMENTO_DI_SPESA
           AND QPDS.ID_PROGETTO = DS.ID_PROGETTO ),

		   CFP.NOTE_COMUNICAZ_FINE_PROGETTO,
          NVL(CFP.IMPORTO_RICH_EROGAZIONE_SALDO,0),
          (SELECT DI.ID_DOCUMENTO_INDEX
           FROM   PBANDI_T_DOCUMENTO_INDEX di,PBANDI_C_ENTITA E
           WHERE  DI.ID_ENTITA  = E.ID_ENTITA
           AND    E.NOME_ENTITA = 'PBANDI_T_COMUNICAZ_FINE_PROG'
           AND    DI.ID_TARGET  = CFP.ID_COMUNICAZ_FINE_PROG)
   FROM   PBANDI_T_PROGETTO P,PBANDI_T_COMUNICAZ_FINE_PROG CFP,PBANDI_T_DICHIARAZIONE_SPESA DS,PBANDI_D_TIPO_DICHIARAZ_SPESA TDS
   WHERE  P.ID_PROGETTO                      = pIdProgetto
   AND    CFP.ID_PROGETTO                    = P.ID_PROGETTO
   AND    DS.ID_PROGETTO                     = P.ID_PROGETTO
   AND    DS.ID_TIPO_DICHIARAZ_SPESA         = TDS.ID_TIPO_DICHIARAZ_SPESA
   AND    TDS.DESC_BREVE_TIPO_DICHIARA_SPESA = 'FC');

  -- Richiesta erogazione
  INSERT INTO PBANDI_W_ATTIVITA_PREGRESSE
  (ID_ATTIVITA_PREGRESSE, ID_PROGETTO, COD_ELEMENTO,
   DESC_ELEMENTO,DATA,DT_INSERIMENTO,ID_DOMANDA,CAUSALE,ID_DOCUMENTO_INDEX)
  (SELECT nIdAttivitaPregresse,pIdProgetto,'O',
          'Richiesta Erogazione n° '||RE.NUMERO_RICHIESTA_EROGAZIONE||' di '||NVL(DECODE(RE.IMPORTO_EROGAZIONE_RICHIESTO,0,'0,00',TO_CHAR(RE.IMPORTO_EROGAZIONE_RICHIESTO,'9G999G999D99')),0)||
          ' presentata dall''utente '||PCK_PBANDI_UTILITY_BATCH.ReturnNomeCognomeUtente(RE.ID_UTENTE_INS),RE.DT_RICHIESTA_EROGAZIONE,SYSDATE,P.ID_DOMANDA,CE.DESC_CAUSALE,
          (SELECT DI.ID_DOCUMENTO_INDEX
           FROM   PBANDI_T_DOCUMENTO_INDEX di,PBANDI_C_ENTITA E
           WHERE  DI.ID_ENTITA  = E.ID_ENTITA
           AND    E.NOME_ENTITA = 'PBANDI_T_RICHIESTA_EROGAZIONE'
           AND    DI.ID_TARGET  = RE.ID_RICHIESTA_EROGAZIONE)
   FROM   PBANDI_T_PROGETTO P,PBANDI_T_RICHIESTA_EROGAZIONE RE,
          PBANDI_D_CAUSALE_EROGAZIONE CE
   WHERE  P.ID_PROGETTO            = pIdProgetto
   AND    RE.ID_PROGETTO           = P.ID_PROGETTO
   AND    RE.ID_CAUSALE_EROGAZIONE = CE.ID_CAUSALE_EROGAZIONE);

  -- Rilevazione Irregolarita'
  INSERT INTO PBANDI_W_ATTIVITA_PREGRESSE
  (ID_ATTIVITA_PREGRESSE, ID_PROGETTO, COD_ELEMENTO,
   DESC_ELEMENTO,DATA,DT_INSERIMENTO,ID_DOMANDA,NUMERO_IMS,
   DATA_IMS, NOTE, SOGGETTO,ID_DOCUMENTO_INDEX)
  (SELECT nIdAttivitaPregresse,pIdProgetto,'P',
          'Rilevata Irregolarita'' n° '||I.ID_IRREGOLARITA||' Versione '||I.NUMERO_VERSIONE||
          ' dall''utente '||PCK_PBANDI_UTILITY_BATCH.ReturnNomeCognomeUtente(I.ID_UTENTE_INS),I.DT_COMUNICAZIONE,SYSDATE,P.ID_DOMANDA,I.NUMERO_IMS,
          NVL(TO_CHAR(I.DT_IMS,'DD/MM/YYYY'),'non ancora inviato all''IMS'),I.NOTE_PRATICA_USATA,I.SOGGETTO_RESPONSABILE,
          (SELECT DI.ID_DOCUMENTO_INDEX
           FROM   PBANDI_T_DOCUMENTO_INDEX di,PBANDI_C_ENTITA E
           WHERE  DI.ID_ENTITA  = E.ID_ENTITA
           AND    E.NOME_ENTITA = 'PBANDI_T_IRREGOLARITA'
           AND    DI.ID_TARGET  = I.ID_IRREGOLARITA
           AND    ID_TIPO_DOCUMENTO_INDEX = 11)
   FROM   PBANDI_T_PROGETTO P,PBANDI_T_IRREGOLARITA I
   WHERE  P.ID_PROGETTO   = pIdProgetto
   AND    I.ID_PROGETTO   = P.ID_PROGETTO);

  -- Fideiussione utente
  INSERT INTO PBANDI_W_ATTIVITA_PREGRESSE
  (ID_ATTIVITA_PREGRESSE, ID_PROGETTO, COD_ELEMENTO,DESC_ELEMENTO,
   DATA,DT_INSERIMENTO,ID_DOMANDA,DATA_DECORRENZA,NOTE, ENTE)
  (SELECT nIdAttivitaPregresse,pIdProgetto,'Q','Fideiussione n° '||F.COD_RIFERIMENTO_FIDEIUSSIONE||' di '||NVL(DECODE(F.IMPORTO_FIDEIUSSIONE,0,'0,00',TO_CHAR(F.IMPORTO_FIDEIUSSIONE,'9G999G999D99')),0),
          F.DT_INSERIMENTO,SYSDATE,P.ID_DOMANDA,TO_CHAR(F.DT_DECORRENZA,'DD/MM/YYYY'),F.NOTE_FIDEIUSSIONE,F.DESC_ENTE_EMITTENTE
   FROM   PBANDI_T_PROGETTO P,PBANDI_T_FIDEIUSSIONE F
   WHERE  P.ID_PROGETTO = pIdProgetto
   AND    F.ID_PROGETTO = P.ID_PROGETTO);

  IF nCont37 != 0 THEN
    -- Atto di liquidazione
    INSERT INTO PBANDI_W_ATTIVITA_PREGRESSE
    (ID_ATTIVITA_PREGRESSE, ID_PROGETTO, COD_ELEMENTO,
     DESC_ELEMENTO,DATA,DT_INSERIMENTO,ID_DOMANDA,NOTE,
     IMPORTO_QUIETANZATO,CAUSALE,ID_DOCUMENTO_INDEX)
    (SELECT nIdAttivitaPregresse,pIdProgetto,'R',
            'Atto di liquidazione n° '||AL.NUMERO_ATTO||' del '||AL.ANNO_ATTO||' come '||MA.DESC_MODALITA_AGEVOLAZIONE||
            ' eseguito dall''utente '||PCK_PBANDI_UTILITY_BATCH.ReturnNomeCognomeUtente(AL.ID_UTENTE_INS),AL.DT_EMISSIONE_ATTO,SYSDATE,P.ID_DOMANDA,AL.NOTE_ATTO,
            (SELECT NVL(SUM(IMPORTO_QUIETANZATO),0)
             FROM   PBANDI_R_LIQUIDAZIONE L,PBANDI_T_MANDATO_QUIETANZATO MQ
             WHERE  L.ID_ATTO_LIQUIDAZIONE = AL.ID_ATTO_LIQUIDAZIONE
             AND    L.DT_FINE_VALIDITA     IS NULL
             AND    MQ.PROGR_LIQUIDAZIONE  = L.PROGR_LIQUIDAZIONE
             AND    MQ.DT_FINE_VALIDITA    IS NULL),CE.DESC_CAUSALE,
             (SELECT DI.ID_DOCUMENTO_INDEX
              FROM   PBANDI_T_DOCUMENTO_INDEX di,PBANDI_C_ENTITA E
              WHERE  DI.ID_ENTITA  = E.ID_ENTITA
              AND    E.NOME_ENTITA = 'PBANDI_T_ATTO_LIQUIDAZIONE'
              AND    DI.ID_TARGET  = AL.ID_ATTO_LIQUIDAZIONE)
     FROM   PBANDI_T_PROGETTO P,PBANDI_T_ATTO_LIQUIDAZIONE AL,
            PBANDI_D_MODALITA_AGEVOLAZIONE MA,PBANDI_D_CAUSALE_EROGAZIONE CE
     WHERE  P.ID_PROGETTO               = pIdProgetto
     AND    AL.ID_PROGETTO              = P.ID_PROGETTO
     AND    AL.ID_MODALITA_AGEVOLAZIONE = MA.ID_MODALITA_AGEVOLAZIONE
     AND    AL.ID_CAUSALE_EROGAZIONE    = CE.ID_CAUSALE_EROGAZIONE);
  END IF;

  -- Chiusura progetto
  BEGIN
    INSERT INTO PBANDI_W_ATTIVITA_PREGRESSE
    (ID_ATTIVITA_PREGRESSE, ID_PROGETTO, COD_ELEMENTO,DESC_ELEMENTO,DATA,
     DT_INSERIMENTO,ID_DOMANDA,NOTE)
    (SELECT nIdAttivitaPregresse,pIdProgetto,'S','Il progetto e'' stato '||SP.DESC_STATO_PROGETTO,P.DT_CHIUSURA_PROGETTO,
            SYSDATE,P.ID_DOMANDA,P.NOTE_CHIUSURA_PROGETTO
     FROM   PBANDI_T_PROGETTO P,PBANDI_D_STATO_PROGETTO SP
     WHERE  P.ID_PROGETTO                = pIdProgetto
     AND    P.ID_STATO_PROGETTO          = SP.ID_STATO_PROGETTO
     AND    SP.DESC_BREVE_STATO_PROGETTO IN ('C','CU'));
  EXCEPTION
    WHEN OTHERS THEN
      NULL;
  END;


  -- Caricamento indicatori finali
  INSERT INTO PBANDI_W_ATTIVITA_PREGRESSE
  (ID_ATTIVITA_PREGRESSE, ID_PROGETTO, COD_ELEMENTO,
   DESC_ELEMENTO,
   DATA,DT_INSERIMENTO,ID_DOMANDA)
  (SELECT DISTINCT nIdAttivitaPregresse,pIdProgetto,'U',
          'Caricamento Indicatori Finali da parte di '||PCK_PBANDI_UTILITY_BATCH.ReturnNomeCognomeUtente(DI.ID_UTENTE_AGG),
          FIRST_VALUE(DI.DT_AGGIORNAMENTO) OVER (ORDER BY DI.DT_AGGIORNAMENTO DESC), SYSDATE,P.ID_DOMANDA
   FROM   PBANDI_T_PROGETTO P,PBANDI_T_COMUNICAZ_FINE_PROG CFP,PBANDI_R_DOMANDA_INDICATORI DI
   WHERE  P.ID_PROGETTO    = pIdProgetto
   AND    CFP.ID_PROGETTO  = P.ID_PROGETTO
   AND    P.ID_DOMANDA     = DI.ID_DOMANDA);

  -- Caricamento Cronoprogramma Finale
  INSERT INTO PBANDI_W_ATTIVITA_PREGRESSE
  (ID_ATTIVITA_PREGRESSE, ID_PROGETTO, COD_ELEMENTO,
   DESC_ELEMENTO,
   DATA,DT_INSERIMENTO,ID_DOMANDA)
  (SELECT DISTINCT nIdAttivitaPregresse,pIdProgetto,'V',
          'Caricamento Cronoprogramma Finale da parte di '||PCK_PBANDI_UTILITY_BATCH.ReturnNomeCognomeUtente(PFM.ID_UTENTE_AGG),
          FIRST_VALUE(PFM.DT_AGGIORNAMENTO) OVER (ORDER BY PFM.DT_AGGIORNAMENTO DESC), SYSDATE,P.ID_DOMANDA
   FROM   PBANDI_T_PROGETTO P,PBANDI_T_COMUNICAZ_FINE_PROG CFP,PBANDI_R_PROGETTO_FASE_MONIT PFM
   WHERE  P.ID_PROGETTO     = pIdProgetto
   AND    CFP.ID_PROGETTO   = P.ID_PROGETTO
   AND    CFP.ID_PROGETTO   = PFM.ID_PROGETTO);

  -- Rettifiche
  INSERT INTO PBANDI_W_ATTIVITA_PREGRESSE
  (ID_ATTIVITA_PREGRESSE, ID_PROGETTO, COD_ELEMENTO,
   DESC_ELEMENTO,
   DATA,DT_INSERIMENTO,ID_DOMANDA)
  (SELECT nIdAttivitaPregresse,pIdProgetto,'W',
          'L'' utente '||PCK_PBANDI_UTILITY_BATCH.ReturnNomeCognomeUtente(RDS.ID_UTENTE_INS)||
          ' ha rettificato il validato della dichiarazione di spesa n° '||DS.ID_DICHIARAZIONE_SPESA||' del '||
          TO_CHAR(DS.DT_DICHIARAZIONE,'DD/MM/YYYY')||' per un importo pari a '||NVL(DECODE(SUM(RDS.IMPORTO_RETTIFICA),0,'0,00',TO_CHAR(RDS.IMPORTO_RETTIFICA,'9G999G999D99')),0),
          TRUNC(RDS.DT_RETTIFICA),SYSDATE,P.ID_DOMANDA
   FROM   PBANDI_T_PROGETTO P,PBANDI_T_DICHIARAZIONE_SPESA DS,PBANDI_R_PAGAMENTO_DICH_SPESA PDS,
          PBANDI_R_PAG_QUOT_PARTE_DOC_SP PQPDS,PBANDI_T_RETTIFICA_DI_SPESA RDS
   WHERE  P.ID_PROGETTO                   = pIdProgetto
   AND    DS.ID_PROGETTO                  = P.ID_PROGETTO
   AND    PDS.ID_DICHIARAZIONE_SPESA      = DS.ID_DICHIARAZIONE_SPESA
   AND    PDS.ID_PAGAMENTO                = PQPDS.ID_PAGAMENTO
   AND    RDS.PROGR_PAG_QUOT_PARTE_DOC_SP = PQPDS.PROGR_PAG_QUOT_PARTE_DOC_SP
   GROUP BY DS.ID_DICHIARAZIONE_SPESA,TRUNC(RDS.DT_RETTIFICA),RDS.ID_UTENTE_INS,DS.DT_DICHIARAZIONE,P.ID_DOMANDA,RDS.IMPORTO_RETTIFICA);

  -- Conto economico master
  INSERT INTO PBANDI_W_ATTIVITA_PREGRESSE
  (ID_ATTIVITA_PREGRESSE, ID_PROGETTO, COD_ELEMENTO, DESC_ELEMENTO, DATA, DT_INSERIMENTO,
   ID_CONTO_ECONOMICO, ID_DOMANDA, IMPORTO_AMMESSO,
   IMPORTO_AGEVOLATO, IMPORTO_IMPEGNO)
  (SELECT nIdAttivitaPregresse,pIdProgetto,'X','Conto economico'||' '||DESC_STATO_CONTO_ECONOMICO,CE.DT_INIZIO_VALIDITA,SYSDATE,
          CE.ID_CONTO_ECONOMICO,D.ID_DOMANDA,
          (SELECT NVL(SUM(rce.IMPORTO_AMMESSO_FINANZIAMENTO),0)
           FROM   PBANDI_T_RIGO_CONTO_ECONOMICO rce
           WHERE  rce.ID_CONTO_ECONOMICO = CE.ID_CONTO_ECONOMICO
           AND    rce.DT_FINE_VALIDITA   IS NULL
		   AND    rce.ID_VOCE_DI_SPESA IS NOT NULL
           GROUP BY RCE.ID_CONTO_ECONOMICO),
          NVL(SUM(cema.QUOTA_IMPORTO_AGEVOLATO),0),NVL(IMPORTO_IMPEGNO_VINCOLANTE,0)
   FROM   PBANDI_T_PROGETTO P,PBANDI_T_DOMANDA D,PBANDI_T_CONTO_ECONOMICO ce,PBANDI_D_STATO_CONTO_ECONOMICO sce,
          PBANDI_D_TIPOLOGIA_CONTO_ECON tce ,PBANDI_R_CONTO_ECONOM_MOD_AGEV cema
   WHERE  P.ID_PROGETTO                             = pIdProgetto
   AND    P.ID_DOMANDA                              = D.ID_DOMANDA
   AND    CE.ID_DOMANDA                             = D.ID_DOMANDA
   AND    CE.ID_STATO_CONTO_ECONOMICO               = SCE.ID_STATO_CONTO_ECONOMICO
   AND    SCE.ID_TIPOLOGIA_CONTO_ECONOMICO          = TCE.ID_TIPOLOGIA_CONTO_ECONOMICO
   AND    UPPER(TCE.DESC_BREVE_TIPOLOGIA_CONTO_ECO) = 'MASTER'
   AND    cema.ID_CONTO_ECONOMICO(+)                = ce.ID_CONTO_ECONOMICO
   GROUP BY CE.ID_CONTO_ECONOMICO,CE.DT_INIZIO_VALIDITA,D.ID_DOMANDA,DESC_STATO_CONTO_ECONOMICO,IMPORTO_IMPEGNO_VINCOLANTE);


  COMMIT;
  RETURN nIdAttivitaPregresse;
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('ERRORE = '||SQLERRM||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
    RETURN -1;
END AttivitaPregresse;

FUNCTION CaricaCheckList (p_CODICE_MODELLO IN PBANDI_C_MODELLO.CODICE_MODELLO%TYPE,
                          p_ID_PROGETTO IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                          p_DESC_BREVE_TIPO_DOC_INDEX  IN PBANDI_C_TIPO_DOCUMENTO_INDEX.DESC_BREVE_TIPO_DOC_INDEX%TYPE,
                          p_ID_CHECKLIST IN PBANDI_T_CHECKLIST.ID_CHECKLIST%TYPE DEFAULT NULL) return LISTCHECKLIST AS

CURSOR c1(c_id_progetto INTEGER, c_ID_TIPO_DOCUMENTO_INDEX IN INTEGER) IS
             SELECT ta.ID_APPALTO, ta.OGGETTO_APPALTO,dt_firma_contratto
              FROM PBANDI_R_PROGETTI_APPALTI  pa, PBANDI_T_APPALTO ta
              WHERE pa.ID_PROGETTO = c_ID_PROGETTO AND
                         pa.ID_APPALTO = TA.ID_APPALTO AND
                         PA.ID_TIPO_DOCUMENTO_INDEX = c_ID_TIPO_DOCUMENTO_INDEX
              ORDER BY ta.dt_firma_contratto;

CURSOR c2(c_id_tipo_modello INTEGER) IS
             SELECT * FROM PBANDI_C_TEMPL_CHECKLIST
              WHERE ID_TIPO_MODELLO = c_id_tipo_modello
              ORDER BY PROGR_ORDINAMENTO;



 -- mc-sviluppo

CURSOR c3(c_id_appalto INTEGER, c_cod_controllo VARCHAR2, c_id_checklist INTEGER) IS
              SELECT ID_DETT_CHECK_APPALTI, FLAG_ESITO, NOTE
               FROM PBANDI_T_APPALTO_CHECKLIST AC, PBANDI_T_DETT_CHECK_APPALTI CA
               WHERE AC.ID_APPALTO_CHECKLIST = CA.ID_APPALTO_CHECKLIST AND
                          AC.ID_APPALTO = c_id_appalto AND
                          AC.ID_CHECKLIST = c_id_checklist AND
                          CA.COD_CONTROLLO = c_cod_controllo;

CURSOR c4 (c_ID_DETT_CHECK_APPALTI  INTEGER) IS
             SELECT *
             FROM PBANDI_T_DETT_CHECK_APP_ITEM
             WHERE ID_DETT_CHECK_APPALTI  = c_ID_DETT_CHECK_APPALTI;

--
r1 c1%ROWTYPE;
r2 c2%ROWTYPE;
r3 c3%ROWTYPE;
--mc-sviluppo
r4 c4%ROWTYPE;
--
v_nome_attributo varchar2(30);
v_cod_controllo PBANDI_C_TEMPL_CHECKLIST.COD_CONTROLLO%TYPE;

--
V_LISTCHECKLIST LISTCHECKLIST:=LISTCHECKLIST(NULL);
v_x PLS_INTEGER := 0;
v_rec_checklist OBJCHECKLIST:= OBJCHECKLIST(NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
                                                                      NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
                                                                      NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
                                                                      NULL,NULL,NULL,NULL,NULL,NULL);
--
v_id_tipo_modello pbandi_c_modello.id_tipo_modello%type;
v_ID_TIPO_DOCUMENTO_INDEX INTEGER;

PROCEDURE prc_ValoreAttributo (p_id_appalto NUMBER, p_id_attributo NUMBER, v_rec_checklist  IN OUT OBJCHECKLIST) AS

BEGIN

         SELECT  CA.NOME_ATTRIBUTO
         INTO      v_nome_attributo
         FROM    PBANDI_C_ATTRIBUTO CA, PBANDI_C_ENTITA CE
         WHERE  CA.ID_ENTITA = CE.ID_ENTITA AND
                      CA.ID_ATTRIBUTO = p_id_attributo;

          IF           v_nome_attributo = 'IMPORTO_CONTRATTO' THEN
                        SELECT  IMPORTO_CONTRATTO
                        INTO    v_rec_checklist.IMPORTO_CONTRATTO
                        FROM    PBANDI_T_APPALTO
                        WHERE   ID_APPALTO = p_id_appalto;
         ELSIF       v_nome_attributo = 'BILANCIO_PREVENTIVO' THEN
                        SELECT  BILANCIO_PREVENTIVO
                        INTO    v_rec_checklist.BILANCIO_PREVENTIVO
                        FROM    PBANDI_T_APPALTO
                        WHERE   ID_APPALTO = p_id_appalto;
         ELSIF       v_nome_attributo = 'DT_FIRMA_CONTRATTO' THEN
                        SELECT  to_char(DT_FIRMA_CONTRATTO,'dd/mm/yyyy')
                        INTO    v_rec_checklist.DT_FIRMA_CONTRATTO
                        FROM    PBANDI_T_APPALTO
                        WHERE   ID_APPALTO = p_id_appalto;
         ELSIF       v_nome_attributo = 'DT_INIZIO_PREVISTA'  THEN
                        SELECT  to_char(DT_INIZIO_PREVISTA,'dd/mm/yyyy')
                        INTO    v_rec_checklist.DT_INIZIO_PREVISTA
                        FROM    PBANDI_T_APPALTO
                        WHERE   ID_APPALTO = p_id_appalto;
         ELSIF       v_nome_attributo = 'DT_CONSEGNA_LAVORI' THEN
                        SELECT  to_char(DT_CONSEGNA_LAVORI,'dd/mm/yyyy')
                        INTO    v_rec_checklist.DT_CONSEGNA_LAVORI
                        FROM    PBANDI_T_APPALTO
                        WHERE   ID_APPALTO = p_id_appalto;
         ELSIF       v_nome_attributo = 'DT_GUUE' THEN
                        SELECT  to_char(DT_GUUE,'dd/mm/yyyy')
                        INTO    v_rec_checklist.DT_GUUE
                        FROM    PBANDI_T_APPALTO
                        WHERE   ID_APPALTO = p_id_appalto;
          ELSIF      v_nome_attributo = 'DT_GURI' THEN
                        SELECT  to_char(DT_GURI,'dd/mm/yyyy')
                        INTO    v_rec_checklist.DT_GURI
                        FROM    PBANDI_T_APPALTO
                        WHERE   ID_APPALTO = p_id_appalto;
         ELSIF        v_nome_attributo = 'DT_QUOT_NAZIONALI' THEN
                        SELECT  to_char(DT_QUOT_NAZIONALI,'dd/mm/yyyy')
                        INTO    v_rec_checklist.DT_QUOT_NAZIONALI
                        FROM    PBANDI_T_APPALTO
                        WHERE   ID_APPALTO = p_id_appalto;
         ELSIF        v_nome_attributo = 'DT_WEB_STAZ_APPALTANTE' THEN
                        SELECT  to_char(DT_WEB_STAZ_APPALTANTE,'dd/mm/yyyy')
                        INTO    v_rec_checklist.DT_WEB_STAZ_APPALTANTE
                        FROM    PBANDI_T_APPALTO
                        WHERE   ID_APPALTO = p_id_appalto;
         ELSIF       v_nome_attributo = 'DT_WEB_OSSERVATORIO' THEN
                        SELECT  to_char(DT_WEB_OSSERVATORIO,'dd/mm/yyyy')
                        INTO    v_rec_checklist.DT_WEB_OSSERVATORIO
                        FROM    PBANDI_T_APPALTO
                        WHERE   ID_APPALTO = p_id_appalto;
         ELSIF       v_nome_attributo = 'DESC_TIPOLOGIA_AGGIUDICAZIONE' THEN
                        SELECT DESC_TIPOLOGIA_AGGIUDICAZIONE
                        INTO     v_rec_checklist.DESC_TIPOLOGIA_AGGIUDICAZIONE
                        FROM    PBANDI_T_PROCEDURA_AGGIUDICAZ pa, PBANDI_T_APPALTO ta, PBANDI_D_TIPOLOGIA_AGGIUDICAZ taz
                        WHERE  pa.id_procedura_aggiudicaz = ta.id_procedura_aggiudicaz AND
                                    pa.id_tipologia_aggiudicaz = taz.id_tipologia_aggiudicaz AND
                                    ta.id_appalto = p_id_appalto;
END IF;

EXCEPTION WHEN OTHERS THEN
RAISE_APPLICATION_ERROR (-20000,'Prc_ValoreAttributo - Errore Accesso appalto: '||p_id_appalto);

END prc_ValoreAttributo;

PROCEDURE prc_ValoreNomeCampo (p_nome_campo_edit IN varchar2, p_valore_edit IN varchar2, v_rec_checklist IN OUT OBJCHECKLIST) IS

BEGIN
                            IF          p_nome_campo_edit =  'norma' THEN  v_rec_checklist.norma := p_valore_edit;
                            ELSIF     p_nome_campo_edit =  'normativaRiferimento' THEN v_rec_checklist.NORMATIVARIFERIMENTO := p_valore_edit;
                            ELSIF     p_nome_campo_edit =  'spiegazioni' THEN v_rec_checklist.SPIEGAZIONI := p_valore_edit;
                            ELSIF     p_nome_campo_edit =  'criteriSelezione' THEN v_rec_checklist.CRITERISELEZIONE := p_valore_edit;
                            ELSIF     p_nome_campo_edit =  'supplementariTitolo' THEN v_rec_checklist.SUPPLEMENTARITITOLO := p_valore_edit;
                            ELSIF     p_nome_campo_edit =  'supplementariDataFirma' THEN v_rec_checklist.SUPPLEMENTARIDATAFIRMA := p_valore_edit;
                            ELSIF     p_nome_campo_edit =  'supplementariAmmontare' THEN v_rec_checklist.SUPPLEMENTARIAMMONTARE := p_valore_edit;
                            ELSIF     p_nome_campo_edit =  'supplementariDataConsegna' THEN v_rec_checklist.SUPPLEMENTARIDATACONSEGNA := p_valore_edit;
                            ELSIF     p_nome_campo_edit =  'supplementariDataEffettiva' THEN v_rec_checklist.SUPPLEMENTARIDATAEFFETTIVA := p_valore_edit;
                            ELSIF     p_nome_campo_edit =  'supplementariGiustificazione' THEN v_rec_checklist.SUPPLEMENTARIGIUSTIFICAZIONE := p_valore_edit;
                            ELSIF     p_nome_campo_edit =  'dataNonContrattuali' THEN v_rec_checklist.DATANONCONTRATTUALI := p_valore_edit;
                            ELSIF     p_nome_campo_edit =  'importoNonContrattuali' THEN v_rec_checklist.IMPORTONONCONTRATTUALI := p_valore_edit;
                            ELSIF     p_nome_campo_edit =  'dataNonEseguiti' THEN v_rec_checklist.DATANONESEGUITI := p_valore_edit;
                            ELSIF     p_nome_campo_edit =  'importoNonEseguiti' THEN v_rec_checklist.IMPORTONONESEGUITI := p_valore_edit;
                            END IF;




END prc_ValoreNomeCampo;



BEGIN
        SELECT ID_TIPO_MODELLO
        INTO v_id_tipo_modello
        FROM PBANDI_C_MODELLO
        WHERE CODICE_MODELLO = p_CODICE_MODELLO;

        SELECT  di.ID_TIPO_DOCUMENTO_INDEX
        INTO      v_ID_TIPO_DOCUMENTO_INDEX
        FROM     PBANDI_C_TIPO_DOCUMENTO_INDEX di
        WHERE   di.DESC_BREVE_TIPO_DOC_INDEX = p_DESC_BREVE_TIPO_DOC_INDEX;


    FOR  r1 in c1 (p_id_progetto, v_ID_TIPO_DOCUMENTO_INDEX )
    LOOP

           FOR r2 in c2 (v_id_tipo_modello)
           LOOP




                   v_rec_checklist := OBJCHECKLIST(NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
                                                                      NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
                                                                      NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
                                                                      NULL,NULL,NULL,NULL,NULL,NULL);
--

                    v_rec_checklist.ID_TIPO_MODELLO:=v_id_tipo_modello;
                    v_rec_checklist.ID_APPALTO:= r1.id_appalto;
                    v_rec_checklist.OGGETTO_APPALTO:= r1.oggetto_appalto;
                    v_rec_checklist.OGGETTO_APPALTO:= r1.oggetto_appalto;
                    v_rec_checklist.PROGR_ORDINAMENTO:=r2.progr_ordinamento;
                    v_rec_checklist.COD_CONTROLLO:=r2.cod_controllo;
                    v_rec_checklist.DESCR_RIGA:=r2.descr_riga;


                    IF r2.id_attributo IS NOT NULL THEN
                       prc_ValoreAttributo(r1.id_appalto, r2.id_attributo, v_rec_checklist);
                    END IF;

                    IF p_id_checklist IS NOT NULL AND r2.cod_controllo IS NOT NULL THEN

                        OPEN c3(r1.id_appalto, r2.cod_controllo, p_id_checklist);
                        FETCH c3 INTO r3;
                        IF c3%NOTFOUND THEN r3:=NULL; END IF;
                        CLOSE c3;

                        v_rec_checklist.FLAG_ESITO:= r3.FLAG_ESITO;
                        v_rec_checklist.NOTE:=r3.NOTE;

                        IF  r2.nome_campo_edit IS NOT NULL THEN

                           --  versione originale
                           --Prc_ValoreNomeCampo (r2.nome_campo_edit, r3.valore_edit, v_rec_checklist );
                           --


                            --mc-sviluppo

                           /* OPEN c4(r3.ID_DETT_CHECK_APPALTI);
                            FETCH c4 INTO r4;
                            IF  c4%NOTFOUND THEN
                                null;
                                --Prc_ValoreNomeCampo (r2.nome_campo_edit, NULL, v_rec_checklist);
                            ELSE

                                Prc_ValoreNomeCampo (r2.nome_campo_edit, r4.valore_edit, v_rec_checklist);

                             END IF;

                           CLOSE c4; */
                           FOR r4 IN c4 (r3.ID_DETT_CHECK_APPALTI)
                           LOOP
                                Prc_ValoreNomeCampo (r4.nome_campo_edit, r4.valore_edit, v_rec_checklist);
                           END LOOP;
                            --

                        END IF;

                    END IF;



                  IF (v_cod_controllo !=  nvl(r2.cod_controllo,'P'||r2.progr_ordinamento)) OR v_cod_controllo is null THEN

                            IF v_x >0 THEN
                                V_LISTCHECKLIST.EXTEND;
                            END IF;
                            v_x := v_x+1;

                            V_LISTCHECKLIST(v_x):= v_rec_checklist;

                    v_cod_controllo := nvl(r2.cod_controllo,'P'||r2.progr_ordinamento);

                  END IF;


           END LOOP;
    END LOOP;

RETURN V_LISTCHECKLIST;
END CaricaCheckList;
---------------
---------------
FUNCTION IsFESR (pIdProgetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE) 
   RETURN VARCHAR2 AS      
   
      cursor c1 (c_id_progetto NUMBER) is
          select c.id_linea_di_intervento
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;
          
        cursor c2 (c_id_linea_di_intervento NUMBER) is
           select desc_breve_linea
           from PBANDI_D_LINEA_DI_INTERVENTO
           where id_linea_di_intervento_padre IS NULL
           connect by prior id_linea_di_intervento_padre = id_linea_di_intervento
           start with id_linea_di_intervento = c_id_linea_di_intervento;
           
         v_desc_breve_linea_radice VARCHAR2(20);
         v_id_linea_intervento INTEGER;
     
    BEGIN
       OPEN c1 (pIdProgetto);
       FETCH c1 into v_id_linea_intervento;
       IF c1%NOTFOUND THEN
         v_id_linea_intervento := NULL;
       END IF;
       CLOSE c1;
       
       IF v_id_linea_intervento IS NOT NULL THEN
          OPEN c2(v_id_linea_intervento);
          FETCH c2 INTO v_desc_breve_linea_radice;
          IF c2%NOTFOUND THEN
             v_desc_breve_linea_radice := NULL;
          END IF;
          CLOSE c2;
       END IF;

       RETURN v_desc_breve_linea_radice;
       
   END IsFESR;
---------------
---------------
FUNCTION FNC_DSFINALE_CFP
(p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
p_id_utente IN PBANDI_T_UTENTE.ID_UTENTE%TYPE
)
RETURN INTEGER IS

vRet INTEGER;

BEGIN
  -- jira 3050 -- 
  --vRet:= PCK_PBANDI_PROCESSO.EndAttivitaNoCommit (p_id_progetto,'DICH-DI-SPESA',p_id_utente,'S');
  --IF vRet <> 0 THEN ROLLBACK; RETURN 1; END IF;
  vRet:= PCK_PBANDI_PROCESSO.EndAttivitaNoCommit (p_id_progetto,'CARICAM-INDIC-PROG',p_id_utente,'S');
  IF vRet <> 0 THEN ROLLBACK; RETURN 1; END IF;
  vRet:= PCK_PBANDI_PROCESSO.EndAttivitaNoCommit (p_id_progetto,'CRONOPROGRAMMA',p_id_utente,'S');
  IF vRet <> 0 THEN ROLLBACK; RETURN 1; END IF;
  vRet:= PCK_PBANDI_PROCESSO.EndAttivitaNoCommit (p_id_progetto,'COMUNIC-FINE-PROG',p_id_utente,'S');   
  IF vRet <> 0 THEN ROLLBACK; RETURN 1; END IF;
  
  COMMIT;
  RETURN 0;

END FNC_DSFINALE_CFP;
 


END PCK_PBANDI_UTILITY_ONLINE;
/