/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper;

import it.csi.pbandi.pbgestfinbo.integration.vo.ControlloDurcVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ControlloDurcVORowMapper implements RowMapper<ControlloDurcVO> {
    @Override
    public ControlloDurcVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ControlloDurcVO controlloDurcVO = new ControlloDurcVO();

        controlloDurcVO.setDataEmissioneDurc(rs.getDate("dtEmissioneDurc"));
        controlloDurcVO.setDataScadenzaDurc(rs.getDate("dtScadenza"));
        controlloDurcVO.setDescEsitoDurc(rs.getString("descEsitoRichieste"));
        controlloDurcVO.setDescStatoRichiestaDurc(rs.getString("descStatoRichiesta"));
        controlloDurcVO.setDescTipoRichiesta(rs.getString("descTipoRichiesta"));

        return controlloDurcVO;
    }
}
