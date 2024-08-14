/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.PbandiDNomeBatchVO;

public class NomePathRowMapper implements RowMapper<PbandiDNomeBatchVO>{

	@Override
	public PbandiDNomeBatchVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiDNomeBatchVO cm = new PbandiDNomeBatchVO();
		cm.setIdNomeBatch(rs.getBigDecimal("IDNOMEBATCH"));
		cm.setNomeBatch(rs.getString("NOMEBATCH"));
		cm.setDescBatch(rs.getString("DESCBATCH"));
		return cm;
	}

}
