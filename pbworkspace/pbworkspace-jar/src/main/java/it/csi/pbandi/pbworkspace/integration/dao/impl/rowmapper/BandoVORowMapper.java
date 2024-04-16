/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbworkspace.integration.vo.BandoVO;

public class BandoVORowMapper implements RowMapper<BandoVO> {

	@Override
	public BandoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		BandoVO cm = new BandoVO();

		cm.setNomeBandoLinea(rs.getString("nomeBandoLinea"));
		cm.setProcesso(rs.getLong("processo"));
		cm.setProgrBandoLineaIntervento(rs.getLong("progrBandoLineaIntervento"));

		return cm;
	}

}
