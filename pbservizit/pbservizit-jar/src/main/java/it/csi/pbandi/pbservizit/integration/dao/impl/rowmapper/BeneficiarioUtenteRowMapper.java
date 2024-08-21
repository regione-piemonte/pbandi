/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbservizit.integration.vo.BeneficiarioSoggettoRuoloVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.profilazione.BeneficiarioUtenteVO;

public class BeneficiarioUtenteRowMapper implements RowMapper<BeneficiarioUtenteVO> {

	@Override
	public BeneficiarioUtenteVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		BeneficiarioUtenteVO cm = new BeneficiarioUtenteVO();
		
		cm.setCodiceFiscaleBeneficiario(rs.getString("codiceFiscaleBeneficiario"));
		cm.setDenominazioneBeneficiario(rs.getString("denominazioneBeneficiario"));
		cm.setIdSoggettoBeneficiario(rs.getBigDecimal("idSoggettoBeneficiario"));
		
		return cm;
	}
}
