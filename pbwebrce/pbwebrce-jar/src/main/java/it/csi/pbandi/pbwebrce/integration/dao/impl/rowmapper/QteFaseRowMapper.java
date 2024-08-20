/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebrce.dto.QteFaseDTO;

public class QteFaseRowMapper implements RowMapper<QteFaseDTO> {

	@Override
	public QteFaseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		QteFaseDTO cm = new QteFaseDTO();
		cm.setIdColonnaQtes(new Long(rs.getLong("ID_COLONNA_QTES")));
		cm.setDescBreveColonnaQtes(rs.getString("DESC_BREVE_COLONNA_QTES"));
		cm.setDescColonnaQtes(rs.getString("DESC_COLONNA_QTES"));

		return cm;
	}

}
