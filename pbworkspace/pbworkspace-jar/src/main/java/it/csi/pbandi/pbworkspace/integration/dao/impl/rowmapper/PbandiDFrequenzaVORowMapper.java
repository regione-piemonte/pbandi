/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbworkspace.integration.vo.FrequenzaVO;

public class PbandiDFrequenzaVORowMapper implements RowMapper<FrequenzaVO> {

	@Override
	public FrequenzaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		FrequenzaVO cm = new FrequenzaVO();

		cm.setIdFrequenza(rs.getLong("idFrequenza"));
		cm.setDescrFrequenza(rs.getString("descrFrequenza"));

		return cm;
	}

}
