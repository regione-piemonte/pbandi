/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebrce.dto.CupProgettoNumeroDomandaDTO;
import it.csi.pbandi.pbwebrce.dto.DtFineEffEstremiAttoDTO;
import it.csi.pbandi.pbwebrce.dto.TitoloBandoNomeBandoLineaDTO;

public class DtFineEffEstremiAttoRowMapper implements RowMapper<DtFineEffEstremiAttoDTO> {

	@Override
	public DtFineEffEstremiAttoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		DtFineEffEstremiAttoDTO cm = new DtFineEffEstremiAttoDTO();

		cm.setDtFineEffettiva(rs.getDate("DT_FINE_EFFETTIVA"));
		cm.setEstremiAttoAmministrativo(rs.getString("ESTREMI_ATTO_AMMINISTRATIVO"));

		return cm;
	}

}
