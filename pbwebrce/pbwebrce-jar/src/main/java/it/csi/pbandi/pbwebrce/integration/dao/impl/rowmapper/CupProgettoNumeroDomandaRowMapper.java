/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebrce.dto.CupProgettoNumeroDomandaDTO;
import it.csi.pbandi.pbwebrce.dto.TitoloBandoNomeBandoLineaDTO;

public class CupProgettoNumeroDomandaRowMapper implements RowMapper<CupProgettoNumeroDomandaDTO> {

	@Override
	public CupProgettoNumeroDomandaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		CupProgettoNumeroDomandaDTO cm = new CupProgettoNumeroDomandaDTO();

		cm.setCup(rs.getString("CUP"));
		cm.setNumeroDomanda(rs.getString("NUMERO_DOMANDA"));

		return cm;
	}

}
