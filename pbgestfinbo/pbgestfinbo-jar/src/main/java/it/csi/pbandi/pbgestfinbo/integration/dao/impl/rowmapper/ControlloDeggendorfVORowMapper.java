/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.ControlloDeggendorfVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ControlloDeggendorfVORowMapper implements RowMapper<ControlloDeggendorfVO> {
    @Override
    public ControlloDeggendorfVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ControlloDeggendorfVO controlloDeggendorfVO = new ControlloDeggendorfVO();

        controlloDeggendorfVO.setDataEmissione(rs.getDate("dataEmissione"));
        controlloDeggendorfVO.setVercor(rs.getString("vercor"));
        if(rs.getLong("esitoRichiesta") == 1){
            controlloDeggendorfVO.setEsitoRichiesta(true);
        }else if(rs.getLong("esitoRichiesta") == 2){
            controlloDeggendorfVO.setEsitoRichiesta(false);
        }
        controlloDeggendorfVO.setDataScadenza(rs.getDate("dataScadenza"));

        return controlloDeggendorfVO;
    }
}
