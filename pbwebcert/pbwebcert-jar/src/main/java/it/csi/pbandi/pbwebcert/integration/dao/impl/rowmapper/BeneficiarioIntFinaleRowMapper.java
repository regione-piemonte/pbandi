/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.BeneficiarioIntFinale;

public class BeneficiarioIntFinaleRowMapper implements RowMapper<BeneficiarioIntFinale>{

	@Override
	public BeneficiarioIntFinale mapRow(ResultSet rs, int rowNum) throws SQLException {
		BeneficiarioIntFinale cm = new BeneficiarioIntFinale();
		cm.setIdSoggetto(rs.getBigDecimal("ID_SOGGETTO"));
		cm.setDenominazioneBeneficiario(rs.getString("DESCRIZIONE"));
		return cm;
	}

}
