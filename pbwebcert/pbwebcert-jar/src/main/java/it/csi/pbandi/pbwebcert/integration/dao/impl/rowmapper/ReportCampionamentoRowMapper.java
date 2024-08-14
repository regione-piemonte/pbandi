/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.ReportCampionamentoVO;

public class ReportCampionamentoRowMapper implements RowMapper<ReportCampionamentoVO>{
	@Override
	public ReportCampionamentoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ReportCampionamentoVO cm = new ReportCampionamentoVO();
		cm.setIdNormativa(rs.getBigDecimal("IDNORMATIVA"));
		cm.setIdDocumentoIndex(rs.getBigDecimal("IDDOCUMENTOINDEX"));
		cm.setNomeFile(rs.getString("NOMEFILE"));
		cm.setDescNormativa(rs.getString("NORMATIVA"));
		cm.setDescAnnoContabile(rs.getString("DESCPERIODOVISUALIZZATA"));
		cm.setDataCampionamento(rs.getDate("DATACAMPIONAMENTO"));
		cm.setIdAnnoContabile(rs.getBigDecimal("IDPERIODO"));
		return cm;
	}

}
