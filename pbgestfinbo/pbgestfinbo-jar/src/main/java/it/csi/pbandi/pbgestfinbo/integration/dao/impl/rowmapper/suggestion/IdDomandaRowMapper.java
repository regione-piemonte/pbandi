/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.suggestion;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.IdDomandaVO;

public class IdDomandaRowMapper implements RowMapper<IdDomandaVO>{

	@Override
	public IdDomandaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		IdDomandaVO cm = new IdDomandaVO();
		
		cm.setNumeroDomanda(rs.getString("NUMERO_DOMANDA"));
		cm.setIdDomanda(rs.getLong("ID_DOMANDA"));
		cm.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
		cm.setDescTipoSoggetto(rs.getString("DESC_BREVE_TIPO_SOGGETTO"));
		
		return cm;
	}

}
