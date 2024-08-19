/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCCostantiVO;

public class CostantiRowMapper implements RowMapper<PbandiCCostantiVO> {

	@Override
	public PbandiCCostantiVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiCCostantiVO cm = new PbandiCCostantiVO();
		cm.setAttributo(rs.getString("ATTRIBUTO"));
		cm.setValore(rs.getString("VALORE"));
		return cm;
	}

}
