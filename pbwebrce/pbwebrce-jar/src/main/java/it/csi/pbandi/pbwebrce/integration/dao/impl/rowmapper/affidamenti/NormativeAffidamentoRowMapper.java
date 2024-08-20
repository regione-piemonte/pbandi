/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiDNormativaAffidamentoVO;


public class NormativeAffidamentoRowMapper implements RowMapper<PbandiDNormativaAffidamentoVO> {

	@Override
	public PbandiDNormativaAffidamentoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiDNormativaAffidamentoVO cm = new PbandiDNormativaAffidamentoVO();
		cm.setIdNorma(rs.getBigDecimal("ID_NORMA"));
		cm.setDescNorma(rs.getString("DESC_NORMA"));
		return cm;
	}


}
