/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.search.DettaglioImpresaVO;

public class DettaglioImpresaRowMapper implements RowMapper<DettaglioImpresaVO>{

	@Override
	public DettaglioImpresaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		DettaglioImpresaVO di = new DettaglioImpresaVO();
		
		di.setAnnoRiferimento(rs.getString("DESC_PERIODO"));
		di.setUnitaLavorativeAnnue(rs.getString("ULA"));
		di.setFatturato(rs.getString("IMP_FATTURATO"));
		di.setTotaleBilancioAnnuale(rs.getString("TOT_BILANCIO_ANNUO"));
		
		return di;
	}

}
