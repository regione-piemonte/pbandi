/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.SchedaClienteStoricoAllVO;

public class SchedaClienteStoricoRatingVORowMapper implements RowMapper<SchedaClienteStoricoAllVO> {

	@Override
	public SchedaClienteStoricoAllVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		SchedaClienteStoricoAllVO bc = new SchedaClienteStoricoAllVO();
				
		bc.setRat_rating(rs.getString("RATING"));
		bc.setRat_provider(rs.getString("DESC_PROVIDER"));
		bc.setRat_dataClass(rs.getDate("DT_CLASSIFICAZIONE"));
		bc.setRat_utenteModifica(rs.getString("COGNOME_NOME"));

		return bc;
	}

}
