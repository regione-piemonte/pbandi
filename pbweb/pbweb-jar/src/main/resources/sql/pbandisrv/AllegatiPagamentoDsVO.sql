SELECT
	P.ID_PROGETTO,
	PQPDS.ID_DICHIARAZIONE_SPESA,
	DOCINDEX.NOME_FILE,
	DOCINDEX.UUID_NODO,
	DOCINDEX.ID_DOCUMENTO_INDEX
FROM
	PBANDI_T_PAGAMENTO PAG,
	PBANDI_R_PAG_QUOT_PARTE_DOC_SP PQPDS,
	PBANDI_T_FILE_ENTITA FE,
	PBANDI_T_FILE FI,
	PBANDI_T_QUOTA_PARTE_DOC_SPESA QPDS,
	PBANDI_T_PROGETTO P,
	PBANDI_T_DOCUMENTO_INDEX DOCINDEX
WHERE
	PAG.ID_PAGAMENTO = PQPDS.ID_PAGAMENTO
	AND DOCINDEX.ID_DOCUMENTO_INDEX = FI.ID_DOCUMENTO_INDEX
	AND PQPDS.ID_QUOTA_PARTE_DOC_SPESA = QPDS.ID_QUOTA_PARTE_DOC_SPESA
	AND QPDS.ID_PROGETTO = P.ID_PROGETTO
	AND PAG.ID_PAGAMENTO = FE.ID_TARGET
	AND fe.id_file = fi.id_file
	AND FE.ID_ENTITA = (
		SELECT ID_ENTITA
	FROM
		PBANDI_C_ENTITA
	WHERE
		NOME_ENTITA = 'PBANDI_T_PAGAMENTO')