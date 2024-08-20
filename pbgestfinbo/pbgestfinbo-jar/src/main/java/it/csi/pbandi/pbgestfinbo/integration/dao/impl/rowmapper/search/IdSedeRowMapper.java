/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.search.IdSedeVO;

public class IdSedeRowMapper implements RowMapper<IdSedeVO>{

	@Override
	public IdSedeVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		IdSedeVO cm = new IdSedeVO();
		
		cm.setIdSede(rs.getLong("ID_SEDE"));
		
		return cm;
	}

}
