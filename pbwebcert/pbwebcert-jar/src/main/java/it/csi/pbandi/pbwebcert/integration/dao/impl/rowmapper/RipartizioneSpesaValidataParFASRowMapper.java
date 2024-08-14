/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.RipartizioneSpesaValidataParFASVO;

public class RipartizioneSpesaValidataParFASRowMapper implements RowMapper<RipartizioneSpesaValidataParFASVO> {

	@Override
	public RipartizioneSpesaValidataParFASVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		RipartizioneSpesaValidataParFASVO cm = new RipartizioneSpesaValidataParFASVO();
		cm.setIdPropostaCertificaz(rs.getBigDecimal("ID_PROPOSTA_CERTIFICAZ"));
		cm.setSpesaValidata(rs.getBigDecimal("SPESA_VALIDATA"));
		cm.setReportKey(rs.getString("REPORT_KEY"));
		return cm;
	}


}
