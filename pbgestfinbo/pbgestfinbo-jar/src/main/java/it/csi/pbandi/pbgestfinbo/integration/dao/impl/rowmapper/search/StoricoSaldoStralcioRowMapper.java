/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoSaldoStralcioDTO;

public class StoricoSaldoStralcioRowMapper implements RowMapper<StoricoSaldoStralcioDTO> {

	@Override
	public StoricoSaldoStralcioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		StoricoSaldoStralcioDTO sss = new StoricoSaldoStralcioDTO(); 
		
		sss.setIdSaldoStralcio(rs.getLong("ID_SALDO_STRALCIO"));
		sss.setNome(rs.getString("NOME"));
		sss.setCognome(rs.getString("COGNOME"));
		sss.setDataAcettazione(rs.getDate("DT_ACCETTAZIONE"));
		sss.setDataEsito(rs.getDate("DT_ESITO"));
		sss.setDataInserimento(rs.getDate("DT_INSERIMENTO"));
		sss.setDataPagamConfidi(rs.getDate("DT_PAGAM_CONFIDI"));
		sss.setDataPagamDebitore(rs.getDate("DT_PAGAM_DEBITORE"));
		sss.setImpConfindi(rs.getBigDecimal("IMP_CONFIDI"));
		sss.setImpDebitore(rs.getBigDecimal("IMP_DEBITORE"));
		sss.setImpPerdita(rs.getBigDecimal("IMP_PERDITA"));
		sss.setImpRecuperato(rs.getBigDecimal("IMP_RECUPERATO"));
		sss.setNote(rs.getString("NOTE"));
		sss.setDataProposta(rs.getDate("DT_PROPOSTA"));
		sss.setIdEsito(rs.getLong("ID_ATTIVITA_ESITO"));
		sss.setIdRecupero(rs.getLong("ID_ATTIVITA_RECUPERO"));
		sss.setDescEsito(rs.getString("descEsito"));
		sss.setDescRecupero(rs.getString("descRecupero"));
		sss.setDescSaldoStralcio(rs.getString("descSaldoStralcio"));
		
		String temp = rs.getString("FLAG_AGEVOLAZIONE");
		sss.setFlagAgevolazione("S".equals(temp) ? true : false);
		sss.setImpDisimpegno(rs.getBigDecimal("IMP_DISIMPEGNO"));
		
		return sss;
	}

}
