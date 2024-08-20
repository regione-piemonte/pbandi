/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.iterAutorizzativi.DocumentoIterVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DocumentoIterVORowMapper implements RowMapper<DocumentoIterVO> {
    @Override
    public DocumentoIterVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DocumentoIterVO documentoIterVO = new DocumentoIterVO();

        documentoIterVO.setIdDocumentoIndex(rs.getLong("id_documento_index"));
        documentoIterVO.setNomeFile(rs.getString("nome_file"));
        documentoIterVO.setIdTipoDocumentoIndex(rs.getLong("id_tipo_documento_index"));
        documentoIterVO.setFlagExcel(false);

        try {
            documentoIterVO.setIdProgetto(rs.getLong("id_progetto"));
            if (rs.wasNull()) {
                documentoIterVO.setIdProgetto(null);
            }
            documentoIterVO.setCodiceVisualizzato(rs.getString("codice_visualizzato"));
        }catch (Exception ignored) {}

        return documentoIterVO;
    }
}
