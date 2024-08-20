/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiDTipologiaAppaltoVO;


public class TipoAppaltoRowMapper implements RowMapper<PbandiDTipologiaAppaltoVO> {

	@Override
	public PbandiDTipologiaAppaltoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiDTipologiaAppaltoVO cm = new PbandiDTipologiaAppaltoVO();
		cm.setIdTipologiaAppalto(rs.getBigDecimal("ID_TIPOLOGIA_APPALTO"));
		cm.setDescTipologiaAppalto(rs.getString("DESC_TIPOLOGIA_APPALTO"));
		cm.setDescBreveAppalto(rs.getString("DESC_BREVE_APPALTO"));
		
		return cm;
	}



}
