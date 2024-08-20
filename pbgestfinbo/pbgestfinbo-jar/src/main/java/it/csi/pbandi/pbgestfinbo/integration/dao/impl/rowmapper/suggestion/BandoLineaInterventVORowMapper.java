/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.suggestion;

import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.BandoLineaInterventVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BandoLineaInterventVORowMapper implements RowMapper<BandoLineaInterventVO> {
    @Override
    public BandoLineaInterventVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        BandoLineaInterventVO bandoLineaInterventVO = new BandoLineaInterventVO();

        bandoLineaInterventVO.setIdBandoLineaIntervent(rs.getLong("idBandoLineaIntervent"));
        bandoLineaInterventVO.setNomeBandoLineaIntervent(rs.getString("nomeBandoLineaIntervent"));

        return bandoLineaInterventVO;
    }
}
