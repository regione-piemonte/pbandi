/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebrce.dto.QteHtmlDTO;
import it.csi.pbandi.pbwebrce.dto.indicatori.InfoIndicatore;

public class QteHtmlRowMapper implements RowMapper<QteHtmlDTO> {

	@Override
	public QteHtmlDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		QteHtmlDTO cm = new QteHtmlDTO();
		if (rs.getString("ID") != null) {
			cm.setIdQtesHtmlProgetto(new Long(rs.getLong("ID")));
		}
		Clob clob = rs.getClob("HTML_QTES");
		cm.setHtmlQtesProgetto(clob.getSubString(1, (int) clob.length()));
		return cm;
	}

}
