/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.suggestion;

import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.StatoRevocaSuggestVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatoRevocaSuggestVORowMapper implements RowMapper<StatoRevocaSuggestVO> {
    @Override
    public StatoRevocaSuggestVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        StatoRevocaSuggestVO statoRevocaSuggestVO = new StatoRevocaSuggestVO();

        statoRevocaSuggestVO.setIdStatoRevoca(rs.getLong("idStatoRevoca"));
        statoRevocaSuggestVO.setDescStatoRevoca(rs.getString("descStatoRevoca"));

        return statoRevocaSuggestVO;
    }
}
