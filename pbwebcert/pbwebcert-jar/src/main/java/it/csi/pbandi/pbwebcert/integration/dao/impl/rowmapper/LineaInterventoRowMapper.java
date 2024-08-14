/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.LineaDiInterventoVO;
import it.csi.pbandi.pbwebcert.integration.vo.PbandiDLineaDiInterventoVO;

public class LineaInterventoRowMapper implements RowMapper<PbandiDLineaDiInterventoVO>{

	@Override
	public PbandiDLineaDiInterventoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiDLineaDiInterventoVO cm = new PbandiDLineaDiInterventoVO();
		cm.setIdLineaDiIntervento(rs.getBigDecimal("ID_LINEA_DI_INTERVENTO"));
		cm.setIdLineaDiInterventoPadre(rs.getBigDecimal("ID_LINEA_DI_INTERVENTO_PADRE"));
		cm.setDescBreveLinea(rs.getString("DESC_BREVE_LINEA"));
		cm.setDescLinea(rs.getString("DESC_LINEA"));
		cm.setDtInizioValidita(rs.getDate("DT_INIZIO_VALIDITA"));
		cm.setDtFineValidita(rs.getDate("DT_FINE_VALIDITA"));
		cm.setIdTipoLineaIntervento(rs.getBigDecimal("ID_TIPO_LINEA_INTERVENTO"));
		cm.setIdTipoStrumentoProgr(rs.getBigDecimal("ID_TIPO_STRUMENTO_PROGR"));
		cm.setCodIgrueT13T14(rs.getString("COD_IGRUE_T13_T14"));
		cm.setIdStrumentoAttuativo(rs.getBigDecimal("ID_STRUMENTO_ATTUATIVO"));
		cm.setNumDelibera(rs.getBigDecimal("NUM_DELIBERA"));
		cm.setAnnoDelibera(rs.getBigDecimal("ANNO_DELIBERA"));
		cm.setIdProcesso(rs.getBigDecimal("ID_PROCESSO"));
		cm.setCodLivGerarchico(rs.getString("COD_LIV_GERARCHICO"));
		cm.setFlagCampionRilev(rs.getString("FLAG_CAMPION_RILEV"));
		return cm;
	}

}
