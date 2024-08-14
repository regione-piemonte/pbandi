/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.PbandiDStatoPropostaCertifVO;

public class TipoStatoPropostaCertifRowMapper implements RowMapper<PbandiDStatoPropostaCertifVO>{

	@Override
	public PbandiDStatoPropostaCertifVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiDStatoPropostaCertifVO cm = new PbandiDStatoPropostaCertifVO();
		cm.setIdTipoStatoPropCert(rs.getBigDecimal("ID_TIPO_STATO_PROP_CERT"));
		cm.setDescBreveStatoPropostaCert(rs.getString("DESC_BREVE_STATO_PROPOSTA_CERT"));
		cm.setDescStatoPropostaCertif(rs.getString("DESC_STATO_PROPOSTA_CERTIF"));
		cm.setIdStatoPropostaCertif(rs.getBigDecimal("ID_STATO_PROPOSTA_CERTIF"));
		cm.setDtInizioValidita(rs.getDate("DT_INIZIO_VALIDITA"));
		cm.setDtFineValidita(rs.getDate("DT_FINE_VALIDITA"));
		return cm;
	}

}
