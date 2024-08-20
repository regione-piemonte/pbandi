/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.MessaMoraVO;

public class MessaMoraVORowMapper implements RowMapper<MessaMoraVO> {

	@Override
	public MessaMoraVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		MessaMoraVO mm =  new MessaMoraVO(); 
		
		mm.setIdMessaMora(rs.getLong("ID_MESSA_IN_MORA"));
		mm.setIdProgetto(rs.getLong("ID_PROGETTO"));
		mm.setIdAttivitaMora(rs.getLong("ID_ATTIVITA_MORA"));
		mm.setDataMessaMora(rs.getDate("DT_MESSA_IN_MORA"));
		mm.setImpMessaMoraComplessiva(rs.getBigDecimal("IMP_MESSA_IN_MORA_COMPLESSIVA"));
		mm.setImpQuotaCapitaleRevoca(rs.getBigDecimal("IMP_QUOTA_CAPITALE_REVOCA"));
		mm.setImpAgevolazRevoca(rs.getBigDecimal("IMP_AGEVOLAZ_REVOCA"));
		mm.setImpCreditoResiduo(rs.getBigDecimal("IMP_CREDITO_RESIDUO"));
		mm.setImpInteressiMora(rs.getBigDecimal("IMP_INTERESSI_MORA"));
		mm.setDataNotifica(rs.getDate("DT_NOTIFICA"));
		mm.setIdAttivitaRecuperoMora(rs.getLong("ID_ATTIVITA_RECUPERO_MORA"));
		mm.setDataPagamento(rs.getDate("DT_PAGAMENTO"));
		mm.setNote(rs.getString("NOTE"));
	    mm.setDataInizioValidita(rs.getDate("DT_INIZIO_VALIDITA"));
		mm.setDataFineValidita(rs.getDate("DT_FINE_VALIDITA"));
		mm.setIdUtenteIns(rs.getLong("ID_UTENTE_INS"));
		mm.setIdUtenteAgg(rs.getLong("ID_UTENTE_AGG"));
		mm.setDataInserimento(rs.getDate("DT_INSERIMENTO"));
		mm.setDataAggiornamento(rs.getDate("DT_AGGIORNAMENTO"));
		mm.setNumeroProtocollo(rs.getString("NUM_PROTOCOLLO")); // New
		
		return mm;
	}

}
