/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.search.AtlanteVO;

public class AtlanteRowMapper implements RowMapper<AtlanteVO>{

	@Override
	public AtlanteVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		AtlanteVO cm = new AtlanteVO();
		
		cm.setIdNazione(rs.getString("ID_NAZIONE"));
		cm.setIdRegione(rs.getString("ID_REGIONE"));
		cm.setIdProvincia(rs.getString("ID_PROVINCIA"));
		cm.setIdComune(rs.getString("ID_COMUNE"));
		cm.setDescNazione(rs.getString("DESC_NAZIONE"));
		cm.setDescRegione(rs.getString("DESC_REGIONE"));
		cm.setDescProvincia(rs.getString("SIGLA_PROVINCIA"));
		cm.setDescComune(rs.getString("DESC_COMUNE"));
		
		return cm;
	}

}
