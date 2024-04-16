/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfree.util.Log;
import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbworkspace.dto.BandoWidgetDTO;

public class BandoWidgetDTORowMapper implements RowMapper<BandoWidgetDTO> {

	@Override
	public BandoWidgetDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		BandoWidgetDTO cm = new BandoWidgetDTO();

		cm.setProgrBandoLineaIntervento(rs.getLong("PROGRBANDOLINEAINTERVENTO"));
		cm.setNomeBandoLinea(rs.getString("NOMEBANDOLINEA"));
		try {
			if (rs.getString("IDBANDOLINSOGGWIDGET") != null) {
				cm.setIdBandoLinSoggWidget(rs.getLong("IDBANDOLINSOGGWIDGET"));
			}
		} catch (SQLException e) {
			Log.info("Errore gestito");
		}

		return cm;
	}

}
