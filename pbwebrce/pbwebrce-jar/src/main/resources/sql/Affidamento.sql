SELECT IMPORTO_CONTRATTO as importoContratto , ID_TIPO_AFFIDAMENTO as idTipoAffidamento ,
 	DT_GURI as dtGuri , OGGETTO_APPALTO as oggettoAppalto , IMPRESA_APPALTATRICE as impresaAppaltatrice , 
	ID_TIPOLOGIA_APPALTO as idTipologiaAppalto , DT_FIRMA_CONTRATTO as dtFirmaContratto , 
	PERC_RIBASSO_ASTA as percRibassoAsta , DT_CONSEGNA_LAVORI as dtConsegnaLavori , 
	ID_UTENTE_AGG as idUtenteAgg , DT_WEB_STAZ_APPALTANTE as dtWebStazAppaltante , 
	DT_WEB_OSSERVATORIO as dtWebOsservatorio , DT_INSERIMENTO as dtInserimento , 
	IMP_RIBASSO_ASTA as impRibassoAsta , ID_STATO_AFFIDAMENTO as idStatoAffidamento ,
 	BILANCIO_PREVENTIVO as bilancioPreventivo , IMP_RENDICONTABILE as impRendicontabile ,
 	DT_AGGIORNAMENTO as dtAggiornamento , ID_PROCEDURA_AGGIUDICAZ as idProceduraAggiudicaz ,
 	INTERVENTO_PISU as interventoPisu , ID_NORMA as idNorma , ID_UTENTE_INS as idUtenteIns ,
 	ID_TIPO_PERCETTORE as idTipoPercettore , DT_QUOT_NAZIONALI as dtQuotNazionali ,
 	DT_INIZIO_PREVISTA as dtInizioPrevista , DT_GUUE as dtGuue , ID_APPALTO as idAppalto ,
 	SOPRA_SOGLIA as sopraSoglia 
FROM PBANDI_T_APPALTO 
WHERE ID_APPALTO = ?