/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.LiberazioneBancaVO;

public class LiberazioneBancaVORowMapper implements RowMapper<LiberazioneBancaVO> {

	@Override
	public LiberazioneBancaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		LiberazioneBancaVO bc = new LiberazioneBancaVO();
				
		bc.setIdCurrentRecord(rs.getString("ID_LIBERAZ_BANCA"));
		bc.setIdModalitaAgevolazione(rs.getLong("ID_MODALITA_AGEVOLAZIONE"));
		bc.setDataLiberazione(rs.getDate("DT_LIBERAZ_BANCA"));
		bc.setMotivazione(rs.getString("DESC_ATT_MONIT_CRED"));
		bc.setBancaLiberata(rs.getString("BANCA_LIBERATA"));
		bc.setNote(rs.getString("NOTE"));
		bc.setStorData(rs.getDate("DT_INSERIMENTO"));
		bc.setStorIstruttore(rs.getString("ISTRUTTORE"));
		
		return bc;
	}

}
