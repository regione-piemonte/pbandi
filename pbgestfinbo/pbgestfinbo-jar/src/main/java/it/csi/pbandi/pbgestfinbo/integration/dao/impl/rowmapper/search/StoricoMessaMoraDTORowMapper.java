/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoMessaMoraDTO;

public class StoricoMessaMoraDTORowMapper implements RowMapper<StoricoMessaMoraDTO> {

	@Override
	public StoricoMessaMoraDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		StoricoMessaMoraDTO smm =  new StoricoMessaMoraDTO(); 
		
		smm.setCognome(rs.getString("COGNOME"));
		smm.setNome(rs.getString("NOME"));
		smm.setNote(rs.getString("NOTE"));
		smm.setDataInserimento(rs.getDate("DT_INSERIMENTO"));
		smm.setDataMessaMora(rs.getDate("DT_MESSA_IN_MORA"));
		smm.setDataNotifica(rs.getDate("DT_NOTIFICA"));
		smm.setDataPagamento(rs.getDate("DT_PAGAMENTO"));
		smm.setDescMessaMora(rs.getString("descMessaMora"));
		smm.setDescRecupero(rs.getString("descRecupero"));
		smm.setIdMessaMora(rs.getLong("ID_MESSA_IN_MORA"));
		smm.setImpAgevolazRevoca(rs.getBigDecimal("IMP_AGEVOLAZ_REVOCA"));
		smm.setImpCreditoResiduo(rs.getBigDecimal("IMP_CREDITO_RESIDUO"));
		smm.setImpInteressiMora(rs.getBigDecimal("IMP_INTERESSI_MORA"));
		smm.setImpMessaMoraComplessiva(rs.getBigDecimal("IMP_MESSA_IN_MORA_COMPLESSIVA"));
		smm.setImpQuotaCapitaleRevoca(rs.getBigDecimal("IMP_QUOTA_CAPITALE_REVOCA"));
		smm.setNumeroProtocollo(rs.getString("NUM_PROTOCOLLO")); // New
	
		return smm;
	}

}
