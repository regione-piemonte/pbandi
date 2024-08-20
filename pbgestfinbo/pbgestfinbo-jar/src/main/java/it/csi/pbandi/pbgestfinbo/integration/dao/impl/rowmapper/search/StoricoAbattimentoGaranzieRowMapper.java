/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoAbbattimentoGaranzieDTO;

public class StoricoAbattimentoGaranzieRowMapper implements RowMapper<StoricoAbbattimentoGaranzieDTO> {

	@Override
	public StoricoAbbattimentoGaranzieDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		
			
		StoricoAbbattimentoGaranzieDTO storico =  new StoricoAbbattimentoGaranzieDTO(); 
		
		storico.setIdAbbattimentoGaranzie( rs.getLong("ID_ABBATTIM_GARANZIE"));
		storico.setCognome(rs.getString("COGNOME"));
		storico.setNome(rs.getString("NOME"));
		storico.setDataInserimento(rs.getDate("DT_INSERIMENTO"));
		storico.setDescAbbattimento(rs.getString("DESC_ATT_MONIT_CRED"));
		storico.setNote(rs.getString("NOTE"));
		storico.setImpIniziale(rs.getBigDecimal("IMP_INIZIALE"));
		storico.setImpLiberato(rs.getBigDecimal("IMP_LIBERATO"));
		storico.setImpNuovo(rs.getBigDecimal("IMP_NUOVO"));
		storico.setDataAbbattimento(rs.getDate("DT_ABBATTIM_GARANZIE"));
		storico.setDataAggiornamento(rs.getDate("DT_AGGIORNAMENTO"));
		
		return storico;
	}

}
