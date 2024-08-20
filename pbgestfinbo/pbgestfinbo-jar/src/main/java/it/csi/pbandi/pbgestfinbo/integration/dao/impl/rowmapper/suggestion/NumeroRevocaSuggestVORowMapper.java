/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.suggestion;

import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.NumeroRevocaSuggestVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NumeroRevocaSuggestVORowMapper implements RowMapper<NumeroRevocaSuggestVO> {
    @Override
    public NumeroRevocaSuggestVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        NumeroRevocaSuggestVO numeroRevocaSuggestVO = new NumeroRevocaSuggestVO();

        numeroRevocaSuggestVO.setIdGestioneRevoca(rs.getLong("idGestioneRevoca"));
        numeroRevocaSuggestVO.setNumeroRevoca(rs.getLong("numeroGestioneRevoca"));

        return numeroRevocaSuggestVO;
    }
}
