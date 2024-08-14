/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.BandoPropostaCertificazUnionPeriodoVO;

public class BandoPropostaUnionePeriodoRowMapper implements RowMapper<BandoPropostaCertificazUnionPeriodoVO>{

	@Override
	public BandoPropostaCertificazUnionPeriodoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		BandoPropostaCertificazUnionPeriodoVO cm = new BandoPropostaCertificazUnionPeriodoVO();
		cm.setDtOraCreazione(rs.getDate("DTORACREAZIONE"));
		cm.setIdPeriodo(rs.getBigDecimal("IDPERIODO"));
		cm.setDescPeriodoVisualizzata(rs.getString("DESCPERIODOVISUALIZZATA"));
		cm.setIdStatoPropostaCertif(rs.getBigDecimal("IDSTATOPROPOSTACERTIF"));
		cm.setIdPropostaCertificaz(rs.getBigDecimal("IDPROPOSTACERTIFICAZ"));
		cm.setDtInizioValidita(rs.getDate("DTINIZIOVALIDITA"));
		cm.setIdTipoPeriodo(rs.getBigDecimal("IDTIPOPERIODO"));
		cm.setDescProposta(rs.getString("DESCPROPOSTA"));
		cm.setDtFineValidita(rs.getDate("DTFINEVALIDITA"));
		cm.setDescPeriodo(rs.getString("DESCPERIODO"));
		return cm;
	}

}
