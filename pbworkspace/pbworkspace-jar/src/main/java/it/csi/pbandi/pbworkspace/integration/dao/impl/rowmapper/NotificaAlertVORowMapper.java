/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbworkspace.integration.vo.NotificaAlertVO;

public class NotificaAlertVORowMapper implements RowMapper<NotificaAlertVO> {

	@Override
	public NotificaAlertVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		NotificaAlertVO cm = new NotificaAlertVO();

		cm.setIdNotificaAlert(rs.getLong("idNotificaAlert"));
		cm.setIdSoggettoNotifica(rs.getLong("idSoggettoNotifica"));
		cm.setIdFrequenza(rs.getLong("idFrequenza"));
		cm.setDescrNotifica(rs.getString("descrNotifica"));
		cm.setDtFineValidita(rs.getDate("dtFineValidita"));
		cm.setHasProgettiAssociated(rs.getString("hasProgettiAssociated"));

		return cm;
	}

}
