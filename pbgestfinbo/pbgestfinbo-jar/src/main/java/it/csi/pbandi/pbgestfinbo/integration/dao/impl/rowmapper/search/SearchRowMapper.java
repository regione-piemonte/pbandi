/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.search.SearchVO;

public class SearchRowMapper implements RowMapper<SearchVO>{

	@Override
	public SearchVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		SearchVO cm = new SearchVO();
		
		cm.setCf(rs.getString("CODICE_FISCALE_SOGGETTO"));
		cm.setDenominazione(rs.getString("DENOMINAZIONE"));
		cm.setIdDomanda(rs.getLong("ID_DOMANDA"));
		cm.setIdProgetto(rs.getLong("ID_PROGETTO"));
		cm.setpIva(rs.getString("PIVA"));
		cm.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
		cm.setIdSede(rs.getLong("ID_SEDE"));
		cm.setDescTipoSogg(rs.getString("DESC_TIPO_SOGGETTO"));
		cm.setIdEnteGiuridico(rs.getLong("ID_ENTE"));
		
		return cm;
	}

}
