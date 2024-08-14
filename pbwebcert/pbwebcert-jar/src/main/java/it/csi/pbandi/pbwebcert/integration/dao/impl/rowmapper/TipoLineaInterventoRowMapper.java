/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.PbandiDTipoLineaInterventoVO;

public class TipoLineaInterventoRowMapper implements RowMapper<PbandiDTipoLineaInterventoVO>{

	@Override
	public PbandiDTipoLineaInterventoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiDTipoLineaInterventoVO cm = new PbandiDTipoLineaInterventoVO();
		cm.setLivelloTipoLinea(rs.getBigDecimal("LIVELLOTIPOLINEA"));
		cm.setIdTipoLineaIntervento(rs.getBigDecimal("IDTIPOLINEAINTERVENTO"));
		cm.setDescTipoLineaFas(rs.getString("DESCTIPOLINEAFAS"));
		cm.setDtInizioValidita(rs.getDate("DTINIZIOVALIDITA"));
		cm.setCodTipoLinea(rs.getString("CODTIPOLINEA"));
		cm.setDescTipoLinea(rs.getString("DESCTIPOLINEA"));
		cm.setDtFineValidita(rs.getDate("DTFINEVALIDITA"));
		return cm;
	}

}
