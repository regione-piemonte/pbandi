/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbservizit.integration.vo.RegolaAssociataBandoLineaVO;

public class RegolaAssociataBandoLineaRowMapper implements RowMapper<RegolaAssociataBandoLineaVO> {

	@Override
	public RegolaAssociataBandoLineaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		RegolaAssociataBandoLineaVO cm = new RegolaAssociataBandoLineaVO();

		cm.setDescBreveRegola(rs.getString("descBreveRegola"));
		cm.setTipoAssociazione(rs.getString("tipoAssociazione"));
		cm.setIdRegola(rs.getLong("idRegola"));
		cm.setDescRegolaComposta(rs.getString("descRegolaComposta"));
		cm.setNomeBandolinea(rs.getString("nomeBandolinea"));
		cm.setProgrBandoLineaIntervento(rs.getLong("progrBandoLineaIntervento"));

		return cm;
	}
}
