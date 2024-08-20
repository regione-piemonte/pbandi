/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.PianoRientroVO;

public class PianoRientroVORowMapper implements RowMapper<PianoRientroVO> {

	@Override
	public PianoRientroVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PianoRientroVO bc = new PianoRientroVO();
		
		bc.setIdCurrentRecord(rs.getBigDecimal("ID_PIANO_RIENTRO"));
		bc.setIdModalitaAgevolazione(rs.getLong("ID_MODALITA_AGEVOLAZIONE"));
		bc.setIdEsito(rs.getBigDecimal("ID_ATTIVITA_ESITO"));
		bc.setEsito(rs.getString("ESITO"));
		bc.setDataEsito(rs.getDate("DT_ESITO"));
		bc.setUtenteModifica(rs.getString("COGNOME_NOME"));
		bc.setDataProposta(rs.getDate("DT_PROPOSTA"));
		bc.setImportoCapitale(rs.getBigDecimal("IMP_QUOTA_CAPITALE"));
		bc.setImportoAgevolazione(rs.getBigDecimal("IMP_AGEVOLAZIONE"));
		bc.setIdRecupero(rs.getBigDecimal("ID_ATTIVITA_RECUPERO"));
		bc.setRecupero(rs.getString("RECUPERO"));
		bc.setNote(rs.getString("NOTE"));
		bc.setDataInserimento(rs.getDate("DT_INSERIMENTO"));
		
		bc.setID_PROGETTO(rs.getString("ID_PROGETTO"));
		bc.setDT_INIZIO_VALIDITA(rs.getString("DT_INIZIO_VALIDITA"));
		bc.setDT_FINE_VALIDITA(rs.getString("DT_FINE_VALIDITA"));
		bc.setID_UTENTE_INS(rs.getString("ID_UTENTE_INS"));
		bc.setID_UTENTE_AGG(rs.getString("ID_UTENTE_AGG"));
		bc.setDT_INSERIMENTO(rs.getString("DT_INSERIMENTO"));
		bc.setDT_AGGIORNAMENTO(rs.getString("DT_AGGIORNAMENTO"));

		
		return bc;
	}

}
