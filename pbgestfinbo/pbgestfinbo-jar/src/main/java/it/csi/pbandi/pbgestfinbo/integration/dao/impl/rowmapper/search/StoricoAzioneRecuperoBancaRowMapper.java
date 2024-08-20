/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoAzioneRecuperoBancaDTO;

public class StoricoAzioneRecuperoBancaRowMapper implements RowMapper<StoricoAzioneRecuperoBancaDTO> {

	@Override
	public StoricoAzioneRecuperoBancaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		StoricoAzioneRecuperoBancaDTO storico = new StoricoAzioneRecuperoBancaDTO(); 
		storico.setCognome(rs.getString("COGNOME"));
		storico.setNome(rs.getString("NOME"));
		storico.setDataInserimento(rs.getDate("DT_INSERIMENTO"));
		storico.setDataAzione(rs.getDate("DT_AZIONE"));
		storico.setDescrizioneAttivita(rs.getString("DESC_ATT_MONIT_CRED"));
		storico.setNote( rs.getString("NOTE"));
		storico.setNumProtocollo(rs.getString("NUM_PROTOCOLLO"));
		storico.setIdAzioneRecuperoBanca(rs.getLong("ID_AZIONE_RECUPERO_BANCA"));
		
		return storico;
	}

}
