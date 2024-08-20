/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTFileEntitaVO;

public class FileEntitaRowMapper implements RowMapper<PbandiTFileEntitaVO> {

	@Override
	public PbandiTFileEntitaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiTFileEntitaVO cm = new PbandiTFileEntitaVO();
		cm.setIdFileEntita(rs.getBigDecimal("IDFILEENTITA"));
		cm.setIdEntita(rs.getBigDecimal("IDENTITA"));
		cm.setIdTarget(rs.getBigDecimal("IDTARGET"));
		cm.setIdProgetto(rs.getBigDecimal("IDPROGETTO"));
		cm.setIdFile(rs.getBigDecimal("IDFILE"));
		cm.setDtEntita(rs.getDate("DTENTITA"));
		cm.setFlagEntita(rs.getString("FLAGENTITA"));
		return cm;
	}

}
