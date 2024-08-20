/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.NoteGeneraliVO;

public class NoteGeneraliVORowMapper implements RowMapper<NoteGeneraliVO> {

	@Override
	public NoteGeneraliVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		NoteGeneraliVO ng = new NoteGeneraliVO(); 
		
		ng.setDataAggiornamento(rs.getDate("DT_AGGIORNAMENTO"));
		ng.setDataFineValidita( rs.getDate("DT_FINE_VALIDITA"));
		ng.setDataInizioValidita(rs.getDate("DT_INIZIO_VALIDITA"));
		ng.setDataInserimento(rs.getDate("DT_INSERIMENTO"));
		ng.setIdAnnotazione(rs.getLong("ID_ANNOTAZ_GENERALE"));
		ng.setIdProgetto(rs.getLong("ID_PROGETTO"));
		ng.setIdUtenteAgg(rs.getLong("ID_UTENTE_AGG"));
		ng.setIdUtenteIns(rs.getLong("ID_UTENTE_INS"));
		ng.setNote(rs.getString("NOTE"));
		ng.setNomeUtente(rs.getString("NOME")); 
		ng.setCognomeUtente(rs.getString("COGNOME"));
		
		ng.setNomeUtenteAgg(rs.getString("NOME_AGG"));
		ng.setCognomeUtenteAgg(rs.getString("COGNOME_AGG"));
	
		return ng;
	}
	

}
