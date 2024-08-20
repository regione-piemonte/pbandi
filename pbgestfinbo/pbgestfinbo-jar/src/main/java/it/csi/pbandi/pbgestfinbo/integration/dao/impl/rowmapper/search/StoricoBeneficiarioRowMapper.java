/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.search.StoricoBeneficiarioVO;

public class StoricoBeneficiarioRowMapper implements RowMapper<StoricoBeneficiarioVO>{

	@Override
	public StoricoBeneficiarioVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		StoricoBeneficiarioVO cm = new  StoricoBeneficiarioVO();
		
		cm.setDenominazione(rs.getString("DENOMINAZIONE_ENTE_GIURIDICO"));
		cm.setcF(rs.getString("CODICE_FISCALE_SOGGETTO"));
		cm.setpIva(rs.getString("PARTITA_IVA"));
		cm.setTipoSogg(rs.getString("DESC_TIPO_SOGGETTO"));
		cm.setNdg(rs.getString("NDG"));
		
		return cm;
	}

}
