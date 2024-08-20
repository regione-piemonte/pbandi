/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.suggestion;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.IdSoggettoVO;

public class IdSoggettoRowMapper implements RowMapper<IdSoggettoVO>{
	
	@Override
	public IdSoggettoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		IdSoggettoVO cm = new IdSoggettoVO();
		
		cm.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
		cm.setDescrTipoSogg(rs.getNString("DESC_BREVE_TIPO_SOGGETTO"));
		cm.setIdTipoSogg(rs.getInt("ID_TIPO_SOGGETTO"));
		cm.setNdg(rs.getString("NDG"));
				
		return cm;
	}

}

