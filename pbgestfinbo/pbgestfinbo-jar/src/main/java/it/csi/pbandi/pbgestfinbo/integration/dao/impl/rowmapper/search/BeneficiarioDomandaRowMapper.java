/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.search.BeneficiarioDomandaVO;

public class BeneficiarioDomandaRowMapper implements RowMapper<BeneficiarioDomandaVO>{

	@Override
	public BeneficiarioDomandaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		BeneficiarioDomandaVO cm = new BeneficiarioDomandaVO();
		
		
		return cm;
	}

}
