/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.SegnalazioneCorteContiVO;

public class SegnalazioneCorteContiRowMapper implements RowMapper<SegnalazioneCorteContiVO> {

	@Override
	public SegnalazioneCorteContiVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		SegnalazioneCorteContiVO scc =  new SegnalazioneCorteContiVO();

		scc.setIdSegnalazioneCorteConti(rs.getBigDecimal("ID_SEGNALAZIONE_CORTE_CONTI"));
		scc.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
		scc.setDataSegnalazione(rs.getDate("DT_SEGNALAZIONE"));
		scc.setNumProtocolloSegn(rs.getString("NUM_PROTOCOLLO_SEGN"));
		scc.setImpCredResCapitale(rs.getBigDecimal("IMP_CRED_RES_CAPITALE"));
		scc.setImpOneriAgevolaz(rs.getBigDecimal("IMP_ONERI_AGEVOLAZ"));
		scc.setImpQuotaSegnalaz(rs.getBigDecimal("IMP_QUOTA_SEGNALAZ"));
		scc.setImpGaranzia(rs.getBigDecimal("IMP_GARANZIA"));
		scc.setFlagPianoRientro(rs.getString("FLAG_PIANO_RIENTRO"));
		scc.setDataPianoRientro(rs.getDate("DT_PIANO_RIENTRO"));
		scc.setFlagSaldoStralcio(rs.getString("FLAG_SALDO_STRALCIO"));
		scc.setDataSaldoStralcio(rs.getDate("DT_SALDO_STRALCIO"));
		scc.setFlagPagamIntegrale(rs.getString("FLAG_PAGAM_INTEGRALE"));
		scc.setDataPagamento(rs.getDate("DT_PAGAMENTO"));
		scc.setFlagDissegnalazione(rs.getString("FLAG_DISSEGNALAZIONE"));
		scc.setDataDissegnalazione(rs.getDate("DT_DISSEGNALAZIONE"));
		scc.setNumProtocolloDiss(rs.getString("NUM_PROTOCOLLO_DISS"));
		scc.setFlagDecretoArchiv(rs.getString("FLAG_DECRETO_ARCHIV"));
		scc.setDataArchiv(rs.getDate("DT_ARCHIV"));
		scc.setNumProtocolloArchiv(rs.getString("NUM_PROTOCOLLO_ARCHIV"));
		scc.setFlagComunicazRegione(rs.getString("FLAG_COMUNICAZ_REGIONE"));
		scc.setNote(rs.getString("NOTE"));
		scc.setDataInizioValidita(rs.getDate("DT_INIZIO_VALIDITA"));
		scc.setDataFineValidita(rs.getDate("DT_FINE_VALIDITA"));
		scc.setIdUtenteIns(rs.getLong("ID_UTENTE_INS"));
		scc.setIdUtenteAgg(rs.getLong("ID_UTENTE_AGG"));
		scc.setDataInserimento(rs.getDate("DT_INSERIMENTO"));
		scc.setDataAggiornamento(rs.getDate("DT_AGGIORNAMENTO"));
		
		return scc;
		
	}

}
