/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.TransazioneVO;

public class TransazioneVORowMapper implements RowMapper<TransazioneVO> {

	@Override
	public TransazioneVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		TransazioneVO trans =  new TransazioneVO(); 
		
		trans.setIdTransazione(rs.getLong("ID_TRANSAZIONE_BANCA"));
		trans.setIdProgetto(rs.getLong("ID_PROGETTO"));
		trans.setImpRiconciliato(rs.getBigDecimal("IMP_RICONCILIATO"));
		trans.setImpTransato(rs.getBigDecimal("IMP_TRANSATO"));
		trans.setPercTransazione(rs.getBigDecimal("PERC_TRANSAZIONE"));
		trans.setImpPagato(rs.getBigDecimal("IMP_PAGATO"));
		trans.setIdBanca(rs.getLong("ID_BANCA"));
		trans.setNote(rs.getString("NOTE"));
		trans.setDataInizioValidita(rs.getDate("DT_INIZIO_VALIDITA"));
		trans.setDataFineValidita(rs.getDate("DT_FINE_VALIDITA"));
		trans.setIdUtenteIns(rs.getLong("ID_UTENTE_INS"));
		trans.setIdUtenteAgg(rs.getLong("ID_UTENTE_AGG"));
		trans.setDataInserimento(rs.getDate("DT_INSERIMENTO"));
		trans.setDataAggiornamento(rs.getDate("DT_AGGIORNAMENTO"));
		trans.setDescBanca(rs.getString("DESC_BANCA"));
		
		
		
		return trans;
	}

}
