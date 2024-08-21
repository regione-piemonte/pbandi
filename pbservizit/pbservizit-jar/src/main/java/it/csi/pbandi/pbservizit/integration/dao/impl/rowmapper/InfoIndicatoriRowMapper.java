/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbservizit.dto.indicatori.InfoIndicatore;

public class InfoIndicatoriRowMapper implements RowMapper<InfoIndicatore> {

	@Override
	public InfoIndicatore mapRow(ResultSet rs, int rowNum) throws SQLException {
		InfoIndicatore cm = new InfoIndicatore();
		cm.setInfoIniziale(rs.getString("INFO_INIZIALE"));
		cm.setInfoFinale(rs.getString("INFO_FINALE"));
		return cm;
	}

}
