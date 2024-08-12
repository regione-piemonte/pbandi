SELECT
	DICHIARAZIONE.ID_DICHIARAZIONE_SPESA ID_DICHIARAZIONE_SPESA,
	DOCINDEX.ID_DOCUMENTO_INDEX,
	PBANDI_T_FILE.SIZE_FILE,
	PBANDI_T_FILE.NOME_FILE,
	DOCINDEX.UUID_NODO,
	FILE_ENTITA.ID_PROGETTO
FROM
	PBANDI_T_DOCUMENTO_INDEX DOCINDEX
JOIN PBANDI_T_FILE ON
	(DOCINDEX.ID_DOCUMENTO_INDEX = PBANDI_T_FILE.ID_DOCUMENTO_INDEX)
JOIN PBANDI_T_FILE_ENTITA FILE_ENTITA ON
	FILE_ENTITA.ID_FILE = PBANDI_T_FILE.ID_FILE
	AND FILE_ENTITA.ID_ENTITA =(
		SELECT ID_ENTITA
	FROM
		PBANDI_C_ENTITA
	WHERE
		UPPER(NOME_ENTITA)= UPPER('PBANDI_T_INTEGRAZIONE_SPESA'))
JOIN PBANDI_T_INTEGRAZIONE_SPESA INTEGRAZIONE ON
	(INTEGRAZIONE.ID_INTEGRAZIONE_SPESA = FILE_ENTITA.ID_TARGET)
JOIN PBANDI_T_DICHIARAZIONE_SPESA DICHIARAZIONE ON
	(DICHIARAZIONE.ID_DICHIARAZIONE_SPESA = INTEGRAZIONE.ID_DICHIARAZIONE_SPESA)
JOIN PBANDI_T_PROGETTO ON
	FILE_ENTITA.ID_PROGETTO = PBANDI_T_PROGETTO.ID_PROGETTO