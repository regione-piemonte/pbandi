/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.EscussioneConfidiVO;

public class EscussioneConfidiVORowMapper implements RowMapper<EscussioneConfidiVO> {

	@Override
	public EscussioneConfidiVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		EscussioneConfidiVO bc = new EscussioneConfidiVO();
		
		bc.setIdCurrentRecord(rs.getString("ID_ESCUSS_CONFIDI"));;
		bc.setIdModalitaAgevolazione(rs.getLong("ID_MODALITA_AGEVOLAZIONE"));
		bc.setNominativo(rs.getString("DENOM_CONFIDI"));
		bc.setDataEscussione(rs.getDate("DT_ESCUSS_CONFIDI"));
		bc.setDataPagamento(rs.getDate("DT_PAGAM_CONFIDI"));
		bc.setIdGaranzia(rs.getBigDecimal("ID_ATTIVITA_GARANZIA_CONFIDI"));
		bc.setGaranzia(rs.getString("DESC_ATT_MONIT_CRED"));
		bc.setPercGaranzia(rs.getBigDecimal("PERC_GARANZIA"));
		bc.setNote(rs.getString("NOTE"));
		bc.setDataInserimento(rs.getDate("DT_INSERIMENTO"));
		bc.setIstruttore(rs.getString("ISTRUTTORE"));
		
		return bc;
	}

}
