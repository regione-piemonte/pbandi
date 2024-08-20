/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.suggestion;

import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.BandoSuggestVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BandoSuggestVORowMapper implements RowMapper<BandoSuggestVO> {

    @Override
    public BandoSuggestVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        BandoSuggestVO bandoSuggestVO = new BandoSuggestVO();

        bandoSuggestVO.setTitoloBando(rs.getString("titoloBando"));
        bandoSuggestVO.setIdBando(rs.getLong("idBando"));

        return bandoSuggestVO;
    }
}
