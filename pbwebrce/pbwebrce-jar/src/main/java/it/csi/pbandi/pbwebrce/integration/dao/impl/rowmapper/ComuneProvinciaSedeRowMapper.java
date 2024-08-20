/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebrce.dto.ComuneProvinciaSedeDTO;
import it.csi.pbandi.pbwebrce.dto.CupProgettoNumeroDomandaDTO;
import it.csi.pbandi.pbwebrce.dto.TitoloBandoNomeBandoLineaDTO;

public class ComuneProvinciaSedeRowMapper implements RowMapper<ComuneProvinciaSedeDTO> {

	@Override
	public ComuneProvinciaSedeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ComuneProvinciaSedeDTO cm = new ComuneProvinciaSedeDTO();

		cm.setDescComune(rs.getString("descComune"));
		cm.setDescProvincia(rs.getString("descProvincia"));

		return cm;
	}

}
