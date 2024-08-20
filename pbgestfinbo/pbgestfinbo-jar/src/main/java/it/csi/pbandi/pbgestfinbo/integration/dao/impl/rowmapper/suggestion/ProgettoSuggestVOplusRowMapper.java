/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.suggestion;

import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.ProgettoSuggestVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProgettoSuggestVOplusRowMapper implements RowMapper<ProgettoSuggestVO> {
    @Override
    public ProgettoSuggestVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProgettoSuggestVO progettoSuggestVO = new ProgettoSuggestVO();

        progettoSuggestVO.setIdProgetto(rs.getLong("idProgetto"));
        progettoSuggestVO.setTitoloProgetto(rs.getString("titoloProgetto"));
        progettoSuggestVO.setCodiceVisualizzato(rs.getString("codiceVisualizzato"));

        return progettoSuggestVO;
    }
}
