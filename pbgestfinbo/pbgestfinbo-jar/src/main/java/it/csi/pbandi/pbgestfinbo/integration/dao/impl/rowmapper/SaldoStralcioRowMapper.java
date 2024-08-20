/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.SaldoStralcioVO;

public class SaldoStralcioRowMapper implements RowMapper<SaldoStralcioVO> {

	@Override
	public SaldoStralcioVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		SaldoStralcioVO ss =  new SaldoStralcioVO(); 
			
		ss.setDataAcettazione(rs.getDate("DT_ACCETTAZIONE"));
		ss.setDataAggiornamento(rs.getDate("DT_AGGIORNAMENTO"));
		ss.setDataEsito(rs.getDate("DT_ESITO"));
		ss.setDataFineValidita(rs.getDate("DT_FINE_VALIDITA"));
		ss.setDataInizioValidita(rs.getDate("DT_INIZIO_VALIDITA"));
		ss.setDataInserimento(rs.getDate("DT_INSERIMENTO"));
		ss.setDataPagamConfidi(rs.getDate("DT_PAGAM_CONFIDI"));
		ss.setDataPagamDebitore(rs.getDate("DT_PAGAM_DEBITORE"));
		ss.setDataProposta(rs.getDate("DT_PROPOSTA"));
		ss.setIdAttivitaEsito(rs.getLong("ID_ATTIVITA_ESITO"));
		ss.setIdAttivitaRecupero(rs.getLong("ID_ATTIVITA_RECUPERO"));
		ss.setIdAttivitaSaldoStralcio(rs.getLong("ID_ATTIVITA_SALDO_STRALCIO"));
		ss.setIdProgetto(rs.getLong("ID_PROGETTO"));
		ss.setIdSaldoStralcio(rs.getLong("ID_SALDO_STRALCIO"));
		ss.setIdUtenteAgg(rs.getLong("ID_UTENTE_AGG"));
		ss.setIdUtenteIns(rs.getLong("ID_UTENTE_INS"));
		ss.setImpConfindi(rs.getBigDecimal("IMP_CONFIDI"));
		ss.setImpDebitore(rs.getBigDecimal("IMP_DEBITORE"));
		ss.setImpPerdita(rs.getBigDecimal("IMP_PERDITA"));
		ss.setImpRecuperato(rs.getBigDecimal("IMP_RECUPERATO"));
		ss.setNote(rs.getString("NOTE"));
		
		String temp = rs.getString("FLAG_AGEVOLAZIONE");
		ss.setFlagAgevolazione("S".equals(temp) ? true : false);
		ss.setImpDisimpegno(rs.getBigDecimal("IMP_DISIMPEGNO"));
		
		return ss;
	}
	
	

}
