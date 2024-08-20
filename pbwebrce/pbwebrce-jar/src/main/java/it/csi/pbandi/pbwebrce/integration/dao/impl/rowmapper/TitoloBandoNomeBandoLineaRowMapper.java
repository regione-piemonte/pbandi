/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebrce.dto.TitoloBandoNomeBandoLineaDTO;

public class TitoloBandoNomeBandoLineaRowMapper implements RowMapper<TitoloBandoNomeBandoLineaDTO> {

	@Override
	public TitoloBandoNomeBandoLineaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		TitoloBandoNomeBandoLineaDTO cm = new TitoloBandoNomeBandoLineaDTO();

		cm.setNomeBandoLinea(rs.getString("NOME_BANDO_LINEA"));
		cm.setTitoloBando(rs.getString("TITOLO_BANDO"));

		return cm;
	}

}
