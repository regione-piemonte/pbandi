SELECT
	qpds.id_progetto,
	pqpds.id_dichiarazione_spesa,
	DOCINDEX.NOME_FILE,
	DOCINDEX.UUID_NODO,
	DOCINDEX.ID_DOCUMENTO_INDEX
FROM
	pbandi_t_documento_di_spesa ds,
	pbandi_t_quota_parte_doc_spesa qpds,
	pbandi_r_pag_quot_parte_doc_sp pqpds,
	pbandi_t_file_entita fe,
	pbandi_t_file fi,
	PBANDI_T_DOCUMENTO_INDEX DOCINDEX
WHERE
	ds.id_documento_di_spesa = qpds.id_documento_di_spesa
	AND qpds.id_quota_parte_doc_spesa = pqpds.id_quota_parte_doc_spesa
	AND qpds.id_documento_di_spesa = fe.id_target
	AND fe.id_file = fi.id_file
	AND DOCINDEX.ID_DOCUMENTO_INDEX = FI.ID_DOCUMENTO_INDEX
	AND fe.id_entita = ( SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_DOCUMENTO_DI_SPESA')