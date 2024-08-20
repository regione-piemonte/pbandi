/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.search.VersioniVO;

public class VersioniRowMapper implements RowMapper<VersioniVO>{

	@Override
	public VersioniVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		VersioniVO cm = new VersioniVO();
		
		cm.setIdVersione(rs.getLong("ID_DOMANDA"));
		cm.setDataRiferimento(rs.getDate("DT_PRESENTAZIONE_DOMANDA"));
		cm.setIdVersione(rs.getLong("ID_PROGETTO"));
		cm.setDataRiferimento(rs.getDate("DT_CONCESSIONE"));
		
		return cm;
	}

}
