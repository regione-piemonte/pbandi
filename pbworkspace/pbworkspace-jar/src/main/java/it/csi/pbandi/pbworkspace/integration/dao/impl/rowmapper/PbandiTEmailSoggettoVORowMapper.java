/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbworkspace.integration.vo.PbandiTEmailSoggettoVO;

public class PbandiTEmailSoggettoVORowMapper implements RowMapper<PbandiTEmailSoggettoVO> {

	@Override
	public PbandiTEmailSoggettoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiTEmailSoggettoVO vo = new PbandiTEmailSoggettoVO();

		vo.setEmailSoggetto(rs.getString("emailSoggetto"));
		vo.setIdSoggetto(rs.getLong("idSoggetto"));

		return vo;
	}

}
