/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.LineaDiInterventoVO;

public class AttivitaLineaInterventoRowMapper implements RowMapper<LineaDiInterventoVO> {
	@Override
	public LineaDiInterventoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		LineaDiInterventoVO cm = new LineaDiInterventoVO();

		cm.setIdLineaDiIntervento(rs.getBigDecimal("IDLINEADIINTERVENTO"));
		cm.setDescLinea(rs.getString("DESCLINEA"));
		cm.setDescBreveCompleta(rs.getString("DESCBREVECOMPLETA"));
		cm.setDescBreveLinea(rs.getString("DESCBREVECOMPLETA"));
		return cm;
	}
}
