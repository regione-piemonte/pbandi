/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.CessioneQuotaVO;

public class CessioneQuotaVORowMapper implements RowMapper<CessioneQuotaVO> {

	@Override
	public CessioneQuotaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		CessioneQuotaVO bc = new CessioneQuotaVO();
		
		bc.setIdProgetto(rs.getLong("ID_PROGETTO"));
		
		bc.setIdCurrentRecord(rs.getString("ID_SERVICER"));
		bc.setIdModalitaAgevolazione(rs.getLong("ID_MODALITA_AGEVOLAZIONE"));
		bc.setImpCessQuotaCap(rs.getBigDecimal("IMP_CESSIONE_CAPITALE"));
		bc.setImpCessOneri(rs.getBigDecimal("IMP_CESSIONE_ONERI"));
		bc.setImpCessInterMora(rs.getBigDecimal("IMP_CESSIONE_INTERESSI_MORA"));
		bc.setImpTotCess(rs.getBigDecimal("IMP_CESSIONE_COMPLESSIVA"));
		bc.setDataCessione(rs.getDate("DT_CESSIONE"));
		bc.setCorrispettivoCess(rs.getBigDecimal("IMP_CORRISP_CESSIONE"));
		bc.setNominativoCess(rs.getString("DENOM_CESSIONARIO"));
		bc.setStatoCess(rs.getString("DESC_ATTIVITA_SERVICER"));
		bc.setNote(rs.getString("NOTE"));
		
		bc.setStor_dataInizio(rs.getString("DT_INIZIO_VALIDITA"));
		bc.setStor_dataFine(rs.getString("DT_FINE_VALIDITA"));
		bc.setStor_dataInserimento(rs.getString("DT_INSERIMENTO"));
		bc.setStor_istruttore(rs.getString("ISTRUTTORE"));
		
		return bc;
	}

}
