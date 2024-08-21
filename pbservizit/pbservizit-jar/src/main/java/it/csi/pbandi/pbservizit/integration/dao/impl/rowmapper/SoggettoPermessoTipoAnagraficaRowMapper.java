/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import it.csi.pbandi.pbservizit.integration.vo.SoggettoPermessoTipoAnagraficaVO;

public class SoggettoPermessoTipoAnagraficaRowMapper implements RowMapper<SoggettoPermessoTipoAnagraficaVO> {
	
	@Override
	public SoggettoPermessoTipoAnagraficaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		SoggettoPermessoTipoAnagraficaVO cm = new SoggettoPermessoTipoAnagraficaVO();
		
		cm.setDescBrevePermesso(rs.getString("descBrevePermesso"));
		cm.setDescBreveTipoAnagrafica(rs.getString("descBreveTipoAnagrafica"));
		cm.setDescPermesso(rs.getString("descPermesso"));
		cm.setDescTipoAnagrafica(rs.getString("descTipoAnagrafica"));
		cm.setIdSoggetto(rs.getBigDecimal("idSoggetto"));
		
		return cm;
	}

}
