/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import it.csi.pbandi.pbservizit.integration.vo.PermessoVO;

public class PermessoVORowMapper implements RowMapper<PermessoVO> {

	@Override
	public PermessoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PermessoVO cm = new PermessoVO();

		cm.setIdPermesso(rs.getLong("idPermesso"));
		cm.setDescMenu(rs.getString("descMenu"));
		cm.setDescBrevePermesso(rs.getString("descBrevePermesso"));

		return cm;
	}

}
