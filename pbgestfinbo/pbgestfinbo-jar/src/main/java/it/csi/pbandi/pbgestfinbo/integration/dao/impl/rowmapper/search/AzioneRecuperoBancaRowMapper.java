/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.search.AzioneRecuperoBancaVO;

public class AzioneRecuperoBancaRowMapper implements RowMapper<AzioneRecuperoBancaVO> {

	@Override
	public AzioneRecuperoBancaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		
		AzioneRecuperoBancaVO azione = new AzioneRecuperoBancaVO();
		
		azione.setIdAzioneRecupero(rs.getLong("ID_AZIONE_RECUPERO_BANCA"));
		azione.setIdProgetto(rs.getLong("ID_PROGETTO"));
		azione.setIdAttivitaAzione(rs.getLong("ID_ATTIVITA_AZIONE"));
		azione.setNumProtocollo(rs.getLong("NUM_PROTOCOLLO"));
		azione.setDataAzione(rs.getDate("DT_AZIONE"));
		azione.setDataAggiornamento(rs.getDate("DT_AGGIORNAMENTO"));
		azione.setDataInizioValidita(rs.getDate("DT_INIZIO_VALIDITA"));
		azione.setDataFineValidita(rs.getDate("DT_FINE_VALIDITA"));
		azione.setDataInserimento(rs.getDate("DT_INSERIMENTO"));
		azione.setNote(rs.getString("NOTE"));
		azione.setIdUtenteIns(rs.getLong("ID_UTENTE_INS"));
		azione.setIdUtenteAgg(rs.getLong("ID_UTENTE_AGG"));
		
		return azione;
	}

}
