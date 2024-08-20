/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.SchedaClienteStoricoAllVO;

public class SchedaClienteStoricoStatoCreditoFinVORowMapper implements RowMapper<SchedaClienteStoricoAllVO> {

	@Override
	public SchedaClienteStoricoAllVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		SchedaClienteStoricoAllVO bc = new SchedaClienteStoricoAllVO();
				
		bc.setCreFin_statoCredito(rs.getString("DESC_STATO_CREDITO_FP"));
		bc.setCreFin_dataModifica(rs.getDate("DT_INIZIO_VALIDITA"));
		bc.setCreFin_utenteModifica(rs.getString("COGNOME_NOME"));
		
		return bc;
	}

}
