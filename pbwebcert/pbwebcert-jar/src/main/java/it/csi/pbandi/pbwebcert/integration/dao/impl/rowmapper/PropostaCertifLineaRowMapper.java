/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.PbandiRPropostaCertifLineaVO;

public class PropostaCertifLineaRowMapper implements RowMapper<PbandiRPropostaCertifLineaVO> {

	@Override
	public PbandiRPropostaCertifLineaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiRPropostaCertifLineaVO cm = new PbandiRPropostaCertifLineaVO();
		cm.setIdLineaDiIntervento(rs.getBigDecimal("ID_LINEA_DI_INTERVENTO"));
		cm.setIdPropostaCertificaz(rs.getBigDecimal("ID_LINEA_DI_INTERVENTO"));
		cm.setIdUtenteIns(rs.getBigDecimal("ID_UTENTE_INS"));
		cm.setIdUtenteAgg(rs.getBigDecimal("ID_UTENTE_AGG"));
		return cm;
	}

}
