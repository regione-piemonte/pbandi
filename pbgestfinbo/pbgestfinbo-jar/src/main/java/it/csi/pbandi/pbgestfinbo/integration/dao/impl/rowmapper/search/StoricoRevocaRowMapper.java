/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoRevocaDTO;

public class StoricoRevocaRowMapper implements RowMapper<StoricoRevocaDTO> {

	@Override
	public StoricoRevocaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		StoricoRevocaDTO storico= new StoricoRevocaDTO();
		
		storico.setCognome(rs.getString("COGNOME"));
		storico.setNome(rs.getString("NOME"));
		storico.setDataInserimento(rs.getDate("DT_INSERIMENTO"));
		
		return storico;
	}
	

}
