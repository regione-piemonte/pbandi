/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.BandoLineaVO;

public class BandoLineaRowMapper implements RowMapper<BandoLineaVO> {

	@Override
	public BandoLineaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		BandoLineaVO cm = new BandoLineaVO();
		//cm.setIdBando(rs.getBigDecimal("ID_BANDO"));
		cm.setNomeBandoLinea(rs.getString("NOME_BANDO_LINEA"));
		cm.setIdLinea(rs.getBigDecimal("ID_LINEA_DI_INTERVENTO"));
		//cm.setProgrBandoLineaIntervento(rs.getBigDecimal("ID_LINEA_DI_INTERVENTO"));
		return cm;
			
	}


}
