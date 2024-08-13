/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebbo.integration.vo.BoStorageDocumentoIndexDTO;

public class BoStorageDocumentoIndexDTORowMapper implements RowMapper<BoStorageDocumentoIndexDTO> {
	@Override
	public BoStorageDocumentoIndexDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		BoStorageDocumentoIndexDTO boStorageDocumentoIndexDTO = new BoStorageDocumentoIndexDTO();

		boStorageDocumentoIndexDTO.setIdDocumentoIndex(rs.getLong("ID_DOCUMENTO_INDEX"));
		boStorageDocumentoIndexDTO.setNomeFile(rs.getString("NOME_FILE"));
		boStorageDocumentoIndexDTO.setIdProgetto(rs.getLong("ID_PROGETTO"));
		boStorageDocumentoIndexDTO.setCodiceVisualizzatoProgetto(rs.getString("CODICE_VISUALIZZATO"));
		boStorageDocumentoIndexDTO.setIdTipoDocIndex(rs.getLong("ID_TIPO_DOCUMENTO_INDEX"));
		boStorageDocumentoIndexDTO.setDescrizioneTipoDocIndex(rs.getString("DESC_TIPO_DOC_INDEX"));
		boStorageDocumentoIndexDTO.setDescrizioneBreveTipoDocIndex(rs.getString("DESC_BREVE_TIPO_DOC_INDEX"));
		boStorageDocumentoIndexDTO.setFlagFirmato(
				rs.getString("NOME_DOCUMENTO_FIRMATO") != null && rs.getString("NOME_DOCUMENTO_FIRMATO").length() > 0
						? Boolean.TRUE
						: Boolean.FALSE);
		boStorageDocumentoIndexDTO.setFlagMarcato(
				rs.getString("NOME_DOCUMENTO_MARCATO") != null && rs.getString("NOME_DOCUMENTO_MARCATO").length() > 0
						? Boolean.TRUE
						: Boolean.FALSE);
		boStorageDocumentoIndexDTO.setFlagFirmaAutografa(
				rs.getString("FLAG_FIRMA_AUTOGRAFA") != null && rs.getString("FLAG_FIRMA_AUTOGRAFA").equals("S")
						? Boolean.TRUE
						: Boolean.FALSE);

		return boStorageDocumentoIndexDTO;
	}
}
