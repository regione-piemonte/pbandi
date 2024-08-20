/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.dto.profilazione.FormaGiuridicaDTO;

public class FormaGiuridicaDTORowMapper implements RowMapper<FormaGiuridicaDTO> {

	@Override
	public FormaGiuridicaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		FormaGiuridicaDTO fg = new FormaGiuridicaDTO();
		fg.setDescFormaGiuridica(rs.getString("DESC_FORMA_GIURIDICA"));
		fg.setIdFormaGiuridica(rs.getLong("ID_FORMA_GIURIDICA"));
		
		return fg;
	}

}
