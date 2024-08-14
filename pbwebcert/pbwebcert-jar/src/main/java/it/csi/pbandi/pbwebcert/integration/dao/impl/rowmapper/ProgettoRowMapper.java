/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.PbandiTProgettoVO;

public class ProgettoRowMapper  implements RowMapper<PbandiTProgettoVO> {

	@Override
	public PbandiTProgettoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiTProgettoVO cm = new PbandiTProgettoVO();
		cm.setIdProgetto(rs.getBigDecimal("IDPROGETTO"));
		cm.setCodiceProgetto(rs.getString("CODICEPROGETTO"));
		cm.setCodiceVisualizzato(rs.getString("codicevisualizzato"));
		cm.setTitoloProgetto(rs.getString("titolo_progetto_attuale"));
		return cm;
	}

}
