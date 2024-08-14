/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.PbandiLProcessoBatchVO;

public class ProcessoBatchRowMapper implements RowMapper<PbandiLProcessoBatchVO> {

	@Override
	public PbandiLProcessoBatchVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiLProcessoBatchVO cm = new PbandiLProcessoBatchVO();
		cm.setIdProcessoBatch(rs.getBigDecimal("IDPROCESSOBATCH"));
		cm.setDtInizioElaborazione(rs.getDate("DTINIZIOELABORAZIONE"));
		cm.setDtFineElaborazione(rs.getDate("DTFINEELABORAZIONE"));
		cm.setIdNomeBatch(rs.getBigDecimal("IDNOMEBATCH"));
		cm.setFlagEsito(rs.getString("FLAGESITO"));
		return cm;
	}

}
