/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.suggestion;

import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.SuggestIdDescVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SuggestIdDescVORowMapper implements RowMapper<SuggestIdDescVO> {
    @Override
    public SuggestIdDescVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        SuggestIdDescVO suggestIdDescVO = new SuggestIdDescVO();

        suggestIdDescVO.setId(rs.getLong("id"));
        suggestIdDescVO.setDesc(rs.getString("descrizione"));
        try {
            suggestIdDescVO.setAltro(rs.getString("altro"));
        }catch(SQLException e){
            suggestIdDescVO.setAltro(null);
        }

        return suggestIdDescVO;
    }
}
