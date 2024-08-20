/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoSegnalazioneCorteContiDTO;

public class StoricoSegnalazioneCorteContiDTORowMapper implements RowMapper<StoricoSegnalazioneCorteContiDTO> {

	@Override
	public StoricoSegnalazioneCorteContiDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			
		
		StoricoSegnalazioneCorteContiDTO sscc =  new StoricoSegnalazioneCorteContiDTO(); 
		
		sscc.setIdSegnalazioneCorteConti(rs.getLong("ID_SEGNALAZIONE_CORTE_CONTI"));
		sscc.setIdProgetto(rs.getLong("ID_PROGETTO"));
		sscc.setDataSegnalazione(rs.getDate("DT_SEGNALAZIONE"));
		sscc.setNumProtocolloSegn(rs.getString("NUM_PROTOCOLLO_SEGN"));
		sscc.setImpCredResCapitale(rs.getBigDecimal("IMP_CRED_RES_CAPITALE"));
		sscc.setImpOneriAgevolaz(rs.getBigDecimal("IMP_ONERI_AGEVOLAZ"));
		sscc.setImpQuotaSegnalaz(rs.getBigDecimal("IMP_QUOTA_SEGNALAZ"));
		sscc.setImpGaranzia(rs.getBigDecimal("IMP_GARANZIA"));
		sscc.setFlagPianoRientro(rs.getString("FLAG_PIANO_RIENTRO"));
		sscc.setDataPianoRientro(rs.getDate("DT_PIANO_RIENTRO"));
		sscc.setFlagSaldoStralcio(rs.getString("FLAG_SALDO_STRALCIO"));
		sscc.setDataSaldoStralcio(rs.getDate("DT_SALDO_STRALCIO"));
		sscc.setFlagPagamIntegrale(rs.getString("FLAG_PAGAM_INTEGRALE"));
		sscc.setDataPagamento(rs.getDate("DT_PAGAMENTO"));
		sscc.setFlagDissegnalazione(rs.getString("FLAG_DISSEGNALAZIONE"));
		sscc.setDataDissegnalazione(rs.getDate("DT_DISSEGNALAZIONE"));
		sscc.setNumProtocolloDiss(rs.getString("NUM_PROTOCOLLO_DISS"));
		sscc.setFlagDecretoArchiv(rs.getString("FLAG_DECRETO_ARCHIV"));
		sscc.setDataArchiv(rs.getDate("DT_ARCHIV"));
		sscc.setNumProtocolloArchiv(rs.getString("NUM_PROTOCOLLO_ARCHIV"));
		sscc.setFlagComunicazRegione(rs.getString("FLAG_COMUNICAZ_REGIONE"));
		sscc.setNote(rs.getString("NOTE"));
		sscc.setNome(rs.getString("NOME"));
		sscc.setCognome(rs.getString("COGNOME"));
		sscc.setDataInserimento(rs.getDate("DT_INSERIMENTO"));
		sscc.setDataAggiornamento(rs.getDate("DT_AGGIORNAMENTO"));
		
		
		return sscc;
	}

}
