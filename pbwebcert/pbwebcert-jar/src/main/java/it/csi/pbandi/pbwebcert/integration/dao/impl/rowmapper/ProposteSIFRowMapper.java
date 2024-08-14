/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class ProposteSIFRowMapper implements RowMapper<BigDecimal> {

	@Override
	public BigDecimal mapRow(ResultSet rs, int rowNum) throws SQLException {
		BigDecimal idProgetto = rs.getBigDecimal("ID_PROGETTO");
		return idProgetto;
	}



}
