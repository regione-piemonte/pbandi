/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbservizit.dto.help.HelpDTO;

public class HelpRowMapper implements RowMapper<HelpDTO> {

	@Override
	public HelpDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		HelpDTO cm = new HelpDTO();

		cm.setIdHelp(rs.getLong("ID_HELP"));
		Clob clob = rs.getClob("TESTO_HELP");
		cm.setTestoHelp(clob.getSubString(1, (int) clob.length()));
		return cm;
	}
}
