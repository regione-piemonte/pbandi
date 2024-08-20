/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.suggestion;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.PartitaIvaVO;

public class PIvaRowMapper implements RowMapper<PartitaIvaVO>{

	@Override
	public PartitaIvaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		PartitaIvaVO cm = new PartitaIvaVO();
		
		cm.setPartitaIva(rs.getString("PARTITA_IVA"));
		cm.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
		cm.setDescrTipoSogg(rs.getNString("DESC_BREVE_TIPO_SOGGETTO"));
		cm.setIdTipoSogg(rs.getInt("ID_TIPO_SOGGETTO"));
		
		return cm;
	}

}
