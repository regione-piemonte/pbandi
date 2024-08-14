/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.PbandiDLineaDiInterventoVO;

public class LineeInterventoRowMapper implements RowMapper<PbandiDLineaDiInterventoVO>{

	@Override
	public PbandiDLineaDiInterventoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiDLineaDiInterventoVO cm = new PbandiDLineaDiInterventoVO();
		cm.setIdLineaDiIntervento(rs.getBigDecimal("C10"));
		cm.setDescLinea(rs.getString("C5"));
		cm.setDescBreveLinea(rs.getString("C17"));
		return cm;
	}

}
