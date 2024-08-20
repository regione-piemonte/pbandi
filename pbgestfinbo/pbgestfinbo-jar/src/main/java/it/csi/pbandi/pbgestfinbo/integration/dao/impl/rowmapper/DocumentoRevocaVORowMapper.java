/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import it.csi.pbandi.pbgestfinbo.business.manager.DocumentoManager;
import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.DocumentoRevocaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DocumentoRevocaVORowMapper implements RowMapper<DocumentoRevocaVO> {

    @Autowired
    DocumentoManager documentoManager;

    @Override
    public DocumentoRevocaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DocumentoRevocaVO documentoRevocaVO = new DocumentoRevocaVO();

        documentoRevocaVO.setNumeroRevoca(rs.getLong("numeroRevoca"));
        documentoRevocaVO.setIdDocumento(rs.getLong("idDocumento"));
        documentoRevocaVO.setNomeFile(rs.getString("nomeFile"));
        documentoRevocaVO.setDataDocumento(rs.getDate("dataDocumento"));
        documentoRevocaVO.setIdTipoDocumento(rs.getLong("idTipoDocumento"));
        documentoRevocaVO.setBozza(false);

        switch (documentoRevocaVO.getIdTipoDocumento().intValue()){
            case 23:
                documentoRevocaVO.setOriginiDocumento("ricevuto da BENEFICIARIO");
                break;
            case 44:
                documentoRevocaVO.setOriginiDocumento("inviato da FINPIEMONTE");
                break;
            case 45:
                documentoRevocaVO.setOriginiDocumento("inviato da FINPIEMONTE");
                break;
            case 46:
                documentoRevocaVO.setOriginiDocumento("inviato da FINPIEMONTE");
                break;
            case 47:
                documentoRevocaVO.setOriginiDocumento("inviato da FINPIEMONTE");
                break;
            case 48:
                documentoRevocaVO.setOriginiDocumento("inviato da FINPIEMONTE");
                break;
            case 49:
                documentoRevocaVO.setOriginiDocumento("inviato da FINPIEMONTE");
                break;
            case 50:
                documentoRevocaVO.setOriginiDocumento("inviato da FINPIEMONTE");
                break;
            case 51:
                documentoRevocaVO.setOriginiDocumento("inviato da FINPIEMONTE");
                break;
            case 52:
                documentoRevocaVO.setOriginiDocumento("inviato da FINPIEMONTE");
                break;
            case 53:
                documentoRevocaVO.setOriginiDocumento("inviato da FINPIEMONTE");
                break;
            case 54:
                documentoRevocaVO.setOriginiDocumento("inviato da FINPIEMONTE");
                break;
            case 55:
                documentoRevocaVO.setOriginiDocumento("inviato da FINPIEMONTE");
                break;
            default:
                break;
        }

        return documentoRevocaVO;
    }
}
