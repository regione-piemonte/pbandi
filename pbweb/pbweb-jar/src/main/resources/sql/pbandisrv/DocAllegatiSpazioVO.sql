SELECT
		file_documenti.id_progetto,
		file_documenti.id_dichiarazione_spesa ,
		file_documenti.num_doc_spesa NUMERO_DOCUMENTI_SPESA,
		file_documenti.size_doc_spesa SIZE_DOCUMENTI_SPESA,
		file_pagamenti.num_pag NUMERO_DOCUMENTI_PAGAMENTI,
		file_pagamenti.size_pag SIZE_DOCUMENTI_PAGAMENTI,
		file_dichiarazione.num_doc_dic NUMERO_DOCUMENTI_DICH_SPESA,
		file_dichiarazione.size_doc_dic SIZE_DOCUMENTI_DICH_SPESA,
		file_integraz.num_doc_int NUMERO_DOCUMENTI_INTEGRAZIONI,
		file_integraz.size_doc_int SIZE_DOCUMENTI_INTEGRAZIONI
	FROM
		(
		SELECT
			qpds.id_progetto,
			pqpds.id_dichiarazione_spesa,
			COUNT (DISTINCT(FI.ID_FILE)) num_doc_spesa,
			SUM(FI.SIZE_FILE) size_doc_spesa
		FROM
			pbandi_t_documento_di_spesa ds,
			pbandi_t_quota_parte_doc_spesa qpds,
			pbandi_r_pag_quot_parte_doc_sp pqpds,
			pbandi_t_file_entita fe,
			pbandi_t_file fi
		WHERE
			ds.id_documento_di_spesa = qpds.id_documento_di_spesa
			AND qpds.id_quota_parte_doc_spesa = pqpds.id_quota_parte_doc_spesa
			AND qpds.id_documento_di_spesa = fe.id_target
			AND fe.id_file = fi.id_file
			AND fe.id_entita = (
				SELECT ID_ENTITA
			FROM
				PBANDI_C_ENTITA
			WHERE
				NOME_ENTITA = 'PBANDI_T_DOCUMENTO_DI_SPESA')
		GROUP BY
			qpds.id_progetto,
			pqpds.id_dichiarazione_spesa) file_documenti,
		(
		SELECT
			P.ID_PROGETTO,
			PQPDS.ID_DICHIARAZIONE_SPESA,
			COUNT(FI.ID_FILE) num_pag,
			SUM(FI.SIZE_FILE) size_pag
		FROM
			PBANDI_T_PAGAMENTO PAG,
			PBANDI_R_PAG_QUOT_PARTE_DOC_SP PQPDS,
			PBANDI_T_FILE_ENTITA FE,
			PBANDI_T_FILE FI,
			PBANDI_T_QUOTA_PARTE_DOC_SPESA QPDS,
			PBANDI_T_PROGETTO P
		WHERE
			PAG.ID_PAGAMENTO = PQPDS.ID_PAGAMENTO
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
		GROUP BY
			P.ID_PROGETTO,
			PQPDS.ID_DICHIARAZIONE_SPESA) file_pagamenti,
		(
		SELECT
			DS.ID_PROGETTO,
			DS.ID_DICHIARAZIONE_SPESA,
			COUNT(FI.ID_FILE) num_doc_dic,
			SUM(FI.SIZE_FILE) size_doc_dic
		FROM
			PBANDI_T_DICHIARAZIONE_SPESA DS,
			PBANDI_T_FILE_ENTITA FE,
			PBANDI_T_FILE FI
		WHERE
			DS.ID_DICHIARAZIONE_SPESA = FE.ID_TARGET
			AND FE.ID_FILE = FI.ID_FILE
			AND FE.ID_ENTITA = (
				SELECT ID_ENTITA
			FROM
				PBANDI_C_ENTITA
			WHERE
				NOME_ENTITA = 'PBANDI_T_DICHIARAZIONE_SPESA')
		GROUP BY
			DS.ID_PROGETTO,
			DS.ID_DICHIARAZIONE_SPESA) file_dichiarazione,
		(
		SELECT
			DICHIARAZIONE.ID_PROGETTO,
			DICHIARAZIONE.ID_DICHIARAZIONE_SPESA,
			COUNT(PBANDI_T_FILE.ID_FILE) num_doc_int,
			SUM(PBANDI_T_FILE.SIZE_FILE) size_doc_int
		FROM
			PBANDI_T_DOCUMENTO_INDEX DOCINDEX
		JOIN PBANDI_T_FILE ON
			(DOCINDEX.ID_DOCUMENTO_INDEX = PBANDI_T_FILE.ID_DOCUMENTO_INDEX)
		JOIN PBANDI_T_FILE_ENTITA FILE_ENTITA ON
			FILE_ENTITA.ID_FILE = PBANDI_T_FILE.ID_FILE
			AND FILE_ENTITA.ID_ENTITA =(
			SELECT
				ID_ENTITA
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
		GROUP BY
			DICHIARAZIONE.id_progetto,
			DICHIARAZIONE.ID_DICHIARAZIONE_SPESA) file_integraz
	WHERE
		(file_documenti.id_progetto = file_pagamenti.id_progetto
		AND file_documenti.ID_DICHIARAZIONE_SPESA = file_pagamenti.ID_DICHIARAZIONE_SPESA)
		AND (file_documenti.id_progetto = file_dichiarazione.id_progetto (+)
		AND file_documenti.ID_DICHIARAZIONE_SPESA = file_dichiarazione.ID_DICHIARAZIONE_SPESA(+))
		AND (file_documenti.id_progetto = file_integraz.id_progetto (+)
		AND file_documenti.ID_DICHIARAZIONE_SPESA = file_integraz.ID_DICHIARAZIONE_SPESA(+))
