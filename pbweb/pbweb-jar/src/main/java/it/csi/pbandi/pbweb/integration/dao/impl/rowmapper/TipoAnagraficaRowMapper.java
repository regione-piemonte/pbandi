/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbweb.integration.vo.TipoAnagraficaVO;

public class TipoAnagraficaRowMapper implements RowMapper<TipoAnagraficaVO>{

	@Override
	public TipoAnagraficaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		TipoAnagraficaVO cm = new TipoAnagraficaVO();
		
		cm.setIdTipoAnagrafica(rs.getBigDecimal("ID_TIPO_ANAGRAFICA"));
		cm.setIdSoggetto(rs.getBigDecimal("ID_SOGGETTO"));
		cm.setDescBreveTipoAnagrafica(rs.getString("DESC_BREVE_TIPO_ANAGRAFICA"));
		cm.setDescRuoloHelp(rs.getString("DESC_RUOLO_HELP"));
		cm.setDescTipoAnagrafica(rs.getString("DESC_TIPO_ANAGRAFICA"));
		cm.setDtFineValidita(rs.getDate("DT_FINE_VALIDITA"));
		cm.setDtInizioValidita(rs.getDate("DT_INIZIO_VALIDITA"));
		cm.setIdCategAnagrafica(rs.getBigDecimal("ID_CATEG_ANAGRAFICA"));
		cm.setIdRuoloHelp(rs.getBigDecimal("ID_RUOLO_HELP"));
	
		return cm;
	}
}
