/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.BeneficiarioVO;
import it.csi.pbandi.pbwebcert.integration.vo.DenominazioneBeneficiarioVO;

public class DenominazioneBeneficiarioRowMapper implements RowMapper<BeneficiarioVO>{

	@Override
	public BeneficiarioVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		BeneficiarioVO cm = new BeneficiarioVO();
		cm.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
		cm.setDenominazioneBeneficiario(rs.getString("DENOMINAZIONE_BENEFICIARIO"));
		//cm.setCodiceFiscale(rs.getString("CODICEFISCALE"));
		return cm;
	}

}
