/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select DESC_BREVE_REGOLA as descBreveRegola, TIPO_ASSOCIAZIONE as tipoAssociazione, NOME_BANDOLINEA as nomeBandolinea, 
		PROGR_BANDO_LINEA_INTERVENTO as progrBandoLineaIntervento, ID_REGOLA as idRegola, DESC_REGOLA_COMPOSTA as descRegolaComposta
from (
		SELECT R.*,
		  REG.DESC_BREVE_REGOLA
		  ||' - '
		  || REG.DESC_REGOLA     AS DESC_REGOLA_COMPOSTA,
		  'Bando-linea corrente' AS TIPO_ASSOCIAZIONE,
		  REG.DESC_BREVE_REGOLA,
		  B.NOME_BANDO_LINEA AS NOME_BANDOLINEA
		FROM PBANDI_R_REGOLA_BANDO_LINEA R,
		     PBANDI_C_REGOLA REG,
		     PBANDI_R_BANDO_LINEA_INTERVENT B
		WHERE R.ID_REGOLA = REG.ID_REGOLA
		AND B.PROGR_BANDO_LINEA_INTERVENTO = R.PROGR_BANDO_LINEA_INTERVENTO
		AND NVL(TRUNC(REG.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)
) 