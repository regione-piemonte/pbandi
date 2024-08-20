/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.search.StoricoBeneficiarioVO;

public class StoricoBeneficiarioRowMapperPF implements RowMapper<StoricoBeneficiarioVO>{

	@Override
	public StoricoBeneficiarioVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		StoricoBeneficiarioVO cm = new  StoricoBeneficiarioVO();
		
		cm.setCognome(rs.getString("COGNOME"));
		cm.setNome(rs.getString("NOME"));
		cm.setIdSoggetto(rs.getString("ID_SOGGETTO"));
		cm.setTipoSoggetto(rs.getString("ID_TIPO_SOGGETTO"));
		cm.setCodiceFiscale(rs.getString("CODICE_FISCALE_SOGGETTO"));
		cm.setNdg(rs.getString("NDG"));
		return cm;
	}

}
