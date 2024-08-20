select IMPORTO_CONTRATTO as importoContratto , ID_TIPO_AFFIDAMENTO as idTipoAffidamento , OGGETTO_APPALTO as oggettoAppalto , DT_GURI as dtGuri , IMPRESA_APPALTATRICE as impresaAppaltatrice , ID_TIPOLOGIA_APPALTO as idTipologiaAppalto , DT_FIRMA_CONTRATTO as dtFirmaContratto , PERC_RIBASSO_ASTA as percRibassoAsta , DT_CONSEGNA_LAVORI as dtConsegnaLavori , DESC_STATO_AFFIDAMENTO as descStatoAffidamento , CIG_PROC_AGG as cigProcAgg , DESC_TIPOLOGIA_APPALTO as descTipologiaAppalto , ID_UTENTE_AGG as idUtenteAgg , DT_WEB_STAZ_APPALTANTE as dtWebStazAppaltante , DT_WEB_OSSERVATORIO as dtWebOsservatorio , DT_INSERIMENTO as dtInserimento , IMP_RIBASSO_ASTA as impRibassoAsta , ID_STATO_AFFIDAMENTO as idStatoAffidamento , BILANCIO_PREVENTIVO as bilancioPreventivo , IMP_RENDICONTABILE as impRendicontabile , DT_AGGIORNAMENTO as dtAggiornamento , ID_PROCEDURA_AGGIUDICAZ as idProceduraAggiudicaz , COD_PROC_AGG as codProcAgg , INTERVENTO_PISU as interventoPisu , ID_NORMA as idNorma , ID_TIPO_DOCUMENTO_INDEX as idTipoDocumentoIndex , ID_UTENTE_INS as idUtenteIns , ID_TIPO_PERCETTORE as idTipoPercettore , ID_PROGETTO as idProgetto , DT_INIZIO_PREVISTA as dtInizioPrevista , DT_QUOT_NAZIONALI as dtQuotNazionali , DT_GUUE as dtGuue , ID_APPALTO as idAppalto , SOPRA_SOGLIA as sopraSoglia from (
select rpa.id_progetto, app.*, tipoapp.desc_tipologia_appalto, agg.cig_proc_agg, agg.cod_proc_agg, stato.desc_stato_affidamento, rpa.id_tipo_documento_index
from pbandi_r_progetti_appalti rpa, pbandi_t_appalto app,
     pbandi_d_tipologia_appalto tipoapp, pbandi_t_procedura_aggiudicaz agg,
     pbandi_d_stato_affidamento stato
where id_tipo_documento_index = ?
  and app.id_appalto = rpa.id_appalto
  and tipoapp.id_tipologia_appalto = app.id_tipologia_appalto
  and agg.id_procedura_aggiudicaz = app.id_procedura_aggiudicaz
  and stato.id_stato_affidamento(+) = app.id_stato_affidamento
order by stato.ordinamento, app.id_appalto
) where ID_TIPO_DOCUMENTO_INDEX = ? and ID_PROGETTO = ?
