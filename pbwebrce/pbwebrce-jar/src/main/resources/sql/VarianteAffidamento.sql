SELECT IMPORTO as importo , DESCRIZIONE_TIPOLOGIA_VARIANTE as descrizioneTipologiaVariante , 
	DT_INVIO_VERIFICA_AFFIDAMENTO as dtInvioVerificaAffidamento , NOTE as note , 
	ID_APPALTO as idAppalto , DT_INSERIMENTO as dtInserimento , 
	FLG_INVIO_VERIFICA_AFFIDAMENTO as flgInvioVerificaAffidamento , 
	ID_TIPOLOGIA_VARIANTE as idTipologiaVariante , ID_VARIANTE as idVariante
 FROM (
	select affidamenti.*, tipologia.DESCRIZIONE as descrizione_tipologia_variante
	from pbandi_t_varianti_affidamenti affidamenti
	join PBANDI_D_TIPOLOGIE_VARIANTI tipologia on affidamenti.ID_TIPOLOGIA_VARIANTE = tipologia.ID_TIPOLOGIA_VARIANTE
) WHERE ID_APPALTO = ?