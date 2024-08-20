/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.dto.profilazione.RevocaBancariaDTO;

public class RevocaBancariaRowMapper implements RowMapper<RevocaBancariaDTO>{

	@Override
	public RevocaBancariaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		RevocaBancariaDTO rb =  new RevocaBancariaDTO(); 
		
		rb.setIdRevoca(rs.getLong("ID_REVOCA_BANCARIA"));
		rb.setIdProgetto(rs.getLong("ID_PROGETTO"));
		rb.setDataRicezComunicazioneRevoca(rs.getDate("DT_RICEZIONE_COMUNICAZ"));
		rb.setDataRevoca(rs.getDate("DT_REVOCA"));
		rb.setImpDebitoResiduoBanca(rs.getBigDecimal("IMP_DEBITO_RESIDUO_BANCA"));;
		rb.setImpDebitoResiduoFinpiemonte(rs.getBigDecimal("IMP_DEBITO_RESIDUO_FP"));;
		rb.setPerCofinanziamentoFinpiemonte(rs.getBigDecimal("PERC_COFINANZ_FP"));
		rb.setNumeroProtocollo(rs.getString("NUM_PROTOCOLLO"));
		rb.setNote(rs.getString("NOTE"));
		rb.setDataInizioValidita(rs.getDate("DT_INIZIO_VALIDITA"));
		rb.setDataFineValidita(rs.getDate("DT_FINE_VALIDITA"));
		rb.setDataInserimento(rs.getDate("DT_INSERIMENTO"));
		rb.setDataAggiornamento(rs.getDate("DT_AGGIORNAMENTO"));
		rb.setIdUtenteIns(rs.getLong("ID_UTENTE_INS"));
		rb.setIdUtenteAgg(rs.getLong("ID_UTENTE_AGG"));

		return rb;
	}
	

}
