/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbservizit.dto.cronoprogrammaFasi.CronoprogrammaFasiItem;
import it.csi.pbandi.pbservizit.integration.vo.BeneficiarioSoggettoRuoloVO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.AltriCostiDTO;

public class AltriCostiRowMapper implements RowMapper<AltriCostiDTO> {

	@Override
	public AltriCostiDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		AltriCostiDTO ac = new AltriCostiDTO();

		ac.setIdDCeAltriCosti(rs.getLong("id_d_ce_altri_costi"));
		ac.setDescBreveCeAltriCosti(rs.getString("desc_breve_ce_altri_costi"));
		ac.setDescCeAltriCosti(rs.getString("desc_ce_altri_costi"));
		if (rs.getString("id_t_ce_altri_costi") != null) {
			ac.setIdTCeAltriCosti(rs.getLong("id_t_ce_altri_costi"));
		}
		if (rs.getString("imp_ce_approvato") != null) {
			ac.setImpCeApprovato(rs.getDouble("imp_ce_approvato"));
		}
		if (rs.getString("imp_cd_propmod") != null) {
			ac.setImpCePropmod(rs.getDouble("imp_cd_propmod"));
		}

		return ac;
	}
}
