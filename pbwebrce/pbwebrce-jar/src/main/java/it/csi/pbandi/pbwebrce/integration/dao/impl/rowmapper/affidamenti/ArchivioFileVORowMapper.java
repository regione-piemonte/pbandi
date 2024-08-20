/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebrce.integration.vo.affidamenti.ArchivioFileVO;

public class ArchivioFileVORowMapper implements RowMapper<ArchivioFileVO> {

	@Override
	public ArchivioFileVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ArchivioFileVO cm = new ArchivioFileVO();
		cm.setIdEntita(rs.getBigDecimal("IDENTITA"));
		cm.setIdTarget(rs.getBigDecimal("IDTARGET"));
		cm.setIdProgetto(rs.getBigDecimal("IDPROGETTO"));
		cm.setNumProtocollo(rs.getString("NUMPROTOCOLLO"));
		cm.setNomeFile(rs.getString("NOMEFILE"));
		cm.setIdDocumentoIndex(rs.getBigDecimal("IDDOCUMENTOINDEX"));
		cm.setDescStatoTipoDocIndex(rs.getString("DESCSTATOTIPODOCINDEX"));
		cm.setDescBreveTipoDocIndex(rs.getString("DESCBREVETIPODOCINDEX"));
		return cm;
	}
}
