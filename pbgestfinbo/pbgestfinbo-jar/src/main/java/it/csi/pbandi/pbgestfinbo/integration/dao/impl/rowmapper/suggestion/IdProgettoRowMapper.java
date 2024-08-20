/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.suggestion;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.IdProgettoVO;

public class IdProgettoRowMapper implements RowMapper<IdProgettoVO>{

	@Override
	public IdProgettoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		IdProgettoVO cm = new IdProgettoVO();
		
		cm.setCodProgetto(rs.getString("CODICE_VISUALIZZATO"));
		cm.setIdProgetto(rs.getLong("ID_PROGETTO"));
		cm.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
		cm.setDescTipoSoggetto(rs.getString("DESC_BREVE_TIPO_SOGGETTO"));
		
		return cm;
	}

}
