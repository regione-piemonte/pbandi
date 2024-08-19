/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbweberog.integration.vo.TipoDocumentoIndexVO;

public class TipoDocumentoIndexMapper implements RowMapper<TipoDocumentoIndexVO>{

	@Override
	public TipoDocumentoIndexVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		TipoDocumentoIndexVO cm = new TipoDocumentoIndexVO();
		cm.setIdTipoDocumentoIndex(rs.getLong("ID_TIPO_DOCUMENTO_INDEX"));
		cm.setDescBreveTipoDocIndex(rs.getString("DESC_BREVE_TIPO_DOC_INDEX"));
		cm.setDescTipoDocIndex(rs.getString("DESC_TIPO_DOC_INDEX"));
		cm.setFlagFirmabile(rs.getString("FLAG_FIRMABILE"));
		cm.setFlagUploadable(rs.getString("FLAG_UPLOADABLE"));
		return cm;
	}

}
