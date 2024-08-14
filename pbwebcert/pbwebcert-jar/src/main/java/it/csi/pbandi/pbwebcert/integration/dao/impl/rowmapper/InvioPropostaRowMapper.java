/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.PbandiDInvioPropostaCertifVO;

public class InvioPropostaRowMapper implements RowMapper<PbandiDInvioPropostaCertifVO>{

	@Override
	public PbandiDInvioPropostaCertifVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiDInvioPropostaCertifVO cm = new PbandiDInvioPropostaCertifVO();
		cm.setIdInvioPropostaCertif(rs.getBigDecimal("ID_INVIO_PROPOSTA_CERTIF"));
		cm.setEmail(rs.getString("EMAIL"));
		cm.setIdLineaDiIntervento(rs.getBigDecimal("ID_LINEA_DI_INTERVENTO"));
		cm.setDtInizioValidita(rs.getDate("DT_INIZIO_VALIDITA"));
		cm.setDtFineValidita(rs.getDate("DT_FINE_VALIDITA"));
		cm.setIdStatoPropostaCertif(rs.getBigDecimal("DT_FINE_VALIDITA"));
		
		return cm;
	}

}
