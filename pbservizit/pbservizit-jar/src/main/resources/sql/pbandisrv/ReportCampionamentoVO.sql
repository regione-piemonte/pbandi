/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT DISTINCT
		camp.*,
		dindex.ID_DOCUMENTO_INDEX,
		DINDEX.NOME_FILE,
		INTERVENTO.ID_LINEA_DI_INTERVENTO ID_NORMATIVA,
		INTERVENTO.DESC_LINEA NORMATIVA,
		PERIODO.DESC_PERIODO_VISUALIZZATA
	FROM
		PBANDI_T_CAMPIONAMENTO camp LEFT JOIN pbandi_t_periodo periodo ON camp.ID_PERIODO = PERIODO.ID_PERIODO, 
		PBANDI_T_DOCUMENTO_INDEX dindex,
		PBANDI_D_LINEA_DI_INTERVENTO intervento
	WHERE
		CAMP.ID_CAMPIONE = dindex.ID_TARGET
		AND INTERVENTO.ID_LINEA_DI_INTERVENTO = CAMP.ID_LINEA_DI_INTERVENTO
		AND DINDEX.ID_ENTITA = (
					SELECT
						id_entita
					FROM
						PBANDI_C_ENTITA entita
					WHERE
						ENTITA.NOME_ENTITA LIKE 'PBANDI_T_CAMPIONAMENTO')