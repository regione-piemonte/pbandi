/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebbo.integration.vo.ProgettoSuggestVO;

public class ProgettoSuggestVORowMapper implements RowMapper<ProgettoSuggestVO> {
    @Override
    public ProgettoSuggestVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProgettoSuggestVO progettoSuggestVO = new ProgettoSuggestVO();

        progettoSuggestVO.setIdProgetto(rs.getLong("id_progetto"));
        progettoSuggestVO.setCodiceVisualizzato(rs.getString("codice_visualizzato"));

        return progettoSuggestVO;
    }
}
