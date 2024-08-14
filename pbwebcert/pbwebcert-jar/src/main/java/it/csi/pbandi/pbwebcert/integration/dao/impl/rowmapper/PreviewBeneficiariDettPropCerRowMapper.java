/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.PbandiTPreviewDettPropCerVO;

public class PreviewBeneficiariDettPropCerRowMapper implements RowMapper<PbandiTPreviewDettPropCerVO>{

	@Override
	public PbandiTPreviewDettPropCerVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiTPreviewDettPropCerVO cm = new PbandiTPreviewDettPropCerVO();
		cm.setDenominazioneBeneficiario(rs.getString("DENOMINAZIONEBENEFICIARIO"));
		cm.setIdSoggettoBeneficiario(rs.getBigDecimal("IDSOGGETTOBENEFICIARIO"));
		return cm;
	}

}
