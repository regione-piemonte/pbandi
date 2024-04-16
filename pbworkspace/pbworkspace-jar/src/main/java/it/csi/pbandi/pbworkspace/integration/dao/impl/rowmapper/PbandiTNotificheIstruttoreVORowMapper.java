/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbworkspace.integration.vo.PbandiTNotificheIstruttoreVO;

public class PbandiTNotificheIstruttoreVORowMapper implements RowMapper<PbandiTNotificheIstruttoreVO> {

	@Override
	public PbandiTNotificheIstruttoreVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiTNotificheIstruttoreVO vo = new PbandiTNotificheIstruttoreVO();

		vo.setIdSoggettoNotifica(rs.getLong("idSoggettoNotifica"));
		vo.setIdSoggetto(rs.getLong("idSoggetto"));
		vo.setIdNotificaAlert(rs.getLong("idNotificaAlert"));
		vo.setIdFrequenza(rs.getLong("idFrequenza"));
		vo.setDtInizioValidita(rs.getDate("dtInizioValidita"));
		vo.setDtFineValidita(rs.getDate("dtFineValidita"));

		return vo;
	}

}
