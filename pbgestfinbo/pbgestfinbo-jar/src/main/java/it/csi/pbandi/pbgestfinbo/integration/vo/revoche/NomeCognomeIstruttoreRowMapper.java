/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.revoche;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class NomeCognomeIstruttoreRowMapper implements RowMapper<NomeCognomeIstruttore> {
	
	
	@Override
	public NomeCognomeIstruttore mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		NomeCognomeIstruttore istruttore = new NomeCognomeIstruttore();
		
		istruttore.setIdSoggetto(rs.getLong("id_soggetto"));
		istruttore.setCognome(rs.getString("cognome"));
		istruttore.setNome(rs.getString("nome"));
		
		return istruttore;
	}
	
	

}
