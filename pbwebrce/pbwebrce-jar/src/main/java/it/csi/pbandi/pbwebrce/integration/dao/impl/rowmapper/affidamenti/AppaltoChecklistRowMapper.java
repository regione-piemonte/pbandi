/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTAppaltoChecklistVO;

public class AppaltoChecklistRowMapper implements RowMapper<PbandiTAppaltoChecklistVO> {

	@Override
	public PbandiTAppaltoChecklistVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiTAppaltoChecklistVO cm = new PbandiTAppaltoChecklistVO();
		cm.setIdAppalto(rs.getBigDecimal("IDAPPALTO"));
		cm.setIdAppaltoChecklist(rs.getBigDecimal("IDAPPALTOCHECKLIST"));
		cm.setIdChecklist(rs.getBigDecimal("IDCHECKLIST"));
		return cm;
	}

}
