/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbservizit.dto.cronoprogrammaFasi.CronoprogrammaFasiItem;
import it.csi.pbandi.pbservizit.integration.vo.BeneficiarioSoggettoRuoloVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRProgettoIterVO;

public class PbandiRProgettoIterVORowMapper implements RowMapper<PbandiRProgettoIterVO> {

	@Override
	public PbandiRProgettoIterVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiRProgettoIterVO cm = new PbandiRProgettoIterVO();

		cm.setIdProgettoIter(rs.getLong("id_progetto_iter"));
		cm.setIdProgetto(rs.getLong("id_progetto"));
		cm.setIdIter(rs.getLong("id_iter"));
		cm.setFlagFaseChiusa(rs.getLong("flag_fase_chiusa"));
		if (rs.getLong("id_dichiarazione_spesa") != 0) {
			cm.setIdDichiarazioneSpesa(rs.getLong("id_dichiarazione_spesa"));
		}

		return cm;
	}
}
