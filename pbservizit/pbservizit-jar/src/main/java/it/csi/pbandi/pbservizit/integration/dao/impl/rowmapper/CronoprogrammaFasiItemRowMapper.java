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

public class CronoprogrammaFasiItemRowMapper implements RowMapper<CronoprogrammaFasiItem> {

	@Override
	public CronoprogrammaFasiItem mapRow(ResultSet rs, int rowNum) throws SQLException {
		CronoprogrammaFasiItem cm = new CronoprogrammaFasiItem();

		cm.setIdIter(rs.getLong("id_iter"));
		cm.setDescIter(rs.getString("desc_iter"));
		cm.setIdFaseMonit(rs.getLong("id_fase_monit"));
		cm.setDescFaseMonit(rs.getString("desc_fase_monit"));
		cm.setDataLimite(rs.getDate("data_limite"));
		cm.setDataPrevista(rs.getDate("data_prevista"));
		cm.setDataEffettiva(rs.getDate("data_effettiva"));
		cm.setEstremiAttoAmministrativo(rs.getString("estremi_atto_amministrativo"));
		cm.setMotivoScostamento(rs.getString("motivo_scostamento"));
		if (rs.getString("id_dichiarazione_spesa") != null) {
			cm.setIdDichiarazioneSpesa(rs.getLong("id_dichiarazione_spesa"));
		}
		if (rs.getString("id_tipo_dichiaraz_spesa") != null) {
			cm.setIdTipoDichiarazSpesa(rs.getLong("id_tipo_dichiaraz_spesa"));
		}
		if (rs.getString("flag_fase_chiusa") != null) {
			cm.setFlagFaseChiusa(rs.getLong("flag_fase_chiusa"));
		}
		cm.setFlagEstremiVisObb(rs.getString("flag_estremi_vis_obb"));
		cm.setFlagPrevAbilitata(rs.getString("flag_prev_abilitata"));
		return cm;
	}
}
