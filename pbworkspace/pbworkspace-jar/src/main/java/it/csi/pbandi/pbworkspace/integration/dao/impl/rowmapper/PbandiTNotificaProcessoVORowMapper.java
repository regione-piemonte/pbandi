/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbworkspace.integration.vo.PbandiTNotificaProcessoVO;

public class PbandiTNotificaProcessoVORowMapper implements RowMapper<PbandiTNotificaProcessoVO> {

	@Override
	public PbandiTNotificaProcessoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiTNotificaProcessoVO vo = new PbandiTNotificaProcessoVO();

		vo.setIdEntita(rs.getLong("ID_ENTITA"));
		vo.setIdNotifica(rs.getLong("ID_NOTIFICA"));
		vo.setIdProgetto(rs.getLong("ID_PROGETTO"));
		vo.setIdRuoloDiProcessoDest(rs.getLong("ID_RUOLO_DI_PROCESSO_DEST"));
		vo.setIdTarget(rs.getLong("ID_TARGET"));
		vo.setIdTemplateNotifica(rs.getLong("ID_TEMPLATE_NOTIFICA"));
		vo.setIdUtenteAgg(rs.getLong("ID_UTENTE_AGG"));
		vo.setIdUtenteMitt(rs.getLong("ID_UTENTE_MITT"));
		vo.setMessageNotifica(rs.getString("MESSAGE_NOTIFICA"));
		vo.setStatoNotifica(rs.getString("STATO_NOTIFICA"));
		vo.setSubjectNotifica(rs.getString("SUBJECT_NOTIFICA"));

		// PK: faccio in questo modo altrimenti perdo le HH:mm:ss
		String stDataNotifica = rs.getString("DT_NOTIFICA");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0");
		try {
			if (stDataNotifica != null) {
				java.util.Date dtJv = sdf.parse(stDataNotifica);
				java.sql.Date dtsql = new java.sql.Date(dtJv.getTime());
				vo.setDtNotifica(dtsql);
			}
		} catch (ParseException e) {
			vo.setDtNotifica(null);
		}

		String stDataAggNotifica = rs.getString("DT_AGG_STATO_NOTIFICA");
		try {
			if (stDataAggNotifica != null) {
				java.util.Date dtJv = sdf.parse(stDataAggNotifica);
				java.sql.Date dtsql = new java.sql.Date(dtJv.getTime());
				vo.setDtAggStatoNotifica(dtsql);
			}
		} catch (ParseException e) {
			vo.setDtAggStatoNotifica(null);
		}

		return vo;
	}

}
