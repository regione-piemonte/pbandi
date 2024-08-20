/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.SchedaClienteStoricoAllVO;

public class SchedaClienteStoricoBancaBeneficiarioVORowMapper implements RowMapper<SchedaClienteStoricoAllVO> {

	@Override
	public SchedaClienteStoricoAllVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		SchedaClienteStoricoAllVO bc = new SchedaClienteStoricoAllVO();
				
		bc.setBanBen_banca(rs.getString("DESC_BANCA"));
		bc.setBanBen_dataModifica(rs.getDate("DT_INSERIMENTO"));
		bc.setBanBen_motivazione(rs.getString("MOTIVAZIONE"));
		bc.setBanBen_soggettoTerzo(rs.getString("DENOM_SOGGETTO_TERZO"));
		bc.setBanBen_utenteModifica(rs.getString("COGNOME_NOME"));

		return bc;
	}

}
