/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiDMotivoAssenzaCigVO;

public class MotiveAssenzaRowMapper implements RowMapper<PbandiDMotivoAssenzaCigVO> {

	@Override
	public PbandiDMotivoAssenzaCigVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiDMotivoAssenzaCigVO cm = new PbandiDMotivoAssenzaCigVO();
		cm.setIdMotivoAssenzaCig(rs.getBigDecimal("IDMOTIVOASSENZACIG"));
		cm.setDescMotivoAssenzaCig(rs.getString("DESCMOTIVOASSENZACIG"));
		cm.setDescBreveMotivoAssenzaCig(rs.getString("DESCBREVEMOTIVOASSENZACIG"));
		cm.setDtInizioValidita(rs.getDate("DTINIZIOVALIDITA"));
		cm.setDtFineValidita(rs.getDate("DTFINEVALIDITA"));
		cm.settc22(rs.getString("TC22"));
		return cm;
	}



}
